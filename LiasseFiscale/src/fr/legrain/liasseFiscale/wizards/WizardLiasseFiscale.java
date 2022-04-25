package fr.legrain.liasseFiscale.wizards;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.Bundle;

import fr.legrain.gestCom.librairiesEcran.swt.LgrWorkEvent;
import fr.legrain.gestCom.librairiesEcran.swt.LgrWorkListener;
import fr.legrain.liasseFiscale.LiasseFiscalePlugin;
import fr.legrain.liasseFiscale.actions.Cle;
import fr.legrain.liasseFiscale.actions.Compte;
import fr.legrain.liasseFiscale.actions.InfoComplement;
import fr.legrain.liasseFiscale.actions.InfosCompta;
import fr.legrain.liasseFiscale.actions.Repart;
import fr.legrain.liasseFiscale.actions.Repartition;
import fr.legrain.liasseFiscale.db.ConstLiasse;
import fr.legrain.liasseFiscale.preferences.PreferenceConstants;
import fr.legrain.lib.data.LibChaine;
import fr.legrain.lib.data.LibConversion;

public class WizardLiasseFiscale extends Wizard implements IWizardNode {
	
	static Logger logger = Logger.getLogger(WizardLiasseFiscale.class.getName());
	
	private WizardLiasseModel model = new WizardLiasseModel(); 
	
	private WizardPageRegime wizardPageRegime = new WizardPageRegime(WizardPageRegime.PAGE_NAME);
	private WizardPageImportCompta wizardPageImportCompta = new WizardPageImportCompta(WizardPageImportCompta.PAGE_NAME);
	
	private ArrayList<WizardPageSaisie> listePageSaisie = new ArrayList<WizardPageSaisie>();
	private Composite pageContainer;
	
	private String repDoc = "/documents";
	private String repTypeDoc = "/liasse";
	private String repAnneeDoc = null;
	private String repRegimeDoc = null;
	
	private String streamRepertoireDoc = "";
	private String streamRepertoirePDF = "/pdf";
	private String streamRepertoireImagesPDF = "/images";
	
	private URL urlImage = null;
	private URL urlPDF = null;
	private String nomFichierPDF = null;
	
	public WizardLiasseFiscale() {
		super();
		setWindowTitle("Assistant - Liasse fiscale");
		
//		addPage(wizardPageDocumentFiscal);
		addPage(wizardPageRegime);
		//addPage(wizardPageSaisie);
		addPage(wizardPageImportCompta);
		
		//ajoutPagesSaisie(null);
		
		//addPage(wizardPageComplement);
		
	}
	
	/**
	 * Ajout des données saisies à la main dans la listeRepatition (pour qu'elles soient prises en compte dans les calculs)
	 */
	public void addSaisieToAutomatique() {
		InfoComplement complement;
		Repartition compl;
		if(model.getInfosCompta().getListeSaisieComplement()!=null)
			for (Cle cle : model.getInfosCompta().getListeSaisieComplement().keySet()) {
				complement = model.getInfosCompta().getListeSaisieComplement().get(cle);
				compl = new Repartition();
				compl.setValeur(complement.getValeur1());
				//pour le montant on prend la 1ere valeur (valeur1) et on la converti en double
				if(LibConversion.queDesChiffres(complement.getValeur1(), true))
					compl.setMontant(LibConversion.stringToDouble(complement.getValeur1()));
				else {
					compl.setMontant(0d);
				}
				getModel().getRepartition().getListeRepartition().put(new Cle(complement.getCle(),null),compl);
			}
	}
	
	/**
	 * Ajout des données saisies à la main de la listeRepatition
	 */
	public void removeSaisieFromAutomatique() {
		InfoComplement complement;
		for (Cle cle : model.getInfosCompta().getListeSaisieComplement().keySet()) {
			complement = model.getInfosCompta().getListeSaisieComplement().get(cle);
			getModel().getRepartition().getListeRepartition().remove(new Cle(complement.getCle(),null));
		}
	}
	
	/**
	 * Calcul des totaux en prenant en compte ce qui a été saisie manuellement
	 * et mise à jour de toutes les pages
	 */
	public void calculTotauxAvecSaisieManuelle() {
		addSaisieToAutomatique();
		getModel().getRepartition().calculTotaux(null, getModel().getRegime().valeurSQL(), EnumTypeDoc.liasse.valeurSQL(), null);
		updatePageSaisie();
		removeSaisieFromAutomatique();
	}
	
	/**
	 * Calcul des totaux en prenant en compte ce qui a été saisie manuellement
	 * et mise à jour d'une page particuliere
	 */
	public void calculTotauxAvecSaisieManuelle(WizardPageSaisie page) {
		addSaisieToAutomatique();
		getModel().getRepartition().calculTotaux(null, getModel().getRegime().valeurSQL(), EnumTypeDoc.liasse.valeurSQL(), null);
		updatePageSaisie(page);
		removeSaisieFromAutomatique();
	}
	
	/**
	 * Recherche les informations qui ont déjà été saisies dans les fichiers XML,
	 * pour préremplir les différentes pages du wizard.
	 */
	private void initWizard() {

		File modelFile = new File(model.getCheminDocument()+"/"+ConstLiasse.C_FICHIER_XML_MODEL_DOC);
		if(modelFile.exists()) {
	      	//récupération des parametres du document
        	WizardLiasseModel m = (WizardLiasseModel)model.lectureXML(model.getCheminDocument()+"/"+ConstLiasse.C_FICHIER_XML_MODEL_DOC);
        	model.copyProperties(m);
		} else {
			MessageDialog.openError(
				this.getShell(),"Erreur","Le fichier "+ConstLiasse.C_FICHIER_XML_MODEL_DOC+" est introuvable.");
			getShell().close();
		}
		
		File repart = new File(model.getCheminDocument()+"/"+ConstLiasse.C_FICHIER_XML_REPART_INITIAL);
        if(repart.exists()) { //reprise d'une liasse
        	
        	Repart r = new Repart();
        	InfosCompta inf = new InfosCompta();
        	boolean donneesSaisies = false;
        	//récupération des données provenant de la compta
        	getModel().setRepartition(r.lectureXML(model.getCheminDocument()+"/"+ConstLiasse.C_FICHIER_XML_REPART_INITIAL));
        	//récupération des données saisies à la main
        	if(new File(model.getCheminDocument()+"/"+ConstLiasse.C_FICHIER_XML_COMPTA_FINAL).exists()) {
        		donneesSaisies = true;
        		getModel().setInfosCompta(inf.lectureXML(model.getCheminDocument()+"/"+ConstLiasse.C_FICHIER_XML_COMPTA_FINAL));
        	} else {
        		logger.debug(ConstLiasse.C_FICHIER_XML_COMPTA_FINAL+" introuvable, pas de récupération des données saisies à la main");
        	}
        	//calculs
        	try {
        		if(donneesSaisies) addSaisieToAutomatique();
        		getModel().getRepartition().calculTotaux(null, getModel().getRegime().valeurSQL(), EnumTypeDoc.liasse.valeurSQL(), null);
        		if(donneesSaisies) removeSaisieFromAutomatique();
        	} catch(Exception e) {
        		logger.error("",e);
        	}
        	
        	
        	ajoutPageSaisieAvecProgression("Récupération des données ...");
        	updatePageSaisie();
        	updatePageSaisieSupplement();
        	
        	getModel().setNouveauDocument(false);
        	
        }
	}

	protected void backPressed(ILgrWizardPage page) {
	}
	
	protected void nextPressed(ILgrWizardPage page) {
		page.finishPage();
	}
	
	protected void finishPressed(ILgrWizardPage page) {
		page.finishPage();
	}
	
	@Override
	public boolean canFinish() {
		if(getContainer().getCurrentPage().getName().equals(WizardPageImportCompta.PAGE_NAME)
				|| getContainer().getCurrentPage().getName().equals(WizardPageRegime.PAGE_NAME)) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean performFinish() {
		fin();
		return true;
	}
	
	public void fin() {	
		model.sauveInfosComptaXML(ConstLiasse.C_FICHIER_XML_COMPTA_FINAL);
		
		repartitionAvecProgression("Répartition finale");
		
		model.getRepartition().arrondiFinalLiasse();
		
		model.sauveRepartXML(ConstLiasse.C_FICHIER_XML_REPART_FINAL);
		String nomFdf = "repart.fdf";
		if(model.getRegime().compareTo(EnumRegimeFiscal.agricole)==0) {
			model.sauveFDF(nomFdf,ConstLiasse.C_PDF_LIASSE_AGRI,urlPDF);
		} else {
			model.sauveFDF(nomFdf,ConstLiasse.C_PDF_LIASSE_BIC,urlPDF);
		}
		
//		System.err.println("************** "+model.getRegime().toString()+" --- "+model.getTypeDocument());
		model.sortieXML(model.getCheminDocument()+"/"+ConstLiasse.C_FICHIER_XML_MODEL_DOC);
//		System.err.println("************** "+model.getRegime().toString()+" --- "+model.getTypeDocument());
		
//		if(System.getProperty("os.name").equals("Windows"))
//			model.openFDFWindows(nomFdf);
//		else if(System.getProperty("os.name").equals("Linux"))
//			model.openFDFLinux(nomFdf);
		model.openFDFPreference(nomFdf);
		
		//model.openFDFIText(nomFdf);
		
		new DernierDocument().enregistreDernierDoc(model);
		
	}	
	
	@Override
	public boolean performCancel() {
		model.sortieXML(model.getCheminDocument()+"/"+ConstLiasse.C_FICHIER_XML_MODEL_DOC);
		return super.performCancel();
	}

	public WizardLiasseModel getModel() {
		return model;
	}
	
	/**
	 * Ajout des pages générées à partir des images des pages du document PDF
	 * Convertion PDF->images possible avec "ImageMagick" 
	 * <a href="http://www.imagemagick.org/">(http://www.imagemagick.org/)</a><br>
	 * <code>convert -density 100 -resize 100% *.pdf *.png</code><br>
	 * ou avec ghostscript : <br>
	 * <code>gs -dSAFER -dBATCH -dNOPAUSE -sDEVICE=png256 -r300 -sOutputFile=test_%03d.png *.pdf</code>
	 */
	public void ajoutPagesSaisie(IProgressMonitor monitor) {
		try {
			WizardPageSaisie wizardPageSaisie = null;
			int hauteurPage = 1169;
			int largeurPage = 826;
			 
			int nbPages = 0;
			int pagePDF = 0;
			String extension = ".png";
			
			//TODO a recupere en dynamique si possible sinon parametrage par bdd
//			if(model.getAnneeDocument()==2007) {
//				hauteurPage = 1200;
//				 largeurPage = 865;
//				 Image originalImage = new Image (pageContainer.getDisplay(), urlImage.openStream());
//				 hauteurPage =  originalImage.getImageData().height;
//				 largeurPage = originalImage.getImageData().width;
//			}

			if(monitor!=null) {
				monitor.beginTask("Génération des pages ...",nbPages);
				//monitor.beginTask("Génération des pages ...",IProgressMonitor.UNKNOWN);
			}
			
			Bundle bundle = LiasseFiscalePlugin.getDefault().getBundle();
			//initialisation des chemins
			String debutNomImg = null;
			if(model.getRegime().compareTo(EnumRegimeFiscal.agricole)==0) {
				nomFichierPDF = ConstLiasse.C_PDF_LIASSE_AGRI;
				repAnneeDoc = "/"+model.getAnneeDocumentPDF();
				repRegimeDoc = "/agricole";
				debutNomImg = "agri-";

				streamRepertoireDoc = repDoc+repTypeDoc+repAnneeDoc+repRegimeDoc;
				urlPDF = bundle.getEntry(streamRepertoireDoc+streamRepertoirePDF+"/"+nomFichierPDF);
			} else {
				nomFichierPDF = ConstLiasse.C_PDF_LIASSE_BIC;
				repAnneeDoc = "/"+model.getAnneeDocumentPDF();
				repRegimeDoc = "/bic";
				debutNomImg = "bic-";

				streamRepertoireDoc = repDoc+repTypeDoc+repAnneeDoc+repRegimeDoc;
				urlPDF = bundle.getEntry(streamRepertoireDoc+streamRepertoirePDF+"/"+nomFichierPDF);
			}
			logger.debug("streamRepertoireDoc => "+streamRepertoireDoc);
			
			//calcul du nombre d'image PNG et donc de page
			Enumeration e = bundle.findEntries(streamRepertoireDoc+streamRepertoireImagesPDF,"*"+extension,false);
			nbPages = 1;
			while(e.hasMoreElements()) {
				e.nextElement();
				nbPages++;
			}
			
			//créations des pages à partir du fichier PDF et des images
			
			int decalage = 1;
			//if(model.getRegime().compareTo(EnumRegimeFiscal.agricole)==0) {
				if(bundle.getEntry(streamRepertoireDoc+streamRepertoireImagesPDF+"/"+debutNomImg+(pagePDF-0)+extension)==null)
					decalage = 0;
			//} else {
			//	if(bundle.getEntry(streamRepertoireDoc+streamRepertoireImagesPDF+"/bic-"+(pagePDF-0)+extension)==null)
			//		decalage = 0;
			//}
			
			for (int i = 1; i < nbPages; i++) {
				pagePDF = i;
				//if(model.getRegime().compareTo(EnumRegimeFiscal.agricole)==0) {
					urlImage = bundle.getEntry(streamRepertoireDoc+streamRepertoireImagesPDF+"/"+debutNomImg+(pagePDF-decalage)+extension);
				//} else {
				//	urlImage = bundle.getEntry(streamRepertoireDoc+streamRepertoireImagesPDF+"/bic-"+(pagePDF-decalage)+extension);
				//}
				
				Image originalImage = new Image (pageContainer.getDisplay(), urlImage.openStream());
				 hauteurPage =  originalImage.getImageData().height;
				 largeurPage = originalImage.getImageData().width;
				 originalImage.dispose();
					
				wizardPageSaisie = new WizardPageSaisie(WizardPageSaisie.PAGE_NAME+"_"+pagePDF,urlPDF.openStream(),
						nomFichierPDF,urlImage.openStream(),pagePDF,hauteurPage,largeurPage
											
				);
				
				//ajout des pages au wizard et creation de leur contenu
				addPage(wizardPageSaisie);
				listePageSaisie.add(wizardPageSaisie);
				
				if(pageContainer!=null) {
					wizardPageSaisie.createControl(pageContainer);
					if (wizardPageSaisie.getControl() != null)
						wizardPageSaisie.getControl().setVisible(false);
					pageContainer.layout(true);
				}
				if(monitor!=null) {
					monitor.worked(1);
				}
			}
			if(monitor!=null) {
				monitor.done();
			}
			((ILgrWizardDialog)this.getContainer()).resize(wizardPageSaisie);
		} catch(Exception e) {
			logger.error("",e);
			MessageDialog.openError(
					this.getShell(),"Erreur","Impossible de poursuivre l'assistant.\nIl manque des informations nécessaires à la créations des pages du document sélectionné.");
			this.getWizard().performCancel();
			//getShell().close();
		}
	}
		
	@Override
	public void createPageControls(Composite pageContainer) {
		super.createPageControls(pageContainer);
		this.pageContainer = pageContainer;
		if(!getModel().isAutomatique())
			initWizard();
	}
	
	/**
	 * Parcours tous les champs d'une page et modifie leur valeur quand 
	 * ils correspondent a une cle présente dans la "repartition"
	 */
	public void updatePageSaisie(WizardPageSaisie page) {
		Repartition r;
		Text t;
		Font oldFont;
		String tooltipDetail;
		
		String[] rgbCouleurASaisir = LiasseFiscalePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_COULEUR_A_SAISIR).split(",");
		String[] rgbCouleurCalculs = LiasseFiscalePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_COULEUR_CALCULS).split(",");
		//String[] rgbCouleurSaisie = LiasseFiscalePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_COULEUR_SAISIE).split(",");
		
		Color c = new Color(this.getShell().getDisplay(),Integer.parseInt(rgbCouleurASaisir[0]),Integer.parseInt(rgbCouleurASaisir[1]),Integer.parseInt(rgbCouleurASaisir[2]));
		Color cDisable = new Color(this.getShell().getDisplay(),Integer.parseInt(rgbCouleurCalculs[0]),Integer.parseInt(rgbCouleurCalculs[1]),Integer.parseInt(rgbCouleurCalculs[2]));
		
		for ( String nomChamp : page.getListeChamp().keySet()) {
			tooltipDetail = "";
			t = page.getListeChamp().get(nomChamp);
			r = model.getRepartition().getListeRepartition().get(new Cle(nomChamp));
			oldFont = t.getFont();
			t.setFont(new Font(this.getShell().getDisplay(),
					oldFont.getFontData()[0].getName(),
					fontSize(t.getSize().y),
					oldFont.getFontData()[0].getStyle()
			));
	
			if(r!=null) {
				for (Compte cpt : r.getDetail()) {
					tooltipDetail+=cpt.getNumero()+"\tDébit : "+cpt.getMtDebit()+"\tCrédit : "+cpt.getMtCredit()+"\n";
				}
				if(LibConversion.queDesChiffres(r.getValeur(),true)) {
					//arrondi a l'affichage
//					t.setText(String.valueOf(r.getMontant()));
					t.setText(String.valueOf(model.getRepartition().arrondiLiasse(r.getMontant(),0)));
				}
				//t.setText(r.getValeur());
				//t.setEnabled(false);
				if(!model.getInfosCompta().getListeSaisieComplement().containsKey(new Cle(nomChamp))){ //si la cellule a ete rempli a la main, elle doit rester modifiable
					t.setEditable(false);
					t.setBackground(cDisable);
				}
			} else {
				t.setText("");
				t.setBackground(c);
			}
			if(!LibChaine.empty(tooltipDetail)) {
//				t.setToolTipText(nomChamp+"\n"+tooltipDetail);
				try {
					ToolTipDetail myTooltipLabel = addToolTipDetailCompte(t,r.getDetail());
					myTooltipLabel.setHeaderText("Détail - "+nomChamp);
					myTooltipLabel.setShift(new Point(-5, -5));
					myTooltipLabel.setHideOnMouseDown(false);
					//myTooltipLabel.setPopupDelay(4000);
					myTooltipLabel.activate();
				} catch(Exception e) {
					logger.error("",e);
				}
			}
			else {
//				t.setToolTipText(nomChamp);
				if(r!=null) {
					ToolTipDetail myTooltipLabel = addToolTipDetailCompte(t,r.getDetail());
					myTooltipLabel.setHeaderText("Détail - "+nomChamp);
					myTooltipLabel.setShift(new Point(-5, -5));
					myTooltipLabel.setHideOnMouseDown(false);
					//myTooltipLabel.setPopupDelay(4000);
					myTooltipLabel.activate();
				}
			}
		}
	}

	/**
	 * Parcours tous les champs de toutes les pages et modifie leur valeur quand 
	 * ils correspondent a une cle présente dans la "repartition"
	 */
	public void updatePageSaisie() {
		logger.debug("debut updatePageSaisie");
		
		for (WizardPageSaisie page : listePageSaisie) {
			updatePageSaisie(page);
		}
		logger.debug("fin updatePageSaisie");
	}
	
	/**
	 * Parcours tous les champs de toutes les pages et modifie leur valeur quand 
	 * ils correspondent a une cle présente dans la "repartition"
	 */
	public void updatePageSaisieSupplement() {
		logger.debug("debut updatePageSaisie");
		//InfosCompta r;
		Text t;
		Font oldFont;
		String[] rgbCouleurSaisie = LiasseFiscalePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_COULEUR_SAISIE).split(",");
		Color c = new Color(this.getShell().getDisplay(),Integer.parseInt(rgbCouleurSaisie[0]),Integer.parseInt(rgbCouleurSaisie[1]),Integer.parseInt(rgbCouleurSaisie[2]));
		InfoComplement info;
		for (WizardPageSaisie page : listePageSaisie) {
			for ( String nomChamp : page.getListeChamp().keySet()) {
				t = page.getListeChamp().get(nomChamp);
				String s = null; 
				info = model.getInfosCompta().getListeSaisieComplement().get(new Cle(nomChamp));
				if(info!=null)
					s=info.getValeur1();
				oldFont = t.getFont();
				t.setFont(new Font(this.getShell().getDisplay(),
						oldFont.getFontData()[0].getName(),
						fontSize(t.getSize().y),
						oldFont.getFontData()[0].getStyle()
				));
				//t.setToolTipText(nomChamp);
				if(s!=null) {
					t.setText(s);
					t.setEnabled(true);
					t.setBackground(c);
				} else {
					//	t.setText("");
					//	t.setBackground(c);
				}
			}
		}
		logger.debug("fin updatePageSaisie");
	}
	
	/**
	 * Recherche dans la map de la liste des champs de toutes les pages et
	 * affecte change sa valeur si sa cle dans la map est nomChampModif
	 */
	public void updateChampSaisie(String nomChampModif, String valeur) {
		//logger.debug("debut updateChampSaisie");
		for (WizardPageSaisie page : listePageSaisie) {
			for ( String nomChamp : page.getListeChamp().keySet()) {
				if(nomChamp.equals(nomChampModif))
					page.getListeChamp().get(nomChamp).setText(valeur);
			}
		}
		//logger.debug("fin updateChampSaisie");
	}
	
	/**
	 * Retourne une taille de police la plus adaptée possible à la hauteur d'un champ texte
	 * @param textFieldHeight - hauteur du champ texte
	 * @return
	 */
	public int fontSize(int textFieldHeight) {
		//tous les champs du pdf sont en "9"
		int retour = 0;
		if (textFieldHeight<=10)
			retour = 6;
		else if (textFieldHeight>10)
			retour = 8;
		else 
			retour = 8;
		
		return retour;
	}
	
	/**
	 * Ajoute un tooltip contenant la liste des comptes, pris en compte dans la cellule
	 * @param t
	 * @param detail
	 * @return
	 */
	public ToolTipDetail addToolTipDetailCompte(Control t, final List<Compte> detail) {
		ToolTipDetail myTooltipLabel = new ToolTipDetail(t) {

			protected Composite createContentArea(Composite parent) {
				Composite comp = super.createContentArea(parent);
				comp.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_INFO_BACKGROUND));
				//FillLayout layout = new FillLayout();
				//layout.marginWidth=5;
				//comp.setLayout(layout);
				GridLayout gl = new GridLayout(1,false);
				GridData layout = new GridData(SWT.FILL,SWT.FILL,true,false);
				layout.heightHint=200;
				comp.setLayout(gl);
				comp.setLayoutData(layout);
				
				
				Table table = new Table(comp, SWT.BORDER | SWT.SINGLE | SWT.FULL_SELECTION);
				table.setHeaderVisible(true);
				table.setLinesVisible(true);
				table.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_INFO_BACKGROUND));
				
				TableColumn colonne1 = new TableColumn(table, SWT.LEFT);
				colonne1.setText("Compte");
				TableColumn colonne2 = new TableColumn(table, SWT.RIGHT);
				colonne2.setText("Débit");
				TableColumn colonne3 = new TableColumn(table, SWT.RIGHT);
				colonne3.setText("Crédit");
				
				for (Compte cpt : detail) {
					TableItem ligne = new TableItem(table,SWT.NONE);
					if(cpt.getMtDebit()!=null) 
						ligne.setText(new String[]{cpt.getNumero(),cpt.getMtDebit().toString(),""});
					else if(cpt.getMtCredit()!=null) 
						ligne.setText(new String[]{cpt.getNumero(),"",cpt.getMtCredit().toString()});
					else
						ligne.setText(new String[]{cpt.getNumero(),"",""});
				}
				
				int marge = 10;
				colonne1.pack();
				colonne2.pack();
				colonne3.pack();
				colonne1.setWidth(colonne1.getWidth()+marge);
				colonne2.setWidth(colonne2.getWidth()+marge);
				colonne3.setWidth(colonne3.getWidth()+marge);
				//table.pack();

				return comp;
			}
		};
		return myTooltipLabel;
	}
	
	public void repartitionAvecProgression() {
		repartitionAvecProgression(null);
	}
	
	public void repartitionAvecProgression(String libelle) {
		try {
			setNeedsProgressMonitor(true);
			DoRepart doRepart = new DoRepart(libelle);
			model/*.getRepartition()*/.addLgrWorkListener(doRepart);
			//execution dans un autre thread car rien ne concerne l'ui
			getContainer().run(true, true, doRepart);
			model/*.getRepartition()*/.removeLgrWorkListener(doRepart);
		} catch (InvocationTargetException e) {
			logger.error("",e);
		} catch (InterruptedException e) {
			logger.error("",e);
		}
	}
	
	public void ajoutPageSaisieAvecProgression(String libelle) {
		try {
			setNeedsProgressMonitor(true);
			DoAjoutPageSaisie doAjoutPageSaisie = new DoAjoutPageSaisie(libelle);
			getContainer().run(false, true, doAjoutPageSaisie);
		} catch (InvocationTargetException e) {
			logger.error("",e);
		} catch (InterruptedException e) {
			logger.error("",e);
		}
	}
	
	private class DoAjoutPageSaisie implements IRunnableWithProgress/*, LgrWorkListener*/ {
		private IProgressMonitor monitor;
		private String libelle = "Génération des pages du document ...";
		
		public IProgressMonitor getMonitor() {
			return monitor;
		}

		public DoAjoutPageSaisie(String libelle) {
			super();
			if(libelle != null)
				this.libelle = libelle;
		}
		
		public void run(IProgressMonitor monitor) {
			this.monitor = monitor;
			ajoutPagesSaisie(monitor);
		}
		
//		public void beginWork(LgrWorkEvent evt) {
//			monitor.beginTask(null, evt.getTotalAmount());
//		}
//		
//		public void endWork(LgrWorkEvent evt) {
//			monitor.done();
//		}
//
//		public void beginSubtask(LgrWorkEvent evt) {
//			if(evt.getSubTaskName()!=null)
//				monitor.subTask(evt.getSubTaskName());
//		}
//
//		public void work(LgrWorkEvent evt) {
//			monitor.worked(1);
//		}
	}
	
	private class DoRepart implements IRunnableWithProgress, LgrWorkListener {
		private IProgressMonitor monitor;
		private String libelle = "Répartition";
		
		public DoRepart(String libelle) {
			super();
			if(libelle != null)
				this.libelle = libelle;
		}
		
		public void run(IProgressMonitor monitor) {
			this.monitor = monitor;
			model.repartitionDocument();
		}
		
		public void work(LgrWorkEvent evt) {
			monitor.worked(1);
		}
		
		public void beginWork(LgrWorkEvent evt) {
//			if(evt.getSubTaskName()==null)
				//monitor.beginTask(libelle+" : ", evt.getTotalAmount());
				monitor.beginTask(null, evt.getTotalAmount());
//			else
//				monitor.beginTask(libelle+" - "+evt.getSubTaskName()+" : ", evt.getTotalAmount());
		}
		
		public void endWork(LgrWorkEvent evt) {
			monitor.done();
		}

		public void beginSubtask(LgrWorkEvent evt) {
			if(evt.getSubTaskName()!=null)
				monitor.subTask(evt.getSubTaskName());
		}
	}
	
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
	public Point getExtent() {
		// TODO Auto-generated method stub
		return new Point(-1,-1);
	}

	public IWizard getWizard() {
		// TODO Auto-generated method stub
		return this;
	}

	public boolean isContentCreated() {
		// TODO Auto-generated method stub
		return false;
	}
	
}

package fr.legrain.liasseFiscale.wizards.fichier;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jface.wizard.WizardSelectionPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;

import fr.legrain.liasseFiscale.LiasseFiscalePlugin;
import fr.legrain.liasseFiscale.actions.InfoComplement;
import fr.legrain.liasseFiscale.db.ConstLiasse;
import fr.legrain.liasseFiscale.preferences.PreferenceConstants;
import fr.legrain.liasseFiscale.wizards.CompositePageImportCompta;
import fr.legrain.liasseFiscale.wizards.Document;
import fr.legrain.liasseFiscale.wizards.EnumTypeDoc;
import fr.legrain.liasseFiscale.wizards.ILgrWizardPage;
import fr.legrain.liasseFiscale.wizards.WizardDoc;
import fr.legrain.liasseFiscale.wizards.WizardDocumentFiscal;
import fr.legrain.liasseFiscale.wizards.WizardDossier;
import fr.legrain.liasseFiscale.wizards.WizardLiasseFiscale;
import fr.legrain.liasseFiscale.wizards.WizardTVA;

public class WizardPageImportFichier /*extends WizardPage*/
									 extends WizardSelectionPage 
									 implements ILgrWizardPage {
	
	static Logger logger = Logger.getLogger(WizardPageImportFichier.class.getName());
	
	static final public String PAGE_NAME = "WizardPageImportCompta";
	static final private String PAGE_TITLE = "Importation des données.";
	private Control control;
	private Listener listenerValidate = new Listener() {
        public void handleEvent(Event e) {
            setPageComplete(validatePage());
        }
    };
    private final String WIZ_LIASSE_FISCALE = "wizard_liasse_fiscale";
    private final String WIZ_TVA = "wizard_tva";
    private final String WIZ_DOSSIER = "wizard_dossier";
    private final String WIZ_DOCUMENT = "wizard_document";
    private String nextWizard = null;
    

    /////////////////////////////--WizardSelectionPage--//////////////////////////////////////
    private HashMap<String,IWizardNode> wizards = new HashMap<String,IWizardNode>();

    public void addWizard(String name, IWizardNode wizard) {
    	wizards.put(name,wizard);
    }


	public WizardPageImportFichier(String pageName) {
		super(pageName);
		setTitle(PAGE_TITLE);
		this.addWizard(WIZ_LIASSE_FISCALE,new WizardLiasseFiscale());
		this.addWizard(WIZ_TVA,new WizardTVA());
		this.addWizard(WIZ_DOSSIER,new WizardDossier());
		this.addWizard(WIZ_DOCUMENT,new WizardDoc());
	}

	public void createControl(Composite parent) {
		CompositePageImportFichier control = new CompositePageImportFichier(parent,SWT.NULL);
		
		final Shell shell = parent.getShell();
		final CompositePageImportFichier controlfinal = control;
		
		control.getTxtFichierCompta().addListener(SWT.Modify, listenerValidate);
		control.getBtnParcourirFichierCompta().addListener(SWT.Selection,new Listener() {
			public void handleEvent(Event e) {
				FileDialog d = new FileDialog(shell);
				d.setFilterPath(LiasseFiscalePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_CHEMIN_COMPTA));
				controlfinal.getTxtFichierCompta().setText(d.open());
			}
		});
		
		this.control = control;
		setControl(control);
		
		setPageComplete(validatePage());
	}
	
	public Control getControl() {
		return control;
	}

	public boolean validatePage() {
		if (isPageComplete()) {
			saveToModel();
//			if(((WizardDocFichier)getWizard()).getModel().getNouveauDocument()
//					|| ((CompositePageImportCompta)control).getCbEcraser().getSelection()
//					) {
			((WizardDocFichier)getWizard()).getModel().lectureInfosCompta(((CompositePageImportFichier)control).getTxtFichierCompta().getText());
			//test dossier
			//((WizardDocFichier)getWizard()).getModel().getInfosCompta().getListeInfosDossier()
			Map<String,String> infosDossier = new HashMap<String,String>();
			for (InfoComplement info : ((WizardDocFichier)getWizard()).getModel().getInfosCompta().getListeInfosDossier()) {
				System.out.println(info.getCle()+" --- "+info.getValeur1());
				infosDossier.put(info.getCle(), info.getValeur1());
			}
			
			String dossier = infosDossier.get("Dossier");
			String exo = infosDossier.get("Nom_Exercice");
			
			((WizardDocFichier)getWizard()).getModel().setNomDossier(dossier);
			((WizardDocFichier)getWizard()).getModel().setAnneeFiscale(exo);
			
			if(((WizardDocFichier)getWizard()).getModel().dossierExiste(dossier)) {
				setSelectedNode(wizards.get(WIZ_DOCUMENT));
				initNextWizard(WIZ_DOCUMENT);
				System.out.println("Le dossier existe deja");
				//quel type de document
				//test document
				//si doc existe deja : ecrasé ?
			} else {
				setSelectedNode(wizards.get(WIZ_DOSSIER));
				initNextWizard(WIZ_DOSSIER);
				// surcharge de ((WizardDocFichier)getWizard()).getNextPage(page)
				//creation du dossier et du document
				//faire suivre une variable pour enchainer les 2 creations
			}
		}
//		if(((CompositePageImportFichier)control).getTxtFichierCompta().getText().equals("")) {
//			setErrorMessage(null);
//			setMessage("Veuillez indiquer l'emplacement du fichier provenant du logiciel de comptabilité.");
//			return false;
//		}
//        
//		saveToModel();
//        setErrorMessage(null);
//        setMessage(null);
        return true;
	}
	
	@Override
	public void setVisible(boolean visible) {
//		if(((WizardLiasseFiscale)getWizard()).getModel().getCheminFichierCompta()!=null) {
//			((CompositePageImportCompta)control).getTxtFichierCompta()
//			.setText(((WizardLiasseFiscale)getWizard()).getModel().getCheminFichierCompta());
//		}
//		if(!((WizardLiasseFiscale)getWizard()).getModel().getNouveauDocument()) {
//			((CompositePageImportCompta)control).getTxtFichierCompta().setEnabled(false);
//		}
//		if(((WizardLiasseFiscale)getWizard()).getModel().getNouveauDocument()) {
//			((CompositePageImportCompta)control).getCbEcraser().setVisible(false);
//		}
//		if(((WizardLiasseFiscale)getWizard()).getModel().estVerrouille()) {
//			((CompositePageImportCompta)control).getCbEcraser().setVisible(false);
//			((CompositePageImportCompta)control).getCbEcraser().setSelection(false);
//			((CompositePageImportCompta)control).getTxtFichierCompta().setEnabled(false);
//			((CompositePageImportCompta)control).getBtnParcourirFichierCompta().setEnabled(false);
//		}
		super.setVisible(visible);
	}

	public void finishPage() {
		try {
		if (isPageComplete()) {
			saveToModel();
//			if(((WizardDocFichier)getWizard()).getModel().getNouveauDocument()
//					|| ((CompositePageImportCompta)control).getCbEcraser().getSelection()
//					) {
				
				
				
			//	((WizardDocFichier)getWizard()).getModel().copieFichierCompta(ConstLiasse.C_FICHIER_COMPTA_LOCAL);
				
			//	((WizardDocFichier)getWizard()).getModel().sauveInfosComptaXML(ConstLiasse.C_FICHIER_XML_COMPTA_INITIAL);
				
//				((WizardDocFichier)getWizard()).repartitionAvecProgression();
//				((WizardDocFichier)getWizard()).getModel().sauveRepartXML(ConstLiasse.C_FICHIER_XML_REPART_INITIAL);
//				((WizardDocFichier)getWizard()).updatePageSaisie();
//			}

		}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void saveToModel() {
		((WizardDocFichier)getWizard()).getModel().setCheminFichierCompta(((CompositePageImportFichier)control).getTxtFichierCompta().getText());
		//((WizardDocFichier)getWizard()).getModel().setNomDossier(((CompositePageImportCompta)control).getTxtFichierCompta().getText());
	}
	
	/**
	 * Selection du wizard suivant en fonction de l'element selectionne.
	 * @param nomDossierDoc
	 * @return - L'ID du wizard suivant
	 */
	public String choixWizard(TreeItem selection) {
		//TODO identification du type de document et choix du wizard en conséquence.
		nextWizard = null;
		Document d = new Document();
	//	d = d.lectureXML(cheminRepSelection(selection)+"/"+ConstLiasse.C_FICHIER_ID_DOC);
		
		if(d.getTypeDocument().compareTo(EnumTypeDoc.liasse)==0) {
			nextWizard = WIZ_LIASSE_FISCALE;
		} else if(d.getTypeDocument().compareTo(EnumTypeDoc.TVA)==0) {
			nextWizard = WIZ_TVA;
		}
		setSelectedNode(wizards.get(nextWizard));
		return nextWizard;
	}
	
	/**
	 * Initialisation d'une partie d'une partie du modèle du prochain wizard dans le noeud
	 * @param nextWizard - ID du prochain wizard
	 */
	public void initNextWizard(String nextWizard) {
		if(nextWizard.equals(WIZ_LIASSE_FISCALE)) {
			//((WizardLiasseFiscale)wizards.get(WIZ_LIASSE_FISCALE).getWizard()).getModel().setNomDossier(((WizardDocumentFiscal)getWizard()).getModel().getNomDossier());
	        //((WizardLiasseFiscale)wizards.get(WIZ_LIASSE_FISCALE).getWizard()).getModel().setCheminDocument(((WizardDocumentFiscal)getWizard()).getModel().getCheminDocument());
	 
		} else if(nextWizard.equals(WIZ_TVA)) {
			;
		} else if(nextWizard.equals(WIZ_DOSSIER)) {
			((WizardDossier)wizards.get(WIZ_DOSSIER).getWizard()).getModel().copyProperties(((WizardDocFichier)getWizard()).getModel());
			//((WizardDossier)wizards.get(WIZ_DOSSIER).getWizard()).getModel().setNomDossier(((WizardDocFichier)getWizard()).getModel().getNomDossier());
			//((WizardDossier)wizards.get(WIZ_DOSSIER).getWizard()).getModel().setAnneeFiscale(((WizardDocFichier)getWizard()).getModel().getAnneeFiscale());
		} else if(nextWizard.equals(WIZ_DOCUMENT)) {
			((WizardDoc)wizards.get(WIZ_DOCUMENT).getWizard()).getModel().copyProperties(((WizardDocFichier)getWizard()).getModel());
			//((WizardDoc)wizards.get(WIZ_DOCUMENT).getWizard()).getModel().setNomDossier(((WizardDocFichier)getWizard()).getModel().getNomDossier());
			//((WizardDoc)wizards.get(WIZ_DOCUMENT).getWizard()).getModel().setAnneeFiscale(((WizardDocFichier)getWizard()).getModel().getAnneeFiscale());
	       // ((WizardDoc)wizards.get(WIZ_DOCUMENT).getWizard()).getModel().setCheminDocument(((WizardDocFichier)getWizard()).getModel().getCheminDocument());

		}
	}

}

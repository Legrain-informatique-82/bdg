package fr.legrain.analyseeconomique.direct;

import java.io.File;

import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import fr.legrain.analyseeconomique.Activator;
import fr.legrain.analyseeconomique.actions.ConstAnalyseEco;
import fr.legrain.analyseeconomique.preferences.PreferenceConstants;
import fr.legrain.liasseFiscale.LiasseFiscalePlugin;
import fr.legrain.liasseFiscale.db.ConstLiasse;
import fr.legrain.liasseFiscale.wizards.Document;
import fr.legrain.liasseFiscale.wizards.EnumTypeDoc;
import fr.legrain.liasseFiscale.wizards.HeadlessLiasse;
import fr.legrain.liasseFiscale.wizards.IControllerInfos;
import fr.legrain.liasseFiscale.wizards.ILgrWizardPage;
import fr.legrain.liasseFiscale.wizards.ValidationResult;
import fr.legrain.liasseFiscale.wizards.WizardDoc;
import fr.legrain.liasseFiscale.wizards.WizardDocumentFiscalModel;
import fr.legrain.liasseFiscale.wizards.WizardLiasseModel;

public class WizardPageAnalyseEcoDirect 
									extends WizardPage
									implements ILgrWizardPage {
	
	static final public String PAGE_NAME = "WizardPageChoixLiasse";
	static final private String PAGE_TITLE = "Exportation pour l'analyse economique";
	private Control control;
	private Listener listenerValidate = new Listener() {
        public void handleEvent(Event e) {
            setPageComplete(validatePage());
        }
    };
    
//    private IControllerInfos controllerInfos = null;
    private String fichierComptaPrecedent = null;

//    private String liasseSelectionnee = null;// = "kjhkj/2008";

	public WizardPageAnalyseEcoDirect(String pageName) {
		super(pageName);
		setTitle(PAGE_TITLE);
	}
	
	public void createControl(Composite parent) {
		CompositePageAnalyseEcoDirect control = new CompositePageAnalyseEcoDirect(parent,SWT.NULL);
		
		final Shell shell = parent.getShell();
		final CompositePageAnalyseEcoDirect controlfinal = control;
		
		control.getTfCheminCompta().addListener(SWT.Modify, listenerValidate);
		control.getBtnCheminCompta().addListener(SWT.Selection,new Listener() {
			public void handleEvent(Event e) {
				FileDialog d = new FileDialog(shell);
				d.setFilterPath(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_CHEMIN_EXPORT_COMPTA_LIASSE));
				controlfinal.getTfCheminCompta().setText(d.open());
			}
		});
		
		control.getCbSite().setSelection(((WizardAnalyseEconomiqueDirect)getWizard()).isOuvertureAutoSite());
		control.getCbFTP().setSelection(((WizardAnalyseEconomiqueDirect)getWizard()).isTransfertAutoSite());
		
		String[] listeNomAgence = new String[ConstAnalyseEco.C_LISTE_AGENCE.length];
		for (int i = 0; i < ConstAnalyseEco.C_LISTE_AGENCE.length; i++) {
			listeNomAgence[i] = ConstAnalyseEco.C_LISTE_AGENCE[i][0];
		}
		control.getCbAgence().setItems(listeNomAgence);
		
		control.getCbAgence().select(ConstAnalyseEco.findPositionListeAgence(((WizardAnalyseEconomiqueDirect)getWizard()).getAgence(), false));
		
		control.getTfAnneeFiscale().addListener(SWT.Modify, listenerValidate);
		control.getTfDossier().addListener(SWT.Modify, listenerValidate);
		
		//controllerInfos = new ControllerInfosLiasseAE(control,WizardPageChoixLiasse.this);		
		
		this.control = control;
		setControl(control);
		
		initInterface();
		setPageComplete(validatePage());
	}
	
	public void initInterface() {
		enableInfosDossier(false);
		((CompositePageAnalyseEcoDirect)control).getRaRegimeAgri().setSelection(true);
		((CompositePageAnalyseEcoDirect)control).getCbEcraser().setSelection(true);
		
		((CompositePageAnalyseEcoDirect)control).getTfCheminCompta().removeListener(SWT.Modify, listenerValidate);
		((CompositePageAnalyseEcoDirect)control).getTfAnneeFiscale().removeListener(SWT.Modify, listenerValidate);
		((CompositePageAnalyseEcoDirect)control).getTfDossier().removeListener(SWT.Modify, listenerValidate);
		
		((CompositePageAnalyseEcoDirect)control).getTfAnneeFiscale().setText("");
		((CompositePageAnalyseEcoDirect)control).getTfCheminCompta().setText("");
		((CompositePageAnalyseEcoDirect)control).getTfDossier().setText("");
		
		((CompositePageAnalyseEcoDirect)control).getTfCheminCompta().addListener(SWT.Modify, listenerValidate);
		((CompositePageAnalyseEcoDirect)control).getTfAnneeFiscale().addListener(SWT.Modify, listenerValidate);
		((CompositePageAnalyseEcoDirect)control).getTfDossier().addListener(SWT.Modify, listenerValidate);
	}

	
	public Control getControl() {
		return control;
	}

	public boolean validatePage() {
		setErrorMessage(null);
		setMessage(null);
		boolean valide = true;
//		ValidationResult validation = null;
		
		//OK si la sélection est sur un document et non un dossier "d'organisation" du prog
		String selection = null;
		selection = ((CompositePageAnalyseEcoDirect)control).getTfCheminCompta().getText();

		if(selection!=null && !selection.equals("")) {

			enableInfosDossier(false);

			HeadlessLiasse hl = new HeadlessLiasse(selection,true);
			WizardLiasseModel wlf = hl.infosCompta();
			
			if((fichierComptaPrecedent==null 
					|| (fichierComptaPrecedent!=null && !fichierComptaPrecedent.equals(selection)))
					&& hl.infosComptaValide(wlf)) {
				//Le fichier a changer => on lit les nouvelles valeurs pour initialiser l'interface
				((CompositePageAnalyseEcoDirect)control).getTfCheminCompta().removeListener(SWT.Modify, listenerValidate);
				((CompositePageAnalyseEcoDirect)control).getTfAnneeFiscale().removeListener(SWT.Modify, listenerValidate);
				((CompositePageAnalyseEcoDirect)control).getTfDossier().removeListener(SWT.Modify, listenerValidate);
				
				((CompositePageAnalyseEcoDirect)control).getTfAnneeFiscale().setText(wlf.getAnneeFiscale());
				((CompositePageAnalyseEcoDirect)control).getTfDossier().setText(wlf.getNomDossier());
				((CompositePageAnalyseEcoDirect)control).getCbEcraser().setSelection(true);
				((CompositePageAnalyseEcoDirect)control).getRaRegimeAgri().setSelection(true);
				((CompositePageAnalyseEcoDirect)control).getCbVerrouLiasse().setSelection(false);

				((CompositePageAnalyseEcoDirect)control).getTfCheminCompta().addListener(SWT.Modify, listenerValidate);
				((CompositePageAnalyseEcoDirect)control).getTfAnneeFiscale().addListener(SWT.Modify, listenerValidate);
				((CompositePageAnalyseEcoDirect)control).getTfDossier().addListener(SWT.Modify, listenerValidate);
			} else {
				valide = false;
				setMessage("Veuillez sélectionner un fichier de comptabilité.",DialogPage.ERROR);
				//Le fichier n'a pas changer => on prend les valeur de l'interface
//				wlf.setAnneeFiscale(((CompositePageAnalyseEcoDirect)control).getTfAnneeFiscale().getText());
//				wlf.setNomDossier(((CompositePageAnalyseEcoDirect)control).getTfDossier().getText());
				//wlf.setRegime(((CompositePageAnalyseEcoDirect)control).getRaRegimeAgri());
			}
			fichierComptaPrecedent = selection;

			if(wlf.dossierExiste(wlf.getNomDossier())) {
				setMessage("Le dossier "+wlf.getNomDossier()+" existe deja.",DialogPage.WARNING);

				if(liasseExiste(wlf.getNomDossier(), wlf.getAnneeFiscale())) {
					setMessage("La liasse "+wlf.getAnneeFiscale()+" existe deja dans le dossier "+wlf.getNomDossier()+"."
							+"\n Si vous continuez les données seront écrasées.",DialogPage.WARNING);
					String chemin = ConstLiasse.C_REPERTOIRE_BASE+ConstLiasse.C_REPERTOIRE_PROJET;
					String cheminDoc = chemin+"/"+wlf.getNomDossier()+"/"+ConstLiasse.C_REP_DOC_TYPE_LIASSE;
					if(WizardDocumentFiscalModel.estVerrouille(cheminDoc+"/"+wlf.getAnneeFiscale())) {
						setMessage("La liasse "+wlf.getAnneeFiscale()+" dans le dossier "+wlf.getNomDossier()+" est verrouillée.",DialogPage.ERROR);
						((CompositePageAnalyseEcoDirect)control).getCbVerrouLiasse().setSelection(true);
						valide = false;
					} else {
						//setMessage("pas verrou",DialogPage.INFORMATION);
					}
				} else {
					//setMessage("liasse existe pas");
				}

			} else {
				//setMessage("dossier existe pas");
			}

//			enableInfosDossier(true);
		} else {
			//pas de fichier de compta selectionne
			setMessage("Veuillez sélectionner un fichier de comptabilité.",DialogPage.INFORMATION);
			initInterface();
			valide = false;
		}

        return valide;
	}
	
	private void enableInfosDossier(boolean enabled) {
		((CompositePageAnalyseEcoDirect)control).getRaRegimeBIC().setEnabled(false);
		((CompositePageAnalyseEcoDirect)control).getRaRegimeAgri().setEnabled(false);
		((CompositePageAnalyseEcoDirect)control).getCbVerrouLiasse().setEnabled(false);
	
		((CompositePageAnalyseEcoDirect)control).getTfAnneeFiscale().setEnabled(enabled);
		((CompositePageAnalyseEcoDirect)control).getTfDossier().setEnabled(enabled);
		((CompositePageAnalyseEcoDirect)control).getCbEcraser().setEnabled(enabled);
	}
	
	public boolean liasseExiste(String nomDossier, String anneeFiscale) {
		boolean existe = false;
		String chemin = ConstLiasse.C_REPERTOIRE_BASE+ConstLiasse.C_REPERTOIRE_PROJET;
		String cheminDoc = chemin+"/"+nomDossier+"/"+ConstLiasse.C_REP_DOC_TYPE_LIASSE;
		
		File rep = new File(cheminDoc);
		if(rep.exists()) { //else : 1ere liasse dans ce dossier
			File[] listeDossier = rep.listFiles();
			for (int i = 0; i < listeDossier.length; i++) {
				if(listeDossier[i].isDirectory()) {
					if(listeDossier[i].getName().equals(anneeFiscale)) {
						existe = true;
					}
				}
			}
		}
		return existe;
	}
	
	public boolean estUneLiasse(String selection) {
		boolean result = false;
		Document d = new Document();
		if(new File(selection+"/"+ConstLiasse.C_FICHIER_ID_DOC).exists()) {
			d = d.lectureXML(selection+"/"+ConstLiasse.C_FICHIER_ID_DOC);

			if(d.getTypeDocument().compareTo(EnumTypeDoc.liasse)==0) {
				result = true;
			}
		}
		return result;
	}

	public void finishPage() {
		if (isPageComplete()) {
			saveToModel();
		}
	}

	public void saveToModel() {
		((WizardAnalyseEconomiqueDirect)getWizard()).setOuvertureAutoSite(((CompositePageAnalyseEcoDirect)control).getCbSite().getSelection());
		((WizardAnalyseEconomiqueDirect)getWizard()).setTransfertAutoSite(((CompositePageAnalyseEcoDirect)control).getCbFTP().getSelection());
		
		((WizardAnalyseEconomiqueDirect)getWizard()).setAgence(ConstAnalyseEco.findValueListeAgence(((CompositePageAnalyseEcoDirect)control).getCbAgence().getText()));
		
		((WizardAnalyseEconomiqueDirect)getWizard()).getModel().setNomDossier(((CompositePageAnalyseEcoDirect)control).getTfDossier().getText());
		((WizardAnalyseEconomiqueDirect)getWizard()).getModel().setAnneeFiscale(((CompositePageAnalyseEcoDirect)control).getTfAnneeFiscale().getText());
		((WizardAnalyseEconomiqueDirect)getWizard()).getModel().setCheminFichierCompta(((CompositePageAnalyseEcoDirect)control).getTfCheminCompta().getText());	
	}

}

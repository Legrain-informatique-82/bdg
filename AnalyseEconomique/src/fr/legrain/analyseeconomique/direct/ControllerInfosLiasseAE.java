//package fr.legrain.analyseeconomique.direct;
//
//import java.io.File;
//import java.util.Calendar;
//
//import org.eclipse.jface.wizard.WizardPage;
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.widgets.Event;
//import org.eclipse.swt.widgets.Listener;
//
//import fr.legrain.liasseFiscale.db.ConstLiasse;
//import fr.legrain.liasseFiscale.wizards.CompositeInfosLiasse;
//import fr.legrain.liasseFiscale.wizards.IControllerInfos;
//import fr.legrain.liasseFiscale.wizards.ILgrWizardPage;
//import fr.legrain.liasseFiscale.wizards.ValidationResult;
//import fr.legrain.liasseFiscale.wizards.WizardDoc;
//import fr.legrain.liasseFiscale.wizards.WizardDocumentFiscalModel;
//
//public class ControllerInfosLiasseAE implements IControllerInfos {
//	private CompositePageAnalyseEcoDirect composite;
//	private WizardPage lgrWizardPage; //WizardPage /ILgrWizardPage
//	private Listener listenerValidate = new Listener() {
//		public void handleEvent(Event e) {
//			//lgrWizardPage.validatePage();
//			lgrWizardPage.setPageComplete(((ILgrWizardPage)lgrWizardPage).validatePage());
//		}
//	};
//	static public final String DOCUMENT_EXISTE = "DOCUMENT_EXISTE";
//	static public final String DOCUMENT_VERROUILLE = "DOCUMENT_VERROUILLE";
//	
//	public ControllerInfosLiasseAE(CompositePageAnalyseEcoDirect composite, WizardPage lgrWizardPage) {
//		this.composite = composite;
//		this.lgrWizardPage = lgrWizardPage;
//		
////		composite.getSpinAnnee().setSelection(Calendar.getInstance().get(Calendar.YEAR));
////		if(((WizardDoc)lgrWizardPage.getWizard()).getModel().getAnneeFiscale()==null)
////			composite.getTfAnneeFiscale().setText(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
//		
//		composite.getTfAnneeFiscale().addListener(SWT.Modify , listenerValidate);
////		composite.getSpinAnnee().addListener(SWT.Selection, listenerValidate);
//		
//		//**composite.getTfDossier().addListener(SWT.Modify , listenerValidate);
//		
//	}
//	
//	public CompositePageAnalyseEcoDirect getComposite() {
//		return composite;
//	}
//
//	public void setComposite(CompositePageAnalyseEcoDirect composite) {
//		this.composite = composite;
//	}
//	
//	public ValidationResult validateComposite() {
//		ValidationResult res = new ValidationResult();
//		if(composite.getTfAnneeFiscale().getText()==null || composite.getTfAnneeFiscale().getText().equals("")) {
//			res.setError("Veuillez saisir une année fiscale");
//			res.setInfo(null);
//			return res;
//		}
//		
//		//Nom/Annee de liasse inexistant dans le dossier
//		//if(((CompositePageAjoutLiasse)control).getListeDossier().getSelection().length>0) {
//			String chemin = ConstLiasse.C_REPERTOIRE_BASE+ConstLiasse.C_REPERTOIRE_PROJET;
////			String annee = String.valueOf(composite.getSpinAnnee().getSelection());
//			String anneeFiscale = composite.getTfAnneeFiscale().getText();
//			String nomDossier = ((WizardDoc)lgrWizardPage.getWizard()).getModel().getNomDossier();
//			String cheminDoc = chemin+"/"+nomDossier+"/"+ConstLiasse.C_REP_DOC_TYPE_LIASSE;
//			File rep = new File(cheminDoc);
//			if(rep.exists()) { //else : 1ere liasse dans ce dossier
//				File[] listeDossier = rep.listFiles();
//				for (int i = 0; i < listeDossier.length; i++) {
//					if(listeDossier[i].isDirectory()) {
//						if(listeDossier[i].getName().equals(anneeFiscale)) {
//						//if(listeDossier[i].getName().equals(annee)) {
//							String message = "La liasse pour l'année fiscale \""+listeDossier[i].getName()+"\" existe déjà dans le dossier "+nomDossier+".";
//							res.setError(message);
//							res.setInfo(message);
//							if(WizardDocumentFiscalModel.estVerrouille(cheminDoc+"/"+anneeFiscale))
//								res.setCodeRetour(DOCUMENT_VERROUILLE);
//							else
//								res.setCodeRetour(DOCUMENT_EXISTE);
//							//res.setInfo(null);
//							return res;
//						}
//					}
//				}
//			}
//		//}
//		
//		return res;
//	}
//	
//}

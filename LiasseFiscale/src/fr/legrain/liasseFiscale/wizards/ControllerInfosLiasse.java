package fr.legrain.liasseFiscale.wizards;

import java.io.File;
import java.util.Calendar;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import fr.legrain.liasseFiscale.db.ConstLiasse;

public class ControllerInfosLiasse implements IControllerInfos {
	private CompositeInfosLiasse composite;
	private WizardPage lgrWizardPage; //WizardPage /ILgrWizardPage
	private Listener listenerValidate = new Listener() {
		public void handleEvent(Event e) {
			//lgrWizardPage.validatePage();
			lgrWizardPage.setPageComplete(((ILgrWizardPage)lgrWizardPage).validatePage());
		}
	};
	static public final String DOCUMENT_EXISTE = "DOCUMENT_EXISTE";
	static public final String DOCUMENT_VERROUILLE = "DOCUMENT_VERROUILLE";
	
	public ControllerInfosLiasse(CompositeInfosLiasse composite, WizardPage lgrWizardPage) {
		this.composite = composite;
		this.lgrWizardPage = lgrWizardPage;
		
		composite.getSpinAnnee().setSelection(Calendar.getInstance().get(Calendar.YEAR));
		if(((WizardDoc)lgrWizardPage.getWizard()).getModel().getAnneeFiscale()==null)
			composite.getTfAnneeFiscale().setText(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
		
		composite.getSpinAnnee().addListener(SWT.Modify , listenerValidate);
		composite.getSpinAnnee().addListener(SWT.Selection, listenerValidate);
		
		composite.getTfAnneeFiscale().addListener(SWT.Modify , listenerValidate);
		
	}
	
	public CompositeInfosLiasse getComposite() {
		return composite;
	}

	public void setComposite(CompositeInfosLiasse composite) {
		this.composite = composite;
	}
	
	public ValidationResult validateComposite() {
		ValidationResult res = new ValidationResult();
		if(composite.getTfAnneeFiscale().getText()==null || composite.getTfAnneeFiscale().getText().equals("")) {
			res.setError("Veuillez saisir une année fiscale");
			res.setInfo(null);
			return res;
		}
		
		//Nom/Annee de liasse inexistant dans le dossier
		//if(((CompositePageAjoutLiasse)control).getListeDossier().getSelection().length>0) {
			String chemin = ConstLiasse.C_REPERTOIRE_BASE+ConstLiasse.C_REPERTOIRE_PROJET;
			String annee = String.valueOf(composite.getSpinAnnee().getSelection());
			String anneeFiscale = composite.getTfAnneeFiscale().getText();
			String nomDossier = ((WizardDoc)lgrWizardPage.getWizard()).getModel().getNomDossier();
			String cheminDoc = chemin+"/"+nomDossier+"/"+ConstLiasse.C_REP_DOC_TYPE_LIASSE;
			File rep = new File(cheminDoc);
			if(rep.exists()) { //else : 1ere liasse dans ce dossier
				File[] listeDossier = rep.listFiles();
				for (int i = 0; i < listeDossier.length; i++) {
					if(listeDossier[i].isDirectory()) {
						if(listeDossier[i].getName().equals(anneeFiscale)) {
						//if(listeDossier[i].getName().equals(annee)) {
							String message = "La liasse pour l'année fiscale \""+listeDossier[i].getName()+"\" existe déjà dans le dossier "+nomDossier+".";
							res.setError(message);
							res.setInfo(message);
							if(WizardDocumentFiscalModel.estVerrouille(cheminDoc+"/"+anneeFiscale))
								res.setCodeRetour(DOCUMENT_VERROUILLE);
							else
								res.setCodeRetour(DOCUMENT_EXISTE);
							//res.setInfo(null);
							return res;
						}
					}
				}
			}
		//}
		
		return res;
	}
	
}

package fr.legrain.liasseFiscale.wizards;

import java.io.File;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import fr.legrain.liasseFiscale.db.ConstLiasse;

public class ControllerInfosTVA implements IControllerInfos {
	private CompositeInfosTVA composite;
	private WizardPage lgrWizardPage; //WizardPage /ILgrWizardPage
	private Listener listenerValidate = new Listener() {
		public void handleEvent(Event e) {
			//lgrWizardPage.validatePage();
			lgrWizardPage.setPageComplete(((ILgrWizardPage)lgrWizardPage).validatePage());
		}
	};
	
	public ControllerInfosTVA(CompositeInfosTVA composite, WizardPage lgrWizardPage) {
		this.composite = composite;
		this.lgrWizardPage = lgrWizardPage;
		
		composite.getSpinAnnee().addListener(SWT.Modify , listenerValidate);
		composite.getSpinAnnee().addListener(SWT.Selection, listenerValidate);
	}
	
	public CompositeInfosTVA getComposite() {
		return composite;
	}

	public void setComposite(CompositeInfosTVA composite) {
		this.composite = composite;
	}
	
	public ValidationResult validateComposite() {
		ValidationResult res = new ValidationResult();
//		if(composite.getSpinAnnee().getSelection()>1920) {
//			res.setError("message d'erreur");
//			res.setInfo(null);
//			return res;
//		}
		
		//Nom/Annee de liasse inexistant dans le dossier
		//if(((CompositePageAjoutLiasse)control).getListeDossier().getSelection().length>0) {
			String chemin = ConstLiasse.C_REPERTOIRE_BASE+ConstLiasse.C_REPERTOIRE_PROJET;
			String annee = String.valueOf(composite.getSpinAnnee().getSelection());
			String nomDossier = ((WizardDoc)lgrWizardPage.getWizard()).getModel().getNomDossier();
			File rep = new File(chemin+"/"+nomDossier+"/"+ConstLiasse.C_REP_DOC_TYPE_TVA);
			if(rep.exists()) { //else : 1ere liasse dans ce dossier
				File[] listeDossier = rep.listFiles();
				for (int i = 0; i < listeDossier.length; i++) {
					if(listeDossier[i].isDirectory()) {
						if(listeDossier[i].getName().equals(annee)) {
							res.setError("\""+listeDossier[i].getName()+"\" existe déjà.");
							res.setInfo(null);
							return res;
						}
					}
				}
			}
		//}
		
		return res;
	}
	
}

package fr.legrain.generationLabelEtiquette.wizard;

import org.eclipse.jface.wizard.WizardDialog;

public interface IModelWizardPage {

	/**
	 * Validation du contenu de la page
	 * @return vrai ssi les informations contenu dans la page sont valide/suffisante pour passer à la suite
	 */
	public boolean validatePage();
	
	/**
	 * Traitement à effectuer lorsque l'on passe à la page suivante.
	 * finishPage() servir dans la fonction nextPressed() du class WizardModelLettre
	 * Ensuit,nextPressed() est servi la fonction nextPressed() du class WizardDialogModelLettre
	 * parce que , WizardDialogModelLettre extends WizardDialog, peut connaire button next.
	 */
	public void finishPage();
	
	/**
	 * Enregistrement des informations saisies dans la page dans l'objet 
	 * servant à stocker les données de l'ensemble des pages.
	 */
	public void saveToModel();
}

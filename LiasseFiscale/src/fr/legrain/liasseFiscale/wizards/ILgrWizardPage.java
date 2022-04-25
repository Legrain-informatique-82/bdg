package fr.legrain.liasseFiscale.wizards;

public interface ILgrWizardPage {
	
	/**
	 * Validation du contenu de la page
	 * @return vrai ssi les informations contenu dans la page sont valide/suffisante pour passer à la suite
	 */
	public boolean validatePage();
	
	/**
	 * Traitement à effectuer lorsque l'on passe à la page suivante.
	 */
	public void finishPage();
	
	/**
	 * Enregistrement des informations saisies dans la page dans l'objet 
	 * servant à stocker les données de l'ensemble des pages.
	 */
	public void saveToModel();
	
}

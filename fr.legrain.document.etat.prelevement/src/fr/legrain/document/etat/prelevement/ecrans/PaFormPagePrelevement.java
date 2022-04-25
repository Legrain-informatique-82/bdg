/**
 * PaFormPage.java						18/04/11
 * ( dernière revision : 19/04/11 )
 */

package fr.legrain.document.etat.prelevement.ecrans;

import org.eclipse.ui.forms.editor.FormEditor;

import fr.legrain.document.etat.devis.ecrans.PaFormPage;


/**
 * Classe permettant l'affichage du tableau de bord
 * Chaque section et chaque composite sont déclarés de façon autonome
 * @author nicolas
 */

public class PaFormPagePrelevement extends PaFormPage {

	// -- Id et titre du formulaire --
//	public static String id = "fr.legrain.document.etat.prelevement.ecrans.PaFormPage";
//	public static String title = "Echéance";
	
	public PaFormPagePrelevement() {
		super(id, title);
	}

	/**
	 * Create the form page || Constructeur
	 * @param id
	 * @param title
	 */
	public PaFormPagePrelevement(String id, String title) {
		super(id, title);
	}

	/**
	 * Create the form page || Constructeur
	 * @param editor
	 * @param id
	 * @param title
	 */
	public PaFormPagePrelevement(FormEditor editor, String id, String title) {
		super((FormEditor) editor, id, title);
	}
	
	public void createSections() {
		createSectionParam(toolkit,form);
		createSectionDoc(toolkit,form);
		createSectionTableauGauche(toolkit,form);
	}

}

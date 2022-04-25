/**
 * 
 */
package fr.legrain.recherchermulticrit.controllers;

import java.util.ArrayList;
import java.util.List;

import fr.legrain.recherchermulticrit.editors.FormEditorRechercherMultiCrit;
import fr.legrain.tiers.dao.TaTiers;

/**
 * @author nicolas²
 *
 */
public class FormPageMessenger {
	
	private FormPageController rechercheController;
	private FormPageControllerResultats resultatsController;
	private FormEditorRechercherMultiCrit editor;
	
	public FormPageMessenger(FormEditorRechercherMultiCrit editor, FormPageController recherche, FormPageControllerResultats resultats){
		this.editor = editor;
		this.rechercheController = recherche;
		this.resultatsController = resultats;
	}
	
    public void creerFeuilleTiers(ArrayList<Object> liste) {
    	resultatsController.getEtape3Controller().creerOngletTiers(liste);
    }
    public void creerFeuilleArticle(ArrayList<Object> liste) {
    	resultatsController.getEtape3Controller().creerOngletArticle(liste);
    }
    public void creerFeuilleDocument(ArrayList<Object> liste) {
    	resultatsController.getEtape3Controller().creerOngletDocument(liste);
    }
    
    public void changeOngletResultats(){
    	editor.changePage(1);
    }

}

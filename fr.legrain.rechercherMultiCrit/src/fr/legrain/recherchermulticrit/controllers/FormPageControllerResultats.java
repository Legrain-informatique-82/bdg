/**
 * 
 */
package fr.legrain.recherchermulticrit.controllers;

import org.apache.log4j.Logger;

import fr.legrain.recherchermulticrit.ecrans.PaFormPage;
import fr.legrain.recherchermulticrit.ecrans.PaFormPageResultats;

/**
 * @author nicolas²
 *
 */
public class FormPageControllerResultats  {
	
	// Logger
	static Logger logger = Logger.getLogger(FormPageControllerResultats.class.getName());
	
	// Vue
	private PaFormPageResultats vue = null;
	
	// Controllers
	private Etape3Controller etape3Controller = null;

	private FormPageMessenger messengerPage;

	/**
	 * @param resultatsFormPage
	 */
	public FormPageControllerResultats(PaFormPageResultats vue) {
		this.vue = vue;
		etape3Controller = new Etape3Controller(this,vue,null);
	}
	

	
	private void initialisationModel() {
		try {
			etape3Controller.initialiseModelIHM();


		} catch(Exception e) {
			logger.error("", e);
		} finally {
			vue.reflow();
		}
	}
	
	

	public void appel(){
		initialisationModel();
	}
	
	/**
	 * Méthode permettant d'affecter un "messenger" au controller
	 * Le rôle du messenger est de faire transiter les informations entre les
	 * différents onglets
	 * @param leMessenger
	 */
	public void setMessengerPage(FormPageMessenger leMessenger) {
		this.messengerPage = leMessenger;
		
	}



	public Etape3Controller getEtape3Controller() {
		return etape3Controller;
	}



	public PaFormPageResultats getVue() {
		return vue;
	}

}

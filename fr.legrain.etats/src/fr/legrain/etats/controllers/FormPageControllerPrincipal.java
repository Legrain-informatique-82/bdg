package fr.legrain.etats.controllers;

import java.util.Date;

import org.apache.log4j.Logger;

import fr.legrain.etats.ecrans.PaFormPage;


public class FormPageControllerPrincipal  {

	static Logger logger = Logger.getLogger(FormPageControllerPrincipal.class.getName());


	// On récupère uniquement l'entity manager de TaArticleDAO
	//private EntityManager masterDAO = new TaArticleDAO().getEntityManager();

	// Vue
	protected PaFormPage vue = null;

	// Controllers
	protected EtatControllerMini paramControllerMini = null;
	protected Date datedeb;
	protected Date datefin;

	public void refreshAll() {
		initialisationModel(false);
	}
	
	public void appel(){
		initialisationModel(true);
	}

	private void initialisationModel() {
		initialisationModel(true);
	}
	
	public FormPageControllerPrincipal() {
		super();
	}


	public FormPageControllerPrincipal(PaFormPage vue) {
		initVue(vue);
	}
	
	public void initVue(PaFormPage vue) {
		this.vue = vue;
		paramControllerMini = new EtatControllerMini(this,vue, null);
	}

	private void initialisationModel(boolean tout) {
		try {

			if(tout) {
				// Initialisation des paramètres
				paramControllerMini.initialiseModelIHM();
			}
			
		} catch(Exception e) {
			logger.error("", e);
		} finally {
			vue.reflow();
		}
	}

}

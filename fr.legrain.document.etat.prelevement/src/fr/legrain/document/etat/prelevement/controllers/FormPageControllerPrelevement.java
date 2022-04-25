package fr.legrain.document.etat.prelevement.controllers;

import java.util.Date;

import org.apache.log4j.Logger;

import fr.legrain.document.etat.devis.controllers.FormPageControllerPrincipal;
import fr.legrain.document.etat.devis.ecrans.PaFormPage;
import fr.legrain.document.etat.prelevement.ecrans.PaFormPagePrelevement;


public class FormPageControllerPrelevement extends FormPageControllerPrincipal {

	static Logger logger = Logger.getLogger(FormPageControllerPrelevement.class.getName());

	// Vue
	//protected PaFormPagePrelevement vue = null;

	// Controllers
	protected ParamControllerMini paramControllerMiniPrelevement = null;
	protected TotauxControllerPrincipal totauxControllerPrincipalPrelevement = null;
	protected DocEcheanceController docEcheanceControllerPrelevement = null;

	public void refreshAll() {
		initialisationModel(false);
	}
	
	public void init(){
		initialisationModel(true);
	}

	private void initialisationModel() {
		initialisationModel(true);
	}
	
	public FormPageControllerPrelevement() {
		super();
	}


	public FormPageControllerPrelevement(PaFormPagePrelevement vue) {
		super(vue);
		initVue(vue);
	}
	
	public void initVue(PaFormPage vue) {
		super.initVue(vue);
		//this.vue = vue;
		paramControllerMiniPrelevement = new ParamControllerMini(this,vue, null);
		totauxControllerPrincipalPrelevement = new TotauxControllerPrincipal(this,vue,null);
		docEcheanceControllerPrelevement = new DocEcheanceController(this,vue,null);
	}

	private void initialisationModel(boolean tout) {
		try {
			//if(tout) {
				// Initialisation des paramètres
				paramControllerMiniPrelevement.initialiseModelIHM();
			//}

			docEcheanceControllerPrelevement.initialiseModelIHM(0); 
			docEcheanceControllerPrelevement.bind();
			
			totauxControllerPrincipalPrelevement.initialiseModelIHM();
			totauxControllerPrincipalPrelevement.bind();
		} catch(Exception e) {
			logger.error("", e);
		} finally {
			vue.reflow();
		}
	}

	public ParamControllerMini getParamControllerMiniPrelevement() {
		return paramControllerMiniPrelevement;
	}

	public TotauxControllerPrincipal getTotauxControllerPrincipalPrelevement() {
		return totauxControllerPrincipalPrelevement;
	}

	public DocEcheanceController getDocEcheanceControllerPrelevement() {
		return docEcheanceControllerPrelevement;
	}

}

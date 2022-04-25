package fr.legrain.document.etat.proforma.controllers;

import org.apache.log4j.Logger;

import fr.legrain.document.etat.devis.controllers.FormPageControllerPrincipal;
import fr.legrain.document.etat.devis.ecrans.PaFormPage;
import fr.legrain.document.etat.proforma.ecrans.PaFormPageProforma;


public class FormPageControllerProforma extends FormPageControllerPrincipal {

	static Logger logger = Logger.getLogger(FormPageControllerProforma.class.getName());

	// Vue
	//protected PaFormPagePrelevement vue = null;

	// Controllers
	protected ParamControllerMini paramControllerMiniProforma = null;
	protected TotauxControllerPrincipal totauxControllerPrincipalProforma = null;
	protected DocEcheanceController docEcheanceControllerProforma = null;

	public void refreshAll() {
		initialisationModel(false);
	}
	
	public void init(){
		initialisationModel(true);
	}

	private void initialisationModel() {
		initialisationModel(true);
	}
	
	public FormPageControllerProforma() {
		super();
	}


	public FormPageControllerProforma(PaFormPageProforma vue) {
		super(vue);
		initVue(vue);
	}
	
	public void initVue(PaFormPage vue) {
		super.initVue(vue);
		//this.vue = vue;
		paramControllerMiniProforma = new ParamControllerMini(this,vue, null);
		totauxControllerPrincipalProforma = new TotauxControllerPrincipal(this,vue,null);
		docEcheanceControllerProforma = new DocEcheanceController(this,vue,null);
	}

	private void initialisationModel(boolean tout) {
		try {
			//if(tout) {
				// Initialisation des paramètres
				paramControllerMiniProforma.initialiseModelIHM();
			//}

			docEcheanceControllerProforma.initialiseModelIHM(0); 
			docEcheanceControllerProforma.bind();
			
			totauxControllerPrincipalProforma.initialiseModelIHM();
			totauxControllerPrincipalProforma.bind();
		} catch(Exception e) {
			logger.error("", e);
		} finally {
			vue.reflow();
		}
	}

	public ParamControllerMini getParamControllerMiniProforma() {
		return paramControllerMiniProforma;
	}

	public TotauxControllerPrincipal getTotauxControllerPrincipalProforma() {
		return totauxControllerPrincipalProforma;
	}

	public DocEcheanceController getDocEcheanceControllerProforma() {
		return docEcheanceControllerProforma;
	}
	
	

}

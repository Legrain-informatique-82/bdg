package fr.legrain.document.etat.boncde.controllers;

import org.apache.log4j.Logger;

import fr.legrain.document.etat.boncde.ecrans.PaFormPageBoncde;
import fr.legrain.document.etat.devis.controllers.FormPageControllerPrincipal;
import fr.legrain.document.etat.devis.ecrans.PaFormPage;


public class FormPageControllerBoncde extends FormPageControllerPrincipal {

	static Logger logger = Logger.getLogger(FormPageControllerBoncde.class.getName());

	// Vue
	//protected PaFormPagePrelevement vue = null;

	// Controllers
	protected ParamControllerMini paramControllerMiniBoncde = null;
	protected TotauxControllerPrincipal totauxControllerPrincipalBoncde = null;
	protected DocEcheanceController docEcheanceControllerBoncde = null;

	public void refreshAll() {
		initialisationModel(false);
	}
	
	public void init(){
		initialisationModel(true);
	}

	private void initialisationModel() {
		initialisationModel(true);
	}
	
	public FormPageControllerBoncde() {
		super();
	}


	public FormPageControllerBoncde(PaFormPageBoncde vue) {
		super(vue);
		initVue(vue);
	}
	
	public void initVue(PaFormPage vue) {
		super.initVue(vue);
		//this.vue = vue;
		paramControllerMiniBoncde = new ParamControllerMini(this,vue, null);
		totauxControllerPrincipalBoncde = new TotauxControllerPrincipal(this,vue,null);
		docEcheanceControllerBoncde = new DocEcheanceController(this,vue,null);
	}

	private void initialisationModel(boolean tout) {
		try {
			//if(tout) {
				// Initialisation des paramètres
				paramControllerMiniBoncde.initialiseModelIHM();
			//}

			docEcheanceControllerBoncde.initialiseModelIHM(0); 
			docEcheanceControllerBoncde.bind();
			
			totauxControllerPrincipalBoncde.initialiseModelIHM();
			totauxControllerPrincipalBoncde.bind();
		} catch(Exception e) {
			logger.error("", e);
		} finally {
			vue.reflow();
		}
	}

	public ParamControllerMini getParamControllerMiniBoncde() {
		return paramControllerMiniBoncde;
	}

	public TotauxControllerPrincipal getTotauxControllerPrincipalBoncde() {
		return totauxControllerPrincipalBoncde;
	}

	public DocEcheanceController getDocEcheanceControllerBoncde() {
		return docEcheanceControllerBoncde;
	}

}

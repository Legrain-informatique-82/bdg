package fr.legrain.statistiques.avoir.controllers;

import org.apache.log4j.Logger;

import fr.legrain.statistiques.controllers.FormPageControllerPrincipal;
import fr.legrain.statistiques.controllers.ParamControllerMini;
import fr.legrain.statistiques.ecrans.PaFormPage;


public class FormPageControllerAvoir extends FormPageControllerPrincipal {
	
	static Logger logger = Logger.getLogger(FormPageControllerAvoir.class.getName());	

	private MontantControllerAvoir avoirMontantController;
//	private DocumentsControllerAvoir avoirDocumentsController;
//	private JaugeControllerAvoir avoirJaugeController;
	private GraphControllerAvoir avoirGraphController;
	private RepartitionControllerAvoir avoirRepartitionController;
	private TabControllerClientsAvoir avoirTabControllerTiers;
	private TabControllerArticlesAvoir avoirTabControllerArticles;

	public FormPageControllerAvoir() {
		super();
	}

	public FormPageControllerAvoir(PaFormPage vue) {
		super(vue);
		initVue(vue);
	}
	
	public void initVue(PaFormPage vue) {
		super.initVue(vue);
		paramControllerMini = new ParamControllerMini(this,vue, null);
		avoirMontantController = new MontantControllerAvoir(this,vue,null);
//		avoirDocumentsController = new DocumentsControllerAvoir(this,vue,null);
//		avoirJaugeController = new JaugeControllerAvoir(this,vue,null);
		avoirGraphController = new GraphControllerAvoir(this,vue,null);
		avoirRepartitionController = new RepartitionControllerAvoir(this,vue,null);
		avoirTabControllerTiers = new TabControllerClientsAvoir(this,vue,null);
		avoirTabControllerArticles  = new TabControllerArticlesAvoir(this,vue,null);
	}
	
	@Override
	public void appel(){
		initialisationModel(true);
	}
	
	@Override
	public void refreshAll() {
		initialisationModel(false);
	}

	private void initialisationModel(boolean tout) {
		try {
			// if(masterDAO!=null && masterEntity!=null) {

			if(tout) {
				// Initialisation des paramètres
				paramControllerMini.initialiseModelIHM();
			}
			// Initialisation du montant avoir
			avoirMontantController.initialiseModelIHM();
			avoirMontantController.bind();
//			
//			// Initialisation des transformations des avoir
//			avoirDocumentsController.initialiseModelIHM();
//			avoirDocumentsController.bind();
//			
//			// Initialisation de la jauge des avoir non transfo
//			avoirJaugeController.initialiseModelIHM();
//			avoirJaugeController.bind();
//
			// Initialisation du graphique
			avoirGraphController.initialiseModelIHM();
			avoirGraphController.bind();
			
			// Initialisation de la carte des répartitions
			avoirRepartitionController.initialiseModelIHM();
			avoirRepartitionController.bind();
			
			// Initialisation des tableaux
			avoirTabControllerTiers.initialiseModelIHM(10);
			avoirTabControllerTiers.bind();
			avoirTabControllerArticles.initialiseModelIHM(10);
			avoirTabControllerArticles.bind();

		} catch(Exception e) {
			logger.error("", e);
		} finally {
			vue.reflow();
		}
	}


}

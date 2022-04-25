package fr.legrain.statistiques.commande.controllers;

import org.apache.log4j.Logger;

import fr.legrain.statistiques.controllers.FormPageControllerPrincipal;
import fr.legrain.statistiques.controllers.ParamControllerMini;
import fr.legrain.statistiques.ecrans.PaFormPage;


public class FormPageControllerCommandes extends FormPageControllerPrincipal {
	
	static Logger logger = Logger.getLogger(FormPageControllerCommandes.class.getName());	

	private MontantControllerCommandes commandesMontantController;
	private DocumentsControllerCommandes commandesDocumentsController;
	private JaugeControllerCommandes commandesJaugeController;
	private GraphControllerCommandes commandesGraphController;
	private RepartitionControllerCommandes commandesRepartitionController;
	private TabControllerCommandes  commandesTabController;

	public FormPageControllerCommandes() {
		super();
	}

	public FormPageControllerCommandes(PaFormPage vue) {
		super(vue);
		initVue(vue);
	}
	
	public void initVue(PaFormPage vue) {
		super.initVue(vue);
		paramControllerMini = new ParamControllerMini(this,vue, null);
		commandesMontantController = new MontantControllerCommandes(this,vue,null);
		commandesDocumentsController = new DocumentsControllerCommandes(this,vue,null);
		commandesJaugeController = new JaugeControllerCommandes(this,vue,null);
		commandesGraphController = new GraphControllerCommandes(this,vue,null);
		commandesRepartitionController = new RepartitionControllerCommandes(this,vue,null);
		commandesTabController = new TabControllerCommandes (this,vue,null);
		
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
			// Initialisation du montant commandes
			commandesMontantController.initialiseModelIHM();
			commandesMontantController.bind();
			
			// Initialisation des transformations des commandes
			commandesDocumentsController.initialiseModelIHM();
			commandesDocumentsController.bind();
			
			// Initialisation de la jauge des commandes non transfo
			commandesJaugeController.initialiseModelIHM();
			commandesJaugeController.bind();

			// Initialisation du graphique
			commandesGraphController.initialiseModelIHM();
			commandesGraphController.bind();
			
			// Initialisation de la carte des répartitions
			commandesRepartitionController.initialiseModelIHM();
			commandesRepartitionController.bind();
			
			// Initialisation des tableaux
			commandesTabController.initialiseModelIHM();
			commandesTabController.bind();

		} catch(Exception e) {
			logger.error("", e);
		} finally {
			vue.reflow();
		}
	}


}

package fr.legrain.statistiques.controllers.a_supprimer;

import org.apache.log4j.Logger;

import fr.legrain.statistiques.controllers.FormPageControllerPrincipal;
import fr.legrain.statistiques.controllers.ParamControllerMini;
import fr.legrain.statistiques.ecrans.PaFormPage;


public class FormPageControllerLivraisons extends FormPageControllerPrincipal {
	
	static Logger logger = Logger.getLogger(FormPageControllerLivraisons.class.getName());

	private MontantControllerLivraisons livraisonsMontantController;
	private DocumentsControllerLivraisons livraisonsDocumentsController;
	private JaugeControllerLivraisons livraisonsJaugeController;
	private GraphControllerLivraisons livraisonsGraphController;
	private RepartitionControllerLivraisons livraisonsRepartitionController;
	private TabControllerLivraisons  livraisonsTabController;


	public FormPageControllerLivraisons(PaFormPage vue) {
		super(vue);
		paramControllerMini = new ParamControllerMini(this,vue, null);
		livraisonsMontantController = new MontantControllerLivraisons(this,vue,null);
		livraisonsDocumentsController = new DocumentsControllerLivraisons(this,vue,null);
		livraisonsJaugeController = new JaugeControllerLivraisons(this,vue,null);
		livraisonsGraphController = new GraphControllerLivraisons(this,vue,null);
		livraisonsRepartitionController = new RepartitionControllerLivraisons(this,vue,null);
		livraisonsTabController = new TabControllerLivraisons (this,vue,null);
		
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
			// Initialisation du montant livraisons
			livraisonsMontantController.initialiseModelIHM();
			livraisonsMontantController.bind();
			
			// Initialisation des transformations des livraisons
			livraisonsDocumentsController.initialiseModelIHM();
			livraisonsDocumentsController.bind();
			
			// Initialisation de la jauge des livraisons non transfo
			livraisonsJaugeController.initialiseModelIHM();
			livraisonsJaugeController.bind();

			// Initialisation du graphique
			livraisonsGraphController.initialiseModelIHM();
			livraisonsGraphController.bind();
			
			// Initialisation de la carte des répartitions
			livraisonsRepartitionController.initialiseModelIHM();
			livraisonsRepartitionController.bind();
			
			// Initialisation des tableaux
			livraisonsTabController.initialiseModelIHM();
			livraisonsTabController.bind();

		} catch(Exception e) {
			logger.error("", e);
		} finally {
			vue.reflow();
		}
	}


}

package fr.legrain.statistiques.controllers.a_supprimer;

import org.apache.log4j.Logger;

import fr.legrain.statistiques.controllers.FormPageControllerPrincipal;
import fr.legrain.statistiques.controllers.ParamControllerMini;
import fr.legrain.statistiques.ecrans.PaFormPage;


public class FormPageControllerDevis extends FormPageControllerPrincipal {
	
	static Logger logger = Logger.getLogger(FormPageControllerDevis.class.getName());

	private MontantControllerDevis devisMontantController;
	private DocumentsControllerDevis devisDocumentsController;
	private JaugeControllerDevis devisJaugeController;
	private GraphControllerDevis devisGraphController;
	private RepartitionControllerDevis devisRepartitionController;
	private TabControllerDevis devisTabController;


	public FormPageControllerDevis(PaFormPage vue) {
		super(vue);
		paramControllerMini = new ParamControllerMini(this,vue, null);
		devisMontantController = new MontantControllerDevis(this,vue,null);
		devisDocumentsController = new DocumentsControllerDevis(this,vue,null);
		devisJaugeController = new JaugeControllerDevis(this,vue,null);
		devisGraphController = new GraphControllerDevis(this,vue,null);
		devisRepartitionController = new RepartitionControllerDevis(this,vue,null);
		devisTabController = new TabControllerDevis (this,vue,null);
		
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
			// Initialisation du montant devis
			devisMontantController.initialiseModelIHM();
			devisMontantController.bind();
			
			// Initialisation des transformations des devis
			devisDocumentsController.initialiseModelIHM();
			devisDocumentsController.bind();
			
			// Initialisation de la jauge des devis non transfo
			devisJaugeController.initialiseModelIHM();
			devisJaugeController.bind();

			// Initialisation du graphique
			devisGraphController.initialiseModelIHM();
			devisGraphController.bind();
			
			// Initialisation de la carte des répartitions
			devisRepartitionController.initialiseModelIHM();
			devisRepartitionController.bind();
			
			// Initialisation des tableaux
			devisTabController.initialiseModelIHM();
			devisTabController.bind();

		} catch(Exception e) {
			logger.error("", e);
		} finally {
			vue.reflow();
		}
	}


}

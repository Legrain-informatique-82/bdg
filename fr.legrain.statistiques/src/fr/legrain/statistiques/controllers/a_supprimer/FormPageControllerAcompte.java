package fr.legrain.statistiques.controllers.a_supprimer;

import org.apache.log4j.Logger;

import fr.legrain.statistiques.controllers.FormPageControllerPrincipal;
import fr.legrain.statistiques.controllers.ParamControllerMini;
import fr.legrain.statistiques.ecrans.PaFormPage;


public class FormPageControllerAcompte extends FormPageControllerPrincipal {
	
	static Logger logger = Logger.getLogger(FormPageControllerAcompte.class.getName());

	private MontantControllerAcompte acompteMontantController;
//	private DocumentsControllerAcompte acompteDocumentsController;
//	private JaugeControllerAcompte acompteJaugeController;
	private GraphControllerAcompte acompteGraphController;
	private RepartitionControllerAcompte acompteRepartitionController;
	private TabControllerClientsAcompte acompteTabControllerTiers;
	private TabControllerArticlesAcompte acompteTabControllerArticles;


	public FormPageControllerAcompte(PaFormPage vue) {
		super(vue);
		paramControllerMini = new ParamControllerMini(this,vue, null);
		acompteMontantController = new MontantControllerAcompte(this,vue,null);
//		acompteDocumentsController = new DocumentsControllerAcompte(this,vue,null);
//		acompteJaugeController = new JaugeControllerAcompte(this,vue,null);
		acompteGraphController = new GraphControllerAcompte(this,vue,null);
		acompteRepartitionController = new RepartitionControllerAcompte(this,vue,null);
		acompteTabControllerTiers = new TabControllerClientsAcompte(this,vue,null);
		acompteTabControllerArticles  = new TabControllerArticlesAcompte(this,vue,null);
		
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
			// Initialisation du montant acompte
			acompteMontantController.initialiseModelIHM();
			acompteMontantController.bind();
//			
//			// Initialisation des transformations des acompte
//			acompteDocumentsController.initialiseModelIHM();
//			acompteDocumentsController.bind();
//			
//			// Initialisation de la jauge des acompte non transfo
//			acompteJaugeController.initialiseModelIHM();
//			acompteJaugeController.bind();
//
			// Initialisation du graphique
			acompteGraphController.initialiseModelIHM();
			acompteGraphController.bind();
			
			// Initialisation de la carte des répartitions
			acompteRepartitionController.initialiseModelIHM();
			acompteRepartitionController.bind();
			
			// Initialisation des tableaux
			acompteTabControllerTiers.initialiseModelIHM(10);
			acompteTabControllerTiers.bind();
			acompteTabControllerArticles.initialiseModelIHM(10);
			acompteTabControllerArticles.bind();

		} catch(Exception e) {
			logger.error("", e);
		} finally {
			vue.reflow();
		}
	}


}

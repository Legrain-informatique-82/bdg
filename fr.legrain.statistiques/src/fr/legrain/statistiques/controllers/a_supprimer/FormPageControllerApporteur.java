package fr.legrain.statistiques.controllers.a_supprimer;

import org.apache.log4j.Logger;

import fr.legrain.statistiques.controllers.FormPageControllerPrincipal;
import fr.legrain.statistiques.controllers.ParamControllerMini;
import fr.legrain.statistiques.ecrans.PaFormPage;


public class FormPageControllerApporteur extends FormPageControllerPrincipal {
	
	static Logger logger = Logger.getLogger(FormPageControllerApporteur.class.getName());

	private MontantControllerApporteur apporteurMontantController;
//	private DocumentsControllerApporteur apporteurDocumentsController;
//	private JaugeControllerApporteur apporteurJaugeController;
	private GraphControllerApporteur apporteurGraphController;
	private RepartitionControllerApporteur apporteurRepartitionController;
	private TabControllerClientsApporteur apporteurTabControllerTiers;
	private TabControllerArticlesApporteur apporteurTabControllerArticles;


	public FormPageControllerApporteur(PaFormPage vue) {
		super(vue);
		paramControllerMini = new ParamControllerMini(this,vue, null);
		apporteurMontantController = new MontantControllerApporteur(this,vue,null);
//		apporteurDocumentsController = new DocumentsControllerApporteur(this,vue,null);
//		apporteurJaugeController = new JaugeControllerApporteur(this,vue,null);
		apporteurGraphController = new GraphControllerApporteur(this,vue,null);
		apporteurRepartitionController = new RepartitionControllerApporteur(this,vue,null);
		apporteurTabControllerTiers = new TabControllerClientsApporteur(this,vue,null);
		apporteurTabControllerArticles  = new TabControllerArticlesApporteur(this,vue,null);
		
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
			// Initialisation du montant apporteur
			apporteurMontantController.initialiseModelIHM();
			apporteurMontantController.bind();
//			
//			// Initialisation des transformations des apporteur
//			apporteurDocumentsController.initialiseModelIHM();
//			apporteurDocumentsController.bind();
//			
//			// Initialisation de la jauge des apporteur non transfo
//			apporteurJaugeController.initialiseModelIHM();
//			apporteurJaugeController.bind();
//
			// Initialisation du graphique
			apporteurGraphController.initialiseModelIHM();
			apporteurGraphController.bind();
			
			// Initialisation de la carte des répartitions
			apporteurRepartitionController.initialiseModelIHM();
			apporteurRepartitionController.bind();
			
			// Initialisation des tableaux
			apporteurTabControllerTiers.initialiseModelIHM(10);
			apporteurTabControllerTiers.bind();
			apporteurTabControllerArticles.initialiseModelIHM(10);
			apporteurTabControllerArticles.bind();

		} catch(Exception e) {
			logger.error("", e);
		} finally {
			vue.reflow();
		}
	}


}

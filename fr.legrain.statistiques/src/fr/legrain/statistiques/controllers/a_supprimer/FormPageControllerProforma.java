package fr.legrain.statistiques.controllers.a_supprimer;

import org.apache.log4j.Logger;

import fr.legrain.statistiques.controllers.FormPageControllerPrincipal;
import fr.legrain.statistiques.controllers.MontantControllerProforma;
import fr.legrain.statistiques.controllers.ParamControllerMini;
import fr.legrain.statistiques.ecrans.PaFormPage;


public class FormPageControllerProforma extends FormPageControllerPrincipal {
	
	static Logger logger = Logger.getLogger(FormPageControllerProforma.class.getName());

	private MontantControllerProforma proformaMontantController;
	private DocumentsControllerProforma proformaDocumentsController;
	private JaugeControllerProforma proformaJaugeController;
	private GraphControllerProforma proformaGraphController;
	private RepartitionControllerProforma proformaRepartitionController;
	private TabControllerProforma proformaTabController;


	public FormPageControllerProforma(PaFormPage vue) {
		super(vue);
		paramControllerMini = new ParamControllerMini(this,vue, null);
		proformaMontantController = new MontantControllerProforma(this,vue,null);
		proformaDocumentsController = new DocumentsControllerProforma(this,vue,null);
		proformaJaugeController = new JaugeControllerProforma(this,vue,null);
		proformaGraphController = new GraphControllerProforma(this,vue,null);
		proformaRepartitionController = new RepartitionControllerProforma(this,vue,null);
		proformaTabController = new TabControllerProforma (this,vue,null);
		
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
			// Initialisation du montant proforma
			proformaMontantController.initialiseModelIHM();
			proformaMontantController.bind();
			
			// Initialisation des transformations des proforma
			proformaDocumentsController.initialiseModelIHM();
			proformaDocumentsController.bind();
			
			// Initialisation de la jauge des proforma non transfo
			proformaJaugeController.initialiseModelIHM();
			proformaJaugeController.bind();

			// Initialisation du graphique
			proformaGraphController.initialiseModelIHM();
			proformaGraphController.bind();
			
			// Initialisation de la carte des répartitions
			proformaRepartitionController.initialiseModelIHM();
			proformaRepartitionController.bind();
			
			// Initialisation des tableaux
			proformaTabController.initialiseModelIHM();
			proformaTabController.bind();

		} catch(Exception e) {
			logger.error("", e);
		} finally {
			vue.reflow();
		}
	}


}

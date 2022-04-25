package fr.legrain.licence.controllers;

import java.util.Date;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.licence.ecrans.PaFormPage;


public class FormPageControllerPrincipal  {

	static Logger logger = Logger.getLogger(FormPageControllerPrincipal.class.getName());

	// On récupère uniquement l'entity manager de TaArticleDAO
	private EntityManager masterDAO = new TaArticleDAO().getEntityManager();

	// Vue
	protected PaFormPage vue = null;

	// Controllers
	protected ParamControllerMini paramControllerMini = null;
	//private TotauxControllerPrincipal totauxControllerPrincipal = null;
	private LigneFactureController docEcheanceController = null;

	protected Date datedeb;
	protected Date datefin;

	public void refreshAll() {
		initialisationModel(true);
	}
	
	public void init(){
		initialisationModel(false);
	}


	
	public FormPageControllerPrincipal() {
		super();
	}


	public FormPageControllerPrincipal(PaFormPage vue) {
		initVue(vue);
	}
	
	public void initVue(PaFormPage vue) {
		this.vue = vue;
		paramControllerMini = new ParamControllerMini(this,vue, null);
		//totauxControllerPrincipal = new TotauxControllerPrincipal(this,vue,null);
		docEcheanceController = new LigneFactureController(this,vue,null);
	}

	public void raz(boolean tout) {
		try {
			docEcheanceController.modelLDocument.clear(); 
			docEcheanceController.bind();			
		} catch(Exception e) {
			logger.error("", e);
		} finally {
			vue.reflow();
		}
	}
	private void initialisationModel(boolean tout) {
		try {
			// if(masterDAO!=null && masterEntity!=null) {

			//if(tout) {
				// Initialisation des paramètres
				paramControllerMini.initialiseModelIHM();
			//}

				if(tout){
					docEcheanceController.initialiseModelIHM(0);
				}
			docEcheanceController.bind();
			
//			totauxControllerPrincipal.initialiseModelIHM();
//			totauxControllerPrincipal.bind();

			
			//  @busy : Mise en attente du Form (icone chargement) pendant le traitement des données de factures
			//vue.getForm().setBusy(true);
			
//			Thread t = new Thread(){
////			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
//				public void run() {
//					// Initialisation des factures
//					facturesController.initialiseModelIHM();
//					facturesController.bind(); // @busy : La fin du chargement se fait dans le bind
//				}
////			});
//			};
//			t.start();
			
		} catch(Exception e) {
			logger.error("", e);
		} finally {
			vue.reflow();
		}
	}


	public EntityManager getMasterDAOEM() {
		return masterDAO;
	}


	public void setMasterDAOEM(EntityManager masterDAO) {
		this.masterDAO = masterDAO;
	}

	public LigneFactureController getDocEcheanceController() {
		return docEcheanceController;
	}

}

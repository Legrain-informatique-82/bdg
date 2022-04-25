package fr.legrain.pointsbonus.controller;

import java.util.Date;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.pointsbonus.ecran.PaFormPageEcheance;


public class FormPageControllerPrincipalEcheance  {

	static Logger logger = Logger.getLogger(FormPageControllerPrincipalEcheance.class.getName());

	// On récupère uniquement l'entity manager de TaArticleDAO
	private EntityManager masterDAO = new TaArticleDAO().getEntityManager();

	// Vue
	protected PaFormPageEcheance vue = null;

	// Controllers
	protected ParamControllerMiniEcheance paramControllerMini = null;
	private LigneEcheanceController docEcheanceController = null;

	protected Date datedeb;
	protected Date datefin;

	public void refreshAll() {
		initialisationModel(false);
	}
	
	public void init(){
		initialisationModel(true);
	}

	private void initialisationModel() {
		initialisationModel(true);
	}
	
	public FormPageControllerPrincipalEcheance() {
		super();
	}


	public FormPageControllerPrincipalEcheance(PaFormPageEcheance vue) {
		initVue(vue);
	}
	
	public void initVue(PaFormPageEcheance vue) {
		this.vue = vue;
		paramControllerMini = new ParamControllerMiniEcheance(this,vue, null);
		docEcheanceController = new LigneEcheanceController(this,vue,null);
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
				
			docEcheanceController.initialiseModelIHM(0); 
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

	public LigneEcheanceController getDocEcheanceController() {
		return docEcheanceController;
	}

}

package fr.legrain.reglement.reglementEnListe;

import java.util.Date;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.reglement.ecran.PaFormPageReglement;


public class FormPageControllerPrincipalReglement  {

	static Logger logger = Logger.getLogger(FormPageControllerPrincipalReglement.class.getName());

	// On récupère uniquement l'entity manager de TaArticleDAO
	private EntityManager masterDAO = new TaArticleDAO().getEntityManager();
	private Boolean suppressionUniquement=false;
	// Vue
	protected PaFormPageReglement vue = null;

	// Controllers
	protected ParamControllerMiniReglement paramControllerMini = null;
	private LigneReglementController docEcheanceController = null;

	protected Date datedeb;
	protected Date datefin;

	public void refreshAll() {
		initialisationModel(true);
	}
	
	public void init(){
		initialisationModel(false);
	}


	
	public FormPageControllerPrincipalReglement() {
		super();
	}


	public FormPageControllerPrincipalReglement(PaFormPageReglement vue) {
		initVue(vue);
	}
	
	public void initVue(PaFormPageReglement vue) {
		this.vue = vue;
		paramControllerMini = new ParamControllerMiniReglement(this,vue, null);
		docEcheanceController = new LigneReglementController(this,vue,null);
	}

	public void raz(boolean tout) {
		try {
			docEcheanceController.modelLDocument.clear(); 
//			docEcheanceController.initialiseModelIHMNouveau(0);	
			docEcheanceController.bind();
		} catch(Exception e) {
			logger.error("", e);
		} finally {
			vue.reflow();
		}
	}
	private void initialisationModel(boolean tout) {
		try {

			paramControllerMini.initialiseModelIHM();
				
			if(tout){
				docEcheanceController.initialiseModelIHM(0);
			}
			docEcheanceController.bind();
			
			
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

	public LigneReglementController getDocEcheanceController() {
		return docEcheanceController;
	}

	public Boolean getSuppressionUniquement() {
		return suppressionUniquement;
	}

	public void setSuppressionUniquement(Boolean suppressionUniquement) {
		this.suppressionUniquement = suppressionUniquement;
		vue.getControllerPage().getDocEcheanceController().setSuppressionUniquement(suppressionUniquement);
	}

}

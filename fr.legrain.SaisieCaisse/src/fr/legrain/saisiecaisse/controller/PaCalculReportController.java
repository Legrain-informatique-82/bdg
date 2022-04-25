package fr.legrain.saisiecaisse.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.documents.dao.TaTPaiement;
import fr.legrain.documents.dao.TaTPaiementDAO;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LibDateTime;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.LibDate;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.saisieCaisse.dao.TaAchatTTC;
import fr.legrain.saisieCaisse.dao.TaCriteresSaisieCaisse;
import fr.legrain.saisieCaisse.dao.TaEtablissement;
import fr.legrain.saisieCaisse.dao.TaReportTPaiement;
import fr.legrain.saisieCaisse.dao.TaReportTPaiementDAO;
import fr.legrain.saisieCaisse.dao.TaSumDepot;
import fr.legrain.saisieCaisse.dao.TaSumOperation;
import fr.legrain.saisiecaisse.divers.EtatSaisieCaisse;
import fr.legrain.saisiecaisse.ecran.PaCalculReportSWT;
import fr.legrain.stocks.dao.TaEtatStock;
import fr.legrain.stocks.dao.TaReportStock;
import fr.legrain.stocks.dao.TaReportStockDAO;


public class PaCalculReportController extends JPABaseControllerSWTStandard implements
RetourEcranListener {
	
	static Logger logger = Logger.getLogger(PaCalculReportController.class.getName());		
	private PaCalculReportSWT vue = null; // vue
	private TaReportTPaiementDAO dao = null;//new TaReportTPaiementDAO();
	private Object ecranAppelant = null;
	private TaEtablissement masterEntity = null;
	
	public PaCalculReportController(PaCalculReportSWT vue) {
		this(vue,null);
	}
	
	public PaCalculReportController(PaCalculReportSWT vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaReportTPaiementDAO(getEm());
		try {
			setVue(vue);

			this.vue=vue;
			vue.getShell().addShellListener(this);

//			Branchement action annuler : empeche la fermeture automatique de la
			// fenetre sur ESC
			vue.getShell().addTraverseListener(new Traverse());
			actionImprimer.setText("Calcul[F3]");
			LibDateTime.setDate(vue.getTfDATEDEB(),new Date("01/01/2000"));
			TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
			TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
			//On récupère la dernière date saisie dans la table des reports de stocks
			LibDateTime.setDate(vue.getTfDATEFIN(),recupDerniereDateCalcul());
			//si cette date est nulle, alors on prend la date fin exercice
			if(LibDateTime.getDate(vue.getTfDATEFIN())==null){
				if(taInfoEntreprise.getDatefinInfoEntreprise()!=null)
					LibDateTime.setDate(vue.getTfDATEFIN(),LibDate.incrementDate(taInfoEntreprise.getDatedebInfoEntreprise(), -1, 0, 0) );
				else LibDateTime.setDate(vue.getTfDATEFIN(),new Date());
			}
			initController();
			initEtatBoutonCommand();
		} catch (Exception e) {
			logger.error("Erreur ", e);
		}

	}
	private Date recupDerniereDateCalcul(){
		return dao.recupDerniereDateReportSaisieCaisse();	
	}
	
	
	private class LgrModifyListener implements ModifyListener{

		public void modifyText(ModifyEvent e) {
			modif(e);
		}
	}
	
	private void modif(TypedEvent e) {
		
	}
	private void initController() {
		try {
			setDaoStandard(dao);		
			
			initMapComposantChamps();
			initMapComposantDecoratedField();
			listeComponentFocusableSWT(listeComposantFocusable);
			initFocusOrder();
			initActions();
			initDeplacementSaisie(listeComposantFocusable);

			branchementBouton();

			Menu popupMenuFormulaire = new Menu(vue.getShell(), SWT.POP_UP);
			Menu popupMenuGrille = new Menu(vue.getShell(), SWT.POP_UP);
			Menu[] tabPopups = new Menu[] { popupMenuFormulaire,
					popupMenuGrille };
			this.initPopupAndButtons(mapActions, tabPopups);


			initEtatBouton();
		} catch (Exception e) {
//			vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaArticlesController", e);
		}
	}

	@Override
	protected void initImageBouton() {
		vue.getPaBtn1().getBtnFermer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_FERMER));
		vue.layout(true);
	}
	
	public Composite getVue() {return vue;}
	
	public ResultAffiche configPanel(ParamAffiche param){
		if (param!=null){
			if (param.getFocusDefaut()!=null)
				param.getFocusDefaut().requestFocus();
			
			if(param.getEcranAppelant()!=null) {
				ecranAppelant = param.getEcranAppelant();
			}
			vue.getGroup1().setText(param.getTitreFormulaire());
			param.setFocus(dao.getModeObjet().getFocusCourant());
		}
		initEtatBoutonCommand();
		initEtatBouton();
		return null;
	}
	
	/**
	 * Initialisation des composants graphiques de la vue.
	 * @throws ExceptLgr 
	 */
	protected void initComposantsVue() throws ExceptLgr {
	}
	
	/**
	 * Initialisation des boutons suivant l'état de l'objet "ibTaTable"
	 */
	protected void initEtatBouton() {
				actionImprimer.setEnabled(true);
				actionAnnuler.setEnabled(true);
				actionFermer.setEnabled(true);
		}	
	
	
	/**
	 * Initialisation des correspondances entre les champs de formulaire et les
	 * champs de bdd
	 */
	protected void initMapComposantChamps() {
		// formulaire Adresse
		if (listeComposantFocusable == null) 
			listeComposantFocusable = new ArrayList();
		
		if (mapComposantChamps == null) 
			mapComposantChamps = new LinkedHashMap();
		listeComposantFocusable.add(vue.getTfDATEDEB());
		listeComposantFocusable.add(vue.getTfDATEFIN());			
		
		listeComposantFocusable.add(vue.getPaBtn1().getBtnImprimer());
		listeComposantFocusable.add(vue.getPaBtn1().getBtnFermer());
		
		if(mapInitFocus == null) 
			mapInitFocus = new LinkedHashMap();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION,vue.getLaDATEFIN());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION,vue.getLaDATEFIN());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION,vue.getLaDATEFIN());
		
	}
	

	
//	@Override
//	public void initCommands(){
//		if(handlerService == null)
//			handlerService = (IHandlerService)PlatformUI.getWorkbench().getService(IHandlerService.class);
//		if(contextService == null)
//			contextService = (IContextService) PlatformUI.getWorkbench().getService(IContextService.class);
//		contextService.registerShell(vue.getShell(),IContextService.TYPE_DIALOG);
//		activeShellExpression = new ActiveShellExpression(vue.getShell());
//		
//		//handlerService.activateHandler(C_COMMAND_GLOBAL_AIDE_ID, handlerAide,activeShellExpression);
//		handlerService.activateHandler(C_COMMAND_GLOBAL_ANNULER_ID, handlerAnnuler, activeShellExpression);
////		handlerService.activateHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID, handlerEnregistrer, activeShellExpression);
//		handlerService.activateHandler(C_COMMAND_GLOBAL_FERMER_ID, handlerFermer, activeShellExpression);
//		handlerService.activateHandler(C_COMMAND_GLOBAL_IMPRIMER_ID, handlerImprimer, activeShellExpression);
//		//handlerService.activateHandler(C_COMMAND_GLOBAL_INSERER_ID, handlerInserer, activeShellExpression);
//		//handlerService.activateHandler(C_COMMAND_GLOBAL_MODIFIER_ID, handlerModifier, activeShellExpression);
//		//handlerService.activateHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID, handlerSupprimer, activeShellExpression);	
//		//handlerService.activateHandler(C_COMMAND_GLOBAL_REFRESH_ID, handlerRefresh, activeShellExpression);
//	}
	
	protected void initActions() {
		mapCommand = new HashMap<String, IHandler>();

		mapCommand.put(C_COMMAND_GLOBAL_FERMER_ID, handlerFermer);
		mapCommand.put(C_COMMAND_GLOBAL_IMPRIMER_ID, handlerImprimer);

		initFocusCommand(String.valueOf(this.hashCode()),listeComposantFocusable,mapCommand);
		
		if (mapActions == null)
			mapActions = new LinkedHashMap<Button, Object>();

		mapActions.put(vue.getPaBtn1().getBtnFermer(), C_COMMAND_GLOBAL_FERMER_ID);
		mapActions.put(vue.getPaBtn1().getBtnImprimer(), C_COMMAND_GLOBAL_IMPRIMER_ID);
	    
		Object[] tabActionsAutres = new Object[] { };
		mapActions.put(null, tabActionsAutres);		
	}
	
	public PaCalculReportController getThis(){
		return this;
	}
	
	@Override
	public boolean onClose() throws ExceptLgr {
		return true;
	}

	
	public void retourEcran(RetourEcranEvent evt) {
		if (evt.getRetour() != null
				&& (evt.getSource() instanceof SWTPaAideControllerSWT)) {
			if (getFocusAvantAideSWT() instanceof Text) {
				try {
					((Text) getFocusAvantAideSWT()).setText(((ResultAffiche) evt
							.getRetour()).getResult());					
					ctrlUnChampsSWT(getFocusAvantAideSWT());
				} catch (Exception e) {
					logger.error("",e);
//					vue.getLaMessage().setText(e.getMessage());
				}
			}
		}
		super.retourEcran(evt);
	}
	
	@Override
	protected void actInserer() throws Exception{}
	
	@Override
	protected void actModifier() throws Exception{}
	
	@Override
	protected void actSupprimer() throws Exception{}
	
	@Override
	protected void actFermer() throws Exception {
		// (controles de sortie et fermeture de la fenetre) => onClose()
		vue.getShell().close();
	}
	
	@Override
	protected void actAnnuler() throws Exception{
		actionFermer.run();
	}
	
//	@Override
//	protected void actImprimer() throws Exception {
//		try {
//			
//
//		//créer une connection à part pour éviter d'avoir la même connection que les factures
//		//en cours de saisie ou de modification dans perspective "Facture"
//		Database base =new Database();
//		base.setConnection(new com.borland.dx.sql.dataset.ConnectionDescriptor(
//				Const.C_URL_BDD+Const.C_FICHIER_BDD,Const.C_USER,Const.C_PASS , false, Const.C_DRIVER_JDBC));
//		base.setAutoCommit(false);
//		
//		String dateDeb = vue.getTfDATEDEB().getText().replace("/", ".");
//		String dateFin = vue.getTfDATEFIN().getText().replace("/", ".");
//		//PreparedStatement ps;
//		CallableStatement ps;
//			//ps = base.getJdbcConnection().prepareCall("call calcul_report_stocks('01.01.70','29.08.08')");
//			ps = base.getJdbcConnection().prepareCall("call CALCUL_REPORT_STOCKS('"+dateDeb+"','"+dateFin+"')");
//			ps.execute();
//			base.commit();
//		actFermer();
//		MessageDialog.openInformation(vue.getShell(),
//				"Information", "Calcul des reports de stocks terminé");
//		} catch (Exception e) {
//			MessageDialog.openWarning(vue.getShell(),
//					"ATTENTION", "Le calcul des reports de stocks ne s'est pas déroulé correctement.");
//			logger.error("",e);
//		}
//	}
	

	@Override
	protected void actImprimer() throws Exception {
		EntityTransaction transaction = null;
		try {
			
			TaCriteresSaisieCaisse criteres = new TaCriteresSaisieCaisse();
			
			criteres.setTaEtablissement(masterEntity);
			if(!vue.getTfDATEDEB().equals("")){
				criteres.setDateDeb(LibDateTime.getDate(vue.getTfDATEFIN()));
			}
//			int annee =  LibConversion.stringToInteger(LibDate.getAnnee(criteres.getDateDeb()));
//			int mois = LibConversion.stringToInteger(LibDate.getMois(criteres.getDateDeb()));
//			Date dateDeb = new Date(annee,mois,01);
//			Date dateFin = new Date(annee,mois+1,01);
//			dateFin= new Date(dateFin.getTime()-1);

			EtatSaisieCaisse etatSaisieCaisse = new EtatSaisieCaisse();
			
			transaction = dao.getEntityManager().getTransaction();
			
			dao.begin(transaction);
			dao.deleteAll();
			dao.commit(transaction);
			
			List<TaSumDepot> listeReportFondCaisse = etatSaisieCaisse.calculReportJournaux(new Date(0),
					criteres.getDateDeb(),
					"%",criteres.getTaEtablissement());
			

			
			for (TaSumDepot valeur : listeReportFondCaisse) {
				TaReportTPaiement taReportSaisieCaisse = new TaReportTPaiement();
				taReportSaisieCaisse.setDateReportTPaiement(LibDateTime.getDate(vue.getTfDATEFIN()));
				TaTPaiementDAO taTPaiementDao = new TaTPaiementDAO(getEm());
				TaTPaiement taTPaiement =taTPaiementDao.findByCode(valeur.getCodeTPaiement());
				taReportSaisieCaisse.setTaTPaiement(taTPaiement);
				taReportSaisieCaisse.setMontantReport(valeur.getMontantOperation());
				taReportSaisieCaisse.setTaEtablissement(masterEntity);
				dao.begin(transaction);
				dao.enregistrerMerge(taReportSaisieCaisse);
				dao.commit(transaction);
			}
			
			transaction = null;
		
		MessageDialog.openInformation(vue.getShell(),
				"Information", "Calcul des reports de caisse terminé");
//		actFermer();
		
		} catch (Exception e) {
			MessageDialog.openWarning(vue.getShell(),
					"ATTENTION", "Le calcul des reports de caisse ne s'est pas déroulé correctement.");
			logger.error("",e);
		}
	}
	@Override
	protected void actAide() throws Exception {
		actAide(null);
	}
	
	
	@Override
	protected void actAide(String message) throws Exception {
	}

	
	@Override
	protected void actEnregistrer() throws Exception{

	}
	
	
	@Override
	public void initEtatComposant() {}
	

	@Override
	protected void actRefresh() throws Exception {
		// TODO Raccord de méthode auto-généré
		
	}

	@Override
	protected void initMapComposantDecoratedField() {
		// TODO Raccord de méthode auto-généré
		
	}

	@Override
	protected void sortieChamps() {
		// TODO Raccord de méthode auto-généré
		
	}

	@Override
	protected void initEtatBoutonCommand() {
			enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_FERMER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID,false);
	}
	public TaEtablissement getMasterEntity() {
		return masterEntity;
	}
	public void setMasterEntity(TaEtablissement masterEntity) {
		this.masterEntity = masterEntity;
	}	
}

package fr.legrain.stocks.controllers;


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
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LibDateTime;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.LibDate;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.stocks.dao.TaEtatStock;
import fr.legrain.stocks.dao.TaReportStock;
import fr.legrain.stocks.dao.TaReportStockDAO;
import fr.legrain.stocks.divers.EtatStocks;
import fr.legrain.stocks.ecran.PaCalculReportSWT;


public class PaCalculReportController extends JPABaseControllerSWTStandard implements
RetourEcranListener {
	
	static Logger logger = Logger.getLogger(PaCalculReportController.class.getName());		
	private PaCalculReportSWT vue = null; // vue
	private TaReportStockDAO dao = null;//new TaReportStockDAO();
	private Object ecranAppelant = null;
	
	public PaCalculReportController(PaCalculReportSWT vue) {
		this(vue,null);
	}
	
	public PaCalculReportController(PaCalculReportSWT vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaReportStockDAO(getEm());
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
			//On récupère la dernière date saisie dans la table des reports de stocks
			LibDateTime.setDate(vue.getTfDATEFIN(),recupDerniereDateCalcul());
			//si cette date est nulle, alors on prend la date fin exercice
			if(LibDateTime.getDate(vue.getTfDATEFIN())==null || LibDate.dateToString(LibDateTime.getDate(vue.getTfDATEFIN())).equals(
					LibDate.dateToString(new Date(0)))){
				if(taInfoEntreprise.getDatedebInfoEntreprise()!=null)
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
		return dao.recupDerniereDateReportStock();	
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
		super.initEtatBouton();
			switch (dao.getModeObjet().getMode()) {
			case C_MO_INSERTION:
				actionImprimer.setEnabled(true);
				actionAnnuler.setEnabled(true);
				actionFermer.setEnabled(true);
				break;
			case C_MO_EDITION:
				actionImprimer.setEnabled(true);
				actionAnnuler.setEnabled(true);
				actionFermer.setEnabled(true);
				break;
			case C_MO_CONSULTATION:
				actionImprimer.setEnabled(true);
				actionAnnuler.setEnabled(true);
				actionFermer.setEnabled(true);
				break;
			default:
				break;
			}
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
		if (onClose()) {
			closeWorkbenchPart();
		}
	}
	
	@Override
	protected void actAnnuler() throws Exception{
		if (focusDansEcran())actionFermer.run();
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
			transaction = dao.getEntityManager().getTransaction();
			
			dao.begin(transaction);
			dao.deleteAll();
			dao.commit(transaction);
			TaEtatStock criteres = new TaEtatStock();
			
			criteres.setDateStock(LibDateTime.getDate(vue.getTfDATEFIN()));
			EtatStocks etatStocks = new EtatStocks();
			criteres.setExclusionQteSsUnite(vue.getCbExclusion().getSelection());
			List<TaEtatStock> listeReports = etatStocks.calculEtatStocks(criteres,null);
//			begin
//			delete from ta_report_stock ;
//			  for select CODE_ARTICLE,QTE,UNITE,PERIODE_DEB,PERIODE_FIN
//			  from ETAT_STOCKS(null,null,null,null,:PERIODE_DEB,:PERIODE_FIN,0)
//			  into :CODE_ARTICLE,QTE,UNITE,PERIODE_DEB,PERIODE_FIN do
//			  begin
//			    insert into ta_report_stock (
//			        ID_REPORT_STOCK,
//			        ID_ARTICLE_REPORT_STOCK ,
//			        DATE_DEB_REPORT_STOCK ,
//			        DATE_FIN_REPORT_STOCK,
//			        QTE1_REPORT_STOCK  ,
//			        UNITE1_REPORT_STOCK
//			        )values(gen_id(num_id_report_stock,1),(select id_article from ta_article where code_article =
//			        :CODE_ARTICLE),:PERIODE_DEB,:PERIODE_FIN,:qte,:unite);
//			  end
//			  suspend;
//			end
			
			for (TaEtatStock taEtatStock : listeReports) {
				TaReportStock taReportStock = new TaReportStock();
				taReportStock.setDateDebReportStock(recupDerniereDateCalcul());
				taReportStock.setDateFinReportStock(LibDateTime.getDate(vue.getTfDATEFIN()));
				TaArticleDAO taArticleDao = new TaArticleDAO(getEm());
				TaArticle taArticle =taArticleDao.findByCode(taEtatStock.getCodeArticle());
				taReportStock.setTaArticle(taArticle);
				taReportStock.setUnite1ReportStock(taEtatStock.getUn1Stock());
				taReportStock.setUnite2ReportStock(taEtatStock.getUn2Stock());
				taReportStock.setQte1ReportStock(taEtatStock.getQte1Stock());
				taReportStock.setQte2ReportStock(taEtatStock.getQte2Stock());
				dao.begin(transaction);
				dao.enregistrerMerge(taReportStock);
				dao.commit(transaction);
			}
			
			transaction = null;
		
		MessageDialog.openInformation(vue.getShell(),
				"Information", "Calcul des reports de stocks terminé");
//		actFermer();
		
		} catch (Exception e) {
			MessageDialog.openWarning(vue.getShell(),
					"ATTENTION", "Le calcul des reports de stocks ne s'est pas déroulé correctement.");
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
}

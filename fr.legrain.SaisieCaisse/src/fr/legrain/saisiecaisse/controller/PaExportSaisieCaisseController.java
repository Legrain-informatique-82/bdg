package fr.legrain.saisiecaisse.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import fr.legrain.documents.dao.TaTPaiement;
import fr.legrain.documents.dao.TaTPaiementDAO;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Document.SWTTPaiement;
import fr.legrain.gestCom.Module_SaisieCaisse.SWTTOperation;
import fr.legrain.gestCom.Module_Stocks.SWTStocks;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.gestCom.librairiesEcran.swt.LibDateTime;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideRechercheSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideRechercheControllerSWT;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.LibChaine;
import fr.legrain.lib.data.LibDate;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.data.VerrouInterface;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.lib.gui.aide.PaAideRechercheSWT;
import fr.legrain.lib.gui.aide.PaAideSWT;
import fr.legrain.lib.gui.aide.ParamAfficheAide;
import fr.legrain.saisieCaisse.dao.TaCriteresSaisieCaisse;
import fr.legrain.saisieCaisse.dao.TaEtablissement;
import fr.legrain.saisieCaisse.dao.TaOperation;
import fr.legrain.saisieCaisse.dao.TaOperationDAO;
import fr.legrain.saisieCaisse.dao.TaTOperation;
import fr.legrain.saisieCaisse.dao.TaTOperationDAO;
import fr.legrain.saisiecaisse.saisieCaissePlugin;
import fr.legrain.saisiecaisse.divers.ExportationEpiceaSaisieCaisse;
import fr.legrain.saisiecaisse.divers.ParamAfficheTOperation;
import fr.legrain.saisiecaisse.divers.ParamImpressionSaisieCaisse;
import fr.legrain.saisiecaisse.ecran.PaEtatSaisieCaisse;
import fr.legrain.saisiecaisse.ecran.PaTOperation;
import fr.legrain.saisiecaisse.editor.EditorInputTOperation;
import fr.legrain.saisiecaisse.editor.EditorTOperation;
import fr.legrain.saisiecaisse.preferences.PreferenceConstants;
import fr.legrain.stocks.dao.TaEtatStock;
import fr.legrain.stocks.dao.TaReportStockDAO;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.divers.ParamAfficheTPaiement;
import fr.legrain.tiers.ecran.PaTypePaiementSWT;
import fr.legrain.tiers.ecran.SWTPaTypePaiementController;
import fr.legrain.tiers.editor.EditorInputTypePaiement;
import fr.legrain.tiers.editor.EditorTypePaiement;


public class PaExportSaisieCaisseController extends JPABaseControllerSWTStandard implements
RetourEcranListener {
	
	static Logger logger = Logger.getLogger(PaExportSaisieCaisseController.class.getName());		
	private PaEtatSaisieCaisse vue = null; // vue
	private TaOperationDAO dao = null;//new TaOperationDAO();
	private Object ecranAppelant = null;
	private boolean synthese = false;
	private TaEtablissement masterEntity = null;
	private LgrDozerMapper<TaEtatStock,SWTStocks> mapperModelToUIEtatStock = 
		new LgrDozerMapper<TaEtatStock,SWTStocks>();
	private LgrDozerMapper<SWTStocks,TaEtatStock> mapperUIToModelEtatStock = 
		new LgrDozerMapper<SWTStocks,TaEtatStock>();
	
//	private List<TaEtatStock>  listeEtatStock = new ArrayList<TaEtatStock>(0);
//	List<TaEtatStock>  listeEtatStockFinal = new ArrayList<TaEtatStock>();
//	private LgrDozerMapper<TaReportStock,SWTStocks> mapperModelToUIReportStock = 
//		new LgrDozerMapper<TaReportStock,SWTStocks>();
	
	public PaExportSaisieCaisseController(PaEtatSaisieCaisse vue) {
		this(vue,null);
	}

	public PaExportSaisieCaisseController(PaEtatSaisieCaisse vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaOperationDAO(getEm());
		try {
			setVue(vue);

			this.vue=vue;
			vue.getShell().addShellListener(this);

//			Branchement action annuler : empeche la fermeture automatique de la
			// fenetre sur ESC
			vue.getShell().addTraverseListener(new Traverse());
			actionImprimer.setText("Calcul[F3]");
			LibDateTime.setDate(vue.getTfDATEDEB(),new Date());
			TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
			TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
			//On récupère la dernière date saisie dans la table des reports de stocks
			//si cette date est nulle, alors on prend la date fin exercice
			if(LibDateTime.getDate(vue.getTfDATEDEB())==null){
				if(taInfoEntreprise.getDatefinInfoEntreprise()!=null)
					LibDateTime.setDate(vue.getTfDATEDEB(),LibDate.incrementDate(taInfoEntreprise.getDatedebInfoEntreprise(), -1, 0, 0) );
				else LibDateTime.setDate(vue.getTfDATEDEB(),new Date());
			}
			initController();
		} catch (Exception e) {
			logger.error("Erreur ", e);
		}

	}
	private Date recupDerniereDateCalcul(){
		TaReportStockDAO daoReport = new TaReportStockDAO(getEm());
		return daoReport.recupDerniereDateReportStock();	
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
		setSynthese(((ParamImpressionSaisieCaisse)param).isSynthese());
		}
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
		mapComposantChamps.put(vue.getTfDATEDEB(), Const.C_DATE_DEB);		
		mapComposantChamps.put(vue.getTfCODE_T_OPERATION(), Const.C_CODE_ARTICLE);
		mapComposantChamps.put(vue.getTfCODE_T_PAIEMENT(), Const.C_LIBELLE_STOCK);

		
		for (Control c : mapComposantChamps.keySet()) {
			listeComposantFocusable.add(c);
		}			
		
		listeComposantFocusable.add(vue.getPaBtn1().getBtnImprimer());
		listeComposantFocusable.add(vue.getPaBtn1().getBtnFermer());
		
		if(mapInitFocus == null) 
			mapInitFocus = new LinkedHashMap();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION,vue.getLaDATEDEB());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION,vue.getLaDATEDEB());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION,vue.getLaDATEDEB());
		
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
	
//	protected void initActions() {
//		initCommands();
//		if (mapActions == null)
//			mapActions = new LinkedHashMap();
//		
//		mapActions.put(vue.getPaBtn1().getBtnFermer(), actionFermer);
//		mapActions.put(vue.getPaBtn1().getBtnImprimer(), actionImprimer);
//		
//		Object[] tabActionsAutres = new Object[] { actionAide };
//		mapActions.put(null, tabActionsAutres);
//	}
	
	protected void initActions() {
		mapCommand = new HashMap<String, IHandler>();

		mapCommand.put(C_COMMAND_GLOBAL_AIDE_ID, handlerAide);
		mapCommand.put(C_COMMAND_GLOBAL_FERMER_ID, handlerFermer);
		mapCommand.put(C_COMMAND_GLOBAL_IMPRIMER_ID, handlerImprimer);

		initFocusCommand(String.valueOf(this.hashCode()),listeComposantFocusable,mapCommand);
		
		if (mapActions == null)
			mapActions = new LinkedHashMap<Button, Object>();

		mapActions.put(vue.getPaBtn1().getBtnFermer(), C_COMMAND_GLOBAL_FERMER_ID);
		mapActions.put(vue.getPaBtn1().getBtnImprimer(), C_COMMAND_GLOBAL_IMPRIMER_ID);
	    
		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID, C_COMMAND_GLOBAL_REFRESH_ID, C_COMMAND_GLOBAL_SELECTION_ID };
		mapActions.put(null, tabActionsAutres);		
	}

	public PaExportSaisieCaisseController getThis(){
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
					if(getFocusAvantAideSWT().equals(vue.getTfCODE_T_PAIEMENT())){
						TaTPaiement f = null;
						TaTPaiementDAO t = new TaTPaiementDAO(getEm());
						f = t.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
						t = null;
						vue.getTfCODE_T_PAIEMENT().setText(f.getCodeTPaiement());
						//taOperation.setTaTPaiement(f);
					}
					if(getFocusAvantAideSWT().equals(vue.getTfCODE_T_OPERATION())){
						TaTOperation f = null;
						TaTOperationDAO t = new TaTOperationDAO(getEm());
						f = t.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
						t = null;
						vue.getTfCODE_T_OPERATION().setText(f.getCodeTOperation());
//						taOperation.setTaTOperation(f);
					}						
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
	

	@Override
	protected void actImprimer() throws Exception {
		//Récupération des paramètres dans l'ihm
		//String[] idFactureAExporter = null;
		//String[] idAvoirAExporter = null;
		List<TaOperation> idOperationAExporter = null;
		boolean reExport = vue.getCbReExport().getSelection();
		Date dateDeb = null;
		Date dateFin = null;
		String codeDeb = null;
		String codeFin = null;
		
		String tiers = saisieCaissePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.TIERS);
		String cptAchatDefaut = saisieCaissePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.FIXE_OP_ACHAT);
		
		TaTiersDAO daoTiers = new TaTiersDAO(getEm());			
		TaTiers taTiers=daoTiers.findByCode(tiers);
		if(taTiers==null){
			MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().
					getShell(), "Paramètrage du tiers","Le tiers : '"+tiers+"' ne fait pas partie de la liste des tiers." +
					"\r\n"+"Pour l'utiliser, il vous faut le rajouter dans l'écran des tiers");			
		}else{
		if(!LibDateTime.isDateNull(vue.getTfDATEDEB() )) {
				dateDeb = LibDateTime.getDate(vue.getTfDATEDEB());
		}
//		if( (!LibChaine.empty(vue.getTfDATEFIN().getText())&& 
//				!vue.getTfDATEFIN().getText().equals("<choisir une date>")
//				&& vue.getTfDATEFIN().getText()!=null) ) {
//				dateFin = vue.getTfDATEFIN().getSelection();
//		}
		 
			if(dateDeb!=null) {//1 date => toutes les Operation à cette date
			logger.debug("Exportation - selection par date");
			TaCriteresSaisieCaisse criteres=new TaCriteresSaisieCaisse();
			criteres.setDateDeb(dateDeb);
			if(dateFin!=null) {//2 dates => Operation entre les 2 dates si intervalle correct
				
				if(LibDate.compareTo(dateDeb,dateFin)>0) {
					MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
							"ATTENTION", "La date de début doit être antérieure à la date fin");
					throw new Exception("problème intervalle des dates");
				}				
				idOperationAExporter = dao.selectOperationExportationDate(criteres,cptAchatDefaut);
			} else {
				dateFin = dateDeb;
				criteres.setDateDeb(dateFin);
				idOperationAExporter = dao.selectOperationExportationDate(criteres,cptAchatDefaut);
			}
		} 
		///////////////////////////////////////

		final List<TaOperation> finalIdOperationAExporter = idOperationAExporter;
		final boolean finalReExport = reExport;
		
		Thread t = new Thread() {
			public void run() {
				try {
					exporter(finalIdOperationAExporter,finalReExport);
					} catch (Exception e) {
					logger.error("Erreur à l'exportation ",e);
				} finally {
				}
			}
		};
		t.start();
		
		//actFermer();
		}
	}
	
	protected void exporter(List<TaOperation> idOperationAExporter,boolean reExport) throws Exception{
		final List<TaOperation> finalIdOperationAExporter = idOperationAExporter;
		final boolean finalReExport = reExport;
		final ExportationEpiceaSaisieCaisse expEpicea = new ExportationEpiceaSaisieCaisse();
		Job job = new Job("Exportation de la caisse") {
			protected IStatus run(IProgressMonitor monitor) {
				int nbOperation = 0;
				if(finalIdOperationAExporter==null) nbOperation = finalIdOperationAExporter.size();
				
				final int ticks = nbOperation;
				monitor.beginTask("Exportation", ticks);
				try {
					if(finalReExport)
						expEpicea.exportJPA(finalIdOperationAExporter,monitor,ExportationEpiceaSaisieCaisse.RE_EXPORT);
					else
						expEpicea.exportJPA(finalIdOperationAExporter,monitor);
				} finally {
					monitor.done();
				}
				return Status.OK_STATUS;
			}
		};
		job.setPriority(Job.SHORT);
		job.schedule(); 
		job.join();	
	}
	@Override
	protected void actAide() throws Exception {
		actAide(null);
	}
	
	
	@Override
	protected void actAide(String message) throws Exception {
		boolean aide = getActiveAide();
		setActiveAide(true);
//		if(aideDisponible()){
			try {
				VerrouInterface.setVerrouille(true);
				vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
				ParamAfficheAideRechercheSWT paramAfficheAideRecherche = new ParamAfficheAideRechercheSWT();
				paramAfficheAideRecherche.setMessage(message);
				// Creation de l'ecran d'aide principal
				Shell s = new Shell(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), LgrShellUtil.styleLgr);
				PaAideSWT paAide = new PaAideSWT(s, SWT.NONE);
				SWTPaAideControllerSWT paAideController = new SWTPaAideControllerSWT(paAide);
				/***************************************************************/
				LgrPartListener.getLgrPartListener().setLgrActivePart(null);
				IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
				openEditor(new EditorInputAide(), EditorAide.ID);
				LgrPartListener.getLgrPartListener().setLgrActivePart(e);
				LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
				paAideController = new SWTPaAideControllerSWT(((EditorAide)e).getComposite());
				((LgrEditorPart)e).setController(paAideController);
				((LgrEditorPart)e).setPanel(((EditorAide)e).getComposite());
				/***************************************************************/
				JPABaseControllerSWTStandard controllerEcranCreation = null;
				ParamAffiche parametreEcranCreation = null;
				IEditorPart editorCreation = null;
				String editorCreationId = null;
				IEditorInput editorInputCreation = null;
				Shell s2 = new Shell(s, LgrShellUtil.styleLgr);

				switch ((PaExportSaisieCaisseController.this.dao.getModeObjet().getMode())) {
				case C_MO_CONSULTATION:					
				case C_MO_EDITION:
				case C_MO_INSERTION:
					if (getFocusCourantSWT().equals(vue.getTfCODE_T_PAIEMENT())) {
						PaTypePaiementSWT paTypePaiementSWT = new PaTypePaiementSWT(s2,SWT.NULL);
						SWTPaTypePaiementController swtPaTypePaiementController = new SWTPaTypePaiementController(paTypePaiementSWT);

						editorCreationId = EditorTypePaiement.ID;
						editorInputCreation = new EditorInputTypePaiement();
						
						ParamAfficheTPaiement paramAfficheTPaiement = new ParamAfficheTPaiement();
						paramAfficheAideRecherche.setJPQLQuery(new TaTPaiementDAO(getEm()).getJPQLQuery());
						paramAfficheTPaiement.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheTPaiement.setEcranAppelant(paAideController);
						
						controllerEcranCreation = swtPaTypePaiementController;
						parametreEcranCreation = paramAfficheTPaiement;

						paramAfficheAideRecherche.setTypeEntite(TaTPaiement.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_T_PAIEMENT);
						paramAfficheAideRecherche.setDebutRecherche("");
						paramAfficheAideRecherche.setControllerAppelant(PaExportSaisieCaisseController.this);
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTPaiement,TaTPaiementDAO,TaTPaiement>(SWTTPaiement.class,getEm()));

						paramAfficheAideRecherche.setTypeObjet(swtPaTypePaiementController.getClassModel());
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_T_PAIEMENT);
					}

					if (getFocusCourantSWT().equals(vue.getTfCODE_T_OPERATION())) {
						PaTOperation paTOperation = new PaTOperation(s2,SWT.NULL);
						PaTOperationController swtPaTOperationController = new PaTOperationController(paTOperation);

						editorCreationId = EditorTOperation.ID;
						editorInputCreation = new EditorInputTOperation();
						
						ParamAfficheTOperation paramAfficheTOperation = new ParamAfficheTOperation();
						paramAfficheAideRecherche.setJPQLQuery(new TaTOperationDAO(getEm()).getJPQLQuery());
						paramAfficheTOperation.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheTOperation.setEcranAppelant(paAideController);
						
						controllerEcranCreation = swtPaTOperationController;
						parametreEcranCreation = paramAfficheTOperation;

						paramAfficheAideRecherche.setTypeEntite(TaTOperation.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_T_OPERATION);
						paramAfficheAideRecherche.setDebutRecherche("");
						paramAfficheAideRecherche.setControllerAppelant(PaExportSaisieCaisseController.this);
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTOperation,TaTOperationDAO,TaTOperation>(SWTTOperation.class,getEm()));

						paramAfficheAideRecherche.setTypeObjet(swtPaTOperationController.getClassModel());
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_T_OPERATION);
					}
					break;
				default:
					break;
				}
				if (paramAfficheAideRecherche.getJPQLQuery() != null) {

					PaAideRechercheSWT paAideRecherche1 = new PaAideRechercheSWT(s,
							SWT.NULL);
					SWTPaAideRechercheControllerSWT paAideRechercheController1 = new SWTPaAideRechercheControllerSWT(
							paAideRecherche1);

					// Parametrage de la recherche
					paramAfficheAideRecherche.setFocusSWT(((PaAideRechercheSWT) paAideRechercheController1.getVue()).getTfChoix());
					paramAfficheAideRecherche.setRefCreationSWT(controllerEcranCreation);
					paramAfficheAideRecherche.setEditorCreation(editorCreation);
					paramAfficheAideRecherche.setEditorCreationId(editorCreationId);
					paramAfficheAideRecherche.setEditorInputCreation(editorInputCreation);
					paramAfficheAideRecherche.setParamEcranCreation(parametreEcranCreation);
					paramAfficheAideRecherche.setShellCreation(s2);
					paAideRechercheController1.configPanel(paramAfficheAideRecherche);

					// Ajout d'une recherche
					paAideController.addRecherche(paAideRechercheController1,
							paramAfficheAideRecherche.getTitreRecherche());

					// Parametrage de l'ecran d'aide principal
					ParamAfficheAideSWT paramAfficheAideSWT = new ParamAfficheAideSWT();
					ParamAfficheAide paramAfficheAide = new ParamAfficheAide();

					// enregistrement pour le retour de l'ecran d'aide
					paAideController.addRetourEcranListener(PaExportSaisieCaisseController.this);
					Control focus = vue.getShell().getDisplay().getFocusControl();
					// affichage de l'ecran d'aide principal (+ ses recherches)


					/*****************************************************************/
					paAideController.configPanel(paramAfficheAide);
					/*****************************************************************/

				}

			} finally {
				VerrouInterface.setVerrouille(false);
				vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
			}
		//}
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
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();

		mapComposantDecoratedField.put(vue.getTfCODE_T_OPERATION(),vue.getFieldCODE_T_OPERATION());
		mapComposantDecoratedField.put(vue.getTfCODE_T_PAIEMENT(), vue.getFieldCODE_T_PAIEMENT());
	}

	@Override
	protected void sortieChamps() {
		// TODO Raccord de méthode auto-généré
		
	}
	public boolean isSynthese() {
		return synthese;
	}
	public void setSynthese(boolean synthese) {
		this.synthese = synthese;
	}
	public TaEtablissement getMasterEntity() {
		return masterEntity;
	}
	public void setMasterEntity(TaEtablissement masterEntity) {
		this.masterEntity = masterEntity;
	}
	
}

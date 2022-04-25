package fr.legrain.gestioncapsules.stocks.controllers;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.bindings.keys.ParseException;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.articles.dao.TaUniteDAO;
import fr.legrain.articles.ecran.PaArticleSWT;
import fr.legrain.articles.ecran.PaUniteSWT;
import fr.legrain.articles.ecran.ParamAfficheArticles;
import fr.legrain.articles.ecran.ParamAfficheUnite;
import fr.legrain.articles.ecran.SWTPaArticlesController;
import fr.legrain.articles.ecran.SWTPaUniteController;
import fr.legrain.articles.editor.ArticleMultiPageEditor;
import fr.legrain.articles.editor.EditorInputArticle;
import fr.legrain.articles.editor.EditorInputUnite;
import fr.legrain.articles.editor.EditorUnite;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Articles.SWTArticle;
import fr.legrain.gestCom.Module_Articles.SWTUnite;
import fr.legrain.gestCom.Module_GestionCapsules.SWTTitreTransport;
import fr.legrain.gestCom.Module_Stocks.SWTStocks;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideRechercheSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheVisualisation;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideRechercheControllerSWT;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.gestioncapsules.controllers.SWTPaTitreTransport;
import fr.legrain.gestioncapsules.dao.SWTStocksCapsules;
import fr.legrain.gestioncapsules.dao.TaStockCapsules;
import fr.legrain.gestioncapsules.dao.TaStockCapsulesDAO;
import fr.legrain.gestioncapsules.dao.TaTitreTransport;
import fr.legrain.gestioncapsules.dao.TaTitreTransportDAO;
import fr.legrain.gestioncapsules.ecrans.PaTitreTransport;
import fr.legrain.gestioncapsules.ecrans.ParamAfficheTitreTansport;
import fr.legrain.gestioncapsules.editors.EditorInputTitreTransport;
import fr.legrain.gestioncapsules.editors.EditorTitreTransport;
import fr.legrain.gestioncapsules.stocks.divers.MessagesEcran;
import fr.legrain.gestioncapsules.stocks.divers.ParamAfficheStocks;
import fr.legrain.gestioncapsules.stocks.ecrans.PaCalculReportSWT;
import fr.legrain.gestioncapsules.stocks.ecrans.PaStocks;
import fr.legrain.gestioncapsules.stocks.editor.EditorInputStocks;
import fr.legrain.gestioncapsules.stocks.editor.EditorStocks;
import fr.legrain.lib.data.ContentProposalProvider;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.InfosVerifSaisie;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
import fr.legrain.lib.data.QueDesCaracteresAutorises;
import fr.legrain.lib.data.VerrouInterface;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ParamAfficheSWT;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.lib.gui.aide.PaAideRechercheSWT;
import fr.legrain.lib.gui.aide.PaAideSWT;
import fr.legrain.lib.gui.aide.ParamAfficheAide;
import fr.legrain.lib.gui.aide.ResultAide;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.lib.gui.grille.LgrViewerSupport;
import fr.legrain.libMessageLGR.LgrMess;

public class PaStocksCapsulesController extends JPABaseControllerSWTStandard
implements RetourEcranListener {

	static Logger logger = Logger.getLogger(PaStocksCapsulesController.class.getName()); 
	private PaStocks vue = null;
	private TaStockCapsulesDAO dao = null;//new TaStockDAO();
	
	private ContentProposalAdapter articleContentProposal;
	private ContentProposalAdapter uniteContentProposal;
	private ContentProposalAdapter mouvContentProposal;
	private ContentProposalAdapter unite2ContentProposal;
	private Object ecranAppelant = null;
	private SWTStocksCapsules swtStocks;
	private SWTStocksCapsules swtOldStocks;
	private Realm realm;
	private DataBindingContext dbc;
	private Class classModel = SWTStocksCapsules.class;

//	private ModelStocks modelStocks = new ModelStocks(ibTaTable);
	private ModelGeneralObjet<SWTStocksCapsules,TaStockCapsulesDAO,TaStockCapsules> modelStocks  = null;//new ModelGeneralObjet<SWTStocksCapsules,TaStockCapsulesDAO,TaStock>(dao,classModel);

	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private IObservableValue selectedStocks;
	private String[] listeChamp;
	private String nomClassController = this.getClass().getSimpleName();
	public QueDesCaracteresAutorises queDesCaracteresAutorises = 
		 new QueDesCaracteresAutorises(new String[]{"e","s","E","S"});

	private MapChangeListener changeListener = new MapChangeListener();
	public static final String C_COMMAND_CALCUL_REPORT_ID = "fr.legrain.gestionCommerciale.stocks.calculReport";
	private HandlerCalculReport handlerCalculReport = new HandlerCalculReport();

	private LgrDozerMapper<SWTStocksCapsules,TaStockCapsules> mapperUIToModel = new LgrDozerMapper<SWTStocksCapsules,TaStockCapsules>();
	private LgrDozerMapper<TaStockCapsules,SWTStocksCapsules> mapperModelToUI = new LgrDozerMapper<TaStockCapsules,SWTStocksCapsules>();
	private TaStockCapsules taStock = null;
	
	String[] listeCodeTitreTransport=null;
	String[] listeDescriptionTitreTransport=null;
	String[] listeCodeUnites=null;
	String[] listeDescriptionUnites=null;
	String[] listeMouv=null;
	String[] listeDescriptionMouv=null;	
	
	public PaStocksCapsulesController(PaStocks vue) {
		this(vue,null);
	}
	
	public PaStocksCapsulesController(PaStocks vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaStockCapsulesDAO(getEm());
		modelStocks  = new ModelGeneralObjet<SWTStocksCapsules,TaStockCapsulesDAO,TaStockCapsules>(dao,classModel);
		setVue(vue);
		// a faire avant l'initialisation du Binding
		vue.getGrille().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {				
				setSwtOldStocks();
				initEtatUnites(((SWTStocksCapsules)e.item.getData()).getCodeTitreTransport(),false);
			}
		});
		vue.getShell().addShellListener(this);
		// Branchement action annuler : empeche la fermeture automatique de la
		// fenetre sur ESC
		vue.getShell().addTraverseListener(new Traverse());
		vue.getTfDATE_STOCK().addTraverseListener(new DateTraverse());
		vue.getTfDATE_STOCK().addFocusListener(dateFocusListener);
		initController();
	}

	private void initController() {
		try {
			setGrille(vue.getGrille());
			initSashFormWeight();
			setDaoStandard(dao);
			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);

			initMapComposantChamps();
			initVerifySaisie();
			initMapComposantDecoratedField();
			listeComponentFocusableSWT(listeComposantFocusable);
			initFocusOrder();
			initActions();
			initDeplacementSaisie(listeComposantFocusable);

			branchementBouton();

			Menu popupMenuFormulaire = new Menu(vue.getShell(), SWT.POP_UP);
			Menu popupMenuGrille = new Menu(vue.getShell(), SWT.POP_UP);
			Menu[] tabPopups = new Menu[] { popupMenuFormulaire, popupMenuGrille };
			this.initPopupAndButtons(mapActions, tabPopups);
			vue.getPaCorpsFormulaire().setMenu(popupMenuFormulaire);
			vue.getTfCODE_TITRE_TRANSPORT().setMenu(popupMenuFormulaire);
			vue.getPaGrille().setMenu(popupMenuGrille);
			vue.getGrille().setMenu(popupMenuGrille);

			initEtatBouton();
//			if(listeContext.size()>0){
//			if(!listeContext.get(0).isActive(((IEvaluationContext) PlatformUI.getWorkbench().getService(IEvaluationContext.class))))
//				((IContextService) PlatformUI.getWorkbench().getService(IContextService.class))
//					.activateContext(idContext);
//			}
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaArticlesController", e);
		}
	}

	public void bind(PaStocks paStocksSWT) {
		try {
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			tableViewer = new LgrTableViewer(paStocksSWT.getGrille());
			tableViewer.createTableCol(classModel,paStocksSWT.getGrille(), nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE,3);
			listeChamp = tableViewer.setListeChampGrille(nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			
			// Set up data binding.
			LgrViewerSupport.bind(tableViewer, 
					new WritableList(modelStocks.remplirListe(), classModel),
					BeanProperties.values(listeChamp)
					);

			selectedStocks = ViewersObservables.observeSingleSelection(tableViewer);
			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			tableViewer.selectionGrille(0);

			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);

			
			//Branchement d'un binding spécial pour traiter les dates sous forme de string pour affichage
			//dans la grille en forme Française
			bindingFormMaitreDetail( dbc, realm, selectedStocks,classModel);
			
			initEtatUnites(vue.getTfCODE_TITRE_TRANSPORT().getText(),false);
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("", e);
		}
	}

	public Composite getVue() {
		return vue;
	}
	public ResultAffiche configPanel(ParamAffiche param) {
		if (param != null) {
			if (param.getFocusDefautSWT() != null && !param.getFocusDefautSWT().isDisposed())
				param.getFocusDefautSWT().setFocus();
			else
				param.setFocusDefautSWT(vue
						.getGrille());
			if(param.getTitreFormulaire()!=null){
				vue.getLaTitreFormulaire().setText(param.getTitreFormulaire());
			} else {
				vue.getLaTitreFormulaire().setText(ParamAfficheStocks.C_TITRE_FORMULAIRE);
			}

			if(param.getTitreGrille()!=null){
				vue.getLaTitreGrille().setText(param.getTitreGrille());
			} else {
				vue.getLaTitreGrille().setText(ParamAfficheStocks.C_TITRE_GRILLE);
			}

			if(param.getSousTitre()!=null){
				vue.getLaTitreFenetre().setText(param.getSousTitre());
			} else {
				vue.getLaTitreFenetre().setText(ParamAfficheStocks.C_SOUS_TITRE);
			}

			if (param.getEcranAppelant() != null) {
				ecranAppelant = param.getEcranAppelant();
			}

			
			if(tableViewer==null) {
				//databinding pas encore realise
				bind(vue);
				tableViewer.tri(classModel, nomClassController,Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			} else {
				try {
					taStock=null;
					actRefresh();
				} catch (Exception e) {
					logger.error("",e);
				}
			}
			VerrouInterface.setVerrouille(false);
			setSwtOldStocks();

			if(param.getCodeDocument()!=null) {
				SWTStocksCapsules art = modelStocks.recherche(Const.C_ID_STOCK, param.getCodeDocument());
				if(art!=null) {
					tableViewer.setSelection(new StructuredSelection(art),true);
				}
			}

			if (param.getModeEcran() != null
					&& param.getModeEcran().compareTo(EnumModeObjet.C_MO_INSERTION) == 0) {
				try {
					actInserer();
				} catch (Exception e) {
					vue.getLaMessage().setText(e.getMessage());
					logger.error("", e);
				}
			}
		}
		return null;
	}

	protected void initComposantsVue() throws ExceptLgr {
	}

	protected void initVerifySaisie() {
		if (mapInfosVerifSaisie == null)
			mapInfosVerifSaisie = new HashMap<Control, InfosVerifSaisie>();
		
		mapInfosVerifSaisie.put(vue.getTfQTE1_STOCK(), new InfosVerifSaisie(new TaStockCapsules(),Const.C_QTE1_STOCK,new int[]{InfosVerifSaisie.CTRL_QUE_DES_CHIFFRES_POSITIFS}));
//		mapInfosVerifSaisie.put(vue.getTfQTE2_STOCK(), new InfosVerifSaisie(new TaStockCapsules(),Const.C_QTE2_STOCK,new int[]{InfosVerifSaisie.CTRL_QUE_DES_CHIFFRES_POSITIFS}));
		mapInfosVerifSaisie.put(vue.getTfMOUV_STOCK(), new InfosVerifSaisie(new TaStockCapsules(),Const.C_MOUV_STOCK,null));
		
		initVerifyListener(mapInfosVerifSaisie, dao);
	}
	protected void initMapComposantChamps() {
		if (mapComposantChamps == null)
			mapComposantChamps = new LinkedHashMap<Control, String>();

		if (listeComposantFocusable == null)
			listeComposantFocusable = new ArrayList<Control>();
		
		listeComposantFocusable.add(vue.getGrille());


		vue.getTfMOUV_STOCK().setToolTipText("E(Entrée) ou S(Sortie)");



		mapComposantChamps.put(vue.getTfCODE_TITRE_TRANSPORT(), Const.C_CODE_TITRE_TRANSPORT);
		mapComposantChamps.put(vue.getTfMOUV_STOCK(), Const.C_MOUV_STOCK);
		mapComposantChamps.put(vue.getTfDATE_STOCK(), Const.C_DATE_STOCK);
		mapComposantChamps.put(vue.getTfLIBELLE_STOCK(), Const.C_LIBELLE_STOCK);
		mapComposantChamps.put(vue.getTfQTE1_STOCK(), Const.C_QTE1_STOCK);
//		mapComposantChamps.put(vue.getTfUN1_STOCK(), Const.C_UN1_STOCK);
//		mapComposantChamps.put(vue.getTfQTE2_STOCK(), Const.C_QTE2_STOCK);
//		mapComposantChamps.put(vue.getTfUN2_STOCK(), Const.C_UN2_STOCK);

		for (Control c : mapComposantChamps.keySet()) {
			listeComposantFocusable.add(c);
		}
		
		listeComposantFocusable.add(vue.getBtnEnregistrer());
		listeComposantFocusable.add(vue.getBtnInserer());
		listeComposantFocusable.add(vue.getBtnModifier());
		listeComposantFocusable.add(vue.getBtnSupprimer());
		listeComposantFocusable.add(vue.getBtnFermer());
		listeComposantFocusable.add(vue.getBtnAnnuler());
		listeComposantFocusable.add(vue.getBtnImprimer());
		listeComposantFocusable.add(vue.getBtnCalcul());

		if (mapInitFocus == null)
			mapInitFocus = new LinkedHashMap<ModeObjet.EnumModeObjet, Control>();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION, vue
				.getTfCODE_TITRE_TRANSPORT());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue
				.getTfCODE_TITRE_TRANSPORT());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION, vue
				.getGrille());

		activeModifytListener();
		
		vue.getTfQTE1_STOCK().addVerifyListener(queDesChiffresPositifs);
//		vue.getTfQTE2_STOCK().addVerifyListener(queDesChiffresPositifs);
		vue.getTfMOUV_STOCK().addVerifyListener(queDesCaracteresAutorises);
//		vue.getTfMOUV_STOCK().addTraverseListener(new TraverseListener(){

//			public void keyTraversed(TraverseEvent e) {
//				if(e.keyCode == SWT.F1){
//					e.doit = false;
//					e.detail = SWT.CTRL+SWT.BS;
//				}
//				
//			}
//			});
		KeyStroke keyStroke;
		try {
			keyStroke = KeyStroke.getInstance("Ctrl+Space");

			String[][] TabArticles = initAdapterTitreTransport();
			if(TabArticles!=null){
				listeCodeTitreTransport=new String[TabArticles.length];
				listeDescriptionTitreTransport=new String[TabArticles.length];
				for (int i=0; i<TabArticles.length; i++) {
					listeCodeTitreTransport [i]=TabArticles[i][0];
					listeDescriptionTitreTransport [i]=TabArticles[i][1];
				}
			}
			
			articleContentProposal = new ContentProposalAdapter(
					vue.getTfCODE_TITRE_TRANSPORT(), new TextContentAdapter(), 
					new ContentProposalProvider(listeCodeTitreTransport,listeDescriptionTitreTransport),keyStroke, null);
			articleContentProposal.setFilterStyle(ContentProposalAdapter.FILTER_NONE);
			articleContentProposal.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);

			String[][] TabMouv = initAdapterMouv();
			if(TabMouv!=null){
				listeMouv=new String[TabMouv.length];
				listeDescriptionMouv=new String[TabMouv.length];
				for (int i=0; i<TabMouv.length; i++) {
					listeMouv [i]=TabMouv[i][0];
					listeDescriptionMouv [i]=TabMouv[i][1];
				}
			}
			
//			String activationContentProposal = C_COMMAND_GLOBAL_AIDE_ID;
//			ContentProposalAdapter codeFactureContentProposalDebut = 
//				LgrSimpleTextContentProposal.defaultTextContentProposalCommand(vue.getTfMOUV_STOCK(),activationContentProposal,initAdapterMouv(),null);
//			LgrSimpleTextContentProposal.defaultOptions(codeFactureContentProposalDebut);

			
			mouvContentProposal = new ContentProposalAdapter(
					vue.getTfMOUV_STOCK(), new TextContentAdapter(), 
					new ContentProposalProvider(listeMouv,listeDescriptionMouv),keyStroke, null);
			mouvContentProposal.setFilterStyle(ContentProposalAdapter.FILTER_NONE);
			mouvContentProposal.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);

			
			String[][] TabUnites = initAdapterUnites();
			if(TabUnites!=null){
				listeCodeUnites=new String[TabUnites.length];
				listeDescriptionUnites=new String[TabUnites.length];
				for (int i=0; i<TabUnites.length; i++) {
					listeCodeUnites [i]=TabUnites[i][0];
					listeDescriptionUnites [i]=TabUnites[i][1];
				}
			}
			
//			uniteContentProposal = new ContentProposalAdapter(
//					vue.getTfUN1_STOCK(), new TextContentAdapter(), 
//					new ContentProposalProvider(listeCodeUnites,listeDescriptionUnites),keyStroke, null);
//			uniteContentProposal.setFilterStyle(ContentProposalAdapter.FILTER_NONE);
//			uniteContentProposal.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);
//
//			unite2ContentProposal = new ContentProposalAdapter(
//					vue.getTfUN2_STOCK(), new TextContentAdapter(), 
//					new ContentProposalProvider(listeCodeUnites,listeDescriptionUnites),keyStroke, null);
//			unite2ContentProposal.setFilterStyle(ContentProposalAdapter.FILTER_NONE);
//			unite2ContentProposal.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);
		} catch (ParseException e1) {
			logger.error("",e1);
		}
	}

	public String[][] initAdapterTitreTransport(){
			TaTitreTransportDAO taTitreTransportDAO = new TaTitreTransportDAO(getEm());
			String[][] valeurs = null;
			List<TaTitreTransport> l = taTitreTransportDAO.selectAll();
			valeurs = new String[l.size()][2];
			int i = 0;
			String description = "";
			for (TaTitreTransport taTitreTransport : l) {
				valeurs[i][0] = taTitreTransport.getCodeTitreTransport();
				
				description = "";
				description += "Libellé : " +taTitreTransport.getLibelleTitreTransport();
//				if(taTitreTransport.getTaPrix()!=null) {
//					description += "/r/n prix HT : " + taTitreTransport.getTaPrix().getPrixPrix()+" €";
//					description += "/r/n prix TTC : " + taTitreTransport.getTaPrix().getPrixttcPrix()+" €";
//				}
//				if(taTitreTransport.getTaPrix()!=null && taTitreTransport.getTaPrix().getTaUnite()!=null) {
//					description += "/r/n Unité : " + taTitreTransport.getTaPrix().getTaUnite().getCodeUnite();
//				}				
				valeurs[i][1] = description;

				i++;
			}
			taTitreTransportDAO = null;
			return valeurs;
		}

		
		
	public String[][] initAdapterMouv(){
		String[][] valeurs = null;
			valeurs = new String[2][2];
				valeurs[0][0] = "E";
				valeurs[0][1] = "Mouvement d'entrée de stock";
				valeurs[1][0] = "S";
				valeurs[1][1] = "Mouvement de Sortie de stock";
		return valeurs;
	}

	public String[][] initAdapterUnites(){
		TaUniteDAO taUniteDAO = new TaUniteDAO(getEm());
		String[][] valeurs = null;
		List<TaUnite> l = taUniteDAO.selectAll();
		valeurs = new String[l.size()][2];
		int i = 0;
		String description = "";
		for (TaUnite taUnite : l) {
			valeurs[i][0] = taUnite.getCodeUnite();
			
			description = "";
			description += "Libellé de l'unité : " +taUnite.getLiblUnite();
			valeurs[i][1] = description;

			i++;
		}
		taUniteDAO = null;
		return valeurs;		
	}

	protected void initActions() {
		mapCommand = new HashMap<String, IHandler>();
		
		mapCommand.put(C_COMMAND_GLOBAL_MODIFIER_ID, handlerModifier);
		mapCommand.put(C_COMMAND_GLOBAL_ANNULER_ID, handlerAnnuler);
		mapCommand.put(C_COMMAND_GLOBAL_ENREGISTRER_ID, handlerEnregistrer);
		mapCommand.put(C_COMMAND_GLOBAL_INSERER_ID, handlerInserer);
		mapCommand.put(C_COMMAND_GLOBAL_SUPPRIMER_ID, handlerSupprimer);
		mapCommand.put(C_COMMAND_GLOBAL_REFRESH_ID, handlerRefresh);
		mapCommand.put(C_COMMAND_GLOBAL_AIDE_ID, handlerAide);
		mapCommand.put(C_COMMAND_GLOBAL_FERMER_ID, handlerFermer);
		mapCommand.put(C_COMMAND_GLOBAL_IMPRIMER_ID, handlerImprimer);
		
		mapCommand.put(C_COMMAND_GLOBAL_SELECTION_ID, handlerSelection);
		//mapCommand.put(C_COMMAND_CALCUL_REPORT_ID, handlerCalculReport);
		
		initFocusCommand(String.valueOf(this.hashCode()),listeComposantFocusable,mapCommand);
		
		if (mapActions == null)
			mapActions = new LinkedHashMap<Button, Object>();
		
		mapActions.put(vue.getBtnAnnuler(), C_COMMAND_GLOBAL_ANNULER_ID);
		mapActions.put(vue.getBtnEnregistrer(), C_COMMAND_GLOBAL_ENREGISTRER_ID);
		mapActions.put(vue.getBtnInserer(), C_COMMAND_GLOBAL_INSERER_ID);
		mapActions.put(vue.getBtnModifier(), C_COMMAND_GLOBAL_MODIFIER_ID);
		mapActions.put(vue.getBtnSupprimer(), C_COMMAND_GLOBAL_SUPPRIMER_ID);
		mapActions.put(vue.getBtnFermer(), C_COMMAND_GLOBAL_FERMER_ID);
		mapActions.put(vue.getBtnImprimer(), C_COMMAND_GLOBAL_IMPRIMER_ID);
		//mapActions.put(vue.getBtnCalcul(), C_COMMAND_CALCUL_REPORT_ID);

		//mapActions.put(vue.getBtnSelection(), C_COMMAND_GLOBAL_SELECTION_ID);
		
		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID, C_COMMAND_GLOBAL_REFRESH_ID, C_COMMAND_GLOBAL_SELECTION_ID };
		mapActions.put(null, tabActionsAutres);

		
		//j'ai désactiver pour l'instant le bouton CalculReport
		enableActionAndHandler(C_COMMAND_CALCUL_REPORT_ID,LgrMess.isDEVELOPPEMENT());
	}
	

	public PaStocksCapsulesController getThis() {
		return this;
	}

	@Override
	public boolean onClose() throws ExceptLgr {
		boolean retour = true;
		VerrouInterface.setVerrouille(true);
		switch (dao.getModeObjet().getMode()) {
		case C_MO_INSERTION:
		case C_MO_EDITION:
			if (MessageDialog.openQuestion(vue.getShell(), MessagesEcran
					.getString("Message.Attention"), MessagesEcran
					.getString("Stocks.Message.Enregistrer"))) {
				try {
					actEnregistrer();
				} catch (Exception e) {
					vue.getLaMessage().setText(e.getMessage());
					logger.error("", e);
				}
			} else
				
			break;
		case C_MO_CONSULTATION:
			break;
		default:
			break;
		}

		if (retour) {
			if (ecranAppelant instanceof SWTPaAideControllerSWT) {
				setListeEntity(getModelStocks().remplirListe());
				dao.initValeurIdTable(taStock);
				fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
						dao.getChampIdEntite(), dao.getValeurIdTable(),
						selectedStocks.getValue())));
				retour = true;
			}
		}
		return retour;
	}

	public void retourEcran(final RetourEcranEvent evt) {
		if (evt.getRetour() != null
				&& (evt.getSource() instanceof SWTPaAideControllerSWT)) {
			if (getFocusAvantAideSWT() instanceof Text) {
				try {
					((Text) getFocusAvantAideSWT()).setText(((ResultAffiche) evt
							.getRetour()).getResult());	
//					if(getFocusAvantAideSWT().equals(vue.getTfCODE_FAMILLE())){
//						TaFamille f = null;
//						TaFamilleDAO t = new TaFamilleDAO();
//						f = t.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
//						t = null;
//						taArticle.setTaFamille(f);
//					}
					ctrlUnChampsSWT(getFocusAvantAideSWT());
				} catch (Exception e) {
					logger.error("",e);
					if(getFocusAvantAideSWT()!=null)setFocusCourantSWT(getFocusAvantAideSWT());
					vue.getLaMessage().setText(e.getMessage());
				}
			}
			if (getFocusAvantAideSWT() instanceof Table) {
				if (getFocusAvantAideSWT() == vue.getGrille()) {
					if(((ResultAffiche) evt.getRetour()).getSelection()!=null)
						tableViewer.setSelection(((ResultAffiche) evt.getRetour()).getSelection(),true);
				}
			}
		} else if (evt.getRetour() != null){
			if (getFocusAvantAideSWT() instanceof Table) {
				if (getFocusAvantAideSWT() == vue.getGrille()) {
				}
			}
		}
		super.retourEcran(evt);
	}

	@Override
	protected void actInserer() throws Exception {
		try {
			if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
			VerrouInterface.setVerrouille(true);
			setSwtOldStocks();
			swtStocks = new SWTStocksCapsules();
			swtStocks.setMouvStock("E");
			taStock = new TaStockCapsules();
			mapperUIToModel.map(swtStocks, taStock);
			//taStock.setMouvStock(swtStocks.getMouvStock());
			dao.inserer(taStock);
			modelStocks.getListeObjet().add(swtStocks);
			writableList = new WritableList(realm, modelStocks
					.getListeObjet(), classModel);
			tableViewer.setInput(writableList);
			tableViewer.refresh();
			tableViewer.setSelection(new StructuredSelection(swtStocks));
			initEtatBouton();
			}
		} catch (ExceptLgr e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		} finally {
			initEtatComposant();
			VerrouInterface.setVerrouille(false);
		}
	}

	public void setSwtOldStocksRefresh() {
		if (selectedStocks.getValue()!=null){
			taStock = dao.findById(((SWTStocksCapsules) selectedStocks.getValue()).getIdStock());
			modelStocks.removeEntity(taStock);
			if(LgrMess.isDOSSIER_EN_RESEAU())taStock = dao.refresh(dao.findById(((SWTStocksCapsules) selectedStocks.getValue()).getIdStock()));
			modelStocks.addEntity(taStock);
			SWTStocksCapsules oldSwtStocks =(SWTStocksCapsules) selectedStocks.getValue(); 
			mapperModelToUI.map(taStock, swtOldStocks);
			if(!oldSwtStocks.equals(swtOldStocks)){
				try {
					if(LgrMess.isDOSSIER_EN_RESEAU())actRefresh();
				} catch (Exception e) {
					logger.error("",e);
				}
			}
			this.swtOldStocks=SWTStocksCapsules.copy((SWTStocksCapsules)selectedStocks.getValue());
		}
	}

	@Override
	protected void actModifier() throws Exception {
		try {
			if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
//				setSwtOldStocks();
				setSwtOldStocksRefresh();
				dao.modifier(taStock);

			initEtatBoutonCommand(!focusDansFormulaire());
			}
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		}
	}

	public void modifMode(){
		if (!VerrouInterface.isVerrouille() ){
			try {
				if(!daoStandard.dataSetEnModif()) {
					if(!modelStocks.getListeObjet().isEmpty()) {
						actModifier();
					} else {
						actInserer();								
					}
					initEtatBouton(false);
				}
			} catch (Exception e1) {
				logger.error("",e1);
			}				
		} 
	}
	@Override
	protected void actSupprimer() throws Exception {
		EntityTransaction transaction = dao.getEntityManager().getTransaction();
		try {
			VerrouInterface.setVerrouille(true);
			if(isUtilise())MessageDialog.openInformation(vue.getShell(), MessagesEcran
					.getString("Message.Attention"), MessagesEcran
					.getString("Message.suppression"));
			else
				if (MessageDialog.openConfirm(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("Stocks.Message.Supprimer"))) {
					dao.begin(transaction);
					TaStockCapsules u = dao.findById(((SWTStocksCapsules) selectedStocks.getValue()).getIdStock());
					dao.supprimer(u);
					dao.commit(transaction);
					Object suivant=tableViewer.getElementAt(tableViewer.getTable().getSelectionIndex()+1);
					modelStocks.removeEntity(u);
					taStock=null;
					transaction=null;
					dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
					if(suivant!=null)tableViewer.setSelection(new StructuredSelection(suivant),true);
					else tableViewer.selectionGrille(0);
					actRefresh(); //ajouter pour tester jpa
				}
		} catch (ExceptLgr e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		} finally {
			if(transaction!=null && transaction.isActive()) {
				transaction.rollback();
			}
			initEtatBouton();
			if(swtStocks!=null && swtStocks.getCodeTitreTransport()!=null)
			  initEtatUnites(swtStocks.getCodeTitreTransport(), false);
			VerrouInterface.setVerrouille(false);
		}
	}


	@Override
	protected void actFermer() throws Exception {
		// (controles de sortie et fermeture de la fenetre) => onClose()
		if (onClose()) {
			closeWorkbenchPart();
		}
	}

	@Override
	protected void actAnnuler() throws Exception {
		try {
			VerrouInterface.setVerrouille(true);

			switch (dao.getModeObjet().getMode()) {
			case C_MO_INSERTION:
				if (MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("Stocks.Message.Annuler"))) {
					remetTousLesChampsApresAnnulationSWT(dbc);
					if(((SWTStocksCapsules) selectedStocks.getValue()).getIdStock()==null||
							((SWTStocksCapsules) selectedStocks.getValue()).getIdStock()==0){
						modelStocks.getListeObjet().remove(
							((SWTStocksCapsules) selectedStocks.getValue()));
					writableList = new WritableList(realm, modelStocks
							.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.selectionGrille(0);
					}
					dao.annuler(taStock);
					hideDecoratedFields();
				}
				initEtatBouton();
				break;
			case C_MO_EDITION:
				if (MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("Stocks.Message.Annuler"))) {
					remetTousLesChampsApresAnnulationSWT(dbc);
					int rang = modelStocks.getListeObjet().indexOf(selectedStocks.getValue());
					modelStocks.getListeObjet().set(rang, swtOldStocks);
					writableList = new WritableList(realm, modelStocks.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.setSelection(new StructuredSelection(swtOldStocks), true);
					dao.annuler(taStock);
					hideDecoratedFields();
				}
				initEtatBouton();

				break;
			case C_MO_CONSULTATION:
				actionFermer.run();
				break;
			default:
				break;
			}
		} finally {
			VerrouInterface.setVerrouille(false);
		}
	}

	@Override
	protected void actImprimer() throws Exception {
		
//		SWTInfoEntreprise infoEntreprise = null;
//		infoEntreprise = SWT_IB_TA_INFO_ENTREPRISE.infosEntreprise("1", infoEntreprise);
//		String nomDossier = null;
//		ConstEdition objetConstEdition = new ConstEdition(); 
//		int nombreLine = objetConstEdition.nombreLineTable(tableViewer);
//		//System.out.println("nombre line is "+nombreLine);
//		
//		LinkedList<Integer> idSWTStocks = new LinkedList<Integer>();//pour stocker tous les id dans un ecrean
//		LinkedList<Integer> oneIDSWTStocks = new LinkedList<Integer>();//pour stocker un id d'une choix
//		
//		LinkedList<SWTStocksCapsules> objectContenuTable = modelStocks.getListeObjet();
//		for (SWTStocksCapsules stocks : objectContenuTable) {
//			//System.out.println("ID_Stocke--"+stocks.getID_STOCK());
//			idSWTStocks.add(stocks.getIdStock());
//		}
//		Integer oneIDStocks = ((SWTStocksCapsules)selectedStocks.getValue()).getIdStock();
//		System.out.println("oneID--"+oneIDStocks);
//		oneIDSWTStocks.add(oneIDStocks);
//		if(nombreLine==0){
//			MessageDialog.openWarning(vue.getShell(), ConstEdition.TITRE_MESSAGE_EDITION_VIDE,
//					ConstEdition.EDITION_VIDE);
//		}
//		else{
//			if(infoEntreprise.getIdInfoEntreprise()==null){
//				nomDossier = objetConstEdition.INFOS_VIDE;
//			}
//			else{
//				nomDossier = infoEntreprise.getNomInfoEntreprise();	
//			}
//			String querySql = ibTaTable.getFIBQuery().getQuery().getQueryString();
//			//System.out.println("querySql "+querySql);
//			String nameTable = ibTaTable.nomTable;
//			String nameClass = PaStocksController.class.getSimpleName();
//			String sqlQueryStart = "SELECT ";
//			String sqlQueryEnd = " FROM "+Const.C_NOM_VU_STOCK;
//			String sqlQueryMiddle = objetConstEdition.addValueList(tableViewer, nameClass);
//			
//			ArrayList<String> nameTableEcran = objetConstEdition.getNameTableEcran();
//			ArrayList<String> nameTableBDD = objetConstEdition.getNameTableBDD();
//			/*
//			 * name ecran ==> Code article--Mouvement--Date--Libellé
//			 * Unité 1--Quantité 1--Unité 2--Quantité 2
//			 */
////			for(int i=0;i<nameTableEcran.size();i++){
////			System.out.println(nameTableEcran.get(i));
////			}
////			/*
////			 * name BDD ==> CODE_ARTICLE(string)--MOUV_STOCK(string)--DATE_STOCK(date)
////			 * LIBELLE_STOCK(string)--UN1_STOCK(string)--QTE1_STOCK(float)
////			 * UN2_STOCK(string)--QTE2_STOCK(float)
////			 */
////			System.out.println("***********");
////			for(int i=0;i<nameTableBDD.size();i++){
////			System.out.println(nameTableBDD.get(i));
////			}
//			String sqlQuery = sqlQueryStart+sqlQueryMiddle+sqlQueryEnd+";";
//			//System.out.println("sqlQuery "+sqlQuery);
//			String  C_FICHIER_BDD = Const.C_FICHIER_BDD.replaceFirst(":", "/");
//			//System.out.println(C_FICHIER_BDD);
//			String FILE_BDD = Const.C_URL_BDD+"//"+C_FICHIER_BDD;//jdbc:firebirdsql://localhost/C:/runtime-GestionCommercialeComplet.product/dossier/Bd/GEST_COM.FDB
//			//System.out.println(FILE_BDD);
//			Path pathFileReport = new Path(Const.C_RCP_INSTANCE_LOCATION+"/"+
//					Const.C_NOM_PROJET_TMP+"/"+Const.C_NOM_VU_STOCK+".rptdesign");
//			final String PATH_FILE_REPORT = pathFileReport.toPortableString();
//			//System.out.println(PATH_FILE_REPORT);
//			Map<String, AttributElement> attribuTabHeader = new LinkedHashMap<String, AttributElement>();
//			attribuTabHeader.put("Date", new AttributElement("7",ConstEdition.TEXT_ALIGN_LEFT,
//					ConstEdition.FONT_SIZE_MEDIUM,ConstEdition.FONT_WEIGHT_BOLD,
//					ConstEdition.UNITS_PERCENTAGE,ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
//					,""));
//			attribuTabHeader.put("Code article", new AttributElement("10",ConstEdition.TEXT_ALIGN_LEFT,
//					ConstEdition.FONT_SIZE_MEDIUM,ConstEdition.FONT_WEIGHT_BOLD,
//					ConstEdition.UNITS_PERCENTAGE,ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
//					,""));
//			attribuTabHeader.put("Libellé de l'article", new AttributElement("31",ConstEdition.TEXT_ALIGN_LEFT,
//					ConstEdition.FONT_SIZE_MEDIUM,ConstEdition.FONT_WEIGHT_BOLD,
//					ConstEdition.UNITS_PERCENTAGE,ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
//					,""));
//			attribuTabHeader.put("M", new AttributElement("2",ConstEdition.TEXT_ALIGN_CENTER,
//					ConstEdition.FONT_SIZE_MEDIUM,ConstEdition.FONT_WEIGHT_BOLD,
//					ConstEdition.UNITS_PERCENTAGE,ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
//					,""));
//			attribuTabHeader.put("Libellé des mouvements", new AttributElement("31",ConstEdition.TEXT_ALIGN_LEFT,
//					ConstEdition.FONT_SIZE_MEDIUM,ConstEdition.FONT_WEIGHT_BOLD,
//					ConstEdition.UNITS_PERCENTAGE,ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
//					,""));
//			attribuTabHeader.put("Qté 1", new AttributElement("8",ConstEdition.TEXT_ALIGN_RIGHT,
//					ConstEdition.FONT_SIZE_MEDIUM,ConstEdition.FONT_WEIGHT_BOLD,
//					ConstEdition.UNITS_PERCENTAGE,ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
//					,""));
//			attribuTabHeader.put("U1", new AttributElement("6",ConstEdition.TEXT_ALIGN_RIGHT,
//					ConstEdition.FONT_SIZE_MEDIUM,ConstEdition.FONT_WEIGHT_BOLD,
//					ConstEdition.UNITS_PERCENTAGE,ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
//					,""));
//			attribuTabHeader.put("Qté 2", new AttributElement("8",ConstEdition.TEXT_ALIGN_RIGHT,
//					ConstEdition.FONT_SIZE_MEDIUM,ConstEdition.FONT_WEIGHT_BOLD,
//					ConstEdition.UNITS_PERCENTAGE,ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
//					,""));
//			attribuTabHeader.put("U2", new AttributElement("6",ConstEdition.TEXT_ALIGN_RIGHT,
//					ConstEdition.FONT_SIZE_MEDIUM,ConstEdition.FONT_WEIGHT_BOLD,
//					ConstEdition.UNITS_PERCENTAGE,ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
//					,""));
//			
//			Map<String, AttributElement> attribuTabDetail = new LinkedHashMap<String, AttributElement>();
//			attribuTabDetail.put("DATE_STOCK", new AttributElement("7",ConstEdition.TEXT_ALIGN_LEFT,
//					ConstEdition.FONT_SIZE_MEDIUM,ConstEdition.FONT_WEIGHT_BOLD,
//					ConstEdition.UNITS_PERCENTAGE,ConstEdition.COLUMN_DATA_TYPE_DATE,ConstEdition.UNITS_VIDE
//					,""));
//			attribuTabDetail.put("CODE_ARTICLE", new AttributElement("10",ConstEdition.TEXT_ALIGN_LEFT,
//					ConstEdition.FONT_SIZE_MEDIUM,ConstEdition.FONT_WEIGHT_BOLD,
//					ConstEdition.UNITS_PERCENTAGE,ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
//					,""));
//			attribuTabDetail.put("LIBELLE_ARTICLE", new AttributElement("31",ConstEdition.TEXT_ALIGN_LEFT,
//					ConstEdition.FONT_SIZE_MEDIUM,ConstEdition.FONT_WEIGHT_BOLD,
//					ConstEdition.UNITS_PERCENTAGE,ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
//					,""));
//			attribuTabDetail.put("MOUV_STOCK", new AttributElement("2",ConstEdition.TEXT_ALIGN_CENTER,
//					ConstEdition.FONT_SIZE_MEDIUM,ConstEdition.FONT_WEIGHT_BOLD,
//					ConstEdition.UNITS_PERCENTAGE,ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
//					,""));
//			attribuTabDetail.put("LIBELLE_STOCK", new AttributElement("31",ConstEdition.TEXT_ALIGN_LEFT,
//					ConstEdition.FONT_SIZE_MEDIUM,ConstEdition.FONT_WEIGHT_BOLD,
//					ConstEdition.UNITS_PERCENTAGE,ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
//					,""));
//			attribuTabDetail.put("QTE1_STOCK", new AttributElement("8",ConstEdition.TEXT_ALIGN_RIGHT,
//					ConstEdition.FONT_SIZE_MEDIUM,ConstEdition.FONT_WEIGHT_BOLD,
//					ConstEdition.UNITS_PERCENTAGE,ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
//					,""));
//			attribuTabDetail.put("UN1_STOCK", new AttributElement("6",ConstEdition.TEXT_ALIGN_RIGHT,
//					ConstEdition.FONT_SIZE_MEDIUM,ConstEdition.FONT_WEIGHT_BOLD,
//					ConstEdition.UNITS_PERCENTAGE,ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
//					,""));
//			attribuTabDetail.put("QTE2_STOCK", new AttributElement("8",ConstEdition.TEXT_ALIGN_RIGHT,
//					ConstEdition.FONT_SIZE_MEDIUM,ConstEdition.FONT_WEIGHT_BOLD,
//					ConstEdition.UNITS_PERCENTAGE,ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
//					,""));
//			attribuTabDetail.put("UN2_STOCK", new AttributElement("6",ConstEdition.TEXT_ALIGN_RIGHT,
//					ConstEdition.FONT_SIZE_MEDIUM,ConstEdition.FONT_WEIGHT_BOLD,
//					ConstEdition.UNITS_PERCENTAGE,ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
//					,""));
//			
//			MakeDynamiqueReport DynamiqueReport = new MakeDynamiqueReport(nameTableEcran,nameTableBDD,
//					sqlQuery/*,ConstEdition.BIRT_HOME*/,PATH_FILE_REPORT,Const.
//					C_DRIVER_JDBC,FILE_BDD,Const.C_USER,Const.C_PASS,
//					Const.C_NOM_VU_STOCK,ConstEdition.PAGE_ORIENTATION_LANDSCAPE,
//					nomDossier); 
//			
//			Map<String, AttributElement> attribuGridHeader = new LinkedHashMap<String, AttributElement>();
//			
////			String nameHeaderTitle = ConstEditionStocks.TITLE_EDITION_STOCKS;
////			attribuGridHeader.put(nameHeaderTitle, new AttribuElement("",ConstEdition.TEXT_ALIGN_CENTER,
////					ConstEdition.FONT_SIZE_XX_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
////					ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
////					,""));
//			
//			String nameSousHeaderTitle = ConstEditionStocks.SOUS_TITLE_EDITION_STOCKS;
//			attribuGridHeader.put(nameSousHeaderTitle, new AttributElement("",ConstEdition.TEXT_ALIGN_CENTER,
//					ConstEdition.FONT_SIZE_X_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
//					ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
//					,ConstEdition.COLOR_GRAY));
//			ConstEdition.CONTENT_COMMENTS = ConstEditionStocks.COMMENTAIRE_EDITION_DEFAUT;
//			
//			DynamiqueReport.initializeBuildDesignReportConfig();
//			DynamiqueReport.makePageMater("0.5", "1", "0.5", "1", "100");
//			DynamiqueReport.makeReportHeaderGrid(3,5,100,ConstEdition.UNITS_PERCENTAGE,attribuGridHeader);
//			DynamiqueReport.makeReportTableDB(100,ConstEdition.UNITS_PERCENTAGE,
//					Const.C_NOM_VU_STOCK,attribuTabHeader,attribuTabDetail,1,1,2,5,"30");
//			DynamiqueReport.savsAsDesignHandle();
//			
//			Bundle bundleCourant = StocksPlugin.getDefault().getBundle();
//			
//			File reportFile = objetConstEdition.findPathReportPlugin(bundleCourant, 
//					ConstEdition.FOLDER_REPORT_PLUGIN, ConstEditionStocks.SOUS_REPORT_STOCKS);
//			
//			final String namePlugin = bundleCourant.getSymbolicName();//name plugin is <<Stocks>>
//			
//			objetConstEdition.makeFolderEditions(Const.C_REPERTOIRE_BASE+
//					Const.C_REPERTOIRE_PROJET.replaceFirst("/", "")+
//					ConstEdition.FOLDER_EDITION);
//			
//			String FloderEdition = Const.C_REPERTOIRE_BASE+
//			Const.C_REPERTOIRE_PROJET.replaceFirst("/", "")+ConstEdition.FOLDER_EDITION+
//			namePlugin;
//			
//			File FloderFileEditions = objetConstEdition.makeFolderEditions(FloderEdition);
//			
//			String nomOnglet = ConstEdition.EDITION+namePlugin;
//			
//			Shell dialogShell = new Shell(vue.getShell(),
//					//SWT.DIALOG_TRIM |SWT.APPLICATION_MODAL);
//					SWT.RESIZE | SWT.CLOSE | SWT.MAX | SWT.APPLICATION_MODAL);
//			dialogShell.setText(ConstEdition.TITLE_SHELL);
//			dialogShell.setLayout(new FillLayout());
//			SwtCompositeReport_new dialogReport = new SwtCompositeReport_new(dialogShell,SWT.NULL);
//			
//			objetConstEdition.addValues(idSWTStocks,oneIDSWTStocks);
//			objetConstEdition.setPARAM_ID_TABLE(ConstEditionStocks.PARAM_REPORT_ID_STOCK);
//			
//			objetConstEdition.openDialogChoixEdition_new(dialogReport, FloderFileEditions, 
//					PATH_FILE_REPORT, namePlugin,nomOnglet,dialogShell,reportFile);
//		}
//		
//		
//		
	}
	
	@Override
	protected boolean aideDisponible() {
		boolean result = false;
		switch ((getThis().dao.getModeObjet().getMode())) {
		case C_MO_CONSULTATION:
			if(getFocusCourantSWT().equals(vue.getGrille())) 
				result = true;
			break;
			
		case C_MO_EDITION:
		case C_MO_INSERTION: 
				result = getFocusCourantSWT().equals(vue.getTfCODE_TITRE_TRANSPORT())
				//||
//				getFocusCourantSWT().equals(vue.getTfUN1_STOCK())||
//				getFocusCourantSWT().equals(vue.getTfUN2_STOCK())
				;
			break;
		default:
			break;
		}
		
		return result;
	}
	
	@Override
	protected void actAide() throws Exception {
		actAide(null);
	}

	@Override
	protected void actAide(String message) throws Exception {
		boolean aide = getActiveAide();
		setActiveAide(true);
		if(aideDisponible()){
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
				IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new EditorInputAide(), EditorAide.ID);
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

				switch ((getThis().dao.getModeObjet().getMode())) {
				case C_MO_CONSULTATION:
					if(getFocusCourantSWT().equals(vue.getGrille())){
						PaStocks paStocks = new PaStocks(s2,SWT.NULL);
						PaStocksCapsulesController PaStocksController = new PaStocksCapsulesController(paStocks);

						editorCreationId = EditorStocks.ID;
						editorInputCreation = new EditorInputStocks();

						ParamAfficheStocks paramAfficheStocks = new ParamAfficheStocks();
						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
						paramAfficheStocks.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheStocks.setEcranAppelant(paAideController);
						controllerEcranCreation = PaStocksController;
						parametreEcranCreation = paramAfficheStocks;

						paramAfficheAideRecherche.setTypeEntite(TaStockCapsules.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_ARTICLE);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_TITRE_TRANSPORT().getText());
						paramAfficheAideRecherche.setControllerAppelant(getThis());
						paramAfficheAideRecherche.setModel(PaStocksController.getModelStocks());
						paramAfficheAideRecherche.setTypeObjet(PaStocksController.getClassModel());
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_STOCK);
					}
					break;
				case C_MO_EDITION:
				case C_MO_INSERTION:
					if(getFocusCourantSWT().equals(vue.getTfCODE_TITRE_TRANSPORT())){
//						PaArticleSWT paArticlesSWT = new PaArticleSWT(s2,SWT.NULL);
//						SWTPaArticlesController swtPaArticlesController = new SWTPaArticlesController(paArticlesSWT);
//
//						editorCreationId = ArticleMultiPageEditor.ID_EDITOR;
//						editorInputCreation = new EditorInputArticle();
//
//						ParamAfficheArticles paramAfficheArticles = new ParamAfficheArticles();
//						paramAfficheAideRecherche.setJPQLQuery(new TaArticleDAO(getEm()).getJPQLQuery());
//						paramAfficheArticles.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//						paramAfficheArticles.setEcranAppelant(paAideController);
//						controllerEcranCreation = swtPaArticlesController;
//						parametreEcranCreation = paramAfficheArticles;
//
//						paramAfficheAideRecherche.setTypeEntite(TaArticle.class);
//						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_ARTICLE);
//						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_TITRE_TRANSPORT().getText());
//						paramAfficheAideRecherche.setControllerAppelant(getThis());
//						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTArticle,TaArticleDAO,TaArticle>(SWTArticle.class,dao.getEntityManager()));
//						paramAfficheAideRecherche.setTypeObjet(swtPaArticlesController.getClassModel());
//						paramAfficheAideRecherche.setChampsIdentifiant(swtPaArticlesController.getDao().getChampIdTable());
						PaTitreTransport paTitreTransport = new PaTitreTransport(s2,SWT.NULL);
						SWTPaTitreTransport swtPaTitreTransport = new SWTPaTitreTransport(paTitreTransport);

						editorCreationId = EditorTitreTransport.ID;
						editorInputCreation = new EditorInputTitreTransport();

						ParamAfficheTitreTansport paramAfficheTitreTansport = new ParamAfficheTitreTansport();
						paramAfficheAideRecherche.setJPQLQuery(new TaTitreTransportDAO(getEm()).getJPQLQuery());
						paramAfficheTitreTansport.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheTitreTansport.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaTitreTransport;
						parametreEcranCreation = paramAfficheTitreTansport;

						paramAfficheAideRecherche.setTypeEntite(TaTitreTransport.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_TITRE_TRANSPORT);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_TITRE_TRANSPORT().getText());
						paramAfficheAideRecherche.setControllerAppelant(PaStocksCapsulesController.this);
//						paramAfficheAideRecherche.setModel(new TaTitreTransportDAO(getEm()).modelObjetUniteArticle(taArticle.getCodeArticle()));
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTitreTransport,TaTitreTransportDAO,TaTitreTransport>(SWTTitreTransport.class,dao.getEntityManager()));
						paramAfficheAideRecherche.setTypeObjet(swtPaTitreTransport.getClassModel());

						paramAfficheAideRecherche.setChampsIdentifiant(swtPaTitreTransport.getDao().getChampIdTable());
					}
//					if(getFocusCourantSWT().equals(vue.getTfUN1_STOCK())){
//						PaUniteSWT paUniteSWT = new PaUniteSWT(s2,SWT.NULL);
//						SWTPaUniteController swtPaUniteController = new SWTPaUniteController(paUniteSWT);
//
//						editorCreationId = EditorUnite.ID;
//						editorInputCreation = new EditorInputUnite();
//
//						ParamAfficheUnite paramAfficheUnite = new ParamAfficheUnite();
//						paramAfficheAideRecherche.setJPQLQuery(new TaUniteDAO(getEm()).getJPQLQuery());
//						paramAfficheUnite.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//						paramAfficheUnite.setEcranAppelant(paAideController);
//						controllerEcranCreation = swtPaUniteController;
//						parametreEcranCreation = paramAfficheUnite;
//
//						paramAfficheAideRecherche.setTypeEntite(TaUnite.class);
//						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_UNITE);
//						paramAfficheAideRecherche.setDebutRecherche(vue.getTfUN1_STOCK().getText());
//						paramAfficheAideRecherche.setControllerAppelant(getThis());
//						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTUnite,TaUniteDAO,TaUnite>(SWTUnite.class,dao.getEntityManager()));
//						paramAfficheAideRecherche.setTypeObjet(swtPaUniteController.getClassModel());
//
//						paramAfficheAideRecherche.setChampsIdentifiant(swtPaUniteController.getDao().getChampIdTable());
//					}
//					if(getFocusCourantSWT().equals(vue.getTfUN2_STOCK())){
//						PaUniteSWT paUniteSWT = new PaUniteSWT(s2,SWT.NULL);
//						SWTPaUniteController swtPaUniteController = new SWTPaUniteController(paUniteSWT);
//
//						editorCreationId = EditorUnite.ID;
//						editorInputCreation = new EditorInputUnite();
//
//						ParamAfficheUnite paramAfficheUnite = new ParamAfficheUnite();
//						paramAfficheAideRecherche.setJPQLQuery(new TaUniteDAO(getEm()).getJPQLQuery());
//						paramAfficheUnite.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//						paramAfficheUnite.setEcranAppelant(paAideController);
//						controllerEcranCreation = swtPaUniteController;
//						parametreEcranCreation = paramAfficheUnite;
//
//						paramAfficheAideRecherche.setTypeEntite(TaUnite.class);
//						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_UNITE);
//						paramAfficheAideRecherche.setDebutRecherche(vue.getTfUN2_STOCK().getText());
//						paramAfficheAideRecherche.setControllerAppelant(getThis());
//						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTUnite,TaUniteDAO,TaUnite>(SWTUnite.class,dao.getEntityManager()));
//						paramAfficheAideRecherche.setTypeObjet(swtPaUniteController.getClassModel());
//
//						paramAfficheAideRecherche.setChampsIdentifiant(swtPaUniteController.getDao().getChampIdTable());
//					}
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
					paAideController.addRetourEcranListener(PaStocksCapsulesController.this);
					Control focus = vue.getShell().getDisplay().getFocusControl();
					// affichage de l'ecran d'aide principal (+ ses recherches)


					dbc.getValidationStatusMap().removeMapChangeListener(changeListener);
					/*****************************************************************/
					paAideController.configPanel(paramAfficheAide);
					/*****************************************************************/
					dbc.getValidationStatusMap().addMapChangeListener(changeListener);

				}

			} finally {
				VerrouInterface.setVerrouille(false);
				vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
			}
		}
	}
	
	protected void initImageBouton() {
		super.initImageBouton();
		}

	public IStatus validateUI() throws Exception {
		if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
				|| (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
			ctrlTousLesChampsAvantEnregistrementSWT();
			mapperUIToModel.map((SWTStocksCapsules) selectedStocks.getValue(),taStock);
		}
		return null;
	}
	
	public IStatus validateUIField(String nomChamp,Object value) {
		String validationContext = "STOCK_CAPSULES";
		try {
			IStatus s = null;

			if(nomChamp.equals(Const.C_CODE_TITRE_TRANSPORT)) {
				TaTitreTransportDAO dao = new TaTitreTransportDAO(getEm());

				dao.setModeObjet(getDao().getModeObjet());
				TaTitreTransport f = new TaTitreTransport();
				PropertyUtils.setSimpleProperty(f, nomChamp, value);
				s = dao.validate(f,nomChamp,validationContext);

				if(s.getSeverity()!=IStatus.ERROR ){
					f = dao.findByCode((String)value);
					taStock.setTaTitreTransport(f);
//					if(f.getTaPrix()!=null && f.getTaPrix().getTaUnite()!=null)
//						taStock.setUn1Stock(f.getTaPrix().getTaUnite().getCodeUnite());
//					taStock.initQte2(f);
				}
				dao = null;
			}  else 
				if(nomChamp.equals(Const.C_UN1_STOCK)) {
					TaUniteDAO dao = new TaUniteDAO(getEm());

					dao.setModeObjet(getDao().getModeObjet());
					TaUnite f = new TaUnite();
					PropertyUtils.setSimpleProperty(f, Const.C_CODE_UNITE, value);
					s = dao.validate(f,Const.C_CODE_UNITE,validationContext);

					if(s.getSeverity()!=IStatus.ERROR ){
						f = dao.findByCode((String)value);
						if(f!=null)
							taStock.setUn1Stock(f.getCodeUnite());
//						taStock.initQte2(taStock.getTaArticle());
					}
					dao = null;
				}  else 
					if(nomChamp.equals(Const.C_UN2_STOCK)) {
						TaUniteDAO dao = new TaUniteDAO(getEm());

						dao.setModeObjet(getDao().getModeObjet());
						TaUnite f = new TaUnite();
						PropertyUtils.setSimpleProperty(f, Const.C_CODE_UNITE, value);
						s = dao.validate(f,Const.C_CODE_UNITE,validationContext);

						if(s.getSeverity()!=IStatus.ERROR ){
							f = dao.findByCode((String)value);
							if(f!=null)
								taStock.setUn2Stock(f.getCodeUnite());
						}
						dao = null;
					
				}  
				else {
				
				TaStockCapsules u = new TaStockCapsules();
				PropertyUtils.setSimpleProperty(u, nomChamp, value);
				if(((SWTStocksCapsules) selectedStocks.getValue()).getIdStock()!=null) {
					u.setIdStock(((SWTStocksCapsules) selectedStocks.getValue()).getIdStock());
				}

				s = dao.validate(u,nomChamp,validationContext);
				if(s.getSeverity()!=IStatus.ERROR){
					if(nomChamp.equals(Const.C_QTE1_STOCK)) {
						taStock.setQte1Stock((BigDecimal)value);
						((SWTStocksCapsules) selectedStocks.getValue()).setQte1Stock(taStock.getQte1Stock());
//						taStock.initQte2(taStock.getTaArticle());
					}
					PropertyUtils.setSimpleProperty(taStock, nomChamp, value);
					modelStocks.getMapperModelToUI().map(taStock,(SWTStocksCapsules) selectedStocks.getValue());					
				}
			}
			return s;
		} catch (IllegalAccessException e) {
			logger.error("",e);
		} catch (InvocationTargetException e) {
			logger.error("",e);
		} catch (NoSuchMethodException e) {
			logger.error("",e);
		} catch (Exception e) {
			logger.error("",e);
		}
		return null;
	}

	
	@Override
	protected void actEnregistrer() throws Exception {
		EntityTransaction transaction = dao.getEntityManager().getTransaction();

		try {
			boolean declanchementExterne = false;
			if(sourceDeclencheCommandeController==null) {
				declanchementExterne = true;
			}
			if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
					|| (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {

				if(declanchementExterne) {
					ctrlTousLesChampsAvantEnregistrementSWT();
				}				
				dao.begin(transaction);
				if(declanchementExterne) {
					mapperUIToModel.map((SWTStocksCapsules) selectedStocks.getValue(),taStock);
				}
				if((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)){	
					taStock=dao.enregistrerMerge(taStock);
					modelStocks.getListeEntity().add(taStock);
				}
				else { 
					taStock=dao.enregistrerMerge(taStock);					
				}
				dao.commit(transaction);
				transaction = null;
				actRefresh(); //deja present
			}
		} catch (Exception e) {
			logger.error("",e);
			if(transaction!=null && transaction.isActive()) {
				transaction.rollback();
			}
		} finally {
			initEtatBouton();
		}
	}


	public void initEtatComposant() {
		try {
			changeCouleur(vue);
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
		}
	}
	public boolean isUtilise(){
		return ((SWTStocksCapsules)selectedStocks.getValue()).getIdStock()!=null && 
		!dao.recordModifiable(dao.getNomTable(),
				((SWTStocksCapsules)selectedStocks.getValue()).getIdStock());		
	}
	public SWTStocksCapsules getSwtOldStocks() {
		return swtOldStocks;
	}

	public void setSwtOldStocks(SWTStocksCapsules swtOldStocks) {
		this.swtOldStocks = swtOldStocks;
	}

	public void setSwtOldStocks() {
		if (selectedStocks.getValue() != null)
			this.swtOldStocks = SWTStocksCapsules.copy((SWTStocksCapsules) selectedStocks.getValue());
		else {
			if(tableViewer.selectionGrille(0)){
				this.swtOldStocks = SWTStocksCapsules.copy((SWTStocksCapsules) selectedStocks.getValue());
				tableViewer.setSelection(new StructuredSelection(
						(SWTArticle) selectedStocks.getValue()), true);
			}}
	}

	public void setVue(PaStocks vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();

		mapComposantDecoratedField.put(vue.getTfCODE_TITRE_TRANSPORT(), vue.getFieldCODE_TITRE_TRANSPORT());
		mapComposantDecoratedField.put(vue.getTfLIBELLE_STOCK(), vue.getFieldLIBELLE_STOCK());
		mapComposantDecoratedField.put(vue.getTfMOUV_STOCK(), vue.getFieldMOUV_STOCK());
		mapComposantDecoratedField.put(vue.getTfQTE1_STOCK(), vue.getFieldQTE1_STOCK());
//		mapComposantDecoratedField.put(vue.getTfQTE2_STOCK(), vue.getFieldQTE2_STOCK());

	}


	protected void actSelection() throws Exception {
//		try{
//			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().
//			getActivePage().openEditor(new fr.legrain.visualisation.editor.EditorInputSelectionVisualisation(), 
//					fr.legrain.visualisation.editor.EditorSelectionVisualisation.ID);
//			LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
//
//			ParamAfficheVisualisation paramAfficheSelectionVisualisation = new ParamAfficheVisualisation();
//			paramAfficheSelectionVisualisation.setModule("stocks");
//			paramAfficheSelectionVisualisation.setNomClassController(nomClassController);
//			paramAfficheSelectionVisualisation.setNomRequete(Const.C_NOM_VU_STOCK);
//
//			((LgrEditorPart)e).setPanel(((LgrEditorPart)e).getControllerSelection().getVue());
//			((LgrEditorPart)e).getControllerSelection().configPanel(paramAfficheSelectionVisualisation);
//
//		}catch (Exception e) {
//			logger.error("",e);
//		}	
	}

	
	

	public Class getClassModel() {
		return classModel;
	}

	@Override
	protected void sortieChamps() {
		// TODO Raccord de methode auto-genere
		try {
//			if(getFocusCourantSWT().equals(vue.getTfCODE_ARTICLE())){
//				initEtatUnites(vue.getTfCODE_ARTICLE().getText(),!swtOldStocks.getCodeArticle().equals(vue.getTfCODE_ARTICLE().getText()));
//			}
			if(getActiveAide())
				LgrPartListener.getLgrPartListener().getLgrActivePart().setFocus();
		} catch (Exception e) {
			logger.error("",e);
		}
	}

	@Override
	protected void actRefresh() throws Exception {
		//rafraichissement des valeurs dans la grille
		writableList = new WritableList(realm, modelStocks.remplirListe(), classModel);
		tableViewer.setInput(writableList);
		tableViewer.refresh();

		//repositionnement sur la valeur courante
		int idActuel = 0;
		if (taStock!=null) { //enregistrement en cours de modification/insertion
			idActuel = taStock.getIdStock();
		} else if(selectedStocks!=null && (SWTStocksCapsules) selectedStocks.getValue()!=null) {
			idActuel = ((SWTStocksCapsules) selectedStocks.getValue()).getIdStock();
		}

		if(idActuel!=0) {
			tableViewer.setSelection(new StructuredSelection(modelStocks.recherche(Const.C_ID_STOCK
					, idActuel)));
		}else
			tableViewer.selectionGrille(0);				
	}


	public ModelGeneralObjet<SWTStocksCapsules,TaStockCapsulesDAO,TaStockCapsules> getModelStocks() {
		return modelStocks;
	}

	private void initEtatUnites(String codeArticle,boolean initCode){
////		vue.getTfQTE2_STOCK().setVisible(false);
////		vue.getLaQTE2_STOCK().setVisible(false);
////		vue.getTfUN2_STOCK().setVisible(false);
////		vue.getLaUN2_STOCK().setVisible(false);
//		if(!codeArticle.equals("")){
//			SWTArticle article =SWT_IB_TA_ARTICLE.infosArticle(Const.C_CODE_ARTICLE,"'"+codeArticle+"'",null);
//			int i=0;
//			for (SWTPrix e : article.getListePrix()) {
//				if(e.getCODE_UNITE()!=null && !e.getCODE_UNITE().equals("")){
//					i++;
//					if(i==1 && initCode){
//						vue.getTfUN1_STOCK().setText(e.getCODE_UNITE());
//					}
//					if(i==2){
////						vue.getTfQTE2_STOCK().setVisible(true);
////						vue.getLaQTE2_STOCK().setVisible(true);
////						vue.getTfUN2_STOCK().setVisible(true);
//						if(initCode)vue.getTfUN2_STOCK().setText(e.getCODE_UNITE());
////						vue.getLaUN2_STOCK().setVisible(true);
//					}
//				}
//			}
//		}
//
	}

	public boolean focusDansFormulaire(){
		for (Control c : mapComposantChamps.keySet()) {
			if(c.isFocusControl())return true;
		}
		if(getFocusCourantSWT().equals(vue.getTfDATE_STOCK()))
			return true;
		return false;
		
	}
	
	protected class HandlerCalculReport extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException{
			try {								
				Shell s = new Shell(vue.getShell(),LgrShellUtil.styleLgr);		
				PaCalculReportSWT paCalculReport = new PaCalculReportSWT(s,SWT.NULL);
				PaCalculReportController paCalculReportController = new PaCalculReportController(paCalculReport);
				ParamAffiche paramCalculReport= new ParamAffiche();
				paramCalculReport.setFocusSWT(paCalculReport.getLaDATEFIN());
				ParamAfficheSWT paramAfficheSWT = new ParamAfficheSWT();
				paramAfficheSWT.setHauteur(150);
				paramAfficheSWT.setLargeur(400);
				paramAfficheSWT.setTitre("Choix de la date fin des reports de stocks.");
				LgrShellUtil.afficheSWT(paramCalculReport, paramAfficheSWT, paCalculReport, paCalculReportController, s);
				if(paramCalculReport.getFocus()!=null)
					paramCalculReport.getFocusDefaut().requestFocus();

			} catch (Exception e1) {
				logger.error("Erreur : actionPerformed", e1);
			}
			return event;
		}
	}

	public TaStockCapsulesDAO getDao() {
		return dao;
	}
	public boolean changementPageValide(){		
		return (!dao.dataSetEnModif());
	}

	public TaStockCapsules getTaStock() {
		return taStock;
	}

	public void setTaStock(TaStockCapsules taStock) {
		this.taStock = taStock;
	}
}

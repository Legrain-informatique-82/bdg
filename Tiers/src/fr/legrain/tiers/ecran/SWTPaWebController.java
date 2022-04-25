package fr.legrain.tiers.ecran;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.DecoratedField;
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
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.edition.actions.AttributElementResport;
import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.edition.actions.MakeDynamiqueReport;
import fr.legrain.edition.dynamique.FonctionGetInfosXmlAndProperties;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Tiers.SWTTWeb;
import fr.legrain.gestCom.Module_Tiers.SWTWeb;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.DeclencheCommandeControllerEvent;
import fr.legrain.gestCom.librairiesEcran.swt.IDetailController;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideRechercheSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideRechercheControllerSWT;
import fr.legrain.gestCom.librairiesEcran.workbench.AnnuleToutEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDePageEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDeSelectionEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.InfosVerifSaisie;
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
import fr.legrain.lib.gui.aide.ResultAide;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.lib.gui.grille.LgrViewerSupport;
import fr.legrain.libMessageLGR.LgrMess;
import fr.legrain.tiers.TiersPlugin;
import fr.legrain.tiers.dao.TaAdresse;
import fr.legrain.tiers.dao.TaTAdr;
import fr.legrain.tiers.dao.TaTWeb;
import fr.legrain.tiers.dao.TaTWebDAO;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.dao.TaWeb;
import fr.legrain.tiers.dao.TaWebDAO;
import fr.legrain.tiers.divers.Impression;
import fr.legrain.tiers.editor.EditorInputTypeWeb;
import fr.legrain.tiers.editor.EditorInputWeb;
import fr.legrain.tiers.editor.EditorTypeWeb;
import fr.legrain.tiers.editor.EditorWeb;

public class SWTPaWebController extends JPABaseControllerSWTStandard
implements RetourEcranListener,IDetailController {

	static Logger logger = Logger.getLogger(SWTPaWebController.class.getName());
	private PaWebSWT vue = null;
	//private SWT_IB_TA_WEB ibTaTable = new SWT_IB_TA_WEB();
	private TaWebDAO dao = null;//new TaWebDAO();


	private Object ecranAppelant = null;
	private SWTWeb swtWeb;
	private SWTWeb swtOldWeb;
	private Realm realm;
	private DataBindingContext dbc;
	//private ModelTypeTiers modelWeb;

	private Class classModel = SWTWeb.class;
	//private ModelGeneral<SWTWeb> modelWeb = new ModelGeneral<SWTWeb>(ibTaTable.getFIBQuery(),classModel);
	private ModelGeneralObjet<SWTWeb,TaWebDAO,TaWeb> modelWeb = null;//new ModelGeneralObjet<SWTWeb,TaWebDAO,TaWeb>(dao,classModel);

	public static final String C_COMMAND_OPEN_URL_ID = "fr.legrain.gestionCommerciale.tiers.web.openurl";
	private HandlerOpenUrl handlerOpenUrl = new HandlerOpenUrl();

	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private IObservableValue selectedWeb;
	private String[] listeChamp;
	private String nomClassController = this.getClass().getSimpleName();

	private TaTiers masterEntity = null;
	private TaTiersDAO masterDAO = null;

	private LgrDozerMapper<SWTWeb,TaWeb> mapperUIToModel  = new LgrDozerMapper<SWTWeb,TaWeb>();
	private TaWeb taWeb = null;

	private MapChangeListener changeListener = new MapChangeListener();

	private Integer idTiers = null;

	public SWTPaWebController(PaWebSWT vue) {
		this(vue,null);
	}

	public SWTPaWebController(PaWebSWT vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaWebDAO(getEm());
		modelWeb = new ModelGeneralObjet<SWTWeb,TaWebDAO,TaWeb>(dao,classModel);
		setVue(vue);

		// a faire avant l'initialisation du Binding
		vue.getGrille().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setSwtOldWeb();
			}
		});
		vue.getShell().addShellListener(this);

		// Branchement action annuler : empeche la fermeture automatique de la
		// fenetre sur ESC
		vue.getShell().addTraverseListener(new Traverse());

		initController();
	}

	private void initController() {
		try {
			setGrille(vue.getGrille());
			initSashFormWeight();
			//setIbTaTableStandard(ibTaTable);
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
			Menu[] tabPopups = new Menu[] { popupMenuFormulaire,
					popupMenuGrille };
			this.initPopupAndButtons(mapActions, tabPopups);
			vue.getPaCorpsFormulaire().setMenu(popupMenuFormulaire);
			vue.getPaGrille().setMenu(popupMenuGrille);

			initEtatBouton();
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaTiersController", e);
		}
	}

	@Override
	protected void initImageBouton() {
		super.initImageBouton();
		String imageCarte = "/icons/world_link.png";
		vue.getBtnOpenUrl().setImage(TiersPlugin.getImageDescriptor(imageCarte).createImage());
		vue.layout(true);
	}

	protected void initEtatBouton() {
		initEtatBouton(IHMmodel());

		boolean trouve = contientDesEnregistrement(IHMmodel());
		switch (daoStandard.getModeObjet().getMode()) {
		case C_MO_INSERTION:
			enableActionAndHandler(C_COMMAND_OPEN_URL_ID,false);
			break;
		case C_MO_EDITION:
			enableActionAndHandler(C_COMMAND_OPEN_URL_ID,false);
			break;
		case C_MO_CONSULTATION:
			enableActionAndHandler(C_COMMAND_OPEN_URL_ID,trouve);
			break;
		default:
			break;
		}
	}

	/**
	 * Creation des objet pour l'interface, a partir de l'entite principale.<br>
	 * @return
	 */
	public List<SWTWeb> IHMmodel() {
		LinkedList<TaWeb> ldao = new LinkedList<TaWeb>();
		LinkedList<SWTWeb> lswt = new LinkedList<SWTWeb>();

		if(masterEntity!=null && !masterEntity.getTaWebs().isEmpty()) {

			ldao.addAll(masterEntity.getTaWebs());

			LgrDozerMapper<TaWeb,SWTWeb> mapper = new LgrDozerMapper<TaWeb,SWTWeb>();
			for (TaWeb o : ldao) {
				SWTWeb t = null;
				t = (SWTWeb) mapper.map(o, SWTWeb.class);
				lswt.add(t);
			}

		}
		return lswt;
	}

	public void bind(PaWebSWT paWebSWT) {
		try {
			//modelWeb = new ModelTypeTiers(ibTaTable);
			//			 modelWeb = new ModelGeneral<SWTWeb>(ibTaTable.getFIBQuery(),classModel);
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			tableViewer = new LgrTableViewer(paWebSWT.getGrille());
			tableViewer.createTableCol(classModel,paWebSWT.getGrille(), nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE,0);
			listeChamp = tableViewer.setListeChampGrille(nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE);

			//			ObservableListContentProvider viewerContent = new ObservableListContentProvider();
			//			tableViewer.setContentProvider(viewerContent);
			//
			//			IObservableMap[] attributeMaps = BeansObservables.observeMaps(
			//					viewerContent.getKnownElements(), classModel,
			//					listeChamp);
			//
			//			tableViewer.setLabelProvider(new ObservableMapLabelProvider(attributeMaps));
			//			List<SWTWeb> lswt = IHMmodel();
			//			//writableList = new WritableList(realm, modelWeb.remplirListe(), classModel);
			//			writableList = new WritableList(realm, lswt, classModel);
			//			tableViewer.setInput(writableList);

			// Set up data binding.
			LgrViewerSupport.bind(tableViewer, 
					new WritableList(IHMmodel(), classModel),
					BeanProperties.values(listeChamp)
			);

			selectedWeb = ViewersObservables.observeSingleSelection(tableViewer);

			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			tableViewer.selectionGrille(0);

			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);

			selectedWeb.getValue();
			bindingFormMaitreDetail(dbc, realm, selectedWeb,classModel);
			changementDeSelection();
			selectedWeb.addChangeListener(new IChangeListener() {

				public void handleChange(ChangeEvent event) {
					changementDeSelection();
				}

			});

		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("", e);
		}
	}

	private void changementDeSelection() {
		if(selectedWeb!=null && selectedWeb.getValue()!=null) {
			if(((SWTWeb) selectedWeb.getValue()).getIdWeb()!=null) {
				taWeb = dao.findById(((SWTWeb) selectedWeb.getValue()).getIdWeb());
			}
			//null a tester ailleurs, car peut etre null en cas d'insertion
			fireChangementDeSelection(new ChangementDeSelectionEvent(SWTPaWebController.this));
		}
	}

	public Composite getVue() {
		return vue;
	}

	public ResultAffiche configPanel(ParamAffiche param) {
		if (param != null) {
			//Map map=ibTaTable.getParamWhereSQL();
			Map<String,String[]> map = dao.getParamWhereSQL();
			//ibTaTable.ouvreDataset();
			if (((ParamAfficheWeb) param).getFocusDefautSWT() != null && !((ParamAfficheWeb) param).getFocusDefautSWT().isDisposed())
				((ParamAfficheWeb) param).getFocusDefautSWT().setFocus();
			else
				((ParamAfficheWeb) param).setFocusDefautSWT(vue
						.getGrille());
			vue.getLaTitreFormulaire().setText(
					((ParamAfficheWeb) param).getTitreFormulaire());
			vue.getLaTitreGrille().setText(
					((ParamAfficheWeb) param).getTitreGrille());
			vue.getLaTitreFenetre().setText(
					((ParamAfficheWeb) param).getSousTitre());


			if (param.getEcranAppelant() != null) {
				ecranAppelant = param.getEcranAppelant();
			}

			if(tableViewer==null) {
				//databinding pas encore realise
				bind(vue);
				tableViewer.tri(classModel, nomClassController,	Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			} else {
				try {
					taWeb=null;
					selectedWeb.setValue(null);
					actRefresh();
				} catch (Exception e) {
					logger.error("",e);
				}
			}

			//			tableViewer.tri(classModel, nomClassController,	Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			VerrouInterface.setVerrouille(false);
			setSwtOldWeb();

			if (param.getModeEcran() != null
					&& param.getModeEcran().compareTo(EnumModeObjet.C_MO_INSERTION) == 0) {
				try {
					actInserer();
				} catch (Exception e) {
					vue.getLaMessage().setText(e.getMessage());
					logger.error("", e);
				}
			} else {
				initEtatBouton();
			}

			//param.setFocus(ibTaTable.getFModeObjet().getFocusCourant());
		}
		return null;
	}
	
	protected void initVerifySaisie() {
		if (mapInfosVerifSaisie == null)
			mapInfosVerifSaisie = new HashMap<Control, InfosVerifSaisie>();
		
		mapInfosVerifSaisie.put(vue.getTfADRESSE_WEB(), new InfosVerifSaisie(new TaWeb(),Const.C_ADRESSE_WEB,null));
		mapInfosVerifSaisie.put(vue.getTfCODE_T_WEB(), new InfosVerifSaisie(new TaTWeb(),Const.C_CODE_T_WEB,null));

		initVerifyListener(mapInfosVerifSaisie, dao);
	}

	protected void initComposantsVue() throws ExceptLgr {
	}

	protected void initMapComposantChamps() {
		if (mapComposantChamps == null)
			mapComposantChamps = new LinkedHashMap<Control, String>();

		if (listeComposantFocusable == null)
			listeComposantFocusable = new ArrayList<Control>();
		listeComposantFocusable.add(vue.getGrille());

		vue.getTfADRESSE_WEB().setToolTipText(Const.C_ADRESSE_WEB);
		vue.getTfCODE_T_WEB().setToolTipText(Const.C_CODE_T_WEB);

		mapComposantChamps.put(vue.getTfADRESSE_WEB(),Const.C_ADRESSE_WEB);
		mapComposantChamps.put(vue.getTfCODE_T_WEB(),Const.C_CODE_T_WEB);



		for (Control c : mapComposantChamps.keySet()) {
			listeComposantFocusable.add(c);
		}

		listeComposantFocusable.add(vue.getBtnOpenUrl());

		listeComposantFocusable.add(vue.getBtnEnregistrer());
		listeComposantFocusable.add(vue.getBtnInserer());
		listeComposantFocusable.add(vue.getBtnModifier());
		listeComposantFocusable.add(vue.getBtnSupprimer());
		listeComposantFocusable.add(vue.getBtnFermer());
		listeComposantFocusable.add(vue.getBtnAnnuler());
		listeComposantFocusable.add(vue.getBtnImprimer());

		if (mapInitFocus == null)
			mapInitFocus = new LinkedHashMap<ModeObjet.EnumModeObjet, Control>();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION, vue
				.getTfADRESSE_WEB());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue
				.getTfADRESSE_WEB());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION, vue
				.getGrille());

		activeModifytListener();
	}

	protected void initActions() {
		mapCommand = new HashMap<String, IHandler>();

		mapCommand.put(C_COMMAND_OPEN_URL_ID, handlerOpenUrl);

		mapCommand.put(C_COMMAND_GLOBAL_MODIFIER_ID, handlerModifier);
		mapCommand.put(C_COMMAND_GLOBAL_ANNULER_ID, handlerAnnuler);
		mapCommand.put(C_COMMAND_GLOBAL_ENREGISTRER_ID, handlerEnregistrer);
		mapCommand.put(C_COMMAND_GLOBAL_INSERER_ID, handlerInserer);
		mapCommand.put(C_COMMAND_GLOBAL_SUPPRIMER_ID, handlerSupprimer);
		mapCommand.put(C_COMMAND_GLOBAL_REFRESH_ID, handlerRefresh);
		mapCommand.put(C_COMMAND_GLOBAL_AIDE_ID, handlerAide);
		mapCommand.put(C_COMMAND_GLOBAL_FERMER_ID, handlerFermer);
		mapCommand.put(C_COMMAND_GLOBAL_IMPRIMER_ID, handlerImprimer);

		mapCommand.put(C_COMMAND_GLOBAL_PRECEDENT_ID, handlerPrecedent);
		mapCommand.put(C_COMMAND_GLOBAL_SUIVANT_ID, handlerSuivant);

		initFocusCommand(String.valueOf(this.hashCode()),listeComposantFocusable,mapCommand);

		if (mapActions == null)
			mapActions = new LinkedHashMap<Button, Object>();

		mapActions.put(vue.getBtnOpenUrl(), C_COMMAND_OPEN_URL_ID);

		mapActions.put(vue.getBtnAnnuler(), C_COMMAND_GLOBAL_ANNULER_ID);
		mapActions.put(vue.getBtnEnregistrer(), C_COMMAND_GLOBAL_ENREGISTRER_ID);
		mapActions.put(vue.getBtnInserer(), C_COMMAND_GLOBAL_INSERER_ID);
		mapActions.put(vue.getBtnModifier(), C_COMMAND_GLOBAL_MODIFIER_ID);
		mapActions.put(vue.getBtnSupprimer(), C_COMMAND_GLOBAL_SUPPRIMER_ID);
		mapActions.put(vue.getBtnFermer(), C_COMMAND_GLOBAL_FERMER_ID);
		mapActions.put(vue.getBtnImprimer(), C_COMMAND_GLOBAL_IMPRIMER_ID);

		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID, C_COMMAND_GLOBAL_REFRESH_ID };
		mapActions.put(null, tabActionsAutres);
	}

	public SWTPaWebController getThis() {
		return this;
	}

	@Override
	public boolean onClose() throws ExceptLgr {
		boolean retour = true;
		VerrouInterface.setVerrouille(true);
		//switch (ibTaTable.getFModeObjet().getMode()) {
		switch (dao.getModeObjet().getMode()) {
		case C_MO_INSERTION:
		case C_MO_EDITION:
			if (MessageDialog.openQuestion(vue.getShell(), MessagesEcran
					.getString("Message.Attention"), MessagesEcran
					.getString("Web.Message.Enregistrer"))) {

				try {
					actEnregistrer();
				} catch (Exception e) {
					vue.getLaMessage().setText(e.getMessage());
					logger.error("", e);
				}
			} else {
				fireAnnuleTout(new AnnuleToutEvent(this,true));
			}
			//ibTaTable.annuler();

			break;
		case C_MO_CONSULTATION:
			break;
		default:
			break;
		}


		if (retour) {
			if (ecranAppelant instanceof SWTPaAideControllerSWT) {
				setListeEntity(getModelWeb().remplirListe());
				dao.initValeurIdTable(taWeb);
				fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
						dao.getChampIdEntite(), dao.getValeurIdTable(),
						selectedWeb.getValue())));

				retour = true;
			}
		}
		return retour;
	}

	public void retourEcran(final RetourEcranEvent evt) {
		if (evt.getRetour() != null
				&& (evt.getSource() instanceof SWTPaAideControllerSWT)) {
			if (getFocusAvantAideSWT() instanceof Text) {
				//				try {
				//					((Text) getFocusAvantAideSWT()).setText(((ResultAffiche) evt.getRetour()).getResult());					
				//					ctrlUnChampsSWT(getFocusAvantAideSWT());
				//				} catch (Exception e) {
				//					logger.error("",e);
				//					vue.getLaMessage().setText(e.getMessage());
				//				}
				try {
					((Text) getFocusAvantAideSWT()).setText(((ResultAffiche) evt.getRetour()).getResult());	
					if(getFocusAvantAideSWT().equals(vue.getTfCODE_T_WEB())){
						TaTWeb u = null;
						TaTWebDAO t = new TaTWebDAO(getEm());
						u = t.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
						t = null;
						taWeb.setTaTWeb(u);
					}
					ctrlUnChampsSWT(getFocusAvantAideSWT());
				} catch (Exception e) {
					logger.error("",e);
					vue.getLaMessage().setText(e.getMessage());
				}
			}
			if (getFocusAvantAideSWT() instanceof Table) {
				if (getFocusAvantAideSWT() == vue.getGrille()) {
					//ibTaTable.lgrLocateID(((ResultAffiche) evt.getRetour()).getIdResult());
					if(((ResultAffiche) evt.getRetour()).getSelection()!=null)
						tableViewer.setSelection(((ResultAffiche) evt.getRetour()).getSelection(),true);
				}
			}
		} else if (evt.getRetour() != null) {
			if (getFocusAvantAideSWT() instanceof Table) {
				if (getFocusAvantAideSWT() == vue.getGrille()) {
					//ibTaTable.lgrLocateID(((ResultAffiche) evt.getRetour()).getIdResult());
					if(((ResultAffiche) evt.getRetour()).getSelection()!=null)
						tableViewer.setSelection(((ResultAffiche) evt.getRetour()).getSelection(),true);
				}
			}
		}
		super.retourEcran(evt);
	}

	protected class HandlerOpenUrl extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException{
			try {
				actOpenUrl();
			} catch (Exception e1) {
				logger.error("Erreur : actionPerformed", e1);
			}
			return event;
		}
	}

	protected void actOpenUrl() {
		if(taWeb!=null && taWeb.getAdresseWeb()!=null) {
			String defaultProtocol = "http://";
			String url = taWeb.getAdresseWeb();
			if(!url.startsWith(defaultProtocol)) {
				url = defaultProtocol+url;
			}
			final String finalURL = url;
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				public void run() {
					try {
						PlatformUI.getWorkbench().getBrowserSupport()
						.createBrowser(
								IWorkbenchBrowserSupport.AS_EDITOR,
								"myId",
								finalURL,
								finalURL
						).openURL(new URL(finalURL));

					} catch (PartInitException e) {
						logger.error("",e);
					} catch (MalformedURLException e) {
						logger.error("",e);

					}
				}	
			});
		}
	}

	@Override
	protected void actInserer() throws Exception {
		try {
			boolean continuer=true;
			VerrouInterface.setVerrouille(true);
			masterDAO.verifAutoriseModification(masterEntity);
			setSwtOldWeb();
			if(LgrMess.isDOSSIER_EN_RESEAU()){
				DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
				fireDeclencheCommandeController(e);
				continuer=masterDAO.dataSetEnModif();
			}
			if(continuer){
				swtWeb = new SWTWeb();
				swtWeb.setIdTiers(idTiers);
				dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_INSERTION);
				taWeb = new TaWeb();
				List l = IHMmodel();
				l.add(swtWeb);
				writableList = new WritableList(realm, l, classModel);
				tableViewer.setInput(writableList);
				tableViewer.refresh();
				tableViewer.setSelection(new StructuredSelection(swtWeb));
				initEtatBouton();

				try {
					DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
					fireDeclencheCommandeController(e);
				} catch (Exception e) {
					logger.error("",e);
				}
			}
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		} finally {
			initEtatComposant();
			VerrouInterface.setVerrouille(false);
		}
	}

	@Override
	protected void actModifier() throws Exception {
		try {
			boolean continuer=true;
			setSwtOldWeb();
			if(LgrMess.isDOSSIER_EN_RESEAU()){
				DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
				fireDeclencheCommandeController(e);
				continuer=masterDAO.dataSetEnModif();
			}
			if(continuer){
				for (TaWeb p : masterEntity.getTaWebs()) {
					if(p.getIdWeb()==((SWTWeb) selectedWeb.getValue()).getIdWeb()) {
						taWeb = p;
//						if(LgrMess.isDOSSIER_EN_RESEAU())actRefresh();
					}
				}
				masterDAO.verifAutoriseModification(masterEntity);
				DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
				fireDeclencheCommandeController(e);
				dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_EDITION);
				initEtatBouton();
			}
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		}
	}

	@Override
	protected void actSupprimer() throws Exception {
		EntityTransaction transaction = dao.getEntityManager().getTransaction();		
		try {
			boolean continuer=true;
			VerrouInterface.setVerrouille(true);
			if(isUtilise())MessageDialog.openInformation(vue.getShell(), MessagesEcran
					.getString("Message.Attention"), MessagesEcran
					.getString("Message.suppression"));
			else
				if(LgrMess.isDOSSIER_EN_RESEAU()){
					DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
					fireDeclencheCommandeController(e);
					continuer=masterDAO.dataSetEnModif();
				}
				
				if(continuer){
					setMasterEntity(masterDAO.findById(masterEntity.getIdTiers()));
					if (MessageDialog.openConfirm(vue.getShell(), MessagesEcran
							.getString("Message.Attention"), MessagesEcran
							.getString("Web.Message.Supprimer"))) {				
						dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_SUPPRESSION);
						try {
							DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
							fireDeclencheCommandeController(e);
						} catch (Exception e) {
							logger.error("",e);
						}
						for (TaWeb p : masterEntity.getTaWebs()) {
							if(p.getIdWeb()==((SWTWeb) selectedWeb.getValue()).getIdWeb()) {
								taWeb = p;
							}
						}

						dao.begin(transaction);
						dao.supprimer(taWeb);
						taWeb.setTaTiers(null);
						masterEntity.removeWeb(taWeb);
						Object suivant=tableViewer.getElementAt(tableViewer.getTable().getSelectionIndex()+1);
						taWeb=masterEntity.getTaWeb();
						dao.commit(transaction);
						if(suivant!=null)tableViewer.setSelection(new StructuredSelection(suivant),true);
						else tableViewer.selectionGrille(0);
						try {
							DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_ENREGISTRER_ID);
							fireDeclencheCommandeController(e);
						} catch (Exception e) {
							logger.error("",e);
						}	
						actRefresh();		
						initEtatBouton();

					}
			}
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		} finally {
			if(transaction!=null && transaction.isActive()) {
				transaction.rollback();
			}
			initEtatBouton();
			VerrouInterface.setVerrouille(false);
		}
	}

	@Override
	protected void actFermer() throws Exception {
		// (controles de sortie et fermeture de la fenetre) => onClose()
		if(onClose())
			closeWorkbenchPart();
	}

	@Override
	protected void actAnnuler() throws Exception {
		// // verifier si l'objet est en modification ou en consultation
		// // si modification ou insertion, alors demander si annulation des
		// // modifications si ok, alors annulation si pas ok, alors arreter le
		// processus d'annulation
		// // si consultation declencher l'action "fermer".
		try {
			VerrouInterface.setVerrouille(true);
			// //InputVerifier inputVerifier =
			// getFocusCourant().getInputVerifier();
			// //getFocusCourant().setInputVerifier(null);
			//switch (ibTaTable.getFModeObjet().getMode()) {
			switch (dao.getModeObjet().getMode()) {
			case C_MO_INSERTION:
				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("Web.Message.Annuler")))) {
					remetTousLesChampsApresAnnulationSWT(dbc);
					actRefresh();
					dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
					hideDecoratedFields();
					if(!annuleToutEnCours) {
						fireAnnuleTout(new AnnuleToutEvent(this));
					}
				}
				initEtatBouton();
				break;
			case C_MO_EDITION:
				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("Web.Message.Annuler")))) {
					//int rang = getGrille().getSelectionIndex();
					//int rang = modelWeb.getListeObjet().indexOf(selectedWeb.getValue());
					int rang =((WritableList)tableViewer.getInput()).indexOf(selectedWeb.getValue());
					remetTousLesChampsApresAnnulationSWT(dbc);
					List<SWTWeb> l = IHMmodel();
					if(rang!=-1)
						l.set(rang, swtOldWeb);

					writableList = new WritableList(realm, l, classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.setSelection(new StructuredSelection(swtOldWeb), true);

					dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
					hideDecoratedFields();
					if(!annuleToutEnCours) {
						fireAnnuleTout(new AnnuleToutEvent(this));
					}
				}
				initEtatBouton();

				break;
			case C_MO_CONSULTATION:
				//				actionFermer.run();
				fireChangementDePage(new ChangementDePageEvent(this,ChangementDePageEvent.DEBUT));
				break;
			default:
				break;
			}
			// getFocusCourant().setInputVerifier(inputVerifier);
			// initEtatBouton();
		} finally {
			VerrouInterface.setVerrouille(false);
		}
	}

	@Override
	protected void actImprimer() throws Exception {


		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();

		String nomChampIdTable =  dao.getChampIdTable();

		FonctionGetInfosXmlAndProperties fonctionGetInfosXmlAndProperties = new FonctionGetInfosXmlAndProperties(mapperUIToModel);
		fonctionGetInfosXmlAndProperties.cleanValueMapAttributeTable();

		fonctionGetInfosXmlAndProperties.setValueMapAttributeTable(TaWeb.class.getSimpleName()+".head");
		fonctionGetInfosXmlAndProperties.setValueMapAttributeTable(TaWeb.class.getSimpleName()+".detail");

		LinkedHashMap<String,AttributElementResport> mapAttributeTablHead = fonctionGetInfosXmlAndProperties.getMapAttributeTablHead(); 
		LinkedHashMap<String,AttributElementResport> mapAttributeTablDetail = fonctionGetInfosXmlAndProperties.getMapAttributeTablDetail();


		Collection<TaWeb> collectionTaWeb = masterEntity.getTaWebs();

		fonctionGetInfosXmlAndProperties.findInfosFileXml(TaWeb.class.getName(),SWTWeb.class.getName(),
				listeChamp,"mapping");
		fonctionGetInfosXmlAndProperties.getInfosObjetJPA(taWeb);

		ConstEdition constEdition = new ConstEdition(); 
		Impression impression = new Impression(constEdition,taWeb,collectionTaWeb,nomChampIdTable,taWeb.getIdWeb());
		String nomDossier = null;

		int nombreLine = collectionTaWeb.size();


		if(nombreLine==0){
			MessageDialog.openError(vue.getShell(), "ALTER",
			"Il n'y a rien dans Cette Edition !");
		}
		else{
			if(taInfoEntreprise.getIdInfoEntreprise()==0){
				nomDossier = ConstEdition.INFOS_VIDE;
			}
			else{
				nomDossier = taInfoEntreprise.getNomInfoEntreprise();	
			}

			constEdition.addValueList(tableViewer, nomClassController);


			/**
			 * pathFileReport ==> le path de ficher de edition dynamique
			 */
			String folderEditionDynamique = Const.C_RCP_INSTANCE_LOCATION+"/"+Const.C_NOM_PROJET_TMP+"/"+TaWeb.class.getSimpleName();
			constEdition.makeFolderEditions(folderEditionDynamique);
			Path pathFileReport = new Path(folderEditionDynamique+"/"+Const.C_NOM_VU_WEB+".rptdesign");
			final String pathFileReportDynamic = pathFileReport.toPortableString();

			MakeDynamiqueReport DynamiqueReport = new MakeDynamiqueReport(constEdition.getNameTableEcran(),
					constEdition.getNameTableBDD(),pathFileReportDynamic,Const.C_NOM_VU_WEB,
					ConstEdition.PAGE_ORIENTATION_LANDSCAPE,nomDossier); 

			DynamiqueReport.setSimpleNameEntity(TaTWeb.class.getSimpleName());
			/**************************************************************/
			DynamiqueReport.setFonctionGetInfosXml(fonctionGetInfosXmlAndProperties);
			DynamiqueReport.setNomObjet(TaWeb.class.getSimpleName());
			/**************************************************************/

			Map<String, AttributElementResport> attribuGridHeader = new LinkedHashMap<String, AttributElementResport>();
			String nameHeaderTitle = ConstEditionTiers.TITLE_EDITION_TIERS_WEB;
			attribuGridHeader.put(nameHeaderTitle, new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
					ConstEdition.FONT_SIZE_XX_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
					ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
					,""));
			String nameSousHeaderTitle = ConstEditionTiers.SOUS_TITLE_EDITION_TIERS_WEB;
			attribuGridHeader.put(nameSousHeaderTitle, new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
					ConstEdition.FONT_SIZE_X_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
					ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
					,ConstEdition.COLOR_GRAY));

			ConstEdition.CONTENT_COMMENTS = ConstEditionTiers.COMMENTAIRE_EDITION_DEFAUT;
			DynamiqueReport.initializeBuildDesignReportConfig();
			DynamiqueReport.makePageMater("1", "1", "1", "1", "100");
			DynamiqueReport.makeReportHeaderGrid(3,5,100,ConstEdition.UNITS_PERCENTAGE,attribuGridHeader);


			//			DynamiqueReport.makeReportTableDB(100,ConstEdition.UNITS_PERCENTAGE,
			//					Const.C_NOM_VU_WEB,attribuTabHeader,attribuTabDetail,1,1,2,5,"30");

			DynamiqueReport.biuldTableReport("100", ConstEdition.UNITS_PERCENTAGE, 
					Const.C_NOM_VU_WEB,1,1,2,"40", mapAttributeTablHead, mapAttributeTablDetail);

			DynamiqueReport.savsAsDesignHandle();
			impression.imprimer(true,pathFileReportDynamic,null,"Webs",TaWeb.class.getSimpleName(),false);

		}

	}

	@Override
	protected boolean aideDisponible() {
		boolean result = false;
		//switch ((getThis().ibTaTable.getFModeObjet().getMode())) {
		switch ((getThis().dao.getModeObjet().getMode())) {
		case C_MO_CONSULTATION:
			if(getFocusCourantSWT().equals(vue.getGrille()))
				result = true;
			break;
		case C_MO_EDITION:
		case C_MO_INSERTION:
			if(getFocusCourantSWT().equals(vue.getTfCODE_T_WEB()))
				result = true;
			break;
		default:
			break;
		}
		return result;
	}

	@Override
	protected void actPrecedent() throws Exception {
		ChangementDePageEvent evt = new ChangementDePageEvent(this,
				ChangementDePageEvent.PRECEDENT);
		fireChangementDePage(evt);
	}

	@Override
	protected void actSuivant() throws Exception {
		ChangementDePageEvent evt = new ChangementDePageEvent(this,
				ChangementDePageEvent.SUIVANT);
		fireChangementDePage(evt);
	}

	@Override
	protected void actAide() throws Exception {
		actAide(null);
	}

	@Override
	protected void actAide(String message) throws Exception {
		if(aideDisponible()){
			try {
				VerrouInterface.setVerrouille(true);
				vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
				ParamAfficheAideRechercheSWT paramAfficheAideRecherche = new ParamAfficheAideRechercheSWT();
				//paramAfficheAideRecherche.setDb(getThis().getIbTaTable().getFIBBase());
				paramAfficheAideRecherche.setMessage(message);
				// Creation de l'ecran d'aide principal
				Shell s = new Shell(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),LgrShellUtil.styleLgr);
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
				//SWTBaseControllerSWTStandard controllerEcranCreation = null;
				JPABaseControllerSWTStandard controllerEcranCreation = null;
				ParamAffiche parametreEcranCreation = null;
				IEditorPart editorCreation = null;
				String editorCreationId = null;
				IEditorInput editorInputCreation = null;
				Shell s2 = new Shell(s, LgrShellUtil.styleLgr);

				//switch ((getThis().ibTaTable.getFModeObjet().getMode())) {
				switch ((getThis().dao.getModeObjet().getMode())) {
				case C_MO_CONSULTATION:
					if(getFocusCourantSWT().equals(vue.getGrille())){
//						PaWebSWT paWebSWT = new PaWebSWT(s2,SWT.NULL);
//						SWTPaWebController swtPaWebController = new SWTPaWebController(paWebSWT);
//						
//						paramAfficheAideRecherche.setForceAffichageAideRemplie(true);
//						paramAfficheAideRecherche.setAfficheDetail(false);
//
//						editorCreationId = EditorWeb.ID;
//						editorInputCreation = new EditorInputWeb();
//
//						ParamAfficheWeb paramAfficheWeb = new ParamAfficheWeb();
//						//paramAfficheAideRecherche.setQuery(swtPaWebController.getIbTaTable().getFIBQuery());
//						paramAfficheAideRecherche.setJPQLQuery(new TaTWebDAO(getEm()).getJPQLQuery());
//						paramAfficheWeb.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//						paramAfficheWeb.setEcranAppelant(paAideController);
//						controllerEcranCreation = swtPaWebController;
//						parametreEcranCreation = paramAfficheWeb;
//
//						paramAfficheAideRecherche.setTypeEntite(TaWeb.class);
//						paramAfficheAideRecherche.setChampsRecherche(Const.C_ADRESSE_WEB);
//						paramAfficheAideRecherche.setDebutRecherche(vue.getTfADRESSE_WEB().getText());
//						paramAfficheAideRecherche.setControllerAppelant(getThis());
//						paramAfficheAideRecherche.setModel(swtPaWebController.getModelWeb());
//						paramAfficheAideRecherche.setTypeObjet(swtPaWebController.getClassModel());
//						//paramAfficheAideRecherche.setChampsIdentifiant(swtPaWebController.getIbTaTable().champIdTable);
//						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_WEB);
					}
					break;
				case C_MO_EDITION:
				case C_MO_INSERTION:
					try {
						if(getFocusCourantSWT().equals(vue.getTfCODE_T_WEB())){
							PaTypeWebSWT paTypeWebSWT = new PaTypeWebSWT(s2,SWT.NULL);
							SWTPaTypeWebController swtPaTypeWebController = new SWTPaTypeWebController(paTypeWebSWT);

							editorCreationId = EditorTypeWeb.ID;
							editorInputCreation = new EditorInputTypeWeb();


							ParamAfficheTypeWeb paramAfficheTypeWeb = new ParamAfficheTypeWeb();
							paramAfficheAideRecherche.setJPQLQuery(new TaTWebDAO(getEm()).getJPQLQuery());
							paramAfficheTypeWeb.setModeEcran(EnumModeObjet.C_MO_INSERTION);
							paramAfficheTypeWeb.setEcranAppelant(paAideController);
							controllerEcranCreation = swtPaTypeWebController;
							parametreEcranCreation = paramAfficheTypeWeb;

							paramAfficheAideRecherche.setTypeEntite(TaTWeb.class);
							paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_T_WEB);
							paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_T_WEB().getText());
							paramAfficheAideRecherche.setControllerAppelant(getThis());
							paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTWeb,TaTWebDAO,TaTWeb>(SWTTWeb.class,dao.getEntityManager()));

							paramAfficheAideRecherche.setTypeObjet(swtPaTypeWebController.getClassModel());

							paramAfficheAideRecherche.setChampsIdentifiant(swtPaTypeWebController.getDao().getChampIdTable());
						}
					} catch (Exception e1) {
						// TODO: handle exception
						logger.error(""+e1);
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
					paramAfficheAideRecherche
					.setFocusSWT(((PaAideRechercheSWT) paAideRechercheController1
							.getVue()).getTfChoix());
					paramAfficheAideRecherche
					.setRefCreationSWT(controllerEcranCreation);
					paramAfficheAideRecherche.setEditorCreation(editorCreation);
					paramAfficheAideRecherche.setEditorCreationId(editorCreationId);
					paramAfficheAideRecherche.setEditorInputCreation(editorInputCreation);
					paramAfficheAideRecherche
					.setParamEcranCreation(parametreEcranCreation);
					paramAfficheAideRecherche.setShellCreation(s2);
					paAideRechercheController1
					.configPanel(paramAfficheAideRecherche);
					// paramAfficheAideRecherche.setFocusDefaut(paAideRechercheController1.getVue().getTfChoix());

					// Ajout d'une recherche
					paAideController.addRecherche(paAideRechercheController1,
							paramAfficheAideRecherche.getTitreRecherche());

					// Parametrage de l'ecran d'aide principal
					ParamAfficheAideSWT paramAfficheAideSWT = new ParamAfficheAideSWT();
					ParamAfficheAide paramAfficheAide = new ParamAfficheAide();

					// enregistrement pour le retour de l'ecran d'aide
					paAideController.addRetourEcranListener(getThis());
					Control focus = vue.getShell().getDisplay().getFocusControl();
					// affichage de l'ecran d'aide principal (+ ses recherches)

					dbc.getValidationStatusMap().removeMapChangeListener(
							changeListener);
					//LgrShellUtil.afficheAideSWT(paramAfficheAide, null, paAide,paAideController, s);
					/*****************************************************************/
					paAideController.configPanel(paramAfficheAide);
					/*****************************************************************/
					dbc.getValidationStatusMap().addMapChangeListener(
							changeListener);

				}

			} finally {
				VerrouInterface.setVerrouille(false);
				vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
			}
		}
	}

	public IStatus validateUI() throws Exception {
		if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
				|| (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
			ctrlTousLesChampsAvantEnregistrementSWT();
		}
		return null;
	}

	public IStatus validateUIField(String nomChamp,Object value) {
		String validationContext = "WEB";
		try {
			IStatus s = null;

			if(nomChamp.equals(Const.C_CODE_T_WEB)) {
				TaTWebDAO dao = new TaTWebDAO(getEm());

				dao.setModeObjet(getDao().getModeObjet());
				TaTWeb f = new TaTWeb();
				PropertyUtils.setSimpleProperty(f, nomChamp, value);
				s = dao.validate(f,nomChamp,validationContext);

				if(s.getSeverity()!=IStatus.ERROR) {
					f = dao.findByCode((String)value);
					taWeb.setTaTWeb(f);
				}
				dao = null;
			} else {
				TaWeb u = new TaWeb();
				u.setTaTiers(masterEntity);
				PropertyUtils.setSimpleProperty(u, nomChamp, value);
				s = dao.validate(u,nomChamp,validationContext);

			}
			return s;
		} catch (IllegalAccessException e) {
			logger.error("",e);
		} catch (InvocationTargetException e) {
			logger.error("",e);
		} catch (NoSuchMethodException e) {
			logger.error("",e);
		}
		return null;
	}
	@Override
	protected void actEnregistrer() throws Exception {
		EntityTransaction transaction = dao.getEntityManager().getTransaction();
		try {
			ctrlUnChampsSWT(getFocusCourantSWT());
			ctrlTousLesChampsAvantEnregistrementSWT();
			dao.begin(transaction);
			//TaPrix u = null;
			if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
				LgrDozerMapper<SWTWeb,TaWeb> mapper = new LgrDozerMapper<SWTWeb,TaWeb>();
				mapper.map((SWTWeb) selectedWeb.getValue(),taWeb);
				masterEntity.addWeb(taWeb);

			} else if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)) {
				LgrDozerMapper<SWTWeb,TaWeb> mapper = new LgrDozerMapper<SWTWeb,TaWeb>();
				mapper.map((SWTWeb) selectedWeb.getValue(),taWeb);
				taWeb.setTaTiers(masterEntity);
				masterEntity.addWeb(taWeb);
			}

			try {
				if(!enregistreToutEnCours) {
					sortieChamps();
					fireEnregistreTout(new AnnuleToutEvent(this,true));
					DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_ENREGISTRER_ID);
					fireDeclencheCommandeController(e);
				}
			} catch (Exception e) {
				logger.error("",e);
			}		
			taWeb=masterEntity.getTaWeb();
			dao.commit(transaction);
			changementDeSelection();
			actRefresh();
			transaction = null;
		} finally {
			if(transaction!=null && transaction.isActive()) {
				transaction.rollback();
			}
			initEtatBouton();
		}
	}

	public void initEtatComposant() {
		try {
			super.initEtatComposantGeneral();
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
		}
		vue.getTfCODE_T_WEB().setEditable(true);
	}

	public boolean isUtilise(){
		return (((SWTWeb)selectedWeb.getValue()).getIdWeb()!=null && 
				!dao.recordModifiable(dao.getNomTable(),
						((SWTWeb)selectedWeb.getValue()).getIdTiers()))||
						!masterDAO.autoriseModification(masterEntity);		
	}
	public SWTWeb getSwtOldWeb() {
		return swtOldWeb;
	}

	public void setSwtOldWeb(SWTWeb swtOldWeb) {
		this.swtOldWeb = swtOldWeb;
	}

	public void setSwtOldWeb() {
		if (selectedWeb.getValue() != null)
			this.swtOldWeb = SWTWeb.copy((SWTWeb) selectedWeb.getValue());
		else {
			if (tableViewer.selectionGrille(0)){
				this.swtOldWeb = SWTWeb.copy((SWTWeb) selectedWeb.getValue());
				tableViewer.setSelection(new StructuredSelection(
						(SWTWeb) selectedWeb.getValue()), true);
			}}
	}


	public void setVue(PaWebSWT vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();
		mapComposantDecoratedField.put(vue.getTfADRESSE_WEB(), vue
				.getFieldADRESSE_WEB());
		mapComposantDecoratedField.put(vue.getTfCODE_T_WEB(), vue
				.getFieldCODE_T_WEB());


	}

	public Class getClassModel() {
		return classModel;
	}

	@Override
	protected void sortieChamps() {
		// TODO Raccord de methode auto-genere

	}

	@Override
	protected void actRefresh() throws Exception {
		writableList = new WritableList(realm, IHMmodel(), classModel);
		tableViewer.setInput(writableList);
		tableViewer.refresh();
		//repositionnement sur la valeur courante
		int idActuel = 0;
		if (taWeb!=null) { //enregistrement en cours de modification/insertion
			idActuel = taWeb.getIdWeb();
		} else if(selectedWeb!=null && (SWTWeb) selectedWeb.getValue()!=null) {
			idActuel = ((SWTWeb) selectedWeb.getValue()).getIdWeb();
		}

		if(idActuel!=0) {
			tableViewer.setSelection(new StructuredSelection(recherche(Const.C_ID_WEB, idActuel)));
		}else
			tableViewer.selectionGrille(0);
	}

	public Object recherche(String propertyName, Object value) {
		boolean trouve = false;
		int i = 0;
		while(!trouve && i<IHMmodel().size()){
			try {
				if(PropertyUtils.getProperty(IHMmodel().get(i), propertyName).equals(value)) {
					trouve = true;
				} else {
					i++;
				}
			} catch (IllegalAccessException e) {
				logger.error("",e);
			} catch (InvocationTargetException e) {
				logger.error("",e);
			} catch (NoSuchMethodException e) {
				logger.error("",e);
			}
		}

		if(trouve)
			return IHMmodel().get(i);
		else 
			return null;

	}
	public ModelGeneralObjet<SWTWeb,TaWebDAO,TaWeb> getModelWeb() {
		return modelWeb;
	}

	public TaWebDAO getDao() {
		return dao;
	}

	public void setDao(TaWebDAO dao) {
		this.dao = dao;
	}

	public TaTiers getMasterEntity() {
		return masterEntity;
	}

	public void setMasterEntity(TaTiers masterEntity) {
		this.masterEntity = masterEntity;
	}

	public TaTiersDAO getMasterDAO() {
		return masterDAO;
	}

	public void setMasterDAO(TaTiersDAO masterDAO) {
		this.masterDAO = masterDAO;
	}

	public void modifMode(){
		if (!VerrouInterface.isVerrouille() ){
			try {
				if(!daoStandard.dataSetEnModif()) {
					if(!masterEntity.getTaWebs().isEmpty()) {
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
}

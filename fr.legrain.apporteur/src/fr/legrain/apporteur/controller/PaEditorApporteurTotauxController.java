package fr.legrain.apporteur.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.util.ListenerList;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import fr.legrain.apporteur.apporteurPlugin;
import fr.legrain.apporteur.divers.IHMLignesTVA;
import fr.legrain.apporteur.divers.ModelLignesTVA;
import fr.legrain.apporteur.divers.ModelTotaux;
import fr.legrain.apporteur.ecran.PaTotauxApporteur;
import fr.legrain.apporteur.preferences.PreferenceConstants;
import fr.legrain.document.DocumentPlugin;
import fr.legrain.document.divers.TypeDoc;
import fr.legrain.document.ecran.PaCommentaire;
import fr.legrain.documents.dao.LigneTva;
import fr.legrain.documents.dao.TaApporteur;
import fr.legrain.documents.dao.TaApporteurDAO;
import fr.legrain.documents.dao.TaTPaiement;
import fr.legrain.documents.dao.TaTPaiementDAO;
import fr.legrain.documents.events.SWTModificationDocumentEvent;
import fr.legrain.documents.events.SWTModificationDocumentListener;
import fr.legrain.generationdocument.controllers.PaChoixGenerationDocumentController;
import fr.legrain.generationdocument.controllers.PaChoixGenerationModeleController;
import fr.legrain.generationdocument.divers.ParamAfficheChoixGenerationDocument;
import fr.legrain.generationdocument.ecran.PaChoixGenerationDocument;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Document.ChangeModeEvent;
import fr.legrain.gestCom.Module_Document.ChangeModeListener;
import fr.legrain.gestCom.Module_Document.IHMTotauxApporteur;
import fr.legrain.gestCom.Module_Document.SWTTPaiement;
import fr.legrain.gestCom.gestComBd.gestComBdPlugin;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.DeclencheCommandeControllerEvent;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideRechercheSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideRechercheControllerSWT;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDePageEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.AbstractLgrDAO;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.IBQuLgr;
import fr.legrain.lib.data.InfosVerifSaisie;
import fr.legrain.lib.data.LibCalcul;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.lib.data.VerrouInterface;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ParamAfficheSWT;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.lib.gui.cdatetimeLgr;
import fr.legrain.lib.gui.aide.PaAideRechercheSWT;
import fr.legrain.lib.gui.aide.PaAideSWT;
import fr.legrain.lib.gui.aide.ParamAfficheAide;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.lib.gui.grille.LgrViewerSupport;
import fr.legrain.tiers.divers.ParamAfficheTPaiement;
import fr.legrain.tiers.ecran.PaTypePaiementSWT;
import fr.legrain.tiers.ecran.SWTPaTypePaiementController;
import fr.legrain.tiers.editor.EditorInputTypePaiement;
import fr.legrain.tiers.editor.EditorTypePaiement;


public class PaEditorApporteurTotauxController extends JPABaseControllerSWTStandard implements 
ISelectionProvider, /*ISelectionListener,*/  RetourEcranListener,
SWTModificationDocumentListener,ChangeModeListener {

	static Logger logger = Logger.getLogger(PaEditorApporteurTotauxController.class.getName());
	private PaTotauxApporteur vue = null;
//	private TaApporteur masterEntity = new TaApporteur();


	public List<Control> listeComposantEntete = null;
	protected Map<Control, String> mapComposantChampsCommentaire = null;


	private Object ecranAppelant = null;
	private IHMTotauxApporteur ihmTotauxApporteur;
	private IHMTotauxApporteur ihmOldTotauxApporteur;
	private Realm realm;
	private DataBindingContext dbc;
	private String dataTVA[][] = {};
	private ModelTotaux modelTotaux = new ModelTotaux();
	private ModelLignesTVA modelLignesTVA;
	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private Object selectedTotauxApporteur = new IHMTotauxApporteur();
	private String[] listeChamp;
	private String nomClassController = this.getClass().getSimpleName();
	private Class classModel = IHMTotauxApporteur.class;

	private MapChangeListener changeListener = new MapChangeListener();

	private TaApporteur masterEntity = null;
	private TaApporteurDAO masterDAO = null;
	private TaApporteurDAO daoLocal = null;
	
	private LgrDozerMapper<IHMTotauxApporteur,TaApporteur> mapperUIToModel = new LgrDozerMapper<IHMTotauxApporteur,TaApporteur>();
	private LgrDozerMapper<TaApporteur,IHMTotauxApporteur> mapperModelToUI = new LgrDozerMapper<TaApporteur,IHMTotauxApporteur>();

	public PaEditorApporteurTotauxController(){}
	
	public PaEditorApporteurTotauxController(PaTotauxApporteur vue/*, WorkbenchPart part*/) {
		this(vue,null);
	}

	public PaEditorApporteurTotauxController(PaTotauxApporteur vue/*, WorkbenchPart part*/,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		try {
			setVue(vue);
			daoLocal = new TaApporteurDAO(getEm());
			vue.getShell().addShellListener(this);
			vue.getShell().addTraverseListener(new Traverse());


//			addDestroyListener(ibTaTable);
//			setIbTaTableStandard(masterDAO);

//			this.masterEntity.addModificationDocumentListener(masterDAO);
//			this.masterEntity.addModificationDocumentListener(this);
//			this.masterEntity.addChangeModeListener(this);



			//à l'insertion d'une nouvelle facture, le champ est contrôlé à vide, ce qui fait
			//lorsque que l'on sort de la zone sans l'avoir rempli, il ne la re-contrôle pas
			//d'où le rajout de se focusListener pour le forcer à remplir la zone

			initController();

		}
		catch (Exception e) {
			logger.debug("",e);
		}		
	}

	private void initController() {
		try {
			setDaoStandard(masterDAO);
			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc); 

			initVue();

			initMapComposantChamps();
			initMapComposantDecoratedField();

			listeComponentFocusableSWT(listeComposantFocusable);
			initDeplacementSaisie(listeComposantFocusable);
			initFocusOrder();
			initActions();

			branchementBouton();

			bind();
			try{
				((IHMTotauxApporteur)selectedTotauxApporteur).setImprimer(apporteurPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.IMPRIMER_AUTO));
			}catch (Exception e) {	
			}
			//#AFAIRE
//			initEtatBouton();

			vue.getTfMT_HT_CALC().setEnabled(false);
			vue.getTfMT_TTC_CALC().setEnabled(false);
			vue.getTfMT_TVA_CALC().setEnabled(false);


		} catch (Exception e) {
			if(e.getMessage()!=null)
				vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaArticlesController", e);
		}
	}

	public void bind() {
		try {
//			modelTotaux = new ModelTotaux();
//			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

//			modelTotaux.remplirListe(masterEntity);
//			if(!modelTotaux.getListeObjet().isEmpty())
//			selectedTotauxApporteur = modelTotaux.getListeObjet().getFirst();
//			dbc = new DataBindingContext(realm);

//			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
////			tableViewer.selectionGrille(0);

//			setTableViewerStandard(tableViewer);
//			setDbcStandard(dbc);

//			bindingFormSimple(dbc, realm, selectedTotauxApporteur,classModel);
//			bindingFormSimple(masterDAO, mapComposantChampsCommentaire, dbc, realm, selectedTotauxApporteur, classModel);

			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			LgrDozerMapper<TaApporteur,IHMTotauxApporteur> mapper = new LgrDozerMapper<TaApporteur,IHMTotauxApporteur>();
			if(ihmTotauxApporteur==null) ihmTotauxApporteur = new IHMTotauxApporteur();
			if(masterEntity!=null)
				mapper.map(masterEntity,ihmTotauxApporteur);
			
			if(modelTotaux.getListeObjet().isEmpty()) {
				modelTotaux.getListeObjet().add(ihmTotauxApporteur);
			}


			selectedTotauxApporteur = ihmTotauxApporteur;
			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			setDbcStandard(dbc);

			bindingFormSimple(dbc, realm, selectedTotauxApporteur,classModel);
			bindingFormSimple(masterDAO, mapComposantChampsCommentaire, dbc, realm, selectedTotauxApporteur, classModel);

			bindTVA();

		} catch (Exception e) {
			if(e.getMessage()!=null)
				vue.getLaMessage().setText(e.getMessage());
			logger.error("", e);
		}
	}

	public void bindTVA() {
		try {
			modelLignesTVA = new ModelLignesTVA();
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			tableViewer = new LgrTableViewer(vue.getTableTVA());
			tableViewer.createTableCol(vue.getTableTVA(), "ApporteurLignesTVA", Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			listeChamp = tableViewer.setListeChampGrille("ApporteurLignesTVA", Const.C_FICHIER_LISTE_CHAMP_GRILLE);

//			ObservableListContentProvider viewerContent = new ObservableListContentProvider();
//			tableViewer.setContentProvider(viewerContent);
//
//			IObservableMap[] attributeMaps = BeansObservables.observeMaps(
//					viewerContent.getKnownElements(), IHMLignesTVA.class, listeChamp);
//
//			tableViewer.setLabelProvider(new ObservableMapLabelProvider(attributeMaps));
//			writableList = new WritableList(realm, modelLignesTVA.remplirListe(masterEntity), IHMLignesTVA.class);
//			tableViewer.setInput(writableList);
			
			LgrViewerSupport.bind(tableViewer, 
					new WritableList(modelLignesTVA.remplirListe(masterEntity), IHMLignesTVA.class),
					BeanProperties.values(listeChamp)
					);

			dbc = new DataBindingContext(realm);

		} catch (Exception e) {
			if(e.getMessage()!=null)
				vue.getLaMessage().setText(e.getMessage());
			logger.error("", e);
		}
	}

	public Composite getVue() {
		return vue;
	}

	public ResultAffiche configPanel(ParamAffiche param) {
		param.setFocus(masterDAO.getModeObjet().getFocusCourant());
		try {
//			actInserer();
			bind();
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}
	
	/**
	 * Permet de pouvoir appele {@link #initVerifySaisie()} qui est une methode protected,
	 * dans l'initialisation du MultipageEditor. Cette methode ne peut etre appele qu'apres l'initialisation du masterDAO
	 * dans le MultipageEditor
	 * @see #initVerifySaisie()
	 */
	public void initVerifySaisiePublic() {
		initVerifySaisie();
	}
	
	/**
	 * @see #initVerifySaisiePublic()
	 */
	protected void initVerifySaisie() {
		if (mapInfosVerifSaisie == null)
			mapInfosVerifSaisie = new HashMap<Control, InfosVerifSaisie>();
		
		mapInfosVerifSaisie.put(vue.getTfMT_HT_CALC(), new InfosVerifSaisie(new TaApporteur(),Const.C_MT_HT_CALC,new int[]{InfosVerifSaisie.CTRL_QUE_DES_CHIFFRES}));
		mapInfosVerifSaisie.put(vue.getTfMT_TTC_CALC(), new InfosVerifSaisie(new TaApporteur(),Const.C_MT_TTC_CALC,new int[]{InfosVerifSaisie.CTRL_QUE_DES_CHIFFRES}));
		mapInfosVerifSaisie.put(vue.getTfMT_TVA_CALC(), new InfosVerifSaisie(new TaApporteur(),Const.C_MT_TVA_CALC,new int[]{InfosVerifSaisie.CTRL_QUE_DES_CHIFFRES}));
		mapInfosVerifSaisie.put(vue.getTfREGLE_APPORTEUR(), new InfosVerifSaisie(new TaApporteur(),Const.C_REGLE_DOCUMENT,new int[]{InfosVerifSaisie.CTRL_QUE_DES_CHIFFRES}));
		mapInfosVerifSaisie.put(vue.getTfTX_REM_HT_APPORTEUR(), new InfosVerifSaisie(new TaApporteur(),Const.C_TX_REM_HT_DOCUMENT,new int[]{InfosVerifSaisie.CTRL_QUE_DES_CHIFFRES}));
		mapInfosVerifSaisie.put(vue.getTfTX_REM_TTC_APPORTEUR(), new InfosVerifSaisie(new TaApporteur(),Const.C_TX_REM_TTC_DOCUMENT,new int[]{InfosVerifSaisie.CTRL_QUE_DES_CHIFFRES}));
		mapInfosVerifSaisie.put(vue.getTfCODE_T_PAIEMENT(), new InfosVerifSaisie(new TaTPaiement(),Const.C_CODE_T_PAIEMENT,null));
		
		initVerifyListener(mapInfosVerifSaisie, masterDAO);
	}

	protected void initComposantsVue() throws ExceptLgr {
	}

	protected void initMapComposantChamps() {
		if (mapComposantChamps == null)
			mapComposantChamps = new LinkedHashMap<Control, String>();

		if (listeComposantFocusable == null)
			listeComposantFocusable = new ArrayList<Control>();

		if (listeComposantEntete == null)
			listeComposantEntete = new ArrayList<Control>();

		if (mapComposantChampsCommentaire == null)
			mapComposantChampsCommentaire = new LinkedHashMap<Control, String>();



		for (Control c : mapComposantChamps.keySet()) {
			listeComposantEntete.add(c);
		}

		mapComposantChamps.put(vue.getTfTX_REM_HT_APPORTEUR(), Const.C_TX_REM_HT_DOCUMENT);
		mapComposantChamps.put(vue.getTfMT_HT_CALC(), Const.C_NET_HT_CALC);
		mapComposantChamps.put(vue.getTfMT_TVA_CALC(), Const.C_NET_TVA_CALC);
		mapComposantChamps.put(vue.getTfTX_REM_TTC_APPORTEUR(), Const.C_TX_REM_TTC_DOCUMENT);
		mapComposantChamps.put(vue.getTfMT_TTC_CALC(), Const.C_NET_TTC_CALC);
		mapComposantChamps.put(vue.getTfREGLE_APPORTEUR(), Const.C_REGLE_DOCUMENT);
		mapComposantChamps.put(vue.getTfCODE_T_PAIEMENT(), Const.C_CODE_T_PAIEMENT);
		mapComposantChamps.put(vue.getCbImprimer(), "imprimer");

		for (Control c : mapComposantChamps.keySet()) {
			c.setToolTipText(mapComposantChamps.get(c));
		}

		mapComposantChampsCommentaire.put(((PaCommentaire) vue.getExpandBar().getItem(0).getControl()).getTfLIBL_COMMENTAIRE(),Const.C_COMMENTAIRE);

		listeComposantFocusable.add(vue.getTfTX_REM_HT_APPORTEUR());
		listeComposantFocusable.add(vue.getTfMT_HT_CALC());
		listeComposantFocusable.add(vue.getTfMT_TVA_CALC());
		listeComposantFocusable.add(vue.getTfTX_REM_TTC_APPORTEUR());
		listeComposantFocusable.add(vue.getTfMT_TTC_CALC());
		listeComposantFocusable.add(vue.getTfREGLE_APPORTEUR());
		listeComposantFocusable.add(vue.getCbImprimer());
		listeComposantFocusable.add(vue.getTfCODE_T_PAIEMENT());
		listeComposantFocusable.add(((PaCommentaire) vue.getExpandBar().getItem(0).getControl()).getTfLIBL_COMMENTAIRE());

		listeComposantFocusable.add(vue.getPaBtnAvecAssistant().getPaBtn().getBtnEnregistrer());
		listeComposantFocusable.add(vue.getPaBtnAvecAssistant().getPaBtn().getBtnInserer());
		listeComposantFocusable.add(vue.getPaBtnAvecAssistant().getPaBtn().getBtnModifier());
		listeComposantFocusable.add(vue.getPaBtnAvecAssistant().getPaBtn().getBtnSupprimer());
		listeComposantFocusable.add(vue.getPaBtnAvecAssistant().getPaBtn().getBtnFermer());
		listeComposantFocusable.add(vue.getPaBtnAvecAssistant().getPaBtn().getBtnAnnuler());
		listeComposantFocusable.add(vue.getPaBtnAvecAssistant().getPaBtn().getBtnImprimer());

		listeComposantFocusable.add(vue.getPaBtnAvecAssistant().getPaBtnAssitant().getBtnPrecedent());
		listeComposantFocusable.add(vue.getPaBtnAvecAssistant().getPaBtnAssitant().getBtnSuivant());


		if (mapInitFocus == null)
			mapInitFocus = new LinkedHashMap<ModeObjet.EnumModeObjet, Control>();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION, vue
				.getTfTX_REM_HT_APPORTEUR());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue
				.getTfTX_REM_HT_APPORTEUR());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION, vue
				.getTfTX_REM_HT_APPORTEUR());

		activeModifytListener();

		vue.getTfREGLE_APPORTEUR().addVerifyListener(queDesChiffresPositifs);
		vue.getTfTX_REM_HT_APPORTEUR().addVerifyListener(queDesChiffresPositifs);
		vue.getTfTX_REM_TTC_APPORTEUR().addVerifyListener(queDesChiffresPositifs);
	}


	protected void initActions() {
		//initCommands();
//		initWorkenchPartCommands(workbenchPart);		
//		if (mapActions == null)
//		mapActions = new LinkedHashMap<Button, Object>();

//		actionAide = new ActionAideApporteur("Aide [F1]");
//		actionAnnuler = new ActionAnnulerApporteur("Annuler [ESC]");
//		actionEnregistrer = new ActionEnregistrerApporteur("Enregistrer [F3]");
//		actionInserer = new ActionInsererApporteur("Insérer [F6]");
//		actionModifier = new ActionModifierApporteur("Modifier [F2]");
//		actionSupprimer = new ActionSupprimerApporteur("Supprimer [F10]");
//		actionFermer = new ActionFermerApporteur("Fermer [F4]");
//		actionImprimer = new ActionImprimerApporteur("Imprimer [F11]");
//		actionRaffraichir = new ActionRefreshApporteur("Rafraîchir[F5]");

//		mapActions.put(vue.getPaBtnAvecAssistant().getPaBtn().getBtnAnnuler(), actionAnnuler);
//		mapActions.put(vue.getPaBtnAvecAssistant().getPaBtn().getBtnEnregistrer(), actionEnregistrer);
//		mapActions.put(vue.getPaBtnAvecAssistant().getPaBtn().getBtnInserer(), actionInserer);
//		mapActions.put(vue.getPaBtnAvecAssistant().getPaBtn().getBtnModifier(), actionModifier);
//		mapActions.put(vue.getPaBtnAvecAssistant().getPaBtn().getBtnSupprimer(), actionSupprimer);
//		mapActions.put(vue.getPaBtnAvecAssistant().getPaBtn().getBtnFermer(), actionFermer);
//		mapActions.put(vue.getPaBtnAvecAssistant().getPaBtn().getBtnImprimer(), actionImprimer);

//		Object[] tabActionsAutres = new Object[] { actionAide,actionRaffraichir };
//		mapActions.put(null, tabActionsAutres);

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

		mapCommand.put(C_COMMAND_GLOBAL_PRECEDENT_ID, handlerPrecedent);
		mapCommand.put(C_COMMAND_GLOBAL_SUIVANT_ID, handlerSuivant);

		initFocusCommand(String.valueOf(this.hashCode()),listeComposantFocusable,mapCommand);

		if (mapActions == null)
			mapActions = new LinkedHashMap<Button, Object>();

		mapActions.put(vue.getPaBtnAvecAssistant().getPaBtn().getBtnAnnuler(), C_COMMAND_GLOBAL_ANNULER_ID);
		mapActions.put(vue.getPaBtnAvecAssistant().getPaBtn().getBtnEnregistrer(), C_COMMAND_GLOBAL_ENREGISTRER_ID);
		mapActions.put(vue.getPaBtnAvecAssistant().getPaBtn().getBtnInserer(), C_COMMAND_GLOBAL_INSERER_ID);
		mapActions.put(vue.getPaBtnAvecAssistant().getPaBtn().getBtnModifier(), C_COMMAND_GLOBAL_MODIFIER_ID);
		mapActions.put(vue.getPaBtnAvecAssistant().getPaBtn().getBtnSupprimer(), C_COMMAND_GLOBAL_SUPPRIMER_ID);
		mapActions.put(vue.getPaBtnAvecAssistant().getPaBtn().getBtnFermer(), C_COMMAND_GLOBAL_FERMER_ID);
		mapActions.put(vue.getPaBtnAvecAssistant().getPaBtn().getBtnImprimer(), C_COMMAND_GLOBAL_IMPRIMER_ID);

		mapActions.put(vue.getPaBtnAvecAssistant().getPaBtnAssitant().getBtnPrecedent(), C_COMMAND_GLOBAL_PRECEDENT_ID);
		mapActions.put(vue.getPaBtnAvecAssistant().getPaBtnAssitant().getBtnSuivant(), C_COMMAND_GLOBAL_SUIVANT_ID);

		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID, C_COMMAND_GLOBAL_REFRESH_ID };
		mapActions.put(null, tabActionsAutres);

		Menu popupMenuFormulaire = new Menu(vue.getShell(), SWT.POP_UP);
		Menu popupMenuGrille = new Menu(vue.getShell(), SWT.POP_UP);
		Menu[] tabPopups = new Menu[] { popupMenuFormulaire, popupMenuGrille };
		this.initPopupAndButtons(mapActions, tabPopups);
		vue.getPaTotaux().setMenu(popupMenuFormulaire);
//		}
}



	public PaEditorApporteurTotauxController getThis() {
		return this;
	}

	@Override
	public boolean onClose() throws ExceptLgr {
		return true;
	}

	public void retourEcran(final RetourEcranEvent evt) {
		try {
			if (evt.getSource() instanceof PaChoixGenerationDocumentController){
				DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_REFRESH_ID);
				fireDeclencheCommandeController(e);
			}
			else
			if (evt.getRetour() != null
					&& (evt.getSource() instanceof SWTPaAideControllerSWT 
							&& !evt.getRetour().getResult().equals(""))) {			
				if (getFocusAvantAideSWT() instanceof Text) {
					try {
						((Text) getFocusAvantAideSWT()).setText(((ResultAffiche) evt.getRetour()).getResult());					
						if(getFocusAvantAideSWT().equals(vue.getTfCODE_T_PAIEMENT())){
							TaTPaiement f = null;
							TaTPaiementDAO t = new TaTPaiementDAO(getEm());
							f = t.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
							t = null;
							masterEntity.setTaTPaiement(f);
						}
						ctrlUnChampsSWT(getFocusAvantAideSWT());
					} catch (Exception e) {
						logger.error("",e);
						vue.getLaMessage().setText(e.getMessage());
					}
				}
			} 
			super.retourEcran(evt);
		}finally{
			//ne pas enlever car sur demande de l'aide, je rends enable false tous les boutons
			//donc au retour de l'aide, je reinitialise les boutons suivant l'état du dataset
			//activeWorkenchPartCommands(true);
			//controllerLigne.activeWorkenchPartCommands(true);
		}
	}

	@Override
	protected void actInserer() throws Exception {
		DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_INSERER_ID);
		fireDeclencheCommandeController(e);
		ChangementDePageEvent change = new ChangementDePageEvent(this,ChangementDePageEvent.AUTRE,0);
		fireChangementDePage(change);
	}

	@Override
	protected void actModifier() throws Exception {
		DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
		fireDeclencheCommandeController(e);
	}

	@Override
	protected void actSupprimer() throws Exception {
		DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_SUPPRIMER_ID);
		fireDeclencheCommandeController(e);
		ChangementDePageEvent change = new ChangementDePageEvent(this,ChangementDePageEvent.AUTRE,0);
		fireChangementDePage(change);
	}


	@Override
	protected void actFermer() throws Exception {
		DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_FERMER_ID);
		fireDeclencheCommandeController(e);
	}


	@Override
	protected void actAnnuler() throws Exception {
		actAnnuler(false);	
	}


	protected void actAnnuler(boolean annulationForcee) throws Exception {
		DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_ANNULER_ID);
		fireDeclencheCommandeController(e);
		ChangementDePageEvent evt2 = new ChangementDePageEvent(this,ChangementDePageEvent.DEBUT);
		fireChangementDePage(evt2);		

	}

	@Override
	protected void actImprimer() throws Exception {
		DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_IMPRIMER_ID);
		fireDeclencheCommandeController(e);
	}

	@Override
	protected boolean aideDisponible() {
		boolean result = false;
		switch ((PaEditorApporteurTotauxController.this.masterDAO.getModeObjet().getMode())) {
		case C_MO_CONSULTATION:
			if (getFocusCourantSWT().equals(vue.getTfCODE_T_PAIEMENT()))
				result = true;
			break;
		case C_MO_EDITION:
		case C_MO_INSERTION:
			if (getFocusCourantSWT().equals(vue.getTfCODE_T_PAIEMENT()))
				result = true;
			break;
		default:
			break;
		}
		return result;
	}

	@Override
	protected void actPrecedent() throws Exception {
		ChangementDePageEvent evt = new ChangementDePageEvent(this,ChangementDePageEvent.PRECEDENT);
		fireChangementDePage(evt);
	}

	@Override
	protected void actSuivant() throws Exception {
		ChangementDePageEvent evt = new ChangementDePageEvent(this,ChangementDePageEvent.SUIVANT);
		fireChangementDePage(evt);
	}

	@Override
	protected void actAide() throws Exception {
		actAide(null);
	}

	@Override
	protected void actAide(String message) throws Exception {
//		DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_AIDE_ID);
//		fireDeclencheCommandeController(e);
		
		
		//boolean aide = getActiveAide();
		if (aideDisponible()) {
			setActiveAide(true);
			boolean verrouLocal = VerrouInterface.isVerrouille();
			VerrouInterface.setVerrouille(true);
			try {
				vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
				ParamAfficheAideRechercheSWT paramAfficheAideRecherche = new ParamAfficheAideRechercheSWT();
//				paramAfficheAideRecherche.setDb(getThis().getIbTaTable().getFIBBase());
				paramAfficheAideRecherche.setMessage(message);
				// Creation de l'ecran d'aide principal
				Shell s = new Shell(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),LgrShellUtil.styleLgr);
				PaAideSWT paAide = new PaAideSWT(s, SWT.NONE);
				SWTPaAideControllerSWT paAideController = new SWTPaAideControllerSWT(paAide);
				/** ************************************************************ */
				LgrPartListener.getLgrPartListener().setLgrActivePart(null);
				IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new EditorInputAide(), EditorAide.ID);
				LgrPartListener.getLgrPartListener().setLgrActivePart(e);
				LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
				paAideController = new SWTPaAideControllerSWT(((EditorAide) e).getComposite());
				((LgrEditorPart) e).setController(paAideController);
				((LgrEditorPart) e).setPanel(((EditorAide) e).getComposite());
				/** ************************************************************ */
				JPABaseControllerSWTStandard controllerEcranCreation = null;
				ParamAffiche parametreEcranCreation = null;
				IEditorPart editorCreation = null;
				String editorCreationId = null;
				IEditorInput editorInputCreation = null;
				Shell s2 = new Shell(s, LgrShellUtil.styleLgr);

				switch ((PaEditorApporteurTotauxController.this.masterDAO.getModeObjet().getMode())) {
				case C_MO_CONSULTATION:
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
						paramAfficheAideRecherche.setControllerAppelant(getThis());
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTPaiement,TaTPaiementDAO,TaTPaiement>(SWTTPaiement.class,getEm()));

						paramAfficheAideRecherche.setTypeObjet(swtPaTypePaiementController.getClassModel());
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_T_PAIEMENT);
						
						/*
						 * Bug #1376
						 */
						if (masterDAO.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION) == 0) {
							actModifier();
						}
					}
					break;
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
						paramAfficheAideRecherche.setControllerAppelant(getThis());
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTPaiement,TaTPaiementDAO,TaTPaiement>(SWTTPaiement.class,getEm()));

						paramAfficheAideRecherche.setTypeObjet(swtPaTypePaiementController.getClassModel());
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_T_PAIEMENT);
					}
					break;
				default:
					break;
				}

				if (paramAfficheAideRecherche.getJPQLQuery() != null) {

					PaAideRechercheSWT paAideRecherche1 = new PaAideRechercheSWT(s, SWT.NULL);
					SWTPaAideRechercheControllerSWT paAideRechercheController1 = new SWTPaAideRechercheControllerSWT(paAideRecherche1);

					// Parametrage de la recherche
					paramAfficheAideRecherche.setFocusSWT(((PaAideRechercheSWT) paAideRechercheController1.getVue()).getTfChoix());
					paramAfficheAideRecherche.setRefCreationSWT(controllerEcranCreation);
					paramAfficheAideRecherche.setEditorCreation(editorCreation);
					paramAfficheAideRecherche.setEditorCreationId(editorCreationId);
					paramAfficheAideRecherche.setEditorInputCreation(editorInputCreation);
					paramAfficheAideRecherche.setParamEcranCreation(parametreEcranCreation);
					paramAfficheAideRecherche.setShellCreation(s2);
					paAideRechercheController1.configPanel(paramAfficheAideRecherche);
					// paramAfficheAideRecherche.setFocusDefaut(paAideRechercheController1.getVue().getTfChoix());

					// Ajout d'une recherche
					paAideController.addRecherche(paAideRechercheController1,paramAfficheAideRecherche.getTitreRecherche());

					// Parametrage de l'ecran d'aide principal
					ParamAfficheAideSWT paramAfficheAideSWT = new ParamAfficheAideSWT();
					ParamAfficheAide paramAfficheAide = new ParamAfficheAide();

					// enregistrement pour le retour de l'ecran d'aide
					paAideController.addRetourEcranListener(getThis());
					Control focus = vue.getShell().getDisplay().getFocusControl();
					// affichage de l'ecran d'aide principal (+ ses recherches)


					dbc.getValidationStatusMap().removeMapChangeListener(changeListener);
					//LgrShellUtil.afficheAideSWT(paramAfficheAide, null, paAide,paAideController, s);
					/*****************************************************************/
					paAideController.configPanel(paramAfficheAide);
					/*****************************************************************/
					dbc.getValidationStatusMap().addMapChangeListener(changeListener);
					// je rends enable false tous les boutons avant de passer
					// dans l'écran d'aide
					// pour ne pas que les actions de l'écran des factures
					// interfèrent ceux de l'écran d'aide
					// activeWorkenchPartCommands(false);
				}

			} finally {
				VerrouInterface.setVerrouille(false);
				vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
			}
		}
	}
	
	public IStatus validateUI() throws Exception {
		if ((masterDAO.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
				|| (masterDAO.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
			ctrlTousLesChampsAvantEnregistrementSWT();
			mapperUIToModel.map(((IHMTotauxApporteur)selectedTotauxApporteur),masterEntity);
		}
		return null;
	}
	
	public IStatus validateUIField(String nomChamp,Object value) {
		String validationContext = "APPORTEUR";
		try {
			IStatus s = null;			
			if(nomChamp.equals(Const.C_CODE_T_PAIEMENT)) {
				TaTPaiementDAO dao = new TaTPaiementDAO(getEm());
				
				dao.setModeObjet(getMasterDAO().getModeObjet());
				TaTPaiement f = new TaTPaiement();
				PropertyUtils.setSimpleProperty(f, nomChamp, value);
				s = dao.validate(f,nomChamp,validationContext);
				
				if(s.getSeverity()!=IStatus.ERROR ){
					f = dao.findByCode((String)value);
					masterEntity.setTaTPaiement(f);
//					f.getTaApporteurs().add(masterEntity);
				}
				dao = null;	
			} else if(nomChamp.equals("imprimer")) {
				s = new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
			} else {
				TaApporteur u = new TaApporteur(true);
				PropertyUtils.setSimpleProperty(u, nomChamp, value);
				s = masterDAO.validate(u,nomChamp,validationContext);
				if(s.getSeverity()!=IStatus.ERROR) {
					PropertyUtils.setSimpleProperty(masterEntity, nomChamp, value);
				}
				if(nomChamp.equals(Const.C_TX_REM_TTC_DOCUMENT)) {
//					if(u.getTxRemTtcApporteur().signum()>0){
//						BigDecimal valeur =masterEntity.getNetTtcCalc().subtract(
//						masterEntity.getNetTtcCalc().multiply(u.getTxRemTtcApporteur().divide(
//								BigDecimal.valueOf(100),MathContext.DECIMAL128))
//						);	
//						masterEntity.setNetTtcCalc(LibCalcul.arrondi(valeur));
//					}				
				}
				if(nomChamp.equals(Const.C_REGLE_DOCUMENT)) {
					if(u.getRegleDocument().compareTo(masterEntity.getNetTtcCalc())==1){
						masterEntity.setRegleDocument(masterEntity.getNetTtcCalc());
					}
				}
			}
			if(s.getSeverity()!=IStatus.ERROR) {
				mapperModelToUI.map(masterEntity, ((IHMTotauxApporteur)selectedTotauxApporteur));
			}
//			new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
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
		try {
//			sortieChamps();
			ctrlUnChampsSWT(getFocusCourantSWT());
			validateUI();
			ChangementDePageEvent change = new ChangementDePageEvent(this,ChangementDePageEvent.AUTRE,0);
			fireChangementDePage(change);
			DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_ENREGISTRER_ID);
			fireDeclencheCommandeController(e);
		} catch (Exception e) {
			logger.error("",e);
		}		
	}

//	public SWT_IB_TA_APPORTEUR getIbTaTable() {
//	return ibTaTable;
//	}

	public void initEtatComposant() {
	}

	public IHMTotauxApporteur getIhmOldEnteteApporteur() {
		return ihmOldTotauxApporteur;
	}

	public void setIhmOldTotauxApporteur(IHMTotauxApporteur ihmOldTotauxApporteur) {
		this.ihmOldTotauxApporteur = ihmOldTotauxApporteur;
	}

	public void setIhmOldTotauxApporteur() {
		if (selectedTotauxApporteur!= null)
			this.ihmOldTotauxApporteur = IHMTotauxApporteur.copy((IHMTotauxApporteur) selectedTotauxApporteur);
		else {
			if(tableViewer.selectionGrille(0)){
				setSelectedTotauxApporteur(selectedTotauxApporteur);
				this.ihmOldTotauxApporteur = IHMTotauxApporteur.copy((IHMTotauxApporteur) selectedTotauxApporteur);
				tableViewer.setSelection(new StructuredSelection(
						(IHMTotauxApporteur) selectedTotauxApporteur), true);
			}}
	}

	public void setVue(PaTotauxApporteur vue) {
		super.setVue(vue);
		this.vue = vue;		
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();

		mapComposantDecoratedField.put(vue.getTfCODE_T_PAIEMENT(), vue.getFieldCODE_T_PAIEMENT());
		mapComposantDecoratedField.put(vue.getTfMT_HT_CALC(), vue.getFieldMT_HT_CALC());
		mapComposantDecoratedField.put(vue.getTfMT_TTC_CALC(), vue.getFieldMT_TTC_CALC());
		mapComposantDecoratedField.put(vue.getTfMT_TVA_CALC(), vue.getFieldMT_TVA_CALC());
		mapComposantDecoratedField.put(vue.getTfREGLE_APPORTEUR(), vue.getFieldREGLE_APPORTEUR());
		mapComposantDecoratedField.put(vue.getTfTX_REM_HT_APPORTEUR(), vue.getFieldTX_REM_HT_APPORTEUR());
		mapComposantDecoratedField.put(vue.getTfTX_REM_TTC_APPORTEUR(), vue.getFieldTX_REM_TTC_APPORTEUR());

	}

	public Class getClassModel() {
		return classModel;
	}

	@Override
	protected void sortieChamps() {
	}

	@Override
	protected void actRefresh() throws Exception {
		
		if (masterDAO.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION) != 0) {
			if (masterEntity!=null && selectedTotauxApporteur!=null && (IHMTotauxApporteur) selectedTotauxApporteur!=null) {
				mapperModelToUI.map(masterEntity, (IHMTotauxApporteur) selectedTotauxApporteur);
			}
		}
		
		DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_REFRESH_ID);
		fireDeclencheCommandeController(e);
	}

	private ListenerList selectionChangedListeners = new ListenerList(3);

	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		selectionChangedListeners.add(listener);
	}

	public ISelection getSelection() {
		return null;
	}

//	ISelectionProvider
	protected void fireSelectionChanged(final SelectionChangedEvent event) {
		Object[] listeners = selectionChangedListeners.getListeners();
		for (int i = 0; i < listeners.length; ++i) {
			final ISelectionChangedListener l = (ISelectionChangedListener) listeners[i];
			SafeRunnable.run(new SafeRunnable() {
				public void run() {
					l.selectionChanged(event);
				}
			});
		}
	}

	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		selectionChangedListeners.remove(listener);
	}

	protected void updateSelection(ISelection selection) {
		SelectionChangedEvent event = new SelectionChangedEvent(this, selection);
		fireSelectionChanged(event);
	}

	public void setSelection(ISelection selection) {
		//TODO Auto-generated method stub
	}


	public void modificationDocument(SWTModificationDocumentEvent evt) {

		BigDecimal montantHT = new BigDecimal(0);
		BigDecimal montantTVA =new BigDecimal(0);
		BigDecimal montantTTC = new BigDecimal(0);

		dataTVA = new String[masterEntity.getLignesTVA().size()][3];

		for(LigneTva ligneTVA : masterEntity.getLignesTVA()){
			montantTVA.add(LibCalcul.arrondi(ligneTVA.getMtTva()));
		}

		writableList = new WritableList(realm, modelLignesTVA.remplirListe(masterEntity), IHMLignesTVA.class);
		tableViewer.setInput(writableList);

		//CALCUL_TOTAL_TEMP
		BigDecimal NetHT = new BigDecimal(0);
		BigDecimal NetTVA = new BigDecimal(0);
		BigDecimal NetTTC = new BigDecimal(0);

		try {
			((IHMTotauxApporteur)selectedTotauxApporteur).setMtHtCalc(LibCalcul.arrondi(montantHT));
			((IHMTotauxApporteur)selectedTotauxApporteur).setMtTtcCalc(LibCalcul.arrondi(montantTTC));
			((IHMTotauxApporteur)selectedTotauxApporteur).setMtTvaCalc(LibCalcul.arrondi(montantTVA));
			((IHMTotauxApporteur)selectedTotauxApporteur).setNetHtCalc(LibCalcul.arrondi(NetHT));
			((IHMTotauxApporteur)selectedTotauxApporteur).setNetTtcCalc(LibCalcul.arrondi(NetTTC));
			((IHMTotauxApporteur)selectedTotauxApporteur).setNetTvaCalc(LibCalcul.arrondi(NetTVA));

			if(((IHMTotauxApporteur)selectedTotauxApporteur).getTxRemTtcDocument()==null)
				((IHMTotauxApporteur)selectedTotauxApporteur).setTxRemTtcDocument(new BigDecimal(0));


			if(((IHMTotauxApporteur)selectedTotauxApporteur).getRegleDocument()==null)
				((IHMTotauxApporteur)selectedTotauxApporteur).setRegleDocument(new BigDecimal(0));

			if(((IHMTotauxApporteur)selectedTotauxApporteur).getRegleDocument().compareTo(((IHMTotauxApporteur)selectedTotauxApporteur).getNetTtcCalc())>0
					&& ((IHMTotauxApporteur)selectedTotauxApporteur).getNetTtcCalc().compareTo(new BigDecimal(0))>0)
				((IHMTotauxApporteur)selectedTotauxApporteur).setRegleDocument(((IHMTotauxApporteur)selectedTotauxApporteur).getNetTtcCalc());
			else if(((IHMTotauxApporteur)selectedTotauxApporteur).getNetTtcCalc().signum()<0)
				((IHMTotauxApporteur)selectedTotauxApporteur).setRegleDocument(new BigDecimal(0));

			if(((IHMTotauxApporteur)selectedTotauxApporteur).getTxRemHtDocument()==null)
				((IHMTotauxApporteur)selectedTotauxApporteur).setTxRemHtDocument(new BigDecimal(0));
			
			masterEntity.calculeTvaEtTotaux();
			mapperModelToUI.map(masterEntity, ((IHMTotauxApporteur) selectedTotauxApporteur));

		} catch (Exception e) {
			logger.error(e);
		}		
	}
//			if(((IHMTotauxApporteur)selectedTotauxApporteur).getTxRemTtcDocument()==null)
//				((IHMTotauxApporteur)selectedTotauxApporteur).setTxRemTtcDocument(new BigDecimal(0));
//
//			if(((IHMTotauxApporteur)selectedTotauxApporteur).getRegleDocument()==null)
//				((IHMTotauxApporteur)selectedTotauxApporteur).setRegleDocument(new BigDecimal(0));
//
//			if(((IHMTotauxApporteur)selectedTotauxApporteur).getRegleDocument().compareTo(((IHMTotauxApporteur)selectedTotauxApporteur).getNetTtcCalc())>0
//					&& ((IHMTotauxApporteur)selectedTotauxApporteur).getNetTtcCalc().compareTo(new BigDecimal(0))>0)
//				((IHMTotauxApporteur)selectedTotauxApporteur).setRegleDocument(((IHMTotauxApporteur)selectedTotauxApporteur).getNetTtcCalc());
//			else if(((IHMTotauxApporteur)selectedTotauxApporteur).getNetTtcCalc().signum()<0)
//				((IHMTotauxApporteur)selectedTotauxApporteur).setRegleDocument(new BigDecimal(0));
//
//			if(((IHMTotauxApporteur)selectedTotauxApporteur).getTxRemHtDocument()==null)
//				((IHMTotauxApporteur)selectedTotauxApporteur).setTxRemHtDocument(new BigDecimal(0));
//
//			if(masterEntity.getTxRemTtcDocument().signum()>0){
//				BigDecimal valeur =masterEntity.getNetTtcCalc().subtract(
//				masterEntity.getNetTtcCalc().multiply(masterEntity.getTxRemTtcDocument().divide(
//						BigDecimal.valueOf(100),MathContext.DECIMAL128))
//				);	
//				masterEntity.setNetTtcCalc(LibCalcul.arrondi(valeur));
//			}
//			if(masterEntity.getRegleDocument().compareTo(masterEntity.getNetTtcCalc())==1){
//				masterEntity.setRegleDocument(masterEntity.getNetTtcCalc());
//			}
////			((SWTEnteteApporteur)masterEntity.getEntete()).setTotauxApporteur(((IHMTotauxApporteur)selectedTotauxApporteur));
//			mapperModelToUI.map(masterEntity, ((IHMTotauxApporteur) selectedTotauxApporteur));
//
//		} catch (Exception e) {
//			logger.error(e);
//		}		
//	}

	public void changementMode(ChangeModeEvent evt) {
		try {
			if(!masterDAO.dataSetEnModif()) {
				if(!masterEntity.getOldCODE().equals(""))
					masterDAO.modifier(masterEntity);
				else
					actInserer();
				initEtatBouton(false);
			}
		} catch (Exception e) {
			logger.error(e);
		}		
	}




	/**
	 * Active l'ecoute de tous les champs du controller qui sont relie a la bdd,
	 * si le dataset n'est pas en modification et qu'un des champs est modifie, le dataset
	 * passera automatiquement en edition.
	 * @see #desactiveModifyListener
	 */
	public void activeModifytListener() {
		System.out.println("active");
		//#AFAIRE
		if(masterDAO!=null){
			activeModifytListener(mapComposantChamps, masterDAO);
			activeModifytListener(mapComposantChampsCommentaire, masterDAO);
		}
	}

	/**
	 * Initialisation des boutons suivant l'etat de l'objet "ibTaTable"
	 * @param initFocus - si vrai initialise le focus en fonction du mode
	 */
	protected void initEtatBouton(boolean initFocus) {
		super.initEtatBoutonCommand(initFocus,modelTotaux.getListeObjet());
		enableActionAndHandler(C_COMMAND_GLOBAL_PRECEDENT_ID,true);
		enableActionAndHandler(C_COMMAND_GLOBAL_SUIVANT_ID,false);
	}

	/**
	 * Positionnement du focus en fonction du mode de l'écran
	 * @param ibTaTable - Ensemble de données utilisé dans l'écran du controller
	 * @param focus - [clé : mode] [valeur : composant qui à le focus par défaut pour ce mode]
	 */
	protected void initFocusSWT(IBQuLgr ibTaTable, Map<ModeObjet.EnumModeObjet,Control> focus) {
		setFocusCourantSWT(vue.getTfTX_REM_HT_APPORTEUR());
		getFocusCourantSWT().forceFocus();
	}


	@Override
	protected void initImageBouton() {
		vue.getPaBtnAvecAssistant().getPaBtn().getBtnAnnuler().setImage(
				LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ANNULER));
		vue.getPaBtnAvecAssistant().getPaBtn().getBtnEnregistrer().setImage(
				LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ENREGISTRER));
		vue.getPaBtnAvecAssistant().getPaBtn().getBtnFermer().setImage(
				LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_FERMER));
		vue.getPaBtnAvecAssistant().getPaBtn().getBtnInserer().setImage(
				LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_INSERER));
		vue.getPaBtnAvecAssistant().getPaBtn().getBtnModifier().setImage(
				LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_MODIFIER));
		vue.getPaBtnAvecAssistant().getPaBtn().getBtnSupprimer().setImage(
				LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_SUPPRIMER));
		vue.getPaBtnAvecAssistant().getPaBtn().getBtnImprimer().setImage(
				LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_IMPRIMER));
		vue.layout(true);
	}

	public Boolean remonterDocument() throws Exception {
		try{
			boolean res = false;	
			//nouvelle facture (mode, code existant "CodeApporteur")
			desactiveModifyListener();

			//#evt
			//#AFAIRE
//			modelTotaux.remplirListe(masterEntity);
//			((IHMTotauxApporteur)selectedTotauxApporteur).setIHMTotauxApporteur(modelTotaux.getListeObjet().getFirst()) ;
			initSelectedObjectFromPreferences();
			res=true;


			return res;
		}
		finally{
			activeModifytListener();
			initEtatBouton(true);
		}

	}
	public void initialisationEcran() {			
//		modelTotaux.remplirListe(masterEntity);
//		((IHMTotauxApporteur)selectedTotauxApporteur).setIHMTotauxApporteur(modelTotaux.getListeObjet().getFirst()) ;
		initSelectedObjectFromPreferences();
	}

	private void initSelectedObjectFromPreferences(){
		((IHMTotauxApporteur)selectedTotauxApporteur).setImprimer(apporteurPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.IMPRIMER_AUTO));		
	}



//	public void setIbTaTable(SWT_IB_TA_APPORTEUR ibTaTable) {
//	this.ibTaTable = ibTaTable;
//	setIbTaTableStandard(ibTaTable);
//	}

	public void setTaApporteur(TaApporteur masterEntity) {
		this.masterEntity = masterEntity;
		this.masterEntity.addModificationDocumentListener(this);
	}

	public Object getSelectedTotauxApporteur() {
		return selectedTotauxApporteur;
	}

	public void setSelectedTotauxApporteur(Object selectedTotauxApporteur) {
		this.selectedTotauxApporteur = selectedTotauxApporteur;
		((ModelObject)selectedTotauxApporteur).addPropertyChangeListener(new TotauxPropertyChangeListener());
	}

	private class TotauxPropertyChangeListener implements PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent evt) {
			if(evt.getPropertyName().equals(Const.C_MT_TTC_CALC))
				masterEntity.setMtTtcCalc((BigDecimal)evt.getNewValue());
			else if(evt.getPropertyName().equals(Const.C_MT_HT_CALC))
				masterEntity.setMtHtCalc((BigDecimal)evt.getNewValue());
			else if(evt.getPropertyName().equals(Const.C_MT_TVA_CALC))
				masterEntity.setMtTvaCalc((BigDecimal)evt.getNewValue());
			else if(evt.getPropertyName().equals(Const.C_NET_TTC_CALC))
				masterEntity.setNetTtcCalc((BigDecimal)evt.getNewValue());
			else if(evt.getPropertyName().equals(Const.C_NET_HT_CALC))
				masterEntity.setNetHtCalc((BigDecimal)evt.getNewValue());
			else if(evt.getPropertyName().equals(Const.C_NET_TVA_CALC))
				masterEntity.setNetTvaCalc((BigDecimal)evt.getNewValue());
			else if(evt.getPropertyName().equals(Const.C_TX_REM_TTC_DOCUMENT))
				masterEntity.setTxRemTtcDocument((BigDecimal)evt.getNewValue());
			else if(evt.getPropertyName().equals(Const.C_REM_TTC_DOCUMENT))
				masterEntity.setRemTtcDocument((BigDecimal)evt.getNewValue());
			else if(evt.getPropertyName().equals(Const.C_TX_REM_HT_DOCUMENT))
				masterEntity.setTxRemHtDocument((BigDecimal)evt.getNewValue());
			else if(evt.getPropertyName().equals(Const.C_REM_HT_DOCUMENT))
				masterEntity.setRemHtDocument((BigDecimal)evt.getNewValue());
			//#AFAIRE
//			else if(evt.getPropertyName().equals("ID_C_PAIEMENT"))
//				masterEntity.getSwtCPaiement().setIdCPaiement((Integer)evt.getNewValue());
//			else if(evt.getPropertyName().equals("CODE_T_PAIEMENT"))
//				masterEntity.getSwtTPaiement().setCODE_T_PAIEMENT((String)evt.getNewValue());
//			else if(evt.getPropertyName().equals("ID_T_PAIEMENT"))
//				masterEntity.getSwtTPaiement().setID_T_PAIEMENT((Integer)evt.getNewValue());
		}

	}


	private void initVue() {
		addExpandBarItem(vue.getExpandBar(), new PaCommentaire(vue
				.getExpandBar(),SWT.PUSH),"Commentaires",
				apporteurPlugin.getImageDescriptor("/icons/creditcards.png")
				.createImage());
		vue.getExpandBar().getItem(0).setExpanded(DocumentPlugin.getDefault().getPreferenceStore()
				.getBoolean(fr.legrain.document.preferences.PreferenceConstants.AFF_COMMENTAIRE));


	}
	
	public boolean impressionAuto() {
		return vue.getCbImprimer().getSelection();
	}

	public TaApporteur getMasterEntity() {
		return masterEntity;
	}

	public void setMasterEntity(TaApporteur masterEntity) {
		this.masterEntity = masterEntity;
	}

	public TaApporteurDAO getMasterDAO() {
		return masterDAO;
	}

	public void setMasterDAO(TaApporteurDAO masterDAO) {
		this.masterDAO = masterDAO;
		setDaoStandard(masterDAO);
	}
	
	public boolean changementPageValide(){
		if (masterDAO.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0
				|| masterDAO.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0) {
			//mise a jour de l'entite principale
			if(masterEntity!=null && selectedTotauxApporteur!=null && ((IHMTotauxApporteur) selectedTotauxApporteur)!=null) {
				mapperUIToModel.map((IHMTotauxApporteur)selectedTotauxApporteur,masterEntity);
			}
		}
//		dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
//		initEtatBouton();
		return true;
	};
	
		public void actCreateDoc() throws Exception {
			ParamAfficheChoixGenerationDocument param = new ParamAfficheChoixGenerationDocument();
			param.setTypeSrc(TypeDoc.TYPE_APPORTEUR);
			param.setDocumentSrc(masterEntity);
//			param.setDateDocument(masterEntity.getDateDocument());
			param.setCodeTiers(masterEntity.getTaTiers().getCodeTiers());
			Shell s = new Shell(vue.getShell(),LgrShellUtil.styleLgr);		
			PaChoixGenerationDocument paChoixGenerationDocument = new PaChoixGenerationDocument(s,SWT.NULL);
			PaChoixGenerationDocumentController paChoixGenerationDocumentController = new PaChoixGenerationDocumentController(paChoixGenerationDocument);
			paChoixGenerationDocumentController.addRetourEcranListener(getThis());
			ParamAfficheSWT paramAfficheSWT = new ParamAfficheSWT();
			paramAfficheSWT.setHauteur(PaChoixGenerationDocumentController.HAUTEUR);
			paramAfficheSWT.setLargeur(PaChoixGenerationDocumentController.LARGEUR);
			paramAfficheSWT.setTitre(PaChoixGenerationDocumentController.TITRE);
			LgrShellUtil.afficheSWT(param, paramAfficheSWT, paChoixGenerationDocument, paChoixGenerationDocumentController, s);
			if(param.getFocus()!=null)
				param.getFocusDefaut().requestFocus();
	}
		
		public void actCreateModele() throws Exception {
			ParamAfficheChoixGenerationDocument param = new ParamAfficheChoixGenerationDocument();
			param.setTypeSrc(TypeDoc.TYPE_APPORTEUR);
			param.setDocumentSrc(masterEntity);

			Shell s = new Shell(vue.getShell(),LgrShellUtil.styleLgr);		
			PaChoixGenerationDocument paChoixGenerationDocument = new PaChoixGenerationDocument(s,SWT.NULL);
			PaChoixGenerationModeleController paChoixGenerationDocumentController = new PaChoixGenerationModeleController(paChoixGenerationDocument);
			paChoixGenerationDocumentController.addRetourEcranListener(getThis());
			ParamAfficheSWT paramAfficheSWT = new ParamAfficheSWT();
			paramAfficheSWT.setHauteur(PaChoixGenerationModeleController.HAUTEUR);
			paramAfficheSWT.setLargeur(PaChoixGenerationModeleController.LARGEUR);
			paramAfficheSWT.setTitre(PaChoixGenerationModeleController.TITRE);
			LgrShellUtil.afficheSWT(param, paramAfficheSWT, paChoixGenerationDocument, paChoixGenerationDocumentController, s);
			if(param.getFocus()!=null)
				param.getFocusDefaut().requestFocus();
	}
		
		@Override
		protected void initFocusSWT(AbstractLgrDAO dao, Map<ModeObjet.EnumModeObjet,Control> focus) {
			switch (daoLocal.getModeObjet().getMode()) {
			case C_MO_INSERTION:
				if (daoLocal.getModeObjet().getFocusCourantSWT()==null)
					daoLocal.getModeObjet().setFocusCourantSWT(focus.get(
							ModeObjet.EnumModeObjet.C_MO_INSERTION));
				break;
			case C_MO_EDITION:
				if (daoLocal.getModeObjet().getFocusCourantSWT()==null){
					if (getFocusCourantSWT() instanceof Text){
						if (((Text)getFocusCourantSWT()).getEditable())
							daoLocal.getModeObjet().setFocusCourantSWT(getFocusCourantSWT());
					}
					if (getFocusCourantSWT() instanceof DateTime){
							break;
					}					
				}
				if (daoLocal.getModeObjet().getFocusCourantSWT()==null)
					daoLocal.getModeObjet().setFocusCourantSWT(
							focus.get(ModeObjet.EnumModeObjet.C_MO_EDITION));
				break;
			default:
				daoLocal.getModeObjet().setFocusCourantSWT(
						focus.get(ModeObjet.EnumModeObjet.C_MO_CONSULTATION));
			break;
			}
			setFocusCourantSWT(daoLocal.getModeObjet().getFocusCourantSWT());
			if(daoLocal.getModeObjet().getFocusCourantSWT()!=null)
				daoLocal.getModeObjet().getFocusCourantSWT().forceFocus();
		}


}

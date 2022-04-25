package fr.legrain.saisiecaisse.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.databinding.swt.SWTObservables;
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
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import fr.legrain.documents.dao.TaTPaiement;
import fr.legrain.documents.dao.TaTPaiementDAO;
import fr.legrain.edition.actions.AttributElementResport;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Document.SWTTPaiement;
import fr.legrain.gestCom.Module_SaisieCaisse.SWTEtablissement;
import fr.legrain.gestCom.Module_SaisieCaisse.SWTOperation;
import fr.legrain.gestCom.Module_SaisieCaisse.SWTTOperation;
import fr.legrain.gestCom.gestComBd.gestComBdPlugin;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideRechercheSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideRechercheControllerSWT;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDePageEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDeSelectionEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.AbstractLgrDAO;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.data.VerrouInterface;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.lib.gui.cdatetimeLgr;
import fr.legrain.lib.gui.aide.PaAideRechercheSWT;
import fr.legrain.lib.gui.aide.PaAideSWT;
import fr.legrain.lib.gui.aide.ParamAfficheAide;
import fr.legrain.lib.gui.aide.ResultAide;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.lib.gui.grille.LgrViewerSupport;
import fr.legrain.libMessageLGR.LgrMess;
import fr.legrain.saisieCaisse.dao.TaEtablissement;
import fr.legrain.saisieCaisse.dao.TaEtablissementDAO;
import fr.legrain.saisieCaisse.dao.TaOperation;
import fr.legrain.saisieCaisse.dao.TaOperationDAO;
import fr.legrain.saisieCaisse.dao.TaTOperation;
import fr.legrain.saisieCaisse.dao.TaTOperationDAO;
import fr.legrain.saisiecaisse.saisieCaissePlugin;
import fr.legrain.saisiecaisse.divers.MessagesEcran;
import fr.legrain.saisiecaisse.divers.ParamAfficheEtablissement;
import fr.legrain.saisiecaisse.divers.ParamAfficheOperation;
import fr.legrain.saisiecaisse.divers.ParamAfficheTOperation;
import fr.legrain.saisiecaisse.ecran.PaEtablissement;
import fr.legrain.saisiecaisse.ecran.PaOperation;
import fr.legrain.saisiecaisse.ecran.PaTOperation;
import fr.legrain.saisiecaisse.editor.EditorEtablissement;
import fr.legrain.saisiecaisse.editor.EditorInputOperation;
import fr.legrain.saisiecaisse.editor.EditorInputTOperation;
import fr.legrain.saisiecaisse.editor.EditorTOperation;
import fr.legrain.saisiecaisse.preferences.PreferenceConstants;
import fr.legrain.tiers.divers.ParamAfficheTPaiement;
import fr.legrain.tiers.ecran.PaTypePaiementSWT;
import fr.legrain.tiers.ecran.SWTPaTypePaiementController;
import fr.legrain.tiers.editor.EditorInputTypePaiement;
import fr.legrain.tiers.editor.EditorTypePaiement;



public class PaOperationController extends JPABaseControllerSWTStandard
implements RetourEcranListener {

	static Logger logger = Logger.getLogger(PaOperationController.class.getName()); 
	private PaOperation vue = null;
	private TaOperationDAO dao = null;//new TaOperationDAO();

	private Object ecranAppelant = null;
	private SWTOperation swtOperation;
	private SWTOperation swtOldOperation;
	private Realm realm;
	private DataBindingContext dbc;

	private Class classModel = SWTOperation.class;
	private ModelGeneralObjet<SWTOperation,TaOperationDAO,TaOperation> 
	modelOperation = null;//new ModelGeneralObjet<SWTOperation,TaOperationDAO,TaOperation>(dao,classModel);
	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private IObservableValue selectedOperation;
	private String[] listeChamp;
	private String nomClassController = this.getClass().getSimpleName();
	String oldCodeTOperation = "";
	private MapChangeListener changeListener = new MapChangeListener();

	private TaEtablissement masterEntity = null;
//	public static final String C_COMMAND_PRIX_ID = "fr.legrain.gestionCommerciale.articles.prix";
//	private HandlerAjoutPrix handlerAjoutPrix = new HandlerAjoutPrix();

	private LgrDozerMapper<SWTOperation,TaOperation> mapperUIToModel = new LgrDozerMapper<SWTOperation,TaOperation>();
	private LgrDozerMapper<TaOperation,SWTOperation> mapperModelToUI = new LgrDozerMapper<TaOperation,SWTOperation>();
	private TaOperation taOperation = null;

	private TaTOperation taTOperationOld;
	private TaTPaiement taTPaiementOld;
	private java.util.Date dateOld;
	/**
	 * les nouvelle variale
	 * mapAttributeTablHead ==> stocker les attributes de head Table
	 * mapAttributeTablDetail ==> stocker les attributes de Detail Table
	 */
	private LinkedHashMap<String,AttributElementResport> mapAttributeTablHead = new LinkedHashMap<String,AttributElementResport>(); 
	private LinkedHashMap<String,AttributElementResport> mapAttributeTablDetail = new LinkedHashMap<String,AttributElementResport>();
	
	public PaOperationController(PaOperation vue) {
		this(vue,null);
	}

	public PaOperationController(PaOperation vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaOperationDAO(getEm());
		modelOperation = new ModelGeneralObjet<SWTOperation,TaOperationDAO,TaOperation>(dao,classModel);
		setVue(vue);
		// a faire avant l'initialisation du Binding
		vue.getGrille().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setSwtOldOperation();
			}
		});
		vue.getShell().addShellListener(this);

		//TODO #JPA a supprimer completement au lieu de les caches
		//vue.getBtnAjoutPrix().setVisible(false);

		// Branchement action annuler : empeche la fermeture automatique de la
		// fenetre sur ESC
		vue.getShell().addTraverseListener(new Traverse());

		vue.getTfDATE_OPERATION().addTraverseListener(new DateTraverse());
		initController();
		vue.getTfDATE_OPERATION().addFocusListener(dateFocusListener);

	}

	private void initController() {
		try {
			setGrille(vue.getGrille());
			initSashFormWeight();
			setDaoStandard(dao);
			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);

			initMapComposantChamps();
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
			vue.getTfDATE_OPERATION().setMenu(popupMenuFormulaire);
			vue.getPaGrille().setMenu(popupMenuGrille);
			vue.getGrille().setMenu(popupMenuGrille);
			initComposantsVue();
			initEtatBouton();
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaArticlesController", e);
		}
	}

	public void bind(PaOperation paOperation) {
		try {
			
			
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			tableViewer = new LgrTableViewer(paOperation.getGrille());
			tableViewer.createTableCol(classModel,paOperation.getGrille(), nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE,0);
			listeChamp = tableViewer.setListeChampGrille(nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE);

//			ObservableListContentProvider viewerContent = new ObservableListContentProvider();
//			tableViewer.setContentProvider(viewerContent);
//
//			IObservableMap[] attributeMaps = BeansObservables.observeMaps(
//					viewerContent.getKnownElements(), classModel, listeChamp);
//
//			tableViewer.setLabelProvider(new ObservableMapLabelProvider(attributeMaps));
//			writableList = new WritableList(realm, modelOperation.remplirListe(), classModel);
//			tableViewer.setInput(writableList);
			
			// Set up data binding.
			LgrViewerSupport.bind(tableViewer, 
					new WritableList(modelOperation.remplirListe(), classModel),
					BeanProperties.values(listeChamp)
					);

			selectedOperation = ViewersObservables.observeSingleSelection(tableViewer);
			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			tableViewer.selectionGrille(0);

			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);

			bindingFormMaitreDetail(dbc, realm, selectedOperation,classModel);

			selectedOperation.addChangeListener(new IChangeListener() {

				public void handleChange(ChangeEvent event) {
					if(selectedOperation!=null && selectedOperation.getValue()!=null) {
						if(((SWTOperation) selectedOperation.getValue()).getIdOperation()!=null) {
							taOperation = dao.findById(((SWTOperation) selectedOperation.getValue()).getIdOperation());
						}
						//null a tester ailleurs, car peut etre null en cas d'insertion
						fireChangementDeSelection(new ChangementDeSelectionEvent(PaOperationController.this));
					}
				}

			});

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
				vue.getLaTitreFormulaire().setText(ParamAfficheOperation.C_TITRE_FORMULAIRE);
			}

			if(param.getTitreGrille()!=null){
				vue.getLaTitreGrille().setText(param.getTitreGrille());
			} else {
				vue.getLaTitreGrille().setText(ParamAfficheOperation.C_TITRE_GRILLE);
			}

			if(param.getSousTitre()!=null){
				vue.getLaTitreFenetre().setText(param.getSousTitre());
			} else {
				vue.getLaTitreFenetre().setText(ParamAfficheOperation.C_SOUS_TITRE);
			}

			if (param.getEcranAppelant() != null) {
				ecranAppelant = param.getEcranAppelant();
			}
			dao.setMasterEntity(masterEntity);
			modelOperation.setListeEntity(null);
			if(tableViewer==null) {
				//databinding pas encore realise
				bind(vue);
				tableViewer.tri(classModel, nomClassController,Const.C_FICHIER_LISTE_CHAMP_GRILLE);
				tableViewer.selectionGrille(0);
			} else {
				try {
					taOperation=null;
					actRefresh();
				} catch (Exception e) {
					logger.error("",e);
				}
			}
			VerrouInterface.setVerrouille(false);
			setSwtOldOperation();


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
		vue.getTfETABLISSEMENT().setEditable(false);
		vue.getTfETABLISSEMENT().setEnabled(false);
	}

	protected void initMapComposantChamps() {
		if (mapComposantChamps == null)
			mapComposantChamps = new LinkedHashMap<Control, String>();

		if (listeComposantFocusable == null)
			listeComposantFocusable = new ArrayList<Control>();
		listeComposantFocusable.add(vue.getGrille());

		vue.getTfDATE_OPERATION().setToolTipText(Const.C_DATE_OPERATION);
		vue.getTfCODE_T_OPERATION().setToolTipText(Const.C_CODE_T_OPERATION);
		vue.getTfT_PAIEMENT().setToolTipText(Const.C_CODE_T_PAIEMENT);
		vue.getTfETABLISSEMENT().setToolTipText(Const.C_NOM_ETABLISSEMENT);
		vue.getTfMONTANT_OPERATION().setToolTipText(Const.C_MONTANT_OPERATION);
		vue.getTfLIB_OPERATION().setToolTipText(Const.C_LIBL_OPERATION);
		vue.getTfCOMPTE().setToolTipText(Const.C_COMPTE);
		vue.getTfTVA().setToolTipText(Const.C_TVA);


		mapComposantChamps.put(vue.getTfDATE_OPERATION(), Const.C_DATE_OPERATION);
		mapComposantChamps.put(vue.getTfCODE_T_OPERATION(), Const.C_CODE_T_OPERATION);
		mapComposantChamps.put(vue.getTfT_PAIEMENT(), Const.C_CODE_T_PAIEMENT);
		mapComposantChamps.put(vue.getTfETABLISSEMENT(), Const.C_NOM_ETABLISSEMENT);
		mapComposantChamps.put(vue.getTfMONTANT_OPERATION(), Const.C_MONTANT_OPERATION);
		mapComposantChamps.put(vue.getTfLIB_OPERATION(), Const.C_LIBL_OPERATION);
		mapComposantChamps.put(vue.getTfCOMPTE(), Const.C_COMPTE);
		mapComposantChamps.put(vue.getTfTVA(), Const.C_TVA);

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

		if (mapInitFocus == null)
			mapInitFocus = new LinkedHashMap<ModeObjet.EnumModeObjet, Control>();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION, vue
				.getTfCODE_T_OPERATION());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue
				.getTfCODE_T_OPERATION());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION, vue
				.getGrille());

		activeModifytListener();

		vue.getTfMONTANT_OPERATION().addVerifyListener(queDesChiffres);
		vue.getTfTVA().addVerifyListener(queDesChiffres);
		vue.getTfCOMPTE().addVerifyListener(queDesChiffresPositifs);
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

		mapCommand.put(C_COMMAND_GLOBAL_PRECEDENT_ID, handlerPrecedent);
		mapCommand.put(C_COMMAND_GLOBAL_SUIVANT_ID, handlerSuivant);

//		mapCommand.put(C_COMMAND_PRIX_ID, handlerAjoutPrix);
		mapCommand.put(C_COMMAND_GLOBAL_SELECTION_ID, handlerSelection);

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
//		mapActions.put(vue.getBtnAjoutPrix(), C_COMMAND_PRIX_ID);

		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID, C_COMMAND_GLOBAL_REFRESH_ID, C_COMMAND_GLOBAL_SELECTION_ID };
		mapActions.put(null, tabActionsAutres);

	}

	protected void initEtatBouton() {
		initEtatBoutonCommand();
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
					.getString("Operation.Message.Enregistrer"))) {

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
				setListeEntity(getModelOperation().remplirListe());
				dao.initValeurIdTable(taOperation);
				fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
						dao.getChampIdEntite(), dao.getValeurIdTable(),
						selectedOperation.getValue())));

				retour = true;
			}
		}
		return retour;
	}
	public void retourEcran(final RetourEcranEvent evt) {
		try {
			if (evt.getRetour() != null
					&& (evt.getSource() instanceof SWTPaAideControllerSWT 
							&& !evt.getRetour().getResult().equals(""))) {			
				if (getFocusAvantAideSWT() instanceof Text) {
					try {
						((Text) getFocusAvantAideSWT()).setText(((ResultAffiche) evt.getRetour()).getResult());					
						if(getFocusAvantAideSWT().equals(vue.getTfT_PAIEMENT())){
							TaTPaiement f = null;
							TaTPaiementDAO t = new TaTPaiementDAO(getEm());
							f = t.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
							t = null;
							taOperation.setTaTPaiement(f);
						}
						if(getFocusAvantAideSWT().equals(vue.getTfCODE_T_OPERATION())){
							TaTOperation f = null;
							TaTOperationDAO t = new TaTOperationDAO(getEm());
							f = t.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
							t = null;
							taOperation.setTaTOperation(f);
						}		
						if(getFocusAvantAideSWT().equals(vue.getTfETABLISSEMENT())){
							TaEtablissement f = null;
							TaEtablissementDAO t = new TaEtablissementDAO(getEm());
							f = t.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
							t = null;
							taOperation.setTaEtablissement(f);
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
		try {
			if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
				VerrouInterface.setVerrouille(true);
				setSwtOldOperation();
				swtOperation = new SWTOperation();
				taOperation = new TaOperation();

				swtOperation.setIdEtablissement(masterEntity.getIdEtablissement());
				taOperation.setTaEtablissement(masterEntity);
				swtOperation.setNomEtablissement(masterEntity.getNomEtablissement());
				if(dateOld!=null){
					swtOperation.setDateOperation(dateOld);
					taOperation.setDateOperation(dateOld);
				}
				if(taTPaiementOld!=null){
					swtOperation.setCodeTPaiement(taTPaiementOld.getCodeTPaiement());
					taOperation.setTaTPaiement(taTPaiementOld);					
				}
				else{
					swtOperation.setCodeTPaiement(masterEntity.getTaTPaiement().getCodeTPaiement());
					taOperation.setTaTPaiement(masterEntity.getTaTPaiement());
				}
				if(taTOperationOld!=null){
					swtOperation.setCodeTOperation(taTOperationOld.getCodeTOperation());
					taOperation.setTaTOperation(taTOperationOld);
				}
				
				dao.inserer(taOperation);
				modelOperation.getListeObjet().add(swtOperation);
				writableList = new WritableList(realm, modelOperation.getListeObjet(), classModel);
				tableViewer.setInput(writableList);
				tableViewer.refresh();
				tableViewer.setSelection(new StructuredSelection(swtOperation));
				initEtatBouton();


			}

		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		} finally {
			initEtatComposant();
			VerrouInterface.setVerrouille(false);
		}
	}
	public void setSwtOldOperationRefresh() {
		if (selectedOperation.getValue()!=null){
			modelOperation.removeEntity(taOperation);
			if(LgrMess.isDOSSIER_EN_RESEAU())taOperation = dao.refresh(dao.findById(
					((SWTOperation) selectedOperation.getValue()).getIdEtablissement()));
			modelOperation.addEntity(taOperation);
			SWTOperation oldSwtDepot =(SWTOperation) selectedOperation.getValue(); 
			new LgrDozerMapper().map(taOperation, swtOldOperation);
			if(!oldSwtDepot.equals(swtOldOperation)){
				try {
					if(LgrMess.isDOSSIER_EN_RESEAU())actRefresh();
				} catch (Exception e) {
					logger.error("",e);
				}
			}
			this.swtOldOperation=SWTOperation.copy((SWTOperation)selectedOperation.getValue());
		}
	}
	@Override
	protected void actModifier() throws Exception {
		try {
			if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
				setSwtOldOperationRefresh();
//				if (getSwtOldOperation().getCodeTOperation()!=null)
//					oldCodeTOperation=getSwtOldOperation().getCodeTOperation();
//				taOperation = dao.findById(((SWTOperation) selectedOperation.getValue()).getIdOperation());
				dao.modifier(taOperation);

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
			VerrouInterface.setVerrouille(true);
			if(isUtilise())MessageDialog.openInformation(vue.getShell(), MessagesEcran
					.getString("Message.Attention"), MessagesEcran
					.getString("Message.suppression"));
			else
				if (MessageDialog.openConfirm(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("Operation.Message.Supprimer"))) {
					dao.begin(transaction);
					TaOperation u = dao.findById(((SWTOperation) selectedOperation.getValue()).getIdOperation());
					dao.supprimer(u);
					modelOperation.removeEntity(taOperation);
					taOperation=null;
					dao.commit(transaction);
					dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
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
		try {
			VerrouInterface.setVerrouille(true);
			switch (dao.getModeObjet().getMode()) {
			case C_MO_INSERTION:
				if (MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("Operation.Message.Annuler"))) {

					if(((SWTOperation) selectedOperation.getValue()).getIdOperation()==null){
						modelOperation.getListeObjet().remove(((SWTOperation) selectedOperation.getValue()));
						writableList = new WritableList(realm, modelOperation.getListeObjet(), classModel);
						tableViewer.setInput(writableList);
						tableViewer.refresh();
						tableViewer.selectionGrille(0);
					}
					dao.annuler(taOperation);
					hideDecoratedFields();
				}
				initEtatBouton();
				break;
			case C_MO_EDITION:
				if (MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("Operation.Message.Annuler"))) {
					int rang = modelOperation.getListeObjet().indexOf(selectedOperation.getValue());
					modelOperation.getListeObjet().set(rang, swtOldOperation);
					writableList = new WritableList(realm, modelOperation.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.setSelection(new StructuredSelection(swtOldOperation), true);
					dao.annuler(taOperation);
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
	}

	@Override
	protected boolean aideDisponible() {
		boolean result = false;
		switch ((PaOperationController.this.dao.getModeObjet().getMode())) {
		case C_MO_CONSULTATION:
			if(getFocusCourantSWT().equals(vue.getGrille())) 
				result = true;
			break;

		case C_MO_EDITION:
		case C_MO_INSERTION:
			if(getFocusCourantSWT().equals(vue.getTfCODE_T_OPERATION())
					|| getFocusCourantSWT().equals(vue.getTfT_PAIEMENT())
					|| getFocusCourantSWT().equals(vue.getTfETABLISSEMENT()))
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

				switch ((PaOperationController.this.dao.getModeObjet().getMode())) {
				case C_MO_CONSULTATION:
					break;

				case C_MO_EDITION:
				case C_MO_INSERTION:
					if (getFocusCourantSWT().equals(vue.getTfT_PAIEMENT())) {
						PaTypePaiementSWT paTypePaiementSWT = new PaTypePaiementSWT(s2,SWT.NULL);
						SWTPaTypePaiementController swtPaTypePaiementController = new SWTPaTypePaiementController(paTypePaiementSWT);
						swtPaTypePaiementController.bind(paTypePaiementSWT);
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
						paramAfficheAideRecherche.setControllerAppelant(PaOperationController.this);
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTPaiement,TaTPaiementDAO,TaTPaiement>(SWTTPaiement.class,getEm()));
						
						paramAfficheAideRecherche.setTypeObjet(swtPaTypePaiementController.getClassModel());
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_T_PAIEMENT);
					}

					if(getFocusCourantSWT().equals(vue.getTfCODE_T_OPERATION())){
						PaTOperation paTOperation = new PaTOperation(s2,SWT.NULL);
						PaTOperationController swtPaTOperationController = new PaTOperationController(paTOperation);

						editorCreationId = EditorTOperation.ID;
						editorInputCreation = new EditorInputTOperation();

						ParamAfficheTOperation paramAfficheArticles = new ParamAfficheTOperation();
						paramAfficheAideRecherche.setJPQLQuery(new TaTOperationDAO(getEm()).getJPQLQuery());
						paramAfficheArticles.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheArticles.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaTOperationController;
						parametreEcranCreation = paramAfficheArticles;

						paramAfficheAideRecherche.setTypeEntite(TaTOperation.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_T_OPERATION);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_T_OPERATION().getText());
						paramAfficheAideRecherche.setControllerAppelant(PaOperationController.this);
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTOperation,TaTOperationDAO,TaTOperation>(SWTTOperation.class,getEm()));
						paramAfficheAideRecherche.setTypeObjet(swtPaTOperationController.getClassModel());
						paramAfficheAideRecherche.setChampsIdentifiant(swtPaTOperationController.getDao().getChampIdTable());
					}
					if (getFocusCourantSWT().equals(vue.getTfETABLISSEMENT())) {
						PaEtablissement paEtablissement = new PaEtablissement(s2,SWT.NULL);
						PaEtablissementController swtPaEtablissementController = new PaEtablissementController(paEtablissement);

						editorCreationId = EditorEtablissement.ID;
						editorInputCreation = new EditorInputOperation();
						
						ParamAfficheEtablissement paramAfficheEtablissement = new ParamAfficheEtablissement();
						paramAfficheAideRecherche.setJPQLQuery(new TaEtablissementDAO(getEm()).getJPQLQuery());
						paramAfficheEtablissement.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheEtablissement.setEcranAppelant(paAideController);
						
						controllerEcranCreation = swtPaEtablissementController;
						parametreEcranCreation = paramAfficheEtablissement;

						paramAfficheAideRecherche.setTypeEntite(TaEtablissement.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_NOM_ETABLISSEMENT);
						paramAfficheAideRecherche.setDebutRecherche("");
						paramAfficheAideRecherche.setControllerAppelant(PaOperationController.this);
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTEtablissement,TaEtablissementDAO,TaEtablissement>(SWTEtablissement.class,getEm()));

						paramAfficheAideRecherche.setTypeObjet(swtPaEtablissementController.getClassModel());
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_ETABLISSEMENT);
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
					paAideController.addRetourEcranListener(PaOperationController.this);
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



	public IStatus validateUI() throws Exception {
		if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
				|| (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
			ctrlTousLesChampsAvantEnregistrementSWT();
			mapperUIToModel.map((SWTOperation) selectedOperation.getValue(),taOperation);
		}
		return null;
	}

	public IStatus validateUIField(String nomChamp,Object value) {
		String validationContext = "OPERATION";
		try {
			IStatus s = null;
			if(nomChamp.equals(Const.C_CODE_T_PAIEMENT)) {
				TaTPaiementDAO dao = new TaTPaiementDAO(getEm());
				dao.setModeObjet(getDao().getModeObjet());
				TaTPaiement f = new TaTPaiement();
				PropertyUtils.setSimpleProperty(f, nomChamp, value);

				s = dao.validate(f,nomChamp,validationContext);

				if(s.getSeverity()!=IStatus.ERROR ){
					f = dao.findByCode((String)value);
					taOperation.setTaTPaiement(f);
				}else
					if(value==null || value.equals("")){
						value=masterEntity.getTaTPaiement().getCodeTPaiement();
						((SWTOperation)selectedOperation.getValue()).
						    setCodeTPaiement(masterEntity.getTaTPaiement().getCodeTPaiement());
						return new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
					}
					
				dao = null;
			}else
				if(nomChamp.equals(Const.C_NOM_ETABLISSEMENT)) {
					TaEtablissementDAO dao = new TaEtablissementDAO(getEm());

					dao.setModeObjet(getDao().getModeObjet());
					TaEtablissement f = new TaEtablissement();
					PropertyUtils.setSimpleProperty(f, nomChamp, value);

					s = dao.validate(f,nomChamp,validationContext);

					if(s.getSeverity()!=IStatus.ERROR ){
						f = dao.findByCode((String)value);
						taOperation.setTaEtablissement(f);
					}
					dao = null;
				}else				
					if(nomChamp.equals(Const.C_CODE_T_OPERATION)) {
				

				
				TaTOperationDAO dao = new TaTOperationDAO(getEm());

				dao.setModeObjet(getDao().getModeObjet());
				TaTOperation f = new TaTOperation();
				PropertyUtils.setSimpleProperty(f, nomChamp, value);
				s = dao.validate(f,nomChamp,validationContext);

				if(s.getSeverity()!=IStatus.ERROR ){
					f = dao.findByCode((String)value);
					if(!value.equals(oldCodeTOperation))
						taOperation.setCompte("");
					taOperation.setTaTOperation(f);
					oldCodeTOperation=f.getCodeTOperation();
					if(f.getCodeTOperation().startsWith(saisieCaissePlugin.getDefault().getPreferenceStore().getString(
							PreferenceConstants.FIXE_OP_ACHAT)))
						taOperation.setCompte(saisieCaissePlugin.getDefault().getPreferenceStore().getString(
								PreferenceConstants.COMPTE_ACHAT));
					((SWTOperation)selectedOperation.getValue()).setCompte(taOperation.getCompte());
				}
				dao = null;
			} else {
				TaOperation u = new TaOperation();
				PropertyUtils.setSimpleProperty(u, nomChamp, value);
				if(((SWTOperation) selectedOperation.getValue()).getIdOperation()!=null) {
					u.setIdOperation(((SWTOperation) selectedOperation.getValue()).getIdOperation());
				}
				if(nomChamp.equals(Const.C_DATE_OPERATION)) {
					taOperation.setDateOperation(u.getDateOperation());
					s = new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
					}
				else
				s = dao.validate(u,nomChamp,validationContext);
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
		EntityTransaction transaction = null;
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

				transaction = dao.getEntityManager().getTransaction();
				dao.begin(transaction);

				if(declanchementExterne) {
					mapperUIToModel.map((SWTOperation) selectedOperation.getValue(),taOperation);
				}
				if((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0))	
					{
					taOperation=dao.enregistrerMerge(taOperation);
					modelOperation.getListeEntity().add(taOperation);
					}
				else taOperation=dao.enregistrerMerge(taOperation);

				dao.commit(transaction);
				transaction = null;
				setTaTOperationOld(taOperation.getTaTOperation());
				setTaTPaiementOld(taOperation.getTaTPaiement());
				setDateOld(taOperation.getDateOperation());

				actRefresh(); //deja present
			}
		} catch (Exception e) {
			logger.error("",e);
		} finally {
			if(transaction!=null && transaction.isActive()) {
				transaction.rollback();
			}
			initEtatBouton();
		}
	}



	public void initEtatComposant() {
//		try {
//			vue.getTfCODE_ARTICLE().setEditable(!isUtilise());
//			changeCouleur(vue);
//		} catch (Exception e) {
//			vue.getLaMessage().setText(e.getMessage());
//		}
	}
	public boolean isUtilise(){
		return false;
//		return ((SWTArticle)selectedOperation.getValue()).getIdArticle()!=null && 
//		!dao.recordModifiable(dao.getNomTable(),
//				((SWTArticle)selectedOperation.getValue()).getIdArticle());		
	}
	
	public SWTOperation getSwtOldOperation() {
		return swtOldOperation;
	}

	public void setSwtOldArticle(SWTOperation swtOldArticle) {
		this.swtOldOperation = swtOldArticle;
	}

	public void setSwtOldOperation() {
		if (selectedOperation!=null && selectedOperation.getValue() != null)
			this.swtOldOperation = SWTOperation.copy((SWTOperation) selectedOperation.getValue());
		else {
			if(tableViewer.selectionGrille(0)){
				this.swtOldOperation = SWTOperation.copy((SWTOperation) selectedOperation.getValue());
				tableViewer.setSelection(new StructuredSelection(
						(SWTOperation) selectedOperation.getValue()), true);
			}}
	}

	public void setVue(PaOperation vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();

		mapComposantDecoratedField.put(vue.getTfDATE_OPERATION(), vue.getFieldDATE_OPERATION());
		mapComposantDecoratedField.put(vue.getTfCODE_T_OPERATION(), vue.getFieldCODE_T_OPERATION());
		mapComposantDecoratedField.put(vue.getTfT_PAIEMENT(), vue.getFieldT_PAIEMENT());
		mapComposantDecoratedField.put(vue.getTfETABLISSEMENT(), vue.getFieldETABLISSEMENT());
		mapComposantDecoratedField.put(vue.getTfMONTANT_OPERATION(), vue.getFieldMONTANT_OPERATION());
		mapComposantDecoratedField.put(vue.getTfLIB_OPERATION(), vue.getFieldLIB_OPERATION());
		mapComposantDecoratedField.put(vue.getTfCOMPTE(), vue.getFieldCOMPTE());
		mapComposantDecoratedField.put(vue.getTfTVA(), vue.getFieldTVA());
	}

	protected class HandlerAjoutPrix extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException{
			try {
				//				LgrPartListener.getLgrPartListener().setLgrActivePart(null);
				//				IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new EditorInputPrix(), EditorPrix.ID);
				//				LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
				//				LgrPartListener.getLgrPartListener().setLgrActivePart(e);
				//				//SWTPaPrixController swtPaPrixController = new SWTPaPrixController(((EditorPrix)e).getComposite());
				//				
				//				ParamAffichePrix paramAffichePrix = new ParamAffichePrix();
				//				paramAffichePrix.setIdArticle(LibConversion.integerToString(((SWTArticle)selectedArticle.getValue()).getIdArticle()));
				//				((JPALgrEditorPart)e).setPanel(((JPALgrEditorPart)e).getController().getVue());
				//				((JPALgrEditorPart)e).getController().configPanel(paramAffichePrix);
				MessageDialog.openInformation(vue.getShell(), "Avertissement",
				"Non implémentée");
			} catch (Exception e1) {
				logger.error("Erreur : actionPerformed", e1);
			}
			return event;
		}
	}

	protected void actSelection() throws Exception {
		try{
//			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
//			openEditor(new fr.legrain.visualisation.editor.EditorInputSelectionVisualisation(), 
//					fr.legrain.visualisation.editor.EditorSelectionVisualisation.ID);
//			LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
//
//			ParamAfficheVisualisation paramAfficheSelectionVisualisation = new ParamAfficheVisualisation();
//			paramAfficheSelectionVisualisation.setModule("saisieCaisse");
//			paramAfficheSelectionVisualisation.setNomClassController(nomClassController);
//			paramAfficheSelectionVisualisation.setNomRequete(Const.C_NOM_VU_ARTICLE);
//
//			((LgrEditorPart)e).setPanel(((LgrEditorPart)e).getControllerSelection().getVue());
//			((LgrEditorPart)e).getControllerSelection().configPanel(paramAfficheSelectionVisualisation);

		}catch (Exception e) {
			logger.error("",e);
		}	
	}



//	class ActionAjoutPrix extends Action {
//		public ActionAjoutPrix(String name) {
//			super(name);
//		}
//
//		public void run() {
//			super.run();
//			try {
//				handlerService.executeCommand(C_COMMAND_PRIX_ID, null);
//			} catch (ExecutionException e) {
//				logger.error("",e);
//			} catch (NotDefinedException e) {
//				logger.error("",e);
//			} catch (NotEnabledException e) {
//				logger.error("",e);
//			} catch (NotHandledException e) {
//				logger.error("",e);
//			}
//		}
//	}

	public Class getClassModel() {
		return classModel;
	}

	@Override
	protected void sortieChamps() {
		// TODO Raccord de methode auto-genere

		try {
			if(getActiveAide()) {
				if(LgrPartListener.getLgrPartListener().getLgrActivePart()!=null)
					LgrPartListener.getLgrPartListener().getLgrActivePart().setFocus();
			}
		} catch (Exception e) {
			logger.error("",e);
		}
	}

	@Override
	protected void actRefresh() throws Exception {
		if (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION) == 0) {
			//rafraichissement des valeurs dans la grille
			writableList = new WritableList(realm, modelOperation.remplirListe(), classModel);
			tableViewer.setInput(writableList);
			tableViewer.refresh();
		} else {
			if (taOperation!=null && selectedOperation!=null && (SWTOperation) selectedOperation.getValue()!=null) {
				mapperModelToUI.map(taOperation, (SWTOperation) selectedOperation.getValue());
			}
		}

		//repositionnement sur la valeur courante
		int idActuel = 0;
		if (taOperation!=null) { //enregistrement en cours de modification/insertion
			idActuel = taOperation.getIdOperation();
		} else if(selectedOperation!=null && (SWTOperation) selectedOperation.getValue()!=null) {
			idActuel = ((SWTOperation) selectedOperation.getValue()).getIdOperation();
		}

		if(idActuel!=0) {
			tableViewer.setSelection(new StructuredSelection(modelOperation.recherche(Const.C_ID_OPERATION
					, idActuel)));
		}else
			tableViewer.selectionGrille(0);				
	}

	public ModelGeneralObjet<SWTOperation,TaOperationDAO,TaOperation>  getModelOperation() {
		return modelOperation;
	}

	public SWTOperation getSwtOperation() {
		return swtOperation;
	}

	public TaOperation getTaOperation() {
		return taOperation;
	}

	public TaOperationDAO getDao() {
		return dao;
	}

	public boolean changementPageValide(){
		return true;		
		//return (daoStandard.selectAll().size()>0);
	}

	public TaEtablissement getMasterEntity() {
		return masterEntity;
	}

	public void setMasterEntity(TaEtablissement masterEntity) {
		this.masterEntity = masterEntity;
	}

	public TaTOperation getTaTOperationOld() {
		return taTOperationOld;
	}

	public void setTaTOperationOld(TaTOperation taTOperationOld) {
		this.taTOperationOld = taTOperationOld;
	}

	public TaTPaiement getTaTPaiementOld() {
		return taTPaiementOld;
	}

	public void setTaTPaiementOld(TaTPaiement taTPaiementOld) {
		this.taTPaiementOld = taTPaiementOld;
	}

	public java.util.Date getDateOld() {
		return dateOld;
	}

	public void setDateOld(java.util.Date dateOld) {
		this.dateOld = dateOld;
	}
	/**
	 * Positionnement du focus en fonction du mode de l'écran
	 * @param dao - Ensemble de données utilisé dans l'écran du controller
	 * @param focus - [clé : mode] [valeur : composant qui à le focus par défaut pour ce mode]
	 */
	protected void initFocusSWT(AbstractLgrDAO dao, Map<ModeObjet.EnumModeObjet,Control> focus) {
		switch (dao.getModeObjet().getMode()) {
		case C_MO_INSERTION:
			int v = saisieCaissePlugin.getDefault().getPreferenceStore().
			   getInt(PreferenceConstants.FOCUS_INSERTION);
			if(v!=0)
				dao.getModeObjet().setFocusCourantSWT(listeComposantFocusable.get(v));
			if (dao.getModeObjet().getFocusCourantSWT()==null)
				dao.getModeObjet().setFocusCourantSWT(focus.get(ModeObjet.EnumModeObjet.C_MO_INSERTION));
			
			break;
		case C_MO_EDITION:
			if (dao.getModeObjet().getFocusCourantSWT()==null){
				if (getFocusCourantSWT() instanceof Text){
					if (((Text)getFocusCourantSWT()).getEditable())
						dao.getModeObjet().setFocusCourantSWT(getFocusCourantSWT());
				}
				if (getFocusCourantSWT() instanceof DateTime){
						break;
				}					
			}
			if (dao.getModeObjet().getFocusCourantSWT()==null)
				dao.getModeObjet().setFocusCourantSWT(focus.get(ModeObjet.EnumModeObjet.C_MO_EDITION));
			break;
		default:
			dao.getModeObjet().setFocusCourantSWT(focus.get(ModeObjet.EnumModeObjet.C_MO_CONSULTATION));
		break;
		}
		setFocusCourantSWT(dao.getModeObjet().getFocusCourantSWT());
		if(dao.getModeObjet().getFocusCourantSWT()!=null)
			dao.getModeObjet().getFocusCourantSWT().forceFocus();
	}

}

package fr.legrain.articles.ecran;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
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
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaPrix;
import fr.legrain.articles.dao.TaRefPrix;
import fr.legrain.articles.dao.TaRefPrixDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Articles.SWTRefPrix;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.InfosVerifSaisie;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.data.VerrouInterface;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.lib.gui.aide.ResultAide;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.lib.gui.grille.LgrViewerSupport;
import fr.legrain.tiers.dao.TaAdresse;
import fr.legrain.tiers.dao.TaTAdr;


public class SWTPaRefPrixController extends JPABaseControllerSWTStandard
implements RetourEcranListener {

	static Logger logger = Logger.getLogger(SWTPaRefPrixController.class.getName());
	private PaRefPrixSWT vue = null;
//	private SWT_IB_TA_REF_PRIX ibTaTable = new SWT_IB_TA_REF_PRIX();
	private TaRefPrixDAO dao = null;//new TaRefPrixDAO(getEm());
	private String idArticle = null;
	private String idPrix = null;

	private Object ecranAppelant = null;
	private SWTRefPrix swtRefPrix;
	private SWTRefPrix swtOldRefPrix;
	private Realm realm;
	private DataBindingContext dbc;

	private Class classModel = SWTRefPrix.class;
	private ModelGeneralObjet<SWTRefPrix,TaRefPrixDAO,TaRefPrix> modelRefPrix = null;//new ModelGeneralObjet<SWTRefPrix,TaRefPrixDAO,TaRefPrix>(dao,classModel);
	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private IObservableValue selectedRefPrix;
	private String[] listeChamp;
	private String nomClassController = this.getClass().getSimpleName();


	private MapChangeListener changeListener = new MapChangeListener();
	
	public SWTPaRefPrixController(PaRefPrixSWT vue) {
		this(vue,null);
	}

	public SWTPaRefPrixController(PaRefPrixSWT vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaRefPrixDAO(getEm());
		modelRefPrix = new ModelGeneralObjet<SWTRefPrix,TaRefPrixDAO,TaRefPrix>(dao,classModel);
		setVue(vue);

		// a faire avant l'initialisation du Binding
		vue.getGrille().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setSwtOldRefPrix();
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
			vue.getPaGrille().setMenu(popupMenuGrille);

			initEtatBouton();
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaArticlesController", e);
		}
	}

	public void bind(PaRefPrixSWT paRefPrixSWT) {
		try {
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			tableViewer = new LgrTableViewer(paRefPrixSWT.getGrille());
			tableViewer.createTableCol(classModel,paRefPrixSWT.getGrille(), nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE,-1);
			listeChamp = tableViewer.setListeChampGrille(nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE);

//			ObservableListContentProvider viewerContent = new ObservableListContentProvider();
//			tableViewer.setContentProvider(viewerContent);
//
//			IObservableMap[] attributeMaps = BeansObservables.observeMaps(
//					viewerContent.getKnownElements(), classModel,listeChamp);
//
//			tableViewer.setLabelProvider(new ObservableMapLabelProvider(attributeMaps));
//			writableList = new WritableList(realm, modelRefPrix.remplirListe(), classModel);
//			tableViewer.setInput(writableList);
			
			// Set up data binding.
			LgrViewerSupport.bind(tableViewer, 
					new WritableList(modelRefPrix.remplirListe(), classModel),
					BeanProperties.values(listeChamp)
					);

			selectedRefPrix = ViewersObservables.observeSingleSelection(tableViewer);

			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			tableViewer.selectionGrille(0);

			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);

			bindingFormMaitreDetail(dbc, realm, selectedRefPrix,classModel);

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
			if (((ParamAfficheUnite) param).getFocusDefautSWT() != null)
				((ParamAfficheUnite) param).getFocusDefautSWT().setFocus();
			else
				((ParamAfficheUnite) param).setFocusDefautSWT(vue.getGrille());
			vue.getLaTitreFormulaire().setText(((ParamAfficheUnite) param).getTitreFormulaire());
			vue.getLaTitreGrille().setText(((ParamAfficheUnite) param).getTitreGrille());
			vue.getLaTitreFenetre().setText(((ParamAfficheUnite) param).getSousTitre());

			if (param.getEcranAppelant() != null) {
				ecranAppelant = param.getEcranAppelant();
			}
			

			bind(vue);
			// permet de se positionner sur le 1er enregistrement et de remplir
			// le formulaire
			tableViewer.selectionGrille(0);
			tableViewer.tri(classModel, nomClassController,Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			VerrouInterface.setVerrouille(false);
			setSwtOldRefPrix();

			if (param.getModeEcran() != null
					&& param.getModeEcran().compareTo(
							EnumModeObjet.C_MO_INSERTION) == 0) {
				try {
					actInserer();
				} catch (Exception e) {
					vue.getLaMessage().setText(e.getMessage());
					logger.error("", e);
				}
			} else {
				initEtatBouton();
			}

		}
		return null;
	}
	
	protected void initVerifySaisie() {
		if (mapInfosVerifSaisie == null)
			mapInfosVerifSaisie = new HashMap<Control, InfosVerifSaisie>();
		
		mapInfosVerifSaisie.put(vue.getTfCODE_ARTICLE(), new InfosVerifSaisie(new TaArticle(),Const.C_CODE_ARTICLE,null));
		mapInfosVerifSaisie.put(vue.getTfPRIX_PRIX(), new InfosVerifSaisie(new TaPrix(),Const.C_PRIX_PRIX,null));

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

		vue.getTfCODE_ARTICLE().setToolTipText(Const.C_CODE_ARTICLE);
		vue.getTfPRIX_PRIX().setToolTipText(Const.C_PRIX_PRIX);

		mapComposantChamps.put(vue.getTfCODE_ARTICLE(), Const.C_CODE_ARTICLE);
		mapComposantChamps.put(vue.getTfPRIX_PRIX(), Const.C_PRIX_PRIX);

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
				.getTfCODE_ARTICLE());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue
				.getTfCODE_ARTICLE());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION, vue
				.getGrille());

		activeModifytListener();
		vue.getTfPRIX_PRIX().addVerifyListener(queDesChiffres);
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


		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID, C_COMMAND_GLOBAL_REFRESH_ID };
		mapActions.put(null, tabActionsAutres);
	}

	public SWTPaRefPrixController getThis() {
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
					.getString("RefPrix.Message.Enregistrer"))) {

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
				//#JPA
				fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
						dao.getChampIdEntite(), dao.getValeurIdTable(),
						selectedRefPrix.getValue())));

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
					ctrlUnChampsSWT(getFocusAvantAideSWT());
				} catch (Exception e) {
					logger.error("",e);
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
			VerrouInterface.setVerrouille(true);
			setSwtOldRefPrix();
			swtRefPrix = new SWTRefPrix();
			if (idArticle!=null && !idArticle.equals("")){
				swtRefPrix.setIdArticle(LibConversion.stringToInteger(idArticle));
				swtRefPrix.setIdPrix(LibConversion.stringToInteger(idPrix));
			}
			dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_INSERTION);
			modelRefPrix.getListeObjet().add(swtRefPrix);
			writableList = new WritableList(realm, modelRefPrix.getListeObjet(), classModel);
			tableViewer.setInput(writableList);
			tableViewer.refresh();
			tableViewer.setSelection(new StructuredSelection(swtRefPrix));
			initEtatBouton();
		} finally {
			initEtatComposant();
			VerrouInterface.setVerrouille(false);
		}
	}

	@Override
	protected void actModifier() throws Exception {
		try {
			setSwtOldRefPrix();
			dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_EDITION);

			initEtatBouton();
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
					.getString("RefPrix.Message.Supprimer"))) {
				dao.begin(transaction);
				TaRefPrix u = dao.findById(((SWTRefPrix) selectedRefPrix.getValue()).getIdRefPrix());
				dao.supprimer(u);

			dao.commit(transaction);
			dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
			actRefresh(); //ajouter pour tester jpa
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
		vue.getShell().close();
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
			switch (dao.getModeObjet().getMode()) {
			case C_MO_INSERTION:
				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("RefPrix.Message.Annuler")))) {
					remetTousLesChampsApresAnnulationSWT(dbc);
					if(((SWTRefPrix) selectedRefPrix.getValue()).getIdRefPrix()==null){
					modelRefPrix.getListeObjet().remove(
							((SWTRefPrix) selectedRefPrix.getValue()));
					writableList = new WritableList(realm, modelRefPrix
							.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.selectionGrille(0);
					}
					hideDecoratedFields();
				}
				initEtatBouton();
				break;
			case C_MO_EDITION:
				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("RefPrix.Message.Annuler")))) {
					int rang = modelRefPrix.getListeObjet().indexOf(selectedRefPrix.getValue());
					remetTousLesChampsApresAnnulationSWT(dbc);
					modelRefPrix.getListeObjet().set(rang, swtOldRefPrix);
					writableList = new WritableList(realm, modelRefPrix.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.setSelection(new StructuredSelection(swtOldRefPrix), true);
					dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
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
	
	protected void initEtatBouton() {
		super.initEtatBouton();
		enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,false);
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
//		if(aideDisponible()){
//			try {
//				VerrouInterface.setVerrouille(true);
//				vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
//				ParamAfficheAideRechercheSWT paramAfficheAideRecherche = new ParamAfficheAideRechercheSWT();
//				paramAfficheAideRecherche.setDb(getThis().getIbTaTable()
//						.getFIBBase());
//				paramAfficheAideRecherche.setMessage(message);
//				// Creation de l'ecran d'aide principal
//				Shell s = new Shell(PlatformUI.getWorkbench()
//						.getActiveWorkbenchWindow().getShell(),
//						LgrShellUtil.styleLgr);
//				PaAideSWT paAide = new PaAideSWT(s, SWT.NONE);
//				SWTPaAideControllerSWT paAideController = new SWTPaAideControllerSWT(
//						paAide);
//				/***************************************************************/
//				LgrPartListener.getLgrPartListener().setLgrActivePart(null);
//				IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new EditorInputAide(), EditorAide.ID);
//				LgrPartListener.getLgrPartListener().setLgrActivePart(e);
//				LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
//				paAideController = new SWTPaAideControllerSWT(((EditorAide)e).getComposite());
//				((LgrEditorPart)e).setController(paAideController);
//				((LgrEditorPart)e).setPanel(((EditorAide)e).getComposite());
//				/***************************************************************/
//				JPABaseControllerSWTStandard controllerEcranCreation = null;
//				ParamAffiche parametreEcranCreation = null;
//				IEditorPart editorCreation = null;
//				String editorCreationId = null;
//				IEditorInput editorInputCreation = null;
//				Shell s2 = new Shell(s, LgrShellUtil.styleLgr);
//
//				switch ((getThis().dao.getModeObjet().getMode())) {
//				case C_MO_CONSULTATION:
//					break;
//				case C_MO_EDITION:
//				case C_MO_INSERTION:
//					break;
//				default:
//					break;
//				}
//
//				if (paramAfficheAideRecherche.getQuery() != null) {
//
//					PaAideRechercheSWT paAideRecherche1 = new PaAideRechercheSWT(s,
//							SWT.NULL);
//					SWTPaAideRechercheControllerSWT paAideRechercheController1 = new SWTPaAideRechercheControllerSWT(
//							paAideRecherche1);
//
//					// Parametrage de la recherche
//					paramAfficheAideRecherche.setFocusSWT(((PaAideRechercheSWT) paAideRechercheController1.getVue()).getTfChoix());
//					paramAfficheAideRecherche.setRefCreationSWT(controllerEcranCreation);
//					paramAfficheAideRecherche.setEditorCreation(editorCreation);
//					paramAfficheAideRecherche.setEditorCreationId(editorCreationId);
//					paramAfficheAideRecherche.setEditorInputCreation(editorInputCreation);
//					paramAfficheAideRecherche.setParamEcranCreation(parametreEcranCreation);
//					paramAfficheAideRecherche.setShellCreation(s2);
//					paAideRechercheController1.configPanel(paramAfficheAideRecherche);
//					// paramAfficheAideRecherche.setFocusDefaut(paAideRechercheController1.getVue().getTfChoix());
//
//					// Ajout d'une recherche
//					paAideController.addRecherche(paAideRechercheController1,
//							paramAfficheAideRecherche.getTitreRecherche());
//
//					// Parametrage de l'ecran d'aide principal
//					ParamAfficheAideSWT paramAfficheAideSWT = new ParamAfficheAideSWT();
//					ParamAfficheAide paramAfficheAide = new ParamAfficheAide();
//
//					// enregistrement pour le retour de l'ecran d'aide
//					paAideController.addRetourEcranListener(getThis());
//					// affichage de l'ecran d'aide principal (+ ses recherches)
//
//					dbc.getValidationStatusMap().removeMapChangeListener(changeListener);
//					//LgrShellUtil.afficheAideSWT(paramAfficheAide, null, paAide,paAideController, s);
//					/*****************************************************************/
//					paAideController.configPanel(paramAfficheAide);
//					/*****************************************************************/
//					dbc.getValidationStatusMap().addMapChangeListener(changeListener);
//
//				}
//
//			} finally {
//				VerrouInterface.setVerrouille(false);
//				vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
//			}
//		}
	}
	
	public IStatus validateUI() {
		return null;
	}
	
	public IStatus validateUIField(String nomChamp,Object value) {
		//nomChamp = "codeUnite";
		IStatus s = ValidationStatus.ok();
//		MultiStatus m = new MultiStatus("1",0,new IStatus[]{s},"Interface",null);
//		
//		TaUnite u = null;
//		u = dao.findById(((SWTUnite) selectedUnite.getValue()).getIdUnite());
//		try {
//			PropertyUtils.setSimpleProperty(u, nomChamp, value);
//		} catch (IllegalAccessException e) {
//			logger.error("",e);
//		} catch (InvocationTargetException e) {
//			logger.error("",e);
//		} catch (NoSuchMethodException e) {
//			logger.error("",e);
//		}
//		//LgrDozerMapper<SWTUnite,TaUnite> mapper = new LgrDozerMapper<SWTUnite,TaUnite>();
//		//mapper.map((SWTUnite) selectedUnite.getValue(),u);
//		//dao.validate(u,nomChamp);
//		return dao.validate(u,nomChamp);
		
		return s;
//		return null;
	}

	@Override
	protected void actEnregistrer() throws Exception {
		EntityTransaction transaction = dao.getEntityManager().getTransaction();		
		try {
			ctrlUnChampsSWT(getFocusCourantSWT());
			dao.begin(transaction);
			TaRefPrix u = null;
			if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
				u = dao.findById(((SWTRefPrix) selectedRefPrix.getValue()).getIdRefPrix());

				LgrDozerMapper<SWTRefPrix,TaRefPrix> mapper = new LgrDozerMapper<SWTRefPrix,TaRefPrix>();
				mapper.map((SWTRefPrix) selectedRefPrix.getValue(),u);

				
			} else if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)) {
				LgrDozerMapper<SWTRefPrix,TaRefPrix> mapper = new LgrDozerMapper<SWTRefPrix,TaRefPrix>();
				u = mapper.map((SWTRefPrix) selectedRefPrix.getValue(),TaRefPrix.class);

				dao.enregistrerMerge(u);
			}
			dao.commit(transaction);

			transaction = null;			
			actRefresh(); //deja present
			
			if(u != null) {
				Iterator<SWTRefPrix> ite = modelRefPrix.getListeObjet().iterator();
				SWTRefPrix tmp = null;
				int i = 0;
				boolean trouve = false;
				while (ite.hasNext() && !trouve) {
					tmp = ite.next();
					if(tmp.getIdRefPrix()==u.getIdRefPrix()) {
						tableViewer.selectionGrille(i);
						trouve = true;
						//((SWTUnite) selectedUnite.getValue()).setSWTUnite(tmp);
					}
					i++;
				}
			}
		} finally {
			if(transaction!=null && transaction.isActive()) {
				transaction.rollback();
			}
			initEtatBouton();
		}
	}



	public void initEtatComposant() {
		try {
			vue.getTfCODE_ARTICLE().setEditable(!isUtilise());
			changeCouleur(vue);
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
		}
	}
	public boolean isUtilise(){
		return ((SWTRefPrix)selectedRefPrix.getValue()).getIdRefPrix()!=null && 
		!dao.recordModifiable(dao.getNomTable(),
				((SWTRefPrix)selectedRefPrix.getValue()).getIdRefPrix());		
	}
	public SWTRefPrix getSwtOldRefPrix() {
		return swtOldRefPrix;
	}

	public void setSwtOldRefPrix(SWTRefPrix swtOldRefPrix) {
		this.swtOldRefPrix = swtOldRefPrix;
	}

	public void setSwtOldRefPrix() {
		if (selectedRefPrix.getValue() != null)
			this.swtOldRefPrix = SWTRefPrix.copy((SWTRefPrix) selectedRefPrix.getValue());
		else {
			if(tableViewer.selectionGrille(0)){
				this.swtOldRefPrix = SWTRefPrix.copy((SWTRefPrix) selectedRefPrix.getValue());
				tableViewer.setSelection(new StructuredSelection(
						(SWTRefPrix) selectedRefPrix.getValue()), true);
			}}
	}

	public void setVue(PaRefPrixSWT vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();

		mapComposantDecoratedField.put(vue.getTfCODE_ARTICLE(), vue.getFieldCODE_ARTICLE());
		mapComposantDecoratedField.put(vue.getTfPRIX_PRIX(), vue.getFieldPRIX_PRIX());
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
		// TODO Raccord de methode auto-genere
		
	}

}

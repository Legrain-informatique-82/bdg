package fr.legrain.articles.ecran;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.value.IObservableValue;
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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaFamille;
import fr.legrain.articles.dao.TaTTva;
import fr.legrain.articles.dao.TaTTvaDAO;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.articles.editor.EditorInputTypeTVA;
import fr.legrain.articles.editor.EditorTypeTVA;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Articles.SWTArticle;
import fr.legrain.gestCom.Module_Articles.SWTFamille;
import fr.legrain.gestCom.Module_Articles.SWTTTva;
import fr.legrain.gestCom.Module_Articles.SWTTva;
import fr.legrain.gestCom.Module_Articles.SWTUnite;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideRechercheSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideRechercheControllerSWT;
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
import fr.legrain.tiers.dao.TaAdresse;
import fr.legrain.tiers.dao.TaTAdr;


public class SWTPaTTvaController extends JPABaseControllerSWTStandard
implements RetourEcranListener {

	static Logger logger = Logger.getLogger(SWTPaTTvaController.class.getName());
	private PaTTVASWT vue = null;
	private TaTTvaDAO dao = null;//new TaTTvaDAO(getEm());

	private Object ecranAppelant = null;
	private SWTTTva swtTTva;
	private SWTTTva swtOldTTva;
	private Realm realm;
	private DataBindingContext dbc;

	private Class classModel = SWTTTva.class;
	private ModelGeneralObjet<SWTTTva,TaTTvaDAO,TaTTva> modelTTva = null;//new ModelGeneralObjet<SWTTTva,TaTTvaDAO,TaTTva>(dao,classModel);
	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private IObservableValue selectedTTva;
	private String[] listeChamp;
	private String nomClassController = this.getClass().getSimpleName();

	private TaTTva taTTva = null;
	
	private MapChangeListener changeListener = new MapChangeListener();
	
	public SWTPaTTvaController(PaTTVASWT vue) {
		this(vue,null);
	}

	public SWTPaTTvaController(PaTTVASWT vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaTTvaDAO(getEm());
		modelTTva = new ModelGeneralObjet<SWTTTva,TaTTvaDAO,TaTTva>(dao,classModel);
		setVue(vue);

		// a faire avant l'initialisation du Binding
		vue.getGrille().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setSwtOldTTva();
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

	public void bind(PaTTVASWT paTTVASWT) {
		try {
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			tableViewer = new LgrTableViewer(paTTVASWT.getGrille());
			tableViewer.createTableCol(classModel,paTTVASWT.getGrille(), nomClassController,
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
//			writableList = new WritableList(realm, modelTTva.remplirListe(), classModel);
//			tableViewer.setInput(writableList);
			
			// Set up data binding.
			LgrViewerSupport.bind(tableViewer, 
					new WritableList(modelTTva.remplirListe(), classModel),
					BeanProperties.values(listeChamp)
					);

			selectedTTva = ViewersObservables.observeSingleSelection(tableViewer);

			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			tableViewer.selectionGrille(0);

			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);

			bindingFormMaitreDetail(dbc, realm, selectedTTva,classModel);

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
			if (((ParamAfficheTTva) param).getFocusDefautSWT() != null && !((ParamAfficheTTva) param).getFocusDefautSWT().isDisposed())
				((ParamAfficheTTva) param).getFocusDefautSWT().setFocus();
			else
				((ParamAfficheTTva) param).setFocusDefautSWT(vue.getGrille());
			vue.getLaTitreFormulaire().setText(((ParamAfficheTTva) param).getTitreFormulaire());
			vue.getLaTitreGrille().setText(((ParamAfficheTTva) param).getTitreGrille());
			vue.getLaTitreFenetre().setText(((ParamAfficheTTva) param).getSousTitre());

			if (param.getEcranAppelant() != null) {
				ecranAppelant = param.getEcranAppelant();
			}
			Map<String,String[]> map = dao.getParamWhereSQL();
			if(param.getIdFiche()!=null &&  !param.getIdFiche().equals("")) {
				if(map==null) map = new HashMap<String,String[]>();
				map.clear();
				map.put("idTTva",new String[]{"=",param.getIdFiche()});
				dao.setParamWhereSQL(map);
				vue.getBtnTous().setVisible(true);
				vue.getGrille().setVisible(false);
				vue.getLaTitreGrille().setVisible(false);
				vue.getCompositeForm().setWeights(new int[]{0,100});					
			}
			bind(vue);
			// permet de se positionner sur le 1er enregistrement et de remplir
			// le formulaire
			tableViewer.selectionGrille(0);
			tableViewer.tri(classModel, nomClassController,Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			VerrouInterface.setVerrouille(false);
			setSwtOldTTva();

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
	
	protected void initVerifySaisie() {
		if (mapInfosVerifSaisie == null)
			mapInfosVerifSaisie = new HashMap<Control, InfosVerifSaisie>();
		
		mapInfosVerifSaisie.put(vue.getTfCODE_T_TVA(), new InfosVerifSaisie(new TaTTva(),Const.C_CODE_T_TVA,null));
		mapInfosVerifSaisie.put(vue.getTfLIB_T_TVA(), new InfosVerifSaisie(new TaTTva(),Const.C_LIB_T_TVA,null));

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

		vue.getTfCODE_T_TVA().setToolTipText(Const.C_CODE_T_TVA);
		vue.getTfLIB_T_TVA().setToolTipText(Const.C_LIB_T_TVA);

		mapComposantChamps.put(vue.getTfCODE_T_TVA(), Const.C_CODE_T_TVA);
		mapComposantChamps.put(vue.getTfLIB_T_TVA(), Const.C_LIB_T_TVA);

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
		listeComposantFocusable.add(vue.getBtnTous());

		if (mapInitFocus == null)
			mapInitFocus = new LinkedHashMap<ModeObjet.EnumModeObjet, Control>();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION, vue
				.getTfCODE_T_TVA());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue
				.getTfCODE_T_TVA());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION, vue
				.getGrille());

		activeModifytListener();
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
		mapCommand.put(C_COMMAND_DOCUMENT_AFFICHER_TOUS_ID, handlerAfficherTous);
		
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
		mapActions.put(vue.getBtnTous(), C_COMMAND_DOCUMENT_AFFICHER_TOUS_ID);
		

		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID, C_COMMAND_GLOBAL_REFRESH_ID };
		mapActions.put(null, tabActionsAutres);

	}

	public SWTPaTTvaController getThis() {
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
					.getString("TTva.Message.Enregistrer"))) {

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
			if(dao.dataSetEnModif())
				try {
					dao.annuler(taTTva);
				} catch (Exception e) {
					throw new ExceptLgr();
				}
			if (ecranAppelant instanceof SWTPaAideControllerSWT) {
				setListeEntity(getModelTTva().remplirListe());
				dao.initValeurIdTable(taTTva);
				fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
						dao.getChampIdEntite(), dao.getValeurIdTable(),
						selectedTTva.getValue())));

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
		} else if (evt.getRetour() != null) {
			if (getFocusAvantAideSWT() instanceof Table) {
				if (getFocusAvantAideSWT() == vue.getGrille()) {
					if(((ResultAffiche) evt.getRetour()).getSelection()!=null)
						tableViewer.setSelection(((ResultAffiche) evt.getRetour()).getSelection(),true);
				}
			}
		}
		super.retourEcran(evt);
	}

	@Override
	protected void actInserer() throws Exception {
		try {
			VerrouInterface.setVerrouille(true);
			setSwtOldTTva();
			swtTTva = new SWTTTva();
			taTTva = new TaTTva();
			dao.inserer(taTTva);
			modelTTva.getListeObjet().add(swtTTva);
			writableList = new WritableList(realm, modelTTva.getListeObjet(), classModel);
			tableViewer.setInput(writableList);
			tableViewer.refresh();
			tableViewer.setSelection(new StructuredSelection(swtTTva));
			initEtatBouton();
		} finally {
			initEtatComposant();
			VerrouInterface.setVerrouille(false);
		}
	}

	@Override
	protected void actModifier() throws Exception {
		try {
			if(!LgrMess.isDOSSIER_EN_RESEAU()){
				setSwtOldTTva();
				taTTva = dao.findById(((SWTTTva) selectedTTva.getValue()).getIdTTva());
			}else{
				if(!setSwtOldTTvaRefresh())throw new Exception();
			}
			
			dao.modifier(taTTva);
			
			initEtatBouton();
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		}
	}
	
	public boolean containsEntity(TaTTva entite){
		if(modelTTva.getListeEntity()!=null){
			for (Object e : modelTTva.getListeEntity()) {
				if(((TaTTva)e).getIdTTva()==
					entite.getIdTTva())return true;
			}
		}
		return false;
	}
	


	public boolean setSwtOldTTvaRefresh() {
		try {	
			if (selectedTTva.getValue()!=null){
				TaTTva taArticleOld =dao.findById(taTTva.getIdTTva());
				taArticleOld=dao.refresh(taArticleOld);
				if(containsEntity(taTTva)) 
					modelTTva.getListeEntity().remove(taTTva);
				if(!taTTva.getVersionObj().equals(taArticleOld.getVersionObj())){
					taTTva=taArticleOld;
					if(!containsEntity(taTTva)) 
						modelTTva.getListeEntity().add(taTTva);					
					actRefresh();
					dao.messageNonAutoriseModification();
				}
				taTTva=taArticleOld;
				if(!containsEntity(taTTva)) 
					modelTTva.getListeEntity().add(taTTva);
				changementDeSelection();
				this.swtOldTTva=SWTTTva.copy((SWTTTva)selectedTTva.getValue());
			}
			return true;
		} catch (Exception e) {
			return false;
		}		
	}

//	public void setSwtOldTTvaRefresh() {
//		if (selectedTTva.getValue()!=null){
//			if(LgrMess.isDOSSIER_EN_RESEAU())dao.refresh(dao.findById(((SWTTTva) selectedTTva.getValue()).getIdTTva()));
//			taTTva=dao.findById(((SWTTTva) selectedTTva.getValue()).getIdTTva());
//			try {
//				if(LgrMess.isDOSSIER_EN_RESEAU())actRefresh();
//			} catch (Exception e) {
//				logger.error("",e);
//			}			
//			this.swtOldTTva=SWTTTva.copy((SWTTTva)selectedTTva.getValue());
//		}
//	}
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
					.getString("TTva.Message.Supprimer"))) {
			dao.begin(transaction);
				TaTTva u = dao.findById(((TaTTva) selectedTTva.getValue()).getIdTTva());
				dao.supprimer(u);
			dao.commit(transaction);
			Object suivant=tableViewer.getElementAt(tableViewer.getTable().getSelectionIndex()+1);
			modelTTva.removeEntity(u);
			taTTva=null;
			dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
			if(suivant!=null)tableViewer.setSelection(new StructuredSelection(suivant),true);
			else tableViewer.selectionGrille(0);
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
			switch (dao.getModeObjet().getMode()) {
			case C_MO_INSERTION:
				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("TTva.Message.Annuler")))) {
					remetTousLesChampsApresAnnulationSWT(dbc);
					if(((SWTTTva) selectedTTva.getValue()).getIdTTva()==null){
					modelTTva.getListeObjet().remove(
							((SWTTTva) selectedTTva.getValue()));
					writableList = new WritableList(realm, modelTTva
							.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.selectionGrille(0);
					}
					dao.annuler(taTTva);
					hideDecoratedFields();
				}
				initEtatBouton();
				break;
			case C_MO_EDITION:
				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("TTva.Message.Annuler")))) {
					int rang = modelTTva.getListeObjet().indexOf(selectedTTva.getValue());
					remetTousLesChampsApresAnnulationSWT(dbc);
					modelTTva.getListeObjet().set(rang, swtOldTTva);
					writableList = new WritableList(realm, modelTTva.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.setSelection(new StructuredSelection(swtOldTTva), true);
					dao.annuler(taTTva);
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
		if(aideDisponible()){
			try {
				VerrouInterface.setVerrouille(true);
				vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
				ParamAfficheAideRechercheSWT paramAfficheAideRecherche = new ParamAfficheAideRechercheSWT();
//				paramAfficheAideRecherche.setDb(getThis().getIbTaTable().getFIBBase());
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
				JPABaseControllerSWTStandard controllerEcranCreation = null;
				ParamAffiche parametreEcranCreation = null;
				IEditorPart editorCreation = null;
				String editorCreationId = null;
				IEditorInput editorInputCreation = null;
				Shell s2 = new Shell(s, LgrShellUtil.styleLgr);

				switch ((getThis().dao.getModeObjet().getMode())) {
				case C_MO_CONSULTATION:
					if(getFocusCourantSWT().equals(vue.getGrille())){
						PaTTVASWT paTTvaSWT = new PaTTVASWT(s2,SWT.NULL);
						SWTPaTTvaController swtPaTTvaController = new SWTPaTTvaController(paTTvaSWT);

						editorCreationId = EditorTypeTVA.ID;
						editorInputCreation = new EditorInputTypeTVA();
						
						paramAfficheAideRecherche.setForceAffichageAideRemplie(true);


						ParamAfficheTTva paramAfficheTTva = new ParamAfficheTTva();
						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
						paramAfficheTTva.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheTTva.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaTTvaController;
						parametreEcranCreation = paramAfficheTTva;

						paramAfficheAideRecherche.setTypeEntite(TaTTva.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_T_TVA);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_T_TVA().getText());
						paramAfficheAideRecherche.setControllerAppelant(getThis());
						paramAfficheAideRecherche.setModel(swtPaTTvaController.getModelTTva());
						paramAfficheAideRecherche.setTypeObjet(swtPaTTvaController.getClassModel());
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_T_TVA);
					}
					break;
				case C_MO_EDITION:
				case C_MO_INSERTION:
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
					paAideController.addRetourEcranListener(getThis());
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
	
	public IStatus validateUI() {
		return null;
	}
	
	public IStatus validateUIField(String nomChamp,Object value) {
		String validationContext = "TTVA";
		try {
			IStatus s = null;
			boolean verrouilleModifCode = false;
			int change=0;

				TaTTva u = new TaTTva();
				PropertyUtils.setSimpleProperty(u, nomChamp, value);
				if(((SWTTTva) selectedTTva.getValue()).getIdTTva()!=null) {
					u.setIdTTva(((SWTTTva) selectedTTva.getValue()).getIdTTva());
				}
				
				if(nomChamp.equals(Const.C_CODE_T_TVA)){
					verrouilleModifCode = true;
				}

				s = dao.validate(u,nomChamp,validationContext,verrouilleModifCode);
				if(s.getSeverity()!=IStatus.ERROR && change!=0) {
					  
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
			
			if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
				LgrDozerMapper<SWTTTva,TaTTva> mapper = new LgrDozerMapper<SWTTTva,TaTTva>();
				mapper.map((SWTTTva) selectedTTva.getValue(),taTTva);

				dao.enregistrerMerge(taTTva);

				
			} else if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)) {
				LgrDozerMapper<SWTTTva,TaTTva> mapper = new LgrDozerMapper<SWTTTva,TaTTva>();
				mapper.map((SWTTTva) selectedTTva.getValue(),taTTva);

				taTTva=dao.enregistrerMerge(taTTva);
//				modelTTva.getListeEntity().add(taTTva);
			}
			
			dao.commit(transaction);
			modelTTva.addEntity(taTTva);
			transaction = null;
			
			actRefresh(); //deja present
			
		} finally {
			if(transaction!=null && transaction.isActive()) {
				transaction.rollback();
			}
			initEtatBouton();
		}
	}

	public void initEtatComposant() {
		try {
			vue.getTfCODE_T_TVA().setEditable(!isUtilise());
			changeCouleur(vue);
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
		}

	}
	public boolean isUtilise(){
		return (((SWTTTva)selectedTTva.getValue()).getIdTTva()!=null &&
		!dao.recordModifiable(dao.getNomTable(),
				((SWTTTva)selectedTTva.getValue()).getIdTTva()))||!dao.autoriseModification(taTTva);		
	}
	public SWTTTva getSwtOldTTva() {
		return swtOldTTva;
	}

	public void setSwtOldTTva(SWTTTva swtOldTTva) {
		this.swtOldTTva = swtOldTTva;
	}

	public void setSwtOldTTva() {
		if (selectedTTva.getValue() != null)
			this.swtOldTTva = SWTTTva.copy((SWTTTva) selectedTTva.getValue());
		else {
			if(tableViewer.selectionGrille(0)){
				this.swtOldTTva = SWTTTva.copy((SWTTTva) selectedTTva.getValue());
				tableViewer.setSelection(new StructuredSelection(
						(SWTTTva) selectedTTva.getValue()), true);
			}}
	}

	public void setVue(PaTTVASWT vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();

		mapComposantDecoratedField.put(vue.getTfCODE_T_TVA(), vue.getFieldCODE_T_TVA());
		mapComposantDecoratedField.put(vue.getTfLIB_T_TVA(), vue.getFieldLIB_T_TVA());
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

		//repositionnement sur la valeur courante
		int idActuel = 0;
		if (taTTva!=null) { //enregistrement en cours de modification/insertion
			idActuel = taTTva.getIdTTva();
		} else if(selectedTTva!=null && (SWTTTva) selectedTTva.getValue()!=null) {
			idActuel = ((SWTTTva) selectedTTva.getValue()).getIdTTva();
		}

		if (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION) == 0) {
			//rafraichissement des valeurs dans la grille
			writableList = new WritableList(realm, modelTTva.remplirListe(), classModel);
			tableViewer.setInput(writableList);
		} else {
			if (taTTva!=null && selectedTTva!=null && (SWTTTva) selectedTTva.getValue()!=null) {
				new LgrDozerMapper<TaTTva, SWTTTva>().
					map(taTTva, (SWTTTva) selectedTTva.getValue());
			}
		}

		if(idActuel!=0) {
			tableViewer.setSelection(new StructuredSelection(modelTTva.recherche(Const.C_ID_T_TVA, idActuel)));
		}else
			tableViewer.selectionGrille(0);		
	}

	public ModelGeneralObjet<SWTTTva,TaTTvaDAO,TaTTva> getModelTTva() {
		return modelTTva;
	}

	public TaTTvaDAO getDao() {
		return dao;
	}

	public TaTTva getTaTTva() {
		return taTTva;
	}
	private void changementDeSelection() {
		if(selectedTTva!=null && selectedTTva.getValue()!=null) {
			if(((SWTTTva) selectedTTva.getValue()).getIdTTva()!=null) {
				taTTva = dao.findById(((SWTTTva) selectedTTva.getValue()).getIdTTva());
			}
			//null a tester ailleurs, car peut etre null en cas d'insertion
			fireChangementDeSelection(new ChangementDeSelectionEvent(SWTPaTTvaController.this));
		}
	}

	public void actAfficherTous() throws Exception{
		vue.getGrille().setVisible(true);  
		vue.getBtnTous().setVisible(false);
		vue.getLaTitreGrille().setVisible(true);
		vue.getCompositeForm().setWeights(new int[]{50,100});
		dao.setJPQLQuery(dao.getJPQLQueryInitial());
		modelTTva.setJPQLQuery(null);
		modelTTva.setListeEntity(null);
		try {
			actRefresh();
		} catch (Exception e1) {
			logger.error("", e1);
		}
	}
}

package fr.legrain.tiers.ecran;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.OptimisticLockException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
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
import org.eclipse.ui.PlatformUI;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.edition.actions.AttributElementResport;
import fr.legrain.edition.actions.BaseImpressionEdition;
import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.edition.actions.MakeDynamiqueReport;
import fr.legrain.edition.dynamique.FonctionGetInfosXmlAndProperties;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Articles.SWTArticle;
import fr.legrain.gestCom.Module_Tiers.SWTTBanque;
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
import fr.legrain.tiers.dao.TaTBanque;
import fr.legrain.tiers.dao.TaTBanqueDAO;
import fr.legrain.tiers.divers.Impression;
import fr.legrain.tiers.editor.EditorInputTypeAdresse;
import fr.legrain.tiers.editor.EditorInputTypeBanque;
import fr.legrain.tiers.editor.EditorTypeAdresse;
import fr.legrain.tiers.editor.EditorTypeBanque;

public class SWTPaTBanqueController extends JPABaseControllerSWTStandard
		implements RetourEcranListener {

	static Logger logger = Logger.getLogger(SWTPaTBanqueController.class.getName());
	private PaTBanqueSWT vue = null;
	private TaTBanqueDAO dao = null;//new TaTBanqueDAO();

	private Object ecranAppelant = null;
	private SWTTBanque swtTBanque;
	private SWTTBanque swtOldTBanque;
	private Realm realm;
	private DataBindingContext dbc;

	private Class classModel = SWTTBanque.class;
	//private ModelGeneral<SWTTAdr>  modelTAdr = new ModelGeneral<SWTTAdr>(ibTaTable.getFIBQuery(),classModel);
	private ModelGeneralObjet<SWTTBanque,TaTBanqueDAO,TaTBanque> modelTBanque = null;//new ModelGeneralObjet<SWTTBanque,TaTBanqueDAO,TaTBanque>(dao,classModel);
	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private IObservableValue selectedTBanque;
	private String[] listeChamp;
	private String nomClassController = this.getClass().getSimpleName();

	private LgrDozerMapper<SWTTBanque,TaTBanque> mapperUIToModel  = new LgrDozerMapper<SWTTBanque,TaTBanque>();
	private TaTBanque taTBanque = null;
	private MapChangeListener changeListener = new MapChangeListener();

	public SWTPaTBanqueController(PaTBanqueSWT vue) {
		this(vue,null);
	}
	
	public SWTPaTBanqueController(PaTBanqueSWT vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaTBanqueDAO(getEm());
		modelTBanque = new ModelGeneralObjet<SWTTBanque,TaTBanqueDAO,TaTBanque>(dao,classModel);
		setVue(vue);

		// a faire avant l'initialisation du Bindingaa
		vue.getGrille().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setSwtOldTBanque();
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
			Menu[] tabPopups = new Menu[] { popupMenuFormulaire,popupMenuGrille };
			this.initPopupAndButtons(mapActions, tabPopups);
			vue.getPaCorpsFormulaire().setMenu(popupMenuFormulaire);
			vue.getPaGrille().setMenu(popupMenuGrille);

			initEtatBouton();
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("", e);
		}
	}

	public void bind(PaTBanqueSWT paTBanqueSWT) {
		try {
//			 modelTAdr = new ModelGeneral<SWTTAdr>(ibTaTable.getFIBQuery(),classModel);
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			tableViewer = new LgrTableViewer(paTBanqueSWT.getGrille());
			tableViewer.createTableCol(classModel,paTBanqueSWT.getGrille(), nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE,0);
			listeChamp = tableViewer.setListeChampGrille(nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE);

//			ObservableListContentProvider viewerContent = new ObservableListContentProvider();
//			tableViewer.setContentProvider(viewerContent);
//
//			IObservableMap[] attributeMaps = BeansObservables.observeMaps(
//					viewerContent.getKnownElements(), classModel,listeChamp);
//
//			tableViewer.setLabelProvider(new ObservableMapLabelProvider(attributeMaps));
//			writableList = new WritableList(realm, modelTBanque.remplirListe(), classModel);
//			tableViewer.setInput(writableList);
			
			// Set up data binding.
			LgrViewerSupport.bind(tableViewer, 
					new WritableList(modelTBanque.remplirListe(), classModel),
					BeanProperties.values(listeChamp)
					);

			selectedTBanque = ViewersObservables.observeSingleSelection(tableViewer);

			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			tableViewer.selectionGrille(0);

			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);

			bindingFormMaitreDetail(dbc, realm, selectedTBanque,classModel);
			changementDeSelection();
			selectedTBanque.addChangeListener(new IChangeListener() {

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
		if(selectedTBanque!=null && selectedTBanque.getValue()!=null) {
			if(((SWTTBanque) selectedTBanque.getValue()).getIdTBanque()!=null) {
				taTBanque = dao.findById(((SWTTBanque) selectedTBanque.getValue()).getIdTBanque());
			}
			//null a tester ailleurs, car peut etre null en cas d'insertion
			fireChangementDeSelection(new ChangementDeSelectionEvent(SWTPaTBanqueController.this));
		}
	}

	public Composite getVue() {
		return vue;
	}

	public ResultAffiche configPanel(ParamAffiche param) {
		if (param != null) {
			//ibTaTable.ouvreDataset();
			if (((ParamAfficheTypeBanque) param).getFocusDefautSWT() != null && !((ParamAfficheTypeBanque) param).getFocusDefautSWT().isDisposed())
				((ParamAfficheTypeBanque) param).getFocusDefautSWT().setFocus();
			else
				((ParamAfficheTypeBanque) param).setFocusDefautSWT(vue.getGrille());
			vue.getLaTitreFormulaire().setText(((ParamAfficheTypeBanque) param).getTitreFormulaire());
			vue.getLaTitreGrille().setText(((ParamAfficheTypeBanque) param).getTitreGrille());
			vue.getLaTitreFenetre().setText(((ParamAfficheTypeBanque) param).getSousTitre());

			if (param.getEcranAppelant() != null) {
				ecranAppelant = param.getEcranAppelant();
			}
			Map<String,String[]> map = dao.getParamWhereSQL();
			if(param.getIdFiche()!=null &&  !param.getIdFiche().equals("")) {
				if(map==null) map = new HashMap<String,String[]>();
				map.clear();
				map.put("idTBanque",new String[]{"=",param.getIdFiche()});
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
			setSwtOldTBanque();

			if (param.getModeEcran() != null
					&& param.getModeEcran().compareTo(EnumModeObjet.C_MO_INSERTION) == 0) {
				try {
					actInserer();
				} catch (Exception e) {
					vue.getLaMessage().setText(e.getMessage());
					logger.error("", e);
				}
			}

			//param.setFocus(ibTaTable.getFModeObjet().getFocusCourant());
		}
		return null;
	}
	
	protected void initVerifySaisie() {
		if (mapInfosVerifSaisie == null)
			mapInfosVerifSaisie = new HashMap<Control, InfosVerifSaisie>();
		
		mapInfosVerifSaisie.put(vue.getTfCODE_T_BANQUE(), new InfosVerifSaisie(new TaTBanque(),Const.C_CODE_T_BANQUE,null));
		mapInfosVerifSaisie.put(vue.getTfLIBL_T_BANQUE(), new InfosVerifSaisie(new TaTBanque(),Const.C_LIBL_T_BANQUE,null));

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

		vue.getTfCODE_T_BANQUE().setToolTipText(Const.C_CODE_T_BANQUE);
		vue.getTfLIBL_T_BANQUE().setToolTipText(Const.C_LIBL_T_BANQUE);

		mapComposantChamps.put(vue.getTfCODE_T_BANQUE(), Const.C_CODE_T_BANQUE);
		mapComposantChamps.put(vue.getTfLIBL_T_BANQUE(), Const.C_LIBL_T_BANQUE);

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
				.getTfCODE_T_BANQUE());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue
				.getTfCODE_T_BANQUE());
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
					.getString("TypeBanque.Message.Enregistrer"))) {

				try {
					actEnregistrer();
				} catch (Exception e) {
					vue.getLaMessage().setText(e.getMessage());
					logger.error("", e);
				}
			} else
				//ibTaTable.annuler();

			break;
		case C_MO_CONSULTATION:
			break;
		default:
			break;
		}

//		if (retour) {
//			if (ecranAppelant instanceof SWTPaAideControllerSWT) {
//				fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
//						ibTaTable.champIdTable, ibTaTable.valeurIdTable,
//						selectedTAdr.getValue())));
////				vue.getDisplay().asyncExec(new Runnable() {
////					public void run() {
////						vue.getShell().setVisible(false);
////					}
////				});
////				retour = false;
//				retour = true;
//			}
//		}
//		if (retour && !(ecranAppelant instanceof SWTPaAideControllerSWT)) {
//			fireDestroy(new DestroyEvent(ibTaTable));
//		}
		if (retour) {
			if (ecranAppelant instanceof SWTPaAideControllerSWT) {
				setListeEntity(getModelTBanque().remplirListe());
				dao.initValeurIdTable(taTBanque);
				fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
						dao.getChampIdEntite(), dao.getValeurIdTable(),
						selectedTBanque.getValue())));

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
					((Text) getFocusAvantAideSWT()).setText(((ResultAffiche) evt.getRetour()).getResult());					
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

	@Override
	protected void actInserer() throws Exception {
		try {
			if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
				VerrouInterface.setVerrouille(true);
				setSwtOldTBanque();
				swtTBanque = new SWTTBanque();
				taTBanque = new TaTBanque();
				dao.inserer(taTBanque);
				//ibTaTable.insertion();
				modelTBanque.getListeObjet().add(swtTBanque);
				writableList = new WritableList(realm, modelTBanque.getListeObjet(), classModel);
				tableViewer.setInput(writableList);
				tableViewer.refresh();
				tableViewer.setSelection(new StructuredSelection(swtTBanque));
				initEtatBouton();
			}
			
			
		//} catch (ExceptLgr e1) {
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
			if(!LgrMess.isDOSSIER_EN_RESEAU()){
				setSwtOldTBanque();
				taTBanque = dao.findById(((SWTTBanque) selectedTBanque.getValue()).getIdTBanque());

			}else{
				if(!setSwtOldTBanqueRefresh())throw new Exception();
			}			
			dao.modifier(taTBanque);
			initEtatBouton();
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		}
	}
	
	public boolean containsEntity(TaTBanque entite){
		if(modelTBanque.getListeEntity()!=null){
			for (Object e : modelTBanque.getListeEntity()) {
				if(((TaTBanque)e).getIdTBanque()==
					entite.getIdTBanque())return true;
			}
		}
		return false;
	}

	public boolean setSwtOldTBanqueRefresh() {
		try {	
			if (selectedTBanque.getValue()!=null){
				TaTBanque taArticleOld =dao.findById(taTBanque.getIdTBanque());
				taArticleOld=dao.refresh(taArticleOld);
				if(containsEntity(taTBanque)) 
					modelTBanque.getListeEntity().remove(taTBanque);
				if(!taTBanque.getVersionObj().equals(taArticleOld.getVersionObj())){
					taTBanque=taArticleOld;
					if(!containsEntity(taTBanque)) 
						modelTBanque.getListeEntity().add(taTBanque);					
					actRefresh();
					dao.messageNonAutoriseModification();
				}
				taTBanque=taArticleOld;
				if(!containsEntity(taTBanque)) 
					modelTBanque.getListeEntity().add(taTBanque);
				changementDeSelection();
				this.swtOldTBanque=SWTTBanque.copy((SWTTBanque)selectedTBanque.getValue());
			}
			return true;
		} catch (Exception e) {
			return false;
		}		
	}

//	public void setSwtOldTBanqueRefresh() {
//		if (selectedTBanque.getValue()!=null){
//			if(LgrMess.isDOSSIER_EN_RESEAU())dao.refresh(dao.findById(((SWTTBanque) selectedTBanque.getValue()).getIdTBanque()));
//			taTBanque=dao.findById(((SWTTBanque) selectedTBanque.getValue()).getIdTBanque());
//			try {
//				if(LgrMess.isDOSSIER_EN_RESEAU())actRefresh();
//			} catch (Exception e) {
//				logger.error("",e);
//			}			
//			this.swtOldTBanque=SWTTBanque.copy((SWTTBanque)selectedTBanque.getValue());
//		}
//	}
	@Override
	protected void actSupprimer() throws Exception {
		EntityTransaction transaction = dao.getEntityManager().getTransaction();
		try {
			VerrouInterface.setVerrouille(true);
			if(isUtilise())MessageDialog.openInformation(vue.getShell(), fr.legrain.tiers.ecran.MessagesEcran
					.getString("Message.Attention"), fr.legrain.tiers.ecran.MessagesEcran
					.getString("Message.suppression"));
			else
			if (MessageDialog.openConfirm(vue.getShell(), fr.legrain.tiers.ecran.MessagesEcran
					.getString("Message.Attention"), fr.legrain.tiers.ecran.MessagesEcran
					.getString("TypeBanque.Message.Supprimer"))) {
			dao.begin(transaction);
			TaTBanque tar = dao.findById(((SWTTBanque) selectedTBanque.getValue()).getIdTBanque());
			dao.supprimer(tar);
			dao.commit(transaction);
			Object suivant=tableViewer.getElementAt(tableViewer.getTable().getSelectionIndex()+1);
			modelTBanque.removeEntity(taTBanque);
			dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
			if(suivant!=null)tableViewer.setSelection(new StructuredSelection(suivant),true);
			else tableViewer.selectionGrille(0);
			actRefresh(); //ajouter pour tester jpa

			}
		//} catch (ExceptLgr e1) {
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
		// (contrï¿½les de sortie et fermeture de la fenï¿½tre) => onClose()
		if(onClose())
		closeWorkbenchPart();
	}

	@Override
	protected void actAnnuler() throws Exception {
		// // vï¿½rifier si l'objet est en modification ou en consultation
		// // si modification ou insertion, alors demander si annulation des
		// // modifications si ok, alors annulation si pas ok, alors arrï¿½ter le
		// processus d'annulation
		// // si consultation dï¿½clencher l'action "fermer".
		try {
			VerrouInterface.setVerrouille(true);
			switch (dao.getModeObjet().getMode()) {
			case C_MO_INSERTION:
				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("TypeBanque.Message.Annuler")))) {
					remetTousLesChampsApresAnnulationSWT(dbc);
					if(((SWTTBanque) selectedTBanque.getValue()).getIdTBanque()==null){
					modelTBanque.getListeObjet().remove(
							((SWTTBanque) selectedTBanque.getValue()));
					writableList = new WritableList(realm, modelTBanque
							.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					//tableViewer.selectionGrille(0);
					if(swtOldTBanque!=null)
						tableViewer.setSelection(new StructuredSelection(swtOldTBanque), true);
					else
						tableViewer.selectionGrille(0);
					}
					//ibTaTable.annuler();
					dao.annuler(taTBanque);
					hideDecoratedFields();
				}
				initEtatBouton();
				break;
			case C_MO_EDITION:
				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("TypeAdresse.Message.Annuler")))) {
					int rang = modelTBanque.getListeObjet().indexOf(selectedTBanque.getValue());
					remetTousLesChampsApresAnnulationSWT(dbc);
					modelTBanque.getListeObjet().set(rang, swtOldTBanque);
					writableList = new WritableList(realm, modelTBanque.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.setSelection(new StructuredSelection(swtOldTBanque), true);
					dao.annuler(taTBanque);
					hideDecoratedFields();
				}
				initEtatBouton();

				break;
			case C_MO_CONSULTATION:
				actFermer();
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
		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();

		String nomChampIdTable =  dao.getChampIdTable();

		FonctionGetInfosXmlAndProperties fonctionGetInfosXmlAndProperties = new FonctionGetInfosXmlAndProperties(mapperUIToModel);
		fonctionGetInfosXmlAndProperties.cleanValueMapAttributeTable();

		fonctionGetInfosXmlAndProperties.setValueMapAttributeTable(TaTBanque.class.getSimpleName()+".head");
		fonctionGetInfosXmlAndProperties.setValueMapAttributeTable(TaTBanque.class.getSimpleName()+".detail");
		
		LinkedHashMap<String,AttributElementResport> mapAttributeTablHead = fonctionGetInfosXmlAndProperties.getMapAttributeTablHead(); 
		LinkedHashMap<String,AttributElementResport> mapAttributeTablDetail = fonctionGetInfosXmlAndProperties.getMapAttributeTablDetail();
		
		Collection<TaTBanque> collectionTaTBanque = modelTBanque.getListeEntity();
		
		fonctionGetInfosXmlAndProperties.findInfosFileXml(TaTBanque.class.getName(),SWTTBanque.class.getName(),
				listeChamp,"mapping");
		fonctionGetInfosXmlAndProperties.getInfosObjetJPA(taTBanque);

		ConstEdition constEdition = new ConstEdition(); 
//		Impression impression = new Impression(constEdition,taTBanque,collectionTaTBanque,nomChampIdTable,taTBanque.getIdTBanque());
		String nomDossier = null;

		int nombreLine = collectionTaTBanque.size();

		if(nombreLine==0){
			MessageDialog.openWarning(vue.getShell(), ConstEdition.TITRE_MESSAGE_EDITION_VIDE,
					ConstEdition.EDITION_VIDE);
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
			String folderEditionDynamique = Const.C_RCP_INSTANCE_LOCATION+"/"+Const.C_NOM_PROJET_TMP+"/"+TaTBanque.class.getSimpleName();
			constEdition.makeFolderEditions(folderEditionDynamique);
			Path pathFileReport = new Path(folderEditionDynamique+"/"+Const.C_NOM_VU_T_BANQUE+".rptdesign");
			final String pathFileReportDynamic = pathFileReport.toPortableString();

			MakeDynamiqueReport DynamiqueReport = new MakeDynamiqueReport(constEdition.getNameTableEcran(),
					constEdition.getNameTableBDD(),pathFileReportDynamic,Const.C_NOM_VU_T_BANQUE,
					ConstEdition.PAGE_ORIENTATION_LANDSCAPE,nomDossier); 
			
			DynamiqueReport.setSimpleNameEntity(TaTBanque.class.getSimpleName());
			/**************************************************************/
			DynamiqueReport.setFonctionGetInfosXml(fonctionGetInfosXmlAndProperties);
			DynamiqueReport.setNomObjet(TaTBanque.class.getSimpleName());
			/**************************************************************/

			Map<String, AttributElementResport> attribuGridHeader = new LinkedHashMap<String, AttributElementResport>();
			String nameHeaderTitle = ConstEditionTiers.TITLE_EDITION_TYPE_BANQUES;
			attribuGridHeader.put(nameHeaderTitle, new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
					ConstEdition.FONT_SIZE_XX_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
					ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE,""));
			String nameSousHeaderTitle = ConstEditionTiers.SOUS_TITLE_EDITION_TYPE_BANQUES;
			attribuGridHeader.put(nameSousHeaderTitle, new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
					ConstEdition.FONT_SIZE_X_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
					ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE,ConstEdition.COLOR_GRAY));

			//DynamiqueReport.buildDesignConfig();
			ConstEdition.CONTENT_COMMENTS = ConstEditionTiers.COMMENTAIRE_EDITION_DEFAUT;
			DynamiqueReport.initializeBuildDesignReportConfig();
			DynamiqueReport.makePageMater("1", "1", "1", "1", "100");
			DynamiqueReport.makeReportHeaderGrid(3,5,100,ConstEdition.UNITS_PERCENTAGE,attribuGridHeader);
//			DynamiqueReport.makeReportTableDB(100,ConstEdition.UNITS_PERCENTAGE,
//					Const.C_NOM_VU_T_TEL,attribuTabHeader,attribuTabDetail,1,1,2,5,"30");
			DynamiqueReport.biuldTableReport("100", ConstEdition.UNITS_PERCENTAGE, 
					Const.C_NOM_VU_T_BANQUE,1,1,2,"40", mapAttributeTablHead, mapAttributeTablDetail);
			DynamiqueReport.savsAsDesignHandle();
//			impression.imprimer(true,pathFileReportDynamic,null,"Type Banque",TaTBanque.class.getSimpleName(),false);

			/** 01/03/2010 modifier les editions (zhaolin) **/
			BaseImpressionEdition impressionEdition = new BaseImpressionEdition(constEdition,taTBanque,
					getEm(),collectionTaTBanque,taTBanque.getIdTBanque());
			impressionEdition.impressionEditionTypeEntity(pathFileReportDynamic,"Banque");
			
		}
	}
	
	@Override
	protected boolean aideDisponible() {
		boolean result = false;
		//switch ((getThis().ibTaTable.getFModeObjet().getMode())) {
		switch (SWTPaTBanqueController.this.dao.getModeObjet().getMode()) {
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
				//paramAfficheAideRecherche.setDb(SWTPaTAdrController.this.getIbTaTable().getFIBBase());
				paramAfficheAideRecherche.setMessage(message);
				// Crï¿½ation de l'ï¿½cran d'aide principal
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
				switch (SWTPaTBanqueController.this.dao.getModeObjet().getMode()) {
				case C_MO_CONSULTATION:
					if(getFocusCourantSWT().equals(vue.getGrille())){
						PaTBanqueSWT paTBanqueSWT = new PaTBanqueSWT(s2,SWT.NULL);
						SWTPaTBanqueController swtPaTBanqueController = new SWTPaTBanqueController(paTBanqueSWT);

						editorCreationId = EditorTypeBanque.ID;
						editorInputCreation = new EditorInputTypeBanque();

						ParamAfficheTypeBanque paramAfficheTBanque = new ParamAfficheTypeBanque();
						//paramAfficheAideRecherche.setQuery(swtPaTAdrController.getIbTaTable().getFIBQuery());
						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
						paramAfficheTBanque.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheTBanque.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaTBanqueController;
						parametreEcranCreation = paramAfficheTBanque;

						paramAfficheAideRecherche.setTypeEntite(TaTBanque.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_T_BANQUE);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_T_BANQUE().getText());
						paramAfficheAideRecherche.setControllerAppelant(SWTPaTBanqueController.this);
						paramAfficheAideRecherche.setModel(swtPaTBanqueController.getModelTBanque());
						paramAfficheAideRecherche.setTypeObjet(swtPaTBanqueController.getClassModel());
						//paramAfficheAideRecherche.setChampsIdentifiant(swtPaTAdrController.getIbTaTable().champIdTable);
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_T_BANQUE);
					}
					break;
				case C_MO_EDITION:
				case C_MO_INSERTION:
					break;
				default:
					break;
				}

				//if (paramAfficheAideRecherche.getQuery() != null) {
				if (paramAfficheAideRecherche.getJPQLQuery() != null) {

					PaAideRechercheSWT paAideRecherche1 = new PaAideRechercheSWT(s,
							SWT.NULL);
					SWTPaAideRechercheControllerSWT paAideRechercheController1 = new SWTPaAideRechercheControllerSWT(
							paAideRecherche1);

					// Paramï¿½trage de la recherche
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
					paAideController.addRecherche(paAideRechercheController1,
							paramAfficheAideRecherche.getTitreRecherche());

					// Paramï¿½trage de l'ï¿½cran d'aide principal
					ParamAfficheAideSWT paramAfficheAideSWT = new ParamAfficheAideSWT();
					ParamAfficheAide paramAfficheAide = new ParamAfficheAide();

					// enregistrement pour le retour de l'ecran d'aide
					paAideController.addRetourEcranListener(SWTPaTBanqueController.this);
					//Control focus = vue.getShell().getDisplay().getFocusControl();
					// affichage de l'ecran d'aide principal (+ ses recherches)

					dbc.getValidationStatusMap().removeMapChangeListener(changeListener);
					//LgrShellUtil.afficheAideSWT(paramAfficheAide, null, paAide,paAideController, s);
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
		String validationContext = "T_BANQUE";
		try {
			//nomChamp = "codeUnite";
//			IStatus s = ValidationStatus.ok();
//			MultiStatus m = new MultiStatus("1",0,new IStatus[]{s},"Interface",null);
			IStatus s = null;
			boolean verrouilleModifCode = false;
			int change=0;

				//TaArticle u = null;
				//u = dao.findById(((SWTArticle) selectedArticle.getValue()).getIdArticle());
				TaTBanque u = new TaTBanque();
				PropertyUtils.setSimpleProperty(u, nomChamp, value);
				if(((SWTTBanque) selectedTBanque.getValue()).getIdTBanque()!=null) {
					u.setIdTBanque(((SWTTBanque) selectedTBanque.getValue()).getIdTBanque());
				}
				
				//LgrDozerMapper<SWTArticle,TaArticle> mapper = new LgrDozerMapper<SWTArticle,TaArticle>();
				//mapper.map((SWTArticle) selectedArticle.getValue(),taArticle);
				//dao.validate(u,nomChamp);
				if(nomChamp.equals(Const.C_CODE_T_BANQUE)){
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
		
//		return s;
	}
	@Override
	protected void actEnregistrer() throws Exception {
		EntityTransaction transaction = dao.getEntityManager().getTransaction();
		try {
			dao.begin(transaction);
			ctrlUnChampsSWT(getFocusCourantSWT());
			ctrlTousLesChampsAvantEnregistrementSWT();

			if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
				LgrDozerMapper<SWTTBanque,TaTBanque> mapper = new LgrDozerMapper<SWTTBanque,TaTBanque>();
				mapper.map((SWTTBanque) selectedTBanque.getValue(),taTBanque);
				taTBanque=dao.enregistrerMerge(taTBanque);
			}else if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)) {
				LgrDozerMapper<SWTTBanque,TaTBanque> mapper = new LgrDozerMapper<SWTTBanque,TaTBanque>();
				mapper.map((SWTTBanque) selectedTBanque.getValue(),taTBanque);
				taTBanque=dao.enregistrerMerge(taTBanque);
//				modelTBanque.getListeEntity().add(taTBanque);
			}
			
			dao.commit(transaction);
			modelTBanque.addEntity(taTBanque);
			transaction = null;
			
			actRefresh(); //deja present
			} catch (Exception e) {
			// TODO: handle exception
			logger.error("",e);
			if(e.getCause() instanceof OptimisticLockException)
				MessageDialog.openError(vue.getShell(), "", e.getMessage()+"\n"+e.getCause().getMessage());
		} finally {
			if(transaction!=null && transaction.isActive()) {
				transaction.rollback();
			}
			initEtatBouton();
		}
	}



	public void initEtatComposant() {
		try {
			vue.getTfCODE_T_BANQUE().setEditable(!isUtilise());
			changeCouleur(vue);
	    } catch (Exception e) {
		vue.getLaMessage().setText(e.getMessage());
	    }
	}
	public boolean isUtilise(){
		return (((SWTTBanque)selectedTBanque.getValue()).getIdTBanque()!=null
		&&	!dao.recordModifiable(dao.getNomTable(),
				((SWTTBanque)selectedTBanque.getValue()).getIdTBanque()))||
				!dao.autoriseModification(taTBanque);		
	}
	

	public SWTTBanque getSwtOldTAdr() {
		return swtOldTBanque;
	}

	public void setSwtOldTAdr(SWTTBanque swtOldTypeAdr) {
		this.swtOldTBanque = swtOldTypeAdr;
	}

	public void setSwtOldTBanque() {
		if (selectedTBanque!=null && selectedTBanque.getValue() != null)
			this.swtOldTBanque = SWTTBanque.copy((SWTTBanque) selectedTBanque.getValue());
		else {
			if(tableViewer.selectionGrille(0)){
			this.swtOldTBanque = SWTTBanque.copy((SWTTBanque) selectedTBanque.getValue());
			tableViewer.setSelection(new StructuredSelection(
					(SWTTBanque) selectedTBanque.getValue()), true);
		}}
	}

	public void setVue(PaTBanqueSWT vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();
		
		mapComposantDecoratedField.put(vue.getTfCODE_T_BANQUE(), vue.getFieldCODE_T_BANQUE());
		mapComposantDecoratedField.put(vue.getTfLIBL_T_BANQUE(), vue.getFieldLIBL_T_BANQUE());		
	}

	public Class getClassModel() {
		return classModel;
	}

	@Override
	protected void sortieChamps() {
		// TODO Raccord de mï¿½thode auto-gï¿½nï¿½rï¿½
		
	}

	@Override
	protected void actRefresh() throws Exception {
		
		//rafraichissement des valeurs dans la grille
		writableList = new WritableList(realm, modelTBanque.remplirListe(), classModel);
		tableViewer.setInput(writableList);
		tableViewer.refresh();

		//repositionnement sur la valeur courante
		int idActuel = 0;
		if (taTBanque!=null) { //enregistrement en cours de modification/insertion
			idActuel = taTBanque.getIdTBanque();
		} else if(selectedTBanque!=null && (SWTTBanque) selectedTBanque.getValue()!=null) {
			idActuel = ((SWTTBanque) selectedTBanque.getValue()).getIdTBanque();
		}

		if(idActuel!=0) {
			tableViewer.setSelection(new StructuredSelection(modelTBanque.recherche(Const.C_ID_T_BANQUE, idActuel)));
		}else
			tableViewer.selectionGrille(0);		
	}

//	public ModelGeneral<SWTTBanque> getModelTAdr() {
//		return modelTAdr;
//	}
	public ModelGeneralObjet<SWTTBanque,TaTBanqueDAO,TaTBanque> getModelTBanque() {
		return modelTBanque;
	}

	public TaTBanqueDAO getDao() {
		return dao;
	}

	public TaTBanque getTaUnite() {
		return taTBanque;
	}
	public void actAfficherTous() throws Exception{
		vue.getGrille().setVisible(true);  
		vue.getBtnTous().setVisible(false);
		vue.getLaTitreGrille().setVisible(true);
		vue.getCompositeForm().setWeights(new int[]{50,100});
		dao.setJPQLQuery(dao.getJPQLQueryInitial());
		modelTBanque.setJPQLQuery(null);
		modelTBanque.setListeEntity(null);
		try {
			actRefresh();
		} catch (Exception e1) {
			logger.error("", e1);
		}
	}
}

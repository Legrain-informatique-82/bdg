package fr.legrain.tiers.ecran;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
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
import fr.legrain.gestCom.Module_Tiers.SWTTAdr;
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
import fr.legrain.tiers.dao.TaTAdrDAO;
import fr.legrain.tiers.divers.Impression;
import fr.legrain.tiers.editor.EditorInputTypeAdresse;
import fr.legrain.tiers.editor.EditorTypeAdresse;

public class SWTPaTAdrController extends JPABaseControllerSWTStandard
		implements RetourEcranListener {

	static Logger logger = Logger.getLogger(SWTPaTAdrController.class.getName());
	private PaTAdrSWT vue = null;
	//private SWT_IB_TA_T_ADR ibTaTable = new SWT_IB_TA_T_ADR();
	private TaTAdrDAO dao = null;//new TaTAdrDAO();

	private Object ecranAppelant = null;
	private SWTTAdr swtTAdr;
	private SWTTAdr swtOldTAdr;
	private Realm realm;
	private DataBindingContext dbc;

	private Class classModel = SWTTAdr.class;
	//private ModelGeneral<SWTTAdr>  modelTAdr = new ModelGeneral<SWTTAdr>(ibTaTable.getFIBQuery(),classModel);
	private ModelGeneralObjet<SWTTAdr,TaTAdrDAO,TaTAdr> modelTAdr = null;//new ModelGeneralObjet<SWTTAdr,TaTAdrDAO,TaTAdr>(dao,classModel);
	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private IObservableValue selectedTAdr;
	private String[] listeChamp;
	private String nomClassController = this.getClass().getSimpleName();

	private LgrDozerMapper<SWTTAdr,TaTAdr> mapperUIToModel  = new LgrDozerMapper<SWTTAdr,TaTAdr>();
	private TaTAdr taTAdr = null;
	private MapChangeListener changeListener = new MapChangeListener();
	
	public SWTPaTAdrController(PaTAdrSWT vue) {
		this(vue,null);
	}

	public SWTPaTAdrController(PaTAdrSWT vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaTAdrDAO(getEm());
		modelTAdr = new ModelGeneralObjet<SWTTAdr,TaTAdrDAO,TaTAdr>(dao,classModel);
		setVue(vue);

		// a faire avant l'initialisation du Bindingaa
		vue.getGrille().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setSwtOldTAdr();
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

	public void bind(PaTAdrSWT paTAdrSWT) {
		try {
//			 modelTAdr = new ModelGeneral<SWTTAdr>(ibTaTable.getFIBQuery(),classModel);
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			tableViewer = new LgrTableViewer(paTAdrSWT.getGrille());
			tableViewer.createTableCol(classModel,paTAdrSWT.getGrille(), nomClassController,
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
//			writableList = new WritableList(realm, modelTAdr.remplirListe(), classModel);
//			tableViewer.setInput(writableList);
			
			// Set up data binding.
			LgrViewerSupport.bind(tableViewer, 
					new WritableList(modelTAdr.remplirListe(), classModel),
					BeanProperties.values(listeChamp)
					);

			selectedTAdr = ViewersObservables.observeSingleSelection(tableViewer);

			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			tableViewer.selectionGrille(0);

			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);

			bindingFormMaitreDetail(dbc, realm, selectedTAdr,classModel);
			
			changementDeSelection();
			selectedTAdr.addChangeListener(new IChangeListener() {

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
		if(selectedTAdr!=null && selectedTAdr.getValue()!=null) {
			if(((SWTTAdr) selectedTAdr.getValue()).getIdTAdr()!=null) {
				taTAdr = dao.findById(((SWTTAdr) selectedTAdr.getValue()).getIdTAdr());
			}
			//null a tester ailleurs, car peut etre null en cas d'insertion
			fireChangementDeSelection(new ChangementDeSelectionEvent(SWTPaTAdrController.this));
		}
	}

	public Composite getVue() {
		return vue;
	}

	public ResultAffiche configPanel(ParamAffiche param) {
		if (param != null) {
			//ibTaTable.ouvreDataset();
			if (((ParamAfficheTypeAdresse) param).getFocusDefautSWT() != null && !((ParamAfficheTypeAdresse) param).getFocusDefautSWT().isDisposed())
				((ParamAfficheTypeAdresse) param).getFocusDefautSWT().setFocus();
			else
				((ParamAfficheTypeAdresse) param).setFocusDefautSWT(vue.getGrille());
			vue.getLaTitreFormulaire().setText(((ParamAfficheTypeAdresse) param).getTitreFormulaire());
			vue.getLaTitreGrille().setText(((ParamAfficheTypeAdresse) param).getTitreGrille());
			vue.getLaTitreFenetre().setText(((ParamAfficheTypeAdresse) param).getSousTitre());

			if (param.getEcranAppelant() != null) {
				ecranAppelant = param.getEcranAppelant();
			}
			Map<String,String[]> map = dao.getParamWhereSQL();
			if(param.getIdFiche()!=null &&  !param.getIdFiche().equals("")) {
				if(map==null) map = new HashMap<String,String[]>();
				map.clear();
				map.put("idTAdr",new String[]{"=",param.getIdFiche()});
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
			setSwtOldTAdr();

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

	protected void initComposantsVue() throws ExceptLgr {
	}
	
	protected void initVerifySaisie() {
		if (mapInfosVerifSaisie == null)
			mapInfosVerifSaisie = new HashMap<Control, InfosVerifSaisie>();
		
		mapInfosVerifSaisie.put(vue.getTfCODE_T_ADR(), new InfosVerifSaisie(new TaTAdr(),Const.C_CODE_T_ADR,null));
		mapInfosVerifSaisie.put(vue.getTfLIBL_T_ADR(), new InfosVerifSaisie(new TaTAdr(),Const.C_LIBL_T_ADR,null));

		initVerifyListener(mapInfosVerifSaisie, dao);
	}

	protected void initMapComposantChamps() {
		if (mapComposantChamps == null)
			mapComposantChamps = new LinkedHashMap<Control, String>();

		if (listeComposantFocusable == null)
			listeComposantFocusable = new ArrayList<Control>();
		listeComposantFocusable.add(vue.getGrille());

		vue.getTfCODE_T_ADR().setToolTipText(Const.C_CODE_T_ADR);
		vue.getTfLIBL_T_ADR().setToolTipText(Const.C_LIBL_T_ADR);

		mapComposantChamps.put(vue.getTfCODE_T_ADR(), Const.C_CODE_T_ADR);
		mapComposantChamps.put(vue.getTfLIBL_T_ADR(), Const.C_LIBL_T_ADR);

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
				.getTfCODE_T_ADR());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue
				.getTfCODE_T_ADR());
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
					.getString("TypeAdresse.Message.Enregistrer"))) {

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
				setListeEntity(getModelTAdr().remplirListe());
				dao.initValeurIdTable(taTAdr);
				fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
						dao.getChampIdEntite(), dao.getValeurIdTable(),
						selectedTAdr.getValue())));

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
				setSwtOldTAdr();
				swtTAdr = new SWTTAdr();
				taTAdr = new TaTAdr();
				dao.inserer(taTAdr);
				//ibTaTable.insertion();
				modelTAdr.getListeObjet().add(swtTAdr);
				writableList = new WritableList(realm, modelTAdr.getListeObjet(), classModel);
				tableViewer.setInput(writableList);
				tableViewer.refresh();
				tableViewer.setSelection(new StructuredSelection(swtTAdr));
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
				setSwtOldTAdr();
				taTAdr = dao.findById(((SWTTAdr) selectedTAdr.getValue()).getIdTAdr());
			}else{
				if(!setSwtOldTAdrRefresh())throw new Exception();
			}
			dao.modifier(taTAdr);
			initEtatBouton();
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		}
	}
	
	public boolean containsEntity(TaTAdr entite){
		if(modelTAdr.getListeEntity()!=null){
			for (Object e : modelTAdr.getListeEntity()) {
				if(((TaTAdr)e).getIdTAdr()==
					entite.getIdTAdr())return true;
			}
		}
		return false;
	}

	public boolean setSwtOldTAdrRefresh() {
		try {	
			if (selectedTAdr.getValue()!=null){
				TaTAdr taOld =dao.findById(taTAdr.getIdTAdr());
				taOld=dao.refresh(taOld);
				if(containsEntity(taTAdr)) 
					modelTAdr.getListeEntity().remove(taTAdr);
				if(!taTAdr.getVersionObj().equals(taOld.getVersionObj())){
					taTAdr=taOld;
					if(!containsEntity(taTAdr)) 
						modelTAdr.getListeEntity().add(taTAdr);					
					actRefresh();
					dao.messageNonAutoriseModification();
				}
				taTAdr=taOld;
				if(!containsEntity(taTAdr)) 
					modelTAdr.getListeEntity().add(taTAdr);
				changementDeSelection();
				this.swtOldTAdr=SWTTAdr.copy((SWTTAdr)selectedTAdr.getValue());
			}
			return true;
		} catch (Exception e) {
			return false;
		}		
	}
//	public void setSwtOldTAdrRefresh() {
//		if (selectedTAdr.getValue()!=null){
//			if(LgrMess.isDOSSIER_EN_RESEAU())dao.refresh(dao.findById(((SWTTAdr) selectedTAdr.getValue()).getIdTAdr()));
//			taTAdr=dao.findById(((SWTTAdr) selectedTAdr.getValue()).getIdTAdr());
//			try {
//				if(LgrMess.isDOSSIER_EN_RESEAU())actRefresh();
//			} catch (Exception e) {
//				logger.error("",e);
//			}			
//			this.swtOldTAdr=SWTTAdr.copy((SWTTAdr)selectedTAdr.getValue());
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
						.getString("TypeAdresse.Message.Supprimer"))) {
					dao.begin(transaction);
					TaTAdr tar = dao.findById(((SWTTAdr) selectedTAdr.getValue()).getIdTAdr());
					dao.supprimer(tar);
					dao.commit(transaction);
					Object suivant=tableViewer.getElementAt(tableViewer.getTable().getSelectionIndex()+1);
					modelTAdr.removeEntity(taTAdr);
					taTAdr=null;
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
			// //InputVerifier inputVerifier =
			// getFocusCourant().getInputVerifier();
			// //getFocusCourant().setInputVerifier(null);
			//switch (ibTaTable.getFModeObjet().getMode()) {
			switch (dao.getModeObjet().getMode()) {
			case C_MO_INSERTION:
				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("TypeAdresse.Message.Annuler")))) {
					remetTousLesChampsApresAnnulationSWT(dbc);
					if(((SWTTAdr) selectedTAdr.getValue()).getIdTAdr()==null){
					modelTAdr.getListeObjet().remove(
							((SWTTAdr) selectedTAdr.getValue()));
					writableList = new WritableList(realm, modelTAdr
							.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					//tableViewer.selectionGrille(0);
					if(swtOldTAdr!=null)
						tableViewer.setSelection(new StructuredSelection(swtOldTAdr), true);
					else
						tableViewer.selectionGrille(0);
					}
					//ibTaTable.annuler();
					dao.annuler(taTAdr);
					hideDecoratedFields();
				}
				initEtatBouton();
				break;
			case C_MO_EDITION:
				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("TypeAdresse.Message.Annuler")))) {
					//int rang = getGrille().getSelectionIndex();
					int rang = modelTAdr.getListeObjet().indexOf(selectedTAdr.getValue());
					// selectedTAdr.setValue(swtOldTAdr);
					remetTousLesChampsApresAnnulationSWT(dbc);
					modelTAdr.getListeObjet().set(rang, swtOldTAdr);
					writableList = new WritableList(realm, modelTAdr.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.setSelection(new StructuredSelection(swtOldTAdr), true);
					//ibTaTable.annuler();
					dao.annuler(taTAdr);
					hideDecoratedFields();
				}
				initEtatBouton();

				break;
			case C_MO_CONSULTATION:
				//actionFermer.run();
				actFermer();
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

		fonctionGetInfosXmlAndProperties.setValueMapAttributeTable(TaTAdr.class.getSimpleName()+".head");
		fonctionGetInfosXmlAndProperties.setValueMapAttributeTable(TaTAdr.class.getSimpleName()+".detail");
		
		LinkedHashMap<String,AttributElementResport> mapAttributeTablHead = fonctionGetInfosXmlAndProperties.getMapAttributeTablHead(); 
		LinkedHashMap<String,AttributElementResport> mapAttributeTablDetail = fonctionGetInfosXmlAndProperties.getMapAttributeTablDetail();
		
		Collection<TaTAdr> collectionTaTAdr = modelTAdr.getListeEntity();
		
		fonctionGetInfosXmlAndProperties.findInfosFileXml(TaTAdr.class.getName(),SWTTAdr.class.getName(),
				listeChamp,"mapping");
		fonctionGetInfosXmlAndProperties.getInfosObjetJPA(taTAdr);

		ConstEdition constEdition = new ConstEdition(); 
//		Impression impression = new Impression(constEdition,taTAdr,collectionTaTAdr,nomChampIdTable,taTAdr.getIdTAdr());
		String nomDossier = null;

		int nombreLine = collectionTaTAdr.size();

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
			String folderEditionDynamique = Const.C_RCP_INSTANCE_LOCATION+"/"+Const.C_NOM_PROJET_TMP+"/"+TaTAdr.class.getSimpleName();
			constEdition.makeFolderEditions(folderEditionDynamique);
			Path pathFileReport = new Path(folderEditionDynamique+"/"+Const.C_NOM_VU_T_ADR+".rptdesign");
			final String pathFileReportDynamic = pathFileReport.toPortableString();

			MakeDynamiqueReport DynamiqueReport = new MakeDynamiqueReport(constEdition.getNameTableEcran(),
					constEdition.getNameTableBDD(),pathFileReportDynamic,Const.C_NOM_VU_T_ADR,
					ConstEdition.PAGE_ORIENTATION_LANDSCAPE,nomDossier); 
			DynamiqueReport.setSimpleNameEntity(TaTAdr.class.getSimpleName());
			/**************************************************************/
			DynamiqueReport.setFonctionGetInfosXml(fonctionGetInfosXmlAndProperties);
			DynamiqueReport.setNomObjet(TaTAdr.class.getSimpleName());
			/**************************************************************/

			Map<String, AttributElementResport> attribuGridHeader = new LinkedHashMap<String, AttributElementResport>();
			String nameHeaderTitle = ConstEditionTiers.TITLE_EDITION_TYPE_ADRESSE;
			attribuGridHeader.put(nameHeaderTitle, new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
					ConstEdition.FONT_SIZE_XX_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
					ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE,""));
			String nameSousHeaderTitle = ConstEditionTiers.SOUS_TITLE_EDITION_TYPE_ADRESSE;
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
					Const.C_NOM_VU_T_TEL,1,1,2,"40", mapAttributeTablHead, mapAttributeTablDetail);
			DynamiqueReport.savsAsDesignHandle();
//			impression.imprimer(true,pathFileReportDynamic,null,"Type Adresse",TaTAdr.class.getSimpleName(),false);

			/** 01/03/2010 modifier les editions (zhaolin) **/
			BaseImpressionEdition impressionEdition = new BaseImpressionEdition(constEdition,taTAdr,
					getEm(),collectionTaTAdr,taTAdr.getIdTAdr());
			impressionEdition.impressionEditionTypeEntity(pathFileReportDynamic,"Adresse");
		}
	}
	
	@Override
	protected boolean aideDisponible() {
		boolean result = false;
		//switch ((getThis().ibTaTable.getFModeObjet().getMode())) {
		switch (SWTPaTAdrController.this.dao.getModeObjet().getMode()) {
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
				switch (SWTPaTAdrController.this.dao.getModeObjet().getMode()) {
				case C_MO_CONSULTATION:
					if(getFocusCourantSWT().equals(vue.getGrille())){
						PaTAdrSWT paTAdrSWT = new PaTAdrSWT(s2,SWT.NULL);
						SWTPaTAdrController swtPaTAdrController = new SWTPaTAdrController(paTAdrSWT);

						editorCreationId = EditorTypeAdresse.ID;
						editorInputCreation = new EditorInputTypeAdresse();

						ParamAfficheTypeAdresse paramAfficheTAdr = new ParamAfficheTypeAdresse();
						//paramAfficheAideRecherche.setQuery(swtPaTAdrController.getIbTaTable().getFIBQuery());
						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
						paramAfficheTAdr.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheTAdr.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaTAdrController;
						parametreEcranCreation = paramAfficheTAdr;

						paramAfficheAideRecherche.setTypeEntite(TaTAdr.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_T_ADR);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_T_ADR().getText());
						paramAfficheAideRecherche.setControllerAppelant(SWTPaTAdrController.this);
						paramAfficheAideRecherche.setModel(swtPaTAdrController.getModelTAdr());
						paramAfficheAideRecherche.setTypeObjet(swtPaTAdrController.getClassModel());
						//paramAfficheAideRecherche.setChampsIdentifiant(swtPaTAdrController.getIbTaTable().champIdTable);
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_T_ADR);
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
					paAideController.addRetourEcranListener(SWTPaTAdrController.this);
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
		String validationContext = "T_ADR";
		try {
			//nomChamp = "codeUnite";
//			IStatus s = ValidationStatus.ok();
//			MultiStatus m = new MultiStatus("1",0,new IStatus[]{s},"Interface",null);
			IStatus s = null;
			boolean verrouilleModifCode = false;
			int change=0;

				//TaArticle u = null;
				//u = dao.findById(((SWTArticle) selectedArticle.getValue()).getIdArticle());
				TaTAdr u = new TaTAdr();
				PropertyUtils.setSimpleProperty(u, nomChamp, value);
				if(((SWTTAdr) selectedTAdr.getValue()).getIdTAdr()!=null) {
					u.setIdTAdr(((SWTTAdr) selectedTAdr.getValue()).getIdTAdr());
				}
				
				//LgrDozerMapper<SWTArticle,TaArticle> mapper = new LgrDozerMapper<SWTArticle,TaArticle>();
				//mapper.map((SWTArticle) selectedArticle.getValue(),taArticle);
				//dao.validate(u,nomChamp);
				if(nomChamp.equals(Const.C_CODE_T_ADR)){
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
				LgrDozerMapper<SWTTAdr,TaTAdr> mapper = new LgrDozerMapper<SWTTAdr,TaTAdr>();
				mapper.map((SWTTAdr) selectedTAdr.getValue(),taTAdr);
				taTAdr=dao.enregistrerMerge(taTAdr);
			}else if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)) {
				LgrDozerMapper<SWTTAdr,TaTAdr> mapper = new LgrDozerMapper<SWTTAdr,TaTAdr>();
				mapper.map((SWTTAdr) selectedTAdr.getValue(),taTAdr);
				taTAdr=dao.enregistrerMerge(taTAdr);
//				modelTAdr.getListeEntity().add(taTAdr);
			}
			
			dao.commit(transaction);
			modelTAdr.addEntity(taTAdr);
			transaction = null;
			
			TaTAdr typeAdr = taTAdr;
			if(typeAdr != null){
				Iterator<SWTTAdr> ite = modelTAdr.getListeObjet().iterator();
				SWTTAdr tmp = null;
				int i = 0;
				boolean trouve = false;
				while (ite.hasNext() && !trouve) {
					tmp = ite.next();
					if(tmp.getIdTAdr()==typeAdr.getIdTAdr()) {
//						tableViewer.selectionGrille(i);
						tableViewer.setSelection(new StructuredSelection(tmp));
						trouve = true;
						//((SWTUnite) selectedUnite.getValue()).setSWTUnite(tmp);
					}
					i++;
				}
			}
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
			vue.getTfCODE_T_ADR().setEditable(!isUtilise());
			changeCouleur(vue);

		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
		}
	}

	public SWTTAdr getSwtOldTAdr() {
		return swtOldTAdr;
	}

	public void setSwtOldTAdr(SWTTAdr swtOldTypeAdr) {
		this.swtOldTAdr = swtOldTypeAdr;
	}

	public void setSwtOldTAdr() {
		if (selectedTAdr.getValue() != null)
			this.swtOldTAdr = SWTTAdr.copy((SWTTAdr) selectedTAdr.getValue());
		else {
			if(tableViewer.selectionGrille(0)){
			this.swtOldTAdr = SWTTAdr.copy((SWTTAdr) selectedTAdr.getValue());
			tableViewer.setSelection(new StructuredSelection(
					(SWTTAdr) selectedTAdr.getValue()), true);
		}}
	}

	public void setVue(PaTAdrSWT vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();
		
		mapComposantDecoratedField.put(vue.getTfCODE_T_ADR(), vue.getFieldCODE_T_ADR());
		mapComposantDecoratedField.put(vue.getTfLIBL_T_ADR(), vue.getFieldLIBL_T_ADR());		
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
		TaTAdr u = taTAdr;
		if (u!=null && u.getIdTAdr()==0)
			u=dao.findById(u.getIdTAdr());
		writableList = new WritableList(realm, modelTAdr.remplirListe(), classModel);
		tableViewer.setInput(writableList);
		tableViewer.refresh();
		tableViewer.selectionGrille(
				tableViewer.selectionGrille(selectedTAdr.getValue()));
		
		if(u != null) {
			Iterator<SWTTAdr> ite = modelTAdr.getListeObjet().iterator();
			SWTTAdr tmp = null;
			int i = 0;
			boolean trouve = false;
			while (ite.hasNext() && !trouve) {
				tmp = ite.next();
				if(tmp.getIdTAdr()==u.getIdTAdr()) {
					tableViewer.setSelection(new StructuredSelection(tmp));
					trouve = true;
				}
				i++;
			}
		}		
	}

	public ModelGeneralObjet<SWTTAdr,TaTAdrDAO,TaTAdr> getModelTAdr() {
		return modelTAdr;
	}

	public TaTAdrDAO getDao() {
		return dao;
	}

	public TaTAdr getTaUnite() {
		return taTAdr;
}
	public boolean isUtilise(){
		return (((SWTTAdr)selectedTAdr.getValue()).getIdTAdr()!=null &&
		!dao.recordModifiable(dao.getNomTable(),
				((SWTTAdr)selectedTAdr.getValue()).getIdTAdr()))||
				!dao.autoriseModification(taTAdr);		
	}

	public void actAfficherTous() throws Exception{
		vue.getGrille().setVisible(true);  
		vue.getBtnTous().setVisible(false);
		vue.getLaTitreGrille().setVisible(true);
		vue.getCompositeForm().setWeights(new int[]{50,100});
		dao.setJPQLQuery(dao.getJPQLQueryInitial());
		modelTAdr.setJPQLQuery(null);
		modelTAdr.setListeEntity(null);
		try {
			actRefresh();
		} catch (Exception e1) {
			logger.error("", e1);
		}
	}
}

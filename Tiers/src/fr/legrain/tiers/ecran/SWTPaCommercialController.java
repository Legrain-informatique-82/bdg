package fr.legrain.tiers.ecran;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
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

import fr.legrain.articles.dao.TaCategorieArticle;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Articles.SWTPrix;
import fr.legrain.gestCom.Module_Articles.SWTTypeCategorieArticle;
import fr.legrain.gestCom.Module_Document.IHMAdresse;
import fr.legrain.gestCom.Module_Tiers.SWTCommercial;
import fr.legrain.gestCom.Module_Tiers.SWTTiers;
import fr.legrain.gestCom.gestComBd.gestComBdPlugin;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.DeclencheCommandeControllerEvent;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
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
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.data.VerrouInterface;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
import fr.legrain.lib.gui.DestroyEvent;
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
import fr.legrain.tiers.dao.TaAdresseDAO;
import fr.legrain.tiers.dao.TaFamilleTiers;
import fr.legrain.tiers.dao.TaTAdr;
import fr.legrain.tiers.dao.TaTEntite;
import fr.legrain.tiers.dao.TaTEntiteDAO;
import fr.legrain.tiers.dao.TaTTiersDAO;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.editor.EditorCommercial;
import fr.legrain.tiers.editor.EditorInputCommercial;
import fr.legrain.tiers.editor.EditorInputTiers;
import fr.legrain.tiers.editor.EditorTiers;
import fr.legrain.tiers.editor.TiersMultiPageEditor;

public class SWTPaCommercialController extends JPABaseControllerSWTStandard
implements RetourEcranListener {

	static Logger logger = Logger.getLogger(SWTPaCommercialController.class.getName());
	private PaCommercialSWT vue = null;
	//	private SWT_IB_TA_R_COMMERCIAL dao = new SWT_IB_TA_R_COMMERCIAL();
	private TaTiersDAO dao = null;//new TaTiersDAO();


	private Integer idTiers = null;

	// private String idCommentaire = null;
	// private String idTypeTiers = null;
	private Object ecranAppelant = null;
	private SWTCommercial swtCommercial;
	private SWTCommercial swtOldCommercial;
	private Realm realm;
	private DataBindingContext dbc;

	private Class classModel = SWTCommercial.class;
	//	private ModelGeneral<SWTCommercial> modelCommercial = new ModelGeneral<SWTCommercial>(dao.getFIBQuery(),classModel);
	private ModelGeneralObjet<SWTCommercial,TaTiersDAO,TaTiers> modelCommercial = null;//new ModelGeneralObjet<SWTCommercial,TaTiersDAO,TaTiers>(dao,classModel);
	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private IObservableValue selectedCommercial;
	private String[] listeChamp;
	private String nomClassController = this.getClass().getSimpleName();

	private TaTiers masterEntity = null;
	private TaTiersDAO masterDAO = null;

	private LgrDozerMapper<SWTCommercial,TaTiers> mapperUIToModel  = new LgrDozerMapper<SWTCommercial,TaTiers>();
	private LgrDozerMapper<TaTiers,SWTCommercial> mapperModelToUI  = new LgrDozerMapper<TaTiers,SWTCommercial>();
	private TaTiers taCommercial = null;

	private MapChangeListener changeListener = new MapChangeListener();

	public SWTPaCommercialController(PaCommercialSWT vue) {
		this(vue,null);
	}

	public SWTPaCommercialController(PaCommercialSWT vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaTiersDAO(getEm());
		modelCommercial = new ModelGeneralObjet<SWTCommercial,TaTiersDAO,TaTiers>(dao,classModel);
		setVue(vue);

		// a faire avant l'initialisation du Binding
		vue.getGrille().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setSwtOldCommercial();
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

	/**
	 * Creation des objet pour l'interface, a partir de l'entite principale.<br>
	 * @return
	 */
	public List<SWTCommercial> IHMmodel() {
		LinkedList<TaTiers> ldao = new LinkedList<TaTiers>();
		LinkedList<SWTCommercial> lswt = new LinkedList<SWTCommercial>();

		if(masterEntity!=null && !masterEntity.getTaCommerciaux().isEmpty()) {

			ldao.addAll(masterEntity.getTaCommerciaux());

			LgrDozerMapper<TaTiers,SWTCommercial> mapper = new LgrDozerMapper<TaTiers,SWTCommercial>();
			for (TaTiers o : ldao) {
				SWTCommercial t = null;
				t = (SWTCommercial) mapper.map(o, SWTCommercial.class);
				lswt.add(t);
			}

		}
		return lswt;
	}

	protected void initEtatBouton() {
		initEtatBouton(IHMmodel());
	}

	public void bind(PaCommercialSWT paCommercialSWT) {
		try {
			// modelCommercial = new ModelGeneral<SWTCommercial>(ibTaTable.getFIBQuery(),classModel);
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			tableViewer = new LgrTableViewer(paCommercialSWT.getGrille());
			tableViewer.createTableCol(classModel,paCommercialSWT.getGrille(), nomClassController,
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
			//			List<SWTCommercial> lswt = IHMmodel();
			//			writableList = new WritableList(realm, lswt, classModel);
			//			tableViewer.setInput(writableList);

			// Set up data binding.
			LgrViewerSupport.bind(tableViewer, 
					new WritableList(IHMmodel(), classModel),
					BeanProperties.values(listeChamp)
			);

			selectedCommercial = ViewersObservables
			.observeSingleSelection(tableViewer);

			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			tableViewer.selectionGrille(0);

			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);

			bindingFormMaitreDetail(dbc, realm, selectedCommercial,classModel);
			changementDeSelection();
			selectedCommercial.addChangeListener(new IChangeListener() {

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
		if(selectedCommercial!=null && selectedCommercial.getValue()!=null) {
			if(((SWTCommercial) selectedCommercial.getValue()).getIdTiers()!=null) {
				taCommercial = dao.findById(((SWTCommercial) selectedCommercial.getValue()).getIdTiers());
				
				if(taCommercial!=null 
						&& masterEntity.getTaCommercial()!=null 
						&& taCommercial.getCodeTiers().equals(masterEntity.getTaCommercial().getCodeTiers())) {
					((SWTCommercial) selectedCommercial.getValue()).setDefaut(true);
				} else {
					((SWTCommercial) selectedCommercial.getValue()).setDefaut(false);
				}
			}
			//null a tester ailleurs, car peut etre null en cas d'insertion
			fireChangementDeSelection(new ChangementDeSelectionEvent(SWTPaCommercialController.this));
		}
	}

	public Composite getVue() {
		return vue;
	}

	public ResultAffiche configPanel(ParamAffiche param) {
		Map map=dao.getParamWhereSQL();
		if (param != null) {
			//			dao.ouvreDataset();
			if (((ParamAfficheCommercial) param).getFocusDefautSWT() != null && !((ParamAfficheCommercial) param).getFocusDefautSWT().isDisposed())
				((ParamAfficheCommercial) param).getFocusDefautSWT().setFocus();
			else
				((ParamAfficheCommercial) param).setFocusDefautSWT(vue
						.getGrille());
			vue.getLaTitreFormulaire().setText(
					((ParamAfficheCommercial) param).getTitreFormulaire());
			vue.getLaTitreGrille().setText(
					((ParamAfficheCommercial) param).getTitreGrille());
			vue.getLaTitreFenetre().setText(
					((ParamAfficheCommercial) param).getSousTitre());
			if(((ParamAfficheCommercial)param).getIdTiers()!=null) {
				this.idTiers = LibConversion.stringToInteger(((ParamAfficheCommercial)param).getIdTiers());
				if(map==null)map=new HashMap();
				if (!map.containsKey(Const.C_ID_TIERS))
					map.put(Const.C_ID_TIERS,new String[]{"=",String.valueOf(idTiers)});
				dao.setParamWhereSQL(map);
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
					taCommercial=null;
					selectedCommercial.setValue(null);
					actRefresh();
				} catch (Exception e) {
					logger.error("",e);
				}
			}
			// permet de se positionner sur le 1er enregistrement et de remplir
			// le formulaire
			//			tableViewer.tri(classModel, nomClassController,Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			VerrouInterface.setVerrouille(false);
			setSwtOldCommercial();

			if (param.getModeEcran() != null
					&& param.getModeEcran().compareTo(
							EnumModeObjet.C_MO_INSERTION) == 0) {
				try {
					actInserer();
				} catch (Exception e) {
					vue.getLaMessage().setText(e.getMessage());
					logger.error("", e);
				}
			}

			//			param.setFocus(dao.getFModeObjet().getFocusCourant());
		}
		return null;
	}
	
	protected void initVerifySaisie() {
		if (mapInfosVerifSaisie == null)
			mapInfosVerifSaisie = new HashMap<Control, InfosVerifSaisie>();
		
		mapInfosVerifSaisie.put(vue.getTfCOMMERCIAL(), new InfosVerifSaisie(new TaTiers(),Const.C_CODE_TIERS,null));

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

		vue.getTfCOMMERCIAL().setToolTipText(Const.C_CODE_TIERS);
		vue.getCbDefaut().setToolTipText(Const.C_COMMERCIAL_DEFAUT);

		mapComposantChamps.put(vue.getTfCOMMERCIAL(), Const.C_CODE_TIERS);
		mapComposantChamps.put(vue.getCbDefaut(), Const.C_COMMERCIAL_DEFAUT);

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
				.getTfCOMMERCIAL());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue
				.getTfCOMMERCIAL());
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

		mapCommand.put(C_COMMAND_GLOBAL_PRECEDENT_ID, handlerPrecedent);
		mapCommand.put(C_COMMAND_GLOBAL_SUIVANT_ID, handlerSuivant);

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

	public SWTPaCommercialController getThis() {
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
					.getString("Commercial.Message.Enregistrer"))) {

				try {
					actEnregistrer();
				} catch (Exception e) {
					vue.getLaMessage().setText(e.getMessage());
					logger.error("", e);
				}
			} else {
				fireAnnuleTout(new AnnuleToutEvent(this,true));
			}
			//				dao.annuler();

			break;
		case C_MO_CONSULTATION:
			break;
		default:
			break;
		}

		if (retour) {
			if (ecranAppelant instanceof SWTPaAideControllerSWT) {
				setListeEntity(getModelCommercial().remplirListe());
				dao.initValeurIdTable(taCommercial);
				fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
						dao.getChampIdEntite(), dao.getValeurIdTable(),
						selectedCommercial.getValue())));
				//				vue.getDisplay().asyncExec(new Runnable() {
				//					public void run() {
				//						vue.getShell().setVisible(false);
				//					}
				//				});
				//				retour = false;
				retour = true;
			} else {
				fireRetourEcran(new RetourEcranEvent(this,new ResultAffiche()));
			}
		}
		if (retour && !(ecranAppelant instanceof SWTPaAideControllerSWT)) {
			fireDestroy(new DestroyEvent(dao));
		}
		return retour;
	}

	public void retourEcran(final RetourEcranEvent evt) {
		if (evt.getRetour() != null
				&& (evt.getSource() instanceof SWTPaAideControllerSWT)) {
			if (getFocusAvantAideSWT() instanceof Text) {
				try {
					((Text) getFocusAvantAideSWT()).setText(((ResultAffiche) evt.getRetour()).getResult());	
					if(getFocusAvantAideSWT().equals(vue.getTfCOMMERCIAL())){
						TaTiers entity = null;
						//TaTiersDAO dao = new TaTiersDAO();
						entity = dao.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
						//dao = null;
						taCommercial = entity;
					}
					ctrlUnChampsSWT(getFocusAvantAideSWT());
				} catch (Exception e) {
					logger.error("",e);
					vue.getLaMessage().setText(e.getMessage());
				}
			}
			if (getFocusAvantAideSWT() instanceof Table) {
				if (getFocusAvantAideSWT() == vue.getGrille()) {
					//					dao.lgrLocateID(((ResultAffiche) evt.getRetour()).getIdResult());
					if(((ResultAffiche) evt.getRetour()).getSelection()!=null)
						tableViewer.setSelection(((ResultAffiche) evt.getRetour()).getSelection(),true);
				}
			}
		} else if (evt.getRetour() != null) {
			if (getFocusAvantAideSWT() instanceof Table) {
				if (getFocusAvantAideSWT() == vue.getGrille()) {
					//					dao.lgrLocateID(((ResultAffiche) evt.getRetour()).getIdResult());
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
			boolean continuer=true;
			VerrouInterface.setVerrouille(true);
			masterDAO.verifAutoriseModification(masterEntity);
			setSwtOldCommercial();
			if(LgrMess.isDOSSIER_EN_RESEAU()){
				DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
				fireDeclencheCommandeController(e);
				continuer=masterDAO.dataSetEnModif();
			}
			if(continuer){
				swtCommercial = new SWTCommercial();			
				swtCommercial.setIdTiers(idTiers);						
				dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_INSERTION);
				List l = IHMmodel();
				l.add(swtCommercial);
				writableList = new WritableList(realm, l, classModel);
				tableViewer.setInput(writableList);
				tableViewer.refresh();
				tableViewer.setSelection(new StructuredSelection(swtCommercial));
				initEtatBouton();

				try {
					DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
					fireDeclencheCommandeController(e);
				} catch (Exception e) {
					logger.error("",e);
				}
			}
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			throw e;
		} finally {
			initEtatComposant();
			VerrouInterface.setVerrouille(false);
		}
	}

	@Override
	protected void actModifier() throws Exception {
		try {
			boolean continuer=true;
			setSwtOldCommercial();
			if(LgrMess.isDOSSIER_EN_RESEAU()){
				DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
				fireDeclencheCommandeController(e);
				continuer=masterDAO.dataSetEnModif();
			}
			if(continuer){
				for (TaTiers p : masterEntity.getTaCommerciaux()) {
					if(p.getIdTiers()==((SWTCommercial) selectedCommercial.getValue()).getIdTiers()) {
						taCommercial = p;
//						if(LgrMess.isDOSSIER_EN_RESEAU())actRefresh();
					}
				}
				masterDAO.verifAutoriseModification(masterEntity);
				DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
				fireDeclencheCommandeController(e);
				dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_EDITION);
				initEtatBouton();
			}
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			throw e;
		}
	}

	@Override
	protected void actSupprimer() throws Exception {
		EntityTransaction transaction = dao.getEntityManager().getTransaction();		
		try {
			boolean continuer=true;
			VerrouInterface.setVerrouille(true);
//			if(isUtilise())MessageDialog.openInformation(vue.getShell(), MessagesEcran
//					.getString("Message.Attention"), MessagesEcran
//					.getString("Message.suppression"));
//			else
				if(LgrMess.isDOSSIER_EN_RESEAU()){
					DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
					fireDeclencheCommandeController(e);
					continuer=masterDAO.dataSetEnModif();
				}
				
				if(continuer){
					setMasterEntity(masterDAO.findById(masterEntity.getIdTiers()));
					if (MessageDialog.openConfirm(vue.getShell(), MessagesEcran
							.getString("Message.Attention"), MessagesEcran
							.getString("Commercial.Message.Supprimer"))) {				
						dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_SUPPRESSION);
						try {
							DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
							fireDeclencheCommandeController(e);
						} catch (Exception e) {
							logger.error("",e);
						}
						for (TaTiers p : masterEntity.getTaCommerciaux()) {
							if(p.getIdTiers()==((SWTCommercial) selectedCommercial.getValue()).getIdTiers()) {
								taCommercial = p;
							}
						}
						masterEntity.removeCommercial(taCommercial);
						Object suivant=tableViewer.getElementAt(tableViewer.getTable().getSelectionIndex()+1);
						taCommercial=null;
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
		} catch (ExceptLgr e) {
			vue.getLaMessage().setText(e.getMessage());
			throw e;
		} finally {
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
			switch (dao.getModeObjet().getMode()) {
			case C_MO_INSERTION:
				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("Commercial.Message.Annuler")))) {
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
						.getString("Commercial.Message.Annuler")))) {
					int rang =((WritableList)tableViewer.getInput()).indexOf(selectedCommercial.getValue());
					List<SWTCommercial> l = IHMmodel();
					if(rang!=-1)
						l.set(rang, swtOldCommercial);
					remetTousLesChampsApresAnnulationSWT(dbc);
					writableList = new WritableList(realm, l, classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();

					tableViewer.setSelection(new StructuredSelection(swtOldCommercial), true);
					//remetTousLesChampsApresAnnulationSWT(dbc);
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
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			throw e;
		} finally {
			VerrouInterface.setVerrouille(false);
		}
	}

	@Override
	protected void actImprimer() throws Exception {
		//		// // TODO procï¿½dure d'impression
		//		// JOptionPane.showMessageDialog(vue, Const.C_A_IMPLEMENTER,
		//		// MessagesEcran.getString("Message.Attention"),
		//		// JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
		//		//initFocusSWT(ibTaTable, mapInitFocus);
		//		/**************************************************/
		////		if(vue.getGrille()==getFocusCourantSWT()){
		//
		//		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO();
		//		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
		//			String nomDossier = null;
		//			ConstEdition objetConstEdition = new ConstEdition(); 
		//			int nombreLine = objetConstEdition.nombreLineTable(tableViewer);
		//			if(nombreLine==0){
		//				MessageDialog.openError(vue.getShell(), "ALTER",
		//						"Il n'y a rien dans Cette Edition !");
		//			}
		//			else{
		//				if(taInfoEntreprise.getIdInfoEntreprise()==0){
		//					nomDossier = objetConstEdition.INFOS_VIDE;
		//				}
		//				else{
		//					nomDossier = taInfoEntreprise.getNomInfoEntreprise();	
		//				}
		//				String querySql = dao.getFIBQuery().getQuery().getQueryString();//select * from V_COMMERCIAL where ID_TIERS = 1256
		//				String nameTable = dao.nomTable;
		//				System.out.println(querySql+"----"+nameTable+"----"+nomDossier);
		//				
		//				String nameClass = SWTPaCommercialController.class.getSimpleName();
		//				String sqlQueryStart = "SELECT ";
		//				String sqlQueryEnd = " FROM "+Const.C_NOM_VU_COMMERCIAL;
		//
		//				String sqlQueryMiddle = objetConstEdition.addValueList(tableViewer, nameClass);
		//				ArrayList<String> nameTableEcran = objetConstEdition.getNameTableEcran();
		//				ArrayList<String> nameTableBDD = objetConstEdition.getNameTableBDD();
		//				/*
		//				 * name ecran ==> COMMERCIAL--NOM TIERS--TYPE TIERS
		//				 */
		////				for(int i=0;i<nameTableEcran.size();i++){
		////				System.out.println(nameTableEcran.get(i));
		////				}
		//				/*
		//				 * name BDD ==> COMMERCIAL(string)--NOM_TIERS(string)--CODE_T_TIERS(string)
		//				 */
		////				System.out.println("***********");
		////				for(int i=0;i<nameTableBDD.size();i++){
		////				System.out.println(nameTableBDD.get(i));
		////				}
		//				/*
		//				 * SELECT COMMERCIAL,NOM_TIERS,CODE_T_TIERS FROM V_COMMERCIAL where ID_TIERS = 1256;
		//				 */
		//				String sqlQuery =  objetConstEdition.obtainSubString(sqlQueryStart,
		//						sqlQueryMiddle,sqlQueryEnd,querySql);
		//				//System.out.println(sqlQuery);
		//				
		//				String  C_FICHIER_BDD = Const.C_FICHIER_BDD.replaceFirst(":", "/");
		//				//System.out.println(C_FICHIER_BDD);
		//				String FILE_BDD = Const.C_URL_BDD+"//"+C_FICHIER_BDD;//jdbc:firebirdsql://localhost/C:/runtime-GestionCommercialeComplet.product/dossier/Bd/GEST_COM.FDB
		//				//System.out.println(FILE_BDD);
		//				
		//				Path pathFileReport = new Path(Const.C_RCP_INSTANCE_LOCATION+"/"+Const.C_NOM_PROJET_TMP+"/"
		//						+Const.C_NOM_VU_COMMERCIAL+ConstEdition.TYPE_FILE_REPORT);
		//				final String PATH_FILE_REPORT = pathFileReport.toPortableString();
		//				
		//				Map<String, AttributElementResport> attribuTabHeader = new LinkedHashMap<String, AttributElementResport>();
		//				attribuTabHeader.put("COMMERCIAL", new AttributElementResport("33",ConstEdition.TEXT_ALIGN_CENTER,
		//						ConstEdition.FONT_SIZE_MEDIUM,ConstEdition.FONT_WEIGHT_BOLD,
		//						ConstEdition.UNITS_PERCENTAGE,ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
		//						,""));
		//				attribuTabHeader.put("NOM TIERS", new AttributElementResport("33",ConstEdition.TEXT_ALIGN_CENTER,
		//						ConstEdition.FONT_SIZE_MEDIUM,ConstEdition.FONT_WEIGHT_BOLD,
		//						ConstEdition.UNITS_PERCENTAGE,ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
		//						,""));
		//				attribuTabHeader.put("TYPE TIERS", new AttributElementResport("33",ConstEdition.TEXT_ALIGN_CENTER,
		//						ConstEdition.FONT_SIZE_MEDIUM,ConstEdition.FONT_WEIGHT_BOLD,
		//						ConstEdition.UNITS_PERCENTAGE,ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
		//						,""));
		//				
		//				Map<String, AttributElementResport> attribuTabDetail = new LinkedHashMap<String, AttributElementResport>();
		//				attribuTabDetail.put("COMMERCIAL", new AttributElementResport("33",ConstEdition.TEXT_ALIGN_CENTER,
		//						ConstEdition.FONT_SIZE_MEDIUM,ConstEdition.FONT_WEIGHT_NORMAL,
		//						ConstEdition.UNITS_PERCENTAGE,ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
		//						,""));
		//				attribuTabDetail.put("NOM_TIERS", new AttributElementResport("33",ConstEdition.TEXT_ALIGN_CENTER,
		//						ConstEdition.FONT_SIZE_MEDIUM,ConstEdition.FONT_WEIGHT_NORMAL,
		//						ConstEdition.UNITS_PERCENTAGE,ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
		//						,""));
		//				attribuTabDetail.put("CODE_T_TIERS", new AttributElementResport("33",ConstEdition.TEXT_ALIGN_CENTER,
		//						ConstEdition.FONT_SIZE_MEDIUM,ConstEdition.FONT_WEIGHT_NORMAL,
		//						ConstEdition.UNITS_PERCENTAGE,ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
		//						,""));
		//				
		//				MakeDynamiqueReport DynamiqueReport = new MakeDynamiqueReport(nameTableEcran,nameTableBDD,
		//						sqlQuery/*,ConstEdition.BIRT_HOME*/,PATH_FILE_REPORT,Const.
		//						C_DRIVER_JDBC,FILE_BDD,Const.C_USER,Const.C_PASS,
		//						Const.C_NOM_VU_COMMERCIAL,ConstEdition.PAGE_ORIENTATION_PORTRAIT,
		//						nomDossier);
		//				
		//				Map<String, AttributElementResport> attribuGridHeader = new LinkedHashMap<String, AttributElementResport>();
		//				String nameHeaderTitle = ConstEditionTiers.TITLE_EDITION_TIERS_COMERCIAUX;
		//				attribuGridHeader.put(nameHeaderTitle, new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
		//						ConstEdition.FONT_SIZE_XX_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
		//						ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
		//						,""));
		//				String nameSousHeaderTitle = ConstEditionTiers.SOUS_TITLE_EDITION_TIERS_COMERCIAUX;
		//				attribuGridHeader.put(nameSousHeaderTitle, new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
		//						ConstEdition.FONT_SIZE_X_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
		//						ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
		//						,ConstEdition.COLOR_GRAY));
		//				
		//				ConstEdition.CONTENT_COMMENTS = ConstEditionTiers.COMMENTAIRE_EDITION_DEFAUT;
		//				DynamiqueReport.initializeBuildDesignReportConfig();
		//				DynamiqueReport.makePageMater("1", "1", "1", "1", "100");
		//				DynamiqueReport.makeReportHeaderGrid(3,5,100,ConstEdition.UNITS_PERCENTAGE,attribuGridHeader);
		//				
		//				DynamiqueReport.makeReportTableDB(100,ConstEdition.UNITS_PERCENTAGE,
		//						Const.C_NOM_VU_COMMERCIAL,attribuTabHeader,attribuTabDetail,1,1,2,5,"30");
		//				
		//				DynamiqueReport.savsAsDesignHandle();
		//				Bundle bundleCourant = TiersPlugin.getDefault().getBundle();
		//				
		////				String reportFileLocation = ConstEdition.FOLDER_REPORT_PLUGIN;
		////				
		////				URL urlReportFile = Platform.asLocalURL(bundleCourant.getEntry(reportFileLocation));
		////				URI uriReportFile = new URI("file", urlReportFile.getAuthority(),
		////						urlReportFile.getPath(), urlReportFile.getQuery(), null);
		////
		////				File pathReport = new File(uriReportFile);
		////				String pathFileEdition = pathReport.toString()+ConstEditionTiers.SOUS_REPORT_COMMERCIAL;
		////				
		////				File reportFile = objetConstEdition.makeFolderEditions(pathFileEdition);
		//				
		//				File reportFile = objetConstEdition.findPathReportPlugin(bundleCourant, 
		//						ConstEdition.FOLDER_REPORT_PLUGIN, ConstEditionTiers.SOUS_REPORT_COMMERCIAL);
		//				
		//				final String namePlugin = bundleCourant.getSymbolicName();//name plugin is <<Tiers>>
		//				
		//				
		//				objetConstEdition.makeFolderEditions(Const.C_REPERTOIRE_BASE+
		//						Const.C_REPERTOIRE_PROJET.replaceFirst("/", "")+
		//						ConstEdition.FOLDER_EDITION);
		//				
		//				String FloderEdition = Const.C_REPERTOIRE_BASE+
		//				Const.C_REPERTOIRE_PROJET.replaceFirst("/", "")+ConstEdition.FOLDER_EDITION+
		//				namePlugin+"/"+ConstEditionTiers.TIERS_COMERCIAUX;
		//				
		//				File FloderFileEditions = objetConstEdition.makeFolderEditions(FloderEdition);
		//
		//				String nomOnglet = ConstEdition.EDITION+ConstEditionTiers.TIERS_COMERCIAUX;
		//
		//				Shell dialogShell = new Shell(vue.getShell(),
		//						//SWT.DIALOG_TRIM |SWT.APPLICATION_MODAL);
		//						SWT.RESIZE | SWT.CLOSE | SWT.MAX | SWT.APPLICATION_MODAL);
		//				dialogShell.setText(ConstEdition.TITLE_SHELL);
		//				dialogShell.setLayout(new FillLayout());
		//				//dialogShell
		//				SwtCompositeReport_new dialogReport = new SwtCompositeReport_new(dialogShell,SWT.NULL);
		//				objetConstEdition.openDialogChoixEdition_new(dialogReport, FloderFileEditions, 
		//						PATH_FILE_REPORT, ConstEditionTiers.TIERS_COMERCIAUX,nomOnglet,dialogShell,reportFile);
		//			}
		////		}
		////		else{
		////		
		//////			MessageDialog.openWarning(vue.getShell(), "ALTER",
		//////			"Cette Edition n'esdddt pas disponible en ce moment !");
		////		}
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
			if(getFocusCourantSWT().equals(vue.getTfCOMMERCIAL()))
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
				//				paramAfficheAideRecherche.setDb(getThis().getDao().getFIBBase());
				paramAfficheAideRecherche.setMessage(message);
				//Crï¿½ation de l'ecran d'aide principal
				Shell s = new Shell(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),LgrShellUtil.styleLgr);
				PaAideSWT paAide = new PaAideSWT(s,SWT.NONE);
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
						PaCommercialSWT paCommercialSWT = new PaCommercialSWT(s2,SWT.NULL);
						SWTPaCommercialController swtPaCommercialController = new SWTPaCommercialController(paCommercialSWT);

						editorCreationId = EditorCommercial.ID;
						editorInputCreation = new EditorInputCommercial();
						
						paramAfficheAideRecherche.setForceAffichageAideRemplie(true);
						paramAfficheAideRecherche.setAfficheDetail(false);

						ParamAfficheCommercial paramAfficheCommercial = new ParamAfficheCommercial();
						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
						paramAfficheCommercial.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheCommercial.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaCommercialController;
						parametreEcranCreation = paramAfficheCommercial;

						paramAfficheAideRecherche.setTypeEntite(TaTiers.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_TIERS);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCOMMERCIAL().getText());
						paramAfficheAideRecherche.setControllerAppelant(getThis());
						paramAfficheAideRecherche.setModel(swtPaCommercialController.getModelCommercial());
						paramAfficheAideRecherche.setTypeObjet(swtPaCommercialController.getClassModel());
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_TIERS);
					}
					break;
				case C_MO_EDITION:
				case C_MO_INSERTION:
					if(getFocusCourantSWT().equals(vue.getTfCOMMERCIAL())){
						PaTiersSWT paTiersSWT = new PaTiersSWT(s2,SWT.NULL);
						SWTPaTiersController swtPaTiersController = new SWTPaTiersController(paTiersSWT);
						paramAfficheAideRecherche.setForceAffichageAideRemplie(false);

						editorCreationId = TiersMultiPageEditor.ID_EDITOR;
						editorInputCreation = new EditorInputTiers();
						ParamAfficheTiers paramAfficheTiers = new ParamAfficheTiers();
						paramAfficheAideRecherche.setJPQLQuery(new TaTiersDAO(getEm()).getJPQLQuery());
						paramAfficheTiers.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheTiers.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaTiersController;
						parametreEcranCreation = paramAfficheTiers;

						paramAfficheAideRecherche.setAfficheDetail(false);
						paramAfficheAideRecherche.setForceAffichageAideRemplie(true);
						
						paramAfficheAideRecherche.setTypeEntite(TaTiers.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_TIERS);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCOMMERCIAL().getText());
						paramAfficheAideRecherche.setControllerAppelant(getThis());
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTiers,TaTiersDAO,TaTiers>(dao.rechercheParType("COM"),SWTTiers.class,dao.getEntityManager()));
						paramAfficheAideRecherche.setTypeObjet(SWTTiers.class);

						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_TIERS);
					}
					break;
				default:
					break;
				}
				if (paramAfficheAideRecherche.getJPQLQuery()!=null){				

					PaAideRechercheSWT paAideRecherche1 = new PaAideRechercheSWT(s,SWT.NULL);	
					SWTPaAideRechercheControllerSWT paAideRechercheController1 = new SWTPaAideRechercheControllerSWT(paAideRecherche1);

					//Paramï¿½trage de la recherche
					paramAfficheAideRecherche.setFocusSWT(((PaAideRechercheSWT)paAideRechercheController1.getVue()).getTfChoix());
					paramAfficheAideRecherche.setRefCreationSWT(controllerEcranCreation);
					paramAfficheAideRecherche.setEditorCreation(editorCreation);
					paramAfficheAideRecherche.setEditorCreationId(editorCreationId);
					paramAfficheAideRecherche.setEditorInputCreation(editorInputCreation);
					paramAfficheAideRecherche.setParamEcranCreation(parametreEcranCreation);
					paramAfficheAideRecherche.setShellCreation(s2);
					paAideRechercheController1.configPanel(paramAfficheAideRecherche);
					//paramAfficheAideRecherche.setFocusDefaut(paAideRechercheController1.getVue().getTfChoix());

					//Ajout d'une recherche
					paAideController.addRecherche(paAideRechercheController1, paramAfficheAideRecherche.getTitreRecherche());

					//Paramï¿½trage de l'ï¿½cran d'aide principal
					ParamAfficheAideSWT paramAfficheAideSWT = new ParamAfficheAideSWT();
					ParamAfficheAide paramAfficheAide = new ParamAfficheAide();

					//enregistrement pour le retour de l'ecran d'aide
					paAideController.addRetourEcranListener(getThis());
					Control focus = vue.getShell().getDisplay().getFocusControl();
					//affichage de l'ecran d'aide principal (+ ses recherches)

					dbc.getValidationStatusMap().removeMapChangeListener(changeListener);
					//LgrShellUtil.afficheAideSWT(paramAfficheAide, null, paAide,paAideController, s);
					/*****************************************************************/
					paAideController.configPanel(paramAfficheAide);
					/*****************************************************************/
					dbc.getValidationStatusMap().addMapChangeListener(changeListener);

				}
			} catch (Exception e) {
				vue.getLaMessage().setText(e.getMessage());
				throw e;
			}finally{
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
		String validationContext = "R_COMMERCIAL";
		try {
			IStatus s = null;
			if(nomChamp.equals(Const.C_COMMERCIAL_DEFAUT)) {
				s = new Status(IStatus.OK, TiersPlugin.PLUGIN_ID, "ok");
			} else {
				TaTiers u = new TaTiers();
				PropertyUtils.setSimpleProperty(u, nomChamp, value);
				s = dao.validate(u,nomChamp,validationContext);
				if(s.isOK()){
					taCommercial = dao.findByCode(u.getCodeTiers());
				}
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
				if( ((SWTCommercial) selectedCommercial.getValue()).getIdTiers()!= taCommercial.getIdTiers()) {
					//l'entité a bien été modifiée, il faut la changer dans la liste de la masterEntity
					//traitement spécial pour les listes où le F1 ou le validateUI change entièrement l'entité de l'écran et non uniquement une propriété (type) de celle-ci
					boolean trouve = false;
					TaTiers tmp = null;
					for (TaTiers p : masterEntity.getTaCommerciaux()) {
						if(p.getIdTiers()==((SWTCommercial) selectedCommercial.getValue()).getIdTiers()) {
							tmp = p;
							trouve = true;
						}
					}
					if(trouve) {
						masterEntity.removeCommercial(tmp);
						masterEntity.addCommercial(taCommercial);
						mapperModelToUI.map(taCommercial,(SWTCommercial) selectedCommercial.getValue());
					}
					tmp = null;
				}

			} else if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)) {
				//mapperUIToModel.map((SWTCommercial) selectedCommercial.getValue(),taCommercial);
				//				taCommercial.setTaTiers(masterEntity);
				masterEntity.addCommercial(taCommercial);				
			}
			
			if(((SWTCommercial) selectedCommercial.getValue()).getDefaut() || masterEntity.getTaCommercial()==null)
				masterEntity.setTaCommercial(taCommercial);			

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

	public TaTiersDAO getDao() {
		return dao;
	}

	public void initEtatComposant() {
		try {
			//vue.getTfCOMMERCIAL().setEditable(!isUtilise());
			changeCouleur(vue);
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
		}
	}

	public boolean isUtilise(){
		return (((SWTCommercial)selectedCommercial.getValue()).getIdTiers()!=null && 
				!dao.recordModifiable(dao.getNomTable(),
						((SWTCommercial)selectedCommercial.getValue()).getIdTiers()))||
						!masterDAO.autoriseModification(masterEntity);		
	}

	public SWTCommercial getSwtOldCommercial() {
		return swtOldCommercial;
	}

	public void setSwtOldTypeCivilite(SWTCommercial swtOldCommercial) {
		this.swtOldCommercial = swtOldCommercial;
	}

	public void setSwtOldCommercial() {
		if (selectedCommercial.getValue() != null)
			this.swtOldCommercial = SWTCommercial.copy((SWTCommercial) selectedCommercial.getValue());
		else {
			if(tableViewer.selectionGrille(0)){
				this.swtOldCommercial = SWTCommercial.copy((SWTCommercial) selectedCommercial.getValue());
				tableViewer.setSelection(new StructuredSelection(
						(SWTCommercial) selectedCommercial.getValue()), true);
			}}
	}

	public void setVue(PaCommercialSWT vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();
		mapComposantDecoratedField.put(vue.getTfCOMMERCIAL(), vue
				.getFieldCOMMERCIAL());
	}

	public Integer getIdTiers() {
		return idTiers;
	}

	public void setIdTiers(Integer idTiers) {
		this.idTiers = idTiers;
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
		if (taCommercial!=null) { //enregistrement en cours de modification/insertion
			idActuel = taCommercial.getIdTiers();
		} else if(selectedCommercial!=null && (SWTCommercial) selectedCommercial.getValue()!=null) {
			idActuel = ((SWTCommercial) selectedCommercial.getValue()).getIdTiers();
		}
		//rafraichissement des valeurs dans la grille
		writableList = new WritableList(realm, IHMmodel(), classModel);
		tableViewer.setInput(writableList);


		if(idActuel!=0 && recherche(Const.C_ID_TIERS, idActuel)!=null) {
			tableViewer.setSelection(new StructuredSelection(recherche(Const.C_ID_TIERS, idActuel)));
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

	public ModelGeneralObjet<SWTCommercial, TaTiersDAO, TaTiers> getModelCommercial() {
		return modelCommercial;
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
					if(!masterEntity.getTaCommerciaux().isEmpty()) {
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

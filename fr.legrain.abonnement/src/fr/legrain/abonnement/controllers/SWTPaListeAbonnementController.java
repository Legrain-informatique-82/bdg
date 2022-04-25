package fr.legrain.abonnement.controllers;

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
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
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
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import fr.legrain.SupportAbon.dao.TaSupportAbon;
import fr.legrain.SupportAbon.dao.TaSupportAbonDAO;
import fr.legrain.abonnement.dao.TaAbonnement;
import fr.legrain.abonnement.dao.TaAbonnementDAO;
import fr.legrain.abonnement.dao.TaRAbonnement;
import fr.legrain.abonnement.divers.ParamAfficheAbonnement;
import fr.legrain.abonnement.ecrans.PalisteAbonnementSWT;
import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.facture.editor.FactureMultiPageEditor;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Module_Tiers.ModelAbonnement;
import fr.legrain.gestCom.Module_Tiers.SWTAbonnement;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.DeclencheCommandeControllerEvent;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.gestCom.librairiesEcran.swt.LibDateTime;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideRechercheSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideRechercheControllerSWT;
import fr.legrain.gestCom.librairiesEcran.workbench.AnnuleToutEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDePageEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDeSelectionEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementMasterEntityEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.IChangementMasterEntityListener;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartUtil;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.InfosVerifSaisie;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
import fr.legrain.lib.data.VerrouInterface;
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
import fr.legrain.licence.divers.MessagesEcran;
import fr.legrain.pointLgr.dao.TaComptePointDAO;

public class SWTPaListeAbonnementController extends JPABaseControllerSWTStandard 
implements RetourEcranListener, ISelectionListener{

	static Logger logger = Logger.getLogger(SWTPaListeAbonnementController.class.getName());	

	private PalisteAbonnementSWT vue = null;
	
	private TaAbonnementDAO dao = null;
	private String idSupportAbon = null;

	private Object ecranAppelant = null;
	private SWTAbonnement swtAbonnement;
	private SWTAbonnement swtOld;
	private Realm realm;
	private DataBindingContext dbc;

	private Class classModel = SWTAbonnement.class;
	private ModelAbonnement modelAbonnement = new ModelAbonnement();	
	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private IObservableValue selectedEpicea;
	private String[] listeChamp;
	private String nomClass = this.getClass().getSimpleName();
	private MapChangeListener changeListener = new MapChangeListener();
	private String nomClassController = this.getClass().getSimpleName();
	private TaSupportAbon masterEntity = null;
	private TaSupportAbonDAO masterDAO = null;
	private List<TaAbonnement> listeEntite=new LinkedList<TaAbonnement>();
	private LgrDozerMapper<SWTAbonnement,TaAbonnement> mapperUIToModel  = new LgrDozerMapper<SWTAbonnement,TaAbonnement>();
	private LgrDozerMapper<TaAbonnement,SWTAbonnement> mapperModelToUI  = new LgrDozerMapper<TaAbonnement,SWTAbonnement>();
	private TaAbonnement taAbonnement = null;

//	private Impression impression = new Impression();

	public SWTPaListeAbonnementController(PalisteAbonnementSWT vue) {
		this(vue,null);
	}

	public SWTPaListeAbonnementController(PalisteAbonnementSWT vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaAbonnementDAO(getEm());
//		daoDocumentTiers = new TaDocumentTiersDAO(getEm());

		setVue(vue);
		vue.getShell().addTraverseListener(new Traverse());
		vue.getTfDateDebAbon().addTraverseListener(new DateTraverse());
		vue.getTfDateDebAbon().addFocusListener(dateFocusListener);
		vue.getTfDateFinAbon().addTraverseListener(new DateTraverse());
		vue.getTfDateFinAbon().addFocusListener(dateFocusListener);		
		initController();
		initSashFormWeight();
		
	}



	private void initController()	{
		try {	
			initSashFormWeight();
			setDaoStandard(dao);

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
			Menu[] tabPopups = new Menu[] {
					popupMenuFormulaire, popupMenuGrille };
			this.initPopupAndButtons(mapActions, tabPopups);
			vue.getPaCorpsFormulaire().setMenu(popupMenuFormulaire);

			initEtatBouton();
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaEpiceaController", e);
		}
	}
	
//	public void bind(PalisteAbonnementSWT paEpiceaSWT){
//		bind(paEpiceaSWT,null);
//	}
	
	public void bindID(PalisteAbonnementSWT paEpiceaSWT, int idAbonnement ){
		SWTAbonnement swtEpiceaTmp = new SWTAbonnement();
		modelAbonnement.mapping(dao.findById(idAbonnement), swtEpiceaTmp);
		bind(paEpiceaSWT, swtEpiceaTmp);
	}
	
	public void bindCode(PalisteAbonnementSWT paEpiceaSWT, String code ){
		SWTAbonnement swtEpiceaTmp = new SWTAbonnement();
		modelAbonnement.mapping(dao.findByCode(code), swtEpiceaTmp);
		bind(paEpiceaSWT, swtEpiceaTmp);
	}
	
	public List<SWTAbonnement> IHMmodel() {
		LinkedList<SWTAbonnement> lswt = new LinkedList<SWTAbonnement>();

		if(masterEntity!=null && masterEntity.getIdSupportAbon()!=0) {
			TaSupportAbonDAO daoSupport = new TaSupportAbonDAO(getEm());
			
			listeEntite= dao.selectAbonnementSupport(masterEntity);

//			LgrDozerMapper<TaAbonnement,SWTAbonnement> mapper = new LgrDozerMapper<TaAbonnement,SWTAbonnement>();
			for (TaAbonnement o : listeEntite) {
				SWTAbonnement t = new SWTAbonnement();
				if(o.getTaArticle()!=null)t.setCodeArticle(o.getTaArticle().getCodeArticle());
				if(o.getTaSupportAbon()!=null)t.setCodeSupportAbon(o.getTaSupportAbon().getCodeSupportAbon());
				if(o.getTaTAbonnement()!=null)t.setCodeTAbonnement(o.getTaTAbonnement().getCodeTAbonnement());
				if(o.getTaTiers()!=null)t.setCodeTiers(o.getTaTiers().getCodeTiers());
				t.setIdAbonnement(o.getIdAbonnement());
				t.setCommentaire(o.getCommentaire());
				t.setDateDebut(o.getDateDebut());
				t.setDateFin(o.getDateFin());
				if(o.getTaLDocument()!=null)t.setCodeDocument(o.getTaLDocument().getTaDocument().getCodeDocument());
//				t = (SWTAbonnement) mapper.map(o, SWTAbonnement.class);
				lswt.add(t);
			}

		}
		return lswt;
	}
	public void bind(PalisteAbonnementSWT palisteAbonnement) {
		try {
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			tableViewer = new LgrTableViewer(palisteAbonnement.getGrille());
			tableViewer.createTableCol(classModel,palisteAbonnement.getGrille(), nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE,0);
			listeChamp = tableViewer.setListeChampGrille(nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE);

			ObservableListContentProvider viewerContent = new ObservableListContentProvider();
			tableViewer.setContentProvider(viewerContent);
			
			// Set up data binding.
			LgrViewerSupport.bind(tableViewer, 
					new WritableList(IHMmodel(), classModel),
					BeanProperties.values(listeChamp)
					);

			selectedEpicea = ViewersObservables
					.observeSingleSelection(tableViewer);

			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			tableViewer.selectionGrille(0);

			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);

			bindingFormMaitreDetail(dbc, realm, selectedEpicea,classModel);
			changementDeSelection();
			selectedEpicea.addChangeListener(new IChangeListener() {

				public void handleChange(ChangeEvent event) {
					changementDeSelection();
				}

			});

		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("", e);
		}
	}
	
	public void bind(PalisteAbonnementSWT paEpiceaSWT, SWTAbonnement selection ){
		bind(paEpiceaSWT);
	}

	private void changementDeSelection() {		
		//		if(swtAbonnement!=null) {
		//			if(swtAbonnement.getIdAbonnement()!=0) {
		//				taAbonnement = dao.findById(swtAbonnement.getIdAbonnement());
		//			}
		//			//null a tester ailleurs, car peut etre null en cas d'insertion
		//			fireChangementDeSelection(new ChangementDeSelectionEvent(SWTPaListeAbonnementController.this));
		//		}
		//		initEtatComposant();
		//		vue.getLaHorsAbon().setVisible(false);
		//		if(swtAbonnement!=null && (swtAbonnement.getDateDebut()==null || swtAbonnement.getDateFin()==null)){
		//			LibDateTime.setDateNull(vue.getTfDateDebAbon());
		//			LibDateTime.setDateNull(vue.getTfDateFinAbon());
		//			vue.getLaHorsAbon().setVisible(true);
		//		}
		//		vue.getTfDocument().setText("");
		//		if(taAbonnement!=null){
		//			if(taAbonnement.getTaLDocument()!=null && taAbonnement.getTaLDocument().getTaDocument()!=null)
		//				vue.getTfDocument().setText(taAbonnement.getTaLDocument().getTaDocument().getCodeDocument());
		//		}

		if(selectedEpicea!=null && selectedEpicea.getValue()!=null) {
			if(((SWTAbonnement) selectedEpicea.getValue()).getIdAbonnement()!=0) {
				taAbonnement = dao.findById(((SWTAbonnement) selectedEpicea.getValue()).getIdAbonnement());
			}
			initEtatComposant();
			vue.getLaHorsAbon().setVisible(false);
			if(taAbonnement!=null && (taAbonnement.getDateDebut()==null || taAbonnement.getDateFin()==null)){
				LibDateTime.setDateNull(vue.getTfDateDebAbon());
				LibDateTime.setDateNull(vue.getTfDateFinAbon());
				vue.getLaHorsAbon().setVisible(true);
			}			
			vue.getTfDocument().setText("");
			if(taAbonnement!=null){
				if(taAbonnement.getTaLDocument()!=null && taAbonnement.getTaLDocument().getTaDocument()!=null)
					vue.getTfDocument().setText(taAbonnement.getTaLDocument().getTaDocument().getCodeDocument());
			}
			//null a tester ailleurs, car peut etre null en cas d'insertion
			fireChangementDeSelection(new ChangementDeSelectionEvent(SWTPaListeAbonnementController.this));
		}
	}
	
	
	public Composite getVue() {return vue;}

//	public ResultAffiche configPanel(ParamAffiche param){
//		Date dateDeb = new Date();
//		if (param!=null){
//			Map<String,String[]> map = dao.getParamWhereSQL();
//			if (param.getFocusDefautSWT()!=null && !param.getFocusDefautSWT().isDisposed())
//				param.getFocusDefautSWT().setFocus();
//
//			if(param.getTitreFormulaire()!=null){
//				vue.getLaTitreFormulaire().setText(param.getTitreFormulaire());
//			} else {
//				vue.getLaTitreFormulaire().setText(ParamAfficheAbonnement.C_TITRE_FORMULAIRE);
//			}
//
//			if(param.getSousTitre()!=null){
//				vue.getLaTitreFenetre().setText(param.getSousTitre());
//			} else {
//				vue.getLaTitreFenetre().setText(ParamAfficheAbonnement.C_SOUS_TITRE);
//			}
//
//			if(param.getEcranAppelant()!=null) {
//				ecranAppelant = param.getEcranAppelant();
//			}
//			EnregistreEtFerme=param.getEnregistreEtFerme();
//			taAbonnement=null;
//			swtAbonnement=null;
//			if(param.getModeEcran()!=null)  {
//				try {
//					if(param.getModeEcran().compareTo(EnumModeObjet.C_MO_INSERTION)==0) {
//						actInserer();
//					} else if (param.getModeEcran().compareTo(EnumModeObjet.C_MO_SUPPRESSION)==0) {
//						actSupprimer();
//					}
//				} catch (Exception e) {
//					if(e!=null)
//						vue.getLaMessage().setText(e.getMessage());
//					logger.error("",e);
//				}
//			}
//
//			if(modelAbonnement!=null && modelAbonnement.getListeEntity()!=null)
//				modelAbonnement.getListeEntity().clear();
//			if(modelAbonnement!=null && modelAbonnement.getListeObjet()!=null)
//			modelAbonnement.getListeObjet().clear();
//			
//			if(param.getSelected()!=null 
//					&& param.getSelected() instanceof SWTAbonnement
//					) {
//				bind(vue,(SWTAbonnement)param.getSelected());
//			} else if (param.getCodeDocument()!=null){
//				bindCode(vue, param.getCodeDocument());
//			}else if (param.getIdDocument()!=null){
//				bindID(vue, param.getIdDocument());
//			}else
//				bind(vue,null);
//
//				VerrouInterface.setVerrouille(false);
//				setSwtOldLicence();
//
//				if(param.getCodeDocument()!=null) {
//					SWTAbonnement licence = modelAbonnement.recherche(Const.C_CODE_ABONNEMENT, param.getCodeDocument());
//					if(licence!=null) {
//						
//					}
//				}
//			
//						
//		}
//		Date dateFin = new Date();
//
//	
//		logger.info("temp config panel "+new Date(dateFin.getTime()-dateDeb.getTime()));
//		return null;
//	}
//	
	
	public ResultAffiche configPanel(ParamAffiche param) {
		Map map=dao.getParamWhereSQL();
		if (param != null) {

				((ParamAfficheAbonnement) param).setFocusDefautSWT(vue
						.getGrille());
			vue.getLaTitreFormulaire().setText(
					((ParamAfficheAbonnement) param).getTitreFormulaire());
			vue.getLaTitreGrille().setText(
					((ParamAfficheAbonnement) param).getTitreGrille());
			vue.getLaTitreFenetre().setText(
					((ParamAfficheAbonnement) param).getSousTitre());

			if (param.getEcranAppelant() != null) {
				ecranAppelant = param.getEcranAppelant();
			}
			if(param.getMasterEntity()!=null){
				masterEntity=(TaSupportAbon) param.getMasterEntity();
			}
			
//			if(tableViewer==null) {
//				//databinding pas encore realis				
				bind(vue);
				tableViewer.tri(classModel, nomClassController,Const.C_FICHIER_LISTE_CHAMP_GRILLE);
//			} else {
//				try {
//					taAbonnement=null;
//					selectedEpicea.setValue(null);
//					actRefresh();
//				} catch (Exception e) {
//					logger.error("",e);
//				}
//			}
			// permet de se positionner sur le 1er enregistrement et de remplir
			// le formulaire
//			tableViewer.tri(classModel, nomClassController,Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			VerrouInterface.setVerrouille(false);
			setSwtOldLicence();

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
		initEtatBouton();
		return null;
	}
	/**
	 * 
	 * @param c
	 * @param show
	 */
	public void showGridLayoutComponent(Control c, boolean show) {

	}
	
	protected void initImageBouton() {
		super.initImageBouton();
		vue.layout(true);
	}
	
	protected void initVerifySaisie() {
		if (mapInfosVerifSaisie == null)
			mapInfosVerifSaisie = new HashMap<Control, InfosVerifSaisie>();
		
		mapInfosVerifSaisie.put(vue.getTfCodeArticle(), new InfosVerifSaisie(new TaArticle(),Const.C_CODE_ARTICLE,null));
//		mapInfosVerifSaisie.put(vue.getTfCodeAbonnement(), new InfosVerifSaisie(new TaAbonnement(),Const.C_CODE_ABONNEMENT,null));
		mapInfosVerifSaisie.put(vue.getTfCommentaire(), new InfosVerifSaisie(new TaAbonnement(),Const.C_COMMENTAIRE,null));
		mapInfosVerifSaisie.put(vue.getTfDateDebAbon(), new InfosVerifSaisie(new TaAbonnement(),Const.C_DATE_DEBUT,null));
		mapInfosVerifSaisie.put(vue.getTfDateFinAbon(), new InfosVerifSaisie(new TaAbonnement(),Const.C_DATE_FIN,null));


		
		initVerifyListener(mapInfosVerifSaisie, dao);
	}

	protected void initComposantsVue() throws ExceptLgr {}

	protected void initEtatBouton() {
		initEtatBoutonCommand();
//			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,false);
//			enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,false);
//			enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID,false);
//			enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID,false);	
	}	

	protected void initMapComposantChamps() {
		if (mapComposantChamps == null) 
			mapComposantChamps = new LinkedHashMap<Control,String>();

		if (listeComposantFocusable == null) 
			listeComposantFocusable = new ArrayList<Control>();



//		mapComposantChamps.put(vue.getTfCodeAbonnement(),Const.C_CODE_ABONNEMENT);
		mapComposantChamps.put(vue.getTfCodeArticle(), Const.C_CODE_ARTICLE);
		mapComposantChamps.put(vue.getTfDateDebAbon(),Const.C_DATE_DEBUT);
		mapComposantChamps.put(vue.getTfDateFinAbon(),Const.C_DATE_FIN);
		mapComposantChamps.put(vue.getTfCommentaire(),Const.C_COMMENTAIRE);
		


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
		

		if(mapInitFocus == null) 
			mapInitFocus = new LinkedHashMap<ModeObjet.EnumModeObjet,Control>();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION,vue.getTfCodeArticle());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION,vue.getTfCodeArticle());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION,vue.getTfCodeArticle());

		activeModifytListener();
		vue.getTfDocument().addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				try {
					String valeurIdentifiant = vue.getTfDocument().getText();
					String idEditor = FactureMultiPageEditor.ID_EDITOR;
					if(!valeurIdentifiant.equals(""))LgrPartUtil.ouvreDocument(valeurIdentifiant,idEditor);
				} catch (Exception e1) {
					logger.error("", e1);
				}
			}

		});

			
			

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

		mapCommand.put(C_COMMAND_GLOBAL_SELECTION_ID, handlerSelection);
		
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


		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID, C_COMMAND_GLOBAL_REFRESH_ID };
		mapActions.put(null, tabActionsAutres);
	}



	public SWTPaListeAbonnementController getThis(){
		return this;
	}

	@Override
	public boolean onClose() throws ExceptLgr {
		boolean retour = true;
		VerrouInterface.setVerrouille(true);
		switch (dao.getModeObjet().getMode()) {
		case C_MO_INSERTION:
		case C_MO_EDITION:
			if(MessageDialog.openQuestion(
					vue.getShell(),
					MessagesEcran.getString("Message.Attention"),
					MessagesEcran.getString("Bdg.Message.Enregistrer"))) {

				try {
					actEnregistrer();
				} catch(Exception e) {
					vue.getLaMessage().setText(e.getMessage());
					logger.error("",e);
					retour= false;
				}
			} else {
				silencieu = true;
				try {
					actAnnuler();
					//fireAnnuleTout(new AnnuleToutEvent(this));
				} catch (Exception e) {
					logger.error("",e);
					vue.getLaMessage().setText(e.getMessage());
				}
			}

			break;
		case C_MO_CONSULTATION:
			break;
		default:
			break;
		}

		if(retour) {
			if (ecranAppelant instanceof SWTPaAideControllerSWT) {
				setListeEntity(getModelAbonnement().remplirListe(getEm()));
				dao.initValeurIdTable(taAbonnement);
				fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
						dao.getChampIdEntite(), dao.getValeurIdTable(),
						selectedEpicea.getValue())));
				retour = true;
			}
		} 
		//		else {
		//			setFocusCourantSWT(ibTaTable.getFModeObjet().getFocusCourantSWT());
		//			getFocusCourantSWT().setFocus();
		//		}

		VerrouInterface.setVerrouille(false);
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
					if(getFocusAvantAideSWT()!=null)setFocusCourantSWT(getFocusAvantAideSWT());
					vue.getLaMessage().setText(e.getMessage());
				}


			}			
			if (getFocusAvantAideSWT() instanceof Table) {

			}
		} else if (evt.getRetour() != null){
			if (getFocusAvantAideSWT() instanceof Table) {

			}
		}
		super.retourEcran(evt);
	}

	@Override
	protected void actInserer() throws Exception{
		try {
			if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
				VerrouInterface.setVerrouille(true);
				setSwtOldLicence();
				taAbonnement = new TaAbonnement();//ne pas déplacer pour que les autres écrans récupère le bon masterEntity
				taAbonnement.setIdAbonnement(0);
				//unBind(dbc);
				swtAbonnement = new SWTAbonnement();
				//bind(vue,swtBdg);
				
//				swtAbonnement.setCodeAbonnement(dao.genereCode());
//				validateUIField(Const.C_CODE_ABONNEMENT,swtAbonnement.getCodeAbonnement());//permet de verrouiller le code genere
				modelAbonnement.mapping(swtAbonnement, taAbonnement);
				dao.inserer(taAbonnement);
				taAbonnement.setIdAbonnement(0);
				TaRAbonnement rAbonnement = new TaRAbonnement();
				rAbonnement.setTaAbonnement(taAbonnement);
				rAbonnement.setTaSupportAbon(masterEntity);
				initEtatComposant();
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

	@Override
	protected void actModifier() throws Exception{
		try {
			if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
				if(!LgrMess.isDOSSIER_EN_RESEAU()){
					setSwtOldLicence();
					taAbonnement = dao.findById(((SWTAbonnement)selectedEpicea.getValue()).getIdAbonnement());				
				}else{
					if(!setSwtOldEpiceaRefresh())throw new Exception();
				}
				dao.modifier(taAbonnement);
				initEtatBouton();
			}
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		}
	}

	@Override
	protected void actSupprimer() throws Exception{
		EntityTransaction transaction = dao.getEntityManager().getTransaction();
		try {
			VerrouInterface.setVerrouille(true);
			if(isUtilise())MessageDialog.openInformation(vue.getShell(), MessagesEcran
					.getString("Message.Attention"), MessagesEcran
					.getString("Message.suppression"));
			else
				if (MessageDialog.openConfirm(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("Bdg.Message.Supprimer"))) {
					dao.begin(transaction);
					TaAbonnement u = dao.findById(((SWTAbonnement)selectedEpicea.getValue()).getIdAbonnement());
					dao.supprimer(u);
					dao.commit(transaction);
					if(containsEntity(u)) modelAbonnement.getListeEntity().remove(u);
					taAbonnement=null;
					dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
					DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(
							this, C_COMMAND_GLOBAL_REFRESH_ID);
					fireDeclencheCommandeController(e);
					changementDeSelection();
				}
		} catch (ExceptLgr e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		}finally {
			if(transaction!=null && transaction.isActive()) {
				transaction.rollback();
			}
			initEtatBouton();
			VerrouInterface.setVerrouille(false);
		}
	}

	@Override
	protected void actFermer() throws Exception{
		//(controles de sortie et fermeture de la fenetre) => onClose()
		if(onClose())
			fireRetourEcran(new RetourEcranEvent(this,null));										
			closeWorkbenchPart();
	}

	@Override
	protected void actAnnuler() throws Exception{
		//		// verifier si l'objet est en modification ou en consultation
		//		// si modification ou insertion, alors demander si annulation des
		//		// modifications si ok, alors annulation si pas ok, alors arreter le processus d'annulation
		//		// si consultation declencher l'action "fermer".
		try {
			VerrouInterface.setVerrouille(true);
			switch (dao.getModeObjet().getMode()) {
			case C_MO_INSERTION:
				closeWorkbenchPart();
				initEtatBouton();

				break;
			case C_MO_EDITION:
				if (silencieu || (!silencieu && MessageDialog.openQuestion(
						vue.getShell(),
						MessagesEcran.getString("Message.Attention"),
						MessagesEcran.getString("Bdg.Message.Annuler")))){

					dao.annuler(taAbonnement);
					changementDeSelection(); //pour réintialiser les autres onglets à partir des données de la bdd
					hideDecoratedFields();
					if(!annuleToutEnCours) {
						fireAnnuleTout(new AnnuleToutEvent(this));
					}
				}
				initEtatBouton();	
				
				//updateView(taLicence, false);

				break;
			case C_MO_CONSULTATION:
				//actionFermer.run();
				break;
			default:
				break;
			}		
		} finally {
			VerrouInterface.setVerrouille(false);
		}
	}

	@Override
	protected void actImprimer() throws Exception{
		
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
	protected boolean aideDisponible() {
		boolean result = false;
		switch ((SWTPaListeAbonnementController.this.dao.getModeObjet().getMode())) {
		case C_MO_CONSULTATION:
		case C_MO_EDITION:
		case C_MO_INSERTION:										

			break;
		default:
			break;
		}
		return result;
	}

	@Override
	protected void actAide() throws Exception{
		actAide(null);
	}

	@Override
	protected void actAide(String message) throws Exception{
		if(aideDisponible()){
			//vue.getDisplay().syncExec(new Runnable(){
			//public void run() {
			try{
				setActiveAide(true);
				VerrouInterface.setVerrouille(true);
				vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
				ParamAfficheAideRechercheSWT paramAfficheAideRecherche = new ParamAfficheAideRechercheSWT();
				//#JPA
				//				paramAfficheAideRecherche.setDb(getThis().getIbTaTable().getFIBBase());
				paramAfficheAideRecherche.setMessage(message);
				//Creation de l'ecran d'aide principal
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


				switch ((SWTPaListeAbonnementController.this.dao.getModeObjet().getMode())) {
				case C_MO_CONSULTATION:
				case C_MO_EDITION:
				case C_MO_INSERTION:										
					//creation d'une recherche
					
			
					
					break;
				default:
					break;
				}

				if (paramAfficheAideRecherche.getJPQLQuery()!=null){


					PaAideRechercheSWT paAideRecherche1 = new PaAideRechercheSWT(s,SWT.NULL);	
					SWTPaAideRechercheControllerSWT paAideRechercheController1 = new SWTPaAideRechercheControllerSWT(paAideRecherche1);

					//Parametrage de la recherche
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

					//Parametrage de l'ecran d'aide principal
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
			mapperUIToModel.map((SWTAbonnement) selectedEpicea.getValue(),taAbonnement);
		}
		return null;
	}

	public IStatus validateUIField(String nomChamp,Object value) {
		String validationContext = "ABONNEMENT";
		boolean changement=false;
		try {
			IStatus s = null;

			 if(nomChamp.equals(Const.C_CODE_ARTICLE)) {
				TaArticleDAO dao = new TaArticleDAO(getEm());

				dao.setModeObjet(getDao().getModeObjet());
				TaArticle f = new TaArticle();
				PropertyUtils.setSimpleProperty(f, Const.C_CODE_ARTICLE, value);
				s = dao.validate(f,Const.C_CODE_ARTICLE,validationContext);

				if(s.getSeverity()!=IStatus.ERROR ){
					f = dao.findByCode((String)value);
					taAbonnement.setTaArticle(f);
				}
				dao = null;
			}else  {
				boolean verrouilleModifCode = false;
				TaAbonnement u = new TaAbonnement();

				PropertyUtils.setSimpleProperty(u, nomChamp, value);
				
				if(((SWTAbonnement)selectedEpicea.getValue())!=null ) {
					u.setIdAbonnement(((SWTAbonnement)selectedEpicea.getValue()).getIdAbonnement());
				}else
					if (((SWTAbonnement)selectedEpicea.getValue())!=null){
						((SWTAbonnement)selectedEpicea.getValue()).setIdAbonnement(0);
					}
				s = dao.validate(u,nomChamp,validationContext,verrouilleModifCode);
				
				if(s.getSeverity()!=Status.ERROR){

					if(nomChamp.equals(Const.C_ID_ABONNEMENT)) {
						PropertyUtils.setSimpleProperty(taAbonnement, nomChamp, value);
					}

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
			try {
				boolean declanchementExterne = false;
				if(sourceDeclencheCommandeController==null) {
					declanchementExterne = true;
				}
				
				boolean inserer = true;
				if ( (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
					inserer = false;
				}
				
				if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
						|| (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {

//					if(declanchementExterne) {
						ctrlTousLesChampsAvantEnregistrementSWT();
//					}

					if(declanchementExterne) {
						modelAbonnement.mapping(((SWTAbonnement)selectedEpicea.getValue()), taAbonnement);
//						mapperUIToModel.map(swtBdg,taLicence);
					}

					fireEnregistreTout(new AnnuleToutEvent(this,true));

					if(!enregistreToutEnCours) {
						dao.begin(transaction);
						if((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)){	
							taAbonnement=dao.enregistrerMerge(taAbonnement);
						}
						else taAbonnement=dao.enregistrerMerge(taAbonnement);

						dao.commit(transaction);
						
						((SWTAbonnement)selectedEpicea.getValue()).setIdAbonnement(taAbonnement.getIdAbonnement());
						// Attention !!!!!le calcul des points bonus ne doit se faire que dans l'enregistrement du réabonnement
//						TaComptePointDAO daoComptePoint=new TaComptePointDAO();
//						daoComptePoint.calculPointTotalAnnee(taAbonnement,false);

						
						fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
								dao.getChampIdEntite(), dao.getValeurIdTable(),
								((SWTAbonnement)selectedEpicea.getValue()))));
						initEtatBouton();
						if(EnregistreEtFerme)actFermer();
					} 
					transaction = null;
				}
			}
			catch (Exception e) {
				logger.error("",e);
				throw new Exception(e);
			}	
		}finally {
			if(transaction!=null && transaction.isActive()) {
				transaction.rollback();
			}
			
		}
	}
	

	

	

	

	



	public boolean isUtilise(){
//		return (selectedEpicea!=null && selectedEpicea.getValue()!=null &&
//				((SWTTiers)selectedEpicea.getValue()).getIdTiers()!=null && 
//				!dao.recordModifiable(dao.getNomTable(),
//						((SWTTiers)selectedEpicea.getValue()).getIdTiers()))||
//						!dao.autoriseModification(taLicence);	
		return (((SWTAbonnement)selectedEpicea.getValue())!=null &&
				((SWTAbonnement)selectedEpicea.getValue()).getIdTiers()!=null && !dao.recordModifiable(dao.getNomTable(),((SWTAbonnement)selectedEpicea.getValue()).getIdTiers()))
				|| !dao.autoriseModification(taAbonnement);
	}



	public void initEtatComposant(){
		vue.getTfCodeArticle().setEditable(false);
		changeCouleur(vue);
	}

	public SWTAbonnement getSwtOld() {
		return swtOld;
	}

	public void setSwtOldLicence(SWTAbonnement swtOldEpicea) {
		this.swtOld = swtOldEpicea;
	}
	public void setSwtOldLicence() {
		if(((SWTAbonnement)selectedEpicea.getValue())!=null) {
			this.swtOld=SWTAbonnement.copy(((SWTAbonnement)selectedEpicea.getValue()));
//		if (selectedEpicea!=null&&selectedEpicea.getValue()!=null){
//			//dao.refresh(dao.findById(((SWTTiers) selectedEpicea.getValue()).getIdTiers()));
//			this.swtOldTiers=SWTTiers.copy((SWTTiers)selectedEpicea.getValue());
//		}
//		else{
//			if (tableViewer.selectionGrille(0)){
//				//dao.refresh(dao.findById(((SWTTiers) selectedEpicea.getValue()).getIdTiers()));
//				this.swtOldTiers=SWTTiers.copy((SWTTiers)selectedEpicea.getValue());
//				tableViewer.setSelection(new StructuredSelection((SWTTiers)selectedEpicea.getValue()),true);
//			}
		}
	}

	//	public void setSwtOldTiersRefresh() {
	//		if (selectedEpicea.getValue()!=null){
	//			if(LgrMess.isDOSSIER_EN_RESEAU())
	//				dao.refresh(dao.findById(((SWTTiers) selectedEpicea.getValue()).getIdTiers()));
	//			taLicence=dao.findById(((SWTTiers) selectedEpicea.getValue()).getIdTiers());
	//			SWTTiers oldSwtTiers=(SWTTiers) selectedEpicea.getValue();
	//			mapperModelToUI.map(taLicence,swtOld);
	//			if(!oldSwtTiers.equals(swtOld)){
	//				try {
	//					actRefresh();
	//				} catch (Exception e) {
	//					logger.error("",e);
	//				}
	//			}
	//			this.swtOldTiers=SWTTiers.copy((SWTTiers)selectedEpicea.getValue());
	//		}
	//	}
	public boolean containsEntity(TaAbonnement entite){
		if(modelAbonnement.getListeEntity()!=null){
			for (Object e : modelAbonnement.getListeEntity()) {
				if(((TaAbonnement)e).getIdAbonnement()==
					entite.getIdAbonnement())return true;
			}
		}
		return false;
	}

	public boolean setSwtOldEpiceaRefresh() {
		try {	
			if (((SWTAbonnement)selectedEpicea.getValue())!=null){
				TaAbonnement taOld =dao.findById(taAbonnement.getIdAbonnement());
				taOld=dao.refresh(taOld);
				taAbonnement=taOld;
				fireChangementMaster(new ChangementMasterEntityEvent(this,taAbonnement,true));
				this.swtOld=SWTAbonnement.copy(((SWTAbonnement)selectedEpicea.getValue()));
			}
			return true;
		} catch (Exception e) {
			return false;
		}		
	}
	public void setVue(PalisteAbonnementSWT vue) {
		super.setVue(vue);
		this.vue = vue;
	}



	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null) 
			mapComposantDecoratedField = new LinkedHashMap<Control,DecoratedField>();
//		mapComposantDecoratedField.put(vue.getTfCodeAbonnement(),vue.getFieldCodeAbonnement());
		mapComposantDecoratedField.put(vue.getTfCodeArticle(),vue.getFieldCodeArticle());
		mapComposantDecoratedField.put(vue.getTfCommentaire(),vue.getFieldCommentaire());
		mapComposantDecoratedField.put(vue.getTfDateDebAbon(),vue.getFieldDateDebAbon());
		mapComposantDecoratedField.put(vue.getTfDateFinAbon(),vue.getFieldDateFinAbon());
	}



	public Class getClassModel() {
		return classModel;
	}



	@Override
	protected void sortieChamps() {


	}

	protected void actSelection() throws Exception {
	
	}
	

	@Override
	protected void actRefresh() throws Exception {
		try{
			vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));		
		//repositionnement sur la valeur courante
		int idActuel = 0;
		if (taAbonnement!=null) { //enregistrement en cours de modification/insertion
			idActuel = taAbonnement.getIdAbonnement();
		} else if(((SWTAbonnement)selectedEpicea.getValue())!=null ) {
			idActuel = ((SWTAbonnement)selectedEpicea.getValue()).getIdAbonnement();
		}

		
		if (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION) == 0) {

		} else {
			if (taAbonnement!=null && ((SWTAbonnement)selectedEpicea.getValue())!=null) {
				mapperModelToUI.map(taAbonnement, ((SWTAbonnement)selectedEpicea.getValue()));
			}
		}

	}finally{
		vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
	}
}

	protected void actRefreshCourant() throws Exception {

			vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));		
		//repositionnement sur la valeur courante
		int idActuel = 0;
		if (taAbonnement!=null) { //enregistrement en cours de modification/insertion
			idActuel = taAbonnement.getIdAbonnement();
		} else if(selectedEpicea!=null && (SWTAbonnement) selectedEpicea.getValue()!=null) {
			idActuel = ((SWTAbonnement) selectedEpicea.getValue()).getIdAbonnement();
		}

		int rang = modelAbonnement.getListeObjet().indexOf(selectedEpicea.getValue());
		mapperModelToUI.map(taAbonnement, swtOld);
		if (rang>0) modelAbonnement.getListeObjet().set(rang, swtOld);
		rang =((WritableList)tableViewer.getInput()).indexOf(swtOld);
		if (rang>0){
			((WritableList)tableViewer.getInput()).set(rang, swtOld);
		}else{
		writableList =new WritableList(realm, modelAbonnement.getListeObjet(),
				classModel);
		tableViewer.setInput(writableList);
		}
		if(idActuel!=0) {
			tableViewer.setSelection(new StructuredSelection(modelAbonnement.recherche(Const.C_ID_SUPPORT_ABON
					, idActuel)),true);
		}else
			tableViewer.selectionGrille(0);				
	}



	public ModelAbonnement getModelAbonnement() {
		return modelAbonnement;
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}



	public TaAbonnementDAO getDao() {
		return dao;
	}



	public SWTAbonnement getSwtBdg() {
		return swtAbonnement;
	}





	public boolean changementPageValide(){		
		return (daoStandard.selectCount()>0);
	}



	public void setDao(TaAbonnementDAO dao) {
		this.dao = dao;
	};



	public void addChangementMasterEntityListener(IChangementMasterEntityListener l) {
		listenerList.add(IChangementMasterEntityListener.class, l);
	}
	
	public void removeChangementMasterEntityListener(IChangementMasterEntityListener l) {
		listenerList.remove(IChangementMasterEntityListener.class, l);
	}

	protected void fireChangementMaster(ChangementMasterEntityEvent e) throws Exception {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == IChangementMasterEntityListener.class) {
				if (e == null)
					e = new ChangementMasterEntityEvent(this);
				( (IChangementMasterEntityListener) listeners[i + 1]).changementMasterEntity(e);
			}
		}
	}



	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if(!vue.isDisposed()) {
			if(selection instanceof IStructuredSelection) {
				if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) != 0)
						&& (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) != 0)) {
					SWTAbonnement t = (SWTAbonnement)((IStructuredSelection)selection).getFirstElement();
					if(t!=null) {
						System.out.println(t.getCodeSupportAbon());
						ParamAffiche paramAffiche = new ParamAffiche();
						//paramAffiche.setIdFiche(String.valueOf(t.getIdTiers()));
						paramAffiche.setSelection(selection);
						configPanel(paramAffiche);
					}
				}
			}
		} else {
//			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().removePostSelectionListener(ListeTiersView.ID, this);
		}
		
	}



} 

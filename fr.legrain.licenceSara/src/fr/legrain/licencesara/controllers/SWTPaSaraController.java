package fr.legrain.licencesara.controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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

import fr.legrain.SupportAbonLegrain.dao.TaUtilisateur;
import fr.legrain.SupportAbonLegrain.dao.TaUtilisateurDAO;
import fr.legrain.abonnement.controllers.SWTPaAbonnementController;
import fr.legrain.abonnement.dao.TaAbonnement;
import fr.legrain.abonnement.dao.TaAbonnementDAO;
import fr.legrain.abonnement.divers.ParamAfficheAbonnement;
import fr.legrain.abonnement.editors.AbonnementMultiPageEditor;
import fr.legrain.abonnement.editors.ListeAbonnementMultiPageEditor;
import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.articles.dao.TaRTSupport;
import fr.legrain.articles.dao.TaRTSupportDAO;
import fr.legrain.articles.ecran.PaArticleSWT;
import fr.legrain.articles.ecran.ParamAfficheArticles;
import fr.legrain.articles.ecran.SWTPaArticlesController;
import fr.legrain.articles.editor.ArticleMultiPageEditor;
import fr.legrain.articles.editor.EditorInputArticle;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Articles.SWTArticle;
import fr.legrain.gestCom.Module_Tiers.ModelSara;
import fr.legrain.gestCom.Module_Tiers.SWTAbonnement;
import fr.legrain.gestCom.Module_Tiers.SWTFamilleTiers;
import fr.legrain.gestCom.Module_Tiers.SWTSupportAbon;
import fr.legrain.gestCom.Module_Tiers.SWTSupportAbonLogiciel;
import fr.legrain.gestCom.Module_Tiers.SWTTiers;
import fr.legrain.gestCom.gestComBd.gestComBdPlugin;
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
import fr.legrain.gestCom.librairiesEcran.workbench.AbstractLgrMultiPageEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.AnnuleToutEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDePageEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDeSelectionEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementMasterEntityEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.IChangementMasterEntityListener;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.InfosVerifSaisie;
import fr.legrain.lib.data.JPABdLgr;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.LibDate;
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
import fr.legrain.libMessageLGR.LgrMess;
import fr.legrain.licenceSara.dao.TaLicenceSara;
import fr.legrain.licenceSara.dao.TaLicenceSaraDAO;
import fr.legrain.licencesara.divers.MessagesEcran;
import fr.legrain.licencesara.divers.ParamAfficheLicenceSara;
import fr.legrain.licencesara.ecrans.PaSaraSWT;
import fr.legrain.pointsbonus.divers.ParamAfficheComptePoint;
import fr.legrain.pointsbonus.editor.comptePointMultiPageEditor;
import fr.legrain.tiers.dao.TaFamilleTiers;
import fr.legrain.tiers.dao.TaFamilleTiersDAO;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.ecran.PaTiersSWT;
import fr.legrain.tiers.ecran.PaTypeFamilleTiers;
import fr.legrain.tiers.ecran.PaTypeFamilleTiersController;
import fr.legrain.tiers.ecran.ParamAfficheFamille;
import fr.legrain.tiers.ecran.ParamAfficheTiers;
import fr.legrain.tiers.ecran.SWTPaTiersController;
import fr.legrain.tiers.editor.EditorFamilleTiers;
import fr.legrain.tiers.editor.EditorInputFamilleTiers;
import fr.legrain.tiers.editor.EditorInputTiers;
import fr.legrain.tiers.editor.TiersMultiPageEditor;

public class SWTPaSaraController extends JPABaseControllerSWTStandard 
implements RetourEcranListener, ISelectionListener{

	static Logger logger = Logger.getLogger(SWTPaSaraController.class.getName());	

	private PaSaraSWT vue = null;
	private TaLicenceSaraDAO dao = null;
	private String idSupportAbon = null;

	private Object ecranAppelant = null;
	private SWTSupportAbonLogiciel swtEpicea;
	private SWTSupportAbonLogiciel swtOldEpicea;
	private Realm realm;
	private DataBindingContext dbc;

	private Class classModel = SWTSupportAbonLogiciel.class;
	private ModelSara modelSara = new ModelSara();	
	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private IObservableValue selectedEpicea;
	private String[] listeChamp;
	private String nomClass = this.getClass().getSimpleName();
	private MapChangeListener changeListener = new MapChangeListener();
	
	private TaTiers masterEntity = null;
	private TaTiersDAO masterDAO = null;

	private LgrDozerMapper<SWTSupportAbonLogiciel,TaLicenceSara> mapperUIToModel  = new LgrDozerMapper<SWTSupportAbonLogiciel,TaLicenceSara>();
	private LgrDozerMapper<TaLicenceSara,SWTSupportAbonLogiciel> mapperModelToUI  = new LgrDozerMapper<TaLicenceSara,SWTSupportAbonLogiciel>();
	private TaLicenceSara taLicence = null;

//	private Impression impression = new Impression();

	public SWTPaSaraController(PaSaraSWT vue) {
		this(vue,null);
	}

	public SWTPaSaraController(PaSaraSWT vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaLicenceSaraDAO(getEm());
//		daoDocumentTiers = new TaDocumentTiersDAO(getEm());

		setVue(vue);
		vue.getShell().addTraverseListener(new Traverse());
		vue.getTfDateAcquisition().addTraverseListener(new DateTraverse());
		vue.getTfDateAcquisition().addFocusListener(dateFocusListener);
		initController();
//		initSashFormWeight();
		
		//PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().addPostSelectionListener(ListeTiersView.ID, this);
	}



	private void initController()	{
		try {	
//			initSashFormWeight();
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
	
	public void bind(PaSaraSWT paEpiceaSWT){
		bind(paEpiceaSWT,null);
	}
	
	public void bindID(PaSaraSWT paEpiceaSWT, int idSupportAbon ){
		SWTSupportAbonLogiciel swtEpiceaTmp = new SWTSupportAbonLogiciel();
		modelSara.mapping(dao.findById(idSupportAbon), swtEpiceaTmp);
		bind(paEpiceaSWT, swtEpiceaTmp);
	}
	
	public void bindCode(PaSaraSWT paEpiceaSWT, String code ){
		SWTSupportAbonLogiciel swtEpiceaTmp = new SWTSupportAbonLogiciel();
		modelSara.mapping(dao.findByCode(code), swtEpiceaTmp);
		bind(paEpiceaSWT, swtEpiceaTmp);
	}
	
	public void bind(PaSaraSWT paEpiceaSWT, SWTSupportAbonLogiciel selection ){
		try {
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());
				dbc = new DataBindingContext(realm);
				if(dao.dataSetEnModif()){
					selection.setCodeSupportAbon(swtEpicea.getCodeSupportAbon());
					swtEpicea = selection;
					modelSara.mapping(selection,taLicence);
				}else{
				if(selection!=null && selection.getIdSupportAbon()!=null) {
					taLicence =  dao.findById(selection.getIdSupportAbon());
					
					/*selection =*/ modelSara.mapping(taLicence, selection);
					swtEpicea = selection;
				} else {
					if(swtEpicea==null) {
						swtEpicea = new SWTSupportAbonLogiciel();
					}
					selection = swtEpicea;
				}
				}


				if(taLicence!=null && taLicence.getTaTiers()!=null)
					vue.getLaCodeTiers().setText("Code tiers : "+taLicence.getTaTiers().getCodeTiers());
				
				dbc.getValidationStatusMap().addMapChangeListener(changeListener);
				setDbcStandard(dbc);

				bindingFormSimple(dbc, realm, selection,classModel);
				
				changementDeSelection();

		} catch(Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("",e);
		}
	}

	private void changementDeSelection() {		
		if(swtEpicea!=null) {
			if(swtEpicea.getIdSupportAbon()!=null) {
				taLicence = dao.findById(swtEpicea.getIdSupportAbon());
			}
			//null a tester ailleurs, car peut etre null en cas d'insertion
			fireChangementDeSelection(new ChangementDeSelectionEvent(SWTPaSaraController.this));
		}
		initEtatComposant();
		initAbonnement();
		vue.getTfDateDebAbon().setEnabled(false);
		vue.getTfDateFinAbon().setEnabled(false);
		if(swtEpicea.getEntreprise()!=null)vue.getTfEntreprise().setText(swtEpicea.getEntreprise());
		if(swtEpicea.getCodeEntite()!=null)vue.getTfCodeEntite().setText(swtEpicea.getCodeEntite());
		if(swtEpicea.getNomTiers()!=null)vue.getTfNomTiers().setText(swtEpicea.getNomTiers());
		if(swtEpicea.getPrenomTiers()!=null)vue.getTfPrenomTiers().setText(swtEpicea.getPrenomTiers());
	}
	
	
	public Composite getVue() {return vue;}

	public ResultAffiche configPanel(ParamAffiche param){
		Date dateDeb = new Date();
		if (param!=null){
			Map<String,String[]> map = dao.getParamWhereSQL();
			if (param.getFocusDefautSWT()!=null && !param.getFocusDefautSWT().isDisposed())
				param.getFocusDefautSWT().setFocus();

			if(param.getTitreFormulaire()!=null){
				vue.getLaTitreFormulaire().setText(param.getTitreFormulaire());
			} else {
				vue.getLaTitreFormulaire().setText(ParamAfficheLicenceSara.C_TITRE_FORMULAIRE);
			}

			if(param.getSousTitre()!=null){
				vue.getLaTitreFenetre().setText(param.getSousTitre());
			} else {
				vue.getLaTitreFenetre().setText(ParamAfficheLicenceSara.C_SOUS_TITRE);
			}

			if(param.getEcranAppelant()!=null) {
				ecranAppelant = param.getEcranAppelant();
			}
			taLicence=null;
			swtEpicea=null;
			if(param.getModeEcran()!=null)  {
				try {
					if(param.getModeEcran().compareTo(EnumModeObjet.C_MO_INSERTION)==0) {
						actInserer();
					} else if (param.getModeEcran().compareTo(EnumModeObjet.C_MO_SUPPRESSION)==0) {
						actSupprimer();
					}
				} catch (Exception e) {
					if(e!=null)
						vue.getLaMessage().setText(e.getMessage());
					logger.error("",e);
				}
			}

			if(modelSara!=null && modelSara.getListeEntity()!=null)
				modelSara.getListeEntity().clear();
			if(modelSara!=null && modelSara.getListeObjet()!=null)
			modelSara.getListeObjet().clear();
			
			if(param.getSelected()!=null 
					&& param.getSelected() instanceof SWTSupportAbon
					) {
				bind(vue,(SWTSupportAbonLogiciel)param.getSelected());
			} else if (param.getCodeDocument()!=null){
				bindCode(vue, param.getCodeDocument());
			}else if (param.getIdDocument()!=null){
				bindID(vue, param.getIdDocument());
			}else
				bind(vue,null);

				VerrouInterface.setVerrouille(false);
				setSwtOldLicence();

				if(param.getCodeDocument()!=null) {
					SWTSupportAbonLogiciel licence = modelSara.recherche(Const.C_CODE_SUPPORT_ABON, param.getCodeDocument());
					if(licence!=null) {
						
					}
				}
			
						
		}
		Date dateFin = new Date();

	
		logger.info("temp config panel "+new Date(dateFin.getTime()-dateDeb.getTime()));
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
		mapInfosVerifSaisie.put(vue.getTfCodeSupport(), new InfosVerifSaisie(new TaLicenceSara(),Const.C_CODE_SUPPORT_ABON,null));
		mapInfosVerifSaisie.put(vue.getTfCommentaire(), new InfosVerifSaisie(new TaLicenceSara(),Const.C_COMMENTAIRE,null));
		mapInfosVerifSaisie.put(vue.getTfCommercial(), new InfosVerifSaisie(new TaTiers(),Const.C_CODE_TIERS,null));
		mapInfosVerifSaisie.put(vue.getTfDateAcquisition(), new InfosVerifSaisie(new TaLicenceSara(),Const.C_DATE_ACQUISITION,null));
//		mapInfosVerifSaisie.put(vue.getTfDateDebAbon(), new InfosVerifSaisie(new TaLicenceSara(),Const.C_DATE_DEB_ABON,null));
//		mapInfosVerifSaisie.put(vue.getTfDateFinAbon(), new InfosVerifSaisie(new TaLicenceSara(),Const.C_DATE_FIN_ABON,null));
		mapInfosVerifSaisie.put(vue.getTfEmailUtilisateur(), new InfosVerifSaisie(new TaUtilisateur(),Const.C_EMAIL,null));
		mapInfosVerifSaisie.put(vue.getTfGroupe(), new InfosVerifSaisie(new TaFamilleTiers(),Const.C_CODE_FAMILLE,null));
		mapInfosVerifSaisie.put(vue.getTfNomUtilisateur(), new InfosVerifSaisie(new TaUtilisateur(),Const.C_NOM,null));
		mapInfosVerifSaisie.put(vue.getTfPrenomUtilisateur(), new InfosVerifSaisie(new TaUtilisateur(),Const.C_PRENOM,null));
		mapInfosVerifSaisie.put(vue.getTfTelUtilisateur(), new InfosVerifSaisie(new TaUtilisateur(),Const.C_TEL,null));

		
		initVerifyListener(mapInfosVerifSaisie, dao);
	}

	protected void initComposantsVue() throws ExceptLgr {}

	protected void initEtatBouton() {
		initEtatBoutonCommand();
		
		switch (daoStandard.getModeObjet().getMode()) {
		case C_MO_INSERTION:

			break;
		case C_MO_EDITION:

			break;
		case C_MO_CONSULTATION:

			break;
		default:
			break;
		}
	
	}	

	protected void initMapComposantChamps() {
		if (mapComposantChamps == null) 
			mapComposantChamps = new LinkedHashMap<Control,String>();

		if (listeComposantFocusable == null) 
			listeComposantFocusable = new ArrayList<Control>();



		mapComposantChamps.put(vue.getTfCodeSupport(),Const.C_CODE_SUPPORT_ABON);
		mapComposantChamps.put(vue.getTfCodeArticle(), Const.C_CODE_ARTICLE);
		mapComposantChamps.put(vue.getTfDateAcquisition(), Const.C_DATE_ACQUISITION);
		mapComposantChamps.put(vue.getBtnTelechargement(),Const.C_TELECHARGEMENT);
		mapComposantChamps.put(vue.getTfNomUtilisateur(), Const.C_NOM);
		mapComposantChamps.put(vue.getTfPrenomUtilisateur(), Const.C_PRENOM);
		mapComposantChamps.put(vue.getTfTelUtilisateur(),Const.C_TEL);
		mapComposantChamps.put(vue.getTfEmailUtilisateur(),Const.C_EMAIL);
		mapComposantChamps.put(vue.getTfGroupe(),Const.C_CODE_FAMILLE);
		mapComposantChamps.put(vue.getTfCommercial(),Const.C_COMMERCIAL);
		mapComposantChamps.put(vue.getTfCommentaire(),Const.C_COMMENTAIRE);
		mapComposantChamps.put(vue.getTfDateDebAbon(),Const.C_DATE_DEB_ABON);
		mapComposantChamps.put(vue.getTfDateFinAbon(),Const.C_DATE_FIN_ABON);
		mapComposantChamps.put(vue.getBtnInActif(),Const.C_INACTIF);
		


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
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION,vue.getTfCodeSupport());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION,vue.getTfCodeSupport());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION,vue.getTfCodeSupport());

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
		
		vue.getBtnAbonnement().addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if(taLicence.getTaAbonnements().size()>0)actAfficheAbonnement();
				else actCreerAbonnement(taLicence,false);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				widgetSelected(e);
			}
			
		});
		vue.getBtnComptePoint().addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				actAfficheComptePoint();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				widgetSelected(e);
			}
			
		});
		vue.getBtnListeAbonnements().addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				actAfficheListeAbonnements();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				widgetSelected(e);
			}
			
		});
		vue.getBtnTiers().addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					actSelectionTiers();
				} catch (Exception e1) {
					logger.error("", e1);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				widgetSelected(e);
			}
			
		});		
		vue.getTfCodeArticle().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				actAfficheArticle();
			}
		}); 	
	}

	protected void actSelectionTiers() throws Exception {
		try{
			if(taLicence!=null && taLicence.getTaTiers()!=null &&
					taLicence.getTaTiers().getIdTiers()!=0){
				ouvreFiche(String.valueOf(taLicence.getTaTiers().getIdTiers()), TiersMultiPageEditor.ID_EDITOR,null,false);
				
			}
		}catch (Exception e) {
			logger.error("",e);
		}	
	}

	public SWTPaSaraController getThis(){
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
					MessagesEcran.getString("Sara.Message.Enregistrer"))) {

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
				setListeEntity(getModelSara().remplirListe(getEm()));
				dao.initValeurIdTable(taLicence);
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
					if(getFocusAvantAideSWT().equals(vue.getTfCommercial())){
						TaTiers u = null;
						TaTiersDAO t = new TaTiersDAO(getEm());
						u = t.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
						t = null;
						taLicence.setCommercial(u);
					}	
					if(getFocusAvantAideSWT().equals(vue.getTfGroupe())){
						TaFamilleTiers u = null;
						TaFamilleTiersDAO t = new TaFamilleTiersDAO(getEm());
						u = t.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
						t = null;
						taLicence.setGroupe(u);
					}	
				} catch (Exception e) {
					logger.error("",e);
					if(getFocusAvantAideSWT()!=null)setFocusCourantSWT(getFocusAvantAideSWT());
					vue.getLaMessage().setText(e.getMessage());
				}


			}			
			if (getFocusAvantAideSWT() instanceof Table) {

			}
		} else if (evt.getRetour() != null){
			if (evt.getSource() instanceof SWTPaAbonnementController) {
				try {
					//dao=new TaLicenceEpiceaDAO();
					if(taLicence!=null)taLicence=dao.refresh(taLicence);
					actRefresh();
					changementDeSelection();
					VerrouInterface.setVerrouille(false);
				} catch (Exception e) {
					logger.error("",e);
				}
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
				taLicence = new TaLicenceSara();//ne pas déplacer pour que les autres écrans récupère le bon masterEntity
				taLicence.setIdSupportAbon(0);
				
				//unBind(dbc);
				swtEpicea = new SWTSupportAbonLogiciel();
				//bind(vue,swtEpicea);
				
				swtEpicea.setCodeSupportAbon(dao.genereCodeJPA());
				validateUIField(Const.C_CODE_SUPPORT_ABON,swtEpicea.getCodeSupportAbon());//permet de verrouiller le code genere
				modelSara.mapping(swtEpicea, taLicence);
				dao.inserer(taLicence);
				taLicence.setIdSupportAbon(0);
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
					taLicence = dao.findById(swtEpicea.getIdSupportAbon());				
				}else{
					if(!setSwtOldEpiceaRefresh())throw new Exception();
				}
				dao.modifier(taLicence);
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
						.getString("Sara.Message.Supprimer"))) {
					dao.begin(transaction);
					TaLicenceSara u = dao.findById(swtEpicea.getIdSupportAbon());
					dao.supprimer(u);
					dao.commit(transaction);
					if(containsEntity(u)) modelSara.getListeEntity().remove(u);
					taLicence=null;
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
						fr.legrain.licencesara.divers.MessagesEcran.getString("Sara.Message.Annuler")))){

					dao.annuler(taLicence);
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
		switch ((SWTPaSaraController.this.dao.getModeObjet().getMode())) {
		case C_MO_CONSULTATION:
		case C_MO_EDITION:
		case C_MO_INSERTION:										
			//creation d'une recherche
			if(getFocusCourantSWT().equals(vue.getTfCommercial())
					|| getFocusCourantSWT().equals(vue.getTfGroupe())|| getFocusCourantSWT().equals(vue.getTfCodeArticle()))
				result = true;
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


				switch ((SWTPaSaraController.this.dao.getModeObjet().getMode())) {
				case C_MO_CONSULTATION:
				case C_MO_EDITION:
				case C_MO_INSERTION:										
					//creation d'une recherche
					if(getFocusCourantSWT().equals(vue.getTfCommercial())){
						TaTiersDAO daoTiers = new TaTiersDAO(getEm());
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
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCommercial().getText());
						paramAfficheAideRecherche.setControllerAppelant(getThis());
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTiers,TaTiersDAO,TaTiers>(daoTiers.rechercheParType("COM"),SWTTiers.class,dao.getEntityManager()));
						paramAfficheAideRecherche.setTypeObjet(SWTTiers.class);

						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_TIERS);
					}
					if(getFocusCourantSWT().equals(vue.getTfGroupe())){
						PaTypeFamilleTiers paFamilleSWT = new PaTypeFamilleTiers(s2,SWT.NULL); 
						PaTypeFamilleTiersController swtPaFamilleController = new PaTypeFamilleTiersController(paFamilleSWT);

						editorCreationId = EditorFamilleTiers.ID;
						editorInputCreation = new EditorInputFamilleTiers();

						ParamAfficheFamille paramAfficheFamille = new ParamAfficheFamille();
						paramAfficheAideRecherche.setJPQLQuery(new TaFamilleTiersDAO(getEm()).getJPQLQuery());
						paramAfficheFamille.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheFamille.setEcranAppelant(paAideController);
						/* 
						 * controllerEcranCreation ne sert plus a rien, pour l'editeur de creation, on creer un nouveau controller
						 */
						paramAfficheAideRecherche.setAfficheDetail(false);
						paramAfficheAideRecherche.setForceAffichageAideRemplie(true);
						
						controllerEcranCreation = swtPaFamilleController;
						parametreEcranCreation = paramAfficheFamille;

						paramAfficheAideRecherche.setTypeEntite(TaFamilleTiers.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_FAMILLE);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfGroupe().getText());
						paramAfficheAideRecherche.setControllerAppelant(SWTPaSaraController.this);
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTFamilleTiers,
								TaFamilleTiersDAO,TaFamilleTiers>(SWTFamilleTiers.class,dao.getEntityManager()));
						paramAfficheAideRecherche.setTypeObjet(SWTFamilleTiers.class);

						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_FAMILLE);
					}
					
					if (getFocusCourantSWT()==vue.getTfCodeArticle()){
						PaArticleSWT paArticleSWT = new PaArticleSWT(s2,SWT.NULL);
						SWTPaArticlesController swtPaArticlesController = new SWTPaArticlesController(paArticleSWT);
						paramAfficheAideRecherche.setForceAffichageAideRemplie(false);

						editorCreationId = ArticleMultiPageEditor.ID_EDITOR;
						editorInputCreation = new EditorInputArticle();

						ParamAfficheArticles paramAfficheArticles = new ParamAfficheArticles();
						paramAfficheAideRecherche.setJPQLQuery(new TaArticleDAO(getEm()).getJPQLQuery());
						paramAfficheArticles.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheArticles.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaArticlesController;
						parametreEcranCreation = paramAfficheArticles;

						paramAfficheAideRecherche.setTypeEntite(TaArticle.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_ARTICLE);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCodeArticle().getText());
						paramAfficheAideRecherche.setControllerAppelant(getThis());
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTArticle,TaArticleDAO,TaArticle>(SWTArticle.class,getEm()));
						paramAfficheAideRecherche.setTypeObjet(swtPaArticlesController.getClassModel());
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_ARTICLE);
					}
					
					
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
			mapperUIToModel.map((SWTSupportAbonLogiciel) selectedEpicea.getValue(),taLicence);
		}
		return null;
	}

	public IStatus validateUIField(String nomChamp,Object value) {
		String validationContext = "SUPPORT_ABON";
		boolean changement=false;
		try {
			IStatus s = null;

			if(nomChamp.equals(Const.C_COMMERCIAL)) {
				TaTiersDAO dao = new TaTiersDAO(getEm());
				
				dao.setModeObjet(getDao().getModeObjet());
				TaTiers f = new TaTiers();
				PropertyUtils.setSimpleProperty(f, Const.C_CODE_TIERS, value);
				s = dao.validate(f,Const.C_CODE_TIERS,validationContext);

				if(s.getSeverity()!=IStatus.ERROR ){
					f = dao.findByCode((String)value);
					taLicence.setTaTiers(f);
				}
				dao = null;
			}else if(nomChamp.equals(Const.C_CODE_ARTICLE)) {
				TaArticleDAO dao = new TaArticleDAO(getEm());

				dao.setModeObjet(getDao().getModeObjet());
				TaArticle f = new TaArticle();
				PropertyUtils.setSimpleProperty(f, Const.C_CODE_ARTICLE, value);
				s = dao.validate(f,Const.C_CODE_ARTICLE,validationContext);

				if(s.getSeverity()!=IStatus.ERROR ){
					f = dao.findByCode((String)value);
					taLicence.setTaArticle(f);
				}
				dao = null;
			}else if(nomChamp.equals(Const.C_CODE_FAMILLE)) {
				TaFamilleTiersDAO dao = new TaFamilleTiersDAO(getEm());

				dao.setModeObjet(getDao().getModeObjet());
				TaFamilleTiers f = new TaFamilleTiers();
				PropertyUtils.setSimpleProperty(f, Const.C_CODE_FAMILLE, value);
				s = dao.validate(f,Const.C_CODE_FAMILLE,validationContext);

				if(s.getSeverity()!=IStatus.ERROR ){
					f = dao.findByCode((String)value);
					taLicence.setGroupe(f);
				}
				dao = null;
			}else if(nomChamp.equals(Const.C_NOM)) {
				TaUtilisateurDAO dao = new TaUtilisateurDAO(getEm());

				dao.setModeObjet(getDao().getModeObjet());
				TaUtilisateur f = new TaUtilisateur();
				f.setIdUtilisateur(0);
				PropertyUtils.setSimpleProperty(f, nomChamp, value);
				s = dao.validate(f,nomChamp,validationContext);

				if(s.getSeverity()!=IStatus.ERROR ){

				}
				dao = null;
			} else if(nomChamp.equals(Const.C_PRENOM)||nomChamp.equals(Const.C_EMAIL)
					||nomChamp.equals(Const.C_TEL)||nomChamp.equals(Const.C_COMMENTAIRE)||nomChamp.equals(Const.C_DATE_DEB_ABON)||nomChamp.equals(Const.C_DATE_FIN_ABON)) {
				s=new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
			}else {
				boolean verrouilleModifCode = false;
				TaLicenceSara u = new TaLicenceSara();

				if(nomChamp.equals(Const.C_INACTIF)){
					if((Boolean)value==true) value=new Integer(1); else value=new Integer(0);					
				}
				if(nomChamp.equals(Const.C_TELECHARGEMENT)){
					if((Boolean)value==true) value=new Integer(1); else value=new Integer(0);					
				}

				PropertyUtils.setSimpleProperty(u, nomChamp, value);
				
				if(swtEpicea!=null && swtEpicea.getIdSupportAbon()!=null) {
					u.setIdSupportAbon(swtEpicea.getIdSupportAbon());
				}
				if(u.getIdSupportAbon()==null)u.setIdSupportAbon(0);
				s = dao.validate(u,nomChamp,validationContext,verrouilleModifCode);
				
				if(s.getSeverity()!=Status.ERROR){

					if(nomChamp.equals(Const.C_ID_SUPPORT_ABON)) {
						PropertyUtils.setSimpleProperty(taLicence, nomChamp, value);
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

					if(declanchementExterne) {
						ctrlTousLesChampsAvantEnregistrementSWT();
					}

					if(declanchementExterne) {
						modelSara.mapping(swtEpicea, taLicence);
//						mapperUIToModel.map(swtEpicea,taLicence);
					}

					if(taLicence.getVersionObj()==null)taLicence.setVersionObj(0);

					
					fireEnregistreTout(new AnnuleToutEvent(this,true));

					if(!enregistreToutEnCours) {
						dao.begin(transaction);
						if((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)){	
							taLicence=dao.enregistrerMerge(taLicence);
						}
						else taLicence=dao.enregistrerMerge(taLicence);

						dao.commit(transaction);
						
						swtEpicea.setIdSupportAbon(taLicence.getIdSupportAbon());
						//updateView(taLicence,inserer);
						DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(
								this, C_COMMAND_GLOBAL_REFRESH_ID);
						fireDeclencheCommandeController(e);
						initEtatBouton();
						actCreerAbonnement(taLicence,true);
						actFermer();
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
			initEtatBouton();
		}
	}
	

	

	

	

	



	public boolean isUtilise(){
//		return (selectedEpicea!=null && selectedEpicea.getValue()!=null &&
//				((SWTTiers)selectedEpicea.getValue()).getIdTiers()!=null && 
//				!dao.recordModifiable(dao.getNomTable(),
//						((SWTTiers)selectedEpicea.getValue()).getIdTiers()))||
//						!dao.autoriseModification(taLicence);	
		return (swtEpicea!=null &&
				swtEpicea.getIdTiers()!=null && !dao.recordModifiable(dao.getNomTable(),swtEpicea.getIdTiers()))
				|| !dao.autoriseModification(taLicence);
	}



	public void initEtatComposant(){
		vue.getTfCodeSupport().setEditable(false);
//		vue.getTfCodeArticle().setEditable(false);
		vue.getGroupAbonnement().setVisible(!dao.getModeObjet().getMode().equals(EnumModeObjet.C_MO_INSERTION));
		changeCouleur(vue);
	}

	public SWTSupportAbonLogiciel getSwtOldEpicea() {
		return swtOldEpicea;
	}

	public void setSwtOldLicence(SWTSupportAbonLogiciel swtOldEpicea) {
		this.swtOldEpicea = swtOldEpicea;
	}
	public void setSwtOldLicence() {
		if(swtEpicea!=null) {
			this.swtOldEpicea=SWTSupportAbonLogiciel.copy(swtEpicea);
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
	//			mapperModelToUI.map(taLicence,swtOldEpicea);
	//			if(!oldSwtTiers.equals(swtOldEpicea)){
	//				try {
	//					actRefresh();
	//				} catch (Exception e) {
	//					logger.error("",e);
	//				}
	//			}
	//			this.swtOldTiers=SWTTiers.copy((SWTTiers)selectedEpicea.getValue());
	//		}
	//	}
	public boolean containsEntity(TaLicenceSara entite){
		if(modelSara.getListeEntity()!=null){
			for (Object e : modelSara.getListeEntity()) {
				if(((TaLicenceSara)e).getIdSupportAbon()==
					entite.getIdSupportAbon())return true;
			}
		}
		return false;
	}

	public boolean setSwtOldEpiceaRefresh() {
		try {	
			if (swtEpicea!=null){
				TaLicenceSara taOld =dao.findById(taLicence.getIdSupportAbon());
				taOld=dao.refresh(taOld);
				taLicence=taOld;
				fireChangementMaster(new ChangementMasterEntityEvent(this,taLicence,true));
				this.swtOldEpicea=SWTSupportAbonLogiciel.copy(swtEpicea);
			}
			return true;
		} catch (Exception e) {
			return false;
		}		
	}
	public void setVue(PaSaraSWT vue) {
		super.setVue(vue);
		this.vue = vue;
	}



	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null) 
			mapComposantDecoratedField = new LinkedHashMap<Control,DecoratedField>();
		mapComposantDecoratedField.put(vue.getTfCodeSupport(),vue.getFieldCodeSupport());
		mapComposantDecoratedField.put(vue.getTfCodeArticle(),vue.getFieldCodeArticle());
		mapComposantDecoratedField.put(vue.getTfCommentaire(),vue.getFieldCommentaire());
		mapComposantDecoratedField.put(vue.getTfCommercial(),vue.getFieldCommercial());
		mapComposantDecoratedField.put(vue.getTfDateAcquisition(),vue.getFieldDateAcquisition());
		mapComposantDecoratedField.put(vue.getTfDateDebAbon(),vue.getFieldDateDebAbon());
		mapComposantDecoratedField.put(vue.getTfDateFinAbon(),vue.getFieldDateFinAbon());
		mapComposantDecoratedField.put(vue.getTfEmailUtilisateur(), vue.getFieldEmailUtilisateur());
		mapComposantDecoratedField.put(vue.getTfGroupe(), vue.getFieldGroupe());
		mapComposantDecoratedField.put(vue.getTfNomUtilisateur(),vue.getFieldNomUtilisateur());
		mapComposantDecoratedField.put(vue.getTfPrenomUtilisateur(), vue.getFieldPrenomUtilisateur());
		mapComposantDecoratedField.put(vue.getTfTelUtilisateur(), vue.getFieldTelUtilisateur());
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
		if (taLicence!=null) { //enregistrement en cours de modification/insertion
			idActuel = taLicence.getIdSupportAbon();
		} else if(swtEpicea!=null && swtEpicea.getIdSupportAbon()!=null) {
			idActuel = swtEpicea.getIdSupportAbon();
		}
			if (taLicence!=null && swtEpicea!=null) {
				modelSara.mapping(taLicence, swtEpicea);
			}
			initAbonnement();
	}finally{
		vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
	}
}
	public void initAbonnement(){
		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO();
		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
		vue.getLaHorsAbon().setVisible(false);
		vue.getLaHorsAbon().setText("Hors abonnement !!!");
		if(swtEpicea.getDateDebAbon()==null || swtEpicea.getDateFinAbon()==null ){
			LibDateTime.setDateNull(vue.getTfDateDebAbon());
			LibDateTime.setDateNull(vue.getTfDateFinAbon());
			vue.getLaHorsAbon().setVisible(true);
		}else if(swtEpicea.getDateFinAbon().before(taInfoEntreprise.getDatedebInfoEntreprise())||
				swtEpicea.getDateDebAbon().after(taInfoEntreprise.getDatefinInfoEntreprise())){
			vue.getLaHorsAbon().setVisible(true);
		}
		if(vue.getBtnInActif().getSelection()){
			vue.getLaHorsAbon().setText("N'utilise plus !!!");
		}
	}
	protected void actRefreshCourant() throws Exception {

			vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));		
		//repositionnement sur la valeur courante
		int idActuel = 0;
		if (taLicence!=null) { //enregistrement en cours de modification/insertion
			idActuel = taLicence.getIdSupportAbon();
		} else if(selectedEpicea!=null && (SWTSupportAbonLogiciel) selectedEpicea.getValue()!=null) {
			idActuel = ((SWTSupportAbonLogiciel) selectedEpicea.getValue()).getIdSupportAbon();
		}

		int rang = modelSara.getListeObjet().indexOf(selectedEpicea.getValue());
		mapperModelToUI.map(taLicence, swtOldEpicea);
		if (rang>0) modelSara.getListeObjet().set(rang, swtOldEpicea);
		rang =((WritableList)tableViewer.getInput()).indexOf(swtOldEpicea);
		if (rang>0){
			((WritableList)tableViewer.getInput()).set(rang, swtOldEpicea);
		}else{
		writableList =new WritableList(realm, modelSara.getListeObjet(),
				classModel);
		tableViewer.setInput(writableList);
		}
		if(idActuel!=0) {
			tableViewer.setSelection(new StructuredSelection(modelSara.recherche(Const.C_ID_SUPPORT_ABON
					, idActuel)),true);
		}else
			tableViewer.selectionGrille(0);				
	}



	public ModelSara getModelSara() {
		return modelSara;
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}



	public TaLicenceSaraDAO getDao() {
		return dao;
	}



	public SWTSupportAbonLogiciel getSwtEpicea() {
		return swtEpicea;
	}



	public TaLicenceSara getTaLicence() {
		return taLicence;
	}

	public boolean changementPageValide(){		
		return (daoStandard.selectCount()>0);
	}



	public void setDao(TaLicenceSaraDAO dao) {
		this.dao = dao;
	};

	private boolean utilisateurEstRempli() {
		boolean retour=false;
		if(!vue.getTfNomUtilisateur().getText().equals(""))return true;
		if(!vue.getTfPrenomUtilisateur().getText().equals(""))return true;
		if(!vue.getTfTelUtilisateur().getText().equals(""))return true;
		if(!vue.getTfEmailUtilisateur().getText().equals(""))return true;
		return retour;
	}

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


	public void testJPQL(){

		//		String jpql = "select tbl from TaBonliv tbl "+
		//		   "where "+
		//		   "not exists "+ 
		//		   "(select trd.taBonliv.idDocument from TaRDocument trd where trd.taBonliv.idDocument=tbl.idDocument) "+
		//		   "or exists "+
		//		   "(select trd.taBonliv.idDocument from TaRDocument trd where trd.taBonliv.idDocument=tbl.idDocument and trd.taFacture is null)"+
		//		   "and "+
		//		   "tbl.dateDocument "+
		//		   "between "+
		//		   "(select tie.datedebInfoEntreprise from TaInfoEntreprise tie) "+
		//		   "and "+
		//		   "(select tie.datefinInfoEntreprise from TaInfoEntreprise tie) "+
		//		   "and tbl.taTiers.idTiers=1";
		String jpql = "select tbl from TaBonliv tbl "+
		"where tbl.taTiers.idTiers=1 "+
		"and " +
		"tbl.dateDocument " +
		"between " +
		"(select tie.datedebInfoEntreprise from TaInfoEntreprise tie) " +
		"and " +
		"(select tie.datefinInfoEntreprise from TaInfoEntreprise tie) " +
		"and " +
		"not exists " +
		"(select trd.taBonliv.idDocument from TaRDocument trd where trd.taBonliv.idDocument=tbl.idDocument) " +
		"or " +
		"exists " +
		"(select trd.taBonliv.idDocument from TaRDocument trd where trd.taBonliv.idDocument=tbl.idDocument and trd.taFacture is null)";

		jpql = "select tb from TaBoncde tb where tb.idDocument not in (select trd.taBoncde.idDocument from "+ 
		"TaRDocument trd where trd.taBoncde.idDocument is not null and trd.taFacture.idDocument is not null)";
		
		jpql = "select tlBoncmd from TaLBoncde tlBoncmd where tlBoncmd.taDocument.idDocument not in (select trd.taBoncde.idDocument from "+ 
		"TaRDocument trd where trd.taBoncde.idDocument is not null and trd.taFacture.idDocument is not null)";
		
		JPABdLgr bdLgr = new JPABdLgr();
		
		Query query = bdLgr.getEntityManager().createQuery(jpql);
		for (int i = 0; i < query.getResultList().size(); i++) {

			System.out.println(query.getResultList().size());
		}

	}
	public void actAfficheListeAbonnements( ){
	    final ParamAffiche param = new ParamAfficheAbonnement();
		param.setModeEcran(EnumModeObjet.C_MO_CONSULTATION);//selectDernierAbonnement
		param.setMasterEntity(taLicence);
		param.setMasterDao(dao);
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() { 			
				IEditorPart e=AbstractLgrMultiPageEditor.ouvreFiche("","", ListeAbonnementMultiPageEditor.ID_EDITOR,param,false);
			((AbstractLgrMultiPageEditor)e).findMasterController().addRetourEcranListener(getThis());
			}});

	}
	public void actAfficheArticle( ){
	    final ParamAffiche param = new ParamAfficheArticles();
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() { 
				changementDeSelection();
				IEditorPart e=AbstractLgrMultiPageEditor.ouvreFiche(LibConversion.integerToString(taLicence.getTaArticle().getIdArticle()),"", ArticleMultiPageEditor.ID_EDITOR,param,false);
			((AbstractLgrMultiPageEditor)e).findMasterController().addRetourEcranListener(getThis());
			}});

	}
	public void actAfficheTiers( ){
	    final ParamAffiche param = new ParamAfficheAbonnement();
		param.setModeEcran(EnumModeObjet.C_MO_CONSULTATION);//selectDernierAbonnement
		param.setIdDocument(taLicence.getTaTiers().getIdTiers());
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() { 			
				IEditorPart e=AbstractLgrMultiPageEditor.ouvreFiche("","", TiersMultiPageEditor.ID_EDITOR,param,false);
			((AbstractLgrMultiPageEditor)e).findMasterController().addRetourEcranListener(getThis());
			}});

	}
	public void actCreerAbonnement(TaLicenceSara taLicence, final Boolean fermer ){
		try{
			final ParamAffiche param = new ParamAfficheAbonnement();
			SWTAbonnement swtAbonnement = new SWTAbonnement();
			if(taLicence.getTaAbonnements()==null || taLicence.getTaAbonnements().size()==0){
				TaRTSupportDAO doaRTSupport = new TaRTSupportDAO();
				TaRTSupport taRTSupport = null;
				param.setModeEcran(EnumModeObjet.C_MO_INSERTION);

				if(taLicence.getTaTSupport()!=null && taLicence.getTaArticle()!=null){
					taRTSupport=doaRTSupport.findByidArticleIdTSupport(taLicence.getTaArticle().getIdArticle(),taLicence.getTaTSupport().getIdTSupport());
				}
				if(taRTSupport!=null){
					if(taRTSupport.getTaArticleAbonnement()!=null){
						swtAbonnement.setCodeArticle(taRTSupport.getTaArticleAbonnement().getCodeArticle());
						swtAbonnement.setIdArticle(taRTSupport.getTaArticleAbonnement().getIdArticle());
					}
					if(taRTSupport.getTaTAbonnement()!=null){
						swtAbonnement.setCodeTAbonnement(taRTSupport.getTaTAbonnement().getCodeTAbonnement());
						swtAbonnement.setIdTAbonnement(taRTSupport.getTaTAbonnement().getIdTAbonnement());
						swtAbonnement.setDateDebut(LibDate.PremierJourMoisSuivant(taLicence.getDateAcquisition()));						
						swtAbonnement.setDateFin(LibDate.incrementDate(swtAbonnement.getDateDebut(),-1, taRTSupport.getTaTAbonnement().getDuree(), 0));
					}
						swtAbonnement.setCodeSupportAbon(taLicence.getCodeSupportAbon());
						swtAbonnement.setIdSupportAbon(taLicence.getIdSupportAbon());
					
					if(taLicence.getTaTiers()!=null){
						swtAbonnement.setCodeTiers(taLicence.getTaTiers().getCodeTiers());
						swtAbonnement.setIdTiers(taLicence.getTaTiers().getIdTiers());
					}

				}				
				param.setSelected(swtAbonnement);
				param.setEnregistreEtFerme(true);
				Display.getDefault().syncExec(new Runnable() {
					@Override
					public void run() { 			
						IEditorPart e=AbstractLgrMultiPageEditor.ouvreFiche("","", AbonnementMultiPageEditor.ID_EDITOR,param,false);
						if(fermer==false)((AbstractLgrMultiPageEditor)e).findMasterController().addRetourEcranListener(getThis());
						((AbstractLgrMultiPageEditor)e).findMasterController().addDeclencheCommandeControllerListener(getThis());
					}});
			}else{
				//actAfficheAbonnement();
			}


		}catch (Exception e) {
			logger.error("",e);
		}
	}
	
	public void actAfficheAbonnement( ){
	    final ParamAffiche param = new ParamAfficheAbonnement();
		param.setModeEcran(EnumModeObjet.C_MO_CONSULTATION);//selectDernierAbonnement
		TaAbonnementDAO daoAbonnement=new TaAbonnementDAO();
		TaAbonnement abonnementenCours = null;
		abonnementenCours=daoAbonnement.selectDernierAbonnement(taLicence.getIdSupportAbon());
		if(abonnementenCours!=null)param.setIdDocument(abonnementenCours.getIdAbonnement());
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() { 			
				IEditorPart e=AbstractLgrMultiPageEditor.ouvreFiche("","", AbonnementMultiPageEditor.ID_EDITOR,param,false);
			((AbstractLgrMultiPageEditor)e).findMasterController().addRetourEcranListener(getThis());
			}});

	}
	
	public void actAfficheComptePoint( ){
	    final ParamAffiche param = new ParamAfficheComptePoint();
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() { 			
				IEditorPart e=AbstractLgrMultiPageEditor.ouvreFiche(LibConversion.integerToString(taLicence.getTaTiers().getIdTiers()),"", comptePointMultiPageEditor.ID_EDITOR,param,false);
			((AbstractLgrMultiPageEditor)e).findMasterController().addRetourEcranListener(getThis());
			}});

	}
	
	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if(!vue.isDisposed()) {
			if(selection instanceof IStructuredSelection) {
				if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) != 0)
						&& (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) != 0)) {
					SWTSupportAbonLogiciel t = (SWTSupportAbonLogiciel)((IStructuredSelection)selection).getFirstElement();
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

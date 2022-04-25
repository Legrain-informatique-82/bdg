package fr.legrain.supporthebergement.controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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


import fr.legrain.SupportAbonLegrain.dao.TaServeur;
import fr.legrain.SupportAbonLegrain.dao.TaServeurDAO;
import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Tiers.ModelHebergement;
import fr.legrain.gestCom.Module_Tiers.SWTSupportAbon;
import fr.legrain.gestCom.Module_Tiers.SWTSupportAbonHerbergement;
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
import fr.legrain.licence.divers.MessagesEcran;
import fr.legrain.supportHebergement.dao.TaHebergement;
import fr.legrain.supportHebergement.dao.TaHebergementDAO;
import fr.legrain.supporthebergement.divers.ParamAfficheLicenceHebergement;
import fr.legrain.supporthebergement.ecrans.PaHebergementSWT;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.ecran.PaTiersSWT;
import fr.legrain.tiers.ecran.ParamAfficheTiers;
import fr.legrain.tiers.ecran.SWTPaTiersController;
import fr.legrain.tiers.editor.EditorInputTiers;
import fr.legrain.tiers.editor.TiersMultiPageEditor;

public class SWTPaHebergementController extends JPABaseControllerSWTStandard 
implements RetourEcranListener, ISelectionListener{

	static Logger logger = Logger.getLogger(SWTPaHebergementController.class.getName());	

	private PaHebergementSWT vue = null;
	private TaHebergementDAO dao = null;
	private String idSupportAbon = null;

	private Object ecranAppelant = null;
	private SWTSupportAbonHerbergement swtEpicea;
	private SWTSupportAbonHerbergement swtOldEpicea;
	private Realm realm;
	private DataBindingContext dbc;

	private Class classModel = SWTSupportAbonHerbergement.class;
	private ModelHebergement modelHebergement = new ModelHebergement();	
	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private IObservableValue selectedEpicea;
	private String[] listeChamp;
	private String nomClass = this.getClass().getSimpleName();
	private MapChangeListener changeListener = new MapChangeListener();
	
	private TaTiers masterEntity = null;
	private TaTiersDAO masterDAO = null;

	private LgrDozerMapper<SWTSupportAbonHerbergement,TaHebergement> mapperUIToModel  = new LgrDozerMapper<SWTSupportAbonHerbergement,TaHebergement>();
	private LgrDozerMapper<TaHebergement,SWTSupportAbonHerbergement> mapperModelToUI  = new LgrDozerMapper<TaHebergement,SWTSupportAbonHerbergement>();
	private TaHebergement taLicence = null;

//	private Impression impression = new Impression();

	public SWTPaHebergementController(PaHebergementSWT vue) {
		this(vue,null);
	}

	public SWTPaHebergementController(PaHebergementSWT vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaHebergementDAO(getEm());
//		daoDocumentTiers = new TaDocumentTiersDAO(getEm());

		setVue(vue);
		vue.getShell().addTraverseListener(new Traverse());
		vue.getTfDateAcquisition().addTraverseListener(new DateTraverse());
		vue.getTfDateAcquisition().addFocusListener(dateFocusListener);
		initController();
		initSashFormWeight();
		
		//PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().addPostSelectionListener(ListeTiersView.ID, this);
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
	
	public void bind(PaHebergementSWT paEpiceaSWT){
		bind(paEpiceaSWT,null);
	}
	
	public void bindID(PaHebergementSWT paEpiceaSWT, int idSupportAbon ){
		SWTSupportAbonHerbergement swtEpiceaTmp = new SWTSupportAbonHerbergement();
		modelHebergement.mapping(dao.findById(idSupportAbon), swtEpiceaTmp);
		bind(paEpiceaSWT, swtEpiceaTmp);
	}
	
	public void bindCode(PaHebergementSWT paEpiceaSWT, String code ){
		SWTSupportAbonHerbergement swtEpiceaTmp = new SWTSupportAbonHerbergement();
		modelHebergement.mapping(dao.findByCode(code), swtEpiceaTmp);
		bind(paEpiceaSWT, swtEpiceaTmp);
	}
	
	public void bind(PaHebergementSWT paEpiceaSWT, SWTSupportAbonHerbergement selection ){
		try {
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());
				dbc = new DataBindingContext(realm);
				if(dao.dataSetEnModif()){
					selection.setCodeSupportAbon(swtEpicea.getCodeSupportAbon());
					swtEpicea = selection;
					modelHebergement.mapping(selection,taLicence);
				}else{
				if(selection!=null && selection.getIdSupportAbon()!=null) {
					taLicence =  dao.findById(selection.getIdSupportAbon());
					
					/*selection =*/ modelHebergement.mapping(taLicence, selection);
					swtEpicea = selection;
				} else {
					if(swtEpicea==null) {
						swtEpicea = new SWTSupportAbonHerbergement();
					}
					selection = swtEpicea;
				}
				}

				if(taLicence!=null && taLicence.getTaTiers()!=null)
					vue.getLaCodeTiers().setText("Code tiers : "+taLicence.getTaTiers().getCodeTiers());
				
				vue.getCfServeur().removeAll();
				TaServeurDAO doaServeur = new TaServeurDAO(getEm());
				List<TaServeur> liste=  doaServeur.selectAll();
				for (TaServeur taServeur : liste) {
					vue.getCfServeur().add(taServeur.getServeur());
				}				
				vue.getCfServeur().setVisibleItemCount(vue.getCfServeur().getItemCount());
				vue.getCfServeur().select(0);
				
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
			fireChangementDeSelection(new ChangementDeSelectionEvent(SWTPaHebergementController.this));
		}
		initEtatComposant();
		vue.getLaHorsAbon().setVisible(false);
		if(swtEpicea.getDateDebAbon()==null || swtEpicea.getDateFinAbon()==null){
			LibDateTime.setDateNull(vue.getTfDateDebAbon());
			LibDateTime.setDateNull(vue.getTfDateFinAbon());
			vue.getLaHorsAbon().setVisible(true);
		}
		vue.getTfDateDebAbon().setEnabled(false);
		vue.getTfDateFinAbon().setEnabled(false);
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
				vue.getLaTitreFormulaire().setText(ParamAfficheLicenceHebergement.C_TITRE_FORMULAIRE);
			}

			if(param.getSousTitre()!=null){
				vue.getLaTitreFenetre().setText(param.getSousTitre());
			} else {
				vue.getLaTitreFenetre().setText(ParamAfficheLicenceHebergement.C_SOUS_TITRE);
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

			if(modelHebergement!=null && modelHebergement.getListeEntity()!=null)
				modelHebergement.getListeEntity().clear();
			if(modelHebergement!=null && modelHebergement.getListeObjet()!=null)
			modelHebergement.getListeObjet().clear();
			
			if(param.getSelected()!=null 
					&& param.getSelected() instanceof SWTSupportAbon
					) {
				bind(vue,(SWTSupportAbonHerbergement)param.getSelected());
			} else if (param.getCodeDocument()!=null){
				bindCode(vue, param.getCodeDocument());
			}else if (param.getIdDocument()!=null){
				bindID(vue, param.getIdDocument());
			}else
				bind(vue,null);

				VerrouInterface.setVerrouille(false);
				setSwtOldLicence();

				if(param.getCodeDocument()!=null) {
					SWTSupportAbonHerbergement licence = modelHebergement.recherche(Const.C_CODE_SUPPORT_ABON, param.getCodeDocument());
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
		mapInfosVerifSaisie.put(vue.getTfCodeSupport(), new InfosVerifSaisie(new TaHebergement(),Const.C_CODE_SUPPORT_ABON,null));
		mapInfosVerifSaisie.put(vue.getTfCommentaire(), new InfosVerifSaisie(new TaHebergement(),Const.C_COMMENTAIRE,null));
		mapInfosVerifSaisie.put(vue.getCfServeur(), new InfosVerifSaisie(new TaHebergement(),Const.C_SERVEUR,null));
		mapInfosVerifSaisie.put(vue.getTfDateAcquisition(), new InfosVerifSaisie(new TaHebergement(),Const.C_DATE_ACQUISITION,null));

		
		initVerifyListener(mapInfosVerifSaisie, dao);
	}

	protected void initComposantsVue() throws ExceptLgr {}

	protected void initEtatBouton() {
		initEtatBoutonCommand();
		
		switch (daoStandard.getModeObjet().getMode()) {
		case C_MO_INSERTION:
			enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,false);
			break;
		case C_MO_EDITION:
			enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,false);
			break;
		case C_MO_CONSULTATION:
			enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,false);
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
		mapComposantChamps.put(vue.getCfServeur(), Const.C_SERVEUR);

		mapComposantChamps.put(vue.getTfDateDebAbon(),Const.C_DATE_DEB_ABON);
		mapComposantChamps.put(vue.getTfDateFinAbon(),Const.C_DATE_FIN_ABON);
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
	}



	public SWTPaHebergementController getThis(){
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
					fr.legrain.licence.divers.MessagesEcran.getString("Message.Attention"),
					MessagesEcran.getString("Epicea.Message.Enregistrer"))) {

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
				setListeEntity(getModelHebergement().remplirListe(getEm()));
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
					if(getFocusAvantAideSWT().equals(vue.getCfServeur())){
//						TaTiers u = null;
//						TaTiersDAO t = new TaTiersDAO(getEm());
//						u = t.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
//						t = null;
//						taLicence.setCommercial(u);
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
				taLicence = new TaHebergement();//ne pas déplacer pour que les autres écrans récupère le bon masterEntity
				taLicence.setIdSupportAbon(0);
				
				//unBind(dbc);
				swtEpicea = new SWTSupportAbonHerbergement();
				//bind(vue,swtEpicea);
				
				swtEpicea.setCodeSupportAbon(dao.genereCodeJPA());
				validateUIField(Const.C_CODE_SUPPORT_ABON,swtEpicea.getCodeSupportAbon());//permet de verrouiller le code genere
				modelHebergement.mapping(swtEpicea, taLicence);
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
						.getString("Epicea.Message.Supprimer"))) {
					dao.begin(transaction);
					TaHebergement u = dao.findById(swtEpicea.getIdSupportAbon());
					dao.supprimer(u);
					dao.commit(transaction);
					if(containsEntity(u)) modelHebergement.getListeEntity().remove(u);
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
						MessagesEcran.getString("Epicea.Message.Annuler")))){

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
		switch ((SWTPaHebergementController.this.dao.getModeObjet().getMode())) {
		case C_MO_CONSULTATION:
		case C_MO_EDITION:
		case C_MO_INSERTION:										
			//creation d'une recherche

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


				switch ((SWTPaHebergementController.this.dao.getModeObjet().getMode())) {
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
			mapperUIToModel.map((SWTSupportAbonHerbergement) selectedEpicea.getValue(),taLicence);
		}
		return null;
	}

	public IStatus validateUIField(String nomChamp,Object value) {
		String validationContext = "SUPPORT_ABON";
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
					taLicence.setTaArticle(f);
				}
				dao = null;
			} else if(nomChamp.equals(Const.C_COMMENTAIRE)||nomChamp.equals(Const.C_DATE_DEB_ABON)
					||nomChamp.equals(Const.C_DATE_FIN_ABON)) {
				s=new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
			}else {
				boolean verrouilleModifCode = false;
				TaHebergement u = new TaHebergement();

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
						modelHebergement.mapping(swtEpicea, taLicence);
//						mapperUIToModel.map(swtEpicea,taLicence);
					}

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
		vue.getTfCodeArticle().setEditable(false);
		vue.getGroupAbonnement().setVisible(!dao.getModeObjet().getMode().equals(EnumModeObjet.C_MO_INSERTION));
		changeCouleur(vue);
	}

	public SWTSupportAbonHerbergement getSwtOldEpicea() {
		return swtOldEpicea;
	}

	public void setSwtOldLicence(SWTSupportAbonHerbergement swtOldEpicea) {
		this.swtOldEpicea = swtOldEpicea;
	}
	public void setSwtOldLicence() {
		if(swtEpicea!=null) {
			this.swtOldEpicea=SWTSupportAbonHerbergement.copy(swtEpicea);
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
	public boolean containsEntity(TaHebergement entite){
		if(modelHebergement.getListeEntity()!=null){
			for (Object e : modelHebergement.getListeEntity()) {
				if(((TaHebergement)e).getIdSupportAbon()==
					entite.getIdSupportAbon())return true;
			}
		}
		return false;
	}

	public boolean setSwtOldEpiceaRefresh() {
		try {	
			if (swtEpicea!=null){
				TaHebergement taOld =dao.findById(taLicence.getIdSupportAbon());
				taOld=dao.refresh(taOld);
				taLicence=taOld;
				fireChangementMaster(new ChangementMasterEntityEvent(this,taLicence,true));
				this.swtOldEpicea=SWTSupportAbonHerbergement.copy(swtEpicea);
			}
			return true;
		} catch (Exception e) {
			return false;
		}		
	}
	public void setVue(PaHebergementSWT vue) {
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
		mapComposantDecoratedField.put(vue.getTfDateAcquisition(),vue.getFieldDateAcquisition());
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
		if (taLicence!=null) { //enregistrement en cours de modification/insertion
			idActuel = taLicence.getIdSupportAbon();
		} else if(swtEpicea!=null && swtEpicea.getIdSupportAbon()!=null) {
			idActuel = swtEpicea.getIdSupportAbon();
		}

		
		if (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION) == 0) {

		} else {
			if (taLicence!=null && swtEpicea!=null) {
				mapperModelToUI.map(taLicence, swtEpicea);
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
		if (taLicence!=null) { //enregistrement en cours de modification/insertion
			idActuel = taLicence.getIdSupportAbon();
		} else if(selectedEpicea!=null && (SWTSupportAbonHerbergement) selectedEpicea.getValue()!=null) {
			idActuel = ((SWTSupportAbonHerbergement) selectedEpicea.getValue()).getIdSupportAbon();
		}

		int rang = modelHebergement.getListeObjet().indexOf(selectedEpicea.getValue());
		mapperModelToUI.map(taLicence, swtOldEpicea);
		if (rang>0) modelHebergement.getListeObjet().set(rang, swtOldEpicea);
		rang =((WritableList)tableViewer.getInput()).indexOf(swtOldEpicea);
		if (rang>0){
			((WritableList)tableViewer.getInput()).set(rang, swtOldEpicea);
		}else{
		writableList =new WritableList(realm, modelHebergement.getListeObjet(),
				classModel);
		tableViewer.setInput(writableList);
		}
		if(idActuel!=0) {
			tableViewer.setSelection(new StructuredSelection(modelHebergement.recherche(Const.C_ID_SUPPORT_ABON
					, idActuel)),true);
		}else
			tableViewer.selectionGrille(0);				
	}



	public ModelHebergement getModelHebergement() {
		return modelHebergement;
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}



	public TaHebergementDAO getDao() {
		return dao;
	}



	public SWTSupportAbonHerbergement getSwtEpicea() {
		return swtEpicea;
	}



	public TaHebergement getTaLicence() {
		return taLicence;
	}

	public boolean changementPageValide(){		
		return (daoStandard.selectCount()>0);
	}



	public void setDao(TaHebergementDAO dao) {
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
	
//	public void actAfficherTous() throws Exception{
//		vue.getGrille().setVisible(true);  
//		vue.getBtnTous().setVisible(false);
//		vue.getLaTitreGrille().setVisible(true);
//		vue.getCompositeForm().setWeights(new int[]{50,100});
//		dao.setJPQLQuery(dao.getJPQLQueryInitial());
//		modelHebergement.setJPQLQuery(null);
//		modelHebergement.setListeEntity(null);
//		try {
//			actRefresh();
//		} catch (Exception e1) {
//			logger.error("", e1);
//		}
//	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if(!vue.isDisposed()) {
			if(selection instanceof IStructuredSelection) {
				if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) != 0)
						&& (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) != 0)) {
					SWTSupportAbonHerbergement t = (SWTSupportAbonHerbergement)((IStructuredSelection)selection).getFirstElement();
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

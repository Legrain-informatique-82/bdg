package fr.legrain.licence.controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

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
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import fr.legrain.SupportAbon.dao.TaSupportAbon;
import fr.legrain.SupportAbon.dao.TaSupportAbonDAO;
import fr.legrain.abonnement.dao.TaAbonnement;
import fr.legrain.document.divers.TypeDoc;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Tiers.SWTSupportAbon;
import fr.legrain.gestCom.Module_Tiers.SWTTiers;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.IDetailController;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideRechercheSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideRechercheControllerSWT;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDePageEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDeSelectionEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.LibConversion;
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
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.lib.gui.grille.LgrViewerSupport;
import fr.legrain.licence.divers.CTabItemSupport;
import fr.legrain.licence.divers.ICTabItem;
import fr.legrain.licence.divers.ParamAfficheSupport;
import fr.legrain.licence.ecrans.PaSupportAbon;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.ecran.ParamAfficheTiers;

public class SWTPaSupportAbonController extends JPABaseControllerSWTStandard
implements RetourEcranListener,IDetailController {

	static Logger logger = Logger.getLogger(SWTPaSupportAbonController.class.getName());
	private PaSupportAbon vue = null;
	private TaSupportAbonDAO dao = null;

	private Object ecranAppelant = null;
//	private SWTSupportAbon swtSupport;
	private SWTSupportAbon swtOldSupport;
	private Realm realm;
	private DataBindingContext dbc;
    private static int previousSelection = -1;
	private Class classModel = SWTSupportAbon.class;
	private ModelGeneralObjet<SWTSupportAbon,TaSupportAbonDAO,TaSupportAbon> modelSupport =  null;

	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private IObservableValue selectedSupport;
	private String[] listeChamp;
	private String nomClassController = this.getClass().getSimpleName();
	private MapChangeListener changeListener = new MapChangeListener();

	private TaTiers masterEntity = null;
	private TaTiersDAO masterDAO = null;

	private Map<Object,CTabItemSupport> listeGestionnaireEditorSupportAbon = new LinkedHashMap<Object,CTabItemSupport>();
	
//	private LgrDozerMapper<SWTSupportAbon,TaSupportAbon> mapperUIToModel  = new LgrDozerMapper<SWTSupportAbon,TaSupportAbon>();
	private TaSupportAbon taSupport = new TaSupportAbon();

	private String idTiers = null;

    private static final String IS_ENABLED = "actif";
    private static final String NOT_ENABLED = "passif";
    
	public SWTPaSupportAbonController(PaSupportAbon vue) {
		this(vue,null);
	}

	public SWTPaSupportAbonController(PaSupportAbon vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaSupportAbonDAO(getEm());
		masterDAO=new TaTiersDAO(getEm());
		
		modelSupport =  new ModelGeneralObjet<SWTSupportAbon,TaSupportAbonDAO,TaSupportAbon>(dao,classModel);
		setVue(vue);
		vue.getGrille().addMouseListener(
				new MouseAdapter() {

					public void mouseDoubleClick(MouseEvent e) {
						if(selectedSupport.getValue()!=null){
							String idEditor = TypeDoc.getInstance().getEditorDoc()
									.get(TypeDoc.TYPE_FACTURE);
							String valeurIdentifiant = ((SWTSupportAbon)selectedSupport.getValue()).getCodeDocument();
							ouvreDocument(valeurIdentifiant, idEditor);
						}
					}

				});
		// a faire avant l'initialisation du Binding
		vue.getGrille().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setSwtOldSupport();
			}
		});
		vue.getShell().addShellListener(this);

		// Branchement action annuler : empeche la fermeture automatique de la
		// fenetre sur ESC
		vue.getShell().addTraverseListener(new Traverse());

		initController();
	}

	private void initVue(){

	}
	
	private void initController() {
		try {
			createContributors();
			setGrille(vue.getGrille());
			vue.getCompositeForm().setWeights(new int[]{30,70});
//			initSashFormWeight();
			vue.layout(true);
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
			vue.getPaFomulaire().setMenu(popupMenuFormulaire);
			vue.getPaGrille().setMenu(popupMenuGrille);

			initEtatBouton();
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaTiersController", e);
		}
	}

	public boolean trouvePluginSupport(Object o){
		boolean trouve=false;
		for (Object key : listeGestionnaireEditorSupportAbon.keySet()) {
			trouve=(key.getClass()==o.getClass());
			if(trouve)return true;
		}
		return false;
	}
	/**
	 * Creation des objet pour l'interface, a partir de l'entite principale.<br>
	 * @return
	 */
	public List<SWTSupportAbon> IHMmodel() {
		
		LinkedList<TaSupportAbon> ldao = new LinkedList<TaSupportAbon>();
		LinkedList<SWTSupportAbon> lswt = new LinkedList<SWTSupportAbon>();
		if(masterEntity!=null)masterEntity=masterDAO.refresh(masterEntity);
		if(masterEntity!=null /*&& !masterEntity.getTaSupportAbons().isEmpty()*/) {
			Query ejbQuery = getEm().createQuery("select s from TaSupportAbon s join s.taTiers t where t.idTiers="+masterEntity.getIdTiers());
			List<TaSupportAbon> l = ejbQuery.getResultList();
//			ldao.addAll(masterEntity.getTaSupportAbons());
			ldao.addAll(l);
			for (TaSupportAbon o : ldao) {
				if(trouvePluginSupport(o)){
					SWTSupportAbon t = new SWTSupportAbon();
					if(o.getTaLDocument()!=null && o.getTaLDocument().getTaDocument()!=null)
						t.setCodeDocument(o.getTaLDocument().getTaDocument().getCodeDocument());
					t.setCodeSupportAbon(o.getCodeSupportAbon());
					t.setIdSupportAbon(o.getIdSupportAbon());
					t.setCodeArticle(o.getTaArticle().getCodeArticle());
					t.setCodeTiers(o.getTaTiers().getCodeTiers());
					t.setNomTiers(o.getTaTiers().getNomTiers());
					t.setCommentaire(o.getCommentaire());
					if(o.getCommercial()!=null)
						t.setCommercial(o.getCommercial().getCodeTiers());
					t.setDateAcquisition(o.getDateAcquisition());
					t.setLibelle(o.getLibelle());
					t.setTypeSupport(o.getTaTSupport().getCodeTSupport());
					
					for (TaAbonnement Ra : o.getTaAbonnements()) {
						t.setDateDebAbon(Ra.getDateDebut());
						t.setDateFinAbon(Ra.getDateFin());
					}

					lswt.add(t);
				}
			}

		}
		return lswt;
	}

	protected void initEtatBouton() {
		initEtatBouton(IHMmodel());

		boolean trouve = contientDesEnregistrement(IHMmodel());
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

	@Override
	protected void initImageBouton() {
		super.initImageBouton();
		String imageCarte = "/icons/email.png";
		//vue.getBtnSendMail().setImage(TiersPlugin.getImageDescriptor(imageCarte).createImage());
		vue.layout(true);
	}


	
	public void bindID(PaSupportAbon paSupportAbon, int idTiers ){
		masterEntity= masterDAO.findById(idTiers);
		bind(paSupportAbon);
	}
	
	public void bindCode(PaSupportAbon paSupportAbon, String code ){
		masterEntity= masterDAO.findByCode(code);
		bind(paSupportAbon);
	}
	public void bindSelection(PaSupportAbon paSupportAbon, SWTTiers selection ){
		if(selection!=null && selection.getIdTiers()!=null) {
			masterEntity =  masterDAO.findById(selection.getIdTiers());
		} else {
			masterEntity=null;
		}
		try {
			actRefresh();
		} catch (Exception e) {
			logger.error("", e);
		}
		bind(paSupportAbon);
	}
	
	public void bind(PaSupportAbon paSupportAbon) {
		try {
			if(tableViewer==null){
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			tableViewer = new LgrTableViewer(paSupportAbon.getGrille());

			setTableViewerStandard( new LgrTableViewer(vue.getGrille()));
			String[] titreColonnes =new String[] {"Code support","type support","D. acquisition","tiers","Nom","D. début","D. fin","document"};
			getTableViewerStandard().createTableCol(vue.getGrille(),titreColonnes,
					new String[] {"100","150","100","70","200","100","100","100"},0);
			String[] listeChamp = new String[] {"codeSupportAbon","typeSupport","dateAcquisition","codeTiers","nomTiers","dateDebAbon","dateFinAbon","codeDocument"};//,"export"
			getTableViewerStandard().setListeChamp(listeChamp);
			getTableViewerStandard().tri(classModel, listeChamp,titreColonnes);


			LgrViewerSupport.bind(tableViewer, 
					new WritableList(IHMmodel(), classModel),
					BeanProperties.values(listeChamp)
			);

			selectedSupport = ViewersObservables
			.observeSingleSelection(tableViewer);

			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			tableViewer.selectionGrille(0);

			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);

			selectedSupport.getValue();
			bindingFormMaitreDetail(dbc, realm, selectedSupport,classModel);
			changementDeSelection();
			selectedSupport.addChangeListener(new IChangeListener() {

				public void handleChange(ChangeEvent event) {
					changementDeSelection();
				}

			});
			}else actRefresh();
			

		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("", e);
		}
	}

	private void changementDeSelection() {
		taSupport=null;
		if(selectedSupport!=null && selectedSupport.getValue()!=null) {
			if(((SWTSupportAbon) selectedSupport.getValue()).getIdSupportAbon()!=null) {
				taSupport = dao.findById(((SWTSupportAbon) selectedSupport.getValue()).getIdSupportAbon());
				
			}
			//null a tester ailleurs, car peut etre null en cas d'insertion
			fireChangementDeSelection(new ChangementDeSelectionEvent(SWTPaSupportAbonController.this));
		}
		for (Object obj : listeGestionnaireEditorSupportAbon.keySet()) {
			if(taSupport!=null && obj.getClass().equals(taSupport.getClass())){
				CTabItemSupport tab=listeGestionnaireEditorSupportAbon.get(obj);
				ParamAffiche param = new ParamAffiche();
				param.setIdDocument(taSupport.getIdSupportAbon());
				tab.getController().configPanel(param);
				tab.getTabFolder().setData(IS_ENABLED);
				tab.getTabFolder().setSelection(tab.getTabItem());
				previousSelection=tab.getTabFolder().getSelectionIndex();
			}else{
				CTabItemSupport tab=listeGestionnaireEditorSupportAbon.get(obj);
				ParamAffiche param = new ParamAffiche();
				tab.getTabFolder().setData(NOT_ENABLED);
				tab.getController().configPanel(param);
			}
		}
	}

	

	public Composite getVue() {
		return vue;
	}

	public ResultAffiche configPanel(ParamAffiche param) {
		if (param != null) {
			Map<String,String[]> map = dao.getParamWhereSQL();
			//			ibTaTable.ouvreDataset();
			if(param instanceof ParamAfficheSupport){
			if (((ParamAfficheSupport) param).getFocusDefautSWT() != null && !((ParamAfficheSupport) param).
					getFocusDefautSWT().isDisposed())
				((ParamAfficheSupport) param).getFocusDefautSWT().setFocus();
			else
				((ParamAfficheSupport) param).setFocusDefautSWT(vue.getGrille());
			//vue.getLaTitreFormulaire().setText(((ParamAfficheSupport) param).getTitreFormulaire());
			vue.getLaTitreGrille().setText(((ParamAfficheSupport) param).getTitreGrille());
			vue.getLaTitreFenetre().setText(((ParamAfficheSupport) param).getSousTitre());
			}
			if (param.getEcranAppelant() != null) {
				ecranAppelant = param.getEcranAppelant();
			}
			if(param.getSelection()!=null 
					&& !param.getSelection().isEmpty()
					&& param.getSelection() instanceof IStructuredSelection
					&& ((IStructuredSelection)param.getSelection()).getFirstElement()!=null
					&& ((IStructuredSelection)param.getSelection()).getFirstElement() instanceof SWTTiers
					) {
				bindSelection(vue,(SWTTiers)((IStructuredSelection)param.getSelection()).getFirstElement());
			} else if(param.getIdFiche()!=null &&  !param.getIdFiche().equals("")) {
				this.idTiers=param.getIdFiche();
				bindID(vue,LibConversion.stringToInteger(idTiers));
			} else if(param.getCodeDocument()!=null && !param.getCodeDocument().equals("")) {
				bindCode(vue,param.getCodeDocument());
			} else if(param instanceof ParamAfficheTiers && ((ParamAfficheTiers)param).getIdTiers()!=null && !((ParamAfficheTiers)param).getIdTiers().equals("")) {
				this.idTiers=((ParamAfficheTiers)param).getIdTiers();
				bindID(vue,LibConversion.stringToInteger(idTiers));
			} else {
				bind(vue);
			}

			VerrouInterface.setVerrouille(false);
			setSwtOldSupport();

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
				//changementDeSelection();
				initEtatBouton();
			}

			//			param.setFocus(ibTaTable.getFModeObjet().getFocusCourant());
		}
		return null;
	}
	
	protected void initVerifySaisie() {

	}

	protected void initComposantsVue() throws ExceptLgr {
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




		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID, C_COMMAND_GLOBAL_REFRESH_ID };
		mapActions.put(null, tabActionsAutres);

	}

	public SWTPaSupportAbonController getThis() {
		return this;
	}

	@Override
	public boolean onClose() throws ExceptLgr {
	
		return true;
	}

	public void retourEcran(final RetourEcranEvent evt) {
		
		super.retourEcran(evt);
	}



	

	@Override
	protected void actInserer() throws Exception {
		
	}

	@Override
	protected void actModifier() throws Exception {
		
	}

	@Override
	protected void actSupprimer() throws Exception {
		
	}

	@Override
	protected void actFermer() throws Exception {
		// (controles de sortie et fermeture de la fenetre) => onClose()
		if(onClose())
			closeWorkbenchPart();
	}

	@Override
	protected void actAnnuler() throws Exception {
		
	}

	@Override
	protected void actImprimer() throws Exception {
//

	}

	@Override
	protected boolean aideDisponible() {
		boolean result = false;
		switch ((getThis().dao.getModeObjet().getMode())) {
		case C_MO_CONSULTATION:
			if(getFocusCourantSWT().equals(vue.getGrille()))
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
					paramAfficheAideRecherche
					.setFocusSWT(((PaAideRechercheSWT) paAideRechercheController1
							.getVue()).getTfChoix());
					paramAfficheAideRecherche
					.setRefCreationSWT(controllerEcranCreation);
					paramAfficheAideRecherche.setEditorCreation(editorCreation);
					paramAfficheAideRecherche.setEditorCreationId(editorCreationId);
					paramAfficheAideRecherche.setEditorInputCreation(editorInputCreation);
					paramAfficheAideRecherche
					.setParamEcranCreation(parametreEcranCreation);
					paramAfficheAideRecherche.setShellCreation(s2);
					paAideRechercheController1
					.configPanel(paramAfficheAideRecherche);
					// paramAfficheAideRecherche.setFocusDefaut(paAideRechercheController1.getVue().getTfChoix());

					// Ajout d'une recherche
					paAideController.addRecherche(paAideRechercheController1,
							paramAfficheAideRecherche.getTitreRecherche());

					// Paramï¿½trage de l'ecran d'aide principal
					ParamAfficheAideSWT paramAfficheAideSWT = new ParamAfficheAideSWT();
					ParamAfficheAide paramAfficheAide = new ParamAfficheAide();

					// enregistrement pour le retour de l'ecran d'aide
					paAideController.addRetourEcranListener(getThis());
					Control focus = vue.getShell().getDisplay().getFocusControl();
					// affichage de l'ecran d'aide principal (+ ses recherches)

					dbc.getValidationStatusMap().removeMapChangeListener(
							changeListener);
					//LgrShellUtil.afficheAideSWT(paramAfficheAide, null, paAide,paAideController, s);
					/*****************************************************************/
					paAideController.configPanel(paramAfficheAide);
					/*****************************************************************/
					dbc.getValidationStatusMap().addMapChangeListener(
							changeListener);

				}

			} finally {
				VerrouInterface.setVerrouille(false);
				vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
			}
		}
	}

	public IStatus validateUI() throws Exception {

		return null;
	}

	public IStatus validateUIField(String nomChamp,Object value) {
		
		return null;
	}


	@Override
	protected void actEnregistrer() throws Exception {
		
	}

	public void initEtatComposant() {
		try {
			//			if((SWTEmail)selectedSupport.getValue()!=null)
			//				ibTaTable.setChamp_Model_Obj((SWTEmail)selectedSupport.getValue());
			super.initEtatComposantGeneral();
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
		}
	}

	public SWTSupportAbon getSwtOldSupport() {
		return swtOldSupport;
	}

	public void setSwtOldSupportAbon(SWTSupportAbon swtOldSupportAbon) {
		this.swtOldSupport = swtOldSupportAbon;
	}

	public void setSwtOldSupport() {
		if (selectedSupport.getValue() != null)
			this.swtOldSupport = SWTSupportAbon.copy((SWTSupportAbon) selectedSupport.getValue());
		else {
			if (tableViewer.selectionGrille(0)){
				this.swtOldSupport = SWTSupportAbon.copy((SWTSupportAbon) selectedSupport.getValue());
				tableViewer.setSelection(new StructuredSelection(
						(SWTSupportAbon) selectedSupport.getValue()), true);
			}}
	}

	public void setVue(PaSupportAbon vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();

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
		if (taSupport!=null) { //enregistrement en cours de modification/insertion
			idActuel = taSupport.getIdSupportAbon();
		} else if(selectedSupport!=null && (SWTSupportAbon) selectedSupport.getValue()!=null) {
			idActuel = ((SWTSupportAbon) selectedSupport.getValue()).getIdSupportAbon();
		}

		//rafraichissement des valeurs dans la grille
		writableList = new WritableList(realm, IHMmodel(), classModel);
		tableViewer.setInput(writableList);

		Object support=recherche(Const.C_ID_SUPPORT_ABON, idActuel);

		if(idActuel!=0 && support!=null) {
			tableViewer.setSelection(new StructuredSelection(support));
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

	public ModelGeneralObjet<SWTSupportAbon,TaSupportAbonDAO,TaSupportAbon> getModelSupport() {
		return modelSupport;
	}

	public TaSupportAbonDAO getDao() {
		return dao;
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

	public boolean isUtilise(){
		return (((SWTSupportAbon)selectedSupport.getValue()).getIdSupportAbon()!=null &&
				!dao.recordModifiable(dao.getNomTable(),
						((SWTSupportAbon)selectedSupport.getValue()).getIdSupportAbon()))||
						!masterDAO.autoriseModification(masterEntity);		
	}
	public void modifMode(){
		if (!VerrouInterface.isVerrouille() ){
			try {
				if(!daoStandard.dataSetEnModif()) {
					if(!masterEntity.getTaEmails().isEmpty()) {
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

	@Override
	protected void initMapComposantChamps() {
		// TODO Auto-generated method stub
		if (mapComposantChamps == null) 
			mapComposantChamps = new LinkedHashMap<Control,String>();

		if (listeComposantFocusable == null) 
			listeComposantFocusable = new ArrayList<Control>();

		
		
		if(mapInitFocus == null) 
			mapInitFocus = new LinkedHashMap<ModeObjet.EnumModeObjet,Control>();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION,vue.getGrille());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION,vue.getGrille());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION,vue.getGrille());

		activeModifytListener();
	}

	public TaSupportAbon getTaSupport() {
		return taSupport;
	}

	public void setTaSupport(TaSupportAbon taSupport) {
		this.taSupport = taSupport;
	}


	private void createContributors() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		
		IExtensionPoint pointSupport = registry.getExtensionPoint("GestionCommerciale.SupportAbonnement"); //$NON-NLS-1$

		//gestion des impressions de document
		if (pointSupport != null) {
			ImageDescriptor icon = null;
			IExtension[] extensions = pointSupport.getExtensions();
			for (int i = 0; i < extensions.length; i++) {
				IConfigurationElement confElements[] = extensions[i].getConfigurationElements();
				for (int jj = 0; jj < confElements.length; jj++) {
					try {
						String ClassEditorName = confElements[jj].getAttribute("classEditor");//$NON-NLS-1$
						String classSupportName= confElements[jj].getAttribute("classSupport");//$NON-NLS-1$
//						String classTabItemName= confElements[jj].getAttribute("classCTablItem");//$NON-NLS-1$
//						String contributorName = confElements[jj].getContributor().getName();
						
						if (ClassEditorName == null || classSupportName == null)
							continue;
						Object classEditor=confElements[jj].createExecutableExtension("classEditor");
						Object classSupport=confElements[jj].createExecutableExtension("classSupport");
						Object classTabItem= (confElements[jj].createExecutableExtension("classCTablItem"));
						if(classEditor!=null && classTabItem!=null){
							CTabItemSupport onglet=((ICTabItem)classTabItem).creationTabItem(vue.getCTabFolder1(),SWT.NONE);
							onglet.getTabItem().setData(NOT_ENABLED);
							onglet.getController().addDeclencheCommandeControllerListener(getThis());
							listeGestionnaireEditorSupportAbon.put(classSupport,onglet);
						}
						

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
			previousSelection=vue.getCTabFolder1().getSelectionIndex();
			vue.getCTabFolder1().addSelectionListener(new SelectionAdapter() {
	            @Override
	            public void widgetSelected(final SelectionEvent e) {
	                Widget selectedItem = e.item;
	                Widget parentWidget = e.widget;
	                if (e.item instanceof CTabItem
	                    &&  e.widget instanceof CTabFolder) {
	                    boolean isEnabled =
	                        e.item.getData().equals(IS_ENABLED);
	                    if (!isEnabled) {
	                        // Attention au cas limite où la première sélection n'est pas permise
	                        ((CTabFolder) e.widget)
	                            .setSelection(previousSelection);
	                    } else {
	                        previousSelection =
	                            ((CTabFolder) e.widget).getSelectionIndex();
	                    }
	                }
	            }
	        });
		}
	}
}

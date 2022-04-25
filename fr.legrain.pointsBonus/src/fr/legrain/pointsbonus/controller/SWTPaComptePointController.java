package fr.legrain.pointsbonus.controller;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IElementComparer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import fr.legrain.articles.ecran.MessagesEcran;
import fr.legrain.facture.editor.FactureMultiPageEditor;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Tiers.SWTTiers;
import fr.legrain.gestCom.gestComBd.gestComBdPlugin;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.DeclencheCommandeControllerEvent;
import fr.legrain.gestCom.librairiesEcran.swt.IDetailController;
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
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartUtil;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.InfosVerifSaisie;
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
import fr.legrain.lib.gui.aide.ResultAide;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.lib.gui.grille.LgrViewerSupport;
import fr.legrain.libMessageLGR.LgrMess;
import fr.legrain.pointLgr.dao.SWTComptePoint;
import fr.legrain.pointLgr.dao.TaComptePoint;
import fr.legrain.pointLgr.dao.TaComptePointDAO;
import fr.legrain.pointLgr.dao.TaTypeMouvPoint;
import fr.legrain.pointLgr.dao.TaTypeMouvPointDAO;
import fr.legrain.pointsbonus.divers.ParamAfficheComptePoint;
import fr.legrain.pointsbonus.ecran.PaComptePointSWT;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.ecran.ParamAfficheTiers;
import fr.legrain.tiers.editor.TiersMultiPageEditor;


public class SWTPaComptePointController extends JPABaseControllerSWTStandard
implements RetourEcranListener,IDetailController {

	static Logger logger = Logger.getLogger(SWTPaComptePointController.class.getName());
	private PaComptePointSWT vue = null;
	private TaComptePointDAO dao = null;//new TaComptePointDAO();
	private ComboViewer viewerComboTypeMouvement;

	private Object ecranAppelant = null;
	private SWTComptePoint swtComptePoint;
	private SWTComptePoint swtOldComptePoint;
	private Realm realm;
	private DataBindingContext dbc;

	private Class  classModel = SWTComptePoint.class;
	private ModelGeneralObjet<SWTComptePoint,TaComptePointDAO,TaComptePoint> modelComptePoint = null;
	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private IObservableValue selectedComptePoint;
	private String[] listeChamp;
	private String nomClassController = this.getClass().getSimpleName();

	private TaTiers masterEntity = null;
	private TaTiersDAO masterDAO = null;

	private LgrDozerMapper<SWTComptePoint,TaComptePoint> mapperUIToModel  = new LgrDozerMapper<SWTComptePoint,TaComptePoint>();
	private TaComptePoint taComptePoint = null;

	private MapChangeListener changeListener = new MapChangeListener();

	private Integer idTiers = null;

	private BigDecimal totaux=new BigDecimal(0);
	private BigDecimal totauxAvis=new BigDecimal(0);
	private Integer nb=0;
	
	public SWTPaComptePointController(PaComptePointSWT vue) {
		this(vue,null);
	}

	public SWTPaComptePointController(PaComptePointSWT vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaComptePointDAO(getEm());
		modelComptePoint = new ModelGeneralObjet<SWTComptePoint,TaComptePointDAO,TaComptePoint>(dao,classModel);
		setVue(vue);

		// a faire avant l'initialisation du Binding
		vue.getGrille().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setSwtOldComptePoint();
			}
		});
		vue.getShell().addShellListener(this);

		// Branchement action annuler : empeche la fermeture automatique de la
		// fenetre sur ESC
		vue.getShell().addTraverseListener(new Traverse());

		initController();
	}

	public void bindComboLocalisationTVA() {
		TaTypeMouvPointDAO dao = new TaTypeMouvPointDAO();
		
		viewerComboTypeMouvement = new ComboViewer(vue.getTfTypeMouvement());
		
		viewerComboTypeMouvement.setComparer(new IElementComparer() {
			
			@Override
			public int hashCode(Object element) {
				return 0;
			}
			
			@Override
			public boolean equals(Object a, Object b) {
				// TODO Auto-generated method stub
				return ((TaTypeMouvPoint)a).getTypeMouv().equals(((TaTypeMouvPoint)b).getTypeMouv());
			}
		});

		viewerComboTypeMouvement.setContentProvider(ArrayContentProvider.getInstance());
		viewerComboTypeMouvement.setLabelProvider(new LabelProvider() {
		  @Override
		  public String getText(Object element) {
		    if (element instanceof TaTypeMouvPoint) {
		    	TaTypeMouvPoint t = (TaTypeMouvPoint) element;
		      return t.getTypeMouv();
		    }
		    return super.getText(element);
		  }
		});

		List<TaTypeMouvPoint> l = dao.selectAll();
		TaTypeMouvPoint[] tab = new TaTypeMouvPoint[l.size()];
		int i = 0;
		for (TaTypeMouvPoint taTypeMouvPoint : l) {
			tab[i] = taTypeMouvPoint;
			i++;
		}

		viewerComboTypeMouvement.setInput(tab); 
		
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
	public List<SWTComptePoint> IHMmodel() {
		ArrayList<TaComptePoint> ldao = new ArrayList<TaComptePoint>();
		LinkedList<SWTComptePoint> lswt = new LinkedList<SWTComptePoint>();
		
		if(masterEntity!=null ) {
			totaux=BigDecimal.ZERO;
			nb=0;
			ldao=(ArrayList<TaComptePoint>) dao.selectAllByTiersCompte(masterEntity.getIdTiers());
			modelComptePoint.setListeEntity(ldao);
//			LgrDozerMapper<TaComptePoint,SWTComptePoint> mapper = new LgrDozerMapper<TaComptePoint,SWTComptePoint>();
			for (TaComptePoint o : ldao) {
				SWTComptePoint t = null;
				
				t = (SWTComptePoint) modelComptePoint.getMapperModelToUI().map(o, SWTComptePoint.class);
				if(o.getDateAcquisition()==null)t.setDateAcquisition(new Date(0));
				if(o.getDatePeremption()==null)t.setDatePeremption(new Date(0));
				if(o.getDateUtilisation()==null)t.setDateUtilisation(new Date(0));
//				if(t.getDatePeremption()==null || !t.getDatePeremption().before(new Date())){
//					totaux=totaux.add(t.getPoint());
//					nb++;
//				}else 
					if(t.getDatePeremption()!=null && t.getDatePeremption().before(new Date()))	t.setLibelleEtat("Périmé");
				lswt.add(t);
			}

		}
		return lswt;
	}

	protected void initEtatBouton() {
		initEtatBouton(IHMmodel());

		//boolean trouve = contientDesEnregistrement(IHMmodel());
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
		vue.layout(true);
	}

	public void bindID(PaComptePointSWT paComptePointSWT, int idTiers ){
		masterEntity= masterDAO.findById(idTiers);
		bind(paComptePointSWT);
	}
	
	public void bindCode(PaComptePointSWT paComptePointSWT, String code ){
		masterEntity= masterDAO.findByCode(code);
		bind(paComptePointSWT);
	}
	public void bindSelection(PaComptePointSWT paComptePointSWT, SWTTiers selection ){
		if(selection!=null && selection.getIdTiers()!=null) {
			masterEntity =  masterDAO.findById(selection.getIdTiers());
		} else {
			masterEntity=null;
		}
		bind(paComptePointSWT);
		try {
			actRefresh();
		} catch (Exception e) {
			logger.error("", e);
		}
		initialiseInfosTiers();
	}
	
	public void bind(PaComptePointSWT paComptePointSWT) {
		try {
			if(masterDAO==null)masterDAO=new TaTiersDAO();
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			tableViewer = new LgrTableViewer(paComptePointSWT.getGrille());
			tableViewer.createTableCol(classModel,paComptePointSWT.getGrille(), nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE,-1);
			listeChamp = tableViewer.setListeChampGrille(nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE);


			// Set up data binding.
			LgrViewerSupport.bind(tableViewer, 
					new WritableList(IHMmodel(), classModel),
					BeanProperties.values(listeChamp)
			);

			selectedComptePoint = ViewersObservables.observeSingleSelection(tableViewer);

			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			tableViewer.selectionGrille(0);

			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);

			selectedComptePoint.getValue();
			bindingFormMaitreDetail(dbc, realm, selectedComptePoint,classModel);
			changementDeSelection();
			selectedComptePoint.addChangeListener(new IChangeListener() {

				public void handleChange(ChangeEvent event) {
					changementDeSelection();
				}

			});
			bindComboLocalisationTVA();
			changementDeSelection();
			initialiseInfosTiers();
			initTotaux();
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("", e);
		}
	}

	private void changementDeSelection() {
		if(selectedComptePoint!=null && selectedComptePoint.getValue()!=null) {
			if(((SWTComptePoint) selectedComptePoint.getValue()).getIdPoint()!=null) {
				taComptePoint = dao.findById(((SWTComptePoint) selectedComptePoint.getValue()).getIdPoint());
				viewerComboTypeMouvement.setSelection(new StructuredSelection(taComptePoint.getTaTypeMouvPoint()),true);
			}
			
			//null a tester ailleurs, car peut etre null en cas d'insertion
			fireChangementDeSelection(new ChangementDeSelectionEvent(SWTPaComptePointController.this));
		}
	}

	public Composite getVue() {
		return vue;
	}

	public ResultAffiche configPanel(ParamAffiche param) {
		if (param != null) {
			Map<String,String[]> map = dao.getParamWhereSQL();
			//			ibTaTable.ouvreDataset();
			if (((ParamAfficheComptePoint) param).getFocusDefautSWT() != null && !((ParamAfficheComptePoint) param).getFocusDefautSWT().isDisposed())
				((ParamAfficheComptePoint) param).getFocusDefautSWT().setFocus();
			else
				((ParamAfficheComptePoint) param).setFocusDefautSWT(vue
						.getGrille());
			vue.getLaTitreFormulaire().setText(
					((ParamAfficheComptePoint) param).getTitreFormulaire());
			vue.getLaTitreGrille().setText(
					((ParamAfficheComptePoint) param).getTitreGrille());
			vue.getLaTitreFenetre().setText(
					((ParamAfficheComptePoint) param).getSousTitre());


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
				this.idTiers=LibConversion.stringToInteger(param.getIdFiche());
				bindID(vue,idTiers);
			} else if(param.getCodeDocument()!=null && !param.getCodeDocument().equals("")) {
				bindCode(vue,param.getCodeDocument());
			} else if(param instanceof ParamAfficheTiers && ((ParamAfficheTiers)param).getIdTiers()!=null && !((ParamAfficheTiers)param).getIdTiers().equals("")) {
				this.idTiers=LibConversion.stringToInteger(((ParamAfficheTiers)param).getIdTiers());
				bindID(vue,idTiers);
			} else {
				bind(vue);
			}

			VerrouInterface.setVerrouille(false);
			setSwtOldComptePoint();

			if (param.getModeEcran() != null
					&& param.getModeEcran().compareTo(EnumModeObjet.C_MO_INSERTION) == 0) {
				try {
					actInserer();
				} catch (Exception e) {
					vue.getLaMessage().setText(e.getMessage());
					logger.error("", e);
				}
			} else {
				initEtatBouton();
			}
			//param.setFocus(ibTaTable.getFModeObjet().getFocusCourant());
		}
		return null;
	}
	
	protected void initVerifySaisie() {
		if (mapInfosVerifSaisie == null)
			mapInfosVerifSaisie = new HashMap<Control, InfosVerifSaisie>();
		
		mapInfosVerifSaisie.put(vue.getTfPoint(), new InfosVerifSaisie(new TaComptePoint(),Const.C_POINT,null));
		mapInfosVerifSaisie.put(vue.getTfTypeMouvement(), new InfosVerifSaisie(new TaComptePoint(),Const.C_TYPE_MOUVEMENT,null));
		mapInfosVerifSaisie.put(vue.getTfDatePeremption(), new InfosVerifSaisie(new TaComptePoint(),Const.C_DATE_PEREMPTION,null));
		mapInfosVerifSaisie.put(vue.getTfCommentaire(), new InfosVerifSaisie(new TaComptePoint(),Const.C_COMMENTAIRE,null));


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

		vue.getTfPoint().setToolTipText(Const.C_POINT);
		vue.getTfTypeMouvement().setToolTipText(Const.C_TYPE_MOUVEMENT);
		vue.getTfDatePeremption().setToolTipText(Const.C_DATE_PEREMPTION);
		vue.getTfCodeDocument().setToolTipText(Const.C_CODE_DOCUMENT);
		vue.getTfCommentaire().setToolTipText(Const.C_COMMENTAIRE);

		mapComposantChamps.put(vue.getTfPoint(), Const.C_POINT);
		mapComposantChamps.put(vue.getTfTypeMouvement(), Const.C_TYPE_MOUVEMENT);
		mapComposantChamps.put(vue.getTfDatePeremption(),Const.C_DATE_PEREMPTION);
		mapComposantChamps.put(vue.getTfCodeDocument(),Const.C_CODE_DOCUMENT);
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

		if (mapInitFocus == null)
			mapInitFocus = new LinkedHashMap<ModeObjet.EnumModeObjet, Control>();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION, vue
				.getTfPoint());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue
				.getTfPoint());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION, vue
				.getGrille());

		activeModifytListener();
		
		vue.getGrille().addMouseListener(new MouseAdapter(){

			public void mouseDoubleClick(MouseEvent e) {
				String valeurIdentifiant=null;
				String idEditor = null;
					valeurIdentifiant =taComptePoint.getCodeDocument(); 
					idEditor = FactureMultiPageEditor.ID_EDITOR;			
					if(valeurIdentifiant!=null && !valeurIdentifiant.equals(""))
						LgrPartUtil.ouvreDocument(valeurIdentifiant,idEditor);
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

		vue.getBtnTiers().addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				actSelectionTiers();
			}
		});
		vue.getBtnCalcul().addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				actCalculPoint(true);
			}
		});
	}
	
	public void actCalculPoint(boolean refresh){
		// TODO Auto-generated method stub
		if(dao!=null && masterEntity!=null)
			dao.calculPointUtilise(masterEntity.getIdTiers(), null);
		try {
			if(refresh)actRefresh();
		} catch (Exception e1) {
			logger.error("", e1);
		}
	}
	protected void actSelectionTiers()  {
		try{
			if(masterEntity!=null && masterEntity.getIdTiers()!=0){
				ouvreFiche(String.valueOf(masterEntity.getIdTiers()), TiersMultiPageEditor.ID_EDITOR,null,false);
				
			}
		}catch (Exception e) {
			logger.error("",e);
		}	
	}

	public SWTPaComptePointController getThis() {
		return this;
	}

	@Override
	public boolean onClose() throws ExceptLgr {
		boolean retour = true;
		VerrouInterface.setVerrouille(true);
		//switch (ibTaTable.getFModeObjet().getMode()) {
		switch (dao.getModeObjet().getMode()) {
		case C_MO_INSERTION:
		case C_MO_EDITION:
			if (MessageDialog.openQuestion(vue.getShell(), "Attention !","Voulez-vous enregistrer les points en cours")) {

				try {
					actEnregistrer();
				} catch (Exception e) {
					vue.getLaMessage().setText(e.getMessage());
					logger.error("", e);
				}
			} else {
				fireAnnuleTout(new AnnuleToutEvent(this,true));
			}
			//ibTaTable.annuler();

			break;
		case C_MO_CONSULTATION:
			break;
		default:
			break;
		}

		if (retour) {
			if (ecranAppelant instanceof SWTPaAideControllerSWT) {
				setListeEntity(getModelComptePoint().remplirListe());
				dao.initValeurIdTable(taComptePoint);
				fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
						dao.getChampIdEntite(), dao.getValeurIdTable(),
						selectedComptePoint.getValue())));

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
			boolean continuer=true;
			VerrouInterface.setVerrouille(true);
			masterDAO.verifAutoriseModification(masterEntity);
			setSwtOldComptePoint();
			if(LgrMess.isDOSSIER_EN_RESEAU()){
				DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
				fireDeclencheCommandeController(e);
				continuer=masterDAO.dataSetEnModif();
			}
			if(continuer){
//				setMasterEntity(masterDAO.findById(masterEntity.getIdTiers()));
				swtComptePoint = new SWTComptePoint();
				swtComptePoint.setIdTiers(idTiers);
				swtComptePoint.setIdTypeMouv(1);
				dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_INSERTION);
				taComptePoint = new TaComptePoint();

				List l = IHMmodel();
				l.add(swtComptePoint);
				writableList = new WritableList(realm, l, classModel);
				tableViewer.setInput(writableList);
				tableViewer.refresh();
				tableViewer.setSelection(new StructuredSelection(swtComptePoint));
				initEtatBouton();

				try {
					DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
					fireDeclencheCommandeController(e);
				} catch (Exception e) {
					logger.error("",e);
				}
			}
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
			if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
				if(!LgrMess.isDOSSIER_EN_RESEAU()){
					setSwtOldComptePoint();
					taComptePoint = dao.findById(((SWTComptePoint)selectedComptePoint.getValue()).getIdPoint());				
				}else{
					if(!setSwtOldComptePointRefresh())throw new Exception();
				}
				dao.modifier(taComptePoint);
				initEtatBouton();
			}
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		}
	}

	public boolean setSwtOldComptePointRefresh() {
		try {	
			if (((SWTComptePoint)selectedComptePoint.getValue())!=null){
				TaComptePoint taOld =dao.findById(taComptePoint.getIdPoint());
				taOld=dao.refresh(taOld);
				taComptePoint=taOld;
				//fireChangementMaster(new ChangementMasterEntityEvent(this,taComptePoint,true));
				this.swtOldComptePoint=SWTComptePoint.copy(((SWTComptePoint)selectedComptePoint.getValue()));
			}
			return true;
		} catch (Exception e) {
			return false;
		}		
	}
	@Override
	protected void actSupprimer() throws Exception {
		EntityTransaction transaction = dao.getEntityManager().getTransaction();		
		try {
			boolean continuer=true;
			VerrouInterface.setVerrouille(true);
			if(isUtilise())MessageDialog.openInformation(vue.getShell(), MessagesEcran
					.getString("Message.Attention"), MessagesEcran
					.getString("Message.suppression"));
			else
				if(LgrMess.isDOSSIER_EN_RESEAU()){
					DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
					fireDeclencheCommandeController(e);
					continuer=masterDAO.dataSetEnModif();
				}

			if(continuer){
				setMasterEntity(masterDAO.findById(masterEntity.getIdTiers()));
				if (MessageDialog.openConfirm(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("Prix.Message.Supprimer"))) {				
					dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_SUPPRESSION);
					try {
						DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
						fireDeclencheCommandeController(e);
					} catch (Exception e) {
						logger.error("",e);
					}

					dao.begin(transaction);
					dao.supprimer(taComptePoint);
					dao.commit(transaction);
					taComptePoint=null;
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
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
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
		
	}

	@Override
	protected void actImprimer() throws Exception {

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
				Shell s = new Shell(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(),
						LgrShellUtil.styleLgr);
				PaAideSWT paAide = new PaAideSWT(s, SWT.NONE);
				SWTPaAideControllerSWT paAideController = new SWTPaAideControllerSWT(
						paAide);
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

					// Parametrage de l'ecran d'aide principal
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
		if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
				|| (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
			ctrlTousLesChampsAvantEnregistrementSWT();
		}
		return null;
	}

	public IStatus validateUIField(String nomChamp,Object value) {
		String validationContext = "COMPTE_POINT";
		try {
			IStatus s = null;
			if(nomChamp.equals(Const.C_TYPE_MOUVEMENT)) {
				return new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
//				TaTypeMouvPointDAO dao = new TaTypeMouvPointDAO(getEm());
//				
//				dao.setModeObjet(getDao().getModeObjet());
//				TaTypeMouvPoint f = new TaTypeMouvPoint();
//				PropertyUtils.setSimpleProperty(f, Const.C_TYPE_MOUVEMENT, value);
//				s = dao.validate(f,Const.C_TYPE_MOUVEMENT,validationContext);
//
//				if(s.getSeverity()!=IStatus.ERROR ){
//					//f = dao.findByCode((String)value);
//					//if(taComptePoint!=null)taComptePoint.setTaTypeMouvPoint(f);
//				}
//				dao = null;
			}else  {
				TaComptePoint u = new TaComptePoint();
				u.setTaTiers(masterEntity);
				PropertyUtils.setSimpleProperty(u, nomChamp, value);
				s = dao.validate(u,nomChamp,validationContext);
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

					if(!((IStructuredSelection)viewerComboTypeMouvement.getSelection()).isEmpty()) {
						TaTypeMouvPointDAO doaType=new TaTypeMouvPointDAO();
						String typeMouv = ((TaTypeMouvPoint)((IStructuredSelection)viewerComboTypeMouvement.getSelection()).getFirstElement()).getTypeMouv();			
						taComptePoint.setTaTypeMouvPoint(doaType.findByCode(typeMouv));
					}
					if(declanchementExterne) {
						ctrlTousLesChampsAvantEnregistrementSWT();
					}
					LgrDozerMapper<SWTComptePoint, TaComptePoint> mapper= new LgrDozerMapper<SWTComptePoint, TaComptePoint>();
					if(declanchementExterne) {
						mapper.map(((SWTComptePoint)selectedComptePoint.getValue()), taComptePoint);
					}
					
					taComptePoint.setTaTiers(masterEntity);
						

					fireEnregistreTout(new AnnuleToutEvent(this,true));

					if(!enregistreToutEnCours) {
						dao.begin(transaction);
						if((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)){	
							taComptePoint=dao.enregistrerMerge(taComptePoint);
						}
						else taComptePoint=dao.enregistrerMerge(taComptePoint);

						dao.commit(transaction);
						
						((SWTComptePoint)selectedComptePoint.getValue()).setIdPoint(taComptePoint.getIdPoint());

						initEtatBouton();
						initTotaux();
					} 
					try {
						DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_ENREGISTRER_ID);
						fireDeclencheCommandeController(e);
					} catch (Exception e) {
						logger.error("",e);
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

//	}

	public void initEtatComposant() {
		try {
			vue.getTfCodeDocument().setEditable(false);
			vue.getTfCodeEntite().setEditable(false);
			vue.getTfEntreprise().setEditable(false);
			vue.getTfNomTiers().setEditable(false);
			vue.getTfPrenomTiers().setEditable(false);
			changeCouleur(vue);
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
		}
	}

	public boolean isUtilise(){
		return (((SWTComptePoint)selectedComptePoint.getValue()).getIdPoint()!=null && 
				!dao.recordModifiable(dao.getNomTable(),
						((SWTComptePoint)selectedComptePoint.getValue()).getIdPoint()))||
						!masterDAO.autoriseModification(masterEntity);		
	}

	public SWTComptePoint getSwtOldComptePoint() {
		return swtOldComptePoint;
	}

	public void setSwtOldAdresse(SWTComptePoint swtOldAdresse) {
		this.swtOldComptePoint = swtOldAdresse;
	}

	public void setSwtOldComptePoint() {
		if (selectedComptePoint.getValue() != null)
			this.swtOldComptePoint = SWTComptePoint.copy((SWTComptePoint) selectedComptePoint.getValue());
		else {
			if (tableViewer.selectionGrille(0)){
				this.swtOldComptePoint = SWTComptePoint.copy((SWTComptePoint) selectedComptePoint.getValue());
				tableViewer.setSelection(new StructuredSelection(
						(SWTComptePoint) selectedComptePoint.getValue()), true);
			}
		}
	}

	public void setVue(PaComptePointSWT vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();
		mapComposantDecoratedField.put(vue.getTfPoint(), vue.getFieldPoint());
		mapComposantDecoratedField.put(vue.getTfTypeMouvement(), vue.getFieldTypeMouvement());
		mapComposantDecoratedField.put(vue.getTfDatePeremption(), vue.getFieldDatePeremption());
		mapComposantDecoratedField.put(vue.getTfCommentaire(), vue.getFieldCommentaire());

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
		actCalculPoint(false);
		//		//repositionnement sur la valeur courante
		int idActuel = 0;
		if (taComptePoint!=null) { //enregistrement en cours de modification/insertion
			idActuel = taComptePoint.getIdPoint();
		} 
//		else 
//			if(selectedComptePoint!=null && (SWTComptePoint) selectedComptePoint.getValue()!=null) {
//				idActuel = ((SWTComptePoint) selectedComptePoint.getValue()).getIdPoint();
//			}
		//rafraichissement des valeurs dans la grille
		//tableViewer.setInput(null);
		writableList = new WritableList(realm,IHMmodel(), classModel);
		tableViewer.setInput(writableList);


		if(idActuel!=0) {
			tableViewer.setSelection(new StructuredSelection(recherche(Const.C_ID_POINT, idActuel)));
		}else
			tableViewer.selectionGrille(0);
		initTotaux();
	}

	public void initTotaux(){

		for (TaComptePoint t : modelComptePoint.getListeEntity()) {
			if(t.getDatePeremption()==null || !t.getDatePeremption().before(new Date()) ){
				if(t.getUtilise_avis()!=null)totauxAvis=totauxAvis.add(t.getPoint().subtract(t.getUtilise_avis()));
				if(t.getUtilise_facture()!=null)totaux=totaux.add(t.getPoint().subtract(t.getUtilise_facture()));
				nb++;
			}
		}
		vue.getTfMT_AVIS_CALC().setText(LibConversion.bigDecimalToString(totauxAvis));
		vue.getTfMT_HT_CALC().setText(LibConversion.bigDecimalToString(totaux));
		vue.getTfNbLigne().setText(LibConversion.integerToString(nb));
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

	public ModelGeneralObjet<SWTComptePoint,TaComptePointDAO,TaComptePoint> getModelComptePoint() {
		return modelComptePoint;
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

	public TaComptePointDAO getDao() {
		return dao;
	}

	
	
	public void initialiseInfosTiers(){
		vue.getLaCodeTiers().setText("");
		vue.getTfCodeEntite().setText("");
		vue.getTfEntreprise().setText("");
		vue.getTfNomTiers().setText("");
		vue.getTfPrenomTiers().setText("");

		if(masterEntity!=null ){
			vue.getLaCodeTiers().setText("Code tiers : "+masterEntity.getCodeTiers());

			if(masterEntity.getTaTEntite()!=null)
				vue.getTfCodeEntite().setText(masterEntity.getTaTEntite().getCodeTEntite());
			if(masterEntity.getTaEntreprise()!=null)
				vue.getTfEntreprise().setText(masterEntity.getTaEntreprise().getNomEntreprise());

			if(masterEntity.getNomTiers()!=null)
				vue.getTfNomTiers().setText(masterEntity.getNomTiers());
			if(masterEntity.getPrenomTiers()!=null)
				vue.getTfPrenomTiers().setText(masterEntity.getPrenomTiers());

		}
	}

	
}

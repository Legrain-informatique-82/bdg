package fr.legrain.pointsbonus.controller;

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
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.articles.ecran.MessagesEcran;
import fr.legrain.articles.editor.ArticleMultiPageEditor;
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
import fr.legrain.pointLgr.dao.SWTArticlePoint;
import fr.legrain.pointLgr.dao.TaArticlePoint;
import fr.legrain.pointLgr.dao.TaArticlePointDAO;
import fr.legrain.pointLgr.dao.TaComptePoint;
import fr.legrain.pointLgr.dao.TaTypeMouvPoint;
import fr.legrain.pointLgr.dao.TaTypeMouvPointDAO;
import fr.legrain.pointsbonus.divers.ParamAfficheArticePoint;
import fr.legrain.pointsbonus.ecran.PaArticlePointSWT;
import fr.legrain.tiers.ecran.ParamAfficheTiers;


public class SWTPaArticlePointController extends JPABaseControllerSWTStandard
implements RetourEcranListener,IDetailController {

	static Logger logger = Logger.getLogger(SWTPaArticlePointController.class.getName());
	private PaArticlePointSWT vue = null;
	private TaArticlePointDAO dao = null;//new TaComptePointDAO();
	private ComboViewer viewerComboTypeMouvement;

	private Object ecranAppelant = null;
	private SWTArticlePoint swtArticlePoint;
	private SWTArticlePoint swtOldArticlePoint;
	private Realm realm;
	private DataBindingContext dbc;

	private Class  classModel = SWTArticlePoint.class;
	private ModelGeneralObjet<SWTArticlePoint,TaArticlePointDAO,TaArticlePoint> modelComptePoint = null;
	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private IObservableValue selectedArticlePoint;
	private String[] listeChamp;
	private String nomClassController = this.getClass().getSimpleName();

	private TaArticle masterEntity = null;
	private TaArticleDAO masterDAO = null;

	private LgrDozerMapper<SWTArticlePoint,TaArticlePoint> mapperUIToModel  = new LgrDozerMapper<SWTArticlePoint,TaArticlePoint>();
	private TaArticlePoint taArticlePoint = null;

	private MapChangeListener changeListener = new MapChangeListener();

	private Integer idArticle = null;

	public SWTPaArticlePointController(PaArticlePointSWT vue) {
		this(vue,null);
	}

	public SWTPaArticlePointController(PaArticlePointSWT vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaArticlePointDAO(getEm());
		modelComptePoint = new ModelGeneralObjet<SWTArticlePoint,TaArticlePointDAO,TaArticlePoint>(dao,classModel);
		setVue(vue);

		// a faire avant l'initialisation du Binding
		vue.getGrille().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setSwtOldArticlePoint();
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
	public List<SWTArticlePoint> IHMmodel() {
		TaArticlePoint objet = null;
		LinkedList<SWTArticlePoint> lswt = new LinkedList<SWTArticlePoint>();

		if(masterEntity!=null ) {
		objet=dao.findByArticle(masterEntity.getIdArticle());	
//			ldao=(ArrayList<TaArticlePoint>) dao.findByArticle(masterEntity.getIdArticle());
//
			LgrDozerMapper<TaArticlePoint,SWTArticlePoint> mapper = new LgrDozerMapper<TaArticlePoint,SWTArticlePoint>();
//			for (TaArticlePoint o : ldao) {
			if(objet!=null){
				SWTArticlePoint t = null;
				t = (SWTArticlePoint) mapper.map(objet, SWTArticlePoint.class);
				lswt.add(t);
//
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

	public void bindID(PaArticlePointSWT paComptePointSWT, int idTiers ){
		masterEntity= masterDAO.findById(idTiers);
		bind(paComptePointSWT);
	}
	
	public void bindCode(PaArticlePointSWT paComptePointSWT, String code ){
		masterEntity= masterDAO.findByCode(code);
		bind(paComptePointSWT);
	}
	public void bindSelection(PaArticlePointSWT paComptePointSWT, SWTTiers selection ){
		if(selection!=null && selection.getIdTiers()!=null) {
			masterEntity =  masterDAO.findById(selection.getIdTiers());
		} else {
			masterEntity=null;
		}
		bind(paComptePointSWT);
		
	}
	
	public void bind(PaArticlePointSWT paComptePointSWT) {
		try {
			if(masterDAO==null)masterDAO=new TaArticleDAO();
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

			selectedArticlePoint = ViewersObservables.observeSingleSelection(tableViewer);

			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			tableViewer.selectionGrille(0);

			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);

			selectedArticlePoint.getValue();
			bindingFormMaitreDetail(dbc, realm, selectedArticlePoint,classModel);
			changementDeSelection();
			selectedArticlePoint.addChangeListener(new IChangeListener() {

				public void handleChange(ChangeEvent event) {
					changementDeSelection();
				}

			});
			bindComboLocalisationTVA();
			changementDeSelection();
			initialiseInfosTiers();
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("", e);
		}
	}

	private void changementDeSelection() {
		if(selectedArticlePoint!=null && selectedArticlePoint.getValue()!=null) {
			if(((SWTArticlePoint) selectedArticlePoint.getValue()).getIdArticlePoint()!=null) {
				taArticlePoint = dao.findById(((SWTArticlePoint) selectedArticlePoint.getValue()).getIdArticlePoint());
				if(viewerComboTypeMouvement!=null)viewerComboTypeMouvement.setSelection(new StructuredSelection(taArticlePoint.getTaTypeMouvPoint()),true);
			}
			
			//null a tester ailleurs, car peut etre null en cas d'insertion
			fireChangementDeSelection(new ChangementDeSelectionEvent(SWTPaArticlePointController.this));
		}
	}

	public Composite getVue() {
		return vue;
	}

	public ResultAffiche configPanel(ParamAffiche param) {
		if (param != null) {
			Map<String,String[]> map = dao.getParamWhereSQL();
			//			ibTaTable.ouvreDataset();
			if (((ParamAfficheArticePoint) param).getFocusDefautSWT() != null && !((ParamAfficheArticePoint) param).getFocusDefautSWT().isDisposed())
				((ParamAfficheArticePoint) param).getFocusDefautSWT().setFocus();
			else
				((ParamAfficheArticePoint) param).setFocusDefautSWT(vue
						.getGrille());
			vue.getLaTitreFormulaire().setText(
					((ParamAfficheArticePoint) param).getTitreFormulaire());
			vue.getLaTitreGrille().setText(
					((ParamAfficheArticePoint) param).getTitreGrille());
			vue.getLaTitreFenetre().setText(
					((ParamAfficheArticePoint) param).getSousTitre());


			if (param.getEcranAppelant() != null) {
				ecranAppelant = param.getEcranAppelant();
			}
			if(tableViewer==null) {
				if(param.getSelection()!=null 
						&& !param.getSelection().isEmpty()
						&& param.getSelection() instanceof IStructuredSelection
						&& ((IStructuredSelection)param.getSelection()).getFirstElement()!=null
						&& ((IStructuredSelection)param.getSelection()).getFirstElement() instanceof SWTTiers
						) {
					bindSelection(vue,(SWTTiers)((IStructuredSelection)param.getSelection()).getFirstElement());
				} else if(param.getIdFiche()!=null &&  !param.getIdFiche().equals("")) {
					this.idArticle=LibConversion.stringToInteger(param.getIdFiche());
					bindID(vue,idArticle);
				} else if(param.getCodeDocument()!=null && !param.getCodeDocument().equals("")) {
					bindCode(vue,param.getCodeDocument());
				} else if(param instanceof ParamAfficheTiers && ((ParamAfficheTiers)param).getIdTiers()!=null && !((ParamAfficheTiers)param).getIdTiers().equals("")) {
					this.idArticle=LibConversion.stringToInteger(((ParamAfficheTiers)param).getIdTiers());
					bindID(vue,idArticle);
				} else {
					bind(vue);
				}

			} else {
				try {
					taArticlePoint=null;
					if(selectedArticlePoint!=null)selectedArticlePoint.setValue(null);
					actRefresh();
				} catch (Exception e) {
					logger.error("",e);
				}
			}
			
			VerrouInterface.setVerrouille(false);
			setSwtOldArticlePoint();

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
		mapInfosVerifSaisie.put(vue.getTfDateDebutPeriode(), new InfosVerifSaisie(new TaComptePoint(),Const.C_DATE_PEREMPTION,null));
		mapInfosVerifSaisie.put(vue.getTfIndice(), new InfosVerifSaisie(new TaComptePoint(),Const.C_COMMENTAIRE,null));


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

		vue.getTfPoint().setToolTipText(Const.C_POINTS);
		vue.getTfTypeMouvement().setToolTipText(Const.C_TYPE_MOUVEMENT);
		vue.getTfDateDebutPeriode().setToolTipText(Const.C_DEBUT_PERIODE);
		vue.getTfDateFinPeriode().setToolTipText(Const.C_FIN_PERIODE);
		vue.getTfIndice().setToolTipText(Const.C_INDICE);
		vue.getTfPrixReference().setToolTipText(Const.C_PRIX_REFERENCE);

		mapComposantChamps.put(vue.getTfPoint(), Const.C_POINTS);
		mapComposantChamps.put(vue.getTfTypeMouvement(), Const.C_TYPE_MOUVEMENT);
		mapComposantChamps.put(vue.getTfDateDebutPeriode(),Const.C_DEBUT_PERIODE);
		mapComposantChamps.put(vue.getTfDateFinPeriode(),Const.C_FIN_PERIODE);
		mapComposantChamps.put(vue.getTfIndice(),Const.C_INDICE);
		mapComposantChamps.put(vue.getTfPrixReference(),Const.C_PRIX_REFERENCE);
		
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

		vue.getBtnArticle().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				actSelectionArticle();
			}
		});
	}
	protected void actSelectionArticle()  {
		try{
			if(masterEntity!=null && masterEntity.getIdArticle()!=0){
				ouvreFiche(String.valueOf(masterEntity.getIdArticle()), ArticleMultiPageEditor.ID_EDITOR,null,false);
				
			}
		}catch (Exception e) {
			logger.error("",e);
		}	
	}
	public SWTPaArticlePointController getThis() {
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
				dao.initValeurIdTable(taArticlePoint);
				fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
						dao.getChampIdEntite(), dao.getValeurIdTable(),
						selectedArticlePoint.getValue())));

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
			setSwtOldArticlePoint();
			if(LgrMess.isDOSSIER_EN_RESEAU()){
				DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
				fireDeclencheCommandeController(e);
				continuer=masterDAO.dataSetEnModif();
			}
			if(continuer){
//				setMasterEntity(masterDAO.findById(masterEntity.getIdTiers()));
				swtArticlePoint = new SWTArticlePoint();
				swtArticlePoint.setIdArticle(idArticle);
				swtArticlePoint.setIdTypeMouv(1);
				dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_INSERTION);
				taArticlePoint = new TaArticlePoint();

				List l = IHMmodel();
				l.add(swtArticlePoint);
				writableList = new WritableList(realm, l, classModel);
				tableViewer.setInput(writableList);
				tableViewer.refresh();
				tableViewer.setSelection(new StructuredSelection(swtArticlePoint));
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
					setSwtOldArticlePoint();
					taArticlePoint = dao.findById(((SWTArticlePoint)selectedArticlePoint.getValue()).getIdArticlePoint());				
				}else{
					if(!setSwtOldComptePointRefresh())throw new Exception();
				}
				dao.modifier(taArticlePoint);
				initEtatBouton();
			}
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		}
	}

	public boolean setSwtOldComptePointRefresh() {
		try {	
			if (((SWTArticlePoint)selectedArticlePoint.getValue())!=null){
				TaArticlePoint taOld =dao.findById(taArticlePoint.getIdArticlePoint());
				taOld=dao.refresh(taOld);
				taArticlePoint=taOld;
				//fireChangementMaster(new ChangementMasterEntityEvent(this,taComptePoint,true));
				this.swtOldArticlePoint=SWTArticlePoint.copy(((SWTArticlePoint)selectedArticlePoint.getValue()));
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
				setMasterEntity(masterDAO.findById(masterEntity.getIdArticle()));
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
					dao.supprimer(taArticlePoint);
					dao.commit(transaction);
					taArticlePoint=null;
					Object suivant=tableViewer.getElementAt(tableViewer.getTable().getSelectionIndex()+1);
					modelComptePoint.removeEntity(taArticlePoint);			
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
		String validationContext = "ARTICLE_POINT";
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
				TaArticlePoint u = new TaArticlePoint();
				u.setTaArticle(masterEntity);
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
						taArticlePoint.setTaTypeMouvPoint(doaType.findByCode(typeMouv));
					}
					if(declanchementExterne) {
						ctrlTousLesChampsAvantEnregistrementSWT();
					}
					LgrDozerMapper<SWTArticlePoint, TaArticlePoint> mapper =new LgrDozerMapper<SWTArticlePoint, TaArticlePoint>();
					if(declanchementExterne) {
						mapper.map(((SWTArticlePoint)selectedArticlePoint.getValue()), taArticlePoint);
					}
					
					taArticlePoint.setTaArticle(masterEntity);
					
					fireEnregistreTout(new AnnuleToutEvent(this,true));

					if(!enregistreToutEnCours) {
						dao.begin(transaction);
						if((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)){	
							taArticlePoint=dao.enregistrerMerge(taArticlePoint);
						}
						else taArticlePoint=dao.enregistrerMerge(taArticlePoint);

						dao.commit(transaction);
						
						((SWTArticlePoint)selectedArticlePoint.getValue()).setIdArticlePoint(taArticlePoint.getIdArticlePoint());
//						LgrDozerMapper<TaArticlePoint,SWTArticlePoint > mapperUI =new LgrDozerMapper<TaArticlePoint,SWTArticlePoint>();
//						mapperUI.map(taArticlePoint,((SWTArticlePoint)selectedArticlePoint.getValue()));
						try {
							DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_ENREGISTRER_ID);
							fireDeclencheCommandeController(e);
						} catch (Exception e) {
							logger.error("",e);
						}
						actRefresh();
						initEtatBouton();

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
			vue.getTfLibelleArticle().setEditable(false);
			changeCouleur(vue);
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
		}
	}

	public boolean isUtilise(){
		return (((SWTArticlePoint)selectedArticlePoint.getValue()).getIdArticlePoint()!=null && 
				!dao.recordModifiable(dao.getNomTable(),
						((SWTArticlePoint)selectedArticlePoint.getValue()).getIdArticlePoint()))||
						!masterDAO.autoriseModification(masterEntity);		
	}

	public SWTArticlePoint getSwtOldArticlePoint() {
		return swtOldArticlePoint;
	}

	public void setSwtOldAdresse(SWTArticlePoint swtOldAdresse) {
		this.swtOldArticlePoint = swtOldAdresse;
	}

	public void setSwtOldArticlePoint() {
		if (selectedArticlePoint.getValue() != null)
			this.swtOldArticlePoint = SWTArticlePoint.copy((SWTArticlePoint) selectedArticlePoint.getValue());
		else {
			if (tableViewer.selectionGrille(0)){
				this.swtOldArticlePoint = SWTArticlePoint.copy((SWTArticlePoint) selectedArticlePoint.getValue());
				tableViewer.setSelection(new StructuredSelection(
						(SWTArticlePoint) selectedArticlePoint.getValue()), true);
			}
		}
	}

	public void setVue(PaArticlePointSWT vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();
		mapComposantDecoratedField.put(vue.getTfPoint(), vue.getFieldPoint());
		mapComposantDecoratedField.put(vue.getTfTypeMouvement(), vue.getFieldTypeMouvement());
		mapComposantDecoratedField.put(vue.getTfDateDebutPeriode(), vue.getFieldDateDebutPeriode());
		mapComposantDecoratedField.put(vue.getTfIndice(), vue.getFieldIndice());

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

		//		//repositionnement sur la valeur courante
		int idActuel = 0;
		if (taArticlePoint!=null) { //enregistrement en cours de modification/insertion
			idActuel = taArticlePoint.getIdArticlePoint();
		}
//		else 
//			if(selectedArticlePoint!=null && (SWTArticlePoint) selectedArticlePoint.getValue()!=null) {
//				idActuel = ((SWTArticlePoint) selectedArticlePoint.getValue()).getIdArticlePoint();
//			}
		//rafraichissement des valeurs dans la grille
		//tableViewer.setInput(null);
		writableList = new WritableList(realm,IHMmodel(), classModel);
		tableViewer.setInput(writableList);


		if(idActuel!=0) {
			tableViewer.setSelection(new StructuredSelection(recherche(Const.C_ID_ARTICLE_POINT, idActuel)));
		}else
			tableViewer.selectionGrille(0);	
		initialiseInfosTiers();
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

	public ModelGeneralObjet<SWTArticlePoint,TaArticlePointDAO,TaArticlePoint> getModelComptePoint() {
		return modelComptePoint;
	}

	public TaArticle getMasterEntity() {
		return masterEntity;
	}

	public void setMasterEntity(TaArticle masterEntity) {
		this.masterEntity = masterEntity;
	}

	public TaArticleDAO getMasterDAO() {
		return masterDAO;
	}

	public void setMasterDAO(TaArticleDAO masterDAO) {
		this.masterDAO = masterDAO;
	}

	public TaArticlePointDAO getDao() {
		return dao;
	}

	
	
	public void initialiseInfosTiers(){
		vue.getLaCodeArticle().setText("");
		vue.getTfLibelleArticle().setText("");

		if(masterEntity!=null ){
			vue.getLaCodeArticle().setText("Code article : "+masterEntity.getCodeArticle());

			if(masterEntity.getLibellecArticle()!=null)
				vue.getTfLibelleArticle().setText(masterEntity.getLibellecArticle());
//			if(masterEntity.getTaEntreprise()!=null)
//				vue.getTfEntreprise().setText(masterEntity.getTaEntreprise().getNomEntreprise());
//
//			if(masterEntity.getNomTiers()!=null)
//				vue.getTfNomTiers().setText(masterEntity.getNomTiers());
//			if(masterEntity.getPrenomTiers()!=null)
//				vue.getTfPrenomTiers().setText(masterEntity.getPrenomTiers());

		}
	}

	
}

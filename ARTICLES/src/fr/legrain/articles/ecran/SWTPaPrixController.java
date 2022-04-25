package fr.legrain.articles.ecran;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.MathContext;
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
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
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
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.articles.dao.TaPrix;
import fr.legrain.articles.dao.TaPrixDAO;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.articles.dao.TaUniteDAO;
import fr.legrain.articles.editor.EditorInputPrix;
import fr.legrain.articles.editor.EditorInputUnite;
import fr.legrain.articles.editor.EditorPrix;
import fr.legrain.articles.editor.EditorUnite;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Articles.SWTCiblePrix;
import fr.legrain.gestCom.Module_Articles.SWTFamille;
import fr.legrain.gestCom.Module_Articles.SWTPrix;
import fr.legrain.gestCom.Module_Articles.SWTUnite;
import fr.legrain.gestCom.Module_Tiers.SWTTTarif;
import fr.legrain.gestCom.Module_Tiers.SWTTiers;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.DeclencheCommandeControllerEvent;
import fr.legrain.gestCom.librairiesEcran.swt.IDetailController;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
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
import fr.legrain.lib.data.ModelObject;
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
import fr.legrain.tiers.dao.TaTTarif;
import fr.legrain.tiers.dao.TaTTarifDAO;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.ecran.PaTiersSWT;
import fr.legrain.tiers.ecran.PaTypeTarifSWT;
import fr.legrain.tiers.ecran.ParamAfficheTiers;
import fr.legrain.tiers.ecran.ParamAfficheTypeTarif;
import fr.legrain.tiers.ecran.SWTPaTiersController;
import fr.legrain.tiers.ecran.SWTPaTypeTarifController;
import fr.legrain.tiers.editor.EditorInputTiers;
import fr.legrain.tiers.editor.EditorInputTypeTarif;
import fr.legrain.tiers.editor.EditorTiers;
import fr.legrain.tiers.editor.EditorTypeTarif;


public class SWTPaPrixController extends JPABaseControllerSWTStandard
implements RetourEcranListener,IDetailController {

	static Logger logger = Logger.getLogger(SWTPaPrixController.class.getName());
	private PaPrixSWT vue = null;
	private TaPrixDAO dao = null;//new TaPrixDAO();
	private String idArticle = null;

	private Object ecranAppelant = null;
	private SWTPrix swtPrix;
	private SWTPrix swtOldPrix;
	private Realm realm;
	private DataBindingContext dbc;

	private Class classModel = SWTPrix.class;
	private ModelGeneralObjet<SWTPrix,TaPrixDAO,TaPrix> modelPrix = null;//new ModelGeneralObjet<SWTPrix,TaPrixDAO,TaPrix>(dao,classModel);
	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private IObservableValue selectedPrix;
	private String[] listeChamp;
	private String nomClassController = this.getClass().getSimpleName();
	
	private LgrTableViewer tableViewerCible;
	private WritableList writableListCible;
	private String[] listeChampCible;
	private IObservableValue selectedCiblePrix;

	private TaArticle masterEntity = null;
	private TaArticleDAO masterDAO = null;

	private TaPrix taPrix = null;

	private MapChangeListener changeListener = new MapChangeListener();
	
	public static final String C_COMMAND_AJOUTER_CIBLE_PRIX_ID = "fr.legrain.gestionCommerciale.article.ajouter.cibleprix";
	public static final String C_COMMAND_SUPPRIMER_CIBLE_PRIX_ID = "fr.legrain.gestionCommerciale.article.supprimer.cibleprix";
	private HandlerAjouterCiblePrix handlerAjouterCiblePrix = new HandlerAjouterCiblePrix();
	private HandlerSupprimerCiblePrix handlerSupprimerCiblePrix = new HandlerSupprimerCiblePrix();


	public SWTPaPrixController(PaPrixSWT vue) {
		this(vue,null);
	}

	public SWTPaPrixController(PaPrixSWT vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaPrixDAO(getEm());
		modelPrix = new ModelGeneralObjet<SWTPrix,TaPrixDAO,TaPrix>(dao,classModel);
		setVue(vue);
		// a faire avant l'initialisation du Binding
		vue.getGrille().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setSwtOldPrix();
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
			logger.error("Erreur : SWTPaPrixController", e);
		}
	}

	/**
	 * Creation des objet pour l'interface, a partir de l'entite principale.<br>
	 * Ici : creation d'une liste de prix pour l'IHM, a partir de la liste des prix contenue dans l'entite article.
	 * @return
	 */
	public List<SWTPrix> IHMmodel() {
		LinkedList<TaPrix> ldao = new LinkedList<TaPrix>();
		LinkedList<SWTPrix> lswt = new LinkedList<SWTPrix>();
		//masterEntity.getTaPrixes().clear();
		//if(masterEntity!=null)	masterDAO.refresh(masterEntity);
		if(masterEntity!=null && !masterEntity.getTaPrixes().isEmpty()) {

			ldao.addAll(masterEntity.getTaPrixes());

			LgrDozerMapper<TaPrix,SWTPrix> mapper = new LgrDozerMapper<TaPrix,SWTPrix>();
			for (TaPrix o : ldao) {
				SWTPrix t = null;
				t = (SWTPrix) mapper.map(o, SWTPrix.class);
				lswt.add(t);
			}

		}
		return lswt;
	}
	
	public List<SWTCiblePrix> IHMmodelCible() {
		LinkedList<TaTiers> ldaoTaTiers = new LinkedList<TaTiers>();
		LinkedList<TaTTarif> ldaoTaTTarif = new LinkedList<TaTTarif>();
		LinkedList<SWTCiblePrix> lswt = new LinkedList<SWTCiblePrix>();
		//masterEntity.getTaPrixes().clear();
		//if(masterEntity!=null)	masterDAO.refresh(masterEntity);
		//if(masterEntity!=null && !masterEntity.getTaPrixes().isEmpty()) {
		if(taPrix!=null && !taPrix.getTaTiers().isEmpty()) {

			ldaoTaTiers.addAll(taPrix.getTaTiers());

			LgrDozerMapper<TaTiers,SWTCiblePrix> mapper = new LgrDozerMapper<TaTiers,SWTCiblePrix>();
			for (TaTiers o : ldaoTaTiers) {
				SWTCiblePrix t = null;
				t = (SWTCiblePrix) mapper.map(o, SWTCiblePrix.class);
				t.setType(SWTCiblePrix.TYPE_TIERS);
				lswt.add(t);
			}

		}
		if(taPrix!=null && !taPrix.getTaTTarif().isEmpty()) {

			ldaoTaTTarif.addAll(taPrix.getTaTTarif());

			LgrDozerMapper<TaTTarif,SWTCiblePrix> mapper = new LgrDozerMapper<TaTTarif,SWTCiblePrix>();
			for (TaTTarif o : ldaoTaTTarif) {
				SWTCiblePrix t = null;
				t = (SWTCiblePrix) mapper.map(o, SWTCiblePrix.class);
				t.setType(SWTCiblePrix.TYPE_TARIF);
				lswt.add(t);
			}

		}
		return lswt;
	}

	public void bind(PaPrixSWT paPrixSWT) {
		try {
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			tableViewer = new LgrTableViewer(paPrixSWT.getGrille());
			tableViewer.createTableCol(classModel,paPrixSWT.getGrille(), nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE,-1);
			listeChamp = tableViewer.setListeChampGrille(nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			selectedPrix = ViewersObservables.observeSingleSelection(tableViewer);

			LgrViewerSupport.bind(tableViewer, 
					new WritableList(IHMmodel(), classModel),
					BeanProperties.values(listeChamp)
			);

			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			tableViewer.selectionGrille(0);

			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);
			bindCible();

			bindingFormMaitreDetail(dbc, realm, selectedPrix,classModel);
			tableViewer.setChecked(tableViewer.getTable().getColumn(1),true);
			changementDeSelection();
			selectedPrix.addChangeListener(new IChangeListener() {

				public void handleChange(ChangeEvent event) {
					changementDeSelection();
				}

			});

		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("", e);
		}
	}
	
	public void bindCible() {
		try {
			//modelLignesTVA = new ModelLignesTVA();
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			tableViewerCible = new LgrTableViewer(vue.getTable());
//			tableViewer.createTableCol(vue.getTable(), "FactureLignesTVA",
//					Const.C_FICHIER_LISTE_CHAMP_GRILLE);
//			listeChamp = tableViewer.setListeChampGrille("FactureLignesTVA",
//					Const.C_FICHIER_LISTE_CHAMP_GRILLE);

			tableViewerCible.createTableCol(vue.getTable(), new String[]{"Type","Code","Libelle"}, new String[]{"100","100","100"});
			listeChampCible = new String[]{"type","code","libelle"};

			LgrViewerSupport.bind(tableViewerCible, new WritableList(IHMmodelCible(), SWTCiblePrix.class),
					BeanProperties.values(listeChampCible));
			selectedCiblePrix = ViewersObservables.observeSingleSelection(tableViewerCible);

			dbc = new DataBindingContext(realm);

		} catch (Exception e) {
			if (e.getMessage() != null)
				vue.getLaMessage().setText(e.getMessage());
			logger.error("", e);
		}
	}

	private void changementDeSelection() {
		if(selectedPrix!=null && selectedPrix.getValue()!=null) {
			if(((SWTPrix) selectedPrix.getValue()).getIdPrix()!=null) {
				taPrix = dao.findById(((SWTPrix) selectedPrix.getValue()).getIdPrix());
			}
			
			refreshCiblePrix();
			
			//null a tester ailleurs, car peut etre null en cas d'insertion
			fireChangementDeSelection(new ChangementDeSelectionEvent(SWTPaPrixController.this));
		}
	}
	public Composite getVue() {
		return vue;
	}

	public ResultAffiche configPanel(ParamAffiche param) {
		if (param != null) {
			Map<String,String[]> map = dao.getParamWhereSQL();
			if (param.getFocusDefautSWT() != null && !param.getFocusDefautSWT().isDisposed())
				param.getFocusDefautSWT().setFocus();
			else
				param.setFocusDefautSWT(vue
						.getGrille());
			if(param.getTitreFormulaire()!=null){
				vue.getLaTitreFormulaire().setText(param.getTitreFormulaire());
			} else {
				vue.getLaTitreFormulaire().setText(ParamAffichePrix.C_TITRE_FORMULAIRE);
			}

			if(param.getTitreGrille()!=null){
				vue.getLaTitreGrille().setText(param.getTitreGrille());
			} else {
				vue.getLaTitreGrille().setText(ParamAffichePrix.C_TITRE_GRILLE);
			}

			if(param.getSousTitre()!=null){
				vue.getLaTitreFenetre().setText(param.getSousTitre());
			} else {
				vue.getLaTitreFenetre().setText(ParamAffichePrix.C_SOUS_TITRE);
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
					taPrix=null;
					selectedPrix.setValue(null);
					actRefresh();
				} catch (Exception e) {
					logger.error("",e);
				}
			}
			// permet de se positionner sur le 1er enregistrement et de remplir
			// le formulaire
			//			tableViewer.tri(classModel, nomClassController,Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			VerrouInterface.setVerrouille(false);
			setSwtOldPrix();

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
				initEtatBouton();
			}

		}
		return null;
	}

	protected void initVerifySaisie() {
		if (mapInfosVerifSaisie == null)
			mapInfosVerifSaisie = new HashMap<Control, InfosVerifSaisie>();

		mapInfosVerifSaisie.put(vue.getTfPRIX_PRIX(), new InfosVerifSaisie(new TaPrix(),Const.C_PRIX_PRIX,new int[]{InfosVerifSaisie.CTRL_QUE_DES_CHIFFRES_POSITIFS}));
		mapInfosVerifSaisie.put(vue.getTfPRIXTTC_PRIX(), new InfosVerifSaisie(new TaPrix(),Const.C_PRIXTTC_PRIX,new int[]{InfosVerifSaisie.CTRL_QUE_DES_CHIFFRES_POSITIFS}));
		
		mapInfosVerifSaisie.put(vue.getTfCODE_UNITE(), new InfosVerifSaisie(new TaUnite(),Const.C_CODE_UNITE,null));

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

		vue.getTfCODE_UNITE().setToolTipText(Const.C_CODE_UNITE);
		vue.getTfPRIX_PRIX().setToolTipText(Const.C_PRIX_PRIX);
		vue.getTfPRIXTTC_PRIX().setToolTipText(Const.C_PRIXTTC_PRIX);
		vue.getCbID_REF_PRIX().setToolTipText(Const.C_ID_REF_PRIX);
		vue.getTfCodeTiers().setToolTipText(Const.C_CODE_TIERS);
		vue.getTfCodeTTarif().setToolTipText(Const.C_CODE_T_TARIF);

		mapComposantChamps.put(vue.getTfCODE_UNITE(), Const.C_CODE_UNITE);
		mapComposantChamps.put(vue.getTfPRIX_PRIX(), Const.C_PRIX_PRIX);
		mapComposantChamps.put(vue.getTfPRIXTTC_PRIX(), Const.C_PRIXTTC_PRIX);
		mapComposantChamps.put(vue.getCbID_REF_PRIX(), Const.C_ID_REF_PRIX);
		
		listeComposantFocusable.add(vue.getTfCodeTiers());
		listeComposantFocusable.add(vue.getTfCodeTTarif());
//		mapComposantChamps.put(vue.getTfCodeTiers(), Const.C_CODE_TIERS);
//		mapComposantChamps.put(vue.getTfCodeTTarif(), Const.C_CODE_T_TARIF);

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
		listeComposantFocusable.add(vue.getBtnAjouter());
		listeComposantFocusable.add(vue.getBtnSupprimerCible());

		if (mapInitFocus == null)
			mapInitFocus = new LinkedHashMap<ModeObjet.EnumModeObjet, Control>();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION, vue
				.getTfCODE_UNITE());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue
				.getTfCODE_UNITE());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION, vue
				.getGrille());

		activeModifytListener();

		vue.getTfPRIX_PRIX().addVerifyListener(queDesChiffres);
		vue.getTfPRIXTTC_PRIX().addVerifyListener(queDesChiffres);
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
		mapCommand.put(C_COMMAND_AJOUTER_CIBLE_PRIX_ID, handlerAjouterCiblePrix);
		mapCommand.put(C_COMMAND_SUPPRIMER_CIBLE_PRIX_ID, handlerSupprimerCiblePrix);

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
		mapActions.put(vue.getBtnAjouter(), C_COMMAND_AJOUTER_CIBLE_PRIX_ID);
		mapActions.put(vue.getBtnSupprimerCible(), C_COMMAND_SUPPRIMER_CIBLE_PRIX_ID);

		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID, C_COMMAND_GLOBAL_REFRESH_ID };
		mapActions.put(null, tabActionsAutres);
	}

	protected void initEtatBouton() {
		//initEtatBoutonCommand();
		initEtatBouton(IHMmodel());
		enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,false);
		switch (daoStandard.getModeObjet().getMode()) {
		case C_MO_INSERTION:
			enableActionAndHandler(C_COMMAND_AJOUTER_CIBLE_PRIX_ID,true);
			enableActionAndHandler(C_COMMAND_SUPPRIMER_CIBLE_PRIX_ID,IHMmodelCible().isEmpty()?false:true);
			break;
		case C_MO_EDITION:
			enableActionAndHandler(C_COMMAND_AJOUTER_CIBLE_PRIX_ID,true);
			enableActionAndHandler(C_COMMAND_SUPPRIMER_CIBLE_PRIX_ID,IHMmodelCible().isEmpty()?false:true);
			break;
		case C_MO_CONSULTATION:
			enableActionAndHandler(C_COMMAND_AJOUTER_CIBLE_PRIX_ID,false);
			enableActionAndHandler(C_COMMAND_SUPPRIMER_CIBLE_PRIX_ID,false);
			break;
		default:
			break;
		}
	}	

	public SWTPaPrixController getThis() {
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
					.getString("Prix.Message.Enregistrer"))) {

				try {
					actEnregistrer();
				} catch (Exception e) {
					vue.getLaMessage().setText(e.getMessage());
					logger.error("", e);
				}
			} else {
				fireAnnuleTout(new AnnuleToutEvent(this,true));
			}
			break;
		case C_MO_CONSULTATION:
			break;
		default:
			break;
		}
		if (retour) {
			if(dao.dataSetEnModif())
				try {
					dao.annuler(taPrix);
				} catch (Exception e) {
					throw new ExceptLgr();
				}
				if (ecranAppelant instanceof SWTPaAideControllerSWT) {
					setListeEntity(getModelPrix().remplirListe());
					dao.initValeurIdTable(taPrix);
					fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
							dao.getChampIdEntite(), dao.getValeurIdTable(),
							selectedPrix.getValue())));

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
					if(getFocusAvantAideSWT().equals(vue.getTfCODE_UNITE())){
						TaUnite u = null;
						TaUniteDAO t = new TaUniteDAO(getEm());
						u = t.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
						t = null;
						taPrix.setTaUnite(u);
					}
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
		} else if (evt.getRetour() != null){
			if (getFocusAvantAideSWT() instanceof Table) {
				if (getFocusAvantAideSWT() == vue.getGrille()) {
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
			setSwtOldPrix();
			if(LgrMess.isDOSSIER_EN_RESEAU()){
				DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
				fireDeclencheCommandeController(e);
				continuer=masterDAO.dataSetEnModif();
			}
			if(continuer){
				//setMasterEntity(masterDAO.findById(masterEntity.getIdArticle()));
				swtPrix = new SWTPrix();
				if (masterEntity.getTaPrix()==null){
					swtPrix.setIdRefPrix(true);
				}
				else
					swtPrix.setIdRefPrix(false);
				swtPrix.setPrixPrix(new BigDecimal(0));
				swtPrix.setPrixttcPrix(new BigDecimal(0));
				swtPrix.setCodeArticle(masterEntity.getCodeArticle());
				dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_INSERTION);
				taPrix = new TaPrix();
				List l = IHMmodel();
				l.add(swtPrix);
				writableList = new WritableList(realm, l, classModel);
				tableViewer.setInput(writableList);
				tableViewer.refresh();
				tableViewer.setSelection(new StructuredSelection(swtPrix));
				initEtatBouton();

				try {
					DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
					fireDeclencheCommandeController(e);
				} catch (Exception e) {
					logger.error("",e);
				}
			}
		} finally {
			initEtatComposant();
			VerrouInterface.setVerrouille(false);
		}
	}

	@Override
	protected void actModifier() throws Exception {
		try {
			boolean continuer=true;
			setSwtOldPrix();
			if(LgrMess.isDOSSIER_EN_RESEAU()){
				DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
				fireDeclencheCommandeController(e);
				continuer=masterDAO.dataSetEnModif();
			}
			if(continuer){
				setMasterEntity(masterDAO.findById(masterEntity.getIdArticle()));
				for (TaPrix p : masterEntity.getTaPrixes()) {
					if(p.getIdPrix()==((SWTPrix) selectedPrix.getValue()).getIdPrix()) {
						taPrix = p;
						//					if(LgrMess.isDOSSIER_EN_RESEAU())actRefresh();
					}
				}			
				masterDAO.verifAutoriseModification(masterEntity);
				DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
				fireDeclencheCommandeController(e);
				dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_EDITION);
				initEtatBouton();
			}
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
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
					for (TaPrix p : masterEntity.getTaPrixes()) {
						if(p.getIdPrix()==((SWTPrix) selectedPrix.getValue()).getIdPrix()) {
							taPrix = p;
						}
					}
					dao.begin(transaction);
					dao.supprimer(taPrix);
					taPrix.setTaArticle(null);
					masterEntity.removePrix(taPrix);
					dao.commit(transaction);
					Object suivant=tableViewer.getElementAt(tableViewer.getTable().getSelectionIndex()+1);
					modelPrix.removeEntity(taPrix);			
					taPrix=masterEntity.getTaPrix();
					tableViewer.selectionGrille(0);
					try {
						DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_ENREGISTRER_ID);
						fireDeclencheCommandeController(e);
					} catch (Exception e) {
						logger.error("",e);
					}	
					if(suivant!=null)tableViewer.setSelection(new StructuredSelection(suivant),true);
					else tableViewer.selectionGrille(0);
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
						.getString("Prix.Message.Annuler")))) {
					remetTousLesChampsApresAnnulationSWT(dbc);
					actRefresh();
					dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
					hideDecoratedFields();
				}
				initEtatBouton();
				if(!annuleToutEnCours) {
					fireAnnuleTout(new AnnuleToutEvent(this));
				}
				break;
			case C_MO_EDITION:
				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("Prix.Message.Annuler")))) {
					int rang =((WritableList)tableViewer.getInput()).indexOf(selectedPrix.getValue());
					List<SWTPrix> l = IHMmodel();
					if(rang!=-1)
						l.set(rang, swtOldPrix);
					remetTousLesChampsApresAnnulationSWT(dbc);
					writableList = new WritableList(realm, l, classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.setSelection(new StructuredSelection(swtOldPrix), true);

					ctrlTousLesChampsAvantEnregistrementSWT();

					dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
					hideDecoratedFields();
					if(!annuleToutEnCours) {
						fireAnnuleTout(new AnnuleToutEvent(this));
					}
				}
				initEtatBouton();

				break;
			case C_MO_CONSULTATION:
				//actionFermer.run();
				fireChangementDePage(new ChangementDePageEvent(this,ChangementDePageEvent.DEBUT));
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
			if(getFocusCourantSWT().equals(vue.getTfCODE_UNITE())
					|| getFocusCourantSWT().equals(vue.getTfCodeTiers())
					|| getFocusCourantSWT().equals(vue.getTfCodeTTarif()))
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
				paramAfficheAideRecherche.setMessage(message);
				// Creation de l'ecran d'aide principal
				Shell s = new Shell(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),LgrShellUtil.styleLgr);
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
					if(getFocusCourantSWT().equals(vue.getGrille())){
//						PaPrixSWT paPrixSWT = new PaPrixSWT(s2,SWT.NULL);
//						SWTPaPrixController swtPaPrixController = new SWTPaPrixController(paPrixSWT);
//
//						editorCreationId = EditorPrix.ID;
//						editorInputCreation = new EditorInputPrix();
//
//						
//						paramAfficheAideRecherche.setForceAffichageAideRemplie(true);
//						paramAfficheAideRecherche.setAfficheDetail(false);
//						
//						
//						ParamAffichePrix paramAffichePrix = new ParamAffichePrix();
//						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
//						paramAffichePrix.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//						paramAffichePrix.setEcranAppelant(paAideController);
//						controllerEcranCreation = swtPaPrixController;
//						parametreEcranCreation = paramAffichePrix;
//
//						paramAfficheAideRecherche.setTypeEntite(TaPrix.class);
//						paramAfficheAideRecherche.setChampsRecherche(Const.C_PRIX_PRIX);
//						paramAfficheAideRecherche.setDebutRecherche(vue.getTfPRIX_PRIX().getText());
//						paramAfficheAideRecherche.setControllerAppelant(getThis());
//						paramAfficheAideRecherche.setModel(swtPaPrixController.getModelPrix());
//						paramAfficheAideRecherche.setTypeObjet(swtPaPrixController.getClassModel());
//						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_PRIX);
					}
					break;
				case C_MO_EDITION:
				case C_MO_INSERTION:
					if(getFocusCourantSWT().equals(vue.getTfCODE_UNITE())){
						PaUniteSWT paUniteSWT = new PaUniteSWT(s2,SWT.NULL);
						SWTPaUniteController swtPaUniteController = new SWTPaUniteController(paUniteSWT);

						editorCreationId = EditorUnite.ID;
						editorInputCreation = new EditorInputUnite();

						ParamAfficheUnite paramAfficheUnite = new ParamAfficheUnite();
						paramAfficheAideRecherche.setJPQLQuery(new TaUniteDAO(getEm()).getJPQLQuery());
						paramAfficheUnite.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheUnite.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaUniteController;
						parametreEcranCreation = paramAfficheUnite;

						paramAfficheAideRecherche.setTypeEntite(TaUnite.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_UNITE);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_UNITE().getText());
						paramAfficheAideRecherche.setControllerAppelant(SWTPaPrixController.this);
						paramAfficheAideRecherche.setModel(new TaUniteDAO(getEm()).modelObjetUniteArticle(masterEntity.getCodeArticle()));
						paramAfficheAideRecherche.setTypeObjet(swtPaUniteController.getClassModel());

						paramAfficheAideRecherche.setChampsIdentifiant(swtPaUniteController.getDao().getChampIdTable());
					}
					if(getFocusCourantSWT().equals(vue.getTfCodeTiers())){
						PaTiersSWT paTiersSWT = new PaTiersSWT(s2,SWT.NULL);
						SWTPaTiersController swtPaTiersController = new SWTPaTiersController(paTiersSWT);
						paramAfficheAideRecherche.setForceAffichageAideRemplie(false);

						editorCreationId = EditorTiers.ID;
						editorInputCreation = new EditorInputTiers();

						ParamAfficheTiers paramAfficheTiers = new ParamAfficheTiers();
						paramAfficheAideRecherche.setJPQLQuery(new TaTiersDAO(getEm()).getJPQLQuery());
						paramAfficheTiers.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheTiers.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaTiersController;
						parametreEcranCreation = paramAfficheTiers;

						paramAfficheAideRecherche.setTypeEntite(TaTiers.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_TIERS);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCodeTiers().getText());
						paramAfficheAideRecherche.setControllerAppelant(SWTPaPrixController.this);
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTiers,TaTiersDAO,TaTiers>(SWTTiers.class,dao.getEntityManager()));
						paramAfficheAideRecherche.setTypeObjet(swtPaTiersController.getClassModel());

						paramAfficheAideRecherche.setChampsIdentifiant(swtPaTiersController.getDao().getChampIdTable());
					}
					if(getFocusCourantSWT().equals(vue.getTfCodeTTarif())){
						PaTypeTarifSWT paTypeTarifSWT = new PaTypeTarifSWT(s2,SWT.NULL);
						SWTPaTypeTarifController swtPaTypeTarifController = new SWTPaTypeTarifController(paTypeTarifSWT);

						editorCreationId = EditorTypeTarif.ID;
						editorInputCreation = new EditorInputTypeTarif();

						ParamAfficheTypeTarif paramAfficheTypeTarif = new ParamAfficheTypeTarif();
						paramAfficheAideRecherche.setJPQLQuery(new TaTTarifDAO(getEm()).getJPQLQuery());
						paramAfficheTypeTarif.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheTypeTarif.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaTypeTarifController;
						parametreEcranCreation = paramAfficheTypeTarif;

						paramAfficheAideRecherche.setTypeEntite(TaTTarif.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_T_TARIF);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCodeTTarif().getText());
						paramAfficheAideRecherche.setControllerAppelant(SWTPaPrixController.this);
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTTarif,TaTTarifDAO,TaTTarif>(SWTTTarif.class,dao.getEntityManager()));
						paramAfficheAideRecherche.setTypeObjet(swtPaTypeTarifController.getClassModel());

						paramAfficheAideRecherche.setChampsIdentifiant(swtPaTypeTarifController.getDao().getChampIdTable());
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

	public IStatus validateUI() throws Exception {
		if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
				|| (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
			ctrlTousLesChampsAvantEnregistrementSWT();
		}
		return null;
	}

	public IStatus validateUIField(String nomChamp,Object value) {
		String validationContext = "PRIX";
		try {
			IStatus s = null;

			if(nomChamp.equals(Const.C_CODE_UNITE)) {
				TaUniteDAO dao = new TaUniteDAO(getEm());

				dao.setModeObjet(getDao().getModeObjet());
				TaUnite f = new TaUnite();
				PropertyUtils.setSimpleProperty(f, nomChamp, value);
				s = dao.validate(f,nomChamp,validationContext);

				if(s.getSeverity()!=IStatus.ERROR) {
					f = dao.findByCode((String)value);
					taPrix.setTaUnite(f);
				}
				dao = null;
			} else {
				TaPrix u = new TaPrix();
				u.setTaArticle(masterEntity);
				PropertyUtils.setSimpleProperty(u, nomChamp, value);

				int change=0;
				if(nomChamp.equals(Const.C_PRIX_PRIX)) {
					u.setPrixttcPrix(((SWTPrix) selectedPrix.getValue()).getPrixttcPrix());
					change =u.getPrixPrix().compareTo(((SWTPrix) selectedPrix.getValue()).getPrixPrix());
				}
				if(nomChamp.equals(Const.C_PRIXTTC_PRIX)) {
					u.setPrixPrix(((SWTPrix) selectedPrix.getValue()).getPrixPrix());
					change =u.getPrixttcPrix().compareTo(((SWTPrix) selectedPrix.getValue()).getPrixttcPrix());
				}

				if(((SWTPrix) selectedPrix.getValue()).getIdPrix()!=null) {
					u.setIdPrix(((SWTPrix) selectedPrix.getValue()).getIdPrix());
				}


				s = dao.validate(u,nomChamp,validationContext);
				if(s.getSeverity()!=IStatus.ERROR && change!=0) {
					((SWTPrix) selectedPrix.getValue()).setPrixPrix(u.getPrixPrix());				
					((SWTPrix) selectedPrix.getValue()).setPrixttcPrix(u.getPrixttcPrix());
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
			//TaPrix u = null;
			if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
				LgrDozerMapper<SWTPrix,TaPrix> mapper = new LgrDozerMapper<SWTPrix,TaPrix>();
				mapper.map((SWTPrix) selectedPrix.getValue(),taPrix);

			} else if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)) {
				LgrDozerMapper<SWTPrix,TaPrix> mapper = new LgrDozerMapper<SWTPrix,TaPrix>();
				mapper.map((SWTPrix) selectedPrix.getValue(),taPrix);
				taPrix.setTaArticle(masterEntity);
				masterEntity.addPrix(taPrix);
				if(((SWTPrix) selectedPrix.getValue()).getIdRefPrix())
					masterEntity.setTaPrix(taPrix);				
			}

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
			taPrix=masterEntity.getTaPrix();
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



	public void initEtatComposant() {
		try {
			if(taPrix!=null) {
				if(!taPrix.getTaTiers().isEmpty() || !taPrix.getTaTTarif().isEmpty()) {
					vue.getCbID_REF_PRIX().setSelection(false);
					vue.getCbID_REF_PRIX().setEnabled(false);
				} else {
					vue.getCbID_REF_PRIX().setEnabled(true);
				}
			}
			vue.getTfCODE_UNITE().setEditable(!isUtilise());
			changeCouleur(vue);
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
		}

	}
	public boolean isUtilise(){
		return (((SWTPrix)selectedPrix.getValue()).getIdArticle()!=null && 
				!dao.recordModifiable(dao.getNomTable(),
						((SWTPrix)selectedPrix.getValue()).getIdArticle()))||
						!masterDAO.autoriseModification(masterEntity);		
	}
	public SWTPrix getSwtOldPrix() {
		return swtOldPrix;
	}

	public void setSwtOldUnite(SWTPrix swtOldPrix) {
		this.swtOldPrix = swtOldPrix;
	}

	public void setSwtOldPrix() {
		if (selectedPrix.getValue() != null)
			this.swtOldPrix = SWTPrix.copy((SWTPrix) selectedPrix.getValue());
		else {
			if(tableViewer.selectionGrille(0)){
				this.swtOldPrix = SWTPrix.copy((SWTPrix) selectedPrix.getValue());
				tableViewer.setSelection(new StructuredSelection(
						(SWTPrix) selectedPrix.getValue()), true);
			}}
	}

	public void setVue(PaPrixSWT vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();

		mapComposantDecoratedField.put(vue.getTfCODE_UNITE(), vue.getFieldCODE_UNITE());
		mapComposantDecoratedField.put(vue.getTfPRIX_PRIX(), vue.getFieldPRIX_PRIX());
		mapComposantDecoratedField.put(vue.getTfPRIXTTC_PRIX(), vue.getFieldPRIXTTC_PRIX());
		mapComposantDecoratedField.put(vue.getCbID_REF_PRIX(), vue.getFieldID_REF_PRIX());
	}

	public Class getClassModel() {
		return classModel;
	}



	@Override
	protected void sortieChamps() {
		if(selectedPrix!=null && selectedPrix.getValue()!=null){
			if(vue.getTfPRIX_PRIX().equals(getFocusCourantSWT())){
				if(((SWTPrix)selectedPrix.getValue()).getTauxTva()!=null)
					((SWTPrix)selectedPrix.getValue()).setPrixttcPrix(((SWTPrix)selectedPrix.getValue()).getPrixPrix().multiply(new BigDecimal(1).
							add(((SWTPrix)selectedPrix.getValue()).getTauxTva().divide(new BigDecimal(100)))));
				vue.getTfPRIXTTC_PRIX().setText(LibConversion.bigDecimalToString(((SWTPrix)selectedPrix.getValue()).getPrixttcPrix()));
			} else if(vue.getTfPRIXTTC_PRIX().equals(getFocusCourantSWT())){
				if(((SWTPrix)selectedPrix.getValue()).getTauxTva()!=null)
					((SWTPrix)selectedPrix.getValue()).setPrixPrix(((SWTPrix)selectedPrix.getValue()).getPrixttcPrix().divide(new BigDecimal(1).
							add(((SWTPrix)selectedPrix.getValue()).getTauxTva().divide(new BigDecimal(100))),MathContext.DECIMAL32));
				vue.getTfPRIX_PRIX().setText(LibConversion.bigDecimalToString(((SWTPrix)selectedPrix.getValue()).getPrixPrix()));
			}
		}		
	}

	@Override
	protected void actRefresh() throws Exception {
		//repositionnement sur la valeur courante
		int idActuel = 0;
		if (taPrix!=null) { //enregistrement en cours de modification/insertion
			idActuel = taPrix.getIdPrix();
		} else if(selectedPrix!=null && (SWTPrix) selectedPrix.getValue()!=null) {
			idActuel = ((SWTPrix) selectedPrix.getValue()).getIdPrix();
		}

		//rafraichissement des valeurs dans la grille
		writableList = new WritableList(realm, IHMmodel(), classModel);
		tableViewer.setInput(writableList);
		
		if(idActuel!=0) {
			tableViewer.setSelection(new StructuredSelection(recherche(Const.C_ID_PRIX, idActuel)));
		}else
			tableViewer.selectionGrille(0);				
	}
	
	private void refreshCiblePrix() {
		writableListCible = new WritableList(realm, IHMmodelCible(), SWTCiblePrix.class);
		tableViewerCible.setInput(writableListCible);
	}
	
	protected class HandlerAjouterCiblePrix extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException{
			try {
				System.out.println("SWTPaPrixController.HandlerAjouterCiblePrix.execute()");
				
				//verif tiers ou type tarif
				//tiers
				String codeTiers = vue.getTfCodeTiers().getText();
				if(!"".equals(codeTiers)) {
					if(!masterEntity.possedeDejaUnPrixPourCeTiers(codeTiers)) {
						TaTiersDAO taTiersDAO = new TaTiersDAO(getEm());
						TaTiers tiers = taTiersDAO.findByCode(codeTiers);
						if(tiers!=null) {
							taPrix.addTaTiers(tiers);
						}
					} else {
						logger.info("L'article possède déjà un prix pour ce tiers");
					}
				}
				
				//type tarif
				String codeTTarif = vue.getTfCodeTTarif().getText();
				if(!"".equals(codeTTarif)) {
					if(!masterEntity.possedeDejaUnPrixPourCeTypeDeTarif(codeTTarif)) {
						TaTTarifDAO taTTarifDAO = new TaTTarifDAO(getEm());
						TaTTarif tTarif = taTTarifDAO.findByCode(codeTTarif);
						if(tTarif!=null) {
							taPrix.addTaTTarif(tTarif);
						}
					} else {
						logger.info("L'article possède déjà un prix pour ce type de tarif");
					}
				}
				
				refreshCiblePrix();
				
			} catch (Exception e1) {
				logger.error("Erreur : actionPerformed", e1);
			}
			return event;
		}
	}
	
	protected class HandlerSupprimerCiblePrix extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException{
			try {
				System.out.println("SWTPaPrixController.HandlerSupprimerCiblePrix.execute()");

				if(selectedCiblePrix!=null && selectedCiblePrix.getValue()!=null) {
					//verif tiers ou type tarif
					//tiers
					if(((SWTCiblePrix)selectedCiblePrix.getValue()).getType().equals(SWTCiblePrix.TYPE_TIERS)) {
						String codeTiers = ((SWTCiblePrix)selectedCiblePrix.getValue()).getCode();
						if(!"".equals(codeTiers)) {
							TaTiersDAO taTiersDAO = new TaTiersDAO(getEm());
							TaTiers tiers = taTiersDAO.findByCode(codeTiers);
							if(tiers!=null) {
								taPrix.removeTaTiers(tiers);
							}
						}
					} else if(((SWTCiblePrix)selectedCiblePrix.getValue()).getType().equals(SWTCiblePrix.TYPE_TARIF)) {
						//type tarif
						String codeTTarif = ((SWTCiblePrix)selectedCiblePrix.getValue()).getCode();
						if(!"".equals(codeTTarif)) {
							TaTTarifDAO taTTarifDAO = new TaTTarifDAO(getEm());
							TaTTarif tTarif = taTTarifDAO.findByCode(codeTTarif);
							if(tTarif!=null) {
								taPrix.removeTaTTarif(tTarif);
							}
						}
					}
				}
				
				refreshCiblePrix();

			} catch (Exception e1) {
				logger.error("Erreur : actionPerformed", e1);
			}
			return event;
		}
	}
	
	protected void initImageBouton() {
		super.initImageBouton();
//		vue.getBtnSupprimerCible().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_SUPPRIMER));
//		vue.getBtnAjouter().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_INSERER));
	}


	public	ModelGeneralObjet<SWTPrix,TaPrixDAO,TaPrix> getModelPrix() {
		return modelPrix;
	}

	public TaArticle getMasterEntity() {
		return masterEntity;
	}

	public void setMasterEntity(TaArticle taArticle) {
		this.masterEntity = taArticle;
	}

	public TaArticleDAO getMasterDAO() {
		return masterDAO;
	}

	public void setMasterDAO(TaArticleDAO masterDAO) {
		this.masterDAO = masterDAO;
	}

	public TaPrix getTaPrix() {
		return taPrix;
	}

	public TaPrixDAO getDao() {
		return dao;
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

	public boolean changementPageValide(){
		if (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0
				|| dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0) {
			//mise a jour de l'entite principale
			if(taPrix!=null && selectedPrix!=null && ((SWTPrix) selectedPrix.getValue())!=null) {
				LgrDozerMapper<SWTPrix,TaPrix> mapper = new LgrDozerMapper<SWTPrix,TaPrix>();
				mapper.map((SWTPrix) selectedPrix.getValue(),taPrix);
			}
		}
		//		dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
		//		initEtatBouton();
		return true;
	};

}

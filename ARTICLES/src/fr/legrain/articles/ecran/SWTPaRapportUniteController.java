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
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.conversion.IConverter;
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
import fr.legrain.articles.dao.TaRapportUnite;
import fr.legrain.articles.dao.TaRapportUniteDAO;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.articles.dao.TaUniteDAO;
import fr.legrain.articles.divers.LgrUpdateValueStrategyComboSensRapportUnite;
import fr.legrain.articles.editor.EditorInputRapportUnite;
import fr.legrain.articles.editor.EditorInputUnite;
import fr.legrain.articles.editor.EditorRapportUnite;
import fr.legrain.articles.editor.EditorUnite;
import fr.legrain.documents.dao.TaLFacture;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Articles.SWTArticle;
import fr.legrain.gestCom.Module_Articles.SWTPrix;
import fr.legrain.gestCom.Module_Articles.SWTRapportUnite;
import fr.legrain.gestCom.Module_Articles.SWTUnite;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.DeclencheCommandeControllerEvent;
import fr.legrain.gestCom.librairiesEcran.swt.IDetailController;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.gestCom.librairiesEcran.swt.LgrUpdateValueStrategy;
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


public class SWTPaRapportUniteController extends JPABaseControllerSWTStandard
implements RetourEcranListener,IDetailController {

	static Logger logger = Logger.getLogger(SWTPaRapportUniteController.class.getName());
	private PaRapportUniteSWT vue = null;
	private TaRapportUniteDAO dao = null;//new TaRapportUniteDAO(getEm());
	private String idArticle = null;

	private Object ecranAppelant = null;
	private SWTRapportUnite swtRapportUnite;
	private SWTRapportUnite swtOldRapportUnite;
	private Realm realm;
	private DataBindingContext dbc;

	private Class classModel = SWTRapportUnite.class;
	private ModelGeneralObjet<SWTRapportUnite,TaRapportUniteDAO,TaRapportUnite> modelRapportUnite = null;//new ModelGeneralObjet<SWTRapportUnite,TaRapportUniteDAO,TaRapportUnite>(dao,classModel);
	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private IObservableValue selectedRapportUnite;
	private String[] listeChamp;
	private String nomClassController = this.getClass().getSimpleName();

	private TaArticle masterEntity = null;
	private TaArticleDAO masterDAO = null;

	private TaRapportUnite taRapportUnite = null;

	private MapChangeListener changeListener = new MapChangeListener();

	public SWTPaRapportUniteController(PaRapportUniteSWT vue) {
		this(vue,null);
	}

	public SWTPaRapportUniteController(PaRapportUniteSWT vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaRapportUniteDAO(getEm());
		modelRapportUnite = new ModelGeneralObjet<SWTRapportUnite,TaRapportUniteDAO,TaRapportUnite>(dao,classModel);
		setVue(vue);

		// a faire avant l'initialisation du Binding
		vue.getGrille().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setSwtOldRapportUnite();
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
	public List<SWTRapportUnite> IHMmodel() {
		LinkedList<TaRapportUnite> ldao = new LinkedList<TaRapportUnite>();
		LinkedList<SWTRapportUnite> lswt = new LinkedList<SWTRapportUnite>();

		if(masterEntity!=null && !masterEntity.getTaRapportUnites().isEmpty()) {

			ldao.addAll(masterEntity.getTaRapportUnites());

			LgrDozerMapper<TaRapportUnite,SWTRapportUnite> mapper = new LgrDozerMapper<TaRapportUnite,SWTRapportUnite>();
			for (TaRapportUnite o : ldao) {
				SWTRapportUnite t = null;
				t = (SWTRapportUnite) mapper.map(o, SWTRapportUnite.class);
				lswt.add(t);
			}

		}
		return lswt;
	}

	public void bind(PaRapportUniteSWT paRapportUniteSWT) {
		try {
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			tableViewer = new LgrTableViewer(paRapportUniteSWT.getGrille());
			tableViewer.createTableCol(classModel,paRapportUniteSWT.getGrille(), nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE,-1);
			listeChamp = tableViewer.setListeChampGrille(nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			//			ObservableListContentProvider viewerContent = new ObservableListContentProvider();
			//			tableViewer.setContentProvider(viewerContent);
			//
			//			IObservableMap[] attributeMaps = BeansObservables.observeMaps(
			//					viewerContent.getKnownElements(), classModel,listeChamp);

			//			tableViewer.setLabelProvider(new ObservableMapLabelProvider(attributeMaps));
			List<SWTRapportUnite> lswt = IHMmodel();

			//			writableList = new WritableList(realm, lswt, classModel);
			//			
			//			tableViewer.setInput(writableList);
			// Set up data binding.
			LgrViewerSupport.bind(tableViewer, 
					new WritableList(lswt, classModel),
					BeanProperties.values(listeChamp)
			);

			selectedRapportUnite = ViewersObservables.observeSingleSelection(tableViewer);

			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			tableViewer.selectionGrille(0);

			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);
			
			vue.getCbSens().setItems(new String[]{LgrUpdateValueStrategyComboSensRapportUnite.sensDiviserTrue,LgrUpdateValueStrategyComboSensRapportUnite.sensMultiplierFalse});
			mapComposantUpdateValueStrategy = new HashMap<Control, LgrUpdateValueStrategy>();
			mapComposantUpdateValueStrategy.put(vue.getCbSens(), new LgrUpdateValueStrategyComboSensRapportUnite());

			bindingFormMaitreDetail(dbc, realm, selectedRapportUnite,classModel);
			
			tableViewer.setChecked(tableViewer.getTable().getColumn(1),true);
			selectedRapportUnite.addChangeListener(new IChangeListener() {

				public void handleChange(ChangeEvent event) {
					changementDeSelection();
				}

			});

		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("", e);
		}
	}
	
	private BigDecimal calculeRapportUnite2() {
		BigDecimal qte2 = new BigDecimal(0);
		if(selectedRapportUnite!=null && selectedRapportUnite.getValue()!=null) {
			SWTRapportUnite swtRapportUnite = (SWTRapportUnite) selectedRapportUnite.getValue();
			boolean sens = LgrUpdateValueStrategyComboSensRapportUnite.sens(vue.getCbSens().getText());
			//if(swtArticle!=null /*&& swtArticle.getSens()!=null*/ /*&& rapportUniteEstRempli()*/) {
			if(swtRapportUnite!=null && swtRapportUnite.getRapport()!=null) {
				int decimale = swtRapportUnite.getNbDecimal()!=null ? swtRapportUnite.getNbDecimal() : 2;
				//if(swtArticle.getSens()) {
				if(sens) {
					qte2 = new BigDecimal(1).divide(swtRapportUnite.getRapport(),MathContext.DECIMAL128).setScale(decimale,BigDecimal.ROUND_HALF_UP);
				} else {
					qte2 = new BigDecimal(1).multiply(swtRapportUnite.getRapport(),MathContext.DECIMAL128).setScale(decimale,BigDecimal.ROUND_HALF_UP);
				}
			}
			vue.getLaU2PhraseRapport().setText(LibConversion.bigDecimalToString(qte2)+" "+swtRapportUnite.getCodeUnite2());
			vue.getLaRapportPhraseRapport().setText(LibConversion.bigDecimalToString(swtRapportUnite.getRapport()));
			vue.getLaU1PhraseRapport().setText("1 "+swtRapportUnite.getCodeUnite1());
		}
		return qte2;
	}
	
	private void changementDeSelection() {
		if(selectedRapportUnite!=null && selectedRapportUnite.getValue()!=null) {
			if(((SWTRapportUnite) selectedRapportUnite.getValue()).getId()!=null) {
				taRapportUnite = dao.findById(((SWTRapportUnite) selectedRapportUnite.getValue()).getId());
			}
			//null a tester ailleurs, car peut etre null en cas d'insertion
			fireChangementDeSelection(new ChangementDeSelectionEvent(SWTPaRapportUniteController.this));
			calculeRapportUnite2();
		}
		initEtatComposant();
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
					taRapportUnite=null;
					selectedRapportUnite.setValue(null);
					actRefresh();
				} catch (Exception e) {
					logger.error("",e);
				}
			}
			// permet de se positionner sur le 1er enregistrement et de remplir
			// le formulaire
			//			tableViewer.tri(classModel, nomClassController,Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			VerrouInterface.setVerrouille(false);
			setSwtOldRapportUnite();

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
		
		mapInfosVerifSaisie.put(vue.getTfCODE_UNITE(), new InfosVerifSaisie(new TaUnite(),Const.C_CODE_UNITE,null));
		mapInfosVerifSaisie.put(vue.getTfCODE_UNITE2(), new InfosVerifSaisie(new TaUnite(),Const.C_CODE_UNITE,null));
		//mapInfosVerifSaisie.put(vue.getTfNB_DECIMAL(), new InfosVerifSaisie(new TaRapportUnite(),Const.C_NB_DECIMAL,new int[]{InfosVerifSaisie.CTRL_QUE_DES_CHIFFRES_POSITIFS}));
		mapInfosVerifSaisie.put(vue.getTfNB_DECIMAL(), new InfosVerifSaisie(new TaLFacture(),Const.C_QTE2_L_DOCUMENT,new int[]{InfosVerifSaisie.CTRL_QUE_DES_CHIFFRES_INTERVALE}));
		mapInfosVerifSaisie.put(vue.getTfRAPPORT(), new InfosVerifSaisie(new TaRapportUnite(),Const.C_RAPPORT,new int[]{InfosVerifSaisie.CTRL_QUE_DES_CHIFFRES}));

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

		vue.getTfCODE_UNITE().setToolTipText(Const.C_CODE_UNITE1);
		vue.getTfCODE_UNITE2().setToolTipText(Const.C_CODE_UNITE2);
		vue.getTfRAPPORT().setToolTipText(Const.C_RAPPORT);
		vue.getTfNB_DECIMAL().setToolTipText(Const.C_NB_DECIMAL);

		mapComposantChamps.put(vue.getTfCODE_UNITE(), Const.C_CODE_UNITE1);
		mapComposantChamps.put(vue.getTfCODE_UNITE2(), Const.C_CODE_UNITE2);
		mapComposantChamps.put(vue.getTfRAPPORT(), Const.C_RAPPORT);
		mapComposantChamps.put(vue.getTfNB_DECIMAL(), Const.C_NB_DECIMAL);
		
//		mapComposantChamps.put(vue.getBtnSensDivision(), Const.C_SENS_RAPPORT);
//		mapComposantChamps.put(vue.getBtnSensMultiplication(), Const.C_SENS_RAPPORT);
		mapComposantChamps.put(vue.getCbSens(), Const.C_SENS_RAPPORT);

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
				.getTfCODE_UNITE());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue
				.getTfCODE_UNITE());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION, vue
				.getGrille());

		activeModifytListener();

		vue.getTfRAPPORT().addVerifyListener(queDesChiffres);
		vue.getTfNB_DECIMAL().addVerifyListener(queDesChiffres);
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

	protected void initEtatBouton() {
		//initEtatBoutonCommand();
		initEtatBouton(IHMmodel());
		enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,false);
	}	

	public SWTPaRapportUniteController getThis() {
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
					.getString("RapportUnite.Message.Enregistrer"))) {

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
					dao.annuler(taRapportUnite);
				} catch (Exception e) {
					throw new ExceptLgr();
				}
				if (ecranAppelant instanceof SWTPaAideControllerSWT) {
					setListeEntity(getModelRapportUnite().remplirListe());
					dao.initValeurIdTable(taRapportUnite);
					fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
							dao.getChampIdEntite(), dao.getValeurIdTable(),
							selectedRapportUnite.getValue())));

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
						taRapportUnite.setTaUnite1(u);
					}
					if(getFocusAvantAideSWT().equals(vue.getTfCODE_UNITE2())){
						TaUnite u = null;
						TaUniteDAO t = new TaUniteDAO(getEm());
						u = t.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
						t = null;
						taRapportUnite.setTaUnite2(u);
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
			setSwtOldRapportUnite();
			if(LgrMess.isDOSSIER_EN_RESEAU()){
				DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
				fireDeclencheCommandeController(e);
				continuer=masterDAO.dataSetEnModif();
			}
			if(continuer){
				setMasterEntity(masterDAO.findById(masterEntity.getIdArticle()));
				swtRapportUnite = new SWTRapportUnite();
				swtRapportUnite.setCodeArticle(masterEntity.getCodeArticle());
				dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_INSERTION);
				taRapportUnite = new TaRapportUnite();
				List l = IHMmodel();
				l.add(swtRapportUnite);
				writableList = new WritableList(realm, l, classModel);
				tableViewer.setInput(writableList);
				tableViewer.refresh();
				tableViewer.setSelection(new StructuredSelection(swtRapportUnite));
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
			setSwtOldRapportUnite();
			if(LgrMess.isDOSSIER_EN_RESEAU()){
				DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
				fireDeclencheCommandeController(e);
				continuer=masterDAO.dataSetEnModif();
			}
			if(continuer){
				setMasterEntity(masterDAO.findById(masterEntity.getIdArticle()));
				for (TaRapportUnite p : masterEntity.getTaRapportUnites()) {
					if(p.getId()==((SWTRapportUnite) selectedRapportUnite.getValue()).getId()) {
						taRapportUnite = p;
						//if(LgrMess.isDOSSIER_EN_RESEAU())actRefresh();
					}
				}			
				masterDAO.verifAutoriseModification(masterEntity);
				DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
				fireDeclencheCommandeController(e);
				dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_EDITION);
				initEtatBouton();
				initEtatComposant();

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
							.getString("RapportUnite.Message.Supprimer"))) {				
						dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_SUPPRESSION);
						try {
							DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
							fireDeclencheCommandeController(e);
						} catch (Exception e) {
							logger.error("",e);
						}
						for (TaRapportUnite p : masterEntity.getTaRapportUnites()) {
							if(p.getId()==((SWTRapportUnite) selectedRapportUnite.getValue()).getId()) {
								taRapportUnite = p;
							}
						}
						dao.begin(transaction);
						dao.supprimer(taRapportUnite);
						taRapportUnite.setTaArticle(null);
						masterEntity.removeRapportUnite(taRapportUnite);
						dao.commit(transaction);
						Object suivant=tableViewer.getElementAt(tableViewer.getTable().getSelectionIndex()+1);
						modelRapportUnite.removeEntity(taRapportUnite);
						taRapportUnite=masterEntity.getTaRapportUnite();
						transaction = null;
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
			if(transaction!=null && transaction.isActive()) {
				transaction.rollback();
			}
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
						.getString("RapportUnite.Message.Annuler")))) {
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
						.getString("RapportUnite.Message.Annuler")))) {
					int rang =((WritableList)tableViewer.getInput()).indexOf(selectedRapportUnite.getValue());
					List<SWTRapportUnite> l = IHMmodel();
					if(rang!=-1)
						l.set(rang, swtOldRapportUnite);
					//remetTousLesChampsApresAnnulationSWT(dbc);
					writableList = new WritableList(realm, l, classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.setSelection(new StructuredSelection(swtOldRapportUnite), true);

					dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
					hideDecoratedFields();
				}
				initEtatBouton();
				if(!annuleToutEnCours) {
					fireAnnuleTout(new AnnuleToutEvent(this));
				}

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
			if(getFocusCourantSWT().equals(vue.getTfCODE_UNITE())||
					getFocusCourantSWT().equals(vue.getTfCODE_UNITE2()))
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
//						PaRapportUniteSWT paRapportUniteSWT = new PaRapportUniteSWT(s2,SWT.NULL);
//						SWTPaRapportUniteController swtPaRapportUniteController = new SWTPaRapportUniteController(paRapportUniteSWT);
//
//						editorCreationId = EditorRapportUnite.ID;
//						editorInputCreation = new EditorInputRapportUnite();
//						
//						paramAfficheAideRecherche.setForceAffichageAideRemplie(true);
//						paramAfficheAideRecherche.setAfficheDetail(false);
//
//						ParamAfficheRapportUnite paramAfficheRapportUnite = new ParamAfficheRapportUnite();
//						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
//						paramAfficheRapportUnite.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//						paramAfficheRapportUnite.setEcranAppelant(paAideController);
//						controllerEcranCreation = swtPaRapportUniteController;
//						parametreEcranCreation = paramAfficheRapportUnite;
//
//						paramAfficheAideRecherche.setTypeEntite(TaRapportUnite.class);
//						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_UNITE1);
//						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_UNITE().getText());
//						paramAfficheAideRecherche.setControllerAppelant(getThis());
//						paramAfficheAideRecherche.setModel(swtPaRapportUniteController.getModelRapportUnite());
//						paramAfficheAideRecherche.setTypeObjet(swtPaRapportUniteController.getClassModel());
//						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID);
					}
					break;
				case C_MO_EDITION:
				case C_MO_INSERTION:
					if(getFocusCourantSWT().equals(vue.getTfCODE_UNITE())||
							getFocusCourantSWT().equals(vue.getTfCODE_UNITE2())){
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
						if(getFocusCourantSWT().equals(vue.getTfCODE_UNITE())){
							paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_UNITE().getText());
						} else {
							paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_UNITE2().getText());
						}
						paramAfficheAideRecherche.setControllerAppelant(SWTPaRapportUniteController.this);
						paramAfficheAideRecherche.setModel(new TaUniteDAO(getEm()).modelObjetUniteArticle(masterEntity.getCodeArticle()));
						paramAfficheAideRecherche.setTypeObjet(swtPaUniteController.getClassModel());

						paramAfficheAideRecherche.setChampsIdentifiant(swtPaUniteController.getDao().getChampIdTable());
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
		String validationContext = "RAPPORT_UNITE";
		try {
			IStatus s = null;

			if(nomChamp.equals(Const.C_CODE_UNITE1)) {
				TaUniteDAO dao = new TaUniteDAO(getEm());

				dao.setModeObjet(getDao().getModeObjet());
				TaUnite f = new TaUnite();
				PropertyUtils.setSimpleProperty(f, Const.C_CODE_UNITE, value);
				s = dao.validate(f,Const.C_CODE_UNITE,validationContext);

				if(s.getSeverity()!=IStatus.ERROR) {
					f = dao.findByCode((String)value);
					taRapportUnite.setTaUnite1(f);
//					if(taRapportUnite.isRapportUniteRef())
				}
				dao = null;
			} else 
				if(nomChamp.equals(Const.C_CODE_UNITE2)) {
					TaUniteDAO dao = new TaUniteDAO(getEm());

					dao.setModeObjet(getDao().getModeObjet());
					TaUnite f = new TaUnite();
					PropertyUtils.setSimpleProperty(f, Const.C_CODE_UNITE, value);
					s = dao.validate(f,Const.C_CODE_UNITE,validationContext);

					if(s.getSeverity()!=IStatus.ERROR) {
						f = dao.findByCode((String)value);
						taRapportUnite.setTaUnite2(f);
					}
					dao = null;
				} else {				
					TaRapportUnite u = new TaRapportUnite();
					
//					if(nomChamp.equals(Const.C_NB_DECIMAL)) {
//						//traitement des booleens
//						if((Boolean)value==true) value=new Integer(1); else value=new Integer(0);
//					}
					
					if(nomChamp.equals(Const.C_SENS_RAPPORT)) {
						//traitement des booleens
						if((Boolean)value==true) value=new Integer(1); else value=new Integer(0);
					}
					PropertyUtils.setSimpleProperty(u, nomChamp, value);
					u.setTaArticle(masterEntity);


					if(((SWTRapportUnite) selectedRapportUnite.getValue()).getId()!=null) {
						u.setId(((SWTRapportUnite) selectedRapportUnite.getValue()).getId());
					}

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
			ctrlUnChampsSWT(getFocusCourantSWT());
			ctrlTousLesChampsAvantEnregistrementSWT();
			dao.begin(transaction);
			if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
				LgrDozerMapper<SWTRapportUnite,TaRapportUnite> mapper = new LgrDozerMapper<SWTRapportUnite,TaRapportUnite>();
				mapper.map((SWTRapportUnite) selectedRapportUnite.getValue(),taRapportUnite);


			} else if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)) {
				LgrDozerMapper<SWTRapportUnite,TaRapportUnite> mapper = new LgrDozerMapper<SWTRapportUnite,TaRapportUnite>();
				mapper.map((SWTRapportUnite) selectedRapportUnite.getValue(),taRapportUnite);
				taRapportUnite.setTaArticle(masterEntity);
				masterEntity.addRapportUnite(taRapportUnite);				
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

			dao.commit(transaction);
			modelRapportUnite.addEntity(taRapportUnite);
			changementDeSelection();
			transaction = null;
			actRefresh();


		} finally {
			initEtatBouton();
		}
	}



	public void initEtatComposant() {
		try {
			vue.getTfCODE_UNITE().setEditable(!isUtilise()&&
					!isRapportRef());
			changeCouleur(vue);
			
			boolean phraseVisible = false;
			if(taRapportUnite.getId()!=0 && !vue.getTfRAPPORT().getText().equals("")) {
				//article enregistrer
				phraseVisible = true;
			}
			
			vue.getLaU1PhraseRapport().setVisible(phraseVisible);
			vue.getLaU2PhraseRapport().setVisible(phraseVisible);
			vue.getLaRapportPhraseRapport().setVisible(phraseVisible);
			vue.getLaEgalePhraseRapport().setVisible(phraseVisible);
			
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
		}

	}
	public boolean isUtilise(){
		boolean retour = false;
		if(selectedRapportUnite!=null && (SWTRapportUnite)selectedRapportUnite.getValue()!=null && 
				((SWTRapportUnite)selectedRapportUnite.getValue()).getId()!=null && 
				masterDAO!=null && masterEntity!=null)
		retour= !dao.recordModifiable(dao.getNomTable(),
						((SWTRapportUnite)selectedRapportUnite.getValue()).getId())||!
						masterDAO.autoriseModification(masterEntity);
		return retour;
	}
	
	public boolean isRapportRef(){
		boolean retour=false;
		if(masterEntity!=null && taRapportUnite!=null)
			retour= masterEntity.isRapportUniteRef(taRapportUnite);
		return retour;
	}
	
	public SWTRapportUnite getSwtOldRapportUnite() {
		return swtOldRapportUnite;
	}

	public void setSwtOldUnite(SWTRapportUnite swtOldRapportUnite) {
		this.swtOldRapportUnite = swtOldRapportUnite;
	}

	public void setSwtOldRapportUnite() {
		if (selectedRapportUnite!=null && selectedRapportUnite.getValue() != null)
			this.swtOldRapportUnite = SWTRapportUnite.copy((SWTRapportUnite) selectedRapportUnite.getValue());
		else {
			if(tableViewer.selectionGrille(0)){
				this.swtOldRapportUnite = SWTRapportUnite.copy((SWTRapportUnite) selectedRapportUnite.getValue());
				tableViewer.setSelection(new StructuredSelection(
						(SWTRapportUnite) selectedRapportUnite.getValue()), true);
			}}
	}

	public void setVue(PaRapportUniteSWT vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();

		mapComposantDecoratedField.put(vue.getTfCODE_UNITE(), vue.getFieldCODE_UNITE());
		mapComposantDecoratedField.put(vue.getTfCODE_UNITE2(), vue.getFieldCODE_UNITE2());
		mapComposantDecoratedField.put(vue.getTfRAPPORT(), vue.getFieldRAPPORT());
		mapComposantDecoratedField.put(vue.getTfNB_DECIMAL(), vue.getFieldNB_DECIMAL());
	}

	public Class getClassModel() {
		return classModel;
	}



	@Override
	protected void sortieChamps() {
	}

	@Override
	protected void actRefresh() throws Exception {
		//repositionnement sur la valeur courante
		int idActuel = 0;
		if (taRapportUnite!=null) { //enregistrement en cours de modification/insertion
			idActuel = taRapportUnite.getId();
		} else if(selectedRapportUnite!=null && (SWTRapportUnite) selectedRapportUnite.getValue()!=null) {
			idActuel = ((SWTRapportUnite) selectedRapportUnite.getValue()).getId();
		}

		//rafraichissement des valeurs dans la grille
		writableList = new WritableList(realm, IHMmodel(), classModel);
		tableViewer.setInput(writableList);

		if(idActuel!=0) {
			tableViewer.setSelection(new StructuredSelection(recherche(Const.C_ID, idActuel)));
		}else
			tableViewer.selectionGrille(0);				
	}


	public	ModelGeneralObjet<SWTRapportUnite,TaRapportUniteDAO,TaRapportUnite> getModelRapportUnite() {
		return modelRapportUnite;
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

	public TaRapportUnite getTaRapportUnite() {
		return taRapportUnite;
	}

	public TaRapportUniteDAO getDao() {
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
			if(taRapportUnite!=null && selectedRapportUnite!=null && ((SWTRapportUnite) selectedRapportUnite.getValue())!=null) {
				LgrDozerMapper<SWTRapportUnite,TaRapportUnite> mapper = new LgrDozerMapper<SWTRapportUnite,TaRapportUnite>();
				mapper.map((SWTRapportUnite) selectedRapportUnite.getValue(),taRapportUnite);
			}
		}
		//		dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
		//		initEtatBouton();
		return true;
	};

}

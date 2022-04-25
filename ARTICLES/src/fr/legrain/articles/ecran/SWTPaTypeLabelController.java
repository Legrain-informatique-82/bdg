package fr.legrain.articles.ecran;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaCategorieArticle;
import fr.legrain.articles.dao.TaCategorieArticleDAO;
import fr.legrain.articles.dao.TaFamille;
import fr.legrain.articles.dao.TaFamilleDAO;
import fr.legrain.articles.dao.TaLabelArticle;
import fr.legrain.articles.dao.TaLabelArticleDAO;
import fr.legrain.articles.dao.TaTTva;
import fr.legrain.articles.dao.TaTTvaDAO;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.articles.ecran.SWTPaImageArticleController.HandlerChoixImage;
import fr.legrain.articles.editor.EditorTypeCategorieArticle;
import fr.legrain.articles.editor.EditorFamille;
import fr.legrain.articles.editor.EditorInputTypeCategorieArticle;
import fr.legrain.articles.editor.EditorInputFamille;
import fr.legrain.articles.editor.EditorInputTypeTVA;
import fr.legrain.articles.editor.EditorTypeTVA;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Articles.SWTArticle;
import fr.legrain.gestCom.Module_Articles.SWTTypeCategorieArticle;
import fr.legrain.gestCom.Module_Articles.SWTFamille;
import fr.legrain.gestCom.Module_Articles.SWTTTva;
import fr.legrain.gestCom.Module_Articles.SWTTva;
import fr.legrain.gestCom.Module_Articles.SWTTypeLabelArticle;
import fr.legrain.gestCom.Module_Articles.SWTUnite;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideRechercheSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideRechercheControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.UtilImage;
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


public class SWTPaTypeLabelController extends JPABaseControllerSWTStandard
implements RetourEcranListener {

	static Logger logger = Logger.getLogger(SWTPaTypeLabelController.class.getName());
	private PaTypeLabelArticleSWT vue = null;
	private TaLabelArticleDAO dao = null;

	private Object ecranAppelant = null;
	private SWTTypeLabelArticle swtTypeLabelArticle;
	private SWTTypeLabelArticle swtOldTypeLabelArticle;
	private Realm realm;
	private DataBindingContext dbc;

	private Class classModel = SWTTypeLabelArticle.class;
	private ModelGeneralObjet<SWTTypeLabelArticle,TaLabelArticleDAO,TaLabelArticle> modelTypeLabelArticle = null;//new ModelGeneralObjet<SWTCategorieArticle,TaLabelArticleDAO,TaLabelArticle>(dao,classModel);
	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private IObservableValue selectedTypeLabelArticle;
	private String[] listeChamp;
	private String nomClassController = this.getClass().getSimpleName();

	private TaLabelArticle taLabelArticle = null;
	
	private MapChangeListener changeListener = new MapChangeListener();
	
	public static final String C_COMMAND_CHOIX_IMAGE_ARTICLE_ID = "fr.legrain.gestionCommerciale.article.image";
	private HandlerChoixImage handlerChoixImage = new HandlerChoixImage();
	
	private Canvas imagePreview = null;
	
	public SWTPaTypeLabelController(PaTypeLabelArticleSWT vue) {
		this(vue,null);
	}

	public SWTPaTypeLabelController(PaTypeLabelArticleSWT vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaLabelArticleDAO(getEm());
		modelTypeLabelArticle = new ModelGeneralObjet<SWTTypeLabelArticle,TaLabelArticleDAO,TaLabelArticle>(dao,classModel);
		setVue(vue);

		// a faire avant l'initialisation du Binding
		vue.getGrille().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setSwtOldTypeLabelArticle();
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
			logger.error("Erreur : PaArticlesController", e);
		}
	}

	public void bind(PaTypeLabelArticleSWT paCategorieArticleSWT) {
		try {
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			tableViewer = new LgrTableViewer(paCategorieArticleSWT.getGrille());
			tableViewer.createTableCol(classModel,paCategorieArticleSWT.getGrille(), nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE,-1);
			listeChamp = tableViewer.setListeChampGrille(nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE);

//			ObservableListContentProvider viewerContent = new ObservableListContentProvider();
//			tableViewer.setContentProvider(viewerContent);
//
//			IObservableMap[] attributeMaps = BeansObservables.observeMaps(
//					viewerContent.getKnownElements(), classModel,listeChamp);
//
//			tableViewer.setLabelProvider(new ObservableMapLabelProvider(attributeMaps));
//			writableList = new WritableList(realm, modelTTva.remplirListe(), classModel);
//			tableViewer.setInput(writableList);
			
			// Set up data binding.
			LgrViewerSupport.bind(tableViewer, 
					new WritableList(modelTypeLabelArticle.remplirListe(), classModel),
					BeanProperties.values(listeChamp)
					);

			selectedTypeLabelArticle = ViewersObservables.observeSingleSelection(tableViewer);

			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			tableViewer.selectionGrille(0);

			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);

			bindingFormMaitreDetail(dbc, realm, selectedTypeLabelArticle,classModel);
			
			changementDeSelection();
			selectedTypeLabelArticle.addChangeListener(new IChangeListener() {

				public void handleChange(ChangeEvent event) {
					changementDeSelection();
				}

			});

		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("", e);
		}
	}

	public Composite getVue() {
		return vue;
	}

	public ResultAffiche configPanel(ParamAffiche param) {
		if (param != null) {
			if (((ParamAfficheTypeLabelArticle) param).getFocusDefautSWT() != null && !((ParamAfficheTypeLabelArticle) param).getFocusDefautSWT().isDisposed())
				((ParamAfficheTypeLabelArticle) param).getFocusDefautSWT().setFocus();
			else
				((ParamAfficheTypeLabelArticle) param).setFocusDefautSWT(vue.getGrille());
			vue.getLaTitreFormulaire().setText(((ParamAfficheTypeLabelArticle) param).getTitreFormulaire());
			vue.getLaTitreGrille().setText(((ParamAfficheTypeLabelArticle) param).getTitreGrille());
			vue.getLaTitreFenetre().setText(((ParamAfficheTypeLabelArticle) param).getSousTitre());

			if (param.getEcranAppelant() != null) {
				ecranAppelant = param.getEcranAppelant();
			}
			Map<String,String[]> map = dao.getParamWhereSQL();
//			if(param.getIdFiche()!=null &&  !param.getIdFiche().equals("")) {
//				if(map==null) map = new HashMap<String,String[]>();
//				map.clear();
//				map.put("idTTva",new String[]{"=",param.getIdFiche()});
//				dao.setParamWhereSQL(map);
//				vue.getBtnTous().setVisible(true);
//				vue.getGrille().setVisible(false);
//				vue.getLaTitreGrille().setVisible(false);
//				vue.getCompositeForm().setWeights(new int[]{0,100});					
//			}
			bind(vue);
			// permet de se positionner sur le 1er enregistrement et de remplir
			// le formulaire
			tableViewer.selectionGrille(0);
			tableViewer.tri(classModel, nomClassController,Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			VerrouInterface.setVerrouille(false);
			setSwtOldTypeLabelArticle();

			if (param.getModeEcran() != null
					&& param.getModeEcran().compareTo(EnumModeObjet.C_MO_INSERTION) == 0) {
				try {
					actInserer();
				} catch (Exception e) {
					vue.getLaMessage().setText(e.getMessage());
					logger.error("", e);
				}
			}

		}
		return null;
	}
	
	protected void initVerifySaisie() {
		if (mapInfosVerifSaisie == null)
			mapInfosVerifSaisie = new HashMap<Control, InfosVerifSaisie>();
		
		//faire une verif "que du texte / minuscule-majuscule"
		//mapInfosVerifSaisie.put(vue.getTfUrlRewriting(), new InfosVerifSaisie(new TaLabelArticle(),Const.C_URL_REWRITING_CATEGORIE_ARTICLE,new int[]{InfosVerifSaisie.CTRL_QUE_DES_CHIFFRES_POSITIFS}));

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

		vue.getTfCodeLabel().setToolTipText(Const.C_CODE_LABEL_ARTICLE);
		vue.getTfLibelleLabel().setToolTipText(Const.C_LIBELLE_LABEL_ARTICLE);
		vue.getTfDescription().setToolTipText(Const.C_DESCRIPTION_LABEL_ARTICLE);
		vue.getTfCheminImageArticle().setToolTipText(Const.C_CHEMIN_IMAGE_LABEL_ARTICLE);
		vue.getTfNomImageArticle().setToolTipText(Const.C_NOM_IMAGE_LABEL_ARTICLE);

		mapComposantChamps.put(vue.getTfCodeLabel(), Const.C_CODE_LABEL_ARTICLE);
		mapComposantChamps.put(vue.getTfLibelleLabel(), Const.C_LIBELLE_LABEL_ARTICLE);
		mapComposantChamps.put(vue.getTfDescription(), Const.C_DESCRIPTION_LABEL_ARTICLE);
		mapComposantChamps.put(vue.getTfCheminImageArticle(), Const.C_CHEMIN_IMAGE_LABEL_ARTICLE);
		mapComposantChamps.put(vue.getTfNomImageArticle(), Const.C_NOM_IMAGE_LABEL_ARTICLE);

		for (Control c : mapComposantChamps.keySet()) {
			listeComposantFocusable.add(c);
		}
		
		listeComposantFocusable.add(vue.getBtnBrowseImage());

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
				.getTfCodeLabel());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue
				.getTfCodeLabel());
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
		
		mapCommand.put(C_COMMAND_CHOIX_IMAGE_ARTICLE_ID, handlerChoixImage);
		
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
		
		mapActions.put(vue.getBtnBrowseImage(), C_COMMAND_CHOIX_IMAGE_ARTICLE_ID);

		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID, C_COMMAND_GLOBAL_REFRESH_ID };
		mapActions.put(null, tabActionsAutres);

	}

	public SWTPaTypeLabelController getThis() {
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
					.getString("TypeLabel.Message.Enregistrer"))) {

				try {
					actEnregistrer();
				} catch (Exception e) {
					vue.getLaMessage().setText(e.getMessage());
					logger.error("", e);
				}
			} else

			break;
		case C_MO_CONSULTATION:
			break;
		default:
			break;
		}
		if (retour) {
			if(dao.dataSetEnModif())
				try {
					dao.annuler(taLabelArticle);
				} catch (Exception e) {
					throw new ExceptLgr();
				}
			if (ecranAppelant instanceof SWTPaAideControllerSWT) {
				setListeEntity(getModelTypeLabelArticle().remplirListe());
				dao.initValeurIdTable(taLabelArticle);
				fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
						dao.getChampIdEntite(), dao.getValeurIdTable(),
						selectedTypeLabelArticle.getValue())));

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
					
//					if(getFocusAvantAideSWT().equals(vue.getTfCategorieMere())){
//						TaLabelArticle f = null;
//						TaLabelArticleDAO t = new TaLabelArticleDAO(getEm());
//						f = t.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
//						t = null;
//						taLabelArticle.setCategorieMereArticle(f);
//					}
					
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
			VerrouInterface.setVerrouille(true);
			setSwtOldTypeLabelArticle();
			swtTypeLabelArticle = new SWTTypeLabelArticle();
			taLabelArticle = new TaLabelArticle();
			dao.inserer(taLabelArticle);
			modelTypeLabelArticle.getListeObjet().add(swtTypeLabelArticle);
			writableList = new WritableList(realm, modelTypeLabelArticle.getListeObjet(), classModel);
			tableViewer.setInput(writableList);
			tableViewer.refresh();
			tableViewer.setSelection(new StructuredSelection(swtTypeLabelArticle));
			initEtatBouton();
		} finally {
			initEtatComposant();
			VerrouInterface.setVerrouille(false);
		}
	}

	@Override
	protected void actModifier() throws Exception {
		try {
			if(!LgrMess.isDOSSIER_EN_RESEAU()){
				setSwtOldTypeLabelArticle();
				taLabelArticle = dao.findById(((SWTTypeLabelArticle) selectedTypeLabelArticle.getValue()).getIdLabelArticle());
			}else{
				if(!setSwtOldTTvaRefresh())throw new Exception();
			}
			
			dao.modifier(taLabelArticle);
			
			initEtatBouton();
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		}
	}
	
	public boolean containsEntity(TaLabelArticle entite){
		if(modelTypeLabelArticle.getListeEntity()!=null){
			for (Object e : modelTypeLabelArticle.getListeEntity()) {
				if(((TaLabelArticle)e).getIdLabelArticle()==
					entite.getIdLabelArticle())return true;
			}
		}
		return false;
	}

	public boolean setSwtOldTTvaRefresh() {
		try {	
			if (selectedTypeLabelArticle.getValue()!=null){
				TaLabelArticle taArticleOld =dao.findById(taLabelArticle.getIdLabelArticle());
				taArticleOld=dao.refresh(taArticleOld);
				if(containsEntity(taLabelArticle)) 
					modelTypeLabelArticle.getListeEntity().remove(taLabelArticle);
				if(!taLabelArticle.getVersionObj().equals(taArticleOld.getVersionObj())){
					taLabelArticle=taArticleOld;
					if(!containsEntity(taLabelArticle)) 
						modelTypeLabelArticle.getListeEntity().add(taLabelArticle);					
					actRefresh();
					dao.messageNonAutoriseModification();
				}
				taLabelArticle=taArticleOld;
				if(!containsEntity(taLabelArticle)) 
					modelTypeLabelArticle.getListeEntity().add(taLabelArticle);
				changementDeSelection();
				this.swtOldTypeLabelArticle=SWTTypeLabelArticle.copy((SWTTypeLabelArticle)selectedTypeLabelArticle.getValue());
			}
			return true;
		} catch (Exception e) {
			return false;
		}		
	}

//	public void setSwtOldTTvaRefresh() {
//		if (selectedTTva.getValue()!=null){
//			if(LgrMess.isDOSSIER_EN_RESEAU())dao.refresh(dao.findById(((SWTCategorieArticle) selectedTTva.getValue()).getIdTTva()));
//			taTTva=dao.findById(((SWTCategorieArticle) selectedTTva.getValue()).getIdTTva());
//			try {
//				if(LgrMess.isDOSSIER_EN_RESEAU())actRefresh();
//			} catch (Exception e) {
//				logger.error("",e);
//			}			
//			this.swtOldTTva=SWTCategorieArticle.copy((SWTCategorieArticle)selectedTTva.getValue());
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
					.getString("TypeLabel.Message.Supprimer"))) {
			dao.begin(transaction);
				TaLabelArticle u = dao.findById(((SWTTypeLabelArticle) selectedTypeLabelArticle.getValue()).getIdLabelArticle());
				dao.supprimer(u);
			dao.commit(transaction);
			Object suivant=tableViewer.getElementAt(tableViewer.getTable().getSelectionIndex()+1);
			modelTypeLabelArticle.removeEntity(u);
			taLabelArticle=null;
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
						.getString("TypeLabel.Message.Annuler")))) {
					remetTousLesChampsApresAnnulationSWT(dbc);
					if(((SWTTypeLabelArticle) selectedTypeLabelArticle.getValue()).getIdLabelArticle()==null){
					modelTypeLabelArticle.getListeObjet().remove(
							((SWTTypeLabelArticle) selectedTypeLabelArticle.getValue()));
					writableList = new WritableList(realm, modelTypeLabelArticle
							.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.selectionGrille(0);
					}
					dao.annuler(taLabelArticle);
					hideDecoratedFields();
				}
				initEtatBouton();
				break;
			case C_MO_EDITION:
				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("TypeLabel.Message.Annuler")))) {
					int rang = modelTypeLabelArticle.getListeObjet().indexOf(selectedTypeLabelArticle.getValue());
					remetTousLesChampsApresAnnulationSWT(dbc);
					modelTypeLabelArticle.getListeObjet().set(rang, swtOldTypeLabelArticle);
					writableList = new WritableList(realm, modelTypeLabelArticle.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.setSelection(new StructuredSelection(swtOldTypeLabelArticle), true);
					dao.annuler(taLabelArticle);
					hideDecoratedFields();
				}
				initEtatBouton();

				break;
			case C_MO_CONSULTATION:
				actionFermer.run();
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
	
	protected void initEtatBouton() {
		super.initEtatBouton();
		enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,false);
		
		switch (daoStandard.getModeObjet().getMode()) {
		case C_MO_INSERTION:
			enableActionAndHandler(C_COMMAND_CHOIX_IMAGE_ARTICLE_ID,true);
			break;
		case C_MO_EDITION:
			enableActionAndHandler(C_COMMAND_CHOIX_IMAGE_ARTICLE_ID,true);
			break;
		case C_MO_CONSULTATION:
			enableActionAndHandler(C_COMMAND_CHOIX_IMAGE_ARTICLE_ID,false);
			break;
		default:
			break;
		}
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
						PaTypeLabelArticleSWT paCategorieArticleSWT = new PaTypeLabelArticleSWT(s2,SWT.NULL);
						SWTPaTypeLabelController swtPaTypeCategorieController = new SWTPaTypeLabelController(paCategorieArticleSWT);

						editorCreationId = EditorTypeCategorieArticle.ID;
						editorInputCreation = new EditorInputTypeCategorieArticle();
						
						paramAfficheAideRecherche.setForceAffichageAideRemplie(true);


						ParamAfficheTypeLabelArticle paramAfficheCategorieArticle = new ParamAfficheTypeLabelArticle();
						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
						paramAfficheCategorieArticle.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheCategorieArticle.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaTypeCategorieController;
						parametreEcranCreation = paramAfficheCategorieArticle;

						paramAfficheAideRecherche.setTypeEntite(TaLabelArticle.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_LABEL_ARTICLE);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCodeLabel().getText());
						paramAfficheAideRecherche.setControllerAppelant(getThis());
						paramAfficheAideRecherche.setModel(swtPaTypeCategorieController.getModelTypeLabelArticle());
						paramAfficheAideRecherche.setTypeObjet(swtPaTypeCategorieController.getClassModel());
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_LABEL_ARTICLE);
					}
					break;
				case C_MO_EDITION:
				case C_MO_INSERTION:
//					if(getFocusCourantSWT().equals(vue.getTfCategorieMere())){
//						PaTypeLabelArticleSWT paCategorieArticleSWT = new PaTypeLabelArticleSWT(s2,SWT.NULL);
//						SWTPaTypeLabelController swtPaTypeCategorieController = new SWTPaTypeLabelController(paCategorieArticleSWT);
//
//						editorCreationId = EditorTypeCategorieArticle.ID;
//						editorInputCreation = new EditorInputTypeCategorieArticle();
//						
//						paramAfficheAideRecherche.setForceAffichageAideRemplie(true);
//
//
//						ParamAfficheTypeLabelArticle paramAfficheCategorieArticle = new ParamAfficheTypeLabelArticle();
//						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
//						paramAfficheCategorieArticle.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//						paramAfficheCategorieArticle.setEcranAppelant(paAideController);
//						controllerEcranCreation = swtPaTypeCategorieController;
//						parametreEcranCreation = paramAfficheCategorieArticle;
//
//						paramAfficheAideRecherche.setTypeEntite(TaLabelArticle.class);
//						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_LABEL_ARTICLE);
//						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCodeCategorie().getText());
//						paramAfficheAideRecherche.setControllerAppelant(getThis());
//						paramAfficheAideRecherche.setModel(swtPaTypeCategorieController.getModelTypeLabelArticle());
//						paramAfficheAideRecherche.setTypeObjet(swtPaTypeCategorieController.getClassModel());
//						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_LABEL_ARTICLE);
//					}
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
	
	protected class HandlerChoixImage extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException{
			try {
				actChoixImage();
			} catch (Exception e1) {
				logger.error("Erreur : actionPerformed", e1);
			}
			return event;
		}
	}

	public void actChoixImage() {
		FileDialog d = new FileDialog(vue.getShell());
		d.setFilterExtensions(UtilImage.FILTRE_EXTENSION_IMAGE);
		d.setFilterNames(UtilImage.FILTRE_LIBELLE_IMAGE);
		String chemin = d.open();
		if(chemin!=null) {
			vue.getTfCheminImageArticle().setText(chemin);
			vue.getTfCheminImageArticle().setToolTipText(chemin);
			vue.getTfNomImageArticle().setText(new File(chemin).getName());
			changementImage(chemin);
		}
	}
	
	/**
	 * 
	 * @param cheminImage - chemin du fichier image à afficher dans la zone de prévisualisation, NULL pour effacer.
	 */
	private void changementImage(String cheminImage) {
		UtilImage util = new UtilImage();
		imagePreview = util.changementImage(cheminImage, imagePreview, vue.getGrpPreview());
	}
	
	public IStatus validateUI() {
		return null;
	}

	public IStatus validateUIField(String nomChamp,Object value) {
		String validationContext = "TYPE_LABEL_ARTICLE";
		try {
			IStatus s = null;
			boolean verrouilleModifCode = false;
			int change=0;

			TaLabelArticle u = new TaLabelArticle();
			PropertyUtils.setSimpleProperty(u, nomChamp, value);
			if(((SWTTypeLabelArticle) selectedTypeLabelArticle.getValue()).getIdLabelArticle()!=null) {
				u.setIdLabelArticle(((SWTTypeLabelArticle) selectedTypeLabelArticle.getValue()).getIdLabelArticle());
			}

			//				if(nomChamp.equals(Const.C_CODE_T_TVA)){
			//					verrouilleModifCode = true;
			//				}

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

	}

	@Override
	protected void actEnregistrer() throws Exception {
		EntityTransaction transaction = dao.getEntityManager().getTransaction();
		try {
			ctrlUnChampsSWT(getFocusCourantSWT());
			ctrlTousLesChampsAvantEnregistrementSWT();
			dao.begin(transaction);
			
			if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
				LgrDozerMapper<SWTTypeLabelArticle,TaLabelArticle> mapper = new LgrDozerMapper<SWTTypeLabelArticle,TaLabelArticle>();
				mapper.map((SWTTypeLabelArticle) selectedTypeLabelArticle.getValue(),taLabelArticle);

				dao.enregistrerMerge(taLabelArticle);

				
			} else if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)) {
				LgrDozerMapper<SWTTypeLabelArticle,TaLabelArticle> mapper = new LgrDozerMapper<SWTTypeLabelArticle,TaLabelArticle>();
				mapper.map((SWTTypeLabelArticle) selectedTypeLabelArticle.getValue(),taLabelArticle);

				taLabelArticle=dao.enregistrerMerge(taLabelArticle);
//				modelTTva.getListeEntity().add(taTTva);
			}
			
			dao.commit(transaction);
			modelTypeLabelArticle.addEntity(taLabelArticle);
			transaction = null;
			
			actRefresh(); //deja present
			
		} finally {
			if(transaction!=null && transaction.isActive()) {
				transaction.rollback();
			}
			initEtatBouton();
		}
	}

	public void initEtatComposant() {
		try {
			vue.getTfCodeLabel().setEditable(!isUtilise());
			changeCouleur(vue);
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
		}

	}
	public boolean isUtilise(){
		return (((SWTTypeLabelArticle)selectedTypeLabelArticle.getValue()).getIdLabelArticle()!=null &&
		!dao.recordModifiable(dao.getNomTable(),
				((SWTTypeLabelArticle)selectedTypeLabelArticle.getValue()).getIdLabelArticle()))||!dao.autoriseModification(taLabelArticle);		
	}
	public SWTTypeLabelArticle getSwtOldTypeLabelArticle() {
		return swtOldTypeLabelArticle;
	}

	public void setSwtOldTypeLabelArticle(SWTTypeLabelArticle swtOldTTva) {
		this.swtOldTypeLabelArticle = swtOldTTva;
	}

	public void setSwtOldTypeLabelArticle() {
		if (selectedTypeLabelArticle!=null && selectedTypeLabelArticle.getValue() != null)
			this.swtOldTypeLabelArticle = SWTTypeLabelArticle.copy((SWTTypeLabelArticle) selectedTypeLabelArticle.getValue());
		else {
			if(tableViewer.selectionGrille(0)){
				this.swtOldTypeLabelArticle = SWTTypeLabelArticle.copy((SWTTypeLabelArticle) selectedTypeLabelArticle.getValue());
				tableViewer.setSelection(new StructuredSelection(
						(SWTTypeLabelArticle) selectedTypeLabelArticle.getValue()), true);
			}}
	}

	public void setVue(PaTypeLabelArticleSWT vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();

		mapComposantDecoratedField.put(vue.getTfCodeLabel(), vue.getFieldCodeLabel());
		mapComposantDecoratedField.put(vue.getTfLibelleLabel(), vue.getFieldLibelleLabel());
		mapComposantDecoratedField.put(vue.getTfDescription(), vue.getFieldDescription());
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
		if (taLabelArticle!=null) { //enregistrement en cours de modification/insertion
			idActuel = taLabelArticle.getIdLabelArticle();
		} else if(selectedTypeLabelArticle!=null && (SWTTypeLabelArticle) selectedTypeLabelArticle.getValue()!=null) {
			idActuel = ((SWTTypeLabelArticle) selectedTypeLabelArticle.getValue()).getIdLabelArticle();
		}

		if (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION) == 0) {
			//rafraichissement des valeurs dans la grille
			writableList = new WritableList(realm, modelTypeLabelArticle.remplirListe(), classModel);
			tableViewer.setInput(writableList);
		} else {
			if (taLabelArticle!=null && selectedTypeLabelArticle!=null && (SWTTypeLabelArticle) selectedTypeLabelArticle.getValue()!=null) {
				new LgrDozerMapper<TaLabelArticle, SWTTypeLabelArticle>().
					map(taLabelArticle, (SWTTypeLabelArticle) selectedTypeLabelArticle.getValue());
			}
		}

		if(idActuel!=0) {
			SWTTypeLabelArticle resultRecherche = modelTypeLabelArticle.recherche(Const.C_ID_LABEL_ARTICLE, idActuel);
			if(resultRecherche!=null)
				tableViewer.setSelection(new StructuredSelection(resultRecherche));
			else
				tableViewer.selectionGrille(0);	
		}else
			tableViewer.selectionGrille(0);		
	}

	public ModelGeneralObjet<SWTTypeLabelArticle,TaLabelArticleDAO,TaLabelArticle> getModelTypeLabelArticle() {
		return modelTypeLabelArticle;
	}

	public TaLabelArticleDAO getDao() {
		return dao;
	}

	public TaLabelArticle getTaLabelArticle() {
		return taLabelArticle;
	}
	private void changementDeSelection() {
		if(selectedTypeLabelArticle!=null && selectedTypeLabelArticle.getValue()!=null) {
			if(((SWTTypeLabelArticle) selectedTypeLabelArticle.getValue()).getIdLabelArticle()!=null) {
				taLabelArticle = dao.findById(((SWTTypeLabelArticle) selectedTypeLabelArticle.getValue()).getIdLabelArticle());
				changementImage(taLabelArticle.getCheminImageLabelArticle());
			}
			//null a tester ailleurs, car peut etre null en cas d'insertion
			fireChangementDeSelection(new ChangementDeSelectionEvent(SWTPaTypeLabelController.this));
		}
	}

	public void actAfficherTous() throws Exception{
		vue.getGrille().setVisible(true);  
		vue.getBtnTous().setVisible(false);
		vue.getLaTitreGrille().setVisible(true);
		vue.getCompositeForm().setWeights(new int[]{50,100});
		dao.setJPQLQuery(dao.getJPQLQueryInitial());
		modelTypeLabelArticle.setJPQLQuery(null);
		modelTypeLabelArticle.setListeEntity(null);
		try {
			actRefresh();
		} catch (Exception e1) {
			logger.error("", e1);
		}
	}
}

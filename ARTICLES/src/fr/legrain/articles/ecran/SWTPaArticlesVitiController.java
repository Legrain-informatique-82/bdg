//package fr.legrain.articles.ecran;
//
//import java.io.File;
//import java.lang.reflect.InvocationTargetException;
//import java.math.BigDecimal;
//import java.math.MathContext;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityTransaction;
//
//import org.apache.commons.beanutils.PropertyUtils;
//import org.apache.commons.lang.StringUtils;
//import org.apache.log4j.Logger;
//import org.eclipse.core.commands.ExecutionEvent;
//import org.eclipse.core.commands.ExecutionException;
//import org.eclipse.core.commands.IHandler;
//import org.eclipse.core.commands.NotEnabledException;
//import org.eclipse.core.commands.NotHandledException;
//import org.eclipse.core.commands.common.NotDefinedException;
//import org.eclipse.core.databinding.Binding;
//import org.eclipse.core.databinding.DataBindingContext;
//import org.eclipse.core.databinding.UpdateValueStrategy;
//import org.eclipse.core.databinding.beans.BeanProperties;
//import org.eclipse.core.databinding.beans.BeansObservables;
//import org.eclipse.core.databinding.conversion.IConverter;
//import org.eclipse.core.databinding.observable.ChangeEvent;
//import org.eclipse.core.databinding.observable.IChangeListener;
//import org.eclipse.core.databinding.observable.Realm;
//import org.eclipse.core.databinding.observable.list.WritableList;
//import org.eclipse.core.databinding.observable.value.IObservableValue;
//import org.eclipse.core.runtime.IConfigurationElement;
//import org.eclipse.core.runtime.IExtension;
//import org.eclipse.core.runtime.IExtensionPoint;
//import org.eclipse.core.runtime.IExtensionRegistry;
//import org.eclipse.core.runtime.IStatus;
//import org.eclipse.core.runtime.Path;
//import org.eclipse.core.runtime.Platform;
//import org.eclipse.core.runtime.Status;
//import org.eclipse.jface.action.Action;
//import org.eclipse.jface.databinding.swt.SWTObservables;
//import org.eclipse.jface.databinding.swt.WidgetProperties;
//import org.eclipse.jface.databinding.viewers.ViewersObservables;
//import org.eclipse.jface.dialogs.IInputValidator;
//import org.eclipse.jface.dialogs.InputDialog;
//import org.eclipse.jface.dialogs.MessageDialog;
//import org.eclipse.jface.fieldassist.DecoratedField;
//import org.eclipse.jface.preference.IPreferenceStore;
//import org.eclipse.jface.resource.ImageDescriptor;
//import org.eclipse.jface.viewers.StructuredSelection;
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.events.ModifyEvent;
//import org.eclipse.swt.events.ModifyListener;
//import org.eclipse.swt.events.SelectionAdapter;
//import org.eclipse.swt.events.SelectionEvent;
//import org.eclipse.swt.events.SelectionListener;
//import org.eclipse.swt.widgets.Button;
//import org.eclipse.swt.widgets.Combo;
//import org.eclipse.swt.widgets.Composite;
//import org.eclipse.swt.widgets.Control;
//import org.eclipse.swt.widgets.Display;
//import org.eclipse.swt.widgets.Menu;
//import org.eclipse.swt.widgets.Shell;
//import org.eclipse.swt.widgets.Table;
//import org.eclipse.swt.widgets.Text;
//import org.eclipse.ui.IEditorInput;
//import org.eclipse.ui.IEditorPart;
//import org.eclipse.ui.PlatformUI;
//import org.osgi.framework.Bundle;
//
//import fr.legrain.articles.ArticlesPlugin;
//import fr.legrain.articles.dao.TaArticle;
//import fr.legrain.articles.dao.TaArticleDAO;
//import fr.legrain.articles.dao.TaArticleViti;
//import fr.legrain.articles.dao.TaArticleVitiDAO;
//import fr.legrain.articles.dao.TaCatalogueWeb;
//import fr.legrain.articles.dao.TaFamille;
//import fr.legrain.articles.dao.TaFamilleDAO;
//import fr.legrain.articles.dao.TaPrix;
//import fr.legrain.articles.dao.TaPrixDAO;
//import fr.legrain.articles.dao.TaRapportUnite;
//import fr.legrain.articles.dao.TaRapportUniteDAO;
//import fr.legrain.articles.dao.TaTTva;
//import fr.legrain.articles.dao.TaTTvaDAO;
//import fr.legrain.articles.dao.TaTva;
//import fr.legrain.articles.dao.TaTvaDAO;
//import fr.legrain.articles.dao.TaUnite;
//import fr.legrain.articles.dao.TaUniteDAO;
//import fr.legrain.articles.divers.LgrUpdateValueStrategyComboSensRapportUnite;
//import fr.legrain.articles.editor.ArticleMultiPageEditor;
//import fr.legrain.articles.editor.EditorArticle;
//import fr.legrain.articles.editor.EditorFamille;
//import fr.legrain.articles.editor.EditorInputArticle;
//import fr.legrain.articles.editor.EditorInputFamille;
//import fr.legrain.articles.editor.EditorInputTva;
//import fr.legrain.articles.editor.EditorInputTypeTVA;
//import fr.legrain.articles.editor.EditorInputUnite;
//import fr.legrain.articles.editor.EditorTva;
//import fr.legrain.articles.editor.EditorTypeTVA;
//import fr.legrain.articles.editor.EditorUnite;
//import fr.legrain.documents.dao.TaLFacture;
//import fr.legrain.dossier.dao.TaInfoEntreprise;
//import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
//import fr.legrain.edition.actions.AttributElementResport;
//import fr.legrain.edition.actions.BaseImpressionEdition;
//import fr.legrain.edition.actions.ConstEdition;
//import fr.legrain.edition.actions.MakeDynamiqueReport;
//import fr.legrain.edition.dynamique.FonctionGetInfosXmlAndProperties;
//import fr.legrain.gestCom.Appli.Const;
//import fr.legrain.gestCom.Appli.LgrDozerMapper;
//import fr.legrain.gestCom.Appli.ModelGeneralObjet;
//import fr.legrain.gestCom.Module_Articles.SWTArticle;
//import fr.legrain.gestCom.Module_Articles.SWTArticleViti;
//import fr.legrain.gestCom.Module_Articles.SWTRapportUnite;
//import fr.legrain.gestCom.Module_Articles.SWTTTva;
//import fr.legrain.gestCom.Module_Articles.SWTTva;
//import fr.legrain.gestCom.Module_Articles.SWTUnite;
//import fr.legrain.gestCom.gestComBd.gestComBdPlugin;
//import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
//import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
//import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
//import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
//import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
//import fr.legrain.gestCom.librairiesEcran.swt.LgrUpdateValueStrategy;
//import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideRechercheSWT;
//import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideSWT;
//import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheVisualisation;
//import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
//import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideRechercheControllerSWT;
//import fr.legrain.gestCom.librairiesEcran.workbench.AnnuleToutEvent;
//import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDePageEvent;
//import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDeSelectionEvent;
//import fr.legrain.gestCom.librairiesEcran.workbench.ChangementMasterEntityEvent;
//import fr.legrain.gestCom.librairiesEcran.workbench.IChangementMasterEntityListener;
//import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
//import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
//import fr.legrain.lib.data.ExceptLgr;
//import fr.legrain.lib.data.InfosVerifSaisie;
//import fr.legrain.lib.data.LibChaine;
//import fr.legrain.lib.data.LibConversion;
//import fr.legrain.lib.data.ModeObjet;
//import fr.legrain.lib.data.VerrouInterface;
//import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
//import fr.legrain.lib.gui.ParamAffiche;
//import fr.legrain.lib.gui.ResultAffiche;
//import fr.legrain.lib.gui.RetourEcranEvent;
//import fr.legrain.lib.gui.RetourEcranListener;
//import fr.legrain.lib.gui.aide.PaAideRechercheSWT;
//import fr.legrain.lib.gui.aide.PaAideSWT;
//import fr.legrain.lib.gui.aide.ParamAfficheAide;
//import fr.legrain.lib.gui.aide.ResultAide;
//import fr.legrain.lib.gui.grille.LgrTableViewer;
//import fr.legrain.lib.gui.grille.LgrViewerSupport;
//import fr.legrain.libMessageLGR.LgrMess;
//
//
//public class SWTPaArticlesVitiController extends SWTPaArticlesController
//implements RetourEcranListener {
//
//	static Logger logger = Logger.getLogger(SWTPaArticlesVitiController.class.getName()); 
//	private PaArticleSWT vue = null;
//	private TaArticleVitiDAO dao = null;//new TaArticleDAO(getEm());
//	private String idArticle = null;
//	
//	private Object ecranAppelant = null;
//	private SWTArticleViti swtArticle;
//	private SWTArticleViti swtOldArticle;
//	private Realm realm;
//	private DataBindingContext dbc;
//	
//	private Class classModel = SWTArticleViti.class;
//	private ModelGeneralObjet<SWTArticleViti,TaArticleVitiDAO,TaArticleViti> modelArticle = null;//new ModelGeneralObjet<SWTArticleViti,TaArticleVitiDAO,TaArticle>(dao,classModel);
//	private LgrTableViewer tableViewer;
//	private WritableList writableList;
//	private IObservableValue selectedArticle;
//	private String[] listeChamp;
//	private String nomClassController = this.getClass().getSimpleName();
//
//	private MapChangeListener changeListener = new MapChangeListener();
//	
//	private LgrDozerMapper<SWTArticleViti,TaArticleViti> mapperUIToModel = new LgrDozerMapper<SWTArticleViti,TaArticleViti>();
//	private LgrDozerMapper<TaArticleViti,SWTArticleViti> mapperModelToUI = new LgrDozerMapper<TaArticleViti,SWTArticleViti>();
//	private TaArticleViti taArticle = null;
//	
//	private List<IExtensionEcran> liste = null;
//	
//	public SWTPaArticlesVitiController(PaArticleSWT vue) {
//		this(vue,null,null);
//	}
//
//	public SWTPaArticlesVitiController(PaArticleSWT vue,EntityManager em, List<IExtensionEcran> liste) {
//		//super(vue,em);
//		this.vue = vue;
//		if(em!=null) {
//			setEm(em);
//		}
//		//createContributors();
//		this.liste = liste;
//		dao = new TaArticleVitiDAO(getEm());
//		
//		modelArticle = new ModelGeneralObjet<SWTArticleViti,TaArticleVitiDAO,TaArticleViti>(dao,classModel);
//		setVue(vue);
//		// a faire avant l'initialisation du Binding
//		vue.getGrille().addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				setSwtOldArticle();
//			}
//		});
//		vue.getShell().addShellListener(this);
//
//		//TODO #JPA a supprimer completement au lieu de les caches
//		vue.getBtnAjoutPrix().setVisible(false);
//		vue.getTfCOMMENTAIRE_ARTICLE().setVisible(false);
//		vue.getLaCOMMENTAIRE_ARTICLE().setVisible(false);
//
//		// Branchement action annuler : empeche la fermeture automatique de la
//		// fenetre sur ESC
//		vue.getShell().addTraverseListener(new Traverse());
//
//		initController();
//	}
//
//	private void initController() {
//		try {
//			setGrille(vue.getGrille());
//			
//			for (IExtensionEcran e : liste) {
//				e.ecranSWT(vue);
//			}
//			
//			initSashFormWeight();
//			setDaoStandard(dao);
//			super.setDaoStandard(dao);
//			//super.dao = dao;
//			setTableViewerStandard(tableViewer);
//			setDbcStandard(dbc);
//
//			initMapComposantChamps();
//			initVerifySaisie();
//			initMapComposantDecoratedField();
//			listeComponentFocusableSWT(listeComposantFocusable);
//			initFocusOrder();
//			initActions();
//			initDeplacementSaisie(listeComposantFocusable);
//
//			branchementBouton();
//
//			Menu popupMenuFormulaire = new Menu(vue.getShell(), SWT.POP_UP);
//			Menu popupMenuGrille = new Menu(vue.getShell(), SWT.POP_UP);
//			Menu[] tabPopups = new Menu[] { popupMenuFormulaire, popupMenuGrille };
//			this.initPopupAndButtons(mapActions, tabPopups);
//			vue.getPaCorpsFormulaire().setMenu(popupMenuFormulaire);
//			vue.getTfCODE_ARTICLE().setMenu(popupMenuFormulaire);
//			vue.getPaGrille().setMenu(popupMenuGrille);
//			vue.getGrille().setMenu(popupMenuGrille);
//
//			initEtatBouton();
//		} catch (Exception e) {
//			vue.getLaMessage().setText(e.getMessage());
//			logger.error("Erreur : PaArticlesController", e);
//		}
//	}
//
//	public void bind(PaArticleSWT paArticleSWT) {
//		try {
//			realm = SWTObservables.getRealm(vue.getParent().getDisplay());
//
//			tableViewer = new LgrTableViewer(paArticleSWT.getGrille());
//			tableViewer.createTableCol(classModel,paArticleSWT.getGrille(), nomClassController,
//					Const.C_FICHIER_LISTE_CHAMP_GRILLE,0);
//			listeChamp = tableViewer.setListeChampGrille(nomClassController,
//					Const.C_FICHIER_LISTE_CHAMP_GRILLE);
//			
//			// Set up data binding.
//			LgrViewerSupport.bind(tableViewer, 
//					new WritableList(modelArticle.remplirListe(), classModel),
//					BeanProperties.values(listeChamp)
//					);
//
//			if(idArticle!=null ) {
//				modelArticle.setJPQLQuery(dao.getJPQLQuery());
//			}
//
//			selectedArticle = ViewersObservables.observeSingleSelection(tableViewer);
//			dbc = new DataBindingContext(realm);
//
//			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
//			tableViewer.selectionGrille(0);
//
//			setTableViewerStandard(tableViewer);
//			setDbcStandard(dbc);
//			
//			vue.getCbSensRapport().setItems(new String[]{LgrUpdateValueStrategyComboSensRapportUnite.sensDiviserTrue,LgrUpdateValueStrategyComboSensRapportUnite.sensMultiplierFalse});
//			vue.getCbSensRapport().select(0);
//			vue.getCbSensRapport().addSelectionListener(new SelectionListener() {
//				
//				@Override
//				public void widgetSelected(SelectionEvent e) {
//					calculeRapportUnite2();
//				}
//				
//				@Override
//				public void widgetDefaultSelected(SelectionEvent e) {
//					calculeRapportUnite2();
//				}
//			});
//			mapComposantUpdateValueStrategy = new HashMap<Control, LgrUpdateValueStrategy>();
//			mapComposantUpdateValueStrategy.put(vue.getCbSensRapport(), new LgrUpdateValueStrategyComboSensRapportUnite());
//
//			bindingFormMaitreDetail(dbc, realm, selectedArticle,classModel);
//			
//			bindPhraseRapportUnite();
//			
//			changementDeSelection();
//			selectedArticle.addChangeListener(new IChangeListener() {
//
//				public void handleChange(ChangeEvent event) {
//					changementDeSelection();
//				}
//
//			});
//			
//			initEtatComposant();
//			
////			for (IExtensionEcran e : liste) {
////				e.bind(vue);
////			}
//
//		} catch (Exception e) {
//			vue.getLaMessage().setText(e.getMessage());
//			logger.error("", e);
//		}
//	}
//	
//	protected void initVerifySaisie() {
//		super.initVerifySaisie();
//	}
//
//	
//	protected void initMapComposantChamps() {
//		if (mapComposantChamps == null)
//			mapComposantChamps = new LinkedHashMap<Control, String>();
//
//		if (listeComposantFocusable == null)
//			listeComposantFocusable = new ArrayList<Control>();
//		listeComposantFocusable.add(vue.getGrille());
//
//		vue.getTfCODE_ARTICLE().setToolTipText(Const.C_CODE_ARTICLE);
//		vue.getTfLIBELLEC_ARTICLE().setToolTipText(Const.C_LIBELLEC_ARTICLE);
//		vue.getTfLIBELLEL_ARTICLE().setToolTipText(Const.C_LIBELLEL_ARTICLE);
//		vue.getTfCODE_FAMILLE().setToolTipText(Const.C_CODE_FAMILLE);
//		vue.getTfNUMCPT_ARTICLE().setToolTipText(Const.C_NUMCPT_ARTICLE);
//		vue.getTfCODE_TVA().setToolTipText(Const.C_CODE_TVA);
//		vue.getTfCODE_T_TVA().setToolTipText(Const.C_CODE_T_TVA);
//		vue.getTfHauteur().setToolTipText(Const.C_HAUTEUR_ARTICLE);
//		vue.getTfLongueur().setToolTipText(Const.C_LONGUEUR_ARTICLE);
//		vue.getTfLargeur().setToolTipText(Const.C_LARGEUR_ARTICLE);
//		vue.getTfPoids().setToolTipText(Const.C_POIDS_ARTICLE);
//		vue.getTfDIVERS_ARTICLE().setToolTipText(Const.C_DIVERS_ARTICLE);
//		vue.getTfSTOCK_MIN_ARTICLE().setToolTipText(Const.C_STOCK_MIN_ARTICLE);
//
//		vue.getTfCODE_UNITE().setToolTipText(Const.C_CODE_UNITE);
//		vue.getTfPRIX_PRIX().setToolTipText(Const.C_PRIX_PRIX);
//		vue.getTfPRIXTTC_PRIX().setToolTipText(Const.C_PRIXTTC_PRIX);		
//		vue.getTfCODE_UNITE2().setToolTipText(Const.C_CODE_UNITE2);
//		vue.getTfRAPPORT().setToolTipText(Const.C_RAPPORT);
//		vue.getTfDECIMAL().setToolTipText(Const.C_NB_DECIMAL);
//
//		mapComposantChamps.put(vue.getTfCODE_ARTICLE(), Const.C_CODE_ARTICLE);
//		mapComposantChamps.put(vue.getTfLIBELLEC_ARTICLE(), Const.C_LIBELLEC_ARTICLE);
//		mapComposantChamps.put(vue.getTfLIBELLEL_ARTICLE(), Const.C_LIBELLEL_ARTICLE);
//		mapComposantChamps.put(vue.getTfCODE_FAMILLE(), Const.C_CODE_FAMILLE);
//		mapComposantChamps.put(vue.getTfCODE_TVA(), Const.C_CODE_TVA);
//		mapComposantChamps.put(vue.getTfCODE_UNITE(), Const.C_CODE_UNITE);
//		mapComposantChamps.put(vue.getTfPRIX_PRIX(), Const.C_PRIX_PRIX);
//		mapComposantChamps.put(vue.getTfPRIXTTC_PRIX(), Const.C_PRIXTTC_PRIX);
//
//		mapComposantChamps.put(vue.getTfCODE_UNITE2(), Const.C_CODE_UNITE2);
//		mapComposantChamps.put(vue.getTfRAPPORT(), Const.C_RAPPORT);
//		mapComposantChamps.put(vue.getTfDECIMAL(), Const.C_NB_DECIMAL);
//		mapComposantChamps.put(vue.getCbSensRapport(), Const.C_SENS_RAPPORT);
//
//		mapComposantChamps.put(vue.getTfSTOCK_MIN_ARTICLE(), Const.C_STOCK_MIN_ARTICLE);
//		mapComposantChamps.put(vue.getTfNUMCPT_ARTICLE(), Const.C_NUMCPT_ARTICLE);
//		mapComposantChamps.put(vue.getCbActif(), Const.C_ACTIF_ARTICLE);
//		mapComposantChamps.put(vue.getTfCODE_T_TVA(), Const.C_CODE_T_TVA);
//		mapComposantChamps.put(vue.getTfHauteur(), Const.C_HAUTEUR_ARTICLE);
//		mapComposantChamps.put(vue.getTfLongueur(), Const.C_LONGUEUR_ARTICLE);
//		mapComposantChamps.put(vue.getTfLargeur(), Const.C_LARGEUR_ARTICLE);
//		mapComposantChamps.put(vue.getTfPoids(), Const.C_POIDS_ARTICLE);
//		mapComposantChamps.put(vue.getTfDIVERS_ARTICLE(), Const.C_DIVERS_ARTICLE);
//		
//		for (IExtensionEcran e : liste) {
//			e.initMapComposantChamps(mapComposantChamps);
//		}
//
//		for (Control c : mapComposantChamps.keySet()) {
//			listeComposantFocusable.add(c);
//		}
//
//		listeComposantFocusable.add(vue.getBtnAjoutPrix());
//
//		listeComposantFocusable.add(vue.getBtnEnregistrer());
//		listeComposantFocusable.add(vue.getBtnInserer());
//		listeComposantFocusable.add(vue.getBtnModifier());
//		listeComposantFocusable.add(vue.getBtnSupprimer());
//		listeComposantFocusable.add(vue.getBtnFermer());
//		listeComposantFocusable.add(vue.getBtnAnnuler());
//		listeComposantFocusable.add(vue.getBtnImprimer());
//		listeComposantFocusable.add(vue.getBtnTous());
//
//		if (mapInitFocus == null)
//			mapInitFocus = new LinkedHashMap<ModeObjet.EnumModeObjet, Control>();
//		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION, vue
//				.getTfCODE_ARTICLE());
//		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue
//				.getTfCODE_ARTICLE());
//		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION, vue
//				.getGrille());
//
//		activeModifytListener();
//
//		vue.getTfSTOCK_MIN_ARTICLE().addVerifyListener(queDesChiffres);
//		vue.getTfNUMCPT_ARTICLE().addVerifyListener(queDesChiffres);		
//		vue.getTfPRIX_PRIX().addVerifyListener(queDesChiffres);
//		vue.getTfPRIXTTC_PRIX().addVerifyListener(queDesChiffres);
//		vue.getTfRAPPORT().addVerifyListener(queDesChiffres);
//		vue.getTfDECIMAL().addVerifyListener(queDesChiffres);
//		
//		vue.getTfHauteur().addVerifyListener(queDesChiffres);
//		vue.getTfLongueur().addVerifyListener(queDesChiffres);
//		vue.getTfLargeur().addVerifyListener(queDesChiffres);
//		vue.getTfPoids().addVerifyListener(queDesChiffres);
//		
//	}
//	
//	protected void actDupliquer() throws Exception {
//		try {
//			if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
//				VerrouInterface.setVerrouille(true);
//				setSwtOldArticle();
//				InputDialog input =new InputDialog(vue.getShell(), "Dupliquer l'article", "Saisissez le nouveau code article", swtOldArticle.getCodeArticle(), new IInputValidator() {
//					
//				@Override
//					public String isValid(String newText) {
//						//newText=newText.toUpperCase();
//							return null;
//					}
//				});
//				input.open();
//				if(validateUIField(Const.C_CODE_ARTICLE,input.getValue().toUpperCase()).getSeverity()!=IStatus.ERROR){
////					taArticle=dao.findById(swtOldArticle.getIdArticle());
//					TaArticleViti articleDupl=dao.findById(swtOldArticle.getIdArticle());
//					taArticle = new TaArticleViti();
//					taArticle=(TaArticleViti)articleDupl.clone();
//					taArticle.setCodeArticle(input.getValue().toUpperCase());
//					swtArticle=new SWTArticleViti();
//					mapperModelToUI.map(taArticle, swtArticle);
//					swtArticle.setIdArticle(null);
//					validateUIField(Const.C_CODE_ARTICLE,swtArticle.getCodeArticle());//permet de verrouiller le code genere
//					dao.inserer(taArticle);
//					modelArticle.getListeObjet().add(swtArticle);
//					writableList = new WritableList(realm, modelArticle.getListeObjet(), classModel);
//					tableViewer.setInput(writableList);
//					tableViewer.refresh();
//					tableViewer.setSelection(new StructuredSelection(swtArticle));
//					initEtatBouton();
//				}
//			}
//
//		} catch (Exception e1) {
//			vue.getLaMessage().setText(e1.getMessage());
//			logger.error("Erreur : actionPerformed", e1);
//		} finally {
//			initEtatComposant();
//			VerrouInterface.setVerrouille(false);
//		}
//	}
//	
//	@Override
//	protected void actInserer() throws Exception {
//		try {
//			if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
//				VerrouInterface.setVerrouille(true);
//				setSwtOldArticle();
//				swtArticle = new SWTArticleViti();
//				swtArticle.setPrixPrix(new BigDecimal(0));
//				swtArticle.setPrixttcPrix(new BigDecimal(0));
//				swtArticle.setCodeArticle(dao.genereCode());
//				swtArticle.setActif(true);
//				validateUIField(Const.C_CODE_ARTICLE,swtArticle.getCodeArticle());//permet de verrouiller le code genere
//				swtArticle.setStockMinArticle(new BigDecimal(-1));
//
//				taArticle = new TaArticleViti();
//				dao.inserer(taArticle);
//				modelArticle.getListeObjet().add(swtArticle);
//				writableList = new WritableList(realm, modelArticle.getListeObjet(), classModel);
//				tableViewer.setInput(writableList);
//				tableViewer.refresh();
//				tableViewer.setSelection(new StructuredSelection(swtArticle));
//				initEtatBouton();
//			}
//
//		} catch (Exception e1) {
//			vue.getLaMessage().setText(e1.getMessage());
//			logger.error("Erreur : actionPerformed", e1);
//		} finally {
//			initEtatComposant();
//			VerrouInterface.setVerrouille(false);
//		}
//	}
//
//	@Override
//	protected void actModifier() throws Exception {
//		try {
//			if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
//				if(!LgrMess.isDOSSIER_EN_RESEAU()){
//					setSwtOldArticle();
//					taArticle = dao.findById(((SWTArticleViti) selectedArticle.getValue()).getIdArticle());
//				}else{
//				if(!setSwtOldArticleRefresh())
//					throw new Exception();
//				}
//				dao.modifier(taArticle);
//				initEtatBouton();
//			}
//		} catch (Exception e1) {
//			vue.getLaMessage().setText(e1.getMessage());
//			logger.error("Erreur : actionPerformed", e1);
//		}
//	}
//
//	@Override
//	protected void actSupprimer() throws Exception {
//		EntityTransaction transaction = null;
//		
//		try {
//			VerrouInterface.setVerrouille(true);
//			if(isUtilise())MessageDialog.openInformation(vue.getShell(), MessagesEcran
//					.getString("Message.Attention"), MessagesEcran
//					.getString("Message.suppression"));
//			else
//				if (MessageDialog.openConfirm(vue.getShell(), MessagesEcran
//						.getString("Message.Attention"), MessagesEcran
//						.getString("Articles.Message.Supprimer"))) {
//					transaction = dao.getEntityManager().getTransaction();
//					dao.begin(transaction);
//					TaArticleViti u = dao.findById(((SWTArticleViti) selectedArticle.getValue()).getIdArticle());
//					dao.supprimer(u);
//					dao.commit(transaction);
//					Object suivant=tableViewer.getElementAt(tableViewer.getTable().getSelectionIndex()+1);
//					if(containsEntity(u)) modelArticle.getListeEntity().remove(u);
//					taArticle=null;
//					transaction = null;
//					dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
//					if(suivant!=null)tableViewer.setSelection(new StructuredSelection(suivant),true);
//					else tableViewer.selectionGrille(0);
//					actRefresh(); //ajouter pour tester jpa
//				}
//		} catch (ExceptLgr e1) {
//			vue.getLaMessage().setText(e1.getMessage());
//			logger.error("Erreur : actionPerformed", e1);
//		} finally {
//			if(transaction!=null && transaction.isActive()) {
//				transaction.rollback();
//			}
//			initEtatBouton();
//			VerrouInterface.setVerrouille(false);
//		}
//	}
//
//	@Override
//	protected void actAnnuler() throws Exception {
//		try {
//			VerrouInterface.setVerrouille(true);
//			switch (dao.getModeObjet().getMode()) {
//			case C_MO_INSERTION:
//				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(), MessagesEcran
//						.getString("Message.Attention"), MessagesEcran
//						.getString("Articles.Message.Annuler")))) {
//					remetTousLesChampsApresAnnulationSWT(dbc);
//					if(((SWTArticleViti) selectedArticle.getValue()).getIdArticle()==null){
//						modelArticle.getListeObjet().remove(((SWTArticleViti) selectedArticle.getValue()));
//						writableList = new WritableList(realm, modelArticle.getListeObjet(), classModel);
//						tableViewer.setInput(writableList);
//						tableViewer.refresh();
//						tableViewer.selectionGrille(0);
//					}
//					dao.annuler(taArticle);
//					hideDecoratedFields();
//					if(!annuleToutEnCours) {
//						fireAnnuleTout(new AnnuleToutEvent(this));
//					}
//				}
//				initEtatBouton();
//				break;
//			case C_MO_EDITION:
//				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(), MessagesEcran
//						.getString("Message.Attention"), MessagesEcran
//						.getString("Articles.Message.Annuler")))) {
//					int rang = modelArticle.getListeObjet().indexOf(selectedArticle.getValue());
//					remetTousLesChampsApresAnnulationSWT(dbc);
//					modelArticle.getListeObjet().set(rang, swtOldArticle);
//					writableList = new WritableList(realm, modelArticle.getListeObjet(), classModel);
//					tableViewer.setInput(writableList);
//					tableViewer.refresh();
//					tableViewer.setSelection(new StructuredSelection(swtOldArticle), true);
//					dao.annuler(taArticle);
//					changementDeSelection(); //pour réintialiser les autres onglets à partir des données de la bdd
//					hideDecoratedFields();
//					if(!annuleToutEnCours) {
//						fireAnnuleTout(new AnnuleToutEvent(this));
//					}
//				}
//				initEtatBouton();
//
//				break;
//			case C_MO_CONSULTATION:
////				actionFermer.run();
//				break;
//			default:
//				break;
//			}
//		} finally {
//			VerrouInterface.setVerrouille(false);
//		}
//	}
//
//	@Override
//	protected boolean aideDisponible() {
//		boolean result = super.aideDisponible();
////		switch ((SWTPaArticlesVitiController.this.dao.getModeObjet().getMode())) {
////		case C_MO_CONSULTATION:
////			if(getFocusCourantSWT().equals(vue.getGrille())) 
////				result = true;
////			break;
////
////		case C_MO_EDITION:
////		case C_MO_INSERTION:
////			if(getFocusCourantSWT().equals(vue.getTfCODE_FAMILLE())
////					|| getFocusCourantSWT().equals(vue.getTfCODE_T_TVA())
////					|| getFocusCourantSWT().equals(vue.getTfCODE_TVA())
////					|| getFocusCourantSWT().equals(vue.getTfCODE_UNITE()))
////				result = true;
////			if(getFocusCourantSWT().equals(vue.getTfCODE_UNITE2())
////					&& vue.getTfCODE_UNITE2().getEditable())
////				result = true;
////			break;
////		default:
////			break;
////		}
//		return result;
//	}
//
//	@Override
//	protected void actAide(String message) throws Exception {
//		boolean aide = getActiveAide();
//		setActiveAide(true);
//		if(aideDisponible()){
//			try {
//				VerrouInterface.setVerrouille(true);
//				vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
//				ParamAfficheAideRechercheSWT paramAfficheAideRecherche = new ParamAfficheAideRechercheSWT();
//				paramAfficheAideRecherche.setMessage(message);
//				// Creation de l'ecran d'aide principal
//				Shell s = new Shell(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), LgrShellUtil.styleLgr);
//				PaAideSWT paAide = new PaAideSWT(s, SWT.NONE);
//				SWTPaAideControllerSWT paAideController = new SWTPaAideControllerSWT(paAide);
//				/***************************************************************/
//				LgrPartListener.getLgrPartListener().setLgrActivePart(null);
//				IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new EditorInputAide(), EditorAide.ID);
//				LgrPartListener.getLgrPartListener().setLgrActivePart(e);
//				LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
//				paAideController = new SWTPaAideControllerSWT(((EditorAide)e).getComposite());
//				((LgrEditorPart)e).setController(paAideController);
//				((LgrEditorPart)e).setPanel(((EditorAide)e).getComposite());
//				/***************************************************************/
//				JPABaseControllerSWTStandard controllerEcranCreation = null;
//				ParamAffiche parametreEcranCreation = null;
//				IEditorPart editorCreation = null;
//				String editorCreationId = null;
//				IEditorInput editorInputCreation = null;
//				Shell s2 = new Shell(s, LgrShellUtil.styleLgr);
//
//				switch ((SWTPaArticlesVitiController.this.dao.getModeObjet().getMode())) {
//				case C_MO_CONSULTATION:
//					if(getFocusCourantSWT().equals(vue.getGrille())){
//						PaArticleSWT paArticlesSWT = new PaArticleSWT(s2,SWT.NULL);
//						SWTPaArticlesVitiController swtPaArticlesController = new SWTPaArticlesVitiController(paArticlesSWT);
//
//						editorCreationId = ArticleMultiPageEditor.ID_EDITOR;
//						editorInputCreation = new EditorInputArticle();
//
//						ParamAfficheArticles paramAfficheArticles = new ParamAfficheArticles();
//						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
//						paramAfficheArticles.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//						paramAfficheArticles.setEcranAppelant(paAideController);
//						controllerEcranCreation = swtPaArticlesController;
//						parametreEcranCreation = paramAfficheArticles;
//
//						paramAfficheAideRecherche.setTypeEntite(TaArticleViti.class);
//						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_ARTICLE);
//						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_ARTICLE().getText());
//						paramAfficheAideRecherche.setControllerAppelant(SWTPaArticlesVitiController.this);
//						paramAfficheAideRecherche.setModel(swtPaArticlesController.getModelArticle());
//						paramAfficheAideRecherche.setTypeObjet(swtPaArticlesController.getClassModel());
//						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_ARTICLE);
//					}
//					break;
//
//				case C_MO_EDITION:
//				case C_MO_INSERTION:
//					if(getFocusCourantSWT().equals(vue.getTfCODE_FAMILLE())){
//
//						PaFamilleSWT paFamilleSWT = new PaFamilleSWT(s2,SWT.NULL); 
//						SWTPaFamilleController swtPaFamilleController = new SWTPaFamilleController(paFamilleSWT);
//
//						editorCreationId = EditorFamille.ID;
//						editorInputCreation = new EditorInputFamille();
//
//						ParamAfficheFamille paramAfficheFamille = new ParamAfficheFamille();
//						paramAfficheAideRecherche.setJPQLQuery(new TaFamilleDAO(getEm()).getJPQLQuery());
//						paramAfficheFamille.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//						paramAfficheFamille.setEcranAppelant(paAideController);
//						/* 
//						 * controllerEcranCreation ne sert plus a rien, pour l'editeur de creation, on creer un nouveau controller
//						 */
//						controllerEcranCreation = swtPaFamilleController;
//						parametreEcranCreation = paramAfficheFamille;
//
//						paramAfficheAideRecherche.setTypeEntite(TaFamille.class);
//						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_FAMILLE);
//						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_FAMILLE().getText());
//						paramAfficheAideRecherche.setControllerAppelant(SWTPaArticlesVitiController.this);
//						paramAfficheAideRecherche.setModel(swtPaFamilleController.getModelFamille());
//						paramAfficheAideRecherche.setTypeObjet(swtPaFamilleController.getClassModel());
//
//						paramAfficheAideRecherche.setChampsIdentifiant(swtPaFamilleController.getDao().getChampIdTable());
//					}
//					if(getFocusCourantSWT().equals(vue.getTfCODE_T_TVA())){
//						PaTTVASWT paTTvaSWT = new PaTTVASWT(s2,SWT.NULL);
//						SWTPaTTvaController swtPaTTvaController = new SWTPaTTvaController(paTTvaSWT);
//
//						editorCreationId = EditorTypeTVA.ID;
//						editorInputCreation = new EditorInputTypeTVA();
//
//						ParamAfficheTTva paramAfficheTTva = new ParamAfficheTTva();
//						paramAfficheAideRecherche.setJPQLQuery(new TaTTvaDAO(getEm()).getJPQLQuery());
//						paramAfficheTTva.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//						paramAfficheTTva.setEcranAppelant(paAideController);
//						controllerEcranCreation = swtPaTTvaController;
//						parametreEcranCreation = paramAfficheTTva;
//
//						paramAfficheAideRecherche.setTypeEntite(TaTTva.class);
//						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_T_TVA);
//						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_T_TVA().getText());
//						paramAfficheAideRecherche.setControllerAppelant(SWTPaArticlesVitiController.this);
//						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTTva,TaTTvaDAO,TaTTva>(SWTTTva.class,dao.getEntityManager()));
//						paramAfficheAideRecherche.setTypeObjet(swtPaTTvaController.getClassModel());
//
//						paramAfficheAideRecherche.setChampsIdentifiant(swtPaTTvaController.getDao().getChampIdTable());
//					}
//					if(getFocusCourantSWT().equals(vue.getTfCODE_TVA())){
//						PaTVASWT paTvaSWT = new PaTVASWT(s2,SWT.NULL);
//						SWTPaTvaController swtPaTvaController = new SWTPaTvaController(paTvaSWT);
//
//						editorCreationId = EditorTva.ID;
//						editorInputCreation = new EditorInputTva();
//
//						ParamAfficheTva paramAfficheTva = new ParamAfficheTva();
//						paramAfficheAideRecherche.setJPQLQuery(new TaTvaDAO(getEm()).getJPQLQuery());
//						paramAfficheTva.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//						paramAfficheTva.setEcranAppelant(paAideController);
//						controllerEcranCreation = swtPaTvaController;
//						parametreEcranCreation = paramAfficheTva;
//
//						paramAfficheAideRecherche.setTypeEntite(TaTva.class);
//						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_TVA);
//						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_TVA().getText());
//						paramAfficheAideRecherche.setControllerAppelant(SWTPaArticlesVitiController.this);
//						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTva,TaTvaDAO,TaTva>(SWTTva.class,dao.getEntityManager()));
//						paramAfficheAideRecherche.setTypeObjet(swtPaTvaController.getClassModel());
//
//						paramAfficheAideRecherche.setChampsIdentifiant(swtPaTvaController.getDao().getChampIdTable());
//					}
//					if(getFocusCourantSWT().equals(vue.getTfCODE_UNITE())){
//						PaUniteSWT paUniteSWT = new PaUniteSWT(s2,SWT.NULL);
//						SWTPaUniteController swtPaUniteController = new SWTPaUniteController(paUniteSWT);
//
//						editorCreationId = EditorUnite.ID;
//						editorInputCreation = new EditorInputUnite();
//
//						ParamAfficheUnite paramAfficheUnite = new ParamAfficheUnite();
//						paramAfficheAideRecherche.setJPQLQuery(new TaUniteDAO(getEm()).getJPQLQuery());
//						paramAfficheUnite.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//						paramAfficheUnite.setEcranAppelant(paAideController);
//						controllerEcranCreation = swtPaUniteController;
//						parametreEcranCreation = paramAfficheUnite;
//
//						paramAfficheAideRecherche.setTypeEntite(TaUnite.class);
//						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_UNITE);
//						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_UNITE().getText());
//						paramAfficheAideRecherche.setControllerAppelant(SWTPaArticlesVitiController.this);
//						paramAfficheAideRecherche.setModel(new TaUniteDAO(getEm()).modelObjetUniteArticle(taArticle.getCodeArticle()));
//						paramAfficheAideRecherche.setTypeObjet(swtPaUniteController.getClassModel());
//
//						paramAfficheAideRecherche.setChampsIdentifiant(swtPaUniteController.getDao().getChampIdTable());
//					}
//					if(getFocusCourantSWT().equals(vue.getTfCODE_UNITE2())){
//						PaUniteSWT paUniteSWT = new PaUniteSWT(s2,SWT.NULL);
//						SWTPaUniteController swtPaUniteController = new SWTPaUniteController(paUniteSWT);
//
//						editorCreationId = EditorUnite.ID;
//						editorInputCreation = new EditorInputUnite();
//
//						ParamAfficheUnite paramAfficheUnite = new ParamAfficheUnite();
//						paramAfficheAideRecherche.setJPQLQuery(new TaUniteDAO(getEm()).getJPQLQuery());
//						paramAfficheUnite.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//						paramAfficheUnite.setEcranAppelant(paAideController);
//						controllerEcranCreation = swtPaUniteController;
//						parametreEcranCreation = paramAfficheUnite;
//
//						paramAfficheAideRecherche.setTypeEntite(TaUnite.class);
//						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_UNITE);
//						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_UNITE2().getText());
//						paramAfficheAideRecherche.setControllerAppelant(SWTPaArticlesVitiController.this);
//						paramAfficheAideRecherche.setModel(new TaUniteDAO(getEm()).modelObjetUniteArticle(taArticle.getCodeArticle()));
//						paramAfficheAideRecherche.setTypeObjet(swtPaUniteController.getClassModel());
//
//						paramAfficheAideRecherche.setChampsIdentifiant(swtPaUniteController.getDao().getChampIdTable());
//					}
//					break;
//				default:
//					break;
//				}
//				if (paramAfficheAideRecherche.getJPQLQuery() != null) {
//
//					PaAideRechercheSWT paAideRecherche1 = new PaAideRechercheSWT(s,
//							SWT.NULL);
//					SWTPaAideRechercheControllerSWT paAideRechercheController1 = new SWTPaAideRechercheControllerSWT(
//							paAideRecherche1);
//
//					// Parametrage de la recherche
//					paramAfficheAideRecherche.setFocusSWT(((PaAideRechercheSWT) paAideRechercheController1.getVue()).getTfChoix());
//					paramAfficheAideRecherche.setRefCreationSWT(controllerEcranCreation);
//					paramAfficheAideRecherche.setEditorCreation(editorCreation);
//					paramAfficheAideRecherche.setEditorCreationId(editorCreationId);
//					paramAfficheAideRecherche.setEditorInputCreation(editorInputCreation);
//					paramAfficheAideRecherche.setParamEcranCreation(parametreEcranCreation);
//					paramAfficheAideRecherche.setShellCreation(s2);
//					paAideRechercheController1.configPanel(paramAfficheAideRecherche);
//
//					// Ajout d'une recherche
//					paAideController.addRecherche(paAideRechercheController1,
//							paramAfficheAideRecherche.getTitreRecherche());
//
//					// Parametrage de l'ecran d'aide principal
//					ParamAfficheAideSWT paramAfficheAideSWT = new ParamAfficheAideSWT();
//					ParamAfficheAide paramAfficheAide = new ParamAfficheAide();
//
//					// enregistrement pour le retour de l'ecran d'aide
//					paAideController.addRetourEcranListener(SWTPaArticlesVitiController.this);
//					Control focus = vue.getShell().getDisplay().getFocusControl();
//					// affichage de l'ecran d'aide principal (+ ses recherches)
//
//
//					dbc.getValidationStatusMap().removeMapChangeListener(changeListener);
//					/*****************************************************************/
//					paAideController.configPanel(paramAfficheAide);
//					/*****************************************************************/
//					dbc.getValidationStatusMap().addMapChangeListener(changeListener);
//
//				}
//
//			} finally {
//				VerrouInterface.setVerrouille(false);
//				vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
//			}
//		}
//	}
//
//	public IStatus validateUIField(String nomChamp,Object value) {
//		//String validationContext = "ARTICLE";
//		try {
//			IStatus s = super.validateUIField(nomChamp, value);
//			
//			return s;
//		} catch (Exception e) {
//			logger.error("",e);
//		}
//		return null;
//	}
//
//	@Override
//	protected void actEnregistrer() throws Exception {
//		EntityTransaction transaction = null;
//		try {
//			boolean declanchementExterne = false;
//			if(sourceDeclencheCommandeController==null) {
//				declanchementExterne = true;
//			}
//			if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
//					|| (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
//
//				if(declanchementExterne) {
//					ctrlTousLesChampsAvantEnregistrementSWT();
//				}
//
//				transaction = dao.getEntityManager().getTransaction();
//				dao.begin(transaction);
//
//				if(declanchementExterne) {
//					mapperUIToModel.map((SWTArticleViti) selectedArticle.getValue(),taArticle);
//				}
//				
//				fireEnregistreTout(new AnnuleToutEvent(this,true));
//				
//				if(!enregistreToutEnCours) {
//					if((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)){	
//						taArticle=dao.enregistrerMerge(taArticle);
//						//					modelArticle.getListeEntity().add(taArticle);
//					}
//					else taArticle=dao.enregistrerMerge(taArticle);
//
//
//					dao.commit(transaction);
//					if(!containsEntity(taArticle)) 
//						modelArticle.getListeEntity().add(taArticle);
//					actRefresh(); //deja present
//				}
//				transaction = null;
//				
//			}
//		} catch (Exception e) {
//			logger.error("",e);
//
//			if(transaction!=null && transaction.isActive()) {
//				transaction.rollback();
//			}
//		} finally {
//			initEtatBouton();
//		}
//	}
//	
//	public SWTArticleViti getSwtOldArticle() {
//		return swtOldArticle;
//	}
//
//	public void setSwtOldArticle(SWTArticleViti swtOldArticle) {
//		this.swtOldArticle = swtOldArticle;
//	}
//
//	public void setSwtOldArticle() {
//
//		if (selectedArticle.getValue() != null)
//			this.swtOldArticle = SWTArticleViti.copy((SWTArticleViti) selectedArticle.getValue());
//		else {
//			if(tableViewer.selectionGrille(0)){
//				this.swtOldArticle = SWTArticleViti.copy((SWTArticleViti) selectedArticle.getValue());
//				tableViewer.setSelection(new StructuredSelection(
//						(SWTArticleViti) selectedArticle.getValue()), true);
//			}}
//	}
//
//	public boolean containsEntity(TaArticleViti entite){
//		if(modelArticle.getListeEntity()!=null){
//			for (Object e : modelArticle.getListeEntity()) {
//				if(((TaArticleViti)e).getIdArticle()==
//					entite.getIdArticle())return true;
//			}
//		}
//		return false;
//	}
//	
//	public boolean setSwtOldArticleRefresh() {
//		try {	
//			if (selectedArticle.getValue()!=null){
//				TaArticleViti taArticleOld =dao.findById(taArticle.getIdArticle());
//				taArticleOld=dao.refresh(taArticleOld);
//				if(containsEntity(taArticle)) 
//					modelArticle.getListeEntity().remove(taArticle);
//				if(!taArticle.getVersionObj().equals(taArticleOld.getVersionObj())){
////					if(containsEntity(taArticle)) 
////						modelArticle.getListeEntity().remove(taArticle);
//					taArticle=taArticleOld;
//					if(!containsEntity(taArticle)) 
//						modelArticle.getListeEntity().add(taArticle);					
//					actRefresh();
//					taArticle=taArticleOld;
//					changementDeSelection();
//					if(!containsEntity(taArticleOld)) 
//						modelArticle.getListeEntity().add(taArticleOld);
//					dao.messageNonAutoriseModification();
//				}
//				taArticle=taArticleOld;
//				fireChangementMaster(new ChangementMasterEntityEvent(this,taArticle,true));
//				this.swtOldArticle=SWTArticleViti.copy((SWTArticleViti)selectedArticle.getValue());
//			}
//			return true;
//		} catch (Exception e) {
//			return false;
//		}		
//	}
//	
//	@Override
//	protected void initMapComposantDecoratedField() {
//		if (mapComposantDecoratedField == null)
//			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();
//
//		mapComposantDecoratedField.put(vue.getTfCODE_ARTICLE(), vue.getFieldCODE_ARTICLE());
//		mapComposantDecoratedField.put(vue.getTfLIBELLEC_ARTICLE(), vue.getFieldLIBELLEC_ARTICLE());
//		mapComposantDecoratedField.put(vue.getTfLIBELLEL_ARTICLE(), vue.getFieldLIBELLEL_ARTICLE());
//		mapComposantDecoratedField.put(vue.getTfCODE_FAMILLE(), vue.getFieldCODE_FAMILLE());
//		mapComposantDecoratedField.put(vue.getTfNUMCPT_ARTICLE(), vue.getFieldNUMCPT_ARTICLE());
//		mapComposantDecoratedField.put(vue.getTfCODE_TVA(), vue.getFieldCODE_TVA());
//		mapComposantDecoratedField.put(vue.getTfCODE_T_TVA(), vue.getFieldCODE_T_TVA());
//		mapComposantDecoratedField.put(vue.getTfDIVERS_ARTICLE(), vue.getFieldDIVERS_ARTICLE());
//		mapComposantDecoratedField.put(vue.getTfCOMMENTAIRE_ARTICLE(), vue.getFieldCOMMENTAIRE_ARTICLE());
//		mapComposantDecoratedField.put(vue.getTfSTOCK_MIN_ARTICLE(), vue.getFieldSTOCK_MIN_ARTICLE());
//		
//		mapComposantDecoratedField.put(vue.getTfCODE_UNITE(), vue.getFieldCODE_UNITE());
//		mapComposantDecoratedField.put(vue.getTfPRIX_PRIX(), vue.getFieldPRIX_PRIX());
//		mapComposantDecoratedField.put(vue.getTfPRIXTTC_PRIX(), vue.getFieldPRIXTTC_PRIX());
//		
//		mapComposantDecoratedField.put(vue.getTfCODE_UNITE2(), vue.getFieldCODE_UNITE2());
//		mapComposantDecoratedField.put(vue.getTfRAPPORT(), vue.getFieldRAPPORT());
//		mapComposantDecoratedField.put(vue.getTfDECIMAL(), vue.getFieldDECIMAL());
//		
//		mapComposantDecoratedField.put(vue.getTfHauteur(), vue.getFieldHauteur());
//		mapComposantDecoratedField.put(vue.getTfLongueur(), vue.getFieldLongueur());
//		mapComposantDecoratedField.put(vue.getTfLargeur(), vue.getFieldLargeur());
//		mapComposantDecoratedField.put(vue.getTfPoids(), vue.getFieldPoids());
//	}
//
//	protected void actSelection() throws Exception {
//		try{
//			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
//			openEditor(new fr.legrain.visualisation.editor.EditorInputSelectionVisualisation(), 
//					fr.legrain.visualisation.editor.EditorSelectionVisualisation.ID);
//			LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
//
//			ParamAfficheVisualisation paramAfficheSelectionVisualisation = new ParamAfficheVisualisation();
//			paramAfficheSelectionVisualisation.setModule("article");
//			paramAfficheSelectionVisualisation.setNomClassController(nomClassController);
//			paramAfficheSelectionVisualisation.setNomRequete(Const.C_NOM_VU_ARTICLE);
//
//			((LgrEditorPart)e).setPanel(((LgrEditorPart)e).getControllerSelection().getVue());
//			((LgrEditorPart)e).getControllerSelection().configPanel(paramAfficheSelectionVisualisation);
//
//		}catch (Exception e) {
//			logger.error("",e);
//		}	
//	}
//	
//	@Override
//	protected void actRefresh() throws Exception {
//		try{
//			vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));		
//		//repositionnement sur la valeur courante
//		int idActuel = 0;
//		if (taArticle!=null) { //enregistrement en cours de modification/insertion
//			idActuel = taArticle.getIdArticle();
//		} else if(selectedArticle!=null && (SWTArticleViti) selectedArticle.getValue()!=null) {
//			idActuel = ((SWTArticleViti) selectedArticle.getValue()).getIdArticle();
//		}
//
//		if (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION) == 0) {
//			//rafraichissement des valeurs dans la grille
//			writableList = new WritableList(realm, modelArticle.remplirListe(), classModel);
//			tableViewer.setInput(writableList);
//		} else {
//			if (taArticle!=null && selectedArticle!=null && (SWTArticleViti) selectedArticle.getValue()!=null) {
//				mapperModelToUI.map(taArticle, (SWTArticleViti) selectedArticle.getValue());
//			}
//		}
//
//		if(idActuel!=0) {
//			tableViewer.setSelection(new StructuredSelection(modelArticle.recherche(Const.C_ID_ARTICLE
//					, idActuel)),true);
//		}else
//			tableViewer.selectionGrille(0);				
//		}finally{
//			vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
//		}
//	}
//
//
////	public ModelGeneralObjet<SWTArticleViti,TaArticleVitiDAO,TaArticleViti>  getModelArticle() {
////		return modelArticle;
////	}
//
//	public SWTArticleViti getSwtArticle() {
//		return swtArticle;
//	}
//
//	public TaArticleViti getTaArticle() {
//		return taArticle;
//	}
//
////	public TaArticleVitiDAO getDao() {
////		return dao;
////	}
//}

package fr.legrain.articles.ecran;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
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
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
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
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;

import fr.legrain.articles.ArticlesPlugin;
import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.articles.dao.TaCatalogueWeb;
import fr.legrain.articles.dao.TaFamille;
import fr.legrain.articles.dao.TaFamilleDAO;
import fr.legrain.articles.dao.TaPrix;
import fr.legrain.articles.dao.TaPrixDAO;
import fr.legrain.articles.dao.TaRTaTitreTransport;
import fr.legrain.articles.dao.TaRTaTitreTransportDAO;
import fr.legrain.articles.dao.TaRapportUnite;
import fr.legrain.articles.dao.TaRapportUniteDAO;
import fr.legrain.articles.dao.TaTTva;
import fr.legrain.articles.dao.TaTTvaDAO;
import fr.legrain.articles.dao.TaTva;
import fr.legrain.articles.dao.TaTvaDAO;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.articles.dao.TaUniteDAO;
import fr.legrain.articles.divers.LgrUpdateValueStrategyComboSensRapportUnite;
import fr.legrain.articles.editor.ArticleMultiPageEditor;
import fr.legrain.articles.editor.EditorFamille;
import fr.legrain.articles.editor.EditorInputArticle;
import fr.legrain.articles.editor.EditorInputFamille;
import fr.legrain.articles.editor.EditorInputTva;
import fr.legrain.articles.editor.EditorInputTypeTVA;
import fr.legrain.articles.editor.EditorInputUnite;
import fr.legrain.articles.editor.EditorTva;
import fr.legrain.articles.editor.EditorTypeTVA;
import fr.legrain.articles.editor.EditorUnite;
import fr.legrain.articles.preferences.PreferenceConstants;
import fr.legrain.documents.dao.TaLFacture;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.edition.actions.AttributElementResport;
import fr.legrain.edition.actions.BaseImpressionEdition;
import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.edition.actions.MakeDynamiqueReport;
import fr.legrain.edition.dynamique.FonctionGetInfosXmlAndProperties;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Articles.ModelArticles;
import fr.legrain.gestCom.Module_Articles.SWTArticle;
import fr.legrain.gestCom.Module_Articles.SWTTTva;
import fr.legrain.gestCom.Module_Articles.SWTTva;
import fr.legrain.gestCom.Module_GestionCapsules.SWTTitreTransport;
import fr.legrain.gestCom.gestComBd.gestComBdPlugin;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.gestCom.librairiesEcran.swt.LgrUpdateValueStrategy;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideRechercheSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheVisualisation;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideRechercheControllerSWT;
import fr.legrain.gestCom.librairiesEcran.workbench.AnnuleToutEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDePageEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDeSelectionEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementMasterEntityEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.IChangementMasterEntityListener;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.gestioncapsules.controllers.SWTPaTitreTransport;
import fr.legrain.gestioncapsules.dao.TaTitreTransport;
import fr.legrain.gestioncapsules.dao.TaTitreTransportDAO;
import fr.legrain.gestioncapsules.ecrans.PaTitreTransport;
import fr.legrain.gestioncapsules.ecrans.ParamAfficheTitreTansport;
import fr.legrain.gestioncapsules.editors.EditorInputTitreTransport;
import fr.legrain.gestioncapsules.editors.EditorTitreTransport;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.InfosVerifSaisie;
import fr.legrain.lib.data.LibChaine;
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
//import fr.legrain.recherchermulticrit.Activator;
//import fr.legrain.recherchermulticrit.ecrans.CompositePublipostageArticleController;
import fr.legrain.pointLgr.dao.TaArticlePoint;
import fr.legrain.pointLgr.dao.TaArticlePointDAO;


public class SWTPaArticlesController extends JPABaseControllerSWTStandard
implements RetourEcranListener {

	static Logger logger = Logger.getLogger(SWTPaArticlesController.class.getName()); 
	private PaArticleSWT vue = null;
	protected TaArticleDAO dao = null;//new TaArticleDAO(getEm());
	private String idArticle = null;
	
	private Object ecranAppelant = null;
	private SWTArticle swtArticle;
	private SWTArticle swtOldArticle;
	private Realm realm;
	private DataBindingContext dbc;
	
	private Class classModel = SWTArticle.class;
//	private ModelArticles modelArticle = new ModelArticles();
	private ModelGeneralObjet<SWTArticle,TaArticleDAO,TaArticle> modelArticle = null;//new ModelGeneralObjet<SWTArticle,TaArticleDAO,TaArticle>(dao,classModel);
	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private IObservableValue selectedArticle;
	private String[] listeChamp;
	private String nomClassController = this.getClass().getSimpleName();

	private MapChangeListener changeListener = new MapChangeListener();


	public static final String C_COMMAND_PRIX_ID = "fr.legrain.gestionCommerciale.articles.prix";
	private HandlerAjoutPrix handlerAjoutPrix = new HandlerAjoutPrix();
	
	public static final String C_COMMAND_DUPLIQUER_ID = "fr.legrain.gestionCommerciale.articles.dupliquer";
	private HandlerDupliquer handlerDupliquer = new HandlerDupliquer();
	
	public static final String C_COMMAND_RECALCULER_ID = "fr.legrain.gestionCommerciale.articles.recalculerPrix";
	private HandlerRecalculer handlerRecalculer = new HandlerRecalculer();	
	
	private LgrDozerMapper<SWTArticle,TaArticle> mapperUIToModel = new LgrDozerMapper<SWTArticle,TaArticle>();
	private LgrDozerMapper<TaArticle,SWTArticle> mapperModelToUI = new LgrDozerMapper<TaArticle,SWTArticle>();
	private TaArticle taArticle = null;
	
	private List<IExtensionEcran> liste = null;
	
	protected SWTPaArticlesController() {}
	
	public SWTPaArticlesController(PaArticleSWT vue) {
		this(vue,null);
	}

	public SWTPaArticlesController(PaArticleSWT vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		createContributors();
		dao = new TaArticleDAO(getEm());
		setVue(vue);
		// a faire avant l'initialisation du Binding
		vue.getGrille().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setSwtOldArticle();
			}
		});
		vue.getShell().addShellListener(this);

		//TODO #JPA a supprimer completement au lieu de les caches
		vue.getBtnAjoutPrix().setVisible(false);
		vue.getTfCOMMENTAIRE_ARTICLE().setVisible(false);
		vue.getLaCOMMENTAIRE_ARTICLE().setVisible(false);

		// Branchement action annuler : empeche la fermeture automatique de la
		// fenetre sur ESC
		vue.getShell().addTraverseListener(new Traverse());

		initController();
	}
	

	private void createContributors() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint("Articles.editorEcranArticles"); //$NON-NLS-1$
		if (point != null) {
			IExtension[] extensions = point.getExtensions();
			for (int i = 0; i < extensions.length; i++) {
				IConfigurationElement confElements[] = extensions[i].getConfigurationElements();
				for (int jj = 0; jj < confElements.length; jj++) {
					try {
						String editorClass = confElements[jj].getAttribute("classe");//$NON-NLS-1$

						if (editorClass == null)
							continue;

						Object o = confElements[jj].createExecutableExtension("classe");
						if(liste == null)
							liste = new ArrayList<IExtensionEcran>();
						liste.add(((IExtensionEcran)o));

					} catch (Exception e) {
						logger.error("Erreur : PaArticlesController", e);
					}
				}
			}
		}
	}

	private void initController() {
		try {
			setGrille(vue.getGrille());
			
//			for (IExtensionEcran e : liste) {
//				//e.ecranSWT(vue);
//			}
			if(liste!=null) {
				vue.extensionTitreTransport(vue);
			}
			modelArticle=new ModelGeneralObjet<SWTArticle, TaArticleDAO, TaArticle>(dao, classModel);
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
			vue.getTfCODE_ARTICLE().setMenu(popupMenuFormulaire);
			vue.getPaGrille().setMenu(popupMenuGrille);
			vue.getGrille().setMenu(popupMenuGrille);

			initEtatBouton();
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaArticlesController", e);
		}
	}

	public void bind(PaArticleSWT paArticleSWT) {
		try {
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			tableViewer = new LgrTableViewer(paArticleSWT.getGrille());
			tableViewer.createTableCol(classModel,paArticleSWT.getGrille(), nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE,0);
			listeChamp = tableViewer.setListeChampGrille(nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE);

//			ObservableListContentProvider viewerContent = new ObservableListContentProvider();
//			tableViewer.setContentProvider(viewerContent);
//
//			IObservableMap[] attributeMaps = BeansObservables.observeMaps(
//					viewerContent.getKnownElements(), classModel, listeChamp);
//
//			tableViewer.setLabelProvider(new ObservableMapLabelProvider(attributeMaps));
//			writableList = new WritableList(realm, modelArticle.remplirListe(), classModel);
//			tableViewer.setInput(writableList);
			
			// Set up data binding.
			LgrViewerSupport.bind(tableViewer, 
					new WritableList(modelArticle.remplirListe(), classModel),
					BeanProperties.values(listeChamp)
					);

			if(idArticle!=null ) {
				modelArticle.setJPQLQuery(dao.getJPQLQuery());
			}

			selectedArticle = ViewersObservables.observeSingleSelection(tableViewer);
			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			tableViewer.selectionGrille(0);

			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);
			
			vue.getCbSensRapport().setItems(new String[]{LgrUpdateValueStrategyComboSensRapportUnite.sensDiviserTrue,LgrUpdateValueStrategyComboSensRapportUnite.sensMultiplierFalse});
			vue.getCbSensRapport().select(0);
			vue.getCbSensRapport().addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent e) {
					calculeRapportUnite2();
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					calculeRapportUnite2();
				}
			});
			mapComposantUpdateValueStrategy = new HashMap<Control, LgrUpdateValueStrategy>();
			mapComposantUpdateValueStrategy.put(vue.getCbSensRapport(), new LgrUpdateValueStrategyComboSensRapportUnite());

			bindingFormMaitreDetail(dbc, realm, selectedArticle,classModel);
			
			bindPhraseRapportUnite();
			
			changementDeSelection();
			selectedArticle.addChangeListener(new IChangeListener() {

				public void handleChange(ChangeEvent event) {
					changementDeSelection();
				}

			});
			
			initEtatComposant();
			
//			for (IExtensionEcran e : liste) {
//				e.bind(vue);
//			}

		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("", e);
		}
	}
	
	protected BigDecimal calculeRapportUnite2() {
		BigDecimal qte2 = new BigDecimal(0);
		if(selectedArticle!=null && selectedArticle.getValue()!=null) {
			SWTArticle swtArticle = (SWTArticle) selectedArticle.getValue();
			boolean sens = LgrUpdateValueStrategyComboSensRapportUnite.sens(vue.getCbSensRapport().getText());
			//if(swtArticle!=null /*&& swtArticle.getSens()!=null*/ /*&& rapportUniteEstRempli()*/) {
			if(swtArticle!=null && swtArticle.getRapport()!=null) {
				int decimale = swtArticle.getNbDecimal()!=null ? swtArticle.getNbDecimal() : 2;
				//if(swtArticle.getSens()) {
				if(sens) {
					if(swtArticle.getRapport().compareTo(new BigDecimal(0))!=0)
						qte2 = new BigDecimal(1).divide(swtArticle.getRapport(),MathContext.DECIMAL128).setScale(decimale,BigDecimal.ROUND_HALF_UP);
					else
						qte2 = new BigDecimal(0);
				} else {
					qte2 = new BigDecimal(1).multiply(swtArticle.getRapport(),MathContext.DECIMAL128).setScale(decimale,BigDecimal.ROUND_HALF_UP);
				}
			}
			vue.getLaU2PhraseRapport().setText(LibConversion.bigDecimalToString(qte2)+" "+swtArticle.getCodeUnite2());
			vue.getLaRapportPhraseRapport().setText(LibConversion.bigDecimalToString(swtArticle.getRapport()));
			vue.getLaU1PhraseRapport().setText("1 "+swtArticle.getCodeUnite());
		}
		return qte2;
	}
	
	protected void bindPhraseRapportUnite() {
//		ModifyListener l = new ModifyListener() {
//			
//			@Override
//			public void modifyText(ModifyEvent e) {
//				calculeRapportUnite2();
////				if(selectedArticle!=null && selectedArticle.getValue()!=null) {
////					SWTArticle swtArticle = (SWTArticle) selectedArticle.getValue();
////					vue.getLaU2PhraseRapport().setText(LibConversion.bigDecimalToString(calculeRapportUnite2())+" "+swtArticle.getCodeUnite2());
////					vue.getLaRapportPhraseRapport().setText(LibConversion.bigDecimalToString(swtArticle.getRapport()));
////					vue.getLaU1PhraseRapport().setText("1 "+swtArticle.getCodeUnite());
////				}
//				
//			}
//		};
//		
//		vue.getTfCODE_UNITE().addModifyListener(l);
//		vue.getTfCODE_UNITE2().addModifyListener(l);
//		vue.getTfRAPPORT().addModifyListener(l);
//		vue.getTfDECIMAL().addModifyListener(l);
		
		
//		UpdateValueStrategy UpdateValueStrategyRapportUnite2 = new UpdateValueStrategy().setConverter(new IConverter() {
//			@Override
//			public Object getToType() {return String.class;}
//			
//			@Override
//			public Object getFromType() {return String.class;}
//			
//			@Override
//			public Object convert(Object fromObject) {
//				return LibConversion.bigDecimalToString(calculeRapportUnite2())+" "+fromObject;
//			}
//		});
//		
//		dbc.bindValue(WidgetProperties.text().observe(vue.getLaU1PhraseRapport()),
//				WidgetProperties.text(SWT.Modify).observe(vue.getTfCODE_UNITE()),
//				null,
//				new UpdateValueStrategy().setConverter(new IConverter() {
//					@Override
//					public Object getToType() {return String.class;}
//					
//					@Override
//					public Object getFromType() {return String.class;}
//					
//					@Override
//					public Object convert(Object fromObject) {return "1 "+fromObject;}
//				})
//				
//		);
//		dbc.bindValue(WidgetProperties.text().observe(vue.getLaU2PhraseRapport()),
//				WidgetProperties.text(SWT.Modify).observe(vue.getTfCODE_UNITE2()),
//				null,
//				UpdateValueStrategyRapportUnite2
//		);
//		dbc.bindValue(WidgetProperties.text().observe(vue.getLaRapportPhraseRapport()),
//				WidgetProperties.text(SWT.Modify).observe(vue.getTfRAPPORT()),
//				null,
//				null//UpdateValueStrategyRapportUnite2
//		);
	}
	
	protected void changementDeSelection() {
		if(selectedArticle!=null && selectedArticle.getValue()!=null) {
			if(((SWTArticle) selectedArticle.getValue()).getIdArticle()!=null) {
				taArticle = dao.findById(((SWTArticle) selectedArticle.getValue()).getIdArticle());
			}
			//null a tester ailleurs, car peut etre null en cas d'insertion
			fireChangementDeSelection(new ChangementDeSelectionEvent(SWTPaArticlesController.this));
			calculeRapportUnite2();
		}
	}

	public Composite getVue() {
		return vue;
	}

	public ResultAffiche configPanel(ParamAffiche param) {
		Date dateDeb = new Date();
		if (param != null) {
			
			if (param.getFocusDefautSWT() != null && !param.getFocusDefautSWT().isDisposed())
				param.getFocusDefautSWT().setFocus();
			else
				param.setFocusDefautSWT(vue
						.getGrille());
			if(param.getTitreFormulaire()!=null){
				vue.getLaTitreFormulaire().setText(param.getTitreFormulaire());
			} else {
				vue.getLaTitreFormulaire().setText(ParamAfficheArticles.C_TITRE_FORMULAIRE);
			}

			if(param.getTitreGrille()!=null){
				vue.getLaTitreGrille().setText(param.getTitreGrille());
			} else {
				vue.getLaTitreGrille().setText(ParamAfficheArticles.C_TITRE_GRILLE);
			}

			if(param.getSousTitre()!=null){
				vue.getLaTitreFenetre().setText(param.getSousTitre());
			} else {
				vue.getLaTitreFenetre().setText(ParamAfficheArticles.C_SOUS_TITRE);
			}

			if (param.getEcranAppelant() != null) {
				ecranAppelant = param.getEcranAppelant();
			}
			Map<String,String[]> map = dao.getParamWhereSQL();
			if(param.getIdFiche()!=null &&  !param.getIdFiche().equals("")) {
				this.idArticle=param.getIdFiche();
				if(map==null) map = new HashMap<String,String[]>();
				map.clear();
				map.put("a.idArticle",new String[]{"=",idArticle});
				dao.setParamWhereSQL(map);
				vue.getBtnTous().setVisible(true);
				vue.getGrille().setVisible(false);
				vue.getLaTitreGrille().setVisible(false);
				vue.getCompositeForm().setWeights(new int[]{0,100});					
			}
			if(param instanceof ParamAfficheArticles){
				if(((ParamAfficheArticles)param).getIdArticle()!=null){
					this.idArticle=((ParamAfficheArticles)param).getIdArticle();
					if(map==null) map = new HashMap<String,String[]>();
					map.clear();
					map.put("a.idArticle",new String[]{"=",idArticle});
					dao.setParamWhereSQL(map);
					vue.getBtnTous().setVisible(true);
					vue.getGrille().setVisible(false);
					vue.getLaTitreGrille().setVisible(false);
					vue.getCompositeForm().setWeights(new int[]{0,100});					
				}

			}
			modelArticle.setListeEntity(null);
			bind(vue);
			// permet de se positionner sur le 1er enregistrement et de remplir
			// le formulaire
			tableViewer.selectionGrille(0);
			tableViewer.tri(classModel, nomClassController,Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			VerrouInterface.setVerrouille(false);
			setSwtOldArticle();

			if(param.getCodeDocument()!=null) {
				SWTArticle art = modelArticle.recherche(Const.C_CODE_ARTICLE, param.getCodeDocument());
				if(art!=null) {
					tableViewer.setSelection(new StructuredSelection(art),true);
				}
			}

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
		Date dateFin = new Date();
		logger.info("temp config panel "+new Date(dateFin.getTime()-dateDeb.getTime()));

		return null;
	}
	
	protected void initVerifySaisie() {
		if (mapInfosVerifSaisie == null)
			mapInfosVerifSaisie = new HashMap<Control, InfosVerifSaisie>();
		
		mapInfosVerifSaisie.put(vue.getTfPRIX_PRIX(), new InfosVerifSaisie(new TaPrix(),Const.C_PRIX_PRIX,new int[]{InfosVerifSaisie.CTRL_QUE_DES_CHIFFRES_POSITIFS}));
		mapInfosVerifSaisie.put(vue.getTfPRIXTTC_PRIX(), new InfosVerifSaisie(new TaPrix(),Const.C_PRIXTTC_PRIX,new int[]{InfosVerifSaisie.CTRL_QUE_DES_CHIFFRES_POSITIFS}));
		
		mapInfosVerifSaisie.put(vue.getTfCODE_ARTICLE(), new InfosVerifSaisie(new TaArticle(),Const.C_CODE_ARTICLE,null));
		mapInfosVerifSaisie.put(vue.getTfCODE_FAMILLE(), new InfosVerifSaisie(new TaFamille(),Const.C_CODE_FAMILLE,null));
		mapInfosVerifSaisie.put(vue.getTfCODE_T_TVA(), new InfosVerifSaisie(new TaTTva(),Const.C_CODE_T_TVA,null));
		mapInfosVerifSaisie.put(vue.getTfCODE_TVA(), new InfosVerifSaisie(new TaTva(),Const.C_CODE_TVA,null));
		mapInfosVerifSaisie.put(vue.getTfCODE_UNITE(), new InfosVerifSaisie(new TaUnite(),Const.C_CODE_UNITE,null));
		mapInfosVerifSaisie.put(vue.getTfCODE_UNITE2(), new InfosVerifSaisie(new TaUnite(),Const.C_CODE_UNITE,null));
		mapInfosVerifSaisie.put(vue.getTfCOMMENTAIRE_ARTICLE(), new InfosVerifSaisie(new TaArticle(),Const.C_COMMENTAIRE_ARTICLE,null));
		//mapInfosVerifSaisie.put(vue.getTfDECIMAL(), new InfosVerifSaisie(new TaRapportUnite(),Const.C_NB_DECIMAL,null));
		mapInfosVerifSaisie.put(vue.getTfDECIMAL(), new InfosVerifSaisie(new TaLFacture(),Const.C_QTE2_L_DOCUMENT,new int[]{InfosVerifSaisie.CTRL_QUE_DES_CHIFFRES_INTERVALE}));
		mapInfosVerifSaisie.put(vue.getTfDIVERS_ARTICLE(), new InfosVerifSaisie(new TaArticle(),Const.C_DIVERS_ARTICLE,null));
		mapInfosVerifSaisie.put(vue.getTfLIBELLEC_ARTICLE(), new InfosVerifSaisie(new TaArticle(),Const.C_LIBELLEC_ARTICLE,null));
		mapInfosVerifSaisie.put(vue.getTfLIBELLEL_ARTICLE(), new InfosVerifSaisie(new TaArticle(),Const.C_LIBELLEL_ARTICLE,null));
		mapInfosVerifSaisie.put(vue.getTfNUMCPT_ARTICLE(), new InfosVerifSaisie(new TaArticle(),Const.C_NUMCPT_ARTICLE,null));
		mapInfosVerifSaisie.put(vue.getTfRAPPORT(), new InfosVerifSaisie(new TaRapportUnite(),Const.C_RAPPORT,null));
		mapInfosVerifSaisie.put(vue.getTfSTOCK_MIN_ARTICLE(), new InfosVerifSaisie(new TaArticle(),Const.C_STOCK_MIN_ARTICLE,new int[]{InfosVerifSaisie.CTRL_QUE_DES_CHIFFRES}));
		
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

		vue.getTfCODE_ARTICLE().setToolTipText(Const.C_CODE_ARTICLE);
		vue.getTfLIBELLEC_ARTICLE().setToolTipText(Const.C_LIBELLEC_ARTICLE);
		vue.getTfLIBELLEL_ARTICLE().setToolTipText(Const.C_LIBELLEL_ARTICLE);
		vue.getTfCODE_FAMILLE().setToolTipText(Const.C_CODE_FAMILLE);
		vue.getTfNUMCPT_ARTICLE().setToolTipText(Const.C_NUMCPT_ARTICLE);
		vue.getTfCODE_TVA().setToolTipText(Const.C_CODE_TVA);
		vue.getTfCODE_T_TVA().setToolTipText(Const.C_CODE_T_TVA);
		vue.getTfHauteur().setToolTipText(Const.C_HAUTEUR_ARTICLE);
		vue.getTfLongueur().setToolTipText(Const.C_LONGUEUR_ARTICLE);
		vue.getTfLargeur().setToolTipText(Const.C_LARGEUR_ARTICLE);
		vue.getTfPoids().setToolTipText(Const.C_POIDS_ARTICLE);
		vue.getTfDIVERS_ARTICLE().setToolTipText(Const.C_DIVERS_ARTICLE);
		vue.getTfSTOCK_MIN_ARTICLE().setToolTipText(Const.C_STOCK_MIN_ARTICLE);

		vue.getTfCODE_UNITE().setToolTipText(Const.C_CODE_UNITE);
		vue.getTfPRIX_PRIX().setToolTipText(Const.C_PRIX_PRIX);
		vue.getTfPRIXTTC_PRIX().setToolTipText(Const.C_PRIXTTC_PRIX);		
		vue.getTfCODE_UNITE2().setToolTipText(Const.C_CODE_UNITE2);
		vue.getTfRAPPORT().setToolTipText(Const.C_RAPPORT);
		vue.getTfDECIMAL().setToolTipText(Const.C_NB_DECIMAL);
		
		if(liste!=null) {
			vue.getTfTitreTransportU1().setToolTipText(Const.C_CODE_TITRE_TRANSPORT);
			vue.getTfNombreTitreTransportU1().setToolTipText(Const.C_QTE_TITRE_TRANSPORT_L_DOCUMENT);
		}

		mapComposantChamps.put(vue.getTfCODE_ARTICLE(), Const.C_CODE_ARTICLE);
		mapComposantChamps.put(vue.getTfLIBELLEC_ARTICLE(), Const.C_LIBELLEC_ARTICLE);
		mapComposantChamps.put(vue.getTfLIBELLEL_ARTICLE(), Const.C_LIBELLEL_ARTICLE);
		mapComposantChamps.put(vue.getTfCODE_FAMILLE(), Const.C_CODE_FAMILLE);
		mapComposantChamps.put(vue.getTfCODE_TVA(), Const.C_CODE_TVA);
		mapComposantChamps.put(vue.getCbActif(), Const.C_ACTIF_ARTICLE);
		mapComposantChamps.put(vue.getTfCODE_UNITE(), Const.C_CODE_UNITE);
		mapComposantChamps.put(vue.getTfCODE_UNITE2(), Const.C_CODE_UNITE2);
		mapComposantChamps.put(vue.getTfPRIX_PRIX(), Const.C_PRIX_PRIX);
		mapComposantChamps.put(vue.getTfPRIXTTC_PRIX(), Const.C_PRIXTTC_PRIX);

		mapComposantChamps.put(vue.getTfRAPPORT(), Const.C_RAPPORT);
		mapComposantChamps.put(vue.getTfDECIMAL(), Const.C_NB_DECIMAL);
		mapComposantChamps.put(vue.getCbSensRapport(), Const.C_SENS_RAPPORT);

		mapComposantChamps.put(vue.getTfNUMCPT_ARTICLE(), Const.C_NUMCPT_ARTICLE);
		mapComposantChamps.put(vue.getTfCODE_T_TVA(), Const.C_CODE_T_TVA);
		
		if(liste!=null) {
			mapComposantChamps.put(vue.getTfTitreTransportU1(), Const.C_CODE_TITRE_TRANSPORT);
			mapComposantChamps.put(vue.getTfNombreTitreTransportU1(), Const.C_QTE_TITRE_TRANSPORT_L_DOCUMENT);
		}
		
		mapComposantChamps.put(vue.getTfSTOCK_MIN_ARTICLE(), Const.C_STOCK_MIN_ARTICLE);
		mapComposantChamps.put(vue.getTfHauteur(), Const.C_HAUTEUR_ARTICLE);
		mapComposantChamps.put(vue.getTfLargeur(), Const.C_LARGEUR_ARTICLE);
		mapComposantChamps.put(vue.getTfLongueur(), Const.C_LONGUEUR_ARTICLE);
		mapComposantChamps.put(vue.getTfPoids(), Const.C_POIDS_ARTICLE);
		mapComposantChamps.put(vue.getTfDIVERS_ARTICLE(), Const.C_DIVERS_ARTICLE);
		
		for (Control c : mapComposantChamps.keySet()) {
			listeComposantFocusable.add(c);
		}

		listeComposantFocusable.add(vue.getBtnAjoutPrix());

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
				.getTfCODE_ARTICLE());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue
				.getTfCODE_ARTICLE());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION, vue
				.getGrille());

		activeModifytListener();

		vue.getTfSTOCK_MIN_ARTICLE().addVerifyListener(queDesChiffres);
		vue.getTfNUMCPT_ARTICLE().addVerifyListener(queDesChiffres);		
		vue.getTfPRIX_PRIX().addVerifyListener(queDesChiffres);
		vue.getTfPRIXTTC_PRIX().addVerifyListener(queDesChiffres);
		vue.getTfRAPPORT().addVerifyListener(queDesChiffres);
		vue.getTfDECIMAL().addVerifyListener(queDesChiffres);
		
		vue.getTfHauteur().addVerifyListener(queDesChiffres);
		vue.getTfLongueur().addVerifyListener(queDesChiffres);
		vue.getTfLargeur().addVerifyListener(queDesChiffres);
		vue.getTfPoids().addVerifyListener(queDesChiffres);
		
	}

	protected void initActions() {
		mapCommand = new HashMap<String, IHandler>();

		mapCommand.put(C_COMMAND_GLOBAL_MODIFIER_ID, handlerModifier);
		mapCommand.put(C_COMMAND_GLOBAL_ANNULER_ID, handlerAnnuler);
		mapCommand.put(C_COMMAND_GLOBAL_ENREGISTRER_ID, handlerEnregistrer);
		mapCommand.put(C_COMMAND_GLOBAL_INSERER_ID, handlerInserer);

		mapCommand.put(C_COMMAND_GLOBAL_SUPPRIMER_ID, handlerSupprimer);
		mapCommand.put(C_COMMAND_GLOBAL_REFRESH_ID, handlerAfficherTous);
		mapCommand.put(C_COMMAND_GLOBAL_AIDE_ID, handlerAide);
		mapCommand.put(C_COMMAND_GLOBAL_FERMER_ID, handlerFermer);
		mapCommand.put(C_COMMAND_GLOBAL_IMPRIMER_ID, handlerImprimer);

		mapCommand.put(C_COMMAND_GLOBAL_PRECEDENT_ID, handlerPrecedent);
		mapCommand.put(C_COMMAND_GLOBAL_SUIVANT_ID, handlerSuivant);

		mapCommand.put(C_COMMAND_PRIX_ID, handlerAjoutPrix);
		mapCommand.put(C_COMMAND_GLOBAL_SELECTION_ID, handlerSelection);
		mapCommand.put(C_COMMAND_DOCUMENT_AFFICHER_TOUS_ID, handlerAfficherTous);
		mapCommand.put(C_COMMAND_DUPLIQUER_ID, handlerDupliquer);
		

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
		mapActions.put(vue.getBtnAjoutPrix(), C_COMMAND_PRIX_ID);
		mapActions.put(vue.getBtnTous(), C_COMMAND_DOCUMENT_AFFICHER_TOUS_ID);
		
		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID, C_COMMAND_GLOBAL_REFRESH_ID, C_COMMAND_GLOBAL_SELECTION_ID,C_COMMAND_DUPLIQUER_ID };
		mapActions.put(null, tabActionsAutres);

	}

	protected void initEtatBouton() {
		boolean trouve = contientDesEnregistrement(modelArticle.getListeObjet());
		
		switch (daoStandard.getModeObjet().getMode()) {
		case C_MO_INSERTION:
			enableActionAndHandler(C_COMMAND_DUPLIQUER_ID,false);
			break;
		case C_MO_EDITION:
			enableActionAndHandler(C_COMMAND_DUPLIQUER_ID,false);
			break;
		case C_MO_CONSULTATION:
			enableActionAndHandler(C_COMMAND_DUPLIQUER_ID,true);
			break;
		default:
			break;
		}
		initEtatBoutonCommand();
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
					.getString("Articles.Message.Enregistrer"))) {

				try {
					actEnregistrer();
				} catch (Exception e) {
					vue.getLaMessage().setText(e.getMessage());
					logger.error("", e);
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
		if (retour) {
			if(dao.dataSetEnModif())
				try {
					dao.annuler(taArticle);
				} catch (Exception e) {
					throw new ExceptLgr();
				}			
				if (ecranAppelant instanceof SWTPaAideControllerSWT) {
					setListeEntity(getModelArticle().remplirListe());
					dao.initValeurIdTable(taArticle);
					fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
							dao.getChampIdEntite(), dao.getValeurIdTable(),
							selectedArticle.getValue())));

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
					((Text) getFocusAvantAideSWT()).setText(((ResultAffiche) evt
							.getRetour()).getResult());					

					if(getFocusAvantAideSWT().equals(vue.getTfCODE_FAMILLE())){
						TaFamille f = null;
						TaFamilleDAO t = new TaFamilleDAO(getEm());
						f = t.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
						t = null;
						taArticle.setTaFamille(f);
					}
					if(getFocusAvantAideSWT().equals(vue.getTfCODE_T_TVA())){
						TaTTva f = null;
						TaTTvaDAO t = new TaTTvaDAO(getEm());
						f = t.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
						t = null;
						taArticle.setTaTTva(f);
					}
					if(getFocusAvantAideSWT().equals(vue.getTfCODE_TVA())){
						TaTva f = null;
						TaTvaDAO t = new TaTvaDAO(getEm());
						f = t.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
						t = null;
						taArticle.setTaTva(f);
					}
					if(getFocusAvantAideSWT().equals(vue.getTfCODE_UNITE())){
						TaUnite u = null;
						TaUniteDAO t = new TaUniteDAO(getEm());
						u = t.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
						t = null;
						initPrixArticle();
						taArticle.getTaPrix().setTaUnite(u);

					}
					if(getFocusAvantAideSWT().equals(vue.getTfCODE_UNITE2())){
						TaUnite u = null;
						TaUniteDAO t = new TaUniteDAO(getEm());
						u = t.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
						t = null;
						initRapportUniteArticle();
						taArticle.getTaRapportUnite().setTaUnite2(u);

					}	
					if(getFocusAvantAideSWT().equals(vue.getTfTitreTransportU1())){
						TaTitreTransport u = null;
						TaTitreTransportDAO t = new TaTitreTransportDAO(getEm());
						u = t.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
						t = null;
						initTaRTaTitreTransport();
						taArticle.getTaRTaTitreTransport().setTaTitreTransport(u);
						//taArticle.setTaTitreTransport(u);
					}		
					ctrlUnChampsSWT(getFocusAvantAideSWT());
				} catch (Exception e) {
					logger.error("",e);
					if(getFocusAvantAideSWT()!=null)setFocusCourantSWT(getFocusAvantAideSWT());
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

	/**
	 * Creation d'un objet "TaPrix" pour l'objet "TaArticle" gerer par cet ecran
	 * dans le cas ou la propriete taPrix de ce dernier est nulle.
	 */
	private void initPrixArticle() {
		if(taArticle.getTaPrix()==null) {
			//initialisation du prix
			TaPrix p = new TaPrix();
			if(uniteEstRempli()){
				p.setPrixPrix(new BigDecimal(0));
				p.setPrixttcPrix(new BigDecimal(0));				
			}
			p.setTaArticle(taArticle);
			taArticle.setTaPrix(p);
			taArticle.getTaPrixes().add(p);
		}
	}


	private boolean rapportUniteEstRempli() {
		boolean retour=false;
			//if(!vue.getTfCODE_UNITE2().getText().equals(""))return true;
			if(!vue.getTfRAPPORT().getText().equals(""))return true;
			if(!vue.getTfDECIMAL().getText().equals(""))return true;		
		return retour;
	}

	private boolean uniteEstRempli() {
		boolean retour=false;
			if(!vue.getTfCODE_UNITE().getText().equals(""))return true;
			if(!vue.getTfPRIX_PRIX().getText().equals(""))return true;
			if(!vue.getTfPRIXTTC_PRIX().getText().equals(""))return true;		
		return retour;
	}
	
	private boolean crdEstRempli() {
		boolean retour=false;
			//if(vue.getTfNombreTitreTransportU1()!=null && !vue.getTfNombreTitreTransportU1().getText().equals(""))return true;
			if(vue.getTfTitreTransportU1()!=null && !vue.getTfTitreTransportU1().getText().equals(""))return true;
		return retour;
	}
	
	/**
	 * Creation d'un objet "TaRapportUnite" pour l'objet "TaArticle" gerer par cet ecran
	 * dans le cas ou la propriete TaRapportUnite de ce dernier est nulle.
	 */
	private void initRapportUniteArticle() {
		if(taArticle.getTaRapportUnite()==null ) {
			//initialisation du prix
			TaRapportUnite r = new TaRapportUnite();
			r.setTaArticle(taArticle);
			r.setRapport(new BigDecimal(0));
			r.setNbDecimal(0);
			r.setSens(0);
			taArticle.setTaRapportUnite(r);
			taArticle.getTaRapportUnites().add(r);
		}
	}
	
	/**
	 * Creation d'un objet "TaRTaTitreTransport" pour l'objet "TaArticle" gerer par cet ecran
	 * dans le cas ou la propriete TaRTaTitreTransport de ce dernier est nulle.
	 */
	private void initTaRTaTitreTransport() {
		if(taArticle.getTaRTaTitreTransport()==null ) {
			//initialisation
			TaRTaTitreTransport r = null;
			if(taArticle.getIdArticle()!=0) {
				TaRTaTitreTransportDAO dao = new TaRTaTitreTransportDAO();
				r = dao.findByIdArticle(taArticle.getIdArticle());
			}
			if (r==null) 
				r = new TaRTaTitreTransport();
			r.setTaArticle(taArticle);
			taArticle.setTaRTaTitreTransport(r);
			
			if(taArticle.getTaPrix()!=null && taArticle.getTaPrix().getTaUnite()!=null) {
				taArticle.getTaRTaTitreTransport().setTaUnite(taArticle.getTaPrix().getTaUnite());
			}
		}
	}
	
	private void initEtatRapportUnite() {
		boolean enabled = false;
		if(taArticle.getTaPrix()!=null) {
			if(taArticle.getTaPrix().getTaUnite()!=null){
				enabled=true;
			}
		}
		if(!enabled && dao.dataSetEnModif()){
			if(taArticle.getTaRapportUnite()!=null){
				taArticle.removeRapportUnite(taArticle.getTaRapportUnite());
				taArticle.setTaRapportUnite(null);
			}			
			((SWTArticle)selectedArticle.getValue()).setCodeUnite2("");
			((SWTArticle)selectedArticle.getValue()).setRapport(null);
			((SWTArticle)selectedArticle.getValue()).setNbDecimal(null);
		}
		vue.getTfCODE_UNITE2().setEditable(enabled);
		initEtatRapportUniteSuite(taArticle.getTaRapportUnite()!=null && 
				!taArticle.getTaRapportUnite().getTaUnite2().getCodeUnite().isEmpty());

	}
	
	private void initEtatRapportUniteSuite(boolean enabled) {
		if(!enabled && dao.dataSetEnModif()){
			((SWTArticle)selectedArticle.getValue()).setRapport(null);
			((SWTArticle)selectedArticle.getValue()).setNbDecimal(null);
			vue.getTfDECIMAL().setText("");
			vue.getTfRAPPORT().setText("");
		}
		vue.getTfRAPPORT().setEditable(enabled);
		vue.getTfDECIMAL().setEditable(enabled);
		vue.getCbSensRapport().setEnabled(enabled);
		vue.getLaU1PhraseRapport().setVisible(enabled);
		vue.getLaU2PhraseRapport().setVisible(enabled);
		vue.getLaRapportPhraseRapport().setVisible(enabled);
		vue.getLaEgalePhraseRapport().setVisible(enabled);
		
		boolean phraseVisible = false;
		if(taArticle.getIdArticle()!=0 && !vue.getTfRAPPORT().getText().equals("")) {
			//article enregistrer
			phraseVisible = true;
		}
		
		vue.getLaU1PhraseRapport().setVisible(phraseVisible);
		vue.getLaU2PhraseRapport().setVisible(phraseVisible);
		vue.getLaRapportPhraseRapport().setVisible(phraseVisible);
		vue.getLaEgalePhraseRapport().setVisible(phraseVisible);
		//vue.getCbSensRapport().setVisible(phraseVisible);
		
//		if(enabled) {
//			vue.getCbSensRapport().select(0);
//		}
	}
	
	protected void actDupliquer() throws Exception {
		try {
			if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
				VerrouInterface.setVerrouille(true);
				setSwtOldArticle();
				InputDialog input =new InputDialog(vue.getShell(), "Dupliquer l'article", "Saisissez le nouveau code article", swtOldArticle.getCodeArticle(), new IInputValidator() {
					
				@Override
					public String isValid(String newText) {
						//newText=newText.toUpperCase();
							return null;
					}
				});
				input.open();
				if(validateUIField(Const.C_CODE_ARTICLE,input.getValue().toUpperCase()).getSeverity()!=IStatus.ERROR){
//					taArticle=dao.findById(swtOldArticle.getIdArticle());
					TaArticle articleDupl=dao.findById(swtOldArticle.getIdArticle());
					taArticle = new TaArticle();
					taArticle=(TaArticle)articleDupl.clone();
					
					taArticle.setCodeArticle(input.getValue().toUpperCase());
					swtArticle=new SWTArticle();
					mapperModelToUI.map(taArticle, swtArticle);
					swtArticle.setIdArticle(null);
					validateUIField(Const.C_CODE_ARTICLE,swtArticle.getCodeArticle());//permet de verrouiller le code genere
					dao.inserer(taArticle);
					modelArticle.getListeObjet().add(swtArticle);
					writableList = new WritableList(realm, modelArticle.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.setSelection(new StructuredSelection(swtArticle));
					initEtatBouton();
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
	
	protected void actRecalculer() throws Exception {
		try {
			if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
				VerrouInterface.setVerrouille(true);
				setSwtOldArticle();
				InputDialog input =new InputDialog(vue.getShell(), "Dupliquer l'article", "Saisissez le nouveau code article", 
						swtOldArticle.getCodeArticle(), new IInputValidator() {
					
				@Override
					public String isValid(String newText) {
						//newText=newText.toUpperCase();
							return null;
					}
				});
				input.open();
				if(validateUIField(Const.C_CODE_ARTICLE,input.getValue().toUpperCase()).getSeverity()!=IStatus.ERROR){
//					taArticle=dao.findById(swtOldArticle.getIdArticle());
					TaArticle articleDupl=dao.findById(swtOldArticle.getIdArticle());
					taArticle = new TaArticle();
					taArticle=(TaArticle)articleDupl.clone();
					taArticle.setCodeArticle(input.getValue().toUpperCase());
					swtArticle=new SWTArticle();
					mapperModelToUI.map(taArticle, swtArticle);
					swtArticle.setIdArticle(null);
					validateUIField(Const.C_CODE_ARTICLE,swtArticle.getCodeArticle());//permet de verrouiller le code genere
					dao.inserer(taArticle);
					modelArticle.getListeObjet().add(swtArticle);
					writableList = new WritableList(realm, modelArticle.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.setSelection(new StructuredSelection(swtArticle));
					initEtatBouton();
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
	protected void actInserer() throws Exception {
		try {
			if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
				VerrouInterface.setVerrouille(true);
				setSwtOldArticle();
				swtArticle = new SWTArticle();
				swtArticle.setPrixPrix(new BigDecimal(0));
				swtArticle.setPrixttcPrix(new BigDecimal(0));
				swtArticle.setCodeArticle(dao.genereCode());
				swtArticle.setActif(true);
				validateUIField(Const.C_CODE_ARTICLE,swtArticle.getCodeArticle());//permet de verrouiller le code genere
				swtArticle.setStockMinArticle(new BigDecimal(-1));

				taArticle = new TaArticle();
				dao.inserer(taArticle);
				modelArticle.getListeObjet().add(swtArticle);
				writableList = new WritableList(realm, modelArticle.getListeObjet(), classModel);
				tableViewer.setInput(writableList);
				tableViewer.refresh();
				tableViewer.setSelection(new StructuredSelection(swtArticle));
				initEtatBouton();
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
					setSwtOldArticle();
					taArticle = dao.findById(((SWTArticle) selectedArticle.getValue()).getIdArticle());
				}else{
				if(!setSwtOldArticleRefresh())
					throw new Exception();
				}
				dao.modifier(taArticle);
				initEtatBouton();
			}
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		}
	}

	@Override
	protected void actSupprimer() throws Exception {
		EntityTransaction transaction = null;
		
		try {
			VerrouInterface.setVerrouille(true);
			if(isUtilise())MessageDialog.openInformation(vue.getShell(), MessagesEcran
					.getString("Message.Attention"), MessagesEcran
					.getString("Message.suppression"));
			else
				if (MessageDialog.openConfirm(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("Articles.Message.Supprimer"))) {
					transaction = dao.getEntityManager().getTransaction();
					dao.begin(transaction);
					TaArticle u = dao.findById(((SWTArticle) selectedArticle.getValue()).getIdArticle());
					dao.supprimer(u);
					dao.commit(transaction);
					Object suivant=tableViewer.getElementAt(tableViewer.getTable().getSelectionIndex()+1);
					if(containsEntity(u)) modelArticle.getListeEntity().remove(u);
					taArticle=null;
					transaction = null;
					dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
					if(suivant!=null)tableViewer.setSelection(new StructuredSelection(suivant),true);
					else tableViewer.selectionGrille(0);
					actRefresh(); //ajouter pour tester jpa
				}
		} catch (ExceptLgr e1) {
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
		try {
			VerrouInterface.setVerrouille(true);
			switch (dao.getModeObjet().getMode()) {
			case C_MO_INSERTION:
				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("Articles.Message.Annuler")))) {
					remetTousLesChampsApresAnnulationSWT(dbc);
					if(((SWTArticle) selectedArticle.getValue()).getIdArticle()==null){
						modelArticle.getListeObjet().remove(((SWTArticle) selectedArticle.getValue()));
						writableList = new WritableList(realm, modelArticle.getListeObjet(), classModel);
						tableViewer.setInput(writableList);
						tableViewer.refresh();
						tableViewer.selectionGrille(0);
					}
					dao.annuler(taArticle);
					hideDecoratedFields();
					if(!annuleToutEnCours) {
						fireAnnuleTout(new AnnuleToutEvent(this));
					}
				}
				initEtatBouton();
				break;
			case C_MO_EDITION:
				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("Articles.Message.Annuler")))) {
					int rang = modelArticle.getListeObjet().indexOf(selectedArticle.getValue());
					remetTousLesChampsApresAnnulationSWT(dbc);
					modelArticle.getListeObjet().set(rang, swtOldArticle);
					writableList = new WritableList(realm, modelArticle.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.setSelection(new StructuredSelection(swtOldArticle), true);
					dao.annuler(taArticle);
					changementDeSelection(); //pour réintialiser les autres onglets à partir des données de la bdd
					hideDecoratedFields();
					if(!annuleToutEnCours) {
						fireAnnuleTout(new AnnuleToutEvent(this));
					}
				}
				initEtatBouton();

				break;
			case C_MO_CONSULTATION:
//				actionFermer.run();
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

		
		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
		
		int idArticle = taArticle.getIdArticle();
	
		String nomChampIdTable =  dao.getChampIdTable();
		
		FonctionGetInfosXmlAndProperties fonctionGetInfosXmlAndProperties = new FonctionGetInfosXmlAndProperties(mapperUIToModel);
		fonctionGetInfosXmlAndProperties.cleanValueMapAttributeTable();
		
		fonctionGetInfosXmlAndProperties.setValueMapAttributeTable(TaArticle.class.getSimpleName()+".head");
		fonctionGetInfosXmlAndProperties.setValueMapAttributeTable(TaArticle.class.getSimpleName()+".detail");
		
		LinkedHashMap<String,AttributElementResport> mapAttributeTablHead = fonctionGetInfosXmlAndProperties.getMapAttributeTablHead(); 
		LinkedHashMap<String,AttributElementResport> mapAttributeTablDetail = fonctionGetInfosXmlAndProperties.getMapAttributeTablDetail();
		
		ConstEdition constEdition = new ConstEdition(getEm()); 
		
	
		fonctionGetInfosXmlAndProperties.findInfosFileXml(TaArticle.class.getName(),SWTArticle.class.getName(),
					listeChamp,"mapping");
		fonctionGetInfosXmlAndProperties.getInfosObjetJPA(taArticle);
		
		/*********09/206/2010****************/
		constEdition.getOrderEcran(modelArticle.getListeEntity(),tableViewer,Const.C_CODE_ARTICLE);
		
		//Collection<TaArticle> collectionTaArticle = modelArticle.getListeEntity();
		Collection<TaArticle> collectionTaArticle = constEdition.getCollectionEntity();
		/***********************************/
//		Impression impression = new Impression(constEdition,taArticle,collectionTaArticle,
//				nomChampIdTable,taArticle.getIdArticle(),null);
		

		String nomDossier=null;
		int nombreLine = collectionTaArticle.size();

		if(nombreLine==0){
			MessageDialog.openWarning(vue.getShell(), ConstEdition.TITRE_MESSAGE_EDITION_VIDE,
					ConstEdition.EDITION_VIDE);
		}else{
			if(taInfoEntreprise.getIdInfoEntreprise()== 0){
				nomDossier = constEdition.INFOS_VIDE;
			}
			else{
				nomDossier = taInfoEntreprise.getNomInfoEntreprise();	
			}
			
			constEdition.addValueList(tableViewer, nomClassController);
			
			String folderEditionDynamique = Const.C_RCP_INSTANCE_LOCATION+ConstEdition.SEPARATOR+Const.C_NOM_PROJET_TMP+
											ConstEdition.SEPARATOR+TaArticle.class.getSimpleName();
			constEdition.makeFolderEditions(folderEditionDynamique);
			/** pathFileReport ==> le path de ficher de edition dynamique **/
			Path pathFileReport = new Path(folderEditionDynamique+ConstEdition.SEPARATOR+Const.C_NOM_VU_ARTICLE+".rptdesign");
			//Path pathFileReport = new Path(folderEditionDynamique+"/"+ConstEdition.START_V+simpleNameEntity+".rptdesign");
			final String pathFileReportDynamic = pathFileReport.toPortableString();

			MakeDynamiqueReport DynamiqueReport = new MakeDynamiqueReport(constEdition.getNameTableEcran(),
					constEdition.getNameTableBDD(),pathFileReportDynamic,Const.C_NOM_VU_ARTICLE,
					ConstEdition.PAGE_ORIENTATION_LANDSCAPE,nomDossier); 
			
			DynamiqueReport.setFonctionGetInfosXml(fonctionGetInfosXmlAndProperties);
			DynamiqueReport.setNomObjet(TaArticle.class.getSimpleName());	
			
			Map<String, AttributElementResport> attribuGridHeader = new LinkedHashMap<String, AttributElementResport>();

			attribuGridHeader.put(ConstEditionArticle.TITLE_EDITION_ARTICLE, new AttributElementResport("",
					ConstEdition.TEXT_ALIGN_CENTER,ConstEdition.FONT_SIZE_XX_LARGE,
					ConstEdition.FONT_WEIGHT_BOLD,"",ConstEdition.COLUMN_DATA_TYPE_STRING,
					ConstEdition.UNITS_VIDE,""));

			attribuGridHeader.put(ConstEditionArticle.SOUS_TITLE_EDITION_ARTICLE, new AttributElementResport("",
					ConstEdition.TEXT_ALIGN_CENTER,ConstEdition.FONT_SIZE_X_LARGE,
					ConstEdition.FONT_WEIGHT_BOLD,"",ConstEdition.COLUMN_DATA_TYPE_STRING,
					ConstEdition.UNITS_VIDE,ConstEdition.COLOR_GRAY));
			
			/** Eventuellement , on peut supprimer **/
			ConstEdition.CONTENT_COMMENTS = ConstEditionArticle.COMMENTAIRE_EDITION_DEFAUT;
			
			DynamiqueReport.setSimpleNameEntity(TaArticle.class.getSimpleName());
			
			
			DynamiqueReport.initializeBuildDesignReportConfig();
			DynamiqueReport.makePageMater("1", "1", "1", "1", "100");
			DynamiqueReport.makeReportHeaderGrid(3,5,100,ConstEdition.UNITS_PERCENTAGE,attribuGridHeader);
			DynamiqueReport.biuldTableReport("100", ConstEdition.UNITS_PERCENTAGE, 
					Const.C_NOM_VU_ARTICLE,1,1,2,ConstEdition.NOMBRE_LINE_EDITION_DYNAMIQUE, 
					mapAttributeTablHead, mapAttributeTablDetail);
			DynamiqueReport.savsAsDesignHandle();
			
			Bundle bundleCourant = ArticlesPlugin.getDefault().getBundle();
			String namePlugin = bundleCourant.getSymbolicName();//name plugin is <<Article>>
//			
//			impression.imprimer(true,pathFileReportDynamic,ConstEdition.FICHE_FILE_REPORT_ARTICLES,namePlugin,
//					TaArticle.class.getSimpleName(),true);
			
			
			/** 01/03/2010 modifier les editions (zhaolin) **/
			BaseImpressionEdition impressionEdition = new BaseImpressionEdition(constEdition,taArticle,
			getEm(),collectionTaArticle,taArticle.getIdArticle());

			IPreferenceStore preferenceStore = ArticlesPlugin.getDefault().getPreferenceStore();
			impressionEdition.impressionEdition(preferenceStore,TaArticle.class.getSimpleName(),/*true,*/
					new File(pathFileReportDynamic),namePlugin,ConstEdition.FICHE_FILE_REPORT_ARTICLES,
					false,true,false,false,false,false,ConstEditionArticle.PARAM_REPORT_ID_ARTICLE);
			
			/************************************************/
		}
	}

	@Override
	protected boolean aideDisponible() {
		boolean result = false;
		switch ((SWTPaArticlesController.this.dao.getModeObjet().getMode())) {
		case C_MO_CONSULTATION:
			if(getFocusCourantSWT().equals(vue.getGrille())) 
				result = true;
			break;

		case C_MO_EDITION:
		case C_MO_INSERTION:
			if(getFocusCourantSWT().equals(vue.getTfCODE_FAMILLE())
					|| getFocusCourantSWT().equals(vue.getTfCODE_T_TVA())
					|| getFocusCourantSWT().equals(vue.getTfCODE_TVA())
					|| getFocusCourantSWT().equals(vue.getTfCODE_UNITE())
					|| getFocusCourantSWT().equals(vue.getTfTitreTransportU1())
					)
				result = true;
			if(getFocusCourantSWT().equals(vue.getTfCODE_UNITE2())
					&& vue.getTfCODE_UNITE2().getEditable())
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
		boolean aide = getActiveAide();
		setActiveAide(true);
		if(aideDisponible()){
			try {
				VerrouInterface.setVerrouille(true);
				vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
				ParamAfficheAideRechercheSWT paramAfficheAideRecherche = new ParamAfficheAideRechercheSWT();
				paramAfficheAideRecherche.setMessage(message);
				// Creation de l'ecran d'aide principal
				Shell s = new Shell(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), LgrShellUtil.styleLgr);
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

				switch ((SWTPaArticlesController.this.dao.getModeObjet().getMode())) {
				case C_MO_CONSULTATION:
					if(getFocusCourantSWT().equals(vue.getGrille())){
						PaArticleSWT paArticlesSWT = new PaArticleSWT(s2,SWT.NULL);
						SWTPaArticlesController swtPaArticlesController = new SWTPaArticlesController(paArticlesSWT);
						paramAfficheAideRecherche.setForceAffichageAideRemplie(false);

						editorCreationId = ArticleMultiPageEditor.ID_EDITOR;
						editorInputCreation = new EditorInputArticle();

						ParamAfficheArticles paramAfficheArticles = new ParamAfficheArticles();
						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
						paramAfficheArticles.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheArticles.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaArticlesController;
						parametreEcranCreation = paramAfficheArticles;

						paramAfficheAideRecherche.setTypeEntite(TaArticle.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_ARTICLE);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_ARTICLE().getText());
						paramAfficheAideRecherche.setControllerAppelant(SWTPaArticlesController.this);
						paramAfficheAideRecherche.setModel(swtPaArticlesController.getModelArticle());
						paramAfficheAideRecherche.setTypeObjet(swtPaArticlesController.getClassModel());
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_ARTICLE);
					}
					break;

				case C_MO_EDITION:
				case C_MO_INSERTION:
					if(getFocusCourantSWT().equals(vue.getTfCODE_FAMILLE())){

						PaFamilleSWT paFamilleSWT = new PaFamilleSWT(s2,SWT.NULL); 
						SWTPaFamilleController swtPaFamilleController = new SWTPaFamilleController(paFamilleSWT);

						editorCreationId = EditorFamille.ID;
						editorInputCreation = new EditorInputFamille();

						ParamAfficheFamille paramAfficheFamille = new ParamAfficheFamille();
						paramAfficheAideRecherche.setJPQLQuery(new TaFamilleDAO(getEm()).getJPQLQuery());
						paramAfficheFamille.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheFamille.setEcranAppelant(paAideController);
						/* 
						 * controllerEcranCreation ne sert plus a rien, pour l'editeur de creation, on creer un nouveau controller
						 */
						controllerEcranCreation = swtPaFamilleController;
						parametreEcranCreation = paramAfficheFamille;

						paramAfficheAideRecherche.setTypeEntite(TaFamille.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_FAMILLE);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_FAMILLE().getText());
						paramAfficheAideRecherche.setControllerAppelant(SWTPaArticlesController.this);
						paramAfficheAideRecherche.setModel(swtPaFamilleController.getModelFamille());
						paramAfficheAideRecherche.setTypeObjet(swtPaFamilleController.getClassModel());

						paramAfficheAideRecherche.setChampsIdentifiant(swtPaFamilleController.getDao().getChampIdTable());
					}
					if(getFocusCourantSWT().equals(vue.getTfCODE_T_TVA())){
						PaTTVASWT paTTvaSWT = new PaTTVASWT(s2,SWT.NULL);
						SWTPaTTvaController swtPaTTvaController = new SWTPaTTvaController(paTTvaSWT);

						editorCreationId = EditorTypeTVA.ID;
						editorInputCreation = new EditorInputTypeTVA();

						ParamAfficheTTva paramAfficheTTva = new ParamAfficheTTva();
						paramAfficheAideRecherche.setJPQLQuery(new TaTTvaDAO(getEm()).getJPQLQuery());
						paramAfficheTTva.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheTTva.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaTTvaController;
						parametreEcranCreation = paramAfficheTTva;

						paramAfficheAideRecherche.setTypeEntite(TaTTva.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_T_TVA);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_T_TVA().getText());
						paramAfficheAideRecherche.setControllerAppelant(SWTPaArticlesController.this);
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTTva,TaTTvaDAO,TaTTva>(SWTTTva.class,dao.getEntityManager()));
						paramAfficheAideRecherche.setTypeObjet(swtPaTTvaController.getClassModel());

						paramAfficheAideRecherche.setChampsIdentifiant(swtPaTTvaController.getDao().getChampIdTable());
					}
					if(getFocusCourantSWT().equals(vue.getTfCODE_TVA())){
						PaTVASWT paTvaSWT = new PaTVASWT(s2,SWT.NULL);
						SWTPaTvaController swtPaTvaController = new SWTPaTvaController(paTvaSWT);

						editorCreationId = EditorTva.ID;
						editorInputCreation = new EditorInputTva();

						ParamAfficheTva paramAfficheTva = new ParamAfficheTva();
						paramAfficheAideRecherche.setJPQLQuery(new TaTvaDAO(getEm()).getJPQLQuery());
						paramAfficheTva.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheTva.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaTvaController;
						parametreEcranCreation = paramAfficheTva;

						paramAfficheAideRecherche.setTypeEntite(TaTva.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_TVA);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_TVA().getText());
						paramAfficheAideRecherche.setControllerAppelant(SWTPaArticlesController.this);
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTva,TaTvaDAO,TaTva>(SWTTva.class,dao.getEntityManager()));
						paramAfficheAideRecherche.setTypeObjet(swtPaTvaController.getClassModel());

						paramAfficheAideRecherche.setChampsIdentifiant(swtPaTvaController.getDao().getChampIdTable());
					}
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
						paramAfficheAideRecherche.setControllerAppelant(SWTPaArticlesController.this);
						paramAfficheAideRecherche.setModel(new TaUniteDAO(getEm()).modelObjetUniteArticle(taArticle.getCodeArticle()));
						paramAfficheAideRecherche.setTypeObjet(swtPaUniteController.getClassModel());

						paramAfficheAideRecherche.setChampsIdentifiant(swtPaUniteController.getDao().getChampIdTable());
					}
					if(getFocusCourantSWT().equals(vue.getTfCODE_UNITE2())){
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
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_UNITE2().getText());
						paramAfficheAideRecherche.setControllerAppelant(SWTPaArticlesController.this);
						paramAfficheAideRecherche.setModel(new TaUniteDAO(getEm()).modelObjetUniteArticle(taArticle.getCodeArticle()));
						paramAfficheAideRecherche.setTypeObjet(swtPaUniteController.getClassModel());

						paramAfficheAideRecherche.setChampsIdentifiant(swtPaUniteController.getDao().getChampIdTable());
					}
					if(getFocusCourantSWT().equals(vue.getTfTitreTransportU1())){
						PaTitreTransport paTitreTransport = new PaTitreTransport(s2,SWT.NULL);
						SWTPaTitreTransport swtPaTitreTransport = new SWTPaTitreTransport(paTitreTransport);

						editorCreationId = EditorTitreTransport.ID;
						editorInputCreation = new EditorInputTitreTransport();

						ParamAfficheTitreTansport paramAfficheTitreTansport = new ParamAfficheTitreTansport();
						paramAfficheAideRecherche.setJPQLQuery(new TaTitreTransportDAO(getEm()).getJPQLQuery());
						paramAfficheTitreTansport.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheTitreTansport.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaTitreTransport;
						parametreEcranCreation = paramAfficheTitreTansport;

						paramAfficheAideRecherche.setTypeEntite(TaTitreTransport.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_TITRE_TRANSPORT);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfTitreTransportU1().getText());
						paramAfficheAideRecherche.setControllerAppelant(SWTPaArticlesController.this);
//						paramAfficheAideRecherche.setModel(new TaTitreTransportDAO(getEm()).modelObjetUniteArticle(taArticle.getCodeArticle()));
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTitreTransport,TaTitreTransportDAO,TaTitreTransport>(SWTTitreTransport.class,dao.getEntityManager()));
						paramAfficheAideRecherche.setTypeObjet(swtPaTitreTransport.getClassModel());

						paramAfficheAideRecherche.setChampsIdentifiant(swtPaTitreTransport.getDao().getChampIdTable());
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
					paAideController.addRetourEcranListener(SWTPaArticlesController.this);
					Control focus = vue.getShell().getDisplay().getFocusControl();
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

	protected void initImageBouton() {
		super.initImageBouton();
		String C_IMAGE_AJOUT_PRIX = "/icons/money.png";
		if(vue instanceof PaArticleSWT) {
			((PaArticleSWT)vue).getBtnAjoutPrix().setImage(ArticlesPlugin.getImageDescriptor(C_IMAGE_AJOUT_PRIX).createImage());
			vue.layout(true);
		}
	}

	public IStatus validateUI() throws Exception {
		if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
				|| (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
			ctrlTousLesChampsAvantEnregistrementSWT();
			mapperUIToModel.map((SWTArticle) selectedArticle.getValue(),taArticle);
		}
		return null;
	}

	public IStatus validateUIField(String nomChamp,Object value) {
		String validationContext = "ARTICLE";
		try {
			IStatus s = null;

			if(nomChamp.equals(Const.C_CODE_FAMILLE)) {
				TaFamilleDAO dao = new TaFamilleDAO(getEm());

				dao.setModeObjet(getDao().getModeObjet());
				TaFamille f = new TaFamille();
				PropertyUtils.setSimpleProperty(f, nomChamp, value);
				s = dao.validate(f,nomChamp,validationContext);

				if(s.getSeverity()!=IStatus.ERROR ){
					f = dao.findByCode((String)value);
					taArticle.setTaFamille(f);
				}
				dao = null;
			} else if(nomChamp.equals(Const.C_CODE_TVA)) {
				TaTvaDAO daoTva = new TaTvaDAO(getEm());
				daoTva.setModeObjet(getDao().getModeObjet());
				TaTva f = new TaTva();

				PropertyUtils.setSimpleProperty(f, nomChamp, value);
				s = daoTva.validate(f,nomChamp,validationContext);

				if(s.getSeverity()!=IStatus.ERROR){
					f = daoTva.findByCode((String)value);
					boolean change=taArticle.getTaTva()==null || !taArticle.getTaTva().equals(f);
					taArticle.setTaTva(f);
					dao.initCodeTTva(taArticle);
					//dao.initPrixTTC(taArticle);
					if(taArticle.getTaTTva()!=null)
						((SWTArticle) selectedArticle.getValue()).setCodeTTva(taArticle.getTaTTva().getCodeTTva());
					else ((SWTArticle) selectedArticle.getValue()).setCodeTTva(null);
				}
				daoTva = null;
			} else if(nomChamp.equals(Const.C_CODE_T_TVA)) {
				TaTTvaDAO daoTTva = new TaTTvaDAO(getEm());
				daoTTva.setModeObjet(getDao().getModeObjet());
				TaTTva f = new TaTTva();
				PropertyUtils.setSimpleProperty(f, nomChamp, value);
				s = daoTTva.validate(f,nomChamp,validationContext);

				if(s.getSeverity()!=IStatus.ERROR){
					f = daoTTva.findByCode((String)value);
					taArticle.setTaTTva(f);
					dao.initCodeTTva(taArticle);
					if(taArticle.getTaTTva()!=null)
						((SWTArticle) selectedArticle.getValue()).setCodeTTva(taArticle.getTaTTva().getCodeTTva());
					else ((SWTArticle) selectedArticle.getValue()).setCodeTTva(null);
				}
				daoTTva = null;
			} else if(nomChamp.equals(Const.C_CODE_UNITE2)) {
				TaUniteDAO dao = new TaUniteDAO(getEm());
				dao.setModeObjet(getDao().getModeObjet());
				TaUnite f = new TaUnite();
				PropertyUtils.setSimpleProperty(f, Const.C_CODE_UNITE, value);
				s = dao.validate(f,Const.C_CODE_UNITE,validationContext);

				if(s.getSeverity()!=IStatus.ERROR){
					f = dao.findByCode((String)value);
					if (!value.equals("")){
						initRapportUniteArticle();
						taArticle.getTaRapportUnite().setTaUnite1(taArticle.getTaPrix().getTaUnite());
						taArticle.getTaRapportUnite().setTaUnite2(f);
					}else
						if(taArticle.getTaRapportUnite()!=null){
							taArticle.removeRapportUnite(taArticle.getTaRapportUnite());
						}
					initEtatRapportUnite();
				}
				dao = null;
			} else if(nomChamp.equals(Const.C_RAPPORT)) {
				TaRapportUniteDAO dao = new TaRapportUniteDAO(getEm());
				dao.setModeObjet(getDao().getModeObjet());
				TaRapportUnite f = new TaRapportUnite();
				PropertyUtils.setSimpleProperty(f, nomChamp, value);
				if((value==null || value.equals(""))&&(!rapportUniteEstRempli()))
					s = new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
				else
					s = dao.validate(f,nomChamp,validationContext);

				if(s.getSeverity()!=IStatus.ERROR && value!=null){
					//					initRapportUniteArticle();
					taArticle.getTaRapportUnite().setRapport(f.getRapport());
				}

				dao = null;
			} else if(nomChamp.equals(Const.C_SENS_RAPPORT)) {
				TaRapportUniteDAO dao = new TaRapportUniteDAO(getEm());
				TaRapportUnite f = new TaRapportUnite();

				//if(nomChamp.equals(Const.C_SENS_RAPPORT)) {
					//traitement des booleens
					if((Boolean)value==true) value=new Integer(1); else value=new Integer(0);
				//}
				PropertyUtils.setSimpleProperty(f, nomChamp, value);

				if((value==null || value.equals("")) && (!rapportUniteEstRempli()))
					s = new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
				else
					s = dao.validate(f,nomChamp,validationContext);

				if(rapportUniteEstRempli() && s.getSeverity()!=IStatus.ERROR && value!=null){
					//					initRapportUniteArticle();
					taArticle.getTaRapportUnite().setSens(f.getSens());
				}

				dao = null;

			} else if(nomChamp.equals(Const.C_NB_DECIMAL)) {
				TaRapportUniteDAO dao = new TaRapportUniteDAO(getEm());
				dao.setModeObjet(getDao().getModeObjet());
				TaRapportUnite f = new TaRapportUnite();

				if((value==null || value.equals(""))&&(!rapportUniteEstRempli())) {
					s = new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
					value=null;
				} else {
					value=LibConversion.stringToInteger(value.toString());
					PropertyUtils.setSimpleProperty(f, nomChamp, value);
					s = dao.validate(f,nomChamp,validationContext);
				}

				if(s.getSeverity()!=IStatus.ERROR && value!=null){
					//					initRapportUniteArticle();
					taArticle.getTaRapportUnite().setNbDecimal(f.getNbDecimal());
				}

				dao = null;
			} else if(nomChamp.equals(Const.C_CODE_UNITE)) {
				TaUniteDAO dao = new TaUniteDAO(getEm());
				dao.setModeObjet(getDao().getModeObjet());
				TaUnite f = new TaUnite();
				PropertyUtils.setSimpleProperty(f, nomChamp, value);
				s = dao.validate(f,nomChamp,validationContext);

				if(s.getSeverity()!=IStatus.ERROR){
					f = dao.findByCode((String)value);
					if (!value.equals("")){
						initPrixArticle();
						taArticle.getTaPrix().setTaUnite(f);
					}else{
						if (taArticle.getTaPrix()!=null)
						{
							taArticle.getTaPrix().setTaUnite(null);
							if(taArticle.getTaPrix().getPrixPrix()!=null && 
									taArticle.getTaPrix().getPrixPrix().equals(0)&& 
									taArticle.getTaPrix().getPrixttcPrix()!=null && 
									taArticle.getTaPrix().getPrixttcPrix().equals(0))
								taArticle.removePrix(taArticle.getTaPrix());
						}
					}
					initEtatRapportUnite();
				}
				dao = null;
			} else if(nomChamp.equals(Const.C_PRIX_PRIX)) {
				TaPrixDAO dao = new TaPrixDAO(getEm());
				//				if(value==null || value.equals(""))value=new BigDecimal(0);
				if(value!=null && !value.equals(""))initPrixArticle();
				dao.setModeObjet(getDao().getModeObjet());
				TaPrix f = new TaPrix();
				f.setTaArticle(taArticle);
				PropertyUtils.setSimpleProperty(f, nomChamp, value);
				int change =0;
				if(value!=null && ((SWTArticle) selectedArticle.getValue()).getPrixPrix()!=null)
					change =f.getPrixPrix().compareTo(((SWTArticle) selectedArticle.getValue()).getPrixPrix());
				else change=-1;
				f.setPrixttcPrix(((SWTArticle) selectedArticle.getValue()).getPrixttcPrix());				
				s = dao.validate(f,nomChamp,validationContext);
				if(s.getSeverity()!=IStatus.ERROR && (change!=0||f.getPrixPrix().compareTo(new BigDecimal(0))==0)
				) {		

					if((value==null||value.equals("")||value.equals(0))&& taArticle.getTaPrix()!=null){
						//taArticle.setTaPrix(null);
						taArticle.removePrix(taArticle.getTaPrix());
					}else{
						if((value!=null && !value.equals(""))&& taArticle.getTaPrix()!=null){
							initPrixArticle();						
							taArticle.getTaPrix().setPrixPrix(f.getPrixPrix());
							taArticle.getTaPrix().setPrixttcPrix(f.getPrixttcPrix());
						}
					}
					((SWTArticle) selectedArticle.getValue()).setPrixPrix(f.getPrixPrix());				
					((SWTArticle) selectedArticle.getValue()).setPrixttcPrix(f.getPrixttcPrix());	  
				}
				dao = null;
			}else if(nomChamp.equals(Const.C_PRIXTTC_PRIX)) {
				TaPrixDAO dao = new TaPrixDAO(getEm());
				if(value!=null && !value.equals(""))initPrixArticle();
				dao.setModeObjet(getDao().getModeObjet());
				TaPrix f = new TaPrix();
				f.setTaArticle(taArticle);
				PropertyUtils.setSimpleProperty(f, nomChamp, value);
				int change =0;
				if(value!=null && ((SWTArticle) selectedArticle.getValue()).getPrixttcPrix()!=null)
					change =f.getPrixttcPrix().compareTo(((SWTArticle) selectedArticle.getValue()).getPrixttcPrix());
				else change=-1;
				f.setPrixPrix(((SWTArticle) selectedArticle.getValue()).getPrixPrix());
				s = dao.validate(f,nomChamp,validationContext);
				if(s.getSeverity()!=IStatus.ERROR
						&& (change!=0||f.getPrixttcPrix().compareTo(new BigDecimal(0))==0)
				) {			
					if((value==null||value.equals("")||value.equals(0))&& taArticle.getTaPrix()!=null){
						//taArticle.setTaPrix(null);
						taArticle.removePrix(taArticle.getTaPrix());
					}else{
						if((value!=null && !value.equals(""))&& taArticle.getTaPrix()!=null){
							initPrixArticle();						
							taArticle.getTaPrix().setPrixPrix(f.getPrixPrix());
							taArticle.getTaPrix().setPrixttcPrix(f.getPrixttcPrix());
						}
					}
					((SWTArticle) selectedArticle.getValue()).setPrixPrix(f.getPrixPrix());				
					((SWTArticle) selectedArticle.getValue()).setPrixttcPrix(f.getPrixttcPrix());				  
				}
				dao = null;
			} else if(nomChamp.equals(Const.C_QTE_TITRE_TRANSPORT_L_DOCUMENT)) {
				//s = new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
				//initTaRTaTitreTransport();
				
				TaRTaTitreTransportDAO dao = new TaRTaTitreTransportDAO(getEm());

				dao.setModeObjet(getDao().getModeObjet());
				TaRTaTitreTransport f = new TaRTaTitreTransport();
				
				//s = dao.validate(f,nomChamp,validationContext);
				
				if((value==null || value.equals("")) && (!crdEstRempli()))
					s = new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
				else  {
					if((value==null || value.equals("") || ((BigDecimal)value).doubleValue()<=0) && crdEstRempli())
						value="1";
						
					value=LibConversion.stringToBigDecimal(value.toString());
					PropertyUtils.setSimpleProperty(f, nomChamp, value);
					s = dao.validate(f,nomChamp,validationContext);
				}
				
				if(crdEstRempli() && s.getSeverity()!=IStatus.ERROR && value!=null){
					initTaRTaTitreTransport();
					taArticle.getTaRTaTitreTransport().setQteTitreTransport(f.getQteTitreTransport());
				} else {
					if(taArticle.getTaRTaTitreTransport()!=null) {
						taArticle.setTaRTaTitreTransport(null);
					}
				}
				dao = null;
				
			} else if(nomChamp.equals(Const.C_CODE_TITRE_TRANSPORT)) {
					TaTitreTransportDAO dao = new TaTitreTransportDAO(getEm());

					dao.setModeObjet(getDao().getModeObjet());
					TaTitreTransport f = new TaTitreTransport();
					PropertyUtils.setSimpleProperty(f, /*nomChamp*/ Const.C_CODE_TITRE_TRANSPORT, value);
					s = dao.validate(f,/*nomChamp*/ Const.C_CODE_TITRE_TRANSPORT,validationContext);

					if(s.getSeverity()!=IStatus.ERROR ){
						f = dao.findByCode((String)value);
						if (!value.equals("")){
							initTaRTaTitreTransport();
							taArticle.getTaRTaTitreTransport().setTaTitreTransport(f);
						} else {
							if(taArticle.getTaRTaTitreTransport()!=null) {
								taArticle.setTaRTaTitreTransport(null);
							}
						}
						//taArticle.setTaTitreTransport(f);
					}
					dao = null;
			} else {
				boolean verrouilleModifCode = false;
				TaArticle u = new TaArticle();
				
				if(nomChamp.equals(Const.C_ACTIF_ARTICLE)) { //traitement des booleens
					if((Boolean)value==true) value=new Integer(1); else value=new Integer(0);
				}
				
				PropertyUtils.setSimpleProperty(u, nomChamp, value);
				if(selectedArticle!=null
						&& selectedArticle.getValue() !=null
						&& ((SWTArticle) selectedArticle.getValue()).getIdArticle()!=null) {
					u.setIdArticle(((SWTArticle) selectedArticle.getValue()).getIdArticle());
				}

				if(nomChamp.equals(Const.C_CODE_ARTICLE)){
					verrouilleModifCode = true;
				}
				
				s = dao.validate(u,nomChamp,validationContext,verrouilleModifCode);
				
				if(s.getSeverity()!=IStatus.ERROR && value!=null){
					if(taArticle!=null) {
						if(taArticle.getTaCatalogueWeb()==null) {
							taArticle.setTaCatalogueWeb(new TaCatalogueWeb());
						}
						if(nomChamp.equals(Const.C_LIBELLEC_ARTICLE)) {
							if(LibChaine.empty(taArticle.getTaCatalogueWeb().getUrlRewritingCatalogueWeb())
									|| ArticlesPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.REGENERATION_URL_REWRITING_POUR_CATALOGUE_WEB_A_PARTIR_ECRAN_PRINCIPAL)) {
								taArticle.getTaCatalogueWeb().setUrlRewritingCatalogueWeb(LibChaine.toUrlRewriting((String)value));
							}
						} else if(nomChamp.equals(Const.C_LIBELLEL_ARTICLE)) {
							if(LibChaine.empty(taArticle.getTaCatalogueWeb().getDescriptionLongueCatWeb())
									|| ArticlesPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.REGENERATION_DESCRIPTION_POUR_CATALOGUE_WEB_A_PARTIR_ECRAN_PRINCIPAL)) {
								taArticle.getTaCatalogueWeb().setDescriptionLongueCatWeb((String)value);
							}
						}
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
		EntityTransaction transaction = null;
		try {
			boolean declanchementExterne = false;
			if(sourceDeclencheCommandeController==null) {
				declanchementExterne = true;
			}
			if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
					|| (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {

				if(declanchementExterne) {
					ctrlTousLesChampsAvantEnregistrementSWT();
				}

				transaction = dao.getEntityManager().getTransaction();
				dao.begin(transaction);

				if(declanchementExterne) {
					mapperUIToModel.map((SWTArticle) selectedArticle.getValue(),taArticle);
				}
				
				fireEnregistreTout(new AnnuleToutEvent(this,true));
				
				if(!enregistreToutEnCours) {
					if((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)){	
						taArticle=dao.enregistrerMerge(taArticle);
						//					modelArticle.getListeEntity().add(taArticle);
					}
					else taArticle=dao.enregistrerMerge(taArticle);


					dao.commit(transaction);
					if(!containsEntity(taArticle)) 
						modelArticle.getListeEntity().add(taArticle);
					actRefresh(); //deja present
				}
				transaction = null;
				
			}
		} catch (Exception e) {
			logger.error("",e);

			if(transaction!=null && transaction.isActive()) {
				transaction.rollback();
			}
		} finally {
			initEtatBouton();
		}
	}


	public void initEtatComposant() {
		try {
			vue.getTfCODE_ARTICLE().setEditable(!isUtilise());
			initEtatRapportUnite();
			changeCouleur(vue);
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
		}
	}
	public boolean isUtilise(){
		return (selectedArticle.getValue()!=null &&
		((SWTArticle)selectedArticle.getValue()).getIdArticle()!=null && 
		!dao.recordModifiable(dao.getNomTable(),
				((SWTArticle)selectedArticle.getValue()).getIdArticle()))||
				!dao.autoriseModification(taArticle);

	}
	public SWTArticle getSwtOldArticle() {
		return swtOldArticle;
	}

	public void setSwtOldArticle(SWTArticle swtOldArticle) {
		this.swtOldArticle = swtOldArticle;
	}

	public void setSwtOldArticle() {

		if (selectedArticle.getValue() != null)
			this.swtOldArticle = SWTArticle.copy((SWTArticle) selectedArticle.getValue());
		else {
			if(tableViewer.selectionGrille(0)){
				this.swtOldArticle = SWTArticle.copy((SWTArticle) selectedArticle.getValue());
				tableViewer.setSelection(new StructuredSelection(
						(SWTArticle) selectedArticle.getValue()), true);
			}}
	}
//	public boolean setSwtOldArticleRefresh() {
//		TaArticle taArticleOld =null;
//		if (selectedArticle.getValue()!=null){
//			taArticle=dao.findById(((SWTArticle) selectedArticle.getValue()).getIdArticle());
//			if(sourceDeclencheCommandeController==null) {
//				if(containsEntity(taArticle)) 
//					modelArticle.getListeEntity().remove(taArticle);
//				taArticleOld =dao.findById(((SWTArticle) selectedArticle.getValue()).getIdArticle());
////				if(LgrMess.isDOSSIER_EN_RESEAU())taArticle = 
//				if(!containsEntity(taArticle)) modelArticle.getListeEntity().add(taArticle);				
//				fireChangementDeSelection(new ChangementDeSelectionEvent(this,taArticle));
//				if(!taArticle.getVersionObj().equals(dao.getEntityManager().refresh(){
//					try {						
//						if(containsEntity(taArticleOld)) 
//							modelArticle.getListeEntity().remove(taArticleOld);
//						if(!containsEntity(taArticle)) modelArticle.getListeEntity().add(taArticle);
//						actRefresh();
//					} catch (Exception e) {
//						logger.error("",e);
//						return false;
//					}
//				}
//			}
//			else{
//				taArticleOld =taArticle;
//				if(LgrMess.isDOSSIER_EN_RESEAU())taArticle = dao.refresh(dao.findById(((SWTArticle) selectedArticle.getValue()).getIdArticle()));
//				fireChangementDeSelection(new ChangementDeSelectionEvent(this,taArticle));
//				if(!taArticle.getVersionObj().equals(taArticleOld.getVersionObj())){
//				try {
//					dao.messageNonAutoriseModification();
//					actAnnuler();
////					if(containsEntity(taArticleOld)) 
////						modelArticle.getListeEntity().remove(taArticleOld);
////					if(!containsEntity(taArticle)) modelArticle.getListeEntity().add(taArticle);					
//				} catch (Exception e) {
//					logger.error("",e);
//					return false;
//				}
//			}
//			}
//			this.swtOldArticle=SWTArticle.copy((SWTArticle)selectedArticle.getValue());
//		}
//		return true;
//	}
	public boolean containsEntity(TaArticle entite){
		if(modelArticle.getListeEntity()!=null){
			for (Object e : modelArticle.getListeEntity()) {
				if(((TaArticle)e).getIdArticle()==
					entite.getIdArticle())return true;
			}
		}
		return false;
	}
	


	public boolean setSwtOldArticleRefresh() {
		try {	
			if (selectedArticle.getValue()!=null){
				TaArticle taArticleOld =dao.findById(taArticle.getIdArticle());
				taArticleOld=dao.refresh(taArticleOld);
				if(containsEntity(taArticle)) 
					modelArticle.getListeEntity().remove(taArticle);
				if(!taArticle.getVersionObj().equals(taArticleOld.getVersionObj())){
//					if(containsEntity(taArticle)) 
//						modelArticle.getListeEntity().remove(taArticle);
					taArticle=taArticleOld;
					if(!containsEntity(taArticle)) 
						modelArticle.getListeEntity().add(taArticle);					
					actRefresh();
					taArticle=taArticleOld;
					changementDeSelection();
					if(!containsEntity(taArticleOld)) 
						modelArticle.getListeEntity().add(taArticleOld);
					dao.messageNonAutoriseModification();
				}
				taArticle=taArticleOld;
				fireChangementMaster(new ChangementMasterEntityEvent(this,taArticle,true));
				this.swtOldArticle=SWTArticle.copy((SWTArticle)selectedArticle.getValue());
			}
			return true;
		} catch (Exception e) {
			return false;
		}		
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
	public void setVue(PaArticleSWT vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();

		mapComposantDecoratedField.put(vue.getTfCODE_ARTICLE(), vue.getFieldCODE_ARTICLE());
		mapComposantDecoratedField.put(vue.getTfLIBELLEC_ARTICLE(), vue.getFieldLIBELLEC_ARTICLE());
		mapComposantDecoratedField.put(vue.getTfLIBELLEL_ARTICLE(), vue.getFieldLIBELLEL_ARTICLE());
		mapComposantDecoratedField.put(vue.getTfCODE_FAMILLE(), vue.getFieldCODE_FAMILLE());
		mapComposantDecoratedField.put(vue.getTfNUMCPT_ARTICLE(), vue.getFieldNUMCPT_ARTICLE());
		mapComposantDecoratedField.put(vue.getTfCODE_TVA(), vue.getFieldCODE_TVA());
		mapComposantDecoratedField.put(vue.getTfCODE_T_TVA(), vue.getFieldCODE_T_TVA());
		mapComposantDecoratedField.put(vue.getTfDIVERS_ARTICLE(), vue.getFieldDIVERS_ARTICLE());
		mapComposantDecoratedField.put(vue.getTfCOMMENTAIRE_ARTICLE(), vue.getFieldCOMMENTAIRE_ARTICLE());
		mapComposantDecoratedField.put(vue.getTfSTOCK_MIN_ARTICLE(), vue.getFieldSTOCK_MIN_ARTICLE());
		
		mapComposantDecoratedField.put(vue.getTfCODE_UNITE(), vue.getFieldCODE_UNITE());
		mapComposantDecoratedField.put(vue.getTfPRIX_PRIX(), vue.getFieldPRIX_PRIX());
		mapComposantDecoratedField.put(vue.getTfPRIXTTC_PRIX(), vue.getFieldPRIXTTC_PRIX());
		
		mapComposantDecoratedField.put(vue.getTfCODE_UNITE2(), vue.getFieldCODE_UNITE2());
		mapComposantDecoratedField.put(vue.getTfRAPPORT(), vue.getFieldRAPPORT());
		mapComposantDecoratedField.put(vue.getTfDECIMAL(), vue.getFieldDECIMAL());
		
		mapComposantDecoratedField.put(vue.getTfHauteur(), vue.getFieldHauteur());
		mapComposantDecoratedField.put(vue.getTfLongueur(), vue.getFieldLongueur());
		mapComposantDecoratedField.put(vue.getTfLargeur(), vue.getFieldLargeur());
		mapComposantDecoratedField.put(vue.getTfPoids(), vue.getFieldPoids());
	}

	
	protected class HandlerRecalculer extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException{
			try {
				actRecalculer();
			} catch (Exception e1) {
				logger.error("Erreur : actionPerformed", e1);
			}
			return event;
		}
	}	
	
	protected class HandlerDupliquer extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException{
			try {
				actDupliquer();
			} catch (Exception e1) {
				logger.error("Erreur : actionPerformed", e1);
			}
			return event;
		}
	}
	
	protected class HandlerAjoutPrix extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException{
			try {
				//				LgrPartListener.getLgrPartListener().setLgrActivePart(null);
				//				IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new EditorInputPrix(), EditorPrix.ID);
				//				LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
				//				LgrPartListener.getLgrPartListener().setLgrActivePart(e);
				//				//SWTPaPrixController swtPaPrixController = new SWTPaPrixController(((EditorPrix)e).getComposite());
				//				
				//				ParamAffichePrix paramAffichePrix = new ParamAffichePrix();
				//				paramAffichePrix.setIdArticle(LibConversion.integerToString(((SWTArticle)selectedArticle.getValue()).getIdArticle()));
				//				((JPALgrEditorPart)e).setPanel(((JPALgrEditorPart)e).getController().getVue());
				//				((JPALgrEditorPart)e).getController().configPanel(paramAffichePrix);
				MessageDialog.openInformation(vue.getShell(), "Avertissement",
				"Non implémentée");
			} catch (Exception e1) {
				logger.error("Erreur : actionPerformed", e1);
			}
			return event;
		}
	}

	protected void actSelection() throws Exception {
		try{
			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
			openEditor(new fr.legrain.visualisation.editor.EditorInputSelectionVisualisation(), 
					fr.legrain.visualisation.editor.EditorSelectionVisualisation.ID);
			LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);

			ParamAfficheVisualisation paramAfficheSelectionVisualisation = new ParamAfficheVisualisation();
			paramAfficheSelectionVisualisation.setModule("article");
			paramAfficheSelectionVisualisation.setNomClassController(nomClassController);
			paramAfficheSelectionVisualisation.setNomRequete(Const.C_NOM_VU_ARTICLE);

			((LgrEditorPart)e).setPanel(((LgrEditorPart)e).getControllerSelection().getVue());
			((LgrEditorPart)e).getControllerSelection().configPanel(paramAfficheSelectionVisualisation);

		}catch (Exception e) {
			logger.error("",e);
		}	
	}



	class ActionAjoutPrix extends Action {
		public ActionAjoutPrix(String name) {
			super(name);
		}

		public void run() {
			super.run();
			try {
				handlerService.executeCommand(C_COMMAND_PRIX_ID, null);
			} catch (ExecutionException e) {
				logger.error("",e);
			} catch (NotDefinedException e) {
				logger.error("",e);
			} catch (NotEnabledException e) {
				logger.error("",e);
			} catch (NotHandledException e) {
				logger.error("",e);
			}
		}
	}

	public Class getClassModel() {
		return classModel;
	}

	@Override
	protected void sortieChamps() {
		// TODO Raccord de methode auto-genere

		try {
			if(getActiveAide()) {
				if(LgrPartListener.getLgrPartListener().getLgrActivePart()!=null)
					LgrPartListener.getLgrPartListener().getLgrActivePart().setFocus();
			}
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	@Override
	protected void actRefresh() throws Exception {
		try{
			vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));		
		//repositionnement sur la valeur courante
		int idActuel = 0;
		if (taArticle!=null) { //enregistrement en cours de modification/insertion
			idActuel = taArticle.getIdArticle();
		} else if(selectedArticle!=null && (SWTArticle) selectedArticle.getValue()!=null) {
			idActuel = ((SWTArticle) selectedArticle.getValue()).getIdArticle();
		}

		if (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION) == 0) {
			//rafraichissement des valeurs dans la grille
			writableList = new WritableList(realm, modelArticle.remplirListe(), classModel);
			tableViewer.setInput(writableList);
		} else {
			if (taArticle!=null && selectedArticle!=null && (SWTArticle) selectedArticle.getValue()!=null) {
				mapperModelToUI.map(taArticle, (SWTArticle) selectedArticle.getValue());
			}
		}

		if(idActuel!=0) {
			tableViewer.setSelection(new StructuredSelection(modelArticle.recherche(Const.C_ID_ARTICLE
					, idActuel)),true);
		}else
			tableViewer.selectionGrille(0);				
		}finally{
			vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
		}
	}




	public SWTArticle getSwtArticle() {
		return swtArticle;
	}

	public TaArticle getTaArticle() {
		return taArticle;
	}

	public TaArticleDAO getDao() {
		return dao;
	}

	public boolean changementPageValide(){		
		return (daoStandard.selectAll().size()>0);
	}

	public String getIdArticle() {
		return idArticle;
	}

	public void setIdArticle(String idArticle) {
		this.idArticle = idArticle;
	}

	@Override
	public void actAfficherTous() throws Exception{
		vue.getGrille().setVisible(true);  
		vue.getBtnTous().setVisible(false);
		vue.getLaTitreGrille().setVisible(true);
		vue.getCompositeForm().setWeights(new int[]{50,100});
		//dao.getEntityManager().clear();
		dao.setJPQLQuery(dao.getJPQLQueryInitial());
		modelArticle.setJPQLQuery(null);
		modelArticle.setListeEntity(null);
		try {
			actRefresh();
		} catch (Exception e1) {
			logger.error("", e1);
		}
	}

	public ModelGeneralObjet<SWTArticle, TaArticleDAO, TaArticle> getModelArticle() {
		return modelArticle;
	}

	public void setModelArticle(
			ModelGeneralObjet<SWTArticle, TaArticleDAO, TaArticle> modelArticle) {
		this.modelArticle = modelArticle;
	}

//	public ModelArticles getModelArticle() {
//		return modelArticle;
//	}
//
//	public void setModelArticle(ModelArticles modelArticle) {
//		this.modelArticle = modelArticle;
//	}
	
//	public void imprimeEtiquette(TaArticle f, String modele, int typeAdresse) {
//		try {
//
//			List<TaArticle> listeFinale = new ArrayList<TaArticle>();
//			listeFinale.add(f);
//
//			//			List<TaTiers> listeFinale = new ArrayList<TaTiers>();
//			//			listeFinale.add(tiers);
//
//			String repert= new File(Platform.getInstanceLocation().getURL().getFile()).getPath();
//			String cheminFichierDonnees = new File(Const.C_CHEMIN_REP_TMP_COMPLET+"/Etiquettes"+"-"+LibDate.dateToString(new Date(),"")+".txt").getPath();
//
//			String cheminFichierMotCle = LgrFileBundleLocator.bundleToFile(generationlabeletiquette.Activator.getDefault().getBundle(), "/modelEtiquette/motcle.properties").getPath();
////			String cheminFichierMotCle = new File(Const.pathRepertoireSpecifiques("GenerationLabelEtiquette", "modelEtiquette")+"/motcle.properties").getPath();
//
//			FichierDonneesArticle donneesAdresse = null;
////			if(typeAdresse == FichierDonneesAdresseFacture.TYPE_ADRESSE_FACTURATION) {
////				donneesAdresse = new FichierDonneesAdresseFacture(FichierDonneesAdresseFacture.TYPE_ADRESSE_FACTURATION);
////			} else if(typeAdresse == FichierDonneesAdresseFacture.TYPE_ADRESSE_LIVRAISON) {
////				donneesAdresse = new FichierDonneesAdresseFacture(FichierDonneesAdresseFacture.TYPE_ADRESSE_LIVRAISON);
////			}
//			donneesAdresse.creationFichierDonneesArticle(listeFinale, repert, cheminFichierDonnees);
//
//			ParamWizardEtiquettes p = null;
//			p = new ParamWizardEtiquettes();
//			if(!modele.equals(HeadlessEtiquette.CHOIX_DEFAUT_CCOMB_PARAM_ETIQUETTE)) {
//				p.setChangeStartingPage(true);
////
////
////				HeadlessEtiquette headlessEtiquette = new HeadlessEtiquette();
////				headlessEtiquette.lectureParam(modele);
////				headlessEtiquette.getParameterEtiquette().setPathFileExtraction(cheminFichierDonnees);
////				headlessEtiquette.getParameterEtiquette().setPathFileMotCle(cheminFichierMotCle);
////				//getParameterEtiquette().setPathFileMotCle(null);
////				headlessEtiquette.print();
//			} 
//			//else {
//				//FichierDonneesAdresseTiers donneesTiers = new FichierDonneesAdresseTiers();
//				//donneesTiers.creationFichierDonneesAdresse(listeFinale, repert, cheminFichierDonnees);
//
//				//creationFichierDonnees(listeFinale,repert,cheminFichierDonnees);
//
//				//ParamWizardEtiquettes p = null;
//				//p = new ParamWizardEtiquettes();
//				p.setModelePredefini(modele);
//				p.setModeIntegre(true);
//				p.setCheminFichierDonnees(cheminFichierDonnees);
//				p.setCheminFichierMotsCle(cheminFichierMotCle);
//				p.setSeparateur(";");
//				
//
//				IPreferenceStore store = ArticlesPlugin.getDefault().getPreferenceStore();
//				//store.setValue(fr.legrain.articles.preferences.PreferenceConstants.P_DERNIER_MODELE_UTILISE_ETIQUETTE, modele);
//
//				//GenerationFileEtiquette generationFileEtiquette = new GenerationFileEtiquette();
//				//WizardModelLables wizardModelLables = new WizardModelLables(generationFileEtiquette,p);
//				WizardModelLables wizardModelLables = new WizardModelLables(new GenerationFileEtiquette());
//				wizardModelLables.initParam(p);
//				WizardDialogModelLabels wizardDialogModelLabels = new WizardDialogModelLabels
//				(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),wizardModelLables);
//				wizardDialogModelLabels.open();
//				//wizardDialogModelLabels.showPage(wizardModelLables.getPage(ConstantModelLabels.NAME_PAGE_FORMAT_ETIQUETTE));
//			//}
//
//
//		} catch (Exception e1) {
//			logger.error("", e1);
//		}
//	}
}

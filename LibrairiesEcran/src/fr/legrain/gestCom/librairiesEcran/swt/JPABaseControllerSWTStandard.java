package fr.legrain.gestCom.librairiesEcran.swt;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.map.IMapChangeListener;
import org.eclipse.core.databinding.observable.map.MapChangeEvent;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.internal.databinding.beans.BeanObservableValueDecorator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.databinding.swt.ISWTObservable;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.fieldassist.SimpleContentProposalProvider;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ActiveShellExpression;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPart3;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.internal.expressions.ActivePartExpression;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.swt.IFocusService;

import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.gestCom.Appli.LgrHibernateValidated;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.gestCom.librairiesEcran.workbench.AbstractLgrMultiPageEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.AbstractLgrDAO;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.InfosVerifSaisie;
import fr.legrain.lib.data.LgrConstantes;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.LibDate;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.data.QueDesChiffres;
import fr.legrain.lib.data.QueDesChiffresIntervaleDecimale;
import fr.legrain.lib.data.QueDesChiffresPositif;
import fr.legrain.lib.data.VerrouInterface;
import fr.legrain.lib.gui.DefaultFrameBoutonSWT;
import fr.legrain.lib.gui.DefaultFrameFormulaireSWT;
import fr.legrain.lib.gui.DefaultFrameFormulaireSWTSansGrille;
import fr.legrain.lib.gui.DefaultFrameFormulaireSWTSimpleToolBarHaut;
import fr.legrain.lib.gui.DefaultFrameFormulaireSWTTree;
import fr.legrain.lib.gui.DestroyEvent;
import fr.legrain.lib.gui.DestroyListener;
import fr.legrain.lib.gui.OrdreFocusSWT;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.SortieChampsEvent;
import fr.legrain.lib.gui.SortieChampsListener;
import fr.legrain.lib.gui.cdatetimeLgr;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.lib.gui.grille.LgrTreeViewer;
import fr.legrain.lib.validation.CDateTimeObservableValue;
import fr.legrain.lib.validation.ConvertBigDecimal2String;
import fr.legrain.lib.validation.ConvertInteger2String;
import fr.legrain.lib.validation.ConvertString2BigDecimal;
import fr.legrain.lib.validation.ConvertString2Integer;
import fr.legrain.lib.validation.JPACtrlInterface;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Le Grain SA</p>
 * @version 1.0
 */

public abstract class JPABaseControllerSWTStandard extends JPABaseControllerSWT implements
SortieChampsListener, ShellListener, DestroyListener, IDeclencheCommandeControllerListener {
	private Boolean activeAide=false;
	protected Object sourceDeclencheCommandeController = null;
	/***/
	protected boolean enregistreToutEnCours = false;
	/***/
	
	protected boolean EnregistreEtFerme = false;
	
	protected boolean ControleForce = false;
	
	
	protected boolean annuleToutEnCours = false;
	public static final String C_COMMAND_GLOBAL_CTRL_SPACE_ID 		= "fr.legrain.gestionCommerciale.ctrlSpace";
	public static final String C_COMMAND_GLOBAL_AIDE_ID 		= "fr.legrain.gestionCommerciale.aide";
	public static final String C_COMMAND_GLOBAL_ANNULER_ID 		= "fr.legrain.gestionCommerciale.annuler";
	public static final String C_COMMAND_GLOBAL_ENREGISTRER_ID 	= "fr.legrain.gestionCommerciale.enregsitrer";
	public static final String C_COMMAND_GLOBAL_FERMER_ID 		= "fr.legrain.gestionCommerciale.fermer";
	public static final String C_COMMAND_GLOBAL_IMPRIMER_ID 	= "fr.legrain.gestionCommerciale.imprimer";
	public static final String C_COMMAND_GLOBAL_INSERER_ID 		= "fr.legrain.gestionCommerciale.inserer";
	public static final String C_COMMAND_GLOBAL_MODIFIER_ID 	= "fr.legrain.gestionCommerciale.modifier";
	public static final String C_COMMAND_GLOBAL_SUPPRIMER_ID 	= "fr.legrain.gestionCommerciale.supprimer";
	public static final String C_COMMAND_GLOBAL_REFRESH_ID 	    = "fr.legrain.gestionCommerciale.refresh";
	public static final String C_COMMAND_GLOBAL_PRECEDENT_ID 	= "fr.legrain.gestionCommerciale.precedent";
	public static final String C_COMMAND_GLOBAL_SUIVANT_ID 	    = "fr.legrain.gestionCommerciale.suivant";
	public static final String C_COMMAND_DOCUMENT_CREATEDOC_ID = "fr.legrain.Document.CreerDoc";
	public static final String C_COMMAND_DOCUMENT_CREATEMODELE_ID = "fr.legrain.Document.CreerModele";
	
	public static final String C_COMMAND_DOCUMENT_AFFICHER_TOUS_ID = "fr.legrain.Document.afficherTous";

	public static final String C_COMMAND_GLOBAL_SELECTION_ID = "fr.legrain.gestionCommerciale.selection";
	
	public static final String C_COMMAND_DOCUMENT_LISTE_DOCUMENT_ID = "fr.legrain.Document.listeDocument";


//	public static final String C_ACTIVEWHEN_GLOBAL_ID 		= "fr.legrain.gestionCommerciale.activeWhen";
//	public static final String C_ACTIVEWHEN_FACTURE_ID 		= "fr.legrain.Facture.activeWhen";

//	public static final String C_ACTIVEWHEN_GLOBAL_ANNULER_ID 		= "fr.legrain.gestionCommerciale.annuler";
//	public static final String C_ACTIVEWHEN_GLOBAL_ENREGISTRER_ID 	= "fr.legrain.gestionCommerciale.enregsitrer";
//	public static final String C_ACTIVEWHEN_GLOBAL_FERMER_ID 		= "fr.legrain.gestionCommerciale.fermer";
//	public static final String C_ACTIVEWHEN_GLOBAL_IMPRIMER_ID 	= "fr.legrain.gestionCommerciale.imprimer";
//	public static final String C_ACTIVEWHEN_GLOBAL_INSERER_ID 		= "fr.legrain.gestionCommerciale.inserer";
//	public static final String C_ACTIVEWHEN_GLOBAL_MODIFIER_ID 	= "fr.legrain.gestionCommerciale.modifier";
//	public static final String C_ACTIVEWHEN_GLOBAL_SUPPRIMER_ID 	= "fr.legrain.gestionCommerciale.supprimer";
//	public static final String C_ACTIVEWHEN_GLOBAL_REFRESH_ID 	    = "fr.legrain.gestionCommerciale.refresh";

	public IContextService contextService = null;
	
	private EntityManager em = null;
	static private PropertiesConfiguration listeChampGrille = new PropertiesConfiguration();


	
	public FieldDecorationRegistry registry = FieldDecorationRegistry.getDefault();
	public ActiveShellExpression activeShellExpression ;
	public ActivePartExpression activePartExpression ;
	//public LGRPartExpression activePartExpression ;
	private IWorkbenchPart3 workbenchPart = null;

	static Logger logger = Logger.getLogger(JPABaseControllerSWTStandard.class.getName());
	protected AbstractLgrDAO daoStandard=null;
	private Table grille = null;
	private LgrTableViewer tableViewerStandard = null;
	private LgrTreeViewer treeViewerStandard = null;
	private DataBindingContext dbcStandard = null;
	private String titre = null;
	//private boolean autoInsert = true; //insertion autommatique quand on arrive en fin de grille

//	protected EventListenerList listenerList = new EventListenerList();
	protected IHandlerService handlerService = null;

	protected ActionInserer actionInserer = new ActionInserer(LgrConstantes.C_LIB_BTNINSERER);
	protected ActionEnregistrer actionEnregistrer = new ActionEnregistrer(LgrConstantes.C_LIB_BTNENREGISTRER);
	protected ActionModifier actionModifier = new ActionModifier(LgrConstantes.C_LIB_BTNMODIFIER);
	protected ActionSupprimer actionSupprimer = new ActionSupprimer(LgrConstantes.C_LIB_BTNSUPPRIMER);
	protected ActionFermer actionFermer = new ActionFermer(LgrConstantes.C_LIB_BTNFERMER);
	protected ActionAnnuler actionAnnuler = new ActionAnnuler(LgrConstantes.C_LIB_BTNANNULER);
	protected ActionImprimer actionImprimer = new ActionImprimer(LgrConstantes.C_LIB_BTNIMPRIMER);
	protected ActionAide actionAide = new ActionAide(LgrConstantes.C_LIB_BTNAIDE);
	protected ActionRefresh actionRaffraichir = new ActionRefresh(LgrConstantes.C_LIB_BTNREFRESH);

	protected HandlerInserer handlerInserer = new HandlerInserer();
	protected HandlerEnregistrer handlerEnregistrer = new HandlerEnregistrer();
	protected HandlerModifier handlerModifier = new HandlerModifier();
	protected HandlerSupprimer handlerSupprimer = new HandlerSupprimer();
	protected HandlerFermer handlerFermer = new HandlerFermer();
	protected HandlerAnnuler handlerAnnuler = new HandlerAnnuler();
	protected HandlerImprimer handlerImprimer = new HandlerImprimer();
	protected HandlerAide handlerAide = new HandlerAide();
	protected HandlerRefresh handlerRefresh = new HandlerRefresh();
	protected HandlerPrecedent handlerPrecedent = new HandlerPrecedent();
	protected HandlerSuivant handlerSuivant = new HandlerSuivant();
	protected HandlerSelection handlerSelection = new HandlerSelection();
	protected HandlerCreateDoc handlerCreateDoc = new HandlerCreateDoc();
	protected HandlerCreateModele handlerCreateModele = new HandlerCreateModele();
	
	protected HandlerAfficherTous handlerAfficherTous = new HandlerAfficherTous();
	
	protected LgrModifyListener lgrModifyListener = new LgrModifyListener(); //surveille les modifications des champs relies a la bdd
	protected LgrFocusListenerCdatetime dateFocusListener = new LgrFocusListenerCdatetime();

	// correspondance composant graphique/champs bdd
	protected Map<Control,String> mapComposantChamps = null;
	protected HashMap<Control, InfosVerifSaisie> mapInfosVerifSaisie = null;
	protected Map<Control,DecoratedField> mapComposantDecoratedField = null;
	protected Map<Control,LgrUpdateValueStrategy> mapComposantUpdateValueStrategy = null;
	
	protected Map<ExpandBar,Map<Composite,ExpandItem>> mapExpandBar = null;

	//composant pouvant prendre le focus
	protected List<Control> listeComposantFocusable = null;
	//Composant qui possede le focus par defaut suivant le mode
	protected Map<ModeObjet.EnumModeObjet,Control> mapInitFocus = null;

	protected Map<Button,Object> mapActions = null;
	protected  Map<Button,Object> mapActionsHorsPopup= null;
	protected Map<String, org.eclipse.core.commands.IHandler> mapCommand = null;

	protected Map<Control, Object> listeBindSpec;
	public QueDesChiffres queDesChiffres = new QueDesChiffres();
	public QueDesChiffresPositif queDesChiffresPositifs = new QueDesChiffresPositif();
	//protected IContextActivation iContext;
	
	//declenchement d'action sans afficher de boite de dialogue
	//Ex: annulation sans demander de confirmation
	protected boolean silencieu = false;
	
	abstract protected void actInserer() throws Exception;
	abstract protected void actEnregistrer() throws Exception;
	abstract protected void actModifier() throws Exception;
	abstract protected void actSupprimer() throws Exception;
	abstract protected void actFermer() throws Exception;
	abstract protected void actAnnuler() throws Exception;
	abstract protected void actImprimer() throws Exception;
	
	abstract protected void actAide(String message) throws Exception;
	abstract protected void actRefresh() throws Exception;
	//abstract protected void actAfficherTous() throws Exception;
	
	protected void actPrecedent() throws Exception {}
	protected void actSuivant() throws Exception {}
	protected void actSelection() throws Exception {}
	protected void actCreateDoc() throws Exception {}
	protected void actCreateModele() throws Exception {}
	
	protected List<IDocumentTiers> listeDocument = null;
	
	abstract protected void initActions();


	/**
	 * Initialisation des composants graphiques de la vue.
	 * @throws ExceptLgr
	 */
	abstract protected void initComposantsVue() throws ExceptLgr;

	/**
	 * Initialisation des correspondances entre les champs de formulaire et les
	 * champs de bdd
	 */
	abstract protected void initMapComposantChamps();
	
//	abstract protected void initVerifySaisie();

	abstract protected void initMapComposantDecoratedField();

	abstract public void initEtatComposant();

//	abstract protected void initEtatBouton();

	//abstract public void actualiserForm();

	//abstract public void resetForm();

	public JPABaseControllerSWTStandard(Composite vue, EntityManager em, Map<Control, String> mapComposantChamps) {
		super();
		if(em!=null) {
			setEm(em);
		}
		//initController();
	}
	
	public JPABaseControllerSWTStandard() {
		super();
	
}


	public void initEtatComposantGeneral(){
//TODO #JPA
//		if (ibTaTableStandard.getFIBQuery().isOpen()){
//			Iterator iteChamps = mapComposantChamps.keySet().iterator();
//			Control champ = null;
//			while (iteChamps.hasNext()) {
//				champ=(Control)iteChamps.next();
//				if(champ instanceof Text)				
//					((Text)champ).setEditable(ibTaTableStandard.recordModifiable(ibTaTableStandard.nomTable,ibTaTableStandard.getChamp_Obj(ibTaTableStandard.champIdTable)));
//				if(champ instanceof Button)
//					if( (((Button)champ).getStyle() & SWT.CHECK) != 0 )
//						((Button)champ).setEnabled(ibTaTableStandard.recordModifiable(ibTaTableStandard.nomTable,ibTaTableStandard.getChamp_Obj(ibTaTableStandard.champIdTable)));
//			}
//		}
	}
	
	
	/**
	 * Methode a surcharge
	 * @throws Exception
	 */
	protected void actAide() throws Exception{
		actAide(null);
	}
	
	

	
	/**
	 * Methode a surcharge
	 * Doit retourne vrai ssi le composant ayant le focus propose de l'aide
	 * @return
	 */
	protected boolean aideDisponible() {
		boolean result = false;
		return result;
	}
	
	public void bindForm(Map<Control,String> mapComposantChamps, Class classeEntite,Object selectedObject,Display display) {
		bindForm(null, mapComposantChamps, classeEntite, selectedObject, display);
	}
	
	public void bindForm(Map<Control,LgrUpdateValueStrategy> mapComposantUpdateValueStrategy,Map<Control,String> mapComposantChamps, Class classeEntite,Object selectedObject,Display display) {
		Realm realm = SWTObservables.getRealm(display);

		DataBindingContext dbc = new DataBindingContext(realm);

//		dbc.getValidationStatusMap().addMapChangeListener(changeListener);
		LgrDatabindingUtil lgrDatabindingUtil = new LgrDatabindingUtil();
		lgrDatabindingUtil.bindingFormSimple(null, mapComposantChamps, dbc, realm,selectedObject, classeEntite,mapComposantUpdateValueStrategy);
	}
	/**
	 * Creation des Bindings pour chaque champ du formulaire en fonction de <code>mapComposantChamps</code>.
	 * Remplissage de <code>listeBindSpec</code>
	 * @param dbc
	 * @param realm
	 * @param selectedObject - Objet courant dans le modele
	 * @param selectedObjectClass - Classe des objets presents dans le modele
	 */
	public void bindingFormMaitreDetail(DataBindingContext dbc, Realm realm, IObservableValue selectedObject, Class selectedObjectClass) {
		bindingFormMaitreDetail(mapComposantChamps,dbc,realm,selectedObject,selectedObjectClass);
	}
	/**
	 * Creation des Bindings pour chaque champ du formulaire en fonction de <code>mapComposantChamps</code>.
	 * Remplissage de <code>listeBindSpec</code>
	 * @param dbc
	 * @param realm
	 * @param selectedObject - Objet courant dans le modele
	 * @param selectedObjectClass - Classe des objets presents dans le modele
	 */
	public void bindingFormMaitreDetail(Map<Control,String> mapComposantChamps,DataBindingContext dbc, Realm realm, IObservableValue selectedObject, Class selectedObjectClass) {
		try {
			//UI to model
			UpdateValueStrategy uiToModel;
//			UpdateListStrategy uiToModelList;
			// model to UI
			UpdateValueStrategy modelToUI;
//			UpdateListStrategy modelToUIList;
			String nomChamp = null;
			if(listeBindSpec == null) {
				listeBindSpec = new HashMap<Control, Object>();
			}
			
			for (Control c : mapComposantChamps.keySet()) {
				uiToModel =  new UpdateValueStrategy();
				modelToUI =  new UpdateValueStrategy() {

					
					@Override
					protected IStatus doSet(IObservableValue observableValue, Object value) {
						boolean verrouLocal=VerrouInterface.isVerrouille();
						VerrouInterface.setVerrouille(true);
						initEtatComposant();
						IStatus retour =  super.doSet(observableValue, value);
						VerrouInterface.setVerrouille(verrouLocal);
						return retour;
					}
					
				};
//				uiToModelList = new UpdateListStrategy();
//				modelToUIList = new UpdateListStrategy();
				
				uiToModel =  new UpdateValueStrategy();//.setAfterConvertValidator(new CtrlInterface(ibTaTableStandard,mapComposantChamps.get(c)));
				Field field=retourneField(selectedObjectClass,c,mapComposantChamps);
				if(field==null)throw new NoSuchFieldException();
				if(field.getType().equals(BigDecimal.class) ) {
					uiToModel.setConverter(new ConvertString2BigDecimal());
					modelToUI.setConverter(new ConvertBigDecimal2String());
				} else if( field.getType().equals(Integer.class) ) {
					uiToModel.setConverter(new ConvertString2Integer());
					modelToUI.setConverter(new ConvertInteger2String());
				}
				
				if(mapComposantUpdateValueStrategy!=null 
						&& mapComposantUpdateValueStrategy.get(c)!=null) {
					uiToModel.setConverter(mapComposantUpdateValueStrategy.get(c).getUiToModelConverter());
					modelToUI.setConverter(mapComposantUpdateValueStrategy.get(c).getModelToUIConverter());
				}

				listeBindSpec.put(c, uiToModel);
				nomChamp = correctionNomChamp(mapComposantChamps.get(c));
				if(c instanceof Text) {
						dbc.bindValue(SWTObservables.observeText((Text)c, SWT.FocusOut),
								BeansObservables.observeDetailValue(/*realm,*/ selectedObject, nomChamp, field.getType()),uiToModel,modelToUI
						);

				} else if(c instanceof Button) {
					dbc.bindValue(SWTObservables.observeSelection((Button)c),
							BeansObservables.observeDetailValue(/*realm,*/ selectedObject, nomChamp, Boolean.class),uiToModel,modelToUI
					);
				}
//				else if(c instanceof CDateTime) {
//					dbc.bindValue(new CDateTimeObservableValue((cdatetimeLgr)c),
//							BeansObservables.observeDetailValue(/*realm,*/ selectedObject, nomChamp, selectedObjectClass.getDeclaredField(mapComposantChamps.get(c)).getType()),uiToModel,modelToUI
//							//BeansObservables.observeValue(realm, selectedObject, nomChamp),uiToModel,modelToUI
//					);
//				}
				else if(c instanceof DateTime) {
					dbc.bindValue(SWTObservables.observeSelection((DateTime)c),
							BeansObservables.observeDetailValue(/*realm,*/ selectedObject, nomChamp, Date.class),uiToModel,modelToUI
					);
				} 
				else if(c instanceof Combo) {
					dbc.bindValue(WidgetProperties.selection().observe((Combo)c),
							BeansObservables.observeDetailValue(/*realm,*/ selectedObject, nomChamp, field.getType()),uiToModel,modelToUI
					);
				}
				//branchement des validators apres le binding pour eviter le 1er controle qui risque d'etre faux
				uiToModel.setAfterConvertValidator(new JPACtrlInterface(this,mapComposantChamps.get(c)));
			}
		} catch (SecurityException e) {
			logger.error("",e);
		} catch (NoSuchFieldException e) {
			logger.error("",e);
		}
	}
	
	/** Binding d'un formulaire simple : pas de maitre/détail
	 * @param dbc - DataBindingContext
	 * @param realm - Realm
	 * @param selectedObject - Objet courant dans le modele
	 * @param selectedObjectClass - Classe des objets presents dans le modele
	 */
	public void bindingFormSimple(DataBindingContext dbc, Realm realm, Object selectedObject, Class selectedObjectClass) {
		bindingFormSimple(daoStandard,mapComposantChamps, dbc, realm, selectedObject, selectedObjectClass);
	}
	
	/** Binding d'un formulaire simple : pas de maitre/détail
	 * @param dbc - DataBindingContext
	 * @param realm - Realm
	 * @param selectedObject - Objet courant dans le modele
	 * @param selectedObjectClass - Classe des objets presents dans le modele
	 */
	public void bindingFormSimple(AbstractLgrDAO ibTaTableStandard,Map<Control,String> mapComposantChamps, DataBindingContext dbc, Realm realm, Object selectedObject, Class selectedObjectClass) {
		try {

			//UI to model
			UpdateValueStrategy uiToModel;
			// model to UI
			UpdateValueStrategy modelToUI;
			String nomChamp = null;
			if(listeBindSpec == null) {
				listeBindSpec = new HashMap<Control, Object>();
			}
			for (final Control c : mapComposantChamps.keySet()) {
				
				uiToModel =  new UpdateValueStrategy();
				modelToUI =  new UpdateValueStrategy() {
					@Override
					protected IStatus doSet(IObservableValue observableValue, Object value) {
						boolean verrouLocal=VerrouInterface.isVerrouille();
						VerrouInterface.setVerrouille(true);
						initEtatComposant();
						IStatus retour =  super.doSet(observableValue, value);
						VerrouInterface.setVerrouille(verrouLocal);
						return retour;
					}
				};

				uiToModel = new UpdateValueStrategy();
				Field field=retourneField(selectedObjectClass,c,mapComposantChamps);
				if(field==null)throw new NoSuchFieldException();
//				try {
//					field = selectedObjectClass.getDeclaredField(mapComposantChamps.get(c));
//				} catch (NoSuchFieldException e) {}
//				Class objet =selectedObjectClass.getSuperclass();
//				while(objet!=null){
//					if(field==null)field=objet.getDeclaredField(mapComposantChamps.get(c));
//					objet =objet.getSuperclass();
//				}

				if( field.getType().equals(BigDecimal.class) ) {
					uiToModel.setConverter(new ConvertString2BigDecimal());
					modelToUI.setConverter(new ConvertBigDecimal2String());
				}		
				if( field.getType().equals(Double.class) ) {
//					uiToModel.setConverter(new ConvertString2Integer());
					modelToUI.setConverter(new ConvertInteger2String());
				}
				
				listeBindSpec.put(c, uiToModel);
				nomChamp = correctionNomChamp(mapComposantChamps.get(c));
				if(c instanceof Text) {
					dbc.bindValue(SWTObservables.observeText((Text)c, SWT.FocusOut),
							BeansObservables.observeValue(realm, selectedObject, nomChamp),uiToModel,modelToUI
					);

				} else if(c instanceof Button) {
					dbc.bindValue(SWTObservables.observeSelection((Button)c),
							BeansObservables.observeValue(realm, selectedObject, nomChamp),uiToModel,modelToUI
					);
				} 
//				else if(c instanceof CDateTime) {
//					dbc.bindValue(new CDateTimeObservableValue((cdatetimeLgr)c),
//							BeansObservables.observeValue(realm, selectedObject, nomChamp),uiToModel,modelToUI
//					);
//				}
				else if(c instanceof DateTime) {
					dbc.bindValue(SWTObservables.observeSelection((DateTime)c),
							BeansObservables.observeValue(realm, selectedObject, nomChamp),uiToModel,modelToUI
					);
				}
				//branchement des validators apres le binding pour eviter le 1er controle qui risque d'etre faux
				uiToModel.setAfterConvertValidator(new JPACtrlInterface(this,nomChamp));
			}
		} catch (SecurityException e) {
			logger.error("",e);
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			logger.error("",e);
		}
	}
	
	/**
	 * Supprime tous les bindings enregistrer dans ce contexte
	 * @param dbc
	 */
	public void unBind(DataBindingContext dbc) {
		Object[] ite = dbc.getBindings().toArray();
		Binding b = null;
		for (int i = 0; i < ite.length; i++) {
			((Binding)ite[i]).dispose();
		}
		ite = null;
	}
	
	public void closeWorkbenchPart() {
		IViewReference[] v;
		if(getWorkbenchPart()!=null) {
			if(getWorkbenchPart() instanceof IEditorPart)
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeEditor((IEditorPart) getWorkbenchPart(), false);
			else if(getWorkbenchPart() instanceof MultiPageEditorPart) {
				
			}
			else if(getWorkbenchPart() instanceof IViewPart) {
//				v = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getViewReferences();
//				for (int i = 0; i < v.length; i++) {
//					if(v[i].getPart(false)==getWorkbenchPart()) {
//						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
//					}
//				}
			}
		} else if(vue.getShell()!=PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell()) {
			//ce n'est ni une vue, ni un editeur et ce n'est pas non plus la fenetre principale du programme donc on ferme le fenetre
			vue.getShell().close();
		}
				
			
	}

	/**
	 * Pour le databinding, le nom des proprietes d'un objet doivent respecter les conventions de nommage Java (java beans / java.beans.BeanInfo).<br>
	 * Le nom d'une propriete devrait donc commencer par une minuscule.<br>
	 * Dans le programme, le nom des champs est de la forme "ABC_DEF_GHI" donc il peut y avoir des problèmes.<br>
	 * Dans le cas ou il y a plusieurs lettre, il n'y a pas de probleme ABC_DEF, BeanInfo laisse tout en majuscule, le champ peut donc etre trouve pendant le databinding<br>
	 * S'il n'y a qu'une seule lettre avant le "_", il y a un probleme A_BCDEF, BeanInfo retourne a_BCDEF, le champ ne peut donc pas etre trouve.<br>
	 * 
	 * @param String - chaine de caractère
	 * @return - si la chaine est de la forme "A_BCD" retourne la meme chaine avec le caractere avant le underscore en minuscule,<br>
	 *  dans tous les autres cas, retourne la meme chaine que celle passe en parametre.
	 */
	private String correctionNomChamp(String a) {
		String resultat;
		String fin = a.substring(1);
		String debut = a.substring(0,1);
		if(fin.startsWith("_"))
			resultat = debut.toLowerCase()+fin;
		else
			resultat = a;
		return resultat;
	}
	
	/**
	 * Initialisation de l'ordre dans lequel le focus doit etre transmis.
	 */
	protected void initFocusOrder() {
		if(listeComposantFocusable!=null && !listeComposantFocusable.isEmpty()) {
			OrdreFocusSWT ordreFocusSWT = new OrdreFocusSWT(listeComposantFocusable);
			for (Control c : listeComposantFocusable) {
				c.addTraverseListener(ordreFocusSWT);
			}
		}
	}
	
	/*
	 * TEST DES JFACE CONTENT PROPOSAL SUR LES CHAMPS TEXT
	 * - probleme de focus traversal sur up et down
	 * - si on clique sur une proposition, focusout sur le Text et declenchement des verifs
	 * - si selection d'une proposition avec entree => apparement pas de verif
	 */
	private void initContentProposal() {
		try {
			char[] autoActivationCharacters = new char[] { '#', '(' };
			KeyStroke keyStroke = KeyStroke.getInstance("Ctrl+Space");

			for (Control c : listeComposantFocusable) {
				if(c instanceof Text) {
					ContentProposalAdapter adapter = new ContentProposalAdapter(
							(Text)c, new TextContentAdapter(),
							new SimpleContentProposalProvider(new String [] {"ProposalOne", "ProposalTwo", "ProposalThree"}),
							keyStroke, autoActivationCharacters);
					adapter.setFilterStyle(ContentProposalAdapter.FILTER_NONE);
					adapter.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);
				}
			}
		} catch(Exception e) {
			logger.error("",e);
		}
	}

	
	protected class ActionInserer extends Action {
		public ActionInserer(String name) {
			super(name);
		}

		@Override
		public void run() {
			super.run();
			try {
				handlerService.executeCommand(C_COMMAND_GLOBAL_INSERER_ID, null);
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

	protected class ActionEnregistrer extends Action {
		public ActionEnregistrer(String name) {
			super(name);
		}

		@Override
		public void run() {
			super.run();
			try {
				handlerService.executeCommand(C_COMMAND_GLOBAL_ENREGISTRER_ID, null);
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

	protected class ActionAnnuler extends Action {
		public ActionAnnuler(String name) {
			super(name);
		}

		@Override
		public void run() {
			super.run();
//			try {
				try {
					actAnnuler();
				} catch (Exception e) {
					logger.error("", e);
				}
				//handlerService.executeCommand(C_COMMAND_GLOBAL_ANNULER_ID, null);
//				catch (ExecutionException e) {
//				logger.error("",e);
//			} catch (NotDefinedException e) {
//				logger.error("",e);
//			} catch (NotEnabledException e) {
//				logger.error("",e);
//			} catch (NotHandledException e) {
//				logger.error("",e);
//			}
		}

	}

	protected class ActionSupprimer extends Action {
		public ActionSupprimer(String name) {
			super(name);
		}

		@Override
		public void run() {
			super.run();
			try {
				handlerService.executeCommand(C_COMMAND_GLOBAL_SUPPRIMER_ID, null);
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

	protected class ActionModifier extends Action {
		public ActionModifier(String name) {
			super(name);
		}

		@Override
		public void run() {
			super.run();
			try {
				handlerService.executeCommand(C_COMMAND_GLOBAL_MODIFIER_ID, null);
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

	protected class ActionFermer extends Action {
		public ActionFermer(String name) {
			super(name);
		}

		@Override
		public void run() {
			super.run();
			try {
				handlerService.executeCommand(C_COMMAND_GLOBAL_FERMER_ID, null);
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

	protected class ActionImprimer extends Action {
		public ActionImprimer(String name) {
			super(name);
		}

		@Override
		public void run() {
			super.run();
			try {
				handlerService.executeCommand(C_COMMAND_GLOBAL_IMPRIMER_ID, null);
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

	protected class ActionAide extends Action {
		public ActionAide(String name) {
			super(name);
		}

		@Override
		public void run() {
			super.run();
			try {
				handlerService.executeCommand(C_COMMAND_GLOBAL_AIDE_ID, null);
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

	
	protected class ActionRefresh extends Action {
		public ActionRefresh(String name) {
			super(name);
		}

		@Override
		public void run() {
			super.run();
			try {
				handlerService.executeCommand(C_COMMAND_GLOBAL_REFRESH_ID, null);
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
	
	protected class HandlerPrecedent extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException{
			try {
				actPrecedent();
			} catch (Exception e) {
				logger.error("",e);
			}
			return event;
		}
	}
	
	protected class HandlerSuivant extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException{
			try {
				actSuivant();
			} catch (Exception e) {
				logger.error("",e);
			}
			return event;
		}
	}

	
	protected class HandlerSelection extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException{
			try {
				actSelection();
			} catch (Exception e) {
				logger.error("",e);
			}
			return event;
		}
	}
	
	protected class HandlerAide extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException{
			try {
				setFocusAvantAideSWT(getFocusCourantSWT());
				actAide();
			} catch (Exception e) {
				logger.error("",e);
			}
			return event;
		}
	}

	
	
	protected class HandlerAnnuler extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException{
			try {
				actAnnuler();
			} catch (Exception e) {
				logger.error("",e);
			}
			return event;
		}
	}

	
	protected class HandlerRefresh extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException{
			try {
				actRefresh();
			} catch (Exception e) {
				logger.error("",e);
			}
			return event;
		}
	}
	
	protected class HandlerEnregistrer extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException{
			try {
				actEnregistrer();
				if(getEnregistreEtFerme())actFermer();
			} catch (Exception e) {
				logger.error("",e);
			}
			return event;
		}
	}

	protected class HandlerFermer extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException{
			try {
				actFermer();
			} catch (Exception e) {
				logger.error("",e);
			}
			return event;
		}
	}

	protected class HandlerImprimer extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException{
			try {
				actImprimer();
			} catch (Exception e) {
				logger.error("",e);
			}
			return event;
		}
	}

	protected class HandlerInserer extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException{
			try {
				actInserer();
			} catch (Exception e) {
				logger.error("",e);
			}
			return event;
		}
	} 

	protected class HandlerModifier extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException{
			try {
				actModifier();
			} catch (Exception e) {
				logger.error("",e);
			}
			return event;
		}
	}

	protected class HandlerSupprimer extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException{
			try {
				actSupprimer();
			} catch (Exception e) {
				logger.error("",e);
			}
			return event;
		}
	}

	/**
	 * Active/Desactive une action, son handler (commande) ainsi que le bouton qui lui est associe
	 * dans <code>mapAction</code>. Si l'action n'est pas liee a un bouton, seul l'etat de l'action et du handler sont modifies.
	 * @param a - l'action dont on souhaite modifier l'etat
	 * @param h - le handler associe a l'action
	 * @param enabled - nouvel etat
	 */
	protected void enableActionAndHandler(Action a, LgrAbstractHandler h, boolean enabled) {
		a.setEnabled(enabled);
		h.setEnabled(enabled);
		if(mapActions != null) {
			for (Object button : mapActions.keySet()) {
				if(button!=null && mapActions.get(button)==h)
					((Button)button).setEnabled(enabled);
			}
		}
	}
	
	/**
	 * Active/Desactive une action, son handler (commande) ainsi que le bouton qui lui est associe
	 * dans <code>mapAction</code>. Si l'action n'est pas liee a un bouton, seul l'etat de l'action et du handler sont modifies.
	 * @param a - l'action dont on souhaite modifier l'etat
	 * @param h - le handler associe a l'action
	 * @param enabled - nouvel etat
	 */
	protected void enableActionAndHandler(String commandId, boolean enabled) {
		ICommandService cs = (ICommandService)PlatformUI.getWorkbench().getService(ICommandService.class);
		if(mapCommand!=null && ((LgrAbstractHandler)mapCommand.get(commandId))!=null) {
			((LgrAbstractHandler)mapCommand.get(commandId)).setEnabled(enabled);
//			a.setEnabled(enabled);
//			h.setEnabled(enabled);
			if(mapActions != null) {
				for (Object button : mapActions.keySet()) {
					if(button!=null && mapActions.get(button)==commandId)
						((Button)button).setEnabled(enabled);
				}
			}
		} else {
			logger.debug("Commande "+commandId+" inconnue.");
		}
	}

	/**
	 * Effactue la liaison entre les boutons et les actions (ajout d'un SelectionListener qui effectue l'action adequat).
	 * La correspondance Action/Bouton se fait par l'intermediaire de <code>mapActions</code> qui devra etre remplie.
	 */
	protected void branchementBouton() {
		initImageBouton();
		if(mapActions!=null) {
			for (final Object button : mapActions.keySet()) {
				if(button!=null)
					((Button)button).addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							if(mapActions.get(button) instanceof Action)
								((Action)mapActions.get(button)).run();
							else if(mapActions.get(button) instanceof String)
								try {
									handlerService.executeCommand((String)mapActions.get(button), null);
								} catch (ExecutionException ex) {
									logger.error("",ex);
								} catch (NotDefinedException ex) {
									logger.error("",ex);
								} catch (NotEnabledException ex) {
									logger.error("",ex);
								} catch (NotHandledException ex) {
									logger.error("",ex);
								}
						}
					});
			}
		}
	}
	
	protected void branchementBouton(final Map<Button, Object> mapActionsHorsPopup) {
		initImageBouton();
		if(mapActionsHorsPopup!=null) {
			for (final Object button : mapActionsHorsPopup.keySet()) {
				if(button!=null)
					((Button)button).addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							if(mapActionsHorsPopup.get(button) instanceof Action)
								((Action)mapActionsHorsPopup.get(button)).run();
							else if(mapActionsHorsPopup.get(button) instanceof String)
								try {
									handlerService.executeCommand((String)mapActionsHorsPopup.get(button), null);
								} catch (ExecutionException ex) {
									logger.error("",ex);
								} catch (NotDefinedException ex) {
									logger.error("",ex);
								} catch (NotEnabledException ex) {
									logger.error("",ex);
								} catch (NotHandledException ex) {
									logger.error("",ex);
								}
						}
					});
			}
		}
	}
	
	public void executeCommand(String commandId) {
		executeCommand(commandId,false);
	}
	/**
	 * Execution de la commande <code>commandId</code>
	 */
	public void executeCommand(String commandId, boolean force) {
		try {
			if(force)
				mapCommand.get(commandId).execute(new ExecutionEvent());
			else
				handlerService.executeCommand(commandId, null);
		} catch (ExecutionException ex) {
			logger.error("",ex);
		} catch (NotDefinedException ex) {
			logger.error("",ex);
		} catch (NotEnabledException ex) {
			logger.error("",ex);
		} catch (NotHandledException ex) {
			logger.error("",ex);
		}
	}
	
	protected void initSashFormWeight(int[] weights) {
		if(vue!=null && (vue instanceof DefaultFrameFormulaireSWT)) {
			if(((DefaultFrameFormulaireSWT)vue).getCompositeForm() instanceof SashForm) {
				((DefaultFrameFormulaireSWT)vue).getCompositeForm().setWeights(weights);
			}
		}
	}
	
	protected void initSashFormWeight() {
		int premierComposite = 30;
		int secondComposite = 70;
		initSashFormWeight(new int[]{premierComposite,secondComposite});
	}
	
//	protected String C_IMAGE_ANNULER = "/icons/cancel.png";
//	protected String C_IMAGE_ENREGISTRER = "/icons/disk.png";
//	protected String C_IMAGE_FERMER = "/icons/door_out.png";
//	protected String C_IMAGE_INSERER = "/icons/add.png";
//	protected String C_IMAGE_MODIFIER = "/icons/page_white_edit.png";
//	protected String C_IMAGE_SUPPRIMER = "/icons/delete.png";
//	protected String C_IMAGE_IMPRIMER = "/icons/printer.png";
//	protected String C_IMAGE_ACCEPTER = "/icons/accept.png";
//	protected String C_IMAGE_EXCLAMATION = "/icons/exclamation.png";
//	protected String C_IMAGE_RECHERCHER = "/icons/find.png";
	
	protected void initImageBouton(Composite vue) {
		if(vue instanceof DefaultFrameFormulaireSWT) {
			((DefaultFrameFormulaireSWT)vue).getBtnAnnuler().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ANNULER));
			((DefaultFrameFormulaireSWT)vue).getBtnEnregistrer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ENREGISTRER));
			((DefaultFrameFormulaireSWT)vue).getBtnFermer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_FERMER));
			((DefaultFrameFormulaireSWT)vue).getBtnInserer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_INSERER));
			((DefaultFrameFormulaireSWT)vue).getBtnModifier().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_MODIFIER));
			((DefaultFrameFormulaireSWT)vue).getBtnSupprimer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_SUPPRIMER));
			((DefaultFrameFormulaireSWT)vue).getBtnImprimer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_IMPRIMER));
			vue.layout(true);
		} else if(vue instanceof DefaultFrameFormulaireSWTSansGrille) {
			((DefaultFrameFormulaireSWTSansGrille)vue).getBtnAnnuler().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ANNULER));
			((DefaultFrameFormulaireSWTSansGrille)vue).getBtnEnregistrer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ENREGISTRER));
			((DefaultFrameFormulaireSWTSansGrille)vue).getBtnFermer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_FERMER));
			((DefaultFrameFormulaireSWTSansGrille)vue).getBtnInserer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_INSERER));
			((DefaultFrameFormulaireSWTSansGrille)vue).getBtnModifier().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_MODIFIER));
			((DefaultFrameFormulaireSWTSansGrille)vue).getBtnSupprimer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_SUPPRIMER));
			((DefaultFrameFormulaireSWTSansGrille)vue).getBtnImprimer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_IMPRIMER));
			vue.layout(true);
		} else if(vue instanceof DefaultFrameBoutonSWT) {
			((DefaultFrameBoutonSWT)vue).getBtnAnnuler().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ANNULER));
			((DefaultFrameBoutonSWT)vue).getBtnEnregistrer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ENREGISTRER));
			((DefaultFrameBoutonSWT)vue).getBtnFermer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_FERMER));
			((DefaultFrameBoutonSWT)vue).getBtnInserer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_INSERER));
			((DefaultFrameBoutonSWT)vue).getBtnModifier().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_MODIFIER));
			((DefaultFrameBoutonSWT)vue).getBtnSupprimer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_SUPPRIMER));
			vue.layout(true);
		} else if(vue instanceof DefaultFrameFormulaireSWTTree) {
			((DefaultFrameFormulaireSWTTree)vue).getBtnAnnuler().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ANNULER));
			((DefaultFrameFormulaireSWTTree)vue).getBtnEnregistrer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ENREGISTRER));
			((DefaultFrameFormulaireSWTTree)vue).getBtnFermer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_FERMER));
			((DefaultFrameFormulaireSWTTree)vue).getBtnInserer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_INSERER));
			((DefaultFrameFormulaireSWTTree)vue).getBtnModifier().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_MODIFIER));
			((DefaultFrameFormulaireSWTTree)vue).getBtnSupprimer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_SUPPRIMER));
			((DefaultFrameFormulaireSWTTree)vue).getBtnImprimer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_IMPRIMER));
			vue.layout(true);
		} else if(vue instanceof DefaultFrameFormulaireSWTSimpleToolBarHaut) {
			((DefaultFrameFormulaireSWTSimpleToolBarHaut)vue).getBtnAnnuler().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ANNULER));
			((DefaultFrameFormulaireSWTSimpleToolBarHaut)vue).getBtnEnregistrer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ENREGISTRER));
			((DefaultFrameFormulaireSWTSimpleToolBarHaut)vue).getBtnInserer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_INSERER));
			((DefaultFrameFormulaireSWTSimpleToolBarHaut)vue).getBtnModifier().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_MODIFIER));
			((DefaultFrameFormulaireSWTSimpleToolBarHaut)vue).getBtnSupprimer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_SUPPRIMER));
			vue.layout(true);
		}
		
	}
	
	protected void initImageBouton() {
		initImageBouton(vue);
	}
	
	/**
	 * Initialisation des boutons suivant l'etat de l'objet "ibTaTable"
	 */
	protected void initEtatBouton() {
		//initEtatBouton(true);
		initEtatBoutonCommand(true,null);
	} 
	
	/**
	 * Initialisation des boutons suivant le nombre d'objet dans le modele
	 */
	protected void initEtatBouton(List model) {
		initEtatBoutonCommand(true,model);
	} 

	/**
	 * Initialisation des boutons suivant l'etat de l'objet "ibTaTable"
	 * @param initFocus - si vrai initialise le focus en fonction du mode
	 */
	protected void initEtatBouton(boolean initFocus) {
		boolean trouve = false;		
		//if (ibTaTableStandard.getFIBQuery().isOpen()){
			trouve=daoStandard.selectCount()>0;
			switch (daoStandard.getModeObjet().getMode()) {
			case C_MO_INSERTION:

				enableActionAndHandler(actionInserer,handlerInserer,false);
				enableActionAndHandler(actionModifier,handlerModifier,false);
				enableActionAndHandler(actionEnregistrer,handlerEnregistrer,true);
				enableActionAndHandler(actionAnnuler,handlerAnnuler,true);
				enableActionAndHandler(actionImprimer,handlerImprimer,false);
				enableActionAndHandler(actionFermer,handlerFermer,true);
				enableActionAndHandler(actionSupprimer,handlerSupprimer,false);
				enableActionAndHandler(actionAide,handlerAide,true);
				enableActionAndHandler(actionRaffraichir,handlerRefresh,false);
				if (grille!=null)grille.setEnabled(false);
				break;
			case C_MO_EDITION:
				enableActionAndHandler(actionInserer,handlerInserer,false);
				enableActionAndHandler(actionModifier,handlerModifier,false);
				enableActionAndHandler(actionEnregistrer,handlerEnregistrer,true);
				enableActionAndHandler(actionAnnuler,handlerAnnuler,true);
				enableActionAndHandler(actionImprimer,handlerImprimer,false);
				enableActionAndHandler(actionFermer,handlerFermer,true);
				enableActionAndHandler(actionSupprimer,handlerSupprimer,false);
				enableActionAndHandler(actionAide,handlerAide,true);
				enableActionAndHandler(actionRaffraichir,handlerRefresh,false);
				if (grille!=null)grille.setEnabled(false);
				break;
			case C_MO_CONSULTATION:
				enableActionAndHandler(actionInserer,handlerInserer,true);
				enableActionAndHandler(actionModifier,handlerModifier,trouve);
				enableActionAndHandler(actionEnregistrer,handlerEnregistrer,false);
				enableActionAndHandler(actionAnnuler,handlerAnnuler,true);
				enableActionAndHandler(actionImprimer,handlerImprimer,trouve);
				enableActionAndHandler(actionFermer,handlerFermer,true);
				enableActionAndHandler(actionSupprimer,handlerSupprimer,trouve);
				enableActionAndHandler(actionAide,handlerAide,true);
				enableActionAndHandler(actionRaffraichir,handlerRefresh,true);
				if (grille!=null)grille.setEnabled(true);
				break;
			default:
				break;
			}
	//	}
		initEtatComposant();
		if(initFocus)
			initFocusSWT(daoStandard, mapInitFocus);	
	}
	
	/**
	 * Initialisation des boutons suivant le nombre d'objet dans le modele
	 */
	protected void initEtatBoutonCommand(List model) {
		initEtatBoutonCommand(true,model);
	} 
	
	/**
	 * Initialisation des boutons suivant le nombre d'objet dans le modele
	 */
	protected void initEtatBoutonCommand(boolean initFocus) {
		initEtatBoutonCommand(initFocus,null);
	} 
	
	/**
	 * Initialisation des boutons suivant l'etat de l'objet "ibTaTable"
	 */
	protected void initEtatBoutonCommand() {
		initEtatBoutonCommand(true,null);
	} 
	
	protected boolean contientDesEnregistrement() {
		return contientDesEnregistrement(null);
	}
	
	protected boolean contientDesEnregistrement(List model) {
		boolean trouve = false;		
		if(model!=null) {
			trouve = model.size()>0;
		} else {
			trouve = daoStandard.selectCount()>0;
		}
		return trouve;
	}
	
	/**
	 * Initialisation des boutons suivant l'etat de l'objet "ibTaTable"
	 * @param initFocus - si vrai initialise le focus en fonction du mode
	 */
	protected void initEtatBoutonCommand(boolean initFocus,List model) {
		boolean trouve = contientDesEnregistrement(model);
		
		switch (daoStandard.getModeObjet().getMode()) {
		case C_MO_INSERTION:

			enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_FERMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID,false);
			enableActionAndHandler(C_COMMAND_DOCUMENT_AFFICHER_TOUS_ID,false);
			if (grille!=null)grille.setEnabled(false);
			break;
		case C_MO_EDITION:
			enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_FERMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID,true);
			enableActionAndHandler(C_COMMAND_DOCUMENT_AFFICHER_TOUS_ID,false);
			if (grille!=null)grille.setEnabled(false);
			break;
		case C_MO_CONSULTATION:
			enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID,trouve);
			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,trouve);
			enableActionAndHandler(C_COMMAND_GLOBAL_FERMER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID,trouve);
			enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID,true);
			enableActionAndHandler(C_COMMAND_DOCUMENT_AFFICHER_TOUS_ID,true);
			if (grille!=null)grille.setEnabled(true);
			break;
		default:
			break;
		}
		//	}
		initEtatComposant();
		if(initFocus)
			initFocusSWT(daoStandard, mapInitFocus);	
	}

	// boucle sur tous les champs avant l'enregistrement
	protected boolean ctrlTousLesChampsAvantEnregistrement(boolean avecRequestFocus) {
		boolean res = true;
//		try {
//		// Enregistrement des controles de sortie de champs des formulaires
//		Iterator iteChamps = mapComposantChamps.keySet().iterator();
//		while (iteChamps.hasNext()) {
//		JComponent courant = (JComponent) iteChamps.next();
//		if(avecRequestFocus==false){
//		if(!verifySansRequestFocus(courant)){
//		setFocusCourant(courant);
//		throw new ExceptLgr();
//		}
//		}else{
//		if (!(courant).getInputVerifier().verify(courant)){
//		setFocusCourant(courant);
//		throw new ExceptLgr();
//		}
//		}				
//		}
		return res;
//		} catch (ExceptLgr ex) {
//		logger.error("Erreur : CtrlTousLesChampsAvantEnregistrement", ex); //$NON-NLS-1$
//		return false;
//		}
	}


	// boucle sur tous les champs avant l'enregistrement
	protected boolean ctrlTousLesChampsAvantEnregistrement() {
		return ctrlTousLesChampsAvantEnregistrement(true);
	}

	public void retourEcran(RetourEcranEvent evt) {
		String retour=null;
		try{
		if (evt.getRetour() == null && getFocusAvantAideSWT()!=null){
				try {				
					retour=ctrlUnChampsSWT_RetourOldValue(getFocusAvantAideSWT());
					if (retour!=null && !retour.toLowerCase().equals("null"))
						((Text) getFocusAvantAideSWT()).setText(retour);
				} catch (Exception e) {
					logger.error("",e);
				}
			}
		setActiveAide(false);
		vue.getDisplay().asyncExec(new Runnable() {
			public void run() {
				if (getFocusCourantSWT()!=null)
					getFocusCourantSWT().forceFocus();
				else initFocusSWT(daoStandard, mapInitFocus);	
			}			
		}); 
		} finally {				
			if(getFocusAvantAideSWT()!=null && retour!=null)
				setFocusCourantSWT(getFocusAvantAideSWT());
		}
	}

	public void declencheAide(String message) throws Exception {
		boolean aide = getActiveAide();
		setActiveAide(true);
		setFocusAvantAideSWT(getFocusCourantSWT());
		actAide(message);
		// mettre le focus sur le champ en question
		vue.getDisplay().asyncExec(new Runnable() {
			public void run() {
				if(activeAide)
					LgrPartListener.getLgrPartListener().getLgrActivePart().setFocus();
					//LgrPartListener.getLgrPartListener().getLgrActivePart().getSite().getPage().activate(LgrPartListener.getLgrPartListener().getLgrActivePart());
			}
		});		
		setActiveAide(aide);
	}

	public void sortieChamps(SortieChampsEvent evt) throws Exception {
		initEtatComposant();
	}

	/**
	 * Ajout d'un filtre aux composants texte limitant le nombre de caracteres que l'on
	 * peut saisir a la taille du champ dans la base de donnees.
	 */
	protected void initMaxLenthTextComponent(Map<Control,String> mapComposantChamps, AbstractLgrDAO ibTaTableStandard) {
		//TODO #JPA
//		for (Control control : mapComposantChamps.keySet()) {
//			if(control instanceof Text) {
//				if(ibTaTableStandard.getFIBQuery().isOpen() && ibTaTableStandard.getFIBQuery().getColumn(mapComposantChamps.get(control)).getPrecision()>0) {
//					((Text)control).setTextLimit(ibTaTableStandard.getFIBQuery().getColumn(mapComposantChamps.get(control)).getPrecision());
//				}
//			} 
//		}
	}
	
	/**
	 * Initialise des controles qui seront effectues lors de la saisie pour les champs texte.<br>
	 * Le controle sur la longueur maximale des champs est toujours effectue. Meme s'il n'est pas dans la liste des {@link InfosVerifSaisie}
	 * @param mapComposantInfosVerif  - liste des champs sur lesquels des controles de saisie doivent etre appliques
	 * @param dao - DAO permettant un acces aux metadonnees de la base de donnees
	 * @see #initVerifyListener(Map, AbstractLgrDAO, boolean)
	 */
	protected void initVerifyListener(Map<Control,InfosVerifSaisie> mapComposantInfosVerif, AbstractLgrDAO dao) {
		initVerifyListener(mapComposantInfosVerif, dao, true);
	}
	
	/**
	 * Initialise des controles qui seront effectues lors de la saisie pour les champs texte
	 * @param mapComposantInfosVerif - liste des champs sur lesquels des controles de saisie doivent etre appliques
	 * @param dao - DAO permettant un acces aux metadonnees de la base de donnees
	 * @param verifieLongueurTexte - la longueur autorisee en saisie ne devant jamais etre superieure a la longueur maximum dans la base de donnees,
	 *  ce controle possede un parametre special plus facile d'acces
	 */
	protected void initVerifyListener(Map<Control,InfosVerifSaisie> mapComposantInfosVerif, AbstractLgrDAO dao, boolean verifieLongueurTexte) {
		try {
			LgrHibernateValidated annotation;
			//pour chaque controle
			for (Control control : mapComposantInfosVerif.keySet()) {
				if(control instanceof Text) {
					//on lit les annotations du champs pour retrouver la table et la colonne dans la base de donnees
					annotation = PropertyUtils.getReadMethod(PropertyUtils.getPropertyDescriptor(mapComposantInfosVerif.get(control).getEntity(), mapComposantInfosVerif.get(control).getField())).getAnnotation(LgrHibernateValidated.class);
					
					if(annotation!=null){
						if(mapComposantInfosVerif.get(control).getListeControles()!=null) {
							boolean numerique = false;
							for (int ctrl : mapComposantInfosVerif.get(control).getListeControles()) {
								//on ajoute les controles
								if(ctrl==InfosVerifSaisie.CTRL_QUE_DES_CHIFFRES_POSITIFS) {
									((Text)control).addVerifyListener(new QueDesChiffresPositif(annotation.champ(),annotation.table(), dao.getDbMetaData()));
									numerique = true;
								}
								if(ctrl==InfosVerifSaisie.CTRL_QUE_DES_CHIFFRES) {
									((Text)control).addVerifyListener(new QueDesChiffres(annotation.champ(),annotation.table(), dao.getDbMetaData()));
									numerique = true;
								}
								if(ctrl==InfosVerifSaisie.CTRL_QUE_DES_CHIFFRES_INTERVALE) {
									((Text)control).addVerifyListener(new QueDesChiffresIntervaleDecimale(annotation.champ(),annotation.table(), dao.getDbMetaData()));
									numerique = true;
								}
								if(((ctrl==InfosVerifSaisie.CTRL_LONGUEUR_TEXTE) || verifieLongueurTexte) && !numerique) {
									int longueurMax =0;
									ResultSet rs = dao.getDbMetaData().getColumns(null, null,annotation.table(), annotation.champ());
									if (rs.next()) longueurMax= rs.getInt(7);
									((Text)control).setTextLimit(longueurMax);
								}
							}
						}
						if( (mapComposantInfosVerif.get(control).getListeControles()==null
								|| (mapComposantInfosVerif.get(control).getListeControles()!=null && mapComposantInfosVerif.get(control).getListeControles().length == 0) 
							) && verifieLongueurTexte) {
							//il n'y a aucun controle explicitement demande, mais la longueur doit quand etre controle
							int longueurMax =0;
							ResultSet rs = dao.getDbMetaData().getColumns(null, null,annotation.table(), annotation.champ());
							if (rs.next()) longueurMax= rs.getInt(7);
							((Text)control).setTextLimit(longueurMax);
						}

					}
				}
			}
		} catch (IllegalAccessException e1) {
			logger.error("",e1);
		} catch (InvocationTargetException e1) {
			logger.error("",e1);
		} catch (NoSuchMethodException e1) {
			logger.error("",e1);
		} catch (SQLException e1) {
			logger.error("",e1);
		}
	}

	private boolean champMaj(String nomChamp,AbstractLgrDAO ibTaTableStandard){
		//TODO #JPA
		boolean res = false;
////
		res= (AbstractLgrDAO.getListeChampMaj().
				containsKey(ibTaTableStandard.getNomTable() + "." +nomChamp));
		return res;
	}

	protected void initMajTextComponent(Map<Control,String> mapComposantChamps, AbstractLgrDAO ibTaTableStandard) {
		for (final Control control : mapComposantChamps.keySet()) {
			if(control instanceof Text) {
				if(/*ibTaTableStandard.getFIBQuery().isOpen() &&*/ 
						champMaj(mapComposantChamps.get(control),ibTaTableStandard)) {
					((Text)control).addVerifyListener(new VerifyListener() {
						public void verifyText(VerifyEvent e) {
							e.text = e.text.toUpperCase();
						}
					});
				}
			} 
		}
	}	
	
	/**
	 * Active l'ecoute de tous les champs du controller qui sont relies a la bdd,
	 * si le dataset n'est pas en modification et qu'un des champs est modifie, le dataset
	 * passera automatiquement en edition.
	 * @see #desactiveModifyListener
	 */
	public void activeModifytListener() {
		activeModifytListener(mapComposantChamps,daoStandard);
	}

	/**
	 * Active l'ecoute de tous les champs du controller qui sont relies a la bdd,
	 * si le dataset n'est pas en modification et qu'un des champs est modifie, le dataset
	 * passera automatiquement en edition.
	 * @see #desactiveModifyListener
	 */
	public void activeModifytListener(Map<Control,String> mapComposantChamps, AbstractLgrDAO daoStandard) {
		for (Control control : mapComposantChamps.keySet()) {
			if(control instanceof Text) {
				((Text)control).addModifyListener(lgrModifyListener);
			} else if(control instanceof Button) {
				((Button)control).addSelectionListener(lgrModifyListener);
			} else if(control instanceof Combo) {
				((Combo)control).addSelectionListener(lgrModifyListener);
			} else if(control instanceof DateTime) {
				//((cdatetimeLgr)control).addModifyListener(lgrModifyListener);
				//((CDateTime)control).addSelectionListener(lgrModifyListener);
			}
		}
		initMaxLenthTextComponent(mapComposantChamps,daoStandard);
		initMajTextComponent(mapComposantChamps,daoStandard);
	}

	

	
	/**
	 * Active l'ecoute de tous les champs du controller qui sont relies a la bdd.
	 * @see #activeModifytListener
	 */
	public void desactiveModifyListener() {
		desactiveModifyListener(mapComposantChamps);
	}

	/**
	 * Active l'ecoute de tous les champs du controller qui sont relies a la bdd.
	 * @see #activeModifytListener
	 */
	public void desactiveModifyListener(Map<Control,String> mapComposantChamps) {
		System.out.println("desactive");
		for (Control control : mapComposantChamps.keySet()) {
			if(control instanceof Text) {
				((Text)control).removeModifyListener(lgrModifyListener);
			} else if(control instanceof Button) {
				((Button)control).removeSelectionListener(lgrModifyListener);
			} else if(control instanceof Combo) {
				((Combo)control).removeSelectionListener(lgrModifyListener);
			} else if(control instanceof DateTime) {
				//((cdatetimeLgr)control).removeModifyListener(lgrModifyListener);
				//((cdatetimeLgr)control).removeSelectionListener(lgrModifyListener);
			}
		} 
	}

	public void modifMode(){
		if (!VerrouInterface.isVerrouille() ){
			try {
				if(!daoStandard.dataSetEnModif()) {
					if(!(daoStandard.selectCount()==0)) {
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

	public class LgrModifyListener implements ModifyListener, SelectionListener {

		public void modifyText(ModifyEvent e) {
			modifMode();
		}

		public void widgetDefaultSelected(SelectionEvent e) {
			modifMode();
		}

		public void widgetSelected(SelectionEvent e) {
			modifMode();
		}

	}

//	public void dernierLigneAtteinte(LgrJdbTableEvent evt) {
//	try {
//	if(isAutoInsert()) {
//	actInserer(null);
//	}
//	} catch (Exception e) {
//	logger.error("",e);
//	}
//	}

	public Table getGrille() {
		return grille;
	}

	public void setGrille(Table grille) {
		this.grille = grille;
	}

//	public boolean isAutoInsert() {
//	return autoInsert;
//	}

//	public void setAutoInsert(boolean autoInsert) {
//	this.autoInsert = autoInsert;
//	}


	public void addDestroyListener(DestroyListener l) {
		listenerList.add(DestroyListener.class, l);
	}

	public void removeDestroyListener(DestroyListener l) {
		listenerList.remove(DestroyListener.class, l);
	}
	public void fireDestroy(DestroyEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == DestroyListener.class) {
				if (e == null)
					e = new DestroyEvent(this);
				( (DestroyListener) listeners[i + 1]).destroy(e);
			}
		}
	}

	public void shellActivated(ShellEvent e) {
//		annulerListeContext();
//		activationContext();
	}

	public void shellClosed(ShellEvent e) {
		try {
			e.doit = onClose();
//			annulerListeContext();
			System.out.println("SWTBaseControllerSWTStandard.shellClosed()");
		}
		catch (Exception ex) {
			logger.error("Erreur : shellClosed", ex);
		}
	}


	public void shellDeactivated(ShellEvent e) {
//		System.out.println("SWTBaseControllerSWTStandard.shellDeactivated()");
//		annulerListeContext();
	}

	public void shellDeiconified(ShellEvent e) {}

	public void shellIconified(ShellEvent e) {}
	public AbstractLgrDAO getDaoStandard() {
		return daoStandard;
	}
	public void setDaoStandard(AbstractLgrDAO daoStandard) {
		this.daoStandard = daoStandard;
	}
	public class DateTraverse implements TraverseListener {
		//il faut le laisser à cause des composants date qui ne fonctionnent pas 
		//comme les autres composants
		public void keyTraversed(TraverseEvent e) {
			if (e.detail == SWT.TRAVERSE_ESCAPE ) {
				try {
					e.doit = false;
					actAnnuler();
				} catch (Exception e1) {
					logger.error("", e1);
				}
			}
		}
	}
	public class Traverse implements TraverseListener {
		public void keyTraversed(TraverseEvent e) {
//			if (e.detail == SWT.TRAVERSE_ESCAPE ) {
//				try {
//					e.doit = false;
//					actAnnuler();
//				} catch (Exception e1) {
//					logger.error("", e1);
//				}
//			}
		}
	}	
	
	public void afficheDecoratedField(final Control control,String message){
		afficheDecoratedField(control,message,mapComposantChamps);
	}
	public void afficheDecoratedField(final Control control,String message,Map<Control,String>mapComposantChamps){
		
		if (control!=null){
			registry.registerFieldDecoration("error.field."+mapComposantChamps.get(control), message, registry.getFieldDecoration(FieldDecorationRegistry.DEC_ERROR).getImage());

			mapComposantDecoratedField.get(control).addFieldDecoration(
					registry.getFieldDecoration("error.field."+mapComposantChamps.get(control)),
					SWT.TOP|SWT.RIGHT,
					false);
			mapComposantDecoratedField.get(control).getLayoutControl().setLayoutData(
					new GridData(
							100 + registry.getMaximumDecorationWidth(),
							SWT.DEFAULT
					)
			);
			if (!getActiveAide()){
				mapComposantDecoratedField.get(control).showHoverText(message);
				mapComposantDecoratedField.get(control).getControl().setToolTipText(message);
				vue.getShell().getDisplay().timerExec (4000, new Runnable () {
					public void run() {
						if(!mapComposantDecoratedField.get(control).getControl().isDisposed())
							mapComposantDecoratedField.get(control).hideHover();
					}

				});
			}

		}
	}

	public void hideDecoratedField(Control control){
		hideDecoratedField(control,mapComposantChamps);
	}
	
	public void hideDecoratedField(Control control,Map<Control,String>mapComposantChamps){
		if (control!=null){	
			if(registry.getFieldDecoration("error.field."+mapComposantChamps.get(control))!=null){
				mapComposantDecoratedField.get(control).hideDecoration(registry.getFieldDecoration("error.field."+mapComposantChamps.get(control)));
				mapComposantDecoratedField.get(control).getControl().setToolTipText("");
			}
		}
	}

	public void hideDecoratedFields(){
		if (mapComposantChamps!=null && mapComposantDecoratedField!=null)
			if(!mapComposantDecoratedField.isEmpty()) {
				for (Control control : mapComposantDecoratedField.keySet()) {			
					if(registry.getFieldDecoration("error.field."+mapComposantChamps.get(control))!=null){
						mapComposantDecoratedField.get(control).hideDecoration(registry.getFieldDecoration("error.field."+mapComposantChamps.get(control)));
						mapComposantDecoratedField.get(control).getControl().setToolTipText("");
				}
				}
			}
	}
	
	public void addFocusCommand(final String focusId, Control control, String commandId, IHandler commandHandler) {
		List<Control> controls = new ArrayList<Control>(1);
		Map<String,IHandler> commandIdsAndHandler = new HashMap<String, IHandler>(1);
		
		controls.add(control);
		commandIdsAndHandler.put(commandId, commandHandler);
		initFocusCommand(focusId,controls,commandIdsAndHandler);
	}
	
	public void initFocusCommand(final String focusId, List<Control> controls, Map<String,org.eclipse.core.commands.IHandler> commandIdsAndHandler/*, WorkbenchPart part*/) {
		if(handlerService == null)
			handlerService = (IHandlerService)PlatformUI.getWorkbench().getService(IHandlerService.class);
		
		IFocusService focusService = (IFocusService) PlatformUI.getWorkbench().getService(IFocusService.class);
		for (Control control : controls) {
			focusService.addFocusTracker(control, focusId);
		}
		
		Expression exp = new FocusControlExpression(focusId);
		
		for (String commandId : commandIdsAndHandler.keySet()) {
			handlerService.activateHandler(commandId, commandIdsAndHandler.get(commandId), exp);
		}


	}

	public void initCommands(){
		if(handlerService == null)
			handlerService = (IHandlerService)PlatformUI.getWorkbench().getService(IHandlerService.class);
		if(contextService == null)
			contextService = (IContextService) PlatformUI.getWorkbench().getService(IContextService.class);
		contextService.registerShell(vue.getShell(),IContextService.TYPE_DIALOG);
		activeShellExpression = new ActiveShellExpression(vue.getShell());
		
//		getListeContext().get(0).clearResult();
//		getListeContext().get(0).evaluate((IEvaluationContext) PlatformUI.getWorkbench().getService(IEvaluationContext.class));

		handlerService.activateHandler(C_COMMAND_GLOBAL_AIDE_ID, handlerAide,activeShellExpression);
		handlerService.activateHandler(C_COMMAND_GLOBAL_ANNULER_ID, handlerAnnuler, activeShellExpression);
		handlerService.activateHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID, handlerEnregistrer, activeShellExpression);
		handlerService.activateHandler(C_COMMAND_GLOBAL_FERMER_ID, handlerFermer, activeShellExpression);
		handlerService.activateHandler(C_COMMAND_GLOBAL_IMPRIMER_ID, handlerImprimer, activeShellExpression);
		handlerService.activateHandler(C_COMMAND_GLOBAL_INSERER_ID, handlerInserer, activeShellExpression);
		handlerService.activateHandler(C_COMMAND_GLOBAL_MODIFIER_ID, handlerModifier, activeShellExpression);
		handlerService.activateHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID, handlerSupprimer, activeShellExpression);	
		handlerService.activateHandler(C_COMMAND_GLOBAL_REFRESH_ID, handlerRefresh, activeShellExpression);
	}
	
//	public void initWorkenchPartCommands(WorkbenchPart part){
////		if(handlerService == null)
////			handlerService = (IHandlerService) part.getSite().getService(IHandlerService.class);
////		if(contextService == null)
////			contextService = (IContextService) part.getSite().getService(IContextService.class);
//		if(handlerService == null)
//			handlerService = (IHandlerService)PlatformUI.getWorkbench().getService(IHandlerService.class);
//		if(contextService == null)
//			contextService = (IContextService) PlatformUI.getWorkbench().getService(IContextService.class);
//		activePartExpression = new ActivePartExpression(part);
//		//activePartExpression = new LGRPartExpression(part,listeComposantFocusable);
//		handlerService.activateHandler(C_COMMAND_GLOBAL_AIDE_ID, handlerAide,activePartExpression);
//		handlerService.activateHandler(C_COMMAND_GLOBAL_ANNULER_ID, handlerAnnuler,activePartExpression);
//		handlerService.activateHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID, handlerEnregistrer,activePartExpression);
//		handlerService.activateHandler(C_COMMAND_GLOBAL_FERMER_ID, handlerFermer,activePartExpression);
//		handlerService.activateHandler(C_COMMAND_GLOBAL_IMPRIMER_ID, handlerImprimer,activePartExpression);
//		handlerService.activateHandler(C_COMMAND_GLOBAL_INSERER_ID, handlerInserer,activePartExpression);
//		handlerService.activateHandler(C_COMMAND_GLOBAL_MODIFIER_ID, handlerModifier,activePartExpression);
//		handlerService.activateHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID, handlerSupprimer,activePartExpression);	
//		handlerService.activateHandler(C_COMMAND_GLOBAL_REFRESH_ID, handlerRefresh,activePartExpression);
//	}

	
	public void ctrlTousLesChampsAvantEnregistrementSWT() throws Exception {
		ctrlTousLesChampsAvantEnregistrementSWT(dbcStandard);
	}
	
	public void ctrlTousLesChampsAvantEnregistrementSWT(DataBindingContext dbcStandard) throws Exception{
		ctrlTousLesChampsAvantEnregistrementSWT(dbcStandard,mapComposantChamps);
	}
	
	@SuppressWarnings("deprecation")
	public void ctrlTousLesChampsAvantEnregistrementSWT(DataBindingContext dbcStandard,Map<Control,String> mapComposantChamps) throws Exception{
		for (final Object binding : dbcStandard.getValidationStatusMap().keySet()) {
			((Binding) binding).updateTargetToModel();
			if (((IStatus) dbcStandard.getValidationStatusMap().get(binding)).getSeverity() == IStatus.ERROR) {
				// mettre le focus sur le champ en question
					vue.getDisplay().asyncExec(new Runnable() {
						public void run() {
							setFocusCourantSWT(((Control) ((ISWTObservable) ((Binding) binding).getTarget()).getWidget()));
							getFocusCourantSWT().setFocus();
							if(activeAide)
								LgrPartListener.getLgrPartListener().getLgrActivePart().setFocus();
								//LgrPartListener.getLgrPartListener().getLgrActivePart().getSite().getPage().activate(LgrPartListener.getLgrPartListener().getLgrActivePart());
						}
					});	
					IStatus[] listChild=((IStatus) dbcStandard.getValidationStatusMap().get(binding)).getChildren();
					String message1=LibConversion.StringNotNull(listChild[0].getChildren()[0].getMessage());
				logger.error("Erreur de validation sur le champ : "+mapComposantChamps.get(((Control) ((ISWTObservable) ((Binding) binding).getTarget()).getWidget())));
				
				afficheDecoratedField(((Control) ((ISWTObservable) ((Binding) binding)
						.getTarget()).getWidget()),message1 ,mapComposantChamps);
				throw new Exception();
			}else{
				try {
					hideDecoratedField(((Control) ((ISWTObservable) ((Binding) binding)
							.getTarget()).getWidget()),mapComposantChamps);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}		
	}
	public void remetTousLesChampsApresAnnulationSWT(DataBindingContext dbcStandard) throws Exception{
		for (final Object binding : dbcStandard.getValidationStatusMap().keySet()) {
			((Binding) binding).updateModelToTarget();
			if (((IStatus) dbcStandard.getValidationStatusMap().get(binding)).getSeverity() == IStatus.ERROR) {
				// mettre le focus sur le champ en question
					vue.getDisplay().asyncExec(new Runnable() {
						public void run() {
							setFocusCourantSWT(((Control) ((ISWTObservable) ((Binding) binding).getTarget()).getWidget()));
							getFocusCourantSWT().setFocus();
							if(activeAide)
								LgrPartListener.getLgrPartListener().getLgrActivePart().setFocus();
								//LgrPartListener.getLgrPartListener().getLgrActivePart().getSite().getPage().activate(LgrPartListener.getLgrPartListener().getLgrActivePart());
						}
					});		
				throw new Exception(LibConversion.StringNotNull(((IStatus) dbcStandard.getValidationStatusMap().get(binding)).getMessage()));
			}
		}		
	}

	@SuppressWarnings("deprecation")
	public void ctrlUnChampsSWT(Control champ) throws Exception{
		for (final Object binding : dbcStandard.getValidationStatusMap()
				.keySet()) {
			if(((ISWTObservable) ((Binding) binding).getTarget()).getWidget().equals(champ)){
				((Binding) binding).updateTargetToModel();
//				TextObservableValue target =(TextObservableValue) ((Binding) binding).getTarget();
//				BeanObservableValueDecorator model =(BeanObservableValueDecorator) ((Binding) binding).getModel();
//				System.err.println("target : "+target.getValue());
//				System.err.println("model : "+model.getValue());
//				System.err.println("getTarget : "+((Binding) binding).getModel());
				if (((IStatus) dbcStandard.getValidationStatusMap().get(binding)).getSeverity() == IStatus.ERROR) {
					throw new Exception("Erreur de saisie");
				}
			}
		}		
	}
	
	/**
	 * 
	 * @param champ le champ concerné par la vérification
	 * @return retourne l'ancienne valeur
	 * @throws Exception
	 */
	public String ctrlUnChampsSWT_RetourOldValue(Control champ){
		String retour=null;
		for (final Object binding : dbcStandard.getValidationStatusMap()
				.keySet()) {
			if(((ISWTObservable) ((Binding) binding)
								.getTarget()).getWidget().equals(champ)){
				((Binding) binding).updateTargetToModel();

				if (((IStatus) dbcStandard.getValidationStatusMap().get(binding))
						.getSeverity() == IStatus.ERROR) {
					if(((BeanObservableValueDecorator) ((Binding) binding).getModel()).getValue()!=null)
						retour= String.valueOf(((BeanObservableValueDecorator) ((Binding) binding).getModel()).getValue());
				}
			}
		}
		return retour;
	}
	
	
	public class MapChangeListener implements IMapChangeListener {
		private DataBindingContext dbcMapChangeListener;
		private Map<Control,String>mapComposantChampsChangeListener;
		
		
		public  MapChangeListener(DataBindingContext dbcMapChangeListener,Map<Control,String>mapComposantChamps){
			this.dbcMapChangeListener = dbcMapChangeListener;
			this.mapComposantChampsChangeListener = mapComposantChamps;
		}
		public  MapChangeListener(){
			
		}
				
		public void handleMapChange(MapChangeEvent event) {
			if(this.dbcMapChangeListener==null)this.dbcMapChangeListener=dbcStandard;
			if(this.mapComposantChampsChangeListener==null)this.mapComposantChampsChangeListener=mapComposantChamps;
			IStatus status = null;
			MultiStatus multiStatus = null;
			//declencher sortie champs dans controller pour mettre a jour l'interface si besoin
			//sortieChamps();
			for (final Object binding : event.diff.getChangedKeys()) {
				// if(
				// ((IStatus)dbc.getValidationStatusMap().get(binding)).getSeverity()
				// == IStatus.ERROR ) {

				
				
				status = null;
				multiStatus = null;
				if (((IStatus) dbcMapChangeListener.getValidationStatusMap().get(binding))!=null ){
					if (((IStatus) dbcMapChangeListener.getValidationStatusMap().get(binding)).isMultiStatus()
							&& ((IStatus) dbcMapChangeListener.getValidationStatusMap().get(binding)).getChildren().length > 0) {
						status = ((IStatus) dbcMapChangeListener.getValidationStatusMap().get(binding)).getChildren()[0];
						multiStatus = ((MultiStatus) dbcMapChangeListener.getValidationStatusMap().get(binding));
					} else
						status = ((IStatus) dbcMapChangeListener.getValidationStatusMap().get(binding));

					if (status.getChildren().length>0 && status.getChildren()[0].getSeverity() == IStatus.ERROR) {
//							afficheDecoratedField(((Control) ((ISWTObservable) ((Binding) binding)
//								.getTarget()).getWidget()), status.getChildren()[0].getMessage());
						try {
							if (multiStatus != null
									&& multiStatus.getMessage().equals(JPACtrlInterface.C_CONTROLE_INTERFACE)) {
								if (getFocusCourantSWT()
										.equals(((Control) ((ISWTObservable) ((Binding) binding)
												.getTarget()).getWidget()))) {
//									System.out.println("declencheAide "
//									+ getFocusCourantSWT()
//									.getToolTipText());
									if (!getActiveAide()&& aideDisponible()){
										if ( getFocusCourantSWT()!= null && getFocusCourantSWT() instanceof Text)
											declencheAide("La valeur "+((Text)getFocusCourantSWT()).getText()+ " n'est pas correcte. Vous devez en selectionner une autre");
										else declencheAide(null);
											setActiveAide(true);
											break;
									}
								}
							}
						} catch (Exception e) {
							logger.error("", e);
						}
						afficheDecoratedField(((Control) ((ISWTObservable) ((Binding) binding)
								.getTarget()).getWidget()), status.getChildren()[0].getMessage(),mapComposantChampsChangeListener);
					} else {
						try {
							hideDecoratedField(((Control) ((ISWTObservable) ((Binding) binding)
									.getTarget()).getWidget()),mapComposantChampsChangeListener);
						} catch (Exception e) {
							// TODO: handle exception
						}
					}

				}
			}
		}

		public DataBindingContext getDbcMapChangeListener() {
			return dbcMapChangeListener;
		}

		public void setDbcMapChangeListener(DataBindingContext dbcMapChangeListener) {
			this.dbcMapChangeListener = dbcMapChangeListener;
		}
		public Map<Control, String> getMapComposantChampsChangeListener() {
			return mapComposantChampsChangeListener;
		}
		public void setMapComposantChampsChangeListener(Map<Control, String> mapComposantChamps) {
			this.mapComposantChampsChangeListener = mapComposantChamps;
		}
	}

	public DataBindingContext getDbcStandard() {
		return dbcStandard;
	}
	
	public void setDbcStandard(DataBindingContext dbcStandard) {
		this.dbcStandard = dbcStandard;
	}
	
	public LgrTableViewer getTableViewerStandard() {
		return tableViewerStandard;
	}
	
	public void setTableViewerStandard(LgrTableViewer tableViewerStandard) {
		this.tableViewerStandard = tableViewerStandard;
//		grille.setItemCount(10);
//		grille.clearAll();
//		grille.addListener(SWT.SetData, new Listener() {
//			public void handleEvent(Event event) {
//				rajoutItem();
//				}
//	      });		
	}
	
	public LgrTreeViewer getTreeViewerStandard() {
		return treeViewerStandard;
	}
	
	public void setTreeViewerStandard(LgrTreeViewer treeViewerStandard) {
		this.treeViewerStandard = treeViewerStandard;		
	}

	private void rajoutItem(){
		int reste = ((WritableList)getTableViewerStandard().getInput()).size() -grille.getItemCount();
		if (reste>10)reste = 10;
		grille.setItemCount(grille.getItemCount()+reste);
		grille.clearAll();		
	}
	
	public void Enregistrer(){
		try {
			actEnregistrer();
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	public Boolean getActiveAide() {
		return activeAide;
	}
	
	public void setActiveAide(Boolean activeAide) {
		this.activeAide = activeAide;
	}
	
	public void addExpandBarItem(ExpandBar expandBar, Composite composite, String titre, Image image) {
		addExpandBarItem(expandBar, composite, titre, image,SWT.DEFAULT, SWT.DEFAULT);
	}
	
	public void addExpandBarItem(ExpandBar expandBar, Composite composite, String titre, Image image,int w,int h) {
		changeCouleur(composite);
	    ExpandItem item = new ExpandItem (expandBar, SWT.NONE);
		item.setText(titre);
		item.setHeight(composite.computeSize(w, h).y);
		item.setControl(composite);
		item.setImage(image);
		
		if(mapExpandBar==null)
			mapExpandBar = new HashMap<ExpandBar, Map<Composite,ExpandItem>>();
		if(!mapExpandBar.keySet().contains(expandBar)) {
			mapExpandBar.put(expandBar, new HashMap<Composite,ExpandItem>());
		}
		mapExpandBar.get(expandBar).put(composite, item);
	}
	
	public ExpandItem findExpandIem(ExpandBar expandBar, Composite composite) {
		return mapExpandBar.get(expandBar).get(composite);
	}
	
	public void destroy(DestroyEvent evt) {
		activeAide =null;
		contextService.dispose();
		registry  = null;
		activeShellExpression  = null;
		activePartExpression  = null;		
//		ibTaTableStandard.destroy();
		grille.clearAll();
		grille.dispose() ;
		tableViewerStandard.destroy();
		dbcStandard.dispose();
		listenerList = null;
		
		actionInserer = null;
		actionEnregistrer = null;
		actionModifier = null;
		actionSupprimer = null;
		actionFermer = null;
		actionAnnuler = null;
		actionImprimer = null;
		actionAide = null;
		actionRaffraichir = null;

		handlerService.dispose();
		handlerInserer.dispose();
		handlerEnregistrer.dispose();
		handlerModifier.dispose();
		handlerSupprimer.dispose();
		handlerFermer.dispose();
		handlerAnnuler.dispose();
		handlerImprimer.dispose();
		handlerAide.dispose();
		handlerRefresh.dispose();
		
		lgrModifyListener = null;
		queDesChiffres = null;
		
		mapComposantChamps.clear();
		mapComposantDecoratedField.clear();
		listeComposantFocusable.clear();
		mapInitFocus.clear();
		mapActions.clear();
		listeBindSpec.clear();
		
		mapComposantChamps = null;
		mapComposantDecoratedField = null;
		listeComposantFocusable = null;
		mapInitFocus = null;
		mapActions = null;		
		listeBindSpec=null;
	}
	public IWorkbenchPart3 getWorkbenchPart() {
		return workbenchPart;
	}
	public void setWorkbenchPart(IWorkbenchPart3 workbenchPart) {
		this.workbenchPart = workbenchPart;
	}
	
	public IStatusLineManager findStatusLineManager() {
		if(getWorkbenchPart() instanceof IEditorPart)
			return ((IEditorPart)getWorkbenchPart()).getEditorSite().getActionBars().getStatusLineManager();
		else
			return null;
	}
	
	public void declencheCommandeController(DeclencheCommandeControllerEvent evt) {
		try {
			sourceDeclencheCommandeController = evt.getSource();
			if(evt.getCommande().equals(C_COMMAND_GLOBAL_AIDE_ID)) {
				actAide();
			} else if(evt.getCommande().equals(C_COMMAND_GLOBAL_ANNULER_ID)) {
				actAnnuler();
			} else if(evt.getCommande().equals(C_COMMAND_GLOBAL_ENREGISTRER_ID)) {
				actEnregistrer();
			} else if(evt.getCommande().equals(C_COMMAND_GLOBAL_FERMER_ID)) {
				actFermer();
			} else if(evt.getCommande().equals(C_COMMAND_GLOBAL_IMPRIMER_ID)) {
				actImprimer();
			} else if(evt.getCommande().equals(C_COMMAND_GLOBAL_INSERER_ID)) {
				actInserer();
			} else if(evt.getCommande().equals(C_COMMAND_GLOBAL_MODIFIER_ID)) {
				actModifier();
			} else if(evt.getCommande().equals(C_COMMAND_GLOBAL_SUPPRIMER_ID)) {
				actSupprimer();
			} else if(evt.getCommande().equals(C_COMMAND_GLOBAL_REFRESH_ID)) {
				actRefresh();
			} else if(evt.getCommande().equals(C_COMMAND_GLOBAL_PRECEDENT_ID)) {
				actPrecedent();
			} else if(evt.getCommande().equals(C_COMMAND_GLOBAL_SUIVANT_ID)) {
				actSuivant();
			}
		} catch (Exception e) {
			logger.error("",e);
		} finally {
			sourceDeclencheCommandeController = null;
		}
	}

	private class HandlerCreateDoc extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				actCreateDoc();
			} catch (Exception e) {
				logger.error("", e);
			}
			return event;
		}
	}
	private class HandlerCreateModele extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				actCreateModele();
			} catch (Exception e) {
				logger.error("", e);
			}
			return event;
		}
	}
	private class HandlerAfficherTous extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				actAfficherTous();
			} catch (Exception e) {
				logger.error("", e);
			}
			return event;
		}
	}
	
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public boolean changementPageValide(){
		return true;
	};
	
	private class LgrFocusListenerCdatetime implements FocusListener {


		public void focusGained(FocusEvent e) {
			((DateTime) e.getSource()).addSelectionListener(lgrModifyListener);
			
		}

		public void focusLost(FocusEvent e) {
			((DateTime) e.getSource())
			.removeSelectionListener(lgrModifyListener);
			
		}

	}

	public boolean isSilencieu() {
		return silencieu;
	}
	public void setSilencieu(boolean silencieu) {
		this.silencieu = silencieu;
	}
	public boolean isPrepareEnregistrement() {
		return enregistreToutEnCours;
	}
	public void setPrepareEnregistrement(boolean prepareEnregistrement) {
		this.enregistreToutEnCours = prepareEnregistrement;
	}
	public Object getSourceDeclencheCommandeController() {
		return sourceDeclencheCommandeController;
	}
	public boolean isAnnuleToutEnCours() {
		return annuleToutEnCours;
	}
	public void setAnnuleToutEnCours(boolean annuleToutEnCours) {
		this.annuleToutEnCours = annuleToutEnCours;
	}
	public EntityManager getEm() {
		if(em==null && getDaoStandard()!=null)
			em=getDaoStandard().getEntityManager();
		return em;
	}
	public void setEm(EntityManager em) {
		this.em = em;
	}
	public void initCursor(int etat){
		if(PlatformUI.getWorkbench().getActiveWorkbenchWindow()!=null) {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().setCursor(Display.getCurrent().getSystemCursor(etat));
		}
	}
	public boolean focusDansEcran(){
		for (Control c : listeComposantFocusable) {
			if(getFocusCourantSWT().equals(c))return true;
		}
		return false;
	}
	
	public void ouvreDocument(String valeurIdentifiant, String idEditor) {
		ouvreDocument(valeurIdentifiant,idEditor,true);
	}
	
	public void ouvreDocument(String valeurIdentifiant, String idEditor,Boolean message) {
		AbstractLgrMultiPageEditor.ouvreFiche(null,valeurIdentifiant, idEditor,null,message);
//		IEditorPart editor = AbstractLgrMultiPageEditor
//				.chercheEditeurDocumentOuvert(idEditor);
//		if (editor == null) {
//			try {
//				IEditorPart e = PlatformUI.getWorkbench()
//						.getActiveWorkbenchWindow().getActivePage().openEditor(
//								new IEditorInput() {
//
//									public boolean exists() {
//										return false;
//									}
//
//									public ImageDescriptor getImageDescriptor() {
//										return null;
//									}
//
//									public String getName() {
//										return "";
//									}
//
//									public IPersistableElement getPersistable() {
//										return null;
//									}
//
//									public String getToolTipText() {
//										return "";
//									}
//
//									public Object getAdapter(Class adapter) {
//										return null;
//									}
//								}, idEditor);
//
//				LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
//
//				ParamAffiche paramAffiche = new ParamAffiche();
//				if (e instanceof AbstractLgrMultiPageEditor) {
//					paramAffiche.setCodeDocument(valeurIdentifiant);
//					((AbstractLgrMultiPageEditor) e).findMasterController()
//							.configPanel(paramAffiche);
//				} else {
//					((JPALgrEditorPart) e).setPanel(((JPALgrEditorPart) e)
//							.getController().getVue());
//					paramAffiche.setCodeDocument(valeurIdentifiant);
//					((JPALgrEditorPart) e).getController().configPanel(
//							paramAffiche);
//				}
//
//			} catch (PartInitException e1) {
//				logger.error("", e1);
//			}
//		} else {
//			Boolean continuer=true;
//			if(message)
//				continuer=MessageDialog.openQuestion(vue.getShell(),
//					"Affichage document",
//					"Voulez-vous abandonner le document en cours de saisie ?");
//				if(continuer)	{
//				ParamAffiche paramAffiche = new ParamAffiche();
//				if (editor instanceof AbstractLgrMultiPageEditor) {
//					paramAffiche.setCodeDocument(valeurIdentifiant);
//					((AbstractLgrMultiPageEditor) editor)
//							.findMasterController().configPanel(paramAffiche);
//				}
//				PlatformUI.getWorkbench().getActiveWorkbenchWindow()
//				.getActivePage().activate(editor);
//				}
//		}
	}

	

	public Boolean containsCodeDocument(IDocumentTiers taDocument){
		for (IDocumentTiers  document : listeDocument) {
			if(document.getCodeDocument().equals(taDocument.getCodeDocument()))
				return true;
		}
		return false;
	}
	
	public Boolean addCodeDocument(IDocumentTiers taDocument){
		if(listeDocument!=null){
			if(!containsCodeDocument(taDocument)){
				listeDocument.add(taDocument);
				return true;
			}
		}
		return false;
	}
	
	public Boolean remplaceCodeDocument( IDocumentTiers newTaDocument){
		if(listeDocument!=null)
			for (IDocumentTiers  document : listeDocument) {
				if(document.getCodeDocument().equals(newTaDocument.getCodeDocument())){
					document=newTaDocument;
					return true;
				}
			}
		return addCodeDocument(newTaDocument);
	}
	
	public Boolean removeCodeDocument( IDocumentTiers taDocument){
		if(listeDocument!=null)
			for (IDocumentTiers  document : listeDocument) {
				if(document.getCodeDocument().equals(taDocument.getCodeDocument())){
					listeDocument.remove(document);
					return true;
				}
			}
		return false;
	}

	/**
	 * Récupère une liste de champsObjetCompare pour une section/écran donnée.
	 * @param section - nom de la classe appelante (controller)
	 * @param fileName - fichier properties
	 * @return - liste de champsObjetCompare
	 * @throws Exception - si fileName n'xiste pas.
	 * @see #setListeChampGrille
	 */
	public String[] setListeChampGrille(String section, String fileName) throws Exception {
		setListeChampGrille(fileName);
		List<String> res =new ArrayList<String>();
		org.apache.commons.configuration.SubsetConfiguration propertie = null;
		propertie = new org.apache.commons.configuration.SubsetConfiguration(getListeChampGrille(),section,".");
		if (!propertie.isEmpty()){
			for (Iterator iter = propertie.getKeys(); iter.hasNext();) {
				res.add(iter.next().toString());
			}
		}
		String[] retour = new String[res.size()];
		for (int i = 0; i < res.size(); i++) {
			retour[i]=res.get(i).toString();
		}
		return retour;
	}
	
	/**
	 * Récupére le contenu de la liste <code>listeChampGrille</code> à partir du fichier properties
	 * <br>
	 * Format des lignes du fichier properties : NomDelaClasse.NOM_CHAMPS=TITRE CHAMPS
	 * <br>
	 * Exemple de fichier properties :
	 * <p>
	 * <code>SWTPaTiersController.COMPTE=COMPTE
	 * SWTPaTiersController.CODE_COMPTA=CODE COMPTA
     * SWTPaTiersController.CODE_TIERS=CODE TIERS 
     * SWTPaTiersController.NOM_TIERS=NOM TIERS
     * </code>
	 * </p>
	 * @param fileName - nom du fichier properties
	 */
	public static void setListeChampGrille(String fileName){
		try {
			if(listeChampGrille.isEmpty()){
				if (!new File(fileName).exists()) {
					MessageDialog.openError(
							PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
							"ERREUR",
							"Le fichier .properties "+ fileName + " est inexistant");
				} else {
					FileInputStream file = new FileInputStream(fileName);
					listeChampGrille.load(file);
					file.close();
				}
			}
		}
		catch (Exception e) {
			logger.error("Erreur : setListeChampGrille", e);
		}
	}
	
	/**
	 * @return liste des champsObjetCompare/colonnes à afficher pour la grille ainsi que leur titre.
	 */
	public static PropertiesConfiguration getListeChampGrille() {
		return listeChampGrille;
	}
	
	/**
	 * Récupère une liste de titresColonnes pour une section/écran donnée.
	 * @param section - nom de la classe appelante (controller)
	 * @param fileName - fichier properties
	 * @return - liste de champsObjetCompare
	 * @throws Exception - si fileName n'xiste pas.
	 * @see #setListeChampGrille
	 */
	public String[] setListeTitreChampGrille( String section,String fileName) throws Exception{
		setListeChampGrille(fileName);
		List<String> res =new ArrayList<String>();
		org.apache.commons.configuration.SubsetConfiguration propertie = null;
		propertie = new org.apache.commons.configuration.SubsetConfiguration(getListeChampGrille(),section,".");
		if (!propertie.isEmpty()){			
			for (Iterator iter = propertie.getKeys(); iter.hasNext();) {
				res.add(propertie.getString(iter.next().toString()));
			}
		}
		String[] retour = new String[res.size()];
		for (int i = 0; i < res.size(); i++) {
			retour[i]=res.get(i).toString().split(";")[0];
		}
		return retour;
	}

	/**
	 * Récupère une liste de titresColonnes pour une section/écran donnée.
	 * @param section - nom de la classe appelante (controller)
	 * @param fileName - fichier properties
	 * @return - liste de champsObjetCompare
	 * @throws Exception - si fileName n'xiste pas.
	 * @see #setListeChampGrille
	 */
	public String[] setListeTailleChampGrille( String section,String fileName) throws Exception{
		setListeChampGrille(fileName);
		List<String> res =new ArrayList<String>();
		org.apache.commons.configuration.SubsetConfiguration propertie = null;
		propertie = new org.apache.commons.configuration.SubsetConfiguration(getListeChampGrille(),section,".");
		if (!propertie.isEmpty()){			
			for (Iterator iter = propertie.getKeys(); iter.hasNext();) {
				res.add(propertie.getString(iter.next().toString()));
			}
		}
		String[] retour = new String[res.size()];
		for (int i = 0; i < res.size(); i++) {
			retour[i]=res.get(i).toString().split(";")[1];
		}
		return retour;
	}
	
	public void actAfficherTous() throws Exception{
		
	}

	public Boolean getEnregistreEtFerme() {
		return EnregistreEtFerme;
	}

	public void setEnregistreEtFerme(Boolean enregistreEtFerme) {
		EnregistreEtFerme = enregistreEtFerme;
	}
	public void setEnregistreEtFerme(boolean enregistreEtFerme) {
		EnregistreEtFerme = enregistreEtFerme;
	}
	public boolean isControleForce() {
		return ControleForce;
	}
	public void setControleForce(boolean controleForce) {
		ControleForce = controleForce;
	}
	
	public Date dateDansExercice(Date valeur) throws Exception {
		
		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(new TaInfoEntrepriseDAO().getEntityManager());
		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
		// si date inférieur à dateDeb dossier
		if (LibDate.compareTo(valeur, taInfoEntreprise.getDatedebInfoEntreprise()) == -1) {
			return taInfoEntreprise.getDatedebInfoEntreprise();
		} else
			// si date supérieur à dateFin dossier
			if (LibDate.compareTo(valeur, taInfoEntreprise.getDatefinInfoEntreprise()) == 1) {
				return taInfoEntreprise.getDatefinInfoEntreprise();
			}
		//return new Date();
		return valeur;
	}

	public IEditorPart ouvreFiche(String valeurIdentifiant, String idEditor,ParamAffiche paramAfficheIn,Boolean message) {
		return AbstractLgrMultiPageEditor.ouvreFiche(valeurIdentifiant, null, idEditor,paramAfficheIn,message);
//		IEditorPart editor = AbstractLgrMultiPageEditor
//				.chercheEditeurDocumentOuvert(idEditor);
//		ParamAffiche paramAffiche;
//		if (editor == null) {
//			try {
//				IEditorPart e = PlatformUI.getWorkbench()
//						.getActiveWorkbenchWindow().getActivePage().openEditor(
//								new IEditorInput() {
//
//									public boolean exists() {
//										return false;
//									}
//
//									public ImageDescriptor getImageDescriptor() {
//										return null;
//									}
//
//									public String getName() {
//										return "";
//									}
//
//									public IPersistableElement getPersistable() {
//										return null;
//									}
//
//									public String getToolTipText() {
//										return "";
//									}
//
//									public Object getAdapter(Class adapter) {
//										return null;
//									}
//								}, idEditor);
//
//				LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
//				if(paramAfficheIn!=null)paramAffiche=paramAfficheIn;
//				else paramAffiche = new ParamAffiche();
//				if (e instanceof AbstractLgrMultiPageEditor) {
//					paramAffiche.setIdFiche(valeurIdentifiant);
//					((AbstractLgrMultiPageEditor) e).findMasterController()
//							.configPanel(paramAffiche);
//				} else {
//					((JPALgrEditorPart) e).setPanel(((JPALgrEditorPart) e)
//							.getController().getVue());
//					paramAffiche.setIdFiche(valeurIdentifiant);
//					((JPALgrEditorPart) e).getController().configPanel(
//							paramAffiche);
//				}
//				return e;
//			} catch (PartInitException e1) {
//				logger.error("", e1);
//				
//			}
//			
//		} else {
//			Boolean continuer=true;
//			if(message)
//				continuer=MessageDialog.openQuestion(vue.getShell(),
//					"Affichage document",
//					"Voulez-vous abandonner le document en cours de saisie ?");
//				if(continuer)	{
//				if(paramAfficheIn!=null)paramAffiche=paramAfficheIn;
//					else paramAffiche = new ParamAffiche();
//				if (editor instanceof AbstractLgrMultiPageEditor) {
//					paramAffiche.setIdFiche(valeurIdentifiant);
//					((AbstractLgrMultiPageEditor) editor)
//							.findMasterController().configPanel(paramAffiche);
//				}
//				PlatformUI.getWorkbench().getActiveWorkbenchWindow()
//				.getActivePage().activate(editor);
//				}
//				return editor;
//		}
//		return null;
	}

	private Field retourneField(Class classObjet, Control c  ,Map<Control,String> mapComposantChamps){
		Field field=null;
		try {
			field = classObjet.getDeclaredField(mapComposantChamps.get(c));
		} catch (NoSuchFieldException e) {}
		Class objet =classObjet.getSuperclass();
		while(objet!=null){
			if(field==null)field=retourneField(objet,c,mapComposantChamps);
			objet =objet.getSuperclass();
		}
		return field;
	}
}

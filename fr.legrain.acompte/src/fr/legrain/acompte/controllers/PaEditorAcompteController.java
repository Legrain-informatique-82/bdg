package fr.legrain.acompte.controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
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
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.IContentProposalListener;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IElementComparer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;

import com.borland.dx.dataset.DataSetException;

import fr.legrain.acompte.pluginAcompte;
import fr.legrain.acompte.divers.ModelRAcompte;
import fr.legrain.acompte.divers.ParamAfficheLAcompte;
import fr.legrain.acompte.ecrans.PaEditorAcompteSWT;
import fr.legrain.acompte.editor.EditorAcompte;
import fr.legrain.acompte.editor.EditorInputAcompte;
import fr.legrain.acompte.preferences.PreferenceInitializer;
import fr.legrain.document.DocumentPlugin;
import fr.legrain.document.controller.MessagesEcran;
import fr.legrain.document.divers.TypeDoc;
import fr.legrain.document.ecran.PaIdentiteTiers;
import fr.legrain.documents.dao.IDocumentTiersDAO;
import fr.legrain.documents.dao.TaAcompte;
import fr.legrain.documents.dao.TaAcompteDAO;
import fr.legrain.documents.dao.TaInfosAcompte;
import fr.legrain.documents.dao.TaInfosAcompteDAO;
import fr.legrain.documents.dao.TaLAcompte;
import fr.legrain.documents.dao.TaTPaiement;
import fr.legrain.documents.dao.TaTPaiementDAO;
import fr.legrain.documents.events.SWTModificationDocumentEvent;
import fr.legrain.documents.events.SWTModificationDocumentListener;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.edition.actions.BaseImpressionEdition;
import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.edition.actions.InfosEmail;
import fr.legrain.edition.actions.InfosFax;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Document.ChangeModeEvent;
import fr.legrain.gestCom.Module_Document.ChangeModeListener;
import fr.legrain.gestCom.Module_Document.IHMAdresse;
import fr.legrain.gestCom.Module_Document.IHMAideAcompte;
import fr.legrain.gestCom.Module_Document.IHMEnteteAcompte;
import fr.legrain.gestCom.Module_Document.IHMRDocument;
import fr.legrain.gestCom.Module_Tiers.IHMAdresseInfosFacturation;
import fr.legrain.gestCom.Module_Tiers.IHMAdresseInfosLivraison;
import fr.legrain.gestCom.Module_Tiers.IHMIdentiteTiers;
import fr.legrain.gestCom.Module_Tiers.IHMInfosCPaiement;
import fr.legrain.gestCom.Module_Tiers.SWTCPaiement;
import fr.legrain.gestCom.Module_Tiers.SWTTiers;
import fr.legrain.gestCom.gestComBd.gestComBdPlugin;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.gestCom.librairiesEcran.swt.PaInfosAdresse;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideRechercheSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheVisualisation;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideRechercheControllerSWT;
import fr.legrain.gestCom.librairiesEcran.workbench.AbstractLgrMultiPageEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDePageEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementMasterEntityEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.IChangementMasterEntityListener;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.gestionCommerciale.GestionCommercialePlugin;
import fr.legrain.lib.data.ContentProposalProvider;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.InfosVerifSaisie;
import fr.legrain.lib.data.JPABdLgr;
import fr.legrain.lib.data.LibChaine;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.LibDate;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
import fr.legrain.lib.data.VerrouInterface;
import fr.legrain.lib.gui.DefaultFrameGrilleSansBouton;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.lib.gui.aide.PaAideRechercheSWT;
import fr.legrain.lib.gui.aide.PaAideSWT;
import fr.legrain.lib.gui.aide.ParamAfficheAide;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.lib.gui.grille.LgrViewerSupport;
import fr.legrain.libMessageLGR.LgrMess;
import fr.legrain.tiers.dao.TaAdresse;
import fr.legrain.tiers.dao.TaAdresseDAO;
import fr.legrain.tiers.dao.TaCPaiement;
import fr.legrain.tiers.dao.TaCPaiementDAO;
import fr.legrain.tiers.dao.TaCompteBanqueDAO;
import fr.legrain.tiers.dao.TaTAdrDAO;
import fr.legrain.tiers.dao.TaTCPaiement;
import fr.legrain.tiers.dao.TaTCPaiementDAO;
import fr.legrain.tiers.dao.TaTTvaDoc;
import fr.legrain.tiers.dao.TaTTvaDocDAO;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.ecran.PaAdresseSWT;
import fr.legrain.tiers.ecran.PaConditionPaiementSWT;
import fr.legrain.tiers.ecran.PaTiersSWT;
import fr.legrain.tiers.ecran.ParamAfficheAdresse;
import fr.legrain.tiers.ecran.ParamAfficheConditionPaiement;
import fr.legrain.tiers.ecran.ParamAfficheTiers;
import fr.legrain.tiers.ecran.SWTPaAdresseController;
import fr.legrain.tiers.ecran.SWTPaConditionPaiementController;
import fr.legrain.tiers.ecran.SWTPaTiersController;
import fr.legrain.tiers.editor.EditorConditionPaiement;
import fr.legrain.tiers.editor.EditorInputConditionPaiement;
import fr.legrain.tiers.editor.EditorInputTiers;
import fr.legrain.tiers.editor.TiersMultiPageEditor;

//#AFAIRE
//#evt
//#JPA
public class PaEditorAcompteController extends JPABaseControllerSWTStandard
		implements   RetourEcranListener,
		SWTModificationDocumentListener, ChangeModeListener {

	static Logger logger = Logger.getLogger(PaEditorAcompteController.class.getName());
	private PaEditorAcompteSWT vue = null;
	private Integer idTiersDocumentOriginal = null; //ID du tiers lorsque l'on remonte on document, ne devrait pas etre changé ailleur
//	private Impression impressionAcompte = null ;
	private boolean desactiveValidateCodeDocument=false;
	private TypeDoc typeDocPresent = TypeDoc.getInstance();

	/** 01/03/2010 modifier les editions (zhaolin) **/
	BaseImpressionEdition baseImpressionEdition = null;
	/************************************************/
	public static final String C_COMMAND_DOCUMENT_REINITIALISER_ID = "fr.legrain.Document.reinitialiser";
	protected HandlerReinitialiser handlerReinitialiser = new HandlerReinitialiser();

	public static final String C_COMMAND_DOCUMENT_REINIT_ADR_FACT_ID = "fr.legrain.Document.reinitAdrFact";
	protected HandlerReinitAdrFact handlerReinitAdrFact = new HandlerReinitAdrFact();

	public static final String C_COMMAND_DOCUMENT_REINIT_ADR_LIV_ID = "fr.legrain.Document.reinitAdrLiv";
	protected HandlerReinitAdrLiv handlerReinitAdrLiv = new HandlerReinitAdrLiv();

	public static final String C_COMMAND_DOCUMENT_REINIT_CPAIEMENT_ID = "fr.legrain.Document.reinitCPaiement";
	protected HandlerReinitCPaiement handlerReinitCPaiement = new HandlerReinitCPaiement();
	
	public static final String C_COMMAND_DOCUMENT_APPLIQUER_CPAIEMENT_ID = "fr.legrain.document.appliquerCPaiement";
	protected HandlerAppliquerCPaiement handlerAppliquerCPaiement = new HandlerAppliquerCPaiement();

	public static final String C_COMMAND_DOCUMENT_REINIT_INFOSTIERS_ID = "fr.legrain.Document.reinitInfosTiers";
	protected HandlerReinitInfosTiers handlerReinitInfosTiers = new HandlerReinitInfosTiers();

	public static final String C_COMMAND_DOCUMENT_AFFICHER_TIERS_ID = "fr.legrain.Document.afficherTiers";
	protected HandlerafficherTiers handlerafficherTiers = new HandlerafficherTiers();

	
	public List<Control> listeComposantEntete = null;
	public List<Control> listeComposantAdresse = null;
	public List<Control> listeComposantAdresse_Liv = null;
	public List<Control> listeComposantCPaiement = null;
	public List<Control> listeComposantInfosTiers = null;
	public List<Control> listeComposantVisuAffectation = null;
	
	protected Map<Control, String> mapComposantChampsAdresse = null;
	protected Map<Control, String> mapComposantChampsAdresseLiv = null;
	protected Map<Control, String> mapComposantChampsCPaiement = null;
	protected Map<Control, String> mapComposantChampsInfosTiers = null;
	protected Map<Control, String> mapComposantChampsVisuAffectation = null;
	
	private TaAcompteDAO dao = null;// new TaAcompteDAO();
	
	private Object ecranAppelant = null;
	private IHMEnteteAcompte ihmOldEnteteAcompte;
	private Realm realm;
	private DataBindingContext dbc;
	private Object selectedAcompte = new IHMEnteteAcompte();
	private String nomClassController = this.getClass().getSimpleName();
	private Class classModel = IHMEnteteAcompte.class;
	
	private ComboViewer viewerComboLocalisationTVA;
	
	private LgrTableViewer tableViewerAffectation;
	private IObservableValue selectedRAcompte; ;
	private ModelRAcompte modelRAcompte;
//	private List<TaAcompte> listeDocument = null;
	private List<TaTiers>listeTiers = null;
	private ModelGeneralObjet<IHMEnteteAcompte,TaAcompteDAO,TaAcompte> modelEnteteAcompte /*= new ModelGeneralObjet<IHMEnteteAcompte,TaAcompteDAO,TaAcompte>(dao,classModel)*/;
	private String typeAdresseFacturation=DocumentPlugin.getDefault().getPreferenceStore().getString(fr.legrain.document.preferences.PreferenceConstants.TYPE_ADRESSE_FACTURATION);
	private String typeAdresseLivraison=DocumentPlugin.getDefault().getPreferenceStore().getString(fr.legrain.document.preferences.PreferenceConstants.TYPE_ADRESSE_BONLIV);
	
	private TaInfosAcompteDAO daoInfosAcompte = null;//new TaInfosAcompteDAO();
	private TaAdresseDAO daoAdresseFact = null;//new TaAdresseDAO();
	private Class classModelAdresseFact = IHMAdresseInfosFacturation.class;
	private ModelGeneralObjet<IHMAdresseInfosFacturation,TaAdresseDAO,TaAdresse> modelAdresseFact /*= new ModelGeneralObjet<IHMAdresseInfosFacturation,TaAdresseDAO,TaAdresse>(daoAdresseFact,classModelAdresseFact)*/;
	private Object selectedAdresseFact = new IHMAdresseInfosFacturation();
	private DataBindingContext dbcAdresseFact = null;

	private Object selectedInfosTiers = new IHMIdentiteTiers();
	
	private TaAdresseDAO daoAdresseLiv = null;//new TaAdresseDAO();
	private Class classModelAdresseLiv = IHMAdresseInfosLivraison.class;
	private ModelGeneralObjet<IHMAdresseInfosLivraison,TaAdresseDAO,TaAdresse> modelAdresseLiv /*= new ModelGeneralObjet<IHMAdresseInfosLivraison,TaAdresseDAO,TaAdresse>(daoAdresseLiv,classModelAdresseLiv)*/;
	private Object selectedAdresseLiv = new IHMAdresseInfosLivraison();
	private DataBindingContext dbcAdresseLiv = null;

	private TaCPaiementDAO daoCPaiement = null;//new TaCPaiementDAO();
	private Object selectedCPaiement = new IHMInfosCPaiement();
	private Class classModelCPaiement = IHMInfosCPaiement.class;
	private ModelGeneralObjet<IHMInfosCPaiement,TaCPaiementDAO,TaCPaiement> modelCPaiement /*= new ModelGeneralObjet<IHMInfosCPaiement,TaCPaiementDAO,TaCPaiement>(daoCPaiement,classModelCPaiement)*/;
	private DataBindingContext dbcCPaiement = null;

	protected ContentProposalAdapter codeDocumentContentProposal;
	private ContentProposalAdapter tiersContentProposal;
	private MapChangeListener changeListener = new MapChangeListener();

	private PaLigneAcompteController controllerLigne = null;
	private PaEditorTotauxAcompteController controllerTotaux = null;
	
	private LgrDozerMapper<TaAdresse,IHMAdresseInfosFacturation> mapperModelToUIAdresseInfosDocument = new LgrDozerMapper<TaAdresse,IHMAdresseInfosFacturation>();
	private LgrDozerMapper<TaAdresse,IHMAdresseInfosLivraison> mapperModelToUIAdresseLivInfosDocument = new LgrDozerMapper<TaAdresse,IHMAdresseInfosLivraison>();
	private LgrDozerMapper<TaCPaiement,IHMInfosCPaiement> mapperModelToUICPaiementInfosDocument = new LgrDozerMapper<TaCPaiement,IHMInfosCPaiement>();
	
	private LgrDozerMapper<TaInfosAcompte,IHMInfosCPaiement> mapperModelToUIInfosDocVersCPaiement = new LgrDozerMapper<TaInfosAcompte,IHMInfosCPaiement>();
	private LgrDozerMapper<TaInfosAcompte,IHMAdresseInfosFacturation> mapperModelToUIInfosDocVersAdresseFact = new LgrDozerMapper<TaInfosAcompte,IHMAdresseInfosFacturation>();
	private LgrDozerMapper<TaInfosAcompte,IHMAdresseInfosLivraison> mapperModelToUIInfosDocVersAdresseLiv = new LgrDozerMapper<TaInfosAcompte,IHMAdresseInfosLivraison>();
		
	private LgrDozerMapper<IHMInfosCPaiement,TaInfosAcompte> mapperUIToModelCPaiementVersInfosDoc = new LgrDozerMapper<IHMInfosCPaiement, TaInfosAcompte>();
	private LgrDozerMapper<IHMAdresseInfosFacturation,TaInfosAcompte> mapperUIToModelAdresseFactVersInfosDoc = new LgrDozerMapper<IHMAdresseInfosFacturation, TaInfosAcompte>();
	private LgrDozerMapper<IHMAdresseInfosLivraison,TaInfosAcompte> mapperUIToModelAdresseLivVersInfosDoc = new LgrDozerMapper<IHMAdresseInfosLivraison, TaInfosAcompte>();
	
	private LgrDozerMapper<TaAcompte,TaInfosAcompte> mapperUIToModelDocumentVersInfosDoc = new LgrDozerMapper<TaAcompte, TaInfosAcompte>();	
	
	private LgrDozerMapper<IHMEnteteAcompte,TaAcompte> mapperUIToModel = new LgrDozerMapper<IHMEnteteAcompte,TaAcompte>();
	private LgrDozerMapper<TaAcompte,IHMEnteteAcompte> mapperModelToUI = new LgrDozerMapper<TaAcompte,IHMEnteteAcompte>();
	
	private TaAcompte taDocument = null;
	private TaInfosAcompte taInfosDocument = null;

	
	private PaInfosAdresse paInfosAdresseLiv = null;
	private PaInfosAdresse paInfosAdresseFact = null;
//	private PaInfosCondPaiement paInfosCondPaiement = null;
	private PaIdentiteTiers paIdentiteTiers = null;
	private DefaultFrameGrilleSansBouton visuDesFacturesAffecteeAcompte = null;
	
	private Menu popupMenuFormulaire = null;
	private Menu popupMenuGrille = null;

	public PaEditorAcompteController() {
	}
	
	public PaEditorAcompteController(PaEditorAcompteSWT vue) {
		this(vue,null);
	}

	public PaEditorAcompteController(PaEditorAcompteSWT vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		try {

			dao = new TaAcompteDAO(getEm());
			daoInfosAcompte = new TaInfosAcompteDAO(getEm());
			daoAdresseFact = new TaAdresseDAO(getEm());
			daoAdresseLiv = new TaAdresseDAO(getEm());
			daoCPaiement = new TaCPaiementDAO(getEm());
			setVue(vue);
			try {
				setTypeAdresseFacturation(DocumentPlugin.getDefault().getPreferenceStore().getString(fr.legrain.document.preferences.PreferenceConstants.TYPE_ADRESSE_FACTURATION));
				setTypeAdresseLivraison(DocumentPlugin.getDefault().getPreferenceStore().getString(fr.legrain.document.preferences.PreferenceConstants.TYPE_ADRESSE_BONLIV));
			} catch (Exception e) {				
			}
			vue.getShell().addShellListener(this);
			vue.getShell().addTraverseListener(new Traverse());
//			impressionAcompte = new Impression(vue.getShell(),getEm());
			
			
			/** 01/03/2010 modifier les editions (zhaolin) **/
			baseImpressionEdition = new BaseImpressionEdition(vue.getShell(),getEm());
			/************************************************/
			
			vue.getDateTimeDATE_DOCUMENT().addTraverseListener(new DateTraverse());
//			vue.getDateTimeDATE_ECH_FACTURE().addTraverseListener(new DateTraverse());
//			vue.getDateTimeDATE_LIV_FACTURE().addTraverseListener(new DateTraverse());

//			addDestroyListener(ibTaTable);
			setDaoStandard(dao);

//			this.swtAcompte.addModificationDocumentListener(dao);
//			this.swtAcompte.addModificationDocumentListener(this);
//			this.swtAcompte.addChangeModeListener(this);
			
//			taAcompte.addModificationDocumentListener(controllerLigne);
//			taAcompte.addModificationDocumentListener(controllerTotaux);
//			taAcompte.addModificationDocumentListener(this);
			
//			taAcompte.addChangeModeListener(this);

			// à l'insertion d'une nouvelle facture, le champ est contrôlé à
			// vide, ce qui fait
			// lorsque que l'on sort de la zone sans l'avoir rempli, il ne la
			// re-contrôle pas
			// d'où le rajout de se focusListener pour le forcer à remplir la
			// zone
			vue.getTfLIBELLE_DOCUMENT().addFocusListener(new FocusAdapter() {
				public void focusLost(FocusEvent e) {
					try {
//						ctrlUnChampsSWT(((PaEditorAcompteSWT) getVue()).getTfLIBELLE_FACTURE());
						validateUIField(Const.C_LIBELLE_DOCUMENT,((PaEditorAcompteSWT) getVue()).getTfLIBELLE_DOCUMENT());
					} catch (Exception e1) {
						logger.debug("", e1);
					}
				}
			});
			initController();

//			vue.getDateTimeDATE_ECH_FACTURE().addFocusListener(dateFocusListener);
			vue.getDateTimeDATE_DOCUMENT().addFocusListener(dateFocusListener);
//			vue.getDateTimeDATE_LIV_FACTURE().addFocusListener(dateFocusListener);

		} catch (Exception e) {
			logger.debug("", e);
		}
	}

	private void initVue() {
		paInfosAdresseFact =  new PaInfosAdresse(vue.getExpandBar(), SWT.PUSH);
		addExpandBarItem(vue.getExpandBar(),paInfosAdresseFact , "Adresse de facturation",
				pluginAcompte.getImageDescriptor("/icons/book_open.png").createImage());

		paInfosAdresseLiv =  new PaInfosAdresse(vue.getExpandBar(), SWT.PUSH);
		addExpandBarItem(vue.getExpandBar(), paInfosAdresseLiv, "Adresse de livraison",
				pluginAcompte.getImageDescriptor("/icons/lorry.png").createImage());
		
//		paInfosCondPaiement =  new PaInfosCondPaiement(vue.getExpandBar(), SWT.PUSH);
//		addExpandBarItem(vue.getExpandBar(), paInfosCondPaiement, "Conditions de paiement",
//				pluginAcompte.getImageDescriptor("/icons/creditcards.png").createImage());

		paIdentiteTiers =  new PaIdentiteTiers(vue.getExpandBar(), SWT.PUSH);
		addExpandBarItem(vue.getExpandBar(),paIdentiteTiers, "Identité du tiers",
				pluginAcompte.getImageDescriptor("/icons/user.png").createImage());
		
		visuDesFacturesAffecteeAcompte = new DefaultFrameGrilleSansBouton(vue.getExpandBar(), SWT.PUSH);
		addExpandBarItem(vue.getExpandBar(),visuDesFacturesAffecteeAcompte, "Visualisation des factures affectées à l'acompte",
				pluginAcompte.getImageDescriptor("/icons/user.png").createImage());

		findExpandIem(vue.getExpandBar(), paInfosAdresseFact).setExpanded(
						DocumentPlugin.getDefault().getPreferenceStore().getBoolean(
										fr.legrain.document.preferences.PreferenceConstants.AFF_ADRESSE));
		findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).setExpanded(
						DocumentPlugin.getDefault().getPreferenceStore().getBoolean(
										fr.legrain.document.preferences.PreferenceConstants.AFF_ADRESSE_LIV));
//		findExpandIem(vue.getExpandBar(), paInfosCondPaiement).setExpanded(
//						DocumentPlugin.getDefault().getPreferenceStore().getBoolean(
//										fr.legrain.document.preferences.PreferenceConstants.AFF_CPAIEMENT));
		findExpandIem(vue.getExpandBar(), paIdentiteTiers).setExpanded(DocumentPlugin.getDefault().getPreferenceStore().getBoolean(
				fr.legrain.document.preferences.PreferenceConstants.AFF_IDENTITE_TIERS));
//		
		findExpandIem(vue.getExpandBar(), visuDesFacturesAffecteeAcompte).setExpanded(pluginAcompte.getDefault().getPreferenceStore()
				.getBoolean(fr.legrain.acompte.preferences.PreferenceConstants.AFF_VISU_AFFECTATION_FACTURE));
	}

	private void initController() {
		try {
			// setGrille(vue.getGrille());
			setDaoStandard(dao);
			// setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);
			// vue.getCbImprimer().setSelection(false);
			//			
			// try{
			// vue.getCbImprimer().setSelection(AcomptePlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.IMPRIMER_AUTO))
			// ;
			// }catch (Exception e) {
			// }
			initVue();

			initMapComposantChamps();
			initVerifySaisie();
			initMapComposantDecoratedField();

			// // / ne pas déplacer !!!
			// *** Je fais le "initDeplacementSaisie" avant de remplir la
			// listeFocusable avec
			// les éléments des lignes pour ne pas leur affecter des traverses 2
			// fois
			// Ceux qui comptent sont affectés dans le controller des lignes ***
			initDeplacementSaisie(listeComposantFocusable);
			listeComponentFocusableSWT(listeComposantFocusable);
			initFocusOrder();
			initActions();

			branchementBouton();

			popupMenuFormulaire = new Menu(vue.getShell(), SWT.POP_UP);
			popupMenuGrille = new Menu(vue.getShell(), SWT.POP_UP);
			Menu[] tabPopups = new Menu[] { popupMenuFormulaire,
					popupMenuGrille };
			this.initPopupAndButtons(mapActions, tabPopups);
			// vue.getPaBas().setMenu(popupMenuFormulaire);
			// vue.getPaTotaux().setMenu(popupMenuFormulaire);
			 vue.getPaEntete().setMenu(popupMenuFormulaire);
			 vue.getExpandBar().setMenu(popupMenuFormulaire);
			 vue.setMenu(popupMenuFormulaire);
			 findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl().setMenu(popupMenuFormulaire);
			 findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl().setMenu(popupMenuFormulaire);
//			 vue.getExpandBar().getItem(2).getControl().setMenu(popupMenuFormulaire);
			 findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl().setMenu(popupMenuFormulaire);
			bind(vue);
			initEtatBouton(true);

			vue.getTfCODE_DOCUMENT().setEditable(false);

		} catch (Exception e) {
			if (e.getMessage() != null)
				vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaArticlesController", e);
		}
	}

	public void bind(PaEditorAcompteSWT PaEditorAcompteSWT) {
		try {
			modelEnteteAcompte = new ModelGeneralObjet<IHMEnteteAcompte,TaAcompteDAO,TaAcompte>(dao,classModel);
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

//			modelEnteteAcompte.remplirListe();
			if (modelEnteteAcompte.getListeObjet().isEmpty()) {
				modelEnteteAcompte.getListeObjet().add(new IHMEnteteAcompte());
			}
			selectedAcompte = modelEnteteAcompte.getListeObjet().getFirst();
			
			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			setDbcStandard(dbc);

			bindingFormSimple(dbc, realm, selectedAcompte, classModel);
			bindAdresse();
			bindAdresseLiv();
			bindCPaiement();
			bindInfosTiers();
			bindAffectation();
			bindComboLocalisationTVA();

		} catch (Exception e) {
			if (e.getMessage() != null)
				vue.getLaMessage().setText(e.getMessage());
			logger.error("", e);
		}
	}
	
	public void bindComboLocalisationTVA() {
		TaTTvaDocDAO dao = new TaTTvaDocDAO();
		
		viewerComboLocalisationTVA = new ComboViewer(vue.getComboLocalisationTVA());
		
		viewerComboLocalisationTVA.setComparer(new IElementComparer() {
			
			@Override
			public int hashCode(Object element) {
				return 0;
			}
			
			@Override
			public boolean equals(Object a, Object b) {
				// TODO Auto-generated method stub
				return ((TaTTvaDoc)a).getCodeTTvaDoc().equals(((TaTTvaDoc)b).getCodeTTvaDoc());
			}
		});

		viewerComboLocalisationTVA.setContentProvider(ArrayContentProvider.getInstance());
		viewerComboLocalisationTVA.setLabelProvider(new LabelProvider() {
		  @Override
		  public String getText(Object element) {
		    if (element instanceof TaTTvaDoc) {
		    	TaTTvaDoc t = (TaTTvaDoc) element;
		      return t.getLibelleTTvaDoc();
		    }
		    return super.getText(element);
		  }
		});

		List<TaTTvaDoc> l = dao.selectAll();
		TaTTvaDoc[] tab = new TaTTvaDoc[l.size()];
		int i = 0;
		for (TaTTvaDoc taTTvaDoc : l) {
			tab[i] = taTTvaDoc;
			i++;
		}

		viewerComboLocalisationTVA.setInput(tab); 
		
	}

	public void bindAffectation() {
		try {
			modelRAcompte = new ModelRAcompte();
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());
			(((DefaultFrameGrilleSansBouton) findExpandIem(vue.getExpandBar(), visuDesFacturesAffecteeAcompte).getControl())).getLaTitreGrille().setText("Liste des factures affectées à l'acompte");
		
			tableViewerAffectation = new LgrTableViewer(((DefaultFrameGrilleSansBouton) 
					findExpandIem(vue.getExpandBar(), visuDesFacturesAffecteeAcompte).getControl())
					.getGrille());
			tableViewerAffectation.createTableCol(((DefaultFrameGrilleSansBouton) 
					findExpandIem(vue.getExpandBar(), visuDesFacturesAffecteeAcompte).getControl())
					.getGrille(), "AffectationsAcompte",
					Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			String[]listeChampAffectation = tableViewerAffectation.setListeChampGrille("AffectationsAcompte",
					Const.C_FICHIER_LISTE_CHAMP_GRILLE);

			selectedRAcompte = ViewersObservables
			.observeSingleSelection(tableViewerAffectation);

			LgrViewerSupport.bind(tableViewerAffectation, 
					 new WritableList(modelRAcompte.remplirListeFactures(taDocument,getEm()), IHMRDocument.class),
					BeanProperties.values(listeChampAffectation)
			);
			((DefaultFrameGrilleSansBouton) findExpandIem(vue.getExpandBar(), visuDesFacturesAffecteeAcompte)
					.getControl()).getGrille().addMouseListener(
					new MouseAdapter() {

						public void mouseDoubleClick(MouseEvent e) {
							String idEditor = typeDocPresent.getEditorDoc()
							.get(
									((IHMRDocument) selectedRAcompte
											.getValue())
											.getTypeDocument());
					String valeurIdentifiant = ((IHMRDocument) selectedRAcompte
							.getValue()).getCodeDocument();
					ouvreDocument(valeurIdentifiant, idEditor);
						}

					});
		} 
		catch (Exception e) {
			if (e.getMessage() != null)
				vue.getLaMessage().setText(e.getMessage());
			logger.error("", e);
		}
	}

	
	public void bindAdresse() {
		try {
			modelAdresseFact = new ModelGeneralObjet<IHMAdresseInfosFacturation,TaAdresseDAO,TaAdresse>(daoAdresseFact,classModelAdresseFact);
//			modelAdresseFact.remplirListe();
			dbcAdresseFact = new DataBindingContext(realm);
			bindingFormSimple(daoAdresseFact,mapComposantChampsAdresse, dbcAdresseFact, realm,selectedAdresseFact, classModelAdresseFact);
		} catch (Exception e) {
			if (e.getMessage() != null)
				vue.getLaMessage().setText(e.getMessage());
			logger.error("", e);
		}
	}

	public void bindInfosTiers() {
		try {
			TaTiersDAO daoTiers = new TaTiersDAO(getEm());
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());
			selectedInfosTiers = new IHMIdentiteTiers();
			DataBindingContext dbcInfosTiers = new DataBindingContext(realm);
			bindingFormSimple(daoTiers,mapComposantChampsInfosTiers, dbcInfosTiers, realm, selectedInfosTiers, IHMIdentiteTiers.class);
		} 
		catch (Exception e) {
			if (e.getMessage() != null)
				vue.getLaMessage().setText(e.getMessage());
			logger.error("", e);
		}
	}
	public void bindAdresseLiv() {
		try {
			modelAdresseLiv = new ModelGeneralObjet<IHMAdresseInfosLivraison,TaAdresseDAO,TaAdresse>(daoAdresseLiv,classModelAdresseLiv);
			dbcAdresseLiv = new DataBindingContext(realm);
			bindingFormSimple(daoAdresseLiv,mapComposantChampsAdresseLiv, dbcAdresseLiv, realm, selectedAdresseLiv, classModelAdresseLiv);
		} catch (Exception e) {
			if (e.getMessage() != null)
				vue.getLaMessage().setText(e.getMessage());
			logger.error("", e);
		}
	}

	public void bindCPaiement() {
		try {
			modelCPaiement = new ModelGeneralObjet<IHMInfosCPaiement,TaCPaiementDAO,TaCPaiement>(daoCPaiement,classModelCPaiement);
//			modelCPaiement.remplirListe();
			bindingFormSimple(daoCPaiement, mapComposantChampsCPaiement, dbc, realm, selectedCPaiement, classModelCPaiement);
		} catch (Exception e) {
			if (e.getMessage() != null)
				vue.getLaMessage().setText(e.getMessage());
			logger.error("", e);
		}
	}


	public Composite getVue() {
		return vue;
	}

	public ResultAffiche configPanel(ParamAffiche param) {
		param.setFocus(dao.getModeObjet().getFocusCourant());
		try {
			if (param.getCodeDocument()!=null) {
				remonterDocument(param.getCodeDocument());
			}else
				actInserer();
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}
	
	protected void initVerifySaisie() {
		if (mapInfosVerifSaisie == null)
			mapInfosVerifSaisie = new HashMap<Control, InfosVerifSaisie>();
		
		mapInfosVerifSaisie.put(vue.getTfCODE_DOCUMENT(), new InfosVerifSaisie(new TaAcompte(),Const.C_CODE_DOCUMENT,null));
		mapInfosVerifSaisie.put(vue.getTfCODE_TIERS(), new InfosVerifSaisie(new TaTiers(),Const.C_CODE_TIERS,null));
		mapInfosVerifSaisie.put(vue.getTfLIBELLE_DOCUMENT(), new InfosVerifSaisie(new TaAcompte(),Const.C_LIBELLE_DOCUMENT,null));
		
		mapInfosVerifSaisie.put(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl()).getTfAdresse1(), new InfosVerifSaisie(new TaInfosAcompte(),Const.C_ADRESSE1_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl()).getTfAdresse2(), new InfosVerifSaisie(new TaInfosAcompte(),Const.C_ADRESSE2_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse)findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl()).getTfAdresse3(), new InfosVerifSaisie(new TaInfosAcompte(),Const.C_ADRESSE3_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl())
				.getTfCodePostal(), new InfosVerifSaisie(new TaInfosAcompte(),Const.C_CODEPOSTAL_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl()).getTfVille(), new InfosVerifSaisie(new TaInfosAcompte(),Const.C_VILLE_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl()).getTfPays(), new InfosVerifSaisie(new TaInfosAcompte(),Const.C_PAYS_INFOS_DOCUMENT,null));

		mapInfosVerifSaisie.put(((PaInfosAdresse) 
				findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl())
				.getTfAdresse1(), new InfosVerifSaisie(new TaInfosAcompte(),Const.C_ADRESSE1_LIV_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse) 
				findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl())
				.getTfAdresse2(), new InfosVerifSaisie(new TaInfosAcompte(),Const.C_ADRESSE2_LIV_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse) 
				findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl())
				.getTfAdresse3(), new InfosVerifSaisie(new TaInfosAcompte(),Const.C_ADRESSE3_LIV_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse) 
				findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl())
				.getTfCodePostal(), new InfosVerifSaisie(new TaInfosAcompte(),Const.C_CODEPOSTAL_LIV_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse) 
				findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl())
				.getTfVille(), new InfosVerifSaisie(new TaInfosAcompte(),Const.C_VILLE_LIV_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse) 
				findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl())
				.getTfPays(), new InfosVerifSaisie(new TaInfosAcompte(),Const.C_PAYS_LIV_INFOS_DOCUMENT,null));
				
//		mapInfosVerifSaisie.put(((PaInfosCondPaiement) vue
//				.getExpandBar().getItem(2).getControl())
//				.getTfCODE_C_PAIEMENT(), new InfosVerifSaisie(new TaInfosAcompte(),Const.C_CODE_C_PAIEMENT,null));
//		mapInfosVerifSaisie.put(((PaInfosCondPaiement) vue
//				.getExpandBar().getItem(2).getControl())
//				.getTfLIB_C_PAIEMENT(), new InfosVerifSaisie(new TaInfosAcompte(),Const.C_LIB_C_PAIEMENT,null));
//		mapInfosVerifSaisie.put(((PaInfosCondPaiement) vue
//				.getExpandBar().getItem(2).getControl())
//				.getTfREPORT_C_PAIEMENT(), new InfosVerifSaisie(new TaInfosAcompte(),Const.C_REPORT_C_PAIEMENT,null));
//		mapInfosVerifSaisie.put(((PaInfosCondPaiement) vue
//				.getExpandBar().getItem(2).getControl())
//				.getTfFIN_MOIS_C_PAIEMENT(), new InfosVerifSaisie(new TaInfosAcompte(),Const.C_FIN_MOIS_C_PAIEMENT,null));
		
		initVerifyListener(mapInfosVerifSaisie, dao);
	}

	protected void initComposantsVue() throws ExceptLgr {
	}

	protected void initMapComposantChamps() {
		if (mapComposantChamps == null)
			mapComposantChamps = new LinkedHashMap<Control, String>();

		if (listeComposantFocusable == null)
			listeComposantFocusable = new ArrayList<Control>();

		if (listeComposantEntete == null)
			listeComposantEntete = new ArrayList<Control>();

		if (listeComposantAdresse == null)
			listeComposantAdresse = new ArrayList<Control>();

		if (listeComposantAdresse_Liv == null)
			listeComposantAdresse_Liv = new ArrayList<Control>();

		if (listeComposantCPaiement == null)
			listeComposantCPaiement = new ArrayList<Control>();
		
		if (listeComposantInfosTiers == null)
			listeComposantInfosTiers = new ArrayList<Control>();

		if (listeComposantVisuAffectation == null)
			listeComposantVisuAffectation = new ArrayList<Control>();
		
		if (mapComposantChampsAdresse == null)
			mapComposantChampsAdresse = new LinkedHashMap<Control, String>();

		if (mapComposantChampsAdresseLiv == null)
			mapComposantChampsAdresseLiv = new LinkedHashMap<Control, String>();

		if (mapComposantChampsCPaiement == null)
			mapComposantChampsCPaiement = new LinkedHashMap<Control, String>();
		
		if(mapComposantChampsInfosTiers== null)
			mapComposantChampsInfosTiers = new LinkedHashMap<Control, String>();

//		if(mapComposantChampsVisuAffectation== null)
//			mapComposantChampsVisuAffectation = new LinkedHashMap<Control, String>();
		
		mapComposantChamps.put(vue.getTfCODE_DOCUMENT(), Const.C_CODE_DOCUMENT);
		mapComposantChamps.put(vue.getTfCODE_TIERS(), Const.C_CODE_TIERS);
		mapComposantChamps.put(vue.getDateTimeDATE_DOCUMENT(),
				Const.C_DATE_DOCUMENT);
		mapComposantChamps.put(vue.getTfLIBELLE_DOCUMENT(),
				Const.C_LIBELLE_DOCUMENT);
		mapComposantChamps.put(vue.getCbTTC(), Const.C_TTC);
		
		vue.getCbTTC().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				initTTC();
			}

		});
		
		vue.getComboLocalisationTVA().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				initLocalisationTVA();
			}

		});

		vue.getCbTTC().addMouseTrackListener(new MouseTrackAdapter() {
			public void mouseEnter(org.eclipse.swt.events.MouseEvent e) {
				initEtatComposant();
			}
		});

//		mapComposantChamps.put(vue.getDateTimeDATE_ECH_FACTURE(),
//				Const.C_DATE_ECH_DOCUMENT);
//		mapComposantChamps.put(vue.getDateTimeDATE_LIV_FACTURE(),
//				Const.C_DATE_LIV_DOCUMENT);

		for (Control c : mapComposantChamps.keySet()) {
			listeComposantEntete.add(c);
		}

		mapComposantChampsAdresse.put(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl()).getTfAdresse1(),
				Const.C_ADRESSE1);
		mapComposantChampsAdresse.put(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl()).getTfAdresse2(),
				Const.C_ADRESSE2);
		mapComposantChampsAdresse.put(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl()).getTfAdresse3(),
				Const.C_ADRESSE3);
		mapComposantChampsAdresse.put(((PaInfosAdresse)findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl())
				.getTfCodePostal(), Const.C_CODEPOSTAL);
		mapComposantChampsAdresse.put(((PaInfosAdresse)findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl()).getTfVille(),
				Const.C_VILLE);
		mapComposantChampsAdresse.put(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl()).getTfPays(),
				Const.C_PAYS);		
		for (Control c : mapComposantChampsAdresse.keySet()) {
			listeComposantAdresse.add(c);
		}
		listeComposantAdresse.add(((PaInfosAdresse)findExpandIem(vue.getExpandBar(), paInfosAdresseFact)
				.getControl()).getBtnReinitialiser());

		mapComposantChampsAdresseLiv.put(((PaInfosAdresse) 
				findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl())
				.getTfAdresse1(), Const.C_ADRESSE1_LIV);
		mapComposantChampsAdresseLiv.put(((PaInfosAdresse) 
				findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl())
				.getTfAdresse2(), Const.C_ADRESSE2_LIV);
		mapComposantChampsAdresseLiv.put(((PaInfosAdresse) 
				findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl())
				.getTfAdresse3(), Const.C_ADRESSE3_LIV);
		mapComposantChampsAdresseLiv.put(((PaInfosAdresse) 
				findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl())
				.getTfCodePostal(), Const.C_CODEPOSTAL_LIV);
		mapComposantChampsAdresseLiv.put(((PaInfosAdresse) 
				findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl())
				.getTfVille(), Const.C_VILLE_LIV);
		mapComposantChampsAdresseLiv.put(((PaInfosAdresse) 
				findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl())
				.getTfPays(), Const.C_PAYS_LIV);
		mapComposantChampsAdresseLiv.put(((PaInfosAdresse) 
				findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl())
				.getTfPays(), Const.C_PAYS_LIV);		
		for (Control c : mapComposantChampsAdresseLiv.keySet()) {
			listeComposantAdresse_Liv.add(c);
		}
		listeComposantAdresse_Liv.add(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl()).getBtnReinitialiser());

//		mapComposantChampsCPaiement.put(((PaInfosCondPaiement) vue
//				.getExpandBar().getItem(2).getControl())
//				.getTfCODE_C_PAIEMENT(),
//				Const.C_CODE_C_PAIEMENT);
//		mapComposantChampsCPaiement.put(((PaInfosCondPaiement) vue
//				.getExpandBar().getItem(2).getControl())
//				.getTfLIB_C_PAIEMENT(),
//				Const.C_LIB_C_PAIEMENT);
//		mapComposantChampsCPaiement.put(((PaInfosCondPaiement) vue
//				.getExpandBar().getItem(2).getControl())
//				.getTfREPORT_C_PAIEMENT(),
//				Const.C_REPORT_C_PAIEMENT);
//		mapComposantChampsCPaiement.put(((PaInfosCondPaiement) vue
//				.getExpandBar().getItem(2).getControl())
//				.getTfFIN_MOIS_C_PAIEMENT(),
//				Const.C_FIN_MOIS_C_PAIEMENT);
		for (Control c : mapComposantChampsCPaiement.keySet()) {
			listeComposantCPaiement.add(c);
		}
//		listeComposantCPaiement.add(((PaInfosCondPaiement) vue
//				.getExpandBar().getItem(2).getControl())
//				.getBtnReinitialiser());
//		listeComposantCPaiement.add(((PaInfosCondPaiement) vue
//				.getExpandBar().getItem(2).getControl())
//				.getBtnAppliquer());
		
		//gestion infos tiers
		mapComposantChampsInfosTiers.put(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfCODE_T_CIVILITE(),
				Const.C_CODE_T_CIVILITE);
		mapComposantChampsInfosTiers.put(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfCODE_COMPTA(),
				Const.C_CODE_COMPTA);
		mapComposantChampsInfosTiers.put(((PaIdentiteTiers)findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfCOMPTE(),
				Const.C_COMPTE);
		mapComposantChampsInfosTiers.put(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfADRESSE_EMAIL(),
				Const.C_ADRESSE_EMAIL);
		mapComposantChampsInfosTiers.put(((PaIdentiteTiers) 
				findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfEntite(),
				Const.C_CODE_T_ENTITE);
		mapComposantChampsInfosTiers.put(((PaIdentiteTiers) 
				findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfCODE_ENTREPRISE(),
				Const.C_NOM_ENTREPRISE);
		mapComposantChampsInfosTiers.put(((PaIdentiteTiers) 
				findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfPRENOM_TIERS(),
				Const.C_PRENOM_TIERS);
		mapComposantChampsInfosTiers.put(((PaIdentiteTiers) 
				findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfNUMERO_TELEPHONE(),
				Const.C_NUMERO_TELEPHONE);
		mapComposantChampsInfosTiers.put(((PaIdentiteTiers) 
				findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfNOM_TIERS(),
				Const.C_NOM_TIERS);
		mapComposantChampsInfosTiers.put(((PaIdentiteTiers) 
				findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfTVA_I_COM_COMPL(),
				Const.C_TVA_I_COM_COMPL);
		
		mapComposantChampsInfosTiers.put(((PaIdentiteTiers) 
				findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfADRESSE_WEB(),
				Const.C_ADRESSE_WEB);
		mapComposantChampsInfosTiers.put(((PaIdentiteTiers) 
				findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfLIBL_COMMENTAIRE(),
				Const.C_LIBL_COMMENTAIRE);
		mapComposantChampsInfosTiers.put(((PaIdentiteTiers) 
				findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfSIRET_COMPL(),
				Const.C_SIRET_COMPL);
		for (Control c : mapComposantChampsInfosTiers.keySet()) {
			listeComposantInfosTiers.add(c);
		}
		listeComposantInfosTiers.add(((PaIdentiteTiers) 
				findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getBtnReinitialiser());
		listeComposantInfosTiers.add(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getBtnAfficher());		
		/////
		listeComposantVisuAffectation.add(((DefaultFrameGrilleSansBouton) 
				findExpandIem(vue.getExpandBar(), visuDesFacturesAffecteeAcompte).getControl())
				.getGrille());
		
		for (Control c : mapComposantChamps.keySet()) {
			c.setToolTipText(mapComposantChamps.get(c));
		}

		// branchement du F6 sur tous les composants, même ceux des lignes
		// en attendant de trouver pourquoi la commande F6 ne fonctionne pas
		// correctement
		// sauf le composite des lignes et des CDateTime car sinon je n'ai plus
		// de réaction
		// sur les traverses sur ces composants
		listeComposantFocusable.add(vue.getTfCODE_DOCUMENT());
		listeComposantFocusable.add(vue.getTfCODE_TIERS());
		listeComposantFocusable.add(vue.getDateTimeDATE_DOCUMENT());
		listeComposantFocusable.add(vue.getTfLIBELLE_DOCUMENT());
		listeComposantFocusable.add(vue.getCbTTC());
		listeComposantFocusable.add(vue.getComboLocalisationTVA());
//		listeComposantFocusable.add(vue.getDateTimeDATE_ECH_FACTURE());
//
//		listeComposantFocusable.add(vue.getDateTimeDATE_LIV_FACTURE());
		listeComposantFocusable.add(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl()).getTfAdresse1());
		listeComposantFocusable.add(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl()).getTfAdresse2());
		listeComposantFocusable.add(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl()).getTfAdresse3());
		listeComposantFocusable.add(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl()).getTfCodePostal());
		listeComposantFocusable.add(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl()).getTfVille());
		listeComposantFocusable.add(((PaInfosAdresse)findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl()).getTfPays());
		listeComposantFocusable.add(((PaInfosAdresse)findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl()).getBtnReinitialiser());
		
		listeComposantFocusable.add(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl()).getTfAdresse1());
		listeComposantFocusable.add(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl()).getTfAdresse2());
		listeComposantFocusable.add(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl()).getTfAdresse3());
		listeComposantFocusable.add(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl()).getTfCodePostal());
		listeComposantFocusable.add(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl()).getTfVille());
		listeComposantFocusable.add(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl()).getTfPays());
		listeComposantFocusable.add(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl()).getBtnReinitialiser());

//		listeComposantFocusable.add(((PaInfosCondPaiement) vue
//				.getExpandBar().getItem(2).getControl())
//				.getTfCODE_C_PAIEMENT());
//		listeComposantFocusable.add(((PaInfosCondPaiement) vue
//				.getExpandBar().getItem(2).getControl())
//				.getTfLIB_C_PAIEMENT());
//		listeComposantFocusable.add(((PaInfosCondPaiement) vue
//				.getExpandBar().getItem(2).getControl())
//				.getTfREPORT_C_PAIEMENT());
//		listeComposantFocusable.add(((PaInfosCondPaiement) vue
//				.getExpandBar().getItem(2).getControl())
//				.getTfFIN_MOIS_C_PAIEMENT());
//		listeComposantFocusable.add(((PaInfosCondPaiement) vue
//				.getExpandBar().getItem(2).getControl())
//				.getBtnReinitialiser());
//		listeComposantFocusable.add(((PaInfosCondPaiement) vue
//				.getExpandBar().getItem(2).getControl())
//				.getBtnAppliquer());

		//identité du tiers
		listeComposantFocusable.add(((PaIdentiteTiers) 
				findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfEntite());
		listeComposantFocusable.add(((PaIdentiteTiers) 
				findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfCODE_ENTREPRISE());
		listeComposantFocusable.add(((PaIdentiteTiers) 
				findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfCODE_T_CIVILITE());
		listeComposantFocusable.add(((PaIdentiteTiers) 
				findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfNOM_TIERS());
		listeComposantFocusable.add(((PaIdentiteTiers) 
				findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfPRENOM_TIERS());
		listeComposantFocusable.add(((PaIdentiteTiers) 
				findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfTVA_I_COM_COMPL());		
		listeComposantFocusable.add(((PaIdentiteTiers) 
				findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfSIRET_COMPL());
		listeComposantFocusable.add(((PaIdentiteTiers) 
				findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfNUMERO_TELEPHONE());
		listeComposantFocusable.add(((PaIdentiteTiers) 
				findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfADRESSE_EMAIL());
		listeComposantFocusable.add(((PaIdentiteTiers) 
				findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfADRESSE_WEB());
		listeComposantFocusable.add(((PaIdentiteTiers) 
				findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfCODE_COMPTA());
		listeComposantFocusable.add(((PaIdentiteTiers) 
				findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfCOMPTE());
		listeComposantFocusable.add(((PaIdentiteTiers) 
				findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfLIBL_COMMENTAIRE());
		listeComposantFocusable.add(((PaIdentiteTiers) 
				findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getBtnReinitialiser());
		listeComposantFocusable.add(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getBtnAfficher());

		
		listeComposantFocusable.add(vue.getPaBtnAvecAssistant().getPaBtnAssitant().getBtnSuivant());

		listeComposantFocusable.add(vue.getPaBtnAvecAssistant().getPaBtn().getBtnEnregistrer());
		listeComposantFocusable.add(vue.getPaBtnAvecAssistant().getPaBtn().getBtnInserer());
		listeComposantFocusable.add(vue.getPaBtnAvecAssistant().getPaBtn().getBtnModifier());
		listeComposantFocusable.add(vue.getPaBtnAvecAssistant().getPaBtn().getBtnSupprimer());
		listeComposantFocusable.add(vue.getPaBtnAvecAssistant().getPaBtn().getBtnFermer());
		listeComposantFocusable.add(vue.getPaBtnAvecAssistant().getPaBtn().getBtnAnnuler());
		listeComposantFocusable.add(vue.getPaBtnAvecAssistant().getPaBtn().getBtnImprimer());

		listeComposantFocusable.add(vue.getPaBtnAvecAssistant().getPaBtnAssitant().getBtnPrecedent());
		
		listeComposantFocusable.add(vue.getBtnFicheTiers());
		listeComposantFocusable.add(vue.getBtnGenDoc());
		listeComposantFocusable.add(vue.getBtnGenModel());
		listeComposantFocusable.add(vue.getBtnAutres());

		if (mapInitFocus == null)
			mapInitFocus = new LinkedHashMap<ModeObjet.EnumModeObjet, Control>();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION, vue.getTfCODE_DOCUMENT());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue.getTfCODE_DOCUMENT());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION, vue.getTfCODE_DOCUMENT());

		activeModifytListener();
		
		try {
			final KeyStroke keyStroke = KeyStroke.getInstance("Ctrl+Space");


			vue.getTfCODE_DOCUMENT().addFocusListener(new FocusAdapter() {
				public void focusGained(FocusEvent e) {
					codeDocumentContentProposal.
					setContentProposalProvider(contentProposalProviderDocument());
				}
			});

			vue.getTfCODE_TIERS().addFocusListener(new FocusAdapter() {
				public void focusGained(FocusEvent e) {
					tiersContentProposal
					.setContentProposalProvider(contentProposalProviderTiers());
				}
			});

			codeDocumentContentProposal = new ContentProposalAdapter(vue
					.getTfCODE_DOCUMENT(), new TextContentAdapter(),
					contentProposalProviderDocument(), keyStroke, null);
			codeDocumentContentProposal
			.setFilterStyle(ContentProposalAdapter.FILTER_NONE);
			codeDocumentContentProposal
			.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);
			codeDocumentContentProposal
			.addContentProposalListener(new IContentProposalListener() {
				public void proposalAccepted(IContentProposal proposal) {
					try {
						desactiveModifyListener();
						remonterDocument(proposal.getContent());
						activeModifytListener();
					} catch (Exception e) {
						logger.error("", e);
					}
				}
			});

			tiersContentProposal = new ContentProposalAdapter(vue
					.getTfCODE_TIERS(), new TextContentAdapter(),
					contentProposalProviderTiers(), keyStroke, null);
			tiersContentProposal
			.setFilterStyle(ContentProposalAdapter.FILTER_NONE);
			tiersContentProposal
			.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);
			tiersContentProposal
			.addContentProposalListener(new IContentProposalListener() {
				public void proposalAccepted(IContentProposal proposal) {
					try {
						ctrlUnChampsSWT(vue.getTfCODE_TIERS());
						changementTiers(true);
					} catch (Exception e) {
						logger.error("", e);
					}
				}
			});

		} catch (Exception e) {
			logger.error("", e);
		}
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
		mapCommand.put(C_COMMAND_DOCUMENT_REINITIALISER_ID, handlerReinitialiser);
		mapCommand.put(C_COMMAND_DOCUMENT_REINIT_ADR_FACT_ID, handlerReinitAdrFact);
		mapCommand.put(C_COMMAND_DOCUMENT_REINIT_ADR_LIV_ID, handlerReinitAdrLiv);
		mapCommand.put(C_COMMAND_DOCUMENT_REINIT_CPAIEMENT_ID, handlerReinitCPaiement);
		mapCommand.put(C_COMMAND_DOCUMENT_APPLIQUER_CPAIEMENT_ID, handlerAppliquerCPaiement);
		mapCommand.put(C_COMMAND_GLOBAL_SELECTION_ID, handlerSelection);
		mapCommand.put(C_COMMAND_DOCUMENT_REINIT_INFOSTIERS_ID, handlerReinitInfosTiers);
		mapCommand.put(C_COMMAND_DOCUMENT_CREATEDOC_ID, handlerCreateDoc);
		mapCommand.put(C_COMMAND_DOCUMENT_CREATEMODELE_ID, handlerCreateModele);
		mapCommand.put(C_COMMAND_DOCUMENT_AFFICHER_TIERS_ID, handlerafficherTiers);

		
		mapCommand.put(C_COMMAND_GLOBAL_PRECEDENT_ID, handlerPrecedent);
		mapCommand.put(C_COMMAND_GLOBAL_SUIVANT_ID, handlerSuivant);


		initFocusCommand(String.valueOf(this.hashCode()),
				listeComposantFocusable, mapCommand);
		

		if (mapActions == null)
			mapActions = new LinkedHashMap<Button, Object>();

		mapActions.put(vue.getPaBtnAvecAssistant().getPaBtn().getBtnAnnuler(),
				C_COMMAND_GLOBAL_ANNULER_ID);
		mapActions.put(vue.getPaBtnAvecAssistant().getPaBtn()
				.getBtnEnregistrer(), C_COMMAND_GLOBAL_ENREGISTRER_ID);
		mapActions.put(vue.getPaBtnAvecAssistant().getPaBtn().getBtnInserer(),
				C_COMMAND_GLOBAL_INSERER_ID);
		mapActions.put(vue.getPaBtnAvecAssistant().getPaBtn().getBtnModifier(),
				C_COMMAND_GLOBAL_MODIFIER_ID);
		mapActions.put(
				vue.getPaBtnAvecAssistant().getPaBtn().getBtnSupprimer(),
				C_COMMAND_GLOBAL_SUPPRIMER_ID);
		mapActions.put(vue.getPaBtnAvecAssistant().getPaBtn().getBtnFermer(),
				C_COMMAND_GLOBAL_FERMER_ID);
		mapActions.put(vue.getPaBtnAvecAssistant().getPaBtn().getBtnImprimer(),
				C_COMMAND_GLOBAL_IMPRIMER_ID);
		
		mapActions.put(vue.getBtnFicheTiers(),C_COMMAND_DOCUMENT_AFFICHER_TIERS_ID);
		mapActions.put(vue.getBtnGenDoc(),C_COMMAND_DOCUMENT_CREATEDOC_ID);
		mapActions.put(vue.getBtnGenModel(),C_COMMAND_DOCUMENT_CREATEMODELE_ID);
		
		mapActions.put(vue.getPaBtnAvecAssistant().getPaBtnAssitant()
				.getBtnPrecedent(), C_COMMAND_GLOBAL_PRECEDENT_ID);
		mapActions.put(vue.getPaBtnAvecAssistant().getPaBtnAssitant()
				.getBtnSuivant(), C_COMMAND_GLOBAL_SUIVANT_ID);
		
		mapActions.put(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl()).getBtnReinitialiser(),C_COMMAND_DOCUMENT_REINIT_ADR_FACT_ID);

		mapActions.put(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl()).getBtnReinitialiser(),C_COMMAND_DOCUMENT_REINIT_ADR_LIV_ID);

//		mapActions.put(((PaInfosCondPaiement) findExpandIem(vue.getExpandBar(), paInfosCondPaiement).getControl()).getBtnReinitialiser(),C_COMMAND_DOCUMENT_REINIT_CPAIEMENT_ID);
//		mapActions.put(((PaInfosCondPaiement) findExpandIem(vue.getExpandBar(), paInfosCondPaiement).getControl()).getBtnAppliquer(),C_COMMAND_DOCUMENT_APPLIQUER_CPAIEMENT_ID);
		
		mapActions.put(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl()).getBtnReinitialiser(),C_COMMAND_DOCUMENT_REINIT_INFOSTIERS_ID);
		mapActions.put(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl()).getBtnAfficher(),C_COMMAND_DOCUMENT_AFFICHER_TIERS_ID);

		
		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID,
				C_COMMAND_GLOBAL_REFRESH_ID ,C_COMMAND_DOCUMENT_CREATEDOC_ID, C_COMMAND_GLOBAL_SELECTION_ID};
		mapActions.put(null, tabActionsAutres);

		vue.getBtnAutres().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				popupMenuFormulaire.setVisible(true);
			}
		});

	}

	public PaEditorAcompteController getThis() {
		return this;
	}

	@Override
	public boolean onClose() throws ExceptLgr {
		// TODO contrôles avant sortie / gestion des modes /fermeture
		// perspective
		try{
			if (!vue.isDisposed() && !vue.getShell().isDisposed()) {
				switch (dao.getModeObjet().getMode()) {
				case C_MO_INSERTION:
				case C_MO_EDITION:
					boolean demande=!((IHMEnteteAcompte) selectedAcompte).factureEstVide(taDocument.dateDansExercice(new Date()));
					boolean reponse=true;
					if(demande)reponse=MessageDialog.openQuestion(vue.getShell(), "ATTENTION",
					"Voulez vous enregistrer les modifications en cours");
					if (demande && reponse) {
						if(demande)actEnregistrer();//pour enregistrer les objets ainsi que les codes générés
						initialisationEcran(null);
						return true;
					} else{					
						actAnnuler(false, false,false);//pour annuler les objets ainsi que les codes générés
						initialisationEcran(null);
						return true;
					}
				case C_MO_CONSULTATION:

					break;
				default:
					break;
				}
			}
			return true;
		}catch (Exception e) {
			logger.error("",e);
			return false;
		}
	}

	public void retourEcran(final RetourEcranEvent evt) {
		try {
			if (evt.getRetour() != null
					&& (evt.getSource() instanceof SWTPaAideControllerSWT && !evt
							.getRetour().getResult().equals(""))) {
				
				TaAdresseDAO daoAdresse = new TaAdresseDAO(getEm());
				TaAdresse taAdresse = null;
				
				if (getFocusCourantSWT() instanceof Text) {
					try {
						((Text) getFocusCourantSWT())
								.setText(((ResultAffiche) evt.getRetour())
										.getResult());
						if(getFocusAvantAideSWT().equals(vue.getTfCODE_TIERS())){
							TaTiers u = null;
							TaTiersDAO t = new TaTiersDAO(getEm());
							u = t.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
							t = null;
							taDocument.setTaTiers(u);
							String nomTiers=taDocument.getTaTiers().getNomTiers();
							((IHMEnteteAcompte)selectedAcompte).setLibelleDocument("Acompte N°"+taDocument.getCodeDocument()+" - "+nomTiers);
							
							if(vue.getCbTTC().isEnabled()){
								taDocument.setTtc(u.getTtcTiers());
								((IHMEnteteAcompte)selectedAcompte).setTtc(LibConversion.intToBoolean(taDocument.getTtc()));
							}

							changementTiers(true);
						}	
						if(getFocusAvantAideSWT().equals(vue.getTfCODE_DOCUMENT())){
							TaAcompte u = null;
							TaAcompteDAO t = new TaAcompteDAO(getEm());
							u = t.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
							t = null;
							taDocument=u;
							taDocument.setLegrain(true);
							try {
								remonterDocument(taDocument.getCodeDocument());
								//remonterDocument(vue.getTfCODE_FACTURE().getText());
							} catch (Exception e) {
								logger.error("", e);
							}							
						}							
						ctrlUnChampsSWT(getFocusCourantSWT());
					} catch (Exception e) {
						logger.error("", e);
						getFocusCourantSWT().forceFocus();
						vue.getLaMessage().setText(e.getMessage());
					}
				}
				
				if (focusDansAdresse(getFocusCourantSWT())) {
					String id = evt.getRetour().getIdResult();

					
					taAdresse = daoAdresse.findById(Integer.parseInt(id));
					mapperModelToUIAdresseInfosDocument.map(taAdresse, ((IHMAdresseInfosFacturation) selectedAdresseFact));
					
//					SWTAdresse swt_Adresse = SWT_IB_TA_ADRESSE.infosAdresse(id,
//							null);
//					((IHMAdresseInfosFacturation) selectedAdresse).setSWTAdresse(swt_Adresse);

				}
				if (focusDansAdresse_Liv(getFocusCourantSWT())) {
					String id = evt.getRetour().getIdResult();

					taAdresse = daoAdresse.findById(Integer.parseInt(id));
					mapperModelToUIAdresseLivInfosDocument.map(taAdresse, ((IHMAdresseInfosLivraison) selectedAdresseLiv));
//					SWTAdresse swt_Adresse = SWT_IB_TA_ADRESSE.infosAdresse(id,
//							null);
//					((IHMAdresseInfosLivraison) selectedAdresseLiv)
//							.setSWTAdresse(swt_Adresse);

				}
				if (focusDansCPaiment(getFocusCourantSWT())) {
					String id = evt.getRetour().getIdResult();

					TaCPaiementDAO taCPaiementDAO = new TaCPaiementDAO(getEm());
					TaCPaiement taCPaiement = taCPaiementDAO.findById(Integer.parseInt(id));
					mapperModelToUICPaiementInfosDocument.map(taCPaiement, ((IHMInfosCPaiement) selectedCPaiement));
					calculDateEcheance();
					
//					SWTCPaiement swt_CPaiement = SWT_IB_TA_CONDITIONPAIEMENT
//							.infosCondition(id, null);
//					((IHMInfosCPaiement) selectedCPaiement)
//							.setSWTCPaiement(swt_CPaiement);

				}
			}
//			if (this.getFocusCourantSWT().equals(vue.getTfCODE_TIERS())) {
//				changementTiers(true);
//			} else if (this.getFocusCourantSWT()
//					.equals(vue.getTfCODE_FACTURE())) {
//				try {
//					remonterDocument(vue.getTfCODE_FACTURE().getText());
//				} catch (Exception e) {
//					logger.error("", e);
//				}
//			}
//			//super.retourEcran(evt);
		} finally {
			if(evt.getSource() instanceof SWTPaAideControllerSWT)
				setActiveAide(false);			
			// ne pas enlever car sur demande de l'aide, je rends enable false
			// tous les boutons
			// donc au retour de l'aide, je reinitialise les boutons suivant
			// l'état du dataset
			// activeWorkenchPartCommands(true);
			// controllerLigne.activeWorkenchPartCommands(true);
		}
	}
	
	public void calculDateEcheance() {
		calculDateEcheance(false);
	}
	public void calculDateEcheance(Boolean appliquer) {
		if(((IHMEnteteAcompte)selectedAcompte).getIdDocument()==0 || appliquer) {
			Integer report = null;
			Integer finDeMois = null;
			if(((IHMInfosCPaiement)selectedCPaiement)!=null) { 
				if(((IHMInfosCPaiement)selectedCPaiement).getReportCPaiement()!=null)
					report = ((IHMInfosCPaiement)selectedCPaiement).getReportCPaiement();
				if(((IHMInfosCPaiement)selectedCPaiement).getFinMoisCPaiement()!=null)
					finDeMois = ((IHMInfosCPaiement)selectedCPaiement).getFinMoisCPaiement();
			}
			((IHMEnteteAcompte)selectedAcompte).setDateEchDocument(taDocument.calculDateEcheance(report, finDeMois));
		}
	}

	/**
	 * A appeler en cas de changement de codeTiers Communication avec les vues
	 * de la perspectives. Pas de rafraichissement des dataset contenus dans les
	 * vues
	 */
	private boolean changementTiers() {
		return changementTiers(false);
	}

	/**
	 * A appeler en cas de changement de codeTiers Communication avec les vues
	 * de la perspectives
	 * 
	 * @param refresh -
	 *            vrai ssi les dataset des vues doivent etre rafraichis
	 * @return
	 */
	private boolean changementTiers(boolean refresh) {
		logger.info("Changement de tiers");
		boolean res = true;
		boolean change=false;
		try {						
			initEtatComposant();
			taDocument.changementDeTiers();
			if(vue.getCbTTC().isEnabled()){
				vue.getCbTTC().setSelection(LibConversion.intToBoolean(taDocument.getTtc()));
			}
			if(vue.getComboLocalisationTVA().isEnabled() && taDocument.getTaTiers()!=null){//){
				//new TaTTvaDocDAO().findByCode(taDocument.getTaTiers().getTaTTvaDoc().getCodeTTvaDoc());
				TaTTvaDoc t = new TaTTvaDocDAO().findByCode(taDocument.getTaTiers().getTaTTvaDoc().getCodeTTvaDoc());
				viewerComboLocalisationTVA.setSelection(new StructuredSelection(t),true);
				//vue.getCbTTC().setSelection(taDocument.getTaTiers().getTaTTvaDoc());
				initLocalisationTVA();
			}
			////////////////////////////////////////////////////
			//boolean majAdr = false;
			if(((IHMEnteteAcompte)selectedAcompte).getIdDocument()!=0 && 
					((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
					|| (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0))) {
				if(!idTiersDocumentOriginal.equals(((IHMEnteteAcompte)selectedAcompte).getIdTiers())){
					//on change de tiers sur une facture deja enregistree
									
					//isa le 4 mars 2010
					mapperUIToModelDocumentVersInfosDoc.map(taDocument,taInfosDocument);
					////
					
					
					findExpandIem(vue.getExpandBar(), paInfosAdresseFact).setExpanded(true);
					//majAdr = MessageDialog.openQuestion(vue.getShell(), "Attention", "Mettre à jour les adresses");
					String message = "Les adresses et conditions du document ont été modifiées en fonction du nouveau tiers.";
					if(findStatusLineManager()!=null)
						findStatusLineManager().setMessage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_EXCLAMATION),message);
					change=true;

				}
			}
			////////////////////////////////////////////////////
			
			HashMap<String,String[]> map = new HashMap<String,String[]>();
			//map.put("a.taTiers."+Const.C_ID_TIERS,new String[]{"=",LibConversion.integerToString(taAcompte.getTaTiers().getIdTiers())});
			
//			map.put("a.taTiers."+Const.C_ID_TIERS,new String[]{"=",LibConversion.integerToString(taAcompte.getTaTiers().getIdTiers())});
//			map.put("a.taTAdr."+Const.C_CODE_T_ADR,new String[]{"=",typeAdresseLivraison});
//			map.put("",new String[]{"order by","a.taTAdr."+Const.C_CODE_T_ADR});
//			daoAdresseLiv.setParamWhereSQL(map);
			
			/*
			 * verifier que le type d'adresse existe
			 * verifier que le tiers à des adresses de ce tpe
			 * remplir les maps et changer les clauses where des DAO de modeles
			 * 
			 * remplir les modèles 
			 * avoir dans l'ordre :
			 * - adresse de l'infos facture si elle existe
			 * - adresse du type demandé s'il y en a
			 * - le reste des adresses du tiers
			 */
			initialisationDesInfosComplementaires(change,"");
			//calculDateEcheance();
			
			return res;
		} catch (Exception e) {
			logger.debug("", e);
			res = false;
		} finally {
//			ibTaTable.changementTiers = false;
			return res;
		}
	}

	public void initialisationDesInfosComplementaires(){
		initialisationDesInfosComplementaires(false,"");
	}
	
	public void initialisationDesInfosComplementaires(Boolean recharger,String typeARecharger){
		/*
		 * verifier que le type d'adresse existe
		 * verifier que le tiers à des adresses de ce tpe
		 * remplir les maps et changer les clauses where des DAO de modeles
		 * 
		 * remplir les modèles 
		 * avoir dans l'ordre :
		 * - adresse de l'infos facture si elle existe
		 * - adresse du type demandé s'il y en a
		 * - le reste des adresses du tiers
		 */		
		if(((IHMEnteteAcompte)selectedAcompte).getCodeDocument()!=null)
			taInfosDocument = daoInfosAcompte.findByCodeAcompte(((IHMEnteteAcompte)selectedAcompte).getCodeDocument());
		else
			taInfosDocument = new TaInfosAcompte();
		if(recharger)
			taDocument.setTaTiers(new TaTiersDAO(new JPABdLgr().getEntityManager()).findById(taDocument.getTaTiers().getIdTiers()));

		if(typeARecharger==Const.RECHARGE_ADR_FACT||typeARecharger=="")
			initialisationDesAdrFact(recharger);
		if(typeARecharger==Const.RECHARGE_ADR_LIV||typeARecharger=="")
			initialisationDesAdrLiv(recharger);
//		if(typeARecharger==Const.RECHARGE_C_PAIEMENT||typeARecharger=="")
//			initialisationDesCPaiement(recharger);
		if(typeARecharger==Const.RECHARGE_INFOS_TIERS||typeARecharger=="")
			initialisationDesInfosTiers(recharger);
//		TaTAdrDAO taTAdrDAO = new TaTAdrDAO(getEm());
//		//taInfosAcompte = daoInfosAcompte.findByCodeAcompte(taAcompte.getCodeAcompte());
//		if(((IHMEnteteAcompte)selectedAcompte).getCodeDocument()!=null)
//			taInfosDocument = daoInfosAcompte.findByCodeAcompte(((IHMEnteteAcompte)selectedAcompte).getCodeDocument());
//		else
//			taInfosDocument = new TaInfosAcompte();
//		//on vide les modeles
//		modelAdresseLiv.getListeObjet().clear();
//		modelAdresseFact.getListeObjet().clear();
//		modelCPaiement.getListeObjet().clear();
//
//
//		boolean leTiersADesAdresseLiv = false;
//		boolean leTiersADesAdresseFact = false;
//		if(taDocument.getTaTiers()!=null){
//			if(typeAdresseLivraison!=null && taTAdrDAO.findByCode(typeAdresseLivraison)!=null) { //le type d'adresse existe bien
//				leTiersADesAdresseLiv = taDocument.getTaTiers().aDesAdressesDuType(typeAdresseLivraison); //le tiers a des adresse de ce type
//			}
//			if(typeAdresseFacturation!=null && taTAdrDAO.findByCode(typeAdresseFacturation)!=null) { //le type d'adresse existe bien
//				leTiersADesAdresseFact = taDocument.getTaTiers().aDesAdressesDuType(typeAdresseFacturation); //le tiers a des adresse de ce type
//			}
//
//		
//		if(leTiersADesAdresseLiv) { 
//			//ajout des adresse de livraison au modele
//			for (TaAdresse taAdresse : taDocument.getTaTiers().getTaAdresses()) {
//				if(taAdresse.getTaTAdr().getCodeTAdr().equals(typeAdresseLivraison)){
//					modelAdresseLiv.getListeObjet().add(modelAdresseLiv.getMapperModelToUI().map(taAdresse, classModelAdresseLiv));
//				}
//			}
//		}
//		
//		if(leTiersADesAdresseFact) { 
//			//ajout des adresse de facturation au modele
//			for (TaAdresse taAdresse : taDocument.getTaTiers().getTaAdresses()) {
//				if(taAdresse.getTaTAdr().getCodeTAdr().equals(typeAdresseFacturation)){
//					modelAdresseFact.getListeObjet().add(modelAdresseFact.getMapperModelToUI().map(taAdresse, classModelAdresseFact));
//				}
//			}
//		}
//
//		//ajout des autres types d'adresse
//		for (TaAdresse taAdresse : taDocument.getTaTiers().getTaAdresses()) {
//			if(leTiersADesAdresseLiv && !taAdresse.getTaTAdr().getCodeTAdr().equals(typeAdresseLivraison)){
//				modelAdresseLiv.getListeObjet().add(modelAdresseLiv.getMapperModelToUI().map(taAdresse, classModelAdresseLiv));
//			}else if(leTiersADesAdresseFact && !taAdresse.getTaTAdr().getCodeTAdr().equals(typeAdresseFacturation)){
//				modelAdresseFact.getListeObjet().add(modelAdresseFact.getMapperModelToUI().map(taAdresse, classModelAdresseFact));
//			} else {
//				modelAdresseFact.getListeObjet().add(modelAdresseFact.getMapperModelToUI().map(taAdresse, classModelAdresseFact));
//				modelAdresseLiv.getListeObjet().add(modelAdresseLiv.getMapperModelToUI().map(taAdresse, classModelAdresseLiv));
//			}
//		}
//		
//		if(taDocument.getTaTiers().getTaCPaiement()==null)
//			modelCPaiement.getListeObjet().add(new IHMInfosCPaiement());
//		else
//			modelCPaiement.getListeObjet().add(modelCPaiement.getMapperModelToUI().map(taDocument.getTaTiers().getTaCPaiement(), classModelCPaiement));
//		}
//
//		//ajout de l'adresse de livraison inscrite dans l'infos facture
//		if(taInfosDocument!=null) {
//			
//			if(recharger && typeARecharger==Const.RECHARGE_ADR_LIV)
//				modelAdresseLiv.getListeObjet().add(mapperModelToUIInfosDocVersAdresseLiv.map(taInfosDocument, classModelAdresseLiv));
//			else
//				modelAdresseLiv.getListeObjet().addFirst(mapperModelToUIInfosDocVersAdresseLiv.map(taInfosDocument, classModelAdresseLiv));
//			if(recharger && typeARecharger==Const.RECHARGE_ADR_FACT)
//				modelAdresseFact.getListeObjet().add(mapperModelToUIInfosDocVersAdresseFact.map(taInfosDocument, classModelAdresseFact));
//			else
//				modelAdresseFact.getListeObjet().addFirst(mapperModelToUIInfosDocVersAdresseFact.map(taInfosDocument, classModelAdresseFact));
//			if(recharger && typeARecharger==Const.RECHARGE_C_PAIEMENT)
//				modelCPaiement.getListeObjet().add(mapperModelToUIInfosDocVersCPaiement.map(taInfosDocument, classModelCPaiement));
//			else
//				modelCPaiement.getListeObjet().addFirst(mapperModelToUIInfosDocVersCPaiement.map(taInfosDocument, classModelCPaiement));
//		}
//
//		
//		if (!modelAdresseFact.getListeObjet().isEmpty())
//			((IHMAdresseInfosFacturation) selectedAdresseFact).setIHMAdresse(modelAdresseFact.getListeObjet().getFirst());
//		else
//			((IHMAdresseInfosFacturation) selectedAdresseFact).setIHMAdresse(new IHMAdresseInfosFacturation());
//
//		if (!modelAdresseLiv.getListeObjet().isEmpty())
//			((IHMAdresseInfosLivraison) selectedAdresseLiv).setIHMAdresse(modelAdresseLiv.getListeObjet().getFirst());
//		else
//			((IHMAdresseInfosLivraison) selectedAdresseLiv).setIHMAdresse(new IHMAdresseInfosLivraison());
//
//		if (!modelCPaiement.getListeObjet().isEmpty())
//			((IHMInfosCPaiement) selectedCPaiement).setIHMInfosCPaiement(modelCPaiement.getListeObjet().getFirst());
//		else
//			((IHMInfosCPaiement) selectedCPaiement).setIHMInfosCPaiement(new IHMInfosCPaiement());
//		
	}
	
	public void initialisationDesAdrFact(Boolean recharger){
		TaTAdrDAO taTAdrDAO = new TaTAdrDAO(getEm());

		//on vide les modeles
		modelAdresseFact.getListeObjet().clear();

		boolean leTiersADesAdresseFact = false;
		if(taDocument.getTaTiers()!=null){
			if(typeAdresseFacturation!=null && taTAdrDAO.findByCode(typeAdresseFacturation)!=null) { //le type d'adresse existe bien
				leTiersADesAdresseFact = taDocument.getTaTiers().aDesAdressesDuType(typeAdresseFacturation); //le tiers a des adresse de ce type
			}	
		
		if(leTiersADesAdresseFact) { 
			//ajout des adresse de facturation au modele
			for (TaAdresse taAdresse : taDocument.getTaTiers().getTaAdresses()) {
				if(taAdresse.getTaTAdr()!=null && taAdresse.getTaTAdr().getCodeTAdr().equals(typeAdresseFacturation)){
					modelAdresseFact.getListeObjet().add(modelAdresseFact.getMapperModelToUI().map(taAdresse, classModelAdresseFact));
				}
			}
		}
		//ajout des autres types d'adresse
		for (TaAdresse taAdresse : taDocument.getTaTiers().getTaAdresses()) {
			if(leTiersADesAdresseFact && taAdresse.getTaTAdr()!=null && !taAdresse.getTaTAdr().getCodeTAdr().equals(typeAdresseFacturation)){
				modelAdresseFact.getListeObjet().add(modelAdresseFact.getMapperModelToUI().map(taAdresse, classModelAdresseFact));
			} else {
				modelAdresseFact.getListeObjet().add(modelAdresseFact.getMapperModelToUI().map(taAdresse, classModelAdresseFact));
			}
		}
		}
		//ajout de l'adresse de livraison inscrite dans l'infos facture
		if(taInfosDocument!=null) {
			if(recharger )
				modelAdresseFact.getListeObjet().add(mapperModelToUIInfosDocVersAdresseFact.map(taInfosDocument, classModelAdresseFact));
			else
				modelAdresseFact.getListeObjet().addFirst(mapperModelToUIInfosDocVersAdresseFact.map(taInfosDocument, classModelAdresseFact));
		}
		
		if (!modelAdresseFact.getListeObjet().isEmpty())
			((IHMAdresseInfosFacturation) selectedAdresseFact).setIHMAdresse(modelAdresseFact.getListeObjet().getFirst());
		else
			((IHMAdresseInfosFacturation) selectedAdresseFact).setIHMAdresse(new IHMAdresseInfosFacturation());
				
	}

	public void initialisationDesInfosTiers(Boolean recharger){
		IHMIdentiteTiers ihmInfosTiers=new IHMIdentiteTiers();
		if(taDocument.getTaTiers()!=null) {
			if(taInfosDocument!=null && !recharger)
				new LgrDozerMapper<TaInfosAcompte, IHMIdentiteTiers>().map(taInfosDocument, ihmInfosTiers);					
			else
				new LgrDozerMapper<TaTiers, IHMIdentiteTiers>().map(taDocument.getTaTiers(), ihmInfosTiers);
			//rajout des infos non contenues dans taInfosDocument
			if(taDocument.getTaTiers().getTaTelephone()!=null)
				ihmInfosTiers.setNumeroTelephone(taDocument.getTaTiers().getTaTelephone().getNumeroTelephone());
			if(taDocument.getTaTiers().getTaEmail()!=null)
				ihmInfosTiers.setAdresseEmail(taDocument.getTaTiers().getTaEmail().getAdresseEmail());
			if(taDocument.getTaTiers().getTaWeb()!=null)
				ihmInfosTiers.setAdresseWeb(taDocument.getTaTiers().getTaWeb().getAdresseWeb());
			if(taDocument.getTaTiers().getTaCompl()!=null)
				ihmInfosTiers.setSiretCompl(taDocument.getTaTiers().getTaCompl().getSiretCompl());
			if(taDocument.getTaTiers().getTaCommentaire()!=null)
				ihmInfosTiers.setCommentaire(taDocument.getTaTiers().getTaCommentaire().getCommentaire());
		}
		if (ihmInfosTiers!=null)
			((IHMIdentiteTiers) selectedInfosTiers).setIHMIdentiteTiers(ihmInfosTiers);
		else
			((IHMIdentiteTiers) selectedInfosTiers).setIHMIdentiteTiers(new IHMIdentiteTiers());
		if(taInfosDocument!=null)
			new LgrDozerMapper<IHMIdentiteTiers,TaInfosAcompte >().map( ihmInfosTiers,taInfosDocument);
	}

	public void initialisationDesAdrLiv(Boolean recharger){

		TaTAdrDAO taTAdrDAO = new TaTAdrDAO(getEm());
		//on vide les modeles
		modelAdresseLiv.getListeObjet().clear();

		boolean leTiersADesAdresseLiv = false;
		if(taDocument.getTaTiers()!=null){
			if(typeAdresseLivraison!=null && taTAdrDAO.findByCode(typeAdresseLivraison)!=null) { //le type d'adresse existe bien
				leTiersADesAdresseLiv = taDocument.getTaTiers().aDesAdressesDuType(typeAdresseLivraison); //le tiers a des adresse de ce type
			}
		
		
		if(leTiersADesAdresseLiv) { 
			//ajout des adresse de livraison au modele
			for (TaAdresse taAdresse : taDocument.getTaTiers().getTaAdresses()) {
				if(taAdresse.getTaTAdr()!=null && taAdresse.getTaTAdr().getCodeTAdr().equals(typeAdresseLivraison)){
					modelAdresseLiv.getListeObjet().add(modelAdresseLiv.getMapperModelToUI().map(taAdresse, classModelAdresseLiv));
				}
			}
		}
		//ajout des autres types d'adresse
		for (TaAdresse taAdresse : taDocument.getTaTiers().getTaAdresses()) {
			if(leTiersADesAdresseLiv && taAdresse.getTaTAdr()!=null && !taAdresse.getTaTAdr().getCodeTAdr().equals(typeAdresseLivraison)){
				modelAdresseLiv.getListeObjet().add(modelAdresseLiv.getMapperModelToUI().map(taAdresse, classModelAdresseLiv));
			} else {
				modelAdresseLiv.getListeObjet().add(modelAdresseLiv.getMapperModelToUI().map(taAdresse, classModelAdresseLiv));
			}
		}
		}
		//ajout de l'adresse de livraison inscrite dans l'infos facture
		if(taInfosDocument!=null) {
			
			if(recharger )
				modelAdresseLiv.getListeObjet().add(mapperModelToUIInfosDocVersAdresseLiv.map(taInfosDocument, classModelAdresseLiv));
			else
				modelAdresseLiv.getListeObjet().addFirst(mapperModelToUIInfosDocVersAdresseLiv.map(taInfosDocument, classModelAdresseLiv));
		}		
		if (!modelAdresseLiv.getListeObjet().isEmpty())
			((IHMAdresseInfosLivraison) selectedAdresseLiv).setIHMAdresse(modelAdresseLiv.getListeObjet().getFirst());
		else
			((IHMAdresseInfosLivraison) selectedAdresseLiv).setIHMAdresse(new IHMAdresseInfosLivraison());
				
	}
	
//	public void initialisationDesCPaiement(Boolean recharger){
//		//on vide les modeles
//		modelCPaiement.getListeObjet().clear();
//
//		if(taDocument.getTaTiers()!=null){
//	
//		if(taDocument.getTaTiers().getTaCPaiement()==null)
//			modelCPaiement.getListeObjet().add(new IHMInfosCPaiement());
//		else
//			modelCPaiement.getListeObjet().add(modelCPaiement.getMapperModelToUI().map(taDocument.getTaTiers().getTaCPaiement(), classModelCPaiement));
//		}
//		//ajout de l'adresse de livraison inscrite dans l'infos facture
//		if(taInfosDocument!=null) {
//			
//			if(recharger )
//				modelCPaiement.getListeObjet().add(mapperModelToUIInfosDocVersCPaiement.map(taInfosDocument, classModelCPaiement));
//			else
//				modelCPaiement.getListeObjet().addFirst(mapperModelToUIInfosDocVersCPaiement.map(taInfosDocument, classModelCPaiement));
//		}		
//
//		if (!modelCPaiement.getListeObjet().isEmpty())
//			((IHMInfosCPaiement) selectedCPaiement).setIHMInfosCPaiement(modelCPaiement.getListeObjet().getFirst());
//		else
//			((IHMInfosCPaiement) selectedCPaiement).setIHMInfosCPaiement(new IHMInfosCPaiement());
//		
//	}
	
//	public void initialisationDesCPaiement(Boolean recharger) {
//		// on vide les modeles
//		modelCPaiement.getListeObjet().clear();
//		TaInfosAcompte taInfosDocument = null;
//		if (taDocument != null) {
//			if (taDocument.getCodeDocument()!=null&& taDocument.getCodeDocument() != "")
//				taInfosDocument = daoInfosAcompte
//						.findByCodeAcompte(taDocument.getCodeDocument());
//			else
//				taInfosDocument = new TaInfosAcompte();
//
//			TaTCPaiementDAO taTCPaiementDAO = new TaTCPaiementDAO(getEm());
//			TaCPaiement taCPaiementDoc = null;
//			if (taTCPaiementDAO.findByCode(TaTCPaiement.C_CODE_TYPE_ACOMPTE) != null
//					&& taTCPaiementDAO.findByCode(
//							TaTCPaiement.C_CODE_TYPE_ACOMPTE).getTaCPaiement() != null) {
//				taCPaiementDoc = taTCPaiementDAO.findByCode(
//						TaTCPaiement.C_CODE_TYPE_ACOMPTE).getTaCPaiement();
//			}
//			int report = 0;
//			int finDeMois = 0;
//
//
//				if (taDocument.getTaTiers() != null) {
//					if (taDocument.getTaTiers().getTaTPaiement() != null) {
//						if (taDocument.getTaTiers().getTaTPaiement()
//								.getReportTPaiement() != null)
//							report = taDocument.getTaTiers().getTaTPaiement()
//									.getReportTPaiement();
//						if (taDocument.getTaTiers().getTaTPaiement()
//								.getFinMoisTPaiement() != null)
//							finDeMois = taDocument.getTaTiers()
//									.getTaTPaiement().getFinMoisTPaiement();
//					}
//
//					if (taDocument.getTaTiers().getTaCPaiement() == null
//							|| (report != 0 || finDeMois != 0)) {
//						if (taCPaiementDoc == null
//								|| (report != 0 || finDeMois != 0)) {// alors on
//																		// met
//																		// au
//																		// moins
//																		// une
//																		// condition
//																		// vide
//							IHMInfosCPaiement ihm = new IHMInfosCPaiement();
//							ihm.setReportCPaiement(report);
//							ihm.setFinMoisCPaiement(finDeMois);
//							modelCPaiement.getListeObjet().add(ihm);
//						}
//					} else
//						modelCPaiement.getListeObjet().add(
//								modelCPaiement.getMapperModelToUI().map(
//										taDocument.getTaTiers()
//												.getTaCPaiement(),
//										classModelCPaiement));
//				}
//			
//
//			if (taCPaiementDoc != null  )
//				modelCPaiement.getListeObjet().add(
//						modelCPaiement.getMapperModelToUI().map(taCPaiementDoc,
//								classModelCPaiement));
//
//			// ajout de l'adresse de livraison inscrite dans l'infos facture
//			if (taInfosDocument != null) {
//				if (recharger)
//					modelCPaiement.getListeObjet().add(
//							mapperModelToUIInfosDocVersCPaiement.map(
//									taDocument.getTaInfosDocument(),
//									classModelCPaiement));
//				else
//					modelCPaiement.getListeObjet().addFirst(
//							mapperModelToUIInfosDocVersCPaiement.map(
//									taDocument.getTaInfosDocument(),
//									classModelCPaiement));
//			}
//		}
//		if (!modelCPaiement.getListeObjet().isEmpty()) {
//			((IHMInfosCPaiement) selectedCPaiement)
//					.setIHMInfosCPaiement(modelCPaiement.getListeObjet()
//							.getFirst());
//		} else {
//			((IHMInfosCPaiement) selectedCPaiement)
//					.setIHMInfosCPaiement(new IHMInfosCPaiement());
//		}
////		findExpandIem(vue.getExpandBar(), paInfosCondPaiement)
////				.setExpanded(!((IHMInfosCPaiement) selectedCPaiement).estVide());
//
//	}


	
	@Override
	protected void actInserer() throws Exception {
		if (!vue.isDisposed()) {
			try {
				if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
					if(pluginAcompte.getDefault().getPreferenceStore().getString(fr.legrain.acompte.preferences.PreferenceConstants.FIXE)==null
							||pluginAcompte.getDefault().getPreferenceStore().getString(fr.legrain.acompte.preferences.PreferenceConstants.FIXE)==""){
						PreferenceInitializer.initDefautProperties();
						PreferenceInitializer.remplieProperties();
					}
					setIhmOldEnteteAcompte();
					initialisationEcran("");
//					dao.getEntityManager().clear();
					taDocument = new TaAcompte(true);
					taDocument.setTaCompteBanque(new TaCompteBanqueDAO(getEm()).findByTiersEntreprise(taDocument.getTaTPaiement()));
					mapperModelToUI.map(taDocument, (IHMEnteteAcompte) selectedAcompte);
					((IHMEnteteAcompte) selectedAcompte).setCodeDocument(dao.genereCode());
					validateUIField(Const.C_CODE_DOCUMENT,((IHMEnteteAcompte) selectedAcompte).getCodeDocument());//permet de verrouiller le code genere
					((IHMEnteteAcompte) selectedAcompte).setCommentaire(pluginAcompte.getDefault().getPreferenceStore().getString(fr.legrain.acompte.preferences.PreferenceConstants.COMMENTAIRE));
					validateUIField(Const.C_CODE_DOCUMENT,((IHMEnteteAcompte) selectedAcompte).getCodeDocument());
					taDocument.setCodeDocument(((IHMEnteteAcompte) selectedAcompte).getCodeDocument());
					changementTiers(true);
					Date date = new Date();
					TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
					TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
					// si date inférieur à dateDeb dossier
					if (LibDate.compareTo(date, taInfoEntreprise.getDatedebInfoEntreprise()) == -1) {
						((IHMEnteteAcompte) selectedAcompte).setDateDocument(taInfoEntreprise.getDatedebInfoEntreprise());
//						((IHMEnteteAcompte) selectedAcompte).setDateEchDocument(taInfoEntreprise.getDatedebInfoEntreprise());
//						((IHMEnteteAcompte) selectedAcompte).setDateLivDocument(taInfoEntreprise.getDatedebInfoEntreprise());
					} else
						// si date supérieur à dateFin dossier
						if (LibDate.compareTo(date, taInfoEntreprise.getDatefinInfoEntreprise()) == 1) {
							((IHMEnteteAcompte) selectedAcompte).setDateDocument(taInfoEntreprise.getDatefinInfoEntreprise());
//							((IHMEnteteAcompte) selectedAcompte).setDateEchDocument(taInfoEntreprise.getDatefinInfoEntreprise());
//							((IHMEnteteAcompte) selectedAcompte).setDateLivDocument(taInfoEntreprise.getDatefinInfoEntreprise());
						} else {
							((IHMEnteteAcompte) selectedAcompte).setDateDocument(new Date());
//							((IHMEnteteAcompte) selectedAcompte).setDateEchDocument(new Date());
//							((IHMEnteteAcompte) selectedAcompte).setDateLivDocument(new Date());
						}
					((IHMEnteteAcompte) selectedAcompte).setCodeTiers("");
					((IHMEnteteAcompte) selectedAcompte).setIdAdresse(0);
					((IHMEnteteAcompte) selectedAcompte).setIdAdresseLiv(0);
					
					// TODO récupérer un code_t_paiement par defaut ou le premier
					// dans la table
					String typePaiementDefaut =DocumentPlugin.getDefault().getPreferenceStore().getString(fr.legrain.document.preferences.PreferenceConstants.TYPE_PAIEMENT_DEFAUT); 
					TaTPaiementDAO taTPaiementDAO = new TaTPaiementDAO(getEm());
					if(typePaiementDefaut!=null){
						TaTPaiement taTPaiement=null;
						try {
							taTPaiement = taTPaiementDAO.findByCode(typePaiementDefaut);
						} catch (Exception e) {
						}						
						/*
						 * Le champ Type de paiement n'est pas gere dans ce controlleur (non present dans l'interface pour cet onglet)
						 * donc les fonctions ctrlTousLesChampsAvantEnregistrementSWT() ou validateUI() ne permetent pas d'initialiser
						 * l'objet correctement. Il faut le faire explicitement. 
						 */
						if(taTPaiement!=null) {
							((IHMEnteteAcompte) selectedAcompte).setCodeTPaiement(typePaiementDefaut);
							taDocument.setTaTPaiement(taTPaiement);
						}
					}
					mapperUIToModel.map(((IHMEnteteAcompte) selectedAcompte), taDocument);
					
					calculDateEcheance();
					
					fireChangementMaster(new ChangementMasterEntityEvent(this,taDocument));
					//dao.insertion(swtAcompte.setSWTAcompte(ihmEnteteAcompte));
					dao.inserer(taDocument);
					controllerLigne.actInserer();
					actRefreshAffectation();
					controllerTotaux.remonterDocument();
					controllerTotaux.actRefreshAffectation();
					ParamAfficheLAcompte paramAfficheLAcompte = new ParamAfficheLAcompte();
					paramAfficheLAcompte.setModeEcran(EnumModeObjet.C_MO_INSERTION);
					//paramAfficheLAcompte.setIdAcompte(swtAcompte.getEntete().getCODE());
					paramAfficheLAcompte.setIdAcompte(taDocument.getCodeDocument());
					paramAfficheLAcompte.setInitFocus(false);
					
				}

			} catch (ExceptLgr e1) {
				vue.getLaMessage().setText(LibChaine.lgrStringNonNull(e1.getMessage()));
				logger.error("Erreur : actionPerformed", e1);
			} catch (Exception e1) {
				vue.getLaMessage().setText(LibChaine.lgrStringNonNull(e1.getMessage()));
				logger.error("Erreur : actionPerformed", e1);
			} finally {
				initEtatComposant();
				initFocusSWT(dao,mapInitFocus);
				initEtatBouton(true);
			}
		}
	}

	@Override
	protected void actModifier() throws Exception {
		boolean continuer=true;
		try {
			if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
				//gestion document dans exercice
				if(taDocument.dateDansExercice(taDocument.getDateDocument()).compareTo(taDocument.getDateDocument())!=0){
					MessageDialog.openError(vue.getShell(),//
							MessagesEcran.getString("Message.Attention"),
							MessagesEcran.getString("Document.Message.HorsExercice"));
					continuer=false;
				}

				if(continuer){
				
				if(isUtilise())MessageDialog.openInformation(vue.getShell(), fr.legrain.tiers.ecran.MessagesEcran
						.getString("Message.Attention"),"Cet acompte a été affecté à une ou plusieurs factures, " +
				"vous devez supprimer les affectations avec ces factures avant de pouvoir le modifier.");
				else{
						if(setIhmOldEnteteDocumentRefresh())
							dao.modifier(taDocument);
				}
			}
			}
			if(dao.dataSetEnModif())
				fireChangementMaster(new ChangementMasterEntityEvent(this,taDocument,true));
			initEtatBouton(false);
		} catch (Exception e1) {
			logger.error("Erreur : actionPerformed", e1);
			if(e1.getMessage()!=null)
				vue.getLaMessage().setText(e1.getMessage());
		}
	}

	@Override
	protected void actSupprimer() throws Exception {
		boolean verrouLocal = VerrouInterface.isVerrouille();
		VerrouInterface.setVerrouille(true);
		EntityTransaction transaction = dao.getEntityManager().getTransaction();
		try {
			if(isUtilise())MessageDialog.openInformation(vue.getShell(), fr.legrain.tiers.ecran.MessagesEcran
					.getString("Message.Attention"),"Cet acompte a été affecté à une ou plusieurs factures, " +
							"vous devez supprimer les affectations avec ces factures avant de pouvoir le supprimer.");
			else			if (MessageDialog.openConfirm(vue.getShell(),
							MessagesEcran.getString("Message.Attention"),
							MessagesEcran.getString("Document.Message.Supprimer"))) {

				dao.begin(transaction);
				TaAcompte taDocument = dao.findById(((IHMEnteteAcompte) selectedAcompte).getIdDocument());
				dao.supprimer(taDocument);
				dao.commit(transaction);
//				if(listeDocument.contains(taDocument))
				removeCodeDocument(taDocument);
//					listeDocument.remove(taDocument);
				taDocument=null;
				dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
				initialisationEcran(null);
				actInserer();
			}
		} catch (ExceptLgr e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		} finally {
			if(transaction!=null && transaction.isActive()) {
				transaction.rollback();
			}
			initEtatBouton(true);
			VerrouInterface.setVerrouille(verrouLocal);
		}
	}
	public boolean isUtilise(){
		return (((IHMEnteteAcompte)selectedAcompte).getIdDocument()!=null && 
				!dao.recordModifiable(dao.getNomTable(),
						((IHMEnteteAcompte)selectedAcompte).getIdDocument()))||
						(taDocument!=null&&!dao.autoriseModification(taDocument));		
	}

	@Override
	protected void actFermer() throws Exception {
		// (controles de sortie et fermeture de la fenetre) => onClose()
		if (onClose()) {
			closeWorkbenchPart();
		}
	}

	@Override
	protected void actAnnuler() throws Exception {
		actAnnuler(false,true,true);
	}

	protected void actAnnuler(boolean annulationForcee,boolean insertion,boolean messageAffiche) throws Exception {
		VerrouInterface.setVerrouille(true);
		if (!vue.isDisposed() && !vue.getShell().isDisposed()) {
			String codeAcompte = vue.getTfCODE_DOCUMENT().getText();
			boolean message = !silencieu && messageAffiche && !((IHMEnteteAcompte) selectedAcompte).factureEstVide(taDocument.dateDansExercice(new Date()));
			boolean repondu = true;
			boolean dejaFermer = false;
			setTypeAdresseFacturation(DocumentPlugin.getDefault().getPreferenceStore().getString(fr.legrain.document.preferences.PreferenceConstants.TYPE_ADRESSE_FACTURATION));
			setTypeAdresseLivraison(DocumentPlugin.getDefault().getPreferenceStore().getString(fr.legrain.document.preferences.PreferenceConstants.TYPE_ADRESSE_BONLIV));
			try {
				taDocument.setLegrain(false);
//				if (!controllerLigne.focusDansLigne() || !controllerLigne.getDao().dataSetEnModif()) {
					boolean hasAnnuler = false;
					switch (dao.getModeObjet().getMode()) {
					case C_MO_INSERTION:
					case C_MO_EDITION:
						switch (controllerLigne.getDao().getModeObjet().getMode()) {
						case C_MO_INSERTION:
						case C_MO_EDITION:
							if (message)
								repondu = (MessageDialog.openQuestion(vue.getShell(),
												MessagesEcran.getString("Message.Attention"),
												MessagesEcran.getString("Document.Message.Annuler")));
							if (repondu) {
								controllerLigne.forceAnnuler = true;
								controllerLigne.actAnnuler();
								dao.annulerCodeGenere(((IHMEnteteAcompte) selectedAcompte).getCodeDocument(),dao.getChampGenere());
								dao.annulerCodeGenere(codeAcompte,dao.getChampGenere());
								taDocument.setLegrain(false);
								taDocument=dao.annulerT(taDocument);
								hideDecoratedFields();

								if (vue.getTfCODE_DOCUMENT().isFocusControl()
										&& !message && !annulationForcee) {
									// si facture vide avant annulation
									// et que l'on veut fermer
									actFermer();
									dejaFermer = true;
								} else {
									initialisationEcran(codeAcompte);
									if (insertion) actInserer();
									hasAnnuler = true;
								}
							}
							break;
						}

						if (message && !hasAnnuler
								&& controllerLigne.getDao()
										.getModeObjet().getMode() == EnumModeObjet.C_MO_CONSULTATION)
							repondu = MessageDialog
									.openQuestion(vue.getShell(),
											MessagesEcran.getString("Message.Attention"),
											MessagesEcran.getString("Document.Message.Annuler"));
						if (!hasAnnuler
								&& repondu
								&& controllerLigne.getDao()
										.getModeObjet().getMode() == EnumModeObjet.C_MO_CONSULTATION) {
							dao.annulerCodeGenere(((IHMEnteteAcompte) selectedAcompte).getCodeDocument(),dao.getChampGenere());
							dao.annulerCodeGenere(codeAcompte,dao.getChampGenere());
							taDocument=dao.annulerT(taDocument);
							if (!dejaFermer) {
								initialisationEcran(codeAcompte);
								if (insertion) actInserer();
							}
							hasAnnuler = true;
						} else if (!hasAnnuler && repondu) {
							dao.annulerCodeGenere(((IHMEnteteAcompte) selectedAcompte).getCodeDocument(),dao.getChampGenere());
							dao.annulerCodeGenere(codeAcompte,dao.getChampGenere());
							taDocument=dao.annulerT(taDocument);
							initialisationEcran(codeAcompte);
							hasAnnuler = true;
						}
						break;
					case C_MO_CONSULTATION:
						dao.annulerCodeGenere(((IHMEnteteAcompte) selectedAcompte).getCodeDocument(),dao.getChampGenere());
						dao.annulerCodeGenere(codeAcompte,dao.getChampGenere());

						if (vue.getTfCODE_DOCUMENT().isFocusControl() && !message) {// si facture vide avant annulation
							actFermer();
							dejaFermer = true;
						} else {
							initialisationEcran(codeAcompte);
							if (insertion) actInserer();
						}

						hasAnnuler = true;
						break;
					default:
						break;
					}
					if (!dejaFermer) {
						initEtatBouton(true);
					}
//				}
			} finally {
				PlatformUI.getWorkbench().getDisplay().asyncExec(
						new Runnable() {
							public void run() {
								VerrouInterface.setVerrouille(false);
							}
						});
			}
		}

	}

	@Override
	protected void actImprimer() throws Exception {
		String simpleNameClass = TaAcompte.class.getSimpleName();
//#JPA
//		Collection<TaAcompte> collectionTaAcompte = dao.selectAll();
		Collection<TaAcompte> collectionTaAcompte = new LinkedList<TaAcompte>();
//		taAcompte.calculeTvaEtTotaux();
		collectionTaAcompte.add(taDocument);
		ConstEdition constEdition = new ConstEdition(getEm()); 
		constEdition.setFlagEditionMultiple(true);
		
//		impressionAcompte.setObject(taDocument);
//		impressionAcompte.setConstEdition(constEdition);
//		if(impressionAcompte.getCollection() != null){
//			impressionAcompte.getCollection().clear();
//		}
//		impressionAcompte.setCollection(collectionTaAcompte);
		
		Bundle bundleCourant = pluginAcompte.getDefault().getBundle();
		String namePlugin = bundleCourant.getSymbolicName();
		
//		impressionAcompte.imprimerSelection(taDocument.getIdDocument(),taDocument.getCodeDocument(),true,
//				ConstEdition.FICHE_FILE_REPORT_FACTURE,namePlugin,TaAcompte.class.getSimpleName());
		//impression.imprimerSelection(taAcompte.getIdDocument(),taAcompte.getCodeDocument(),true,ConstEdition.FICHE_FILE_REPORT_FACTURES,"Article",TaAcompte.class.getSimpleName());
		
		/** 01/03/2010 modifier les editions (zhaolin) **/
		baseImpressionEdition.setObject(taDocument);
		baseImpressionEdition.setConstEdition(constEdition);
		baseImpressionEdition.setCollection(collectionTaAcompte);
		baseImpressionEdition.setIdEntity(taDocument.getIdDocument());
		
		//information pour l'envoie de document par email
		baseImpressionEdition.setInfosEmail(null);
		String email = null;
		if(taDocument.getTaTiers().getTaEmail()!=null && taDocument.getTaTiers().getTaEmail().getAdresseEmail()!=null) {
			email = taDocument.getTaTiers().getTaEmail().getAdresseEmail();
		}
		baseImpressionEdition.setInfosEmail(
				new InfosEmail(
						new String[]{email},
						null,
						taDocument.getCodeDocument()+".pdf")
				);
		
		//information pour l'envoie de document par fax
		baseImpressionEdition.setInfosFax(null);
		String fax = null;
		if(!taDocument.getTaTiers().findNumeroFax().isEmpty()) {
			fax = taDocument.getTaTiers().findNumeroFax().get(0);
		}
		baseImpressionEdition.setInfosFax(
				new InfosFax(
						new String[]{fax},
						taDocument.getCodeDocument()+".pdf")
				);
		
		IPreferenceStore preferenceStore = pluginAcompte.getDefault().getPreferenceStore();
		baseImpressionEdition.impressionEdition(preferenceStore,simpleNameClass,/*true,*/null,
												namePlugin,ConstEdition.FICHE_FILE_REPORT_ACOMPTE, 
												true,false,true,false,false,false,"");
		/************************************************/
		
	}

	@Override
	protected boolean aideDisponible() {
		boolean result = false;
		switch ((PaEditorAcompteController.this.dao.getModeObjet().getMode())) {
		case C_MO_CONSULTATION:
			if (getFocusCourantSWT().equals(vue.getTfCODE_DOCUMENT())
					|| getFocusCourantSWT().equals(vue.getTfCODE_TIERS()))
				result = true;
			break;
		case C_MO_EDITION:
		case C_MO_INSERTION:
			if (getFocusCourantSWT().equals(vue.getTfCODE_TIERS())
					|| getFocusCourantSWT().equals(vue.getTfCODE_DOCUMENT())
			/* || getFocusCourantSWT().equals(vue.getTfCODE_T_PAIEMENT()) */)
				result = true;
			if (focusDansAdresse(getFocusCourantSWT())
					|| focusDansAdresse_Liv(getFocusCourantSWT())
					|| focusDansCPaiment(getFocusCourantSWT()))
				result = true;
			break;
		default:
			break;
		}

		return result;
	}

	protected void actSelection() throws Exception {
		try{
			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().
			getActivePage().openEditor(new fr.legrain.visualisation.editor.EditorInputSelectionVisualisation(), 
					fr.legrain.visualisation.editor.EditorSelectionVisualisation.ID);
			LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);

			ParamAfficheVisualisation paramAfficheSelectionVisualisation = new ParamAfficheVisualisation();
			paramAfficheSelectionVisualisation.setEcranAppelant(getThis());
			paramAfficheSelectionVisualisation.setModule("Acompte");
			paramAfficheSelectionVisualisation.setNomClassController(nomClassController);
			paramAfficheSelectionVisualisation.setNomRequete(Const.C_NOM_VU_ACOMPTE);

			((LgrEditorPart)e).setPanel(((LgrEditorPart)e).getControllerSelection().getVue());
			((LgrEditorPart)e).getControllerSelection().configPanel(paramAfficheSelectionVisualisation);

		}catch (Exception e) {
			logger.error("",e);
		}	
	}
	
	@Override
	protected void actPrecedent() throws Exception {
		ChangementDePageEvent evt = new ChangementDePageEvent(this,
				ChangementDePageEvent.PRECEDENT);
		fireChangementDePage(evt);
	}

	@Override
	protected void actSuivant() throws Exception {
		if (vue.getTfCODE_TIERS().equals(getFocusCourantSWT())) {
			vue.getDateTimeDATE_DOCUMENT().forceFocus();
		}
		ChangementDePageEvent evt = new ChangementDePageEvent(this,
				ChangementDePageEvent.SUIVANT);
		fireChangementDePage(evt);
	}

	@Override
	protected void actAide() throws Exception {
		actAide(null);
	}

	//AFAIRE
	@Override
	protected void actAide(String message) throws Exception {
		if (aideDisponible()) {
//			boolean affichageAideRemplie = DocumentPlugin.getDefault().getPreferenceStore().getBoolean(fr.legrain.document.preferences.PreferenceConstants.TYPE_AFFICHAGE_AIDE);
			boolean affichageAideRemplie = LgrMess.isAfficheAideRemplie();
			setActiveAide(true);
			boolean verrouLocal = VerrouInterface.isVerrouille();
			VerrouInterface.setVerrouille(true);
			try {
				vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
				ParamAfficheAideRechercheSWT paramAfficheAideRecherche = new ParamAfficheAideRechercheSWT();
//				paramAfficheAideRecherche.setDb(getThis().getIbTaTable().getFIBBase());
				paramAfficheAideRecherche.setMessage(message);
				// Creation de l'ecran d'aide principal
				Shell s = new Shell(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),LgrShellUtil.styleLgr);
				PaAideSWT paAide = new PaAideSWT(s, SWT.NONE);
				SWTPaAideControllerSWT paAideController = new SWTPaAideControllerSWT(paAide);
				/** ************************************************************ */
				LgrPartListener.getLgrPartListener().setLgrActivePart(null);
				IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new EditorInputAide(), EditorAide.ID);
				LgrPartListener.getLgrPartListener().setLgrActivePart(e);
				LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
				paAideController = new SWTPaAideControllerSWT(((EditorAide) e).getComposite());
				((LgrEditorPart) e).setController(paAideController);
				((LgrEditorPart) e).setPanel(((EditorAide) e).getComposite());
				/** ************************************************************ */
				JPABaseControllerSWTStandard controllerEcranCreation = null;
				ParamAffiche parametreEcranCreation = null;
				IEditorPart editorCreation = null;
				String editorCreationId = null;
				IEditorInput editorInputCreation = null;
				Shell s2 = new Shell(s, LgrShellUtil.styleLgr);
				
				
				switch ((PaEditorAcompteController.this.dao.getModeObjet().getMode())) {
				case C_MO_CONSULTATION:
					if (getFocusCourantSWT().equals(vue.getTfCODE_DOCUMENT())) {
						paramAfficheAideRecherche.setForceAffichageAideRemplie(false);
						editorCreationId = EditorAcompte.ID_EDITOR;
						editorInputCreation = new EditorInputAcompte();

//						SWT_IB_TA_FACTURE ibTaTableAcompte = new SWT_IB_TA_FACTURE(swtAcompte, false);
//						ibTaTableAcompte.getFIBQuery().setQuery(new QueryDescriptor(ibTaTableAcompte.getFIBBase(), Const.C_Debut_Requete+ Const.C_NOM_VU_FACTURE_REDUIT, true));
//						ibTaTableAcompte.ouvreDataset();
//						paramAfficheAideRecherche.setQuery(ibTaTableAcompte.getFIBQuery());
						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
						controllerEcranCreation = this;

						paramAfficheAideRecherche.setAfficheDetail(false);
						
						paramAfficheAideRecherche.setTypeEntite(TaAcompte.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_DOCUMENT);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_DOCUMENT().getText());
						paramAfficheAideRecherche.setControllerAppelant(PaEditorAcompteController.this);
						paramAfficheAideRecherche.setCleListeTitre("PaEditorAcompteController");
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideAcompte,TaAcompteDAO,TaAcompte>(dao,IHMAideAcompte.class));
						paramAfficheAideRecherche.setTypeObjet(IHMAideAcompte.class);
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_DOCUMENT);
						
						//permet de paramètrer l'affichage remplie ou non de l'aide

						try {
							dao.annulerCodeGenere(taDocument.getCodeDocument(), dao.getChampGenere());
//							ibTaTable.annulerCodeGenere(ibTaTable.getChamp_Obj(Const.C_CODE_FACTURE),Const.C_CODE_FACTURE);
						} catch (Exception ex) {
							logger.error(ex);
						}
					}
					if (getFocusCourantSWT().equals(vue.getTfCODE_TIERS())) {
						//permet de paramètrer l'affichage remplie ou non de l'aide
						PaTiersSWT paTiersSWT = new PaTiersSWT(s2, SWT.NULL);
						SWTPaTiersController swtPaTiersController = new SWTPaTiersController(paTiersSWT);
						paramAfficheAideRecherche.setForceAffichageAideRemplie(false);

						editorCreationId = TiersMultiPageEditor.ID_EDITOR;
						editorInputCreation = new EditorInputTiers();

						ParamAfficheTiers paramAfficheTiers = new ParamAfficheTiers();
						paramAfficheAideRecherche.setJPQLQuery(new TaTiersDAO(getEm()).getTiersActif());
						paramAfficheTiers.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheTiers.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaTiersController;
						parametreEcranCreation = paramAfficheTiers;

						paramAfficheAideRecherche.setTypeEntite(TaTiers.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_TIERS);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_TIERS().getText());
						paramAfficheAideRecherche.setControllerAppelant(getThis());
//						ModelTiers modelTiers = new ModelTiers(swtPaTiersController.getIbTaTable());
//						paramAfficheAideRecherche.setModel(modelTiers);
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTiers,TaTiersDAO,TaTiers>(SWTTiers.class,getEm()));
						paramAfficheAideRecherche.setTypeObjet(swtPaTiersController.getClassModel());
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_TIERS);
						
						/*
						 * Bug #1376
						 */
						if (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION) == 0) {
							actModifier();
						}
					}

					break;
				case C_MO_EDITION:
				case C_MO_INSERTION:
					if (getFocusCourantSWT().equals(vue.getTfCODE_TIERS())) {
						//permet de paramètrer l'affichage remplie ou non de l'aide

						PaTiersSWT paTiersSWT = new PaTiersSWT(s2, SWT.NULL);
						SWTPaTiersController swtPaTiersController = new SWTPaTiersController(paTiersSWT);
						paramAfficheAideRecherche.setForceAffichageAideRemplie(false);
						editorCreationId = TiersMultiPageEditor.ID_EDITOR;
						editorInputCreation = new EditorInputTiers();

						ParamAfficheTiers paramAfficheTiers = new ParamAfficheTiers();
						paramAfficheAideRecherche.setJPQLQuery(new TaTiersDAO(getEm()).getTiersActif());
						paramAfficheTiers.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheTiers.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaTiersController;
						parametreEcranCreation = paramAfficheTiers;

						paramAfficheAideRecherche.setTypeEntite(TaTiers.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_TIERS);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_TIERS().getText());
						paramAfficheAideRecherche.setControllerAppelant(getThis());
//						ModelTiers modelTiers = new ModelTiers(swtPaTiersController.getIbTaTable());
//						paramAfficheAideRecherche.setModel(modelTiers);
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTiers,TaTiersDAO,TaTiers>(SWTTiers.class,getEm()));
						paramAfficheAideRecherche.setTypeObjet(swtPaTiersController.getClassModel());
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_TIERS);
					}
					if (getFocusCourantSWT().equals(vue.getTfCODE_DOCUMENT())) {
						//permet de paramètrer l'affichage remplie ou non de l'aide
						paramAfficheAideRecherche.setForceAffichageAideRemplie(false);
						editorCreationId = EditorAcompte.ID_EDITOR;
						editorInputCreation = new EditorInputAcompte();

						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
						controllerEcranCreation = this;

						paramAfficheAideRecherche.setAfficheDetail(false);
						
						paramAfficheAideRecherche.setTypeEntite(TaAcompte.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_DOCUMENT);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_DOCUMENT().getText());
						paramAfficheAideRecherche.setControllerAppelant(PaEditorAcompteController.this);
						paramAfficheAideRecherche.setCleListeTitre("SWTPaEditorFactureController");
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideAcompte,TaAcompteDAO,TaAcompte>(dao,IHMAideAcompte.class));
						paramAfficheAideRecherche.setTypeObjet(IHMAideAcompte.class);
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_DOCUMENT);
					}
					if (focusDansAdresse(getFocusCourantSWT())
							|| focusDansAdresse_Liv(getFocusCourantSWT())) {
						PaAdresseSWT paAdresseSWT = new PaAdresseSWT(s2,SWT.NULL);
						SWTPaAdresseController swtPaAdresseController = new SWTPaAdresseController(paAdresseSWT);

//						editorCreationId = EditorAdresse.ID;
//						editorInputCreation = new EditorInputAdresse();
						
						paramAfficheAideRecherche.setForceAffichageAideRemplie(true);
						paramAfficheAideRecherche.setAfficheDetail(false);

						ParamAfficheAdresse paramAfficheAdresse = new ParamAfficheAdresse();
						paramAfficheAideRecherche.setJPQLQuery(new TaAdresseDAO(getEm()).getJPQLQuery());
						paramAfficheAdresse.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheAdresse.setEcranAppelant(paAideController);
						paramAfficheAdresse.setIdTiers(LibConversion.integerToString(taDocument.getTaTiers().getIdTiers()));
						
						controllerEcranCreation = swtPaAdresseController;
						parametreEcranCreation = paramAfficheAdresse;

						paramAfficheAideRecherche.setTypeEntite(TaAdresse.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_ADRESSE1_ADRESSE);
						paramAfficheAideRecherche.setDebutRecherche("");
						paramAfficheAideRecherche.setControllerAppelant(getThis());
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAdresse,TaAdresseDAO,TaAdresse>(taDocument.getTaTiers().getTaAdresses(),IHMAdresse.class,getEm()));	
						paramAfficheAideRecherche.setTypeObjet(IHMAdresse.class);
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_ADRESSE);
					}

					if (focusDansCPaiment(getFocusCourantSWT())) {
						PaConditionPaiementSWT paCPaiementSWT = new PaConditionPaiementSWT(s2, SWT.NULL);
						SWTPaConditionPaiementController swtPaConditionPaiementController = new SWTPaConditionPaiementController(paCPaiementSWT);

						editorCreationId = EditorConditionPaiement.ID;
						editorInputCreation = new EditorInputConditionPaiement();
						
						paramAfficheAideRecherche.setForceAffichageAideRemplie(true);

						paramAfficheAideRecherche.setAfficheDetail(false);
						
						ParamAfficheConditionPaiement paramAfficheConditionPaiement = new ParamAfficheConditionPaiement();
						paramAfficheAideRecherche.setJPQLQuery(new TaCPaiementDAO(getEm()).getJPQLQuery());
						paramAfficheConditionPaiement.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheConditionPaiement.setEcranAppelant(paAideController);
						paramAfficheConditionPaiement.setIdTiers(taDocument.getTaTiers().getIdTiers());
						
						controllerEcranCreation = swtPaConditionPaiementController;
						parametreEcranCreation = paramAfficheConditionPaiement;

						paramAfficheAideRecherche.setTypeEntite(TaCPaiement.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_C_PAIEMENT);
						paramAfficheAideRecherche.setDebutRecherche("");
						paramAfficheAideRecherche.setControllerAppelant(getThis());
						List<TaCPaiement> l = new ArrayList<TaCPaiement>();
//						l.add(taAcompte.getTaCPaiement());
						if(taDocument.getTaTiers()!=null)
							l.add(taDocument.getTaTiers().getTaCPaiement());	
						TaTCPaiementDAO taTCPaiementDAO = new TaTCPaiementDAO(getEm());
						if(taTCPaiementDAO.findByCode(TaTCPaiement.C_CODE_TYPE_ACOMPTE)!=null
								&& taTCPaiementDAO.findByCode(TaTCPaiement.C_CODE_TYPE_ACOMPTE).getTaCPaiement()!=null)
								l.add(taTCPaiementDAO.findByCode(TaTCPaiement.C_CODE_TYPE_ACOMPTE).getTaCPaiement());
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTCPaiement,TaCPaiementDAO,TaCPaiement>(l,SWTCPaiement.class,getEm()));

						paramAfficheAideRecherche.setTypeObjet(swtPaConditionPaiementController.getClassModel());
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_C_PAIEMENT);
					}

					break;
				default:
					break;
				}

				if (paramAfficheAideRecherche.getJPQLQuery() != null) {

					PaAideRechercheSWT paAideRecherche1 = new PaAideRechercheSWT(s, SWT.NULL);
					SWTPaAideRechercheControllerSWT paAideRechercheController1 = new SWTPaAideRechercheControllerSWT(paAideRecherche1);

					// Parametrage de la recherche
					paramAfficheAideRecherche.setFocusSWT(((PaAideRechercheSWT) paAideRechercheController1.getVue()).getTfChoix());
					paramAfficheAideRecherche.setRefCreationSWT(controllerEcranCreation);
					paramAfficheAideRecherche.setEditorCreation(editorCreation);
					paramAfficheAideRecherche.setEditorCreationId(editorCreationId);
					paramAfficheAideRecherche.setEditorInputCreation(editorInputCreation);
					paramAfficheAideRecherche.setParamEcranCreation(parametreEcranCreation);
					paramAfficheAideRecherche.setShellCreation(s2);
					paAideRechercheController1.configPanel(paramAfficheAideRecherche);
					// paramAfficheAideRecherche.setFocusDefaut(paAideRechercheController1.getVue().getTfChoix());

					// Ajout d'une recherche
					paAideController.addRecherche(paAideRechercheController1,paramAfficheAideRecherche.getTitreRecherche());

					// Parametrage de l'ecran d'aide principal
					ParamAfficheAideSWT paramAfficheAideSWT = new ParamAfficheAideSWT();
					ParamAfficheAide paramAfficheAide = new ParamAfficheAide();

					// enregistrement pour le retour de l'ecran d'aide
					paAideController.addRetourEcranListener(getThis());
					Control focus = vue.getShell().getDisplay().getFocusControl();
					// affichage de l'ecran d'aide principal (+ ses recherches)


					dbc.getValidationStatusMap().removeMapChangeListener(changeListener);
					//LgrShellUtil.afficheAideSWT(paramAfficheAide, null, paAide,paAideController, s);
					/*****************************************************************/
					paAideController.configPanel(paramAfficheAide);
					/*****************************************************************/
					dbc.getValidationStatusMap().addMapChangeListener(changeListener);
					// je rends enable false tous les boutons avant de passer
					// dans l'écran d'aide
					// pour ne pas que les actions de l'écran des factures
					// interfèrent ceux de l'écran d'aide
					// activeWorkenchPartCommands(false);
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
			verifCode();
			ctrlTousLesChampsAvantEnregistrementSWT(dbcAdresseFact);
			ctrlTousLesChampsAvantEnregistrementSWT(dbcAdresseLiv);
			ctrlTousLesChampsAvantEnregistrementSWT();
			
			initInfosAcompte();
			
			mapperUIToModelAdresseFactVersInfosDoc.map((IHMAdresseInfosFacturation) selectedAdresseFact, taInfosDocument);
			mapperUIToModelAdresseLivVersInfosDoc.map((IHMAdresseInfosLivraison) selectedAdresseLiv, taInfosDocument);
			mapperUIToModelCPaiementVersInfosDoc.map((IHMInfosCPaiement) selectedCPaiement, taInfosDocument);
			
			mapperUIToModel.map((IHMEnteteAcompte)selectedAcompte,taDocument);
		}
		return null;
	}
	
	public IStatus validateUIField(String nomChamp,Object value) {
		String validationContext = "ACOMPTE";
		try {
			IStatus s = null;

			if(nomChamp.equals(Const.C_CODE_TIERS)) {
				TaTiersDAO dao = new TaTiersDAO(getEm());
				
				dao.setModeObjet(getDao().getModeObjet());
				TaTiers f = new TaTiers();
				PropertyUtils.setSimpleProperty(f, nomChamp, value);
					s = dao.validate(f,nomChamp,validationContext);
				boolean change =(taDocument.getTaTiers()!=null && 
						!taDocument.getTaTiers().getCodeTiers().equals(f.getCodeTiers()));
				change=change||taDocument.getTaTiers()==null;
				if(change && !taDocument.rechercheSiMemeTiers(f))
				s=new Status(Status.ERROR,gestComBdPlugin.PLUGIN_ID,
						"Ce document est lié à des acomptes, vous ne pouvez pas modifier le tiers initial");

				if(s.getSeverity()!=IStatus.ERROR && change){
					f = dao.findByCode((String)value);
					taDocument.setTaTiers(f);
					String nomTiers=taDocument.getTaTiers().getNomTiers();
					((IHMEnteteAcompte)selectedAcompte).setLibelleDocument("Acompte N°"+taDocument.getCodeDocument()+" - "+nomTiers);					
					if(vue.getCbTTC().isEnabled()){
						taDocument.setTtc(f.getTtcTiers());
						((IHMEnteteAcompte)selectedAcompte).setTtc(LibConversion.intToBoolean(taDocument.getTtc()));
					}
				}
				dao = null;
			} else if(nomChamp.equals(Const.C_LIBELLE_DOCUMENT)) {
				if(value==null || value.equals("")) {
					String nomTiers="";
					if(taDocument.getTaTiers()!=null)
						nomTiers=taDocument.getTaTiers().getNomTiers();
					((IHMEnteteAcompte)selectedAcompte).setLibelleDocument("Acompte N°"+taDocument.getCodeDocument()+" - "+nomTiers);
				}
				s = new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
			} else if(nomChamp.equals(Const.C_TTC)) {
				
				s = new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
			}  else if(nomChamp.equals(Const.C_CODE_C_PAIEMENT)
					|| nomChamp.equals(Const.C_LIB_C_PAIEMENT)
					|| nomChamp.equals(Const.C_FIN_MOIS_C_PAIEMENT)
					|| nomChamp.equals(Const.C_REPORT_C_PAIEMENT)) {
				TaCPaiementDAO dao = new TaCPaiementDAO(getEm());
				
				dao.setModeObjet(getDao().getModeObjet());
				TaCPaiement f = new TaCPaiement();
				PropertyUtils.setSimpleProperty(f, nomChamp, value);
				s = dao.validate(f,nomChamp,validationContext);
				
//				if(s.getSeverity()!=IStatus.ERROR ){
//					f = dao.findByCode((String)value);
//					taAcompte.setTaCPaiement(f);
//				}
				dao = null;
			} else if(nomChamp.equals(Const.C_ADRESSE1_ADRESSE)
					|| nomChamp.equals(Const.C_ADRESSE2_ADRESSE)
					|| nomChamp.equals(Const.C_ADRESSE3_ADRESSE)
					|| nomChamp.equals(Const.C_CODEPOSTAL_ADRESSE)
					|| nomChamp.equals(Const.C_VILLE_ADRESSE)
					|| nomChamp.equals(Const.C_PAYS_ADRESSE)
					|| nomChamp.equals(Const.C_ADRESSE1_LIV)
					|| nomChamp.equals(Const.C_ADRESSE2_LIV)
					|| nomChamp.equals(Const.C_ADRESSE3_LIV)
					|| nomChamp.equals(Const.C_CODEPOSTAL_LIV)
					|| nomChamp.equals(Const.C_VILLE_LIV)
					|| nomChamp.equals(Const.C_PAYS_LIV)) {
				TaAdresseDAO dao = new TaAdresseDAO(getEm());
				
//				dao.setModeObjet(getDao().getModeObjet());
//				TaAdresse f = new TaAdresse();
//				PropertyUtils.setSimpleProperty(f, nomChamp, value);
//				s = dao.validate(f,nomChamp,validationContext);
				s = new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
				
//				if(s.getSeverity()!=IStatus.ERROR ){
//					f = dao.findByCode((String)value);
//					taAcompte.setTaCPaiement(f);
//				}
				dao = null;
			} else {
				boolean verrouilleModifCode = false;
				TaAcompte u = new TaAcompte(true);
				PropertyUtils.setSimpleProperty(u, nomChamp, value);
				if(((IHMEnteteAcompte) selectedAcompte).getIdDocument()!=null) {
					u.setIdDocument(((IHMEnteteAcompte) selectedAcompte).getIdDocument());
				}
				if(nomChamp.equals(Const.C_CODE_DOCUMENT)){
					//J'ai rajouté cette variable car lorsqu'on remonte un document et que l'on
					//est déjà en modif, il sort de la zone et fait une vérif du code qui existe déjà
					//et pour cause, on veut remonter un document existant, donc cette variable est initialisée
					//dans remonterDocument uniquement
					if(desactiveValidateCodeDocument){
						desactiveValidateCodeDocument=false;
						return new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
					}
					desactiveValidateCodeDocument=false;
					verrouilleModifCode = true;
				}else if(nomChamp.equals(Const.C_DATE_DOCUMENT)
						|| nomChamp.equals(Const.C_DATE_ECH_DOCUMENT)) {
					value=(dateDansPeriode((Date)value,nomChamp));
					PropertyUtils.setSimpleProperty(u, nomChamp, value);
//					s = new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
				}
				
				s = dao.validate(u,nomChamp,validationContext,verrouilleModifCode);
				
			}
			if(s.getSeverity()!=Status.ERROR){
				if(nomChamp.equals(Const.C_DATE_DOCUMENT)){
					Boolean changement=taDocument.changementDateDocument((Date)value);
					((IHMEnteteAcompte)selectedAcompte).setDateDocument((Date)value);
					PropertyUtils.setSimpleProperty(taDocument, Const.C_DATE_DOCUMENT, value);
					if(changement){
						((IHMEnteteAcompte)selectedAcompte).setDateEchDocument(taDocument.getDateDocument());
						PropertyUtils.setSimpleProperty(taDocument, Const.C_DATE_ECH_DOCUMENT, taDocument.getDateDocument());
						((IHMEnteteAcompte)selectedAcompte).setDateLivDocument(taDocument.getDateDocument());
						PropertyUtils.setSimpleProperty(taDocument, Const.C_DATE_LIV_DOCUMENT, taDocument.getDateDocument());
						taDocument.setOldDate(taDocument.getDateDocument());
					}
				}
				if(nomChamp.equals(Const.C_DATE_LIV_DOCUMENT)) {
					((IHMEnteteAcompte)selectedAcompte).setDateLivDocument((Date)value);
					PropertyUtils.setSimpleProperty(taDocument, Const.C_DATE_LIV_DOCUMENT, value);
				}
			}
//			s = new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
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
	
	public void initInfosAcompte() {
		if(taInfosDocument==null) {
			//initialisation de l'infosfacture
			taInfosDocument = new TaInfosAcompte();
		}
		if(taDocument!=null) {
			//if(taAcompte.getTaInfosAcomptes().isEmpty()) { //pas d'infosfacture dans la facture
				taInfosDocument.setTaDocument(taDocument);
				taDocument.setTaInfosDocument(taInfosDocument);
				//taAcompte.getTaInfosAcomptes().add(taInfosAcompte);
//			} else {
//				taInfosAcompte = taAcompte.getTaInfosAcomptes().iterator().next();
//			}
		}
	}

	@Override
	protected void actEnregistrer() throws Exception {
		EntityTransaction transaction = dao.getEntityManager().getTransaction();
		try {

			try {
				boolean declanchementInterne = false;
				if(sourceDeclencheCommandeController==null) {
					declanchementInterne = true;
				}
				
					if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
							|| (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
						
						try{
							taDocument.setLegrain(false);
							for (TaLAcompte ligne : taDocument.getLignes()) {
								ligne.setLegrain(false);
							}
							controllerLigne.ctrlTouslesChampsToutesLesLignes();
							if(controllerTotaux.getDaoRAcompte().dataSetEnModif())
								controllerTotaux.actEnregistrerAffectation();
							
						}catch (Exception e) {
							actSuivant();
							throw new Exception();
						}
						taDocument.calculeTvaEtTotaux();
						initInfosAcompte();

						
//						if(declanchementInterne) {
						verifCode();
							ctrlTousLesChampsAvantEnregistrementSWT(dbcAdresseFact);
							ctrlTousLesChampsAvantEnregistrementSWT(dbcAdresseLiv);
							ctrlTousLesChampsAvantEnregistrementSWT();
//						}
						

						dao.begin(transaction);
						
						if(declanchementInterne) {
							 mapperUIToModelAdresseFactVersInfosDoc.getMapper();
							mapperUIToModelAdresseFactVersInfosDoc.map((IHMAdresseInfosFacturation) selectedAdresseFact, taInfosDocument);
							mapperUIToModelAdresseLivVersInfosDoc.map((IHMAdresseInfosLivraison) selectedAdresseLiv, taInfosDocument);
							mapperUIToModelCPaiementVersInfosDoc.map((IHMInfosCPaiement) selectedCPaiement, taInfosDocument);

							/*
							 * recuperation des objets a partir de l'interface au cas ou l'utilisateur ne serait pas passe 
							 * sur cet onglet
							 */
							controllerTotaux.validateUI(); 

						}

						mapperUIToModel.map((IHMEnteteAcompte) selectedAcompte, taDocument);
						mapperUIToModelDocumentVersInfosDoc.map(taDocument, taInfosDocument);
						

						if(!((IStructuredSelection)viewerComboLocalisationTVA.getSelection()).isEmpty()) {
							String codeTTvaDoc = ((TaTTvaDoc)((IStructuredSelection)viewerComboLocalisationTVA.getSelection()).getFirstElement()).getCodeTTvaDoc();
							taInfosDocument.setCodeTTvaDoc(codeTTvaDoc);
						}
						
						taDocument.setLegrain(false);
						for (TaLAcompte ligne : taDocument.getLignes()) {
							ligne.setLegrain(false);
						}
						
						try{						
							boolean afficher= DocumentPlugin.getDefault().getPreferenceStore().
									getBoolean(fr.legrain.document.preferences.PreferenceConstants.MESSAGE_TIERS_DIFFERENT);
									if(afficher && !taDocument.getTaTiers().getNomTiers().equals(taInfosDocument.getNomTiers())){
								if(MessageDialog.openConfirm(vue.getShell(), "nom de tiers différent", "Le nom du tiers dans la facture est différent du nom du tiers lié au code tiers " +
										taDocument.getTaTiers().getCodeTiers()+". Etes-vous sûr de vouloir le conserver ? ")==false){
									throw new Exception();
								}
							}
						}catch (Exception e) {
//							retourPage=ChangementDePageEvent.DEBUT;
							throw new Exception("",e);
						}
						
							taDocument=dao.enregistrerMerge(taDocument);							
						dao.commit(transaction);
						remplaceCodeDocument(taDocument);
						transaction = null;

						if (controllerTotaux.impressionAuto())
							actImprimer();
							
						initialisationEcran(null);
						
						actInserer();
					} else {
						throw new Exception("Erreur actEnregistrer dataset pas en modif");
					}
			} catch (ExceptLgr e1) {
				logger.error("Erreur : actionPerformed", e1);
			} finally {
				if(transaction!=null && transaction.isActive()) {
					transaction.rollback();
				}
				initEtatBouton(true);
			}
		} catch (ExceptLgr e1) {
			logger.error("Erreur : actionPerformed", e1);
		}

	}


	public void initEtatComposant() {
		try {
			if (taDocument.isRemplie()) {
				vue.getCbTTC().setEnabled(false);
				vue.getComboLocalisationTVA().setEnabled(false);
			} else {
				vue.getCbTTC().setEnabled(true);
				vue.getComboLocalisationTVA().setEnabled(true);
			}
			vue.getTfCODE_TIERS().setEnabled(taDocument.getTaRAcomptes().size()==0);
		} catch (Exception e) {
			if (e.getMessage() != null)
				vue.getLaMessage().setText(e.getMessage());
		}
	}

	public IHMEnteteAcompte getIhmOldEnteteAcompte() {
		return ihmOldEnteteAcompte;
	}

	public void setIhmOldEnteteAcompte(IHMEnteteAcompte ihmOldEnteteAcompte) {
		this.ihmOldEnteteAcompte = ihmOldEnteteAcompte;
	}

	public void setIhmOldEnteteAcompte() {
		if (selectedAcompte != null){
			this.ihmOldEnteteAcompte = IHMEnteteAcompte.copy((IHMEnteteAcompte) selectedAcompte);
		}
	}
	
	
	public Boolean setIhmOldEnteteDocumentRefresh(){
		try {			
		if (selectedAcompte != null){
			this.ihmOldEnteteAcompte = IHMEnteteAcompte.copy((IHMEnteteAcompte) selectedAcompte);
			if (taDocument!=null){
				//dao.getEntityManager().clear();
				taDocument =rechercheAcompte(taDocument.getCodeDocument(), false, true);
				taInfosDocument=taDocument.getTaInfosDocument();
			}
		}
		return true;
		} catch (Exception e) {
			return false;
		}		
	}



	public void setVue(PaEditorAcompteSWT vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();

		mapComposantDecoratedField.put(vue.getTfCODE_DOCUMENT(), vue.getFieldCODE_DOCUMENT());
		mapComposantDecoratedField.put(vue.getTfCODE_TIERS(), vue.getFieldCODE_TIERS());
//		mapComposantDecoratedField.put(vue.getDateTimeDATE_ECH_FACTURE(),vue.getFieldDATE_ECH_FACTURE());
		mapComposantDecoratedField.put(vue.getDateTimeDATE_DOCUMENT(),vue.getFieldDATE_DOCUMENT());
//		mapComposantDecoratedField.put(vue.getDateTimeDATE_LIV_FACTURE(), vue.getFieldDATE_LIV_FACTURE());
		mapComposantDecoratedField.put(vue.getTfLIBELLE_DOCUMENT(), vue.getFieldLIBELLE_DOCUMENT());
	}

	public Class getClassModel() {
		return classModel;
	}

	@Override
	protected void sortieChamps() {
		boolean verrouille = VerrouInterface.isVerrouille();
		// si sortie champ code facture
		try {
			
			if (!(focusDansEntete()
					|| (vue.getPaBtnAvecAssistant().getPaBtn().getBtnAnnuler().isFocusControl()) 
					|| (vue.getPaBtnAvecAssistant().getPaBtn().getBtnFermer().isFocusControl()))) {
				verifCode();
				validateUI();
			}
	
			VerrouInterface.setVerrouille(true);
			if (vue.getTfCODE_TIERS().equals(getFocusCourantSWT())) {
				changementTiers(true);
			}
			// if(vue.getTfTX_REM_HT_FACTURE().equals(getFocusCourantSWT())){
			// modificationDocument(null);
			// }
			if (vue.getCbTTC().equals(getFocusCourantSWT()) && (vue.getCbTTC().isEnabled())) {
				controllerLigne.initTTC();
			}

//			if (vue.getDateTimeDATE_FACTURE().equals(getFocusCourantSWT()) && (vue.getDateTimeDATE_FACTURE().isEnabled())) {
//				Date d = dateDansExercice(vue.getDateTimeDATE_FACTURE().getSelection());
//				if(LibDate.compareTo(vue.getDateTimeDATE_FACTURE().getSelection(), d)!=0) {
//					System.err.println("Date hors exo");
//					vue.getDateTimeDATE_FACTURE().setSelection(d);
//					ihmEnteteAcompte.setDATE_FACTURE(d);
//				}
//			}

			// vide l'entête
			// annulerListeContext();

			// vide les lignes
			// controllerLigne.annulerListeContext();

			// active l'entête
			// activationContext();

		} catch (Exception e) {
			logger.error("",e);
			// getFocusCourantSWT().forceFocus();
		} finally {
			VerrouInterface.setVerrouille(verrouille);

			System.out.println("sortieChamps() " + getFocusCourantSWT());
		}
	}

	@Override
	protected void actRefresh() throws Exception {
		try{
		vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
		if (dao.dataSetEnModif()) {
			if (taDocument!=null && selectedAcompte!=null && (IHMEnteteAcompte) selectedAcompte!=null) {
				mapperModelToUI.map(taDocument, (IHMEnteteAcompte) selectedAcompte);
			}
		}
		listeDocument=null;
		codeDocumentContentProposal.setContentProposalProvider(contentProposalProviderDocument());
		listeTiers=null;
		tiersContentProposal.setContentProposalProvider(contentProposalProviderTiers());
		controllerLigne.raffraichitListeArticles();
		}finally{
			vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
		}
	}



	public void modificationDocument(SWTModificationDocumentEvent evt) {
//		controllerTotaux.modificationDocument(evt);
		
		mapperModelToUI.map(taDocument, ((IHMEnteteAcompte) selectedAcompte));
//		((IHMEnteteAcompte) selectedEnteteAcompte).setIHMEnteteAcompte(((SWTEnteteAcompte) swtAcompte.getEntete()));
//		((IHMTotauxAcompte) controllerTotaux.getSelectedTotauxAcompte()).setIHMTotauxAcompte(((SWTEnteteAcompte) swtAcompte.getEntete()));
//		((IHMEnteteAcompte) selectedEnteteAcompte).setIHMEnteteAcompte(((SWTEnteteAcompte) swtAcompte.getEntete()));

	}

	public void changementMode(ChangeModeEvent evt) {
		try {
			if (!dao.dataSetEnModif()) {
				if (!taDocument.getOldCODE().equals(""))
					dao.modifier(taDocument);
				else
					actInserer();
				initEtatBouton(false);
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}

	//#AFAIRE
	private void initialisationEcran(String code) {
		boolean verrouLocal = VerrouInterface.isVerrouille();
		if (!vue.isDisposed()) {
			try {
				// vider l'entête
				if(findStatusLineManager()!=null)
					findStatusLineManager().setMessage(null);
				vue.getLaMessage().setText("");
				VerrouInterface.setVerrouille(true);
					codeDocumentContentProposal.setContentProposalProvider(contentProposalProviderDocument());
				controllerTotaux.initialisationEcran();
			} catch (Exception e) {

				logger.error("Problème sur l'initialisation de l'écran");
			} finally {
				initFocusSWT(dao, mapInitFocus);
				VerrouInterface.setVerrouille(verrouLocal);
			}
		}
	}

	public ContentProposalProvider contentProposalProviderDocument(){
		String[][] adapterAcompte = initAdapterDocument();
		String[] listeCodeAcompte = null;
		String[] listeLibelleAcompte = null;
		if (adapterAcompte != null) {
			listeCodeAcompte = new String[adapterAcompte.length];
			listeLibelleAcompte = new String[adapterAcompte.length];
			for (int i = 0; i < adapterAcompte.length; i++) {
				listeCodeAcompte[i] = adapterAcompte[i][0];
				listeLibelleAcompte[i] = adapterAcompte[i][1];
			}
		}

		return new ContentProposalProvider(listeCodeAcompte,
				listeLibelleAcompte);
		
	}
	
	public ContentProposalProvider contentProposalProviderTiers(){
		String[][] TabTiers = initAdapterTiers();
		String[] listeCodeTiers = null;
		String[] listeDescriptionTiers = null;
		if (TabTiers != null) {
			listeCodeTiers = new String[TabTiers.length];
			listeDescriptionTiers = new String[TabTiers.length];
			for (int i = 0; i < TabTiers.length; i++) {
				listeCodeTiers[i] = TabTiers[i][0];
				listeDescriptionTiers[i] = TabTiers[i][1];
			}
		}
		return new ContentProposalProvider(listeCodeTiers,
				listeDescriptionTiers);		
	}
	
//	public SWTAcompte getSwtAcompte() {
//		return swtAcompte;
//	}

	public PaLigneAcompteController getControllerLigne() {
		return controllerLigne;
	}

	public void setControllerLigne(PaLigneAcompteController controllerLigne) {
		this.controllerLigne = controllerLigne;
		this.controllerLigne.setParentEcran(this);
	}

	private Boolean remonterDocument(String codeAcompte) throws Exception {
		try {
			boolean res = false;
			desactiveModifyListener();
			desactiveValidateCodeDocument=true;
			if(taDocument!=null)
				dao.annulerCodeGenere(taDocument.getOldCODE(),dao.getChampGenere());
			if (dao.getModeObjet().getMode() == ModeObjet.EnumModeObjet.C_MO_EDITION
					|| dao.getModeObjet().getMode() == ModeObjet.EnumModeObjet.C_MO_INSERTION)
				actAnnuler(true,false,true);
			controllerLigne.forceAnnuler = true;
			controllerLigne.actAnnuler();
			//dao.getEntityManager().clear();
			boolean factureVide = true;
			if(taDocument!=null) {
				factureVide = ((IHMEnteteAcompte) selectedAcompte).factureEstVide(taDocument.dateDansExercice(new Date()));
			}
			TaAcompte factureRecherche = rechercheAcompte(codeAcompte, true, factureVide);
			if (factureRecherche!=null) {
				taDocument = factureRecherche;
//				taDocument.calculeTvaEtTotaux();
				taDocument.calculLignesTva();
				fireChangementMaster(new ChangementMasterEntityEvent(this,taDocument));
				taDocument.addChangeModeListener(this);
				res = true;
				idTiersDocumentOriginal = taDocument.getTaTiers().getIdTiers();
				changementTiers(true);
				
				TaTTvaDoc t = new TaTTvaDocDAO().findByCode(taInfosDocument.getCodeTTvaDoc());
				viewerComboLocalisationTVA.setSelection(new StructuredSelection(t),true);
				initLocalisationTVA();
				
				actRefreshAffectation();
				controllerTotaux.remonterDocument();
				controllerTotaux.actRefreshAffectation();
			}
			return res;
		} finally {
			activeModifytListener();
			initEtatBouton(true);
		}

	}

//	public boolean nouvelleAcompte(EnumModeObjet mode, String codeAcompte)
//			throws Exception {
//		boolean resultat = true;
//		boolean verrouLocal = VerrouInterface.getVerrouInterface().isVerrouille();
//		String codeTmp = ((IHMEnteteAcompte) selectedAcompte).getCodeAcompte();
//		try {
//			// si pièce déjà en mode Edition ou Insertion -> annuler
//			if (dao.dataSetEnModif())
//				actAnnuler(true);
//
//			if (!dao.dataSetEnModif()) {
//				// si pièce en consultation
//				VerrouInterface.getVerrouInterface().setVerrouille(true);
//				desactiveModifyListener();
//				taAcompte = new TaAcompte(codeAcompte);
////				taAcompte.addModificationDocumentListener(this);
////				taAcompte.addModificationDocumentListener(dao);
//
//				taAcompte.addChangeModeListener(this);
////				ibTaTable.setAcompte(swtAcompte);
//				controllerLigne.setTaAcompte(taAcompte);
//				controllerTotaux.setTaAcompte(taAcompte);
////				controllerLigne.getIbTaTable().setAcompte(swtAcompte);
////				controllerLigne.getIbTaTable().videTableTemp();
////				controllerLigne.initLigneCourantSurRow();
////				controllerLigne.getIbTaTable().initLigneCourantSurRow();
////				swtAcompte.getLignes().clear();
////				taAcompte.getLignesTVA().clear();
//				dao.annulerCodeGenere(codeTmp, Const.C_CODE_FACTURE);
//				if (codeAcompte.equals("")) {
//					codeAcompte = dao.genereCode();
//					if (codeAcompte.equals(""))
//						throw new ExceptLgr(MessagesGestCom.getString("IB_TA_FACTURE.Message.CodeIncoherent"),0, true, 0);
//					dao.rentreCodeGenere(codeAcompte,Const.C_CODE_FACTURE);
//				}
//				((IHMEnteteAcompte) selectedAcompte).setCodeAcompte(codeAcompte);
//				vue.getTfCODE_FACTURE().setText(codeAcompte);
//				switch (mode) {
//				case C_MO_INSERTION:
//					actInserer();
//					// récupérer code validé dans facture, dataset et interface
//					break;
//				case C_MO_EDITION:
//					// actModifier(null);
//					// récupérer code validé dans facture, dataset et interface
//					// récupérer entête dans objet facture
////					ibTaTable.remplissageEnteteSurObjetQuery(null, null);
////					ibTaTable.getQuery_Champ_Obj();
////					controllerLigne.getIbTaTable().recupLignesAcompte(codeAcompte);
//					//#evt
//					break;
//				default:
//					break;
//				}
//
//				// récupérer les lignes dans objet facture
//				// récupérer les totaux
//				//#evt
////				if (!controllerLigne.getIbTaTable().getFIBQuery().isEmpty()) {
////					controllerLigne.getIbTaTable().getFIBQuery().first();
////					do {
////						controllerLigne.initLigneCourantSurRow();
////						controllerLigne.getIbTaTable().initLigneCourantSurRow();
////						swtAcompte.addLigne(new SWTLigneAcompte(swtAcompte.getLigneCourante()));
////						controllerLigne.getIbTaTable().remplissageLigneSurObjetQuery(swtAcompte.getLigneCourante());
////						// ibTaTable.modificationDocument(new
////						// ModificationDocumentEvent(this,facture.getLigne(facture.getLigneCourante())));
////					} while (controllerLigne.getIbTaTable().getFIBQuery().next());
////					ibTaTable.modificationDocument(new SWTModificationDocumentEvent(this, swtAcompte.getLigne(swtAcompte.getLigneCourante())));
////					modificationDocument(new SWTModificationDocumentEvent(this,swtAcompte.getLigne(swtAcompte.getLigneCourante())));
////					controllerLigne.getIbTaTable().getFIBQuery().first();
////				}
//
//				// actualiser l'écran entête
//				// remplirInterface(false);
//				// actualiser l'écran des lignes
//				// controllerLigne.remplirInterface(false);
//
//				taAcompte.addModificationDocumentListener(this.controllerLigne);
//				
//				taAcompte.setCodeAcompte(codeAcompte);
////				((IHMEnteteAcompte) selectedEnteteAcompte).setIHMEnteteAcompte(((SWTEnteteAcompte) swtAcompte.getEntete()));
//				mapperModelToUI.map(taAcompte, (IHMEnteteAcompte) selectedAcompte);
//
//				// récupérer code validé dans facture, dataset et interface
////				swtAcompte.getEntete().setCODE(codeAcompte);
//				taAcompte.setCodeAcompte(codeAcompte);
//				if(validateUIField(Const.C_CODE_FACTURE, ((IHMEnteteAcompte) selectedAcompte).getCodeAcompte()).getSeverity()==IStatus.ERROR)
////				if (!ibTaTable.verifChamp(Const.C_CODE_FACTURE,((IHMEnteteAcompte) selectedEnteteAcompte).getCodeAcompte(), null, null))
//					throw new ExceptLgr();
//				vue.getTfCODE_FACTURE().setText(codeAcompte);
//				initEtatBouton(false);
//			} else {
//				resultat = false;
//			}
//			activeModifytListener();
//			VerrouInterface.setVerrouille(verrouLocal);
//		} catch (Exception e) {
//			VerrouInterface.setVerrouille(verrouLocal);
//			return false;
//		}
//		return resultat;
//	}

	// private boolean validationCodeAcompte(String codeAcompte){
	// boolean res = false;
	// try {
	// if(!swtAcompte.getOldCODE().equals(codeAcompte)
	// ||(swtAcompte.getOldCODE().equals(SWTDocument.C_OLD_CODE_INITIAL))) {
	// try {//si changement de code facture
	// //
	// ibTaTable.annulerCodeGenere(facture.getEntete().getCODE(),"validationCodeAcompte");
	// if (ibTaTable.dataSetEnModif())
	// actAnnuler(true);
	// if(controllerLigne.getIbTaTable().dataSetEnModif())
	// controllerLigne.actAnnuler();
	// if(!ibTaTable.dataSetEnModif()){
	// if(ibTaTable.rechercheAcompte(codeAcompte,true,((IHMEnteteAcompte)selectedEnteteAcompte).AcompteEstVide(dateDansExercice(new
	// Date())))) {
	// selectedEnteteAcompte = modelEnteteAcompte.getListeObjet().getFirst();
	// // si code existant -> remonterDocument (code existant "CodeAcompte")
	// if (remonterDocument(codeAcompte))
	// res=true;
	// }else {//si nouveau code
	// // -> nouvelle facture (Insertion, nouveau code "CodeAcompte")
	// if (nouvelleAcompte(EnumModeObjet.C_MO_INSERTION,codeAcompte))
	// res=true;
	// }
	// if (!res)throw new Exception();
	// swtAcompte.setOldCODE(codeAcompte);
	// // ibTaTable.rentreCodeGenere(codeAcompte,"validationCodeAcompte");
	// }
	// } catch (SQLException e) {
	// throw new Exception(e.getMessage());
	// }
	// }else res=true;
	// } catch (Exception e) {
	// logger.error("",e);
	// return false;
	// }
	// return res;
	// }

	// public WorkbenchPart getWorkbenchPart() {
	// return workbenchPart;
	// }
	//
	// public void setWorkbenchPart(WorkbenchPart workbenchPart) {
	// this.workbenchPart = workbenchPart;
	// }
	/**
	 * Active l'ecoute de tous les champs du controller qui sont relie a la bdd,
	 * si le dataset n'est pas en modification et qu'un des champs est modifie,
	 * le dataset passera automatiquement en edition.
	 * 
	 * @see #desactiveModifyListener
	 */
	public void activeModifytListener() {
		logger.debug("active");
		super.activeModifytListener();
		activeModifytListener(mapComposantChampsAdresse,daoAdresseFact);
		activeModifytListener(mapComposantChampsAdresseLiv,daoAdresseLiv);
		activeModifytListener(mapComposantChampsCPaiement, daoCPaiement);
		vue.getTfCODE_DOCUMENT().removeModifyListener(lgrModifyListener);
	}

	//#JPA
	//#AFAIRE
//	public String[][] initAdapterDocument() {
//		String[][] valeurs = null;
//		boolean affichageCtrlEspace = GestionCommercialePlugin.getDefault().getPreferenceStore().getBoolean(fr.legrain.gestionCommerciale.preferences.PreferenceConstants.AFFICHAGE_CTRL_ESPACE);
//		if(affichageCtrlEspace){
//		if(listeDocument==null){
//			TaAcompteDAO taAcompteDAO = new TaAcompteDAO(getEm());
//			TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
//			TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
//
//			listeDocument =((IDocumentTiersDAO)taAcompteDAO).rechercheDocument(taInfoEntreprise.getDatedebInfoEntreprise(),
//					taInfoEntreprise.getDatefinInfoEntreprise());
//			//			listeAcompte =taAcompteDAO.selectAll(); 
//			taAcompteDAO = null;
//		}
//		valeurs = new String[listeDocument.size()][2];
//		int i = 0;
//		String description = "";
//		for (IDocumentTiers taAcompte : listeDocument) {
////			taAcompte.calculeTvaEtTotaux();
//			valeurs[i][0] = taAcompte.getCodeDocument();
//			
//			description = "";
//			description += taAcompte.getLibelleDocument();
//			if(taAcompte.getTaTiers()!=null) {
//				description += " \r\n " + taAcompte.getTaTiers().getCodeTiers();
//			}
////			description += "\r\n Net TTC = " + taAcompte.getNetHtCalc()
////			+ " \r\n Net HT = " + taAcompte.getNetTtcCalc()
////			+ " \r\n Net à payer = " + taAcompte.getNetAPayer()
////			+ " \r\n Montant régler = " + taAcompte.getRegleAcompte();
//			description += " \r\n Date = " + LibDate.dateToStringAA(taAcompte.getDateDocument())
////			+ " \r\n Echéance = " + LibDate.dateToStringAA(taAcompte.getDateEchDocument())
//			+ " \r\n Exportée = " 
//			+LibConversion.booleanToStringFrancais(LibConversion.intToBoolean(((TaAcompte)taAcompte).getExport()));
//			valeurs[i][1] = description;
//
//			i++;
//		}
//		}
//		return valeurs;
//	}	
	public String[][] initAdapterDocument() {
		String[][] valeurs = null;
		List<Object[]>listeTemp=null;
		boolean affichageCtrlEspace = GestionCommercialePlugin.getDefault().getPreferenceStore().getBoolean(fr.legrain.gestionCommerciale.preferences.PreferenceConstants.AFFICHAGE_CTRL_ESPACE);
		if(affichageCtrlEspace){
		if(listeDocument==null){
			TaAcompteDAO taDocDAO = new TaAcompteDAO(getEm());
			TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
			TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();

			listeTemp =((IDocumentTiersDAO)taDocDAO).rechercheDocument(taInfoEntreprise.getDatedebInfoEntreprise(),
					taInfoEntreprise.getDatefinInfoEntreprise(),true);
			//			listeFacture =taFactureDAO.selectAll(); 
			taDocDAO = null;
		}
		valeurs = new String[listeTemp.size()][2];
		int i = 0;
		String description = "";
		for (Object[] taFacture : listeTemp) {
//			taFacture.calculeTvaEtTotaux();
			valeurs[i][0] = (String)taFacture[1];
//			f.idDocument, f.codeDocument, f.dateDocument, f.libelleDocument, tiers.codeTiers, infos.nomTiers,f.dateEchDocument			
			description = "";
			description += (String)taFacture[3];
			if((String)taFacture[4]!=null) {
				description += " \r\n " + (String)taFacture[4];
			}
//			description += "\r\n Net TTC = " + taFacture.getNetHtCalc()
//			+ " \r\n Net HT = " + taFacture.getNetTtcCalc()
//			+ " \r\n Net à payer = " + taFacture.getNetAPayer()
//			+ " \r\n Montant régler = " + taFacture.getRegleFacture();
			description += " \r\n Date = " + LibDate.dateToStringAA((Date)taFacture[2])
			+ " \r\n Echéance = " + LibDate.dateToStringAA((Date)taFacture[6])
			+ " \r\n Exportée = " 
			+LibConversion.booleanToStringFrancais(LibConversion.intToBoolean((Integer)taFacture[7]));
			valeurs[i][1] = description;

			i++;
		}
		}
		return valeurs;
	}	
//	public String[][] initAdapterTiers() {
//		String[][] valeurs = null;
//		boolean affichageCtrlEspace = GestionCommercialePlugin.getDefault().getPreferenceStore().getBoolean(fr.legrain.gestionCommerciale.preferences.PreferenceConstants.AFFICHAGE_CTRL_ESPACE);
//		if(affichageCtrlEspace){
//		TaTiersDAO taTiersDAO = new TaTiersDAO(getEm());
//		//#AFAIRE
//		taTiersDAO.setPreferences(pluginAcompte.getDefault().getPreferenceStore().getString(fr.legrain.acompte.preferences.PreferenceConstants.TYPE_TIERS_DOCUMENT));
//		if(listeTiers==null)
//			listeTiers = taTiersDAO.selectTiersSurTypeTiers();
//		//List<TaTiers> l = taTiersDAO.selectAll();
//		valeurs = new String[listeTiers.size()][2];
//		int i = 0;
//		String description = "";
//		for (TaTiers taTiers : listeTiers) {
//			valeurs[i][0] = taTiers.getCodeTiers();
//			
//			description = "";
//			description += taTiers.getCodeCompta() + " - " + taTiers.getCompte() + " - " + taTiers.getNomTiers();
//			if(taTiers.getTaTTiers()!=null) {
//				description += " - " + taTiers.getTaTTiers().getLibelleTTiers();
//			}
//			valeurs[i][1] = description;
//
//			i++;
//		}
//		taTiersDAO = null;
//		}
//		return valeurs;
//	}
	public String[][] initAdapterTiers() {
		String[][] valeurs = null;
		List<Object[]> listeTemp = null;
		boolean affichageCtrlEspace = GestionCommercialePlugin.getDefault().getPreferenceStore().getBoolean(fr.legrain.gestionCommerciale.preferences.PreferenceConstants.AFFICHAGE_CTRL_ESPACE);
		if(affichageCtrlEspace){
		TaTiersDAO taTiersDAO = new TaTiersDAO(getEm());
		//#AFAIRE
		taTiersDAO.setPreferences(pluginAcompte.getDefault().getPreferenceStore().getString(fr.legrain.acompte.preferences.PreferenceConstants.TYPE_TIERS_DOCUMENT));
		if(listeTiers==null)
			listeTemp = taTiersDAO.selectTiersSurTypeTiersLight();
		//List<TaTiers> l = taTiersDAO.selectAll();
		valeurs = new String[listeTemp.size()][2];
		int i = 0;
		String description = "";
		for (Object[] taTiers : listeTemp) {
			valeurs[i][0] = (String)taTiers[0];
//			a.codeTiers,a.nomTiers,a.codeCompta,a.compte,ttiers.libelleTTiers
			description = "";
			description += (String)taTiers[2] + " - " + (String)taTiers[3] + " - " + (String)taTiers[1];
			if(taTiers[4]!=null) {
				description += " - " +(String)taTiers[4];
			}
			valeurs[i][1] = description;

			i++;
		}
		taTiersDAO = null;
		}
		return valeurs;
	}

	
	private void initTTC() {
		if (!vue.getCbTTC().getSelection())
//			((SWTEnteteAcompte) swtAcompte.getEntete()).setTTC(false);
			taDocument.setTtc(0);
		else
//			((SWTEnteteAcompte) swtAcompte.getEntete()).setTTC(true);
			taDocument.setTtc(1);
		try {
//			ibTaTable.affecte(Const.C_TTC,LibConversion.booleanToString(((SWTEnteteAcompte) swtAcompte.getEntete()).getTTC()));
		} catch (DataSetException e1) {
			logger.error(e1);
		} catch (Exception e1) {
			logger.error(e1);
		}
		if (controllerLigne != null)
			controllerLigne.initTTC();
	}
	
	private void initLocalisationTVA() {
		TaTTvaDoc taTTvaDoc = ((TaTTvaDoc)((IStructuredSelection)viewerComboLocalisationTVA.getSelection()).getFirstElement());
		
		if(taTTvaDoc!=null && taTTvaDoc.getCodeTTvaDoc()!=null){
			if(!taTTvaDoc.getCodeTTvaDoc().equals("F")
					//|| taTTvaDoc.getCodeTTvaDoc().equals("UE")
					//|| taTTvaDoc.getCodeTTvaDoc().equals("HUE")
					) {
				taDocument.setGestionTVA(false);
			} else {
				taDocument.setGestionTVA(true);
			}
		}else {
			taDocument.setGestionTVA(true);
		}
	}

	/**
	 * Initialisation des boutons suivant l'etat de l'objet "ibTaTable"
	 * 
	 * @param initFocus -
	 *            si vrai initialise le focus en fonction du mode
	 */
	protected void initEtatBouton(boolean initFocus) {
		// super.initEtatBouton(initFocus);
		if (controllerTotaux != null)
			controllerTotaux.initEtatBouton(initFocus);
		initEtatBoutonCommand(initFocus,modelEnteteAcompte.getListeObjet());
		boolean trouve = false;
		enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID, dao.getModeObjet().getMode()==EnumModeObjet.C_MO_EDITION);
		enableActionAndHandler(C_COMMAND_GLOBAL_PRECEDENT_ID, false);
		enableActionAndHandler(C_COMMAND_GLOBAL_SUIVANT_ID, true);
		enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID, true);
		enableActionAndHandler(C_COMMAND_DOCUMENT_CREATEDOC_ID, true);
		enableActionAndHandler(C_COMMAND_DOCUMENT_REINIT_ADR_FACT_ID, true);
		enableActionAndHandler(C_COMMAND_DOCUMENT_REINIT_ADR_LIV_ID, true);
		enableActionAndHandler(C_COMMAND_DOCUMENT_REINIT_CPAIEMENT_ID, true);
		enableActionAndHandler(C_COMMAND_DOCUMENT_REINIT_INFOSTIERS_ID, true);
	}

	@Override
	protected void initImageBouton() {
		vue.getPaBtnAvecAssistant().getPaBtn().getBtnAnnuler().setImage(
				LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ANNULER));
		vue.getPaBtnAvecAssistant().getPaBtn().getBtnEnregistrer().setImage(
				LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ENREGISTRER));
		vue.getPaBtnAvecAssistant().getPaBtn().getBtnFermer().setImage(
				LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_FERMER));
		vue.getPaBtnAvecAssistant().getPaBtn().getBtnInserer().setImage(
				LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_INSERER));
		vue.getPaBtnAvecAssistant().getPaBtn().getBtnModifier().setImage(
				LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_MODIFIER));
		vue.getPaBtnAvecAssistant().getPaBtn().getBtnSupprimer().setImage(
				LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_SUPPRIMER));
		vue.getPaBtnAvecAssistant().getPaBtn().getBtnImprimer().setImage(
				LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_IMPRIMER));
		
		vue.getBtnFicheTiers().setImage(
				LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_IDENTITE_TIERS));
		vue.getBtnGenDoc().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_GEN_DOCUMENT));
		vue.getBtnGenModel().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_GEN_MODELE));
		vue.getBtnAutres().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_TOOLS));
		
		vue.getBtnFicheTiers().setText("Fiche tiers");
		vue.getBtnGenDoc().setText("Générer un document");
		vue.getBtnGenModel().setText("Utiliser comme modèle");
		vue.getBtnAutres().setText("Autres fonctionnalités");
		
		vue.getBtnFicheTiers().setToolTipText("Fiche tiers");
		vue.getBtnGenDoc().setToolTipText("Générer un document");
		vue.getBtnGenModel().setToolTipText("Utiliser comme modèle");
		vue.getBtnAutres().setToolTipText("Autres fonctionnalités");

		vue.layout(true);
	}

	public class OldAcompte {
		String code_facture = "";
		int id_facture = 0;

		public OldAcompte() {
		}

		public OldAcompte(String code_facture, int id_facture) {
			this.code_facture = code_facture;
			this.id_facture = id_facture;
		}
	}

	public boolean focusDansEntete() {
		for (Control element : listeComposantEntete) {
			if (element.isFocusControl())
				return true;
		}
		return false;
	}

	public boolean focusDansAdresse(Control focusControl) {
		for (Control element : listeComposantAdresse) {
			if (element.equals(focusControl))
				return true;
		}
		return false;
	}

	public boolean focusDansAdresse_Liv(Control focusControl) {
		for (Control element : listeComposantAdresse_Liv) {
			if (element.equals(focusControl))
				return true;
		}
		return false;
	}

	public boolean focusDansCPaiment(Control focusControl) {
		for (Control element : listeComposantCPaiement) {
			if (element.equals(focusControl))
				return true;
		}
		return false;
	}

	// public boolean focusDansBasAcompte(){
	// for (Control element : listeComposantBasAcompte) {
	// if(element.isFocusControl())
	// return true;
	// }
	// return false;
	// }



//	/**
//	 * Si la valeur est antérieure à la date de debut de l'exercice, retourne la date de début de l'exercice<br>
//	 * Si la valeur est postérieure à la date de fin de l'exercice, retourne la date de fin de l'exercice<br>
//	 * @param valeur - date a tester
//	 * @return 
//	 * @throws Exception
//	 */
//	public Date dateDansExercice(Date valeur) throws Exception {
////		SWTInfoEntreprise infoEntreprise = null;
////		infoEntreprise = SWT_IB_TA_INFO_ENTREPRISE.infosEntreprise("1", infoEntreprise);
//		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
//		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
//		// si date inférieur à dateDeb dossier
//		if (LibDate.compareTo(valeur, taInfoEntreprise.getDatedebInfoEntreprise()) == -1) {
//			return taInfoEntreprise.getDatedebInfoEntreprise();
//		} else
//			// si date supérieur à dateFin dossier
//			if (LibDate.compareTo(valeur, taInfoEntreprise.getDatefinInfoEntreprise()) == 1) {
//				return taInfoEntreprise.getDatefinInfoEntreprise();
//			}
//		//return new Date();
//		return valeur;
//	}

	public PaEditorTotauxAcompteController getControllerTotaux() {
		return controllerTotaux;
	}

	public void setControllerTotaux(PaEditorTotauxAcompteController controllerTotaux) {
		this.controllerTotaux = controllerTotaux;
		// controllerTotaux.initHandlerAcompte(mapCommand);
		controllerTotaux.removeDeclencheCommandeControllerListener(this); // on le supprime s'il est deja enregistre
		controllerTotaux.addDeclencheCommandeControllerListener(this);
	}

	@Override
	public void desactiveModifyListener() {
		// TODO Auto-generated method stub
		super.desactiveModifyListener();
		desactiveModifyListener(mapComposantChampsAdresse);
		desactiveModifyListener(mapComposantChampsAdresseLiv);
		desactiveModifyListener(mapComposantChampsCPaiement);
	}

	private class HandlerReinitialiser extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				actReinitialiser();
			} catch (Exception e) {
				logger.error("", e);
			}
			return event;
		}
	}
	
	private class HandlerReinitAdrFact extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				actReinitAdrFact();
			} catch (Exception e) {
				logger.error("", e);
			}
			return event;
		}
	}
	
	private class HandlerReinitAdrLiv extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				actReinitAdrLiv();
			} catch (Exception e) {
				logger.error("", e);
			}
			return event;
		}
	}
	
	private class HandlerReinitCPaiement extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				actReinitCPaiement();
			} catch (Exception e) {
				logger.error("", e);
			}
			return event;
		}
	}
	
	private class HandlerAppliquerCPaiement extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				actAppliquerCPaiement();
			} catch (Exception e) {
				logger.error("", e);
			}
			return event;
		}
	}

	private class HandlerReinitInfosTiers extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				actReinitInfosTiers();
			} catch (Exception e) {
				logger.error("", e);
			}
			return event;
		}
	}
	
	//#AFAIRE
	private void actReinitialiser() throws Exception {
		initialisationDesInfosComplementaires();
	}
	
	private void actReinitAdrFact() throws Exception {
		initialisationDesInfosComplementaires(true,Const.RECHARGE_ADR_FACT);
	}
	
	private void actReinitAdrLiv() throws Exception {
		initialisationDesInfosComplementaires(true,Const.RECHARGE_ADR_LIV);
	}
	
	private void actReinitCPaiement() throws Exception {
		initialisationDesInfosComplementaires(true,Const.RECHARGE_C_PAIEMENT);
		calculDateEcheance(true);
		modifMode();
	}
	
	private void actAppliquerCPaiement() throws Exception {
		calculDateEcheance(true);
		modifMode();
	}
	
	private void actReinitInfosTiers() throws Exception {
		initialisationDesInfosComplementaires(true,Const.RECHARGE_INFOS_TIERS);
	}
	@Override
	protected void actCreateDoc() throws Exception {
		controllerTotaux.actCreateDoc();
	}	

	public ContentProposalAdapter getCodeDocumentContentProposal() {
		return codeDocumentContentProposal;
	}

	public void setCodeDocumentContentProposal(
			ContentProposalAdapter codeAcompteContentProposal) {
		this.codeDocumentContentProposal = codeAcompteContentProposal;
	}
	
	public Boolean verifCode() throws Exception{
		if(dao.dataSetEnModif()&& 
				dao.getModeObjet().getMode()==ModeObjet.EnumModeObjet.C_MO_INSERTION &&
				!dao.autoriseUtilisationCodeGenere(vue.getTfCODE_DOCUMENT().getText())){
			String code_Old =vue.getTfCODE_DOCUMENT().getText();
			MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"Erreur saisie","Le code "+vue.getTfCODE_DOCUMENT().getText()+" est utilisé, il va être automatiquement remplacé par le suivant !");
			dao.annulerCodeGenere(vue.getTfCODE_DOCUMENT().getText(),dao.getChampGenere());				
			vue.getTfCODE_DOCUMENT().setText(dao.genereCode());
			dao.rentreCodeGenere(vue.getTfCODE_DOCUMENT().getText(),dao.getChampGenere());
			vue.getTfLIBELLE_DOCUMENT().setText(vue.getTfLIBELLE_DOCUMENT().getText().replace(code_Old, vue.getTfCODE_DOCUMENT().getText()));
			return false;
		}
		return true;
	}

	public String getTypeAdresseFacturation() {
		return typeAdresseFacturation;
	}

	public void setTypeAdresseFacturation(String typeAdresseFacturation) {
		this.typeAdresseFacturation = typeAdresseFacturation;
	}

	public String getTypeAdresseLivraison() {
		return typeAdresseLivraison;
	}

	public void setTypeAdresseLivraison(String typeAdresseLivraison) {
		this.typeAdresseLivraison = typeAdresseLivraison;
	}

	public TaAcompteDAO getDao() {
		return dao;
	}

	public void setDao(TaAcompteDAO dao) {
		this.dao = dao;
	}

	public TaAcompte getTaDocument() {
		return taDocument;
	}
	
	public TaAcompte rechercheAcompte(String codeAcompte,boolean annule,boolean factureVide) throws Exception {
		TaAcompte fact = null;
		fact = dao.findByCode(codeAcompte);
		if(fact!=null){
			if(annule && dao.getModeObjet().getMode()==EnumModeObjet.C_MO_EDITION 
					|| (dao.getModeObjet().getMode()==EnumModeObjet.C_MO_INSERTION )) {
				if(!factureVide)
					if(MessageDialog.openQuestion(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "ATTENTION",
					"Voulez-vous annuler les modifications en cours ?")) {
						dao.annuler(taDocument);
					}
			}			
			taDocument=fact;
			fact=dao.refresh(fact);
			if(taDocument!=null && taDocument.getVersionObj()!=null && 
					!fact.getVersionObj().equals(taDocument.getVersionObj()))					
				dao.messageNonAutoriseModification();
			fact = dao.findByCode(fact.getCodeDocument());
			remplaceCodeDocument(fact);
		}
		return fact;
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


	public Date dateDansPeriode(Date newValue,String champ) throws Exception{
		if(champ.equals(Const.C_DATE_DOCUMENT)){
			newValue=taDocument.dateDansExercice(newValue);
			if(newValue!=null){
//				if((((IHMEnteteAcompte)selectedAcompte).getDateEchDocument()!=null && 
//						((IHMEnteteAcompte)selectedAcompte).getDateEchDocument().before(newValue))||
//						((IHMEnteteAcompte)selectedAcompte).getDateEchDocument()==null ){
//					taDocument.setDateEchDocument(newValue);
//					((IHMEnteteAcompte)selectedAcompte).setDateEchDocument(newValue);
//					}
//				if((((IHMEnteteAcompte)selectedAcompte).getDateLivDocument()!=null && 
//						((IHMEnteteAcompte)selectedAcompte).getDateLivDocument().before(newValue))||
//						((IHMEnteteAcompte)selectedAcompte).getDateLivDocument()==null ){
//					taDocument.setDateLivDocument(newValue);
//					((IHMEnteteAcompte)selectedAcompte).setDateLivDocument(newValue);
//					}
			}
		}
		if(champ.equals(Const.C_DATE_ECH_DOCUMENT)
//				||champ.equals(Const.C_DATE_LIV_DOCUMENT)
				){
			if(newValue!=null){
				if(taDocument.getDateDocument()!=null && 
						taDocument.getDateDocument().after(newValue))
					newValue=taDocument.getDateDocument();
			}
		}
		return newValue;
	}
	

	


	protected void actRefreshAffectation() throws Exception {
		//rafraichissement des valeurs dans la grille
		WritableList writableListAffectation =  new WritableList(
				modelRAcompte.remplirListeFactures(taDocument,getEm()), IHMRDocument.class);
		tableViewerAffectation.setInput(writableListAffectation);
		tableViewerAffectation.selectionGrille(0);
		findExpandIem(vue.getExpandBar(), visuDesFacturesAffecteeAcompte).setExpanded(
				modelRAcompte.getListeObjet().size()>0);
	}

	public PaInfosAdresse getPaInfosAdresseLiv() {
		return paInfosAdresseLiv;
	}

	public void setPaInfosAdresseLiv(PaInfosAdresse paInfosAdresseLiv) {
		this.paInfosAdresseLiv = paInfosAdresseLiv;
	}

	public PaInfosAdresse getPaInfosAdresseFact() {
		return paInfosAdresseFact;
	}

	public void setPaInfosAdresseFact(PaInfosAdresse paInfosAdresseFact) {
		this.paInfosAdresseFact = paInfosAdresseFact;
	}

//	public PaInfosCondPaiement getPaInfosCondPaiement() {
//		return paInfosCondPaiement;
//	}
//
//	public void setPaInfosCondPaiement(PaInfosCondPaiement paInfosCondPaiement) {
//		this.paInfosCondPaiement = paInfosCondPaiement;
//	}

	public PaIdentiteTiers getPaIdentiteTiers() {
		return paIdentiteTiers;
	}

	public void setPaIdentiteTiers(PaIdentiteTiers paIdentiteTiers) {
		this.paIdentiteTiers = paIdentiteTiers;
	}

	public DefaultFrameGrilleSansBouton getVisuDesFacturesAffecteeAcompte() {
		return visuDesFacturesAffecteeAcompte;
	}

	public void setVisuDesFacturesAffecteeAcompte(
			DefaultFrameGrilleSansBouton visuDesFacturesAffecteeAcompte) {
		this.visuDesFacturesAffecteeAcompte = visuDesFacturesAffecteeAcompte;
	}

	private class HandlerafficherTiers extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				actSelectionTiers();
			} catch (Exception e) {
				logger.error("", e);
			}
			return event;
		}
	}
	
	protected void actSelectionTiers() throws Exception {
		try{
			if(taDocument!=null && taDocument.getTaTiers()!=null &&
					taDocument.getTaTiers().getIdTiers()!=0){
			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().
			getActivePage().openEditor(new EditorInputTiers(), 
					TiersMultiPageEditor.ID_EDITOR);
			LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);

			ParamAfficheTiers paramAfficheTiers = new ParamAfficheTiers();
			paramAfficheTiers.setIdTiers(String.valueOf(taDocument.getTaTiers().getIdTiers()));

			((AbstractLgrMultiPageEditor)e).findMasterController().configPanel(paramAfficheTiers);
//			((LgrEditorPart)e).setPanel(((LgrEditorPart)e).getController().getVue());
//			((LgrEditorPart)e).getController().configPanel(paramAfficheTiers);
			}
		}catch (Exception e) {
			logger.error("",e);
		}	
	}

}




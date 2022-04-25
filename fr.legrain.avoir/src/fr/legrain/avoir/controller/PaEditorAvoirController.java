package fr.legrain.avoir.controller;

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

import fr.legrain.avoir.avoirPLugin;
import fr.legrain.avoir.divers.ModelRAvoir;
import fr.legrain.avoir.divers.ParamAfficheLAvoir;
import fr.legrain.avoir.ecran.PaEditorAvoir;
import fr.legrain.avoir.editor.EditorAvoir;
import fr.legrain.avoir.editor.EditorInputAvoir;
import fr.legrain.avoir.preferences.PreferenceConstants;
import fr.legrain.document.DocumentPlugin;
import fr.legrain.document.controller.MessagesEcran;
import fr.legrain.document.divers.TypeDoc;
import fr.legrain.document.ecran.PaInfosCondPaiement;
import fr.legrain.documents.dao.IDocumentTiersDAO;
import fr.legrain.documents.dao.TaAvoir;
import fr.legrain.documents.dao.TaAvoirDAO;
import fr.legrain.documents.dao.TaInfosAvoir;
import fr.legrain.documents.dao.TaInfosAvoirDAO;
import fr.legrain.documents.dao.TaLAvoir;
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
import fr.legrain.gestCom.Module_Document.IHMAideAvoir;
import fr.legrain.gestCom.Module_Document.IHMEnteteAvoir;
import fr.legrain.gestCom.Module_Document.IHMRDocument;
import fr.legrain.gestCom.Module_Tiers.IHMAdresseInfosFacturation;
import fr.legrain.gestCom.Module_Tiers.IHMAdresseInfosLivraison;
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
public class PaEditorAvoirController extends JPABaseControllerSWTStandard
		implements   RetourEcranListener,
		SWTModificationDocumentListener, ChangeModeListener {

	static Logger logger = Logger.getLogger(PaEditorAvoirController.class.getName());
	private PaEditorAvoir vue = null;
	private Integer idTiersDocumentOriginal = null; //ID du tiers lorsque l'on remonte on document, ne devrait pas etre changé ailleur
	//private Impression impressionAvoir = null ;
	/** 01/03/2010 modifier les editions (zhaolin) **/
	BaseImpressionEdition baseImpressionEdition = null;
	/************************************************/
	private boolean desactiveValidateCodeDocument=false;
	
	private DefaultFrameGrilleSansBouton visuDesFacturesAffecteeAvoir = null;
	private LgrTableViewer tableViewerAffectation;
	private IObservableValue selectedRAvoir; ;
	private ModelRAvoir modelRAvoir;
	private TypeDoc typeDocPresent = TypeDoc.getInstance();

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

	protected Map<Control, String> mapComposantChampsAdresse = null;
	protected Map<Control, String> mapComposantChampsAdresseLiv = null;
	protected Map<Control, String> mapComposantChampsCPaiement = null;

//	private List<TaAvoir> listeDocument = null;
	private List<TaTiers>listeTiers = null;
	
	private TaAvoirDAO dao = null;//new TaAvoirDAO();
	
	private Object ecranAppelant = null;
	private IHMEnteteAvoir ihmOldEnteteAvoir;
	private Realm realm;
	private DataBindingContext dbc;
	private Object selectedAvoir = new IHMEnteteAvoir();
	private String nomClassController = this.getClass().getSimpleName();
	private Class classModel = IHMEnteteAvoir.class;
	
	private ComboViewer viewerComboLocalisationTVA;
	
	private ModelGeneralObjet<IHMEnteteAvoir,TaAvoirDAO,TaAvoir> modelEnteteAvoir /*= new ModelGeneralObjet<IHMEnteteAvoir,TaAvoirDAO,TaAvoir>(dao,classModel)*/;
	private String typeAdresseFacturation=DocumentPlugin.getDefault().getPreferenceStore().getString(fr.legrain.document.preferences.PreferenceConstants.TYPE_ADRESSE_FACTURATION);
	private String typeAdresseLivraison=DocumentPlugin.getDefault().getPreferenceStore().getString(fr.legrain.document.preferences.PreferenceConstants.TYPE_ADRESSE_BONLIV);
	
	private TaInfosAvoirDAO daoInfosAvoir = null;//new TaInfosAvoirDAO();
	private TaAdresseDAO daoAdresseFact = null;//new TaAdresseDAO();
	private Class classModelAdresseFact = IHMAdresseInfosFacturation.class;
	private ModelGeneralObjet<IHMAdresseInfosFacturation,TaAdresseDAO,TaAdresse> modelAdresseFact /*= new ModelGeneralObjet<IHMAdresseInfosFacturation,TaAdresseDAO,TaAdresse>(daoAdresseFact,classModelAdresseFact)*/;
	private Object selectedAdresseFact = new IHMAdresseInfosFacturation();
	private DataBindingContext dbcAdresseFact = null;

	private TaAdresseDAO daoAdresseLiv = null;//new TaAdresseDAO();
	private Class classModelAdresseLiv = IHMAdresseInfosLivraison.class;
	private ModelGeneralObjet<IHMAdresseInfosLivraison,TaAdresseDAO,TaAdresse> modelAdresseLiv /*= new ModelGeneralObjet<IHMAdresseInfosLivraison,TaAdresseDAO,TaAdresse>(daoAdresseLiv,classModelAdresseLiv)*/;
	private Object selectedAdresseLiv = new IHMAdresseInfosLivraison();
	private DataBindingContext dbcAdresseLiv = null;

	private TaCPaiementDAO daoCPaiement = null;//new TaCPaiementDAO();
	private Object selectedCPaiement = new IHMInfosCPaiement();
	private Class classModelCPaiement = IHMInfosCPaiement.class;
	private ModelGeneralObjet<IHMInfosCPaiement,TaCPaiementDAO,TaCPaiement> modelCPaiement/* = new ModelGeneralObjet<IHMInfosCPaiement,TaCPaiementDAO,TaCPaiement>(daoCPaiement,classModelCPaiement)*/;
	private DataBindingContext dbcCPaiement = null;

	protected ContentProposalAdapter codeDocumentContentProposal;
	private ContentProposalAdapter tiersContentProposal;
	private MapChangeListener changeListener = new MapChangeListener();

	private PaLigneAvoirController controllerLigne = null;
	private PaEditorAvoirTotauxController controllerTotaux = null;
	
	private LgrDozerMapper<TaAdresse,IHMAdresseInfosFacturation> mapperModelToUIAdresseInfosDocument = new LgrDozerMapper<TaAdresse,IHMAdresseInfosFacturation>();
	private LgrDozerMapper<TaAdresse,IHMAdresseInfosLivraison> mapperModelToUIAdresseLivInfosDocument = new LgrDozerMapper<TaAdresse,IHMAdresseInfosLivraison>();
	private LgrDozerMapper<TaCPaiement,IHMInfosCPaiement> mapperModelToUICPaiementInfosDocument = new LgrDozerMapper<TaCPaiement,IHMInfosCPaiement>();
	
	private LgrDozerMapper<TaInfosAvoir,IHMInfosCPaiement> mapperModelToUIInfosDocVersCPaiement = new LgrDozerMapper<TaInfosAvoir,IHMInfosCPaiement>();
	private LgrDozerMapper<TaInfosAvoir,IHMAdresseInfosFacturation> mapperModelToUIInfosDocVersAdresseFact = new LgrDozerMapper<TaInfosAvoir,IHMAdresseInfosFacturation>();
	private LgrDozerMapper<TaInfosAvoir,IHMAdresseInfosLivraison> mapperModelToUIInfosDocVersAdresseLiv = new LgrDozerMapper<TaInfosAvoir,IHMAdresseInfosLivraison>();
		
	private LgrDozerMapper<IHMInfosCPaiement,TaInfosAvoir> mapperUIToModelCPaiementVersInfosDoc = new LgrDozerMapper<IHMInfosCPaiement, TaInfosAvoir>();
	private LgrDozerMapper<IHMAdresseInfosFacturation,TaInfosAvoir> mapperUIToModelAdresseFactVersInfosDoc = new LgrDozerMapper<IHMAdresseInfosFacturation, TaInfosAvoir>();
	private LgrDozerMapper<IHMAdresseInfosLivraison,TaInfosAvoir> mapperUIToModelAdresseLivVersInfosDoc = new LgrDozerMapper<IHMAdresseInfosLivraison, TaInfosAvoir>();
	
	private LgrDozerMapper<TaAvoir,TaInfosAvoir> mapperUIToModelDocumentVersInfosDoc = new LgrDozerMapper<TaAvoir, TaInfosAvoir>();	
	
	private LgrDozerMapper<IHMEnteteAvoir,TaAvoir> mapperUIToModel = new LgrDozerMapper<IHMEnteteAvoir,TaAvoir>();
	private LgrDozerMapper<TaAvoir,IHMEnteteAvoir> mapperModelToUI = new LgrDozerMapper<TaAvoir,IHMEnteteAvoir>();
	
	private TaAvoir taDocument = null;
	private TaInfosAvoir taInfosDocument = null;
	private TaInfoEntreprise infoEntreprise;
	private PaInfosCondPaiement paInfosCondPaiement = null;
	private PaInfosAdresse paInfosAdresseLiv = null;
	private PaInfosAdresse paInfosAdresseFact = null;
	
	private Menu popupMenuFormulaire = null;
	private Menu popupMenuGrille = null;
	
	public PaEditorAvoirController() {
	}
	
	public PaEditorAvoirController(PaEditorAvoir vue) {
		this(vue,null);
	}

	public PaEditorAvoirController(PaEditorAvoir vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaAvoirDAO(getEm());
		daoInfosAvoir = new TaInfosAvoirDAO(getEm());
		daoAdresseFact = new TaAdresseDAO(getEm());
		daoAdresseLiv = new TaAdresseDAO(getEm());
		daoCPaiement = new TaCPaiementDAO(getEm());
		try {

			setVue(vue);
			try {
				setTypeAdresseFacturation(DocumentPlugin.getDefault().getPreferenceStore().getString(fr.legrain.document.preferences.PreferenceConstants.TYPE_ADRESSE_FACTURATION));
				setTypeAdresseLivraison(DocumentPlugin.getDefault().getPreferenceStore().getString(fr.legrain.document.preferences.PreferenceConstants.TYPE_ADRESSE_BONLIV));
			} catch (Exception e) {				
			}
			vue.getShell().addShellListener(this);
			vue.getShell().addTraverseListener(new Traverse());
//			impressionAvoir= new Impression(vue.getShell());
			
			/** 01/03/2010 modifier les editions (zhaolin) **/
			baseImpressionEdition = new BaseImpressionEdition(vue.getShell(),getEm());
			/************************************************/
			
			vue.getDateTimeDATE_DOCUMENT().addTraverseListener(new DateTraverse());
			vue.getDateTimeDATE_ECH_DOCUMENT().addTraverseListener(new DateTraverse());
			vue.getDateTimeDATE_LIV_DOCUMENT().addTraverseListener(new DateTraverse());

//			addDestroyListener(ibTaTable);
			setDaoStandard(dao);

//			this.swtAvoir.addModificationDocumentListener(dao);
//			this.swtAvoir.addModificationDocumentListener(this);
//			this.swtAvoir.addChangeModeListener(this);
			
//			taAvoir.addModificationDocumentListener(controllerLigne);
//			taAvoir.addModificationDocumentListener(controllerTotaux);
//			taAvoir.addModificationDocumentListener(this);
			
//			taAvoir.addChangeModeListener(this);

			// à l'insertion d'une nouvelle facture, le champ est contrôlé à
			// vide, ce qui fait
			// lorsque que l'on sort de la zone sans l'avoir rempli, il ne la
			// re-contrôle pas
			// d'où le rajout de se focusListener pour le forcer à remplir la
			// zone
			vue.getTfLIBELLE_DOCUMENT().addFocusListener(new FocusAdapter() {
				public void focusLost(FocusEvent e) {
					try {
//						ctrlUnChampsSWT(((PaEditorAvoir) getVue()).getTfLIBELLE_AVOIR());
						validateUIField(Const.C_LIBELLE_DOCUMENT,((PaEditorAvoir) getVue()).getTfLIBELLE_DOCUMENT());
					} catch (Exception e1) {
						logger.debug("", e1);
					}
				}
			});
			initController();

			vue.getDateTimeDATE_ECH_DOCUMENT().addFocusListener(dateFocusListener);
			vue.getDateTimeDATE_DOCUMENT().addFocusListener(dateFocusListener);
			vue.getDateTimeDATE_LIV_DOCUMENT().addFocusListener(dateFocusListener);
		} catch (Exception e) {
			logger.debug("", e);
		}
	}

	private void initVue() {
		paInfosAdresseFact =  new PaInfosAdresse(vue.getExpandBar(), SWT.PUSH);
		addExpandBarItem(vue.getExpandBar(), paInfosAdresseFact, "Adresse de facturation",
				LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ADRESSE_FAC));

		paInfosAdresseLiv =  new PaInfosAdresse(vue.getExpandBar(), SWT.PUSH);
		addExpandBarItem(vue.getExpandBar(), paInfosAdresseLiv, "Adresse de livraison",
				LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ADRESSE_LIV));

		paInfosCondPaiement = new PaInfosCondPaiement(vue
				.getExpandBar(), SWT.PUSH);
		addExpandBarItem(vue.getExpandBar(), paInfosCondPaiement,
				"Conditions de paiement", LibrairiesEcranPlugin.ir
						.get(LibrairiesEcranPlugin.IMAGE_CONDITION_PAIEMENT));
		// Boolean affiche_expands =
		// DocumentPlugin.getDefault().getPreferenceStore().getBoolean(fr.legrain.document.preferences.PreferenceConstants.AFF_ADRESSE);

		visuDesFacturesAffecteeAvoir = new DefaultFrameGrilleSansBouton(vue.getExpandBar(), SWT.PUSH);
		addExpandBarItem(vue.getExpandBar(),visuDesFacturesAffecteeAvoir, "Visualisation des factures affectées à l'avoir",
				LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_GEN_DOCUMENT));
		
		vue.getExpandBar().getItem(0).setExpanded(
						DocumentPlugin.getDefault().getPreferenceStore().getBoolean(
										fr.legrain.document.preferences.PreferenceConstants.AFF_ADRESSE));
		vue.getExpandBar().getItem(1).setExpanded(
						DocumentPlugin.getDefault().getPreferenceStore().getBoolean(
										fr.legrain.document.preferences.PreferenceConstants.AFF_ADRESSE_LIV));
		findExpandIem(vue.getExpandBar(), paInfosCondPaiement).setExpanded(
				DocumentPlugin.getDefault().getPreferenceStore().getBoolean(
								fr.legrain.document.preferences.PreferenceConstants.AFF_CPAIEMENT));
	}
	
	protected void actRefreshAffectation() throws Exception {
		//rafraichissement des valeurs dans la grille
		WritableList writableListAffectation =  new WritableList(
				modelRAvoir.remplirListeFactures(taDocument,getEm()), IHMRDocument.class);
		tableViewerAffectation.setInput(writableListAffectation);
		tableViewerAffectation.selectionGrille(0);
		findExpandIem(vue.getExpandBar(), visuDesFacturesAffecteeAvoir).setExpanded(
				modelRAvoir.getListeObjet().size()>0);
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
			// vue.getCbImprimer().setSelection(AvoirPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.IMPRIMER_AUTO))
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
			 
			bind(vue);
			initEtatBouton(true);

			vue.getTfCODE_DOCUMENT().setEditable(false);

		} catch (Exception e) {
			if (e.getMessage() != null)
				vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaArticlesController", e);
		}
	}

	public void bindAffectation() {
		try {
			modelRAvoir = new ModelRAvoir();
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());
			((DefaultFrameGrilleSansBouton)findExpandIem(vue.getExpandBar(), visuDesFacturesAffecteeAvoir).
					getControl()).getLaTitreGrille().setText("Liste des factures affectées à l'avoir");
		
			tableViewerAffectation = new LgrTableViewer(((DefaultFrameGrilleSansBouton)findExpandIem(vue.getExpandBar(), visuDesFacturesAffecteeAvoir).
					getControl()).getGrille());
			tableViewerAffectation.createTableCol(((DefaultFrameGrilleSansBouton)findExpandIem(vue.getExpandBar(), visuDesFacturesAffecteeAvoir).
					getControl()).getGrille(), "AffectationsAcompte",
					Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			String[]listeChampAffectation = tableViewerAffectation.setListeChampGrille("AffectationsAcompte",
					Const.C_FICHIER_LISTE_CHAMP_GRILLE);

			selectedRAvoir = ViewersObservables
			.observeSingleSelection(tableViewerAffectation);

			LgrViewerSupport.bind(tableViewerAffectation, 
					 new WritableList(modelRAvoir.remplirListeFactures(taDocument,getEm()), IHMRDocument.class),
					BeanProperties.values(listeChampAffectation)
			);
			((DefaultFrameGrilleSansBouton)findExpandIem(vue.getExpandBar(), visuDesFacturesAffecteeAvoir).
					getControl()).getGrille().addMouseListener(
					new MouseAdapter() {

						public void mouseDoubleClick(MouseEvent e) {
							String idEditor = typeDocPresent.getEditorDoc()
							.get(
									((IHMRDocument) selectedRAvoir
											.getValue())
											.getTypeDocument());
					String valeurIdentifiant = ((IHMRDocument) selectedRAvoir
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

	public void bind(PaEditorAvoir PaEditorAvoirSWT) {
		try {
			modelEnteteAvoir = new ModelGeneralObjet<IHMEnteteAvoir,TaAvoirDAO,TaAvoir>(dao,classModel);
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

//			modelEnteteFacture.remplirListe();
			if (modelEnteteAvoir.getListeObjet().isEmpty()) {
				modelEnteteAvoir.getListeObjet().add(new IHMEnteteAvoir());
			}
			selectedAvoir = modelEnteteAvoir.getListeObjet().getFirst();
			
			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			setDbcStandard(dbc);

			bindingFormSimple(dbc, realm, selectedAvoir, classModel);
			bindAdresse();
			bindAdresseLiv();
			bindCPaiement();
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

	public void bindAdresseLiv() {
		try {
			modelAdresseLiv = new ModelGeneralObjet<IHMAdresseInfosLivraison,TaAdresseDAO,TaAdresse>(daoAdresseLiv,classModelAdresseLiv);
//			modelAdresseLiv.remplirListe();
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
		
		mapInfosVerifSaisie.put(vue.getTfCODE_DOCUMENT(), new InfosVerifSaisie(new TaAvoir(),Const.C_CODE_DOCUMENT,null));
		mapInfosVerifSaisie.put(vue.getTfCODE_TIERS(), new InfosVerifSaisie(new TaTiers(),Const.C_CODE_TIERS,null));
		mapInfosVerifSaisie.put(vue.getTfLIBELLE_DOCUMENT(), new InfosVerifSaisie(new TaAvoir(),Const.C_LIBELLE_DOCUMENT,null));
		
		mapInfosVerifSaisie.put(((PaInfosAdresse) vue.getExpandBar()
				.getItem(0).getControl()).getTfAdresse1(), new InfosVerifSaisie(new TaInfosAvoir(),Const.C_ADRESSE1_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse) vue.getExpandBar()
				.getItem(0).getControl()).getTfAdresse2(), new InfosVerifSaisie(new TaInfosAvoir(),Const.C_ADRESSE2_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse) vue.getExpandBar()
				.getItem(0).getControl()).getTfAdresse3(), new InfosVerifSaisie(new TaInfosAvoir(),Const.C_ADRESSE3_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse) vue.getExpandBar()
				.getItem(0).getControl())
				.getTfCodePostal(), new InfosVerifSaisie(new TaInfosAvoir(),Const.C_CODEPOSTAL_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse) vue.getExpandBar()
				.getItem(0).getControl()).getTfVille(), new InfosVerifSaisie(new TaInfosAvoir(),Const.C_VILLE_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse) vue.getExpandBar()
				.getItem(0).getControl()).getTfPays(), new InfosVerifSaisie(new TaInfosAvoir(),Const.C_PAYS_INFOS_DOCUMENT,null));

		mapInfosVerifSaisie.put(((PaInfosAdresse) vue
				.getExpandBar().getItem(1).getControl())
				.getTfAdresse1(), new InfosVerifSaisie(new TaInfosAvoir(),Const.C_ADRESSE1_LIV_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse) vue
				.getExpandBar().getItem(1).getControl())
				.getTfAdresse2(), new InfosVerifSaisie(new TaInfosAvoir(),Const.C_ADRESSE2_LIV_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse) vue
				.getExpandBar().getItem(1).getControl())
				.getTfAdresse3(), new InfosVerifSaisie(new TaInfosAvoir(),Const.C_ADRESSE3_LIV_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse) vue
				.getExpandBar().getItem(1).getControl())
				.getTfCodePostal(), new InfosVerifSaisie(new TaInfosAvoir(),Const.C_CODEPOSTAL_LIV_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse) vue
				.getExpandBar().getItem(1).getControl())
				.getTfVille(), new InfosVerifSaisie(new TaInfosAvoir(),Const.C_VILLE_LIV_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse) vue
				.getExpandBar().getItem(1).getControl())
				.getTfPays(), new InfosVerifSaisie(new TaInfosAvoir(),Const.C_PAYS_LIV_INFOS_DOCUMENT,null));
				
		mapInfosVerifSaisie.put(((PaInfosCondPaiement) vue
				.getExpandBar().getItem(2).getControl())
				.getTfCODE_C_PAIEMENT(), new InfosVerifSaisie(new TaInfosAvoir(),Const.C_CODE_C_PAIEMENT,null));
		mapInfosVerifSaisie.put(((PaInfosCondPaiement) vue
				.getExpandBar().getItem(2).getControl())
				.getTfLIB_C_PAIEMENT(), new InfosVerifSaisie(new TaInfosAvoir(),Const.C_LIB_C_PAIEMENT,null));
		mapInfosVerifSaisie.put(((PaInfosCondPaiement) vue
				.getExpandBar().getItem(2).getControl())
				.getTfREPORT_C_PAIEMENT(), new InfosVerifSaisie(new TaInfosAvoir(),Const.C_REPORT_C_PAIEMENT,null));
		mapInfosVerifSaisie.put(((PaInfosCondPaiement) vue
				.getExpandBar().getItem(2).getControl())
				.getTfFIN_MOIS_C_PAIEMENT(), new InfosVerifSaisie(new TaInfosAvoir(),Const.C_FIN_MOIS_C_PAIEMENT,null));
		
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

		if (mapComposantChampsAdresse == null)
			mapComposantChampsAdresse = new LinkedHashMap<Control, String>();

		if (mapComposantChampsAdresseLiv == null)
			mapComposantChampsAdresseLiv = new LinkedHashMap<Control, String>();

		if (mapComposantChampsCPaiement == null)
			mapComposantChampsCPaiement = new LinkedHashMap<Control, String>();

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

		mapComposantChamps.put(vue.getDateTimeDATE_ECH_DOCUMENT(),
				Const.C_DATE_ECH_DOCUMENT);
		mapComposantChamps.put(vue.getDateTimeDATE_LIV_DOCUMENT(),
				Const.C_DATE_LIV_DOCUMENT);

		for (Control c : mapComposantChamps.keySet()) {
			listeComposantEntete.add(c);
		}

		mapComposantChampsAdresse.put(((PaInfosAdresse) vue.getExpandBar()
				.getItem(0).getControl()).getTfAdresse1(),
				Const.C_ADRESSE1);
		mapComposantChampsAdresse.put(((PaInfosAdresse) vue.getExpandBar()
				.getItem(0).getControl()).getTfAdresse2(),
				Const.C_ADRESSE2);
		mapComposantChampsAdresse.put(((PaInfosAdresse) vue.getExpandBar()
				.getItem(0).getControl()).getTfAdresse3(),
				Const.C_ADRESSE3);
		mapComposantChampsAdresse.put(((PaInfosAdresse) vue.getExpandBar()
				.getItem(0).getControl())
				.getTfCodePostal(), Const.C_CODEPOSTAL);
		mapComposantChampsAdresse.put(((PaInfosAdresse) vue.getExpandBar()
				.getItem(0).getControl()).getTfVille(),
				Const.C_VILLE);
		mapComposantChampsAdresse.put(((PaInfosAdresse) vue.getExpandBar()
				.getItem(0).getControl()).getTfPays(),
				Const.C_PAYS);		
		for (Control c : mapComposantChampsAdresse.keySet()) {
			listeComposantAdresse.add(c);
		}
		listeComposantAdresse.add(((PaInfosAdresse) vue.getExpandBar()
				.getItem(0).getControl()).getBtnReinitialiser());

		mapComposantChampsAdresseLiv.put(((PaInfosAdresse) vue
				.getExpandBar().getItem(1).getControl())
				.getTfAdresse1(), Const.C_ADRESSE1_LIV);
		mapComposantChampsAdresseLiv.put(((PaInfosAdresse) vue
				.getExpandBar().getItem(1).getControl())
				.getTfAdresse2(), Const.C_ADRESSE2_LIV);
		mapComposantChampsAdresseLiv.put(((PaInfosAdresse) vue
				.getExpandBar().getItem(1).getControl())
				.getTfAdresse3(), Const.C_ADRESSE3_LIV);
		mapComposantChampsAdresseLiv.put(((PaInfosAdresse) vue
				.getExpandBar().getItem(1).getControl())
				.getTfCodePostal(), Const.C_CODEPOSTAL_LIV);
		mapComposantChampsAdresseLiv.put(((PaInfosAdresse) vue
				.getExpandBar().getItem(1).getControl())
				.getTfVille(), Const.C_VILLE_LIV);
		mapComposantChampsAdresseLiv.put(((PaInfosAdresse) vue
				.getExpandBar().getItem(1).getControl())
				.getTfPays(), Const.C_PAYS_LIV);
		mapComposantChampsAdresseLiv.put(((PaInfosAdresse) vue
				.getExpandBar().getItem(1).getControl())
				.getTfPays(), Const.C_PAYS_LIV);		
		for (Control c : mapComposantChampsAdresseLiv.keySet()) {
			listeComposantAdresse_Liv.add(c);
		}
		listeComposantAdresse_Liv.add(((PaInfosAdresse) vue.getExpandBar()
				.getItem(1).getControl()).getBtnReinitialiser());

		mapComposantChampsCPaiement.put(((PaInfosCondPaiement) vue
				.getExpandBar().getItem(2).getControl())
				.getTfCODE_C_PAIEMENT(),
				Const.C_CODE_C_PAIEMENT);
		mapComposantChampsCPaiement.put(((PaInfosCondPaiement) vue
				.getExpandBar().getItem(2).getControl())
				.getTfLIB_C_PAIEMENT(),
				Const.C_LIB_C_PAIEMENT);
		mapComposantChampsCPaiement.put(((PaInfosCondPaiement) vue
				.getExpandBar().getItem(2).getControl())
				.getTfREPORT_C_PAIEMENT(),
				Const.C_REPORT_C_PAIEMENT);
		mapComposantChampsCPaiement.put(((PaInfosCondPaiement) vue
				.getExpandBar().getItem(2).getControl())
				.getTfFIN_MOIS_C_PAIEMENT(),
				Const.C_FIN_MOIS_C_PAIEMENT);
		for (Control c : mapComposantChampsCPaiement.keySet()) {
			listeComposantCPaiement.add(c);
		}
//		listeComposantCPaiement.add(((PaInfosCondPaiement) vue
//				.getExpandBar().getItem(2).getControl())
//				.getBtnReinitialiser());
		listeComposantCPaiement.add(((PaInfosCondPaiement) vue
				.getExpandBar().getItem(2).getControl())
				.getBtnAppliquer());

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
		listeComposantFocusable.add(vue.getDateTimeDATE_ECH_DOCUMENT());
		listeComposantFocusable.add(vue.getDateTimeDATE_LIV_DOCUMENT());
		listeComposantFocusable.add(vue.getComboLocalisationTVA());

		listeComposantFocusable.add(((PaInfosAdresse) vue.getExpandBar()
				.getItem(0).getControl()).getTfAdresse1());
		listeComposantFocusable.add(((PaInfosAdresse) vue.getExpandBar()
				.getItem(0).getControl()).getTfAdresse2());
		listeComposantFocusable.add(((PaInfosAdresse) vue.getExpandBar()
				.getItem(0).getControl()).getTfAdresse3());
		listeComposantFocusable.add(((PaInfosAdresse) vue.getExpandBar()
				.getItem(0).getControl()).getTfCodePostal());
		listeComposantFocusable.add(((PaInfosAdresse) vue.getExpandBar()
				.getItem(0).getControl()).getTfVille());
		listeComposantFocusable.add(((PaInfosAdresse) vue.getExpandBar()
				.getItem(0).getControl()).getTfPays());
		listeComposantFocusable.add(((PaInfosAdresse) vue.getExpandBar()
				.getItem(0).getControl()).getBtnReinitialiser());
		
		listeComposantFocusable.add(((PaInfosAdresse) vue.getExpandBar()
				.getItem(1).getControl()).getTfAdresse1());
		listeComposantFocusable.add(((PaInfosAdresse) vue.getExpandBar()
				.getItem(1).getControl()).getTfAdresse2());
		listeComposantFocusable.add(((PaInfosAdresse) vue.getExpandBar()
				.getItem(1).getControl()).getTfAdresse3());
		listeComposantFocusable.add(((PaInfosAdresse) vue.getExpandBar()
				.getItem(1).getControl()).getTfCodePostal());
		listeComposantFocusable.add(((PaInfosAdresse) vue.getExpandBar()
				.getItem(1).getControl()).getTfVille());
		listeComposantFocusable.add(((PaInfosAdresse) vue.getExpandBar()
				.getItem(1).getControl()).getTfPays());
		listeComposantFocusable.add(((PaInfosAdresse) vue.getExpandBar()
				.getItem(1).getControl()).getBtnReinitialiser());

		listeComposantFocusable.add(((PaInfosCondPaiement) vue
				.getExpandBar().getItem(2).getControl())
				.getTfCODE_C_PAIEMENT());
//		listeComposantFocusable.add(((PaInfosCondPaiement) vue
//				.getExpandBar().getItem(2).getControl())
//				.getTfLIB_C_PAIEMENT());
		listeComposantFocusable.add(((PaInfosCondPaiement) vue
				.getExpandBar().getItem(2).getControl())
				.getTfREPORT_C_PAIEMENT());
		listeComposantFocusable.add(((PaInfosCondPaiement) vue
				.getExpandBar().getItem(2).getControl())
				.getTfFIN_MOIS_C_PAIEMENT());
//		listeComposantFocusable.add(((PaInfosCondPaiement) vue
//				.getExpandBar().getItem(2).getControl())
//				.getBtnReinitialiser());
		listeComposantFocusable.add(((PaInfosCondPaiement) vue
				.getExpandBar().getItem(2).getControl())
				.getBtnAppliquer());

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
		mapCommand.put(C_COMMAND_DOCUMENT_REINIT_INFOSTIERS_ID, handlerReinitInfosTiers);
		mapCommand.put(C_COMMAND_DOCUMENT_AFFICHER_TIERS_ID, handlerafficherTiers);
		mapCommand.put(C_COMMAND_DOCUMENT_CREATEDOC_ID, handlerCreateDoc);
		mapCommand.put(C_COMMAND_DOCUMENT_CREATEMODELE_ID, handlerCreateModele);
		mapCommand.put(C_COMMAND_GLOBAL_SELECTION_ID, handlerSelection);
//		mapCommand.put(C_COMMAND_DOCUMENT_AFFICHER_TIERS_ID, handlerafficherTiers);

		
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
		
		mapActions.put(((PaInfosAdresse) vue.getExpandBar()
				.getItem(0).getControl()).getBtnReinitialiser(),C_COMMAND_DOCUMENT_REINIT_ADR_FACT_ID);

		mapActions.put(((PaInfosAdresse) vue.getExpandBar()
				.getItem(1).getControl()).getBtnReinitialiser(),C_COMMAND_DOCUMENT_REINIT_ADR_LIV_ID);

//		mapActions.put(((PaInfosCondPaiement) vue.getExpandBar()
//				.getItem(2).getControl()).getBtnReinitialiser(),C_COMMAND_DOCUMENT_REINIT_CPAIEMENT_ID);
		
		mapActions.put(((PaInfosCondPaiement) vue.getExpandBar()
				.getItem(2).getControl()).getBtnAppliquer(),C_COMMAND_DOCUMENT_APPLIQUER_CPAIEMENT_ID);
		
//		mapActions.put(((PaIdentiteTiers) vue.getExpandBar()
//				.getItem(3).getControl()).getBtnReinitialiser(),C_COMMAND_DOCUMENT_REINIT_INFOSTIERS_ID);

		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID,
				C_COMMAND_GLOBAL_REFRESH_ID ,C_COMMAND_DOCUMENT_CREATEDOC_ID,C_COMMAND_DOCUMENT_CREATEMODELE_ID, C_COMMAND_GLOBAL_SELECTION_ID};
		mapActions.put(null, tabActionsAutres);
		
		vue.getBtnAutres().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				popupMenuFormulaire.setVisible(true);
			}
		});
		
	}
	public PaEditorAvoirController getThis() {
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
					boolean demande=!((IHMEnteteAvoir) selectedAvoir).factureEstVide(taDocument.dateDansExercice(new Date()));
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
							((IHMEnteteAvoir)selectedAvoir).setLibelleDocument("Avoir N°"+taDocument.getCodeDocument()+" - "+nomTiers);
							
							if(vue.getCbTTC().isEnabled()){
								taDocument.setTtc(u.getTtcTiers());
								((IHMEnteteAvoir)selectedAvoir).setTtc(LibConversion.intToBoolean(taDocument.getTtc()));
							}
							
							changementTiers(true);
						}	
						if(getFocusAvantAideSWT().equals(vue.getTfCODE_DOCUMENT())){
							TaAvoir u = null;
							TaAvoirDAO t = new TaAvoirDAO(getEm());
							u = t.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
							t = null;
							taDocument=u;
							taDocument.setLegrain(true);
							try {
								remonterDocument(taDocument.getCodeDocument());
								//remonterDocument(vue.getTfCODE_AVOIR().getText());
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
//					.equals(vue.getTfCODE_AVOIR())) {
//				try {
//					remonterDocument(vue.getTfCODE_AVOIR().getText());
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
		if(((IHMEnteteAvoir)selectedAvoir).getIdDocument()==0 || appliquer) {
			Integer report = null;
			Integer finDeMois = null;
			if(((IHMInfosCPaiement)selectedCPaiement)!=null) { 
				if(((IHMInfosCPaiement)selectedCPaiement).getReportCPaiement()!=null)
					report = ((IHMInfosCPaiement)selectedCPaiement).getReportCPaiement();
				if(((IHMInfosCPaiement)selectedCPaiement).getFinMoisCPaiement()!=null)
					finDeMois = ((IHMInfosCPaiement)selectedCPaiement).getFinMoisCPaiement();
			}
			((IHMEnteteAvoir)selectedAvoir).setDateEchDocument(taDocument.calculDateEcheance(report, finDeMois));
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
			if(((IHMEnteteAvoir)selectedAvoir).getIdDocument()!=0 && 
					((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
					|| (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0))) {
				if(!idTiersDocumentOriginal.equals(((IHMEnteteAvoir)selectedAvoir).getIdTiers())){
					//idTiersDocumentOriginal=((IHMEnteteAvoir)selectedEnteteAvoir).getID_TIERS();
					//on change de tiers sur une facture deja enregistree
					
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
			//map.put("a.taTiers."+Const.C_ID_TIERS,new String[]{"=",LibConversion.integerToString(taAvoir.getTaTiers().getIdTiers())});
			
//			map.put("a.taTiers."+Const.C_ID_TIERS,new String[]{"=",LibConversion.integerToString(taAvoir.getTaTiers().getIdTiers())});
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
			calculDateEcheance();
			
//			TaTAdrDAO taTAdrDAO = new TaTAdrDAO();
//			//taInfosAvoir = daoInfosAvoir.findByCodeAvoir(taAvoir.getCodeAvoir());
//			if(((IHMEnteteAvoir)selectedAvoir).getCodeAvoir()!=null)
//				taInfosAvoir = daoInfosAvoir.findByCodeAvoir(((IHMEnteteAvoir)selectedAvoir).getCodeAvoir());
//			else
//				taInfosAvoir = new TaInfosAvoir();
//			//on vide les modeles
//			modelAdresseLiv.getListeObjet().clear();
//			modelAdresseFact.getListeObjet().clear();
//			modelCPaiement.getListeObjet().clear();
//
//			//ajout de l'adresse de livraison inscrite dans l'infos facture
//			if(taInfosAvoir!=null) {
//				modelAdresseLiv.getListeObjet().add(mapperModelToUIInfosDocVersAdresseLiv.map(taInfosAvoir, classModelAdresseLiv));
//				modelAdresseFact.getListeObjet().add(mapperModelToUIInfosDocVersAdresseFact.map(taInfosAvoir, classModelAdresseFact));
//				modelCPaiement.getListeObjet().add(mapperModelToUIInfosDocVersCPaiement.map(taInfosAvoir, classModelCPaiement));
//			}
//
//			boolean leTiersADesAdresseLiv = false;
//			boolean leTiersADesAdresseFact = false;
//			if(taAvoir.getTaTiers()!=null){
//				if(typeAdresseLivraison!=null && taTAdrDAO.findByCode(typeAdresseLivraison)!=null) { //le type d'adresse existe bien
//					leTiersADesAdresseLiv = taAvoir.getTaTiers().aDesAdressesDuType(typeAdresseLivraison); //le tiers a des adresse de ce type
//				}
//				if(typeAdresseFacturation!=null && taTAdrDAO.findByCode(typeAdresseFacturation)!=null) { //le type d'adresse existe bien
//					leTiersADesAdresseFact = taAvoir.getTaTiers().aDesAdressesDuType(typeAdresseFacturation); //le tiers a des adresse de ce type
//				}
//
//			
//			if(leTiersADesAdresseLiv) { 
//				//ajout des adresse de livraison au modele
//				for (TaAdresse taAdresse : taAvoir.getTaTiers().getTaAdresses()) {
//					if(taAdresse.getTaTAdr().getCodeTAdr().equals(typeAdresseLivraison)){
//						modelAdresseLiv.getListeObjet().add(modelAdresseLiv.getMapperModelToUI().map(taAdresse, classModelAdresseLiv));
//					}
//				}
//			}
//			
//			if(leTiersADesAdresseFact) { 
//				//ajout des adresse de facturation au modele
//				for (TaAdresse taAdresse : taAvoir.getTaTiers().getTaAdresses()) {
//					if(taAdresse.getTaTAdr().getCodeTAdr().equals(typeAdresseFacturation)){
//						modelAdresseFact.getListeObjet().add(modelAdresseFact.getMapperModelToUI().map(taAdresse, classModelAdresseFact));
//					}
//				}
//			}
//
//			//ajout des autres types d'adresse
//			for (TaAdresse taAdresse : taAvoir.getTaTiers().getTaAdresses()) {
//				if(leTiersADesAdresseLiv && !taAdresse.getTaTAdr().getCodeTAdr().equals(typeAdresseLivraison)){
//					modelAdresseLiv.getListeObjet().add(modelAdresseLiv.getMapperModelToUI().map(taAdresse, classModelAdresseLiv));
//				}else if(leTiersADesAdresseFact && !taAdresse.getTaTAdr().getCodeTAdr().equals(typeAdresseFacturation)){
//					modelAdresseFact.getListeObjet().add(modelAdresseFact.getMapperModelToUI().map(taAdresse, classModelAdresseFact));
//				} else {
//					modelAdresseFact.getListeObjet().add(modelAdresseFact.getMapperModelToUI().map(taAdresse, classModelAdresseFact));
//					modelAdresseLiv.getListeObjet().add(modelAdresseLiv.getMapperModelToUI().map(taAdresse, classModelAdresseLiv));
//				}
//			}
//			
//			if(taAvoir.getTaTiers().getTaCPaiement()==null)
//				modelCPaiement.getListeObjet().add(new IHMInfosCPaiement());
//			else
//				modelCPaiement.getListeObjet().add(modelCPaiement.getMapperModelToUI().map(taAvoir.getTaTiers().getTaCPaiement(), classModelCPaiement));
//			}
////			if(!majAdr) {//cas normal				
////				ibTaInfosAvoirAdresse.initQuery(LibConversion
////						.integerToString(((IHMEnteteAvoir) selectedEnteteAvoir)
////								.getID_AVOIR()), LibConversion
////								.integerToString(((IHMEnteteAvoir) selectedEnteteAvoir)
////										.getID_TIERS()), false,getTypeAdresseFacturation());
////				ibTaInfosAvoirAdresseLiv.initQuery(LibConversion
////						.integerToString(((IHMEnteteAvoir) selectedEnteteAvoir)
////								.getID_AVOIR()), LibConversion
////								.integerToString(((IHMEnteteAvoir) selectedEnteteAvoir)
////										.getID_TIERS()), true,getTypeAdresseLivraison());
////				ibTaCPaiement.initQuery(LibConversion
////						.integerToString(((IHMEnteteAvoir) selectedEnteteAvoir)
////								.getID_AVOIR()), LibConversion
////								.integerToString(((IHMEnteteAvoir) selectedEnteteAvoir)
////										.getID_TIERS()));
////			} else {
////				ibTaInfosAvoirAdresse.initQuery(LibConversion
////						.integerToString(0), LibConversion
////						.integerToString(((IHMEnteteAvoir) selectedEnteteAvoir)
////								.getID_TIERS()), false,getTypeAdresseFacturation());
////				ibTaInfosAvoirAdresseLiv.initQuery(LibConversion
////						.integerToString(0), LibConversion
////						.integerToString(((IHMEnteteAvoir) selectedEnteteAvoir)
////								.getID_TIERS()), true,getTypeAdresseLivraison());
////				ibTaCPaiement.initQuery(LibConversion
////						.integerToString(0), LibConversion
////						.integerToString(((IHMEnteteAvoir) selectedEnteteAvoir)
////								.getID_TIERS()));
////			}
////
////			modelAdresseFact.setQueryDataSet(ibTaInfosAvoirAdresse.getFIBQuery());
////			modelAdresseFact.remplirListe();
//			if (!modelAdresseFact.getListeObjet().isEmpty())
//				((IHMAdresseInfosFacturation) selectedAdresseFact).setIHMAdresse(modelAdresseFact.getListeObjet().getFirst());
//			else
//				((IHMAdresseInfosFacturation) selectedAdresseFact).setIHMAdresse(new IHMAdresseInfosFacturation());
//			
////			modelAdresseLiv.setQueryDataSet(ibTaInfosAvoirAdresseLiv.getFIBQuery());
////			modelAdresseLiv.remplirListe();
//			if (!modelAdresseLiv.getListeObjet().isEmpty())
//				((IHMAdresseInfosLivraison) selectedAdresseLiv).setIHMAdresse(modelAdresseLiv.getListeObjet().getFirst());
//			else
//				((IHMAdresseInfosLivraison) selectedAdresseLiv).setIHMAdresse(new IHMAdresseInfosLivraison());
//
////			modelCPaiement.setQueryDataSet(ibTaCPaiement.getFIBQuery());
////			modelCPaiement.remplirListe();
//			if (!modelCPaiement.getListeObjet().isEmpty())
//				((IHMInfosCPaiement) selectedCPaiement).setIHMInfosCPaiement(modelCPaiement.getListeObjet().getFirst());
//			else
//				((IHMInfosCPaiement) selectedCPaiement).setIHMInfosCPaiement(new IHMInfosCPaiement());			
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
		if(((IHMEnteteAvoir)selectedAvoir).getCodeDocument()!=null)
			taInfosDocument = daoInfosAvoir.findByCodeAvoir(((IHMEnteteAvoir)selectedAvoir).getCodeDocument());
		else
			taInfosDocument = new TaInfosAvoir();
		if(recharger)
			taDocument.setTaTiers(new TaTiersDAO(new JPABdLgr().getEntityManager()).findById(taDocument.getTaTiers().getIdTiers()));

		if(typeARecharger==Const.RECHARGE_ADR_FACT||typeARecharger=="")
			initialisationDesAdrFact(recharger);
		if(typeARecharger==Const.RECHARGE_ADR_LIV||typeARecharger=="")
			initialisationDesAdrLiv(recharger);
		if(typeARecharger==Const.RECHARGE_C_PAIEMENT||typeARecharger=="")
			initialisationDesCPaiement(recharger);
		if(typeARecharger==Const.RECHARGE_INFOS_TIERS||typeARecharger=="")
			initialisationDesInfosTiers(recharger);
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
//		IHMIdentiteTiers ihmInfosTiers=new IHMIdentiteTiers();
//		if(taDocument.getTaTiers()!=null) {
//			if(taInfosDocument!=null && !recharger)
//				new LgrDozerMapper<TaInfosFacture, IHMIdentiteTiers>().map(taInfosDocument, ihmInfosTiers);					
//			else
//				new LgrDozerMapper<TaTiers, IHMIdentiteTiers>().map(taDocument.getTaTiers(), ihmInfosTiers);
//			//rajout des infos non contenues dans taInfosDocument
//			if(taDocument.getTaTiers().getTaTelephone()!=null)
//				ihmInfosTiers.setNumeroTelephone(taDocument.getTaTiers().getTaTelephone().getNumeroTelephone());
//			if(taDocument.getTaTiers().getTaEmail()!=null)
//				ihmInfosTiers.setAdresseEmail(taDocument.getTaTiers().getTaEmail().getAdresseEmail());
//			if(taDocument.getTaTiers().getTaWeb()!=null)
//				ihmInfosTiers.setAdresseWeb(taDocument.getTaTiers().getTaWeb().getAdresseWeb());
//			if(taDocument.getTaTiers().getTaCompl()!=null)
//				ihmInfosTiers.setSiretCompl(taDocument.getTaTiers().getTaCompl().getSiretCompl());
//			if(taDocument.getTaTiers().getTaCommentaire()!=null)
//				ihmInfosTiers.setCommentaire(taDocument.getTaTiers().getTaCommentaire().getCommentaire());
//		}
//		if (ihmInfosTiers!=null)
//			((IHMIdentiteTiers) selectedInfosTiers).setIHMIdentiteTiers(ihmInfosTiers);
//		else
//			((IHMIdentiteTiers) selectedInfosTiers).setIHMIdentiteTiers(new IHMIdentiteTiers());
//		if(taInfosDocument!=null)
//			new LgrDozerMapper<IHMIdentiteTiers,TaInfosFacture >().map( ihmInfosTiers,taInfosDocument);
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
	
	public void initialisationDesCPaiement(Boolean recharger) {
	// on vide les modeles
	modelCPaiement.getListeObjet().clear();
	TaInfosAvoir taInfosDocument = null;
	if (taDocument != null) {
		if (taDocument.getCodeDocument()!=null&& taDocument.getCodeDocument() != "")
			taInfosDocument = daoInfosAvoir
					.findByCodeAvoir(taDocument.getCodeDocument());
		else
			taInfosDocument = new TaInfosAvoir();

		TaTCPaiementDAO taTCPaiementDAO = new TaTCPaiementDAO(getEm());
		TaCPaiement taCPaiementDoc = null;
		if (taTCPaiementDAO.findByCode(TaTCPaiement.C_CODE_TYPE_AVOIR) != null
				&& taTCPaiementDAO.findByCode(
						TaTCPaiement.C_CODE_TYPE_AVOIR).getTaCPaiement() != null) {
			taCPaiementDoc = taTCPaiementDAO.findByCode(
					TaTCPaiement.C_CODE_TYPE_AVOIR).getTaCPaiement();
		}
		int report = 0;
		int finDeMois = 0;


			if (taDocument.getTaTiers() != null) {
				if (taDocument.getTaTiers().getTaTPaiement() != null) {
					if (taDocument.getTaTiers().getTaTPaiement()
							.getReportTPaiement() != null)
						report = taDocument.getTaTiers().getTaTPaiement()
								.getReportTPaiement();
					if (taDocument.getTaTiers().getTaTPaiement()
							.getFinMoisTPaiement() != null)
						finDeMois = taDocument.getTaTiers()
								.getTaTPaiement().getFinMoisTPaiement();
				}

				if (taDocument.getTaTiers().getTaCPaiement() == null
						|| (report != 0 || finDeMois != 0)) {
					if (taCPaiementDoc == null
							|| (report != 0 || finDeMois != 0)) {// alors on
																	// met
																	// au
																	// moins
																	// une
																	// condition
																	// vide
						IHMInfosCPaiement ihm = new IHMInfosCPaiement();
						ihm.setReportCPaiement(report);
						ihm.setFinMoisCPaiement(finDeMois);
						modelCPaiement.getListeObjet().add(ihm);
					}
				} else
					modelCPaiement.getListeObjet().add(
							modelCPaiement.getMapperModelToUI().map(
									taDocument.getTaTiers()
											.getTaCPaiement(),
									classModelCPaiement));
			}
		

		if (taCPaiementDoc != null  )
			modelCPaiement.getListeObjet().add(
					modelCPaiement.getMapperModelToUI().map(taCPaiementDoc,
							classModelCPaiement));

		// ajout de l'adresse de livraison inscrite dans l'infos facture
		if (taInfosDocument != null) {
			if (recharger)
				modelCPaiement.getListeObjet().add(
						mapperModelToUIInfosDocVersCPaiement.map(
								taDocument.getTaInfosDocument(),
								classModelCPaiement));
			else
				modelCPaiement.getListeObjet().addFirst(
						mapperModelToUIInfosDocVersCPaiement.map(
								taDocument.getTaInfosDocument(),
								classModelCPaiement));
		}
	}
	if (!modelCPaiement.getListeObjet().isEmpty()) {
		((IHMInfosCPaiement) selectedCPaiement)
				.setIHMInfosCPaiement(modelCPaiement.getListeObjet()
						.getFirst());
	} else {
		((IHMInfosCPaiement) selectedCPaiement)
				.setIHMInfosCPaiement(new IHMInfosCPaiement());
	}
	findExpandIem(vue.getExpandBar(), paInfosCondPaiement)
			.setExpanded(!((IHMInfosCPaiement) selectedCPaiement).estVide());

}
	
	@Override
	protected void actInserer() throws Exception {
		if (!vue.isDisposed()) {
			try {
				if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
					setIhmOldEnteteAvoir();
					initialisationEcran("");
//					dao.getEntityManager().clear();
					taDocument = new TaAvoir(true);				
					mapperModelToUI.map(taDocument, (IHMEnteteAvoir) selectedAvoir);
					((IHMEnteteAvoir) selectedAvoir).setCodeDocument(dao.genereCode());
					validateUIField(Const.C_CODE_DOCUMENT,((IHMEnteteAvoir) selectedAvoir).getCodeDocument());//permet de verrouiller le code genere
					((IHMEnteteAvoir) selectedAvoir).setCommentaire(avoirPLugin.getDefault().getPreferenceStore().getString(PreferenceConstants.COMMENTAIRE));
					validateUIField(Const.C_CODE_DOCUMENT,((IHMEnteteAvoir) selectedAvoir).getCodeDocument());
					taDocument.setCodeDocument(((IHMEnteteAvoir) selectedAvoir).getCodeDocument());
					changementTiers(true);

					Date date = new Date();
					TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
					TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
					// si date inférieur à dateDeb dossier
					if (LibDate.compareTo(date, taInfoEntreprise.getDatedebInfoEntreprise()) == -1) {
						((IHMEnteteAvoir) selectedAvoir).setDateDocument(taInfoEntreprise.getDatedebInfoEntreprise());
						((IHMEnteteAvoir) selectedAvoir).setDateEchDocument(taInfoEntreprise.getDatedebInfoEntreprise());
						((IHMEnteteAvoir) selectedAvoir).setDateLivDocument(taInfoEntreprise.getDatedebInfoEntreprise());
					} else
						// si date supérieur à dateFin dossier
						if (LibDate.compareTo(date, taInfoEntreprise.getDatefinInfoEntreprise()) == 1) {
							((IHMEnteteAvoir) selectedAvoir).setDateDocument(taInfoEntreprise.getDatefinInfoEntreprise());
							((IHMEnteteAvoir) selectedAvoir).setDateEchDocument(taInfoEntreprise.getDatefinInfoEntreprise());
							((IHMEnteteAvoir) selectedAvoir).setDateLivDocument(taInfoEntreprise.getDatefinInfoEntreprise());
						} else {
							((IHMEnteteAvoir) selectedAvoir).setDateDocument(new Date());
							((IHMEnteteAvoir) selectedAvoir).setDateEchDocument(new Date());
							((IHMEnteteAvoir) selectedAvoir).setDateLivDocument(new Date());
						}
					((IHMEnteteAvoir) selectedAvoir).setCodeTiers("");
					((IHMEnteteAvoir) selectedAvoir).setIdAdresse(0);
					((IHMEnteteAvoir) selectedAvoir).setIdAdresseLiv(0);
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
							((IHMEnteteAvoir) selectedAvoir).setCodeTPaiement(typePaiementDefaut);
							taDocument.setTaTPaiement(taTPaiement);
						}
					}					
					mapperUIToModel.map(((IHMEnteteAvoir) selectedAvoir), taDocument);
					
					calculDateEcheance();
					
					fireChangementMaster(new ChangementMasterEntityEvent(this,taDocument));
					//dao.insertion(swtAvoir.setSWTAvoir(ihmEnteteAvoir));
					dao.inserer(taDocument);
					controllerLigne.actInserer();
					controllerTotaux.remonterDocument();
					actRefreshAffectation();
					ParamAfficheLAvoir paramAfficheLAvoir = new ParamAfficheLAvoir();
					paramAfficheLAvoir.setModeEcran(EnumModeObjet.C_MO_INSERTION);
					//paramAfficheLAvoir.setIdAvoir(swtAvoir.getEntete().getCODE());
					paramAfficheLAvoir.setCodeDocument(taDocument.getCodeDocument());
					paramAfficheLAvoir.setInitFocus(false);
					
				}

			} catch (ExceptLgr e1) {
				vue.getLaMessage().setText(LibChaine.lgrStringNonNull(e1.getMessage()));
				logger.error("Erreur : actionPerformed", e1);
			} catch (Exception e1) {
				vue.getLaMessage().setText(LibChaine.lgrStringNonNull(e1.getMessage()));
				logger.error("Erreur : actionPerformed", e1);
			} finally {
				initEtatComposant();
				initEtatBouton(true);
			}
		}
	}

	@Override
	protected void actModifier() throws Exception {
		try {
			boolean continuer=true;
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
						.getString("Message.Attention"),"Cet avoir a été affecté à une ou plusieurs factures, " +
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
	public Boolean setIhmOldEnteteDocumentRefresh(){
		try {			
			if (selectedAvoir != null){
				this.ihmOldEnteteAvoir = IHMEnteteAvoir.copy((IHMEnteteAvoir) selectedAvoir);
				if (taDocument!=null){
					taDocument =rechercheAvoir(taDocument.getCodeDocument(), false, true);
					taInfosDocument=taDocument.getTaInfosDocument();
					}
			}
			return true;
		} catch (Exception e) {
			return false;
		}		
	}


	@Override
	protected void actSupprimer() throws Exception {
		boolean verrouLocal = VerrouInterface.isVerrouille();
		VerrouInterface.setVerrouille(true);
		EntityTransaction transaction = dao.getEntityManager().getTransaction();		
		try {
			if(isUtilise())MessageDialog.openInformation(vue.getShell(), fr.legrain.tiers.ecran.MessagesEcran
					.getString("Message.Attention"),"Cet avoir a été affecté à une ou plusieurs factures, " +
							"vous devez supprimer les affectations avec ces factures avant de pouvoir le supprimer.");
			else			if (MessageDialog.openConfirm(vue.getShell(),
							MessagesEcran.getString("Message.Attention"),
							MessagesEcran.getString("Document.Message.Supprimer"))) {

				dao.begin(transaction);
				TaAvoir u = dao.findById(((IHMEnteteAvoir) selectedAvoir).getIdDocument());
				dao.supprimer(u);
				dao.commit(transaction);
				removeCodeDocument(u);
//				if(listeDocument!=null && containsCodeDocument())
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
		return (((IHMEnteteAvoir)selectedAvoir).getIdDocument()!=null && 
				!dao.recordModifiable(dao.getNomTable(),
						((IHMEnteteAvoir)selectedAvoir).getIdDocument()))||
						(taDocument!=null&&!dao.autoriseModification(taDocument));		
	}
//		return taDocument!=null&&!dao.autoriseModification(taDocument);		
//	}

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
			String codeAvoir = vue.getTfCODE_DOCUMENT().getText();
			boolean message = messageAffiche && !((IHMEnteteAvoir) selectedAvoir).factureEstVide(taDocument.dateDansExercice(new Date()));
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
								dao.annulerCodeGenere(((IHMEnteteAvoir) selectedAvoir).getCodeDocument(),Const.C_CODE_DOCUMENT);
								dao.annulerCodeGenere(codeAvoir,Const.C_CODE_DOCUMENT);
								taDocument.setLegrain(false);
								dao.annuler(taDocument);
								hideDecoratedFields();

								if (vue.getTfCODE_DOCUMENT().isFocusControl()
										&& !message && !annulationForcee) {
									// si facture vide avant annulation
									// et que l'on veut fermer
									actFermer();
									dejaFermer = true;
								} else {
									initialisationEcran(codeAvoir);
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
							dao.annulerCodeGenere(((IHMEnteteAvoir) selectedAvoir).getCodeDocument(),Const.C_CODE_DOCUMENT);
							dao.annulerCodeGenere(codeAvoir,Const.C_CODE_DOCUMENT);
							dao.annuler(taDocument);
							if (!dejaFermer) {
								initialisationEcran(codeAvoir);
								if (insertion) actInserer();
							}
							hasAnnuler = true;
						} else if (!hasAnnuler && repondu) {
							dao.annulerCodeGenere(((IHMEnteteAvoir) selectedAvoir).getCodeDocument(),Const.C_CODE_DOCUMENT);
							dao.annulerCodeGenere(codeAvoir,Const.C_CODE_DOCUMENT);
							dao.annuler(taDocument);
							initialisationEcran(codeAvoir);
							hasAnnuler = true;
						}
						break;
					case C_MO_CONSULTATION:
						dao.annulerCodeGenere(((IHMEnteteAvoir) selectedAvoir).getCodeDocument(),Const.C_CODE_DOCUMENT);
						dao.annulerCodeGenere(codeAvoir,Const.C_CODE_DOCUMENT);

						if (vue.getTfCODE_DOCUMENT().isFocusControl() && !message) {// si facture vide avant annulation
							actFermer();
							dejaFermer = true;
						} else {
							initialisationEcran(codeAvoir);
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
		
		String simpleNameClass = TaAvoir.class.getSimpleName();
//#JPA
//		Collection<TaAvoir> collectionTaAvoir = dao.selectAll();
		Collection<TaAvoir> collectionTaAvoir = new LinkedList<TaAvoir>();
		taDocument.calculeTvaEtTotaux();
		collectionTaAvoir.add(taDocument);
		//impressionAvoir.imprimerSelection(taAvoir.getIdDocument(),taAvoir.getCodeDocument(), true);
		ConstEdition constEdition = new ConstEdition(getEm()); 
		constEdition.setFlagEditionMultiple(true);
//		impressionAvoir.setObject(taDocument);
//		impressionAvoir.setConstEdition(constEdition);
//		impressionAvoir.setCollection(collectionTaAvoir);
		
		Bundle bundleCourant = avoirPLugin.getDefault().getBundle();
		String namePlugin = bundleCourant.getSymbolicName();
		
//		impressionAvoir.imprimerSelection(taDocument.getIdDocument(),taDocument.getCodeDocument(),
//				true,ConstEdition.FICHE_FILE_REPORT_AVOIR,namePlugin,TaAvoir.class.getSimpleName());
		/** 01/03/2010 modifier les editions (zhaolin) **/
		baseImpressionEdition.setObject(taDocument);
		baseImpressionEdition.setConstEdition(constEdition);
		baseImpressionEdition.setCollection(collectionTaAvoir);
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
		
		IPreferenceStore preferenceStore = avoirPLugin.getDefault().getPreferenceStore();
		baseImpressionEdition.impressionEdition(preferenceStore,simpleNameClass,/*true,*/null,namePlugin,
												ConstEdition.FICHE_FILE_REPORT_AVOIR,
												true,false,true,false,false,false,"");
				
	}

	@Override
	protected boolean aideDisponible() {
		boolean result = false;
		switch ((PaEditorAvoirController.this.dao.getModeObjet().getMode())) {
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
			paramAfficheSelectionVisualisation.setModule("avoir");
			paramAfficheSelectionVisualisation.setNomClassController(nomClassController);
			paramAfficheSelectionVisualisation.setNomRequete(Const.C_NOM_VU_AVOIR);

			((LgrEditorPart)e).setPanel(((LgrEditorPart)e).getControllerSelection().getVue());
			((LgrEditorPart)e).getControllerSelection().configPanel(paramAfficheSelectionVisualisation);

		}catch (Exception e) {
			logger.error("",e);
		}	
	}
	
	protected void actSelectionTiers() throws Exception {
		try{
			if(taDocument!=null && taDocument.getTaTiers()!=null &&
					taDocument.getTaTiers().getIdTiers()!=0){
				ouvreFiche(String.valueOf(taDocument.getTaTiers().getIdTiers()), TiersMultiPageEditor.ID_EDITOR,null,false);
				
			}
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
		//boolean aide = getActiveAide();
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

				switch ((PaEditorAvoirController.this.dao.getModeObjet().getMode())) {
				case C_MO_CONSULTATION:
					if (getFocusCourantSWT().equals(vue.getTfCODE_DOCUMENT())) {
						//permet de paramètrer l'affichage remplie ou non de l'aide
						paramAfficheAideRecherche.setForceAffichageAideRemplie(false);
						editorCreationId = EditorAvoir.ID_EDITOR;
						editorInputCreation = new EditorInputAvoir();

						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
						controllerEcranCreation = this;

						paramAfficheAideRecherche.setAfficheDetail(false);

						paramAfficheAideRecherche.setTypeEntite(TaAvoir.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_DOCUMENT);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_DOCUMENT().getText());
						paramAfficheAideRecherche.setControllerAppelant(PaEditorAvoirController.this);
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideAvoir,TaAvoirDAO,TaAvoir>(dao,IHMAideAvoir.class));
						paramAfficheAideRecherche.setTypeObjet(IHMAideAvoir.class);
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_DOCUMENT);
						try {
							dao.annulerCodeGenere(taDocument.getCodeDocument(), Const.C_CODE_DOCUMENT);
//							ibTaTable.annulerCodeGenere(ibTaTable.getChamp_Obj(Const.C_CODE_AVOIR),Const.C_CODE_AVOIR);
						} catch (Exception ex) {
							logger.error(ex);
						}
					}
					if (getFocusCourantSWT().equals(vue.getTfCODE_TIERS())) {
						PaTiersSWT paTiersSWT = new PaTiersSWT(s2, SWT.NULL);
						SWTPaTiersController swtPaTiersController = new SWTPaTiersController(paTiersSWT);
						//permet de paramètrer l'affichage remplie ou non de l'aide
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
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTiers,TaTiersDAO,TaTiers>(SWTTiers.class,getEm()));
						paramAfficheAideRecherche.setTypeObjet(swtPaTiersController.getClassModel());
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_TIERS);
					}
					if (getFocusCourantSWT().equals(vue.getTfCODE_DOCUMENT())) {
						//permet de paramètrer l'affichage remplie ou non de l'aide
						paramAfficheAideRecherche.setForceAffichageAideRemplie(false);
						editorCreationId = EditorAvoir.ID_EDITOR;
						editorInputCreation = new EditorInputAvoir();

						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
						controllerEcranCreation = this;

						paramAfficheAideRecherche.setAfficheDetail(false);

						paramAfficheAideRecherche.setTypeEntite(TaAvoir.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_DOCUMENT);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_DOCUMENT().getText());
						paramAfficheAideRecherche.setControllerAppelant(PaEditorAvoirController.this);
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideAvoir,TaAvoirDAO,TaAvoir>(dao,IHMAideAvoir.class));
						paramAfficheAideRecherche.setTypeObjet(IHMAideAvoir.class);
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
//						l.add(taAvoir.getTaCPaiement());
						if(taDocument.getTaTiers()!=null)
							l.add(taDocument.getTaTiers().getTaCPaiement());	
						TaTCPaiementDAO taTCPaiementDAO = new TaTCPaiementDAO(getEm());
						if(taTCPaiementDAO.findByCode(TaTCPaiement.C_CODE_TYPE_AVOIR)!=null
								&& taTCPaiementDAO.findByCode(TaTCPaiement.C_CODE_TYPE_AVOIR).getTaCPaiement()!=null)
								l.add(taTCPaiementDAO.findByCode(TaTCPaiement.C_CODE_TYPE_AVOIR).getTaCPaiement());
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
			
			initInfosAvoir();
			
			mapperUIToModelAdresseFactVersInfosDoc.map((IHMAdresseInfosFacturation) selectedAdresseFact, taInfosDocument);
			mapperUIToModelAdresseLivVersInfosDoc.map((IHMAdresseInfosLivraison) selectedAdresseLiv, taInfosDocument);
			mapperUIToModelCPaiementVersInfosDoc.map((IHMInfosCPaiement) selectedCPaiement, taInfosDocument);
			
			mapperUIToModel.map((IHMEnteteAvoir)selectedAvoir,taDocument);
		}
		return null;
	}
	
	public IStatus validateUIField(String nomChamp,Object value) {
		String validationContext = "AVOIR";
		try {
			IStatus s = null;

			if(nomChamp.equals(Const.C_CODE_TIERS)) {
				TaTiersDAO dao = new TaTiersDAO(getEm());
				
				dao.setModeObjet(getDao().getModeObjet());
				TaTiers f = new TaTiers();
				PropertyUtils.setSimpleProperty(f, nomChamp, value);
				if(!taDocument.rechercheSiMemeTiers(f))
					s=new Status(Status.ERROR,gestComBdPlugin.PLUGIN_ID,
							"Ce document est lié à des acomptes, vous ne pouvez pas modifier le tiers initial");
				else
					s = dao.validate(f,nomChamp,validationContext);
				boolean change =(taDocument.getTaTiers()!=null && 
						!taDocument.getTaTiers().getCodeTiers().equals(f.getCodeTiers()));
				change=change||taDocument.getTaTiers()==null;
				if(s.getSeverity()!=IStatus.ERROR && change){
					f = dao.findByCode((String)value);
					taDocument.setTaTiers(f);
					String nomTiers=taDocument.getTaTiers().getNomTiers();
					((IHMEnteteAvoir)selectedAvoir).setLibelleDocument("Avoir N°"+taDocument.getCodeDocument()+" - "+nomTiers);
					if(vue.getCbTTC().isEnabled()){
						taDocument.setTtc(f.getTtcTiers());
						((IHMEnteteAvoir)selectedAvoir).setTtc(LibConversion.intToBoolean(taDocument.getTtc()));
					}
				}
				dao = null;
			} else if(nomChamp.equals(Const.C_LIBELLE_DOCUMENT)) {
				if(value==null || value.equals("")) {
					String nomTiers="";
					if(taDocument.getTaTiers()!=null)
						nomTiers=taDocument.getTaTiers().getNomTiers();
					((IHMEnteteAvoir)selectedAvoir).setLibelleDocument("Avoir N°"+taDocument.getCodeDocument()+" - "+nomTiers);
				}
				s = new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
			} else if(nomChamp.equals(Const.C_TTC)) {
				
				s = new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
			}else if(nomChamp.equals(Const.C_CODE_C_PAIEMENT)
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
//					taAvoir.setTaCPaiement(f);
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
//					taAvoir.setTaCPaiement(f);
//				}
				dao = null;
			} else {
				boolean verrouilleModifCode = false;
				TaAvoir u = new TaAvoir(true);
				PropertyUtils.setSimpleProperty(u, nomChamp, value);
				if(((IHMEnteteAvoir) selectedAvoir).getIdDocument()!=null) {
					u.setIdDocument(((IHMEnteteAvoir) selectedAvoir).getIdDocument());
				}
				if(nomChamp.equals(Const.C_CODE_DOCUMENT)){
					//J'ai rajouté cette variable car lorsqu'on remonte un document et que l'on
					//est déjà en modif, il sort de la zone et fait une vérif du code qui existe déjà
					//et pour cause, on veut remonter un document existant, donc cette variable est initialisée
					//dans remonterDocument uniquement
					if(desactiveValidateCodeDocument)
					  return new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
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
					((IHMEnteteAvoir)selectedAvoir).setDateDocument((Date)value);
					PropertyUtils.setSimpleProperty(taDocument, Const.C_DATE_DOCUMENT, value);
					if(changement){
						((IHMEnteteAvoir)selectedAvoir).setDateEchDocument(taDocument.getDateDocument());
						PropertyUtils.setSimpleProperty(taDocument, Const.C_DATE_ECH_DOCUMENT, taDocument.getDateDocument());
						((IHMEnteteAvoir)selectedAvoir).setDateLivDocument(taDocument.getDateDocument());
						PropertyUtils.setSimpleProperty(taDocument, Const.C_DATE_LIV_DOCUMENT, taDocument.getDateDocument());
						taDocument.setOldDate(taDocument.getDateDocument());
					}
				}
				if(nomChamp.equals(Const.C_DATE_LIV_DOCUMENT)) {
					((IHMEnteteAvoir)selectedAvoir).setDateLivDocument((Date)value);
					PropertyUtils.setSimpleProperty(taDocument, Const.C_DATE_LIV_DOCUMENT, value);
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
	
	public void initInfosAvoir() {
		if(taInfosDocument==null) {
			//initialisation de l'infosfacture
			taInfosDocument = new TaInfosAvoir();
		}
		if(taDocument!=null) {
			//if(taAvoir.getTaInfosAvoirs().isEmpty()) { //pas d'infosfacture dans la facture
				taInfosDocument.setTaDocument(taDocument);
				taDocument.setTaInfosDocument(taInfosDocument);
				//taAvoir.getTaInfosAvoirs().add(taInfosAvoir);
//			} else {
//				taInfosAvoir = taAvoir.getTaInfosAvoirs().iterator().next();
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
							for (TaLAvoir ligne : taDocument.getLignes()) {
								ligne.setLegrain(false);
							}
							controllerLigne.ctrlTouslesChampsToutesLesLignes();							
						}catch (Exception e) {
							actSuivant();
							throw new Exception();
						}
						taDocument.calculeTvaEtTotaux();
						initInfosAvoir();

//						if(declanchementInterne) {
						verifCode();
							ctrlTousLesChampsAvantEnregistrementSWT(dbcAdresseFact);
							ctrlTousLesChampsAvantEnregistrementSWT(dbcAdresseLiv);
							ctrlTousLesChampsAvantEnregistrementSWT();
//						}
						
						dao.begin(transaction);
						mapperUIToModel.map((IHMEnteteAvoir) selectedAvoir, taDocument);
						mapperUIToModelDocumentVersInfosDoc.map(taDocument, taInfosDocument);
						
						if(declanchementInterne) {
							mapperUIToModelAdresseFactVersInfosDoc.map((IHMAdresseInfosFacturation) selectedAdresseFact, taInfosDocument);
							mapperUIToModelAdresseLivVersInfosDoc.map((IHMAdresseInfosLivraison) selectedAdresseLiv, taInfosDocument);
							mapperUIToModelCPaiementVersInfosDoc.map((IHMInfosCPaiement) selectedCPaiement, taInfosDocument);

							/*
							 * recuperation des objets a partir de l'interface au cas ou l'utilisateur ne serait pas passe 
							 * sur cet onglet
							 */
							controllerTotaux.validateUI(); 
							
						}

						if(!((IStructuredSelection)viewerComboLocalisationTVA.getSelection()).isEmpty()) {
							String codeTTvaDoc = ((TaTTvaDoc)((IStructuredSelection)viewerComboLocalisationTVA.getSelection()).getFirstElement()).getCodeTTvaDoc();
							taInfosDocument.setCodeTTvaDoc(codeTTvaDoc);
						}
						
						taDocument.setLegrain(false);
						for (TaLAvoir ligne : taDocument.getLignes()) {
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

	public IHMEnteteAvoir getIhmOldEnteteAvoir() {
		return ihmOldEnteteAvoir;
	}

	public void setIhmOldEnteteAvoir(IHMEnteteAvoir ihmOldEnteteAvoir) {
		this.ihmOldEnteteAvoir = ihmOldEnteteAvoir;
	}

	public void setIhmOldEnteteAvoir() {
		if (selectedAvoir != null)
			this.ihmOldEnteteAvoir = IHMEnteteAvoir.copy((IHMEnteteAvoir) selectedAvoir);
		else {
		}
	}

	public void setVue(PaEditorAvoir vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();

		mapComposantDecoratedField.put(vue.getTfCODE_DOCUMENT(), vue.getFieldCODE_DOCUMENT());
		mapComposantDecoratedField.put(vue.getTfCODE_TIERS(), vue.getFieldCODE_TIERS());
		mapComposantDecoratedField.put(vue.getDateTimeDATE_ECH_DOCUMENT(),vue.getFieldDATE_ECH_DOCUMENT());
		mapComposantDecoratedField.put(vue.getDateTimeDATE_DOCUMENT(),vue.getFieldDATE_DOCUMENT());
		mapComposantDecoratedField.put(vue.getDateTimeDATE_LIV_DOCUMENT(), vue.getFieldDATE_LIV_DOCUMENT());
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
//				ctrlTousLesChampsAvantEnregistrementSWT();
				verifCode();
				validateUI();
			}
	
			VerrouInterface.setVerrouille(true);
			if (vue.getTfCODE_TIERS().equals(getFocusCourantSWT())) {
				changementTiers(true);
			}
			// if(vue.getTfTX_REM_HT_AVOIR().equals(getFocusCourantSWT())){
			// modificationDocument(null);
			// }
			if (vue.getCbTTC().equals(getFocusCourantSWT()) && (vue.getCbTTC().isEnabled())) {
				controllerLigne.initTTC();
			}

//			if (vue.getDateTimeDATE_AVOIR().equals(getFocusCourantSWT()) && (vue.getDateTimeDATE_AVOIR().isEnabled())) {
//				Date d = dateDansExercice(vue.getDateTimeDATE_AVOIR().getSelection());
//				if(LibDate.compareTo(vue.getDateTimeDATE_AVOIR().getSelection(), d)!=0) {
//					System.err.println("Date hors exo");
//					vue.getDateTimeDATE_AVOIR().setSelection(d);
//					ihmEnteteAvoir.setDATE_AVOIR(d);
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
			if (taDocument!=null && selectedAvoir!=null && (IHMEnteteAvoir) selectedAvoir!=null) {
				mapperModelToUI.map(taDocument, (IHMEnteteAvoir) selectedAvoir);
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
		
		mapperModelToUI.map(taDocument, ((IHMEnteteAvoir) selectedAvoir));
//		((IHMEnteteAvoir) selectedEnteteAvoir).setIHMEnteteAvoir(((SWTEnteteAvoir) swtAvoir.getEntete()));
//		((IHMTotauxAvoir) controllerTotaux.getSelectedTotauxAvoir()).setIHMTotauxAvoir(((SWTEnteteAvoir) swtAvoir.getEntete()));
//		((IHMEnteteAvoir) selectedEnteteAvoir).setIHMEnteteAvoir(((SWTEnteteAvoir) swtAvoir.getEntete()));

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
//				dao.annulerCodeGenere(((IHMEnteteAvoir) selectedAvoir).getCodeAvoir(), Const.C_CODE_AVOIR);
//				((IHMEnteteAvoir) selectedAvoir).setIHMEnteteAvoir(new IHMEnteteAvoir());
//				mapperModelToUI.map(taAvoir, ((IHMEnteteAvoir) selectedAvoir));
////				swtAvoir.setSWTAvoir(((IHMEnteteAvoir) selectedEnteteAvoir));
//				vue.getTfCODE_TIERS().setText("");
//				modelEnteteAvoir.remplirListe();
//				taAvoir.setOldCODE(SWTDocument.C_OLD_CODE_INITIAL);
//				ibTaTable.setChamp_Model_Obj(swtAvoir);
				// vider les lignes tables temp
//				if (controllerLigne != null)
//					controllerLigne.recupLignes(swtAvoir);
				//#evt
				// writableList = new WritableList(realm,
				// modelLignesTVA.remplirListe(swtAvoir), IHMLignesTVA.class);
				// tableViewer.setInput(writableList);

//				initEtatBouton(false);
//				changementTiers = true;
//				changementTiers();

				codeDocumentContentProposal.setContentProposalProvider(contentProposalProviderDocument());
//				codeAvoirContentProposal.setContentProposalProvider(contentProposalProviderDocument());
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
		String[][] adapterAvoir = initAdapterDocument();
		String[] listeCodeAvoir = null;
		String[] listeLibelleAvoir = null;
		if (adapterAvoir != null) {
			listeCodeAvoir = new String[adapterAvoir.length];
			listeLibelleAvoir = new String[adapterAvoir.length];
			for (int i = 0; i < adapterAvoir.length; i++) {
				listeCodeAvoir[i] = adapterAvoir[i][0];
				listeLibelleAvoir[i] = adapterAvoir[i][1];
			}
		}

		return new ContentProposalProvider(listeCodeAvoir,
				listeLibelleAvoir);
		
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
	
//	public SWTAvoir getSwtAvoir() {
//		return swtAvoir;
//	}

	public PaLigneAvoirController getControllerLigne() {
		return controllerLigne;
	}

	public void setControllerLigne(PaLigneAvoirController controllerLigne) {
		this.controllerLigne = controllerLigne;
		this.controllerLigne.setParentEcran(this);
	}

	private Boolean remonterDocument(String codeAvoir) throws Exception {
		try {
			boolean res = false;
			// nouvelle facture (mode, code existant "CodeAvoir")
			desactiveModifyListener();
			desactiveValidateCodeDocument=true;
			if(taDocument!=null)
				dao.annulerCodeGenere(taDocument.getOldCODE(),Const.C_CODE_DOCUMENT);
			if (dao.getModeObjet().getMode() == ModeObjet.EnumModeObjet.C_MO_EDITION
					|| dao.getModeObjet().getMode() == ModeObjet.EnumModeObjet.C_MO_INSERTION)
				actAnnuler(true,false,true);
			controllerLigne.forceAnnuler = true;
			controllerLigne.actAnnuler();
			boolean factureVide = true;
			if(taDocument!=null) {
				factureVide = ((IHMEnteteAvoir) selectedAvoir).factureEstVide(taDocument.dateDansExercice(new Date()));
			}
			TaAvoir factureRecherche = rechercheAvoir(codeAvoir, true, factureVide);
			if (factureRecherche!=null) {
				taDocument = factureRecherche;
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
			}
			return res;
		} finally {
			activeModifytListener();
			initEtatBouton(true);
		}

	}

//	public boolean nouvelleAvoir(EnumModeObjet mode, String codeAvoir)
//			throws Exception {
//		boolean resultat = true;
//		boolean verrouLocal = VerrouInterface.getVerrouInterface().isVerrouille();
//		String codeTmp = ((IHMEnteteAvoir) selectedAvoir).getCodeAvoir();
//		try {
//			// si pièce déjà en mode Edition ou Insertion -> annuler
//			if (dao.dataSetEnModif())
//				actAnnuler(true);
//
//			if (!dao.dataSetEnModif()) {
//				// si pièce en consultation
//				VerrouInterface.getVerrouInterface().setVerrouille(true);
//				desactiveModifyListener();
//				taAvoir = new TaAvoir(codeAvoir);
////				taAvoir.addModificationDocumentListener(this);
////				taAvoir.addModificationDocumentListener(dao);
//
//				taAvoir.addChangeModeListener(this);
////				ibTaTable.setAvoir(swtAvoir);
//				controllerLigne.setTaAvoir(taAvoir);
//				controllerTotaux.setTaAvoir(taAvoir);
////				controllerLigne.getIbTaTable().setAvoir(swtAvoir);
////				controllerLigne.getIbTaTable().videTableTemp();
////				controllerLigne.initLigneCourantSurRow();
////				controllerLigne.getIbTaTable().initLigneCourantSurRow();
////				swtAvoir.getLignes().clear();
////				taAvoir.getLignesTVA().clear();
//				dao.annulerCodeGenere(codeTmp, Const.C_CODE_AVOIR);
//				if (codeAvoir.equals("")) {
//					codeAvoir = dao.genereCode();
//					if (codeAvoir.equals(""))
//						throw new ExceptLgr(MessagesGestCom.getString("IB_TA_AVOIR.Message.CodeIncoherent"),0, true, 0);
//					dao.rentreCodeGenere(codeAvoir,Const.C_CODE_AVOIR);
//				}
//				((IHMEnteteAvoir) selectedAvoir).setCodeAvoir(codeAvoir);
//				vue.getTfCODE_AVOIR().setText(codeAvoir);
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
////					controllerLigne.getIbTaTable().recupLignesAvoir(codeAvoir);
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
////						swtAvoir.addLigne(new SWTLigneAvoir(swtAvoir.getLigneCourante()));
////						controllerLigne.getIbTaTable().remplissageLigneSurObjetQuery(swtAvoir.getLigneCourante());
////						// ibTaTable.modificationDocument(new
////						// ModificationDocumentEvent(this,facture.getLigne(facture.getLigneCourante())));
////					} while (controllerLigne.getIbTaTable().getFIBQuery().next());
////					ibTaTable.modificationDocument(new SWTModificationDocumentEvent(this, swtAvoir.getLigne(swtAvoir.getLigneCourante())));
////					modificationDocument(new SWTModificationDocumentEvent(this,swtAvoir.getLigne(swtAvoir.getLigneCourante())));
////					controllerLigne.getIbTaTable().getFIBQuery().first();
////				}
//
//				// actualiser l'écran entête
//				// remplirInterface(false);
//				// actualiser l'écran des lignes
//				// controllerLigne.remplirInterface(false);
//
//				taAvoir.addModificationDocumentListener(this.controllerLigne);
//				
//				taAvoir.setCodeAvoir(codeAvoir);
////				((IHMEnteteAvoir) selectedEnteteAvoir).setIHMEnteteAvoir(((SWTEnteteAvoir) swtAvoir.getEntete()));
//				mapperModelToUI.map(taAvoir, (IHMEnteteAvoir) selectedAvoir);
//
//				// récupérer code validé dans facture, dataset et interface
////				swtAvoir.getEntete().setCODE(codeAvoir);
//				taAvoir.setCodeAvoir(codeAvoir);
//				if(validateUIField(Const.C_CODE_AVOIR, ((IHMEnteteAvoir) selectedAvoir).getCodeAvoir()).getSeverity()==IStatus.ERROR)
////				if (!ibTaTable.verifChamp(Const.C_CODE_AVOIR,((IHMEnteteAvoir) selectedEnteteAvoir).getCodeAvoir(), null, null))
//					throw new ExceptLgr();
//				vue.getTfCODE_AVOIR().setText(codeAvoir);
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

	// private boolean validationCodeAvoir(String codeAvoir){
	// boolean res = false;
	// try {
	// if(!swtAvoir.getOldCODE().equals(codeAvoir)
	// ||(swtAvoir.getOldCODE().equals(SWTDocument.C_OLD_CODE_INITIAL))) {
	// try {//si changement de code facture
	// //
	// ibTaTable.annulerCodeGenere(facture.getEntete().getCODE(),"validationCodeAvoir");
	// if (ibTaTable.dataSetEnModif())
	// actAnnuler(true);
	// if(controllerLigne.getIbTaTable().dataSetEnModif())
	// controllerLigne.actAnnuler();
	// if(!ibTaTable.dataSetEnModif()){
	// if(ibTaTable.rechercheAvoir(codeAvoir,true,((IHMEnteteAvoir)selectedEnteteAvoir).AvoirEstVide(dateDansExercice(new
	// Date())))) {
	// selectedEnteteAvoir = modelEnteteAvoir.getListeObjet().getFirst();
	// // si code existant -> remonterDocument (code existant "CodeAvoir")
	// if (remonterDocument(codeAvoir))
	// res=true;
	// }else {//si nouveau code
	// // -> nouvelle facture (Insertion, nouveau code "CodeAvoir")
	// if (nouvelleAvoir(EnumModeObjet.C_MO_INSERTION,codeAvoir))
	// res=true;
	// }
	// if (!res)throw new Exception();
	// swtAvoir.setOldCODE(codeAvoir);
	// // ibTaTable.rentreCodeGenere(codeAvoir,"validationCodeAvoir");
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
		((PaInfosAdresse) vue.getExpandBar()
				.getItem(0).getControl()).getBtnReinitialiser().
				addSelectionListener(lgrModifyListener);
		activeModifytListener(mapComposantChampsAdresseLiv,daoAdresseLiv);
		((PaInfosAdresse) vue.getExpandBar()
				.getItem(1).getControl()).getBtnReinitialiser().
				addSelectionListener(lgrModifyListener);
		activeModifytListener(mapComposantChampsCPaiement, daoCPaiement);
//		((PaInfosCondPaiement) vue.getExpandBar()
//				.getItem(2).getControl()).getBtnReinitialiser().
//				addSelectionListener(lgrModifyListener);
		vue.getTfCODE_DOCUMENT().removeModifyListener(lgrModifyListener);
	}

	//#JPA
	//#AFAIRE
//	public String[][] initAdapterDocument() {
//		String[][] valeurs = null;
//		boolean affichageCtrlEspace = GestionCommercialePlugin.getDefault().getPreferenceStore().getBoolean(fr.legrain.gestionCommerciale.preferences.PreferenceConstants.AFFICHAGE_CTRL_ESPACE);
//		if(affichageCtrlEspace){
//		if(listeDocument==null){
//			TaAvoirDAO taAvoirDAO = new TaAvoirDAO(getEm());
//			TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
//			TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
//
//			//		List<TaBonliv> l = taBonlivDAO.selectAll();
//			//		if(listeDocument==null)
//			listeDocument =((IDocumentTiersDAO)taAvoirDAO).rechercheDocument(taInfoEntreprise.getDatedebInfoEntreprise(),
//					taInfoEntreprise.getDatefinInfoEntreprise());	
//			taAvoirDAO = null;
//		}
////		List<TaAvoir> l = taAvoirDAO.selectAll();
//		valeurs = new String[listeDocument.size()][2];
//		int i = 0;
//		String description = "";
//		for (IDocumentTiers taAvoir : listeDocument) {
//			taAvoir.calculeTvaEtTotaux();
//			valeurs[i][0] = taAvoir.getCodeDocument();
//			
//			description = "";
//			description += taAvoir.getLibelleDocument();
//			if(taAvoir.getTaTiers()!=null) {
//				description += " \r\n " + taAvoir.getTaTiers().getCodeTiers();
//			}
////			description += "\r\n Net TTC = " + taAvoir.getNetHtCalc()
////			+ " \r\n Net HT = " + taAvoir.getNetTtcCalc()
////			+ " \r\n Net à payer = " + taAvoir.getNetAPayer()
////			+ " \r\n Montant régler = " + taAvoir.getRegleDocument()
//			description += " \r\n Date = " + LibDate.dateToStringAA(taAvoir.getDateDocument())
//			+ " \r\n Echéance = " + LibDate.dateToStringAA(((TaAvoir)taAvoir).getDateEchDocument())
//			+ " \r\n Exportée = " 
//			+LibConversion.booleanToStringFrancais(LibConversion.intToBoolean(((TaAvoir)taAvoir).getExport()));
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
			TaAvoirDAO taDocDAO = new TaAvoirDAO(getEm());
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
//		taTiersDAO.setPreferences(avoirPLugin.getDefault().getPreferenceStore().getString(fr.legrain.avoir.preferences.PreferenceConstants.TYPE_TIERS_DOCUMENT));
//		if(listeTiers==null)
//			listeTiers = taTiersDAO.selectTiersSurTypeTiers();
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
		taTiersDAO.setPreferences(avoirPLugin.getDefault().getPreferenceStore().getString(fr.legrain.avoir.preferences.PreferenceConstants.TYPE_TIERS_DOCUMENT));
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
//			((SWTEnteteAvoir) swtAvoir.getEntete()).setTTC(false);
			taDocument.setTtc(0);
		else
//			((SWTEnteteAvoir) swtAvoir.getEntete()).setTTC(true);
			taDocument.setTtc(1);
		try {
//			ibTaTable.affecte(Const.C_TTC,LibConversion.booleanToString(((SWTEnteteAvoir) swtAvoir.getEntete()).getTTC()));
		} catch (DataSetException e1) {
			logger.error(e1);
		} catch (Exception e1) {
			logger.error(e1);
		}
		if (controllerLigne != null)
			controllerLigne.initTTC();
	}

	/**
	 * Initialisation des boutons suivant l'etat de l'objet "ibTaTable"
	 * 
	 * @param initFocus -
	 *            si vrai initialise le focus en fonction du mode
	 */
	protected void initEtatBouton(boolean initFocus) {
		// super.initEtatBouton(initFocus);
		initEtatBoutonCommand(initFocus,modelEnteteAvoir.getListeObjet());
		enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID, dao.getModeObjet().getMode()==EnumModeObjet.C_MO_EDITION);
		boolean trouve = false;
		enableActionAndHandler(C_COMMAND_GLOBAL_PRECEDENT_ID, false);
		enableActionAndHandler(C_COMMAND_GLOBAL_SUIVANT_ID, true);
		enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID, true);
		enableActionAndHandler(C_COMMAND_DOCUMENT_CREATEDOC_ID, true);
//#JPA
//		if (ibTaTable.getFIBQuery().isOpen()) {
//			trouve = ibTaTable.getFIBQuery().getRowCount() > 0;
//			switch (ibTaTable.getFModeObjet().getMode()) {
//			case C_MO_INSERTION:
//				// enableActionAndHandler(actionInserer,handlerInserer,false);
//				enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID, false);
//				break;
//			case C_MO_EDITION:
//				// enableActionAndHandler(actionInserer,handlerInserer,false);
//				enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID, false);
//				break;
//			case C_MO_CONSULTATION:
//				// enableActionAndHandler(actionInserer,handlerInserer,true);
//				enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID, true);
//				break;
//			default:
//				break;
//			}
//		}
		if (controllerTotaux != null)
			controllerTotaux.initEtatBouton(initFocus);
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

	public class OldAvoir {
		String code_facture = "";
		int id_facture = 0;

		public OldAvoir() {
		}

		public OldAvoir(String code_facture, int id_facture) {
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

	// public boolean focusDansBasAvoir(){
	// for (Control element : listeComposantBasAvoir) {
	// if(element.isFocusControl())
	// return true;
	// }
	// return false;
	// }

	public Date dateDansPeriode(Date newValue,String champ) throws Exception{
		if(champ.equals(Const.C_DATE_DOCUMENT)){
			newValue=taDocument.dateDansExercice(newValue);
			if(newValue!=null){
				if((((IHMEnteteAvoir)selectedAvoir).getDateEchDocument()!=null && 
						((IHMEnteteAvoir)selectedAvoir).getDateEchDocument().before(newValue))||
						((IHMEnteteAvoir)selectedAvoir).getDateEchDocument()==null ){
					taDocument.setDateEchDocument(newValue);
					((IHMEnteteAvoir)selectedAvoir).setDateEchDocument(newValue);
					}
//				if((((IHMEnteteAvoir)selectedAvoir).getDateLivDocument()!=null && 
//						((IHMEnteteAvoir)selectedAvoir).getDateLivDocument().before(newValue))||
//						((IHMEnteteAvoir)selectedAvoir).getDateLivDocument()==null ){
//					taDocument.setDateLivDocument(newValue);
//					((IHMEnteteAvoir)selectedAvoir).setDateLivDocument(newValue);
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


//	/**
//	 * Si la valeur est antérieure à la date de debut de l'exercice, retourne la date de début de l'exercice<br>
//	 * Si la valeur est postérieure à la date de fin de l'exercice, retourne la date de fin de l'exercice<br>
//	 * @param valeur - date a tester
//	 * @return 
//	 * @throws Exception
//	 */
//	public Date dateDansExercice(Date valeur) throws Exception {
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
//		return valeur;
//	}

	public PaEditorAvoirTotauxController getControllerTotaux() {
		return controllerTotaux;
	}

	public void setControllerTotaux(PaEditorAvoirTotauxController controllerTotaux) {
		this.controllerTotaux = controllerTotaux;
		// controllerTotaux.initHandlerAvoir(mapCommand);
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

	//#AFAIRE
	private void actReinitialiser() throws Exception {
		initialisationDesInfosComplementaires();
//		if (focusDansAdresse(getFocusCourantSWT())) {
//			ibTaInfosAvoirAdresse.initQuery(LibConversion.integerToString(((IHMEnteteAvoir) selectedEnteteAvoir).getIdAvoir()), 
//					LibConversion.integerToString(((IHMEnteteAvoir) selectedEnteteAvoir).getIdTiers()), false,getTypeAdresseFacturation());
//			modelAdresseFact.remplirListe();
//			if (!modelAdresseFact.getListeObjet().isEmpty())
//				((IHMAdresseInfosFacturation) selectedAdresseFact).setIHMAdresse(modelAdresseFact.getListeObjet().getFirst());
//			else
//				((IHMAdresseInfosFacturation) selectedAdresseFact).setIHMAdresse(new IHMAdresseInfosFacturation());
//			SWTAdresse swtAdresse = new SWTAdresse();
//			((SWTEnteteAvoir) swtAvoir.getEntete()).setSwtAdresse(swtAdresse.setSWTAdresse((IHMAdresseInfosFacturation) selectedAdresseFact));
//
//		}
//		if (focusDansAdresse_Liv(getFocusCourantSWT())) {
//			ibTaInfosAvoirAdresseLiv.initQuery(LibConversion
//					.integerToString(((IHMEnteteAvoir) selectedEnteteAvoir).getIdAvoir()), LibConversion
//					.integerToString(((IHMEnteteAvoir) selectedEnteteAvoir).getIdTiers()), false,getTypeAdresseLivraison());
//			modelAdresseLiv.remplirListe();
//			if (!modelAdresseLiv.getListeObjet().isEmpty())
//				((IHMAdresseInfosLivraison) selectedAdresseLiv).setIHMAdresse(modelAdresseLiv.getListeObjet().getFirst());
//			else
//				((IHMAdresseInfosLivraison) selectedAdresseLiv).setIHMAdresse(new IHMAdresseInfosLivraison());
//			SWTAdresse swtAdresse = new SWTAdresse();
//			((SWTEnteteAvoir) swtAvoir.getEntete()).setSwtAdresseLiv(swtAdresse.setSWTAdresse((IHMAdresseInfosLivraison) selectedAdresseLiv));
//
//		}
//		if (focusDansCPaiment(getFocusCourantSWT())) {
//			ibTaCPaiement.initQuery(LibConversion
//					.integerToString(((IHMEnteteAvoir) selectedEnteteAvoir).getIdAvoir()), LibConversion
//					.integerToString(((IHMEnteteAvoir) selectedEnteteAvoir).getIdTiers()));
//			modelCPaiement.remplirListe();
//			if (!modelCPaiement.getListeObjet().isEmpty())
//				((IHMInfosCPaiement) selectedCPaiement).setIHMInfosCPaiement(modelCPaiement.getListeObjet().getFirst());
//			else
//				((IHMInfosCPaiement) selectedCPaiement).setIHMInfosCPaiement(new IHMInfosCPaiement());
//			SWTCPaiement swtCPaiement = new SWTCPaiement();
//			((SWTEnteteAvoir) swtAvoir.getEntete()).setSwtCPaiement(swtCPaiement.setSWTCPaiement((IHMInfosCPaiement) selectedCPaiement));
//
//		}
	}
	
	@Override
	protected void actCreateDoc() throws Exception {
		try{
			controllerTotaux.actCreateDoc();
		}catch (Exception e) {
			logger.error("",e);
		}
	}	
	@Override
	protected void actCreateModele() throws Exception {
		try{
			controllerTotaux.actCreateModele();
		}catch (Exception e) {
			logger.error("",e);
		}
	}
	public ContentProposalAdapter getCodeDocumentContentProposal() {
		return codeDocumentContentProposal;
	}

	public void setCodeDocumentContentProposal(
			ContentProposalAdapter codeAvoirContentProposal) {
		this.codeDocumentContentProposal = codeAvoirContentProposal;
	}
	
	public Boolean verifCode() throws Exception{
		if(dao.dataSetEnModif()&& 
				dao.getModeObjet().getMode()==ModeObjet.EnumModeObjet.C_MO_INSERTION &&
				!dao.autoriseUtilisationCodeGenere(vue.getTfCODE_DOCUMENT().getText())){
			String code_Old =vue.getTfCODE_DOCUMENT().getText();
			MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"Erreur saisie","Le code "+vue.getTfCODE_DOCUMENT().getText()+" est utilisé, il va être automatiquement remplacé par le suivant !");
			dao.annulerCodeGenere(vue.getTfCODE_DOCUMENT().getText(),Const.C_CODE_DOCUMENT);				
			vue.getTfCODE_DOCUMENT().setText(dao.genereCode());
			dao.rentreCodeGenere(vue.getTfCODE_DOCUMENT().getText(),Const.C_CODE_DOCUMENT);
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

	public TaAvoirDAO getDao() {
		return dao;
	}

	public void setDao(TaAvoirDAO dao) {
		this.dao = dao;
	}

	public TaAvoir getTaDocument() {
		return taDocument;
	}
	
	public TaAvoir rechercheAvoir(String codeAvoir,boolean annule,boolean factureVide) throws Exception {
		TaAvoir fact = null;
		fact = dao.findByCode(codeAvoir);
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

	public DefaultFrameGrilleSansBouton getVisuDesFacturesAffecteeAvoir() {
		return visuDesFacturesAffecteeAvoir;
	}

	public void setVisuDesFacturesAffecteeAvoir(
			DefaultFrameGrilleSansBouton visuDesFacturesAffecteeAvoir) {
		this.visuDesFacturesAffecteeAvoir = visuDesFacturesAffecteeAvoir;
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


}

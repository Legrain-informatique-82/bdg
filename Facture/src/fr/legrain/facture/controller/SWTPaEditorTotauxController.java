package fr.legrain.facture.controller;

import java.awt.Desktop;
import java.awt.event.FocusAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.ListenerList;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import fr.legrain.document.DocumentPlugin;
import fr.legrain.document.controller.PaReglementController;
import fr.legrain.document.divers.TypeDoc;
import fr.legrain.document.ecran.PaAffectationAcompteSurFacture;
import fr.legrain.document.ecran.PaAffectationReglementSurFacture;
import fr.legrain.document.ecran.PaCommentaire;
import fr.legrain.document.ecran.PaInfosCondPaiement;
import fr.legrain.documents.dao.LigneTva;
import fr.legrain.documents.dao.TaAcompte;
import fr.legrain.documents.dao.TaAcompteDAO;
import fr.legrain.documents.dao.TaAvoir;
import fr.legrain.documents.dao.TaAvoirDAO;
import fr.legrain.documents.dao.TaDocumentEditable;
import fr.legrain.documents.dao.TaDocumentEditableDAO;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaInfosFacture;
import fr.legrain.documents.dao.TaInfosFactureDAO;
import fr.legrain.documents.dao.TaRAcompte;
import fr.legrain.documents.dao.TaRAcompteDAO;
import fr.legrain.documents.dao.TaRAvoir;
import fr.legrain.documents.dao.TaRAvoirDAO;
import fr.legrain.documents.dao.TaRReglement;
import fr.legrain.documents.dao.TaRReglementDAO;
import fr.legrain.documents.dao.TaReglement;
import fr.legrain.documents.dao.TaTPaiement;
import fr.legrain.documents.dao.TaTPaiementDAO;
import fr.legrain.documents.events.SWTModificationDocumentEvent;
import fr.legrain.documents.events.SWTModificationDocumentListener;
import fr.legrain.facture.FacturePlugin;
import fr.legrain.facture.divers.IHMLignesTVA;
import fr.legrain.facture.divers.ModelAcompteDisponible;
import fr.legrain.facture.divers.ModelAvoirDisponible;
import fr.legrain.facture.divers.ModelLignesTVA;
import fr.legrain.facture.divers.ModelRDocument;
import fr.legrain.facture.divers.ModelTotaux;
import fr.legrain.facture.ecran.PaTotauxSWT;
import fr.legrain.facture.preferences.PreferenceConstants;
import fr.legrain.generationLabelEtiquette.divers.GenerationFileEtiquette;
import fr.legrain.generationLabelEtiquette.wizard.HeadlessEtiquette;
import fr.legrain.generationLabelEtiquette.wizard.WizardModelLables;
import fr.legrain.generationdocument.controllers.PaChoixGenerationDocumentController;
import fr.legrain.generationdocument.controllers.PaChoixGenerationModeleController;
import fr.legrain.generationdocument.divers.ParamAfficheChoixGenerationDocument;
import fr.legrain.generationdocument.ecran.PaChoixGenerationDocument;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Document.ChangeModeEvent;
import fr.legrain.gestCom.Module_Document.ChangeModeListener;
import fr.legrain.gestCom.Module_Document.IHMAideAcompte;
import fr.legrain.gestCom.Module_Document.IHMAideDocument;
import fr.legrain.gestCom.Module_Document.IHMEnteteDocument;
import fr.legrain.gestCom.Module_Document.IHMEnteteFacture;
import fr.legrain.gestCom.Module_Document.IHMEtat;
import fr.legrain.gestCom.Module_Document.IHMRDocument;
import fr.legrain.gestCom.Module_Document.IHMTotauxDocument;
import fr.legrain.gestCom.Module_Document.SWTDocumentEditable;
import fr.legrain.gestCom.Module_Document.SWTTPaiement;
import fr.legrain.gestCom.Module_Tiers.IHMInfosCPaiement;
import fr.legrain.gestCom.Module_Tiers.SWTCPaiement;
import fr.legrain.gestCom.gestComBd.gestComBdPlugin;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.DeclencheCommandeControllerEvent;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideRechercheSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideRechercheControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard.DateTraverse;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDePageEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.AbstractLgrDAO;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.IBQuLgr;
import fr.legrain.lib.data.InfosVerifSaisie;
import fr.legrain.lib.data.LibCalcul;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.LibDate;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.lib.data.VerrouInterface;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ParamAfficheSWT;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.lib.gui.cdatetimeLgr;
import fr.legrain.lib.gui.aide.PaAideRechercheSWT;
import fr.legrain.lib.gui.aide.PaAideSWT;
import fr.legrain.lib.gui.aide.ParamAfficheAide;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.lib.gui.grille.LgrViewerSupport;
import fr.legrain.tiers.dao.TaCPaiement;
import fr.legrain.tiers.dao.TaCPaiementDAO;
import fr.legrain.tiers.dao.TaCompteBanqueDAO;
import fr.legrain.tiers.dao.TaTCPaiement;
import fr.legrain.tiers.dao.TaTCPaiementDAO;
import fr.legrain.tiers.divers.ParamAfficheTPaiement;
import fr.legrain.tiers.ecran.PaConditionPaiementSWT;
import fr.legrain.tiers.ecran.PaTypePaiementSWT;
import fr.legrain.tiers.ecran.ParamAfficheConditionPaiement;
import fr.legrain.tiers.ecran.SWTPaConditionPaiementController;
import fr.legrain.tiers.ecran.SWTPaTypePaiementController;
import fr.legrain.tiers.editor.EditorConditionPaiement;
import fr.legrain.tiers.editor.EditorInputConditionPaiement;
import fr.legrain.tiers.editor.EditorInputTypePaiement;
import fr.legrain.tiers.editor.EditorTypePaiement;
import fr.legrain.tiers.divers.TiersUtil;
import fr.legrain.tiers.divers.ParamAfficheTPaiement;
import fr.legrain.tiers.ecran.PaTypePaiementSWT;
import fr.legrain.tiers.ecran.SWTPaTypePaiementController;
import fr.legrain.tiers.editor.EditorInputTypePaiement;
import fr.legrain.tiers.editor.EditorTypePaiement;

public class SWTPaEditorTotauxController extends JPABaseControllerSWTStandard
		implements ISelectionProvider, /* ISelectionListener, */
		RetourEcranListener, SWTModificationDocumentListener,
		ChangeModeListener {

	static Logger logger = Logger.getLogger(SWTPaEditorTotauxController.class
			.getName());

	public static final String titreAcomptesDisponibles = "Liste des acomptes disponibles";
	public static final String titreAvoirsDisponibles = "Liste des avoirs disponibles";
	public static final String titreReglements = "Création des règlements";

	private PaTotauxSWT vue = null;
	private PaReglementController paReglementController = null;
	// private TaFacture masterEntity = new TaFacture();
	private PaAffectationAcompteSurFacture paAffectation = null;
	private PaCommentaire paCommentaire = null;
	private PaAffectationReglementSurFacture paAffectationReglement = null;

	public List<Control> listeComposantCPaiement = null;
	protected Map<Control, String> mapComposantChampsCPaiement = null;

	private LgrDozerMapper<TaCPaiement, IHMInfosCPaiement> mapperModelToUICPaiementInfosDocument = new LgrDozerMapper<TaCPaiement, IHMInfosCPaiement>();
	private LgrDozerMapper<TaInfosFacture, IHMInfosCPaiement> mapperModelToUIInfosDocVersCPaiement = new LgrDozerMapper<TaInfosFacture, IHMInfosCPaiement>();
	private LgrDozerMapper<IHMInfosCPaiement, TaInfosFacture> mapperUIToModelCPaiementVersInfosDoc = new LgrDozerMapper<IHMInfosCPaiement, TaInfosFacture>();

	private Object selectedCPaiement = new IHMInfosCPaiement();
	private TaCPaiementDAO daoCPaiement = null;
	private Class classModelCPaiement = IHMInfosCPaiement.class;
	private ModelGeneralObjet<IHMInfosCPaiement, TaCPaiementDAO, TaCPaiement> modelCPaiement /*
																							 * =
																							 * new
																							 * ModelGeneralObjet
																							 * <
																							 * IHMInfosCPaiement
																							 * ,
																							 * TaCPaiementDAO
																							 * ,
																							 * TaCPaiement
																							 * >
																							 * (
																							 * daoCPaiement
																							 * ,
																							 * classModelCPaiement
																							 * )
																							 */;
	private DataBindingContext dbcCPaiement = null;
	private TaInfosFactureDAO daoInfosFacture = null;

	private PaInfosCondPaiement paInfosCondPaiement = null;

	private PaAffectationAcompteSurFacture paAffectationAvoir = null;
	public List<Control> listeComposantEntete = null;
	protected Map<Control, String> mapComposantChampsCommentaire = null;

	private Object ecranAppelant = null;
	private IHMTotauxDocument ihmTotauxFacture;
	private IHMTotauxDocument ihmOldTotauxFacture;
	private Realm realm;
	private DataBindingContext dbc;
	private String dataTVA[][] = {};
	private ModelTotaux modelTotaux = new ModelTotaux();
	private ModelLignesTVA modelLignesTVA;

	// private ModelReglement modelReglement;
	private ModelRDocument modelRAcompte;
	private ModelRDocument modelRAvoir;
	private ModelAcompteDisponible modelAcomptesDisponibles;
	private ModelAvoirDisponible modelAvoirsDisponibles;
	private ModelGeneralObjet<SWTDocumentEditable, TaDocumentEditableDAO, TaDocumentEditable> modelDocumentEditable;

	private IObservableValue selectedRAcompte;
	private IObservableValue selectedRAvoir;
	private IHMRDocument swtOldRAcompte;
	private IHMRDocument swtOldRAvoir;
	private IHMRDocument ihmRAcompte;
	private IObservableValue selectedSWTDocumentEditable;
	// private IHMReglement swtOldReglement;
	// private IHMReglement ihmRReglement;

	private LgrTableViewer tableViewerAffectation;
	private LgrTableViewer tableViewerAffectationAvoirs;
	private LgrTableViewer tableViewerAcompte;
	private LgrTableViewer tableViewerAvoir;
	private LgrTableViewer tableViewerDocumentEditable;
	private TaRAcompteDAO daoRAcompte = null;
	private TaRAvoirDAO daoRAvoir = null;
	private TaAvoirDAO daoAvoir = null;
	private TaRAvoir taRAvoir = null;
	private TaAcompteDAO daoAcompte = null;
	private TaRAcompte taRAcompte = null;
	private TaDocumentEditable taDocumentEditable = null;
	private TaDocumentEditableDAO daoDocumentEditable = null;
	private DataBindingContext dbcAffectation;
	private DataBindingContext dbcAffectationAvoirs;
	protected Map<Control, String> mapComposantChampsAffectation = null;
	protected Map<Control, String> mapComposantChampsAffectationAvoirs = null;
	public List<Control> listeComposantAffectation = null;
	public List<Control> listeComposantAffectationAvoirs = null;
	private TypeDoc typeDocPresent = TypeDoc.getInstance();
	private IObservableValue selectedAcompte;
	private IObservableValue selectedAvoir;
	private String typePaiementDefaut=null;
	

	public static final String C_COMMAND_DOCUMENT_INSERER_AFFECTATION_ID = "fr.legrain.Document.insererAffectation";
	protected HandlerInsererAffectation handlerInsererAffectation = new HandlerInsererAffectation();

	public static final String C_COMMAND_DOCUMENT_SUPPRIMER_AFFECTATION_ID = "fr.legrain.Document.supprimerAffectation";
	protected HandlerSupprimerAffectation handlerSupprimerAffectation = new HandlerSupprimerAffectation();

	public static final String C_COMMAND_DOCUMENT_ENREGISTRER_AFFECTATION_ID = "fr.legrain.Document.enregistrerAffectation";
	protected HandlerEnregistrerAffectation handlerEnregistrerAffectation = new HandlerEnregistrerAffectation();

	public static final String C_COMMAND_DOCUMENT_INSERER_AFFECTATION_AVOIR_ID = "fr.legrain.Document.insererAffectationAvoir";
	protected HandlerInsererAffectationAvoir handlerInsererAffectationAvoir = new HandlerInsererAffectationAvoir();

	public static final String C_COMMAND_DOCUMENT_SUPPRIMER_AFFECTATION_AVOIR_ID = "fr.legrain.Document.supprimerAffectationAvoir";
	protected HandlerSupprimerAffectationAvoir handlerSupprimerAffectationAvoir = new HandlerSupprimerAffectationAvoir();

	public static final String C_COMMAND_DOCUMENT_ENREGISTRER_AFFECTATION_AVOIR_ID = "fr.legrain.Document.enregistrerAffectationAvoir";
	protected HandlerEnregistrerAffectationAvoir handlerEnregistrerAffectationAvoir = new HandlerEnregistrerAffectationAvoir();

	public static final String C_COMMAND_DOCUMENT_OUVRIR_COURRIER_ID = "fr.legrain.gestionCommerciale.document.ouvrircourrier";
	private HandlerOuvrirCourrierTiers handlerOuvrirCourrierTiers = new HandlerOuvrirCourrierTiers();
	
	public static final String C_COMMAND_FACTURE_EMAIL_ID = "fr.legrain.facture.email";
	private HandlerSendMail handlerSendMail = new HandlerSendMail();

	// public static final String C_COMMAND_DOCUMENT_REINIT_CPAIEMENT_ID =
	// "fr.legrain.Document.reinitCPaiement";
	// protected HandlerReinitCPaiement handlerReinitCPaiement = new
	// HandlerReinitCPaiement();

	public static final String C_COMMAND_DOCUMENT_APPLIQUER_CPAIEMENT_ID = "fr.legrain.document.appliquerCPaiement";
	protected HandlerAppliquerCPaiement handlerAppliquerCPaiement = new HandlerAppliquerCPaiement();

	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private Object selectedTotauxFacture = new IHMTotauxDocument();
	private String[] listeChamp;
	private String nomClassController = this.getClass().getSimpleName();
	private Class classModel = IHMTotauxDocument.class;

	private MapChangeListener changeListener = new MapChangeListener();

	private TaFacture masterEntity = null;
	private TaFactureDAO masterDAO = null;
	private TaFactureDAO daoLocal = null;

	private LgrDozerMapper<IHMTotauxDocument, TaFacture> mapperUIToModel = new LgrDozerMapper<IHMTotauxDocument, TaFacture>();
	private LgrDozerMapper<TaFacture, IHMTotauxDocument> mapperModelToUI = new LgrDozerMapper<TaFacture, IHMTotauxDocument>();

	public SWTPaEditorTotauxController() {
	}

	public SWTPaEditorTotauxController(PaTotauxSWT vue) {
		this(vue, null);
	}

	public SWTPaEditorTotauxController(PaTotauxSWT vue/* , WorkbenchPart part */,
			EntityManager em) {
		if (em != null) {
			setEm(em);
		}
		try {
			setVue(vue);
			daoLocal = new TaFactureDAO(getEm());
			daoRAcompte = new TaRAcompteDAO(getEm());
			daoAcompte = new TaAcompteDAO(getEm());
			daoRAvoir = new TaRAvoirDAO(getEm());
			daoAvoir = new TaAvoirDAO(getEm());
			daoDocumentEditable = new TaDocumentEditableDAO(getEm());
			daoCPaiement = new TaCPaiementDAO(getEm());
			daoInfosFacture = new TaInfosFactureDAO(getEm());

			vue.getShell().addShellListener(this);
			vue.getShell().addTraverseListener(new Traverse());
			vue.getDateTimeDATE_ECH_FACTURE().addTraverseListener(
					new DateTraverse());
			vue.getDateTimeDATE_ECH_FACTURE().addFocusListener(
					dateFocusListener);
			// addDestroyListener(ibTaTable);
			// setIbTaTableStandard(masterDAO);

			// this.masterEntity.addModificationDocumentListener(masterDAO);
			// this.masterEntity.addModificationDocumentListener(this);
			// this.masterEntity.addChangeModeListener(this);

			// à l'insertion d'une nouvelle facture, le champ est contrôlé à
			// vide, ce qui fait
			// lorsque que l'on sort de la zone sans l'avoir rempli, il ne la
			// re-contrôle pas
			// d'où le rajout de se focusListener pour le forcer à remplir la
			// zone

			initController();

		} catch (Exception e) {
			logger.debug("", e);
		}
	}

	private void initController() {
		try {
			setDaoStandard(masterDAO);
			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);

			initVue();

			initMapComposantChamps();
			initMapComposantDecoratedField();

			listeComponentFocusableSWT(listeComposantFocusable);
			initDeplacementSaisie(listeComposantFocusable);
			initFocusOrder();
			initActions();

			branchementBouton();

			// bug #1261
			vue.getTfMT_REMISE().setEnabled(false);
			vue.getTfMT_ESCOMPTE().setEnabled(false);
			typePaiementDefaut = DocumentPlugin.getDefault().getPreferenceStore().getString(
							fr.legrain.document.preferences.PreferenceConstants.TYPE_PAIEMENT_DEFAUT);
			if (typePaiementDefaut == null || typePaiementDefaut=="")
				typePaiementDefaut="C";
			
			bind();
			bindCPaiement();
			try {
				((IHMTotauxDocument) selectedTotauxFacture)
						.setImprimer(FacturePlugin.getDefault()
								.getPreferenceStore().getBoolean(
										PreferenceConstants.IMPRIMER_AUTO));
				vue
						.getCbImprimerCourrier()
						.setSelection(
								FacturePlugin
										.getDefault()
										.getPreferenceStore()
										.getBoolean(
												PreferenceConstants.P_IMPRIMER_LES_COURRIERS_AUTOMATIQUEMENT));
			} catch (Exception e) {

			}
			// #AFAIRE
			// initEtatBouton();

			vue.getCbImprimerCourrier().addSelectionListener(
					new SelectionListener() {

						@Override
						public void widgetSelected(SelectionEvent e) {
							initEtatTableCourrier();
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
							widgetSelected(e);
						}
					});
			initEtatTableCourrier();
			vue.getPaEcran().layout();
			vue.getScrolledComposite().setMinSize(vue.getPaEcran().computeSize(SWT.DEFAULT, SWT.DEFAULT));

		} catch (Exception e) {
			if (e.getMessage() != null)
				vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaArticlesController", e);
		}
	}

	public void initEtatTableCourrier() {
		boolean enabled = true;
		if (!vue.getCbImprimerCourrier().getSelection()) {
			enabled = false;
		}
		vue.getGroupCourrier().setEnabled(enabled);
		vue.getTableCourrier().setEnabled(enabled);
		vue.getBtnFusionPublipostage().setEnabled(enabled);
	}

	public void bind() {
		try {

			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			LgrDozerMapper<TaFacture, IHMTotauxDocument> mapper = new LgrDozerMapper<TaFacture, IHMTotauxDocument>();
			if (ihmTotauxFacture == null)
				ihmTotauxFacture = new IHMTotauxDocument();
			if (masterEntity != null)
				mapper.map(masterEntity, ihmTotauxFacture);

			if (modelTotaux.getListeObjet().isEmpty()) {
				modelTotaux.getListeObjet().add(ihmTotauxFacture);
			}
			selectedTotauxFacture = ihmTotauxFacture;
			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			setDbcStandard(dbc);

			bindingFormSimple(dbc, realm, selectedTotauxFacture, classModel);
			bindingFormSimple(masterDAO, mapComposantChampsCommentaire, dbc,
					realm, selectedTotauxFacture, classModel);

			bindTVA();
			if (affichageAcomptes()) {
				bindListeAcomptesPossible();
				bindAffectation();
			}
			if (affichageAvoirs()) {
				bindListeAvoirsPossible();
				bindAffectationAvoirs();
			}
			bindCourrierImprimable();

		} catch (Exception e) {
			if (e.getMessage() != null)
				vue.getLaMessage().setText(e.getMessage());
			logger.error("", e);
		}
	}

	public void bindTVA() {
		try {
			modelLignesTVA = new ModelLignesTVA();
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			tableViewer = new LgrTableViewer(vue.getTableTVA());
			tableViewer.createTableCol(vue.getTableTVA(), "FactureLignesTVA",
					Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			listeChamp = tableViewer.setListeChampGrille("FactureLignesTVA",
					Const.C_FICHIER_LISTE_CHAMP_GRILLE);

			LgrViewerSupport.bind(tableViewer, new WritableList(modelLignesTVA
					.remplirListe(masterEntity), IHMLignesTVA.class),
					BeanProperties.values(listeChamp));

			dbc = new DataBindingContext(realm);

		} catch (Exception e) {
			if (e.getMessage() != null)
				vue.getLaMessage().setText(e.getMessage());
			logger.error("", e);
		}
	}

	public void bindAffectation() {
		try {
			modelRAcompte = new ModelRDocument();

			((PaAffectationAcompteSurFacture) findExpandIem(vue.getExpandBar(),
					paAffectation).getControl()).getLaTitreGrilleDest()
					.setText("Liste des acomptes déjà affectés à la facture");

			tableViewerAffectation = new LgrTableViewer(
					((PaAffectationAcompteSurFacture) findExpandIem(
							vue.getExpandBar(), paAffectation).getControl())
							.getGrilleDest());

			tableViewerAffectation.createTableCol(IHMRDocument.class,
					((PaAffectationAcompteSurFacture) findExpandIem(
							vue.getExpandBar(), paAffectation).getControl())
							.getGrilleDest(), "AffectationsAcompte",
					Const.C_FICHIER_LISTE_CHAMP_GRILLE, 0);
			String[] listeChampAffectation = tableViewerAffectation
					.setListeChampGrille("AffectationsAcompte",
							Const.C_FICHIER_LISTE_CHAMP_GRILLE);

			// selectedRAcompte = new IHMRAcompte();
			selectedRAcompte = ViewersObservables
					.observeSingleSelection(tableViewerAffectation);
			LgrViewerSupport.bind(tableViewerAffectation, new WritableList(
					modelRAcompte.remplirListeRAcompte(masterEntity),
					IHMRDocument.class), BeanProperties
					.values(listeChampAffectation));

			dbcAffectation = new DataBindingContext(realm);

			((PaAffectationAcompteSurFacture) findExpandIem(vue.getExpandBar(),
					paAffectation).getControl()).getGrilleDest()
					.addMouseListener(new MouseAdapter() {

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

			bindingFormMaitreDetail(mapComposantChampsAffectation,
					dbcAffectation, realm, selectedRAcompte, IHMRDocument.class);
			initEtatBoutonAffectation();
		} catch (Exception e) {
			if (e.getMessage() != null)
				vue.getLaMessage().setText(e.getMessage());
			logger.error("", e);
		}
	}

	public void bindAffectationAvoirs() {
		try {
			modelRAvoir = new ModelRDocument();

			((PaAffectationAcompteSurFacture) findExpandIem(vue.getExpandBar(),
					paAffectationAvoir).getControl()).getLaTitreGrilleDest()
					.setText("Liste des avoirs déjà affectés à la facture");

			tableViewerAffectationAvoirs = new LgrTableViewer(
					((PaAffectationAcompteSurFacture) findExpandIem(
							vue.getExpandBar(), paAffectationAvoir)
							.getControl()).getGrilleDest());

			tableViewerAffectationAvoirs.createTableCol(IHMRDocument.class,
					((PaAffectationAcompteSurFacture) findExpandIem(
							vue.getExpandBar(), paAffectationAvoir)
							.getControl()).getGrilleDest(),
					"AffectationsAcompte", Const.C_FICHIER_LISTE_CHAMP_GRILLE,
					0);
			String[] listeChampAffectation = tableViewerAffectationAvoirs
					.setListeChampGrille("AffectationsAcompte",
							Const.C_FICHIER_LISTE_CHAMP_GRILLE);

			selectedRAvoir = ViewersObservables
					.observeSingleSelection(tableViewerAffectationAvoirs);
			LgrViewerSupport.bind(tableViewerAffectationAvoirs,
					new WritableList(modelRAvoir
							.remplirListeRAcompte(masterEntity),
							IHMRDocument.class), BeanProperties
							.values(listeChampAffectation));

			dbcAffectationAvoirs = new DataBindingContext(realm);

			((PaAffectationAcompteSurFacture) findExpandIem(vue.getExpandBar(),
					paAffectationAvoir).getControl()).getGrilleDest()
					.addMouseListener(new MouseAdapter() {

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

			bindingFormMaitreDetail(mapComposantChampsAffectationAvoirs,
					dbcAffectationAvoirs, realm, selectedRAvoir,
					IHMRDocument.class);
			initEtatBoutonAffectation();
		} catch (Exception e) {
			if (e.getMessage() != null)
				vue.getLaMessage().setText(e.getMessage());
			logger.error("", e);
		}
	}

	public void bindCourrierImprimable() {
		try {
			modelDocumentEditable = new ModelGeneralObjet<SWTDocumentEditable, TaDocumentEditableDAO, TaDocumentEditable>(
					 daoDocumentEditable.findByCodeTypeDoc(TaFacture.TYPE_DOC),
					//daoDocumentEditable.findByCodeTypeDoc("F"),
					SWTDocumentEditable.class, getEm());
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			String cleListeTitreColonne = "SWTPaEditorTotauxControllerCourrier";

			tableViewerDocumentEditable = new LgrTableViewer(vue
					.getTableCourrier());
			tableViewerDocumentEditable.createTableCol(vue.getTableCourrier(),
					cleListeTitreColonne, Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			String[] listeChamp = tableViewerDocumentEditable
					.setListeChampGrille(cleListeTitreColonne,
							Const.C_FICHIER_LISTE_CHAMP_GRILLE);

			tableViewerDocumentEditable
					.setCheckStateProvider(new ICheckStateProvider() {
						@Override
						public boolean isGrayed(Object element) {
							return false;
						}

						@Override
						public boolean isChecked(Object element) {
							if (element instanceof SWTDocumentEditable) {
								if (((SWTDocumentEditable) element).getDefaut() != null
										&& ((SWTDocumentEditable) element)
												.getDefaut()) {
									return true;
								}
							}
							return false;
						}
					});

			LgrViewerSupport.bind(tableViewerDocumentEditable,
					new WritableList(modelDocumentEditable.remplirListe(),
							SWTDocumentEditable.class), BeanProperties
							.values(listeChamp));

			dbc = new DataBindingContext(realm);

		} catch (Exception e) {
			if (e.getMessage() != null)
				vue.getLaMessage().setText(e.getMessage());
			logger.error("", e);
		}
	}

	public List<SWTDocumentEditable> listeCourrierAImprimer() {
		List<SWTDocumentEditable> result = new ArrayList<SWTDocumentEditable>();
		Object[] checkedElements = tableViewerDocumentEditable
				.getCheckedElements();
		for (int i = 0; i < checkedElements.length; i++) {
			result.add((SWTDocumentEditable) checkedElements[i]);
		}
		return result;
	}

	private void changementDeSelection() {
		initEtatComposant();
	}

	public void bindListeAcomptesPossible() {
		try {
			// modelAcomptesDisponibles = new
			// ModelGeneralObjet<IHMAideAcompte,TaAcompteDAO,
			// TaAcompte>(new TaAcompteDAO(getEm()),IHMAideAcompte.class);
			modelAcomptesDisponibles = new ModelAcompteDisponible();
			((PaAffectationAcompteSurFacture) findExpandIem(vue.getExpandBar(),
					paAffectation).getControl()).getLaTitreGrilleSource()
					.setText(titreAcomptesDisponibles);

			tableViewerAcompte = new LgrTableViewer(
					((PaAffectationAcompteSurFacture) findExpandIem(
							vue.getExpandBar(), paAffectation).getControl())
							.getGrilleSource());
			tableViewerAcompte.createTableCol(IHMAideAcompte.class,
					((PaAffectationAcompteSurFacture) findExpandIem(
							vue.getExpandBar(), paAffectation).getControl())
							.getGrilleSource(), "AcomptesDisponibles",
					Const.C_FICHIER_LISTE_CHAMP_GRILLE, 0);
			String[] listeChampAffectation = tableViewerAcompte
					.setListeChampGrille("AcomptesDisponibles",
							Const.C_FICHIER_LISTE_CHAMP_GRILLE);

			selectedAcompte = ViewersObservables
					.observeSingleSelection(tableViewerAcompte);
			LgrViewerSupport.bind(tableViewerAcompte, new WritableList(
					modelAcomptesDisponibles
							.remplirListe(masterEntity, getEm()),
					IHMAideAcompte.class), BeanProperties
					.values(listeChampAffectation));
			((PaAffectationAcompteSurFacture) findExpandIem(vue.getExpandBar(),
					paAffectation).getControl()).getGrilleSource()
					.addMouseListener(new MouseAdapter() {

						public void mouseDoubleClick(MouseEvent e) {
							String idEditor = typeDocPresent.getEditorDoc()
									.get(typeDocPresent.TYPE_ACOMPTE);
							String valeurIdentifiant = ((IHMAideAcompte) selectedAcompte
									.getValue()).getCodeDocument();
							ouvreDocument(valeurIdentifiant, idEditor);
						}

					});

		} catch (Exception e) {
			if (e.getMessage() != null)
				vue.getLaMessage().setText(e.getMessage());
			logger.error("", e);
		}
	}

	public void bindListeAvoirsPossible() {
		try {
			modelAvoirsDisponibles = new ModelAvoirDisponible();
			(((PaAffectationAcompteSurFacture) findExpandIem(
					vue.getExpandBar(), paAffectationAvoir).getControl()))
					.getLaTitreGrilleSource().setText(titreAvoirsDisponibles);

			tableViewerAvoir = new LgrTableViewer(
					((PaAffectationAcompteSurFacture) findExpandIem(
							vue.getExpandBar(), paAffectationAvoir)
							.getControl()).getGrilleSource());
			tableViewerAvoir.createTableCol(IHMAideDocument.class,
					((PaAffectationAcompteSurFacture) findExpandIem(
							vue.getExpandBar(), paAffectationAvoir)
							.getControl()).getGrilleSource(),
					"AcomptesDisponibles", Const.C_FICHIER_LISTE_CHAMP_GRILLE,
					0);
			String[] listeChampAffectation = tableViewerAcompte
					.setListeChampGrille("AcomptesDisponibles",
							Const.C_FICHIER_LISTE_CHAMP_GRILLE);

			selectedAvoir = ViewersObservables
					.observeSingleSelection(tableViewerAvoir);
			LgrViewerSupport.bind(tableViewerAvoir, new WritableList(
					modelAvoirsDisponibles.remplirListe(masterEntity, getEm()),
					IHMAideDocument.class), BeanProperties
					.values(listeChampAffectation));
			((PaAffectationAcompteSurFacture) findExpandIem(vue.getExpandBar(),
					paAffectationAvoir).getControl()).getGrilleSource()
					.addMouseListener(new MouseAdapter() {

						public void mouseDoubleClick(MouseEvent e) {
							String idEditor = typeDocPresent.getEditorDoc()
									.get(typeDocPresent.TYPE_AVOIR);
							String valeurIdentifiant = ((IHMAideDocument) selectedAvoir
									.getValue()).getCodeDocument();
							ouvreDocument(valeurIdentifiant, idEditor);
						}

					});

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
		param.setFocus(masterDAO.getModeObjet().getFocusCourant());
		try {
			// actInserer();
			bind();
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	/**
	 * Permet de pouvoir appele
	 * {@link SWTPaEditorTotauxController#initVerifySaisie()} qui est une
	 * methode protected, dans l'initialisation du MultipageEditor. Cette
	 * methode ne peut etre appele qu'apres l'initialisation du masterDAO dans
	 * le MultipageEditor
	 * 
	 * @see #initVerifySaisie()
	 */
	public void initVerifySaisiePublic() {
		initVerifySaisie();
	}

	/**
	 * @see #initVerifySaisiePublic()
	 */
	protected void initVerifySaisie() {
		if (mapInfosVerifSaisie == null)
			mapInfosVerifSaisie = new HashMap<Control, InfosVerifSaisie>();

		mapInfosVerifSaisie.put(vue.getTfMT_REMISE(), new InfosVerifSaisie(
				new TaFacture(), Const.C_MT_HT_CALC,
				new int[] { InfosVerifSaisie.CTRL_QUE_DES_CHIFFRES }));
		mapInfosVerifSaisie.put(vue.getTfMT_ESCOMPTE(), new InfosVerifSaisie(
				new TaFacture(), Const.C_MT_TTC_CALC,
				new int[] { InfosVerifSaisie.CTRL_QUE_DES_CHIFFRES }));
		// mapInfosVerifSaisie.put(vue.getTfREGLE_FACTURE(), new
		// InfosVerifSaisie(
		// new TaFacture(), Const.C_REGLE_DOCUMENT,
		// new int[] { InfosVerifSaisie.CTRL_QUE_DES_CHIFFRES }));
		mapInfosVerifSaisie.put(vue.getTfTX_REM_HT_FACTURE(),
				new InfosVerifSaisie(new TaFacture(),
						Const.C_TX_REM_HT_DOCUMENT,
						new int[] { InfosVerifSaisie.CTRL_QUE_DES_CHIFFRES }));
		mapInfosVerifSaisie.put(vue.getTfTX_REM_TTC_FACTURE(),
				new InfosVerifSaisie(new TaFacture(),
						Const.C_TX_REM_TTC_DOCUMENT,
						new int[] { InfosVerifSaisie.CTRL_QUE_DES_CHIFFRES }));
		// mapInfosVerifSaisie.put(vue.getTfCODE_T_PAIEMENT(),
		// new InfosVerifSaisie(new TaTPaiement(),
		// Const.C_CODE_T_PAIEMENT, null));
		// mapInfosVerifSaisie.put(vue.getTfLIBELLE_PAIEMENT(),
		// new InfosVerifSaisie(new TaFacture(),
		// Const.C_LIBELLE_PAIEMENT, null));
		mapInfosVerifSaisie.put(((PaInfosCondPaiement) findExpandIem(
				vue.getExpandBarCondition(), paInfosCondPaiement).getControl())
				.getTfCODE_C_PAIEMENT(), new InfosVerifSaisie(
				new TaInfosFacture(), Const.C_CODE_C_PAIEMENT, null));
		mapInfosVerifSaisie.put(((PaInfosCondPaiement) findExpandIem(
				vue.getExpandBarCondition(), paInfosCondPaiement).getControl())
				.getTfREPORT_C_PAIEMENT(), new InfosVerifSaisie(
				new TaInfosFacture(), Const.C_REPORT_C_PAIEMENT, null));
		mapInfosVerifSaisie.put(((PaInfosCondPaiement) findExpandIem(
				vue.getExpandBarCondition(), paInfosCondPaiement).getControl())
				.getTfFIN_MOIS_C_PAIEMENT(), new InfosVerifSaisie(
				new TaInfosFacture(), Const.C_FIN_MOIS_C_PAIEMENT, null));
		initVerifyListener(mapInfosVerifSaisie, masterDAO);
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

		if (mapComposantChampsCommentaire == null)
			mapComposantChampsCommentaire = new LinkedHashMap<Control, String>();

		if (listeComposantCPaiement == null)
			listeComposantCPaiement = new ArrayList<Control>();

		if (mapComposantChampsCPaiement == null)
			mapComposantChampsCPaiement = new LinkedHashMap<Control, String>();

		if (mapComposantChampsAffectation == null)
			mapComposantChampsAffectation = new LinkedHashMap<Control, String>();

		if (listeComposantAffectation == null)
			listeComposantAffectation = new ArrayList<Control>();

		if (mapComposantChampsAffectationAvoirs == null)
			mapComposantChampsAffectationAvoirs = new LinkedHashMap<Control, String>();

		if (listeComposantAffectationAvoirs == null)
			listeComposantAffectationAvoirs = new ArrayList<Control>();

		for (Control c : mapComposantChamps.keySet()) {
			listeComposantEntete.add(c);
		}

		vue.getTfMT_HT_APRES_REMISE().setEnabled(false);
		vue.getTfMT_TTC_APRES_REMISE().setEnabled(false);
		vue.getTfMT_TVA_APRES_REMISE().setEnabled(false);

		vue.getTfMT_HT_AVANT_REMISE().setEnabled(false);
		vue.getTfMT_TTC_AVANT_REMISE().setEnabled(false);
		vue.getTfMT_TVA_AVANT_REMISE().setEnabled(false);

		vue.getTfNET_A_PAYER().setEnabled(false);
		vue.getTfRESTE_A_REGLER().setEnabled(false);
		vue.getTfACOMPTES().setEnabled(false);
		vue.getTfAVOIRS().setEnabled(false);
		


		
		//
		mapComposantChamps.put(vue.getTfMT_HT_AVANT_REMISE(),
				Const.C_MT_HT_CALC);
		mapComposantChamps.put(vue.getTfMT_TVA_AVANT_REMISE(),
				Const.C_MT_TVA_CALC);
		mapComposantChamps.put(vue.getTfMT_TTC_AVANT_REMISE(),
				Const.C_MT_TTC_AVANTREMISEGLOBALE_CALC);
		mapComposantChamps.put(vue.getTfTX_REM_HT_FACTURE(),
				Const.C_TX_REM_HT_DOCUMENT);
		mapComposantChamps.put(vue.getTfMT_REMISE(), Const.C_REM_HT_DOCUMENT);
		mapComposantChamps.put(vue.getTfMT_HT_APRES_REMISE(),
				Const.C_NET_HT_CALC);
		mapComposantChamps.put(vue.getTfMT_TVA_APRES_REMISE(),
				Const.C_NET_TVA_CALC);
		mapComposantChamps.put(vue.getTfMT_TTC_APRES_REMISE(),
				Const.C_MT_TTC_CALC);
		mapComposantChamps.put(vue.getTfTX_REM_TTC_FACTURE(),
				Const.C_TX_REM_TTC_DOCUMENT);
		mapComposantChamps
				.put(vue.getTfMT_ESCOMPTE(), Const.C_REM_TTC_DOCUMENT);
		mapComposantChamps.put(vue.getTfNET_A_PAYER(), Const.C_NET_TTC_CALC);

		mapComposantChamps.put(vue.getTfACOMPTES(), Const.C_ACOMPTES);
		mapComposantChamps.put(vue.getTfAVOIRS(), Const.C_AVOIRS);
		mapComposantChamps.put(vue.getTfCODE_T_PAIEMENT(),
				Const.C_CODE_T_PAIEMENT);
		mapComposantChamps
				.put(vue.getTfREGLE_FACTURE(), Const.C_REGLE_DOCUMENT);
		mapComposantChamps.put(vue.getTfLIBELLE_PAIEMENT(),
				Const.C_LIBELLE_PAIEMENT);
		mapComposantChamps.put(vue.getDateTimeDATE_ECH_FACTURE(),
				Const.C_DATE_ECH_DOCUMENT);
		mapComposantChamps.put(vue.getTfRESTE_A_REGLER(),
				Const.C_RESTE_A_REGLER);
		//		
		mapComposantChamps.put(vue.getCbImprimer(), "imprimer");

		for (Control c : mapComposantChamps.keySet()) {
			c.setToolTipText(mapComposantChamps.get(c));
		}

		mapComposantChampsCommentaire.put(((PaCommentaire) findExpandIem(
				vue.getExpandBar(), paCommentaire).getControl())
				.getTfLIBL_COMMENTAIRE(), Const.C_COMMENTAIRE);

		if (affichageAcomptes()) {
			mapComposantChampsAffectation.put(
					((PaAffectationAcompteSurFacture) findExpandIem(
							vue.getExpandBar(), paAffectation).getControl())
							.getTfCODE_DOCUMENT(), Const.C_CODE_DOCUMENT);

			mapComposantChampsAffectation.put(
					((PaAffectationAcompteSurFacture) findExpandIem(
							vue.getExpandBar(), paAffectation).getControl())
							.getTfMONTANT_AFFECTE(), Const.C_MONTANT_AFFECTE);

			listeComposantAffectation
					.add(((PaAffectationAcompteSurFacture) findExpandIem(
							vue.getExpandBar(), paAffectation).getControl())
							.getTfMONTANT_AFFECTE());
			listeComposantAffectation
					.add(((PaAffectationAcompteSurFacture) findExpandIem(
							vue.getExpandBar(), paAffectation).getControl())
							.getBtnValider());
		}

		if (affichageAvoirs()) {
			mapComposantChampsAffectationAvoirs.put(
					((PaAffectationAcompteSurFacture) findExpandIem(
							vue.getExpandBar(), paAffectationAvoir)
							.getControl()).getTfCODE_DOCUMENT(),
					Const.C_CODE_DOCUMENT);

			mapComposantChampsAffectationAvoirs.put(
					((PaAffectationAcompteSurFacture) findExpandIem(
							vue.getExpandBar(), paAffectationAvoir)
							.getControl()).getTfMONTANT_AFFECTE(),
					Const.C_MONTANT_AFFECTE);

			listeComposantAffectationAvoirs
					.add(((PaAffectationAcompteSurFacture) findExpandIem(
							vue.getExpandBar(), paAffectationAvoir)
							.getControl()).getTfMONTANT_AFFECTE());
			listeComposantAffectationAvoirs
					.add(((PaAffectationAcompteSurFacture) findExpandIem(
							vue.getExpandBar(), paAffectationAvoir)
							.getControl()).getBtnValider());
		}

		mapComposantChampsCPaiement.put(((PaInfosCondPaiement) findExpandIem(
				vue.getExpandBarCondition(), paInfosCondPaiement).getControl())
				.getTfCODE_C_PAIEMENT(), Const.C_CODE_C_PAIEMENT);
		mapComposantChampsCPaiement.put(((PaInfosCondPaiement) findExpandIem(
				vue.getExpandBarCondition(), paInfosCondPaiement).getControl())
				.getTfLIB_C_PAIEMENT(), Const.C_LIB_C_PAIEMENT);
		mapComposantChampsCPaiement.put(((PaInfosCondPaiement) findExpandIem(
				vue.getExpandBarCondition(), paInfosCondPaiement).getControl())
				.getTfREPORT_C_PAIEMENT(), Const.C_REPORT_C_PAIEMENT);
		mapComposantChampsCPaiement.put(((PaInfosCondPaiement) findExpandIem(
				vue.getExpandBarCondition(), paInfosCondPaiement).getControl())
				.getTfFIN_MOIS_C_PAIEMENT(), Const.C_FIN_MOIS_C_PAIEMENT);

		for (Control c : mapComposantChampsCPaiement.keySet()) {
			listeComposantCPaiement.add(c);
		}

		listeComposantCPaiement.add(((PaInfosCondPaiement) findExpandIem(
				vue.getExpandBarCondition(), paInfosCondPaiement).getControl())
				.getBtnAppliquer());

		listeComposantFocusable.add(vue.getTfTX_REM_HT_FACTURE());
		listeComposantFocusable.add(vue.getTfMT_REMISE());
		listeComposantFocusable.add(vue.getTfTX_REM_TTC_FACTURE());
		listeComposantFocusable.add(vue.getTfMT_ESCOMPTE());
		listeComposantFocusable.add(vue.getTfREGLE_FACTURE());
		listeComposantFocusable.add(vue.getTfCODE_T_PAIEMENT());
		listeComposantFocusable.add(vue.getDateTimeDATE_ECH_FACTURE());
		listeComposantFocusable.add(vue.getTfLIBELLE_PAIEMENT());
		listeComposantFocusable.add(vue.getCbImprimer());
//		listeComposantFocusable.add(vue.getBtnEmail());

		listeComposantFocusable.add(((PaInfosCondPaiement) findExpandIem(
				vue.getExpandBarCondition(), paInfosCondPaiement).getControl())
				.getTfCODE_C_PAIEMENT());
		listeComposantFocusable.add(((PaInfosCondPaiement) findExpandIem(
				vue.getExpandBarCondition(), paInfosCondPaiement).getControl())
				.getTfREPORT_C_PAIEMENT());
		listeComposantFocusable.add(((PaInfosCondPaiement) findExpandIem(
				vue.getExpandBarCondition(), paInfosCondPaiement).getControl())
				.getTfFIN_MOIS_C_PAIEMENT());
		listeComposantFocusable.add(((PaInfosCondPaiement) findExpandIem(
				vue.getExpandBarCondition(), paInfosCondPaiement).getControl())
				.getBtnAppliquer());

		listeComposantFocusable.add(vue.getBtnFusionPublipostage());
		listeComposantFocusable.add(vue.getBtnOuvreRepertoireCourrier());

		listeComposantFocusable.add(((PaCommentaire) findExpandIem(
				vue.getExpandBar(), paCommentaire).getControl())
				.getTfLIBL_COMMENTAIRE());

		listeComposantFocusable.add(vue.getPaBtnAvecAssistant().getPaBtn()
				.getBtnEnregistrer());
		listeComposantFocusable.add(vue.getPaBtnAvecAssistant().getPaBtn()
				.getBtnInserer());
		listeComposantFocusable.add(vue.getPaBtnAvecAssistant().getPaBtn()
				.getBtnModifier());
		listeComposantFocusable.add(vue.getPaBtnAvecAssistant().getPaBtn()
				.getBtnSupprimer());
		listeComposantFocusable.add(vue.getPaBtnAvecAssistant().getPaBtn()
				.getBtnFermer());
		listeComposantFocusable.add(vue.getPaBtnAvecAssistant().getPaBtn()
				.getBtnAnnuler());
		listeComposantFocusable.add(vue.getPaBtnAvecAssistant().getPaBtn()
				.getBtnImprimer());

		listeComposantFocusable.add(vue.getPaBtnAvecAssistant()
				.getPaBtnAssitant().getBtnPrecedent());
		listeComposantFocusable.add(vue.getPaBtnAvecAssistant()
				.getPaBtnAssitant().getBtnSuivant());

		if (affichageAcomptes()) {
			listeComposantFocusable
					.add(((PaAffectationAcompteSurFacture) findExpandIem(
							vue.getExpandBar(), paAffectation).getControl())
							.getBtnAjouter());
			listeComposantFocusable
					.add(((PaAffectationAcompteSurFacture) findExpandIem(
							vue.getExpandBar(), paAffectation).getControl())
							.getBtnEnlever());
			listeComposantFocusable
					.add(((PaAffectationAcompteSurFacture) findExpandIem(
							vue.getExpandBar(), paAffectation).getControl())
							.getBtnValider());
		}

		if (affichageAvoirs()) {
			listeComposantFocusable
					.add(((PaAffectationAcompteSurFacture) findExpandIem(
							vue.getExpandBar(), paAffectationAvoir)
							.getControl()).getBtnAjouter());
			listeComposantFocusable
					.add(((PaAffectationAcompteSurFacture) findExpandIem(
							vue.getExpandBar(), paAffectationAvoir)
							.getControl()).getBtnEnlever());
			listeComposantFocusable
					.add(((PaAffectationAcompteSurFacture) findExpandIem(
							vue.getExpandBar(), paAffectationAvoir)
							.getControl()).getBtnValider());
		}
		if (mapInitFocus == null)
			mapInitFocus = new LinkedHashMap<ModeObjet.EnumModeObjet, Control>();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION, vue
				.getTfTX_REM_HT_FACTURE());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue
				.getTfTX_REM_HT_FACTURE());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION, vue
				.getTfTX_REM_HT_FACTURE());
		if (affichageAcomptes()) {
			((PaAffectationAcompteSurFacture) findExpandIem(vue.getExpandBar(),
					paAffectation).getControl()).getBtnAjouter().setImage(
					LibrairiesEcranPlugin.ir
							.get(LibrairiesEcranPlugin.IMAGE_INSERER));
			((PaAffectationAcompteSurFacture) findExpandIem(vue.getExpandBar(),
					paAffectation).getControl()).getBtnEnlever().setImage(
					LibrairiesEcranPlugin.ir
							.get(LibrairiesEcranPlugin.IMAGE_SUPPRIMER));
			((PaAffectationAcompteSurFacture) findExpandIem(vue.getExpandBar(),
					paAffectation).getControl()).getBtnValider().setImage(
					LibrairiesEcranPlugin.ir
							.get(LibrairiesEcranPlugin.IMAGE_ENREGISTRER));

			((PaAffectationAcompteSurFacture) findExpandIem(vue.getExpandBar(),
					paAffectation).getControl()).getTfMONTANT_AFFECTE()
					.addVerifyListener(queDesChiffresPositifs);

			((PaAffectationAcompteSurFacture) findExpandIem(vue.getExpandBar(),
					paAffectation).getControl()).getTfCODE_DOCUMENT()
					.setEnabled(false);

			((PaAffectationAcompteSurFacture) findExpandIem(vue.getExpandBar(),
					paAffectation).getControl()).getBtnAjouter().setText("");
			((PaAffectationAcompteSurFacture) findExpandIem(vue.getExpandBar(),
					paAffectation).getControl()).getBtnEnlever().setText("");
			((PaAffectationAcompteSurFacture) findExpandIem(vue.getExpandBar(),
					paAffectation).getControl()).getBtnValider().setText("");
		}

		if (affichageAvoirs()) {
			((PaAffectationAcompteSurFacture) findExpandIem(vue.getExpandBar(),
					paAffectationAvoir).getControl()).getBtnAjouter().setImage(
					LibrairiesEcranPlugin.ir
							.get(LibrairiesEcranPlugin.IMAGE_INSERER));
			((PaAffectationAcompteSurFacture) findExpandIem(vue.getExpandBar(),
					paAffectationAvoir).getControl()).getBtnEnlever().setImage(
					LibrairiesEcranPlugin.ir
							.get(LibrairiesEcranPlugin.IMAGE_SUPPRIMER));
			((PaAffectationAcompteSurFacture) findExpandIem(vue.getExpandBar(),
					paAffectationAvoir).getControl()).getBtnValider().setImage(
					LibrairiesEcranPlugin.ir
							.get(LibrairiesEcranPlugin.IMAGE_ENREGISTRER));

			((PaAffectationAcompteSurFacture) findExpandIem(vue.getExpandBar(),
					paAffectationAvoir).getControl()).getTfMONTANT_AFFECTE()
					.addVerifyListener(queDesChiffresPositifs);

			((PaAffectationAcompteSurFacture) findExpandIem(vue.getExpandBar(),
					paAffectationAvoir).getControl()).getTfCODE_DOCUMENT()
					.setEnabled(false);

			((PaAffectationAcompteSurFacture) findExpandIem(vue.getExpandBar(),
					paAffectationAvoir).getControl()).getBtnAjouter().setText(
					"");
			((PaAffectationAcompteSurFacture) findExpandIem(vue.getExpandBar(),
					paAffectationAvoir).getControl()).getBtnEnlever().setText(
					"");
			((PaAffectationAcompteSurFacture) findExpandIem(vue.getExpandBar(),
					paAffectationAvoir).getControl()).getBtnValider().setText(
					"");
		}

		//		

		activeModifytListener();
		activeModifytListener(mapComposantChampsAffectation, daoAcompte,
				new LgrModifyListener());
		activeModifytListener(mapComposantChampsAffectationAvoirs, daoAvoir,
				new LgrModifyListener());

		vue.getTfREGLE_FACTURE().addVerifyListener(queDesChiffresPositifs);
		vue.getTfTX_REM_HT_FACTURE().addVerifyListener(queDesChiffresPositifs);
		vue.getTfMT_ESCOMPTE().addVerifyListener(queDesChiffresPositifs);
		vue.getTfMT_REMISE().addVerifyListener(queDesChiffresPositifs);
		vue.getTfTX_REM_TTC_FACTURE().addVerifyListener(queDesChiffresPositifs);
		modifRegler();
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
		mapCommand.put(C_COMMAND_DOCUMENT_OUVRIR_COURRIER_ID,
				handlerOuvrirCourrierTiers);
		// mapCommand.put(C_COMMAND_DOCUMENT_REINIT_CPAIEMENT_ID,
		// handlerReinitCPaiement);
		mapCommand.put(C_COMMAND_DOCUMENT_APPLIQUER_CPAIEMENT_ID,
				handlerAppliquerCPaiement);
		
		mapCommand.put(C_COMMAND_FACTURE_EMAIL_ID,
				handlerSendMail);

		if (affichageAcomptes()) {
			mapCommand.put(C_COMMAND_DOCUMENT_INSERER_AFFECTATION_ID,
					handlerInsererAffectation);
			mapCommand.put(C_COMMAND_DOCUMENT_SUPPRIMER_AFFECTATION_ID,
					handlerSupprimerAffectation);
			mapCommand.put(C_COMMAND_DOCUMENT_ENREGISTRER_AFFECTATION_ID,
					handlerEnregistrerAffectation);
		}
		if (affichageAvoirs()) {
			mapCommand.put(C_COMMAND_DOCUMENT_INSERER_AFFECTATION_AVOIR_ID,
					handlerInsererAffectationAvoir);
			mapCommand.put(C_COMMAND_DOCUMENT_SUPPRIMER_AFFECTATION_AVOIR_ID,
					handlerSupprimerAffectationAvoir);
			mapCommand.put(C_COMMAND_DOCUMENT_ENREGISTRER_AFFECTATION_AVOIR_ID,
					handlerEnregistrerAffectationAvoir);
		}

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

		mapActions.put(vue.getBtnOuvreRepertoireCourrier(),
				C_COMMAND_DOCUMENT_OUVRIR_COURRIER_ID);
		
//		mapActions.put(vue.getBtnEmail(),
//				C_COMMAND_FACTURE_EMAIL_ID);

		mapActions.put(((PaInfosCondPaiement) findExpandIem(
				vue.getExpandBarCondition(), paInfosCondPaiement).getControl())
				.getBtnAppliquer(), C_COMMAND_DOCUMENT_APPLIQUER_CPAIEMENT_ID);
		if (affichageAcomptes()) {
			mapActions
					.put(((PaAffectationAcompteSurFacture) findExpandIem(
							vue.getExpandBar(), paAffectation).getControl())
							.getBtnAjouter(),
							C_COMMAND_DOCUMENT_INSERER_AFFECTATION_ID);
			mapActions.put(((PaAffectationAcompteSurFacture) findExpandIem(
					vue.getExpandBar(), paAffectation).getControl())
					.getBtnEnlever(),
					C_COMMAND_DOCUMENT_SUPPRIMER_AFFECTATION_ID);
			mapActions.put(((PaAffectationAcompteSurFacture) findExpandIem(
					vue.getExpandBar(), paAffectation).getControl())
					.getBtnValider(),
					C_COMMAND_DOCUMENT_ENREGISTRER_AFFECTATION_ID);
		}

		if (affichageAvoirs()) {
			mapActions.put(((PaAffectationAcompteSurFacture) findExpandIem(
					vue.getExpandBar(), paAffectationAvoir).getControl())
					.getBtnAjouter(),
					C_COMMAND_DOCUMENT_INSERER_AFFECTATION_AVOIR_ID);
			mapActions.put(((PaAffectationAcompteSurFacture) findExpandIem(
					vue.getExpandBar(), paAffectationAvoir).getControl())
					.getBtnEnlever(),
					C_COMMAND_DOCUMENT_SUPPRIMER_AFFECTATION_AVOIR_ID);
			mapActions.put(((PaAffectationAcompteSurFacture) findExpandIem(
					vue.getExpandBar(), paAffectationAvoir).getControl())
					.getBtnValider(),
					C_COMMAND_DOCUMENT_ENREGISTRER_AFFECTATION_AVOIR_ID);
		}

		mapActions.put(vue.getPaBtnAvecAssistant().getPaBtnAssitant()
				.getBtnPrecedent(), C_COMMAND_GLOBAL_PRECEDENT_ID);
		mapActions.put(vue.getPaBtnAvecAssistant().getPaBtnAssitant()
				.getBtnSuivant(), C_COMMAND_GLOBAL_SUIVANT_ID);

		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID,
				C_COMMAND_GLOBAL_REFRESH_ID };
		mapActions.put(null, tabActionsAutres);

		Menu popupMenuFormulaire = new Menu(vue.getShell(), SWT.POP_UP);
		Menu popupMenuGrille = new Menu(vue.getShell(), SWT.POP_UP);
		Menu[] tabPopups = new Menu[] { popupMenuFormulaire, popupMenuGrille };
		this.initPopupAndButtons(mapActions, tabPopups);
//		vue.getPaTotaux().setMenu(popupMenuFormulaire);
		// }
	}

	public SWTPaEditorTotauxController getThis() {
		return this;
	}

	@Override
	public boolean onClose() throws ExceptLgr {
		return true;
	}

	public void retourEcran(final RetourEcranEvent evt) {
		try {
			if (evt.getSource() instanceof PaChoixGenerationDocumentController) {
				DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(
						this, C_COMMAND_GLOBAL_REFRESH_ID);
				fireDeclencheCommandeController(e);
			} else if (evt.getRetour() != null
					&& (evt.getSource() instanceof SWTPaAideControllerSWT && !evt
							.getRetour().getResult().equals(""))) {
				if (getFocusAvantAideSWT() instanceof Text) {
					try {
						((Text) getFocusAvantAideSWT())
								.setText(((ResultAffiche) evt.getRetour())
										.getResult());
						if (getFocusAvantAideSWT().equals(
								vue.getTfCODE_T_PAIEMENT())) {
							ctrlUnChampsSWT(vue.getTfCODE_T_PAIEMENT());
							TaTPaiement f = null;
							TaTPaiementDAO t = new TaTPaiementDAO(getEm());
							f = t.findById(Integer
									.parseInt(((ResultAffiche) evt.getRetour())
											.getIdResult()));
							t = null;
							if (masterEntity.getTaRReglement() != null)
								masterEntity.getTaRReglement().getTaReglement()
										.setTaTPaiement(f);
							initialisationDesCPaiement(true);
							calculDateEcheance(true);
							masterEntity.mettreAJourDateEcheanceReglements();
						}
						if (focusDansCPaiment(getFocusCourantSWT())) {
							String id = evt.getRetour().getIdResult();

							TaCPaiementDAO taCPaiementDAO = new TaCPaiementDAO(
									getEm());
							TaCPaiement taCPaiement = taCPaiementDAO
									.findById(Integer.parseInt(id));
							mapperModelToUICPaiementInfosDocument.map(
									taCPaiement,
									((IHMInfosCPaiement) selectedCPaiement));
							calculDateEcheance();
						}

						ctrlUnChampsSWT(getFocusAvantAideSWT());
					} catch (Exception e) {
						logger.error("", e);
						vue.getLaMessage().setText(e.getMessage());
					}
				}
			}
			super.retourEcran(evt);
		} finally {
			// ne pas enlever car sur demande de l'aide, je rends enable false
			// tous les boutons
			// donc au retour de l'aide, je reinitialise les boutons suivant
			// l'état du dataset
			// activeWorkenchPartCommands(true);
			// controllerLigne.activeWorkenchPartCommands(true);
		}
	}

	@Override
	protected void actInserer() throws Exception {
		DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(
				this, C_COMMAND_GLOBAL_INSERER_ID);
		fireDeclencheCommandeController(e);
		ChangementDePageEvent change = new ChangementDePageEvent(this,
				ChangementDePageEvent.AUTRE, 0);
		fireChangementDePage(change);
	}

	@Override
	protected void actModifier() throws Exception {
		DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(
				this, C_COMMAND_GLOBAL_MODIFIER_ID);
		fireDeclencheCommandeController(e);
	}

	@Override
	protected void actSupprimer() throws Exception {
		DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(
				this, C_COMMAND_GLOBAL_SUPPRIMER_ID);
		fireDeclencheCommandeController(e);
		ChangementDePageEvent change = new ChangementDePageEvent(this,
				ChangementDePageEvent.AUTRE, 0);
		fireChangementDePage(change);
	}

	@Override
	protected void actFermer() throws Exception {
		DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(
				this, C_COMMAND_GLOBAL_FERMER_ID);
		fireDeclencheCommandeController(e);
	}

	@Override
	protected void actAnnuler() throws Exception {
		actAnnuler(false);
	}

	protected void actAnnuler(boolean annulationForcee) throws Exception {
		if (daoRAcompte.dataSetEnModif())
			actAnnulerAffectation();
		if (daoRAvoir.dataSetEnModif())
			actAnnulerAffectationAvoirs();
		// Gestion des reglements
		if (paReglementController.getDaoStandard().dataSetEnModif())
			paReglementController.actAnnuler();
		DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(
				this, C_COMMAND_GLOBAL_ANNULER_ID);
		fireDeclencheCommandeController(e);
		ChangementDePageEvent evt2 = new ChangementDePageEvent(this,
				ChangementDePageEvent.DEBUT);
		fireChangementDePage(evt2);

	}

	@Override
	protected void actImprimer() throws Exception {
		DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(
				this, C_COMMAND_GLOBAL_IMPRIMER_ID);
		fireDeclencheCommandeController(e);
	}

	@Override
	protected boolean aideDisponible() {
		boolean result = false;
		switch ((SWTPaEditorTotauxController.this.masterDAO.getModeObjet()
				.getMode())) {
		// case C_MO_CONSULTATION:
		// if (getFocusCourantSWT().equals(vue.getTfCODE_T_PAIEMENT()))
		// result = true;
		// break;
		case C_MO_CONSULTATION:
		case C_MO_EDITION:
		case C_MO_INSERTION:
			if (getFocusCourantSWT().equals(vue.getTfCODE_T_PAIEMENT())
					|| focusDansCPaiment(getFocusCourantSWT()))
				result = true;

			result = true;
			break;
		default:
			break;
		}
		return result;
	}

	@Override
	protected void actPrecedent() throws Exception {
		// Gestion des reglements
		if (!daoRAcompte.dataSetEnModif() && !daoRAvoir.dataSetEnModif()
				&& !paReglementController.getDaoStandard().dataSetEnModif()) {
			ChangementDePageEvent evt = new ChangementDePageEvent(this,
					ChangementDePageEvent.PRECEDENT);
			fireChangementDePage(evt);
		} else if (daoRAcompte.dataSetEnModif()) {
			MessageDialog
					.openInformation(vue.getShell(), "ATTENTION",
							"Voulez devez enregistrer l'affectation de l'acompte en cours.");
			throw new Exception();
		} else if (daoRAvoir.dataSetEnModif()) {
			MessageDialog
					.openInformation(vue.getShell(), "ATTENTION",
							"Voulez devez enregistrer l'affectation de l'avoir en cours.");
			throw new Exception();
		} else if (paReglementController.getDaoStandard().dataSetEnModif()) {
			MessageDialog.openInformation(vue.getShell(), "ATTENTION",
					"Voulez devez enregistrer le règlement en cours.");
			throw new Exception();
		}
	}

	@Override
	protected void actSuivant() throws Exception {
		// Gestion des reglements
		if (!daoRAcompte.dataSetEnModif() && !daoRAvoir.dataSetEnModif()
				&& !paReglementController.getDaoStandard().dataSetEnModif()) {
			ChangementDePageEvent evt = new ChangementDePageEvent(this,
					ChangementDePageEvent.SUIVANT);
			fireChangementDePage(evt);
		} else if (daoRAcompte.dataSetEnModif()) {
			MessageDialog.openInformation(vue.getShell(), "ATTENTION",
					"Voulez devez enregistrer l'affectation en cours.");
			throw new Exception();
		} else if (daoRAvoir.dataSetEnModif()) {
			MessageDialog
					.openInformation(vue.getShell(), "ATTENTION",
							"Voulez devez enregistrer l'affectation de l'avoir en cours.");
			throw new Exception();
		} else if (paReglementController.getDaoStandard().dataSetEnModif()) {
			MessageDialog.openInformation(vue.getShell(), "ATTENTION",
					"Voulez devez enregistrer le règlement en cours.");
			throw new Exception();
		}
	}

	@Override
	protected void actAide() throws Exception {
		actAide(null);
	}

	@Override
	protected void actAide(String message) throws Exception {
		// DeclencheCommandeControllerEvent e = new
		// DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_AIDE_ID);
		// fireDeclencheCommandeController(e);

		// boolean aide = getActiveAide();
		if (aideDisponible()) {
			setActiveAide(true);
			boolean verrouLocal = VerrouInterface.isVerrouille();
			VerrouInterface.setVerrouille(true);
			try {
				vue.setCursor(Display.getCurrent().getSystemCursor(
						SWT.CURSOR_WAIT));
				ParamAfficheAideRechercheSWT paramAfficheAideRecherche = new ParamAfficheAideRechercheSWT();
				// paramAfficheAideRecherche.setDb(getThis().getIbTaTable().getFIBBase());
				paramAfficheAideRecherche.setMessage(message);
				// Creation de l'ecran d'aide principal
				Shell s = new Shell(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(),
						LgrShellUtil.styleLgr);
				PaAideSWT paAide = new PaAideSWT(s, SWT.NONE);
				SWTPaAideControllerSWT paAideController = new SWTPaAideControllerSWT(
						paAide);
				/** ************************************************************ */
				LgrPartListener.getLgrPartListener().setLgrActivePart(null);
				IEditorPart e = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage().openEditor(
								new EditorInputAide(), EditorAide.ID);
				LgrPartListener.getLgrPartListener().setLgrActivePart(e);
				LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
				paAideController = new SWTPaAideControllerSWT(((EditorAide) e)
						.getComposite());
				((LgrEditorPart) e).setController(paAideController);
				((LgrEditorPart) e).setPanel(((EditorAide) e).getComposite());
				/** ************************************************************ */
				JPABaseControllerSWTStandard controllerEcranCreation = null;
				ParamAffiche parametreEcranCreation = null;
				IEditorPart editorCreation = null;
				String editorCreationId = null;
				IEditorInput editorInputCreation = null;
				Shell s2 = new Shell(s, LgrShellUtil.styleLgr);

				switch ((SWTPaEditorTotauxController.this.masterDAO
						.getModeObjet().getMode())) {
				case C_MO_CONSULTATION:
				case C_MO_EDITION:
				case C_MO_INSERTION:
					if (getFocusCourantSWT().equals(vue.getTfCODE_T_PAIEMENT())) {
						PaTypePaiementSWT paTypePaiementSWT = new PaTypePaiementSWT(
								s2, SWT.NULL);
						SWTPaTypePaiementController swtPaTypePaiementController = new SWTPaTypePaiementController(
								paTypePaiementSWT);
						editorCreationId = EditorTypePaiement.ID;
						editorInputCreation = new EditorInputTypePaiement();
						ParamAfficheTPaiement paramAfficheTPaiement = new ParamAfficheTPaiement();
						paramAfficheAideRecherche
								.setJPQLQuery(new TaTPaiementDAO(getEm())
										.getJPQLQuery());
						paramAfficheTPaiement
								.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheTPaiement
								.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaTypePaiementController;
						parametreEcranCreation = paramAfficheTPaiement;
						paramAfficheAideRecherche
								.setTypeEntite(TaTPaiement.class);
						paramAfficheAideRecherche
								.setChampsRecherche(Const.C_CODE_T_PAIEMENT);
						paramAfficheAideRecherche.setDebutRecherche("");
						paramAfficheAideRecherche
								.setControllerAppelant(getThis());
						paramAfficheAideRecherche
								.setModel(new ModelGeneralObjet<SWTTPaiement, TaTPaiementDAO, TaTPaiement>(
										SWTTPaiement.class, getEm()));
						paramAfficheAideRecherche
								.setTypeObjet(swtPaTypePaiementController
										.getClassModel());
						paramAfficheAideRecherche
								.setChampsIdentifiant(Const.C_ID_T_PAIEMENT);
						
						/*
						 * Bug #1376
						 */
						if (masterDAO.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION) == 0) {
							actModifier();
						}
					}
					if (focusDansCPaiment(getFocusCourantSWT())) {
						PaConditionPaiementSWT paCPaiementSWT = new PaConditionPaiementSWT(
								s2, SWT.NULL);
						SWTPaConditionPaiementController swtPaConditionPaiementController = new SWTPaConditionPaiementController(
								paCPaiementSWT);

						editorCreationId = EditorConditionPaiement.ID;
						editorInputCreation = new EditorInputConditionPaiement();

						paramAfficheAideRecherche
								.setForceAffichageAideRemplie(true);
						paramAfficheAideRecherche.setAfficheDetail(false);

						ParamAfficheConditionPaiement paramAfficheConditionPaiement = new ParamAfficheConditionPaiement();
						paramAfficheAideRecherche
								.setJPQLQuery(new TaCPaiementDAO(getEm())
										.getJPQLQuery());
						paramAfficheConditionPaiement
								.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheConditionPaiement
								.setEcranAppelant(paAideController);
						paramAfficheConditionPaiement.setIdTiers(masterEntity
								.getTaTiers().getIdTiers());

						controllerEcranCreation = swtPaConditionPaiementController;
						parametreEcranCreation = paramAfficheConditionPaiement;

						paramAfficheAideRecherche
								.setTypeEntite(TaCPaiement.class);
						paramAfficheAideRecherche
								.setChampsRecherche(Const.C_CODE_C_PAIEMENT);
						paramAfficheAideRecherche.setDebutRecherche("");
						paramAfficheAideRecherche
								.setControllerAppelant(getThis());
						List<TaCPaiement> l = new ArrayList<TaCPaiement>();
						// l.add(taFacture.getTaCPaiement());
						if (masterEntity.getTaTiers() != null)
							l.add(masterEntity.getTaTiers().getTaCPaiement());
						TaTCPaiementDAO taTCPaiementDAO = new TaTCPaiementDAO(
								getEm());
						if (taTCPaiementDAO
								.findByCode(TaTCPaiement.C_CODE_TYPE_FACTURE) != null
								&& taTCPaiementDAO.findByCode(
										TaTCPaiement.C_CODE_TYPE_FACTURE)
										.getTaCPaiement() != null)
							l.add(taTCPaiementDAO.findByCode(
									TaTCPaiement.C_CODE_TYPE_FACTURE)
									.getTaCPaiement());
						paramAfficheAideRecherche
								.setModel(new ModelGeneralObjet<SWTCPaiement, TaCPaiementDAO, TaCPaiement>(
										l, SWTCPaiement.class, getEm()));
						// paramAfficheAideRecherche.setModel(modelCPaiement);

						paramAfficheAideRecherche
								.setTypeObjet(swtPaConditionPaiementController
										.getClassModel());
						// paramAfficheAideRecherche.setTypeObjet(IHMCPaiement.class);
						paramAfficheAideRecherche
								.setChampsIdentifiant(Const.C_ID_C_PAIEMENT);
					}

					break;
				default:
					break;
				}

				if (paramAfficheAideRecherche.getJPQLQuery() != null) {

					PaAideRechercheSWT paAideRecherche1 = new PaAideRechercheSWT(
							s, SWT.NULL);
					SWTPaAideRechercheControllerSWT paAideRechercheController1 = new SWTPaAideRechercheControllerSWT(
							paAideRecherche1);

					// Parametrage de la recherche
					paramAfficheAideRecherche
							.setFocusSWT(((PaAideRechercheSWT) paAideRechercheController1
									.getVue()).getTfChoix());
					paramAfficheAideRecherche
							.setRefCreationSWT(controllerEcranCreation);
					paramAfficheAideRecherche.setEditorCreation(editorCreation);
					paramAfficheAideRecherche
							.setEditorCreationId(editorCreationId);
					paramAfficheAideRecherche
							.setEditorInputCreation(editorInputCreation);
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
					Control focus = vue.getShell().getDisplay()
							.getFocusControl();
					// affichage de l'ecran d'aide principal (+ ses recherches)

					dbc.getValidationStatusMap().removeMapChangeListener(
							changeListener);
					// LgrShellUtil.afficheAideSWT(paramAfficheAide, null,
					// paAide,paAideController, s);
					/*****************************************************************/
					paAideController.configPanel(paramAfficheAide);
					/*****************************************************************/
					dbc.getValidationStatusMap().addMapChangeListener(
							changeListener);
					// je rends enable false tous les boutons avant de passer
					// dans l'écran d'aide
					// pour ne pas que les actions de l'écran des factures
					// interfèrent ceux de l'écran d'aide
					// activeWorkenchPartCommands(false);
				}

			} finally {
				VerrouInterface.setVerrouille(false);
				vue.setCursor(Display.getCurrent().getSystemCursor(
						SWT.CURSOR_ARROW));
			}
		}
	}

	public IStatus validateUI() throws Exception {
		if ((masterDAO.getModeObjet().getMode().compareTo(
				ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
				|| (masterDAO.getModeObjet().getMode().compareTo(
						ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
			ctrlTousLesChampsAvantEnregistrementSWT();
			mapperUIToModelCPaiementVersInfosDoc.map(
					(IHMInfosCPaiement) selectedCPaiement, masterEntity
							.getTaInfosDocument());

			mapperUIToModel.map(((IHMTotauxDocument) selectedTotauxFacture),
					masterEntity);
		}
		return null;
	}

	public IStatus validateUIField(String nomChamp, Object value) {
		String validationContext = "FACTURE";
		try {
			IStatus s = null;
			if (nomChamp.equals(Const.C_CODE_DOCUMENT)) {
				s = new Status(Status.OK, gestComBdPlugin.PLUGIN_ID, "OK");
			} else if (nomChamp.equals("imprimer")) {
				s = new Status(Status.OK, gestComBdPlugin.PLUGIN_ID, "OK");
			} else if (nomChamp.equals(Const.C_MONTANT_AFFECTE)) {
				if (focusDansAffectation()) {
					if (nomChamp.equals(Const.C_MONTANT_AFFECTE)) {
						TaRAcompte u = new TaRAcompte();
						PropertyUtils.setSimpleProperty(u, nomChamp, value);
						s = daoRAcompte.validate(u, nomChamp, "R_ACOMPTE");
						if (s.getSeverity() != IStatus.ERROR) {
							BigDecimal affectationCourante = BigDecimal
									.valueOf(0);
							if (taRAcompte.getId() != 0)
								affectationCourante = taRAcompte
										.getAffectation();
							if (u.getAffectation().compareTo(
									taRAcompte.getTaAcompte()
											.calculResteARegler().add(
													affectationCourante)) > 0)
								taRAcompte.setAffectation(affectationCourante);
							else
								PropertyUtils.setSimpleProperty(taRAcompte,
										nomChamp, value);
							((IHMRDocument) selectedRAcompte.getValue())
									.setAffectation(taRAcompte.getAffectation());
							if (taRAcompte.getId() == 0)
								taRAcompte
										.getTaAcompte()
										.setResteARegler(
												taRAcompte
														.getTaAcompte()
														.calculResteARegler()
														.subtract(
																taRAcompte
																		.getAffectation()));
							else
								taRAcompte.getTaAcompte().setResteARegler(
										taRAcompte.getTaAcompte()
												.calculResteARegler());
							((IHMRDocument) selectedRAcompte.getValue())
									.setResteARegler(taRAcompte.getTaAcompte()
											.getResteARegler());
						}
					}
				} else if (focusDansAffectationAvoir()) {
					if (nomChamp.equals(Const.C_MONTANT_AFFECTE)) {
						TaRAvoir u = new TaRAvoir();
						PropertyUtils.setSimpleProperty(u, nomChamp, value);
						s = daoRAvoir.validate(u, nomChamp, "R_AVOIR");
						if (s.getSeverity() != IStatus.ERROR) {
							BigDecimal affectationCourante = BigDecimal
									.valueOf(0);
							if (taRAvoir.getId() != 0)
								affectationCourante = taRAvoir.getAffectation();
							if (u.getAffectation().compareTo(
									taRAvoir.getTaAvoir().calculResteARegler()
											.add(affectationCourante)) > 0)
								taRAvoir.setAffectation(affectationCourante);
							else
								PropertyUtils.setSimpleProperty(taRAvoir,
										nomChamp, value);
							((IHMRDocument) selectedRAvoir.getValue())
									.setAffectation(taRAvoir.getAffectation());
							if (taRAvoir.getId() == 0)
								taRAvoir
										.getTaAvoir()
										.setResteAReglerComplet(
												taRAvoir
														.getTaAvoir()
														.calculResteARegler()
														.subtract(
																taRAvoir
																		.getAffectation()));
							else
								taRAvoir.getTaAvoir().setResteAReglerComplet(
										taRAvoir.getTaAvoir()
												.calculResteARegler());
							((IHMRDocument) selectedRAvoir.getValue())
									.setResteARegler(taRAvoir.getTaAvoir()
											.getResteAReglerComplet());
						}
					}
				}
			} else if (nomChamp.equals(Const.C_CODE_T_PAIEMENT)) {
				Boolean change = !value.equals("");
				TaTPaiementDAO dao = new TaTPaiementDAO(getEm());
				dao.setModeObjet(getMasterDAO().getModeObjet());
				TaTPaiement f = new TaTPaiement();
				PropertyUtils.setSimpleProperty(f, nomChamp, value);
				if (masterEntity.reglementExiste()
						&& masterEntity.getTaRReglement().getTaReglement()
								.getTaTPaiement() != null)
					change = !f.getCodeTPaiement().equals(
							masterEntity.getTaRReglement().getTaReglement()
									.getTaTPaiement().getCodeTPaiement());
				s = dao.validate(f, nomChamp, "REGLEMENT");
				if (s.getSeverity() != IStatus.ERROR) {
					f = dao.findByCode((String) value);
					if (change) {
						if (masterEntity.reglementExiste()) {
							masterEntity.getTaRReglement().getTaReglement()
									.setTaTPaiement(f);
							masterEntity.getTaRReglement().getTaReglement()
							.setLibelleDocument(f.getLibTPaiement());
							((IHMTotauxDocument) selectedTotauxFacture)
							.setLibellePaiement(f.getLibTPaiement());
							masterEntity.getTaRReglement().getTaReglement()
							.setTaCompteBanque(new TaCompteBanqueDAO(getEm()).findByTiersEntreprise(f));
							if(masterEntity.getTaRReglement().getTaReglement()
									.getTaCompteBanque()!=null)	((IHMTotauxDocument) selectedTotauxFacture)
							.setIdCompteBanque(masterEntity.getTaRReglement().getTaReglement()
									.getTaCompteBanque().getIdCompteBanque());							
						}
						((IHMTotauxDocument) selectedTotauxFacture)
								.setCodeTPaiement(f.getCodeTPaiement());
						
						initialisationDesCPaiement(true);
						masterEntity.gestionReglement(masterEntity
								.getTaRReglement());
						calculDateEcheance(true);
						masterEntity.mettreAJourDateEcheanceReglements();
						actRefreshReglements(null);
					}
				}
				dao = null;
			} else {
				if (focusDansEcran() && nomChamp.equals(Const.C_REGLE_DOCUMENT)) {

					TaRReglement u = new TaRReglement();
					PropertyUtils.setSimpleProperty(u, Const.C_MONTANT_AFFECTE,
							value);
					boolean modifie=masterEntity.getRegleDocument().compareTo(u.getAffectation())!=0;					
					s = new TaRReglementDAO(getEm()).validate(u,Const.C_MONTANT_AFFECTE, "R_REGLEMENT");
					if (s.getSeverity() != IStatus.ERROR) {
						BigDecimal valeurTemp =BigDecimal.valueOf(0);
						if (u.getAffectation() != null
								&& (u.getAffectation().add(masterEntity.getAcomptes()).add(masterEntity.getAvoirs())).compareTo(masterEntity.getNetAPayer()) == 1
								&& vue.getCbAffectationStricte().getSelection()) {
							
							valeurTemp = masterEntity.getNetAPayer().subtract(masterEntity.getAcomptes()).subtract(masterEntity.getAvoirs());
							if (valeurTemp.signum() < 0) valeurTemp = valeurTemp.valueOf(0);
							masterEntity.getTaRReglement().setAffectation(valeurTemp);
							if (masterEntity.getResteAReglerComplet().signum() < 0	&& vue.getCbAffectationStricte().getSelection())
								validateUIField(Const.C_RESTE_A_REGLER,	masterEntity.getResteAReglerComplet());
						} else
							PropertyUtils.setSimpleProperty(masterEntity.getTaRReglement(),Const.C_MONTANT_AFFECTE, value);
						if (masterEntity.getTaRReglement().getAffectation() != null && modifie) {
							if ((masterEntity.getTaRReglement().getTaReglement().getTaRReglements().size() <= 1)
									&& (masterEntity.getTaRReglement().getTaReglement().getNetTtcCalc()
											.compareTo(BigDecimal.valueOf(0)) == 0|| masterEntity.getTaRReglement().getTaReglement().getNetTtcCalc().compareTo(
													masterEntity.getTaRReglement().getAffectation()) != 0))
							{
								masterEntity.getTaRReglement().getTaReglement().setNetTtcCalc(masterEntity.getTaRReglement().getAffectation());
								
							}
							if(masterEntity.getRegleCompletDocument().subtract(masterEntity.getRegleDocument()).add(u.getAffectation()).
									compareTo(masterEntity.getNetAPayer())>0){
								valeurTemp=masterEntity.getNetAPayer().subtract(masterEntity.getRegleCompletDocument());
								masterEntity.getTaRReglement().setAffectation(valeurTemp);
								validateUIField(Const.C_RESTE_A_REGLER,valeurTemp);
							}
								if (masterEntity.getTaRReglement().getAffectation().compareTo(masterEntity.getTaRReglement().getTaReglement().getNetTtcCalc()) > 0)
								masterEntity.getTaRReglement().setAffectation(masterEntity.getTaRReglement().getTaReglement().getNetTtcCalc());
							if(masterEntity.getTaRReglement().getAffectation().signum()!=0 &&  !masterEntity.multiReglement()){
								masterEntity.getTaRReglement().getTaReglement().setEtat(IHMEtat.integre);
							}
						}
						if(modifie){
							if( masterEntity.getTaRReglement()
								.getTaReglement().getTaTPaiement()==null ||
								masterEntity.getTaRReglement().getAffectation().signum()==0 )calculTypePaiement(true);
						}

						((IHMTotauxDocument) selectedTotauxFacture).setRegleDocument(masterEntity.getTaRReglement().getAffectation());
						if(masterEntity.getTaRReglement().getAffectation().signum()==0 
								&& masterEntity.getTaRReglement().getTaReglement()!=null)masterEntity.getTaRReglement().getTaReglement().setNetTtcCalc(BigDecimal.valueOf(0));
						masterEntity.gestionReglement(masterEntity.getTaRReglement());
						// masterEntity.mettreAJourDateEcheanceReglement(masterEntity.getTaRReglement().getTaReglement());
						// masterEntity.addRReglement(masterEntity.getTaRReglement());
						masterEntity.calculRegleDocument();
						actRefreshReglements(null);
					}
				} else if (nomChamp.equals(Const.C_LIBELLE_PAIEMENT)) {
					TaReglement f = new TaReglement();
					PropertyUtils
							.setSimpleProperty(f, "libelleDocument", value);
					if (masterEntity.getTaRReglement() != null
							&& masterEntity.getTaRReglement().getTaReglement() != null)
						masterEntity.getTaRReglement().getTaReglement()
								.setLibelleDocument(f.getLibelleDocument());
					s = new Status(Status.OK, gestComBdPlugin.PLUGIN_ID, "OK");
				} else if (nomChamp.equals(Const.C_DATE_ECH_DOCUMENT)) {
					boolean change = value != null && !value.equals("");
					s = new Status(Status.OK, gestComBdPlugin.PLUGIN_ID, "OK");
					if (value != null && !value.equals(""))
						change = !LibDate.dateToString(
								masterEntity.getDateEchDocument()).equals(
								LibDate.dateToString((Date) value));
					((IHMTotauxDocument) selectedTotauxFacture)
							.setDateEchDocument((Date) value);
					PropertyUtils.setSimpleProperty(masterEntity,
							Const.C_DATE_ECH_DOCUMENT, value);
					if (change) {
						masterEntity
								.mettreAJourDateEcheanceReglement(masterEntity
										.getTaRReglement().getTaReglement());
						actRefreshReglements(null);
					}
				} else if (nomChamp.equals(Const.C_CODE_C_PAIEMENT)
						|| nomChamp.equals(Const.C_LIB_C_PAIEMENT)
						|| nomChamp.equals(Const.C_FIN_MOIS_C_PAIEMENT)
						|| nomChamp.equals(Const.C_REPORT_C_PAIEMENT)) {
					TaCPaiementDAO dao = new TaCPaiementDAO(getEm());

					dao.setModeObjet(getMasterDAO().getModeObjet());
					TaCPaiement f = new TaCPaiement();
					PropertyUtils.setSimpleProperty(f, nomChamp, value);
					s = dao.validate(f, nomChamp, validationContext);

					if (s.getSeverity() != IStatus.ERROR) {
						PropertyUtils.setSimpleProperty(masterEntity
								.getTaInfosDocument(), nomChamp, value);
					}
					dao = null;
				} else {
					TaFacture u = new TaFacture(true);
					PropertyUtils.setSimpleProperty(u, nomChamp, value);
					s = masterDAO.validate(u, nomChamp, validationContext);
					if (s.getSeverity() != IStatus.ERROR) {
						PropertyUtils.setSimpleProperty(masterEntity, nomChamp,
								value);

						if (nomChamp.equals(Const.C_REM_HT_DOCUMENT)) {
							// if(u.getRemHtDocument().compareTo(masterEntity.getRemHtDocument())
							// != 0) {
							PropertyUtils.setSimpleProperty(masterEntity,
									nomChamp, value);
							if (masterEntity.getRemHtDocument().compareTo(
									masterEntity.getMtHtCalc()) > 0) {
								masterEntity.setRemHtDocument(masterEntity
										.getMtHtCalc());
							}
							if (masterEntity.getMtHtCalc().signum() == 0)
								masterEntity.setTxRemHtDocument(BigDecimal
										.valueOf(0));
							// bug #1261
							// else
							// masterEntity.setTxRemHtDocument(masterEntity.getRemHtDocument().multiply(
							// BigDecimal.valueOf(100).divide(masterEntity.getMtHtCalc(),MathContext.DECIMAL128)).setScale(2,BigDecimal.ROUND_HALF_UP));
							// validateUIField(Const.C_REGLE_DOCUMENT,
							// masterEntity.getRegleDocument());
						} else if (nomChamp.equals(Const.C_REM_TTC_DOCUMENT)) {
							// if(u.getRemTtcDocument().compareTo(
							// masterEntity.getRemTtcDocument()) != 0)
							// {//.setScale(2,BigDecimal.ROUND_HALF_UP)
							PropertyUtils.setSimpleProperty(masterEntity,
									nomChamp, value);
							if (masterEntity.getRemTtcDocument().compareTo(
									masterEntity.getMtTtcCalc()) > 0) {
								value = masterEntity.getMtTtcCalc();
								((IHMTotauxDocument) selectedTotauxFacture)
										.setRemTtcDocument(masterEntity
												.getMtTtcCalc());
								masterEntity.setRemTtcDocument(masterEntity
										.getMtTtcCalc());
							}
							if (masterEntity.getMtTtcCalc().signum() == 0)
								masterEntity.setTxRemTtcDocument(BigDecimal
										.valueOf(0));
							// bug #1261
							// else
							// masterEntity.setTxRemTtcDocument(masterEntity.getRemTtcDocument().
							// multiply(BigDecimal.valueOf(100).divide(masterEntity.getMtTtcCalc(),
							// MathContext.DECIMAL128)).setScale(2,BigDecimal.ROUND_HALF_UP));
							// validateUIField(Const.C_REGLE_DOCUMENT,
							// masterEntity.getRegleDocument());
						}
					}
					if (nomChamp.equals(Const.C_RESTE_A_REGLER)) {
						if (masterEntity.getResteAReglerEcran().signum() < 0
								&& vue.getCbAffectationStricte().getSelection())
							s = new Status(Status.ERROR,
									gestComBdPlugin.PLUGIN_ID,
									"Le reste à régler est négatif.");
					}
				}
			}
			if (s.getSeverity() != IStatus.ERROR) {
				mapperModelToUI.map(masterEntity,
						((IHMTotauxDocument) selectedTotauxFacture));
			}
			// new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
			return s;
		} catch (IllegalAccessException e) {
			logger.error("", e);
		} catch (InvocationTargetException e) {
			logger.error("", e);
		} catch (NoSuchMethodException e) {
			logger.error("", e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void actEnregistrer() throws Exception {
		try {
			rechercherModifAcompte();
			rechercherModifAvoir();
			ctrlUnChampsSWT(getFocusCourantSWT());
			mapperUIToModelCPaiementVersInfosDoc.map(
					(IHMInfosCPaiement) selectedCPaiement, masterEntity
							.getTaInfosDocument());
			// validateUI();
			// Gestion des reglements
			if (!daoRAcompte.dataSetEnModif() && !daoRAvoir.dataSetEnModif()
					&& !paReglementController.getDaoStandard().dataSetEnModif()) {
				// actEnregistrerAffectation();

				ChangementDePageEvent change = new ChangementDePageEvent(this,
						ChangementDePageEvent.AUTRE, 0);
				fireChangementDePage(change);
				DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(
						this, C_COMMAND_GLOBAL_ENREGISTRER_ID);
				fireDeclencheCommandeController(e);
			} else if (daoRAcompte.dataSetEnModif()) {
				MessageDialog
						.openInformation(
								vue.getShell(),
								"ATTENTION",
								"Voulez devez enregistrer l'affectation de l'acompte en cours avant de pouvoir enregistrer la facture en cours");
				throw new Exception();
			} else if (daoRAvoir.dataSetEnModif()) {
				MessageDialog
						.openInformation(
								vue.getShell(),
								"ATTENTION",
								"Voulez devez enregistrer l'affectation de l'avoir en cours avant de pouvoir enregistrer la facture en cours");
				throw new Exception();
			} else if (paReglementController.getDaoStandard().dataSetEnModif()) {
				MessageDialog
						.openInformation(
								vue.getShell(),
								"ATTENTION",
								"Voulez devez enregistrer le règlement en cours avant de pouvoir enregistrer la facture en cours");
				throw new Exception();
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	public IHMTotauxDocument getIhmOldEnteteFacture() {
		return ihmOldTotauxFacture;
	}

	public void setIhmOldTotauxFacture(IHMTotauxDocument ihmOldTotauxFacture) {
		this.ihmOldTotauxFacture = ihmOldTotauxFacture;
	}

	public void setIhmOldTotauxFacture() {
		if (selectedTotauxFacture != null)
			this.ihmOldTotauxFacture = IHMTotauxDocument
					.copy((IHMTotauxDocument) selectedTotauxFacture);
		else {
			if (tableViewer.selectionGrille(0)) {
				setSelectedTotauxFacture(selectedTotauxFacture);
				this.ihmOldTotauxFacture = IHMTotauxDocument
						.copy((IHMTotauxDocument) selectedTotauxFacture);
				tableViewer.setSelection(new StructuredSelection(
						(IHMTotauxDocument) selectedTotauxFacture), true);
			}
		}
	}

	public void setVue(PaTotauxSWT vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();

		mapComposantDecoratedField.put(vue.getTfCODE_T_PAIEMENT(), vue
				.getFieldCODE_T_PAIEMENT());
		mapComposantDecoratedField.put(vue.getTfMT_HT_APRES_REMISE(), vue
				.getFieldMT_HT_CALC());
		mapComposantDecoratedField.put(vue.getTfMT_TTC_APRES_REMISE(), vue
				.getFieldMT_TTC_CALC());
		mapComposantDecoratedField.put(vue.getTfMT_TVA_APRES_REMISE(), vue
				.getFieldMT_TVA_CALC());

		mapComposantDecoratedField.put(vue.getTfMT_HT_AVANT_REMISE(), vue
				.getFieldMT_HT_AVANT_REMISE());
		mapComposantDecoratedField.put(vue.getTfMT_TTC_AVANT_REMISE(), vue
				.getFieldMT_TTC_AVANT_REMISE());
		mapComposantDecoratedField.put(vue.getTfMT_TVA_AVANT_REMISE(), vue
				.getFieldMT_TVA_AVANT_REMISE());

		mapComposantDecoratedField.put(vue.getTfMT_ESCOMPTE(), vue
				.getFieldMT_ESCOMPTE());
		mapComposantDecoratedField.put(vue.getTfMT_REMISE(), vue
				.getFieldMT_REMISE());
		mapComposantDecoratedField.put(vue.getTfREGLE_FACTURE(), vue
				.getFieldREGLE_FACTURE());
		mapComposantDecoratedField.put(vue.getTfTX_REM_HT_FACTURE(), vue
				.getFieldTX_REM_HT_FACTURE());
		mapComposantDecoratedField.put(vue.getTfTX_REM_TTC_FACTURE(), vue
				.getFieldTX_REM_TTC_FACTURE());
		mapComposantDecoratedField.put(vue.getTfACOMPTES(), vue
				.getFieldACOMPTES());
		mapComposantDecoratedField.put(vue.getTfLIBELLE_PAIEMENT(), vue
				.getFieldLIBELLE_PAIEMENT());
		mapComposantDecoratedField.put(vue.getDateTimeDATE_ECH_FACTURE(), vue
				.getFieldDATE_ECH_FACTURE());
		mapComposantDecoratedField.put(vue.getTfRESTE_A_REGLER(), vue
				.getFieldRESTE_A_REGLER());
		mapComposantDecoratedField.put(vue.getTfNET_A_PAYER(), vue
				.getFieldNET_A_PAYER());
		if (affichageAcomptes()) {
			mapComposantDecoratedField.put(
					((PaAffectationAcompteSurFacture) findExpandIem(
							vue.getExpandBar(), paAffectation).getControl())
							.getTfMONTANT_AFFECTE(),
					((PaAffectationAcompteSurFacture) findExpandIem(
							vue.getExpandBar(), paAffectation).getControl())
							.getFieldMONTANT_AFFECTE());
		}
		if (affichageAvoirs()) {
			mapComposantDecoratedField.put(
					((PaAffectationAcompteSurFacture) findExpandIem(
							vue.getExpandBar(), paAffectationAvoir)
							.getControl()).getTfMONTANT_AFFECTE(),
					((PaAffectationAcompteSurFacture) findExpandIem(
							vue.getExpandBar(), paAffectationAvoir)
							.getControl()).getFieldMONTANT_AFFECTE());
		}
	}

	public Class getClassModel() {
		return classModel;
	}

	@Override
	protected void sortieChamps() {
		if(vue.getTfREGLE_FACTURE().equals(getFocusCourantSWT()))
			modifRegler();
		mapperModelToUI.map(masterEntity,
				((IHMTotauxDocument) selectedTotauxFacture));
	}

	@Override
	protected void actRefresh() throws Exception {

		if (masterDAO.getModeObjet().getMode().compareTo(
				ModeObjet.EnumModeObjet.C_MO_CONSULTATION) != 0) {
			if (masterEntity != null && selectedTotauxFacture != null
					&& (IHMTotauxDocument) selectedTotauxFacture != null) {
				mapperModelToUI.map(masterEntity,
						(IHMTotauxDocument) selectedTotauxFacture);
			}
		}

		DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(
				this, C_COMMAND_GLOBAL_REFRESH_ID);
		fireDeclencheCommandeController(e);
	}

	private ListenerList selectionChangedListeners = new ListenerList(3);

	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		selectionChangedListeners.add(listener);
	}

	public ISelection getSelection() {
		return null;
	}

	// ISelectionProvider
	protected void fireSelectionChanged(final SelectionChangedEvent event) {
		Object[] listeners = selectionChangedListeners.getListeners();
		for (int i = 0; i < listeners.length; ++i) {
			final ISelectionChangedListener l = (ISelectionChangedListener) listeners[i];
			SafeRunnable.run(new SafeRunnable() {
				public void run() {
					l.selectionChanged(event);
				}
			});
		}
	}

	public void removeSelectionChangedListener(
			ISelectionChangedListener listener) {
		selectionChangedListeners.remove(listener);
	}

	protected void updateSelection(ISelection selection) {
		SelectionChangedEvent event = new SelectionChangedEvent(this, selection);
		fireSelectionChanged(event);
	}

	public void setSelection(ISelection selection) {
		// TODO Auto-generated method stub
	}

	public void modificationDocument(SWTModificationDocumentEvent evt) {
		BigDecimal montantHT = new BigDecimal(0);
		BigDecimal montantTVA = new BigDecimal(0);
		BigDecimal montantTTC = new BigDecimal(0);

		dataTVA = new String[masterEntity.getLignesTVA().size()][3];

		for (LigneTva ligneTVA : masterEntity.getLignesTVA()) {
			montantTVA.add(LibCalcul.arrondi(ligneTVA.getMtTva()));
		}

		writableList = new WritableList(realm, modelLignesTVA
				.remplirListe(masterEntity), IHMLignesTVA.class);
		tableViewer.setInput(writableList);

		// CALCUL_TOTAL_TEMP
		BigDecimal NetHT = new BigDecimal(0);
		BigDecimal NetTVA = new BigDecimal(0);
		BigDecimal NetTTC = new BigDecimal(0);

		try {
			((IHMTotauxDocument) selectedTotauxFacture).setMtHtCalc(LibCalcul
					.arrondi(montantHT));
			((IHMTotauxDocument) selectedTotauxFacture).setMtTtcCalc(LibCalcul
					.arrondi(montantTTC));
			((IHMTotauxDocument) selectedTotauxFacture).setMtTvaCalc(LibCalcul
					.arrondi(montantTVA));
			((IHMTotauxDocument) selectedTotauxFacture).setNetHtCalc(LibCalcul
					.arrondi(NetHT));
			((IHMTotauxDocument) selectedTotauxFacture).setNetTtcCalc(LibCalcul
					.arrondi(NetTTC));
			((IHMTotauxDocument) selectedTotauxFacture).setNetTvaCalc(LibCalcul
					.arrondi(NetTVA));

			if (((IHMTotauxDocument) selectedTotauxFacture)
					.getTxRemTtcDocument() == null)
				((IHMTotauxDocument) selectedTotauxFacture)
						.setTxRemTtcDocument(new BigDecimal(0));

			if (((IHMTotauxDocument) selectedTotauxFacture).getRegleDocument() == null)
				((IHMTotauxDocument) selectedTotauxFacture)
						.setRegleDocument(new BigDecimal(0));

			if (((IHMTotauxDocument) selectedTotauxFacture).getRegleDocument()
					.compareTo(
							((IHMTotauxDocument) selectedTotauxFacture)
									.getNetTtcCalc()) > 0
					&& ((IHMTotauxDocument) selectedTotauxFacture)
							.getNetTtcCalc().compareTo(new BigDecimal(0)) > 0)
				((IHMTotauxDocument) selectedTotauxFacture)
						.setRegleDocument(((IHMTotauxDocument) selectedTotauxFacture)
								.getNetTtcCalc());
			else if (((IHMTotauxDocument) selectedTotauxFacture)
					.getNetTtcCalc().signum() < 0)
				((IHMTotauxDocument) selectedTotauxFacture)
						.setRegleDocument(new BigDecimal(0));

			if (((IHMTotauxDocument) selectedTotauxFacture)
					.getTxRemHtDocument() == null)
				((IHMTotauxDocument) selectedTotauxFacture)
						.setTxRemHtDocument(new BigDecimal(0));

			if (masterEntity.getRemHtDocument().compareTo(
					masterEntity.getMtHtCalc()) > 0) {
				masterEntity.setRemHtDocument(masterEntity.getMtHtCalc());
			}
			if (masterEntity.getMtHtCalc().signum() == 0)
				masterEntity.setTxRemHtDocument(BigDecimal.valueOf(0));
			// bug #1261
			// else
			// masterEntity.setTxRemHtDocument(masterEntity.getRemHtDocument().multiply(
			// BigDecimal.valueOf(100).divide(masterEntity.getMtHtCalc(),MathContext.DECIMAL128)).setScale(2,BigDecimal.ROUND_HALF_UP));

			if (masterEntity.getRemTtcDocument().compareTo(
					masterEntity.getMtTtcCalc()) > 0) {
				((IHMTotauxDocument) selectedTotauxFacture)
						.setRemTtcDocument(masterEntity.getMtTtcCalc());
				masterEntity.setRemTtcDocument(masterEntity.getMtTtcCalc());
			}
			if (masterEntity.getMtTtcCalc().signum() == 0)
				masterEntity.setTxRemTtcDocument(BigDecimal.valueOf(0));
			// bug #1261
			// else
			// masterEntity.setTxRemTtcDocument(masterEntity.getRemTtcDocument().
			// multiply(BigDecimal.valueOf(100).divide(masterEntity.getMtTtcCalc(),
			// MathContext.DECIMAL128)).setScale(2,BigDecimal.ROUND_HALF_UP));

			refreshVue();
			// masterEntity.calculeTvaEtTotaux();
			mapperModelToUI.map(masterEntity,
					((IHMTotauxDocument) selectedTotauxFacture));

			// if(masterEntity.getResteARegler().signum()<0){
			// registry.registerFieldDecoration("avertissement.ResteARegler",
			// "Attention, le reste à régler est négatif !!!",
			// FacturePlugin.getImageDescriptor("/icons/lightbulb.png").createImage());
			// vue.getFieldRESTE_A_REGLER().addFieldDecoration(registry.getFieldDecoration("avertissement.ResteARegler"),
			// SWT.RIGHT | SWT.TOP, false);
			// vue.getFieldRESTE_A_REGLER().getLayoutControl().setLayoutData(
			// new GridData(
			// 100 + registry.getMaximumDecorationWidth(),
			// SWT.DEFAULT
			// )
			// );
			// }
			// else{
			// if(registry.getFieldDecoration("avertissement.ResteARegler")!=null){
			// vue.getFieldRESTE_A_REGLER().hideDecoration(registry.getFieldDecoration("avertissement.ResteARegler"));
			// }
			// }
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public void calculTypePaiement(boolean recharger) {
		if (masterEntity != null) {
			if (masterEntity.getTaTiers() != null
					&& masterEntity.getTaTiers().getTaTPaiement() != null
					&& masterEntity.getTaRReglement() != null
					&& masterEntity.getTaRReglement().getTaReglement() != null &&masterEntity.getTaRReglement().
					getTaReglement().getNetTtcCalc().signum()!=0 ) {
				masterEntity.getTaRReglement().getTaReglement().setTaTPaiement(
						masterEntity.getTaTiers().getTaTPaiement());
				masterEntity.getTaRReglement().getTaReglement()
				.setLibelleDocument(masterEntity.getTaTiers().getTaTPaiement().getLibTPaiement());
			} else {
				typePaiementDefaut = DocumentPlugin.getDefault().getPreferenceStore().getString(
								fr.legrain.document.preferences.PreferenceConstants.TYPE_PAIEMENT_DEFAUT);
				if (typePaiementDefaut == null || typePaiementDefaut=="")
					typePaiementDefaut="C";
				TaTPaiementDAO taTPaiementDAO = new TaTPaiementDAO(getEm());
				TaTPaiement taTPaiement = null;
				try {
					taTPaiement = taTPaiementDAO
							.findByCode(typePaiementDefaut);

				} catch (Exception e) {
				}

				if(masterEntity.reglementExiste()&&masterEntity.getTaRReglement().
						getTaReglement().getNetTtcCalc().signum()!=0){
					masterEntity.getTaRReglement().getTaReglement()
					.setTaTPaiement(taTPaiement);
					masterEntity.getTaRReglement().getTaReglement()
					.setLibelleDocument(taTPaiement.getLibTPaiement());
					validateUIField(Const.C_CODE_T_PAIEMENT, taTPaiement.getCodeTPaiement());
				}else{
					if(masterEntity.getTaRReglement()!=null && masterEntity.getTaRReglement().getTaReglement()!=null){
						masterEntity.getTaRReglement().getTaReglement().setTaTPaiement(null);
						masterEntity.getTaRReglement().getTaReglement().setLibelleDocument(null);
					}
				}


			}
		}
		initialisationDesCPaiement(recharger);
	}

	public void changementMode(ChangeModeEvent evt) {
		try {
			if (!masterDAO.dataSetEnModif()) {
				if (!masterEntity.getOldCODE().equals(""))
					masterDAO.modifier(masterEntity);
				else
					actInserer();
				initEtatBouton(false);
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/**
	 * Active l'ecoute de tous les champs du controller qui sont relie a la bdd,
	 * si le dataset n'est pas en modification et qu'un des champs est modifie,
	 * le dataset passera automatiquement en edition.
	 * 
	 * @see #desactiveModifyListener
	 */
	public void activeModifytListener() {
		System.out.println("active");
		// #AFAIRE
		if (masterDAO != null) {
			activeModifytListener(mapComposantChamps, masterDAO);
			activeModifytListener(mapComposantChampsCommentaire, masterDAO);
		}
		if (daoRAcompte != null) {
			activeModifytListener(mapComposantChampsAffectation, daoRAcompte,
					new LgrModifyListenerAffectation());
		}
		if (daoRAvoir != null) {
			activeModifytListener(mapComposantChampsAffectationAvoirs,
					daoRAvoir, new LgrModifyListenerAffectationAvoir());
		}
		activeModifytListener(mapComposantChampsCPaiement, daoCPaiement);
		// if(daoReglement!=null){
		// activeModifytListener(mapComposantChampsReglement, daoReglement,new
		// LgrModifyListenerReglement());
		// }
	}

	/**
	 * Active l'ecoute de tous les champs du controller qui sont relies a la
	 * bdd, si le dataset n'est pas en modification et qu'un des champs est
	 * modifie, le dataset passera automatiquement en edition.
	 * 
	 * @see #desactiveModifyListener
	 */
	public void activeModifytListener(Map<Control, String> mapComposantChamps,
			AbstractLgrDAO daoStandard, LgrModifyListener lgrModifyListener) {
		for (Control control : mapComposantChamps.keySet()) {
			if (control instanceof Text) {
				((Text) control).addModifyListener(lgrModifyListener);
			} else if (control instanceof Button) {
				((Button) control).addSelectionListener(lgrModifyListener);
			} else if (control instanceof DateTime) {
				// ((cdatetimeLgr)control).addModifyListener(lgrModifyListener);
				// ((CDateTime)control).addSelectionListener(lgrModifyListener);
			}
		}
		initMaxLenthTextComponent(mapComposantChamps, daoStandard);
		initMajTextComponent(mapComposantChamps, daoStandard);
	}

	public void modifModeAffectation() {
		if (!VerrouInterface.isVerrouille()) {
			try {
				if (!daoRAcompte.dataSetEnModif()) {
					if (!(modelRAcompte.getListeObjet().size() == 0)) {
						actModifierAffectation();
					} else {
						actInsererAffectation();
					}
					initEtatBoutonAffectation();
				}
			} catch (Exception e1) {
				logger.error("", e1);
			}
		}
	}

	public void modifModeAffectationAvoir() {
		if (!VerrouInterface.isVerrouille()) {
			try {
				if (!daoRAvoir.dataSetEnModif()) {
					if (!(modelRAvoir.getListeObjet().size() == 0)) {
						actModifierAffectationAvoirs();
					} else {
						actInsererAffectationAvoir();
					}
					initEtatBoutonAffectationAvoir();
				}
			} catch (Exception e1) {
				logger.error("", e1);
			}
		}
	}

	// public void modifModeReglement(){
	// if (!VerrouInterface.isVerrouille() ){
	// try {
	// if(!daoReglement.dataSetEnModif()) {
	// if(!(modelReglement.getListeObjet().size()==0)) {
	// actModifierReglement();
	// } else {
	// actInsererReglement();
	// }
	// initEtatBoutonReglement();
	// }
	// } catch (Exception e1) {
	// logger.error("",e1);
	// }
	// }
	// }
	public void setSwtOldRAcompte() {

		if (selectedRAcompte.getValue() != null)
			this.swtOldRAcompte = IHMRDocument
					.copy((IHMRDocument) selectedRAcompte.getValue());
		else {
			if (tableViewerAffectation.selectionGrille(0)) {
				this.swtOldRAcompte = IHMRDocument
						.copy((IHMRDocument) selectedRAcompte.getValue());
				tableViewerAffectation.setSelection(new StructuredSelection(
						(IHMRDocument) selectedRAcompte.getValue()), true);
			}
		}
	}

	public void setSwtOldRAvoir() {

		if (selectedRAvoir.getValue() != null)
			this.swtOldRAvoir = IHMRDocument.copy((IHMRDocument) selectedRAvoir
					.getValue());
		else {
			if (tableViewerAffectationAvoirs.selectionGrille(0)) {
				this.swtOldRAvoir = IHMRDocument
						.copy((IHMRDocument) selectedRAvoir.getValue());
				tableViewerAffectationAvoirs.setSelection(
						new StructuredSelection((IHMRDocument) selectedRAvoir
								.getValue()), true);
			}
		}
	}

	// gestion de la modification dans l'expand d'affectation
	private void actModifierAffectation() throws Exception {
		try {
			DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(
					this, C_COMMAND_GLOBAL_MODIFIER_ID);
			fireDeclencheCommandeController(e);
			if (masterDAO.dataSetEnModif()) {
				if (daoRAcompte.getModeObjet().getMode().compareTo(
						ModeObjet.EnumModeObjet.C_MO_CONSULTATION) == 0) {
					setSwtOldRAcompte();
					for (TaRAcompte p : masterEntity.getTaRAcomptes()) {
						if (p.getTaAcompte() != null
								&& p.getTaAcompte().getCodeDocument().equals(
										((IHMRDocument) selectedRAcompte
												.getValue()).getCodeDocument()))
							taRAcompte = p;
					}

					// taRAcompte = daoRAcompte.findById(((IHMRAcompte)
					// selectedRAcompte.getValue()).getIdDocument());

					daoRAcompte.modifier(taRAcompte);
					initEtatBoutonAffectation();
				}
			}
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		}
	}

	// gestion de la modification dans l'expand d'affectation
	private void actModifierAffectationAvoirs() throws Exception {
		try {
			DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(
					this, C_COMMAND_GLOBAL_MODIFIER_ID);
			fireDeclencheCommandeController(e);
			if (masterDAO.dataSetEnModif()) {
				if (daoRAvoir.getModeObjet().getMode().compareTo(
						ModeObjet.EnumModeObjet.C_MO_CONSULTATION) == 0) {
					setSwtOldRAvoir();
					for (TaRAvoir p : masterEntity.getTaRAvoirs()) {
						if (p.getTaAvoir() != null
								&& p.getTaAvoir().getCodeDocument().equals(
										((IHMRDocument) selectedRAvoir
												.getValue()).getCodeDocument()))
							taRAvoir = p;
					}
					daoRAvoir.modifier(taRAvoir);
					initEtatBoutonAffectationAvoir();
				}
			}
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		}
	}

	protected void initEtatBoutonAffectation() {
		if (affichageAcomptes()) {
			boolean trouve = modelRAcompte.getListeObjet().size() > 0;
			// mapCommand.put(C_COMMAND_DOCUMENT_ENREGISTRER_AFFECTATION_ID,
			// handlerEnregistrerAffectation);
			// mapCommand.put(C_COMMAND_DOCUMENT_INSERER_AFFECTATION_ID,
			// handlerInsererAffectation);
			// mapCommand.put(C_COMMAND_DOCUMENT_SUPPRIMER_AFFECTATION_ID,
			// handlerSupprimerAffectation);

			switch (daoRAcompte.getModeObjet().getMode()) {
			case C_MO_INSERTION:
				enableActionAndHandler(
						C_COMMAND_DOCUMENT_INSERER_AFFECTATION_ID, false);
				enableActionAndHandler(
						C_COMMAND_DOCUMENT_ENREGISTRER_AFFECTATION_ID, true);
				enableActionAndHandler(
						C_COMMAND_DOCUMENT_SUPPRIMER_AFFECTATION_ID, trouve);
				enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID, true);
				enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID, false);
				if (((PaAffectationAcompteSurFacture) findExpandIem(
						vue.getExpandBar(), paAffectation).getControl())
						.getGrilleDest() != null)
					((PaAffectationAcompteSurFacture) findExpandIem(
							vue.getExpandBar(), paAffectation).getControl())
							.getGrilleDest().setEnabled(false);
				break;
			case C_MO_EDITION:
				enableActionAndHandler(
						C_COMMAND_DOCUMENT_INSERER_AFFECTATION_ID, false);
				enableActionAndHandler(
						C_COMMAND_DOCUMENT_ENREGISTRER_AFFECTATION_ID, true);
				enableActionAndHandler(
						C_COMMAND_DOCUMENT_SUPPRIMER_AFFECTATION_ID, trouve);
				enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID, true);
				enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID, true);
				if (((PaAffectationAcompteSurFacture) findExpandIem(
						vue.getExpandBar(), paAffectation).getControl())
						.getGrilleDest() != null)
					((PaAffectationAcompteSurFacture) findExpandIem(
							vue.getExpandBar(), paAffectation).getControl())
							.getGrilleDest().setEnabled(false);
				break;
			case C_MO_CONSULTATION:
				enableActionAndHandler(
						C_COMMAND_DOCUMENT_INSERER_AFFECTATION_ID, true);
				enableActionAndHandler(
						C_COMMAND_DOCUMENT_ENREGISTRER_AFFECTATION_ID, false);
				enableActionAndHandler(
						C_COMMAND_DOCUMENT_SUPPRIMER_AFFECTATION_ID, trouve);
				enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID, true);
				enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID, true);
				if (((PaAffectationAcompteSurFacture) findExpandIem(
						vue.getExpandBar(), paAffectation).getControl())
						.getGrilleDest() != null)
					((PaAffectationAcompteSurFacture) findExpandIem(
							vue.getExpandBar(), paAffectation).getControl())
							.getGrilleDest().setEnabled(true);
				break;
			default:
				break;
			}
		}
	}

	protected void initEtatBoutonAffectationAvoir() {
		if (affichageAvoirs()) {
			boolean trouve = modelRAvoir.getListeObjet().size() > 0;
			// mapCommand.put(C_COMMAND_DOCUMENT_ENREGISTRER_AFFECTATION_AVOIR_ID,
			// handlerEnregistrerAffectation);
			// mapCommand.put(C_COMMAND_DOCUMENT_INSERER_AFFECTATION_AVOIR_ID,
			// handlerInsererAffectation);
			// mapCommand.put(C_COMMAND_DOCUMENT_SUPPRIMER_AFFECTATION_AVOIR_ID,
			// handlerSupprimerAffectation);

			switch (daoRAvoir.getModeObjet().getMode()) {
			case C_MO_INSERTION:
				enableActionAndHandler(
						C_COMMAND_DOCUMENT_INSERER_AFFECTATION_AVOIR_ID, false);
				enableActionAndHandler(
						C_COMMAND_DOCUMENT_ENREGISTRER_AFFECTATION_AVOIR_ID,
						true);
				enableActionAndHandler(
						C_COMMAND_DOCUMENT_SUPPRIMER_AFFECTATION_AVOIR_ID,
						trouve);
				enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID, true);
				enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID, false);
				if (((PaAffectationAcompteSurFacture) findExpandIem(
						vue.getExpandBar(), paAffectationAvoir).getControl())
						.getGrilleDest() != null)
					((PaAffectationAcompteSurFacture) findExpandIem(
							vue.getExpandBar(), paAffectationAvoir)
							.getControl()).getGrilleDest().setEnabled(false);
				break;
			case C_MO_EDITION:
				enableActionAndHandler(
						C_COMMAND_DOCUMENT_INSERER_AFFECTATION_AVOIR_ID, false);
				enableActionAndHandler(
						C_COMMAND_DOCUMENT_ENREGISTRER_AFFECTATION_AVOIR_ID,
						true);
				enableActionAndHandler(
						C_COMMAND_DOCUMENT_SUPPRIMER_AFFECTATION_AVOIR_ID,
						trouve);
				enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID, true);
				enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID, true);
				if (((PaAffectationAcompteSurFacture) findExpandIem(
						vue.getExpandBar(), paAffectationAvoir).getControl())
						.getGrilleDest() != null)
					((PaAffectationAcompteSurFacture) findExpandIem(
							vue.getExpandBar(), paAffectationAvoir)
							.getControl()).getGrilleDest().setEnabled(false);
				break;
			case C_MO_CONSULTATION:
				enableActionAndHandler(
						C_COMMAND_DOCUMENT_INSERER_AFFECTATION_AVOIR_ID, true);
				enableActionAndHandler(
						C_COMMAND_DOCUMENT_ENREGISTRER_AFFECTATION_AVOIR_ID,
						false);
				enableActionAndHandler(
						C_COMMAND_DOCUMENT_SUPPRIMER_AFFECTATION_AVOIR_ID,
						trouve);
				enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID, true);
				enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID, true);
				if (((PaAffectationAcompteSurFacture) findExpandIem(
						vue.getExpandBar(), paAffectationAvoir).getControl())
						.getGrilleDest() != null)
					((PaAffectationAcompteSurFacture) findExpandIem(
							vue.getExpandBar(), paAffectationAvoir)
							.getControl()).getGrilleDest().setEnabled(true);
				break;
			default:
				break;
			}
		}
	}

	private class LgrModifyListenerAffectation extends LgrModifyListener {

		public void modifyText(ModifyEvent e) {
			modifModeAffectation();
		}

		public void widgetDefaultSelected(SelectionEvent e) {
			modifModeAffectation();
		}

		public void widgetSelected(SelectionEvent e) {
			modifModeAffectation();
		}

	}

	private class LgrModifyListenerAffectationAvoir extends LgrModifyListener {

		public void modifyText(ModifyEvent e) {
			modifModeAffectationAvoir();
		}

		public void widgetDefaultSelected(SelectionEvent e) {
			modifModeAffectationAvoir();
		}

		public void widgetSelected(SelectionEvent e) {
			modifModeAffectationAvoir();
		}

	}

	/**
	 * Initialisation des boutons suivant l'etat de l'objet "ibTaTable"
	 * 
	 * @param initFocus
	 *            - si vrai initialise le focus en fonction du mode
	 */
	protected void initEtatBouton(boolean initFocus) {
		super.initEtatBoutonCommand(initFocus, modelTotaux.getListeObjet());
		enableActionAndHandler(C_COMMAND_GLOBAL_PRECEDENT_ID, true);
		enableActionAndHandler(C_COMMAND_GLOBAL_SUIVANT_ID, false);
		// enableActionAndHandler(C_COMMAND_DOCUMENT_REINIT_CPAIEMENT_ID, true);
	}

	/**
	 * Positionnement du focus en fonction du mode de l'écran
	 * 
	 * @param ibTaTable
	 *            - Ensemble de données utilisé dans l'écran du controller
	 * @param focus
	 *            - [clé : mode] [valeur : composant qui à le focus par défaut
	 *            pour ce mode]
	 */
	protected void initFocusSWT(IBQuLgr ibTaTable,
			Map<ModeObjet.EnumModeObjet, Control> focus) {
		setFocusCourantSWT(vue.getTfTX_REM_HT_FACTURE());
		getFocusCourantSWT().forceFocus();
	}

	@Override
	protected void initImageBouton() {
		vue.getPaBtnAvecAssistant().getPaBtn().getBtnAnnuler().setImage(
				LibrairiesEcranPlugin.ir
						.get(LibrairiesEcranPlugin.IMAGE_ANNULER));
		vue.getPaBtnAvecAssistant().getPaBtn().getBtnEnregistrer().setImage(
				LibrairiesEcranPlugin.ir
						.get(LibrairiesEcranPlugin.IMAGE_ENREGISTRER));
		vue.getPaBtnAvecAssistant().getPaBtn().getBtnFermer().setImage(
				LibrairiesEcranPlugin.ir
						.get(LibrairiesEcranPlugin.IMAGE_FERMER));
		vue.getPaBtnAvecAssistant().getPaBtn().getBtnInserer().setImage(
				LibrairiesEcranPlugin.ir
						.get(LibrairiesEcranPlugin.IMAGE_INSERER));
		vue.getPaBtnAvecAssistant().getPaBtn().getBtnModifier().setImage(
				LibrairiesEcranPlugin.ir
						.get(LibrairiesEcranPlugin.IMAGE_MODIFIER));
		vue.getPaBtnAvecAssistant().getPaBtn().getBtnSupprimer().setImage(
				LibrairiesEcranPlugin.ir
						.get(LibrairiesEcranPlugin.IMAGE_SUPPRIMER));
		vue.getPaBtnAvecAssistant().getPaBtn().getBtnImprimer().setImage(
				LibrairiesEcranPlugin.ir
						.get(LibrairiesEcranPlugin.IMAGE_IMPRIMER));
		vue.getBtnOuvreRepertoireCourrier().setImage(
				LibrairiesEcranPlugin.ir
						.get(LibrairiesEcranPlugin.IMAGE_REPERTOIRE));
		vue.getBtnOuvreRepertoireCourrier().setToolTipText(
				"Ouvrir le répertoire courrier du tiers");
//		vue.getBtnEmail().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_EMAIL));
		vue.layout(true);
	}

	public Boolean remonterDocument() throws Exception {
		try {
			boolean res = false;
			// nouvelle facture (mode, code existant "CodeFacture")
			desactiveModifyListener();
			initialisationDesCPaiement(false);
			masterEntity.setTaRReglement(new TaRReglement());
			masterEntity.affecteReglementFacture(typePaiementDefaut);
			initSelectedObjectFromPreferences();
			res = true;
			modifRegler();
			return res;
		} finally {
			activeModifytListener();
			initEtatBouton(true);
		}

	}

	public void initialisationEcran() {
		try {
			actAnnulerAffectation();
			actAnnulerAffectationAvoirs();
			// Gestion des reglements
			paReglementController.actAnnuler();
		} catch (Exception e) {
			logger.error("", e);
		}
		initSelectedObjectFromPreferences();
	}

	private void initSelectedObjectFromPreferences() {
		((IHMTotauxDocument) selectedTotauxFacture).setImprimer(FacturePlugin
				.getDefault().getPreferenceStore().getBoolean(
						PreferenceConstants.IMPRIMER_AUTO));
	}

	// public void setIbTaTable(SWT_IB_TA_FACTURE ibTaTable) {
	// this.ibTaTable = ibTaTable;
	// setIbTaTableStandard(ibTaTable);
	// }

	public void setTaFacture(TaFacture masterEntity) {
		this.masterEntity = masterEntity;
		this.masterEntity.addModificationDocumentListener(this);
	}

	public Object getSelectedTotauxFacture() {
		return selectedTotauxFacture;
	}

	public void setSelectedTotauxFacture(Object selectedTotauxFacture) {
		this.selectedTotauxFacture = selectedTotauxFacture;
		((ModelObject) selectedTotauxFacture)
				.addPropertyChangeListener(new TotauxPropertyChangeListener());
	}

	private class TotauxPropertyChangeListener implements
			PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getPropertyName().equals(Const.C_MT_TTC_CALC))
				masterEntity.setMtTtcCalc((BigDecimal) evt.getNewValue());
			else if (evt.getPropertyName().equals(Const.C_MT_HT_CALC))
				masterEntity.setMtHtCalc((BigDecimal) evt.getNewValue());
			else if (evt.getPropertyName().equals(Const.C_MT_TVA_CALC))
				masterEntity.setMtTvaCalc((BigDecimal) evt.getNewValue());
			else if (evt.getPropertyName().equals(Const.C_NET_TTC_CALC))
				masterEntity.setNetTtcCalc((BigDecimal) evt.getNewValue());
			else if (evt.getPropertyName().equals(Const.C_NET_HT_CALC))
				masterEntity.setNetHtCalc((BigDecimal) evt.getNewValue());
			else if (evt.getPropertyName().equals(Const.C_NET_TVA_CALC))
				masterEntity.setNetTvaCalc((BigDecimal) evt.getNewValue());
			else if (evt.getPropertyName().equals(Const.C_TX_REM_TTC_DOCUMENT))
				masterEntity
						.setTxRemTtcDocument((BigDecimal) evt.getNewValue());
			else if (evt.getPropertyName().equals(Const.C_REM_TTC_DOCUMENT))
				masterEntity.setRemTtcDocument((BigDecimal) evt.getNewValue());
			else if (evt.getPropertyName().equals(Const.C_TX_REM_HT_DOCUMENT))
				masterEntity.setTxRemHtDocument((BigDecimal) evt.getNewValue());
			else if (evt.getPropertyName().equals(Const.C_REM_HT_DOCUMENT))
				masterEntity.setRemHtDocument((BigDecimal) evt.getNewValue());
			// #AFAIRE
			// else if(evt.getPropertyName().equals("ID_C_PAIEMENT"))
			// masterEntity.getSwtCPaiement().setIdCPaiement((Integer)evt.getNewValue());
			// else if(evt.getPropertyName().equals("CODE_T_PAIEMENT"))
			// masterEntity.getSwtTPaiement().setCODE_T_PAIEMENT((String)evt.getNewValue());
			// else if(evt.getPropertyName().equals("ID_T_PAIEMENT"))
			// masterEntity.getSwtTPaiement().setID_T_PAIEMENT((Integer)evt.getNewValue());
		}

	}

	public void initVue() {
		paInfosCondPaiement = new PaInfosCondPaiement(vue
				.getExpandBarCondition(), SWT.PUSH);
		addExpandBarItem(vue.getExpandBarCondition(), paInfosCondPaiement,
				"Conditions de paiement", LibrairiesEcranPlugin.ir
						.get(LibrairiesEcranPlugin.IMAGE_CONDITION_PAIEMENT));

		paAffectationReglement = new PaAffectationReglementSurFacture(vue
				.getExpandBar(), SWT.PUSH);
		paAffectationReglement.getLaTitreFormulaire().setText("Règlement");
		setPaReglementController(new PaReglementController(
				paAffectationReglement, getEm()));
		getPaReglementController().setIntegrer(true);
		getPaReglementController().setAfficheListeReglements(true);
		getPaReglementController().setEnregistrementDirect(false);

		addExpandBarItem(vue.getExpandBar(), paAffectationReglement,
				titreReglements, FacturePlugin.getImageDescriptor(
						"/icons/user.png").createImage(), SWT.DEFAULT, 350);
		findExpandIem(vue.getExpandBar(), paAffectationReglement).setExpanded(
				false);
		paAffectationReglement.getCompositeForm().setWeights(new int[]{35,65});

		paCommentaire = new PaCommentaire(vue.getExpandBar(), SWT.PUSH);
		addExpandBarItem(vue.getExpandBar(), paCommentaire, "Commentaires",
				FacturePlugin.getImageDescriptor("/icons/creditcards.png")
						.createImage());
		findExpandIem(vue.getExpandBar(), paCommentaire)
				.setExpanded(
						DocumentPlugin
								.getDefault()
								.getPreferenceStore()
								.getBoolean(
										fr.legrain.document.preferences.PreferenceConstants.AFF_COMMENTAIRE));
		// à enlever sur prochaine version, car on ne doit pas pouvoir régler
		// plus que le montant de la facture
		vue.getCbAffectationStricte().setVisible(false);
		vue.getCbAffectationStricte().setSelection(true);

		if (affichageAcomptes()) {
			paAffectation = new PaAffectationAcompteSurFacture(vue
					.getExpandBar(), SWT.PUSH);
			addExpandBarItem(vue.getExpandBar(), paAffectation,
					"Affectation des acomptes", FacturePlugin
							.getImageDescriptor("/icons/user.png")
							.createImage(), SWT.DEFAULT, 200);
			findExpandIem(vue.getExpandBar(), paAffectation).setExpanded(
					FacturePlugin.getDefault().getPreferenceStore().getBoolean(
							PreferenceConstants.AFF_AFFECTATION_ACOMPTE));
		} else {
			Composite paAffectation = new Composite(vue.getExpandBar(),
					SWT.PUSH);
			addExpandBarItem(
					vue.getExpandBar(),
					paAffectation,
					"Affectation des acomptes (module proposé dans la version supérieure)",
					FacturePlugin.getImageDescriptor("/icons/user.png")
							.createImage(), SWT.DEFAULT, 200);
			findExpandIem(vue.getExpandBar(), paAffectation).setExpanded(false);
		}

		if (affichageAvoirs()) {
			paAffectationAvoir = new PaAffectationAcompteSurFacture(vue
					.getExpandBar(), SWT.PUSH);
			addExpandBarItem(vue.getExpandBar(), paAffectationAvoir,
					"Affectation des avoirs", FacturePlugin.getImageDescriptor(
							"/icons/user.png").createImage(), SWT.DEFAULT, 200);
			findExpandIem(vue.getExpandBar(), paAffectationAvoir).setExpanded(
					FacturePlugin.getDefault().getPreferenceStore().getBoolean(
							PreferenceConstants.AFF_AFFECTATION_AVOIR));
		} else {
			Composite paAffectation = new Composite(vue.getExpandBar(),
					SWT.PUSH);
			addExpandBarItem(
					vue.getExpandBar(),
					paAffectation,
					"Affectation des avoirs (module proposé dans la version supérieure)",
					FacturePlugin.getImageDescriptor("/icons/user.png")
							.createImage(), SWT.DEFAULT, 200);
			findExpandIem(vue.getExpandBar(), paAffectation).setExpanded(false);
		}

		findExpandIem(vue.getExpandBarCondition(), paInfosCondPaiement)
				.setExpanded(
						DocumentPlugin
								.getDefault()
								.getPreferenceStore()
								.getBoolean(
										fr.legrain.document.preferences.PreferenceConstants.AFF_CPAIEMENT));

		// init combo liste param etiquette
		IPreferenceStore store = FacturePlugin.getDefault()
				.getPreferenceStore();

		WizardModelLables wizardModelLables = new WizardModelLables(
				new GenerationFileEtiquette());
		String[] listeParamEtiquette = wizardModelLables
				.listeParamEtiquette(
						new String[] { HeadlessEtiquette.CHOIX_AUCUN_CCOMB_PARAM_ETIQUETTE },
						new String[] { HeadlessEtiquette.CHOIX_DEFAUT_CCOMB_PARAM_ETIQUETTE });
		vue.getCbListeParamEtiquette().setItems(listeParamEtiquette);

		String dernierModeleEtiquetteUtilise = store
				.getString(PreferenceConstants.P_DERNIER_MODELE_UTILISE_ETIQUETTE);

		if (!dernierModeleEtiquetteUtilise.equals("")) {
			for (int i = 0; i < listeParamEtiquette.length; i++) {
				if (listeParamEtiquette[i]
						.equals(dernierModeleEtiquetteUtilise)) {
					vue.getCbListeParamEtiquette().select(i);
				}
			}
		}
	}

	public boolean impressionAuto() {
		return vue.getCbImprimer().getSelection();
	}

	public String etiquetteAuto() {
		return vue.getCbListeParamEtiquette().getText();
	}

	public boolean fusionnerDocuments() {
		return vue.getBtnFusionPublipostage().getSelection();
	}

	public boolean impressionAutoCourrier() {
		return vue.getCbImprimerCourrier().getSelection();
	}

	public TaFacture getMasterEntity() {
		return masterEntity;
	}

	public void setMasterEntity(TaFacture masterEntity) {
		this.masterEntity = masterEntity;
	}

	public TaFactureDAO getMasterDAO() {
		return masterDAO;
	}

	public void setMasterDAO(TaFactureDAO masterDAO) {
		this.masterDAO = masterDAO;
		setDaoStandard(masterDAO);
	}

	public boolean changementPageValide() {
		if (masterDAO.getModeObjet().getMode().compareTo(
				ModeObjet.EnumModeObjet.C_MO_EDITION) == 0
				|| masterDAO.getModeObjet().getMode().compareTo(
						ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0) {
			// mise a jour de l'entite principale
			if (masterEntity != null && selectedTotauxFacture != null
					&& ((IHMTotauxDocument) selectedTotauxFacture) != null) {
				mapperUIToModel.map((IHMTotauxDocument) selectedTotauxFacture,
						masterEntity);
			}
		}
		// dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
		// initEtatBouton();
		return true;
	};

	public void actCreateDoc() throws Exception {
		ParamAfficheChoixGenerationDocument param = new ParamAfficheChoixGenerationDocument();
		param.setTypeSrc(TypeDoc.TYPE_FACTURE);
		param.setDocumentSrc(masterEntity);
//		param.setDateDocument(masterEntity.getDateDocument());
		param.setCodeTiers(masterEntity.getTaTiers().getCodeTiers());
		Shell s = new Shell(vue.getShell(), LgrShellUtil.styleLgr);
		PaChoixGenerationDocument paChoixGenerationDocument = new PaChoixGenerationDocument(
				s, SWT.NULL);
		PaChoixGenerationDocumentController paChoixGenerationDocumentController = new PaChoixGenerationDocumentController(
				paChoixGenerationDocument);
		paChoixGenerationDocumentController.addRetourEcranListener(getThis());
		ParamAfficheSWT paramAfficheSWT = new ParamAfficheSWT();
		paramAfficheSWT.setHauteur(PaChoixGenerationDocumentController.HAUTEUR);
		paramAfficheSWT.setLargeur(PaChoixGenerationDocumentController.LARGEUR);
		paramAfficheSWT.setTitre(PaChoixGenerationDocumentController.TITRE);
		LgrShellUtil.afficheSWT(param, paramAfficheSWT,
				paChoixGenerationDocument, paChoixGenerationDocumentController,
				s);
		if (param.getFocus() != null)
			param.getFocusDefaut().requestFocus();
	}

	public void actCreateModele() throws Exception {
		ParamAfficheChoixGenerationDocument param = new ParamAfficheChoixGenerationDocument();
		param.setTypeSrc(TypeDoc.TYPE_FACTURE);
		param.setDocumentSrc(masterEntity);
		param.setCodeTiers(masterEntity.getTaTiers().getCodeTiers());
		
		Shell s = new Shell(vue.getShell(), LgrShellUtil.styleLgr);
		PaChoixGenerationDocument paChoixGenerationDocument = new PaChoixGenerationDocument(
				s, SWT.NULL);
		PaChoixGenerationModeleController paChoixGenerationDocumentController = new PaChoixGenerationModeleController(
				paChoixGenerationDocument);
		paChoixGenerationDocumentController.addRetourEcranListener(getThis());
		ParamAfficheSWT paramAfficheSWT = new ParamAfficheSWT();
		paramAfficheSWT.setHauteur(PaChoixGenerationModeleController.HAUTEUR);
		paramAfficheSWT.setLargeur(PaChoixGenerationModeleController.LARGEUR);
		paramAfficheSWT.setTitre(PaChoixGenerationModeleController.TITRE);
		LgrShellUtil.afficheSWT(param, paramAfficheSWT,
				paChoixGenerationDocument, paChoixGenerationDocumentController,
				s);
		if (param.getFocus() != null)
			param.getFocusDefaut().requestFocus();
	}

	@Override
	protected void initFocusSWT(AbstractLgrDAO dao,
			Map<ModeObjet.EnumModeObjet, Control> focus) {
		switch (daoLocal.getModeObjet().getMode()) {
		case C_MO_INSERTION:
			if (daoLocal.getModeObjet().getFocusCourantSWT() == null)
				daoLocal.getModeObjet().setFocusCourantSWT(
						focus.get(ModeObjet.EnumModeObjet.C_MO_INSERTION));
			break;
		case C_MO_EDITION:
			if (daoLocal.getModeObjet().getFocusCourantSWT() == null) {
				if (getFocusCourantSWT() instanceof Text) {
					if (((Text) getFocusCourantSWT()).getEditable())
						daoLocal.getModeObjet().setFocusCourantSWT(
								getFocusCourantSWT());
				}
				if (getFocusCourantSWT() instanceof DateTime) {
					break;
				}
			}
			if (daoLocal.getModeObjet().getFocusCourantSWT() == null)
				daoLocal.getModeObjet().setFocusCourantSWT(
						focus.get(ModeObjet.EnumModeObjet.C_MO_EDITION));
			break;
		default:
			daoLocal.getModeObjet().setFocusCourantSWT(
					focus.get(ModeObjet.EnumModeObjet.C_MO_CONSULTATION));
			break;
		}
		setFocusCourantSWT(daoLocal.getModeObjet().getFocusCourantSWT());
		if (daoLocal.getModeObjet().getFocusCourantSWT() != null)
			daoLocal.getModeObjet().getFocusCourantSWT().forceFocus();
	}

	// gestion de l'insertion dans l'expand d'affectation
	private void actInsererAffectation() throws Exception {
		// passer le document de la grille sources vers la grille dest
		// puis passer le document en modification
		DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(
				this, C_COMMAND_GLOBAL_MODIFIER_ID);
		fireDeclencheCommandeController(e);
		if (masterDAO.dataSetEnModif()) {
			if ((IHMAideAcompte) selectedAcompte.getValue() != null) {
				taRAcompte = null;
				for (TaRAcompte p : masterEntity.getTaRAcomptes()) {
					if (p.getTaAcompte() != null
							&& p.getTaAcompte().getCodeDocument().equals(
									((IHMAideAcompte) selectedAcompte
											.getValue()).getCodeDocument()))
						taRAcompte = p;
				}
				BigDecimal affectationCourante = BigDecimal.valueOf(0);
				TaAcompte taAcompte = daoAcompte
						.findByCode(((IHMAideAcompte) selectedAcompte
								.getValue()).getCodeDocument());
				// recherche si modif sur l'acompte entre temps
				rechercherModifAcompte(taAcompte);
				// rechercherModifAcompte();
				if (taRAcompte == null) {
					taRAcompte = new TaRAcompte();
					// taRAcompte.setTaAcompte(taAcompte);
					// affectationCourante=taRAcompte.getTaAcompte().getResteARegler();
				}
				taRAcompte.setTaAcompte(taAcompte);
				taRAcompte.setTaFacture(masterEntity);
				// taRAcompte.setAffectation(taAcompte.getResteARegler());
				if (masterEntity.getResteAReglerEcran().compareTo(
						taAcompte.calculResteARegler()) >= 0)
					taRAcompte.setAffectation(taAcompte.calculResteARegler());
				else
					taRAcompte.setAffectation(masterEntity.getResteAReglerEcran());
				if (taRAcompte.getAffectation().signum() < 0)
					taRAcompte.setAffectation(BigDecimal.valueOf(0));
				if (taRAcompte.getId() == 0)
					affectationCourante = taRAcompte.getAffectation();
				taRAcompte.getTaAcompte().setResteARegler(
						taRAcompte.getTaAcompte().calculResteARegler()
								.subtract(affectationCourante));
				taRAcompte.setEtatDeSuppression(false);
				masterEntity.addRAcompte(taRAcompte);
				// }
			}
			actRefreshAffectation();
			actRefreshListeAcompteDisponible();
		}
	}

	// gestion de l'insertion dans l'expand d'affectation
	private void actInsererAffectationAvoir() throws Exception {
		// passer le document de la grille sources vers la grille dest
		// puis passer le document en modification
		DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(
				this, C_COMMAND_GLOBAL_MODIFIER_ID);
		fireDeclencheCommandeController(e);
		if (masterDAO.dataSetEnModif()) {
			if ((IHMAideDocument) selectedAvoir.getValue() != null) {
				taRAvoir = null;
				for (TaRAvoir p : masterEntity.getTaRAvoirs()) {
					if (p.getTaAvoir() != null
							&& p.getTaAvoir().getCodeDocument()
									.equals(
											((IHMAideDocument) selectedAvoir
													.getValue())
													.getCodeDocument()))
						taRAvoir = p;
				}
				BigDecimal affectationCourante = BigDecimal.valueOf(0);
				TaAvoir taAvoir = daoAvoir
						.findByCode(((IHMAideDocument) selectedAvoir.getValue())
								.getCodeDocument());
				// recherche si modif sur l'acompte entre temps
				rechercherModifAvoir(taAvoir);
				// rechercherModifAcompte();
				if (taRAvoir == null) {
					taRAvoir = new TaRAvoir();
					// taRAcompte.setTaAcompte(taAcompte);
					// affectationCourante=taRAcompte.getTaAcompte().getResteARegler();
				}
				taRAvoir.setTaAvoir(taAvoir);
				taRAvoir.setTaFacture(masterEntity);
				if (masterEntity.getResteAReglerEcran().compareTo(
						taAvoir.calculResteARegler()) >= 0)
					taRAvoir.setAffectation(taAvoir.calculResteARegler());
				else
					taRAvoir.setAffectation(masterEntity
							.getResteAReglerComplet());
				if (taRAvoir.getAffectation().signum() < 0)
					taRAvoir.setAffectation(BigDecimal.valueOf(0));
				if (taRAvoir.getId() == 0)
					affectationCourante = taRAvoir.getAffectation();
				taRAvoir.getTaAvoir().setResteAReglerComplet(
						taRAvoir.getTaAvoir().calculResteARegler().subtract(
								affectationCourante));
				taRAvoir.setEtat(IHMEtat.integre);
				masterEntity.addRAvoir(taRAvoir);
				// }
			}
			actRefreshAffectationAvoirs();
			actRefreshListeAvoirDisponible();
		}
	}

	private class HandlerEnregistrerAffectationAvoir extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				actEnregistrerAffectationAvoir();
			} catch (Exception e) {
				logger.error("", e);
			}
			return event;
		}
	}

	// gestion de l'enregistrement dans l'expand d'affectation
	protected void actEnregistrerAffectationAvoir() throws Exception {
		EntityTransaction transaction = daoRAvoir.getEntityManager()
				.getTransaction();
		try {
			// TaAvoirDAO daoRecherche = new TaAvoirDAO(new
			// TaAvoirDAO().getEntityManager());
			TaAvoirDAO daoRecherche = new TaAvoirDAO(getEm());
			for (TaRAvoir taRAvoir : masterEntity.getTaRAvoirs()) {
				TaAvoir avoir = daoRecherche.findByCode(taRAvoir.getTaAvoir()
						.getCodeDocument());
				if (avoir == null)
					daoRecherche.messageNonAutoriseModification();
				else {
					if (!taRAvoir.getTaAvoir().getVersionObj().equals(
							avoir.getVersionObj()))
						daoRecherche
								.messageNonAutoriseModificationRelation(avoir);
				}
			}

			ctrlTousLesChampsAvantEnregistrementSWT(dbcAffectationAvoirs);
			daoRAvoir.getModeObjet().setMode(
					ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
			masterEntity.calculSommeAvoir();
			actRefreshAffectationAvoirs();
			initEtatBoutonAffectationAvoir();
		} catch (ExceptLgr e1) {
			logger.error("Erreur : actionPerformed", e1);
		} finally {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
		}
	}

	private class HandlerEnregistrerAffectation extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				actEnregistrerAffectation();
			} catch (Exception e) {
				logger.error("", e);
			}
			return event;
		}
	}

	// gestion de l'enregistrement dans l'expand d'affectation
	protected void actEnregistrerAffectation() throws Exception {
		EntityTransaction transaction = daoRAcompte.getEntityManager()
				.getTransaction();
		try {
			TaAcompteDAO daoRecherche = new TaAcompteDAO(getEm());
			for (TaRAcompte taRAcompte : masterEntity.getTaRAcomptes()) {
				TaAcompte acompte = daoRecherche.findByCode(taRAcompte
						.getTaAcompte().getCodeDocument());
				if (acompte == null)
					daoRecherche.messageNonAutoriseModification();
				else {
					if (!taRAcompte.getTaAcompte().getVersionObj().equals(
							acompte.getVersionObj()))
						daoRecherche
								.messageNonAutoriseModificationRelation(acompte);
				}
			}

			ctrlTousLesChampsAvantEnregistrementSWT(dbcAffectation);
			daoRAcompte.getModeObjet().setMode(
					ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
			masterEntity.calculSommeAcomptes();
			actRefreshAffectation();
			initEtatBoutonAffectation();
		} catch (ExceptLgr e1) {
			logger.error("Erreur : actionPerformed", e1);
		} finally {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
		}
	}

	// gestion de l'enregistrement dans l'expand d'affectation
	private void actAnnulerAffectation() throws Exception {
		try {
			daoRAcompte.annuler(taRAcompte);
			daoRAcompte.getModeObjet().setMode(
					ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
			if (masterEntity != null) {
				masterEntity.calculSommeAcomptes();
			}
			actRefreshAffectation();
			initEtatBoutonAffectation();
		} catch (ExceptLgr e1) {
			logger.error("Erreur : actionPerformed", e1);
		} finally {
		}
	}

	// gestion de l'enregistrement dans l'expand d'affectation
	private void actAnnulerAffectationAvoirs() throws Exception {
		try {
			daoRAvoir.annuler(taRAvoir);
			daoRAvoir.getModeObjet().setMode(
					ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
			if (masterEntity != null)
				masterEntity.calculSommeAvoir();
			actRefreshAffectationAvoirs();
			initEtatBoutonAffectationAvoir();
		} catch (ExceptLgr e1) {
			logger.error("Erreur : actionPerformed", e1);
		} finally {
		}
	}

	private class HandlerInsererAffectation extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				actInsererAffectation();
			} catch (Exception e) {
				logger.error("", e);
				try {
					actRefreshListeAcompteDisponible();
				} catch (Exception e1) {
					logger.error("", e);
				}
			}
			return event;
		}
	}

	private class HandlerInsererAffectationAvoir extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				actInsererAffectationAvoir();
			} catch (Exception e) {
				logger.error("", e);
				try {
					actRefreshListeAvoirDisponible();
				} catch (Exception e1) {
					logger.error("", e);
				}
			}
			return event;
		}
	}

	// gestion de la suppression dans l'expand d'affectation
	private void actSupprimerAffectation() throws Exception {
		EntityTransaction transaction = daoRAcompte.getEntityManager()
				.getTransaction();
		try {
			VerrouInterface.setVerrouille(true);
			DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(
					this, C_COMMAND_GLOBAL_MODIFIER_ID);
			fireDeclencheCommandeController(e);
			if (masterDAO.dataSetEnModif()) {
				// if (MessageDialog.openConfirm(vue.getShell(), MessagesEcran
				// .getString("Message.Attention"),
				// fr.legrain.document.controller.MessagesEcran
				// .getString("Document.Message.SupprimerAffectation"))) {
				for (TaRAcompte p : masterEntity.getTaRAcomptes()) {
					if (p.getTaAcompte() != null
							&& p.getTaAcompte().getCodeDocument()
									.equals(
											((IHMRDocument) selectedRAcompte
													.getValue())
													.getCodeDocument()))
						taRAcompte = p;
				}
				// daoRAcompte.begin(transaction);
				taRAcompte.setEtatDeSuppression(true);
				if (taRAcompte.getId() == 0)
					taRAcompte.setAffectation(BigDecimal.valueOf(0));
				if (taRAcompte.getId() == 0)
					taRAcompte.getTaAcompte().setResteARegler(
							taRAcompte.getTaAcompte().calculResteARegler());
				// daoRAcompte.commit(transaction);
				// taRAcompte = null;
				transaction = null;
				tableViewerAffectation.selectionGrille(0);
				daoRAcompte.getModeObjet().setMode(
						ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
				modificationDocument(new SWTModificationDocumentEvent(this));

				actRefreshAffectation(); // ajouter pour tester jpa
				actRefreshListeAcompteDisponible();

				masterEntity.calculSommeAcomptes();
				// }
			}
			// }
		} catch (ExceptLgr e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		} finally {
			VerrouInterface.setVerrouille(false);
		}
	}

	private class HandlerSupprimerAffectation extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				actSupprimerAffectation();
			} catch (Exception e) {
				logger.error("", e);
			}
			return event;
		}
	}

	private class HandlerSupprimerAffectationAvoir extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				actSupprimerAffectationAvoir();
			} catch (Exception e) {
				logger.error("", e);
			}
			return event;
		}
	}

	// gestion de la suppression dans l'expand d'affectation
	private void actSupprimerAffectationAvoir() throws Exception {
		EntityTransaction transaction = daoRAvoir.getEntityManager()
				.getTransaction();
		try {
			VerrouInterface.setVerrouille(true);
			DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(
					this, C_COMMAND_GLOBAL_MODIFIER_ID);
			fireDeclencheCommandeController(e);
			if (masterDAO.dataSetEnModif()) {
				// if (MessageDialog.openConfirm(vue.getShell(), MessagesEcran
				// .getString("Message.Attention"),
				// fr.legrain.document.controller.MessagesEcran
				// .getString("Document.Message.SupprimerAffectation"))) {
				for (TaRAvoir p : masterEntity.getTaRAvoirs()) {
					if (p.getTaAvoir() != null
							&& p.getTaAvoir().getCodeDocument().equals(
									((IHMRDocument) selectedRAvoir.getValue())
											.getCodeDocument()))
						taRAvoir = p;
				}
				// daoRAcompte.begin(transaction);
				taRAvoir.setEtat(IHMEtat.suppression);
				if (taRAvoir.getId() == 0)
					taRAvoir.setAffectation(BigDecimal.valueOf(0));
				if (taRAvoir.getId() == 0)
					taRAvoir.getTaAvoir().setResteAReglerComplet(
							taRAvoir.getTaAvoir().calculResteARegler());
				transaction = null;
				tableViewerAffectationAvoirs.selectionGrille(0);
				daoRAvoir.getModeObjet().setMode(
						ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
				modificationDocument(new SWTModificationDocumentEvent(this));

				actRefreshAffectationAvoirs(); // ajouter pour tester jpa
				actRefreshListeAvoirDisponible();

				masterEntity.calculSommeAvoir();
				// }
			}
			// }
		} catch (ExceptLgr e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		} finally {
			VerrouInterface.setVerrouille(false);
		}
	}
	
	protected class HandlerOuvrirCourrierTiers extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				new TiersUtil(getEm()).ouvreRepertoireCourrier(masterEntity
						.getTaTiers(), "/Facture/"
						+ masterEntity.getCodeDocument());
			} catch (Exception e1) {
				logger.error("Erreur : actionPerformed", e1);
			}
			return event;
		}
	}

	protected class HandlerSendMail extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				System.out
						.println("SWTPaEditorTotauxController.HandlerSendMail.execute()");
			} catch (Exception e1) {
				logger.error("Erreur : actionPerformed", e1);
			}
			return event;
		}
	}

	protected void refreshVue() {
		// Gestion des reglements
		vue.getTfLIBELLE_PAIEMENT().setEnabled(
				masterEntity.getTaRReglement().getTaReglement() != null
						&& !masterEntity.multiReglement()
						&& !paReglementController.getDaoStandard()
								.dataSetEnModif());
		vue.getTfREGLE_FACTURE().setEnabled(
				!masterEntity.multiReglement()
						&& !paReglementController.getDaoStandard()
								.dataSetEnModif());
		modifRegler();
		// calculTypePaiement();
		mapperModelToUI.map(masterEntity,
				((IHMTotauxDocument) selectedTotauxFacture));

	}

	protected void actRefreshAffectation() throws Exception {
		// rafraichissement des valeurs dans la grille
		if (affichageAcomptes()) {
			WritableList writableListAffectation = new WritableList(
					modelRAcompte.remplirListeRAcompte(masterEntity),
					IHMRDocument.class);
			tableViewerAffectation.setInput(writableListAffectation);
			tableViewerAffectation.selectionGrille(0);
			if (selectedRAcompte.getValue() != null)
				for (TaRAcompte p : masterEntity.getTaRAcomptes()) {
					if (p.getTaAcompte() != null
							&& p.getTaAcompte().getCodeDocument()
									.equals(
											((IHMRDocument) selectedRAcompte
													.getValue())
													.getCodeDocument()))
						taRAcompte = p;
				}
			findExpandIem(vue.getExpandBar(), paAffectation).setExpanded(
					modelRAcompte.getListeObjet().size() > 0);
		}
		initEtatBoutonAffectation();
	}

	protected void actRefreshAffectationAvoirs() throws Exception {

		// rafraichissement des valeurs dans la grille
		if (affichageAvoirs()) {
			WritableList writableListAffectation = new WritableList(modelRAvoir
					.remplirListeRAvoir(masterEntity), IHMRDocument.class);
			tableViewerAffectationAvoirs.setInput(writableListAffectation);
			tableViewerAffectationAvoirs.selectionGrille(0);
			if (selectedRAvoir.getValue() != null)
				for (TaRAvoir p : masterEntity.getTaRAvoirs()) {
					if (p.getTaAvoir() != null
							&& p.getTaAvoir().getCodeDocument().equals(
									((IHMRDocument) selectedRAvoir.getValue())
											.getCodeDocument()))
						taRAvoir = p;
				}
			findExpandIem(vue.getExpandBar(), paAffectationAvoir).setExpanded(
					modelRAvoir.getListeObjet().size() > 0);
		}
		initialiseTitreFenetreAvoirs();
		initEtatBoutonAffectationAvoir();
	}

	protected void actRefreshListeAcompteDisponible() throws Exception {
		// rafraichissement des valeurs dans la grille
		if (affichageAcomptes()) {
			WritableList writableListAffectation = new WritableList(
					// modelAcomptesDisponibles.remplirListe(masterEntity,new
					// TaAcompteDAO().getEntityManager()),
					modelAcomptesDisponibles
							.remplirListe(masterEntity, getEm()),
					IHMAideAcompte.class);
			tableViewerAcompte.setInput(writableListAffectation);
			tableViewerAcompte.selectionGrille(0);
			if (modelAcomptesDisponibles.getListeObjet().size() > 0) {
				findExpandIem(vue.getExpandBar(), paAffectation).setExpanded(
						true);
			}
			vue.getLaAvertissement().setVisible(
					modelAcomptesDisponibles.getListeObjet().size() > 0);
			String variable1 = "acompte";
			String variable2 = "";
			if (modelAcomptesDisponibles.getListeObjet().size() > 1)
				variable2 = "s";
			if (modelAcomptesDisponibles.getListeObjet().size() > 0) {
				findExpandIem(vue.getExpandBar(), paAffectation).setText(
						titreAcomptesDisponibles
								+ " ("
								+ modelAcomptesDisponibles.getListeObjet()
										.size() + " " + variable1 + variable2
								+ " en attente d'affectation)");
			} else {
				findExpandIem(vue.getExpandBar(), paAffectation).setText(
						titreAcomptesDisponibles);
			}
		}
	}

	protected void actRefreshListeAvoirDisponible() throws Exception {
		// rafraichissement des valeurs dans la grille
		if (affichageAvoirs()) {
			WritableList writableListAffectation = new WritableList(
					modelAvoirsDisponibles.remplirListe(masterEntity, getEm()),
					IHMAideDocument.class);
			tableViewerAvoir.setInput(writableListAffectation);
			tableViewerAvoir.selectionGrille(0);
			if (modelAvoirsDisponibles.getListeObjet().size() > 0) {
				findExpandIem(vue.getExpandBar(), paAffectationAvoir)
						.setExpanded(true);
			}
			initialiseTitreFenetreAvoirs();
		}
	}

	public void initialiseTitreFenetreAvoirs() {
		String suppLibelleFenetre = "";
		vue.getLaAvertissementAvoirs().setVisible(
				modelAvoirsDisponibles.getListeObjet().size() > 0);
		String variable1 = "avoir";
		String variable2 = "";
		if (modelAvoirsDisponibles.getListeObjet().size() > 1)
			variable2 = "s";
		if (modelAvoirsDisponibles.getListeObjet().size() > 0) {
			suppLibelleFenetre = titreAvoirsDisponibles + " ("
					+ modelAvoirsDisponibles.getListeObjet().size() + " "
					+ variable1 + variable2 + " en attente d'affectation)";
		} else {
			suppLibelleFenetre = titreAvoirsDisponibles;
		}
		if (masterEntity != null) {
			vue.getLaReglementsIndirects().setVisible(
					masterEntity.aDesReglementsIndirects()
							|| masterEntity.aDesAvoirsIndirects());
			if (masterEntity.aDesAvoirsIndirects())
				suppLibelleFenetre += "   (Attention!!! des affectations d'avoir ont été effectués hors facture)";
		}
		findExpandIem(vue.getExpandBar(), paAffectationAvoir).setText(
				suppLibelleFenetre);

	}

	public void supprimeTaRAvoir(TaRAvoir taRAvoir) throws Exception {
		masterEntity.removeRAvoir(taRAvoir);
		daoRAvoir.supprimer(taRAvoir);
		taRAvoir.setAffectation(BigDecimal.valueOf(0));
		taRAvoir.getTaAvoir().setResteAReglerComplet(
				taRAvoir.getTaAvoir().calculResteARegler());
		taRAvoir.getTaAvoir().removeRAvoir(taRAvoir);
		taRAvoir.getTaFacture().removeRAvoir(taRAvoir);
		taRAvoir.setTaFacture(null);
		taRAvoir.setTaAvoir(null);
		taRAvoir = null;
	}

	public void supprimeTaRAcompte(TaRAcompte taRAcompte) throws Exception {
		masterEntity.removeRAcompte(taRAcompte);
		daoRAcompte.supprimer(taRAcompte);
		taRAcompte.setAffectation(BigDecimal.valueOf(0));
		taRAcompte.getTaAcompte().setResteARegler(
				taRAcompte.getTaAcompte().calculResteARegler());
		taRAcompte.getTaAcompte().removeRAcompte(taRAcompte);
		taRAcompte.setTaFacture(null);
		taRAcompte.setTaAcompte(null);
		taRAcompte = null;
	}

	// public void supprimeTaReglement(TaRReglement taReglement) throws
	// Exception{
	// EntityTransaction transaction =
	// daoReglement.getEntityManager().getTransaction();
	// daoReglement.begin(transaction);
	// daoReglement.supprimer(taReglement);
	// masterEntity.removeReglement(taReglement);
	// taReglement.setAffectation(BigDecimal.valueOf(0));
	// taReglement.setTaFacture(null);
	// taReglement=null;
	// daoReglement.commit(transaction);
	// transaction=null;
	// }
	//	
	public void rechercherModifAcompte(TaAcompte acompte) throws Exception {
		if (acompte != null) {
			TaAcompteDAO daoRecherche = new TaAcompteDAO(getEm());
			TaAcompte acompteRech = daoRecherche.findByCode(acompte
					.getCodeDocument());
			if (acompteRech == null)
				daoRecherche
						.messageNonAutoriseModificationRelation(acompteRech);
			else {
				if (!acompte.getVersionObj()
						.equals(acompteRech.getVersionObj()))
					daoRecherche
							.messageNonAutoriseModificationRelation(acompteRech);
			}
		}
	}

	public void rechercherModifAcompte() throws Exception {
		for (TaRAcompte taRAcompte : masterEntity.getTaRAcomptes()) {
			rechercherModifAcompte(taRAcompte.getTaAcompte());
		}
	}

	public void rechercherModifAvoir(TaAvoir avoir) throws Exception {
		if (avoir != null) {
			TaAvoirDAO daoRecherche = new TaAvoirDAO(getEm());
			TaAvoir avoirRech = daoRecherche
					.findByCode(avoir.getCodeDocument());
			if (avoirRech == null)
				daoRecherche.messageNonAutoriseModificationRelation(avoirRech);
			else {
				if (!avoir.getVersionObj().equals(avoirRech.getVersionObj()))
					daoRecherche
							.messageNonAutoriseModificationRelation(avoirRech);
			}
		}
	}

	public void rechercherModifAvoir() throws Exception {
		for (TaRAvoir taRAvoir : masterEntity.getTaRAvoirs()) {
			rechercherModifAvoir(taRAvoir.getTaAvoir());
		}
	}

	public boolean affichageAcomptes() {
		return typeDocPresent.getTypeDocCompletPresent().containsKey(
				typeDocPresent.TYPE_ACOMPTE_BUNDLEID);
	}

	public boolean affichageAvoirs() {
		return typeDocPresent.getTypeDocCompletPresent().containsKey(
				typeDocPresent.TYPE_AVOIR_BUNDLEID);
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			new TiersUtil(getEm())
					.ouvreRepertoireCourrier(masterEntity.getTaTiers(),
							"/Facture/" + masterEntity.getCodeDocument());
		} catch (Exception e1) {
			logger.error("Erreur : actionPerformed", e1);
		}
		return event;
	}

	public boolean focusDansAffectation() {
		for (Control c : listeComposantAffectation) {
			if (getFocusCourantSWT().equals(c) || c.isFocusControl())
				return true;
		}
		return false;
	}

	public boolean focusDansAffectationAvoir() {
		for (Control c : listeComposantAffectationAvoirs) {
			if (getFocusCourantSWT().equals(c) || c.isFocusControl())
				return true;
		}
		return false;
	}

	public TaRAcompteDAO getDaoRAcompte() {
		return daoRAcompte;
	}

	public PaReglementController getPaReglementController() {
		return paReglementController;
	}

	public void setPaReglementController(
			PaReglementController paReglementController) {
		this.paReglementController = paReglementController;
	}

	@Override
	public void initEtatComposant() {
//		vue.getTfCODE_T_PAIEMENT().setEnabled(((IHMTotauxDocument)selectedTotauxFacture).getRegleDocument().signum()!=0);

	}

	public void actRefreshReglements(TaRReglement taReglement) throws Exception {
		// if(!daoReglement.dataSetEnModif()){
		// rafraichissement des valeurs dans la grille
		// Gestion des reglements
		String suppLibelleFenetre = "";
		vue.getLaReglementsIndirects().setVisible(
				masterEntity.aDesReglementsIndirects()
						|| masterEntity.aDesAvoirsIndirects());
		if (masterEntity.aDesReglementsIndirects())
			suppLibelleFenetre = "   (Attention!!! des règlements ont été effectués hors facture)";
		paReglementController.actRefresh(masterEntity, masterDAO, taReglement);

		vue.getTfLIBELLE_PAIEMENT().setEnabled(
				!masterEntity.multiReglement()
						&& !paReglementController.getDaoStandard()
								.dataSetEnModif());
		vue.getTfREGLE_FACTURE().setEnabled(
				!masterEntity.multiReglement()
						&& !paReglementController.getDaoStandard()
								.dataSetEnModif());
		modifRegler();
//		vue.getTfCODE_T_PAIEMENT().setEnabled(
//				!masterEntity.multiReglement()
//						&& !paReglementController.getDaoStandard()
//								.dataSetEnModif());
		mapperModelToUI.map(masterEntity,
				((IHMTotauxDocument) selectedTotauxFacture));
		findExpandIem(vue.getExpandBar(), paAffectationReglement).setExpanded(
				masterEntity.getTaRReglements().size() > 0);
		findExpandIem(vue.getExpandBar(), paAffectationReglement).setText(
				titreReglements + suppLibelleFenetre);
	}

	public void enregistreReglement() throws Exception {
		// Gestion des reglements
		if (paReglementController.getDaoStandard().dataSetEnModif())
			paReglementController.actEnregistrer();
	}

	public TaRReglement enregistreReglement(AbstractApplicationDAO dao,TaRReglement taRReglement) throws Exception {
		// Gestion des reglements
		return paReglementController.enregistreTaReglement(dao,taRReglement, false);
	}
	
	public void supprimeReglement(TaRReglement taRReglement) throws Exception {
		// Gestion des reglements
		paReglementController.supprimeTaRReglement(taRReglement, false);
	}

	public TaRAvoirDAO getDaoRAvoir() {
		return daoRAvoir;
	}

	public void bindCPaiement() {
		try {
			modelCPaiement = new ModelGeneralObjet<IHMInfosCPaiement, TaCPaiementDAO, TaCPaiement>(
					daoCPaiement, classModelCPaiement);
			// modelCPaiement.remplirListe();
			bindingFormSimple(daoCPaiement, mapComposantChampsCPaiement, dbc,
					realm, selectedCPaiement, classModelCPaiement);
		} catch (Exception e) {
			if (e.getMessage() != null)
				vue.getLaMessage().setText(e.getMessage());
			logger.error("", e);
		}
	}

	public boolean focusDansCPaiment(Control focusControl) {
		for (Control element : listeComposantCPaiement) {
			if (element.equals(focusControl))
				return true;
		}
		return false;
	}

	public void calculDateEcheance(Boolean appliquer) {
		TaTPaiement taTPaiement = null;
//		if (masterEntity != null
//				&& (masterEntity.getIdDocument() == 0 || appliquer)) {
		if (masterEntity != null && appliquer) {
			Integer report = null;
			Integer finDeMois = null;
			if (((IHMInfosCPaiement) selectedCPaiement) != null) {
				if (((IHMInfosCPaiement) selectedCPaiement)
						.getReportCPaiement() != null)
					report = ((IHMInfosCPaiement) selectedCPaiement)
							.getReportCPaiement();
				if (((IHMInfosCPaiement) selectedCPaiement)
						.getFinMoisCPaiement() != null)
					finDeMois = ((IHMInfosCPaiement) selectedCPaiement)
			
					.getFinMoisCPaiement();
			}
			if (masterEntity.reglementExiste())//&& masterEntity.getTaRReglement().getAffectation().signum()!=0
				taTPaiement = masterEntity.getTaRReglement().getTaReglement()
						.getTaTPaiement();
			masterEntity.calculDateEcheance(report, finDeMois, taTPaiement);
			((IHMTotauxDocument) selectedTotauxFacture)
					.setDateEchDocument(masterEntity.getDateEchDocument());
		}
	}

	public void calculDateEcheance() {
		calculDateEcheance(false);
	}

	private void actAppliquerCPaiement() throws Exception {
		if (masterEntity.multiReglement()) {
			MessageDialog
					.openInformation(
							vue.getShell(),
							"Attention",
							"Lors de réglements multiples, la date d'échéance n'est plus recalculée."
									+ Const.finDeLigne
									+ "Vous pouvez cependant ajuster vos dates d'échéance manuellement une par une.");
		} else {
			calculDateEcheance(true);
			masterEntity.mettreAJourDateEcheanceReglements();
			actRefreshReglements(null);
		}
	}

	public void initialisationDesCPaiement(Boolean recharger) {
		// on vide les modeles
		modelCPaiement.getListeObjet().clear();
		TaInfosFacture taInfosDocument = null;
		if (masterEntity != null) {
			if (masterEntity.getCodeDocument() != "")
				taInfosDocument = daoInfosFacture
						.findByCodeFacture(masterEntity.getCodeDocument());
			else
				taInfosDocument = new TaInfosFacture();

			TaTCPaiementDAO taTCPaiementDAO = new TaTCPaiementDAO(getEm());
			TaCPaiement taCPaiementDoc = null;
			if (taTCPaiementDAO.findByCode(TaTCPaiement.C_CODE_TYPE_FACTURE) != null
					&& taTCPaiementDAO.findByCode(
							TaTCPaiement.C_CODE_TYPE_FACTURE).getTaCPaiement() != null) {
				taCPaiementDoc = taTCPaiementDAO.findByCode(
						TaTCPaiement.C_CODE_TYPE_FACTURE).getTaCPaiement();
			}
			int report = 0;
			int finDeMois = 0;

			TaTPaiement taTPaiementReglement = new TaTPaiement();
			if (masterEntity.reglementExiste()
					&& masterEntity.getTaRReglement().getTaReglement()
							.getTaTPaiement() != null) {
				taTPaiementReglement = masterEntity.getTaRReglement()
						.getTaReglement().getTaTPaiement();
			}

			if (taTPaiementReglement != null
					&& ((taTPaiementReglement.getReportTPaiement() != null && taTPaiementReglement
							.getReportTPaiement() != 0) || (taTPaiementReglement
							.getFinMoisTPaiement() != null && taTPaiementReglement
							.getFinMoisTPaiement() != 0))) {
				IHMInfosCPaiement ihm = new IHMInfosCPaiement();
				ihm.setReportCPaiement(taTPaiementReglement
						.getReportTPaiement());
				ihm.setFinMoisCPaiement(taTPaiementReglement
						.getFinMoisTPaiement());
				modelCPaiement.getListeObjet().add(ihm);
			} else {
				if (masterEntity.getTaTiers() != null) {
					if (masterEntity.getTaTiers().getTaTPaiement() != null) {
						if (masterEntity.getTaTiers().getTaTPaiement()
								.getReportTPaiement() != null)
							report = masterEntity.getTaTiers().getTaTPaiement()
									.getReportTPaiement();
						if (masterEntity.getTaTiers().getTaTPaiement()
								.getFinMoisTPaiement() != null)
							finDeMois = masterEntity.getTaTiers()
									.getTaTPaiement().getFinMoisTPaiement();
					}

					if (masterEntity.getTaTiers().getTaCPaiement() == null
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
										masterEntity.getTaTiers()
												.getTaCPaiement(),
										classModelCPaiement));
				}
			}

			if (taCPaiementDoc != null)
				modelCPaiement.getListeObjet().add(
						modelCPaiement.getMapperModelToUI().map(taCPaiementDoc,
								classModelCPaiement));

			// ajout de l'adresse de livraison inscrite dans l'infos facture
			if (taInfosDocument != null) {
				if (recharger)
					modelCPaiement.getListeObjet().add(
							mapperModelToUIInfosDocVersCPaiement.map(
									masterEntity.getTaInfosDocument(),
									classModelCPaiement));
				else
					modelCPaiement.getListeObjet().addFirst(
							mapperModelToUIInfosDocVersCPaiement.map(
									masterEntity.getTaInfosDocument(),
									classModelCPaiement));
			}
		}
		if (!modelCPaiement.getListeObjet().isEmpty()) {
			((IHMInfosCPaiement) selectedCPaiement)
					.setIHMInfosCPaiement(modelCPaiement.getListeObjet()
							.getFirst());
			// findExpandIem(vue.getExpandBarCondition(),
			// paInfosCondPaiement).setExpanded(true);
		} else {
			((IHMInfosCPaiement) selectedCPaiement)
					.setIHMInfosCPaiement(new IHMInfosCPaiement());
			// findExpandIem(vue.getExpandBarCondition(),
			// paInfosCondPaiement).setExpanded(false);
		}
		findExpandIem(vue.getExpandBarCondition(), paInfosCondPaiement)
				.setExpanded(!((IHMInfosCPaiement) selectedCPaiement).estVide());

	}

	// private class HandlerReinitCPaiement extends LgrAbstractHandler {
	//
	// public Object execute(ExecutionEvent event) throws ExecutionException {
	// try {
	// actReinitCPaiement();
	// } catch (Exception e) {
	// logger.error("", e);
	// }
	// return event;
	// }
	// }

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

	
	public void modifRegler(){
		try {
			boolean enabled=false;
			//vue.getTfCODE_T_PAIEMENT().setEditable(enabled);
			// TODO Auto-generated method stub
			enabled=!vue.getTfREGLE_FACTURE().getText().equals("") 
					&& LibConversion.stringToBigDecimal(vue.getTfREGLE_FACTURE().getText()).signum()!=0;
			if(enabled)enabled=!masterEntity.multiReglement()
			&& !paReglementController.getDaoStandard()
			.dataSetEnModif();
			vue.getTfCODE_T_PAIEMENT().setEditable(enabled);
			vue.getTfLIBELLE_PAIEMENT().setEditable(enabled);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}


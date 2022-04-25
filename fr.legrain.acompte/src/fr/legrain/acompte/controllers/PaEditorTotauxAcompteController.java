package fr.legrain.acompte.controllers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.eclipse.jface.util.ListenerList;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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

import fr.legrain.acompte.pluginAcompte;
import fr.legrain.acompte.divers.IHMLignesTVA;
import fr.legrain.acompte.divers.ModelLignesTVA;
import fr.legrain.acompte.divers.ModelRAcompte;
import fr.legrain.acompte.divers.ModelTotaux;
import fr.legrain.acompte.ecrans.PaTotauxSWT;
import fr.legrain.acompte.editor.EditorAcompte;
import fr.legrain.acompte.editor.EditorInputAcompte;
import fr.legrain.acompte.preferences.PreferenceConstants;
import fr.legrain.document.DocumentPlugin;
import fr.legrain.document.divers.TypeDoc;
import fr.legrain.document.ecran.MessagesEcran;
import fr.legrain.document.ecran.PaAffectationDocumentSWT;
import fr.legrain.document.ecran.PaCommentaire;
import fr.legrain.documents.dao.IDocumentTiersDAO;
import fr.legrain.documents.dao.LigneTva;
import fr.legrain.documents.dao.TaAcompte;
import fr.legrain.documents.dao.TaAcompteDAO;
import fr.legrain.documents.dao.TaApporteur;
import fr.legrain.documents.dao.TaApporteurDAO;
import fr.legrain.documents.dao.TaAvoir;
import fr.legrain.documents.dao.TaAvoirDAO;
import fr.legrain.documents.dao.TaBoncde;
import fr.legrain.documents.dao.TaBoncdeDAO;
import fr.legrain.documents.dao.TaBonliv;
import fr.legrain.documents.dao.TaBonlivDAO;
import fr.legrain.documents.dao.TaDevis;
import fr.legrain.documents.dao.TaDevisDAO;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaProforma;
import fr.legrain.documents.dao.TaProformaDAO;
import fr.legrain.documents.dao.TaRAcompte;
import fr.legrain.documents.dao.TaRAcompteDAO;
import fr.legrain.documents.dao.TaTPaiement;
import fr.legrain.documents.dao.TaTPaiementDAO;
import fr.legrain.documents.events.SWTModificationDocumentEvent;
import fr.legrain.documents.events.SWTModificationDocumentListener;
import fr.legrain.generationdocument.controllers.PaChoixGenerationDocumentController;
import fr.legrain.generationdocument.divers.ParamAfficheChoixGenerationDocument;
import fr.legrain.generationdocument.ecran.PaChoixGenerationDocument;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Document.ChangeModeEvent;
import fr.legrain.gestCom.Module_Document.ChangeModeListener;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IHMAideApporteur;
import fr.legrain.gestCom.Module_Document.IHMAideAvoir;
import fr.legrain.gestCom.Module_Document.IHMAideBoncde;
import fr.legrain.gestCom.Module_Document.IHMAideBonliv;
import fr.legrain.gestCom.Module_Document.IHMAideDevis;
import fr.legrain.gestCom.Module_Document.IHMAideFacture;
import fr.legrain.gestCom.Module_Document.IHMAideProforma;
import fr.legrain.gestCom.Module_Document.IHMRDocumentReduit;
import fr.legrain.gestCom.Module_Document.IHMTotauxAcompte;
import fr.legrain.gestCom.Module_Document.SWTTPaiement;
import fr.legrain.gestCom.Module_Tiers.SWTCompteBanque;
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
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDePageEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.AbstractLgrDAO;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.IBQuLgr;
import fr.legrain.lib.data.InfosVerifSaisie;
import fr.legrain.lib.data.LibCalcul;
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
import fr.legrain.tiers.dao.TaCompteBanque;
import fr.legrain.tiers.dao.TaCompteBanqueDAO;
import fr.legrain.tiers.divers.ParamAfficheTPaiement;
import fr.legrain.tiers.ecran.PaBanqueSWT;
import fr.legrain.tiers.ecran.PaTypePaiementSWT;
import fr.legrain.tiers.ecran.ParamAfficheBanque;
import fr.legrain.tiers.ecran.SWTPaBanqueController;
import fr.legrain.tiers.ecran.SWTPaTypePaiementController;
import fr.legrain.tiers.editor.EditorBanque;
import fr.legrain.tiers.editor.EditorInputBanque;
import fr.legrain.tiers.editor.EditorInputTypePaiement;
import fr.legrain.tiers.editor.EditorTypePaiement;

public class PaEditorTotauxAcompteController extends JPABaseControllerSWTStandard
		implements ISelectionProvider, /* ISelectionListener, */
		RetourEcranListener, SWTModificationDocumentListener,
		ChangeModeListener {

	static Logger logger = Logger.getLogger(PaEditorTotauxAcompteController.class
			.getName());
	private PaTotauxSWT vue = null;
	// private TaFacture masterEntity = new TaFacture();

	private PaAffectationDocumentSWT paAffectation=null;
	public List<Control> listeComposantEntete = null;
	protected Map<Control, String> mapComposantChampsCommentaire = null;
	protected Map<Control, String> mapComposantChampsAffectation = null;

	public static final String C_COMMAND_DOCUMENT_ANNULER_AFFECTATION_ID = "fr.legrain.Document.annulerAffectation";
	protected HandlerAnnulerAffectation handlerAnnulerAffectation = new HandlerAnnulerAffectation();

	public static final String C_COMMAND_DOCUMENT_MODIFIER_AFFECTATION_ID = "fr.legrain.Document.modifierAffectation";
	protected HandlerModifierAffectation handlerModifierAffectation = new HandlerModifierAffectation();
	
	public static final String C_COMMAND_DOCUMENT_ENREGISTRER_AFFECTATION_ID = "fr.legrain.Document.enregistrerAffectation";
	protected HandlerEnregistrerAffectation handlerEnregistrerAffectation = new HandlerEnregistrerAffectation();
	
	public static final String C_COMMAND_DOCUMENT_INSERER_AFFECTATION_ID = "fr.legrain.Document.insererAffectation";
	protected HandlerInsererAffectation handlerInsererAffectation = new HandlerInsererAffectation();
	
	public static final String C_COMMAND_DOCUMENT_SUPPRIMER_AFFECTATION_ID = "fr.legrain.Document.supprimerAffectation";
	protected HandlerSupprimerAffectation handlerSupprimerAffectation = new HandlerSupprimerAffectation();
	
	public List<Control> listeComposantAffectation = null;
	
	private Object ecranAppelant = null;
	private IHMTotauxAcompte ihmTotauxAcompte;
	private IHMTotauxAcompte ihmOldTotauxAcompte;
	private Realm realm;
	private DataBindingContext dbc;
	private DataBindingContext dbcAffectation;
	private String dataTVA[][] = {};
	private ModelTotaux modelTotaux = new ModelTotaux();
	private ModelLignesTVA modelLignesTVA;
	private ModelRAcompte modelRAcompte;
	
	private LgrTableViewer tableViewer;
	private LgrTableViewer tableViewerAffectation;
	private WritableList writableList;
	private Object selectedTotauxAcompte = new IHMTotauxAcompte();
//	private IHMRAcompte selectedRAcompte ;
	private IObservableValue selectedRAcompte ;
	private IHMRDocumentReduit swtOldRAcompte;
	private IHMRDocumentReduit ihmRAcompte;
	private String[] listeChamp;
	private String nomClassController = this.getClass().getSimpleName();
	private Class classModel = IHMTotauxAcompte.class;

	private MapChangeListener changeListener = new MapChangeListener();

	private TaAcompte masterEntity = null;
	private TaAcompteDAO masterDAO = null;
	private TaAcompteDAO daoLocal = null;

	private TaRAcompteDAO daoRAcompte = null;
	private TaRAcompte taRAcompte = null;
	
	private LgrDozerMapper<IHMTotauxAcompte, TaAcompte> mapperUIToModel = new LgrDozerMapper<IHMTotauxAcompte, TaAcompte>();
	private LgrDozerMapper<TaAcompte, IHMTotauxAcompte> mapperModelToUI = new LgrDozerMapper<TaAcompte, IHMTotauxAcompte>();
	private TypeDoc typeDocPresent = TypeDoc.getInstance();
	public PaEditorTotauxAcompteController() {
	}

	public PaEditorTotauxAcompteController(PaTotauxSWT vue) {
		this(vue, null);
	}

	public PaEditorTotauxAcompteController(PaTotauxSWT vue/* , WorkbenchPart part */,
			EntityManager em) {
		if (em != null) {
			setEm(em);
		}
		try {
			setVue(vue);
			daoLocal = new TaAcompteDAO(getEm());
			daoRAcompte = new TaRAcompteDAO(getEm());
			
			vue.getShell().addShellListener(this);
			vue.getShell().addTraverseListener(new Traverse());

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

			bind();
			try {
				((IHMTotauxAcompte) selectedTotauxAcompte)
						.setImprimer(pluginAcompte.getDefault()
								.getPreferenceStore().getBoolean(
										PreferenceConstants.IMPRIMER_AUTO));
			} catch (Exception e) {
			}
			// #AFAIRE
			// initEtatBouton();
			vue.getTfTX_REM_HT_FACTURE().setEnabled(false);
			vue.getTfMT_HT_CALC().setEnabled(false);
			vue.getTfMT_TTC_CALC().setEnabled(false);
			vue.getTfMT_TVA_CALC().setEnabled(false);
			vue.getTfTX_REM_TTC_FACTURE().setEnabled(false);

//				typeDocPresent.getTypeDocPresentSelectionAcompte().remove(typeDocPresent.TYPE_ACOMPTE_BUNDLEID);
//				typeDocPresent.getTypeDocPresentSelectionAcompte().remove(typeDocPresent.TYPE_FACTURE_BUNDLEID);
//				typeDocPresent.getTypeDocPresentSelectionAcompte().remove(typeDocPresent.TYPE_APPORTEUR_BUNDLEID);
//				typeDocPresent.getTypeDocPresentSelectionAcompte().remove(typeDocPresent.TYPE_BON_LIVRAISON_BUNDLEID);
//				typeDocPresent.getTypeDocPresentSelectionAcompte().remove(typeDocPresent.TYPE_AVOIR_BUNDLEID);
//				typeDocPresent.getTypeDocPresentSelectionAcompte().remove(typeDocPresent.TYPE_PRELEVEMENT_BUNDLEID);
//				typeDocPresent.getTypeDocPresentSelectionAcompte().remove(typeDocPresent.TYPE_REMISE_BUNDLEID);
//				typeDocPresent.getTypeDocPresentSelectionAcompte().remove(typeDocPresent.TYPE_REGLEMENT_BUNDLEID);

			String[] liste= new String[typeDocPresent.getTypeDocPresentSelectionAcompte().size()];
			int i = 0;
			for (String libelle : typeDocPresent.getTypeDocPresentSelectionAcompte().values()) {
				liste[i]=libelle;
				i++;
			}
//BUG #1263
			((PaAffectationDocumentSWT) vue
					.getExpandBar().getItem(1).getControl())
					.getComboTypeDoc().setItems(liste);
			((PaAffectationDocumentSWT) vue
					.getExpandBar().getItem(1).getControl())
					.getComboTypeDoc().select(0);
			

		} catch (Exception e) {
			if (e.getMessage() != null)
				vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaArticlesController", e);
		}
	}

	public void bind() {
		try {
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			LgrDozerMapper<TaAcompte, IHMTotauxAcompte> mapper = new LgrDozerMapper<TaAcompte, IHMTotauxAcompte>();
			if (ihmTotauxAcompte == null)
				ihmTotauxAcompte = new IHMTotauxAcompte();
			if (masterEntity != null)
				mapper.map(masterEntity, ihmTotauxAcompte);

			if (modelTotaux.getListeObjet().isEmpty()) {
				modelTotaux.getListeObjet().add(ihmTotauxAcompte);
			}
			selectedTotauxAcompte = ihmTotauxAcompte;
			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			setDbcStandard(dbc);

			bindingFormSimple(dbc, realm, selectedTotauxAcompte, classModel);
			bindingFormSimple(masterDAO, mapComposantChampsCommentaire, dbc,
					realm, selectedTotauxAcompte, classModel);

			bindTVA();
			bindAffectation();
		} catch (Exception e) {
			if (e.getMessage() != null)
				vue.getLaMessage().setText(e.getMessage());
			logger.error("", e);
		}
	}

	public void bindAffectation() {
//BUG #1263
		try {
			modelRAcompte = new ModelRAcompte();
			
			((PaAffectationDocumentSWT) vue.getExpandBar()
					.getItem(1).getControl()).getLaTitreGrille().setText("Liste des documents associés à l'acompte (hors facture)");
			((PaAffectationDocumentSWT) vue.getExpandBar()
					.getItem(1).getControl()).getLaTitreFormulaire().setText("Choix d'un document à associer à l'acompte");			
			
			tableViewerAffectation = new LgrTableViewer(((PaAffectationDocumentSWT) vue.getExpandBar()
					.getItem(1).getControl()).getGrille());
			
			tableViewerAffectation.createTableCol(IHMRDocumentReduit.class,((PaAffectationDocumentSWT) vue.getExpandBar()
					.getItem(1).getControl()).getGrille(), "AffectationsAcompteInformatif",
					Const.C_FICHIER_LISTE_CHAMP_GRILLE,-1);
			String[]listeChampAffectation = tableViewerAffectation.setListeChampGrille("AffectationsAcompteInformatif",
					Const.C_FICHIER_LISTE_CHAMP_GRILLE);

//			selectedRAcompte = new IHMRAcompte();
			selectedRAcompte = ViewersObservables.observeSingleSelection(tableViewerAffectation);
			LgrViewerSupport.bind(tableViewerAffectation, 
					 new WritableList(modelRAcompte.remplirListe(masterEntity,getEm()), IHMRDocumentReduit.class),
					BeanProperties.values(listeChampAffectation)
			);

			dbcAffectation = new DataBindingContext(realm);
			dbcAffectation.getValidationStatusMap().addMapChangeListener(new MapChangeListener(dbcAffectation,mapComposantChampsAffectation));
			
			tableViewerAffectation.addSelectionChangedListener(new ISelectionChangedListener() {
				public void selectionChanged(SelectionChangedEvent event) {
					if(selectedRAcompte.getValue()!=null){
						for (int i = 0; i < ((PaAffectationDocumentSWT) vue
								.getExpandBar().getItem(1).getControl())
								.getComboTypeDoc().getItemCount(); i++) {
							if(((PaAffectationDocumentSWT) vue
									.getExpandBar().getItem(1).getControl())
									.getComboTypeDoc().getItem(i).equals(((IHMRDocumentReduit)selectedRAcompte.getValue()).getTypeDocument()))
								((PaAffectationDocumentSWT) vue
										.getExpandBar().getItem(1).getControl())
										.getComboTypeDoc().select(i);
						}
					}
				}
			});

			((PaAffectationDocumentSWT) vue
					.getExpandBar().getItem(1).getControl())
					.getGrille().addMouseListener(new MouseAdapter(){

				public void mouseDoubleClick(MouseEvent e) {					
					String idEditor=typeDocPresent.getEditorDoc().get(((IHMRDocumentReduit)selectedRAcompte.getValue()).getTypeDocument());
					String valeurIdentifiant = ((IHMRDocumentReduit)selectedRAcompte.getValue()).getCodeDocument();
					ouvreDocument(valeurIdentifiant,idEditor);
				}

			});

			bindingFormMaitreDetail(mapComposantChampsAffectation, dbcAffectation, realm, selectedRAcompte, IHMRDocumentReduit.class);
			initEtatBoutonAffectation();
		} 
		catch (Exception e) {
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
			tableViewer.createTableCol(vue.getTableTVA(), "AcompteLignesTVA",
					Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			listeChamp = tableViewer.setListeChampGrille("AcompteLignesTVA",
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
	 * {@link PaEditorTotauxAcompteController#initVerifySaisie()} qui est une methode
	 * protected, dans l'initialisation du MultipageEditor. Cette methode ne
	 * peut etre appele qu'apres l'initialisation du masterDAO dans le
	 * MultipageEditor
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

		mapInfosVerifSaisie.put(vue.getTfMT_HT_CALC(), new InfosVerifSaisie(
				new TaAcompte(), Const.C_MT_HT_CALC,
				new int[] { InfosVerifSaisie.CTRL_QUE_DES_CHIFFRES }));
		mapInfosVerifSaisie.put(vue.getTfMT_TTC_CALC(), new InfosVerifSaisie(
				new TaAcompte(), Const.C_MT_TTC_CALC,
				new int[] { InfosVerifSaisie.CTRL_QUE_DES_CHIFFRES }));
		mapInfosVerifSaisie.put(vue.getTfMT_TVA_CALC(), new InfosVerifSaisie(
				new TaAcompte(), Const.C_MT_TVA_CALC,
				new int[] { InfosVerifSaisie.CTRL_QUE_DES_CHIFFRES }));
//		mapInfosVerifSaisie.put(vue.getTfREGLE_FACTURE(), new InfosVerifSaisie(
//				new TaAcompte(), Const.C_REGLE_DOCUMENT,
//				new int[] { InfosVerifSaisie.CTRL_QUE_DES_CHIFFRES }));
		mapInfosVerifSaisie.put(vue.getTfTX_REM_HT_FACTURE(),
				new InfosVerifSaisie(new TaAcompte(),
						Const.C_TX_REM_HT_DOCUMENT,
						new int[] { InfosVerifSaisie.CTRL_QUE_DES_CHIFFRES }));
		mapInfosVerifSaisie.put(vue.getTfTX_REM_TTC_FACTURE(),
				new InfosVerifSaisie(new TaAcompte(),
						Const.C_TX_REM_TTC_DOCUMENT,
						new int[] { InfosVerifSaisie.CTRL_QUE_DES_CHIFFRES }));
		mapInfosVerifSaisie.put(vue.getTfCODE_T_PAIEMENT(),
				new InfosVerifSaisie(new TaTPaiement(),
						Const.C_CODE_T_PAIEMENT, null));

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

		if(mapComposantChampsAffectation== null)
			mapComposantChampsAffectation = new LinkedHashMap<Control, String>();
		
		if (listeComposantAffectation == null)
			listeComposantAffectation = new ArrayList<Control>();
		
		
		for (Control c : mapComposantChamps.keySet()) {
			listeComposantEntete.add(c);
		}

		//vue.getTfTX_REM_HT_FACTURE().setEnabled(false);
		vue.getTfTX_REM_TTC_FACTURE().setEnabled(false);
		
		mapComposantChamps.put(vue.getTfTX_REM_HT_FACTURE(),
				Const.C_TX_REM_HT_DOCUMENT);
		mapComposantChamps.put(vue.getTfMT_HT_CALC(), Const.C_NET_HT_CALC);
		mapComposantChamps.put(vue.getTfMT_TVA_CALC(), Const.C_NET_TVA_CALC);
		mapComposantChamps.put(vue.getTfTX_REM_TTC_FACTURE(),
				Const.C_TX_REM_TTC_DOCUMENT);
		mapComposantChamps.put(vue.getTfMT_TTC_CALC(), Const.C_NET_TTC_CALC);
		mapComposantChamps
				.put(vue.getTfCOMPTEBANQUE(), Const.C_COMPTE_BANQUE);
		mapComposantChamps.put(vue.getTfCODE_T_PAIEMENT(),
				Const.C_CODE_T_PAIEMENT);
		mapComposantChamps.put(vue.getCbImprimer(), "imprimer");

		for (Control c : mapComposantChamps.keySet()) {
			c.setToolTipText(mapComposantChamps.get(c));
		}

		mapComposantChampsCommentaire.put(((PaCommentaire) vue.getExpandBar()
				.getItem(0).getControl()).getTfLIBL_COMMENTAIRE(),
				Const.C_COMMENTAIRE);
//BUG #1263
		mapComposantChampsAffectation.put(((PaAffectationDocumentSWT) vue
				.getExpandBar().getItem(1).getControl())
				.getTfCODE_DOCUMENT(),
				Const.C_CODE_DOCUMENT);
		
		listeComposantAffectation.add(((PaAffectationDocumentSWT) vue
				.getExpandBar().getItem(1).getControl())
				.getTfCODE_DOCUMENT());

		
		listeComposantFocusable.add(vue.getTfTX_REM_HT_FACTURE());
		listeComposantFocusable.add(vue.getTfMT_HT_CALC());
		listeComposantFocusable.add(vue.getTfMT_TVA_CALC());
		listeComposantFocusable.add(vue.getTfTX_REM_TTC_FACTURE());
		listeComposantFocusable.add(vue.getTfMT_TTC_CALC());
		listeComposantFocusable.add(vue.getTfCOMPTEBANQUE());
		listeComposantFocusable.add(vue.getCbImprimer());
		listeComposantFocusable.add(vue.getTfCODE_T_PAIEMENT());
		listeComposantFocusable.add(((PaCommentaire) vue.getExpandBar()
				.getItem(0).getControl()).getTfLIBL_COMMENTAIRE());
	
//BUG #1263
		listeComposantFocusable.add(((PaAffectationDocumentSWT) vue.getExpandBar()
				.getItem(1).getControl()).getGrille());
		listeComposantFocusable.add(((PaAffectationDocumentSWT) vue.getExpandBar()
				.getItem(1).getControl()).getTfCODE_DOCUMENT());		
		listeComposantFocusable.add(((PaAffectationDocumentSWT) vue.getExpandBar()
				.getItem(1).getControl()).getBtnInserer());
		listeComposantFocusable.add(((PaAffectationDocumentSWT) vue.getExpandBar()
				.getItem(1).getControl()).getBtnEnregistrer());
		listeComposantFocusable.add(((PaAffectationDocumentSWT) vue.getExpandBar()
				.getItem(1).getControl()).getBtnModifier());
		listeComposantFocusable.add(((PaAffectationDocumentSWT) vue.getExpandBar()
				.getItem(1).getControl()).getBtnSupprimer());
		listeComposantFocusable.add(((PaAffectationDocumentSWT) vue.getExpandBar()
				.getItem(1).getControl()).getBtnAnnuler());

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

		if (mapInitFocus == null)
			mapInitFocus = new LinkedHashMap<ModeObjet.EnumModeObjet, Control>();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION, vue
				.getTfCODE_T_PAIEMENT());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue
				.getTfCODE_T_PAIEMENT());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION, vue
				.getTfCODE_T_PAIEMENT());

		activeModifytListener();

//		vue.getTfREGLE_FACTURE().addVerifyListener(queDesChiffresPositifs);
		vue.getTfTX_REM_HT_FACTURE().addVerifyListener(queDesChiffresPositifs);
		vue.getTfTX_REM_TTC_FACTURE().addVerifyListener(queDesChiffresPositifs);
//BUG #1263
		((PaAffectationDocumentSWT)vue.getExpandBar()
				.getItem(1).getControl()).getBtnAnnuler().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ANNULER));
		((PaAffectationDocumentSWT)vue.getExpandBar()
				.getItem(1).getControl()).getBtnEnregistrer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ENREGISTRER));
		((PaAffectationDocumentSWT)vue.getExpandBar()
				.getItem(1).getControl()).getBtnInserer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_INSERER));
		((PaAffectationDocumentSWT)vue.getExpandBar()
				.getItem(1).getControl()).getBtnModifier().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_MODIFIER));
		((PaAffectationDocumentSWT)vue.getExpandBar()
				.getItem(1).getControl()).getBtnSupprimer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_SUPPRIMER));
		
		((PaAffectationDocumentSWT)vue.getExpandBar()
				.getItem(1).getControl()).getBtnAnnuler().setText("");
		((PaAffectationDocumentSWT)vue.getExpandBar()
				.getItem(1).getControl()).getBtnEnregistrer().setText("");
		((PaAffectationDocumentSWT)vue.getExpandBar()
				.getItem(1).getControl()).getBtnInserer().setText("");
		((PaAffectationDocumentSWT)vue.getExpandBar()
				.getItem(1).getControl()).getBtnModifier().setText("");
		((PaAffectationDocumentSWT)vue.getExpandBar()
				.getItem(1).getControl()).getBtnSupprimer().setText("");
		
//		((PaAffectationDocumentSWT)vue.getExpandBar()
//				.getItem(1).getControl()).getBtnAnnuler().setSize(20, 20);
//		((PaAffectationDocumentSWT)vue.getExpandBar()
//				.getItem(1).getControl()).getBtnEnregistrer().setSize(20, 20);
//		((PaAffectationDocumentSWT)vue.getExpandBar()
//				.getItem(1).getControl()).getBtnInserer().setSize(20, 20);
//		((PaAffectationDocumentSWT)vue.getExpandBar()
//				.getItem(1).getControl()).getBtnModifier().setSize(20, 20);
//		((PaAffectationDocumentSWT)vue.getExpandBar()
//				.getItem(1).getControl()).getBtnSupprimer().setSize(20, 20);
		
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

		
		mapCommand.put(C_COMMAND_DOCUMENT_MODIFIER_AFFECTATION_ID, handlerModifierAffectation);
		mapCommand.put(C_COMMAND_DOCUMENT_ANNULER_AFFECTATION_ID, handlerAnnulerAffectation);
		mapCommand.put(C_COMMAND_DOCUMENT_ENREGISTRER_AFFECTATION_ID, handlerEnregistrerAffectation);
		mapCommand.put(C_COMMAND_DOCUMENT_INSERER_AFFECTATION_ID, handlerInsererAffectation);
		mapCommand.put(C_COMMAND_DOCUMENT_SUPPRIMER_AFFECTATION_ID, handlerSupprimerAffectation);

		
		
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

		mapActions.put(vue.getPaBtnAvecAssistant().getPaBtnAssitant()
				.getBtnPrecedent(), C_COMMAND_GLOBAL_PRECEDENT_ID);
		mapActions.put(vue.getPaBtnAvecAssistant().getPaBtnAssitant()
				.getBtnSuivant(), C_COMMAND_GLOBAL_SUIVANT_ID);

//BUG #1263
		mapActions.put(((PaAffectationDocumentSWT)vue.getExpandBar()
				.getItem(1).getControl()).getBtnSupprimer(),
				C_COMMAND_DOCUMENT_SUPPRIMER_AFFECTATION_ID);
		mapActions.put(((PaAffectationDocumentSWT)vue.getExpandBar()
				.getItem(1).getControl()).getBtnAnnuler(),
				C_COMMAND_DOCUMENT_ANNULER_AFFECTATION_ID);
		mapActions.put(((PaAffectationDocumentSWT)vue.getExpandBar()
				.getItem(1).getControl()).getBtnEnregistrer(),
				C_COMMAND_DOCUMENT_ENREGISTRER_AFFECTATION_ID);
		mapActions.put(((PaAffectationDocumentSWT)vue.getExpandBar()
				.getItem(1).getControl()).getBtnInserer(),
				C_COMMAND_DOCUMENT_INSERER_AFFECTATION_ID);
		mapActions.put(((PaAffectationDocumentSWT)vue.getExpandBar()
				.getItem(1).getControl()).getBtnModifier(),
				C_COMMAND_DOCUMENT_MODIFIER_AFFECTATION_ID);		
		
		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID,
				C_COMMAND_GLOBAL_REFRESH_ID };
		mapActions.put(null, tabActionsAutres);

		Menu popupMenuFormulaire = new Menu(vue.getShell(), SWT.POP_UP);
		Menu popupMenuGrille = new Menu(vue.getShell(), SWT.POP_UP);
		Menu[] tabPopups = new Menu[] { popupMenuFormulaire, popupMenuGrille };
		this.initPopupAndButtons(mapActions, tabPopups);
		vue.getPaTotaux().setMenu(popupMenuFormulaire);
		// }
	}

	public PaEditorTotauxAcompteController getThis() {
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
							TaTPaiement f = null;
							TaTPaiementDAO t = new TaTPaiementDAO(getEm());
							f = t.findById(Integer
									.parseInt(((ResultAffiche) evt.getRetour())
											.getIdResult()));
							t = null;
							masterEntity.setTaTPaiement(f);
						}
//						if (getFocusAvantAideSWT().equals(((PaAffectationDocumentSWT) vue
//								.getExpandBar().getItem(1).getControl())
//								.getTfCODE_DOCUMENT())) {
//							validateUIField(Const.C_CODE_DOCUMENT, ((ResultAffiche) evt.getRetour())
//											.getResult());
//						}						
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
		if(daoRAcompte.dataSetEnModif())actAnnulerAffectation();
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
		switch ((PaEditorTotauxAcompteController.this.masterDAO.getModeObjet()
				.getMode())) {
		case C_MO_CONSULTATION:
			if (getFocusCourantSWT().equals(vue.getTfCODE_T_PAIEMENT())||getFocusCourantSWT().equals(vue.getTfCOMPTEBANQUE()))
				result = true;
//			if (getFocusCourantSWT().equals(((PaAffectationDocumentSWT)vue.getExpandBar()
//					.getItem(1).getControl()).getTfCODE_DOCUMENT()))
//				result = true;
			break;
		case C_MO_EDITION:
		case C_MO_INSERTION:
			if (getFocusCourantSWT().equals(vue.getTfCODE_T_PAIEMENT()))
				result = true;			
			if (getFocusCourantSWT().equals(((PaAffectationDocumentSWT)vue.getExpandBar()
					.getItem(1).getControl()).getTfCODE_DOCUMENT()))
				result = true;
			break;
		default:
			break;
		}
		return result;
	}


	@Override
	protected void actPrecedent() throws Exception {
		if(!daoRAcompte.dataSetEnModif()){	
		ChangementDePageEvent evt = new ChangementDePageEvent(this,
				ChangementDePageEvent.PRECEDENT);
		fireChangementDePage(evt);
		}else{				
			MessageDialog.openInformation(vue.getShell(), "ATTENTION",
			"Voulez devez enregistrer l'affectation en cours.");
			throw new Exception();
		}
	}

	@Override
	protected void actSuivant() throws Exception {
		if(!daoRAcompte.dataSetEnModif()){		
		ChangementDePageEvent evt = new ChangementDePageEvent(this,
				ChangementDePageEvent.SUIVANT);
		fireChangementDePage(evt);
		}else{				
			MessageDialog.openInformation(vue.getShell(), "ATTENTION",
			"Voulez devez enregistrer l'affectation en cours.");
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

				switch ((PaEditorTotauxAcompteController.this.masterDAO.getModeObjet()
						.getMode())) {
				case C_MO_CONSULTATION:
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
					if (getFocusCourantSWT().equals(vue.getTfCOMPTEBANQUE())) {
						PaBanqueSWT paBanqueSWT = new PaBanqueSWT(s2,SWT.NULL);
						SWTPaBanqueController swtPaBanqueController = new SWTPaBanqueController(paBanqueSWT);
						editorCreationId = EditorBanque.ID;
						editorInputCreation = new EditorInputBanque();
						paramAfficheAideRecherche.setForceAffichageAideRemplie(true);
						paramAfficheAideRecherche.setAfficheDetail(false);
						
						ParamAfficheBanque paramAfficheBanque = new ParamAfficheBanque();
						paramAfficheAideRecherche.setJPQLQuery(new TaCompteBanqueDAO(getEm()).getJPQLQuery());
						paramAfficheBanque.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheBanque.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaBanqueController;
						parametreEcranCreation = paramAfficheBanque;
						paramAfficheAideRecherche.setTypeEntite(TaCompteBanque.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_COMPTE_BANQUE);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCOMPTEBANQUE().getText());
						paramAfficheAideRecherche.setControllerAppelant(getThis());
//						paramAfficheAideRecherche.setModel(swtPaBanqueController.getModelBanque());
						
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTCompteBanque,TaCompteBanqueDAO,TaCompteBanque>
						(new TaCompteBanqueDAO(getEm()).selectCompteEntreprise(),SWTCompteBanque.class,getEm()));
						paramAfficheAideRecherche.setTypeObjet(swtPaBanqueController.getClassModel());
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_COMPTE_BANQUE);
					}
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
					}
					if (getFocusCourantSWT().equals(vue.getTfCOMPTEBANQUE())) {
						PaBanqueSWT paBanqueSWT = new PaBanqueSWT(s2,SWT.NULL);
						SWTPaBanqueController swtPaBanqueController = new SWTPaBanqueController(paBanqueSWT);
						editorCreationId = EditorBanque.ID;
						editorInputCreation = new EditorInputBanque();
						paramAfficheAideRecherche.setForceAffichageAideRemplie(true);
						paramAfficheAideRecherche.setAfficheDetail(false);
						
						ParamAfficheBanque paramAfficheBanque = new ParamAfficheBanque();
						paramAfficheAideRecherche.setJPQLQuery(new TaCompteBanqueDAO(getEm()).getJPQLQuery());
						paramAfficheBanque.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheBanque.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaBanqueController;
						parametreEcranCreation = paramAfficheBanque;
						paramAfficheAideRecherche.setTypeEntite(TaCompteBanque.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_COMPTE_BANQUE);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCOMPTEBANQUE().getText());
						paramAfficheAideRecherche.setControllerAppelant(getThis());
//						paramAfficheAideRecherche.setModel(swtPaBanqueController.getModelBanque());
						
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTCompteBanque,TaCompteBanqueDAO,TaCompteBanque>
						(new TaCompteBanqueDAO(getEm()).selectCompteEntreprise(),SWTCompteBanque.class,getEm()));
						paramAfficheAideRecherche.setTypeObjet(swtPaBanqueController.getClassModel());
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_COMPTE_BANQUE);
					}

					if (getFocusCourantSWT().equals(((PaAffectationDocumentSWT)vue.getExpandBar().
							getItem(1).getControl()).getTfCODE_DOCUMENT())) {
						int typeDoc = ((PaAffectationDocumentSWT) vue
								.getExpandBar().getItem(1).getControl())
								.getComboTypeDoc().getSelectionIndex();
						IDocumentTiersDAO dao = null;
						
						paramAfficheAideRecherche.setForceAffichageAideRemplie(false);
						
						if(((PaAffectationDocumentSWT) vue
								.getExpandBar().getItem(1).getControl())
								
								.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_FACTURE)) {
							dao = new TaFactureDAO(getEm());
							paramAfficheAideRecherche.setTypeEntite(TaFacture.class);
							paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaFactureDAO,TaFacture>((TaFactureDAO)dao,IHMAideFacture.class));
							paramAfficheAideRecherche.setTypeObjet(IHMAideFacture.class);
							paramAfficheAideRecherche.setJPQLQuery("select a from TaFacture a where a.taTiers.codeTiers ='"+masterEntity.getTaTiers().getCodeTiers()+"'");
							paramAfficheAideRecherche.setCleListeTitre("SWTPaEditorFactureController");
						} else if(((PaAffectationDocumentSWT) vue
								.getExpandBar().getItem(1).getControl())
								.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_DEVIS)) {
							dao = new TaDevisDAO(getEm());
							paramAfficheAideRecherche.setTypeEntite(TaDevis.class);
							paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideDevis,TaDevisDAO,TaDevis>((TaDevisDAO)dao,IHMAideDevis.class));
							paramAfficheAideRecherche.setTypeObjet(IHMAideDevis.class);
							paramAfficheAideRecherche.setJPQLQuery("select a from TaDevis a where a.taTiers.codeTiers ='"+masterEntity.getTaTiers().getCodeTiers()+"'");
//							paramAfficheAideRecherche.setJPQLQuery(((TaDevisDAO)dao).getJPQLQuery());
							paramAfficheAideRecherche.setCleListeTitre("PaEditorDevisController");
						} else if(((PaAffectationDocumentSWT) vue
								.getExpandBar().getItem(1).getControl())
								.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_APPORTEUR)) {
							dao = new TaApporteurDAO(getEm());
							paramAfficheAideRecherche.setTypeEntite(TaApporteur.class);
							paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideApporteur,TaApporteurDAO,TaApporteur>((TaApporteurDAO)dao,IHMAideApporteur.class));
							paramAfficheAideRecherche.setTypeObjet(IHMAideApporteur.class);
							paramAfficheAideRecherche.setJPQLQuery("select a from TaApporteur a where a.taTiers.codeTiers ='"+masterEntity.getTaTiers().getCodeTiers()+"'");
//							paramAfficheAideRecherche.setJPQLQuery(((TaApporteurDAO)dao).getJPQLQuery());
							paramAfficheAideRecherche.setCleListeTitre("PaEditorDevisController");
						} else if(((PaAffectationDocumentSWT) vue
								.getExpandBar().getItem(1).getControl())
								.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_AVOIR)) {
							dao = new TaAvoirDAO(getEm());
							paramAfficheAideRecherche.setTypeEntite(TaAvoir.class);
							paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideAvoir,TaAvoirDAO,TaAvoir>((TaAvoirDAO)dao,IHMAideAvoir.class));
							paramAfficheAideRecherche.setTypeObjet(IHMAideAvoir.class);
							paramAfficheAideRecherche.setJPQLQuery("select a from TaAvoir a where a.taTiers.codeTiers ='"+masterEntity.getTaTiers().getCodeTiers()+"'");
//							paramAfficheAideRecherche.setJPQLQuery(((TaAvoirDAO)dao).getJPQLQuery());
							paramAfficheAideRecherche.setCleListeTitre("PaEditorDevisController");
						} else if(((PaAffectationDocumentSWT) vue
								.getExpandBar().getItem(1).getControl())
								.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_PROFORMA)) {
							dao = new TaProformaDAO(getEm());
							paramAfficheAideRecherche.setTypeEntite(TaProforma.class);
							paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideProforma,TaProformaDAO,TaProforma>((TaProformaDAO)dao,IHMAideProforma.class));
							paramAfficheAideRecherche.setTypeObjet(IHMAideProforma.class);
							paramAfficheAideRecherche.setJPQLQuery("select a from TaProforma a where a.taTiers.codeTiers ='"+masterEntity.getTaTiers().getCodeTiers()+"'");
//							paramAfficheAideRecherche.setJPQLQuery(((TaProformaDAO)dao).getJPQLQuery());
							paramAfficheAideRecherche.setCleListeTitre("PaEditorDevisController");
						} else if(((PaAffectationDocumentSWT) vue
								.getExpandBar().getItem(1).getControl())
								.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_BON_COMMANDE)) {
							dao = new TaBoncdeDAO(getEm());
							paramAfficheAideRecherche.setTypeEntite(TaBoncde.class);
							paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideBoncde,TaBoncdeDAO,TaBoncde>((TaBoncdeDAO)dao,IHMAideBoncde.class));
							paramAfficheAideRecherche.setTypeObjet(IHMAideBoncde.class);
							paramAfficheAideRecherche.setJPQLQuery("select a from TaBoncde a where a.taTiers.codeTiers ='"+masterEntity.getTaTiers().getCodeTiers()+"'");
//							paramAfficheAideRecherche.setJPQLQuery(((TaBoncdeDAO)dao).getJPQLQuery());
							paramAfficheAideRecherche.setCleListeTitre("PaEditorDevisController");
						} else if(((PaAffectationDocumentSWT) vue
								.getExpandBar().getItem(1).getControl())
								.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_BON_LIVRAISON)) {
							dao = new TaBonlivDAO(getEm());
							paramAfficheAideRecherche.setTypeEntite(TaBonliv.class);
							paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideBonliv,TaBonlivDAO,TaBonliv>((TaBonlivDAO)dao,IHMAideBonliv.class));
							paramAfficheAideRecherche.setTypeObjet(IHMAideBonliv.class);
							paramAfficheAideRecherche.setJPQLQuery("select a from TaBonliv a where a.taTiers.codeTiers ='"+masterEntity.getTaTiers().getCodeTiers()+"'");
//							paramAfficheAideRecherche.setJPQLQuery(((TaBonlivDAO)dao).getJPQLQuery());
							paramAfficheAideRecherche.setCleListeTitre("PaEditorDevisController");
						}
						editorCreationId = EditorAcompte.ID_EDITOR;
						editorInputCreation = new EditorInputAcompte();
//						TaFactureDAO dao = new TaFactureDAO(getEm());
//						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
						controllerEcranCreation = this;

//						paramAfficheAideRecherche.setTypeEntite(TaFacture.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_DOCUMENT);
						paramAfficheAideRecherche.setDebutRecherche(((PaAffectationDocumentSWT)vue.getExpandBar().
								getItem(1).getControl()).getTfCODE_DOCUMENT().getText());
						paramAfficheAideRecherche.setControllerAppelant(PaEditorTotauxAcompteController.this);
						paramAfficheAideRecherche.setCleListeTitre("PaEditorDevisController");
//						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaFactureDAO,TaFacture>(dao,IHMAideFacture.class));
//						paramAfficheAideRecherche.setTypeObjet(IHMAideFacture.class);
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_DOCUMENT);
					}
					break;

//				default:
//					break;
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
			mapperUIToModel.map(((IHMTotauxAcompte) selectedTotauxAcompte),
					masterEntity);
		}
		return null;
	}

	public IStatus validateUIField(String nomChamp, Object value) {
		String validationContext = "ACOMPTE";
		try {
			IStatus s = null;
			if (nomChamp.equals(Const.C_COMPTE_BANQUE)) {
				TaCompteBanqueDAO dao = new TaCompteBanqueDAO(getEm());

				dao.setModeObjet(getMasterDAO().getModeObjet());
				TaCompteBanque f = new TaCompteBanque();
				PropertyUtils.setSimpleProperty(f, nomChamp, value);
				s = dao.validate(f, nomChamp, validationContext);

				if (s.getSeverity() != IStatus.ERROR) {
					f = dao.findByTiersEntreprise((String) value);
					masterEntity.setTaCompteBanque(f);
					// f.getTaAcomptes().add(masterEntity);
				}
				dao = null;
			} else
			if (nomChamp.equals(Const.C_CODE_T_PAIEMENT)) {
				TaTPaiementDAO dao = new TaTPaiementDAO(getEm());

				dao.setModeObjet(getMasterDAO().getModeObjet());
				TaTPaiement f = new TaTPaiement();
				PropertyUtils.setSimpleProperty(f, nomChamp, value);
				s = dao.validate(f, nomChamp, validationContext);

				if (s.getSeverity() != IStatus.ERROR) {
					f = dao.findByCode((String) value);
					masterEntity.setTaTPaiement(f);
					// f.getTaAcomptes().add(masterEntity);
				}
				dao = null;
			} else if (nomChamp.equals("imprimer")) {
				s = new Status(Status.OK, gestComBdPlugin.PLUGIN_ID, "OK");
			}else
				if(nomChamp.equals(Const.C_CODE_DOCUMENT)){
					IDocumentTiers ob=null;
					IDocumentTiersDAO dao=null;
					List liste=null;
					if(value!=null && !value.equals("")){
						int typeDoc = ((PaAffectationDocumentSWT) vue
								.getExpandBar().getItem(1).getControl())
								.getComboTypeDoc().getSelectionIndex();
						if(((PaAffectationDocumentSWT) vue
								.getExpandBar().getItem(1).getControl())
								.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_BON_COMMANDE)) {
							dao = new TaBoncdeDAO(getEm());
							liste=((TaBoncdeDAO)dao).rechercheDocument(value.toString(), value.toString());
							if(liste!=null && liste.size()>0 && !rechercheMemeCode(((TaBoncde)liste.get(0)).getCodeDocument(),TypeDoc.TYPE_BON_COMMANDE)){
								if(((TaBoncde)liste.get(0)).getTaTiers().getCodeTiers().equals(masterEntity.getTaTiers().getCodeTiers())){
									taRAcompte.setTaBoncde((TaBoncde)liste.get(0));
									//							taRAcompte.setAffectation(BigDecimal.valueOf(0));
									((IHMRDocumentReduit)selectedRAcompte.getValue()).setCodeDocument(((TaBoncde)liste.get(0)).getCodeDocument());
									//							((IHMRAcompte)selectedRAcompte.getValue()).setAffectation(taRAcompte.getAffectation());
									((IHMRDocumentReduit)selectedRAcompte.getValue()).setTypeDocument(TypeDoc.TYPE_BON_COMMANDE);
									if(((IHMRDocumentReduit) selectedRAcompte.getValue()).getId()!=null) {
										taRAcompte.setId(((IHMRDocumentReduit) selectedRAcompte.getValue()).getId());
									}
									return new Status(Status.OK, gestComBdPlugin.PLUGIN_ID, "OK");
								}
							}
						}
						if(((PaAffectationDocumentSWT) vue
								.getExpandBar().getItem(1).getControl())
								.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_FACTURE)) {
							dao = new TaFactureDAO(getEm());
							liste=((TaFactureDAO)dao).rechercheDocument(value.toString(), value.toString());
							if(liste!=null && liste.size()>0 && !rechercheMemeCode(((TaFacture)liste.get(0)).getCodeDocument(),TypeDoc.TYPE_FACTURE)){
								if(((TaFacture)liste.get(0)).getTaTiers().getCodeTiers().equals(masterEntity.getTaTiers().getCodeTiers())){
									taRAcompte.setTaFacture((TaFacture)liste.get(0));
									//							taRAcompte.setAffectation(BigDecimal.valueOf(0));
									((IHMRDocumentReduit)selectedRAcompte.getValue()).setCodeDocument(((TaFacture)liste.get(0)).getCodeDocument());
									//((IHMRAcompte)selectedRAcompte.getValue()).setAffectation(taRAcompte.getAffectation());
									((IHMRDocumentReduit)selectedRAcompte.getValue()).setTypeDocument(TypeDoc.TYPE_FACTURE);
									if(((IHMRDocumentReduit) selectedRAcompte.getValue()).getId()!=null) {
										taRAcompte.setId(((IHMRDocumentReduit) selectedRAcompte.getValue()).getId());
									}
									return new Status(Status.OK, gestComBdPlugin.PLUGIN_ID, "OK");
								}
							}
						}						
						if(((PaAffectationDocumentSWT) vue
								.getExpandBar().getItem(1).getControl())
								.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_DEVIS)) {
							dao = new TaDevisDAO(getEm());
							liste=((TaDevisDAO)dao).rechercheDocument(value.toString(), value.toString());
							if(liste!=null && liste.size()>0 && !rechercheMemeCode(((TaDevis)liste.get(0)).getCodeDocument(),TypeDoc.TYPE_DEVIS)){
								if(((TaDevis)liste.get(0)).getTaTiers().getCodeTiers().equals(masterEntity.getTaTiers().getCodeTiers())){
									taRAcompte.setTaDevis((TaDevis)liste.get(0));
									//							taRAcompte.setAffectation(BigDecimal.valueOf(0));
									((IHMRDocumentReduit)selectedRAcompte.getValue()).setCodeDocument(((TaDevis)liste.get(0)).getCodeDocument());
									//((IHMRAcompte)selectedRAcompte.getValue()).setAffectation(taRAcompte.getAffectation());
									((IHMRDocumentReduit)selectedRAcompte.getValue()).setTypeDocument(TypeDoc.TYPE_DEVIS);
									if(((IHMRDocumentReduit) selectedRAcompte.getValue()).getId()!=null) {
										taRAcompte.setId(((IHMRDocumentReduit) selectedRAcompte.getValue()).getId());
									}
									return new Status(Status.OK, gestComBdPlugin.PLUGIN_ID, "OK");
								}
							}
						}
						if(((PaAffectationDocumentSWT) vue
								.getExpandBar().getItem(1).getControl())
								.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_PROFORMA)) {

							dao = new TaProformaDAO(getEm());
							liste=((TaProformaDAO)dao).rechercheDocument(value.toString(), value.toString());
							if(liste!=null && liste.size()>0 && !rechercheMemeCode(((TaProforma)liste.get(0)).getCodeDocument(),TypeDoc.TYPE_PROFORMA)){
								if(((TaProforma)liste.get(0)).getTaTiers().getCodeTiers().equals(masterEntity.getTaTiers().getCodeTiers())){
									taRAcompte.setTaProforma((TaProforma)liste.get(0));
									//								taRAcompte.setAffectation(BigDecimal.valueOf(0));
									((IHMRDocumentReduit)selectedRAcompte.getValue()).setCodeDocument(((TaProforma)liste.get(0)).getCodeDocument());
									//								((IHMRAcompte)selectedRAcompte.getValue()).setAffectation(taRAcompte.getAffectation());
									((IHMRDocumentReduit)selectedRAcompte.getValue()).setTypeDocument(TypeDoc.TYPE_PROFORMA);
									if(((IHMRDocumentReduit) selectedRAcompte.getValue()).getId()!=null) {
										taRAcompte.setId(((IHMRDocumentReduit) selectedRAcompte.getValue()).getId());
									}
									return new Status(Status.OK, gestComBdPlugin.PLUGIN_ID, "OK");
								}
							}
						}
						if(((PaAffectationDocumentSWT) vue
								.getExpandBar().getItem(1).getControl())
								.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_APPORTEUR)) {
							dao = new TaApporteurDAO(getEm());
							liste=((TaApporteurDAO)dao).rechercheDocument(value.toString(), value.toString());
							if(liste!=null && liste.size()>0 && !rechercheMemeCode(((TaApporteur)liste.get(0)).getCodeDocument(),TypeDoc.TYPE_APPORTEUR)){
								if(((TaApporteur)liste.get(0)).getTaTiers().getCodeTiers().equals(masterEntity.getTaTiers().getCodeTiers())){
									taRAcompte.setTaApporteur((TaApporteur)liste.get(0));
									//							taRAcompte.setAffectation(BigDecimal.valueOf(0));
									((IHMRDocumentReduit)selectedRAcompte.getValue()).setCodeDocument(((TaApporteur)liste.get(0)).getCodeDocument());
									//((IHMRAcompte)selectedRAcompte.getValue()).setAffectation(taRAcompte.getAffectation());
									((IHMRDocumentReduit)selectedRAcompte.getValue()).setTypeDocument(TypeDoc.TYPE_APPORTEUR);
									if(((IHMRDocumentReduit) selectedRAcompte.getValue()).getId()!=null) {
										taRAcompte.setId(((IHMRDocumentReduit) selectedRAcompte.getValue()).getId());
									}
									return new Status(Status.OK, gestComBdPlugin.PLUGIN_ID, "OK");
								}
							}
						}						
						if(((PaAffectationDocumentSWT) vue
								.getExpandBar().getItem(1).getControl())
								.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_AVOIR)) {
							dao = new TaAvoirDAO(getEm());
							liste=((TaAvoirDAO)dao).rechercheDocument(value.toString(), value.toString());
							if(liste!=null && liste.size()>0 && !rechercheMemeCode(((TaAvoir)liste.get(0)).getCodeDocument(),TypeDoc.TYPE_AVOIR)){
								if(((TaAvoir)liste.get(0)).getTaTiers().getCodeTiers().equals(masterEntity.getTaTiers().getCodeTiers())){
									taRAcompte.setTaAvoir((TaAvoir)liste.get(0));
									//							taRAcompte.setAffectation(BigDecimal.valueOf(0));
									((IHMRDocumentReduit)selectedRAcompte.getValue()).setCodeDocument(((TaAvoir)liste.get(0)).getCodeDocument());
									//((IHMRAcompte)selectedRAcompte.getValue()).setAffectation(taRAcompte.getAffectation());
									((IHMRDocumentReduit)selectedRAcompte.getValue()).setTypeDocument(TypeDoc.TYPE_AVOIR);
									if(((IHMRDocumentReduit) selectedRAcompte.getValue()).getId()!=null) {
										taRAcompte.setId(((IHMRDocumentReduit) selectedRAcompte.getValue()).getId());
									}
									return new Status(Status.OK, gestComBdPlugin.PLUGIN_ID, "OK");
								}
							}
						}						
						if(((PaAffectationDocumentSWT) vue
								.getExpandBar().getItem(1).getControl())
								.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_BON_LIVRAISON)) {
							dao = new TaBonlivDAO(getEm());
							liste=((TaBonlivDAO)dao).rechercheDocument(value.toString(), value.toString());
							if(liste!=null && liste.size()>0 && !rechercheMemeCode(((TaBonliv)liste.get(0)).getCodeDocument(),TypeDoc.TYPE_BON_LIVRAISON)){
								if(((TaBonliv)liste.get(0)).getTaTiers().getCodeTiers().equals(masterEntity.getTaTiers().getCodeTiers())){
									taRAcompte.setTaBonliv((TaBonliv)liste.get(0));
									//							taRAcompte.setAffectation(BigDecimal.valueOf(0));
									((IHMRDocumentReduit)selectedRAcompte.getValue()).setCodeDocument(((TaBonliv)liste.get(0)).getCodeDocument());
									//((IHMRAcompte)selectedRAcompte.getValue()).setAffectation(taRAcompte.getAffectation());
									((IHMRDocumentReduit)selectedRAcompte.getValue()).setTypeDocument(TypeDoc.TYPE_BON_LIVRAISON);
									if(((IHMRDocumentReduit) selectedRAcompte.getValue()).getId()!=null) {
										taRAcompte.setId(((IHMRDocumentReduit) selectedRAcompte.getValue()).getId());
									}
									return new Status(Status.OK, gestComBdPlugin.PLUGIN_ID, "OK");
								}
							}
						}						
						
						s = new Status(Status.ERROR, gestComBdPlugin.PLUGIN_ID, "Ce document n'est pas valide");						
					}
				}		
				else {
					TaAcompte u = new TaAcompte(true);
					PropertyUtils.setSimpleProperty(u, nomChamp, value);
					s = masterDAO.validate(u, nomChamp, validationContext);
					if (s.getSeverity() != IStatus.ERROR) {
						PropertyUtils.setSimpleProperty(masterEntity, nomChamp,
								value);
					}
					if (nomChamp.equals(Const.C_TX_REM_TTC_DOCUMENT)) {

					}
				}
			if (s.getSeverity() != IStatus.ERROR) {
				mapperModelToUI.map(masterEntity,
						((IHMTotauxAcompte) selectedTotauxAcompte));
			}
			// new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
			return s;
		} catch (IllegalAccessException e) {
			logger.error("", e);
		} catch (InvocationTargetException e) {
			logger.error("", e);
		} catch (NoSuchMethodException e) {
			logger.error("", e);
		}
		return null;
	}

	@Override
	protected void actEnregistrer() throws Exception {
		try {
			// sortieChamps();
			ctrlUnChampsSWT(getFocusCourantSWT());
			validateUI();
			if(!daoRAcompte.dataSetEnModif()){
//				actEnregistrerAffectation();
			ChangementDePageEvent change = new ChangementDePageEvent(this,
					ChangementDePageEvent.AUTRE, 0);
			fireChangementDePage(change);
			DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(
					this, C_COMMAND_GLOBAL_ENREGISTRER_ID);
			fireDeclencheCommandeController(e);
			}
			else{				
				MessageDialog.openInformation(vue.getShell(), "ATTENTION",
				"Voulez devez enregistrer l'affectation en cours avant de pouvoir enregistrer l'acompte en cours");
				throw new Exception();
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	// public SWT_IB_TA_FACTURE getIbTaTable() {
	// return ibTaTable;
	// }

	public void initEtatComposant() {
	}

	public IHMTotauxAcompte getIhmOldEnteteAcompte() {
		return ihmOldTotauxAcompte;
	}

	public void setIhmOldTotauxAcompte(IHMTotauxAcompte ihmOldTotauxAcompte) {
		this.ihmOldTotauxAcompte = ihmOldTotauxAcompte;
	}

	public void setIhmOldTotauxAcompte() {
		if (selectedTotauxAcompte != null)
			this.ihmOldTotauxAcompte = IHMTotauxAcompte
					.copy((IHMTotauxAcompte) selectedTotauxAcompte);
		else {
			if (tableViewer.selectionGrille(0)) {
				setSelectedTotauxAcompte(selectedTotauxAcompte);
				this.ihmOldTotauxAcompte = IHMTotauxAcompte
						.copy((IHMTotauxAcompte) selectedTotauxAcompte);
				tableViewer.setSelection(new StructuredSelection(
						(IHMTotauxAcompte) selectedTotauxAcompte), true);
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
		mapComposantDecoratedField.put(vue.getTfMT_HT_CALC(), vue
				.getFieldMT_HT_CALC());
		mapComposantDecoratedField.put(vue.getTfMT_TTC_CALC(), vue
				.getFieldMT_TTC_CALC());
		mapComposantDecoratedField.put(vue.getTfMT_TVA_CALC(), vue
				.getFieldMT_TVA_CALC());
//		mapComposantDecoratedField.put(vue.getTfREGLE_FACTURE(), vue
//				.getFieldREGLE_FACTURE());
		mapComposantDecoratedField.put(vue.getTfTX_REM_HT_FACTURE(), vue
				.getFieldTX_REM_HT_FACTURE());
		mapComposantDecoratedField.put(vue.getTfTX_REM_TTC_FACTURE(), vue
				.getFieldTX_REM_TTC_FACTURE());
//BUG #1263		
		mapComposantDecoratedField.put(((PaAffectationDocumentSWT) vue
				.getExpandBar().getItem(1).getControl())
				.getTfCODE_DOCUMENT(), ((PaAffectationDocumentSWT) vue
						.getExpandBar().getItem(1).getControl())
						.getFieldCODE_DOCUMENT());

	}

	public Class getClassModel() {
		return classModel;
	}

	@Override
	protected void sortieChamps() {
	}

	@Override
	protected void actRefresh() throws Exception {

		if (masterDAO.getModeObjet().getMode().compareTo(
				ModeObjet.EnumModeObjet.C_MO_CONSULTATION) != 0) {
			if (masterEntity != null && selectedTotauxAcompte != null
					&& (IHMTotauxAcompte) selectedTotauxAcompte != null) {
				mapperModelToUI.map(masterEntity,
						(IHMTotauxAcompte) selectedTotauxAcompte);
			}
			actRefreshAffectation();
		}

		DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(
				this, C_COMMAND_GLOBAL_REFRESH_ID);
		fireDeclencheCommandeController(e);
	}

	protected void actRefreshAffectation() throws Exception {
//BUG #1263
		//rafraichissement des valeurs dans la grille
		WritableList writableListAffectation =  new WritableList(
				modelRAcompte.remplirListe(masterEntity,getEm()), IHMRDocumentReduit.class);
		tableViewerAffectation.setInput(writableListAffectation);
		tableViewerAffectation.selectionGrille(0);	
		if(selectedRAcompte.getValue()!=null)
			for (TaRAcompte p : masterEntity.getTaRAcomptes()) {
				if (p.getTaAcompte() != null
						&& p.getTaAcompte().getCodeDocument().equals(
								((IHMRDocumentReduit) selectedRAcompte.getValue())
								.getCodeDocument()))
					taRAcompte = p;
			}
		findExpandIem(vue.getExpandBar(), paAffectation).setExpanded(masterEntity.getTaRAcomptes().size()>0);		
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
			((IHMTotauxAcompte) selectedTotauxAcompte).setMtHtCalc(LibCalcul
					.arrondi(montantHT));
			((IHMTotauxAcompte) selectedTotauxAcompte).setMtTtcCalc(LibCalcul
					.arrondi(montantTTC));
			((IHMTotauxAcompte) selectedTotauxAcompte).setMtTvaCalc(LibCalcul
					.arrondi(montantTVA));
			((IHMTotauxAcompte) selectedTotauxAcompte).setNetHtCalc(LibCalcul
					.arrondi(NetHT));
			((IHMTotauxAcompte) selectedTotauxAcompte).setNetTtcCalc(LibCalcul
					.arrondi(NetTTC));
			((IHMTotauxAcompte) selectedTotauxAcompte).setNetTvaCalc(LibCalcul
					.arrondi(NetTVA));

			if (((IHMTotauxAcompte) selectedTotauxAcompte)
					.getTxRemTtcDocument() == null)
				((IHMTotauxAcompte) selectedTotauxAcompte)
						.setTxRemTtcDocument(new BigDecimal(0));

//			if (((IHMTotauxAcompte) selectedTotauxAcompte).getRegleDocument() == null)
//				((IHMTotauxAcompte) selectedTotauxAcompte)
//						.setRegleDocument(new BigDecimal(0));

//			if (((IHMTotauxAcompte) selectedTotauxAcompte).getRegleDocument()
//					.compareTo(
//							((IHMTotauxAcompte) selectedTotauxAcompte)
//									.getNetTtcCalc()) > 0
//					&& ((IHMTotauxAcompte) selectedTotauxAcompte)
//							.getNetTtcCalc().compareTo(new BigDecimal(0)) > 0)
//				((IHMTotauxAcompte) selectedTotauxAcompte)
//						.setRegleDocument(((IHMTotauxAcompte) selectedTotauxAcompte)
//								.getNetTtcCalc());
//			else if (((IHMTotauxAcompte) selectedTotauxAcompte)
//					.getNetTtcCalc().signum() < 0)
//				((IHMTotauxAcompte) selectedTotauxAcompte)
//						.setRegleDocument(new BigDecimal(0));

			if (((IHMTotauxAcompte) selectedTotauxAcompte)
					.getTxRemHtDocument() == null)
				((IHMTotauxAcompte) selectedTotauxAcompte)
						.setTxRemHtDocument(new BigDecimal(0));

			masterEntity.calculeTvaEtTotaux();
			mapperModelToUI.map(masterEntity,
					((IHMTotauxAcompte) selectedTotauxAcompte));

		} catch (Exception e) {
			logger.error(e);
		}
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
			activeModifytListener(mapComposantChampsAffectation, daoRAcompte,new LgrModifyListener());
		}
	}

	/**
	 * Active l'ecoute de tous les champs du controller qui sont relies a la bdd,
	 * si le dataset n'est pas en modification et qu'un des champs est modifie, le dataset
	 * passera automatiquement en edition.
	 * @see #desactiveModifyListener
	 */
	public void activeModifytListener(Map<Control,String> mapComposantChamps, AbstractLgrDAO daoStandard,LgrModifyListener lgrModifyListener) {
		for (Control control : mapComposantChamps.keySet()) {
			if(control instanceof Text) {
				((Text)control).addModifyListener(lgrModifyListener);
			} else if(control instanceof Button) {
				((Button)control).addSelectionListener(lgrModifyListener);
			} else if(control instanceof DateTime) {
				//((cdatetimeLgr)control).addModifyListener(lgrModifyListener);
				//((CDateTime)control).addSelectionListener(lgrModifyListener);
			}
		}
		initMaxLenthTextComponent(mapComposantChamps,daoStandard);
		initMajTextComponent(mapComposantChamps,daoStandard);
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
		vue.layout(true);
	}

	public Boolean remonterDocument() throws Exception {
		try {
			boolean res = false;
			// nouvelle facture (mode, code existant "CodeAcompte")
			desactiveModifyListener();
			
			// #evt
			// #AFAIRE
			// modelTotaux.remplirListe(masterEntity);
			// ((IHMTotauxAcompte)selectedTotauxAcompte).setIHMTotauxAcompte(modelTotaux.getListeObjet().getFirst())
			// ;
			initSelectedObjectFromPreferences();
			res = true;

			return res;
		} finally {
			activeModifytListener();
			initEtatBouton(true);
		}

	}

	public void initialisationEcran() {
		try {
			actAnnulerAffectation();
		} catch (Exception e) {
			logger.error("", e);
		}
		initSelectedObjectFromPreferences();
	}

	private void initSelectedObjectFromPreferences() {
		((IHMTotauxAcompte) selectedTotauxAcompte).setImprimer(pluginAcompte
				.getDefault().getPreferenceStore().getBoolean(
						PreferenceConstants.IMPRIMER_AUTO));
	}

	// public void setIbTaTable(SWT_IB_TA_FACTURE ibTaTable) {
	// this.ibTaTable = ibTaTable;
	// setIbTaTableStandard(ibTaTable);
	// }

	public void setTaAcompte(TaAcompte masterEntity) {
		this.masterEntity = masterEntity;
		this.masterEntity.addModificationDocumentListener(this);
	}

	public Object getSelectedTotauxAcompte() {
		return selectedTotauxAcompte;
	}

	public void setSelectedTotauxAcompte(Object selectedTotauxAcompte) {
		this.selectedTotauxAcompte = selectedTotauxAcompte;
		((ModelObject) selectedTotauxAcompte)
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

	private void initVue() {
		addExpandBarItem(vue.getExpandBar(), new PaCommentaire(vue
				.getExpandBar(), SWT.PUSH), "Commentaires", pluginAcompte
				.getImageDescriptor("/icons/creditcards.png").createImage());
		vue.getExpandBar().getItem(0).setExpanded(
			DocumentPlugin.getDefault().getPreferenceStore()
				.getBoolean(fr.legrain.document.preferences.PreferenceConstants.AFF_COMMENTAIRE));

//BUG #1263
		paAffectation =new PaAffectationDocumentSWT(vue.getExpandBar(), SWT.PUSH);
		addExpandBarItem(vue.getExpandBar(), paAffectation, "Associer des documents (A titre informatif)",
				pluginAcompte.getImageDescriptor("/icons/user.png").createImage(),SWT.DEFAULT,200);
		findExpandIem(vue.getExpandBar(), paAffectation).setExpanded(pluginAcompte.getDefault().getPreferenceStore()
				.getBoolean(PreferenceConstants.AFF_ASSOCIER_DOCUMENT));

	}

	public boolean impressionAuto() {
		return vue.getCbImprimer().getSelection();
	}

	public TaAcompte getMasterEntity() {
		return masterEntity;
	}

	public void setMasterEntity(TaAcompte masterEntity) {
		this.masterEntity = masterEntity;
	}

	public TaAcompteDAO getMasterDAO() {
		return masterDAO;
	}

	public void setMasterDAO(TaAcompteDAO masterDAO) {
		this.masterDAO = masterDAO;
		setDaoStandard(masterDAO);
	}

	public boolean changementPageValide() {
		if (masterDAO.getModeObjet().getMode().compareTo(
				ModeObjet.EnumModeObjet.C_MO_EDITION) == 0
				|| masterDAO.getModeObjet().getMode().compareTo(
						ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0) {
			// mise a jour de l'entite principale
			if (masterEntity != null && selectedTotauxAcompte != null
					&& ((IHMTotauxAcompte) selectedTotauxAcompte) != null) {
				mapperUIToModel.map((IHMTotauxAcompte) selectedTotauxAcompte,
						masterEntity);
			}
		}
		// dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
		// initEtatBouton();
		return true;
	};

	public void actCreateDoc() throws Exception {
		ParamAfficheChoixGenerationDocument param = new ParamAfficheChoixGenerationDocument();
		param.setTypeSrc(TypeDoc.TYPE_ACOMPTE);
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
    

	private class HandlerAideAffectation extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				actAide(null);
			} catch (Exception e) {
				logger.error("", e);
			}
			return event;
		}
	}
	//gestion de l'insertion dans l'expand d'affectation
	private void actInsererAffectation() throws Exception {
		DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(
				this, C_COMMAND_GLOBAL_MODIFIER_ID);
		fireDeclencheCommandeController(e);			
		if(masterDAO.dataSetEnModif()){
			if(daoRAcompte.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
				VerrouInterface.setVerrouille(true);
				setSwtOldRAcompte();
				ihmRAcompte = new IHMRDocumentReduit();
				ihmRAcompte.setTaAcompte(masterEntity);
				taRAcompte = new TaRAcompte();
				taRAcompte.setTaAcompte(masterEntity);
				daoRAcompte.inserer(taRAcompte);
				modelRAcompte.getListeObjetReduit().add(ihmRAcompte);
				writableList = new WritableList(realm, modelRAcompte.getListeObjetReduit(), IHMRDocumentReduit.class);
				tableViewerAffectation.setInput(writableList);
				tableViewerAffectation.refresh();
				tableViewerAffectation.setSelection(new StructuredSelection(ihmRAcompte));
				initEtatBoutonAffectation();
			}
		}
	}
	
	private class HandlerInsererAffectation extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				actInsererAffectation();
			} catch (Exception e) {
				logger.error("", e);
			}
			return event;
		}
	}
	
	//gestion de la modification dans l'expand d'affectation
	private void actModifierAffectation() throws Exception {
		try {
			DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(
					this, C_COMMAND_GLOBAL_MODIFIER_ID);
			fireDeclencheCommandeController(e);			
			if(masterDAO.dataSetEnModif()){
				if(daoRAcompte.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
					setSwtOldRAcompte();
					for (TaRAcompte p : masterEntity.getTaRAcomptes()) {
						if (p.getTaAcompte() != null
								&& p.getTaAcompte().getCodeDocument().equals(
										((IHMRDocumentReduit) selectedRAcompte.getValue())
										.getCodeDocument()))
							taRAcompte = p;
					}

					daoRAcompte.modifier(taRAcompte);
					initEtatBoutonAffectation();
				}	
			}
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		}
	}		
		
	protected void initEtatBoutonAffectation() {
//		boolean trouve = contientDesEnregistrement(model);
		boolean trouve = true;
		mapCommand.put(C_COMMAND_DOCUMENT_MODIFIER_AFFECTATION_ID, handlerModifierAffectation);
		mapCommand.put(C_COMMAND_DOCUMENT_ANNULER_AFFECTATION_ID, handlerAnnulerAffectation);
		mapCommand.put(C_COMMAND_DOCUMENT_ENREGISTRER_AFFECTATION_ID, handlerEnregistrerAffectation);
		mapCommand.put(C_COMMAND_DOCUMENT_INSERER_AFFECTATION_ID, handlerInsererAffectation);
		mapCommand.put(C_COMMAND_DOCUMENT_SUPPRIMER_AFFECTATION_ID, handlerSupprimerAffectation);

		switch (daoRAcompte.getModeObjet().getMode()) {
		case C_MO_INSERTION:
			enableActionAndHandler(C_COMMAND_DOCUMENT_INSERER_AFFECTATION_ID,false);
			enableActionAndHandler(C_COMMAND_DOCUMENT_MODIFIER_AFFECTATION_ID,false);
			enableActionAndHandler(C_COMMAND_DOCUMENT_ENREGISTRER_AFFECTATION_ID,true);
			enableActionAndHandler(C_COMMAND_DOCUMENT_ANNULER_AFFECTATION_ID,true);
			enableActionAndHandler(C_COMMAND_DOCUMENT_SUPPRIMER_AFFECTATION_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID,false);
			if (((PaAffectationDocumentSWT) vue
					.getExpandBar().getItem(1).getControl())
					.getGrille()!=null)((PaAffectationDocumentSWT) vue
							.getExpandBar().getItem(1).getControl())
							.getGrille().setEnabled(false);
			break;
		case C_MO_EDITION:
			enableActionAndHandler(C_COMMAND_DOCUMENT_INSERER_AFFECTATION_ID,false);
			enableActionAndHandler(C_COMMAND_DOCUMENT_MODIFIER_AFFECTATION_ID,false);
			enableActionAndHandler(C_COMMAND_DOCUMENT_ENREGISTRER_AFFECTATION_ID,true);
			enableActionAndHandler(C_COMMAND_DOCUMENT_ANNULER_AFFECTATION_ID,true);
			enableActionAndHandler(C_COMMAND_DOCUMENT_SUPPRIMER_AFFECTATION_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID,true);
			if (((PaAffectationDocumentSWT) vue
					.getExpandBar().getItem(1).getControl())
					.getGrille()!=null)((PaAffectationDocumentSWT) vue
							.getExpandBar().getItem(1).getControl())
							.getGrille().setEnabled(false);
			break;
		case C_MO_CONSULTATION:
			enableActionAndHandler(C_COMMAND_DOCUMENT_INSERER_AFFECTATION_ID,true);
			enableActionAndHandler(C_COMMAND_DOCUMENT_MODIFIER_AFFECTATION_ID,trouve);
			enableActionAndHandler(C_COMMAND_DOCUMENT_ENREGISTRER_AFFECTATION_ID,false);
			enableActionAndHandler(C_COMMAND_DOCUMENT_ANNULER_AFFECTATION_ID,false);
			enableActionAndHandler(C_COMMAND_DOCUMENT_SUPPRIMER_AFFECTATION_ID,trouve);
			enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID,true);
			if (((PaAffectationDocumentSWT) vue
					.getExpandBar().getItem(1).getControl())
					.getGrille()!=null)((PaAffectationDocumentSWT) vue
							.getExpandBar().getItem(1).getControl())
							.getGrille().setEnabled(true);
			break;
		default:
			break;
		}	
	}

	private class HandlerModifierAffectation extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				actModifierAffectation();
			} catch (Exception e) {
				logger.error("", e);
			}
			return event;
		}
	}

	//gestion de l'enregistrement dans l'expand d'affectation
	protected void actEnregistrerAffectation() throws Exception {
		EntityTransaction transaction = daoRAcompte.getEntityManager().getTransaction();
		try {
			ctrlTousLesChampsAvantEnregistrementSWT(dbcAffectation,mapComposantChampsAffectation);
			taRAcompte.setTaAcompte(masterEntity);
			masterEntity.addRAcompte(taRAcompte);
//			taRAcompte=daoRAcompte.enregistrerMerge(taRAcompte);
			daoRAcompte.commit(transaction);
			daoRAcompte.setModeObjet(new ModeObjet(EnumModeObjet.C_MO_CONSULTATION));
			actRefreshAffectation();
			initEtatBoutonAffectation();
		} catch (ExceptLgr e1) {
			logger.error("Erreur : actionPerformed", e1);
		} finally {
			if(transaction!=null && transaction.isActive()) {
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

	//gestion de la suppression dans l'expand d'affectation
	private void actSupprimerAffectation() throws Exception {
		EntityTransaction transaction =daoRAcompte.getEntityManager().getTransaction();
		try {
			VerrouInterface.setVerrouille(true);
			DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
			fireDeclencheCommandeController(e);
			if(masterDAO.dataSetEnModif()){
				if (MessageDialog.openConfirm(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), fr.legrain.document.controller.MessagesEcran
						.getString("Document.Message.SupprimerAffectation"))) {
					for (TaRAcompte p : masterEntity.getTaRAcomptes()) {
						//TaBoncde
						if(p.getTaBoncde()!=null && (p.getTaBoncde().getCodeDocument().equals(((IHMRDocumentReduit) selectedRAcompte.getValue()).getCodeDocument())||
								p.getTaBoncde().getIdDocument()==((IHMRDocumentReduit) selectedRAcompte.getValue()).getIdDocument())) 
							taRAcompte = p;
						else //TaDevis
							if(p.getTaDevis()!=null && (p.getTaDevis().getCodeDocument().equals(
									((IHMRDocumentReduit) selectedRAcompte.getValue()).getCodeDocument())||
									p.getTaDevis().getIdDocument()==((IHMRDocumentReduit) selectedRAcompte.getValue()).getIdDocument()))  
								taRAcompte = p;
							else //TaProforma
								if(p.getTaProforma()!=null && (p.getTaProforma().getCodeDocument().equals(((IHMRDocumentReduit) selectedRAcompte.getValue()).getCodeDocument())||
										p.getTaProforma().getIdDocument()==((IHMRDocumentReduit) selectedRAcompte.getValue()).getIdDocument()))  
									taRAcompte = p;
								else //TaFacture
									if(p.getTaFacture()!=null && (p.getTaFacture().getCodeDocument().equals(((IHMRDocumentReduit) selectedRAcompte.getValue()).getCodeDocument())||
											p.getTaFacture().getIdDocument()==((IHMRDocumentReduit) selectedRAcompte.getValue()).getIdDocument()))  
										taRAcompte = p;	
									else //TaApporteur
										if(p.getTaApporteur()!=null && (p.getTaApporteur().getCodeDocument().equals(((IHMRDocumentReduit) selectedRAcompte.getValue()).getCodeDocument())||
												p.getTaApporteur().getIdDocument()==((IHMRDocumentReduit) selectedRAcompte.getValue()).getIdDocument()))  
											taRAcompte = p;						
										else //TaAvoir
											if(p.getTaAvoir()!=null && (p.getTaAvoir().getCodeDocument().equals(((IHMRDocumentReduit) selectedRAcompte.getValue()).getCodeDocument())||
													p.getTaAvoir().getIdDocument()==((IHMRDocumentReduit) selectedRAcompte.getValue()).getIdDocument()))  
												taRAcompte = p;
											else //Bonliv
												if(p.getTaBonliv()!=null && (p.getTaBonliv().getCodeDocument().equals(((IHMRDocumentReduit) selectedRAcompte.getValue()).getCodeDocument())||
														p.getTaBonliv().getIdDocument()==((IHMRDocumentReduit) selectedRAcompte.getValue()).getIdDocument()))  
													taRAcompte = p;	
						
					}
					if(taRAcompte!=null){
						daoRAcompte.begin(transaction);
						daoRAcompte.supprimer(taRAcompte);
						
						if(taRAcompte.getTaAvoir()!=null && taRAcompte.getTaAvoir().getIdDocument()==((IHMRDocumentReduit) selectedRAcompte.getValue()).getIdDocument())
							taRAcompte.getTaAvoir().getTaRAcomptes().remove(taRAcompte);
						if(taRAcompte.getTaApporteur()!=null && taRAcompte.getTaApporteur().getIdDocument()==((IHMRDocumentReduit) selectedRAcompte.getValue()).getIdDocument())
							taRAcompte.getTaApporteur().getTaRAcomptes().remove(taRAcompte);
						if(taRAcompte.getTaBoncde()!=null && taRAcompte.getTaBoncde().getIdDocument()==((IHMRDocumentReduit) selectedRAcompte.getValue()).getIdDocument())
							taRAcompte.getTaBoncde().getTaRAcomptes().remove(taRAcompte);
						if(taRAcompte.getTaBonliv()!=null && taRAcompte.getTaBonliv().getIdDocument()==((IHMRDocumentReduit) selectedRAcompte.getValue()).getIdDocument())
							taRAcompte.getTaBonliv().getTaRAcomptes().remove(taRAcompte);
						if(taRAcompte.getTaDevis()!=null && taRAcompte.getTaDevis().getIdDocument()==((IHMRDocumentReduit) selectedRAcompte.getValue()).getIdDocument())
							taRAcompte.getTaDevis().getTaRAcomptes().remove(taRAcompte);
						if(taRAcompte.getTaFacture()!=null && taRAcompte.getTaFacture().getIdDocument()==((IHMRDocumentReduit) selectedRAcompte.getValue()).getIdDocument())
							taRAcompte.getTaFacture().getTaRAcomptes().remove(taRAcompte);
						if(taRAcompte.getTaProforma()!=null && taRAcompte.getTaProforma().getIdDocument()==((IHMRDocumentReduit) selectedRAcompte.getValue()).getIdDocument())
							taRAcompte.getTaProforma().getTaRAcomptes().remove(taRAcompte);
						
						masterEntity.removeRAcompte(taRAcompte);
						taRAcompte.setTaAcompte(null);
						daoRAcompte.commit(transaction);
						taRAcompte=null;
						transaction = null;
					}
					tableViewerAffectation.selectionGrille(0);
					daoRAcompte.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
					actRefreshAffectation(); //ajouter pour tester jpa

				}
			}
		} catch (ExceptLgr e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		} finally {
			initEtatBoutonAffectation();
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

	//gestion de l'annulation dans l'expand d'affectation
	protected void actAnnulerAffectation() throws Exception {
		try {
			VerrouInterface.setVerrouille(true);
			switch (daoRAcompte.getModeObjet().getMode()) {
			case C_MO_INSERTION:
				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(),
						MessagesEcran
						.getString("Message.Attention"), fr.legrain.document.controller.MessagesEcran.
						getString("Document.Message.AnnulerAffectation")))) {
					remetTousLesChampsApresAnnulationSWT(dbcAffectation);
					if(((IHMRDocumentReduit) selectedRAcompte.getValue()).getId()==0){
						modelRAcompte.getListeObjetReduit().remove(((IHMRDocumentReduit) selectedRAcompte.getValue()));
						writableList = new WritableList(realm, modelRAcompte.getListeObjetReduit(), IHMRDocumentReduit.class);
						tableViewerAffectation.setInput(writableList);
						tableViewerAffectation.refresh();
						tableViewerAffectation.selectionGrille(0);
					}
					daoRAcompte.annuler(taRAcompte);
					hideDecoratedFieldsAffectation();
//					if(!annuleToutEnCours) {
//						fireAnnuleTout(new AnnuleToutEvent(this));
//					}
				}
				initEtatBoutonAffectation();
				break;
			case C_MO_EDITION:
				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), fr.legrain.document.controller.MessagesEcran.
						getString("Document.Message.AnnulerAffectation")))) {
					remetTousLesChampsApresAnnulationSWT(dbcAffectation);
					int rang = modelRAcompte.getListeObjetReduit().indexOf(selectedRAcompte.getValue());
					modelRAcompte.getListeObjetReduit().set(rang, swtOldRAcompte);
					writableList = new WritableList(realm, modelRAcompte.getListeObjetReduit(), IHMRDocumentReduit.class);
					tableViewerAffectation.setInput(writableList);
					tableViewerAffectation.refresh();
					tableViewerAffectation.setSelection(new StructuredSelection(swtOldRAcompte), true);
					daoRAcompte.annuler(taRAcompte);
					hideDecoratedFieldsAffectation();
//					if(!annuleToutEnCours) {
//						fireAnnuleTout(new AnnuleToutEvent(this));
//					}
				}
				initEtatBoutonAffectation();

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
	private class HandlerAnnulerAffectation extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				actAnnulerAffectation();
			} catch (Exception e) {
				logger.error("", e);
			}
			return event;
		}
	}
	

	public void setSwtOldRAcompte() {

		if (selectedRAcompte.getValue() != null)
			this.swtOldRAcompte = IHMRDocumentReduit.copy((IHMRDocumentReduit) selectedRAcompte.getValue());
		else {
			if(tableViewerAffectation.selectionGrille(0)){
				this.swtOldRAcompte = IHMRDocumentReduit.copy((IHMRDocumentReduit) selectedRAcompte.getValue());
				tableViewerAffectation.setSelection(new StructuredSelection(
						(IHMRDocumentReduit) selectedRAcompte.getValue()), true);
			}}
	}
	
	public void hideDecoratedFieldsAffectation(){
		if (mapComposantChampsAffectation!=null && mapComposantDecoratedField!=null)
			if(!mapComposantDecoratedField.isEmpty()) {
				for (Control control : mapComposantDecoratedField.keySet()) {			
					if(registry.getFieldDecoration("error.field."+mapComposantChampsAffectation.get(control))!=null){
						mapComposantDecoratedField.get(control).hideDecoration(registry.getFieldDecoration("error.field."+mapComposantChampsAffectation.get(control)));
						mapComposantDecoratedField.get(control).getControl().setToolTipText("");
				}
				}
			}
	}
	
	public void modifModeAffectation(){
		if (!VerrouInterface.isVerrouille() ){
			try {
				if(!daoRAcompte.dataSetEnModif()) {
					for (TaRAcompte p : masterEntity.getTaRAcomptes()) {
						if (p.getTaAcompte() != null
								&& p.getTaAcompte().getCodeDocument().equals(
										((IHMRDocumentReduit) selectedRAcompte.getValue())
												.getCodeDocument()))
							taRAcompte = p;
					}

					if(taRAcompte!=null) {
						actModifierAffectation();
					} else {
						actInsererAffectation();								
					}
					initEtatBoutonAffectation();
				}
			} catch (Exception e1) {
				logger.error("",e1);
			}				
		} 
	}
	private class LgrModifyListener implements ModifyListener, SelectionListener {

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

public boolean rechercheMemeCode(String code,String typeDoc){
	for (TaRAcompte taRAcompte : masterEntity.getTaRAcomptes()) {
		if(typeDoc.equals(TypeDoc.TYPE_BON_COMMANDE)&& taRAcompte.getTaBoncde()!=null){
			if (taRAcompte.getTaBoncde().getCodeDocument().equals(code))
				return true;
		}
		if(typeDoc.equals(TypeDoc.TYPE_BON_LIVRAISON)&& taRAcompte.getTaBonliv()!=null){
			if (taRAcompte.getTaBonliv().getCodeDocument().equals(code))
				return true;
		}
		if(typeDoc.equals(TypeDoc.TYPE_FACTURE)&& taRAcompte.getTaFacture()!=null){
			if (taRAcompte.getTaFacture().getCodeDocument().equals(code))
				return true;
		}
		if(typeDoc.equals(TypeDoc.TYPE_DEVIS)&& taRAcompte.getTaDevis()!=null){
			if (taRAcompte.getTaDevis().getCodeDocument().equals(code))
				return true;
		}
		if(typeDoc.equals(TypeDoc.TYPE_AVOIR)&& taRAcompte.getTaAvoir()!=null){
			if (taRAcompte.getTaAvoir().getCodeDocument().equals(code))
				return true;
		}
		if(typeDoc.equals(TypeDoc.TYPE_APPORTEUR)&& taRAcompte.getTaApporteur()!=null){
			if (taRAcompte.getTaApporteur().getCodeDocument().equals(code))
				return true;
		}
		if(typeDoc.equals(TypeDoc.TYPE_PROFORMA)&& taRAcompte.getTaProforma()!=null){
			if (taRAcompte.getTaProforma().getCodeDocument().equals(code))
				return true;
		}
	}
	return false;
}

public TaRAcompteDAO getDaoRAcompte() {
	return daoRAcompte;
}
}


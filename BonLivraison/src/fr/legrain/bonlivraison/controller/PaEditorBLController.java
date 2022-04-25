package fr.legrain.bonlivraison.controller;

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
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.databinding.swt.SWTObservables;
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

import fr.legrain.bonlivraison.BonLivraisonPlugin;
import fr.legrain.bonlivraison.divers.ParamAfficheLBL;
import fr.legrain.bonlivraison.ecran.PaEditorBL;
import fr.legrain.bonlivraison.editor.EditorBonliv;
import fr.legrain.bonlivraison.editor.EditorInputBonliv;
import fr.legrain.bonlivraison.preferences.PreferenceConstants;
import fr.legrain.document.DocumentPlugin;
import fr.legrain.document.controller.MessagesEcran;
import fr.legrain.document.ecran.PaCommentaire;
import fr.legrain.documents.dao.IDocumentTiersDAO;
import fr.legrain.documents.dao.TaBonliv;
import fr.legrain.documents.dao.TaBonlivDAO;
import fr.legrain.documents.dao.TaInfosBonliv;
import fr.legrain.documents.dao.TaInfosBonlivDAO;
import fr.legrain.documents.dao.TaLBonliv;
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
import fr.legrain.gestCom.Module_Document.IHMAideBonliv;
import fr.legrain.gestCom.Module_Document.IHMEnteteBonliv;
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
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.lib.gui.aide.PaAideRechercheSWT;
import fr.legrain.lib.gui.aide.PaAideSWT;
import fr.legrain.lib.gui.aide.ParamAfficheAide;
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

public class PaEditorBLController extends JPABaseControllerSWTStandard
		implements   RetourEcranListener,
		SWTModificationDocumentListener, ChangeModeListener {

	static Logger logger = Logger.getLogger(PaEditorBLController.class.getName());
	private PaEditorBL vue = null;
	private Integer idTiersDocumentOriginal = null; //ID du tiers lorsque l'on remonte un document, ne devrait pas etre changé ailleur
//	private Impression impressionBonliv = null;
	
	/** 01/03/2010 modifier les editions (zhaolin) **/
	BaseImpressionEdition baseImpressionEdition = null;
	/************************************************/
	
	private boolean desactiveValidateCodeDocument=false;
	
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

//	private List<TaBonliv> listeDocument = null;
	private List<TaTiers>listeTiers = null;
	
	protected Map<Control, String> mapComposantChampsAdresse = null;
	protected Map<Control, String> mapComposantChampsAdresseLiv = null;
	protected Map<Control, String> mapComposantChampsCPaiement = null;
	
	private TaBonlivDAO dao = null;//new TaBonlivDAO();

	private Object ecranAppelant = null;
	private IHMEnteteBonliv ihmOldEnteteBonliv;
	private Realm realm;
	private DataBindingContext dbc;
	private Object selectedBonliv = new IHMEnteteBonliv();
	private String nomClassController = this.getClass().getSimpleName();
	private Class classModel = IHMEnteteBonliv.class;
	
	private ComboViewer viewerComboLocalisationTVA;
	
	private ModelGeneralObjet<IHMEnteteBonliv,TaBonlivDAO,TaBonliv> modelEnteteBonliv /*= new ModelGeneralObjet<IHMEnteteBonliv,TaBonlivDAO,TaBonliv>(dao,classModel)*/;
	private String typeAdresseFacturation=DocumentPlugin.getDefault().getPreferenceStore().getString(fr.legrain.document.preferences.PreferenceConstants.TYPE_ADRESSE_FACTURATION);
	private String typeAdresseLivraison=DocumentPlugin.getDefault().getPreferenceStore().getString(fr.legrain.document.preferences.PreferenceConstants.TYPE_ADRESSE_BONLIV);

	private TaInfosBonlivDAO daoInfosBonliv = null;//new TaInfosBonlivDAO();
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
	private ModelGeneralObjet<IHMInfosCPaiement,TaCPaiementDAO,TaCPaiement> modelCPaiement /*= new ModelGeneralObjet<IHMInfosCPaiement,TaCPaiementDAO,TaCPaiement>(daoCPaiement,classModelCPaiement)*/;
	private DataBindingContext dbcCPaiement = null;

	private ContentProposalAdapter codeDocumentContentProposal;
	private ContentProposalAdapter tiersContentProposal;
	private MapChangeListener changeListener = new MapChangeListener();

	private PaLigneBLController controllerLigne = null;
	private PaEditorTotauxBLController controllerTotaux = null;

	private LgrDozerMapper<TaAdresse,IHMAdresseInfosFacturation> mapperModelToUIAdresseInfosDocument = new LgrDozerMapper<TaAdresse,IHMAdresseInfosFacturation>();
	private LgrDozerMapper<TaAdresse,IHMAdresseInfosLivraison> mapperModelToUIAdresseLivInfosDocument = new LgrDozerMapper<TaAdresse,IHMAdresseInfosLivraison>();
	private LgrDozerMapper<TaCPaiement,IHMInfosCPaiement> mapperModelToUICPaiementInfosDocument = new LgrDozerMapper<TaCPaiement,IHMInfosCPaiement>();
	
	private LgrDozerMapper<TaInfosBonliv,IHMInfosCPaiement> mapperModelToUIInfosDocVersCPaiement = new LgrDozerMapper<TaInfosBonliv,IHMInfosCPaiement>();
	private LgrDozerMapper<TaInfosBonliv,IHMAdresseInfosFacturation> mapperModelToUIInfosDocVersAdresseFact = new LgrDozerMapper<TaInfosBonliv,IHMAdresseInfosFacturation>();
	private LgrDozerMapper<TaInfosBonliv,IHMAdresseInfosLivraison> mapperModelToUIInfosDocVersAdresseLiv = new LgrDozerMapper<TaInfosBonliv,IHMAdresseInfosLivraison>();
		
	private LgrDozerMapper<IHMInfosCPaiement,TaInfosBonliv> mapperUIToModelCPaiementVersInfosDoc = new LgrDozerMapper<IHMInfosCPaiement, TaInfosBonliv>();
	private LgrDozerMapper<IHMAdresseInfosFacturation,TaInfosBonliv> mapperUIToModelAdresseFactVersInfosDoc = new LgrDozerMapper<IHMAdresseInfosFacturation, TaInfosBonliv>();
	private LgrDozerMapper<IHMAdresseInfosLivraison,TaInfosBonliv> mapperUIToModelAdresseLivVersInfosDoc = new LgrDozerMapper<IHMAdresseInfosLivraison, TaInfosBonliv>();
	
	private LgrDozerMapper<TaBonliv,TaInfosBonliv> mapperUIToModelDocumentVersInfosDoc = new LgrDozerMapper<TaBonliv, TaInfosBonliv>();	
	
	private LgrDozerMapper<IHMEnteteBonliv,TaBonliv> mapperUIToModel = new LgrDozerMapper<IHMEnteteBonliv,TaBonliv>();
	private LgrDozerMapper<TaBonliv,IHMEnteteBonliv> mapperModelToUI = new LgrDozerMapper<TaBonliv,IHMEnteteBonliv>();
	
	private TaBonliv taDocument = null;
	private TaInfosBonliv taInfosDocument = null;
//	private PaInfosCondPaiement paInfosCondPaiement = null;
	private PaInfosAdresse paInfosAdresseLiv = null;
	private PaInfosAdresse paInfosAdresseFact = null;
	
	private Menu popupMenuFormulaire = null;
	private Menu popupMenuGrille = null;

	public PaEditorBLController() {
	}
	
	public PaEditorBLController(PaEditorBL vue) {
		this(vue,null);
	}

	public PaEditorBLController(PaEditorBL vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaBonlivDAO(getEm());
		daoInfosBonliv = new TaInfosBonlivDAO(getEm());
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
//			impressionBonliv = new Impression(vue.getShell(),getEm());
			
			/** 01/03/2010 modifier les editions (zhaolin) **/
			baseImpressionEdition = new BaseImpressionEdition(vue.getShell(),getEm());
			/************************************************/
			vue.getDateTimeDATE_DOCUMENT().addTraverseListener(new DateTraverse());
			vue.getDateTimeDATE_LIV_DOCUMENT().addTraverseListener(new DateTraverse());
			
			//addDestroyListener(ibTaTable);
			setDaoStandard(dao);

//			this.swtBonliv.addModificationDocumentListener(ibTaTable);
//			this.swtBonliv.addModificationDocumentListener(this);
//			this.swtBonliv.addChangeModeListener(this);

			vue.getTfLIBELLE_DOCUMENT().addFocusListener(new FocusAdapter() {
				public void focusLost(FocusEvent e) {
					try {
//						ctrlUnChampsSWT(((PaEditorBonliv) getVue()).getTfLIBELLE_BONLIV());
						validateUIField(Const.C_LIBELLE_DOCUMENT,((PaEditorBL) getVue()).getTfLIBELLE_DOCUMENT());
					} catch (Exception e1) {
						logger.debug("", e1);
					}
				}
			});
			initController();

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



//		paInfosCondPaiement = new PaInfosCondPaiement(vue
//				.getExpandBar(), SWT.PUSH);
//		addExpandBarItem(vue.getExpandBar(), paInfosCondPaiement,
//				"Conditions de paiement", LibrairiesEcranPlugin.ir
//						.get(LibrairiesEcranPlugin.IMAGE_CONDITION_PAIEMENT));

		vue
				.getExpandBar()
				.getItem(0)
				.setExpanded(
						DocumentPlugin
								.getDefault()
								.getPreferenceStore()
								.getBoolean(
										fr.legrain.document.preferences.PreferenceConstants.AFF_ADRESSE));
		vue
				.getExpandBar()
				.getItem(1)
				.setExpanded(
						DocumentPlugin
								.getDefault()
								.getPreferenceStore()
								.getBoolean(
										fr.legrain.document.preferences.PreferenceConstants.AFF_ADRESSE_LIV));
//		findExpandIem(vue.getExpandBar(), paInfosCondPaiement).setExpanded(
//				DocumentPlugin.getDefault().getPreferenceStore().getBoolean(
//								fr.legrain.document.preferences.PreferenceConstants.AFF_CPAIEMENT));

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
			// vue.getCbImprimer().setSelection(FacturePlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.IMPRIMER_AUTO))
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

	public void bind(PaEditorBL PaEditorBonlivSWT) {
		try {
			modelEnteteBonliv = new ModelGeneralObjet<IHMEnteteBonliv,TaBonlivDAO,TaBonliv>(dao,classModel);
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

//			modelEnteteFacture.remplirListe();
			if (modelEnteteBonliv.getListeObjet().isEmpty()) {
				modelEnteteBonliv.getListeObjet().add(new IHMEnteteBonliv());
			}
			selectedBonliv = modelEnteteBonliv.getListeObjet().getFirst();
			
			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			setDbcStandard(dbc);

			bindingFormSimple(dbc, realm, selectedBonliv, classModel);
			bindAdresse();
			bindAdresseLiv();
			bindCPaiement();
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

//	public void bindCommentaire() {
//		try {
//			modelCPaiement = new ModelGeneral<IHMInfosCPaiement>(ibTaCPaiement
//					.getFIBQuery(), classModelCPaiement);
//			// realm = SWTObservables.getRealm(vue.getParent().getDisplay());
//			modelCPaiement.remplirListe();
//			if (!modelCPaiement.getListeObjet().isEmpty())
//				((IHMInfosCPaiement) selectedCPaiement)
//						.setIHMInfosCPaiement(modelCPaiement.getListeObjet()
//								.getFirst());
//			// dbcCPaiement = new DataBindingContext(realm);
//
//			// bindingFormSimple(ibTaCPaiement,mapComposantChampsCPaiement,
//			// dbcCPaiement, realm, selectedCPaiement,classModelCPaiement);
//			bindingFormSimple(ibTaCPaiement, mapComposantChampsCPaiement, dbc,
//					realm, selectedCPaiement, classModelCPaiement);
//
//		} catch (Exception e) {
//			if (e.getMessage() != null)
//				vue.getLaMessage().setText(e.getMessage());
//			logger.error("", e);
//		}
//	}

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
		
		mapInfosVerifSaisie.put(vue.getTfCODE_DOCUMENT(), new InfosVerifSaisie(new TaBonliv(),Const.C_CODE_DOCUMENT,null));
		mapInfosVerifSaisie.put(vue.getTfCODE_TIERS(), new InfosVerifSaisie(new TaTiers(),Const.C_CODE_TIERS,null));
		mapInfosVerifSaisie.put(vue.getTfLIBELLE_DOCUMENT(), new InfosVerifSaisie(new TaBonliv(),Const.C_LIBELLE_DOCUMENT,null));
		
		mapInfosVerifSaisie.put(((PaInfosAdresse) vue.getExpandBar()
				.getItem(0).getControl()).getTfAdresse1(), new InfosVerifSaisie(new TaInfosBonliv(),Const.C_ADRESSE1_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse) vue.getExpandBar()
				.getItem(0).getControl()).getTfAdresse2(), new InfosVerifSaisie(new TaInfosBonliv(),Const.C_ADRESSE2_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse) vue.getExpandBar()
				.getItem(0).getControl()).getTfAdresse3(), new InfosVerifSaisie(new TaInfosBonliv(),Const.C_ADRESSE3_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse) vue.getExpandBar()
				.getItem(0).getControl())
				.getTfCodePostal(), new InfosVerifSaisie(new TaInfosBonliv(),Const.C_CODEPOSTAL_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse) vue.getExpandBar()
				.getItem(0).getControl()).getTfVille(), new InfosVerifSaisie(new TaInfosBonliv(),Const.C_VILLE_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse) vue.getExpandBar()
				.getItem(0).getControl()).getTfPays(), new InfosVerifSaisie(new TaInfosBonliv(),Const.C_PAYS_INFOS_DOCUMENT,null));

		mapInfosVerifSaisie.put(((PaInfosAdresse) vue
				.getExpandBar().getItem(1).getControl())
				.getTfAdresse1(), new InfosVerifSaisie(new TaInfosBonliv(),Const.C_ADRESSE1_LIV_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse) vue
				.getExpandBar().getItem(1).getControl())
				.getTfAdresse2(), new InfosVerifSaisie(new TaInfosBonliv(),Const.C_ADRESSE2_LIV_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse) vue
				.getExpandBar().getItem(1).getControl())
				.getTfAdresse3(), new InfosVerifSaisie(new TaInfosBonliv(),Const.C_ADRESSE3_LIV_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse) vue
				.getExpandBar().getItem(1).getControl())
				.getTfCodePostal(), new InfosVerifSaisie(new TaInfosBonliv(),Const.C_CODEPOSTAL_LIV_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse) vue
				.getExpandBar().getItem(1).getControl())
				.getTfVille(), new InfosVerifSaisie(new TaInfosBonliv(),Const.C_VILLE_LIV_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse) vue
				.getExpandBar().getItem(1).getControl())
				.getTfPays(), new InfosVerifSaisie(new TaInfosBonliv(),Const.C_PAYS_LIV_INFOS_DOCUMENT,null));
				
//		mapInfosVerifSaisie.put(((PaInfosCondPaiement) vue
//				.getExpandBar().getItem(2).getControl())
//				.getTfCODE_C_PAIEMENT(), new InfosVerifSaisie(new TaInfosBonliv(),Const.C_CODE_C_PAIEMENT,null));
//		mapInfosVerifSaisie.put(((PaInfosCondPaiement) vue
//				.getExpandBar().getItem(2).getControl())
//				.getTfLIB_C_PAIEMENT(), new InfosVerifSaisie(new TaInfosBonliv(),Const.C_LIB_C_PAIEMENT,null));
//		mapInfosVerifSaisie.put(((PaInfosCondPaiement) vue
//				.getExpandBar().getItem(2).getControl())
//				.getTfREPORT_C_PAIEMENT(), new InfosVerifSaisie(new TaInfosBonliv(),Const.C_REPORT_C_PAIEMENT,null));
//		mapInfosVerifSaisie.put(((PaInfosCondPaiement) vue
//				.getExpandBar().getItem(2).getControl())
//				.getTfFIN_MOIS_C_PAIEMENT(), new InfosVerifSaisie(new TaInfosBonliv(),Const.C_FIN_MOIS_C_PAIEMENT,null));
		
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


		listeComposantFocusable.add(vue.getPaBtnAvecAssistant()
				.getPaBtnAssitant().getBtnSuivant());

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

		listeComposantFocusable.add(vue.getBtnFicheTiers());
		listeComposantFocusable.add(vue.getBtnGenDoc());
		listeComposantFocusable.add(vue.getBtnGenModel());
		listeComposantFocusable.add(vue.getBtnAutres());

		if (mapInitFocus == null)
			mapInitFocus = new LinkedHashMap<ModeObjet.EnumModeObjet, Control>();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION, vue
				.getTfCODE_DOCUMENT());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue
				.getTfCODE_DOCUMENT());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION, vue
				.getTfCODE_DOCUMENT());

		activeModifytListener();
		vue.getTfCODE_DOCUMENT().addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				codeDocumentContentProposal.setContentProposalProvider(contentProposalProviderDocument());
			}
			});

		vue.getTfCODE_TIERS().addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				tiersContentProposal
						.setContentProposalProvider(contentProposalProviderTiers());
			}

		});

		try {

			KeyStroke keyStroke = KeyStroke.getInstance("Ctrl+Space");
			codeDocumentContentProposal = new ContentProposalAdapter(vue
					.getTfCODE_DOCUMENT(), new TextContentAdapter(),contentProposalProviderDocument()
					, keyStroke, null);
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
								//validateUIField(Const.C_CODE_TIERS, vue.getTfCODE_TIERS());
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
		
//		mapActions.put(((PaInfosCondPaiement) vue.getExpandBar()
//				.getItem(2).getControl()).getBtnAppliquer(),C_COMMAND_DOCUMENT_APPLIQUER_CPAIEMENT_ID);
		
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


	public PaEditorBLController getThis() {
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
					boolean demande=!((IHMEnteteBonliv) selectedBonliv).BonlivEstVide(taDocument.dateDansExercice(new Date()));
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
							((IHMEnteteBonliv)selectedBonliv).setLibelleDocument("Bonliv N°"+taDocument.getCodeDocument()+" - "+nomTiers);
							
							if(vue.getCbTTC().isEnabled()){
								taDocument.setTtc(u.getTtcTiers());
								((IHMEnteteBonliv)selectedBonliv).setTtc(LibConversion.intToBoolean(taDocument.getTtc()));
							}
							
							changementTiers(true);
						}	
						if(getFocusAvantAideSWT().equals(vue.getTfCODE_DOCUMENT())){
							TaBonliv u = null;
							TaBonlivDAO t = new TaBonlivDAO(getEm());
							u = t.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
							t = null;
							taDocument=u;
							taDocument.setLegrain(true);
							try {
								remonterDocument(taDocument.getCodeDocument());
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
				}
				if (focusDansAdresse_Liv(getFocusCourantSWT())) {
					String id = evt.getRetour().getIdResult();

					taAdresse = daoAdresse.findById(Integer.parseInt(id));
					mapperModelToUIAdresseLivInfosDocument.map(taAdresse, ((IHMAdresseInfosLivraison) selectedAdresseLiv));

				}
				if (focusDansCPaiment(getFocusCourantSWT())) {
					String id = evt.getRetour().getIdResult();

					TaCPaiementDAO taCPaiementDAO = new TaCPaiementDAO(getEm());
					TaCPaiement taCPaiement = taCPaiementDAO.findById(Integer.parseInt(id));
					mapperModelToUICPaiementInfosDocument.map(taCPaiement, ((IHMInfosCPaiement) selectedCPaiement));
					calculDateEcheance();

				}
			}
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
//		if(((IHMEnteteBonliv)selectedBonliv).getIdDocument()==0) {
//		Integer report = null;
//		Integer finDeMois = null;
//		if(((IHMInfosCPaiement)selectedCPaiement)!=null) { 
//			if(((IHMInfosCPaiement)selectedCPaiement).getReportCPaiement()!=null)
//				report = ((IHMInfosCPaiement)selectedCPaiement).getReportCPaiement();
//			if(((IHMInfosCPaiement)selectedCPaiement).getFinMoisCPaiement()!=null)
//				finDeMois = ((IHMInfosCPaiement)selectedCPaiement).getFinMoisCPaiement();
//		}
//		((IHMEnteteBonliv)selectedBonliv).setDateEchDocument(taDocument.calculDateEcheance(report, finDeMois));
//		}
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
		boolean change=false;
		boolean res = true;
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

			if(((IHMEnteteBonliv)selectedBonliv).getIdDocument()!=0 && 
					((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
					|| (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0))) {
				if(!idTiersDocumentOriginal.equals(((IHMEnteteBonliv)selectedBonliv).getIdTiers())){
					findExpandIem(vue.getExpandBar(), paInfosAdresseFact).setExpanded(true);
					String message = "Les adresses et conditions du document ont été modifiées en fonction du nouveau tiers.";
					if(findStatusLineManager()!=null)
						findStatusLineManager().setMessage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_EXCLAMATION),message);
					change=true;
				}
			}
			////////////////////////////////////////////////////
			
			HashMap<String,String[]> map = new HashMap<String,String[]>();
			
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
			
			return res;
		} catch (Exception e) {
			logger.debug("", e);
			res = false;
		} finally {
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
		//taInfosFacture = daoInfosFacture.findByCodeFacture(taFacture.getCodeFacture());
		if(((IHMEnteteBonliv)selectedBonliv).getCodeDocument()!=null)
			taInfosDocument = daoInfosBonliv.findByCodeBonliv(((IHMEnteteBonliv)selectedBonliv).getCodeDocument());
		else
			taInfosDocument = new TaInfosBonliv();
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
	public void initialisationDesCPaiement(Boolean recharger) {
		// on vide les modeles
		modelCPaiement.getListeObjet().clear();
		TaInfosBonliv taInfosDocument = null;
		if (taDocument != null) {
			if (taDocument.getCodeDocument()!=null&& taDocument.getCodeDocument() != "")
				taInfosDocument = daoInfosBonliv
						.findByCodeBonliv(taDocument.getCodeDocument());
			else
				taInfosDocument = new TaInfosBonliv();

			TaTCPaiementDAO taTCPaiementDAO = new TaTCPaiementDAO(getEm());
			TaCPaiement taCPaiementDoc = null;
			if (taTCPaiementDAO.findByCode(TaTCPaiement.C_CODE_TYPE_BON_LIVRAISON) != null
					&& taTCPaiementDAO.findByCode(
							TaTCPaiement.C_CODE_TYPE_BON_LIVRAISON).getTaCPaiement() != null) {
				taCPaiementDoc = taTCPaiementDAO.findByCode(
						TaTCPaiement.C_CODE_TYPE_BON_LIVRAISON).getTaCPaiement();
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
//		findExpandIem(vue.getExpandBar(), paInfosCondPaiement)
//				.setExpanded(!((IHMInfosCPaiement) selectedCPaiement).estVide());

	}


	@Override
	protected void actInserer() throws Exception {
		if (!vue.isDisposed()) {
			try {
				if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
					setIhmOldEnteteBonliv();
					initialisationEcran("");
//					dao.getEntityManager().clear();
					taDocument = new TaBonliv(true);
					mapperModelToUI.map(taDocument, (IHMEnteteBonliv) selectedBonliv);
					((IHMEnteteBonliv) selectedBonliv).setCodeDocument(dao.genereCode());
					validateUIField(Const.C_CODE_DOCUMENT,((IHMEnteteBonliv) selectedBonliv).getCodeDocument());//permet de verrouiller le code genere
					((IHMEnteteBonliv) selectedBonliv).setCommentaire(BonLivraisonPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.COMMENTAIRE));

					validateUIField(Const.C_CODE_DOCUMENT,((IHMEnteteBonliv) selectedBonliv).getCodeDocument());
					taDocument.setCodeDocument(((IHMEnteteBonliv) selectedBonliv).getCodeDocument());
					changementTiers(true);
					
					Date date = new Date();
					TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
					TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
					// si date inférieur à dateDeb dossier
					if (LibDate.compareTo(date, taInfoEntreprise.getDatedebInfoEntreprise()) == -1) {
						((IHMEnteteBonliv) selectedBonliv).setDateDocument(taInfoEntreprise.getDatedebInfoEntreprise());
						((IHMEnteteBonliv) selectedBonliv).setDateEchDocument(taInfoEntreprise.getDatedebInfoEntreprise());
						((IHMEnteteBonliv) selectedBonliv).setDateLivDocument(taInfoEntreprise.getDatedebInfoEntreprise());
					} else
						// si date supérieur à dateFin dossier
						if (LibDate.compareTo(date, taInfoEntreprise.getDatefinInfoEntreprise()) == 1) {
							((IHMEnteteBonliv) selectedBonliv).setDateDocument(taInfoEntreprise.getDatefinInfoEntreprise());
							((IHMEnteteBonliv) selectedBonliv).setDateEchDocument(taInfoEntreprise.getDatefinInfoEntreprise());
							((IHMEnteteBonliv) selectedBonliv).setDateLivDocument(taInfoEntreprise.getDatefinInfoEntreprise());
						} else {
							((IHMEnteteBonliv) selectedBonliv).setDateDocument(new Date());
							((IHMEnteteBonliv) selectedBonliv).setDateEchDocument(new Date());
							((IHMEnteteBonliv) selectedBonliv).setDateLivDocument(new Date());
						}
					((IHMEnteteBonliv) selectedBonliv).setCodeTiers("");
					((IHMEnteteBonliv) selectedBonliv).setIdAdresse(0);
					((IHMEnteteBonliv) selectedBonliv).setIdAdresseLiv(0);
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
							((IHMEnteteBonliv) selectedBonliv).setCodeTPaiement(typePaiementDefaut);
							taDocument.setTaTPaiement(taTPaiement);
						}
					}					
					mapperUIToModel.map(((IHMEnteteBonliv) selectedBonliv), taDocument);
					
					calculDateEcheance();
					
					fireChangementMaster(new ChangementMasterEntityEvent(this,taDocument));

					dao.inserer(taDocument);
					controllerLigne.actInserer();
					controllerTotaux.remonterDocument();
					ParamAfficheLBL paramAfficheLBonliv = new ParamAfficheLBL();
					paramAfficheLBonliv.setModeEcran(EnumModeObjet.C_MO_INSERTION);

					paramAfficheLBonliv.setIdBonliv(taDocument.getCodeDocument());
					paramAfficheLBonliv.setInitFocus(false);
					
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
	public Boolean setIhmOldEnteteDocumentRefresh(){
		try {			
			if (selectedBonliv != null){
				this.ihmOldEnteteBonliv = IHMEnteteBonliv.copy((IHMEnteteBonliv) selectedBonliv);
				if (taDocument!=null){
					taDocument =rechercheBonliv(taDocument.getCodeDocument(), false, true);
					taInfosDocument=taDocument.getTaInfosDocument();
				}
			}
			return true;
		} catch (Exception e) {
			return false;
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
//				if(!LgrMess.isDOSSIER_EN_RESEAU()){
//				setIhmOldEnteteBonliv();
//				taDocument = dao.findById(((IHMEnteteBonliv) selectedBonliv).getIdDocument());
//				dao.modifier(taDocument);
//				}else
				if(setIhmOldEnteteDocumentRefresh())
					dao.modifier(taDocument);
			}
			}
			if(dao.dataSetEnModif())
				fireChangementMaster(new ChangementMasterEntityEvent(this,taDocument,true));
			initEtatBouton(false);
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		}
	}

	@Override
	protected void actSupprimer() throws Exception {
		boolean verrouLocal = VerrouInterface.isVerrouille();
		VerrouInterface.setVerrouille(true);
		EntityTransaction transaction = dao.getEntityManager().getTransaction();
		
		try {
			if(isUtilise())MessageDialog.openInformation(vue.getShell(), fr.legrain.tiers.ecran.MessagesEcran
					.getString("Message.Attention"), fr.legrain.tiers.ecran.MessagesEcran
					.getString("Message.suppression"));
			else
			if (MessageDialog.openConfirm(vue.getShell(),
							MessagesEcran.getString("Message.Attention"),
							MessagesEcran.getString("Document.Message.Supprimer"))) {

				dao.begin(transaction);
				TaBonliv u = dao.findById(((IHMEnteteBonliv) selectedBonliv).getIdDocument());
				dao.supprimer(u);
				dao.commit(transaction);
				removeCodeDocument( u);
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
		return taDocument!=null&&!dao.autoriseModification(taDocument);		
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
			String codeDocument= vue.getTfCODE_DOCUMENT().getText();
			boolean message = messageAffiche && !((IHMEnteteBonliv) selectedBonliv).BonlivEstVide(taDocument.dateDansExercice(new Date()));
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
								dao.annulerCodeGenere(((IHMEnteteBonliv) selectedBonliv).getCodeDocument(),Const.C_CODE_DOCUMENT);
								dao.annulerCodeGenere(codeDocument,Const.C_CODE_DOCUMENT);
								
								dao.annuler(taDocument);
								hideDecoratedFields();

								if (vue.getTfCODE_DOCUMENT().isFocusControl()
										&& !message && !annulationForcee) {
									// si facture vide avant annulation
									// et que l'on veut fermer
									actFermer();
									dejaFermer = true;
								} else {
									initialisationEcran(codeDocument);									
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
							dao.annulerCodeGenere(((IHMEnteteBonliv) selectedBonliv).getCodeDocument(),Const.C_CODE_DOCUMENT);
							dao.annulerCodeGenere(codeDocument,Const.C_CODE_DOCUMENT);
							dao.annuler(taDocument);
							if (!dejaFermer) {
								initialisationEcran(codeDocument);
								if (insertion) actInserer();
							}
							hasAnnuler = true;
						} else if (!hasAnnuler && repondu) {
							dao.annulerCodeGenere(((IHMEnteteBonliv) selectedBonliv).getCodeDocument(),Const.C_CODE_DOCUMENT);
							dao.annulerCodeGenere(codeDocument,Const.C_CODE_DOCUMENT);
							dao.annuler(taDocument);
							initialisationEcran(codeDocument);
							hasAnnuler = true;
						}
						break;
					case C_MO_CONSULTATION:
						dao.annulerCodeGenere(((IHMEnteteBonliv) selectedBonliv).getCodeDocument(),Const.C_CODE_DOCUMENT);
						dao.annulerCodeGenere(codeDocument,Const.C_CODE_DOCUMENT);

						if (vue.getTfCODE_DOCUMENT().isFocusControl() && !message) {// si facture vide avant annulation
							actFermer();
							dejaFermer = true;
						} else {
							initialisationEcran(codeDocument);
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
	protected void actImprimer()throws Exception {

		final String simpleNameClass = TaBonliv.class.getSimpleName();
		Collection<TaBonliv> collectionTaBonliv = new LinkedList<TaBonliv>();
		TaBonliv documentLocale=(TaBonliv)taDocument;
		
		collectionTaBonliv.add(documentLocale);
		ConstEdition constEdition = new ConstEdition(getEm()); 
		constEdition.setFlagEditionMultiple(true);
//		impressionBonliv.setObject(taDocument);
//		impressionBonliv.setConstEdition(constEdition);
//		impressionBonliv.setCollection(collectionTaBonliv);
		
		Bundle bundleCourant = BonLivraisonPlugin.getDefault().getBundle();
		final String namePlugin = bundleCourant.getSymbolicName();
		
//		impressionBonliv.imprimerSelection(taDocument.getIdDocument(),taDocument.getCodeDocument(),true,
//				ConstEdition.FICHE_FILE_REPORT_BONLIV,namePlugin,TaBonliv.class.getSimpleName());
		
		/** 01/03/2010 modifier les editions (zhaolin) **/
		baseImpressionEdition.setObject(documentLocale);
		baseImpressionEdition.setConstEdition(constEdition);
		baseImpressionEdition.setCollection(collectionTaBonliv);
		baseImpressionEdition.setIdEntity(documentLocale.getIdDocument());
		
		//information pour l'envoie de document par email
		baseImpressionEdition.setInfosEmail(null);
		String email = null;
		if(documentLocale.getTaTiers().getTaEmail()!=null && documentLocale.getTaTiers().getTaEmail().getAdresseEmail()!=null) {
			email = documentLocale.getTaTiers().getTaEmail().getAdresseEmail();
		}
		baseImpressionEdition.setInfosEmail(
				new InfosEmail(
						new String[]{email},
						null,
						documentLocale.getCodeDocument()+".pdf")
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
		
		final IPreferenceStore preferenceStore = BonLivraisonPlugin.getDefault().getPreferenceStore();
//        Display.getDefault().syncExec(new Runnable() {
//            @Override
//            public void run() { 
            	baseImpressionEdition.impressionEdition(preferenceStore,simpleNameClass,/*true,*/null,
												namePlugin,ConstEdition.FICHE_FILE_REPORT_BONLIV, 
												true,false,true,false,false,false,"");
//            }});
		/************************************************/

	}


	@Override
	protected boolean aideDisponible() {
		boolean result = false;
		switch ((PaEditorBLController.this.dao.getModeObjet().getMode())) {
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

				switch ((PaEditorBLController.this.dao.getModeObjet().getMode())) {
				case C_MO_CONSULTATION:
					if (getFocusCourantSWT().equals(vue.getTfCODE_DOCUMENT())) {
						//permet de paramètrer l'affichage remplie ou non de l'aide
						paramAfficheAideRecherche.setForceAffichageAideRemplie(false);
						editorCreationId = EditorBonliv.ID_EDITOR;
						editorInputCreation = new EditorInputBonliv();

						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
						controllerEcranCreation = this;
						
						paramAfficheAideRecherche.setAfficheDetail(false);
						
						paramAfficheAideRecherche.setTypeEntite(TaBonliv.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_DOCUMENT);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_DOCUMENT().getText());
						paramAfficheAideRecherche.setControllerAppelant(PaEditorBLController.this);
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideBonliv,TaBonlivDAO,TaBonliv>(dao,IHMAideBonliv.class));
						paramAfficheAideRecherche.setTypeObjet(IHMAideBonliv.class);
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_DOCUMENT);
						try {
							dao.annulerCodeGenere(taDocument.getCodeDocument(), Const.C_CODE_DOCUMENT);
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
						editorCreationId =TiersMultiPageEditor.ID_EDITOR;
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
						editorCreationId = EditorBonliv.ID_EDITOR;
						editorInputCreation = new EditorInputBonliv();
						paramAfficheAideRecherche.setForceAffichageAideRemplie(false);
						paramAfficheAideRecherche.setAfficheDetail(false);

						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
						controllerEcranCreation = this;

						paramAfficheAideRecherche.setTypeEntite(TaBonliv.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_DOCUMENT);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_DOCUMENT().getText());
						paramAfficheAideRecherche.setControllerAppelant(PaEditorBLController.this);
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideBonliv,TaBonlivDAO,TaBonliv>(dao,IHMAideBonliv.class));
						paramAfficheAideRecherche.setTypeObjet(IHMAideBonliv.class);
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_DOCUMENT);
					}
					if (focusDansAdresse(getFocusCourantSWT())
							|| focusDansAdresse_Liv(getFocusCourantSWT())) {
						PaAdresseSWT paAdresseSWT = new PaAdresseSWT(s2,SWT.NULL);
						SWTPaAdresseController swtPaAdresseController = new SWTPaAdresseController(paAdresseSWT);

//						editorCreationId = EditorAdresse.ID;
//						editorInputCreation = new EditorInputAdresse();
						paramAfficheAideRecherche.setAfficheDetail(false);
						paramAfficheAideRecherche.setForceAffichageAideRemplie(true);


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
//						l.add(taBonliv.getTaCPaiement());
						if(taDocument.getTaTiers()!=null)
							l.add(taDocument.getTaTiers().getTaCPaiement());
						TaTCPaiementDAO taTCPaiementDAO = new TaTCPaiementDAO(getEm());
						if(taTCPaiementDAO.findByCode(TaTCPaiement.C_CODE_TYPE_BON_LIVRAISON)!=null
								&& taTCPaiementDAO.findByCode(TaTCPaiement.C_CODE_TYPE_BON_LIVRAISON).getTaCPaiement()!=null)
								l.add(taTCPaiementDAO.findByCode(TaTCPaiement.C_CODE_TYPE_BON_LIVRAISON).getTaCPaiement());
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
	

//	@Override
//	protected void actAide(String message) throws Exception {
//		//boolean aide = getActiveAide();
//		//setActiveAide(true);
//		if (aideDisponible()) {
//			setActiveAide(true);
//			boolean verrouLocal = VerrouInterface.isVerrouille();
//			VerrouInterface.setVerrouille(true);
//			try {
//				vue.setCursor(Display.getCurrent().getSystemCursor(
//						SWT.CURSOR_WAIT));
//				ParamAfficheAideRechercheSWT paramAfficheAideRecherche = new ParamAfficheAideRechercheSWT();
//				paramAfficheAideRecherche.setDb(getThis().getIbTaTable()
//						.getFIBBase());
//				paramAfficheAideRecherche.setMessage(message);
//				// Creation de l'ecran d'aide principal
//				Shell s = new Shell(PlatformUI.getWorkbench()
//						.getActiveWorkbenchWindow().getShell(),
//						LgrShellUtil.styleLgr);
//				PaAideSWT paAide = new PaAideSWT(s, SWT.NONE);
//				SWTPaAideControllerSWT paAideController = new SWTPaAideControllerSWT(
//						paAide);
//				/** ************************************************************ */
//				LgrPartListener.getLgrPartListener().setLgrActivePart(null);
//				IEditorPart e = PlatformUI.getWorkbench()
//						.getActiveWorkbenchWindow().getActivePage().openEditor(
//								new EditorInputAide(), EditorAide.ID);
//				LgrPartListener.getLgrPartListener().setLgrActivePart(e);
//				LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
//				paAideController = new SWTPaAideControllerSWT(((EditorAide) e)
//						.getComposite());
//				((LgrEditorPart) e).setController(paAideController);
//				((LgrEditorPart) e).setPanel(((EditorAide) e).getComposite());
//				/** ************************************************************ */
//				SWTBaseControllerSWTStandard controllerEcranCreation = null;
//				ParamAffiche parametreEcranCreation = null;
//				IEditorPart editorCreation = null;
//				String editorCreationId = null;
//				IEditorInput editorInputCreation = null;
//				Shell s2 = new Shell(s, LgrShellUtil.styleLgr);
//
//				switch (ibTaTable.getFModeObjet().getMode()) {
//				case C_MO_CONSULTATION:
//					if (getFocusCourantSWT().equals(vue.getTfCODE_BONLIV())) {
//
//						editorCreationId = EditorBonliv.ID_EDITOR;
//						editorInputCreation = new EditorInputBonliv();
//
//						SWT_IB_TA_BONLIV ibTaTableBonliv = new SWT_IB_TA_BONLIV(
//								swtBonliv, false);
//						ibTaTableBonliv.getFIBQuery().setQuery(
//								new QueryDescriptor(ibTaTableBonliv
//										.getFIBBase(), Const.C_Debut_Requete
//										+ Const.C_NOM_VU_BONLIV_REDUIT, true));
//						ibTaTableBonliv.ouvreDataset();
//						paramAfficheAideRecherche.setQuery(ibTaTableBonliv
//								.getFIBQuery());
//						controllerEcranCreation = this;
//
//						paramAfficheAideRecherche
//								.setChampsRecherche(Const.C_CODE_BONLIV);
//						paramAfficheAideRecherche.setDebutRecherche(vue
//								.getTfCODE_BONLIV().getText());
//						paramAfficheAideRecherche
//								.setControllerAppelant(getThis());
//						paramAfficheAideRecherche
//								.setModel(new ModelGeneral<IHMAideBonliv>(
//										ibTaTableBonliv.getFIBQuery(),
//										IHMAideBonliv.class));
//						paramAfficheAideRecherche
//								.setTypeObjet(IHMAideBonliv.class);
//						paramAfficheAideRecherche
//								.setChampsIdentifiant(ibTaTableBonliv.champIdTable);
//						try {
//							ibTaTable.annulerCodeGenere(swtBonliv.getEntete()
//									.getCODE(), Const.C_CODE_BONLIV);
//							ibTaTable.annulerCodeGenere(ibTaTable
//									.getChamp_Obj(Const.C_CODE_BONLIV),
//									Const.C_CODE_BONLIV);
//						} catch (Exception ex) {
//							logger.error(ex);
//						}
//					}
//					if (getFocusCourantSWT().equals(vue.getTfCODE_TIERS())) {
//						PaTiersSWT paTiersSWT = new PaTiersSWT(s2, SWT.NULL);
//						SWTPaTiersController swtPaTiersController = new SWTPaTiersController(
//								paTiersSWT);
//
//						editorCreationId = EditorTiers.ID;
//						editorInputCreation = new EditorInputTiers();
//
//						ParamAfficheTiers paramAfficheTiers = new ParamAfficheTiers();
//						swtPaTiersController
//						.getIbTaTable().setPreferences(BonLivraisonPlugin.getDefault().getPreferenceStore().
//								getString(PreferenceConstants.TYPE_TIERS_DOCUMENT));
//						swtPaTiersController.getIbTaTable().setFDescriptor(
//								new QueryDescriptor(swtPaTiersController.getIbTaTable().getFIBBase()
//										,swtPaTiersController.getIbTaTable().getRequete()+
//										swtPaTiersController.getIbTaTable().clauseWhereTypeTiers("")+
//										swtPaTiersController.getIbTaTable().getRequeteFin(),true));
//						swtPaTiersController.getIbTaTable().getFIBQuery().close();
//						swtPaTiersController.getIbTaTable().getFIBQuery().setQuery(
//								swtPaTiersController.getIbTaTable().getFDescriptor());
//						
//						swtPaTiersController.getIbTaTable().ouvreDataset();
//						paramAfficheAideRecherche.setQuery(swtPaTiersController
//								.getIbTaTable().getFIBQuery());
//						paramAfficheTiers
//								.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//						paramAfficheTiers.setEcranAppelant(paAideController);
//						controllerEcranCreation = swtPaTiersController;
//						parametreEcranCreation = paramAfficheTiers;
//
//						paramAfficheAideRecherche
//								.setChampsRecherche(Const.C_CODE_TIERS);
//						paramAfficheAideRecherche.setDebutRecherche(vue
//								.getTfCODE_TIERS().getText());
//						paramAfficheAideRecherche
//								.setControllerAppelant(getThis());
//						ModelTiers modelTiers = new ModelTiers(
//								swtPaTiersController.getIbTaTable());
//						paramAfficheAideRecherche.setModel(modelTiers);
//						// paramAfficheAideRecherche.setModel(new
//						// ModelGeneral<SWTTiers>(swtPaTiersController.getIbTaTable().getFIBQuery(),SWTTiers.class));
//						paramAfficheAideRecherche
//								.setTypeObjet(swtPaTiersController
//										.getClassModel());
//						paramAfficheAideRecherche
//								.setChampsIdentifiant(swtPaTiersController
//										.getIbTaTable().champIdTable);
//					}
//
//					break;
//				case C_MO_EDITION:
//				case C_MO_INSERTION:
//					if (getFocusCourantSWT().equals(vue.getTfCODE_TIERS())) {
//						PaTiersSWT paTiersSWT = new PaTiersSWT(s2, SWT.NULL);
//						SWTPaTiersController swtPaTiersController = new SWTPaTiersController(
//								paTiersSWT);
//
//						editorCreationId = EditorTiers.ID;
//						editorInputCreation = new EditorInputTiers();
//
//						ParamAfficheTiers paramAfficheTiers = new ParamAfficheTiers();
//						swtPaTiersController
//						.getIbTaTable().setPreferences(BonLivraisonPlugin.getDefault().getPreferenceStore().
//								getString(PreferenceConstants.TYPE_TIERS_DOCUMENT));
//						swtPaTiersController.getIbTaTable().setFDescriptor(
//								new QueryDescriptor(swtPaTiersController.getIbTaTable().getFIBBase()
//										,swtPaTiersController.getIbTaTable().getRequete()+
//										swtPaTiersController.getIbTaTable().clauseWhereTypeTiers("")+
//										swtPaTiersController.getIbTaTable().getRequeteFin(),true));
//						swtPaTiersController.getIbTaTable().getFIBQuery().close();
//						swtPaTiersController.getIbTaTable().getFIBQuery().setQuery(
//								swtPaTiersController.getIbTaTable().getFDescriptor());
//						
//						swtPaTiersController.getIbTaTable().ouvreDataset();
//						paramAfficheAideRecherche.setQuery(swtPaTiersController
//								.getIbTaTable().getFIBQuery());
//						paramAfficheTiers
//								.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//						paramAfficheTiers.setEcranAppelant(paAideController);
//						controllerEcranCreation = swtPaTiersController;
//						parametreEcranCreation = paramAfficheTiers;
//
//						paramAfficheAideRecherche
//								.setChampsRecherche(Const.C_CODE_TIERS);
//						paramAfficheAideRecherche.setDebutRecherche(vue
//								.getTfCODE_TIERS().getText());
//						paramAfficheAideRecherche
//								.setControllerAppelant(getThis());
//						ModelTiers modelTiers = new ModelTiers(
//								swtPaTiersController.getIbTaTable());
//						paramAfficheAideRecherche.setModel(modelTiers);
//						// paramAfficheAideRecherche.setModel(new
//						// ModelGeneral<SWTTiers>(swtPaTiersController.getIbTaTable().getFIBQuery(),SWTTiers.class));
//						paramAfficheAideRecherche
//								.setTypeObjet(swtPaTiersController
//										.getClassModel());
//						paramAfficheAideRecherche
//								.setChampsIdentifiant(swtPaTiersController
//										.getIbTaTable().champIdTable);
//					}
//					if (getFocusCourantSWT().equals(vue.getTfCODE_BONLIV())) {
//
//						editorCreationId = EditorBonliv.ID_EDITOR;
//						editorInputCreation = new EditorInputBonliv();
//
//						SWT_IB_TA_BONLIV ibTaTableBonliv = new SWT_IB_TA_BONLIV(
//								swtBonliv, false);
//						ibTaTableBonliv.getFIBQuery().setQuery(
//								new QueryDescriptor(ibTaTableBonliv
//										.getFIBBase(), Const.C_Debut_Requete
//										+ Const.C_NOM_VU_BONLIV_REDUIT, true));
//						paramAfficheAideRecherche.setQuery(ibTaTableBonliv
//								.getFIBQuery());
//						paramAfficheAideRecherche
//								.setCleListeTitre(nomClassController);
//						controllerEcranCreation = null;
//
//						paramAfficheAideRecherche
//								.setChampsRecherche(Const.C_CODE_BONLIV);
//						paramAfficheAideRecherche.setDebutRecherche(vue
//								.getTfCODE_BONLIV().getText());
//						paramAfficheAideRecherche
//								.setControllerAppelant(getThis());
//						paramAfficheAideRecherche
//								.setModel(new ModelGeneral<IHMAideBonliv>(
//										ibTaTableBonliv.getFIBQuery(),
//										IHMAideBonliv.class));
//						paramAfficheAideRecherche
//								.setTypeObjet(IHMAideBonliv.class);
//						paramAfficheAideRecherche
//								.setChampsIdentifiant(ibTaTableBonliv.champIdTable);
//					}
//					// if
//					// (getFocusCourantSWT().equals(vue.getTfCODE_T_PAIEMENT())){
//					//
//					// //editorCreationId = EditortyTiers.ID;
//					// //editorInputCreation = new EditorInputTiers();
//					//
//					// SWT_IB_TA_T_PAIEMENT ibTaTPaiement = new
//					// SWT_IB_TA_T_PAIEMENT();
//					// paramAfficheAideRecherche.setQuery(ibTaTPaiement.getFIBQuery());
//					// paramAfficheAideRecherche.setControllerAppelant(getThis());
//					// paramAfficheAideRecherche.setModel(new
//					// ModelGeneral<SWTTPaiement>(ibTaTPaiement.getFIBQuery(),SWTTPaiement.class));
//					// paramAfficheAideRecherche.setTypeObjet(SWTTPaiement.class);
//					// paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_T_PAIEMENT);
//					// paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_T_PAIEMENT().getText());
//					// paramAfficheAideRecherche.setChampsIdentifiant(ibTaTPaiement.champIdTable);
//					// controllerEcranCreation = null;
//					// paramAfficheAideRecherche.setCleListeTitre(SWTTPaiement.class.getSimpleName());
//					// }
//
//					if (focusDansAdresse(getFocusCourantSWT())
//							|| focusDansAdresse_Liv(getFocusCourantSWT())) {
//						PaAdresseSWT paAdresseSWT = new PaAdresseSWT(s2,
//								SWT.NULL);
//						SWTPaAdresseController swtPaAdresseController = new SWTPaAdresseController(
//								paAdresseSWT);
//
//						editorCreationId = EditorAdresse.ID;
//						editorInputCreation = new EditorInputAdresse();
//
//						ParamAfficheAdresse paramAfficheAdresse = new ParamAfficheAdresse();
//						paramAfficheAideRecherche
//								.setQuery(swtPaAdresseController.getIbTaTable()
//										.getFIBQuery());
//						paramAfficheAdresse
//								.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//						paramAfficheAdresse.setEcranAppelant(paAideController);
//						paramAfficheAdresse.setIdTiers(swtBonliv.getEntete()
//								.getIdTiers());
//						Map map = swtPaAdresseController.getIbTaTable().getParamWhereSQL();
//						if(map==null)map=new HashMap();
//						if (!map.containsKey(Const.C_ID_TIERS))					
//							map.put(Const.C_ID_TIERS,new String[]{"=",LibConversion.integerToString(paramAfficheAdresse.getIdTiers())});
//							swtPaAdresseController.getIbTaTable().setParamWhereSQL(map);
//						controllerEcranCreation = swtPaAdresseController;
//						parametreEcranCreation = paramAfficheAdresse;
//
//						paramAfficheAideRecherche
//								.setChampsRecherche(Const.C_ADRESSE1_ADRESSE);
//						paramAfficheAideRecherche.setDebutRecherche("");
//						paramAfficheAideRecherche
//								.setControllerAppelant(getThis());
//						paramAfficheAideRecherche
//								.setModel(new ModelGeneral<SWTAdresse>(
//										swtPaAdresseController.getIbTaTable()
//												.getFIBQuery(),
//										SWTAdresse.class));
//						paramAfficheAideRecherche
//								.setTypeObjet(swtPaAdresseController
//										.getClassModel());
//						paramAfficheAideRecherche
//								.setChampsIdentifiant(swtPaAdresseController
//										.getIbTaTable().champIdTable);
//					}
//
//					if (focusDansCPaiment(getFocusCourantSWT())) {
//						PaConditionPaiementSWT paCPaiementSWT = new PaConditionPaiementSWT(
//								s2, SWT.NULL);
//						SWTPaConditionPaiementController swtPaConditionPaiementController = new SWTPaConditionPaiementController(
//								paCPaiementSWT);
//
//						editorCreationId = EditorConditionPaiement.ID;
//						editorInputCreation = new EditorInputConditionPaiement();
//
//						ParamAfficheConditionPaiement paramAfficheConditionPaiement = new ParamAfficheConditionPaiement();
//						paramAfficheAideRecherche
//								.setQuery(swtPaConditionPaiementController
//										.getIbTaTable().getFIBQuery());
//						paramAfficheConditionPaiement
//								.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//						paramAfficheConditionPaiement
//								.setEcranAppelant(paAideController);
//						paramAfficheConditionPaiement.setIdTiers(swtBonliv
//								.getEntete().getIdTiers());
//						controllerEcranCreation = swtPaConditionPaiementController;
//						parametreEcranCreation = paramAfficheConditionPaiement;
//
//						paramAfficheAideRecherche
//								.setChampsRecherche(Const.C_CODE_C_PAIEMENT);
//						paramAfficheAideRecherche.setDebutRecherche("");
//						paramAfficheAideRecherche
//								.setControllerAppelant(getThis());
//						paramAfficheAideRecherche
//								.setModel(new ModelGeneral<SWTCPaiement>(
//										swtPaConditionPaiementController
//												.getIbTaTable().getFIBQuery(),
//										SWTCPaiement.class));
//						paramAfficheAideRecherche
//								.setTypeObjet(swtPaConditionPaiementController
//										.getClassModel());
//						paramAfficheAideRecherche
//								.setChampsIdentifiant(swtPaConditionPaiementController
//										.getIbTaTable().champIdTable);
//					}
//
//					break;
//				default:
//					break;
//				}
//
//				if (paramAfficheAideRecherche.getQuery() != null) {
//
//					PaAideRechercheSWT paAideRecherche1 = new PaAideRechercheSWT(
//							s, SWT.NULL);
//					SWTPaAideRechercheControllerSWT paAideRechercheController1 = new SWTPaAideRechercheControllerSWT(
//							paAideRecherche1);
//
//					// Parametrage de la recherche
//					paramAfficheAideRecherche
//							.setFocusSWT(((PaAideRechercheSWT) paAideRechercheController1
//									.getVue()).getTfChoix());
//					paramAfficheAideRecherche
//							.setRefCreationSWT(controllerEcranCreation);
//					paramAfficheAideRecherche.setEditorCreation(editorCreation);
//					paramAfficheAideRecherche
//							.setEditorCreationId(editorCreationId);
//					paramAfficheAideRecherche
//							.setEditorInputCreation(editorInputCreation);
//					paramAfficheAideRecherche
//							.setParamEcranCreation(parametreEcranCreation);
//					paramAfficheAideRecherche.setShellCreation(s2);
//					paAideRechercheController1
//							.configPanel(paramAfficheAideRecherche);
//					// paramAfficheAideRecherche.setFocusDefaut(paAideRechercheController1.getVue().getTfChoix());
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
//					paAideController.addRetourEcranListener(getThis());
//					// affichage de l'ecran d'aide principal (+ ses recherches)
//					setActiveAide(true);// permet de savoir que l'aide à été
//										// déclenchée pour ne pas
//					// que la perte du focus redéclenche l'aide si erreur trouvé
//					// dans le control qui a le focus.
//
//					// LgrShellUtil.afficheAideSWT(paramAfficheAide, null,
//					// paAide,paAideController, s);
//					/** ************************************************************** */
//					paAideController.configPanel(paramAfficheAide);
//					/** ************************************************************** */
//					// je rends enable false tous les boutons avant de passer
//					// dans l'écran d'aide
//					// pour ne pas que les actions de l'écran des factures
//					// interfèrent ceux de l'écran d'aide
//					// activeWorkenchPartCommands(false);
//				}
//
//			} finally {
//				VerrouInterface.setVerrouille(verrouLocal);
//				setActiveAide(false);
//				vue.setCursor(Display.getCurrent().getSystemCursor(
//						SWT.CURSOR_ARROW));
//			}
//		}
//		setActiveAide(aide);
//	}
//
	public void initInfosBonliv() {
		if(taInfosDocument==null) {
			taInfosDocument = new TaInfosBonliv();
		}
		if(taDocument!=null) {
			taInfosDocument.setTaDocument(taDocument);
			taDocument.setTaInfosDocument(taInfosDocument);
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
							for (TaLBonliv ligne : taDocument.getLignes()) {
								ligne.setLegrain(false);
							}
							controllerLigne.ctrlTouslesChampsToutesLesLignes();							
						}catch (Exception e) {
							actSuivant();
							throw new Exception();
						}
						taDocument.calculeTvaEtTotaux();
						initInfosBonliv();

//						if(declanchementInterne) {
						verifCode();
							ctrlTousLesChampsAvantEnregistrementSWT(dbcAdresseFact);
							ctrlTousLesChampsAvantEnregistrementSWT(dbcAdresseLiv);
							ctrlTousLesChampsAvantEnregistrementSWT();
//						}
						
						dao.begin(transaction);

						mapperUIToModel.map((IHMEnteteBonliv) selectedBonliv, taDocument);
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
						for (TaLBonliv ligne : taDocument.getLignes()) {
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

	public IHMEnteteBonliv getIhmOldEnteteBonliv() {
		return ihmOldEnteteBonliv;
	}

	public void setIhmOldEnteteBonliv(IHMEnteteBonliv ihmOldEnteteBonliv) {
		this.ihmOldEnteteBonliv = ihmOldEnteteBonliv;
	}

	public void setIhmOldEnteteBonliv() {
		if (selectedBonliv != null)
			this.ihmOldEnteteBonliv = IHMEnteteBonliv
					.copy((IHMEnteteBonliv) selectedBonliv);
		else {
		}
	}

	public void setVue(PaEditorBL vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();

		mapComposantDecoratedField.put(vue.getTfCODE_DOCUMENT(), vue
				.getFieldCODE_DOCUMENT());
		mapComposantDecoratedField.put(vue.getTfCODE_TIERS(), vue
				.getFieldCODE_TIERS());
		 mapComposantDecoratedField.put(vue.getDateTimeDATE_DOCUMENT(),
		 vue.getFieldDATE_DOCUMENT());
		 mapComposantDecoratedField.put(vue.getDateTimeDATE_LIV_DOCUMENT(),
		 vue.getFieldDATE_LIV_DOCUMENT());
		mapComposantDecoratedField.put(vue.getTfLIBELLE_DOCUMENT(), vue
				.getFieldLIBELLE_DOCUMENT());
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
					|| (vue.getPaBtnAvecAssistant().getPaBtn().getBtnAnnuler()
							.isFocusControl()) || (vue.getPaBtnAvecAssistant()
					.getPaBtn().getBtnFermer().isFocusControl()))) {
				//ctrlTousLesChampsAvantEnregistrementSWT();
				verifCode();
				validateUI();
			}
			VerrouInterface.setVerrouille(true);
			if (vue.getTfCODE_TIERS().equals(getFocusCourantSWT())) {
				changementTiers(true);
			}
			if (vue.getCbTTC().equals(getFocusCourantSWT())
					&& (vue.getCbTTC().isEnabled())) {
				controllerLigne.initTTC();
			}

//			if (focusDansDate()&& 
//					!((vue.getPaBtnAvecAssistant().getPaBtn().getBtnAnnuler()
//					.isFocusControl()) || (vue.getPaBtnAvecAssistant()
//							.getPaBtn().getBtnFermer().isFocusControl()))) {
//				ctrlUnChampsSWT(getFocusCourantSWT());
//			}
		} catch (Exception e) {
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
			if (taDocument!=null && selectedBonliv!=null && (IHMEnteteBonliv) selectedBonliv!=null) {
				mapperModelToUI.map(taDocument, (IHMEnteteBonliv) selectedBonliv);
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
		mapperModelToUI.map(taDocument, ((IHMEnteteBonliv) selectedBonliv));
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
//				codeBonlivContentProposal.setContentProposalProvider(contentProposalProviderDocument());
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
		String[][] adapterDocument = initAdapterDocument();
		String[] listeCodeDocument = null;
		String[] listeLibelleDocument = null;
		if (adapterDocument != null) {
			listeCodeDocument = new String[adapterDocument.length];
			listeLibelleDocument = new String[adapterDocument.length];
			for (int i = 0; i < adapterDocument.length; i++) {
				listeCodeDocument[i] = adapterDocument[i][0];
				listeLibelleDocument[i] = adapterDocument[i][1];
			}
		}

		return new ContentProposalProvider(listeCodeDocument,
				listeLibelleDocument);
		
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


	public PaLigneBLController getControllerLigne() {
		return controllerLigne;
	}

	public void setControllerLigne(PaLigneBLController controllerLigne) {
		this.controllerLigne = controllerLigne;
		this.controllerLigne.setParentEcran(this);
	}

	private Boolean remonterDocument(String codeDocument) throws Exception {
		try {
			boolean res = false;
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
				factureVide = ((IHMEnteteBonliv) selectedBonliv).BonlivEstVide(taDocument.dateDansExercice(new Date()));
			}
			TaBonliv documentRecherche = rechercheBonliv(codeDocument, true, factureVide);
			if (documentRecherche!=null) {
				taDocument = documentRecherche;	
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
				
				controllerTotaux.remonterDocument();
			}
			return res;
		} finally {
			activeModifytListener();
			initEtatBouton(true);
		}

	}

//	public boolean nouveauBonliv(EnumModeObjet mode, String codeBonliv)
//			throws Exception {
//		boolean resultat = true;
//		boolean verrouLocal = VerrouInterface.getVerrouInterface()
//				.isVerrouille();
//		String codeTmp = ((IHMEnteteBonliv) selectedBonliv)
//				.getCodeBonliv();
//		try {
//			// si pièce déjà en mode Edition ou Insertion -> annuler
//			if (ibTaTable.dataSetEnModif())
//				actAnnuler(true);
//
//			if (!ibTaTable.dataSetEnModif()) {
//				// si pièce en consultation
//				VerrouInterface.getVerrouInterface().setVerrouille(true);
//				desactiveModifyListener();
//				swtBonliv = new SWTBonliv(codeBonliv);
//				this.swtBonliv.addModificationDocumentListener(this);
//				this.swtBonliv.addModificationDocumentListener(ibTaTable);
//
//				this.swtBonliv.addChangeModeListener(this);
//				ibTaTable.setBonliv(swtBonliv);
//				controllerLigne.setSwtBonliv(swtBonliv);
//				controllerTotaux.setSwtBonliv(swtBonliv);
//				controllerLigne.getIbTaTable().setBonliv(swtBonliv);
//				controllerLigne.getIbTaTable().videTableTemp();
//				controllerLigne.initLigneCourantSurRow();
//				controllerLigne.getIbTaTable().initLigneCourantSurRow();
//				swtBonliv.getLignes().clear();
//				swtBonliv.getLignesTVA().clear();
//				ibTaTable.annulerCodeGenere(codeTmp, Const.C_CODE_BONLIV);
//				if (codeBonliv.equals("")) {
//					codeBonliv = ibTaTable.genereCode();
//					if (codeBonliv.equals(""))
//						throw new ExceptLgr(
//								MessagesGestCom
//										.getString("IB_TA_BONLIV.Message.CodeIncoherent"),
//								0, true, 0);
//					ibTaTable.rentreCodeGenere(codeBonliv,
//							Const.C_CODE_BONLIV);
//				}
//				((IHMEnteteBonliv) selectedBonliv)
//						.setCodeBonliv(codeBonliv);
//				vue.getTfCODE_BONLIV().setText(codeBonliv);
//				switch (mode) {
//				case C_MO_INSERTION:
//					actInserer();
//					// récupérer code validé dans facture, dataset et interface
//					break;
//				case C_MO_EDITION:
//					// actModifier(null);
//					// récupérer code validé dans facture, dataset et interface
//					// récupérer entête dans objet facture
//					ibTaTable.remplissageEnteteSurObjetQuery(null, null);
//					ibTaTable.getQuery_Champ_Obj();
//					controllerLigne.getIbTaTable().recupLignesBonliv(
//							codeBonliv);
//					break;
//				default:
//					break;
//				}
//
//				// récupérer les lignes dans objet facture
//				// récupérer les totaux
//
//				if (!controllerLigne.getIbTaTable().getFIBQuery().isEmpty()) {
//					controllerLigne.getIbTaTable().getFIBQuery().first();
//					do {
//						controllerLigne.initLigneCourantSurRow();
//						controllerLigne.getIbTaTable().initLigneCourantSurRow();
//						swtBonliv.addLigne(new SWTLigneBonliv(swtBonliv
//								.getLigneCourante()));
//						controllerLigne.getIbTaTable()
//								.remplissageLigneSurObjetQuery(
//										swtBonliv.getLigneCourante());
//						// ibTaTable.modificationDocument(new
//						// ModificationDocumentEvent(this,facture.getLigne(facture.getLigneCourante())));
//					} while (controllerLigne.getIbTaTable().getFIBQuery()
//							.next());
//					ibTaTable
//							.modificationDocument(new SWTModificationDocumentEvent(
//									this, swtBonliv.getLigne(swtBonliv
//											.getLigneCourante())));
//					modificationDocument(new SWTModificationDocumentEvent(this,
//							swtBonliv.getLigne(swtBonliv.getLigneCourante())));
//					controllerLigne.getIbTaTable().getFIBQuery().first();
//				}
//
//				// actualiser l'écran entête
//				// remplirInterface(false);
//				// actualiser l'écran des lignes
//				// controllerLigne.remplirInterface(false);
//
//				this.swtBonliv
//						.addModificationDocumentListener(this.controllerLigne);
//
//				((IHMEnteteBonliv) selectedBonliv)
//						.setIHMEnteteBonliv(((SWTEnteteBonliv) swtBonliv
//								.getEntete()));
//
//				// récupérer code validé dans facture, dataset et interface
//				swtBonliv.getEntete().setCODE(codeBonliv);
//				if (!ibTaTable.verifChamp(Const.C_CODE_BONLIV,
//						((IHMEnteteBonliv) selectedBonliv)
//								.getCodeBonliv(), null, null))
//					throw new ExceptLgr();
//				vue.getTfCODE_BONLIV().setText(codeBonliv);
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

	// private boolean validationCodeFacture(String codeFacture){
	// boolean res = false;
	// try {
	// if(!swtFacture.getOldCODE().equals(codeFacture)
	// ||(swtFacture.getOldCODE().equals(SWTDocument.C_OLD_CODE_INITIAL))) {
	// try {//si changement de code facture
	// //
	// ibTaTable.annulerCodeGenere(facture.getEntete().getCODE(),"validationCodeFacture");
	// if (ibTaTable.dataSetEnModif())
	// actAnnuler(true);
	// if(controllerLigne.getIbTaTable().dataSetEnModif())
	// controllerLigne.actAnnuler();
	// if(!ibTaTable.dataSetEnModif()){
	// if(ibTaTable.rechercheFacture(codeFacture,true,((IHMEnteteFacture)selectedEnteteFacture).FactureEstVide(dateDansExercice(new
	// Date())))) {
	// selectedEnteteFacture = modelEnteteFacture.getListeObjet().getFirst();
	// // si code existant -> remonterDocument (code existant "CodeFacture")
	// if (remonterDocument(codeFacture))
	// res=true;
	// }else {//si nouveau code
	// // -> nouvelle facture (Insertion, nouveau code "CodeFacture")
	// if (nouvelleFacture(EnumModeObjet.C_MO_INSERTION,codeFacture))
	// res=true;
	// }
	// if (!res)throw new Exception();
	// swtFacture.setOldCODE(codeFacture);
	// // ibTaTable.rentreCodeGenere(codeFacture,"validationCodeFacture");
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

//	public String[][] initAdapterDocument() {
//		String[][] valeurs = null;
//		boolean affichageCtrlEspace = GestionCommercialePlugin.getDefault().getPreferenceStore().getBoolean(fr.legrain.gestionCommerciale.preferences.PreferenceConstants.AFFICHAGE_CTRL_ESPACE);
//		if(affichageCtrlEspace){
//		if(listeDocument==null){
//			TaBonlivDAO taBonlivDAO = new TaBonlivDAO(getEm());
//			TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
//			TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
//
//			//		List<TaBonliv> l = taBonlivDAO.selectAll();
//			//		if(listeDocument==null)
//			listeDocument =((IDocumentTiersDAO)taBonlivDAO).rechercheDocument(taInfoEntreprise.getDatedebInfoEntreprise(),
//					taInfoEntreprise.getDatefinInfoEntreprise());
//			taBonlivDAO = null;
//		}
//		valeurs = new String[listeDocument.size()][2];
//		int i = 0;
//		String description = "";
//		for (IDocumentTiers taDocument : listeDocument) {
////			taDocument.calculeTvaEtTotaux();
//			valeurs[i][0] = taDocument.getCodeDocument();
//			
//			description = "";
//			description += taDocument.getLibelleDocument();
//			if(taDocument.getTaTiers()!=null) {
//				description += " \r\n " + taDocument.getTaTiers().getCodeTiers();
//			}
////			description += "\r\n Net TTC = " + taDocument.getNetHtCalc()
////			+ " \r\n Net HT = " + taDocument.getNetTtcCalc()
////			+ " \r\n Net à payer = " + taDocument.getNetAPayer();
//			description += " \r\n Date = " + LibDate.dateToStringAA(taDocument.getDateDocument());
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
				TaBonlivDAO taDocDAO = new TaBonlivDAO(getEm());
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
				valeurs[i][0] = (String)taFacture[1];
				description = "";
				description += (String)taFacture[3];
				if((String)taFacture[4]!=null) {
					description += " \r\n " + (String)taFacture[4];
				}

				description += " \r\n Date = " + LibDate.dateToStringAA((Date)taFacture[2]);
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
//		taTiersDAO.setPreferences(BonLivraisonPlugin.getDefault().getPreferenceStore().getString(fr.legrain.bonlivraison.preferences.PreferenceConstants.TYPE_TIERS_DOCUMENT));
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
		taTiersDAO.setPreferences(BonLivraisonPlugin.getDefault().getPreferenceStore().getString(fr.legrain.bonlivraison.preferences.PreferenceConstants.TYPE_TIERS_DOCUMENT));
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
			taDocument.setTtc(0);
		else
			taDocument.setTtc(1);
		try {
		} catch (DataSetException e1) {
			logger.error(e1);
		} catch (Exception e1) {
			logger.error(e1);
		}
		if (controllerLigne != null)
			controllerLigne.initTTC();
	}

	/**
	 * Initialisation des boutons suivant l'ï¿½tat de l'objet "ibTaTable"
	 * 
	 * @param initFocus -
	 *            si vrai initialise le focus en fonction du mode
	 */
	protected void initEtatBouton(boolean initFocus) {
		initEtatBoutonCommand(initFocus,modelEnteteBonliv.getListeObjet());
		enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID, dao.getModeObjet().getMode()==EnumModeObjet.C_MO_EDITION);
		boolean trouve = false;
		enableActionAndHandler(C_COMMAND_GLOBAL_PRECEDENT_ID, false);
		enableActionAndHandler(C_COMMAND_GLOBAL_SUIVANT_ID, true);
		enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID, true);
		enableActionAndHandler(C_COMMAND_DOCUMENT_CREATEDOC_ID, true);
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




	protected class ActionAnnulerFacture extends ActionAnnuler {
		public ActionAnnulerFacture(String name) {
			super(name);
		}

		@Override
		public void run() {
			try {
				actAnnuler();
			} catch (Exception e) {
				logger.error("", e);
			}
			// handlerService.executeCommand(C_COMMAND_FACTURE_ANNULER_ID,
			// null);
			// } catch (ExecutionException e) {
			// logger.error("",e);
			// } catch (NotDefinedException e) {
			// logger.error("",e);
			// } catch (NotEnabledException e) {
			// logger.error("",e);
			// } catch (NotHandledException e) {
			// logger.error("",e);
			// }
		}
	}

//	protected class ActionSupprimerFacture extends ActionSupprimer {
//		public ActionSupprimerFacture(String name) {
//			super(name);
//		}
//
//		@Override
//		public void run() {
//			try {
//				handlerService.executeCommand(C_COMMAND_FACTURE_SUPPRIMER_ID,
//						null);
//			} catch (ExecutionException e) {
//				logger.error("", e);
//			} catch (NotDefinedException e) {
//				logger.error("", e);
//			} catch (NotEnabledException e) {
//				logger.error("", e);
//			} catch (NotHandledException e) {
//				logger.error("", e);
//			}
//		}
//	}

//	protected class ActionModifierFacture extends ActionModifier {
//		public ActionModifierFacture(String name) {
//			super(name);
//		}
//
//		@Override
//		public void run() {
//			try {
//				handlerService.executeCommand(C_COMMAND_FACTURE_MODIFIER_ID,
//						null);
//			} catch (ExecutionException e) {
//				logger.error("", e);
//			} catch (NotDefinedException e) {
//				logger.error("", e);
//			} catch (NotEnabledException e) {
//				logger.error("", e);
//			} catch (NotHandledException e) {
//				logger.error("", e);
//			}
//		}
//	}

//	protected class ActionFermerFacture extends ActionFermer {
//		public ActionFermerFacture(String name) {
//			super(name);
//		}
//
//		@Override
//		public void run() {
//			try {
//				handlerService
//						.executeCommand(C_COMMAND_FACTURE_FERMER_ID, null);
//			} catch (ExecutionException e) {
//				logger.error("", e);
//			} catch (NotDefinedException e) {
//				logger.error("", e);
//			} catch (NotEnabledException e) {
//				logger.error("", e);
//			} catch (NotHandledException e) {
//				logger.error("", e);
//			}
//		}
//	}

//	protected class ActionImprimerFacture extends ActionImprimer {
//		public ActionImprimerFacture(String name) {
//			super(name);
//		}
//
//		@Override
//		public void run() {
//			try {
//				handlerService.executeCommand(C_COMMAND_FACTURE_IMPRIMER_ID,
//						null);
//			} catch (ExecutionException e) {
//				logger.error("", e);
//			} catch (NotDefinedException e) {
//				logger.error("", e);
//			} catch (NotEnabledException e) {
//				logger.error("", e);
//			} catch (NotHandledException e) {
//				logger.error("", e);
//			}
//		}
//	}

//	protected class ActionAideFacture extends ActionAide {
//		public ActionAideFacture(String name) {
//			super(name);
//		}
//
//		@Override
//		public void run() {
//			try {
//				handlerService.executeCommand(C_COMMAND_FACTURE_AIDE_ID, null);
//			} catch (ExecutionException e) {
//				logger.error("", e);
//			} catch (NotDefinedException e) {
//				logger.error("", e);
//			} catch (NotEnabledException e) {
//				logger.error("", e);
//			} catch (NotHandledException e) {
//				logger.error("", e);
//			}
//		}
//	}
//
//	protected class ActionRefreshFacture extends ActionRefresh {
//		public ActionRefreshFacture(String name) {
//			super(name);
//		}
//
//		@Override
//		public void run() {
//			try {
//				handlerService.executeCommand(C_COMMAND_FACTURE_REFRESH_ID,
//						null);
//			} catch (ExecutionException e) {
//				logger.error("", e);
//			} catch (NotDefinedException e) {
//				logger.error("", e);
//			} catch (NotEnabledException e) {
//				logger.error("", e);
//			} catch (NotHandledException e) {
//				logger.error("", e);
//			}
//		}
//	}

	public class OldBonliv {
		String code_devis = "";
		int id_devis = 0;

		public OldBonliv() {
		}

		public OldBonliv(String code_devis, int id_devis) {
			this.code_devis = code_devis;
			this.id_devis = id_devis;
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
	public boolean focusDansCommentaire(Control focusControl) {
		if(focusControl == ((PaCommentaire)vue.getExpandBar()
				.getItem(0).getControl()).getTfLIBL_COMMENTAIRE())return true;
		
		return false;
	}

	public Date dateDansPeriode(Date newValue,String champ) throws Exception{
		if(champ.equals(Const.C_DATE_DOCUMENT)){
			newValue=taDocument.dateDansExercice(newValue);
			if(newValue!=null){
//				if((((IHMEnteteBonliv)selectedBonliv).getDateLivDocument()!=null && 
//						((IHMEnteteBonliv)selectedBonliv).getDateLivDocument().before(newValue))||
//						((IHMEnteteBonliv)selectedBonliv).getDateLivDocument()==null ){
//					taDocument.setDateLivDocument(newValue);
//					((IHMEnteteBonliv)selectedBonliv).setDateLivDocument(newValue);
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

	public PaEditorTotauxBLController getControllerTotaux() {
		return controllerTotaux;
	}

	public void setControllerTotaux(PaEditorTotauxBLController controllerTotaux) {
		this.controllerTotaux = controllerTotaux;
		// controllerTotaux.initHandlerFacture(mapCommand);
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

	private void actReinitialiser() throws Exception {
		initialisationDesInfosComplementaires();
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
	protected void actSelection() throws Exception {
		try{
			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().
			getActivePage().openEditor(new fr.legrain.visualisation.editor.EditorInputSelectionVisualisation(),
					fr.legrain.visualisation.editor.EditorSelectionVisualisation.ID);
			LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);

			ParamAfficheVisualisation paramAfficheSelectionVisualisation = new ParamAfficheVisualisation();
			paramAfficheSelectionVisualisation.setModule("Bonliv");
			paramAfficheSelectionVisualisation.setNomClassController(nomClassController);
			paramAfficheSelectionVisualisation.setNomRequete(Const.C_NOM_VU_BONLIV_REDUIT);

			((LgrEditorPart)e).setPanel(((LgrEditorPart)e).getControllerSelection().getVue());
			((LgrEditorPart)e).getControllerSelection().configPanel(paramAfficheSelectionVisualisation);

		}catch (Exception e) {
			logger.error("",e);
		}	
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
	
	public TaBonliv rechercheBonliv(String codeBonliv,boolean annule,boolean devisVide) throws Exception {
		TaBonliv fact = null;
		fact = dao.findByCode(codeBonliv);
		if(fact!=null){
			if(annule && dao.getModeObjet().getMode()==EnumModeObjet.C_MO_EDITION 
					|| (dao.getModeObjet().getMode()==EnumModeObjet.C_MO_INSERTION )) {
				if(!devisVide)
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

	public TaBonliv getTaDocument() {
		return taDocument;
	}

	public void setTaDocument(TaBonliv taBonliv) {
		this.taDocument = taBonliv;
	}

	public TaBonlivDAO getDao() {
		return dao;
	}

	public void setDao(TaBonlivDAO dao) {
		this.dao = dao;
	}
	public IStatus validateUI() throws Exception {
		if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
				|| (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
			verifCode();
			ctrlTousLesChampsAvantEnregistrementSWT(dbcAdresseFact);
			ctrlTousLesChampsAvantEnregistrementSWT(dbcAdresseLiv);
			ctrlTousLesChampsAvantEnregistrementSWT();
			
			initInfosBonliv();
			
			mapperUIToModelAdresseFactVersInfosDoc.map((IHMAdresseInfosFacturation) selectedAdresseFact, taInfosDocument);
			mapperUIToModelAdresseLivVersInfosDoc.map((IHMAdresseInfosLivraison) selectedAdresseLiv, taInfosDocument);
			mapperUIToModelCPaiementVersInfosDoc.map((IHMInfosCPaiement) selectedCPaiement, taInfosDocument);
			
			mapperUIToModel.map((IHMEnteteBonliv)selectedBonliv,taDocument);
		}
		return null;
	}
	
	public IStatus validateUIField(String nomChamp,Object value) {
		String validationContext = "BONLIV";
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
						((IHMEnteteBonliv)selectedBonliv).setLibelleDocument("Bonliv N°"+taDocument.getCodeDocument()+" - "+nomTiers);
					if(vue.getCbTTC().isEnabled()){
						taDocument.setTtc(f.getTtcTiers());
						((IHMEnteteBonliv)selectedBonliv).setTtc(LibConversion.intToBoolean(taDocument.getTtc()));
					}
				}
				dao = null;
			} else if(nomChamp.equals(Const.C_LIBELLE_DOCUMENT)) {
				if(value==null || value.equals("")) {
					String nomTiers="";
					if(taDocument.getTaTiers()!=null)
						 nomTiers=taDocument.getTaTiers().getNomTiers();
					((IHMEnteteBonliv)selectedBonliv).setLibelleDocument("Bonliv N°"+taDocument.getCodeDocument()+" - "+nomTiers);
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
//					taFacture.setTaCPaiement(f);
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
//					taFacture.setTaCPaiement(f);
//				}
				dao = null;
			} else {
				boolean verrouilleModifCode = false;
				TaBonliv u = new TaBonliv(true);
				PropertyUtils.setSimpleProperty(u, nomChamp, value);
				if(((IHMEnteteBonliv) selectedBonliv).getIdDocument()!=null) {
					u.setIdDocument(((IHMEnteteBonliv) selectedBonliv).getIdDocument());
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
				}else if(nomChamp.equals(Const.C_DATE_DOCUMENT)) {
					value=(dateDansPeriode((Date)value,nomChamp));
					PropertyUtils.setSimpleProperty(u, nomChamp, value);
//					s = new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
				}
				s = dao.validate(u,nomChamp,validationContext,verrouilleModifCode);
			}
			if(s.getSeverity()!=Status.ERROR){
//				if(nomChamp.equals(Const.C_DATE_DOCUMENT)){
//					((IHMEnteteBonliv)selectedBonliv).setDateDocument((Date)value);
//				}
				if(nomChamp.equals(Const.C_DATE_DOCUMENT)){
					Boolean changement=taDocument.changementDateDocument((Date)value);
					((IHMEnteteBonliv)selectedBonliv).setDateDocument((Date)value);
					PropertyUtils.setSimpleProperty(taDocument, Const.C_DATE_DOCUMENT, value);
					if(changement){
						((IHMEnteteBonliv)selectedBonliv).setDateLivDocument(taDocument.getDateDocument());
						PropertyUtils.setSimpleProperty(taDocument, Const.C_DATE_LIV_DOCUMENT, taDocument.getDateDocument());
						taDocument.setOldDate(taDocument.getDateDocument());
					}
				}
				if(nomChamp.equals(Const.C_DATE_LIV_DOCUMENT)) {
					((IHMEnteteBonliv)selectedBonliv).setDateLivDocument((Date)value);
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


	
	
	private void actReinitAdrFact() throws Exception {
		initialisationDesInfosComplementaires(true,Const.RECHARGE_ADR_FACT);
	}
	
	private void actReinitAdrLiv() throws Exception {
		initialisationDesInfosComplementaires(true,Const.RECHARGE_ADR_LIV);
	}
	
	private void actReinitCPaiement() throws Exception {
		initialisationDesInfosComplementaires(true,Const.RECHARGE_C_PAIEMENT);
		calculDateEcheance();
	}
	
	private void actAppliquerCPaiement() throws Exception {
		calculDateEcheance();
	}
	
	private void actReinitInfosTiers() throws Exception {
		initialisationDesInfosComplementaires(true,Const.RECHARGE_INFOS_TIERS);
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

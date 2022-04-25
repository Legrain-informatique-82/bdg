package fr.legrain.facture.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
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
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
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
import org.eclipse.jface.resource.ImageDescriptor;
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
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
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

import fr.legrain.document.DocumentPlugin;
import fr.legrain.document.controller.MessagesEcran;
import fr.legrain.document.controller.PaNotesTiersController;
import fr.legrain.document.divers.IEditionTraite;
import fr.legrain.document.ecran.PaIdentiteTiers;
import fr.legrain.document.ecran.PaNotesTiers;
import fr.legrain.document.preferences.PreferenceConstants;
import fr.legrain.documents.dao.IDocumentTiersDAO;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaInfosFacture;
import fr.legrain.documents.dao.TaInfosFactureDAO;
import fr.legrain.documents.dao.TaLFacture;
import fr.legrain.documents.dao.TaRAcompte;
import fr.legrain.documents.dao.TaRAvoir;
import fr.legrain.documents.dao.TaRReglement;
import fr.legrain.documents.events.SWTModificationDocumentEvent;
import fr.legrain.documents.events.SWTModificationDocumentListener;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.edition.actions.BaseImpressionEdition;
import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.edition.actions.InfosEmail;
import fr.legrain.edition.actions.InfosFax;
import fr.legrain.facture.FacturePlugin;
import fr.legrain.facture.divers.FichierDonneesAdresseFacture;
import fr.legrain.facture.divers.ParamAfficheLFacture;
import fr.legrain.facture.ecran.PaEditorFactureSWT;
import fr.legrain.facture.editor.EditorFacture;
import fr.legrain.facture.editor.EditorInputFacture;
import fr.legrain.generationLabelEtiquette.divers.GenerationFileEtiquette;
import fr.legrain.generationLabelEtiquette.handlers.ParamWizardEtiquettes;
import fr.legrain.generationLabelEtiquette.wizard.HeadlessEtiquette;
import fr.legrain.generationLabelEtiquette.wizard.WizardController;
import fr.legrain.generationLabelEtiquette.wizard.WizardDialogModelLabels;
import fr.legrain.generationLabelEtiquette.wizard.WizardModelLables;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Document.ChangeModeEvent;
import fr.legrain.gestCom.Module_Document.ChangeModeListener;
import fr.legrain.gestCom.Module_Document.IHMAdresse;
import fr.legrain.gestCom.Module_Document.IHMAideFacture;
import fr.legrain.gestCom.Module_Document.IHMEnteteFacture;
import fr.legrain.gestCom.Module_Document.IHMEtat;
import fr.legrain.gestCom.Module_Document.SWTDocumentEditable;
import fr.legrain.gestCom.Module_Tiers.IHMAdresseInfosFacturation;
import fr.legrain.gestCom.Module_Tiers.IHMAdresseInfosLivraison;
import fr.legrain.gestCom.Module_Tiers.IHMIdentiteTiers;
import fr.legrain.gestCom.Module_Tiers.SWTTiers;
import fr.legrain.gestCom.gestComBd.gestComBdPlugin;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.notifier.NotificationType;
import fr.legrain.gestCom.librairiesEcran.notifier.NotifierDialog;
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
import fr.legrain.gestCom.librairiesEcran.workbench.LgrFileBundleLocator;
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
import fr.legrain.lib.gui.UpdateMasterEntityEvent;
import fr.legrain.lib.gui.UpdateMasterEntityListener;
import fr.legrain.lib.gui.aide.PaAideRechercheSWT;
import fr.legrain.lib.gui.aide.PaAideSWT;
import fr.legrain.lib.gui.aide.ParamAfficheAide;
import fr.legrain.lib.windows.WinRegistry;
import fr.legrain.libMessageLGR.LgrMess;
import fr.legrain.pointLgr.dao.TaComptePointDAO;
import fr.legrain.publipostage.divers.ParamPublipostage;
import fr.legrain.publipostage.divers.TypeVersionPublipostage;
import fr.legrain.publipostage.msword.PublipostageMsWord;
import fr.legrain.publipostage.openoffice.AbstractPublipostageOOo;
import fr.legrain.publipostage.openoffice.PublipostageOOoFactory;
import fr.legrain.tiers.dao.TaAdresse;
import fr.legrain.tiers.dao.TaAdresseDAO;
import fr.legrain.tiers.dao.TaCPaiement;
import fr.legrain.tiers.dao.TaCPaiementDAO;
import fr.legrain.tiers.dao.TaCompteBanque;
import fr.legrain.tiers.dao.TaTAdrDAO;
import fr.legrain.tiers.dao.TaTTvaDoc;
import fr.legrain.tiers.dao.TaTTvaDocDAO;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.divers.TiersUtil;
import fr.legrain.tiers.ecran.PaAdresseSWT;
import fr.legrain.tiers.ecran.PaTiersSWT;
import fr.legrain.tiers.ecran.ParamAfficheAdresse;
import fr.legrain.tiers.ecran.ParamAfficheNoteTiers;
import fr.legrain.tiers.ecran.ParamAfficheTiers;
import fr.legrain.tiers.ecran.SWTPaAdresseController;
import fr.legrain.tiers.ecran.SWTPaTiersController;
import fr.legrain.tiers.editor.EditorAdresse;
import fr.legrain.tiers.editor.EditorInputAdresse;
import fr.legrain.tiers.editor.EditorInputTiers;
import fr.legrain.tiers.editor.TiersMultiPageEditor;

//#AFAIRE
//#evt
//#JPA
public class SWTPaEditorFactureController extends JPABaseControllerSWTStandard
		implements   RetourEcranListener,
		SWTModificationDocumentListener, ChangeModeListener {

	static Logger logger = Logger.getLogger(SWTPaEditorFactureController.class.getName());
	private PaEditorFactureSWT vue = null;
	private Integer idTiersDocumentOriginal = null; //ID du tiers lorsque l'on remonte un document, ne devrait pas etre changé ailleurs
//	private Impression impressionFacture = null ;
	private boolean desactiveValidateCodeDocument=false;
	private boolean desactiveVerifCodeTiers=false;
	
	BaseImpressionEdition impressionEdition = null;
	Object gestionnaireTraite=null;
	String typeTraite=null;
	/** 01/03/2010 modifier les editions (zhaolin) **/
	BaseImpressionEdition baseImpressionEdition = null;
	/************************************************/
	public static final String C_COMMAND_DOCUMENT_REINITIALISER_ID = "fr.legrain.Document.reinitialiser";
	protected HandlerReinitialiser handlerReinitialiser = new HandlerReinitialiser();

	public static final String C_COMMAND_DOCUMENT_REINIT_ADR_FACT_ID = "fr.legrain.Document.reinitAdrFact";
	protected HandlerReinitAdrFact handlerReinitAdrFact = new HandlerReinitAdrFact();

	public static final String C_COMMAND_DOCUMENT_REINIT_ADR_LIV_ID = "fr.legrain.Document.reinitAdrLiv";
	protected HandlerReinitAdrLiv handlerReinitAdrLiv = new HandlerReinitAdrLiv();

//	public static final String C_COMMAND_DOCUMENT_REINIT_CPAIEMENT_ID = "fr.legrain.Document.reinitCPaiement";
//	protected HandlerReinitCPaiement handlerReinitCPaiement = new HandlerReinitCPaiement();
//	
//	public static final String C_COMMAND_DOCUMENT_APPLIQUER_CPAIEMENT_ID = "fr.legrain.document.appliquerCPaiement";
//	protected HandlerAppliquerCPaiement handlerAppliquerCPaiement = new HandlerAppliquerCPaiement();

	public static final String C_COMMAND_DOCUMENT_REINIT_INFOSTIERS_ID = "fr.legrain.Document.reinitInfosTiers";
	protected HandlerReinitInfosTiers handlerReinitInfosTiers = new HandlerReinitInfosTiers();
	
	public static final String C_COMMAND_DOCUMENT_AFFICHER_TIERS_ID = "fr.legrain.Document.afficherTiers";
	protected HandlerafficherTiers handlerafficherTiers = new HandlerafficherTiers();
	
	public static final String TYPE_ETIQUETTE_ADRESSE_FACTURATION = "fact";
	public static final String TYPE_ETIQUETTE_ADRESSE_LIVRAISON = "liv";
	
	protected HandlerEtiquette handlerEtiquette = new HandlerEtiquette();

	public List<Control> listeComposantEntete = null;
	public List<Control> listeComposantAdresse = null;
	public List<Control> listeComposantAdresse_Liv = null;
//	public List<Control> listeComposantCPaiement = null;
	public List<Control> listeComposantInfosTiers = null;
	public List<Control> listeComposantNonFocusable = null;
	
	protected Map<Control, String> mapComposantChampsAdresse = null;
	protected Map<Control, String> mapComposantChampsAdresseLiv = null;
//	protected Map<Control, String> mapComposantChampsCPaiement = null;
	protected Map<Control, String> mapComposantChampsInfosTiers = null;

	private TaFactureDAO dao = null;// new TaFactureDAO();
	
	private Object ecranAppelant = null;
	private IHMEnteteFacture ihmOldEnteteFacture;
	private Realm realm;
	private DataBindingContext dbc;
	private Object selectedFacture = new IHMEnteteFacture();
	private String nomClassController = this.getClass().getSimpleName();
	private Class classModel = IHMEnteteFacture.class;
	
	private ComboViewer viewerComboLocalisationTVA;
	
//	private List<IDocumentTiers> listeDocument = null;
	private List<TaTiers>listeTiers = null;
	private ModelGeneralObjet<IHMEnteteFacture,TaFactureDAO,TaFacture> modelEnteteFacture /*= new ModelGeneralObjet<IHMEnteteFacture,TaFactureDAO,TaFacture>(dao,classModel)*/;
	private String typeAdresseFacturation=DocumentPlugin.getDefault().getPreferenceStore().getString(fr.legrain.document.preferences.PreferenceConstants.TYPE_ADRESSE_FACTURATION);
	private String typeAdresseLivraison=DocumentPlugin.getDefault().getPreferenceStore().getString(fr.legrain.document.preferences.PreferenceConstants.TYPE_ADRESSE_BONLIV);
	
	private TaInfosFactureDAO daoInfosFacture = null;
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

//	private TaCPaiementDAO daoCPaiement = null;//new TaCPaiementDAO();
//	private Object selectedCPaiement = new IHMInfosCPaiement();
//	private Class classModelCPaiement = IHMInfosCPaiement.class;
//	private ModelGeneralObjet<IHMInfosCPaiement,TaCPaiementDAO,TaCPaiement> modelCPaiement /*= new ModelGeneralObjet<IHMInfosCPaiement,TaCPaiementDAO,TaCPaiement>(daoCPaiement,classModelCPaiement)*/;
//	private DataBindingContext dbcCPaiement = null;

	protected ContentProposalAdapter codeDocumentContentProposal;
	private ContentProposalAdapter tiersContentProposal;
	private MapChangeListener changeListener = new MapChangeListener();

	private SWTPaLigneController controllerLigne = null;
	private SWTPaEditorTotauxController controllerTotaux = null;
	
	private LgrDozerMapper<TaAdresse,IHMAdresseInfosFacturation> mapperModelToUIAdresseInfosDocument = new LgrDozerMapper<TaAdresse,IHMAdresseInfosFacturation>();
	private LgrDozerMapper<TaAdresse,IHMAdresseInfosLivraison> mapperModelToUIAdresseLivInfosDocument = new LgrDozerMapper<TaAdresse,IHMAdresseInfosLivraison>();
//	private LgrDozerMapper<TaCPaiement,IHMInfosCPaiement> mapperModelToUICPaiementInfosDocument = new LgrDozerMapper<TaCPaiement,IHMInfosCPaiement>();
	
//	private LgrDozerMapper<TaInfosFacture,IHMInfosCPaiement> mapperModelToUIInfosDocVersCPaiement = new LgrDozerMapper<TaInfosFacture,IHMInfosCPaiement>();
	private LgrDozerMapper<TaInfosFacture,IHMAdresseInfosFacturation> mapperModelToUIInfosDocVersAdresseFact = new LgrDozerMapper<TaInfosFacture,IHMAdresseInfosFacturation>();
	private LgrDozerMapper<TaInfosFacture,IHMAdresseInfosLivraison> mapperModelToUIInfosDocVersAdresseLiv = new LgrDozerMapper<TaInfosFacture,IHMAdresseInfosLivraison>();
		
//	private LgrDozerMapper<IHMInfosCPaiement,TaInfosFacture> mapperUIToModelCPaiementVersInfosDoc = new LgrDozerMapper<IHMInfosCPaiement, TaInfosFacture>();
	private LgrDozerMapper<IHMAdresseInfosFacturation,TaInfosFacture> mapperUIToModelAdresseFactVersInfosDoc = new LgrDozerMapper<IHMAdresseInfosFacturation, TaInfosFacture>();
	private LgrDozerMapper<IHMAdresseInfosLivraison,TaInfosFacture> mapperUIToModelAdresseLivVersInfosDoc = new LgrDozerMapper<IHMAdresseInfosLivraison, TaInfosFacture>();
	
	private LgrDozerMapper<TaFacture,TaInfosFacture> mapperUIToModelDocumentVersInfosDoc = new LgrDozerMapper<TaFacture, TaInfosFacture>();
	
	private LgrDozerMapper<IHMEnteteFacture,TaFacture> mapperUIToModel = new LgrDozerMapper<IHMEnteteFacture,TaFacture>();
	private LgrDozerMapper<TaFacture,IHMEnteteFacture> mapperModelToUI = new LgrDozerMapper<TaFacture,IHMEnteteFacture>();
	
	private TaFacture taDocument = null;
	private TaInfosFacture taInfosDocument = null;
	
	private PaNotesTiersController paNotesTiersController = null;
	private PaNotesTiers paNotesTiers = null;
	private PaInfosAdresse paInfosAdresseLiv = null;
	private PaInfosAdresse paInfosAdresseFact = null;
//	private PaInfosCondPaiement paInfosCondPaiement = null;
	private PaIdentiteTiers paIdentiteTiers = null;
	private String typePaiementDefaut=null;
	
	Menu popupMenuFormulaire = null;
	Menu popupMenuGrille = null;
	
	
	public SWTPaEditorFactureController() {
	}
	
	public SWTPaEditorFactureController(PaEditorFactureSWT vue) {
		this(vue,null);
	}

	public SWTPaEditorFactureController(PaEditorFactureSWT vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		try {

			dao = new TaFactureDAO(getEm());
			daoInfosFacture = new TaInfosFactureDAO(getEm());
			daoAdresseFact = new TaAdresseDAO(getEm());
			daoAdresseLiv = new TaAdresseDAO(getEm());
//			daoCPaiement = new TaCPaiementDAO(getEm());
			setVue(vue);
			try {
				setTypeAdresseFacturation(DocumentPlugin.getDefault().getPreferenceStore().getString(fr.legrain.document.preferences.PreferenceConstants.TYPE_ADRESSE_FACTURATION));
				setTypeAdresseLivraison(DocumentPlugin.getDefault().getPreferenceStore().getString(fr.legrain.document.preferences.PreferenceConstants.TYPE_ADRESSE_BONLIV));
			} catch (Exception e) {				
			}
			vue.getShell().addShellListener(this);
			vue.getShell().addTraverseListener(new Traverse());
//			impressionFacture = new Impression(vue.getShell(),getEm());
			
			
			/** 01/03/2010 modifier les editions (zhaolin) **/
			baseImpressionEdition = new BaseImpressionEdition(vue.getShell(),getEm());
			impressionEdition = new BaseImpressionEdition(vue.getShell(),getEm());
			/************************************************/
			
			vue.getDateTimeDATE_DOCUMENT().addTraverseListener(new DateTraverse());

			vue.getDateTimeDATE_LIV_DOCUMENT().addTraverseListener(new DateTraverse());

//			addDestroyListener(ibTaTable);
			setDaoStandard(dao);

//			this.swtFacture.addModificationDocumentListener(dao);
//			this.swtFacture.addModificationDocumentListener(this);
//			this.swtFacture.addChangeModeListener(this);
			
//			taFacture.addModificationDocumentListener(controllerLigne);
//			taFacture.addModificationDocumentListener(controllerTotaux);
//			taFacture.addModificationDocumentListener(this);
			
//			taFacture.addChangeModeListener(this);

			// à l'insertion d'une nouvelle facture, le champ est contrôlé à
			// vide, ce qui fait
			// lorsque que l'on sort de la zone sans l'avoir rempli, il ne la
			// re-contrôle pas
			// d'où le rajout de se focusListener pour le forcer à remplir la
			// zone
			vue.getTfLIBELLE_DOCUMENT().addFocusListener(new FocusAdapter() {
				public void focusLost(FocusEvent e) {
					try {
//						ctrlUnChampsSWT(((PaEditorFactureSWT) getVue()).getTfLIBELLE_FACTURE());
						validateUIField(Const.C_LIBELLE_DOCUMENT,((PaEditorFactureSWT) getVue()).getTfLIBELLE_DOCUMENT().getText());
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
		
		//creation de l'écran
		paNotesTiers =  new PaNotesTiers(vue.getExpandBar(), SWT.PUSH);
		setPaNotesTiersController(new PaNotesTiersController(paNotesTiers, getEm()));
		//préparation du filtrage suivant le type de note
		String prefNoteTiers = FacturePlugin.getDefault().getPreferenceStore().getString(fr.legrain.facture.preferences.PreferenceConstants.TYPE_NOTES_TIERS_DOCUMENT);
		List<String> listeTypeNoteAAfficher = null;
		if (prefNoteTiers!=null && !prefNoteTiers.equals("")){
			listeTypeNoteAAfficher = new ArrayList<String>();
			String[] typeNoteTiers = prefNoteTiers.split(";"); 
			for (int i = 0; i < typeNoteTiers.length; i++) {
				listeTypeNoteAAfficher.add(typeNoteTiers[i].split(",")[0]);
			}
		}
		ParamAfficheNoteTiers paramAfficheNoteTiers = new ParamAfficheNoteTiers();
		paramAfficheNoteTiers.setListeTypeNoteAAfficher(listeTypeNoteAAfficher);
		//ajout de l'expanditem
		getPaNotesTiersController().configPanel(paramAfficheNoteTiers);
		addUpdateMasterEntityListener(getPaNotesTiersController());
		addExpandBarItem(vue.getExpandBar(), paNotesTiers, "Notes", //titre definitif changer dans une autre methode
				FacturePlugin.getImageDescriptor("/icons/lightbulb.png").createImage(),SWT.DEFAULT,300);
		
		paInfosAdresseFact =  new PaInfosAdresse(vue.getExpandBar(), SWT.PUSH);
		addExpandBarItem(vue.getExpandBar(), paInfosAdresseFact, "Adresse de facturation",
				LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ADRESSE_FAC));

		paInfosAdresseLiv =  new PaInfosAdresse(vue.getExpandBar(), SWT.PUSH);
		addExpandBarItem(vue.getExpandBar(), paInfosAdresseLiv, "Adresse de livraison",
				LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ADRESSE_LIV));

//		paInfosCondPaiement =  new PaInfosCondPaiement(vue.getExpandBar(), SWT.PUSH);
//		addExpandBarItem(vue.getExpandBar(), paInfosCondPaiement, "Conditions de paiement",
//						LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_CONDITION_PAIEMENT));

		paIdentiteTiers =  new PaIdentiteTiers(vue.getExpandBar(), SWT.PUSH);
		addExpandBarItem(vue.getExpandBar(), paIdentiteTiers, "Identité du tiers",
				LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_IDENTITE_TIERS));
		
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
	}

	private void initController() {
		try {
			setDaoStandard(dao);
			setDbcStandard(dbc);
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
			initActions();

			branchementBouton();
			listeComposantFocusable.remove(paInfosAdresseFact.getBtnReinitialiser());
			listeComposantFocusable.remove(paInfosAdresseLiv.getBtnReinitialiser());

			initFocusOrder();

			popupMenuFormulaire = new Menu(vue.getShell(), SWT.POP_UP);
			popupMenuGrille = new Menu(vue.getShell(), SWT.POP_UP);
			Menu[] tabPopups = new Menu[] { popupMenuFormulaire,
					popupMenuGrille };
			this.initPopupAndButtons(mapActions, tabPopups);
			
			HeadlessEtiquette headlessEtiquette = new HeadlessEtiquette();
			headlessEtiquette.menuEtiquette(popupMenuFormulaire,"Etiquette adresse facturation",TYPE_ETIQUETTE_ADRESSE_FACTURATION, WizardController.DOSSIER_PARAM_TIERS);
			headlessEtiquette.menuEtiquette(popupMenuFormulaire,"Etiquette adresse livraison",TYPE_ETIQUETTE_ADRESSE_LIVRAISON, WizardController.DOSSIER_PARAM_TIERS);
			
			// vue.getPaBas().setMenu(popupMenuFormulaire);
			// vue.getPaTotaux().setMenu(popupMenuFormulaire);
//			vue.getShell().setMenu(popupMenuFormulaire);
			 vue.getPaEntete().setMenu(popupMenuFormulaire);
			 vue.getExpandBar().setMenu(popupMenuFormulaire);
			 vue.setMenu(popupMenuFormulaire);
			 findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl().setMenu(popupMenuFormulaire);
			 findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl().setMenu(popupMenuFormulaire);
//			 findExpandIem(vue.getExpandBar(), paInfosCondPaiement).getControl().setMenu(popupMenuFormulaire);
			 findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl().setMenu(popupMenuFormulaire);
			 vue.getBtnAutres().setMenu(popupMenuFormulaire);
			 
				typePaiementDefaut = DocumentPlugin.getDefault().getPreferenceStore().getString(
						fr.legrain.document.preferences.PreferenceConstants.TYPE_PAIEMENT_DEFAUT);
		if (typePaiementDefaut == null || typePaiementDefaut=="")
			typePaiementDefaut="C";
		
			bind(vue);
			initEtatBouton(true);
			createContributors();
			
			vue.getTfCODE_TIERS().addVerifyListener(new VerifyListener() {
				@Override
				public void verifyText(VerifyEvent e) {
					// TODO Auto-generated method stub
					if(!desactiveVerifCodeTiers)
						e.doit=autoriseModifCodeTiers();
				}
			});
				
				
				
		
//			HeadlessEtiquette headlessEtiquette = new HeadlessEtiquette();
//			headlessEtiquette.menuEtiquette(popupMenuFormulaire);

			

		} catch (Exception e) {
			if (e.getMessage() != null)
				vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaArticlesController", e);
		} finally {
			vue.getTfCODE_DOCUMENT().setEditable(false);
		}
	}

	public boolean autoriseModifCodeTiers(){
		if(!(taDocument.getTaRAcomptes().size()==0 && taDocument.getTaRAvoirs().size()==0
				&& !taDocument.reglementRempli())){
			MessageDialog.openInformation(vue.getShell(),"Attention","Vous devez supprimer tous les réglements ou " +
					"liaisons existantes avant de pouvoir " +
					"modifier ce code tiers.");
			return false;
		}
		return true;
	}
	
	
	public void bind(PaEditorFactureSWT PaEditorFactureSWT) {
		try {
			modelEnteteFacture = new ModelGeneralObjet<IHMEnteteFacture,TaFactureDAO,TaFacture>(dao,classModel);
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

//			modelEnteteFacture.remplirListe();
			if (modelEnteteFacture.getListeObjet().isEmpty()) {
				modelEnteteFacture.getListeObjet().add(new IHMEnteteFacture());
			}
			selectedFacture = modelEnteteFacture.getListeObjet().getFirst();
			
			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			setDbcStandard(dbc);

			bindingFormSimple(dbc, realm, selectedFacture, classModel);
			bindAdresse();
			bindAdresseLiv();
//			bindCPaiement();
			bindInfosTiers();
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

//	public void bindCPaiement() {
//		try {
//			modelCPaiement = new ModelGeneralObjet<IHMInfosCPaiement,TaCPaiementDAO,TaCPaiement>(daoCPaiement,classModelCPaiement);
////			modelCPaiement.remplirListe();
//			bindingFormSimple(daoCPaiement, mapComposantChampsCPaiement, dbc, realm, selectedCPaiement, classModelCPaiement);
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
		
		mapInfosVerifSaisie.put(vue.getTfCODE_DOCUMENT(), new InfosVerifSaisie(new TaFacture(),Const.C_CODE_DOCUMENT,null));
		mapInfosVerifSaisie.put(vue.getTfCODE_TIERS(), new InfosVerifSaisie(new TaTiers(),Const.C_CODE_TIERS,null));
		mapInfosVerifSaisie.put(vue.getTfLIBELLE_DOCUMENT(), new InfosVerifSaisie(new TaFacture(),Const.C_LIBELLE_DOCUMENT,null));
		
		mapInfosVerifSaisie.put(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl()).getTfAdresse1(), new InfosVerifSaisie(new TaInfosFacture(),Const.C_ADRESSE1_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl()).getTfAdresse2(), new InfosVerifSaisie(new TaInfosFacture(),Const.C_ADRESSE2_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl()).getTfAdresse3(), new InfosVerifSaisie(new TaInfosFacture(),Const.C_ADRESSE3_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl())
				.getTfCodePostal(), new InfosVerifSaisie(new TaInfosFacture(),Const.C_CODEPOSTAL_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl()).getTfVille(), new InfosVerifSaisie(new TaInfosFacture(),Const.C_VILLE_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl()).getTfPays(), new InfosVerifSaisie(new TaInfosFacture(),Const.C_PAYS_INFOS_DOCUMENT,null));

		mapInfosVerifSaisie.put(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl())
				.getTfAdresse1(), new InfosVerifSaisie(new TaInfosFacture(),Const.C_ADRESSE1_LIV_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl())
				.getTfAdresse2(), new InfosVerifSaisie(new TaInfosFacture(),Const.C_ADRESSE2_LIV_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl())
				.getTfAdresse3(), new InfosVerifSaisie(new TaInfosFacture(),Const.C_ADRESSE3_LIV_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl())
				.getTfCodePostal(), new InfosVerifSaisie(new TaInfosFacture(),Const.C_CODEPOSTAL_LIV_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl())
				.getTfVille(), new InfosVerifSaisie(new TaInfosFacture(),Const.C_VILLE_LIV_INFOS_DOCUMENT,null));
		mapInfosVerifSaisie.put(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl())
				.getTfPays(), new InfosVerifSaisie(new TaInfosFacture(),Const.C_PAYS_LIV_INFOS_DOCUMENT,null));
				
//		mapInfosVerifSaisie.put(((PaInfosCondPaiement) findExpandIem(vue.getExpandBar(), paInfosCondPaiement).getControl())
//				.getTfCODE_C_PAIEMENT(), new InfosVerifSaisie(new TaInfosFacture(),Const.C_CODE_C_PAIEMENT,null));
//		mapInfosVerifSaisie.put(((PaInfosCondPaiement) findExpandIem(vue.getExpandBar(), paInfosCondPaiement).getControl())
//				.getTfLIB_C_PAIEMENT(), new InfosVerifSaisie(new TaInfosFacture(),Const.C_LIB_C_PAIEMENT,null));
//		mapInfosVerifSaisie.put(((PaInfosCondPaiement) findExpandIem(vue.getExpandBar(), paInfosCondPaiement).getControl())
//				.getTfREPORT_C_PAIEMENT(), new InfosVerifSaisie(new TaInfosFacture(),Const.C_REPORT_C_PAIEMENT,null));
//		mapInfosVerifSaisie.put(((PaInfosCondPaiement) findExpandIem(vue.getExpandBar(), paInfosCondPaiement).getControl())
//				.getTfFIN_MOIS_C_PAIEMENT(), new InfosVerifSaisie(new TaInfosFacture(),Const.C_FIN_MOIS_C_PAIEMENT,null));
		
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

//		if (listeComposantCPaiement == null)
//			listeComposantCPaiement = new ArrayList<Control>();
		
		if (listeComposantInfosTiers == null)
			listeComposantInfosTiers = new ArrayList<Control>();

		if (mapComposantChampsAdresse == null)
			mapComposantChampsAdresse = new LinkedHashMap<Control, String>();

		if (mapComposantChampsAdresseLiv == null)
			mapComposantChampsAdresseLiv = new LinkedHashMap<Control, String>();

//		if (mapComposantChampsCPaiement == null)
//			mapComposantChampsCPaiement = new LinkedHashMap<Control, String>();
		
		if(mapComposantChampsInfosTiers== null)
			mapComposantChampsInfosTiers = new LinkedHashMap<Control, String>();

		mapComposantChamps.put(vue.getTfCODE_DOCUMENT(), Const.C_CODE_DOCUMENT);
		mapComposantChamps.put(vue.getTfCODE_TIERS(), Const.C_CODE_TIERS);
		mapComposantChamps.put(vue.getTfNOM_TIERS(),
				Const.C_NOM_TIERS);
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

		mapComposantChampsAdresse.put(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl()).getTfAdresse1(),
				Const.C_ADRESSE1);
		mapComposantChampsAdresse.put(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl()).getTfAdresse2(),
				Const.C_ADRESSE2);
		mapComposantChampsAdresse.put(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl()).getTfAdresse3(),
				Const.C_ADRESSE3);
		mapComposantChampsAdresse.put(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl())
				.getTfCodePostal(), Const.C_CODEPOSTAL);
		mapComposantChampsAdresse.put(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl()).getTfVille(),
				Const.C_VILLE);
		mapComposantChampsAdresse.put(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl()).getTfPays(),
				Const.C_PAYS);		
		for (Control c : mapComposantChampsAdresse.keySet()) {
			listeComposantAdresse.add(c);
		}
		listeComposantAdresse.add(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl()).getBtnReinitialiser());

		mapComposantChampsAdresseLiv.put(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl())
				.getTfAdresse1(), Const.C_ADRESSE1_LIV);
		mapComposantChampsAdresseLiv.put(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl())
				.getTfAdresse2(), Const.C_ADRESSE2_LIV);
		mapComposantChampsAdresseLiv.put(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl())
				.getTfAdresse3(), Const.C_ADRESSE3_LIV);
		mapComposantChampsAdresseLiv.put(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl())
				.getTfCodePostal(), Const.C_CODEPOSTAL_LIV);
		mapComposantChampsAdresseLiv.put(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl())
				.getTfVille(), Const.C_VILLE_LIV);
		mapComposantChampsAdresseLiv.put(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl())
				.getTfPays(), Const.C_PAYS_LIV);
		mapComposantChampsAdresseLiv.put(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl())
				.getTfPays(), Const.C_PAYS_LIV);		
		for (Control c : mapComposantChampsAdresseLiv.keySet()) {
			listeComposantAdresse_Liv.add(c);
		}
		listeComposantAdresse_Liv.add(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl()).getBtnReinitialiser());

//		mapComposantChampsCPaiement.put(((PaInfosCondPaiement) findExpandIem(vue.getExpandBar(), paInfosCondPaiement).getControl())
//				.getTfCODE_C_PAIEMENT(),
//				Const.C_CODE_C_PAIEMENT);
//		mapComposantChampsCPaiement.put(((PaInfosCondPaiement) findExpandIem(vue.getExpandBar(), paInfosCondPaiement).getControl())
//				.getTfLIB_C_PAIEMENT(),
//				Const.C_LIB_C_PAIEMENT);
//		mapComposantChampsCPaiement.put(((PaInfosCondPaiement) findExpandIem(vue.getExpandBar(), paInfosCondPaiement).getControl())
//				.getTfREPORT_C_PAIEMENT(),
//				Const.C_REPORT_C_PAIEMENT);
//		mapComposantChampsCPaiement.put(((PaInfosCondPaiement)findExpandIem(vue.getExpandBar(), paInfosCondPaiement).getControl())
//				.getTfFIN_MOIS_C_PAIEMENT(),
//				Const.C_FIN_MOIS_C_PAIEMENT);
//		for (Control c : mapComposantChampsCPaiement.keySet()) {
//			listeComposantCPaiement.add(c);
//		}
//		listeComposantCPaiement.add(((PaInfosCondPaiement) findExpandIem(vue.getExpandBar(), paInfosCondPaiement).getControl())
//				.getBtnReinitialiser());
//		listeComposantCPaiement.add(((PaInfosCondPaiement) findExpandIem(vue.getExpandBar(), paInfosCondPaiement).getControl())
//				.getBtnAppliquer());
		
		//gestion infos tiers
		mapComposantChampsInfosTiers.put(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfCODE_T_CIVILITE(),
				Const.C_CODE_T_CIVILITE);
		mapComposantChampsInfosTiers.put(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfCODE_COMPTA(),
				Const.C_CODE_COMPTA);
		mapComposantChampsInfosTiers.put(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfCOMPTE(),
				Const.C_COMPTE);
		mapComposantChampsInfosTiers.put(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfTYPE_TVA_DOC(),
				Const.C_CODE_T_TVA_DOC);		
		mapComposantChampsInfosTiers.put(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfADRESSE_EMAIL(),
				Const.C_ADRESSE_EMAIL);
		mapComposantChampsInfosTiers.put(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfEntite(),
				Const.C_CODE_T_ENTITE);
		mapComposantChampsInfosTiers.put(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfCODE_ENTREPRISE(),
				Const.C_NOM_ENTREPRISE);
		mapComposantChampsInfosTiers.put(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfPRENOM_TIERS(),
				Const.C_PRENOM_TIERS);
		mapComposantChampsInfosTiers.put(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfNUMERO_TELEPHONE(),
				Const.C_NUMERO_TELEPHONE);
		mapComposantChampsInfosTiers.put(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfNOM_TIERS(),
				Const.C_NOM_TIERS);
		mapComposantChampsInfosTiers.put(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfTVA_I_COM_COMPL(),
				Const.C_TVA_I_COM_COMPL);
		mapComposantChampsInfosTiers.put(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfADRESSE_WEB(),
				Const.C_ADRESSE_WEB);
		mapComposantChampsInfosTiers.put(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfLIBL_COMMENTAIRE(),
				Const.C_LIBL_COMMENTAIRE);
		mapComposantChampsInfosTiers.put(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfSIRET_COMPL(),
				Const.C_SIRET_COMPL);
		for (Control c : mapComposantChampsInfosTiers.keySet()) {
			listeComposantInfosTiers.add(c);
		}
		listeComposantInfosTiers.add(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getBtnReinitialiser());
		listeComposantInfosTiers.add(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getBtnAfficher());
		/////
		
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
		listeComposantFocusable.add(vue.getTfNOM_TIERS());
		listeComposantFocusable.add(vue.getDateTimeDATE_DOCUMENT());
		listeComposantFocusable.add(vue.getTfLIBELLE_DOCUMENT());
		listeComposantFocusable.add(vue.getCbTTC());

		listeComposantFocusable.add(vue.getDateTimeDATE_LIV_DOCUMENT());
		listeComposantFocusable.add(vue.getComboLocalisationTVA());


		
		listeComposantFocusable.add(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl()).getTfAdresse1());
		listeComposantFocusable.add(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl()).getTfAdresse2());
		listeComposantFocusable.add(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl()).getTfAdresse3());
		listeComposantFocusable.add(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl()).getTfCodePostal());
		listeComposantFocusable.add(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl()).getTfVille());
		listeComposantFocusable.add(((PaInfosAdresse)findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl()).getTfPays());
		listeComposantFocusable.add(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl()).getBtnReinitialiser());
		
		listeComposantFocusable.add(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl()).getTfAdresse1());
		listeComposantFocusable.add(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl()).getTfAdresse2());
		listeComposantFocusable.add(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl()).getTfAdresse3());
		listeComposantFocusable.add(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl()).getTfCodePostal());
		listeComposantFocusable.add(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl()).getTfVille());
		listeComposantFocusable.add(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl()).getTfPays());
		listeComposantFocusable.add(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl()).getBtnReinitialiser());

//		listeComposantFocusable.add(((PaInfosCondPaiement) findExpandIem(vue.getExpandBar(), paInfosCondPaiement).getControl())
//				.getTfCODE_C_PAIEMENT());
//		listeComposantFocusable.add(((PaInfosCondPaiement) findExpandIem(vue.getExpandBar(), paInfosCondPaiement).getControl())
//				.getTfLIB_C_PAIEMENT());
//		listeComposantFocusable.add(((PaInfosCondPaiement) findExpandIem(vue.getExpandBar(), paInfosCondPaiement).getControl())
//				.getTfREPORT_C_PAIEMENT());
//		listeComposantFocusable.add(((PaInfosCondPaiement) findExpandIem(vue.getExpandBar(), paInfosCondPaiement).getControl())
//				.getTfFIN_MOIS_C_PAIEMENT());
//		listeComposantFocusable.add(((PaInfosCondPaiement) findExpandIem(vue.getExpandBar(), paInfosCondPaiement).getControl())
//				.getBtnReinitialiser());
//		listeComposantFocusable.add(((PaInfosCondPaiement) findExpandIem(vue.getExpandBar(), paInfosCondPaiement).getControl())
//				.getBtnAppliquer());

		//identité du tiers
		listeComposantFocusable.add(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfEntite());
		listeComposantFocusable.add(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfCODE_ENTREPRISE());
		listeComposantFocusable.add(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfCODE_T_CIVILITE());
		listeComposantFocusable.add(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfNOM_TIERS());
		listeComposantFocusable.add(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfPRENOM_TIERS());
		listeComposantFocusable.add(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfTVA_I_COM_COMPL());	
		listeComposantFocusable.add(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfSIRET_COMPL());
		listeComposantFocusable.add(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfNUMERO_TELEPHONE());
		listeComposantFocusable.add(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfADRESSE_EMAIL());
		listeComposantFocusable.add(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfADRESSE_WEB());
		listeComposantFocusable.add(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfCODE_COMPTA());
		listeComposantFocusable.add(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfCOMPTE());
		listeComposantFocusable.add(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfTYPE_TVA_DOC());		
		listeComposantFocusable.add(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
				.getTfLIBL_COMMENTAIRE());
		listeComposantFocusable.add(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl())
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

		listeComposantFocusable.add(vue.getBtnAideDocument());
		listeComposantFocusable.add(vue.getBtnAideTiers());
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
			
			vue.getTfCODE_TIERS().addMouseListener(new MouseAdapter() {

				@Override
				public void mouseDoubleClick(MouseEvent e) {
					try {
						actSelectionTiers();
					} catch (Exception e1) {
						logger.error("", e1);
					}
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
//		mapCommand.put(C_COMMAND_DOCUMENT_REINIT_CPAIEMENT_ID, handlerReinitCPaiement);
//		mapCommand.put(C_COMMAND_DOCUMENT_APPLIQUER_CPAIEMENT_ID, handlerAppliquerCPaiement);
		mapCommand.put(C_COMMAND_DOCUMENT_REINIT_INFOSTIERS_ID, handlerReinitInfosTiers);
		mapCommand.put(C_COMMAND_DOCUMENT_AFFICHER_TIERS_ID, handlerafficherTiers);
		mapCommand.put(C_COMMAND_DOCUMENT_CREATEDOC_ID, handlerCreateDoc);
		mapCommand.put(C_COMMAND_DOCUMENT_CREATEMODELE_ID, handlerCreateModele);
		mapCommand.put(C_COMMAND_GLOBAL_SELECTION_ID, handlerSelection);
		
		/*
		 * Les sous menus pour les etiquettes utilisent cette commande avec des paramètres
		 * c'est donc cette commande qui doit être activé/désactivé et ajouter dans mapCommand
		 * par contre, comme elle n'est pas utilisable "seule", elle ne doit pas apparaitre dans
		 * les menus contextuels et donc elle ne doit pas être dans le tableau tabActionsAutres
		 */
		mapCommand.put(HeadlessEtiquette.C_COMMAND_IMPRIMER_ETIQUETTE_ID, handlerEtiquette);

		
		mapCommand.put(C_COMMAND_GLOBAL_PRECEDENT_ID, handlerPrecedent);
		mapCommand.put(C_COMMAND_GLOBAL_SUIVANT_ID, handlerSuivant);


		initFocusCommand(String.valueOf(this.hashCode()),
				listeComposantFocusable, mapCommand);
		

		if (mapActions == null)
			mapActions = new LinkedHashMap<Button, Object>();
		if(mapActionsHorsPopup== null)
			mapActionsHorsPopup=new LinkedHashMap<Button, Object>();
				
		
		mapActions.put(vue.getBtnAideDocument(),C_COMMAND_GLOBAL_AIDE_ID);
		mapActionsHorsPopup.put(vue.getBtnAideTiers(),C_COMMAND_GLOBAL_AIDE_ID);

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

				
		mapActionsHorsPopup.put(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl()).getBtnReinitialiser(),C_COMMAND_DOCUMENT_REINIT_ADR_FACT_ID);

		mapActionsHorsPopup.put(((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl()).getBtnReinitialiser(),C_COMMAND_DOCUMENT_REINIT_ADR_LIV_ID);

//		mapActionsHorsPopup.put(((PaInfosCondPaiement) findExpandIem(vue.getExpandBar(), paInfosCondPaiement).getControl()).getBtnReinitialiser(),C_COMMAND_DOCUMENT_REINIT_CPAIEMENT_ID);
//		mapActionsHorsPopup.put(((PaInfosCondPaiement) findExpandIem(vue.getExpandBar(), paInfosCondPaiement).getControl()).getBtnAppliquer(),C_COMMAND_DOCUMENT_APPLIQUER_CPAIEMENT_ID);
		
		mapActionsHorsPopup.put(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl()).getBtnReinitialiser(),C_COMMAND_DOCUMENT_REINIT_INFOSTIERS_ID);
		mapActionsHorsPopup.put(((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl()).getBtnAfficher(),C_COMMAND_DOCUMENT_AFFICHER_TIERS_ID);

		Object[] tabActionsAutres = new Object[] {C_COMMAND_GLOBAL_REFRESH_ID , C_COMMAND_GLOBAL_SELECTION_ID/*, HeadlessEtiquette.C_COMMAND_IMPRIMER_ETIQUETTE_ID*/};
		mapActions.put(null, tabActionsAutres);
		
		mapActions.put(vue.getPaBtnAvecAssistant().getPaBtnAssitant()
				.getBtnPrecedent(), C_COMMAND_GLOBAL_PRECEDENT_ID);
		mapActions.put(vue.getPaBtnAvecAssistant().getPaBtnAssitant()
				.getBtnSuivant(), C_COMMAND_GLOBAL_SUIVANT_ID);

		

		branchementBouton(mapActionsHorsPopup);
		vue.getBtnAutres().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				popupMenuFormulaire.setVisible(true);
			}
		});
	}

	public SWTPaEditorFactureController getThis() {
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
					boolean demande=!((IHMEnteteFacture) selectedFacture).factureEstVide(taDocument.dateDansExercice(new Date()));
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
			if(getFocusCourantSWT() instanceof Button){
				if(getFocusCourantSWT().equals(vue.getBtnAideDocument()))setFocusCourantSWT(vue.getTfCODE_DOCUMENT());
				if(getFocusCourantSWT().equals(vue.getBtnAideTiers()))setFocusCourantSWT(vue.getTfCODE_TIERS());
			}
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
						if(getFocusAvantAideSWT().equals(vue.getTfCODE_TIERS())||getFocusAvantAideSWT().equals(vue.getTfNOM_TIERS())||
								getFocusAvantAideSWT().equals(vue.getBtnAideTiers())){
							if(getFocusAvantAideSWT().equals(vue.getTfCODE_TIERS()))
							validateUIField(Const.C_CODE_TIERS, ((ResultAffiche) evt.getRetour()).getResult());
							else if(getFocusAvantAideSWT().equals(vue.getTfNOM_TIERS()))validateUIField(Const.C_NOM_TIERS, ((ResultAffiche) evt.getRetour()).getResult());
							TaTiers u = null;
							TaTiersDAO t = new TaTiersDAO(getEm());
							u = t.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
							t = null;
							taDocument.setTaTiers(u);
							String nomTiers=taDocument.getTaTiers().getNomTiers();
							((IHMEnteteFacture)selectedFacture).setLibelleDocument("Facture N°"+taDocument.getCodeDocument()+" - "+nomTiers);
							((IHMEnteteFacture)selectedFacture).setNomTiers(nomTiers);							
							((IHMEnteteFacture)selectedFacture).setCodeTiers(taDocument.getTaTiers().getCodeTiers());
							
							PropertyUtils.setSimpleProperty(taDocument, Const.C_LIBELLE_DOCUMENT, ((IHMEnteteFacture)selectedFacture).getLibelleDocument());
							if(taInfosDocument!=null) 
								taInfosDocument.setNomTiers(nomTiers);
							if(taDocument.getTaInfosDocument()!=null)
								taDocument.getTaInfosDocument().setNomTiers(nomTiers);
							
							controllerTotaux.calculTypePaiement(true);
							//controllerTotaux.initialisationDesCPaiement( true);
							if(vue.getCbTTC().isEnabled()){
								taDocument.setTtc(u.getTtcTiers());
								((IHMEnteteFacture)selectedFacture).setTtc(LibConversion.intToBoolean(taDocument.getTtc()));
							}
							
							changementTiers(true);
						}	
						if(getFocusAvantAideSWT().equals(vue.getTfCODE_DOCUMENT())||
								getFocusAvantAideSWT().equals(vue.getBtnAideDocument())){
//							TaFacture u = null;
//							TaFactureDAO t = new TaFactureDAO(getEm());
//							u = t.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
//							t = null;
//							taDocument=u;
//							taDocument.setLegrain(true);
							try {
//								remonterDocument(taDocument.getCodeDocument());
								remonterDocument(vue.getTfCODE_DOCUMENT().getText());
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
//				if (focusDansCPaiment(getFocusCourantSWT())) {
//					String id = evt.getRetour().getIdResult();
//
//					TaCPaiementDAO taCPaiementDAO = new TaCPaiementDAO(getEm());
//					TaCPaiement taCPaiement = taCPaiementDAO.findById(Integer.parseInt(id));
//					mapperModelToUICPaiementInfosDocument.map(taCPaiement, ((IHMInfosCPaiement) selectedCPaiement));
//					calculDateEcheance();
//				}
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
	

//	public void calculDateEcheance() {
////		TaTCPaiementDAO taTCPaiementDAO = new TaTCPaiementDAO(getEm());
////		TaTCPaiement typeCP = taTCPaiementDAO.findByCode(TaTCPaiement.C_CODE_TYPE_FACTURE);
////		TaCPaiement conditionDoc = null;
////		TaCPaiement conditionTiers = null;
////		TaCPaiement conditionSaisie = null;
////		
////		if(typeCP!=null) conditionDoc = typeCP.getTaCPaiement();
////		if(taDocument!=null && taDocument.getTaTiers()!=null) conditionTiers = taDocument.getTaTiers().getTaCPaiement();
//////		if(taDocument!=null 
//////				&& taDocument.getTaInfosDocument()!=null) {
//////			conditionSaisie = new TaCPaiement();
//////			conditionSaisie.setReportCPaiement(taDocument.getTaInfosDocument().getReportCPaiement());
//////			conditionSaisie.setFinMoisCPaiement(taDocument.getTaInfosDocument().getFinMoisCPaiement());
//////		}
////		if(((IHMInfosCPaiement)selectedCPaiement)!=null) { 
////			conditionSaisie = new TaCPaiement();
////			if(((IHMInfosCPaiement)selectedCPaiement).getReportCPaiement()!=null)
////				conditionSaisie.setReportCPaiement(((IHMInfosCPaiement)selectedCPaiement).getReportCPaiement());
////			if(((IHMInfosCPaiement)selectedCPaiement).getFinMoisCPaiement()!=null)
////				conditionSaisie.setFinMoisCPaiement(((IHMInfosCPaiement)selectedCPaiement).getFinMoisCPaiement());
////		}
////		
////		//on applique toute les conditions par ordre décroissant de priorité, la derniere valide est conservée
////		Date nouvelleDate = null;
////		if(conditionDoc!=null) {
////			nouvelleDate = conditionDoc.calculeNouvelleDate(taDocument.getDateDocument());
////		}
////		if(conditionTiers!=null) {
////			nouvelleDate = conditionTiers.calculeNouvelleDate(taDocument.getDateDocument());
////		}
////		if(conditionSaisie!=null) {
////			nouvelleDate = conditionSaisie.calculeNouvelleDate(taDocument.getDateDocument());
////		}
////		taDocument.setDateEchDocument(nouvelleDate);
//		
//		
//		if(((IHMEnteteFacture)selectedFacture).getIdDocument()==0) {
//			Integer report = null;
//			Integer finDeMois = null;
//			if(((IHMInfosCPaiement)selectedCPaiement)!=null) { 
//				if(((IHMInfosCPaiement)selectedCPaiement).getReportCPaiement()!=null)
//					report = ((IHMInfosCPaiement)selectedCPaiement).getReportCPaiement();
//				if(((IHMInfosCPaiement)selectedCPaiement).getFinMoisCPaiement()!=null)
//					finDeMois = ((IHMInfosCPaiement)selectedCPaiement).getFinMoisCPaiement();
//			}
//			((IHMEnteteFacture)selectedFacture).setDateEchDocument(taDocument.calculDateEcheance(report, finDeMois));
//		}
//	}

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
				initTTC();
			}
			if(vue.getComboLocalisationTVA().isEnabled()  && taDocument.getTaTiers()!=null){//
				//new TaTTvaDocDAO().findByCode(taDocument.getTaTiers().getTaTTvaDoc().getCodeTTvaDoc());
					TaTTvaDoc t = new TaTTvaDocDAO().findByCode(taDocument.getTaTiers().getTaTTvaDoc().getCodeTTvaDoc());
				viewerComboLocalisationTVA.setSelection(new StructuredSelection(t),true);
				//vue.getCbTTC().setSelection(taDocument.getTaTiers().getTaTTvaDoc());
				initLocalisationTVA();
			}
			////////////////////////////////////////////////////
			//boolean majAdr = false;
			if(((IHMEnteteFacture)selectedFacture).getIdDocument()!=0 && 
					((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
					|| (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0))) {
				if(!idTiersDocumentOriginal.equals(((IHMEnteteFacture)selectedFacture).getIdTiers())){
					//on change de tiers sur une facture deja enregistree
					//isa le 4 mars 2010
					mapperUIToModelDocumentVersInfosDoc.map(taDocument,taInfosDocument);
					////
					String message = "Les adresses et conditions du document ont été modifiées en fonction du nouveau tiers.";
					if(findStatusLineManager()!=null)
						findStatusLineManager().setMessage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_EXCLAMATION),message);
					change=true;
					actReinitAdrFact();
					actReinitAdrLiv();
					actReinitInfosTiers();
					controllerTotaux.initialisationDesCPaiement(true);
					taDocument.mettreAJourDateEcheanceReglements();
					initInfosFacture();
					
					
					findExpandIem(vue.getExpandBar(), paInfosAdresseFact).setExpanded(true);
//					//majAdr = MessageDialog.openQuestion(vue.getShell(), "Attention", "Mettre à jour les adresses");
//					String message = "Les adresses de la facture n'ont pas été modifiées en fonction du nouveau tiers." +
//							"Vous pouvez les réinitialiser en cliquant sur <Raffraîchir>";
//					if(findStatusLineManager()!=null)
//						findStatusLineManager().setMessage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_EXCLAMATION),message);
//					//majAdr=true;
					
//					final ToolTip tip = new ToolTip(vue.getShell(), SWT.BALLOON | SWT.ICON_INFORMATION);
//					tip.setMessage(message);
//					tip.setText("Attention");
//
////					Tray tray = vue.getShell().getDisplay().getSystemTray();
////					if (tray != null) {
////						TrayItem item = new TrayItem(tray, SWT.NONE);
////						item.setImage(LibrairiesEcranPlugin.getImageDescriptor(C_IMAGE_INSERER).createImage());
////						item.setVisible(true);
////						item.setToolTip(tip);
////					} else {
//						int x = vue.getExpandBar().getItem(0).getControl().getLocation().x+vue.getExpandBar().getItem(0).getControl().getBounds().width;
//						int y = vue.getExpandBar().getItem(0).getControl().getLocation().y;
//
//						Point p = vue.getExpandBar().getItem(0).getControl().toDisplay(x,y);
//						//tip.setLocation(400, 400);
//						tip.setLocation(p);
////					}
//					tip.setVisible(true);

				}
			}
			////////////////////////////////////////////////////
			
			//HashMap<String,String[]> map = new HashMap<String,String[]>();
			//map.put("a.taTiers."+Const.C_ID_TIERS,new String[]{"=",LibConversion.integerToString(taFacture.getTaTiers().getIdTiers())});
			
//			map.put("a.taTiers."+Const.C_ID_TIERS,new String[]{"=",LibConversion.integerToString(taFacture.getTaTiers().getIdTiers())});
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
			controllerTotaux.calculDateEcheance(change);
			controllerTotaux.actRefreshListeAcompteDisponible();
			controllerTotaux.actRefreshListeAvoirDisponible();
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
		if(((IHMEnteteFacture)selectedFacture).getCodeDocument()!=null)
			taInfosDocument = daoInfosFacture.findByCodeFacture(((IHMEnteteFacture)selectedFacture).getCodeDocument());
		else
			if(((IHMEnteteFacture)selectedFacture).getCodeDocument()==null)	
				taInfosDocument = new TaInfosFacture();
		if(recharger){
			if(taDocument.getTaTiers()!=null)
				taDocument.setTaTiers(new TaTiersDAO(new JPABdLgr().getEntityManager()).findById(taDocument.getTaTiers().getIdTiers()));
			try {
				actModifier();
			} catch (Exception e) {
				logger.error("", e);
			}
		}
		
		if(typeARecharger==Const.RECHARGE_ADR_FACT||typeARecharger=="")
			initialisationDesAdrFact(recharger);
		if(typeARecharger==Const.RECHARGE_ADR_LIV||typeARecharger=="")
			initialisationDesAdrLiv(recharger);
//		if(typeARecharger==Const.RECHARGE_C_PAIEMENT||typeARecharger=="")
//			controllerTotaux.initialisationDesCPaiement(recharger);
		if(typeARecharger==Const.RECHARGE_INFOS_TIERS||typeARecharger=="")
			initialisationDesInfosTiers(recharger);
		
		initialisationDesNotesTiers();
		
//		TaTAdrDAO taTAdrDAO = new TaTAdrDAO(getEm());
//		//taInfosFacture = daoInfosFacture.findByCodeFacture(taFacture.getCodeFacture());
//		if(((IHMEnteteFacture)selectedFacture).getCodeDocument()!=null)
//			taInfosDocument = daoInfosFacture.findByCodeFacture(((IHMEnteteFacture)selectedFacture).getCodeDocument());
//		else
//			taInfosDocument = new TaInfosFacture();
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
	
	private String codeTiersPrecedentNoticationNotes = "";
	public void initialisationDesNotesTiers() {
		getPaNotesTiersController().setMasterEntity(taDocument.getTaTiers());
		fireUpdateMasterEntity(new UpdateMasterEntityEvent(this));
		
		int nbNoteApresFiltrage = getPaNotesTiersController().getGrille().getItemCount();
		if(taDocument.getTaTiers()!=null && nbNoteApresFiltrage>0){
			String message = nbNoteApresFiltrage==1 ? "MESSAGE" : "MESSAGES";
			findExpandIem(vue.getExpandBar(), paNotesTiers).setText("Notes ("+nbNoteApresFiltrage+" "+message+")");
//			findExpandIem(vue.getExpandBar(), paNotesTiers).setExpanded(true);
			
			//notification de la présence de notes pour ce tiers
			if(!codeTiersPrecedentNoticationNotes.equals(taDocument.getTaTiers().getCodeTiers())) {
				NotifierDialog.notify("Bureau de gestion", 
						nbNoteApresFiltrage+" note(s) disponible(s) pour ce tiers ("+taDocument.getTaTiers().getCodeTiers()+").", 
						NotificationType.INFO
						,vue.getTfCODE_TIERS().toDisplay(vue.getTfCODE_TIERS().getLocation().x, vue.getTfCODE_TIERS().getLocation().y).x + vue.getTfCODE_TIERS().getSize().x + 10, 
						vue.getTfCODE_TIERS().toDisplay(vue.getTfCODE_TIERS().getLocation().x, vue.getTfCODE_TIERS().getLocation().y).y
				);
				System.err.println(taDocument.getTaTiers().getCodeTiers()+" avant : "+codeTiersPrecedentNoticationNotes);
				codeTiersPrecedentNoticationNotes = taDocument.getTaTiers().getCodeTiers();
				System.err.println(taDocument.getTaTiers().getCodeTiers()+" apres : "+codeTiersPrecedentNoticationNotes);
			}
		} else {
			findExpandIem(vue.getExpandBar(), paNotesTiers).setText("Notes");
//			findExpandIem(vue.getExpandBar(), paNotesTiers).setExpanded(false);
		}
		
	}
	
	public void initialisationDesAdrFact(Boolean recharger){
		TaTAdrDAO taTAdrDAO = new TaTAdrDAO(getEm());
		
		//taTAdrDAO.setEm(taTAdrDAO.getEntityManager());

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
			if(recharger){
				if (!modelAdresseFact.getListeObjet().isEmpty())
				mapperUIToModelAdresseFactVersInfosDoc.map(modelAdresseFact.getListeObjet().getFirst(), taInfosDocument);
				else
					mapperUIToModelAdresseFactVersInfosDoc.map(new IHMAdresseInfosFacturation(), taInfosDocument);
			}
			
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
				new LgrDozerMapper<TaInfosFacture, IHMIdentiteTiers>().map(taInfosDocument, ihmInfosTiers);					
			else
			{
				new LgrDozerMapper<TaTiers, IHMIdentiteTiers>().map(taDocument.getTaTiers(), ihmInfosTiers);				
			}
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
			new LgrDozerMapper<IHMIdentiteTiers,TaInfosFacture >().map( ihmInfosTiers,taInfosDocument);
		if(ihmInfosTiers!=null&& ihmInfosTiers.getNomTiers()!=null && selectedFacture!=null && !ihmInfosTiers.getNomTiers().equals(((IHMEnteteFacture)selectedFacture).getNomTiers())){
			if(((IHMEnteteFacture)selectedFacture).getLibelleDocument()!=null && ((IHMEnteteFacture)selectedFacture).getLibelleDocument().contains("Facture N°"))
				((IHMEnteteFacture)selectedFacture).setLibelleDocument("Facture N°"+taDocument.getCodeDocument()+" - "+ihmInfosTiers.getNomTiers());
			taDocument.setLibelleDocument(((IHMEnteteFacture)selectedFacture).getLibelleDocument());
			((IHMEnteteFacture)selectedFacture).setNomTiers(ihmInfosTiers.getNomTiers());
		}
	}

	public void initialisationDesAdrLiv(Boolean recharger){

		TaTAdrDAO taTAdrDAO = new TaTAdrDAO(getEm());
		//taTAdrDAO.setEm(taTAdrDAO.getEntityManager());
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
			if(leTiersADesAdresseLiv && taAdresse.getTaTAdr()!=null &&  !taAdresse.getTaTAdr().getCodeTAdr().equals(typeAdresseLivraison)){
				modelAdresseLiv.getListeObjet().add(modelAdresseLiv.getMapperModelToUI().map(taAdresse, classModelAdresseLiv));
			} else {
				modelAdresseLiv.getListeObjet().add(modelAdresseLiv.getMapperModelToUI().map(taAdresse, classModelAdresseLiv));
			}
		}
		}
		//ajout de l'adresse de livraison inscrite dans l'infos facture
		if(taInfosDocument!=null) {
			if(recharger){
				if (!modelAdresseLiv.getListeObjet().isEmpty())
				mapperUIToModelAdresseLivVersInfosDoc.map(modelAdresseLiv.getListeObjet().getFirst(), taInfosDocument);
				else
					mapperUIToModelAdresseLivVersInfosDoc.map(new IHMAdresseInfosLivraison(), taInfosDocument);
			}
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
	

	


	
	@Override
	protected void actInserer() throws Exception {
		if (!vue.isDisposed()) {
			try {
				if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
					setIhmOldEnteteFacture();
					initialisationEcran("");
					//dao.getEntityManager().clear();
					taDocument = new TaFacture(true);
					controllerTotaux.setMasterEntity(taDocument);
					controllerLigne.setMasterEntity(taDocument);
					mapperModelToUI.map(taDocument, (IHMEnteteFacture) selectedFacture);
//					changementTiers(true);
					((IHMEnteteFacture) selectedFacture).setCodeDocument(dao.genereCode());
					validateUIField(Const.C_CODE_DOCUMENT,((IHMEnteteFacture) selectedFacture).getCodeDocument());//permet de verrouiller le code genere
					((IHMEnteteFacture) selectedFacture).setCommentaire(FacturePlugin.getDefault().getPreferenceStoreProject().
							getString(fr.legrain.facture.preferences.PreferenceConstants.COMMENTAIRE));
					validateUIField(Const.C_CODE_DOCUMENT,((IHMEnteteFacture) selectedFacture).getCodeDocument());
					taDocument.setCodeDocument(((IHMEnteteFacture) selectedFacture).getCodeDocument());
					changementTiers(true);
					Date date = new Date();
					TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
					TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
					// si date inférieur à dateDeb dossier
					if (LibDate.compareTo(date, taInfoEntreprise.getDatedebInfoEntreprise()) == -1) {
						((IHMEnteteFacture) selectedFacture).setDateDocument(taInfoEntreprise.getDatedebInfoEntreprise());
//						((IHMEnteteFacture) selectedFacture).setDateEchDocument(taInfoEntreprise.getDatedebInfoEntreprise());
						((IHMEnteteFacture) selectedFacture).setDateLivDocument(taInfoEntreprise.getDatedebInfoEntreprise());
					} else
						// si date supérieur à dateFin dossier
						if (LibDate.compareTo(date, taInfoEntreprise.getDatefinInfoEntreprise()) == 1) {
							((IHMEnteteFacture) selectedFacture).setDateDocument(taInfoEntreprise.getDatefinInfoEntreprise());
//							((IHMEnteteFacture) selectedFacture).setDateEchDocument(taInfoEntreprise.getDatefinInfoEntreprise());
							((IHMEnteteFacture) selectedFacture).setDateLivDocument(taInfoEntreprise.getDatefinInfoEntreprise());
						} else {
							((IHMEnteteFacture) selectedFacture).setDateDocument(new Date());
//							((IHMEnteteFacture) selectedFacture).setDateEchDocument(new Date());
							((IHMEnteteFacture) selectedFacture).setDateLivDocument(new Date());
						}
					((IHMEnteteFacture) selectedFacture).setCodeTiers("");
					((IHMEnteteFacture) selectedFacture).setIdAdresse(0);
					((IHMEnteteFacture) selectedFacture).setIdAdresseLiv(0);
					
					// TODO récupérer un code_t_paiement par defaut ou le premier
					// dans la table
					controllerTotaux.calculTypePaiement(false);
					
//					String typePaiementDefaut =DocumentPlugin.getDefault().getPreferenceStore().
//							getString(fr.legrain.document.preferences.PreferenceConstants.TYPE_PAIEMENT_DEFAUT); 
//					TaTPaiementDAO taTPaiementDAO = new TaTPaiementDAO(getEm());
//					if(typePaiementDefaut!=null){
//						TaTPaiement taTPaiement=null;
//						try {
//							taTPaiement = taTPaiementDAO.findByCode(typePaiementDefaut);
//						} catch (Exception e) {
//						}						
//						/*
//						 * Le champ Type de paiement n'est pas gere dans ce controlleur (non present dans l'interface pour cet onglet)
//						 * donc les fonctions ctrlTousLesChampsAvantEnregistrementSWT() ou validateUI() ne permetent pas d'initialiser
//						 * l'objet correctement. Il faut le faire explicitement. 
//						 */
////						if(taTPaiement!=null) {
////							((IHMEnteteFacture) selectedFacture).setCodeTPaiement(typePaiementDefaut);
////							taDocument.setTaTPaiement(taTPaiement);
////						}
//					}
					mapperUIToModel.map(((IHMEnteteFacture) selectedFacture), taDocument);
					
					
					
					fireChangementMaster(new ChangementMasterEntityEvent(this,taDocument));
					controllerTotaux.calculDateEcheance(true);
					//dao.insertion(swtFacture.setSWTFacture(ihmEnteteFacture));
					dao.inserer(taDocument);
					controllerLigne.initTTC();
					controllerLigne.actInserer();
					controllerTotaux.remonterDocument();

					controllerTotaux.actRefreshAffectation();
					controllerTotaux.actRefreshAffectationAvoirs();
					controllerTotaux.actRefreshReglements(null);
					controllerTotaux.refreshVue();
					controllerTotaux.actRefreshListeAcompteDisponible();
					controllerTotaux.actRefreshListeAvoirDisponible();
					
					ParamAfficheLFacture paramAfficheLFacture = new ParamAfficheLFacture();
					paramAfficheLFacture.setModeEcran(EnumModeObjet.C_MO_INSERTION);
					//paramAfficheLFacture.setIdFacture(swtFacture.getEntete().getCODE());
					paramAfficheLFacture.setIdFacture(taDocument.getCodeDocument());
					paramAfficheLFacture.setInitFocus(false);
					
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
		try {
			boolean continuer=true;
			if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
				if(setIhmOldEnteteDocumentRefresh()){
					continuer=true;
				}
				//gestion document dans exercice
				if(taDocument.dateDansExercice(taDocument.getDateDocument()).compareTo(taDocument.getDateDocument())!=0){
					MessageDialog.openError(vue.getShell(),//
							MessagesEcran.getString("Message.Attention"),
							MessagesEcran.getString("Document.Message.HorsExercice"));
					continuer=false;
				}

				if(continuer)	{
					if(taDocument.getExport()==1){
						continuer=MessageDialog.openConfirm(vue.getShell(),
								MessagesEcran.getString("Message.Attention"),
								MessagesEcran.getString("Document.Message.Exporte"));
					}
				}
				if(continuer)	{
//					if(!LgrMess.isDOSSIER_EN_RESEAU()){
//						setIhmOldEnteteFacture();
//						taDocument = dao.findById(((IHMEnteteFacture) selectedFacture).getIdDocument());
//						dao.modifier(taDocument);						
//					}					
//					else{
						//if(setIhmOldEnteteDocumentRefresh()){
							dao.modifier(taDocument);
						//}
//					}
				} else{
					//remonterDocument(taDocument.getCodeDocument(),false);
					taDocument =rechercheFacture(taDocument.getCodeDocument(), false, true);
					taInfosDocument=taDocument.getTaInfosDocument();
					mapperModelToUI.map(taDocument,((IHMEnteteFacture) selectedFacture));
					sortieChamps();
					getControllerTotaux().sortieChamps();
					getControllerLigne().sortieChamps();

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
	 finally {
		// activeModifytListener();
	}
	}

	@Override
	protected void actSupprimer() throws Exception {
		boolean verrouLocal = VerrouInterface.isVerrouille();
		VerrouInterface.setVerrouille(true);
		TaComptePointDAO daoComptePoint = new TaComptePointDAO(getEm());
		EntityTransaction transaction = dao.getEntityManager().getTransaction();
		try {
			String mess="";
			mess="Ce document est lié à :";

			if(taDocument.getTaRAcomptes().size()>0){
				mess+=Const.finDeLigne+"- "+String.valueOf(taDocument.getTaRAcomptes().size())+" acomptes.";
			}
			
			if(taDocument.getTaRAvoirs().size()>0){
				mess+=Const.finDeLigne+"- "+String.valueOf(taDocument.getTaRAvoirs().size())+" avoirs.";
			}
			if(taDocument.getTaRReglements().size()>0){
				mess+=Const.finDeLigne+"- "+String.valueOf(taDocument.getTaRReglements().size())+" règlements.";
			}
			if(taDocument.getTaRAcomptes().size()>0 ||taDocument.getTaRAvoirs().size()>0 || taDocument.getTaRReglements().size()>0){
				if (!MessageDialog.openConfirm(vue.getShell(),
						MessagesEcran.getString("Message.Attention"),mess +
								Const.finDeLigne+"Etes-vous sûr de vouloir le supprimer ?")) 
					throw new Exception();
			}
			
			
			if(isUtilise())MessageDialog.openInformation(vue.getShell(), fr.legrain.tiers.ecran.MessagesEcran
					.getString("Message.Attention"), fr.legrain.tiers.ecran.MessagesEcran
					.getString("Message.suppression"));
			else			if (MessageDialog.openConfirm(vue.getShell(),
							MessagesEcran.getString("Message.Attention"),
							MessagesEcran.getString("Document.Message.Supprimer"))) {

				dao.begin(transaction);
				TaFacture u = dao.findById(((IHMEnteteFacture) selectedFacture).getIdDocument());
				try {
					dao.removeTousLesAbonnements(u);
					dao.removeTousLesSupportAbons(u);
					daoComptePoint.removeTousLesPointsBonus(u,true);
					if(!transaction.isActive())dao.begin(transaction);
					dao.supprimer(u);	
				u.removeTousRAcomptes(getEm());
				u.removeTousRAvoirs(getEm());
				u.removeTousRReglements(getEm());
				} catch (Exception e) {
					throw e;
				}
				dao.commit(transaction);
				removeCodeDocument(u);

				taDocument=null;
				dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
				initialisationEcran(null);
				actInserer();
			}
		} catch (Exception e1) {
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
			String codeFacture = vue.getTfCODE_DOCUMENT().getText();
			boolean message = !silencieu && messageAffiche && !((IHMEnteteFacture) selectedFacture).factureEstVide(taDocument.dateDansExercice(new Date()));
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
								dao.annulerCodeGenere(((IHMEnteteFacture) selectedFacture).getCodeDocument(),dao.getChampGenere());
								dao.annulerCodeGenere(codeFacture,dao.getChampGenere());
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
									initialisationEcran(codeFacture);
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
							dao.annulerCodeGenere(((IHMEnteteFacture) selectedFacture).getCodeDocument(),dao.getChampGenere());
							dao.annulerCodeGenere(codeFacture,dao.getChampGenere());
							taDocument=dao.annulerT(taDocument);
							if (!dejaFermer) {
								initialisationEcran(codeFacture);
								if (insertion) actInserer();
							}
							hasAnnuler = true;
						} else if (!hasAnnuler && repondu) {
							dao.annulerCodeGenere(((IHMEnteteFacture) selectedFacture).getCodeDocument(),dao.getChampGenere());
							dao.annulerCodeGenere(codeFacture,dao.getChampGenere());
							taDocument=dao.annulerT(taDocument);
							initialisationEcran(codeFacture);
							hasAnnuler = true;
						}
						break;
					case C_MO_CONSULTATION:
						dao.annulerCodeGenere(((IHMEnteteFacture) selectedFacture).getCodeDocument(),dao.getChampGenere());
						dao.annulerCodeGenere(codeFacture,dao.getChampGenere());

						if (vue.getTfCODE_DOCUMENT().isFocusControl() && !message) {// si facture vide avant annulation
							actFermer();
							dejaFermer = true;
						} else {
							initialisationEcran(codeFacture);
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
		String simpleNameClass = TaFacture.class.getSimpleName();
		//TaFactureDAO daoTemp = new TaFactureDAO();		
		//TaFacture documentLocale =daoTemp.findById(((TaFacture)taDocument).getIdDocument());
		
		TaFacture documentLocale =taDocument;
		documentLocale.calculRegleDocument();
		documentLocale.calculRegleDocumentComplet();
//#JPA  
		Collection<TaFacture> collectionTaFacture = new LinkedList<TaFacture>();
		
		for (TaRReglement rReglement : documentLocale.getTaRReglements()) {
			if(rReglement.getTaReglement().getTaCompteBanque()!=null){
				rReglement.getTaReglement().getTaCompteBanque().getCodeBanque();
				//rReglement.getTaReglement().getTaCompteBanque().getTaTBanque().getCodeTBanque();
			}
		}
		
		for (TaCompteBanque comptes : documentLocale.getTaTiers().getTaCompteBanques()) {
			if(comptes!=null){
				comptes.getCodeBanque();
			}
			if(comptes.getTaTBanque()!=null){
				comptes.getTaTBanque().getCodeTBanque();
			}			
		}
		


		collectionTaFacture.add(documentLocale);
//		ConstEdition constEdition = new ConstEdition(daoTemp.getEntityManager()); 
		ConstEdition constEdition = new ConstEdition(dao.getEntityManager());
		constEdition.setFlagEditionMultiple(true);
		

		
		Bundle bundleCourant = FacturePlugin.getDefault().getBundle();
		String namePlugin = bundleCourant.getSymbolicName();
		
		
		/** 01/03/2010 modifier les editions (zhaolin) **/
		baseImpressionEdition.setObject(documentLocale);
		baseImpressionEdition.setConstEdition(constEdition);
		baseImpressionEdition.setCollection(collectionTaFacture);
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

		if(gestionnaireTraite!=null){
			typeTraite = ((IEditionTraite)gestionnaireTraite).getTypeTraite();
		}
		baseImpressionEdition.setTypeTraite(typeTraite);
		
		IPreferenceStore preferenceStore = FacturePlugin.getDefault().getPreferenceStore();
		baseImpressionEdition.impressionEdition(preferenceStore,simpleNameClass,/*true,*/null,
												namePlugin,ConstEdition.FICHE_FILE_REPORT_FACTURE, 
												true,false,true,false,false,false,"");
		
		
		
//		if(gestionnaireTraite!=null){
//			((IEditionTraite)gestionnaireTraite).setShell(vue.getShell());
//			String typeTraite = ((IEditionTraite)gestionnaireTraite).getTypeTraite();
//			((IEditionTraite)gestionnaireTraite).
//			ImpressionTraite(factureLocale.rechercheSiDocumentContientTraite(typeTraite),baseImpressionEdition);
//		}
		/************************************************/
				
		if (controllerTotaux.impressionAutoCourrier()) {
			imprimeCourrier(controllerTotaux.listeCourrierAImprimer(), taDocument, FichierDonneesAdresseFacture.TYPE_ADRESSE_FACTURATION);
		}
		
		
//		taDocument.getLibelleDocument().substring(0, 20)
//		taDocument.getLibelleDocument().length()
	}
	

	
	
//	protected void ImpressionTraite(Collection<TaRReglement> listeTraite,Shell shell,EntityManager em) throws Exception {
//		if(listeTraite!=null && listeTraite.size()>0){
//			String simpleNameClass = TaRReglement.class.getSimpleName();//#JPA  
//
//			TaRReglement rReglement =listeTraite.iterator().next();
//
//			ConstEdition constEdition = new ConstEdition(em); 
//			constEdition.setFlagEditionMultiple(true);
//
//
//			Bundle bundleCourant = FacturePlugin.getDefault().getBundle();
//			String namePlugin = bundleCourant.getSymbolicName();
//
//			/** 01/03/2010 modifier les editions (zhaolin) **/
//			impressionEdition.setObject(rReglement);
//			impressionEdition.setConstEdition(constEdition);
//			impressionEdition.setCollection(listeTraite);
//			impressionEdition.setIdEntity(rReglement.getId());
//
//
//			String reportFileLocationDefaut = constEdition.pathFichierEditionsSpecifiques(ConstEdition.FICHE_FILE_REPORT_TRAITE, namePlugin);
//			impressionEdition.impressionEditionTypeEntity(reportFileLocationDefaut,"Traites");
//		}
//	}
	@Override
	protected boolean aideDisponible() {
		boolean result = false;
		switch ((SWTPaEditorFactureController.this.dao.getModeObjet().getMode())) {
		case C_MO_CONSULTATION:
			if (getFocusCourantSWT().equals(vue.getTfCODE_DOCUMENT())
					|| getFocusCourantSWT().equals(vue.getTfCODE_TIERS())||
					getFocusCourantSWT().equals(vue.getBtnAideDocument())||
							getFocusCourantSWT().equals(vue.getBtnAideTiers()))
				result = true;
			break;
		case C_MO_EDITION:
		case C_MO_INSERTION:
			if (getFocusCourantSWT().equals(vue.getTfCODE_TIERS())
					|| getFocusCourantSWT().equals(vue.getTfNOM_TIERS())
					||getFocusCourantSWT().equals(vue.getTfCODE_DOCUMENT())||
					getFocusCourantSWT().equals(vue.getBtnAideDocument())||
					getFocusCourantSWT().equals(vue.getBtnAideTiers()))
				result = true;
			if (focusDansAdresse(getFocusCourantSWT())
					|| focusDansAdresse_Liv(getFocusCourantSWT())
					//|| focusDansCPaiment(getFocusCourantSWT())
					)
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
			paramAfficheSelectionVisualisation.setModule("facture");
			paramAfficheSelectionVisualisation.setNomClassController(nomClassController);
			paramAfficheSelectionVisualisation.setNomRequete(Const.C_NOM_VU_FACTURE);

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
			vue.getTfNOM_TIERS().forceFocus();
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
				
				
				switch ((SWTPaEditorFactureController.this.dao.getModeObjet().getMode())) {
				case C_MO_CONSULTATION:
//					if (getFocusCourantSWT().equals(vue.getTfCODE_FACTURE())||getFocusCourantSWT().equals(vue.getBtnAideFacture())) {
//						editorCreationId = EditorFacture.ID_EDITOR;
//						editorInputCreation = new EditorInputFacture();
//						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
//						controllerEcranCreation = this;
//						paramAfficheAideRecherche.setAfficheDetail(false);
//						
//						
//						paramAfficheAideRecherche.setTypeEntite(TaFacture.class);
//						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_DOCUMENT);
//						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_FACTURE().getText());
//						paramAfficheAideRecherche.setControllerAppelant(SWTPaEditorFactureController.this);
//						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaFactureDAO,TaFacture>(dao,IHMAideFacture.class));
//						paramAfficheAideRecherche.setTypeObjet(IHMAideFacture.class);
//						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_DOCUMENT);
//						
//						//permet de paramètrer l'affichage remplie ou non de l'aide
//
//						try {
//							dao.annulerCodeGenere(taDocument.getCodeDocument(), dao.getChampGenere());
////							ibTaTable.annulerCodeGenere(ibTaTable.getChamp_Obj(Const.C_CODE_FACTURE),Const.C_CODE_FACTURE);
//						} catch (Exception ex) {
//							logger.error(ex);
//						}
//					}
//					if ((getFocusCourantSWT().equals(vue.getTfCODE_TIERS())||getFocusCourantSWT().equals(vue.getBtnAideTiers()))) {
//						//permet de paramètrer l'affichage remplie ou non de l'aide
//						PaTiersSWT paTiersSWT = new PaTiersSWT(s2, SWT.NULL);
//						SWTPaTiersController swtPaTiersController = new SWTPaTiersController(paTiersSWT);
//
//						editorCreationId = TiersMultiPageEditor.ID_EDITOR;
//						
//						editorInputCreation = new EditorInputTiers();
//
//						ParamAfficheTiers paramAfficheTiers = new ParamAfficheTiers();
//						paramAfficheAideRecherche.setJPQLQuery(new TaTiersDAO(getEm()).getTiersActif());
//						paramAfficheTiers.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//						paramAfficheTiers.setEcranAppelant(paAideController);
//						controllerEcranCreation = swtPaTiersController;
//						parametreEcranCreation = paramAfficheTiers;
//
//						paramAfficheAideRecherche.setTypeEntite(TaTiers.class);
//						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_TIERS);
//						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_TIERS().getText());
//						paramAfficheAideRecherche.setControllerAppelant(getThis());
////						ModelTiers modelTiers = new ModelTiers(swtPaTiersController.getIbTaTable());
////						paramAfficheAideRecherche.setModel(modelTiers);
//						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTiers,TaTiersDAO,TaTiers>(SWTTiers.class,getEm()));
//						paramAfficheAideRecherche.setTypeObjet(swtPaTiersController.getClassModel());
//						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_TIERS);
//						
//						/*
//						 * Bug #1376
//						 */
//						if (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION) == 0) {
//							if(autoriseModifCodeTiers())
//								actModifier();
//						}
//					}
//
//					break;
				case C_MO_EDITION:
				case C_MO_INSERTION:
					if (getFocusCourantSWT().equals(vue.getTfCODE_TIERS())||
							getFocusCourantSWT().equals(vue.getBtnAideTiers())) {
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
					if (getFocusCourantSWT().equals(vue.getTfNOM_TIERS())) {
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
						paramAfficheAideRecherche.setChampsRecherche(Const.C_NOM_TIERS);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfNOM_TIERS().getText());
						paramAfficheAideRecherche.setControllerAppelant(getThis());
//						ModelTiers modelTiers = new ModelTiers(swtPaTiersController.getIbTaTable());
//						paramAfficheAideRecherche.setModel(modelTiers);
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTiers,TaTiersDAO,TaTiers>(SWTTiers.class,getEm()));
						paramAfficheAideRecherche.setTypeObjet(swtPaTiersController.getClassModel());
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_TIERS);
					}					
					if (getFocusCourantSWT().equals(vue.getTfCODE_DOCUMENT())||getFocusCourantSWT().equals(vue.getBtnAideDocument())) {
						//permet de paramètrer l'affichage remplie ou non de l'aide
						editorCreationId = EditorFacture.ID_EDITOR;
						editorInputCreation = new EditorInputFacture();
						paramAfficheAideRecherche.setForceAffichageAideRemplie(false);
						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
						controllerEcranCreation = this;
						paramAfficheAideRecherche.setAfficheDetail(false);
						
						paramAfficheAideRecherche.setTypeEntite(TaFacture.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_DOCUMENT);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_DOCUMENT().getText());
						paramAfficheAideRecherche.setControllerAppelant(SWTPaEditorFactureController.this);
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaFactureDAO,TaFacture>(dao,IHMAideFacture.class));
						paramAfficheAideRecherche.setTypeObjet(IHMAideFacture.class);
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_DOCUMENT);
					}
					if (focusDansAdresse(getFocusCourantSWT())
							|| focusDansAdresse_Liv(getFocusCourantSWT())) {
						PaAdresseSWT paAdresseSWT = new PaAdresseSWT(s2,SWT.NULL);
						SWTPaAdresseController swtPaAdresseController = new SWTPaAdresseController(paAdresseSWT);

						editorCreationId = EditorAdresse.ID;
						editorInputCreation = new EditorInputAdresse();
						
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

//					
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
			
			initInfosFacture();
			
			mapperUIToModelAdresseFactVersInfosDoc.map((IHMAdresseInfosFacturation) selectedAdresseFact, taInfosDocument);
			mapperUIToModelAdresseLivVersInfosDoc.map((IHMAdresseInfosLivraison) selectedAdresseLiv, taInfosDocument);
//			mapperUIToModelCPaiementVersInfosDoc.map((IHMInfosCPaiement) selectedCPaiement, taInfosDocument);
			
			mapperUIToModel.map((IHMEnteteFacture)selectedFacture,taDocument);
		}
		return null;
	}
	
	public IStatus validateUIField(String nomChamp,Object value) {
		String validationContext = "FACTURE";
		try {
			IStatus s = null;

			if(nomChamp.equals(Const.C_NOM_TIERS)) {
				TaTiers t = new TaTiers();
				PropertyUtils.setSimpleProperty(t, nomChamp, value);
				Boolean change =((IHMEnteteFacture)selectedFacture).getNomTiers()==null ||
				  !((IHMEnteteFacture)selectedFacture).getNomTiers().equals(t.getNomTiers());
				if(change){
				((IHMEnteteFacture)selectedFacture).setNomTiers(t.getNomTiers());
				((IHMEnteteFacture)selectedFacture).setLibelleDocument("Facture N°"+taDocument.getCodeDocument()+" - "+t.getNomTiers());
				((IHMIdentiteTiers)selectedInfosTiers).setNomTiers(t.getNomTiers());
				PropertyUtils.setSimpleProperty(taDocument, Const.C_LIBELLE_DOCUMENT, ((IHMEnteteFacture)selectedFacture).getLibelleDocument());
				if(taInfosDocument!=null) 
					taInfosDocument.setNomTiers(t.getNomTiers());
				}
				if(!findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getExpanded() 
						&& taDocument.getTaTiers()!=null && change ){
					findExpandIem(vue.getExpandBar(), paInfosAdresseFact).setExpanded(true);
				}
				return new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
			}
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
					taDocument.removeReglement(taDocument.getTaRReglement());
					taDocument.setTaRReglement(taDocument.creeReglement(dao.getEntityManager(),null, false,typePaiementDefaut));
						String nomTiers=taDocument.getTaTiers().getNomTiers();
						((IHMEnteteFacture)selectedFacture).setLibelleDocument("Facture N°"+taDocument.getCodeDocument()+" - "+nomTiers);
						((IHMEnteteFacture)selectedFacture).setNomTiers(nomTiers);
						((IHMIdentiteTiers)selectedInfosTiers).setNomTiers(nomTiers);
						PropertyUtils.setSimpleProperty(taDocument, Const.C_LIBELLE_DOCUMENT, ((IHMEnteteFacture)selectedFacture).getLibelleDocument());
						if(taInfosDocument!=null) 
							taInfosDocument.setNomTiers(nomTiers);						
					
					if(vue.getCbTTC().isEnabled()){
						taDocument.setTtc(f.getTtcTiers());
						((IHMEnteteFacture)selectedFacture).setTtc(LibConversion.intToBoolean(taDocument.getTtc()));
					}

					controllerTotaux.calculTypePaiement(true);
					controllerTotaux.calculDateEcheance(change);
					//controllerTotaux.initialisationDesCPaiement(true);
				}
				dao = null;
			} else if(nomChamp.equals(Const.C_LIBELLE_DOCUMENT)) {
				if(value==null || value.equals("")) {
					String nomTiers="";
					if(taDocument.getTaTiers()!=null)
						nomTiers=taDocument.getTaTiers().getNomTiers();
					((IHMEnteteFacture)selectedFacture).setLibelleDocument("Facture N°"+taDocument.getCodeDocument()+" - "+nomTiers);
					}
				PropertyUtils.setSimpleProperty(taDocument, Const.C_LIBELLE_DOCUMENT, ((IHMEnteteFacture)selectedFacture).getLibelleDocument());
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
				TaFacture u = new TaFacture(true);
				PropertyUtils.setSimpleProperty(u, nomChamp, value);
				if(((IHMEnteteFacture) selectedFacture).getIdDocument()!=null) {
					u.setIdDocument(((IHMEnteteFacture) selectedFacture).getIdDocument());
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
//				else if(nomChamp.equals(Const.C_DATE_LIV_DOCUMENT)) {
//					PropertyUtils.setSimpleProperty(u, nomChamp, value);
//				}
				s = dao.validate(u,nomChamp,validationContext,verrouilleModifCode);
				
			}
			if(s.getSeverity()!=Status.ERROR){
				if(nomChamp.equals(Const.C_DATE_DOCUMENT)){
					Boolean changement=taDocument.changementDateDocument((Date)value);
					((IHMEnteteFacture)selectedFacture).setDateDocument((Date)value);
					PropertyUtils.setSimpleProperty(taDocument, Const.C_DATE_DOCUMENT, value);
					if(changement){
						controllerTotaux.calculDateEcheance(true);
						((IHMEnteteFacture)selectedFacture).setDateLivDocument(taDocument.getDateDocument());
						PropertyUtils.setSimpleProperty(taDocument, Const.C_DATE_LIV_DOCUMENT, taDocument.getDateDocument());
						taDocument.setOldDate(taDocument.getDateDocument());
					}
				}
				if(nomChamp.equals(Const.C_DATE_LIV_DOCUMENT)) {
					((IHMEnteteFacture)selectedFacture).setDateLivDocument((Date)value);
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
	
	public void initInfosFacture() {
		if(taInfosDocument==null) {
			//initialisation de l'infosfacture
			taInfosDocument = new TaInfosFacture();
		}
		if(taDocument!=null) {
			//if(taFacture.getTaInfosFactures().isEmpty()) { //pas d'infosfacture dans la facture
				taInfosDocument.setTaDocument(taDocument);
				taDocument.setTaInfosDocument(taInfosDocument);
				//taFacture.getTaInfosFactures().add(taInfosFacture);
//			} else {
//				taInfosFacture = taFacture.getTaInfosFactures().iterator().next();
//			}
		}
	}

	@Override
	protected void actEnregistrer() throws Exception {
		EntityTransaction transaction = dao.getEntityManager().getTransaction();
		try {
			int retourPage=0;
			try {
				boolean declanchementInterne = false;
				if(sourceDeclencheCommandeController==null) {
					declanchementInterne = true;
				}

				if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
						|| (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
					
					boolean instertionDocuemnt = false;
					if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)) {
						instertionDocuemnt = true;
					}

					try{
						taDocument.setLegrain(false);							
						for (TaLFacture ligne : taDocument.getLignes()) {
							ligne.setLegrain(false);
						}

						controllerLigne.ctrlTouslesChampsToutesLesLignes();
						if(controllerTotaux.getDaoRAcompte().dataSetEnModif())
							controllerTotaux.actEnregistrerAffectation();
						
						if(controllerTotaux.getDaoRAvoir().dataSetEnModif())
							controllerTotaux.actEnregistrerAffectationAvoir();
						
						controllerTotaux.enregistreReglement();

					}catch (Exception e) {
						actSuivant();
						throw new Exception();
					}

					taDocument.calculeTvaEtTotaux();
					if((taDocument.getTaRReglement()!=null && taDocument.getTaRReglement().getAffectation()!=null&&
							taDocument.getTaRReglement().getAffectation().compareTo(BigDecimal.valueOf(0))!=0
//							||taDocument.getTaRReglement().getLibellePaiement()!=""
							)
							&& !taDocument.multiReglement()){						
						if(taDocument.getTaRReglement().getTaReglement().getCodeDocument()==null){
							taDocument.setTaRReglement(taDocument.creeReglement(dao.getEntityManager(),taDocument.getTaRReglement(),true,typePaiementDefaut));
							taDocument.addRReglement(taDocument.getTaRReglement());
						}
					}else if(taDocument.getTaRReglement()!=null) 
						taDocument.getTaRReglement().setEtatDeSuppression(IHMEtat.suppression);
					initInfosFacture();
					verifCode();
					ctrlTousLesChampsAvantEnregistrementSWT(dbcAdresseFact);
					ctrlTousLesChampsAvantEnregistrementSWT(dbcAdresseLiv);
					
					ctrlTousLesChampsAvantEnregistrementSWT();

					try{

					controllerTotaux.validateUI();
					}catch (Exception e) {
						retourPage=ChangementDePageEvent.FIN;
						throw new Exception("",e);
					}

					dao.begin(transaction);

					if(declanchementInterne) {
						mapperUIToModelAdresseFactVersInfosDoc.getMapper();
						mapperUIToModelAdresseFactVersInfosDoc.map((IHMAdresseInfosFacturation) selectedAdresseFact, taInfosDocument);
						mapperUIToModelAdresseLivVersInfosDoc.map((IHMAdresseInfosLivraison) selectedAdresseLiv, taInfosDocument);
//						mapperUIToModelCPaiementVersInfosDoc.map((IHMInfosCPaiement) selectedCPaiement, taInfosDocument);
						/*
						 * recuperation des objets a partir de l'interface au cas ou l'utilisateur ne serait pas passe 
						 * sur cet onglet
						 */
						controllerTotaux.validateUI(); 

					}
					mapperUIToModel.map((IHMEnteteFacture) selectedFacture, taDocument);
					mapperUIToModelDocumentVersInfosDoc.map(taDocument, taInfosDocument);
					if(!((IStructuredSelection)viewerComboLocalisationTVA.getSelection()).isEmpty()) {
						String codeTTvaDoc = ((TaTTvaDoc)((IStructuredSelection)viewerComboLocalisationTVA.getSelection()).getFirstElement()).getCodeTTvaDoc();
						taInfosDocument.setCodeTTvaDoc(codeTTvaDoc);
					}
					taDocument.setLegrain(false);
					for (TaLFacture ligne : taDocument.getLignes()) {
						ligne.setLegrain(false);
					}
					dao.begin(transaction);

					List<TaRAcompte> listeTemp = new LinkedList<TaRAcompte>();						
					for (TaRAcompte taRAcompte : taDocument.getTaRAcomptes()) {
						if(taRAcompte.isEtatDeSuppression())
							listeTemp.add(taRAcompte);
					}
					for (TaRAcompte taRAcompte : listeTemp) {
						controllerTotaux.supprimeTaRAcompte(taRAcompte);
					}
					
					List<TaRAvoir> listeTempAvoirs = new LinkedList<TaRAvoir>();						
					for (TaRAvoir taRAvoir : taDocument.getTaRAvoirs()) {
						if((taRAvoir.getEtat()&IHMEtat.suppression)!=0)
							listeTempAvoirs.add(taRAvoir);
					}
					for (TaRAvoir taRAvoir : listeTempAvoirs) {
						controllerTotaux.supprimeTaRAvoir(taRAvoir);
					}
					
					List<TaRReglement>  listeTempReglement = new LinkedList<TaRReglement>();						
					for (TaRReglement taRReglement : taDocument.getTaRReglements()) {
						if((taRReglement.getEtatDeSuppression()& IHMEtat.suppression) != 0||
								taRReglement.getAffectation()==BigDecimal.valueOf(0))
							listeTempReglement.add(taRReglement);
					}
					for (TaRReglement taRReglement : listeTempReglement) {
						controllerTotaux.supprimeReglement(taRReglement);
					}
					controllerTotaux.rechercherModifAcompte();
					controllerTotaux.rechercherModifAvoir();
					
					
					for (TaRReglement taRReglement : taDocument.getTaRReglements()){
						taRReglement=controllerTotaux.enregistreReglement(dao,taRReglement);
						taRReglement.setTaFacture(taDocument);
					}
					
					try{
						boolean afficher= DocumentPlugin.getDefault().getPreferenceStore().
						getBoolean(PreferenceConstants.MESSAGE_TIERS_DIFFERENT);
						if(afficher && !taDocument.getTaTiers().getNomTiers().equals(taInfosDocument.getNomTiers())){
							if(MessageDialog.openConfirm(vue.getShell(), "nom de tiers différent", "Le nom du tiers dans la facture est différent du nom du tiers lié au code tiers " +
									taDocument.getTaTiers().getCodeTiers()+". Etes-vous sûr de vouloir le conserver ? ")==false){
								throw new Exception();
							}
						}
					}catch (Exception e) {
						retourPage=ChangementDePageEvent.DEBUT;
						throw new Exception("",e);
					}
					
					taDocument=dao.enregistrerMerge(taDocument);

					for (TaRReglement taRReglement : taDocument.getTaRReglements()){
						taRReglement.setTaFacture(taDocument);
						taRReglement.getTaReglement().addRReglement(taRReglement);
					}
					
					dao.commit(transaction);
					transaction = null;
					if (controllerTotaux.impressionAuto())
						actImprimer();
					
					taDocument.setTaRReglement(null);
					remplaceCodeDocument(taDocument);

					if (controllerTotaux.etiquetteAuto()!=null
							&& !controllerTotaux.etiquetteAuto().equals("")) {
						
						IPreferenceStore store = FacturePlugin.getDefault().getPreferenceStore();
						store.setValue(fr.legrain.facture.preferences.PreferenceConstants.P_DERNIER_MODELE_UTILISE_ETIQUETTE,controllerTotaux.etiquetteAuto());
						
						if(!controllerTotaux.etiquetteAuto().equals(HeadlessEtiquette.CHOIX_AUCUN_CCOMB_PARAM_ETIQUETTE)) {
							//imprimeEtiquette(taDocument.getTaTiers(),controllerTotaux.etiquetteAuto());*
							imprimeEtiquette(taDocument,controllerTotaux.etiquetteAuto(),FichierDonneesAdresseFacture.TYPE_ADRESSE_FACTURATION);
						} else {
							logger.info("Aucun modele d'etiquette selectionné.");
						}
					}
					
					if (controllerTotaux.impressionAutoCourrier()) {
						imprimeCourrier(controllerTotaux.listeCourrierAImprimer(), taDocument, FichierDonneesAdresseFacture.TYPE_ADRESSE_FACTURATION);
					}

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
				if(retourPage!=0){
					ChangementDePageEvent change = new ChangementDePageEvent(this,
							retourPage,0);
					fireChangementDePage(change);
				}
			}
		} catch (ExceptLgr e1) {
			logger.error("Erreur : actionPerformed", e1);
		}
	}
	
	public void imprimeCourrier(List<SWTDocumentEditable> l, TaFacture f, int typeAdresse) {
		if(l!=null && !l.isEmpty()) {
			try {
				boolean publipostage = true; //vrai si les documents .doc et .odt doivent être traités avec les fonctions de publipostage/fusion
				boolean fusionPublipostage = controllerTotaux.fusionnerDocuments(); //vrai si on souhaite obtenir au final un seul document par type de logiciel de traitement de texte utilisé 
				
				final String timeStamp = LibDate.dateToStringTimeStamp(new Date(),"","_","");

				//Création du fichier de données pour le document sélectionné
				List<TaFacture> listeFinale = new ArrayList<TaFacture>();
				listeFinale.add(f);
				
				final String nomDocGenere = "Facture";

				String repertTravail = Const.C_CHEMIN_REP_TMP_COMPLET;
				String repertFinal = null;
				final TiersUtil tiersUtil = new TiersUtil(getEm());
				repertFinal = tiersUtil.repertoireCourrierDocument(taDocument.getTaTiers(),"/"+nomDocGenere+"/"+taDocument.getCodeDocument());
				
				final File fileRepertFinal = new File(repertFinal);
				if(fileRepertFinal.mkdirs()) {
					logger.info("Création du répertoire :"+repertFinal);
				}
				String cheminFichierDonnees = new File(repertTravail+"/"+nomDocGenere+"-"+timeStamp+".txt").getPath();

				File cheminFichierMotCleDefaut = LgrFileBundleLocator.bundleToFile(fr.legrain.publipostage.Activator.getDefault().getBundle(), "/default/motcle.properties");

				FichierDonneesAdresseFacture donneesAdresse = null;
				if(typeAdresse == FichierDonneesAdresseFacture.TYPE_ADRESSE_FACTURATION) {
					donneesAdresse = new FichierDonneesAdresseFacture(FichierDonneesAdresseFacture.TYPE_ADRESSE_FACTURATION);
				} else if(typeAdresse == FichierDonneesAdresseFacture.TYPE_ADRESSE_LIVRAISON) {
					donneesAdresse = new FichierDonneesAdresseFacture(FichierDonneesAdresseFacture.TYPE_ADRESSE_LIVRAISON);
				}
				donneesAdresse.creationFichierDonneesAdresse(listeFinale, repertTravail, cheminFichierDonnees);

				final TypeVersionPublipostage typeVersion = TypeVersionPublipostage.getInstance();

				//Pour chaque case de document coché créer initialisé un objet ParamPublipostage
				final Map<String,List<ParamPublipostage>> listeParamPubliParTypeLogiciel = new HashMap<String,List<ParamPublipostage>>();
				
				for (SWTDocumentEditable b : l) {
					String documentName = new File(b.getCheminModelDocumentDoc()).getName();
					String extensionDocument = documentName.substring(documentName.lastIndexOf(".")+1);
					
					if(publipostage && typeVersion.getLogicielParExtension().containsKey(extensionDocument)) {
						//c'est un fichier de traitement de texte, on peu faire un publipostage

						ParamPublipostage param = new ParamPublipostage();
						param.setCheminFichierDonnees(cheminFichierDonnees);

						//if(listeModelesPublipostage.get(b).getDefaut()==1){
						//	param.setCheminFichierModelLettre(Const.C_REPERTOIRE_BASE+Const.C_REPERTOIRE_PROJET+"/"+Const.FOLDER_MODEL_LETTRE+"/"+listeModelesPublipostage.get(b).getCheminModelDocumentTiers());
						//	param.setCheminFichierMotCle(fichierMotCle.getAbsolutePath());
						//}
						//else{
							param.setCheminFichierModelLettre(b.getCheminModelDocumentDoc());
							if(b.getCheminCorrespDocumentDoc()!=null && !b.getCheminCorrespDocumentDoc().equals("")) {
								param.setCheminFichierMotCle(b.getCheminCorrespDocumentDoc());
							} else {
								//param.setCheminFichierMotCle(fichierMotCle.getAbsolutePath());
								param.setCheminFichierMotCle(cheminFichierMotCleDefaut.getAbsolutePath());
							}
						//	param.setCheminFichierMotCle(b.getCheminCorrespDocumentDoc());
						//}

						//param.setCheminFichierFinal(repert+"/"+nomDocGenere+"-"+b.getLibelleDocumentDoc()+"-"+LibDate.dateToString(new Date(),"")+"."+extensionDocument);
						String repDoc = null;
						if(!fusionPublipostage) {
							repDoc = repertFinal;
						} else {
							repDoc = repertTravail;
						}
						param.setCheminFichierFinal(repDoc+"/"+nomDocGenere+"-"+b.getLibelleDocumentDoc()+"-"+timeStamp+"."+extensionDocument);
						param.setCheminRepertoireFinal(repertFinal);
						if(listeParamPubliParTypeLogiciel.get(typeVersion.getLogicielParExtension().get(extensionDocument))==null) {
							listeParamPubliParTypeLogiciel.put(typeVersion.getLogicielParExtension().get(extensionDocument), new ArrayList<ParamPublipostage>());
						}
						listeParamPubliParTypeLogiciel.get(typeVersion.getLogicielParExtension().get(extensionDocument)).add(param);

						if(!fusionPublipostage) {
							//Il faut une liste par document pour eviter les problèmes de thread
							final List<ParamPublipostage> listeParamPubliSansFusion = new ArrayList<ParamPublipostage>();
							listeParamPubliSansFusion.add(param);
							String extensionFinale = typeVersion.getExtensionFinaleDefaut().get(typeVersion.getLogicielParExtension().get(extensionDocument));
							final String nonFichierFinal = "publi"+nomDocGenere+"-"+f.getCodeDocument()+"-"+timeStamp+extensionFinale;
							lancePublipostage(typeVersion.getLogicielParExtension().get(extensionDocument), nonFichierFinal, listeParamPubliParTypeLogiciel.get(typeVersion.getLogicielParExtension().get(extensionDocument)));
						}

					} else {
						// autre type de doc, pas de publipostage, on essai juste de l'ouvrir
						final String finalURL = new File(b.getCheminModelDocumentDoc()).getAbsolutePath();
						PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
							public void run() {
								File file = new File(finalURL);
								String cheminCompletDest = fileRepertFinal.getPath()+"/";
								cheminCompletDest += file.getName().substring(0,file.getName().lastIndexOf("."));
								cheminCompletDest += "_"+timeStamp;
								cheminCompletDest += file.getName().substring(file.getName().lastIndexOf("."));
								File destFile = new File(cheminCompletDest);
								if (Desktop.isDesktopSupported()) {
									Desktop desktop = Desktop.getDesktop();
									if (desktop.isSupported(Desktop.Action.OPEN)) {
										try {
											if(file.exists()) {
												TiersUtil.copyFile(file, destFile);
												desktop.open(destFile);
											}
											else
												MessageDialog.openError(vue.getShell(), "Erreur", 
												"Le chemin est invalide ou inaccessible pour l'instant.");
										} catch (IOException e) {
											logger.error("",e);
										}
									}
								}
							}	
						});
					}
				}

				//Finir de paramétrer les paramètres généraux du publipostage (fichier final, ...)
				//lancer le publipostage dans un thread
				if(fusionPublipostage) {
					//il y une liste de publipostage a générer
					for (String typeLogiciel : listeParamPubliParTypeLogiciel.keySet()) {
						String extensionFinale = typeVersion.getExtensionFinaleDefaut().get(typeLogiciel);
						final String nonFichierFinal = "publi"+nomDocGenere+"Fusion-"+f.getCodeDocument()+"-"+timeStamp+extensionFinale;
						lancePublipostage(typeLogiciel, nonFichierFinal, listeParamPubliParTypeLogiciel.get(typeLogiciel));
					}
				}

			} catch (Exception e1) {
				logger.error("Erreur : actionPerformed", e1);
			}

		}
	}


	//private void lancePublipostage(final TypeVersionPublipostage typeVersion, final String nonFichierFinal, final List<ParamPublipostage> listeParamPubli, final TaParamPublipostage taParamPublipostage) {
	private void lancePublipostage(final String typeVersion, final String nonFichierFinal, final List<ParamPublipostage> listeParamPubli) {
		Thread t = new Thread() {

			@Override
			public void run() {
				//if(typeVersion.getType().get(taParamPublipostage.getLogicielPublipostage()).equals(TypeVersionPublipostage.TYPE_OPENOFFICE)){
				if(typeVersion.equals(TypeVersionPublipostage.TYPE_OPENOFFICE)){

					//PublipostageOOoWin32 pub = new PublipostageOOoWin32(listeParamPubli);
					AbstractPublipostageOOo pub = PublipostageOOoFactory.createPublipostageOOo(listeParamPubli);
					String pathOpenOffice = "";
					try {
						if(Platform.getOS().equals(Platform.OS_WIN32)){
							pathOpenOffice = WinRegistry.readString(
									WinRegistry.HKEY_LOCAL_MACHINE,
									WinRegistry.KEY_REGISTR_WIN_OPENOFFICE,
							"");
						} else if(Platform.getOS().equals(Platform.OS_LINUX)){
							pathOpenOffice = "/usr/bin/soffice";
						} else if(Platform.getOS().equals(Platform.OS_MACOSX)) {}
					}
					catch (Exception e3) {
						logger.error("",e3);
					}    

					pub.setCheminOpenOffice(new File(pathOpenOffice).getPath());
					pub.setPortOpenOffice("8100");
					pub.setNomFichierFinalFusionne(new File(nonFichierFinal).getPath());
					pub.demarrerServeur();
					pub.publipostages();

					//}else if(typeVersion.getType().get(taParamPublipostage.getLogicielPublipostage()).
				}else if(typeVersion.equals(TypeVersionPublipostage.TYPE_MSWORD)){
					PublipostageMsWord pub = new PublipostageMsWord(listeParamPubli);
					pub.setNomFichierFinalFusionne(new File(nonFichierFinal).getPath());
					pub.publipostages();
				}

			}//fin run
		}; //fin thread
		t.start();
	}
	
	//public void imprimeEtiquette(TaTiers tiers, String modele) {
	public void imprimeEtiquette(TaFacture f, String modele, int typeAdresse) {
		try {

			List<TaFacture> listeFinale = new ArrayList<TaFacture>();
			listeFinale.add(f);

			//			List<TaTiers> listeFinale = new ArrayList<TaTiers>();
			//			listeFinale.add(tiers);

			String repert= new File(Platform.getInstanceLocation().getURL().getFile()).getPath();
			String cheminFichierDonnees = new File(Const.C_CHEMIN_REP_TMP_COMPLET+"/Etiquettes"+"-"+LibDate.dateToString(new Date(),"")+".txt").getPath();

			String cheminFichierMotCle = LgrFileBundleLocator.bundleToFile(generationlabeletiquette.Activator.getDefault().getBundle(), "/modelEtiquette/motcle.properties").getPath();
//			String cheminFichierMotCle = new File(Const.pathRepertoireSpecifiques("GenerationLabelEtiquette", "modelEtiquette")+"/motcle.properties").getPath();

			FichierDonneesAdresseFacture donneesAdresse = null;
			if(typeAdresse == FichierDonneesAdresseFacture.TYPE_ADRESSE_FACTURATION) {
				donneesAdresse = new FichierDonneesAdresseFacture(FichierDonneesAdresseFacture.TYPE_ADRESSE_FACTURATION);
			} else if(typeAdresse == FichierDonneesAdresseFacture.TYPE_ADRESSE_LIVRAISON) {
				donneesAdresse = new FichierDonneesAdresseFacture(FichierDonneesAdresseFacture.TYPE_ADRESSE_LIVRAISON);
			}
			donneesAdresse.creationFichierDonneesAdresse(listeFinale, repert, cheminFichierDonnees);

			ParamWizardEtiquettes p = null;
			p = new ParamWizardEtiquettes();
			if(!modele.equals(HeadlessEtiquette.CHOIX_DEFAUT_CCOMB_PARAM_ETIQUETTE)) {
				p.setChangeStartingPage(true);
//
//
//				HeadlessEtiquette headlessEtiquette = new HeadlessEtiquette();
//				headlessEtiquette.lectureParam(modele);
//				headlessEtiquette.getParameterEtiquette().setPathFileExtraction(cheminFichierDonnees);
//				headlessEtiquette.getParameterEtiquette().setPathFileMotCle(cheminFichierMotCle);
//				//getParameterEtiquette().setPathFileMotCle(null);
//				headlessEtiquette.print();
			} 
			//else {
				//FichierDonneesAdresseTiers donneesTiers = new FichierDonneesAdresseTiers();
				//donneesTiers.creationFichierDonneesAdresse(listeFinale, repert, cheminFichierDonnees);

				//creationFichierDonnees(listeFinale,repert,cheminFichierDonnees);

				//ParamWizardEtiquettes p = null;
				//p = new ParamWizardEtiquettes();
				p.setModelePredefini(modele);
				p.setModeIntegre(true);
				p.setCheminFichierDonnees(cheminFichierDonnees);
				p.setCheminFichierMotsCle(cheminFichierMotCle);
				p.setType(WizardController.DOSSIER_PARAM_TIERS);
				p.setSeparateur(";");
				

				IPreferenceStore store = FacturePlugin.getDefault().getPreferenceStore();
				store.setValue(fr.legrain.facture.preferences.PreferenceConstants.P_DERNIER_MODELE_UTILISE_ETIQUETTE, modele);

				//GenerationFileEtiquette generationFileEtiquette = new GenerationFileEtiquette();
				//WizardModelLables wizardModelLables = new WizardModelLables(generationFileEtiquette,p);
				WizardModelLables wizardModelLables = new WizardModelLables(new GenerationFileEtiquette());
				wizardModelLables.initParam(p);
				WizardDialogModelLabels wizardDialogModelLabels = new WizardDialogModelLabels
				(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),wizardModelLables);
				wizardDialogModelLabels.open();
				//wizardDialogModelLabels.showPage(wizardModelLables.getPage(ConstantModelLabels.NAME_PAGE_FORMAT_ETIQUETTE));
			//}


		} catch (Exception e1) {
			logger.error("", e1);
		}
	}

	public void initEtatComposant() {
		try {
			if(taDocument!=null){
				if (taDocument.isRemplie()) {
					vue.getCbTTC().setEnabled(false);
					vue.getComboLocalisationTVA().setEnabled(false);
				} else {
					vue.getCbTTC().setEnabled(true);
					vue.getComboLocalisationTVA().setEnabled(true);
				}
//			vue.getTfCODE_TIERS().setEditable(taDocument.getTaRAcomptes().size()==0 && taDocument.getTaRAvoirs().size()==0
//					&& taDocument.getTaRReglements().size()==0);
			}

		} catch (Exception e) {
			if (e.getMessage() != null)
				vue.getLaMessage().setText(e.getMessage());
		}
	}

	public IHMEnteteFacture getIhmOldEnteteFacture() {
		return ihmOldEnteteFacture;
	}

	public void setIhmOldEnteteFacture(IHMEnteteFacture ihmOldEnteteFacture) {
		this.ihmOldEnteteFacture = ihmOldEnteteFacture;
	}

	public void setIhmOldEnteteFacture() {
		if (selectedFacture != null){
			this.ihmOldEnteteFacture = IHMEnteteFacture.copy((IHMEnteteFacture) selectedFacture);
		}
	}
	
	
	public Boolean setIhmOldEnteteDocumentRefresh(){
		try {			
		if (selectedFacture != null){
			this.ihmOldEnteteFacture = IHMEnteteFacture.copy((IHMEnteteFacture) selectedFacture);
			if (taDocument!=null){
//				dao.getEntityManager().clear();
				taDocument =rechercheFacture(taDocument.getCodeDocument(), false, true);
				taInfosDocument=taDocument.getTaInfosDocument();
			}
		}
		return true;
		} catch (Exception e) {
			return false;
		}		
	}



	public void setVue(PaEditorFactureSWT vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();

		mapComposantDecoratedField.put(vue.getTfCODE_DOCUMENT(), vue.getFieldCODE_DOCUMENT());
		mapComposantDecoratedField.put(vue.getTfCODE_TIERS(), vue.getFieldCODE_TIERS());

		mapComposantDecoratedField.put(vue.getDateTimeDATE_DOCUMENT(),vue.getFieldDATE_DOCUMENT());
		mapComposantDecoratedField.put(vue.getDateTimeDATE_LIV_DOCUMENT(), vue.getFieldDATE_LIV_DOCUMENT());
		mapComposantDecoratedField.put(vue.getTfLIBELLE_DOCUMENT(), vue.getFieldLIBELLE_DOCUMENT());
		mapComposantDecoratedField.put(vue.getTfNOM_TIERS(), vue.getFieldNOM_TIERS());
	}

	public Class getClassModel() {
		return classModel;
	}

	@Override
	protected void sortieChamps() {
		boolean verrouille = VerrouInterface.isVerrouille();
		// si sortie champ code facture
		try {
			
			if (!((focusDansEntete()
					|| (vue.getPaBtnAvecAssistant().getPaBtn().getBtnAnnuler().isFocusControl()) 
					|| (vue.getPaBtnAvecAssistant().getPaBtn().getBtnFermer().isFocusControl()))
					
					|| (vue.getBtnAideDocument().isFocusControl())
							|| (vue.getBtnAideTiers().isFocusControl()))) {
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

			initEtatBouton(false);

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
			if (taDocument!=null && selectedFacture!=null && (IHMEnteteFacture) selectedFacture!=null) {
				mapperModelToUI.map(taDocument, (IHMEnteteFacture) selectedFacture);
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
		String oldNomTiers=null;
		oldNomTiers=((IHMEnteteFacture) selectedFacture).getNomTiers();
		mapperModelToUI.map(taDocument, ((IHMEnteteFacture) selectedFacture));
		if(oldNomTiers!=null)((IHMEnteteFacture) selectedFacture).setNomTiers(oldNomTiers);
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
//				dao.annulerCodeGenere(((IHMEnteteFacture) selectedFacture).getCodeFacture(), Const.C_CODE_FACTURE);
//				((IHMEnteteFacture) selectedFacture).setIHMEnteteFacture(new IHMEnteteFacture());
//				mapperModelToUI.map(taFacture, ((IHMEnteteFacture) selectedFacture));
////				swtFacture.setSWTFacture(((IHMEnteteFacture) selectedEnteteFacture));
//				vue.getTfCODE_TIERS().setText("");
//				modelEnteteFacture.remplirListe();
//				taFacture.setOldCODE(SWTDocument.C_OLD_CODE_INITIAL);
//				ibTaTable.setChamp_Model_Obj(swtFacture);
				// vider les lignes tables temp
//				if (controllerLigne != null)
//					controllerLigne.recupLignes(swtFacture);
				//#evt
				// writableList = new WritableList(realm,
				// modelLignesTVA.remplirListe(swtFacture), IHMLignesTVA.class);
				// tableViewer.setInput(writableList);

//				initEtatBouton(false);
//				changementTiers = true;
//				changementTiers();
				//if(listeDocument==null)
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
		String[][] adapterFacture = initAdapterDocument();
		String[] listeCodeFacture = null;
		String[] listeLibelleFacture = null;
		if (adapterFacture != null) {
			listeCodeFacture = new String[adapterFacture.length];
			listeLibelleFacture = new String[adapterFacture.length];
			for (int i = 0; i < adapterFacture.length; i++) {
				listeCodeFacture[i] = adapterFacture[i][0];
				listeLibelleFacture[i] = adapterFacture[i][1];
			}
		}

		return new ContentProposalProvider(listeCodeFacture,
				listeLibelleFacture);
		
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
	
//	public SWTFacture getSwtFacture() {
//		return swtFacture;
//	}

	public SWTPaLigneController getControllerLigne() {
		return controllerLigne;
	}

	public void setControllerLigne(SWTPaLigneController controllerLigne) {
		this.controllerLigne = controllerLigne;
		this.controllerLigne.setParentEcran(this);
	}

	private Boolean remonterDocument(String codeFacture)throws Exception {
		return remonterDocument(codeFacture,true);
	}
			
	private Boolean remonterDocument(String codeFacture, Boolean activeModify) throws Exception {
		try {
			boolean res = false;
			desactiveModifyListener();
			desactiveValidateCodeDocument=true;
			desactiveVerifCodeTiers=true;
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
				factureVide = ((IHMEnteteFacture) selectedFacture).factureEstVide(taDocument.dateDansExercice(new Date()));
			}
			TaFacture factureRecherche = rechercheFacture(codeFacture, true, factureVide);
			if (factureRecherche!=null) {
				taDocument = factureRecherche;
				//taDocument.calculeTvaEtTotaux();
				taDocument.calculLignesTva();
				fireChangementMaster(new ChangementMasterEntityEvent(this,taDocument));
//				controllerTotaux.setMasterDAO(dao);
//				controllerLigne.setMasterDAO(dao);
				taDocument.addChangeModeListener(this);
				res = true;
				idTiersDocumentOriginal = taDocument.getTaTiers().getIdTiers();
				taInfosDocument = daoInfosFacture.findByCodeFacture(((IHMEnteteFacture)selectedFacture).getCodeDocument());
				changementTiers(true);
				
				TaTTvaDoc t = new TaTTvaDocDAO().findByCode(taInfosDocument.getCodeTTvaDoc());
				viewerComboLocalisationTVA.setSelection(new StructuredSelection(t),true);
				initLocalisationTVA();
				
				controllerLigne.initTTC();
				controllerTotaux.remonterDocument();
				controllerTotaux.actRefreshAffectation();
				controllerTotaux.actRefreshListeAcompteDisponible();
				controllerTotaux.actRefreshAffectationAvoirs();
				controllerTotaux.actRefreshListeAvoirDisponible();
				
				controllerTotaux.actRefreshReglements(null);
				controllerTotaux.refreshVue();
			}
			return res;
		} finally {
			if(activeModify)activeModifytListener();
			initEtatBouton(true);
			desactiveVerifCodeTiers=false;
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
		logger.debug("active");
		super.activeModifytListener();
		activeModifytListener(mapComposantChampsAdresse,daoAdresseFact);
		((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseFact).getControl()).getBtnReinitialiser().
				addSelectionListener(lgrModifyListener);
		activeModifytListener(mapComposantChampsAdresseLiv,daoAdresseLiv);
		((PaInfosAdresse) findExpandIem(vue.getExpandBar(), paInfosAdresseLiv).getControl()).getBtnReinitialiser().
				addSelectionListener(lgrModifyListener);
//		activeModifytListener(mapComposantChampsCPaiement, daoCPaiement);
//		((PaInfosCondPaiement) findExpandIem(vue.getExpandBar(), paInfosCondPaiement).getControl()).getBtnReinitialiser().
//				addSelectionListener(lgrModifyListener);
		activeModifytListener(mapComposantChampsInfosTiers, dao);
		((PaIdentiteTiers) findExpandIem(vue.getExpandBar(), paIdentiteTiers).getControl()).getBtnReinitialiser().
				addSelectionListener(lgrModifyListener);

		vue.getTfCODE_DOCUMENT().removeModifyListener(lgrModifyListener);
	}

	//#JPA
	//#AFAIRE
	public String[][] initAdapterDocument() {
		String[][] valeurs = null;
		List<Object[]>listeTemp=null;
		boolean affichageCtrlEspace = GestionCommercialePlugin.getDefault().getPreferenceStore().getBoolean(fr.legrain.gestionCommerciale.preferences.PreferenceConstants.AFFICHAGE_CTRL_ESPACE);
		if(affichageCtrlEspace){
		if(listeDocument==null){
			TaFactureDAO taDocDAO = new TaFactureDAO(getEm());
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
	
	public String[][] initAdapterTiers() {
		String[][] valeurs = null;
		List<Object[]> listeTemp = null;
		boolean affichageCtrlEspace = GestionCommercialePlugin.getDefault().getPreferenceStore().getBoolean(fr.legrain.gestionCommerciale.preferences.PreferenceConstants.AFFICHAGE_CTRL_ESPACE);
		if(affichageCtrlEspace){
		TaTiersDAO taTiersDAO = new TaTiersDAO(getEm());
		//#AFAIRE
		taTiersDAO.setPreferences(FacturePlugin.getDefault().getPreferenceStore().getString(fr.legrain.facture.preferences.PreferenceConstants.TYPE_TIERS_DOCUMENT));
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
//			((SWTEnteteFacture) swtFacture.getEntete()).setTTC(false);
			taDocument.setTtc(0);
		else
//			((SWTEnteteFacture) swtFacture.getEntete()).setTTC(true);
			taDocument.setTtc(1);
		try {
//			ibTaTable.affecte(Const.C_TTC,LibConversion.booleanToString(((SWTEnteteFacture) swtFacture.getEntete()).getTTC()));
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
		initEtatBoutonCommand(initFocus,modelEnteteFacture.getListeObjet());
//		enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID, dao.getModeObjet().getMode()==EnumModeObjet.C_MO_EDITION);
		enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID, false);
		boolean trouve = false;
		boolean trouveTiers=false;
		boolean trouveAdresse=false;
		boolean trouveDocument=false;
		enableActionAndHandler(C_COMMAND_GLOBAL_PRECEDENT_ID, false);
		enableActionAndHandler(C_COMMAND_GLOBAL_SUIVANT_ID, true);
		enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID, true);
//		enableActionAndHandler(C_COMMAND_DOCUMENT_CREATEDOC_ID, true);
		enableActionAndHandler(C_COMMAND_DOCUMENT_REINIT_ADR_FACT_ID, true);
		enableActionAndHandler(C_COMMAND_DOCUMENT_REINIT_ADR_LIV_ID, true);
//		enableActionAndHandler(C_COMMAND_DOCUMENT_REINIT_CPAIEMENT_ID, true);
		enableActionAndHandler(C_COMMAND_DOCUMENT_REINIT_INFOSTIERS_ID, true);
		//enableActionAndHandler(C_COMMAND_DOCUMENT_AFFICHER_TIERS_ID, true);
		trouveTiers=(taDocument!=null && taDocument.getTaTiers()!=null && taDocument.getTaTiers().getCodeTiers()!="");
		enableActionAndHandler(C_COMMAND_DOCUMENT_AFFICHER_TIERS_ID, trouveTiers);
		trouveDocument=(taDocument!=null && taDocument.getIdDocument()!=0);
		enableActionAndHandler(C_COMMAND_DOCUMENT_CREATEDOC_ID, trouveDocument);
		enableActionAndHandler(C_COMMAND_DOCUMENT_CREATEMODELE_ID, trouveDocument);	
		
		trouveAdresse=(taDocument!=null && taDocument.getTaInfosDocument()!=null && taDocument.getTaInfosDocument().getCodepostal()!="");
		enableActionAndHandler(HeadlessEtiquette.C_COMMAND_IMPRIMER_ETIQUETTE_ID, trouveAdresse);
		
		
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
		vue.getBtnAideDocument().setImage(
				LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_RECHERCHER_JUMELLE));
		vue.getBtnAideTiers().setImage(
				LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_RECHERCHER_JUMELLE));

		
		
		vue.getBtnFicheTiers().setImage(
				LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_IDENTITE_TIERS));
		vue.getBtnGenDoc().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_GEN_DOCUMENT));
		vue.getBtnGenModel().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_GEN_MODELE));
		vue.getBtnAutres().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_TOOLS));
		
		vue.getBtnAideDocument().setText("F1");
		vue.getBtnAideTiers().setText("F1");
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

	public class OldFacture {
		String code_facture = "";
		int id_facture = 0;

		public OldFacture() {
		}

		public OldFacture(String code_facture, int id_facture) {
			this.code_facture = code_facture;
			this.id_facture = id_facture;
		}
	}

	public boolean focusDansEntete() {
		for (Control element : listeComposantEntete) {
			//if (element.isFocusControl())
			if(element.equals(getFocusCourantSWT()))	
				return true;
		}
		if(vue.getBtnAideDocument().isFocusControl())return true;
		if(vue.getBtnAideTiers().isFocusControl())return true;
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

//	public boolean focusDansCPaiment(Control focusControl) {
//		for (Control element : listeComposantCPaiement) {
//			if (element.equals(focusControl))
//				return true;
//		}
//		return false;
//	}

	// public boolean focusDansBasFacture(){
	// for (Control element : listeComposantBasFacture) {
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

	public SWTPaEditorTotauxController getControllerTotaux() {
		return controllerTotaux;
	}

	public void setControllerTotaux(SWTPaEditorTotauxController controllerTotaux) {
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
//		desactiveModifyListener(mapComposantChampsCPaiement);
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
	
//	private class HandlerReinitCPaiement extends LgrAbstractHandler {
//
//		public Object execute(ExecutionEvent event) throws ExecutionException {
//			try {
//				actReinitCPaiement();
//			} catch (Exception e) {
//				logger.error("", e);
//			}
//			return event;
//		}
//	}
//	
//	private class HandlerAppliquerCPaiement extends LgrAbstractHandler {
//
//		public Object execute(ExecutionEvent event) throws ExecutionException {
//			try {
//				actAppliquerCPaiement();
//			} catch (Exception e) {
//				logger.error("", e);
//			}
//			return event;
//		}
//	}

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
	}
	
	private void actReinitAdrFact() throws Exception {
		initialisationDesInfosComplementaires(true,Const.RECHARGE_ADR_FACT);
	}
	
	private void actReinitAdrLiv() throws Exception {
		initialisationDesInfosComplementaires(true,Const.RECHARGE_ADR_LIV);
	}
	
//	private void actReinitCPaiement() throws Exception {
//		initialisationDesInfosComplementaires(true,Const.RECHARGE_C_PAIEMENT);
//		controllerTotaux.calculDateEcheance();
//	}
//	
//	private void actAppliquerCPaiement() throws Exception {
//		controllerTotaux.calculDateEcheance();
//	}
	
	private void actReinitInfosTiers() throws Exception {
		initialisationDesInfosComplementaires(true,Const.RECHARGE_INFOS_TIERS);
	}

	@Override
	protected void actCreateDoc() throws Exception {
		controllerTotaux.actCreateDoc();
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
			ContentProposalAdapter codeFactureContentProposal) {
		this.codeDocumentContentProposal = codeFactureContentProposal;
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

	public TaFactureDAO getDao() {
		return dao;
	}

	public void setDao(TaFactureDAO dao) {
		this.dao = dao;
	}

	public TaFacture getTaDocument() {
		return taDocument;
	}
	
	public TaFacture rechercheFacture(String codeFacture,boolean annule,boolean factureVide) throws Exception {
		TaFacture fact = null;
		fact = dao.findByCode(codeFacture);
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
			fact.calculeTvaEtTotaux();
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


	public Date dateDansPeriode(Date newValue,String champ) throws Exception{
		if(champ.equals(Const.C_DATE_DOCUMENT)){
			newValue=taDocument.dateDansExercice(newValue);
			if(newValue!=null){
//				if((((IHMEnteteFacture)selectedFacture).getDateEchDocument()!=null && 
//						((IHMEnteteFacture)selectedFacture).getDateEchDocument().before(newValue))||
//						((IHMEnteteFacture)selectedFacture).getDateEchDocument()==null ){
//					taDocument.setDateEchDocument(newValue);
//					((IHMEnteteFacture)selectedFacture).setDateEchDocument(newValue);
//					}
//				if((((IHMEnteteFacture)selectedFacture).getDateLivDocument()!=null && 
//						((IHMEnteteFacture)selectedFacture).getDateLivDocument().before(newValue))||
//						((IHMEnteteFacture)selectedFacture).getDateLivDocument()==null ){
//					taDocument.setDateLivDocument(newValue);
//					((IHMEnteteFacture)selectedFacture).setDateLivDocument(newValue);
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

	public PaNotesTiersController getPaNotesTiersController() {
		return paNotesTiersController;
	}

	public void setPaNotesTiersController(
			PaNotesTiersController paNotesTiersController) {
		this.paNotesTiersController = paNotesTiersController;
	}

	public PaNotesTiers getPaNotesTiers() {
		return paNotesTiers;
	}

	public void setPaNotesTiers(PaNotesTiers paNotesTiers) {
		this.paNotesTiers = paNotesTiers;
	}
	
//	public Boolean containsCodeDocument(){
//		if(listeDocument!=null)
//			for (IDocumentTiers  document : listeDocument) {
//				if(document.getCodeDocument().equals(taDocument.getCodeDocument()))
//					return true;
//			}
//		return false;
//	}
	
	/*
	 * *************************************************************************************************************
	 */
	public void addUpdateMasterEntityListener(UpdateMasterEntityListener l) {
		listenerList.add(UpdateMasterEntityListener.class, l);
	}
	
	public void removeUpdateMasterEntityListener(UpdateMasterEntityListener l) {
		listenerList.remove(UpdateMasterEntityListener.class, l);
	}
	
	protected void fireUpdateMasterEntity(UpdateMasterEntityEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == UpdateMasterEntityListener.class) {
				if (e == null)
					e = new UpdateMasterEntityEvent(this);
				( (UpdateMasterEntityListener) listeners[i + 1]).updateMasterEntity(e);
			}
		}
	}
	/*
	 * *************************************************************************************************************
	 */
	
	protected class HandlerEtiquette extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException{
			try {
				
				String paramType = event.getParameters().get(HeadlessEtiquette.C_PARAM_COMMANDE_IMPRIMER_ETIQUETTE_TYPE_ID).toString();
				String paramModele = event.getParameters().get(HeadlessEtiquette.C_PARAM_COMMANDE_IMPRIMER_ETIQUETTE_MODELE_ID).toString();
				if(!paramModele.equals(HeadlessEtiquette.CHOIX_AUCUN_CCOMB_PARAM_ETIQUETTE)) {
					if(paramType.equals(TYPE_ETIQUETTE_ADRESSE_FACTURATION)) 
						imprimeEtiquette(taDocument,paramModele,FichierDonneesAdresseFacture.TYPE_ADRESSE_FACTURATION);
					else if(paramType.equals(TYPE_ETIQUETTE_ADRESSE_LIVRAISON))
						imprimeEtiquette(taDocument,paramModele,FichierDonneesAdresseFacture.TYPE_ADRESSE_LIVRAISON);
				} else {
					logger.info("Aucun modele d'etiquette selectionné.");
				}
			} catch (Exception e) {
				logger.error("",e);
			}
			return event;
		}
	}


	private void createContributors() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint("GestionCommerciale.ImpressionTraite"); //$NON-NLS-1$
		if (point != null) {
			ImageDescriptor icon = null;
			IExtension[] extensions = point.getExtensions();
			for (int i = 0; i < extensions.length; i++) {
				IConfigurationElement confElements[] = extensions[i].getConfigurationElements();
				for (int jj = 0; jj < confElements.length; jj++) {
					try {
						String ClassEdition = confElements[jj].getAttribute("ClassEdition");//$NON-NLS-1$
						String contributorName = confElements[jj].getContributor().getName();
						
						if (ClassEdition == null )
							continue;

						gestionnaireTraite = confElements[jj].createExecutableExtension("ClassEdition");


					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}

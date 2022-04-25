package fr.legrain.creationDocMultipleTiers.controllers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
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
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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

import creationdocmultipletiers.Activator;
import fr.legrain.creationDocMultipleTiers.divers.DeclencheInitEtatBoutonControllerEvent;
import fr.legrain.creationDocMultipleTiers.divers.IDeclencheInitEtatBoutonControllerListener;
import fr.legrain.creationDocMultipleTiers.divers.ParamAffichecreationDocMultiple;
import fr.legrain.creationDocMultipleTiers.ecrans.PaCriterecreationDocMultiple;
import fr.legrain.creationDocMultipleTiers.ecrans.PaSelectionLigneDocTree;
import fr.legrain.creationDocMultipleTiers.ecrans.PaSelectionLigneMultiTiers;
import fr.legrain.document.divers.ModelRecupDocumentCreationDoc;
import fr.legrain.document.divers.ModelTiersCreationDoc;
import fr.legrain.document.divers.TaCreationDoc;
import fr.legrain.document.divers.TypeDoc;
import fr.legrain.documents.dao.TaAcompte;
import fr.legrain.documents.dao.TaAcompteDAO;
import fr.legrain.documents.dao.TaApporteur;
import fr.legrain.documents.dao.TaApporteurDAO;
import fr.legrain.documents.dao.TaAvisEcheance;
import fr.legrain.documents.dao.TaAvisEcheanceDAO;
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
import fr.legrain.documents.dao.TaPrelevement;
import fr.legrain.documents.dao.TaPrelevementDAO;
import fr.legrain.documents.dao.TaProforma;
import fr.legrain.documents.dao.TaProformaDAO;
import fr.legrain.documents.dao.TaTPaiement;
import fr.legrain.documents.dao.TaTPaiementDAO;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.dossierIntelligent.dao.TaParamDossierIntel;
import fr.legrain.dossierIntelligent.dao.TaParamDossierIntelDAO;
import fr.legrain.dossierIntelligent.dao.TaRParamDossierIntel;
import fr.legrain.dossierIntelligent.dao.TaTypeDonnee;
import fr.legrain.dossierIntelligent.dao.TaTypeDonneeDAO;
import fr.legrain.generationdocument.controllers.CreationDocumentMultiple;
import fr.legrain.generationdocument.controllers.PaParamGenerationDocController;
import fr.legrain.generationdocument.divers.ParamAfficheChoixGenerationDocument;
import fr.legrain.generationdocument.ecran.PaChoixGenerationDocument;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IHMAideFacture;
import fr.legrain.gestCom.Module_Document.IHMEnteteDocument;
import fr.legrain.gestCom.Module_Tiers.SWTTiers;
import fr.legrain.gestCom.gestComBd.gestComBdPlugin;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.gestCom.librairiesEcran.swt.LibDateTime;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideRechercheSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideRechercheControllerSWT;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.AbstractLgrDAO;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.InfosVerifSaisie;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.LibDate;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.data.VerrouInterface;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ParamAfficheSWT;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.lib.gui.aide.PaAideRechercheSWT;
import fr.legrain.lib.gui.aide.PaAideSWT;
import fr.legrain.lib.gui.aide.ParamAfficheAide;
import fr.legrain.libMessageLGR.LgrMess;
import fr.legrain.tiers.dao.TaAdresse;
import fr.legrain.tiers.dao.TaEntreprise;
import fr.legrain.tiers.dao.TaFamilleTiers;
import fr.legrain.tiers.dao.TaParamCreeDocTiers;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;

public class PaCriterecreationDocMultipleTiersController extends JPABaseControllerSWTStandard 
implements RetourEcranListener,IDeclencheInitEtatBoutonControllerListener{

	static Logger logger = Logger.getLogger(PaCriterecreationDocMultipleTiersController.class.getName());	
	private PaCriterecreationDocMultiple  vue = null;
	private Date dateDeb=null;
	private Date dateFin=null;
//	private TaTiers tiers = null;
	
	private List<TaTiers>listeTiers = null;
	private ContentProposalAdapter tiersContentProposal;
	
	private TaTiersDAO daoTiers = new TaTiersDAO(getEm());
	private TaFactureDAO daoFacture=new TaFactureDAO(getEm());
	private TaAvoirDAO daoAvoir=new TaAvoirDAO(getEm());
	private TaBonlivDAO daoBonLiv=new TaBonlivDAO(getEm());
	private TaBoncdeDAO daoBoncde=new TaBoncdeDAO(getEm());
	private TaApporteurDAO daoApporteur=new TaApporteurDAO(getEm());
	private TaProformaDAO daoProforma=new TaProformaDAO(getEm());
	private TaDevisDAO daoDevis=new TaDevisDAO(getEm());
	private TaPrelevementDAO daoPrelevement=new TaPrelevementDAO(getEm());
	private TaAcompteDAO daoAcompte=new TaAcompteDAO(getEm());
	private TaAvisEcheanceDAO daoAvisEcheance=new TaAvisEcheanceDAO(getEm());
	
	private TaBonliv taBonLiv = null;
	private IDocumentTiers document = null;
	
	TaInfoEntreprise infos =null;
	TaInfoEntrepriseDAO daoInfos=null;
	
	private Object ecranAppelant = null;
	private Realm realm;
	private DataBindingContext dbc;
	private String selectedTypeSelection = "";
	private String selectedTypeCreation = "";
	
	private MapChangeListener changeListener = new MapChangeListener();
	private ModelRecupDocumentCreationDoc modelDocument = null;
	private ModelTiersCreationDoc<TaTiers> modelTiers = null;
	private PaSelectionLigneDocControlleur paSelectionLigneDocControlleur=null;
	private PaSelectionMultiTiersControlleur paSelectionMultiTiersControlleur=null;
	private SWTTiers selectedCritere ;
	private List<Control> listeComposantCritere = null;
	private List<Control> listeComposantRegroupement = null;
	
	private Map<String,String> listeCorrespondanceTiers=null;
	private Map<String,String> listeTitreChampsTiers=null;
	private List<String> listeRequeteTiers=null;
	private Map<String,Object> listeObjetTiers=null;
	
	private TaTypeDonneeDAO daoType=null;
	private TaTypeDonnee typeDonnee=null;
	private String sql=null;
	private String champsTiers=null;
	private String mot=null;
	private String valeurCritere=null;
	private String valeurCritere2=null;
	private TaParamDossierIntelDAO taParamDao=null;
	private Class classNameSelectionTiers=null;
	private TaTPaiement taTPaiement=null;
	private TypeDoc typeDocPresent;
	private TaTPaiementDAO daoTPaiement = new TaTPaiementDAO();
	
	protected LgrModifyListener lgrModifyListener = new LgrModifyListener(); //surveille les modifications des champs relies a la bdd

	
	
	
	public PaCriterecreationDocMultipleTiersController(PaCriterecreationDocMultiple vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		daoTiers = new TaTiersDAO(getEm());
		daoFacture=new TaFactureDAO(getEm());
		daoAvoir=new TaAvoirDAO(getEm());
		daoBonLiv=new TaBonlivDAO(getEm());
		daoBoncde=new TaBoncdeDAO(getEm());
		daoApporteur=new TaApporteurDAO(getEm());
		daoProforma=new TaProformaDAO(getEm());
		daoDevis=new TaDevisDAO(getEm());
		daoPrelevement=new TaPrelevementDAO(getEm());
		daoAcompte=new TaAcompteDAO(getEm());
		daoAvisEcheance=new TaAvisEcheanceDAO(getEm());
		
		modelDocument = new ModelRecupDocumentCreationDoc(daoBonLiv,IHMEnteteDocument.class);
		modelTiers = new ModelTiersCreationDoc<TaTiers>(daoTiers,TaTiers.class);
		setVue(vue);

		vue.getShell().addShellListener(this);

		//Branchement action annuler : empeche la fermeture automatique de la fenetre sur ESC
		vue.getShell().addTraverseListener(new Traverse());

		initController();
		initSashFormWeight();
		daoInfos=new TaInfoEntrepriseDAO(getEm());
		infos =daoInfos.findInstance();
	}

	public PaCriterecreationDocMultipleTiersController(PaCriterecreationDocMultiple vue) {
		this(vue,null);
	}
	
	private void initController()	{
		try {	
//			setGrille(vue.getTableFacture());
			initSashFormWeight();
			setDaoStandard(daoBonLiv);
			//			setTableViewerStandard(tableViewer);
						setDbcStandard(dbc);

			initVue();
					
			
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
			Menu[] tabPopups = new Menu[] {
					popupMenuFormulaire, popupMenuGrille };
			this.initPopupAndButtons(mapActions, tabPopups);
			vue.getParent().setMenu(popupMenuFormulaire);
//			vue.getPaGrille().setMenu(popupMenuGrille);

			initEtatBouton(true);
			vue.getPaEcran().layout();
			vue.getScrolledComposite().setMinSize(vue.getPaEcran().computeSize(SWT.DEFAULT, SWT.DEFAULT));
			
//			affectationReglementControllerMini = new AffectationReglementControllerMini(this,paAffectationReglement,this.getEm());
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaTiersController", e);
		}
	}
	
	protected void initVerifySaisie() {
		if (mapInfosVerifSaisie == null)
			mapInfosVerifSaisie = new HashMap<Control, InfosVerifSaisie>();
		
//		mapInfosVerifSaisie.put(vue.getTfTiers(), new InfosVerifSaisie(new TaTiers(),Const.C_CODE_TIERS,null));
//		mapInfosVerifSaisie.put(paFormulaireReglement.getTfCODE_DOCUMENT(), new InfosVerifSaisie(new TaFacture(),Const.C_CODE_DOCUMENT,null));
//		mapInfosVerifSaisie.put(paFormulaireReglement.getTfCODE_REGLEMENT(), new InfosVerifSaisie(new TaReglement(),Const.C_CODE_REGLEMENT,null));
//		mapInfosVerifSaisie.put(paFormulaireReglement.getTfCPT_COMPTABLE(), new InfosVerifSaisie(new TaCompteBanque(),Const.C_CPTCOMPTABLE,null));
//		mapInfosVerifSaisie.put(paFormulaireReglement.getTfLIBELLE_PAIEMENT(), new InfosVerifSaisie(new TaReglement(),Const.C_LIBELLE_PAIEMENT,null));
//		mapInfosVerifSaisie.put(paFormulaireReglement.getTfMONTANT_AFFECTE(), new InfosVerifSaisie(new TaRReglement(),Const.C_MONTANT_AFFECTE,null));
//		mapInfosVerifSaisie.put(paFormulaireReglement.getTfTYPE_PAIEMENT(), new InfosVerifSaisie(new TaTPaiement(),Const.C_CODE_T_PAIEMENT,null));
	
		
		initVerifyListener(mapInfosVerifSaisie, daoBonLiv);
	}

	private void initVue() {
		
		PaSelectionLigneMultiTiers selectionLigneMultiTiers = new PaSelectionLigneMultiTiers(vue.getExpandBarGroup(), SWT.PUSH,1,
				 SWT.SINGLE|SWT.FULL_SELECTION
					| SWT.H_SCROLL
					| SWT.V_SCROLL
					| SWT.BORDER
					| SWT.CHECK);
		paSelectionMultiTiersControlleur = new PaSelectionMultiTiersControlleur(selectionLigneMultiTiers,getEm());
		ParamAffichecreationDocMultiple paramAfficheCreationDocMultiple =new ParamAffichecreationDocMultiple();
		paramAfficheCreationDocMultiple.setEnregistreDirect(true);
		paSelectionMultiTiersControlleur.configPanel(paramAfficheCreationDocMultiple);
		paSelectionMultiTiersControlleur.addDeclencheInitEtatBoutonControllerListener(getThis());
		
		
		addExpandBarItem(vue.getExpandBarGroup(), selectionLigneMultiTiers,
		"Sélectionnez les tiers à prendre en compte", Activator.getImageDescriptor(
		"/icons/logo_lgr_16.png").createImage(), SWT.DEFAULT, 250);
		vue.getExpandBarGroup().getItem(0).setExpanded(true);
		
		PaSelectionLigneDocTree selectionLigneRelance = new PaSelectionLigneDocTree(vue.getExpandBarGroup(), SWT.PUSH,1,
				 SWT.SINGLE|SWT.FULL_SELECTION
					| SWT.H_SCROLL
					| SWT.V_SCROLL
					| SWT.BORDER
					| SWT.CHECK);
		paSelectionLigneDocControlleur = new PaSelectionLigneDocControlleur(selectionLigneRelance,getEm());
		ParamAffichecreationDocMultiple paramAfficheRelanceFacture =new ParamAffichecreationDocMultiple();
		paramAfficheRelanceFacture.setEnregistreDirect(true);
		paSelectionLigneDocControlleur.configPanel(paramAfficheRelanceFacture);
		paSelectionLigneDocControlleur.addDeclencheInitEtatBoutonControllerListener(getThis());
		
		addExpandBarItem(vue.getExpandBarGroup(), selectionLigneRelance,
		"Sélectionnez les documents à prendre en compte dans les documents à créer ", Activator.getImageDescriptor(
		"/icons/logo_lgr_16.png").createImage(), SWT.DEFAULT, 280);
		vue.getExpandBarGroup().getItem(1).setExpanded(true);
		getPaSelectionMultiTiersControlleur().setPaSelectionLigneDocControlleur(paSelectionLigneDocControlleur);
	}

	@Override
	protected void actAide() throws Exception {
		// TODO Auto-generated method stub
		actAide(null);
	}

	@Override
	protected boolean aideDisponible() {
		boolean result = false;
		if (getFocusCourantSWT().equals(vue.getTfDocument()))
			result = true;
		return result;
	}
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
				Class nomClass=null;
					if (getFocusCourantSWT().equals(vue.getTfDocument())) {
						//permet de paramètrer l'affichage remplie ou non de l'aide
						AbstractLgrDAO dao = null;
						paramAfficheAideRecherche.setAfficheDetail(false);
						paramAfficheAideRecherche.setForceAffichageAideRemplie(false);
						if(getSelectedTypeSelection().equals(TypeDoc.TYPE_FACTURE)) {
							dao =new TaFactureDAO(getEm());
							nomClass=TaFacture.class;
							paramAfficheAideRecherche.setCleListeTitre("SWTPaEditorFactureController");
							paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaFactureDAO,TaFacture>(dao,IHMAideFacture.class));
						}
						if(getSelectedTypeSelection().equals(TypeDoc.TYPE_APPORTEUR)) {
							dao =new TaApporteurDAO(getEm());
							nomClass=TaApporteur.class;
							paramAfficheAideRecherche.setCleListeTitre("PaEditorApporteurController");
							paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaApporteurDAO,TaApporteur>(dao,IHMAideFacture.class));
						}
						if(getSelectedTypeSelection().equals(TypeDoc.TYPE_AVOIR)) {
							dao =new TaAvoirDAO(getEm());
							nomClass=TaAvoir.class;
							paramAfficheAideRecherche.setCleListeTitre("PaEditorAvoirController");
							paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaAvoirDAO,TaAvoir>(dao,IHMAideFacture.class));
						}
						if(getSelectedTypeSelection().equals(TypeDoc.TYPE_BON_COMMANDE)) {
							dao =new TaBoncdeDAO(getEm());
							nomClass=TaBoncde.class;
							paramAfficheAideRecherche.setCleListeTitre("PaEditorBoncdeController");
							paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaBoncdeDAO,TaBoncde>(dao,IHMAideFacture.class));
						}
						if(getSelectedTypeSelection().equals(TypeDoc.TYPE_BON_LIVRAISON)) {
							dao =new TaBonlivDAO(getEm());
							nomClass=TaBonliv.class;
							paramAfficheAideRecherche.setCleListeTitre("PaEditorBLController");
							paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaBonlivDAO,TaBonliv>(dao,IHMAideFacture.class));
						}
						if(getSelectedTypeSelection().equals(TypeDoc.TYPE_DEVIS)) {
							dao =new TaDevisDAO(getEm());
							nomClass=TaDevis.class;
							paramAfficheAideRecherche.setCleListeTitre("PaEditorDevisController");
							paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaDevisDAO,TaDevis>(dao,IHMAideFacture.class));
						}
						if(getSelectedTypeSelection().equals(TypeDoc.TYPE_PROFORMA)) {
							dao =new TaProformaDAO(getEm());
							nomClass=TaProforma.class;
							paramAfficheAideRecherche.setCleListeTitre("PaEditorProformaController");
							paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaProformaDAO,TaProforma>(dao,IHMAideFacture.class));
						}
						if(getSelectedTypeSelection().equals(TypeDoc.TYPE_ACOMPTE)) {
							dao =new TaAcompteDAO(getEm());
							nomClass=TaAcompte.class;
							paramAfficheAideRecherche.setCleListeTitre("PaEditorAcompteController");
							paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaAcompteDAO,TaAcompte>(dao,IHMAideFacture.class));
						}
						if(getSelectedTypeSelection().equals(TypeDoc.TYPE_PRELEVEMENT)) {
							dao =new TaPrelevementDAO(getEm());
							nomClass=TaPrelevement.class;
							paramAfficheAideRecherche.setCleListeTitre("PaEditorProformaController");
							paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaPrelevementDAO,TaPrelevement>(dao,IHMAideFacture.class));
						}
						if(getSelectedTypeSelection().equals(TypeDoc.TYPE_AVIS_ECHEANCE)) {
							dao =new TaAvisEcheanceDAO(getEm());
							nomClass=TaAvisEcheance.class;
							paramAfficheAideRecherche.setCleListeTitre("PaEditorProformaController");
							paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaAvisEcheanceDAO,TaAvisEcheance>(dao,IHMAideFacture.class));
						}						
						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
						controllerEcranCreation = this;
						paramAfficheAideRecherche.setTypeEntite(nomClass);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_DOCUMENT);

						paramAfficheAideRecherche.setDebutRecherche(vue.getTfDocument().getText());
						
						paramAfficheAideRecherche.setControllerAppelant(PaCriterecreationDocMultipleTiersController.this);
						paramAfficheAideRecherche.setTypeObjet(IHMAideFacture.class);
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_DOCUMENT);
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
					paAideController.addRetourEcranListener(this);
					Control focus = vue.getShell().getDisplay().getFocusControl();
					// affichage de l'ecran d'aide principal (+ ses recherches)


				}

			} finally {
				VerrouInterface.setVerrouille(false);
				vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
			}
		}
	}


	

	@Override
	protected void actAnnuler() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void actEnregistrer() throws Exception {

        paSelectionLigneDocControlleur.actEnregistrer();
		//créer le document à partir des documents selectionné dans le modelDocument
		final  ParamAfficheChoixGenerationDocument paramTmp = new ParamAfficheChoixGenerationDocument();
		ParamAfficheChoixGenerationDocument param = new ParamAfficheChoixGenerationDocument();
		selectionComboSelection();
		String finDeLigne = "\r\n";
		paramTmp.setTypeSrc(selectedTypeSelection);
		paramTmp.setTypeDest(selectedTypeCreation);
		String libelle="Reprise de ";
		paramTmp.setDateDocument(new Date());
		paramTmp.setDateLivraison(new Date());
		
		
		paramTmp.setLibelle(paSelectionMultiTiersControlleur.initialiseLibelleDoc(selectedTypeSelection)
				+" du "+LibDate.dateToString(LibDateTime.getDate(vue.getTfDateDebutPeriode()))+" au "+
				LibDate.dateToString(LibDateTime.getDate(vue.getTfDateFinPeriode())));

		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {               	
				final Shell s = new Shell(vue.getShell(),LgrShellUtil.styleLgr);		
				final PaChoixGenerationDocument paChoixGenerationDocument = new PaChoixGenerationDocument(s,SWT.NULL);
				final PaParamGenerationDocController paChoixGenerationDocumentController = new PaParamGenerationDocController(paChoixGenerationDocument);
				paChoixGenerationDocumentController.addRetourEcranListener(getThis());
				paramTmp.setTiersModifiable(false);
				final ParamAfficheSWT paramAfficheSWT = new ParamAfficheSWT();
				paramAfficheSWT.setHauteur(PaParamGenerationDocController.HAUTEUR);
				paramAfficheSWT.setLargeur(PaParamGenerationDocController.LARGEUR);
				paramAfficheSWT.setTitre(PaParamGenerationDocController.TITRE);
				LgrShellUtil.afficheSWT(paramTmp, paramAfficheSWT, paChoixGenerationDocument, paChoixGenerationDocumentController, s,true);
				if(paChoixGenerationDocumentController.getResult()!=null){
					paramTmp.setCodeDocument(paChoixGenerationDocumentController.getResult().getCodeDocument());
					paramTmp.setDateDocument(paChoixGenerationDocumentController.getResult().getDateDocument());
					paramTmp.setDateLivraison(paChoixGenerationDocumentController.getResult().getDateLivraison());
					paramTmp.setLibelle(paChoixGenerationDocumentController.getResult().getLibelle());
					paramTmp.setRepriseLibelleSrc(paChoixGenerationDocumentController.getResult().getRepriseLibelleSrc());
					paramTmp.setRepriseReferenceSrc(paChoixGenerationDocumentController.getResult().getRepriseReferenceSrc());
					paramTmp.setRepriseAucun(paChoixGenerationDocumentController.getResult().getRepriseAucun());
					paramTmp.setMultiple(true);
					//    			paramTmp=paChoixGenerationDocumentController.getResult();
				}
			}});
        param=paramTmp;
        if(param.getRetour()){
//		List<IHMEnteteDocument> listeTriee=new LinkedList<IHMEnteteDocument>();
		List<IDocumentTiers> listeDocCrees=new LinkedList<IDocumentTiers>();
		param.getListeDocumentSrc().clear();
		param.setDateDocument(paramTmp.getDateDocument());
		param.setDateLivraison(paramTmp.getDateDocument());
		for (TaCreationDoc elem : paSelectionLigneDocControlleur.getModelCreation().getListeEntity()) {
			if(elem.getAccepte()){
				for (IDocumentTiers doc : elem.getListeDoc()) {
					if(doc.getAccepte()){
						param.getListeDocumentSrc().add(doc);
						param.setDateDocument(paramTmp.getDateDocument());
						param.setDateLivraison(paramTmp.getDateDocument());
						//libelle+=" - "+doc.getCodeDocument();
					}
				}
				//creation document
				if(param.getListeDocumentSrc().size()!=0){
					IDocumentTiers doc=null; 
					//param.setLibelle(libelle);
					CreationDocumentMultiple creation = new CreationDocumentMultiple(param,getEm());
					doc=creation.creationDocument(true);
					if(doc!=null){
						listeDocCrees.add(doc);
					}
				}
			}
			param.getListeDocumentSrc().clear();

			//libelle="Reprise des documents : ";
		}
		
		param.setDateDocument(new Date());
		param.setDateLivraison(new Date());

		String message="";
			for (IDocumentTiers iDocumentTiers : listeDocCrees) {
				message+=iDocumentTiers.getCodeDocument()+finDeLigne;
			}
			if (!message.equals(""))
				MessageDialog.openInformation(vue.getShell(), "Liste des documents créés", message);

		resetTous();
        }
	}

	@Override
	protected void actFermer() throws Exception {
		// TODO Auto-generated method stub
		if(onClose())
			closeWorkbenchPart();
	}

	@Override
	protected void actImprimer() throws Exception {

	}


	@Override
	protected void actInserer() throws Exception {

	}

	@Override
	protected void actModifier() throws Exception {
		initEtatBouton(true);
	}
	
	@Override
	protected void actRefresh() throws Exception {
		try{
			vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
			//récupérer la liste des documents associable au type de document à créer
			//ces documents ne doivent pas déjà avoir de relation dans rDocument par rapport au
			//type document à créer.
			
			modelTiers.getListeObjet().clear();
			resetDoc();
			selectionComboSelection();
			if(selectedTypeCreation.equals("")){
				MessageDialog.openWarning(vue.getShell(), "Aucune correspondance pour la création", 
						"Ce type de document ne peut générer aucun autre type de document.");
				throw new Exception();
			}
			final Date dateDeb=LibDateTime.getDate(vue.getTfDateDebutPeriode());
			final Date dateFin=LibDateTime.getDate(vue.getTfDateFinPeriode());
			final String filtre=creeFiltre();
			modelTiers.remplirListe(dateDeb,
					dateFin,
					selectedTypeSelection,selectedTypeCreation,filtre,document,taTPaiement);

			if(paSelectionMultiTiersControlleur!=null){
				paSelectionMultiTiersControlleur.setMasterModel(modelTiers);
				paSelectionMultiTiersControlleur.setMasterDateDeb(LibDateTime.getDate(vue.getTfDateDebutPeriode()));
				paSelectionMultiTiersControlleur.setMasterDateFin(LibDateTime.getDate(vue.getTfDateFinPeriode()));
				paSelectionMultiTiersControlleur.setSelectedTypeCreation(selectedTypeCreation);
				paSelectionMultiTiersControlleur.setSelectedTypeSelection(selectedTypeSelection);
				paSelectionMultiTiersControlleur.setParamGen(rempliParamGeneral());
				paSelectionMultiTiersControlleur.setMasterDocument(document);
				paSelectionMultiTiersControlleur.actRefresh();
				if(document!=null)
					paSelectionMultiTiersControlleur.actEnregistrer();
			}
			initEtatBouton(false);
		}catch (Exception e) {
			logger.error("",e);
		}
		finally{
			vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
		}
	}
	
	protected void resetTous() throws Exception {
		try{
			//récupérer la liste des documents associable au type de document à créer
			//ces documents ne doivent pas déjà avoir de relation dans rDocument par rapport au
			//type document à créer.
			modelTiers.getListeObjet().clear();
			paSelectionMultiTiersControlleur.setSelectedTypeCreation(selectedTypeCreation);
			paSelectionMultiTiersControlleur.setSelectedTypeSelection(selectedTypeSelection);
			modelDocument.getListeObjet().clear();
			if(paSelectionMultiTiersControlleur!=null){
				paSelectionMultiTiersControlleur.getMasterModel().getListeEntity().clear();
				paSelectionMultiTiersControlleur.actRefresh();
			}
			resetDoc();
		}catch (Exception e) {
				logger.error("",e);
			}
		finally{
			vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
		}
	}
	protected void resetDoc() throws Exception {
		try{
			//récupérer la liste des documents associable au type de document à créer
			//ces documents ne doivent pas déjà avoir de relation dans rDocument par rapport au
			//type document à créer.
			paSelectionMultiTiersControlleur.setSelectedTypeCreation(selectedTypeCreation);
			paSelectionMultiTiersControlleur.setSelectedTypeSelection(selectedTypeSelection);
			paSelectionMultiTiersControlleur.setParamGen(rempliParamGeneral());
			if(paSelectionLigneDocControlleur!=null){
				paSelectionLigneDocControlleur.getModelCreation().getListeEntity().clear();
				paSelectionLigneDocControlleur.actRefresh();
			}

		}catch (Exception e) {
				logger.error("",e);
			}
		finally{
			vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
		}
	}
	public IStatus validateUI() throws Exception {
		if ((daoBonLiv.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
				|| (daoBonLiv.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
			ctrlTousLesChampsAvantEnregistrementSWT();
		}
		return null;
	}

	public IStatus validateUIField(String nomChamp,Object value) {
		String validationContext = "RELANCE";

		try {
			IStatus s = new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
				if (nomChamp.equals(Const.C_CODE_DOCUMENT)&& value!=null && value!=""){
					document=documentValide(value.toString());
					if(document!=null)new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
					else new Status(Status.ERROR,gestComBdPlugin.PLUGIN_ID,"Ce document n'existe pas.");
				} 
				if (nomChamp.equals(Const.C_DATE_DEBUT) ) {

				} 
				if (nomChamp.equals(Const.C_DATE_FIN) ) {

				} 
				if (nomChamp.equals(Const.C_DATE_FIN) ) {

				} 
				
			if (s.getSeverity() != IStatus.ERROR) {
			}
						 new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
			return s;
//		} catch (IllegalAccessException e) {
//			logger.error("", e);
//		} catch (InvocationTargetException e) {
//			logger.error("", e);
//		} catch (NoSuchMethodException e) {
//			logger.error("", e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void actSupprimer() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
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

		mapCommand.put(C_COMMAND_GLOBAL_PRECEDENT_ID, handlerPrecedent);
		mapCommand.put(C_COMMAND_GLOBAL_SUIVANT_ID, handlerSuivant);


		initFocusCommand(String.valueOf(this.hashCode()),listeComposantFocusable,mapCommand);

		if (mapActions == null)
			mapActions = new LinkedHashMap<Button, Object>();

		mapActions.put(vue.getBtnFermer(), C_COMMAND_GLOBAL_FERMER_ID);
		mapActions.put(vue.getBtnValider(), C_COMMAND_GLOBAL_ENREGISTRER_ID);
		mapActions.put(vue.getBtnValiderParam(), C_COMMAND_GLOBAL_REFRESH_ID);


		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID, C_COMMAND_GLOBAL_REFRESH_ID };
		mapActions.put(null, tabActionsAutres);
	}


	@Override
	protected void initComposantsVue() throws ExceptLgr {
		// TODO Auto-generated method stub

	}

	@Override
	public void initEtatComposant() {
		// TODO Auto-generated method stub
		
	}

	public void bind(){
		try {
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());
			dbc = new DataBindingContext(realm);
			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			setDbcStandard(dbc);
			selectedCritere = new SWTTiers();
			bindingFormSimple(dbc, realm, selectedCritere, SWTTiers.class);
			initEtatBouton(true);
			//vue.getComboTypeDocSelection().select(0);
			selectionComboSelection();
			vue.getRegroupement().getBtnTiers().setSelection(true);
			vue.getRegroupement().getBtnAppliquer().setSelection(false);
		} catch(Exception e) {
			logger.error("",e);
			vue.getLaMessage().setText(e.getMessage());
		}
	}

	@Override
	protected void initMapComposantChamps() {
		try {
		if (mapComposantChamps == null) 
			mapComposantChamps = new LinkedHashMap<Control,String>();

		//mapComposantChamps.put(vue.getTfDateDebutPeriode(), Const.C_DATE_DEBUT);
		//mapComposantChamps.put(vue.getTfDateFinPeriode(), Const.C_DATE_FIN);
		//mapComposantChamps.put(vue.getTfDocument(), Const.C_CODE_DOCUMENT);

		
		if (listeComposantFocusable == null) 
			listeComposantFocusable = new ArrayList<Control>();
		
		
		if (listeComposantCritere == null) 
			listeComposantCritere = new ArrayList<Control>();
		
		
		if (listeComposantRegroupement == null) 
			listeComposantRegroupement = new ArrayList<Control>();
		
		listeComposantCritere.add(vue.getTfDateDebutPeriode());
		listeComposantCritere.add(vue.getTfDateFinPeriode());
		listeComposantCritere.add(vue.getTfDocument());
		listeComposantCritere.add(vue.getCbChamps());
		listeComposantCritere.add(vue.getCbMots());
		listeComposantCritere.add(vue.getTfCritereTiers());
		listeComposantCritere.add(vue.getTfCritereTiers2());
		listeComposantCritere.add(vue.getCbTypePaiement());
		
		vue.getRegroupement().getBtnTiers().addSelectionListener(lgrModifyListener);
		vue.getRegroupement().getBtnDocument().addSelectionListener(lgrModifyListener);
		vue.getRegroupement().getBtn1semaine().addSelectionListener(lgrModifyListener);
		vue.getRegroupement().getBtn2semaines().addSelectionListener(lgrModifyListener);
		vue.getRegroupement().getBtn1mois().addSelectionListener(lgrModifyListener);
		vue.getRegroupement().getBtnXjours().addSelectionListener(lgrModifyListener);
		vue.getRegroupement().getBtnAppliquer().addSelectionListener(lgrModifyListener);
		vue.getRegroupement().getTfJours().addSelectionListener(lgrModifyListener);
		vue.getRegroupement().getTfJours().addModifyListener(lgrModifyListener);
//		vue.getTfDateDebutPeriode().addSelectionListener(lgrModifyListener);
//		vue.getTfDateFinPeriode().addSelectionListener(lgrModifyListener);
//		vue.getTfTiers().addModifyListener(lgrModifyListener);
		
		listeComposantRegroupement.add(vue.getRegroupement().getBtnTiers());
		listeComposantRegroupement.add(vue.getRegroupement().getBtnDocument());
		listeComposantRegroupement.add(vue.getRegroupement().getBtn1semaine());
		listeComposantRegroupement.add(vue.getRegroupement().getBtn2semaines());
		listeComposantRegroupement.add(vue.getRegroupement().getBtn1mois());
		listeComposantRegroupement.add(vue.getRegroupement().getBtnXjours());
		listeComposantRegroupement.add(vue.getRegroupement().getBtnAppliquer());
		listeComposantRegroupement.add(vue.getRegroupement().getTfJours());
		
		listeComposantFocusable.add(vue.getTfDateFinPeriode());
		listeComposantFocusable.add(vue.getTfDocument());
		listeComposantFocusable.add(vue.getCbChamps());
		listeComposantFocusable.add(vue.getCbMots());
		listeComposantFocusable.add(vue.getTfCritereTiers());
		listeComposantFocusable.add(vue.getTfCritereTiers2());
		listeComposantFocusable.add(vue.getCbTypePaiement());
		
		listeComposantFocusable.add(vue.getBtnValiderParam());
		listeComposantFocusable.add(vue.getBtnValider());
		listeComposantFocusable.add(vue.getBtnFermer());
		
		if(mapInitFocus == null) 
			mapInitFocus = new LinkedHashMap<ModeObjet.EnumModeObjet,Control>();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION,vue.getTfDocument());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION,vue.getTfDocument());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION,vue.getTfDocument());

		activeModifytListener();
		KeyStroke keyStroke;

		vue.getComboTypeDocSelection().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if(selectionComboSelection()) actRefresh();
				} catch (Exception e1) {
					logger.error("", e1);
				}
			}

		});
		vue.getComboTypeDocCreation().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					selectionComboCreation();
					//actRefresh();
				} catch (Exception e1) {
					logger.error("", e1);
				}
			}

		});		
		vue.getComboTypeDocSelection().select(0);
		selectionComboSelection();
		
		vue.getTfDocument().addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				try {
					if(vue.getTfDocument().isEnabled()){
						IStatus status=validateUIField(Const.C_CODE_DOCUMENT,vue.getTfDocument().getText());
						if(status.getSeverity()== IStatus.ERROR){
							MessageDialog.openWarning(vue.getShell(),"Erreur de saisie",status.getMessage());
							throw new Exception(status.getMessage());
						}
					}
				} catch (Exception e1) {
					logger.error("", e1);
					vue.getTfDocument().forceFocus();
				}
			}
		});
		
		vue.getTfDateDebutPeriode().addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				try {
					if(vue.getTfDateDebutPeriode().isEnabled()){
						IStatus status=validateUIField(Const.C_DATE_DEBUT,LibDateTime.getDate(vue.getTfDateDebutPeriode()));
						if(status.getSeverity()== IStatus.ERROR){
							MessageDialog.openWarning(vue.getShell(),"Erreur de saisie",status.getMessage());
							throw new Exception(status.getMessage());
						}
					}
				} catch (Exception e1) {
					logger.error("", e1);
					vue.getTfDateDebutPeriode().forceFocus();
				}
			}
		});
		
		vue.getTfDateFinPeriode().addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				try {
					if(vue.getTfDateFinPeriode().isEnabled()){
						IStatus status=validateUIField(Const.C_DATE_FIN,LibDateTime.getDate(vue.getTfDateFinPeriode()));
						if(status.getSeverity()== IStatus.ERROR){
							MessageDialog.openWarning(vue.getShell(),"Erreur de saisie",status.getMessage());
							throw new Exception(status.getMessage());
						}
					}
				} catch (Exception e1) {
					logger.error("", e1);
					vue.getTfDateFinPeriode().forceFocus();
				}
			}
		});
		vue.getCbChamps().removeAll();
		//traiter critères Tiers
		listeCorrespondanceTiers=new LinkedHashMap<String, String>();
		listeTitreChampsTiers=new LinkedHashMap<String, String>();
		listeObjetTiers=new LinkedHashMap<String,Object>();
		listeRequeteTiers=new ArrayList<String>();
		
			
		listeTitreChampsTiers.put("code Tiers","codeTiers");
		listeTitreChampsTiers.put("Nom","nomTiers");
		listeTitreChampsTiers.put("prenom","prenomTiers");
		listeTitreChampsTiers.put("code postal","codepostalAdresse");
		listeTitreChampsTiers.put("ville","villeAdresse");
		listeTitreChampsTiers.put("nom de l'entreprise","nomEntreprise");
		listeTitreChampsTiers.put("Famille tiers","FamilleTiers");
//		listeTitreChampsTiers.put("Note tiers","NoteTiers");
//		listeTitreChampsTiers.put("Type de notes","TnoteTiers");
		
		TaTiers tiers =new TaTiers();
		TaAdresse adresse=new TaAdresse();
		listeObjetTiers.put("codeTiers",tiers);
		listeObjetTiers.put("nomTiers",tiers);
		listeObjetTiers.put("prenomTiers",tiers);
		listeObjetTiers.put("codepostalAdresse",adresse);
		listeObjetTiers.put("villeAdresse",adresse);
		listeObjetTiers.put("nomEntreprise",new TaEntreprise());
		listeObjetTiers.put("FamilleTiers",new TaFamilleTiers());
//		listeObjetTiers.put("NoteTiers",new TaNoteTiers());
//		listeObjetTiers.put("TnoteTiers",new TaTNoteTiers());
		
		listeCorrespondanceTiers.put("codeTiers","a.codeTiers");
		listeCorrespondanceTiers.put("nomTiers","a.nomTiers");
		listeCorrespondanceTiers.put("prenomTiers","a.prenomTiers");
		listeCorrespondanceTiers.put("codepostalAdresse","adr.codepostalAdresse");
		listeCorrespondanceTiers.put("villeAdresse","adr.villeAdresse");
		listeCorrespondanceTiers.put("nomEntreprise","Ent.nomEntreprise");
		listeCorrespondanceTiers.put("FamilleTiers","ft.codeFamille");
//		listeCorrespondanceTiers.put("NoteTiers","nt.noteTiers");
//		listeCorrespondanceTiers.put("TnoteTiers","tnt.codeTNoteTiers");
		
		listeRequeteTiers.add("select a from fr.legrain.tiers.dao.TaTiers a ");
		listeRequeteTiers.add("left join a.taAdresse adr ");
		listeRequeteTiers.add("left join a.taEntreprise Ent ");
		listeRequeteTiers.add("left join a.taFamilleTierses ft ");
		listeRequeteTiers.add("left join a.taTTiers tt ");
//		listeRequeteTiers.add("left join a.taNotes nt ");
//		listeRequeteTiers.add("left join nt.taTNoteTiers tnt ");
		//dernière ligne
		listeRequeteTiers.add("where tt.idTTiers <>-1");
		
		
		for (String champs : listeTitreChampsTiers.keySet()) {
			vue.getCbChamps().add(champs);
		}
		vue.getCbChamps().setVisibleItemCount(vue.getCbChamps().getItemCount());
		vue.getCbChamps().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				selectionChamp();
			}
		});
		
		vue.getCbMots().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				selectionMot();
			}
		});
		
		vue.getCbChamps().select(0);
		selectionChamp();
		
		vue.getCbTypePaiement().removeAll();
		vue.getCbTypePaiement().add("Tous");
		List<TaTPaiement> listePaiement =null;
		listePaiement=daoTPaiement.selectAll();
		for (TaTPaiement taTPaiement : listePaiement) {
			vue.getCbTypePaiement().add(taTPaiement.getCodeTPaiement());
		}
		vue.getCbTypePaiement().select(0);
		vue.getCbTypePaiement().setVisibleItemCount(vue.getCbTypePaiement().getItemCount());
		
		vue.getCbTypePaiement().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				selectionTPaiement();
			}
		});
		
		} catch (Exception e2) {
			logger.error("", e2);
		}
	}
	private void initTiers() {

	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null) 
			mapComposantDecoratedField = new LinkedHashMap<Control,DecoratedField>();
		//mapComposantDecoratedField.put(vue.getTfDateDebutPeriode(), vue.getFieldDateDebutPeriode());
		//mapComposantDecoratedField.put(vue.getTfDateFinPeriode(), vue.getFieldDateFinPeriode());
		mapComposantDecoratedField.put(vue.getTfDocument(), vue.getFieldDocument());
	}
	protected void initImageBouton() {
		super.initImageBouton();
		vue.getBtnValiderParam().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ACCEPTER));
		vue.getBtnFermer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_FERMER));
		vue.layout(true);
	}

	@Override
	public boolean onClose() throws ExceptLgr {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public ResultAffiche configPanel(ParamAffiche param) {
		if (param!=null){
			Map<String,String[]> map = daoBonLiv.getParamWhereSQL();
			if (param.getFocusDefautSWT()!=null && !param.getFocusDefautSWT().isDisposed())
				param.getFocusDefautSWT().setFocus();
			else param.setFocusDefautSWT(vue.getTfDocument());

			if(param instanceof ParamAffichecreationDocMultiple){
//				if(((ParamAffichecreationDocMultiple)param).getIdTiers()!=0) {
//					TaTiers tiers=new TaTiersDAO(getEm()).findById(((ParamAffichecreationDocMultiple)param).getIdTiers());
//					vue.getTfTiers().setText(tiers.getCodeTiers());
//				};

				if(((ParamAffichecreationDocMultiple)param).getDateDeb()!=null){
					setDateDeb(((ParamAffichecreationDocMultiple)param).getDateDeb());
					LibDateTime.setDate(vue.getTfDateDebutPeriode(),getDateDeb());
				}else{
					LibDateTime.setDate(vue.getTfDateDebutPeriode(),infos.getDatedebInfoEntreprise());
				}
				if(((ParamAffichecreationDocMultiple)param).getDateFin()!=null){
					setDateFin(((ParamAffichecreationDocMultiple)param).getDateFin());
					LibDateTime.setDate(vue.getTfDateFinPeriode(),getDateFin());
				}else{
					LibDateTime.setDate(vue.getTfDateFinPeriode(),infos.getDatefinInfoEntreprise());
				}
				
			}
			vue.getComboTypeDocSelection().removeAll();
			typeDocPresent.getInstance();
			for (String type : typeDocPresent.getInstance().getTypeDocCompletPresent().values()) {
				if(!type.equals(TypeDoc.TYPE_REGLEMENT)&&!type.equals(TypeDoc.TYPE_REMISE)&&!type.equals(TypeDoc.TYPE_ACOMPTE))
					vue.getComboTypeDocSelection().add(type);
			}
//			vue.getComboTypeDocSelection().add(TypeDoc.TYPE_BON_LIVRAISON);
//			vue.getComboTypeDocSelection().add(TypeDoc.TYPE_FACTURE);
			int rang=0;
			rang=(vue.getComboTypeDocSelection().indexOf(TypeDoc.TYPE_BON_LIVRAISON));
			if(rang==-1)rang=0;
			vue.getComboTypeDocSelection().select(rang);
			
			bind();
	
			
			VerrouInterface.setVerrouille(false);
			initEtatBouton(true);
		}
		return null;
	}

	@Override
	public Composite getVue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void sortieChamps() {
		// TODO Auto-generated method stub
		try {
			if(getFocusCourantSWT()!=null && getFocusCourantSWT().equals(vue.getTfDocument())){
				document=null;
				if(!vue.getTfDocument().getText().trim().equals(""))
					document=documentValide(vue.getTfDocument().getText());
			}
			if(focusDansEcran())
				ctrlUnChampsSWT(getFocusCourantSWT());
		} catch (Exception e) {
			logger.error("", e);
			vue.getTfDocument().forceFocus();
		}
	}
	

	public Date getDateDeb() {
		return dateDeb;
	}

	public void setDateDeb(Date dateDeb) {
		this.dateDeb = dateDeb;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}



	public void setVue(PaCriterecreationDocMultiple vue) {
		super.setVue(vue);
		this.vue = vue;
	}


	public PaSelectionLigneDocControlleur getPaSelectionLigneRelanceControlleur() {
		return paSelectionLigneDocControlleur;
	}

	public void setPaSelectionLigneRelanceControlleur(
			PaSelectionLigneDocControlleur paSelectionLigneRelanceControlleur) {
		this.paSelectionLigneDocControlleur = paSelectionLigneRelanceControlleur;
	}

	@Override
	protected void initEtatBouton() {
		initEtatBouton(false);
	}
	
	@Override
	protected void initEtatBouton(boolean initFocus) {
		boolean trouve = false;
		switch (daoStandard.getModeObjet().getMode()) {
		case C_MO_CONSULTATION:
			if(paSelectionLigneDocControlleur!=null && paSelectionLigneDocControlleur.getModelCreation()!=null
			&& paSelectionLigneDocControlleur.getModelCreation().getListeEntity()!=null){
				for (TaCreationDoc elem : paSelectionLigneDocControlleur.getModelCreation().getListeEntity()) {
					if(elem.getAccepte()){
						for (IDocumentTiers doc : elem.getListeDoc()) {
							if(doc.getAccepte()){
								trouve=true;
								break;
							}
						}
					}
				}
			}
			enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,trouve);
			enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_FERMER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID,true);
			break;
		default:
			break;
		}
		initEtatComposant();
		if (initFocus)
			initFocusSWT(daoStandard, mapInitFocus);	

	}
	
	
	private IDocumentTiers documentValide(String code){
		if(getSelectedTypeSelection().equals(TypeDoc.TYPE_FACTURE)) {
			document=daoFacture.findByCode(code);
			}
		if(getSelectedTypeSelection().equals(TypeDoc.TYPE_APPORTEUR)) {
			document=daoApporteur.findByCode(code);
		}
		if(getSelectedTypeSelection().equals(TypeDoc.TYPE_AVOIR)) {
			document=daoAvoir.findByCode(code);
		}
		if(getSelectedTypeSelection().equals(TypeDoc.TYPE_BON_COMMANDE)) {
			document=daoBoncde.findByCode(code);
		}
		if(getSelectedTypeSelection().equals(TypeDoc.TYPE_BON_LIVRAISON)) {
			document=daoBonLiv.findByCode(code);
		}
		if(getSelectedTypeSelection().equals(TypeDoc.TYPE_DEVIS)) {
			document=daoDevis.findByCode(code);
		}
		if(getSelectedTypeSelection().equals(TypeDoc.TYPE_PROFORMA)) {
			document=daoProforma.findByCode(code);
		}
		if(getSelectedTypeSelection().equals(TypeDoc.TYPE_ACOMPTE)) {
			document=daoAcompte.findByCode(code);
		}
		if(getSelectedTypeSelection().equals(TypeDoc.TYPE_PRELEVEMENT)) {
			document=daoPrelevement.findByCode(code);
		}
		if(getSelectedTypeSelection().equals(TypeDoc.TYPE_AVIS_ECHEANCE)) {
			document=daoAvisEcheance.findByCode(code);
		}
		return document;
	}
	
	public void retourEcran(final RetourEcranEvent evt) {
		if (evt.getRetour() != null
				&& (evt.getSource() instanceof SWTPaAideControllerSWT)) {
			if (getFocusAvantAideSWT() instanceof Text) {
				try {
					((Text) getFocusAvantAideSWT()).setText(((ResultAffiche) evt
							.getRetour()).getResult());
					document=documentValide(((ResultAffiche) evt.getRetour()).getResult());
					validateUIField(Const.C_CODE_DOCUMENT,document.getCodeDocument());
					//ctrlUnChampsSWT(getFocusAvantAideSWT());
				} catch (Exception e) {
					logger.error("",e);
					if(getFocusAvantAideSWT()!=null)setFocusCourantSWT(getFocusAvantAideSWT());
					vue.getLaMessage().setText(e.getMessage());
				}
			}			
		} else if (evt.getRetour() != null){

		}
		super.retourEcran(evt);
	}

	public PaCriterecreationDocMultipleTiersController getThis() {
		return this;
	}

	public String getSelectedTypeSelection() {
		return selectedTypeSelection;
	}

	public void setSelectedTypeSelection(String selectedType) {
		this.selectedTypeSelection = selectedType;
	}

	public boolean selectionComboSelection(){
		boolean changement = false;
		try{
		TypeDoc.getInstance();
		String typeEntrant=selectedTypeSelection;
		if(vue.getComboTypeDocSelection().getSelectionIndex()!=-1){
		if(vue.getComboTypeDocSelection().getItem(vue.getComboTypeDocSelection().getSelectionIndex()).equals(TypeDoc.TYPE_ACOMPTE)){
			selectedTypeSelection=TypeDoc.TYPE_ACOMPTE;
		}
		if(vue.getComboTypeDocSelection().getItem(vue.getComboTypeDocSelection().getSelectionIndex()).equals(TypeDoc.TYPE_APPORTEUR)){
			selectedTypeSelection=TypeDoc.TYPE_APPORTEUR;
		}
		if(vue.getComboTypeDocSelection().getItem(vue.getComboTypeDocSelection().getSelectionIndex()).equals(TypeDoc.TYPE_AVOIR)){
			selectedTypeSelection=TypeDoc.TYPE_AVOIR;
		}
		if(vue.getComboTypeDocSelection().getItem(vue.getComboTypeDocSelection().getSelectionIndex()).equals(TypeDoc.TYPE_BON_COMMANDE)){
			selectedTypeSelection=TypeDoc.TYPE_BON_COMMANDE;
		}
		if(vue.getComboTypeDocSelection().getItem(vue.getComboTypeDocSelection().getSelectionIndex()).equals(TypeDoc.TYPE_BON_LIVRAISON)){
			selectedTypeSelection=TypeDoc.TYPE_BON_LIVRAISON;
		}
		if(vue.getComboTypeDocSelection().getItem(vue.getComboTypeDocSelection().getSelectionIndex()).equals(TypeDoc.TYPE_DEVIS)){
			selectedTypeSelection=TypeDoc.TYPE_DEVIS;
		}
		if(vue.getComboTypeDocSelection().getItem(vue.getComboTypeDocSelection().getSelectionIndex()).equals(TypeDoc.TYPE_FACTURE)){
			selectedTypeSelection=TypeDoc.TYPE_FACTURE;
		}
		if(vue.getComboTypeDocSelection().getItem(vue.getComboTypeDocSelection().getSelectionIndex()).equals(TypeDoc.TYPE_PRELEVEMENT)){
			selectedTypeSelection=TypeDoc.TYPE_PRELEVEMENT;
		}
		if(vue.getComboTypeDocSelection().getItem(vue.getComboTypeDocSelection().getSelectionIndex()).equals(TypeDoc.TYPE_PROFORMA)){
			selectedTypeSelection=TypeDoc.TYPE_PROFORMA;
		}
		if(vue.getComboTypeDocSelection().getItem(vue.getComboTypeDocSelection().getSelectionIndex()).equals(TypeDoc.TYPE_AVIS_ECHEANCE)){
			selectedTypeSelection=TypeDoc.TYPE_AVIS_ECHEANCE;
		}
		if(!typeEntrant.equalsIgnoreCase(selectedTypeSelection)){
			resetTous();
			changement=true;
			vue.getComboTypeDocCreation().removeAll();
			for (int i = 0; i < TypeDoc.getInstance().getEditorDocPossibleCreationDocument().size(); i++) {
				if(TypeDoc.getInstance().getEditorDocPossibleCreationDocument().get(i)[0].equalsIgnoreCase(selectedTypeSelection)){
					vue.getComboTypeDocCreation().add(TypeDoc.getInstance().getEditorDocPossibleCreationDocument().get(i)[1]);
				}
				vue.getComboTypeDocCreation().select(0);
			}
//			tiersContentProposal
//			.setContentProposalProvider(contentProposalProviderTiers());
		}
		}
		selectionComboCreation();
		}catch (Exception e) {
			logger.error("", e);
			return false;
		}
		return changement;
	}
	
	public void selectionComboCreation(){
		String oldSelectedTypeCreation=selectedTypeCreation;
		selectedTypeCreation="";
		if(vue.getComboTypeDocCreation().getSelectionIndex()!=-1){
			if(vue.getComboTypeDocCreation().getItem(vue.getComboTypeDocCreation().getSelectionIndex()).equals(TypeDoc.TYPE_ACOMPTE)){
				selectedTypeCreation=TypeDoc.TYPE_ACOMPTE;
			}
			if(vue.getComboTypeDocCreation().getItem(vue.getComboTypeDocCreation().getSelectionIndex()).equals(TypeDoc.TYPE_APPORTEUR)){
				selectedTypeCreation=TypeDoc.TYPE_APPORTEUR;
			}
			if(vue.getComboTypeDocCreation().getItem(vue.getComboTypeDocCreation().getSelectionIndex()).equals(TypeDoc.TYPE_AVOIR)){
				selectedTypeCreation=TypeDoc.TYPE_AVOIR;
			}
			if(vue.getComboTypeDocCreation().getItem(vue.getComboTypeDocCreation().getSelectionIndex()).equals(TypeDoc.TYPE_BON_COMMANDE)){
				selectedTypeCreation=TypeDoc.TYPE_BON_COMMANDE;
			}
			if(vue.getComboTypeDocCreation().getItem(vue.getComboTypeDocCreation().getSelectionIndex()).equals(TypeDoc.TYPE_BON_LIVRAISON)){
				selectedTypeCreation=TypeDoc.TYPE_BON_LIVRAISON;
			}
			if(vue.getComboTypeDocCreation().getItem(vue.getComboTypeDocCreation().getSelectionIndex()).equals(TypeDoc.TYPE_DEVIS)){
				selectedTypeCreation=TypeDoc.TYPE_DEVIS;
			}
			if(vue.getComboTypeDocCreation().getItem(vue.getComboTypeDocCreation().getSelectionIndex()).equals(TypeDoc.TYPE_FACTURE)){
				selectedTypeCreation=TypeDoc.TYPE_FACTURE;
			}
			if(vue.getComboTypeDocCreation().getItem(vue.getComboTypeDocCreation().getSelectionIndex()).equals(TypeDoc.TYPE_PRELEVEMENT)){
				selectedTypeCreation=TypeDoc.TYPE_PRELEVEMENT;
			}
			if(vue.getComboTypeDocCreation().getItem(vue.getComboTypeDocCreation().getSelectionIndex()).equals(TypeDoc.TYPE_PROFORMA)){
				selectedTypeCreation=TypeDoc.TYPE_PROFORMA;
			}
			if(vue.getComboTypeDocCreation().getItem(vue.getComboTypeDocCreation().getSelectionIndex()).equals(TypeDoc.TYPE_AVIS_ECHEANCE)){
				selectedTypeCreation=TypeDoc.TYPE_AVIS_ECHEANCE;
			}
			if(!oldSelectedTypeCreation.equals(selectedTypeCreation))
				try {
					resetDoc();
				} catch (Exception e) {
					logger.error("", e);
				}
		}

	}
	public String getSelectedTypeCreation() {
		return selectedTypeCreation;
	}

	public void setSelectedTypeCreation(String selectedTypeCreation) {
		this.selectedTypeCreation = selectedTypeCreation;
	}

	
	public class TaDocComparator implements Comparator<IHMEnteteDocument> {
		 
	    public int compare(IHMEnteteDocument doc1, IHMEnteteDocument doc2) {
	     	        
	        int premier = doc1.getCodeTiers().compareTo(doc2.getCodeTiers());
	        
	        int deuxieme = doc1.getDateDocument().compareTo(doc2.getDateDocument());

	        int compared = premier;
	        if (compared == 0) {
	            compared = deuxieme;
	        }
	 
	        return compared;
	    }
	}
//	public String[][] initAdapterTiers() {
//		String[][] valeurs = null;
//		//boolean affichageCtrlEspace = GestionCommercialePlugin.getDefault().getPreferenceStore().getBoolean(fr.legrain.gestionCommerciale.preferences.PreferenceConstants.AFFICHAGE_CTRL_ESPACE);
//		//if(affichageCtrlEspace){
//		TaTiersDAO taTiersDAO = new TaTiersDAO(getEm());
//		//#AFAIRE
//		taTiersDAO.setPreferences(FacturePlugin.getDefault().getPreferenceStore().getString(fr.legrain.facture.preferences.PreferenceConstants.TYPE_TIERS_DOCUMENT));
//		if( !selectedTypeSelection.equals("")&& !selectedTypeCreation.equals(""))
//			listeTiers = taTiersDAO.selectTiersDocNonTransforme(selectedTypeSelection,selectedTypeCreation,vue.getTfDateDebutPeriode().getSelection()
//					,vue.getTfDateFinPeriode().getSelection());
//		if(listeTiers!=null){
//		valeurs = new String[listeTiers.size()][2];
//		int i = 0;
//		String description = "";
//		for (TaTiers taTiers : listeTiers) {
//			valeurs[i][0] = taTiers.getCodeTiers();
//			
//			description = "";
////			description += taTiers.getCodeCompta() + " - " + taTiers.getCompte() + " - " + taTiers.getNomTiers();
////			if(taTiers.getTaTTiers()!=null) {
////				description += " - " + taTiers.getTaTTiers().getLibelleTTiers();
////			}
//			valeurs[i][1] = description;
//
//			i++;
//		}
//		}
//		taTiersDAO = null;
//		//}
//		return valeurs;
//	}
//	public ContentProposalProvider contentProposalProviderTiers(){
//		String[][] TabTiers = initAdapterTiers();
//		String[] listeCodeTiers = null;
//		String[] listeDescriptionTiers = null;
//		if (TabTiers != null) {
//			listeCodeTiers = new String[TabTiers.length];
//			listeDescriptionTiers = new String[TabTiers.length];
//			for (int i = 0; i < TabTiers.length; i++) {
//				listeCodeTiers[i] = TabTiers[i][0];
//				listeDescriptionTiers[i] = TabTiers[i][1];
//			}
//		}
//		return new ContentProposalProvider(listeCodeTiers,
//				listeDescriptionTiers);		
//	}


	public PaSelectionMultiTiersControlleur getPaSelectionMultiTiersControlleur() {
		return paSelectionMultiTiersControlleur;
	}

	public void setPaSelectionMultiTiersControlleur(
			PaSelectionMultiTiersControlleur paSelectionMultiTiersControlleur) {
		this.paSelectionMultiTiersControlleur = paSelectionMultiTiersControlleur;
	}


	public TaParamCreeDocTiers rempliParamGeneral(){
		TaParamCreeDocTiers paramGen = new TaParamCreeDocTiers();
		paramGen.setTiers(LibConversion.booleanToInt(vue.getRegroupement().getBtnTiers().getSelection()));
		paramGen.setDocument(LibConversion.booleanToInt(vue.getRegroupement().getBtnDocument().getSelection()));;
		paramGen.setSemaine(LibConversion.booleanToInt(vue.getRegroupement().getBtn1semaine().getSelection()));
		paramGen.setDeuxSemaines(LibConversion.booleanToInt(vue.getRegroupement().getBtn2semaines().getSelection()));
		paramGen.setMois(LibConversion.booleanToInt(vue.getRegroupement().getBtn1mois().getSelection()));
		paramGen.setxJours(LibConversion.booleanToInt(vue.getRegroupement().getBtnXjours().getSelection()));
		paramGen.setNbJours(LibConversion.stringToInteger(vue.getRegroupement().getTfJours().getText()));
		paramGen.setAppliquerATous(vue.getRegroupement().getBtnAppliquer().getSelection());
		paramGen.setDecade(LibConversion.booleanToInt(vue.getRegroupement().getBtnDecad().getSelection()));
		paramGen.setTaTiers(null);
		return paramGen;
	}

	public class LgrModifyListener  implements ModifyListener, SelectionListener{

		public void modifyText(ModifyEvent e) {
			try {
				if(listeComposantCritere.contains(e.getSource())){
					resetTous();
					((Control)e.getSource()).forceFocus();
				}
				if(listeComposantRegroupement.contains(e.getSource())){
					resetDoc();
					((Control)e.getSource()).forceFocus();
				}
			} catch (Exception e1) {

			}	
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			// TODO Auto-generated method stub
			try {
				if(listeComposantCritere.contains(e.getSource())){
					resetTous();
				}
				if(listeComposantRegroupement.contains(e.getSource())){
					resetDoc();
				}
			} catch (Exception e1) {

			}	
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			// TODO Auto-generated method stub
			widgetSelected(e);
		}
	}
	
	public void selectionChamp(){
		String[] tab= new String[]{};
		String champPropertie;
		if (daoType==null)daoType =new TaTypeDonneeDAO(getEm());
		
		champsTiers=listeTitreChampsTiers.get(vue.getCbChamps().getItem(vue.getCbChamps().getSelectionIndex()));
		champPropertie=listeCorrespondanceTiers.get(champsTiers);
		tab=champPropertie.split("\\.");
		if(tab.length>0)
			champPropertie=tab[tab.length-1];
		
		classNameSelectionTiers =renvoiTypeChampsTiers(champPropertie,listeObjetTiers.get(champsTiers));
		
		typeDonnee=daoType.findByCode(classNameSelectionTiers.getName());
		if(typeDonnee!=null){
			vue.getCbMots().removeAll();
			for (TaRParamDossierIntel rParam : typeDonnee.getTaRParamDossierInteles()) {
				vue.getCbMots().add(rParam.getTaParamDossierIntel().getMot());
			}
			int index=vue.getCbMots().indexOf("EST EGALE A");
			if(index==-1)index=0;
			vue.getCbMots().select(index);
			selectionMot();
		}
		champsTiers=listeCorrespondanceTiers.get(champsTiers);
		
		
		vue.getTfCritereTiers().setText("");
		vue.getTfCritereTiers2().setText("");
		vue.getCbMots().setVisibleItemCount(vue.getCbMots().getItemCount());
		selectionMot();
	}
	
	public void selectionTPaiement(){
		String code=vue.getCbTypePaiement().getText();
		if(code.equals("Tous")){
			taTPaiement=null;
		}else{
			taTPaiement=daoTPaiement.findByCode(code);
		}
			
		
	}
	public void selectionMot(){
		mot="";
		vue.getTfCritereTiers2().setEnabled(false);
		vue.getTfCritereTiers().setText("");
		vue.getTfCritereTiers2().setText("");
		if(vue.getCbMots().getSelectionIndex()!=-1)
			mot=vue.getCbMots().getItem(vue.getCbMots().getSelectionIndex());
		if(mot!=null && !mot.equals("")){
			if(taParamDao==null) taParamDao=new TaParamDossierIntelDAO(getEm());
			TaParamDossierIntel taParamDossierIntel=taParamDao.findByCode(mot);
			if(taParamDossierIntel!=null){
				sql=taParamDossierIntel.getSql();
				vue.getCbMots().setToolTipText(sql);
				vue.getTfCritereTiers2().setEnabled(taParamDossierIntel.getNbZones()>1);
			}
		}
	}

	public Class renvoiTypeChampsTiers(String champs,Object objet){
		try {
			return PropertyUtils.getPropertyType(objet, champs);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String creeFiltre(){
		try {
		Boolean appliquer=false;
		String sqlSuppDeb="";
		String sqlSuppFin="";
		String sqlLoc="";
			String critere="";
			sqlLoc=sql;
			//champs=listeCorrespondanceTiers.get(champs);
			if(!vue.getTfCritereTiers2().getText().equals("")){
				valeurCritere=vue.getTfCritereTiers().getText();
				valeurCritere2=vue.getTfCritereTiers2().getText();			
				valeurCritere=valeurCritere.replace("*", "%");
				valeurCritere2=valeurCritere2.replace("*", "%");
				if(classNameSelectionTiers == Date.class){

						valeurCritere=LibDate.StringDateToStringSql(valeurCritere,new Date());

					valeurCritere2=LibDate.StringDateToStringSql(valeurCritere2,new Date());
					critere=champsTiers+" "+sqlLoc+" cast('"+valeurCritere+"' as date) and cast('"+valeurCritere2+"' as date) ";
				}else if(classNameSelectionTiers == String.class){
					critere="upper("+champsTiers+") "+sqlLoc+" upper('"+valeurCritere+"') and upper('"+valeurCritere2+"')";
				}else{
					critere=champsTiers+" "+sqlLoc+" "+valeurCritere+" and "+valeurCritere2;	
				}
			}
			else
				if(!vue.getTfCritereTiers().getText().equals("")){
					valeurCritere=vue.getTfCritereTiers().getText();
					valeurCritere=valeurCritere.replace("*", "%");
					if(classNameSelectionTiers == Date.class){
						valeurCritere=LibDate.StringDateToStringSql(valeurCritere,new Date());
						critere=champsTiers+" "+sqlLoc+" cast('"+valeurCritere+"' as date)";
					}else if(classNameSelectionTiers == String.class){					
					if(sqlLoc.contains("like")){
						valeurCritere=valeurCritere.replace("%", "");
						sqlSuppDeb="%";
						sqlSuppFin="%";
					}
					if(sqlLoc.contains("start with")){
						valeurCritere=valeurCritere.replace("%", "");
						sqlLoc="like";
						sqlSuppDeb="";
						sqlSuppFin="%";
					}
					if(sqlLoc.contains("finish with")){
						valeurCritere=valeurCritere.replace("%", "");
						sqlLoc="like";
						sqlSuppDeb="%";
						sqlSuppFin="";
					}
					critere="upper("+champsTiers+") "+sqlLoc+" upper('"+sqlSuppDeb+valeurCritere+sqlSuppFin+"')";
					}
					else{
						critere=champsTiers+" "+sqlLoc+" "+sqlSuppDeb+valeurCritere+sqlSuppFin;	
					}
				}

			String requete="";
				//"select a from TaTiers a left join a.taCommerciaux com where a.taTTiers.idTTiers <>-1 ";
			for (String req : listeRequeteTiers) {
				requete=requete+" "+req;
			}
			
			if(!critere.equals("")){
				requete+=" and "+critere;
			}
		return requete;
		} catch (java.text.ParseException e) {
			logger.error("", e);
		}
		return "";
	}


	@Override
	public void DeclencheInitEtatBoutonController(DeclencheInitEtatBoutonControllerEvent evt) {
		try {
			sourceDeclencheCommandeController = evt.getSource();
			initEtatBouton(false);
		} catch (Exception e) {
			logger.error("",e);
		} finally {
			sourceDeclencheCommandeController = null;
		}
	}
	public void addDeclencheInitBorneControllerListener(IDeclencheInitEtatBoutonControllerListener l) {
		listenerList.add(IDeclencheInitEtatBoutonControllerListener.class, l);
	}
	
	public void removeDeclencheInitBorneControllerListener(IDeclencheInitEtatBoutonControllerListener l) {
		listenerList.remove(IDeclencheInitEtatBoutonControllerListener.class, l);
	}
	
	protected void fireDeclencheInitBorneController(DeclencheInitEtatBoutonControllerEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == IDeclencheInitEtatBoutonControllerListener.class) {
				if (e == null)
					e = new DeclencheInitEtatBoutonControllerEvent(this);
				( (IDeclencheInitEtatBoutonControllerListener) listeners[i + 1]).DeclencheInitEtatBoutonController(e);
			}
		}
	}
}

package fr.legrain.creationDocMultiple.controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import org.eclipse.jface.bindings.keys.ParseException;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.IContentProposalListener;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
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

import creationdocMultiple.CreationDocMultiplePlugin;
import fr.legrain.creationDocMultiple.divers.ParamAffichecreationDocMultiple;
import fr.legrain.creationDocMultiple.ecran.PaCriterecreationDocMultiple;
import fr.legrain.creationDocMultiple.ecran.PaSelectionLigneDoc;
import fr.legrain.document.divers.ModelRecupDocumentCreationDoc;
import fr.legrain.document.divers.TypeDoc;
import fr.legrain.documents.dao.TaAcompteDAO;
import fr.legrain.documents.dao.TaApporteurDAO;
import fr.legrain.documents.dao.TaAvoirDAO;
import fr.legrain.documents.dao.TaBoncdeDAO;
import fr.legrain.documents.dao.TaBonliv;
import fr.legrain.documents.dao.TaBonlivDAO;
import fr.legrain.documents.dao.TaDevisDAO;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaPrelevementDAO;
import fr.legrain.documents.dao.TaProformaDAO;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.facture.FacturePlugin;
import fr.legrain.generationdocument.controllers.PaChoixGenerationDocumentController;
import fr.legrain.generationdocument.divers.ParamAfficheChoixGenerationDocument;
import fr.legrain.generationdocument.ecran.PaChoixGenerationDocument;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Document.IHMEnteteDocument;
import fr.legrain.gestCom.Module_Tiers.SWTTiers;
import fr.legrain.gestCom.gestComBd.gestComBdPlugin;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideRechercheSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideRechercheControllerSWT;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.ContentProposalProvider;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.InfosVerifSaisie;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.data.VerrouInterface;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ParamAfficheSWT;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.lib.gui.aide.PaAideRechercheSWT;
import fr.legrain.lib.gui.aide.PaAideSWT;
import fr.legrain.lib.gui.aide.ParamAfficheAide;
import fr.legrain.libMessageLGR.LgrMess;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.ecran.PaTiersSWT;
import fr.legrain.tiers.ecran.ParamAfficheTiers;
import fr.legrain.tiers.ecran.SWTPaTiersController;
import fr.legrain.tiers.editor.EditorInputTiers;
import fr.legrain.tiers.editor.TiersMultiPageEditor;

public class PaCriterecreationDocMultipleController extends JPABaseControllerSWTStandard 
implements RetourEcranListener{

	static Logger logger = Logger.getLogger(PaCriterecreationDocMultipleController.class.getName());	
	private PaCriterecreationDocMultiple  vue = null;
	private Date dateDeb=null;
	private Date dateFin=null;
//	private TaTiers tiers = null;
	
	private List<TaTiers>listeTiers = null;
	private ContentProposalAdapter tiersContentProposal;
	
	private TaFactureDAO daoFacture=new TaFactureDAO(getEm());
	private TaAvoirDAO daoAvoir=new TaAvoirDAO(getEm());
	private TaBonlivDAO daoBonLiv=new TaBonlivDAO(getEm());
	private TaBoncdeDAO daoBoncde=new TaBoncdeDAO(getEm());
	private TaApporteurDAO daoApporteur=new TaApporteurDAO(getEm());
	private TaProformaDAO daoProforma=new TaProformaDAO(getEm());
	private TaDevisDAO daoDevis=new TaDevisDAO(getEm());
	private TaPrelevementDAO daoPrelevement=new TaPrelevementDAO(getEm());
	private TaAcompteDAO daoAcompte=new TaAcompteDAO(getEm());
	
	private TaBonliv taBonLiv = null;
	
	TaInfoEntreprise infos =null;
	TaInfoEntrepriseDAO daoInfos=null;
	
	private Object ecranAppelant = null;
	private Realm realm;
	private DataBindingContext dbc;
	private String selectedTypeSelection = "";
	private String selectedTypeCreation = "";
	
	private MapChangeListener changeListener = new MapChangeListener();
	private ModelRecupDocumentCreationDoc modelDocument = null;
	private PaSelectionLigneDocControlleur paSelectionLigneDocControlleur=null;
	private IHMEnteteDocument selectedCritere ;
	public PaCriterecreationDocMultipleController(PaCriterecreationDocMultiple vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		daoFacture=new TaFactureDAO(getEm());
		daoAvoir=new TaAvoirDAO(getEm());
		daoBonLiv=new TaBonlivDAO(getEm());
		daoBoncde=new TaBoncdeDAO(getEm());
		daoApporteur=new TaApporteurDAO(getEm());
		daoProforma=new TaProformaDAO(getEm());
		daoDevis=new TaDevisDAO(getEm());
		daoPrelevement=new TaPrelevementDAO(getEm());
		daoAcompte=new TaAcompteDAO(getEm());
		
		modelDocument = new ModelRecupDocumentCreationDoc(daoBonLiv,IHMEnteteDocument.class);
		setVue(vue);

		vue.getShell().addShellListener(this);

		//Branchement action annuler : empeche la fermeture automatique de la fenetre sur ESC
		vue.getShell().addTraverseListener(new Traverse());

		initController();
		initSashFormWeight();
		daoInfos=new TaInfoEntrepriseDAO(getEm());
		infos =daoInfos.findInstance();
	}

	public PaCriterecreationDocMultipleController(PaCriterecreationDocMultiple vue) {
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
		PaSelectionLigneDoc selectionLigneRelance = new PaSelectionLigneDoc(vue.getExpandBarGroup(), SWT.PUSH,1,
				 SWT.SINGLE|SWT.FULL_SELECTION
					| SWT.H_SCROLL
					| SWT.V_SCROLL
					| SWT.BORDER
					| SWT.CHECK);
		paSelectionLigneDocControlleur = new PaSelectionLigneDocControlleur(selectionLigneRelance,getEm());
		ParamAffichecreationDocMultiple paramAfficheRelanceFacture =new ParamAffichecreationDocMultiple();
		paramAfficheRelanceFacture.setEnregistreDirect(true);
		paSelectionLigneDocControlleur.configPanel(paramAfficheRelanceFacture);
		
		addExpandBarItem(vue.getExpandBarGroup(), selectionLigneRelance,
		"Sélectionnez les documents à prendre en compte dans le document à créer ", CreationDocMultiplePlugin.getImageDescriptor(
		"/icons/logo_lgr_16.png").createImage(), SWT.DEFAULT, 400);
		vue.getExpandBarGroup().getItem(0).setExpanded(true);
	}

	@Override
	protected void actAide() throws Exception {
		// TODO Auto-generated method stub
		actAide(null);
	}

	@Override
	protected boolean aideDisponible() {
		boolean result = false;
		if (getFocusCourantSWT().equals(vue.getTfTiers()))
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
					if (getFocusCourantSWT().equals(vue.getTfTiers())) {
						//permet de paramètrer l'affichage remplie ou non de l'aide

						PaTiersSWT paTiersSWT = new PaTiersSWT(s2, SWT.NULL);
						SWTPaTiersController swtPaTiersController = new SWTPaTiersController(paTiersSWT);
						paramAfficheAideRecherche.setForceAffichageAideRemplie(false);

						editorCreationId = TiersMultiPageEditor.ID_EDITOR;
						
						editorInputCreation = new EditorInputTiers();

						ParamAfficheTiers paramAfficheTiers = new ParamAfficheTiers();
						paramAfficheAideRecherche.setJPQLQuery(new TaTiersDAO(getEm()).selectTiersDocNonTransformeRequete(selectedTypeSelection,
								selectedTypeCreation,vue.getTfDateDebutPeriode().getSelection(),vue.getTfDateFinPeriode().getSelection()));
						paramAfficheTiers.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheTiers.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaTiersController;
						parametreEcranCreation = paramAfficheTiers;
						paramAfficheAideRecherche.setForceAffichageAideRemplie(true);
						paramAfficheAideRecherche.setTypeEntite(TaTiers.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_TIERS);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfTiers().getText());
						paramAfficheAideRecherche.setControllerAppelant(getThis());
//						ModelTiers modelTiers = new ModelTiers(swtPaTiersController.getIbTaTable());
//						paramAfficheAideRecherche.setModel(modelTiers);
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTiers,TaTiersDAO,TaTiers>(SWTTiers.class,getEm()));
						paramAfficheAideRecherche.setTypeObjet(swtPaTiersController.getClassModel());
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_TIERS);
						
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
//créer le document à partir des documents selectionné dans le modelDocument
		final ParamAfficheChoixGenerationDocument param = new ParamAfficheChoixGenerationDocument();
		selectionComboSelection();
		param.setTypeSrc(selectedTypeSelection);
		param.setTypeDest(selectedTypeCreation);
		String libelle="Reprise des documents : ";
		param.setDateDocument(null);
		param.setDateLivraison(new Date(0));
		for (Object documents : modelDocument.getListeObjet()) {
			if(((IHMEnteteDocument)documents).getAccepte()){
				if(selectedTypeSelection==TypeDoc.TYPE_BON_LIVRAISON){
					param.getListeDocumentSrc().add(daoBonLiv.findById(((IHMEnteteDocument)documents).getIdDocument()));
				}
				if(selectedTypeSelection==TypeDoc.TYPE_BON_COMMANDE){
					param.getListeDocumentSrc().add(daoBoncde.findById(((IHMEnteteDocument)documents).getIdDocument()));
				}
				if(selectedTypeSelection==TypeDoc.TYPE_ACOMPTE){
					param.getListeDocumentSrc().add(daoAcompte.findById(((IHMEnteteDocument)documents).getIdDocument()));
				}
				if(selectedTypeSelection==TypeDoc.TYPE_AVOIR){
					param.getListeDocumentSrc().add(daoAvoir.findById(((IHMEnteteDocument)documents).getIdDocument()));
				}
				if(selectedTypeSelection==TypeDoc.TYPE_APPORTEUR){
					param.getListeDocumentSrc().add(daoApporteur.findById(((IHMEnteteDocument)documents).getIdDocument()));
				}
				if(selectedTypeSelection==TypeDoc.TYPE_DEVIS){
					param.getListeDocumentSrc().add(daoDevis.findById(((IHMEnteteDocument)documents).getIdDocument()));
				}
				if(selectedTypeSelection==TypeDoc.TYPE_FACTURE){
					param.getListeDocumentSrc().add(daoFacture.findById(((IHMEnteteDocument)documents).getIdDocument()));
				}
				if(selectedTypeSelection==TypeDoc.TYPE_PRELEVEMENT){
					param.getListeDocumentSrc().add(daoPrelevement.findById(((IHMEnteteDocument)documents).getIdDocument()));
				}
				if(selectedTypeSelection==TypeDoc.TYPE_PROFORMA){
					param.getListeDocumentSrc().add(daoProforma.findById(((IHMEnteteDocument)documents).getIdDocument()));
				}
				libelle=libelle+" - "+((IHMEnteteDocument)documents).getCodeDocument();
				if(param.getDateLivraison().before(((IHMEnteteDocument)documents).getDateDocument()))
					param.setDateLivraison(((IHMEnteteDocument)documents).getDateDocument());
			}
		}
		if(param.getListeDocumentSrc().size()==0){
			MessageDialog.openWarning(vue.getShell(), "Création document", "Il n'y a aucun document de sélectionné...");

		}else{
			param.setLibelle(libelle);
			final Shell s = new Shell(vue.getShell(),LgrShellUtil.styleLgr);		
			final PaChoixGenerationDocument paChoixGenerationDocument = new PaChoixGenerationDocument(s,SWT.NULL);
			final PaChoixGenerationDocumentController paChoixGenerationDocumentController = new PaChoixGenerationDocumentController(paChoixGenerationDocument);
			paChoixGenerationDocumentController.addRetourEcranListener(getThis());
			final ParamAfficheSWT paramAfficheSWT = new ParamAfficheSWT();
			paramAfficheSWT.setHauteur(PaChoixGenerationDocumentController.HAUTEUR);
			paramAfficheSWT.setLargeur(PaChoixGenerationDocumentController.LARGEUR);
			paramAfficheSWT.setTitre(PaChoixGenerationDocumentController.TITRE);
			LgrShellUtil.afficheSWT(param, paramAfficheSWT, paChoixGenerationDocument, paChoixGenerationDocumentController, s);
			if(param.getFocus()!=null)
				param.getFocusDefaut().requestFocus();
			reset();
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
			//récupérer la liste des documents associable au type de document à créer
			//ces documents ne doivent pas déjà avoir de relation dans rDocument par rapport au
			//type document à créer.			
			modelDocument.getListeObjet().clear();
			selectionComboSelection();
			modelDocument.remplirListe(vue.getTfDateDebutPeriode().getSelection(),
					 vue.getTfDateFinPeriode().getSelection(),vue.getTfTiers().getText(),
					 selectedTypeSelection,selectedTypeCreation,null);
			modelDocument.remplirListe();
			if(paSelectionLigneDocControlleur!=null){
				paSelectionLigneDocControlleur.setMasterModelDocument(modelDocument);
				paSelectionLigneDocControlleur.actRefresh();
			}
		}catch (Exception e) {
				logger.error("",e);
			}
		finally{
			vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
		}
	}

	
	protected void reset() throws Exception {
		try{
			//récupérer la liste des documents associable au type de document à créer
			//ces documents ne doivent pas déjà avoir de relation dans rDocument par rapport au
			//type document à créer.			
			modelDocument.getListeObjet().clear();
			if(paSelectionLigneDocControlleur!=null){
				paSelectionLigneDocControlleur.setMasterModelDocument(modelDocument);
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
			IStatus s = new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");;
				if (nomChamp.equals(Const.C_CODE_TIERS)&& value!=null && value!=""){
					TaTiersDAO dao = new TaTiersDAO(getEm());					
					dao.getModeObjet().setMode(EnumModeObjet.C_MO_EDITION);
					TaTiers f = new TaTiers();
					PropertyUtils.setSimpleProperty(f, nomChamp, value);
						s = dao.validate(f,nomChamp,"FACTURE");
					if (s.getSeverity() != IStatus.ERROR) {
							if(!f.getCodeTiers().equals("")){
								vue.getTfTiers().setText(f.getCodeTiers());
							}else {
								vue.getTfTiers().setText(f.getCodeTiers());
							}

					}
					dao = null;
				} 
				if (nomChamp.equals(Const.C_DATE_DEBUT) ) {

				} 
				if (nomChamp.equals(Const.C_DATE_FIN) ) {

				} 

				
			if (s.getSeverity() != IStatus.ERROR) {
			}
						 new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
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
			selectedCritere = new IHMEnteteDocument();
			bindingFormSimple(dbc, realm, selectedCritere, IHMEnteteDocument.class);
			initEtatBouton(true);
			vue.getComboTypeDocSelection().select(0);
			selectionComboSelection();
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
		mapComposantChamps.put(vue.getTfTiers(), Const.C_CODE_TIERS);

		
		if (listeComposantFocusable == null) 
			listeComposantFocusable = new ArrayList<Control>();
		
		listeComposantFocusable.add(vue.getTfDateDebutPeriode());
		listeComposantFocusable.add(vue.getTfDateFinPeriode());
		listeComposantFocusable.add(vue.getTfTiers());
		listeComposantFocusable.add(vue.getBtnValiderParam());
		listeComposantFocusable.add(vue.getBtnValider());
		listeComposantFocusable.add(vue.getBtnFermer());
		
		if(mapInitFocus == null) 
			mapInitFocus = new LinkedHashMap<ModeObjet.EnumModeObjet,Control>();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION,vue.getTfTiers());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION,vue.getTfTiers());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION,vue.getTfTiers());

		activeModifytListener();
		KeyStroke keyStroke;

//			keyStroke = KeyStroke.getInstance("Ctrl+Space");
//
//		vue.getTfTiers().addFocusListener(new FocusAdapter() {
//			public void focusGained(FocusEvent e) {
//				tiersContentProposal
//				.setContentProposalProvider(contentProposalProviderTiers());
//			}
//		});
//		
//		tiersContentProposal = new ContentProposalAdapter(vue
//				.getTfTiers(), new TextContentAdapter(),
//				contentProposalProviderTiers(), keyStroke, null);
//		tiersContentProposal
//		.setFilterStyle(ContentProposalAdapter.FILTER_NONE);
//		tiersContentProposal
//		.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);
//		tiersContentProposal
//		.addContentProposalListener(new IContentProposalListener() {
//			public void proposalAccepted(IContentProposal proposal) {
//				try {
//					actRefresh();
//				} catch (Exception e) {
//					logger.error("", e);
//				}
//			}
//		});

		vue.getComboTypeDocSelection().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					selectionComboSelection();
					
					actRefresh();
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
					actRefresh();
				} catch (Exception e1) {
					logger.error("", e1);
				}
			}

		});		
		vue.getComboTypeDocSelection().select(0);
		selectionComboSelection();
		vue.getTfDateDebutPeriode().addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				try {
					if(vue.getTfDateDebutPeriode().isEnabled()){
						IStatus status=validateUIField(Const.C_DATE_DEBUT,vue.getTfDateDebutPeriode().getSelection());
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
						IStatus status=validateUIField(Const.C_DATE_FIN,vue.getTfDateFinPeriode().getSelection());
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
		mapComposantDecoratedField.put(vue.getTfTiers(), vue.getFieldTiers());
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
			else param.setFocusDefautSWT(vue.getTfTiers());

			if(param instanceof ParamAffichecreationDocMultiple){
				if(((ParamAffichecreationDocMultiple)param).getIdTiers()!=0) {
					TaTiers tiers=new TaTiersDAO(getEm()).findById(((ParamAffichecreationDocMultiple)param).getIdTiers());
					vue.getTfTiers().setText(tiers.getCodeTiers());
				};

				if(((ParamAffichecreationDocMultiple)param).getDateDeb()!=null){
					setDateDeb(((ParamAffichecreationDocMultiple)param).getDateDeb());
					vue.getTfDateDebutPeriode().setSelection(getDateDeb());
				}else{
					vue.getTfDateDebutPeriode().setSelection(infos.getDatedebInfoEntreprise());
				}
				if(((ParamAffichecreationDocMultiple)param).getDateFin()!=null){
					setDateFin(((ParamAffichecreationDocMultiple)param).getDateFin());
					vue.getTfDateFinPeriode().setSelection(getDateFin());
				}else{
					vue.getTfDateFinPeriode().setSelection(infos.getDatefinInfoEntreprise());
				}
				
			}
			vue.getComboTypeDocSelection().removeAll();
			vue.getComboTypeDocSelection().add(TypeDoc.TYPE_BON_LIVRAISON);
			vue.getComboTypeDocSelection().add(TypeDoc.TYPE_FACTURE);
			vue.getComboTypeDocSelection().select(0);
			
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
			if(focusDansEcran())ctrlUnChampsSWT(getFocusCourantSWT());
		} catch (Exception e) {
			logger.error("", e);
			vue.getTfTiers().forceFocus();
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
		boolean trouve = true;
		switch (daoStandard.getModeObjet().getMode()) {
		case C_MO_CONSULTATION:
			enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,true);
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
	
	
	public void retourEcran(final RetourEcranEvent evt) {
		if (evt.getRetour() != null
				&& (evt.getSource() instanceof SWTPaAideControllerSWT)) {
			if (getFocusAvantAideSWT() instanceof Text) {
				try {
					((Text) getFocusAvantAideSWT()).setText(((ResultAffiche) evt
							.getRetour()).getResult());		
					if(getFocusAvantAideSWT().equals(vue.getTfTiers())){
						TaTiers entity = null;
						TaTiersDAO dao = new TaTiersDAO(getEm());
						entity = dao.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
						dao = null;
						validateUIField(Const.C_CODE_TIERS,entity.getCodeTiers());
						vue.getTfTiers().setText(entity.getCodeTiers());
					}


					ctrlUnChampsSWT(getFocusAvantAideSWT());
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

	public PaCriterecreationDocMultipleController getThis() {
		return this;
	}

	public String getSelectedTypeSelection() {
		return selectedTypeSelection;
	}

	public void setSelectedTypeSelection(String selectedType) {
		this.selectedTypeSelection = selectedType;
	}

	public void selectionComboSelection(){
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
		if(!typeEntrant.equalsIgnoreCase(selectedTypeSelection)){
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
	}
	
	public void selectionComboCreation(){
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

		}

	}
	public String getSelectedTypeCreation() {
		return selectedTypeCreation;
	}

	public void setSelectedTypeCreation(String selectedTypeCreation) {
		this.selectedTypeCreation = selectedTypeCreation;
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
}

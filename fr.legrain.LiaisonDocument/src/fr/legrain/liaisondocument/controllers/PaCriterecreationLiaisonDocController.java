package fr.legrain.liaisondocument.controllers;

import java.lang.reflect.InvocationTargetException;
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
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.DecoratedField;
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

import fr.legrain.document.DocumentPlugin;
import fr.legrain.document.controller.PaVisuDocumentControlleur;
import fr.legrain.document.controller.PaVisuLiaisonDocumentControlleur;
import fr.legrain.document.controller.PaVisuReglementControlleur;
import fr.legrain.document.divers.ModelDocumentLiaisonDoc;
import fr.legrain.document.divers.TypeDoc;
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
import fr.legrain.documents.dao.TaPrelevement;
import fr.legrain.documents.dao.TaPrelevementDAO;
import fr.legrain.documents.dao.TaProforma;
import fr.legrain.documents.dao.TaProformaDAO;
import fr.legrain.documents.dao.TaRDocument;
import fr.legrain.documents.dao.TaRDocumentDAO;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.facture.FacturePlugin;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
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
import fr.legrain.liaisondocument.Activator;
import fr.legrain.liaisondocument.divers.ParamAffichecreationLiaisonDoc;
import fr.legrain.liaisondocument.ecrans.PaCriterecreationLiaisonDoc;
import fr.legrain.liaisondocument.ecrans.PaSelectionLigneDoc;
import fr.legrain.lib.data.AbstractLgrDAO;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.InfosVerifSaisie;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.data.VerrouInterface;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
import fr.legrain.lib.gui.DefaultFrameGrilleSansBouton;
import fr.legrain.lib.gui.ParamAffiche;
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

public class PaCriterecreationLiaisonDocController extends JPABaseControllerSWTStandard 
implements RetourEcranListener{

	static Logger logger = Logger.getLogger(PaCriterecreationLiaisonDocController.class.getName());	
	private PaCriterecreationLiaisonDoc  vue = null;
	private Date dateDeb=null;
	private Date dateFin=null;
//	private TaTiers tiers = null;
	
	private TaFactureDAO daoFacture=new TaFactureDAO(getEm());
	private TaAvoirDAO daoAvoir=new TaAvoirDAO(getEm());
	private TaBonlivDAO daoBonLiv=new TaBonlivDAO(getEm());
	private TaBoncdeDAO daoBoncde=new TaBoncdeDAO(getEm());
	private TaApporteurDAO daoApporteur=new TaApporteurDAO(getEm());
	private TaProformaDAO daoProforma=new TaProformaDAO(getEm());
	private TaDevisDAO daoDevis=new TaDevisDAO(getEm());
	private TaPrelevementDAO daoPrelevement=new TaPrelevementDAO(getEm());
	private TaAcompteDAO daoAcompte=new TaAcompteDAO(getEm());
	
	//private TaBonliv taBonLiv = null;
	
	TaInfoEntreprise infos =null;
	TaInfoEntrepriseDAO daoInfos=null;
	
	private Object ecranAppelant = null;
	private Realm realm;
	private DataBindingContext dbc;
	private String selectedTypeSelection = "";
	private String selectedTypeCreation = "";
	
	private MapChangeListener changeListener = new MapChangeListener();
	private ModelDocumentLiaisonDoc modelDocument = null;
	private ModelDocumentLiaisonDoc modelDocument2 = null;
	private PaSelectionLigneDocControlleur paSelectionLigneDocControlleur=null;
	private PaSelectionLigneDocControlleur paSelectionLigneDocControlleur2=null;

	private IHMEnteteDocument selectedCritere ;
	public PaCriterecreationLiaisonDocController(PaCriterecreationLiaisonDoc vue,EntityManager em) {
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
		
		modelDocument = new ModelDocumentLiaisonDoc(daoBonLiv,IHMEnteteDocument.class,selectedTypeSelection);
		modelDocument2 = new ModelDocumentLiaisonDoc(daoBonLiv,IHMEnteteDocument.class,selectedTypeCreation);
		setVue(vue);

		vue.getShell().addShellListener(this);

		//Branchement action annuler : empeche la fermeture automatique de la fenetre sur ESC
		vue.getShell().addTraverseListener(new Traverse());

		initController();
		initSashFormWeight();
		daoInfos=new TaInfoEntrepriseDAO(getEm());
		infos =daoInfos.findInstance();
	}

	public PaCriterecreationLiaisonDocController(PaCriterecreationLiaisonDoc vue) {
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
		PaSelectionLigneDoc selectionLigneRelance1 = new PaSelectionLigneDoc(vue.getExpandBarDocuments1(), SWT.PUSH,1,
				SWT.SINGLE|SWT.FULL_SELECTION
					| SWT.H_SCROLL
					| SWT.V_SCROLL
					| SWT.BORDER
					| SWT.CHECK);
		//PaSelectionLigneDoc selectionLigneRelance1 = new PaSelectionLigneDoc(vue.getExpandBarDocuments1(), SWT.PUSH);
		paSelectionLigneDocControlleur = new PaSelectionLigneDocControlleur(selectionLigneRelance1,getEm());
		ParamAffichecreationLiaisonDoc paramAfficheRelanceFacture =new ParamAffichecreationLiaisonDoc();
		paramAfficheRelanceFacture.setEnregistreDirect(true);
		paSelectionLigneDocControlleur.configPanel(paramAfficheRelanceFacture);
		
		addExpandBarItem(vue.getExpandBarDocuments1(), selectionLigneRelance1,
		"Sélectionnez les documents à prendre en compte dans la liaison ", Activator.getImageDescriptor(
		"/icons/legrain.gif").createImage(),  SWT.DEFAULT,500);
		
		vue.getExpandBarDocuments1().getItem(0).setExpanded(true);
		
		///deuxième expand
		PaSelectionLigneDoc selectionLigneRelance2 = new PaSelectionLigneDoc(vue.getExpandBarDocuments2(), SWT.PUSH,1,
				SWT.SINGLE|SWT.FULL_SELECTION
					| SWT.H_SCROLL
					| SWT.V_SCROLL
					| SWT.BORDER
					| SWT.CHECK);
		//PaSelectionLigneDoc selectionLigneRelance2 = new PaSelectionLigneDoc(vue.getExpandBarDocuments2(), SWT.PUSH);
		paSelectionLigneDocControlleur2 = new PaSelectionLigneDocControlleur(selectionLigneRelance2,getEm());
		paramAfficheRelanceFacture =new ParamAffichecreationLiaisonDoc();
		paramAfficheRelanceFacture.setEnregistreDirect(true);
		paSelectionLigneDocControlleur2.configPanel(paramAfficheRelanceFacture);
		
		addExpandBarItem(vue.getExpandBarDocuments2(), selectionLigneRelance2,
		"Sélectionnez les documents à prendre en compte dans la liaison ", Activator.getImageDescriptor(
		"/icons/legrain.gif").createImage(), SWT.DEFAULT, 500);
		vue.getExpandBarDocuments2().getItem(0).setExpanded(true);
		
	
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
						paramAfficheAideRecherche.setJPQLQuery(new TaTiersDAO(getEm()).getTiersActif());
						paramAfficheTiers.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheTiers.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaTiersController;
						parametreEcranCreation = paramAfficheTiers;

						paramAfficheAideRecherche.setTypeEntite(TaTiers.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_TIERS);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfTiers().getText());
						paramAfficheAideRecherche.setControllerAppelant(this);
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
		EntityTransaction transaction = null;
		try {

			String typeMaitre;
			String typeEsclave;
			List<IHMEnteteDocument> listeMaitre=new ArrayList<IHMEnteteDocument>(0);
			List<IHMEnteteDocument> listeEsclave=new ArrayList<IHMEnteteDocument>(0);
			List<IHMEnteteDocument> model1=new ArrayList<IHMEnteteDocument>(0);
			List<IHMEnteteDocument> model2=new ArrayList<IHMEnteteDocument>(0);
			for (Object doc : modelDocument.getListeObjet()) {
				if(((IHMEnteteDocument)doc).getAccepte())
					model1.add(((IHMEnteteDocument)doc));
			}
			for (Object doc : modelDocument2.getListeObjet()) {
				if(((IHMEnteteDocument)doc).getAccepte())
					model2.add(((IHMEnteteDocument)doc));
			}
			if(model1.size()>1 && model2.size()>1){
				MessageDialog.openWarning(vue.getShell(), "Liaison document", "Vous ne pouvez pas lier plusieurs documents avec plusieurs documents...");
			}else{

				if(model1.size()>model2.size()){
					listeMaitre.add(model2.get(0));
					listeEsclave=model1;
					typeMaitre=selectedTypeCreation;
					typeEsclave=selectedTypeSelection;
				}
				else {
					listeMaitre.add(model1.get(0));
					listeEsclave=model2;
					typeMaitre=selectedTypeSelection;
					typeEsclave=selectedTypeCreation;				
				}
				IDocumentTiers docMaitre = null ;
				
//				if(typeMaitre==TypeDoc.TYPE_ACOMPTE){
//					daoAcompte.setEm(getEm());
//					docMaitre=daoAcompte.findById(listeMaitre.get(0).getIdDocument());
//				}
//				if(typeMaitre==TypeDoc.TYPE_APPORTEUR){
//					daoApporteur.setEm(getEm());
//					docMaitre=daoApporteur.findById(listeMaitre.get(0).getIdDocument());
//				}
//				if(typeMaitre==TypeDoc.TYPE_AVOIR){
//					daoAvoir.setEm(getEm());
//					docMaitre=daoAvoir.findById(listeMaitre.get(0).getIdDocument());
//				}
//				if(typeMaitre==TypeDoc.TYPE_BON_COMMANDE){
//					daoBoncde.setEm(getEm());
//					docMaitre=daoBoncde.findById(listeMaitre.get(0).getIdDocument());
//				}
//				if(typeMaitre==TypeDoc.TYPE_BON_LIVRAISON){
//					daoBonLiv.setEm(getEm());
//					docMaitre=daoBonLiv.findById(listeMaitre.get(0).getIdDocument());
//					
//				}
//				if(typeMaitre==TypeDoc.TYPE_DEVIS){
//					daoDevis.setEm(getEm());
//					docMaitre=daoDevis.findById(listeMaitre.get(0).getIdDocument());
//				}
//				if(typeMaitre==TypeDoc.TYPE_FACTURE){
//					daoFacture.setEm(getEm());
//					docMaitre=daoFacture.findById(listeMaitre.get(0).getIdDocument());
//				}
//				if(typeMaitre==TypeDoc.TYPE_PRELEVEMENT){
//					daoPrelevement.setEm(getEm());
//					docMaitre=daoPrelevement.findById(listeMaitre.get(0).getIdDocument());
//				}
//				if(typeMaitre==TypeDoc.TYPE_PROFORMA){
//					daoProforma.setEm(getEm());
//					docMaitre=daoProforma.findById(listeMaitre.get(0).getIdDocument());
//				}
				for (IHMEnteteDocument doc : listeEsclave) {
					//traitement du maître
					TaRDocument rDocument = new TaRDocument();
					TaRDocumentDAO daoRDocument = new TaRDocumentDAO(getEm());

					transaction = daoRDocument.getEntityManager().getTransaction();
					daoRDocument.begin(transaction);
					


					if(typeMaitre==TypeDoc.TYPE_FACTURE){
						daoFacture.setEm(getEm());
						docMaitre=daoFacture.findById(listeMaitre.get(0).getIdDocument());
						rDocument.setTaFacture((TaFacture)docMaitre);
						rDocument=initRDocumentEsclave(docMaitre,rDocument,typeEsclave,doc);
						if(rDocument==null)MessageDialog.openWarning(vue.getShell(),"Liaison existante","La liaison entre le document "+
								docMaitre.getCodeDocument()+" et le document "+doc.getCodeDocument()+" existe déjà.");
						else{
							docMaitre.getTaRDocuments().add(rDocument);
							docMaitre=daoFacture.enregistrerMerge((TaFacture)docMaitre);
						}
					}
					if(typeMaitre==TypeDoc.TYPE_DEVIS){	
						daoDevis.setEm(getEm());
						docMaitre=daoDevis.findById(listeMaitre.get(0).getIdDocument());
						rDocument.setTaDevis((TaDevis)docMaitre);
						rDocument=initRDocumentEsclave(docMaitre,rDocument,typeEsclave,doc);
						if(rDocument==null)MessageDialog.openWarning(vue.getShell(),"Liaison existante","La liaison entre le document "+
								docMaitre.getCodeDocument()+" et le document "+doc.getCodeDocument()+" existe déjà.");
						else{
							docMaitre.getTaRDocuments().add(rDocument);
							docMaitre=daoDevis.enregistrerMerge((TaDevis)docMaitre);
						}
					}
					if(typeMaitre==TypeDoc.TYPE_ACOMPTE){
						daoAcompte.setEm(getEm());
						docMaitre=daoAcompte.findById(listeMaitre.get(0).getIdDocument());
						rDocument.setTaAcompte((TaAcompte)docMaitre);
						rDocument=initRDocumentEsclave(docMaitre,rDocument,typeEsclave,doc);
						if(rDocument==null)MessageDialog.openWarning(vue.getShell(),"Liaison existante","La liaison entre le document "+
								docMaitre.getCodeDocument()+" et le document "+doc.getCodeDocument()+" existe déjà.");
						else{
							docMaitre.getTaRDocuments().add(rDocument);
							docMaitre=daoAcompte.enregistrerMerge((TaAcompte)docMaitre);
						}
					}
					if(typeMaitre==TypeDoc.TYPE_APPORTEUR){
						daoApporteur.setEm(getEm());
						docMaitre=daoApporteur.findById(listeMaitre.get(0).getIdDocument());
						rDocument.setTaApporteur((TaApporteur)docMaitre);
						rDocument=initRDocumentEsclave(docMaitre,rDocument,typeEsclave,doc);
						if(rDocument==null)MessageDialog.openWarning(vue.getShell(),"Liaison existante","La liaison entre le document "+
								docMaitre.getCodeDocument()+" et le document "+doc.getCodeDocument()+" existe déjà.");
						else{
							docMaitre.getTaRDocuments().add(rDocument);
							docMaitre=daoApporteur.enregistrerMerge((TaApporteur)docMaitre);
						}
					}
					if(typeMaitre==TypeDoc.TYPE_AVOIR){	
						daoAvoir.setEm(getEm());
						docMaitre=daoAvoir.findById(listeMaitre.get(0).getIdDocument());
						rDocument.setTaAvoir((TaAvoir)docMaitre);
						rDocument=initRDocumentEsclave(docMaitre,rDocument,typeEsclave,doc);
						if(rDocument==null)MessageDialog.openWarning(vue.getShell(),"Liaison existante","La liaison entre le document "+
								docMaitre.getCodeDocument()+" et le document "+doc.getCodeDocument()+" existe déjà.");
						else{
							docMaitre.getTaRDocuments().add(rDocument);
							docMaitre=daoAvoir.enregistrerMerge((TaAvoir)docMaitre);
						}
					}
					if(typeMaitre==TypeDoc.TYPE_BON_COMMANDE){
						daoBoncde.setEm(getEm());
						docMaitre=daoBoncde.findById(listeMaitre.get(0).getIdDocument());
						rDocument.setTaBoncde((TaBoncde)docMaitre);
						rDocument=initRDocumentEsclave(docMaitre,rDocument,typeEsclave,doc);
						if(rDocument==null)MessageDialog.openWarning(vue.getShell(),"Liaison existante","La liaison entre le document "+
								docMaitre.getCodeDocument()+" et le document "+doc.getCodeDocument()+" existe déjà.");
						else{
							docMaitre.getTaRDocuments().add(rDocument);
							docMaitre=daoBoncde.enregistrerMerge((TaBoncde)docMaitre);
						}
					}
					if(typeMaitre==TypeDoc.TYPE_BON_LIVRAISON){
						daoBonLiv.setEm(getEm());
						docMaitre=daoBonLiv.findById(listeMaitre.get(0).getIdDocument());
						rDocument.setTaBonliv((TaBonliv)docMaitre);
						rDocument=initRDocumentEsclave(docMaitre,rDocument,typeEsclave,doc);
						if(rDocument==null)MessageDialog.openWarning(vue.getShell(),"Liaison existante","La liaison entre le document "+
								docMaitre.getCodeDocument()+" et le document "+doc.getCodeDocument()+" existe déjà.");
						else{
							docMaitre.getTaRDocuments().add(rDocument);
							docMaitre=daoBonLiv.enregistrerMerge((TaBonliv)docMaitre);
						}
					}
					if(typeMaitre==TypeDoc.TYPE_PRELEVEMENT){
						daoPrelevement.setEm(getEm());
						docMaitre=daoPrelevement.findById(listeMaitre.get(0).getIdDocument());
						rDocument.setTaPrelevement((TaPrelevement)docMaitre);
						rDocument=initRDocumentEsclave(docMaitre,rDocument,typeEsclave,doc);
						if(rDocument==null)MessageDialog.openWarning(vue.getShell(),"Liaison existante","La liaison entre le document "+
								docMaitre.getCodeDocument()+" et le document "+doc.getCodeDocument()+" existe déjà.");
						else{
							docMaitre.getTaRDocuments().add(rDocument);
							docMaitre=daoPrelevement.enregistrerMerge((TaPrelevement)docMaitre);
						}
					}
					if(typeMaitre==TypeDoc.TYPE_PROFORMA){
						daoProforma.setEm(getEm());
						docMaitre=daoProforma.findById(listeMaitre.get(0).getIdDocument());
						rDocument.setTaProforma((TaProforma)docMaitre);
						rDocument=initRDocumentEsclave(docMaitre,rDocument,typeEsclave,doc);
						if(rDocument==null)MessageDialog.openWarning(vue.getShell(),"Liaison existante","La liaison entre le document "+
								docMaitre.getCodeDocument()+" et le document "+doc.getCodeDocument()+" existe déjà.");
						else{
							docMaitre.getTaRDocuments().add(rDocument);
							docMaitre=daoProforma.enregistrerMerge((TaProforma)docMaitre);
						}
					}
					daoRDocument.commit(transaction);
					//daoRDocument.refresh(rDocument);
				}

				getEm().clear();
				actRefresh();
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
	
	private Boolean verifExistenceRDocument(IDocumentTiers docMaitre,IDocumentTiers docEsclave){
		
		for (TaRDocument rDoc : docMaitre.getTaRDocuments()) {
			if(docEsclave.getTypeDocument()==TypeDoc.TYPE_FACTURE){
				if(rDoc.getTaFacture()!=null&&
						rDoc.getTaFacture().getCodeDocument().equals(docEsclave.getCodeDocument()))return true;
			}
		}
		for (TaRDocument rDoc : docMaitre.getTaRDocuments()) {
			if(docEsclave.getTypeDocument()==TypeDoc.TYPE_ACOMPTE){
				if(rDoc.getTaAcompte()!=null&&
						rDoc.getTaAcompte().getCodeDocument().equals(docEsclave.getCodeDocument()))return true;
			}
		}
		for (TaRDocument rDoc : docMaitre.getTaRDocuments()) {
			if(docEsclave.getTypeDocument()==TypeDoc.TYPE_APPORTEUR){
				if(rDoc.getTaApporteur()!=null&&
						rDoc.getTaApporteur().getCodeDocument().equals(docEsclave.getCodeDocument()))return true;
			}
		}
		for (TaRDocument rDoc : docMaitre.getTaRDocuments()) {
			if(docEsclave.getTypeDocument()==TypeDoc.TYPE_AVOIR){
				if(rDoc.getTaAvoir()!=null&&
						rDoc.getTaAvoir().getCodeDocument().equals(docEsclave.getCodeDocument()))return true;
			}
		}
		for (TaRDocument rDoc : docMaitre.getTaRDocuments()) {
			if(docEsclave.getTypeDocument()==TypeDoc.TYPE_BON_COMMANDE){
				if(rDoc.getTaBoncde()!=null&&
						rDoc.getTaBoncde().getCodeDocument().equals(docEsclave.getCodeDocument()))return true;
			}
		}
		for (TaRDocument rDoc : docMaitre.getTaRDocuments()) {
			if(docEsclave.getTypeDocument()==TypeDoc.TYPE_BON_LIVRAISON){
				if(rDoc.getTaBonliv()!=null&&
						rDoc.getTaBonliv().getCodeDocument().equals(docEsclave.getCodeDocument()))return true;
			}
		}
		for (TaRDocument rDoc : docMaitre.getTaRDocuments()) {
			if(docEsclave.getTypeDocument()==TypeDoc.TYPE_DEVIS){
				if(rDoc.getTaDevis()!=null&&
						rDoc.getTaDevis().getCodeDocument().equals(docEsclave.getCodeDocument()))return true;
			}
		}
		for (TaRDocument rDoc : docMaitre.getTaRDocuments()) {
			if(docEsclave.getTypeDocument()==TypeDoc.TYPE_PRELEVEMENT){
				if(rDoc.getTaPrelevement()!=null&&
						rDoc.getTaPrelevement().getCodeDocument().equals(docEsclave.getCodeDocument()))return true;
			}
		}
		for (TaRDocument rDoc : docMaitre.getTaRDocuments()) {
			if(docEsclave.getTypeDocument()==TypeDoc.TYPE_PROFORMA){
				if(rDoc.getTaProforma()!=null&&
						rDoc.getTaProforma().getCodeDocument().equals(docEsclave.getCodeDocument()))return true;
			}
		}
		return false;
	}
	
	private TaRDocument initRDocumentEsclave(IDocumentTiers docMaitre ,TaRDocument rDocument,String typeEsclave,IHMEnteteDocument doc){
		//traitement de l'esclave
		if(typeEsclave==TypeDoc.TYPE_FACTURE){					
			rDocument.setTaFacture(daoFacture.findById(doc.getIdDocument()));
			if(verifExistenceRDocument(docMaitre,rDocument.getTaFacture()))return null;				
		}
		if(typeEsclave==TypeDoc.TYPE_DEVIS){					
			rDocument.setTaDevis(daoDevis.findById(doc.getIdDocument()));
			if(verifExistenceRDocument(docMaitre,rDocument.getTaDevis()))return null;	
		}
		if(typeEsclave==TypeDoc.TYPE_ACOMPTE){					
			rDocument.setTaAcompte(daoAcompte.findById(doc.getIdDocument()));
			if(verifExistenceRDocument(docMaitre,rDocument.getTaAcompte()))return null;	
		}
		if(typeEsclave==TypeDoc.TYPE_APPORTEUR){					
			rDocument.setTaApporteur(daoApporteur.findById(doc.getIdDocument()));
			if(verifExistenceRDocument(docMaitre,rDocument.getTaApporteur()))return null;	
		}
		if(typeEsclave==TypeDoc.TYPE_AVOIR){					
			rDocument.setTaAvoir(daoAvoir.findById(doc.getIdDocument()));
			if(verifExistenceRDocument(docMaitre,rDocument.getTaAvoir()))return null;	
		}
		if(typeEsclave==TypeDoc.TYPE_BON_COMMANDE){					
			rDocument.setTaBoncde(daoBoncde.findById(doc.getIdDocument()));
			if(verifExistenceRDocument(docMaitre,rDocument.getTaBoncde()))return null;	
		}
		if(typeEsclave==TypeDoc.TYPE_BON_LIVRAISON){					
			rDocument.setTaBonliv(daoBonLiv.findById(doc.getIdDocument()));
			if(verifExistenceRDocument(docMaitre,rDocument.getTaBonliv()))return null;	
		}
		if(typeEsclave==TypeDoc.TYPE_PRELEVEMENT){					
			rDocument.setTaPrelevement(daoPrelevement.findById(doc.getIdDocument()));
			if(verifExistenceRDocument(docMaitre,rDocument.getTaPrelevement()))return null;	
		}
		if(typeEsclave==TypeDoc.TYPE_PROFORMA){					
			rDocument.setTaProforma(daoProforma.findById(doc.getIdDocument()));
			if(verifExistenceRDocument(docMaitre,rDocument.getTaProforma()))return null;	
		}
		return rDocument;
	}
	
	
	@Override
	protected void actFermer() throws Exception {
		// TODO Auto-generated method stub
		if(onClose())
			closeWorkbenchPart();
	}

	@Override
	protected void actImprimer() throws Exception {
//		//récupérer la liste des documents associable au type de document à créer
//		//ces documents ne doivent pas déjà avoir de relation dans rDocument par rapport au
//		//type document à créer.
//		Boolean accepte=true;
//		modelDocument.getListeObjet().clear();
//		List<TaBonliv> listeDocumentsFinal =	new ArrayList<TaBonliv>(0);
//		List<TaBonliv> listeDocuments =	daoBonLiv.findByCodeTiersAndDate(vue.getTfTiers().getText(),vue.getTfDateDebutPeriode().getSelection(),
//				  vue.getTfDateFinPeriode().getSelection());
//		for (TaBonliv taBonliv : listeDocuments) {
//			for (TaRDocument relation : taBonliv.getTaRDocuments()) {
//				if(relation.getTaFacture()!=null){
//					accepte=false;
//					break;
//					}
//			}
//			if(accepte)listeDocumentsFinal.add(taBonliv);
//		}
//		LinkedList<IHMEnteteBonliv> lswt = new LinkedList<IHMEnteteBonliv>();
//		
//
//			LgrDozerMapper<TaBonliv,IHMEnteteBonliv> mapper = new LgrDozerMapper<TaBonliv,IHMEnteteBonliv>();
//			for (TaBonliv o : listeDocumentsFinal) {
//				IHMEnteteBonliv t = null;
//				t = (IHMEnteteBonliv) mapper.map(o, IHMEnteteBonliv.class);
//				lswt.add(t);
//			}
//		if(modelDocument==null)modelDocument=new ModelGeneralObjet<IHMEnteteBonliv,TaBonlivDAO,TaBonliv>
//		(daoBonLiv,IHMEnteteBonliv.class);
//		modelDocument.setListeEntity(listeDocumentsFinal);
//		modelDocument.getListeObjet().addAll(lswt);
//		actRefresh();
	}

	
//	@Override
//	protected void actImprimer() throws Exception {
//		Date deb = new Date();
//		List<TaFacture> listeFacture =new LinkedList<TaFacture>();
//		TaTRelanceDAO daoTRelance = new TaTRelanceDAO(getEm());
//		List<Object[]> listeRelance=daoFacture.rechercheDocumentNonTotalementRegleAEcheance2(
//				vue.getTfDateDebutPeriode().getSelection(),vue.getTfDateFinPeriode().getSelection(),
//				vue.getTfTiers().getText(),null);
//		//		List<TaFacture> listeFacture =daoFacture.rechercheDocumentNonTotalementRegleAEcheance(
//		//				vue.getTfDateDebutPeriode().getSelection(),vue.getTfDateFinPeriode().getSelection(),
//		//				vue.getTfTiers().getText(),null);
//		taRelance=new TaRelance();
//		Date dateRelance=new Date();
//		taRelance.setCodeRelance("Relance du "+LibDate.dateToString(dateRelance)+" à "+
//				LibDate.getHeure(dateRelance)+" h"+LibDate.getMinute(dateRelance)
//				+" mn"+LibDate.getSeconde(dateRelance)+" s");
//		taRelance.setDateDebut(vue.getTfDateDebutPeriode().getSelection());
//		taRelance.setDateFin(vue.getTfDateFinPeriode().getSelection());
//		taRelance.setCodeTiers(vue.getTfTiers().getText());
//		taRelance.setDateRelance(dateRelance);
//		for (Object[] taRelanceDoc : listeRelance) {
//			TaTRelance taTRelance =daoTRelance.findById((Integer)taRelanceDoc[1]);
//			TaFacture taFacture = daoFacture.findById((Integer)taRelanceDoc[0]);
//			if(taFacture.calculRegleDocumentComplet().add(taFacture.getAcomptes()).
//					compareTo(taFacture.getNetTtcCalc())<0){
//				if(taTRelance!=null){
//					TaLRelance r =new TaLRelance();
//					r.setTypeDocument(TypeDoc.TYPE_FACTURE);
//					r.setCodeDocument(taFacture.getCodeDocument());
//					r.setCodeTiers(taFacture.getTaTiers().getCodeTiers());
//					r.setNomTiers(taFacture.getTaTiers().getNomTiers());
//					r.setDateEcheance(taFacture.getDateEchDocument());
//					r.setNetTTC(taFacture.getNetTtcCalc());
//					r.setResteARegler(taFacture.getResteAReglerComplet());
//					r.setTaRelance(taRelance);
//					r.setTaTRelance(taTRelance);
//					r.setAccepte(true);
//					taRelance.getTaLRelances().add(r);
//				}
//			}
//		}
//		vue.getTfCodeRelance().setText(taRelance.getCodeRelance());
//		List<TaRelance> liste =new LinkedList<TaRelance>();
//		liste.add(taRelance);
//		modelRelance.setListeEntity(liste);
////		logger.info("***** intermediaire : "+new Date().toString());
//		
//		actRefresh();
//		Date fin = new Date();
//		Long duree=fin.getTime()-deb.getTime();
//		logger.info("***** durée : "+duree );
//	}

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

			

			selectionComboSelection();

			modelDocument.remplirListe(vue.getTfDateDebutPeriode().getSelection(),
					 vue.getTfDateFinPeriode().getSelection(),vue.getTfTiers().getText(),
					 selectedTypeSelection,selectedTypeCreation,vue.getBtnLies().getSelection(),getEm());

			if(paSelectionLigneDocControlleur!=null){
				paSelectionLigneDocControlleur.setSelectedType(selectedTypeSelection);
				paSelectionLigneDocControlleur.setSelectedTypeAdvers(selectedTypeCreation);
				paSelectionLigneDocControlleur.setMasterModelDocument(modelDocument);
				paSelectionLigneDocControlleur.actRefresh();
			}
			
			//récupérer la liste des documents associable au type de document à créer
			//ces documents ne doivent pas déjà avoir de relation dans rDocument par rapport au
			//type document à créer.			

			selectionComboCreation();
			modelDocument2.remplirListe(vue.getTfDateDebutPeriode().getSelection(),
					 vue.getTfDateFinPeriode().getSelection(),vue.getTfTiers().getText(),
					 selectedTypeCreation,selectedTypeSelection,vue.getBtnLie2().getSelection(),getEm());

			if(paSelectionLigneDocControlleur2!=null){
				paSelectionLigneDocControlleur2.setSelectedType(selectedTypeCreation);
				paSelectionLigneDocControlleur2.setSelectedTypeAdvers(selectedTypeSelection);
				paSelectionLigneDocControlleur2.setMasterModelDocument(modelDocument2);
				paSelectionLigneDocControlleur2.actRefresh();
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
			//récupérer la liste des documents associable au type de document à créer
			//ces documents ne doivent pas déjà avoir de relation dans rDocument par rapport au
			//type document à créer.			
			modelDocument2.getListeObjet().clear();
			if(paSelectionLigneDocControlleur2!=null){
				paSelectionLigneDocControlleur2.setMasterModelDocument(modelDocument2);
				paSelectionLigneDocControlleur2.actRefresh();
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
			//			// new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
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
		paSelectionLigneDocControlleur.setEm(getEm());
		paSelectionLigneDocControlleur2.setEm(getEm());
		paSelectionLigneDocControlleur.getPaVisuDocumentControlleur().setEm(getEm());
		paSelectionLigneDocControlleur2.getPaVisuDocumentControlleur().setEm(getEm());
		//actRefresh();
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
			vue.getComboTypeDocuments1().select(0);
			selectionComboSelection();
		} catch(Exception e) {
			logger.error("",e);
			vue.getLaMessage().setText(e.getMessage());
		}
	}

	@Override
	protected void initMapComposantChamps() {
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
		//vue.getBtnValiderParam().setVisible(false);
		
		vue.getComboTypeDocuments1().addSelectionListener(new SelectionAdapter() {
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
		vue.getComboTypeDocuments2().addSelectionListener(new SelectionAdapter() {
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
		
		vue.getBtnLies().addSelectionListener(new SelectionAdapter() {
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
		vue.getBtnLie2().addSelectionListener(new SelectionAdapter() {
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
		
		vue.getComboTypeDocuments1().select(0);
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

			if(param instanceof ParamAffichecreationLiaisonDoc){
				if(((ParamAffichecreationLiaisonDoc)param).getIdTiers()!=0) {
					TaTiers tiers=new TaTiersDAO(getEm()).findById(((ParamAffichecreationLiaisonDoc)param).getIdTiers());
					vue.getTfTiers().setText(tiers.getCodeTiers());
				};

				if(((ParamAffichecreationLiaisonDoc)param).getDateDeb()!=null){
					setDateDeb(((ParamAffichecreationLiaisonDoc)param).getDateDeb());
					vue.getTfDateDebutPeriode().setSelection(getDateDeb());
				}else{
					vue.getTfDateDebutPeriode().setSelection(infos.getDatedebInfoEntreprise());
				}
				if(((ParamAffichecreationLiaisonDoc)param).getDateFin()!=null){
					setDateFin(((ParamAffichecreationLiaisonDoc)param).getDateFin());
					vue.getTfDateFinPeriode().setSelection(getDateFin());
				}else{
					vue.getTfDateFinPeriode().setSelection(infos.getDatefinInfoEntreprise());
				}
				
			}
			vue.getComboTypeDocuments1().removeAll();
			String[] liste= new String[TypeDoc.getInstance().getTypeDocCompletPresent().size()];
			int i = 0;
			for (String libelle : TypeDoc.getInstance().getTypeDocCompletPresent().values()) {
				liste[i]=libelle;
				i++;
			}
			vue.getComboTypeDocuments1().setItems(liste);
			vue.getComboTypeDocuments1().select(0);
			vue.getComboTypeDocuments1().setVisibleItemCount(vue.getComboTypeDocuments1().getItemCount());

			
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
//			if(focusDansEcran())
//				ctrlUnChampsSWT(getFocusCourantSWT());
		} catch (Exception e) {
			logger.error("", e);
//			setFocusCourantSWT(vue.getTfTiers());
//			vue.getTfTiers().forceFocus();
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



	public void setVue(PaCriterecreationLiaisonDoc vue) {
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

	public PaCriterecreationLiaisonDocController getThis() {
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
		if(vue.getComboTypeDocuments1().getSelectionIndex()!=-1){
		if(vue.getComboTypeDocuments1().getItem(vue.getComboTypeDocuments1().getSelectionIndex()).equals(TypeDoc.TYPE_ACOMPTE)){
			selectedTypeSelection=TypeDoc.TYPE_ACOMPTE;
		}
		if(vue.getComboTypeDocuments1().getItem(vue.getComboTypeDocuments1().getSelectionIndex()).equals(TypeDoc.TYPE_APPORTEUR)){
			selectedTypeSelection=TypeDoc.TYPE_APPORTEUR;
		}
		if(vue.getComboTypeDocuments1().getItem(vue.getComboTypeDocuments1().getSelectionIndex()).equals(TypeDoc.TYPE_AVOIR)){
			selectedTypeSelection=TypeDoc.TYPE_AVOIR;
		}
		if(vue.getComboTypeDocuments1().getItem(vue.getComboTypeDocuments1().getSelectionIndex()).equals(TypeDoc.TYPE_BON_COMMANDE)){
			selectedTypeSelection=TypeDoc.TYPE_BON_COMMANDE;
		}
		if(vue.getComboTypeDocuments1().getItem(vue.getComboTypeDocuments1().getSelectionIndex()).equals(TypeDoc.TYPE_BON_LIVRAISON)){
			selectedTypeSelection=TypeDoc.TYPE_BON_LIVRAISON;
		}
		if(vue.getComboTypeDocuments1().getItem(vue.getComboTypeDocuments1().getSelectionIndex()).equals(TypeDoc.TYPE_DEVIS)){
			selectedTypeSelection=TypeDoc.TYPE_DEVIS;
		}
		if(vue.getComboTypeDocuments1().getItem(vue.getComboTypeDocuments1().getSelectionIndex()).equals(TypeDoc.TYPE_FACTURE)){
			selectedTypeSelection=TypeDoc.TYPE_FACTURE;
		}
		if(vue.getComboTypeDocuments1().getItem(vue.getComboTypeDocuments1().getSelectionIndex()).equals(TypeDoc.TYPE_PRELEVEMENT)){
			selectedTypeSelection=TypeDoc.TYPE_PRELEVEMENT;
		}
		if(vue.getComboTypeDocuments1().getItem(vue.getComboTypeDocuments1().getSelectionIndex()).equals(TypeDoc.TYPE_PROFORMA)){
			selectedTypeSelection=TypeDoc.TYPE_PROFORMA;
		}

		}else selectedTypeSelection="";
		if(!typeEntrant.equalsIgnoreCase(selectedTypeSelection)){
			vue.getComboTypeDocuments2().removeAll();
			for (int i = 0; i < TypeDoc.getInstance().getEditorDocPossibleCreationDocument().size(); i++) {
				if(TypeDoc.getInstance().getEditorDocPossibleCreationDocument().get(i)[0].equalsIgnoreCase(selectedTypeSelection)){
					vue.getComboTypeDocuments2().add(TypeDoc.getInstance().getEditorDocPossibleCreationDocument().get(i)[1]);
				}
				vue.getComboTypeDocuments2().select(0);
			}		
		}
		selectionComboCreation();
	}
	
	public void selectionComboCreation(){
		if(vue.getComboTypeDocuments2().getSelectionIndex()!=-1){
			if(vue.getComboTypeDocuments2().getItem(vue.getComboTypeDocuments2().getSelectionIndex()).equals(TypeDoc.TYPE_ACOMPTE)){
				selectedTypeCreation=TypeDoc.TYPE_ACOMPTE;
			}
			if(vue.getComboTypeDocuments2().getItem(vue.getComboTypeDocuments2().getSelectionIndex()).equals(TypeDoc.TYPE_APPORTEUR)){
				selectedTypeCreation=TypeDoc.TYPE_APPORTEUR;
			}
			if(vue.getComboTypeDocuments2().getItem(vue.getComboTypeDocuments2().getSelectionIndex()).equals(TypeDoc.TYPE_AVOIR)){
				selectedTypeCreation=TypeDoc.TYPE_AVOIR;
			}
			if(vue.getComboTypeDocuments2().getItem(vue.getComboTypeDocuments2().getSelectionIndex()).equals(TypeDoc.TYPE_BON_COMMANDE)){
				selectedTypeCreation=TypeDoc.TYPE_BON_COMMANDE;
			}
			if(vue.getComboTypeDocuments2().getItem(vue.getComboTypeDocuments2().getSelectionIndex()).equals(TypeDoc.TYPE_BON_LIVRAISON)){
				selectedTypeCreation=TypeDoc.TYPE_BON_LIVRAISON;
			}
			if(vue.getComboTypeDocuments2().getItem(vue.getComboTypeDocuments2().getSelectionIndex()).equals(TypeDoc.TYPE_DEVIS)){
				selectedTypeCreation=TypeDoc.TYPE_DEVIS;
			}
			if(vue.getComboTypeDocuments2().getItem(vue.getComboTypeDocuments2().getSelectionIndex()).equals(TypeDoc.TYPE_FACTURE)){
				selectedTypeCreation=TypeDoc.TYPE_FACTURE;
			}
			if(vue.getComboTypeDocuments2().getItem(vue.getComboTypeDocuments2().getSelectionIndex()).equals(TypeDoc.TYPE_PRELEVEMENT)){
				selectedTypeCreation=TypeDoc.TYPE_PRELEVEMENT;
			}
			if(vue.getComboTypeDocuments2().getItem(vue.getComboTypeDocuments2().getSelectionIndex()).equals(TypeDoc.TYPE_PROFORMA)){
				selectedTypeCreation=TypeDoc.TYPE_PROFORMA;
			}

		}else selectedTypeCreation="";

	}
	public String getSelectedTypeCreation() {
		return selectedTypeCreation;
	}

	public void setSelectedTypeCreation(String selectedTypeCreation) {
		this.selectedTypeCreation = selectedTypeCreation;
	}

	public PaSelectionLigneDocControlleur getPaSelectionLigneDocControlleur2() {
		return paSelectionLigneDocControlleur2;
	}

	public void setPaSelectionLigneDocControlleur2(
			PaSelectionLigneDocControlleur paSelectionLigneDocControlleur2) {
		this.paSelectionLigneDocControlleur2 = paSelectionLigneDocControlleur2;
	}
}

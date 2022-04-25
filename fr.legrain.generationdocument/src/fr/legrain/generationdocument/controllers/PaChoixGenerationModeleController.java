package fr.legrain.generationdocument.controllers;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ActiveShellExpression;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.handlers.IHandlerService;

import fr.legrain.document.divers.TypeDoc;
import fr.legrain.documents.dao.TaApporteur;
import fr.legrain.documents.dao.TaAvoir;
import fr.legrain.documents.dao.TaBoncde;
import fr.legrain.documents.dao.TaBonliv;
import fr.legrain.documents.dao.TaDevis;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaPrelevement;
import fr.legrain.documents.dao.TaProforma;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.generationdocument.Activator;
import fr.legrain.generationdocument.divers.AbstractGenereDoc;
import fr.legrain.generationdocument.divers.GenereDocFactory;
import fr.legrain.generationdocument.divers.ParamAfficheChoixGenerationDocument;
import fr.legrain.generationdocument.ecran.PaChoixGenerationDocument;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.SWTDocument;
import fr.legrain.gestCom.Module_Tiers.SWTTiers;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.gestCom.librairiesEcran.swt.LibDateTime;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideRechercheSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideRechercheControllerSWT;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.LibChaine;
import fr.legrain.lib.data.LibDate;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.data.VerrouInterface;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.lib.gui.aide.PaAideRechercheSWT;
import fr.legrain.lib.gui.aide.PaAideSWT;
import fr.legrain.lib.gui.aide.ParamAfficheAide;
import fr.legrain.lib.gui.aide.ResultAide;
import fr.legrain.tiers.dao.TaCPaiement;
import fr.legrain.tiers.dao.TaCPaiementDAO;
import fr.legrain.tiers.dao.TaTCPaiement;
import fr.legrain.tiers.dao.TaTCPaiementDAO;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.ecran.PaTiersSWT;
import fr.legrain.tiers.ecran.ParamAfficheTiers;
import fr.legrain.tiers.ecran.SWTPaTiersController;
import fr.legrain.tiers.editor.EditorInputTiers;
import fr.legrain.tiers.editor.TiersMultiPageEditor;



public class PaChoixGenerationModeleController extends JPABaseControllerSWTStandard implements
RetourEcranListener {

	static Logger logger = Logger.getLogger(PaChoixGenerationModeleController.class.getName());
	
	private static final int HAUTEUR_BOUTON=25;
	private PaChoixGenerationDocument vue = null; // vue

	private Object ecranAppelant = null;

	
	public static final int TYPE_AVOIR = 1;
	public static final int TYPE_DEVIS = 2;
	public static final int TYPE_FACTURE = 3;
	public static final int TYPE_PROFORMA = 4;
	public static final int TYPE_APPORTEUR = 5;
	public static final int TYPE_BON_COMMANDE = 6;
	public static final int TYPE_BON_LIVRAISON = 7;
	public static final String TITRE = "Génération de modèle.";
	
	public static final int HAUTEUR = 300;
	public static final int LARGEUR = 500;
	public  int hauteur = HAUTEUR;
	
	private String typeSrc = "";
	private String typeDest = "";
	private IDocumentTiers documentSrc = null;
	private LinkedHashMap<String, Button> boutonChoix = new LinkedHashMap<String, Button>();

	private String selectedType = "";
	private String nouveauLibelle = null;
	private Date dateDocument = null;
	private Date dateLivDocument = null;
	
	private TaTiersDAO daoTiers=null;


	private ParamAfficheChoixGenerationDocument result;
	
	public static final String C_COMMAND_GENERATIONDOCUMENT_REMONTERDOC_ID = "fr.legrain.generationdocument.RemonterDoc";
	protected HandlerRemonterDoc	 handlerRemonterDoc = new HandlerRemonterDoc	();

	public static final String C_COMMAND_GENERATIONDOCUMENT_IMPRIMERDOC_ID = "fr.legrain.generationdocument.ImprimerDoc";
	protected HandlerImprimerDoc	 handlerImprimerDoc = new HandlerImprimerDoc	();

	public PaChoixGenerationModeleController(PaChoixGenerationDocument vue) {
		try {
			setVue(vue);

			this.vue=vue;
			vue.getShell().addShellListener(this);
			//			Branchement action annuler : empeche la fermeture automatique de la
			// fenetre sur ESC
			vue.getShell().addTraverseListener(new Traverse());
			actionImprimer.setText("Valider[F11]");

			initController();
		} catch (Exception e) {
			logger.error("Erreur ", e);
		}

	}

	private void modif(TypedEvent e) {

	}
	private void initController() {
		try {		
			daoTiers=new TaTiersDAO(getEm());
			initMapComposantChamps();
			initMapComposantDecoratedField();
			listeComponentFocusableSWT(listeComposantFocusable);
			initFocusOrder();
			initActions();
			initDeplacementSaisie(listeComposantFocusable);

			branchementBouton();

			Menu popupMenuFormulaire = new Menu(vue.getShell(), SWT.POP_UP);
			Menu popupMenuGrille = new Menu(vue.getShell(), SWT.POP_UP);
			Menu[] tabPopups = new Menu[] { popupMenuFormulaire,
					popupMenuGrille };
			this.initPopupAndButtons(mapActions, tabPopups);


			initEtatBouton();
		} catch (Exception e) {
			//			vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaArticlesController", e);
		}
	}

	@Override
	protected void initImageBouton() {
//		String C_IMAGE_GENERE = "/icons/page_white_gear.png";
		vue.getPaBtn1().getBtnFermer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_FERMER));
		vue.getPaBtn1().getBtnImprimer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_GENERE));
		vue.layout(true);
	}

	public Composite getVue() {return vue;}


	public ResultAffiche configPanel(ParamAffiche param){
		if (param!=null){
			if (param.getFocusDefaut()!=null)
				param.getFocusDefaut().requestFocus();

			if(param.getEcranAppelant()!=null) {
				ecranAppelant = param.getEcranAppelant();
			}

			if(param instanceof ParamAfficheChoixGenerationDocument) {
				String codeTiers="";
				documentSrc = ((ParamAfficheChoixGenerationDocument)param).getDocumentSrc();
				typeSrc = ((ParamAfficheChoixGenerationDocument)param).getTypeSrc();
				typeDest =((ParamAfficheChoixGenerationDocument)param).getTypeDest();
				listeDocument=((ParamAfficheChoixGenerationDocument)param).getListeDocumentSrc();
				setNouveauLibelle(((ParamAfficheChoixGenerationDocument)param).getLibelle());
				if(((ParamAfficheChoixGenerationDocument)param).getDateDocument()==null)
					dateDocument=new Date();
				else
					dateDocument=((ParamAfficheChoixGenerationDocument)param).getDateDocument();
				if(((ParamAfficheChoixGenerationDocument)param).getDateLivraison()==null)
					dateLivDocument=new Date();
				else
					dateLivDocument=((ParamAfficheChoixGenerationDocument)param).getDateLivraison();

				LibDateTime.setDate(vue.getTfDateDoc(), dateDocument) ;
				vue.getTfTiers().setEnabled(((ParamAfficheChoixGenerationDocument) param).isTiersModifiable());
				if(listeDocument!=null && listeDocument.size()>0)
					codeTiers=((IDocumentTiers)listeDocument.get(0)).getTaTiers().getCodeTiers();
				if(((ParamAfficheChoixGenerationDocument) param).getCodeTiers()!=null &&
						!((ParamAfficheChoixGenerationDocument) param).getCodeTiers().equals(""))
					codeTiers=((ParamAfficheChoixGenerationDocument) param).getCodeTiers();
				else 
					if(((ParamAfficheChoixGenerationDocument) param).isMultiple()==true)codeTiers="";
				vue.getTfTiers().setText(codeTiers);
				
				//parametrage d'un libellé reprenant le numéro de chaque document pour séparer chaque groupe de lignes
				//des différents documents composant le nouveau.
				if(!((ParamAfficheChoixGenerationDocument) param).isMultiple()){
					((ParamAfficheChoixGenerationDocument)param).setRepriseAucun(true);
					((ParamAfficheChoixGenerationDocument)param).setRepriseReferenceSrc(false);
					((ParamAfficheChoixGenerationDocument)param).setRepriseLibelleSrc(false);
				}
				else{
					((ParamAfficheChoixGenerationDocument)param).setRepriseReferenceSrc(false);
					((ParamAfficheChoixGenerationDocument)param).setRepriseLibelleSrc(true);
					((ParamAfficheChoixGenerationDocument)param).setRepriseAucun(false);
				}
				((ParamAfficheChoixGenerationDocument)param).setGenerationModele(true);
				vue.getBtnReference().setSelection(((ParamAfficheChoixGenerationDocument)param).getRepriseReferenceSrc());
				vue.getBtnLibelle().setSelection(((ParamAfficheChoixGenerationDocument)param).getRepriseLibelleSrc());
				vue.getBtnAucun().setSelection(((ParamAfficheChoixGenerationDocument)param).getRepriseAucun());

				setResult((ParamAfficheChoixGenerationDocument)param);
			}

//			addAllButton();
			
			if(typeSrc==TypeDoc.TYPE_AVOIR) {
				addBtnAvoir();
			} else if(typeSrc==TypeDoc.TYPE_ACOMPTE) {
				addBtnAcompte();
			} else 
				if(typeSrc==TypeDoc.TYPE_DEVIS) {
				addBtnDevis();
				addBtnFacture();
				addBtnBonLiv();
				addBtnBonCmd();
				addBtnProforma();
				addBtnAvoir();
				addBtnApporteur();
//				addBtnAcompte();
				addBtnPrelevement();
			} else if(typeSrc==TypeDoc.TYPE_FACTURE) {
				addBtnDevis();
				addBtnFacture();
				addBtnBonLiv();
				addBtnBonCmd();
				addBtnProforma();
				addBtnAvoir();
				addBtnApporteur();
//				addBtnAcompte();
				addBtnPrelevement();
			} else if(typeSrc==TypeDoc.TYPE_PROFORMA) {
				addBtnDevis();
				addBtnFacture();
				addBtnBonLiv();
				addBtnBonCmd();
				addBtnProforma();
				addBtnAvoir();
				addBtnApporteur();
//				addBtnAcompte();
				addBtnPrelevement();
			}else if(typeSrc==TypeDoc.TYPE_PRELEVEMENT) {
				addBtnDevis();
				addBtnFacture();
				addBtnBonLiv();
				addBtnBonCmd();
				addBtnProforma();
				addBtnAvoir();
				addBtnApporteur();
//				addBtnAcompte();
				addBtnPrelevement();
			} else if(typeSrc==TypeDoc.TYPE_APPORTEUR) {
				addBtnApporteur();
			} else if(typeSrc==TypeDoc.TYPE_BON_COMMANDE) {
				addBtnDevis();
				addBtnFacture();
				addBtnBonLiv();
				addBtnBonCmd();
				addBtnProforma();
				addBtnAvoir();
				addBtnApporteur();
//				addBtnAcompte();
				addBtnPrelevement();
			} else if(typeSrc==TypeDoc.TYPE_BON_LIVRAISON) {
				addBtnDevis();
				addBtnFacture();
				addBtnBonLiv();
				addBtnBonCmd();
				addBtnProforma();
				addBtnAvoir();
				addBtnApporteur();
//				addBtnAcompte();
				addBtnPrelevement();
			}

			if(!boutonChoix.isEmpty())
				boutonChoix.get(boutonChoix.keySet().iterator().next()).setSelection(true);
			vue.getParent().setSize(LARGEUR, hauteur);
			vue.getGroupChoix().layout();
			vue.layout();
			
//			for (Integer i : boutonChoix.keySet()) {
//				listeComposantFocusable.add(boutonChoix.get(i));
//				addFocusCommand(focusId, control, commandId, commandHandler)
//			}
			initEtatBouton();
		}
		return null;
	}
	
	private void addAllButton() {
		addBtnAvoir();
		addBtnDevis();
		addBtnFacture();
		addBtnProforma();
		addBtnApporteur();
		addBtnBonCmd();
		addBtnBonLiv();
	}

	private void addBtnAvoir() {
		Button btn = new Button(vue.getGroupChoix(),SWT.RADIO);
		btn.setText("Avoir");
		boutonChoix.put(TypeDoc.TYPE_AVOIR, btn);
		hauteur+=HAUTEUR_BOUTON;
	}

	private void addBtnDevis() {
		Button btn = new Button(vue.getGroupChoix(),SWT.RADIO);
		btn.setText("Devis");
		boutonChoix.put(TypeDoc.TYPE_DEVIS, btn);
		hauteur+=HAUTEUR_BOUTON;
	}

	private void addBtnFacture() {
		Button btn = new Button(vue.getGroupChoix(),SWT.RADIO);
		btn.setText("Facture");
		boutonChoix.put(TypeDoc.TYPE_FACTURE, btn);
		hauteur+=HAUTEUR_BOUTON;
	}

	private void addBtnProforma() {
		Button btn = new Button(vue.getGroupChoix(),SWT.RADIO);
		btn.setText("Proforma");
		boutonChoix.put(TypeDoc.TYPE_PROFORMA, btn);
		hauteur+=HAUTEUR_BOUTON;
	}

	private void addBtnApporteur() {
		Button btn = new Button(vue.getGroupChoix(),SWT.RADIO);
		btn.setText("Facture apporteur");
		boutonChoix.put(TypeDoc.TYPE_APPORTEUR, btn);
		hauteur+=HAUTEUR_BOUTON;
	}

	private void addBtnBonCmd() {
		Button btn = new Button(vue.getGroupChoix(),SWT.RADIO);
		btn.setText("Bon de commande");
		boutonChoix.put(TypeDoc.TYPE_BON_COMMANDE, btn);
		hauteur+=HAUTEUR_BOUTON;
	}

	private void addBtnBonLiv() {
		Button btn = new Button(vue.getGroupChoix(),SWT.RADIO);
		btn.setText("Bon de livraison");
		boutonChoix.put(TypeDoc.TYPE_BON_LIVRAISON, btn);
		hauteur+=HAUTEUR_BOUTON;
	}
	private void addBtnPrelevement() {
		Button btn = new Button(vue.getGroupChoix(),SWT.RADIO);
		btn.setText("Prelevement");
		boutonChoix.put(TypeDoc.TYPE_PRELEVEMENT, btn);
		hauteur+=HAUTEUR_BOUTON;
	}
	private void addBtnAcompte() {
		Button btn = new Button(vue.getGroupChoix(),SWT.RADIO);
		btn.setText("Acompte");
		boutonChoix.put(TypeDoc.TYPE_ACOMPTE, btn);
		hauteur+=HAUTEUR_BOUTON;
	}
	/**
	 * Initialisation des composants graphiques de la vue.
	 * @throws ExceptLgr 
	 */
	protected void initComposantsVue() throws ExceptLgr {
	}

	/**
	 * Initialisation des boutons suivant l'état de l'objet "ibTaTable"
	 */
	protected void initEtatBouton() {
		//Il n'y a pas de DAO sur cet ecran donc on active/desactive directement les handlers
		boolean trouve = boutonChoix.size()>0?true:false;
		enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,true);
		enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,trouve);
		enableActionAndHandler(C_COMMAND_GLOBAL_FERMER_ID,true);
		enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,true);
	}	


	/**
	 * Initialisation des correspondances entre les champs de formulaire et les
	 * champs de bdd
	 */
	protected void initMapComposantChamps() {
		// formulaire Adresse
		if (listeComposantFocusable == null) 
			listeComposantFocusable = new ArrayList();

		if (mapComposantChamps == null) 
			mapComposantChamps = new LinkedHashMap();

//		listeComposantFocusable.add(vue.getTfTiers());
		listeComposantFocusable.add(vue.getTfLibelle());

		listeComposantFocusable.add(vue.getPaBtn1().getBtnImprimer());
		listeComposantFocusable.add(vue.getPaBtn1().getBtnFermer());

		if(mapInitFocus == null) 
			mapInitFocus = new LinkedHashMap();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION,vue.getTfLibelle());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION,vue.getTfLibelle());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION,vue.getTfLibelle());
	}


	@Override
	public void initCommands(){
		if(handlerService == null)
			handlerService = (IHandlerService)PlatformUI.getWorkbench().getService(IHandlerService.class);
		if(contextService == null)
			contextService = (IContextService) PlatformUI.getWorkbench().getService(IContextService.class);
		contextService.registerShell(vue.getShell(),IContextService.TYPE_DIALOG);
		activeShellExpression = new ActiveShellExpression(vue.getShell());

		handlerService.activateHandler(C_COMMAND_GLOBAL_AIDE_ID, handlerAide,activeShellExpression);
		handlerService.activateHandler(C_COMMAND_GLOBAL_ANNULER_ID, handlerAnnuler, activeShellExpression);
		handlerService.activateHandler(C_COMMAND_GLOBAL_FERMER_ID, handlerFermer, activeShellExpression);
		handlerService.activateHandler(C_COMMAND_GLOBAL_IMPRIMER_ID, handlerImprimer, activeShellExpression);
	}

	protected void initActions() {
		initCommands();
		if (mapActions == null)
			mapActions = new LinkedHashMap();

		mapCommand = new HashMap<String, IHandler>();

		//mapCommand.put(C_COMMAND_GLOBAL_AIDE_ID, handlerAide);
		mapCommand.put(C_COMMAND_GLOBAL_FERMER_ID, handlerFermer);
		mapCommand.put(C_COMMAND_GLOBAL_IMPRIMER_ID, handlerImprimer);
		mapCommand.put(C_COMMAND_GENERATIONDOCUMENT_REMONTERDOC_ID, handlerRemonterDoc);
		mapCommand.put(C_COMMAND_GENERATIONDOCUMENT_IMPRIMERDOC_ID, handlerImprimerDoc);

		initFocusCommand(String.valueOf(this.hashCode()),
				listeComposantFocusable, mapCommand);

		mapActions.put(vue.getPaBtn1().getBtnFermer(), C_COMMAND_GLOBAL_FERMER_ID);
		mapActions.put(vue.getPaBtn1().getBtnImprimer(), C_COMMAND_GLOBAL_IMPRIMER_ID);


		Object[] tabActionsAutres = new Object[] { };
		mapActions.put(null, tabActionsAutres);
	}

	@Override
	public boolean onClose() throws ExceptLgr {
		fireRetourEcran(new RetourEcranEvent(this, null));		
		return true;
	}


	public void retourEcran(RetourEcranEvent evt) {
		if (evt.getRetour() != null
				&& (evt.getSource() instanceof SWTPaAideControllerSWT)) {
			if (getFocusAvantAideSWT() instanceof Text) {
				try {
					((Text) getFocusAvantAideSWT()).setText(((ResultAffiche) evt
							.getRetour()).getResult());					
					ctrlUnChampsSWT(getFocusAvantAideSWT());
				} catch (Exception e) {
					logger.error("",e);
					//					vue.getLaMessage().setText(e.getMessage());
				}
			}
		}
		super.retourEcran(evt);
	}

	@Override
	protected void actInserer() throws Exception{}

	@Override
	protected void actModifier() throws Exception{}

	@Override
	protected void actSupprimer() throws Exception{}

	@Override
	protected void actFermer() throws Exception {
		closeWorkbenchPart();
	}

	@Override
	protected void actAnnuler() throws Exception {
		actFermer();
	}

	@Override
	protected void actImprimer() throws Exception {
		if(validation(true)){
			for (String i : boutonChoix.keySet()) {
				if(boutonChoix.get(i).getSelection()) {
					selectedType = i;
				}
			}
			nouveauLibelle = vue.getTfLibelle().getText();
			result.setDateDocument(LibDateTime.getDate(vue.getTfDateDoc()));
			result.setLibelle(vue.getTfLibelle().getText());
			result.setRepriseLibelleSrc(vue.getBtnLibelle().getSelection());
			result.setRepriseReferenceSrc(vue.getBtnReference().getSelection());
			result.setRepriseAucun(vue.getBtnAucun().getSelection());
			result.setTypeDest(selectedType);
//			if(result.isTiersModifiable())
				result.setCodeTiers(vue.getTfTiers().getText());
			result.setRetour(true);
			
			List<IDocumentTiers> listeDocCrees=new LinkedList<IDocumentTiers>();
			if(getResult().getListeDocumentSrc().size()!=0){
				IDocumentTiers doc=null; 
				//param.setLibelle(libelle);
				CreationDocumentMultiple creation = new CreationDocumentMultiple(getResult(),getEm());
				doc=creation.creationDocument(false);
				if(doc!=null){
					listeDocCrees.add(doc);
				}
			}
//			nouveauLibelle = vue.getTfLibelle().getText();
//			
//			AbstractGenereDoc genereDocument = null;
//			IDocumentTiers docGenere = null;
//			String idEditor ="";
//			final TypeDoc typeDocPresent = TypeDoc.getInstance();
//			String typeCPaiement="";
//			TaCPaiement taCPaiement1=null;
//			TaCPaiement taCPaiement2=null;
//			TaTiersDAO daoTiers = new TaTiersDAO(getEm());
//			TaTiers tiers = null;
//			
//			if(selectedType==TypeDoc.TYPE_AVOIR) {
//				typeCPaiement=TaTCPaiement.C_CODE_TYPE_AVOIR;
//				docGenere = new TaAvoir();
//				//if(documentSrc.)
//			} else if(selectedType==TypeDoc.TYPE_DEVIS) {
//				typeCPaiement=TaTCPaiement.C_CODE_TYPE_DEVIS;
//				docGenere = new TaDevis();
//			} else if(selectedType==TypeDoc.TYPE_FACTURE) {
//				typeCPaiement=TaTCPaiement.C_CODE_TYPE_FACTURE;
//				docGenere = new TaFacture();
//			} else if(selectedType==TypeDoc.TYPE_PROFORMA) {
//				typeCPaiement=TaTCPaiement.C_CODE_TYPE_PROFORMA;
//				docGenere = new TaProforma();
//			} else if(selectedType==TypeDoc.TYPE_APPORTEUR) {
//				typeCPaiement=TaTCPaiement.C_CODE_TYPE_APORTEUR;
//				docGenere = new TaApporteur();
//			} else if(selectedType==TypeDoc.TYPE_BON_COMMANDE) {
//				typeCPaiement=TaTCPaiement.C_CODE_TYPE_BON_COMMANDE;
//				docGenere = new TaBoncde();
//			} else if(selectedType==TypeDoc.TYPE_BON_LIVRAISON) {
//				typeCPaiement=TaTCPaiement.C_CODE_TYPE_BON_LIVRAISON;
//				docGenere = new TaBonliv();
//			}else if(selectedType==TypeDoc.TYPE_PRELEVEMENT) {
//				typeCPaiement=TaTCPaiement.C_CODE_TYPE_PRELEVEMENT;
//				docGenere = new TaPrelevement();
//			}
//			
//			TaTCPaiementDAO taTCPaiementDAO = new TaTCPaiementDAO(getEm());
//			TaCPaiementDAO taCPaiementDAO = new TaCPaiementDAO(getEm());
//			if(taTCPaiementDAO.findByCode(typeCPaiement)!=null
//					&& taTCPaiementDAO.findByCode(typeCPaiement).getTaCPaiement()!=null) {
//				taCPaiement1=taTCPaiementDAO.findByCode(typeCPaiement).getTaCPaiement();
//				taCPaiement2=taCPaiementDAO.findById(taCPaiement1.getIdCPaiement());
//			}
//			
//			typeDocPresent.getInstance();
//			idEditor = typeDocPresent.getEditorDoc().get(selectedType);
//			documentSrc.setRelationDocument(false);
//			genereDocument = GenereDocFactory.create(documentSrc, docGenere);
//			//if(!genereDocument.dejaGenere(documentSrc)) {
//				
//				if(!LibChaine.empty(vue.getTfLibelle().getText())) {
//					genereDocument.setLibelleDoc(vue.getTfLibelle().getText());
//				} else {
//					genereDocument.setLibelleDoc(documentSrc.getLibelleDocument());
//				}
//				
//				if(documentSrc.getTaTiers().getTaCPaiement()!=null && selectedType==TypeDoc.TYPE_FACTURE){
//					taCPaiement2=documentSrc.getTaTiers().getTaCPaiement();
//				}
//				if(taCPaiement2!=null){
//					genereDocument.setTaCPaiement(taCPaiement2);
//				}
//				
//				Date date = new Date();
//				if (date.before(documentSrc.getDateDocument()))
//					date=documentSrc.getDateDocument();
//				genereDocument.setDateDoc(dateDansExercice(date));
//				
//				//genereDocument.setDateDoc(dateDansExercice(new Date()));
//				
//				if(!vue.getTfTiers().getText().trim().equals("") && 
//						daoTiers.findByCode(vue.getTfTiers().getText())!=null)
//					genereDocument.setCodeTiers(daoTiers.findByCode(vue.getTfTiers().getText()).getCodeTiers());
//				
//				docGenere = genereDocument.genereDocument(documentSrc,docGenere,genereDocument.getCodeTiers());
//				
//
//				
//				if(MessageDialog.openConfirm(
//						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
//						"Création document",
//						"Le document '"+docGenere.getCodeDocument()+"' à été généré correctement. Voulez-vous le visualiser ?"));
//				{
//					String valeurIdentifiant = docGenere.getCodeDocument();
//					ouvreDocument(valeurIdentifiant, idEditor);
//				}
				
			if(listeDocCrees.size()>0){
				TypeDoc typeDocPresent = TypeDoc.getInstance();
				String idEditor = typeDocPresent.getEditorDoc().get(selectedType);
				if(MessageDialog.openQuestion(
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
						"Création document",
						"Le document '"+listeDocCrees.get(0).getCodeDocument()+"' à été généré correctement. Voulez-vous le visualiser ?"))
				{
					String valeurIdentifiant = listeDocCrees.get(0).getCodeDocument();
					ouvreDocument(valeurIdentifiant, idEditor);
				}
			}
			actFermer();
		}
	}


	@Override
	protected void actAide() throws Exception {
		actAide(null);
	}


	@Override
	protected void actAide(String message) throws Exception {
//		if (true) {
////			boolean affichageAideRemplie = LgrMess.isAfficheAideRemplie();
//			boolean affichageAideRemplie = true;
//			setActiveAide(true);
//			boolean verrouLocal = VerrouInterface.isVerrouille();
//			VerrouInterface.setVerrouille(true);
//			try {
//				vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
//				ParamAfficheAideRechercheSWT paramAfficheAideRecherche = new ParamAfficheAideRechercheSWT();
////				paramAfficheAideRecherche.setDb(getThis().getIbTaTable().getFIBBase());
//				paramAfficheAideRecherche.setMessage(message);
//				// Creation de l'ecran d'aide principal
//				Shell s = new Shell(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),LgrShellUtil.styleLgr);
//				PaAideSWT paAide = new PaAideSWT(s, SWT.NONE);
//				SWTPaAideControllerSWT paAideController = new SWTPaAideControllerSWT(paAide);
//				/** ************************************************************ */
//				LgrPartListener.getLgrPartListener().setLgrActivePart(null);
//				IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new EditorInputAide(), EditorAide.ID);
//				LgrPartListener.getLgrPartListener().setLgrActivePart(e);
//				LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
//				paAideController = new SWTPaAideControllerSWT(((EditorAide) e).getComposite());
//				((LgrEditorPart) e).setController(paAideController);
//				((LgrEditorPart) e).setPanel(((EditorAide) e).getComposite());
//				/** ************************************************************ */
//				JPABaseControllerSWTStandard controllerEcranCreation = null;
//				ParamAffiche parametreEcranCreation = null;
//				IEditorPart editorCreation = null;
//				String editorCreationId = null;
//				IEditorInput editorInputCreation = null;
//				Shell s2 = new Shell(s, LgrShellUtil.styleLgr);
//				
//
//					
//					if (getFocusCourantSWT().equals(vue.getTfTiers())) {
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
//						paramAfficheAideRecherche.setDebutRecherche(vue.getTfTiers().getText());
//						paramAfficheAideRecherche.setControllerAppelant(this);
////						ModelTiers modelTiers = new ModelTiers(swtPaTiersController.getIbTaTable());
////						paramAfficheAideRecherche.setModel(modelTiers);
//						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTiers,TaTiersDAO,TaTiers>(SWTTiers.class,getEm()));
//						paramAfficheAideRecherche.setTypeObjet(swtPaTiersController.getClassModel());
//						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_TIERS);
//						
//					}
//
//				
//
//				if (paramAfficheAideRecherche.getJPQLQuery() != null) {
//
//					PaAideRechercheSWT paAideRecherche1 = new PaAideRechercheSWT(s, SWT.NULL);
//					SWTPaAideRechercheControllerSWT paAideRechercheController1 = new SWTPaAideRechercheControllerSWT(paAideRecherche1);
//
//					// Parametrage de la recherche
//					paramAfficheAideRecherche.setFocusSWT(((PaAideRechercheSWT) paAideRechercheController1.getVue()).getTfChoix());
//					paramAfficheAideRecherche.setRefCreationSWT(controllerEcranCreation);
//					paramAfficheAideRecherche.setEditorCreation(editorCreation);
//					paramAfficheAideRecherche.setEditorCreationId(editorCreationId);
//					paramAfficheAideRecherche.setEditorInputCreation(editorInputCreation);
//					paramAfficheAideRecherche.setParamEcranCreation(parametreEcranCreation);
//					paramAfficheAideRecherche.setShellCreation(s2);
//					paAideRechercheController1.configPanel(paramAfficheAideRecherche);
//					// paramAfficheAideRecherche.setFocusDefaut(paAideRechercheController1.getVue().getTfChoix());
//
//					// Ajout d'une recherche
//					paAideController.addRecherche(paAideRechercheController1,paramAfficheAideRecherche.getTitreRecherche());
//
//					// Parametrage de l'ecran d'aide principal
//					ParamAfficheAideSWT paramAfficheAideSWT = new ParamAfficheAideSWT();
//					ParamAfficheAide paramAfficheAide = new ParamAfficheAide();
//
//					// enregistrement pour le retour de l'ecran d'aide
//					paAideController.addRetourEcranListener(this);
//					Control focus = vue.getShell().getDisplay().getFocusControl();
//					// affichage de l'ecran d'aide principal (+ ses recherches)
//
//
//					paAideController.configPanel(paramAfficheAide);
//					/*****************************************************************/
//
//				}
//
//			} finally {
//				VerrouInterface.setVerrouille(false);
//				vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
//			}
//		}
	}


	@Override
	protected void actEnregistrer() throws Exception{
	}


	@Override
	public void initEtatComposant() {}


	@Override
	protected void actRefresh() throws Exception {}

	@Override
	protected void initMapComposantDecoratedField() {}

	@Override
	protected void sortieChamps() {}

	private class HandlerRemonterDoc extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
//			try {
//				//fr.legrain.editor.facture.swt.multi
//				if(vue.getTfListeDoc().getSelection()!=null&&vue.getTfListeDoc().getSelection().length>0){										
//					String idEditor = "fr.legrain.editor.facture.swt.multi";
//					IEditorInput editorInput = new IEditorInput() {
//						public boolean exists() {
//							return false;
//						}
//
//						public ImageDescriptor getImageDescriptor() {
//							return null;
//						}
//
//						public String getName() {
//							return "";
//						}
//
//						public IPersistableElement getPersistable() {
//							return null;
//						}
//
//						public String getToolTipText() {
//							return "";
//						}
//
//						public Object getAdapter(Class adapter) {
//							return null;
//						}
//
//					};
//
//					if(idEditor!=null) {
//						IEditorPart editor = AbstractLgrMultiPageEditor.chercheEditeurDocumentOuvert(idEditor);
//						if(editor==null) {
//							LgrPartListener.getLgrPartListener().setLgrActivePart(null);
//							IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().
//							getActivePage().openEditor(editorInput, idEditor);
//							//							LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
//							//							LgrPartListener.getLgrPartListener().setLgrActivePart(e);
//
//							ParamAffiche paramAffiche = new ParamAffiche();	
//							paramAffiche.setCodeDocument(vue.getTfListeDoc().getSelection()[0]);
//							paramAffiche.setModeEcran(EnumModeObjet.C_MO_CONSULTATION);
//							((AbstractLgrMultiPageEditor)e).findMasterController().configPanel(paramAffiche);
//						}
//
//					}				  
//				}
//			} catch (Exception e1) {
//				logger.error("Erreur : actionPerformed", e1);
//			}
			return event;
		}
	}

	private class HandlerImprimerDoc extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
//			if(vue.getTfListeDoc().getSelectionCount()>0){
//				try {
//					LgrPartListener.getLgrPartListener().setLgrActivePart(null);
//					PlatformUI.getWorkbench().getActiveWorkbenchWindow();
//					FacturePlugin.getDefault().getBundle();
//
//					String[] idFactureAImprimer = new String[vue.getTfListeDoc().getSelection().length];
//					boolean preview = vue.getCbReExport().getSelection();
//
//					String codeDeb = null;
//					String[] idDoc=null;
//
//					for (int i = 0; i < vue.getTfListeDoc().getSelection().length; i++) {
//						idDoc=null;
//						codeDeb = vue.getTfListeDoc().getSelection()[i];
//						TaFactureDAO daoFacture = new TaFactureDAO();
//						idDoc=daoFacture.rechercheFacture(codeDeb, codeDeb);
//						if(idDoc!=null && idDoc.length>0)
//							idFactureAImprimer[i]=idDoc[0];
//					}
//
//					///////////////////////////////////////
//					final String[] finalIdFactureAImprimer = idFactureAImprimer;
//					final boolean finalPreview = preview;
//					vue.getParent().getDisplay().asyncExec(new Thread() {
//						public void run() {
//							try {					
//								impressionFacture.imprimer(finalIdFactureAImprimer,finalPreview);
//							} catch (Exception e) {
//								logger.error("Erreur à l'impression ",e);
//							} finally {
//							}
//						}
//					});
//
//				} catch (Exception e1) {
//					logger.error("Erreur : actionPerformed", e1);
//				}
//			}
			return event;
		}
	}

	private TaTiers rechercheTiers(String code){
		try {
			
		TaTiers tiers=daoTiers.findByCode(code);
		return tiers;
		} catch (Exception e) {
			return null;
		}
	}
	
	private boolean validation(boolean message){
		try {
			Date dateTemp;
			
			dateTemp =LibDateTime.getDate(vue.getTfDateDoc());
			TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO();
			TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
			if(taInfoEntreprise.getDatedebInfoEntreprise().after(dateTemp)||
					taInfoEntreprise.getDatefinInfoEntreprise().before(dateTemp))
			{
				if (message)MessageDialog.openWarning(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(),
						"Attention", "La date n'est pas valide");
				vue.getTfDateDoc().forceFocus();
				return false;
			}
			if(vue.getTfTiers().getText().equals("") || rechercheTiers(vue.getTfTiers().getText())==null)
			{
				if (message)MessageDialog.openWarning(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(),
						"Attention", "Vous n'avez pas sélectionné de code tiers existant !!!");
				vue.getTfTiers().forceFocus();
				return false;
			}		
			
			return boutonChoix.size() > 0 ? true : false;
		} catch (Exception e) {
			logger.error("", e);
			return false;
		}
	}

	public String getSelectedType() {
		return selectedType;
	}

	public String getNouveauLibelle() {
		return nouveauLibelle;
	}

	public String getTypeSrc() {
		return typeSrc;
	}

	public void setTypeSrc(String typeSrc) {
		this.typeSrc = typeSrc;
	}

	public String getTypeDest() {
		return typeDest;
	}

	public void setTypeDest(String typeDest) {
		this.typeDest = typeDest;
	}

	public IDocumentTiers getDocumentSrc() {
		return documentSrc;
	}

	public void setDocumentSrc(IDocumentTiers documentSrc) {
		this.documentSrc = documentSrc;
	}

	public Date getDateDocument() {
		return dateDocument;
	}

	public void setDateDocument(Date dateDocument) {
		this.dateDocument = dateDocument;
	}

	public Date getDateLivDocument() {
		return dateLivDocument;
	}

	public void setDateLivDocument(Date dateLivDocument) {
		this.dateLivDocument = dateLivDocument;
	}

	public void setSelectedType(String selectedType) {
		this.selectedType = selectedType;
	}

	public void setNouveauLibelle(String nouveauLibelle) {
		this.nouveauLibelle = nouveauLibelle;
	}

	public ParamAfficheChoixGenerationDocument getResult() {
		return result;
	}

	public void setResult(ParamAfficheChoixGenerationDocument result) {
		this.result = result;
	}


}


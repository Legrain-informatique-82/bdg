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
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.generationdocument.divers.ParamAfficheChoixGenerationDocument;
import fr.legrain.generationdocument.ecran.PaChoixGenerationDocument;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
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
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.ecran.PaTiersSWT;
import fr.legrain.tiers.ecran.ParamAfficheTiers;
import fr.legrain.tiers.ecran.SWTPaTiersController;
import fr.legrain.tiers.editor.EditorInputTiers;
import fr.legrain.tiers.editor.TiersMultiPageEditor;



public class PaChoixGenerationDocumentController extends JPABaseControllerSWTStandard implements
RetourEcranListener {

	static Logger logger = Logger.getLogger(PaChoixGenerationDocumentController.class.getName());		
	private PaChoixGenerationDocument vue = null; // vue

	private Object ecranAppelant = null;

	public static final String TITRE = "Génération de document.";
	private static final int HAUTEUR_BOUTON=25;
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

	public PaChoixGenerationDocumentController(PaChoixGenerationDocument vue) {
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
				typeSrc = ((ParamAfficheChoixGenerationDocument)param).getTypeSrc();
				typeDest =((ParamAfficheChoixGenerationDocument)param).getTypeDest();
				listeDocument=((ParamAfficheChoixGenerationDocument)param).getListeDocumentSrc();
				setNouveauLibelle(((ParamAfficheChoixGenerationDocument)param).getLibelle());
				if(((ParamAfficheChoixGenerationDocument)param).getDateDocument()==null)
					dateDocument=new Date();
				else
					dateDocument=((ParamAfficheChoixGenerationDocument)param).getDateDocument();
//				if(((ParamAfficheChoixGenerationDocument)param).getDateLivraison()==null)
//					dateLivDocument=new Date();
//				else
//					dateLivDocument=((ParamAfficheChoixGenerationDocument)param).getDateLivraison();
				LibDateTime.setDate(vue.getTfDateDoc(), dateDocument) ;
				vue.getTfTiers().setEnabled(false);
				vue.getLaTiers().setEnabled(false);
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
				((ParamAfficheChoixGenerationDocument)param).setGenerationModele(false);
				vue.getBtnReference().setSelection(((ParamAfficheChoixGenerationDocument)param).getRepriseReferenceSrc());
				vue.getBtnLibelle().setSelection(((ParamAfficheChoixGenerationDocument)param).getRepriseLibelleSrc());
				vue.getBtnAucun().setSelection(((ParamAfficheChoixGenerationDocument)param).getRepriseAucun());
				
				setResult((ParamAfficheChoixGenerationDocument)param);
			}
			

			
			if(typeDest!=null && typeDest!=""){
					if(typeDest==TypeDoc.TYPE_ACOMPTE)
						addBtnAcompte();
					if(typeDest==TypeDoc.TYPE_APPORTEUR)
						addBtnApporteur();
					if(typeDest==TypeDoc.TYPE_AVOIR)
						addBtnAvoir();
					if(typeDest==TypeDoc.TYPE_BON_COMMANDE)
						addBtnBonCmd();
					if(typeDest==TypeDoc.TYPE_BON_LIVRAISON)
						addBtnBonLiv();
					if(typeDest==TypeDoc.TYPE_DEVIS)
						addBtnDevis();
					if(typeDest==TypeDoc.TYPE_FACTURE)
						addBtnFacture();
					if(typeDest==TypeDoc.TYPE_PRELEVEMENT)
						addBtnPrelevement();
					if(typeDest==TypeDoc.TYPE_PROFORMA)
						addBtnProforma();
					if(typeDest==TypeDoc.TYPE_AVIS_ECHEANCE)
						addBtnAvisEcheance();

			}else{
				TypeDoc.getInstance();
				for (int i = 0; i < TypeDoc.getInstance().getEditorDocPossibleCreationDocument().size(); i++) {
					if(TypeDoc.getInstance().getEditorDocPossibleCreationDocument().get(i)[0]==typeSrc){
						if(TypeDoc.getInstance().getEditorDocPossibleCreationDocument().get(i)[1]==TypeDoc.TYPE_ACOMPTE)
							addBtnAcompte();
						if(TypeDoc.getInstance().getEditorDocPossibleCreationDocument().get(i)[1]==TypeDoc.TYPE_APPORTEUR)
							addBtnApporteur();
						if(TypeDoc.getInstance().getEditorDocPossibleCreationDocument().get(i)[1]==TypeDoc.TYPE_AVOIR)
							addBtnAvoir();
						if(TypeDoc.getInstance().getEditorDocPossibleCreationDocument().get(i)[1]==TypeDoc.TYPE_BON_COMMANDE)
							addBtnBonCmd();
						if(TypeDoc.getInstance().getEditorDocPossibleCreationDocument().get(i)[1]==TypeDoc.TYPE_BON_LIVRAISON)
							addBtnBonLiv();
						if(TypeDoc.getInstance().getEditorDocPossibleCreationDocument().get(i)[1]==TypeDoc.TYPE_DEVIS)
							addBtnDevis();
						if(TypeDoc.getInstance().getEditorDocPossibleCreationDocument().get(i)[1]==TypeDoc.TYPE_FACTURE)
							addBtnFacture();
						if(TypeDoc.getInstance().getEditorDocPossibleCreationDocument().get(i)[1]==TypeDoc.TYPE_PRELEVEMENT)
							addBtnPrelevement();
						if(TypeDoc.getInstance().getEditorDocPossibleCreationDocument().get(i)[1]==TypeDoc.TYPE_PROFORMA)
							addBtnProforma();
						if(TypeDoc.getInstance().getEditorDocPossibleCreationDocument().get(i)[1]==TypeDoc.TYPE_AVIS_ECHEANCE)
							addBtnAvisEcheance();
					}
				}
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
		addBtnAcompte();
		addBtnAvoir();
		addBtnDevis();
		addBtnFacture();
		addBtnProforma();
		addBtnPrelevement();
		addBtnApporteur();
		addBtnBonCmd();
		addBtnBonLiv();
		addBtnAvisEcheance();
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
	private void addBtnAvisEcheance() {
		Button btn = new Button(vue.getGroupChoix(),SWT.RADIO);
		btn.setText("Avis d'échéance");
		boutonChoix.put(TypeDoc.TYPE_AVIS_ECHEANCE, btn);
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
//		enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,true);
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

//		mapCommand.put(C_COMMAND_GLOBAL_AIDE_ID, handlerAide);
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
			result.setTypeDest(selectedType);
			result.setRepriseLibelleSrc(vue.getBtnLibelle().getSelection());
			result.setRepriseReferenceSrc(vue.getBtnReference().getSelection());
			result.setRepriseAucun(vue.getBtnAucun().getSelection());
//			if(result.isTiersModifiable())
				result.setCodeTiers(vue.getTfTiers().getText());
			result.setRetour(true);
			
			List<IDocumentTiers> listeDocCrees=new LinkedList<IDocumentTiers>();
			if(getResult().getListeDocumentSrc().size()!=0){
				IDocumentTiers doc=null; 
				//param.setLibelle(libelle);
				CreationDocumentMultiple creation = new CreationDocumentMultiple(getResult(),getEm());
				doc=creation.creationDocument(true);
				if(doc!=null){
					listeDocCrees.add(doc);
				}
			}
			

			if(listeDocCrees.size()>0){
				TypeDoc typeDocPresent = TypeDoc.getInstance();
				String idEditor = typeDocPresent.getEditorDoc().get(selectedType);
			
				if(MessageDialog.openConfirm(
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

	public Date getDateDocument() {
		return dateDocument;
	}

	public void setDateDocument(Date dateDocument) {
		this.dateDocument = dateDocument;
	}

	public void setNouveauLibelle(String nouveauLibelle) {
		if(this.nouveauLibelle==null||
				!this.nouveauLibelle.equals(nouveauLibelle))
			vue.getTfLibelle().setText(nouveauLibelle);
		this.nouveauLibelle = nouveauLibelle;
	}

	public Date getDateLivDocument() {
		return dateLivDocument;
	}

	public void setDateLivDocument(Date dateLivDocument) {
		this.dateLivDocument = dateLivDocument;
	}

	public String getTypeDest() {
		return typeDest;
	}

	public void setTypeDest(String typeDest) {
		this.typeDest = typeDest;
	}

	public ParamAfficheChoixGenerationDocument getResult() {
		return result;
	}

	public void setResult(ParamAfficheChoixGenerationDocument result) {
		this.result = result;
	}

}


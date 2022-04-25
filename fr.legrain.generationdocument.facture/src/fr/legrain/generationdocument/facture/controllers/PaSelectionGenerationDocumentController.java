package fr.legrain.generationdocument.facture.controllers;


import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.TypedEvent;
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
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.facture.FacturePlugin;
import fr.legrain.facture.divers.Impression;
import fr.legrain.facture.preferences.PreferenceConstants;
import fr.legrain.generationdocument.divers.GenereDocFactureVersFacture;
import fr.legrain.generationdocument.facture.divers.ParamAfficheSelectionListeTiers;
import fr.legrain.generationdocument.facture.ecran.PaSelectionGenerationDocument;
import fr.legrain.generationdocument.facture.editor.EditorEditeurListeTiers;
import fr.legrain.generationdocument.facture.editor.EditorInputEditeurListeTiers;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Document.IHMAideFacture;
import fr.legrain.gestCom.Module_Tiers.SWTTiers;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideRechercheSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideRechercheControllerSWT;
import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.gestionCommerciale.GestionCommercialePlugin;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.LgrSimpleTextContentProposal;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.LibDate;
import fr.legrain.lib.data.ModeObjet;
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



public class PaSelectionGenerationDocumentController extends  JPABaseControllerSWTStandard implements
RetourEcranListener {
	
	static Logger logger = Logger.getLogger(PaSelectionGenerationDocumentController.class.getName());		
	private PaSelectionGenerationDocument vue = null; // vue
//	private SWT_IB_TA_TIERS ibTaTable = new SWT_IB_TA_TIERS();
	private TaTiersDAO dao = null;//new TaTiersDAO();
	private Object ecranAppelant = null;
	
	private Impression impressionFacture = null ;
	public static final String C_COMMAND_GENERATIONDOCUMENT_EDITEURLISTE_ID = "fr.legrain.generationdocument.facture.EditeurListeTiers";
	protected HandlerEditeurListe handlerEditeurListe = new HandlerEditeurListe();
	
	public static final String C_COMMAND_GENERATIONDOCUMENT_SELECTIONLISTE_ID = "fr.legrain.generationdocument.facture.SelectionListeTiers";
	protected HandlerSelectionListe handlerSelectionListe = new HandlerSelectionListe();
	String[] listeCodeFactures=null;
	String[] listeDescriptionFactures=null;
	String[] listeCodeTiers=null;
	String[] listeDescriptionTiers=null;
	private ContentProposalAdapter facturesContentProposal;
	private ContentProposalAdapter tiersContentProposal;
	
	public PaSelectionGenerationDocumentController(PaSelectionGenerationDocument vue) {
		this(vue,null);
	}
	
	public PaSelectionGenerationDocumentController(PaSelectionGenerationDocument vue,EntityManager em) {
		initCursor(SWT.CURSOR_WAIT);
		if(em!=null) {
			setEm(em);
		}
		dao = new TaTiersDAO(getEm());
		try {
			setVue(vue);

			this.vue=vue;
			vue.getShell().addShellListener(this);
			impressionFacture= new Impression(vue.getShell());
//			Branchement action annuler : empeche la fermeture automatique de la
			// fenetre sur ESC
			vue.getShell().addTraverseListener(new Traverse());
			actionImprimer.setText("Valider[F3]");

			initController();
		} catch (Exception e) {
			logger.error("Erreur ", e);
		}
		initCursor(SWT.CURSOR_ARROW);
	}
	
	
	private class LgrModifyListener implements ModifyListener{

		public void modifyText(ModifyEvent e) {
			modif(e);
		}
	}
	
	private void modif(TypedEvent e) {
		
	}
	private void initController() {
		try {
			setDaoStandard(dao);		
			
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
		final String C_IMAGE_EXPORT = "/icons/export_wiz.gif";
		vue.getPaBtn1().getBtnFermer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_FERMER));
//		vue.getPaBtn1().getBtnImprimer().setImage(StocksPlugin.getImageDescriptor(C_IMAGE_EXPORT).createImage());
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
			param.setFocus(dao.getModeObjet().getFocusCourant());
		}
		return null;
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
		super.initEtatBouton();
		switch (daoStandard.getModeObjet().getMode()) {
		case C_MO_INSERTION:
			actionInserer.setEnabled(false);
			actionModifier.setEnabled(false);
			actionEnregistrer.setEnabled(true);
			actionAnnuler.setEnabled(true);
			actionImprimer.setEnabled(false);
			actionFermer.setEnabled(true);
			actionSupprimer.setEnabled(false);
			actionAide.setEnabled(true);
			break;
		case C_MO_EDITION:
			actionInserer.setEnabled(false);
			actionModifier.setEnabled(false);
			actionEnregistrer.setEnabled(true);
			actionAnnuler.setEnabled(true);
			actionImprimer.setEnabled(false);
			actionFermer.setEnabled(true);
			actionSupprimer.setEnabled(false);
			actionAide.setEnabled(true);
			break;
		case C_MO_CONSULTATION:
			actionInserer.setEnabled(false);
			actionModifier.setEnabled(false);
			actionEnregistrer.setEnabled(false);
			actionAnnuler.setEnabled(true);
			actionImprimer.setEnabled(true);
			actionFermer.setEnabled(true);
			actionSupprimer.setEnabled(false);
			actionAide.setEnabled(true);
			break;
		default:
			break;
		}

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
		
		listeComposantFocusable.add(vue.getTfCodeFacture());		
		listeComposantFocusable.add(vue.getTfCODE_TIERS());		
		
		listeComposantFocusable.add(vue.getPaBtn1().getBtnImprimer());
		listeComposantFocusable.add(vue.getPaBtn1().getBtnFermer());
		
		listeComposantFocusable.add(vue.getBtnEditeur());
		//listeComposantFocusable.add(vue.getBtnSelection());
		
		if(mapInitFocus == null) 
			mapInitFocus = new LinkedHashMap();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION,vue.getTfCodeFacture());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION,vue.getTfCodeFacture());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION,vue.getTfCodeFacture());
		
		String activationContentProposal = "Ctrl+Space";
		try {

			String[][] TabFactures = initAdapterFacture();
			if(TabFactures!=null){
				listeCodeFactures=new String[TabFactures.length];
				listeDescriptionFactures=new String[TabFactures.length];
				for (int i=0; i<TabFactures.length; i++) {
					listeCodeFactures [i]=TabFactures[i][0];
					listeDescriptionFactures [i]=TabFactures[i][1];
				}
			}
			facturesContentProposal = LgrSimpleTextContentProposal.defaultTextContentProposalKey(vue.getTfCodeFacture(),activationContentProposal,initAdapterFacture(),null);
			LgrSimpleTextContentProposal.defaultOptions(facturesContentProposal);

//			facturesContentProposal = new ContentProposalAdapter(
//					vue.getTfCodeFacture(), new TextContentAdapter(), 
//					new ContentProposalProvider(listeCodeFactures,listeDescriptionFactures),keyStroke, null);
//			facturesContentProposal.setFilterStyle(ContentProposalAdapter.FILTER_NONE);
//			facturesContentProposal.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);

			String[][] TabTiers = initAdapterTiers();
			if(TabTiers!=null){
				listeCodeTiers=new String[TabTiers.length];
				listeDescriptionTiers=new String[TabTiers.length];
				for (int i=0; i<TabTiers.length; i++) {
					listeCodeTiers [i]=TabTiers[i][0];
					listeDescriptionTiers [i]=TabTiers[i][1];
				}
			}
			tiersContentProposal = LgrSimpleTextContentProposal.defaultTextContentProposalKey(vue.getTfCODE_TIERS(),activationContentProposal,initAdapterTiers(),null);
			LgrSimpleTextContentProposal.defaultOptions(tiersContentProposal);

//			tiersContentProposal = new ContentProposalAdapter(
//					vue.getTfCODE_TIERS(), new TextContentAdapter(), 
//					new ContentProposalProvider(listeCodeTiers,listeDescriptionTiers),keyStroke, null);
//			tiersContentProposal.setFilterStyle(ContentProposalAdapter.FILTER_NONE);
//			tiersContentProposal.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);
		} catch (Exception e1) {
			logger.error("",e1);
		}
	}
	

	
//	@Override
//	public void initCommands(){
//		if(handlerService == null)
//			handlerService = (IHandlerService)PlatformUI.getWorkbench().getService(IHandlerService.class);
//		if(contextService == null)
//			contextService = (IContextService) PlatformUI.getWorkbench().getService(IContextService.class);
//		contextService.registerShell(vue.getShell(),IContextService.TYPE_DIALOG);
//		activeShellExpression = new ActiveShellExpression(vue.getShell());
//		
//		handlerService.activateHandler(C_COMMAND_GLOBAL_AIDE_ID, handlerAide,activeShellExpression);
//		handlerService.activateHandler(C_COMMAND_GLOBAL_ANNULER_ID, handlerAnnuler, activeShellExpression);
//		handlerService.activateHandler(C_COMMAND_GLOBAL_FERMER_ID, handlerFermer, activeShellExpression);
//		handlerService.activateHandler(C_COMMAND_GLOBAL_IMPRIMER_ID, handlerImprimer, activeShellExpression);
//		handlerService.activateHandler(C_COMMAND_GENERATIONDOCUMENT_EDITEURLISTE_ID, handlerEditeurListe, activeShellExpression);
//		handlerService.activateHandler(C_COMMAND_GENERATIONDOCUMENT_EDITEURLISTE_ID, handlerSelectionListe, activeShellExpression);
//	}
	
	protected void initActions() {
		if (mapActions == null)
			mapActions = new LinkedHashMap();
		
		mapCommand = new HashMap<String, IHandler>();
		
		mapCommand.put(C_COMMAND_GLOBAL_AIDE_ID, handlerAide);
		mapCommand.put(C_COMMAND_GLOBAL_FERMER_ID, handlerFermer);
		mapCommand.put(C_COMMAND_GLOBAL_IMPRIMER_ID, handlerImprimer);
		mapCommand.put(C_COMMAND_GENERATIONDOCUMENT_EDITEURLISTE_ID, handlerEditeurListe);
		mapCommand.put(C_COMMAND_GENERATIONDOCUMENT_SELECTIONLISTE_ID, handlerSelectionListe);
		
		initFocusCommand(String.valueOf(this.hashCode()),
				listeComposantFocusable, mapCommand);
		
		mapActions.put(vue.getPaBtn1().getBtnFermer(), C_COMMAND_GLOBAL_FERMER_ID);
		mapActions.put(vue.getPaBtn1().getBtnImprimer(), C_COMMAND_GLOBAL_IMPRIMER_ID);
		
		mapActions.put(vue.getBtnEditeur(), C_COMMAND_GENERATIONDOCUMENT_EDITEURLISTE_ID);
		//mapActions.put(vue.getBtnSelection(), C_COMMAND_GENERATIONDOCUMENT_SELECTIONLISTE_ID);
		
		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID };
		mapActions.put(null, tabActionsAutres);
	}
	
	public PaSelectionGenerationDocumentController getThis(){
		return this;
	}
	
	@Override
	public boolean onClose() throws ExceptLgr {
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
		boolean correct = false;
		String messageErreur ="";
		correct=(rechercheFacture(vue.getTfCodeFacture().getText())!=null);
		if(!correct)messageErreur="Vous devez saisir un code de facture existant !!!";
		else {
			correct=(rechercheTiers(vue.getTfCODE_TIERS().getText())!=null);
			if(!correct)messageErreur="Vous devez saisir un code de tiers existant !!!";
		}
		if(correct){
			String fichierExportation = Platform.getInstanceLocation().getURL().getFile()+"/Erreur_Generation_Document.TXT";
			StringBuffer ligne = new StringBuffer("");
			FileWriter fw = new FileWriter(fichierExportation);
			String message = "";
			String finDeLigne = "\r\n";
			String codeTiers = vue.getTfCODE_TIERS().getText();
			String modelFacture = vue.getTfCodeFacture().getText();

			GenereDocFactureVersFacture genereDocument = new GenereDocFactureVersFacture();
			TaFacture document= null;
			TaFacture documentInit=new TaFactureDAO(getEm()).findByCode(modelFacture);
			genereDocument.setCode(modelFacture);
			genereDocument.setCodeTiers(codeTiers);
			genereDocument.setLibelleDoc(documentInit.getLibelleDocument());
			genereDocument.setDateDoc(documentInit.getDateDocument());
			genereDocument.setCommentaire(FacturePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.COMMENTAIRE));
			genereDocument.setTypeAdresseFacturation(DocumentPlugin.getDefault().getPreferenceStore().getString(fr.legrain.document.preferences.PreferenceConstants.TYPE_ADRESSE_FACTURATION));
			genereDocument.setTypeAdresseLivraison(DocumentPlugin.getDefault().getPreferenceStore().getString(fr.legrain.document.preferences.PreferenceConstants.TYPE_ADRESSE_BONLIV));
			document= (TaFacture) genereDocument.genereDocument(documentInit,new TaFacture(),codeTiers);
			try {
				message+="Code tiers : "+codeTiers+" - Document : "+document.getCodeDocument()+finDeLigne;

			} catch (Exception e) {
				ligne.append(codeTiers).append(';').append("Facture source : "+modelFacture).append(';');
				fw.write(ligne.toString());
				ligne.delete(0,ligne.length());
				fw.write(finDeLigne);
				messageErreur+="Code tiers : "+codeTiers+" - Facture source : "+modelFacture+finDeLigne;

				logger.error("",e);
			}
			if(!message.equals("")){
				if(MessageDialog.openQuestion(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(),
						"Information", "Le document : "+finDeLigne+finDeLigne+message+finDeLigne+" a été créé correctement."+
						finDeLigne+"Voulez-vous l'imprimer ?"))
					actImprimerDoc(document.getCodeDocument());
			}

			if(!messageErreur.equals("")){
				MessageDialog.openWarning(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(),
						"ATTENTION", "La génération des documents ne s'est pas déroulée correctement."
						+finDeLigne+finDeLigne+"Les documents suivants n'ont pas pu être créés"+finDeLigne+finDeLigne+
						messageErreur+finDeLigne);
			}

		}else
			MessageDialog.openWarning(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell(),
					"Attention", messageErreur);	
	}
	

	
	@Override
	protected void actAide() throws Exception {
		actAide(null);
	}
	
	
	@Override
	protected void actAide(String message) throws Exception {
	VerrouInterface.setVerrouille(true);
	try {
		vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
		ParamAfficheAideRechercheSWT paramAfficheAideRecherche = new ParamAfficheAideRechercheSWT();
//		paramAfficheAideRecherche.setDb(getThis().getIbTaTable().getFIBBase());
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
		paramAfficheAideRecherche.setForceAffichageAideRemplie(false);
		
			if (getFocusCourantSWT().equals(vue.getTfCodeFacture())) {

				TaFactureDAO dao =new TaFactureDAO(getEm());
				paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
				controllerEcranCreation = this;
				paramAfficheAideRecherche.setTypeEntite(TaFacture.class);
				paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_DOCUMENT);
				paramAfficheAideRecherche.setDebutRecherche(vue.getTfCodeFacture().getText());
				paramAfficheAideRecherche.setCleListeTitre("SWTPaEditorFactureController");
				paramAfficheAideRecherche.setControllerAppelant(getThis());
				paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaFactureDAO,TaFacture>(dao,IHMAideFacture.class));
				paramAfficheAideRecherche.setTypeObjet(IHMAideFacture.class);
				paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_DOCUMENT);
				paramAfficheAideRecherche.setAfficheDetail(false);
			}
			if (getFocusCourantSWT().equals(vue.getTfCODE_TIERS())) {
				TaTiersDAO dao =new TaTiersDAO(getEm());
				paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
//				paramAfficheAideRecherche.setJPQLQuery(new TaTiersDAO(getEm()).getJPQLQuery());
				controllerEcranCreation = this;
				paramAfficheAideRecherche.setTypeEntite(TaTiers.class);
				paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_TIERS);
				paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_TIERS().getText());
				paramAfficheAideRecherche.setCleListeTitre("SWTPaTiersController");
				paramAfficheAideRecherche.setControllerAppelant(getThis());
				paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTiers,TaTiersDAO,TaTiers>(dao,SWTTiers.class));
				paramAfficheAideRecherche.setTypeObjet(SWTTiers.class);
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

			// Ajout d'une recherche
			paAideController.addRecherche(paAideRechercheController1,paramAfficheAideRecherche.getTitreRecherche());

			// Parametrage de l'ecran d'aide principal
			ParamAfficheAideSWT paramAfficheAideSWT = new ParamAfficheAideSWT();
			ParamAfficheAide paramAfficheAide = new ParamAfficheAide();

			// enregistrement pour le retour de l'ecran d'aide
			paAideController.addRetourEcranListener(getThis());
			Control focus = vue.getShell().getDisplay().getFocusControl();
			// affichage de l'ecran d'aide principal (+ ses recherches)

			paAideController.configPanel(paramAfficheAide);

		}

	} finally {
		VerrouInterface.setVerrouille(false);
		vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
	}
}


	
	@Override
	protected void actEnregistrer() throws Exception{

	}
	
	
	@Override
	public void initEtatComposant() {}
	

	@Override
	protected void actRefresh() throws Exception {
		// TODO Raccord de méthode auto-généré
		
	}

	@Override
	protected void initMapComposantDecoratedField() {
		// TODO Raccord de méthode auto-généré
		
	}

	@Override
	protected void sortieChamps() {
		// TODO Raccord de méthode auto-généré
		
	}
	
	private class HandlerEditeurListe extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				boolean correct = false;
				String messageErreur ="";				
				correct=(rechercheFacture(vue.getTfCodeFacture().getText())!=null);
				if(!correct)messageErreur="Vous devez saisir un code de facture existant !!!";
				if(correct){
					LgrPartListener.getLgrPartListener().setLgrActivePart(null);
					IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
					openEditor(new EditorInputEditeurListeTiers(), EditorEditeurListeTiers.ID);
					LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
					LgrPartListener.getLgrPartListener().setLgrActivePart(e);

					ParamAfficheSelectionListeTiers paramAffiche = new ParamAfficheSelectionListeTiers();
					paramAffiche.setCodeTiers(vue.getTfCODE_TIERS().getText());
					paramAffiche.setModelFacture(vue.getTfCodeFacture().getText());
					((JPALgrEditorPart)e).setPanel(((JPALgrEditorPart)e).getController().getVue());
					((JPALgrEditorPart)e).getController().configPanel(paramAffiche);
				}else
					MessageDialog.openWarning(PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell(),
							"Attention", messageErreur);	
			} catch (Exception e1) {
				logger.error("Erreur : actionPerformed", e1);
			}

			return event;
		}
	}
	
//	protected void actEditeurListe() throws Exception {
//		// TODO Raccord de méthode auto-généré
//		MessageDialog.openInformation(Workbench.getInstance()
//				.getActiveWorkbenchWindow().getShell(),
//				"Information", "Editeur liste tiers");
//	}
	
	
	
	private class HandlerSelectionListe extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
//					try {
//						
//						LgrPartListener.getLgrPartListener().setLgrActivePart(null);
//						IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
//						  openEditor(new EditorInputSelectionListeTiers(), EditorSelectionListeTiers.ID);
//						LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
//						LgrPartListener.getLgrPartListener().setLgrActivePart(e);
//						
//						ParamAfficheSelectionListeTiers paramAffiche = new ParamAfficheSelectionListeTiers();
//						paramAffiche.setCodeTiers(vue.getTfCODE_TIERS().getText());
//						paramAffiche.setModelFacture(vue.getTfCodeFacture().getText());
//						((JPALgrEditorPart)e).setPanel(((JPALgrEditorPart)e).getController().getVue());
//						((JPALgrEditorPart)e).getController().configPanel(paramAffiche);
//					} catch (Exception e1) {
//						logger.error("Erreur : actionPerformed", e1);
//					}
					return event;
				}
			}

	
	public String[][] initAdapterFacture(){
		String[][] valeurs = null;
		boolean affichageCtrlEspace = GestionCommercialePlugin.getDefault().getPreferenceStore().getBoolean(fr.legrain.gestionCommerciale.preferences.PreferenceConstants.AFFICHAGE_CTRL_ESPACE);
		if(affichageCtrlEspace){
			TaFactureDAO taFactureDAO = new TaFactureDAO(getEm());
			TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
			TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();

			List<TaFacture> l = taFactureDAO.rechercheDocument(taInfoEntreprise.getDatedebInfoEntreprise(),
					taInfoEntreprise.getDatefinInfoEntreprise());
			valeurs = new String[l.size()][2];
			int i = 0;
			String description = "";
			for (TaFacture taFacture : l) {
				//			taFacture.calculeTvaEtTotaux();
				valeurs[i][0] = taFacture.getCodeDocument();

				description = "";
				description += taFacture.getLibelleDocument();
				if(taFacture.getTaTiers()!=null) {
					description += " \r\n " + taFacture.getTaTiers().getCodeTiers();
				}
				//			description += "\r\n Net TTC = " + taFacture.getNetHtCalc()
				//			+ " \r\n Net HT = " + taFacture.getNetTtcCalc()
				//			+ " \r\n Net à payer = " + taFacture.getNetAPayer()
				//			+ " \r\n Montant régler = " + taFacture.getRegleFacture();
				description += " \r\n Date = " + LibDate.dateToStringAA(taFacture.getDateDocument())
				+ " \r\n Echéance = " + LibDate.dateToStringAA(taFacture.getDateEchDocument())
				+ " \r\n Exportée = " 
				+LibConversion.booleanToStringFrancais(LibConversion.intToBoolean(taFacture.getExport()));
				valeurs[i][1] = description;

				i++;
			}
			taFactureDAO = null;
		}
		return valeurs;		
	}	


	public String[][] initAdapterTiers(){
		String[][] valeurs = null;
		boolean affichageCtrlEspace = GestionCommercialePlugin.getDefault().getPreferenceStore().getBoolean(fr.legrain.gestionCommerciale.preferences.PreferenceConstants.AFFICHAGE_CTRL_ESPACE);
		if(affichageCtrlEspace){
			TaTiersDAO taTiersDAO = new TaTiersDAO(getEm());
			List<TaTiers> l = taTiersDAO.selectAll();
			valeurs = new String[l.size()][2];
			int i = 0;
			String description = "";
			for (TaTiers taTiers : l) {
				valeurs[i][0] = taTiers.getCodeTiers();

				description = "";
				description += taTiers.getCodeCompta() + " - " + taTiers.getCompte() + " - " + taTiers.getNomTiers();
				if(taTiers.getTaTTiers()!=null) {
					description += " - " + taTiers.getTaTTiers().getLibelleTTiers();
				}
				valeurs[i][1] = description;

				i++;
			}
			taTiersDAO = null;
		}
		return valeurs;
	}

	protected void actImprimerDoc(final String doc) throws Exception {
//			try {
//				LgrPartListener.getLgrPartListener().setLgrActivePart(null);
//				PlatformUI.getWorkbench().getActiveWorkbenchWindow();
//				FacturePlugin.getDefault().getBundle();
//				vue.getParent().getDisplay().asyncExec(new Thread() {
//					public void run() {
//						try {			
//							TaFactureDAO daoFacture = new TaFactureDAO(getEm());
//							//String[] idDoc=daoFacture.rechercheFacture(doc, doc);
//							List<TaFacture> idDoc=daoFacture.rechercheDocument(doc, doc);
//							for (TaFacture taFacture : idDoc) {
//								taFacture.calculeTvaEtTotaux();
//							}
//							//impressionFacture.imprimer(idDoc,true);			
//							impressionFacture.imprimerChoix(ConstEdition.FICHE_FILE_REPORT_FACTURE,"Facture",
//									idDoc,TaFacture.class.getSimpleName());
//							
//						} catch (Exception e) {
//							logger.error("Erreur à l'impression ",e);
//						} finally {
//						}
//					}
//				});
//				
//			} catch (Exception e1) {
//				logger.error("Erreur : actionPerformed", e1);
//			}
	}
	public TaTiersDAO getDao() {
		return dao;
	}
	public void setDao(TaTiersDAO dao) {
		this.dao = dao;
	}
	
	public TaFacture rechercheFacture(String codeFacture) {
		TaFacture fact = null;
		TaFactureDAO daoFacture = new TaFactureDAO(getEm());
		fact = daoFacture.findByCode(codeFacture);
		return fact;
	}
	
	public TaTiers rechercheTiers(String codeTiers) {
		TaTiers tiers = null;
		TaTiersDAO daoTiers = new TaTiersDAO(getEm());
		tiers = daoTiers.findByCode(codeTiers);
		return tiers;
	}	
}


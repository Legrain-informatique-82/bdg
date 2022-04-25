//package fr.legrain.facture.controller;
//
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.LinkedHashMap;
//import java.util.List;
//
//import org.apache.log4j.Logger;
//import org.eclipse.jface.dialogs.MessageDialog;
//import org.eclipse.jface.fieldassist.ContentProposalAdapter;
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.events.ModifyEvent;
//import org.eclipse.swt.events.ModifyListener;
//import org.eclipse.swt.events.TypedEvent;
//import org.eclipse.swt.widgets.Composite;
//import org.eclipse.swt.widgets.Menu;
//import org.eclipse.swt.widgets.Text;
//import org.eclipse.ui.ActiveShellExpression;
//import org.eclipse.ui.PlatformUI;
//import org.eclipse.ui.contexts.IContextService;
//import org.eclipse.ui.handlers.IHandlerService;
//
//import fr.legrain.document.divers.IImpressionDocumentTiers;
//import fr.legrain.documents.dao.IDocumentTiersDAO;
//import fr.legrain.documents.dao.TaDevisDAO;
//import fr.legrain.documents.dao.TaFacture;
//import fr.legrain.documents.dao.TaFactureDAO;
//import fr.legrain.dossier.dao.TaInfoEntreprise;
//import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
//import fr.legrain.edition.actions.ConstEdition;
//import fr.legrain.facture.divers.ParamImpressionFacture;
//import fr.legrain.facture.ecran.PaImpressionFactureSWT;
//import fr.legrain.gestCom.Module_Document.IDocumentTiers;
//import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
//import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
//import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
//import fr.legrain.lib.data.AbstractLgrDAO;
//import fr.legrain.lib.data.ContentProposalProvider;
//import fr.legrain.lib.data.ExceptLgr;
//import fr.legrain.lib.data.LgrSimpleTextContentProposal;
//import fr.legrain.lib.data.LibChaine;
//import fr.legrain.lib.data.ModeObjet;
//import fr.legrain.lib.gui.DestroyEvent;
//import fr.legrain.lib.gui.ParamAffiche;
//import fr.legrain.lib.gui.ResultAffiche;
//import fr.legrain.lib.gui.RetourEcranEvent;
//import fr.legrain.lib.gui.RetourEcranListener;
//
//
//public class SWTPaImpressionFactureController extends JPABaseControllerSWTStandard implements
//RetourEcranListener {
//
//	static Logger logger = Logger.getLogger(SWTPaImpressionFactureController.class.getName());		
//	private PaImpressionFactureSWT vue = null; // vue	
//	//private SWT_IB_TA_FACTURE ibTaTable = new SWT_IB_TA_FACTURE(new SWTFacture(null));
//	private IDocumentTiersDAO dao = new TaFactureDAO();
//	private IImpressionDocumentTiers impressionFacture = null; 
//	
//	private String activationContentProposal = C_COMMAND_GLOBAL_AIDE_ID;
//	private ContentProposalAdapter codeFactureContentProposalDebut = null;
//	private ContentProposalAdapter codeFactureContentProposalFin = null;
//
//	private Object ecranAppelant = null;
//
//	public SWTPaImpressionFactureController(PaImpressionFactureSWT vue) {
//		try {
//			setVue(vue);
//			this.vue=vue;
//			vue.getShell().addShellListener(this);
//			
//			//			 Branchement action annuler : empeche la fermeture automatique de la
//			// fenetre sur ESC
//			vue.getShell().addTraverseListener(new Traverse());
//			actionImprimer.setText("Exporter [F11]");
//
//			//			vue.getPaBtn1().getBtnAnnuler().setVisible(false);
//			//			vue.getPaBtn1().getBtnEnregistrer().setVisible(false);
//			//			//vue.getPaBtn1().getBtnFermer().setVisible(false);
//			//			vue.getPaBtn1().getBtnInserer().setVisible(false);
//			//			vue.getPaBtn1().getBtnSupprimer().setVisible(false);
//			//			vue.getPaBtn1().getBtnModifier().setVisible(false);
//
//			vue.getTfDATEDEB().addModifyListener(new LgrModifyListener());
//			vue.getTfNumDeb().addModifyListener(new LgrModifyListener());
//			vue.getComboTypeDoc().addModifyListener(new LgrModifyListener());
//
//			vue.getTfDATEFIN().setEnabled(true);
//			vue.getTfNumFin().setEnabled(true);
//			
//			vue.getComboTypeDoc().setItems(new String[]{"Facture","Devis","Bon de commande",
//					"Avoir","Facture apporteur","Proforma","Bon de livraison"});
//			//			vue.getTfDATEDEB().setFocusLostBehavior(JFormattedTextField.PERSIST);
//
//			initController();
//		} catch (Exception e) {
//			logger.error("Erreur ", e);
//		}
//
//	}
//
//
//	private class LgrModifyListener implements ModifyListener{
//
//		public void modifyText(ModifyEvent e) {
//			modif(e);
//		}
//	}
//
//	private void modif(TypedEvent e) {
//		try {
//			if(e.getSource().equals(vue.getTfNumDeb())) {
//				if(!LibChaine.empty(vue.getTfNumDeb().getText())) {
//					vue.getTfNumFin().setEnabled(true);
//					vue.getTfDATEDEB().setSelection(null); vue.getTfDATEDEB().setEnabled(false);
//					vue.getTfDATEFIN().setSelection(null); vue.getTfDATEFIN().setEnabled(false);
//				} else {
//					vue.getTfNumFin().setText(""); vue.getTfNumFin().setEnabled(false);
//					vue.getTfDATEDEB().setSelection(new Date()); vue.getTfDATEDEB().setEnabled(true);
//					vue.getTfDATEFIN().setSelection(new Date()); vue.getTfDATEFIN().setEnabled(true);
//				}
//			}
//			if(e.getSource().equals(vue.getTfDATEDEB())) {
//				if(!LibChaine.empty(vue.getTfDATEDEB().getText())) {
//					vue.getTfDATEFIN().setEnabled(true);
//					vue.getTfNumDeb().setText(""); vue.getTfNumDeb().setEnabled(false);
//					vue.getTfNumFin().setText(""); vue.getTfNumFin().setEnabled(false);
//				} else {
//					vue.getTfDATEDEB().setSelection(null);
//					vue.getTfDATEFIN().setSelection(null); vue.getTfDATEFIN().setEnabled(false);
//					vue.getTfNumDeb().setEnabled(true);
//				}
//			}
//			if(e.getSource().equals(vue.getComboTypeDoc())) {
//				int typeDocSelection = vue.getComboTypeDoc().getSelectionIndex();
//				if(typeDocSelection==0) {
//					//initialisé le dao
//					dao = new TaFactureDAO();
//					setDaoStandard(((AbstractLgrDAO) dao));
//					//initialisé les content assist
//	
//				} else {
//					dao = new TaDevisDAO();
//					setDaoStandard(((AbstractLgrDAO) dao));
//				}
//				codeFactureContentProposalDebut.setContentProposalProvider(contentProposalProviderDocument());
//				codeFactureContentProposalFin.setContentProposalProvider(contentProposalProviderDocument());
//
//			}
//		} catch (Exception e1) {
//			logger.error(e1);
//		}		
//	}
//	private void initController() {
//		try {
//			setDaoStandard(((AbstractLgrDAO) dao));
//			vue.getCbReExport().setSelection(true);
//			//			ibTaTableStandard=dao;
//			//			addDestroyListener(dao);
//
//
//			initMapComposantChamps();
//			initMapComposantDecoratedField();
//			listeComponentFocusableSWT(listeComposantFocusable);
//			initFocusOrder();
//			initActions();
//			initDeplacementSaisie(listeComposantFocusable);
//
//			branchementBouton();
//
//			Menu popupMenuFormulaire = new Menu(vue.getShell(), SWT.POP_UP);
//			Menu popupMenuGrille = new Menu(vue.getShell(), SWT.POP_UP);
//			Menu[] tabPopups = new Menu[] { popupMenuFormulaire,
//					popupMenuGrille };
//			this.initPopupAndButtons(mapActions, tabPopups);
//
//
//			initEtatBouton();
//		} catch (Exception e) {
//			//			vue.getLaMessage().setText(e.getMessage());
//			logger.error("Erreur : PaArticlesController", e);
//		}
//	}
//
//	@Override
//	protected void initImageBouton() {
//		vue.getPaBtn1().getBtnFermer().setImage(LibrairiesEcranPlugin.getImageDescriptor(C_IMAGE_FERMER).createImage());
//		vue.getPaBtn1().getBtnImprimer().setImage(LibrairiesEcranPlugin.getImageDescriptor(C_IMAGE_IMPRIMER).createImage());
//		vue.layout(true);
//	}
//
//
//	public Composite getVue() {return vue;}
//
//	public ResultAffiche configPanel(ParamAffiche param){
//		if (param!=null){
//			if (((ParamImpressionFacture)param).getFocusDefaut()!=null)
//				((ParamImpressionFacture)param).getFocusDefaut().requestFocus();
//
//			if(param.getEcranAppelant()!=null) {
//				ecranAppelant = param.getEcranAppelant();
//			}
//			//			if(param.getModeEcran()!=null  
//			//					&& param.getModeEcran().compareTo(EnumModeObjet.C_MO_INSERTION)==0) {
//			//				actionInserer.run();
//			//				initEtatBouton();
//			//			} else if(ibTaTable.getFIBQuery().getRowCount()<=0) {
//			//				actionInserer.run();
//			//				initEtatBouton();
//			//			}
//			param.setFocus(((AbstractLgrDAO) dao).getModeObjet().getFocusCourant());
//		}
//		return null;
//	}
//
//	/**
//	 * Initialisation des composants graphiques de la vue.
//	 * @throws ExceptLgr 
//	 */
//	protected void initComposantsVue() throws ExceptLgr {
//	}
//
//	/**
//	 * Initialisation des boutons suivant l'état de l'objet "ibTaTable"
//	 */
//	protected void initEtatBouton() {
//		super.initEtatBouton();
//		boolean trouve =false;
//
//		trouve = daoStandard.selectCount()>0;
//
//		switch (((AbstractLgrDAO) dao).getModeObjet().getMode()) {
//		case C_MO_INSERTION:
//			actionInserer.setEnabled(false);
//			actionModifier.setEnabled(false);
//			actionEnregistrer.setEnabled(true);
//			actionAnnuler.setEnabled(true);
//			actionImprimer.setEnabled(false);
//			actionFermer.setEnabled(true);
//			actionSupprimer.setEnabled(false);
//			actionAide.setEnabled(true);
//			break;
//		case C_MO_EDITION:
//			actionInserer.setEnabled(false);
//			actionModifier.setEnabled(false);
//			actionEnregistrer.setEnabled(true);
//			actionAnnuler.setEnabled(true);
//			actionImprimer.setEnabled(false);
//			actionFermer.setEnabled(true);
//			actionSupprimer.setEnabled(false);
//			actionAide.setEnabled(true);
//			break;
//		case C_MO_CONSULTATION:
//			actionInserer.setEnabled(!trouve);
//			actionModifier.setEnabled(trouve);
//			actionEnregistrer.setEnabled(false);
//			actionAnnuler.setEnabled(true);
//			actionImprimer.setEnabled(true);
//			actionFermer.setEnabled(true);
//			actionSupprimer.setEnabled(false);
//			actionAide.setEnabled(true);
//			break;
//		default:
//			break;
//		}
//
//		initFocusSWT(((AbstractLgrDAO) dao),mapInitFocus);
//	}	
//
//
//	/**
//	 * Initialisation des correspondances entre les champs de formulaire et les
//	 * champs de bdd
//	 */
//	protected void initMapComposantChamps() {
//		if (listeComposantFocusable == null) 
//			listeComposantFocusable = new ArrayList();
//
//		if (mapComposantChamps == null) 
//			mapComposantChamps = new LinkedHashMap();
//		listeComposantFocusable.add(vue.getTfNumDeb());
//		listeComposantFocusable.add(vue.getTfNumFin());
//		listeComposantFocusable.add(vue.getTfDATEDEB());
//		listeComposantFocusable.add(vue.getTfDATEFIN());			
//
//		listeComposantFocusable.add(vue.getPaBtn1().getBtnImprimer());
//		listeComposantFocusable.add(vue.getPaBtn1().getBtnFermer());
//
//		if(mapInitFocus == null) 
//			mapInitFocus = new LinkedHashMap();
//		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION,vue.getTfNumDeb());
//		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION,vue.getTfNumDeb());
//		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION,vue.getTfNumDeb());
//
//		//		activeDocumentListener();
//
//		//String activationContentProposal = "Ctrl+Space";
//		String activationContentProposal = C_COMMAND_GLOBAL_AIDE_ID;
//		codeFactureContentProposalDebut = LgrSimpleTextContentProposal.defaultTextContentProposalCommand(vue.getTfNumDeb(),activationContentProposal,initContentProposalFacture(),null);
//		LgrSimpleTextContentProposal.defaultOptions(codeFactureContentProposalDebut);
//
//		codeFactureContentProposalFin = LgrSimpleTextContentProposal.defaultTextContentProposalCommand(vue.getTfNumFin(),activationContentProposal,initContentProposalFacture(),null);
//		LgrSimpleTextContentProposal.defaultOptions(codeFactureContentProposalFin);
//
//	}
//
//	public String[][] initContentProposalFacture(){
//
//		String[][] valeurs = null;
////		List<IDocumentTiers> l = ((AbstractLgrDAO) dao).selectAll();
//		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO();
//		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
//		List<IDocumentTiers> l = ((IDocumentTiersDAO) dao).rechercheDocument(taInfoEntreprise.getDatedebInfoEntreprise(),
//				taInfoEntreprise.getDatefinInfoEntreprise());		
//		valeurs = new String[l.size()][2];
//		int i = 0;
//		String description = "";
//		for (IDocumentTiers doc : l) {
//			valeurs[i][0] = doc.getCodeDocument();
//
//			description = "";
//			description += doc.getLibelleDocument();
//			valeurs[i][1] = description;
//
//			i++;
//		}
//		return valeurs;		
//	}
//	
//	public ContentProposalProvider contentProposalProviderDocument(){
//		String[][] adapterFacture = initContentProposalFacture();
//		String[] listeCodeFacture = null;
//		String[] listeLibelleFacture = null;
//		if (adapterFacture != null) {
//			listeCodeFacture = new String[adapterFacture.length];
//			listeLibelleFacture = new String[adapterFacture.length];
//			for (int i = 0; i < adapterFacture.length; i++) {
//				listeCodeFacture[i] = adapterFacture[i][0];
//				listeLibelleFacture[i] = adapterFacture[i][1];
//			}
//		}
//
//		return new ContentProposalProvider(listeCodeFacture,
//				listeLibelleFacture);
//		
//	}
//
//	@Override
//	public void initCommands(){
//		if(handlerService == null)
//			handlerService = (IHandlerService)PlatformUI.getWorkbench().getService(IHandlerService.class);
//		if(contextService == null)
//			contextService = (IContextService) PlatformUI.getWorkbench().getService(IContextService.class);
//		contextService.registerShell(vue.getShell(),IContextService.TYPE_DIALOG);
//		activeShellExpression = new ActiveShellExpression(vue.getShell());
//
////		handlerService.activateHandler(C_COMMAND_GLOBAL_AIDE_ID, handlerAide,activeShellExpression);
//		handlerService.activateHandler(C_COMMAND_GLOBAL_ANNULER_ID, handlerAnnuler, activeShellExpression);
//		//handlerService.activateHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID, handlerEnregistrer, activeShellExpression);
//		handlerService.activateHandler(C_COMMAND_GLOBAL_FERMER_ID, handlerFermer, activeShellExpression);
//		handlerService.activateHandler(C_COMMAND_GLOBAL_IMPRIMER_ID, handlerImprimer, activeShellExpression);
//		//handlerService.activateHandler(C_COMMAND_GLOBAL_INSERER_ID, handlerInserer, activeShellExpression);
//		//handlerService.activateHandler(C_COMMAND_GLOBAL_MODIFIER_ID, handlerModifier, activeShellExpression);
//		//handlerService.activateHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID, handlerSupprimer, activeShellExpression);	
//		//handlerService.activateHandler(C_COMMAND_GLOBAL_REFRESH_ID, handlerRefresh, activeShellExpression);
//	}
//
//	protected void initActions() {
//		initCommands();
//		if (mapActions == null)
//			mapActions = new LinkedHashMap();
//
//		mapActions.put(vue.getPaBtn1().getBtnFermer(), actionFermer);
//		mapActions.put(vue.getPaBtn1().getBtnImprimer(), actionImprimer);
//
//		Object[] tabActionsAutres = new Object[] { actionAide };
//		mapActions.put(null, tabActionsAutres);
//	}
//
//	public SWTPaImpressionFactureController getThis(){
//		return this;
//	}
//
//	@Override
//	public boolean onClose() throws ExceptLgr {
//		boolean retour = true;
//		switch (((AbstractLgrDAO) dao).getModeObjet().getMode()) {
//		case C_MO_INSERTION:
//		case C_MO_EDITION:
//			break;
//		case C_MO_CONSULTATION:
//			break;
//		default:
//			break;
//		}
//
//		if (retour) {
//			if (ecranAppelant instanceof SWTPaAideControllerSWT) {
//				//				fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
//				//						dao.champIdTable, dao.valeurIdTable,
//				//						null)));
//
//				retour = false;
//			}
//		}
//		if (retour && !(ecranAppelant instanceof SWTPaAideControllerSWT)) {
//			fireDestroy(new DestroyEvent(dao));
//		}
//		return retour;
//	}
//
//
//	public void retourEcran(RetourEcranEvent evt) {
//		if (evt.getRetour() != null
//				&& (evt.getSource() instanceof SWTPaAideControllerSWT)) {
//			if (getFocusAvantAideSWT() instanceof Text) {
//				try {
//					((Text) getFocusAvantAideSWT()).setText(((ResultAffiche) evt
//							.getRetour()).getResult());					
//					ctrlUnChampsSWT(getFocusAvantAideSWT());
//				} catch (Exception e) {
//					logger.error("",e);
//					//					vue.getLaMessage().setText(e.getMessage());
//				}
//			}
//		}
//		super.retourEcran(evt);
//	}
//
//	@Override
//	protected void actInserer() throws Exception{}
//
//	@Override
//	protected void actModifier() throws Exception{}
//
//	@Override
//	protected void actSupprimer() throws Exception{}
//
//	@Override
//	protected void actFermer() throws Exception {
//		// (controles de sortie et fermeture de la fenetre) => onClose()
//		vue.getShell().close();
//	}
//
//	@Override
//	protected void actAnnuler() throws Exception{
//		actionFermer.run();
//	}
//
//	@Override
//	protected void actImprimer() throws Exception{
//		//Récupération des paramètres dans l'ihm
//		//String[] idFactureAImprimer = null;
//		List<TaFacture> listFactureAImprimer = null;
//		boolean preview = vue.getCbReExport().getSelection();
//		Date dateDeb = null;
//		Date dateFin = null;
//		String codeDeb = null;
//		String codeFin = null;
//
//		if(!LibChaine.empty(vue.getTfNumDeb().getText())) {
//			codeDeb = vue.getTfNumDeb().getText();
//		}
//		if(!LibChaine.empty(vue.getTfNumFin().getText())) {
//			codeFin = vue.getTfNumFin().getText();
//		}
//		if( (!LibChaine.empty(vue.getTfDATEDEB().getText()) && 
//				!vue.getTfDATEDEB().getText().equals("<choisir une date>") && 
//				vue.getTfDATEDEB().getText()!=null) ) {
//			dateDeb = vue.getTfDATEDEB().getSelection();
//		}
//		if( (!LibChaine.empty(vue.getTfDATEFIN().getText())&& 
//				!vue.getTfDATEFIN().getText().equals("<choisir une date>")
//				&& vue.getTfDATEFIN().getText()!=null) ) {
//			dateFin = vue.getTfDATEFIN().getSelection();
//		}
//
//		if(codeDeb!=null) {//1 code => cette facture
//			logger.debug("Exportation - selection par code");
//			if(codeFin!=null) {//2 codes => factures entre les 2 codes si intervalle correct
//				listFactureAImprimer = dao.rechercheDocument(codeDeb,codeFin);
//			} else {
//				codeFin = codeDeb;
//				listFactureAImprimer = dao.rechercheDocument(codeDeb,codeFin);
//			}
//		} else if(dateDeb!=null) {//1 date => toutes les factures à cette date
//			logger.debug("Exportation - selection par date");
//			if(dateFin!=null) {//2 dates => factures entre les 2 dates si intervalle correct
//				if(dateDeb.compareTo(dateFin)>0) {
//					MessageDialog.openWarning(PlatformUI.getWorkbench()
//							.getActiveWorkbenchWindow().getShell(),
//							"ATTENTION", "La date de début doit être antérieure à la date fin");
//					throw new Exception("probleme intervalle des dates");
//				}
//				listFactureAImprimer = dao.rechercheDocument(dateDeb,dateFin);
//			} else {
//				dateFin = dateDeb;
//				listFactureAImprimer = dao.rechercheDocument(dateDeb,dateFin);
//			}
//		} 
//		for (TaFacture taFacture : listFactureAImprimer) {
//			taFacture.calculeTvaEtTotaux();
//		}
//		///////////////////////////////////////
//		//final String[] finalIdFactureAImprimer = listFactureAImprimer;
//		final List<TaFacture> finalListFactureAImprimer = listFactureAImprimer;
//		final boolean finalPreview = preview;
//		String fichierEdition = null;
//		String nomOnglet = null;
//		if(vue.getComboTypeDoc().getSelectionIndex()==0) {
//			impressionFacture = new fr.legrain.facture.divers.Impression(vue.getShell());
//			fichierEdition = ConstEdition.FICHE_FILE_REPORT_FACTURE;
//			nomOnglet = "Facture";
//		} 
//		final String finalFichierEdition = fichierEdition;
//		final String finalNomOnglet = nomOnglet;
//		vue.getParent().getDisplay().asyncExec(new Thread() {
//			public void run() {
//				try {	
//					
//					
//					//impressionFacture.imprimerSelection(finalListFactureAImprimer,finalPreview);
//					//#JPA
//					//					dao.commitLgr();
//					impressionFacture.imprimerChoix(finalFichierEdition,finalNomOnglet,
////					impressionFacture.imprimerChoix(ConstEdition.FICHE_FILE_REPORT_DEVIS,"Facture",
//															finalListFactureAImprimer);
//					
//				} catch (Exception e) {
//					logger.error("Erreur à l'impression ",e);
//				} finally {
//				}
//			}
//		});
//		//		vue.getShell().setVisible(false);
//		actFermer();
//
//	}
//
//	//	protected void imprimer(String[] idFactureAImprimer,boolean preview) throws Exception{		
//	//		//Chemin de l'edition
//	//		Bundle bundleCourant = FacturePlugin.getDefault().getBundle();
//	//		URL urlReportFile = Platform.asLocalURL(bundleCourant.getEntry("/report/facture.rptdesign"));
//	//		URI uriReportFile = new URI("file",urlReportFile.getAuthority(),urlReportFile.getPath(),urlReportFile.getQuery(),null);
//	//		File reportFile = new File(uriReportFile);
//	//		
//	//		//Preparation de l'edition
//	//		BirtUtil.startReportEngine();
//	//		
//	//		SWTInfoEntreprise infoEntreprise = SWT_IB_TA_INFO_ENTREPRISE.infosEntreprise("1",null);
//	//		
//	//		HashMap<String,String> reportParam = new HashMap<String,String>();
//	//		reportParam.put("paramUrlJDBC",IB_APPLICATION.findDatabase().getConnection().getConnectionURL());
//	//		reportParam.put("capital",infoEntreprise.getCAPITAL_INFO_ENTREPRISE());
//	//		reportParam.put("ape",infoEntreprise.getAPE_INFO_ENTREPRISE());
//	//		reportParam.put("siret",infoEntreprise.getSIRET_INFO_ENTREPRISE());
//	//		reportParam.put("rcs",infoEntreprise.getRCS_INFO_ENTREPRISE());
//	//		reportParam.put("nomEntreprise",infoEntreprise.getNOM_INFO_ENTREPRISE());
//	//
//	//		
//	//		
//	//		LgrSpooler spooler = LgrSpooler.getInstance();
//	//
//	//		final String[] finalIdFactureAImprimer = idFactureAImprimer;
//	//		final File finalReportFile = reportFile;
//	//		final HashMap finalReportParam = reportParam;
//	//		final LgrSpooler finalSpooler = spooler;
//	//		Job job = new Job("Préparation de l'impression") {
//	//			protected IStatus run(IProgressMonitor monitor) {
//	//				final int ticks = finalIdFactureAImprimer.length;
//	//				monitor.beginTask("Préparation de l'impression", ticks);
//	//				try {
//	//					OutputStream os = null;
//	//					for (int i = 0; i < finalIdFactureAImprimer.length; i++) {
//	//						finalReportParam.put("paramID_DOC",finalIdFactureAImprimer[i]);
//	//						os = BirtUtil.renderReportToStream(finalReportFile.getAbsolutePath(),finalReportParam,BirtUtil.PDF_FORMAT);
//	//						finalSpooler.add(os);
//	//						monitor.worked(1);
//	//						if (monitor.isCanceled()) {
//	//							finalSpooler.clear();
//	//							return Status.CANCEL_STATUS;
//	//						}
//	//					}
//	//				} finally {
//	//					monitor.done();
//	//				}
//	//				return Status.OK_STATUS;
//	//			}
//	//		};
//	//		job.setPriority(Job.SHORT);
//	//		//job.setUser(true);
//	//		job.schedule(); 
//	//		job.join();
//	//		
//	//		//Impression
//	//		if(job.getResult()==Status.OK_STATUS)
//	//			spooler.print(preview);
//	//		
//	//		BirtUtil.destroyReportEngine();
//	//	}
//
//
//
//	@Override
//	protected void actAide() throws Exception {
//		actAide(null);
//	}
//
//
//	@Override
//	protected void actAide(String message) throws Exception {
////		boolean verrouLocal=VerrouInterface.isVerrouille();
////		VerrouInterface.setVerrouille(true);
////		try {
////			vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
////			ParamAfficheAideRechercheSWT paramAfficheAideRecherche = new ParamAfficheAideRechercheSWT();
////			paramAfficheAideRecherche.setMessage(message);
////			// Creation de l'ecran d'aide principal
////			Shell s = new Shell(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), LgrShellUtil.styleLgr);
////			PaAideSWT paAide = new PaAideSWT(s, SWT.NONE);
////			SWTPaAideControllerSWT paAideController = new SWTPaAideControllerSWT(paAide);
////			/***************************************************************/
////			LgrPartListener.getLgrPartListener().setLgrActivePart(null);
////			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new EditorInputAide(), EditorAide.ID);
////			LgrPartListener.getLgrPartListener().setLgrActivePart(e);
////			LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
////			paAideController = new SWTPaAideControllerSWT(((EditorAide)e).getComposite());
////			((LgrEditorPart)e).setController(paAideController);
////			((LgrEditorPart)e).setPanel(((EditorAide)e).getComposite());
////			/***************************************************************/
////			JPABaseControllerSWTStandard controllerEcranCreation = null;
////			ParamAffiche parametreEcranCreation = null;
////			IEditorPart editorCreation = null;
////			String editorCreationId = null;
////			IEditorInput editorInputCreation = null;
////			Shell s2 = new Shell(s, LgrShellUtil.styleLgr);
////
////
////			if (getThis().getFocusCourantSWT()==vue.getTfNumDeb()
////					|| getThis().getFocusCourantSWT()==vue.getTfNumFin()){
////
////				//							SWTFacture swtFacture = new SWTFacture("") ;
////				//							SWT_IB_TA_FACTURE ibTaTableFacture = new SWT_IB_TA_FACTURE(swtFacture,false);
////				//							ibTaTableFacture.getFIBQuery().close();
////				//							ibTaTableFacture.getFIBQuery().setQuery(new QueryDescriptor(ibTaTableFacture.getFIBBase(),Const.C_Debut_Requete+ Const.C_NOM_VU_FACTURE_REDUIT,true));
////				//							ibTaTableFacture.ouvreDataset();
////				//							paramAfficheAideRecherche.setQuery(ibTaTableFacture.getFIBQuery());
////				//							controllerEcranCreation = null;
////				//		
////				//							paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_FACTURE);
////				//							if(getThis().getFocusCourantSWT()==vue.getTfNumDeb())
////				//							  paramAfficheAideRecherche.setDebutRecherche(vue.getTfNumDeb().getText());
////				//							else
////				//								if(getThis().getFocusCourantSWT()==vue.getTfNumFin())
////				//									paramAfficheAideRecherche.setDebutRecherche(vue.getTfNumFin().getText());
////				//							paramAfficheAideRecherche.setControllerAppelant(getThis());
////				//							paramAfficheAideRecherche.setModel(new ModelGeneral<IHMAideFacture>(ibTaTableFacture.getFIBQuery(),IHMAideFacture.class));
////				//							paramAfficheAideRecherche.setTypeObjet(IHMAideFacture.class);
////				//							paramAfficheAideRecherche.setChampsIdentifiant(ibTaTableFacture.champIdTable);
////				//							paramAfficheAideRecherche.setCleListeTitre("SWTPaEditorFactureController");
////				///////
////				if(vue.getComboTypeDoc().getSelectionIndex()==0) {
////					PaEditorFactureSWT paEditorFactureSWT = new PaEditorFactureSWT(s2,SWT.NULL);
////					SWTPaEditorFactureController swtPaEditorFactureController = new SWTPaEditorFactureController(paEditorFactureSWT);
////
////					editorCreationId = EditorFacture.ID_EDITOR;
////					editorInputCreation = new EditorInputFacture();
////
////					paramAfficheAideRecherche.setJPQLQuery(((AbstractLgrDAO) dao).getJPQLQuery());
////					controllerEcranCreation = this;
////
////					paramAfficheAideRecherche.setTypeEntite(TaFacture.class);
////					paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_DOCUMENT);
////					paramAfficheAideRecherche.setControllerAppelant(SWTPaImpressionFactureController.this);
////					paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaFactureDAO,TaFacture>(((AbstractLgrDAO) dao),IHMAideFacture.class));
////					paramAfficheAideRecherche.setTypeObjet(IHMAideFacture.class);
////					paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_DOCUMENT);
////
////				}
////
////				//							ParamAfficheArticles paramAfficheArticles = new ParamAfficheArticles();
////				//							paramAfficheAideRecherche.setJPQLQuery(((AbstractLgrDAO) dao).getJPQLQuery());
////				//							paramAfficheArticles.setModeEcran(EnumModeObjet.C_MO_INSERTION);
////				//							paramAfficheArticles.setEcranAppelant(paAideController);
////				//							controllerEcranCreation = swtPaArticlesController;
////				//							parametreEcranCreation = paramAfficheArticles;
////
////				//							paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_ARTICLE);
////				if(getThis().getFocusCourantSWT()==vue.getTfNumDeb())
////					paramAfficheAideRecherche.setDebutRecherche(vue.getTfNumDeb().getText());
////				else
////					if(getThis().getFocusCourantSWT()==vue.getTfNumFin())
////						paramAfficheAideRecherche.setDebutRecherche(vue.getTfNumFin().getText());
////				//							paramAfficheAideRecherche.setControllerAppelant(SWTPaImpressionFactureController.this);
////				//							paramAfficheAideRecherche.setModel(swtPaArticlesController.getModelArticle());
////				//							paramAfficheAideRecherche.setTypeObjet(swtPaArticlesController.getClassModel());
////				//							paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_ARTICLE);
////			}
////
////
////			if (paramAfficheAideRecherche.getJPQLQuery() != null) {
////
////				PaAideRechercheSWT paAideRecherche1 = new PaAideRechercheSWT(s,
////						SWT.NULL);
////				SWTPaAideRechercheControllerSWT paAideRechercheController1 = new SWTPaAideRechercheControllerSWT(
////						paAideRecherche1);
////
////				// Parametrage de la recherche
////				paramAfficheAideRecherche.setFocusSWT(((PaAideRechercheSWT) paAideRechercheController1.getVue()).getTfChoix());
////				paramAfficheAideRecherche.setRefCreationSWT(controllerEcranCreation);
////				paramAfficheAideRecherche.setEditorCreation(editorCreation);
////				paramAfficheAideRecherche.setEditorCreationId(editorCreationId);
////				paramAfficheAideRecherche.setEditorInputCreation(editorInputCreation);
////				paramAfficheAideRecherche.setParamEcranCreation(parametreEcranCreation);
////				paramAfficheAideRecherche.setShellCreation(s2);
////				paAideRechercheController1.configPanel(paramAfficheAideRecherche);
////
////				// paramAfficheAideRecherche.setFocusDefaut(paAideRechercheController1.getVue().getTfChoix());
////
////				// Ajout d'une recherche
////				paAideController.addRecherche(paAideRechercheController1,
////						paramAfficheAideRecherche.getTitreRecherche());
////
////				// Parametrage de l'ecran d'aide principal
////				ParamAfficheAideSWT paramAfficheAideSWT = new ParamAfficheAideSWT();
////				ParamAfficheAide paramAfficheAide = new ParamAfficheAide();
////
////				// enregistrement pour le retour de l'ecran d'aide
////				paAideController.addRetourEcranListener(SWTPaImpressionFactureController.this);
////				Control focus = vue.getShell().getDisplay().getFocusControl();
////				// affichage de l'ecran d'aide principal (+ ses recherches)
////
////				//				dbc.getValidationStatusMap().removeMapChangeListener(changeListener);
////				//LgrShellUtil.afficheAideSWT(paramAfficheAide, null, paAide,paAideController, s);
////				paAideController.configPanel(paramAfficheAide);
////				//				dbc.getValidationStatusMap().addMapChangeListener(changeListener);
////
////			}
////
////		} finally {
////			VerrouInterface.setVerrouille(false);
////			vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
////		}
//	}
//
//	@Override
//	protected void actEnregistrer() throws Exception{}
//
//	//	public SWT_IB_TA_FACTURE getDao() {
//	//		return dao;
//	//	}
//
//	public AbstractLgrDAO getDao() {
//		return ((AbstractLgrDAO) dao);
//	}
//
//	@Override
//	public void initEtatComposant() {}
//
//
//	@Override
//	protected void actRefresh() throws Exception {
//		// TODO Raccord de méthode auto-généré
//
//	}
//
//	@Override
//	protected void initMapComposantDecoratedField() {
//		// TODO Raccord de méthode auto-généré
//
//	}
//
//	@Override
//	protected void sortieChamps() {
//		// TODO Raccord de méthode auto-généré
//
//	}
//
//}

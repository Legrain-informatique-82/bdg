package fr.legrain.generationdocumentLGR.controllers;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.IContentProposalListener;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ActiveShellExpression;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.internal.Workbench;

import fr.legrain.document.DocumentPlugin;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaTPaiement;
import fr.legrain.documents.dao.TaTPaiementDAO;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.facture.FacturePlugin;
import fr.legrain.facture.divers.Impression;
import fr.legrain.facture.preferences.PreferenceConstants;
import fr.legrain.generationDocumentLGR.dao.TaWlgr;
import fr.legrain.generationDocumentLGR.dao.TaWlgrDAO;
import fr.legrain.generationDocumentLGR.dao.TaWlgrNMoins1;
import fr.legrain.generationDocumentLGR.dao.TaWlgrNMoins1DAO;
import fr.legrain.generationdocument.divers.GenerationDocument;
import fr.legrain.generationdocumentLGR.divers.ParamAfficheEditeurListeTiers;
import fr.legrain.generationdocumentLGR.ecran.PaEditeurListeTiers;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Document.SWTTPaiement;
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
import fr.legrain.gestCom.librairiesEcran.workbench.AbstractLgrMultiPageEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.ContentProposalProvider;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.LibChaine;
import fr.legrain.lib.data.LibConversion;
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
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.divers.ParamAfficheTPaiement;
import fr.legrain.tiers.ecran.PaTypePaiementSWT;
import fr.legrain.tiers.ecran.SWTPaTypePaiementController;
import fr.legrain.tiers.editor.EditorInputTypePaiement;
import fr.legrain.tiers.editor.EditorTypePaiement;



public class PaEditeurListeTiersNMoins1Controller extends JPABaseControllerSWTStandard implements
RetourEcranListener {
	
	static Logger logger = Logger.getLogger(PaEditeurListeTiersNMoins1Controller.class.getName());		
	private PaEditeurListeTiers vue = null; // vue
	private TaTiersDAO dao = null;//new TaTiersDAO();
	private TaWlgrNMoins1DAO wlgrDao = null;//new TaWlgrDAO();
	private Object ecranAppelant = null;
	private String LibelleFacture = "";
	private String commentaire = null;
	private String codeTiers = "";
	private String finDeLigne = "\r\n";
	private Impression impressionFacture = null ;
	private fr.legrain.facture.divers.Impression impressionGeneration = null ;
	private BigDecimal totalFacture = new BigDecimal(0); 
	private Integer nbFactures = 0;
	
	public static final String C_COMMAND_GENERATIONDOCUMENT_RAJOUTER_ID = "fr.legrain.generationdocumentLGR.Rajouter";
	protected HandlerRajouter handlerRajouter = new HandlerRajouter();

	public static final String C_COMMAND_GENERATIONDOCUMENT_REINITIALISER_ID = "fr.legrain.generationdocumentLGR.Reinitialiser";
	protected HandlerReinitialiser handlerReinitialiser = new HandlerReinitialiser();

	public static final String C_COMMAND_GENERATIONDOCUMENT_CHARGERFICHIER_ID = "fr.legrain.generationdocumentLGR.ChargerFichier";
    protected HandlerChargerFichier handlerChargerFichier = new HandlerChargerFichier();

	public static final String C_COMMAND_GENERATIONDOCUMENT_REMONTERDOC_ID = "fr.legrain.generationdocumentLGR.RemonterDoc";
    protected HandlerRemonterDoc	 handlerRemonterDoc = new HandlerRemonterDoc	();
	
	public static final String C_COMMAND_GENERATIONDOCUMENT_IMPRIMERDOC_ID = "fr.legrain.generationdocumentLGR.ImprimerDoc";
    protected HandlerImprimerDoc	 handlerImprimerDoc = new HandlerImprimerDoc	();
	
    public static final String C_COMMAND_GENERATIONDOCUMENT_REINITDOCCREE_ID = "fr.legrain.generationdocumentLGR.ReinitDocCree";
	protected HandlerReinitDocCree handlerReinitDocCree = new HandlerReinitDocCree();

    public static final String C_COMMAND_GENERATIONDOCUMENT_IMPRESSIONDIVERSES_ID = "fr.legrain.generationdocumentLGR.ImpressionDiverses";
	protected HandlerImpressionDiverses handlerImpressionDiverses = new HandlerImpressionDiverses();
	//
    private static final String END_STYLES_MARK = "***EndStyles***";
	  private static final String START_STYLES_MARK = "***Styles***";
	  private static final String START_TEXT_MARK = "***Text***";
	  private static  String FILE_NAME = Platform.getInstanceLocation().getURL().getFile()+"/CodeTiers.TXT";;

	  private boolean doBold = false;
	
	String[] listeCodeTiers=null;
	String[] listeDescriptionTiers=null;

	private ContentProposalAdapter tiersContentProposal;
	
	public PaEditeurListeTiersNMoins1Controller(PaEditeurListeTiers vue) {
		this(vue,null);
	}

	public PaEditeurListeTiersNMoins1Controller(PaEditeurListeTiers vue,EntityManager em) {
		try{
			vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
		if(em!=null) {
			setEm(em);
		}else
			setEm(new TaWlgrNMoins1DAO().getEntityManager());
		dao = new TaTiersDAO(getEm());
		wlgrDao = new TaWlgrNMoins1DAO(getEm());
		try {
			setVue(vue);

			this.vue=vue;
			vue.getShell().addShellListener(this);
			impressionFacture= new Impression(vue.getShell());
			impressionGeneration= new fr.legrain.facture.divers.Impression(vue.getShell());
//			Branchement action annuler : empeche la fermeture automatique de la
			// fenetre sur ESC
			vue.getShell().addTraverseListener(new Traverse());
			actionImprimer.setText("Valider[F3]");

			initController();
		} catch (Exception e) {
			logger.error("Erreur ", e);
		}
		}finally{
			vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
		}
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
			codeTiers = ((ParamAfficheEditeurListeTiers)param).getCodeTiers();
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
				actionImprimer.setEnabled(true);
				actionAnnuler.setEnabled(true);
				actionFermer.setEnabled(true);
				break;
			case C_MO_EDITION:
				actionImprimer.setEnabled(true);
				actionAnnuler.setEnabled(true);
				actionFermer.setEnabled(true);
				break;
			case C_MO_CONSULTATION:
				actionImprimer.setEnabled(true);
				actionAnnuler.setEnabled(true);
				actionFermer.setEnabled(true);
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
		listeComposantFocusable.add(vue.getTfLibelle());
		listeComposantFocusable.add(vue.getTfDateDocument());
		listeComposantFocusable.add(vue.getTfTPaiement());
		
		listeComposantFocusable.add(vue.getTfRecherche());
		listeComposantFocusable.add(vue.getTfListeTiers());
		listeComposantFocusable.add(vue.getTfStyleEditeur());
		listeComposantFocusable.add(vue.getTfListeDoc());
		
		listeComposantFocusable.add(vue.getBtnRajouter());
		listeComposantFocusable.add(vue.getBtnReinitialiser());

		listeComposantFocusable.add(vue.getBtnChargerFichier());

		listeComposantFocusable.add(vue.getPaBtn1().getBtnImprimer());
		listeComposantFocusable.add(vue.getPaBtn1().getBtnFermer());
		
		listeComposantFocusable.add(vue.getBtnRemonterDoc());
		listeComposantFocusable.add(vue.getBtnImprimerDoc());
		listeComposantFocusable.add(vue.getBtnReinitDocCree());
		
		if(mapInitFocus == null) 
			mapInitFocus = new LinkedHashMap();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION,vue.getTfLibelle());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION,vue.getTfLibelle());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION,vue.getTfLibelle());
	
		vue.getTfListeTiers().addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				try {
					handlerRajouter.execute(new ExecutionEvent());
				} catch (ExecutionException e1) {
					logger.error("",e1);
				}				
			}

			@Override
			public void mouseDown(MouseEvent e) {}

			@Override
			public void mouseUp(MouseEvent e) {}			
		});
	    vue.getTfRecherche().addModifyListener(new ModifyListener(){

			public void modifyText(ModifyEvent e) {
				int i =0;
				vue.getTfListeTiers().deselectAll();
				i=vue.getTfListeTiers().indexOf(vue.getTfRecherche().getText(), 0);
				if(i==-1)i=vue.getTfListeTiers().indexOf(vue.getTfRecherche().getText().toUpperCase(), 0);
				if(i!=-1)vue.getTfListeTiers().setSelection(i);				
			}
	    	
	    });
	    try {
			actRefresh();
			setNbFactures(0);
			setTotalFacture(new BigDecimal(0));

		} catch (Exception e1) {
			logger.error("", e1);
		}



	}
	public String[] initAdapterWlgr() {
		TaWlgrNMoins1DAO taWlgrDAO = new TaWlgrNMoins1DAO(getEm());
		String[] valeurs = null;
		List<TaWlgrNMoins1> l = taWlgrDAO.selectAllClientNonTraite();
		valeurs = new String[l.size()];
		int i = 0;
		String description = "";
		for (TaWlgrNMoins1 taWlgr : l) {
			valeurs[i] = taWlgr.getCodeClient();
			i++;
		}
		taWlgrDAO = null;
		return valeurs;
	}

//	public String[] initAdapterWlgr(){
//		String requete = "select v.code_client,v.traite from v_wlgr v where v.traite=0 and ttc is not null and ttc<>0 order by 1";
//		ibTaTable.getQueryRecherche().close();
//		ibTaTable.getQueryRecherche().setQuery(new QueryDescriptor(ibTaTable.getFIBBase(),requete,true));
//		ibTaTable.getQueryRecherche().open();
//		String[] valeurs = null;
//		if(!ibTaTable.getQueryRecherche().isEmpty()) {
//			valeurs = new String[ibTaTable.getQueryRecherche().getRowCount()];
//			int i = 0;
//			do {
//				valeurs[i]= ibTaTable.getQueryRecherche().getString("code_client");
//				//+";"+ibTaTable.getQueryRecherche().getShort("Traite");
//				i++;
//			} while(ibTaTable.getQueryRecherche().next());
//		}
//		ibTaTable.getQueryRecherche().close();
//		return valeurs;
//	}	
	
	
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
		handlerService.activateHandler(C_COMMAND_GENERATIONDOCUMENT_IMPRESSIONDIVERSES_ID,handlerImpressionDiverses);		
	}
	
	protected void initActions() {
		initCommands();
		if (mapActions == null)
			mapActions = new LinkedHashMap();
		
		mapCommand = new HashMap<String, IHandler>();
		
		mapCommand.put(C_COMMAND_GLOBAL_AIDE_ID, handlerAide);
		mapCommand.put(C_COMMAND_GLOBAL_FERMER_ID, handlerFermer);
		mapCommand.put(C_COMMAND_GLOBAL_IMPRIMER_ID, handlerImprimer);
		mapCommand.put(C_COMMAND_GENERATIONDOCUMENT_RAJOUTER_ID, handlerRajouter);
		mapCommand.put(C_COMMAND_GENERATIONDOCUMENT_REINITIALISER_ID, handlerReinitialiser);
		mapCommand.put(C_COMMAND_GENERATIONDOCUMENT_CHARGERFICHIER_ID, handlerChargerFichier);
		mapCommand.put(C_COMMAND_GENERATIONDOCUMENT_REMONTERDOC_ID, handlerRemonterDoc);
		mapCommand.put(C_COMMAND_GENERATIONDOCUMENT_IMPRIMERDOC_ID, handlerImprimerDoc);
		mapCommand.put(C_COMMAND_GENERATIONDOCUMENT_REINITDOCCREE_ID, handlerReinitDocCree);
		mapCommand.put(C_COMMAND_GENERATIONDOCUMENT_IMPRESSIONDIVERSES_ID, handlerImpressionDiverses);
		
		initFocusCommand(String.valueOf(this.hashCode()),
				listeComposantFocusable, mapCommand);
		
		mapActions.put(vue.getPaBtn1().getBtnFermer(), C_COMMAND_GLOBAL_FERMER_ID);
		mapActions.put(vue.getPaBtn1().getBtnImprimer(), C_COMMAND_GLOBAL_IMPRIMER_ID);
		mapActions.put(vue.getBtnRajouter(), C_COMMAND_GENERATIONDOCUMENT_RAJOUTER_ID);
		mapActions.put(vue.getBtnReinitialiser(), C_COMMAND_GENERATIONDOCUMENT_REINITIALISER_ID);
		mapActions.put(vue.getBtnChargerFichier(), C_COMMAND_GENERATIONDOCUMENT_CHARGERFICHIER_ID);
		mapActions.put(vue.getBtnRemonterDoc(), C_COMMAND_GENERATIONDOCUMENT_REMONTERDOC_ID);
		mapActions.put(vue.getBtnImprimerDoc(), C_COMMAND_GENERATIONDOCUMENT_IMPRIMERDOC_ID);
		mapActions.put(vue.getBtnReinitDocCree(), C_COMMAND_GENERATIONDOCUMENT_REINITDOCCREE_ID);
			
		
		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID,C_COMMAND_GENERATIONDOCUMENT_IMPRESSIONDIVERSES_ID };
		mapActions.put(null, tabActionsAutres);
	}
	
	public PaEditeurListeTiersNMoins1Controller getThis(){
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
		if(validation(true)){
			String text = vue.getTfStyleEditeur().getText();
			//vue.getTfStyleEditeur().get
			String[] lines = text.split("\r\n|\r|\n");
			String fichierExportation = Platform.getInstanceLocation().getURL().getFile()+"/Erreur_Generation_Document.TXT";
			String message = "";
			String messageErreur = "";

			//GenCode code=IB_APPLICATION.recupCodeDocument(Const.C_NOM_TA_FACTURE);
			GenerationDocument genereDocument = new GenerationDocument();
			genereDocument.setDateDoc(LibDateTime.getDate(vue.getTfDateDocument()));
			genereDocument.setLibelleDoc(vue.getTfLibelle().getText());
			genereDocument.setTypePaiement(vue.getTfTPaiement().getText());
			//genereDocument.setCode(modelFacture);
			genereDocument.setCommentaire(FacturePlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.COMMENTAIRE));
//N			genereDocument.setModule("FACTURE");
			genereDocument.setTypeAdresseFacturation(DocumentPlugin.getDefault().getPreferenceStore().getString(fr.legrain.document.preferences.PreferenceConstants.TYPE_ADRESSE_FACTURATION));
			genereDocument.setTypeAdresseLivraison(DocumentPlugin.getDefault().getPreferenceStore().getString(fr.legrain.document.preferences.PreferenceConstants.TYPE_ADRESSE_BONLIV));
			


			StringBuffer ligne = new StringBuffer("");

			FileWriter fw = new FileWriter(fichierExportation);
			BufferedWriter bw = new BufferedWriter(fw);
			CallableStatement ps;

//			//
//			//créer une connection à part pour éviter d'avoir la même connection que les factures
//			//en cours de saisie ou de modification dans perspective "Facture"		
//			Database base =new Database();
//			base.setConnection(new com.borland.dx.sql.dataset.ConnectionDescriptor(
//					Const.C_URL_BDD+Const.C_FICHIER_BDD,Const.C_USER,Const.C_PASS , false, Const.C_DRIVER_JDBC));
//			base.setAutoCommit(false);
//			ps = base.getJdbcConnection().prepareCall("{?,?,? = call GENERATIONFACTURE_LGR(?,?,?,?,?,?)}");
//			ps.registerOutParameter(1,Types.INTEGER);
//			ps.registerOutParameter(2,Types.VARCHAR);
//			ps.registerOutParameter(3,Types.NUMERIC);
			boolean continuer = true;
			int nb = 0;
			
			BigDecimal totalFacture = new BigDecimal(0);
			TaFacture document= null;
			//TaFacture documentInit=new TaFactureDAO().findByCode(modelFacture);
			for (int i = 0; i < lines.length; i++) {
				codeTiers=lines[i];
//				if (!codeTiers.trim().equals("")){
				if(wlgrDao.clientExiste(codeTiers)){	
					try {
						continuer=true;
						if(wlgrDao.ClientPlusieursLicence(codeTiers)){
							MessageDialog.openWarning(Workbench.getInstance()
									.getActiveWorkbenchWindow().getShell(),
									"Attention", "Le code client "+codeTiers+" a plusieurs licences");
							throw new Exception();
					}
						if(!wlgrDao.clientNonTraite(codeTiers)){
							continuer=MessageDialog.openConfirm(Workbench.getInstance()
									.getActiveWorkbenchWindow().getShell(),
									"Attention", "Le code client "+codeTiers+" est déjà traité"+finDeLigne
									+"Voulez-vous le re-traiter ?");
								
						}
						if(continuer){
							genereDocument.setCodeTiers(codeTiers);
							document= (TaFacture) genereDocument.genereLGRNMoins1(getEm());
							TaWlgrNMoins1 taWlgrNMoins = wlgrDao.findByCode(this.codeTiers);
//						ps.setString(4,LibelleFacture);
//						ps.setString(5,code.getCodeFixe());
//						ps.setInt(6,5);
//						ps.setString(7,codeTiers);
//						ps.setDate(8,new java.sql.Date(vue.getTfDateDocument().getSelection().getTime()));
//						ps.setString(9,commentaire);
//						ps.execute();
						if(document!=null){
							String dif="";
							if(document.getNetTtcCalc().compareTo(taWlgrNMoins.getTtc())!=0)
								dif=" ("+LibConversion.bigDecimalToString(document.getNetTtcCalc().subtract(taWlgrNMoins.getTtc()))+")";
							message+="tiers : "+codeTiers+" - Doc : "+document.getCodeDocument()+
							" bdg : "+document.getNetTtcCalc()+" Wlgr : "+taWlgrNMoins.getTtc()+dif+finDeLigne;
							
							//message+="Code tiers : "+codeTiers+" - Document : "+document.getCodeDocument()+finDeLigne;
							vue.getTfListeDoc().add(document.getCodeDocument());
							nb +=1;
							totalFacture=totalFacture.add(document.getNetTtcCalc());
						}
						}
					} catch (Exception e) {
						ligne.append(codeTiers).append(';').append(';');
						fw.write(ligne.toString());
						ligne.delete(0,ligne.length());
						fw.write(finDeLigne);
						messageErreur+="Code tiers : "+codeTiers+finDeLigne;

						logger.error("",e);
					}
				}else{
					MessageDialog.openWarning(Workbench.getInstance()
							.getActiveWorkbenchWindow().getShell(),
							"Attention", "Le code tiers "+codeTiers+" n'est pas valide");
				}
			}
			if(!message.equals("")){
				MessageDialog.openInformation(Workbench.getInstance()
						.getActiveWorkbenchWindow().getShell(),
						"Information", "Les documents : "+finDeLigne+finDeLigne+message+finDeLigne+" ont été créés correctement.");
			}

			if(!messageErreur.equals("")){
				MessageDialog.openWarning(Workbench.getInstance()
						.getActiveWorkbenchWindow().getShell(),
						"ATTENTION", "La génération des documents ne s'est pas déroulée correctement."
						+finDeLigne+finDeLigne+"Les documents sur les clients suivants n'ont pas pu être créés"+finDeLigne+finDeLigne+
						messageErreur+finDeLigne);
			}
			setNbFactures(getNbFactures()+ nb);
			setTotalFacture(getTotalFacture().add(totalFacture));				
			
//			bw.close();
//			fw.close();
//			base.closeConnection();	
		}else{
			MessageDialog.openWarning(Workbench.getInstance()
					.getActiveWorkbenchWindow().getShell(),
					"Attention", "Vous n'avez pas renseigné correctement le libellé de la facture ou le(s) client(s) !!!");
		}

		actRefresh();	
	}

	private boolean validation(boolean message){
		try {		
		if(vue.getTfLibelle().getText().trim().equals(""))
		{
			if (message)MessageDialog.openWarning(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell(),
					"Attention", "Le libellé n'est pas valide");
			return false;
		}
		Date dateTemp;

			dateTemp = LibDateTime.getDate(vue.getTfDateDocument());
			TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
			TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
			if(taInfoEntreprise.getDatedebInfoEntreprise().after(dateTemp)||
					taInfoEntreprise.getDatefinInfoEntreprise().before(dateTemp))
			{
				if (message)MessageDialog.openWarning(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(),
						"Attention", "La date n'est pas valide");
				return false;
			}
		} catch (Exception e) {
			logger.error("", e);
			return false;
		}
		return true;
	}

	@Override
	protected void actAide() throws Exception {
		actAide(null);
	}
	
	
	@Override
	protected void actAide(String message) throws Exception {
		if(vue.getTfRecherche().isFocusControl()){
			 KeyStroke keyStroke = KeyStroke.getInstance("Ctrl+Space");
			vue.getTfRecherche().traverse(keyStroke.getModifierKeys());
		}
		if(vue.getTfTPaiement().isFocusControl()){
				setActiveAide(true);
				boolean verrouLocal = VerrouInterface.isVerrouille();
				VerrouInterface.setVerrouille(true);
				try {
					vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
					ParamAfficheAideRechercheSWT paramAfficheAideRecherche = new ParamAfficheAideRechercheSWT();
//					paramAfficheAideRecherche.setDb(getThis().getIbTaTable().getFIBBase());
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

							PaTypePaiementSWT paTypePaiementSWT = new PaTypePaiementSWT(s2,SWT.NULL);
							SWTPaTypePaiementController swtPaTypePaiementController = new SWTPaTypePaiementController(paTypePaiementSWT);

							editorCreationId = EditorTypePaiement.ID;
							editorInputCreation = new EditorInputTypePaiement();
							
							ParamAfficheTPaiement paramAfficheTPaiement = new ParamAfficheTPaiement();
							paramAfficheAideRecherche.setJPQLQuery(new TaTPaiementDAO(getEm()).getJPQLQuery());
							paramAfficheTPaiement.setModeEcran(EnumModeObjet.C_MO_INSERTION);
							paramAfficheTPaiement.setEcranAppelant(paAideController);
							
							controllerEcranCreation = swtPaTypePaiementController;
							parametreEcranCreation = paramAfficheTPaiement;

							paramAfficheAideRecherche.setTypeEntite(TaTPaiement.class);
							paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_T_PAIEMENT);
							paramAfficheAideRecherche.setDebutRecherche("");
							paramAfficheAideRecherche.setControllerAppelant(getThis());
							paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTPaiement,TaTPaiementDAO,TaTPaiement>(SWTTPaiement.class,getEm()));

							paramAfficheAideRecherche.setTypeObjet(swtPaTypePaiementController.getClassModel());
							paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_T_PAIEMENT);

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


//						dbc.getValidationStatusMap().removeMapChangeListener(changeListener);
//						//LgrShellUtil.afficheAideSWT(paramAfficheAide, null, paAide,paAideController, s);
//						/*****************************************************************/
//						paAideController.configPanel(paramAfficheAide);
//						/*****************************************************************/
//						dbc.getValidationStatusMap().addMapChangeListener(changeListener);
//						// je rends enable false tous les boutons avant de passer
//						// dans l'écran d'aide
//						// pour ne pas que les actions de l'écran des factures
//						// interfèrent ceux de l'écran d'aide
//						// activeWorkenchPartCommands(false);
					}

				} finally {
					VerrouInterface.setVerrouille(false);
					vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
				}
		}
	}

	
	@Override
	protected void actEnregistrer() throws Exception{
	}
	
	
	@Override
	public void initEtatComposant() {}
	

	@Override
	protected void actRefresh() throws Exception {
		vue.getTfListeTiers().setItems(initAdapterWlgr());
		
		
		vue.getLaNbWlgr().setText(LibConversion.integerToString(vue.getTfListeTiers().getItemCount())+" bons restants");
		vue.getLaSumWlgr().setText(LibConversion.bigDecimalToString(wlgrDao.selectSommeRestante())+" € à venir");
		vue.getTfLibelle().setText("Facture maintenance 2012");
		vue.getTfTPaiement().setText("C");
		try {
			//char[] autoActivationCharacters = new char[] {'?'};
	    KeyStroke keyStroke = KeyStroke.getInstance("Ctrl+Space");
	    
		String[][] TabTiers = initAdapterWlgrDecrit();
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
		tiersContentProposal = new ContentProposalAdapter(vue
				.getTfRecherche(), new TextContentAdapter(),
				new ContentProposalProvider(listeCodeTiers,
						listeDescriptionTiers), keyStroke, null);
		tiersContentProposal
				.setFilterStyle(ContentProposalAdapter.FILTER_NONE);
		tiersContentProposal
				.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);
		tiersContentProposal
				.addContentProposalListener(new IContentProposalListener() {
					public void proposalAccepted(IContentProposal proposal) {
						try {

						} catch (Exception e) {
							logger.error("", e);
						}
					}
				});	
		} catch (Exception e) {
			logger.error("", e);
		}		
	}

	@Override
	protected void initMapComposantDecoratedField() {
		// TODO Raccord de méthode auto-généré
		
	}

	@Override
	protected void sortieChamps() {
		// TODO Raccord de méthode auto-généré
		
	}
	
    private boolean trouveCodeTiers(String newValeur){
    	return false;
    	//indexOf(newValeur)!=-1;
    	
    }
	
	private class HandlerRajouter extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
	    		String selected[] = vue.getTfListeTiers().getSelection();
	    		for (int i = 0; i < selected.length; i++) {
	    			if(!trouveCodeTiers(selected[i])){
	    				String valeur=vue.getTfStyleEditeur().getText();
	    				if (!valeur.equals(""))valeur+=finDeLigne;
	    				vue.getTfStyleEditeur().setText(valeur+selected[i]);
	    			}
	    		}
			} catch (Exception e1) {
				logger.error("Erreur : actionPerformed", e1);
			}
			return event;
		}
	}	

	
	private class HandlerReinitialiser extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				vue.getTfStyleEditeur().setText("");
			} catch (Exception e1) {
				logger.error("Erreur : actionPerformed", e1);
			}
			return event;
		}
	}
	private class HandlerReinitDocCree extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				vue.getTfListeDoc().removeAll();
				setNbFactures(0);
				setTotalFacture(new BigDecimal(0));
			} catch (Exception e1) {
				logger.error("Erreur : actionPerformed", e1);
			}
			return event;
		}
	}	

	private class HandlerChargerFichier extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				FileDialog dd = new FileDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
				dd.setFilterExtensions(new String[] {"*.txt"});
				dd.setFilterNames(new String[] {"texte"});
				String repDestination = Platform.getInstanceLocation().getURL().getFile();
				dd.setFilterPath(LibChaine.pathCorrect(repDestination));
				String choix = dd.open();
				System.out.println(choix);
				if(choix!=null){
					FILE_NAME = choix;				
					load();
				}
			} catch (Exception e1) {
				logger.error("Erreur : actionPerformed", e1);
			}
			return event;
		}
	}
	
	  private void load() {
		    if (new File(FILE_NAME).exists()) {
		      try {
		    		FileReader reader = new FileReader(FILE_NAME);
		    		BufferedReader br = new BufferedReader(reader);

		    		String ligne = br.readLine();
		    		while (ligne!=null){		    			
		    			vue.getTfStyleEditeur().append(ligne+System.getProperty("line.separator"));
		    			ligne = br.readLine();
		    		}
		    		br.close();
		        reader.close();
		      } catch (IOException e) {
		        e.printStackTrace();
		      }
		    }
		  }
	
//	  private void readStyles(BufferedReader reader, String currLine)
//	  throws IOException {
//		  while (!END_STYLES_MARK.equals(currLine)) {
//			  currLine = reader.readLine();
//			  if (!END_STYLES_MARK.equals(currLine))
//				  buildOneStyle(currLine);
//		  }
//	  }
	  
	  

  private void readContent(BufferedReader reader, int contentLength)
      throws IOException {
    char[] buffer = new char[contentLength];
    reader.read(buffer, 0, contentLength);

    vue.getTfStyleEditeur().append(new String(buffer));
  }

//  private void buildOneStyle(String styleText) {
//    StringTokenizer tokenizer = new StringTokenizer(styleText);
//    int startPos = Integer.parseInt(tokenizer.nextToken());
//    int length = Integer.parseInt(tokenizer.nextToken());
//
//    StyleRange style = new StyleRange(startPos, length, null, null,
//        SWT.BOLD);
//    vue.getTfStyleEditeur().setStyleRange(style);
//  }

  private class HandlerRemonterDoc extends LgrAbstractHandler {

	  public Object execute(ExecutionEvent event) throws ExecutionException {
		  try {
			  //fr.legrain.editor.facture.swt.multi
			  if(vue.getTfListeDoc().getSelection()!=null&&vue.getTfListeDoc().getSelection().length>0){										
					String idEditor = "fr.legrain.editor.facture.swt.multi";
					IEditorInput editorInput = new IEditorInput() {
						public boolean exists() {
							// TODO Auto-generated method stub
							return false;
						}

						public ImageDescriptor getImageDescriptor() {
							// TODO Auto-generated method stub
							return null;
						}

						public String getName() {
							// TODO Auto-generated method stub
							return "";
						}

						public IPersistableElement getPersistable() {
							// TODO Auto-generated method stub
							return null;
						}

						public String getToolTipText() {
							// TODO Auto-generated method stub
							return "";
						}

						public Object getAdapter(Class adapter) {
							// TODO Auto-generated method stub
							return null;
						}

					};
					
					if(idEditor!=null) {
						IEditorPart editor = AbstractLgrMultiPageEditor.chercheEditeurDocumentOuvert(idEditor);
						if(editor==null) {
							LgrPartListener.getLgrPartListener().setLgrActivePart(null);
							IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().
							  getActivePage().openEditor(editorInput, idEditor);
//							LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
//							LgrPartListener.getLgrPartListener().setLgrActivePart(e);

							ParamAffiche paramAffiche = new ParamAffiche();	
							paramAffiche.setCodeDocument(vue.getTfListeDoc().getSelection()[0]);
							paramAffiche.setModeEcran(EnumModeObjet.C_MO_CONSULTATION);
							((AbstractLgrMultiPageEditor)e).findMasterController().configPanel(paramAffiche);
						}

					}				  
			  }
		  } catch (Exception e1) {
			  logger.error("Erreur : actionPerformed", e1);
		  }
		  return event;
	  }
  }
	
	private class HandlerImprimerDoc extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			if(vue.getTfListeDoc().getSelectionCount()>0){
			try {
				LgrPartListener.getLgrPartListener().setLgrActivePart(null);
				PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				FacturePlugin.getDefault().getBundle();

				//String[] idFactureAImprimer = new String[vue.getTfListeDoc().getSelection().length];;
				List<TaFacture> idFactureAImprimer = new ArrayList<TaFacture>(vue.getTfListeDoc().getSelectionCount());
				final boolean preview = vue.getCbReExport().getSelection();
				final boolean flagPrint = vue.getCbPrint().getSelection();

				String codeDeb = null;
				//String[] idDoc=null;
				List<TaFacture> idDoc = null;
				
				for (int i = 0; i < vue.getTfListeDoc().getSelection().length; i++) {
					idDoc=null;
					codeDeb = vue.getTfListeDoc().getSelection()[i];
					TaFactureDAO daoFacture = new TaFactureDAO(getEm());
					idDoc=daoFacture.rechercheDocument(codeDeb, codeDeb);
					//if(idDoc!=null && idDoc.length>0)
					if(idDoc!=null && idDoc.size()>0)
						idDoc.get(0).calculeTvaEtTotaux();
						idFactureAImprimer.add( idDoc.get(0));
				}
 
				///////////////////////////////////////
				//final String[] finalIdFactureAImprimer = idFactureAImprimer;
				final List<TaFacture> finalIdFactureAImprimer = idFactureAImprimer;
				final boolean finalPreview = preview;
				vue.getParent().getDisplay().asyncExec(new Thread() {
					public void run() {
						try {					
							//impressionFacture.imprimer(finalIdFactureAImprimer,finalPreview);
							impressionFacture.imprimerChoix(ConstEdition.FICHE_FILE_REPORT_FACTURE,"Facture",
									finalIdFactureAImprimer,TaFacture.class.getSimpleName(),
									preview,flagPrint);
							ConstEdition constEdition = new ConstEdition(); 
							constEdition.setFlagEditionMultiple(true);
						} catch (Exception e) {
							logger.error("Erreur à l'impression ",e);
						} finally {
						}
					}
				});
				
			} catch (Exception e1) {
				logger.error("Erreur : actionPerformed", e1);
			}
			}
			return event;
		}
	}
//	protected void imprimer(String[] idFactureAImprimer,boolean preview) throws Exception{		
//		//Chemin de l'edition
//		PlatformUI.getWorkbench().getActiveWorkbenchWindow();
//		Bundle bundleCourant = FacturePlugin.getDefault().getBundle();
//		URL urlReportFile = Platform.asLocalURL(bundleCourant.getEntry("/report/facture.rptdesign"));
//		URI uriReportFile = new URI("file",urlReportFile.getAuthority(),urlReportFile.getPath(),urlReportFile.getQuery(),null);
//		File reportFile = new File(uriReportFile);
//		
//		//Preparation de l'edition
//		BirtUtil.startReportEngine();
//		
//		SWTInfoEntreprise infoEntreprise = SWT_IB_TA_INFO_ENTREPRISE.infosEntreprise("1",null);
//		
//		HashMap<String,String> reportParam = new HashMap<String,String>();
//		reportParam.put("paramUrlJDBC",IB_APPLICATION.findDatabase().getConnection().getConnectionURL());
//		reportParam.put("capital",infoEntreprise.getCAPITAL_INFO_ENTREPRISE());
//		reportParam.put("ape",infoEntreprise.getAPE_INFO_ENTREPRISE());
//		reportParam.put("siret",infoEntreprise.getSIRET_INFO_ENTREPRISE());
//		reportParam.put("rcs",infoEntreprise.getRCS_INFO_ENTREPRISE());
//		reportParam.put("nomEntreprise",infoEntreprise.getNOM_INFO_ENTREPRISE());
//		
//		LgrSpooler spooler = LgrSpooler.getInstance();
//	
//		final String[] finalIdFactureAImprimer = idFactureAImprimer;
//		final File finalReportFile = reportFile;
//		final HashMap finalReportParam = reportParam;
//		final LgrSpooler finalSpooler = spooler;
//		Job job = new Job("Préparation de l'impression") {
//			protected IStatus run(IProgressMonitor monitor) {
//				final int ticks = finalIdFactureAImprimer.length;
//				monitor.beginTask("Préparation de l'impression", ticks);
//				try {
//					OutputStream os = null;
//					for (int i = 0; i < finalIdFactureAImprimer.length; i++) {
//						finalReportParam.put("paramID_DOC",finalIdFactureAImprimer[i]);
//						os = BirtUtil.renderReportToStream(finalReportFile.getAbsolutePath(),finalReportParam,BirtUtil.PDF_FORMAT);
//						finalSpooler.add(os);
//						monitor.worked(1);
//						if (monitor.isCanceled()) {
//							finalSpooler.clear();
//							return Status.CANCEL_STATUS;
//						}
//					}
//				} finally {
//					monitor.done();
//				}
//				return Status.OK_STATUS;
//			}
//		};
//		job.setPriority(Job.SHORT);
//		//job.setUser(true);
//		job.schedule(); 
//		job.join();
//		
//		//Impression
//		if(job.getResult()==Status.OK_STATUS)
//			spooler.print(preview);
//		
//		BirtUtil.destroyReportEngine();		
//	}
	
  
	private class HandlerImpressionDiverses extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
//			if(vue.getTfListeDoc().getSelectionCount()>0)
			{
			try {
				LgrPartListener.getLgrPartListener().setLgrActivePart(null);

				List<TaFacture> idFactureAImprimer = new ArrayList<TaFacture>();
				final boolean preview = vue.getCbReExport().getSelection();
				final boolean flagPrint = vue.getCbPrint().getSelection();

				String codeDeb = null;
				List<TaFacture> idDoc = null;
				
				for (int i = 0; i < vue.getTfListeDoc().getSelection().length; i++) {
					idDoc=null;
					codeDeb = vue.getTfListeDoc().getSelection()[i];
					TaFactureDAO daoFacture = new TaFactureDAO(getEm());
					idDoc=daoFacture.rechercheDocument(codeDeb, codeDeb);
					if(idDoc!=null && idDoc.size()>0)
						  //idFactureAImprimer[i]=idDoc[0];
							idFactureAImprimer.set(i, idDoc.get(0));
					}
 
				///////////////////////////////////////
				final List<TaFacture> finalIdFactureAImprimer = idFactureAImprimer;
				final boolean finalPreview = preview;
				vue.getParent().getDisplay().asyncExec(new Thread() {
					public void run() {
						try {	
							//impressionGeneration.imprimer(null,finalPreview);
							impressionGeneration.imprimerChoix(ConstEdition.FICHE_FILE_REPORT_FACTURE,"Facture",
									finalIdFactureAImprimer,TaFacture.class.getSimpleName(),
									preview,flagPrint);
							ConstEdition constEdition = new ConstEdition(); 
							constEdition.setFlagEditionMultiple(true);
							//actFermer();
						} catch (Exception e) {
							logger.error("Erreur à l'impression ",e);
						} finally {
						}
					}
				});
				
			} catch (Exception e1) {
				logger.error("Erreur : actionPerformed", e1);
			}
			}
			return event;
		}
	}

	
	class ListItem {
		  public String codeTiers;
		  public String nomTiers;

		  public ListItem(String codeTiers, String nomTiers) {
			  this.codeTiers = codeTiers;
			  this.nomTiers = nomTiers;
		  }
	}
//	public String[][] initAdapterWlgrDecrit() {
//		String requete = "select v.code_client,v.licence, v.nom,(case v.traite when 0 then 'non traité' else 'traité' end)as T from v_wlgr v where ttc is not null and ttc<>0 order by 1";
//		dao.getQueryRecherche().close();
//		ibTaTable.getQueryRecherche().setQuery(
//				new QueryDescriptor(ibTaTable.getFIBBase(),
//						requete, true));
//		ibTaTable.getQueryRecherche().open();
//		String[][] valeurs = null;
//		if (!ibTaTable.getQueryRecherche().isEmpty()) {
//			valeurs = new String[ibTaTable.getQueryRecherche().getRowCount()][2];
//			int i = 0;
//			do {
//				valeurs[i][0] = ibTaTable.getQueryRecherche().getString("code_client");
//				valeurs[i][1] = ibTaTable.getQueryRecherche().getString(
//						"licence")
//						+ " - "
//						+ ibTaTable.getQueryRecherche().getString(
//								"nom")
//						+ " - "
//						+ ibTaTable.getQueryRecherche().getString(
//								"T");
//				i++;
//			} while (ibTaTable.getQueryRecherche().next());
//		}
//		ibTaTable.getQueryRecherche().close();
//		return valeurs;
//	}
	public String[][] initAdapterWlgrDecrit() {
		TaWlgrNMoins1DAO taWlgrDAO = new TaWlgrNMoins1DAO(getEm());
		String[][] valeurs = null;
		List<TaWlgrNMoins1> l = taWlgrDAO.selectAll();
		valeurs = new String[l.size()][2];
		int i = 0;
		String description = "";
		for (TaWlgrNMoins1 taWlgr : l) {
			valeurs[i][0] = taWlgr.getCodeClient();
			
			description = "";
			description += taWlgr.getLicence() + " - " + taWlgr.getNom() ;
			if(taWlgr.getTraite()==1) description +=" - traité" ;
			else description +=" - non traité" ;
			description +=" \r\nEpicéa : "+taWlgr.getTotalHtE2();
			description +=" \r\nE2-Fac : "+taWlgr.getTotalHtF2();
			description +=" \r\nSara : "+taWlgr.getTotalHtS2();
			description +=" \r\nTTC : "+taWlgr.getTtc();
			valeurs[i][1] = description;

			i++;
		}
		taWlgrDAO = null;
		return valeurs;
	}

	public String getCommentaire() {
		return commentaire;
	}
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	public String getLibelleFacture() {
		return LibelleFacture;
	}
	public void setLibelleFacture(String libelleFacture) {
		LibelleFacture = libelleFacture;
	}
	
//	public boolean ClientNonTraite(String codeClient) throws HeadlessException, Exception {
//		QueryDataSet queryRechercheLoc = new QueryDataSet();
//		boolean result = false;
//		queryRechercheLoc.close();
//		queryRechercheLoc.setQuery(new QueryDescriptor(ibTaTable.getFIBBase(),"select v.code_client,v.traite from v_wlgr v where " +
//				"code_client like('"+codeClient+"')",true));
//		queryRechercheLoc.open();
//		DataRow courant = new DataRow(queryRechercheLoc,"traite");
//		courant.setShort("traite",new Short("0"));
//		result=queryRechercheLoc.locate(courant,Const.C_LOCATE_OPTION_BORLAND_FIRST);
//		queryRechercheLoc.close();
//		return result;
//	}	
	
//	public boolean ClientPlusieursLicence(String codeClient) throws HeadlessException, Exception {
//		QueryDataSet queryRechercheLoc = new QueryDataSet();
//		boolean result = false;
//		queryRechercheLoc.close();
//		queryRechercheLoc.setQuery(new QueryDescriptor(ibTaTable.getFIBBase(),"select count(*)as nb from ta_wlgr where " +
//				"code_client like('"+codeClient+"') ",true));
//		queryRechercheLoc.open();
//		result=queryRechercheLoc.getInt("nb")>1;
//		queryRechercheLoc.close();
//		return result;
//	}
	
//	public BigDecimal sommeRestanteWlgr() throws HeadlessException, Exception {
//		wlgrDao.selectAll();
//		QueryDataSet queryRechercheLoc = new QueryDataSet();
//		BigDecimal result = new BigDecimal(0);
//		queryRechercheLoc.close();
//		queryRechercheLoc.setQuery(new QueryDescriptor(ibTaTable.getFIBBase(),
//				"select sum(ttc)as sommeRestante from ta_wlgr where " +
//				"traite = 0 and ttc > 0 ",true));
//		queryRechercheLoc.open();
//		result=queryRechercheLoc.getBigDecimal("sommeRestante");
//		queryRechercheLoc.close();
//		return result;
//	}
	
//	public boolean ClientExiste(String codeClient) throws HeadlessException, Exception {
//		QueryDataSet queryRechercheLoc = new QueryDataSet();
//		boolean result = false;
//		queryRechercheLoc.close();
//		queryRechercheLoc.setQuery(new QueryDescriptor(ibTaTable.getFIBBase(),"select v.code_client,v.traite from v_wlgr v where " +
//				"code_client like('"+codeClient+"')",true));
//		queryRechercheLoc.open();
//		DataRow courant = new DataRow(queryRechercheLoc,"code_client");
//		courant.setString("code_client",codeClient);
//		result=queryRechercheLoc.locate(courant,Const.C_LOCATE_OPTION_BORLAND_FIRST);
//		queryRechercheLoc.close();
//		return result;
//	}
	
	public BigDecimal getTotalFacture() {
		return totalFacture;
	}
	public void setTotalFacture(BigDecimal totalFacture) {
		this.totalFacture = totalFacture;
		vue.getLaTotalDoc().setText(LibConversion.bigDecimalToString(totalFacture)+" €");
	}
	public Integer getNbFactures() {
		return nbFactures;
	}
	public void setNbFactures(Integer nbFactures) {
		this.nbFactures = nbFactures;
		vue.getLaNbFacture().setText(LibConversion.integerToString(nbFactures)+" facture(s)");
	}
}


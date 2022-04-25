package fr.legrain.document.impression.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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
import fr.legrain.document.divers.ConstEditionDocument;
import fr.legrain.document.divers.IEditionTraite;
import fr.legrain.document.divers.IImpressionDocumentTiers;
import fr.legrain.document.divers.TypeDoc;
import fr.legrain.document.impression.divers.ParamImpressionFacture;
import fr.legrain.document.impression.ecran.PaImpressionFactureSWT;
import fr.legrain.document.preferences.PreferenceConstants;
import fr.legrain.documents.dao.IDocumentTiersDAO;
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
import fr.legrain.documents.dao.TaRReglement;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.edition.actions.BaseImpressionEdition;
import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IHMAideFacture;
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
import fr.legrain.gestionCommerciale.GestionCommercialePlugin;
import fr.legrain.lib.data.AbstractLgrDAO;
import fr.legrain.lib.data.ContentProposalProvider;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.LgrSimpleTextContentProposal;
import fr.legrain.lib.data.LibChaine;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.lib.gui.aide.PaAideRechercheSWT;
import fr.legrain.lib.gui.aide.PaAideSWT;
import fr.legrain.lib.gui.aide.ParamAfficheAide;


public class SWTPaImpressionDocumentController extends JPABaseControllerSWTStandard implements
RetourEcranListener {

	static Logger logger = Logger.getLogger(SWTPaImpressionDocumentController.class.getName());		
	private PaImpressionFactureSWT vue = null; // vue	
	private IDocumentTiersDAO dao = null;//new TaFactureDAO();
	private IImpressionDocumentTiers impressionDocument = null; 
	
	/** 01/03/2010 modifier les editions (zhaolin) **/
	private BaseImpressionEdition impressionEdition = null;
	/************************************************/
	private TaInfoEntreprise taInfoEntreprise = null;
	private ContentProposalAdapter codeFactureContentProposalDebut = null;
	private ContentProposalAdapter codeFactureContentProposalFin = null;

	private Object ecranAppelant = null;
	
	private TypeDoc typeDoc = TypeDoc.getInstance();
	
	Object gestionnaireTraite=null;
	private Map<String,Object> listeGestionnaireImpressionDocument = new LinkedHashMap<String,Object>();
	
	private LgrModifyListener tfModifyListener = new LgrModifyListener();
	
	
	
	public SWTPaImpressionDocumentController(PaImpressionFactureSWT vue) {
		this(vue,null);
	}

	public SWTPaImpressionDocumentController(PaImpressionFactureSWT vue,EntityManager em) {
		try{
			initCursor(SWT.CURSOR_WAIT);
//			vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));		
		if(em!=null) {
			setEm(em);
		}
		dao = new TaFactureDAO(getEm());
		try {
			setVue(vue);
			this.vue=vue;
			vue.getShell().addShellListener(this);
			
			//			 Branchement action annuler : empeche la fermeture automatique de la
			// fenetre sur ESC
			vue.getShell().addTraverseListener(new Traverse());
			actionImprimer.setText("Exporter [F11]");

			//			vue.getPaBtn1().getBtnAnnuler().setVisible(false);
			//			vue.getPaBtn1().getBtnEnregistrer().setVisible(false);
			//			//vue.getPaBtn1().getBtnFermer().setVisible(false);
			//			vue.getPaBtn1().getBtnInserer().setVisible(false);
			//			vue.getPaBtn1().getBtnSupprimer().setVisible(false);
			//			vue.getPaBtn1().getBtnModifier().setVisible(false);

			vue.getTfDATEDEB().addSelectionListener(new LgrModifyListener());
			vue.getTfNumDeb().addModifyListener(tfModifyListener);
			vue.getComboTypeDoc().addModifyListener(new LgrModifyListener());

			vue.getTfDATEFIN().setEnabled(true);
			vue.getTfNumFin().setEnabled(true);
			
			String[] liste= new String[typeDoc.getTypeDocImpressionPresent().size()];
			int i = 0;
			for (String libelle : typeDoc.getTypeDocImpressionPresent().values()) {
				liste[i]=libelle;
				i++;
			}
			vue.getComboTypeDoc().setItems(liste);
			vue.getComboTypeDoc().select(0);
			vue.getComboTypeDoc().setVisibleItemCount(vue.getComboTypeDoc().getItemCount());
//			vue.getComboTypeDoc().setEditable(false);

			initController();
		} catch (Exception e) {
			logger.error("Erreur ", e);
		}
		}finally{
			initCursor(SWT.CURSOR_ARROW);
//			vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
		}
	}


	private class LgrModifyListener implements ModifyListener, SelectionListener{

		public void modifyText(ModifyEvent e) {
			modif(e);
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			modif(e);
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			modif(e);
		}
	}

	private void modif(TypedEvent e) {
		try {
			if(e.getSource().equals(vue.getTfNumDeb())) {
				if(!LibChaine.empty(vue.getTfNumDeb().getText())) {
					vue.getTfNumFin().setEnabled(true);
					LibDateTime.setDateNull(vue.getTfDATEDEB()); //vue.getTfDATEDEB().setEnabled(false);
					LibDateTime.setDateNull(vue.getTfDATEFIN()); vue.getTfDATEFIN().setEnabled(false);
				} else {
					vue.getTfNumFin().setText(""); vue.getTfNumFin().setEnabled(false);
					LibDateTime.setDateNull(vue.getTfDATEDEB()); //vue.getTfDATEDEB().setEnabled(true);
					LibDateTime.setDateNull(vue.getTfDATEFIN()); vue.getTfDATEFIN().setEnabled(true);
				}
			}
			if(e.getSource().equals(vue.getTfDATEDEB())) {
				//if(!LibChaine.empty(vue.getTfDATEDEB().getText())) {
				if(!LibDateTime.isDateNull(vue.getTfDATEDEB())) {
					vue.getTfDATEFIN().setEnabled(true);
					vue.getTfNumDeb().removeModifyListener(tfModifyListener);
						vue.getTfNumDeb().setText(""); 
						//vue.getTfNumDeb().setEnabled(false);
						vue.getTfNumFin().setText(""); 
						vue.getTfNumFin().setEnabled(false);
					vue.getTfNumDeb().addModifyListener(tfModifyListener);
				} else {
					LibDateTime.setDateNull(vue.getTfDATEDEB());
					LibDateTime.setDateNull(vue.getTfDATEFIN()); vue.getTfDATEFIN().setEnabled(false);
					vue.getTfNumDeb().setEnabled(true);
				}
			}
			if(e.getSource().equals(vue.getComboTypeDoc())) {
				int typeDoc = vue.getComboTypeDoc().getSelectionIndex();
				//réinitialisation des références si changement de type de document
				vue.getTfNumDeb().setText("");
				vue.getTfNumFin().setText("");
				if(vue.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_FACTURE)) {
					dao = new TaFactureDAO(getEm());
				} else if(vue.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_DEVIS)) {
					dao = new TaDevisDAO(getEm());
				} else if(vue.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_APPORTEUR)) {
					dao = new TaApporteurDAO(getEm());
				} else if(vue.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_AVOIR)) {
					dao = new TaAvoirDAO(getEm());
				} else if(vue.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_PROFORMA)) {
					dao = new TaProformaDAO(getEm());
				} else if(vue.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_BON_COMMANDE)) {
					dao = new TaBoncdeDAO(getEm());
				} else if(vue.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_ACOMPTE)) {
					dao = new TaAcompteDAO(getEm());
				}else if(vue.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_BON_LIVRAISON)) {
					dao = new TaBonlivDAO(getEm());
				}else if(vue.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_AVIS_ECHEANCE)) {
					dao = new TaAvisEcheanceDAO(getEm());
				}else if(vue.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_PRELEVEMENT)) {
					dao = new TaPrelevementDAO(getEm());
				}
				setDaoStandard(((AbstractLgrDAO) dao));
				if(codeFactureContentProposalDebut!=null)
					codeFactureContentProposalDebut.setContentProposalProvider(contentProposalProviderDocument());
				if(codeFactureContentProposalFin!=null)
					codeFactureContentProposalFin.setContentProposalProvider(contentProposalProviderDocument());

			}
			if(taInfoEntreprise!=null){
				if(LibDateTime.isDateNull(vue.getTfDATEDEB())) LibDateTime.setDate(vue.getTfDATEDEB(),taInfoEntreprise.getDatedebInfoEntreprise());
				if(LibDateTime.isDateNull(vue.getTfDATEFIN())) LibDateTime.setDate(vue.getTfDATEFIN(),taInfoEntreprise.getDatefinInfoEntreprise());
			}
		} catch (Exception e1) {
			logger.error(e1);
		}		
	}
	private void initController() {
		try {
			setDaoStandard(((AbstractLgrDAO) dao));
			vue.getCbReExport().setSelection(true);
//			ibTaTableStandard=dao;
//			addDestroyListener(dao);
			
			impressionEdition = new BaseImpressionEdition(vue.getShell(),null);
			TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
			taInfoEntreprise = taInfoEntrepriseDAO.findInstance();

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
			
			IPreferenceStore preferenceStore = DocumentPlugin.getDefault().getPreferenceStore();
			boolean preview = preferenceStore.getBoolean(PreferenceConstants.afficheEditionImprimer);
			vue.getCbReExport().setSelection(preview);
			boolean printDirect = preferenceStore.getBoolean(PreferenceConstants.editionImprimerDirect);
			vue.getCbPrintExport().setSelection(printDirect);
			createContributors();
			
			LibDateTime.setDateSystem(vue.getTfDATEDEB());
			LibDateTime.setDateSystem(vue.getTfDATEFIN());
			if(taInfoEntreprise!=null){
				LibDateTime.setDate(vue.getTfDATEDEB(),taInfoEntreprise.getDatedebInfoEntreprise());
				LibDateTime.setDate(vue.getTfDATEFIN(),taInfoEntreprise.getDatefinInfoEntreprise());
			}
			
		} catch (Exception e) {
			//			vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaArticlesController", e);
		}
	}

	@Override
	protected void initImageBouton() {
		vue.getPaBtn1().getBtnFermer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_FERMER));
		vue.getPaBtn1().getBtnImprimer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_IMPRIMER));
		vue.layout(true);
	}


	public Composite getVue() {return vue;}

	public ResultAffiche configPanel(ParamAffiche param){
		if (param!=null){
			if (((ParamImpressionFacture)param).getFocusDefaut()!=null)
				((ParamImpressionFacture)param).getFocusDefaut().requestFocus();

			if(param.getEcranAppelant()!=null) {
				ecranAppelant = param.getEcranAppelant();
			}
			param.setFocus(((AbstractLgrDAO) dao).getModeObjet().getFocusCourant());
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
		boolean trouve =false;

		trouve = daoStandard.selectCount()>0;

		switch (((AbstractLgrDAO) dao).getModeObjet().getMode()) {
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
			actionInserer.setEnabled(!trouve);
			actionModifier.setEnabled(trouve);
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

		initFocusSWT(((AbstractLgrDAO) dao),mapInitFocus);
	}	


	/**
	 * Initialisation des correspondances entre les champs de formulaire et les
	 * champs de bdd
	 */
	protected void initMapComposantChamps() {
		if (listeComposantFocusable == null) 
			listeComposantFocusable = new ArrayList();

		if (mapComposantChamps == null) 
			mapComposantChamps = new LinkedHashMap();
		listeComposantFocusable.add(vue.getComboTypeDoc());
		listeComposantFocusable.add(vue.getTfNumDeb());
		listeComposantFocusable.add(vue.getTfNumFin());
		listeComposantFocusable.add(vue.getTfDATEDEB());
		listeComposantFocusable.add(vue.getTfDATEFIN());			

		listeComposantFocusable.add(vue.getPaBtn1().getBtnImprimer());
		listeComposantFocusable.add(vue.getPaBtn1().getBtnFermer());

		if(mapInitFocus == null) 
			mapInitFocus = new LinkedHashMap();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION,vue.getComboTypeDoc());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION,vue.getComboTypeDoc());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION,vue.getComboTypeDoc());

		//		activeDocumentListener();

		String activationContentProposal = "Ctrl+Space";
		//String activationContentProposal = C_COMMAND_GLOBAL_AIDE_ID;
		String [][]listeDocuments =initContentProposalFacture();
		codeFactureContentProposalDebut = LgrSimpleTextContentProposal.defaultTextContentProposalKey(vue.getTfNumDeb(),activationContentProposal,listeDocuments,null);
		LgrSimpleTextContentProposal.defaultOptions(codeFactureContentProposalDebut);

		codeFactureContentProposalFin = LgrSimpleTextContentProposal.defaultTextContentProposalKey(vue.getTfNumFin(),activationContentProposal,listeDocuments,null);
		LgrSimpleTextContentProposal.defaultOptions(codeFactureContentProposalFin);

	}

	public String[][] initContentProposalFacture(){
		String[][] valeurs = null;
		boolean affichageCtrlEspace = GestionCommercialePlugin.getDefault().getPreferenceStore().getBoolean(fr.legrain.gestionCommerciale.preferences.PreferenceConstants.AFFICHAGE_CTRL_ESPACE);
		if(affichageCtrlEspace){
			TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
			TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
			try {
				List<IDocumentTiers> l = ((IDocumentTiersDAO) dao).rechercheDocument(taInfoEntreprise.getDatedebInfoEntreprise(),
						taInfoEntreprise.getDatefinInfoEntreprise());

				valeurs = new String[l.size()][2];
				int i = 0;
				String description = "";
				for (IDocumentTiers doc : l) {
					valeurs[i][0] = doc.getCodeDocument();

					description = "";
					description += doc.getLibelleDocument();
					valeurs[i][1] = description;

					i++;
				}

			} catch (Exception e) {
				return null;
			}
		}
		return valeurs;			
	}
	
	public ContentProposalProvider contentProposalProviderDocument(){
		String[][] adapterFacture = initContentProposalFacture();
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

		return new ContentProposalProvider(listeCodeFacture, listeLibelleFacture);
		
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
//		//handlerService.activateHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID, handlerEnregistrer, activeShellExpression);
//		handlerService.activateHandler(C_COMMAND_GLOBAL_FERMER_ID, handlerFermer, activeShellExpression);
//		handlerService.activateHandler(C_COMMAND_GLOBAL_IMPRIMER_ID, handlerImprimer, activeShellExpression);
//		//handlerService.activateHandler(C_COMMAND_GLOBAL_INSERER_ID, handlerInserer, activeShellExpression);
//		//handlerService.activateHandler(C_COMMAND_GLOBAL_MODIFIER_ID, handlerModifier, activeShellExpression);
//		//handlerService.activateHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID, handlerSupprimer, activeShellExpression);	
//		//handlerService.activateHandler(C_COMMAND_GLOBAL_REFRESH_ID, handlerRefresh, activeShellExpression);
//	}

	protected void initActions() {
		mapCommand = new HashMap<String, IHandler>();

		mapCommand.put(C_COMMAND_GLOBAL_ANNULER_ID, handlerAnnuler);
		mapCommand.put(C_COMMAND_GLOBAL_AIDE_ID, handlerAide);
		mapCommand.put(C_COMMAND_GLOBAL_FERMER_ID, handlerFermer);
		mapCommand.put(C_COMMAND_GLOBAL_IMPRIMER_ID, handlerImprimer);
		
		initFocusCommand(String.valueOf(this.hashCode()),
				listeComposantFocusable, mapCommand);
//		initCommands();
		
		if (mapActions == null)
			mapActions = new LinkedHashMap();
		
		mapActions.put(vue.getPaBtn1().getBtnFermer(), C_COMMAND_GLOBAL_FERMER_ID);
		mapActions.put(vue.getPaBtn1().getBtnImprimer(), C_COMMAND_GLOBAL_IMPRIMER_ID);
		
		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID };
		mapActions.put(null, tabActionsAutres);
	}

	public SWTPaImpressionDocumentController getThis(){
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
				} catch (Exception e) {
					logger.error("",e);
				}
			}
		}
//		super.retourEcran(evt);
	}

	@Override
	protected void actInserer() throws Exception{}

	@Override
	protected void actModifier() throws Exception{}

	@Override
	protected void actSupprimer() throws Exception{}

	@Override
	protected void actFermer() throws Exception {
		if (onClose()) {
			closeWorkbenchPart();
		}
	}

	@Override
	protected void actAnnuler() throws Exception{
		if (focusDansEcran())actionFermer.run();
	}

	@Override
	protected void actImprimer() throws Exception{
		//Récupération des paramètres dans l'ihm
		//String[] idFactureAImprimer = null;
		List<IDocumentTiers> listDocumentAImprimer = null;
			
		final boolean preview = vue.getCbReExport().getSelection();
		final boolean printDirect = vue.getCbPrintExport().getSelection();
		
		
		Date dateDeb = null;
		Date dateFin = null;
		String codeDeb = null;
		String codeFin = null;

		if(!LibChaine.empty(vue.getTfNumDeb().getText())) {
			codeDeb = vue.getTfNumDeb().getText();
		}
		if(!LibChaine.empty(vue.getTfNumFin().getText())) {
			codeFin = vue.getTfNumFin().getText();
		}
//		if( (!LibChaine.empty(vue.getTfDATEDEB().getText()) && 
//				!vue.getTfDATEDEB().getText().equals("<choisir une date>") && 
//				vue.getTfDATEDEB().getText()!=null) ) {
		if( !LibDateTime.isDateNull(vue.getTfDATEDEB()) ) {
			dateDeb = LibDateTime.getDate(vue.getTfDATEDEB());
		}
//		if( (!LibChaine.empty(vue.getTfDATEFIN().getText())&& 
//				!vue.getTfDATEFIN().getText().equals("<choisir une date>")
//				&& vue.getTfDATEFIN().getText()!=null) ) {
		if( !LibDateTime.isDateNull(vue.getTfDATEFIN()) ) {
			dateFin = LibDateTime.getDate(vue.getTfDATEFIN());
		}

		if(codeDeb!=null) {//1 code => cette facture
			logger.debug("Exportation - selection par code");
			if(codeFin!=null) {//2 codes => factures entre les 2 codes si intervalle correct
				listDocumentAImprimer = dao.rechercheDocument(codeDeb,codeFin);
			} else {
				codeFin = codeDeb;
				listDocumentAImprimer = dao.rechercheDocument(codeDeb,codeFin);
			}
		} else if(dateDeb!=null) {//1 date => toutes les factures à cette date
			logger.debug("Exportation - selection par date");
			if(dateFin!=null) {//2 dates => factures entre les 2 dates si intervalle correct
				if(dateDeb.compareTo(dateFin)>0) {
					MessageDialog.openWarning(PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell(),
							"ATTENTION", "La date de début doit être antérieure à la date fin");
					throw new Exception("probleme intervalle des dates");
				}
				listDocumentAImprimer = dao.rechercheDocument(dateDeb,dateFin);
			} else {
				dateFin = dateDeb;
				listDocumentAImprimer = dao.rechercheDocument(dateDeb,dateFin);
			}
		} 
//		for (IDocumentTiers documentTiers : listDocumentAImprimer) {
//			documentTiers.calculeTvaEtTotaux();
//		}
		///////////////////////////////////////
		//final String[] finalIdFactureAImprimer = listFactureAImprimer;
		
		if(listDocumentAImprimer==null ||listDocumentAImprimer.size()==0){
			MessageDialog.openWarning(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell(),
					"ATTENTION", "Aucun document valide n'a été sélectionné !!!");
			throw new Exception("pas de factures à imprimer");
		}
		final List<IDocumentTiers> finalListDocumentAImprimer = listDocumentAImprimer;
		final boolean finalPreview = preview;
		String fichierEdition = null;
		String nomOnglet = null;
		String nomEntity = null;
		String typeTraite = null;
		int typeDoc = vue.getComboTypeDoc().getSelectionIndex();
		final LinkedList<TaRReglement> listeTraite=new LinkedList<TaRReglement>();
		listeTraite.clear();
		/** 01/03/2010 modifier les editions (zhaolin) **/
		IPreferenceStore preferenceStore = null;
		String namePlugin = null;
		/************************************************/
		if(vue.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_FACTURE)) {
			impressionDocument=(IImpressionDocumentTiers) listeGestionnaireImpressionDocument.get(TypeDoc.TYPE_FACTURE_BUNDLEID);

			//impressionDocument = new fr.legrain.facture.divers.Impression(vue.getShell());
			fichierEdition = ConstEdition.FICHE_FILE_REPORT_FACTURE;
			nomOnglet = "Facture";
			nomEntity = TaFacture.class.getSimpleName();
			if(gestionnaireTraite!=null){
				typeTraite = ((IEditionTraite)gestionnaireTraite).getTypeTraite();
			}
			//typeTraite=DocumentPlugin.getDefault().getPreferenceStore().getDefaultString(PreferenceConstants.TYPE_TRAITE);
			/** 01/03/2010 modifier les editions (zhaolin) **/
//			preferenceStore=impressionDocument.getPreferenceStore();
//			namePlugin = impressionDocument.getPluginName();
			//preferenceStore = FacturePlugin.getDefault().getPreferenceStore();
//			namePlugin = FacturePlugin.getDefault().getBundle().getSymbolicName();
			
//			if(gestionnaireTraite!=null){
//				((IEditionTraite)gestionnaireTraite).setShell(vue.getShell());
//				String typeTraite = DocumentPlugin.getDefault().getPreferenceStore().getDefaultString(PreferenceConstants.TYPE_TRAITE);
//				LinkedList<TaRReglement> listeTemp=new LinkedList<TaRReglement>();
//				for (IDocumentTiers iDocumentTiers : finalListDocumentAImprimer) {
//					listeTemp.clear();
//					listeTemp=((TaFacture)iDocumentTiers).rechercheSiDocumentContientTraite(typeTraite);
//					for (TaRReglement taRReglement : listeTemp) {
//						listeTraite.add(taRReglement);
//					}
//				}
//			}
//			/************************************************/
			
		} else if(vue.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_DEVIS)) {
//			impressionDocument = new fr.legrain.devis.divers.Impression(vue.getShell());
			impressionDocument=(IImpressionDocumentTiers) listeGestionnaireImpressionDocument.get(TypeDoc.TYPE_DEVIS_BUNDLEID);
			fichierEdition = ConstEdition.FICHE_FILE_REPORT_DEVIS;
			nomOnglet = "Devis";
			nomEntity = TaDevis.class.getSimpleName();
			
			/** 01/03/2010 modifier les editions (zhaolin) **/
//			preferenceStore = DevisPlugin.getDefault().getPreferenceStore();
//			namePlugin = DevisPlugin.getDefault().getBundle().getSymbolicName();
			/************************************************/
			
		} else if(vue.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_APPORTEUR)) {
//			impressionDocument = new fr.legrain.apporteur.divers.Impression(vue.getShell());
			impressionDocument=(IImpressionDocumentTiers) listeGestionnaireImpressionDocument.get(TypeDoc.TYPE_APPORTEUR_BUNDLEID);
			fichierEdition = ConstEdition.FICHE_FILE_REPORT_APPORTEUR;
			nomOnglet = "Apporteur";
			nomEntity = TaApporteur.class.getSimpleName();
			
			/** 01/03/2010 modifier les editions (zhaolin) **/
//			preferenceStore = apporteurPlugin.getDefault().getPreferenceStore();
//			namePlugin = apporteurPlugin.getDefault().getBundle().getSymbolicName();
			/************************************************/
			
		} else if(vue.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_AVOIR)) {
//			impressionDocument = new fr.legrain.avoir.divers.Impression(vue.getShell());
			impressionDocument=(IImpressionDocumentTiers) listeGestionnaireImpressionDocument.get(TypeDoc.TYPE_AVOIR_BUNDLEID);
			fichierEdition = ConstEdition.FICHE_FILE_REPORT_AVOIR;
			nomOnglet = "Avoir";
			nomEntity = TaAvoir.class.getSimpleName();
			
			/** 01/03/2010 modifier les editions (zhaolin) **/
//			preferenceStore = avoirPLugin.getDefault().getPreferenceStore();
//			namePlugin = avoirPLugin.getDefault().getBundle().getSymbolicName();
			/************************************************/
			
		} else if(vue.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_PROFORMA)) {
//			impressionDocument = new fr.legrain.proforma.divers.Impression(vue.getShell());
			impressionDocument=(IImpressionDocumentTiers) listeGestionnaireImpressionDocument.get(TypeDoc.TYPE_PROFORMA_BUNDLEID);
			fichierEdition = ConstEdition.FICHE_FILE_REPORT_PROFORMA;
			nomOnglet = "Proforma";
			nomEntity = TaProforma.class.getSimpleName();
			
			/** 01/03/2010 modifier les editions (zhaolin) **/
//			preferenceStore = proformaPlugin.getDefault().getPreferenceStore();
//			namePlugin = proformaPlugin.getDefault().getBundle().getSymbolicName();
			/************************************************/
			
		} else if(vue.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_BON_COMMANDE)) {
//			impressionDocument = new fr.legrain.boncde.divers.Impression(vue.getShell());
			impressionDocument=(IImpressionDocumentTiers) listeGestionnaireImpressionDocument.get(TypeDoc.TYPE_BON_COMMANDE_BUNDLEID);
			fichierEdition = ConstEdition.FICHE_FILE_REPORT_BONCDE;
			nomOnglet = "Bon de commande";
			nomEntity = TaBoncde.class.getSimpleName();
			
			/** 01/03/2010 modifier les editions (zhaolin) **/
//			preferenceStore = boncdePlugin.getDefault().getPreferenceStore();
//			namePlugin = boncdePlugin.getDefault().getBundle().getSymbolicName();
			/************************************************/
			
		} else if(vue.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_BON_LIVRAISON)) {
//			impressionDocument = new fr.legrain.bonlivraison.divers.Impression(vue.getShell());
			impressionDocument=(IImpressionDocumentTiers) listeGestionnaireImpressionDocument.get(TypeDoc.TYPE_BON_LIVRAISON_BUNDLEID);
			fichierEdition = ConstEdition.FICHE_FILE_REPORT_BONLIV;
			nomOnglet = "Bon de livraison";
			nomEntity = TaBonliv.class.getSimpleName();
			
			/** 01/03/2010 modifier les editions (zhaolin) **/
//			preferenceStore = BonLivraisonPlugin.getDefault().getPreferenceStore();
//			namePlugin = BonLivraisonPlugin.getDefault().getBundle().getSymbolicName();
			/************************************************/
		}else if(vue.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_ACOMPTE)) {
//			impressionDocument = new Impression(vue.getShell());
			impressionDocument=(IImpressionDocumentTiers) listeGestionnaireImpressionDocument.get(TypeDoc.TYPE_ACOMPTE_BUNDLEID);
			fichierEdition = ConstEdition.FICHE_FILE_REPORT_ACOMPTE;
			nomOnglet = "Acompte";
			nomEntity = TaAcompte.class.getSimpleName();
			
			/** 01/03/2010 modifier les editions (zhaolin) **/
//			preferenceStore = fr.legrain.acompte.pluginAcompte.getDefault().getPreferenceStore();
//			namePlugin = fr.legrain.acompte.pluginAcompte.getDefault().getBundle().getSymbolicName();
			/************************************************/
			
		}
		else if(vue.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_AVIS_ECHEANCE)) {
//			impressionDocument = new Impression(vue.getShell());
			impressionDocument=(IImpressionDocumentTiers) listeGestionnaireImpressionDocument.get(TypeDoc.TYPE_AVIS_ECHEANCE_BUNDLEID);
			fichierEdition = ConstEdition.FICHE_FILE_REPORT_AVIS_ECHEANCE;
			nomOnglet = "Avis d'échéance";
			nomEntity = TaAvisEcheance.class.getSimpleName();
			
			/** 01/03/2010 modifier les editions (zhaolin) **/
//			preferenceStore = fr.legrain.acompte.pluginAcompte.getDefault().getPreferenceStore();
//			namePlugin = fr.legrain.acompte.pluginAcompte.getDefault().getBundle().getSymbolicName();
			/************************************************/
			
		}
		else if(vue.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_PRELEVEMENT)) {
//			impressionDocument = new Impression(vue.getShell());
			impressionDocument=(IImpressionDocumentTiers) listeGestionnaireImpressionDocument.get(TypeDoc.TYPE_PRELEVEMENT_BUNDLEID);
			fichierEdition = ConstEdition.FICHE_FILE_REPORT_PRELEVEMENT;
			nomOnglet = "Avis prélèvement";
			nomEntity = TaPrelevement.class.getSimpleName();
			
			/** 01/03/2010 modifier les editions (zhaolin) **/
//			preferenceStore = fr.legrain.acompte.pluginAcompte.getDefault().getPreferenceStore();
//			namePlugin = fr.legrain.acompte.pluginAcompte.getDefault().getBundle().getSymbolicName();
			/************************************************/
			
		}
		preferenceStore=impressionDocument.getPreferenceStore();
		namePlugin = impressionDocument.getPluginName();
		
		final String finalFichierEdition = fichierEdition;
		final String finalNomOnglet = nomOnglet;
		final String finalNomEntity = nomEntity;
		final String finalTypeTraite = typeTraite;
		
		/** 01/03/2010 modifier les editions (zhaolin) **/
		final String finalNamePlugin = namePlugin;
		final IPreferenceStore finalPreferenceStore = preferenceStore;
		final ConstEdition constEdition = new ConstEdition(null);
//		impressionEdition = new BaseImpressionEdition(vue.getShell(),null);
		
//		impressionEdition.setConstEdition(constEdition);
//		impressionEdition.setCollection(listDocumentAImprimer);
		/************************************************/
//		vue.getParent().getDisplay().asyncExec(new Thread() {
//			public void run() {
//				try {	
//
//				
//					if(gestionnaireTraite!=null && listeTraite.size()>0){
//						((IEditionTraite)gestionnaireTraite).
//						ImpressionTraite(listeTraite,impressionEdition);
//					}
//
//					
//					
//
//				} catch (Exception e) {
//					logger.error("Erreur à l'impression ",e);
//				} finally {
//				}
//			}
//		});
		vue.getParent().getDisplay().asyncExec(new Thread() {
			public void run() {
				try {	
					impressionEdition.setConstEdition(constEdition);
					impressionEdition.setCollection(finalListDocumentAImprimer);
					impressionEdition.setTypeTraite(finalTypeTraite);
					impressionEdition.impressionEdition(finalPreferenceStore, finalNomEntity,/*true,*/ 
					        null, finalNamePlugin, finalFichierEdition, true, 
					        false, true, true , preview,printDirect,"");
					
					

				} catch (Exception e) {
					logger.error("Erreur à l'impression ",e);
				} finally {
				}
			}
		});
		actFermer();

	}

	@Override
	protected void actAide() throws Exception {
		actAide(null);
	}


	@Override
	protected void actAide(String message) throws Exception {
//	VerrouInterface.setVerrouille(true);
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
		Class nomClass=null;
		
			if (getFocusCourantSWT().equals(vue.getTfNumDeb())||
					getFocusCourantSWT().equals(vue.getTfNumFin())) {
				int typeDoc = vue.getComboTypeDoc().getSelectionIndex();
				AbstractLgrDAO dao = null;
				paramAfficheAideRecherche.setAfficheDetail(false);
				paramAfficheAideRecherche.setForceAffichageAideRemplie(false);
				if(vue.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_FACTURE)) {
					dao =new TaFactureDAO(getEm());
					nomClass=TaFacture.class;
					paramAfficheAideRecherche.setCleListeTitre("SWTPaEditorFactureController");
					paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaFactureDAO,TaFacture>(dao,IHMAideFacture.class));
				}
				if(vue.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_APPORTEUR)) {
					dao =new TaApporteurDAO(getEm());
					nomClass=TaApporteur.class;
					paramAfficheAideRecherche.setCleListeTitre("PaEditorApporteurController");
					paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaApporteurDAO,TaApporteur>(dao,IHMAideFacture.class));
				}
				if(vue.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_AVOIR)) {
					dao =new TaAvoirDAO(getEm());
					nomClass=TaAvoir.class;
					paramAfficheAideRecherche.setCleListeTitre("PaEditorAvoirController");
					paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaAvoirDAO,TaAvoir>(dao,IHMAideFacture.class));
				}
				if(vue.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_BON_COMMANDE)) {
					dao =new TaBoncdeDAO(getEm());
					nomClass=TaBoncde.class;
					paramAfficheAideRecherche.setCleListeTitre("PaEditorBoncdeController");
					paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaBoncdeDAO,TaBoncde>(dao,IHMAideFacture.class));
				}
				if(vue.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_BON_LIVRAISON)) {
					dao =new TaBonlivDAO(getEm());
					nomClass=TaBonliv.class;
					paramAfficheAideRecherche.setCleListeTitre("PaEditorBLController");
					paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaBonlivDAO,TaBonliv>(dao,IHMAideFacture.class));
				}
				if(vue.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_DEVIS)) {
					dao =new TaDevisDAO(getEm());
					nomClass=TaDevis.class;
					paramAfficheAideRecherche.setCleListeTitre("PaEditorDevisController");
					paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaDevisDAO,TaDevis>(dao,IHMAideFacture.class));
				}
				if(vue.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_PROFORMA)) {
					dao =new TaProformaDAO(getEm());
					nomClass=TaProforma.class;
					paramAfficheAideRecherche.setCleListeTitre("PaEditorProformaController");
					paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaProformaDAO,TaProforma>(dao,IHMAideFacture.class));
				}
				if(vue.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_ACOMPTE)) {
					dao =new TaAcompteDAO(getEm());
					nomClass=TaAcompte.class;
					paramAfficheAideRecherche.setCleListeTitre("PaEditorAcompteController");
					paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaAcompteDAO,TaAcompte>(dao,IHMAideFacture.class));
				}
				if(vue.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_PRELEVEMENT)) {
					dao =new TaPrelevementDAO(getEm());
					nomClass=TaPrelevement.class;
					paramAfficheAideRecherche.setCleListeTitre("PaEditorProformaController");
					paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaPrelevementDAO,TaPrelevement>(dao,IHMAideFacture.class));
				}
				if(vue.getComboTypeDoc().getItem(typeDoc).equals(TypeDoc.TYPE_AVIS_ECHEANCE)) {
					dao =new TaAvisEcheanceDAO(getEm());
					nomClass=TaAvisEcheance.class;
					paramAfficheAideRecherche.setCleListeTitre("PaEditorAvisEcheanceController");
					paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaAvisEcheanceDAO,TaAvisEcheance>(dao,IHMAideFacture.class));
				}				
				paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
				controllerEcranCreation = this;
				paramAfficheAideRecherche.setTypeEntite(nomClass);
				paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_DOCUMENT);
				if(getFocusCourantSWT().equals(vue.getTfNumDeb())){
					paramAfficheAideRecherche.setDebutRecherche(vue.getTfNumDeb().getText());
				} else if(getFocusCourantSWT().equals(vue.getTfNumFin())){
					paramAfficheAideRecherche.setDebutRecherche(vue.getTfNumFin().getText());
				}
				paramAfficheAideRecherche.setControllerAppelant(SWTPaImpressionDocumentController.this);
				paramAfficheAideRecherche.setTypeObjet(IHMAideFacture.class);
				paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_DOCUMENT);
				paramAfficheAideRecherche.setForceAffichageAideRemplie(false);
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
//		VerrouInterface.setVerrouille(false);
		vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
	}
}
	@Override
	protected void actEnregistrer() throws Exception{}

	public AbstractLgrDAO getDao() {
		return ((AbstractLgrDAO) dao);
	}

	@Override
	public void initEtatComposant() {}


	@Override
	protected void actRefresh() throws Exception {
	}

	@Override
	protected void initMapComposantDecoratedField() {
	}

	@Override
	protected void sortieChamps() {
	}
	
	private void createContributors() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		
		IExtensionPoint pointTraite = registry.getExtensionPoint("GestionCommerciale.ImpressionTraite"); //$NON-NLS-1$
		IExtensionPoint pointImpressionDocument = registry.getExtensionPoint("GestionCommerciale.ImpressionDocument"); //$NON-NLS-1$
		//gestion des traites
		if (pointTraite != null) {
			ImageDescriptor icon = null;
			IExtension[] extensions = pointTraite.getExtensions();
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
		//gestion des impressions de document
		if (pointImpressionDocument != null) {
			ImageDescriptor icon = null;
			IExtension[] extensions = pointImpressionDocument.getExtensions();
			for (int i = 0; i < extensions.length; i++) {
				IConfigurationElement confElements[] = extensions[i].getConfigurationElements();
				for (int jj = 0; jj < confElements.length; jj++) {
					try {
						String ClassImpressionDocument = confElements[jj].getAttribute("ClassImpressionDocument");//$NON-NLS-1$
						String typeDoc = confElements[jj].getAttribute("TypeDoc");//$NON-NLS-1$
						String contributorName = confElements[jj].getContributor().getName();
						
						if (ClassImpressionDocument == null )
							continue;
						Object classImpression=confElements[jj].createExecutableExtension("ClassImpressionDocument");
						if(classImpression!=null)
						listeGestionnaireImpressionDocument.put(typeDoc,classImpression );

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}

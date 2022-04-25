package fr.legrain.generationdocumentLGR.controllers;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.preference.IPreferenceStore;
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
import org.eclipse.ui.ActiveShellExpression;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.internal.Workbench;

import fr.legrain.document.DocumentPlugin;
import fr.legrain.document.divers.IImpressionDocumentTiers;
import fr.legrain.document.divers.TypeDoc;
import fr.legrain.document.preferences.PreferenceConstants;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.edition.actions.BaseImpressionEdition;
import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.facture.FacturePlugin;
import fr.legrain.generationdocumentLGR.divers.ParamImpressionFacture;
import fr.legrain.generationdocumentLGR.ecran.PaImpressionFactureSWT;
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
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.LgrSimpleTextContentProposal;
import fr.legrain.lib.data.LibChaine;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.gui.DestroyEvent;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.lib.gui.aide.PaAideRechercheSWT;
import fr.legrain.lib.gui.aide.PaAideSWT;
import fr.legrain.lib.gui.aide.ParamAfficheAide;
import fr.legrain.libMessageLGR.LgrMess;


public class SWTPaImpressionFactureController extends JPABaseControllerSWTStandard implements
RetourEcranListener {
	
	static Logger logger = Logger.getLogger(SWTPaImpressionFactureController.class.getName());		
	private PaImpressionFactureSWT vue = null; // vue	
	private TaFactureDAO dao = new TaFactureDAO();
	private IImpressionDocumentTiers impressionDocument = null;  
	private BaseImpressionEdition impressionEdition = null;
	private Object ecranAppelant = null;
	
	public SWTPaImpressionFactureController(PaImpressionFactureSWT vue) {
		this(vue,null);
	}
	
	public SWTPaImpressionFactureController(PaImpressionFactureSWT vue,EntityManager em) {
		try{
			vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
			if(em!=null) {
			setEm(em);
		}
		dao = new TaFactureDAO(getEm());
		try {
			setVue(vue);
			this.vue=vue;
			vue.getShell().addShellListener(this);
			impressionDocument = new fr.legrain.facture.divers.Impression(vue.getShell());
//			 Branchement action annuler : empeche la fermeture automatique de la
			// fenetre sur ESC
			vue.getShell().addTraverseListener(new Traverse());
			actionImprimer.setText("Exporter [F11]");
			
			
			vue.getTfDATEDEB().addSelectionListener(new LgrModifyListener());
			vue.getTfNumDeb().addSelectionListener(new LgrModifyListener());

			vue.getTfDATEFIN().setEnabled(true);
			vue.getTfNumFin().setEnabled(true);
			
			initController();
		} catch (Exception e) {
			logger.error("Erreur ", e);
		}
	}finally{
		vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
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
					LibDateTime.setDateNull(vue.getTfDATEDEB()); vue.getTfDATEDEB().setEnabled(false);
					LibDateTime.setDateNull(vue.getTfDATEFIN()); vue.getTfDATEFIN().setEnabled(false);
				} else {
					vue.getTfNumFin().setText(""); vue.getTfNumFin().setEnabled(false);
					LibDateTime.setDateNull(vue.getTfDATEDEB()); vue.getTfDATEDEB().setEnabled(true);
					LibDateTime.setDateNull(vue.getTfDATEFIN()); vue.getTfDATEFIN().setEnabled(true);
				}
			}
			if(e.getSource().equals(vue.getTfDATEDEB())) {
				//if(!LibChaine.empty(vue.getTfDATEDEB().getText())) {
				if(!LibDateTime.isDateNull(vue.getTfDATEDEB())) {
					vue.getTfDATEFIN().setEnabled(true);
					vue.getTfNumDeb().setText(""); vue.getTfNumDeb().setEnabled(false);
					vue.getTfNumFin().setText(""); vue.getTfNumFin().setEnabled(false);
				} else {
					LibDateTime.setDateNull(vue.getTfDATEDEB());
					LibDateTime.setDateNull(vue.getTfDATEFIN()); vue.getTfDATEFIN().setEnabled(false);
					vue.getTfNumDeb().setEnabled(true);
				}
			}
		} catch (Exception e1) {
			logger.error(e1);
		}		
	}
	private void initController() {
		try {
			setDaoStandard(((AbstractLgrDAO) dao));
			vue.getCbReExport().setSelection(true);
			vue.getCbPrintExport().setSelection(false);
			//			ibTaTableStandard=dao;
			//			addDestroyListener(dao);


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
		listeComposantFocusable.add(vue.getTfNumDeb());
		listeComposantFocusable.add(vue.getTfNumFin());
		listeComposantFocusable.add(vue.getTfDATEDEB());
		listeComposantFocusable.add(vue.getTfDATEFIN());			
		
		listeComposantFocusable.add(vue.getPaBtn1().getBtnImprimer());
		listeComposantFocusable.add(vue.getPaBtn1().getBtnFermer());
		
		if(mapInitFocus == null) 
			mapInitFocus = new LinkedHashMap();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION,vue.getTfNumDeb());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION,vue.getTfNumDeb());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION,vue.getTfNumDeb());
		
		
		String activationContentProposal = "Ctrl+Space";
		//String activationContentProposal = C_COMMAND_GLOBAL_AIDE_ID;
		
		ContentProposalAdapter codeFactureContentProposalDebut = LgrSimpleTextContentProposal.
		defaultTextContentProposalKey(vue.getTfNumDeb(),activationContentProposal,initContentProposalFacture(),null);
		LgrSimpleTextContentProposal.defaultOptions(codeFactureContentProposalDebut);

		ContentProposalAdapter codeFactureContentProposalFin = LgrSimpleTextContentProposal.
		defaultTextContentProposalKey(vue.getTfNumFin(),activationContentProposal,initContentProposalFacture(),null);
		LgrSimpleTextContentProposal.defaultOptions(codeFactureContentProposalFin);

	}
	
	public String[][] initContentProposalFacture(){
		String[][] valeurs = null;
		boolean affichageCtrlEspace = GestionCommercialePlugin.getDefault().getPreferenceStore().getBoolean(fr.legrain.gestionCommerciale.preferences.PreferenceConstants.AFFICHAGE_CTRL_ESPACE);
		if(affichageCtrlEspace){
		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
		List<TaFacture> l =  dao.rechercheFactureMaintenance(taInfoEntreprise.getDatedebInfoEntreprise(),
				taInfoEntreprise.getDatefinInfoEntreprise());
		valeurs = new String[l.size()][2];
		int i = 0;
		String description = "";
		for (TaFacture doc : l) {
			valeurs[i][0] = doc.getCodeDocument();

			description = "";
			description += doc.getLibelleDocument();
			valeurs[i][1] = description;

			i++;
		}
		}
		return valeurs;		
	}
	

	
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
	
	public SWTPaImpressionFactureController getThis(){
		return this;
	}
	
	@Override
	public boolean onClose() throws ExceptLgr {
		boolean retour = true;
		switch (((AbstractLgrDAO) dao).getModeObjet().getMode()) {
		case C_MO_INSERTION:
		case C_MO_EDITION:
			break;
		case C_MO_CONSULTATION:
			break;
		default:
			break;
		}

		if (retour) {
			if (ecranAppelant instanceof SWTPaAideControllerSWT) {
				retour = false;
			}
		}
		if (retour && !(ecranAppelant instanceof SWTPaAideControllerSWT)) {
			fireDestroy(new DestroyEvent(dao));
		}
		return retour;
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
		List<TaFacture> idFactureAImprimer = null;
		final boolean preview = vue.getCbReExport().getSelection();
		final boolean print = vue.getCbPrintExport().getSelection();
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
		if(!LibDateTime.isDateNull(vue.getTfDATEDEB())) {
				dateDeb = LibDateTime.getDate(vue.getTfDATEDEB());
		}
//		if( (!LibChaine.empty(vue.getTfDATEFIN().getText())&& 
//				!vue.getTfDATEFIN().getText().equals("<choisir une date>")
//				&& vue.getTfDATEFIN().getText()!=null) ) {
		if(!LibDateTime.isDateNull(vue.getTfDATEFIN())) {
				dateFin = LibDateTime.getDate(vue.getTfDATEFIN());
		}
		
		if(codeDeb!=null) {//1 code => cette facture
			logger.debug("Exportation - selection par code");
			if(codeFin!=null) {//2 codes => factures entre les 2 codes si intervalle correct
				idFactureAImprimer = ((TaFactureDAO)dao).rechercheFactureMaintenance(codeDeb,codeFin);
			} else {
				codeFin = codeDeb;
				idFactureAImprimer = ((TaFactureDAO)dao).rechercheFactureMaintenance(codeDeb,codeFin);
			}
		} else 
			if(dateDeb!=null) {//1 date => toutes les factures à cette date
			logger.debug("Exportation - selection par date");
			if(dateFin!=null) {//2 dates => factures entre les 2 dates si intervalle correct
				if(dateDeb.compareTo(dateFin)>0) {
					MessageDialog.openWarning(Workbench.getInstance()
							.getActiveWorkbenchWindow().getShell(),
							"ATTENTION", "La date de début doit être antérieure à la date fin");
					throw new Exception("probleme intervalle des dates");
				}
				idFactureAImprimer = ((TaFactureDAO)dao).rechercheFactureMaintenance(dateDeb,dateFin);
			} else {
				dateFin = dateDeb;
				idFactureAImprimer = ((TaFactureDAO)dao).rechercheFactureMaintenance(dateDeb,dateFin);
			}
		} 
		///////////////////////////////////////

		final List<TaFacture> finalListDocumentAImprimer = idFactureAImprimer;
		final boolean finalPreview = preview;
		String fichierEdition = null;
		String nomOnglet = null;
		String nomEntity = null;

		
		/** 01/03/2010 modifier les editions (zhaolin) **/
		IPreferenceStore preferenceStore = null;
		String namePlugin = null;
		/************************************************/

			//impressionDocument = new fr.legrain.facture.divers.Impression(vue.getShell());
			fichierEdition = ConstEdition.FICHE_FILE_REPORT_FACTURE;
			nomOnglet = "Facture";
			nomEntity = TaFacture.class.getSimpleName();
			
			/** 01/03/2010 modifier les editions (zhaolin) **/
			preferenceStore = FacturePlugin.getDefault().getPreferenceStore();
			namePlugin = FacturePlugin.getDefault().getBundle().getSymbolicName();
			/************************************************/
			final String finalFichierEdition = fichierEdition;
			final String finalNomOnglet = nomOnglet;
			final String finalNomEntity = nomEntity;
			
			/** 01/03/2010 modifier les editions (zhaolin) **/
			final String finalNamePlugin = namePlugin;
			final IPreferenceStore finalPreferenceStore = preferenceStore;
			ConstEdition constEdition = new ConstEdition(null);
			impressionEdition = new BaseImpressionEdition(vue.getShell(),null);
			impressionEdition.setConstEdition(constEdition);
			impressionEdition.setCollection(finalListDocumentAImprimer);
			/************************************************/
			vue.getParent().getDisplay().asyncExec(new Thread() {
				public void run() {
					try {	
						
						
//						impressionFacture.imprimerSelection(finalListFactureAImprimer,finalPreview);
//		  			    #JPA
//						dao.commitLgr();
						
//						impressionDocument.imprimerChoix(finalFichierEdition,finalNomOnglet,
//																finalListDocumentAImprimer,finalNomEntity,
//																preview,printDirect);
						
						impressionEdition.impressionEdition(finalPreferenceStore, finalNomEntity,/*true,*/ 
													        null, finalNamePlugin, finalFichierEdition, true, 
													        false, true, true , preview,print,"");
						
					} catch (Exception e) {
						logger.error("Erreur à l'impression ",e);
					} finally {
					}
				}
			});
			//		vue.getShell().setVisible(false);
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


			if (getFocusCourantSWT().equals(vue.getTfNumDeb())||
					getFocusCourantSWT().equals(vue.getTfNumFin())) {
				TaFactureDAO 	dao =new TaFactureDAO(getEm());
				paramAfficheAideRecherche.setForceAffichageAideRemplie(false);
				paramAfficheAideRecherche.setCleListeTitre("SWTPaEditorFactureController");
				TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
				TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
				List<TaFacture> l =  dao.rechercheFactureMaintenance(taInfoEntreprise.getDatedebInfoEntreprise(),
						taInfoEntreprise.getDatefinInfoEntreprise());
//				paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture, AbstractLgrDAO<TaFacture>, TaFacture>(l, IHMAideFacture.class, getEm()));
				paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaFactureDAO,TaFacture>(dao,IHMAideFacture.class));				
				dao.setJPQLQuery(dao.FIND_BY_MAINTENANCE);
				paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
				controllerEcranCreation = this;
				paramAfficheAideRecherche.setTypeEntite(TaFacture.class);
				paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_DOCUMENT);
				paramAfficheAideRecherche.setDebutRecherche(vue.getTfNumDeb().getText());
				paramAfficheAideRecherche.setControllerAppelant(SWTPaImpressionFactureController.this);
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
	


	
}

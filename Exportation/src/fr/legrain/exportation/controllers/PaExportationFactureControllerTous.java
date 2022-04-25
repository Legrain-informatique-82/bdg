package fr.legrain.exportation.controllers;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;

import fr.legrain.documents.dao.TaAcompte;
import fr.legrain.documents.dao.TaAcompteDAO;
import fr.legrain.documents.dao.TaApporteur;
import fr.legrain.documents.dao.TaApporteurDAO;
import fr.legrain.documents.dao.TaAvoir;
import fr.legrain.documents.dao.TaAvoirDAO;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaReglement;
import fr.legrain.documents.dao.TaReglementDAO;
import fr.legrain.documents.dao.TaRemise;
import fr.legrain.documents.dao.TaRemiseDAO;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.exportation.ExportationPlugin;
import fr.legrain.exportation.divers.DeclencheInitBorneControllerEvent;
import fr.legrain.exportation.divers.ExportationEpicea;
import fr.legrain.exportation.divers.IDeclencheInitBorneControllerListener;
import fr.legrain.exportation.divers.OptionExportation;
import fr.legrain.exportation.ecrans.PaExportationFactureOption;
import fr.legrain.exportation.ecrans.PaExportationFactureTous;
import fr.legrain.exportation.preferences.PreferenceConstants;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDePageEvent;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;


public class PaExportationFactureControllerTous extends JPABaseControllerSWTStandard implements
RetourEcranListener,IDeclencheInitBorneControllerListener {
	
	static Logger logger = Logger.getLogger(PaExportationFactureControllerTous.class.getName());		
	private PaExportationFactureTous vue = null; 
	
	private TaFactureDAO daoFacture = null;
	private TaAvoirDAO daoAvoir = null;
	private TaAcompteDAO daoAcompte = null;
	private TaReglementDAO daoReglement = null;
	private TaRemiseDAO daoRemise = null;
	private TaApporteurDAO daoApporteur = null;
	
	
	String [][] listeDocumentsFactures=null;
	String [][] listeDocumentsAvoirs=null;
	String [][] listeDocumentsApporteurs=null;
	String [][] listeDocumentsAcomptes=null;
	String [][] listeDocumentsReglements=null;
	String [][] listeDocumentsRemises=null;
	
	private Object ecranAppelant = null;
	private PaExportationFactureOption paExportationFactureOption=null;
	private PaExportationFactureControllerOption paExportationFactureControllerOption = null;
	
	public static final String C_COMMAND_DOCUMENT_SUIVANT_REFERENCE_ID = "fr.legrain.Document.suivantReference";
	protected HandlerSuivantReference handlerSuivantReference = new HandlerSuivantReference();
	
	public static final String C_COMMAND_DOCUMENT_SUIVANT_DATES_ID = "fr.legrain.Document.suivantDates";
	protected HandlerSuivantDates handlerSuivantDates = new HandlerSuivantDates();
	
	public static final String C_COMMAND_DOCUMENT_LISTE_DOCUMENT_ID = "fr.legrain.Document.listeDocument";
	protected HandlerListeDocument handlerListeDocument = new HandlerListeDocument();
	
	public PaExportationFactureControllerTous(PaExportationFactureTous vue) {
		this(vue,null);
	}
	
	public PaExportationFactureControllerTous(PaExportationFactureTous vue,EntityManager em) {
		initCursor(SWT.CURSOR_WAIT);
		if(em!=null) {
			setEm(em);
		}
		daoFacture = new TaFactureDAO(getEm());
		daoAvoir = new TaAvoirDAO(getEm());
		daoApporteur = new TaApporteurDAO(getEm());
		daoAcompte = new TaAcompteDAO(getEm());
		daoReglement = new TaReglementDAO(getEm());
		daoRemise=new TaRemiseDAO(getEm());
		try {
			setVue(vue);
			this.vue=vue;
			vue.getShell().addShellListener(this);
			
//			 Branchement action annuler : empeche la fermeture automatique de la
			// fenetre sur ESC
			vue.getShell().addTraverseListener(new Traverse());
			actionImprimer.setText("Exporter [F11]");
			
			
			initController();
		} catch (Exception e) {
			logger.error("Erreur ", e);
		}
		initCursor(SWT.CURSOR_ARROW);
	}
	
	private class HandlerSuivantReference extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				actSuivantReference();
			} catch (Exception e) {
				logger.error("", e);
			}
			return event;
		}
	}
	//#AFAIRE
	private void actSuivantReference() throws Exception {
		ChangementDePageEvent change = new ChangementDePageEvent(this,ChangementDePageEvent.AUTRE,1);
		fireChangementDePage(change);
	}
	private class HandlerSuivantDates extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				actSuivantDates();
			} catch (Exception e) {
				logger.error("", e);
			}
			return event;
		}
	}
	
	//#AFAIRE
	private void actSuivantDates() throws Exception {
		ChangementDePageEvent change = new ChangementDePageEvent(this,ChangementDePageEvent.AUTRE,2);
		fireChangementDePage(change);
	}
	private class HandlerListeDocument extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
								ChangementDePageEvent change = new ChangementDePageEvent(this,ChangementDePageEvent.AUTRE,3,0);
								fireChangementDePage(change);

			} catch (Exception e) {
				logger.error("", e);
			}
			return event;
		}
	}
	
	
	private void initVue(){
		final String C_IMAGE_OPTION = "/icons/logo_lgr_16.png";
		paExportationFactureOption = new PaExportationFactureOption(vue.getExpandBarOption(), SWT.PUSH);
		setPaExportationFactureControllerOption(new PaExportationFactureControllerOption(paExportationFactureOption,getEm()));
		addExpandBarItem(vue.getExpandBarOption(), paExportationFactureOption, "Options d'Exportation",
				ExportationPlugin.getImageDescriptor(C_IMAGE_OPTION).createImage());
		getPaExportationFactureControllerOption().addDeclencheInitBorneControllerListener(this);
	}
	
	private void initController() {
		try {
			setDaoStandard(daoFacture);
			vue.getCbReglement().setSelection(ExportationPlugin.getDefault().
					getPreferenceStoreProject().getBoolean(PreferenceConstants.REGLEMENTS_LIES));
			
			vue.getCbPointages().setSelection(ExportationPlugin.getDefault().
					getPreferenceStoreProject().getBoolean(PreferenceConstants.POINTAGES));
		
			vue.getCbCentralisation().setSelection(ExportationPlugin.getDefault().
					getPreferenceStoreProject().getBoolean(PreferenceConstants.CENTRALISATION));
			
			vue.getCbApporteurs().setSelection(ExportationPlugin.getDefault().
					getPreferenceStoreProject().getBoolean(PreferenceConstants.APPORTEURS));			
			
			initVue();
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

			actRefresh();
			initEtatBouton();
			vue.getPaEcran().layout();
			vue.getScrolledComposite().setMinSize(vue.getPaEcran().computeSize(SWT.DEFAULT, SWT.DEFAULT));

		} catch (Exception e) {
//			vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaArticlesController", e);
		}
	}

	@Override
	protected void initImageBouton() {
		final String C_IMAGE_EXPORT = "/icons/export_wiz.gif";
		vue.getBtnFermer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_FERMER));
		vue.getBtnExporter().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_EXPORT));
		vue.getBtnDates().setImage(ExportationPlugin.getImageDescriptor(C_IMAGE_EXPORT).createImage());
		vue.getBtnReference().setImage(ExportationPlugin.getImageDescriptor(C_IMAGE_EXPORT).createImage());
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
			param.setFocus(daoFacture.getModeObjet().getFocusCourant());
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
		boolean reglement=getPaExportationFactureControllerOption().getVue().getCbAcompte().getSelection()||
		getPaExportationFactureControllerOption().getVue().getCbReglementSimple().getSelection()||
		getPaExportationFactureControllerOption().getVue().getCbRemise().getSelection();
		trouve=(listeDocumentsFactures!=null&&listeDocumentsFactures.length>0) || 
		(listeDocumentsAvoirs!=null&&listeDocumentsAvoirs.length>0) ||
		(listeDocumentsApporteurs!=null&&listeDocumentsApporteurs.length>0) ||
		(listeDocumentsAcomptes!=null&&listeDocumentsAcomptes.length>0)	 || 
		(listeDocumentsReglements!=null&&listeDocumentsReglements.length>0) ||
		(listeDocumentsRemises!=null&&listeDocumentsRemises.length>0);

		enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID, trouve);
		enableActionAndHandler(C_COMMAND_DOCUMENT_SUIVANT_REFERENCE_ID, true);
		enableActionAndHandler(C_COMMAND_DOCUMENT_SUIVANT_DATES_ID, true);
		enableActionAndHandler(C_COMMAND_DOCUMENT_LISTE_DOCUMENT_ID, true);
		initFocusSWT(daoFacture,mapInitFocus);
		findExpandIem(vue.getExpandBarOption(), paExportationFactureControllerOption.getVue()).setExpanded(vue.getCbReglement().getSelection());
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
			
		
		listeComposantFocusable.add(vue.getBtnExporter());
		listeComposantFocusable.add(vue.getCbReglement());
		listeComposantFocusable.add(vue.getBtnReference());
		listeComposantFocusable.add(vue.getBtnDates());
		listeComposantFocusable.add(vue.getBtnListeDoc());
		listeComposantFocusable.add(vue.getBtnFermer());

		if(mapInitFocus == null) 
			mapInitFocus = new LinkedHashMap();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION,vue.getBtnExporter());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION,vue.getBtnExporter());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION,vue.getBtnExporter());
		
		
		vue.getCbPointages().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ExportationPlugin.getDefault().getPreferenceStoreProject().setValue(
						PreferenceConstants.POINTAGES, vue.getCbPointages().getSelection());
				try {
					paExportationFactureControllerOption.initEcranSurPreferences(PreferenceConstants.POINTAGES);
				} catch (Exception e1) {
					logger.error("", e1);
				}
			}
		});
		vue.getCbReglement().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				vue.getCbPointages().setSelection(vue.getCbReglement().getSelection());
				ExportationPlugin.getDefault().getPreferenceStoreProject().setValue(
						PreferenceConstants.REGLEMENTS_LIES, vue.getCbReglement().getSelection());
				paExportationFactureControllerOption.griserTout(vue.getCbReglement().getSelection());
				findExpandIem(vue.getExpandBarOption(), paExportationFactureControllerOption.getVue()).setExpanded(vue.getCbReglement().getSelection());
				DeclencheInitBorneController(new DeclencheInitBorneControllerEvent(this));
				try {
					paExportationFactureControllerOption.actRefresh();
				} catch (Exception e1) {
					logger.error("", e1);
				}
			}
		});
		
		vue.getCbCentralisation().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {	
				ExportationPlugin.getDefault().getPreferenceStoreProject().
				setValue(PreferenceConstants.CENTRALISATION, vue.getCbCentralisation().getSelection());

			} catch (Exception e1) {
				logger.error("", e1);
			}
			}
		});
		
		vue.getCbApporteurs().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {	
				ExportationPlugin.getDefault().getPreferenceStoreProject().
				setValue(PreferenceConstants.APPORTEURS, vue.getCbApporteurs().getSelection());
				DeclencheInitBorneController(new DeclencheInitBorneControllerEvent(this));
			} catch (Exception e1) {
				logger.error("", e1);
			}
			}
		});
		
	}
	
	
	protected void initActions() {
		mapCommand = new HashMap<String, IHandler>();

		mapCommand.put(C_COMMAND_GLOBAL_ANNULER_ID, handlerAnnuler);
		mapCommand.put(C_COMMAND_GLOBAL_AIDE_ID, handlerAide);
		mapCommand.put(C_COMMAND_GLOBAL_FERMER_ID, handlerFermer);
		mapCommand.put(C_COMMAND_GLOBAL_IMPRIMER_ID, handlerImprimer);
		mapCommand.put(C_COMMAND_DOCUMENT_SUIVANT_REFERENCE_ID, handlerSuivantReference);
		mapCommand.put(C_COMMAND_DOCUMENT_SUIVANT_DATES_ID, handlerSuivantDates);
		mapCommand.put(C_COMMAND_DOCUMENT_LISTE_DOCUMENT_ID, handlerListeDocument);
		
		initFocusCommand(String.valueOf(this.hashCode()),
				listeComposantFocusable, mapCommand);
		
		if (mapActions == null)
			mapActions = new LinkedHashMap();
		
		mapActions.put(vue.getBtnExporter(), C_COMMAND_GLOBAL_IMPRIMER_ID);
		mapActions.put(vue.getBtnDates(), C_COMMAND_DOCUMENT_SUIVANT_DATES_ID);
		mapActions.put(vue.getBtnReference(), C_COMMAND_DOCUMENT_SUIVANT_REFERENCE_ID);
		mapActions.put(vue.getBtnListeDoc(), C_COMMAND_DOCUMENT_LISTE_DOCUMENT_ID);
		mapActions.put(vue.getBtnFermer(), C_COMMAND_GLOBAL_FERMER_ID);
//		
		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID };
		mapActions.put(null, tabActionsAutres);
	}
	
	public PaExportationFactureControllerTous getThis(){
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

	}
	
	@Override
	protected void actInserer() throws Exception{}
	
	@Override
	protected void actModifier() throws Exception{}
	
	@Override
	protected void actSupprimer() throws Exception{}
	
	@Override
	protected void actFermer() throws Exception {
		// (controles de sortie et fermeture de la fenetre) => onClose()
		if (onClose()) {
			closeWorkbenchPart();
		}
	}
	
	@Override
	protected void actAnnuler() throws Exception{
		if (focusDansEcran())actionFermer.run();
	}
	
	@Override
	protected void actImprimer() throws Exception {
		//Récupération des paramètres dans l'ihm

		List<TaFacture> idFactureAExporter = null;
		List<TaAvoir> idAvoirAExporter = null;
		List<TaApporteur> idApporteurAExporter = null;
		List<TaAcompte> idAcompteAExporter = null;
		List<TaReglement> idReglementAExporter = null;
		List<TaRemise> idRemiseAExporter = null;
		
		String codeDeb = null;
		String codeFin = null;
		String codeDebAvoir = null;
		String codeFinAvoir = null;
		String codeDebApporteur = null;
		String codeFinApporteur = null;		
		String codeDebAcompte = null;
		String codeFinAcompte = null;
		String codeDebReglement = null;
		String codeFinReglement = null;
		String codeDebRemise = null;
		String codeFinRemise = null;
		

		if(listeDocumentsFactures.length>0){
			codeDeb = (listeDocumentsFactures[0][0]);

			codeFin = (listeDocumentsFactures[listeDocumentsFactures.length-1][0]);
		}
		if(listeDocumentsAvoirs.length>0){
			codeDebAvoir = (listeDocumentsAvoirs[0][0]);

			codeFinAvoir = (listeDocumentsAvoirs[listeDocumentsAvoirs.length-1][0]);
		}
		if(listeDocumentsApporteurs.length>0){
			codeDebApporteur = (listeDocumentsApporteurs[0][0]);

			codeFinApporteur = (listeDocumentsApporteurs[listeDocumentsApporteurs.length-1][0]);
		}		
		if(vue.getCbReglement().getSelection()){
		if(paExportationFactureControllerOption.getVue().getCbAcompte().getSelection()){
			if(listeDocumentsAcomptes.length>0){
				codeDebAcompte = (listeDocumentsAcomptes[0][0]);

				codeFinAcompte = (listeDocumentsAcomptes[listeDocumentsAcomptes.length-1][0]);
			}
		}
		if(paExportationFactureControllerOption.getVue().getCbReglementSimple().getSelection()){		
			if(listeDocumentsReglements.length>0){
				codeDebReglement = (listeDocumentsReglements[0][0]);

				codeFinReglement = (listeDocumentsReglements[listeDocumentsReglements.length-1][0]);
			}
		}
		if(paExportationFactureControllerOption.getVue().getCbRemise().getSelection()){
			if(listeDocumentsRemises.length>0){
				codeDebRemise = (listeDocumentsRemises[0][0]);

				codeFinRemise = (listeDocumentsRemises[listeDocumentsRemises.length-1][0]);
			}
		}
		}
		
		if(codeDeb!=null || codeDebAvoir!=null || codeDebApporteur!=null || codeDebAcompte!=null || codeDebReglement!=null || codeDebRemise!=null) {//1 code => cette facture
			logger.debug("Exportation - selection par code");
			if(codeDeb!=null) {
				if(codeFin!=null) {//2 codes => factures entre les 2 codes si intervalle correct
					idFactureAExporter = daoFacture.rechercheDocument(codeDeb,codeFin);
				} else {
					codeFin = codeDeb;
					idFactureAExporter = daoFacture.rechercheDocument(codeDeb,codeFin);
				}
			}
			if(codeDebAvoir!=null) {
				if(codeFinAvoir!=null) {//2 codes => factures entre les 2 codes si intervalle correct
					idAvoirAExporter = daoAvoir.rechercheDocument(codeDebAvoir,codeFinAvoir);
				} else {
					codeFinAvoir = codeDebAvoir;
					idAvoirAExporter = daoAvoir.rechercheDocument(codeDebAvoir,codeFinAvoir);
				}
			}
			if(codeDebApporteur!=null) {
				if(codeFinApporteur!=null) {//2 codes => factures entre les 2 codes si intervalle correct
					idApporteurAExporter = daoApporteur.rechercheDocument(codeDebApporteur,codeFinApporteur);
				} else {
					codeFinApporteur = codeDebApporteur;
					idApporteurAExporter = daoApporteur.rechercheDocument(codeDebApporteur,codeFinApporteur);
				}
			}			
			if(codeDebAcompte!=null) {
				if(codeFinAcompte!=null) {//2 codes => factures entre les 2 codes si intervalle correct
					idAcompteAExporter = daoAcompte.rechercheDocument(codeDebAcompte,codeFinAcompte);
				} else {
					codeFinAcompte = codeDebAcompte;
					idAcompteAExporter = daoAcompte.rechercheDocument(codeDebAcompte,codeFinAcompte);
				}
			}
			if(codeDebReglement!=null) {
				if(codeFinReglement!=null) {//2 codes => factures entre les 2 codes si intervalle correct
					idReglementAExporter = daoReglement.rechercheDocument(codeDebReglement,codeFinReglement);
				} else {
					codeFinReglement = codeDebReglement;
					idReglementAExporter = daoReglement.rechercheDocument(codeDebReglement,codeFinReglement);
				}
			}
			if(codeDebRemise!=null) {
				if(codeFinRemise!=null) {//2 codes => factures entre les 2 codes si intervalle correct
					idRemiseAExporter = daoRemise.rechercheDocument(codeDebRemise,codeFinRemise);
				} else {
					codeFinRemise = codeDebRemise;
					idRemiseAExporter = daoRemise.rechercheDocument(codeDebRemise,codeFinRemise);
				}
			}			
		 
			
		} 
		///////////////////////////////////////

		final List<TaFacture> finalIdFactureAExporter = idFactureAExporter;
		final List<TaAvoir> finalIdAvoirAExporter = idAvoirAExporter;
		final List<TaApporteur> finalIdApporteurAExporter = idApporteurAExporter;
		final List<TaAcompte> finalIdAcompteAExporter = idAcompteAExporter;
		final List<TaReglement> finalIdReglementAExporter = idReglementAExporter;
		final List<TaRemise> finalIdRemiseAExporter = idRemiseAExporter;
		
		final boolean finalReExportFacture = false;
		final boolean finalReExportAvoir = false;
		final boolean finalReExportApporteur = false;
		final boolean finalReExportAcompte = false;
		final boolean finalReExportReglement = false;
		final boolean finalReExportRemise = false;
		
		final OptionExportation optionsInitiales = new OptionExportation();
		optionsInitiales.setAcomptes(ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.ACOMPTES));
		optionsInitiales.setApporteurs(ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.APPORTEURS));
		optionsInitiales.setReglementLies(ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.REGLEMENTS_LIES));
		optionsInitiales.setReglementSimple(ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.REGLEMENT_SIMPLE));
		optionsInitiales.setRemise(ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.REMISE));
		optionsInitiales.setDocumentLies(ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.DOCUMENTS_LIES));
		optionsInitiales.setPointages(ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.POINTAGES));
		
		OptionExportation options = new OptionExportation();
		if(vue.getCbReglement().getSelection()){
			options.setAcomptes(optionsInitiales.getAcomptes());
			options.setDocumentLies(optionsInitiales.getDocumentLies());
			options.setReglementLies(optionsInitiales.getReglementLies());
			options.setReglementSimple(optionsInitiales.getReglementSimple());
			options.setRemise(optionsInitiales.getRemise());
		}else{
			options.setAcomptes(false);
			options.setDocumentLies(false);
			options.setReglementLies(false);
			options.setReglementSimple(false);
			options.setRemise(false);
		}
		options.setPointages(vue.getCbPointages().getSelection());

		
		//modifie de façon temporaire les options de transfert
		paExportationFactureControllerOption.initPreferences(options);
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				ExportationEpicea retour;
				try {
					retour = exporter(finalIdFactureAExporter,finalIdAvoirAExporter,finalIdApporteurAExporter,finalIdAcompteAExporter,finalIdReglementAExporter,finalIdRemiseAExporter,
							finalReExportFacture,finalReExportAvoir,finalReExportApporteur,finalReExportAcompte,finalReExportReglement,finalReExportRemise,
							vue.getCbPointages().getSelection(),vue.getCbCentralisation().getSelection());
					if(!retour.getRetour()&& !retour.getMessageRetour().equals(""))
						MessageDialog.openError(vue.getShell(), "Exportation abandonnée", retour.getMessageRetour());
					else
						if(retour.getMessageRetour().equals(""))
							retour.setMessageRetour( "L'exportation est terminée.\nElle s'est déroulée correctement." +
									"\n\rLe fichier se trouve à l'adresse suivante : \n\r"+retour.getLocationFichier());
					MessageDialog.openInformation(vue.getShell(),"Exportation Terminée",retour.getMessageRetour() );
					paExportationFactureControllerOption.initPreferences(optionsInitiales);
				} catch (Exception e) {
					logger.error("", e);
					paExportationFactureControllerOption.initPreferences(optionsInitiales);
				}
			}
		});						
		
		actFermer();
	}
	
	protected ExportationEpicea exporter(List<TaFacture> idFactureAExporter,List<TaAvoir> idAvoirAExporter,List<TaApporteur> idApporteurAExporter,List<TaAcompte> idAcompteAExporter,
			List<TaReglement> idReglementAExporter,List<TaRemise> idRemiseAExporter,boolean reExportFacture,boolean reExportAvoir,boolean reExportApporteur,boolean reExportAcompte,
			boolean reExportReglement,boolean reExportRemise,final boolean gererPointages, final boolean centralisation) throws Exception{

		final List<TaFacture> finalIdFactureAExporter = idFactureAExporter;
		final List<TaAvoir> finalIdAvoirAExporter = idAvoirAExporter;
		final List<TaApporteur> finalIdApporteurAExporter = idApporteurAExporter;
		final List<TaAcompte> finalIdAcompteAExporter = idAcompteAExporter;
		final List<TaReglement> finalIdReglementAExporter = idReglementAExporter;
		final List<TaRemise> finalIdRemiseAExporter = idRemiseAExporter;
		
		final int finalReExportFacture = LibConversion.booleanToInt(reExportFacture);
		final int finalReExportAvoir = LibConversion.booleanToInt(reExportAvoir);
		final int finalReExportApporteur = LibConversion.booleanToInt(reExportApporteur);
		final int finalReExportAcompte = LibConversion.booleanToInt(reExportAcompte);
		final int finalReExportReglement = LibConversion.booleanToInt(reExportReglement);
		final int finalReExportRemise = LibConversion.booleanToInt(reExportRemise);
		
		final ExportationEpicea expEpicea = new ExportationEpicea();

		Job job = new Job("Exportation") {
			protected IStatus run(IProgressMonitor monitor) {
				int nbFacture = 0;
				int nbAvoir = 0;
				int nbApporteur = 0;
				int nbAcompte = 0;
				int nbReglement = 0;
				int nbRemise = 0;

				if(finalIdFactureAExporter!=null) nbFacture = finalIdFactureAExporter.size();
				if(finalIdAvoirAExporter!=null) nbAvoir = finalIdAvoirAExporter.size();
				if(finalIdApporteurAExporter!=null) nbApporteur = finalIdApporteurAExporter.size();
				if(finalIdAcompteAExporter!=null) nbAcompte = finalIdAcompteAExporter.size();
				if(finalIdReglementAExporter!=null) nbReglement = finalIdReglementAExporter.size();
				if(finalIdRemiseAExporter!=null) nbRemise = finalIdRemiseAExporter.size();
				
				final int ticks = nbFacture+nbAvoir+nbApporteur+nbAcompte+nbReglement+nbRemise;
				monitor.beginTask("Exportation", ticks);
				try {
	
					
							expEpicea.exportJPA(finalIdFactureAExporter,finalIdAvoirAExporter,finalIdApporteurAExporter,finalIdAcompteAExporter,finalIdReglementAExporter,
									finalIdRemiseAExporter,monitor,finalReExportFacture,finalReExportAvoir,finalReExportApporteur,finalReExportAcompte,
									finalReExportReglement,finalReExportRemise,gererPointages,centralisation);

				} catch (Exception e) {
				} finally {
					monitor.done();
				}
				return Status.OK_STATUS;
			}
		};
		job.setPriority(Job.SHORT);

		job.schedule(); 
		job.join();
		return expEpicea;	
	}
	
	
	@Override
	protected void actAide() throws Exception {
		actAide(null);
	}
	

	@Override
	protected void actAide(String message) throws Exception {
}

	@Override
	protected void actEnregistrer() throws Exception{}
	
	public TaFactureDAO getDaoFacture() {
		return daoFacture;
	}
	
	@Override
	public void initEtatComposant() {}
	

	@Override
	protected void actRefresh() throws Exception {
		// TODO Raccord de méthode auto-généré
		paExportationFactureControllerOption.actRefresh();
		vue.getCbReglement().setSelection(ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.REGLEMENTS_LIES));
		initProposalAdapter();
		initBornes();
		initEtatBouton();
		paExportationFactureControllerOption.griserTout(vue.getCbReglement().getSelection());
		vue.getCbPointages().setSelection(vue.getCbReglement().getSelection());
	}

	@Override
	protected void initMapComposantDecoratedField() {
		// TODO Raccord de méthode auto-généré
		
	}

	@Override
	protected void sortieChamps() {
		// TODO Raccord de méthode auto-généré
		
	}

	public PaExportationFactureControllerOption getPaExportationFactureControllerOption() {
		return paExportationFactureControllerOption;
	}

	public void setPaExportationFactureControllerOption(
			PaExportationFactureControllerOption paExportationFactureControllerOption) {
		this.paExportationFactureControllerOption = paExportationFactureControllerOption;
	}

	public TaAvoirDAO getDaoAvoir() {
		return daoAvoir;
	}

	public void setDaoAvoir(TaAvoirDAO daoAvoir) {
		this.daoAvoir = daoAvoir;
	}

	public TaAcompteDAO getDaoAcompte() {
		return daoAcompte;
	}

	public void setDaoAcompte(TaAcompteDAO daoAcompte) {
		this.daoAcompte = daoAcompte;
	}

	public TaReglementDAO getDaoReglement() {
		return daoReglement;
	}

	public void setDaoReglement(TaReglementDAO daoReglement) {
		this.daoReglement = daoReglement;
	}

	public TaRemiseDAO getDaoRemise() {
		return daoRemise;
	}

	public void setDaoRemise(TaRemiseDAO daoRemise) {
		this.daoRemise = daoRemise;
	}

	public void setDaoFacture(TaFactureDAO daoFacture) {
		this.daoFacture = daoFacture;
	}

	
	public void initProposalAdapterFacture(){
		String activationContentProposal = "Ctrl+Space";
		listeDocumentsFactures=initContentProposalFacture();		
	}
	public void initProposalAdapterAvoir(){
		String activationContentProposal = "Ctrl+Space";
		//branchement des contents proposal Avoir
		listeDocumentsAvoirs=initContentProposalAvoir();
	}
	public void initProposalAdapterApporteur(){
		String activationContentProposal = "Ctrl+Space";
		//branchement des contents proposal Avoir
		listeDocumentsApporteurs=initContentProposalApporteur();
	}	
	public void initProposalAdapterAcompte(){
		String activationContentProposal = "Ctrl+Space";
		
		//branchement des contents proposal Acompte
		listeDocumentsAcomptes=initContentProposalAcompte();
	}
	public void initProposalAdapterReglement(){
		String activationContentProposal = "Ctrl+Space";
		
		//branchement des contents proposal Reglement
		listeDocumentsReglements=initContentProposalReglement();
	}
	public void initProposalAdapterRemises(){
		String activationContentProposal = "Ctrl+Space";
		
		//branchement des contents proposal Remises
		listeDocumentsRemises=initContentProposalRemise();
	}
	public void initProposalAdapter(){		
		initProposalAdapterFacture();
		initProposalAdapterAvoir();
		initProposalAdapterApporteur();
		initProposalAdapterAcompte();
		initProposalAdapterReglement();
		initProposalAdapterRemises();
		initBornes();
	}
	
	private void initBornes(){

		if((listeDocumentsFactures!=null&&listeDocumentsFactures.length>0) || 
				(listeDocumentsAvoirs!=null&&listeDocumentsAvoirs.length>0) ||
				(listeDocumentsApporteurs!=null&&listeDocumentsApporteurs.length>0) ||
				(listeDocumentsAcomptes!=null&&listeDocumentsAcomptes.length>0)	 || 
				(listeDocumentsReglements!=null&&listeDocumentsReglements.length>0) ||
				(listeDocumentsRemises!=null&&listeDocumentsRemises.length>0)){
			enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID, true);

		}else
			enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID, false);
	}

	public String[][] initContentProposalFacture(){
		String[][] valeurs = null;

		boolean affichageCtrlEspace = ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.AFFICHAGE_CTRL_ESPACE);

		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();

		List<TaFacture> l =new LinkedList<TaFacture>();
		List<TaFacture> temp =null;
		temp=daoFacture.rechercheDocumentNonExporte(taInfoEntreprise.getDatedebInfoEntreprise(), 
				taInfoEntreprise.getDatefinInfoEntreprise(),false);
		if(!affichageCtrlEspace){
			if(temp.size()>0){
				l.add(temp.get(0));
				if(temp.size()>1)l.add(temp.get(temp.size()-1));
			}
		}else l=temp;
		valeurs = new String[l.size()][2];
		int i = 0;
		String description = "";
		for (TaFacture taFacture : l) {
			valeurs[i][0] = taFacture.getCodeDocument();

			description = "";
			description += taFacture.getLibelleDocument();
			valeurs[i][1] = description;

			i++;
		}
//		}
		return valeurs;		
	}

	public String[][] initContentProposalApporteur(){		
		String[][] valeurs = null;
		boolean affichageCtrlEspace = ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.AFFICHAGE_CTRL_ESPACE);
		
		if(ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.APPORTEURS)){

		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();		
		List<TaApporteur> l =new LinkedList<TaApporteur>();
		List<TaApporteur> temp =null;
		temp=daoApporteur.rechercheDocumentNonExporte(taInfoEntreprise.getDatedebInfoEntreprise(), 
				taInfoEntreprise.getDatefinInfoEntreprise(),false);
		if(!affichageCtrlEspace){
			if(temp.size()>0){
				l.add(temp.get(0));
				if(temp.size()>1)l.add(temp.get(temp.size()-1));
			}
			}else l=temp;
		valeurs = new String[l.size()][2];
		int i = 0;
		String description = "";
		for (TaApporteur taApporteur : l) {
			valeurs[i][0] = taApporteur.getCodeDocument();

			description = "";
			description += taApporteur.getLibelleDocument();
			valeurs[i][1] = description;

			i++;
		}
		}
		return valeurs;		
	}
	
	public String[][] initContentProposalAvoir(){		
		String[][] valeurs = null;
		boolean affichageCtrlEspace = ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.AFFICHAGE_CTRL_ESPACE);

		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();		
		List<TaAvoir> l =new LinkedList<TaAvoir>();
		List<TaAvoir> temp =null;
		temp=daoAvoir.rechercheDocumentNonExporte(taInfoEntreprise.getDatedebInfoEntreprise(), 
				taInfoEntreprise.getDatefinInfoEntreprise(),false);
		if(!affichageCtrlEspace){
			if(temp.size()>0){
				l.add(temp.get(0));
				if(temp.size()>1)l.add(temp.get(temp.size()-1));
			}
			}else l=temp;
		valeurs = new String[l.size()][2];
		int i = 0;
		String description = "";
		for (TaAvoir taAvoir : l) {
			valeurs[i][0] = taAvoir.getCodeDocument();

			description = "";
			description += taAvoir.getLibelleDocument();
			valeurs[i][1] = description;

			i++;
		}
//		}
		return valeurs;		
	}
	
	public String[][] initContentProposalAcompte(){		
		String[][] valeurs = null;
		boolean affichageCtrlEspace = ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.AFFICHAGE_CTRL_ESPACE);
		
		if(ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.ACOMPTES)&&
				paExportationFactureControllerOption.getVue().getCbAcompte().getEnabled()){
		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();		
		List<TaAcompte> l = new LinkedList<TaAcompte>();
		List<TaAcompte> temp =daoAcompte.rechercheAcompteNonRemises(null,taInfoEntreprise.getDatedebInfoEntreprise(), 
				taInfoEntreprise.getDatefinInfoEntreprise(),false,null,null,false);
		if(!affichageCtrlEspace){
			if(temp.size()>0){
				l.add(temp.get(0));
				if(temp.size()>1)l.add(temp.get(temp.size()-1));
			}
			}else l=temp;
		valeurs = new String[l.size()][2];
		int i = 0;
		String description = "";
		for (TaAcompte taDoc : l) {
			valeurs[i][0] = taDoc.getCodeDocument();

			description = "";
			description += taDoc.getLibelleDocument();
			valeurs[i][1] = description;

			i++;
		}
		}
		return valeurs;		
	}
	
	public String[][] initContentProposalReglement(){		
		String[][] valeurs = null;
		boolean affichageCtrlEspace = ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.AFFICHAGE_CTRL_ESPACE);
		if(ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.REGLEMENT_SIMPLE)&&
				paExportationFactureControllerOption.getVue().getCbReglementSimple().getEnabled()){
		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
		
		List<TaReglement> l = new LinkedList<TaReglement>();
		List<TaReglement> temp =null;
		//on ne prend que les réglements non integrés dans une remise
		temp=daoReglement.rechercheReglementNonRemises(null,taInfoEntreprise.getDatedebInfoEntreprise(), 
				taInfoEntreprise.getDatefinInfoEntreprise(),false,null,null,false);
		if(!affichageCtrlEspace){
			if(temp.size()>0){
				l.add(temp.get(0));
				if(temp.size()>1)l.add(temp.get(temp.size()-1));
			}
		}else l=temp;
		valeurs = new String[l.size()][2];
		int i = 0;
		String description = "";
		for (TaReglement taDoc : l) {
			valeurs[i][0] = taDoc.getCodeDocument();

			description = "";
			description += taDoc.getLibelleDocument();
			valeurs[i][1] = description;

			i++;
		}
		}
		return valeurs;		
	}
	public String[][] initContentProposalRemise(){		
		String[][] valeurs = null;
		boolean affichageCtrlEspace = ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.AFFICHAGE_CTRL_ESPACE);
		if(ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.REMISE)&&
				paExportationFactureControllerOption.getVue().getCbRemise().getEnabled()){
		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
		
		List<TaRemise> l = new LinkedList<TaRemise>();
		List<TaRemise> temp =null;
		temp=daoRemise.rechercheDocumentNonExporte(taInfoEntreprise.getDatedebInfoEntreprise(), 
				taInfoEntreprise.getDatefinInfoEntreprise(),false);
		if(!affichageCtrlEspace){
			if(temp.size()>0){
				l.add(temp.get(0));
				if(temp.size()>1)l.add(temp.get(temp.size()-1));
			}
		}else l=temp;
		valeurs = new String[l.size()][2];
		int i = 0;
		String description = "";
		for (TaRemise taDoc : l) {
			valeurs[i][0] = taDoc.getCodeDocument();

			description = "";
			description += taDoc.getLibelleDocument();
			valeurs[i][1] = description;

			i++;
		}
		}
		return valeurs;		
	}

	@Override
	public void DeclencheInitBorneController(DeclencheInitBorneControllerEvent evt) {
		try {
			sourceDeclencheCommandeController = evt.getSource();
			initProposalAdapter();
			initBornes();
		} catch (Exception e) {
			logger.error("",e);
		} finally {
			sourceDeclencheCommandeController = null;
		}
	}
	public void addDeclencheInitBorneControllerListener(IDeclencheInitBorneControllerListener l) {
		listenerList.add(IDeclencheInitBorneControllerListener.class, l);
	}
	
	public void removeDeclencheInitBorneControllerListener(IDeclencheInitBorneControllerListener l) {
		listenerList.remove(IDeclencheInitBorneControllerListener.class, l);
	}
	
	protected void fireDeclencheInitBorneController(DeclencheInitBorneControllerEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == IDeclencheInitBorneControllerListener.class) {
				if (e == null)
					e = new DeclencheInitBorneControllerEvent(this);
				( (IDeclencheInitBorneControllerListener) listeners[i + 1]).DeclencheInitBorneController(e);
			}
		}
	}

	public TaApporteurDAO getDaoApporteur() {
		return daoApporteur;
	}

	public void setDaoApporteur(TaApporteurDAO daoApporteur) {
		this.daoApporteur = daoApporteur;
	}
}

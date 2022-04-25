package fr.legrain.exportation.controllers;


import java.util.ArrayList;
import java.util.Date;
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
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

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
import fr.legrain.exportation.ecrans.PaExportationFactureDates;
import fr.legrain.exportation.ecrans.PaExportationFactureOption;
import fr.legrain.exportation.preferences.PreferenceConstants;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.swt.LibDateTime;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDePageEvent;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.LibDate;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;


public class PaExportationFactureControllerDates extends JPABaseControllerSWTStandard implements
RetourEcranListener,IDeclencheInitBorneControllerListener {
	
	static Logger logger = Logger.getLogger(PaExportationFactureControllerDates.class.getName());		
	private PaExportationFactureDates vue = null; 	
	private TaFactureDAO masterDaoFacture = null;
	private TaAvoirDAO masterDaoAvoir = null;
	private TaAcompteDAO masterDaoAcompte = null;
	private TaReglementDAO masterDaoReglement = null;
	private TaRemiseDAO masterDaoRemise = null;	
	private TaApporteurDAO masterDaoApporteur = null;
	
	
	private TaInfoEntreprise taInfoEntreprise = null;
	
	String [][] listeDocumentsFactures=null;
	String [][] listeDocumentsAvoirs=null;
	String [][] listeDocumentsApporteurs=null;
	String [][] listeDocumentsAcomptes=null;
	String [][] listeDocumentsReglements=null;
	String [][] listeDocumentsRemises=null;
	
	
	private PaExportationFactureOption paExportationFactureOption=null;
	private PaExportationFactureControllerOption paExportationFactureControllerOption = null;
	
	private Object ecranAppelant = null;
	
	private boolean gerePointage = true;
	private boolean centralisation = false;
	
	
	protected HandlerPrecedent handlerPrecedent = new HandlerPrecedent();
	public static final String C_COMMAND_DOCUMENT_LISTE_DOCUMENT_ID = "fr.legrain.Document.listeDocument";
	protected HandlerListeDocument handlerListeDocument = new HandlerListeDocument();
	
	public static final String C_COMMAND_DOCUMENT_IMPRIMER_REGLEMENT_ID = "fr.legrain.Document.imprimerReglement";
	protected HandlerImprimerReglement handlerImprimerReglement = new HandlerImprimerReglement();
	
	
	public PaExportationFactureControllerDates(PaExportationFactureDates vue) {
		this(vue,null);
	}
	
	public PaExportationFactureControllerDates(PaExportationFactureDates vue,EntityManager em) {
		initCursor(SWT.CURSOR_WAIT);
		if(em!=null) {
			setEm(em);
		}
		masterDaoFacture = new TaFactureDAO(getEm());
		masterDaoAvoir = new TaAvoirDAO(getEm());
		masterDaoApporteur = new TaApporteurDAO(getEm());
		masterDaoAcompte = new TaAcompteDAO(getEm());
		masterDaoReglement = new TaReglementDAO(getEm());
		masterDaoRemise=new TaRemiseDAO(getEm());
		try {
			setVue(vue);
			this.vue=vue;
			vue.getShell().addShellListener(this);
			
//			 Branchement action annuler : empeche la fermeture automatique de la
			// fenetre sur ESC
			vue.getShell().addTraverseListener(new Traverse());
			actionImprimer.setText("Exporter [F11]");
			vue.getTfDATEDEBVentes().addSelectionListener(new LgrModifyListener());
			vue.getTfDATEFINVentes().addSelectionListener(new LgrModifyListener());
			vue.getTfDATEDEBReglement().addSelectionListener(new LgrModifyListener());
			vue.getTfDATEFINReglement().addSelectionListener(new LgrModifyListener());
			vue.getTfDATEFINVentes().setEnabled(true);
			vue.getTfDATEFINReglement().setEnabled(true);
			
			initController();
		} catch (Exception e) {
			logger.error("Erreur ", e);
		}
		initCursor(SWT.CURSOR_ARROW);
	}
	
	private class LgrModifyListener implements SelectionListener{


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
			if(e.getSource().equals(vue.getTfDATEDEBVentes())) {
				if(!LibDateTime.isDateNull(vue.getTfDATEDEBVentes())) {
					vue.getTfDATEFINVentes().setEnabled(true);
				} else {
					LibDateTime.setDateNull(vue.getTfDATEDEBVentes());
					LibDateTime.setDateNull(vue.getTfDATEFINVentes()); 
					vue.getTfDATEFINVentes().setEnabled(false);
				}
			}
			if(e.getSource().equals(vue.getTfDATEDEBReglement())) {
				if(!LibDateTime.isDateNull(vue.getTfDATEDEBReglement())) {
					vue.getTfDATEFINReglement().setEnabled(true);
				} else {
					LibDateTime.setDateNull(vue.getTfDATEDEBReglement());
					LibDateTime.setDateNull(vue.getTfDATEFINReglement()); 
					vue.getTfDATEFINReglement().setEnabled(false);
				}
			}
		} catch (Exception e1) {
			logger.error(e1);
		}		
	}
	private void initController() {
		try {
			setDaoStandard(masterDaoFacture);
			vue.getCbAcceptFacture().setSelection(true);
			vue.getCbAcceptAvoir().setSelection(true);
			vue.getCbAcceptApporteur().setSelection(ExportationPlugin.getDefault().
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

			TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
			taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
			
			vue.getPaEcran().layout();
			vue.getScrolledComposite().setMinSize(vue.getPaEcran().computeSize(SWT.DEFAULT, SWT.DEFAULT));
		} catch (Exception e) {
			logger.error("Erreur : PaArticlesController", e);
		}
	}

	@Override
	protected void initImageBouton() {
		final String C_IMAGE_EXPORT = "/icons/export_wiz.gif";
		vue.getBtnExporterVentes().setImage(ExportationPlugin.getImageDescriptor(C_IMAGE_EXPORT).createImage());
		vue.getBtnExporterReglement().setImage(ExportationPlugin.getImageDescriptor(C_IMAGE_EXPORT).createImage());
		vue.getPaBtn1().getBtnImprimer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_FERMER));
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
			param.setFocus(masterDaoFacture.getModeObjet().getFocusCourant());
		}
		try {
			actRefresh();
		} catch (Exception e) {
			logger.error("", e);
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
		boolean trouve =true;
		boolean trouveReglement =true;

		
		enableActionAndHandler(C_COMMAND_DOCUMENT_IMPRIMER_REGLEMENT_ID, trouveReglement);
		enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID, trouve);
		enableActionAndHandler(C_COMMAND_GLOBAL_PRECEDENT_ID, true);


		initFocusSWT(masterDaoFacture,mapInitFocus);
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
		listeComposantFocusable.add(vue.getTfDATEDEBVentes());
		listeComposantFocusable.add(vue.getTfDATEFINVentes());
		listeComposantFocusable.add(vue.getTfDATEDEBReglement());
		listeComposantFocusable.add(vue.getTfDATEFINReglement());
			
		listeComposantFocusable.add(vue.getBtnListeDoc());
		listeComposantFocusable.add(vue.getBtnExporterVentes());
		listeComposantFocusable.add(vue.getBtnExporterReglement());
		listeComposantFocusable.add(vue.getPaBtn1().getBtnImprimer());
		listeComposantFocusable.add(vue.getPaBtn1().getBtnFermer());
		
		if(mapInitFocus == null) 
			mapInitFocus = new LinkedHashMap();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION,vue.getTfDATEDEBVentes());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION,vue.getTfDATEDEBVentes());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION,vue.getTfDATEDEBVentes());
		
		

		vue.getCbReExport().addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				vue.getCbRexportFacture().setSelection(vue.getCbReExport().getSelection());
				vue.getCbRexportAvoirs().setSelection(vue.getCbReExport().getSelection());
				vue.getCbRexportApporteurs().setSelection(vue.getCbReExport().getSelection());
				vue.getCbReExportAcompte().setSelection(vue.getCbReExport().getSelection());
				vue.getCbRexportReglement().setSelection(vue.getCbReExport().getSelection());
				vue.getCbRexportRemise().setSelection(vue.getCbReExport().getSelection());
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				widgetSelected(e);
			}
		});
		vue.getCbAcceptFacture().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					initProposalAdapterFacture();
					initBornes(TaFacture.TYPE_DOC,vue.getCbAcceptFacture().getSelection());
				} catch (Exception e1) {
					logger.error("", e1);
				}
			}
		});
		vue.getCbAcceptAvoir().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					initProposalAdapterAvoir();
					initBornes(TaAvoir.TYPE_DOC,vue.getCbAcceptAvoir().getSelection());
				} catch (Exception e1) {
					logger.error("", e1);
				}
			}
		});
		vue.getCbAcceptApporteur().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					ExportationPlugin.getDefault().getPreferenceStoreProject().
					setValue(PreferenceConstants.APPORTEURS, vue.getCbAcceptApporteur().getSelection());					
					initProposalAdapterApporteur();
					initBornes(TaApporteur.TYPE_DOC,vue.getCbAcceptApporteur().getSelection());
				} catch (Exception e1) {
					logger.error("", e1);
				}
			}
		});		
		vue.getCbAccepAcompte().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					ExportationPlugin.getDefault().getPreferenceStoreProject().setValue(
							PreferenceConstants.ACOMPTES, vue.getCbAccepAcompte().getSelection());
					initProposalAdapterAcompte();
					initBornes(TaAcompte.TYPE_DOC,vue.getCbAccepAcompte().getSelection());
					ExportationPlugin.getDefault().getPreferenceStoreProject().setValue(
							PreferenceConstants.ACOMPTES, vue.getCbAccepAcompte().getSelection());
					paExportationFactureControllerOption.initEcranSurPreferences(PreferenceConstants.ACOMPTES);
				} catch (Exception e1) {
					logger.error("", e1);
				}
			}
		});
		vue.getCbAcceptReglement().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					ExportationPlugin.getDefault().getPreferenceStoreProject().setValue(
							PreferenceConstants.REGLEMENT_SIMPLE, vue.getCbAcceptReglement().getSelection());
					initProposalAdapterReglement();
					initBornes(TaReglement.TYPE_DOC,vue.getCbAcceptReglement().getSelection());
					ExportationPlugin.getDefault().getPreferenceStoreProject().setValue(
							PreferenceConstants.REGLEMENT_SIMPLE, vue.getCbAcceptReglement().getSelection());
					paExportationFactureControllerOption.initEcranSurPreferences(PreferenceConstants.REGLEMENT_SIMPLE);
				} catch (Exception e1) {
					logger.error("", e1);
				}

			}
		});
		vue.getCbAcceptRemise().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {				
					ExportationPlugin.getDefault().getPreferenceStoreProject().setValue(
						PreferenceConstants.REMISE, vue.getCbAcceptRemise().getSelection());					
					initProposalAdapterRemises();
					initBornes(TaRemise.TYPE_DOC,vue.getCbAcceptRemise().getSelection());
					ExportationPlugin.getDefault().getPreferenceStoreProject().setValue(
							PreferenceConstants.REMISE, vue.getCbAcceptRemise().getSelection());
					paExportationFactureControllerOption.initEcranSurPreferences(PreferenceConstants.REMISE);
					
				} catch (Exception e1) {
					logger.error("", e1);
				}
			}
		});
		
		vue.getCbDocumentLie().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ExportationPlugin.getDefault().getPreferenceStoreProject().
				setValue(PreferenceConstants.DOCUMENTS_LIES, vue.getCbDocumentLie().getSelection());
			}
		});
		
		vue.getCbReglementLie().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					ExportationPlugin.getDefault().getPreferenceStoreProject().
					setValue(PreferenceConstants.REGLEMENTS_LIES, vue.getCbReglementLie().getSelection());
					vue.getCbPointages().setSelection(vue.getCbReglementLie().getSelection());
					vue.getCbPointages2().setSelection(vue.getCbReglementLie().getSelection());
					ExportationPlugin.getDefault().getPreferenceStoreProject().
					setValue(PreferenceConstants.POINTAGES, vue.getCbPointages().getSelection());
					paExportationFactureControllerOption.griserTout(vue.getCbReglementLie().getSelection());
					findExpandIem(vue.getExpandBarOption(), paExportationFactureOption).setExpanded(vue.getCbReglementLie().getSelection());
					DeclencheInitBorneController(new DeclencheInitBorneControllerEvent(this));
					paExportationFactureControllerOption.actRefresh();
				} catch (Exception e1) {
					logger.error("", e1);
				}
			}
		});
		
		vue.getCbPointages().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
			try {
				ExportationPlugin.getDefault().getPreferenceStoreProject().
				setValue(PreferenceConstants.POINTAGES, vue.getCbPointages().getSelection());
				paExportationFactureControllerOption.initEcranSurPreferences(PreferenceConstants.POINTAGES);
			} catch (Exception e1) {
				logger.error("", e1);
			}
			}
		});
		
		vue.getCbPointages2().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {	
				ExportationPlugin.getDefault().getPreferenceStoreProject().
				setValue(PreferenceConstants.POINTAGES, vue.getCbPointages2().getSelection());
				paExportationFactureControllerOption.initEcranSurPreferences(PreferenceConstants.POINTAGES);
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
		
		
		vue.getCbCentralisation().setSelection(ExportationPlugin.getDefault().
				getPreferenceStoreProject().getBoolean(PreferenceConstants.CENTRALISATION));
		vue.getCbDocumentLie().setSelection(ExportationPlugin.getDefault().
				getPreferenceStoreProject().getBoolean(PreferenceConstants.DOCUMENTS_LIES));
		vue.getCbReglementLie().setSelection(ExportationPlugin.getDefault().
				getPreferenceStoreProject().getBoolean(PreferenceConstants.REGLEMENTS_LIES));
		vue.getCbPointages().setSelection(ExportationPlugin.getDefault().
				getPreferenceStoreProject().getBoolean(PreferenceConstants.POINTAGES));	
		vue.getCbPointages2().setSelection(ExportationPlugin.getDefault().
				getPreferenceStoreProject().getBoolean(PreferenceConstants.POINTAGES));
		vue.getCbPointages().setSelection(vue.getCbReglementLie().getSelection());
		vue.getCbPointages2().setSelection(vue.getCbReglementLie().getSelection());
		ExportationPlugin.getDefault().getPreferenceStoreProject().
		setValue(PreferenceConstants.POINTAGES, vue.getCbPointages().getSelection());
		findExpandIem(vue.getExpandBarOption(), paExportationFactureOption).setExpanded(vue.getCbReglementLie().getSelection());
	}



	
	protected void initActions() {
		mapCommand = new HashMap<String, IHandler>();

		mapCommand.put(C_COMMAND_GLOBAL_ANNULER_ID, handlerAnnuler);
		mapCommand.put(C_COMMAND_GLOBAL_AIDE_ID, handlerAide);
		mapCommand.put(C_COMMAND_GLOBAL_FERMER_ID, handlerFermer);
		mapCommand.put(C_COMMAND_GLOBAL_IMPRIMER_ID, handlerImprimer);
		mapCommand.put(C_COMMAND_GLOBAL_PRECEDENT_ID, handlerPrecedent);
		mapCommand.put(C_COMMAND_DOCUMENT_LISTE_DOCUMENT_ID, handlerListeDocument);
		mapCommand.put(C_COMMAND_DOCUMENT_IMPRIMER_REGLEMENT_ID, handlerImprimerReglement);
		
		initFocusCommand(String.valueOf(this.hashCode()),
				listeComposantFocusable, mapCommand);
//		initCommands();
		
		if (mapActions == null)
			mapActions = new LinkedHashMap();
		
		mapActions.put(vue.getBtnExporterVentes(), C_COMMAND_GLOBAL_IMPRIMER_ID);
		mapActions.put(vue.getBtnExporterReglement(), C_COMMAND_DOCUMENT_IMPRIMER_REGLEMENT_ID);
		mapActions.put(vue.getPaBtn1().getBtnFermer(), C_COMMAND_GLOBAL_PRECEDENT_ID);
		mapActions.put(vue.getPaBtn1().getBtnImprimer(), C_COMMAND_GLOBAL_FERMER_ID);
		mapActions.put(vue.getBtnListeDoc(), C_COMMAND_DOCUMENT_LISTE_DOCUMENT_ID);
		
		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID };
		mapActions.put(null, tabActionsAutres);
	}
	
	public PaExportationFactureControllerDates getThis(){
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
	
	protected void actImprimerReglement() throws Exception {
		//Récupération des paramètres dans l'ihm
		List<TaAcompte> idAcompteAExporter = null;
		List<TaReglement> idReglementAExporter = null;
		List<TaRemise> idRemiseAExporter = null;
		Date dateDebReglement = null;
		Date dateFinReglement = null;

		if( !LibDateTime.isDateNull(vue.getTfDATEDEBReglement()) ) {
				dateDebReglement = LibDateTime.getDate(vue.getTfDATEDEBReglement());
		}

		if(!LibDateTime.isDateNull(vue.getTfDATEFINReglement()) ) {
				dateFinReglement = LibDateTime.getDate(vue.getTfDATEFINReglement());
		}
				
		
		if(dateDebReglement!=null) {//1 date => toutes les factures à cette date
			logger.debug("Exportation - selection par date");
			if(dateFinReglement!=null) {//2 dates => factures entre les 2 dates si intervalle correct

				if(LibDate.compareTo(dateDebReglement,dateFinReglement)>0) {
					MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
							"ATTENTION", "La date de début des réglements doit être antérieure à la date fin");
					throw new Exception("probleme intervalle des dates des réglements");
				}				
			} else {
				dateFinReglement = dateDebReglement;
			}			
		}
			
		if(vue.getCbAccepAcompte().getSelection())idAcompteAExporter = masterDaoAcompte.rechercheAcompteNonRemises(null,dateDebReglement,dateFinReglement,vue.getCbRexportReglement().getSelection(),null,null,true);
		if(vue.getCbAcceptReglement().getSelection())idReglementAExporter = masterDaoReglement.rechercheReglementNonRemises(null,dateDebReglement,dateFinReglement,vue.getCbRexportReglement().getSelection(),null,null,true);
		if(vue.getCbAcceptRemise().getSelection())idRemiseAExporter = masterDaoRemise.rechercheDocumentOrderByDate(dateDebReglement,dateFinReglement);
	 
		///////////////////////////////////////

		final List<TaAcompte> finalIdAcompteAExporter = idAcompteAExporter;
		final List<TaReglement> finalIdReglementAExporter = idReglementAExporter;
		final List<TaRemise> finalIdRemiseAExporter = idRemiseAExporter;
		
		final boolean finalReExportFacture = vue.getCbRexportFacture().getSelection();
		final boolean finalReExportAvoir = vue.getCbRexportAvoirs().getSelection();
		final boolean finalReExportApporteur = vue.getCbRexportApporteurs().getSelection();
		final boolean finalReExportAcompte = vue.getCbReExportAcompte().getSelection();
		final boolean finalReExportReglement = vue.getCbRexportReglement().getSelection();
		final boolean finalReExportRemise = vue.getCbRexportRemise().getSelection();
		

		gerePointage=ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.POINTAGES);
		centralisation=ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.CENTRALISATION);
		ExportationEpicea retour=exporter(null,null,null,finalIdAcompteAExporter,finalIdReglementAExporter,finalIdRemiseAExporter,
				finalReExportFacture,finalReExportAvoir,finalReExportApporteur,finalReExportAcompte,finalReExportReglement,finalReExportRemise,
				gerePointage,centralisation);
					if(!retour.getRetour()&& !retour.getMessageRetour().equals(""))
						MessageDialog.openError(vue.getShell(), "Exportation abandonnée", retour.getMessageRetour());
					else
						if(retour.getMessageRetour().equals(""))
							retour.setMessageRetour( "L'exportation est terminée.\nElle s'est déroulée correctement." +
									"\n\rLe fichier se trouve à l'adresse suivante : \n\r"+retour.getLocationFichier());
						MessageDialog.openInformation(vue.getShell(),"Exportation Terminée",retour.getMessageRetour() );
		
		actFermer();

	}
	
	@Override
	protected void actImprimer() throws Exception {
		//Récupération des paramètres dans l'ihm

		List<TaFacture> idFactureAExporter = null;
		List<TaAvoir> idAvoirAExporter = null;
		List<TaApporteur> idApporteurAExporter = null;
		Date dateDebVentes = null;
		Date dateFinVentes = null;

		if( !LibDateTime.isDateNull(vue.getTfDATEDEBVentes()) ) {
				dateDebVentes = LibDateTime.getDate(vue.getTfDATEDEBVentes());
		}
		if( !LibDateTime.isDateNull(vue.getTfDATEFINVentes()) ) {
				dateFinVentes = LibDateTime.getDate(vue.getTfDATEFINVentes());
		}

		
		if(dateDebVentes!=null) {//1 date => toutes les factures à cette date
			logger.debug("Exportation - selection par date");
			if(dateFinVentes!=null) {//2 dates => factures entre les 2 dates si intervalle correct

				if(LibDate.compareTo(dateDebVentes,dateFinVentes)>0) {
					MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
							"ATTENTION", "La date de début des ventes doit être antérieure à la date fin");
					throw new Exception("probleme intervalle des dates de ventes");
				}				

			} else {
				dateFinVentes = dateDebVentes;
			}			
		} 		
		
		if(vue.getCbAcceptFacture().getSelection())idFactureAExporter = masterDaoFacture.rechercheDocumentOrderByDate(dateDebVentes,dateFinVentes);
		if(vue.getCbAcceptAvoir().getSelection())idAvoirAExporter = masterDaoAvoir.rechercheDocumentOrderByDate(dateDebVentes,dateFinVentes);		
		if(vue.getCbAcceptApporteur().getSelection())idApporteurAExporter = masterDaoApporteur.rechercheDocumentOrderByDate(dateDebVentes,dateFinVentes);	

	 
		///////////////////////////////////////

		final List<TaFacture> finalIdFactureAExporter = idFactureAExporter;
		final List<TaAvoir> finalIdAvoirAExporter = idAvoirAExporter;
		final List<TaApporteur> finalIdApporteurAExporter = idApporteurAExporter;

		
		final boolean finalReExportFacture = vue.getCbRexportFacture().getSelection();
		final boolean finalReExportAvoir = vue.getCbRexportAvoirs().getSelection();
		final boolean finalReExportApporteur = vue.getCbRexportApporteurs().getSelection();
		final boolean finalReExportAcompte = vue.getCbReExportAcompte().getSelection();
		final boolean finalReExportReglement = vue.getCbRexportReglement().getSelection();
		final boolean finalReExportRemise = vue.getCbRexportRemise().getSelection();

		gerePointage=ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.POINTAGES);
		centralisation=ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.CENTRALISATION);

		ExportationEpicea retour=exporter(finalIdFactureAExporter,finalIdAvoirAExporter,finalIdApporteurAExporter,null,null,null,
				finalReExportFacture,finalReExportAvoir,finalReExportApporteur,finalReExportAcompte,finalReExportReglement,finalReExportRemise,
				gerePointage,centralisation);
					if(!retour.getRetour()&& !retour.getMessageRetour().equals(""))
						MessageDialog.openError(vue.getShell(), "Exportation abandonnée", retour.getMessageRetour());
					else
						if(retour.getMessageRetour().equals(""))
							retour.setMessageRetour( "L'exportation est terminée.\nElle s'est déroulée correctement." +
									"\n\rLe fichier se trouve à l'adresse suivante : \n\r"+retour.getLocationFichier());
						MessageDialog.openInformation(vue.getShell(),"Exportation Terminée",retour.getMessageRetour() );

		actFermer();

	}
	

	protected ExportationEpicea exporter(List<TaFacture> idFactureAExporter,List<TaAvoir> idAvoirAExporter,List<TaApporteur> idApporteurAExporter,List<TaAcompte> idAcompteAExporter,
			List<TaReglement> idReglementAExporter,List<TaRemise> idRemiseAExporter,boolean reExportFacture,boolean reExportAvoir,boolean reExportApporteur,boolean reExportAcompte,
			boolean reExportReglement,boolean reExportRemise,final boolean gererPointages ,final boolean centralisation) throws Exception{

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
	
	public TaFactureDAO getMasterDaoFacture() {
		return masterDaoFacture;
	}
	
	@Override
	public void initEtatComposant() {}
	

	@Override
	protected void actRefresh() throws Exception {
		// TODO Raccord de méthode auto-généré
		paExportationFactureControllerOption.actRefresh();
		vue.getCbReglementLie().setSelection(ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.REGLEMENTS_LIES));
		findExpandIem(vue.getExpandBarOption(), paExportationFactureOption).setExpanded(vue.getCbReglementLie().getSelection());
		initProposalAdapter();
		initBornes();
		initEtatBouton();
	}

	@Override
	protected void initMapComposantDecoratedField() {
		// TODO Raccord de méthode auto-généré
		
	}

	@Override
	protected void sortieChamps() {
		// TODO Raccord de méthode auto-généré
		
	}
	private class HandlerPrecedent extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				ChangementDePageEvent change = new ChangementDePageEvent(this,ChangementDePageEvent.AUTRE,0);
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

	public PaExportationFactureControllerOption getPaExportationFactureControllerOption() {
		return paExportationFactureControllerOption;
	}

	public void setPaExportationFactureControllerOption(
			PaExportationFactureControllerOption paExportationFactureControllerOption) {
		this.paExportationFactureControllerOption = paExportationFactureControllerOption;
	}

	public TaAvoirDAO getMasterDaoAvoir() {
		return masterDaoAvoir;
	}

	public void setMasterDaoAvoir(TaAvoirDAO masterDaoAvoir) {
		this.masterDaoAvoir = masterDaoAvoir;
	}

	public TaAcompteDAO getMasterDaoAcompte() {
		return masterDaoAcompte;
	}

	public void setMasterDaoAcompte(TaAcompteDAO masterDaoAcompte) {
		this.masterDaoAcompte = masterDaoAcompte;
	}

	public TaReglementDAO getMasterDaoReglement() {
		return masterDaoReglement;
	}

	public void setMasterDaoReglement(TaReglementDAO masterDaoReglement) {
		this.masterDaoReglement = masterDaoReglement;
	}

	public TaRemiseDAO getMasterDaoRemise() {
		return masterDaoRemise;
	}

	public void setMasterDaoRemise(TaRemiseDAO masterDaoRemise) {
		this.masterDaoRemise = masterDaoRemise;
	}

	public void setMasterDaoFacture(TaFactureDAO masterDaoFacture) {
		this.masterDaoFacture = masterDaoFacture;
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
	
	public String[][] initContentProposalFacture(){
		String[][] valeurs = null;
		boolean affichageCtrlEspace = ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.AFFICHAGE_CTRL_ESPACE);
		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();

		List<TaFacture> l =new LinkedList<TaFacture>();
		List<TaFacture> temp =null;
		if(!vue.getCbRexportFacture().getSelection())temp = masterDaoFacture.rechercheDocumentNonExporte(taInfoEntreprise.getDatedebInfoEntreprise(), 
				taInfoEntreprise.getDatefinInfoEntreprise(),true);
		else temp = masterDaoFacture.rechercheDocumentOrderByDate(taInfoEntreprise.getDatedebInfoEntreprise(), 
				taInfoEntreprise.getDatefinInfoEntreprise());
		if(!affichageCtrlEspace){
			if(temp.size()>0){
				l.add(temp.get(0));
				if(temp.size()>1)l.add(temp.get(temp.size()-1));
			}
		}		else l=temp;
		valeurs = new String[l.size()][2];
		int i = 0;
		String description = "";
		for (TaFacture taFacture : l) {
			valeurs[i][0] = taFacture.getCodeDocument();

			description = "";
			description += LibDate.dateToString(taFacture.getDateDocument());
			valeurs[i][1] = description;

			i++;
		}
//		}
		return valeurs;		
	}
	
	public String[][] initContentProposalAvoir(){		
		String[][] valeurs = null;
		boolean affichageCtrlEspace = ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.AFFICHAGE_CTRL_ESPACE);
		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();		
		List<TaAvoir> l =new LinkedList<TaAvoir>();;
		List<TaAvoir> temp =null;
		if(!vue.getCbRexportAvoirs().getSelection())temp =masterDaoAvoir.rechercheDocumentNonExporte(taInfoEntreprise.getDatedebInfoEntreprise(), 
				taInfoEntreprise.getDatefinInfoEntreprise(),true);
		else temp = masterDaoAvoir.rechercheDocumentOrderByDate(taInfoEntreprise.getDatedebInfoEntreprise(), 
				taInfoEntreprise.getDatefinInfoEntreprise());
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
			description += LibDate.dateToString(taAvoir.getDateDocument());
			valeurs[i][1] = description;

			i++;
		}
		return valeurs;		
	}
	
	public String[][] initContentProposalApporteur(){		
		String[][] valeurs = null;
		boolean affichageCtrlEspace = ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.AFFICHAGE_CTRL_ESPACE);
		if(ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.APPORTEURS)){
		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();		
		List<TaApporteur> l =new LinkedList<TaApporteur>();;
		List<TaApporteur> temp =null;
		if(!vue.getCbRexportApporteurs().getSelection())temp =masterDaoApporteur.rechercheDocumentNonExporte(taInfoEntreprise.getDatedebInfoEntreprise(), 
				taInfoEntreprise.getDatefinInfoEntreprise(),true);
		else temp = masterDaoApporteur.rechercheDocumentOrderByDate(taInfoEntreprise.getDatedebInfoEntreprise(), 
				taInfoEntreprise.getDatefinInfoEntreprise());
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
			description += LibDate.dateToString(taApporteur.getDateDocument());
			valeurs[i][1] = description;

			i++;
		}
		}
		return valeurs;		
	}	
	public String[][] initContentProposalAcompte(){		
		String[][] valeurs = null;
		boolean affichageCtrlEspace = ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.AFFICHAGE_CTRL_ESPACE);
		if(ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.ACOMPTES)){
		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
		
		List<TaAcompte> l = new LinkedList<TaAcompte>();
		//on ne prend que les réglements non integrés dans une remise
		List<TaAcompte> temp =masterDaoAcompte.rechercheAcompteNonRemises(null,taInfoEntreprise.getDatedebInfoEntreprise(), 
				taInfoEntreprise.getDatefinInfoEntreprise(),vue.getCbReExportAcompte().getSelection(),null,null,true);
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
			description += LibDate.dateToString(taDoc.getDateDocument());
			valeurs[i][1] = description;

			i++;
		}
		}
		return valeurs;		
	}
	public String[][] initContentProposalReglement(){		
		String[][] valeurs = null;
		boolean affichageCtrlEspace = ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.AFFICHAGE_CTRL_ESPACE);
		if(ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.REGLEMENT_SIMPLE)){
		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
		
		List<TaReglement> l = new LinkedList<TaReglement>();
		List<TaReglement> temp =null;
		//on ne prend que les réglements non integrés dans une remise
		temp=masterDaoReglement.rechercheReglementNonRemises(null,taInfoEntreprise.getDatedebInfoEntreprise(), 
				taInfoEntreprise.getDatefinInfoEntreprise(),vue.getCbRexportReglement().getSelection(),null,null,true);
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
			description += LibDate.dateToString(taDoc.getDateDocument());
			valeurs[i][1] = description;

			i++;
		}
		}
		return valeurs;		
	}
	public String[][] initContentProposalRemise(){		
		String[][] valeurs = null;
		boolean affichageCtrlEspace = ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.AFFICHAGE_CTRL_ESPACE);
		if(ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.REMISE)){
		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
		
		List<TaRemise> l = new LinkedList<TaRemise>();
		List<TaRemise> temp =null;
		if(!vue.getCbRexportRemise().getSelection())temp=masterDaoRemise.rechercheDocumentNonExporte(taInfoEntreprise.getDatedebInfoEntreprise(), 
				taInfoEntreprise.getDatefinInfoEntreprise(),true);
		else
			temp=masterDaoRemise.rechercheDocumentOrderByDate(taInfoEntreprise.getDatedebInfoEntreprise(), 
				taInfoEntreprise.getDatefinInfoEntreprise());
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
			description += LibDate.dateToString(taDoc.getDateDocument());
			valeurs[i][1] = description;

			i++;
		}
		}
		return valeurs;		
	}


	private void initBornes(String type,boolean selection){
		if(type.equals(TaFacture.TYPE_DOC)){
			vue.getCbAcceptFacture().setSelection(selection && listeDocumentsFactures!=null && listeDocumentsFactures.length>0);
		}
		if(type.equals(TaAvoir.TYPE_DOC)){
			vue.getCbAcceptAvoir().setSelection(selection && listeDocumentsAvoirs!=null && listeDocumentsAvoirs.length>0);
		}
		if(type.equals(TaApporteur.TYPE_DOC)){
			vue.getCbAcceptApporteur().setSelection(selection && listeDocumentsApporteurs!=null && listeDocumentsApporteurs.length>0);
		}		
		if(type.equals(TaFacture.TYPE_DOC)||type.equals(TaAvoir.TYPE_DOC)||type.equals(TaApporteur.TYPE_DOC)){
			LibDateTime.setDateNull(vue.getTfDATEDEBVentes());
			LibDateTime.setDateNull(vue.getTfDATEFINVentes());
		}
		if(type.equals(TaAcompte.TYPE_DOC)||type.equals(TaReglement.TYPE_DOC)||type.equals(TaRemise.TYPE_DOC)){
			LibDateTime.setDateNull(vue.getTfDATEDEBReglement());
			LibDateTime.setDateNull(vue.getTfDATEFINReglement());
		}
		
		if(type.equals(TaAcompte.TYPE_DOC)){
			vue.getCbAccepAcompte().setSelection(selection && listeDocumentsAcomptes!=null && listeDocumentsAcomptes.length>0 );
		}
		if(type.equals(TaReglement.TYPE_DOC)){
			vue.getCbAcceptReglement().setSelection(selection && listeDocumentsReglements!=null && listeDocumentsReglements.length>0 );
		}
		if(type.equals(TaRemise.TYPE_DOC)){
			vue.getCbAcceptRemise().setSelection(selection && listeDocumentsRemises!=null && listeDocumentsRemises.length>0);
		}
		Date dateInit=LibDate.incrementDate( taInfoEntreprise.getDatefinInfoEntreprise(), 0, 0, 0);
		Date dateFacture=dateInit;
		Date dateAvoir = dateInit;
		Date dateApporteur = dateInit;
		Date dateAcompte=dateInit;
		Date dateReglement=dateInit;
		Date dateRemise=dateInit;
		boolean VentesVisible=false;
		boolean reglementVisible=false;
		
		if(type.equals(TaFacture.TYPE_DOC)||type.equals(TaAvoir.TYPE_DOC)||type.equals(TaApporteur.TYPE_DOC)){
			if(listeDocumentsFactures!=null && listeDocumentsFactures.length>0){
				dateFacture=LibDate.stringToDate2(listeDocumentsFactures[0][1]);
				VentesVisible=true;
			}
			if(listeDocumentsAvoirs!=null && listeDocumentsAvoirs.length>0){
				dateAvoir=LibDate.stringToDate2(listeDocumentsAvoirs[0][1]);
				VentesVisible=true;
			}
			if(listeDocumentsApporteurs!=null && listeDocumentsApporteurs.length>0){
				dateApporteur=LibDate.stringToDate2(listeDocumentsApporteurs[0][1]);
				VentesVisible=true;
			}			
			if(VentesVisible){
				if(vue.getCbAcceptFacture().getSelection()) LibDateTime.setDate(vue.getTfDATEDEBVentes(),dateFacture);
				if(vue.getCbAcceptAvoir().getSelection()) LibDateTime.setDate(vue.getTfDATEDEBVentes(),dateAvoir);
				if(vue.getCbAcceptApporteur().getSelection()) LibDateTime.setDate(vue.getTfDATEDEBVentes(),dateApporteur);
				if(vue.getCbAcceptFacture().getSelection()&& vue.getCbAcceptAvoir().getSelection()&& vue.getCbAcceptApporteur().getSelection()){
					if(dateFacture.before(dateAvoir) && dateFacture.before(dateApporteur)) LibDateTime.setDate(vue.getTfDATEDEBVentes(),dateFacture);
					else if(dateFacture.before(dateAvoir) && dateFacture.after(dateApporteur)) LibDateTime.setDate(vue.getTfDATEDEBVentes(),dateApporteur);
					else if(dateFacture.after(dateAvoir) && dateAvoir.before(dateApporteur)) LibDateTime.setDate(vue.getTfDATEDEBVentes(),dateAvoir);
				}
					
				if(!LibDateTime.isDateNull(vue.getTfDATEDEBVentes())) LibDateTime.setDate(vue.getTfDATEFINVentes(),taInfoEntreprise.getDatefinInfoEntreprise());
			}
		}
		
		if(type.equals(TaAcompte.TYPE_DOC)||type.equals(TaReglement.TYPE_DOC)||type.equals(TaRemise.TYPE_DOC)){
		if(listeDocumentsAcomptes!=null && listeDocumentsAcomptes.length>0){
			dateAcompte=LibDate.stringToDate2(listeDocumentsAcomptes[0][1]);
			reglementVisible=true;
		}
		if(listeDocumentsReglements!=null && listeDocumentsReglements.length>0){
			dateReglement=LibDate.stringToDate2(listeDocumentsReglements[0][1]);
			reglementVisible=true;
		}
		if(listeDocumentsRemises!=null && listeDocumentsRemises.length>0){
			dateRemise=LibDate.stringToDate2(listeDocumentsRemises[0][1]);
			reglementVisible=true;
		}
		if(reglementVisible){
			if(vue.getCbAccepAcompte().getSelection()) LibDateTime.setDate(vue.getTfDATEDEBReglement(),dateAcompte);
			if(vue.getCbAcceptReglement().getSelection()) LibDateTime.setDate(vue.getTfDATEDEBReglement(),dateReglement);
			if(vue.getCbAcceptRemise().getSelection()) LibDateTime.setDate(vue.getTfDATEDEBReglement(),dateRemise);
		if(dateAcompte.after(dateReglement)||dateAcompte.equals(dateReglement)){
			if(dateReglement.after(dateRemise)) LibDateTime.setDate(vue.getTfDATEDEBReglement(),dateRemise);
			else LibDateTime.setDate(vue.getTfDATEDEBReglement(),dateReglement);
		}
		else LibDateTime.setDate(vue.getTfDATEDEBReglement(),dateAcompte);
		
		
		if(!LibDateTime.isDateNull(vue.getTfDATEDEBReglement())) LibDateTime.setDate(vue.getTfDATEFINReglement(),taInfoEntreprise.getDatefinInfoEntreprise());
		}
		}
		if(LibDateTime.isDateNull(vue.getTfDATEDEBReglement()))LibDateTime.setDateSystem(vue.getTfDATEDEBReglement());
		if(LibDateTime.isDateNull(vue.getTfDATEFINReglement()))LibDateTime.setDateSystem(vue.getTfDATEFINReglement());
		if(LibDateTime.isDateNull(vue.getTfDATEDEBVentes()))LibDateTime.setDateSystem(vue.getTfDATEDEBVentes());
		if(LibDateTime.isDateNull(vue.getTfDATEFINVentes()))LibDateTime.setDateSystem(vue.getTfDATEFINVentes());
	}
	
private void initBornes(){
	initBornes(TaFacture.TYPE_DOC,true);
	initBornes(TaAvoir.TYPE_DOC,true);
	initBornes(TaApporteur.TYPE_DOC,true);
	initBornes(TaAcompte.TYPE_DOC,true);
	initBornes(TaReglement.TYPE_DOC,true);
	initBornes(TaRemise.TYPE_DOC,true);
	paExportationFactureControllerOption.griserTout(vue.getCbReglementLie().getSelection());
	}

	private class HandlerListeDocument extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				ChangementDePageEvent change = new ChangementDePageEvent(this,ChangementDePageEvent.AUTRE,3,2);
				fireChangementDePage(change);
			} catch (Exception e) {
				logger.error("", e);
			}
			return event;
		}
	}

	private class HandlerImprimerReglement extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				actImprimerReglement();
			} catch (Exception e) {
				logger.error("", e);
			}
			return event;
		}
	}
	
	public void DeclencheInitBorneController(DeclencheInitBorneControllerEvent evt) {
		try {
			sourceDeclencheCommandeController = evt.getSource();
			initProposalAdapterAcompte();
			initProposalAdapterReglement();
			initProposalAdapterRemises();
			initBornes(TaAcompte.TYPE_DOC,vue.getCbAccepAcompte().getSelection());
			initBornes(TaReglement.TYPE_DOC,vue.getCbAcceptReglement().getSelection());
			initBornes(TaRemise.TYPE_DOC,vue.getCbAcceptRemise().getSelection());
			initEtatBouton();
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

	public TaApporteurDAO getMasterDaoApporteur() {
		return masterDaoApporteur;
	}

	public void setMasterDaoApporteur(TaApporteurDAO masterDaoApporteur) {
		this.masterDaoApporteur = masterDaoApporteur;
	}
}

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
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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

import fr.legrain.document.divers.TypeDoc;
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
import fr.legrain.exportation.ecrans.PaExportationFactureOption;
import fr.legrain.exportation.ecrans.PaExportationFactureReference;
import fr.legrain.exportation.preferences.PreferenceConstants;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IHMAideFacture;
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
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDePageEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.LgrSimpleTextContentProposal;
import fr.legrain.lib.data.LibChaine;
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


public class PaExportationFactureControllerReference extends JPABaseControllerSWTStandard implements
RetourEcranListener,IDeclencheInitBorneControllerListener {
	
	static Logger logger = Logger.getLogger(PaExportationFactureControllerReference.class.getName());		
	private PaExportationFactureReference vue = null; 	
	
	private TaFactureDAO masterDaoFacture = null;
	private TaAvoirDAO masterDaoAvoir = null;
	private TaAcompteDAO masterDaoAcompte = null;
	private TaReglementDAO masterDaoReglement = null;
	private TaRemiseDAO masterDaoRemise = null;
	private TaApporteurDAO masterDaoApporteur = null;
	
	private PaExportationFactureOption paExportationFactureOption=null;
	private PaExportationFactureControllerOption paExportationFactureControllerOption = null;
	
	private Object ecranAppelant = null;
	
	private boolean gerePointage = true;
	private boolean centralisation = true;
	
	protected HandlerPrecedent handlerPrecedent = new HandlerPrecedent();
	public static final String C_COMMAND_DOCUMENT_LISTE_DOCUMENT_ID = "fr.legrain.Document.listeDocument";
	protected HandlerListeDocument handlerListeDocument = new HandlerListeDocument();
	
	public static final String C_COMMAND_DOCUMENT_IMPRIMER_REGLEMENT_ID = "fr.legrain.Document.imprimerReglement";
	protected HandlerImprimerReglement handlerImprimerReglement = new HandlerImprimerReglement();
	
	
	
	protected List<IDocumentTiers> listeVerif=null;
	
	String [][] listeDocumentsFactures=null;
	String [][] listeDocumentsAvoirs=null;
	String [][] listeDocumentsApporteurs=null;
	String [][] listeDocumentsAcomptes=null;
	String [][] listeDocumentsReglements=null;
	String [][] listeDocumentsRemises=null;
	
	
	public PaExportationFactureControllerReference(PaExportationFactureReference vue) {
		this(vue,null);
	}
	
	public PaExportationFactureControllerReference(PaExportationFactureReference vue,EntityManager em) {
		initCursor(SWT.CURSOR_WAIT);
		if(em!=null) {
			setEm(em);
		}
		try {
			setVue(vue);
			this.vue=vue;
			vue.getShell().addShellListener(this);
			
//			 Branchement action annuler : empeche la fermeture automatique de la
			// fenetre sur ESC
			vue.getShell().addTraverseListener(new Traverse());
			actionImprimer.setText("Exporter [F11]");
			
			vue.getTfNumDeb().addModifyListener(new LgrModifyListener());
			vue.getTfNumDebAvoir().addModifyListener(new LgrModifyListener());
			vue.getTfNumDebApporteur().addModifyListener(new LgrModifyListener());
			vue.getTfNumDebAcompte().addModifyListener(new LgrModifyListener());
			vue.getTfNumDebReglement().addModifyListener(new LgrModifyListener());
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
		try {
			if(e.getSource().equals(vue.getTfNumDeb())) {
				if(!LibChaine.empty(vue.getTfNumDeb().getText())) {
					vue.getTfNumFin().setEnabled(true);
				} else {
					vue.getTfNumFin().setText(""); vue.getTfNumFin().setEnabled(false);
				}
			}
			if(e.getSource().equals(vue.getTfNumDebAvoir())) {
				if(!LibChaine.empty(vue.getTfNumDebAvoir().getText())) {
					vue.getTfNumFinAvoir().setEnabled(true);
				} else {
					vue.getTfNumFinAvoir().setText(""); vue.getTfNumFinAvoir().setEnabled(false);
				}
			}
			if(e.getSource().equals(vue.getTfNumDebApporteur())) {
				if(!LibChaine.empty(vue.getTfNumDebApporteur().getText())) {
					vue.getTfNumFinApporteur().setEnabled(true);
				} else {
					vue.getTfNumFinApporteur().setText(""); vue.getTfNumFinApporteur().setEnabled(false);
				}
			}			
			if(e.getSource().equals(vue.getTfNumDebAcompte())) {
				if(!LibChaine.empty(vue.getTfNumDebAcompte().getText())) {
					vue.getTfNumFinAcompte().setEnabled(true);
				} else {
					vue.getTfNumFinAcompte().setText(""); vue.getTfNumFinAcompte().setEnabled(false);

				}
			}
			if(e.getSource().equals(vue.getTfNumDebReglement())) {
				if(!LibChaine.empty(vue.getTfNumDebReglement().getText())) {
					vue.getTfNumFinReglement().setEnabled(true);
				} else {
					vue.getTfNumFinReglement().setText(""); vue.getTfNumFinReglement().setEnabled(false);

				}
			}

		} catch (Exception e1) {
			logger.error(e1);
		}		
	}
	private void initController() {
		try {
			setDaoStandard(masterDaoFacture);

		
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

			vue.getPaEcran().layout();
			vue.getScrolledComposite().setMinSize(vue.getPaEcran().computeSize(SWT.DEFAULT, SWT.DEFAULT));
		} catch (Exception e) {
			logger.error("Erreur : PaArticlesController", e);
		}
	}

	@Override
	protected void initImageBouton() {
		final String C_IMAGE_EXPORT = "/icons/export_wiz.gif";
		vue.getBtnExporterFactures().setImage(ExportationPlugin.getImageDescriptor(C_IMAGE_EXPORT).createImage());
		vue.getBtnExporterReglements().setImage(ExportationPlugin.getImageDescriptor(C_IMAGE_EXPORT).createImage());
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
		

		
		enableActionAndHandler(C_COMMAND_DOCUMENT_IMPRIMER_REGLEMENT_ID, trouveReglement );// &&!ReglementVide
		enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID, trouve );//&& !ventesVide
		enableActionAndHandler(C_COMMAND_GLOBAL_PRECEDENT_ID, true);


		initFocusSWT(masterDaoFacture,mapInitFocus);
		}	
	
	
	private boolean VentesVide(){
		return vue.getTfNumDeb().getText().trim().equals("")&& vue.getTfNumFin().getText().trim().equals("");
	}
	private boolean AvoirsVide(){
		return vue.getTfNumDebAvoir().getText().trim().equals("")&& vue.getTfNumFinAvoir().getText().trim().equals("");
	}

	private boolean ApporteursVide(){
		return vue.getTfNumDebApporteur().getText().trim().equals("")&& vue.getTfNumFinApporteur().getText().trim().equals("");
	}
	
	private boolean AcomptesVide(){
		return vue.getTfNumDebAcompte().getText().trim().equals("")&& vue.getTfNumFinAcompte().getText().trim().equals("");
	}
	private boolean ReglementsVide(){
		return vue.getTfNumDebReglement().getText().trim().equals("")&& vue.getTfNumFinReglement().getText().trim().equals("");
	}
	private boolean RemisesVide(){
		return vue.getTfNumDebRemise().getText().trim().equals("")&& vue.getTfNumFinRemise().getText().trim().equals("");
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
		listeComposantFocusable.add(vue.getTfNumDeb());
		listeComposantFocusable.add(vue.getTfNumFin());
		listeComposantFocusable.add(vue.getTfNumDebAvoir());
		listeComposantFocusable.add(vue.getTfNumFinAvoir());
		listeComposantFocusable.add(vue.getTfNumDebApporteur());
		listeComposantFocusable.add(vue.getTfNumFinApporteur());		
		listeComposantFocusable.add(vue.getTfNumDebAcompte());
		listeComposantFocusable.add(vue.getTfNumFinAcompte());
		listeComposantFocusable.add(vue.getTfNumDebReglement());
		listeComposantFocusable.add(vue.getTfNumFinReglement());
		listeComposantFocusable.add(vue.getTfNumDebRemise());
		listeComposantFocusable.add(vue.getTfNumFinRemise());
			
		listeComposantFocusable.add(vue.getBtnListeDoc());
		listeComposantFocusable.add(vue.getBtnExporterFactures());
		listeComposantFocusable.add(vue.getBtnExporterReglements());
		listeComposantFocusable.add(vue.getPaBtn1().getBtnImprimer());
		listeComposantFocusable.add(vue.getPaBtn1().getBtnFermer());
		
		if(mapInitFocus == null) 
			mapInitFocus = new LinkedHashMap();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION,vue.getTfNumDeb());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION,vue.getTfNumDeb());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION,vue.getTfNumDeb());
		
		vue.getCbRempliVentes().setSelection(true);
		vue.getCbRempliAvoir().setSelection(true);
		vue.getCbRempliApporteur().setSelection(ExportationPlugin.getDefault().
				getPreferenceStoreProject().getBoolean(PreferenceConstants.APPORTEURS));
		
		vue.getCbRempliAcompte().setSelection(ExportationPlugin.getDefault().
				getPreferenceStoreProject().getBoolean(PreferenceConstants.ACOMPTES));
		vue.getCbRempliReglement().setSelection(ExportationPlugin.getDefault().
				getPreferenceStoreProject().getBoolean(PreferenceConstants.REGLEMENT_SIMPLE));
		vue.getCbRempliRemise().setSelection(ExportationPlugin.getDefault().
				getPreferenceStoreProject().getBoolean(PreferenceConstants.REMISE));
		
		vue.getCbDocumentLie().setSelection(ExportationPlugin.getDefault().
				getPreferenceStoreProject().getBoolean(PreferenceConstants.DOCUMENTS_LIES));
		vue.getCbReglement().setSelection(ExportationPlugin.getDefault().
				getPreferenceStoreProject().getBoolean(PreferenceConstants.REGLEMENTS_LIES));
		vue.getCbPointages().setSelection(ExportationPlugin.getDefault().
				getPreferenceStoreProject().getBoolean(PreferenceConstants.POINTAGES));	
		vue.getCbPointages2().setSelection(ExportationPlugin.getDefault().
				getPreferenceStoreProject().getBoolean(PreferenceConstants.POINTAGES));
		vue.getCbPointages().setSelection(vue.getCbReglement().getSelection());
		vue.getCbPointages2().setSelection(vue.getCbReglement().getSelection());
		vue.getCbCentralisation().setSelection(ExportationPlugin.getDefault().
				getPreferenceStoreProject().getBoolean(PreferenceConstants.CENTRALISATION));
		
		findExpandIem(vue.getExpandBarOption(), paExportationFactureOption).setExpanded(vue.getCbReglement().getSelection());
		
		vue.getCbDocumentLie().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ExportationPlugin.getDefault().getPreferenceStoreProject().
				setValue(PreferenceConstants.DOCUMENTS_LIES, vue.getCbDocumentLie().getSelection());
			}
		});
		


	
		vue.getCbReglement().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					ExportationPlugin.getDefault().getPreferenceStoreProject().
					setValue(PreferenceConstants.REGLEMENTS_LIES, vue.getCbReglement().getSelection());
					vue.getCbPointages().setSelection(vue.getCbReglement().getSelection());
					vue.getCbPointages2().setSelection(vue.getCbReglement().getSelection());
					paExportationFactureControllerOption.griserTout(vue.getCbReglement().getSelection());
					findExpandIem(vue.getExpandBarOption(), paExportationFactureOption).setExpanded(vue.getCbReglement().getSelection());
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
		
		
		vue.getCbRempliRemise().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {				
				try {	
					ExportationPlugin.getDefault().getPreferenceStoreProject().
					setValue(PreferenceConstants.REMISE, vue.getCbRempliRemise().getSelection());
					paExportationFactureControllerOption.initEcranSurPreferences(PreferenceConstants.REMISE);
					initBornes(TaRemise.TYPE_DOC);
				} catch (Exception e1) {
					logger.error("", e1);
				}
				}
			});
		vue.getCbRempliReglement().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {	
					ExportationPlugin.getDefault().getPreferenceStoreProject().
					setValue(PreferenceConstants.REGLEMENT_SIMPLE, vue.getCbRempliReglement().getSelection());
					paExportationFactureControllerOption.initEcranSurPreferences(PreferenceConstants.REGLEMENT_SIMPLE);
				initBornes(TaReglement.TYPE_DOC);
			} catch (Exception e1) {
				logger.error("", e1);
			}
			}
		});

		vue.getCbRempliAcompte().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {	
					ExportationPlugin.getDefault().getPreferenceStoreProject().
					setValue(PreferenceConstants.ACOMPTES, vue.getCbRempliAcompte().getSelection());
					paExportationFactureControllerOption.initEcranSurPreferences(PreferenceConstants.ACOMPTES);
				initBornes(TaAcompte.TYPE_DOC);
			} catch (Exception e1) {
				logger.error("", e1);
			}
			}
		});
		vue.getCbRempliVentes().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				initBornes(TaFacture.TYPE_DOC);
			}
			
		});
		vue.getCbRempliAvoir().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				initBornes(TaAvoir.TYPE_DOC);
			}
			
		});
		vue.getCbRempliApporteur().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ExportationPlugin.getDefault().getPreferenceStoreProject().
				setValue(PreferenceConstants.APPORTEURS, vue.getCbRempliApporteur().getSelection());
				initProposalAdapterApporteur();
				initBornes(TaApporteur.TYPE_DOC);
			}
			
		});		
		vue.getCbReExpFacture().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				initProposalAdapterFacture();
				initBornes(TaFacture.TYPE_DOC);
			}
			
		});
		
		vue.getCbReExpAcompte().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				initProposalAdapterAcompte();
				initBornes(TaAcompte.TYPE_DOC);
			}
			
		});
		vue.getCbReExpAvoir().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				initProposalAdapterAvoir();
				initBornes(TaAvoir.TYPE_DOC);
			}
			
		});	
		vue.getCbExpApporteur().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				initProposalAdapterApporteur();
				initBornes(TaApporteur.TYPE_DOC);
			}
			
		});		
		vue.getCbReExpReglement().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				initProposalAdapterReglement();
				initBornes(TaReglement.TYPE_DOC);
			}			
		});
		
		vue.getCbReExpRemise().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				initProposalAdapterRemises();
				initBornes(TaRemise.TYPE_DOC);
			}
			
		});
		vue.getCbReExport().addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				vue.getCbReExpFacture().setSelection(vue.getCbReExport().getSelection());
				vue.getCbReExpAvoir().setSelection(vue.getCbReExport().getSelection());
				vue.getCbExpApporteur().setSelection(vue.getCbReExport().getSelection());
				vue.getCbReExpAcompte().setSelection(vue.getCbReExport().getSelection());
				vue.getCbReExpReglement().setSelection(vue.getCbReExport().getSelection());
				vue.getCbReExpRemise().setSelection(vue.getCbReExport().getSelection());
				initProposalAdapter();
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


	}
	public void initProposalAdapterFacture(){
		String activationContentProposal = "Ctrl+Space";
		listeDocumentsFactures=initContentProposalFacture();
		ContentProposalAdapter codeFactureContentProposalDebut = LgrSimpleTextContentProposal.defaultTextContentProposalKey(vue.getTfNumDeb(),activationContentProposal,listeDocumentsFactures,null);
		LgrSimpleTextContentProposal.defaultOptions(codeFactureContentProposalDebut);

		ContentProposalAdapter codeFactureContentProposalFin = LgrSimpleTextContentProposal.defaultTextContentProposalKey(vue.getTfNumFin(),activationContentProposal,listeDocumentsFactures,null);
		LgrSimpleTextContentProposal.defaultOptions(codeFactureContentProposalFin);
		
	}
	public void initProposalAdapterAvoir(){
		String activationContentProposal = "Ctrl+Space";
		//branchement des contents proposal Avoir
		listeDocumentsAvoirs=initContentProposalAvoir();

		ContentProposalAdapter codeAvoirContentProposalDebut = LgrSimpleTextContentProposal.defaultTextContentProposalKey(vue.getTfNumDebAvoir(),activationContentProposal,listeDocumentsAvoirs,null);
		LgrSimpleTextContentProposal.defaultOptions(codeAvoirContentProposalDebut);

		ContentProposalAdapter codeAvoirContentProposalFin = LgrSimpleTextContentProposal.defaultTextContentProposalKey(vue.getTfNumFinAvoir(),activationContentProposal,listeDocumentsAvoirs,null);
		LgrSimpleTextContentProposal.defaultOptions(codeAvoirContentProposalFin);
	}
	public void initProposalAdapterApporteur(){
		String activationContentProposal = "Ctrl+Space";
		//branchement des contents proposal Apporteur
		listeDocumentsApporteurs=initContentProposalApporteur();

		ContentProposalAdapter codeAvoirContentProposalDebut = LgrSimpleTextContentProposal.defaultTextContentProposalKey(vue.getTfNumDebApporteur(),activationContentProposal,listeDocumentsApporteurs,null);
		LgrSimpleTextContentProposal.defaultOptions(codeAvoirContentProposalDebut);

		ContentProposalAdapter codeAvoirContentProposalFin = LgrSimpleTextContentProposal.defaultTextContentProposalKey(vue.getTfNumFinApporteur(),activationContentProposal,listeDocumentsApporteurs,null);
		LgrSimpleTextContentProposal.defaultOptions(codeAvoirContentProposalFin);
	}	
	public void initProposalAdapterAcompte(){
		String activationContentProposal = "Ctrl+Space";
		
		//branchement des contents proposal Acompte
		listeDocumentsAcomptes=initContentProposalAcompte();

		ContentProposalAdapter codeAcompteContentProposalDebut = LgrSimpleTextContentProposal.defaultTextContentProposalKey(vue.getTfNumDebAcompte(),activationContentProposal,listeDocumentsAcomptes,null);
		LgrSimpleTextContentProposal.defaultOptions(codeAcompteContentProposalDebut);

		ContentProposalAdapter codeAcompteContentProposalFin = LgrSimpleTextContentProposal.defaultTextContentProposalKey(vue.getTfNumFinAcompte(),activationContentProposal,listeDocumentsAcomptes,null);
		LgrSimpleTextContentProposal.defaultOptions(codeAcompteContentProposalFin);
	}
	public void initProposalAdapterReglement(){
		String activationContentProposal = "Ctrl+Space";
		
		//branchement des contents proposal Reglement
		listeDocumentsReglements=initContentProposalReglement();

		ContentProposalAdapter codeReglementContentProposalDebut = LgrSimpleTextContentProposal.defaultTextContentProposalKey(vue.getTfNumDebReglement(),activationContentProposal,listeDocumentsReglements,null);
		LgrSimpleTextContentProposal.defaultOptions(codeReglementContentProposalDebut);

		ContentProposalAdapter codeReglementContentProposalFin = LgrSimpleTextContentProposal.defaultTextContentProposalKey(vue.getTfNumFinReglement(),activationContentProposal,listeDocumentsReglements,null);
		LgrSimpleTextContentProposal.defaultOptions(codeReglementContentProposalFin);
	}
	public void initProposalAdapterRemises(){
		String activationContentProposal = "Ctrl+Space";
		
		//branchement des contents proposal Remises
		listeDocumentsRemises=initContentProposalRemise();

		ContentProposalAdapter codeRemiseContentProposalDebut = LgrSimpleTextContentProposal.defaultTextContentProposalKey(vue.getTfNumDebRemise(),activationContentProposal,listeDocumentsRemises,null);
		LgrSimpleTextContentProposal.defaultOptions(codeRemiseContentProposalDebut);

		ContentProposalAdapter codeRemiseContentProposalFin = LgrSimpleTextContentProposal.defaultTextContentProposalKey(vue.getTfNumFinRemise(),activationContentProposal,listeDocumentsRemises,null);
		LgrSimpleTextContentProposal.defaultOptions(codeRemiseContentProposalFin);
	}
	public void initProposalAdapter(){		
		initProposalAdapterVentes();
		initProposalAdapterTousReglements();
	}
	
	public void initProposalAdapterVentes(){		
		initProposalAdapterFacture();
		initProposalAdapterAvoir();
		initProposalAdapterApporteur();
		initBornesVente();
	}
	
	public void initProposalAdapterTousReglements(){		
		initProposalAdapterAcompte();
		initProposalAdapterReglement();
		initProposalAdapterRemises();
		initBornesTousReglements();
	}
	
	public String[][] initContentProposalFacture(){
		String[][] valeurs = null;
		boolean affichageCtrlEspace = ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.AFFICHAGE_CTRL_ESPACE);
		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();

		List<TaFacture> l =new LinkedList<TaFacture>();
		List<TaFacture> temp =null;
		if(!vue.getCbReExpFacture().getSelection())temp = masterDaoFacture.rechercheDocumentNonExporte(taInfoEntreprise.getDatedebInfoEntreprise(), 
				taInfoEntreprise.getDatefinInfoEntreprise(),false);
		else temp = masterDaoFacture.rechercheDocument(taInfoEntreprise.getDatedebInfoEntreprise(), 
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
	
	public String[][] initContentProposalAvoir(){		
		String[][] valeurs = null;
		boolean affichageCtrlEspace = ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.AFFICHAGE_CTRL_ESPACE);
		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();		
		List<TaAvoir> l =new LinkedList<TaAvoir>();
		List<TaAvoir> temp =null;
		if(!vue.getCbReExpAvoir().getSelection())temp =masterDaoAvoir.rechercheDocumentNonExporte(taInfoEntreprise.getDatedebInfoEntreprise(), 
				taInfoEntreprise.getDatefinInfoEntreprise(),false);
		else temp = masterDaoAvoir.rechercheDocument(taInfoEntreprise.getDatedebInfoEntreprise(), 
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
			description += taAvoir.getLibelleDocument();
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
		if(!vue.getCbExpApporteur().getSelection())temp =masterDaoApporteur.rechercheDocumentNonExporte(taInfoEntreprise.getDatedebInfoEntreprise(), 
				taInfoEntreprise.getDatefinInfoEntreprise(),false);
		else temp = masterDaoApporteur.rechercheDocument(taInfoEntreprise.getDatedebInfoEntreprise(), 
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
			description += taApporteur.getLibelleDocument();
			valeurs[i][1] = description;

			i++;
		}
		}
		return valeurs;		
	}
	
	public String[][] initContentProposalAcompte(){		
		String[][] valeurs = null;
		boolean affichageCtrlEspace = ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(
				PreferenceConstants.AFFICHAGE_CTRL_ESPACE);
		if(ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.ACOMPTES)){
		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();		
		List<TaAcompte> l = new LinkedList<TaAcompte>();
		List<TaAcompte> temp =masterDaoAcompte.rechercheAcompteNonRemises(null,taInfoEntreprise.getDatedebInfoEntreprise(), 
				taInfoEntreprise.getDatefinInfoEntreprise(),vue.getCbReExpAcompte().getSelection(),null,null,false);
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
		if(ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.REGLEMENT_SIMPLE)){
		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
		
		List<TaReglement> l = new LinkedList<TaReglement>();
		List<TaReglement> temp =null;
		//on ne prend que les réglements non integrés dans une remise
		temp=masterDaoReglement.rechercheReglementNonRemises(null,taInfoEntreprise.getDatedebInfoEntreprise(), 
				taInfoEntreprise.getDatefinInfoEntreprise(),vue.getCbReExpReglement().getSelection(),null,null,false);
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
		if(ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.REMISE)){
		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
		
		List<TaRemise> l = new LinkedList<TaRemise>();
		List<TaRemise> temp =null;
		if(!vue.getCbReExpRemise().getSelection())temp=masterDaoRemise.rechercheDocumentNonExporte(taInfoEntreprise.getDatedebInfoEntreprise(), 
				taInfoEntreprise.getDatefinInfoEntreprise(),false);
		else
			temp=masterDaoRemise.rechercheDocument(taInfoEntreprise.getDatedebInfoEntreprise(), 
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
			description += taDoc.getLibelleDocument();
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
		mapCommand.put(C_COMMAND_GLOBAL_PRECEDENT_ID, handlerPrecedent);
		mapCommand.put(C_COMMAND_DOCUMENT_LISTE_DOCUMENT_ID, handlerListeDocument);
		mapCommand.put(C_COMMAND_DOCUMENT_IMPRIMER_REGLEMENT_ID, handlerImprimerReglement);
		
		initFocusCommand(String.valueOf(this.hashCode()),
				listeComposantFocusable, mapCommand);
		
		if (mapActions == null)
			mapActions = new LinkedHashMap();
		
		mapActions.put(vue.getBtnExporterFactures(), C_COMMAND_GLOBAL_IMPRIMER_ID);
		mapActions.put(vue.getBtnExporterReglements(), C_COMMAND_DOCUMENT_IMPRIMER_REGLEMENT_ID);
		mapActions.put(vue.getPaBtn1().getBtnFermer(), C_COMMAND_GLOBAL_PRECEDENT_ID);
		mapActions.put(vue.getPaBtn1().getBtnImprimer(), C_COMMAND_GLOBAL_FERMER_ID);
		mapActions.put(vue.getBtnListeDoc(), C_COMMAND_DOCUMENT_LISTE_DOCUMENT_ID);
		
		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID };
		mapActions.put(null, tabActionsAutres);
	}
	
	public PaExportationFactureControllerReference getThis(){
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

		List<TaAcompte> idAcompteAExporter = null;
		List<TaReglement> idReglementAExporter = null;
		List<TaRemise> idRemiseAExporter = null;
		
		boolean reExportFacture = vue.getCbReExpFacture().getSelection();
		boolean reExportAvoir = vue.getCbReExpAvoir().getSelection();
		boolean reExportApporteur = vue.getCbExpApporteur().getSelection();
		boolean reExportAcompte = vue.getCbReExpAcompte().getSelection();
		boolean reExportReglement = vue.getCbReExpReglement().getSelection();
		boolean reExportRemise = vue.getCbReExpRemise().getSelection();
		
		Date dateDeb = null;
		Date dateFin = null;

		String codeDebAcompte = null;
		String codeFinAcompte = null;
		String codeDebReglement = null;
		String codeFinReglement = null;
		String codeDebRemise = null;
		String codeFinRemise = null;

		if(!LibChaine.empty(vue.getTfNumDebAcompte().getText())) {
			codeDebAcompte = vue.getTfNumDebAcompte().getText().toUpperCase();
		}
		if(!LibChaine.empty(vue.getTfNumFinAcompte().getText())) {
			codeFinAcompte = vue.getTfNumFinAcompte().getText().toUpperCase();
		}
		if(!LibChaine.empty(vue.getTfNumDebReglement().getText())) {
			codeDebReglement = vue.getTfNumDebReglement().getText().toUpperCase();
		}
		if(!LibChaine.empty(vue.getTfNumFinReglement().getText())) {
			codeFinReglement = vue.getTfNumFinReglement().getText().toUpperCase();
		}
		if(!LibChaine.empty(vue.getTfNumDebRemise().getText())) {
			codeDebRemise = vue.getTfNumDebRemise().getText().toUpperCase();
		}
		if(!LibChaine.empty(vue.getTfNumFinRemise().getText())) {
			codeFinRemise = vue.getTfNumFinRemise().getText().toUpperCase();
		}

		if( codeDebAcompte!=null || codeDebReglement!=null || codeDebRemise!=null) {//1 code => cette facture
			logger.debug("Exportation - selection par code");

			if(codeDebAcompte!=null) {
				if(codeFinAcompte!=null) {//2 codes => factures entre les 2 codes si intervalle correct
					idAcompteAExporter = masterDaoAcompte.rechercheDocument(codeDebAcompte,codeFinAcompte);
				} else {
					codeFinAcompte = codeDebAcompte;
					idAcompteAExporter = masterDaoAcompte.rechercheDocument(codeDebAcompte,codeFinAcompte);
				}
			}
			if(codeDebReglement!=null) {
				if(codeFinReglement!=null) {//2 codes => factures entre les 2 codes si intervalle correct
					idReglementAExporter = masterDaoReglement.rechercheDocument(codeDebReglement,codeFinReglement);
				} else {
					codeFinReglement = codeDebReglement;
					idReglementAExporter = masterDaoReglement.rechercheDocument(codeDebReglement,codeFinReglement);
				}
			}
			if(codeDebRemise!=null) {
				if(codeFinRemise!=null) {//2 codes => factures entre les 2 codes si intervalle correct
					idRemiseAExporter = masterDaoRemise.rechercheDocument(codeDebRemise,codeFinRemise);
				} else {
					codeFinRemise = codeDebRemise;
					idRemiseAExporter = masterDaoRemise.rechercheDocument(codeDebRemise,codeFinRemise);
				}
			}			
		} else if(dateDeb!=null) {//1 date => toutes les factures à cette date
			logger.debug("Exportation - selection par date");
			if(dateFin!=null) {//2 dates => factures entre les 2 dates si intervalle correct
				if(LibDate.compareTo(dateDeb,dateFin)>0) {
					MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
							"ATTENTION", "La date de début doit être antérieure à la date fin");
					throw new Exception("probleme intervalle des dates");
				}				

			} else {
				dateFin = dateDeb;
			}

			idAcompteAExporter = masterDaoAcompte.rechercheAcompteNonRemises(null,dateDeb,dateFin,reExportReglement,null,null,false);
			idReglementAExporter = masterDaoReglement.rechercheReglementNonRemises(null,dateDeb,dateFin,reExportReglement,null,null,false);
			idRemiseAExporter = masterDaoRemise.rechercheDocument(dateDeb,dateFin);
			
		} 
		///////////////////////////////////////

		final List<TaAcompte> finalIdAcompteAExporter = idAcompteAExporter;
		final List<TaReglement> finalIdReglementAExporter = idReglementAExporter;
		final List<TaRemise> finalIdRemiseAExporter = idRemiseAExporter;
		
		final boolean finalReExportFacture = reExportFacture;
		final boolean finalReExportAvoir = reExportAvoir;
		final boolean finalReExportApporteur = reExportApporteur;
		final boolean finalReExportAcompte = reExportAcompte;
		final boolean finalReExportReglement = reExportReglement;
		final boolean finalReExportRemise = reExportRemise;

		gerePointage=ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.POINTAGES);
		centralisation=ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.CENTRALISATION);
		ExportationEpicea retour=exporter(null,null,null,finalIdAcompteAExporter,finalIdReglementAExporter,finalIdRemiseAExporter,
				finalReExportFacture,finalReExportAvoir,finalReExportApporteur,finalReExportAcompte,finalReExportReglement,finalReExportRemise,gerePointage,centralisation);
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

		List<TaFacture> idFactureAExporter = null;
		List<TaAvoir> idAvoirAExporter = null;
		List<TaApporteur> idApporteurAExporter = null;
		
		boolean reExportFacture = vue.getCbReExpFacture().getSelection();
		boolean reExportAvoir = vue.getCbReExpAvoir().getSelection();
		boolean reExportApporteur = vue.getCbExpApporteur().getSelection();
		boolean reExportAcompte = vue.getCbReExpAcompte().getSelection();
		boolean reExportReglement = vue.getCbReExpReglement().getSelection();
		boolean reExportRemise = vue.getCbReExpRemise().getSelection();
		
		Date dateDeb = null;
		Date dateFin = null;
		String codeDeb = null;
		String codeFin = null;
		String codeDebAvoir = null;
		String codeFinAvoir = null;
		String codeDebApporteur = null;
		String codeFinApporteur = null;		

		
		if(!LibChaine.empty(vue.getTfNumDeb().getText())) {
			codeDeb = vue.getTfNumDeb().getText().toUpperCase();
		}
		if(!LibChaine.empty(vue.getTfNumFin().getText())) {
			codeFin = vue.getTfNumFin().getText().toUpperCase();
		}
		if(!LibChaine.empty(vue.getTfNumDebAvoir().getText())) {
			codeDebAvoir = vue.getTfNumDebAvoir().getText().toUpperCase();
		}
		if(!LibChaine.empty(vue.getTfNumFinAvoir().getText())) {
			codeFinAvoir = vue.getTfNumFinAvoir().getText().toUpperCase();
		}
		if(!LibChaine.empty(vue.getTfNumDebApporteur().getText())) {
			codeDebApporteur = vue.getTfNumDebApporteur().getText().toUpperCase();
		}
		if(!LibChaine.empty(vue.getTfNumFinApporteur().getText())) {
			codeFinApporteur = vue.getTfNumFinApporteur().getText().toUpperCase();
		}		

		
		if(codeDeb!=null || codeDebAvoir!=null || codeDebApporteur!=null) {//1 code => cette facture
			logger.debug("Exportation - selection par code");
			if(codeDeb!=null) {
				if(codeFin!=null) {//2 codes => factures entre les 2 codes si intervalle correct
					idFactureAExporter = masterDaoFacture.rechercheDocument(codeDeb,codeFin);
				} else {
					codeFin = codeDeb;
					idFactureAExporter = masterDaoFacture.rechercheDocument(codeDeb,codeFin);
				}
			}
			if(codeDebAvoir!=null) {
				if(codeFinAvoir!=null) {//2 codes => factures entre les 2 codes si intervalle correct
					idAvoirAExporter = masterDaoAvoir.rechercheDocument(codeDebAvoir,codeFinAvoir);
				} else {
					codeFinAvoir = codeDebAvoir;
					idAvoirAExporter = masterDaoAvoir.rechercheDocument(codeDebAvoir,codeFinAvoir);
				}
			}
			if(codeDebApporteur!=null) {
				if(codeFinApporteur!=null) {//2 codes => factures entre les 2 codes si intervalle correct
					idApporteurAExporter = masterDaoApporteur.rechercheDocument(codeDebApporteur,codeFinApporteur);
				} else {
					codeFinApporteur = codeDebApporteur;
					idApporteurAExporter = masterDaoApporteur.rechercheDocument(codeDebApporteur,codeFinApporteur);
				}
			}			
			
		} else if(dateDeb!=null) {//1 date => toutes les factures à cette date
			logger.debug("Exportation - selection par date");
			if(dateFin!=null) {//2 dates => factures entre les 2 dates si intervalle correct
				
				if(LibDate.compareTo(dateDeb,dateFin)>0) {
					MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
							"ATTENTION", "La date de début doit être antérieure à la date fin");
					throw new Exception("probleme intervalle des dates");
				}				

			} else {
				dateFin = dateDeb;
			}
			idFactureAExporter = masterDaoFacture.rechercheDocument(dateDeb,dateFin);
			idAvoirAExporter = masterDaoAvoir.rechercheDocument(dateDeb,dateFin);
			idApporteurAExporter = masterDaoApporteur.rechercheDocument(dateDeb,dateFin);

			
		} 
		///////////////////////////////////////

		final List<TaFacture> finalIdFactureAExporter = idFactureAExporter;
		final List<TaAvoir> finalIdAvoirAExporter = idAvoirAExporter;
		final List<TaApporteur> finalIdApporteurAExporter = idApporteurAExporter;

		
		final boolean finalReExportFacture = reExportFacture;
		final boolean finalReExportAvoir = reExportAvoir;
		final boolean finalReExportApporteur = reExportApporteur;
		final boolean finalReExportAcompte = reExportAcompte;
		final boolean finalReExportReglement = reExportReglement;
		final boolean finalReExportRemise = reExportRemise;

		gerePointage=ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.POINTAGES);
		centralisation=ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.CENTRALISATION);

		ExportationEpicea retour=exporter(finalIdFactureAExporter,finalIdAvoirAExporter,finalIdApporteurAExporter,null,null,null,
				finalReExportFacture,finalReExportAvoir,finalReExportApporteur,finalReExportAcompte,finalReExportReglement,finalReExportRemise,gerePointage,centralisation);
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
			boolean reExportReglement,boolean reExportRemise,final boolean gererPointages,final boolean centralisation) throws Exception{

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
	VerrouInterface.setVerrouille(true);
	try {
		vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
		ParamAfficheAideRechercheSWT paramAfficheAideRecherche = new ParamAfficheAideRechercheSWT();
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
		
			if (getFocusCourantSWT().equals(vue.getTfNumDeb())||
					getFocusCourantSWT().equals(vue.getTfNumFin())) {

				TaFactureDAO dao =new TaFactureDAO(getEm());
				paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
				controllerEcranCreation = this;
				paramAfficheAideRecherche.setTypeEntite(TaFacture.class);
				paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_DOCUMENT);
				if(getFocusCourantSWT().equals(vue.getTfNumDeb()))
					paramAfficheAideRecherche.setDebutRecherche(vue.getTfNumDeb().getText());
				else
					paramAfficheAideRecherche.setDebutRecherche(vue.getTfNumFin().getText());
				paramAfficheAideRecherche.setCleListeTitre("SWTPaEditorFactureController");
				paramAfficheAideRecherche.setControllerAppelant(PaExportationFactureControllerReference.this);
				paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaFactureDAO,TaFacture>(dao,IHMAideFacture.class));
				paramAfficheAideRecherche.setTypeObjet(IHMAideFacture.class);
				paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_DOCUMENT);
			}
			if (getFocusCourantSWT().equals(vue.getTfNumDebAvoir())||
					getFocusCourantSWT().equals(vue.getTfNumFinAvoir())) {

				TaAvoirDAO dao =new TaAvoirDAO(getEm());
				paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
				controllerEcranCreation = this;
				paramAfficheAideRecherche.setTypeEntite(TaAvoir.class);
				paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_DOCUMENT);
				if(getFocusCourantSWT().equals(vue.getTfNumDebAvoir()))
					paramAfficheAideRecherche.setDebutRecherche(vue.getTfNumDebAvoir().getText());
				else
					paramAfficheAideRecherche.setDebutRecherche(vue.getTfNumFinAvoir().getText());
				paramAfficheAideRecherche.setCleListeTitre("PaEditorAvoirController");
				paramAfficheAideRecherche.setControllerAppelant(PaExportationFactureControllerReference.this);
				paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaAvoirDAO,TaAvoir>(dao,IHMAideFacture.class));
				paramAfficheAideRecherche.setTypeObjet(IHMAideFacture.class);
				paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_DOCUMENT);
			}	
			
			if (getFocusCourantSWT().equals(vue.getTfNumDebApporteur())||
					getFocusCourantSWT().equals(vue.getTfNumFinApporteur())) {

				TaApporteurDAO dao =new TaApporteurDAO(getEm());
				paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
				controllerEcranCreation = this;
				paramAfficheAideRecherche.setTypeEntite(TaApporteur.class);
				paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_DOCUMENT);
				if(getFocusCourantSWT().equals(vue.getTfNumDebApporteur()))
					paramAfficheAideRecherche.setDebutRecherche(vue.getTfNumDebApporteur().getText());
				else
					paramAfficheAideRecherche.setDebutRecherche(vue.getTfNumFinApporteur().getText());
				paramAfficheAideRecherche.setCleListeTitre("PaEditorApporteurController");
				paramAfficheAideRecherche.setControllerAppelant(PaExportationFactureControllerReference.this);
				paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaApporteurDAO,TaApporteur>(dao,IHMAideFacture.class));
				paramAfficheAideRecherche.setTypeObjet(IHMAideFacture.class);
				paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_DOCUMENT);
			}		
			
			if (getFocusCourantSWT().equals(vue.getTfNumDebAcompte())||
					getFocusCourantSWT().equals(vue.getTfNumFinAcompte())) {

				TaAcompteDAO dao =new TaAcompteDAO(getEm());
				paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
				controllerEcranCreation = this;
				paramAfficheAideRecherche.setTypeEntite(TaAcompte.class);
				paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_DOCUMENT);
				if(getFocusCourantSWT().equals(vue.getTfNumDebAcompte()))
					paramAfficheAideRecherche.setDebutRecherche(vue.getTfNumDebAcompte().getText());
				else
					paramAfficheAideRecherche.setDebutRecherche(vue.getTfNumFinAcompte().getText());
				paramAfficheAideRecherche.setCleListeTitre("PaEditorAcompteController");
				paramAfficheAideRecherche.setControllerAppelant(PaExportationFactureControllerReference.this);
				paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaAcompteDAO,TaAcompte>(dao,IHMAideFacture.class));
				paramAfficheAideRecherche.setTypeObjet(IHMAideFacture.class);
				paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_DOCUMENT);
			}	
			
			if (getFocusCourantSWT().equals(vue.getTfNumDebReglement())||
					getFocusCourantSWT().equals(vue.getTfNumFinReglement())) {

				TaReglementDAO dao =new TaReglementDAO(getEm());
				paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
				controllerEcranCreation = this;
				paramAfficheAideRecherche.setTypeEntite(TaReglement.class);
				paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_DOCUMENT);
				if(getFocusCourantSWT().equals(vue.getTfNumDebReglement()))
					paramAfficheAideRecherche.setDebutRecherche(vue.getTfNumDebReglement().getText());
				else
					paramAfficheAideRecherche.setDebutRecherche(vue.getTfNumFinReglement().getText());
				paramAfficheAideRecherche.setCleListeTitre("PaEditorAvoirController");
				paramAfficheAideRecherche.setControllerAppelant(PaExportationFactureControllerReference.this);
				paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaReglementDAO,TaReglement>(dao,IHMAideFacture.class));
				paramAfficheAideRecherche.setTypeObjet(IHMAideFacture.class);
				paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_DOCUMENT);
			}	
			
			if (getFocusCourantSWT().equals(vue.getTfNumDebRemise())||
					getFocusCourantSWT().equals(vue.getTfNumFinRemise())) {

				TaRemiseDAO dao =new TaRemiseDAO(getEm());
				paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
				controllerEcranCreation = this;
				paramAfficheAideRecherche.setTypeEntite(TaRemise.class);
				paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_DOCUMENT);
				if(getFocusCourantSWT().equals(vue.getTfNumDebRemise()))
					paramAfficheAideRecherche.setDebutRecherche(vue.getTfNumDebRemise().getText());
				else
					paramAfficheAideRecherche.setDebutRecherche(vue.getTfNumFinRemise().getText());
				paramAfficheAideRecherche.setCleListeTitre("PaEditorAvoirController");
				paramAfficheAideRecherche.setControllerAppelant(PaExportationFactureControllerReference.this);
				paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAideFacture,TaRemiseDAO,TaRemise>(dao,IHMAideFacture.class));
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
		VerrouInterface.setVerrouille(false);
		vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
	}
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
		vue.getCbReglement().setSelection(ExportationPlugin.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstants.REGLEMENTS_LIES));
		findExpandIem(vue.getExpandBarOption(), paExportationFactureOption).setExpanded(vue.getCbReglement().getSelection());
		vue.getCbRempliVentes().setSelection(true);
		vue.getCbRempliAvoir().setSelection(true);
		vue.getCbRempliApporteur().setSelection(true);
		initProposalAdapter();
		initBornes();
		initEtatBouton();
	}

	private void initBornes(String type){
		if(type.equals(TaFacture.TYPE_DOC)){
			vue.getTfNumDeb().setText("");
			vue.getTfNumFin().setText("");
			if(vue.getCbRempliVentes().getSelection())
				vue.getCbRempliVentes().setSelection( listeDocumentsFactures!=null &&listeDocumentsFactures.length>0);
		}
		if(type.equals(TaAvoir.TYPE_DOC)){
			vue.getTfNumDebAvoir().setText("");
			vue.getTfNumFinAvoir().setText("");
			if(vue.getCbRempliAvoir().getSelection())
				vue.getCbRempliAvoir().setSelection(listeDocumentsAvoirs!=null &&listeDocumentsAvoirs.length>0);
		}
		if(type.equals(TaApporteur.TYPE_DOC)){
			vue.getTfNumDebApporteur().setText("");
			vue.getTfNumFinApporteur().setText("");
			if(vue.getCbRempliApporteur().getSelection())
				vue.getCbRempliApporteur().setSelection(listeDocumentsApporteurs!=null &&listeDocumentsApporteurs.length>0);
		}		
		//reglements
		if(type.equals(TaAcompte.TYPE_DOC)){
			vue.getTfNumDebAcompte().setText("");
			vue.getTfNumFinAcompte().setText("");
			vue.getCbRempliAcompte().setSelection(listeDocumentsAcomptes!=null &&listeDocumentsAcomptes.length>0 );
		}
		if(type.equals(TaReglement.TYPE_DOC)){
			vue.getTfNumDebReglement().setText("");
			vue.getTfNumFinReglement().setText("");
			vue.getCbRempliReglement().setSelection(listeDocumentsReglements!=null &&listeDocumentsReglements.length>0  );
		}
		if(type.equals(TaRemise.TYPE_DOC)){
			vue.getTfNumDebRemise().setText("");
			vue.getTfNumFinRemise().setText("");
			vue.getCbRempliRemise().setSelection(listeDocumentsRemises!=null &&listeDocumentsRemises.length>0 );
		}

		if(type.equals(TaFacture.TYPE_DOC)){
			if(vue.getCbRempliVentes().getSelection()==true){
				if(listeDocumentsFactures!=null &&listeDocumentsFactures.length>0){
					vue.getTfNumDeb().setText(listeDocumentsFactures[0][0]);
					vue.getTfNumFin().setText(listeDocumentsFactures[listeDocumentsFactures.length-1][0]);
				}
			}
		}
		if(type.equals(TaAvoir.TYPE_DOC)){
			if(vue.getCbRempliAvoir().getSelection()==true){
				if(listeDocumentsAvoirs!=null &&listeDocumentsAvoirs.length>0){
					vue.getTfNumDebAvoir().setText(listeDocumentsAvoirs[0][0]);
					vue.getTfNumFinAvoir().setText(listeDocumentsAvoirs[listeDocumentsAvoirs.length-1][0]);
				}
			}
		}
		if(type.equals(TaApporteur.TYPE_DOC)){
			if(vue.getCbRempliApporteur().getSelection()==true){
				if(listeDocumentsApporteurs!=null &&listeDocumentsApporteurs.length>0){
					vue.getTfNumDebApporteur().setText(listeDocumentsApporteurs[0][0]);
					vue.getTfNumFinApporteur().setText(listeDocumentsApporteurs[listeDocumentsApporteurs.length-1][0]);
				}
			}
		}		
		if(type.equals(TaReglement.TYPE_DOC)){
			if(vue.getCbRempliReglement().getSelection()==true){
				if(listeDocumentsReglements!=null &&listeDocumentsReglements.length>0){
					vue.getTfNumDebReglement().setText(listeDocumentsReglements[0][0]);
					vue.getTfNumFinReglement().setText(listeDocumentsReglements[listeDocumentsReglements.length-1][0]);
				}
			}
		}
		if(type.equals(TaAcompte.TYPE_DOC)){
			if(vue.getCbRempliAcompte().getSelection()==true){
				if(listeDocumentsAcomptes!=null && listeDocumentsAcomptes.length>0){
					vue.getTfNumDebAcompte().setText(listeDocumentsAcomptes[0][0]);
					vue.getTfNumFinAcompte().setText(listeDocumentsAcomptes[listeDocumentsAcomptes.length-1][0]);
				}
			}
		}
		if(type.equals(TaRemise.TYPE_DOC)){
			if(vue.getCbRempliRemise().getSelection()==true){
				if(listeDocumentsRemises!=null &&listeDocumentsRemises.length>0){
					vue.getTfNumDebRemise().setText(listeDocumentsRemises[0][0]);
					vue.getTfNumFinRemise().setText(listeDocumentsRemises[listeDocumentsRemises.length-1][0]);
				}
			}
		}
	}
	private void initBornes(){
		initBornesVente();
		initBornesTousReglements();
		paExportationFactureControllerOption.griserTout(vue.getCbReglement().getSelection());
	}
	
	private void initBornesVente(){
		initBornes(TaFacture.TYPE_DOC);
		initBornes(TaAvoir.TYPE_DOC);
		initBornes(TaApporteur.TYPE_DOC);		
	}
	private void initBornesTousReglements(){
		initBornes(TaAcompte.TYPE_DOC);
		initBornes(TaReglement.TYPE_DOC);
		initBornes(TaRemise.TYPE_DOC);
		
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
	private class HandlerListeDocument extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				ChangementDePageEvent change = new ChangementDePageEvent(this,ChangementDePageEvent.AUTRE,3,1);
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
	
	
	public List<IDocumentTiers> getListeVerif() {
		return listeVerif;
	}

	@Override
	public void DeclencheInitBorneController(DeclencheInitBorneControllerEvent evt) {
		try {
			sourceDeclencheCommandeController = evt.getSource();
			if(evt.getOption().equals(TypeDoc.TYPE_TOUS)){
				initProposalAdapterTousReglements();
			}
			if(evt.getOption().equals(PreferenceConstants.ACOMPTES)){
				initProposalAdapterAcompte();
				initBornes(TaAcompte.TYPE_DOC);
			}
			if(evt.getOption().equals(PreferenceConstants.REGLEMENT_SIMPLE)){
				initProposalAdapterReglement();
				initBornes(TaReglement.TYPE_DOC);
			}
			if(evt.getOption().equals(PreferenceConstants.REMISE)){
				initProposalAdapterRemises();
				initBornes(TaRemise.TYPE_DOC);
			}
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

package fr.legrain.exportation.controllers;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.preference.IPersistentPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;

import fr.legrain.document.divers.TypeDoc;
import fr.legrain.documents.dao.TaAcompteDAO;
import fr.legrain.documents.dao.TaAvoirDAO;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaReglementDAO;
import fr.legrain.documents.dao.TaRemiseDAO;
import fr.legrain.exportation.ExportationPlugin;
import fr.legrain.exportation.divers.DeclencheInitBorneControllerEvent;
import fr.legrain.exportation.divers.IDeclencheInitBorneControllerListener;
import fr.legrain.exportation.divers.OptionExportation;
import fr.legrain.exportation.divers.ParamExportationFacture;
import fr.legrain.exportation.ecrans.PaExportationFactureOption;
import fr.legrain.exportation.preferences.PreferenceConstants;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDePageEvent;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;


public class PaExportationFactureControllerOption extends JPABaseControllerSWTStandard implements
RetourEcranListener {
	
	static Logger logger = Logger.getLogger(PaExportationFactureControllerOption.class.getName());		
	private PaExportationFactureOption vue = null; // vue	
	//private SWT_IB_TA_FACTURE ibTaTable = new SWT_IB_TA_FACTURE(new SWTFacture(null));
	private TaFactureDAO daoFacture = null;//new TaFactureDAO();
	private TaAvoirDAO daoAvoir = null;//new TaAvoirDAO();
	private TaAcompteDAO daoAcompte = null;
	private TaReglementDAO daoReglement = null;
	private TaRemiseDAO daoRemise = null;
	
	private Object ecranAppelant = null;
	
	public static final String C_COMMAND_DOCUMENT_SUIVANT_REFERENCE_ID = "fr.legrain.Document.suivantReference";
	protected HandlerSuivantReference handlerSuivantReference = new HandlerSuivantReference();
	
	public static final String C_COMMAND_DOCUMENT_SUIVANT_DATES_ID = "fr.legrain.Document.suivantDates";
	protected HandlerSuivantDates handlerSuivantDates = new HandlerSuivantDates();
	
	public PaExportationFactureControllerOption(PaExportationFactureOption vue) {
		this(vue,null);
	}
	
	public PaExportationFactureControllerOption(PaExportationFactureOption vue,EntityManager em) {
		initCursor(SWT.CURSOR_WAIT);
		if(em!=null) {
			setEm(em);
		}

		daoFacture = new TaFactureDAO(getEm());
		daoAvoir = new TaAvoirDAO(getEm());
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
	
	
	private void initController() {
		try {
			setDaoStandard(daoFacture);
			
			vue.getCbTous().setSelection(ExportationPlugin.getDefault().
					getPreferenceStoreProject().getBoolean(PreferenceConstants.ACOMPTES)&&
					ExportationPlugin.getDefault().
					getPreferenceStoreProject().getBoolean(PreferenceConstants.REGLEMENT_SIMPLE)&&
					ExportationPlugin.getDefault().
					getPreferenceStoreProject().getBoolean(PreferenceConstants.REMISE));
			vue.getCbAcompte().setSelection(ExportationPlugin.getDefault().
					getPreferenceStoreProject().getBoolean(PreferenceConstants.ACOMPTES));
			vue.getCbReglementSimple().setSelection(ExportationPlugin.getDefault().
					getPreferenceStoreProject().getBoolean(PreferenceConstants.REGLEMENT_SIMPLE));
			vue.getCbRemise().setSelection(ExportationPlugin.getDefault().
					getPreferenceStoreProject().getBoolean(PreferenceConstants.REMISE));
			vue.getCbReglementLies().setSelection(ExportationPlugin.getDefault().
					getPreferenceStoreProject().getBoolean(PreferenceConstants.REGLEMENTS_LIES));
			vue.getCbTransDocLies().setSelection(ExportationPlugin.getDefault().
					getPreferenceStoreProject().getBoolean(PreferenceConstants.DOCUMENTS_LIES));
			vue.getCbTransPointage().setSelection(ExportationPlugin.getDefault().
					getPreferenceStoreProject().getBoolean(PreferenceConstants.POINTAGES));

			vue.getCbReglementLies().setVisible(false);
			vue.getCbTransDocLies().setVisible(false);
			vue.getCbTransPointage().setVisible(false);
			
			vue.getGroupDocumentLie().setVisible(false);
			vue.getGroupPointage().setVisible(false);
			vue.getGroupReglementLie().setVisible(false);
			
			
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
	}
	
	public PaExportationFactureOption getVue() {return vue;}
	
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

		//trouve = daoStandard.selectAll().size()>0; // trop lourd
		trouve = daoStandard.selectCount()>0;//trouver une autre solution, en attendant
		//j'ai mis �a !!!

		switch (daoFacture.getModeObjet().getMode()) {
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

		initFocusSWT(daoFacture,mapInitFocus);
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
			
		
		listeComposantFocusable.add(vue.getCbTous());
		listeComposantFocusable.add(vue.getCbAcompte());
		listeComposantFocusable.add(vue.getCbReglementSimple());
		listeComposantFocusable.add(vue.getCbRemise());
		listeComposantFocusable.add(vue.getCbReglementLies());
		listeComposantFocusable.add(vue.getCbTransDocLies());
		listeComposantFocusable.add(vue.getCbTransPointage());

		if(mapInitFocus == null) 
			mapInitFocus = new LinkedHashMap();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION,vue.getCbTous());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION,vue.getCbTous());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION,vue.getCbTous());
//		ExportationPlugin.setPreferenceStore(ExportationPlugin.getDefault().getPreferenceStoreProject()); 
		vue.getCbTous().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
//				ExportationPlugin.getDefault().getPreferenceStoreProject().setValue(
//						PreferenceConstants.TYPE_REGLEMENT, vue.getCbTous().getSelection());
				ExportationPlugin.getDefault().getPreferenceStoreProject().setValue(
						PreferenceConstants.ACOMPTES, vue.getCbTous().getSelection());
				ExportationPlugin.getDefault().getPreferenceStoreProject().setValue(
						PreferenceConstants.REGLEMENT_SIMPLE, vue.getCbTous().getSelection());
				ExportationPlugin.getDefault().getPreferenceStoreProject().setValue(
						PreferenceConstants.REMISE, vue.getCbTous().getSelection());
				initEcranSurPreferences();
				
			}
			
		});
		vue.getCbAcompte().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ExportationPlugin.getDefault().getPreferenceStoreProject().setValue(
						PreferenceConstants.ACOMPTES, vue.getCbAcompte().getSelection());
//				if(vue.getCbAcompte().getSelection()==false){
//					ExportationPlugin.getDefault().getPreferenceStoreProject().setValue(
//							PreferenceConstants.TYPE_REGLEMENT, false);
//				}
				initEcranSurPreferences();
			}			
		});
		vue.getCbReglementLies().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ExportationPlugin.getDefault().getPreferenceStoreProject().setValue(
						PreferenceConstants.REGLEMENTS_LIES, vue.getCbReglementLies().getSelection());
				initEcranSurPreferences();
			}			
		});
		vue.getCbReglementSimple().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ExportationPlugin.getDefault().getPreferenceStoreProject().setValue(
						PreferenceConstants.REGLEMENT_SIMPLE, vue.getCbReglementSimple().getSelection());
//				if(vue.getCbReglementSimple().getSelection()==false){
//					ExportationPlugin.getDefault().getPreferenceStoreProject().setValue(
//							PreferenceConstants.TYPE_REGLEMENT, false);
//				}
				initEcranSurPreferences();
			}			
		});
		vue.getCbRemise().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ExportationPlugin.getDefault().getPreferenceStoreProject().setValue(
						PreferenceConstants.REMISE, vue.getCbRemise().getSelection());
//				if(vue.getCbRemise().getSelection()==false){
//					ExportationPlugin.getDefault().getPreferenceStoreProject().setValue(
//							PreferenceConstants.TYPE_REGLEMENT, false);
//				}
				initEcranSurPreferences();
			}			
		});
		vue.getCbTransDocLies().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ExportationPlugin.getDefault().getPreferenceStoreProject().setValue(
						PreferenceConstants.DOCUMENTS_LIES, vue.getCbTransDocLies().getSelection());
				initEcranSurPreferences();
			}			
		});
		vue.getCbTransPointage().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ExportationPlugin.getDefault().getPreferenceStoreProject().setValue(
						PreferenceConstants.POINTAGES, vue.getCbTransPointage().getSelection());
				initEcranSurPreferences();
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
		
		initFocusCommand(String.valueOf(this.hashCode()),
				listeComposantFocusable, mapCommand);
//		initCommands();
		
		if (mapActions == null)
			mapActions = new LinkedHashMap();
		
//		mapActions.put(vue.getBtnExporter(), C_COMMAND_GLOBAL_IMPRIMER_ID);
//		mapActions.put(vue.getBtnDates(), C_COMMAND_DOCUMENT_SUIVANT_DATES_ID);
//		mapActions.put(vue.getBtnReference(), C_COMMAND_DOCUMENT_SUIVANT_REFERENCE_ID);
//		mapActions.put(vue.getPaBtn1().getBtnFermer(), C_COMMAND_GLOBAL_FERMER_ID);
//		
		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID };
		mapActions.put(null, tabActionsAutres);
	}
	
	public PaExportationFactureControllerOption getThis(){
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
		initEcranSurPreferences();
	}
	
	public void initEcranSurPreferences(String type){
		boolean enabled=true;
		try {
			((IPersistentPreferenceStore)ExportationPlugin.getDefault().getPreferenceStoreProject()).save();
			String option =TypeDoc.TYPE_TOUS;
			if(type.equals(PreferenceConstants.ACOMPTES)){
				vue.getCbAcompte().setSelection(ExportationPlugin.getDefault().getPreferenceStoreProject()
						.getBoolean(PreferenceConstants.ACOMPTES));
				option=PreferenceConstants.ACOMPTES;
			}
			if(type.equals(PreferenceConstants.REGLEMENTS_LIES)){
				vue.getCbReglementLies().setSelection(ExportationPlugin.getDefault().getPreferenceStoreProject()
						.getBoolean(PreferenceConstants.REGLEMENTS_LIES));
				option=PreferenceConstants.REGLEMENTS_LIES;
			}
			if(type.equals(PreferenceConstants.REGLEMENT_SIMPLE)){
				vue.getCbReglementSimple().setSelection(ExportationPlugin.getDefault().getPreferenceStoreProject()
						.getBoolean(PreferenceConstants.REGLEMENT_SIMPLE));
				option=PreferenceConstants.REGLEMENT_SIMPLE;
			}
			if(type.equals(PreferenceConstants.REMISE)){
				vue.getCbRemise().setSelection(ExportationPlugin.getDefault().getPreferenceStoreProject()
						.getBoolean(PreferenceConstants.REMISE));
				option=PreferenceConstants.REMISE;
			}
			Boolean old=vue.getCbTous().getSelection();
			vue.getCbTous().setSelection(ExportationPlugin.getDefault().
					getPreferenceStoreProject().getBoolean(PreferenceConstants.ACOMPTES)&&
					ExportationPlugin.getDefault().
					getPreferenceStoreProject().getBoolean(PreferenceConstants.REGLEMENT_SIMPLE)&&
					ExportationPlugin.getDefault().
					getPreferenceStoreProject().getBoolean(PreferenceConstants.REMISE));
			if(old.compareTo(vue.getCbTous().getSelection())!=0)
				option=TypeDoc.TYPE_TOUS;
			if(type.equals(PreferenceConstants.DOCUMENTS_LIES)){
				vue.getCbTransDocLies().setSelection(ExportationPlugin.getDefault().getPreferenceStoreProject()
						.getBoolean(PreferenceConstants.DOCUMENTS_LIES));
				option=PreferenceConstants.DOCUMENTS_LIES;
			}
			if(type.equals(PreferenceConstants.POINTAGES)){
				vue.getCbTransPointage().setSelection(ExportationPlugin.getDefault().getPreferenceStoreProject()
						.getBoolean(PreferenceConstants.POINTAGES));
				option=PreferenceConstants.POINTAGES;
			}
			

			
			Boolean old2=vue.getCbReglementLies().getSelection()&&
			vue.getCbTransDocLies().getSelection()&&vue.getCbTransPointage().getSelection();
			enabled=vue.getCbAcompte().getSelection()||vue.getCbReglementSimple().getSelection()
			||vue.getCbRemise().getSelection();
			vue.getCbReglementLies().setEnabled(enabled);
			vue.getCbTransDocLies().setEnabled(enabled);
			vue.getCbTransPointage().setEnabled(enabled);
			if(old2.compareTo(vue.getCbReglementLies().getSelection()&&
			vue.getCbTransDocLies().getSelection()&&vue.getCbTransPointage().getSelection())!=0)
				option=TypeDoc.TYPE_TOUS;
			//déclenche la mise à jour de l'écran parent
			DeclencheInitBorneControllerEvent e = new DeclencheInitBorneControllerEvent(this,option);
			fireDeclencheInitBorneController(e);
		} catch (IOException e1) {
			logger.error("", e1);
		}
	}
	public void initEcranSurPreferences(){
		boolean enabled=true;
		try {
			((IPersistentPreferenceStore)ExportationPlugin.getDefault().getPreferenceStoreProject()).save();

		vue.getCbAcompte().setSelection(ExportationPlugin.getDefault().getPreferenceStoreProject()
				.getBoolean(PreferenceConstants.ACOMPTES));
		vue.getCbReglementLies().setSelection(ExportationPlugin.getDefault().getPreferenceStoreProject()
				.getBoolean(PreferenceConstants.REGLEMENTS_LIES));
		vue.getCbReglementSimple().setSelection(ExportationPlugin.getDefault().getPreferenceStoreProject()
				.getBoolean(PreferenceConstants.REGLEMENT_SIMPLE));
		vue.getCbRemise().setSelection(ExportationPlugin.getDefault().getPreferenceStoreProject()
				.getBoolean(PreferenceConstants.REMISE));
		vue.getCbTous().setSelection(ExportationPlugin.getDefault().
				getPreferenceStoreProject().getBoolean(PreferenceConstants.ACOMPTES)&&
				ExportationPlugin.getDefault().
				getPreferenceStoreProject().getBoolean(PreferenceConstants.REGLEMENT_SIMPLE)&&
				ExportationPlugin.getDefault().
				getPreferenceStoreProject().getBoolean(PreferenceConstants.REMISE));
		vue.getCbTransDocLies().setSelection(ExportationPlugin.getDefault().getPreferenceStoreProject()
				.getBoolean(PreferenceConstants.DOCUMENTS_LIES));
		vue.getCbTransPointage().setSelection(ExportationPlugin.getDefault().getPreferenceStoreProject()
				.getBoolean(PreferenceConstants.POINTAGES));
		enabled=vue.getCbAcompte().getSelection()||vue.getCbReglementSimple().getSelection()
		||vue.getCbRemise().getSelection();
		vue.getCbReglementLies().setEnabled(enabled);
		vue.getCbTransDocLies().setEnabled(enabled);
		vue.getCbTransPointage().setEnabled(enabled);
		
		//déclenche la mise à jour de l'écran parent
		DeclencheInitBorneControllerEvent e = new DeclencheInitBorneControllerEvent(this,TypeDoc.TYPE_TOUS);
		fireDeclencheInitBorneController(e);
		} catch (IOException e1) {
			logger.error("", e1);
		}
	}

	public void initPreferences(OptionExportation option){
//		ExportationPlugin.getDefault().getPreferenceStoreProject().setValue(
//				PreferenceConstants.TYPE_REGLEMENT, option.getTousReglements());
		ExportationPlugin.getDefault().getPreferenceStoreProject().setValue(
				PreferenceConstants.ACOMPTES, option.getAcomptes());
		ExportationPlugin.getDefault().getPreferenceStoreProject().setValue(
				PreferenceConstants.REGLEMENT_SIMPLE, option.getReglementSimple());
		ExportationPlugin.getDefault().getPreferenceStoreProject().setValue(
				PreferenceConstants.REMISE, option.getRemise());
		ExportationPlugin.getDefault().getPreferenceStoreProject().setValue(
				PreferenceConstants.DOCUMENTS_LIES, option.getDocumentLies());
		ExportationPlugin.getDefault().getPreferenceStoreProject().setValue(
				PreferenceConstants.REGLEMENTS_LIES, option.getReglementLies());
		ExportationPlugin.getDefault().getPreferenceStoreProject().setValue(
				PreferenceConstants.POINTAGES, option.getPointages());		
	}
	@Override
	protected void initMapComposantDecoratedField() {
		// TODO Raccord de méthode auto-généré
		
	}

	@Override
	protected void sortieChamps() {
		// TODO Raccord de méthode auto-généré
		
	}

	public void griserTout(boolean griser){
		vue.getCbAcompte().setEnabled(griser);
		vue.getCbReglementLies().setEnabled(griser);
		vue.getCbReglementSimple().setEnabled(griser);
		vue.getCbRemise().setEnabled(griser);
		vue.getCbTous().setEnabled(griser);
		vue.getCbTransDocLies().setEnabled(griser);
		vue.getCbTransPointage().setEnabled(griser);
		
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
					e = new DeclencheInitBorneControllerEvent(this,e.getOption());
				( (IDeclencheInitBorneControllerListener) listeners[i + 1]).DeclencheInitBorneController(e);
			}
		}
	}
}

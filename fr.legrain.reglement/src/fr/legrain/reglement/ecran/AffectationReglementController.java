package fr.legrain.reglement.ecran;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TooManyListenersException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.swing.event.EventListenerList;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;

import fr.legrain.document.divers.ModelRReglement;
import fr.legrain.document.divers.ModelReglement;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaRReglement;
import fr.legrain.documents.dao.TaRReglementDAO;
import fr.legrain.documents.dao.TaReglement;
import fr.legrain.documents.dao.TaReglementDAO;
import fr.legrain.documents.dao.TaTPaiement;
import fr.legrain.documents.dao.TaTPaiementDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Articles.SWTArticle;
import fr.legrain.gestCom.Module_Document.ChangeModeEvent;
import fr.legrain.gestCom.Module_Document.ChangeModeListener;
import fr.legrain.gestCom.Module_Document.IHMEtat;
import fr.legrain.gestCom.Module_Document.IHMReglement;
import fr.legrain.gestCom.gestComBd.gestComBdPlugin;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.swt.LgrDatabindingUtil;
import fr.legrain.gestCom.librairiesEcran.workbench.AnnuleToutEvent;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.UpdateMasterEntityEvent;
import fr.legrain.lib.gui.UpdateMasterEntityListener;
import fr.legrain.libMessageLGR.LgrMess;
import fr.legrain.reglement.Activator;
import fr.legrain.tiers.dao.TaCompteBanque;
import fr.legrain.tiers.dao.TaCompteBanqueDAO;
import fr.legrain.tiers.dao.TaTiersDAO;

public class AffectationReglementController extends JPABaseControllerSWTStandard {
	
	static Logger logger = Logger.getLogger(AffectationReglementController.class.getName());
	
	protected EventListenerList listenerList = new EventListenerList();
	
	private Class objetIHM = IHMReglement.class;
//	private Object selectedObject = null;
	
	private TaFacture masterEntity = null;
	private TaFactureDAO masterDAO = null;
	
	private TaReglement taReglement = null;
	private TaRReglement taRReglement = null;
	private TaReglementDAO dao = null;
	private TaRReglementDAO daoRReglementDAO = null;
	
	//private List<ModelObject> modele = null;
//	private ModelGeneralObjet<IHMReglement,TaReglementDAO,TaReglement> modele = null;
	private ModelReglement modele = null;


	private SWTPaReglementMultipleController masterController = null;
	
	private PaAffectationReglement vue = null;
	
	private LgrDatabindingUtil lgrDatabindingUtil = new LgrDatabindingUtil(this);
	private IObservableValue selectedReglement;
	
	private IHMReglement ihmReglement;
	private IHMReglement ihmOldReglement;
	
	private LgrDozerMapper<IHMReglement,TaRReglement> mapperUIToModel  = new LgrDozerMapper<IHMReglement,TaRReglement>();
	private LgrDozerMapper<TaRReglement,IHMReglement> mapperModelToUI  = new LgrDozerMapper<TaRReglement,IHMReglement>();
	
//	public static final String C_COMMAND_DOCUMENT_AFFECTER_REGLEMENT_ID = "fr.legrain.document.reglement.affecter";
//	protected HandlerAffecterReglement handlerAffecterReglement = new HandlerAffecterReglement();
	
	public AffectationReglementController(PaAffectationReglement vue, EntityManager em) {
		if (em != null) {
			setEm(em);
		}
		try {
			setVue(vue);
			this.vue = vue;
			dao = new TaReglementDAO(getEm());
			daoRReglementDAO = new TaRReglementDAO(getEm());

			this.vue.getShell().addShellListener(this);
			this.vue.getShell().addTraverseListener(new Traverse());

			initController();

		} catch (Exception e) {
			logger.debug("", e);
		}
	}
	
	private void initController() {
		try {
			setDaoStandard(dao);
//			setTableViewerStandard(lgrDatabindingUtil.getMapLgrTableViewer().get(vue.getTableReglementNonAffecte()));
//			setDbcStandard(dbcReglement);

			initMapComposantChamps();
			initMapComposantDecoratedField();

			listeComponentFocusableSWT(listeComposantFocusable);
			initDeplacementSaisie(listeComposantFocusable);
			initFocusOrder();
			initActions();

			branchementBouton();

			bind();
			setTableViewerStandard(lgrDatabindingUtil.getMapLgrTableViewer().get(vue.getTableReglementNonAffecte()));

		} catch (Exception e) {
//			if (e.getMessage() != null)
//				vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaArticlesController", e);
		}
	}
	
	protected void initImageBouton() {
		super.initImageBouton();
//		String C_IMAGE_VALIDER = "/icons/accept.png";
		vue.getBtnAffecter().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ACCEPTER));
		vue.getBtnAnnuler().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ANNULER));
		vue.layout(true);
	}
	
	public void bind(){
		
//		dao = new TaReglementDAO();
		modele = new ModelReglement();
		
		selectedReglement = lgrDatabindingUtil.bindTable(
				vue.getTableReglementNonAffecte(),modele.remplirListeReglementsNonTotalementRegles(null,null,null,getEm()),objetIHM,
				new String[] {"Code règlement","Date règlement","Date encaissement","Montant","Affectation","Reste à affecter"},
				new String[] {"100","250","100","150","150","150","150"},
				new String[] {"codeDocument","dateDocument","dateLivDocument","netTtcCalc","affectation","resteAAffecter"}
		,1);
//		setTableViewerStandard(lgrDatabindingUtil.getMapLgrTableViewer().get(vue.getTableReglementNonAffecte()));
//		getTableViewerStandard().tri(objetIHM, getTableViewerStandard().getListeChamp(), getTableViewerStandard().getTitresColonnes());

		lgrDatabindingUtil.bindingFormMaitreDetail(mapComposantChamps,new DataBindingContext(),
				SWTObservables.getRealm(vue.getParent().getDisplay()),selectedReglement,objetIHM);
		
		initEtatBouton();
	}

	@Override
	protected void actAide() throws Exception {}

	@Override
	protected void actAide(String message) throws Exception {}

	@Override
	protected void actAnnuler() throws Exception {
		logger.debug("Annuler");
		switch (dao.getModeObjet().getMode()) {
		case C_MO_INSERTION:
			dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
			//actRefresh(masterEntity,masterDAO, null);	
			break;
		case C_MO_EDITION:
			//remetTousLesChampsApresAnnulationSWT(dbcReglement);
			if(ihmOldReglement!=null) mapperUIToModel.map(ihmOldReglement, taRReglement);
			dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
			//actRefresh(masterEntity,masterDAO,taRReglement);

			break;
		default:
			break;
		}
	}

	@Override
	protected void actEnregistrer() throws Exception {
		//logger.debug("Enregistrer");
//		taRReglement = new TaRReglement();
		taRReglement.setTaFacture(masterEntity);
		taRReglement.setTaReglement(taReglement);
//		taRReglement.setAffectation(new BigDecimal(0));
		taReglement.getTaRReglements().add(taRReglement);
		
		
		EntityTransaction transaction = dao.getEntityManager().getTransaction();
		try {
			if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
					|| (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {

				//mapperUIToModel.map((IHMReglement) selectedReglement.getValue(),taRReglement);

				dao.begin(transaction);
				if((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)){	
					taReglement=dao.enregistrerMerge(taReglement);
				}
				else taReglement=dao.enregistrerMerge(taReglement);
				
				dao.commit(transaction);
//				dao.getEntityManager().clear();
				dao.getEntityManager().refresh(masterEntity);
				fireUpdateMasterEntity(new UpdateMasterEntityEvent(this));
				actRefresh(masterEntity,taReglement); //deja present
				transaction = null;
			}

		}catch (Exception e) {
			logger.error("",e);
			throw new Exception();
		}finally {
			if(transaction!=null && transaction.isActive()) {
				transaction.rollback();
			}
			initEtatBouton();
		}
	}

	@Override
	protected void actFermer() throws Exception {}

	@Override
	protected void actImprimer() throws Exception {}

	@Override
	protected void actInserer() throws Exception {
		dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_INSERTION);
//		taRReglement = new TaRReglement();
	}

	@Override
	protected void actModifier() throws Exception {
//		dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_EDITION);
		if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
				setSwtOldReglement();
//				taRReglement=null;
				taReglement = dao.findById(((IHMReglement) selectedReglement.getValue()).getId());
//				for (TaRReglement taRReglementTmp : taReglement.getTaRReglements()) {
//					if(taRReglementTmp.getTaFacture().equals(masterEntity))
//					taRReglement=taRReglementTmp;
//				}
//				if(taRReglement==null)
			taRReglement = new TaRReglement();
			
			dao.modifier(taReglement);
			initEtatBouton();
		}
	}
	
	public void setSwtOldReglement() {

		if (selectedReglement.getValue() != null)
			this.ihmOldReglement = IHMReglement.copy((IHMReglement) selectedReglement.getValue());
		else {
			if(lgrDatabindingUtil.getMapLgrTableViewer().get(vue.getTableReglementNonAffecte()).selectionGrille(0)){
				this.ihmOldReglement = IHMReglement.copy((IHMReglement) selectedReglement.getValue());
				lgrDatabindingUtil.getMapLgrTableViewer().get(vue.getTableReglementNonAffecte()).setSelection(
						new StructuredSelection((IHMReglement) selectedReglement.getValue()), true
						);
			}}
	}

	@Override
	protected void actRefresh() throws Exception {
		actRefresh(masterEntity,null);
	}

	public void actRefresh(TaFacture taDocument,TaReglement taReglement) throws Exception {
//		setMasterDAO(masterDAO);
		if (lgrDatabindingUtil.getMapLgrTableViewer().get(vue.getTableReglementNonAffecte())==null)
			bind();
		this.taReglement=taReglement;
		masterEntity=taDocument;
		WritableList writableListReglement=null;
			writableListReglement = new WritableList(modele.
					remplirListeReglementsNonTotalementRegles(masterEntity,null,null, dao.getEntityManager()), IHMReglement.class);
		
			lgrDatabindingUtil.getMapLgrTableViewer().get(vue.getTableReglementNonAffecte()).setInput(writableListReglement);
		if(taReglement==null || (taReglement.getEtatDeSuppression()&IHMEtat.multiple)!=0){
			lgrDatabindingUtil.getMapLgrTableViewer().get(vue.getTableReglementNonAffecte()).selectionGrille(0);
		} else {
			Object valeurRecherchee =rechercheReglement(Const.C_CODE_DOCUMENT, 
					taReglement.getCodeDocument());
			if(valeurRecherchee!=null)
				lgrDatabindingUtil.getMapLgrTableViewer().get(vue.getTableReglementNonAffecte()).setSelection(new StructuredSelection());
			else lgrDatabindingUtil.getMapLgrTableViewer().get(vue.getTableReglementNonAffecte()).selectionGrille(0);
		}
		initEtatBouton();
	}

	
	public Object rechercheReglement(String propertyName, Object value) {
		boolean trouve = false;
		int i = 0;
		while(!trouve && i<modele.getListeObjet().size()){
			try {
				if(PropertyUtils.getProperty(modele.getListeObjet().get(i), propertyName).equals(value)) {
					trouve = true;
				} else {
					i++;
				}
			} catch (IllegalAccessException e) {
				logger.error("",e);
			} catch (InvocationTargetException e) {
				logger.error("",e);
			} catch (NoSuchMethodException e) {
				logger.error("",e);
			}
		}

		if(trouve)
			return modele.getListeObjet().get(i);
		else 
			return null;

	}
	@Override
	protected void actSupprimer() throws Exception {}

	@Override
	protected void initActions() {
		mapCommand = new HashMap<String, IHandler>();
		
//		mapCommand.put(C_COMMAND_DOCUMENT_AFFECTER_REGLEMENT_ID,handlerAffecterReglement);
		mapCommand.put(C_COMMAND_GLOBAL_ENREGISTRER_ID,handlerEnregistrer);
		mapCommand.put(C_COMMAND_GLOBAL_ANNULER_ID,handlerAnnuler);

		initFocusCommand(String.valueOf(this.hashCode()),listeComposantFocusable, mapCommand);

		if (mapActions == null)
			mapActions = new LinkedHashMap<Button, Object>();
		
//		mapActions.put(vue.getBtnAffecter(),C_COMMAND_DOCUMENT_AFFECTER_REGLEMENT_ID);
		mapActions.put(vue.getBtnAffecter(),C_COMMAND_GLOBAL_ENREGISTRER_ID);
		mapActions.put(vue.getBtnAnnuler(),C_COMMAND_GLOBAL_ANNULER_ID);
		
		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID };
		mapActions.put(null, tabActionsAutres);

		Menu popupMenuFormulaire = new Menu(vue.getShell(), SWT.POP_UP);
		Menu popupMenuGrille = new Menu(vue.getShell(), SWT.POP_UP);
		Menu[] tabPopups = new Menu[] { popupMenuFormulaire, popupMenuGrille };
		this.initPopupAndButtons(mapActions, tabPopups);
		vue.setMenu(popupMenuFormulaire);
	}

	@Override
	protected void initComposantsVue() throws ExceptLgr {}

	@Override
	public void initEtatComposant() {}

	@Override
	protected void initMapComposantChamps() {
		if (mapComposantChamps == null)
			mapComposantChamps = new LinkedHashMap<Control, String>();
		
		if (listeComposantFocusable == null)
			listeComposantFocusable = new ArrayList<Control>();
		
		mapComposantChamps.put(vue.getTfMontantAffectation(),Const.C_MONTANT_AFFECTE);
		
		listeComposantFocusable.add(vue.getTableReglementNonAffecte());
		listeComposantFocusable.add(vue.getTfMontantAffectation());
		listeComposantFocusable.add(vue.getBtnAffecter());
		listeComposantFocusable.add(vue.getBtnAnnuler());
		
		if (mapInitFocus == null)
			mapInitFocus = new LinkedHashMap<ModeObjet.EnumModeObjet, Control>();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION, vue.getTfMontantAffectation());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue.getTfMontantAffectation());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION, vue.getTfMontantAffectation());
		
		activeModifytListener();
	}

	@Override
	protected void initMapComposantDecoratedField() {}

	@Override
	public boolean onClose() throws ExceptLgr {
		return true;
	}

	@Override
	public ResultAffiche configPanel(ParamAffiche param) {
		return null;
	}

	@Override
	public Composite getVue() {
		return vue;
	}

	@Override
	protected void sortieChamps() {}
	
//	private class HandlerAffecterReglement extends LgrAbstractHandler {
//
//		public Object execute(ExecutionEvent event) throws ExecutionException {
//			try {
//				logger.debug("Affectation");
//				initEtatBouton();
//			} catch (Exception e) {
//				logger.error("", e);
//			}
//			return event;
//		}
//	}
	
	public TaFacture getMasterEntity() {
		return masterEntity;
	}

	public void setMasterEntity(TaFacture masterEntity) {
		this.masterEntity = masterEntity;
		try {
			actRefresh(masterEntity,taReglement);
		} catch (Exception e) {
			logger.error("",e);
		}
	}

	public TaFactureDAO getMasterDAO() {
		return masterDAO;
	}

	public void setMasterDAO(TaFactureDAO masterDAO) {
		this.masterDAO = masterDAO;
		if(masterDAO!=null && !dao.getEntityManager().equals(masterDAO.getEntityManager())){
			dao=new TaReglementDAO(masterDAO.getEntityManager());
		}
	}
	
	@Override
	protected void initEtatBouton(boolean initFocus) {
		initEtatBouton();
	}

	@Override
	protected void initEtatBouton() {
		//super.initEtatBouton();
		switch (dao.getModeObjet().getMode()) {
		case C_MO_INSERTION:
//			enableActionAndHandler(C_COMMAND_DOCUMENT_AFFECTER_REGLEMENT_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,true);
//			enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID,false);
//			if (vue.getTableReglementNonAffecte()!=null)vue.getTableReglementNonAffecte().setEnabled(false);
			break;
		case C_MO_EDITION:
//			enableActionAndHandler(C_COMMAND_DOCUMENT_AFFECTER_REGLEMENT_ID,true);	
			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,true);
//			enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID,true);
//			if (vue.getTableReglementNonAffecte()!=null)vue.getTableReglementNonAffecte().setEnabled(false);
			break;
		case C_MO_CONSULTATION:
//			enableActionAndHandler(C_COMMAND_DOCUMENT_AFFECTER_REGLEMENT_ID,false);		
			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,false);
//			enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID,true);
//			if (vue.getTableReglementNonAffecte()!=null)vue.getTableReglementNonAffecte().setEnabled(true);
			break;
		default:
			break;
		}
	}
	
	public IStatus validateUIField(String nomChamp, Object value) {
		//String validationContext = "REGLEMENT";
		try {
			IStatus s = null;
//			if(nomChamp.equals(Const.C_CODE_DOCUMENT)){
//				s = new Status(Status.OK, gestComBdPlugin.PLUGIN_ID, "OK");
//			}else if (nomChamp.equals("imprimer")) {
//				s = new Status(Status.OK, gestComBdPlugin.PLUGIN_ID, "OK");
//			} else			
				 if (nomChamp.equals(Const.C_MONTANT_AFFECTE) ) {
					TaRReglement u = new TaRReglement();
					PropertyUtils.setSimpleProperty(u, nomChamp, value);
					s = daoRReglementDAO.validate(u, nomChamp, "R_REGLEMENT");
					if (s.getSeverity() != IStatus.ERROR) {
						PropertyUtils.setSimpleProperty(taRReglement, nomChamp, value);
						taRReglement.setTaReglement(taReglement);
						taRReglement.setTaFacture(masterEntity);
						//si affectation supérieure au reste à regler de la facture
						if(masterEntity.getRegleCompletDocument(taRReglement).add(taRReglement.getAffectation()).
								compareTo(masterEntity.getNetAPayer())>0){
							taRReglement.setAffectation(masterEntity.getNetAPayer().subtract(masterEntity.getRegleCompletDocument(taRReglement)));
						}
						//si affectation supérieure au reste à regler du règlement
						if(taRReglement.getTaReglement().calculAffectationTotale(taRReglement).add(taRReglement.getAffectation()).
								compareTo(taRReglement.getTaReglement().getNetTtcCalc())>0){
							taRReglement.setAffectation(taRReglement.getTaReglement().getNetTtcCalc().subtract(taRReglement.getTaReglement().calculAffectationTotale(taRReglement)));
						}						
						if(taRReglement.getAffectation().compareTo(taReglement.getNetTtcCalc())>0)
							taRReglement.setAffectation(taReglement.getNetTtcCalc());
						if(taRReglement.getAffectation().compareTo(taReglement.getResteAAffecter())>0)
							taRReglement.setAffectation(taReglement.getResteAAffecter());

						((IHMReglement)selectedReglement.getValue()).setAffectation(taRReglement.getAffectation());
						masterEntity.calculRegleDocumentComplet();
					}	
				}

			if (s.getSeverity() != IStatus.ERROR) {

			}
			//new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
			return s;
		} catch (IllegalAccessException e) {
			logger.error("", e);
		} catch (InvocationTargetException e) {
			logger.error("", e);
		} catch (NoSuchMethodException e) {
			logger.error("", e);
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}
	
	
	public ModelReglement getModele() {
		return modele;
	}
	
	
	/*
	 * *************************************************************************************************************
	 */
	public void addUpdateMasterEntityListener(UpdateMasterEntityListener l) {
		listenerList.add(UpdateMasterEntityListener.class, l);
	}
	
	public void removeUpdateMasterEntityListener(UpdateMasterEntityListener l) {
		listenerList.remove(UpdateMasterEntityListener.class, l);
	}
	
	protected void fireUpdateMasterEntity(UpdateMasterEntityEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == UpdateMasterEntityListener.class) {
				if (e == null)
					e = new UpdateMasterEntityEvent(this);
				( (UpdateMasterEntityListener) listeners[i + 1]).updateMasterEntity(e);
			}
		}
	}
	/*
	 * *************************************************************************************************************
	 */
}

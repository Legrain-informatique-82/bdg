package fr.legrain.remisecheque.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import fr.legrain.document.divers.TypeDoc;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaReglementDAO;
import fr.legrain.documents.dao.TaRemise;
import fr.legrain.documents.dao.TaRemiseDAO;
import fr.legrain.documents.dao.TaTPaiement;
import fr.legrain.documents.dao.TaTPaiementDAO;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Document.IHMEnteteFacture;
import fr.legrain.gestCom.Module_Document.IHMRemise;
import fr.legrain.gestCom.Module_Tiers.SWTTiers;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrDatabindingUtil;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.gestCom.librairiesEcran.swt.LibDateTime;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideRechercheSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheReglementMultiple;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideRechercheControllerSWT;
import fr.legrain.gestCom.librairiesEcran.workbench.AnnuleToutEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.InfosVerifSaisie;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.data.VerrouInterface;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.lib.gui.aide.PaAideRechercheSWT;
import fr.legrain.lib.gui.aide.PaAideSWT;
import fr.legrain.lib.gui.aide.ParamAfficheAide;
import fr.legrain.libMessageLGR.LgrMess;

import fr.legrain.remisecheque.pluginRemise;
import fr.legrain.remisecheque.divers.ParamAfficheRemise;
import fr.legrain.remisecheque.ecrans.PaCritereRemonterRemise;
import fr.legrain.remisecheque.ecrans.PaSelectionLigneRemise;
import fr.legrain.remisecheque.ecrans.PaSelectionLigneRemise2;
import fr.legrain.tiers.dao.TaCompteBanque;
import fr.legrain.tiers.dao.TaCompteBanqueDAO;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.ecran.MessagesEcran;

public class PaCritereRemonterRemiseController extends JPABaseControllerSWTStandard 
implements RetourEcranListener{

	static Logger logger = Logger.getLogger(PaCritereRemonterRemiseController.class.getName());	
	private PaCritereRemonterRemise  vue = null;
	private Date dateDeb=null;
	private Date dateFin=null;
//	private TaTiers tiers = null;
	private String typePaiementCourant=null;
	private TaFactureDAO daoFacture = null;
	private TaRemiseDAO dao = null;
	private TaRemise taRemise = null;
	
	TaInfoEntreprise infos =null;
	TaInfoEntrepriseDAO daoInfos=null;
	
	private Object ecranAppelant = null;
	private Realm realm;
	private DataBindingContext dbc;
	private IObservableValue selectedRelance;
	
	private MapChangeListener changeListener = new MapChangeListener();
	private ModelGeneralObjet<IHMRemise,TaRemiseDAO,TaRemise> modelRemise = null;
	private PaSelectionLigneRemiseControlleur paSelectionLigneRelanceControlleur=null;
	
	private LgrDatabindingUtil lgrDatabindingUtil = new LgrDatabindingUtil();
	
	LinkedList<IHMRemise> listeObjet = new LinkedList<IHMRemise>();
	List<TaRemise> listeEntity = null;
	
	public PaCritereRemonterRemiseController(PaCritereRemonterRemise vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao =new TaRemiseDAO(getEm());
		daoFacture=new TaFactureDAO(getEm());
		modelRemise = new ModelGeneralObjet<IHMRemise,TaRemiseDAO,TaRemise>
		(dao,IHMRemise.class);
		setVue(vue);

		vue.getShell().addShellListener(this);

		//Branchement action annuler : empeche la fermeture automatique de la fenetre sur ESC
		vue.getShell().addTraverseListener(new Traverse());

		initController();
		//initSashFormWeight();
		daoInfos=new TaInfoEntrepriseDAO(getEm());
		infos =daoInfos.findInstance();
	}

	public PaCritereRemonterRemiseController(PaCritereRemonterRemise vue) {
		this(vue,null);
	}
	
	private void initController()	{
		try {	
			setGrille(vue.getGrille());
			initSashFormWeight();
			setDaoStandard(dao);
			//			setTableViewerStandard(tableViewer);
						setDbcStandard(dbc);

			initVue();
			
			initMapComposantChamps();
			initVerifySaisie();
			initMapComposantDecoratedField();
			listeComponentFocusableSWT(listeComposantFocusable);
			initFocusOrder();
			initActions();
			initDeplacementSaisie(listeComposantFocusable);

			branchementBouton();

			Menu popupMenuFormulaire = new Menu(vue.getShell(), SWT.POP_UP);
			Menu popupMenuGrille = new Menu(vue.getShell(), SWT.POP_UP);
			Menu[] tabPopups = new Menu[] {
					popupMenuFormulaire, popupMenuGrille };
			this.initPopupAndButtons(mapActions, tabPopups);
			vue.getParent().setMenu(popupMenuFormulaire);
			
			daoInfos=new TaInfoEntrepriseDAO(getEm());
			infos =daoInfos.findInstance();
			LibDateTime.setDate(vue.getTfDateDebutPeriode(),infos.getDatedebInfoEntreprise());
			LibDateTime.setDate(vue.getTfDateFinPeriode(),infos.getDatefinInfoEntreprise());
			
			TaTPaiementDAO daoPaiement =new TaTPaiementDAO();
			List<TaTPaiement> listePaiement =daoPaiement.selectAll();
			if(listePaiement.size()>0)
				vue.getCbListePaiement().add(TypeDoc.TYPE_TOUS);
			for (TaTPaiement taTPaiement : listePaiement) {				
				vue.getCbListePaiement().add(taTPaiement.getCodeTPaiement());
			}
			
			vue.getCbListePaiement().select(0);
			typePaiementCourant=vue.getCbListePaiement().getItem(vue.getCbListePaiement().getSelectionIndex());
			vue.getCbListePaiement().setVisibleItemCount(vue.getCbListePaiement().getItemCount());
			initEtatBouton(true);
			vue.getPaEcran().layout();
			vue.getScrolledComposite().setMinSize(vue.getPaEcran().computeSize(SWT.DEFAULT, SWT.DEFAULT));
//			affectationReglementControllerMini = new AffectationReglementControllerMini(this,paAffectationReglement,this.getEm());
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaTiersController", e);
		}
	}
	
	protected void initVerifySaisie() {
		if (mapInfosVerifSaisie == null)
			mapInfosVerifSaisie = new HashMap<Control, InfosVerifSaisie>();
		
//		mapInfosVerifSaisie.put(vue.getTfTiers(), new InfosVerifSaisie(new TaTiers(),Const.C_CODE_TIERS,null));
//		mapInfosVerifSaisie.put(paFormulaireReglement.getTfCODE_DOCUMENT(), new InfosVerifSaisie(new TaFacture(),Const.C_CODE_DOCUMENT,null));
//		mapInfosVerifSaisie.put(paFormulaireReglement.getTfCODE_REGLEMENT(), new InfosVerifSaisie(new TaReglement(),Const.C_CODE_REGLEMENT,null));
//		mapInfosVerifSaisie.put(paFormulaireReglement.getTfCPT_COMPTABLE(), new InfosVerifSaisie(new TaCompteBanque(),Const.C_CPTCOMPTABLE,null));
//		mapInfosVerifSaisie.put(paFormulaireReglement.getTfLIBELLE_PAIEMENT(), new InfosVerifSaisie(new TaReglement(),Const.C_LIBELLE_PAIEMENT,null));
//		mapInfosVerifSaisie.put(paFormulaireReglement.getTfMONTANT_AFFECTE(), new InfosVerifSaisie(new TaRReglement(),Const.C_MONTANT_AFFECTE,null));
//		mapInfosVerifSaisie.put(paFormulaireReglement.getTfTYPE_PAIEMENT(), new InfosVerifSaisie(new TaTPaiement(),Const.C_CODE_T_PAIEMENT,null));
	
		
		initVerifyListener(mapInfosVerifSaisie, dao);
	}

	private void initVue() {
		
		PaSelectionLigneRemise selectionLigneRelance = new PaSelectionLigneRemise(vue.getExpandBarGroup(), SWT.PUSH,1,
				 SWT.FULL_SELECTION
					| SWT.H_SCROLL
					| SWT.V_SCROLL
					| SWT.BORDER
					| SWT.CHECK);
		selectionLigneRelance.getGrille().setSize(SWT.DEFAULT, 100);
		selectionLigneRelance.getGrille().getLayout();
		selectionLigneRelance.getLayout();
//		PaSelectionLigneRemise selectionLigneRelance = new PaSelectionLigneRemise(vue.getExpandBarGroup(), SWT.PUSH);
		paSelectionLigneRelanceControlleur = new PaSelectionLigneRemiseControlleur(selectionLigneRelance,getEm());
		ParamAfficheRemise paramAfficheRelanceFacture =new ParamAfficheRemise();
		paramAfficheRelanceFacture.setEnregistreDirect(true);
		paSelectionLigneRelanceControlleur.configPanel(new ParamAfficheRemise());
		paSelectionLigneRelanceControlleur.setImpressionUniquement(true);
		paSelectionLigneRelanceControlleur.initImageBouton();
		
		selectionLigneRelance.getBtnImprimer().setText("Imprimer");
		
		addExpandBarItem(vue.getExpandBarGroup(), selectionLigneRelance,
		"Sélection des documents de la remise en cours", pluginRemise.getImageDescriptor(
		"/icons/logo_lgr_16.png").createImage(), SWT.DEFAULT, 400);
		vue.getExpandBarGroup().getItem(0).setExpanded(true);
		
	}

	@Override
	protected void actAide() throws Exception {
		// TODO Auto-generated method stub
		actAide(null);
	}

	@Override
	protected boolean aideDisponible() {
		boolean result = false;
		if (getFocusCourantSWT().equals(vue.getGrille()))
			result = true;
		return result;
	}
	@Override
	protected void actAide(String message) throws Exception {
		if (aideDisponible()) {
//			boolean affichageAideRemplie = DocumentPlugin.getDefault().getPreferenceStore().getBoolean(fr.legrain.document.preferences.PreferenceConstants.TYPE_AFFICHAGE_AIDE);
			boolean affichageAideRemplie = LgrMess.isAfficheAideRemplie();
			setActiveAide(true);
			boolean verrouLocal = VerrouInterface.isVerrouille();
			VerrouInterface.setVerrouille(true);
			try {
				vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
				ParamAfficheAideRechercheSWT paramAfficheAideRecherche = new ParamAfficheAideRechercheSWT();
//				paramAfficheAideRecherche.setDb(getThis().getIbTaTable().getFIBBase());
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
					if (getFocusCourantSWT().equals(vue.getGrille())) {
//						//permet de paramètrer l'affichage remplie ou non de l'aide
//
//						PaTiersSWT paTiersSWT = new PaTiersSWT(s2, SWT.NULL);
//						SWTPaTiersController swtPaTiersController = new SWTPaTiersController(paTiersSWT);
//
//						editorCreationId = EditorTiers.ID;
//						editorInputCreation = new EditorInputTiers();
//
//						ParamAfficheTiers paramAfficheTiers = new ParamAfficheTiers();
//						paramAfficheAideRecherche.setJPQLQuery(new TaTiersDAO(getEm()).getTiersActif());
//						paramAfficheTiers.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//						paramAfficheTiers.setEcranAppelant(paAideController);
//						controllerEcranCreation = swtPaTiersController;
//						parametreEcranCreation = paramAfficheTiers;
//
//						paramAfficheAideRecherche.setTypeEntite(TaTiers.class);
//						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_TIERS);
//						paramAfficheAideRecherche.setDebutRecherche(vue.getTfTiers().getText());
//						paramAfficheAideRecherche.setControllerAppelant(this);
//						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTiers,TaTiersDAO,TaTiers>(SWTTiers.class,getEm()));
//						paramAfficheAideRecherche.setTypeObjet(swtPaTiersController.getClassModel());
//						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_TIERS);
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
					// paramAfficheAideRecherche.setFocusDefaut(paAideRechercheController1.getVue().getTfChoix());

					// Ajout d'une recherche
					paAideController.addRecherche(paAideRechercheController1,paramAfficheAideRecherche.getTitreRecherche());

					// Parametrage de l'ecran d'aide principal
					ParamAfficheAideSWT paramAfficheAideSWT = new ParamAfficheAideSWT();
					ParamAfficheAide paramAfficheAide = new ParamAfficheAide();

					// enregistrement pour le retour de l'ecran d'aide
					paAideController.addRetourEcranListener(this);
					Control focus = vue.getShell().getDisplay().getFocusControl();
					// affichage de l'ecran d'aide principal (+ ses recherches)


				}

			} finally {
				VerrouInterface.setVerrouille(false);
				vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
			}
		}
	}


	

	@Override
	protected void actAnnuler() throws Exception {
		// TODO Auto-generated method stub

	}

	protected void actReinitialiser() throws Exception {

		dao.setEm(new TaReglementDAO().getEntityManager());
		setEm(dao.getEntityManager());
		taRemise=new TaRemise();
		listeEntity.clear();
		listeObjet.clear();
		actRefresh(false);
	}
	
	@Override
	protected void actEnregistrer() throws Exception {
		EntityTransaction transaction = dao.getEntityManager().getTransaction();
		try {
			try {
				boolean declanchementExterne = false;
				if(sourceDeclencheCommandeController==null) {
					declanchementExterne = true;
				}
				if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
						|| (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {

					if(declanchementExterne) {
						ctrlTousLesChampsAvantEnregistrementSWT();
					}
					
					fireEnregistreTout(new AnnuleToutEvent(this,true));

					if(!enregistreToutEnCours) {
						dao.begin(transaction);
						taRemise=dao.enregistrerMerge(taRemise);

						dao.commit(transaction);
//						actImprimer();
						//reinitialiser l'�cran
						actReinitialiser();

						initEtatBouton(true);
					} 
					transaction = null;
				}
			}
			catch (Exception e) {
				logger.error("",e);
				throw e;
			}	
		}finally {
			if(transaction!=null && transaction.isActive()) {
				transaction.rollback();
			}
			initEtatBouton();
		}
	}
	@Override
	protected void actFermer() throws Exception {
		// TODO Auto-generated method stub
		if(onClose())
			closeWorkbenchPart();
	}

	@Override
	protected void actImprimer() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void actInserer() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void actModifier() throws Exception {
		if(taRemise!=null)dao.modifier(taRemise);
		initEtatBouton(true);
	}

	@Override
	protected void actRefresh() throws Exception {
		try{
			modelRemise.setListeEntity(null);
			WritableList writableListFacture = new WritableList(remplirListe(), IHMRemise.class);
			getTableViewerStandard().setInput(writableListFacture);	
			getTableViewerStandard().selectionGrille(0);
			changementDeSelection();
			if(paSelectionLigneRelanceControlleur!=null){
				paSelectionLigneRelanceControlleur.setMasterEntity(taRemise);
				paSelectionLigneRelanceControlleur.setMasterDAO(dao);
				paSelectionLigneRelanceControlleur.actRefresh();
				paSelectionLigneRelanceControlleur.actToutDeCocher();
				paSelectionLigneRelanceControlleur.getGrille().forceFocus();
			}
		}catch (Exception e) {
				logger.error("",e);
			}
		finally{
			vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
		}
	}
	protected void actRefresh(boolean remplirListe) throws Exception {
		try{
			modelRemise.setListeEntity(null);
			WritableList writableListFacture = null;
			if(remplirListe)writableListFacture=new WritableList(remplirListe(), IHMRemise.class);
			else	 writableListFacture = new WritableList(listeObjet, IHMRemise.class);
			getTableViewerStandard().setInput(writableListFacture);	
			getTableViewerStandard().selectionGrille(0);
			changementDeSelection();
			if(paSelectionLigneRelanceControlleur!=null){
				paSelectionLigneRelanceControlleur.setMasterEntity(taRemise);
				paSelectionLigneRelanceControlleur.setMasterDAO(dao);
				paSelectionLigneRelanceControlleur.actRefresh();
				paSelectionLigneRelanceControlleur.actToutDeCocher();
				paSelectionLigneRelanceControlleur.getGrille().forceFocus();
			}
		}catch (Exception e) {
				logger.error("",e);
			}
		finally{
			vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
		}
	}
	public IStatus validateUI() throws Exception {
		if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
				|| (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
			ctrlTousLesChampsAvantEnregistrementSWT();
		}
		return null;
	}

	public IStatus validateUIField(String nomChamp,Object value) {
		return null;
	}

	@Override
	protected void actSupprimer() throws Exception {
		EntityTransaction transaction = dao.getEntityManager().getTransaction();
		try {
			VerrouInterface.setVerrouille(true);
//			if(isUtilise())MessageDialog.openInformation(vue.getShell(), MessagesEcran
//					.getString("Message.Attention"), MessagesEcran
//					.getString("Message.suppression"));
//			else
			taRemise=dao.findById(((IHMRemise)selectedRelance.getValue()).getIdDocument());
				if (MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), "Etes-vous sûr de vouloir supprimer la remise :"+
						taRemise.getCodeDocument())) {
					dao.begin(transaction);
					dao.supprimer(taRemise);
					dao.commit(transaction);
					taRemise=null;
					dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
					actRefresh(); //ajouter pour tester jpa
				}
		} catch (ExceptLgr e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		}finally {
			if(transaction!=null && transaction.isActive()) {
				transaction.rollback();
			}
			initEtatBouton();
			VerrouInterface.setVerrouille(false);
		}
	}


	@Override
	protected void initActions() {
		mapCommand = new HashMap<String, IHandler>();

		mapCommand.put(C_COMMAND_GLOBAL_MODIFIER_ID, handlerModifier);
		mapCommand.put(C_COMMAND_GLOBAL_ANNULER_ID, handlerAnnuler);
		mapCommand.put(C_COMMAND_GLOBAL_ENREGISTRER_ID, handlerEnregistrer);
		mapCommand.put(C_COMMAND_GLOBAL_INSERER_ID, handlerInserer);
		mapCommand.put(C_COMMAND_GLOBAL_SUPPRIMER_ID, handlerSupprimer);
		mapCommand.put(C_COMMAND_GLOBAL_REFRESH_ID, handlerRefresh);
		mapCommand.put(C_COMMAND_GLOBAL_AIDE_ID, handlerAide);
		mapCommand.put(C_COMMAND_GLOBAL_FERMER_ID, handlerFermer);
		mapCommand.put(C_COMMAND_GLOBAL_IMPRIMER_ID, handlerImprimer);

		mapCommand.put(C_COMMAND_GLOBAL_PRECEDENT_ID, handlerPrecedent);
		mapCommand.put(C_COMMAND_GLOBAL_SUIVANT_ID, handlerSuivant);


		initFocusCommand(String.valueOf(this.hashCode()),listeComposantFocusable,mapCommand);

		if (mapActions == null)
			mapActions = new LinkedHashMap<Button, Object>();

		mapActions.put(vue.getBtnFermer(), C_COMMAND_GLOBAL_FERMER_ID);
		mapActions.put(vue.getBtnSupprimer(), C_COMMAND_GLOBAL_SUPPRIMER_ID);
		mapActions.put(vue.getBtnValiderParam(), C_COMMAND_GLOBAL_REFRESH_ID);

		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID, C_COMMAND_GLOBAL_REFRESH_ID };
		mapActions.put(null, tabActionsAutres);
	}


	@Override
	protected void initComposantsVue() throws ExceptLgr {
		// TODO Auto-generated method stub

	}

	@Override
	public void initEtatComposant() {
		// TODO Auto-generated method stub

	}

	private void changementDeSelection() {
		try {
			taRemise=new TaRemise();
			if(selectedRelance!=null && ((IHMRemise)selectedRelance.getValue())!=null)
				taRemise=dao.findById(((IHMRemise)selectedRelance.getValue()).getIdDocument());
				if(paSelectionLigneRelanceControlleur!=null && taRemise!=null){
				paSelectionLigneRelanceControlleur.setMasterEntity(taRemise);
				paSelectionLigneRelanceControlleur.setMasterDAO(dao);
				paSelectionLigneRelanceControlleur.actRefresh();
				paSelectionLigneRelanceControlleur.actToutDeCocher();
				paSelectionLigneRelanceControlleur.initEtatBouton(true);
			}
//			initEtatBouton(true);
		} catch (Exception e) {
			logger.error("",e );
		}
	}
	public void bind(){
		try {
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			selectedRelance = lgrDatabindingUtil.bindTable(vue.getTableFacture(),modelRemise.getListeObjet(),IHMRemise.class,
					new String[] {"Code remise","libelle remise","Date remise","type paiement"},
					new String[] {"100","400","100","150"},
					new String[] {"codeDocument","libelleDocument","dateDocument","codePaiement"}
			);
					

			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			setTableViewerStandard(lgrDatabindingUtil.getMapLgrTableViewer().get(vue.getTableFacture()));
			setDbcStandard(dbc);
			selectedRelance.addChangeListener(new IChangeListener() {

				public void handleChange(ChangeEvent event) {
					changementDeSelection();
				}

			});
			vue.getGrille().addFocusListener(new FocusListener() {
				
				@Override
				public void focusLost(FocusEvent e) {
					
				}
				
				@Override
				public void focusGained(FocusEvent e) {
				}
			});
			initEtatBouton(true);
			changementDeSelection();
		
		} catch(Exception e) {
			logger.error("",e);
			vue.getLaMessage().setText(e.getMessage());
		}
	}


	@Override
	protected void initMapComposantChamps() {
		if (mapComposantChamps == null) 
			mapComposantChamps = new LinkedHashMap<Control,String>();


		
		if (listeComposantFocusable == null) 
			listeComposantFocusable = new ArrayList<Control>();
		listeComposantFocusable.add(vue.getTfDateDebutPeriode());
		listeComposantFocusable.add(vue.getTfDateFinPeriode());
		listeComposantFocusable.add(vue.getCbListePaiement());
		listeComposantFocusable.add(vue.getBtnValiderParam());
		listeComposantFocusable.add(vue.getGrille());
		listeComposantFocusable.add(vue.getBtnFermer());
		listeComposantFocusable.add(vue.getBtnSupprimer());

		
		if(mapInitFocus == null) 
			mapInitFocus = new LinkedHashMap<ModeObjet.EnumModeObjet,Control>();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION,vue.getGrille());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION,vue.getGrille());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION,vue.getGrille());

		activeModifytListener();
		
		vue.getCbListePaiement().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if(!typePaiementCourant.equals(vue.getCbListePaiement().getItem(vue.getCbListePaiement().getSelectionIndex()))){	
						typePaiementCourant=vue.getCbListePaiement().getItem(vue.getCbListePaiement().getSelectionIndex());
						actRefresh();
					}
				} catch (Exception e1) {
					logger.error("", e1);
					vue.getCbListePaiement().forceFocus();
				}
			}
		});

	}


	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null) 
			mapComposantDecoratedField = new LinkedHashMap<Control,DecoratedField>();

	}
	protected void initImageBouton() {
		super.initImageBouton();
		vue.getBtnValiderParam().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ACCEPTER));
		vue.getBtnFermer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_FERMER));
		vue.getBtnSupprimer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_SUPPRIMER));
		vue.layout(true);
	}

	@Override
	public boolean onClose() throws ExceptLgr {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public ResultAffiche configPanel(ParamAffiche param) {
		if (param!=null){
			Map<String,String[]> map = dao.getParamWhereSQL();
			if (param.getFocusDefautSWT()!=null && !param.getFocusDefautSWT().isDisposed())
				param.getFocusDefautSWT().setFocus();


			if(param instanceof ParamAfficheReglementMultiple){

			}
			bind();
			taRemise=new TaRemise();


			VerrouInterface.setVerrouille(false);
			initEtatBouton(true);
		}
		return null;
	}

	@Override
	public Composite getVue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void sortieChamps() {
		// TODO Auto-generated method stub

	}

	public Date getDateDeb() {
		return dateDeb;
	}

	public void setDateDeb(Date dateDeb) {
		this.dateDeb = dateDeb;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}



	public void setVue(PaCritereRemonterRemise vue) {
		super.setVue(vue);
		this.vue = vue;
	}


	public PaSelectionLigneRemiseControlleur getPaSelectionLigneRelanceControlleur() {
		return paSelectionLigneRelanceControlleur;
	}

	public void PaSelectionLigneRemiseControlleur(
			PaSelectionLigneRemiseControlleur paSelectionLigneRelanceControlleur) {
		this.paSelectionLigneRelanceControlleur = paSelectionLigneRelanceControlleur;
	}

	@Override
	protected void initEtatBouton() {
		initEtatBouton(false);
	}
	
	@Override
	protected void initEtatBouton(boolean initFocus) {
		boolean trouve = true;
		switch (daoStandard.getModeObjet().getMode()) {
		case C_MO_INSERTION:
			enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_FERMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID,true);
			break;
		case C_MO_EDITION:
			enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_FERMER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID,true);
			break;
		case C_MO_CONSULTATION:
			enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_FERMER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID,true);
			break;
		default:
			break;
		}
		//	}
		initEtatComposant();
		if (initFocus)
			initFocusSWT(daoStandard, mapInitFocus);	
		
	}
	
	
	public void retourEcran(final RetourEcranEvent evt) {
		if (evt.getRetour() != null
				&& (evt.getSource() instanceof SWTPaAideControllerSWT)) {
			
		} else if (evt.getRetour() != null){

		}
		super.retourEcran(evt);
	}

	
	public LinkedList<IHMRemise> remplirListe() {
		TaRemiseDAO dao =new TaRemiseDAO(getEm());
		IHMRemise ihmRemise = null;
		listeObjet.clear();
		listeEntity=dao.rechercheDocument(LibDateTime.getDate(vue.getTfDateDebutPeriode()), 
				LibDateTime.getDate(vue.getTfDateFinPeriode()));
		if(listeEntity != null) {
			for(TaRemise remise : listeEntity){
				if(remise.getTaTPaiement().getCodeTPaiement().equals(typePaiementCourant)||
						typePaiementCourant.equals(TypeDoc.TYPE_TOUS)){
					ihmRemise = new IHMRemise();
					ihmRemise.setIdDocument(remise.getIdDocument());
					ihmRemise.setCodeDocument(remise.getCodeDocument());
					ihmRemise.setLibelleDocument(remise.getLibelleDocument());
					ihmRemise.setDateDocument(remise.getDateDocument());
					ihmRemise.setCodePaiement(remise.getTaTPaiement().getCodeTPaiement());
					listeObjet.add(ihmRemise);
				}
			}
		}
		return listeObjet;
	}
}

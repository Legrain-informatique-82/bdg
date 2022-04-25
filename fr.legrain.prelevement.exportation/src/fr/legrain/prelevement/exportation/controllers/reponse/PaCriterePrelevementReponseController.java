package fr.legrain.prelevement.exportation.controllers.reponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import fr.legrain.document.editor.EditorListeDocument;
import fr.legrain.documents.dao.TaEtat;
import fr.legrain.documents.dao.TaEtatDAO;
import fr.legrain.documents.dao.TaPrelevement;
import fr.legrain.documents.dao.TaPrelevementDAO;
import fr.legrain.documents.dao.TaTPaiement;
import fr.legrain.documents.dao.TaTPaiementDAO;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Document.IHMAidePrelevement;
import fr.legrain.gestCom.Module_Document.IHMEntetePrelevement;
import fr.legrain.gestCom.Module_Tiers.SWTTiers;
import fr.legrain.gestCom.gestComBd.gestComBdPlugin;
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
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.InfosVerifSaisie;
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
import fr.legrain.libMessageLGR.LgrMess;
import fr.legrain.prelevement.editor.EditorInputPrelevement;
import fr.legrain.prelevement.editor.EditorPrelevement;
import fr.legrain.prelevement.exportation.pluginPrelevement;
import fr.legrain.prelevement.exportation.divers.ParamAffichePresentation;
import fr.legrain.prelevement.exportation.ecrans.presentationBanque.PaCriterePrelevementPresentation;
import fr.legrain.prelevement.exportation.ecrans.presentationBanque.PaSelectionLignePresentation;
import fr.legrain.prelevement.exportation.ecrans.refus.PaCriterePrelevementRefus;
import fr.legrain.prelevement.exportation.ecrans.refus.PaSelectionLigneRefus;
import fr.legrain.prelevement.exportation.ecrans.reponses.PaCriterePrelevementReponse;
import fr.legrain.prelevement.exportation.ecrans.reponses.PaSelectionLigneReponse;
import fr.legrain.tiers.dao.TaCompteBanque;
import fr.legrain.tiers.dao.TaCompteBanqueDAO;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.ecran.PaTiersSWT;
import fr.legrain.tiers.ecran.ParamAfficheTiers;
import fr.legrain.tiers.ecran.SWTPaTiersController;
import fr.legrain.tiers.editor.EditorInputTiers;
import fr.legrain.tiers.editor.TiersMultiPageEditor;

public class PaCriterePrelevementReponseController extends JPABaseControllerSWTStandard 
implements RetourEcranListener{

	static Logger logger = Logger.getLogger(PaCriterePrelevementReponseController.class.getName());	
	private PaCriterePrelevementReponse  vue = null;
	private Date dateDeb=null;
	private Date dateFin=null;
	
	
	private TaPrelevementDAO dao = null;
	private List<TaPrelevement> listeTaPrelevement=null; // <--
	
	TaInfoEntreprise infos =null;
	TaInfoEntrepriseDAO daoInfos=null;
	TaEtatDAO daoEtat = null;
//	TaCompteBanqueDAO daoCompte = null;
	
	private String typeReponseCourante=null;
	private String compteCourant = null;
			
	private Object ecranAppelant = null;
	private Realm realm;
	private DataBindingContext dbc;
	
	private ModelGeneralObjet<IHMEntetePrelevement,TaPrelevementDAO,TaPrelevement> modelPrelevement = null;
	private Object selectedPrelevement = new IHMEntetePrelevement();
	private PaSelectionLigneReponseControlleur paSelectionLigneRemiseControlleur=null;
	private MapChangeListener changeListener = new MapChangeListener();

	protected List<TaEtat> listeEtat= new LinkedList<TaEtat>();
	
	protected HandlerListeDocument handlerListeDocument = new HandlerListeDocument();
	
	public PaCriterePrelevementReponseController(PaCriterePrelevementReponse vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao =new TaPrelevementDAO(getEm());
		modelPrelevement = new ModelGeneralObjet<IHMEntetePrelevement,TaPrelevementDAO,TaPrelevement>
		(dao,IHMEntetePrelevement.class);
		setVue(vue);

		vue.getShell().addShellListener(this);

		//Branchement action annuler : empeche la fermeture automatique de la fenetre sur ESC
		vue.getShell().addTraverseListener(new Traverse());

		initController();
		initSashFormWeight();
		daoInfos=new TaInfoEntrepriseDAO(getEm());
		infos =daoInfos.findInstance();
		
	}

	public PaCriterePrelevementReponseController(PaCriterePrelevementReponse vue) {
		this(vue,null);
	}
	
	private void initController()	{
		try {	
//			setGrille(vue.getTableFacture());
			initSashFormWeight();
			setDaoStandard(dao);
			//			setTableViewerStandard(tableViewer);
						setDbcStandard(dbc);

			initVue();
			//boutton cahcés pour l'instant mais peut être à supprimer définitivement
			vue.getBtnSuivant().setVisible(false);
			
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
//			vue.getPaGrille().setMenu(popupMenuGrille);

			initEtatBouton(true);
			
			daoInfos=new TaInfoEntrepriseDAO(getEm());
			infos =daoInfos.findInstance();
			LibDateTime.setDate(vue.getTfDateDebutPeriode(),infos.getDatedebInfoEntreprise());
			LibDateTime.setDate(vue.getTfDateFinPeriode(),infos.getDatefinInfoEntreprise());
			
			daoEtat =new TaEtatDAO();
			vue.getCbListeReponse().add("TOUS");
			listeEtat =daoEtat.selectAll();
			for (TaEtat taEtat : listeEtat) {
				vue.getCbListeReponse().add(taEtat.getCodeEtat());
			}
			vue.getCbListeReponse().select(0);
			vue.getCbListeReponse().setVisibleItemCount(vue.getCbListeReponse().getItemCount());		

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
		PaSelectionLigneReponse selectionLigneRelance = new PaSelectionLigneReponse(vue.getGroupFactureNonRegle(), SWT.PUSH,1,
				 SWT.FULL_SELECTION
					| SWT.H_SCROLL
					| SWT.V_SCROLL
					| SWT.BORDER
					| SWT.CHECK);//
		selectionLigneRelance.getGrille().setSize(SWT.DEFAULT, 600);
		GridData laDateDebutLData = new GridData();
		laDateDebutLData.grabExcessHorizontalSpace=true;
		laDateDebutLData.grabExcessVerticalSpace=true;
		laDateDebutLData.horizontalAlignment=GridData.FILL;
		laDateDebutLData.verticalAlignment=GridData.FILL;
		selectionLigneRelance.setLayoutData(laDateDebutLData);
		selectionLigneRelance.layout();
		vue.layout();
		paSelectionLigneRemiseControlleur = new PaSelectionLigneReponseControlleur(selectionLigneRelance,getEm());
		ParamAffichePresentation paramAfficheRelanceFacture =new ParamAffichePresentation();
		paramAfficheRelanceFacture.setEnregistreDirect(true);
		paSelectionLigneRemiseControlleur.configPanel(paramAfficheRelanceFacture);
		paSelectionLigneRemiseControlleur.setImpressionUniquement(false);
		
		selectionLigneRelance.getBtnImprimer().setText("Enregistrer/Imprimer");
		
//		addExpandBarItem(vue.getExpandBarGroup(), selectionLigneRelance,
//		"Sélection des prélèvements déjà présentés en banque", pluginPrelevement.getImageDescriptor(
//		"/icons/logo_lgr_16.png").createImage(), SWT.DEFAULT, 400);
//		vue.getExpandBarGroup().getItem(0).setExpanded(true);
	}

	@Override
	protected void actAide() throws Exception {
		// TODO Auto-generated method stub
		actAide(null);
	}

	@Override
	protected boolean aideDisponible() {
		return true;
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
				
				
				if(getFocusAvantAideSWT().equals(vue.getTfTiers())){
					
					PaTiersSWT paTiersSWT = new PaTiersSWT(s2,SWT.NULL);
					SWTPaTiersController swtPaTiersController = new SWTPaTiersController(paTiersSWT);
					paramAfficheAideRecherche.setForceAffichageAideRemplie(false);

					editorCreationId = TiersMultiPageEditor.ID_EDITOR;
					editorInputCreation = new EditorInputTiers();

					ParamAfficheTiers paramAfficheTiers = new ParamAfficheTiers();
					paramAfficheAideRecherche.setJPQLQuery(new TaTiersDAO(getEm()).getJPQLQuery());
					paramAfficheTiers.setModeEcran(EnumModeObjet.C_MO_INSERTION);
					paramAfficheTiers.setEcranAppelant(paAideController);
					controllerEcranCreation = swtPaTiersController;
					parametreEcranCreation = paramAfficheTiers;
					
					paramAfficheAideRecherche.setTypeEntite(TaTiers.class);
					paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_TIERS);
					paramAfficheAideRecherche.setDebutRecherche(vue.getTfTiers().getText());
		//			paramAfficheAideRecherche.setControllerAppelant(this);
					paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTiers, TaTiersDAO, TaTiers>(SWTTiers.class, 
							new TaTiersDAO(getEm()).getEntityManager()));
					paramAfficheAideRecherche.setTypeObjet(swtPaTiersController.getClassModel());
					paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_TIERS);
					
				}
				if(getFocusAvantAideSWT().equals(vue.getTfCodePrelevement()) ){
						//permet de paramètrer l'affichage remplie ou non de l'aide
						paramAfficheAideRecherche.setForceAffichageAideRemplie(false);
						editorCreationId = EditorPrelevement.ID_EDITOR;
						editorInputCreation = new EditorInputPrelevement();

						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
						controllerEcranCreation = this;

						paramAfficheAideRecherche.setAfficheDetail(false);

						paramAfficheAideRecherche.setTypeEntite(TaPrelevement.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_DOCUMENT);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCodePrelevement().getText());
//						paramAfficheAideRecherche.setControllerAppelant("PaEditorPrelevementController");
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<IHMAidePrelevement,TaPrelevementDAO,TaPrelevement>(dao,IHMAidePrelevement.class));
						paramAfficheAideRecherche.setTypeObjet(IHMAidePrelevement.class);
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
//		dao.annuler(taPrelevement);
//		taPrelevement=new TaPrelevement();
//		dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
	}

	@Override
	protected void actEnregistrer() throws Exception {
//		EntityTransaction transaction = dao.getEntityManager().getTransaction();
//		try {
//			try {
//				boolean declanchementExterne = false;
//				if(sourceDeclencheCommandeController==null) {
//					declanchementExterne = true;
//				}
//				if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
//						|| (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
//
//					if(declanchementExterne) {
//						ctrlTousLesChampsAvantEnregistrementSWT();
//					}
//					
//					fireEnregistreTout(new AnnuleToutEvent(this,true));
//
//					if(!enregistreToutEnCours) {
//						dao.begin(transaction);
//						if((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)){	
//							taPrelevement=dao.enregistrerMerge(taPrelevement);
//						}
//						else taPrelevement=dao.enregistrerMerge(taPrelevement);
//
//						dao.commit(transaction);
//						actImprimer();
//						//reinitialiser l'�cran
//						actReinitialiser();
//
//						initEtatBouton(true);
//					} 
//					transaction = null;
//				}
//			}
//			catch (Exception e) {
//				logger.error("",e);
//				throw e;
//			}	
//		}finally {
//			if(transaction!=null && transaction.isActive()) {
//				transaction.rollback();
//			}
//			initEtatBouton();
//		}
	}

	@Override
	protected void actFermer() throws Exception {
		onClose();
	}

	
	protected void actReinitialiser() throws Exception {
		modelPrelevement.getListeEntity().clear();
		modelPrelevement.getListeObjet().clear();
		vue.getTfCodePrelevement().setText("");
		actRefresh();
	}
	@Override
	protected void actImprimer() throws Exception { //Button VALIDER <--
		String etat = null;
		int rang =vue.getCbListeReponse().getSelectionIndex();
		if(rang!=-1)
			etat=vue.getCbListeReponse().getItem(rang);
		
		
		if(!vue.getTfCodePrelevement().getText().equals("") || vue.getTfTiers().getText().equals("") ){
			listeTaPrelevement = dao.recherchePrelevement(LibDateTime.getDate(vue.getTfDateDebutPeriode()),LibDateTime.getDate(vue.getTfDateFinPeriode()),
					vue.getTfCodePrelevement().getText(),1,etat);
		}else{
			listeTaPrelevement = dao.recherchePrelevementTiers(LibDateTime.getDate(vue.getTfDateDebutPeriode()),LibDateTime.getDate(vue.getTfDateFinPeriode()),
					vue.getTfTiers().getText(),1,etat);	
		}
		for (TaPrelevement obj : listeTaPrelevement) {
			obj.setAccepte(true);
		}


		actRefresh();

	}

	

	@Override
	protected void actInserer() throws Exception {

	}

	@Override
	protected void actModifier() throws Exception {
//		if(taPrelevement!=null)dao.modifier(taPrelevement);
		initEtatBouton(true);
	}

	@Override
	protected void actRefresh() throws Exception {
		try{
			if(paSelectionLigneRemiseControlleur!=null){
;
//				
//				Set <TaCompteBanque> selectCompteBanqueSEPA = new HashSet<TaCompteBanque>() ; //<<--
//				TaCompteBanqueDAO daoCompteBanqueSepa = new TaCompteBanqueDAO();
//				infos = new TaInfoEntrepriseDAO().findInstance();
//				selectCompteBanqueSEPA=infos.getTaTiers().getTaCompteBanques();
//				for (TaCompteBanque taCompteBanque : selectCompteBanqueSEPA) {
//					if(taCompteBanque.getCptcomptable().equals(compteCourant))
//						paSelectionLigneRemiseControlleur.setCompteEntrepriseCourant(taCompteBanque);				
//				}
				paSelectionLigneRemiseControlleur.setMasterListeEntity(listeTaPrelevement);
				paSelectionLigneRemiseControlleur.setMasterDAO(dao);
				paSelectionLigneRemiseControlleur.actRefresh();
				//((PaSelectionLignePrelevement)paSelectionLigneRemiseControlleur.getVue()).getGrille().forceFocus();
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
		String validationContext = "REMISE";

		try {
			IStatus s = null;
				if (nomChamp.equals(Const.C_DATE_DEBUT) ) {
					 return new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
				} 
				if (nomChamp.equals(Const.C_DATE_FIN) ) {
					return new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
				} 
//				if (nomChamp.equals(Const.C_DATE_DOCUMENT) ) {
//					TaPrelevement f=new TaPrelevement();
//					PropertyUtils.setSimpleProperty(f, nomChamp, value);
//					f.setDateDocument(f.dateDansExercice((Date)value));
//					s = dao.validate(f, nomChamp, validationContext);
//					if(f.getDateDocument().compareTo(infos.getDatedebInfoEntreprise())<0)
//						value=infos.getDatedebInfoEntreprise();
//					PropertyUtils.setSimpleProperty(taPrelevement, nomChamp, value);
//					LibDateTime.setDate(vue.getTfDateRemise(),taPrelevement.getDateDocument());
//				} 
//				if (nomChamp.equals(Const.C_LIBELLE_DOCUMENT) ) {
//					TaPrelevement f=new TaPrelevement();
//					PropertyUtils.setSimpleProperty(f, nomChamp, value);
//					s = dao.validate(f, nomChamp, validationContext);
//					if (s.getSeverity() != IStatus.ERROR) {
//						PropertyUtils.setSimpleProperty(taPrelevement, nomChamp, value);
//						vue.getTfCodeRelance().setText(taPrelevement.getLibelleDocument());
//					}
//				} 
				
			if (s.getSeverity() != IStatus.ERROR) {
			}
			//			// new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
			return s;
//		} catch (IllegalAccessException e) {
//			logger.error("", e);
//		} catch (InvocationTargetException e) {
//			logger.error("", e);
//		} catch (NoSuchMethodException e) {
//			logger.error("", e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void actSupprimer() throws Exception {
		// TODO Auto-generated method stub

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
		mapCommand.put(C_COMMAND_DOCUMENT_LISTE_DOCUMENT_ID, handlerListeDocument);

		mapCommand.put(C_COMMAND_GLOBAL_PRECEDENT_ID, handlerPrecedent);
		mapCommand.put(C_COMMAND_GLOBAL_SUIVANT_ID, handlerSuivant);


		initFocusCommand(String.valueOf(this.hashCode()),listeComposantFocusable,mapCommand);

		if (mapActions == null)
			mapActions = new LinkedHashMap<Button, Object>();

		mapActions.put(vue.getBtnFermer(), C_COMMAND_GLOBAL_FERMER_ID);
//		mapActions.put(vue.getBtnListeDoc(), C_COMMAND_DOCUMENT_LISTE_DOCUMENT_ID);
		mapActions.put(vue.getBtnValiderParam(), C_COMMAND_GLOBAL_IMPRIMER_ID);


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

	public void bind(){
		try {
			listeTaPrelevement = new LinkedList<TaPrelevement>();
			modelPrelevement = new ModelGeneralObjet<IHMEntetePrelevement,TaPrelevementDAO,TaPrelevement>(dao,IHMEntetePrelevement.class);
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			if (modelPrelevement.getListeObjet().isEmpty()) {
				modelPrelevement.getListeObjet().add(new IHMEntetePrelevement());
			}
			selectedPrelevement = modelPrelevement.getListeObjet().getFirst();
			
			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			setDbcStandard(dbc);

			bindingFormSimple(dbc, realm, selectedPrelevement, IHMEntetePrelevement.class);
			
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());
			dbc = new DataBindingContext(realm);
			setDbcStandard(dbc);
			initEtatBouton(true);
		} catch(Exception e) {
			logger.error("",e);
			vue.getLaMessage().setText(e.getMessage());
		}
	}

	@Override
	protected void initMapComposantChamps() {
		if (mapComposantChamps == null) 
			mapComposantChamps = new LinkedHashMap<Control,String>();

//		mapComposantChamps.put(vue.getTfDateDebutPeriode(), Const.C_DATE_DEBUT);
//		mapComposantChamps.put(vue.getTfDateFinPeriode(), Const.C_DATE_FIN);
		mapComposantChamps.put(vue.getTfTiers(), Const.C_CODE_DOCUMENT);
		mapComposantChamps.put(vue.getTfCodePrelevement(), Const.C_LIBELLE_DOCUMENT);
//		mapComposantChamps.put(vue.getTfDateRemise(), Const.C_DATE_DOCUMENT);
		
		if (listeComposantFocusable == null) 
			listeComposantFocusable = new ArrayList<Control>();
		
		listeComposantFocusable.add(vue.getTfDateDebutPeriode());
		listeComposantFocusable.add(vue.getTfDateFinPeriode());
		listeComposantFocusable.add(vue.getTfTiers());
		listeComposantFocusable.add(vue.getTfCodePrelevement());
//		listeComposantFocusable.add(vue.getPrelevExport());
//		listeComposantFocusable.add(vue.getPrelevNonExport());
//		listeComposantFocusable.add(vue.getCbListeCompte());
		listeComposantFocusable.add(vue.getCbListeReponse());	
//		listeComposantFocusable.add(vue.getBtnListeDoc());
		listeComposantFocusable.add(vue.getBtnValiderParam());

//		listeComposantFocusable.add(vue.getTfDateRemise());
		listeComposantFocusable.add(vue.getBtnFermer());
		
		if(mapInitFocus == null) 
			mapInitFocus = new LinkedHashMap<ModeObjet.EnumModeObjet,Control>();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION,vue.getTfDateDebutPeriode());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION,vue.getTfDateDebutPeriode());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION,vue.getTfDateDebutPeriode());

		activeModifytListener();

		vue.getCbListeReponse().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if(vue.getCbListeReponse().getSelectionIndex()!=-1 && typeReponseCourante!=null && !typeReponseCourante.equals(vue.getCbListeReponse().getItem(vue.getCbListeReponse().getSelectionIndex()))){	
						if(dao.dataSetEnModif()){
							if(MessageDialog.openQuestion(vue.getShell(), "Annuler les modifications", "Voulez-vous annuler les modifications en cours")){						
								actAnnuler();
								typeReponseCourante=vue.getCbListeReponse().getItem(vue.getCbListeReponse().getSelectionIndex());
								actRefresh();
							}else
								vue.getCbListeReponse().select(vue.getCbListeReponse().indexOf(typeReponseCourante));
						}else
							typeReponseCourante=vue.getCbListeReponse().getItem(vue.getCbListeReponse().getSelectionIndex());
					}
				} catch (Exception e1) {
					logger.error("", e1);
					vue.getCbListeReponse().forceFocus();
				}
			}
		});
//		
//		vue.getCbListeCompte().addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				try {
//					if(!compteCourant.equals(vue.getCbListeCompte().getItem(vue.getCbListeCompte().getSelectionIndex()))){	
//						if(dao.dataSetEnModif()){
//							if(MessageDialog.openQuestion(vue.getShell(), "Annuler les modifications", "Voulez-vous annuler les modifications en cours")){						
//								actAnnuler();
//								compteCourant=vue.getCbListeCompte().getItem(vue.getCbListeCompte().getSelectionIndex());
//								actRefresh();
//							}else
//								vue.getCbListeCompte().select(vue.getCbListeCompte().indexOf(compteCourant));
//						}else
//							compteCourant=vue.getCbListeCompte().getItem(vue.getCbListeCompte().getSelectionIndex());
//					}
//				} catch (Exception e1) {
//					logger.error("", e1);
//					vue.getCbListeCompte().forceFocus();
//				}
//			}
//		});
		
//		vue.getTfCodeRelance().addFocusListener(new FocusAdapter() {
//			public void focusLost(FocusEvent e) {
//				try {
//					if(vue.getTfCodeRelance().isEnabled()){
//						IStatus status=validateUIField(Const.C_LIBELLE_DOCUMENT,vue.getTfCodeRelance().getText());
//						if(status.getSeverity()== IStatus.ERROR){
//							MessageDialog.openWarning(vue.getShell(),"Erreur de saisie",status.getMessage());
//							throw new Exception(status.getMessage());
//						}
//					}
//				} catch (Exception e1) {
//					logger.error("", e1);
//					vue.getTfCodeRelance().forceFocus();
//				}
//			}
//		});

		vue.getTfDateDebutPeriode().addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				try {
					if(vue.getTfDateDebutPeriode().isEnabled()){
						IStatus status=validateUIField(Const.C_DATE_DEBUT,LibDateTime.getDate(vue.getTfDateDebutPeriode()));
						if(status.getSeverity()== IStatus.ERROR){
							MessageDialog.openWarning(vue.getShell(),"Erreur de saisie",status.getMessage());
							throw new Exception(status.getMessage());
						}
					}
				} catch (Exception e1) {
					logger.error("", e1);
					vue.getTfDateDebutPeriode().forceFocus();
				}
			}
		});
		
		vue.getTfDateFinPeriode().addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				try {
					if(vue.getTfDateFinPeriode().isEnabled()){
						IStatus status=validateUIField(Const.C_DATE_FIN,LibDateTime.getDate(vue.getTfDateFinPeriode()));
						if(status.getSeverity()== IStatus.ERROR){
							MessageDialog.openWarning(vue.getShell(),"Erreur de saisie",status.getMessage());
							throw new Exception(status.getMessage());
						}
					}
				} catch (Exception e1) {
					logger.error("", e1);
					vue.getTfDateFinPeriode().forceFocus();
				}
			}
		});
		

	}
	private void initTiers() {

	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null) 
			mapComposantDecoratedField = new LinkedHashMap<Control,DecoratedField>();
		mapComposantDecoratedField.put(vue.getTfDateDebutPeriode(), vue.getFieldDateDebutPeriode());
		mapComposantDecoratedField.put(vue.getTfDateFinPeriode(), vue.getFieldDateFinPeriode());
	}
	protected void initImageBouton() {
		super.initImageBouton();
		vue.getBtnValiderParam().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ACCEPTER));
		vue.getBtnFermer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_FERMER));
//		vue.getBtnListeDoc().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_EXPORT));
		vue.layout(true);
	}

	@Override
	public boolean onClose() throws ExceptLgr {
		// TODO Auto-generated method stub
		try {
			if(getPaSelectionLigneRemiseControlleur().onClose()){
				closeWorkbenchPart();
				return true;
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return false;
	}

	@Override
	public ResultAffiche configPanel(ParamAffiche param) {
		if (param!=null){
			Map<String,String[]> map = dao.getParamWhereSQL();
			if (param.getFocusDefautSWT()!=null && !param.getFocusDefautSWT().isDisposed())
				param.getFocusDefautSWT().setFocus();
			else param.setFocusDefautSWT(vue.getTfDateDebutPeriode());
			}

			if(param instanceof ParamAffichePresentation){
				if(((ParamAffichePresentation)param).getDateDeb()!=null){
					setDateDeb(((ParamAffichePresentation)param).getDateDeb());
					LibDateTime.setDate(vue.getTfDateDebutPeriode(),getDateDeb());
				}else{
					LibDateTime.setDate(vue.getTfDateDebutPeriode(),infos.getDatedebInfoEntreprise());
				}
				if(((ParamAffichePresentation)param).getDateFin()!=null){
					setDateFin(((ParamAffichePresentation)param).getDateFin());
					LibDateTime.setDate(vue.getTfDateFinPeriode(),getDateFin());
				}else{
					LibDateTime.setDate(vue.getTfDateFinPeriode(),infos.getDatefinInfoEntreprise());
				}
			}
			bind();


			VerrouInterface.setVerrouille(false);
			initEtatBouton(true);
		
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



	public void setVue(PaCriterePrelevementReponse vue) {
		super.setVue(vue);
		this.vue = vue;
	}


	public PaSelectionLigneReponseControlleur getPaSelectionLigneRemiseControlleur() {
		return paSelectionLigneRemiseControlleur;
	}

	public void setPaSelectionLigneRemiseControlleur(
			PaSelectionLigneReponseControlleur paSelectionLigneRelanceControlleur) {
		this.paSelectionLigneRemiseControlleur = paSelectionLigneRelanceControlleur;
	}

	@Override
	protected void initEtatBouton() {
		initEtatBouton(false);
	}
	
	@Override
	protected void initEtatBouton(boolean initFocus) {
		boolean trouve = listeTaPrelevement!=null && listeTaPrelevement.size()>0;
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
			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,trouve);
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
			enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID,true);
			break;
		default:
			break;
		}
		//	}
		enableActionAndHandler(C_COMMAND_DOCUMENT_LISTE_DOCUMENT_ID,true);
		initEtatComposant();
		if (initFocus)
			initFocusSWT(daoStandard, mapInitFocus);	
		
	}
	
	
	public void retourEcran(final RetourEcranEvent evt) {
		if (evt.getRetour() != null
				&& (evt.getSource() instanceof SWTPaAideControllerSWT)) {
			if (getFocusAvantAideSWT() instanceof Text) {
				try {
					((Text) getFocusAvantAideSWT()).setText(((ResultAffiche) evt
							.getRetour()).getResult());		

					ctrlUnChampsSWT(getFocusAvantAideSWT());
				} catch (Exception e) {
					logger.error("",e);
					if(getFocusAvantAideSWT()!=null)setFocusCourantSWT(getFocusAvantAideSWT());
					vue.getLaMessage().setText(e.getMessage());
				}
			}			
		} else if (evt.getRetour() != null){

		}
		super.retourEcran(evt);
	}
	
	
	private class HandlerListeDocument extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				String idEditor = EditorListeDocument.ID;// "fr.legrain.document.editor.EditorListeDocument";
				String valeurIdentifiant = "";
				ouvreDocument(valeurIdentifiant, idEditor);
			} catch (Exception e) {
				logger.error("", e);
			}
			return event;
		}
	}


	public List<TaEtat> getListeEtat() {
		return listeEtat;
	}

	@Override
	public boolean changementPageValide() {
		// TODO Stub de la méthode généré automatiquement
		return getPaSelectionLigneRemiseControlleur().acceptChangementPage();
	}

	


}

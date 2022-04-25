package fr.legrain.relancefacture.controllers;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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

import fr.legrain.document.divers.TypeDoc;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaLRelance;
import fr.legrain.documents.dao.TaRelance;
import fr.legrain.documents.dao.TaRelanceDAO;
import fr.legrain.documents.dao.TaRelanceDocument;
import fr.legrain.documents.dao.TaTRelance;
import fr.legrain.documents.dao.TaTRelanceDAO;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.facture.FacturePlugin;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Document.IHMRelance;
import fr.legrain.gestCom.Module_Tiers.SWTTRelance;
import fr.legrain.gestCom.Module_Tiers.SWTTiers;
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
import fr.legrain.gestCom.librairiesEcran.workbench.AnnuleToutEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.InfosVerifSaisie;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.LibDate;
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
import fr.legrain.relancefacture.Activator;
import fr.legrain.relancefacture.divers.ParamAfficheRelanceFacture;
import fr.legrain.relancefacture.ecran.PaCritereRelanceDocument;
import fr.legrain.relancefacture.ecran.PaSelectionLigneRelance;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.ecran.PaTiersSWT;
import fr.legrain.tiers.ecran.ParamAfficheTiers;
import fr.legrain.tiers.ecran.SWTPaTiersController;
import fr.legrain.tiers.editor.EditorInputTiers;
import fr.legrain.tiers.editor.EditorTiers;
import fr.legrain.tiers.editor.TiersMultiPageEditor;

public class PaCritereRelanceController extends JPABaseControllerSWTStandard 
implements RetourEcranListener{

	static Logger logger = Logger.getLogger(PaCritereRelanceController.class.getName());	
	private PaCritereRelanceDocument  vue = null;
	private Date dateDeb=null;
	private Date dateFin=null;
//	private TaTiers tiers = null;
	
	private TaFactureDAO daoFacture = null;
	private TaRelanceDAO dao = null;
	private TaRelance taRelance = null;
	
	TaInfoEntreprise infos =null;
	TaInfoEntrepriseDAO daoInfos=null;
	
	private Object ecranAppelant = null;
	private Realm realm;
	private DataBindingContext dbc;
	
	private ModelGeneralObjet<IHMRelance,TaRelanceDAO,TaRelance> modelRelance = null;
	private PaSelectionLigneRelanceControlleur paSelectionLigneRelanceControlleur=null;
	
	public PaCritereRelanceController(PaCritereRelanceDocument vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao =new TaRelanceDAO(getEm());
		daoFacture=new TaFactureDAO(getEm());
		modelRelance = new ModelGeneralObjet<IHMRelance,TaRelanceDAO,TaRelance>
		(dao,IHMRelance.class);
		setVue(vue);

		vue.getShell().addShellListener(this);

		//Branchement action annuler : empeche la fermeture automatique de la fenetre sur ESC
		vue.getShell().addTraverseListener(new Traverse());

		initController();
		initSashFormWeight();
		daoInfos=new TaInfoEntrepriseDAO(getEm());
		infos =daoInfos.findInstance();
	}

	public PaCritereRelanceController(PaCritereRelanceDocument vue) {
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
		PaSelectionLigneRelance selectionLigneRelance = new PaSelectionLigneRelance(vue.getExpandBarGroup(), SWT.PUSH,1,
				 SWT.FULL_SELECTION
					| SWT.H_SCROLL
					| SWT.V_SCROLL
					| SWT.BORDER
					| SWT.CHECK);
		paSelectionLigneRelanceControlleur = new PaSelectionLigneRelanceControlleur(selectionLigneRelance,getEm());
		ParamAfficheRelanceFacture paramAfficheRelanceFacture =new ParamAfficheRelanceFacture();
		paramAfficheRelanceFacture.setEnregistreDirect(true);
		paSelectionLigneRelanceControlleur.configPanel(paramAfficheRelanceFacture);
		
		addExpandBarItem(vue.getExpandBarGroup(), selectionLigneRelance,
		"Sélection des documents à relancer ", Activator.getImageDescriptor(
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
		if (getFocusCourantSWT().equals(vue.getTfTiers()))
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
					if (getFocusCourantSWT().equals(vue.getTfTiers())) {
						//permet de paramètrer l'affichage remplie ou non de l'aide

						PaTiersSWT paTiersSWT = new PaTiersSWT(s2, SWT.NULL);
						SWTPaTiersController swtPaTiersController = new SWTPaTiersController(paTiersSWT);
						paramAfficheAideRecherche.setForceAffichageAideRemplie(false);

						editorCreationId = TiersMultiPageEditor.ID_EDITOR;
						editorInputCreation = new EditorInputTiers();

						ParamAfficheTiers paramAfficheTiers = new ParamAfficheTiers();
						paramAfficheAideRecherche.setJPQLQuery(new TaTiersDAO(getEm()).getTiersActif());
						paramAfficheTiers.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheTiers.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaTiersController;
						parametreEcranCreation = paramAfficheTiers;

						paramAfficheAideRecherche.setTypeEntite(TaTiers.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_TIERS);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfTiers().getText());
						paramAfficheAideRecherche.setControllerAppelant(this);
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTiers,TaTiersDAO,TaTiers>(SWTTiers.class,getEm()));
						paramAfficheAideRecherche.setTypeObjet(swtPaTiersController.getClassModel());
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_TIERS);
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

//					if(declanchementExterne) {
//						mapperUIToModel.map((IHMReglement) selectedReglement.getValue(),taRReglement);
//					}

					fireEnregistreTout(new AnnuleToutEvent(this,true));

					if(!enregistreToutEnCours) {
						dao.begin(transaction);
						if((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)){	
							taRelance=dao.enregistrerMerge(taRelance);
						}
						else taRelance=dao.enregistrerMerge(taRelance);

						dao.commit(transaction);
//						actRefresh(); //deja present
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
		Date deb = new Date();
		List<TaFacture> listeFacture =new LinkedList<TaFacture>();
		daoFacture.setEm(new TaFactureDAO().getEntityManager());
		setEm(daoFacture.getEntityManager());
		TaTRelanceDAO daoTRelance = new TaTRelanceDAO(getEm());
		List<TaFacture> listeRelance=daoFacture.rechercheDocumentNonTotalementRegleAEcheance(
				LibDateTime.getDate(vue.getTfDateDebutPeriode()),
				LibDateTime.getDate(vue.getTfDateFinPeriode()),
				vue.getTfTiers().getText(),null,LibConversion.stringToBigDecimal(vue.getTfLimite().getText(), BigDecimal.valueOf(0)) );
		taRelance=new TaRelance();
		Date dateRelance=new Date();
		taRelance.setCodeRelance("Relance du "+LibDate.dateToString(dateRelance)+" à "+
				LibDate.getHeure(dateRelance)+" h"+LibDate.getMinute(dateRelance)
				+" mn"+LibDate.getSeconde(dateRelance)+" s");
		taRelance.setDateDebut(LibDateTime.getDate(vue.getTfDateDebutPeriode()));
		taRelance.setDateFin(LibDateTime.getDate(vue.getTfDateFinPeriode()));
		taRelance.setCodeTiers(vue.getTfTiers().getText());
		taRelance.setDateRelance(dateRelance);
		for (TaFacture taFacture : listeRelance) {
			TaTRelance taTRelance =dao.maxTaTRelance(taFacture);
				if(taTRelance!=null){
					TaLRelance r =new TaLRelance();
					r.setTypeDocument(TypeDoc.TYPE_FACTURE);
					r.setCodeDocument(taFacture.getCodeDocument());
					r.setCodeTiers(taFacture.getTaTiers().getCodeTiers());
					r.setNomTiers(taFacture.getTaTiers().getNomTiers());
					r.setDateEcheance(taFacture.getDateEchDocument());
					r.setNetTTC(taFacture.getNetTtcCalc());
					r.setResteARegler(taFacture.getResteAReglerComplet());
					r.setTaRelance(taRelance);
					r.setTaTRelance(taTRelance);
					r.setAccepte(true);
					taRelance.getTaLRelances().add(r);
				}
			
		}
		vue.getTfCodeRelance().setText(taRelance.getCodeRelance());
		List<TaRelance> liste =new LinkedList<TaRelance>();
		liste.add(taRelance);
		modelRelance.setListeEntity(liste);
//		logger.info("***** intermediaire : "+new Date().toString());
		
		actRefresh();
		paSelectionLigneRelanceControlleur.initTotaux();
		Date fin = new Date();
		Long duree=fin.getTime()-deb.getTime();
		logger.info("***** durée : "+duree );
	}

	
//	@Override
//	protected void actImprimer() throws Exception {
//		Date deb = new Date();
//		List<TaFacture> listeFacture =new LinkedList<TaFacture>();
//		TaTRelanceDAO daoTRelance = new TaTRelanceDAO(getEm());
//		List<Object[]> listeRelance=daoFacture.rechercheDocumentNonTotalementRegleAEcheance2(
//				vue.getTfDateDebutPeriode().getSelection(),vue.getTfDateFinPeriode().getSelection(),
//				vue.getTfTiers().getText(),null);
//		//		List<TaFacture> listeFacture =daoFacture.rechercheDocumentNonTotalementRegleAEcheance(
//		//				vue.getTfDateDebutPeriode().getSelection(),vue.getTfDateFinPeriode().getSelection(),
//		//				vue.getTfTiers().getText(),null);
//		taRelance=new TaRelance();
//		Date dateRelance=new Date();
//		taRelance.setCodeRelance("Relance du "+LibDate.dateToString(dateRelance)+" à "+
//				LibDate.getHeure(dateRelance)+" h"+LibDate.getMinute(dateRelance)
//				+" mn"+LibDate.getSeconde(dateRelance)+" s");
//		taRelance.setDateDebut(vue.getTfDateDebutPeriode().getSelection());
//		taRelance.setDateFin(vue.getTfDateFinPeriode().getSelection());
//		taRelance.setCodeTiers(vue.getTfTiers().getText());
//		taRelance.setDateRelance(dateRelance);
//		for (Object[] taRelanceDoc : listeRelance) {
//			TaTRelance taTRelance =daoTRelance.findById((Integer)taRelanceDoc[1]);
//			TaFacture taFacture = daoFacture.findById((Integer)taRelanceDoc[0]);
//			if(taFacture.calculRegleDocumentComplet().add(taFacture.getAcomptes()).
//					compareTo(taFacture.getNetTtcCalc())<0){
//				if(taTRelance!=null){
//					TaLRelance r =new TaLRelance();
//					r.setTypeDocument(TypeDoc.TYPE_FACTURE);
//					r.setCodeDocument(taFacture.getCodeDocument());
//					r.setCodeTiers(taFacture.getTaTiers().getCodeTiers());
//					r.setNomTiers(taFacture.getTaTiers().getNomTiers());
//					r.setDateEcheance(taFacture.getDateEchDocument());
//					r.setNetTTC(taFacture.getNetTtcCalc());
//					r.setResteARegler(taFacture.getResteAReglerComplet());
//					r.setTaRelance(taRelance);
//					r.setTaTRelance(taTRelance);
//					r.setAccepte(true);
//					taRelance.getTaLRelances().add(r);
//				}
//			}
//		}
//		vue.getTfCodeRelance().setText(taRelance.getCodeRelance());
//		List<TaRelance> liste =new LinkedList<TaRelance>();
//		liste.add(taRelance);
//		modelRelance.setListeEntity(liste);
////		logger.info("***** intermediaire : "+new Date().toString());
//		
//		actRefresh();
//		Date fin = new Date();
//		Long duree=fin.getTime()-deb.getTime();
//		logger.info("***** durée : "+duree );
//	}

	@Override
	protected void actInserer() throws Exception {

	}

	@Override
	protected void actModifier() throws Exception {
		if(taRelance!=null)dao.modifier(taRelance);
		initEtatBouton(true);
	}

	@Override
	protected void actRefresh() throws Exception {
		try{
			if(paSelectionLigneRelanceControlleur!=null){
				paSelectionLigneRelanceControlleur.setMasterEntity(taRelance);
				paSelectionLigneRelanceControlleur.actRefresh();
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
		String validationContext = "RELANCE";

		try {
			IStatus s = null;
				if (nomChamp.equals(Const.C_CODE_TIERS) ) {
					TaTiersDAO dao = new TaTiersDAO(getEm());					
					dao.setModeObjet(this.dao.getModeObjet());
					TaTiers f = new TaTiers();
					PropertyUtils.setSimpleProperty(f, nomChamp, value);
						s = dao.validate(f,nomChamp,validationContext);
					if (s.getSeverity() != IStatus.ERROR) {
							if(!f.getCodeTiers().equals("")){
								vue.getTfTiers().setText(f.getCodeTiers());
							}else {
								vue.getTfTiers().setText(f.getCodeTiers());
							}
							vue.getBtnTousTiers().setSelection(vue.getTfTiers().getText().equals(""));

					}
					dao = null;
				} 
				if (nomChamp.equals(Const.C_DATE_DEBUT) ) {
					TaRelance f=new TaRelance();
					PropertyUtils.setSimpleProperty(f, nomChamp, value);
					s = dao.validate(f, nomChamp, validationContext);
					if(f.getDateDebut().compareTo(infos.getDatedebRelInfoEntreprise())<0)
						value=infos.getDatedebRelInfoEntreprise();
					PropertyUtils.setSimpleProperty(taRelance, nomChamp, value);
					LibDateTime.setDate(vue.getTfDateDebutPeriode(),taRelance.getDateDebut());
				} 
				if (nomChamp.equals(Const.C_DATE_FIN) ) {
					TaRelance f=new TaRelance();
					PropertyUtils.setSimpleProperty(f, nomChamp, value);
					s = dao.validate(f, nomChamp, validationContext);
					if(f.getDateFin().compareTo(infos.getDatedebRelInfoEntreprise())<0)
						value=infos.getDatedebRelInfoEntreprise();
					PropertyUtils.setSimpleProperty(taRelance, nomChamp, value);
					LibDateTime.setDate(vue.getTfDateFinPeriode(),taRelance.getDateFin());
				} 
				if (nomChamp.equals(Const.C_CODE_RELANCE) ) {
					TaRelance f=new TaRelance();
					PropertyUtils.setSimpleProperty(f, nomChamp, value);
					s = dao.validate(f, nomChamp, validationContext);
					if (s.getSeverity() != IStatus.ERROR) {
						PropertyUtils.setSimpleProperty(taRelance, nomChamp, value);
						vue.getTfCodeRelance().setText(taRelance.getCodeRelance());
					}
				} 
				
			if (s.getSeverity() != IStatus.ERROR) {
			}
			//			// new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
			return s;
		} catch (IllegalAccessException e) {
			logger.error("", e);
		} catch (InvocationTargetException e) {
			logger.error("", e);
		} catch (NoSuchMethodException e) {
			logger.error("", e);
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

		mapCommand.put(C_COMMAND_GLOBAL_PRECEDENT_ID, handlerPrecedent);
		mapCommand.put(C_COMMAND_GLOBAL_SUIVANT_ID, handlerSuivant);


		initFocusCommand(String.valueOf(this.hashCode()),listeComposantFocusable,mapCommand);

		if (mapActions == null)
			mapActions = new LinkedHashMap<Button, Object>();

		mapActions.put(vue.getBtnFermer(), C_COMMAND_GLOBAL_FERMER_ID);
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

		mapComposantChamps.put(vue.getTfDateDebutPeriode(), Const.C_DATE_DEBUT);
		mapComposantChamps.put(vue.getTfDateFinPeriode(), Const.C_DATE_FIN);
		mapComposantChamps.put(vue.getTfTiers(), Const.C_CODE_TIERS);
		mapComposantChamps.put(vue.getTfCodeRelance(), Const.C_CODE_RELANCE);
		mapComposantChamps.put(vue.getTfLimite(), Const.C_LIMITE);
		
		if (listeComposantFocusable == null) 
			listeComposantFocusable = new ArrayList<Control>();
		
		listeComposantFocusable.add(vue.getTfDateDebutPeriode());
		listeComposantFocusable.add(vue.getTfDateFinPeriode());
		listeComposantFocusable.add(vue.getBtnTousTiers());
		listeComposantFocusable.add(vue.getTfTiers());
		listeComposantFocusable.add(vue.getBtnValiderParam());
		listeComposantFocusable.add(vue.getTfCodeRelance());
		listeComposantFocusable.add(vue.getBtnFermer());
		
		if(mapInitFocus == null) 
			mapInitFocus = new LinkedHashMap<ModeObjet.EnumModeObjet,Control>();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION,vue.getTfTiers());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION,vue.getTfTiers());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION,vue.getTfTiers());

		activeModifytListener();

		vue.getTfTiers().addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				try {
					if(vue.getTfTiers().isEnabled()){
						IStatus status=validateUIField(Const.C_CODE_TIERS,vue.getTfTiers().getText().toUpperCase());
						if(status.getSeverity()== IStatus.ERROR){
							MessageDialog.openWarning(vue.getShell(),"Erreur de saisie",status.getMessage());
							throw new Exception(status.getMessage());
						}
					}
				} catch (Exception e1) {
					logger.error("", e1);
					vue.getTfTiers().forceFocus();
				}
			}
		});
		
		vue.getTfCodeRelance().addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				try {
					if(vue.getTfCodeRelance().isEnabled()){
						IStatus status=validateUIField(Const.C_CODE_RELANCE,vue.getTfCodeRelance().getText());
						if(status.getSeverity()== IStatus.ERROR){
							MessageDialog.openWarning(vue.getShell(),"Erreur de saisie",status.getMessage());
							throw new Exception(status.getMessage());
						}
					}
				} catch (Exception e1) {
					logger.error("", e1);
					vue.getTfTiers().forceFocus();
				}
			}
		});

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
		
		vue.getBtnTousTiers().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				initTiers();
			}

		});
	}
	private void initTiers() {
		if (vue.getBtnTousTiers().getSelection()){
			vue.getTfTiers().setText("");
		}
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null) 
			mapComposantDecoratedField = new LinkedHashMap<Control,DecoratedField>();
		mapComposantDecoratedField.put(vue.getTfDateDebutPeriode(), vue.getFieldDateDebutPeriode());
		mapComposantDecoratedField.put(vue.getTfDateFinPeriode(), vue.getFieldDateFinPeriode());
		mapComposantDecoratedField.put(vue.getTfTiers(), vue.getFieldTiers());
	}
	protected void initImageBouton() {
		super.initImageBouton();
		vue.getBtnValiderParam().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ACCEPTER));
		vue.getBtnFermer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_FERMER));
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
			else param.setFocusDefautSWT(vue.getTfTiers());

			if(LibDateTime.isDateNull(infos.getDatedebRelInfoEntreprise())){
				MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().
						getShell(),"Date de début de prise en compte des relances",
						"La date de début de prise en compte des relances doit être renseignée"
						+" avant d'utiliser la gestion des relances." +
						"\r\n"+		
						" La description de cette date se fait" +
								" dans l'écran "+"'Dossier/Exercice'");
				try {
					actFermer();
				} catch (Exception e) {
					logger.error("",e);
				}
			}

			if(param instanceof ParamAfficheRelanceFacture){
				if(((ParamAfficheRelanceFacture)param).getIdTiers()!=0) {
					TaTiers tiers=new TaTiersDAO(getEm()).findById(((ParamAfficheRelanceFacture)param).getIdTiers());
					vue.getTfTiers().setText(tiers.getCodeTiers());
				}else vue.getBtnTousTiers().setSelection(true);

				if(((ParamAfficheRelanceFacture)param).getDateDeb()!=null){
					setDateDeb(((ParamAfficheRelanceFacture)param).getDateDeb());
					LibDateTime.setDate(vue.getTfDateDebutPeriode(),getDateDeb());
				}else{
					LibDateTime.setDate(vue.getTfDateDebutPeriode(),infos.getDatedebRelInfoEntreprise());
				}
				if(((ParamAfficheRelanceFacture)param).getDateFin()!=null){
					setDateFin(((ParamAfficheRelanceFacture)param).getDateFin());
					LibDateTime.setDate(vue.getTfDateFinPeriode(),getDateFin());
				}else{
					LibDateTime.setDate(vue.getTfDateFinPeriode(),infos.getDatefinInfoEntreprise());
				}
			}
			bind();
			taRelance=new TaRelance();
			taRelance.setDateDebut(LibDateTime.getDate(vue.getTfDateDebutPeriode()));
			taRelance.setDateFin(LibDateTime.getDate(vue.getTfDateFinPeriode()));
			taRelance.setCodeTiers(vue.getTfTiers().getText());

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



	public void setVue(PaCritereRelanceDocument vue) {
		super.setVue(vue);
		this.vue = vue;
	}


	public PaSelectionLigneRelanceControlleur getPaSelectionLigneRelanceControlleur() {
		return paSelectionLigneRelanceControlleur;
	}

	public void setPaSelectionLigneRelanceControlleur(
			PaSelectionLigneRelanceControlleur paSelectionLigneRelanceControlleur) {
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
			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,true);
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
					if(getFocusAvantAideSWT().equals(vue.getTfTiers())){
						TaTiers entity = null;
						TaTiersDAO dao = new TaTiersDAO(getEm());
						entity = dao.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
						dao = null;
						validateUIField(Const.C_CODE_TIERS,entity.getCodeTiers());
						vue.getTfTiers().setText(entity.getCodeTiers());
					}


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


}

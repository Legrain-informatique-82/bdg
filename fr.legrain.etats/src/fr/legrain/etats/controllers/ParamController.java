package fr.legrain.etats.controllers;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.IHandler;
import org.eclipse.swt.SWT;
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

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.articles.dao.TaFamille;
import fr.legrain.articles.dao.TaFamilleDAO;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.articles.dao.TaUniteDAO;
import fr.legrain.articles.ecran.PaArticleSWT;
import fr.legrain.articles.ecran.PaFamilleSWT;
import fr.legrain.articles.ecran.PaUniteSWT;
import fr.legrain.articles.ecran.ParamAfficheArticles;
import fr.legrain.articles.ecran.ParamAfficheFamille;
import fr.legrain.articles.ecran.ParamAfficheUnite;
import fr.legrain.articles.ecran.SWTPaArticlesController;
import fr.legrain.articles.ecran.SWTPaFamilleController;
import fr.legrain.articles.ecran.SWTPaUniteController;
import fr.legrain.articles.editor.ArticleMultiPageEditor;
import fr.legrain.articles.editor.EditorFamille;
import fr.legrain.articles.editor.EditorInputArticle;
import fr.legrain.articles.editor.EditorInputFamille;
import fr.legrain.articles.editor.EditorInputUnite;
import fr.legrain.articles.editor.EditorUnite;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Articles.SWTArticle;
import fr.legrain.gestCom.Module_Articles.SWTFamille;
import fr.legrain.gestCom.Module_Articles.SWTUnite;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideRechercheSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideRechercheControllerSWT;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
import fr.legrain.lib.data.VerrouInterface;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.lib.gui.aide.PaAideRechercheSWT;
import fr.legrain.lib.gui.aide.PaAideSWT;
import fr.legrain.lib.gui.aide.ParamAfficheAide;



public class ParamController extends JPABaseControllerSWTStandard implements
RetourEcranListener {

	static Logger logger = Logger.getLogger(ParamController.class.getName());		
	private Composite vue = null; // vue
	private TaArticleDAO dao = null;//new TaStockDAO();
	private Object ecranAppelant = null;
	
	private List<Control> listeComposantFocusableParam = null;

//	public ParamController(Composite vue, List<Control> listeComposantFocusableParam) {
//		this(vue/*,null*/);
//		this.listeComposantFocusableParam = listeComposantFocusableParam;
//
//	}

	public ParamController(Composite vue, List<Control> listeComposantFocusableParam) {
		this.listeComposantFocusableParam = listeComposantFocusableParam;
//	public ParamController(Composite vue/*,EntityManager em*/) {
//		if(em!=null) {
//			setEm(em);
//		}
	
		dao = new TaArticleDAO(getEm());
		try {
			setVue(vue);

			this.vue=vue;
			vue.getShell().addShellListener(this);

			//			Branchement action annuler : empeche la fermeture automatique de la
			// fenetre sur ESC
			vue.getShell().addTraverseListener(new Traverse());
//			actionImprimer.setText("Calcul[F3]");

			TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
			TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
			//On récupère la dernière date saisie dans la table des reports de stocks
//			LibDateTime.setDate(vue.getTfDATEDEB(),taInfoEntreprise.getDatedebInfoEntreprise());
//			LibDateTime.setDate(vue.getTfDATEFIN(),taInfoEntreprise.getDatefinInfoEntreprise());

			initController();
			initEtatBoutonCommand();
		} catch (Exception e) {
			logger.error("Erreur ", e);
		}

	}

	private void initController() {
		try {
			setDaoStandard(dao);		

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


		} catch (Exception e) {
			//			vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaArticlesController", e);
		}
	}

	@Override
	protected void initImageBouton() {
		//vue.getPaBtn1().getBtnFermer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_FERMER));
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
			//vue.getGroup1().setText(param.getTitreFormulaire());
			param.setFocus(dao.getModeObjet().getFocusCourant());
		}
		initEtatBoutonCommand();
		return null;
	}

	/**
	 * Initialisation des composants graphiques de la vue.
	 * @throws ExceptLgr 
	 */
	protected void initComposantsVue() throws ExceptLgr {}

	/**
	 * Initialisation des boutons suivant l'état de l'objet "ibTaTable"
	 */
	protected void initEtatBouton() {		
		super.initEtatBouton();
		switch (dao.getModeObjet().getMode()) {
		case C_MO_INSERTION:
			actionImprimer.setEnabled(true);
			actionAnnuler.setEnabled(true);
			actionFermer.setEnabled(true);
			break;
		case C_MO_EDITION:
			actionImprimer.setEnabled(true);
			actionAnnuler.setEnabled(true);
			actionFermer.setEnabled(true);
			break;
		case C_MO_CONSULTATION:
			actionImprimer.setEnabled(true);
			actionAnnuler.setEnabled(true);
			actionFermer.setEnabled(true);
			break;
		default:
			break;
		}
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
//		mapComposantChamps.put(vue.getTfDATEDEB(), Const.C_DATE_DEB);	
//		mapComposantChamps.put(vue.getTfDATEFIN(), Const.C_DATE_FIN);	
//		mapComposantChamps.put(vue.getTfCODE_ARTICLE(), Const.C_CODE_ARTICLE);
//		//		mapComposantChamps.put(vue.getTfCODE_FAMILLE(), Const.C_LIBELLE_STOCK);
//		mapComposantChamps.put(vue.getTfQTE1_STOCK(), Const.C_QTE1_STOCK);
//		mapComposantChamps.put(vue.getTfUN1_STOCK(), Const.C_UN1_STOCK);
//		mapComposantChamps.put(vue.getTfQTE2_STOCK(), Const.C_QTE2_STOCK);
//		mapComposantChamps.put(vue.getTfUN2_STOCK(), Const.C_UN2_STOCK);

		for (Control c : mapComposantChamps.keySet()) {
			listeComposantFocusable.add(c);
		}			
		//		listeComposantFocusable.add(vue.getCbChoix1());
		//		listeComposantFocusable.add(vue.getCbChoix2());

//		listeComposantFocusable.add(vue.getPaBtn1().getBtnImprimer());
//		listeComposantFocusable.add(vue.getPaBtn1().getBtnFermer());
		
		listeComposantFocusable.addAll(listeComposantFocusableParam);

		if(mapInitFocus == null) 
			mapInitFocus = new LinkedHashMap();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION,listeComposantFocusable.get(0));
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION,listeComposantFocusable.get(0));
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION,listeComposantFocusable.get(0));

//		vue.getTfQTE1_STOCK().addVerifyListener(queDesChiffresPositifs);
//		vue.getTfQTE2_STOCK().addVerifyListener(queDesChiffresPositifs);
		
//		vue.getCbChoix1().setSelection(true);
//		vue.getCbChoix2().addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				if(vue.getCbChoix2().getSelection())
//					vue.getCbChoix1().setSelection(false);
//				else vue.getCbChoix1().setSelection(true);
//			}
//		});
//		vue.getCbChoix1().addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				if(vue.getCbChoix1().getSelection())
//					vue.getCbChoix2().setSelection(false);
//				else vue.getCbChoix2().setSelection(true);
//			}
//		});		
	}

	protected void initActions() {
		mapCommand = new HashMap<String, IHandler>();

		mapCommand.put(C_COMMAND_GLOBAL_AIDE_ID, handlerAide);
		mapCommand.put(C_COMMAND_GLOBAL_FERMER_ID, handlerFermer);
		mapCommand.put(C_COMMAND_GLOBAL_IMPRIMER_ID, handlerImprimer);

		initFocusCommand(String.valueOf(this.hashCode()),listeComposantFocusable,mapCommand);

		if (mapActions == null)
			mapActions = new LinkedHashMap<Button, Object>();

//		mapActions.put(vue.getPaBtn1().getBtnFermer(), C_COMMAND_GLOBAL_FERMER_ID);
//		mapActions.put(vue.getPaBtn1().getBtnImprimer(), C_COMMAND_GLOBAL_IMPRIMER_ID);

		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID, C_COMMAND_GLOBAL_REFRESH_ID, C_COMMAND_GLOBAL_SELECTION_ID };
		mapActions.put(null, tabActionsAutres);		
	}

	public ParamController getThis(){
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
					
					for (Control c : listeComposantFocusableParam) {
						if(getFocusAvantAideSWT().equals(c)){
							if(c instanceof Text) {
								((Text)c).setText(((ResultAffiche) evt.getRetour()).getResult());
							}
						}
					}
//					if(getFocusAvantAideSWT().equals(vue.getTfCODE_ARTICLE())){
//						TaArticle f = null;
//						TaArticleDAO t = new TaArticleDAO(getEm());
//						f = t.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
//						t = null;
//						if(f.getTaFamille()!=null)
//							vue.getTfCODE_FAMILLE().setText(f.getTaFamille().getCodeFamille());
//					}	
//					if(getFocusAvantAideSWT().equals(vue.getTfCODE_FAMILLE())){
//						TaFamille f = null;
//						TaFamilleDAO t = new TaFamilleDAO(getEm());
//						f = t.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
//						t = null;
//					}
//					if(getFocusAvantAideSWT().equals(vue.getTfUN1_STOCK())){
//						TaUnite u = null;
//						TaUniteDAO t = new TaUniteDAO(getEm());
//						u = t.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
//						t = null;
//					}
//					if(getFocusAvantAideSWT().equals(vue.getTfUN2_STOCK())){
//						TaUnite u = null;
//						TaUniteDAO t = new TaUniteDAO(getEm());
//						u = t.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
//						t = null;
//
//					}					
					
//					ctrlUnChampsSWT(getFocusAvantAideSWT());
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
//		TaEtatMouvementDms criteres = new TaEtatMouvementDms();
//		if(!vue.getTfCODE_ARTICLE().getText().equals("")){
//			criteres.setCodeArticle(vue.getTfCODE_ARTICLE().getText());
//		}
//		if(!vue.getTfCODE_FAMILLE().getText().equals("")){
//			criteres.setCodeFamille(vue.getTfCODE_FAMILLE().getText());
//		}
//		//if(!vue.getTfDATEDEB().getText().equals("")){
//		criteres.setDateDeb(LibDateTime.getDate(vue.getTfDATEDEB()));
//		//}
//		//if(!vue.getTfDATEFIN().getText().equals("")){
//		criteres.setDateFin(LibDateTime.getDate(vue.getTfDATEFIN()));
//		//}
//		if(!vue.getTfQTE1_STOCK().getText().equals("")){
//			criteres.setQte1(LibConversion.stringToBigDecimal(vue.getTfQTE1_STOCK().getText()));
//		}
//		if(!vue.getTfQTE2_STOCK().getText().equals("")){
//			criteres.setQte2(LibConversion.stringToBigDecimal(vue.getTfQTE2_STOCK().getText()));
//		}
//		if(!vue.getTfUN1_STOCK().getText().equals("")){
//			criteres.setUn1(vue.getTfUN1_STOCK().getText());
//		}
//		if(!vue.getTfUN2_STOCK().getText().equals("")){
//			criteres.setUn2(vue.getTfUN2_STOCK().getText());
//		}
//		criteres.setExclusionQteSsUnite(vue.getCbExclusion().getSelection());
//		//Récupérer les critères de séléction et récupérer les lignes de stocks
//		//en fonction de ces critères
//		EtatFamille etatDms = new EtatFamille();
//		ConstEdition constEdition = new ConstEdition();
//		HashMap<String,String> reportParam = new HashMap<String,String>();
//		reportParam.put("DateDeb",LibDateTime.getDateText(vue.getTfDATEDEB()));
//		reportParam.put("DateFin",LibDateTime.getDateText(vue.getTfDATEFIN()));
//
//
//		List<TaEtatMouvementDms> listeEtat = etatDms.calculEtatArticle(criteres,vue.getCbChoix1().getSelection());
//		Impression impression =new Impression(listeEtat,constEdition);
//		if(vue.getCbChoix1().getSelection()){
//			impression.imprimer(ConstEdition.FICHE_FILE_REPORT_SYNTHESEETATARTICLE,constEdition.makeStringEditionParamtre(reportParam));
//		}else{
//			impression.imprimer(ConstEdition.FICHE_FILE_REPORT_ETATARTICLE,constEdition.makeStringEditionParamtre(reportParam));
//		}
	}

	@Override
	protected void actAide() throws Exception {
		actAide(null);
	}

//	@Override
//	protected void actAide(String message) throws Exception {
//		boolean aide = getActiveAide();
//		if(aideDisponible()){
//			setActiveAide(true);
//			try {
//				VerrouInterface.setVerrouille(true);
//				vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
//				ParamAfficheAideRechercheSWT paramAfficheAideRecherche = new ParamAfficheAideRechercheSWT();
//				paramAfficheAideRecherche.setMessage(message);
//				// Creation de l'ecran d'aide principal
//				Shell s = new Shell(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), LgrShellUtil.styleLgr);
//				PaAideSWT paAide = new PaAideSWT(s, SWT.NONE);
//				SWTPaAideControllerSWT paAideController = new SWTPaAideControllerSWT(paAide);
//				/***************************************************************/
//				LgrPartListener.getLgrPartListener().setLgrActivePart(null);
//				IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
//						openEditor(new EditorInputAide(), EditorAide.ID);
//				LgrPartListener.getLgrPartListener().setLgrActivePart(e);
//				LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
//				paAideController = new SWTPaAideControllerSWT(((EditorAide)e).getComposite());
//				((LgrEditorPart)e).setController(paAideController);
//				((LgrEditorPart)e).setPanel(((EditorAide)e).getComposite());
//				/***************************************************************/
//				JPABaseControllerSWTStandard controllerEcranCreation = null;
//				ParamAffiche parametreEcranCreation = null;
//				IEditorPart editorCreation = null;
//				String editorCreationId = null;
//				IEditorInput editorInputCreation = null;
//				Shell s2 = new Shell(s, LgrShellUtil.styleLgr);
//
//				switch ((ParamController.this.dao.getModeObjet().getMode())) {
//				case C_MO_CONSULTATION:					
//				case C_MO_EDITION:
//				case C_MO_INSERTION:
//					if (getFocusCourantSWT()==vue.getTfCODE_ARTICLE()){
//						PaArticleSWT paArticleSWT = new PaArticleSWT(s2,SWT.NULL);
//						SWTPaArticlesController swtPaArticlesController = new SWTPaArticlesController(paArticleSWT);
//
//						editorCreationId = ArticleMultiPageEditor.ID_EDITOR;
//						editorInputCreation = new EditorInputArticle();
//
//						ParamAfficheArticles paramAfficheArticles = new ParamAfficheArticles();
//						paramAfficheAideRecherche.setJPQLQuery(new TaArticleDAO(getEm()).getJPQLQuery());
//						paramAfficheArticles.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//						paramAfficheArticles.setEcranAppelant(paAideController);
//						controllerEcranCreation = swtPaArticlesController;
//						parametreEcranCreation = paramAfficheArticles;
//
//						paramAfficheAideRecherche.setTypeEntite(TaArticle.class);
//						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_ARTICLE);
//						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_ARTICLE().getText());
//						paramAfficheAideRecherche.setControllerAppelant(getThis());
//						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTArticle,TaArticleDAO,TaArticle>(SWTArticle.class,dao.getEntityManager()));
//						paramAfficheAideRecherche.setTypeObjet(swtPaArticlesController.getClassModel());
//						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_ARTICLE);
//					}	
//
//					if (getFocusCourantSWT()==vue.getTfCODE_FAMILLE()){
//						PaFamilleSWT paFamilleSWT = new PaFamilleSWT(s2,SWT.NULL);
//						SWTPaFamilleController swtPaFamilleController = new SWTPaFamilleController(paFamilleSWT);
//
//						editorCreationId = EditorFamille.ID;
//						editorInputCreation = new EditorInputFamille();
//
//						ParamAfficheFamille paramAfficheArticles = new ParamAfficheFamille();
//						paramAfficheAideRecherche.setJPQLQuery(new TaFamilleDAO(getEm()).getJPQLQuery());
//						paramAfficheArticles.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//						paramAfficheArticles.setEcranAppelant(paAideController);
//						controllerEcranCreation = swtPaFamilleController;
//						parametreEcranCreation = paramAfficheArticles;
//
//						paramAfficheAideRecherche.setTypeEntite(TaFamille.class);
//						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_FAMILLE);
//						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_FAMILLE().getText());
//						paramAfficheAideRecherche.setControllerAppelant(getThis());
//						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTFamille,TaFamilleDAO,TaFamille>(SWTFamille.class,dao.getEntityManager()));
//						paramAfficheAideRecherche.setTypeObjet(swtPaFamilleController.getClassModel());
//						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_FAMILLE);
//					}
//
//					if (getFocusCourantSWT()==vue.getTfUN1_STOCK()||
//							getFocusCourantSWT()==vue.getTfUN2_STOCK()){
//						PaUniteSWT paUniteSWT = new PaUniteSWT(s2,SWT.NULL);
//						SWTPaUniteController swtPaUniteController = new SWTPaUniteController(paUniteSWT);
//
//						editorCreationId = EditorUnite.ID;
//						editorInputCreation = new EditorInputUnite();
//
//						ParamAfficheUnite paramAfficheUnite = new ParamAfficheUnite();
//						paramAfficheUnite.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//						paramAfficheUnite.setEcranAppelant(paAideController);
//						paramAfficheAideRecherche.setJPQLQuery(swtPaUniteController.getDao().getJPQLQuery());
//						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_UNITE);
//
//						paramAfficheAideRecherche.setTypeEntite(TaUnite.class);
//						if(getFocusCourantSWT()==vue.getTfUN1_STOCK())
//							paramAfficheAideRecherche.setDebutRecherche(vue.getTfUN1_STOCK().getText());
//						else
//							paramAfficheAideRecherche.setDebutRecherche(vue.getTfUN2_STOCK().getText());
//						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_UNITE);
//						controllerEcranCreation = swtPaUniteController;
//						parametreEcranCreation = paramAfficheUnite;
//						paramAfficheAideRecherche.setControllerAppelant(getThis());
//						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTUnite,TaUniteDAO,TaUnite>(SWTUnite.class,dao.getEntityManager()));
//						paramAfficheAideRecherche.setTypeObjet(swtPaUniteController.getClassModel());
//					}					
//					break;
//				default:
//					break;
//				}
//				if (paramAfficheAideRecherche.getJPQLQuery() != null) {
//
//					PaAideRechercheSWT paAideRecherche1 = new PaAideRechercheSWT(s,
//							SWT.NULL);
//					SWTPaAideRechercheControllerSWT paAideRechercheController1 = new SWTPaAideRechercheControllerSWT(
//							paAideRecherche1);
//
//					// Parametrage de la recherche
//					paramAfficheAideRecherche.setFocusSWT(((PaAideRechercheSWT) paAideRechercheController1.getVue()).getTfChoix());
//					paramAfficheAideRecherche.setRefCreationSWT(controllerEcranCreation);
//					paramAfficheAideRecherche.setEditorCreation(editorCreation);
//					paramAfficheAideRecherche.setEditorCreationId(editorCreationId);
//					paramAfficheAideRecherche.setEditorInputCreation(editorInputCreation);
//					paramAfficheAideRecherche.setParamEcranCreation(parametreEcranCreation);
//					paramAfficheAideRecherche.setShellCreation(s2);
//					paAideRechercheController1.configPanel(paramAfficheAideRecherche);
//
//					// Ajout d'une recherche
//					paAideController.addRecherche(paAideRechercheController1,
//							paramAfficheAideRecherche.getTitreRecherche());
//
//					// Parametrage de l'ecran d'aide principal
//					ParamAfficheAideSWT paramAfficheAideSWT = new ParamAfficheAideSWT();
//					ParamAfficheAide paramAfficheAide = new ParamAfficheAide();
//
//					// enregistrement pour le retour de l'ecran d'aide
//					paAideController.addRetourEcranListener(ParamController.this);
//					Control focus = vue.getShell().getDisplay().getFocusControl();
//					// affichage de l'ecran d'aide principal (+ ses recherches)
//
//
//					/*****************************************************************/
//					paAideController.configPanel(paramAfficheAide);
//					/*****************************************************************/
//
//				}
//
//			} finally {
//				VerrouInterface.setVerrouille(false);
//				vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
//			}
//		}
//	}

	@Override
	protected void actEnregistrer() throws Exception{}
	@Override
	public void initEtatComposant() {}
	@Override
	protected void actRefresh() throws Exception {}
	@Override
	protected void initMapComposantDecoratedField() {}
	@Override
	protected void sortieChamps() {}
	
	@Override
	protected void initEtatBoutonCommand() {
		enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,true);
		enableActionAndHandler(C_COMMAND_GLOBAL_FERMER_ID,true);
		enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,true);
	}

	@Override
	protected boolean aideDisponible() {
		//boolean result = false;
		boolean result = true;
//		switch ((getThis().dao.getModeObjet().getMode())) {
//		case C_MO_CONSULTATION:
//		case C_MO_EDITION:
//		case C_MO_INSERTION:
//			if(getFocusCourantSWT().equals(vue.getTfCODE_ARTICLE()))
//				result = true;
//			if(getFocusCourantSWT().equals(vue.getTfCODE_FAMILLE()))
//				result = true;
//			if(getFocusCourantSWT().equals(vue.getTfUN1_STOCK()))
//				result = true;
//			if(getFocusCourantSWT().equals(vue.getTfUN2_STOCK()))
//				result = true;
//			break;
//		default:
//			break;
//		}
		return result;
	}

	@Override
	protected void actAide(String message) throws Exception {
		// TODO Auto-generated method stub
		
	}
}

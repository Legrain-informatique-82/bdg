package fr.legrain.etats.defaut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.articles.dao.TaFamille;
import fr.legrain.articles.dao.TaFamilleDAO;
import fr.legrain.articles.dao.TaTva;
import fr.legrain.articles.dao.TaTvaDAO;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.articles.dao.TaUniteDAO;
import fr.legrain.articles.ecran.PaArticleSWT;
import fr.legrain.articles.ecran.PaFamilleSWT;
import fr.legrain.articles.ecran.PaTVASWT;
import fr.legrain.articles.ecran.PaUniteSWT;
import fr.legrain.articles.ecran.ParamAfficheArticles;
import fr.legrain.articles.ecran.ParamAfficheFamille;
import fr.legrain.articles.ecran.ParamAfficheTva;
import fr.legrain.articles.ecran.ParamAfficheUnite;
import fr.legrain.articles.ecran.SWTPaArticlesController;
import fr.legrain.articles.ecran.SWTPaFamilleController;
import fr.legrain.articles.ecran.SWTPaTvaController;
import fr.legrain.articles.ecran.SWTPaUniteController;
import fr.legrain.articles.editor.ArticleMultiPageEditor;
import fr.legrain.articles.editor.EditorFamille;
import fr.legrain.articles.editor.EditorInputArticle;
import fr.legrain.articles.editor.EditorInputFamille;
import fr.legrain.articles.editor.EditorInputTva;
import fr.legrain.articles.editor.EditorInputUnite;
import fr.legrain.articles.editor.EditorTva;
import fr.legrain.articles.editor.EditorUnite;
import fr.legrain.dms.dao.TaEtatMouvementDms;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.etats.Activator;
import fr.legrain.etats.controllers.Etat;
import fr.legrain.etats.controllers.Impression;
import fr.legrain.etats.controllers.Param;
import fr.legrain.etats.controllers.ParamController;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Articles.SWTArticle;
import fr.legrain.gestCom.Module_Articles.SWTFamille;
import fr.legrain.gestCom.Module_Articles.SWTTva;
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
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.LibDate;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
import fr.legrain.lib.data.VerrouInterface;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.aide.PaAideRechercheSWT;
import fr.legrain.lib.gui.aide.PaAideSWT;
import fr.legrain.lib.gui.aide.ParamAfficheAide;

public class EtatArticle extends Etat {
	
	static Logger logger = Logger.getLogger(EtatArticle.class);

	private String paramNomDateDebut = "p1";
	private String paramNomDateFin = "p2";
	private String paramNomCodeArticle = "p3";
	private String paramNomCodeArticleFin = "p4";
	private String paramNomCodeFamille = "p5";
	private String paramNomCodeFamilleFin = "p6";
	private String paramNomCodeUnite1 = "p7";
	private String paramNomCodeUnite2 = "p8";
	//	private String paramNomExclureQteSsUnite = "p9";
	private String paramNomRegroupementUnite = "p10";
	private String paramNomLocalisationTVAFr = "p11";
	private String paramNomLocalisationTVAUE = "p12";
	private String paramNomLocalisationTVAHUE = "p13";
	private String paramNomLocalisationTVAFranchise = "p14";
	private String paramNomLocalisationSansTVA = "p17";
	private String paramNomTauxTVA = "p15";
	private String paramNomType = "p16";
	private String paramNomTypeValeurSynhtese = "0_synthese";
	private String paramNomTypeValeurDetail = "1_detail";

	private String paramNomRegroupementValeurU1 = "0_U1";
	private String paramNomRegroupementValeurU2 = "1_U2";
	private String paramNomRegroupementValeurU1etU2 = "1_U1U2";
	
	private String paramNomEditionSynthese = "Synthèse des ventes";
	private String paramNomEditionDetail = "Détail des ventes";

	private boolean synthese = true;

	private boolean travailSurDateLivraison = false;
	
	
	public EtatArticle(boolean travailSurDateLivraison) {
		super();
		this.travailSurDateLivraison = travailSurDateLivraison;
	}

	//	private String cheminEditionSynthese = "/donnees/Projet/Java/Eclipse/GestionCommerciale/fr.legrain.gestionStatistiques/report/EtatSyntheseArticle.rptdesign";
	//	private String cheminEditionDetail = "/donnees/Projet/Java/Eclipse/GestionCommerciale/fr.legrain.gestionStatistiques/report/EtatArticle.rptdesign";

	private String cheminEditionSynthese = "/report/defaut/EtatSyntheseArticle.rptdesign";
	private String cheminEditionDetail = "/report/defaut/EtatArticle.rptdesign";

	private String cheminEditionDetailLivraison = "/report/defaut/EtatArticleLivraison.rptdesign";
	
	private Param paramDateDeb = null;
	private Param paramDateFin = null;
	private Param paramCodeArticle = null;
	private Param paramCodeArticleFin = null;
	private Param paramCodeFamille = null;
	private Param paramCodeFamilleFin = null;
	private Param paramCodeUnite1 = null;
	private Param paramCodeUnite2 = null;
	//	private Param paramExclureQteSsUnite = null;
	private Param paramRegroupementUnite = null;
	private Param paramLocalisationTVAFr = null;
	private Param paramLocalisationTVAUE = null;
	private Param paramLocalisationTVAHUE = null;
	private Param paramLocalisationTVAFranchise = null;
	private Param paramLocalisationSansTVA = null;
	private Param paramTauxTVA = null;
	private Param paramType = null;
	/*
	 * Liste param final, map string clé valeur
	 * 
	 * méthode pour créer l'ihm des param, soit auto + manuel, soit manuel
	 */
	@Override
	public LinkedHashMap<String,LinkedList<Param>> description() {
		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO();
		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
		if(travailSurDateLivraison){
			paramDateDeb = new Param(EtatArticle.TYPE_PARAM_DATE, paramNomDateDebut, "Date début Livraison", null, null, null, taInfoEntreprise.getDatedebInfoEntreprise(), 1, false);
			paramDateFin = new Param(EtatArticle.TYPE_PARAM_DATE, paramNomDateFin, "Date fin Livraison", null, null, null, taInfoEntreprise.getDatefinInfoEntreprise(), 1, false);
		}else{
			paramDateDeb = new Param(EtatArticle.TYPE_PARAM_DATE, paramNomDateDebut, "Date début", null, null, null, taInfoEntreprise.getDatedebInfoEntreprise(), 1, false);
			paramDateFin = new Param(EtatArticle.TYPE_PARAM_DATE, paramNomDateFin, "Date fin", null, null, null, taInfoEntreprise.getDatefinInfoEntreprise(), 1, false);
		}

		paramCodeArticle = new Param(EtatArticle.TYPE_PARAM_TEXT, paramNomCodeArticle, "Code article", null, null, "", null, 1, false);
		paramCodeArticleFin = new Param(EtatArticle.TYPE_PARAM_TEXT, paramNomCodeArticleFin, "à", null, null, "", null, 1, false);
		paramCodeFamille = new Param(EtatArticle.TYPE_PARAM_TEXT, paramNomCodeFamille, "Code Famille", null, null, "", null, 1, false);
		paramCodeFamilleFin = new Param(EtatArticle.TYPE_PARAM_TEXT, paramNomCodeFamilleFin, "à", null, null, "", null, 1, false);
		paramCodeUnite1 = new Param(EtatArticle.TYPE_PARAM_TEXT, paramNomCodeUnite1, "Unité 1", null, null, "", null, 1, true);
		paramCodeUnite2 = new Param(EtatArticle.TYPE_PARAM_TEXT, paramNomCodeUnite2, "Unité 2", null, null, "", null, 1, true);
		//		paramExclureQteSsUnite = new Param(EtatArticle.TYPE_PARAM_BOOLEAN, paramNomExclureQteSsUnite, "Exclure Qté sans unité", null, null, "", null, 1, true);

		paramRegroupementUnite = new Param(EtatArticle.TYPE_PARAM_LIST, paramNomRegroupementUnite, "", null, 
				new String[][]{
				{"Distinction sur U1",paramNomRegroupementValeurU1},
				{"Distinction sur U2",paramNomRegroupementValeurU2}
		}, 
		"defaut", null, 1, true);

		paramLocalisationTVAFr = new Param(EtatArticle.TYPE_PARAM_BOOLEAN, paramNomLocalisationTVAFr, "Vente en France", null, null, "", null, 1, true);
		paramLocalisationTVAUE = new Param(EtatArticle.TYPE_PARAM_BOOLEAN, paramNomLocalisationTVAUE, "Vente UE", null, null, "", null, 1, true);
		paramLocalisationTVAHUE = new Param(EtatArticle.TYPE_PARAM_BOOLEAN, paramNomLocalisationTVAHUE, "Vente Hors UE", null, null, "", null, 1, true);
		paramLocalisationTVAFranchise = new Param(EtatArticle.TYPE_PARAM_BOOLEAN, paramNomLocalisationTVAFranchise, "Vente franchise TVA", null, null, "", null, 1, true);
		paramLocalisationSansTVA = new Param(EtatArticle.TYPE_PARAM_BOOLEAN, paramNomLocalisationSansTVA, "Vente sans TVA", null, null, "", null, 1, true);

		paramTauxTVA = new Param(EtatArticle.TYPE_PARAM_TEXT, paramNomTauxTVA, "Code TVA", null, null, "", null, 1, true);

		paramType = new Param(EtatArticle.TYPE_PARAM_LIST, paramNomType, "", null, 
				new String[][]{{paramNomEditionSynthese,paramNomTypeValeurSynhtese},{paramNomEditionDetail,paramNomTypeValeurDetail}}, 
				"defaut", null, 1, true);

		LinkedHashMap<String,LinkedList<Param>> l = new LinkedHashMap<String,LinkedList<Param>>();
		
		LinkedList<Param> l1 = new LinkedList<Param>();
		l1.add(paramDateDeb);
		l1.add(paramDateFin);
		l.put("Période", l1);
		LinkedList<Param> l2 = new LinkedList<Param>();
		l2.add(paramCodeArticle);
		l2.add(paramCodeArticleFin);
		l2.add(paramCodeFamille);
		l2.add(paramCodeFamilleFin);
		l2.add(paramCodeUnite1);
		l2.add(paramCodeUnite2);
		//		l.add(paramExclureQteSsUnite);
		l2.add(paramRegroupementUnite);
		l.put("", l2);
		LinkedList<Param> l3 = new LinkedList<Param>();
		l3.add(paramLocalisationTVAFr);
		l3.add(paramLocalisationTVAUE);
		l3.add(paramLocalisationTVAHUE);
		l3.add(paramLocalisationTVAFranchise);
		l3.add(paramLocalisationSansTVA);
		l3.add(paramTauxTVA);
		l.put("TVA", l3);
		LinkedList<Param> l4 = new LinkedList<Param>();
		l4.add(paramType);
		l.put("Edition", l4);
		return l;
	}

	/**
	 * methode (point d'entrée) pour différents calculs pour obtenir les données
	 * @param reportParam
	 * @return
	 */
	@Override
	public List<Object> calcul(/*HashMap<String,String> reportParam*/) {
		List<Object> l = null;
		try {
			HashMap<String,String> reportParam = getParamValue();

			TaEtatMouvementDms criteres = new TaEtatMouvementDms();
			if(!reportParam.get(paramNomCodeArticle).equals("")){
				criteres.setCodeArticle(reportParam.get(paramNomCodeArticle));
			}
			if(!reportParam.get(paramNomCodeArticleFin).equals("")){
				criteres.setCodeArticleFin(reportParam.get(paramNomCodeArticleFin));
			}
			if(!reportParam.get(paramNomCodeFamille).equals("")){
				criteres.setCodeFamille(reportParam.get(paramNomCodeFamille));
			}
			if(!reportParam.get(paramNomCodeFamilleFin).equals("")){
				criteres.setCodeFamilleFin(reportParam.get(paramNomCodeFamilleFin));
			}
			criteres.setDateDeb(LibDate.stringToDate(reportParam.get(paramNomDateDebut)));

			criteres.setDateFin(LibDate.stringToDate(reportParam.get(paramNomDateFin)));
			criteres.setTravailSurDateLivraison(travailSurDateLivraison);
			/*
			 ALTER TABLE TA_INFOS_FACTURE ADD CODE_T_TVA_DOC DLGR_CODEL;
			 */
			criteres.setLocalisationTVAFr(LibConversion.StringToBoolean(reportParam.get(paramNomLocalisationTVAFr)));
			criteres.setLocalisationTVAUE(LibConversion.StringToBoolean(reportParam.get(paramNomLocalisationTVAUE)));
			criteres.setLocalisationTVAHUE(LibConversion.StringToBoolean(reportParam.get(paramNomLocalisationTVAHUE)));
			criteres.setLocalisationTVAFranchise(LibConversion.StringToBoolean(reportParam.get(paramNomLocalisationTVAFranchise)));
			criteres.setLocalisationSansTVA(LibConversion.StringToBoolean(reportParam.get(paramNomLocalisationSansTVA)));
			
			criteres.setCodeTauxTVA(reportParam.get(paramNomTauxTVA));
			
			if(!reportParam.get(paramNomCodeUnite1).equals("")){
				criteres.setUn1(reportParam.get(paramNomCodeUnite1));
			}
			if(!reportParam.get(paramNomCodeUnite2).equals("")){
				criteres.setUn2(reportParam.get(paramNomCodeUnite2));
			} 
			//criteres.setExclusionQteSsUnite(LibConversion.StringToBoolean(reportParam.get(paramNomExclureQteSsUnite)));

			//Récupérer les critères de séléction et récupérer les lignes de stocks
			//en fonction de ces critères
			CalculsEtatFamilleEtArticle etatDms = new CalculsEtatFamilleEtArticle();

			synthese = true;
			if(reportParam.get(paramNomType).equals(paramNomTypeValeurDetail)) {
				synthese = false;
			}

			List<TaEtatMouvementDms> listeEtat = etatDms.calculEtatArticle(criteres,synthese);
			l = new ArrayList<Object>();
			for (TaEtatMouvementDms t : listeEtat) {
				l.add(t);
			}
			//			Impression impression =new Impression(l,constEdition);
			//			if(synthese){
			//				impression.imprimer(ConstEdition.FICHE_FILE_REPORT_SYNTHESEETATARTICLE,constEdition.makeStringEditionParamtre(reportParam2));
			//			}else{
			//				impression.imprimer(ConstEdition.FICHE_FILE_REPORT_ETATARTICLE,constEdition.makeStringEditionParamtre(reportParam2));
			//			}
		} catch(Exception e) {
			logger.error("",e);
		}
		return l;
	}

	/**
	 * méthode pour appeler l'édition BIRT
	 * @param valeur
	 * @param reportParam
	 */
	@Override
	public void edit(List<Object> valeur, HashMap<String,String> reportParam, Boolean extraction) {
		ConstEdition constEdition = new ConstEdition();


		HashMap<String,String> reportParamBirt = new HashMap<String,String>();
		reportParamBirt.put("DateDeb",reportParam.get(paramNomDateDebut));
		reportParamBirt.put("DateFin",reportParam.get(paramNomDateFin));
		if(reportParam.get(paramNomRegroupementUnite).equals(paramNomRegroupementValeurU1)) {
			reportParamBirt.put("groupeUnite","u1");
		} else {
			reportParamBirt.put("groupeUnite","u2");
		}
		reportParamBirt.put("listeParam",getDescriptionParam());

		List<Object> listeObjet = null;
		if(valeur==null) {
			listeObjet = calcul(/*reportParam*/);
		} else {
			listeObjet = valeur;
		}

		System.err.println(getDescriptionParam());
		Impression impression =new Impression(listeObjet,constEdition);
		impression.setExtraction(extraction);
		if(extraction) {
			CalculsEtatFamilleEtArticle a = new CalculsEtatFamilleEtArticle();
			a.exportCSVObject(listeObjet);
		} else {
			impression.imprimer(getEditionFilePath(),getNomOngletEdition(),constEdition.makeStringEditionParamtre(reportParamBirt));
		}
	}

	@Override
	public String getName() {
		return "Ventes (et avoirs) par article";
	}

	@Override
	public String getDescription() {
		return "Ventes (et avoirs) par article";
	}

	@Override
	public Image getIcon() {
//		if(!this.travailSurDateLivraison)
//			return Activator.getImageDescriptor("/icons/boutons/picto_75px/picto_art_75px.png").createImage();
//		else 
//			return Activator.getImageDescriptor("/icons/boutons/picto_75px/picto_art2_75px.bmp").createImage();
		if(!this.travailSurDateLivraison)
			return Activator.getImageDescriptor("/icons/boutons/picto_art_75px-date-fact.png").createImage();
		else 
			return Activator.getImageDescriptor("/icons/boutons/picto_art_75px-date-livr.png").createImage();
	}

	@Override
	public String getEditionFilePath() {
		//return ConstEdition.FICHE_FILE_REPORT_ETATFAMILLE;
		if(synthese)
			return cheminEditionSynthese;
		else
			if(travailSurDateLivraison)return cheminEditionDetailLivraison;
			else	return cheminEditionDetail;
	}
	
	@Override
	public String getNomOngletEdition() {
		if(synthese)
			return paramNomEditionSynthese;
		else
			return paramNomEditionDetail;
	}

	@Override
	public Map<String, String> getParamEditonURL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addIHMAction() {

		List<Control> listeComposantFocusableParam = new ArrayList<Control>();
		//		listeComposantFocusableParam.add(paramCodeArticle.control.get(0));
		//		listeComposantFocusableParam.add(paramCodeFamille.control.get(0));
		//		listeComposantFocusableParam.add(paramCodeUnite1.control.get(0));
		//		listeComposantFocusableParam.add(paramCodeUnite2.control.get(0));
		listeComposantFocusableParam = listeControlParam;

		final Composite c = paramCodeArticle.control.get(0).getParent();

		paramController = new ParamController(c,listeComposantFocusableParam) {

			@Override
			protected boolean aideDisponible() {
				boolean result = false;
				//				boolean result = true;
				//				switch ((getThis().dao.getModeObjet().getMode())) {
				//				case C_MO_CONSULTATION:
				//				case C_MO_EDITION:
				//				case C_MO_INSERTION:
				if(getFocusCourantSWT().equals(paramCodeArticle.control.get(0))
						|| getFocusCourantSWT().equals(paramCodeArticleFin.control.get(0))
						|| getFocusCourantSWT().equals(paramCodeFamille.control.get(0))
						|| getFocusCourantSWT().equals(paramCodeFamilleFin.control.get(0))
						|| getFocusCourantSWT().equals(paramCodeUnite1.control.get(0))
						|| getFocusCourantSWT().equals(paramCodeUnite2.control.get(0))
						|| getFocusCourantSWT().equals(paramTauxTVA.control.get(0))
						)
					result = true;
				//					if(getFocusCourantSWT().equals(vue.getTfCODE_FAMILLE()))
				//						result = true;
				//					if(getFocusCourantSWT().equals(vue.getTfUN1_STOCK()))
				//						result = true;
				//					if(getFocusCourantSWT().equals(vue.getTfUN2_STOCK()))
				//						result = true;
				//					break;
				//				default:
				//					break;
				//				}
				return result;
			}

			@Override
			protected void actAide(String message) throws Exception {
				boolean aide = getActiveAide();
				if(aideDisponible()){
					setActiveAide(true);
					try {
						VerrouInterface.setVerrouille(true);
						c.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
						ParamAfficheAideRechercheSWT paramAfficheAideRecherche = new ParamAfficheAideRechercheSWT();
						paramAfficheAideRecherche.setMessage(message);
						// Creation de l'ecran d'aide principal
						Shell s = new Shell(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), LgrShellUtil.styleLgr);
						PaAideSWT paAide = new PaAideSWT(s, SWT.NONE);
						SWTPaAideControllerSWT paAideController = new SWTPaAideControllerSWT(paAide);
						/***************************************************************/
						LgrPartListener.getLgrPartListener().setLgrActivePart(null);
						IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
								openEditor(new EditorInputAide(), EditorAide.ID);
						LgrPartListener.getLgrPartListener().setLgrActivePart(e);
						LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
						paAideController = new SWTPaAideControllerSWT(((EditorAide)e).getComposite());
						((LgrEditorPart)e).setController(paAideController);
						((LgrEditorPart)e).setPanel(((EditorAide)e).getComposite());
						/***************************************************************/
						JPABaseControllerSWTStandard controllerEcranCreation = null;
						ParamAffiche parametreEcranCreation = null;
						IEditorPart editorCreation = null;
						String editorCreationId = null;
						IEditorInput editorInputCreation = null;
						Shell s2 = new Shell(s, LgrShellUtil.styleLgr);

						//switch ((ParamController.this.dao.getModeObjet().getMode())) {
						//case C_MO_CONSULTATION:					
						//case C_MO_EDITION:
						//case C_MO_INSERTION:
						if (getFocusCourantSWT()==paramCodeArticle.control.get(0)
								|| getFocusCourantSWT()==paramCodeArticleFin.control.get(0)	){
							PaArticleSWT paArticleSWT = new PaArticleSWT(s2,SWT.NULL);
							SWTPaArticlesController swtPaArticlesController = new SWTPaArticlesController(paArticleSWT);
							paramAfficheAideRecherche.setForceAffichageAideRemplie(false);

							editorCreationId = ArticleMultiPageEditor.ID_EDITOR;
							editorInputCreation = new EditorInputArticle();

							ParamAfficheArticles paramAfficheArticles = new ParamAfficheArticles();
							paramAfficheAideRecherche.setJPQLQuery(new TaArticleDAO(getEm()).getJPQLQuery());
							paramAfficheArticles.setModeEcran(EnumModeObjet.C_MO_INSERTION);
							paramAfficheArticles.setEcranAppelant(paAideController);
							controllerEcranCreation = swtPaArticlesController;
							parametreEcranCreation = paramAfficheArticles;

							paramAfficheAideRecherche.setTypeEntite(TaArticle.class);
							paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_ARTICLE);
							//paramAfficheAideRecherche.setDebutRecherche((Text)p3.control.get(0)).getText());
							paramAfficheAideRecherche.setDebutRecherche("");
							paramAfficheAideRecherche.setControllerAppelant(getThis());
							//paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTArticle,TaArticleDAO,TaArticle>(SWTArticle.class,dao.getEntityManager()));
							paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTArticle,TaArticleDAO,TaArticle>(new TaArticleDAO(), SWTArticle.class));
							paramAfficheAideRecherche.setTypeObjet(swtPaArticlesController.getClassModel());
							paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_ARTICLE);
						}	

						else if (getFocusCourantSWT()==paramCodeFamille.control.get(0)
								|| getFocusCourantSWT()==paramCodeFamilleFin.control.get(0)	){
							PaFamilleSWT paFamilleSWT = new PaFamilleSWT(s2,SWT.NULL);
							SWTPaFamilleController swtPaFamilleController = new SWTPaFamilleController(paFamilleSWT);

							editorCreationId = EditorFamille.ID;
							editorInputCreation = new EditorInputFamille();

							ParamAfficheFamille paramAfficheArticles = new ParamAfficheFamille();
							paramAfficheAideRecherche.setJPQLQuery(new TaFamilleDAO(getEm()).getJPQLQuery());
							paramAfficheArticles.setModeEcran(EnumModeObjet.C_MO_INSERTION);
							paramAfficheArticles.setEcranAppelant(paAideController);
							controllerEcranCreation = swtPaFamilleController;
							parametreEcranCreation = paramAfficheArticles;

							paramAfficheAideRecherche.setTypeEntite(TaFamille.class);
							paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_FAMILLE);
							//paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_FAMILLE().getText());
							paramAfficheAideRecherche.setDebutRecherche("");
							paramAfficheAideRecherche.setControllerAppelant(getThis());
							//paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTFamille,TaFamilleDAO,TaFamille>(SWTFamille.class,dao.getEntityManager()));
							paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTFamille,TaFamilleDAO,TaFamille>(new TaFamilleDAO(), SWTFamille.class));
							paramAfficheAideRecherche.setTypeObjet(swtPaFamilleController.getClassModel());
							paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_FAMILLE);
						}

						else if (getFocusCourantSWT()==paramCodeUnite1.control.get(0)
								|| getFocusCourantSWT()==paramCodeUnite2.control.get(0)	){
							PaUniteSWT paUniteSWT = new PaUniteSWT(s2,SWT.NULL);
							SWTPaUniteController swtPaUniteController = new SWTPaUniteController(paUniteSWT);

							editorCreationId = EditorUnite.ID;
							editorInputCreation = new EditorInputUnite();

							ParamAfficheUnite paramAfficheUnite = new ParamAfficheUnite();
							paramAfficheUnite.setModeEcran(EnumModeObjet.C_MO_INSERTION);
							paramAfficheUnite.setEcranAppelant(paAideController);
							paramAfficheAideRecherche.setJPQLQuery(swtPaUniteController.getDao().getJPQLQuery());
							paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_UNITE);

							paramAfficheAideRecherche.setTypeEntite(TaUnite.class);
							if(getFocusCourantSWT()==paramCodeUnite1.control.get(0))
								paramAfficheAideRecherche.setDebutRecherche(((Text)paramCodeUnite1.control.get(0)).getText());
							else
								paramAfficheAideRecherche.setDebutRecherche(((Text)paramCodeUnite2.control.get(0)).getText());
							paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_UNITE);
							controllerEcranCreation = swtPaUniteController;
							parametreEcranCreation = paramAfficheUnite;
							paramAfficheAideRecherche.setControllerAppelant(getThis());
							//paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTUnite,TaUniteDAO,TaUnite>(SWTUnite.class,dao.getEntityManager()));
							paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTUnite,TaUniteDAO,TaUnite>(new TaUniteDAO(), SWTUnite.class));
							paramAfficheAideRecherche.setTypeObjet(swtPaUniteController.getClassModel());
						}	
						
						else if (getFocusCourantSWT()==paramTauxTVA.control.get(0)){
							PaTVASWT paTVASWT = new PaTVASWT(s2,SWT.NULL);
							SWTPaTvaController swtPaTvaController = new SWTPaTvaController(paTVASWT);

							editorCreationId = EditorTva.ID;
							editorInputCreation = new EditorInputTva();

							ParamAfficheTva paramAfficheTva = new ParamAfficheTva();
							paramAfficheTva.setModeEcran(EnumModeObjet.C_MO_INSERTION);
							paramAfficheTva.setEcranAppelant(paAideController);
							paramAfficheAideRecherche.setJPQLQuery(swtPaTvaController.getDao().getJPQLQuery());
							paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_TVA);

							paramAfficheAideRecherche.setTypeEntite(TaTva.class);
							paramAfficheAideRecherche.setDebutRecherche(((Text)paramTauxTVA.control.get(0)).getText());
							paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_TVA);
							controllerEcranCreation = swtPaTvaController;
							parametreEcranCreation = paramAfficheTva;
							paramAfficheAideRecherche.setControllerAppelant(getThis());
							//paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTUnite,TaUniteDAO,TaUnite>(SWTUnite.class,dao.getEntityManager()));
							paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTva,TaTvaDAO,TaTva>(new TaTvaDAO(), SWTTva.class));
							paramAfficheAideRecherche.setTypeObjet(swtPaTvaController.getClassModel());
						}

						//break;
						//default:
						//break;
						//}
						if (paramAfficheAideRecherche.getJPQLQuery() != null) {

							PaAideRechercheSWT paAideRecherche1 = new PaAideRechercheSWT(s,
									SWT.NULL);
							SWTPaAideRechercheControllerSWT paAideRechercheController1 = new SWTPaAideRechercheControllerSWT(
									paAideRecherche1);

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
							paAideController.addRecherche(paAideRechercheController1,
									paramAfficheAideRecherche.getTitreRecherche());

							// Parametrage de l'ecran d'aide principal
							ParamAfficheAideSWT paramAfficheAideSWT = new ParamAfficheAideSWT();
							ParamAfficheAide paramAfficheAide = new ParamAfficheAide();

							// enregistrement pour le retour de l'ecran d'aide
							//paAideController.addRetourEcranListener(ParamController.this);
							paAideController.addRetourEcranListener(this);
							Control focus = c.getShell().getDisplay().getFocusControl();
							// affichage de l'ecran d'aide principal (+ ses recherches)


							/*****************************************************************/
							paAideController.configPanel(paramAfficheAide);
							/*****************************************************************/

						}

					} finally {
						VerrouInterface.setVerrouille(false);
						c.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
					}
				}
			}
		};
	}

	@Override
	public String getDescriptionParam() {
		HashMap<String,String> reportParam = getParamValue();
		String desc = "";
		if(reportParam.get(paramNomCodeArticle)!=null && !reportParam.get(paramNomCodeArticle).equals("")) {
			if(reportParam.get(paramNomCodeArticleFin)!=null && !reportParam.get(paramNomCodeArticleFin).equals("")) {
				desc += "De l'article '"+reportParam.get(paramNomCodeArticle)+"' à l'article '"+reportParam.get(paramNomCodeArticleFin)+"'";
			} else {
				desc += "Article '"+reportParam.get(paramNomCodeArticle)+"'";
			}
		}
		
		
		if(reportParam.get(paramNomCodeFamille)!=null && !reportParam.get(paramNomCodeFamille).equals("")) {
			if(!desc.equals("")) desc+=", ";
			if(reportParam.get(paramNomCodeFamilleFin)!=null && !reportParam.get(paramNomCodeFamilleFin).equals("")) {
				desc += "De la famille '"+reportParam.get(paramNomCodeFamille)+"' à la famille '"+reportParam.get(paramNomCodeFamilleFin)+"'";
			} else {
				desc += "Famille '"+reportParam.get(paramNomCodeFamille)+"'";
			}
		}
		
		if(reportParam.get(paramNomCodeUnite1)!=null && !reportParam.get(paramNomCodeUnite1).equals("")) {
			if(!desc.equals("")) desc+=", ";
			desc += "U1 '"+reportParam.get(paramNomCodeUnite1)+"'";
		}
		
		if(reportParam.get(paramNomCodeUnite2)!=null && !reportParam.get(paramNomCodeUnite2).equals("")) {
			if(!desc.equals("")) desc+=", ";
			desc += "U2 '"+reportParam.get(paramNomCodeUnite2)+"'";
		}
		
		if(reportParam.get(paramNomTauxTVA)!=null && !reportParam.get(paramNomTauxTVA).equals("")) {
			if(!desc.equals("")) desc+=", ";
			desc += "Taux TVA '"+reportParam.get(paramNomTauxTVA)+"'";
		}
		
		if(!LibConversion.StringToBoolean(reportParam.get(paramNomLocalisationTVAFr))
				|| !LibConversion.StringToBoolean(reportParam.get(paramNomLocalisationTVAUE))
				|| !LibConversion.StringToBoolean(reportParam.get(paramNomLocalisationTVAHUE))
				|| !LibConversion.StringToBoolean(reportParam.get(paramNomLocalisationTVAFranchise))
				|| !LibConversion.StringToBoolean(reportParam.get(paramNomLocalisationSansTVA))
				) {

			String tva = "";
			if(LibConversion.StringToBoolean(reportParam.get(paramNomLocalisationTVAFr))) {
				if(!tva.equals("")) tva+=", ";
				tva+= "France";
			}
			if(LibConversion.StringToBoolean(reportParam.get(paramNomLocalisationTVAUE))) {
				if(!tva.equals("")) tva+=", ";
				tva+= "UE";
			}
			if(LibConversion.StringToBoolean(reportParam.get(paramNomLocalisationTVAHUE))) {
				if(!tva.equals("")) tva+=", ";
				tva+= "HUE";
			}
			if(LibConversion.StringToBoolean(reportParam.get(paramNomLocalisationTVAFranchise))) {
				if(!tva.equals("")) tva+=", ";
				tva+= "Franchise";
			}
			if(LibConversion.StringToBoolean(reportParam.get(paramNomLocalisationSansTVA))) {
				if(!tva.equals("")) tva+=", ";
				tva+= "Sans tva";
			}			
			if(tva!=null) {
				if(!desc.equals("")) desc+=", ";
				desc += "Localisation TVA : '"+tva+"'";
			}
		}
			
		return desc;
	}


}

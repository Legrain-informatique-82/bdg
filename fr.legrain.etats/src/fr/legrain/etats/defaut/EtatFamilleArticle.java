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

import fr.legrain.articles.dao.TaFamille;
import fr.legrain.articles.dao.TaFamilleDAO;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.articles.dao.TaUniteDAO;
import fr.legrain.articles.ecran.PaFamilleSWT;
import fr.legrain.articles.ecran.PaUniteSWT;
import fr.legrain.articles.ecran.ParamAfficheFamille;
import fr.legrain.articles.ecran.ParamAfficheUnite;
import fr.legrain.articles.ecran.SWTPaFamilleController;
import fr.legrain.articles.ecran.SWTPaUniteController;
import fr.legrain.articles.editor.EditorFamille;
import fr.legrain.articles.editor.EditorInputFamille;
import fr.legrain.articles.editor.EditorInputUnite;
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
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.LibDate;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
import fr.legrain.lib.data.VerrouInterface;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.aide.PaAideRechercheSWT;
import fr.legrain.lib.gui.aide.PaAideSWT;
import fr.legrain.lib.gui.aide.ParamAfficheAide;

public class EtatFamilleArticle extends Etat {
	
	static Logger logger = Logger.getLogger(EtatFamilleArticle.class);

	private String paramNomDateDebut = "p1";
	private String paramNomDateFin = "p2";
//	private String paramNomCodeArticle = "p3";
	private String paramNomCodeFamille = "p4";
	private String paramNomCodeUnite1 = "p5";
	private String paramNomCodeUnite2 = "p6";
//	private String paramNomExclureQteSsUnite = "p7";
	private String paramNomRegroupementUnite = "p10";
	private String paramNomLocalisationTVAFr = "p11";
	private String paramNomLocalisationTVAUE = "p12";
	private String paramNomLocalisationTVAHUE = "p13";
	private String paramNomLocalisationTVAFranchise = "p14";
	private String paramNomLocalisationSansTVA = "p17";
	private String paramNomType = "p8";
	private String paramNomTypeValeurSynhteseFamille = "0_synthese_famille";
	private String paramNomTypeValeurSynhteseFamilleEtArticle= "0_synthese_famille_article";
	private String paramNomTypeValeurDetailFamille = "1_detail_famille";
	private String paramNomTypeValeurDetailFamilleEtArticle= "1_detail_famille_article";
	
	private String paramNomRegroupementValeurU1 = "0_U1";
	private String paramNomRegroupementValeurU2 = "1_U2";
	private String paramNomRegroupementValeurU1etU2 = "1_U1U2";
	
	private String paramNomEditionSyntheseFamille = "Synthèse par famille";
	private String paramNomEditionSyntheseFamilleEtArticle = "Synthèse par famille et article";
	private String paramNomEditionDetailFamille = "Détail par famille";
	private String paramNomEditionDetailFamilleEtArticle = "Détail par famille et article";
	
	private String choixTypeEditon = null;
	private int synthese = CalculsEtatFamilleEtArticle.DETAIL;
	private boolean travailSurDateLivraison = false;
	
//	private String cheminEditionSynthese = "/donnees/Projet/Java/Eclipse/GestionCommerciale/fr.legrain.gestionStatistiques/report/EtatSyntheseArticle.rptdesign";
//	private String cheminEditionDetail = "/donnees/Projet/Java/Eclipse/GestionCommerciale/fr.legrain.gestionStatistiques/report/EtatArticle.rptdesign";
	
	public EtatFamilleArticle(boolean travailSurDateLivraison) {
		super();
		this.travailSurDateLivraison = travailSurDateLivraison;
	}

	private String cheminEditionSyntheseFamille = "/report/defaut/EtatSyntheseFamille.rptdesign";
	private String cheminEditionSyntheseFamilleEtArticle = "/report/defaut/EtatSyntheseFamilleArticle.rptdesign";
	private String cheminEditionDetailFamille = "/report/defaut/EtatFamille.rptdesign";
	private String cheminEditionDetailFamilleLivraison = "/report/defaut/EtatFamilleLivraison.rptdesign";
	private String cheminEditionDetailFamilleEtArticle = "/report/defaut/EtatFamilleArticle.rptdesign";
	private String cheminEditionDetailFamilleEtArticleLivraison = "/report/defaut/EtatFamilleArticleLivraison.rptdesign";
	
	private Param paramDateDeb = null;
	private Param paramDateFin = null;
//	private Param paramCodeArticle = null;
	private Param paramCodeFamille = null;
	private Param paramCodeUnite1 = null;
	private Param paramCodeUnite2 = null;
//	private Param paramExclureQteSsUnite = null;
	private Param paramRegroupementUnite = null;
	private Param paramLocalisationTVAFr = null;
	private Param paramLocalisationTVAUE = null;
	private Param paramLocalisationTVAHUE = null;
	private Param paramLocalisationTVAFranchise = null;
	private Param paramLocalisationSansTVA = null;
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
		paramCodeFamille = new Param(EtatArticle.TYPE_PARAM_TEXT, paramNomCodeFamille, "Code Famille", null, null, "", null, 1, true);
		paramCodeUnite1 = new Param(EtatArticle.TYPE_PARAM_TEXT, paramNomCodeUnite1, "Unité 1", null, null, "", null, 1, true);
		paramCodeUnite2 = new Param(EtatArticle.TYPE_PARAM_TEXT, paramNomCodeUnite2, "Unité 2", null, null, "", null, 1, true);
		
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
		
		paramType = new Param(EtatFamilleArticle.TYPE_PARAM_LIST, "p8", "", null, 
				new String[][]{
				{paramNomEditionSyntheseFamille,paramNomTypeValeurSynhteseFamille},
				{paramNomEditionSyntheseFamilleEtArticle,paramNomTypeValeurSynhteseFamilleEtArticle},
				{paramNomEditionDetailFamille,paramNomTypeValeurDetailFamille},
				{paramNomEditionDetailFamilleEtArticle,paramNomTypeValeurDetailFamilleEtArticle}
				}, 
				"defaut", null, 1, true);
		
		LinkedHashMap<String,LinkedList<Param>> l = new LinkedHashMap<String,LinkedList<Param>>();
		
		LinkedList<Param> l1 = new LinkedList<Param>();
		l1.add(paramDateDeb);
		l1.add(paramDateFin);
		l.put("Période", l1);
		LinkedList<Param> l2 = new LinkedList<Param>();
		l2.add(paramCodeFamille);
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
//			if(!reportParam.get(paramNomCodeArticle).equals("")){
//				criteres.setCodeArticle(reportParam.get(paramNomCodeArticle));
//			}
			if(!reportParam.get(paramNomCodeFamille).equals("")){
				criteres.setCodeFamille(reportParam.get(paramNomCodeFamille));
			}
			
			criteres.setDateDeb(LibDate.stringToDate(reportParam.get(paramNomDateDebut)));
			criteres.setDateFin(LibDate.stringToDate(reportParam.get(paramNomDateFin)));
			
			criteres.setLocalisationTVAFr(LibConversion.StringToBoolean(reportParam.get(paramNomLocalisationTVAFr)));
			criteres.setLocalisationTVAUE(LibConversion.StringToBoolean(reportParam.get(paramNomLocalisationTVAUE)));
			criteres.setLocalisationTVAHUE(LibConversion.StringToBoolean(reportParam.get(paramNomLocalisationTVAHUE)));
			criteres.setLocalisationTVAFranchise(LibConversion.StringToBoolean(reportParam.get(paramNomLocalisationTVAFranchise)));
			criteres.setLocalisationSansTVA(LibConversion.StringToBoolean(reportParam.get(paramNomLocalisationSansTVA)));
			criteres.setTravailSurDateLivraison(travailSurDateLivraison);
			
			if(!reportParam.get(paramNomCodeUnite1).equals("")){
				criteres.setUn1(reportParam.get(paramNomCodeUnite1));
			}
			if(!reportParam.get(paramNomCodeUnite2).equals("")){
				criteres.setUn2(reportParam.get(paramNomCodeUnite2));
			} 
//			criteres.setExclusionQteSsUnite(LibConversion.StringToBoolean(reportParam.get(paramNomExclureQteSsUnite)));
			
			//Récupérer les critères de séléction et récupérer les lignes de stocks
			//en fonction de ces critères
			CalculsEtatFamilleEtArticle etatDms = new CalculsEtatFamilleEtArticle();

			synthese = CalculsEtatFamilleEtArticle.DETAIL;
			if(reportParam.get(paramNomType).equals(paramNomTypeValeurSynhteseFamilleEtArticle)) {
				synthese = CalculsEtatFamilleEtArticle.SYNTHESE_ET_ARTICLE;
			}
			if(reportParam.get(paramNomType).equals(paramNomTypeValeurSynhteseFamille)) {
				synthese = CalculsEtatFamilleEtArticle.SYNTHESE;
			}

			choixTypeEditon = reportParam.get(paramNomType);

			List<TaEtatMouvementDms> listeEtat = etatDms.calculEtatFamille(criteres,synthese);
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
		return "Ventes (et avoirs) par famille d'article";
	}

	@Override
	public String getDescription() {
		return "Ventes (et avoirs) par famille d'article";
	}

	@Override
	public Image getIcon() {
//		if(!this.travailSurDateLivraison)
//			return Activator.getImageDescriptor("/icons/boutons/picto_75px/picto_fam_art_75px.png").createImage();
//		else
//			return Activator.getImageDescriptor("/icons/boutons/picto_75px/picto_fam_art2_75px.bmp").createImage();
		if(!this.travailSurDateLivraison)
			return Activator.getImageDescriptor("/icons/boutons/picto_fam_art_75px-date-fact.png").createImage();
		else
			return Activator.getImageDescriptor("/icons/boutons/picto_fam_art_75px-date-livr.png").createImage();
	}

	@Override
	public String getEditionFilePath() {
		//return ConstEdition.FICHE_FILE_REPORT_ETATFAMILLE;
		
		if(choixTypeEditon!=null) {
			if(choixTypeEditon.equals(paramNomTypeValeurSynhteseFamille)) {
				return cheminEditionSyntheseFamille;
			} else if(choixTypeEditon.equals(paramNomTypeValeurSynhteseFamilleEtArticle)) {
				return cheminEditionSyntheseFamilleEtArticle;
			} else if(choixTypeEditon.equals(paramNomTypeValeurDetailFamille)) {
				if(travailSurDateLivraison) return cheminEditionDetailFamilleLivraison; else return cheminEditionDetailFamille;
			} else if(choixTypeEditon.equals(paramNomTypeValeurDetailFamilleEtArticle)) {
				if(travailSurDateLivraison) return cheminEditionDetailFamilleEtArticleLivraison; else return cheminEditionDetailFamilleEtArticle;
			}
		}
		return null;
	}
	
	@Override
	public String getNomOngletEdition() {
		if(choixTypeEditon!=null) {
			if(choixTypeEditon.equals(paramNomTypeValeurSynhteseFamille)) {
				return paramNomEditionSyntheseFamille;
			} else if(choixTypeEditon.equals(paramNomTypeValeurSynhteseFamilleEtArticle)) {
				return paramNomEditionSyntheseFamilleEtArticle;
			} else if(choixTypeEditon.equals(paramNomTypeValeurDetailFamille)) {
				return paramNomEditionDetailFamille;
			} else if(choixTypeEditon.equals(paramNomTypeValeurDetailFamilleEtArticle)) {
				return paramNomEditionDetailFamilleEtArticle;
			}
		}
		return null;
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
		
		final Composite c = paramCodeFamille.control.get(0).getParent();
		
		paramController = new ParamController(c,listeComposantFocusableParam) {
			
			@Override
			protected boolean aideDisponible() {
				boolean result = false;
//				boolean result = true;
//				switch ((getThis().dao.getModeObjet().getMode())) {
//				case C_MO_CONSULTATION:
//				case C_MO_EDITION:
//				case C_MO_INSERTION:
				if(getFocusCourantSWT().equals(paramCodeFamille.control.get(0))
						|| getFocusCourantSWT().equals(paramCodeUnite1.control.get(0))
						|| getFocusCourantSWT().equals(paramCodeUnite2.control.get(0))
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

//						switch ((ParamController.this.dao.getModeObjet().getMode())) {
//						case C_MO_CONSULTATION:					
//						case C_MO_EDITION:
//						case C_MO_INSERTION:
						if (getFocusCourantSWT()==paramCodeFamille.control.get(0)	){
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
							
//							break;
//						default:
//							break;
//						}
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
		
		if(reportParam.get(paramNomCodeFamille)!=null && !reportParam.get(paramNomCodeFamille).equals("")) {
			if(!desc.equals("")) desc+=", ";
			desc += "Famille '"+reportParam.get(paramNomCodeFamille)+"'";
		}
		
		if(reportParam.get(paramNomCodeUnite1)!=null && !reportParam.get(paramNomCodeUnite1).equals("")) {
			if(!desc.equals("")) desc+=", ";
			desc += "U1 '"+reportParam.get(paramNomCodeUnite1)+"'";
		}
		
		if(reportParam.get(paramNomCodeUnite2)!=null && !reportParam.get(paramNomCodeUnite2).equals("")) {
			if(!desc.equals("")) desc+=", ";
			desc += "U2 '"+reportParam.get(paramNomCodeUnite2)+"'";
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

	public boolean isTravailSurDateLivraison() {
		return travailSurDateLivraison;
	}

	public void setTravailSurDateLivraison(boolean travailSurDateLivraison) {
		this.travailSurDateLivraison = travailSurDateLivraison;
	}
	
	
}

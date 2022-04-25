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
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

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
import fr.legrain.gestCom.Module_Tiers.SWTTiers;
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
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.ecran.PaTiersSWT;
import fr.legrain.tiers.ecran.ParamAfficheTiers;
import fr.legrain.tiers.ecran.SWTPaTiersController;
import fr.legrain.tiers.editor.EditorInputTiers;
import fr.legrain.tiers.editor.TiersMultiPageEditor;

public class EtatTiers extends Etat {
	
	static Logger logger = Logger.getLogger(EtatTiers.class);

	private String paramNomDateDebut = "p1";
	private String paramNomDateFin = "p2";
	private String paramNomCodeTiers = "p3";
	private String paramNomCodeTiersFin = "p4";
//	private String paramNomCodeFamilleTiers = "p5";
//	private String paramNomCodeFamilleTiersFin = "p6";
	private String paramNomRegroupementUnite = "p10";
	private String paramNomLocalisationTVAFr = "p11";
	private String paramNomLocalisationTVAUE = "p12";
	private String paramNomLocalisationTVAHUE = "p13";
	private String paramNomLocalisationTVAFranchise = "p14";
	private String paramNomLocalisationSansTVA = "p17";
	private String paramNomType = "p16";
	private String paramNomTypeValeurSynhtese = "0_synthese";
	private String paramNomTypeValeurSynhteseArticle = "2_synthese_tiers_article";
	private String paramNomTypeValeurDetail = "1_detail";
	
	private String paramNomRegroupementValeurU1 = "0_U1";
	private String paramNomRegroupementValeurU2 = "1_U2";
	private String paramNomRegroupementValeurU1etU2 = "1_U1U2";
	
	private String paramNomEditionSynthese = "Synthèse des ventes par client";
	private String paramNomEditionSyntheseEtArticle = "Synthèse des ventes par client et par article";
	private String paramNomEditionDetail = "Détail des ventes par client";
	
	private String choixTypeEditon = null;
	private boolean synthese = true;
	
//	private String cheminEditionSynthese = "/donnees/Projet/Java/Eclipse/GestionCommerciale/fr.legrain.gestionStatistiques/report/EtatSyntheseArticle.rptdesign";
//	private String cheminEditionDetail = "/donnees/Projet/Java/Eclipse/GestionCommerciale/fr.legrain.gestionStatistiques/report/EtatArticle.rptdesign";
	
	private String cheminEditionSynthese = "/report/defaut/EtatSyntheseTiers.rptdesign";
	private String cheminEditionSyntheseTiersArticle = "/report/defaut/EtatSyntheseTiersArticle.rptdesign";
	private String cheminEditionDetail = "/report/defaut/EtatTiers.rptdesign";
	
	private Param paramDateDeb = null;
	private Param paramDateFin = null;
	private Param paramCodeTiers = null;
	private Param paramCodeTiersFin = null;
//	private Param paramCodeFamilleTiers = null;
//	private Param paramCodeFamilleTiersFin = null;
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
		
		paramDateDeb = new Param(EtatTiers.TYPE_PARAM_DATE, paramNomDateDebut, "Date début", null, null, null, taInfoEntreprise.getDatedebInfoEntreprise(), 1, false);
		paramDateFin = new Param(EtatTiers.TYPE_PARAM_DATE, paramNomDateFin, "Date fin", null, null, null, taInfoEntreprise.getDatefinInfoEntreprise(), 1, false);
		paramCodeTiers = new Param(EtatTiers.TYPE_PARAM_TEXT, paramNomCodeTiers, "Code client", null, null, "", null, 1, false);
		paramCodeTiersFin = new Param(EtatTiers.TYPE_PARAM_TEXT, paramNomCodeTiersFin, "à", null, null, "", null, 1, false);
//		paramCodeFamilleTiers = new Param(EtatTiers.TYPE_PARAM_TEXT, paramNomCodeFamilleTiers, "Code famille client", null, null, "", null, 1, false);
//		paramCodeFamilleTiersFin = new Param(EtatTiers.TYPE_PARAM_TEXT, paramNomCodeFamilleTiersFin, "à", null, null, "", null, 1, false);
		
		paramRegroupementUnite = new Param(EtatTiers.TYPE_PARAM_LIST, paramNomRegroupementUnite, "", null, 
				new String[][]{
				{"Distinction sur U1",paramNomRegroupementValeurU1},
				{"Distinction sur U2",paramNomRegroupementValeurU2}
				}, 
				"defaut", null, 1, true);
		
		paramLocalisationTVAFr = new Param(EtatTiers.TYPE_PARAM_BOOLEAN, paramNomLocalisationTVAFr, "Vente en France", null, null, "", null, 1, true);
		paramLocalisationTVAUE = new Param(EtatTiers.TYPE_PARAM_BOOLEAN, paramNomLocalisationTVAUE, "Vente UE", null, null, "", null, 1, true);
		paramLocalisationTVAHUE = new Param(EtatTiers.TYPE_PARAM_BOOLEAN, paramNomLocalisationTVAHUE, "Vente Hors UE", null, null, "", null, 1, true);
		paramLocalisationTVAFranchise = new Param(EtatTiers.TYPE_PARAM_BOOLEAN, paramNomLocalisationTVAFranchise, "Vente franchise TVA", null, null, "", null, 1, true);
		paramLocalisationSansTVA = new Param(EtatArticle.TYPE_PARAM_BOOLEAN, paramNomLocalisationSansTVA, "Vente sans TVA", null, null, "", null, 1, true);		
		
		paramType = new Param(EtatTiers.TYPE_PARAM_LIST, paramNomType, "", null, 
				new String[][]{
				{paramNomEditionSynthese,paramNomTypeValeurSynhtese},
				{paramNomEditionSyntheseEtArticle,paramNomTypeValeurSynhteseArticle},
				{paramNomEditionDetail,paramNomTypeValeurDetail}
				}, 
				"defaut", null, 1, true);
		
		LinkedHashMap<String,LinkedList<Param>> l = new LinkedHashMap<String,LinkedList<Param>>();
		
		LinkedList<Param> l1 = new LinkedList<Param>();
		l1.add(paramDateDeb);
		l1.add(paramDateFin);
		l.put("Période", l1);
		LinkedList<Param> l2 = new LinkedList<Param>();
		l2.add(paramCodeTiers);
		l2.add(paramCodeTiersFin);
//		l2.add(paramCodeFamilleTiers);
//		l2.add(paramCodeFamilleTiersFin);
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
			if(!reportParam.get(paramNomCodeTiers).equals("")){
				criteres.setCodeTiers(reportParam.get(paramNomCodeTiers));
			}
			if(!reportParam.get(paramNomCodeTiersFin).equals("")){
				criteres.setCodeTiersFin(reportParam.get(paramNomCodeTiersFin));
			}
//			if(!reportParam.get(paramNomCodeFamilleTiers).equals("")){
//				criteres.setCodeFamilleTiers(reportParam.get(paramNomCodeFamilleTiers));
//			}
//			if(!reportParam.get(paramNomCodeFamilleTiersFin).equals("")){
//				criteres.setCodeFamilleTiersFin(reportParam.get(paramNomCodeFamilleTiersFin));
//			}
			criteres.setDateDeb(LibDate.stringToDate(reportParam.get(paramNomDateDebut)));
		
			criteres.setDateFin(LibDate.stringToDate(reportParam.get(paramNomDateFin)));
			
			/*
			 ALTER TABLE TA_INFOS_FACTURE ADD CODE_T_TVA_DOC DLGR_CODEL;
			 */
			criteres.setLocalisationTVAFr(LibConversion.StringToBoolean(reportParam.get(paramNomLocalisationTVAFr)));
			criteres.setLocalisationTVAUE(LibConversion.StringToBoolean(reportParam.get(paramNomLocalisationTVAUE)));
			criteres.setLocalisationTVAHUE(LibConversion.StringToBoolean(reportParam.get(paramNomLocalisationTVAHUE)));
			criteres.setLocalisationTVAFranchise(LibConversion.StringToBoolean(reportParam.get(paramNomLocalisationTVAFranchise)));
			criteres.setLocalisationSansTVA(LibConversion.StringToBoolean(reportParam.get(paramNomLocalisationSansTVA)));

			//Récupérer les critères de séléction et récupérer les lignes de stocks
			//en fonction de ces critères
			CalculsEtatFamilleEtArticle etatDms = new CalculsEtatFamilleEtArticle();
			
			synthese = true;
			if(reportParam.get(paramNomType).equals(paramNomTypeValeurDetail)) {
				synthese = false;
			}
			
			boolean groupByTiers = false;
			if(reportParam.get(paramNomType).equals(paramNomTypeValeurSynhtese)) {
				groupByTiers = true;
			}

			choixTypeEditon = reportParam.get(paramNomType);

			List<TaEtatMouvementDms> listeEtat = etatDms.calculEtatTiers(criteres,synthese,groupByTiers);
			l = new ArrayList<Object>();
			for (TaEtatMouvementDms t : listeEtat) {
				l.add(t);
			}

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
		return "Ventes (et avoirs) par client";
	}

	@Override
	public String getDescription() {
		return "Ventes (et avoirs) par client";
	}

	@Override
	public Image getIcon() {
		return Activator.getImageDescriptor("/icons/boutons/picto_75px/picto_clients_75px.png").createImage();
	}

	@Override
	public String getEditionFilePath() {
		
		if(choixTypeEditon!=null) {
			if(choixTypeEditon.equals(paramNomTypeValeurSynhtese)) {
				return cheminEditionSynthese;
			} else if(choixTypeEditon.equals(paramNomTypeValeurSynhteseArticle)) {
				return cheminEditionSyntheseTiersArticle;
			} else if(choixTypeEditon.equals(paramNomTypeValeurDetail)) {
				return cheminEditionDetail;
			}
		}
		
		if(synthese)
			return cheminEditionSynthese;
		else
			return cheminEditionDetail;
	}
	
	@Override
	public String getNomOngletEdition() {
		if(choixTypeEditon!=null) {
			if(choixTypeEditon.equals(paramNomTypeValeurSynhtese)) {
				return paramNomEditionSynthese;
			} else if(choixTypeEditon.equals(paramNomTypeValeurSynhteseArticle)) {
				return paramNomEditionSyntheseEtArticle;
			} else if(choixTypeEditon.equals(paramNomTypeValeurDetail)) {
				return paramNomEditionDetail;
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
		
		final Composite c = paramCodeTiers.control.get(0).getParent();
		
		paramController = new ParamController(c,listeComposantFocusableParam) {
			
			@Override
			protected boolean aideDisponible() {
				boolean result = false;
//				boolean result = true;
//				switch ((getThis().dao.getModeObjet().getMode())) {
//				case C_MO_CONSULTATION:
//				case C_MO_EDITION:
//				case C_MO_INSERTION:
				if(getFocusCourantSWT().equals(paramCodeTiers.control.get(0))
						|| getFocusCourantSWT().equals(paramCodeTiersFin.control.get(0))
//						|| getFocusCourantSWT().equals(paramCodeFamilleTiers.control.get(0))
//						|| getFocusCourantSWT().equals(paramCodeFamilleTiersFin.control.get(0))
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
						if (getFocusCourantSWT()==paramCodeTiers.control.get(0)
								|| getFocusCourantSWT()==paramCodeTiersFin.control.get(0) ){
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
							//paramAfficheAideRecherche.setDebutRecherche((Text)p3.control.get(0)).getText());
							paramAfficheAideRecherche.setDebutRecherche("");
							paramAfficheAideRecherche.setControllerAppelant(getThis());
							//paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTArticle,TaArticleDAO,TaArticle>(SWTArticle.class,dao.getEntityManager()));
							paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTiers,TaTiersDAO,TaTiers>(new TaTiersDAO(), SWTTiers.class));
							paramAfficheAideRecherche.setTypeObjet(swtPaTiersController.getClassModel());
							paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_TIERS);
						}
//						else if (getFocusCourantSWT()==paramCodeFamilleTiers.control.get(0)
//								|| getFocusCourantSWT()==paramCodeFamilleTiersFin.control.get(0) ){
//							fr.legrain.tiers.ecran.PaFamilleSWT paFamilleTiers = new fr.legrain.tiers.ecran.PaFamilleSWT(s2,SWT.NULL);
//							SWTPaFamilleTiersController swtPaFamilleTiersController = new SWTPaFamilleTiersController(paFamilleTiers);
//
//							editorCreationId = EditorFamilleTiers.ID;
//							editorInputCreation = new EditorInputFamilleTiers();
//
//							ParamAfficheFamilleTiers paramAfficheFamilleTiers = new ParamAfficheFamilleTiers();
//							paramAfficheAideRecherche.setJPQLQuery(new TaFamilleTiersDAO(getEm()).getJPQLQuery());
//							paramAfficheFamilleTiers.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//							paramAfficheFamilleTiers.setEcranAppelant(paAideController);
//							controllerEcranCreation = swtPaFamilleTiersController;
//							parametreEcranCreation = paramAfficheFamilleTiers;
//
//							paramAfficheAideRecherche.setTypeEntite(TaFamilleTiers.class);
//							paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_FAMILLE);
//							//paramAfficheAideRecherche.setDebutRecherche((Text)p3.control.get(0)).getText());
//							paramAfficheAideRecherche.setDebutRecherche("");
//							paramAfficheAideRecherche.setControllerAppelant(getThis());
//							//paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTArticle,TaArticleDAO,TaArticle>(SWTArticle.class,dao.getEntityManager()));
//							paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTFamilleTiers,TaFamilleTiersDAO,TaFamilleTiers>(new TaFamilleTiersDAO(), SWTFamilleTiers.class));
//							paramAfficheAideRecherche.setTypeObjet(swtPaFamilleTiersController.getClassModel());
//							paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_FAMILLE);
//						}		
							
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
		if(reportParam.get(paramNomCodeTiers)!=null && !reportParam.get(paramNomCodeTiers).equals("")) {
			if(reportParam.get(paramNomCodeTiersFin)!=null && !reportParam.get(paramNomCodeTiersFin).equals("")) {
				desc += "Du tiers '"+reportParam.get(paramNomCodeTiers)+"' au tiers '"+reportParam.get(paramNomCodeTiersFin)+"'";
			} else {
				desc += "Tiers '"+reportParam.get(paramNomCodeTiers)+"'";
			}
		}
		
//		if(reportParam.get(paramNomCodeFamilleTiers)!=null && !reportParam.get(paramNomCodeFamilleTiers).equals("")) {
//			if(!desc.equals("")) desc+=", ";
//			if(reportParam.get(paramNomCodeFamilleTiersFin)!=null && !reportParam.get(paramNomCodeFamilleTiersFin).equals("")) {
//				desc += "De la famille '"+reportParam.get(paramNomCodeFamilleTiers)+"' à la famille '"+reportParam.get(paramNomCodeFamilleTiersFin)+"'";
//			} else {
//				desc += "Famille '"+reportParam.get(paramNomCodeFamilleTiers)+"'";
//			}
//		}
		
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

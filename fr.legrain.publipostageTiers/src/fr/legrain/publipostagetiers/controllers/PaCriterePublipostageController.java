package fr.legrain.publipostagetiers.controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.openmbean.TabularType;

import javax.persistence.EntityManager;

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

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaFamille;
import fr.legrain.document.divers.ModelDocument;
import fr.legrain.document.divers.TypeDoc;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaParamPublipostageDAO;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.dossierIntelligent.dao.TaParamDossierIntel;
import fr.legrain.dossierIntelligent.dao.TaParamDossierIntelDAO;
import fr.legrain.dossierIntelligent.dao.TaRParamDossierIntel;
import fr.legrain.dossierIntelligent.dao.TaTypeDonnee;
import fr.legrain.dossierIntelligent.dao.TaTypeDonneeDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.CustomFieldMapperIFLGR;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Tiers.ModelTiers;
import fr.legrain.gestCom.Module_Tiers.SWTTiers;
import fr.legrain.gestCom.gestComBd.gestComBdPlugin;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
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
import fr.legrain.lib.data.InfosVerifSaisie;
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
import fr.legrain.publipostage.divers.ParamAffichePublipostageFacture;
import fr.legrain.publipostage.divers.TypeVersionPublipostage;
import fr.legrain.publipostagetiers.Activator;
import fr.legrain.publipostagetiers.ecrans.PaCriterePublipostage;
import fr.legrain.publipostagetiers.ecrans.PaSelectionLignePublipostage;
import fr.legrain.publipostagetiers.preferences.PreferenceConstants;
import fr.legrain.tiers.dao.TaAdresse;
import fr.legrain.tiers.dao.TaCPaiement;
import fr.legrain.tiers.dao.TaCommentaire;
import fr.legrain.tiers.dao.TaCompl;
import fr.legrain.tiers.dao.TaCompteBanque;
import fr.legrain.tiers.dao.TaEmail;
import fr.legrain.tiers.dao.TaEntreprise;
import fr.legrain.tiers.dao.TaFamilleTiers;
import fr.legrain.tiers.dao.TaInfoJuridique;
import fr.legrain.tiers.dao.TaLiens;
import fr.legrain.tiers.dao.TaNoteTiers;
import fr.legrain.tiers.dao.TaTAdr;
import fr.legrain.tiers.dao.TaTBanque;
import fr.legrain.tiers.dao.TaTCivilite;
import fr.legrain.tiers.dao.TaTEntite;
import fr.legrain.tiers.dao.TaTNoteTiers;
import fr.legrain.tiers.dao.TaTTarif;
import fr.legrain.tiers.dao.TaTTiers;
import fr.legrain.tiers.dao.TaTTvaDoc;
import fr.legrain.tiers.dao.TaTelephone;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.dao.TaWeb;
import fr.legrain.tiers.ecran.PaTiersSWT;
import fr.legrain.tiers.ecran.ParamAfficheTiers;
import fr.legrain.tiers.ecran.SWTPaTiersController;
import fr.legrain.tiers.editor.EditorInputTiers;
import fr.legrain.tiers.editor.TiersMultiPageEditor;

public class PaCriterePublipostageController extends JPABaseControllerSWTStandard 
implements RetourEcranListener{

	static Logger logger = Logger.getLogger(PaCriterePublipostageController.class.getName());	
	private PaCriterePublipostage  vue = null;
	private Date dateDeb=null;
	private Date dateFin=null;
//	private TaTiers tiers = null;
	TaTiersDAO dao=null;
	TaFactureDAO daoFacture=null;
	private LgrDozerMapper<TaTiers,SWTTiers> mapperModelToUI = null;
	TaInfoEntreprise infos =null;
	TaInfoEntrepriseDAO daoInfos=null;
	
	private Object ecranAppelant = null;
	private Realm realm;
	private DataBindingContext dbc;
	
	private ModelTiers modelTiers = null;
	private ModelDocument modelDocument = null;
	private PaSelectionPublipostageControlleur paSelectionPublipostageControlleurPreSelection=null;
	private PaSelectionFinalePublipostageControlleur paSelectionPublipostageControlleurSelectionFinale=null;
	private TaTypeDonneeDAO daoType=null;
	private TaTypeDonnee typeDonnee=null;
	private String sql=null;
	private String champsTiers=null;
	private String mot=null;
	private String valeurCritere=null;
	private String valeurCritere2=null;
	private TaParamDossierIntelDAO taParamDao=null;
	private Map<String,String> listeCorrespondanceTiers=null;
	private Map<String,String> listeTitreChampsTiers=null;
	private List<String> listeRequeteTiers=null;
	private List<String> listeRequeteDocument=null;
	private Map<String,Object> listeObjetTiers=null;
	private Class classNameSelectionTiers=null;
	private Class classNameSelectionDoc=null;
	
	private TaTypeDonnee typeDonneeDoc=null;
	private String sqlDoc=null;
	private String champsDoc=null;
	private String motDoc=null;
	private Map<String,String> listeCorrespondanceDoc=null;
	private Map<String,String> listeTitreChampsDoc=null;
	private Map<String,Object> listeObjetDocument=null;
	
	public PaCriterePublipostageController(PaCriterePublipostage vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao =new TaTiersDAO(getEm());
		daoFacture=new TaFactureDAO(getEm());
		modelTiers = new ModelTiers();
		setVue(vue);

		vue.getShell().addShellListener(this);

		//Branchement action annuler : empeche la fermeture automatique de la fenetre sur ESC
		vue.getShell().addTraverseListener(new Traverse());

		initController();
		initSashFormWeight();
		daoInfos=new TaInfoEntrepriseDAO(getEm());
		infos =daoInfos.findInstance();
	}

	public PaCriterePublipostageController(PaCriterePublipostage vue) {
		this(vue,null);
	}
	
	private void initController()	{
		try {	
			initSashFormWeight();
			setDaoStandard(dao);
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
			logger.error("Erreur : PaTiersController", e);
			vue.getLaMessage().setText(e.getMessage());
		}
	}
	
	protected void initVerifySaisie() {
		if (mapInfosVerifSaisie == null)
			mapInfosVerifSaisie = new HashMap<Control, InfosVerifSaisie>();
		
	
		
		initVerifyListener(mapInfosVerifSaisie, dao);
	}

	private void initVue() {
		PaSelectionLignePublipostage selectionLigneTiersPreSelection = new PaSelectionLignePublipostage(vue.getExpandBarGroup(), SWT.PUSH,1,
				 SWT.FULL_SELECTION
					| SWT.H_SCROLL
					| SWT.V_SCROLL
					| SWT.BORDER
					| SWT.CHECK);
		paSelectionPublipostageControlleurPreSelection = new PaSelectionPublipostageControlleur(selectionLigneTiersPreSelection,getEm());
		ParamAffichePublipostageFacture paramAffichePublipostageFacture =new ParamAffichePublipostageFacture();
		paramAffichePublipostageFacture.setEnregistreDirect(true);
		paSelectionPublipostageControlleurPreSelection.configPanel(paramAffichePublipostageFacture);
		
		addExpandBarItem(vue.getExpandBarGroup(), selectionLigneTiersPreSelection,
		"Pré-Sélection des tiers ", LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_IDENTITE_TIERS), SWT.DEFAULT, 400);
		vue.getExpandBarGroup().getItem(0).setExpanded(true);
		
		PaSelectionLignePublipostage selectionLigneTiersSelectionFinale = new PaSelectionLignePublipostage(vue.getExpandBarGroup(), SWT.PUSH,1,
				 SWT.FULL_SELECTION
					| SWT.H_SCROLL
					| SWT.V_SCROLL
					| SWT.BORDER
					| SWT.CHECK);
		paSelectionPublipostageControlleurSelectionFinale = new PaSelectionFinalePublipostageControlleur(selectionLigneTiersSelectionFinale,getEm());
		paramAffichePublipostageFacture =new ParamAffichePublipostageFacture();
		paramAffichePublipostageFacture.setEnregistreDirect(true);
		paSelectionPublipostageControlleurSelectionFinale.configPanel(paramAffichePublipostageFacture);
		
		addExpandBarItem(vue.getExpandBarGroup(), selectionLigneTiersSelectionFinale,
		"Sélection finale du publipostage ", LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_IDENTITE_TIERS), SWT.DEFAULT, 400);
		vue.getExpandBarGroup().getItem(1).setExpanded(true);		
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
		try{
			if(paSelectionPublipostageControlleurSelectionFinale!=null){
				paSelectionPublipostageControlleurSelectionFinale.setMasterModel(
					paSelectionPublipostageControlleurPreSelection.getMasterModelFinal());
			paSelectionPublipostageControlleurSelectionFinale.actRefresh();
			}
		}catch (Exception e) {
				logger.error("",e);
			}
		finally{
			vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
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
		Boolean appliquer=false;
		String sqlSuppDeb="";
		String sqlSuppFin="";
		String sqlLoc="";
		if(vue.getBtnValiderCritereTiers().isFocusControl()){
			String critere="";
			sqlLoc=sql;
			//champs=listeCorrespondanceTiers.get(champs);
			if(!vue.getTfCritereTiers2().getText().equals("")){
				valeurCritere=vue.getTfCritereTiers().getText();
				valeurCritere2=vue.getTfCritereTiers2().getText();			
				valeurCritere=valeurCritere.replace("*", "%");
				valeurCritere2=valeurCritere2.replace("*", "%");
				if(classNameSelectionTiers == Date.class){
					valeurCritere=LibDate.StringDateToStringSql(valeurCritere,new Date());
					valeurCritere2=LibDate.StringDateToStringSql(valeurCritere2,new Date());
					critere=champsTiers+" "+sqlLoc+" cast('"+valeurCritere+"' as date) and cast('"+valeurCritere2+"' as date) ";
				}else if(classNameSelectionTiers == String.class){
					critere="upper("+champsTiers+") "+sqlLoc+" upper('"+valeurCritere+"') and upper('"+valeurCritere2+"')";
				}else{
					critere=champsTiers+" "+sqlLoc+" "+valeurCritere+" and "+valeurCritere2;	
				}
			}
			else
				if(!vue.getTfCritereTiers().getText().equals("")){
					valeurCritere=vue.getTfCritereTiers().getText();
					valeurCritere=valeurCritere.replace("*", "%");
					if(classNameSelectionTiers == Date.class){
						valeurCritere=LibDate.StringDateToStringSql(valeurCritere,new Date());
//						if(valeurCritere.contains("%")){
//							String[] tabCritereDate=valeurCritere.split("/");
//							String[] tabMotExtract=new String[]{"MONTH","DAY","YEAR"};
//							String and="";
//							String mot="";
//							for (int j = 0; j < tabCritereDate.length; j++) {
//								String val=tabCritereDate[j];
//								mot=tabMotExtract[j];
//								if(!val.contains("%")){
//									critere=critere+and+"Extract("+mot+" FROM "+champsTiers+")= '"+val+"'";
//									and=" and ";
//								}
//
//							}
//						}						
//						else
							critere=champsTiers+" "+sqlLoc+" cast('"+valeurCritere+"' as date)";
					}else if(classNameSelectionTiers == String.class){					
					if(sqlLoc.contains("like")){
						valeurCritere=valeurCritere.replace("%", "");
						sqlSuppDeb="%";
						sqlSuppFin="%";
					}
					if(sqlLoc.contains("start with")){
						valeurCritere=valeurCritere.replace("%", "");
						sqlLoc="like";
						sqlSuppDeb="";
						sqlSuppFin="%";
					}
					if(sqlLoc.contains("finish with")){
						valeurCritere=valeurCritere.replace("%", "");
						sqlLoc="like";
						sqlSuppDeb="%";
						sqlSuppFin="";
					}
					critere="upper("+champsTiers+") "+sqlLoc+" upper('"+sqlSuppDeb+valeurCritere+sqlSuppFin+"')";
					}
					else{
						critere=champsTiers+" "+sqlLoc+" "+sqlSuppDeb+valeurCritere+sqlSuppFin;	
					}
				}

			String requete="";
				//"select a from TaTiers a left join a.taCommerciaux com where a.taTTiers.idTTiers <>-1 ";
			for (String req : listeRequeteTiers) {
				requete=requete+" "+req;
			}
			
			if(!critere.equals("")){
				requete+=" and "+critere;
			}
			dao.setJPQLQuery(requete);
			appliquer=vue.getCcFiltreFinal().getSelection();
		}else
			if(vue.getBtnValiderCritereDoc().isFocusControl()){
				String critere="";
				sqlLoc=sqlDoc;
				if(!vue.getTfCritereDoc2().getText().equals("")){
					valeurCritere=vue.getTfCritereDoc().getText();
					valeurCritere2=vue.getTfCritereDoc2().getText();			
					valeurCritere=valeurCritere.replace("*", "%");
					valeurCritere2=valeurCritere2.replace("*", "%");
					if(classNameSelectionDoc == Date.class){
						valeurCritere=LibDate.StringDateToStringSql(valeurCritere,new Date());
						valeurCritere2=LibDate.StringDateToStringSql(valeurCritere2,new Date());
						critere=champsDoc+" "+sqlLoc+" cast('"+valeurCritere+"' as date) and cast('"+valeurCritere2+"' as date) ";
					}else if(classNameSelectionDoc == String.class){
					critere="upper("+champsDoc+") "+sqlLoc+" upper('"+valeurCritere+"') and upper('"+valeurCritere2+"')";
					}else{
						critere=champsDoc+" "+sqlLoc+" "+valeurCritere+" and "+valeurCritere2;
					}
				}
				else
					if(!vue.getTfCritereDoc().getText().equals("")){
						valeurCritere=vue.getTfCritereDoc().getText();
						valeurCritere=valeurCritere.replace("*", "%");	
						if(classNameSelectionDoc == Date.class){
							valeurCritere=LibDate.StringDateToStringSql(valeurCritere,new Date());
							critere=champsDoc+" "+sqlLoc+" cast('"+ valeurCritere+"' as date)";
						}else if(classNameSelectionDoc == String.class){
							if(sqlLoc.contains("like")){
								valeurCritere=valeurCritere.replace("%", "");
								sqlSuppDeb="%";
								sqlSuppFin="%";
							}	
							if(sqlLoc.contains("start with")){
								valeurCritere=valeurCritere.replace("%", "");
								sqlLoc="like";
								sqlSuppDeb="";
								sqlSuppFin="%";
							}
							if(sqlLoc.contains("finish with")){
								valeurCritere=valeurCritere.replace("%", "");
								sqlLoc="like";
								sqlSuppDeb="%";
								sqlSuppFin="";
							}
							critere="upper("+champsDoc+") "+sqlLoc+" upper('"+sqlSuppDeb+valeurCritere+sqlSuppFin+"')";
						}else
							critere=champsDoc+" "+sqlLoc+" "+sqlSuppDeb+valeurCritere+sqlSuppFin;	
					}
				String requete="";
//					 requete="select distinct(a) from TaFacture f join f.taTiers a join f.lignes l " +
//					 		" where exists (select a1.idTiers,sum(f1.netAPayer) from TaFacture f1 join f1.taTiers a1 where a1.idTiers=a.idTiers " +
//					 		" group by a1.idTiers" +
//					 		"  having  sum(f1.netAPayer) >=10000 )";
					 
				for (String req : listeRequeteDocument) {
					requete=requete+" "+req;
				}

				//TaFacture alias  join alias.lignes b where b.taArticle.taFamille.idFamille between 21
				if(!critere.equals("")){
					requete+="where "+critere;
				}
				dao.setJPQLQuery(requete);
				appliquer=vue.getCcFiltreSelectionFinaleDoc().getSelection();
			}

		modelTiers.setListeEntity(dao.selectAll());
		for (TaTiers tiers : modelTiers.getListeEntity()) {
			tiers.setAccepte(false);
		}
		modelTiers.remplirListe(getEm());
		Boolean trouve=false;
		List<SWTTiers> listeTemp=new LinkedList<SWTTiers>();
//		if(appliquer){
			if(paSelectionPublipostageControlleurSelectionFinale.getMasterModel()!=null
					&& paSelectionPublipostageControlleurSelectionFinale.getMasterModel().getListeObjet()!=null){
				for (SWTTiers tiers : modelTiers.getListeObjet()) {
					for (SWTTiers tiersFinal : paSelectionPublipostageControlleurSelectionFinale.getMasterModel().getListeObjet()) {
						if(tiersFinal.getCodeTiers().equals(tiers.getCodeTiers())){
							trouve=true;
							tiers.setAccepte(true);
						}
					}
					if(!trouve)listeTemp.add(tiers);
					trouve=false;
				}
				if(appliquer)modelTiers.getListeObjet().removeAll(listeTemp);

			}
		//}
		actRefresh();
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

	}

	@Override
	protected void actRefresh() throws Exception {
		try{			
			if(paSelectionPublipostageControlleurPreSelection!=null){
				paSelectionPublipostageControlleurPreSelection.setMasterModel(modelTiers);
				paSelectionPublipostageControlleurPreSelection.actRefresh();
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
		String validationContext = "TIERS";

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
					new org.eclipse.core.runtime.Status(org.eclipse.core.runtime.Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
				} 
				if (nomChamp.equals(Const.C_DATE_FIN) ) {
					new org.eclipse.core.runtime.Status(org.eclipse.core.runtime.Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
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
		mapActions.put(vue.getBtnValiderCritereTiers(), C_COMMAND_GLOBAL_IMPRIMER_ID);
		mapActions.put(vue.getBtnValiderCritereDoc(), C_COMMAND_GLOBAL_IMPRIMER_ID);

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


		mapComposantChamps.put(vue.getTfTiers(), Const.C_CODE_TIERS);
//		mapComposantChamps.put(vue.getTfCritereTiers(), "critereTiers");
//		mapComposantChamps.put(vue.getTfCritereTiers2(), "critereTiers2");
		
		if (listeComposantFocusable == null) 
			listeComposantFocusable = new ArrayList<Control>();
		

		listeComposantFocusable.add(vue.getBtnTousTiers());
		listeComposantFocusable.add(vue.getTfTiers());
		listeComposantFocusable.add(vue.getCbChamps());
		listeComposantFocusable.add(vue.getCbMots());
		listeComposantFocusable.add(vue.getTfCritereTiers());
		listeComposantFocusable.add(vue.getTfCritereTiers2());
		listeComposantFocusable.add(vue.getBtnValiderCritereTiers());
		listeComposantFocusable.add(vue.getBtnValiderCritereDoc());
		listeComposantFocusable.add(vue.getBtnFermer());
		
		if(mapInitFocus == null) 
			mapInitFocus = new LinkedHashMap<ModeObjet.EnumModeObjet,Control>();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION,vue.getTfTiers());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION,vue.getTfTiers());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION,vue.getTfTiers());

		activeModifytListener();
		
		
		
		vue.getCbListeDoc().add(TypeDoc.TYPE_FACTURE);
		vue.getCbListeDoc().select(0);
		vue.getTfTiers().setVisible(false);
		vue.getLabel1().setVisible(false);
		vue.getCbChamps().removeAll();
		
		//traiter critères Tiers
		listeCorrespondanceTiers=new LinkedHashMap<String, String>();
		listeTitreChampsTiers=new LinkedHashMap<String, String>();
		listeObjetTiers=new LinkedHashMap<String,Object>();
		listeRequeteTiers=new ArrayList<String>();
		
			
		listeTitreChampsTiers.put("code Tiers","codeTiers");
		listeTitreChampsTiers.put("compte","compte");
		listeTitreChampsTiers.put("Nom","nomTiers");
		listeTitreChampsTiers.put("prenom","prenomTiers");
		listeTitreChampsTiers.put("Anniversaire","dateAnniv");
		//listeTitreChampsTiers.put("actif","actifTiers");
		//listeTitreChampsTiers.put("ttc","ttcTiers");
		listeTitreChampsTiers.put("numero de Telephone","numeroTelephone");
		listeTitreChampsTiers.put("code Paiement","codeCPaiement");
		listeTitreChampsTiers.put("adresse Email","adresseEmail");
		listeTitreChampsTiers.put("adresse Web","adresseWeb");
		listeTitreChampsTiers.put("code Entite","codeTEntite");
		listeTitreChampsTiers.put("code postal","codepostalAdresse");
		listeTitreChampsTiers.put("ville","villeAdresse");
		listeTitreChampsTiers.put("Pays","paysAdresse");
		listeTitreChampsTiers.put("nom de l'entreprise","nomEntreprise");
		listeTitreChampsTiers.put("Type tiers","codeTTiers");
		listeTitreChampsTiers.put("Code du commercial","codeCommercial");
		listeTitreChampsTiers.put("Nom du commercial","nomCommercial");
		
		listeTitreChampsTiers.put("Type d'adresse","TAdresse");
		listeTitreChampsTiers.put("Type compte bancaire","TcompteB");
		listeTitreChampsTiers.put("Famille tiers","FamilleTiers");
		listeTitreChampsTiers.put("Note tiers","NoteTiers");
		listeTitreChampsTiers.put("Type de notes","TnoteTiers");
		
		TaTiers tiers =new TaTiers();
		TaAdresse adresse=new TaAdresse();
		listeObjetTiers.put("codeTiers",tiers);
		listeObjetTiers.put("compte",tiers);
		listeObjetTiers.put("nomTiers",tiers);
		listeObjetTiers.put("prenomTiers",tiers);
		listeObjetTiers.put("dateAnniv",tiers);
		//listeObjetTiers.put("actifTiers",tiers);
		//listeObjetTiers.put("ttcTiers",tiers);
		listeObjetTiers.put("numeroTelephone",new TaTelephone());
		listeObjetTiers.put("codeCPaiement",new TaCPaiement());
		listeObjetTiers.put("adresseEmail",new TaEmail());
		listeObjetTiers.put("tadresseWeb",new TaWeb());
		listeObjetTiers.put("codeTEntite",new TaTEntite());
		listeObjetTiers.put("codepostalAdresse",adresse);
		listeObjetTiers.put("villeAdresse",adresse);
		listeObjetTiers.put("paysAdresse",adresse);
		listeObjetTiers.put("nomEntreprise",new TaEntreprise());
		listeObjetTiers.put("codeTTiers",new TaTTiers());
		listeObjetTiers.put("codeCommercial",tiers);
		listeObjetTiers.put("nomCommercial",tiers);		
		listeObjetTiers.put("TAdresse",new TaTAdr());
		listeObjetTiers.put("TcompteB",new TaTBanque());
		listeObjetTiers.put("FamilleTiers",new TaFamilleTiers());
		listeObjetTiers.put("NoteTiers",new TaNoteTiers());
		listeObjetTiers.put("TnoteTiers",new TaTNoteTiers());
		
		listeCorrespondanceTiers.put("codeTiers","a.codeTiers");
		listeCorrespondanceTiers.put("compte","a.compte");
		listeCorrespondanceTiers.put("nomTiers","a.nomTiers");
		listeCorrespondanceTiers.put("prenomTiers","a.prenomTiers");
		listeCorrespondanceTiers.put("dateAnniv","a.dateAnniv");
		//listeCorrespondanceTiers.put("actifTiers","actifTiers");
		//listeCorrespondanceTiers.put("ttcTiers","ttcTiers");
		listeCorrespondanceTiers.put("numeroTelephone","tel.numeroTelephone");
		listeCorrespondanceTiers.put("codeCPaiement","cp.codeCPaiement");
		listeCorrespondanceTiers.put("adresseEmail","Em.adresseEmail");
		listeCorrespondanceTiers.put("adresseWeb","w.adresseWeb");
		listeCorrespondanceTiers.put("codeTEntite","Et.codeTEntite");
		listeCorrespondanceTiers.put("codepostalAdresse","adr.codepostalAdresse");
		listeCorrespondanceTiers.put("villeAdresse","adr.villeAdresse");
		listeCorrespondanceTiers.put("paysAdresse","adr.paysAdresse");
		listeCorrespondanceTiers.put("nomEntreprise","Ent.nomEntreprise");
		listeCorrespondanceTiers.put("codeTTiers","tt.codeTTiers");
		listeCorrespondanceTiers.put("codeCommercial","com.codeTiers");
		listeCorrespondanceTiers.put("nomCommercial","com.nomTiers");
		
		listeCorrespondanceTiers.put("TAdresse","ttadr.codeTAdr");
		listeCorrespondanceTiers.put("TcompteB","tb.codeTBanque");
		listeCorrespondanceTiers.put("FamilleTiers","ft.codeFamille");
		listeCorrespondanceTiers.put("NoteTiers","nt.noteTiers");
		listeCorrespondanceTiers.put("TnoteTiers","tnt.codeTNoteTiers");
		
		listeRequeteTiers.add("select a from fr.legrain.tiers.dao.TaTiers a ");
		listeRequeteTiers.add("left join a.taCommerciaux com ");
		listeRequeteTiers.add("left join a.taTelephone tel ");
		listeRequeteTiers.add("left join a.taCPaiement cp ");
		listeRequeteTiers.add("left join a.taEmail Em ");
		listeRequeteTiers.add("left join a.taWeb w ");
		listeRequeteTiers.add("left join a.taTEntite Et ");
		listeRequeteTiers.add("left join a.taAdresse adr ");
		listeRequeteTiers.add("left join a.taEntreprise Ent ");
		listeRequeteTiers.add("left join a.taTTiers tt ");		
		listeRequeteTiers.add("left join adr.taTAdr ttadr ");
		listeRequeteTiers.add("left join a.taCompteBanques cb left join cb.taTBanque tb ");
		listeRequeteTiers.add("left join a.taFamilleTierses ft ");
		listeRequeteTiers.add("left join a.taNotes nt ");
		listeRequeteTiers.add("left join nt.taTNoteTiers tnt ");
		//dernière ligne
		listeRequeteTiers.add("where tt.idTTiers <>-1");
		
		
		for (String champs : listeTitreChampsTiers.keySet()) {
			vue.getCbChamps().add(champs);
		}
		vue.getCbChamps().setVisibleItemCount(vue.getCbChamps().getItemCount());
		vue.getCbChamps().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				selectionChamp();
			}
		});
		
		vue.getCbMots().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				selectionMot();
			}
		});
		
		vue.getCbChamps().select(0);
		selectionChamp();
		
		//traiter critère document
		listeCorrespondanceDoc=new LinkedHashMap<String, String>();
		listeTitreChampsDoc=new LinkedHashMap<String, String>();
		listeObjetDocument=new LinkedHashMap<String,Object>();
		listeRequeteDocument=new ArrayList<String>();
		
		listeTitreChampsDoc.put("code Document","codeDocument");
		listeTitreChampsDoc.put("date Document","dateDocument");
		listeTitreChampsDoc.put("date Echeance","dateEchDocument");
		listeTitreChampsDoc.put("date Livraison","dateLivDocument");		
		listeTitreChampsDoc.put("article","codeArticle");
		listeTitreChampsDoc.put("famille article","codeFamille");
//		listeTitreChampsDoc.put("reglement","regleDocument");
//		listeTitreChampsDoc.put("reste A Régler","resteARegler");
		listeTitreChampsDoc.put("net HT","netHtCalc");
		listeTitreChampsDoc.put("net Tva","netTvaCalc");
		listeTitreChampsDoc.put("net A Payer","netAPayer");

		TaFacture facture =new TaFacture();	
		listeCorrespondanceDoc.put("codeDocument","f.codeDocument");
		listeCorrespondanceDoc.put("dateDocument","f.dateDocument");
		listeCorrespondanceDoc.put("dateEchDocument","f.dateEchDocument");
		listeCorrespondanceDoc.put("dateLivDocument","f.dateLivDocument");		
		listeCorrespondanceDoc.put("codeArticle","Ar.codeArticle");
		listeCorrespondanceDoc.put("codeFamille","Fa.codeFamille");
//		listeCorrespondanceDoc.put("regleDocument","f.regleDocument");
//		listeCorrespondanceDoc.put("resteARegler","f.resteARegler");
		listeCorrespondanceDoc.put("netHtCalc","f.netHtCalc");
		listeCorrespondanceDoc.put("netTvaCalc","f.netTvaCalc");
		listeCorrespondanceDoc.put("netAPayer","f.netAPayer");
		
		listeObjetDocument.put("codeDocument",facture);
		listeObjetDocument.put("dateDocument",facture);
		listeObjetDocument.put("dateEchDocument",facture);
		listeObjetDocument.put("dateLivDocument",facture);
		listeObjetDocument.put("codeArticle",new TaArticle());
		listeObjetDocument.put("codeFamille",new TaFamille());
//		listeObjetDocument.put("resteARegler",facture);
		listeObjetDocument.put("netHtCalc",facture);
		listeObjetDocument.put("netTvaCalc",facture);
		listeObjetDocument.put("netAPayer",facture);
//		listeObjetDocument.put("regleDocument",facture);
		
		listeRequeteDocument.add("select distinct a from TaFacture f join f.taTiers a join f.lignes l ");
		listeRequeteDocument.add("join l.taArticle Ar ");
		listeRequeteDocument.add("join Ar.taFamille Fa ");
//		listeRequeteDocument.add("join l. ");
//		listeRequeteDocument.add("join l. ");
//		listeRequeteDocument.add("join l. ");
//		listeRequeteDocument.add("join l. ");
//		listeRequeteDocument.add("join l. ");
//		listeRequeteDocument.add("join l. ");
//		listeRequeteDocument.add("join l. ");
//		listeRequeteDocument.add("join l. ");
		

		
		
		for (String champs : listeTitreChampsDoc.keySet()) {
			vue.getCbChampsDoc().add(champs);
		}
		vue.getCbChampsDoc().setVisibleItemCount(vue.getCbChampsDoc().getItemCount());
		vue.getCbChampsDoc().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				selectionChampDoc();
			}
		});
		
		vue.getCbMotsDoc().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				selectionMotDoc();
			}
		});
		
		vue.getCbChampsDoc().select(0);
		selectionChampDoc();
		
		
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
		mapComposantDecoratedField.put(vue.getTfTiers(), vue.getFieldTiers());
	}
	protected void initImageBouton() {
		super.initImageBouton();
		vue.getBtnValiderCritereTiers().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ACCEPTER));
		vue.getBtnValiderCritereDoc().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ACCEPTER));
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

			if(param instanceof ParamAffichePublipostageFacture){
				if(((ParamAffichePublipostageFacture)param).getIdTiers()!=0) {
					TaTiers tiers=new TaTiersDAO(getEm()).findById(((ParamAffichePublipostageFacture)param).getIdTiers());
					vue.getTfTiers().setText(tiers.getCodeTiers());
				}else vue.getBtnTousTiers().setSelection(true);

			}
			bind();

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



	public void setVue(PaCriterePublipostage vue) {
		super.setVue(vue);
		this.vue = vue;
	}


	public PaSelectionPublipostageControlleur getPaSelectionPublipostageControlleur() {
		return paSelectionPublipostageControlleurPreSelection;
	}

	public void setPaSelectionPublipostageControlleur(
			PaSelectionPublipostageControlleur paSelectionPublipostageControlleur) {
		this.paSelectionPublipostageControlleurPreSelection = paSelectionPublipostageControlleur;
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

	public PaSelectionFinalePublipostageControlleur getPaSelectionPublipostageControlleurSelectionFinale() {
		return paSelectionPublipostageControlleurSelectionFinale;
	}

	public void setPaSelectionPublipostageControlleurSelectionFinale(
			PaSelectionFinalePublipostageControlleur paSelectionPublipostageControlleurSelectionFinale) {
		this.paSelectionPublipostageControlleurSelectionFinale = paSelectionPublipostageControlleurSelectionFinale;
	}

	public Class renvoiTypeChampsTiers(String champs,Object objet){
		try {
			return PropertyUtils.getPropertyType(objet, champs);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public  Class renvoiTypeChampsDocument(Object document, String champs){
		try {
			return PropertyUtils.getPropertyType(document, champs);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public void selectionChamp(){
		String[] tab= new String[]{};
		String champPropertie;
		if (daoType==null)daoType =new TaTypeDonneeDAO(getEm());
		
		champsTiers=listeTitreChampsTiers.get(vue.getCbChamps().getItem(vue.getCbChamps().getSelectionIndex()));
		champPropertie=listeCorrespondanceTiers.get(champsTiers);
		tab=champPropertie.split("\\.");
		if(tab.length>0)
			champPropertie=tab[tab.length-1];
		
		classNameSelectionTiers =renvoiTypeChampsTiers(champPropertie,listeObjetTiers.get(champsTiers));
		
		typeDonnee=daoType.findByCode(classNameSelectionTiers.getName());
		if(typeDonnee!=null){
			vue.getCbMots().removeAll();
			for (TaRParamDossierIntel rParam : typeDonnee.getTaRParamDossierInteles()) {
				vue.getCbMots().add(rParam.getTaParamDossierIntel().getMot());
			}
			vue.getCbMots().select(0);
			selectionMot();
		}
		champsTiers=listeCorrespondanceTiers.get(champsTiers);
		
		
		vue.getTfCritereTiers().setText("");
		vue.getTfCritereTiers2().setText("");
		vue.getCbMots().setVisibleItemCount(vue.getCbMots().getItemCount());
		selectionMot();
	}
	public void selectionMot(){
		mot="";
		vue.getTfCritereTiers2().setEnabled(false);
		vue.getTfCritereTiers().setText("");
		vue.getTfCritereTiers2().setText("");
		if(vue.getCbMots().getSelectionIndex()!=-1)
			mot=vue.getCbMots().getItem(vue.getCbMots().getSelectionIndex());
		if(mot!=null && !mot.equals("")){
			if(taParamDao==null) taParamDao=new TaParamDossierIntelDAO(getEm());
			TaParamDossierIntel taParamDossierIntel=taParamDao.findByCode(mot);
			if(taParamDossierIntel!=null){
				sql=taParamDossierIntel.getSql();
				vue.getCbMots().setToolTipText(sql);
				vue.getTfCritereTiers2().setEnabled(taParamDossierIntel.getNbZones()>1);
			}
		}
	}
	
	
	public void selectionChampDoc(){
		String[] tab= new String[]{};
		String champPropertie;
		if (daoType==null)daoType =new TaTypeDonneeDAO(getEm());
//		champsDoc=listeTitreChampsDoc.get(vue.getCbChampsDoc().getItem(vue.getCbChampsDoc().getSelectionIndex()));
////
//		classNameSelectionDoc=renvoiTypeChampsDocument(listeObjetDocument.get(champsDoc), champsDoc);
		
		champsDoc=listeTitreChampsDoc.get(vue.getCbChampsDoc().getItem(vue.getCbChampsDoc().getSelectionIndex()));
		champPropertie=listeCorrespondanceDoc.get(champsDoc);
		tab=champPropertie.split("\\.");
		if(tab.length>0)
			champPropertie=tab[tab.length-1];
		
		classNameSelectionDoc =renvoiTypeChampsDocument(listeObjetDocument.get(champsDoc),champPropertie);
		
		typeDonneeDoc=daoType.findByCode(classNameSelectionDoc.getName());
		
		if(typeDonneeDoc!=null){
			vue.getCbMotsDoc().removeAll();
			for (TaRParamDossierIntel rParam : typeDonneeDoc.getTaRParamDossierInteles()) {
				vue.getCbMotsDoc().add(rParam.getTaParamDossierIntel().getMot());
			}
			vue.getCbMotsDoc().select(0);
		}
		//
		champsDoc=listeCorrespondanceDoc.get(champsDoc);
		vue.getTfCritereDoc().setText("");
		vue.getTfCritereDoc2().setText("");
		vue.getCbMotsDoc().setVisibleItemCount(vue.getCbMotsDoc().getItemCount());
		selectionMotDoc();
	}
	public void selectionMotDoc(){
		motDoc="";
		vue.getTfCritereDoc2().setEnabled(false);
		vue.getTfCritereDoc().setText("");
		vue.getTfCritereDoc2().setText("");
		if(vue.getCbMotsDoc().getSelectionIndex()!=-1)
			motDoc=vue.getCbMotsDoc().getItem(vue.getCbMotsDoc().getSelectionIndex());
		if(motDoc!=null && !motDoc.equals("")){
			if(taParamDao==null) taParamDao=new TaParamDossierIntelDAO(getEm());
			TaParamDossierIntel taParamDossierIntel=taParamDao.findByCode(motDoc);
			if(taParamDossierIntel!=null){
				sqlDoc=taParamDossierIntel.getSql();
				vue.getCbMotsDoc().setToolTipText(sqlDoc);
				vue.getTfCritereDoc2().setEnabled(taParamDossierIntel.getNbZones()>1);
			}
		}
	}
}

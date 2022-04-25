package fr.legrain.reglement.ecran;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import fr.legrain.document.controller.PaVisuDocumentControlleur;
import fr.legrain.document.divers.ModelRReglement;
import fr.legrain.document.divers.TypeDoc;
import fr.legrain.document.ecran.MessagesEcran;
import fr.legrain.documents.dao.IDocumentTiersDAO;
import fr.legrain.documents.dao.TaAcompte;
import fr.legrain.documents.dao.TaAcompteDAO;
import fr.legrain.documents.dao.TaAvoir;
import fr.legrain.documents.dao.TaAvoirDAO;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaRAcompte;
import fr.legrain.documents.dao.TaRAcompteDAO;
import fr.legrain.documents.dao.TaRAvoir;
import fr.legrain.documents.dao.TaRAvoirDAO;
import fr.legrain.documents.dao.TaRReglement;
import fr.legrain.documents.dao.TaRReglementDAO;
import fr.legrain.documents.dao.TaReglement;
import fr.legrain.documents.dao.TaReglementDAO;
import fr.legrain.documents.dao.TaTPaiement;
import fr.legrain.documents.dao.TaTPaiementDAO;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.facture.FacturePlugin;
import fr.legrain.facture.editor.EditorFacture;
import fr.legrain.facture.editor.EditorInputFacture;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IHMAideFacture;
import fr.legrain.gestCom.Module_Document.IHMEtat;
import fr.legrain.gestCom.Module_Document.IHMReglement;
import fr.legrain.gestCom.Module_Document.SWTTPaiement;
import fr.legrain.gestCom.Module_Tiers.SWTCompteBanque;
import fr.legrain.gestCom.Module_Tiers.SWTTiers;
import fr.legrain.gestCom.gestComBd.gestComBdPlugin;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.gestCom.librairiesEcran.swt.LibDateTime;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideRechercheSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheReglementMultiple;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheVisualisation;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideRechercheControllerSWT;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.InfosVerifSaisie;
import fr.legrain.lib.data.JPABdLgr;
import fr.legrain.lib.data.LibDate;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.data.VerrouInterface;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
import fr.legrain.lib.gui.DefaultFrameGrilleSansBouton;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.lib.gui.aide.PaAideRechercheSWT;
import fr.legrain.lib.gui.aide.PaAideSWT;
import fr.legrain.lib.gui.aide.ParamAfficheAide;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.lib.gui.grille.LgrViewerSupport;
import fr.legrain.reglement.Activator;
import fr.legrain.tiers.dao.TaCompteBanque;
import fr.legrain.tiers.dao.TaCompteBanqueDAO;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.divers.ParamAfficheTPaiement;
import fr.legrain.tiers.ecran.PaBanqueSWT;
import fr.legrain.tiers.ecran.PaTiersSWT;
import fr.legrain.tiers.ecran.PaTypePaiementSWT;
import fr.legrain.tiers.ecran.ParamAfficheBanque;
import fr.legrain.tiers.ecran.ParamAfficheTiers;
import fr.legrain.tiers.ecran.SWTPaBanqueController;
import fr.legrain.tiers.ecran.SWTPaTiersController;
import fr.legrain.tiers.ecran.SWTPaTypePaiementController;
import fr.legrain.tiers.editor.EditorBanque;
import fr.legrain.tiers.editor.EditorInputBanque;
import fr.legrain.tiers.editor.EditorInputTiers;
import fr.legrain.tiers.editor.EditorInputTypePaiement;
import fr.legrain.tiers.editor.EditorTypePaiement;
import fr.legrain.tiers.editor.TiersMultiPageEditor;

public class PaGestionReglementController extends JPABaseControllerSWTStandard implements RetourEcranListener {
	static Logger logger = Logger.getLogger(PaGestionReglementController.class
			.getName());
	
	private TaFacture masterEntity = null;
	private TaFactureDAO masterDAO = null;
	private IHMReglement swtOldReglement;
	private IHMReglement ihmRReglement;
	private IObservableValue selectedReglement;
	private ModelRReglement modelReglement;
	private LgrTableViewer tableViewerReglement;
	private TaRReglementDAO dao = null;
	private TaRReglement taRReglement = null;
	private TaRAvoir taRAvoir = null;
	private TaRAvoirDAO taRAvoirDao = null;
	private TaRAcompte taRAcompte = null;
	private TaRAcompteDAO taRAcompteDao = null;
	private DataBindingContext dbcReglement;
	private String nomClassController = this.getClass().getSimpleName();
	private boolean integrer=false;
	private boolean afficheListeReglements=true;
	private boolean enregistrementDirect=false;
//	protected Map<Control, String> mapComposantChampsReglement = null;
//	public List<Control> listeComposantReglement = null;
	private Realm realm;
	private TaTiers tiers=null;
	private Date dateDeb=null;
	private Date dateFin=null;
	
	private LgrDozerMapper<TaRReglement, IHMReglement>mapperModelToUI = new LgrDozerMapper<TaRReglement, IHMReglement>();
	private LgrDozerMapper<TaRAvoir, IHMReglement>mapperModelToUIAvoir = new LgrDozerMapper<TaRAvoir, IHMReglement>();
	private LgrDozerMapper<TaRAcompte, IHMReglement>mapperModelToUIAcompte = new LgrDozerMapper<TaRAcompte, IHMReglement>();
	private MapChangeListener changeListener = new MapChangeListener();
	
	PaVisuDocumentControlleur paVisuDocumentControlleur;
	

	
	public Map<Control, String>  getMapComposantChamps(){
		return  mapComposantChamps;
	}
	
	public List<Control> getListeComposantFocusable() {
			return listeComposantFocusable;
	}
	
	
	private PaGestionReglement vue = null;
	
	
	public PaGestionReglementController(PaGestionReglement ecran,EntityManager em) {
		if (em != null) {
			setEm(em);
		}
		try {
			setVue(ecran);
			dao = new TaRReglementDAO(getEm());
			taRAvoirDao = new TaRAvoirDAO(getEm());
			taRAcompteDao=new TaRAcompteDAO(getEm());

			this.vue.getShell().addShellListener(this);
			this.vue.getShell().addTraverseListener(new Traverse());

			initController();

		} catch (Exception e) {
			logger.debug("", e);
		}
	}
	public void setVue(PaGestionReglement vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void actAide() throws Exception {
		// TODO Auto-generated method stub
		actAide(null);
	}

	@Override
	protected void actAide(String message) throws Exception {
		if (aideDisponible()) {
			setActiveAide(true);
			boolean verrouLocal = VerrouInterface.isVerrouille();
			VerrouInterface.setVerrouille(true);
			try {
				vue.setCursor(Display.getCurrent().getSystemCursor(
						SWT.CURSOR_WAIT));
				ParamAfficheAideRechercheSWT paramAfficheAideRecherche = new ParamAfficheAideRechercheSWT();
				// paramAfficheAideRecherche.setDb(getThis().getIbTaTable().getFIBBase());
				paramAfficheAideRecherche.setMessage(message);
				// Creation de l'ecran d'aide principal
				Shell s = new Shell(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(),
						LgrShellUtil.styleLgr);
				PaAideSWT paAide = new PaAideSWT(s, SWT.NONE);
				SWTPaAideControllerSWT paAideController = new SWTPaAideControllerSWT(
						paAide);
				/** ************************************************************ */
				LgrPartListener.getLgrPartListener().setLgrActivePart(null);
				IEditorPart e = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage().openEditor(
								new EditorInputAide(), EditorAide.ID);
				LgrPartListener.getLgrPartListener().setLgrActivePart(e);
				LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
				paAideController = new SWTPaAideControllerSWT(((EditorAide) e)
						.getComposite());
				((LgrEditorPart) e).setController(paAideController);
				((LgrEditorPart) e).setPanel(((EditorAide) e).getComposite());
				/** ************************************************************ */
				JPABaseControllerSWTStandard controllerEcranCreation = null;
				ParamAffiche parametreEcranCreation = null;
				IEditorPart editorCreation = null;
				String editorCreationId = null;
				IEditorInput editorInputCreation = null;
				Shell s2 = new Shell(s, LgrShellUtil.styleLgr);

				switch (dao.getModeObjet().getMode()) {
				case C_MO_CONSULTATION:
				case C_MO_EDITION:
				case C_MO_INSERTION:
					if (getFocusCourantSWT().equals(vue.getTfFacture())) {
						editorCreationId = EditorFacture.ID_EDITOR;
						editorInputCreation = new EditorInputFacture();
						TaFactureDAO daoFacture=new TaFactureDAO(getEm());
						if(tiers!=null)
							daoFacture.setJPQLQuery("select a from TaFacture a where a.taTiers.codeTiers='"+tiers.getCodeTiers()+"'" +
									" and a.dateDocument between '"+LibDate.dateToStringSql(dateDeb)+"' and '"+LibDate.dateToStringSql(dateFin)+"'");
						else
							daoFacture.setJPQLQuery("select a from TaFacture a where a.dateDocument between '"+LibDate.dateToStringSql(dateDeb)+"' and '"+LibDate.dateToStringSql(dateFin)+"'");
//						paramAfficheAideRecherche.setForceAffichageAideRemplie(true);
						paramAfficheAideRecherche.setAfficheDetail(false);
						paramAfficheAideRecherche.setForceAffichageAideRemplie(false);
						
						ModelGeneralObjet<IHMAideFacture,TaFactureDAO,TaFacture> modelFactureAide =  
							new ModelGeneralObjet<IHMAideFacture,TaFactureDAO,TaFacture>(daoFacture,IHMAideFacture.class);
						String codeTiers="";
						String codeDocument="";
						if(tiers!=null)codeTiers=tiers.getCodeTiers();
						if(masterEntity!=null)codeDocument=masterEntity.getCodeDocument();
						modelFactureAide.setListeEntity(daoFacture.rechercheDocumentRegle(null,null,codeTiers,codeDocument));
						paramAfficheAideRecherche.setJPQLQuery(daoFacture.getJPQLQuery());
						controllerEcranCreation = this;

						paramAfficheAideRecherche.setTypeEntite(TaFacture.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_DOCUMENT);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfFacture().getText());
						paramAfficheAideRecherche.setControllerAppelant(getThis());
						paramAfficheAideRecherche.setCleListeTitre("SWTPaEditorFactureController");
						paramAfficheAideRecherche.setModel(modelFactureAide);
						paramAfficheAideRecherche.setTypeObjet(IHMAideFacture.class);
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_DOCUMENT);					
						}
					
					if (getFocusCourantSWT().equals(vue.getTfTiers())) {
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
						paramAfficheAideRecherche.setControllerAppelant(getThis());
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTiers,TaTiersDAO,TaTiers>(SWTTiers.class,dao.getEntityManager()));
								//swtPaTiersController.getModelTiers());
						paramAfficheAideRecherche.setTypeObjet(swtPaTiersController.getClassModel());
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_TIERS);
					}
					
					if (getFocusCourantSWT().equals(vue.getPaAffectationReglementSurFacture().getTfTYPE_PAIEMENT())) {
						PaTypePaiementSWT paTypePaiementSWT = new PaTypePaiementSWT(s2, SWT.NULL);
						SWTPaTypePaiementController swtPaTypePaiementController = new SWTPaTypePaiementController(
								paTypePaiementSWT);
						
						editorCreationId = EditorTypePaiement.ID;
						editorInputCreation = new EditorInputTypePaiement();
						
						paramAfficheAideRecherche.setForceAffichageAideRemplie(true);
//						paramAfficheAideRecherche.setAfficheDetail(false);
						
						ParamAfficheTPaiement paramAfficheTPaiement = new ParamAfficheTPaiement();
						paramAfficheAideRecherche.setJPQLQuery(new TaTPaiementDAO(getEm()).getJPQLQuery());
						paramAfficheTPaiement.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheTPaiement.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaTypePaiementController;
						parametreEcranCreation = paramAfficheTPaiement;
						paramAfficheAideRecherche.setTypeEntite(TaTPaiement.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_T_PAIEMENT);
						paramAfficheAideRecherche.setDebutRecherche("");
						paramAfficheAideRecherche.setControllerAppelant(getThis());
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTPaiement,
								TaTPaiementDAO, TaTPaiement>(SWTTPaiement.class, getEm()));
						paramAfficheAideRecherche.setTypeObjet(swtPaTypePaiementController
										.getClassModel());
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_T_PAIEMENT);
					}
					if (getFocusCourantSWT().equals(vue.getPaAffectationReglementSurFacture().getTfCPT_COMPTABLE())) {
						PaBanqueSWT paBanqueSWT = new PaBanqueSWT(s2,SWT.NULL);
						SWTPaBanqueController swtPaBanqueController = new SWTPaBanqueController(paBanqueSWT);
						editorCreationId = EditorBanque.ID;
						editorInputCreation = new EditorInputBanque();
						
						paramAfficheAideRecherche.setForceAffichageAideRemplie(true);
						paramAfficheAideRecherche.setAfficheDetail(false);
						
						ParamAfficheBanque paramAfficheBanque = new ParamAfficheBanque();
						paramAfficheAideRecherche.setJPQLQuery(new TaCompteBanqueDAO(getEm()).getJPQLQuery());
						paramAfficheBanque.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheBanque.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaBanqueController;
						parametreEcranCreation = paramAfficheBanque;
						paramAfficheAideRecherche.setTypeEntite(TaCompteBanque.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_COMPTE_BANQUE);
						paramAfficheAideRecherche.setDebutRecherche(vue.getPaAffectationReglementSurFacture().getTfCPT_COMPTABLE().getText());
						paramAfficheAideRecherche.setControllerAppelant(getThis());
//						paramAfficheAideRecherche.setModel(swtPaBanqueController.getModelBanque());
						
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTCompteBanque,TaCompteBanqueDAO,TaCompteBanque>
						(new TaCompteBanqueDAO(getEm()).selectCompteEntreprise(),SWTCompteBanque.class,getEm()));
						paramAfficheAideRecherche.setTypeObjet(swtPaBanqueController.getClassModel());
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_COMPTE_BANQUE);
					}
					break;
				default:
					break;
				}

				if (paramAfficheAideRecherche.getJPQLQuery() != null) {

					PaAideRechercheSWT paAideRecherche1 = new PaAideRechercheSWT(
							s, SWT.NULL);
					SWTPaAideRechercheControllerSWT paAideRechercheController1 = new SWTPaAideRechercheControllerSWT(
							paAideRecherche1);

					// Parametrage de la recherche
					paramAfficheAideRecherche
							.setFocusSWT(((PaAideRechercheSWT) paAideRechercheController1
									.getVue()).getTfChoix());
					paramAfficheAideRecherche
							.setRefCreationSWT(controllerEcranCreation);
					paramAfficheAideRecherche.setEditorCreation(editorCreation);
					paramAfficheAideRecherche
							.setEditorCreationId(editorCreationId);
					paramAfficheAideRecherche
							.setEditorInputCreation(editorInputCreation);
					paramAfficheAideRecherche
							.setParamEcranCreation(parametreEcranCreation);
					paramAfficheAideRecherche.setShellCreation(s2);
					paAideRechercheController1
							.configPanel(paramAfficheAideRecherche);
					// paramAfficheAideRecherche.setFocusDefaut(paAideRechercheController1.getVue().getTfChoix());

					// Ajout d'une recherche
					paAideController.addRecherche(paAideRechercheController1,
							paramAfficheAideRecherche.getTitreRecherche());

					// Parametrage de l'ecran d'aide principal
					ParamAfficheAideSWT paramAfficheAideSWT = new ParamAfficheAideSWT();
					ParamAfficheAide paramAfficheAide = new ParamAfficheAide();

					// enregistrement pour le retour de l'ecran d'aide
					paAideController.addRetourEcranListener(getThis());
					Control focus = vue.getShell().getDisplay()
							.getFocusControl();
					// affichage de l'ecran d'aide principal (+ ses recherches)

					dbcReglement.getValidationStatusMap().removeMapChangeListener(
							changeListener);
					/*****************************************************************/
					paAideController.configPanel(paramAfficheAide);
					/*****************************************************************/
					dbcReglement.getValidationStatusMap().addMapChangeListener(
							changeListener);
				}

			} finally {
				VerrouInterface.setVerrouille(false);
				vue.setCursor(Display.getCurrent().getSystemCursor(
						SWT.CURSOR_ARROW));
			}
		}
	}



	@Override
	protected void actFermer() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void actImprimer() throws Exception {
		// TODO Auto-generated method stub

	}

	protected void actSelection() throws Exception {
		try{
			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().
			getActivePage().openEditor(new fr.legrain.visualisation.editor.EditorInputSelectionVisualisation(), 
					fr.legrain.visualisation.editor.EditorSelectionVisualisation.ID);
			LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);

			ParamAfficheVisualisation paramAfficheSelectionVisualisation = new ParamAfficheVisualisation();
			paramAfficheSelectionVisualisation.setEcranAppelant(getThis());
			paramAfficheSelectionVisualisation.setModule("ReglementSurListe");
			paramAfficheSelectionVisualisation.setNomClassController(nomClassController);
			paramAfficheSelectionVisualisation.setNomRequete(Const.C_NOM_VU_FACTURE);

			((LgrEditorPart)e).setPanel(((LgrEditorPart)e).getControllerSelection().getVue());
			((LgrEditorPart)e).getControllerSelection().configPanel(paramAfficheSelectionVisualisation);

		}catch (Exception e) {
			logger.error("",e);
		}	
	}
	

	@Override
	public void actRefresh() throws Exception {
		actRefresh(masterEntity);
	}
	
//	public void actRefresh(TaRReglement taRReglement) throws Exception {
//		if (tableViewerReglement==null)
//			bindReglements();
//		this.taRReglement=taRReglement;
//		getEm().clear();
//		tiers=new TaTiersDAO(getEm()).findByCode((String) vue.getTfTiers().getText().toUpperCase());
//		WritableList writableListReglement = new WritableList(modelReglement.
//				remplirListeReglements(tiers,vue.getTfDateDebutPeriode().getSelection(),
//						vue.getTfDateFinPeriode().getSelection(), getEm()), IHMReglement.class);
//		tableViewerReglement.setInput(writableListReglement);
//		if(taRReglement==null || (taRReglement.getEtatDeSuppression()&IHMEtat.multiple)!=0){
//			tableViewerReglement.selectionGrille(0);
//		} else {
//			Object valeurRecherchee =rechercheReglement(Const.C_CODE_REGLEMENT, 
//					taRReglement.getTaReglement().getCodeReglement());
//			if(valeurRecherchee!=null)
//				tableViewerReglement.setSelection(new StructuredSelection());
//			else tableViewerReglement.selectionGrille(0);
//		}
//		changementDeSelection();
//		initEtatBouton();
//}
	@Override
	protected void initComposantsVue() throws ExceptLgr {
		// TODO Auto-generated method stub

	}


	@Override
	public boolean onClose() throws ExceptLgr {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ResultAffiche configPanel(ParamAffiche param) {
		parametrageDefautSelection();
		if(param instanceof ParamAfficheReglementMultiple){
			if(((ParamAfficheReglementMultiple)param).getIdTiers()!=0) {
				tiers=new TaTiersDAO(getEm()).findById(((ParamAfficheReglementMultiple)param).getIdTiers());
			}
			if(((ParamAfficheReglementMultiple)param).getDateDeb()!=null){
				setDateDeb(((ParamAfficheReglementMultiple)param).getDateDeb());
				
			}
			if(((ParamAfficheReglementMultiple)param).getDateFin()!=null){
				setDateFin(((ParamAfficheReglementMultiple)param).getDateFin());
				
			}
			if(((ParamAfficheReglementMultiple)param).getIdFacture()!=0) {
				setMasterEntity(new TaFactureDAO(getEm()).findById(((ParamAfficheReglementMultiple)param).getIdFacture()));
				
			}			
		}else{
			if(param.getCodeDocument()!=null){
				TaReglementDAO daoReglement =new TaReglementDAO(getEm());
				TaReglement reglement = daoReglement.findByCode(param.getCodeDocument());
				tiers=reglement.getTaTiers();
				//setDateDeb(reglement.getDateDocument());
				//setDateFin(reglement.getDateDocument());
				
				for (TaRReglement rr : reglement.getTaRReglements()) {
					if(rr.getTaFacture()!=null)setMasterEntity(rr.getTaFacture());
				}
				
			}
		}
		if(tiers!=null) vue.getTfTiers().setText(tiers.getCodeTiers());
//		vue.getTfDateDebutPeriode().setSelection(getDateDeb());
//		vue.getTfDateFinPeriode().setSelection(getDateFin());
		LibDateTime.setDate(vue.getTfDateDebutPeriode(), getDateDeb());
		LibDateTime.setDate(vue.getTfDateFinPeriode(), getDateFin());
		if(masterEntity!=null) vue.getTfFacture().setText(masterEntity.getCodeDocument());
		// TODO Auto-generated method stub
		if (tableViewerReglement==null)
			bindReglements();
		else
			try {
//				if(((ParamAfficheReglementMultiple)param).getCodeReglement()!=null) {
//					TaRReglement taRReglement = dao.findByCode(((ParamAfficheReglementMultiple)param).getCodeReglement());
//				}
				if(masterEntity!=null) {
					actRefresh(masterEntity);
				}
			} catch (Exception e) {
				logger.error("",e);
			}
			initEtatBouton(true);
		return null;
	}
	public void parametrageDefautSelection(){
		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
		setDateDeb(taInfoEntreprise.getDatedebInfoEntreprise());
		setDateFin(taInfoEntreprise.getDatefinInfoEntreprise());
//		vue.getTfDateDebutPeriode().setSelection(taInfoEntreprise.getDatedebInfoEntreprise());
//		vue.getTfDateFinPeriode().setSelection(taInfoEntreprise.getDatefinInfoEntreprise());
		LibDateTime.setDate(vue.getTfDateDebutPeriode(), taInfoEntreprise.getDatedebInfoEntreprise());
		LibDateTime.setDate(vue.getTfDateDebutPeriode(), taInfoEntreprise.getDatefinInfoEntreprise());
	}
	@Override
	public Composite getVue() {
		// TODO Auto-generated method stub
		return vue;
	}
	@Override
	protected void sortieChamps() {
		if(dao.dataSetEnModif()&& taRReglement!=null && selectedReglement.getValue()!=null){
			if(((IHMReglement) selectedReglement.getValue()).getTypeDocument()==TaFacture.TYPE_DOC)
			mapperModelToUI.map(taRReglement,
					((IHMReglement) selectedReglement.getValue()));
			else if(((IHMReglement) selectedReglement.getValue()).getTypeDocument()==TaAvoir.TYPE_DOC)
				mapperModelToUIAvoir.map(taRAvoir,
						((IHMReglement) selectedReglement.getValue()));
			else if(((IHMReglement) selectedReglement.getValue()).getTypeDocument()==TaAcompte.TYPE_DOC)
				mapperModelToUIAcompte.map(taRAcompte,
						((IHMReglement) selectedReglement.getValue()));
		}

	}
	public IStatus validateUIField(String nomChamp, Object value) {
		String validationContext = "REGLEMENT";
		try {
			IStatus s = null;
			if(nomChamp.equals(Const.C_CODE_DOCUMENT)&&
					getFocusCourantSWT().equals(vue.getTfFacture())){
				TaFactureDAO daoFacture = new TaFactureDAO(getEm());
				TaFacture f = new TaFacture(true);
				PropertyUtils.setSimpleProperty(f, nomChamp, value);
				s = daoFacture.validate(f, nomChamp, "FACTURE");
				if (s.getSeverity() != IStatus.ERROR) {
					if(getMasterEntity()==null || !f.getCodeDocument().equals(getMasterEntity().getCodeDocument())){
						if(!f.getCodeDocument().equals("")){
							f = daoFacture.findByCode((String) value);
							setMasterEntity(f);
							vue.getTfFacture().setText(f.getCodeDocument());
							vue.getTfTiers().setText(f.getTaTiers().getCodeTiers());
							tiers=f.getTaTiers();
						}else {
							setMasterEntity(null);
							vue.getTfFacture().setText(f.getCodeDocument());
						}
					}
				}
			} else		
				if (nomChamp.equals(Const.C_CODE_TIERS) ) {
					TaTiersDAO dao = new TaTiersDAO(getEm());
					TaTiers f = new TaTiers();
					PropertyUtils.setSimpleProperty(f, nomChamp, value);
					s = dao.validate(f, nomChamp, "TIERS");
					if (s.getSeverity() != IStatus.ERROR) {
						if(tiers==null || !f.getCodeTiers().equals(tiers.getCodeTiers())){
							if(!f.getCodeTiers().equals("")){
								f = dao.findByCode((String) value);
								tiers=f;
								vue.getTfTiers().setText(f.getCodeTiers());
								setMasterEntity(null);
								vue.getTfFacture().setText("");
							}else {
								tiers=null;
								vue.getTfTiers().setText(f.getCodeTiers());
								setMasterEntity(null);
							}
						}
					}
					dao = null;
				} 
				else	
					if (nomChamp.equals(Const.C_CODE_T_PAIEMENT) ) {
						if(swtOldReglement.getTypeDocument()==TaAvoir.TYPE_DOC || swtOldReglement.getTypeDocument()==TaAcompte.TYPE_DOC){
							return new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
						}
						TaTPaiementDAO dao = new TaTPaiementDAO(getEm());
						TaTPaiement f = new TaTPaiement();
						PropertyUtils.setSimpleProperty(f, nomChamp, value);
						s = dao.validate(f, nomChamp, validationContext);
						if (s.getSeverity() != IStatus.ERROR) {
							f = dao.findByCode((String) value);
							Boolean changement=false;
							if(taRReglement.getTaReglement().getTaTPaiement()==null){
								taRReglement.getTaReglement().setTaTPaiement(f);
								changement=true;
							}
							else{
								changement=!f.getCodeTPaiement().equals(taRReglement.getTaReglement().getTaTPaiement().getCodeTPaiement());
								taRReglement.getTaReglement().setTaTPaiement(f);
							}
							if(changement){
								taRReglement.getTaReglement().setLibelleDocument(f.getLibTPaiement());
								((IHMReglement)selectedReglement.getValue()).setLibelleDocument(f.getLibTPaiement());
							}
//							taRReglement.getTaReglement().setTaTPaiement(f);
						}
						dao = null;
					}
					else  if (nomChamp.equals(Const.C_NET_TTC_CALC) ) {
						if(swtOldReglement.getTypeDocument()==TaAvoir.TYPE_DOC || swtOldReglement.getTypeDocument()==TaAcompte.TYPE_DOC){
							return new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
						}
						TaReglement u =new TaReglement();
						PropertyUtils.setSimpleProperty(u, nomChamp, value);
						s = new TaReglementDAO(getEm()).validate(u, nomChamp,validationContext);
						if (s.getSeverity() != IStatus.ERROR) {
							PropertyUtils.setSimpleProperty(taRReglement.getTaReglement(), nomChamp, value);
							if(taRReglement.getAffectation().compareTo(taRReglement.getTaReglement().getNetTtcCalc())>0)
							{
								taRReglement.setAffectation(taRReglement.getTaReglement().getNetTtcCalc());
							}
						}	
					}
					else  if (nomChamp.equals(Const.C_MONTANT_AFFECTE) ) {
						if(((IHMReglement)selectedReglement.getValue()).getTypeDocument()==TaFacture.TYPE_DOC){
							TaRReglement u =new TaRReglement();
							PropertyUtils.setSimpleProperty(u, nomChamp, value);
							s = dao.validate(u, nomChamp, "R_REGLEMENT");
							if (s.getSeverity() != IStatus.ERROR) {
								PropertyUtils.setSimpleProperty(taRReglement, nomChamp, value);
								taRReglement.setTaReglement(taRReglement.getTaReglement());
								if(masterEntity!=null){
									taRReglement.setTaFacture(masterEntity);
									//si affectation supérieure au reste à regler de la facture
									if(masterEntity.getRegleCompletDocument(taRReglement).add(taRReglement.getAffectation()).
											compareTo(masterEntity.getNetAPayer())>0){
										taRReglement.setAffectation(masterEntity.getNetAPayer().subtract(masterEntity.getRegleCompletDocument(taRReglement)));
									}
								}else if(taRReglement.getTaFacture()!=null){
									if(taRReglement.getTaFacture().getRegleCompletDocument(taRReglement).add(taRReglement.getAffectation()).
											compareTo(taRReglement.getTaFacture().getNetAPayer())>0){
										taRReglement.setAffectation(taRReglement.getTaFacture().getNetAPayer().subtract(taRReglement.getTaFacture().getRegleCompletDocument(taRReglement)));
									}
								}
								//si affectation supérieure au reste à regler du règlement
								if(taRReglement.getTaReglement().calculAffectationTotale(taRReglement).add(taRReglement.getAffectation()).
										compareTo(taRReglement.getTaReglement().getNetTtcCalc())>0){
									taRReglement.setAffectation(taRReglement.getTaReglement().getNetTtcCalc().subtract(taRReglement.getTaReglement().calculAffectationTotale(taRReglement)));
								}
								if(taRReglement.getTaReglement().getNetTtcCalc()==BigDecimal.valueOf(0))
									taRReglement.getTaReglement().setNetTtcCalc(taRReglement.getAffectation());

								if(taRReglement.getAffectation().compareTo(taRReglement.getTaReglement().getNetTtcCalc())>0)
									taRReglement.setAffectation(taRReglement.getTaReglement().getNetTtcCalc());
								
//								if(taRReglement.getAffectation().compareTo(taRReglement.getTaReglement().getNetTtcCalc().
//										subtract(taRReglement.getTaReglement().calculAffectationTotale(taRReglement)))>0)
//									taRReglement.setAffectation(taRReglement.getTaReglement().getNetTtcCalc().
//											subtract(taRReglement.getTaReglement().calculAffectationTotale(taRReglement)));
//								BigDecimal dif=taRReglement.getTaFacture().getNetTtcCalc().
//								subtract(taRReglement.getTaFacture().calculRegleDocumentComplet());
//								if(dif.signum()<0)
//									taRReglement.setAffectation(taRReglement.getAffectation().
//											subtract(dif.abs()));
							}
							}else if(((IHMReglement)selectedReglement.getValue()).getTypeDocument()==TaAvoir.TYPE_DOC){
								TaRAvoir r =new TaRAvoir();
								PropertyUtils.setSimpleProperty(r, nomChamp, value);
								s = new TaRAvoirDAO(getEm()).validate(r, nomChamp, "R_AVOIR");
								if (s.getSeverity() != IStatus.ERROR) {
									PropertyUtils.setSimpleProperty(taRAvoir, nomChamp, value);
									if(taRAvoir.getTaAvoir().getNetTtcCalc()==BigDecimal.valueOf(0))
										taRAvoir.getTaAvoir().setNetTtcCalc(taRAvoir.getAffectation());

									if(taRAvoir.getAffectation().compareTo(taRAvoir.getTaAvoir().getNetTtcCalc())>0)
										taRAvoir.setAffectation(taRAvoir.getTaAvoir().getNetTtcCalc());
									
									if(taRAvoir.getAffectation().compareTo(taRAvoir.getTaAvoir().getNetTtcCalc().
											subtract(taRAvoir.getTaAvoir().calculAffectationTotale(taRAvoir)))>0)
										taRAvoir.setAffectation(taRAvoir.getTaAvoir().getNetTtcCalc().
												subtract(taRAvoir.getTaAvoir().calculAffectationTotale(taRAvoir)));
//									if(taRAvoir.getAffectation().compareTo(taRAvoir.getTaAvoir().getResteAAffecter())>0)
//										taRAvoir.setAffectation(taRAvoir.getTaAvoir().getResteAAffecter());
									BigDecimal resteSurFacture=taRAvoir.getTaFacture().getNetTtcCalc().subtract(taRAvoir.getTaFacture().
											calculSommeReglementsComplet().add(taRAvoir.getTaFacture().getAcomptes().add(
													taRAvoir.getTaFacture().calculSommeAvoir(taRAvoir))));
									
									if(taRAvoir.getAffectation().compareTo(resteSurFacture)>0)
										taRAvoir.setAffectation(resteSurFacture);
									
									((IHMReglement)selectedReglement.getValue()).setAffectation(taRAvoir.getAffectation());
									
								}
							}else if(((IHMReglement)selectedReglement.getValue()).getTypeDocument()==TaAcompte.TYPE_DOC){
								TaRAcompte r =new TaRAcompte();
								PropertyUtils.setSimpleProperty(r, nomChamp, value);
								s = new TaRAcompteDAO(getEm()).validate(r, nomChamp, "R_ACOMPTE");
								if (s.getSeverity() != IStatus.ERROR) {
									PropertyUtils.setSimpleProperty(taRAcompte, nomChamp, value);
									if(taRAcompte.getTaAcompte().getNetTtcCalc()==BigDecimal.valueOf(0))
										taRAcompte.getTaAcompte().setNetTtcCalc(taRAcompte.getAffectation());

									if(taRAcompte.getAffectation().compareTo(taRAcompte.getTaAcompte().getNetTtcCalc())>0)
										taRAcompte.setAffectation(taRAcompte.getTaAcompte().getNetTtcCalc());
									
									if(taRAcompte.getAffectation().compareTo(taRAcompte.getTaAcompte().getNetTtcCalc().
											subtract(taRAcompte.getTaAcompte().calculAffectationTotale(taRAcompte)))>0)
										taRAcompte.setAffectation(taRAcompte.getTaAcompte().getNetTtcCalc().
												subtract(taRAcompte.getTaAcompte().calculAffectationTotale(taRAcompte)));

									BigDecimal resteSurFacture=taRAcompte.getTaFacture().getNetTtcCalc().subtract(taRAcompte.getTaFacture().
											calculSommeReglementsComplet().add(taRAcompte.getTaFacture().getAcomptes().add(
													taRAcompte.getTaFacture().calculSommeAcomptes(taRAcompte))));
									
									if(taRAcompte.getAffectation().compareTo(resteSurFacture)>0)
										taRAcompte.setAffectation(resteSurFacture);
									
									((IHMReglement)selectedReglement.getValue()).setAffectation(taRAcompte.getAffectation());
									
								}
							}
						
					}else if (nomChamp.equals(Const.C_DATE_LIV_DOCUMENT) ) {
						if(swtOldReglement.getTypeDocument()==TaAvoir.TYPE_DOC || swtOldReglement.getTypeDocument()==TaAcompte.TYPE_DOC){
							return new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
						}
						TaReglement u =new TaReglement();
						PropertyUtils.setSimpleProperty(u, nomChamp, value);
						s = new TaReglementDAO(getEm()).validate(u, nomChamp, validationContext);
						if (s.getSeverity() != IStatus.ERROR) {
							PropertyUtils.setSimpleProperty(taRReglement.getTaReglement(), nomChamp, value);
						}	
					}else if (nomChamp.equals(Const.C_DATE_DOCUMENT)) {
						if(swtOldReglement.getTypeDocument()==TaAvoir.TYPE_DOC || swtOldReglement.getTypeDocument()==TaAcompte.TYPE_DOC){
							return new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
						}
						TaReglement u =new TaReglement();
						PropertyUtils.setSimpleProperty(u, nomChamp, value);
						s = new TaReglementDAO(getEm()).validate(u, nomChamp, validationContext);
						if (s.getSeverity() != IStatus.ERROR) {
							PropertyUtils.setSimpleProperty(taRReglement.getTaReglement(), nomChamp, value);
						}	
					}else if (nomChamp.equals(Const.C_LIBELLE_DOCUMENT) ) {
						if(swtOldReglement.getTypeDocument()==TaAvoir.TYPE_DOC || swtOldReglement.getTypeDocument()==TaAcompte.TYPE_DOC){
							return new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
						}
						TaReglement u =new TaReglement();
						PropertyUtils.setSimpleProperty(u, nomChamp, value);
						s = new TaReglementDAO(getEm()).validate(u, nomChamp, validationContext);
						if (s.getSeverity() != IStatus.ERROR) {		
							taRReglement.getTaReglement().setLibelleDocument(u.getLibelleDocument());
						}	
					}else if (nomChamp.equals(Const.C_CODE_DOCUMENT)) {
						if(swtOldReglement.getTypeDocument()==TaAvoir.TYPE_DOC || swtOldReglement.getTypeDocument()==TaAcompte.TYPE_DOC){
							return new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
						}
						TaReglement u =new TaReglement();
						PropertyUtils.setSimpleProperty(u, nomChamp, value);
						s = new TaReglementDAO(getEm()).validate(u, nomChamp, validationContext);
						if (s.getSeverity() != IStatus.ERROR) {	
							PropertyUtils.setSimpleProperty(taRReglement.getTaReglement(), nomChamp, value);
						}	
					}else if (nomChamp.equals(Const.C_COMPTE)) {
						if(swtOldReglement.getTypeDocument()==TaAvoir.TYPE_DOC || swtOldReglement.getTypeDocument()==TaAcompte.TYPE_DOC){
							return new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
						}
						TaCompteBanque u =new TaCompteBanque();
						TaCompteBanqueDAO dao = new TaCompteBanqueDAO(getEm());
						PropertyUtils.setSimpleProperty(u, nomChamp, value);
						s = dao.validate(u, nomChamp, validationContext);
						if (s.getSeverity() != IStatus.ERROR) {
							if(u.getCompte()!=null && u.getCompte()!="")
								u = dao.findByTiersEntreprise(u.getCompte());
							taRReglement.getTaReglement().setTaCompteBanque(u);
						}	
					}  

			if (s.getSeverity() != IStatus.ERROR) {
				if((taRReglement!=null ||taRAvoir!=null ||taRAcompte!=null) && selectedReglement.getValue()!=null)
					if(((IHMReglement)selectedReglement.getValue()).getTypeDocument()==TaFacture.TYPE_DOC){
						mapperModelToUI.map(taRReglement,
								((IHMReglement) selectedReglement.getValue()));
					}else if(((IHMReglement)selectedReglement.getValue()).getTypeDocument()==TaAvoir.TYPE_DOC){
						mapperModelToUIAvoir.map(taRAvoir,
								((IHMReglement) selectedReglement.getValue()));
					}else if(((IHMReglement)selectedReglement.getValue()).getTypeDocument()==TaAcompte.TYPE_DOC){
						mapperModelToUIAcompte.map(taRAcompte,
								((IHMReglement) selectedReglement.getValue()));
					}


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


	//gestion de l'enregistrement dans l'expand d'affectation
	@Override
	public void actAnnuler() throws Exception {
		switch (dao.getModeObjet().getMode()) {
		case C_MO_INSERTION:
		break;
		case C_MO_EDITION:
			int rang = modelReglement.getListeObjet().indexOf(selectedReglement.getValue());
			remetTousLesChampsApresAnnulationSWT(dbcReglement);
			if(swtOldReglement.getTypeDocument()==TaFacture.TYPE_DOC){
			if(swtOldReglement!=null)new LgrDozerMapper<IHMReglement, TaRReglement>().map(swtOldReglement, taRReglement);
			}
			else if(swtOldReglement.getTypeDocument()==TaAvoir.TYPE_DOC) {
				new LgrDozerMapper<IHMReglement, TaRAvoir>().map(swtOldReglement, taRAvoir);
			}else if(swtOldReglement.getTypeDocument()==TaAcompte.TYPE_DOC){
				new LgrDozerMapper<IHMReglement, TaRAcompte>().map(swtOldReglement, taRAcompte);
			}
			dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
			actRefresh(masterEntity);
			setFocusCourantSWT(vue.getTfTiers());
			break;
		default:
			break;
		}
	}

	//gestion de l'enregistrement dans l'expand d'affectation
	@Override
	public void actEnregistrer() throws Exception {
		EntityTransaction transaction = dao.getEntityManager().getTransaction();
		try {
			try{
				ctrlTousLesChampsAvantEnregistrementSWT(dbcReglement);
				if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
						|| (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {

					dao.begin(transaction);
					if(((IHMReglement)selectedReglement.getValue()).getTypeDocument()==TaAcompte.TYPE_DOC){
						taRAcompte=taRAcompteDao.enregistrerMerge(taRAcompte);			
						taRAcompteDao.commit(transaction);
						dao.commit(transaction);
					}else
					if(((IHMReglement)selectedReglement.getValue()).getTypeDocument()==TaAvoir.TYPE_DOC){
						taRAvoir=taRAvoirDao.enregistrerMerge(taRAvoir);			
						taRAvoirDao.commit(transaction);
						dao.commit(transaction);
					}else{
						taRReglement=dao.enregistrerMerge(taRReglement);			
						dao.commit(transaction);
					}
					transaction = null;
					actRefresh(masterEntity);
					initEtatBoutonCommand(true, modelReglement.getListeObjet());
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
	

	//gestion de l'enregistrement dans l'expand d'affectation
	@Override
	protected void actInserer() throws Exception {

	}

	

	//gestion de l'enregistrement dans l'expand d'affectation
	@Override
	protected void actModifier() throws Exception {
		try {
			if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {		
				setSwtOldReglement();
				if(swtOldReglement.getTypeDocument()==TaFacture.TYPE_DOC)
				dao.modifier(taRReglement);
				else if(swtOldReglement.getTypeDocument()==TaAvoir.TYPE_DOC){
					taRAvoirDao.modifier(taRAvoir);
					dao.modifier(new TaRReglement());
				}else if(swtOldReglement.getTypeDocument()==TaAcompte.TYPE_DOC){
					taRAcompteDao.modifier(taRAcompte);
					dao.modifier(new TaRReglement());
				}
				initEtatBoutonCommand(true, modelReglement.getListeObjet());
			}
		} catch (Exception e1) {
			vue.getPaAffectationReglementSurFacture().getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		}
	}
	
	public void setSwtOldReglement() {
		if (selectedReglement.getValue() != null)
			this.swtOldReglement = IHMReglement.copy((IHMReglement) selectedReglement.getValue());
		else {
			if(tableViewerReglement.selectionGrille(0)){
				this.swtOldReglement = IHMReglement.copy((IHMReglement) selectedReglement.getValue());
				tableViewerReglement.setSelection(new StructuredSelection(
						(IHMReglement) selectedReglement.getValue()), true);
			}
		}
		if(selectedReglement.getValue()!=null)
			if(swtOldReglement.getTypeDocument()==TaFacture.TYPE_DOC)
			taRReglement=dao.findById(((IHMReglement)selectedReglement.getValue()).getId());
			
			else if(swtOldReglement.getTypeDocument()==TaAvoir.TYPE_DOC) 
				taRAvoir=taRAvoirDao.findById(((IHMReglement)selectedReglement.getValue()).getId());
			else if(swtOldReglement.getTypeDocument()==TaAcompte.TYPE_DOC)
				taRAcompte=taRAcompteDao.findById(((IHMReglement)selectedReglement.getValue()).getId());
	}
	
	
    private IDocumentTiers rechercheTaRReglement(IHMReglement selection){
    	TaRReglementDAO dao = new TaRReglementDAO(new JPABdLgr().getEntityManager());
    	TaReglementDAO daoDocument = new TaReglementDAO(new JPABdLgr().getEntityManager());
    	TaRReglement r=dao.findById(selection.getId());
    	if(r==null)return null;
    	return ((IDocumentTiers)((IDocumentTiersDAO)daoDocument).findById(r.getTaReglement().getIdDocument()));
//    	return listeDocument.get(0);
    }

    private IDocumentTiers rechercheTaRAvoir(IHMReglement selection){
    	 List<IDocumentTiers> listeDocument = null;
    	TaRAvoirDAO dao = new TaRAvoirDAO(new JPABdLgr().getEntityManager());
    	TaAvoirDAO daoDocument = new TaAvoirDAO(new JPABdLgr().getEntityManager());
    	TaRAvoir r=dao.findById(selection.getId());
    	listeDocument= ((IDocumentTiersDAO)daoDocument).rechercheDocument(r.getTaAvoir().getCodeDocument(),
    			r.getTaAvoir().getCodeDocument());
    	for (IDocumentTiers iDocumentTiersComplet : listeDocument) {
    		return iDocumentTiersComplet;
		}
    	return null;
//    	return (((IDocumentTiersComplet)((IDocumentTiersDAO)daoDocument).findById(r.getTaAvoir().getIdDocument())));
    }
    
    private IDocumentTiers rechercheTaRAcompte(IHMReglement selection){
   	 List<IDocumentTiers> listeDocument = null;
   	TaRAcompteDAO dao = new TaRAcompteDAO(new JPABdLgr().getEntityManager());
   	TaAcompteDAO daoDocument = new TaAcompteDAO(new JPABdLgr().getEntityManager());
   	TaRAcompte r=dao.findById(selection.getId());
   	listeDocument= ((IDocumentTiersDAO)daoDocument).rechercheDocument(r.getTaAcompte().getCodeDocument(),
   			r.getTaAcompte().getCodeDocument());
   	for (IDocumentTiers iDocumentTiersComplet : listeDocument) {
   		return iDocumentTiersComplet;
		}
   	return null;
//   	return (((IDocumentTiersComplet)((IDocumentTiersDAO)daoDocument).findById(r.getTaAvoir().getIdDocument())));
   }
	//gestion de l'enregistrement dans l'expand d'affectation
	@Override
	protected void actSupprimer() throws Exception {
		EntityTransaction transaction = dao.getEntityManager().getTransaction();
		try {
			VerrouInterface.setVerrouille(true);
					if (MessageDialog.openConfirm(vue.getShell(), MessagesEcran
							.getString("Message.Attention"), fr.legrain.document.controller.MessagesEcran
							.getString("Document.Message.SupprimerAffectation"))) {
						
						setSwtOldReglement();
						if(swtOldReglement.getTypeDocument()==TaFacture.TYPE_DOC){
							dao.begin(transaction);
						taRReglement.getTaFacture().removeReglement(taRReglement);
//						taRReglement.setTaFacture(null);
						taRReglement.getTaReglement().removeReglement(taRReglement);
						dao.supprimer(taRReglement);
						if(taRReglement.getTaReglement().getTaRReglements().size()==0)
						{
							new TaReglementDAO(getEm()).supprimer(taRReglement.getTaReglement());
						}
						dao.commit(transaction);
						taRReglement=null;
						}else if(swtOldReglement.getTypeDocument()==TaAvoir.TYPE_DOC){
							dao.begin(transaction);
							if(masterEntity!=null && masterEntity.getCodeDocument().equals(taRAvoir.getTaFacture().getCodeDocument()))
								masterEntity.removeRAvoir(taRAvoir);
							
							taRAvoir.getTaFacture().removeRAvoir(taRAvoir);
							
							taRAvoir.setAffectation(BigDecimal.valueOf(0));
							taRAvoir.getTaAvoir().setResteAReglerComplet(taRAvoir.getTaAvoir().calculResteARegler());
							taRAvoir.getTaAvoir().removeRAvoir(taRAvoir);
							taRAvoirDao.supprimer(taRAvoir);
							//taRAvoir.setTaFacture(null);
							//taRAvoir.setTaAvoir(null);
							
							dao.commit(transaction);
							taRAvoir=null;
							if(masterEntity!=null)dao.getEntityManager().refresh(masterEntity);
							//fireUpdateMasterEntity(new UpdateMasterEntityEvent(this));
							
						}else if(swtOldReglement.getTypeDocument()==TaAcompte.TYPE_DOC){
							dao.begin(transaction);
							if(masterEntity!=null && masterEntity.getCodeDocument().equals(taRAcompte.getTaFacture().getCodeDocument()))
								masterEntity.removeRAcompte(taRAcompte);
							
							taRAcompte.getTaFacture().removeRAcompte(taRAcompte);
							
							taRAcompte.setAffectation(BigDecimal.valueOf(0));
							taRAcompte.getTaAcompte().setResteARegler(taRAcompte.getTaAcompte().calculResteARegler());
							taRAcompte.getTaAcompte().removeRAcompte(taRAcompte);
							taRAcompteDao.supprimer(taRAcompte);
							//taRAvoir.setTaFacture(null);
							//taRAvoir.setTaAvoir(null);
							
							dao.commit(transaction);
							taRAcompte=null;
							if(masterEntity!=null)dao.getEntityManager().refresh(masterEntity);
							//fireUpdateMasterEntity(new UpdateMasterEntityEvent(this));
							
						}
							
					dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
					tableViewerReglement.selectionGrille(0);
					actRefresh();
					changementDeSelection();
					initEtatBoutonCommand(true, modelReglement.getListeObjet());
					}
//				}
		} catch (ExceptLgr e1) {
			vue.getPaAffectationReglementSurFacture().getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		}finally {
			if(transaction!=null && transaction.isActive()) {
				transaction.rollback();
			}
			initEtatBouton();
			VerrouInterface.setVerrouille(false);
		}
	}
	
	
	
	public void actRefresh(TaFacture taDocument) throws Exception {
		try {
			vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));	
			if (tableViewerReglement==null)
				bindReglements();
			setMasterEntity(taDocument);
			//getEm().clear();
			WritableList writableListReglement=null;
			tiers = new TaTiersDAO(getEm()).findByCode((String) vue.getTfTiers().getText().toUpperCase());
			if(masterEntity==null){
//				boolean continuer = true;
//				if(tiers==null) {
//					continuer = !MessageDialog.openQuestion(
//							PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
//							"Bureau de gestion",
//							"Attention, il n'y a aucun tiers sélectionné.\n" +
//							"Si vous continuez sans sélectionné de tiers, la recherche peut être longue.\n\n" +
//							"Voulez vous sélectionné un tiers avant de continuer ?");
//				}
//				if(continuer) {
					writableListReglement = new WritableList(modelReglement.
							remplirListeReglements(tiers,
									LibDateTime.getDate(vue.getTfDateDebutPeriode()),
											LibDateTime.getDate(vue.getTfDateFinPeriode()), getEm()), IHMReglement.class);
//				}
			}
			else{
				writableListReglement = new WritableList(modelReglement.
						remplirListeReglementsFactureTous(masterEntity), IHMReglement.class);
			}
			tableViewerReglement.setInput(writableListReglement);
			if(taRReglement==null || (taRReglement.getEtatDeSuppression()&IHMEtat.multiple)!=0){
				tableViewerReglement.selectionGrille(0);
			} else {
				Object valeurRecherchee =rechercheReglement(Const.C_CODE_DOCUMENT, 
						taRReglement.getTaReglement().getCodeDocument());
				if(valeurRecherchee!=null)
					tableViewerReglement.setSelection(new StructuredSelection());
				else tableViewerReglement.selectionGrille(0);
			}
			changementDeSelection();
			if(getFocusCourantSWT()!=null &&(getFocusCourantSWT().equals(vue.getTfTiers())||
					getFocusCourantSWT().equals(vue.getTfFacture())))
				initEtatBoutonCommand(false, modelReglement.getListeObjet());
			else
				initEtatBoutonCommand(true, modelReglement.getListeObjet());
		} finally {
			vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
		}
	}

	
	public TaFacture getMasterEntity() {
		return masterEntity;
	}

	public void setMasterEntity(TaFacture masterEntity) {
		this.masterEntity = masterEntity;
	}

	public TaFactureDAO getMasterDAO() {
		return masterDAO;
	}

	public void setMasterDAO(TaFactureDAO masterDAO) {
		if(masterDAO!=null){
			this.masterDAO = masterDAO;
			setEm(masterDAO.getEntityManager());
			dao = new TaRReglementDAO(getEm());
			taRAvoirDao=new TaRAvoirDAO(getEm());
			taRAcompteDao=new TaRAcompteDAO(getEm());
			setDaoStandard(dao);
		}
	}


	public IHMReglement getSwtOldReglement() {
		return swtOldReglement;
	}

	public void setSwtOldReglement(IHMReglement swtOldReglement) {
		this.swtOldReglement = swtOldReglement;
	}

	public IHMReglement getIhmRReglement() {
		return ihmRReglement;
	}

	public void setIhmRReglement(IHMReglement ihmRReglement) {
		this.ihmRReglement = ihmRReglement;
	}

	


	@Override
	public void modifMode(){
		if (!VerrouInterface.isVerrouille() ){
			try {
				if(!dao.dataSetEnModif()) {
					if(!(modelReglement.getListeObjet().size()==0)) {
						actModifier();
					} 
					initEtatBouton();
				}
			} catch (Exception e1) {
				logger.error("",e1);
			}				
		} 
	}

	
	public Object rechercheReglement(String propertyName, Object value) {
		boolean trouve = false;
		int i = 0;
		while(!trouve && i<modelReglement.getListeObjet().size()){
			try {
				if(PropertyUtils.getProperty(modelReglement.getListeObjet().get(i), propertyName).equals(value)) {
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
			return modelReglement.getListeObjet().get(i);
		else 
			return null;

	}
	private void changementDeSelection() {
		try {
			TaRReglement r=null;
			TaRAvoir rA=null;
			setSwtOldReglement();
			initEtatComposant();
			IDocumentTiers document=null;
			if(paVisuDocumentControlleur!=null && (selectedReglement!=null && selectedReglement.getValue()!=null)){
				if(((IHMReglement)selectedReglement.getValue()).getTypeDocument()==TaAvoir.TYPE_DOC){
					document =rechercheTaRAvoir(((IHMReglement)selectedReglement.getValue()));
				}
				else if(((IHMReglement)selectedReglement.getValue()).getTypeDocument()==TaAcompte.TYPE_DOC){
					document =rechercheTaRAcompte(((IHMReglement)selectedReglement.getValue()));
				}
				else{
					document =rechercheTaRReglement(((IHMReglement)selectedReglement.getValue()));
				}
				if(document==null)
					paVisuDocumentControlleur.actRefresh(null);
				else
					paVisuDocumentControlleur.actRefresh(document);
//					if(r!=null)
//					paVisuDocumentControlleur.actRefresh((IDocumentTiersComplet)r.getTaReglement());
//					else 
//					paVisuDocumentControlleur.actRefresh((IDocumentTiersComplet)rA.getTaAvoir());
			}
			
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	@Override
	public void initEtatComposant() {
//		vue.getPaAffectationReglementSurFacture().getTfCODE_DOCUMENT().setEnabled(false);
		vue.getPaAffectationReglementSurFacture().getTfCODE_REGLEMENT().setEnabled(false);
		if(selectedReglement!=null && selectedReglement.getValue()!=null)
			vue.getPaAffectationReglementSurFacture().getTfMONTANT_REGLEMENT().setEnabled(
							!((IHMReglement)selectedReglement.getValue()).getMulti()&& ((IHMReglement)selectedReglement.getValue()).getTypeDocument()!=TaAvoir.TYPE_DOC);			
		if(selectedReglement!=null && selectedReglement.getValue()!=null)
			vue.getPaAffectationReglementSurFacture().getTfCPT_COMPTABLE().setEnabled(
							!((IHMReglement)selectedReglement.getValue()).getMulti()&& ((IHMReglement)selectedReglement.getValue()).getTypeDocument()!=TaAvoir.TYPE_DOC);	
		if(selectedReglement!=null && selectedReglement.getValue()!=null)
			vue.getPaAffectationReglementSurFacture().getTfDATE_ENCAISSEMENT().setEnabled(
							!((IHMReglement)selectedReglement.getValue()).getMulti()&& ((IHMReglement)selectedReglement.getValue()).getTypeDocument()!=TaAvoir.TYPE_DOC);	
		if(selectedReglement!=null && selectedReglement.getValue()!=null)
			vue.getPaAffectationReglementSurFacture().getTfDATE_REGLEMENT().setEnabled(
							!((IHMReglement)selectedReglement.getValue()).getMulti()&& ((IHMReglement)selectedReglement.getValue()).getTypeDocument()!=TaAvoir.TYPE_DOC);	
		if(selectedReglement!=null && selectedReglement.getValue()!=null)
			vue.getPaAffectationReglementSurFacture().getTfTYPE_PAIEMENT().setEnabled(
							!((IHMReglement)selectedReglement.getValue()).getMulti()&& ((IHMReglement)selectedReglement.getValue()).getTypeDocument()!=TaAvoir.TYPE_DOC);	
		if(selectedReglement!=null && selectedReglement.getValue()!=null)
			vue.getPaAffectationReglementSurFacture().getTfLIBELLE_PAIEMENT().setEnabled(
							!((IHMReglement)selectedReglement.getValue()).getMulti()&& ((IHMReglement)selectedReglement.getValue()).getTypeDocument()!=TaAvoir.TYPE_DOC);		
//		if(selectedReglement!=null && selectedReglement.getValue()!=null)
//			vue.getPaAffectationReglementSurFacture().getTfMONTANT_AFFECTE().setEnabled(((IHMReglement)selectedReglement.getValue()).getTypeDocument()!=TaAvoir.TYPE_DOC);		
	}
	public void bindReglements() {
		try {
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());
			modelReglement = new ModelRReglement();

			vue.getPaAffectationReglementSurFacture().getLaTitreGrille().setText("Liste des règlements");
			vue.getPaAffectationReglementSurFacture().getLaTitreFormulaire().setText("Règlement");

			tableViewerReglement = new LgrTableViewer(vue.getPaAffectationReglementSurFacture().getGrille());

			tableViewerReglement.createTableCol(IHMReglement.class,
					 vue.getPaAffectationReglementSurFacture().getGrille(),
					"ReglementsDocument", Const.C_FICHIER_LISTE_CHAMP_GRILLE,
					0);
//			String[] listeChampAffectation = tableViewerReglement			
//					.setListeChampGrille("ReglementsDocument",
//							Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			String[] listeChampAffectation = setListeChampGrille("ReglementsDocument",
					Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			selectedReglement = ViewersObservables
					.observeSingleSelection(tableViewerReglement);
			if(masterEntity==null)
				LgrViewerSupport.bind(tableViewerReglement, new WritableList(
						modelReglement.remplirListeReglements(tiers,
								LibDateTime.getDate(vue.getTfDateDebutPeriode()),
								LibDateTime.getDate(vue.getTfDateFinPeriode()), getEm()),
						IHMReglement.class), org.eclipse.core.databinding.beans.BeanProperties
						.values(listeChampAffectation));
			else
				LgrViewerSupport.bind(tableViewerReglement, new WritableList(
					modelReglement.remplirListeReglementsFactureIntegres(masterEntity, getEm()),
					IHMReglement.class), org.eclipse.core.databinding.beans.BeanProperties
					.values(listeChampAffectation));
			
			tableViewerReglement.tri(IHMReglement.class, tableViewerReglement.getListeChamp(), tableViewerReglement.getTitresColonnes());
			selectedReglement.addChangeListener(new IChangeListener() {
				public void handleChange(ChangeEvent event) {
					changementDeSelection();
				}
			});
			dbcReglement = new DataBindingContext(realm);

			bindingFormMaitreDetail(mapComposantChamps,
					dbcReglement, realm, selectedReglement, IHMReglement.class);
			
//			((PaAffectationReglementSurFacture) vue.getExpandBar()
//					.getItem(2).getControl()).getTfCODE_DOCUMENT().setEnabled(false);
			vue.getPaAffectationReglementSurFacture().getTfDATE_REGLEMENT().setEnabled(false);
			//vue.getPaAffectationReglementSurFacture().getTfDATE_ENCAISSEMENT().setEnabled(false);
			vue.getPaAffectationReglementSurFacture().getTfCODE_REGLEMENT().setEnabled(false);
			
			vue.getPaAffectationReglementSurFacture().getGrille().addMouseListener(
					new MouseAdapter() {
						public void mouseDoubleClick(MouseEvent e) {
							if(((IHMReglement) selectedReglement.getValue()).getTypeDocument()==TaAvoir.TYPE_DOC){
							String idEditor =TypeDoc.getInstance().getEditorDoc()
							.get(((IHMReglement) selectedReglement.getValue()).getTypeDocument());
							String valeurIdentifiant = ((IHMReglement) selectedReglement
									.getValue()).getCodeDocument();
							ouvreDocument(valeurIdentifiant, idEditor);
							}
						}

					});
			
			vue.getPaAffectationReglementSurFacture().getGrille().addFocusListener(new FocusListener() {
						
						@Override
						public void focusLost(FocusEvent e) {
						}
						@Override
						public void focusGained(FocusEvent e) {
							changementDeSelection();	
						}
					});
				if(!afficheListeReglements){	
					getGrille().setEnabled(false);
					getGrille().setVisible(false);
				}
			
				initEtatBoutonCommand(true, modelReglement.getListeObjet());
//				setFocusCourantSWT(vue.getTfTiers());
		} catch (Exception e) {
			if (e.getMessage() != null)
				vue.getPaAffectationReglementSurFacture().getLaMessage().setText(e.getMessage());
			logger.error("", e);
		}
	}
	
	/**
	 * Initialisation des boutons suivant l'etat de l'objet "ibTaTable"
	 * @param initFocus - si vrai initialise le focus en fonction du mode
	 */
	protected void initEtatBoutonCommand(boolean initFocus,List model) {
		boolean trouve = contientDesEnregistrement(model);
		
		switch (daoStandard.getModeObjet().getMode()) {
		case C_MO_INSERTION:

			enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_FERMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID,false);
			if (vue.getPaAffectationReglementSurFacture().getGrille()!=null)
				vue.getPaAffectationReglementSurFacture().getGrille().setEnabled(false);
			break;
		case C_MO_EDITION:
			enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_FERMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID,true);
			if (vue.getPaAffectationReglementSurFacture().getGrille()!=null)
				vue.getPaAffectationReglementSurFacture().getGrille().setEnabled(false);
			break;
		case C_MO_CONSULTATION:
			enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID,trouve);
			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,trouve);
			enableActionAndHandler(C_COMMAND_GLOBAL_FERMER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID,trouve);
			enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID,true);
			if (vue.getPaAffectationReglementSurFacture().getGrille()!=null)
				vue.getPaAffectationReglementSurFacture().getGrille().setEnabled(true);
			break;
		default:
			break;
		}
		//	}
		initEtatComposant();
		if(initFocus)
			initFocusSWT(daoStandard, mapInitFocus);	
	}


	@Override
	protected void initMapComposantChamps() {
		if (mapComposantChamps == null)
			mapComposantChamps = new LinkedHashMap<Control, String>();

		if (listeComposantFocusable == null)
			listeComposantFocusable = new ArrayList<Control>();
		
//		mapComposantChamps.put(vue.getTfTiers(),Const.C_CODE_TIERS);
		vue.getTfTiers().setToolTipText(Const.C_CODE_TIERS);
		vue.getPaAffectationReglementSurFacture().getTfMONTANT_REGLEMENT().setToolTipText(Const.C_MONTANT_AFFECTE);
//		mapComposantChamps.put(vue.getPaAffectationReglementSurFacture().getTfCODE_DOCUMENT(),
//				Const.C_CODE_DOCUMENT);
		mapComposantChamps.put( vue.getPaAffectationReglementSurFacture().getTfCODE_REGLEMENT(),
				Const.C_CODE_DOCUMENT);		
		mapComposantChamps.put(vue.getPaAffectationReglementSurFacture().getTfDATE_REGLEMENT(),
				Const.C_DATE_DOCUMENT);
		mapComposantChamps.put(vue.getPaAffectationReglementSurFacture().getTfDATE_ENCAISSEMENT(),
				Const.C_DATE_LIV_DOCUMENT);
		mapComposantChamps.put(vue.getPaAffectationReglementSurFacture().getTfTYPE_PAIEMENT(),
				Const.C_CODE_T_PAIEMENT);
		mapComposantChamps.put(vue.getPaAffectationReglementSurFacture().getTfLIBELLE_PAIEMENT(),
				Const.C_LIBELLE_DOCUMENT);
		mapComposantChamps.put(vue.getPaAffectationReglementSurFacture().getTfMONTANT_REGLEMENT(),
				Const.C_NET_TTC_CALC);
		mapComposantChamps.put(vue.getPaAffectationReglementSurFacture().getTfMONTANT_AFFECTE(),
				Const.C_MONTANT_AFFECTE);		
		mapComposantChamps.put(vue.getPaAffectationReglementSurFacture().getTfCPT_COMPTABLE(),
				Const.C_COMPTE_BANQUE);
		
		
		listeComposantFocusable.add(vue.getTfDateDebutPeriode());
		listeComposantFocusable.add(vue.getTfDateFinPeriode());
		listeComposantFocusable.add(vue.getTfTiers());
		listeComposantFocusable.add(vue.getTfFacture());
		listeComposantFocusable.add(vue.getBtnValiderParam());

//		listeComposantFocusable.add(vue.getPaAffectationReglementSurFacture().getTfCODE_DOCUMENT());
		listeComposantFocusable.add(vue.getPaAffectationReglementSurFacture().getTfCODE_REGLEMENT());
		listeComposantFocusable.add(vue.getPaAffectationReglementSurFacture().getTfDATE_REGLEMENT());
		listeComposantFocusable.add(vue.getPaAffectationReglementSurFacture().getTfDATE_ENCAISSEMENT());
		listeComposantFocusable.add(vue.getPaAffectationReglementSurFacture().getTfTYPE_PAIEMENT());
		listeComposantFocusable.add(vue.getPaAffectationReglementSurFacture().getTfLIBELLE_PAIEMENT());
		listeComposantFocusable.add(vue.getPaAffectationReglementSurFacture().getTfMONTANT_REGLEMENT());		
		listeComposantFocusable.add(vue.getPaAffectationReglementSurFacture().getTfMONTANT_AFFECTE());
		listeComposantFocusable.add(vue.getPaAffectationReglementSurFacture().getTfCPT_COMPTABLE());
		
		listeComposantFocusable.add(vue.getPaAffectationReglementSurFacture().getBtnAnnuler());
		listeComposantFocusable.add(vue.getPaAffectationReglementSurFacture().getBtnEnregistrer());
		listeComposantFocusable.add(vue.getPaAffectationReglementSurFacture().getBtnInserer());
		listeComposantFocusable.add(vue.getPaAffectationReglementSurFacture().getBtnModifier());
		listeComposantFocusable.add(vue.getPaAffectationReglementSurFacture().getBtnSupprimer());

		if (mapInitFocus == null)
			mapInitFocus = new LinkedHashMap<ModeObjet.EnumModeObjet, Control>();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION,vue.getPaAffectationReglementSurFacture()
				.getTfMONTANT_AFFECTE());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue.getPaAffectationReglementSurFacture()
				.getTfMONTANT_AFFECTE());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION,  
				vue.getTfTiers());
		
		vue.getPaAffectationReglementSurFacture().getTfMONTANT_AFFECTE().addVerifyListener(queDesChiffresPositifs);		
		vue.getPaAffectationReglementSurFacture().getTfMONTANT_REGLEMENT().addVerifyListener(queDesChiffresPositifs);		
		
		vue.getTfTiers().addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				try {
					validateUIField(Const.C_CODE_TIERS,vue.getTfTiers().getText().toUpperCase());
					actRefresh();
				} catch (Exception e1) {
					logger.error("", e1);
				}
			}
		});
		vue.getTfFacture().addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				try {
					validateUIField(Const.C_CODE_DOCUMENT,vue.getTfFacture().getText().toUpperCase());
					actRefresh();
				} catch (Exception e1) {
					logger.error("", e1);
				}
			}
		});
		activeModifytListener();
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
		mapCommand.put(C_COMMAND_GLOBAL_SELECTION_ID, handlerSelection);
		
		initFocusCommand(String.valueOf(this.hashCode()),
				listeComposantFocusable, mapCommand);

		if (mapActions == null)
			mapActions = new LinkedHashMap<Button, Object>();

		mapActions.put(vue.getPaAffectationReglementSurFacture().getBtnSupprimer(),
				C_COMMAND_GLOBAL_SUPPRIMER_ID);		
		mapActions.put(vue.getPaAffectationReglementSurFacture().getBtnEnregistrer(),
				C_COMMAND_GLOBAL_ENREGISTRER_ID);
		mapActions.put(vue.getPaAffectationReglementSurFacture().getBtnModifier(),
				C_COMMAND_GLOBAL_MODIFIER_ID);
		mapActions.put(vue.getPaAffectationReglementSurFacture().getBtnAnnuler(),
				C_COMMAND_GLOBAL_ANNULER_ID);
		mapActions.put(vue.getPaAffectationReglementSurFacture().getBtnInserer(),
				C_COMMAND_GLOBAL_INSERER_ID);
		
		mapActions.put(vue.getBtnValiderParam(), C_COMMAND_GLOBAL_REFRESH_ID);

		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID,C_COMMAND_GLOBAL_SELECTION_ID,C_COMMAND_GLOBAL_REFRESH_ID };
		mapActions.put(null, tabActionsAutres);

		Menu popupMenuFormulaire = new Menu(vue.getShell(), SWT.POP_UP);
		Menu popupMenuGrille = new Menu(vue.getShell(), SWT.POP_UP);
		Menu[] tabPopups = new Menu[] { popupMenuFormulaire, popupMenuGrille };
		this.initPopupAndButtons(mapActions, tabPopups);
		vue.setMenu(popupMenuFormulaire);
		// }
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();
		
		mapComposantDecoratedField.put(vue.getPaAffectationReglementSurFacture().getTfCPT_COMPTABLE(),
				vue.getPaAffectationReglementSurFacture().getFieldCPT_COMPTABLE());
		mapComposantDecoratedField.put(vue.getPaAffectationReglementSurFacture().getTfLIBELLE_PAIEMENT(),
				vue.getPaAffectationReglementSurFacture().getFieldLIBELLE_PAIEMENT());
		mapComposantDecoratedField.put(vue.getPaAffectationReglementSurFacture().getTfMONTANT_AFFECTE(),
				vue.getPaAffectationReglementSurFacture().getFieldMONTANT_AFFECTE());
		mapComposantDecoratedField.put(vue.getPaAffectationReglementSurFacture().getTfTYPE_PAIEMENT(),
				vue.getPaAffectationReglementSurFacture().getFieldTYPE_PAIEMENT());
		
	}

	
	private void initController() {
		try {
			setDaoStandard(dao);
			setTableViewerStandard(tableViewerReglement);
			setDbcStandard(dbcReglement);

			initVue();
			vue.getPaAffectationReglementSurFacture().getCompositeForm().setWeights(new int[]{35,65});

			initMapComposantChamps();
			initMapComposantDecoratedField();
			initVerifySaisie();

			listeComponentFocusableSWT(listeComposantFocusable);
			initDeplacementSaisie(listeComposantFocusable);
			initFocusOrder();
			initActions();

			branchementBouton();
			initImageBouton(vue.getPaAffectationReglementSurFacture());
			bindReglements();
			vue.getPaEcran().layout();
			vue.getScrolledComposite().setMinSize(vue.getPaEcran().computeSize(SWT.DEFAULT, SWT.DEFAULT));
		} catch (Exception e) {
			if (e.getMessage() != null)
				vue.getPaAffectationReglementSurFacture().getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaArticlesController", e);
		}
	}
	
	protected void initImageBouton() {
		super.initImageBouton();
		String C_IMAGE_VALIDER = "/icons/accept.png";
		vue.getBtnValiderParam().setImage(Activator.getImageDescriptor(C_IMAGE_VALIDER).createImage());
		vue.layout(true);
	}
	
	protected void initVerifySaisie() {
		if (mapInfosVerifSaisie == null)
			mapInfosVerifSaisie = new HashMap<Control, InfosVerifSaisie>();
		
		mapInfosVerifSaisie.put(vue.getTfTiers(), new InfosVerifSaisie(new TaTiers(),Const.C_CODE_TIERS,null));
		initVerifyListener(mapInfosVerifSaisie, new TaFactureDAO(getEm()));
	}


	public TaRReglement getTaRReglement() {
		return taRReglement;
	}

	public void setTaRReglement(TaRReglement taRReglement) {
		this.taRReglement = taRReglement;
	}

	public boolean isIntegrer() {
		return integrer;
	}

	public void setIntegrer(boolean integrer) {
		this.integrer = integrer;
	}

	public boolean isAfficheListeReglements() {
		return afficheListeReglements;
	}

	public void setAfficheListeReglements(boolean afficheListeReglements) {
		this.afficheListeReglements = afficheListeReglements;
	}

	public boolean isEnregistrementDirect() {
		return enregistrementDirect;
	}

	public void setEnregistrementDirect(boolean enregistrementDirect) {
		this.enregistrementDirect = enregistrementDirect;
	}
	
	public PaGestionReglementController getThis(){
		return this;
	}
	
	@Override
	protected boolean aideDisponible() {
		boolean result = false;
		switch (dao.getModeObjet().getMode()) {
		case C_MO_CONSULTATION:
		case C_MO_EDITION:
		case C_MO_INSERTION:
			if (getFocusCourantSWT().equals(vue.getTfTiers())
					||getFocusCourantSWT().equals(vue.getTfFacture())
					||getFocusCourantSWT().equals(vue.getPaAffectationReglementSurFacture().getTfTYPE_PAIEMENT())||
					getFocusCourantSWT().equals(vue.getPaAffectationReglementSurFacture().getTfCPT_COMPTABLE()))
				result = true;		
			break;
		default:
			break;
		}
		return result;
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
	


	public void retourEcran(final RetourEcranEvent evt) {
		if (evt.getRetour() != null
				&& (evt.getSource() instanceof SWTPaAideControllerSWT)) {
			if (getFocusAvantAideSWT() instanceof Text) {
				try {
					((Text) getFocusAvantAideSWT()).setText(((ResultAffiche) evt
							.getRetour()).getResult());
					
					if(getFocusAvantAideSWT().equals(vue.getPaAffectationReglementSurFacture().getTfTYPE_PAIEMENT())){
						TaTPaiement entity = null;
						TaTPaiementDAO dao = new TaTPaiementDAO(getEm());
						entity = dao.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
						dao = null;
						validateUIField(Const.C_CODE_T_PAIEMENT,entity.getCodeTPaiement());
						vue.getPaAffectationReglementSurFacture().getTfTYPE_PAIEMENT().setText(entity.getCodeTPaiement());
//						actRefresh();
					}
					if(getFocusAvantAideSWT().equals(vue.getPaAffectationReglementSurFacture().getTfCPT_COMPTABLE())){
						TaCompteBanque entity = null;
						TaCompteBanqueDAO dao = new TaCompteBanqueDAO(getEm());
						entity = dao.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
						dao = null;
						validateUIField(Const.C_COMPTE_BANQUE,entity.getCompte());
						vue.getPaAffectationReglementSurFacture().getTfCPT_COMPTABLE().setText(entity.getCompte());
//						actRefresh();
					}
					if(getFocusAvantAideSWT().equals(vue.getTfTiers())){
						TaTiers entity = null;
						TaTiersDAO dao = new TaTiersDAO(getEm());
						entity = dao.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
						dao = null;
						validateUIField(Const.C_CODE_TIERS,entity.getCodeTiers());
						tiers=entity;
						vue.getTfTiers().setText(tiers.getCodeTiers());
//						actRefresh();
					}
					if(getFocusAvantAideSWT().equals(vue.getTfFacture())){
						TaFacture entity = null;
						TaFactureDAO dao = new TaFactureDAO(getEm());
						entity = dao.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
						dao = null;
						validateUIField(Const.C_CODE_DOCUMENT,entity.getCodeDocument());
						setMasterEntity(entity);
						vue.getTfFacture().setText(masterEntity.getCodeDocument());
//						actRefresh();
					}
					ctrlUnChampsSWT(getFocusAvantAideSWT());
				} catch (Exception e) {
					logger.error("",e);
					if(getFocusAvantAideSWT()!=null)setFocusCourantSWT(getFocusAvantAideSWT());
					vue.getPaAffectationReglementSurFacture().getLaMessage().setText(e.getMessage());
				}
			}			
			if (getFocusAvantAideSWT() instanceof Table) {
				if (getFocusAvantAideSWT() == vue.getPaAffectationReglementSurFacture().getGrille()) {
					if(((ResultAffiche) evt.getRetour()).getSelection()!=null)
						getTableViewerStandard().setSelection(((ResultAffiche) evt.getRetour()).getSelection(),true);
				}
			}
		} else if (evt.getRetour() != null){
			if (getFocusAvantAideSWT() instanceof Table) {
				if (getFocusAvantAideSWT() == vue.getPaAffectationReglementSurFacture().getGrille()) {
				}
			}
		}
		super.retourEcran(evt);
	}

	
	public void initVue(){
		DefaultFrameGrilleSansBouton visuDocument = new DefaultFrameGrilleSansBouton(vue.getExpandBar(), SWT.PUSH);
		paVisuDocumentControlleur = new PaVisuDocumentControlleur(visuDocument,getEm());
		
		addExpandBarItem(vue.getExpandBar(), visuDocument,
		"Documents liés au règlement en cours ", FacturePlugin.getImageDescriptor(
		"/icons/user.png").createImage(), SWT.DEFAULT, 200);
		vue.getExpandBar().getItem(0).setExpanded(true);
		
//		PaAffectationReglementSurFacture paAffectationReglement = new PaAffectationReglementSurFacture(
//				vue.getExpandBar(), SWT.PUSH);
//				setPaReglementController(new PaReglementController(paAffectationReglement, getEm())); 
//				getPaReglementController().setIntegrer(true);
//				getPaReglementController().setAfficheListeReglements(true);
//				getPaReglementController().setEnregistrementDirect(false);
//				
//				addExpandBarItem(vue.getExpandBar(), paAffectationReglement,
//						"Création des réglements", FacturePlugin.getImageDescriptor(
//						"/icons/user.png").createImage(), SWT.DEFAULT, 350);
//				vue.getExpandBar().getItem(2).setExpanded(true);
	}
	
}

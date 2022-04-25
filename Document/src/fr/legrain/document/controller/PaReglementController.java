package fr.legrain.document.controller;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.eclipse.core.databinding.beans.BeanProperties;
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
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
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

import fr.legrain.document.DocumentPlugin;
import fr.legrain.document.divers.ModelRReglement;
import fr.legrain.document.ecran.MessagesEcran;
import fr.legrain.document.ecran.PaAffectationReglementSurFacture;
import fr.legrain.documents.dao.IDocumentTiersDAO;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaRReglement;
import fr.legrain.documents.dao.TaRReglementDAO;
import fr.legrain.documents.dao.TaReglement;
import fr.legrain.documents.dao.TaReglementDAO;
import fr.legrain.documents.dao.TaTPaiement;
import fr.legrain.documents.dao.TaTPaiementDAO;
import fr.legrain.documents.events.SWTModificationDocumentEvent;
import fr.legrain.gestCom.Appli.AbstractApplicationDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Document.IHMEtat;
import fr.legrain.gestCom.Module_Document.IHMReglement;
import fr.legrain.gestCom.Module_Document.SWTTPaiement;
import fr.legrain.gestCom.Module_Tiers.SWTCompteBanque;
import fr.legrain.gestCom.gestComBd.gestComBdPlugin;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.DeclencheCommandeControllerEvent;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideRechercheSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideRechercheControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard.DateTraverse;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.ExceptLgr;
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
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.lib.gui.grille.LgrViewerSupport;
import fr.legrain.tiers.dao.TaCompteBanque;
import fr.legrain.tiers.dao.TaCompteBanqueDAO;
import fr.legrain.tiers.divers.ParamAfficheTPaiement;
import fr.legrain.tiers.ecran.PaBanqueSWT;
import fr.legrain.tiers.ecran.PaTypePaiementSWT;
import fr.legrain.tiers.ecran.ParamAfficheBanque;
import fr.legrain.tiers.ecran.SWTPaBanqueController;
import fr.legrain.tiers.ecran.SWTPaTypePaiementController;
import fr.legrain.tiers.editor.EditorBanque;
import fr.legrain.tiers.editor.EditorInputBanque;
import fr.legrain.tiers.editor.EditorInputTypePaiement;
import fr.legrain.tiers.editor.EditorTypePaiement;

public class PaReglementController extends JPABaseControllerSWTStandard implements 
RetourEcranListener {
	static Logger logger = Logger.getLogger(PaReglementController.class
			.getName());
	
	private TaFacture masterEntity = null;
	private TaFactureDAO masterDAO = null;
	private IHMReglement swtOldReglement;
	private IHMReglement ihmRReglement;
	private IObservableValue selectedReglement;
	private ModelRReglement modelReglement;
	private LgrTableViewer tableViewerReglement;
	private TaRReglementDAO dao = null;
	private TaReglementDAO daoReglement = null;
	private TaRReglement taRReglement = null;
	private DataBindingContext dbcReglement;
	
	private boolean integrer=false;
	private boolean afficheListeReglements=true;
	private boolean enregistrementDirect=false;
	private Realm realm;
	private String typePaiementDefaut=null;
	private MapChangeListener changeListener = new MapChangeListener();
	
	
	public Map<Control, String>  getMapComposantChamps(){
		return  mapComposantChamps;
	}
	
	public List<Control> getListeComposantFocusable() {
			return listeComposantFocusable;
	}
	
	
	private PaAffectationReglementSurFacture vue = null;
	
	
	public PaReglementController(PaAffectationReglementSurFacture ecran,EntityManager em) {
		if (em != null) {
			setEm(em);
		}
		try {
			setVue(ecran);
			dao = new TaRReglementDAO(getEm());
			daoReglement =new TaReglementDAO(getEm());
			
			this.vue.getShell().addShellListener(this);
			this.vue.getShell().addTraverseListener(new Traverse());
			
			vue.getTfDATE_REGLEMENT().addTraverseListener(new DateTraverse());
			vue.getTfDATE_REGLEMENT().addFocusListener(dateFocusListener);
			
			vue.getTfDATE_ENCAISSEMENT().addTraverseListener(new DateTraverse());
			vue.getTfDATE_ENCAISSEMENT().addFocusListener(dateFocusListener);

			initController();
		} catch (Exception e) {
			logger.debug("", e);
		}
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
					if (getFocusCourantSWT().equals(vue.getTfTYPE_PAIEMENT())) {
						PaTypePaiementSWT paTypePaiementSWT = new PaTypePaiementSWT(s2, SWT.NULL);
						SWTPaTypePaiementController swtPaTypePaiementController = new SWTPaTypePaiementController(
								paTypePaiementSWT);
						editorCreationId = EditorTypePaiement.ID;
						editorInputCreation = new EditorInputTypePaiement();
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
					if (getFocusCourantSWT().equals(vue.getTfCPT_COMPTABLE())) {
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
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCPT_COMPTABLE().getText());
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



	@Override
	public void actRefresh() throws Exception {
		if (tableViewerReglement==null)
			bindReglements();
		WritableList writableListReglement = new WritableList(modelReglement.
				razListeReglements(), IHMReglement.class);
		tableViewerReglement.setInput(writableListReglement);
		changementDeSelection();
		initEtatBouton();
}
	@Override
	protected void initComposantsVue() throws ExceptLgr {
		// TODO Auto-generated method stub

	}


	@Override
	public boolean onClose() throws ExceptLgr {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public ResultAffiche configPanel(ParamAffiche param) {
		// TODO Auto-generated method stub
		if (tableViewerReglement==null)
			bindReglements();
		else
			try {
				actRefresh(masterEntity, masterDAO, null);
			} catch (Exception e) {
				logger.error("",e);
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
	public IStatus validateUIField(String nomChamp, Object value) {
		String validationContext = "REGLEMENT";
		try {
			IStatus s = null;
			if(nomChamp.equals(Const.C_CODE_DOCUMENT)){
				s = new Status(Status.OK, gestComBdPlugin.PLUGIN_ID, "OK");
			}else 
				if (nomChamp.equals("imprimer")) {
				s = new Status(Status.OK, gestComBdPlugin.PLUGIN_ID, "OK");
			} else			
				if (nomChamp.equals(Const.C_CODE_T_PAIEMENT) ) {
					TaTPaiementDAO daoPaiement = new TaTPaiementDAO(dao.getEntityManager());
					daoPaiement.setModeObjet(masterDAO.getModeObjet());
					TaTPaiement f = new TaTPaiement();
					PropertyUtils.setSimpleProperty(f, nomChamp, value);
					s = daoPaiement.validate(f, nomChamp, validationContext);
					if (s.getSeverity() != IStatus.ERROR) {
						f = daoPaiement.findByCode((String) value);
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
							TaCompteBanqueDAO daoCompte=new TaCompteBanqueDAO(getEm());
							daoPaiement = new TaTPaiementDAO(dao.getEntityManager());
							TaCompteBanque compte=null;
							if(f.getCodeTPaiement()!=null){
								compte=daoCompte.findByTiersEntreprise(daoPaiement.findByCode(f.getCodeTPaiement()));
							}
							if(compte!=null)validateUIField(Const.C_COMPTE, compte.getCompte());
							taRReglement.getTaReglement().setTaCompteBanque(compte);
							ihmRReglement.setCompte(compte.getCompte());
						}
						masterEntity.modifieTypePaiementMultiple();
					}
					daoPaiement = null;
				}
				else  if (nomChamp.equals(Const.C_NET_TTC_CALC) ) {
					TaReglement u =new TaReglement();
					PropertyUtils.setSimpleProperty(u, nomChamp, value);
					s = new TaReglementDAO(dao.getEntityManager()).validate(u, nomChamp,validationContext);
					if (s.getSeverity() != IStatus.ERROR) {
						PropertyUtils.setSimpleProperty(taRReglement.getTaReglement(), nomChamp, value);
						if(taRReglement.getAffectation().compareTo(taRReglement.getTaReglement().getNetTtcCalc())>0)
						{
							taRReglement.setAffectation(taRReglement.getTaReglement().getNetTtcCalc());
							masterEntity.calculRegleDocument();
						}
					}	
				}
				else  if (nomChamp.equals(Const.C_MONTANT_AFFECTE) ) {
					TaRReglement u =new TaRReglement();
					PropertyUtils.setSimpleProperty(u, nomChamp, value);
					s = dao.validate(u, nomChamp, "R_REGLEMENT");
					if (s.getSeverity() != IStatus.ERROR) {
						PropertyUtils.setSimpleProperty(taRReglement, nomChamp, value);
						taRReglement.setTaReglement(taRReglement.getTaReglement());
						//taRReglement.setTaFacture(masterEntity);
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
						if(taRReglement.getTaReglement().getNetTtcCalc()==BigDecimal.valueOf(0))
							taRReglement.getTaReglement().setNetTtcCalc(taRReglement.getAffectation());
						if(taRReglement.getAffectation().compareTo(taRReglement.getTaReglement().getNetTtcCalc())>0)
							taRReglement.setAffectation(taRReglement.getTaReglement().getNetTtcCalc());
//						BigDecimal valeurTemp = masterEntity.getNetAPayer().subtract(masterEntity.getAcomptes()).subtract(masterEntity.getAvoirs());
//						if(valeurTemp.signum()<0 )
//							valeurTemp=valeurTemp.valueOf(0);
//						if(valeurTemp.compareTo(taRReglement.getAffectation())<0)
//							taRReglement.setAffectation(valeurTemp);
//						BigDecimal dif=masterEntity.getNetTtcCalc().
//						subtract(masterEntity.calculRegleDocumentComplet());
//						if(dif.signum()<0)
//							taRReglement.setAffectation(taRReglement.getAffectation().
//									subtract(dif.abs()));
						
						((IHMReglement)selectedReglement.getValue()).setAffectation(taRReglement.getAffectation());
						masterEntity.calculRegleDocumentComplet();
						
						masterEntity.calculRegleDocument();
						((IHMReglement)selectedReglement.getValue()).setAffectation(taRReglement.getAffectation());
					}	
				}else if (nomChamp.equals(Const.C_DATE_LIV_DOCUMENT) ) {
					TaReglement u =new TaReglement();
					PropertyUtils.setSimpleProperty(u, nomChamp, value);
					s = new TaReglementDAO(dao.getEntityManager()).validate(u, nomChamp, validationContext);
					if (s.getSeverity() != IStatus.ERROR) {
						//s'il y a eu changement
						if(taRReglement.getTaReglement().getDateLivDocument()==null)
							PropertyUtils.setSimpleProperty(taRReglement.getTaReglement(), nomChamp, u.getDateLivDocument());
						if( u.getDateLivDocument().compareTo(taRReglement.getTaReglement().getDateLivDocument())!=0){
							if(u.getDateLivDocument().before(taRReglement.getTaReglement().getDateDocument()))
								PropertyUtils.setSimpleProperty(taRReglement.getTaReglement(), nomChamp, taRReglement.getTaReglement().getDateDocument());
							else
								PropertyUtils.setSimpleProperty(taRReglement.getTaReglement(), nomChamp, value);
						}	
					}
				}else if (nomChamp.equals(Const.C_DATE_DOCUMENT)) {
					TaReglement u =new TaReglement();
					PropertyUtils.setSimpleProperty(u, nomChamp, value);
					s = new TaReglementDAO(dao.getEntityManager()).validate(u, nomChamp, validationContext);
					if (s.getSeverity() != IStatus.ERROR) {
						//s'il y a eu changement
						if(u.getDateDocument().compareTo(taRReglement.getTaReglement().getDateDocument())!=0){
							if(taRReglement.getTaFacture()!=null && u.getDateDocument().before(taRReglement.getTaFacture().getDateDocument()))
								PropertyUtils.setSimpleProperty(taRReglement.getTaReglement(),
										nomChamp, taRReglement.getTaFacture().getDateDocument());
							else
								PropertyUtils.setSimpleProperty(taRReglement.getTaReglement(), nomChamp, value);
						}
					}	
				}else if (nomChamp.equals(Const.C_LIBELLE_DOCUMENT) ) {
					TaReglement u =new TaReglement();
					PropertyUtils.setSimpleProperty(u, nomChamp, value);
					s = new TaReglementDAO(dao.getEntityManager()).validate(u, nomChamp, validationContext);
					if (s.getSeverity() != IStatus.ERROR) {		
						taRReglement.getTaReglement().setLibelleDocument(u.getLibelleDocument());
						masterEntity.modifieLibellePaiementMultiple();
					}	
				}else if (nomChamp.equals(Const.C_CODE_DOCUMENT)) {
					TaReglement u =new TaReglement();
					PropertyUtils.setSimpleProperty(u, nomChamp, value);
					s = new TaReglementDAO(getEm()).validate(u, nomChamp, validationContext);
					if (s.getSeverity() != IStatus.ERROR) {	
						PropertyUtils.setSimpleProperty(taRReglement.getTaReglement(), nomChamp, value);
					}	
				}else if (nomChamp.equals(Const.C_COMPTE)) {
					TaCompteBanque u =new TaCompteBanque();
					TaCompteBanqueDAO daoCompte = new TaCompteBanqueDAO(dao.getEntityManager());
					PropertyUtils.setSimpleProperty(u, nomChamp, value);
					s = daoCompte.validate(u, nomChamp, validationContext);
					if (s.getSeverity() != IStatus.ERROR) {
						if(u.getCompte()!=null && u.getCompte()!=""){
							u = daoCompte.findByTiersEntreprise(u.getCompte());
						taRReglement.getTaReglement().setTaCompteBanque(u);
						}
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


	//gestion de l'enregistrement dans l'expand d'affectation
	@Override
	public void actAnnuler() throws Exception {
		switch (dao.getModeObjet().getMode()) {
		case C_MO_INSERTION:
		supprimeTaRReglement(taRReglement);
		dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
		actRefresh(masterEntity,masterDAO, null);	
		break;
		case C_MO_EDITION:
//			int rang = modelReglement.getListeObjet().indexOf(selectedReglement.getValue());
			remetTousLesChampsApresAnnulationSWT(dbcReglement);
			if(swtOldReglement!=null)new LgrDozerMapper<IHMReglement, TaRReglement>().map(swtOldReglement, taRReglement);
			dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
			actRefresh(masterEntity,masterDAO,taRReglement);
			
			break;
		default:
			break;
		}

		if(enregistrementDirect){
			DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(
					this, C_COMMAND_GLOBAL_ANNULER_ID);
			fireDeclencheCommandeController(e);
			}
	}

	//gestion de l'enregistrement dans l'expand d'affectation
	@Override
	public void actEnregistrer() throws Exception {
		
			ctrlTousLesChampsAvantEnregistrementSWT(dbcReglement);
			for (TaRReglement reglement : masterEntity.getTaRReglements()) {
				reglement.setEtatDeSuppression(reglement.getEtatDeSuppression()|IHMEtat.enregistrer);
			}
			dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
			if(enregistrementDirect){
			DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(
					this, C_COMMAND_GLOBAL_ENREGISTRER_ID);
			fireDeclencheCommandeController(e);
			}			
			
			actRefresh(masterEntity,masterDAO,taRReglement);
			DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(
					this, C_COMMAND_GLOBAL_REFRESH_ID);
			fireDeclencheCommandeController(e);

			initEtatBouton();
	}


	//gestion de l'enregistrement dans l'expand d'affectation
	@Override
	public void actInserer() throws Exception {
		// passer le document de la grille sources vers la grille dest
		// puis passer le document en modification	
		if(!dao.dataSetEnModif()) {
			typePaiementDefaut = DocumentPlugin.getDefault().getPreferenceStore().getString(
					fr.legrain.document.preferences.PreferenceConstants.TYPE_PAIEMENT_DEFAUT);
			if (typePaiementDefaut == null || typePaiementDefaut=="")
				typePaiementDefaut="C";	
			DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(
					this, C_COMMAND_GLOBAL_MODIFIER_ID);
			fireDeclencheCommandeController(e);
			TaRReglement taRReglementTmp=null;
			if(masterDAO.dataSetEnModif()){
				taRReglement=masterEntity.creeReglement(masterDAO.getEntityManager(),taRReglementTmp,integrer,typePaiementDefaut);
				validateUIField(Const.C_CODE_DOCUMENT,taRReglement.getTaReglement().getCodeDocument());//permet de verrouiller le code genere			
				if(masterEntity.calculResteAReglerComplet().signum()>0){
					taRReglement.getTaReglement().setNetTtcCalc(masterEntity.calculResteAReglerComplet());
					taRReglement.setAffectation(masterEntity.calculResteAReglerComplet());
				}

				masterEntity.addRReglement(taRReglement);
				masterEntity.affecteReglementFacture(typePaiementDefaut);
				dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_INSERTION);
				masterEntity.calculRegleDocument();
				ihmRReglement=new IHMReglement(); 
				new LgrDozerMapper<TaRReglement, IHMReglement>().map(taRReglement, ihmRReglement);
				modelReglement.getListeObjet().add(ihmRReglement);
				actRefresh(masterEntity,masterDAO,taRReglement);
				initEtatBouton();
			}
		}
	}


	//gestion de l'enregistrement dans l'expand d'affectation
	@Override
	protected void actModifier() throws Exception {
		try {
			if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
				DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(
						this, C_COMMAND_GLOBAL_MODIFIER_ID);
				fireDeclencheCommandeController(e);			
				setSwtOldReglement();
				for (TaRReglement p : masterEntity.getTaRReglements()) {
					if (p.getTaReglement().getCodeDocument().equals(((IHMReglement) selectedReglement.getValue())
									.getCodeDocument()))
						taRReglement = p;
				}
				if(masterDAO.dataSetEnModif()){
					dao.modifier(taRReglement);
				}
				initEtatBouton();
			}
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
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
			}}
	}
	

	//gestion de l'enregistrement dans l'expand d'affectation
	@Override
	protected void actSupprimer() throws Exception {
			try {
				typePaiementDefaut = DocumentPlugin.getDefault().getPreferenceStore().getString(
						fr.legrain.document.preferences.PreferenceConstants.TYPE_PAIEMENT_DEFAUT);
		if (typePaiementDefaut == null || typePaiementDefaut=="")
			typePaiementDefaut="C";	
				VerrouInterface.setVerrouille(true);
					DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(
							this, C_COMMAND_GLOBAL_MODIFIER_ID);
					fireDeclencheCommandeController(e);
					if (MessageDialog.openConfirm(vue.getShell(), MessagesEcran
							.getString("Message.Attention"), fr.legrain.document.controller.MessagesEcran
							.getString("Document.Message.SupprimerAffectation"))) {
						for (TaRReglement p : masterEntity.getTaRReglements()) {
							if (p.getTaReglement().getCodeDocument().equals(((IHMReglement) selectedReglement.getValue())
											.getCodeDocument()))
								taRReglement = p;
						}
					if((taRReglement.getEtatDeSuppression()&IHMEtat.insertion)!=0)
						supprimeTaRReglement(taRReglement);
					else
					taRReglement.setEtatDeSuppression(taRReglement.getEtatDeSuppression()|IHMEtat.suppression);	
					tableViewerReglement.selectionGrille(0);
					changementDeSelection();
					actRefresh(masterEntity,masterDAO,null);
					masterEntity.affecteReglementFacture(typePaiementDefaut);
					initEtatBouton();			
					masterEntity.calculRegleDocument();
					masterEntity.modifieLibellePaiementMultiple();
					masterEntity.modifieTypePaiementMultiple();
					}
//				}
			} catch (ExceptLgr e1) {
				vue.getLaMessage().setText(e1.getMessage());
				logger.error("Erreur : actionPerformed", e1);
			} finally {
				VerrouInterface.setVerrouille(false);
			}
		}
	
	
	private class LgrModifyListenerReglement extends LgrModifyListener{

		public void modifyText(ModifyEvent e) {
			modifMode();
		}

		public void widgetDefaultSelected(SelectionEvent e) {
			modifMode();
		}

		public void widgetSelected(SelectionEvent e) {
			modifMode();
		}

	}
	
	public void actRefresh(TaFacture taDocument,TaFactureDAO taDocumentDao, TaRReglement taReglement) throws Exception {
		//		if(!daoReglement.dataSetEnModif()){
		// rafraichissement des valeurs dans la grille
		typePaiementDefaut = DocumentPlugin.getDefault().getPreferenceStore().getString(
				fr.legrain.document.preferences.PreferenceConstants.TYPE_PAIEMENT_DEFAUT);
if (typePaiementDefaut == null || typePaiementDefaut=="")
	typePaiementDefaut="C";		
		if (tableViewerReglement==null)
			bindReglements();
		setMasterEntity(taDocument);
		setMasterDAO(taDocumentDao);
		if(afficheListeReglements){
			WritableList writableListReglement = new WritableList(modelReglement.remplirListeReglementsFactureIntegres(masterEntity, taDocumentDao.getEntityManager()), IHMReglement.class);
			tableViewerReglement.setInput(writableListReglement);
		}else{
			if (dao.getModeObjet().getMode().compareTo(EnumModeObjet.C_MO_INSERTION)!=0)
				modelReglement.getListeObjet().clear();
			WritableList writableListReglement = new WritableList(modelReglement.getListeObjet(), IHMReglement.class);
			tableViewerReglement.setInput(writableListReglement);
		}
		if(taReglement==null || (taReglement.getEtatDeSuppression()&IHMEtat.multiple)!=0){
			tableViewerReglement.selectionGrille(0);
			if (selectedReglement.getValue() != null)
				for (TaRReglement p : masterEntity.getTaRReglements()) {
					if (p.getTaReglement().getCodeDocument().equals(((IHMReglement) selectedReglement.getValue()).getCodeDocument()))
						taReglement = p;
				}
		} else {
			tableViewerReglement.setSelection(new StructuredSelection(rechercheReglement(Const.C_CODE_DOCUMENT, taReglement.getTaReglement().getCodeDocument())));
		}
		taRReglement=taReglement;
		if(afficheListeReglements){
			masterEntity.calculRegleDocument();
			masterEntity.modifieLibellePaiementMultiple();
			masterEntity.modifieTypePaiementMultiple();
			masterEntity.affecteReglementFacture(typePaiementDefaut);	

		}
		masterEntity.declencheModificationDocument(new SWTModificationDocumentEvent(masterEntity));
		changementDeSelection();
		initEtatBouton();
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
		this.masterDAO = masterDAO;
	}

	public void setVue(PaAffectationReglementSurFacture vue) {
		super.setVue(vue);
		this.vue = vue;
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

	
//	protected void initEtatBoutonReglement() {
//		boolean trouve = modelReglement.getListeObjet().size()>0;
//		switch (dao.getModeObjet().getMode()) {
//		case C_MO_INSERTION:
//			enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,false);
//			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,true);
//			enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID,false);
//			enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,true);
//			enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID,false);
//			enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,true);
//			enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID,false);
//			if (vue.getGrille()!=null)vue.getGrille().setEnabled(false);
//			break;
//		case C_MO_EDITION:
//			enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,false);
//			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,true);
//			enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID,false);
//			enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,true);
//			enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID,false);			
//			enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,true);
//			enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID,true);
//			if (vue.getGrille()!=null)vue.getGrille().setEnabled(false);
//			break;
//		case C_MO_CONSULTATION:
//			enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,true);
//			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,false);
//			enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID,trouve);
//			enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,false);
//			enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID,trouve);			
//			enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,true);
//			enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID,true);
//			if (vue.getGrille()!=null)vue.getGrille().setEnabled(true);
//			break;
//		default:
//			break;
//		}
//	}

	public void supprimeTaRReglement(TaRReglement taRReglement) throws Exception{		
		supprimeTaRReglement(taRReglement,true);
	}
	public void supprimeTaRReglement(TaRReglement taRReglement,Boolean commit) throws Exception{		
		EntityTransaction transaction = dao.getEntityManager().getTransaction();
		if(commit)dao.begin(transaction);
		masterEntity.removeReglement(taRReglement);
		dao.supprimer(taRReglement);
		taRReglement.setAffectation(BigDecimal.valueOf(0));
		taRReglement.getTaReglement().removeReglement(taRReglement);
		taRReglement.setTaFacture(null);
		taRReglement.setTaReglement(null);
		taRReglement=null;
		if(commit)dao.commit(transaction);
		transaction=null;
	}
	
	public TaRReglement enregistreTaReglement(AbstractApplicationDAO dao, TaRReglement taRReglement,Boolean commit) throws Exception{		
		EntityTransaction transaction = dao.getEntityManager().getTransaction();
		TaCompteBanqueDAO daoCompte = new TaCompteBanqueDAO(dao.getEntityManager());
		TaReglement reglement =taRReglement.getTaReglement();
		TaCompteBanque compte = daoCompte.findById(reglement.getTaCompteBanque().getIdCompteBanque());
		taRReglement.getTaReglement().setTaCompteBanque(compte);
		//taRReglement.setTaFacture(null);
		taRReglement.getTaReglement().removeReglement(taRReglement);
		if(commit)dao.begin(transaction);
		daoReglement.setEm(dao.getEntityManager());
		taRReglement.setTaReglement(daoReglement.enregistrerMerge(taRReglement.getTaReglement()));
		if(commit)
			dao.commit(transaction);
		reglement.addRReglement(taRReglement);
		taRReglement.setTaFacture(masterEntity);
		transaction=null;
		return taRReglement;
	}
	
	@Override
	public void modifMode(){
		if (!VerrouInterface.isVerrouille() ){
			try {
				if(!dao.dataSetEnModif()) {
					if(!(modelReglement.getListeObjet().size()==0)) {
						actModifier();
					} else {
						actInserer();								
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
		if(taRReglement==null || (taRReglement.getEtatDeSuppression()&IHMEtat.multiple)!=0){
			tableViewerReglement.selectionGrille(0);
		} 
		if (selectedReglement.getValue() != null)
			for (TaRReglement p : masterEntity.getTaRReglements()) {
				if (p.getTaReglement().getCodeDocument().equals(((IHMReglement) selectedReglement.getValue()).getCodeDocument()))
					taRReglement = p;
			}
		initEtatComposant();
	}
	
	@Override
	public void initEtatComposant() {
		if(selectedReglement!=null && selectedReglement.getValue()!=null){		
			vue.getTfMONTANT_REGLEMENT().setEnabled(
					!((IHMReglement)selectedReglement.getValue()).getMulti());
			vue.getTfDATE_REGLEMENT().setEnabled(enregistrementDirect &&
					!((IHMReglement)selectedReglement.getValue()).getMulti());		
			vue.getTfDATE_ENCAISSEMENT().setEnabled(true );		
		}
	}
	public void bindReglements() {
		try {
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());
			modelReglement = new ModelRReglement();

			vue.getLaTitreGrille().setText(
					"Liste des règlements de la facture");

			tableViewerReglement = new LgrTableViewer(vue.getGrille());

			tableViewerReglement.createTableCol(IHMReglement.class,
					 vue.getGrille(),
					"ReglementsDocument", Const.C_FICHIER_LISTE_CHAMP_GRILLE,
					0);
			String[] listeChampAffectation = tableViewerReglement			
					.setListeChampGrille("ReglementsDocument",
							Const.C_FICHIER_LISTE_CHAMP_GRILLE);

			selectedReglement = ViewersObservables
					.observeSingleSelection(tableViewerReglement);
			if(masterEntity==null)
				LgrViewerSupport.bind(tableViewerReglement, new WritableList(
						modelReglement.razListeReglements(),
						IHMReglement.class), BeanProperties
						.values(listeChampAffectation));
			else
				LgrViewerSupport.bind(tableViewerReglement, new WritableList(
					modelReglement.remplirListeReglementsFactureIntegres(masterEntity, getEm()),
					IHMReglement.class), BeanProperties
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
			
			//vue.getTfCODE_DOCUMENT().setEnabled(false);
			vue.getTfDATE_REGLEMENT().setEnabled( enregistrementDirect);
			vue.getTfDATE_ENCAISSEMENT().setEnabled(enregistrementDirect);
			vue.getTfCODE_REGLEMENT().setEnabled(false);

			vue.getGrille().addFocusListener(new FocusListener() {
						
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
			
				initEtatBouton();
		} catch (Exception e) {
			if (e.getMessage() != null)
				vue.getLaMessage().setText(e.getMessage());
			logger.error("", e);
		}
	}

	@Override
	protected void initMapComposantChamps() {
		if (mapComposantChamps == null)
			mapComposantChamps = new LinkedHashMap<Control, String>();

		if (listeComposantFocusable == null)
			listeComposantFocusable = new LinkedList<Control>();
		
		
//		mapComposantChamps.put(vue.getTfCODE_DOCUMENT(),
//				Const.C_CODE_DOCUMENT);
		mapComposantChamps.put( vue.getTfCODE_REGLEMENT(),
				Const.C_CODE_DOCUMENT);		
		mapComposantChamps.put(vue.getTfDATE_REGLEMENT(),
				Const.C_DATE_DOCUMENT);
		mapComposantChamps.put(vue.getTfDATE_ENCAISSEMENT(),
				Const.C_DATE_LIV_DOCUMENT);
		mapComposantChamps.put(vue.getTfTYPE_PAIEMENT(),
				Const.C_CODE_T_PAIEMENT);
		mapComposantChamps.put(vue.getTfLIBELLE_PAIEMENT(),
				Const.C_LIBELLE_DOCUMENT);
		mapComposantChamps.put(vue.getTfMONTANT_REGLEMENT(),
				Const.C_NET_TTC_CALC);
		mapComposantChamps.put(vue.getTfMONTANT_AFFECTE(),
				Const.C_MONTANT_AFFECTE);		
		mapComposantChamps.put(vue.getTfCPT_COMPTABLE(),
				Const.C_COMPTE_BANQUE);
		
//		listeComposantFocusable.add(vue.getTfCODE_DOCUMENT());
		listeComposantFocusable.add(vue.getTfCODE_REGLEMENT());
		listeComposantFocusable.add(vue.getTfCPT_COMPTABLE());
		listeComposantFocusable.add(vue.getTfDATE_REGLEMENT());
		listeComposantFocusable.add(vue.getTfDATE_ENCAISSEMENT());
		listeComposantFocusable.add(vue.getTfMONTANT_REGLEMENT());		
		listeComposantFocusable.add(vue.getTfMONTANT_AFFECTE());
		listeComposantFocusable.add(vue.getTfTYPE_PAIEMENT());
		listeComposantFocusable.add(vue.getTfLIBELLE_PAIEMENT());
		
		
		listeComposantFocusable.add(vue.getBtnEnregistrer());
		listeComposantFocusable.add(vue.getBtnAnnuler());
		listeComposantFocusable.add(vue.getBtnInserer());
		listeComposantFocusable.add(vue.getBtnModifier());
		listeComposantFocusable.add(vue.getBtnSupprimer());

		if (mapInitFocus == null)
			mapInitFocus = new LinkedHashMap<ModeObjet.EnumModeObjet, Control>();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION, vue
				.getTfMONTANT_AFFECTE());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue
				.getTfMONTANT_AFFECTE());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION, vue
				.getTfMONTANT_AFFECTE());

		
		vue.getTfMONTANT_AFFECTE().addVerifyListener(queDesChiffresPositifs);		
		
		
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


		initFocusCommand(String.valueOf(this.hashCode()),
				listeComposantFocusable, mapCommand);

		if (mapActions == null)
			mapActions = new LinkedHashMap<Button, Object>();
		
		mapActions.put(vue.getBtnInserer(),
				C_COMMAND_GLOBAL_INSERER_ID);
		mapActions.put(vue.getBtnSupprimer(),
				C_COMMAND_GLOBAL_SUPPRIMER_ID);		
		mapActions.put(vue.getBtnEnregistrer(),
				C_COMMAND_GLOBAL_ENREGISTRER_ID);
		mapActions.put(vue.getBtnModifier(),
				C_COMMAND_GLOBAL_MODIFIER_ID);
		mapActions.put(vue.getBtnAnnuler(),
				C_COMMAND_GLOBAL_ANNULER_ID);


		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID };
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
		
		mapComposantDecoratedField.put(vue.getTfCPT_COMPTABLE(),
				vue.getFieldCPT_COMPTABLE());
		mapComposantDecoratedField.put(vue.getTfLIBELLE_PAIEMENT(),
				vue.getFieldLIBELLE_PAIEMENT());
		mapComposantDecoratedField.put(vue.getTfMONTANT_AFFECTE(),
				vue.getFieldMONTANT_AFFECTE());
		mapComposantDecoratedField.put(vue.getTfTYPE_PAIEMENT(),
				vue.getFieldTYPE_PAIEMENT());
		
	}

	
	private void initController() {
		try {
			setDaoStandard(dao);
			setTableViewerStandard(tableViewerReglement);
			setDbcStandard(dbcReglement);

//			initVue();

			initMapComposantChamps();
			initMapComposantDecoratedField();

			listeComponentFocusableSWT(listeComposantFocusable);
			initDeplacementSaisie(listeComposantFocusable);
			initFocusOrder();
			initActions();

			branchementBouton();

			bindReglements();

		} catch (Exception e) {
			if (e.getMessage() != null)
				vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaArticlesController", e);
		}
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
	
	public PaReglementController getThis(){
		return this;
	}
	
	@Override
	protected boolean aideDisponible() {
		boolean result = false;
		switch (dao.getModeObjet().getMode()) {
		case C_MO_CONSULTATION:
		case C_MO_EDITION:
		case C_MO_INSERTION:
			if (getFocusCourantSWT().equals(vue.getTfTYPE_PAIEMENT())||
					getFocusCourantSWT().equals(vue.getTfCPT_COMPTABLE()))
				result = true;		
			break;
		default:
			break;
		}
		return result;
	}
	
	public void retourEcran(final RetourEcranEvent evt) {
		try {
			if (evt.getRetour() != null
					&& (evt.getSource() instanceof SWTPaAideControllerSWT && !evt
							.getRetour().getResult().equals(""))) {
				if (getFocusAvantAideSWT() instanceof Text) {
					try {
						((Text) getFocusAvantAideSWT())
								.setText(((ResultAffiche) evt.getRetour())
										.getResult());
						if (getFocusAvantAideSWT().equals(vue.getTfTYPE_PAIEMENT())) {
							TaTPaiement f = null;
							TaTPaiementDAO t = new TaTPaiementDAO(getEm());
							f = t.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
							t = null;
							validateUIField(Const.C_CODE_T_PAIEMENT, f.getCodeTPaiement());
							taRReglement.getTaReglement().setTaTPaiement(f);
							
						}
						if (getFocusAvantAideSWT().equals(vue.getTfCPT_COMPTABLE())) {
							TaCompteBanque f = null;
							TaCompteBanqueDAO t = new TaCompteBanqueDAO(getEm());
							f = t.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
							t = null;
							taRReglement.getTaReglement().setTaCompteBanque(f);
						}					
						ctrlUnChampsSWT(getFocusAvantAideSWT());
					} catch (Exception e) {
						logger.error("", e);
						vue.getLaMessage().setText(e.getMessage());
					}
				}
			}
			super.retourEcran(evt);
		} finally {

		}
	}

}

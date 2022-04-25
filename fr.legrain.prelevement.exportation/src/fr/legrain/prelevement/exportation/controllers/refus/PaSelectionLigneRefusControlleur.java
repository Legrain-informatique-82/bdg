package fr.legrain.prelevement.exportation.controllers.refus;

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
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
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
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
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

import fr.legrain.document.divers.TaCreationDoc;
import fr.legrain.document.divers.TypeDoc;
import fr.legrain.documents.dao.TaAcompte;
import fr.legrain.documents.dao.TaAcompteDAO;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaPrelevement;
import fr.legrain.documents.dao.TaPrelevementDAO;
import fr.legrain.documents.dao.TaRDocument;
import fr.legrain.documents.dao.TaRDocumentDAO;
import fr.legrain.documents.dao.TaReglement;
import fr.legrain.documents.dao.TaReglementDAO;
import fr.legrain.documents.dao.TaTDoc;
import fr.legrain.documents.dao.TaTDocDAO;
import fr.legrain.documents.dao.TaTPaiementDAO;
import fr.legrain.edition.actions.BaseImpressionEdition;
import fr.legrain.generationdocument.controllers.CreationDocumentMultiple;
import fr.legrain.generationdocument.controllers.PaParamGenerationDocController;
import fr.legrain.generationdocument.divers.ParamAfficheChoixGenerationDocument;
import fr.legrain.generationdocument.ecran.PaChoixGenerationDocument;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Document.IHMEntetePrelevement;
import fr.legrain.gestCom.gestComBd.gestComBdPlugin;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideRechercheSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideRechercheControllerSWT;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.InfosVerifSaisie;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.data.VerrouInterface;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ParamAfficheSWT;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.lib.gui.aide.PaAideRechercheSWT;
import fr.legrain.lib.gui.aide.PaAideSWT;
import fr.legrain.lib.gui.aide.ParamAfficheAide;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.libMessageLGR.LgrMess;
import fr.legrain.prelevement.exportation.divers.ParamAffichePresentation;
import fr.legrain.prelevement.exportation.divers.ViewerSupportLocal;
import fr.legrain.prelevement.exportation.ecrans.refus.PaSelectionLigneRefus;
import fr.legrain.publipostage.divers.TypeVersionPublipostage;
import fr.legrain.tiers.dao.TaCompteBanque;
import fr.legrain.tiers.dao.TaCompteBanqueDAO;
import fr.legrain.tiers.ecran.ParamAfficheTiers;

//imports de Dima

public class PaSelectionLigneRefusControlleur extends
		JPABaseControllerSWTStandard implements RetourEcranListener{
	static Logger logger = Logger.getLogger(PaSelectionLigneRefusControlleur.class.getName());	

	PaSelectionLigneRefus  vue = null;
	private TaPrelevementDAO dao = null;
	private TaPrelevement TaPrelevement=null;
	private IObservableValue selectedLignePrelevement;
	
	private TaReglementDAO daoReglement = null;
	private TaAcompteDAO daoAcompte = null;

	private List<IHMEntetePrelevement> modelLRemise = null;
	private Object ecranAppelant = null;
	private Realm realm;
	private DataBindingContext dbc;
	private MapChangeListener changeListener = new MapChangeListener();
	private Class classModel = IHMEntetePrelevement.class;
	private String nomClass = this.getClass().getSimpleName();
	private String finDeLigne = "\r\n";
	private String separateur = ";";
	protected Boolean ImpressionUniquement=false;
	protected TaCompteBanque compteEntrepriseCourant;
	private String typePaiementCourant=null;
	private TaTPaiementDAO daoPaiement = null;
	private TaCompteBanqueDAO daoCompte = null;
	
	
	private TypeVersionPublipostage typeVersion;
	
	private List<TaPrelevement> masterListeEntity = null; //une Liste
	private List<IHMEntetePrelevement> listeIhm = null; //une Liste
	private TaPrelevementDAO masterDAO = null;
	BaseImpressionEdition baseImpressionEdition = null;
	
	public static final String C_COMMAND_DOCUMENT_REINITIALISER_ID = "fr.legrain.Document.reinitialiser";
	protected HandlerInverser handlerInverser = new HandlerInverser();

	public static final String C_COMMAND_DOCUMENT_TOUT_COCHER_ID = "fr.legrain.Publipostage.toutCocher";
	protected HandlerToutCocher handlerToutCocher = new HandlerToutCocher();
	
	public static final String C_COMMAND_DOCUMENT_TOUT_DECOCHER_ID = "fr.legrain.Publipostage.toutDeCocher";
	protected HandlerToutDeCocher handlerToutDeCocher = new HandlerToutDeCocher();
	
	
	private class HandlerInverser extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				for (TaPrelevement objet : masterListeEntity) {
					objet.setAccepte(!objet.getAccepte());							
				}
				actRefresh();
				} catch (Exception e) {
					logger.error("", e);
				}
				return event;
			}
		}
	
	private class HandlerToutCocher extends LgrAbstractHandler {
	
		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				for (TaPrelevement objet : masterListeEntity) {
					objet.setAccepte(true);													
				}
				actRefresh();
			} catch (Exception e) {
				logger.error("", e);
			}
			return event;
		}
	}

	public void actToutDeCocher() throws ExecutionException{
		handlerToutDeCocher.execute(new ExecutionEvent());
	}
	private class HandlerToutDeCocher extends LgrAbstractHandler {
	
		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				for (TaPrelevement objet : masterListeEntity) {
					objet.setAccepte(false);
				}
				actRefresh();
			} catch (Exception e) {
				logger.error("", e);
			}
			return event;
		}
	}
	public PaSelectionLigneRefusControlleur(PaSelectionLigneRefus vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao =new TaPrelevementDAO(getEm());
//		modelFacture = new ModelGeneralObjet<IHMLRelance,TaLRelanceDAO,TaLRelance>
//		(dao,IHMLRelance.class);
		setVue(vue);

		vue.getShell().addShellListener(this);

		//Branchement action annuler : empeche la fermeture automatique de la fenetre sur ESC
		vue.getShell().addTraverseListener(new Traverse());

		initController();
		initSashFormWeight();
		baseImpressionEdition = new BaseImpressionEdition(vue.getShell(),getEm());
	}

	public PaSelectionLigneRefusControlleur(PaSelectionLigneRefus vue) {
		this(vue,null);
	}

	
	private void initController()	{
		try {	
			
			setDaoStandard(dao);
			daoReglement = new TaReglementDAO(getEm());
			daoAcompte = new TaAcompteDAO(getEm());
			
			typeVersion=TypeVersionPublipostage.getInstance();
			initVue();			
			initMapComposantChamps();
			initVerifySaisie();
			initMapComposantDecoratedField();
			listeComponentFocusableSWT(listeComposantFocusable);
			initFocusOrder();
			initActions();
			initDeplacementSaisie(listeComposantFocusable);

			vue.getBtnImprimer().setVisible(false);
			
			
			branchementBouton();

			Menu popupMenuFormulaire = new Menu(vue.getShell(), SWT.POP_UP);
			Menu popupMenuGrille = new Menu(vue.getShell(), SWT.POP_UP);
			Menu[] tabPopups = new Menu[] {
					popupMenuFormulaire, popupMenuGrille };
			this.initPopupAndButtons(mapActions, tabPopups);
			vue.getParent().setMenu(popupMenuFormulaire);
			initEtatBouton(true);
			
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
	}
	@Override
	protected void actAide() throws Exception {
		// TODO Auto-generated method stub
		actAide(null);
	}

	@Override
	protected boolean aideDisponible() {
		return false;
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

	}
	public PaSelectionLigneRefusControlleur getThis() {
		return this;
	}
	
	
	public void generationPrel(List<TaPrelevement> listePrel)throws Exception {
		int compteur=0;
		int compteurFacture=1;
		TaCreationDoc c=null;
		LinkedList<IDocumentTiers> list =new LinkedList<IDocumentTiers>();
		LinkedList<IDocumentTiers> listTemp =new LinkedList<IDocumentTiers>();
		LinkedList<TaCreationDoc> listCreation =new LinkedList<TaCreationDoc>();
		String codeTiersEnCours=null;

		for (TaPrelevement taPrelevement : listePrel) {		
			c=new TaCreationDoc();
			c.setCodeDocument(TypeDoc.TYPE_PRELEVEMENT+" :"+compteurFacture);
			c.setLibelleDocument(taPrelevement.getLibelleDocument());
			c.setCodeTiers(taPrelevement.getTaTiers().getCodeTiers());
			c.setAccepte(true);
			listCreation.add(c);
			codeTiersEnCours=taPrelevement.getTaTiers().getCodeTiers();
			compteurFacture++;
			compteur=0;

			c.getListeDoc().add(taPrelevement);
			compteur++;
		}

		//créer le document à partir des documents selectionné dans le modelDocument
		final  ParamAfficheChoixGenerationDocument paramTmp = new ParamAfficheChoixGenerationDocument();
		ParamAfficheChoixGenerationDocument param = new ParamAfficheChoixGenerationDocument();
		String finDeLigne = "\r\n";
		paramTmp.setTypeSrc(TypeDoc.TYPE_PRELEVEMENT);
		paramTmp.setTypeDest(TypeDoc.TYPE_PRELEVEMENT);
		String libelle="Reprise de ";
		paramTmp.setDateDocument(new Date());
		paramTmp.setDateLivraison(new Date());


		//		paramTmp.setLibelle();

		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {               	
				final Shell s = new Shell(vue.getShell(),LgrShellUtil.styleLgr);		
				final PaChoixGenerationDocument paChoixGenerationDocument = new PaChoixGenerationDocument(s,SWT.NULL);
				final PaParamGenerationDocController paChoixGenerationDocumentController = new PaParamGenerationDocController(paChoixGenerationDocument);
				paChoixGenerationDocumentController.addRetourEcranListener(getThis());
				paramTmp.setTiersModifiable(false);
				final ParamAfficheSWT paramAfficheSWT = new ParamAfficheSWT();
				paramAfficheSWT.setHauteur(PaParamGenerationDocController.HAUTEUR);
				paramAfficheSWT.setLargeur(PaParamGenerationDocController.LARGEUR);
				paramAfficheSWT.setTitre(PaParamGenerationDocController.TITRE);
				LgrShellUtil.afficheSWT(paramTmp, paramAfficheSWT, paChoixGenerationDocument, paChoixGenerationDocumentController, s,true);
				if(paChoixGenerationDocumentController.getResult()!=null){
					paramTmp.setCodeDocument(paChoixGenerationDocumentController.getResult().getCodeDocument());
					paramTmp.setDateDocument(paChoixGenerationDocumentController.getResult().getDateDocument());
					paramTmp.setDateLivraison(paChoixGenerationDocumentController.getResult().getDateLivraison());
					paramTmp.setLibelle(paChoixGenerationDocumentController.getResult().getLibelle());
					paramTmp.setRepriseLibelleSrc(paChoixGenerationDocumentController.getResult().getRepriseLibelleSrc());
					paramTmp.setRepriseReferenceSrc(paChoixGenerationDocumentController.getResult().getRepriseReferenceSrc());
					paramTmp.setRepriseAucun(paChoixGenerationDocumentController.getResult().getRepriseAucun());
					paramTmp.setMultiple(true);
					//    			paramTmp=paChoixGenerationDocumentController.getResult();
				}
			}});
		param=paramTmp;
		if(param.getRetour()){
			//		List<IHMEnteteDocument> listeTriee=new LinkedList<IHMEnteteDocument>();
			List<IDocumentTiers> listeDocCrees=new LinkedList<IDocumentTiers>();
			TaPrelevement docSrc =null;
			TaPrelevement docGenere =null;
			param.getListeDocumentSrc().clear();
			param.setDateDocument(paramTmp.getDateDocument());
			param.setDateLivraison(paramTmp.getDateDocument());
			for (TaCreationDoc elem : listCreation) {
				if(elem.getAccepte()){
					for (IDocumentTiers doc : elem.getListeDoc()) {
//						if(doc.getAccepte()){
							docSrc=((TaPrelevement)doc);
							param.getListeDocumentSrc().add(doc);
							param.setDateDocument(paramTmp.getDateDocument());
							param.setDateLivraison(paramTmp.getDateDocument());
							//libelle+=" - "+doc.getCodeDocument();
//						}
					}
					//creation document
					if(param.getListeDocumentSrc().size()!=0){
						IDocumentTiers doc=null; 
						//param.setLibelle(libelle);
						CreationDocumentMultiple creation = new CreationDocumentMultiple(param,getEm());
						doc=creation.creationDocument(true);
						//enregistrer relation rDocument
						TaTDocDAO daoTDoc = new TaTDocDAO();
						TaTDoc tDoc=daoTDoc.findByCode(TaPrelevement.TYPE_DOC);
						TaRDocumentDAO daoRDao = new TaRDocumentDAO(dao.getEntityManager());
						EntityTransaction transaction = daoRDao.getEntityManager().getTransaction();
						try {
							daoRDao.begin(transaction);
							docGenere=dao.findByCode(doc.getCodeDocument());
							docSrc=dao.findByCode(docSrc.getCodeDocument());
							//génération de la relation du document généré
							TaRDocument taRDocumentDest = new TaRDocument();
							taRDocumentDest.setTaPrelevement(docGenere);					
							taRDocumentDest=daoRDao.enregistrerMerge(taRDocumentDest);	

							
							//génération de la relation du document source
							TaRDocument taRDocumentGenere = new TaRDocument();
							taRDocumentGenere.setTaPrelevement(docSrc);
							taRDocumentGenere.setTaDocumentGenere(taRDocumentDest);
							taRDocumentGenere=daoRDao.enregistrerMerge(taRDocumentGenere);


							daoRDao.commit(transaction);
						}finally {
							if(transaction!=null && transaction.isActive()) {
								transaction.rollback();
							}
						}					
						// 
						if(doc!=null){
							listeDocCrees.add(doc);
						}
					}
				}
				param.getListeDocumentSrc().clear();

				//libelle="Reprise des documents : ";
			}

			param.setDateDocument(new Date());
			param.setDateLivraison(new Date());

			String message="";
			for (IDocumentTiers iDocumentTiers : listeDocCrees) {
				message+=iDocumentTiers.getCodeDocument()+finDeLigne;
			}
			if (!message.equals(""))
				MessageDialog.openInformation(vue.getShell(), "Liste des documents créés", message);

		}


	}

	protected void enregistrer(){ //<--
		try {
			boolean trouveDocExporte=false;
			List<TaPrelevement>list = new LinkedList<TaPrelevement>();
			for (Object prelIHM : getTableViewerStandard().getCheckedElements()) {
					for (TaPrelevement prel : masterListeEntity) {
						if(prel.getCodeDocument().equals(((IHMEntetePrelevement)prelIHM).getCodeDocument())) {
							list.add(prel);
							break;
						}						
					}					
			}
			//regénérer tous les prélèvement sélectionnés
			generationPrel(list);
			reset();

		
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	private void reset(){
		masterListeEntity.clear();
		dao.getEntityManager().clear();
		try {
			actRefresh();
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	protected void supprimer(){
		
	}

	@Override
	protected void actEnregistrer() throws Exception {
		enregistrer();
	}

	@Override
	protected void actFermer() throws Exception {
		//(controles de sortie et fermeture de la fenetre) => onClose()

	}

	@Override
	protected void actImprimer() throws Exception {
		try {
//			if(ImpressionUniquement==false){
//			boolean trouveDocExporte=false;
//			List<TaLPrelevement>list = new LinkedList<TaLPrelevement>();
//			//enlever les lignes de remise non accept�es
//			for (TaLPrelevement lRemise : masterListeEntity.getLignes()) {
//				if(!lRemise.getAccepte())list.add(lRemise);
//			}
//			for (TaLPrelevement TaLPrelevement : list) {
//				masterListeEntity.getLignes().remove(TaLPrelevement);
//			}
//			
//			for (TaLPrelevement lRemise : masterListeEntity.getLignes()) {
//				IDocumentTiers doc =null;
//				if(lRemise.getTypeDocument().equals(TaReglement.TYPE_DOC))doc=lRemise.getTaReglement();
//				if(lRemise.getTypeDocument().equals(TaAcompte.TYPE_DOC))doc=lRemise.getTaAcompte();
//				if(verifDocDejaExporte(doc)){
//					MessageDialog.openError(vue.getShell(), "Document déjà exporté", "Le document "+doc.getCodeDocument()+" " +
//					"a déjà été exporté. Vous ne pouvez pas l'intégrer dans cette remise.");
//					checkAccept(lRemise.getCodeDocument(),false);
//					trouveDocExporte=true;
//				}
//			}
//			
//			if(trouveDocExporte)throw new Exception();
////
//			}
//
//			
//			String codeRemiseAImprimer =masterListeEntity.getCodeDocument();
//			TaPrelevement remiseAImprimer=masterListeEntity;
//
//			if(ImpressionUniquement==false){
////			//enregistrement de la remise
//			DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(
//					this, C_COMMAND_GLOBAL_ENREGISTRER_ID);
//			fireDeclencheCommandeController(e);
//			}
//
////			remiseAImprimer=getMasterDAO().findByCode(codeRemiseAImprimer);
//			remiseAImprimer=new TaPrelevementDAO().findByCode(codeRemiseAImprimer);
//			//impression de la remise
//			String simpleNameClass = TaPrelevement.class.getSimpleName(); 
//			Collection<TaPrelevement> collectionTaPrelevement = new LinkedList<TaPrelevement>();
//			collectionTaPrelevement.add(masterListeEntity);
//			ConstEdition constEdition = new ConstEdition(getEm()); 
//			constEdition.setFlagEditionMultiple(true);
//
//
//			Bundle bundleCourant = pluginPrelevement.getDefault().getBundle();
//			String namePlugin = bundleCourant.getSymbolicName();
//
//			/** 01/03/2010 modifier les editions (zhaolin) **/
//			baseImpressionEdition.setObject(remiseAImprimer);
//			baseImpressionEdition.setConstEdition(constEdition);
//			baseImpressionEdition.setCollection(collectionTaPrelevement);
//			baseImpressionEdition.setIdEntity(remiseAImprimer.getIdDocument());
//
//			IPreferenceStore preferenceStore = pluginPrelevement.getDefault().getPreferenceStore();
//			baseImpressionEdition.impressionEdition(preferenceStore,simpleNameClass,/*true,*/null,
//					namePlugin,ConstEdition.FICHE_FILE_REPORT_REMISE,
//					true,false,true,false,false,false,"");			
			///

		} catch (Exception e) {
			logger.error("", e);
		}
	}

	

	
	@Override
	protected void actInserer() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void actModifier() throws Exception {

	}

	/**
	 * Creation des objet pour l'interface, a partir de l'entite principale.<br>
	 * Ici : creation d'une liste de ligne pour l'IHM, a partir de la liste des lignes contenue dans l'entite facture.
	 * @return
	 */
	public List<IHMEntetePrelevement> IHMmodel() {
		LinkedList<TaPrelevement> ldao = new LinkedList<TaPrelevement>();
		LinkedList<IHMEntetePrelevement> lswt = new LinkedList<IHMEntetePrelevement>();
		
		if(masterListeEntity!=null && !masterListeEntity.isEmpty()) {
			ldao.addAll(masterListeEntity);
			
			for (TaPrelevement o : ldao) {
				IHMEntetePrelevement t = new IHMEntetePrelevement();
				t.setCodeDocument(o.getCodeDocument());
				t.setAccepte(o.getAccepte());
				t.setDateDocument(o.getDateDocument());
				t.setCodeTiers(o.getTaTiers().getCodeTiers());
				t.setNomTiers(o.getTaTiers().getNomTiers());
				t.setNetTtcCalc(o.getNetTtcCalc());
				t.setExportBanque(LibConversion.intToBoolean(o.getExportBanque()));
				if(o.getTaEtat()!=null)t.setCodeEtat(o.getTaEtat().getCodeEtat());
				lswt.add(t);
			}

		}
		return lswt;
	}
	
	@Override
	protected void actRefresh() throws Exception {
		vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
		try{	
//			String codeActuel="";
			dao.setEm(masterDAO.getEntityManager());	
			WritableList writableListFacture = new WritableList(IHMmodel(), IHMEntetePrelevement.class);
			getTableViewerStandard().setInput(writableListFacture);			
//			if(codeActuel!="" && writableListFacture.size()>0) {
//				getTableViewerStandard().setSelection(new StructuredSelection(recherche(Const.C_CODE_REGLEMENT
//						, codeActuel)),true);
//			}
			
			initTotaux();
			changementDeSelection();
			initEtatBouton(true);
			vue.getGrille().forceFocus();
		}catch (Exception e) {
			logger.error("",e);
		}
		finally{
			vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
		}
	}

	protected void initTotaux() {
		Integer nbLigne=0;
		BigDecimal totalTTC=BigDecimal.valueOf(0);
		if(masterListeEntity!=null){
			for (TaPrelevement obj : masterListeEntity) {
				if (obj.getAccepte()){
					totalTTC=totalTTC.add(obj.getNetTtcCalc());		
					nbLigne++;
				}
			}
		}
		vue.getTfMT_HT_CALC().setText(LibConversion.bigDecimalToString(totalTTC));
		vue.getTfMT_TTC_CALC().setText(LibConversion.integerToString(nbLigne));
	}
	@Override
	protected void actSupprimer() throws Exception {
		// TODO Auto-generated method stub

	}

	public void bind(PaSelectionLigneRefus paSelectionLigneRelance){ // <--
		try {
			vue.getPaFomulaire().setVisible(false);
			
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());
			setTableViewerStandard( new LgrTableViewer(vue.getGrille()));
			String[] titreColonnes =new String[] {"Code document","Date","tiers","Nom","Net TTC","Réponse"};
			//, "banque","compte","type", "Export�",
			getTableViewerStandard().createTableCol(vue.getGrille(),titreColonnes,
					new String[] {"120","80","60","100","70", "100"},0); // ,"200","150","80","30"
			String[] listeChamp = new String[] {"codeDocument","dateDocument","codeTiers","nomTiers","netTtcCalc","codeEtat"};
			//,"export","nomBanque","compte","codeTPaiement"
			getTableViewerStandard().setListeChamp(listeChamp);
			getTableViewerStandard().tri(classModel, listeChamp, titreColonnes);
			modelLRemise=IHMmodel();

			ViewerSupportLocal.bind(getTableViewerStandard(), 
					new WritableList(IHMmodel(), classModel),
					BeanProperties.values(listeChamp)
			);
			
			getTableViewerStandard().addCheckStateListener(new ICheckStateListener() {
				@Override
				public void checkStateChanged(CheckStateChangedEvent event) {
					checkAccept(((IHMEntetePrelevement)event.getElement()).getCodeDocument(),event.getChecked());	
				}
			});	
		
		getTableViewerStandard().setCheckStateProvider(new ICheckStateProvider() {
			
			@Override
			public boolean isGrayed(Object element) {
				// TODO Auto-generated method stub
				if(!((IHMEntetePrelevement)element).getAccepte())
					return true;
				return false;
			}
			
			@Override
			public boolean isChecked(Object element) {
				// TODO Auto-generated method stub
				if(((IHMEntetePrelevement)element).getAccepte())
					return true;
				return false;
			}
		});

//		affectationReglementControllerMini.bind();
		vue.getGrille().addMouseListener(
				new MouseAdapter() {

					public void mouseDoubleClick(MouseEvent e) {
						String idEditor = TypeDoc.getInstance().getEditorDoc()
								.get(TaPrelevement.TYPE_DOC);
						String valeurIdentifiant = ((IHMEntetePrelevement) selectedLignePrelevement
								.getValue()).getCodeDocument();
						ouvreDocument(valeurIdentifiant, idEditor);
					}

				});
		dbc = new DataBindingContext(realm);

		dbc.getValidationStatusMap().addMapChangeListener(changeListener);
		setDbcStandard(dbc);
		selectedLignePrelevement = ViewersObservables.observeSingleSelection(getTableViewerStandard());
		bindingFormMaitreDetail(dbc, realm, selectedLignePrelevement,classModel);
		selectedLignePrelevement.addChangeListener(new IChangeListener() {

			public void handleChange(ChangeEvent event) {
				changementDeSelection();
			}

		});


		changementDeSelection();
		initEtatBouton(true);
		initTotaux();
	} catch(Exception e) {
		logger.error("",e);
		vue.getLaMessage().setText(e.getMessage());
	}
}

private void changementDeSelection() {
	try {
		if(selectedLignePrelevement==null ||selectedLignePrelevement.getValue()==null)
			getTableViewerStandard().selectionGrille(0);
		if(selectedLignePrelevement!=null && selectedLignePrelevement.getValue()!=null){
			if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
				for (TaPrelevement ligne : masterListeEntity) {
					if(ligne.getCodeDocument().equals(((IHMEntetePrelevement) selectedLignePrelevement.getValue()).getCodeDocument()))
						TaPrelevement =ligne;	
				}
			}
		}
		initTotaux();
		initEtatBouton(true);
	} catch (Exception e) {
		logger.error("",e );
	}
}

	@Override
	protected void initActions() {
		mapCommand = new HashMap<String, IHandler>();

	
		mapCommand.put(C_COMMAND_GLOBAL_ANNULER_ID, handlerAnnuler);
		mapCommand.put(C_COMMAND_GLOBAL_MODIFIER_ID, handlerToutCocher);//tout cocher
		mapCommand.put(C_COMMAND_GLOBAL_SUPPRIMER_ID, handlerToutDeCocher);//tout décocher
		mapCommand.put(C_COMMAND_GLOBAL_INSERER_ID, handlerInverser);// Inverser
		mapCommand.put(C_COMMAND_GLOBAL_ENREGISTRER_ID, handlerEnregistrer);
		mapCommand.put(C_COMMAND_GLOBAL_REFRESH_ID, handlerRefresh);
		mapCommand.put(C_COMMAND_GLOBAL_AIDE_ID, handlerAide);
		mapCommand.put(C_COMMAND_GLOBAL_FERMER_ID, handlerFermer);
		mapCommand.put(C_COMMAND_GLOBAL_IMPRIMER_ID, handlerImprimer);

		mapCommand.put(C_COMMAND_GLOBAL_PRECEDENT_ID, handlerPrecedent);
		mapCommand.put(C_COMMAND_GLOBAL_SUIVANT_ID, handlerSuivant);


		initFocusCommand(String.valueOf(this.hashCode()),listeComposantFocusable,mapCommand);

		if (mapActions == null)
			mapActions = new LinkedHashMap<Button, Object>();


		mapActions.put(vue.getBtnAnnuler(), C_COMMAND_GLOBAL_ANNULER_ID);
		mapActions.put(vue.getBtnModifier(), C_COMMAND_GLOBAL_MODIFIER_ID);
		mapActions.put(vue.getBtnSupprimer(), C_COMMAND_GLOBAL_SUPPRIMER_ID);
		mapActions.put(vue.getBtnInserer(), C_COMMAND_GLOBAL_INSERER_ID);
		mapActions.put(vue.getBtnEnregister(), C_COMMAND_GLOBAL_ENREGISTRER_ID);
		mapActions.put(vue.getBtnImprimer(), C_COMMAND_GLOBAL_IMPRIMER_ID);
		mapActions.put(vue.getBtnFermer(), C_COMMAND_GLOBAL_FERMER_ID);
		
		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID, C_COMMAND_GLOBAL_REFRESH_ID };
		mapActions.put(null, tabActionsAutres);
		initEtatBouton(true);
	}

	@Override
	protected void initComposantsVue() throws ExceptLgr {
		// TODO Auto-generated method stub

	}

	@Override
	public void initEtatComposant() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initMapComposantChamps() {
		if (mapComposantChamps == null) 
			mapComposantChamps = new LinkedHashMap<Control,String>();

		if (listeComposantFocusable == null) 
			listeComposantFocusable = new ArrayList<Control>();

		listeComposantFocusable.add(vue.getBtnAnnuler());
		listeComposantFocusable.add(vue.getBtnInserer());//Inverser		
		listeComposantFocusable.add(vue.getBtnModifier());//tout cocher
		listeComposantFocusable.add(vue.getBtnEnregister());//Envoyer dans l'autre grille
		listeComposantFocusable.add(vue.getBtnSupprimer());//tout Décocher
		listeComposantFocusable.add(vue.getBtnImprimer());
		listeComposantFocusable.add(vue.getBtnFermer());
		
		vue.getBtnModifier().setText("tout cocher");
		vue.getBtnSupprimer().setText("tout Décocher");
		vue.getBtnInserer().setText("Inverser les cochés");		
		

		
		if(mapInitFocus == null) 
			mapInitFocus = new LinkedHashMap<ModeObjet.EnumModeObjet,Control>();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION,vue.getGrille());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION,vue.getGrille());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION,vue.getGrille());

		activeModifytListener();

	}

	@Override
	protected void initMapComposantDecoratedField() {
		// TODO Auto-generated method stub

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
			else param.setFocusDefautSWT(vue.getGrille());


			if(param.getTitreGrille()!=null){
				vue.getLaTitreGrille().setText(param.getTitreGrille());
			} else {
				vue.getLaTitreGrille().setText(ParamAfficheTiers.C_TITRE_GRILLE);
			}


			if(param.getEcranAppelant()!=null) {
				ecranAppelant = param.getEcranAppelant();
			}
			if(param instanceof ParamAffichePresentation){
				setImpressionUniquement(((ParamAffichePresentation)param).getEnregistreDirect());
			}
		}	

		bind(vue);
		initSashFormWeight();
			getTableViewerStandard().selectionGrille(0);
//			getTableViewerStandard().tri(classModel,nomClass,Const.C_FICHIER_LISTE_CHAMP_GRILLE);
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

	public void setVue(PaSelectionLigneRefus vue) {
		super.setVue(vue);
		this.vue = vue;
	}



	public TaPrelevementDAO getMasterDAO() {
		return masterDAO;
	}

	public void setMasterDAO(TaPrelevementDAO masterDAO) {
		this.masterDAO = masterDAO;
	}

	@Override
	protected void initEtatBouton() {
		initEtatBouton(false);
	}
	
	protected void initImageBouton() {
		super.initImageBouton();
		vue.getBtnAnnuler().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ANNULER));
			vue.getBtnEnregister().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ENREGISTRER));
			vue.getBtnEnregister().setText("Regénérer[F3]");

		vue.getBtnFermer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_FERMER));
		vue.getBtnImprimer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_IMPRIMER));
		vue.getBtnInserer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_REINITIALISER));
		vue.getBtnModifier().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_MODIFIER));
		vue.getBtnSupprimer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_SUPPRIMER));
		
//		vue.getBtnImprimer().setText("Enregistrer/Imprimer");
		vue.layout(true);
	}
	
	@Override
	protected void initEtatBouton(boolean initFocus) {
		boolean trouve = masterListeEntity!=null && masterListeEntity.size()>0;
		switch (daoStandard.getModeObjet().getMode()) {
		case C_MO_CONSULTATION:
			enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID, trouve);//!ImpressionUniquement &&
			enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID,trouve);//!ImpressionUniquement && 
			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,trouve);//!ImpressionUniquement&&
			enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,trouve);
			enableActionAndHandler(C_COMMAND_GLOBAL_FERMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID, trouve);//!ImpressionUniquement &&
			enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID,true);
			if (vue.getGrille()!=null)vue.getGrille().setEnabled(true);
			break;
		default:
			break;
		}
		//	}
		initEtatComposant();
		if (initFocus)
			initFocusSWT(daoStandard, mapInitFocus);	
		
	}

//	public ModelGeneralObjet<IHMLRelance, TaLRelanceDAO, TaLRelance> getModelFacture() {
//		return modelFacture;
//	}
//
//	public void setModelFacture(
//			ModelGeneralObjet<IHMLRelance, TaLRelanceDAO, TaLRelance> modelFacture) {
//		this.modelFacture = modelFacture;
//	}

//	public class TaLPrelevementComparator implements Comparator<TaLPrelevement> {
//		 
//	    public int compare(TaLPrelevement TaLPrelevement1, TaLPrelevement TaLPrelevement2) {
//	        IDocumentTiers document1=null;
//	        IDocumentTiers document2=null;
//	        if(TaLPrelevement1.getTaAcompte()!=null)document1=TaLPrelevement1.getTaAcompte();
//	        if(TaLPrelevement1.getTaReglement()!=null)document1=TaLPrelevement1.getTaReglement();
//	        
//	        if(TaLPrelevement2.getTaAcompte()!=null)document2=TaLPrelevement2.getTaAcompte();
//	        if(TaLPrelevement2.getTaReglement()!=null)document2=TaLPrelevement2.getTaReglement();
//	     	        
//	        int premier = document1.getCodeDocument().compareTo(document2.getCodeDocument());
//	        
//	        int deuxieme = document1.getDateDocument().compareTo(document2.getDateDocument());
//
//	        int compared = premier;
//	        if (compared == 0) {
//	            compared = deuxieme;
//	        }
//	 
//	        return compared;
//	    }
//	}

	
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
			if (nomChamp.equals(Const.C_CODE_DOCUMENT) ) {
				s=new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
			}
			if (nomChamp.equals(Const.C_CODE_TIERS) ) {
				s=new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
			}

				if (nomChamp.equals(Const.C_ACCEPTE) ) {
					TaPrelevement f = new TaPrelevement();
					PropertyUtils.setSimpleProperty(f, nomChamp, value);
					s = dao.validate(f, nomChamp, "PRELEVEMENT");
					if (s.getSeverity() != IStatus.ERROR) {
						TaPrelevement.setAccepte(f.getAccepte());
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
	
	public Object recherche(String propertyName, Object value) {
		boolean trouve = false;
		int i = 0;
		List<IHMEntetePrelevement> model=IHMmodel();
		while(!trouve && i<model.size()){
			try {
				if(PropertyUtils.getProperty(model.get(i), propertyName).equals(value)) {
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
			return model.get(i);
		else 
			return null;

	}

	protected void initSashFormWeight() {
		int premierComposite = 30;
		int secondComposite = 70;
//		if(!getEnregistreDirect()){
//			premierComposite = 0;
//			secondComposite = 100;
//		}
		vue.getCompositeForm().setWeights(new int[]{premierComposite,secondComposite});
	}
	
	public Boolean getImpressionUniquement() {
		return ImpressionUniquement;
	}

	public void setImpressionUniquement(Boolean enregistreDirect) {
		this.ImpressionUniquement = enregistreDirect;
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

public boolean verifDocDejaExporte(IDocumentTiers document){
	daoAcompte=new TaAcompteDAO();
	daoReglement=new TaReglementDAO();
	if(document!=null){
		if(((IDocumentTiers)document).getTypeDocument().equals(TaAcompte.TYPE_DOC))
			document=daoAcompte.findByCode(((TaAcompte)document).getCodeDocument());
		if(((IDocumentTiers)document).getTypeDocument().equals(TaReglement.TYPE_DOC))
			document=daoReglement.findByCode(((TaReglement)document).getCodeDocument());
	}
	if(document!=null)return document.getExport()==1;
	return false;
}

public void checkAccept(String codeDocument,boolean check){
	try {
		//Object objet=recherche(Const.C_CODE_REGLEMENT, codeDocument);
		Object objet=recherche(Const.C_CODE_DOCUMENT, codeDocument);
		StructuredSelection selection =new StructuredSelection(objet);
		getTableViewerStandard().setSelection(selection,true);
		if(selectedLignePrelevement.getValue()!=null){
			actModifier();
			((IHMEntetePrelevement)selectedLignePrelevement.getValue()).setAccepte(check);
			validateUIField(Const.C_ACCEPTE,check);
			//actEnregistrer();
			initTotaux();
		}
	} catch (Exception e) {
		logger.error("", e);
	}		
}

public List<TaPrelevement> getMasterListeEntity() {
	return masterListeEntity;
}

public void setMasterListeEntity(List<TaPrelevement> masterListeEntity) {
	this.masterListeEntity = masterListeEntity;
}

public TaCompteBanque getCompteEntrepriseCourant() {
	return compteEntrepriseCourant;
}

public void setCompteEntrepriseCourant(TaCompteBanque compteEntrepriseCourant) {
	this.compteEntrepriseCourant = compteEntrepriseCourant;
}


}

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
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ExpandAdapter;
import org.eclipse.swt.events.ExpandEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import fr.legrain.document.DocumentPlugin;
import fr.legrain.document.controller.MessagesEcran;
import fr.legrain.document.controller.PaReglementController;
import fr.legrain.document.controller.PaVisuReglementControlleur;
import fr.legrain.document.divers.TypeDoc;
import fr.legrain.document.ecran.PaAffectationReglementSurFacture;
import fr.legrain.documents.dao.TaAcompte;
import fr.legrain.documents.dao.TaAcompteDAO;
import fr.legrain.documents.dao.TaAvoir;
import fr.legrain.documents.dao.TaAvoirDAO;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaRReglement;
import fr.legrain.documents.dao.TaReglement;
import fr.legrain.documents.dao.TaReglementDAO;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.facture.FacturePlugin;
import fr.legrain.facture.editor.EditorFacture;
import fr.legrain.facture.editor.EditorInputFacture;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Document.IHMAideFacture;
import fr.legrain.gestCom.Module_Document.IHMEnteteDocument;
import fr.legrain.gestCom.Module_Document.IHMEnteteFacture;
import fr.legrain.gestCom.Module_Tiers.SWTTiers;
import fr.legrain.gestCom.gestComBd.gestComBdPlugin;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.swt.LgrDatabindingUtil;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.gestCom.librairiesEcran.swt.LibDateTime;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideRechercheSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheReglementMultiple;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheVisualisation;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideRechercheControllerSWT;
import fr.legrain.gestCom.librairiesEcran.workbench.AnnuleToutEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDePageEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDeSelectionEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementMasterEntityEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.IChangementMasterEntityListener;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.InfosVerifSaisie;
import fr.legrain.lib.data.JPABdLgr;
import fr.legrain.lib.data.LibCalcul;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.LibDate;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
import fr.legrain.lib.data.VerrouInterface;
import fr.legrain.lib.gui.DefaultFrameGrilleSansBouton_grilleReduite;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.lib.gui.UpdateMasterEntityEvent;
import fr.legrain.lib.gui.UpdateMasterEntityListener;
import fr.legrain.lib.gui.aide.PaAideRechercheSWT;
import fr.legrain.lib.gui.aide.PaAideSWT;
import fr.legrain.lib.gui.aide.ParamAfficheAide;
import fr.legrain.lib.gui.grille.EditionGrille;
import fr.legrain.libMessageLGR.LgrMess;
import fr.legrain.reglement.Activator;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.ecran.PaTiersSWT;
import fr.legrain.tiers.ecran.ParamAfficheTiers;
import fr.legrain.tiers.ecran.SWTPaTiersController;

public class SWTPaReglementMultipleController extends JPABaseControllerSWTStandard implements RetourEcranListener, UpdateMasterEntityListener{

	static Logger logger = Logger.getLogger(SWTPaReglementMultipleController.class.getName());	

	public static final String C_COMMAND_DOCUMENT_TOUT_REGLER_ID = "fr.legrain.Reglement.toutDeRegle";
	protected HandlerToutRegle handlerToutRegler = new HandlerToutRegle();
	
	private PaReglementMultiple vue = null;
	private PaAffectationReglementSurFacture paFormulaireReglement = null;
	private PaReglementController paReglementController=null;
	private AffectationAvoirController affectationAvoirController=null;
	private AffectationAcompteController affectationAcompteController=null;
	private AffectationReglementController affectationReglementController=null;
	private PaAffectationReglement paAffectationReglement = null;
	private PaAffectationReglement paAffectationAvoir = null;
	private PaAffectationReglement paAffectationAcompte = null;
	private String typePaiementDefaut=null;
	
	PaVisuReglementControlleur paVisuReglementControlleur;
	private String nomClassController = this.getClass().getSimpleName();
	private TaFactureDAO dao = null;
	private TaFacture taDocument=null;
	private String idCommentaire = null;
//	private String idTypeTiers = null;
//	private String idTiers = null;
	private TaTiers tiers = null;
	private Date dateDeb=null;
	private Date dateFin=null;
	private TaFacture document = null;
	
	private Object ecranAppelant = null;
	private Realm realm;
	private DataBindingContext dbc;


	private Class classModel = IHMEnteteFacture.class;
	private ModelGeneralObjet<IHMEnteteFacture,TaFactureDAO,TaFacture> modelFacture = null;
//	private LgrTableViewer tableViewer;
//	private WritableList writableList;
	private IHMEnteteDocument selectedCritere;
	private IObservableValue selectedFacture;
	private String[] listeChamp;
	private String nomClass = this.getClass().getSimpleName();
	private MapChangeListener changeListener = new MapChangeListener();

//	private LgrDozerMapper<IHMReglement,TaRReglement> mapperUIToModel  = new LgrDozerMapper<IHMReglement,TaRReglement>();
//	private LgrDozerMapper<TaRReglement,IHMReglement> mapperModelToUI  = new LgrDozerMapper<TaRReglement,IHMReglement>();
//	private TaRReglement taRReglement = null;
	
	private LgrDatabindingUtil lgrDatabindingUtil = new LgrDatabindingUtil();
//	private AffectationReglementControllerMini affectationReglementControllerMini = null;

	public SWTPaReglementMultipleController(PaReglementMultiple vue) {
		this(vue,null);
	}

	public SWTPaReglementMultipleController(PaReglementMultiple vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao =new TaFactureDAO(getEm());
		modelFacture = new ModelGeneralObjet<IHMEnteteFacture,TaFactureDAO,TaFacture>(dao,classModel);
		setVue(vue);
		//vue.getCompositeForm().setOrientation(SWT.VERTICAL);
		//vue.getCompositeForm().setWeights(new int[]{20,80});

		//a faire avant l'initialisation du Binding
		vue.getTableFacture().addSelectionListener( new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
//				setSwtOldTiers();
				//				ibTaTable.setTiersCourant((IHMReglement)selectedReglement.getValue());
			}			
		});
		vue.getShell().addShellListener(this);

		//Branchement action annuler : empeche la fermeture automatique de la fenetre sur ESC
		vue.getShell().addTraverseListener(new Traverse());

		initController();
		initSashFormWeight();
	}

	private class HandlerToutRegle extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			if (MessageDialog.openConfirm(vue.getShell(),"Attention","Vous êtes sur le point de régler tous les documents de la liste." +
					" Cette opération est irréversible. Etes-vous sûr de vouloir tout régler ?")) {
				EntityTransaction transaction = dao.getEntityManager().getTransaction();
				try {
					typePaiementDefaut = DocumentPlugin.getDefault().getPreferenceStore().getString(
							fr.legrain.document.preferences.PreferenceConstants.TYPE_PAIEMENT_DEFAUT);
			if (typePaiementDefaut == null || typePaiementDefaut=="")
				typePaiementDefaut="C";
					TaRReglement taRReglement=null;
					TaRReglement taRReglementTmp=null;

					for (TaFacture facture : modelFacture.getListeEntity()) {
						taDocument=facture;
						taRReglement=taDocument.creeReglement(dao.getEntityManager(),taRReglementTmp,false,typePaiementDefaut);
						//validateUIField(Const.C_CODE_DOCUMENT,taRReglement.getTaReglement().getCodeDocument());//permet de verrouiller le code genere			
						if(taDocument.calculResteAReglerComplet().signum()>0){
							taRReglement.getTaReglement().setNetTtcCalc(taDocument.calculResteAReglerComplet());
							taRReglement.setAffectation(taDocument.calculResteAReglerComplet());		

							taDocument.addRReglement(taRReglement);
							taDocument.affecteReglementFacture(typePaiementDefaut);
							taDocument.calculRegleDocument();

							dao.begin(transaction);
							for (TaRReglement taRReglementDoc : taDocument.getTaRReglements()){
								taRReglementDoc=paReglementController.enregistreTaReglement(dao,taRReglementDoc,false);
							}							
							taDocument=dao.enregistrerMerge(taDocument);

							dao.commit(transaction);
						}
					}
					actRefresh(); //deja present
				} catch (Exception e) {
					logger.error("", e);
				}
			}
			return event;
		}
	}

	private void initController()	{
		try {	
			setGrille(vue.getTableFacture());
			initSashFormWeight();
			setDaoStandard(dao);
			//			setTableViewerStandard(tableViewer);
			//			setDbcStandard(dbc);

			initVue();
			//boutton cahcés pour l'instant mais peut être à supprimer définitivement
			vue.getBtnFermer().setVisible(false);
			vue.getBtnSuivant().setVisible(false);
			vue.getBtnToutRegler().setVisible(false);
			
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
			vue.getGroupParam().setMenu(popupMenuGrille);
			vue.getGroupFactureNonRegle().setMenu(popupMenuGrille);
			vue.getPaGroupTotaux().setMenu(popupMenuGrille);
			vue.getTableFacture().setMenu(popupMenuGrille);
			vue.getShell().setMenu(popupMenuGrille);
			vue.setMenu(popupMenuGrille);

			initEtatBouton();
			vue.getPaEcran().layout();
			vue.getScrolledComposite().setMinSize(vue.getPaEcran().computeSize(SWT.DEFAULT, SWT.DEFAULT));
//			affectationReglementControllerMini = new AffectationReglementControllerMini(this,paAffectationReglement,this.getEm());
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaTiersController", e);
		}
	}
	
	protected void initImageBouton() {
		super.initImageBouton();
		String C_IMAGE_VALIDER = "/icons/accept.png";
		vue.getBtnValiderParam().setImage(Activator.getImageDescriptor(C_IMAGE_VALIDER).createImage());
		vue.layout(true);
	}
	
	public void bind(PaReglementMultiple paReglementMultiple ){
		try {
			
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			modelFacture.setListeEntity(dao.rechercheDocumentNonTotalementRegle(
					LibDateTime.getDate(vue.getTfDateDebutPeriode()),
					LibDateTime.getDate(vue.getTfDateDebutPeriode())
					,null,null));
			selectedFacture = lgrDatabindingUtil.bindTable(vue.getTableFacture(),modelFacture.remplirListe(),IHMEnteteFacture.class,
					new String[] {"Code document","Libellé","Date","Date échéance","Code tiers","Net TTC","Déjà réglé","Reste à régler"},
					new String[] {"100","250","100","150","150","150","150","150"},
					new String[] {"codeDocument","libelleDocument","dateDocument","dateEchDocument","codeTiers","netTtcCalc","regleCompletDocument",
				"resteAReglerComplet"},1
			);


//			affectationReglementControllerMini.bind();
			vue.getTableFacture().addMouseListener(
					new MouseAdapter() {

						public void mouseDoubleClick(MouseEvent e) {
							String idEditor = TypeDoc.getInstance().getEditorDoc()
									.get(TypeDoc.TYPE_FACTURE);
							String valeurIdentifiant = ((IHMEnteteFacture) selectedFacture
									.getValue()).getCodeDocument();
							ouvreDocument(valeurIdentifiant, idEditor);
						}

					});
			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			setTableViewerStandard(lgrDatabindingUtil.getMapLgrTableViewer().get(vue.getTableFacture()));

					 
			setDbcStandard(dbc);
			selectedFacture.addChangeListener(new IChangeListener() {
				
				public void handleChange(ChangeEvent event) {
					changementDeSelection();
				}

			});
			changementDeSelection();
			selectedCritere = new IHMEnteteDocument();
			bindingFormSimple(dbc, realm, selectedCritere, IHMEnteteDocument.class);

			initEtatBouton(true);
		} catch(Exception e) {
			logger.error("",e);
			vue.getLaMessage().setText(e.getMessage());
		}
	}

	private void changementDeSelection() {
		try {
			taDocument=null;
			if(paReglementController.getDaoStandard().dataSetEnModif() ){
				paReglementController.actAnnuler();
			}
			if(getAffectationReglementController().getDaoStandard().dataSetEnModif()){
				getAffectationReglementController().actAnnuler();
			}		
			if(getAffectationAvoirController().getDaoStandard().dataSetEnModif()){
				getAffectationAvoirController().actAnnuler();
			}
			
			if(getAffectationAcompteController().getDaoStandard().dataSetEnModif()){
				getAffectationAcompteController().actAnnuler();
			}
			
			
			if(selectedFacture==null ||selectedFacture.getValue()==null)
				lgrDatabindingUtil.getMapLgrTableViewer().get(vue.getTableFacture()).selectionGrille(0);
			if(selectedFacture!=null && selectedFacture.getValue()!=null) {
				if(((IHMEnteteFacture) selectedFacture.getValue()).getIdDocument()!=null) {
					taDocument = dao.findById(((IHMEnteteFacture) selectedFacture.getValue()).getIdDocument());
				}
				//null a tester ailleurs, car peut etre null en cas d'insertion
				fireChangementDeSelection(new ChangementDeSelectionEvent(SWTPaReglementMultipleController.this));
			}else
				selectedFacture.setValue(new IHMEnteteFacture());
			
			
			fireChangementDeSelection(new ChangementDeSelectionEvent(SWTPaReglementMultipleController.this));
			if(paVisuReglementControlleur!=null){
				if(taDocument==null)
					paVisuReglementControlleur.actRefresh(null);
				else
					paVisuReglementControlleur.actRefresh(taDocument);
				findExpandIem(vue.getExpandBar(),paVisuReglementControlleur.getVue()).setExpanded(
					paVisuReglementControlleur.getModelReglement().getListeObjet().size()>0);
			}
			
			affectationReglementController.actRefresh();
			affectationAvoirController.actRefresh();
			affectationAcompteController.actRefresh();
			
			findExpandIem(vue.getExpandBar(),paAffectationReglement).setExpanded(
					affectationReglementController.getModele().getListeObjet().size()>0);
			expandedItem(paAffectationReglement);
			
			findExpandIem(vue.getExpandBar(),paAffectationAvoir).setExpanded(
					affectationAvoirController.getModele().getListeObjet().size()>0);
			expandedItem(paAffectationAvoir);
			
			findExpandIem(vue.getExpandBar(),paAffectationAcompte).setExpanded(
					affectationAcompteController.getModele().getListeObjet().size()>0);
			expandedItem(paAffectationAcompte);
			
			initTotaux(true);
		} catch (Exception e) {
			logger.error("",e );
		}
	}

	public Composite getVue() {return vue;}

	public ResultAffiche configPanel(ParamAffiche param){
		if (param!=null){
			Map<String,String[]> map = dao.getParamWhereSQL();
			//#JPA
			//			ibTaTable.ouvreDataset();
			if (param.getFocusDefautSWT()!=null && !param.getFocusDefautSWT().isDisposed())
				param.getFocusDefautSWT().setFocus();
			else param.setFocusDefautSWT(vue.getTfTiers());
			TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
			TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();

			if(LibDateTime.isDateNull(taInfoEntreprise.getDatedebRegInfoEntreprise())){
				MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().
						getShell(),"Date de début de prise en compte des règlements",
						"La date de début de prise en compte des règlements doit être renseignée"
						+" avant d'utiliser la gestion des règlements." +
						"\r\n" +
								" La description de cette date se fait" +
								" dans l'écran "+"'Dossier/Exercice'");
				try {
					actFermer();
				} catch (Exception e) {
					logger.error("",e);
				}
			}
			if((param).getCodeDocument()!=null && !(param).getCodeDocument().equals("")){
				document=new TaFactureDAO(getEm()).findByCode(param.getCodeDocument());
				vue.getTfFacture().setText(document.getCodeDocument());
			}
			if(param instanceof ParamAfficheReglementMultiple){
				if(((ParamAfficheReglementMultiple)param).getIdTiers()!=0) {
					tiers=new TaTiersDAO(getEm()).findById(((ParamAfficheReglementMultiple)param).getIdTiers());
					vue.getTfTiers().setText(tiers.getCodeTiers());
				}
				if(((ParamAfficheReglementMultiple)param).getIdFacture()!=0) {
					document=new TaFactureDAO(getEm()).findById(((ParamAfficheReglementMultiple)param).getIdFacture());
					vue.getTfFacture().setText(document.getCodeDocument());
				}
				
				if(((ParamAfficheReglementMultiple)param).getDateDeb()!=null){
					setDateDeb(((ParamAfficheReglementMultiple)param).getDateDeb());
//					vue.getTfDateDebutPeriode().setSelection(getDateDeb());
					LibDateTime.setDate(vue.getTfDateDebutPeriode(), getDateDeb());
				}else{
//					vue.getTfDateDebutPeriode().setSelection(taInfoEntreprise.getDatedebRegInfoEntreprise());
					LibDateTime.setDate(vue.getTfDateDebutPeriode(), taInfoEntreprise.getDatedebRegInfoEntreprise());
				}
				if(LibDateTime.getDate(vue.getTfDateDebutPeriode()).before(taInfoEntreprise.getDatedebRegInfoEntreprise()))
					//vue.getTfDateDebutPeriode().setSelection(taInfoEntreprise.getDatedebRegInfoEntreprise());
					LibDateTime.setDate(vue.getTfDateDebutPeriode(), taInfoEntreprise.getDatedebRegInfoEntreprise());
				
				if(((ParamAfficheReglementMultiple)param).getDateFin()!=null){
					setDateFin(((ParamAfficheReglementMultiple)param).getDateFin());
//					vue.getTfDateFinPeriode().setSelection(getDateFin());
					LibDateTime.setDate(vue.getTfDateFinPeriode(), getDateFin());
				}else{
//					vue.getTfDateFinPeriode().setSelection(taInfoEntreprise.getDatefinInfoEntreprise());
					LibDateTime.setDate(vue.getTfDateFinPeriode(), taInfoEntreprise.getDatefinInfoEntreprise());
				}
				if(LibDateTime.getDate(vue.getTfDateFinPeriode()).before(LibDateTime.getDate(vue.getTfDateFinPeriode())))
					//vue.getTfDateDebutPeriode().setSelection(vue.getTfDateFinPeriode().getSelection());
					LibDateTime.setDate(vue.getTfDateDebutPeriode(), LibDateTime.getDate(vue.getTfDateFinPeriode()));
				
			}

			if(param.getEcranAppelant()!=null) {
				ecranAppelant = param.getEcranAppelant();
			}

			bind(vue);
			
			lgrDatabindingUtil.getMapLgrTableViewer().get(vue.getTableFacture()).selectionGrille(0);
			VerrouInterface.setVerrouille(false);
			initEtatBouton(true);
//			setFocusCourantSWT(vue.getTfTiers());
		}
		return null;
	}
	
	protected void initVerifySaisie() {
		if (mapInfosVerifSaisie == null)
			mapInfosVerifSaisie = new HashMap<Control, InfosVerifSaisie>();
		
		mapInfosVerifSaisie.put(vue.getTfTiers(), new InfosVerifSaisie(new TaTiers(),Const.C_CODE_TIERS,null));
//		mapInfosVerifSaisie.put(paFormulaireReglement.getTfCODE_DOCUMENT(), new InfosVerifSaisie(new TaFacture(),Const.C_CODE_DOCUMENT,null));
//		mapInfosVerifSaisie.put(paFormulaireReglement.getTfCODE_REGLEMENT(), new InfosVerifSaisie(new TaReglement(),Const.C_CODE_REGLEMENT,null));
//		mapInfosVerifSaisie.put(paFormulaireReglement.getTfCPT_COMPTABLE(), new InfosVerifSaisie(new TaCompteBanque(),Const.C_CPTCOMPTABLE,null));
//		mapInfosVerifSaisie.put(paFormulaireReglement.getTfLIBELLE_PAIEMENT(), new InfosVerifSaisie(new TaReglement(),Const.C_LIBELLE_PAIEMENT,null));
//		mapInfosVerifSaisie.put(paFormulaireReglement.getTfMONTANT_AFFECTE(), new InfosVerifSaisie(new TaRReglement(),Const.C_MONTANT_AFFECTE,null));
//		mapInfosVerifSaisie.put(paFormulaireReglement.getTfTYPE_PAIEMENT(), new InfosVerifSaisie(new TaTPaiement(),Const.C_CODE_T_PAIEMENT,null));
	
		
		initVerifyListener(mapInfosVerifSaisie, dao);
	}

	protected void initComposantsVue() throws ExceptLgr {}


	protected void initEtatBouton() {
		initEtatBouton(false);	
	}	

	@Override
	protected void initEtatBouton(boolean initFocus) {
		boolean trouve = modelFacture.getListeObjet().size()>0;
		switch (daoStandard.getModeObjet().getMode()) {
		case C_MO_INSERTION:
			enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_FERMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID,false);
			enableActionAndHandler(C_COMMAND_DOCUMENT_TOUT_REGLER_ID, false);
			if (vue.getTableFacture()!=null)vue.getTableFacture().setEnabled(false);
			vue.getTfTiers().setEnabled(false);
			vue.getTfFacture().setEnabled(false);
			break;
		case C_MO_EDITION:
			enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_FERMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID,true);
			enableActionAndHandler(C_COMMAND_DOCUMENT_TOUT_REGLER_ID, false);
			if (vue.getTableFacture()!=null)vue.getTableFacture().setEnabled(false);
			vue.getTfTiers().setEnabled(false);
			vue.getTfFacture().setEnabled(false);
			break;
		case C_MO_CONSULTATION:
			enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,trouve);
			enableActionAndHandler(C_COMMAND_GLOBAL_FERMER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID,true);
			enableActionAndHandler(C_COMMAND_DOCUMENT_TOUT_REGLER_ID, trouve);
			if (vue.getTableFacture()!=null)vue.getTableFacture().setEnabled(true);
			vue.getTfTiers().setEnabled(true);
			vue.getTfFacture().setEnabled(true);
			break;
		default:
			break;
		}
			
		initEtatComposant();
		if (initFocus)
			initFocusSWT(daoStandard, mapInitFocus);	
		
	}
	protected void initMapComposantChamps() {
		if (mapComposantChamps == null) 
			mapComposantChamps = new LinkedHashMap<Control,String>();

		if (listeComposantFocusable == null) 
			listeComposantFocusable = new ArrayList<Control>();
		
		
		mapComposantChamps.put(vue.getTfTiers(), Const.C_CODE_TIERS);
		mapComposantChamps.put(vue.getTfFacture(), Const.C_CODE_DOCUMENT);
		
		listeComposantFocusable.add(vue.getTfDateDebutPeriode());
		listeComposantFocusable.add(vue.getTfDateFinPeriode());
		listeComposantFocusable.add(vue.getTfTiers());
		listeComposantFocusable.add(vue.getTfFacture());
		listeComposantFocusable.add(vue.getBtnValiderParam());
		
		listeComposantFocusable.add(vue.getTableFacture());
		listeComposantFocusable.add(paAffectationReglement.getTableReglementNonAffecte());

//		vue.getTfDateDebutPeriode().setToolTipText("DateDebutPeriode");
//		vue.getTfDateFinPeriode().setToolTipText("DateFinPeriode");
//		vue.getTfTiers().setToolTipText("Tiers");

		
		listeComposantFocusable.add(vue.getBtnValiderParam());
		//listeComposantFocusable.add(paAffectationReglement.getBtnAffecter());
//		listeComposantFocusable.add(paFormulaireReglement.getBtnAnnuler());
		listeComposantFocusable.add(vue.getBtnFermer());
//		listeComposantFocusable.add(vue.getBtnRegler());
		listeComposantFocusable.add(vue.getBtnSuivant());
		
//		listeComposantFocusable.add(paFormulaireReglement.getBtnEnregistrer());


		if(mapInitFocus == null) 
			mapInitFocus = new LinkedHashMap<ModeObjet.EnumModeObjet,Control>();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION,vue.getTfTiers());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION,vue.getTfTiers());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION,vue.getTfTiers());

		activeModifytListener();

//		vue.getTfTiers().addFocusListener(new FocusAdapter() {
//			public void focusLost(FocusEvent e) {
//				try {
//					if(vue.getTfTiers().isEnabled()){
//						IStatus status=validateUIField(Const.C_CODE_TIERS,vue.getTfTiers().getText().toUpperCase());
//						if(status.getSeverity()== IStatus.ERROR){
//							MessageDialog.openWarning(vue.getShell(),"Erreur de saisie",status.getMessage());
//							throw new Exception(status.getMessage());
//						}
//					}
//				} catch (Exception e1) {
//					logger.error("", e1);
//					vue.getTfTiers().forceFocus();
//				}
//			}
//		});
//		vue.getTfFacture().addFocusListener(new FocusAdapter() {
//			public void focusLost(FocusEvent e) {
//				try {
//					if(vue.getTfTiers().isEnabled()){
//						IStatus status=validateUIField(Const.C_CODE_DOCUMENT,vue.getTfFacture().getText().toUpperCase());
//						if(status.getSeverity()== IStatus.ERROR){
//							MessageDialog.openWarning(vue.getShell(),"Erreur de saisie",status.getMessage());
//							throw new Exception(status.getMessage());
//						}
//					}
//				} catch (Exception e1) {
//					logger.error("", e1);
//					vue.getTfFacture().forceFocus();
//				}
//			}
//		});
	}

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

		mapCommand.put(C_COMMAND_GLOBAL_PRECEDENT_ID, handlerPrecedent);
		mapCommand.put(C_COMMAND_GLOBAL_SUIVANT_ID, handlerSuivant);
		mapCommand.put(C_COMMAND_DOCUMENT_TOUT_REGLER_ID, handlerToutRegler);


		initFocusCommand(String.valueOf(this.hashCode()),listeComposantFocusable,mapCommand);

		if (mapActions == null)
			mapActions = new LinkedHashMap<Button, Object>();

		mapActions.put(vue.getBtnFermer(), C_COMMAND_GLOBAL_FERMER_ID);


		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID,C_COMMAND_GLOBAL_SELECTION_ID, C_COMMAND_GLOBAL_REFRESH_ID ,C_COMMAND_DOCUMENT_TOUT_REGLER_ID};
		mapActions.put(null, tabActionsAutres);
		
		vue.getBtnValiderParam().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					actRefreshCourant();
				} catch (Exception e1) {
					logger.error("", e1);
				}
			}
		});
	}


	public SWTPaReglementMultipleController getThis(){
		return this;
	}

	@Override
	public boolean onClose() throws ExceptLgr {
		boolean retour = true;
		VerrouInterface.setVerrouille(true);
		switch (dao.getModeObjet().getMode()) {
		case C_MO_INSERTION:
		case C_MO_EDITION:
			if(MessageDialog.openQuestion(
					vue.getShell(),
					MessagesEcran.getString("Message.Attention"),
					MessagesEcran.getString("Tiers.Message.Enregistrer"))) {

				try {
					actEnregistrer();
				} catch(Exception e) {
					vue.getLaMessage().setText(e.getMessage());
					logger.error("",e);
					retour= false;
				}
			} else {
				silencieu = true;
				try {
					actAnnuler();
					//fireAnnuleTout(new AnnuleToutEvent(this));
				} catch (Exception e) {
					logger.error("",e);
					vue.getLaMessage().setText(e.getMessage());
				}
			}

			break;
		case C_MO_CONSULTATION:
			break;
		default:
			break;
		}

		if(retour) {
			if (ecranAppelant instanceof SWTPaAideControllerSWT) {
//				setListeEntity(getModelTiers().remplirListe());
//				dao.initValeurIdTable(taRReglement);
//				fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
//						dao.getChampIdEntite(), dao.getValeurIdTable(),
//						selectedReglement.getValue())));
				retour = true;
			}
		} 
		//		else {
		//			setFocusCourantSWT(ibTaTable.getFModeObjet().getFocusCourantSWT());
		//			getFocusCourantSWT().setFocus();
		//		}


		return retour;
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
						tiers=entity;
						vue.getTfTiers().setText(tiers.getCodeTiers());
						//actRefresh();
					}
					if(getFocusAvantAideSWT().equals(vue.getTfFacture())){
						TaFacture entity = null;
						TaFactureDAO dao = new TaFactureDAO(getEm());
						entity = dao.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
						dao = null;
						validateUIField(Const.C_CODE_DOCUMENT,entity.getCodeDocument());
						document=entity;
						vue.getTfFacture().setText(document.getCodeDocument());
						//actRefresh();
					}

					ctrlUnChampsSWT(getFocusAvantAideSWT());
				} catch (Exception e) {
					logger.error("",e);
					if(getFocusAvantAideSWT()!=null)setFocusCourantSWT(getFocusAvantAideSWT());
					vue.getLaMessage().setText(e.getMessage());
				}
			}			
			if (getFocusAvantAideSWT() instanceof Table) {
				if (getFocusAvantAideSWT() == vue.getTableFacture()) {
					if(((ResultAffiche) evt.getRetour()).getSelection()!=null)
						lgrDatabindingUtil.getMapLgrTableViewer().get(vue.getTableFacture()).
						setSelection(((ResultAffiche) evt.getRetour()).getSelection(),true);
				}
			}
		} else if (evt.getRetour() != null){
			if (getFocusAvantAideSWT() instanceof Table) {
				if (getFocusAvantAideSWT() == vue.getTableFacture()) {
				}
			}
		}
		super.retourEcran(evt);
	}


	@Override
	protected void actInserer() throws Exception{
		try {
			if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
				VerrouInterface.setVerrouille(true);
//				setSwtOldTiers();
				initEtatBouton(true);
			}
		}
		finally {
			initEtatComposant();
			VerrouInterface.setVerrouille(false);
		}
	}

	@Override
	protected void actModifier() throws Exception{
		try {
			if(selectedFacture!=null && selectedFacture.getValue()!=null){
				if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
					if(!LgrMess.isDOSSIER_EN_RESEAU()){
						//					setSwtOldTiers();
						taDocument = dao.findById(((IHMEnteteFacture) selectedFacture.getValue()).getIdDocument());				
					}else{
						if(!setSwtOldTiersRefresh())throw new Exception();
					}
					dao.modifier(taDocument);
					initEtatBouton(true);
				}
			}
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		}
	}

	@Override
	protected void actSupprimer() throws Exception{
	}

	@Override
	protected void actFermer() throws Exception{
		//(controles de sortie et fermeture de la fenetre) => onClose()
		if(onClose())
			closeWorkbenchPart();
	}

	@Override
	protected void actAnnuler() throws Exception{
		try {
			VerrouInterface.setVerrouille(true);
			switch (dao.getModeObjet().getMode()) {
			case C_MO_INSERTION:
				break;
			case C_MO_EDITION:
//				if (silencieu || (!silencieu && MessageDialog.openQuestion(
//						vue.getShell(),
//						MessagesEcran.getString("Message.Attention"),
//						MessagesEcran.getString("Tiers.Message.Annuler")))){
					dao.annuler(taDocument);
					hideDecoratedFields();
					if(!annuleToutEnCours) {
						fireAnnuleTout(new AnnuleToutEvent(this));
					}
//				}
				initEtatBouton();	

				break;
			case C_MO_CONSULTATION:
				break;
			default:
				break;
			}		
		} finally {
			VerrouInterface.setVerrouille(false);
		}
	}

	@Override
	protected void actImprimer() throws Exception{
	}

	@Override
	protected void actPrecedent() throws Exception {
		ChangementDePageEvent evt = new ChangementDePageEvent(this,
				ChangementDePageEvent.PRECEDENT);
		fireChangementDePage(evt);
	}

	@Override
	protected void actSuivant() throws Exception {
		ChangementDePageEvent evt = new ChangementDePageEvent(this,
				ChangementDePageEvent.SUIVANT);
		fireChangementDePage(evt);
	}
	
	@Override
	protected boolean aideDisponible() {
		boolean result = false;
		switch (dao.getModeObjet().getMode()) {
		case C_MO_CONSULTATION:
		case C_MO_EDITION:
		case C_MO_INSERTION:										
			//creation d'une recherche
			if(getFocusCourantSWT().equals(vue.getTfTiers())
					||getFocusCourantSWT().equals(vue.getTfFacture()))result = true;
			break;
		default:
			break;
		}
		return result;
	}

	@Override
	protected void actAide() throws Exception{
		actAide(null);
	}

	@Override
	protected void actAide(String message) throws Exception{
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
					if (getFocusCourantSWT().equals(vue.getTfTiers())) {
						PaTiersSWT paTiersSWT = new PaTiersSWT(s2,SWT.NULL);
						SWTPaTiersController swtPaTiersController = new SWTPaTiersController(paTiersSWT);
						paramAfficheAideRecherche.setForceAffichageAideRemplie(false);
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
						paramAfficheAideRecherche.setTypeObjet(swtPaTiersController.getClassModel());
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_TIERS);
					}
					if (getFocusCourantSWT().equals(vue.getTfFacture())) {
						editorCreationId = EditorFacture.ID_EDITOR;
						editorInputCreation = new EditorInputFacture();
						
						paramAfficheAideRecherche.setForceAffichageAideRemplie(false);
						
						ModelGeneralObjet<IHMAideFacture,TaFactureDAO,TaFacture> modelFactureAide =  
							new ModelGeneralObjet<IHMAideFacture,TaFactureDAO,TaFacture>(dao,IHMAideFacture.class);
						String codeTiers="";
						String codeDocument="";
						if(tiers!=null)codeTiers=tiers.getCodeTiers();
						if(document!=null)codeDocument=document.getCodeDocument();
						modelFactureAide.setListeEntity(dao.rechercheDocumentNonTotalementRegle(null,null,codeTiers,codeDocument));
						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
						controllerEcranCreation = this;

						paramAfficheAideRecherche.setTypeEntite(TaFacture.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_DOCUMENT);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfFacture().getText());
						paramAfficheAideRecherche.setControllerAppelant(getThis());
						paramAfficheAideRecherche.setCleListeTitre("SWTPaEditorFactureController");
						paramAfficheAideRecherche.setModel(modelFactureAide);
						paramAfficheAideRecherche.setTypeObjet(IHMAideFacture.class);
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_DOCUMENT);					}
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

					dbc.getValidationStatusMap().removeMapChangeListener(
							changeListener);
					// LgrShellUtil.afficheAideSWT(paramAfficheAide, null,
					// paAide,paAideController, s);
					/*****************************************************************/
					paAideController.configPanel(paramAfficheAide);
					/*****************************************************************/
					dbc.getValidationStatusMap().addMapChangeListener(
							changeListener);
				}

			} finally {
				VerrouInterface.setVerrouille(false);
				vue.setCursor(Display.getCurrent().getSystemCursor(
						SWT.CURSOR_ARROW));
			}
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
		String validationContext = "REGLEMENT";
		try {
			IStatus s =null;
			if(nomChamp.equals(Const.C_CODE_DOCUMENT)&& value!=null && value!="" 
				//&&!dao.dataSetEnModif()
				){
				TaFactureDAO daoFacture = new TaFactureDAO(getEm());
				TaFacture f = new TaFacture(true);
				PropertyUtils.setSimpleProperty(f, nomChamp, value);
				s = daoFacture.validate(f, nomChamp, "FACTURE");
				if (s.getSeverity() != IStatus.ERROR) {
					if(document==null || !f.getCodeDocument().equals(document.getCodeDocument())){
						if(!f.getCodeDocument().equals("")){
							f = daoFacture.findByCode((String) value);
							document=f;
							vue.getTfFacture().setText(f.getCodeDocument());
							vue.getTfTiers().setText(f.getTaTiers().getCodeTiers());
						}else {
							document=null;
							vue.getTfFacture().setText(f.getCodeDocument());
						}
					}
				}
			} else		
				if (nomChamp.equals(Const.C_CODE_TIERS)) {
				if (value!=null && value!="" 
					) {
					TaTiersDAO dao = new TaTiersDAO(getEm());
					dao.getModeObjet().setMode(EnumModeObjet.C_MO_EDITION);
					TaTiers f = new TaTiers();
					PropertyUtils.setSimpleProperty(f, nomChamp, value);
					s = dao.validate(f, nomChamp, "FACTURE");
					if (s.getSeverity() != IStatus.ERROR) {
						if(tiers==null || !f.getCodeTiers().equals(tiers.getCodeTiers())){
							if(!f.getCodeTiers().equals("")){
								f = dao.findByCode((String) value);
								tiers=f;
								vue.getTfTiers().setText(f.getCodeTiers());
								vue.getTfFacture().setText("");
							}else {
								tiers=null;
								vue.getTfTiers().setText(f.getCodeTiers());
							}
						}
					}
					dao = null;
				}else
					tiers=null;
				}
			if (s==null )return new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
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
//						if((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)){
							for (TaRReglement taRReglement : taDocument.getTaRReglements()){
								taRReglement=paReglementController.enregistreTaReglement(dao,taRReglement,false);
							}							
							taDocument=dao.enregistrerMerge(taDocument);
//						}
//						else taDocument=dao.enregistrerMerge(taDocument);

						dao.commit(transaction);
						fireChangementMaster(new ChangementMasterEntityEvent(this,taDocument));
						actRefresh(); //deja present
						initEtatBouton(true);
						getPaReglementController().setTaRReglement(null);
						getPaReglementController().actRefresh(taDocument,dao,taDocument.getTaRReglement());
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

	public boolean isUtilise(){
		return (((IHMEnteteFacture)selectedFacture.getValue()).getIdDocument()!=null && 
				!dao.recordModifiable(dao.getNomTable(),
						((IHMEnteteFacture)selectedFacture.getValue()).getIdDocument()))||
						!dao.autoriseModification(taDocument);		
	}

	public String getIdCommentaire() {
		return idCommentaire;
	}

	public void setIdCommentaire(String idCommentaire) {
		this.idCommentaire = idCommentaire;
	}

	//	public SWT_IB_TA_TIERS getIbTaTable() {
	//		return ibTaTable;
	//	}

	public void initEtatComposant(){
		//		super.initEtatComposantGeneral();
		//		if (ibTaTable.getFIBQuery().isOpen()){
		//			vue.getTfCODE_TIERS().setEditable(ibTaTable.recordModifiable(ibTaTable.nomTable,
		//					ibTaTable.getChamp_Obj(ibTaTable.champIdTable)));
		//		}	
	}




	public void setSwtOldDocument() {
//		if (selectedFacture!=null&&selectedFacture.getValue()!=null){
//			this.swtOldReglement=IHMReglement.copy((IHMReglement)selectedReglement.getValue());
//		}
//		else{
//			if (tableViewer.selectionGrille(0)){
//				//dao.refresh(dao.findById(((IHMReglement) selectedReglement.getValue()).getIdTiers()));
//				this.swtOldReglement=IHMReglement.copy((IHMReglement)selectedReglement.getValue());
//				tableViewer.setSelection(new StructuredSelection((IHMReglement)selectedReglement.getValue()),true);
//			}
//		}
	}

	public boolean containsEntity(TaRReglement entite){
//		if(modelReglement.getListeEntity()!=null){
//			for (Object e : modelReglement.getListeEntity()) {
//				if(((TaRReglement)e).getId()==
//					entite.getId())return true;
//			}
//		}
		return false;
	}
	
	public boolean setSwtOldTiersRefresh() {
//		try {	
//			if (selectedReglement.getValue()!=null){
//				TaRReglement taOld =dao.findById(taRReglement.getId());
//				taOld=dao.refresh(taOld);
//				if(containsEntity(taRReglement)) 
//					modelReglement.getListeEntity().remove(taRReglement);
//				if(!taRReglement.getVersionObj().equals(taOld.getVersionObj())){
//					taRReglement=taOld;
//					if(!containsEntity(taRReglement)) 
//						modelReglement.getListeEntity().add(taRReglement);					
//					if(!containsEntity(taRReglement)) 
//						modelReglement.getListeEntity().add(taRReglement);
//					changementDeSelection();
//					actRefresh();
//					dao.messageNonAutoriseModification();
//				}
//				taRReglement=taOld;
//				fireChangementMaster(new ChangementMasterEntityEvent(this,taRReglement,true));
//				this.swtOldReglement=IHMReglement.copy((IHMReglement)selectedReglement.getValue());
//			}
			return true;
//		} catch (Exception e) {
//			return false;
//		}		
	}
	public void setVue(PaReglementMultiple vue) {
		super.setVue(vue);
		this.vue = vue;
	}



	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null) 
			mapComposantDecoratedField = new LinkedHashMap<Control,DecoratedField>();
		mapComposantDecoratedField.put(vue.getTfTiers(), vue.getFieldCODE_TIERS());
		mapComposantDecoratedField.put(vue.getTfFacture(), vue.getFieldCODE_DOCUMENT());
	}



	public Class getClassModel() {
		return classModel;
	}



	@Override
	protected void sortieChamps() {
		// TODO Auto-generated method stub
		try {
			if(focusDansEcran()){
				ctrlUnChampsSWT(vue.getTfTiers());
			}
		} catch (Exception e) {
			logger.error("", e);
			vue.getTfTiers().forceFocus();
		}
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
	

	private void initVue() {
		
		paFormulaireReglement = new PaAffectationReglementSurFacture(
				vue.getExpandBar(), SWT.PUSH);
				setPaReglementController(new PaReglementController(paFormulaireReglement, getEm())); 
				getPaReglementController().setIntegrer(false);
				getPaReglementController().setAfficheListeReglements(false);
				getPaReglementController().setEnregistrementDirect(true);
				
		paFormulaireReglement.getCompositeForm().setWeights(new int[]{100,0});
		paFormulaireReglement.getLaTitreFormulaire().setText("Règlement");
		
		DefaultFrameGrilleSansBouton_grilleReduite visuReglement = new DefaultFrameGrilleSansBouton_grilleReduite(vue.getExpandBar(), SWT.PUSH);
		paVisuReglementControlleur = new PaVisuReglementControlleur(visuReglement,getEm());
		
		addExpandBarItem(vue.getExpandBar(), visuReglement,
		"Règlements liés au document en cours ", FacturePlugin.getImageDescriptor(
		"/icons/user.png").createImage(), SWT.DEFAULT, 120);
		vue.getExpandBar().getItem(0).setExpanded(false);
		
		addExpandBarItem(vue.getExpandBar(), paFormulaireReglement, "Nouveau règlement", Activator
				.getImageDescriptor("/icons/logo_lgr_16.png").createImage(), SWT.DEFAULT, 350);
		vue.getExpandBar().getItem(1).setExpanded(true);
		

		
		paAffectationReglement = new PaAffectationReglement(vue.getExpandBar(), SWT.PUSH);
		setAffectationReglementController(new AffectationReglementController(paAffectationReglement, getEm())); 
		addExpandBarItem(vue.getExpandBar(), paAffectationReglement, "Affectation d'un règlement", Activator
				.getImageDescriptor("/icons/logo_lgr_16.png").createImage(), SWT.DEFAULT, 130);


		paAffectationAvoir = new PaAffectationReglement(vue.getExpandBar(), SWT.PUSH);
		setAffectationAvoirController(new AffectationAvoirController(paAffectationAvoir, getEm())); 
		addExpandBarItem(vue.getExpandBar(), paAffectationAvoir, "Affectation d'un avoir", Activator
				.getImageDescriptor("/icons/logo_lgr_16.png").createImage(), SWT.DEFAULT, 130);
		
		paAffectationAcompte = new PaAffectationReglement(vue.getExpandBar(), SWT.PUSH);
		setAffectationAcompteController(new AffectationAcompteController(paAffectationAcompte, getEm())); 
		addExpandBarItem(vue.getExpandBar(), paAffectationAcompte, "Affectation d'un acompte", Activator
				.getImageDescriptor("/icons/logo_lgr_16.png").createImage(), SWT.DEFAULT, 130);
		
		affectationReglementController.setTitre("Affectation d'un règlement");
		affectationAcompteController.setTitre("Affectation d'un acompte");
		affectationAvoirController.setTitre("Affectation d'un avoir");
		
		vue.getExpandBar().addExpandListener(new ExpandAdapter() {
			@Override
			public void itemExpanded(ExpandEvent e) {
				expandedItem(((ExpandItem)e.item).getControl());
			}
		});
		
	}

	@Override
	protected void actRefresh() throws Exception {
		actRefreshCourant();
		changementDeSelection();
//		try{
//			dao=new TaFactureDAO(new JPABdLgr().getEntityManager());
//			dao.getModeObjet().setMode(EnumModeObjet.C_MO_CONSULTATION);
//			fireChangementMaster(new ChangementMasterEntityEvent(this));
//			if(document!=null)
//				modelFacture.setListeEntity(dao.rechercheDocumentNonTotalementRegle(null,null,
//						vue.getTfTiers().getText(),vue.getTfFacture().getText()));
//			else
//			modelFacture.setListeEntity(dao.rechercheDocumentNonTotalementRegle(
//					vue.getTfDateDebutPeriode().getSelection(),vue.getTfDateFinPeriode().getSelection(),
//					vue.getTfTiers().getText(),vue.getTfFacture().getText()));
//			
//			WritableList writableListFacture = new WritableList(modelFacture.remplirListe(), IHMEnteteFacture.class);
//			getTableViewerStandard().setInput(writableListFacture);			
//			changementDeSelection();
//			initEtatBouton();
//			if(getFocusCourantSWT()!=null && (getFocusCourantSWT().equals(vue.getTfFacture())||
//					getFocusCourantSWT().equals(vue.getTfTiers())))initEtatBouton();
//			else initEtatBouton(true);
//
//	}finally{
//		vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
//	}
}

	protected void actRefreshCourant() throws Exception {
		try{
			
			if(paReglementController.getDaoStandard().dataSetEnModif() ){
				paReglementController.actAnnuler();
			}
			if(getAffectationReglementController().getDaoStandard().dataSetEnModif()){
				getAffectationReglementController().actAnnuler();
			}	
			if(getAffectationAvoirController().getDaoStandard().dataSetEnModif()){
				getAffectationAvoirController().actAnnuler();
			}
			
			if(getAffectationAcompteController().getDaoStandard().dataSetEnModif()){
				getAffectationAcompteController().actAnnuler();
			}
			
			
			dao=new TaFactureDAO(new JPABdLgr().getEntityManager());
			dao.getModeObjet().setMode(EnumModeObjet.C_MO_CONSULTATION);
			fireChangementMaster(new ChangementMasterEntityEvent(this));
			modelFacture.getListeEntity().clear();
			if(document!=null)
				modelFacture.setListeEntity(dao.rechercheDocumentNonTotalementRegle(null,null,
						vue.getTfTiers().getText(),vue.getTfFacture().getText()));
			else
			modelFacture.setListeEntity(dao.rechercheDocumentNonTotalementRegle(
					LibDateTime.getDate(vue.getTfDateDebutPeriode()),
					LibDateTime.getDate(vue.getTfDateFinPeriode()),
					vue.getTfTiers().getText(),vue.getTfFacture().getText()));
			
			WritableList writableListFacture = new WritableList(modelFacture.remplirListe(), IHMEnteteFacture.class);
			getTableViewerStandard().setInput(writableListFacture);			
			changementDeSelection();
			initEtatBouton();
			if(getFocusCourantSWT()!=null && (getFocusCourantSWT().equals(vue.getTfFacture())||
					getFocusCourantSWT().equals(vue.getTfTiers())))initEtatBouton();
			else initEtatBouton(true);
			initTotaux(false);
			
//			 PrintDialog dialog = new PrintDialog( vue.getShell(), SWT.NONE );
//		        PrinterData printerData = dialog.open();
//		        if ( printerData != null ) {
//		          Print print = createPrint( vue.getTableFacture() );
//		          PaperClips.print( new PrintJob( "Snippet1.java", print ).setMargins( 72 ), printerData );
//		          PrintPreview ppd = new PrintPreview(pp, parentShell, _printHandler); 
//		          ppd.open();
//		        }
			
			
			
			
//			PrintJob job = new PrintJob("essai preview",
//					createPrint( vue.getTableFacture() ));
//
//			Composite buttons = new Composite( vue.getShell(), SWT.NONE);
//			buttons.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
//			buttons.setLayout(new RowLayout(SWT.HORIZONTAL));
//
//			final Button prev = new Button(buttons, SWT.PUSH);
//			prev.setText("<< Prev");
//			prev.setEnabled(false);
//
//			final Button next = new Button(buttons, SWT.PUSH);
//			next.setText("Next >>");
//
//			final PrintPreview preview = new PrintPreview(vue.getShell(), SWT.BORDER);
//			preview.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//			preview.setPrintJob(job);
//			next.setEnabled(preview.getPageCount() > 1);
//
//			Listener listener = new Listener() {
//				public void handleEvent(Event event) {
//					int newIndex = preview.getPageIndex();
//					if (event.widget == prev)
//						newIndex--;
//					else
//						newIndex++;
//					preview.setPageIndex(newIndex);
//					prev.setEnabled(newIndex > 0);
//					next.setEnabled(newIndex < preview.getPageCount() - 1);
//				}
//			};
//			prev.addListener(SWT.Selection, listener);
//			next.addListener(SWT.Selection, listener);
//
//			PaperClips.print(job, new PrinterData());
	}finally{
		vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
	}
	}

//	public static Print createPrint( Table table ) {
//	    // Create GridPrint with all columns at default size.
//
//	    DefaultGridLook look = new DefaultGridLook();
//	    look.setCellBorder( new LineBorder() );
//	    RGB background = table.getDisplay().getSystemColor( SWT.COLOR_WIDGET_BACKGROUND ).getRGB();
//	    look.setHeaderBackground( background );
//	    look.setFooterBackground( background );
//
//	    GridPrint grid = new GridPrint( look );
//
//	    // Add header and footer to match table column names.
//	    TableColumn[] columns = table.getColumns();
//	    for ( int i = 0; i < columns.length; i++ ) {
//	      TableColumn col = columns[i];
//
//	      // Add the column to the grid with alignment applied, default width, no
//	      // weight
//	      grid.addColumn( new GridColumn( col.getAlignment(), SWT.DEFAULT, 0 ) );
//
//	      Print cell = createCell( col.getImage(), col.getText(), SWT.CENTER );
//	      grid.addHeader( cell );
//	      grid.addFooter( cell );
//	    }
//
//	    // Add content rows
//	    TableItem[] items = table.getItems();
//	    for ( int i = 0; i < items.length; i++ ) {
//	      TableItem item = items[i];
//	      for ( int j = 0; j < columns.length; j++ )
//	        grid.add( createCell( item.getImage( j ), item.getText( j ), columns[j].getAlignment() ) );
//	    }
//
//	    return grid;
//	  }
//
//	  public static Print createCell( Image image, String text, int align ) {
//	    if ( image == null )
//	      return new TextPrint( text, align );
//
//	    GridPrint grid = new GridPrint( "p, d" );
//	    grid.add( new ImagePrint( image.getImageData(), image.getDevice().getDPI() ) );
//	    grid.add( new TextPrint( text, align ) );
//	    return grid;
//	  }



	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}



	public boolean changementPageValide(){		
		return (daoStandard.selectCount()>0);
	}



	public void setDao(TaFactureDAO dao) {
		this.dao = dao;
	};

	public void addChangementMasterEntityListener(IChangementMasterEntityListener l) {
		listenerList.add(IChangementMasterEntityListener.class, l);
	}
	
	public void removeChangementMasterEntityListener(IChangementMasterEntityListener l) {
		listenerList.remove(IChangementMasterEntityListener.class, l);
	}

	protected void fireChangementMaster(ChangementMasterEntityEvent e) throws Exception {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == IChangementMasterEntityListener.class) {
				if (e == null)
					e = new ChangementMasterEntityEvent(this);
				( (IChangementMasterEntityListener) listeners[i + 1]).changementMasterEntity(e);
			}
		}
	}

	public PaAffectationReglementSurFacture getPaFormulaireReglement() {
		return paFormulaireReglement;
	}
	public PaReglementController getPaReglementController() {
		return paReglementController;
	}

	public void setPaReglementController(PaReglementController paReglementController) {
		this.paReglementController = paReglementController;
	}

	public TaFactureDAO getDao() {
		return dao;
	}

	public TaFacture getTaDocument() {
		return taDocument;
	}

	public void setTaDocument(TaFacture taDocument) {
		this.taDocument = taDocument;
	}

	public AffectationReglementController getAffectationReglementController() {
		return affectationReglementController;
	}

	public void setAffectationReglementController(
			AffectationReglementController affectationReglementController) {
		this.affectationReglementController = affectationReglementController;
	}
	
	public TaTiers getTiers() {
		return tiers;
	}

	public void setTiers(TaTiers tiers) {
		this.tiers = tiers;
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

	public TaFacture getDocument() {
		return document;
	}

	public void setDocument(TaFacture document) {
		this.document = document;
	}

	public PaVisuReglementControlleur getPaVisuReglementControlleur() {
		return paVisuReglementControlleur;
	}

	public void setPaVisuReglementControlleur(
			PaVisuReglementControlleur paVisuReglementControlleur) {
		this.paVisuReglementControlleur = paVisuReglementControlleur;
	}

	@Override
	public void updateMasterEntity(UpdateMasterEntityEvent evt) {
		try {
			actRefresh();
		} catch (Exception e) {
			logger.error("",e);
		}
	}

	
	private void expandedItem(Control e){
		if(e.equals(paFormulaireReglement)) {
			vue.getExpandBar().getItem(2).setExpanded(false);
			try {
				getPaReglementController().actInserer();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//if(getPaReglementController().)
		} else {
			vue.getExpandBar().getItem(1).setExpanded(false);
		}
	}

	public AffectationAvoirController getAffectationAvoirController() {
		return affectationAvoirController;
	}

	public void setAffectationAvoirController(
			AffectationAvoirController affectationAvoirController) {
		this.affectationAvoirController = affectationAvoirController;
	}
	
	protected void initTotaux(boolean partiel) {
		Integer nbLigne=0;
		BigDecimal totalTTC=BigDecimal.valueOf(0);
		double solde = 0;
		double soldeFacture = 0;
		double soldeReglement = 0;
		double soldeAvoir = 0;
		double soldeAcompte = 0;
//		dateDeb=LibDateTime.getDate(vue.getTfDateDebutPeriode());
		dateFin=LibDateTime.getDate(vue.getTfDateFinPeriode());
		TaInfoEntrepriseDAO daoInfos=new TaInfoEntrepriseDAO(getEm());
		TaInfoEntreprise infos =daoInfos.findInstance();
		dateDeb=infos.getDatedebRegInfoEntreprise();
		if(dateDeb==null)dateDeb=LibDate.stringToDate2("01/01/1900");
		

		if(!partiel){
		if(taDocument!=null){
			for (TaFacture doc : modelFacture.getListeEntity()) {
//				if (doc.getAccepte()){
					if(doc.getResteAReglerComplet()!=null)totalTTC=totalTTC.add(doc.getResteAReglerComplet());
					nbLigne++;
//				}
			}

		}
		vue.getTfMT_HT_CALC().setText(LibConversion.bigDecimalToString(totalTTC));
		vue.getTfMT_TTC_CALC().setText(LibConversion.integerToString(nbLigne));
		}
		if(partiel){
		totalTTC=BigDecimal.valueOf(0);
		if(affectationReglementController.getModele().getListeEntity()!=null){
		for (TaReglement doc : affectationReglementController.getModele().getListeEntity()) {
				if(doc.getResteAAffecter()!=null)totalTTC=totalTTC.add(doc.getResteAAffecter());
				nbLigne++;
		}
		}
		findExpandIem(vue.getExpandBar(), paAffectationReglement).setText(affectationReglementController.getTitre()+" - Total des réglements affectables au document sélectionné ("+LibConversion.bigDecimalToString(totalTTC)+")");
//		vue.getTfReglement().setText(LibConversion.bigDecimalToString(totalTTC));
		
		totalTTC=BigDecimal.valueOf(0);
		if(affectationAvoirController.getModele().getListeEntityAvoir()!=null){
		for (TaAvoir doc : affectationAvoirController.getModele().getListeEntityAvoir()) {
				if(doc.getResteAAffecter()!=null)totalTTC=totalTTC.add(doc.getResteAAffecter());
				nbLigne++;
		}
		}
		findExpandIem(vue.getExpandBar(), paAffectationAvoir).setText(affectationAvoirController.getTitre()+" - Total des avoirs affectables au document sélectionné ("+LibConversion.bigDecimalToString(totalTTC)+")");
		
		totalTTC=BigDecimal.valueOf(0);
		if(affectationAcompteController.getModele().getListeEntityAcompte()!=null){
		for (TaAcompte doc : affectationAcompteController.getModele().getListeEntityAcompte()) {
				if(doc.getResteARegler()!=null)totalTTC=totalTTC.add(doc.getResteARegler());
				nbLigne++;
		}
		}
		findExpandIem(vue.getExpandBar(), paAffectationAcompte).setText(affectationAcompteController.getTitre()+" - Total des acomptes affectables au document sélectionné ("+LibConversion.bigDecimalToString(totalTTC)+")");

//		vue.getTfTotAvoir().setText(LibConversion.bigDecimalToString(totalTTC));
		}
		if(tiers!=null && tiers.getCodeTiers()!=null){
	if(! partiel){
		TaFactureDAO taFactureDAO = new TaFactureDAO(dao.getEntityManager());
		List<TaFacture> listeFacture = taFactureDAO.findByCodeTiersAndDate(
				tiers.getCodeTiers(),
				dateDeb,dateFin);
		
		TaReglementDAO taReglementDAO = new TaReglementDAO(dao.getEntityManager());
		List<TaReglement> listeReglement = taReglementDAO.rechercheDocument(
				dateDeb,dateFin,tiers.getCodeTiers());
		
		TaAvoirDAO taAvoirDAO = new TaAvoirDAO(dao.getEntityManager());
		List<TaAvoir> listeAvoir = taAvoirDAO.rechercheDocument(
				dateDeb,dateFin,tiers.getCodeTiers());
		
		TaAcompteDAO taAcompteDAO = new TaAcompteDAO(dao.getEntityManager());
		List<TaAcompte> listeAcompte = taAcompteDAO.rechercheDocument(
				dateDeb,dateFin,tiers.getCodeTiers());

		for (TaFacture taFacture : listeFacture) {
			soldeFacture += taFacture.getNetAPayer().doubleValue();
		}
		
		for (TaReglement doc : listeReglement) {
			soldeReglement += doc.getNetTtcCalc().doubleValue();
		}
		for (TaAcompte doc : listeAcompte) {
			soldeAcompte += doc.getNetTtcCalc().doubleValue();
		}
		for (TaAvoir doc : listeAvoir) {
			soldeAvoir += doc.getNetTtcCalc().doubleValue();
		}
		
		solde=(soldeReglement+soldeAvoir+soldeAcompte) - soldeFacture;
		solde = LibCalcul.arrondi(solde);

		vue.getTfSoldeTiers().setText(LibConversion.doubleToString(solde));
		}
	
		}else vue.getTfSoldeTiers().setText(LibConversion.doubleToString(solde));

		if(tiers==null || tiers.getCodeTiers()==null)vue.getTfSoldeTiers().setText("");
		vue.getTfSoldeTiers().setEnabled(tiers!=null && tiers.getCodeTiers()!=null);
		vue.getLaSoldeTiers().setEnabled(tiers!=null && tiers.getCodeTiers()!=null);
	}

	public PaAffectationReglement getPaAffectationAcompte() {
		return paAffectationAcompte;
	}

	public void setPaAffectationAcompte(PaAffectationReglement paAffectationAcompte) {
		this.paAffectationAcompte = paAffectationAcompte;
	}

	public AffectationAcompteController getAffectationAcompteController() {
		return affectationAcompteController;
	}

	public void setAffectationAcompteController(
			AffectationAcompteController affectationAcompteController) {
		this.affectationAcompteController = affectationAcompteController;
	}

	/* Permet le rafraîchissement des differents composites quand on clique sur le bouton */
	private Action editonAction = new Action("Edition",Activator.getImageDescriptor("/icons/printer.png")) { 
		@Override 
		public void run() {
			EditionGrille edition = new EditionGrille(vue.getShell());
			edition.print(vue.getTableFacture());
		}
	};
} 

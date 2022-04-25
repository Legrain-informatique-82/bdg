package fr.legrain.creationDocMultiple.controllers;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

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

import fr.legrain.creationDocMultiple.divers.ParamAffichecreationDocMultiple;
import fr.legrain.creationDocMultiple.divers.ViewerSupportLocal;
import fr.legrain.creationDocMultiple.ecran.PaSelectionLigneDoc;
import fr.legrain.document.divers.ModelRecupDocumentCreationDoc;
import fr.legrain.document.divers.TypeDoc;
import fr.legrain.documents.dao.TaBonliv;
import fr.legrain.documents.dao.TaBonlivDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Document.IHMEnteteDocument;
import fr.legrain.gestCom.gestComBd.gestComBdPlugin;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideRechercheSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideRechercheControllerSWT;
import fr.legrain.gestCom.librairiesEcran.workbench.AnnuleToutEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.InfosVerifSaisie;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.data.VerrouInterface;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.lib.gui.aide.PaAideRechercheSWT;
import fr.legrain.lib.gui.aide.PaAideSWT;
import fr.legrain.lib.gui.aide.ParamAfficheAide;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.libMessageLGR.LgrMess;
import fr.legrain.tiers.ecran.ParamAfficheTiers;

public class PaSelectionLigneDocControlleur extends
		JPABaseControllerSWTStandard implements RetourEcranListener{
	static Logger logger = Logger.getLogger(PaSelectionLigneDocControlleur.class.getName());	

	private PaSelectionLigneDoc  vue = null;
	private TaBonlivDAO dao = null;
	private TaBonliv taBonLiv=null;
	private IObservableValue selectedDocument;
	private ModelRecupDocumentCreationDoc masterModelDocument = null;
//	private LgrDatabindingUtil lgrDatabindingUtil = new LgrDatabindingUtil();

	//private ModelGeneralObjet<IHMLRelance,TaLRelanceDAO,TaLRelance> modelFacture = null;
	private Object ecranAppelant = null;
	private Realm realm;
	private DataBindingContext dbc;
	private MapChangeListener changeListener = new MapChangeListener();
	private Class classModel = IHMEnteteDocument.class;
	private String nomClass = this.getClass().getSimpleName();
	private String finDeLigne = "\r\n";
	private String separateur = ";";
	protected Boolean enregistreDirect=false; 
	private Boolean SelectionTTC=null;
	
	public PaSelectionLigneDocControlleur(PaSelectionLigneDoc vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao =new TaBonlivDAO(getEm());
//		modelFacture = new ModelGeneralObjet<IHMLRelance,TaLRelanceDAO,TaLRelance>
//		(dao,IHMLRelance.class);
		setVue(vue);

		vue.getShell().addShellListener(this);

		//Branchement action annuler : empeche la fermeture automatique de la fenetre sur ESC
		vue.getShell().addTraverseListener(new Traverse());

		initController();
		initSashFormWeight();
	}

	public PaSelectionLigneDocControlleur(PaSelectionLigneDoc vue) {
		this(vue,null);
	}

	
	private void initController()	{
		try {	
			
			setDaoStandard(dao);

			initVue();			
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
		boolean result = false;
		if (getFocusCourantSWT().equals(vue.getGrille()))
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
		try {
			VerrouInterface.setVerrouille(true);
			switch (dao.getModeObjet().getMode()) {
			case C_MO_INSERTION:
				break;
			case C_MO_EDITION:
					dao.annuler(taBonLiv);
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
	protected void actEnregistrer() throws Exception {
		try {
//			ctrlTousLesChampsAvantEnregistrementSWT();
//			new LgrDozerMapper< TaBonliv,IHMEnteteBonliv>().
//				map( taBonLiv,((IHMEnteteBonliv)selectedDocument.getValue()));
//			dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
//			actRefresh();
////			initEtatBouton(true);
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	@Override
	protected void actFermer() throws Exception {
		//(controles de sortie et fermeture de la fenetre) => onClose()

	}

	@Override
	protected void actImprimer() throws Exception {

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
	public List<IHMEnteteDocument> IHMmodel() {
		LinkedList<TaBonliv> Ldocuments = new LinkedList<TaBonliv>();
		LinkedList<IHMEnteteDocument> lswt = new LinkedList<IHMEnteteDocument>();
		
		return lswt;
	}
	
	@Override
	protected void actRefresh() throws Exception {
		vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
		try{	
			SelectionTTC=null;
			String codeActuel="";
			if(taBonLiv!=null)
				codeActuel =taBonLiv.getCodeDocument();				
			WritableList writableListFacture = new WritableList(masterModelDocument.getListeObjet(), IHMEnteteDocument.class);
			getTableViewerStandard().setInput(writableListFacture);			
			if(codeActuel!="") {
				getTableViewerStandard().setSelection(new StructuredSelection(recherche(Const.C_CODE_DOCUMENT
						, codeActuel)),true);
			}
			changementDeSelection();
			initEtatBouton(true);

		}catch (Exception e) {
			logger.error("",e);
		}
		finally{
			vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
		}
	}

	@Override
	protected void actSupprimer() throws Exception {
		// TODO Auto-generated method stub

	}

	public void bind(PaSelectionLigneDoc paSelectionLigneRelance){
		try {
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());
			setTableViewerStandard( new LgrTableViewer(vue.getGrille()));
			String[] titreColonnes =new String[] {"Code document","Date","Date Livraison","Code tiers","Nom ","Net TTC","Remise ht","Escompte","Ttc","Accepté"};
			getTableViewerStandard().createTableCol(vue.getGrille(),titreColonnes,
					new String[] {"100","80","80","100","150","70","70","70","30","30"},1);
			String[] listeChamp = new String[] {"codeDocument","dateDocument","dateLivDocument","codeTiers","nomTiers","netTtcCalc","txRemHtDocument","txRemTtcDocument","ttc","accepte"};
			getTableViewerStandard().setListeChamp(listeChamp);
			getTableViewerStandard().tri(classModel, listeChamp,titreColonnes);

			ViewerSupportLocal.bind(getTableViewerStandard(), 
					new WritableList(IHMmodel(), classModel),
					BeanProperties.values(listeChamp)
					);
			getTableViewerStandard().addCheckStateListener(new ICheckStateListener() {
				@Override
				public void checkStateChanged(CheckStateChangedEvent event) {
					String message="";
					boolean saisieTtc=false;
					try {
						saisieTtc=((IHMEnteteDocument)event.getElement()).getTtc();
						getTableViewerStandard().setSelection(new StructuredSelection(recherche(Const.C_CODE_DOCUMENT
								, ((IHMEnteteDocument)event.getElement()).getCodeDocument())),true);
						if(((IHMEnteteDocument)selectedDocument.getValue()).getTxRemHtDocument().
								compareTo(BigDecimal.valueOf(0))!=0||((IHMEnteteDocument)selectedDocument.getValue()).
								getTxRemTtcDocument().compareTo(BigDecimal.valueOf(0))!=0)
							message="Ce document contient des remises globales qui ne peuvent pas" +
							" être récupérées.";
						for (Object doc : masterModelDocument.getListeObjet()) {
							if(
									//!((IHMEnteteDocument)doc).equals(((IHMEnteteDocument)selectedDocument.getValue()))&&
									((IHMEnteteDocument)doc).getAccepte()&& event.getChecked()&&((IHMEnteteDocument)doc).getTtc().compareTo(saisieTtc)!=0)
								message="Vous ne pouvez pas sélectionner à la fois des documents saisis en TTC et des documents saisis en HT.";
						}
						if(message==""){
							((IHMEnteteDocument)selectedDocument.getValue()).setAccepte(event.getChecked());
							validateUIField(Const.C_ACCEPTE,event.getChecked());
						}else {
							event.getCheckable().setChecked(((IHMEnteteDocument)selectedDocument.getValue()), false);
							MessageDialog.openWarning(vue.getShell(),"Document incompatible",message);
						}
					} catch (Exception e) {
						logger.error("", e);
					}		
				}
			});	
		
		getTableViewerStandard().setCheckStateProvider(new ICheckStateProvider() {
			
			@Override
			public boolean isGrayed(Object element) {
				// TODO Auto-generated method stub
				if(!((IHMEnteteDocument)element).getAccepte())
					return true;
				return false;
			}
			
			@Override
			public boolean isChecked(Object element) {
				// TODO Auto-generated method stub
				if(((IHMEnteteDocument)element).getAccepte())
					return true;
				return false;
			}
		});

//		affectationReglementControllerMini.bind();
		vue.getGrille().addMouseListener(
				new MouseAdapter() {

					public void mouseDoubleClick(MouseEvent e) {
						String idEditor = TypeDoc.getInstance().getEditorDoc()
								.get((((IHMEnteteDocument) selectedDocument).getTypeDocument()));
						String valeurIdentifiant = ((IHMEnteteDocument) selectedDocument
								.getValue()).getCodeDocument();
						ouvreDocument(valeurIdentifiant, idEditor);
					}

				});
		dbc = new DataBindingContext(realm);

		dbc.getValidationStatusMap().addMapChangeListener(changeListener);
		setDbcStandard(dbc);
		selectedDocument = ViewersObservables.observeSingleSelection(getTableViewerStandard());
		bindingFormMaitreDetail(dbc, realm, selectedDocument,classModel);
		selectedDocument.addChangeListener(new IChangeListener() {

			public void handleChange(ChangeEvent event) {
				changementDeSelection();
			}

		});

		changementDeSelection();
		initEtatBouton(true);
	} catch(Exception e) {
		logger.error("",e);
		vue.getLaMessage().setText(e.getMessage());
	}
}

private void changementDeSelection() {
	try {
		if(selectedDocument==null ||selectedDocument.getValue()==null)
			getTableViewerStandard().selectionGrille(0);
		if(selectedDocument!=null && selectedDocument.getValue()!=null){

		}

		initEtatBouton(true);
	} catch (Exception e) {
		logger.error("",e );
	}
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
			if(param instanceof ParamAffichecreationDocMultiple){
				setEnregistreDirect(((ParamAffichecreationDocMultiple)param).getEnregistreDirect());
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

	public void setVue(PaSelectionLigneDoc vue) {
		super.setVue(vue);
		this.vue = vue;
	}



	@Override
	protected void initEtatBouton() {
		initEtatBouton(false);
	}
	
	protected void initImageBouton() {
		super.initImageBouton();
		vue.layout(true);
	}
	
	@Override
	protected void initEtatBouton(boolean initFocus) {
		boolean trouve = true;
		switch (daoStandard.getModeObjet().getMode()) {
		case C_MO_INSERTION:
			enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,enregistreDirect);
			enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_FERMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID,false);
			if (vue.getGrille()!=null)vue.getGrille().setEnabled(false);
			break;
		case C_MO_EDITION:
			enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,enregistreDirect);
			enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_FERMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID,true);
			if (vue.getGrille()!=null)vue.getGrille().setEnabled(false);
			break;
		case C_MO_CONSULTATION:
			enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID,enregistreDirect);
			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,trouve);
			enableActionAndHandler(C_COMMAND_GLOBAL_FERMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID,false);
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
			if (nomChamp.equals(Const.C_CODE_DOCUMENT) ) {
				s=new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
			}
			if (nomChamp.equals(Const.C_CODE_TIERS) ) {
				s=new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
			}
			if (nomChamp.equals(Const.C_ACCEPTE) ) {
				s=new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");			
			}
			if (s.getSeverity() != IStatus.ERROR) {
			}
			//			// new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
			return s;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Object recherche(String propertyName, Object value) {
		boolean trouve = false;
		int i = 0;
		List<IHMEnteteDocument> model=masterModelDocument.getListeObjet();
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


	
	public Boolean getEnregistreDirect() {
		return enregistreDirect;
	}

	public void setEnregistreDirect(Boolean enregistreDirect) {
		this.enregistreDirect = enregistreDirect;
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

	public ModelRecupDocumentCreationDoc getMasterModelDocument() {
		return masterModelDocument;
	}

	public void setMasterModelDocument(
			ModelRecupDocumentCreationDoc masterModelDocument) {
		this.masterModelDocument = masterModelDocument;
	}
}

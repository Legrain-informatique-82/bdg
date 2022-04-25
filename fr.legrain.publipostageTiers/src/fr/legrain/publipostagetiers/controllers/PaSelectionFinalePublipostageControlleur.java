package fr.legrain.publipostagetiers.controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

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
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaParamPublipostage;
import fr.legrain.documents.dao.TaParamPublipostageDAO;
import fr.legrain.facture.FacturePlugin;
import fr.legrain.generationLabelEtiquette.divers.GenerationFileEtiquette;
import fr.legrain.generationLabelEtiquette.ecrans.CompositeEtiquetteArticleController;
import fr.legrain.generationLabelEtiquette.ecrans.CompositeEtiquetteTiersController;
import fr.legrain.generationLabelEtiquette.handlers.HandlerGenerationLabelsEtiquette;
import fr.legrain.generationLabelEtiquette.handlers.ParamWizardEtiquettes;
import fr.legrain.generationLabelEtiquette.wizard.HeadlessEtiquette;
import fr.legrain.generationLabelEtiquette.wizard.WizardController;
import fr.legrain.generationLabelEtiquette.wizard.WizardDialogModelLabels;
import fr.legrain.generationLabelEtiquette.wizard.WizardModelLables;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Module_Tiers.ModelTiers;
import fr.legrain.gestCom.Module_Tiers.SWTTiers;
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
import fr.legrain.gestCom.librairiesEcran.workbench.LgrFileBundleLocator;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.ISelectionLgr;
import fr.legrain.lib.data.InfosVerifSaisie;
import fr.legrain.lib.data.LibChaine;
import fr.legrain.lib.data.LibDate;
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
import fr.legrain.lib.windows.WinRegistry;
import fr.legrain.libMessageLGR.LgrMess;
import fr.legrain.publipostage.divers.ParamPublipostage;
import fr.legrain.publipostage.divers.TypeVersionPublipostage;
import fr.legrain.publipostage.gui.CompositePublipostageArticleController;
import fr.legrain.publipostage.gui.CompositePublipostageTiersController;
import fr.legrain.publipostage.gui.preferences.PreferenceConstants;
import fr.legrain.publipostage.gui.preferences.PreferenceInitializer;
import fr.legrain.publipostage.msword.PublipostageMsWord;
import fr.legrain.publipostage.openoffice.AbstractPublipostageOOo;
import fr.legrain.publipostage.openoffice.PublipostageOOoFactory;
import fr.legrain.publipostagetiers.Activator;
import fr.legrain.publipostagetiers.divers.ParamAffichePublipostageFacture;
import fr.legrain.publipostagetiers.divers.ViewerSupportLocal;
import fr.legrain.publipostagetiers.ecrans.PaSelectionLignePublipostage;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.divers.FichierDonneesAdresseTiers;
import fr.legrain.tiers.ecran.ParamAfficheTiers;
import fr.legrain.tiers.editor.TiersMultiPageEditor;

public class PaSelectionFinalePublipostageControlleur extends
		JPABaseControllerSWTStandard implements RetourEcranListener, ISelectionLgr<TaTiers>{
	static Logger logger = Logger.getLogger(PaSelectionFinalePublipostageControlleur.class.getName());	

	private PaSelectionLignePublipostage  vue = null;
	private IObservableValue selectedLignePublipostage;

	private Object ecranAppelant = null;
	private Realm realm;
	private DataBindingContext dbc;
	private MapChangeListener changeListener = new MapChangeListener();
	private Class classModel = SWTTiers.class;
	private String nomClass = this.getClass().getSimpleName();
//	private String finDeLigne = "\r\n";
//	private String separateur = ";";
	protected Boolean enregistreDirect=false; 
	private TaTiersDAO dao=null;
	private TaTiers taTiers=null;
	private ModelTiers masterModel = null;
	private TaParamPublipostage paramPubli;
	private String versionPubli=null;
	private TypeVersionPublipostage typeVersion;
	
	public static final String C_COMMAND_DOCUMENT_REINITIALISER_ID = "fr.legrain.Document.reinitialiser";
	protected HandlerReinitialiser handlerReinitialiser = new HandlerReinitialiser();

	public static final String C_COMMAND_DOCUMENT_TOUT_COCHER_ID = "fr.legrain.Publipostage.toutCocher";
	protected HandlerToutCocher handlerToutCocher = new HandlerToutCocher();
	
	public static final String C_COMMAND_DOCUMENT_TOUT_DECOCHER_ID = "fr.legrain.Publipostage.toutDeCocher";
	protected HandlerToutDeCocher handlerToutDeCocher = new HandlerToutDeCocher();
	
	private WizardModelLables wizardModelLables = null;
	
	
	private CompositePublipostageTiersController publipostageTiersController = null;
	private CompositeEtiquetteTiersController etiquetteTiersController = null;
	
	public PaSelectionFinalePublipostageControlleur(PaSelectionLignePublipostage vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao =new TaTiersDAO(getEm());
		setVue(vue);

		vue.getShell().addShellListener(this);
		
		GenerationFileEtiquette generationFileEtiquette = new GenerationFileEtiquette();
		wizardModelLables = new WizardModelLables(generationFileEtiquette);

		//Branchement action annuler : empeche la fermeture automatique de la fenetre sur ESC
		vue.getShell().addTraverseListener(new Traverse());
		typeVersion=TypeVersionPublipostage.getInstance();
		initController();
		initSashFormWeight();
	}

	public PaSelectionFinalePublipostageControlleur(PaSelectionLignePublipostage vue) {
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
		
		
		initVerifyListener(mapInfosVerifSaisie, dao);
	}
	
	private void initVue() {
		
//		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
//		
//		String[] listeParamEtiquette = wizardModelLables.listeParamEtiquette(WizardController.DOSSIER_PARAM_TIERS);
//		vue.getCbListeParamEtiquette().setItems(listeParamEtiquette);
//		
//		String dernierModeleEtiquetteUtilise = store.getString(PreferenceConstants.P_DERNIER_MODELE_UTILISE_ETIQUETTE);
//		
//		if(!dernierModeleEtiquetteUtilise.equals("")) {
//			for (int i = 0; i < listeParamEtiquette.length; i++) {
//				if(listeParamEtiquette[i].equals(dernierModeleEtiquetteUtilise)) {
//					vue.getCbListeParamEtiquette().select(i);
//				}
//			}
//		}
		
		PreferenceInitializer.initDefautProperties(false);
		publipostageTiersController = new CompositePublipostageTiersController(
				vue.getCompositePublipostage(), 
				null,//listeTiersOnglet.get(item),
				null,
				Activator.getDefault().getPreferenceStore(),
				PreferenceConstants.P_DERNIER_MODELE_UTILISE_WORD,
				PreferenceConstants.P_DERNIER_MODELE_UTILISE_OPENOFFICE, 
				new FichierDonneesAdresseTiers(),
				fr.legrain.publipostage.gui.Activator.getDefault().getPreferenceStore().getString(
						PreferenceConstants.P_DERNIER_MODELE_UTILISE_WORD),
				fr.legrain.publipostage.gui.Activator.getDefault().getPreferenceStore().getString(
								PreferenceConstants.P_DERNIER_MODELE_UTILISE_OPENOFFICE)
								);
//		remplissageEtiqTiers(item);
		etiquetteTiersController = new CompositeEtiquetteTiersController(
				vue.getCompositeEtiquette(),
				null,null,this,
				Activator.getDefault().getPreferenceStore(),
				PreferenceConstants.P_DERNIER_MODELE_UTILISE_ETIQUETTE, 
				new FichierDonneesAdresseTiers()
		);
		
	}
	
	@Override
	protected void actAide() throws Exception {
		// TODO Auto-generated method stub
		actAide(null);
	}

	@Override
	protected boolean aideDisponible() {
		boolean result = false;
		if (getFocusCourantSWT().equals(vue.getCompositePublipostage().getTfLETTRE()))
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
//					if (getFocusCourantSWT().equals(vue.getTfTYPE_RELANCE())) {
//						//permet de paramètrer l'affichage remplie ou non de l'aide
//
//						PaTypeRelanceVersion paEcran = new PaTypeRelanceVersion(s2, SWT.NULL);
//						PaTypeRelanceController paController = new PaTypeRelanceController(paEcran);
//
//						editorCreationId = EditorTypeRelance.ID;
//						editorInputCreation = new EditorInputTypeRelance();
//
//						ParamAfficheTRelance paramAffiche = new ParamAfficheTRelance();
//						paramAfficheAideRecherche.setJPQLQuery(new TaTRelanceDAO(getEm()).getJPQLQuery());
//						paramAffiche.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//						paramAffiche.setEcranAppelant(paAideController);
//						controllerEcranCreation = paController;
//						parametreEcranCreation = paramAffiche;
//
//						paramAfficheAideRecherche.setTypeEntite(TaTRelance.class);
//						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_T_RELANCE);
//						paramAfficheAideRecherche.setDebutRecherche(vue.getTfTYPE_RELANCE().getText());
//						paramAfficheAideRecherche.setControllerAppelant(this);
//						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTRelance,TaTRelanceDAO,TaTRelance>(SWTTRelance.class,getEm()));
//						paramAfficheAideRecherche.setTypeObjet(SWTTRelance.class);
//						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_T_RELANCE);
//					}


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
		masterModel.getListeEntity().clear();
		masterModel.getListeObjet().clear();
		actRefresh();
	}

	@Override
	protected void actEnregistrer() throws Exception {
		//prendre tout ce qu'il y a dans la grille et l'envoyer dans l'autre grille
	}

	@Override
	protected void actFermer() throws Exception {
		List<SWTTiers> listeTemp=new LinkedList<SWTTiers>();
		//(controles de sortie et fermeture de la fenetre) => onClose()
		for (SWTTiers tiers : masterModel.getListeObjet()) {
			if(!tiers.getAccepte())listeTemp.add(tiers);				
		}
		masterModel.getListeObjet().removeAll(listeTemp);
		actRefresh();
	}

	@Override
	protected void actImprimer() throws Exception {
		try {
			List<TaTiers> listeFinale = new ArrayList<TaTiers>();
			for (SWTTiers tiers : masterModel.getListeObjet()) {
				if(tiers.getAccepte()){
					listeFinale.add(dao.findById(tiers.getIdTiers()));
				}
			}
			publipostageTiersController.publipostage(listeFinale);
//			final String cheminModele=new File(vue.getCompositePublipostage().getTfLETTRE().getText()).getPath();
//			Thread t = new Thread() {
//				@Override
//				public void run() {
//					TaParamPublipostage param =new TaParamPublipostageDAO(getEm()).findInstance();
//					List<TaTiers> listeFinale = new ArrayList<TaTiers>();
//					for (SWTTiers tiers : masterModel.getListeObjet()) {
//						if(tiers.getAccepte()){
//							listeFinale.add(dao.findById(tiers.getIdTiers()));
//						}
//					}
//					
//					String repert= new File(Platform.getInstanceLocation().getURL().getFile()).getPath();
//					String extensionFinale=TypeVersionPublipostage.getInstance().getExtensionFinale().get(param.getLogicielPublipostage());
//					List<ParamPublipostage> listeParamPubli =creationFichierPublipostages(listeFinale,repert,extensionFinale,cheminModele);
//
//					TypeVersionPublipostage typeVersion=TypeVersionPublipostage.getInstance();
//					String nonFichierFinal="publiFusion-"+LibDate.dateToString(new Date(),"")+extensionFinale;
//
//					if(typeVersion.getType().get(param.getLogicielPublipostage()).
//							equals(TypeVersionPublipostage.TYPE_OPENOFFICE)){
//
//
//						//PublipostageOOoWin32 pub = new PublipostageOOoWin32(listeParamPubli);
//						AbstractPublipostageOOo pub = PublipostageOOoFactory.createPublipostageOOo(listeParamPubli);
//						String pathOpenOffice = "";
//						try {
//							if(Platform.getOS().equals(Platform.OS_WIN32)){
//								pathOpenOffice = WinRegistry.readString(
//										WinRegistry.HKEY_LOCAL_MACHINE,
//										WinRegistry.KEY_REGISTR_WIN_OPENOFFICE,
//								"");
//							} else if(Platform.getOS().equals(Platform.OS_LINUX)){
//								pathOpenOffice = "/usr/bin/soffice";
//							} else if(Platform.getOS().equals(Platform.OS_MACOSX)) {}
//						}
//						catch (Exception e3) {
//							logger.error("",e3);
//						}    
//
//						pub.setCheminOpenOffice(new File(pathOpenOffice).getPath());
//						pub.setPortOpenOffice("8100");
//						pub.setNomFichierFinalFusionne(new File(nonFichierFinal).getPath());
//						pub.demarrerServeur();
//						pub.publipostages();
//
//					}else if(typeVersion.getType().get(param.getLogicielPublipostage()).
//							equals(TypeVersionPublipostage.TYPE_MSWORD)){
//						PublipostageMsWord pub = new PublipostageMsWord(listeParamPubli);
//						pub.setNomFichierFinalFusionne(new File(nonFichierFinal).getPath());
//						pub.publipostages();
//					}
//
//				}//fin run
//			}; //fin thread
//			t.start();
//
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	@Override
	protected void actInserer() throws Exception {
		// TODO Auto-generated method stub
		for (SWTTiers tiers : masterModel.getListeObjet()) {
			tiers.setAccepte(!tiers.getAccepte());
		}
	}

	@Override
	protected void actModifier() throws Exception {

	}

	/**
	 * Creation des objet pour l'interface, a partir de l'entite principale.<br>
	 * Ici : creation d'une liste de ligne pour l'IHM, a partir de la liste des lignes contenue dans l'entite facture.
	 * @return
	 */
	public List<SWTTiers> IHMmodel() {
		LinkedList<TaTiers> ldao = new LinkedList<TaTiers>();
		LinkedList<SWTTiers> lswt = new LinkedList<SWTTiers>();
		return lswt;
	}
	
	@Override
	protected void actRefresh() throws Exception {
		vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
		try{	
			String codeActuel="";
			if(taTiers!=null)
				codeActuel =taTiers.getCodeTiers();				
			WritableList writableListFacture = new WritableList(masterModel.getListeObjet(), SWTTiers.class);
			getTableViewerStandard().setInput(writableListFacture);			
			if(codeActuel!="") {
				getTableViewerStandard().setSelection(new StructuredSelection(recherche(Const.C_CODE_TIERS
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

	public void bind(PaSelectionLignePublipostage paSelectionLignePublipostage){
		try {
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());
			setTableViewerStandard( new LgrTableViewer(vue.getGrille()));
			String[] titreColonnes =new String[] {"code Tiers","nom","prenom","nom Entreprise","code postal","ville","accepte"};
			getTableViewerStandard().createTableCol(vue.getGrille(),titreColonnes,
					new String[] {"80","150","80","150","80","100","30"},1);
			String[] listeChamp = new String[] {"codeTiers","nomTiers","prenomTiers","nomEntreprise","codepostalAdresse","villeAdresse","accepte"};
			getTableViewerStandard().setListeChamp(listeChamp);
			getTableViewerStandard().tri(classModel, listeChamp,titreColonnes);
			

			ViewerSupportLocal.bind(getTableViewerStandard(), 
					new WritableList(IHMmodel(), classModel),
					BeanProperties.values(listeChamp)
					);
			getTableViewerStandard().addCheckStateListener(new ICheckStateListener() {
				@Override
				public void checkStateChanged(CheckStateChangedEvent event) {
					try {
						getTableViewerStandard().setSelection(new StructuredSelection(recherche(Const.C_CODE_TIERS
								, ((SWTTiers)event.getElement()).getCodeTiers())),true);
						if(selectedLignePublipostage.getValue()!=null){
							//actModifier();
							((SWTTiers)selectedLignePublipostage.getValue()).setAccepte(event.getChecked());
							validateUIField(Const.C_ACCEPTE,event.getChecked());
							//actEnregistrer();
						}
					} catch (Exception e) {
						logger.error("", e);
					}		
				}
			});	
		
		getTableViewerStandard().setCheckStateProvider(new ICheckStateProvider() {
			
			@Override
			public boolean isGrayed(Object element) {
//				// TODO Auto-generated method stub
				if(!((SWTTiers)element).getAccepte())
					return true;
				return false;
			}
			
			@Override
			public boolean isChecked(Object element) {
				// TODO Auto-generated method stub
				if(((SWTTiers)element).getAccepte())
					return true;
				return false;
			}
		});

//		affectationReglementControllerMini.bind();
		vue.getGrille().addMouseListener(
				new MouseAdapter() {

					public void mouseDoubleClick(MouseEvent e) {
						String idEditor = TiersMultiPageEditor.ID_EDITOR;
						String valeurIdentifiant = ((SWTTiers) selectedLignePublipostage
								.getValue()).getCodeTiers();
						ouvreDocument(valeurIdentifiant, idEditor);
					}

				});
		dbc = new DataBindingContext(realm);

		dbc.getValidationStatusMap().addMapChangeListener(changeListener);
		setDbcStandard(dbc);
		selectedLignePublipostage = ViewersObservables.observeSingleSelection(getTableViewerStandard());
		bindingFormMaitreDetail(dbc, realm, selectedLignePublipostage,classModel);
		selectedLignePublipostage.addChangeListener(new IChangeListener() {

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
		if(selectedLignePublipostage==null ||selectedLignePublipostage.getValue()==null)
			getTableViewerStandard().selectionGrille(0);
		if(selectedLignePublipostage!=null && selectedLignePublipostage.getValue()!=null){

		}

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
		mapCommand.put(C_COMMAND_GLOBAL_INSERER_ID, handlerInserer);// Inverser
		mapCommand.put(C_COMMAND_GLOBAL_ENREGISTRER_ID, handlerEnregistrer);//envoyer dans l'autre grille
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

		vue.getPaFomulaire().setVisible(true);
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

//		//vue.getComboChoixParamEtiquette().setVisible(true);
//		vue.getLaETTIQUETTE().setVisible(true);
//		//vue.getTfETIQUETTE().setVisible(false);
//		
//		
//		vue.getBtnChemin_Model().addMouseListener(new MouseAdapter() {
//
//			@Override
//			public void mouseDown(MouseEvent e) {
//				// TODO Auto-generated method stub
//				super.mouseDown(e);
//				
//				FileDialog dd = new FileDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
//							//paramPubli.getExtension()
//				dd.setFilterExtensions(new String[] {paramPubli.getExtension()});
//				dd.setFilterNames(new String[] {"Modèle de lettre"});
//				dd.setFileName(vue.getTfLETTRE().getText());
//				String repDestination = dd.getFileName(); 
//				if(repDestination.equals(""))repDestination=Platform.getInstanceLocation().getURL().getFile();
//				dd.setFilterPath(LibChaine.pathCorrect(repDestination));
//				String choix = dd.open();
//				System.out.println(choix);
//				if(choix!=null){
//					vue.getTfLETTRE().setText(choix);
//				}
//				vue.getTfLETTRE().forceFocus();
//			}
//			
//		});
//		vue.getBtnOuvrir_Model().addMouseListener(new MouseAdapter() {
//
//			@Override
//			public void mouseDown(MouseEvent e) {
//				super.mouseDown(e);
//				if(typeVersion.getType().get(paramPubli.getLogicielPublipostage()).
//						equals(TypeVersionPublipostage.TYPE_OPENOFFICE)){
//
//					//PublipostageOOoWin32 pub = new PublipostageOOoWin32(listeParamPubli);
//					AbstractPublipostageOOo pub = PublipostageOOoFactory.createPublipostageOOo(null);
//					String pathOpenOffice = "";
//					try {
//						if(Platform.getOS().equals(Platform.OS_WIN32)){
//							pathOpenOffice = WinRegistry.readString(
//									WinRegistry.HKEY_LOCAL_MACHINE,
//									WinRegistry.KEY_REGISTR_WIN_OPENOFFICE,
//							"");
//						} else if(Platform.getOS().equals(Platform.OS_LINUX)){
//							pathOpenOffice = "/usr/bin/soffice";
//						} else if(Platform.getOS().equals(Platform.OS_MACOSX)) {}
//					}
//					catch (Exception e3) {
//						logger.error("",e3);
//					}    
//
//					pub.setCheminOpenOffice(new File(pathOpenOffice).getPath());
//					pub.setPortOpenOffice("8100");
//					pub.setNomFichierFinalFusionne(new File(vue.getTfLETTRE().getText()).getPath());
//					pub.demarrerServeur();
//					pub.OuvreDocument("8100",new File(vue.getTfLETTRE().getText()).getPath());
//
//				}else if(typeVersion.getType().get(paramPubli.getLogicielPublipostage()).
//						equals(TypeVersionPublipostage.TYPE_MSWORD)){
//					PublipostageMsWord pub = new PublipostageMsWord(null);
//					//pub.setNomFichierFinalFusionne(new File(vue.getTfLETTRE().getText()).getPath());
//					pub.OuvreDocument(new File(vue.getTfLETTRE().getText()).getPath());
//				}
//				
//			}
//			
//		});
//		
//		
//		vue.getBtnImprimerEtiquette().addMouseListener(new MouseAdapter() {			
//			@Override
//			public void mouseDown(MouseEvent e) {
//				super.mouseDown(e);
//				try {
//					
//					List<TaTiers> listeFinale = new ArrayList<TaTiers>();
//					for (SWTTiers tiers : masterModel.getListeObjet()) {
//						if(tiers.getAccepte()){
//							listeFinale.add(dao.findById(tiers.getIdTiers()));
//						}
//					}
//					
//					String repert= new File(Platform.getInstanceLocation().getURL().getFile()).getPath();
//					String cheminFichierDonnees = new File(Const.C_CHEMIN_REP_TMP_COMPLET+"/Etiquettes"+"-"+LibDate.dateToString(new Date(),"")+".txt").getPath();
//					String modele = vue.getCbListeParamEtiquette().getText();
//
//					//String cheminFichierMotCle = new File(Const.pathRepertoireSpecifiques("GenerationLabelEtiquette", "modelEtiquette")+"/motcle.properties").getPath();
//					String cheminFichierMotCle = LgrFileBundleLocator.bundleToFile(generationlabeletiquette.Activator.getDefault().getBundle(), "/modelEtiquette/motcle.properties").getPath();
//					FichierDonneesAdresseTiers donneesTiers = new FichierDonneesAdresseTiers();
//					donneesTiers.creationFichierDonneesAdresse(listeFinale, repert, cheminFichierDonnees);
//					
//					IPreferenceStore store = Activator.getDefault().getPreferenceStore();
//					store.setValue(PreferenceConstants.P_DERNIER_MODELE_UTILISE_ETIQUETTE, vue.getCbListeParamEtiquette().getText());
//					
//					ParamWizardEtiquettes p = null;
//					p = new ParamWizardEtiquettes();
//					
//					if(!modele.equals(HeadlessEtiquette.CHOIX_DEFAUT_CCOMB_PARAM_ETIQUETTE)) {
//						p.setChangeStartingPage(true);
//					}
//					
////					HeadlessEtiquette headlessEtiquette = new HeadlessEtiquette();
////					headlessEtiquette.lectureParam(vue.getCbListeParamEtiquette().getText());
////					headlessEtiquette.getParameterEtiquette().setPathFileExtraction(cheminFichierDonnees);
////					headlessEtiquette.getParameterEtiquette().setPathFileMotCle(cheminFichierMotCle);
//////					getParameterEtiquette().setPathFileMotCle(null);
////					headlessEtiquette.print();
//					
//					p.setModelePredefini(modele);
//					p.setModeIntegre(true);
//					p.setCheminFichierDonnees(cheminFichierDonnees);
//					p.setCheminFichierMotsCle(cheminFichierMotCle);
//					p.setSeparateur(";");
//
//					//GenerationFileEtiquette generationFileEtiquette = new GenerationFileEtiquette();
//					//WizardModelLables wizardModelLables = new WizardModelLables(generationFileEtiquette,p);
//					WizardModelLables wizardModelLables = new WizardModelLables(new GenerationFileEtiquette());
//					wizardModelLables.initParam(p);
//					WizardDialogModelLabels wizardDialogModelLabels = new WizardDialogModelLabels
//					(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),wizardModelLables);
//					wizardDialogModelLabels.open();
//					
//				} catch (Exception e1) {
//					logger.error("", e1);
//				}
//			}			
//		});
//		
//		vue.getBtnModifierEtiquette().addMouseListener(new MouseAdapter() {			
//			@Override
//			public void mouseDown(MouseEvent e) {
//				super.mouseDown(e);
//				try {
//
//					List<TaTiers> listeFinale = new ArrayList<TaTiers>();
//					for (SWTTiers tiers : masterModel.getListeObjet()) {
//						if(tiers.getAccepte()){
//							listeFinale.add(dao.findById(tiers.getIdTiers()));
//						}
//					}
//					String repert= new File(Platform.getInstanceLocation().getURL().getFile()).getPath();
//					String cheminFichierDonnees = new File(Const.C_CHEMIN_REP_TMP_COMPLET+"/Etiquettes"+"-"+LibDate.dateToString(new Date(),"")+".txt").getPath();
//					String modele = vue.getCbListeParamEtiquette().getText();
//
//					//String cheminFichierMotCle = new File(Const.pathRepertoireSpecifiques("GenerationLabelEtiquette", "modelEtiquette")+"/motcle.properties").getPath();
//					String cheminFichierMotCle = LgrFileBundleLocator.bundleToFile(generationlabeletiquette.Activator.getDefault().getBundle(), "/modelEtiquette/motcle.properties").getPath();
//					FichierDonneesAdresseTiers donneesTiers = new FichierDonneesAdresseTiers();
//					donneesTiers.creationFichierDonneesAdresse(listeFinale, repert, cheminFichierDonnees);
////					creationFichierDonnees(listeFinale,repert,cheminFichierDonnees);
//
//					ParamWizardEtiquettes p = null;
//					p = new ParamWizardEtiquettes();
//					p.setModelePredefini(modele);
//					p.setModeIntegre(true);
//					p.setCheminFichierDonnees(cheminFichierDonnees);
//					p.setCheminFichierMotsCle(cheminFichierMotCle);
//					p.setSeparateur(";");
//
//					IPreferenceStore store = Activator.getDefault().getPreferenceStore();
//					store.setValue(PreferenceConstants.P_DERNIER_MODELE_UTILISE_ETIQUETTE, modele);
//
//					//GenerationFileEtiquette generationFileEtiquette = new GenerationFileEtiquette();
//					//WizardModelLables wizardModelLables = new WizardModelLables(generationFileEtiquette,p);
//					wizardModelLables.initParam(p);
//					WizardDialogModelLabels wizardDialogModelLabels = new WizardDialogModelLabels
//					(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),wizardModelLables);
//					wizardDialogModelLabels.open();
//				} catch (Exception e1) {
//					logger.error("", e1);
//				}
//			}			
//		});

		activeModifytListener();

		versionPubli="";
		int rangVersion=0;
		paramPubli=new TaParamPublipostageDAO(getEm()).findInstance();
		if(paramPubli!=null && paramPubli.getLogicielPublipostage()!=null )
			versionPubli=paramPubli.getLogicielPublipostage();
		String[] liste= new String[typeVersion.getTypeVersionExistantes().size()];
		int i = 0;
		for (String libelle : typeVersion.getTypeVersionExistantes().values()) {
			liste[i]=libelle;
			if(versionPubli.equals(libelle))
				rangVersion=i;
			i++;
		}
//		vue.getCbListeVersion().setItems(liste);
//		vue.getCbListeVersion().select(rangVersion);
//		PreferenceInitializer.initDefautProperties(false);
//		if(typeVersion.getType().get(vue.getCbListeVersion().getItem(vue.getCbListeVersion().getSelectionIndex())).equals(TypeVersionPublipostage.TYPE_MSWORD))
//			vue.getTfLETTRE().setText(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_DERNIER_MODELE_UTILISE_WORD));
//		if(typeVersion.getType().get(vue.getCbListeVersion().getItem(vue.getCbListeVersion().getSelectionIndex())).equals(TypeVersionPublipostage.TYPE_OPENOFFICE))
//			vue.getTfLETTRE().setText(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_DERNIER_MODELE_UTILISE_OPENOFFICE));
//
//
//		vue.getCbListeVersion().addSelectionListener(new SelectionAdapter() {
//
//			@Override
//			public void widgetSelected(SelectionEvent e) {
////				// TODO Auto-generated method stub
////				super.widgetSelected(e);
//				String versionNew=vue.getCbListeVersion().getItem(vue.getCbListeVersion().getSelectionIndex());
//				if(!versionNew.equals(versionPubli)){
////					if(MessageDialog.openQuestion(vue.getShell(),"Changement de version", 
////							"Attention, si vous changez de version de logiciel, tous les chemins de vos types " +
////							"de relances seront réinitialisés. Vous devrez ensuite les renseigner en fonction de la version " +
//							//"du logiciel de publipostage choisie !!!"))
//							//{
//						//Supprimer tous les chemins de tous les types relances
//						//avec un message pour prévenir
//						TaParamPublipostageDAO daoParam =new TaParamPublipostageDAO(getEm());
//						if(paramPubli==null)paramPubli=daoParam.findInstance();
//						paramPubli.setLogicielPublipostage(versionNew);
//						paramPubli.setExtension(typeVersion.getExtension().get(versionNew));
//						paramPubli.setId(1);
//					
//						try {
//							daoParam.begin(daoParam.getEntityManager().getTransaction());
//							paramPubli=daoParam.merge(paramPubli);
//							daoParam.commit(daoParam.getEntityManager().getTransaction());
//							versionPubli=paramPubli.getLogicielPublipostage();
//							//vue.getTfETIQUETTE().setText("");
//							vue.getTfLETTRE().setText("");
//							if(typeVersion.getType().get(vue.getCbListeVersion().getItem(vue.getCbListeVersion().getSelectionIndex())).equals(TypeVersionPublipostage.TYPE_MSWORD))
//								vue.getTfLETTRE().setText(Activator.getDefault().getPreferenceStore().getDefaultString(PreferenceConstants.P_DERNIER_MODELE_UTILISE_WORD));
//							if(typeVersion.getType().get(vue.getCbListeVersion().getItem(vue.getCbListeVersion().getSelectionIndex())).equals(TypeVersionPublipostage.TYPE_OPENOFFICE))
//								vue.getTfLETTRE().setText(Activator.getDefault().getPreferenceStore().getDefaultString(PreferenceConstants.P_DERNIER_MODELE_UTILISE_OPENOFFICE));
//							
//						} catch (Exception e1) {
//							logger.error("",e1);
//						}
////					}else{
////						int rang =vue.getCbListeVersion().indexOf(versionPubli);
////						if(rang==-1)rang=0;
////						vue.getCbListeVersion().select(rang);
////					}
//						
//				}					
//			}
//			
//		});
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

			if(param.getTitreFormulaire()!=null){
				vue.getLaTitreFormulaire().setText(param.getTitreFormulaire());
			} else {
				vue.getLaTitreFormulaire().setText(ParamAfficheTiers.C_TITRE_FORMULAIRE);
			}

			if(param.getTitreGrille()!=null){
				vue.getLaTitreGrille().setText(param.getTitreGrille());
			} else {
				vue.getLaTitreGrille().setText(ParamAfficheTiers.C_TITRE_GRILLE);
			}


			if(param.getEcranAppelant()!=null) {
				ecranAppelant = param.getEcranAppelant();
			}
			if(param instanceof ParamAffichePublipostageFacture){
				setEnregistreDirect(((ParamAffichePublipostageFacture)param).getEnregistreDirect());
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

	public void setVue(PaSelectionLignePublipostage vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	public ModelTiers getMasterModel() {
		return masterModel;
	}

	public void setMasterModel(ModelTiers masterModel) {
		this.masterModel = masterModel;
	}

	@Override
	protected void initEtatBouton() {
		initEtatBouton(false);
	}
	
	protected void initImageBouton() {
		super.initImageBouton();
		vue.getBtnAnnuler().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ANNULER));
		vue.getBtnEnregister().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ENREGISTRER));
		vue.getBtnFermer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_FERMER));
		vue.getBtnImprimer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_IMPRIMER));
		vue.getBtnInserer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_REINITIALISER));
		vue.getBtnModifier().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_MODIFIER));
		vue.getBtnSupprimer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_SUPPRIMER));
		
//		vue.getBtnModifierEtiquette().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_MODIFIER));
//		vue.getBtnModifierEtiquette().setText("");
//		vue.getBtnImprimerEtiquette().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_IMPRIMER));
//		vue.getBtnImprimerEtiquette().setText("");
		
		vue.getBtnFermer().setText("Supprimer les non cochés");
		vue.getBtnAnnuler().setText("Réinitialiser");
		
		vue.layout(true);
	}
	
	@Override
	protected void initEtatBouton(boolean initFocus) {
		boolean trouve = true;
		switch (daoStandard.getModeObjet().getMode()) {
		case C_MO_INSERTION:
//			enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,false);
//			enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID,false);
//			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,false);
//			enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,enregistreDirect);
//			enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,false);
//			enableActionAndHandler(C_COMMAND_GLOBAL_FERMER_ID,false);
//			enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID,false);
//			enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,true);
//			enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID,false);
//			if (vue.getGrille()!=null)vue.getGrille().setEnabled(false);
			break;
		case C_MO_EDITION:
//			enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,false);
//			enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID,false);
//			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,enregistreDirect);
//			enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,true);
//			enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,false);
//			enableActionAndHandler(C_COMMAND_GLOBAL_FERMER_ID,false);
//			enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID,false);
//			enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,true);
//			enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID,true);
//			if (vue.getGrille()!=null)vue.getGrille().setEnabled(false);
			break;
		case C_MO_CONSULTATION:
			enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_FERMER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID,true);
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

//	public class TaLRelanceComparator implements Comparator<TaLRelance> {
//		 
//	    public int compare(TaLRelance taLRelance1, TaLRelance taLRelance2) {
//	        int powerCompare = taLRelance1.getTaTRelance().getOrdreTRelance().
//	        compareTo(taLRelance2.getTaTRelance().getOrdreTRelance());
////	        int thougnessCompare = card1.getThougness().compareTo(card2.getThougness());
////	        int nameCompare = card1.getName().compareTo(card2.getName());
////	        int rarityCompare = card1.getRarity().getOrder().compareTo(card2.getRarity().getOrder());
//	 
//	        int compared = powerCompare;
////	        if (compared == 0) {
////	            compared = thougnessCompare;
////	            if (compared == 0) {
////	                compared = rarityCompare;
////	                if (compared == 0) {
////	                    compared = nameCompare;
////	                }
////	            }
////	        }
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
		String validationContext = "RELANCE";
		try {
			IStatus s = null;
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
		List<SWTTiers> model=masterModel.getListeObjet();
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
		int premierComposite = 40;
		int secondComposite = 60;
		vue.getCompositeForm().setWeights(new int[]{premierComposite,secondComposite});
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
	
	
	

	
	private class HandlerReinitialiser extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				masterModel.getListeObjet().clear();
				masterModel.getListeEntity().clear();
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
				for (SWTTiers tiers : masterModel.getListeObjet()) {
					tiers.setAccepte(true);
				}
			} catch (Exception e) {
				logger.error("", e);
			}
			return event;
		}
	}

	private class HandlerToutDeCocher extends LgrAbstractHandler {
	
		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				for (SWTTiers tiers : masterModel.getListeObjet()) {
					tiers.setAccepte(false);
				}
			} catch (Exception e) {
				logger.error("", e);
			}
			return event;
		}
	}

	public List<ParamPublipostage> creationFichierPublipostages(List<TaTiers>liste,String repertoireModele,String extensionFichierFinal,String fileModele) {
		TaFactureDAO daoFacture=new TaFactureDAO(getEm());
		TaFacture facture =null;
		List<ParamPublipostage> listePubli=new LinkedList<ParamPublipostage>();
		if(liste!=null && liste.size()>0){
//			FileWriter fw = null;
//			BufferedWriter bw = null;
			String codeRelance="";
			String cheminFichier="";
			String cheminFichierFinal="";
//			try {
//				if(bw!=null) bw.close();
//				if(fw!=null) fw.close();
				cheminFichier=new File(repertoireModele+"/Publipostage"+"-"+LibDate.dateToString(new Date(),"")+".txt").getPath();
				cheminFichierFinal=new File(repertoireModele+"/Publipostage"+"-"+LibDate.dateToString(new Date(),"")+extensionFichierFinal).getPath();
//				fw = new FileWriter(cheminFichier);
				ParamPublipostage param = new ParamPublipostage();
				param.setCheminFichierDonnees(cheminFichier);

					param.setCheminFichierModelLettre(fileModele);
					
					param.setCheminFichierMotCle(new File(Const.pathRepertoireSpecifiques("fr.legrain.publipostageTiers", "Modeles")+"/motcle.properties").getPath());
					//param.setCheminFichierMotCle(new File("/Modeles/motcle.properties").getPath());
				
				
				param.setCheminFichierFinal(cheminFichierFinal);
				param.setCheminRepertoireFinal(repertoireModele);
				listePubli.add(param);
				
				FichierDonneesAdresseTiers donneesTiers = new FichierDonneesAdresseTiers();
				donneesTiers.creationFichierDonneesAdresse(liste, repertoireModele, cheminFichier,false);
//				creationFichierDonnees(liste,repertoireModele,cheminFichier);
				
//				bw = new BufferedWriter(fw);
//				fw.write("codeTiers;nomTiers;adresse1;adresse2;adresse3;codepostal;ville;pays" +
//						";codeTCivilite;codeTEntite;nomEntreprise;prenomTiers" 
//						//+";solde_total_pts;solde_pts_2010"
//						+separateur+finDeLigne);
//
//				for (TaTiers tiers : liste) {
//					if(tiers.getAccepte()){
//						String ligne="";
//						
//					ligne=tiers.getCodeTiers()+separateur;
//					if(tiers.getNomTiers()!=null)ligne+=tiers.getNomTiers()+separateur;else ligne+=separateur;
//					if(tiers.getTaAdresse()!=null)ligne+=tiers.getTaAdresse().getAdresse1Adresse()+separateur;else ligne+=separateur;
//					if(tiers.getTaAdresse()!=null)ligne+=tiers.getTaAdresse().getAdresse2Adresse()+separateur;else ligne+=separateur;
//					if(tiers.getTaAdresse()!=null)ligne+=tiers.getTaAdresse().getAdresse3Adresse()+separateur;else ligne+=separateur;
//					if(tiers.getTaAdresse()!=null)ligne+=tiers.getTaAdresse().getCodepostalAdresse()+separateur;else ligne+=separateur;
//					if(tiers.getTaAdresse()!=null)ligne+=tiers.getTaAdresse().getVilleAdresse()+separateur;else ligne+=separateur;
//					if(tiers.getTaAdresse()!=null)ligne+=tiers.getTaAdresse().getPaysAdresse()+separateur;else ligne+=separateur;
//					if(tiers.getTaTCivilite()!=null)
//						ligne+=tiers.getTaTCivilite().getCodeTCivilite()+separateur;else ligne+=separateur;
//					if(tiers.getTaTEntite()!=null)
//						ligne+=tiers.getTaTEntite().getCodeTEntite()+separateur;else ligne+=separateur;
//					if(tiers.getTaEntreprise()!=null)
//						ligne+=tiers.getTaEntreprise().getNomEntreprise()+separateur;else ligne+=separateur;
//					ligne+=tiers.getPrenomTiers()+separateur;
////					ligne+="200"+separateur;
////					ligne+="200"+separateur;
//					fw.write(ligne);
//					fw.write(finDeLigne);
//				}
//				}
//
//				if(bw!=null) bw.close();
//				if(fw!=null) fw.close();
//			} catch(IOException ioe){
//				logger.error("",ioe);
//			} 
//			finally{
//				try {
//					if(bw!=null) bw.close();
//					if(fw!=null) fw.close();
//
//				} catch (Exception e) {}
//			}
		}
		return listePubli;

	}

	@Override
	public List<TaTiers> getSelection() {
		List<TaTiers> listeFinale = new ArrayList<TaTiers>();
		for (SWTTiers tiers : masterModel.getListeObjet()) {
			if(tiers.getAccepte()){
				listeFinale.add(dao.findById(tiers.getIdTiers()));
			}
		}
		return listeFinale;
	}
	
//	public void creationFichierDonnees(List<TaTiers>liste, String repertoireModele, String nomFichier) {
//		if(liste!=null && liste.size()>0){
//			FileWriter fw = null;
//			BufferedWriter bw = null;
//			String cheminFichier="";
//			try {
//				if(bw!=null) bw.close();
//				if(fw!=null) fw.close();
//				//cheminFichier=new File(repertoireModele+"/Etiquettes"+"-"+LibDate.dateToString(new Date(),"")+".txt").getPath();
//				cheminFichier=nomFichier;
//				fw = new FileWriter(cheminFichier);
//
//
//				//param.setCheminFichierMotCle(new File(Const.pathRepertoireSpecifiques("fr.legrain.publipostageTiers", "Modeles")+"/motcle.properties").getPath());
//
//
//				bw = new BufferedWriter(fw);
//				fw.write("codeTiers;nomTiers;adresse1;adresse2;adresse3;codepostal;ville;pays" +
//						";codeTCivilite;codeTEntite;nomEntreprise;prenomTiers" 
//						//+";solde_total_pts;solde_pts_2010"
//						+separateur+finDeLigne);
//
//				for (TaTiers tiers : liste) {
//					if(tiers.getAccepte()){
//						String ligne="";
//
//						ligne=tiers.getCodeTiers()+separateur;
//						if(tiers.getNomTiers()!=null)ligne+=tiers.getNomTiers()+separateur;else ligne+=separateur;
//						if(tiers.getTaAdresse()!=null)ligne+=tiers.getTaAdresse().getAdresse1Adresse()+separateur;else ligne+=separateur;
//						if(tiers.getTaAdresse()!=null)ligne+=tiers.getTaAdresse().getAdresse2Adresse()+separateur;else ligne+=separateur;
//						if(tiers.getTaAdresse()!=null)ligne+=tiers.getTaAdresse().getAdresse3Adresse()+separateur;else ligne+=separateur;
//						if(tiers.getTaAdresse()!=null)ligne+=tiers.getTaAdresse().getCodepostalAdresse()+separateur;else ligne+=separateur;
//						if(tiers.getTaAdresse()!=null)ligne+=tiers.getTaAdresse().getVilleAdresse()+separateur;else ligne+=separateur;
//						if(tiers.getTaAdresse()!=null)ligne+=tiers.getTaAdresse().getPaysAdresse()+separateur;else ligne+=separateur;
//						if(tiers.getTaTCivilite()!=null)
//							ligne+=tiers.getTaTCivilite().getCodeTCivilite()+separateur;else ligne+=separateur;
//						if(tiers.getTaTEntite()!=null)
//							ligne+=tiers.getTaTEntite().getCodeTEntite()+separateur;else ligne+=separateur;
//						if(tiers.getTaEntreprise()!=null)
//							ligne+=tiers.getTaEntreprise().getNomEntreprise()+separateur;else ligne+=separateur;
//						ligne+=tiers.getPrenomTiers()+separateur;
//						//					ligne+="200"+separateur;
//						//					ligne+="200"+separateur;
//						fw.write(ligne);
//						fw.write(finDeLigne);
//					}
//				}
//
//				if(bw!=null) bw.close();
//				if(fw!=null) fw.close();
//			} catch(IOException ioe){
//				logger.error("",ioe);
//			} 
//			finally{
//				try {
//					if(bw!=null) bw.close();
//					if(fw!=null) fw.close();
//
//				} catch (Exception e) {
//					
//				}
//			}
//		}
//	}

}

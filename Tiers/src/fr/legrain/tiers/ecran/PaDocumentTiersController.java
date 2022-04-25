package fr.legrain.tiers.ecran;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

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
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.documents.dao.TaParamPublipostage;
import fr.legrain.documents.dao.TaParamPublipostageDAO;
import fr.legrain.documents.dao.TaTRelance;
import fr.legrain.documents.dao.TaTRelanceDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Articles.SWTArticle;
import fr.legrain.gestCom.Module_Tiers.SWTDocumentTiers;
import fr.legrain.gestCom.Module_Tiers.SWTTRelance;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideRechercheSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideRechercheControllerSWT;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDeSelectionEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrFileBundleLocator;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.InfosVerifSaisie;
import fr.legrain.lib.data.LibChaine;
import fr.legrain.lib.data.LibConversion;
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
import fr.legrain.lib.gui.aide.ResultAide;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.lib.windows.WinRegistry;
import fr.legrain.libMessageLGR.LgrMess;
import fr.legrain.publipostage.divers.TypeVersionPublipostage;
import fr.legrain.publipostage.msword.PublipostageMsWord;
import fr.legrain.publipostage.openoffice.AbstractPublipostageOOo;
import fr.legrain.publipostage.openoffice.PublipostageOOoFactory;
import fr.legrain.tiers.dao.TaDocumentTiers;
import fr.legrain.tiers.dao.TaDocumentTiersDAO;
import fr.legrain.tiers.divers.ViewerSupportLocalDocumentTiers;
import fr.legrain.tiers.editor.EditorDocumentTiers;
import fr.legrain.tiers.editor.EditorInputDocumentTiers;

public class PaDocumentTiersController extends JPABaseControllerSWTStandard
		implements RetourEcranListener {

	static Logger logger = Logger.getLogger(PaDocumentTiersController.class.getName());
	private PaDocumentTiers vue = null;
	private TaDocumentTiersDAO dao = null;

	private Object ecranAppelant = null;
	private SWTDocumentTiers swtDocumentTiers;
	private SWTDocumentTiers swtOldDocumentTiers;
	private Realm realm;
	private DataBindingContext dbc;

	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private IObservableValue selectedDocumentTiers;
	private String[] listeChamp;
	private String nomClassController = this.getClass().getSimpleName();
	private MapChangeListener changeListener = new MapChangeListener();
	private Class classModel = SWTDocumentTiers.class;
	private ModelGeneralObjet<SWTDocumentTiers,TaDocumentTiersDAO,TaDocumentTiers> modelDocumentTiers = null;
	
	private TypeVersionPublipostage typeVersion;
	private LgrDozerMapper<SWTDocumentTiers,TaDocumentTiers> mapperUIToModel  = new LgrDozerMapper<SWTDocumentTiers,TaDocumentTiers>();
	private LgrDozerMapper<TaDocumentTiers,SWTDocumentTiers> mapperToModelUI  = new LgrDozerMapper<TaDocumentTiers,SWTDocumentTiers>();
	private TaDocumentTiers taDocumentTiers = null;
	private TaParamPublipostage paramPubli;
	private String versionPubli=null;
	
	
	public PaDocumentTiersController(PaDocumentTiers vue) {
		this(vue,null);
	}

	public PaDocumentTiersController(PaDocumentTiers vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaDocumentTiersDAO(getEm());
		modelDocumentTiers = new ModelGeneralObjet<SWTDocumentTiers,TaDocumentTiersDAO,TaDocumentTiers>(dao,classModel);
		setVue(vue);
		typeVersion = TypeVersionPublipostage.getInstance();
		// a faire avant l'initialisation du Binding
		vue.getFormulaire().getGrille().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setSwtOldTypeLiens();
			}
		});
		vue.getShell().addShellListener(this);

		// Branchement action annuler : empeche la fermeture automatique de la
		// fenetre sur ESC
		vue.getShell().addTraverseListener(new Traverse());

		initController();
	}

	private void initController() {
		try {
			setGrille(vue.getFormulaire().getGrille());
			initSashFormWeight();
			setDaoStandard(dao);
			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);
//			PreferenceInitializer.initDefautProperties(false);
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
			Menu[] tabPopups = new Menu[] { popupMenuFormulaire,popupMenuGrille };
			this.initPopupAndButtons(mapActions, tabPopups);
			vue.getPaCorpsFormulaire().setMenu(popupMenuFormulaire);
			vue.getFormulaire().getPaGrille().setMenu(popupMenuGrille);

			initEtatBouton();
		} catch (Exception e) {
			vue.getFormulaire().getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaTiersController", e);
		}
	}

	public void bind(PaDocumentTiers paTypeLiensSWT) {
		try {
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());
			tableViewer = new LgrTableViewer(paTypeLiensSWT.getFormulaire().getGrille());
			tableViewer.createTableCol(classModel,paTypeLiensSWT.getFormulaire().getGrille(), nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE,-1);
			listeChamp = tableViewer.setListeChampGrille(nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			
			
			tableViewer.addCheckStateListener(new ICheckStateListener() {

				@Override
				public void checkStateChanged(CheckStateChangedEvent event) {
					try {
						String versionNew=vue.getCbListeVersion().getItem(vue.getCbListeVersion().getSelectionIndex());
						getTableViewerStandard().setSelection(new StructuredSelection(recherche(Const.C_CODE_DOCUMENT_TIERS
								, ((SWTDocumentTiers)event.getElement()).getCodeDocumentTiers())),true);
						if(selectedDocumentTiers.getValue()!=null){
							actModifier();
							if(!event.getChecked())
								((SWTDocumentTiers)event.getElement()).setActif(event.getChecked());
							else 					
								if(event.getChecked() && ((SWTDocumentTiers)event.getElement()).getTypeLogiciel().equals(typeVersion.getType().get(versionNew)))
									((SWTDocumentTiers)event.getElement()).setActif(event.getChecked());
								else
									event.getCheckable().setChecked(event.getElement(), false);
							validateUIField(Const.C_ACTIF_DOCUMENT_TIERS,LibConversion.booleanToInt(event.getChecked()));
							actEnregistrer();
						}
					} catch (Exception e) {
						logger.error("", e);
					}
				}
			});
			tableViewer.setCheckStateProvider(new ICheckStateProvider() {
				
				@Override
				public boolean isGrayed(Object element) {
					// TODO Auto-generated method stub
					if(!((SWTDocumentTiers)element).getActif())
						return true;
					return false;
				}
				
				@Override
				public boolean isChecked(Object element) {
					// TODO Auto-generated method stub
					if(((SWTDocumentTiers)element).getActif())
						return true;
					return false;
				}
			});
			
			// Set up data binding.
			ViewerSupportLocalDocumentTiers.bind(tableViewer, 
					new WritableList(modelDocumentTiers.remplirListe(), classModel),
					BeanProperties.values(listeChamp)
					);
			
			selectedDocumentTiers = ViewersObservables.observeSingleSelection(tableViewer);

			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			tableViewer.selectionGrille(0);

			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);

			bindingFormMaitreDetail(dbc, realm, selectedDocumentTiers,classModel);
			changementDeSelection();
			selectedDocumentTiers.addChangeListener(new IChangeListener() {

				public void handleChange(ChangeEvent event) {
					changementDeSelection();
				}

			});

		} catch (Exception e) {
			vue.getFormulaire().getLaMessage().setText(e.getMessage());
			logger.error("", e);
		}
	}
	
	private void changementDeSelection() {
		if(selectedDocumentTiers!=null && selectedDocumentTiers.getValue()!=null) {
			if(((SWTDocumentTiers) selectedDocumentTiers.getValue()).getIdDocumentTiers()!=null) {
				taDocumentTiers = dao.findById(((SWTDocumentTiers) selectedDocumentTiers.getValue()).getIdDocumentTiers());
			}
			//null a tester ailleurs, car peut etre null en cas d'insertion
			fireChangementDeSelection(new ChangementDeSelectionEvent(PaDocumentTiersController.this));
		}
	}

	public Composite getVue() {
		return vue;
	}

	public ResultAffiche configPanel(ParamAffiche param) {
		if (param != null) {
			//ibTaTable.ouvreDataset();
			if (((ParamAfficheDocumentTiers) param).getFocusDefautSWT() != null && !((ParamAfficheDocumentTiers) param).getFocusDefautSWT().isDisposed())
				((ParamAfficheDocumentTiers) param).getFocusDefautSWT().setFocus();
			else
				((ParamAfficheDocumentTiers) param).setFocusDefautSWT(vue.getFormulaire().getGrille());
			vue.getFormulaire().getLaTitreFormulaire().setText(((ParamAfficheDocumentTiers) param).getTitreFormulaire());
			vue.getFormulaire().getLaTitreGrille().setText(((ParamAfficheDocumentTiers) param).getTitreGrille());
			vue.getFormulaire().getLaTitreFenetre().setText(((ParamAfficheDocumentTiers) param).getSousTitre());

			if (param.getEcranAppelant() != null) {
				ecranAppelant = param.getEcranAppelant();
			}

			bind(vue);
			// permet de se positionner sur le 1er enregistrement et de remplir
			// le formulaire
			tableViewer.selectionGrille(0);
			tableViewer.brancheComparateur(new SWTDocumentTiersComparator());
			tableViewer.tri(classModel, nomClassController,Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			VerrouInterface.setVerrouille(false);
			setSwtOldTypeLiens();

			if (param.getModeEcran() != null
					&& param.getModeEcran().compareTo(EnumModeObjet.C_MO_INSERTION) == 0) {
				try {
					actInserer();
				} catch (Exception e) {
					vue.getFormulaire().getLaMessage().setText(e.getMessage());
					logger.error("", e);
				}
			}
		}
		return null;
	}
	
	protected void initVerifySaisie() {
		if (mapInfosVerifSaisie == null)
			mapInfosVerifSaisie = new HashMap<Control, InfosVerifSaisie>();
		
		mapInfosVerifSaisie.put(vue.getTfCodeDocumentTiers(), new InfosVerifSaisie(new TaDocumentTiers(),Const.C_CODE_DOCUMENT_TIERS,null));
		mapInfosVerifSaisie.put(vue.getTfLibelleDocumentTiers(), new InfosVerifSaisie(new TaDocumentTiers(),Const.C_LIBELLE_DOCUMENT_TIERS,null));
		mapInfosVerifSaisie.put(vue.getTfCheminModelDocumentTiers(), new InfosVerifSaisie(new TaDocumentTiers(),Const.C_CHEMIN_MODEL_DOCUMENT_TIERS,null));
		mapInfosVerifSaisie.put(vue.getTfCheminCorrespDocumentTiers(), new InfosVerifSaisie(new TaDocumentTiers(),Const.C_CHEMIN_CORRESP_DOCUMENT_TIERS,null));

		initVerifyListener(mapInfosVerifSaisie, dao);
	}

	protected void initComposantsVue() throws ExceptLgr {
	}

	protected void initMapComposantChamps() {
		if (mapComposantChamps == null)
			mapComposantChamps = new LinkedHashMap<Control, String>();

		if (listeComposantFocusable == null)
			listeComposantFocusable = new ArrayList<Control>();
		listeComposantFocusable.add(vue.getFormulaire().getGrille());

		
		vue.getTfCodeDocumentTiers().setToolTipText(Const.C_CODE_DOCUMENT_TIERS);
		vue.getTfLibelleDocumentTiers().setToolTipText(Const.C_LIBELLE_DOCUMENT_TIERS);
		vue.getTfCheminModelDocumentTiers().setToolTipText(Const.C_CHEMIN_MODEL_DOCUMENT_TIERS);
		vue.getTfCheminCorrespDocumentTiers().setToolTipText(Const.C_CHEMIN_CORRESP_DOCUMENT_TIERS);
		
		listeComposantFocusable.add(vue.getCbListeVersion());
		
		mapComposantChamps.put(vue.getTfCodeDocumentTiers(), Const.C_CODE_DOCUMENT_TIERS);
		mapComposantChamps.put(vue.getTfLibelleDocumentTiers(), Const.C_LIBELLE_DOCUMENT_TIERS);
		mapComposantChamps.put(vue.getTfCheminModelDocumentTiers(), Const.C_CHEMIN_MODEL_DOCUMENT_TIERS);
		mapComposantChamps.put(vue.getTfCheminCorrespDocumentTiers(), Const.C_CHEMIN_CORRESP_DOCUMENT_TIERS);

		for (Control c : mapComposantChamps.keySet()) {
			listeComposantFocusable.add(c);
		}

		listeComposantFocusable.add(vue.getBtnChemin_Model());
		listeComposantFocusable.add(vue.getBtnChemin_Corresp());
		
		vue.getBtnChemin_Model().addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseDown(e);
				
				FileDialog dd = new FileDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
							
				dd.setFilterExtensions(new String[] {"*.*",paramPubli.getExtension()});
				dd.setFilterNames(new String[] {"Tous les fichiers","Modèle de lettre"});
				String repDestination = dd.getFilterPath(); 
				if(repDestination.equals(""))repDestination=Platform.getInstanceLocation().getURL().getFile();
				dd.setFilterPath(LibChaine.pathCorrect(repDestination));
				String choix = dd.open();
				System.out.println(choix);
				if(choix!=null){
					vue.getTfCheminModelDocumentTiers().setText(choix);
				}
				vue.getTfCheminModelDocumentTiers().forceFocus();
			}
			
		});
		
		vue.getBtnChemin_Corresp().addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseDown(e);
				FileDialog dd = new FileDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
				dd.setFilterExtensions(new String[] {typeVersion.EXTENSION_CORRESPONDANCE});
				dd.setFilterNames(new String[] {"Fichier de correspondance"});
				String repDestination = dd.getFilterPath(); 
				if(repDestination.equals(""))repDestination=Platform.getInstanceLocation().getURL().getFile();
				dd.setFilterPath(LibChaine.pathCorrect(repDestination));
				String choix = dd.open();
				System.out.println(choix);
				if(choix!=null){
					vue.getTfCheminCorrespDocumentTiers().setText(choix);
				}
				vue.getTfCheminCorrespDocumentTiers().forceFocus();
			}
			
		});
		
		vue.getBtnOuvrir().addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDown(MouseEvent e) {
				super.mouseDown(e);
				String chemin=vue.getTfCheminModelDocumentTiers().getText();
				if(((SWTDocumentTiers) selectedDocumentTiers.getValue()).getDefaut()){
					File fileNew=new File(Const.C_REPERTOIRE_BASE+Const.C_REPERTOIRE_PROJET+"/ModelesRelance"+"/"+chemin);
					chemin=fileNew.getPath();
				}
				if(typeVersion.getType().get(paramPubli.getLogicielPublipostage()).
						equals(TypeVersionPublipostage.TYPE_OPENOFFICE)){

					//PublipostageOOoWin32 pub = new PublipostageOOoWin32(listeParamPubli);
					AbstractPublipostageOOo pub = PublipostageOOoFactory.createPublipostageOOo(null);
					String pathOpenOffice = "";

					try {
						if(Platform.getOS().equals(Platform.OS_WIN32)){
							pathOpenOffice = WinRegistry.readString(
									WinRegistry.HKEY_LOCAL_MACHINE,
									WinRegistry.KEY_REGISTR_WIN_OPENOFFICE,
							"");
						} else if(Platform.getOS().equals(Platform.OS_LINUX)){
							pathOpenOffice = "/usr/bin/soffice";
						} else if(Platform.getOS().equals(Platform.OS_MACOSX)) {}
					}
					catch (Exception e3) {
						logger.error("",e3);
					}    

					pub.setCheminOpenOffice(new File(pathOpenOffice).getPath());
					pub.setPortOpenOffice("8100");

					pub.setNomFichierFinalFusionne(new File(chemin).getPath());
					pub.demarrerServeur();
					pub.OuvreDocument("8100",new File(chemin).getPath());

				}else if(typeVersion.getType().get(paramPubli.getLogicielPublipostage()).
						equals(TypeVersionPublipostage.TYPE_MSWORD)){
					PublipostageMsWord pub = new PublipostageMsWord(null);
					pub.OuvreDocument(new File(chemin).getPath());
				}
				
			}
			
		});
		

//		vue.getCbListeVersion().
		
		listeComposantFocusable.add(vue.getFormulaire().getBtnEnregistrer());
		listeComposantFocusable.add(vue.getFormulaire().getBtnInserer());
		listeComposantFocusable.add(vue.getFormulaire().getBtnModifier());
		listeComposantFocusable.add(vue.getFormulaire().getBtnSupprimer());
		listeComposantFocusable.add(vue.getFormulaire().getBtnFermer());
		listeComposantFocusable.add(vue.getFormulaire().getBtnAnnuler());
		listeComposantFocusable.add(vue.getFormulaire().getBtnImprimer());

		if (mapInitFocus == null)
			mapInitFocus = new LinkedHashMap<ModeObjet.EnumModeObjet, Control>();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION, vue
				.getTfCodeDocumentTiers());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue
				.getTfCodeDocumentTiers());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION, vue.getFormulaire()
				.getGrille());

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
		vue.getCbListeVersion().setItems(liste);
		vue.getCbListeVersion().select(rangVersion);
		String versionNew=vue.getCbListeVersion().getItem(vue.getCbListeVersion().getSelectionIndex());
		dao.RAZCheminVersion(typeVersion.getType().get(versionNew));

		vue.getCbListeVersion().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
//				super.widgetSelected(e);
				String versionNew=vue.getCbListeVersion().getItem(vue.getCbListeVersion().getSelectionIndex());
				if(!versionNew.equals(versionPubli)){
					if(MessageDialog.openQuestion(vue.getShell(),"Changement de version", 
							"Attention, si vous changez de version de logiciel, tous les chemins de vos types " +
							"de relances seront réinitialisés. Vous devrez ensuite les renseigner en fonction de la version " +
							"du logiciel de publipostage choisie !!!")){
						//Supprimer tous les chemins de tous les types relances
						//avec un message pour prévenir
						TaParamPublipostageDAO daoParam =new TaParamPublipostageDAO(getEm());
						if(paramPubli==null)paramPubli=daoParam.findInstance();
						paramPubli.setLogicielPublipostage(versionNew);
						paramPubli.setExtension(typeVersion.getExtension().get(versionNew));
						paramPubli.setId(1);
					
						try {
							daoParam.begin(daoParam.getEntityManager().getTransaction());
							paramPubli=daoParam.merge(paramPubli);
							daoParam.commit(daoParam.getEntityManager().getTransaction());
							versionPubli=paramPubli.getLogicielPublipostage();
							dao.RAZCheminVersion(typeVersion.getType().get(versionNew));
							modelDocumentTiers.setListeEntity(null);
							actRefresh(true);
						} catch (Exception e1) {
							logger.error("",e1);
						}
					}else{
						int rang =vue.getCbListeVersion().indexOf(versionPubli);
						if(rang==-1)rang=0;
						vue.getCbListeVersion().select(rang);
					}
						
				}					
			}
			
		});
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
		
		initFocusCommand(String.valueOf(this.hashCode()),listeComposantFocusable,mapCommand);
		
		if (mapActions == null)
			mapActions = new LinkedHashMap<Button, Object>();
		
		mapActions.put(vue.getFormulaire().getBtnAnnuler(), C_COMMAND_GLOBAL_ANNULER_ID);
		mapActions.put(vue.getFormulaire().getBtnEnregistrer(), C_COMMAND_GLOBAL_ENREGISTRER_ID);
		mapActions.put(vue.getFormulaire().getBtnInserer(), C_COMMAND_GLOBAL_INSERER_ID);
		mapActions.put(vue.getFormulaire().getBtnModifier(), C_COMMAND_GLOBAL_MODIFIER_ID);
		mapActions.put(vue.getFormulaire().getBtnSupprimer(), C_COMMAND_GLOBAL_SUPPRIMER_ID);
		mapActions.put(vue.getFormulaire().getBtnFermer(), C_COMMAND_GLOBAL_FERMER_ID);
		mapActions.put(vue.getFormulaire().getBtnImprimer(), C_COMMAND_GLOBAL_IMPRIMER_ID);

		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID, C_COMMAND_GLOBAL_REFRESH_ID };
		mapActions.put(null, tabActionsAutres);
	}

	public PaDocumentTiersController getThis() {
		return this;
	}

	@Override
	public boolean onClose() throws ExceptLgr {
		boolean retour = true;
		VerrouInterface.setVerrouille(true);
		switch (dao.getModeObjet().getMode()) {
		case C_MO_INSERTION:
		case C_MO_EDITION:
			if (MessageDialog.openQuestion(vue.getShell(), MessagesEcran
					.getString("Message.Attention"), MessagesEcran
					.getString("TypeLiens.Message.Enregistrer"))) {

				try {
					actEnregistrer();
				} catch (Exception e) {
					vue.getFormulaire().getLaMessage().setText(e.getMessage());
					logger.error("", e);
				}
			} else
			break;
		case C_MO_CONSULTATION:
			break;
		default:
			break;
		}

		if (retour) {
			if (ecranAppelant instanceof SWTPaAideControllerSWT) {
				setListeEntity(getModelDocumentTiers().remplirListe());
				dao.initValeurIdTable(taDocumentTiers);
				fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
						dao.getChampIdEntite(), dao.getValeurIdTable(),
						selectedDocumentTiers.getValue())));

				retour = true;
			}
		}
		return retour;
	}

	public void retourEcran(final RetourEcranEvent evt) {
		if (evt.getRetour() != null
				&& (evt.getSource() instanceof SWTPaAideControllerSWT)) {
			if (getFocusAvantAideSWT() instanceof Text) {
				try {
					((Text) getFocusAvantAideSWT()).setText(((ResultAffiche) evt.getRetour()).getResult());					
					ctrlUnChampsSWT(getFocusAvantAideSWT());
				} catch (Exception e) {
					logger.error("",e);
					vue.getFormulaire().getLaMessage().setText(e.getMessage());
				}
			}
			if (getFocusAvantAideSWT() instanceof Table) {
				if (getFocusAvantAideSWT() == vue.getFormulaire().getGrille()) {
					if(((ResultAffiche) evt.getRetour()).getSelection()!=null)
						tableViewer.setSelection(((ResultAffiche) evt.getRetour()).getSelection(),true);
				}
			}
		} else if (evt.getRetour() != null) {
			if (getFocusAvantAideSWT() instanceof Table) {
				if (getFocusAvantAideSWT() == vue.getFormulaire().getGrille()) {
					if(((ResultAffiche) evt.getRetour()).getSelection()!=null)
						tableViewer.setSelection(((ResultAffiche) evt.getRetour()).getSelection(),true);
				}
			}
		}
		super.retourEcran(evt);
	}

	@Override
	protected void actInserer() throws Exception {
		try {
			if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
				VerrouInterface.setVerrouille(true);
				setSwtOldTypeLiens();
				swtDocumentTiers = new SWTDocumentTiers();
				swtDocumentTiers.setActif(true);
				swtDocumentTiers.setDefaut(false);
				String versionNew=vue.getCbListeVersion().getItem(vue.getCbListeVersion().getSelectionIndex());
				swtDocumentTiers.setTypeLogiciel(typeVersion.getType().get(versionNew));
				taDocumentTiers = new TaDocumentTiers();
//				String modelDefaut=fr.legrain.relancefacture.divers.Const.pathRepertoireModelesRelances();
				dao.inserer(taDocumentTiers);
				modelDocumentTiers.getListeObjet().add(swtDocumentTiers);
				writableList = new WritableList(realm, modelDocumentTiers.getListeObjet(), classModel);
				tableViewer.setInput(writableList);
				tableViewer.refresh(true);
				tableViewer.setSelection(new StructuredSelection(swtDocumentTiers));
				initEtatBouton();
			}

		} catch (Exception e1) {
			vue.getFormulaire().getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		} finally {
			initEtatComposant();
			VerrouInterface.setVerrouille(false);
		}
	}

	@Override
	protected void actModifier() throws Exception {
		try {
			if(!LgrMess.isDOSSIER_EN_RESEAU()){
				setSwtOldTypeLiens();
				taDocumentTiers = dao.findById(((SWTDocumentTiers) selectedDocumentTiers.getValue()).getIdDocumentTiers());
			}else{
				if(!setSwtOldTypeLiensRefresh())throw new Exception();
			}			
			dao.modifier(taDocumentTiers);
			initEtatBouton();
		} catch (Exception e1) {
			vue.getFormulaire().getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		}
	}
	public boolean containsEntity(TaDocumentTiers entite){
		if(modelDocumentTiers.getListeEntity()!=null){
			for (Object e : modelDocumentTiers.getListeEntity()) {
				if(((TaDocumentTiers)e).getIdDocumentTiers()==
					entite.getIdDocumentTiers())return true;
			}
		}
		return false;
	}


	
	public boolean setSwtOldTypeLiensRefresh() {
		try {	
			if (selectedDocumentTiers.getValue()!=null){
				TaDocumentTiers taTRelanceOld =dao.findById(taDocumentTiers.getIdDocumentTiers());
				taTRelanceOld=dao.refresh(taTRelanceOld);
				if(containsEntity(taDocumentTiers)) 
					modelDocumentTiers.getListeEntity().remove(taDocumentTiers);
				if(!taDocumentTiers.getVersionObj().equals(taTRelanceOld.getVersionObj())){
					taDocumentTiers=taTRelanceOld;
					if(!containsEntity(taDocumentTiers)) 
						modelDocumentTiers.getListeEntity().add(taDocumentTiers);					
					actRefresh();
					dao.messageNonAutoriseModification();
				}
				taDocumentTiers=taTRelanceOld;
				if(!containsEntity(taDocumentTiers)) 
					modelDocumentTiers.getListeEntity().add(taDocumentTiers);
				changementDeSelection();
				this.swtOldDocumentTiers=SWTDocumentTiers.copy((SWTDocumentTiers)selectedDocumentTiers.getValue());
			}
			return true;
		} catch (Exception e) {
			return false;
		}		
	}
	
//	public void setSwtOldTypeLiensRefresh() {
//		if (selectedTypeLiens.getValue()!=null){
//			if(LgrMess.isDOSSIER_EN_RESEAU())dao.refresh(dao.findById(((SWTTLiens) selectedTypeLiens.getValue()).getIdTLiens()));
//			taTLiens=dao.findById(((SWTTLiens) selectedTypeLiens.getValue()).getIdTLiens());
//			try {
//				if(LgrMess.isDOSSIER_EN_RESEAU())actRefresh();
//			} catch (Exception e) {
//				logger.error("",e);
//			}			
//			this.swtOldTypeLiens=SWTTLiens.copy((SWTTLiens)selectedTypeLiens.getValue());
//		}
//	}
	@Override
	protected void actSupprimer() throws Exception {
		EntityTransaction transaction = dao.getEntityManager().getTransaction();
		try {
			VerrouInterface.setVerrouille(true);
//			if(isUtilise())MessageDialog.openInformation(vue.getShell(), MessagesEcran
//					.getString("Message.Attention"), "Ce type de relance est utilisé, il ne peut pas être supprimé.");
//			else		
			if (MessageDialog.openConfirm(vue.getShell(), MessagesEcran
					.getString("Message.Attention"), "Etes-vous sûr de vouloir supprimer ce type document")) {

			dao.begin(transaction);
				TaDocumentTiers u = dao.findById(((SWTDocumentTiers) selectedDocumentTiers.getValue()).getIdDocumentTiers());
				if(u.getDefaut()==1){
					MessageDialog.openInformation(vue.getShell(), "Attention", "Ce type de relance ne peut pas être supprimé." +
							"\r\n"+"Il fait partie des types de relance fournis par défaut") ;
					throw new Exception();
				}
				
			dao.supprimer(u);
			dao.commit(transaction);
			Object suivant=tableViewer.getElementAt(tableViewer.getTable().getSelectionIndex()+1);
				modelDocumentTiers.removeEntity(taDocumentTiers);
				taDocumentTiers=null;
			dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
			if(suivant!=null)tableViewer.setSelection(new StructuredSelection(suivant),true);
			else tableViewer.selectionGrille(0);
			actRefresh(); //ajouter pour tester jpa

			}
		} catch (Exception e1) {
			vue.getFormulaire().getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		} finally {
			if(transaction!=null && transaction.isActive()) {
				transaction.rollback();
			}
			initEtatBouton();
			VerrouInterface.setVerrouille(false);
		}
	}

	@Override
	protected void actFermer() throws Exception {
		// (controles de sortie et fermeture de la fenetre) => onClose()
		if(onClose())
		closeWorkbenchPart();
	}

	@Override
	protected void actAnnuler() throws Exception {
		// // verifier si l'objet est en modification ou en consultation
		// // si modification ou insertion, alors demander si annulation des
		// // modifications si ok, alors annulation si pas ok, alors arreter le
		// processus d'annulation
		// // si consultation declencher l'action "fermer".
		try {
			VerrouInterface.setVerrouille(true);
			switch (dao.getModeObjet().getMode()) {
			case C_MO_INSERTION:
				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("TypeLiens.Message.Annuler")))) {
					remetTousLesChampsApresAnnulationSWT(dbc);
					if(((SWTDocumentTiers) selectedDocumentTiers.getValue()).getIdDocumentTiers()==null){
					modelDocumentTiers.getListeObjet().remove(((SWTDocumentTiers) selectedDocumentTiers.getValue()));
					writableList = new WritableList(realm, modelDocumentTiers.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.selectionGrille(0);
					}
					dao.annuler(taDocumentTiers);
					hideDecoratedFields();
				}
				initEtatBouton();
				break;
			case C_MO_EDITION:
				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("TypeLiens.Message.Annuler")))) {
					int rang = modelDocumentTiers.getListeObjet().indexOf(selectedDocumentTiers.getValue());
					remetTousLesChampsApresAnnulationSWT(dbc);
					modelDocumentTiers.getListeObjet().set(rang, swtOldDocumentTiers);
					writableList = new WritableList(realm, modelDocumentTiers.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.setSelection(new StructuredSelection(swtOldDocumentTiers), true);
					dao.annuler(taDocumentTiers);
					hideDecoratedFields();
				}
				initEtatBouton();

				break;
			case C_MO_CONSULTATION:
				actFermer();
				break;
			default:
				break;
			}
		} finally {
			VerrouInterface.setVerrouille(false);
		}
	}

	@Override
	protected void actImprimer() throws Exception {
//		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
//		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
//
//		String nomChampIdTable =  dao.getChampIdTable();
//
//		FonctionGetInfosXmlAndProperties fonctionGetInfosXmlAndProperties = new FonctionGetInfosXmlAndProperties(mapperUIToModel);
//		fonctionGetInfosXmlAndProperties.cleanValueMapAttributeTable();
//
//		fonctionGetInfosXmlAndProperties.setValueMapAttributeTable(TaTLiens.class.getSimpleName()+".head");
//		fonctionGetInfosXmlAndProperties.setValueMapAttributeTable(TaTLiens.class.getSimpleName()+".detail");
//		
//		LinkedHashMap<String,AttributElementResport> mapAttributeTablHead = fonctionGetInfosXmlAndProperties.getMapAttributeTablHead(); 
//		LinkedHashMap<String,AttributElementResport> mapAttributeTablDetail = fonctionGetInfosXmlAndProperties.getMapAttributeTablDetail();
//		
//		Collection<TaTLiens> collectionTaTLiens = modelTypeLiens.getListeEntity();
//		
//		fonctionGetInfosXmlAndProperties.findInfosFileXml(TaTLiens.class.getName(),SWTTLiens.class.getName(),
//				listeChamp,"mapping");
//		fonctionGetInfosXmlAndProperties.getInfosObjetJPA(taTLiens);
//
//		ConstEdition constEdition = new ConstEdition(); 
////		Impression impression = new Impression(constEdition,taTLiens,collectionTaTLiens,nomChampIdTable,taTLiens.getIdTLiens());
//		String nomDossier = null;
//
//		int nombreLine = collectionTaTLiens.size();
//
//		if(nombreLine==0){
//			MessageDialog.openWarning(vue.getShell(), ConstEdition.TITRE_MESSAGE_EDITION_VIDE,
//					ConstEdition.EDITION_VIDE);
//		}
//		else{
//			if(taInfoEntreprise.getIdInfoEntreprise()==0){
//				nomDossier = ConstEdition.INFOS_VIDE;
//			}
//			else{
//				nomDossier = taInfoEntreprise.getNomInfoEntreprise();	
//			}
//
//			constEdition.addValueList(tableViewer, nomClassController);
//
//			/**
//			 * pathFileReport ==> le path de ficher de edition dynamique
//			 */
//			String folderEditionDynamique = Const.C_RCP_INSTANCE_LOCATION+"/"+Const.C_NOM_PROJET_TMP+"/"+TaTLiens.class.getSimpleName();
//			constEdition.makeFolderEditions(folderEditionDynamique);
//			Path pathFileReport = new Path(folderEditionDynamique+"/"+Const.C_NOM_VU_T_LIEN+".rptdesign");
//			final String pathFileReportDynamic = pathFileReport.toPortableString();
//
//			MakeDynamiqueReport DynamiqueReport = new MakeDynamiqueReport(constEdition.getNameTableEcran(),
//					constEdition.getNameTableBDD(),pathFileReportDynamic,Const.C_NOM_VU_T_LIEN,
//					ConstEdition.PAGE_ORIENTATION_LANDSCAPE,nomDossier); 
//			
//			DynamiqueReport.setSimpleNameEntity(TaTLiens.class.getSimpleName());
//			/**************************************************************/
//			DynamiqueReport.setFonctionGetInfosXml(fonctionGetInfosXmlAndProperties);
//			DynamiqueReport.setNomObjet(TaTLiens.class.getSimpleName());
//			/**************************************************************/
//
//			Map<String, AttributElementResport> attribuGridHeader = new LinkedHashMap<String, AttributElementResport>();
//			String nameHeaderTitle = ConstEditionTiers.TITLE_EDITION_TYPE_LIENS;
//			attribuGridHeader.put(nameHeaderTitle, new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
//					ConstEdition.FONT_SIZE_XX_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
//					ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE,""));
//			String nameSousHeaderTitle = ConstEditionTiers.SOUS_TITLE_EDITION_TYPE_LIENS;
//			attribuGridHeader.put(nameSousHeaderTitle, new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
//					ConstEdition.FONT_SIZE_X_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
//					ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE,ConstEdition.COLOR_GRAY));
//
//			//DynamiqueReport.buildDesignConfig();
//			ConstEdition.CONTENT_COMMENTS = ConstEditionTiers.COMMENTAIRE_EDITION_DEFAUT;
//			DynamiqueReport.initializeBuildDesignReportConfig();
//			DynamiqueReport.makePageMater("1", "1", "1", "1", "100");
//			DynamiqueReport.makeReportHeaderGrid(3,5,100,ConstEdition.UNITS_PERCENTAGE,attribuGridHeader);
////			DynamiqueReport.makeReportTableDB(100,ConstEdition.UNITS_PERCENTAGE,
////					Const.C_NOM_VU_T_TEL,attribuTabHeader,attribuTabDetail,1,1,2,5,"30");
//			DynamiqueReport.biuldTableReport("100", ConstEdition.UNITS_PERCENTAGE, 
//					Const.C_NOM_VU_T_LIEN,1,1,2,"40", mapAttributeTablHead, mapAttributeTablDetail);
//			DynamiqueReport.savsAsDesignHandle();
////			impression.imprimer(true,pathFileReportDynamic,null,"Type Liens",TaTLiens.class.getSimpleName(),false);
//			/** 01/03/2010 modifier les editions (zhaolin) **/
//			BaseImpressionEdition impressionEdition = new BaseImpressionEdition(constEdition,taTLiens,
//					getEm(),collectionTaTLiens,taTLiens.getIdTLiens());
//			impressionEdition.impressionEditionTypeEntity(pathFileReportDynamic,"Lien");
//			
//		}
	}
	
	@Override
	protected boolean aideDisponible() {
		boolean result = false;
		switch ((getThis().dao.getModeObjet().getMode())) {
		case C_MO_CONSULTATION:
			if(getFocusCourantSWT().equals(vue.getFormulaire().getGrille()))
				result = true;
			break;
		case C_MO_EDITION:
		case C_MO_INSERTION:
			break;
		default:
			break;
		}
		return result;
	}

	@Override
	protected void actAide() throws Exception {
		actAide(null);
	}

	@Override
	protected void actAide(String message) throws Exception {
		if(aideDisponible()){
			try {
				VerrouInterface.setVerrouille(true);
				vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
				ParamAfficheAideRechercheSWT paramAfficheAideRecherche = new ParamAfficheAideRechercheSWT();
				paramAfficheAideRecherche.setMessage(message);
				// Creation de l'ecran d'aide principal
				Shell s = new Shell(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),LgrShellUtil.styleLgr);
				PaAideSWT paAide = new PaAideSWT(s, SWT.NONE);
				SWTPaAideControllerSWT paAideController = new SWTPaAideControllerSWT(paAide);
				/***************************************************************/
				LgrPartListener.getLgrPartListener().setLgrActivePart(null);
				IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new EditorInputAide(), EditorAide.ID);
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

				switch ((getThis().dao.getModeObjet().getMode())) {
				case C_MO_CONSULTATION:
					if(getFocusCourantSWT().equals(vue.getFormulaire().getGrille())){
						PaDocumentTiers paTypeLiensSWT = new PaDocumentTiers(s2,SWT.NULL);
						PaDocumentTiersController swtPaTypeLiensController = new PaDocumentTiersController(paTypeLiensSWT);

						editorCreationId = EditorDocumentTiers.ID;
						editorInputCreation = new EditorInputDocumentTiers();

						ParamAfficheDocumentTiers paramAfficheTypeLiens = new ParamAfficheDocumentTiers();
						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
						paramAfficheTypeLiens.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheTypeLiens.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaTypeLiensController;
						parametreEcranCreation = paramAfficheTypeLiens;

						paramAfficheAideRecherche.setTypeEntite(TaDocumentTiers.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_T_RELANCE);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCodeDocumentTiers().getText());
						paramAfficheAideRecherche.setControllerAppelant(getThis());
						paramAfficheAideRecherche.setModel(swtPaTypeLiensController.getModelDocumentTiers());
						paramAfficheAideRecherche.setTypeObjet(swtPaTypeLiensController.getClassModel());
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_T_RELANCE);
					}
					break;
				case C_MO_EDITION:
				case C_MO_INSERTION:
					break;
				default:
					break;
				}

				if (paramAfficheAideRecherche.getJPQLQuery() != null) {

					PaAideRechercheSWT paAideRecherche1 = new PaAideRechercheSWT(s,SWT.NULL);
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
					paAideController.addRetourEcranListener(getThis());
					Control focus = vue.getShell().getDisplay().getFocusControl();
					// affichage de l'ecran d'aide principal (+ ses recherches)

					dbc.getValidationStatusMap().removeMapChangeListener(
							changeListener);
					/*****************************************************************/
					paAideController.configPanel(paramAfficheAide);
					/*****************************************************************/
					dbc.getValidationStatusMap().addMapChangeListener(changeListener);

				}

			} finally {
				VerrouInterface.setVerrouille(false);
				vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
			}
		}
	}
	public IStatus validateUI() {
		return null;
	}
	
	public IStatus validateUIField(String nomChamp,Object value) {
		String validationContext = "DOCUMENT_TIERS";
		try {
			IStatus s = null;
			boolean verrouilleModifCode = false;
			//int change=0;
			TaDocumentTiers u = new TaDocumentTiers();
			if(nomChamp.equals(Const.C_ACTIF_DOCUMENT_TIERS)||nomChamp.equals(Const.C_CODE_DOCUMENT_TIERS)||
					nomChamp.equals(Const.C_LIBELLE_DOCUMENT_TIERS)||nomChamp.equals(Const.C_CHEMIN_MODEL_DOCUMENT_TIERS)
					||nomChamp.equals(Const.C_CHEMIN_CORRESP_DOCUMENT_TIERS)){
				PropertyUtils.setSimpleProperty(u, nomChamp, value);
			}

			if(nomChamp.equals(Const.C_ACTIF_DOCUMENT_TIERS)){
				Integer retour=0;
				if(value!=null)
					if(value instanceof Boolean)retour=LibConversion.booleanToInt((Boolean) value);
					PropertyUtils.setSimpleProperty(u, nomChamp, retour);
			}
			
			if(((SWTDocumentTiers) selectedDocumentTiers.getValue()).getIdDocumentTiers()!=null) {
				u.setIdDocumentTiers(((SWTDocumentTiers) selectedDocumentTiers.getValue()).getIdDocumentTiers());
			}
			if(nomChamp.equals(Const.C_CODE_DOCUMENT_TIERS)){
				verrouilleModifCode = true;
			}
			s = dao.validate(u,nomChamp,validationContext,verrouilleModifCode);
			if(s.getSeverity()!=IStatus.ERROR ) {
//				if(nomChamp.equals(Const.C_CODE_T_RELANCE)){
//					if(((SWTDocumentTiers) selectedDocumentTiers.getValue()).getOrdreTRelance()==null||
//							((SWTDocumentTiers) selectedDocumentTiers.getValue()).getOrdreTRelance()==0){
//						taDocumentTiers.setOrdreTRelance(dao.maxOrdreRelance());
//						((SWTDocumentTiers) selectedDocumentTiers.getValue()).setOrdreTRelance(taDocumentTiers.getOrdreTRelance());
//					}
//				}
//
//				if(nomChamp.equals(Const.C_ORDRE_T_RELANCE)){
//					if(u.getOrdreTRelance()==null || u.getOrdreTRelance()==0 ||dao.OrdreRelanceUtilise(u.getOrdreTRelance()))
//						u.setOrdreTRelance(dao.maxOrdreRelance());
//					taDocumentTiers.setOrdreTRelance(u.getOrdreTRelance());
//				}	
				if(nomChamp.equals(Const.C_ACTIF_DOCUMENT_TIERS)){
					taDocumentTiers.setActif(u.getActif());
				}
			}
			return s;
		} catch (IllegalAccessException e) {
			logger.error("",e);
		} catch (InvocationTargetException e) {
			logger.error("",e);
		} catch (NoSuchMethodException e) {
			logger.error("",e);
		}
		return null;
	}
	
	@Override
	protected void actEnregistrer() throws Exception {
		EntityTransaction transaction = dao.getEntityManager().getTransaction();
		try {
			ctrlUnChampsSWT(getFocusCourantSWT());
			dao.begin(transaction);
			if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)||
					(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)) {
				mapperUIToModel.map((SWTDocumentTiers) selectedDocumentTiers.getValue(),taDocumentTiers);
				
				taDocumentTiers=dao.enregistrerMerge(taDocumentTiers);
//				if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
//					modelTypeLiens.getListeEntity().add(taTLiens);
			} 
			dao.commit(transaction);
			modelDocumentTiers.addEntity(taDocumentTiers);
			transaction = null;
			actRefresh();
		} finally {
			if(transaction!=null && transaction.isActive()) {
				transaction.rollback();
			}
			initEtatBouton();
		}
	}


	public void initEtatComposant() {
		try {
			Boolean modifiable =!((SWTDocumentTiers)selectedDocumentTiers.getValue()).getDefaut() &&
			((SWTDocumentTiers)selectedDocumentTiers.getValue()).getActif();
			vue.getTfCodeDocumentTiers().setEditable(!isUtilise());
			vue.getTfCheminCorrespDocumentTiers().setEditable(modifiable);
			vue.getTfCheminModelDocumentTiers().setEditable(modifiable);
			vue.getBtnChemin_Corresp().setEnabled(modifiable);
			vue.getBtnChemin_Model().setEnabled(modifiable);
			changeCouleur(vue);
	    } catch (Exception e) {
		vue.getFormulaire().getLaMessage().setText(e.getMessage());
	    }
	}

	public boolean isUtilise(){
		return (((SWTDocumentTiers)selectedDocumentTiers.getValue()).getIdDocumentTiers()!=null &&
		!dao.recordModifiable(dao.getNomTable(),
				((SWTDocumentTiers)selectedDocumentTiers.getValue()).getIdDocumentTiers()))||
				!dao.autoriseModification(taDocumentTiers);		
	}
	
	public SWTDocumentTiers getSwtOldDocumentTiers() {
		return swtOldDocumentTiers;
	}

	public void setSwtOldTypeLiens(SWTDocumentTiers swtOldTypeLiens) {
		this.swtOldDocumentTiers = swtOldTypeLiens;
	}
	

	public void setSwtOldTypeLiens() {
		if (selectedDocumentTiers.getValue() != null)
			this.swtOldDocumentTiers = SWTDocumentTiers.copy((SWTDocumentTiers) selectedDocumentTiers.getValue());
		else {
			if(tableViewer.selectionGrille(0)){
				this.swtOldDocumentTiers = SWTDocumentTiers.copy((SWTDocumentTiers) selectedDocumentTiers.getValue());
				tableViewer.setSelection(new StructuredSelection(
						(SWTDocumentTiers) selectedDocumentTiers.getValue()), true);
			}
		}
	}
	public void setVue(PaDocumentTiers vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();
		mapComposantDecoratedField.put(vue.getTfCodeDocumentTiers(), vue.getFieldCodeDocumentTiers());
		mapComposantDecoratedField.put(vue.getTfLibelleDocumentTiers(), vue.getFieldLibelleDocumentTiers());
		mapComposantDecoratedField.put(vue.getTfCheminModelDocumentTiers(), vue.getFieldCheminDocumentTiers());
		mapComposantDecoratedField.put(vue.getTfCheminCorrespDocumentTiers(), vue.getFieldCheminCorrespDocumentTiers());
	}

	public Class getClassModel() {
		return classModel;
	}

	@Override
	protected void sortieChamps() {
//		if(selectedDocumentTiers!=null && selectedDocumentTiers.getValue()!=null){
//			if(vue.getTfORDRE_T_RELANCE().equals(getFocusCourantSWT())){
//				((SWTDocumentTiers) selectedDocumentTiers.getValue()).setOrdreTRelance(taDocumentTiers.getOrdreTRelance());
////				mapperToModelUI.map(taTRelance,(SWTDocumentTiers) selectedTypeRelance.getValue());	
//			}
//		}
	}
	protected void actRefresh(Boolean sorter) throws Exception {
		TaDocumentTiers u = taDocumentTiers;
		if (u!=null && u.getIdDocumentTiers()==0)
			u=dao.findById(u.getIdDocumentTiers());
		writableList = new WritableList(realm, modelDocumentTiers.remplirListe(), classModel);
		tableViewer.setInput(writableList);
		//if(sorter)tableViewer.setSorter(new SWTDocumentTiersComparator());
		tableViewer.selectionGrille(
				tableViewer.selectionGrille(selectedDocumentTiers.getValue()));
		
		if(u != null) {
			Iterator<SWTDocumentTiers> ite = modelDocumentTiers.getListeObjet().iterator();
			SWTDocumentTiers tmp = null;
			int i = 0;
			boolean trouve = false;
			while (ite.hasNext() && !trouve) {
				tmp = ite.next();
				if(tmp.getIdDocumentTiers()==u.getIdDocumentTiers()) {
					tableViewer.setSelection(new StructuredSelection(tmp));
					trouve = true;
				}
				i++;
			}
		}	
	
	}
	@Override
	protected void actRefresh() throws Exception {
		actRefresh(false);
	}

	public ModelGeneralObjet<SWTDocumentTiers,TaDocumentTiersDAO,TaDocumentTiers> getModelDocumentTiers() {
		return modelDocumentTiers;
	}

	public TaDocumentTiersDAO getDao() {
		return dao;
	}


//	public static String pathRepertoireModelesRelances(){
//		Bundle bundleEditions =Platform.getBundle(Activator.PLUGIN_ID);
//		if(bundleEditions==null)return null;
//		URL urlReportFile;
//		try {
//
//			urlReportFile = FileLocator.find(bundleEditions,new Path("ModelesRelance"),null);
//			if(urlReportFile!=null){
//				urlReportFile = FileLocator.toFileURL(urlReportFile);
//
//				URI uriReportFile = new URI("file", urlReportFile.getAuthority(),
//						urlReportFile.getPath(), urlReportFile.getQuery(), null);
//				File reportFileEdition = new File(uriReportFile);
//				return reportFileEdition.getAbsolutePath();
//			}else{
//				/*
//				 * il n'a y pas fragement pour client 
//				 */
//				return null;
//			}
//		} catch (Exception e) {
//			logger.error("",e);
//			return null;
//		}		
//	}

	protected void initImageBouton() {
		super.initImageBouton();
		vue.getFormulaire().getBtnAnnuler().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ANNULER));
		vue.getFormulaire().getBtnEnregistrer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ENREGISTRER));
		vue.getFormulaire().getBtnFermer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_FERMER));
		vue.getFormulaire().getBtnImprimer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_IMPRIMER));
		vue.getFormulaire().getBtnInserer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_INSERER));
		vue.getFormulaire().getBtnModifier().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_MODIFIER));
		vue.getFormulaire().getBtnSupprimer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_SUPPRIMER));
		vue.layout(true);
	}

//	public class SWTDocumentTiersComparator implements Comparator<SWTDocumentTiers> {
//
//		public int compare(SWTDocumentTiers elem1, SWTDocumentTiers elem2) {
//			int Compare2 = elem1.getOrdreTRelance().
//			compareTo(elem2.getOrdreTRelance());
//			Integer bool1=(LibConversion.booleanToInt(elem1.getActif())+1)*-1;
//			Integer bool2=(LibConversion.booleanToInt(elem2.getActif())+1)*-1;
//			int Compare1 = bool1.compareTo(bool2);
//			
////			int Compare3 = elem1.getDefaut().compareTo(elem2.getDefaut());
//
//			int compared = Compare1;
//			if (compared == 0) {
//				compared = Compare2;
//			}
////			if (compared == 0) {
////				compared = Compare3;
////			}
//			return compared;
//		}
//	}

	public Object recherche(String propertyName, Object value) {
		boolean trouve = false;
		int i = 0;
		List<SWTDocumentTiers> model=modelDocumentTiers.getListeObjet();
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
			return modelDocumentTiers.getListeObjet().get(i);
		else 
			return null;

	}
	
	public class SWTDocumentTiersComparator extends ViewerSorter {

		@Override
		public int compare(Viewer viewer, Object elem1, Object elem2) {
			int Compare2 = 0;
			int Compare1 = 0;
			if(elem1!=null && elem1!=null){
//				if(((SWTDocumentTiers)elem1).getOrdreTRelance()!=null && ((SWTDocumentTiers)elem2).getOrdreTRelance()!=null)	
//					Compare2 = ((SWTDocumentTiers)elem1).getOrdreTRelance().compareTo(((SWTDocumentTiers)elem2).getOrdreTRelance());
				if(((SWTDocumentTiers)elem1).getActif()!=null && ((SWTDocumentTiers)elem2).getActif()!=null){		
					Integer bool1=(LibConversion.booleanToInt(((SWTDocumentTiers)elem1).getActif())+1)*-1;
					Integer bool2=(LibConversion.booleanToInt(((SWTDocumentTiers)elem2).getActif())+1)*-1;
					Compare1 = bool1.compareTo(bool2);
				}
			}
			int compared = Compare1;
			if (compared == 0) {
				compared = Compare2;
			}
			return compared;
		}

	}
}

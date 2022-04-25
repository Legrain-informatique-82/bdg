package fr.legrain.relancefacture.controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
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

import fr.legrain.document.controller.MessagesEcran;
import fr.legrain.document.divers.TypeDoc;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaFactureDAO;
import fr.legrain.documents.dao.TaLRelance;
import fr.legrain.documents.dao.TaLRelanceDAO;
import fr.legrain.documents.dao.TaLRemise;
import fr.legrain.documents.dao.TaParamPublipostage;
import fr.legrain.documents.dao.TaParamPublipostageDAO;
import fr.legrain.documents.dao.TaRelance;
import fr.legrain.documents.dao.TaRelanceDAO;
import fr.legrain.documents.dao.TaTRelance;
import fr.legrain.documents.dao.TaTRelanceDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Document.IHMLRelance;
import fr.legrain.gestCom.Module_Tiers.SWTTRelance;
import fr.legrain.gestCom.gestComBd.gestComBdPlugin;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.DeclencheCommandeControllerEvent;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrDatabindingUtil;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideRechercheSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideRechercheControllerSWT;
import fr.legrain.gestCom.librairiesEcran.workbench.AnnuleToutEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.gestionCommerciale.UtilWorkspace;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.InfosVerifSaisie;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.LibDate;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.data.VerrouInterface;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
import fr.legrain.lib.gui.DefaultFrameFormulaireSWT;
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
import fr.legrain.publipostage.msword.PublipostageMsWord;
import fr.legrain.publipostage.openoffice.AbstractPublipostageOOo;
import fr.legrain.publipostage.openoffice.PublipostageOOoFactory;
import fr.legrain.publipostage.openoffice.PublipostageOOoWin32;
import fr.legrain.relancefacture.Activator;
import fr.legrain.relancefacture.divers.ParamAfficheRelanceFacture;
import fr.legrain.relancefacture.divers.ParamAfficheTRelance;
import fr.legrain.relancefacture.divers.ViewerSupportLocal;
import fr.legrain.relancefacture.ecran.PaSelectionLigneRelance;
import fr.legrain.relancefacture.ecran.PaTypeRelanceSWT;
import fr.legrain.relancefacture.ecran.PaTypeRelanceVersion;
import fr.legrain.relancefacture.editors.EditorInputTypeRelance;
import fr.legrain.relancefacture.editors.EditorTypeRelance;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.ecran.ParamAfficheTiers;

public class PaSelectionLigneRelanceControlleur extends
		JPABaseControllerSWTStandard implements RetourEcranListener{
	static Logger logger = Logger.getLogger(PaSelectionLigneRelanceControlleur.class.getName());	

	private PaSelectionLigneRelance  vue = null;
	private TaLRelanceDAO dao = null;
	private TaLRelance taLRelance=null;
	private IObservableValue selectedLigneRelance;
//	private LgrDatabindingUtil lgrDatabindingUtil = new LgrDatabindingUtil();

	//private ModelGeneralObjet<IHMLRelance,TaLRelanceDAO,TaLRelance> modelFacture = null;
	private Object ecranAppelant = null;
	private Realm realm;
	private DataBindingContext dbc;
	private MapChangeListener changeListener = new MapChangeListener();
	private Class classModel = IHMLRelance.class;
	private String nomClass = this.getClass().getSimpleName();
	private String finDeLigne = "\r\n";
	private String separateur = ";";
	protected Boolean enregistreDirect=false; 
	
	private TypeVersionPublipostage typeVersion;
	
	private TaRelance masterEntity = null;
	private TaRelanceDAO masterDAO = null;
	
	public PaSelectionLigneRelanceControlleur(PaSelectionLigneRelance vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao =new TaLRelanceDAO(getEm());
//		modelFacture = new ModelGeneralObjet<IHMLRelance,TaLRelanceDAO,TaLRelance>
//		(dao,IHMLRelance.class);
		setVue(vue);

		vue.getShell().addShellListener(this);

		//Branchement action annuler : empeche la fermeture automatique de la fenetre sur ESC
		vue.getShell().addTraverseListener(new Traverse());

		initController();
		initSashFormWeight();
	}

	public PaSelectionLigneRelanceControlleur(PaSelectionLigneRelance vue) {
		this(vue,null);
	}

	
	private void initController()	{
		try {	
			
			setDaoStandard(dao);
			typeVersion=TypeVersionPublipostage.getInstance();
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
		if (getFocusCourantSWT().equals(vue.getTfTYPE_RELANCE()))
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
					if (getFocusCourantSWT().equals(vue.getTfTYPE_RELANCE())) {
						//permet de paramètrer l'affichage remplie ou non de l'aide

						PaTypeRelanceVersion paEcran = new PaTypeRelanceVersion(s2, SWT.NULL);
						PaTypeRelanceController paController = new PaTypeRelanceController(paEcran);

						editorCreationId = EditorTypeRelance.ID;
						editorInputCreation = new EditorInputTypeRelance();

						ParamAfficheTRelance paramAffiche = new ParamAfficheTRelance();
						paramAfficheAideRecherche.setJPQLQuery(new TaTRelanceDAO(getEm()).getJPQLQuery());
						paramAffiche.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAffiche.setEcranAppelant(paAideController);
						controllerEcranCreation = paController;
						parametreEcranCreation = paramAffiche;

						paramAfficheAideRecherche.setTypeEntite(TaTRelance.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_T_RELANCE);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfTYPE_RELANCE().getText());
						paramAfficheAideRecherche.setControllerAppelant(this);
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTRelance,TaTRelanceDAO,TaTRelance>(SWTTRelance.class,getEm()));
						paramAfficheAideRecherche.setTypeObjet(SWTTRelance.class);
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_T_RELANCE);
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
		try {
			VerrouInterface.setVerrouille(true);
			switch (dao.getModeObjet().getMode()) {
			case C_MO_INSERTION:
				break;
			case C_MO_EDITION:
					dao.annuler(taLRelance);
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
			ctrlTousLesChampsAvantEnregistrementSWT();
			new LgrDozerMapper< TaLRelance,IHMLRelance>().
				map( taLRelance,((IHMLRelance)selectedLigneRelance.getValue()));
			dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
			actRefresh();
//			initEtatBouton(true);
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
		try {
//			boolean continuer=true;
			TaParamPublipostageDAO daoParam=new TaParamPublipostageDAO();
			daoParam.setEm(daoParam.getEntityManager());
			final TaParamPublipostage param =daoParam.findInstance();

			
			//création d'un fichier par type de relance trouvé
			//On enregistre chaque ligne dans le fichier correspondant à son type de relance
			//puis on récupère, par rapport au type de relance, le fichier model et 
			//le fichier de correspondance
			//enregister la relance dans la table si nécessaire
			DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(
					this, C_COMMAND_GLOBAL_MODIFIER_ID);
			fireDeclencheCommandeController(e);
	
			List<TaLRelance> listeTriee = new ArrayList<TaLRelance>();
			List<TaLRelance> listeNonAcceptee = new ArrayList<TaLRelance>();
			for (TaLRelance taLRelance : masterEntity.getTaLRelances()) {
				if(taLRelance.getAccepte()){
					listeTriee.add(taLRelance);
				}
				else listeNonAcceptee.add(taLRelance);
			}
			Collections.sort(listeTriee,new TaLRelanceComparator());


			String repert= new File(Platform.getInstanceLocation().getURL().getFile()).getPath();
			String extensionFinale=TypeVersionPublipostage.getInstance().getExtensionFinale().get(param.getLogicielPublipostage());
			final List<ParamPublipostage> listeParamPubli =creationFichierRelance(listeTriee,repert,extensionFinale,param.getLogicielPublipostage());
			if(listeParamPubli!=null){
			for (TaLRelance taLRelanceNonAcceptee : listeNonAcceptee) {
				masterEntity.getTaLRelances().remove(taLRelanceNonAcceptee);
			}
			DeclencheCommandeControllerEvent e2 = new DeclencheCommandeControllerEvent(
					this, C_COMMAND_GLOBAL_ENREGISTRER_ID);
			fireDeclencheCommandeController(e2);
			final TypeVersionPublipostage typeVersion=TypeVersionPublipostage.getInstance();
			final String nonFichierFinal="publiFusion-"+LibDate.dateToString(new Date(),"")+extensionFinale;
			
			Thread t = new Thread() {
				
				@Override
				public void run() {



					if(typeVersion.getType().get(param.getLogicielPublipostage()).
							equals(TypeVersionPublipostage.TYPE_OPENOFFICE)){
						

						//PublipostageOOoWin32 pub = new PublipostageOOoWin32(listeParamPubli);
						AbstractPublipostageOOo pub = PublipostageOOoFactory.createPublipostageOOo(listeParamPubli);
						String pathOpenOffice = "";
						try {
							if(Platform.getOS().equals(Platform.OS_WIN32)){
								pathOpenOffice = WinRegistry.readString(
										WinRegistry.HKEY_LOCAL_MACHINE,
										WinRegistry.KEY_REGISTR_WIN_OPENOFFICE,
								"");
							} else if(Platform.getOS().equals(Platform.OS_LINUX)){
								pathOpenOffice = "/usr/bin/soffice";
							}
							else if(Platform.getOS().equals(Platform.OS_MACOSX)) {}
						}
						catch (Exception e3) {
							logger.error("",e3);
						}    

						pub.setCheminOpenOffice(new File(pathOpenOffice).getPath());
						pub.setPortOpenOffice("8100");
						pub.setNomFichierFinalFusionne(new File(nonFichierFinal).getPath());
						pub.demarrerServeur();
						pub.publipostages();

					}else if(typeVersion.getType().get(param.getLogicielPublipostage()).
							equals(TypeVersionPublipostage.TYPE_MSWORD)){
						PublipostageMsWord pub = new PublipostageMsWord(listeParamPubli);
						pub.setNomFichierFinalFusionne(new File(nonFichierFinal).getPath());
						pub.publipostages();
					}

				}//fin run
			}; //fin thread
			t.start();
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	
public List<ParamPublipostage> creationFichierRelance(List<TaLRelance>liste,String repertoireRelance,String extensionFichierFinal,String logiciel) {
		TaFactureDAO daoFacture=new TaFactureDAO(getEm());
		TaFacture facture =null;
		List<ParamPublipostage> listePubli=new LinkedList<ParamPublipostage>();
		if(liste!=null && liste.size()>0){
			FileWriter fw = null;
			BufferedWriter bw = null;
			String codeRelance="";
			String cheminFichier="";
			String cheminFichierFinal="";
			try {
				String codeTiers="";
				BigDecimal totalTtc1=new BigDecimal(0);
				Map<String, BigDecimal>listeTotaux = new HashMap<String, BigDecimal>();
				
				for (TaLRelance taLRelance : liste) {
					if(!taLRelance.getCodeTiers().equals(codeTiers)&& !codeTiers.equals("")){
						listeTotaux.put(codeTiers+codeRelance, totalTtc1);
						totalTtc1=new BigDecimal(0);
					}
					codeTiers=taLRelance.getCodeTiers();
					if(!codeRelance.equals(taLRelance.getTaTRelance().getCodeTRelance())){
						totalTtc1=new BigDecimal(0);
						codeRelance=taLRelance.getTaTRelance().getCodeTRelance();
					}
					totalTtc1=totalTtc1.add(taLRelance.getResteARegler());
				}
				listeTotaux.put(codeTiers+codeRelance, totalTtc1);
				codeRelance="";
				for (TaLRelance taLRelance : liste) {
					if(!codeRelance.equals(taLRelance.getTaTRelance().getCodeTRelance())){
						TaParamPublipostageDAO daoParam=new TaParamPublipostageDAO();
						daoParam.setEm(daoParam.getEntityManager());
						final TaParamPublipostage taParam =daoParam.findInstance();
						if(!taLRelance.getTaTRelance().getTypeLogiciel().equalsIgnoreCase(typeVersion.getType().get(taParam.getLogicielPublipostage()))){
							MessageDialog.openError(vue.getShell(), "Attention !", "Ce publipostage a été effectué avec un logiciel de type : "+taLRelance.getTaTRelance().getTypeLogiciel()
									+" qui n'est pas celui actuellement utilisé ("+taParam.getLogicielPublipostage()+")") ;
							return null;
						}
						//fin du fichier, on rempli un tableau de chemin de fichier
						if(bw!=null) bw.close();
						if(fw!=null) fw.close();
						cheminFichier=new File(repertoireRelance+"RelanceFac_"+taLRelance.getTaTRelance().
						getCodeTRelance()+"-"+LibDate.dateToString(taLRelance.getTaRelance().getDateRelance(),"")+".txt").getPath();
						cheminFichierFinal=new File(repertoireRelance+"RelanceFac_"+taLRelance.getTaTRelance().
						getCodeTRelance()+"-"+LibDate.dateToString(taLRelance.getTaRelance().getDateRelance(),"")+extensionFichierFinal).getPath();
						fw = new FileWriter(cheminFichier);
						ParamPublipostage param = new ParamPublipostage();
						param.setCheminFichierDonnees(cheminFichier);
						if(taLRelance.getTaTRelance().getDefaut()==1){
							UtilWorkspace uw =new UtilWorkspace();
							uw.openProjectLocationPath();
							param.setCheminFichierModelLettre(new File(uw.openProjectLocationPath()+"/ModelesRelance"+"/"+taLRelance.getTaTRelance().getCheminModelRelance()).getPath());
							param.setCheminFichierMotCle(new File(fr.legrain.relancefacture.divers.Const.pathRepertoireModelesRelances()+"/"+taLRelance.getTaTRelance().getCheminCorrespRelance()).getPath());
						}
						else{
							param.setCheminFichierModelLettre(new File(taLRelance.getTaTRelance().getCheminModelRelance()).getPath());
							param.setCheminFichierMotCle(new File(taLRelance.getTaTRelance().getCheminCorrespRelance()).getPath());
						}
						
						param.setCheminFichierFinal(cheminFichierFinal);
						param.setCheminRepertoireFinal(repertoireRelance);
						listePubli.add(param);
						bw = new BufferedWriter(fw);
						fw.write("codeTiers;dateDocument;dateEcheance;nomTiers;codeDocument;adresse1;adresse2;adresse3;codepostal;ville;pays;netTTC;resteARegler" +
								";codeTCivilite;codeTEntite;nomEntreprise;prenomTiers;totalTtc"+separateur+finDeLigne);
					}
					codeRelance=taLRelance.getTaTRelance().getCodeTRelance();
					facture=daoFacture.findByCode(taLRelance.getCodeDocument());
					fw.write(taLRelance.getCodeTiers()+separateur
							+LibDate.dateToString(facture.getDateDocument())+separateur
							+LibDate.dateToString(taLRelance.getDateEcheance())+separateur
							+facture.getTaTiers().getNomTiers()+separateur
							+taLRelance.getCodeDocument()+separateur
							+facture.getTaInfosDocument().getAdresse1()+separateur
							+facture.getTaInfosDocument().getAdresse2()+separateur
							+facture.getTaInfosDocument().getAdresse3()+separateur
							+facture.getTaInfosDocument().getCodepostal()+separateur
							+facture.getTaInfosDocument().getVille()+separateur
							+facture.getTaInfosDocument().getPays()+separateur
							+taLRelance.getNetTTC()+separateur
							+taLRelance.getResteARegler()+separateur							
							+facture.getTaInfosDocument().getCodeTCivilite()+separateur
							+facture.getTaInfosDocument().getCodeTEntite()+separateur
							+facture.getTaInfosDocument().getNomEntreprise()+separateur
							+facture.getTaInfosDocument().getPrenomTiers()+separateur
							//+"0"+separateur);
							+listeTotaux.get(taLRelance.getCodeTiers()+codeRelance)+separateur);
					fw.write(finDeLigne);
					
				}

				if(bw!=null) bw.close();
				if(fw!=null) fw.close();
			} catch(IOException ioe){
				logger.error("",ioe);
			} 
			finally{
				try {
					if(bw!=null) bw.close();
					if(fw!=null) fw.close();

				} catch (Exception e) {}
			}
		}
		return listePubli;

	}
	
	@Override
	protected void actInserer() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void actModifier() throws Exception {
		try {
			DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(
					this, C_COMMAND_GLOBAL_MODIFIER_ID);
			fireDeclencheCommandeController(e);
		
			if(selectedLigneRelance!=null && selectedLigneRelance.getValue()!=null){
				if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
					for (TaLRelance ligne : masterEntity.getTaLRelances()) {
						if(ligne.getCodeDocument().equals(((IHMLRelance) selectedLigneRelance.getValue()).getCodeDocument()))
							taLRelance =ligne;	
					}

					dao.modifier(taLRelance);
					initEtatBouton(true);
				}
			}
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		}
	}

	/**
	 * Creation des objet pour l'interface, a partir de l'entite principale.<br>
	 * Ici : creation d'une liste de ligne pour l'IHM, a partir de la liste des lignes contenue dans l'entite facture.
	 * @return
	 */
	public List<IHMLRelance> IHMmodel() {
		LinkedList<TaLRelance> ldao = new LinkedList<TaLRelance>();
		LinkedList<IHMLRelance> lswt = new LinkedList<IHMLRelance>();
		
		if(masterEntity!=null && !masterEntity.getTaLRelances().isEmpty()) {

			ldao.addAll(masterEntity.getTaLRelances());

			LgrDozerMapper<TaLRelance,IHMLRelance> mapper = new LgrDozerMapper<TaLRelance,IHMLRelance>();
			for (TaLRelance o : ldao) {
				IHMLRelance t = null;
				t = (IHMLRelance) mapper.map(o, IHMLRelance.class);
				lswt.add(t);
			}
			
		}
		return lswt;
	}
	
	@Override
	protected void actRefresh() throws Exception {
		vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
		try{	
			String codeActuel="";
			if(taLRelance!=null)
				codeActuel =taLRelance.getCodeDocument();				
			WritableList writableListFacture = new WritableList(IHMmodel(), IHMLRelance.class);
			getTableViewerStandard().setInput(writableListFacture);			
			if(codeActuel!="") {
				getTableViewerStandard().setSelection(new StructuredSelection(recherche(Const.C_CODE_DOCUMENT
						, codeActuel)),true);
			}
			changementDeSelection();
			initTotaux();
			initEtatBouton(true);

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
		if(masterEntity!=null){
			for (TaLRelance lRelance : masterEntity.getTaLRelances()) {
				if (lRelance.getAccepte()){
					totalTTC=totalTTC.add(lRelance.getResteARegler());
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

	public void bind(PaSelectionLigneRelance paSelectionLigneRelance){
		try {
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());
			setTableViewerStandard( new LgrTableViewer(vue.getGrille()));
			String[] titreColonnes =new String[] {"Code document","Date échéance","Code tiers","Nom ","Net TTC","Reste à régler","type relance","libellé relance","Accepté"};
			getTableViewerStandard().createTableCol(vue.getGrille(),titreColonnes,
					new String[] {"100","80","60","150","70","70","50","120","30"},5);
			String[] listeChamp = new String[] {"codeDocument","dateEcheance","codeTiers","nomTiers","netTTC","resteARegler","codeTRelance","libelleTRelance","accepte"};
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
						getTableViewerStandard().setSelection(new StructuredSelection(recherche(Const.C_CODE_DOCUMENT
								, ((IHMLRelance)event.getElement()).getCodeDocument())),true);
						if(selectedLigneRelance.getValue()!=null){
							actModifier();
							((IHMLRelance)selectedLigneRelance.getValue()).setAccepte(event.getChecked());
							validateUIField(Const.C_ACCEPTE,event.getChecked());
							actEnregistrer();
							initTotaux();
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
				if(!((IHMLRelance)element).getAccepte())
					return true;
				return false;
			}
			
			@Override
			public boolean isChecked(Object element) {
				// TODO Auto-generated method stub
				if(((IHMLRelance)element).getAccepte())
					return true;
				return false;
			}
		});

//		affectationReglementControllerMini.bind();
		vue.getGrille().addMouseListener(
				new MouseAdapter() {

					public void mouseDoubleClick(MouseEvent e) {
						String idEditor = TypeDoc.getInstance().getEditorDoc()
								.get(TypeDoc.TYPE_FACTURE);
						String valeurIdentifiant = ((IHMLRelance) selectedLigneRelance
								.getValue()).getCodeDocument();
						ouvreDocument(valeurIdentifiant, idEditor);
					}

				});
		dbc = new DataBindingContext(realm);

		dbc.getValidationStatusMap().addMapChangeListener(changeListener);
		setDbcStandard(dbc);
		selectedLigneRelance = ViewersObservables.observeSingleSelection(getTableViewerStandard());
		bindingFormMaitreDetail(dbc, realm, selectedLigneRelance,classModel);
		selectedLigneRelance.addChangeListener(new IChangeListener() {

			public void handleChange(ChangeEvent event) {
				changementDeSelection();
			}

		});
//		vue.getTfACCEPTE().setEnabled(getEnregistreDirect());
		vue.getTfTYPE_RELANCE().setEnabled(getEnregistreDirect());

		changementDeSelection();
		initTotaux();
		initEtatBouton(true);
	} catch(Exception e) {
		logger.error("",e);
		vue.getLaMessage().setText(e.getMessage());
	}
}

private void changementDeSelection() {
	try {
		if(selectedLigneRelance==null ||selectedLigneRelance.getValue()==null)
			getTableViewerStandard().selectionGrille(0);
		if(selectedLigneRelance!=null && selectedLigneRelance.getValue()!=null){
			if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
				for (TaLRelance ligne : masterEntity.getTaLRelances()) {
					if(ligne.getCodeDocument().equals(((IHMLRelance) selectedLigneRelance.getValue()).getCodeDocument()))
						taLRelance =ligne;	
				}
			}
		}
//		if(paVisuReglementControlleur!=null){
//			if(taDocument==null)
//				paVisuReglementControlleur.actRefresh(null);
//			else
//				paVisuReglementControlleur.actRefresh(taDocument);
//		}
		initTotaux();
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

		mapActions.put(vue.getBtnAnnuler(), C_COMMAND_GLOBAL_ANNULER_ID);
		mapActions.put(vue.getBtnModifier(), C_COMMAND_GLOBAL_MODIFIER_ID);
		mapActions.put(vue.getBtnImprimer(), C_COMMAND_GLOBAL_IMPRIMER_ID);
		mapActions.put(vue.getBtnEnregister(), C_COMMAND_GLOBAL_ENREGISTRER_ID);
		mapActions.put(vue.getBtnFermer(), C_COMMAND_GLOBAL_FERMER_ID);
		mapActions.put(vue.getBtnSupprimer(), C_COMMAND_GLOBAL_SUPPRIMER_ID);
		mapActions.put(vue.getBtnInserer(), C_COMMAND_GLOBAL_INSERER_ID);
		
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
		mapComposantChamps.put(vue.getTfCODE_DOCUMENT(), Const.C_CODE_DOCUMENT);
		mapComposantChamps.put(vue.getTfCODE_TIERS(), Const.C_CODE_TIERS);
//		mapComposantChamps.put(vue.getTfACCEPTE(), Const.C_ACCEPTE);
		mapComposantChamps.put(vue.getTfTYPE_RELANCE(), Const.C_CODE_T_RELANCE);

		vue.getTfCODE_DOCUMENT().setEnabled(false);
		vue.getTfCODE_TIERS().setEnabled(false);
		
		
//		listeComposantFocusable.add(vue.getTfACCEPTE());
		listeComposantFocusable.add(vue.getTfTYPE_RELANCE());
		
		listeComposantFocusable.add(vue.getBtnEnregister());
		listeComposantFocusable.add(vue.getBtnAnnuler());
		listeComposantFocusable.add(vue.getBtnModifier());
		listeComposantFocusable.add(vue.getBtnImprimer());
		listeComposantFocusable.add(vue.getBtnFermer());
		
		if(mapInitFocus == null) 
			mapInitFocus = new LinkedHashMap<ModeObjet.EnumModeObjet,Control>();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION,vue.getTfTYPE_RELANCE());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION,vue.getTfTYPE_RELANCE());
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
			if(param instanceof ParamAfficheRelanceFacture){
				setEnregistreDirect(((ParamAfficheRelanceFacture)param).getEnregistreDirect());
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

	public void setVue(PaSelectionLigneRelance vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	public TaRelance getMasterEntity() {
		return masterEntity;
	}

	public void setMasterEntity(TaRelance masterEntity) {
		this.masterEntity = masterEntity;
	}

	public TaRelanceDAO getMasterDAO() {
		return masterDAO;
	}

	public void setMasterDAO(TaRelanceDAO masterDAO) {
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
		vue.getBtnFermer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_FERMER));
		vue.getBtnImprimer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_IMPRIMER));
		vue.getBtnInserer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_INSERER));
		vue.getBtnModifier().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_MODIFIER));
		vue.getBtnSupprimer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_SUPPRIMER));
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

	public class TaLRelanceComparator implements Comparator<TaLRelance> {
		 
	    public int compare(TaLRelance taLRelance1, TaLRelance taLRelance2) {
	        int premier = taLRelance1.getTaTRelance().getOrdreTRelance().
	        compareTo(taLRelance2.getTaTRelance().getOrdreTRelance());
	        
	        int deuxieme = taLRelance1.getCodeTiers().compareTo(taLRelance2.getCodeTiers());
	        
	        int troisieme = taLRelance1.getDateEcheance().compareTo(taLRelance2.getDateEcheance());

	        int compared = premier;
	        if (compared == 0) {
	            compared = deuxieme;
	            if (compared == 0) {
	                compared = troisieme;
	            }
	        }
	 
	        return compared;
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
		String validationContext = "RELANCE";
		try {
			IStatus s = null;
			if (nomChamp.equals(Const.C_CODE_DOCUMENT) ) {
				s=new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
			}
			if (nomChamp.equals(Const.C_CODE_TIERS) ) {
				s=new Status(Status.OK,gestComBdPlugin.PLUGIN_ID,"OK");
			}
				if (nomChamp.equals(Const.C_CODE_T_RELANCE) ) {
					TaTRelanceDAO daoLocal = new TaTRelanceDAO(getEm());
					TaTRelance f = new TaTRelance();
					PropertyUtils.setSimpleProperty(f, nomChamp, value);
					s = daoLocal.validate(f, nomChamp, "T_RELANCE");
					if (s.getSeverity() != IStatus.ERROR) {
						f=daoLocal.findByCode(f.getCodeTRelance());
						taLRelance.setTaTRelance(f);
					}
					daoLocal = null;
				} 
				else
				if (nomChamp.equals(Const.C_ACCEPTE) ) {
					TaLRelance f = new TaLRelance();
					PropertyUtils.setSimpleProperty(f, nomChamp, value);
					s = dao.validate(f, nomChamp, "RELANCE");
					if (s.getSeverity() != IStatus.ERROR) {
						taLRelance.setAccepte(f.getAccepte());
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
		List<IHMLRelance> model=IHMmodel();
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
		int premierComposite = 25;
		int secondComposite = 75;
//		if(!getEnregistreDirect()){
//			premierComposite = 0;
//			secondComposite = 100;
//		}
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
					if(getFocusAvantAideSWT().equals(vue.getTfTYPE_RELANCE())){
						TaTRelance entity = null;
						TaTRelanceDAO dao = new TaTRelanceDAO(getEm());
						entity = dao.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
						dao = null;
						validateUIField(Const.C_CODE_T_RELANCE,entity.getCodeTRelance());
						vue.getTfTYPE_RELANCE().setText(entity.getCodeTRelance());
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
}

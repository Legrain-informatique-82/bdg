package fr.legrain.creationDocMultipleTiers.controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
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
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
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

import creationdocmultipletiers.Activator;
import fr.legrain.creationDocMultipleTiers.divers.DeclencheInitEtatBoutonControllerEvent;
import fr.legrain.creationDocMultipleTiers.divers.IDeclencheInitEtatBoutonControllerListener;
import fr.legrain.creationDocMultipleTiers.divers.ParamAffichecreationDocMultiple;
import fr.legrain.creationDocMultipleTiers.divers.ViewerSupportLocal;
import fr.legrain.creationDocMultipleTiers.ecrans.PaSelectionLigneMultiTiers;
import fr.legrain.document.divers.ModelRecupDocumentCreationDoc;
import fr.legrain.document.divers.ModelTiersCreationDoc;
import fr.legrain.document.divers.TaCreationDoc;
import fr.legrain.documents.dao.TaAcompte;
import fr.legrain.documents.dao.TaAvisEcheance;
import fr.legrain.documents.dao.TaAvoir;
import fr.legrain.documents.dao.TaBoncde;
import fr.legrain.documents.dao.TaBonliv;
import fr.legrain.documents.dao.TaDevis;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaPrelevement;
import fr.legrain.documents.dao.TaProforma;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.gestCom.Module_Tiers.SWTTiers;
import fr.legrain.gestCom.gestComBd.gestComBdPlugin;
import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.DeclencheCommandeControllerEvent;
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
import fr.legrain.libMessageLGR.LgrMess;
import fr.legrain.tiers.dao.TaParamCreeDocTiers;
import fr.legrain.tiers.dao.TaParamCreeDocTiersDAO;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.ecran.ParamAfficheTiers;
import fr.legrain.tiers.editor.TiersMultiPageEditor;

public class PaSelectionMultiTiersControlleur extends
		JPABaseControllerSWTStandard implements RetourEcranListener{
	static Logger logger = Logger.getLogger(PaSelectionMultiTiersControlleur.class.getName());	

	static String LIBELLE_GRILLE="Liste des tiers à prendre en compte dans le(s) document(s) à créer";
	private PaSelectionLigneMultiTiers  vue = null;
	private IObservableValue selectedLignePublipostage;

	private Object ecranAppelant = null;
	private Realm realm;
	private DataBindingContext dbc;
	private MapChangeListener changeListener = new MapChangeListener();
	private Class classModel = SWTTiers.class;
	private String nomClass = this.getClass().getSimpleName();
	private String finDeLigne = "\r\n";
	private String separateur = ";";
	protected Boolean enregistreDirect=false; 
	private TaTiersDAO dao=null;
	private TaTiers taTiers=null;
	private ModelTiersCreationDoc masterModel = null;
	private ModelRecupDocumentCreationDoc masterModelFinal = null;
	private PaSelectionLigneDocControlleur paSelectionLigneDocControlleur=null;
	private IDocumentTiers masterDocument = null;
	
	private Date masterDateDeb=null;
	private Date masterDateFin=null;
	private String selectedTypeCreation = "";
	private String selectedTypeSelection = "";
	private TaParamCreeDocTiers paramGen = new TaParamCreeDocTiers();
	private TaParamCreeDocTiersDAO daoParam = null;
	private String libelle="";
	
	
	
	public static final String C_COMMAND_DOCUMENT_TOUT_COCHER_ID = "fr.legrain.Publipostage.toutCocher";
	protected HandlerToutCocher handlerToutCocher = new HandlerToutCocher();
	
	public static final String C_COMMAND_DOCUMENT_TOUT_DECOCHER_ID = "fr.legrain.Publipostage.toutDeCocher";
	protected HandlerToutDeCocher handlerToutDeCocher = new HandlerToutDeCocher();
	
	
	public PaSelectionMultiTiersControlleur(PaSelectionLigneMultiTiers vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao =new TaTiersDAO(getEm());
		daoParam = new TaParamCreeDocTiersDAO(getEm());
		setVue(vue);

		vue.getShell().addShellListener(this);
		
		//Branchement action annuler : empeche la fermeture automatique de la fenetre sur ESC
		vue.getShell().addTraverseListener(new Traverse());

		initController();
		initSashFormWeight();
	}

	public PaSelectionMultiTiersControlleur(PaSelectionLigneMultiTiers vue) {
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
	}
	@Override
	protected void actAide() throws Exception {
		// TODO Auto-generated method stub
		actAide(null);
	}

	@Override
	protected boolean aideDisponible() {
		boolean result = false;
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
		try {
			
			vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
		//prendre tout ce qu'il y a dans la grille et l'envoyer dans l'autre grille
		if(masterModelFinal==null){
			masterModelFinal=new ModelRecupDocumentCreationDoc(dao,IDocumentTiers.class);
			masterModelFinal.setListeEntity(new LinkedList<IDocumentTiers>());
		}
		LinkedList<IDocumentTiers> list =new LinkedList<IDocumentTiers>();
		LinkedList<IDocumentTiers> listTemp =new LinkedList<IDocumentTiers>();
		LinkedList<TaCreationDoc> listCreation =new LinkedList<TaCreationDoc>();
		
		for (Object tiers : masterModel.getListeObjet()) {
			if(((SWTTiers)tiers).getAccepte()){
				listTemp.clear();
				listTemp=masterModelFinal.remplirListe( masterDateDeb, masterDateFin, ((SWTTiers)tiers).getCodeTiers(),
						getSelectedTypeSelection(), getSelectedTypeCreation(),masterDocument);
				for (IDocumentTiers doc : listTemp) {
					list.add(doc);
				}
			}
		}
		

		Calendar lundiSuivant = Calendar.getInstance();
		Calendar lundi15Suivant = Calendar.getInstance();
		Calendar moisSuivant = Calendar.getInstance();
		Calendar jourSuivant = Calendar.getInstance();
		Calendar decadeSuivant = Calendar.getInstance();
		
		boolean changement=false;
		int compteur=0;
		int compteurFacture=1;
		String codeTiersEnCours="";
		int ttcEnCours=0;
		TaCreationDoc c=null;
		String libelleInit=initialiseLibelleDoc(getSelectedTypeSelection());
		libelle=libelleInit;
		String libelleSupp=" (saisie en TTC)";

		List<IDocumentTiers> listeDocAvecEscompte=new LinkedList<IDocumentTiers>();
		TaParamCreeDocTiers paramTiers=null;
		for (IDocumentTiers doc1 : list) {
			if(doc1.getTxRemTtcDocument()!=null && doc1.getTxRemTtcDocument().signum()!=0){
				//on traite le document dans un deuxième temps
				listeDocAvecEscompte.add(doc1);
			}else {//si on traite le document maintenant
			if(!codeTiersEnCours.equals(doc1.getTaTiers().getCodeTiers())){
				//récupérer les param de chaque tiers
				paramTiers=paramGen;
				if(paramGen.getAppliquerATous())
					paramTiers=paramGen;
				else{ 
					try {
						List<TaParamCreeDocTiers>liste=daoParam.findByCodeTypeDoc(doc1.getTaTiers().getCodeTiers(),selectedTypeSelection);
					if(liste!=null && liste.size()>0)
						paramTiers=liste.get(0);
					} catch (Exception e) {
						paramTiers=paramGen;
					}
				}
				if(paramTiers==null)paramTiers=paramGen;
					changement=true;
					lundiSuivant.setTime(getMasterDateDeb());//initialise avec la date début
					lundiSuivant.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);//indique le premier jour de la semaine
					lundiSuivant.add(Calendar.DAY_OF_WEEK, 7);//initialise le prochain lundi

					
					lundi15Suivant.setTime(getMasterDateDeb());//initialise avec la date début
					lundi15Suivant.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);//indique le premier jour de la semaine
					lundi15Suivant.add(Calendar.DAY_OF_WEEK, 14);//initialise le prochain lundi
					
					jourSuivant.setTime(getMasterDateDeb());//initialise avec la date début
					jourSuivant.add(Calendar.DAY_OF_WEEK, paramTiers.getNbJours());//initialise le prochain jour en fonction d'un nb de jour donné
					
					
					Calendar moisSuivant1 = Calendar.getInstance();
					moisSuivant.setTime(getMasterDateDeb());//initialise avec la date début
					moisSuivant.set(Calendar.DAY_OF_MONTH,1);
					moisSuivant.setTime(LibDate.incrementDate(moisSuivant.getTime(), 0, 1, 0));
					
					decadeSuivant.setTime(getMasterDateDeb());//initialise avec la date début
					decadeSuivant.set(Calendar.DAY_OF_MONTH,1);
					if(LibConversion.stringToInteger(LibDate.getJour(decadeSuivant.getTime()))>20){
						decadeSuivant.set(Calendar.DAY_OF_MONTH,1);
						decadeSuivant.setTime(LibDate.incrementDate(decadeSuivant.getTime(), 0, 1, 0));
					}
					else
						decadeSuivant.add(Calendar.DAY_OF_MONTH, 10);
			}
			if(doc1.getTtc().compareTo(ttcEnCours )!=0){
				if(!changement){
					changement=true;
					lundiSuivant.setTime(getMasterDateDeb());//initialise avec la date début
					lundiSuivant.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);//indique le premier jour de la semaine
					lundiSuivant.add(Calendar.DAY_OF_WEEK, 7);//initialise le prochain lundi

					
					lundi15Suivant.setTime(getMasterDateDeb());//initialise avec la date début
					lundi15Suivant.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);//indique le premier jour de la semaine
					lundi15Suivant.add(Calendar.DAY_OF_WEEK, 14);//initialise le prochain lundi
					
					jourSuivant.setTime(getMasterDateDeb());//initialise avec la date début
					jourSuivant.add(Calendar.DAY_OF_WEEK, paramTiers.getNbJours());//initialise le prochain jour en fonction d'un nb de jour donné
					
					
					Calendar moisSuivant1 = Calendar.getInstance();
					moisSuivant.setTime(getMasterDateDeb());//initialise avec la date début
					moisSuivant.set(Calendar.DAY_OF_MONTH,1);
					moisSuivant.setTime(LibDate.incrementDate(moisSuivant.getTime(), 0, 1, 0));
					
					decadeSuivant.setTime(getMasterDateDeb());//initialise avec la date début
					if(LibConversion.stringToInteger(LibDate.getJour(decadeSuivant.getTime()))>20){
						decadeSuivant.set(Calendar.DAY_OF_MONTH,1);
						decadeSuivant.setTime(LibDate.incrementDate(decadeSuivant.getTime(), 0, 1, 0));
					}
					else
						decadeSuivant.add(Calendar.DAY_OF_MONTH, 10);
				}
		
			}
				if(paramTiers.getTiers().compareTo(1)==0){
					if(changement){
						if(doc1.getTtc().compareTo(new Integer(1))==0)
							libelle=libelleInit+" (saisie en TTC)";
						else libelle=libelleInit;
					//création d'un nouveau document
					c=new TaCreationDoc();
					c.setCodeDocument(getSelectedTypeCreation()+" :"+compteurFacture);
					c.setLibelleDocument(libelle);
					c.setCodeTiers(doc1.getTaTiers().getCodeTiers());
					c.setAccepte(true);
					listCreation.add(c);
					codeTiersEnCours=doc1.getTaTiers().getCodeTiers();
					ttcEnCours=doc1.getTtc();
					changement=false;
					compteurFacture++;
					compteur=0;
					}
					c.getListeDoc().add(doc1);
					compteur++;
				}
				if(paramTiers.getDocument().compareTo(1)==0){
					if(changement){
						if(doc1.getTtc().compareTo(new Integer(1))==0)
							libelle=libelleInit+" (saisie en TTC)";
						else libelle=libelleInit;
					//création d'un nouveau document
					c=new TaCreationDoc();
					c.setCodeDocument(getSelectedTypeCreation()+" :"+compteurFacture);
					c.setLibelleDocument(libelle);
					c.setCodeTiers(doc1.getTaTiers().getCodeTiers());
					c.setAccepte(true);
					listCreation.add(c);
					codeTiersEnCours=doc1.getTaTiers().getCodeTiers();
					ttcEnCours=doc1.getTtc();
					changement=true;
					compteurFacture++;
					compteur=0;
					}
					c.getListeDoc().add(doc1);
					compteur++;
				}
				if(paramTiers.getSemaine().compareTo(1)==0){
					if(LibDate.compareTo(lundiSuivant.getTime(),doc1.getDateDocument())<=0||
							changement){	
						if(doc1.getTtc().compareTo(new Integer(1))==0)
							libelle=libelleInit+" (saisie en TTC)";
						else libelle=libelleInit;
						//création d'un nouveau document
						c=new TaCreationDoc();
						c.setCodeDocument(getSelectedTypeCreation()+" :"+compteurFacture);
						c.setLibelleDocument(libelle);
						c.setCodeTiers(doc1.getTaTiers().getCodeTiers());
						c.setAccepte(true);
						listCreation.add(c);
						codeTiersEnCours=doc1.getTaTiers().getCodeTiers();
						ttcEnCours=doc1.getTtc();
						changement=false;
						compteurFacture++;
						compteur=0;
						while(LibDate.compareTo(lundiSuivant.getTime(),doc1.getDateDocument())<=0)
							lundiSuivant.add(Calendar.DAY_OF_WEEK, 7);
//						if(lundiSuivant.getTime().before(doc1.getDateDocument()))lundiSuivant.add(Calendar.DAY_OF_WEEK, 7);
						}
						c.getListeDoc().add(doc1);
						compteur++;
				}
				if(paramTiers.getDeuxSemaines().compareTo(1)==0){
					if(LibDate.compareTo(lundi15Suivant.getTime(),doc1.getDateDocument())<=0||
							changement){	
						if(doc1.getTtc().compareTo(new Integer(1))==0)
							libelle=libelleInit+" (saisie en TTC)";
						else libelle=libelleInit;
						//création d'un nouveau document
						c=new TaCreationDoc();
						c.setCodeDocument(getSelectedTypeCreation()+" :"+compteurFacture);
						c.setLibelleDocument(libelle);
						c.setCodeTiers(doc1.getTaTiers().getCodeTiers());
						c.setAccepte(true);
						listCreation.add(c);
						codeTiersEnCours=doc1.getTaTiers().getCodeTiers();
						ttcEnCours=doc1.getTtc();
						changement=false;
						compteurFacture++;
						compteur=0;
						while(LibDate.compareTo(lundi15Suivant.getTime(),doc1.getDateDocument())<=0)
							lundi15Suivant.add(Calendar.DAY_OF_WEEK,14);
//						if(lundi15Suivant.getTime().before(doc1.getDateDocument()))	lundi15Suivant.add(Calendar.DAY_OF_WEEK, 14);
						}
						c.getListeDoc().add(doc1);
						compteur++;
				}
				if(paramTiers.getDecade().compareTo(1)==0){
					if(LibDate.compareTo(decadeSuivant.getTime(), doc1.getDateDocument())<=0||
							changement){	
						if(doc1.getTtc().compareTo(new Integer(1))==0)
							libelle=libelleInit+" (saisie en TTC)";
						else libelle=libelleInit;
						//création d'un nouveau document
						c=new TaCreationDoc();
						c.setCodeDocument(getSelectedTypeCreation()+" :"+compteurFacture);
						c.setLibelleDocument(libelle);
						c.setCodeTiers(doc1.getTaTiers().getCodeTiers());
						c.setAccepte(true);
						listCreation.add(c);
						codeTiersEnCours=doc1.getTaTiers().getCodeTiers();
						ttcEnCours=doc1.getTtc();
						changement=false;
						compteurFacture++;
						compteur=0;
						while(LibDate.compareTo(decadeSuivant.getTime(),doc1.getDateDocument())<=0)		
							if(LibConversion.stringToInteger(LibDate.getJour(decadeSuivant.getTime()))>20){
								decadeSuivant.set(Calendar.DAY_OF_MONTH,1);
								decadeSuivant.setTime(LibDate.incrementDate(decadeSuivant.getTime(), 0, 1, 0));
							}
							else
								decadeSuivant.add(Calendar.DAY_OF_WEEK, 10);
						}
						c.getListeDoc().add(doc1);
						compteur++;
				}
				if(paramTiers.getMois().compareTo(1)==0){
					if(LibDate.compareTo(moisSuivant.getTime(),doc1.getDateDocument())<=0||
							changement){	
						if(doc1.getTtc().compareTo(new Integer(1))==0)
							libelle=libelleInit+" (saisie en TTC)";
						else libelle=libelleInit;
						//création d'un nouveau document
						c=new TaCreationDoc();
						c.setCodeDocument(getSelectedTypeCreation()+" :"+compteurFacture);
						c.setLibelleDocument(libelle);
						c.setCodeTiers(doc1.getTaTiers().getCodeTiers());
						c.setAccepte(true);
						listCreation.add(c);
						codeTiersEnCours=doc1.getTaTiers().getCodeTiers();
						ttcEnCours=doc1.getTtc();
						changement=false;
						compteurFacture++;
						compteur=0;
						while(LibDate.compareTo(moisSuivant.getTime(),doc1.getDateDocument())<=0)
							moisSuivant.setTime(LibDate.incrementDate(moisSuivant.getTime(), 0, 1, 0));
//						if(moisSuivant.getTime().before(doc1.getDateDocument()))
//							moisSuivant.setTime(LibDate.incrementDate(moisSuivant.getTime(), 0, 1, 0));
						}
						c.getListeDoc().add(doc1);
						compteur++;
				}
				if(paramTiers.getxJours().compareTo(1)==0){
					if(LibDate.compareTo(jourSuivant.getTime(),doc1.getDateDocument())<=0||
							changement){		
						if(doc1.getTtc().compareTo(new Integer(1))==0)
							libelle=libelleInit+" (saisie en TTC)";
						else libelle=libelleInit;
						//création d'un nouveau document
						c=new TaCreationDoc();
						c.setCodeDocument(getSelectedTypeCreation()+" :"+compteurFacture);
						c.setLibelleDocument(libelle);
						c.setCodeTiers(doc1.getTaTiers().getCodeTiers());
						c.setAccepte(true);
						listCreation.add(c);
						codeTiersEnCours=doc1.getTaTiers().getCodeTiers();
						ttcEnCours=doc1.getTtc();
						changement=false;
						compteurFacture++;
						compteur=0;
						while(LibDate.compareTo(jourSuivant.getTime(),doc1.getDateDocument())<=0)
							jourSuivant.add(Calendar.DAY_OF_WEEK, paramTiers.getNbJours());
//						if(jourSuivant.getTime().before(doc1.getDateDocument()))
//							jourSuivant.add(Calendar.DAY_OF_WEEK, paramTiers.getNbJours());
						}
						c.getListeDoc().add(doc1);
						compteur++;
				}
				

		}//fin si on traite le document maintenant	
		}
		changement=true;
		for (IDocumentTiers doc1 : listeDocAvecEscompte) {
				if(changement){
				//création d'un nouveau document
				c=new TaCreationDoc();
				c.setCodeDocument(getSelectedTypeCreation()+" :"+compteurFacture);
				c.setLibelleDocument(libelleInit+" (avec escompte)");
				c.setCodeTiers(doc1.getTaTiers().getCodeTiers());
				c.setAccepte(true);
				listCreation.add(c);
				codeTiersEnCours=doc1.getTaTiers().getCodeTiers();
				changement=true;
				compteurFacture++;
				compteur=0;
				}
				c.getListeDoc().add(doc1);
				compteur++;
			}


		masterModelFinal.setListeEntity(listCreation);
		getPaSelectionLigneDocControlleur().getModelCreation().setListeEntity(listCreation);
		getPaSelectionLigneDocControlleur().setSelectedTypeCreation(selectedTypeCreation);
		getPaSelectionLigneDocControlleur().setSelectedTypeSelection(selectedTypeSelection);
		DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(
				this, C_COMMAND_GLOBAL_REFRESH_ID);
		fireDeclencheCommandeController(e);
		}catch (Exception e) {
			logger.error("",e);
		}
		finally{
			vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
		}
	}

	@Override
	protected void actFermer() throws Exception {
		List<SWTTiers> listeTemp=new LinkedList<SWTTiers>();
		//(controles de sortie et fermeture de la fenetre) => onClose()
		for (Object tiers : masterModel.getListeObjet()) {
			if(!((SWTTiers)tiers).getAccepte())listeTemp.add(((SWTTiers)tiers));				
		}
		masterModel.getListeObjet().removeAll(listeTemp);
		actRefresh();
	}

	@Override
	protected void actImprimer() throws Exception {
		if(masterModelFinal!=null && masterModelFinal.getListeEntity()!=null){
			masterModelFinal.getListeEntity().clear();
			masterModelFinal.getListeObjet().clear();
		}
		actEnregistrer();
	}

	
	@Override
	protected void actInserer() throws Exception {
		// TODO Auto-generated method stub
		for (Object tiers : masterModel.getListeObjet()) {
			((SWTTiers)tiers).setAccepte(!((SWTTiers)tiers).getAccepte());
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
			vue.getLaTitreGrille().setText(LIBELLE_GRILLE+" ("+masterModel.getListeObjet().size()+")");
			fireDeclencheInitEtatBoutonController(new DeclencheInitEtatBoutonControllerEvent(this,null));
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

	public void bind(PaSelectionLigneMultiTiers paSelectionLignePublipostage){
		try {
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());
			setTableViewerStandard( new LgrTableViewer(vue.getGrille()));
			String[] titreColonnes =new String[] {"code Tiers","nom","prenom","nom Entreprise","code postal","ville","accepte"};
			getTableViewerStandard().createTableCol(vue.getGrille(),titreColonnes,
					new String[] {"150","150","80","150","80","100","0"},0);
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

		vue.getPaFomulaire().setVisible(false);
		listeComposantFocusable.add(vue.getBtnAnnuler());
		listeComposantFocusable.add(vue.getBtnInserer());//Inverser		
		listeComposantFocusable.add(vue.getBtnModifier());//tout cocher
		listeComposantFocusable.add(vue.getBtnEnregister());//Envoyer dans l'autre grille
		listeComposantFocusable.add(vue.getBtnSupprimer());//tout Décocher
		listeComposantFocusable.add(vue.getBtnImprimer());
		listeComposantFocusable.add(vue.getBtnFermer());
		
		

		
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

			if(param.getTitreFormulaire()!=null){
				vue.getLaTitreFormulaire().setText(param.getTitreFormulaire());
			} else {
				vue.getLaTitreFormulaire().setText(ParamAfficheTiers.C_TITRE_FORMULAIRE);
			}


				vue.getLaTitreGrille().setText(LIBELLE_GRILLE);
			


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

	public void setVue(PaSelectionLigneMultiTiers vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	public ModelTiersCreationDoc getMasterModel() {
		return masterModel;
	}

	public void setMasterModel(ModelTiersCreationDoc<TaTiers> masterModel) {
		this.masterModel = masterModel;
	}

	@Override
	protected void initEtatBouton() {
		initEtatBouton(false);
	}
	
	protected void initImageBouton() {
		super.initImageBouton();
		vue.getBtnAnnuler().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ANNULER));
		vue.getBtnEnregister().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ACCEPTER));
		vue.getBtnFermer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_FERMER));
		vue.getBtnImprimer().setImage(Activator.getImageDescriptor("/icons/arrow_undo.png").createImage());
		vue.getBtnInserer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_REINITIALISER));
		vue.getBtnModifier().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_MODIFIER));
		vue.getBtnSupprimer().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_SUPPRIMER));
		vue.getBtnEnregister().setText("Valider la sélection");
		vue.getBtnImprimer().setText("Remplacer la sélection");
		vue.getBtnFermer().setText("Supprimer les non cochés");
		vue.getBtnAnnuler().setText("Réinitialiser");
		vue.getBtnModifier().setText("tout cocher");
		vue.getBtnSupprimer().setText("tout Décocher");
		vue.getBtnInserer().setText("Inverser les cochés");
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
			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,true);
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
		int premierComposite = 30;
		int secondComposite = 70;
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
	
	
	
	private class HandlerToutCocher extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException {
			try {
				for (Object obj : masterModel.getListeObjet()) {
					((SWTTiers)obj).setAccepte(true);
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
				for (Object obj : masterModel.getListeObjet()) {
					((SWTTiers)obj).setAccepte(false);
				}
			} catch (Exception e) {
				logger.error("", e);
			}
			return event;
		}
	}

	public ModelRecupDocumentCreationDoc getMasterModelFinal() {
		return masterModelFinal;
	}

	public void setMasterModelFinal(ModelRecupDocumentCreationDoc masterModelFinal) {
		this.masterModelFinal = masterModelFinal;
	}

	public Date getMasterDateDeb() {
		return masterDateDeb;
	}

	public void setMasterDateDeb(Date masterDateDeb) {
		this.masterDateDeb = masterDateDeb;
	}

	public Date getMasterDateFin() {
		return masterDateFin;
	}

	public void setMasterDateFin(Date masterDateFin) {
		this.masterDateFin = masterDateFin;
	}

	public String getSelectedTypeCreation() {
		return selectedTypeCreation;
	}

	public void setSelectedTypeCreation(String selectedTypeCreation) {
		this.selectedTypeCreation = selectedTypeCreation;
	}

	public String getSelectedTypeSelection() {
		return selectedTypeSelection;
	}

	public void setSelectedTypeSelection(String selectedTypeSelection) {
		this.selectedTypeSelection = selectedTypeSelection;
	}

	public PaSelectionLigneDocControlleur getPaSelectionLigneDocControlleur() {
		return paSelectionLigneDocControlleur;
	}

	public void setPaSelectionLigneDocControlleur(
			PaSelectionLigneDocControlleur paSelectionLigneDocControlleur) {
		this.paSelectionLigneDocControlleur = paSelectionLigneDocControlleur;
	}

	public TaParamCreeDocTiers getParamGen() {
		return paramGen;
	}

	public void setParamGen(TaParamCreeDocTiers paramGen) {
		this.paramGen = paramGen;
	}
	
public String initialiseLibelleDoc(String typeSelection){
	if(typeSelection.equals(TaBonliv.TYPE_DOC)){
		libelle="Reprise des BL ";
	}
	if(typeSelection.equals(TaBoncde.TYPE_DOC)){
		libelle="Reprise des Bcde ";
	}
	if(typeSelection.equals(TaFacture.TYPE_DOC)){
		libelle="Reprise des Factures ";
	}
	if(typeSelection.equals(TaAvoir.TYPE_DOC)){
		libelle="Reprise des Avoirs ";
	}
	if(typeSelection.equals(TaDevis.TYPE_DOC)){
		libelle="Reprise des Devis ";
	}
	if(typeSelection.equals(TaPrelevement.TYPE_DOC)){
		libelle="Reprise des Prel ";
	}
	if(typeSelection.equals(TaAcompte.TYPE_DOC)){
		libelle="Reprise des Acomptes ";
	}
	if(typeSelection.equals(TaProforma.TYPE_DOC)){
		libelle="Reprise des Prof. ";
	}
	if(typeSelection.equals(TaAvisEcheance.TYPE_DOC)){
		libelle="Reprise des Avis ";
	}	
	return libelle;
}

public IDocumentTiers getMasterDocument() {
	return masterDocument;
}

public void setMasterDocument(IDocumentTiers masterDocument) {
	this.masterDocument = masterDocument;
}

public void addDeclencheInitEtatBoutonControllerListener(IDeclencheInitEtatBoutonControllerListener l) {
	listenerList.add(IDeclencheInitEtatBoutonControllerListener.class, l);
}

public void removeDeclencheInitEtatBoutonControllerListener(IDeclencheInitEtatBoutonControllerListener l) {
	listenerList.remove(IDeclencheInitEtatBoutonControllerListener.class, l);
}

protected void fireDeclencheInitEtatBoutonController(DeclencheInitEtatBoutonControllerEvent e) {
	Object[] listeners = listenerList.getListenerList();
	for (int i = listeners.length - 2; i >= 0; i -= 2) {
		if (listeners[i] == IDeclencheInitEtatBoutonControllerListener.class) {
			if (e == null)
				e = new DeclencheInitEtatBoutonControllerEvent(this,e.getOption());
			( (IDeclencheInitEtatBoutonControllerListener) listeners[i + 1]).DeclencheInitEtatBoutonController(e);
		}
	}
}

}

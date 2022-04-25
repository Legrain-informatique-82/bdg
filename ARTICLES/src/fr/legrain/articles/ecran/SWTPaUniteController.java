package fr.legrain.articles.ecran;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.OptimisticLockException;
import javax.persistence.RollbackException;

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
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaFamille;
import fr.legrain.articles.dao.TaFamilleUnite;
import fr.legrain.articles.dao.TaFamilleUniteDAO;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.articles.dao.TaUniteDAO;
import fr.legrain.articles.divers.Impression;
import fr.legrain.articles.editor.EditorFamilleUnite;
import fr.legrain.articles.editor.EditorInputFamille;
import fr.legrain.articles.editor.EditorInputUnite;
import fr.legrain.articles.editor.EditorUnite;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.edition.actions.AttributElementResport;
import fr.legrain.edition.actions.BaseImpressionEdition;
import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.edition.actions.MakeDynamiqueReport;
import fr.legrain.edition.dynamique.FonctionGetInfosXmlAndProperties;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Articles.SWTArticle;
import fr.legrain.gestCom.Module_Articles.SWTFamille;
import fr.legrain.gestCom.Module_Articles.SWTUnite;
import fr.legrain.gestCom.Module_Tiers.SWTTiers;
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
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.InfosVerifSaisie;
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
import fr.legrain.lib.gui.grille.LgrViewerSupport;
import fr.legrain.libMessageLGR.LgrMess;
import fr.legrain.tiers.dao.TaAdresse;
import fr.legrain.tiers.dao.TaTAdr;

public class SWTPaUniteController extends JPABaseControllerSWTStandard
implements RetourEcranListener {

	static Logger logger = Logger.getLogger(SWTPaUniteController.class.getName());
	private PaUniteSWT vue = null;
	private TaUniteDAO dao = null;//new TaUniteDAO(getEm());

	private Object ecranAppelant = null;
	private SWTUnite swtUnite;
	private SWTUnite swtOldUnite;
	private Realm realm;
	private DataBindingContext dbc;

	private Class classModel = SWTUnite.class;
	private ModelGeneralObjet<SWTUnite,TaUniteDAO,TaUnite> modelUnite = null;//new ModelGeneralObjet<SWTUnite,TaUniteDAO,TaUnite>(dao,classModel);
	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private IObservableValue selectedUnite;
	private String[] listeChamp;
	private String nomClassController = this.getClass().getSimpleName();

	private TaUnite taUnite = null;

	private MapChangeListener changeListener = new MapChangeListener();
	private LgrDozerMapper<SWTUnite,TaUnite> mapperUIToModel = new LgrDozerMapper<SWTUnite,TaUnite>();
	
	public SWTPaUniteController(PaUniteSWT vue) {
		this(vue,null);
	}

	public SWTPaUniteController(PaUniteSWT vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaUniteDAO(getEm());
		modelUnite = new ModelGeneralObjet<SWTUnite,TaUniteDAO,TaUnite>(dao,classModel);
		setVue(vue);

		int nb = 10;

		// a faire avant l'initialisation du Binding
		vue.getGrille().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setSwtOldUnite();
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
			setGrille(vue.getGrille());
			initSashFormWeight();
			setDaoStandard(dao);
			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);

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
			Menu[] tabPopups = new Menu[] { popupMenuFormulaire, popupMenuGrille };
			this.initPopupAndButtons(mapActions, tabPopups);
			vue.getPaCorpsFormulaire().setMenu(popupMenuFormulaire);
			vue.getPaGrille().setMenu(popupMenuGrille);

			initEtatBouton();
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaArticlesController", e);
		}
	}

	public void bind(PaUniteSWT paUniteSWT) {
		try {
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			tableViewer = new LgrTableViewer(paUniteSWT.getGrille());
			tableViewer.createTableCol(classModel,paUniteSWT.getGrille(), nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE,0);
			listeChamp = tableViewer.setListeChampGrille(nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE);

//			ObservableListContentProvider viewerContent = new ObservableListContentProvider();
//			tableViewer.setContentProvider(viewerContent);
//
//			IObservableMap[] attributeMaps = BeansObservables.observeMaps(
//					viewerContent.getKnownElements(), classModel,listeChamp);
//
//			tableViewer.setLabelProvider(new ObservableMapLabelProvider(attributeMaps));
//			writableList = new WritableList(realm, modelUnite.remplirListe(), classModel);
//			tableViewer.setInput(writableList);
			
			// Set up data binding.
			LgrViewerSupport.bind(tableViewer, 
					new WritableList(modelUnite.remplirListe(), classModel),
					BeanProperties.values(listeChamp)
					);

			selectedUnite = ViewersObservables.observeSingleSelection(tableViewer);

			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			tableViewer.selectionGrille(0);

			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);

			bindingFormMaitreDetail(dbc, realm, selectedUnite,classModel);
			changementDeSelection();
			selectedUnite.addChangeListener(new IChangeListener() {

				public void handleChange(ChangeEvent event) {
					changementDeSelection();
				}

			});

		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("", e);
		}
	}
	
	private void changementDeSelection() {
		if(selectedUnite!=null && selectedUnite.getValue()!=null) {
			if(((SWTUnite) selectedUnite.getValue()).getIdUnite()!=null) {
				taUnite = dao.findById(((SWTUnite) selectedUnite.getValue()).getIdUnite());
			}
			//null a tester ailleurs, car peut etre null en cas d'insertion
			fireChangementDeSelection(new ChangementDeSelectionEvent(SWTPaUniteController.this));
		}
	}

	public Composite getVue() {
		return vue;
	}

	public ResultAffiche configPanel(ParamAffiche param) {
		if (param != null) {
			if (((ParamAfficheUnite) param).getFocusDefautSWT() != null && !((ParamAfficheUnite) param).getFocusDefautSWT().isDisposed())
				((ParamAfficheUnite) param).getFocusDefautSWT().setFocus();
			else
				((ParamAfficheUnite) param).setFocusDefautSWT(vue.getGrille());
			vue.getLaTitreFormulaire().setText(((ParamAfficheUnite) param).getTitreFormulaire());
			vue.getLaTitreGrille().setText(((ParamAfficheUnite) param).getTitreGrille());
			vue.getLaTitreFenetre().setText(((ParamAfficheUnite) param).getSousTitre());

			if (param.getEcranAppelant() != null) {
				ecranAppelant = param.getEcranAppelant();
			}
			Map<String,String[]> map = dao.getParamWhereSQL();
			if(param.getIdFiche()!=null &&  !param.getIdFiche().equals("")) {
				if(map==null) map = new HashMap<String,String[]>();
				map.clear();
				map.put("idUnite",new String[]{"=",param.getIdFiche()});
				dao.setParamWhereSQL(map);
				vue.getBtnTous().setVisible(true);
				vue.getGrille().setVisible(false);
				vue.getLaTitreGrille().setVisible(false);
				vue.getCompositeForm().setWeights(new int[]{0,100});					
			}
			bind(vue);
			// permet de se positionner sur le 1er enregistrement et de remplir
			// le formulaire
			tableViewer.selectionGrille(0);
			tableViewer.tri(classModel, nomClassController,Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			VerrouInterface.setVerrouille(false);
			setSwtOldUnite();

			if (param.getModeEcran() != null
					&& param.getModeEcran().compareTo(EnumModeObjet.C_MO_INSERTION) == 0) {
				try {
					actInserer();
				} catch (Exception e) {
					vue.getLaMessage().setText(e.getMessage());
					logger.error("", e);
				}
			}
		}
		return null;
	}
	
	protected void initVerifySaisie() {
		if (mapInfosVerifSaisie == null)
			mapInfosVerifSaisie = new HashMap<Control, InfosVerifSaisie>();
		
		mapInfosVerifSaisie.put(vue.getTfCODE_FAMILLE(), new InfosVerifSaisie(new TaFamille(),Const.C_CODE_FAMILLE,null));
		mapInfosVerifSaisie.put(vue.getTfCODE_UNITE(), new InfosVerifSaisie(new TaUnite(),Const.C_CODE_UNITE,null));
		mapInfosVerifSaisie.put(vue.getTfLIBL_UNITE(), new InfosVerifSaisie(new TaUnite(),Const.C_LIBL_UNITE,null));

		initVerifyListener(mapInfosVerifSaisie, dao);
	}

	protected void initComposantsVue() throws ExceptLgr {
	}

	protected void initMapComposantChamps() {
		if (mapComposantChamps == null)
			mapComposantChamps = new LinkedHashMap<Control, String>();

		if (listeComposantFocusable == null)
			listeComposantFocusable = new ArrayList<Control>();
		listeComposantFocusable.add(vue.getGrille());

		vue.getTfCODE_UNITE().setToolTipText(Const.C_CODE_UNITE);
		vue.getTfLIBL_UNITE().setToolTipText(Const.C_LIBL_UNITE);
		vue.getTfCODE_FAMILLE().setToolTipText(Const.C_CODE_FAMILLE);

		mapComposantChamps.put(vue.getTfCODE_UNITE(), Const.C_CODE_UNITE);
		mapComposantChamps.put(vue.getTfLIBL_UNITE(), Const.C_LIBL_UNITE);
		mapComposantChamps.put(vue.getTfCODE_FAMILLE(), Const.C_CODE_FAMILLE);

		for (Control c : mapComposantChamps.keySet()) {
			listeComposantFocusable.add(c);
		}

		listeComposantFocusable.add(vue.getBtnEnregistrer());
		listeComposantFocusable.add(vue.getBtnInserer());
		listeComposantFocusable.add(vue.getBtnModifier());
		listeComposantFocusable.add(vue.getBtnSupprimer());
		listeComposantFocusable.add(vue.getBtnFermer());
		listeComposantFocusable.add(vue.getBtnAnnuler());
		listeComposantFocusable.add(vue.getBtnImprimer());
		listeComposantFocusable.add(vue.getBtnTous());

		if (mapInitFocus == null)
			mapInitFocus = new LinkedHashMap<ModeObjet.EnumModeObjet, Control>();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION, vue
				.getTfCODE_UNITE());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue
				.getTfCODE_UNITE());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION, vue
				.getGrille());

		activeModifytListener();
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
		mapCommand.put(C_COMMAND_DOCUMENT_AFFICHER_TOUS_ID, handlerAfficherTous);
		
		initFocusCommand(String.valueOf(this.hashCode()),listeComposantFocusable,mapCommand);

		if (mapActions == null)
			mapActions = new LinkedHashMap<Button, Object>();

		mapActions.put(vue.getBtnAnnuler(), C_COMMAND_GLOBAL_ANNULER_ID);
		mapActions.put(vue.getBtnEnregistrer(), C_COMMAND_GLOBAL_ENREGISTRER_ID);
		mapActions.put(vue.getBtnInserer(), C_COMMAND_GLOBAL_INSERER_ID);
		mapActions.put(vue.getBtnModifier(), C_COMMAND_GLOBAL_MODIFIER_ID);
		mapActions.put(vue.getBtnSupprimer(), C_COMMAND_GLOBAL_SUPPRIMER_ID);
		mapActions.put(vue.getBtnFermer(), C_COMMAND_GLOBAL_FERMER_ID);
		mapActions.put(vue.getBtnImprimer(), C_COMMAND_GLOBAL_IMPRIMER_ID);
		mapActions.put(vue.getBtnTous(), C_COMMAND_DOCUMENT_AFFICHER_TOUS_ID);
		
		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID, C_COMMAND_GLOBAL_REFRESH_ID };
		mapActions.put(null, tabActionsAutres);
	}

	public SWTPaUniteController getThis() {
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
					.getString("Unite.Message.Enregistrer"))) {

				try {
					actEnregistrer();
				} catch (Exception e) {
					vue.getLaMessage().setText(e.getMessage());
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
			if(dao.dataSetEnModif())
				try {
					dao.annuler(taUnite);
				} catch (Exception e) {
					throw new ExceptLgr();
				}
			if (ecranAppelant instanceof SWTPaAideControllerSWT) {
				setListeEntity(getModelUnite().remplirListe());
				dao.initValeurIdTable(taUnite);
				fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
						dao.getChampIdEntite(), dao.getValeurIdTable(),
						selectedUnite.getValue())));

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
					if(getFocusAvantAideSWT().equals(vue.getTfCODE_FAMILLE())){
						TaFamilleUnite f = null;
						TaFamilleUniteDAO t = new TaFamilleUniteDAO(getEm());
						f = t.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
						t = null;
						taUnite.setTaFamilleUnite(f);
					}					
					ctrlUnChampsSWT(getFocusAvantAideSWT());
					
				} catch (Exception e) {
					logger.error("",e);
					vue.getLaMessage().setText(e.getMessage());
				}
			}
			if (getFocusAvantAideSWT() instanceof Table) {
				if (getFocusAvantAideSWT() == vue.getGrille()) {
					if(((ResultAffiche) evt.getRetour()).getSelection()!=null)
						tableViewer.setSelection(((ResultAffiche) evt.getRetour()).getSelection(),true);
				}
			}			
		} else if (evt.getRetour() != null){
			if (getFocusAvantAideSWT() instanceof Table) {
				if (getFocusAvantAideSWT() == vue.getGrille()) {
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
				setSwtOldUnite();
				swtUnite = new SWTUnite();

				taUnite = new TaUnite();
				dao.inserer(taUnite);
				modelUnite.getListeObjet().add(swtUnite);
				writableList = new WritableList(realm, modelUnite.getListeObjet(), classModel);
				tableViewer.setInput(writableList);
				tableViewer.refresh();
				tableViewer.setSelection(new StructuredSelection(swtUnite));
				initEtatBouton();
			}
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
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
				setSwtOldUnite();
				taUnite = dao.findById(((SWTUnite) selectedUnite.getValue()).getIdUnite());
			}else{
				if(!setSwtOldUniteRefresh())throw new Exception();
			}
			dao.modifier(taUnite);
			initEtatBouton();
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		}
	}
	
	public boolean containsEntity(TaUnite entite){
		if(modelUnite.getListeEntity()!=null){
			for (Object e : modelUnite.getListeEntity()) {
				if(((TaUnite)e).getIdUnite()==
					entite.getIdUnite())return true;
			}
		}
		return false;
	}


	public boolean setSwtOldUniteRefresh() {
		try {	
			if (selectedUnite.getValue()!=null){
				TaUnite taArticleOld =dao.findById(taUnite.getIdUnite());
				taArticleOld=dao.refresh(taArticleOld);
				if(containsEntity(taUnite)) 
					modelUnite.getListeEntity().remove(taUnite);
				if(!taUnite.getVersionObj().equals(taArticleOld.getVersionObj())){
					taUnite=taArticleOld;
					if(!containsEntity(taUnite)) 
						modelUnite.getListeEntity().add(taUnite);					
					actRefresh();
					dao.messageNonAutoriseModification();
				}
				taUnite=taArticleOld;
				if(!containsEntity(taUnite)) 
					modelUnite.getListeEntity().add(taUnite);
				changementDeSelection();
				this.swtOldUnite=SWTUnite.copy((SWTUnite)selectedUnite.getValue());
			}
			return true;
		} catch (Exception e) {
			return false;
		}		
	}
	
//	public void setSwtOldUniteRefresh() {
//		if (selectedUnite.getValue()!=null){
//			if(LgrMess.isDOSSIER_EN_RESEAU())dao.refresh(dao.findById(((SWTUnite) selectedUnite.getValue()).getIdUnite()));
//			taUnite=dao.findById(((SWTUnite) selectedUnite.getValue()).getIdUnite());
//			try {
//				if(LgrMess.isDOSSIER_EN_RESEAU())actRefresh();
//			} catch (Exception e) {
//				logger.error("",e);
//			}			
//			this.swtOldUnite=SWTUnite.copy((SWTUnite)selectedUnite.getValue());
//		}
//	}
	
	@Override
	protected void actSupprimer() throws Exception {
		EntityTransaction transaction = dao.getEntityManager().getTransaction();
		try {
			VerrouInterface.setVerrouille(true);
			if(isUtilise())MessageDialog.openInformation(vue.getShell(), MessagesEcran
					.getString("Message.Attention"), MessagesEcran
					.getString("Message.suppression"));
			else		
				if (MessageDialog.openConfirm(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("Unite.Message.Supprimer"))) {

					dao.begin(transaction);
					TaUnite u = dao.findById(((SWTUnite) selectedUnite.getValue()).getIdUnite());
					dao.supprimer(u);
					dao.commit(transaction);
					Object suivant=tableViewer.getElementAt(tableViewer.getTable().getSelectionIndex()+1);
					modelUnite.removeEntity(u);
					taUnite=null;
					dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
					if(suivant!=null)tableViewer.setSelection(new StructuredSelection(suivant),true);
					else tableViewer.selectionGrille(0);
					actRefresh(); //ajouter pour tester jpa

				}
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
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
						.getString("Unite.Message.Annuler")))) {
					remetTousLesChampsApresAnnulationSWT(dbc);
					if(((SWTUnite) selectedUnite.getValue()).getIdUnite()==null){
						modelUnite.getListeObjet().remove(
								((SWTUnite) selectedUnite.getValue()));
						writableList = new WritableList(realm, modelUnite
								.getListeObjet(), classModel);
						tableViewer.setInput(writableList);
						tableViewer.refresh();
						if(swtOldUnite!=null)
							tableViewer.setSelection(new StructuredSelection(swtOldUnite), true);
						else
							tableViewer.selectionGrille(0);
					}
					dao.annuler(taUnite);
					hideDecoratedFields();
				}
				initEtatBouton();
				break;
			case C_MO_EDITION:
				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("Unite.Message.Annuler")))) {
					int rang = modelUnite.getListeObjet().indexOf(selectedUnite.getValue());
					remetTousLesChampsApresAnnulationSWT(dbc);
					modelUnite.getListeObjet().set(rang, swtOldUnite);
					writableList = new WritableList(realm, modelUnite.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.setSelection(new StructuredSelection(swtOldUnite), true);
					dao.annuler(taUnite);
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
		/******************************/
		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();

		String nomChampIdTable =  dao.getChampIdTable();
		
		FonctionGetInfosXmlAndProperties fonctionGetInfosXmlAndProperties = new FonctionGetInfosXmlAndProperties(mapperUIToModel);
		fonctionGetInfosXmlAndProperties.cleanValueMapAttributeTable();

		fonctionGetInfosXmlAndProperties.setValueMapAttributeTable(TaUnite.class.getSimpleName()+".head");
		fonctionGetInfosXmlAndProperties.setValueMapAttributeTable(TaUnite.class.getSimpleName()+".detail");
		

		LinkedHashMap<String,AttributElementResport> mapAttributeTablHead = fonctionGetInfosXmlAndProperties.getMapAttributeTablHead(); 
		LinkedHashMap<String,AttributElementResport> mapAttributeTablDetail = fonctionGetInfosXmlAndProperties.getMapAttributeTablDetail();
		
		Collection<TaUnite> collectionTaUnite = modelUnite.getListeEntity();

		fonctionGetInfosXmlAndProperties.findInfosFileXml(TaUnite.class.getName(),SWTUnite.class.getName(),
				listeChamp,"mapping");
		fonctionGetInfosXmlAndProperties.getInfosObjetJPA(taUnite);
		
		ConstEdition constEdition = new ConstEdition(); 
//		Impression impression = new Impression(constEdition,taUnite,collectionTaUnite,nomChampIdTable,
//				taUnite.getIdUnite());
		
		String nomDossier = null;
		
		int nombreLine = collectionTaUnite.size();
		if(nombreLine==0){
			MessageDialog.openWarning(vue.getShell(), ConstEdition.TITRE_MESSAGE_EDITION_VIDE,
					ConstEdition.EDITION_VIDE);
		}
		else{
			if(taInfoEntreprise.getIdInfoEntreprise()==0){
				nomDossier = ConstEdition.INFOS_VIDE;
			}
			else{
				nomDossier = taInfoEntreprise.getNomInfoEntreprise();	
			}
			
			constEdition.addValueList(tableViewer, nomClassController);

			String folderEditionDynamique = Const.C_RCP_INSTANCE_LOCATION+ConstEdition.SEPARATOR+
					Const.C_NOM_PROJET_TMP+ConstEdition.SEPARATOR+TaUnite.class.getSimpleName();
			constEdition.makeFolderEditions(folderEditionDynamique);
			Path pathFileReport = new Path(folderEditionDynamique+ConstEdition.SEPARATOR+Const.C_NOM_VU_UNITE+".rptdesign");
			final String pathFileReportDynamic = pathFileReport.toPortableString();

			MakeDynamiqueReport DynamiqueReport = new MakeDynamiqueReport(constEdition.getNameTableEcran(),
					constEdition.getNameTableBDD(),pathFileReportDynamic,Const.C_NOM_VU_UNITE,
					ConstEdition.PAGE_ORIENTATION_LANDSCAPE,nomDossier); 
			
			/**************************************************************/
			DynamiqueReport.setFonctionGetInfosXml(fonctionGetInfosXmlAndProperties);
			DynamiqueReport.setNomObjet(TaUnite.class.getSimpleName());
			/**************************************************************/

			Map<String, AttributElementResport> attribuGridHeader = new LinkedHashMap<String, AttributElementResport>();
			String nameHeaderTitle = ConstEditionArticle.TITLE_EDITION_ARTICLE_UNITE;
			attribuGridHeader.put(nameHeaderTitle, new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
					ConstEdition.FONT_SIZE_XX_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
					ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
					,""));
			String nameSousHeaderTitle = ConstEditionArticle.SOUS_TITLE_EDITION_ARTICLE_UNITE;
			attribuGridHeader.put(nameSousHeaderTitle, new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
					ConstEdition.FONT_SIZE_X_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
					ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
					,ConstEdition.COLOR_GRAY));

			//DynamiqueReport.buildDesignConfig();
			ConstEdition.CONTENT_COMMENTS = ConstEditionArticle.COMMENTAIRE_EDITION_DEFAUT;

			DynamiqueReport.setSimpleNameEntity(TaUnite.class.getSimpleName());
			
			DynamiqueReport.initializeBuildDesignReportConfig();
			DynamiqueReport.makePageMater("1", "1", "1", "1", "100");
			DynamiqueReport.makeReportHeaderGrid(3,5,100,ConstEdition.UNITS_PERCENTAGE,attribuGridHeader);
			DynamiqueReport.biuldTableReport("100", ConstEdition.UNITS_PERCENTAGE, 
					Const.C_NOM_VU_UNITE,1,1,2,"40", mapAttributeTablHead, mapAttributeTablDetail);
			DynamiqueReport.savsAsDesignHandle();
//			impression.imprimer(true,pathFileReportDynamic,null,"Unite",TaUnite.class.getSimpleName(),false);
			
			BaseImpressionEdition impressionEdition = new BaseImpressionEdition(constEdition,taUnite,
					getEm(),collectionTaUnite,taUnite.getIdUnite());
			
			impressionEdition.impressionEditionTypeEntity(pathFileReportDynamic,"Unité");
		}

	}

	@Override
	protected boolean aideDisponible() {
		boolean result = false;
		switch ((getThis().dao.getModeObjet().getMode())) {
		case C_MO_CONSULTATION:
			if(getFocusCourantSWT().equals(vue.getGrille()))
				result = true;
			break;
		case C_MO_EDITION:
		case C_MO_INSERTION:
			if(getFocusCourantSWT().equals(vue.getTfCODE_FAMILLE()))
				result = true;			
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

				switch (SWTPaUniteController.this.dao.getModeObjet().getMode()) {
				case C_MO_CONSULTATION:
					if(getFocusCourantSWT().equals(vue.getGrille())){
//						PaUniteSWT paUniteSWT = new PaUniteSWT(s2,SWT.NULL);
//						SWTPaUniteController swtPaUniteController = new SWTPaUniteController(paUniteSWT);
//
//						editorCreationId = EditorUnite.ID;
//						editorInputCreation = new EditorInputUnite();
//
//						ParamAfficheUnite paramAfficheUnite = new ParamAfficheUnite();
//						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
//						paramAfficheUnite.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//						paramAfficheUnite.setEcranAppelant(paAideController);
//						controllerEcranCreation = swtPaUniteController;
//						parametreEcranCreation = paramAfficheUnite;
//						
//						paramAfficheAideRecherche.setForceAffichageAideRemplie(true);
//
//
//						paramAfficheAideRecherche.setTypeEntite(TaUnite.class);
//						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_UNITE);
//						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_UNITE().getText());
//						paramAfficheAideRecherche.setControllerAppelant(SWTPaUniteController.this);
//						paramAfficheAideRecherche.setModel(swtPaUniteController.getModelUnite());
//						paramAfficheAideRecherche.setTypeObjet(swtPaUniteController.getClassModel());
//						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_UNITE);
					}
					break;
				case C_MO_EDITION:
				case C_MO_INSERTION:
					if(getFocusCourantSWT().equals(vue.getTfCODE_FAMILLE())){

						PaFamilleUnite paFamilleSWT = new PaFamilleUnite(s2,SWT.NULL); 
						PaFamilleUniteController swtPaFamilleController = new PaFamilleUniteController(paFamilleSWT);

						editorCreationId = EditorFamilleUnite.ID;
						editorInputCreation = new EditorInputFamille();

						ParamAfficheFamille paramAfficheFamille = new ParamAfficheFamille();
						paramAfficheAideRecherche.setJPQLQuery(new TaFamilleUniteDAO(getEm()).getJPQLQuery());
						paramAfficheFamille.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheFamille.setEcranAppelant(paAideController);
						/* 
						 * controllerEcranCreation ne sert plus a rien, pour l'editeur de creation, on creer un nouveau controller
						 */
						controllerEcranCreation = swtPaFamilleController;
						parametreEcranCreation = paramAfficheFamille;

						paramAfficheAideRecherche.setTypeEntite(TaFamilleUnite.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_FAMILLE);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_FAMILLE().getText());
						paramAfficheAideRecherche.setControllerAppelant(SWTPaUniteController.this);
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTFamille,TaFamilleUniteDAO,TaFamilleUnite>(SWTFamille.class,dao.getEntityManager()));
						paramAfficheAideRecherche.setTypeObjet(swtPaFamilleController.getClassModel());

						paramAfficheAideRecherche.setChampsIdentifiant(swtPaFamilleController.getDao().getChampIdTable());
					}
					
					break;
				default:
					break;
				}

				if (paramAfficheAideRecherche.getJPQLQuery() != null) {

					PaAideRechercheSWT paAideRecherche1 = new PaAideRechercheSWT(s,
							SWT.NULL);
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
					paAideController.addRetourEcranListener(SWTPaUniteController.this);
					// affichage de l'ecran d'aide principal (+ ses recherches)

					dbc.getValidationStatusMap().removeMapChangeListener(changeListener);
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
		String validationContext = "UNITE";
		try {
			setActiveAide(true);
			IStatus s = null;
			int change=0;
			if(nomChamp.equals(Const.C_CODE_FAMILLE)) {
				TaFamilleUniteDAO dao = new TaFamilleUniteDAO(getEm());

				dao.setModeObjet(getDao().getModeObjet());
				TaFamilleUnite f = new TaFamilleUnite();
				PropertyUtils.setSimpleProperty(f, nomChamp, value);
				s = dao.validate(f,nomChamp,validationContext);

				if(s.getSeverity()!=IStatus.ERROR ){
					f = dao.findByCode((String)value);
					taUnite.setTaFamilleUnite(f);
				}
				dao = null;
			} else {
				boolean verrouilleModifCode = false;
				TaUnite u = new TaUnite();
				PropertyUtils.setSimpleProperty(u, nomChamp, value);
				if(((SWTUnite) selectedUnite.getValue()).getIdUnite()!=null) {
					u.setIdUnite(((SWTUnite) selectedUnite.getValue()).getIdUnite());
				}
				if(nomChamp.equals(Const.C_CODE_UNITE)){
					verrouilleModifCode = true;
				}

				s = dao.validate(u,nomChamp,validationContext,verrouilleModifCode);
				if(s.getSeverity()!=IStatus.ERROR && change!=0) {

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
			ctrlTousLesChampsAvantEnregistrementSWT();
			dao.begin(transaction);

			if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
				LgrDozerMapper<SWTUnite,TaUnite> mapper = new LgrDozerMapper<SWTUnite,TaUnite>();
				mapper.map((SWTUnite) selectedUnite.getValue(),taUnite);

				dao.enregistrerMerge(taUnite);



			} else if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)) {
				LgrDozerMapper<SWTUnite,TaUnite> mapper = new LgrDozerMapper<SWTUnite,TaUnite>();
				mapper.map((SWTUnite) selectedUnite.getValue(),taUnite);

				taUnite=dao.enregistrerMerge(taUnite);
//				modelUnite.getListeEntity().add(taUnite);
			}
			
			dao.commit(transaction);
			modelUnite.addEntity(taUnite);
			transaction = null;
			actRefresh(); 

		} catch (RollbackException e) {	
			logger.error("",e);
			if(e.getCause() instanceof OptimisticLockException)
				MessageDialog.openError(vue.getShell(), "", e.getMessage()+"\n"+e.getCause().getMessage());
		} finally {
			if(transaction!=null && transaction.isActive()) {
				transaction.rollback();
			}
			initEtatBouton();
		}
	}

	public void initEtatComposant() {
		try {
			vue.getTfCODE_UNITE().setEditable(!isUtilise());
			changeCouleur(vue);
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
		}
	}
	public boolean isUtilise(){
		return (((SWTUnite)selectedUnite.getValue()).getIdUnite()!=null &&
		!dao.recordModifiable(dao.getNomTable(),
				((SWTUnite)selectedUnite.getValue()).getIdUnite()))||
				!dao.autoriseModification(taUnite);		
	}
	public SWTUnite getSwtOldUnite() {
		return swtOldUnite;
	}

	public void setSwtOldUnite(SWTUnite swtOldUnite) {
		this.swtOldUnite = swtOldUnite;
	}

	public void setSwtOldUnite() {
		if (selectedUnite.getValue() != null)
			this.swtOldUnite = SWTUnite.copy((SWTUnite) selectedUnite.getValue());
		else {
			if(tableViewer.selectionGrille(0)){
				this.swtOldUnite = SWTUnite.copy((SWTUnite) selectedUnite.getValue());
				tableViewer.setSelection(new StructuredSelection(
						(SWTUnite) selectedUnite.getValue()), true);
			}
		}
	}

	public void setVue(PaUniteSWT vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();

		mapComposantDecoratedField.put(vue.getTfCODE_UNITE(), vue.getFieldCODE_UNITE());
		mapComposantDecoratedField.put(vue.getTfLIBL_UNITE(), vue.getFieldLIBL_UNITE());
		mapComposantDecoratedField.put(vue.getTfCODE_FAMILLE(), vue.getFieldCODE_FAMILLE());

	}

	public Class getClassModel() {
		return classModel;
	}

	@Override
	protected void sortieChamps() {
		// TODO Raccord de methode auto-genere

	}

	@Override
	protected void actRefresh() throws Exception {
		//repositionnement sur la valeur courante
		int idActuel = 0;
		if (taUnite!=null) { //enregistrement en cours de modification/insertion
			idActuel = taUnite.getIdUnite();
		} else if(selectedUnite!=null && (SWTUnite) selectedUnite.getValue()!=null) {
			idActuel = ((SWTUnite) selectedUnite.getValue()).getIdUnite();
		}

		if (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION) == 0) {
			//rafraichissement des valeurs dans la grille
			writableList = new WritableList(realm, modelUnite.remplirListe(), classModel);
			tableViewer.setInput(writableList);
		} else {
			if (taUnite!=null && selectedUnite!=null && (SWTUnite) selectedUnite.getValue()!=null) {
				new LgrDozerMapper<TaUnite, SWTUnite>().
					map(taUnite, (SWTUnite) selectedUnite.getValue());
			}
		}

		if(idActuel!=0) {
			tableViewer.setSelection(new StructuredSelection(modelUnite.recherche(Const.C_ID_UNITE, idActuel)));
		}else
			tableViewer.selectionGrille(0);

	}

	public ModelGeneralObjet<SWTUnite,TaUniteDAO,TaUnite> getModelUnite() {
		return modelUnite;
	}

	public TaUniteDAO getDao() {
		return dao;
	}

	public TaUnite getTaUnite() {
		return taUnite;
	}
	public void actAfficherTous() throws Exception{
		vue.getGrille().setVisible(true);  
		vue.getBtnTous().setVisible(false);
		vue.getLaTitreGrille().setVisible(true);
		vue.getCompositeForm().setWeights(new int[]{50,100});
		dao.setJPQLQuery(dao.getJPQLQueryInitial());
		modelUnite.setJPQLQuery(null);
		modelUnite.setListeEntity(null);
		try {
			actRefresh();
		} catch (Exception e1) {
			logger.error("", e1);
		}
	}
}


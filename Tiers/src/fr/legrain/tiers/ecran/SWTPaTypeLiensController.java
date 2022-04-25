package fr.legrain.tiers.ecran;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
import fr.legrain.gestCom.Module_Tiers.SWTTLiens;
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
import fr.legrain.tiers.dao.TaTLiens;
import fr.legrain.tiers.dao.TaTLiensDAO;
import fr.legrain.tiers.divers.Impression;
import fr.legrain.tiers.editor.EditorInputTypeLiens;
import fr.legrain.tiers.editor.EditorTypeLiens;

public class SWTPaTypeLiensController extends JPABaseControllerSWTStandard
		implements RetourEcranListener {

	static Logger logger = Logger.getLogger(SWTPaTypeLiensController.class.getName());
	private PaTypeLiensSWT vue = null;
	private TaTLiensDAO dao = null;//new TaTLiensDAO();

	private Object ecranAppelant = null;
	private SWTTLiens swtTypeLiens;
	private SWTTLiens swtOldTypeLiens;
	private Realm realm;
	private DataBindingContext dbc;

	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private IObservableValue selectedTypeLiens;
	private String[] listeChamp;
	private String nomClassController = this.getClass().getSimpleName();
	private MapChangeListener changeListener = new MapChangeListener();
	private Class classModel = SWTTLiens.class;
	private ModelGeneralObjet<SWTTLiens,TaTLiensDAO,TaTLiens> modelTypeLiens = null;//new ModelGeneralObjet<SWTTLiens,TaTLiensDAO,TaTLiens>(dao,classModel);
	
	private LgrDozerMapper<SWTTLiens,TaTLiens> mapperUIToModel  = new LgrDozerMapper<SWTTLiens,TaTLiens>();
	private TaTLiens taTLiens = null;
	
	public SWTPaTypeLiensController(PaTypeLiensSWT vue) {
		this(vue,null);
	}

	public SWTPaTypeLiensController(PaTypeLiensSWT vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaTLiensDAO(getEm());
		modelTypeLiens = new ModelGeneralObjet<SWTTLiens,TaTLiensDAO,TaTLiens>(dao,classModel);
		setVue(vue);

		// a faire avant l'initialisation du Binding
		vue.getGrille().addSelectionListener(new SelectionAdapter() {
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
			Menu[] tabPopups = new Menu[] { popupMenuFormulaire,popupMenuGrille };
			this.initPopupAndButtons(mapActions, tabPopups);
			vue.getPaCorpsFormulaire().setMenu(popupMenuFormulaire);
			vue.getPaGrille().setMenu(popupMenuGrille);

			initEtatBouton();
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaTiersController", e);
		}
	}
	
	protected void initEtatBouton() {
		/*
		 * Les commandes sont desactivees tant que les seuls types de liens geres sont fichier" et "repertoie",
		 * l'utilisateur n'a pas la possiblite d'en ajouter.
		 * 
		 * Le verouillage des champs texte est également fait ici, car si on autorise la modif, il suffira de changer cette methode
		 */
		vue.getTfLABELLE_T_LIENS().setEnabled(false);
		
		enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,false);
		enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID,false);
		enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,false);
		enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,false);
		enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,false);
		enableActionAndHandler(C_COMMAND_GLOBAL_FERMER_ID,false);
		enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID,false);
		enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,false);
		enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID,false);
		enableActionAndHandler(C_COMMAND_DOCUMENT_AFFICHER_TOUS_ID,false);
	}

	public void bind(PaTypeLiensSWT paTypeLiensSWT) {
		try {
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			tableViewer = new LgrTableViewer(paTypeLiensSWT.getGrille());
			tableViewer.createTableCol(classModel,paTypeLiensSWT.getGrille(), nomClassController,
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
//			writableList = new WritableList(realm, modelTypeLiens.remplirListe(), classModel);
//			tableViewer.setInput(writableList);
			
			// Set up data binding.
			LgrViewerSupport.bind(tableViewer, 
					new WritableList(modelTypeLiens.remplirListe(), classModel),
					BeanProperties.values(listeChamp)
					);

			selectedTypeLiens = ViewersObservables.observeSingleSelection(tableViewer);

			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			tableViewer.selectionGrille(0);

			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);

			bindingFormMaitreDetail(dbc, realm, selectedTypeLiens,classModel);
			changementDeSelection();
			selectedTypeLiens.addChangeListener(new IChangeListener() {

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
		if(selectedTypeLiens!=null && selectedTypeLiens.getValue()!=null) {
			if(((SWTTLiens) selectedTypeLiens.getValue()).getIdTLiens()!=null) {
				taTLiens = dao.findById(((SWTTLiens) selectedTypeLiens.getValue()).getIdTLiens());
			}
			//null a tester ailleurs, car peut etre null en cas d'insertion
			fireChangementDeSelection(new ChangementDeSelectionEvent(SWTPaTypeLiensController.this));
		}
	}

	public Composite getVue() {
		return vue;
	}

	public ResultAffiche configPanel(ParamAffiche param) {
		if (param != null) {
			//ibTaTable.ouvreDataset();
			if (((ParamAfficheTypeLiens) param).getFocusDefautSWT() != null && !((ParamAfficheTypeLiens) param).getFocusDefautSWT().isDisposed())
				((ParamAfficheTypeLiens) param).getFocusDefautSWT().setFocus();
			else
				((ParamAfficheTypeLiens) param).setFocusDefautSWT(vue.getGrille());
			vue.getLaTitreFormulaire().setText(((ParamAfficheTypeLiens) param).getTitreFormulaire());
			vue.getLaTitreGrille().setText(((ParamAfficheTypeLiens) param).getTitreGrille());
			vue.getLaTitreFenetre().setText(((ParamAfficheTypeLiens) param).getSousTitre());

			if (param.getEcranAppelant() != null) {
				ecranAppelant = param.getEcranAppelant();
			}
			Map<String,String[]> map = dao.getParamWhereSQL();
			if(param.getIdFiche()!=null &&  !param.getIdFiche().equals("")) {
				if(map==null) map = new HashMap<String,String[]>();
				map.clear();
				map.put("idTLiens",new String[]{"=",param.getIdFiche()});
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
			setSwtOldTypeLiens();

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
		
		mapInfosVerifSaisie.put(vue.getTfCODE_T_LIENS(), new InfosVerifSaisie(new TaTLiens(),Const.C_CODE_T_LIENS,null));
		mapInfosVerifSaisie.put(vue.getTfLABELLE_T_LIENS(), new InfosVerifSaisie(new TaTLiens(),Const.C_LIBL_T_LIENS,null));

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

		vue.getTfCODE_T_LIENS().setToolTipText(Const.C_CODE_T_LIENS);
		vue.getTfLABELLE_T_LIENS().setToolTipText(Const.C_LIBL_T_LIENS);


		mapComposantChamps.put(vue.getTfCODE_T_LIENS(), Const.C_CODE_T_LIENS);
		mapComposantChamps.put(vue.getTfLABELLE_T_LIENS(), Const.C_LIBL_T_LIENS);

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
				.getTfCODE_T_LIENS());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue
				.getTfCODE_T_LIENS());
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

	public SWTPaTypeLiensController getThis() {
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
			if (ecranAppelant instanceof SWTPaAideControllerSWT) {
				setListeEntity(getModelTypeLiens().remplirListe());
				dao.initValeurIdTable(taTLiens);
				fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
						dao.getChampIdEntite(), dao.getValeurIdTable(),
						selectedTypeLiens.getValue())));

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
					vue.getLaMessage().setText(e.getMessage());
				}
			}
			if (getFocusAvantAideSWT() instanceof Table) {
				if (getFocusAvantAideSWT() == vue.getGrille()) {
					if(((ResultAffiche) evt.getRetour()).getSelection()!=null)
						tableViewer.setSelection(((ResultAffiche) evt.getRetour()).getSelection(),true);
				}
			}
		} else if (evt.getRetour() != null) {
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
// Pour l'intant, les types de liens sont fixés à la création du dossier ou lors de mises à jour,
// on ne peu pas en ajouter ou les modifier à partir de l'interface
		
//		try {
//			if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
//				VerrouInterface.setVerrouille(true);
//				setSwtOldTypeLiens();
//				swtTypeLiens = new SWTTLiens();
//				taTLiens = new TaTLiens();
//				dao.inserer(taTLiens);
//				modelTypeLiens.getListeObjet().add(swtTypeLiens);
//				writableList = new WritableList(realm, modelTypeLiens.getListeObjet(), classModel);
//				tableViewer.setInput(writableList);
//				tableViewer.refresh();
//				tableViewer.setSelection(new StructuredSelection(swtTypeLiens));
//				initEtatBouton();
//			}
//
//		} catch (Exception e1) {
//			vue.getLaMessage().setText(e1.getMessage());
//			logger.error("Erreur : actionPerformed", e1);
//		} finally {
//			initEtatComposant();
//			VerrouInterface.setVerrouille(false);
//		}
	}

	@Override
	protected void actModifier() throws Exception {
// Pour l'intant, les types de liens sont fixés à la création du dossier ou lors de mises à jour,
// on ne peu pas en ajouter ou les modifier à partir de l'interface
		
//		try {
//			if(!LgrMess.isDOSSIER_EN_RESEAU()){
//				setSwtOldTypeLiens();
//				taTLiens = dao.findById(((SWTTLiens) selectedTypeLiens.getValue()).getIdTLiens());
//			}else{
//				if(!setSwtOldTypeLiensRefresh())throw new Exception();
//			}			
//			dao.modifier(taTLiens);
//			initEtatBouton();
//		} catch (Exception e1) {
//			vue.getLaMessage().setText(e1.getMessage());
//			logger.error("Erreur : actionPerformed", e1);
//		}
	}
	public boolean containsEntity(TaTLiens entite){
		if(modelTypeLiens.getListeEntity()!=null){
			for (Object e : modelTypeLiens.getListeEntity()) {
				if(((TaTLiens)e).getIdTLiens()==
					entite.getIdTLiens())return true;
			}
		}
		return false;
	}

	public boolean setSwtOldTypeLiensRefresh() {
		try {	
			if (selectedTypeLiens.getValue()!=null){
				TaTLiens taArticleOld =dao.findById(taTLiens.getIdTLiens());
				taArticleOld=dao.refresh(taArticleOld);
				if(containsEntity(taTLiens)) 
					modelTypeLiens.getListeEntity().remove(taTLiens);
				if(!taTLiens.getVersionObj().equals(taArticleOld.getVersionObj())){
					taTLiens=taArticleOld;
					if(!containsEntity(taTLiens)) 
						modelTypeLiens.getListeEntity().add(taTLiens);					
					actRefresh();
					dao.messageNonAutoriseModification();
				}
				taTLiens=taArticleOld;
				if(!containsEntity(taTLiens)) 
					modelTypeLiens.getListeEntity().add(taTLiens);
				changementDeSelection();
				this.swtOldTypeLiens=SWTTLiens.copy((SWTTLiens)selectedTypeLiens.getValue());
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
			if(isUtilise())MessageDialog.openInformation(vue.getShell(), MessagesEcran
					.getString("Message.Attention"), MessagesEcran
					.getString("Message.suppression"));
			else		
			if (MessageDialog.openConfirm(vue.getShell(), MessagesEcran
					.getString("Message.Attention"), MessagesEcran
					.getString("TypeLiens.Message.Supprimer"))) {

			dao.begin(transaction);
				TaTLiens u = dao.findById(((SWTTLiens) selectedTypeLiens.getValue()).getIdTLiens());
				dao.supprimer(u);
			dao.commit(transaction);
			Object suivant=tableViewer.getElementAt(tableViewer.getTable().getSelectionIndex()+1);
				modelTypeLiens.removeEntity(taTLiens);
				taTLiens=null;
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
						.getString("TypeLiens.Message.Annuler")))) {
					remetTousLesChampsApresAnnulationSWT(dbc);
					if(((SWTTLiens) selectedTypeLiens.getValue()).getIdTLiens()==null){
					modelTypeLiens.getListeObjet().remove(((SWTTLiens) selectedTypeLiens.getValue()));
					writableList = new WritableList(realm, modelTypeLiens.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.selectionGrille(0);
					}
					dao.annuler(taTLiens);
					hideDecoratedFields();
				}
				initEtatBouton();
				break;
			case C_MO_EDITION:
				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("TypeLiens.Message.Annuler")))) {
					int rang = modelTypeLiens.getListeObjet().indexOf(selectedTypeLiens.getValue());
					remetTousLesChampsApresAnnulationSWT(dbc);
					modelTypeLiens.getListeObjet().set(rang, swtOldTypeLiens);
					writableList = new WritableList(realm, modelTypeLiens.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.setSelection(new StructuredSelection(swtOldTypeLiens), true);
					dao.annuler(taTLiens);
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
		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();

		String nomChampIdTable =  dao.getChampIdTable();

		FonctionGetInfosXmlAndProperties fonctionGetInfosXmlAndProperties = new FonctionGetInfosXmlAndProperties(mapperUIToModel);
		fonctionGetInfosXmlAndProperties.cleanValueMapAttributeTable();

		fonctionGetInfosXmlAndProperties.setValueMapAttributeTable(TaTLiens.class.getSimpleName()+".head");
		fonctionGetInfosXmlAndProperties.setValueMapAttributeTable(TaTLiens.class.getSimpleName()+".detail");
		
		LinkedHashMap<String,AttributElementResport> mapAttributeTablHead = fonctionGetInfosXmlAndProperties.getMapAttributeTablHead(); 
		LinkedHashMap<String,AttributElementResport> mapAttributeTablDetail = fonctionGetInfosXmlAndProperties.getMapAttributeTablDetail();
		
		Collection<TaTLiens> collectionTaTLiens = modelTypeLiens.getListeEntity();
		
		fonctionGetInfosXmlAndProperties.findInfosFileXml(TaTLiens.class.getName(),SWTTLiens.class.getName(),
				listeChamp,"mapping");
		fonctionGetInfosXmlAndProperties.getInfosObjetJPA(taTLiens);

		ConstEdition constEdition = new ConstEdition(); 
//		Impression impression = new Impression(constEdition,taTLiens,collectionTaTLiens,nomChampIdTable,taTLiens.getIdTLiens());
		String nomDossier = null;

		int nombreLine = collectionTaTLiens.size();

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

			/**
			 * pathFileReport ==> le path de ficher de edition dynamique
			 */
			String folderEditionDynamique = Const.C_RCP_INSTANCE_LOCATION+"/"+Const.C_NOM_PROJET_TMP+"/"+TaTLiens.class.getSimpleName();
			constEdition.makeFolderEditions(folderEditionDynamique);
			Path pathFileReport = new Path(folderEditionDynamique+"/"+Const.C_NOM_VU_T_LIEN+".rptdesign");
			final String pathFileReportDynamic = pathFileReport.toPortableString();

			MakeDynamiqueReport DynamiqueReport = new MakeDynamiqueReport(constEdition.getNameTableEcran(),
					constEdition.getNameTableBDD(),pathFileReportDynamic,Const.C_NOM_VU_T_LIEN,
					ConstEdition.PAGE_ORIENTATION_LANDSCAPE,nomDossier); 
			
			DynamiqueReport.setSimpleNameEntity(TaTLiens.class.getSimpleName());
			/**************************************************************/
			DynamiqueReport.setFonctionGetInfosXml(fonctionGetInfosXmlAndProperties);
			DynamiqueReport.setNomObjet(TaTLiens.class.getSimpleName());
			/**************************************************************/

			Map<String, AttributElementResport> attribuGridHeader = new LinkedHashMap<String, AttributElementResport>();
			String nameHeaderTitle = ConstEditionTiers.TITLE_EDITION_TYPE_LIENS;
			attribuGridHeader.put(nameHeaderTitle, new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
					ConstEdition.FONT_SIZE_XX_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
					ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE,""));
			String nameSousHeaderTitle = ConstEditionTiers.SOUS_TITLE_EDITION_TYPE_LIENS;
			attribuGridHeader.put(nameSousHeaderTitle, new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
					ConstEdition.FONT_SIZE_X_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
					ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE,ConstEdition.COLOR_GRAY));

			//DynamiqueReport.buildDesignConfig();
			ConstEdition.CONTENT_COMMENTS = ConstEditionTiers.COMMENTAIRE_EDITION_DEFAUT;
			DynamiqueReport.initializeBuildDesignReportConfig();
			DynamiqueReport.makePageMater("1", "1", "1", "1", "100");
			DynamiqueReport.makeReportHeaderGrid(3,5,100,ConstEdition.UNITS_PERCENTAGE,attribuGridHeader);
//			DynamiqueReport.makeReportTableDB(100,ConstEdition.UNITS_PERCENTAGE,
//					Const.C_NOM_VU_T_TEL,attribuTabHeader,attribuTabDetail,1,1,2,5,"30");
			DynamiqueReport.biuldTableReport("100", ConstEdition.UNITS_PERCENTAGE, 
					Const.C_NOM_VU_T_LIEN,1,1,2,"40", mapAttributeTablHead, mapAttributeTablDetail);
			DynamiqueReport.savsAsDesignHandle();
//			impression.imprimer(true,pathFileReportDynamic,null,"Type Liens",TaTLiens.class.getSimpleName(),false);
			/** 01/03/2010 modifier les editions (zhaolin) **/
			BaseImpressionEdition impressionEdition = new BaseImpressionEdition(constEdition,taTLiens,
					getEm(),collectionTaTLiens,taTLiens.getIdTLiens());
			impressionEdition.impressionEditionTypeEntity(pathFileReportDynamic,"Lien");
			
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
					if(getFocusCourantSWT().equals(vue.getGrille())){
						PaTypeLiensSWT paTypeLiensSWT = new PaTypeLiensSWT(s2,SWT.NULL);
						SWTPaTypeLiensController swtPaTypeLiensController = new SWTPaTypeLiensController(paTypeLiensSWT);

						editorCreationId = EditorTypeLiens.ID;
						editorInputCreation = new EditorInputTypeLiens();

						ParamAfficheTypeLiens paramAfficheTypeLiens = new ParamAfficheTypeLiens();
						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
						paramAfficheTypeLiens.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheTypeLiens.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaTypeLiensController;
						parametreEcranCreation = paramAfficheTypeLiens;

						paramAfficheAideRecherche.setTypeEntite(TaTLiens.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_T_LIENS);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_T_LIENS().getText());
						paramAfficheAideRecherche.setControllerAppelant(getThis());
						paramAfficheAideRecherche.setModel(swtPaTypeLiensController.getModelTypeLiens());
						paramAfficheAideRecherche.setTypeObjet(swtPaTypeLiensController.getClassModel());
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_T_LIENS);
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
		String validationContext = "T_LIENS";
		try {
			IStatus s = null;
			boolean verrouilleModifCode = false;
			int change=0;
				TaTLiens u = new TaTLiens();
				PropertyUtils.setSimpleProperty(u, nomChamp, value);
				if(((SWTTLiens) selectedTypeLiens.getValue()).getIdTLiens()!=null) {
					u.setIdTLiens(((SWTTLiens) selectedTypeLiens.getValue()).getIdTLiens());
				}
				if(nomChamp.equals(Const.C_CODE_T_LIENS)){
					verrouilleModifCode = true;
				}

				s = dao.validate(u,nomChamp,validationContext,verrouilleModifCode);
				if(s.getSeverity()!=IStatus.ERROR && change!=0) {
					  
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
			if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)||
					(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)) {
				LgrDozerMapper<SWTTLiens,TaTLiens> mapper = new LgrDozerMapper<SWTTLiens,TaTLiens>();
				mapper.map((SWTTLiens) selectedTypeLiens.getValue(),taTLiens);
				
				taTLiens=dao.enregistrerMerge(taTLiens);
//				if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
//					modelTypeLiens.getListeEntity().add(taTLiens);
			} 
			dao.commit(transaction);
			modelTypeLiens.addEntity(taTLiens);
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
			vue.getTfCODE_T_LIENS().setEditable(!isUtilise());
			//vue.getTfLABELLE_T_WEB().setEditable(!isUtilise());
			changeCouleur(vue);
	    } catch (Exception e) {
		vue.getLaMessage().setText(e.getMessage());
	    }
	}

	public boolean isUtilise(){
		return (((SWTTLiens)selectedTypeLiens.getValue()).getIdTLiens()!=null &&
		!dao.recordModifiable(dao.getNomTable(),
				((SWTTLiens)selectedTypeLiens.getValue()).getIdTLiens()))||
				!dao.autoriseModification(taTLiens);		
	}
	
	public SWTTLiens getSwtOldTypeLiens() {
		return swtOldTypeLiens;
	}

	public void setSwtOldTypeLiens(SWTTLiens swtOldTypeLiens) {
		this.swtOldTypeLiens = swtOldTypeLiens;
	}
	

	public void setSwtOldTypeLiens() {
		if (selectedTypeLiens.getValue() != null)
			this.swtOldTypeLiens = SWTTLiens.copy((SWTTLiens) selectedTypeLiens.getValue());
		else {
			if(tableViewer.selectionGrille(0)){
				this.swtOldTypeLiens = SWTTLiens.copy((SWTTLiens) selectedTypeLiens.getValue());
				tableViewer.setSelection(new StructuredSelection(
						(SWTTLiens) selectedTypeLiens.getValue()), true);
			}
		}
	}
	public void setVue(PaTypeLiensSWT vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();
		mapComposantDecoratedField.put(vue.getTfCODE_T_LIENS(), vue.getFieldCODE_T_LIENS());
		mapComposantDecoratedField.put(vue.getTfLABELLE_T_LIENS(), vue.getFieldLABELLE_T_LIENS());
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
		TaTLiens u = taTLiens;
		if (u!=null && u.getIdTLiens()==0)
			u=dao.findById(u.getIdTLiens());
		writableList = new WritableList(realm, modelTypeLiens.remplirListe(), classModel);
		tableViewer.setInput(writableList);
		tableViewer.refresh();
		tableViewer.selectionGrille(
				tableViewer.selectionGrille(selectedTypeLiens.getValue()));
		
		if(u != null) {
			Iterator<SWTTLiens> ite = modelTypeLiens.getListeObjet().iterator();
			SWTTLiens tmp = null;
			int i = 0;
			boolean trouve = false;
			while (ite.hasNext() && !trouve) {
				tmp = ite.next();
				if(tmp.getIdTLiens()==u.getIdTLiens()) {
					tableViewer.setSelection(new StructuredSelection(tmp));
					trouve = true;
				}
				i++;
			}
		}	
	}

	public ModelGeneralObjet<SWTTLiens,TaTLiensDAO,TaTLiens> getModelTypeLiens() {
		return modelTypeLiens;
	}

	public TaTLiensDAO getDao() {
		return dao;
	}


	public void actAfficherTous() throws Exception{
		vue.getGrille().setVisible(true);  
		vue.getBtnTous().setVisible(false);
		vue.getLaTitreGrille().setVisible(true);
		vue.getCompositeForm().setWeights(new int[]{50,100});
		dao.setJPQLQuery(dao.getJPQLQueryInitial());
		modelTypeLiens.setJPQLQuery(null);
		modelTypeLiens.setListeEntity(null);
		try {
			actRefresh();
		} catch (Exception e1) {
			logger.error("", e1);
		}
	}

}

package fr.legrain.articles.champsupp.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.databinding.swt.SWTObservables;
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
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import fr.legrain.articles.champsupp.dao.SWTTypeChampSuppArt;
import fr.legrain.articles.champsupp.dao.TaChampSuppArt;
import fr.legrain.articles.champsupp.dao.TaChampSuppArtDAO;
import fr.legrain.articles.champsupp.editors.PaTypeChampSuppArticle;
import fr.legrain.articles.ecran.MessagesEcran;
import fr.legrain.articles.ecran.ParamAfficheTTva;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard.Traverse;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDeSelectionEvent;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.InfosVerifSaisie;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.data.VerrouInterface;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.lib.gui.aide.ResultAide;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.lib.gui.grille.LgrViewerSupport;
import fr.legrain.libMessageLGR.LgrMess;


public class SWTPaTypeChampSuppArticleController extends JPABaseControllerSWTStandard
implements RetourEcranListener {

	static Logger logger = Logger.getLogger(SWTPaTypeChampSuppArticleController.class.getName());
	private PaTypeChampSuppArticle vue = null;
	private TaChampSuppArtDAO dao = null;

	private Object ecranAppelant = null;
	private SWTTypeChampSuppArt swtTypeChampSupp;
	private SWTTypeChampSuppArt swtOldTypeChampSupp;
	private Realm realm;
	private DataBindingContext dbc;

	private Class classModel = SWTTypeChampSuppArt.class;
	private ModelGeneralObjet<SWTTypeChampSuppArt,TaChampSuppArtDAO,TaChampSuppArt> modelTypeChampSupp = null;
	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private IObservableValue selectedTypeChampSupp;
	private String[] listeChamp;
	private String nomClassController = this.getClass().getSimpleName();

	private TaChampSuppArt taChampSuppArt = null;
	
	private MapChangeListener changeListener = new MapChangeListener();
	
	public SWTPaTypeChampSuppArticleController(PaTypeChampSuppArticle vue) {
		this(vue,null);
	}

	public SWTPaTypeChampSuppArticleController(PaTypeChampSuppArticle vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaChampSuppArtDAO(getEm());
		modelTypeChampSupp = new ModelGeneralObjet<SWTTypeChampSuppArt,TaChampSuppArtDAO,TaChampSuppArt>(dao,classModel);
		setVue(vue);

		// a faire avant l'initialisation du Binding
		vue.getGrille().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setSwtOldTypeChampSupp();
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

	public void bind(PaTypeChampSuppArticle paTTVASWT) {
		try {
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			tableViewer = new LgrTableViewer(paTTVASWT.getGrille());
			tableViewer.createTableCol(classModel,paTTVASWT.getGrille(), nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE,-1);
			listeChamp = tableViewer.setListeChampGrille(nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE);

//			ObservableListContentProvider viewerContent = new ObservableListContentProvider();
//			tableViewer.setContentProvider(viewerContent);
//
//			IObservableMap[] attributeMaps = BeansObservables.observeMaps(
//					viewerContent.getKnownElements(), classModel,listeChamp);
//
//			tableViewer.setLabelProvider(new ObservableMapLabelProvider(attributeMaps));
//			writableList = new WritableList(realm, modelTTva.remplirListe(), classModel);
//			tableViewer.setInput(writableList);
			
			// Set up data binding.
			LgrViewerSupport.bind(tableViewer, 
					new WritableList(modelTypeChampSupp.remplirListe(), classModel),
					BeanProperties.values(listeChamp)
					);

			selectedTypeChampSupp = ViewersObservables.observeSingleSelection(tableViewer);

			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			tableViewer.selectionGrille(0);

			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);

			bindingFormMaitreDetail(dbc, realm, selectedTypeChampSupp,classModel);

		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("", e);
		}
	}

	public Composite getVue() {
		return vue;
	}

	public ResultAffiche configPanel(ParamAffiche param) {
		if (param != null) {
			if (((ParamAfficheTTva) param).getFocusDefautSWT() != null && !((ParamAfficheTTva) param).getFocusDefautSWT().isDisposed())
				((ParamAfficheTTva) param).getFocusDefautSWT().setFocus();
			else
				((ParamAfficheTTva) param).setFocusDefautSWT(vue.getGrille());
			vue.getLaTitreFormulaire().setText(((ParamAfficheTTva) param).getTitreFormulaire());
			vue.getLaTitreGrille().setText(((ParamAfficheTTva) param).getTitreGrille());
			vue.getLaTitreFenetre().setText(((ParamAfficheTTva) param).getSousTitre());

			if (param.getEcranAppelant() != null) {
				ecranAppelant = param.getEcranAppelant();
			}
			Map<String,String[]> map = dao.getParamWhereSQL();
			if(param.getIdFiche()!=null &&  !param.getIdFiche().equals("")) {
				if(map==null) map = new HashMap<String,String[]>();
//				map.clear();
//				map.put("idTTva",new String[]{"=",param.getIdFiche()});
//				dao.setParamWhereSQL(map);
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
			setSwtOldTypeChampSupp();

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
		
		mapInfosVerifSaisie.put(vue.getTfNom(), new InfosVerifSaisie(new TaChampSuppArt(),Const.C_NOM_CHAMP_SUPP_ART,null));
		mapInfosVerifSaisie.put(vue.getTfType(), new InfosVerifSaisie(new TaChampSuppArt(),Const.C_TYPE_VALEUR_CHAMP_SUPP_ART,null));
		mapInfosVerifSaisie.put(vue.getTfDescription(), new InfosVerifSaisie(new TaChampSuppArt(),Const.C_DESCRIPTION_CHAMP_SUPP_ART,null));
		mapInfosVerifSaisie.put(vue.getTfDefaut(), new InfosVerifSaisie(new TaChampSuppArt(),Const.C_DEFAUT_CHAMP_SUPP_ART,null));

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

		vue.getTfNom().setToolTipText(Const.C_NOM_CHAMP_SUPP_ART);
		vue.getTfType().setToolTipText(Const.C_TYPE_VALEUR_CHAMP_SUPP_ART);
		vue.getTfDescription().setToolTipText(Const.C_DESCRIPTION_CHAMP_SUPP_ART);
		vue.getTfDefaut().setToolTipText(Const.C_DEFAUT_CHAMP_SUPP_ART);

		mapComposantChamps.put(vue.getTfNom(), Const.C_NOM_CHAMP_SUPP_ART);
		mapComposantChamps.put(vue.getTfType(), Const.C_TYPE_VALEUR_CHAMP_SUPP_ART);
		mapComposantChamps.put(vue.getTfDescription(), Const.C_DESCRIPTION_CHAMP_SUPP_ART);
		mapComposantChamps.put(vue.getTfDefaut(), Const.C_DEFAUT_CHAMP_SUPP_ART);

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
				.getTfNom());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue
				.getTfNom());
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

	public SWTPaTypeChampSuppArticleController getThis() {
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
					.getString("TTva.Message.Enregistrer"))) {

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
					dao.annuler(taChampSuppArt);
				} catch (Exception e) {
					throw new ExceptLgr();
				}
			if (ecranAppelant instanceof SWTPaAideControllerSWT) {
				setListeEntity(getModelTypeChampSupp().remplirListe());
				dao.initValeurIdTable(taChampSuppArt);
				fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
						dao.getChampIdEntite(), dao.getValeurIdTable(),
						selectedTypeChampSupp.getValue())));

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
					((Text) getFocusAvantAideSWT()).setText(((ResultAffiche) evt
							.getRetour()).getResult());					
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
		try {
			VerrouInterface.setVerrouille(true);
			setSwtOldTypeChampSupp();
			swtTypeChampSupp = new SWTTypeChampSuppArt();
			taChampSuppArt = new TaChampSuppArt();
			dao.inserer(taChampSuppArt);
			modelTypeChampSupp.getListeObjet().add(swtTypeChampSupp);
			writableList = new WritableList(realm, modelTypeChampSupp.getListeObjet(), classModel);
			tableViewer.setInput(writableList);
			tableViewer.refresh();
			tableViewer.setSelection(new StructuredSelection(swtTypeChampSupp));
			initEtatBouton();
		} finally {
			initEtatComposant();
			VerrouInterface.setVerrouille(false);
		}
	}

	@Override
	protected void actModifier() throws Exception {
		try {
			if(!LgrMess.isDOSSIER_EN_RESEAU()){
				setSwtOldTypeChampSupp();
				taChampSuppArt = dao.findById(((SWTTypeChampSuppArt) selectedTypeChampSupp.getValue()).getIdChampSuppArt());
			}else{
				if(!setSwtOldTTvaRefresh())throw new Exception();
			}
			
			dao.modifier(taChampSuppArt);
			
			initEtatBouton();
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		}
	}
	
	public boolean containsEntity(TaChampSuppArt entite){
		if(modelTypeChampSupp.getListeEntity()!=null){
			for (Object e : modelTypeChampSupp.getListeEntity()) {
				if(((TaChampSuppArt)e).getIdChampSuppArt()==
					entite.getIdChampSuppArt())return true;
			}
		}
		return false;
	}

	public boolean setSwtOldTTvaRefresh() {
		try {	
			if (selectedTypeChampSupp.getValue()!=null){
				TaChampSuppArt taArticleOld =dao.findById(taChampSuppArt.getIdChampSuppArt());
				taArticleOld=dao.refresh(taArticleOld);
				if(containsEntity(taChampSuppArt)) 
					modelTypeChampSupp.getListeEntity().remove(taChampSuppArt);
				if(!taChampSuppArt.getVersionObj().equals(taArticleOld.getVersionObj())){
					taChampSuppArt=taArticleOld;
					if(!containsEntity(taChampSuppArt)) 
						modelTypeChampSupp.getListeEntity().add(taChampSuppArt);					
					actRefresh();
					dao.messageNonAutoriseModification();
				}
				taChampSuppArt=taArticleOld;
				if(!containsEntity(taChampSuppArt)) 
					modelTypeChampSupp.getListeEntity().add(taChampSuppArt);
				changementDeSelection();
				this.swtOldTypeChampSupp=SWTTypeChampSuppArt.copy((SWTTypeChampSuppArt)selectedTypeChampSupp.getValue());
			}
			return true;
		} catch (Exception e) {
			return false;
		}		
	}

//	public void setSwtOldTTvaRefresh() {
//		if (selectedTTva.getValue()!=null){
//			if(LgrMess.isDOSSIER_EN_RESEAU())dao.refresh(dao.findById(((SWTChampSuppArt) selectedTTva.getValue()).getIdTTva()));
//			taTTva=dao.findById(((SWTChampSuppArt) selectedTTva.getValue()).getIdTTva());
//			try {
//				if(LgrMess.isDOSSIER_EN_RESEAU())actRefresh();
//			} catch (Exception e) {
//				logger.error("",e);
//			}			
//			this.swtOldTTva=SWTChampSuppArt.copy((SWTChampSuppArt)selectedTTva.getValue());
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
					.getString("TTva.Message.Supprimer"))) {
			dao.begin(transaction);
				TaChampSuppArt u = dao.findById(((SWTTypeChampSuppArt) selectedTypeChampSupp.getValue()).getIdChampSuppArt());
				dao.supprimer(u);
			dao.commit(transaction);
			modelTypeChampSupp.removeEntity(u);
			taChampSuppArt=null;
			dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
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
						.getString("TTva.Message.Annuler")))) {
					remetTousLesChampsApresAnnulationSWT(dbc);
					if(((SWTTypeChampSuppArt) selectedTypeChampSupp.getValue()).getIdChampSuppArt()==null){
					modelTypeChampSupp.getListeObjet().remove(
							((SWTTypeChampSuppArt) selectedTypeChampSupp.getValue()));
					writableList = new WritableList(realm, modelTypeChampSupp
							.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.selectionGrille(0);
					}
					dao.annuler(taChampSuppArt);
					hideDecoratedFields();
				}
				initEtatBouton();
				break;
			case C_MO_EDITION:
				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("TTva.Message.Annuler")))) {
					int rang = modelTypeChampSupp.getListeObjet().indexOf(selectedTypeChampSupp.getValue());
					remetTousLesChampsApresAnnulationSWT(dbc);
					modelTypeChampSupp.getListeObjet().set(rang, swtOldTypeChampSupp);
					writableList = new WritableList(realm, modelTypeChampSupp.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.setSelection(new StructuredSelection(swtOldTypeChampSupp), true);
					dao.annuler(taChampSuppArt);
					hideDecoratedFields();
				}
				initEtatBouton();

				break;
			case C_MO_CONSULTATION:
				actionFermer.run();
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

	}
	
	protected void initEtatBouton() {
		super.initEtatBouton();
		enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,false);
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
//			try {
//				/*
//				VerrouInterface.setVerrouille(true);
//				vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
//				ParamAfficheAideRechercheSWT paramAfficheAideRecherche = new ParamAfficheAideRechercheSWT();
////				paramAfficheAideRecherche.setDb(getThis().getIbTaTable().getFIBBase());
//				paramAfficheAideRecherche.setMessage(message);
//				// Creation de l'ecran d'aide principal
//				Shell s = new Shell(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),LgrShellUtil.styleLgr);
//				PaAideSWT paAide = new PaAideSWT(s, SWT.NONE);
//				SWTPaAideControllerSWT paAideController = new SWTPaAideControllerSWT(paAide);
//				/***************************************************************/
//				LgrPartListener.getLgrPartListener().setLgrActivePart(null);
//				IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new EditorInputAide(), EditorAide.ID);
//				LgrPartListener.getLgrPartListener().setLgrActivePart(e);
//				LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
//				paAideController = new SWTPaAideControllerSWT(((EditorAide)e).getComposite());
//				((LgrEditorPart)e).setController(paAideController);
//				((LgrEditorPart)e).setPanel(((EditorAide)e).getComposite());
//				/***************************************************************/
//				JPABaseControllerSWTStandard controllerEcranCreation = null;
//				ParamAffiche parametreEcranCreation = null;
//				IEditorPart editorCreation = null;
//				String editorCreationId = null;
//				IEditorInput editorInputCreation = null;
//				Shell s2 = new Shell(s, LgrShellUtil.styleLgr);
//
//				switch ((getThis().dao.getModeObjet().getMode())) {
//				case C_MO_CONSULTATION:
//					if(getFocusCourantSWT().equals(vue.getGrille())){
//						PaTypeChampSuppArticle paTTvaSWT = new PaTypeChampSuppArticle(s2,SWT.NULL);
//						SWTPaTypeChampSuppArticleController swtPaTTvaController = new SWTPaTypeChampSuppArticleController(paTTvaSWT);
//
//						editorCreationId = EditorTypeTVA.ID;
//						editorInputCreation = new EditorInputTypeTVA();
//						
//						paramAfficheAideRecherche.setForceAffichageAideRemplie(true);
//
//
//						ParamAfficheTTva paramAfficheTTva = new ParamAfficheTTva();
//						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
//						paramAfficheTTva.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//						paramAfficheTTva.setEcranAppelant(paAideController);
//						controllerEcranCreation = swtPaTTvaController;
//						parametreEcranCreation = paramAfficheTTva;
//
//						paramAfficheAideRecherche.setTypeEntite(TaChampSuppArt.class);
//						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_T_TVA);
//						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_T_TVA().getText());
//						paramAfficheAideRecherche.setControllerAppelant(getThis());
//						paramAfficheAideRecherche.setModel(swtPaTTvaController.getModelTypeChampSupp());
//						paramAfficheAideRecherche.setTypeObjet(swtPaTTvaController.getClassModel());
//						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_T_TVA);
//					}
//					break;
//				case C_MO_EDITION:
//				case C_MO_INSERTION:
//					break;
//				default:
//					break;
//				}
//
//				if (paramAfficheAideRecherche.getJPQLQuery() != null) {
//
//					PaAideRechercheSWT paAideRecherche1 = new PaAideRechercheSWT(s,
//							SWT.NULL);
//					SWTPaAideRechercheControllerSWT paAideRechercheController1 = new SWTPaAideRechercheControllerSWT(
//							paAideRecherche1);
//
//					// Parametrage de la recherche
//					paramAfficheAideRecherche.setFocusSWT(((PaAideRechercheSWT) paAideRechercheController1.getVue()).getTfChoix());
//					paramAfficheAideRecherche.setRefCreationSWT(controllerEcranCreation);
//					paramAfficheAideRecherche.setEditorCreation(editorCreation);
//					paramAfficheAideRecherche.setEditorCreationId(editorCreationId);
//					paramAfficheAideRecherche.setEditorInputCreation(editorInputCreation);
//					paramAfficheAideRecherche.setParamEcranCreation(parametreEcranCreation);
//					paramAfficheAideRecherche.setShellCreation(s2);
//					paAideRechercheController1.configPanel(paramAfficheAideRecherche);
//
//					// Ajout d'une recherche
//					paAideController.addRecherche(paAideRechercheController1,
//							paramAfficheAideRecherche.getTitreRecherche());
//
//					// Parametrage de l'ecran d'aide principal
//					ParamAfficheAideSWT paramAfficheAideSWT = new ParamAfficheAideSWT();
//					ParamAfficheAide paramAfficheAide = new ParamAfficheAide();
//
//					// enregistrement pour le retour de l'ecran d'aide
//					paAideController.addRetourEcranListener(getThis());
//					// affichage de l'ecran d'aide principal (+ ses recherches)
//
//					dbc.getValidationStatusMap().removeMapChangeListener(changeListener);
//					/*****************************************************************/
//					paAideController.configPanel(paramAfficheAide);
//					/*****************************************************************/
//					dbc.getValidationStatusMap().addMapChangeListener(changeListener);
//
//				}
//
//			} finally {
//				VerrouInterface.setVerrouille(false);
//				vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
//			}
		}
	}
	
	public IStatus validateUI() {
		return null;
	}
	
	public IStatus validateUIField(String nomChamp,Object value) {
		String validationContext = "T_CHAMP_SUPP_ART";
		try {
			IStatus s = null;
			boolean verrouilleModifCode = false;
			int change=0;

				TaChampSuppArt u = new TaChampSuppArt();
				PropertyUtils.setSimpleProperty(u, nomChamp, value);
				if(((SWTTypeChampSuppArt) selectedTypeChampSupp.getValue()).getIdChampSuppArt()!=null) {
					u.setIdChampSuppArt(((SWTTypeChampSuppArt) selectedTypeChampSupp.getValue()).getIdChampSuppArt());
				}
				
//				if(nomChamp.equals(Const.C_CODE_T_TVA)){
//					verrouilleModifCode = true;
//				}

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
			
			if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
				LgrDozerMapper<SWTTypeChampSuppArt,TaChampSuppArt> mapper = new LgrDozerMapper<SWTTypeChampSuppArt,TaChampSuppArt>();
				mapper.map((SWTTypeChampSuppArt) selectedTypeChampSupp.getValue(),taChampSuppArt);

				dao.enregistrerMerge(taChampSuppArt);

				
			} else if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)) {
				LgrDozerMapper<SWTTypeChampSuppArt,TaChampSuppArt> mapper = new LgrDozerMapper<SWTTypeChampSuppArt,TaChampSuppArt>();
				mapper.map((SWTTypeChampSuppArt) selectedTypeChampSupp.getValue(),taChampSuppArt);

				taChampSuppArt=dao.enregistrerMerge(taChampSuppArt);
//				modelTTva.getListeEntity().add(taTTva);
			}
			
			dao.commit(transaction);
			modelTypeChampSupp.addEntity(taChampSuppArt);
			transaction = null;
			
			actRefresh(); //deja present
			
		} finally {
			if(transaction!=null && transaction.isActive()) {
				transaction.rollback();
			}
			initEtatBouton();
		}
	}

	public void initEtatComposant() {
		try {
//			vue.getTfCODE_T_TVA().setEditable(!isUtilise());
			changeCouleur(vue);
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
		}

	}
	public boolean isUtilise(){
		return (((SWTTypeChampSuppArt)selectedTypeChampSupp.getValue()).getIdChampSuppArt()!=null &&
		!dao.recordModifiable(dao.getNomTable(),
				((SWTTypeChampSuppArt)selectedTypeChampSupp.getValue()).getIdChampSuppArt()))||!dao.autoriseModification(taChampSuppArt);		
	}
	public SWTTypeChampSuppArt getSwtOldTypeChampSupp() {
		return swtOldTypeChampSupp;
	}

	public void setSwtOldTypeChampSupp(SWTTypeChampSuppArt swtOldTypeChampSupp) {
		this.swtOldTypeChampSupp = swtOldTypeChampSupp;
	}

	public void setSwtOldTypeChampSupp() {
		if (selectedTypeChampSupp.getValue() != null)
			this.swtOldTypeChampSupp = SWTTypeChampSuppArt.copy((SWTTypeChampSuppArt) selectedTypeChampSupp.getValue());
		else {
			if(tableViewer.selectionGrille(0)){
				this.swtOldTypeChampSupp = SWTTypeChampSuppArt.copy((SWTTypeChampSuppArt) selectedTypeChampSupp.getValue());
				tableViewer.setSelection(new StructuredSelection(
						(SWTTypeChampSuppArt) selectedTypeChampSupp.getValue()), true);
			}}
	}

	public void setVue(PaTypeChampSuppArticle vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();

		mapComposantDecoratedField.put(vue.getTfNom(), vue.getFieldNom());
		mapComposantDecoratedField.put(vue.getTfType(), vue.getFieldType());
		mapComposantDecoratedField.put(vue.getTfDescription(), vue.getFieldDescription());
		mapComposantDecoratedField.put(vue.getTfDefaut(), vue.getFieldDefaut());
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
		if (taChampSuppArt!=null) { //enregistrement en cours de modification/insertion
			idActuel = taChampSuppArt.getIdChampSuppArt();
		} else if(selectedTypeChampSupp!=null && (SWTTypeChampSuppArt) selectedTypeChampSupp.getValue()!=null) {
			idActuel = ((SWTTypeChampSuppArt) selectedTypeChampSupp.getValue()).getIdChampSuppArt();
		}

		if (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION) == 0) {
			//rafraichissement des valeurs dans la grille
			writableList = new WritableList(realm, modelTypeChampSupp.remplirListe(), classModel);
			tableViewer.setInput(writableList);
		} else {
			if (taChampSuppArt!=null && selectedTypeChampSupp!=null && (SWTTypeChampSuppArt) selectedTypeChampSupp.getValue()!=null) {
				new LgrDozerMapper<TaChampSuppArt, SWTTypeChampSuppArt>().
					map(taChampSuppArt, (SWTTypeChampSuppArt) selectedTypeChampSupp.getValue());
			}
		}

		if(idActuel!=0) {
			tableViewer.setSelection(new StructuredSelection(modelTypeChampSupp.recherche(Const.C_ID_CHAMP_SUPP_ART, idActuel)));
		}else
			tableViewer.selectionGrille(0);		
	}

	public ModelGeneralObjet<SWTTypeChampSuppArt,TaChampSuppArtDAO,TaChampSuppArt> getModelTypeChampSupp() {
		return modelTypeChampSupp;
	}

	public TaChampSuppArtDAO getDao() {
		return dao;
	}

	public TaChampSuppArt getTaChampSuppArt() {
		return taChampSuppArt;
	}
	private void changementDeSelection() {
		if(selectedTypeChampSupp!=null && selectedTypeChampSupp.getValue()!=null) {
			if(((SWTTypeChampSuppArt) selectedTypeChampSupp.getValue()).getIdChampSuppArt()!=null) {
				taChampSuppArt = dao.findById(((SWTTypeChampSuppArt) selectedTypeChampSupp.getValue()).getIdChampSuppArt());
			}
			//null a tester ailleurs, car peut etre null en cas d'insertion
			fireChangementDeSelection(new ChangementDeSelectionEvent(SWTPaTypeChampSuppArticleController.this));
		}
	}

	public void actAfficherTous() throws Exception{
		vue.getGrille().setVisible(true);  
		vue.getBtnTous().setVisible(false);
		vue.getLaTitreGrille().setVisible(true);
		vue.getCompositeForm().setWeights(new int[]{50,100});
		dao.setJPQLQuery(dao.getJPQLQueryInitial());
		modelTypeChampSupp.setJPQLQuery(null);
		modelTypeChampSupp.setListeEntity(null);
		try {
			actRefresh();
		} catch (Exception e1) {
			logger.error("", e1);
		}
	}
}

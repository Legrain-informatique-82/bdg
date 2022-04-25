package fr.legrain.articles.ecran;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
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
import fr.legrain.articles.dao.TaTConditionnement;
import fr.legrain.articles.dao.TaTConditionnementDAO;
import fr.legrain.articles.dao.TaTva;
import fr.legrain.articles.dao.TaTvaDAO;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.articles.divers.Impression;
import fr.legrain.articles.editor.EditorInputTva;
import fr.legrain.articles.editor.EditorTva;
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
import fr.legrain.gestCom.Module_Articles.SWTTConditionnement;
import fr.legrain.gestCom.Module_Articles.SWTTva;
import fr.legrain.gestCom.Module_Articles.SWTUnite;
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


public class SWTPaTypeConditionnementController extends JPABaseControllerSWTStandard
implements RetourEcranListener {

	static Logger logger = Logger.getLogger(SWTPaTypeConditionnementController.class.getName());
	private PaTConditionnementSWT vue = null;
	private TaTConditionnementDAO dao = null;//new TaTConditionnementDAO(getEm());

	private Object ecranAppelant = null;
	private SWTTConditionnement swtTConditionnement;
	private SWTTConditionnement swtOldTConditionnement;
	private Realm realm;
	private DataBindingContext dbc;

	private Class classModel = SWTTConditionnement.class;
	private ModelGeneralObjet<SWTTConditionnement,TaTConditionnementDAO,TaTConditionnement> modelTConditionnement = null;//new ModelGeneralObjet<SWTTConditionnement,TaTConditionnementDAO,TaTConditionnement>(dao,classModel);
	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private IObservableValue selectedTConditionnement;
	private String[] listeChamp;
	private String nomClassController = this.getClass().getSimpleName();
	
	private TaTConditionnement taTConditionnement = null;

	private MapChangeListener changeListener = new MapChangeListener();
	
	private LgrDozerMapper<SWTTConditionnement,TaTConditionnement> mapperUIToModel = new LgrDozerMapper<SWTTConditionnement,TaTConditionnement>();
	
	public SWTPaTypeConditionnementController(PaTConditionnementSWT vue) {
		this(vue,null);
	}

	public SWTPaTypeConditionnementController(PaTConditionnementSWT vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaTConditionnementDAO(getEm());
		modelTConditionnement = new ModelGeneralObjet<SWTTConditionnement,TaTConditionnementDAO,TaTConditionnement>(dao,classModel);
		setVue(vue);

		// a faire avant l'initialisation du Binding
		vue.getGrille().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setSwtOldTConditionnement();
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
			Menu[] tabPopups = new Menu[] { popupMenuFormulaire,
					popupMenuGrille };
			this.initPopupAndButtons(mapActions, tabPopups);
			vue.getPaCorpsFormulaire().setMenu(popupMenuFormulaire);
			vue.getPaGrille().setMenu(popupMenuGrille);
			
			
			initEtatBouton();
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaArticlesController", e);
		}
	}

	public void bind(PaTConditionnementSWT paTVASWT) {
		try {
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			tableViewer = new LgrTableViewer(paTVASWT.getGrille());
			tableViewer.createTableCol(classModel,paTVASWT.getGrille(), nomClassController,
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
//			writableList = new WritableList(realm, modelTva.remplirListe(), classModel);
//			tableViewer.setInput(writableList);
			
			// Set up data binding.
			LgrViewerSupport.bind(tableViewer, 
					new WritableList(modelTConditionnement.remplirListe(), classModel),
					BeanProperties.values(listeChamp)
					);

			selectedTConditionnement = ViewersObservables.observeSingleSelection(tableViewer);

			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			tableViewer.selectionGrille(0);

			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);

			bindingFormMaitreDetail(dbc, realm, selectedTConditionnement,classModel);
			changementDeSelection();
			selectedTConditionnement.addChangeListener(new IChangeListener() {

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
		if(selectedTConditionnement!=null && selectedTConditionnement.getValue()!=null) {
			if(((SWTTConditionnement) selectedTConditionnement.getValue()).getId()!=null) {
				taTConditionnement = dao.findById(((SWTTConditionnement) selectedTConditionnement.getValue()).getId());
			}
			//null a tester ailleurs, car peut etre null en cas d'insertion
			fireChangementDeSelection(new ChangementDeSelectionEvent(SWTPaTypeConditionnementController.this));
		}
	}

	public Composite getVue() {
		return vue;
	}

	public ResultAffiche configPanel(ParamAffiche param) {
		if (param != null) {
			if (((ParamAfficheTypeConditionnement) param).getFocusDefautSWT() != null && !((ParamAfficheTypeConditionnement) param).getFocusDefautSWT().isDisposed())
				((ParamAfficheTypeConditionnement) param).getFocusDefautSWT().setFocus();
			else
				((ParamAfficheTypeConditionnement) param).setFocusDefautSWT(vue.getGrille());
			vue.getLaTitreFormulaire().setText(((ParamAfficheTypeConditionnement) param).getTitreFormulaire());
			vue.getLaTitreGrille().setText(((ParamAfficheTypeConditionnement) param).getTitreGrille());
			vue.getLaTitreFenetre().setText(((ParamAfficheTypeConditionnement) param).getSousTitre());

			if (param.getEcranAppelant() != null) {
				ecranAppelant = param.getEcranAppelant();
			}
			Map<String,String[]> map = dao.getParamWhereSQL();
			if(param.getIdFiche()!=null &&  !param.getIdFiche().equals("")) {
				if(map==null) map = new HashMap<String,String[]>();
				map.clear();
				map.put("idTva",new String[]{"=",param.getIdFiche()});
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
			setSwtOldTConditionnement();

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
		
//		mapInfosVerifSaisie.put(vue.getTfCodeType(), new InfosVerifSaisie(new TaTConditionnement(),Const.C_CODE_T_CONDITIONNEMENT,null));
//		mapInfosVerifSaisie.put(vue.getTfLibelle(), new InfosVerifSaisie(new TaTConditionnement(),Const.C_LIBELLE_T_CONDITIONNEMENT,null));
//		mapInfosVerifSaisie.put(vue.getTfHauteur(), new InfosVerifSaisie(new TaTConditionnement(),Const.C_HAUTEUR_T_CONDITIONNEMENT,null));
//		mapInfosVerifSaisie.put(vue.getTfLargeur(), new InfosVerifSaisie(new TaTConditionnement(),Const.C_LARGEUR_T_CONDITIONNEMENT,new int[]{InfosVerifSaisie.CTRL_QUE_DES_CHIFFRES}));

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

		vue.getTfCodeType().setToolTipText(Const.C_CODE_T_CONDITIONNEMENT);
		vue.getTfHauteur().setToolTipText(Const.C_HAUTEUR_T_CONDITIONNEMENT);
		vue.getTfLargeur().setToolTipText(Const.C_LARGEUR_T_CONDITIONNEMENT);
		vue.getTfLibelle().setToolTipText(Const.C_LIBELLE_T_CONDITIONNEMENT);
		vue.getTfLongueur().setToolTipText(Const.C_LONGUEUR_T_CONDITIONNEMENT);
		vue.getTfPoids().setToolTipText(Const.C_POIDS_T_CONDITIONNEMENT);

		mapComposantChamps.put(vue.getTfCodeType(), Const.C_CODE_T_CONDITIONNEMENT);
		mapComposantChamps.put(vue.getTfHauteur(), Const.C_HAUTEUR_T_CONDITIONNEMENT);
		mapComposantChamps.put(vue.getTfLargeur(), Const.C_LARGEUR_T_CONDITIONNEMENT);
		mapComposantChamps.put(vue.getTfLibelle(), Const.C_LIBELLE_T_CONDITIONNEMENT);
		mapComposantChamps.put(vue.getTfLongueur(), Const.C_LONGUEUR_T_CONDITIONNEMENT);
		mapComposantChamps.put(vue.getTfPoids(), Const.C_POIDS_T_CONDITIONNEMENT);

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
				.getTfCodeType());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue
				.getTfCodeType());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION, vue
				.getGrille());

		activeModifytListener();
//		vue.getTfNUMCPT_TVA().addVerifyListener(queDesChiffres);
//		vue.getTfTAUX_TVA().addVerifyListener(queDesChiffres);
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

	public SWTPaTypeConditionnementController getThis() {
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
					.getString("Tva.Message.Enregistrer"))) {

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
					dao.annuler(taTConditionnement);
				} catch (Exception e) {
					throw new ExceptLgr();
				}
			if (ecranAppelant instanceof SWTPaAideControllerSWT) {
				setListeEntity(getModelTConditionnement().remplirListe());
				dao.initValeurIdTable(taTConditionnement);
				fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
						dao.getChampIdEntite(), dao.getValeurIdTable(),
						selectedTConditionnement.getValue())));

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
			setSwtOldTConditionnement();
			swtTConditionnement = new SWTTConditionnement();
			taTConditionnement = new TaTConditionnement();
//			swtTConditionnement.setTauxTva(new BigDecimal(0));
//			swtTConditionnement.setNumcptTva("445");
			dao.inserer(taTConditionnement);
			modelTConditionnement.getListeObjet().add(swtTConditionnement);
			writableList = new WritableList(realm, modelTConditionnement.getListeObjet(), classModel);
			tableViewer.setInput(writableList);
			tableViewer.refresh();
			tableViewer.setSelection(new StructuredSelection(swtTConditionnement));
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
				setSwtOldTConditionnement();
				taTConditionnement = dao.findById(((SWTTConditionnement) selectedTConditionnement.getValue()).getId());			
			}else{
				if(!setSwtOldTvaRefresh())throw new Exception();
			}
			dao.modifier(taTConditionnement);

			initEtatBouton();
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		}
	}
	
	
	public boolean containsEntity(TaTConditionnement entite){
		if(modelTConditionnement.getListeEntity()!=null){
			for (Object e : modelTConditionnement.getListeEntity()) {
				if(((TaTConditionnement)e).getId()==
					entite.getId())return true;
			}
		}
		return false;
	}


	public boolean setSwtOldTvaRefresh() {
		try {	
			if (selectedTConditionnement.getValue()!=null){
				TaTConditionnement taArticleOld =dao.findById(taTConditionnement.getId());
				taArticleOld=dao.refresh(taArticleOld);
				if(containsEntity(taTConditionnement)) 
					modelTConditionnement.getListeEntity().remove(taTConditionnement);
				if(!taTConditionnement.getVersionObj().equals(taArticleOld.getVersionObj())){
					taTConditionnement=taArticleOld;
					if(!containsEntity(taTConditionnement)) 
						modelTConditionnement.getListeEntity().add(taTConditionnement);					
					actRefresh();
					dao.messageNonAutoriseModification();
				}
				taTConditionnement=taArticleOld;
				if(!containsEntity(taTConditionnement)) 
					modelTConditionnement.getListeEntity().add(taTConditionnement);
				changementDeSelection();
				this.swtOldTConditionnement=SWTTConditionnement.copy((SWTTConditionnement)selectedTConditionnement.getValue());
			}
			return true;
		} catch (Exception e) {
			return false;
		}		
	}

//	public void setSwtOldTvaRefresh() {
//		if (selectedTva.getValue()!=null){
//			if(LgrMess.isDOSSIER_EN_RESEAU())dao.refresh(dao.findById(((SWTTConditionnement) selectedTva.getValue()).getIdTva()));
//			taTva=dao.findById(((SWTTConditionnement) selectedTva.getValue()).getIdTva());
//			try {
//				if(LgrMess.isDOSSIER_EN_RESEAU())actRefresh();
//			} catch (Exception e) {
//				logger.error("",e);
//			}			
//			this.swtOldTva=SWTTConditionnement.copy((SWTTConditionnement)selectedTva.getValue());
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
						.getString("Tva.Message.Supprimer"))) {


					dao.begin(transaction);
					TaTConditionnement u = dao.findById(((SWTTConditionnement) selectedTConditionnement.getValue()).getId());
					dao.supprimer(u);
					dao.commit(transaction);
					Object suivant=tableViewer.getElementAt(tableViewer.getTable().getSelectionIndex()+1);
					modelTConditionnement.removeEntity(u);
					taTConditionnement=null;
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
						.getString("Tva.Message.Annuler")))) {
					remetTousLesChampsApresAnnulationSWT(dbc);
					if(((SWTTConditionnement) selectedTConditionnement.getValue()).getId()==null){
					modelTConditionnement.getListeObjet().remove(
							((SWTTConditionnement) selectedTConditionnement.getValue()));
					writableList = new WritableList(realm, modelTConditionnement
							.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.selectionGrille(0);
					}
					dao.annuler(taTConditionnement);
					hideDecoratedFields();
				}
				initEtatBouton();
				break;
			case C_MO_EDITION:
				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("Tva.Message.Annuler")))) {
					int rang = modelTConditionnement.getListeObjet().indexOf(selectedTConditionnement.getValue());
					remetTousLesChampsApresAnnulationSWT(dbc);
					modelTConditionnement.getListeObjet().set(rang, swtOldTConditionnement);
					writableList = new WritableList(realm, modelTConditionnement.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.setSelection(new StructuredSelection(swtOldTConditionnement), true);
					dao.annuler(taTConditionnement);
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
		/******************************/
		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();

		String nomChampIdTable =  dao.getChampIdTable();
		
		FonctionGetInfosXmlAndProperties fonctionGetInfosXmlAndProperties = new FonctionGetInfosXmlAndProperties(mapperUIToModel);
		fonctionGetInfosXmlAndProperties.cleanValueMapAttributeTable();

		fonctionGetInfosXmlAndProperties.setValueMapAttributeTable(TaTConditionnement.class.getSimpleName()+".head");
		fonctionGetInfosXmlAndProperties.setValueMapAttributeTable(TaTConditionnement.class.getSimpleName()+".detail");
		

		LinkedHashMap<String,AttributElementResport> mapAttributeTablHead = fonctionGetInfosXmlAndProperties.getMapAttributeTablHead(); 
		LinkedHashMap<String,AttributElementResport> mapAttributeTablDetail = fonctionGetInfosXmlAndProperties.getMapAttributeTablDetail();
		
		Collection<TaTConditionnement> collectionTaTConditionnement = modelTConditionnement.getListeEntity();
		fonctionGetInfosXmlAndProperties.findInfosFileXml(TaTConditionnement.class.getName(),SWTTConditionnement.class.getName(),
				listeChamp,"mapping");
		fonctionGetInfosXmlAndProperties.getInfosObjetJPA(taTConditionnement);

		ConstEdition constEdition = new ConstEdition(); 
		
//		Impression impression = new Impression(constEdition,taTva,collectionTaTConditionnement,nomChampIdTable,
//				taTva.getIdTva());
		
		String nomDossier = null;
		int nombreLine = collectionTaTConditionnement.size();

		if(nombreLine==0){
			MessageDialog.openWarning(vue.getShell(), ConstEdition.TITRE_MESSAGE_EDITION_VIDE,
					ConstEdition.EDITION_VIDE);
		}
		else{
			if(taInfoEntreprise.getIdInfoEntreprise()==0){
				nomDossier = constEdition.INFOS_VIDE;
			}
			else{
				nomDossier = taInfoEntreprise.getNomInfoEntreprise();	
			}
			
			constEdition.addValueList(tableViewer, nomClassController);
			
			String folderEditionDynamique = Const.C_RCP_INSTANCE_LOCATION+ConstEdition.SEPARATOR+
			Const.C_NOM_PROJET_TMP+ConstEdition.SEPARATOR+TaTConditionnement.class.getSimpleName();
			constEdition.makeFolderEditions(folderEditionDynamique);
			Path pathFileReport = new Path(folderEditionDynamique+ConstEdition.SEPARATOR+Const.C_NOM_VU_TVA+".rptdesign");
			final String pathFileReportDynamic = pathFileReport.toPortableString();
			
			MakeDynamiqueReport DynamiqueReport = new MakeDynamiqueReport(constEdition.getNameTableEcran(),
					constEdition.getNameTableBDD(),pathFileReportDynamic,Const.C_NOM_VU_TVA,
					ConstEdition.PAGE_ORIENTATION_LANDSCAPE,nomDossier); 
			
			/**************************************************************/
			DynamiqueReport.setFonctionGetInfosXml(fonctionGetInfosXmlAndProperties);
			DynamiqueReport.setNomObjet(TaTConditionnement.class.getSimpleName());
			/**************************************************************/

			Map<String, AttributElementResport> attribuGridHeader = new LinkedHashMap<String, AttributElementResport>();
			String nameHeaderTitle = ConstEditionArticle.TITLE_EDITION_ARTICLE_TVA;
			attribuGridHeader.put(nameHeaderTitle, new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
					ConstEdition.FONT_SIZE_XX_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
					ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE,""));
			String nameSousHeaderTitle = ConstEditionArticle.SOUS_TITLE_EDITION_ARTICLE_TVA;
			attribuGridHeader.put(nameSousHeaderTitle, new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
					ConstEdition.FONT_SIZE_X_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
					ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE,ConstEdition.COLOR_GRAY));

			//DynamiqueReport.buildDesignConfig();
			ConstEdition.CONTENT_COMMENTS = ConstEditionArticle.COMMENTAIRE_EDITION_DEFAUT;
			DynamiqueReport.setSimpleNameEntity(TaTConditionnement.class.getSimpleName());
			DynamiqueReport.initializeBuildDesignReportConfig();
			DynamiqueReport.makePageMater("1", "1", "1", "1", "100");
			DynamiqueReport.makeReportHeaderGrid(3,5,100,ConstEdition.UNITS_PERCENTAGE,attribuGridHeader);
			DynamiqueReport.biuldTableReport("100", ConstEdition.UNITS_PERCENTAGE, 
					Const.C_NOM_VU_TVA,1,1,2,"40", mapAttributeTablHead, mapAttributeTablDetail);
			DynamiqueReport.savsAsDesignHandle();
			
//			impression.imprimer(true,pathFileReportDynamic,null,"Tva",TaTConditionnement.class.getSimpleName(),false);
			BaseImpressionEdition impressionEdition = new BaseImpressionEdition(constEdition,taTConditionnement,
					getEm(),collectionTaTConditionnement,taTConditionnement.getId());
			impressionEdition.impressionEditionTypeEntity(pathFileReportDynamic,"TVA");
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
						PaTConditionnementSWT paTvaSWT = new PaTConditionnementSWT(s2,SWT.NULL);
						SWTPaTypeConditionnementController swtPaTvaController = new SWTPaTypeConditionnementController(paTvaSWT);

						editorCreationId = EditorTva.ID;
						editorInputCreation = new EditorInputTva();

						ParamAfficheTypeConditionnement paramAfficheTypeConditionnement = new ParamAfficheTypeConditionnement();
						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
						paramAfficheTypeConditionnement.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheTypeConditionnement.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaTvaController;
						parametreEcranCreation = paramAfficheTypeConditionnement;
						
						paramAfficheAideRecherche.setForceAffichageAideRemplie(true);


						paramAfficheAideRecherche.setTypeEntite(TaTConditionnement.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_T_CONDITIONNEMENT);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCodeType().getText());
						paramAfficheAideRecherche.setControllerAppelant(SWTPaTypeConditionnementController.this);
						paramAfficheAideRecherche.setModel(swtPaTvaController.getModelTConditionnement());
						paramAfficheAideRecherche.setTypeObjet(swtPaTvaController.getClassModel());
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_T_CONDITIONNEMENT);
					}
					break;
				case C_MO_EDITION:
				case C_MO_INSERTION:
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
					// paramAfficheAideRecherche.setFocusDefaut(paAideRechercheController1.getVue().getTfChoix());

					// Ajout d'une recherche
					paAideController.addRecherche(paAideRechercheController1,
							paramAfficheAideRecherche.getTitreRecherche());

					// Parametrage de l'ecran d'aide principal
					ParamAfficheAideSWT paramAfficheAideSWT = new ParamAfficheAideSWT();
					ParamAfficheAide paramAfficheAide = new ParamAfficheAide();

					// enregistrement pour le retour de l'ecran d'aide
					paAideController.addRetourEcranListener(getThis());
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
		String validationContext = "T_CONDITIONNEMENT";
		try {	
			IStatus s = null;
			boolean verrouilleModifCode = false;
			int change=0;
			
				TaTConditionnement u = new TaTConditionnement();
				PropertyUtils.setSimpleProperty(u, nomChamp, value);
				if(((SWTTConditionnement) selectedTConditionnement.getValue()).getId()!=null) {
					u.setId(((SWTTConditionnement) selectedTConditionnement.getValue()).getId());
				}
				
				if(nomChamp.equals(Const.C_CODE_TVA)){
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
		EntityTransaction transaction = null;
		try {
			ctrlUnChampsSWT(getFocusCourantSWT());
			ctrlTousLesChampsAvantEnregistrementSWT();
			transaction = dao.getEntityManager().getTransaction();
			dao.begin(transaction);
			
			if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {

				LgrDozerMapper<SWTTConditionnement,TaTConditionnement> mapper = new LgrDozerMapper<SWTTConditionnement,TaTConditionnement>();
				mapper.map((SWTTConditionnement) selectedTConditionnement.getValue(),taTConditionnement);

				dao.enregistrerMerge(taTConditionnement);
				
			} else if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)) {
				LgrDozerMapper<SWTTConditionnement,TaTConditionnement> mapper = new LgrDozerMapper<SWTTConditionnement,TaTConditionnement>();
				mapper.map((SWTTConditionnement) selectedTConditionnement.getValue(),taTConditionnement);
				taTConditionnement=dao.enregistrerMerge(taTConditionnement);
//				modelTva.getListeEntity().add(taTva);
			}
			
			dao.commit(transaction);
			modelTConditionnement.addEntity(taTConditionnement);
			transaction = null;
			actRefresh(); //deja present
			
		} catch(Exception e) {
			throw e;
		}finally {
			if(transaction!=null && transaction.isActive()) {
				transaction.rollback();
			}
			initEtatBouton();
		}
	}


	public void initEtatComposant() {
		try {
				vue.getTfCodeType().setEditable(!isUtilise());
				changeCouleur(vue);
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
		}
	}

	public boolean isUtilise(){
		return (((SWTTConditionnement)selectedTConditionnement.getValue()).getId()!=null &&
		!dao.recordModifiable(dao.getNomTable(),
				((SWTTConditionnement)selectedTConditionnement.getValue()).getId()))||
				!dao.autoriseModification(taTConditionnement);		
	}
	
	public SWTTConditionnement getSwtOldTConditionnement() {
		return swtOldTConditionnement;
	}

	public void setSwtOldTConditionnement(SWTTConditionnement swtOldTva) {
		this.swtOldTConditionnement = swtOldTva;
	}

	public void setSwtOldTConditionnement() {
		if (selectedTConditionnement.getValue() != null)
			this.swtOldTConditionnement = SWTTConditionnement.copy((SWTTConditionnement) selectedTConditionnement.getValue());
		else {
			if(tableViewer.selectionGrille(0)){
				this.swtOldTConditionnement = SWTTConditionnement.copy((SWTTConditionnement) selectedTConditionnement.getValue());
				tableViewer.setSelection(new StructuredSelection(
						(SWTTConditionnement) selectedTConditionnement.getValue()), true);
			}}
	}

	public void setVue(PaTConditionnementSWT vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();

		mapComposantDecoratedField.put(vue.getTfCodeType(), vue.getFieldCodeType());
		mapComposantDecoratedField.put(vue.getTfHauteur(), vue.getFieldHauteur());
		mapComposantDecoratedField.put(vue.getTfLargeur(), vue.getFieldLargeur());
		mapComposantDecoratedField.put(vue.getTfLibelle(), vue.getFieldLibelle());
		mapComposantDecoratedField.put(vue.getTfLongueur(), vue.getFieldLongueur());
		mapComposantDecoratedField.put(vue.getTfPoids(), vue.getFieldPoids());
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
		if (taTConditionnement!=null) { //enregistrement en cours de modification/insertion
			idActuel = taTConditionnement.getId();
		} else if(selectedTConditionnement!=null && (SWTTConditionnement) selectedTConditionnement.getValue()!=null) {
			idActuel = ((SWTTConditionnement) selectedTConditionnement.getValue()).getId();
		}
		
		if (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION) == 0) {
			//rafraichissement des valeurs dans la grille
			writableList = new WritableList(realm, modelTConditionnement.remplirListe(), classModel);
			tableViewer.setInput(writableList);
		} else {
			if (taTConditionnement!=null && selectedTConditionnement!=null && (SWTTConditionnement) selectedTConditionnement.getValue()!=null) {
				new LgrDozerMapper<TaTConditionnement, SWTTConditionnement>().
					map(taTConditionnement, (SWTTConditionnement) selectedTConditionnement.getValue());
			}
		}

		if(idActuel!=0) {
			tableViewer.setSelection(new StructuredSelection(modelTConditionnement.recherche(Const.C_ID_T_CONDITIONNEMENT, idActuel)));
		}else
			tableViewer.selectionGrille(0);
		
	}

	public	ModelGeneralObjet<SWTTConditionnement,TaTConditionnementDAO,TaTConditionnement> getModelTConditionnement() {
		return modelTConditionnement;
	}

	public TaTConditionnementDAO getDao() {
		return dao;
	}

	public TaTConditionnement getTaTConditionnement() {
		return taTConditionnement;
	}

	public void actAfficherTous() throws Exception{
		vue.getGrille().setVisible(true);  
		vue.getBtnTous().setVisible(false);
		vue.getLaTitreGrille().setVisible(true);
		vue.getCompositeForm().setWeights(new int[]{50,100});
		dao.setJPQLQuery(dao.getJPQLQueryInitial());
		modelTConditionnement.setJPQLQuery(null);
		modelTConditionnement.setListeEntity(null);
		try {
			actRefresh();
		} catch (Exception e1) {
			logger.error("", e1);
		}
	}
}

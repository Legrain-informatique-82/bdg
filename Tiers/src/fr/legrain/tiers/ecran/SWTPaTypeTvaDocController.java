package fr.legrain.tiers.ecran;

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
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
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
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Articles.SWTArticle;
import fr.legrain.gestCom.Module_Tiers.SWTTypeTvaDoc;
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
import fr.legrain.tiers.dao.TaTTvaDoc;
import fr.legrain.tiers.dao.TaTTvaDocDAO;
import fr.legrain.tiers.editor.EditorInputTypeTiers;
import fr.legrain.tiers.editor.EditorTypeTvaDoc;

public class SWTPaTypeTvaDocController extends JPABaseControllerSWTStandard
		implements RetourEcranListener {

	static Logger logger = Logger.getLogger(SWTPaTypeTvaDocController.class.getName());
	private PaTypeTtvaDocSWT vue = null;
	private TaTTvaDocDAO dao = null;
	
	private Object ecranAppelant = null;
	private SWTTypeTvaDoc swtTypeTvaDoc;
	private SWTTypeTvaDoc swtOldTypeTvaDoc;
	private Realm realm;
	private DataBindingContext dbc;

	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private IObservableValue selectedTypeTvaDoc;
	private String[] listeChamp;
	private String nomClassController = this.getClass().getSimpleName();
	private MapChangeListener changeListener = new MapChangeListener();
	private Class classModel = SWTTypeTvaDoc.class;
	private ModelGeneralObjet<SWTTypeTvaDoc,TaTTvaDocDAO,TaTTvaDoc> modelTypeTvaDoc = null;//new ModelGeneralObjet<SWTTypeTiers,TaTTiersDAO,TaTTiers>(dao,classModel);
	
	private LgrDozerMapper<SWTTypeTvaDoc,TaTTvaDoc> mapperUIToModel  = new LgrDozerMapper<SWTTypeTvaDoc,TaTTvaDoc>();
	private TaTTvaDoc taTTvaDoc = null;
	
	public SWTPaTypeTvaDocController(PaTypeTtvaDocSWT vue) {
		this(vue,null);
	}

	public SWTPaTypeTvaDocController(PaTypeTtvaDocSWT vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaTTvaDocDAO(getEm());
		modelTypeTvaDoc = new ModelGeneralObjet<SWTTypeTvaDoc,TaTTvaDocDAO,TaTTvaDoc>(dao,classModel);
		setVue(vue);

		// a faire avant l'initialisation du Binding
		vue.getGrille().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setSwtOldTypeTiers();
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
			//setIbTaTableStandard(ibTaTable);
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
			logger.error("Erreur : PaTiersController", e);
		}
	}

	public void bind(PaTypeTtvaDocSWT paTypeTvaDocSWT) {
		try {
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			tableViewer = new LgrTableViewer(paTypeTvaDocSWT.getGrille());
			tableViewer.createTableCol(classModel,paTypeTvaDocSWT.getGrille(), nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE,0);
			listeChamp = tableViewer.setListeChampGrille(nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE);

			// Set up data binding.
			LgrViewerSupport.bind(tableViewer, 
					new WritableList(modelTypeTvaDoc.remplirListe(), classModel),
					BeanProperties.values(listeChamp)
					);
			selectedTypeTvaDoc = ViewersObservables
					.observeSingleSelection(tableViewer);

			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			tableViewer.selectionGrille(0);

			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);

			bindingFormMaitreDetail(dbc, realm, selectedTypeTvaDoc,classModel);
			changementDeSelection();
			selectedTypeTvaDoc.addChangeListener(new IChangeListener() {

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
		if(selectedTypeTvaDoc!=null && selectedTypeTvaDoc.getValue()!=null) {
			if(((SWTTypeTvaDoc) selectedTypeTvaDoc.getValue()).getIdTTvaDoc()!=null) {
				taTTvaDoc = dao.findById(((SWTTypeTvaDoc) selectedTypeTvaDoc.getValue()).getIdTTvaDoc());
			}
			//null a tester ailleurs, car peut etre null en cas d'insertion
			fireChangementDeSelection(new ChangementDeSelectionEvent(SWTPaTypeTvaDocController.this));
		}
	}

	public Composite getVue() {
		return vue;
	}

	public ResultAffiche configPanel(ParamAffiche param) {
		if (param != null) {
			//ibTaTable.ouvreDataset();
			if (((ParamAfficheTypeTvaDoc) param).getFocusDefautSWT() != null && !((ParamAfficheTypeTvaDoc) param).getFocusDefautSWT().isDisposed())
				((ParamAfficheTypeTvaDoc) param).getFocusDefautSWT().setFocus();
			else
				((ParamAfficheTypeTvaDoc) param).setFocusDefautSWT(vue
						.getGrille());
			vue.getLaTitreFormulaire().setText(
					((ParamAfficheTypeTvaDoc) param).getTitreFormulaire());
			vue.getLaTitreGrille().setText(
					((ParamAfficheTypeTvaDoc) param).getTitreGrille());
			vue.getLaTitreFenetre().setText(
					((ParamAfficheTypeTvaDoc) param).getSousTitre());

			if (param.getEcranAppelant() != null) {
				ecranAppelant = param.getEcranAppelant();
			}
			Map<String,String[]> map = dao.getParamWhereSQL();
			if(param.getIdFiche()!=null &&  !param.getIdFiche().equals("")) {
				if(map==null) map = new HashMap<String,String[]>();
				map.clear();
				map.put("idTTvaDoc",new String[]{"=",param.getIdFiche()});
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
			tableViewer.tri(classModel, nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			VerrouInterface.setVerrouille(false);
			setSwtOldTypeTiers();

			if (param.getModeEcran() != null
					&& param.getModeEcran().compareTo(
							EnumModeObjet.C_MO_INSERTION) == 0) {
				try {
					actInserer();
				} catch (Exception e) {
					vue.getLaMessage().setText(e.getMessage());
					logger.error("", e);
				}
			}

			//param.setFocus(ibTaTable.getFModeObjet().getFocusCourant());
		}
		return null;
	}
	
	protected void initVerifySaisie() {
		if (mapInfosVerifSaisie == null)
			mapInfosVerifSaisie = new HashMap<Control, InfosVerifSaisie>();
		
		mapInfosVerifSaisie.put(vue.getTfCODE_T_TVA_DOC(), new InfosVerifSaisie(new TaTTvaDoc(),Const.C_CODE_T_TVA_DOC,null));
		mapInfosVerifSaisie.put(vue.getTfCOMPTE_T_TVA_DOC(), new InfosVerifSaisie(new TaTTvaDoc(),Const.C_COMPTE_T_TVA_DOC,null));
		mapInfosVerifSaisie.put(vue.getTfLIBELLE_T_TVA_DOC(), new InfosVerifSaisie(new TaTTvaDoc(),Const.C_LIBELLE_T_TVA_DOC,null));

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

		vue.getTfCODE_T_TVA_DOC().setToolTipText(Const.C_CODE_T_TVA_DOC);
		vue.getTfCOMPTE_T_TVA_DOC().setToolTipText(Const.C_COMPTE_T_TVA_DOC);
		vue.getTfLIBELLE_T_TVA_DOC().setToolTipText(Const.C_LIBELLE_T_TVA_DOC);

		mapComposantChamps.put(vue.getTfCODE_T_TVA_DOC(), Const.C_CODE_T_TVA_DOC);
		mapComposantChamps.put(vue.getTfCOMPTE_T_TVA_DOC(), Const.C_COMPTE_T_TVA_DOC);
		mapComposantChamps.put(vue.getTfLIBELLE_T_TVA_DOC(),
				Const.C_LIBELLE_T_TVA_DOC);

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
				.getTfCODE_T_TVA_DOC());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue
				.getTfCODE_T_TVA_DOC());
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

	public SWTPaTypeTvaDocController getThis() {
		return this;
	}

	@Override
	public boolean onClose() throws ExceptLgr {
		boolean retour = true;
		VerrouInterface.setVerrouille(true);
		//switch (ibTaTable.getFModeObjet().getMode()) {
		switch (dao.getModeObjet().getMode()) {
		case C_MO_INSERTION:
		case C_MO_EDITION:
			if (MessageDialog.openQuestion(vue.getShell(), MessagesEcran
					.getString("Message.Attention"), MessagesEcran
					.getString("TypeTiers.Message.Enregistrer"))) {

				try {
					actEnregistrer();
				} catch (Exception e) {
					vue.getLaMessage().setText(e.getMessage());
					logger.error("", e);
				}
			} else
				//ibTaTable.annuler();

			break;
		case C_MO_CONSULTATION:
			break;
		default:
			break;
		}

		if (retour) {
			if (ecranAppelant instanceof SWTPaAideControllerSWT) {
				setListeEntity(getModelTypeTvaDoc().remplirListe());
				dao.initValeurIdTable(taTTvaDoc);
				fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
						//ibTaTable.champIdTable, ibTaTable.valeurIdTable,
						dao.getChampIdEntite(), dao.getValeurIdTable(),
						selectedTypeTvaDoc.getValue())));
//				vue.getDisplay().asyncExec(new Runnable() {
//					public void run() {
//						vue.getShell().setVisible(false);
//					}
//				});
//				retour = false;
				retour = true;
			}
		}
//		if (retour && !(ecranAppelant instanceof SWTPaAideControllerSWT)) {
//			fireDestroy(new DestroyEvent(ibTaTable));
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
					ctrlUnChampsSWT(getFocusAvantAideSWT());
				} catch (Exception e) {
					logger.error("",e);
					vue.getLaMessage().setText(e.getMessage());
				}
			}
			if (getFocusAvantAideSWT() instanceof Table) {
				if (getFocusAvantAideSWT() == vue.getGrille()) {
					//ibTaTable.lgrLocateID(((ResultAffiche) evt.getRetour()).getIdResult());
					if(((ResultAffiche) evt.getRetour()).getSelection()!=null)
						tableViewer.setSelection(((ResultAffiche) evt.getRetour()).getSelection(),true);
				}
			}
		} else if (evt.getRetour() != null) {
			if (getFocusAvantAideSWT() instanceof Table) {
				if (getFocusAvantAideSWT() == vue.getGrille()) {
					//ibTaTable.lgrLocateID(((ResultAffiche) evt.getRetour()).getIdResult());
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
				setSwtOldTypeTiers();
				swtTypeTvaDoc = new SWTTypeTvaDoc();
				taTTvaDoc = new TaTTvaDoc();
				
				//ibTaTable.insertion();
				dao.inserer(taTTvaDoc);
				modelTypeTvaDoc.getListeObjet().add(swtTypeTvaDoc);
				writableList = new WritableList(realm, modelTypeTvaDoc
						.getListeObjet(),classModel);
				tableViewer.setInput(writableList);
				tableViewer.refresh();
				tableViewer.setSelection(new StructuredSelection(swtTypeTvaDoc));
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
				setSwtOldTypeTiers();
				taTTvaDoc = dao.findById(((SWTTypeTvaDoc) selectedTypeTvaDoc.getValue()).getIdTTvaDoc());
			}else{
				if(!setSwtOldTypeTiersRefresh())throw new Exception();
			}						
			dao.modifier(taTTvaDoc);
			initEtatBouton();
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		}
	}
	public boolean containsEntity(TaTTvaDoc entite){
		if(modelTypeTvaDoc.getListeEntity()!=null){
			for (Object e : modelTypeTvaDoc.getListeEntity()) {
				if(((TaTTvaDoc)e).getIdTTvaDoc()==
					entite.getIdTTvaDoc())return true;
			}
		}
		return false;
	}

	public boolean setSwtOldTypeTiersRefresh() {
		try {	
			if (selectedTypeTvaDoc.getValue()!=null){
				TaTTvaDoc taTTvaDocOld =dao.findById(taTTvaDoc.getIdTTvaDoc());
				taTTvaDocOld=dao.refresh(taTTvaDocOld);
				if(containsEntity(taTTvaDoc)) 
					modelTypeTvaDoc.getListeEntity().remove(taTTvaDoc);
				if(!taTTvaDoc.getVersionObj().equals(taTTvaDocOld.getVersionObj())){
					taTTvaDoc=taTTvaDocOld;
					if(!containsEntity(taTTvaDoc)) 
						modelTypeTvaDoc.getListeEntity().add(taTTvaDoc);					
					actRefresh();
					dao.messageNonAutoriseModification();
				}
				taTTvaDoc=taTTvaDocOld;
				if(!containsEntity(taTTvaDoc)) 
					modelTypeTvaDoc.getListeEntity().add(taTTvaDoc);
				changementDeSelection();
				this.swtOldTypeTvaDoc=SWTTypeTvaDoc.copy((SWTTypeTvaDoc)selectedTypeTvaDoc.getValue());
			}
			return true;
		} catch (Exception e) {
			return false;
		}		
	}

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
					.getString("TypeTiers.Message.Supprimer"))) {

			dao.begin(transaction);
			TaTTvaDoc u = dao.findById(((SWTTypeTvaDoc) selectedTypeTvaDoc.getValue()).getIdTTvaDoc());
				dao.supprimer(u);
				
			dao.commit(transaction);
			Object suivant=tableViewer.getElementAt(tableViewer.getTable().getSelectionIndex()+1);
			modelTypeTvaDoc.removeEntity(taTTvaDoc);
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
			// //InputVerifier inputVerifier =
			// getFocusCourant().getInputVerifier();
			// //getFocusCourant().setInputVerifier(null);
			//switch (ibTaTable.getFModeObjet().getMode()) {
			switch (dao.getModeObjet().getMode()) {
			case C_MO_INSERTION:
				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("TypeTiers.Message.Annuler")))) {
					remetTousLesChampsApresAnnulationSWT(dbc);
					if(((SWTTypeTvaDoc) selectedTypeTvaDoc.getValue()).getIdTTvaDoc()==null){
					modelTypeTvaDoc.getListeObjet().remove(
							((SWTTypeTvaDoc) selectedTypeTvaDoc.getValue()));
					writableList = new WritableList(realm, modelTypeTvaDoc
							.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.selectionGrille(0);
					}
					//ibTaTable.annuler();
					dao.annuler(taTTvaDoc);
					hideDecoratedFields();
				}
				initEtatBouton();
				break;
			case C_MO_EDITION:
				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("TypeTiers.Message.Annuler")))) {
					//int rang = getGrille().getSelectionIndex();
					int rang = modelTypeTvaDoc.getListeObjet().indexOf(selectedTypeTvaDoc.getValue());
					// selectedTypeTiers.setValue(swtOldTypeTiers);
					remetTousLesChampsApresAnnulationSWT(dbc);
					modelTypeTvaDoc.getListeObjet().set(rang, swtOldTypeTvaDoc);
					writableList = new WritableList(realm, modelTypeTvaDoc
							.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.setSelection(new StructuredSelection(
							swtOldTypeTvaDoc), true);
					//ibTaTable.annuler();
					dao.annuler(taTTvaDoc);
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
			// getFocusCourant().setInputVerifier(inputVerifier);
			// initEtatBouton();
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
//		fonctionGetInfosXmlAndProperties.setValueMapAttributeTable(TaTTiers.class.getSimpleName()+".head");
//		fonctionGetInfosXmlAndProperties.setValueMapAttributeTable(TaTTiers.class.getSimpleName()+".detail");
//		
//		LinkedHashMap<String,AttributElementResport> mapAttributeTablHead = fonctionGetInfosXmlAndProperties.getMapAttributeTablHead(); 
//		LinkedHashMap<String,AttributElementResport> mapAttributeTablDetail = fonctionGetInfosXmlAndProperties.getMapAttributeTablDetail();
//		
//		Collection<TaTTiers> collectionTaTTiers = modelTypeTvaDoc.getListeEntity();
//		
//		fonctionGetInfosXmlAndProperties.findInfosFileXml(TaTTiers.class.getName(),SWTTypeTiers.class.getName(),
//				listeChamp,"mapping");
//		fonctionGetInfosXmlAndProperties.getInfosObjetJPA(taTTvaDoc);
//
//		ConstEdition constEdition = new ConstEdition(); 
////		Impression impression = new Impression(constEdition,taTTiers,collectionTaTTiers,nomChampIdTable,taTTiers.getIdTTiers());
//		String nomDossier = null;
//
//		int nombreLine = collectionTaTTiers.size();
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
//			String folderEditionDynamique = Const.C_RCP_INSTANCE_LOCATION+"/"+Const.C_NOM_PROJET_TMP+"/"+TaTTiers.class.getSimpleName();
//			constEdition.makeFolderEditions(folderEditionDynamique);
//			Path pathFileReport = new Path(folderEditionDynamique+"/"+Const.C_NOM_VU_T_TIERS+".rptdesign");
//			final String pathFileReportDynamic = pathFileReport.toPortableString();
//
//			MakeDynamiqueReport DynamiqueReport = new MakeDynamiqueReport(constEdition.getNameTableEcran(),
//					constEdition.getNameTableBDD(),pathFileReportDynamic,Const.C_NOM_VU_T_TIERS,
//					ConstEdition.PAGE_ORIENTATION_LANDSCAPE,nomDossier); 
//			DynamiqueReport.setSimpleNameEntity(TaTTiers.class.getSimpleName());
//			/**************************************************************/
//			DynamiqueReport.setFonctionGetInfosXml(fonctionGetInfosXmlAndProperties);
//			DynamiqueReport.setNomObjet(TaTTel.class.getSimpleName());
//			/**************************************************************/
//
//			Map<String, AttributElementResport> attribuGridHeader = new LinkedHashMap<String, AttributElementResport>();
//			String nameHeaderTitle = ConstEditionTiers.TITLE_EDITION_TYPE_TELEPHONES;
//			attribuGridHeader.put(nameHeaderTitle, new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
//					ConstEdition.FONT_SIZE_XX_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
//					ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE,""));
//			String nameSousHeaderTitle = ConstEditionTiers.SOUS_TITLE_EDITION_TYPE_TELEPHONES;
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
//					Const.C_NOM_VU_T_TIERS,1,1,2,"40", mapAttributeTablHead, mapAttributeTablDetail);
//			DynamiqueReport.savsAsDesignHandle();
////			impression.imprimer(true,pathFileReportDynamic,null,"Type Tiers",TaTTiers.class.getSimpleName(),false);
//
//			/** 01/03/2010 modifier les editions (zhaolin) **/
//			BaseImpressionEdition impressionEdition = new BaseImpressionEdition(constEdition,taTTvaDoc,
//					getEm(),collectionTaTTiers,taTTvaDoc.getIdTTiers());
//			impressionEdition.impressionEditionTypeEntity(pathFileReportDynamic,"Tiers");
//		}
	}
	
	@Override
	protected boolean aideDisponible() {
		boolean result = false;
		//switch ((getThis().ibTaTable.getFModeObjet().getMode())) {
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
				//paramAfficheAideRecherche.setDb(getThis().getIbTaTable().getFIBBase());
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
				//SWTBaseControllerSWTStandard controllerEcranCreation = null;
				JPABaseControllerSWTStandard controllerEcranCreation = null;
				ParamAffiche parametreEcranCreation = null;
				IEditorPart editorCreation = null;
				String editorCreationId = null;
				IEditorInput editorInputCreation = null;
				Shell s2 = new Shell(s, LgrShellUtil.styleLgr);

				//switch ((getThis().ibTaTable.getFModeObjet().getMode())) {
				switch ((getThis().dao.getModeObjet().getMode())) {
				case C_MO_CONSULTATION:
					if(getFocusCourantSWT().equals(vue.getGrille())){
						PaTypeTtvaDocSWT paTTiersSWT = new PaTypeTtvaDocSWT(s2,SWT.NULL);
						SWTPaTypeTvaDocController swtPaTTiersController = new SWTPaTypeTvaDocController(paTTiersSWT);

						editorCreationId = EditorTypeTvaDoc.ID;
						editorInputCreation = new EditorInputTypeTiers();

						ParamAfficheTypeTiers paramAfficheTTiers = new ParamAfficheTypeTiers();
						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
						paramAfficheTTiers.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheTTiers.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaTTiersController;
						parametreEcranCreation = paramAfficheTTiers;

						paramAfficheAideRecherche.setTypeEntite(TaTTvaDoc.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_T_TVA_DOC);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_T_TVA_DOC().getText());
						paramAfficheAideRecherche.setControllerAppelant(getThis());
						paramAfficheAideRecherche.setModel(swtPaTTiersController.getModelTypeTvaDoc());
						paramAfficheAideRecherche.setTypeObjet(swtPaTTiersController.getClassModel());
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_T_TVA_DOC);
					}
					break;
				case C_MO_EDITION:
				case C_MO_INSERTION:
					break;
				default:
					break;
				}

				//if (paramAfficheAideRecherche.getQuery() != null) {
				if (paramAfficheAideRecherche.getJPQLQuery() != null) {

					PaAideRechercheSWT paAideRecherche1 = new PaAideRechercheSWT(s,
							SWT.NULL);
					SWTPaAideRechercheControllerSWT paAideRechercheController1 = new SWTPaAideRechercheControllerSWT(
							paAideRecherche1);

					// Parametrage de la recherche
					paramAfficheAideRecherche
					.setFocusSWT(((PaAideRechercheSWT) paAideRechercheController1
							.getVue()).getTfChoix());
					paramAfficheAideRecherche
					.setRefCreationSWT(controllerEcranCreation);
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
					//Control focus = vue.getShell().getDisplay().getFocusControl();
					// affichage de l'ecran d'aide principal (+ ses recherches)

					dbc.getValidationStatusMap().removeMapChangeListener(changeListener);
					//LgrShellUtil.afficheAideSWT(paramAfficheAide, null, paAide,paAideController, s);
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
		String validationContext = "T_TVA_DOC";
		try {
			IStatus s = null;
			boolean verrouilleModifCode = false;
			int change=0;

				TaTTvaDoc u = new TaTTvaDoc();
				PropertyUtils.setSimpleProperty(u, nomChamp, value);
				if(((SWTTypeTvaDoc) selectedTypeTvaDoc.getValue()).getIdTTvaDoc()!=null) {
					u.setIdTTvaDoc(((SWTTypeTvaDoc) selectedTypeTvaDoc.getValue()).getIdTTvaDoc());
				}
				
				if(nomChamp.equals(Const.C_CODE_T_TVA_DOC)){
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
		
//		return s;
	}
	@Override
	protected void actEnregistrer() throws Exception {
		EntityTransaction transaction = dao.getEntityManager().getTransaction();
		try {
			dao.begin(transaction);
			ctrlUnChampsSWT(getFocusCourantSWT());
			ctrlTousLesChampsAvantEnregistrementSWT();
			
			if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
				//u = dao.findById(((SWTUnite) selectedUnite.getValue()).getIdUnite());

				LgrDozerMapper<SWTTypeTvaDoc,TaTTvaDoc> mapper = new LgrDozerMapper<SWTTypeTvaDoc,TaTTvaDoc>();
				mapper.map((SWTTypeTvaDoc) selectedTypeTvaDoc.getValue(),taTTvaDoc);
				
				taTTvaDoc=dao.enregistrerMerge(taTTvaDoc);
				
				
			} else if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)) {
				LgrDozerMapper<SWTTypeTvaDoc,TaTTvaDoc> mapper = new LgrDozerMapper<SWTTypeTvaDoc,TaTTvaDoc>();
				mapper.map((SWTTypeTvaDoc) selectedTypeTvaDoc.getValue(),taTTvaDoc);

				taTTvaDoc=dao.enregistrerMerge(taTTvaDoc);
			}
			dao.commit(transaction);
			modelTypeTvaDoc.addEntity(taTTvaDoc);
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
			vue.getTfCODE_T_TVA_DOC().setEditable(!isUtilise());
			changeCouleur(vue);
	  } catch (Exception e) {
		vue.getLaMessage().setText(e.getMessage());
	  }
	}
	public boolean isUtilise(){
		return (((SWTTypeTvaDoc)selectedTypeTvaDoc.getValue()).getIdTTvaDoc()!=null &&
		!dao.recordModifiable(dao.getNomTable(),
				((SWTTypeTvaDoc)selectedTypeTvaDoc.getValue()).getIdTTvaDoc()))||
				!dao.autoriseModification(taTTvaDoc);		
	}
	public SWTTypeTvaDoc getSwtOldTypeTvaDoc() {
		return swtOldTypeTvaDoc;
	}

	public void setSwtOldTypeTiers(SWTTypeTvaDoc swtOldTypeTiers) {
		this.swtOldTypeTvaDoc = swtOldTypeTiers;
	}

	public void setSwtOldTypeTiers() {
		if (selectedTypeTvaDoc.getValue() != null)
			this.swtOldTypeTvaDoc = SWTTypeTvaDoc.copy((SWTTypeTvaDoc) selectedTypeTvaDoc.getValue());
		else {
			if (tableViewer.selectionGrille(0)){
			this.swtOldTypeTvaDoc = SWTTypeTvaDoc.copy((SWTTypeTvaDoc) selectedTypeTvaDoc.getValue());
			tableViewer.setSelection(new StructuredSelection(
					(SWTTypeTvaDoc) selectedTypeTvaDoc.getValue()), true);
		}}
	}

	public void setVue(PaTypeTtvaDocSWT vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();
		mapComposantDecoratedField.put(vue.getTfCODE_T_TVA_DOC(), vue
				.getFieldCODE_T_TVA_DOC());
		mapComposantDecoratedField.put(vue.getTfCOMPTE_T_TVA_DOC(), vue
				.getFieldCOMPTE_T_TVA_DOC());
		mapComposantDecoratedField.put(vue.getTfLIBELLE_T_TVA_DOC(), vue
				.getFieldLIBELLE_T_TVA_DOC());
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
		writableList = new WritableList(realm, modelTypeTvaDoc.remplirListe(), classModel);
		tableViewer.setInput(writableList);
		tableViewer.refresh();
		tableViewer.selectionGrille(
				tableViewer.selectionGrille(selectedTypeTvaDoc.getValue()));
	}

	//public ModelTypeTiers getModelTypeTiers() {
	public ModelGeneralObjet<SWTTypeTvaDoc,TaTTvaDocDAO,TaTTvaDoc> getModelTypeTvaDoc() {
		return modelTypeTvaDoc;
	}

	public TaTTvaDocDAO getDao() {
		return dao;
	}

	public void actAfficherTous() throws Exception{
		vue.getGrille().setVisible(true);  
		vue.getBtnTous().setVisible(false);
		vue.getLaTitreGrille().setVisible(true);
		vue.getCompositeForm().setWeights(new int[]{50,100});
		dao.setJPQLQuery(dao.getJPQLQueryInitial());
		modelTypeTvaDoc.setJPQLQuery(null);
		modelTypeTvaDoc.setListeEntity(null);
		try {
			actRefresh();
		} catch (Exception e1) {
			logger.error("", e1);
		}
	}
}

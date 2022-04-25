package fr.legrain.tiers.ecran;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
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

import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Tiers.SWTEmail;
import fr.legrain.gestCom.Module_Tiers.SWTEntreprise;
import fr.legrain.gestCom.Module_Tiers.SWTTiers;
import fr.legrain.gestCom.Module_Tiers.SWTWeb;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.DeclencheCommandeControllerEvent;
import fr.legrain.gestCom.librairiesEcran.swt.IDetailController;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideRechercheSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideRechercheControllerSWT;
import fr.legrain.gestCom.librairiesEcran.workbench.AnnuleToutEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDePageEvent;
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
import fr.legrain.tiers.dao.TaEmail;
import fr.legrain.tiers.dao.TaEntreprise;
import fr.legrain.tiers.dao.TaEntrepriseDAO;
import fr.legrain.tiers.dao.TaTAdr;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.editor.EditorEntreprise;
import fr.legrain.tiers.editor.EditorInputEntreprise;

public class SWTPaEntrepriseController extends JPABaseControllerSWTStandard
implements RetourEcranListener ,IDetailController {

	static Logger logger = Logger.getLogger(SWTPaEntrepriseController.class.getName());
	private PaEntrepriseSWT vue = null;
	private TaEntrepriseDAO dao = null;//new TaEntrepriseDAO();
	private Object ecranAppelant = null;
	private SWTEntreprise swtEntreprise;
	private SWTEntreprise swtOldEntreprise;
	private Realm realm;
	private DataBindingContext dbc;
	private TaTiers masterEntity = null;
	private TaTiersDAO masterDAO = null;

	private Class classModel = SWTEntreprise.class;
	private ModelGeneralObjet<SWTEntreprise,TaEntrepriseDAO,TaEntreprise> modelEntreprise = null;//new ModelGeneralObjet<SWTEntreprise,TaEntrepriseDAO,TaEntreprise>(dao,classModel);

	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private IObservableValue selectedEntreprise;
	private String[] listeChamp;
	private String nomClassController = this.getClass().getSimpleName();
	private MapChangeListener changeListener = new MapChangeListener();

	private LgrDozerMapper<SWTEntreprise,TaEntreprise> mapperUIToModel  = 
		new LgrDozerMapper<SWTEntreprise,TaEntreprise>();
	private TaEntreprise taEntreprise = new TaEntreprise();

	public SWTPaEntrepriseController(PaEntrepriseSWT vue) {
		this(vue,null);
	}

	public SWTPaEntrepriseController(PaEntrepriseSWT vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaEntrepriseDAO(getEm());
		modelEntreprise = new ModelGeneralObjet<SWTEntreprise,TaEntrepriseDAO,TaEntreprise>(dao,classModel);
		setVue(vue);

		// a faire avant l'initialisation du Binding
		vue.getGrille().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setSwtOldEntreprise();
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
			logger.error("Erreur : PaTiersController", e);
		}
	}

	public void bind(PaEntrepriseSWT paEntrepriseSWT) {
		try {
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			tableViewer = new LgrTableViewer(paEntrepriseSWT.getGrille());
			tableViewer.createTableCol(classModel,paEntrepriseSWT.getGrille(), nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE,0);
			listeChamp = tableViewer.setListeChampGrille(nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE);

			//			ObservableListContentProvider viewerContent = new ObservableListContentProvider();
			//			tableViewer.setContentProvider(viewerContent);
			//
			//			IObservableMap[] attributeMaps = BeansObservables.observeMaps(
			//					viewerContent.getKnownElements(), classModel,
			//					listeChamp);
			//
			//			tableViewer.setLabelProvider(new ObservableMapLabelProvider(
			//					attributeMaps));
			//			writableList = new WritableList(realm, IHMmodel(), classModel);
			//			tableViewer.setInput(writableList);

			// Set up data binding.
			LgrViewerSupport.bind(tableViewer, 
					new WritableList(IHMmodel(), classModel),
					BeanProperties.values(listeChamp)
			);

			selectedEntreprise = ViewersObservables
			.observeSingleSelection(tableViewer);

			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			tableViewer.selectionGrille(0);

			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);

			bindingFormMaitreDetail(dbc, realm, selectedEntreprise,classModel);
			changementDeSelection();
			selectedEntreprise.addChangeListener(new IChangeListener() {

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
		if(selectedEntreprise!=null && selectedEntreprise.getValue()!=null) {
			if(((SWTEntreprise) selectedEntreprise.getValue()).getIdEntreprise()!=null) {
				taEntreprise = dao.findById(((SWTEntreprise) selectedEntreprise.getValue()).getIdEntreprise());
			}
			//null a tester ailleurs, car peut etre null en cas d'insertion
			fireChangementDeSelection(new ChangementDeSelectionEvent(SWTPaEntrepriseController.this));
		}
	}

	public Composite getVue() {
		return vue;
	}

	public ResultAffiche configPanel(ParamAffiche param) {
		if (param != null) {
			//			ibTaTable.ouvreDataset();
			if (((ParamAfficheEntreprise) param).getFocusDefautSWT() != null && !((ParamAfficheEntreprise) param).getFocusDefautSWT().isDisposed())
				((ParamAfficheEntreprise) param).getFocusDefautSWT().setFocus();
			else
				((ParamAfficheEntreprise) param).setFocusDefautSWT(vue
						.getGrille());
			vue.getLaTitreFormulaire().setText(
					((ParamAfficheEntreprise) param).getTitreFormulaire());
			vue.getLaTitreGrille().setText(
					((ParamAfficheEntreprise) param).getTitreGrille());
			vue.getLaTitreFenetre().setText(
					((ParamAfficheEntreprise) param).getSousTitre());

			if (param.getEcranAppelant() != null) {
				ecranAppelant = param.getEcranAppelant();
			}

			if(tableViewer==null) {
				//databinding pas encore realise
				bind(vue);
				tableViewer.tri(classModel, nomClassController,
						Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			} else {
				try {
					taEntreprise=null;
					selectedEntreprise.setValue(null);
					actRefresh();
				} catch (Exception e) {
					logger.error("",e);
				}
			}
			// permet de se positionner sur le 1er enregistrement et de remplir
			// le formulaire
			//			tableViewer.tri(classModel, nomClassController,
			//					Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			VerrouInterface.setVerrouille(false);
			setSwtOldEntreprise();

			if (param.getModeEcran() != null
					&& param.getModeEcran().compareTo(
							EnumModeObjet.C_MO_INSERTION) == 0) {
				try {
					if(IHMmodel().size()==0)
						actInserer();
				} catch (Exception e) {
					vue.getLaMessage().setText(e.getMessage());
					logger.error("", e);
				}
			}
			initEtatBouton();
			//param.setFocus(ibTaTable.getFModeObjet().getFocusCourant());
		}
		return null;
	}
	
	protected void initVerifySaisie() {
		if (mapInfosVerifSaisie == null)
			mapInfosVerifSaisie = new HashMap<Control, InfosVerifSaisie>();
		
		mapInfosVerifSaisie.put(vue.getTfCODE_TIERS(), new InfosVerifSaisie(new TaTiers(),Const.C_CODE_TIERS,null));
		mapInfosVerifSaisie.put(vue.getTfNOM_ENTREPRISE(), new InfosVerifSaisie(new TaEntreprise(),Const.C_NOM_ENTREPRISE,null));

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

		vue.getTfNOM_ENTREPRISE().setToolTipText(Const.C_NOM_ENTREPRISE);
		vue.getTfCODE_TIERS().setToolTipText(Const.C_CONTACT);		


		mapComposantChamps.put(vue.getTfNOM_ENTREPRISE(), Const.C_NOM_ENTREPRISE);
		mapComposantChamps.put(vue.getTfCODE_TIERS(),Const.C_CONTACT);

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

		if (mapInitFocus == null)
			mapInitFocus = new LinkedHashMap<ModeObjet.EnumModeObjet, Control>();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION, vue
				.getTfNOM_ENTREPRISE());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue
				.getTfNOM_ENTREPRISE());
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

		mapCommand.put(C_COMMAND_GLOBAL_PRECEDENT_ID, handlerPrecedent);
		mapCommand.put(C_COMMAND_GLOBAL_SUIVANT_ID, handlerSuivant);

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

		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID, C_COMMAND_GLOBAL_REFRESH_ID };
		mapActions.put(null, tabActionsAutres);
	}

	public SWTPaEntrepriseController getThis() {
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
					.getString("Entreprise.Message.Enregistrer"))) {

				try {
					actEnregistrer();
				} catch (Exception e) {
					vue.getLaMessage().setText(e.getMessage());
					logger.error("", e);
				}
			} else {
				fireAnnuleTout(new AnnuleToutEvent(this,true));
			}
			//ibTaTable.annuler();

			break;
		case C_MO_CONSULTATION:
			break;
		default:
			break;
		}

		if (retour) {
			if (ecranAppelant instanceof SWTPaAideControllerSWT) {
				setListeEntity(getModelEntreprise().remplirListe());
				dao.initValeurIdTable(taEntreprise);
				fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
						dao.getChampIdEntite(), dao.getValeurIdTable(),
						selectedEntreprise.getValue())));
				//				vue.getDisplay().asyncExec(new Runnable() {
				//				public void run() {
				//				vue.getShell().setVisible(false);
				//				}
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
					//					ibTaTable.lgrLocateID(((ResultAffiche) evt.getRetour()).getIdResult());
					if(((ResultAffiche) evt.getRetour()).getSelection()!=null)
						tableViewer.setSelection(((ResultAffiche) evt.getRetour()).getSelection(),true);
				}
			}
		} else if (evt.getRetour() != null) {
			if (getFocusAvantAideSWT() instanceof Table) {
				if (getFocusAvantAideSWT() == vue.getGrille()) {
					//					ibTaTable.lgrLocateID(((ResultAffiche) evt.getRetour()).getIdResult());
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
				boolean continuer=true;
				VerrouInterface.setVerrouille(true);
				if(masterEntity!=null)masterDAO.verifAutoriseModification(masterEntity);
				setSwtOldEntreprise();
				if(LgrMess.isDOSSIER_EN_RESEAU()){
					DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
					fireDeclencheCommandeController(e);
					continuer=masterDAO.dataSetEnModif();
				}
				if(continuer){
					swtEntreprise = new SWTEntreprise();
					taEntreprise = new TaEntreprise();
					dao.inserer(taEntreprise);
					modelEntreprise.getListeObjet().add(swtEntreprise);
					writableList = new WritableList(realm, modelEntreprise.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.setSelection(new StructuredSelection(swtEntreprise));
					masterEntity.addEntreprise(taEntreprise);

					initEtatBouton();
					try {
						DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
						fireDeclencheCommandeController(e);
					} catch (Exception e) {
						logger.error("",e);
					}
				}
			}
		} catch (ExceptLgr e1) {
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
			boolean continuer=true;
			setSwtOldEntreprise();
			//			dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_EDITION);
			if(LgrMess.isDOSSIER_EN_RESEAU()){
				DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
				fireDeclencheCommandeController(e);
				continuer=masterDAO.dataSetEnModif();
			}
			if(continuer){
			taEntreprise = masterEntity.getTaEntreprise();
			if(LgrMess.isDOSSIER_EN_RESEAU())actRefresh();
			masterDAO.verifAutoriseModification(masterEntity);
			DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
			fireDeclencheCommandeController(e);
			dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_EDITION);
			initEtatBouton();
			}
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		}
	}

	@Override
	protected void actSupprimer() throws Exception {
		EntityTransaction transaction = dao.getEntityManager().getTransaction();		
		try {
			boolean continuer=true;
			VerrouInterface.setVerrouille(true);
			if(isUtilise())MessageDialog.openInformation(vue.getShell(), MessagesEcran
					.getString("Message.Attention"), MessagesEcran
					.getString("Message.suppression"));
			else
				if(LgrMess.isDOSSIER_EN_RESEAU()){
					DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
					fireDeclencheCommandeController(e);
					continuer=masterDAO.dataSetEnModif();
				}

			if(continuer){
				setMasterEntity(masterDAO.findById(masterEntity.getIdTiers()));
				if (MessageDialog.openConfirm(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("Entreprise.Message.Supprimer"))) {				
					dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_SUPPRESSION);
					try {
						DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
						fireDeclencheCommandeController(e);
					} catch (Exception e) {
						logger.error("",e);
					}
					dao.begin(transaction);
					taEntreprise =  masterEntity.getTaEntreprise();
					dao.supprimer(taEntreprise);
					masterEntity.removeEntreprise(taEntreprise);
					Object suivant=tableViewer.getElementAt(tableViewer.getTable().getSelectionIndex()+1);
					taEntreprise=null;
					dao.commit(transaction);
					if(suivant!=null)tableViewer.setSelection(new StructuredSelection(suivant),true);
					else tableViewer.selectionGrille(0);
					try {
						DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_ENREGISTRER_ID);
						fireDeclencheCommandeController(e);
					} catch (Exception e) {
						logger.error("",e);
					}
					actRefresh();		
					
				}
				dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
				initEtatBouton();
			}
		} catch (ExceptLgr e1) {
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

	public boolean isUtilise(){
		return (((SWTEntreprise)selectedEntreprise.getValue()).getIdEntreprise()!=null &&
				!dao.recordModifiable(dao.getNomTable(),
						((SWTEntreprise)selectedEntreprise.getValue()).getIdEntreprise()))||
						!masterDAO.autoriseModification(masterEntity);		
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
			switch (dao.getModeObjet().getMode()) {
			case C_MO_INSERTION:
				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("Entreprise.Message.Annuler")))) {
					//					remetTousLesChampsApresAnnulationSWT(dbc);
					//					if(((SWTEntreprise) selectedEntreprise.getValue()).getIdEntreprise()==null){
					//						modelEntreprise.getListeObjet().remove(
					//								((SWTEntreprise) selectedEntreprise.getValue()));
					//						writableList = new WritableList(realm, modelEntreprise
					//								.getListeObjet(), classModel);
					//						tableViewer.setInput(writableList);
					//						tableViewer.refresh();
					//						tableViewer.selectionGrille(0);
					//					}
					//					dao.annuler(taEntreprise);
					remetTousLesChampsApresAnnulationSWT(dbc);
					actRefresh();
					dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
					hideDecoratedFields();
					if(!annuleToutEnCours) {
						fireAnnuleTout(new AnnuleToutEvent(this));
					}
				}
				initEtatBouton();
				break;
			case C_MO_EDITION:
				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("Entreprise.Message.Annuler")))) {
					int rang =((WritableList)tableViewer.getInput()).indexOf(selectedEntreprise.getValue());
					remetTousLesChampsApresAnnulationSWT(dbc);
					List<SWTEntreprise> l = IHMmodel();
					if(rang!=-1)
						l.set(rang, swtOldEntreprise);

					writableList = new WritableList(realm, l, classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.setSelection(new StructuredSelection(swtOldEntreprise), true);

					dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
					hideDecoratedFields();
					if(!annuleToutEnCours) {
						fireAnnuleTout(new AnnuleToutEvent(this));
					}
				}
				initEtatBouton();

				break;
			case C_MO_CONSULTATION:
				//				actionFermer.run();
				fireChangementDePage(new ChangementDePageEvent(this,ChangementDePageEvent.DEBUT));
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
			if( getFocusCourantSWT().equals(vue.getTfCODE_TIERS()))
				result = true;
			break;
		default:
			break;
		}
		return result;
	}

	@Override
	protected void actPrecedent() throws Exception {
		ChangementDePageEvent evt = new ChangementDePageEvent(this,
				ChangementDePageEvent.PRECEDENT);
		fireChangementDePage(evt);
	}

	@Override
	protected void actSuivant() throws Exception {
		ChangementDePageEvent evt = new ChangementDePageEvent(this,
				ChangementDePageEvent.SUIVANT);
		fireChangementDePage(evt);
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

				switch ((getThis().dao.getModeObjet().getMode())) {
				case C_MO_CONSULTATION:
					if(getFocusCourantSWT().equals(vue.getGrille())){
//						PaEntrepriseSWT paEntrepriseSWT = new PaEntrepriseSWT(s2,SWT.NULL);
//						SWTPaEntrepriseController swtPaEntrepriseController = new SWTPaEntrepriseController(paEntrepriseSWT);
//
//						editorCreationId = EditorEntreprise.ID;
//						editorInputCreation = new EditorInputEntreprise();
//						
//						paramAfficheAideRecherche.setForceAffichageAideRemplie(true);
//						paramAfficheAideRecherche.setAfficheDetail(false);
//
//						ParamAfficheEntreprise paramAfficheEntreprise = new ParamAfficheEntreprise();
//						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
//						paramAfficheEntreprise.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//						paramAfficheEntreprise.setEcranAppelant(paAideController);
//						controllerEcranCreation = swtPaEntrepriseController;
//						parametreEcranCreation = paramAfficheEntreprise;
//
//						paramAfficheAideRecherche.setTypeEntite(TaEntreprise.class);
//						paramAfficheAideRecherche.setChampsRecherche(Const.C_NOM_ENTREPRISE);
//						paramAfficheAideRecherche.setDebutRecherche(vue.getTfNOM_ENTREPRISE().getText());
//						paramAfficheAideRecherche.setControllerAppelant(getThis());
//						paramAfficheAideRecherche.setModel(swtPaEntrepriseController.getModelEntreprise());
//						paramAfficheAideRecherche.setTypeObjet(swtPaEntrepriseController.getClassModel());
//						paramAfficheAideRecherche.setChampsIdentifiant(swtPaEntrepriseController.dao.getChampIdTable());
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
					// paramAfficheAideRecherche.setFocusDefaut(paAideRechercheController1.getVue().getTfChoix());

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
					//LgrShellUtil.afficheAideSWT(paramAfficheAide, null, paAide,paAideController, s);
					/*****************************************************************/
					paAideController.configPanel(paramAfficheAide);
					/*****************************************************************/
					dbc.getValidationStatusMap().addMapChangeListener(
							changeListener);

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
		String validationContext = "ENTREPRISE";
		try {
			IStatus s = null;
			int change=0;
			TaEntreprise u = new TaEntreprise();
			PropertyUtils.setSimpleProperty(u, nomChamp, value);
			if(((SWTEntreprise) selectedEntreprise.getValue()).getIdEntreprise()!=null) {
				u.setIdEntreprise(((SWTEntreprise) selectedEntreprise.getValue()).getIdEntreprise());
			}
			s = dao.validate(u,nomChamp,validationContext);
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
				LgrDozerMapper<SWTEntreprise,TaEntreprise> mapper = 
					new LgrDozerMapper<SWTEntreprise,TaEntreprise>();
				mapper.map((SWTEntreprise) selectedEntreprise.getValue(),taEntreprise);

			} 
			try {
				if(!enregistreToutEnCours) {
					sortieChamps();
					fireEnregistreTout(new AnnuleToutEvent(this,true));
					DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_ENREGISTRER_ID);
					fireDeclencheCommandeController(e);
				}
			} catch (Exception e) {
				logger.error("",e);
			}		
			taEntreprise=masterEntity.getTaEntreprise();
			dao.commit(transaction);
			changementDeSelection();
			actRefresh();
			transaction = null;
		} finally {
			if(transaction!=null && transaction.isActive()) {
				transaction.rollback();
			}
			initEtatBouton();
		}
	}


	public void initEtatComposant() {
		try {
			vue.getTfNOM_ENTREPRISE().setEditable(!isUtilise());
			changeCouleur(vue);
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
		}
	}

	public SWTEntreprise getSwtOldEntreprise() {
		return swtOldEntreprise;
	}

	public void setSwtOldBanque(SWTEntreprise swtOldEntreprise) {
		this.swtOldEntreprise = swtOldEntreprise;
	}

	public void setSwtOldEntreprise() {
		if (selectedEntreprise.getValue() != null)
			this.swtOldEntreprise = SWTEntreprise.copy((SWTEntreprise) selectedEntreprise.getValue());
		else {
			if (tableViewer.selectionGrille(0)){
				this.swtOldEntreprise = SWTEntreprise.copy((SWTEntreprise) selectedEntreprise.getValue());
				tableViewer.setSelection(new StructuredSelection(
						(SWTEntreprise) selectedEntreprise.getValue()), true);
			}
		}
	}

	public void setVue(PaEntrepriseSWT vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();
		mapComposantDecoratedField.put(vue.getTfNOM_ENTREPRISE(), vue
				.getFieldCODE_ENTREPRISE());
		mapComposantDecoratedField.put(vue.getTfCODE_TIERS(), vue
				.getFieldCODE_TIERS());
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
		//rafraichissement des valeurs dans la grille
		writableList = new WritableList(realm, IHMmodel(), classModel);
		tableViewer.setInput(writableList);


		tableViewer.selectionGrille(0);			
	}


	public ModelGeneralObjet<SWTEntreprise,TaEntrepriseDAO,TaEntreprise> getModelEntreprise() {
		return modelEntreprise;
	}

	public TaEntrepriseDAO getDao() {
		return dao;
	}

	public TaTiers getMasterEntity() {
		return masterEntity;
	}

	public void setMasterEntity(TaTiers masterEntity) {
		this.masterEntity = masterEntity;
	}

	public TaTiersDAO getMasterDAO() {
		return masterDAO;
	}

	public void setMasterDAO(TaTiersDAO masterDAO) {
		this.masterDAO = masterDAO;
	}

	@Override
	/**
	 * Creation des objet pour l'interface, a partir de l'entite principale.<br>
	 * @return
	 */
	public List<SWTEntreprise> IHMmodel() {
		LinkedList<TaEntreprise> ldao = new LinkedList<TaEntreprise>();
		LinkedList<SWTEntreprise> lswt = new LinkedList<SWTEntreprise>();

		if(masterEntity!=null && masterEntity.getTaEntreprise()!=null ){
			ldao.add(masterEntity.getTaEntreprise());

			LgrDozerMapper<TaEntreprise,SWTEntreprise> mapper = 
				new LgrDozerMapper<TaEntreprise,SWTEntreprise>();
			for (TaEntreprise o : ldao) {
				SWTEntreprise t = null;
				t = (SWTEntreprise) mapper.map(o, SWTEntreprise.class);
				lswt.add(t);
			}

		}
		return lswt;
	}

	protected void initEtatBouton() {
		//initEtatBouton(true);
		initEtatBoutonCommand(true,IHMmodel());
	}
	/**
	 * Initialisation des boutons suivant l'etat de l'objet "ibTaTable"
	 * @param initFocus - si vrai initialise le focus en fonction du mode
	 */
	protected void initEtatBoutonCommand(boolean initFocus,List model) {
		boolean trouve = false;		
		//	if (ibTaTableStandard.getFIBQuery().isOpen()){
		if(model!=null) {
			trouve = model.size()>0;
		} else {
			trouve = daoStandard.selectCount()>0;
		}
		switch (daoStandard.getModeObjet().getMode()) {
		case C_MO_INSERTION:
			enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_FERMER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID,false);
			break;
		case C_MO_EDITION:
			enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_FERMER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID,true);
			break;
		case C_MO_CONSULTATION:
			enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID,!trouve);
			enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID,trouve);
			enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID,false);
			enableActionAndHandler(C_COMMAND_GLOBAL_ANNULER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,trouve);
			enableActionAndHandler(C_COMMAND_GLOBAL_FERMER_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_SUPPRIMER_ID,trouve);
			enableActionAndHandler(C_COMMAND_GLOBAL_AIDE_ID,true);
			enableActionAndHandler(C_COMMAND_GLOBAL_REFRESH_ID,true);
			break;
		default:
			break;
		}
		//	}
		initEtatComposant();
		if(initFocus)
			initFocusSWT(daoStandard, mapInitFocus);	
	}

	public void modifMode(){
		if (!VerrouInterface.isVerrouille() ){
			try {
				if(!daoStandard.dataSetEnModif()) {
					if(masterEntity.getTaEntreprise()!=null) {
						actModifier();
					} else {
						actInserer();								
					}
					initEtatBouton(false);
				}
			} catch (Exception e1) {
				logger.error("",e1);
			}				
		} 
	}

}

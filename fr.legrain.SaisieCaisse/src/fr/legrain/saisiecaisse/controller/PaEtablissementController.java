package fr.legrain.saisiecaisse.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import fr.legrain.documents.dao.TaTPaiement;
import fr.legrain.documents.dao.TaTPaiementDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Document.SWTTPaiement;
import fr.legrain.gestCom.Module_SaisieCaisse.SWTEtablissement;
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
import fr.legrain.saisieCaisse.dao.TaEtablissement;
import fr.legrain.saisieCaisse.dao.TaEtablissementDAO;
import fr.legrain.saisiecaisse.divers.MessagesEcran;
import fr.legrain.saisiecaisse.divers.ParamAfficheEtablissement;
import fr.legrain.saisiecaisse.ecran.PaEtablissement;
import fr.legrain.tiers.divers.ParamAfficheTPaiement;
import fr.legrain.tiers.ecran.PaTypePaiementSWT;
import fr.legrain.tiers.ecran.SWTPaTypePaiementController;
import fr.legrain.tiers.editor.EditorInputTypePaiement;
import fr.legrain.tiers.editor.EditorTypePaiement;

public class PaEtablissementController extends JPABaseControllerSWTStandard
implements RetourEcranListener {

	static Logger logger = Logger.getLogger(PaEtablissementController.class.getName());
	private PaEtablissement vue = null;
	private TaEtablissementDAO dao = null;//new TaEtablissementDAO();

	private Object ecranAppelant = null;
	private SWTEtablissement swtEtablissement;
	private SWTEtablissement swtOldEtablissement;
	private Realm realm;
	private DataBindingContext dbc;

	private Class classModel = SWTEtablissement.class;
	private ModelGeneralObjet<SWTEtablissement,TaEtablissementDAO,TaEtablissement> modelEtablissement = null;//new ModelGeneralObjet<SWTEtablissement,TaEtablissementDAO,TaEtablissement>(dao,classModel);
	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private IObservableValue selectedEtablissement;
	private String[] listeChamp;
	private String nomClassController = this.getClass().getSimpleName();
	
	private TaEtablissement taEtablissement = null;

	private MapChangeListener changeListener = new MapChangeListener();
	
	public PaEtablissementController(PaEtablissement vue) {
		this(vue,null);
	}

	public PaEtablissementController(PaEtablissement vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaEtablissementDAO(getEm());
		modelEtablissement = new ModelGeneralObjet<SWTEtablissement,TaEtablissementDAO,TaEtablissement>(dao,classModel);
		setVue(vue);
		
		int nb = 10;

		// a faire avant l'initialisation du Binding
		vue.getGrille().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setSwtOldEtablissement();
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

	public void bind(PaEtablissement paEtablissement) {
		try {
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			tableViewer = new LgrTableViewer(paEtablissement.getGrille());
			tableViewer.createTableCol(classModel,paEtablissement.getGrille(), nomClassController,
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
//			writableList = new WritableList(realm, modelEtablissement.remplirListe(), classModel);
//			tableViewer.setInput(writableList);
			
			// Set up data binding.
			LgrViewerSupport.bind(tableViewer, 
					new WritableList(modelEtablissement.remplirListe(), classModel),
					BeanProperties.values(listeChamp)
					);

			selectedEtablissement = ViewersObservables.observeSingleSelection(tableViewer);

			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			tableViewer.selectionGrille(0);

			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);

			bindingFormMaitreDetail(dbc, realm, selectedEtablissement,classModel);
			selectedEtablissement.addChangeListener(new IChangeListener() {

				public void handleChange(ChangeEvent event) {
					if(selectedEtablissement!=null && selectedEtablissement.getValue()!=null) {
						if(((SWTEtablissement) selectedEtablissement.getValue()).getIdEtablissement()!=null) {
							taEtablissement = dao.findById(((SWTEtablissement) selectedEtablissement.getValue()).getIdEtablissement());
						}
						//null a tester ailleurs, car peut etre null en cas d'insertion
						fireChangementDeSelection(new ChangementDeSelectionEvent(PaEtablissementController.this));
					}
				}

			});

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
			if (param.getFocusDefautSWT() != null && !param.getFocusDefautSWT().isDisposed())
				param.getFocusDefautSWT().setFocus();
			else
				param.setFocusDefautSWT(vue
						.getGrille());
			if(param.getTitreFormulaire()!=null){
				vue.getLaTitreFormulaire().setText(param.getTitreFormulaire());
			} else {
				vue.getLaTitreFormulaire().setText(ParamAfficheEtablissement.C_TITRE_FORMULAIRE);
			}

			if(param.getTitreGrille()!=null){
				vue.getLaTitreGrille().setText(param.getTitreGrille());
			} else {
				vue.getLaTitreGrille().setText(ParamAfficheEtablissement.C_TITRE_GRILLE);
			}

			if(param.getSousTitre()!=null){
				vue.getLaTitreFenetre().setText(param.getSousTitre());
			} else {
				vue.getLaTitreFenetre().setText(ParamAfficheEtablissement.C_SOUS_TITRE);
			}

			if (param.getEcranAppelant() != null) {
				ecranAppelant = param.getEcranAppelant();
			}

			if(tableViewer==null) {
				//databinding pas encore realise
				bind(vue);
				tableViewer.tri(classModel, nomClassController,Const.C_FICHIER_LISTE_CHAMP_GRILLE);
				tableViewer.selectionGrille(0);
			} else {
				try {
					taEtablissement=null;
					actRefresh();
				} catch (Exception e) {
					logger.error("",e);
				}
			}
			
			VerrouInterface.setVerrouille(false);
			setSwtOldEtablissement();

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

	protected void initComposantsVue() throws ExceptLgr {
	}

	protected void initMapComposantChamps() {
		if (mapComposantChamps == null)
			mapComposantChamps = new LinkedHashMap<Control, String>();

		if (listeComposantFocusable == null)
			listeComposantFocusable = new ArrayList<Control>();
		listeComposantFocusable.add(vue.getGrille());

		vue.getTfNOM_ETABLISSEMENT().setToolTipText(Const.C_NOM_ETABLISSEMENT);
		vue.getTfCONTACT().setToolTipText(Const.C_CONTACT);
		vue.getTfJOURNAL_CAISSE().setToolTipText(Const.C_JOURNAL_CAISSE);
		
		

		mapComposantChamps.put(vue.getTfNOM_ETABLISSEMENT(), Const.C_NOM_ETABLISSEMENT);
		mapComposantChamps.put(vue.getTfCONTACT(), Const.C_CONTACT);
		mapComposantChamps.put(vue.getTfJOURNAL_CAISSE(), Const.C_JOURNAL_CAISSE);


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
				.getTfNOM_ETABLISSEMENT());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue
				.getTfNOM_ETABLISSEMENT());
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

	public PaEtablissementController getThis() {
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
					.getString("Depot.Message.Enregistrer"))) {

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
				setListeEntity(getModelEtablissement().remplirListe());
				dao.initValeurIdTable(taEtablissement);
				fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
						dao.getChampIdEntite(), dao.getValeurIdTable(),
						selectedEtablissement.getValue())));

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
					if(getFocusAvantAideSWT().equals(vue.getTfJOURNAL_CAISSE())){
						TaTPaiement f = null;
						TaTPaiementDAO t = new TaTPaiementDAO(getEm());
						f = t.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
						t = null;
						taEtablissement.setTaTPaiement(f);
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
				setSwtOldEtablissement();
				swtEtablissement = new SWTEtablissement();
				taEtablissement = new TaEtablissement();
				dao.inserer(taEtablissement);
				modelEtablissement.getListeObjet().add(swtEtablissement);
				writableList = new WritableList(realm, modelEtablissement.getListeObjet(), classModel);
				tableViewer.setInput(writableList);
				tableViewer.refresh();
				tableViewer.setSelection(new StructuredSelection(swtEtablissement));
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
	public void setSwtOldEtablissementRefresh() {
		if (selectedEtablissement.getValue()!=null){
			modelEtablissement.removeEntity(taEtablissement);
			if(LgrMess.isDOSSIER_EN_RESEAU())taEtablissement = dao.refresh(dao.findById(
					((SWTEtablissement) selectedEtablissement.getValue()).getIdEtablissement()));
			modelEtablissement.addEntity(taEtablissement);
			SWTEtablissement oldSwtDepot =(SWTEtablissement) selectedEtablissement.getValue(); 
			new LgrDozerMapper().map(taEtablissement, swtOldEtablissement);
			if(!oldSwtDepot.equals(swtOldEtablissement)){
				try {
					if(LgrMess.isDOSSIER_EN_RESEAU())actRefresh();
				} catch (Exception e) {
					logger.error("",e);
				}
			}
			this.swtOldEtablissement=SWTEtablissement.copy((SWTEtablissement)selectedEtablissement.getValue());
		}
	}
	@Override
	protected void actModifier() throws Exception {
		try {
			setSwtOldEtablissementRefresh();
			
//			taEtablissement = dao.findById(((SWTEtablissement) selectedEtablissement.getValue()).getIdEtablissement());
			dao.modifier(taEtablissement);
			
			initEtatBouton();
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
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
					.getString("Etablissement.Message.Supprimer"))) {

				dao.begin(transaction);
				TaEtablissement u = dao.findById(((SWTEtablissement) selectedEtablissement.getValue()).getIdEtablissement());
				dao.supprimer(u);
				modelEtablissement.removeEntity(taEtablissement);
				taEtablissement=null;
				dao.commit(transaction);
				transaction = null;
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
				if (MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("Etablissement.Message.Annuler"))) {
					if(((SWTEtablissement) selectedEtablissement.getValue()).getIdEtablissement()==null){
					modelEtablissement.getListeObjet().remove(
							((SWTEtablissement) selectedEtablissement.getValue()));
					writableList = new WritableList(realm, modelEtablissement
							.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					if(swtOldEtablissement!=null)
						tableViewer.setSelection(new StructuredSelection(swtOldEtablissement), true);
					else
						tableViewer.selectionGrille(0);
					}
					dao.annuler(taEtablissement);
					hideDecoratedFields();
				}
				initEtatBouton();
				break;
			case C_MO_EDITION:
				if (MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("Etablissement.Message.Annuler"))) {
					int rang = modelEtablissement.getListeObjet().indexOf(selectedEtablissement.getValue());
					modelEtablissement.getListeObjet().set(rang, swtOldEtablissement);
					writableList = new WritableList(realm, modelEtablissement.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.setSelection(new StructuredSelection(swtOldEtablissement), true);
					dao.annuler(taEtablissement);
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
			if(getFocusCourantSWT().equals(vue.getTfJOURNAL_CAISSE()))
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

				switch (PaEtablissementController.this.dao.getModeObjet().getMode()) {
				case C_MO_CONSULTATION:
//					if(getFocusCourantSWT().equals(vue.getGrille())){
//						PaDepot paUniteSWT = new PaDepot(s2,SWT.NULL);
//						PaDepotController swtPaUniteController = new PaDepotController(paUniteSWT);
//
//						editorCreationId = EditorDepot.ID;
//						editorInputCreation = new EditorInputDepot();
//
//						ParamAfficheDepot paramAfficheDepot = new ParamAfficheDepot();
//						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
//						paramAfficheDepot.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//						paramAfficheDepot.setEcranAppelant(paAideController);
//						controllerEcranCreation = swtPaUniteController;
//						parametreEcranCreation = paramAfficheDepot;
//
//						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_UNITE);
//						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_T_PAIEMENT().getText());
//						paramAfficheAideRecherche.setControllerAppelant(PaDepotController.this);
//						paramAfficheAideRecherche.setModel(swtPaUniteController.getModelDepot());
//						paramAfficheAideRecherche.setTypeObjet(swtPaUniteController.getClassModel());
//						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_UNITE);
//					}
					break;
				case C_MO_EDITION:
				case C_MO_INSERTION:
					if (getFocusCourantSWT().equals(vue.getTfJOURNAL_CAISSE())) {
						PaTypePaiementSWT paTypePaiementSWT = new PaTypePaiementSWT(s2,SWT.NULL);
						SWTPaTypePaiementController swtPaTypePaiementController = new SWTPaTypePaiementController(paTypePaiementSWT);

						editorCreationId = EditorTypePaiement.ID;
						editorInputCreation = new EditorInputTypePaiement();
						
						ParamAfficheTPaiement paramAfficheTPaiement = new ParamAfficheTPaiement();
						paramAfficheAideRecherche.setJPQLQuery(new TaTPaiementDAO(getEm()).getJPQLQuery());
						paramAfficheTPaiement.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheTPaiement.setEcranAppelant(paAideController);
						
						controllerEcranCreation = swtPaTypePaiementController;
						parametreEcranCreation = paramAfficheTPaiement;

						paramAfficheAideRecherche.setTypeEntite(TaTPaiement.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_T_PAIEMENT);
						paramAfficheAideRecherche.setDebutRecherche("");
						paramAfficheAideRecherche.setControllerAppelant(PaEtablissementController.this);
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTPaiement,TaTPaiementDAO,TaTPaiement>(SWTTPaiement.class,getEm()));

						paramAfficheAideRecherche.setTypeObjet(swtPaTypePaiementController.getClassModel());
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_T_PAIEMENT);
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
					paAideController.addRetourEcranListener(PaEtablissementController.this);
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
		String validationContext = "ETABLISSEMENT";
		try {
			setActiveAide(true);
			IStatus s = null;
			int change=0;
			    if(nomChamp.equals(Const.C_JOURNAL_CAISSE)){
			    	TaTPaiement tp = new TaTPaiement();
			    	TaTPaiementDAO dao = new TaTPaiementDAO(getEm());
					dao.setModeObjet(getDao().getModeObjet());

					PropertyUtils.setSimpleProperty(tp, Const.C_CODE_T_PAIEMENT, value);

					s = dao.validate(tp,Const.C_CODE_T_PAIEMENT,validationContext);

					if(s.getSeverity()!=IStatus.ERROR ){
						tp = dao.findByCode((String)value);
						taEtablissement.setTaTPaiement(tp);
						((SWTEtablissement)selectedEtablissement.getValue()).setIdJournalCaisse(tp.getIdTPaiement());
					}
					dao = null;
			    }else{					
				TaEtablissement u = new TaEtablissement();
				PropertyUtils.setSimpleProperty(u, nomChamp, value);
				if(((SWTEtablissement) selectedEtablissement.getValue()).getIdEtablissement()!=null) {
					u.setIdEtablissement(((SWTEtablissement) selectedEtablissement.getValue()).getIdEtablissement());
				}
				s = dao.validate(u,nomChamp,validationContext);
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
			ctrlTousLesChampsAvantEnregistrement();
			dao.begin(transaction);
			
			if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
				LgrDozerMapper<SWTEtablissement,TaEtablissement> mapper = new LgrDozerMapper<SWTEtablissement,TaEtablissement>();
				mapper.map((SWTEtablissement) selectedEtablissement.getValue(),taEtablissement);
				
				dao.enregistrerMerge(taEtablissement);


				
			} else if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)) {
				LgrDozerMapper<SWTEtablissement,TaEtablissement> mapper = new LgrDozerMapper<SWTEtablissement,TaEtablissement>();
				mapper.map((SWTEtablissement) selectedEtablissement.getValue(),taEtablissement);

				taEtablissement=dao.enregistrerMerge(taEtablissement);
				modelEtablissement.getListeEntity().add(taEtablissement);
			}
			
			dao.commit(transaction);
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
				vue.getTfNOM_ETABLISSEMENT().setEditable(!isUtilise());
				changeCouleur(vue);
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
		}
	}
	public boolean isUtilise(){
		return ((SWTEtablissement)selectedEtablissement.getValue()).getIdEtablissement()!=null &&
		!dao.recordModifiable(dao.getNomTable(),
				((SWTEtablissement)selectedEtablissement.getValue()).getIdEtablissement());		
	}
	public SWTEtablissement getSwtOldEtablissement() {
		return swtOldEtablissement;
	}

	public void setSwtOldUnite(SWTEtablissement swtOldEtablissement) {
		this.swtOldEtablissement = swtOldEtablissement;
	}

	public void setSwtOldEtablissement() {
		if (selectedEtablissement.getValue() != null)
			this.swtOldEtablissement = SWTEtablissement.copy((SWTEtablissement) selectedEtablissement.getValue());
		else {
			if(tableViewer.selectionGrille(0)){
				this.swtOldEtablissement = SWTEtablissement.copy((SWTEtablissement) selectedEtablissement.getValue());
				tableViewer.setSelection(new StructuredSelection(
						(SWTEtablissement) selectedEtablissement.getValue()), true);
			}
		}
	}

	public void setVue(PaEtablissement vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();

		mapComposantDecoratedField.put(vue.getTfNOM_ETABLISSEMENT(), vue.getFieldNOM_ETABLISSEMENT());
		mapComposantDecoratedField.put(vue.getTfCONTACT(), vue.getFieldCONTACT());
		mapComposantDecoratedField.put(vue.getTfJOURNAL_CAISSE(), vue.getFieldJOURNAL_CAISSE());

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
		writableList = new WritableList(realm, modelEtablissement.remplirListe(), classModel);
		tableViewer.setInput(writableList);
		tableViewer.refresh();

		//repositionnement sur la valeur courante
		int idActuel = 0;
		if (taEtablissement!=null) { //enregistrement en cours de modification/insertion
			idActuel = taEtablissement.getIdEtablissement();
		} else if(selectedEtablissement!=null && (SWTEtablissement) selectedEtablissement.getValue()!=null) {
			idActuel = ((SWTEtablissement) selectedEtablissement.getValue()).getIdEtablissement();
		}
		
		if(idActuel!=0) {
			tableViewer.setSelection(new StructuredSelection(modelEtablissement.recherche(Const.C_ID_ETABLISSEMENT, idActuel)));
		}else
			tableViewer.selectionGrille(0);
		
	}

	public ModelGeneralObjet<SWTEtablissement,TaEtablissementDAO,TaEtablissement> getModelEtablissement() {
		return modelEtablissement;
	}
	
	public TaEtablissementDAO getDao() {
		return dao;
	}

	public TaEtablissement getTaEtablissement() {
		return taEtablissement;
	}
	public boolean changementPageValide(){				
		return (daoStandard.selectAll().size()>0);
	}
}


package fr.legrain.tiers.ecran;

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
import fr.legrain.documents.dao.TaTPaiement;
import fr.legrain.documents.dao.TaTPaiementDAO;
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
import fr.legrain.gestCom.Module_Document.SWTTPaiement;
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
import fr.legrain.tiers.dao.TaCPaiement;
import fr.legrain.tiers.divers.ParamAfficheTPaiement;
import fr.legrain.tiers.editor.EditorInputTypePaiement;
import fr.legrain.tiers.editor.EditorTypePaiement;

public class SWTPaTypePaiementController extends JPABaseControllerSWTStandard
implements RetourEcranListener {

	static Logger logger = Logger.getLogger(SWTPaTypePaiementController.class.getName());
	private PaTypePaiementSWT vue = null;
	private TaTPaiementDAO dao = null;//new TaTPaiementDAO();

	private Object ecranAppelant = null;
	private SWTTPaiement swtTPaiement;
	private SWTTPaiement swtOldTPaiement;
	private Realm realm;
	private DataBindingContext dbc;

	private Class classModel = SWTTPaiement.class;
	private ModelGeneralObjet<SWTTPaiement,TaTPaiementDAO,TaTPaiement> modelTPaiement = null;//new ModelGeneralObjet<SWTTPaiement,TaTPaiementDAO,TaTPaiement>(dao,classModel);
	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private IObservableValue selectedTPaiement;
	private String[] listeChamp;
	private String nomClassController = this.getClass().getSimpleName();
	
	private TaTPaiement taTPaiement = null;

	private MapChangeListener changeListener = new MapChangeListener();
	private LgrDozerMapper<SWTTPaiement,TaTPaiement> mapper = new LgrDozerMapper<SWTTPaiement,TaTPaiement>();
	
	public SWTPaTypePaiementController(PaTypePaiementSWT vue) {
		this(vue,null);
	}

	public SWTPaTypePaiementController(PaTypePaiementSWT vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaTPaiementDAO(getEm());
		modelTPaiement = new ModelGeneralObjet<SWTTPaiement,TaTPaiementDAO,TaTPaiement>(dao,classModel);
		setVue(vue);
		
		int nb = 10;

		// a faire avant l'initialisation du Binding
		vue.getGrille().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setSwtOldTPaiement();
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

	public void bind(PaTypePaiementSWT paUniteSWT) {
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
//			writableList = new WritableList(realm, modelTPaiement.remplirListe(), classModel);
//			tableViewer.setInput(writableList);
			
			// Set up data binding.
			LgrViewerSupport.bind(tableViewer, 
					new WritableList(modelTPaiement.remplirListe(), classModel),
					BeanProperties.values(listeChamp)
					);

			selectedTPaiement = ViewersObservables.observeSingleSelection(tableViewer);

			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			tableViewer.selectionGrille(0);

			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);

			bindingFormMaitreDetail(dbc, realm, selectedTPaiement,classModel);
			
			selectedTPaiement.addChangeListener(new IChangeListener(){

				@Override
				public void handleChange(ChangeEvent event){
					// TODO Auto-generated method stub
					changementDeSelection();	
				}
			});

		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("", e);
		}
	}

	private void changementDeSelection() {
		if(selectedTPaiement != null && selectedTPaiement.getValue() != null){
			if(((SWTTPaiement)selectedTPaiement.getValue()).getIdTPaiement() != null){
				taTPaiement = dao.findById(((SWTTPaiement)selectedTPaiement.getValue()).getIdTPaiement());
			}
		}
		fireChangementDeSelection(new ChangementDeSelectionEvent(SWTPaTypePaiementController.this));
	}
	public Composite getVue() {
		return vue;
	}

	public ResultAffiche configPanel(ParamAffiche param) {
		if (param != null) {
			if (((ParamAfficheTPaiement) param).getFocusDefautSWT() != null && !((ParamAfficheTPaiement) param).getFocusDefautSWT().isDisposed())
				((ParamAfficheTPaiement) param).getFocusDefautSWT().setFocus();
			else
				((ParamAfficheTPaiement) param).setFocusDefautSWT(vue.getGrille());
			vue.getLaTitreFormulaire().setText(((ParamAfficheTPaiement) param).getTitreFormulaire());
			vue.getLaTitreGrille().setText(((ParamAfficheTPaiement) param).getTitreGrille());
			vue.getLaTitreFenetre().setText(((ParamAfficheTPaiement) param).getSousTitre());

			if (param.getEcranAppelant() != null) {
				ecranAppelant = param.getEcranAppelant();
			}
			Map<String,String[]> map = dao.getParamWhereSQL();
			if(param.getIdFiche()!=null &&  !param.getIdFiche().equals("")) {
				if(map==null) map = new HashMap<String,String[]>();
				map.clear();
				map.put("idTPaiement",new String[]{"=",param.getIdFiche()});
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
			setSwtOldTPaiement();

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
		if(mapInfosVerifSaisie == null)
			mapInfosVerifSaisie = new HashMap<Control, InfosVerifSaisie>(); 
		mapInfosVerifSaisie.put(vue.getTfCODE_T_PAIEMENT(),new InfosVerifSaisie(new TaTPaiement(),Const.C_CODE_T_PAIEMENT,null));
		mapInfosVerifSaisie.put(vue.getTfLIB_T_PAIEMENT(),new InfosVerifSaisie(new TaTPaiement(),Const.C_LIB_T_PAIEMENT,null));
		mapInfosVerifSaisie.put(vue.getTfFIN_MOIS_C_PAIEMENT(), new InfosVerifSaisie(new TaCPaiement(),Const.C_FIN_MOIS_C_PAIEMENT,null));
		mapInfosVerifSaisie.put(vue.getTfREPORT_C_PAIEMENT(), new InfosVerifSaisie(new TaCPaiement(),Const.C_REPORT_C_PAIEMENT,null));
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

		vue.getTfCODE_T_PAIEMENT().setToolTipText(Const.C_CODE_T_PAIEMENT);
		vue.getTfLIB_T_PAIEMENT().setToolTipText(Const.C_LIB_T_PAIEMENT);
		vue.getTfLIB_T_PAIEMENT().setToolTipText(Const.C_COMPTE);
		vue.getTfFIN_MOIS_C_PAIEMENT().setToolTipText(Const.C_FIN_MOIS_C_PAIEMENT);
		vue.getTfREPORT_C_PAIEMENT().setToolTipText(Const.C_REPORT_C_PAIEMENT);

		mapComposantChamps.put(vue.getTfCODE_T_PAIEMENT(), Const.C_CODE_T_PAIEMENT);
		mapComposantChamps.put(vue.getTfLIB_T_PAIEMENT(), Const.C_LIB_T_PAIEMENT);
		mapComposantChamps.put(vue.getTfCOMPTE(), Const.C_COMPTE);
		mapComposantChamps.put(vue.getTfREPORT_C_PAIEMENT(),Const.C_REPORT_T_PAIEMENT);
		mapComposantChamps.put(vue.getTfFIN_MOIS_C_PAIEMENT(), Const.C_FIN_MOIS_T_PAIEMENT);

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
				.getTfCODE_T_PAIEMENT());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue
				.getTfCODE_T_PAIEMENT());
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

	public SWTPaTypePaiementController getThis() {
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
					.getString("TPaiement.Message.Enregistrer"))) {

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
				setListeEntity(getModelTPaiement().remplirListe());
				dao.initValeurIdTable(taTPaiement);
				fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
						dao.getChampIdEntite(), dao.getValeurIdTable(),
						selectedTPaiement.getValue())));

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
				setSwtOldTPaiement();
				swtTPaiement = new SWTTPaiement();
		
				taTPaiement = new TaTPaiement();
				dao.inserer(taTPaiement);
				modelTPaiement.getListeObjet().add(swtTPaiement);
				writableList = new WritableList(realm, modelTPaiement.getListeObjet(), classModel);
				tableViewer.setInput(writableList);
				tableViewer.refresh();
				tableViewer.setSelection(new StructuredSelection(swtTPaiement));
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
			setSwtOldTPaiement();
			
			taTPaiement = dao.findById(((SWTTPaiement) selectedTPaiement.getValue()).getIdTPaiement());
			dao.modifier(taTPaiement);
			
			initEtatBouton();
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		}
	}
	
	public boolean containsEntity(TaTPaiement entite){
		if(modelTPaiement.getListeEntity() != null){
			for (Object e :modelTPaiement.getListeEntity()){
				if(((TaTPaiement)e).getIdTPaiement() == entite.getIdTPaiement())
					return true;
			}
		}
		return false;
	}

	@Override
	protected void actSupprimer() throws Exception {
		try {
			VerrouInterface.setVerrouille(true);
			if(isUtilise())MessageDialog.openInformation(vue.getShell(), MessagesEcran
					.getString("Message.Attention"), MessagesEcran
					.getString("Message.suppression"));
			else		
				if (MessageDialog.openConfirm(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("TPaiement.Message.Supprimer"))) {

					dao.getEntityManager().getTransaction().begin();
					TaTPaiement u = dao.findById(((SWTTPaiement) selectedTPaiement.getValue()).getIdTPaiement());
					dao.supprimer(u);
					dao.getEntityManager().getTransaction().commit();
					Object suivant=tableViewer.getElementAt(tableViewer.getTable().getSelectionIndex()+1);
					modelTPaiement.removeEntity(u);
					taTPaiement=null;
					dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
					if(suivant!=null)tableViewer.setSelection(new StructuredSelection(suivant),true);
					else tableViewer.selectionGrille(0);
					actRefresh(); //ajouter pour tester jpa

				}
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		} finally {
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
						.getString("TPaiement.Message.Annuler"))) {
					if(((SWTTPaiement) selectedTPaiement.getValue()).getIdTPaiement()==null){
					modelTPaiement.getListeObjet().remove(
							((SWTTPaiement) selectedTPaiement.getValue()));
					writableList = new WritableList(realm, modelTPaiement
							.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					if(swtOldTPaiement!=null)
						tableViewer.setSelection(new StructuredSelection(swtOldTPaiement), true);
					else
						tableViewer.selectionGrille(0);
					}
					dao.annuler(taTPaiement);
					hideDecoratedFields();
				}
				initEtatBouton();
				break;
			case C_MO_EDITION:
				if (MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("TPaiement.Message.Annuler"))) {
					int rang = modelTPaiement.getListeObjet().indexOf(selectedTPaiement.getValue());
					modelTPaiement.getListeObjet().set(rang, swtOldTPaiement);
					writableList = new WritableList(realm, modelTPaiement.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.setSelection(new StructuredSelection(swtOldTPaiement), true);
					dao.annuler(taTPaiement);
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
		FonctionGetInfosXmlAndProperties fonctionGetInfosXmlAndProperties = new FonctionGetInfosXmlAndProperties(mapper);
		fonctionGetInfosXmlAndProperties.cleanValueMapAttributeTable();
		
		fonctionGetInfosXmlAndProperties.setValueMapAttributeTable(TaTPaiement.class.getSimpleName()+".head");
		fonctionGetInfosXmlAndProperties.setValueMapAttributeTable(TaTPaiement.class.getSimpleName()+".detail");
				

		LinkedHashMap<String,AttributElementResport> mapAttributeTablHead = fonctionGetInfosXmlAndProperties.getMapAttributeTablHead(); 
		LinkedHashMap<String,AttributElementResport> mapAttributeTablDetail = fonctionGetInfosXmlAndProperties.getMapAttributeTablDetail();
		
		Collection<TaTPaiement> collectionTaTPaiement = modelTPaiement.getListeEntity();
		fonctionGetInfosXmlAndProperties.findInfosFileXml(TaTPaiement.class.getName(),SWTTPaiement.class.getName(),
				listeChamp,"mapping");
		fonctionGetInfosXmlAndProperties.getInfosObjetJPA(taTPaiement);
		
		ConstEdition constEdition = new ConstEdition();
		
//		Impression impression = new Impression(constEdition,taTPaiement,collectionTaTPaiement,nomChampIdTable,
//				taTPaiement.getIdTPaiement());
		
		String nomDossier = null;
		
		int nombreLine = collectionTaTPaiement.size();
		
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
			
			constEdition.addValueList(tableViewer,nomClassController);
			
			String folderEditionDynamique = Const.C_RCP_INSTANCE_LOCATION+"/"+Const.C_NOM_PROJET_TMP+"/"+
				TaTPaiement.class.getSimpleName();
			constEdition.makeFolderEditions(folderEditionDynamique);
			Path pathFileReport = new Path(folderEditionDynamique+"/"+Const.C_NOM_VU_T_PAIEMENT+".rptdesign");
			
			final String pathFileReportDynamic = pathFileReport.toPortableString();
			
			MakeDynamiqueReport DynamiqueReport = new MakeDynamiqueReport(constEdition.getNameTableEcran(),
					constEdition.getNameTableBDD(),pathFileReportDynamic,Const.C_NOM_VU_T_PAIEMENT,
					ConstEdition.PAGE_ORIENTATION_LANDSCAPE,nomDossier);
			DynamiqueReport.setSimpleNameEntity(TaTPaiement.class.getSimpleName());
			/**************************************************************/
			DynamiqueReport.setFonctionGetInfosXml(fonctionGetInfosXmlAndProperties);
			DynamiqueReport.setNomObjet(TaTPaiement.class.getSimpleName());
			/**************************************************************/
			
			Map<String, AttributElementResport> attribuGridHeader = new LinkedHashMap<String, AttributElementResport>();
			String nameHeaderTitle = ConstEditionTiers.TITLE_EDITION_T_PAIEMENT;
			attribuGridHeader.put(nameHeaderTitle, new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
					ConstEdition.FONT_SIZE_XX_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
					ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
					,""));
			
			String nameSousHeaderTitle = ConstEditionTiers.SOUS_TITLE_EDITION_T_PAIEMENT;
			attribuGridHeader.put(nameSousHeaderTitle, new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
					ConstEdition.FONT_SIZE_X_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
					ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
					,ConstEdition.COLOR_GRAY));
			ConstEdition.CONTENT_COMMENTS = ConstEditionTiers.COMMENTAIRE_EDITION_DEFAUT;
			
			DynamiqueReport.initializeBuildDesignReportConfig();
			DynamiqueReport.makePageMater("1", "1", "1", "1", "100");
			DynamiqueReport.makeReportHeaderGrid(3,5,100,ConstEdition.UNITS_PERCENTAGE,attribuGridHeader);
			DynamiqueReport.biuldTableReport("100", ConstEdition.UNITS_PERCENTAGE, 
					Const.C_NOM_VU_T_PAIEMENT,1,1,2,"40", mapAttributeTablHead, mapAttributeTablDetail);
			DynamiqueReport.savsAsDesignHandle();
//			impression.imprimer(true,pathFileReportDynamic,null,"Type Paiement",
//					TaTPaiement.class.getSimpleName(),false);
				
			/** 01/03/2010 modifier les editions (zhaolin) **/
			BaseImpressionEdition impressionEdition = new BaseImpressionEdition(constEdition,taTPaiement,
					getEm(),collectionTaTPaiement,taTPaiement.getIdTPaiement());
			impressionEdition.impressionEditionTypeEntity(pathFileReportDynamic,"Paiement");
		}
		
		
//		/******************************/
//			SWTInfoEntreprise infoEntreprise = null;
//			infoEntreprise = SWT_IB_TA_INFO_ENTREPRISE.infosEntreprise("1", infoEntreprise);
//			String nomDossier = null;
//			ConstEdition objetConstEdition = new ConstEdition(); 
//			int nombreLine = objetConstEdition.nombreLineTable(tableViewer);
//			
//			LinkedList<Integer> idSWTTPaiement = new LinkedList<Integer>();//pour stocker tous les id dans un ecrean
//			LinkedList<Integer> oneIDSWTTPaiement = new LinkedList<Integer>();//pour stocker un id d'une choix
//			
//			LinkedList<SWTTPaiement> objectContenuTable =  modelTPaiement.getListeObjet();
//			
//			for (SWTTPaiement tpaiement : objectContenuTable) {
//				//System.out.println("ID_UNITE--"+unite.getID_UNITE());
//				idSWTTPaiement.add(tpaiement.getIdTPaiement());
//			}
//			Integer oneIDTPaiement = ((SWTTPaiement)selectedTPaiement.getValue()).getIdTPaiement();
//			//System.out.println("oneID--"+oneIDUnite);
//			oneIDSWTTPaiement.add(oneIDTPaiement);
//			
//			if(nombreLine==0){
//				MessageDialog.openWarning(vue.getShell(), ConstEdition.TITRE_MESSAGE_EDITION_VIDE,
//						ConstEdition.EDITION_VIDE);
//			}
//			else{
//				if(infoEntreprise.getID_INFO_ENTREPRISE()==null){
//					nomDossier = objetConstEdition.INFOS_VIDE;
//				}
//				else{
//					nomDossier = infoEntreprise.getNOM_INFO_ENTREPRISE();	
//				}
////#JPA
////				String querySql = ibTaTable.getFIBQuery().getQuery().getQueryString();//select * from V_UNITE
////				String nameTable = ibTaTable.nomTable;
//				
//				String nameClass = SWTPaTypePaiementController.class.getSimpleName();
//				String sqlQueryStart = "SELECT ";
//				String sqlQueryEnd = " FROM "+Const.C_NOM_VU_T_PAIEMENT;
//
//				String sqlQueryMiddle = objetConstEdition.addValueList(tableViewer, nameClass);
//				ArrayList<String> nameTableEcran = objetConstEdition.getNameTableEcran();
//				ArrayList<String> nameTableBDD = objetConstEdition.getNameTableBDD();
//				/*
//				 * name ecran ==> UNITE--LIBELLE
//				 */
////				for(int i=0;i<nameTableEcran.size();i++){
////				System.out.println(nameTableEcran.get(i));
////				}
//				/*
//				 * name BDD ==> CODE_UNITE(string)--LIBL_UNITE(string)
//				 */
////				System.out.println("***********");
////				for(int i=0;i<nameTableBDD.size();i++){
////				System.out.println(nameTableBDD.get(i));
////				}
//				
//				String sqlQuery = sqlQueryStart+sqlQueryMiddle+sqlQueryEnd+";";
//				//System.out.println(sqlQuery);//SELECT CODE_UNITE,LIBL_UNITE FROM V_UNITE;
//				String  C_FICHIER_BDD = Const.C_FICHIER_BDD.replaceFirst(":", "/");
//				//System.out.println(C_FICHIER_BDD);
//				String FILE_BDD = Const.C_URL_BDD+"//"+C_FICHIER_BDD;//jdbc:firebirdsql://localhost/C:/runtime-GestionCommercialeComplet.product/dossier/Bd/GEST_COM.FDB
//				//System.out.println(FILE_BDD);
//				
//				Path pathFileReport = new Path(Const.C_RCP_INSTANCE_LOCATION+"/"+Const.C_NOM_PROJET_TMP+"/"+Const.C_NOM_VU_T_PAIEMENT
//						+".rptdesign");
//				final String PATH_FILE_REPORT = pathFileReport.toPortableString();
//				//System.out.println(PATH_FILE_REPORT);
//				Map<String, AttribuElement> attribuTabHeader = new LinkedHashMap<String, AttribuElement>();
//				attribuTabHeader.put("TYPE PAIEMENT", new AttribuElement("50",ConstEdition.TEXT_ALIGN_CENTER,
//						ConstEdition.FONT_SIZE_MEDIUM,ConstEdition.FONT_WEIGHT_BOLD,
//						ConstEdition.UNITS_PERCENTAGE,ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
//						,""));
//				attribuTabHeader.put("LIBELLE", new AttribuElement("50",ConstEdition.TEXT_ALIGN_CENTER,
//						ConstEdition.FONT_SIZE_MEDIUM,ConstEdition.FONT_WEIGHT_BOLD,
//						ConstEdition.UNITS_PERCENTAGE,ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
//						,""));
//				
//				
//				Map<String, AttribuElement> attribuTabDetail = new LinkedHashMap<String, AttribuElement>();
//				attribuTabDetail.put("CODE_T_PAIEMENT", new AttribuElement("50",ConstEdition.TEXT_ALIGN_CENTER,
//						ConstEdition.FONT_SIZE_SMALL,ConstEdition.FONT_WEIGHT_NORMAL,
//						ConstEdition.UNITS_PERCENTAGE,ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
//						,""));
//				attribuTabDetail.put("LIB_T_PAIEMENT", new AttribuElement("50",ConstEdition.TEXT_ALIGN_CENTER,
//						ConstEdition.FONT_SIZE_SMALL,ConstEdition.FONT_WEIGHT_NORMAL,
//						ConstEdition.UNITS_PERCENTAGE,ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
//						,""));
//			
//				
//				MakeDynamiqueReport DynamiqueReport = new MakeDynamiqueReport(nameTableEcran,nameTableBDD,
//						sqlQuery/*,ConstEdition.BIRT_HOME*/,PATH_FILE_REPORT,Const.
//						C_DRIVER_JDBC,FILE_BDD,Const.C_USER,Const.C_PASS,
//						Const.C_NOM_VU_UNITE,ConstEdition.PAGE_ORIENTATION_PORTRAIT,
//						nomDossier); 
//				
//				Map<String, AttribuElement> attribuGridHeader = new LinkedHashMap<String, AttribuElement>();
//				String nameHeaderTitle = ConstEditionArticle.TITLE_EDITION_ARTICLE_UNITE;
//				attribuGridHeader.put(nameHeaderTitle, new AttribuElement("",ConstEdition.TEXT_ALIGN_CENTER,
//						ConstEdition.FONT_SIZE_XX_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
//						ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
//						,""));
//				String nameSousHeaderTitle = ConstEditionArticle.SOUS_TITLE_EDITION_ARTICLE_UNITE;
//				attribuGridHeader.put(nameSousHeaderTitle, new AttribuElement("",ConstEdition.TEXT_ALIGN_CENTER,
//						ConstEdition.FONT_SIZE_X_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
//						ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
//						,ConstEdition.COLOR_GRAY));
//				
//				//DynamiqueReport.buildDesignConfig();
//				ConstEdition.CONTENT_COMMENTS = ConstEditionArticle.COMMENTAIRE_EDITION_DEFAUT;
//				
//				DynamiqueReport.initializeBuildDesignReportConfig();
//				DynamiqueReport.makePageMater("1", "1", "1", "1", "100");
//				DynamiqueReport.makeReportHeaderGrid(3,5,100,ConstEdition.UNITS_PERCENTAGE,attribuGridHeader);
//				DynamiqueReport.makeReportTableDB(100,ConstEdition.UNITS_PERCENTAGE,
//						Const.C_NOM_VU_UNITE,attribuTabHeader,attribuTabDetail,1,1,2,5,"30");
//				DynamiqueReport.savsAsDesignHandle();
//					
//				ConstEdition constEdition = new ConstEdition();
//				Bundle bundleCourant = ArticlesPlugin.getDefault().getBundle();
//				
////				String reportFileLocation = ConstEdition.FOLDER_REPORT_PLUGIN;
////				
////				URL urlReportFile = Platform.asLocalURL(bundleCourant.getEntry(reportFileLocation));
////				URI uriReportFile = new URI("file", urlReportFile.getAuthority(),
////						urlReportFile.getPath(), urlReportFile.getQuery(), null);
////
////				File pathReport = new File(uriReportFile);
////				String pathFileEdition = pathReport.toString()+ConstEditionArticle.SOUS_REPORT_UNITE;
////				
////				File reportFile = new File(pathFileEdition);
//				
//				File reportFile = constEdition.findPathReportPlugin(bundleCourant, 
//						ConstEdition.FOLDER_REPORT_PLUGIN, ConstEditionArticle.SOUS_REPORT_UNITE);
//				
//				
//				final String namePlugin = bundleCourant.getSymbolicName();//name plugin is <<ARTICLES>>
//				
//				
//				constEdition.makeFolderEditions(Const.C_REPERTOIRE_BASE+
//						Const.C_REPERTOIRE_PROJET.replaceFirst("/", "")+
//						ConstEdition.FOLDER_EDITION);
//				
//				String FloderEdition = Const.C_REPERTOIRE_BASE+
//				Const.C_REPERTOIRE_PROJET.replaceFirst("/", "")+ConstEdition.FOLDER_EDITION+
//				namePlugin+"/"+ConstEditionArticle.ARTICLE_UNITE;
//
//				File FloderFileEditions = constEdition.makeFolderEditions(FloderEdition);
//
//				String nomOnglet = ConstEdition.EDITION+ConstEditionArticle.ARTICLE_UNITE;
//
//				Shell dialogShell = new Shell(vue.getShell(),
//						//SWT.DIALOG_TRIM |SWT.APPLICATION_MODAL);
//						SWT.RESIZE | SWT.CLOSE | SWT.MAX | SWT.APPLICATION_MODAL);
//				dialogShell.setText(ConstEdition.TITLE_SHELL);
//				dialogShell.setLayout(new FillLayout());
//				
//				SwtCompositeReport_new dialogReport = new SwtCompositeReport_new(dialogShell,SWT.NULL);
//								
//				constEdition.addValues(idSWTTPaiement, oneIDSWTTPaiement);
//				constEdition.setPARAM_ID_TABLE(ConstEditionArticle.PARAM_REPORT_ID_UNITE);
//				
//				constEdition.openDialogChoixEdition_new(dialogReport, FloderFileEditions, 
//						PATH_FILE_REPORT, namePlugin,nomOnglet,dialogShell,reportFile);
//			}
////		}
////		else{
////			MessageDialog.openWarning(vue.getShell(), ConstEdition.TITRE_MESSAGE_EDITION_VIDE,
////					ConstEdition.EDITION_NON_USABLE);
////		}
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

				switch (SWTPaTypePaiementController.this.dao.getModeObjet().getMode()) {
				case C_MO_CONSULTATION:
					if(getFocusCourantSWT().equals(vue.getGrille())){
						PaTypePaiementSWT paTPaiementSWT = new PaTypePaiementSWT(s2,SWT.NULL);
						SWTPaTypePaiementController swtPaTPaiementController = new SWTPaTypePaiementController(paTPaiementSWT);

						editorCreationId = EditorTypePaiement.ID;
						editorInputCreation = new EditorInputTypePaiement();

						ParamAfficheTPaiement paramAfficheTPaiement = new ParamAfficheTPaiement();
						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
						paramAfficheTPaiement.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheTPaiement.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaTPaiementController;
						parametreEcranCreation = paramAfficheTPaiement;

						paramAfficheAideRecherche.setTypeEntite(TaTPaiement.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_T_PAIEMENT);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_T_PAIEMENT().getText());
						paramAfficheAideRecherche.setControllerAppelant(SWTPaTypePaiementController.this);
						paramAfficheAideRecherche.setModel(swtPaTPaiementController.getModelTPaiement());
						paramAfficheAideRecherche.setTypeObjet(swtPaTPaiementController.getClassModel());
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_T_PAIEMENT);
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

					// Ajout d'une recherche
					paAideController.addRecherche(paAideRechercheController1,
							paramAfficheAideRecherche.getTitreRecherche());

					// Parametrage de l'ecran d'aide principal
					ParamAfficheAideSWT paramAfficheAideSWT = new ParamAfficheAideSWT();
					ParamAfficheAide paramAfficheAide = new ParamAfficheAide();

					// enregistrement pour le retour de l'ecran d'aide
					paAideController.addRetourEcranListener(SWTPaTypePaiementController.this);
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
		String validationContext = "T_PAIEMENT";
		try {
			setActiveAide(true);
			IStatus s = null;
			boolean verrouilleModifCode = false;
			int change=0;
				TaTPaiement u = new TaTPaiement();
				PropertyUtils.setSimpleProperty(u, nomChamp, value);
				if(((SWTTPaiement) selectedTPaiement.getValue()).getIdTPaiement()!=null) {
					u.setIdTPaiement(((SWTTPaiement) selectedTPaiement.getValue()).getIdTPaiement());
				}
				if(nomChamp.equals(Const.C_CODE_T_PAIEMENT)){
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
		try {
			ctrlUnChampsSWT(getFocusCourantSWT());
			EntityTransaction transaction = dao.getEntityManager().getTransaction();
			dao.begin(transaction);
			
			if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
				mapper = new LgrDozerMapper<SWTTPaiement,TaTPaiement>();
				mapper.map((SWTTPaiement) selectedTPaiement.getValue(),taTPaiement);
				
				dao.enregistrerMerge(taTPaiement);


				
			} else if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)) {
				mapper = new LgrDozerMapper<SWTTPaiement,TaTPaiement>();
				mapper.map((SWTTPaiement) selectedTPaiement.getValue(),taTPaiement);

				taTPaiement=dao.enregistrerMerge(taTPaiement);
				modelTPaiement.getListeEntity().add(taTPaiement);
			}
			
			dao.commit(transaction);
			transaction = null;
			actRefresh(); 
			
		} catch (RollbackException e) {	
			logger.error("",e);
			if(e.getCause() instanceof OptimisticLockException)
				MessageDialog.openError(vue.getShell(), "", e.getMessage()+"\n"+e.getCause().getMessage());
		} finally {
			initEtatBouton();
		}
	}

	public void initEtatComposant() {
		try {
				vue.getTfCODE_T_PAIEMENT().setEditable(!isUtilise());
				changeCouleur(vue);
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
		}
	}
	public boolean isUtilise(){
		return ((SWTTPaiement)selectedTPaiement.getValue()).getIdTPaiement()!=null &&
		!dao.recordModifiable(dao.getNomTable(),
				((SWTTPaiement)selectedTPaiement.getValue()).getIdTPaiement());		
	}
	public SWTTPaiement getSwtOldTPaiement() {
		return swtOldTPaiement;
	}

	public void setSwtOldTPaiement(SWTTPaiement swtOldUnite) {
		this.swtOldTPaiement = swtOldUnite;
	}

	public void setSwtOldTPaiement() {
		if (selectedTPaiement.getValue() != null)
			this.swtOldTPaiement = SWTTPaiement.copy((SWTTPaiement) selectedTPaiement.getValue());
		else {
			if(tableViewer.selectionGrille(0)){
				this.swtOldTPaiement = SWTTPaiement.copy((SWTTPaiement) selectedTPaiement.getValue());
				tableViewer.setSelection(new StructuredSelection(
						(SWTTPaiement) selectedTPaiement.getValue()), true);
			}
		}
	}

	public void setVue(PaTypePaiementSWT vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();

		mapComposantDecoratedField.put(vue.getTfCODE_T_PAIEMENT(), vue.getFieldCODE_T_PAIEMENT());
		mapComposantDecoratedField.put(vue.getTfLIB_T_PAIEMENT(), vue.getFieldLIB_T_PAIEMENT());
		mapComposantDecoratedField.put(vue.getTfCOMPTE(), vue.getFieldCOMPTE());
		mapComposantDecoratedField.put(vue.getTfFIN_MOIS_C_PAIEMENT(), vue
				.getFieldFIN_MOIS_C_PAIEMENT());
		mapComposantDecoratedField.put(vue.getTfREPORT_C_PAIEMENT(), vue
				.getFieldREPORT_C_PAIEMENT());
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
		writableList = new WritableList(realm, modelTPaiement.remplirListe(), classModel);
		tableViewer.setInput(writableList);
		tableViewer.refresh();

		//repositionnement sur la valeur courante
		int idActuel = 0;
		if (taTPaiement!=null) { //enregistrement en cours de modification/insertion
			idActuel = taTPaiement.getIdTPaiement();
		} else if(selectedTPaiement!=null && (SWTTPaiement) selectedTPaiement.getValue()!=null) {
			idActuel = ((SWTTPaiement) selectedTPaiement.getValue()).getIdTPaiement();
		}
		
		if(idActuel!=0) {
			tableViewer.setSelection(new StructuredSelection(modelTPaiement.recherche(Const.C_ID_T_PAIEMENT, idActuel)));
		}else
			tableViewer.selectionGrille(0);
		
	}

	public ModelGeneralObjet<SWTTPaiement,TaTPaiementDAO,TaTPaiement> getModelTPaiement() {
		return modelTPaiement;
	}
	
	public TaTPaiementDAO getDao() {
		return dao;
	}

	public TaTPaiement getTaTPaiement() {
		return taTPaiement;
	}

	public void actAfficherTous() throws Exception{
		vue.getGrille().setVisible(true);  
		vue.getBtnTous().setVisible(false);
		vue.getLaTitreGrille().setVisible(true);
		vue.getCompositeForm().setWeights(new int[]{50,100});
		dao.setJPQLQuery(dao.getJPQLQueryInitial());
		modelTPaiement.setJPQLQuery(null);
		modelTPaiement.setListeEntity(null);
		try {
			actRefresh();
		} catch (Exception e1) {
			logger.error("", e1);
		}
	}
}


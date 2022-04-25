package fr.legrain.gestioncapsules.controllers;

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
import fr.legrain.gestCom.Module_GestionCapsules.SWTTitreTransport;
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
import fr.legrain.gestioncapsules.dao.TaTitreTransport;
import fr.legrain.gestioncapsules.dao.TaTitreTransportDAO;
import fr.legrain.gestioncapsules.ecrans.MessagesEcran;
import fr.legrain.gestioncapsules.ecrans.PaTitreTransport;
import fr.legrain.gestioncapsules.ecrans.ParamAfficheTitreTansport;
import fr.legrain.gestioncapsules.editors.EditorInputTitreTransport;
import fr.legrain.gestioncapsules.editors.EditorTitreTransport;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.InfosVerifSaisie;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
import fr.legrain.lib.data.VerrouInterface;
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

public class SWTPaTitreTransport extends JPABaseControllerSWTStandard
		implements RetourEcranListener {

	static Logger logger = Logger.getLogger(SWTPaTitreTransport.class.getName());
	private PaTitreTransport vue = null;
	private TaTitreTransportDAO dao = null;//new TaTNoteDAO();

	private Object ecranAppelant = null;
	private SWTTitreTransport swtTitreTransport;
	private SWTTitreTransport swtOldTitreTransport;
	private Realm realm;
	private DataBindingContext dbc;

	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private IObservableValue selectedTitreTransport;
	private String[] listeChamp;
	private String nomClassController = this.getClass().getSimpleName();
	private MapChangeListener changeListener = new MapChangeListener();
	private Class classModel = SWTTitreTransport.class;
	private ModelGeneralObjet<SWTTitreTransport,TaTitreTransportDAO,TaTitreTransport> modelTitreTransport = null;//new ModelGeneralObjet<SWTTNote,TaTNoteDAO,TaTNote>(dao,classModel);
	
	private LgrDozerMapper<SWTTitreTransport,TaTitreTransport> mapperUIToModel  = new LgrDozerMapper<SWTTitreTransport,TaTitreTransport>();
	private TaTitreTransport taTitreTransport = null;
	
	
	public SWTPaTitreTransport(PaTitreTransport vue) {
		this(vue,null);
	}

	public SWTPaTitreTransport(PaTitreTransport vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaTitreTransportDAO(getEm());
		modelTitreTransport = new ModelGeneralObjet<SWTTitreTransport,TaTitreTransportDAO,TaTitreTransport>(dao,classModel);
		setVue(vue);

		// a faire avant l'initialisation du Binding
		vue.getGrille().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setSwtOldTitreTransport();
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

	/**
	 * Creation des objet pour l'interface, a partir de l'entite principale.<br>
	 * @return
	 */
	
	public void bind(PaTitreTransport PaTypeNoteSWT) {
		try {
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			tableViewer = new LgrTableViewer(PaTypeNoteSWT.getGrille());
			tableViewer.createTableCol(classModel,PaTypeNoteSWT.getGrille(), nomClassController,
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
//			writableList = new WritableList(realm, modelTypeWeb.remplirListe(), classModel);
//			tableViewer.setInput(writableList);
			
			// Set up data binding.
			LgrViewerSupport.bind(tableViewer, 
					new WritableList(modelTitreTransport.remplirListe(), classModel),
					BeanProperties.values(listeChamp)
					);

			selectedTitreTransport = ViewersObservables.observeSingleSelection(tableViewer);

			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			tableViewer.selectionGrille(0);

			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);

			bindingFormMaitreDetail(dbc, realm, selectedTitreTransport,classModel);
			changementDeSelection();
			selectedTitreTransport.addChangeListener(new IChangeListener() {

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
		if(selectedTitreTransport!=null && selectedTitreTransport.getValue()!=null) {
			if(((SWTTitreTransport) selectedTitreTransport.getValue()).getIdTitreTransport()!=null) {
				taTitreTransport = dao.findById(((SWTTitreTransport) selectedTitreTransport.getValue()).getIdTitreTransport());
			}
			//null a tester ailleurs, car peut etre null en cas d'insertion
			fireChangementDeSelection(new ChangementDeSelectionEvent(SWTPaTitreTransport.this));
		}
	}

	public Composite getVue() {
		return vue;
	}

	public ResultAffiche configPanel(ParamAffiche param) {
		if (param != null) {
			//ibTaTable.ouvreDataset();
			if (((ParamAfficheTitreTansport) param).getFocusDefautSWT() != null && !((ParamAfficheTitreTansport) param).getFocusDefautSWT().isDisposed())
				((ParamAfficheTitreTansport) param).getFocusDefautSWT().setFocus();
			else
				((ParamAfficheTitreTansport) param).setFocusDefautSWT(vue.getGrille());
			vue.getLaTitreFormulaire().setText(((ParamAfficheTitreTansport) param).getTitreFormulaire());
			vue.getLaTitreGrille().setText(((ParamAfficheTitreTansport) param).getTitreGrille());
			vue.getLaTitreFenetre().setText(((ParamAfficheTitreTansport) param).getSousTitre());

			if (param.getEcranAppelant() != null) {
				ecranAppelant = param.getEcranAppelant();
			}
			Map<String,String[]> map = dao.getParamWhereSQL();
			if(param.getIdFiche()!=null &&  !param.getIdFiche().equals("")) {
				if(map==null) map = new HashMap<String,String[]>();
				map.clear();
				map.put(Const.C_ID_TITRE_TRANSPORT,new String[]{"=",param.getIdFiche()});
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
			setSwtOldTitreTransport();

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
		
		mapInfosVerifSaisie.put(vue.getTfCodeTitreTransport(), new InfosVerifSaisie(new TaTitreTransport(),Const.C_CODE_TITRE_TRANSPORT,null));

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

		vue.getTfCodeTitreTransport().setToolTipText(Const.C_CODE_TITRE_TRANSPORT);


		mapComposantChamps.put(vue.getTfCodeTitreTransport(), Const.C_CODE_TITRE_TRANSPORT);
		mapComposantChamps.put(vue.getTfLibelleTitreTransport(), Const.C_LIBELLE_TITRE_TRANSPORT);
		mapComposantChamps.put(vue.getTfQteMinTitreTransport(), Const.C_QTE_MIN_TITRE_TRANSPORT);

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
				.getTfCodeTitreTransport());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue
				.getTfCodeTitreTransport());
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

	public SWTPaTitreTransport getThis() {
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
					.getString("TitreTransport.Message.Enregistrer"))) {

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
				setListeEntity(getModelTitreTransport().remplirListe());
				dao.initValeurIdTable(taTitreTransport);
				fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
						dao.getChampIdEntite(), dao.getValeurIdTable(),
						selectedTitreTransport.getValue())));

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
		try {
			if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
				VerrouInterface.setVerrouille(true);
				setSwtOldTitreTransport();
				swtTitreTransport = new SWTTitreTransport();
				taTitreTransport = new TaTitreTransport();
				dao.inserer(taTitreTransport);
				modelTitreTransport.getListeObjet().add(swtTitreTransport);
				writableList = new WritableList(realm, modelTitreTransport.getListeObjet(), classModel);
				tableViewer.setInput(writableList);
				tableViewer.refresh();
				tableViewer.setSelection(new StructuredSelection(swtTitreTransport));
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
				setSwtOldTitreTransport();
				taTitreTransport = dao.findById(((SWTTitreTransport) selectedTitreTransport.getValue()).getIdTitreTransport());
			}else{
				if(!setSwtOldTypeNoteRefresh())throw new Exception();
			}			
			dao.modifier(taTitreTransport);
			initEtatBouton();
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		}
	}
	public boolean containsEntity(TaTitreTransport entite){
		if(modelTitreTransport.getListeEntity()!=null){
			for (Object e : modelTitreTransport.getListeEntity()) {
				if(((TaTitreTransport)e).getIdTitreTransport()==
					entite.getIdTitreTransport())return true;
			}
		}
		return false;
	}


	public boolean setSwtOldTypeNoteRefresh() {
		try {	
			if (selectedTitreTransport.getValue()!=null){
				TaTitreTransport taTNoteOld =dao.findById(taTitreTransport.getIdTitreTransport());
				taTNoteOld=dao.refresh(taTNoteOld);
				if(containsEntity(taTitreTransport)) 
					modelTitreTransport.getListeEntity().remove(taTitreTransport);
				if(!taTitreTransport.getVersionObj().equals(taTNoteOld.getVersionObj())){
					taTitreTransport=taTNoteOld;
					if(!containsEntity(taTitreTransport)) 
						modelTitreTransport.getListeEntity().add(taTitreTransport);					
					actRefresh();
					dao.messageNonAutoriseModification();
				}
				taTitreTransport=taTNoteOld;
				if(!containsEntity(taTitreTransport)) 
					modelTitreTransport.getListeEntity().add(taTitreTransport);
				changementDeSelection();
				this.swtOldTitreTransport=SWTTitreTransport.copy((SWTTitreTransport)selectedTitreTransport.getValue());
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
					.getString("TitreTransport.Message.Supprimer"))) {

			dao.begin(transaction);
				TaTitreTransport u = dao.findById(((SWTTitreTransport) selectedTitreTransport.getValue()).getIdTitreTransport());
				dao.supprimer(u);				
			dao.commit(transaction);
			Object suivant=tableViewer.getElementAt(tableViewer.getTable().getSelectionIndex()+1);
			modelTitreTransport.removeEntity(taTitreTransport);
			taTitreTransport=null;

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
						.getString("TitreTransport.Message.Annuler")))) {
					remetTousLesChampsApresAnnulationSWT(dbc);
					if(((SWTTitreTransport) selectedTitreTransport.getValue()).getIdTitreTransport()==null){
					modelTitreTransport.getListeObjet().remove(((SWTTitreTransport) selectedTitreTransport.getValue()));
					writableList = new WritableList(realm, modelTitreTransport.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.selectionGrille(0);
					}
					dao.annuler(taTitreTransport);
					hideDecoratedFields();
				}
				initEtatBouton();
				break;
			case C_MO_EDITION:
				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("TitreTransport.Message.Annuler")))) {
					int rang = modelTitreTransport.getListeObjet().indexOf(selectedTitreTransport.getValue());
					remetTousLesChampsApresAnnulationSWT(dbc);
					modelTitreTransport.getListeObjet().set(rang, swtOldTitreTransport);
					writableList = new WritableList(realm, modelTitreTransport.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.setSelection(new StructuredSelection(swtOldTitreTransport), true);
					dao.annuler(taTitreTransport);
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
//		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
//		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
//
//		String nomChampIdTable =  dao.getChampIdTable();
//
//		FonctionGetInfosXmlAndProperties fonctionGetInfosXmlAndProperties = new FonctionGetInfosXmlAndProperties(mapperUIToModel);
//		fonctionGetInfosXmlAndProperties.cleanValueMapAttributeTable();
//
//		fonctionGetInfosXmlAndProperties.setValueMapAttributeTable(TaTitreTransport.class.getSimpleName()+".head");
//		fonctionGetInfosXmlAndProperties.setValueMapAttributeTable(TaTitreTransport.class.getSimpleName()+".detail");
//		
//		LinkedHashMap<String,AttributElementResport> mapAttributeTablHead = fonctionGetInfosXmlAndProperties.getMapAttributeTablHead(); 
//		LinkedHashMap<String,AttributElementResport> mapAttributeTablDetail = fonctionGetInfosXmlAndProperties.getMapAttributeTablDetail();
//		
//		Collection<TaTitreTransport> collectionTaTNote = modelTitreTransport.getListeEntity();
//		
//		fonctionGetInfosXmlAndProperties.findInfosFileXml(TaTitreTransport.class.getName(),SWTTitreTransport.class.getName(),
//				listeChamp,"mapping");
//		fonctionGetInfosXmlAndProperties.getInfosObjetJPA(taTitreTransport);
//
//		ConstEdition constEdition = new ConstEdition(); 
////		Impression impression = new Impression(constEdition,TaTNote,collectionTaTNote,nomChampIdTable,TaTNote.getIdTWeb());
//		String nomDossier = null;
//
//		int nombreLine = collectionTaTNote.size();
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
//			String folderEditionDynamique = Const.C_RCP_INSTANCE_LOCATION+"/"+Const.C_NOM_PROJET_TMP+"/"+TaTitreTransport.class.getSimpleName();
//			constEdition.makeFolderEditions(folderEditionDynamique);
//			Path pathFileReport = new Path(folderEditionDynamique+"/"+Const.C_NOM_VU_T_NOTE_ARTICLE+".rptdesign");
//			final String pathFileReportDynamic = pathFileReport.toPortableString();
//
//			MakeDynamiqueReport DynamiqueReport = new MakeDynamiqueReport(constEdition.getNameTableEcran(),
//					constEdition.getNameTableBDD(),pathFileReportDynamic,Const.C_NOM_VU_T_NOTE_ARTICLE,
//					ConstEdition.PAGE_ORIENTATION_LANDSCAPE,nomDossier); 
//			DynamiqueReport.setSimpleNameEntity(TaTitreTransport.class.getSimpleName());
//			/**************************************************************/
//			DynamiqueReport.setFonctionGetInfosXml(fonctionGetInfosXmlAndProperties);
//			DynamiqueReport.setNomObjet(TaTitreTransport.class.getSimpleName());
//			/**************************************************************/
//
//			Map<String, AttributElementResport> attribuGridHeader = new LinkedHashMap<String, AttributElementResport>();
//			String nameHeaderTitle = ConstEditionArticle.SOUS_TITLE_EDITION_ARTICLE_FAMILLE;
//			attribuGridHeader.put(nameHeaderTitle, new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
//					ConstEdition.FONT_SIZE_XX_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
//					ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE,""));
//			String nameSousHeaderTitle = ConstEditionArticle.SOUS_TITLE_EDITION_ARTICLE_FAMILLE;
//			attribuGridHeader.put(nameSousHeaderTitle, new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
//					ConstEdition.FONT_SIZE_X_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
//					ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE,ConstEdition.COLOR_GRAY));
//
//			//DynamiqueReport.buildDesignConfig();
//			ConstEdition.CONTENT_COMMENTS = ConstEditionArticle.COMMENTAIRE_EDITION_DEFAUT;
//			DynamiqueReport.initializeBuildDesignReportConfig();
//			DynamiqueReport.makePageMater("1", "1", "1", "1", "100");
//			DynamiqueReport.makeReportHeaderGrid(3,5,100,ConstEdition.UNITS_PERCENTAGE,attribuGridHeader);
////			DynamiqueReport.makeReportTableDB(100,ConstEdition.UNITS_PERCENTAGE,
////					Const.C_NOM_VU_T_TEL,attribuTabHeader,attribuTabDetail,1,1,2,5,"30");
//			DynamiqueReport.biuldTableReport("100", ConstEdition.UNITS_PERCENTAGE, 
//					Const.C_NOM_VU_T_NOTE_ARTICLE,1,1,2,"40", mapAttributeTablHead, mapAttributeTablDetail);
//			DynamiqueReport.savsAsDesignHandle();
////			impression.imprimer(true,pathFileReportDynamic,null,"Type Web",TaTNote.class.getSimpleName(),false);
//			
//			/** 01/03/2010 modifier les editions (zhaolin) **/
//			BaseImpressionEdition impressionEdition = new BaseImpressionEdition(constEdition,taTitreTransport,
//					getEm(),collectionTaTNote,taTitreTransport.getIdTNoteArticle());
//			impressionEdition.impressionEditionTypeEntity(pathFileReportDynamic,"Web");
//		}
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
						PaTitreTransport paTypeNoteSWT = new PaTitreTransport(s2,SWT.NULL);
						SWTPaTitreTransport swtPaTypeNoteController = new SWTPaTitreTransport(paTypeNoteSWT);

						editorCreationId = EditorTitreTransport.ID;
						editorInputCreation = new EditorInputTitreTransport();

						ParamAfficheTitreTansport ParamAfficheTypeNote = new ParamAfficheTitreTansport();
						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
						ParamAfficheTypeNote.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						ParamAfficheTypeNote.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaTypeNoteController;
						parametreEcranCreation = ParamAfficheTypeNote;

						paramAfficheAideRecherche.setTypeEntite(TaTitreTransport.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_T_NOTE_ARTICLE);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCodeTitreTransport().getText());
						paramAfficheAideRecherche.setControllerAppelant(getThis());
						paramAfficheAideRecherche.setModel(swtPaTypeNoteController.getModelTitreTransport());
						paramAfficheAideRecherche.setTypeObjet(swtPaTypeNoteController.getClassModel());
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_T_NOTE_ARTICLE);
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
		String validationContext = "TITRE_TRANSPORT";
		try {
			IStatus s = null;
			boolean verrouilleModifCode = false;
			int change=0;
				TaTitreTransport u = new TaTitreTransport();
				PropertyUtils.setSimpleProperty(u, nomChamp, value);
				if(((SWTTitreTransport) selectedTitreTransport.getValue()).getIdTitreTransport()!=null) {
					u.setIdTitreTransport(((SWTTitreTransport) selectedTitreTransport.getValue()).getIdTitreTransport());
				}
				if(nomChamp.equals(Const.C_CODE_TITRE_TRANSPORT)){
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
			ctrlTousLesChampsAvantEnregistrement();
			dao.begin(transaction);
			if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)||
					(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)) {
				LgrDozerMapper<SWTTitreTransport,TaTitreTransport> mapper = new LgrDozerMapper<SWTTitreTransport,TaTitreTransport>();
				mapper.map((SWTTitreTransport) selectedTitreTransport.getValue(),taTitreTransport);
				
				taTitreTransport=dao.enregistrerMerge(taTitreTransport);
//				if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
//					modelTypeWeb.getListeEntity().add(TaTNote);
			} 
			dao.commit(transaction);
			modelTitreTransport.addEntity(taTitreTransport);
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
			vue.getTfCodeTitreTransport().setEditable(!isUtilise());
			//vue.getTfLABELLE_T_WEB().setEditable(!isUtilise());
			changeCouleur(vue);
	    } catch (Exception e) {
		vue.getLaMessage().setText(e.getMessage());
	    }
	}

	public boolean isUtilise(){
		return (((SWTTitreTransport)selectedTitreTransport.getValue()).getIdTitreTransport()!=null &&
		!dao.recordModifiable(dao.getNomTable(),
				((SWTTitreTransport)selectedTitreTransport.getValue()).getIdTitreTransport()))||
				!dao.autoriseModification(taTitreTransport);		
	}
	
	public SWTTitreTransport getSwtOldTitreTransport() {
		return swtOldTitreTransport;
	}

	public void setSwtOldTitreTransport(SWTTitreTransport swtOldTypeNote) {
		this.swtOldTitreTransport = swtOldTypeNote;
	}
	

	public void setSwtOldTitreTransport() {
		if (selectedTitreTransport.getValue() != null)
			this.swtOldTitreTransport = SWTTitreTransport.copy((SWTTitreTransport) selectedTitreTransport.getValue());
		else {
			if(tableViewer.selectionGrille(0)){
				this.swtOldTitreTransport = SWTTitreTransport.copy((SWTTitreTransport) selectedTitreTransport.getValue());
				tableViewer.setSelection(new StructuredSelection(
						(SWTTitreTransport) selectedTitreTransport.getValue()), true);
			}
		}
	}
	public void setVue(PaTitreTransport vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();
		mapComposantDecoratedField.put(vue.getTfCodeTitreTransport(), vue.getFieldCodeTitreTransport());
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
		TaTitreTransport u = taTitreTransport;
		if (u!=null && u.getIdTitreTransport()==0)
			u=dao.findById(u.getIdTitreTransport());
		writableList = new WritableList(realm, modelTitreTransport.remplirListe(), classModel);
		tableViewer.setInput(writableList);
		tableViewer.refresh();
		tableViewer.selectionGrille(
				tableViewer.selectionGrille(selectedTitreTransport.getValue()));
		
		if(u != null) {
			Iterator<SWTTitreTransport> ite = modelTitreTransport.getListeObjet().iterator();
			SWTTitreTransport tmp = null;
			int i = 0;
			boolean trouve = false;
			while (ite.hasNext() && !trouve) {
				tmp = ite.next();
				if(tmp.getIdTitreTransport()==u.getIdTitreTransport()) {
					tableViewer.setSelection(new StructuredSelection(tmp));
					trouve = true;
				}
				i++;
			}
		}	
	}

	public ModelGeneralObjet<SWTTitreTransport,TaTitreTransportDAO,TaTitreTransport> getModelTitreTransport() {
		return modelTitreTransport;
	}

	public TaTitreTransportDAO getDao() {
		return dao;
	}

	public void setDao(TaTitreTransportDAO dao) {
		this.dao = dao;
	}

	public void actAfficherTous() throws Exception{
		vue.getGrille().setVisible(true);  
		vue.getBtnTous().setVisible(false);
		vue.getLaTitreGrille().setVisible(true);
		vue.getCompositeForm().setWeights(new int[]{50,100});
		dao.setJPQLQuery(dao.getJPQLQueryInitial());
		modelTitreTransport.setJPQLQuery(null);
		modelTitreTransport.setListeEntity(null);
		try {
			actRefresh();
		} catch (Exception e1) {
			logger.error("", e1);
		}
	}
}

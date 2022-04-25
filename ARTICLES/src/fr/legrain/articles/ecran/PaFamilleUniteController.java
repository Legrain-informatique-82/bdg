package fr.legrain.articles.ecran;

import java.lang.reflect.InvocationTargetException;
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
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
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

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaFamille;
import fr.legrain.articles.dao.TaFamilleUnite;
import fr.legrain.articles.dao.TaFamilleUniteDAO;
import fr.legrain.articles.divers.Impression;
import fr.legrain.articles.editor.EditorFamille;
import fr.legrain.articles.editor.EditorInputFamille;
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
import fr.legrain.lib.gui.DestroyEvent;
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
import fr.legrain.tiers.dao.TaTiers;


public class PaFamilleUniteController extends JPABaseControllerSWTStandard
implements RetourEcranListener {

	static Logger logger = Logger.getLogger(PaFamilleUniteController.class.getName());
	private PaFamilleUnite vue = null;
	private TaFamilleUniteDAO dao = null;//new TaFamilleUniteDAO(getEm());

	private Object ecranAppelant = null;
	private SWTFamille swtFamille;
	private SWTFamille swtOldFamille;
	private Realm realm;
	private DataBindingContext dbc;

	private Class classModel = SWTFamille.class;
	private ModelGeneralObjet<SWTFamille,TaFamilleUniteDAO,TaFamilleUnite> modelFamille = null;//new ModelGeneralObjet<SWTFamille,TaFamilleUniteDAO,TaFamilleUnite>(dao,classModel);
	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private IObservableValue selectedFamille;
	private String[] listeChamp;
	private String nomClassController = this.getClass().getSimpleName();

	private TaFamilleUnite taFamilleUnite = null;

	private MapChangeListener changeListener = new MapChangeListener();

	private LgrDozerMapper<SWTFamille,TaFamilleUnite> mapperUIToModel = new LgrDozerMapper<SWTFamille,TaFamilleUnite>();
	
	public PaFamilleUniteController(PaFamilleUnite vue) {
		this(vue,null);
	}
	
	public PaFamilleUniteController(PaFamilleUnite vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaFamilleUniteDAO(getEm());
		modelFamille = new ModelGeneralObjet<SWTFamille,TaFamilleUniteDAO,TaFamilleUnite>(dao,classModel);
		setVue(vue);

		// a faire avant l'initialisation du Binding
		vue.getGrille().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setSwtOldFamille();
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
			Menu[] tabPopups = new Menu[] { popupMenuFormulaire,
					popupMenuGrille };
			this.initPopupAndButtons(mapActions, tabPopups);
			vue.getPaCorpsFormulaire().setMenu(popupMenuFormulaire);
			vue.getPaGrille().setMenu(popupMenuGrille);

			initEtatBouton();

			addDestroyListener(this);
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaArticlesController", e);
		}
	}

	public void bind(PaFamilleUnite paFamilleSWT) {
		try {
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			tableViewer = new LgrTableViewer(paFamilleSWT.getGrille());
			tableViewer.createTableCol(classModel,paFamilleSWT.getGrille(), nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE,0);
			listeChamp = tableViewer.setListeChampGrille(nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			
			// Set up data binding.
			LgrViewerSupport.bind(tableViewer, 
					new WritableList(modelFamille.remplirListe(), classModel),
					BeanProperties.values(listeChamp)
					);

			selectedFamille = ViewersObservables.observeSingleSelection(tableViewer);

			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			tableViewer.selectionGrille(0);

			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);

			bindingFormMaitreDetail(dbc, realm, selectedFamille,classModel);
			changementDeSelection();
			selectedFamille.addChangeListener(new IChangeListener() {

				public void handleChange(ChangeEvent event) {
					changementDeSelection();
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
				vue.getLaTitreFormulaire().setText(ParamAfficheFamille.C_TITRE_FORMULAIRE);
			}

			if(param.getTitreGrille()!=null){
				vue.getLaTitreGrille().setText(param.getTitreGrille());
			} else {
				vue.getLaTitreGrille().setText(ParamAfficheFamille.C_TITRE_GRILLE);
			}

			if(param.getSousTitre()!=null){
				vue.getLaTitreFenetre().setText(param.getSousTitre());
			} else {
				vue.getLaTitreFenetre().setText(ParamAfficheFamille.C_SOUS_TITRE);
			}

			if (param.getEcranAppelant() != null) {
				ecranAppelant = param.getEcranAppelant();
			}
			Map<String,String[]> map = dao.getParamWhereSQL();
			if(param.getIdFiche()!=null &&  !param.getIdFiche().equals("")) {
				if(map==null) map = new HashMap<String,String[]>();
				map.clear();
				map.put("idFamille",new String[]{"=",param.getIdFiche()});
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
			setSwtOldFamille();

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

		vue.getTfCODE_FAMILLE().setToolTipText(Const.C_CODE_FAMILLE);
		vue.getTfLIBC_FAMILLE().setToolTipText(Const.C_LIBC_FAMILLE);
		vue.getTfLIBL_FAMILLE().setToolTipText(Const.C_LIBL_FAMILLE);

		mapComposantChamps.put(vue.getTfCODE_FAMILLE(), Const.C_CODE_FAMILLE);
		mapComposantChamps.put(vue.getTfLIBC_FAMILLE(), Const.C_LIBC_FAMILLE);
		mapComposantChamps.put(vue.getTfLIBL_FAMILLE(), Const.C_LIBL_FAMILLE);

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
				.getTfCODE_FAMILLE());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue
				.getTfCODE_FAMILLE());
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

	public PaFamilleUniteController getThis() {
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
					.getString("Famille.Message.Enregistrer"))) {

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
					dao.annuler(taFamilleUnite);
				} catch (Exception e) {
					throw new ExceptLgr();
				}
			if (ecranAppelant instanceof SWTPaAideControllerSWT) {
				setListeEntity(getModelFamille().remplirListe());
				dao.initValeurIdTable(taFamilleUnite);
				fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
						dao.getChampIdEntite(), dao.getValeurIdTable(),
						selectedFamille.getValue())));

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
			setSwtOldFamille();
			swtFamille = new SWTFamille();
			taFamilleUnite = new TaFamilleUnite();
			dao.inserer(taFamilleUnite);
			modelFamille.getListeObjet().add(swtFamille);
			writableList = new WritableList(realm, modelFamille.getListeObjet(), classModel);
			tableViewer.setInput(writableList);
			tableViewer.refresh();
			tableViewer.setSelection(new StructuredSelection(swtFamille));
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
				setSwtOldFamille();
				taFamilleUnite = dao.findById(((SWTFamille) selectedFamille.getValue()).getIdFamille());
			}else{
				if(!setSwtOldFamilleRefresh())throw new Exception();
			}
			dao.modifier(taFamilleUnite);
			initEtatBouton();
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		}
	}
	public boolean containsEntity(TaFamilleUnite entite){
		if(modelFamille.getListeEntity()!=null){
			for (Object e : modelFamille.getListeEntity()) {
				if(((TaFamilleUnite)e).getIdFamille()==
					entite.getIdFamille())return true;
			}
		}
		return false;
	}


	public boolean setSwtOldFamilleRefresh() {
		try {	
			if (selectedFamille.getValue()!=null){
				TaFamilleUnite taOld =dao.findById(taFamilleUnite.getIdFamille());
				taOld=dao.refresh(taOld);
				if(containsEntity(taFamilleUnite)) 
					modelFamille.getListeEntity().remove(taFamilleUnite);
				if(!taFamilleUnite.getVersionObj().equals(taOld.getVersionObj())){
					taFamilleUnite=taOld;
					if(!containsEntity(taFamilleUnite)) 
						modelFamille.getListeEntity().add(taFamilleUnite);					
					actRefresh();
					dao.messageNonAutoriseModification();
				}
				taFamilleUnite=taOld;
				if(!containsEntity(taFamilleUnite)) 
					modelFamille.getListeEntity().add(taFamilleUnite);
				changementDeSelection();
				this.swtOldFamille=SWTFamille.copy((SWTFamille)selectedFamille.getValue());
			}
			return true;
		} catch (Exception e) {
			return false;
		}		
	}
	private void changementDeSelection() {
		if(selectedFamille!=null && selectedFamille.getValue()!=null) {
			if(((SWTFamille) selectedFamille.getValue()).getIdFamille()!=null) {
				taFamilleUnite = dao.findById(((SWTFamille) selectedFamille.getValue()).getIdFamille());
			}
			//null a tester ailleurs, car peut etre null en cas d'insertion
			fireChangementDeSelection(new ChangementDeSelectionEvent(PaFamilleUniteController.this));
		}
	}

//	public void setSwtOldFamilleRefresh() {
//		if (selectedFamille.getValue()!=null){
//			if(LgrMess.isDOSSIER_EN_RESEAU())
//				dao.refresh(dao.findById(((SWTFamille) selectedFamille.getValue()).getIdFamille()));
//			taFamilleUnite=dao.findById(((SWTFamille) selectedFamille.getValue()).getIdFamille());
//				try {
//					if(LgrMess.isDOSSIER_EN_RESEAU())actRefresh();
//				} catch (Exception e) {
//					logger.error("",e);
//				}
//			this.swtOldFamille=SWTFamille.copy((SWTFamille)selectedFamille.getValue());
//		}
//	}

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
						.getString("Tva.Message.Supprimer"))) {
					dao.getEntityManager().getTransaction().begin();
					TaFamilleUnite u = dao.findById(((SWTFamille) selectedFamille.getValue()).getIdFamille());
					dao.supprimer(u);
					Object suivant=tableViewer.getElementAt(tableViewer.getTable().getSelectionIndex()+1);
					modelFamille.removeEntity(u);
					taFamilleUnite=null;
					dao.getEntityManager().getTransaction().commit();
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
						.getString("Famille.Message.Annuler"))) {
					if(((SWTFamille) selectedFamille.getValue()).getIdFamille()==null){
						modelFamille.getListeObjet().remove(
								((SWTFamille) selectedFamille.getValue()));
						writableList = new WritableList(realm, modelFamille
								.getListeObjet(), classModel);
						tableViewer.setInput(writableList);
						tableViewer.refresh();
						tableViewer.selectionGrille(0);
					}
					dao.annuler(taFamilleUnite);
					hideDecoratedFields();
				}
				initEtatBouton();
				break;
			case C_MO_EDITION:
				if (MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("Famille.Message.Annuler"))) {
					int rang = modelFamille.getListeObjet().indexOf(selectedFamille.getValue());
					modelFamille.getListeObjet().set(rang, swtOldFamille);
					writableList = new WritableList(realm, modelFamille.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.setSelection(new StructuredSelection(swtOldFamille), true);
					dao.annuler(taFamilleUnite);
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

		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();

		String nomChampIdTable =  dao.getChampIdTable();
		
		FonctionGetInfosXmlAndProperties fonctionGetInfosXmlAndProperties = new FonctionGetInfosXmlAndProperties(mapperUIToModel);
		fonctionGetInfosXmlAndProperties.cleanValueMapAttributeTable();

		fonctionGetInfosXmlAndProperties.setValueMapAttributeTable(TaFamilleUnite.class.getSimpleName()+".head");
		fonctionGetInfosXmlAndProperties.setValueMapAttributeTable(TaFamilleUnite.class.getSimpleName()+".detail");
		

		LinkedHashMap<String,AttributElementResport> mapAttributeTablHead = fonctionGetInfosXmlAndProperties.getMapAttributeTablHead(); 
		LinkedHashMap<String,AttributElementResport> mapAttributeTablDetail = fonctionGetInfosXmlAndProperties.getMapAttributeTablDetail();
		
		Collection<TaFamilleUnite> collectionTaFamille = modelFamille.getListeEntity();
		
		fonctionGetInfosXmlAndProperties.findInfosFileXml(TaFamilleUnite.class.getName(),SWTFamille.class.getName(),
				listeChamp,"mapping");
		fonctionGetInfosXmlAndProperties.getInfosObjetJPA(taFamilleUnite);
		
		ConstEdition constEdition = new ConstEdition(); 
//		Impression impression = new Impression(constEdition,taFamilleUnite,collectionTaFamille,nomChampIdTable,taFamilleUnite.getIdFamille());
		
		String nomDossier = null;
		 
		int nombreLine = collectionTaFamille.size();


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
								Const.C_NOM_PROJET_TMP+ConstEdition.SEPARATOR+TaFamilleUnite.class.getSimpleName();
			constEdition.makeFolderEditions(folderEditionDynamique);
			Path pathFileReport = new Path(folderEditionDynamique+ConstEdition.SEPARATOR+Const.C_NOM_VU_FAMILLE+".rptdesign");
//			Path pathFileReport = new Path(Const.C_RCP_INSTANCE_LOCATION+"/"+Const.C_NOM_PROJET_TMP+"/"
//					+Const.C_NOM_VU_FAMILLE+".rptdesign");
			final String pathFileReportDynamic = pathFileReport.toPortableString();
			
			MakeDynamiqueReport DynamiqueReport = new MakeDynamiqueReport(constEdition.getNameTableEcran(),
					constEdition.getNameTableBDD(),pathFileReportDynamic,Const.C_NOM_VU_FAMILLE,
					ConstEdition.PAGE_ORIENTATION_LANDSCAPE,nomDossier); 
			
			/**************************************************************/
			DynamiqueReport.setFonctionGetInfosXml(fonctionGetInfosXmlAndProperties);
			DynamiqueReport.setNomObjet(TaFamilleUnite.class.getSimpleName());
			/**************************************************************/
			

			Map<String, AttributElementResport> attribuGridHeader = new LinkedHashMap<String, AttributElementResport>();
			String nameHeaderTitle = ConstEditionArticle.TITLE_EDITION_ARTICLE_FAMILLE;
			attribuGridHeader.put(nameHeaderTitle, new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
					ConstEdition.FONT_SIZE_XX_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
					ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
					,""));
			String nameSousHeaderTitle = ConstEditionArticle.SOUS_TITLE_EDITION_ARTICLE_FAMILLE;
			attribuGridHeader.put(nameSousHeaderTitle, new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
					ConstEdition.FONT_SIZE_X_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
					ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
					,ConstEdition.COLOR_GRAY));

			//DynamiqueReport.buildDesignConfig();

			ConstEdition.CONTENT_COMMENTS = ConstEditionArticle.COMMENTAIRE_EDITION_DEFAUT;

			DynamiqueReport.setSimpleNameEntity(TaFamilleUnite.class.getSimpleName());
			
			DynamiqueReport.initializeBuildDesignReportConfig();
			DynamiqueReport.makePageMater("1", "1", "1", "1", "100");
			DynamiqueReport.makeReportHeaderGrid(3,5,100,ConstEdition.UNITS_PERCENTAGE,attribuGridHeader);
			DynamiqueReport.biuldTableReport("100", ConstEdition.UNITS_PERCENTAGE, 
					Const.C_NOM_VU_FAMILLE,1,1,2,"40", mapAttributeTablHead, mapAttributeTablDetail);
			DynamiqueReport.savsAsDesignHandle();
			
//			impression.imprimer(true,pathFileReportDynamic,null,"Famille Unite",TaFamilleUnite.class.getSimpleName(),false);
			/** 01/03/2010 modifier les editions (zhaolin) **/
			BaseImpressionEdition impressionEdition = new BaseImpressionEdition(constEdition,taFamilleUnite,
					getEm(),collectionTaFamille,taFamilleUnite.getIdFamille());
			
			impressionEdition.impressionEditionTypeEntity(pathFileReportDynamic,"Famille Unité");
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
				//				paramAfficheAideRecherche.setDb(getThis().getIbTaTable().getFIBBase());
				paramAfficheAideRecherche.setMessage(message);
				// Cration de l'ecran d'aide principal
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
						PaFamilleUnite paFamilleSWT = new PaFamilleUnite(s2,SWT.NULL);
						PaFamilleUniteController swtPaFamilleController = new PaFamilleUniteController(paFamilleSWT);

						editorCreationId = EditorFamille.ID;
						editorInputCreation = new EditorInputFamille();

						ParamAfficheFamille paramAfficheFamille = new ParamAfficheFamille();
						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
						paramAfficheFamille.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheFamille.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaFamilleController;
						parametreEcranCreation = paramAfficheFamille;

						paramAfficheAideRecherche.setTypeEntite(TaFamilleUnite.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_FAMILLE);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_FAMILLE().getText());
						paramAfficheAideRecherche.setControllerAppelant(getThis());
						paramAfficheAideRecherche.setModel(swtPaFamilleController.getModelFamille());
						paramAfficheAideRecherche.setTypeObjet(swtPaFamilleController.getClassModel());
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_FAMILLE);
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
		String validationContext = "FAMILLE_UNITE";
		try {
			IStatus s = null;
			boolean verrouilleModifCode = false;
			int change=0;
			TaFamilleUnite u = new TaFamilleUnite();
			PropertyUtils.setSimpleProperty(u, nomChamp, value);
			if(((SWTFamille) selectedFamille.getValue()).getIdFamille()!=null) {
				u.setIdFamille(((SWTFamille) selectedFamille.getValue()).getIdFamille());
			}
			
			if(nomChamp.equals(Const.C_CODE_FAMILLE)){
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
			//TaFamille u = null;
			if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
				//LgrDozerMapper<SWTFamille,TaFamille> mapper = new LgrDozerMapper<SWTFamille,TaFamille>();
				mapperUIToModel.map((SWTFamille) selectedFamille.getValue(),taFamilleUnite);

				dao.enregistrerMerge(taFamilleUnite);

			} else if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)) {
				//LgrDozerMapper<SWTFamille,TaFamille> mapper = new LgrDozerMapper<SWTFamille,TaFamille>();
				mapperUIToModel.map((SWTFamille) selectedFamille.getValue(),taFamilleUnite);

				taFamilleUnite=dao.enregistrerMerge(taFamilleUnite);
				//modelFamille.getListeEntity().add(taFamilleUnite);
			}
			//dao.getEntityManager().flush();
			dao.commit(transaction);
			modelFamille.addEntity(taFamilleUnite);
			transaction = null;			
			actRefresh(); //deja present

		} catch(Exception e) {
			if(transaction!=null && transaction.isActive()) {
				transaction.rollback();
			}
			throw e;
		} finally {
			initEtatBouton();
		}
	}

	public TaFamilleUniteDAO getDao() {
		return dao;
	}

	public void initEtatComposant() {
		try {			
			vue.getTfCODE_FAMILLE().setEditable(!isUtilise());
			changeCouleur(vue);
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
		}

	}
	public boolean isUtilise(){
		return (((SWTFamille)selectedFamille.getValue()).getIdFamille()!=null && 
		!dao.recordModifiable(dao.getNomTable(),
				((SWTFamille)selectedFamille.getValue()).getIdFamille()))
				||!dao.autoriseModification(taFamilleUnite);		
	}
	public SWTFamille getSwtOldFamille() {
		return swtOldFamille;
	}

	public void setSwtOldFamille(SWTFamille swtOldFamille) {
		this.swtOldFamille = swtOldFamille;
	}

	public void setSwtOldFamille() {
		if (selectedFamille.getValue() != null){
			this.swtOldFamille = SWTFamille.copy((SWTFamille) selectedFamille.getValue());
		}
		else {
			if(tableViewer.selectionGrille(0)){
				this.swtOldFamille = SWTFamille.copy((SWTFamille) selectedFamille.getValue());
				tableViewer.setSelection(new StructuredSelection(
						(SWTFamille) selectedFamille.getValue()), true);
			}}
	}

	public void setVue(PaFamilleUnite vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();

		mapComposantDecoratedField.put(vue.getTfCODE_FAMILLE(), vue.getFieldCODE_FAMILLE());
		mapComposantDecoratedField.put(vue.getTfLIBC_FAMILLE(), vue.getFieldLIBC_FAMILLE());
		mapComposantDecoratedField.put(vue.getTfLIBL_FAMILLE(), vue.getFieldLIBL_FAMILLE());
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
		if (taFamilleUnite!=null) { //enregistrement en cours de modification/insertion
			idActuel = taFamilleUnite.getIdFamille();
		} else if(selectedFamille!=null && (SWTFamille) selectedFamille.getValue()!=null) {
			idActuel = ((SWTFamille) selectedFamille.getValue()).getIdFamille();
		}
		if (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION) == 0) {
			//rafraichissement des valeurs dans la grille
			writableList = new WritableList(realm, modelFamille.remplirListe(), classModel);
			tableViewer.setInput(writableList);
		} else {
			if (taFamilleUnite!=null && selectedFamille!=null && (SWTArticle) selectedFamille.getValue()!=null) {
				new LgrDozerMapper<TaFamilleUnite, SWTFamille>().
					map(taFamilleUnite, (SWTFamille) selectedFamille.getValue());
			}
		}

		if(idActuel!=0) {
			tableViewer.setSelection(new StructuredSelection(modelFamille.recherche(Const.C_ID_FAMILLE
					, idActuel)));
		}else
			tableViewer.selectionGrille(0);				
	}

	public ModelGeneralObjet<SWTFamille,TaFamilleUniteDAO,TaFamilleUnite> getModelFamille() {
		return modelFamille;
	}


	public void destroy(DestroyEvent evt) {

		//		tableViewer.destroy();
		//		ibTaTableStandard.destroy();
		//		ibTaTable.destroy();
		//		vue.getGrille().dispose();
		//		mapComposantChamps.clear();
		//		mapComposantChamps=null;
		//		mapComposantDecoratedField.clear();
		//		mapComposantDecoratedField=null;
		//		listeComposantFocusable.clear();
		//		listeComposantFocusable=null;
		//		mapInitFocus.clear();
		//		mapInitFocus=null;
		//		mapActions.clear();
		//		mapActions=null;
		//		ecranAppelant=null;
		//		getVue().dispose();
		//		modelFamille.destroy();
		//		swtFamille=null;
		//		swtOldFamille=null;
		//		realm=null;
		//		dbc.dispose();
		//		writableList.dispose();
		//		selectedFamille.dispose();		
	}	

	@Override
	protected void finalize() throws Throwable {
		destroy(new DestroyEvent(this));
		super.finalize();
	}

	public TaFamilleUnite getTaFamilleUnite() {
		return taFamilleUnite;
	}

	public void actAfficherTous() throws Exception{
		vue.getGrille().setVisible(true);  
		vue.getBtnTous().setVisible(false);
		vue.getLaTitreGrille().setVisible(true);
		vue.getCompositeForm().setWeights(new int[]{50,100});
		dao.setJPQLQuery(dao.getJPQLQueryInitial());
		modelFamille.setJPQLQuery(null);
		modelFamille.setListeEntity(null);
		try {
			actRefresh();
		} catch (Exception e1) {
			logger.error("", e1);
		}
	}
}

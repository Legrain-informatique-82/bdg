package fr.legrain.updater.ecran;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.update.search.BackLevelFilter;
import org.eclipse.update.search.EnvironmentFilter;
import org.eclipse.update.search.UpdateSearchRequest;
import org.eclipse.update.search.UpdateSearchScope;
import org.eclipse.update.ui.UpdateJob;
import org.eclipse.update.ui.UpdateManagerUI;

import com.borland.dx.dataset.DataRow;

import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Updater.SWTMailMaj;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideRechercheSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideRechercheControllerSWT;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.LibConversion;
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
import fr.legrain.updater.UpdaterPlugin;
import fr.legrain.updater.actions.LgrUpdate;
import fr.legrain.updater.dao.TaMailMaj;
import fr.legrain.updater.dao.TaMailMajDAO;
//import fr.legrain.updater.p2.InstallNewSoftwareHandler;

public class SWTPaMailUpdaterController extends JPABaseControllerSWTStandard
implements RetourEcranListener {

	static Logger logger = Logger.getLogger(SWTPaMailUpdaterController.class.getName());
	private PaMailUpdaterSWT vue = null;
	private TaMailMajDAO dao = null;//new TaMailMajDAO(); 
	private Object ecranAppelant = null;
	private SWTMailMaj swtMailMaj;
	private SWTMailMaj swtOldMailMaj;
	private Realm realm;
	private DataBindingContext dbc;


	private Class classModel = SWTMailMaj.class;
	private ModelGeneralObjet<SWTMailMaj,TaMailMajDAO,TaMailMaj> modelMailMaj = null;//new ModelGeneralObjet<SWTMailMaj,TaMailMajDAO,TaMailMaj>(dao,classModel);

	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private IObservableValue selectedMailMaj;
	private String[] listeChamp;
	private String nomClassController = this.getClass().getSimpleName();
	private MapChangeListener changeListener = new MapChangeListener();

	private ActionMiseAJour actionMiseAJour = new ActionMiseAJour("Mise à jour (Alt+U)");
	private ActionRechercheMailMiseAJour actionRechercheMailMiseAJour = new ActionRechercheMailMiseAJour("Recherche Mise à jour (Alt+R)");

	public static final String C_COMMAND_MISE_A_JOUR_ID = "fr.legrain.gestionCommerciale.updater.maj";
	public static final String C_COMMAND_RECHERCHE_MAIL_MISE_A_JOUR_ID = "fr.legrain.gestionCommerciale.updater.recherche";

	private HandlerMiseAJour handlerMiseAJour = new HandlerMiseAJour();
	private HandlerRechercheMailMiseAJour handlerRechercheMailMiseAJour = new HandlerRechercheMailMiseAJour();

	private LgrDozerMapper<SWTMailMaj,TaMailMaj> mapperUIToModel = new LgrDozerMapper<SWTMailMaj,TaMailMaj>();
	private LgrDozerMapper<TaMailMaj,SWTMailMaj> mapperModelToUI = new LgrDozerMapper<TaMailMaj,SWTMailMaj>();
	private TaMailMaj taMailMaj = null;
	
	public SWTPaMailUpdaterController(PaMailUpdaterSWT vue) {
		this(vue,null);
	}

	public SWTPaMailUpdaterController(PaMailUpdaterSWT vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaMailMajDAO(getEm()); 
		modelMailMaj = new ModelGeneralObjet<SWTMailMaj,TaMailMajDAO,TaMailMaj>(dao,classModel);
		setVue(vue);

		// a faire avant l'initialisation du Binding
		vue.getGrille().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setSwtOldMailMaj();
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
			//			int s = vue.getGrille().getStyle();
			//			vue.getGrille().dispose();
			//			vue.setGrille(new Table(vue.getPaGrille(),s|SWT.CHECK));
			//			vue.pack();

			setGrille(vue.getGrille());
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
			checkHandler();
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : SWTPaMailUpdaterController", e);
		}
	}

	public void bind(PaMailUpdaterSWT paMailUpdaterSWT) {
		try {
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			tableViewer = new LgrTableViewer(paMailUpdaterSWT.getGrille());
			tableViewer.createTableCol(paMailUpdaterSWT.getGrille(), nomClassController,Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			listeChamp = tableViewer.setListeChampGrille(nomClassController,Const.C_FICHIER_LISTE_CHAMP_GRILLE);

//			ObservableListContentProvider viewerContent = new ObservableListContentProvider();
//			tableViewer.setContentProvider(viewerContent);
//
//			IObservableMap[] attributeMaps = BeansObservables.observeMaps(
//					viewerContent.getKnownElements(), classModel,listeChamp);
//
//			tableViewer.setLabelProvider(new ObservableMapLabelProvider(attributeMaps));
//			writableList = new WritableList(realm, modelMailMaj.remplirListe(), classModel);
//			tableViewer.setInput(writableList);
			
			// Set up data binding.
			LgrViewerSupport.bind(tableViewer, 
					new WritableList(modelMailMaj.remplirListe(), classModel),
					BeanProperties.values(listeChamp)
					);

			selectedMailMaj = ViewersObservables.observeSingleSelection(tableViewer);

			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			tableViewer.selectionGrille(0);

			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);

			checkInit();

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
			if (((ParamAfficheMailUpdater) param).getFocusDefautSWT() != null)
				((ParamAfficheMailUpdater) param).getFocusDefautSWT().setFocus();
			else
				((ParamAfficheMailUpdater) param).setFocusDefautSWT(vue
						.getGrille());
			vue.getLaTitreFormulaire().setText(
					((ParamAfficheMailUpdater) param).getTitreFormulaire());
			vue.getLaTitreGrille().setText(
					((ParamAfficheMailUpdater) param).getTitreGrille());
			vue.getLaTitreFenetre().setText(
					((ParamAfficheMailUpdater) param).getSousTitre());

			if (param.getEcranAppelant() != null) {
				ecranAppelant = param.getEcranAppelant();
			}

			bind(vue);
			// permet de se positionner sur le 1er enregistrement et de remplir
			// le formulaire
			tableViewer.selectionGrille(0);
			tableViewer.tri(classModel, nomClassController, Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			VerrouInterface.setVerrouille(false);
			setSwtOldMailMaj();

			if (param.getModeEcran() != null
					&& param.getModeEcran().compareTo(
							EnumModeObjet.C_MO_INSERTION) == 0) {
				try {
					actInserer();
				} catch (Exception e) {
					vue.getLaMessage().setText(e.getMessage());
					logger.error("", e);
				}
			} else {
				initEtatBouton();
			}

			param.setFocus(dao.getModeObjet().getFocusCourant());
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

		//		vue.getTfDateMAJ().setToolTipText(Const.C_DATE_MAIL_MAJ);
		//		vue.getTfNomMAJ().setToolTipText(Const.C_SUJET_MAIL_MAJ);
		//
		//		mapComposantChamps.put(vue.getTfDateMAJ(), Const.C_DATE_MAIL_MAJ);
		//		mapComposantChamps.put(vue.getTfNomMAJ(), Const.C_SUJET_MAIL_MAJ);
		//		mapComposantChamps.put(vue.getCbAFaire(), Const.C_A_FAIRE_MAIL_MAJ);
		////mapComposantChamps.put(vue.getCbAFaire(), "a_FAIRE_MAIL_MAJ");

		for (Control c : mapComposantChamps.keySet()) {
			listeComposantFocusable.add(c);
		}

		listeComposantFocusable.add(vue.getBtnEnregistrer());
		listeComposantFocusable.add(vue.getBtnInserer());
		listeComposantFocusable.add(vue.getBtnModifier());
		listeComposantFocusable.add(vue.getBtnSupprimer());
		listeComposantFocusable.add(vue.getBtnFermer());
		listeComposantFocusable.add(vue.getBtnAnnuler());
		listeComposantFocusable.add(vue.getBtnMail());
		listeComposantFocusable.add(vue.getBtnMAJ());
		// listeComposantFocusable.add(vue.getBtnImprimer());

		if (mapInitFocus == null)
			mapInitFocus = new LinkedHashMap<ModeObjet.EnumModeObjet, Control>();
		//		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION, vue
		//				.getTfDateMAJ());
		//		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue
		//				.getTfDateMAJ());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION, vue
				.getGrille());

		activeModifytListener();
	}

	protected void initEtatBouton() {
		initEtatBoutonCommand();
		boolean trouve = false;
		if(modelMailMaj!=null) {
			trouve = modelMailMaj.getListeObjet().size()>0;
		} else {
			trouve = daoStandard.selectAll().size()>0;
		}
		switch (dao.getModeObjet().getMode()) {
		case C_MO_INSERTION:
		case C_MO_EDITION:
			enableActionAndHandler(C_COMMAND_MISE_A_JOUR_ID, false);
			break;
		case C_MO_CONSULTATION:
			enableActionAndHandler(C_COMMAND_MISE_A_JOUR_ID, trouve);
			break;
		default:
			break;
		}

		enableActionAndHandler(C_COMMAND_GLOBAL_ENREGISTRER_ID, false);
		enableActionAndHandler(C_COMMAND_GLOBAL_INSERER_ID, false);
		enableActionAndHandler(C_COMMAND_GLOBAL_MODIFIER_ID, false);
		enableActionAndHandler(C_COMMAND_RECHERCHE_MAIL_MISE_A_JOUR_ID, true);
		enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,false);
		initFocusSWT(dao,mapInitFocus);

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
		mapCommand.put(C_COMMAND_MISE_A_JOUR_ID, handlerMiseAJour);
		mapCommand.put(C_COMMAND_RECHERCHE_MAIL_MISE_A_JOUR_ID, handlerRechercheMailMiseAJour);


		initFocusCommand(String.valueOf(this.hashCode()),listeComposantFocusable,mapCommand);

		if (mapActions == null)
			mapActions = new LinkedHashMap<Button, Object>();

		mapActions.put(vue.getBtnAnnuler(), C_COMMAND_GLOBAL_ANNULER_ID);
		mapActions.put(vue.getBtnEnregistrer(), C_COMMAND_GLOBAL_ENREGISTRER_ID);
		mapActions.put(vue.getBtnInserer(), C_COMMAND_GLOBAL_INSERER_ID);
		mapActions.put(vue.getBtnModifier(), C_COMMAND_GLOBAL_MODIFIER_ID);
		mapActions.put(vue.getBtnSupprimer(), C_COMMAND_GLOBAL_SUPPRIMER_ID);
		mapActions.put(vue.getBtnFermer(), C_COMMAND_GLOBAL_FERMER_ID);
		mapActions.put(vue.getBtnMAJ(), C_COMMAND_MISE_A_JOUR_ID);
		mapActions.put(vue.getBtnMail(), C_COMMAND_RECHERCHE_MAIL_MISE_A_JOUR_ID);
		// mapActions.put(vue.getBtnImprimer(), C_COMMAND_GLOBAL_IMPRIMER_ID);

		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID, C_COMMAND_GLOBAL_REFRESH_ID };
		mapActions.put(null, tabActionsAutres);

	}

	protected void initImageBouton() {
		super.initImageBouton();
		String C_IMAGE_RECHERCHE_MAJ = "/icons/usearch_obj.gif";
		String C_IMAGE_MAJ = "/icons/updates_obj.gif";
		if(vue instanceof PaMailUpdaterSWT) {
			((PaMailUpdaterSWT)vue).getBtnMail().setImage(UpdaterPlugin.getImageDescriptor(C_IMAGE_RECHERCHE_MAJ).createImage());
			((PaMailUpdaterSWT)vue).getBtnMAJ().setImage(UpdaterPlugin.getImageDescriptor(C_IMAGE_MAJ).createImage());
			vue.layout(true);
		}
	}

	public SWTPaMailUpdaterController getThis() {
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
					.getString("MailUpdater.Message.Enregistrer"))) {

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
				dao.initValeurIdTable(taMailMaj);
				fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
						dao.getChampIdEntite(), dao.getValeurIdTable(),
						selectedMailMaj.getValue())));
				retour = true;
			}
		}
		if (retour && !(ecranAppelant instanceof SWTPaAideControllerSWT)) {
			fireDestroy(new DestroyEvent(dao));
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
		} else if (evt.getRetour() != null) {
			if (getFocusAvantAideSWT() instanceof Table) {
			}
		}
		super.retourEcran(evt);
	}

	@Override
	protected void actInserer() throws Exception {
		try {
			if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
				VerrouInterface.setVerrouille(true);
				setSwtOldMailMaj();
				swtMailMaj = new SWTMailMaj();

				taMailMaj = new TaMailMaj();
				dao.inserer(taMailMaj);
				modelMailMaj.getListeObjet().add(swtMailMaj);
				writableList = new WritableList(realm, modelMailMaj.getListeObjet(), classModel);
				tableViewer.setInput(writableList);
				tableViewer.refresh();
				tableViewer.setSelection(new StructuredSelection(swtMailMaj));
				initEtatBouton();
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
			if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
				setSwtOldMailMaj();
				taMailMaj = dao.findById(((SWTMailMaj) selectedMailMaj.getValue()).getIdMailMaj());
				dao.modifier(taMailMaj);

				initEtatBouton();
			}
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		}
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
						.getString("MailUpdater.Message.Supprimer"))) {
					dao.getEntityManager().getTransaction().begin();
					TaMailMaj u = dao.findById(((SWTMailMaj) selectedMailMaj.getValue()).getIdMailMaj());
					dao.supprimer(u);
					taMailMaj=null;
					dao.getEntityManager().getTransaction().commit();
					dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
					actRefresh(); //ajouter pour tester jpa
				}
		} catch (ExceptLgr e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		} finally {
			initEtatBouton();
			VerrouInterface.setVerrouille(false);
		}
	}

	@Override
	protected void actFermer() throws Exception {
		if(onClose())
			closeWorkbenchPart();
	}

	@Override
	protected void actAnnuler() throws Exception {
		// // vï¿½rifier si l'objet est en modification ou en consultation
		// // si modification ou insertion, alors demander si annulation des
		// // modifications si ok, alors annulation si pas ok, alors arrï¿½ter le
		// processus d'annulation
		// // si consultation dï¿½clencher l'action "fermer".
		try {
			VerrouInterface.setVerrouille(true);
			// //InputVerifier inputVerifier =
			// getFocusCourant().getInputVerifier();
			// //getFocusCourant().setInputVerifier(null);
			switch (dao.getModeObjet().getMode()) {
			case C_MO_INSERTION:
				if (MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("MailUpdater.Message.Annuler"))) {

					if(((SWTMailMaj) selectedMailMaj.getValue()).getIdMailMaj()==null){
						modelMailMaj.getListeObjet().remove(((SWTMailMaj) selectedMailMaj.getValue()));
						writableList = new WritableList(realm, modelMailMaj.getListeObjet(), classModel);
						tableViewer.setInput(writableList);
						tableViewer.refresh();
						tableViewer.selectionGrille(0);
					}
					dao.annuler(taMailMaj);
					hideDecoratedFields();
				}
				initEtatBouton();
				break;
			case C_MO_EDITION:
				if (MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("MailUpdater.Message.Annuler"))) {
					int rang = modelMailMaj.getListeObjet().indexOf(selectedMailMaj.getValue());
					modelMailMaj.getListeObjet().set(rang, swtOldMailMaj);
					writableList = new WritableList(realm, modelMailMaj.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.setSelection(new StructuredSelection(swtOldMailMaj), true);
					dao.annuler(taMailMaj);
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
		// // TODO procedure d'impression
		// JOptionPane.showMessageDialog(vue, Const.C_A_IMPLEMENTER,
		// MessagesEcran.getString("Message.Attention"),
		// JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
		initFocusSWT(dao, mapInitFocus);
	}

	@Override
	protected void actAide() throws Exception {
		actAide(null);
	}

	@Override
	protected void actAide(String message) throws Exception {
		try {
			VerrouInterface.setVerrouille(true);
			vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
			ParamAfficheAideRechercheSWT paramAfficheAideRecherche = new ParamAfficheAideRechercheSWT();
			paramAfficheAideRecherche.setMessage(message);
			// Creation de l'ecran d'aide principal
			Shell s = new Shell(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),LgrShellUtil.styleLgr);
			PaAideSWT paAide = new PaAideSWT(s, SWT.NONE);
			SWTPaAideControllerSWT paAideController = new SWTPaAideControllerSWT(paAide);
			JPABaseControllerSWTStandard controllerEcranCreation = null;
			ParamAffiche parametreEcranCreation = null;
			Shell s2 = new Shell(s, LgrShellUtil.styleLgr);

			switch ((getThis().dao.getModeObjet().getMode())) {
			case C_MO_CONSULTATION:
				if(getFocusCourantSWT().equals(vue.getGrille())){
					PaMailUpdaterSWT paMailUpdaterSWT = new PaMailUpdaterSWT(s2,SWT.NULL);
					SWTPaMailUpdaterController swtPaBanqueController = new SWTPaMailUpdaterController(paMailUpdaterSWT);
					ParamAfficheMailUpdater paramAfficheMailUpdater = new ParamAfficheMailUpdater();
					//					paramAfficheAideRecherche.setQuery(swtPaBanqueController.getDao().getFIBQuery());
					paramAfficheMailUpdater.setModeEcran(EnumModeObjet.C_MO_INSERTION);
					paramAfficheMailUpdater.setEcranAppelant(paAideController);
					controllerEcranCreation = swtPaBanqueController;
					parametreEcranCreation = paramAfficheMailUpdater;

					paramAfficheAideRecherche.setChampsRecherche(Const.C_SUJET_MAIL_MAJ);
					//					paramAfficheAideRecherche.setDebutRecherche(vue.getTfDateMAJ().getText());
					paramAfficheAideRecherche.setControllerAppelant(getThis());
					//					paramAfficheAideRecherche.setModel(SWTPaMailUpdaterController.getMailMaj());
					//					paramAfficheAideRecherche.setTypeObjet(SWTPaMailUpdaterController.getClassModel());
					//					paramAfficheAideRecherche.setChampsIdentifiant(SWTPaMailUpdaterController.getIbTaTable().champIdTable);
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
				paramAfficheAideRecherche
				.setFocusSWT(((PaAideRechercheSWT) paAideRechercheController1
						.getVue()).getTfChoix());
				paramAfficheAideRecherche
				.setRefCreationSWT(controllerEcranCreation);
				paramAfficheAideRecherche
				.setParamEcranCreation(parametreEcranCreation);
				paramAfficheAideRecherche.setShellCreation(s2);
				paAideRechercheController1
				.configPanel(paramAfficheAideRecherche);
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
				LgrShellUtil.afficheAideSWT(paramAfficheAide, null, paAide,
						paAideController, s);
				dbc.getValidationStatusMap().addMapChangeListener(
						changeListener);

			}

		} finally {
			VerrouInterface.setVerrouille(false);
			vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
		}
	}

	public IStatus validateUI() throws Exception {
		if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
				|| (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
			ctrlTousLesChampsAvantEnregistrementSWT();
			mapperUIToModel.map((SWTMailMaj) selectedMailMaj.getValue(),taMailMaj);
		}
		return null;
	}

	public IStatus validateUIField(String nomChamp,Object value) {
		String validationContext = "MAIL_MAJ";
		try {
			IStatus s = null;

			if(nomChamp.equals(Const.C_CODE_FAMILLE)) {
				//				TaFamilleDAO dao = new TaFamilleDAO();
				//				
				//				dao.setModeObjet(getDao().getModeObjet());
				//				TaFamille f = new TaFamille();
				//				PropertyUtils.setSimpleProperty(f, nomChamp, value);
				//				s = dao.validate(f,nomChamp,validationContext);
				//				
				//				if(s.getSeverity()!=IStatus.ERROR ){
				//					f = dao.findByCode((String)value);
				//					taArticle.setTaFamille(f);
				//				}
				//				dao = null;
			} else {
				TaMailMaj u = new TaMailMaj();
				PropertyUtils.setSimpleProperty(u, nomChamp, value);
				if(((SWTMailMaj) selectedMailMaj.getValue()).getIdMailMaj()!=null) {
					u.setIdMailMaj(((SWTMailMaj) selectedMailMaj.getValue()).getIdMailMaj());
				}

				s = dao.validate(u,nomChamp,validationContext);
			}
			return s;
		} catch (IllegalAccessException e) {
			logger.error("",e);
		} catch (InvocationTargetException e) {
			logger.error("",e);
		} catch (NoSuchMethodException e) {
			logger.error("",e);
		} catch (Exception e) {
			logger.error("",e);
		}
		return null;
	}

	@Override
	protected void actEnregistrer() throws Exception {
		EntityTransaction transaction = null;
		try {
			boolean declanchementExterne = false;
			if(sourceDeclencheCommandeController==null) {
				declanchementExterne = true;
			}
			if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
					|| (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {

				if(declanchementExterne) {
					ctrlTousLesChampsAvantEnregistrementSWT();
				}

				transaction = dao.getEntityManager().getTransaction();
				dao.begin(transaction);

				if(declanchementExterne) {
					mapperUIToModel.map((SWTMailMaj) selectedMailMaj.getValue(),taMailMaj);
				}
				if((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)){	
					taMailMaj=dao.enregistrerMerge(taMailMaj);
					modelMailMaj.getListeEntity().add(taMailMaj);
				}
				else taMailMaj=dao.enregistrerMerge(taMailMaj);
				

				dao.commit(transaction);
				transaction = null;


				actRefresh(); //deja present

			}
		} finally {
			initEtatBouton();
		}
	}

	public TaMailMajDAO getDao() {
		return dao;
	}

	public void initEtatComposant() {
		try {
			//			if((SWTMailMaj)selectedMailMaj.getValue()!=null)
			//				dao.setChamp_Model_Obj((SWTMailMaj)selectedMailMaj.getValue());

			//			if (ibTaTable.getFIBQuery().isOpen()) {
			//				vue.getTfDateMAJ().setEditable(
			//						ibTaTable.recordModifiable(ibTaTable.nomTable, ibTaTable
			//								.getChamp_Obj(ibTaTable.champIdTable)));
			//			}
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
		}
	}

	public boolean isUtilise(){
		return ((SWTMailMaj)selectedMailMaj.getValue()).getIdMailMaj()!=null && 
		!dao.recordModifiable(dao.getNomTable(),
				((SWTMailMaj)selectedMailMaj.getValue()).getIdMailMaj());		
	}

	public SWTMailMaj getSwtOldMailMaj() {
		return swtOldMailMaj;
	}

	public void setSwtOldMailMaj(SWTMailMaj swtOldMailMaj) {
		this.swtOldMailMaj = swtOldMailMaj;
	}

	public void setSwtOldMailMaj() {
		if (selectedMailMaj.getValue() != null)
			this.swtOldMailMaj = SWTMailMaj.copy((SWTMailMaj) selectedMailMaj.getValue());
		else {
			if (tableViewer.selectionGrille(0)){
				this.swtOldMailMaj = SWTMailMaj.copy((SWTMailMaj) selectedMailMaj.getValue());
				tableViewer.setSelection(new StructuredSelection(
						(SWTMailMaj) selectedMailMaj.getValue()), true);
			}}
	}

	public void setVue(PaMailUpdaterSWT vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();
		//		mapComposantDecoratedField.put(vue.getTfDateMAJ(), vue
		//				.getFieldNomMAJ());
		//		mapComposantDecoratedField.put(vue.getTfNomMAJ(), vue
		//				.getFieldDateMAJ());
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
		if (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION) == 0) {
			//rafraichissement des valeurs dans la grille
			writableList = new WritableList(realm, modelMailMaj.remplirListe(), classModel);
			tableViewer.setInput(writableList);
			tableViewer.refresh();
		} else {
			if (taMailMaj!=null && selectedMailMaj!=null && (SWTMailMaj) selectedMailMaj.getValue()!=null) {
				mapperModelToUI.map(taMailMaj, (SWTMailMaj) selectedMailMaj.getValue());
			}
		}

		//repositionnement sur la valeur courante
		int idActuel = 0;
		if (taMailMaj!=null) { //enregistrement en cours de modification/insertion
			idActuel = taMailMaj.getIdMailMaj();
		} else if(selectedMailMaj!=null && (SWTMailMaj) selectedMailMaj.getValue()!=null) {
			idActuel = ((SWTMailMaj) selectedMailMaj.getValue()).getIdMailMaj();
		}

		if(idActuel!=0) {
			tableViewer.setSelection(new StructuredSelection(modelMailMaj.recherche(Const.C_ID_MAIL_MAJ
					, idActuel)));
		}else
			tableViewer.selectionGrille(0);				
		checkInit();
	}

	public ModelGeneralObjet<SWTMailMaj,TaMailMajDAO,TaMailMaj> getMailMaj() {
		return modelMailMaj;
	}

	/**
	 * Repercute la selection/deselection de la case a coche en debut de ligne sur la liste d'objet
	 * represente dans la table. Modification des objets.
	 */
	private void checkHandler() {
		vue.getGrille().addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if(event.detail == SWT.CHECK) {
					((SWTMailMaj)selectedMailMaj.getValue()).setAFaireMailMaj(!((SWTMailMaj)selectedMailMaj.getValue()).getAFaireMailMaj());
					try {
						actModifier();
						actEnregistrer();
					} catch (Exception e) {
						logger.debug("",e);
					}
				}
			}
		});
	}

	/**
	 * Initialise les checkbox en debut de ligne en fonction de la valeur des champs de la ligne
	 */
	private void checkInit() {
		for (SWTMailMaj maj : modelMailMaj.getListeObjet()) {
			tableViewer.setChecked(maj, maj.getAFaireMailMaj());
		}
	}

	class ActionMiseAJour extends Action {
		public ActionMiseAJour(String name) {
			super(name);
		}

		public void run() {
			super.run();
			try {
				handlerService.executeCommand(C_COMMAND_MISE_A_JOUR_ID, null);
			} catch (ExecutionException e) {
				logger.error("",e);
			} catch (NotDefinedException e) {
				logger.error("",e);
			} catch (NotEnabledException e) {
				logger.error("",e);
			} catch (NotHandledException e) {
				logger.error("",e);
			}
		}
	}

	protected class HandlerMiseAJour extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException{

			if(!modelMailMaj.getListeObjet().isEmpty()) {
				BusyIndicator.showWhile(vue.getDisplay(),
						new Runnable() {
					public void run() {
						ArrayList<Integer> idMailMaj = new ArrayList<Integer>(); //id des mail de mise à jour qui doivent être traiter
						//UpdateJob job = new UpdateJob(
						//		"Recherche de mises à jour", getSearchRequest(idMailMaj));
						//UpdateManagerUI.openInstaller(vue.getShell(), job);
						try {
							
							handlerService.executeCommand("org.eclipse.equinox.p2.examples.rcp.cloud.command.install", null);
						} catch (ExecutionException e) {
							logger.error("",e);
						} catch (NotDefinedException e) {
							logger.error("",e);
						} catch (NotEnabledException e) {
							logger.error("",e);
						} catch (NotHandledException e) {
							logger.error("",e);
						}

						validationDesMAJ(idMailMaj);
					}
				}
				);
			} else {
				if(logger.isDebugEnabled())
					logger.debug("Pas de mise à jour à faire.");
			}

			return event;
		}

		/**
		 * Creation de la liste des sites de MAJ pour le module de mise à jour d'eclipse
		 * @return
		 */
		private UpdateSearchRequest getSearchRequest(List<Integer> idMailMaj) {
			UpdateSearchRequest result = new UpdateSearchRequest(
					UpdateSearchRequest.createDefaultSiteSearchCategory(),
					new UpdateSearchScope());
			result.addFilter(new BackLevelFilter());
			result.addFilter(new EnvironmentFilter());
			UpdateSearchScope scope = new UpdateSearchScope();
			//ajout des sites de mises à jour
			String nom = null;
			URL url = null;
			boolean trouve = false;
			/*
			 * DEBUT AVANT JPA
			 */
			//			dao.first();
			//			do {
			//				logger.debug("MAJ : "+dao.getChamp_Obj(Const.C_NOM_SITE_MAIL_MAJ)+" : "+dao.getChamp_Obj(Const.C_URL_MAIL_MAJ));
			//				if(dao.getChamp_Obj(Const.C_A_FAIRE_MAIL_MAJ).equals("1")) { //la maj doit etre prise en compte
			//					try {
			//						idMailMaj.add(dao.getChamp_Obj(Const.C_ID_MAIL_MAJ));
			//						nom = dao.getChamp_Obj(Const.C_NOM_SITE_MAIL_MAJ);
			//						url = new URL(dao.getChamp_Obj(Const.C_URL_MAIL_MAJ));
			//						trouve = false; int i =0;
			//						//Dans une session de MAJ 2 sites ne peuvent pas avoir le meme nom, ni la meme adresse
			//						while(!trouve && i<scope.getSearchSites().length) {
			//							if(scope.getSearchSites()[i].getLabel().equals(nom)) {
			//								trouve = true;
			//								logger.info("2 sites de MAJ avec le même nom : "+nom);
			//							} else if (scope.getSearchSites()[i].getURL().equals(url)) {
			//								trouve = true;
			//								logger.info("2 sites de MAJ avec la même adresse : "+url.toString());
			//							}
			//							i++;
			//						}
			//						if(!trouve) {
			//							scope.addSearchSite(nom,url,null);
			//						}
			//					} catch (MalformedURLException e) {
			//						logger.error(e);
			//					}
			//				}
			//			} while(dao.next());
			/*
			 * FIN AVANT JPA
			 */

			
			for (SWTMailMaj maj : modelMailMaj.getListeObjet()) {
				
			
				logger.debug("MAJ : "+maj.getNomSiteMailMaj()+" : "+maj.getUrlMailMaj());
				if(maj.getAFaireMailMaj()) { //la maj doit etre prise en compte
					try {
						idMailMaj.add(maj.getIdMailMaj());
						nom = maj.getNomSiteMailMaj();
						url = new URL(maj.getUrlMailMaj());
						trouve = false; int i =0;
						//Dans une session de MAJ 2 sites ne peuvent pas avoir le meme nom, ni la meme adresse
						while(!trouve && i<scope.getSearchSites().length) {
							if(scope.getSearchSites()[i].getLabel().equals(nom)) {
								trouve = true;
								logger.info("2 sites de MAJ avec le même nom : "+nom);
							} else if (scope.getSearchSites()[i].getURL().equals(url)) {
								trouve = true;
								logger.info("2 sites de MAJ avec la même adresse : "+url.toString());
							}
							i++;
						}
						if(!trouve) {
							scope.addSearchSite(nom,url,null);
						}
					} catch (MalformedURLException e) {
						logger.error(e);
					}
				}
			} 

			result.setScope(scope);
			return result;
		}

		/**
		 * Marque les MAJ "A Faire" comme effectuées
		 * Si 2 sites avaient le meme nom ou la meme adresse, ils sont tous les 2 "validés"
		 * même si un seul des 2 à effectivement été utilisé.
		 */
		private void validationDesMAJ(List<Integer> idMailMaj) {

			/*
			 * DEBUT AVANT JPA
			 */
			//			SWTMailMaj maj = new SWTMailMaj();
			//			for (String string : idMailMaj) {
			//
			//				//Positionnement sur le bon enregistrement
			//				DataRow courant = new DataRow(dao.getFIBQuery(),dao.champIdTable);
			//				courant.setInt(dao.champIdTable,LibConversion.stringToInteger(string));
			//				if(dao.getFIBQuery().locate(courant,Const.C_LOCATE_OPTION_BORLAND_FIRST)) {
			//
			//					//SWTMailMaj.getMAIL_MAJ(string,maj);
			//					dao.infosMailMaj(string, maj);
			//					maj.setFaitMailMaj(/*LibDate.dateToString(*/new java.util.Date()/*)*/);
			//					maj.setAFaireMailMaj(false);
			//					try {
			//						dao.modfication(maj);
			//					} catch (ExceptLgr e) {
			//						logger.error(e);
			//					}
			//				}
			//			}
			/*
			 * FIN AVANT JPA
			 */

			TaMailMaj maj = null;
			for (Integer i : idMailMaj) {
				//Positionnement sur le bon enregistrement
				maj = dao.findById(i);
				if(maj!=null) {

					maj.setFaitMailMaj(new java.util.Date());
					maj.setAFaireMailMaj(0);
					try {
						dao.modifier(maj);
						dao.enregistrerMerge(maj);
					} catch (ExceptLgr e) {
						logger.error(e);
					} catch (Exception e) {
						logger.error(e);
					}
				}
			}
		}

	}

	class ActionRechercheMailMiseAJour extends Action {
		public ActionRechercheMailMiseAJour(String name) {
			super(name);
		}

		public void run() {
			super.run();
			try {
				handlerService.executeCommand(C_COMMAND_RECHERCHE_MAIL_MISE_A_JOUR_ID, null);
			} catch (ExecutionException e) {
				logger.error("",e);
			} catch (NotDefinedException e) {
				logger.error("",e);
			} catch (NotEnabledException e) {
				logger.error("",e);
			} catch (NotHandledException e) {
				logger.error("",e);
			}
		}
	}

	protected class HandlerRechercheMailMiseAJour extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException{

			LgrUpdate lgrUpdate = new LgrUpdate();
			lgrUpdate.recupMailUpdate();
			try {
				actRefresh();
				initEtatBouton();
			} catch (Exception e) {
				logger.error("",e);
			}

			return event;
		}
	}

}

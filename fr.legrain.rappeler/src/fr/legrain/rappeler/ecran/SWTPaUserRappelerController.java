package fr.legrain.rappeler.ecran;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

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

import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDePageEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.ModeObjet;
import fr.legrain.lib.data.VerrouInterface;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.lib.gui.aide.ResultAide;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.rappeler.SWTTaUserRappeler;
import fr.legrain.rappeler.dao.TaUserRappeler;
import fr.legrain.rappeler.dao.TaUserRappelerDAO;
import fr.legrain.tiers.ecran.MessagesEcran;





public class SWTPaUserRappelerController extends JPABaseControllerSWTStandard
implements RetourEcranListener {

	static Logger logger = Logger.getLogger(SWTPaUserRappelerController.class.getName()); 
	private PaUserRappeler vue = null;
	private TaUserRappelerDAO dao = new TaUserRappelerDAO();

	private Object ecranAppelant = null;
	private SWTTaUserRappeler SWTTaUserRappeler;
	private SWTTaUserRappeler swtOldUserRappeler;
	private Realm realm;
	private DataBindingContext dbc;

	private Class classModel = SWTTaUserRappeler.class;
//	private ModelGeneralObjet<SWTTaUserRappeler,TaUserRappelerDAO,TaUserRappeler> modelUserRappeler = new ModelGeneralObjet<SWTTaUserRappeler,TaUserRappelerDAO,TaUserRappeler>(dao,classModel);
	private ModelGeneralObjet<SWTTaUserRappeler,TaUserRappelerDAO,TaUserRappeler> modelListUserRappeler = new ModelGeneralObjet<SWTTaUserRappeler,TaUserRappelerDAO,TaUserRappeler>(dao,classModel);
	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private IObservableValue selectedListUserRappeler;
	//private Object selectedListUserRappeler;
	
	private String[] listeChamp;
	private String nomClassController = this.getClass().getSimpleName();

	private MapChangeListener changeListener = new MapChangeListener();


	public static final String C_COMMAND_PRIX_ID = "fr.legrain.gestionCommerciale.articles.prix";
	private HandlerAjoutPrix handlerAjoutPrix = new HandlerAjoutPrix();

	private LgrDozerMapper<SWTTaUserRappeler,TaUserRappeler> mapperUIToModel = new LgrDozerMapper<SWTTaUserRappeler,TaUserRappeler>();
	private LgrDozerMapper<TaUserRappeler,SWTTaUserRappeler> mapperModelToUI = new LgrDozerMapper<TaUserRappeler,SWTTaUserRappeler>();
	private TaUserRappeler TaUserRappeler = null;

	
	



	public SWTPaUserRappelerController(PaUserRappeler vue) {
		setVue(vue);
		// a faire avant l'initialisation du Binding
		vue.getGrilleSource().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//setSwtOldArticle();
			}
		});
		vue.getShell().addShellListener(this);

		// Branchement action annuler : empeche la fermeture automatique de la
		// fenetre sur ESC
		vue.getShell().addTraverseListener(new Traverse());

		initController();
	}

	public TaUserRappelerDAO getDao() {
		return dao;
	}

	public void setDao(TaUserRappelerDAO dao) {
		this.dao = dao;
	}

	private void initController() {
		try {
			setGrille(vue.getGrilleSource());
			//vue.getGrille().setVisible(false);
			initSashFormWeight(new int[]{100,100});
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
//			vue.getTfCODE_ARTICLE().setMenu(popupMenuFormulaire);
			vue.getPaGrille().setMenu(popupMenuGrille);
			vue.getGrilleSource().setMenu(popupMenuGrille);
			//vue.getPaBtn().setVisible(false);
			initEtatBouton();

//			vue.getBtInscrireEmail().addSelectionListener(new SelectionListener(){
//
//				@Override
//				public void widgetDefaultSelected(SelectionEvent e) {
//					// TODO Auto-generated method stub
//					try {
//						actInserer();
//						actEnregistrer();
//					} catch (Exception e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//				}
//
//				@Override
//				public void widgetSelected(SelectionEvent e) {
//					// TODO Auto-generated method stub
//					widgetDefaultSelected(e);
//				}
//				
//			});
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaArticlesController", e);
		}
	}

	public void bind(PaUserRappeler paUserRappeler) {
		try {
//			realm = SWTObservables.getRealm(vue.getParent().getDisplay());
//
//			//LgrDozerMapper<TaFacture,IHMTotauxFacture> mapper = new LgrDozerMapper<TaFacture,IHMTotauxFacture>();
//			if(SWTTaUserRappeler==null) SWTTaUserRappeler = new SWTTaUserRappeler();
//			if(TaUserRappeler!=null)
//				mapperModelToUI.map(TaUserRappeler,SWTTaUserRappeler);
//
//			if(modelUserRappeler.getListeObjet().isEmpty()) {
//				modelUserRappeler.getListeObjet().add(SWTTaUserRappeler);
//			}
//			selectedUserRappeler = SWTTaUserRappeler;
//			dbc = new DataBindingContext(realm);
//
//			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
//			setDbcStandard(dbc);
//
//			bindingFormSimple(dbc, realm, selectedUserRappeler,classModel);
//			//bindingFormSimple(dao, mapComposantChampsCommentaire, dbc, realm, selectedUserRappeler, classModel);

			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			tableViewer = new LgrTableViewer(paUserRappeler.getGrilleSource());
			tableViewer.createTableCol(classModel,paUserRappeler.getGrilleSource(), nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE,0);
			listeChamp = tableViewer.setListeChampGrille(nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE);

			ObservableListContentProvider viewerContent = new ObservableListContentProvider();
			tableViewer.setContentProvider(viewerContent);

			IObservableMap[] attributeMaps = BeansObservables.observeMaps(
					viewerContent.getKnownElements(), classModel,listeChamp);

			tableViewer.setLabelProvider(new ObservableMapLabelProvider(attributeMaps));
			writableList = new WritableList(realm, modelListUserRappeler.remplirListe(), classModel);
			tableViewer.setInput(writableList);

			selectedListUserRappeler = ViewersObservables.observeSingleSelection(tableViewer);

			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			tableViewer.selectionGrille(0);

			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);

			bindingFormMaitreDetail(dbc, realm, selectedListUserRappeler,classModel);

	
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
						.getGrilleSource());
			if(param.getTitreFormulaire()!=null){
				vue.getLaTitreFormulaire().setText(param.getTitreFormulaire());
			} else {
				vue.getLaTitreFormulaire().setText(ParamAfficheRappeler.C_TITRE_FORMULAIRE);
			}

			if(param.getTitreGrille()!=null){
				vue.getLaTitreGrille().setText(param.getTitreGrille());
			} else {
				vue.getLaTitreGrille().setText(ParamAfficheRappeler.C_TITRE_GRILLE);
			}

			if(param.getSousTitre()!=null){
				vue.getLaTitreFenetre().setText(param.getSousTitre());
			} else {
				vue.getLaTitreFenetre().setText(ParamAfficheRappeler.C_SOUS_TITRE);
			}

			if (param.getEcranAppelant() != null) {
				ecranAppelant = param.getEcranAppelant();
			}

			bind(vue);

			// permet de se positionner sur le 1er enregistrement et de remplir
			// le formulaire
//			tableViewer.selectionGrille(0);
//			tableViewer.tri(classModel, nomClassController,Const.C_FICHIER_LISTE_CHAMP_GRILLE);
//			VerrouInterface.setVerrouille(false);
//			setSwtOldArticle();

//			if(param.getCodeDocument()!=null) {
//				SWTTaUserRappeler art = modelArticle.recherche(Const.C_CODE_ARTICLE, param.getCodeDocument());
//				if(art!=null) {
//					tableViewer.setSelection(new StructuredSelection(art),true);
//				}
//			}

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
		listeComposantFocusable.add(vue.getGrilleSource());

//		mapComposantChamps.put(vue.getEmailCombo(), Const.C_CODE_ARTICLE);
//		mapComposantChamps.put(vue.getBtChoixEmail(), Const.C_LIBELLEC_ARTICLE);
//		mapComposantChamps.put(vue.getTfLIBELLEL_ARTICLE(), Const.C_LIBELLEL_ARTICLE);
//		mapComposantChamps.put(vue.getTfCODE_FAMILLE(), Const.C_CODE_FAMILLE);
//		mapComposantChamps.put(vue.getTfCODE_TVA(), Const.C_CODE_TVA);
//		mapComposantChamps.put(vue.getTfCODE_UNITE(), Const.C_CODE_UNITE);
//		mapComposantChamps.put(vue.getTfPRIX_PRIX(), Const.C_PRIX_PRIX);
//		mapComposantChamps.put(vue.getTfPRIXTTC_PRIX(), Const.C_PRIXTTC_PRIX);
//		mapComposantChamps.put(vue.getTfSTOCK_MIN_ARTICLE(), Const.C_STOCK_MIN_ARTICLE);
//		mapComposantChamps.put(vue.getTfNUMCPT_ARTICLE(), Const.C_NUMCPT_ARTICLE);
//		mapComposantChamps.put(vue.getTfCODE_T_TVA(), Const.C_CODE_T_TVA);
//		mapComposantChamps.put(vue.getTfDIVERS_ARTICLE(), Const.C_DIVERS_ARTICLE);
		mapComposantChamps.put(vue.getNewMail() , Const.C_IS_MAIL );
		mapComposantChamps.put(vue.getNewName(), Const.C_New_Name);

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
				.getNewMail());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue
				.getNewMail());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION, vue
				.getGrilleSource());

		activeModifytListener();

//		vue.getTfSTOCK_MIN_ARTICLE().addVerifyListener(queDesChiffres);
//		vue.getTfNUMCPT_ARTICLE().addVerifyListener(queDesChiffres);
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
		//mapCommand.put(C_COMMAND_GLOBAL_IMPRIMER_ID, handlerImprimer);

	

		
		mapCommand.put(C_COMMAND_GLOBAL_SELECTION_ID, handlerSelection);

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
//		mapActions.put(vue.getBtnAjoutPrix(), C_COMMAND_PRIX_ID);

		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID, C_COMMAND_GLOBAL_REFRESH_ID, C_COMMAND_GLOBAL_SELECTION_ID };
		mapActions.put(null, tabActionsAutres);

	}

	protected void initEtatBouton() {
		initEtatBoutonCommand();
		
		
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
					.getString("Articles.Message.Enregistrer"))) {

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
				dao.initValeurIdTable(TaUserRappeler);
				fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
						dao.getChampIdEntite(), dao.getValeurIdTable(),
						selectedListUserRappeler.getValue())));

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

//					if(getFocusAvantAideSWT().equals(vue.getTfCODE_FAMILLE())){
//						TaFamille f = null;
//						TaFamilleDAO t = new TaFamilleDAO();
//						f = t.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
//						t = null;
//						TaUserRappeler.setTaFamille(f);
//					}

					ctrlUnChampsSWT(getFocusAvantAideSWT());
				} catch (Exception e) {
					logger.error("",e);
					if(getFocusAvantAideSWT()!=null)setFocusCourantSWT(getFocusAvantAideSWT());
					vue.getLaMessage().setText(e.getMessage());
				}
			}
			if (getFocusAvantAideSWT() instanceof Table) {
				if (getFocusAvantAideSWT() == vue.getGrilleSource()) {
					if(((ResultAffiche) evt.getRetour()).getSelection()!=null)
						tableViewer.setSelection(((ResultAffiche) evt.getRetour()).getSelection(),true);
				}
			}
		} else if (evt.getRetour() != null){
			if (getFocusAvantAideSWT() instanceof Table) {
				if (getFocusAvantAideSWT() == vue.getGrilleSource()) {
				}
			}
		}
		super.retourEcran(evt);
	}

	/**
	 * Creation d'un objet "TaPrix" pour l'objet "TaUserRappeler" gerer par cet ecran
	 * dans le cas ou la propriete taPrix de ce dernier est nulle.
	 */
//	private void initPrixArticle() {
//		if(TaUserRappeler.getTaPrix()==null) {
//			//initialisation du prix
//			TaPrix p = new TaPrix();
//			p.setTaUserRappeler(TaUserRappeler);
//			TaUserRappeler.setTaPrix(p);
//			TaUserRappeler.getTaPrixes().add(p);
//		}
//	}

	@Override
	protected void actInserer() throws Exception {
		try {
			if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
				VerrouInterface.setVerrouille(true);
				setSwtOldUserRappeler();
				SWTTaUserRappeler = new SWTTaUserRappeler();
//				SWTTaUserRappeler.setCodeArticle(dao.genereCode());
//				validateUIField(Const.C_CODE_ARTICLE,SWTTaUserRappeler.getCodeArticle());//permet de verrouiller le code genere
//				SWTTaUserRappeler.setStockMinArticle(new BigDecimal(1));

				TaUserRappeler = new TaUserRappeler();
				dao.inserer(TaUserRappeler);
				modelListUserRappeler.getListeObjet().add(SWTTaUserRappeler);
				writableList = new WritableList(realm, modelListUserRappeler.getListeObjet(), classModel);
				tableViewer.setInput(writableList);
				tableViewer.refresh();
				tableViewer.setSelection(new StructuredSelection(SWTTaUserRappeler));
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
			if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
				setSwtOldUserRappeler();
				TaUserRappeler = dao.findById(((SWTTaUserRappeler) selectedListUserRappeler.getValue()).getIdUserRappeler());
				dao.modifier(TaUserRappeler);

				initEtatBouton();
			}
			
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		}
	}


	protected void actSupprimer() throws Exception {
		try {
			VerrouInterface.setVerrouille(true);
			if(isUtilise())MessageDialog.openInformation(vue.getShell(), MessagesEcran
					.getString("Message.Attention"), MessagesEcran
					.getString("Message.suppression"));
			else
				if (MessageDialog.openConfirm(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("Articles.Message.Supprimer"))) {
					dao.getEntityManager().getTransaction().begin();
					TaUserRappeler u = dao.findById(((SWTTaUserRappeler) selectedListUserRappeler.getValue()).getIdUserRappeler());
					dao.supprimer(u);
					TaUserRappeler=null;
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
		// (controles de sortie et fermeture de la fenetre) => onClose()
		if(onClose())
			closeWorkbenchPart();
	}

	@Override
	protected void actAnnuler() throws Exception {
		try {
			VerrouInterface.setVerrouille(true);
			switch (dao.getModeObjet().getMode()) {
			case C_MO_INSERTION:
				if (MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("Articles.Message.Annuler"))) {

//					if(((SWTTaUserRappeler) selectedArticle.getValue()).getIdArticle()==null){
//						modelArticle.getListeObjet().remove(((SWTTaUserRappeler) selectedArticle.getValue()));
//						writableList = new WritableList(realm, modelArticle.getListeObjet(), classModel);
//						tableViewer.setInput(writableList);
//						tableViewer.refresh();
//						tableViewer.selectionGrille(0);
//					}
					dao.annuler(TaUserRappeler);
					hideDecoratedFields();
				}
				initEtatBouton();
				break;
			case C_MO_EDITION:
				if (MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("Articles.Message.Annuler"))) {
					int rang = modelListUserRappeler.getListeObjet().indexOf(selectedListUserRappeler.getValue());
					modelListUserRappeler.getListeObjet().set(rang, swtOldUserRappeler);
					writableList = new WritableList(realm, modelListUserRappeler.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.setSelection(new StructuredSelection(swtOldUserRappeler), true);
					dao.annuler(TaUserRappeler);
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

	public void imprimerSimple(int idDoc,final String codeDoc){
	
//		try {
//			String url = WebViewerUtil.debutURL();
//
//			System.setProperty("RUN_UNDER_ECLIPSE", "true");
//
//			url += "run?__report=";
//
//			Bundle bundleCourant = ArticlesPlugin.getDefault().getBundle();
//			String reportFileLocation = "/report/test_article.rptdesign";
//
//			URL urlReportFile = Platform.asLocalURL(bundleCourant.getEntry(reportFileLocation));
//			URI uriReportFile = new URI("file", urlReportFile.getAuthority(),urlReportFile.getPath(), urlReportFile.getQuery(), null);
//			File reportFile = new File(uriReportFile);
//
//			url += reportFile.getAbsolutePath();
//
//			logger.debug("Report file asLocalURL URL  : " + urlReportFile);
//			logger.debug("Report file asLocalURL File : "+ reportFile.getAbsolutePath());
//			url += "&paramIdArticle=" + idDoc;
//			url += "&paramUrlJDBC="+ IB_APPLICATION.findDatabase().getConnection().getConnectionURL();
//
//			SWTInfoEntreprise infoEntreprise = SWT_IB_TA_INFO_ENTREPRISE.infosEntreprise("1", null);
//			url += "&capital=" + infoEntreprise.getCapitalInfoEntreprise();
//			url += "&ape=" + infoEntreprise.getApeInfoEntreprise();
//			url += "&siret=" + infoEntreprise.getSiretInfoEntreprise();
//			url += "&rcs=" + infoEntreprise.getRcsInfoEntreprise();
//			url += "&nomEntreprise=" + infoEntreprise.getNomInfoEntreprise();
//			url += "&__document=doc"+new Date().getTime();
//
//			url += "&__format=pdf";
//
//			logger.debug("URL edition: " + url);
//			// CustomBrowser birtCustomBrowser = new CustomBrowser();
//			// WebViewer.display("C:/Projet/Java/Eclipse/GestionCommerciale/testBirt/new_report.rptdesign",
//			// WebViewer.HTML,false);
//			// WebViewer.display("C:/Projet/Java/Eclipse/GestionCommerciale/testBirt/tiers.rptdesign",
//			// WebViewer.HTML,false);
//			// org.eclipse.birt.report.viewer.browsers.BrowserManager.getInstance().createBrowser(false).displayURL(url);
//			//url="Http://www.google.com";
//			final String finalURL = url;
//			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
//				public void run() {
//					try {
//						PlatformUI.getWorkbench().getBrowserSupport()
//						.createBrowser(
//								IWorkbenchBrowserSupport.AS_EDITOR, "myId",
//								"Prévisualisation de la facture " + codeDoc,
//								"Prévisualisation de la facture " + codeDoc).openURL(
//										new URL(finalURL));
//						// oldFacture = new OldFacture();//réinitialisation de
//						// cet objet
//					} catch (PartInitException e) {
//						logger.error("", e);
//					} catch (MalformedURLException e) {
//						logger.error("", e);
//					}
//				}
//			});
//			////			
//			////			SWTPaBrowserController.openURL(finalURL, 
//			////					"Prévisualisation de la facture "+ codeFacture, 
//			////					"Prévisualisation de la facture "+ codeFacture);
//
//		} catch (Exception ex) {
//			logger.error(ex);
//		}

	}

	@Override
	protected void actImprimer() throws Exception {
//
//		mapAttributeTablHead.clear();
//		mapAttributeTablDetail.clear();
//
//		setValueMapAttributeTableArticle(TaUserRappeler.class.getSimpleName()+".head");
//		setValueMapAttributeTableArticle(TaUserRappeler.class.getSimpleName()+".detail");
//
//		ViewerPlugin.getDefault( ).getPluginPreferences( ).setValue(WebViewer.APPCONTEXT_EXTENSION_KEY, ArticleAppContext.APP_CONTEXT_NAME);
//		//ViewerPlugin.getDefault( ).getPluginPreferences( ).setValue(WebViewer.APPCONTEXT_EXTENSION_KEY, EditionAppContext.APP_CONTEXT_NAME);
//
//		
//		
//		ImprimeObjet.l.clear();
//		ImprimeObjet.l.add(TaUserRappeler);
//
////		imprimerSimple(TaUserRappeler.getIdArticle(),"ART_1");
//
//
//		/**
//		 * il faut cette modèle pour générer la fichier de PDF  
//		 */
//		/**************************************************************************/
//		//		String repTmp = Const.C_REPERTOIRE_BASE+"/"+Const.C_NOM_PROJET_TMP;
//		//		String nomFichier = "aa.pdf";
//		//		
//		//		AffichageEdition ae = new AffichageEdition();
//		//
//		//		ae.setAppContextEdition(new ArticleAppContext().getExtensionMap());
//		//		
//		//		LinkedList<Integer> l = new LinkedList<Integer>();
//		//		l.add(1);
//		//		ae.genereFichierResultatEdition(repTmp+"/"+nomFichier,
//		//				"/donnees/Projet/Java/Eclipse/GestionCommerciale/Articles/report/test_article.rptdesign",
//		//				new HashMap<String,String>(),null, BirtUtil.PDF_FORMAT, l , "", "");
//		//		ae.afficheFichierResultatEdition("file://"+repTmp+"/"+nomFichier,"aa");
//		/***************************************************************************/
//
//		FonctionGetInfosXml fonctionGetInfosXml = new FonctionGetInfosXml(mapperUIToModel);
//		fonctionGetInfosXml.findInfosFileXml(TaUserRappeler.class.getName(),SWTTaUserRappeler.class.getName(),
//				listeChamp,"mapping");
//		fonctionGetInfosXml.getInfosObjetJPA(TaUserRappeler);
//
//		Impression impression = new Impression();
//
//		/**
//		 * Remplacer par JPA
//		 */
//		SWTInfoEntreprise infoEntreprise = null;
//		infoEntreprise = SWT_IB_TA_INFO_ENTREPRISE.infosEntreprise("1", infoEntreprise);
//
//
//
//		String nomDossier=null;
//		ConstEdition constEdition = new ConstEdition(); 
//		int nombreLine = constEdition.nombreLineTable(tableViewer);
//		/*
//		 * for find id of Article
//		 */
//		constEdition.setMonbreLineTable(nombreLine);
//
//		LinkedList<Integer> idSWTTaUserRappeler = new LinkedList<Integer>();//pour stocker tous les id dans un ecrean
//		LinkedList<Integer> oneIDSWTTaUserRappeler = new LinkedList<Integer>();//pour stocker un id d'une choix
//
//		LinkedList<SWTTaUserRappeler> objectContenuTable =  modelArticle.getListeObjet();
//
//		String[] idDoc = new String[objectContenuTable.size()];
//		int i=0;
//		for (SWTTaUserRappeler article : objectContenuTable) {
//			idDoc[i]=LibConversion.integerToString(article.getIdArticle());
//			i++;
//		}
//
//		Integer oneIDArticle = ((SWTTaUserRappeler)selectedArticle.getValue()).getIdArticle();
//		oneIDSWTTaUserRappeler.add(oneIDArticle);
//
//		if(nombreLine==0){
//			MessageDialog.openWarning(vue.getShell(), ConstEdition.TITRE_MESSAGE_EDITION_VIDE,
//					ConstEdition.EDITION_VIDE);
//		}
//		else{
//			if(infoEntreprise.getIdInfoEntreprise()==null){
//				nomDossier = constEdition.INFOS_VIDE;
//			}
//			else{
//				nomDossier = infoEntreprise.getNomInfoEntreprise();	
//			}
//
//			Iterator key = tableViewer.getListeChampGrille().getKeys();
//			String nameClass = SWTPaArticlesController.class.getSimpleName();
//
//			/**
//			 *  supprimer 
//			 */
//			String sqlQueryStart = "SELECT ";
//			String sqlQueryEnd = " FROM "+Const.C_NOM_VU_ARTICLE;
//			String sqlQueryMiddle = constEdition.addValueList(tableViewer, nameClass);
//
//			ArrayList<String> nameTableEcran = constEdition.getNameTableEcran();
//			ArrayList<String> nameTableBDD = constEdition.getNameTableBDD();
//
//			/**
//			 * supprimer
//			 */
//			String sqlQuery = sqlQueryStart+sqlQueryMiddle+sqlQueryEnd+";";
//			String  C_FICHIER_BDD = Const.C_FICHIER_BDD.replaceFirst(":", "/");
//			String FILE_BDD = Const.C_URL_BDD+"//"+C_FICHIER_BDD;//jdbc:firebirdsql://localhost/C:/runtime-GestionCommercialeComplet.product/dossier/Bd/GEST_COM.FDB
//			//String PATH_FILE_REPORT = "c:/tmp/"+nameTable+".rptdesign";
//			Path pathFileReport = new Path(Const.C_RCP_INSTANCE_LOCATION+"/"+Const.C_NOM_PROJET_TMP+"/"+Const.C_NOM_VU_ARTICLE+".rptdesign");
//			final String PATH_FILE_REPORT = pathFileReport.toPortableString();
//
//
//
//			MakeDynamiqueReport DynamiqueReport = new MakeDynamiqueReport(nameTableEcran,nameTableBDD,
//					sqlQuery/*,ConstEdition.BIRT_HOME*/,PATH_FILE_REPORT,Const.
//					C_DRIVER_JDBC,FILE_BDD,Const.C_USER,Const.C_PASS,
//					Const.C_NOM_VU_ARTICLE,ConstEdition.PAGE_ORIENTATION_PORTRAIT,nomDossier); 
//
//			/**************************************************************/
//			DynamiqueReport.setFonctionGetInfosXml(fonctionGetInfosXml);
//			DynamiqueReport.setNomObjet(TaUserRappeler.class.getSimpleName());
//			/**************************************************************/
//			Map<String, AttributElementResport> attribuGridHeader = new LinkedHashMap<String, AttributElementResport>();
//
//			attribuGridHeader.put(ConstEditionArticle.TITLE_EDITION_ARTICLE, new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
//					ConstEdition.FONT_SIZE_XX_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
//					ConstEdition.COLUMN_DATA_TYPE_STRING, ConstEdition.UNITS_VIDE
//					,""));
//
//			attribuGridHeader.put(ConstEditionArticle.SOUS_TITLE_EDITION_ARTICLE, new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
//					ConstEdition.FONT_SIZE_X_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
//					ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE,
//					ConstEdition.COLOR_GRAY));
//
//			//DynamiqueReport.buildDesignConfig();
//			//ConstEdition.CONTENT_COMMENTS = ConstEditionArticle.COMMENTAIRE_EDITION;
//			ConstEdition.CONTENT_COMMENTS = ConstEditionArticle.COMMENTAIRE_EDITION_DEFAUT;
//			DynamiqueReport.initializeBuildDesignReportConfig();
//			DynamiqueReport.makePageMater("1", "1", "1", "1", "100");
//			DynamiqueReport.makeReportHeaderGrid(3,5,100,ConstEdition.UNITS_PERCENTAGE,attribuGridHeader);
//			//				DynamiqueReport.makeReportTableDB(100,ConstEdition.UNITS_PERCENTAGE,
//			//						Const.C_NOM_VU_ARTICLE,attribuTabHeader,attribuTabDetail,1,1,2,5,"40");
//
//			DynamiqueReport.biuldTableReport("100", ConstEdition.UNITS_PERCENTAGE, 
//					Const.C_NOM_VU_ARTICLE, 1,1,2,"40", mapAttributeTablHead, mapAttributeTablDetail);
//			DynamiqueReport.savsAsDesignHandle();
//			impression.imprimer(idDoc,LibConversion.integerToString(oneIDArticle), true,PATH_FILE_REPORT);
//
//			//				Bundle bundleCourant = ArticlesPlugin.getDefault().getBundle();
//			//				
//			////				String reportFileLocation = ConstEdition.FOLDER_REPORT_PLUGIN;
//			////				
//			////				URL urlReportFile = Platform.asLocalURL(bundleCourant.getEntry(reportFileLocation));
//			////				URI uriReportFile = new URI("file", urlReportFile.getAuthority(),
//			////						urlReportFile.getPath(), urlReportFile.getQuery(), null);
//			////
//			////				File pathReport = new File(uriReportFile);
//			////				
//			////				String pathFileEdition = pathReport.toString()+ConstEditionArticle.SOUS_REPORT_ARTICLE;
//			////				
//			////				File reportFile = new File(pathFileEdition);
//			//				File reportFile = constEdition.findPathReportPlugin(bundleCourant, 
//			//						ConstEdition.FOLDER_REPORT_PLUGIN, ConstEditionArticle.SOUS_REPORT_ARTICLE);
//			//				
//			//				System.out.println(reportFile.toString());
//			//				
//			//				final String namePlugin = bundleCourant.getSymbolicName();//name plugin is <<Articles>>
//			//
//			//
//			//				//Path ChangeFormatPath = new Path(Const.C_REPERTOIRE_BASE+Const.C_NOM_PROJET_TMP);
//			//				//ChangeFormatPath.toPortableString();
//			//				constEdition.makeFolderEditions(Const.C_REPERTOIRE_BASE+
//			//						Const.C_REPERTOIRE_PROJET.replaceFirst("/", "")+
//			//						ConstEdition.FOLDER_EDITION);
//			//				
//			//				String FloderEdition = Const.C_REPERTOIRE_BASE+
//			//				Const.C_REPERTOIRE_PROJET.replaceFirst("/", "")+ConstEdition.FOLDER_EDITION+
//			//				namePlugin;
//			//
//			//				//Path FileReport = new Path(FloderEdition);
//			//
//			//				/*
//			//				 * pour chaque ecrean,creer a floder pour stocker les editions
//			//				 */
//			//				File FloderFileEditions = constEdition.makeFolderEditions(FloderEdition);
//			//				
//			//				String nomOnglet = ConstEdition.EDITION+namePlugin;
//			//				
//			//				Shell dialogShell = new Shell(vue.getShell(),
//			//						//SWT.DIALOG_TRIM |SWT.APPLICATION_MODAL);
//			//						SWT.RESIZE | SWT.CLOSE | SWT.MAX | SWT.APPLICATION_MODAL);
//			//				dialogShell.setText(ConstEdition.TITLE_SHELL);
//			//				dialogShell.setLayout(new FillLayout());
//			//				
//			//				SwtCompositeReport_new dialogReport = new SwtCompositeReport_new(dialogShell,SWT.NULL);
//			//				/*
//			//				 * add un button for ficher d'edition article
//			//				 */
//			//				ConstEdition.NAME_FILE_EDITION = ConstEditionArticle.FICHE_ARTICLE;//on n'utilise pas en ce monent!
//			//				
//			//				constEdition.addValues(idSWTTaUserRappeler,oneIDSWTTaUserRappeler);
//			//				constEdition.setPARAM_ID_TABLE(ConstEditionArticle.PARAM_REPORT_ID_ARTICLE);
//			//				
//			//				
//			//				
//			//				constEdition.openDialogChoixEdition_new(dialogReport, FloderFileEditions, 
//			//				PATH_FILE_REPORT, namePlugin,nomOnglet,dialogShell,reportFile);
//
//		}
//		//		}
//		//		else{
//		//			Table tableArticl = vue.getGrille();
//		//			/*
//		//			 * nom de champ
//		//			 */
//		//			String nameChamp = ibTaTable.champIdTable;
//		//			Integer idArticle = ((SWTTaUserRappeler)selectedArticle.getValue()).getID_ARTICLE();
//		//			String ID_ARTICLE = idArticle.toString();
//		//			Bundle bundleCourant = ArticlesPlugin.getDefault().getBundle();
//		//			String reportFileLocation = "/report/oneArticle.rptdesign";
//		//			URL urlReportFile = Platform.asLocalURL(bundleCourant.getEntry(reportFileLocation));
//		//			URI uriReportFile = new URI("file", urlReportFile.getAuthority(),
//		//					urlReportFile.getPath(), urlReportFile.getQuery(), null);
//		//			File reportFile = new File(uriReportFile);
//		//			AffichageEdition imageEdition = new AffichageEdition();
//		//			imageEdition.showEditionComplex(ID_ARTICLE, "Fiche Article", reportFile);
//		//			MessageDialog.openWarning(vue.getShell(), ConstEdition.TITRE_MESSAGE_EDITION_VIDE,
//		//					ConstEdition.EDITION_NON_USABLE);
//		//			
//		//		}
//
//

	}

	@Override
	protected boolean aideDisponible() {
		boolean result = false;
		switch ((SWTPaUserRappelerController.this.dao.getModeObjet().getMode())) {
		case C_MO_CONSULTATION:
			if(getFocusCourantSWT().equals(vue.getGrilleSource())) 
				result = true;
			break;

		case C_MO_EDITION:
		case C_MO_INSERTION:
//			if(getFocusCourantSWT().equals(vue.getTfCODE_FAMILLE())
//					|| getFocusCourantSWT().equals(vue.getTfCODE_T_TVA())
//					|| getFocusCourantSWT().equals(vue.getTfCODE_TVA())
//					|| getFocusCourantSWT().equals(vue.getTfCODE_UNITE()))
//				result = true;
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
//		boolean aide = getActiveAide();
//		setActiveAide(true);
//		if(aideDisponible()){
//			try {
//				VerrouInterface.setVerrouille(true);
//				vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
//				ParamAfficheAideRechercheSWT paramAfficheAideRecherche = new ParamAfficheAideRechercheSWT();
//				paramAfficheAideRecherche.setMessage(message);
//				// Creation de l'ecran d'aide principal
//				Shell s = new Shell(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), LgrShellUtil.styleLgr);
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
//				switch ((SWTPaRappelerController.this.dao.getModeObjet().getMode())) {
//				case C_MO_CONSULTATION:
//					if(getFocusCourantSWT().equals(vue.getGrille())){
//						PaInscriptionEmailRappeler paArticlesSWT = new PaInscriptionEmailRappeler(s2,SWT.NULL);
//						SWTPaRappelerController swtPaArticlesController = new SWTPaRappelerController(paArticlesSWT);
//
//						editorCreationId = EditorArticle.ID;
//						editorInputCreation = new EditorInputArticle();
//
//						ParamAfficheRappeler ParamAfficheRappeler = new ParamAfficheRappeler();
//						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
//						ParamAfficheRappeler.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//						ParamAfficheRappeler.setEcranAppelant(paAideController);
//						controllerEcranCreation = swtPaArticlesController;
//						parametreEcranCreation = ParamAfficheRappeler;
//
//						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_ARTICLE);
//						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_ARTICLE().getText());
//						paramAfficheAideRecherche.setControllerAppelant(SWTPaRappelerController.this);
//						paramAfficheAideRecherche.setModel(swtPaArticlesController.getModelArticle());
//						paramAfficheAideRecherche.setTypeObjet(swtPaArticlesController.getClassModel());
//						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_ARTICLE);
//					}
//					break;
//
//				case C_MO_EDITION:
//				case C_MO_INSERTION:
//					if(getFocusCourantSWT().equals(vue.getTfCODE_FAMILLE())){
//
//						PaFamilleSWT paFamilleSWT = new PaFamilleSWT(s2,SWT.NULL); 
//						SWTPaFamilleController swtPaFamilleController = new SWTPaFamilleController(paFamilleSWT);
//
//						editorCreationId = EditorFamille.ID;
//						editorInputCreation = new EditorInputFamille();
//
//						ParamAfficheFamille paramAfficheFamille = new ParamAfficheFamille();
//						paramAfficheAideRecherche.setJPQLQuery(new TaFamilleDAO().getJPQLQuery());
//						paramAfficheFamille.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//						paramAfficheFamille.setEcranAppelant(paAideController);
//						/* 
//						 * controllerEcranCreation ne sert plus a rien, pour l'editeur de creation, on creer un nouveau controller
//						 */
//						controllerEcranCreation = swtPaFamilleController;
//						parametreEcranCreation = paramAfficheFamille;
//
//						paramAfficheAideRecherche.setTypeEntite(TaFamille.class);
//						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_FAMILLE);
//						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_FAMILLE().getText());
//						paramAfficheAideRecherche.setControllerAppelant(SWTPaRappelerController.this);
//						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTFamille,TaFamilleDAO,TaFamille>(SWTFamille.class));
//						paramAfficheAideRecherche.setTypeObjet(swtPaFamilleController.getClassModel());
//
//						paramAfficheAideRecherche.setChampsIdentifiant(swtPaFamilleController.getDao().getChampIdTable());
//					}
//					if(getFocusCourantSWT().equals(vue.getTfCODE_T_TVA())){
//						PaTTVASWT paTTvaSWT = new PaTTVASWT(s2,SWT.NULL);
//						SWTPaTTvaController swtPaTTvaController = new SWTPaTTvaController(paTTvaSWT);
//
//						editorCreationId = EditorTypeTVA.ID;
//						editorInputCreation = new EditorInputTypeTVA();
//
//						ParamAfficheTTva paramAfficheTTva = new ParamAfficheTTva();
//						paramAfficheAideRecherche.setJPQLQuery(new TaTTvaDAO().getJPQLQuery());
//						paramAfficheTTva.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//						paramAfficheTTva.setEcranAppelant(paAideController);
//						controllerEcranCreation = swtPaTTvaController;
//						parametreEcranCreation = paramAfficheTTva;
//
//						paramAfficheAideRecherche.setTypeEntite(TaTTva.class);
//						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_T_TVA);
//						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_T_TVA().getText());
//						paramAfficheAideRecherche.setControllerAppelant(SWTPaRappelerController.this);
//						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTTva,TaTTvaDAO,TaTTva>(SWTTTva.class));
//						paramAfficheAideRecherche.setTypeObjet(swtPaTTvaController.getClassModel());
//
//						paramAfficheAideRecherche.setChampsIdentifiant(swtPaTTvaController.getDao().getChampIdTable());
//					}
//					if(getFocusCourantSWT().equals(vue.getTfCODE_TVA())){
//						PaTVASWT paTvaSWT = new PaTVASWT(s2,SWT.NULL);
//						SWTPaTvaController swtPaTvaController = new SWTPaTvaController(paTvaSWT);
//
//						editorCreationId = EditorTva.ID;
//						editorInputCreation = new EditorInputTva();
//
//						ParamAfficheTva paramAfficheTva = new ParamAfficheTva();
//						paramAfficheAideRecherche.setJPQLQuery(new TaTvaDAO().getJPQLQuery());
//						paramAfficheTva.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//						paramAfficheTva.setEcranAppelant(paAideController);
//						controllerEcranCreation = swtPaTvaController;
//						parametreEcranCreation = paramAfficheTva;
//
//						paramAfficheAideRecherche.setTypeEntite(TaTva.class);
//						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_TVA);
//						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_TVA().getText());
//						paramAfficheAideRecherche.setControllerAppelant(SWTPaRappelerController.this);
//						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTva,TaTvaDAO,TaTva>(SWTTva.class));
//						paramAfficheAideRecherche.setTypeObjet(swtPaTvaController.getClassModel());
//
//						paramAfficheAideRecherche.setChampsIdentifiant(swtPaTvaController.getDao().getChampIdTable());
//					}
//					if(getFocusCourantSWT().equals(vue.getTfCODE_UNITE())){
//						PaUniteSWT paUniteSWT = new PaUniteSWT(s2,SWT.NULL);
//						SWTPaUniteController swtPaUniteController = new SWTPaUniteController(paUniteSWT);
//
//						editorCreationId = EditorUnite.ID;
//						editorInputCreation = new EditorInputUnite();
//
//						ParamAfficheUnite paramAfficheUnite = new ParamAfficheUnite();
//						paramAfficheAideRecherche.setJPQLQuery(new TaUniteDAO().getJPQLQuery());
//						paramAfficheUnite.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//						paramAfficheUnite.setEcranAppelant(paAideController);
//						controllerEcranCreation = swtPaUniteController;
//						parametreEcranCreation = paramAfficheUnite;
//
//						paramAfficheAideRecherche.setTypeEntite(TaUnite.class);
//						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_UNITE);
//						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_UNITE().getText());
//						paramAfficheAideRecherche.setControllerAppelant(SWTPaRappelerController.this);
//						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTUnite,TaUniteDAO,TaUnite>(SWTUnite.class));
//						paramAfficheAideRecherche.setTypeObjet(swtPaUniteController.getClassModel());
//
//						paramAfficheAideRecherche.setChampsIdentifiant(swtPaUniteController.getDao().getChampIdTable());
//					}
//					break;
//				default:
//					break;
//				}
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
//					paAideController.addRetourEcranListener(SWTPaRappelerController.this);
//					Control focus = vue.getShell().getDisplay().getFocusControl();
//					// affichage de l'ecran d'aide principal (+ ses recherches)
//
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
//		}
	}

	protected void initImageBouton() {
		super.initImageBouton();
////		String C_IMAGE_AJOUT_PRIX = "/icons/money.png";
////		if(vue instanceof PaInscriptionEmailRappeler) {
////			((PaInscriptionEmailRappeler)vue).getBtnAjoutPrix().setImage(ArticlesPlugin.getImageDescriptor(C_IMAGE_AJOUT_PRIX).createImage());
////			vue.layout(true);
////		}
	}

	public IStatus validateUI() throws Exception {
		if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
				|| (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
			ctrlTousLesChampsAvantEnregistrementSWT();
			mapperUIToModel.map((SWTTaUserRappeler) selectedListUserRappeler.getValue(),TaUserRappeler);
		}
		return null;
	}

	public IStatus validateUIField(String nomChamp,Object value) {
		String validationContext = "EMAIL_RAPPELER";
		try {
			
		
				
			
					
//			IStatus s = null;
//
//			if(nomChamp.equals(Const.C_CODE_FAMILLE)) {
//				TaFamilleDAO dao = new TaFamilleDAO();
//
//				dao.setModeObjet(getDao().getModeObjet());
//				TaFamille f = new TaFamille();
//				PropertyUtils.setSimpleProperty(f, nomChamp, value);
//				s = dao.validate(f,nomChamp,validationContext);
//
//				if(s.getSeverity()!=IStatus.ERROR ){
//					f = dao.findByCode((String)value);
//					TaUserRappeler.setTaFamille(f);
//				}
//				dao = null;
//			} else if(nomChamp.equals(Const.C_CODE_TVA)) {
//				TaTvaDAO daoTva = new TaTvaDAO();
//				daoTva.setModeObjet(getDao().getModeObjet());
//				TaTva f = new TaTva();
//
//				PropertyUtils.setSimpleProperty(f, nomChamp, value);
//				s = daoTva.validate(f,nomChamp,validationContext);
//
//				if(s.getSeverity()!=IStatus.ERROR){
//					f = daoTva.findByCode((String)value);
//					TaUserRappeler.setTaTva(f);
//					dao.initCodeTTva(TaUserRappeler);
//					dao.initPrixTTC(TaUserRappeler);
//					if(TaUserRappeler.getTaTTva()!=null)
//						((SWTTaUserRappeler) selectedArticle.getValue()).setCodeTTva(TaUserRappeler.getTaTTva().getCodeTTva());
//					else ((SWTTaUserRappeler) selectedArticle.getValue()).setCodeTTva(null);
//				}
//				daoTva = null;
//			} else if(nomChamp.equals(Const.C_CODE_T_TVA)) {
//				TaTTvaDAO daoTTva = new TaTTvaDAO();
//				daoTTva.setModeObjet(getDao().getModeObjet());
//				TaTTva f = new TaTTva();
//				PropertyUtils.setSimpleProperty(f, nomChamp, value);
//				s = daoTTva.validate(f,nomChamp,validationContext);
//
//				if(s.getSeverity()!=IStatus.ERROR){
//					f = daoTTva.findByCode((String)value);
//					TaUserRappeler.setTaTTva(f);
//					dao.initCodeTTva(TaUserRappeler);
//					if(TaUserRappeler.getTaTTva()!=null)
//						((SWTTaUserRappeler) selectedArticle.getValue()).setCodeTTva(TaUserRappeler.getTaTTva().getCodeTTva());
//					else ((SWTTaUserRappeler) selectedArticle.getValue()).setCodeTTva(null);
//				}
//				daoTTva = null;
//			} else if(nomChamp.equals(Const.C_CODE_UNITE)) {
//				TaUniteDAO dao = new TaUniteDAO();
//				dao.setModeObjet(getDao().getModeObjet());
//				TaUnite f = new TaUnite();
//				PropertyUtils.setSimpleProperty(f, nomChamp, value);
//				s = dao.validate(f,nomChamp,validationContext);
//
//				if(s.getSeverity()!=IStatus.ERROR){
//					f = dao.findByCode((String)value);
//					if (!value.equals("")){
//						initPrixArticle();
//						TaUserRappeler.getTaPrix().setTaUnite(f);
//					}
//				}
//				dao = null;
//			} else if(nomChamp.equals(Const.C_PRIX_PRIX)) {
//				TaPrixDAO dao = new TaPrixDAO();
//				if(value!=null && !value.equals(""))initPrixArticle();
//				dao.setModeObjet(getDao().getModeObjet());
//				TaPrix f = new TaPrix();
//				f.setTaUserRappeler(TaUserRappeler);
//				PropertyUtils.setSimpleProperty(f, nomChamp, value);
//				int change =0;
//				if(value!=null && ((SWTTaUserRappeler) selectedArticle.getValue()).getPrixPrix()!=null)
//					change =f.getPrixPrix().compareTo(((SWTTaUserRappeler) selectedArticle.getValue()).getPrixPrix());
//				else change=-1;
//				f.setPrixttcPrix(((SWTTaUserRappeler) selectedArticle.getValue()).getPrixttcPrix());				
//				s = dao.validate(f,nomChamp,validationContext);
//				if(s.getSeverity()!=IStatus.ERROR && (change!=0||f.getPrixPrix().compareTo(new BigDecimal(0))==0)
//				) {					
//					((SWTTaUserRappeler) selectedArticle.getValue()).setPrixPrix(f.getPrixPrix());				
//					((SWTTaUserRappeler) selectedArticle.getValue()).setPrixttcPrix(f.getPrixttcPrix());	  
//				}
//				dao = null;
//			}else if(nomChamp.equals(Const.C_PRIXTTC_PRIX)) {
//				TaPrixDAO dao = new TaPrixDAO();
//				if(value!=null && !value.equals(""))initPrixArticle();
//				dao.setModeObjet(getDao().getModeObjet());
//				TaPrix f = new TaPrix();
//				f.setTaUserRappeler(TaUserRappeler);
//				PropertyUtils.setSimpleProperty(f, nomChamp, value);
//				int change =0;
//				if(value!=null && ((SWTTaUserRappeler) selectedArticle.getValue()).getPrixttcPrix()!=null)
//					f.getPrixttcPrix().compareTo(((SWTTaUserRappeler) selectedArticle.getValue()).getPrixttcPrix());
//				else change=-1;
//				f.setPrixPrix(((SWTTaUserRappeler) selectedArticle.getValue()).getPrixPrix());
//				s = dao.validate(f,nomChamp,validationContext);
//				if(s.getSeverity()!=IStatus.ERROR
//						&& (change!=0||f.getPrixttcPrix().compareTo(new BigDecimal(0))==0)
//				) {									
//					((SWTTaUserRappeler) selectedArticle.getValue()).setPrixPrix(f.getPrixPrix());				
//					((SWTTaUserRappeler) selectedArticle.getValue()).setPrixttcPrix(f.getPrixttcPrix());				  
//				}
//				dao = null;
//			} else {
//				TaUserRappeler u = new TaUserRappeler();
//				PropertyUtils.setSimpleProperty(u, nomChamp, value);
//				if(((SWTTaUserRappeler) selectedArticle.getValue()).getIdArticle()!=null) {
//					u.setIdArticle(((SWTTaUserRappeler) selectedArticle.getValue()).getIdArticle());
//				}
//
//				s = dao.validate(u,nomChamp,validationContext);
//			}
//			return s;
			
			
			
			
			//nomChamp.equals(Const.C_NEW_MAIL));
			IStatus s = null;
			
			
			TaUserRappeler u = new TaUserRappeler();
			PropertyUtils.setSimpleProperty(u, nomChamp, value);
			if(((SWTTaUserRappeler) selectedListUserRappeler.getValue()).getIdUserRappeler()!=null) {
				u.setIdUserRappeler(((SWTTaUserRappeler) selectedListUserRappeler.getValue()).getIdUserRappeler());
			}
			
			s = dao.validate(u,nomChamp,validationContext);
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
					mapperUIToModel.map((SWTTaUserRappeler) selectedListUserRappeler.getValue(),TaUserRappeler);
				}
				if((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0))	
					TaUserRappeler=dao.enregistrerMerge(TaUserRappeler);
				else TaUserRappeler=dao.enregistrerMerge(TaUserRappeler);

				dao.commit(transaction);
				transaction = null;


				actRefresh(); //deja present

			}
		} catch (Exception e) {
			logger.error("",e);
			if(transaction!=null) {
				transaction.rollback();
			}
		} finally {
			initEtatBouton();
		}
	}



	public void initEtatComposant() {
//		try {
//			vue.getTfCODE_ARTICLE().setEditable(!isUtilise());
//			changeCouleur(vue);
//		} catch (Exception e) {
//			vue.getLaMessage().setText(e.getMessage());
//		}
	}
	public boolean isUtilise(){
		return ((SWTTaUserRappeler)selectedListUserRappeler.getValue()).getIdUserRappeler()!=null && 
		!dao.recordModifiable(dao.getNomTable(),
				((SWTTaUserRappeler)selectedListUserRappeler.getValue()).getIdUserRappeler());		
	}
	public SWTTaUserRappeler getSwtOldUserRappeler() {
		return swtOldUserRappeler;
	}

	public void setSwtOldUserRappeler(SWTTaUserRappeler swtOldArticle) {
		this.swtOldUserRappeler = swtOldArticle;
	}

	public void setSwtOldUserRappeler() {
		if (selectedListUserRappeler.getValue() != null){}
		//	this.swtOldUserRappeler = SWTTaUserRappeler.copy((SWTTaUserRappeler) selectedListUserRappeler.getValue());
		else {
			if(tableViewer.selectionGrille(0)){
				this.swtOldUserRappeler = SWTTaUserRappeler.copy((SWTTaUserRappeler) selectedListUserRappeler.getValue());
				tableViewer.setSelection(new StructuredSelection(
						(SWTTaUserRappeler) selectedListUserRappeler.getValue()), true);
			}}
	}

	public void setVue(PaUserRappeler vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();

		mapComposantDecoratedField.put(vue.getNewMail(), vue.getFieldNewMail());
		mapComposantDecoratedField.put(vue.getNewName(), vue.getFieldNewName());


		
		
	}

	protected class HandlerAjoutPrix extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException{
			try {
				//				LgrPartListener.getLgrPartListener().setLgrActivePart(null);
				//				IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new EditorInputPrix(), EditorPrix.ID);
				//				LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
				//				LgrPartListener.getLgrPartListener().setLgrActivePart(e);
				//				//SWTPaPrixController swtPaPrixController = new SWTPaPrixController(((EditorPrix)e).getComposite());
				//				
				//				ParamAffichePrix paramAffichePrix = new ParamAffichePrix();
				//				paramAffichePrix.setIdArticle(LibConversion.integerToString(((SWTTaUserRappeler)selectedArticle.getValue()).getIdArticle()));
				//				((JPALgrEditorPart)e).setPanel(((JPALgrEditorPart)e).getController().getVue());
				//				((JPALgrEditorPart)e).getController().configPanel(paramAffichePrix);
				MessageDialog.openInformation(vue.getShell(), "Avertissement",
				"Non implémentée");
			} catch (Exception e1) {
				logger.error("Erreur : actionPerformed", e1);
			}
			return event;
		}
	}

	protected void actSelection() throws Exception {
		
	}



	class ActionAjoutPrix extends Action {
		public ActionAjoutPrix(String name) {
			super(name);
		}

		public void run() {
			super.run();
			try {
				handlerService.executeCommand(C_COMMAND_PRIX_ID, null);
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

	public Class getClassModel() {
		return classModel;
	}

	@Override
	protected void sortieChamps() {
		// TODO Raccord de methode auto-genere

		try {
			if(getActiveAide()) {
				if(LgrPartListener.getLgrPartListener().getLgrActivePart()!=null)
					LgrPartListener.getLgrPartListener().getLgrActivePart().setFocus();
			}
		} catch (Exception e) {
			logger.error("",e);
		}
	}

	@Override
	protected void actRefresh() throws Exception {
		if (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION) == 0) {
			//rafraichissement des valeurs dans la grille
			writableList = new WritableList(realm, modelListUserRappeler.remplirListe(), classModel);
			tableViewer.setInput(writableList);
			//tableViewer.refresh();
		} else {
			if (TaUserRappeler!=null && selectedListUserRappeler.getValue()!=null && (SWTTaUserRappeler) selectedListUserRappeler.getValue()!=null) {
				mapperModelToUI.map(TaUserRappeler, (SWTTaUserRappeler) selectedListUserRappeler.getValue());
			}
		}
	
		//repositionnement sur la valeur courante
		int idActuel = 0;
		if (TaUserRappeler!=null) { //enregistrement en cours de modification/insertion
			idActuel = TaUserRappeler.getIdUserRappeler();
		} else if(selectedListUserRappeler.getValue()!=null && (SWTTaUserRappeler) selectedListUserRappeler.getValue()!=null) {
			idActuel = ((SWTTaUserRappeler) selectedListUserRappeler.getValue()).getIdUserRappeler();
		}

		if(idActuel!=0) {
			tableViewer.setSelection(new StructuredSelection(modelListUserRappeler.recherche(Const.C_ID_USER_RAPPELER
					, idActuel)));
		}else
			tableViewer.selectionGrille(0);				
	}

	

//	public ModelGeneralObjet<SWTTaUserRappeler,TaUserRappelerDAO,TaUserRappeler>  getModelArticle() {
//		return modelArticle;
//	}
//
//	public SWTTaUserRappeler getSWTTaUserRappeler() {
//		return SWTTaUserRappeler;
//	}
//
//	public TaUserRappeler getTaUserRappeler() {
//		return TaUserRappeler;
//	}
//
//	public TaUserRappelerDAO getDao() {
//		return dao;
//	}
//
//	public boolean changementPageValide(){		
//		return (daoStandard.selectAll().size()>0);
//	}
//
//	
//}


}
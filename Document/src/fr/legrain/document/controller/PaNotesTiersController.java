package fr.legrain.document.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
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
import org.eclipse.swt.widgets.Menu;

import fr.legrain.document.ecran.MessagesEcran;
import fr.legrain.document.ecran.PaNotesTiers;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.edition.actions.AttributElementResport;
import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.edition.actions.MakeDynamiqueReport;
import fr.legrain.edition.dynamique.FonctionGetInfosXmlAndProperties;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Tiers.SWTNoteTiers;
import fr.legrain.gestCom.librairiesEcran.swt.DeclencheCommandeControllerEvent;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.workbench.AnnuleToutEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDePageEvent;
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
import fr.legrain.lib.gui.UpdateMasterEntityEvent;
import fr.legrain.lib.gui.UpdateMasterEntityListener;
import fr.legrain.lib.gui.aide.ResultAide;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.lib.gui.grille.LgrViewerSupport;
import fr.legrain.libMessageLGR.LgrMess;
import fr.legrain.tiers.dao.TaNoteTiers;
import fr.legrain.tiers.dao.TaNoteTiersDAO;
import fr.legrain.tiers.dao.TaTNoteTiers;
import fr.legrain.tiers.dao.TaTNoteTiersDAO;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.divers.Impression;
import fr.legrain.tiers.ecran.ConstEditionTiers;
import fr.legrain.tiers.ecran.ParamAfficheNoteTiers;

public class PaNotesTiersController extends JPABaseControllerSWTStandard implements 
RetourEcranListener, UpdateMasterEntityListener {
	static Logger logger = Logger.getLogger(PaNotesTiersController.class
			.getName());
	
	private PaNotesTiers vue = null;
	private TaNoteTiersDAO dao = null;

	private Object ecranAppelant = null;
	private SWTNoteTiers SWTNote;
	private SWTNoteTiers swtOldNote;
	private Realm realm;
	private DataBindingContext dbc;

	private Class classModel = SWTNoteTiers.class;
	//private ModelGeneral<SWTNote> modelWeb = new ModelGeneral<SWTNote>(ibTaTable.getFIBQuery(),classModel);
	private ModelGeneralObjet<SWTNoteTiers,TaNoteTiersDAO,TaNoteTiers> modelNote = null;//new ModelGeneralObjet<SWTNote,TaNoteDAO,TaNote>(dao,classModel);

//	public static final String C_COMMAND_OPEN_URL_ID = "fr.legrain.gestionCommerciale.tiers.web.openurl";
//	private HandlerOpenUrl handlerOpenUrl = new HandlerOpenUrl();

	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private IObservableValue selectedNote;
	private String[] listeChamp;
	private String nomClassController = this.getClass().getSimpleName();

	private TaTiers masterEntity = null;
	private TaTiersDAO masterDAO = null;

	private LgrDozerMapper<SWTNoteTiers,TaNoteTiers> mapperUIToModel  = new LgrDozerMapper<SWTNoteTiers,TaNoteTiers>();
	private TaNoteTiers taNote = null;

	private MapChangeListener changeListener = new MapChangeListener();

	private Integer idTiers = null;
	
//	private boolean notify = true;
	
	private List<String> listeFiltreCodeTypeNoteTiers = null;

	public PaNotesTiersController(PaNotesTiers vue) {
		this(vue,null);
	}

	public PaNotesTiersController(PaNotesTiers vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaNoteTiersDAO(getEm());
		modelNote = new ModelGeneralObjet<SWTNoteTiers,TaNoteTiersDAO,TaNoteTiers>(dao,classModel);
		setVue(vue);

		// a faire avant l'initialisation du Binding
		vue.getGrille().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setSwtOldNote();
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
//			vue.getPaCorpsFormulaire().setMenu(popupMenuFormulaire);
//			vue.getPaGrille().setMenu(popupMenuGrille);

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
	public List<SWTNoteTiers> IHMmodel() {
		LinkedList<TaNoteTiers> ldao = new LinkedList<TaNoteTiers>();
		LinkedList<SWTNoteTiers> lswt = new LinkedList<SWTNoteTiers>();

		if(masterEntity!=null && !masterEntity.getTaNotes().isEmpty()) {

			if(listeFiltreCodeTypeNoteTiers==null) {
				//pas de filtre, on affiche tout
				ldao.addAll(masterEntity.getTaNotes());
			} else {
				for (TaNoteTiers taNote : masterEntity.getTaNotes()) {
					if(listeFiltreCodeTypeNoteTiers.contains(taNote.getTaTNoteTiers().getCodeTNoteTiers())) {
						ldao.add(taNote);
					}
				}
			}

			LgrDozerMapper<TaNoteTiers,SWTNoteTiers> mapper = new LgrDozerMapper<TaNoteTiers,SWTNoteTiers>();
			for (TaNoteTiers o : ldao) {
				SWTNoteTiers t = null;
				t = (SWTNoteTiers) mapper.map(o, SWTNoteTiers.class);
				lswt.add(t);
			}

		}
		
		return lswt;
	}
	
//	public void notifier() {
//		if(notify) {
//			if(masterEntity!=null && masterEntity.getTaNotes().size()>0) {
////				NotifierDialog.notify("Bureau de gestion", masterEntity.getTaNotes().size()+" note(s) disponible(s) pour ce tiers", NotificationType.INFO);
//			}
//		}
//	}

	public void bind(PaNotesTiers vue) {
		try {
			//modelWeb = new ModelTypeTiers(ibTaTable);
			//			 modelWeb = new ModelGeneral<SWTNote>(ibTaTable.getFIBQuery(),classModel);
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			tableViewer = new LgrTableViewer(vue.getGrille());
			tableViewer.createTableCol(classModel,vue.getGrille(), nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE,0);
			listeChamp = tableViewer.setListeChampGrille(nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE);

			// Set up data binding.
			LgrViewerSupport.bind(tableViewer, 
					new WritableList(IHMmodel(), classModel),
					BeanProperties.values(listeChamp)
			);

			selectedNote = ViewersObservables.observeSingleSelection(tableViewer);

			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			tableViewer.selectionGrille(0);

			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);

			selectedNote.getValue();
			bindingFormMaitreDetail(dbc, realm, selectedNote,classModel);
			changementDeSelection();
			selectedNote.addChangeListener(new IChangeListener() {

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
		if(selectedNote!=null && selectedNote.getValue()!=null) {
			if(((SWTNoteTiers) selectedNote.getValue()).getIdNoteTiers()!=null) {
				taNote = dao.findById(((SWTNoteTiers) selectedNote.getValue()).getIdNoteTiers());
			}
			//null a tester ailleurs, car peut etre null en cas d'insertion
			fireChangementDeSelection(new ChangementDeSelectionEvent(PaNotesTiersController.this));
		}
	}

	public Composite getVue() {
		return vue;
	}

	public ResultAffiche configPanel(ParamAffiche param) {
		if (param != null) {
			//Map map=ibTaTable.getParamWhereSQL();
			Map<String,String[]> map = dao.getParamWhereSQL();
			//ibTaTable.ouvreDataset();
			if (((ParamAfficheNoteTiers) param).getFocusDefautSWT() != null && !((ParamAfficheNoteTiers) param).getFocusDefautSWT().isDisposed())
				((ParamAfficheNoteTiers) param).getFocusDefautSWT().setFocus();
			else
				((ParamAfficheNoteTiers) param).setFocusDefautSWT(vue.getGrille());
			
			if(((ParamAfficheNoteTiers) param).getListeTypeNoteAAfficher()!=null)
				listeFiltreCodeTypeNoteTiers = ((ParamAfficheNoteTiers) param).getListeTypeNoteAAfficher();
//			vue.getLaTitreFormulaire().setText(
//					((ParamAfficheNoteTiers) param).getTitreFormulaire());
//			vue.getLaTitreGrille().setText(
//					((ParamAfficheNoteTiers) param).getTitreGrille());
//			vue.getLaTitreFenetre().setText(
//					((ParamAfficheNoteTiers) param).getSousTitre());


			if (param.getEcranAppelant() != null) {
				ecranAppelant = param.getEcranAppelant();
			}

			if(tableViewer==null) {
				//databinding pas encore realise
				bind(vue);
				tableViewer.tri(classModel, nomClassController,	Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			} else {
				try {
					taNote=null;
					selectedNote.setValue(null);
					actRefresh();
				} catch (Exception e) {
					logger.error("",e);
				}
			}

			//			tableViewer.tri(classModel, nomClassController,	Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			VerrouInterface.setVerrouille(false);
			setSwtOldNote();

			if (param.getModeEcran() != null
					&& param.getModeEcran().compareTo(EnumModeObjet.C_MO_INSERTION) == 0) {
				try {
					actInserer();
				} catch (Exception e) {
					vue.getLaMessage().setText(e.getMessage());
					logger.error("", e);
				}
			} else {
				initEtatBouton();
			}

			//param.setFocus(ibTaTable.getFModeObjet().getFocusCourant());
		}
		return null;
	}
	
	protected void initVerifySaisie() {
		if (mapInfosVerifSaisie == null)
			mapInfosVerifSaisie = new HashMap<Control, InfosVerifSaisie>();
		
//		mapInfosVerifSaisie.put(vue.getTfNOTE_TIERS(), new InfosVerifSaisie(new TaNoteTiers(),Const.C_NOTE_TIERS,null));
//		mapInfosVerifSaisie.put(vue.getTfCODE_T_NOTE_TIERS(), new InfosVerifSaisie(new TaTNoteTiers(),Const.C_CODE_T_NOTE_TIERS,null));

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

//		vue.getTfNOTE_TIERS().setToolTipText(Const.C_NOTE_TIERS);
//		vue.getTfCODE_T_NOTE_TIERS().setToolTipText(Const.C_CODE_T_NOTE_TIERS);
//		vue.getDateTimeNOTE_TIERS().setToolTipText(Const.C_DATE_NOTE_TIERS);

//		mapComposantChamps.put(vue.getTfNOTE_TIERS(),Const.C_NOTE_TIERS);
//		mapComposantChamps.put(vue.getTfCODE_T_NOTE_TIERS(),Const.C_CODE_T_NOTE_TIERS);
//		mapComposantChamps.put(vue.getDateTimeNOTE_TIERS(),Const.C_DATE_NOTE_TIERS);

		for (Control c : mapComposantChamps.keySet()) {
			listeComposantFocusable.add(c);
		}

//		listeComposantFocusable.add(vue.getBtnOpenUrl());

//		listeComposantFocusable.add(vue.getBtnEnregistrer());
//		listeComposantFocusable.add(vue.getBtnInserer());
//		listeComposantFocusable.add(vue.getBtnModifier());
//		listeComposantFocusable.add(vue.getBtnSupprimer());
//		listeComposantFocusable.add(vue.getBtnFermer());
//		listeComposantFocusable.add(vue.getBtnAnnuler());
//		listeComposantFocusable.add(vue.getBtnImprimer());

		if (mapInitFocus == null)
			mapInitFocus = new LinkedHashMap<ModeObjet.EnumModeObjet, Control>();
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION, vue
				.getGrille());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue
				.getGrille());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION, vue
				.getGrille());

		activeModifytListener();
	}

	protected void initActions() {
		mapCommand = new HashMap<String, IHandler>();

//		mapCommand.put(C_COMMAND_OPEN_URL_ID, handlerOpenUrl);

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

//		mapActions.put(vue.getBtnOpenUrl(), C_COMMAND_OPEN_URL_ID);

//		mapActions.put(vue.getBtnAnnuler(), C_COMMAND_GLOBAL_ANNULER_ID);
//		mapActions.put(vue.getBtnEnregistrer(), C_COMMAND_GLOBAL_ENREGISTRER_ID);
//		mapActions.put(vue.getBtnInserer(), C_COMMAND_GLOBAL_INSERER_ID);
//		mapActions.put(vue.getBtnModifier(), C_COMMAND_GLOBAL_MODIFIER_ID);
//		mapActions.put(vue.getBtnSupprimer(), C_COMMAND_GLOBAL_SUPPRIMER_ID);
//		mapActions.put(vue.getBtnFermer(), C_COMMAND_GLOBAL_FERMER_ID);
//		mapActions.put(vue.getBtnImprimer(), C_COMMAND_GLOBAL_IMPRIMER_ID);

		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID, C_COMMAND_GLOBAL_REFRESH_ID };
		mapActions.put(null, tabActionsAutres);
	}

	public PaNotesTiersController getThis() {
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
					.getString("NoteTiers.Message.Enregistrer"))) {

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
				setListeEntity(getModelNote().remplirListe());
				dao.initValeurIdTable(taNote);
				fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
						dao.getChampIdEntite(), dao.getValeurIdTable(),
						selectedNote.getValue())));

				retour = true;
			}
		}
		return retour;
	}

	public void retourEcran(final RetourEcranEvent evt) {
//		if (evt.getRetour() != null
//				&& (evt.getSource() instanceof SWTPaAideControllerSWT)) {
//			if (getFocusAvantAideSWT() instanceof Text) {
//				//				try {
//				//					((Text) getFocusAvantAideSWT()).setText(((ResultAffiche) evt.getRetour()).getResult());					
//				//					ctrlUnChampsSWT(getFocusAvantAideSWT());
//				//				} catch (Exception e) {
//				//					logger.error("",e);
//				//					vue.getLaMessage().setText(e.getMessage());
//				//				}
//				try {
//					((Text) getFocusAvantAideSWT()).setText(((ResultAffiche) evt.getRetour()).getResult());	
//					if(getFocusAvantAideSWT().equals(vue.getTfCODE_T_NOTE_TIERS())){
//						TaTNoteTiers u = null;
//						TaTNoteTiersDAO t = new TaTNoteTiersDAO(getEm());
//						u = t.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
//						t = null;
//						taNote.setTaTNoteTiers(u);
//					}
//					ctrlUnChampsSWT(getFocusAvantAideSWT());
//				} catch (Exception e) {
//					logger.error("",e);
//					vue.getLaMessage().setText(e.getMessage());
//				}
//			}
//			if (getFocusAvantAideSWT() instanceof Table) {
//				if (getFocusAvantAideSWT() == vue.getGrille()) {
//					//ibTaTable.lgrLocateID(((ResultAffiche) evt.getRetour()).getIdResult());
//					if(((ResultAffiche) evt.getRetour()).getSelection()!=null)
//						tableViewer.setSelection(((ResultAffiche) evt.getRetour()).getSelection(),true);
//				}
//			}
//		} else if (evt.getRetour() != null) {
//			if (getFocusAvantAideSWT() instanceof Table) {
//				if (getFocusAvantAideSWT() == vue.getGrille()) {
//					//ibTaTable.lgrLocateID(((ResultAffiche) evt.getRetour()).getIdResult());
//					if(((ResultAffiche) evt.getRetour()).getSelection()!=null)
//						tableViewer.setSelection(((ResultAffiche) evt.getRetour()).getSelection(),true);
//				}
//			}
//		}
		super.retourEcran(evt);
	}

//	protected class HandlerOpenUrl extends LgrAbstractHandler {
//
//		public Object execute(ExecutionEvent event) throws ExecutionException{
//			try {
//				actOpenUrl();
//			} catch (Exception e1) {
//				logger.error("Erreur : actionPerformed", e1);
//			}
//			return event;
//		}
//	}
//
//	protected void actOpenUrl() {
//		if(taNote!=null && taNote.getAdresseWeb()!=null) {
//			String defaultProtocol = "http://";
//			String url = taNote.getAdresseWeb();
//			if(!url.startsWith(defaultProtocol)) {
//				url = defaultProtocol+url;
//			}
//			final String finalURL = url;
//			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
//				public void run() {
//					try {
//						PlatformUI.getWorkbench().getBrowserSupport()
//						.createBrowser(
//								IWorkbenchBrowserSupport.AS_EDITOR,
//								"myId",
//								finalURL,
//								finalURL
//						).openURL(new URL(finalURL));
//
//					} catch (PartInitException e) {
//						logger.error("",e);
//					} catch (MalformedURLException e) {
//						logger.error("",e);
//
//					}
//				}	
//			});
//		}
//	}

	@Override
	protected void actInserer() throws Exception {
		try {
			boolean continuer=true;
			VerrouInterface.setVerrouille(true);
			masterDAO.verifAutoriseModification(masterEntity);
			setSwtOldNote();
			if(LgrMess.isDOSSIER_EN_RESEAU()){
				DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
				fireDeclencheCommandeController(e);
				continuer=masterDAO.dataSetEnModif();
			}
			if(continuer){
				SWTNote = new SWTNoteTiers();
				SWTNote.setIdTiers(idTiers);
				dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_INSERTION);
				taNote = new TaNoteTiers();
				List l = IHMmodel();
				l.add(SWTNote);
				writableList = new WritableList(realm, l, classModel);
				tableViewer.setInput(writableList);
				tableViewer.refresh();
				tableViewer.setSelection(new StructuredSelection(SWTNote));
				initEtatBouton();

				try {
					DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
					fireDeclencheCommandeController(e);
				} catch (Exception e) {
					logger.error("",e);
				}
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
			boolean continuer=true;
			setSwtOldNote();
			if(LgrMess.isDOSSIER_EN_RESEAU()){
				DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
				fireDeclencheCommandeController(e);
				continuer=masterDAO.dataSetEnModif();
			}
			if(continuer){
				for (TaNoteTiers p : masterEntity.getTaNotes()) {
					if(p.getIdNoteTiers()==((SWTNoteTiers) selectedNote.getValue()).getIdNoteTiers()) {
						taNote = p;
//						if(LgrMess.isDOSSIER_EN_RESEAU())actRefresh();
					}
				}
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
							.getString("NoteTiers.Message.Supprimer"))) {				
						dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_SUPPRESSION);
						try {
							DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
							fireDeclencheCommandeController(e);
						} catch (Exception e) {
							logger.error("",e);
						}
						for (TaNoteTiers p : masterEntity.getTaNotes()) {
							if(p.getIdNoteTiers()==((SWTNoteTiers) selectedNote.getValue()).getIdNoteTiers()) {
								taNote = p;
							}
						}

						dao.begin(transaction);
						dao.supprimer(taNote);
						taNote.setTaTiers(null);
						masterEntity.removeNote(taNote);
						Object suivant=tableViewer.getElementAt(tableViewer.getTable().getSelectionIndex()+1);
						//taNote=masterEntity.getTaNote();
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
						initEtatBouton();

					}
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
						.getString("NoteTiers.Message.Annuler")))) {
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
						.getString("NoteTiers.Message.Annuler")))) {
					//int rang = getGrille().getSelectionIndex();
					//int rang = modelWeb.getListeObjet().indexOf(selectedWeb.getValue());
					int rang =((WritableList)tableViewer.getInput()).indexOf(selectedNote.getValue());
					remetTousLesChampsApresAnnulationSWT(dbc);
					List<SWTNoteTiers> l = IHMmodel();
					if(rang!=-1)
						l.set(rang, swtOldNote);

					writableList = new WritableList(realm, l, classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.setSelection(new StructuredSelection(swtOldNote), true);

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


		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();

		String nomChampIdTable =  dao.getChampIdTable();

		FonctionGetInfosXmlAndProperties fonctionGetInfosXmlAndProperties = new FonctionGetInfosXmlAndProperties(mapperUIToModel);
		fonctionGetInfosXmlAndProperties.cleanValueMapAttributeTable();

		fonctionGetInfosXmlAndProperties.setValueMapAttributeTable(TaNoteTiers.class.getSimpleName()+".head");
		fonctionGetInfosXmlAndProperties.setValueMapAttributeTable(TaNoteTiers.class.getSimpleName()+".detail");

		LinkedHashMap<String,AttributElementResport> mapAttributeTablHead = fonctionGetInfosXmlAndProperties.getMapAttributeTablHead(); 
		LinkedHashMap<String,AttributElementResport> mapAttributeTablDetail = fonctionGetInfosXmlAndProperties.getMapAttributeTablDetail();


		Collection<TaNoteTiers> collectionTaNote = masterEntity.getTaNotes();

		fonctionGetInfosXmlAndProperties.findInfosFileXml(TaNoteTiers.class.getName(),SWTNoteTiers.class.getName(),
				listeChamp,"mapping");
		fonctionGetInfosXmlAndProperties.getInfosObjetJPA(taNote);

		ConstEdition constEdition = new ConstEdition(); 
		Impression impression = new Impression(constEdition,taNote,collectionTaNote,nomChampIdTable,taNote.getIdNoteTiers());
		String nomDossier = null;

		int nombreLine = collectionTaNote.size();


		if(nombreLine==0){
			MessageDialog.openError(vue.getShell(), "ALTER",
			"Il n'y a rien dans Cette Edition !");
		}
		else{
			if(taInfoEntreprise.getIdInfoEntreprise()==0){
				nomDossier = ConstEdition.INFOS_VIDE;
			}
			else{
				nomDossier = taInfoEntreprise.getNomInfoEntreprise();	
			}

			constEdition.addValueList(tableViewer, nomClassController);


			/**
			 * pathFileReport ==> le path de ficher de edition dynamique
			 */
			String folderEditionDynamique = Const.C_RCP_INSTANCE_LOCATION+"/"+Const.C_NOM_PROJET_TMP+"/"+TaNoteTiers.class.getSimpleName();
			constEdition.makeFolderEditions(folderEditionDynamique);
			Path pathFileReport = new Path(folderEditionDynamique+"/"+Const.C_NOM_VU_NOTE_TIERS+".rptdesign");
			final String pathFileReportDynamic = pathFileReport.toPortableString();

			MakeDynamiqueReport DynamiqueReport = new MakeDynamiqueReport(constEdition.getNameTableEcran(),
					constEdition.getNameTableBDD(),pathFileReportDynamic,Const.C_NOM_VU_NOTE_TIERS,
					ConstEdition.PAGE_ORIENTATION_LANDSCAPE,nomDossier); 

			DynamiqueReport.setSimpleNameEntity(TaTNoteTiers.class.getSimpleName());
			/**************************************************************/
			DynamiqueReport.setFonctionGetInfosXml(fonctionGetInfosXmlAndProperties);
			DynamiqueReport.setNomObjet(TaNoteTiers.class.getSimpleName());
			/**************************************************************/

			Map<String, AttributElementResport> attribuGridHeader = new LinkedHashMap<String, AttributElementResport>();
			String nameHeaderTitle = ConstEditionTiers.TITLE_EDITION_TIERS_WEB;
			attribuGridHeader.put(nameHeaderTitle, new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
					ConstEdition.FONT_SIZE_XX_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
					ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
					,""));
			String nameSousHeaderTitle = ConstEditionTiers.SOUS_TITLE_EDITION_TIERS_WEB;
			attribuGridHeader.put(nameSousHeaderTitle, new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
					ConstEdition.FONT_SIZE_X_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
					ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
					,ConstEdition.COLOR_GRAY));

			ConstEdition.CONTENT_COMMENTS = ConstEditionTiers.COMMENTAIRE_EDITION_DEFAUT;
			DynamiqueReport.initializeBuildDesignReportConfig();
			DynamiqueReport.makePageMater("1", "1", "1", "1", "100");
			DynamiqueReport.makeReportHeaderGrid(3,5,100,ConstEdition.UNITS_PERCENTAGE,attribuGridHeader);


			//			DynamiqueReport.makeReportTableDB(100,ConstEdition.UNITS_PERCENTAGE,
			//					Const.C_NOM_VU_WEB,attribuTabHeader,attribuTabDetail,1,1,2,5,"30");

			DynamiqueReport.biuldTableReport("100", ConstEdition.UNITS_PERCENTAGE, 
					Const.C_NOM_VU_NOTE_TIERS,1,1,2,"40", mapAttributeTablHead, mapAttributeTablDetail);

			DynamiqueReport.savsAsDesignHandle();
			impression.imprimer(true,pathFileReportDynamic,null,"Webs",TaNoteTiers.class.getSimpleName(),false);

		}

	}

	@Override
	protected boolean aideDisponible() {
		boolean result = false;
//		//switch ((getThis().ibTaTable.getFModeObjet().getMode())) {
//		switch ((getThis().dao.getModeObjet().getMode())) {
//		case C_MO_CONSULTATION:
//			if(getFocusCourantSWT().equals(vue.getGrille()))
//				result = true;
//			break;
//		case C_MO_EDITION:
//		case C_MO_INSERTION:
//			if(getFocusCourantSWT().equals(vue.getTfCODE_T_NOTE_TIERS()))
//				result = true;
//			break;
//		default:
//			break;
//		}
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
//			try {
//				VerrouInterface.setVerrouille(true);
//				vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
//				ParamAfficheAideRechercheSWT paramAfficheAideRecherche = new ParamAfficheAideRechercheSWT();
//				//paramAfficheAideRecherche.setDb(getThis().getIbTaTable().getFIBBase());
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
//				//SWTBaseControllerSWTStandard controllerEcranCreation = null;
//				JPABaseControllerSWTStandard controllerEcranCreation = null;
//				ParamAffiche parametreEcranCreation = null;
//				IEditorPart editorCreation = null;
//				String editorCreationId = null;
//				IEditorInput editorInputCreation = null;
//				Shell s2 = new Shell(s, LgrShellUtil.styleLgr);
//
//				//switch ((getThis().ibTaTable.getFModeObjet().getMode())) {
//				switch ((getThis().dao.getModeObjet().getMode())) {
//				case C_MO_CONSULTATION:
//					if(getFocusCourantSWT().equals(vue.getGrille())){
//						 PaNoteSWT = new (s2,SWT.NULL);
//						SWTPaNoteTiersController swtPaNoteController = new SWTPaNoteTiersController(PaNoteSWT);
//						
//						paramAfficheAideRecherche.setForceAffichageAideRemplie(true);
//
//
//						editorCreationId = EditorNoteTiers.ID;
//						editorInputCreation = new EditorInputNoteTiers();
//
//						ParamAfficheNoteTiers ParamAfficheNote = new ParamAfficheNoteTiers();
//						//paramAfficheAideRecherche.setQuery(swtPaWebController.getIbTaTable().getFIBQuery());
//						paramAfficheAideRecherche.setJPQLQuery(new TaTNoteTiersDAO(getEm()).getJPQLQuery());
//						ParamAfficheNote.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//						ParamAfficheNote.setEcranAppelant(paAideController);
//						controllerEcranCreation = swtPaNoteController;
//						parametreEcranCreation = ParamAfficheNote;
//
//						paramAfficheAideRecherche.setTypeEntite(TaNoteTiers.class);
//						paramAfficheAideRecherche.setChampsRecherche(Const.C_NOTE_TIERS);
//						paramAfficheAideRecherche.setDebutRecherche(vue.getTfNOTE_TIERS().getText());
//						paramAfficheAideRecherche.setControllerAppelant(getThis());
//						paramAfficheAideRecherche.setModel(swtPaNoteController.getModelNote());
//						paramAfficheAideRecherche.setTypeObjet(swtPaNoteController.getClassModel());
//						//paramAfficheAideRecherche.setChampsIdentifiant(swtPaWebController.getIbTaTable().champIdTable);
//						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_NOTE_TIERS);
//					}
//					break;
//				case C_MO_EDITION:
//				case C_MO_INSERTION:
//					try {
//						if(getFocusCourantSWT().equals(vue.getTfCODE_T_NOTE_TIERS())){
//							PaTypeNoteTiersSWT paTypeNoteSWT = new PaTypeNoteTiersSWT(s2,SWT.NULL);
//							SWTPaTypeNoteTiersController swtPaTypeNoteController = new SWTPaTypeNoteTiersController(paTypeNoteSWT);
//
//							editorCreationId = EditorTypeNoteTiers.ID;
//							editorInputCreation = new EditorInputTypeNoteTiers();
//
//
//							ParamAfficheTypeNoteTiers paramAfficheTypeNote = new ParamAfficheTypeNoteTiers();
//							paramAfficheAideRecherche.setJPQLQuery(new TaTNoteTiersDAO(getEm()).getJPQLQuery());
//							paramAfficheTypeNote.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//							paramAfficheTypeNote.setEcranAppelant(paAideController);
//							controllerEcranCreation = swtPaTypeNoteController;
//							parametreEcranCreation = paramAfficheTypeNote;
//
//							paramAfficheAideRecherche.setTypeEntite(TaTNoteTiers.class);
//							paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_T_NOTE_TIERS);
//							paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_T_NOTE_TIERS().getText());
//							paramAfficheAideRecherche.setControllerAppelant(getThis());
//							paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTNoteTiers,TaTNoteTiersDAO,TaTNoteTiers>(SWTTNoteTiers.class,getEm()));
//
//							paramAfficheAideRecherche.setTypeObjet(swtPaTypeNoteController.getClassModel());
//
//							paramAfficheAideRecherche.setChampsIdentifiant(swtPaTypeNoteController.getDao().getChampIdTable());
//						}
//					} catch (Exception e1) {
//						// TODO: handle exception
//						logger.error(""+e1);
//					}
//
//
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
//					paramAfficheAideRecherche
//					.setFocusSWT(((PaAideRechercheSWT) paAideRechercheController1
//							.getVue()).getTfChoix());
//					paramAfficheAideRecherche
//					.setRefCreationSWT(controllerEcranCreation);
//					paramAfficheAideRecherche.setEditorCreation(editorCreation);
//					paramAfficheAideRecherche.setEditorCreationId(editorCreationId);
//					paramAfficheAideRecherche.setEditorInputCreation(editorInputCreation);
//					paramAfficheAideRecherche
//					.setParamEcranCreation(parametreEcranCreation);
//					paramAfficheAideRecherche.setShellCreation(s2);
//					paAideRechercheController1
//					.configPanel(paramAfficheAideRecherche);
//					// paramAfficheAideRecherche.setFocusDefaut(paAideRechercheController1.getVue().getTfChoix());
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
//					Control focus = vue.getShell().getDisplay().getFocusControl();
//					// affichage de l'ecran d'aide principal (+ ses recherches)
//
//					dbc.getValidationStatusMap().removeMapChangeListener(
//							changeListener);
//					//LgrShellUtil.afficheAideSWT(paramAfficheAide, null, paAide,paAideController, s);
//					/*****************************************************************/
//					paAideController.configPanel(paramAfficheAide);
//					/*****************************************************************/
//					dbc.getValidationStatusMap().addMapChangeListener(
//							changeListener);
//
//				}
//
//			} finally {
//				VerrouInterface.setVerrouille(false);
//				vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
//			}
		}
	}

	public IStatus validateUI() throws Exception {
		if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
				|| (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
			ctrlTousLesChampsAvantEnregistrementSWT();
		}
		return null;
	}

	public IStatus validateUIField(String nomChamp,Object value) {
		String validationContext = "NOTE_TIERS";
		try {
			IStatus s = null;

			if(nomChamp.equals(Const.C_CODE_T_NOTE_TIERS)) {
				TaTNoteTiersDAO dao = new TaTNoteTiersDAO(getEm());

				dao.setModeObjet(getDao().getModeObjet());
				TaTNoteTiers f = new TaTNoteTiers();
				PropertyUtils.setSimpleProperty(f, nomChamp, value);
				s = dao.validate(f,nomChamp,validationContext);

				if(s.getSeverity()!=IStatus.ERROR) {
					f = dao.findByCode((String)value);
					taNote.setTaTNoteTiers(f);
				}
				dao = null;
			} else {
				TaNoteTiers u = new TaNoteTiers();
				u.setTaTiers(masterEntity);
				PropertyUtils.setSimpleProperty(u, nomChamp, value);
				s = dao.validate(u,nomChamp,validationContext);

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
			//TaPrix u = null;
			if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
				LgrDozerMapper<SWTNoteTiers,TaNoteTiers> mapper = new LgrDozerMapper<SWTNoteTiers,TaNoteTiers>();
				mapper.map((SWTNoteTiers) selectedNote.getValue(),taNote);
				masterEntity.addNote(taNote);

			} else if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)) {
				LgrDozerMapper<SWTNoteTiers,TaNoteTiers> mapper = new LgrDozerMapper<SWTNoteTiers,TaNoteTiers>();
				mapper.map((SWTNoteTiers) selectedNote.getValue(),taNote);
				taNote.setTaTiers(masterEntity);
				masterEntity.addNote(taNote);
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
			//taNote=masterEntity.getTaNote();
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
			super.initEtatComposantGeneral();
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
		}
//		vue.getTfCODE_T_NOTE_TIERS().setEditable(true);
	}

	public boolean isUtilise(){
		return (((SWTNoteTiers)selectedNote.getValue()).getIdNoteTiers()!=null && 
				!dao.recordModifiable(dao.getNomTable(),
						((SWTNoteTiers)selectedNote.getValue()).getIdTiers()))||
						!masterDAO.autoriseModification(masterEntity);		
	}
	public SWTNoteTiers getSwtOldNote() {
		return swtOldNote;
	}

	public void setSwtOldNote(SWTNoteTiers swtOldNote) {
		this.swtOldNote = swtOldNote;
	}

	public void setSwtOldNote() {
		if (selectedNote.getValue() != null)
			this.swtOldNote = SWTNote.copy((SWTNoteTiers) selectedNote.getValue());
		else {
			if (tableViewer.selectionGrille(0)){
				this.swtOldNote = SWTNote.copy((SWTNoteTiers) selectedNote.getValue());
				tableViewer.setSelection(new StructuredSelection(
						(SWTNoteTiers) selectedNote.getValue()), true);
			}}
	}


	public void setVue(PaNotesTiers vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();
//		mapComposantDecoratedField.put(vue.getTfNOTE_TIERS(), vue.getFieldNOTE_TIERS());
//		mapComposantDecoratedField.put(vue.getTfCODE_T_NOTE_TIERS(), vue.getFieldCODE_T_NOTE_TIERS());
//		mapComposantDecoratedField.put(vue.getDateTimeNOTE_TIERS(), vue.getFieldDATE_NOTE_TIERS());

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
		writableList = new WritableList(realm, IHMmodel(), classModel);
		tableViewer.setInput(writableList);
		tableViewer.refresh();
		//repositionnement sur la valeur courante
		int idActuel = 0;
		if (taNote!=null) { //enregistrement en cours de modification/insertion
			idActuel = taNote.getIdNoteTiers();
		} else if(selectedNote!=null && (SWTNoteTiers) selectedNote.getValue()!=null) {
			idActuel = ((SWTNoteTiers) selectedNote.getValue()).getIdNoteTiers();
		}

		if(idActuel!=0) {
			Object s = recherche(Const.C_ID_NOTE_TIERS, idActuel);
			if(s!=null)
				tableViewer.setSelection(new StructuredSelection(s));
		}else
			tableViewer.selectionGrille(0);
	}

	public Object recherche(String propertyName, Object value) {
		boolean trouve = false;
		int i = 0;
		while(!trouve && i<IHMmodel().size()){
			try {
				if(PropertyUtils.getProperty(IHMmodel().get(i), propertyName).equals(value)) {
					trouve = true;
				} else {
					i++;
				}
			} catch (IllegalAccessException e) {
				logger.error("",e);
			} catch (InvocationTargetException e) {
				logger.error("",e);
			} catch (NoSuchMethodException e) {
				logger.error("",e);
			}
		}

		if(trouve)
			return IHMmodel().get(i);
		else 
			return null;

	}
	public ModelGeneralObjet<SWTNoteTiers,TaNoteTiersDAO,TaNoteTiers> getModelNote() {
		return modelNote;
	}

	public TaNoteTiersDAO getDao() {
		return dao;
	}

	public void setDao(TaNoteTiersDAO dao) {
		this.dao = dao;
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

	public void modifMode(){
		if (!VerrouInterface.isVerrouille() ){
			try {
				if(!daoStandard.dataSetEnModif()) {
					if(!masterEntity.getTaNotes().isEmpty()) {
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

	@Override
	public void updateMasterEntity(UpdateMasterEntityEvent evt) {
		try {
			actRefresh();
//			notifier();
		} catch (Exception e) {
			logger.error("",e);
		}
	}

}

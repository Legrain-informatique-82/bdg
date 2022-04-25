package fr.legrain.tiers.ecran;

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
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
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
import org.eclipse.jface.dialogs.IDialogConstants;
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
import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.edition.actions.MakeDynamiqueReport;
import fr.legrain.edition.dynamique.FonctionGetInfosXmlAndProperties;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;

import fr.legrain.gestCom.Module_Tiers.SWTTTel;
import fr.legrain.gestCom.Module_Tiers.SWTTelephone;
import fr.legrain.gestCom.Module_Tiers.SWTTiers;

import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.DeclencheCommandeControllerEvent;
import fr.legrain.gestCom.librairiesEcran.swt.IDetailController;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
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
import fr.legrain.lib.data.LibChaine;
import fr.legrain.lib.data.LibConversion;
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
import fr.legrain.openmail.sms.OpenmailSMS;
import fr.legrain.tiers.TiersPlugin;
import fr.legrain.tiers.dao.TaAdresse;
import fr.legrain.tiers.dao.TaTAdr;
import fr.legrain.tiers.dao.TaTTel;
import fr.legrain.tiers.dao.TaTTelDAO;
import fr.legrain.tiers.dao.TaTelephone;
import fr.legrain.tiers.dao.TaTelephoneDAO;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.divers.Impression;
import fr.legrain.tiers.ecran.SWTPaEmailController.HandlerSendMail;
import fr.legrain.tiers.editor.EditorInputTelephone;
import fr.legrain.tiers.editor.EditorInputTypeTelephone;
import fr.legrain.tiers.editor.EditorTelephone;
import fr.legrain.tiers.editor.EditorTypeTelephone;
import fr.legrain.tiers.preferences.PreferenceConstants;

public class SWTPaTelephoneController extends JPABaseControllerSWTStandard
implements RetourEcranListener,IDetailController {

	static Logger logger = Logger.getLogger(SWTPaTelephoneController.class.getName());
	private PaTelephoneSWT vue = null;
	//private SWT_IB_TA_TELEPHONE ibTaTable;// = new SWT_IB_TA_TELEPHONE();
	private TaTelephoneDAO dao = null;//new TaTelephoneDAO();


	private Object ecranAppelant = null;
	private SWTTelephone swtTelephone;
	private SWTTelephone swtOldTelephone;
	private Realm realm;
	private DataBindingContext dbc;


	private Class classModel = SWTTelephone.class;
	private ModelGeneralObjet<SWTTelephone,TaTelephoneDAO,TaTelephone> modelTelephone = null;//new ModelGeneralObjet<SWTTelephone,TaTelephoneDAO,TaTelephone>(dao,classModel);
	
	public static final String C_COMMAND_SEND_SMS_ID = "fr.legrain.gestionCommerciale.tiers.telephone.sendsms";
	private HandlerSendSMS handlerSendSMS = new HandlerSendSMS();

	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private IObservableValue selectedTelephone;
	private String[] listeChamp;
	private String nomClassController = this.getClass().getSimpleName();
	private TaTiers masterEntity = null;
	private TaTiersDAO masterDAO = null;

	LinkedList<SWTTelephone> listeModel;

	private LgrDozerMapper<SWTTelephone,TaTelephone> mapperUIToModel  = new LgrDozerMapper<SWTTelephone,TaTelephone>();
	private TaTelephone taTelephone = null;

	private MapChangeListener changeListener = new MapChangeListener();

	private Integer idTiers = null;

	public SWTPaTelephoneController(PaTelephoneSWT vue) {
		this(vue,null);
	}

	public SWTPaTelephoneController(PaTelephoneSWT vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaTelephoneDAO(getEm());
		modelTelephone = new ModelGeneralObjet<SWTTelephone,TaTelephoneDAO,TaTelephone>(dao,classModel);
		setVue(vue);

		// a faire avant l'initialisation du Binding
		vue.getGrille().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setSwtOldTelephone();
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
	/**
	 * Creation des objet pour l'interface, a partir de l'entite principale.<br>
	 * @return
	 */
	public List<SWTTelephone> IHMmodel() {
		LinkedList<TaTelephone> ldao = new LinkedList<TaTelephone>();
		listeModel = new LinkedList<SWTTelephone>();

		if(masterEntity!=null && !masterEntity.getTaTelephones().isEmpty()) {

			ldao.addAll(masterEntity.getTaTelephones());

			LgrDozerMapper<TaTelephone,SWTTelephone> mapper = new LgrDozerMapper<TaTelephone,SWTTelephone>();
			for (TaTelephone o : ldao) {
				SWTTelephone t = null;
				t = (SWTTelephone) mapper.map(o, SWTTelephone.class);
				listeModel.add(t);
			}			
		}
		logger.info("IHMmodel()");
		return listeModel;
	}

	public List<SWTTelephone> IHMmodelFixe() {
		//		LinkedList<TaTelephone> ldao = new LinkedList<TaTelephone>();
		//		if (listeModel==null){
		//			listeModel = new LinkedList<SWTTelephone>();
		//
		//
		//			if(masterEntity!=null && !masterEntity.getTaTelephones().isEmpty()) {
		//
		//				ldao.addAll(masterEntity.getTaTelephones());
		//
		//				LgrDozerMapper<TaTelephone,SWTTelephone> mapper = new LgrDozerMapper<TaTelephone,SWTTelephone>();
		//				for (TaTelephone o : ldao) {
		//					SWTTelephone t = null;
		//					t = (SWTTelephone) mapper.map(o, SWTTelephone.class);
		//					listeModel.add(t);
		//				}
		//			}
		//		}
		//		logger.info("IHMmodelFixe()");
		//		return listeModel;
		return IHMmodel();
	}
	protected void initEtatBouton() {
		initEtatBouton(IHMmodelFixe());
	}
	
	@Override
	protected void initImageBouton() {
		super.initImageBouton();
		String imageCarte = "/icons/phone.png";
		vue.getBtnSendSMS().setImage(TiersPlugin.getImageDescriptor(imageCarte).createImage());
		vue.layout(true);
	}

	public void bind(PaTelephoneSWT paTelephoneSWT) {
		try {
			//modelTelephone = new ModelTypeTiers(ibTaTable);
			//			 modelTelephone = new ModelGeneral<SWTTelephone>(ibTaTable.getFIBQuery(),classModel);
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			tableViewer = new LgrTableViewer(paTelephoneSWT.getGrille());
			tableViewer.createTableCol(classModel,paTelephoneSWT.getGrille(), nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE,0);
			listeChamp = tableViewer.setListeChampGrille(nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE);

			// Set up data binding.
			LgrViewerSupport.bind(tableViewer, 
					new WritableList(IHMmodel(), classModel),
					BeanProperties.values(listeChamp)
			);

			selectedTelephone = ViewersObservables
			.observeSingleSelection(tableViewer);

			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			tableViewer.selectionGrille(0);

			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);

			selectedTelephone.getValue();
			bindingFormMaitreDetail(dbc, realm, selectedTelephone,classModel);
			changementDeSelection();
			selectedTelephone.addChangeListener(new IChangeListener() {

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
		if(selectedTelephone!=null && selectedTelephone.getValue()!=null) {
			if(((SWTTelephone) selectedTelephone.getValue()).getIdTelephone()!=null) {
				taTelephone = dao.findById(((SWTTelephone) selectedTelephone.getValue()).getIdTelephone());
			}
			//null a tester ailleurs, car peut etre null en cas d'insertion
			fireChangementDeSelection(new ChangementDeSelectionEvent(SWTPaTelephoneController.this));
		}
	}

	public Composite getVue() {
		return vue;
	}

	public ResultAffiche configPanel(ParamAffiche param) {
		if (param != null) {
			Map<String,String[]> map = dao.getParamWhereSQL();
			//ibTaTable.ouvreDataset();
			if (((ParamAfficheTelephone) param).getFocusDefautSWT() != null && !((ParamAfficheTelephone) param).getFocusDefautSWT().isDisposed())
				((ParamAfficheTelephone) param).getFocusDefautSWT().setFocus();
			else
				((ParamAfficheTelephone) param).setFocusDefautSWT(vue
						.getGrille());
			vue.getLaTitreFormulaire().setText(
					((ParamAfficheTelephone) param).getTitreFormulaire());
			vue.getLaTitreGrille().setText(
					((ParamAfficheTelephone) param).getTitreGrille());
			vue.getLaTitreFenetre().setText(
					((ParamAfficheTelephone) param).getSousTitre());

		}
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
				taTelephone=null;
				selectedTelephone.setValue(null);
				actRefresh(true);
			} catch (Exception e) {
				logger.error("",e);
			}
		}

		//		tableViewer.tri(classModel, nomClassController,
		//				Const.C_FICHIER_LISTE_CHAMP_GRILLE);
		VerrouInterface.setVerrouille(false);
		setSwtOldTelephone();

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

		//param.setFocus(ibTaTable.getFModeObjet().getFocusCourant());
		//}
		return null;
	}
	
	protected void initVerifySaisie() {
		if (mapInfosVerifSaisie == null)
			mapInfosVerifSaisie = new HashMap<Control, InfosVerifSaisie>();
		
		mapInfosVerifSaisie.put(vue.getTfCODE_T_TEL(), new InfosVerifSaisie(new TaTTel(),Const.C_CODE_T_TEL,null));
		mapInfosVerifSaisie.put(vue.getTfCODE_TIERS(), new InfosVerifSaisie(new TaTiers(),Const.C_CODE_TIERS,null));
		mapInfosVerifSaisie.put(vue.getTfNUMERO_TELEPHONE(), new InfosVerifSaisie(new TaTelephone(),Const.C_NUMERO_TELEPHONE,null));
		mapInfosVerifSaisie.put(vue.getTfPOSTE_TELEPHONE(), new InfosVerifSaisie(new TaTelephone(),Const.C_POSTE_TELEPHONE,null));

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

		vue.getTfNUMERO_TELEPHONE().setToolTipText(Const.C_NUMERO_TELEPHONE);
		vue.getTfPOSTE_TELEPHONE().setToolTipText(Const.C_POSTE_TELEPHONE);
		vue.getTfCODE_TIERS().setToolTipText(Const.C_CODE_TIERS);
		vue.getTfCODE_T_TEL().setToolTipText(Const.C_CODE_T_TEL);

		mapComposantChamps.put(vue.getTfNUMERO_TELEPHONE(),Const.C_NUMERO_TELEPHONE);
		mapComposantChamps.put(vue.getTfPOSTE_TELEPHONE(),Const.C_POSTE_TELEPHONE);
		mapComposantChamps.put(vue.getTfCODE_TIERS(),Const.C_CONTACT);
		mapComposantChamps.put(vue.getTfCODE_T_TEL(),Const.C_CODE_T_TEL);
//		mapComposantChamps.put(vue.getBtnCommAdministratifTelephone(),Const.C_COMM_ADMINISTRATIF_TELEPHONE);
//		mapComposantChamps.put(vue.getBtnCommCommercialTelephone(),Const.C_COMM_COMMERCIAL_TELEPHONE);


		for (Control c : mapComposantChamps.keySet()) {
			listeComposantFocusable.add(c);
		}
		
		listeComposantFocusable.add(vue.getBtnSendSMS());

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
				.getTfNUMERO_TELEPHONE());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue
				.getTfNUMERO_TELEPHONE());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION, vue
				.getGrille());

		activeModifytListener();
	}

	protected void initActions() {
		mapCommand = new HashMap<String, IHandler>();
		
		mapCommand.put(C_COMMAND_SEND_SMS_ID, handlerSendSMS);

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
		
		mapActions.put(vue.getBtnSendSMS(), C_COMMAND_SEND_SMS_ID);

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

	public SWTPaTelephoneController getThis() {
		return this;
	}

	@Override
	public boolean onClose() throws ExceptLgr {
		boolean retour = true;
		VerrouInterface.setVerrouille(true);
		switch (dao.getModeObjet().getMode()) {
		case C_MO_INSERTION:
		case C_MO_EDITION:
			//			MessageDialog dialog = new MessageDialog(vue.getShell(), 
			////					"Annulation",
			////					null, "Voulez vous annuler les modifications ?",
			//					MessagesEcran.getString("Message.Attention"),
			//					null, MessagesEcran.getString("Telephone.Message.Enregistrer"),
			//					MessageDialog.QUESTION, 
			//					new String [] {IDialogConstants.YES_LABEL,IDialogConstants.YES_TO_ALL_LABEL,IDialogConstants.NO_LABEL,IDialogConstants.NO_TO_ALL_LABEL,IDialogConstants.CANCEL_LABEL},
			//					0);
			//			
			//			final int dialogResult = dialog.open();
			//			
			//			/*
			//			 * yes => boucle avec question
			//			 * yes_all => boucle sans question (annulation silencieuse)
			//			 * no => enregistre avec question
			//			 * no_all => enregistre tout
			//			 */
			//			
			//			if(dialogResult==0) {
			//				//yes
			//				fireEnregistreTout(new AnnuleToutEvent(this));
			//			} else if(dialogResult==1) {
			//				//yes_all
			//				fireEnregistreTout(new AnnuleToutEvent(this,true));
			//			} else if(dialogResult==2) {
			//				//no
			//				fireAnnuleTout(new AnnuleToutEvent(this));
			//			} else if(dialogResult==3) {
			//				//no_all
			//				fireAnnuleTout(new AnnuleToutEvent(this,true));
			//			} else if(dialogResult==4) {
			//				//cancel
			//				retour=false;
			//			}
			if (MessageDialog.openQuestion(vue.getShell(), MessagesEcran
					.getString("Message.Attention"), MessagesEcran
					.getString("Telephone.Message.Enregistrer"))) {

				try {
					actEnregistrer();
					//fireAnnuleTout(new AnnuleToutEvent(this));
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
				setListeEntity(getModelTelephone().remplirListe());
				dao.initValeurIdTable(taTelephone);
				fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
						dao.getChampIdEntite(), dao.getValeurIdTable(),
						selectedTelephone.getValue())));

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
					if(getFocusAvantAideSWT().equals(vue.getTfCODE_T_TEL())){
						TaTTel u = null;
						TaTTelDAO t = new TaTTelDAO(getEm());
						u = t.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
						t = null;
						taTelephone.setTaTTel(u);
					}
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
	
	protected class HandlerSendSMS extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException{
			try {
				//System.err.println("SMS");
				OpenmailSMS om= new OpenmailSMS();
				if(taTelephone!=null && taTelephone.getNumeroTelephone()!=null) {
					om.sendSMS(taTelephone.getNumeroTelephone());
				}
			} catch (Exception e1) {
				logger.error("Erreur : actionPerformed", e1);
			}
			return event;
		}
	}

	@Override
	protected void actInserer() throws Exception {
		try {
			boolean continuer=true;
			VerrouInterface.setVerrouille(true);
			masterDAO.verifAutoriseModification(masterEntity);
			setSwtOldTelephone();
			if(LgrMess.isDOSSIER_EN_RESEAU()){
				DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
				fireDeclencheCommandeController(e);
				continuer=masterDAO.dataSetEnModif();
			}
			if(continuer){
				swtTelephone = new SWTTelephone();
				swtTelephone.setIdTiers(idTiers);
				dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_INSERTION);
				taTelephone = new TaTelephone();
				if(TiersPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.CORRESPONDANCE_TELEPHONE_ADMINISTRATIF_DEFAUT)) {
					taTelephone.setCommAdministratifTelephone(1);
				} else {
					taTelephone.setCommAdministratifTelephone(0);
				}
				
				if(TiersPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.CORRESPONDANCE_TELEPHONE_COMMERCIAL_DEFAUT)) {
					taTelephone.setCommCommercialTelephone(1);
				} else {
					taTelephone.setCommCommercialTelephone(0);
				}
				List l = IHMmodelFixe();
				l.add(swtTelephone);
				writableList = new WritableList(realm, l, classModel);
				tableViewer.setInput(writableList);
				tableViewer.refresh();
				tableViewer.setSelection(new StructuredSelection(swtTelephone));
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
			setSwtOldTelephone();
			if(LgrMess.isDOSSIER_EN_RESEAU()){
				DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
				fireDeclencheCommandeController(e);
				continuer=masterDAO.dataSetEnModif();
			}
			if(continuer){
				for (TaTelephone p : masterEntity.getTaTelephones()) {
					if(p.getIdTelephone()==((SWTTelephone) selectedTelephone.getValue()).getIdTelephone()) {
						taTelephone = p;
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
							.getString("Telephone.Message.Supprimer"))) {				
						dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_SUPPRESSION);
						try {
							DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
							fireDeclencheCommandeController(e);
						} catch (Exception e) {
							logger.error("",e);
						}
						for (TaTelephone p : masterEntity.getTaTelephones()) {
							if(p.getIdTelephone()==((SWTTelephone) selectedTelephone.getValue()).getIdTelephone()) {
								taTelephone = p;
							}
						}

						dao.begin(transaction);
						dao.supprimer(taTelephone);
						taTelephone.setTaTiers(null);
						masterEntity.removeTelephone(taTelephone);
						Object suivant=tableViewer.getElementAt(tableViewer.getTable().getSelectionIndex()+1);
						taTelephone=masterEntity.getTaTelephone();
						dao.commit(transaction);
						if(suivant!=null)tableViewer.setSelection(new StructuredSelection(suivant),true);
						else tableViewer.selectionGrille(0);
						try {
							DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_ENREGISTRER_ID);
							fireDeclencheCommandeController(e);
						} catch (Exception e) {
							logger.error("",e);
						}	
						actRefresh(true);		
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
			switch (dao.getModeObjet().getMode()) {
			case C_MO_INSERTION:

				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("Telephone.Message.Annuler")))) {
					remetTousLesChampsApresAnnulationSWT(dbc);
					actRefresh(true);
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
						.getString("Telephone.Message.Annuler")))) {
					int rang =((WritableList)tableViewer.getInput()).indexOf(selectedTelephone.getValue());
					List<SWTTelephone> l = IHMmodelFixe();
					if(rang!=-1)
						l.set(rang, swtOldTelephone);
					remetTousLesChampsApresAnnulationSWT(dbc);
					writableList = new WritableList(realm, l, classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.setSelection(new StructuredSelection(swtOldTelephone), true);

					dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
					hideDecoratedFields();
					if(!annuleToutEnCours) {
						fireAnnuleTout(new AnnuleToutEvent(this));
					}
				}
				initEtatBouton();

				break;
			case C_MO_CONSULTATION:
				//actionFermer.run();
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

		fonctionGetInfosXmlAndProperties.setValueMapAttributeTable(TaTelephone.class.getSimpleName()+".head");
		fonctionGetInfosXmlAndProperties.setValueMapAttributeTable(TaTelephone.class.getSimpleName()+".detail");


		LinkedHashMap<String,AttributElementResport> mapAttributeTablHead = fonctionGetInfosXmlAndProperties.getMapAttributeTablHead(); 
		LinkedHashMap<String,AttributElementResport> mapAttributeTablDetail = fonctionGetInfosXmlAndProperties.getMapAttributeTablDetail();

		Collection<TaTelephone> collectionTaTelephone = masterEntity.getTaTelephones();

		fonctionGetInfosXmlAndProperties.findInfosFileXml(TaTelephone.class.getName(),SWTTelephone.class.getName(),
				listeChamp,"mapping");
		fonctionGetInfosXmlAndProperties.getInfosObjetJPA(taTelephone);

		ConstEdition constEdition = new ConstEdition(); 
		Impression impression = new Impression(constEdition,taTelephone,collectionTaTelephone,nomChampIdTable,taTelephone.getIdTelephone());
		String nomDossier = null;

		int nombreLine = collectionTaTelephone.size();

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
			String folderEditionDynamique = Const.C_RCP_INSTANCE_LOCATION+"/"+Const.C_NOM_PROJET_TMP+"/"+TaTelephone.class.getSimpleName();
			constEdition.makeFolderEditions(folderEditionDynamique);
			Path pathFileReport = new Path(folderEditionDynamique+"/"+Const.C_NOM_VU_TELEPHONE+".rptdesign");
			final String pathFileReportDynamic = pathFileReport.toPortableString();


			MakeDynamiqueReport DynamiqueReport = new MakeDynamiqueReport(constEdition.getNameTableEcran(),
					constEdition.getNameTableBDD(),pathFileReportDynamic,Const.C_NOM_VU_TELEPHONE,
					ConstEdition.PAGE_ORIENTATION_LANDSCAPE,nomDossier); 

			DynamiqueReport.setSimpleNameEntity(TaTelephone.class.getSimpleName());
			/**************************************************************/
			DynamiqueReport.setFonctionGetInfosXml(fonctionGetInfosXmlAndProperties);
			DynamiqueReport.setNomObjet(TaTelephone.class.getSimpleName());
			/**************************************************************/

			Map<String, AttributElementResport> attribuGridHeader = new LinkedHashMap<String, AttributElementResport>();
			String nameHeaderTitle = ConstEditionTiers.TITLE_EDITION_TIERS_TEL;
			attribuGridHeader.put(nameHeaderTitle, new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
					ConstEdition.FONT_SIZE_XX_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
					ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
					,""));
			String nameSousHeaderTitle = ConstEditionTiers.SOUS_TITLE_EDITION_TIERS_TEL;
			attribuGridHeader.put(nameSousHeaderTitle, new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
					ConstEdition.FONT_SIZE_X_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
					ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
					,ConstEdition.COLOR_GRAY));

			ConstEdition.CONTENT_COMMENTS = ConstEditionTiers.COMMENTAIRE_EDITION_DEFAUT;
			DynamiqueReport.initializeBuildDesignReportConfig();
			DynamiqueReport.makePageMater("1", "1", "1", "1", "100");
			DynamiqueReport.makeReportHeaderGrid(3,5,100,ConstEdition.UNITS_PERCENTAGE,attribuGridHeader);

			//				DynamiqueReport.makeReportTableDB(100,ConstEdition.UNITS_PERCENTAGE,
			//						Const.C_NOM_VU_TELEPHONE,attribuTabHeader,attribuTabDetail,1,1,2,5,"30");
			DynamiqueReport.biuldTableReport("100", ConstEdition.UNITS_PERCENTAGE, 
					Const.C_NOM_VU_TELEPHONE,1,1,2,"40", mapAttributeTablHead, mapAttributeTablDetail);

			DynamiqueReport.savsAsDesignHandle();
			impression.imprimer(true,pathFileReportDynamic,null,"Telephones",TaTelephone.class.getSimpleName(),false);
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
			if(getFocusCourantSWT().equals(vue.getTfCODE_T_TEL()))
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
				JPABaseControllerSWTStandard controllerEcranCreation = null;
				ParamAffiche parametreEcranCreation = null;
				IEditorPart editorCreation = null;
				String editorCreationId = null;
				IEditorInput editorInputCreation = null;
				Shell s2 = new Shell(s, LgrShellUtil.styleLgr);

				switch ((getThis().dao.getModeObjet().getMode())) {
				case C_MO_CONSULTATION:
					if(getFocusCourantSWT().equals(vue.getGrille())){
//						PaTelephoneSWT paAdresseSWT = new PaTelephoneSWT(s2,SWT.NULL);
//						SWTPaTelephoneController swtPaTelephoneController = new SWTPaTelephoneController(paAdresseSWT);
//						
//						paramAfficheAideRecherche.setForceAffichageAideRemplie(true);
//						paramAfficheAideRecherche.setAfficheDetail(false);
//
//						editorCreationId = EditorTelephone.ID;
//						editorInputCreation = new EditorInputTelephone();
//
//						ParamAfficheTelephone paramAfficheAdresse = new ParamAfficheTelephone();
//						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
//						paramAfficheAdresse.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//						paramAfficheAdresse.setEcranAppelant(paAideController);
//						controllerEcranCreation = swtPaTelephoneController;
//						parametreEcranCreation = paramAfficheAdresse;
//
//						paramAfficheAideRecherche.setTypeEntite(TaTelephone.class);
//						paramAfficheAideRecherche.setChampsRecherche(Const.C_NUMERO_TELEPHONE);
//						paramAfficheAideRecherche.setDebutRecherche(vue.getTfNUMERO_TELEPHONE().getText());
//						paramAfficheAideRecherche.setControllerAppelant(getThis());
//						paramAfficheAideRecherche.setModel(swtPaTelephoneController.getModelTelephone());
//						paramAfficheAideRecherche.setTypeObjet(swtPaTelephoneController.getClassModel());
//						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_TELEPHONE);
					}
					break;
				case C_MO_EDITION:
				case C_MO_INSERTION:
					if(getFocusCourantSWT().equals(vue.getTfCODE_T_TEL())){
						PaTTelSWT paTTelSWT = new PaTTelSWT(s2,SWT.NULL);
						SWTPaTTelController swtPaTTelController = new SWTPaTTelController(paTTelSWT);

						editorCreationId = EditorTypeTelephone.ID;
						editorInputCreation = new EditorInputTypeTelephone();

						ParamAfficheTypeTelephone paramAfficheTTel = new ParamAfficheTypeTelephone();
						//paramAfficheAideRecherche.setQuery(swtPaTTelController.getIbTaTable().getFIBQuery());
						paramAfficheAideRecherche.setJPQLQuery(new TaTTelDAO(getEm()).getJPQLQuery());
						paramAfficheTTel.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheTTel.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaTTelController;
						parametreEcranCreation = paramAfficheTTel;

						paramAfficheAideRecherche.setTypeEntite(TaTTel.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_T_TEL);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_T_TEL().getText());
						paramAfficheAideRecherche.setControllerAppelant(getThis());
						//paramAfficheAideRecherche.setModel(new ModelGeneral<SWTTTel>(swtPaTTelController.getIbTaTable().getFIBQuery(),SWTTTel.class));
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTTel,TaTTelDAO,TaTTel>(SWTTTel.class,dao.getEntityManager()));
						paramAfficheAideRecherche.setTypeObjet(swtPaTTelController.getClassModel());

						paramAfficheAideRecherche.setChampsIdentifiant(swtPaTTelController.getDao().getChampIdTable());
					}
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
					paramAfficheAideRecherche.setEditorCreation(editorCreation);
					paramAfficheAideRecherche.setEditorCreationId(editorCreationId);
					paramAfficheAideRecherche.setEditorInputCreation(editorInputCreation);
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


	public IStatus validateUI() throws Exception {
		if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
				|| (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
			ctrlTousLesChampsAvantEnregistrementSWT();
		}
		return null;
	}

	public IStatus validateUIField(String nomChamp,Object value) {
		String validationContext = "TELEPHONE";
		try {
			IStatus s = null;

			if(nomChamp.equals(Const.C_CODE_T_TEL)) {
				TaTTelDAO dao = new TaTTelDAO(getEm());

				dao.setModeObjet(getDao().getModeObjet());
				TaTTel f = new TaTTel();
				PropertyUtils.setSimpleProperty(f, nomChamp, value);
				s = dao.validate(f,nomChamp,validationContext);

				if(s.getSeverity()!=IStatus.ERROR) {
					f = dao.findByCode((String)value);
					taTelephone.setTaTTel(f);
				}
				dao = null;
			} else {
				if(nomChamp.equals(Const.C_COMM_ADMINISTRATIF_TELEPHONE) || nomChamp.equals(Const.C_COMM_COMMERCIAL_TELEPHONE)) { //traitement des booleens
					if((Boolean)value==true) value=new Integer(1); else value=new Integer(0);
				}
				TaTelephone u = new TaTelephone();
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
				LgrDozerMapper<SWTTelephone,TaTelephone> mapper = new LgrDozerMapper<SWTTelephone,TaTelephone>();
				mapper.map((SWTTelephone) selectedTelephone.getValue(),taTelephone);
				masterEntity.addTelephone(taTelephone);

			} else if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)) {
				LgrDozerMapper<SWTTelephone,TaTelephone> mapper = new LgrDozerMapper<SWTTelephone,TaTelephone>();
				mapper.map((SWTTelephone) selectedTelephone.getValue(),taTelephone);
				taTelephone.setTaTiers(masterEntity);
				masterEntity.addTelephone(taTelephone);


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
			taTelephone=masterEntity.getTaTelephone();
			dao.commit(transaction);
			changementDeSelection();
			actRefresh(true);
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
			//vue.getTfCODE_T_TEL().setEditable(!isUtilise());
			changeCouleur(vue);
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
		}
	}
	public boolean isUtilise(){
		return (((SWTTelephone)selectedTelephone.getValue()).getIdTelephone()!=null && 
				!dao.recordModifiable(dao.getNomTable(),
						((SWTTelephone)selectedTelephone.getValue()).getIdTelephone()))||
						!masterDAO.autoriseModification(masterEntity);		
	}
	public SWTTelephone getSwtOldTelephone() {
		return swtOldTelephone;
	}

	public void setSwtOldTelephone(SWTTelephone swtOldTelephone) {
		this.swtOldTelephone = swtOldTelephone;
	}

	public void setSwtOldTelephone() {
		if (selectedTelephone.getValue() != null)
			this.swtOldTelephone = SWTTelephone.copy((SWTTelephone) selectedTelephone.getValue());
		else {
			if (tableViewer.selectionGrille(0)){
				this.swtOldTelephone = SWTTelephone.copy((SWTTelephone) selectedTelephone.getValue());
				tableViewer.setSelection(new StructuredSelection((SWTTelephone) selectedTelephone.getValue()), true);
			}}
	}

	public void setVue(PaTelephoneSWT vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();
		mapComposantDecoratedField.put(vue.getTfNUMERO_TELEPHONE(), vue
				.getFieldNUMERO_TELEPHONE());
		mapComposantDecoratedField.put(vue.getTfPOSTE_TELEPHONE(), vue
				.getFieldPOSTE_TELEPHONE());
		mapComposantDecoratedField.put(vue.getTfCODE_T_TEL(), vue
				.getFieldCODE_T_TEL());
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
		actRefresh(false);		
	}


	protected void actRefresh(boolean initModel) throws Exception {
		//rafraichissement des valeurs dans la grille
		//repositionnement sur la valeur courante
		int idActuel = 0;
		if (taTelephone!=null) { //enregistrement en cours de modification/insertion
			idActuel = taTelephone.getIdTelephone();
		} else 
			if(selectedTelephone!=null && (SWTTelephone) selectedTelephone.getValue()!=null) {
				idActuel = ((SWTTelephone) selectedTelephone.getValue()).getIdTelephone();
			}

		//tableViewer.setInput(null);
		if (initModel)tableViewer.setInput(new WritableList( IHMmodel(), classModel));
		else tableViewer.setInput(new WritableList( IHMmodelFixe(), classModel));

		if(idActuel!=0) {
			StructuredSelection e =new StructuredSelection(recherche(Const.C_ID_TELEPHONE, idActuel));
			if (e!=null)tableViewer.setSelection(e);
			else tableViewer.selectionGrille(0);
		}else
			tableViewer.selectionGrille(0);	

	}
	public Object recherche(String propertyName, Object value) {
		boolean trouve = false;
		int i = 0;
		while(!trouve && i<IHMmodelFixe().size()){
			try {
				if(PropertyUtils.getProperty(IHMmodelFixe().get(i), propertyName).equals(value)) {
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
			return IHMmodelFixe().get(i);
		else 
			return null;

	}

	public ModelGeneralObjet<SWTTelephone,TaTelephoneDAO,TaTelephone> getModelTelephone() {
		return modelTelephone;
	}

	public TaTelephoneDAO getDao() {
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
	public void modifMode(){
		if (!VerrouInterface.isVerrouille() ){
			try {
				if(!daoStandard.dataSetEnModif()) {
					if(!masterEntity.getTaTelephones().isEmpty()) {
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

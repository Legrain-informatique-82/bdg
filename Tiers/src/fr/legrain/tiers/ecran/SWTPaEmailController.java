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
import fr.legrain.edition.actions.ConstEdition;
import fr.legrain.edition.actions.MakeDynamiqueReport;
import fr.legrain.edition.dynamique.FonctionGetInfosXmlAndProperties;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Tiers.SWTEmail;
import fr.legrain.gestCom.Module_Tiers.SWTTEmail;
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
import fr.legrain.openmail.mail.OpenmailMail;
import fr.legrain.tiers.TiersPlugin;
import fr.legrain.tiers.dao.TaEmail;
import fr.legrain.tiers.dao.TaEmailDAO;
import fr.legrain.tiers.dao.TaTEmail;
import fr.legrain.tiers.dao.TaTEmailDAO;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;
import fr.legrain.tiers.divers.Impression;
import fr.legrain.tiers.editor.EditorInputTypeEmail;
import fr.legrain.tiers.editor.EditorTypeEmail;
import fr.legrain.tiers.preferences.PreferenceConstants;

public class SWTPaEmailController extends JPABaseControllerSWTStandard
implements RetourEcranListener,IDetailController {

	static Logger logger = Logger.getLogger(SWTPaEmailController.class.getName());
	private PaEmailSWT vue = null;
	//	private SWT_IB_TA_EMAIL ibTaTable = new SWT_IB_TA_EMAIL();
	private TaEmailDAO dao = null;//new TaEmailDAO();

	private Object ecranAppelant = null;
	private SWTEmail swtEmail;
	private SWTEmail swtOldEmail;
	private Realm realm;
	private DataBindingContext dbc;

	private Class classModel = SWTEmail.class;
	//	private ModelGeneral<SWTEmail> modelEmail = new ModelGeneral<SWTEmail>(ibTaTable.getFIBQuery(),classModel);
	private ModelGeneralObjet<SWTEmail,TaEmailDAO,TaEmail> modelEmail =  null;//new ModelGeneralObjet<SWTEmail,TaEmailDAO,TaEmail>(dao,classModel);

	public static final String C_COMMAND_SEND_MAIL_ID = "fr.legrain.gestionCommerciale.tiers.email.sendmail";
	private HandlerSendMail handlerSendMail = new HandlerSendMail();

	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private IObservableValue selectedEmail;
	private String[] listeChamp;
	private String nomClassController = this.getClass().getSimpleName();
	private MapChangeListener changeListener = new MapChangeListener();

	private TaTiers masterEntity = null;
	private TaTiersDAO masterDAO = null;

	private LgrDozerMapper<SWTEmail,TaEmail> mapperUIToModel  = new LgrDozerMapper<SWTEmail,TaEmail>();
	private TaEmail taEmail = new TaEmail();

	private Integer idTiers = null;

	public SWTPaEmailController(PaEmailSWT vue) {
		this(vue,null);
	}

	public SWTPaEmailController(PaEmailSWT vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaEmailDAO(getEm());
		modelEmail =  new ModelGeneralObjet<SWTEmail,TaEmailDAO,TaEmail>(dao,classModel);
		setVue(vue);

		// a faire avant l'initialisation du Binding
		vue.getGrille().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setSwtOldEmail();
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
	public List<SWTEmail> IHMmodel() {
		LinkedList<TaEmail> ldao = new LinkedList<TaEmail>();
		LinkedList<SWTEmail> lswt = new LinkedList<SWTEmail>();

		if(masterEntity!=null && !masterEntity.getTaEmails().isEmpty()) {

			ldao.addAll(masterEntity.getTaEmails());

			LgrDozerMapper<TaEmail,SWTEmail> mapper = new LgrDozerMapper<TaEmail,SWTEmail>();
			for (TaEmail o : ldao) {
				SWTEmail t = null;
				t = (SWTEmail) mapper.map(o, SWTEmail.class);
				lswt.add(t);
			}

		}
		return lswt;
	}

	protected void initEtatBouton() {
		initEtatBouton(IHMmodel());

		boolean trouve = contientDesEnregistrement(IHMmodel());
		switch (daoStandard.getModeObjet().getMode()) {
		case C_MO_INSERTION:
			enableActionAndHandler(C_COMMAND_SEND_MAIL_ID,false);
			break;
		case C_MO_EDITION:
			enableActionAndHandler(C_COMMAND_SEND_MAIL_ID,false);
			break;
		case C_MO_CONSULTATION:
			enableActionAndHandler(C_COMMAND_SEND_MAIL_ID,trouve);
			break;
		default:
			break;
		}
	}

	@Override
	protected void initImageBouton() {
		super.initImageBouton();
		String imageCarte = "/icons/email.png";
		vue.getBtnSendMail().setImage(TiersPlugin.getImageDescriptor(imageCarte).createImage());
		vue.layout(true);
	}

	public void bind(PaEmailSWT paAdresseSWT) {
		try {
			//modelEmail = new ModelTypeTiers(ibTaTable);
			//			 modelEmail = new ModelGeneral<SWTEmail>(ibTaTable.getFIBQuery(),classModel);
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			tableViewer = new LgrTableViewer(paAdresseSWT.getGrille());
			tableViewer.createTableCol(classModel,paAdresseSWT.getGrille(), nomClassController,
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
			//			List<SWTEmail> lswt = IHMmodel();
			//			writableList = new WritableList(realm, lswt, classModel);
			//			tableViewer.setInput(writableList);

			// Set up data binding.
			LgrViewerSupport.bind(tableViewer, 
					new WritableList(IHMmodel(), classModel),
					BeanProperties.values(listeChamp)
			);

			selectedEmail = ViewersObservables
			.observeSingleSelection(tableViewer);

			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			tableViewer.selectionGrille(0);

			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);

			selectedEmail.getValue();
			bindingFormMaitreDetail(dbc, realm, selectedEmail,classModel);
			changementDeSelection();
			selectedEmail.addChangeListener(new IChangeListener() {

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
		if(selectedEmail!=null && selectedEmail.getValue()!=null) {
			if(((SWTEmail) selectedEmail.getValue()).getIdEmail()!=null) {
				taEmail = dao.findById(((SWTEmail) selectedEmail.getValue()).getIdEmail());
			}
			//null a tester ailleurs, car peut etre null en cas d'insertion
			fireChangementDeSelection(new ChangementDeSelectionEvent(SWTPaEmailController.this));
		}
	}

	public Composite getVue() {
		return vue;
	}

	public ResultAffiche configPanel(ParamAffiche param) {
		if (param != null) {
			Map<String,String[]> map = dao.getParamWhereSQL();
			//			ibTaTable.ouvreDataset();
			if (((ParamAfficheEmail) param).getFocusDefautSWT() != null && !((ParamAfficheEmail) param).getFocusDefautSWT().isDisposed())
				((ParamAfficheEmail) param).getFocusDefautSWT().setFocus();
			else
				((ParamAfficheEmail) param).setFocusDefautSWT(vue.getGrille());
			vue.getLaTitreFormulaire().setText(((ParamAfficheEmail) param).getTitreFormulaire());
			vue.getLaTitreGrille().setText(((ParamAfficheEmail) param).getTitreGrille());
			vue.getLaTitreFenetre().setText(((ParamAfficheEmail) param).getSousTitre());
			//			if(param instanceof ParamAfficheEmail){
			//				if(((ParamAfficheEmail)param).getIdTiers()!=null && !((ParamAfficheEmail)param).getIdTiers().equals("")) {
			//					this.idTiers=LibConversion.stringToInteger(((ParamAfficheEmail)param).getIdTiers());
			//					if(map==null) map = new HashMap<String,String[]>();
			//					map.clear();
			//					map.put("a.taTiers."+Const.C_ID_TIERS,new String[]{"=",idTiers.toString()});
			//					dao.setParamWhereSQL(map);
			//				}
			//			}
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
					taEmail=null;
					selectedEmail.setValue(null);
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
			setSwtOldEmail();

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

			//			param.setFocus(ibTaTable.getFModeObjet().getFocusCourant());
		}
		return null;
	}
	
	protected void initVerifySaisie() {
		if (mapInfosVerifSaisie == null)
			mapInfosVerifSaisie = new HashMap<Control, InfosVerifSaisie>();
		
		mapInfosVerifSaisie.put(vue.getTfADRESSE_EMAIL(), new InfosVerifSaisie(new TaEmail(),Const.C_ADRESSE_EMAIL,null));
		mapInfosVerifSaisie.put(vue.getTfCODE_T_EMAIL(), new InfosVerifSaisie(new TaTEmail(),Const.C_CODE_T_EMAIL,null));

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

		vue.getTfADRESSE_EMAIL().setToolTipText(Const.C_ADRESSE_EMAIL);
		vue.getTfCODE_T_EMAIL().setToolTipText(Const.C_CODE_T_EMAIL);

		mapComposantChamps.put(vue.getTfADRESSE_EMAIL(), Const.C_ADRESSE_EMAIL);
		mapComposantChamps.put(vue.getTfCODE_T_EMAIL(), Const.C_CODE_T_EMAIL);
//		mapComposantChamps.put(vue.getBtnCommAdministratifEmail(), Const.C_COMM_ADMINISTRATIF_EMAIL);
//		mapComposantChamps.put(vue.getBtnCommCommercialEmail(), Const.C_COMM_COMMERCIAL_EMAIL);

		for (Control c : mapComposantChamps.keySet()) {
			listeComposantFocusable.add(c);
		}

		listeComposantFocusable.add(vue.getBtnSendMail());

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
				.getTfADRESSE_EMAIL());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue
				.getTfADRESSE_EMAIL());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION, vue
				.getGrille());

		activeModifytListener();
	}

	protected void initActions() {
		mapCommand = new HashMap<String, IHandler>();

		mapCommand.put(C_COMMAND_SEND_MAIL_ID, handlerSendMail);

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

		mapActions.put(vue.getBtnSendMail(), C_COMMAND_SEND_MAIL_ID);

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

	public SWTPaEmailController getThis() {
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
					.getString("Email.Message.Enregistrer"))) {

				try {
					actEnregistrer();
				} catch (Exception e) {
					vue.getLaMessage().setText(e.getMessage());
					logger.error("", e);
				}
			} else {
				fireAnnuleTout(new AnnuleToutEvent(this,true));
			}
			//				ibTaTable.annuler();

			break;
		case C_MO_CONSULTATION:
			break;
		default:
			break;
		}

		if (retour) {
			if (ecranAppelant instanceof SWTPaAideControllerSWT) {
				setListeEntity(getModelEmail().remplirListe());
				dao.initValeurIdTable(taEmail);
				fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
						dao.getChampIdEntite(), dao.getValeurIdTable(),
						selectedEmail.getValue())));

				retour = true;
			} else {
				fireRetourEcran(new RetourEcranEvent(this,new ResultAffiche()));
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
					if(getFocusAvantAideSWT().equals(vue.getTfCODE_T_EMAIL())){
						TaTEmail u = null;
						TaTEmailDAO t = new TaTEmailDAO(getEm());
						u = t.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
						t = null;
						taEmail.setTaTEmail(u);
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
					//					ibTaTable.lgrLocateID(((ResultAffiche) evt.getRetour()).getIdResult());
					if(((ResultAffiche) evt.getRetour()).getSelection()!=null)
						tableViewer.setSelection(((ResultAffiche) evt.getRetour()).getSelection(),true);
				}
			}
		}
		super.retourEcran(evt);
	}

	protected class HandlerSendMail extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException{
			try {
				actSendMail();
			} catch (Exception e1) {
				logger.error("Erreur : actionPerformed", e1);
			}
			return event;
		}
	}

	protected void actSendMail() {
		OpenmailMail om= new OpenmailMail();
		if(taEmail!=null && taEmail.getAdresseEmail()!=null) {
			om.sendMail(taEmail.getAdresseEmail());
		}
//		if(Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(java.awt.Desktop.Action.MAIL)) {
//			if(taEmail!=null && taEmail.getAdresseEmail()!=null) {
//				final String adresse = taEmail.getAdresseEmail();
//				/*
//				 * Definition du format de l'uri mailto
//				 * http://www.ietf.org/rfc/rfc2368.txt
//				 */
//				URI u = null;
//				try {
//					u = new URI("mailto:"+adresse/*+"?subject=current-issue"+&body=aaa"*/);
////					u = new URI("mailto:"+adresse+"?subject=current-issue"/*+&body=aaa"*/);
//					//u = new URI("mailto",adresse,null);
//					Desktop.getDesktop().mail(u);
//				} catch (URISyntaxException e) {
//					logger.error("",e);
//				} catch (IOException e) {
//					logger.error("Le client mail par defaut n'a pas ete trouve ou n'a pas pu etre ouvert",e);
//				}
//			}
//		}
	}

	@Override
	protected void actInserer() throws Exception {
		try {
			boolean continuer=true;
			VerrouInterface.setVerrouille(true);
			masterDAO.verifAutoriseModification(masterEntity);
			setSwtOldEmail();
			if(LgrMess.isDOSSIER_EN_RESEAU()){
				DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
				fireDeclencheCommandeController(e);
				continuer=masterDAO.dataSetEnModif();
			}
			if(continuer){
				swtEmail = new SWTEmail();
				swtEmail.setIdTiers(idTiers);
				dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_INSERTION);
				taEmail = new TaEmail();
				if(TiersPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.CORRESPONDANCE_EMAIL_ADMINISTRATIF_DEFAUT)) {
					taEmail.setCommAdministratifEmail(1);
				} else {
					taEmail.setCommAdministratifEmail(0);
				}
				
				if(TiersPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.CORRESPONDANCE_EMAIL_COMMERCIAL_DEFAUT)) {
					taEmail.setCommCommercialEmail(1);
				} else {
					taEmail.setCommCommercialEmail(0);
				}
				List l = IHMmodel();
				l.add(swtEmail);
				writableList = new WritableList(realm, l, classModel);
				tableViewer.setInput(writableList);
				tableViewer.refresh();
				tableViewer.setSelection(new StructuredSelection(swtEmail));
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
			setSwtOldEmail();
			if(LgrMess.isDOSSIER_EN_RESEAU()){
				DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
				fireDeclencheCommandeController(e);
				continuer=masterDAO.dataSetEnModif();
			}
			if(continuer){
				for (TaEmail p : masterEntity.getTaEmails()) {
					if(p.getIdEmail()==((SWTEmail) selectedEmail.getValue()).getIdEmail()) {
						taEmail = p;
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
							.getString("Email.Message.Supprimer"))) {				
						dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_SUPPRESSION);
						try {
							DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
							fireDeclencheCommandeController(e);
						} catch (Exception e) {
							logger.error("",e);
						}
						for (TaEmail p : masterEntity.getTaEmails()) {
							if(p.getIdEmail()==((SWTEmail) selectedEmail.getValue()).getIdEmail()) {
								taEmail = p;
							}
						}
						dao.begin(transaction);
						dao.supprimer(taEmail);
						taEmail.setTaTiers(null);
						masterEntity.removeEmail(taEmail);
						Object suivant=tableViewer.getElementAt(tableViewer.getTable().getSelectionIndex()+1);
						dao.commit(transaction);
						modelEmail.removeEntity(taEmail);
						taEmail=masterEntity.getTaEmail();
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
						.getString("Email.Message.Annuler")))) {
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
						.getString("Email.Message.Annuler")))) {
					int rang =((WritableList)tableViewer.getInput()).indexOf(selectedEmail.getValue());
					remetTousLesChampsApresAnnulationSWT(dbc);
					List<SWTEmail> l = IHMmodel();
					swtOldEmail.setCodeTEmail(null);
					if(rang!=-1)
						l.set(rang, swtOldEmail);

					writableList = new WritableList(realm, l, classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.setSelection(new StructuredSelection(swtOldEmail), true);

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

		fonctionGetInfosXmlAndProperties.setValueMapAttributeTable(TaEmail.class.getSimpleName()+".head");
		fonctionGetInfosXmlAndProperties.setValueMapAttributeTable(TaEmail.class.getSimpleName()+".detail");

		LinkedHashMap<String,AttributElementResport> mapAttributeTablHead = fonctionGetInfosXmlAndProperties.getMapAttributeTablHead(); 
		LinkedHashMap<String,AttributElementResport> mapAttributeTablDetail = fonctionGetInfosXmlAndProperties.getMapAttributeTablDetail();


		Collection<TaEmail> collectionTaEmail = masterEntity.getTaEmails();

		fonctionGetInfosXmlAndProperties.findInfosFileXml(TaEmail.class.getName(),SWTEmail.class.getName(),
				listeChamp,"mapping");
		fonctionGetInfosXmlAndProperties.getInfosObjetJPA(taEmail);

		ConstEdition constEdition = new ConstEdition(); 
		Impression impression = new Impression(constEdition,taEmail,collectionTaEmail,nomChampIdTable,taEmail.getIdEmail());
		String nomDossier = null;

		int nombreLine = collectionTaEmail.size();

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
			String folderEditionDynamique = Const.C_RCP_INSTANCE_LOCATION+"/"+Const.C_NOM_PROJET_TMP+"/"+TaEmail.class.getSimpleName();
			constEdition.makeFolderEditions(folderEditionDynamique);
			Path pathFileReport = new Path(folderEditionDynamique+"/"+Const.C_NOM_VU_EMAIL+".rptdesign");
			final String pathFileReportDynamic = pathFileReport.toPortableString();


			MakeDynamiqueReport DynamiqueReport = new MakeDynamiqueReport(constEdition.getNameTableEcran(),
					constEdition.getNameTableBDD(),pathFileReportDynamic,Const.C_NOM_VU_EMAIL,
					ConstEdition.PAGE_ORIENTATION_LANDSCAPE,nomDossier); 

			DynamiqueReport.setSimpleNameEntity(TaEmail.class.getSimpleName());
			/**************************************************************/
			DynamiqueReport.setFonctionGetInfosXml(fonctionGetInfosXmlAndProperties);
			DynamiqueReport.setNomObjet(TaEmail.class.getSimpleName());
			/**************************************************************/

			Map<String, AttributElementResport> attribuGridHeader = new LinkedHashMap<String, AttributElementResport>();
			String nameHeaderTitle = ConstEditionTiers.TITLE_EDITION_TIERS_EMAIL;
			attribuGridHeader.put(nameHeaderTitle, new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
					ConstEdition.FONT_SIZE_XX_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
					ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
					,""));
			String nameSousHeaderTitle = ConstEditionTiers.SOUS_TITLE_EDITION_TIERS_EMAIL;
			attribuGridHeader.put(nameSousHeaderTitle, new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
					ConstEdition.FONT_SIZE_X_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
					ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE
					,ConstEdition.COLOR_GRAY));

			ConstEdition.CONTENT_COMMENTS = ConstEditionTiers.COMMENTAIRE_EDITION_DEFAUT;
			
			DynamiqueReport.initializeBuildDesignReportConfig();
			DynamiqueReport.makePageMater("1", "1", "1", "1", "100");
			DynamiqueReport.makeReportHeaderGrid(3,5,100,ConstEdition.UNITS_PERCENTAGE,attribuGridHeader);

			//			DynamiqueReport.makeReportTableDB(100,ConstEdition.UNITS_PERCENTAGE,
			//					Const.C_NOM_VU_EMAIL,attribuTabHeader,attribuTabDetail,1,1,2,5,"30");
			DynamiqueReport.biuldTableReport("100", ConstEdition.UNITS_PERCENTAGE, 
					Const.C_NOM_VU_EMAIL,1,1,2,"40", mapAttributeTablHead, mapAttributeTablDetail);

			DynamiqueReport.savsAsDesignHandle();
			impression.imprimer(true,pathFileReportDynamic,null,"Emails",TaEmail.class.getSimpleName());

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
			if(getFocusCourantSWT().equals(vue.getTfCODE_T_EMAIL()))
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
				//				paramAfficheAideRecherche.setDb(getThis().getIbTaTable().getFIBBase());
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
//						PaEmailSWT paEmailSWT = new PaEmailSWT(s2,SWT.NULL);
//						SWTPaEmailController swtPaEmailController = new SWTPaEmailController(paEmailSWT);
//						
//						paramAfficheAideRecherche.setForceAffichageAideRemplie(true);
//						paramAfficheAideRecherche.setAfficheDetail(false);
//
//						editorCreationId = EditorEmail.ID;
//						editorInputCreation = new EditorInputEmail();
//
//						ParamAfficheEmail paramAfficheEmail = new ParamAfficheEmail();
//						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
//						paramAfficheEmail.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//						paramAfficheEmail.setEcranAppelant(paAideController);
//						controllerEcranCreation = swtPaEmailController;
//						parametreEcranCreation = paramAfficheEmail;
//
//						paramAfficheAideRecherche.setTypeEntite(TaEmail.class);
//						paramAfficheAideRecherche.setChampsRecherche(Const.C_ADRESSE_EMAIL);
//						paramAfficheAideRecherche.setDebutRecherche(vue.getTfADRESSE_EMAIL().getText());
//						paramAfficheAideRecherche.setControllerAppelant(getThis());
//						paramAfficheAideRecherche.setModel(swtPaEmailController.getModelEmail());
//						paramAfficheAideRecherche.setTypeObjet(swtPaEmailController.getClassModel());
//						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_EMAIL);
					}
					break;
				case C_MO_EDITION:
				case C_MO_INSERTION:
					if(getFocusCourantSWT().equals(vue.getTfCODE_T_EMAIL())){
						PaTypeEMailSWT paTypeEMailSWT = new PaTypeEMailSWT(s2,SWT.NULL);
						SWTPaTypeEmailController swtPaTypeEmailController = new SWTPaTypeEmailController(paTypeEMailSWT);

						editorCreationId = EditorTypeEmail.ID;
						editorInputCreation = new EditorInputTypeEmail();


						ParamAfficheTypeEMail paramAfficheTypeEMail = new ParamAfficheTypeEMail();
						paramAfficheAideRecherche.setJPQLQuery(new TaTEmailDAO(getEm()).getJPQLQuery());
						paramAfficheTypeEMail.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheTypeEMail.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaTypeEmailController;
						parametreEcranCreation = paramAfficheTypeEMail;

						paramAfficheAideRecherche.setTypeEntite(TaTEmail.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_T_EMAIL);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_T_EMAIL().getText());
						paramAfficheAideRecherche.setControllerAppelant(getThis());
						//						paramAfficheAideRecherche.setModel(new ModelGeneral<SWTTEmail>(swtPaTypeEmailController.getIbTaTable().getFIBQuery(),SWTTAdr.class));
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTEmail,TaTEmailDAO,TaTEmail>(SWTTEmail.class,dao.getEntityManager()));

						paramAfficheAideRecherche.setTypeObjet(swtPaTypeEmailController.getClassModel());

						paramAfficheAideRecherche.setChampsIdentifiant(swtPaTypeEmailController.getDao().getChampIdTable());
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

					// Paramï¿½trage de l'ecran d'aide principal
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
		String validationContext = "EMAIL";
		try {
			IStatus s = null;

			if(nomChamp.equals(Const.C_CODE_T_EMAIL)) {
				TaTEmailDAO dao = new TaTEmailDAO(getEm());

				dao.setModeObjet(getDao().getModeObjet());
				TaTEmail f = new TaTEmail();
				PropertyUtils.setSimpleProperty(f, nomChamp, value);
				s = dao.validate(f,nomChamp,validationContext);

				if(s.getSeverity()!=IStatus.ERROR) {
					f = dao.findByCode((String)value);
					taEmail.setTaTEmail(f);
				}
				dao = null;
			} else {
				if(nomChamp.equals(Const.C_COMM_ADMINISTRATIF_EMAIL) || nomChamp.equals(Const.C_COMM_COMMERCIAL_EMAIL)) { //traitement des booleens
					if((Boolean)value==true) value=new Integer(1); else value=new Integer(0);
				}
				TaEmail u = new TaEmail();
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
				LgrDozerMapper<SWTEmail,TaEmail> mapper = new LgrDozerMapper<SWTEmail,TaEmail>();
				mapper.map((SWTEmail) selectedEmail.getValue(),taEmail);
				masterEntity.addEmail(taEmail);

			} else if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)) {
				LgrDozerMapper<SWTEmail,TaEmail> mapper = new LgrDozerMapper<SWTEmail,TaEmail>();
				mapper.map((SWTEmail) selectedEmail.getValue(),taEmail);
				taEmail.setTaTiers(masterEntity);
				masterEntity.addEmail(taEmail);
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
			taEmail=masterEntity.getTaEmail();
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
			//			if((SWTEmail)selectedEmail.getValue()!=null)
			//				ibTaTable.setChamp_Model_Obj((SWTEmail)selectedEmail.getValue());
			super.initEtatComposantGeneral();
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
		}
	}

	public SWTEmail getSwtOldEmail() {
		return swtOldEmail;
	}

	public void setSwtOldEmail(SWTEmail swtOldEmail) {
		this.swtOldEmail = swtOldEmail;
	}

	public void setSwtOldEmail() {
		if (selectedEmail.getValue() != null)
			this.swtOldEmail = SWTEmail.copy((SWTEmail) selectedEmail.getValue());
		else {
			if (tableViewer.selectionGrille(0)){
				this.swtOldEmail = SWTEmail.copy((SWTEmail) selectedEmail.getValue());
				tableViewer.setSelection(new StructuredSelection(
						(SWTEmail) selectedEmail.getValue()), true);
			}}
	}

	public void setVue(PaEmailSWT vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();
		mapComposantDecoratedField.put(vue.getTfADRESSE_EMAIL(), vue.getFieldADRESSE_EMAIL());
		mapComposantDecoratedField.put(vue.getTfCODE_T_EMAIL(), vue.getFieldCODE_T_EMAIL());
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
		if (taEmail!=null) { //enregistrement en cours de modification/insertion
			idActuel = taEmail.getIdEmail();
		} else if(selectedEmail!=null && (SWTEmail) selectedEmail.getValue()!=null) {
			idActuel = ((SWTEmail) selectedEmail.getValue()).getIdEmail();
		}

		//rafraichissement des valeurs dans la grille
		writableList = new WritableList(realm, IHMmodel(), classModel);
		tableViewer.setInput(writableList);


		if(idActuel!=0) {
			tableViewer.setSelection(new StructuredSelection(recherche(Const.C_ID_EMAIL, idActuel)));
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

	public ModelGeneralObjet<SWTEmail,TaEmailDAO,TaEmail> getModelEmail() {
		return modelEmail;
	}

	public TaEmailDAO getDao() {
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

	public boolean isUtilise(){
		return (((SWTEmail)selectedEmail.getValue()).getIdEmail()!=null &&
				!dao.recordModifiable(dao.getNomTable(),
						((SWTEmail)selectedEmail.getValue()).getIdEmail()))||
						!masterDAO.autoriseModification(masterEntity);		
	}
	public void modifMode(){
		if (!VerrouInterface.isVerrouille() ){
			try {
				if(!daoStandard.dataSetEnModif()) {
					if(!masterEntity.getTaEmails().isEmpty()) {
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

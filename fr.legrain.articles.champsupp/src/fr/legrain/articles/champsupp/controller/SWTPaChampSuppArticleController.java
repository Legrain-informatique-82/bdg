package fr.legrain.articles.champsupp.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;

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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import fr.legrain.articles.champsupp.dao.SWTChampSuppArt;
import fr.legrain.articles.champsupp.dao.TaChampSuppArt;
import fr.legrain.articles.champsupp.dao.TaChampSuppArtDAO;
import fr.legrain.articles.champsupp.dao.TaRChampSuppArt;
import fr.legrain.articles.champsupp.dao.TaRChampSuppArtDAO;
import fr.legrain.articles.champsupp.editors.PaChampSuppArticle;
import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.articles.dao.TaPrix;
import fr.legrain.articles.dao.TaPrixDAO;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.articles.dao.TaUniteDAO;
import fr.legrain.articles.ecran.MessagesEcran;
import fr.legrain.articles.ecran.PaPrixSWT;
import fr.legrain.articles.ecran.ParamAffichePrix;
import fr.legrain.articles.editor.EditorInputPrix;
import fr.legrain.articles.editor.EditorInputUnite;
import fr.legrain.articles.editor.EditorPrix;
import fr.legrain.articles.editor.EditorUnite;
import fr.legrain.articles.statistiques.editors.IFormPageArticlesContoller;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Articles.SWTFamille;
import fr.legrain.gestCom.Module_Articles.SWTPrix;
import fr.legrain.gestCom.Module_Articles.SWTUnite;
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
import fr.legrain.tiers.dao.TaAdresse;
import fr.legrain.tiers.dao.TaTAdr;


public class SWTPaChampSuppArticleController extends JPABaseControllerSWTStandard
implements RetourEcranListener,IDetailController,IFormPageArticlesContoller {

	static Logger logger = Logger.getLogger(SWTPaChampSuppArticleController.class.getName());
	private PaChampSuppArticle vue = null;
	private TaRChampSuppArtDAO dao = null;
	private String idArticle = null;

	private Object ecranAppelant = null;
	private SWTChampSuppArt swtChampSuppArt;
	private SWTChampSuppArt swtOldChampSuppArt;
	private Realm realm;
	private DataBindingContext dbc;

	private Class classModel = SWTChampSuppArt.class;
	private ModelGeneralObjet<SWTChampSuppArt,TaRChampSuppArtDAO,TaRChampSuppArt> modelChampSuppArt = null;//new ModelGeneralObjet<SWTChampSuppArt,TaRChampSuppArtDAO,TaRChampSuppArt>(dao,classModel);
	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private IObservableValue selectedChampSuppArt;
	private String[] listeChamp;
	private String nomClassController = this.getClass().getSimpleName();

	private TaArticle masterEntity = null;
	private TaArticleDAO masterDAO = null;
	
	private TaChampSuppArtDAO taChampSuppArtDAO = null;
	private TaRChampSuppArtDAO taRChampSuppArtDAO = null;
	
	private LinkedHashMap<String, Control> listeChampSupp = new LinkedHashMap<String, Control>();

	private TaRChampSuppArt taRChampSuppArt = null;

	private MapChangeListener changeListener = new MapChangeListener();


	public SWTPaChampSuppArticleController(PaChampSuppArticle vue) {
		this(vue,null);
	}

	public SWTPaChampSuppArticleController(PaChampSuppArticle vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaRChampSuppArtDAO(getEm());
		modelChampSuppArt = new ModelGeneralObjet<SWTChampSuppArt,TaRChampSuppArtDAO,TaRChampSuppArt>(dao,classModel);
		setVue(vue);
		// a faire avant l'initialisation du Binding
		vue.getGrille().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setSwtOldPrix();
			}
		});
		vue.getShell().addShellListener(this);

		// Branchement action annuler : empeche la fermeture automatique de la
		// fenetre sur ESC
		vue.getShell().addTraverseListener(new Traverse());

		initChampDynamique();
		
		initController();
	}
	
	private void initChampDynamique() {
		taChampSuppArtDAO = new TaChampSuppArtDAO();
		List<TaChampSuppArt> listeDescChamp = taChampSuppArtDAO.selectAll();
		
		int i = 0;
		for (Label label : ((PaChampSuppArticle)vue).getListeChamp().keySet()) {
			if(i<listeDescChamp.size()) {
				label.setText(listeDescChamp.get(i).getNomChampSuppArt());
				((PaChampSuppArticle)vue).getListeChamp().get(label).setText(listeDescChamp.get(i).getDefautChampSuppArt());
				listeChampSupp.put(listeDescChamp.get(i).getNomChampSuppArt(), ((PaChampSuppArticle)vue).getListeChamp().get(label));
				i++;
			} else {
//				label.setVisible(false);
//				((PaChampSuppArticle)vue).getListeChamp().get(label).setVisible(false);
				//label.dispose();
				((PaChampSuppArticle)vue).getListeChamp().get(label).dispose();
			}
		}
		
//		Label l = null;
//		Text t = null;
//		for (TaChampSuppArt taChampSuppArt : listeDescChamp) {
//			l = new Label(vue.getPaCorpsFormulaire(),SWT.NONE);
//			l.setText("Label "+taChampSuppArt.getNomChampSuppArt());
//			
//			t = new Text(vue.getPaCorpsFormulaire(),SWT.BORDER);
//			t.setText("Text "+taChampSuppArt.getDefautChampSuppArt());
//			t.setToolTipText(taChampSuppArt.getDescriptionChampSuppArt());
//			
//			listeChampSupp.put(taChampSuppArt.getNomChampSuppArt(), t);
//			
//		}
		
		vue.getPaCorpsFormulaire().layout(true,true);
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
			Menu[] tabPopups = new Menu[] { popupMenuFormulaire, popupMenuGrille };
			this.initPopupAndButtons(mapActions, tabPopups);
			vue.getPaCorpsFormulaire().setMenu(popupMenuFormulaire);
			vue.getPaGrille().setMenu(popupMenuGrille);

			initEtatBouton();
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : SWTPaPrixController", e);
		}
	}

	/**
	 * Creation des objet pour l'interface, a partir de l'entite principale.<br>
	 * Ici : creation d'une liste de prix pour l'IHM, a partir de la liste des prix contenue dans l'entite article.
	 * @return
	 */
	public List<SWTChampSuppArt> IHMmodel() {
		LinkedList<TaRChampSuppArt> ldao = new LinkedList<TaRChampSuppArt>();
		LinkedList<SWTChampSuppArt> lswt = new LinkedList<SWTChampSuppArt>();
//		//masterEntity.getTaRChampSuppArtes().clear();
//		//if(masterEntity!=null)	masterDAO.refresh(masterEntity);
//		if(masterEntity!=null && !masterEntity.getTaRChampSuppArtes().isEmpty()) {
//
//			ldao.addAll(masterEntity.getTaRChampSuppArtes());
//
//			LgrDozerMapper<TaRChampSuppArt,SWTChampSuppArt> mapper = new LgrDozerMapper<TaRChampSuppArt,SWTChampSuppArt>();
//			for (TaRChampSuppArt o : ldao) {
//				SWTChampSuppArt t = null;
//				t = (SWTChampSuppArt) mapper.map(o, SWTChampSuppArt.class);
//				lswt.add(t);
//			}
//
//		}
		return lswt;
	}
	
	public void bind(PaChampSuppArticle paChampSuppArticle) {
		try {
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			tableViewer = new LgrTableViewer(paChampSuppArticle.getGrille());
			tableViewer.createTableCol(classModel,paChampSuppArticle.getGrille(), nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE,-1);
			listeChamp = tableViewer.setListeChampGrille(nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			selectedChampSuppArt = ViewersObservables.observeSingleSelection(tableViewer);

			LgrViewerSupport.bind(tableViewer, 
					new WritableList(IHMmodel(), classModel),
					BeanProperties.values(listeChamp)
			);

			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			tableViewer.selectionGrille(0);

			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);

			bindingFormMaitreDetail(dbc, realm, selectedChampSuppArt,classModel);
			tableViewer.setChecked(tableViewer.getTable().getColumn(1),true);
			changementDeSelection();
			selectedChampSuppArt.addChangeListener(new IChangeListener() {

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
		if(selectedChampSuppArt!=null && selectedChampSuppArt.getValue()!=null) {
			if(((SWTChampSuppArt) selectedChampSuppArt.getValue()).getIdChampSuppArt()!=null) {
				taRChampSuppArt = dao.findById(((SWTChampSuppArt) selectedChampSuppArt.getValue()).getIdChampSuppArt());
			}
			//null a tester ailleurs, car peut etre null en cas d'insertion
			fireChangementDeSelection(new ChangementDeSelectionEvent(SWTPaChampSuppArticleController.this));
		}
	}
	public Composite getVue() {
		return vue;
	}

	public ResultAffiche configPanel(ParamAffiche param) {
		if (param != null) {
			Map<String,String[]> map = dao.getParamWhereSQL();
			if (param.getFocusDefautSWT() != null && !param.getFocusDefautSWT().isDisposed())
				param.getFocusDefautSWT().setFocus();
			else
				param.setFocusDefautSWT(vue
						.getGrille());
			if(param.getTitreFormulaire()!=null){
				vue.getLaTitreFormulaire().setText(param.getTitreFormulaire());
			} else {
				vue.getLaTitreFormulaire().setText(ParamAffichePrix.C_TITRE_FORMULAIRE);
			}

			if(param.getTitreGrille()!=null){
				vue.getLaTitreGrille().setText(param.getTitreGrille());
			} else {
				vue.getLaTitreGrille().setText(ParamAffichePrix.C_TITRE_GRILLE);
			}

			if(param.getSousTitre()!=null){
				vue.getLaTitreFenetre().setText(param.getSousTitre());
			} else {
				vue.getLaTitreFenetre().setText(ParamAffichePrix.C_SOUS_TITRE);
			}

			if (param.getEcranAppelant() != null) {
				ecranAppelant = param.getEcranAppelant();
			}

			if(tableViewer==null) {
				//databinding pas encore realise
				bind(vue);
				tableViewer.tri(classModel, nomClassController,Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			} else {
				try {
					taRChampSuppArt=null;
					selectedChampSuppArt.setValue(null);
					actRefresh();
				} catch (Exception e) {
					logger.error("",e);
				}
			}
			// permet de se positionner sur le 1er enregistrement et de remplir
			// le formulaire
			//			tableViewer.tri(classModel, nomClassController,Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			VerrouInterface.setVerrouille(false);
			setSwtOldPrix();

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

		}
		return null;
	}

	protected void initVerifySaisie() {
		if (mapInfosVerifSaisie == null)
			mapInfosVerifSaisie = new HashMap<Control, InfosVerifSaisie>();

//		mapInfosVerifSaisie.put(vue.getTfPRIX_PRIX(), new InfosVerifSaisie(new TaRChampSuppArt(),Const.C_PRIX_PRIX,new int[]{InfosVerifSaisie.CTRL_QUE_DES_CHIFFRES_POSITIFS}));
//		mapInfosVerifSaisie.put(vue.getTfPRIXTTC_PRIX(), new InfosVerifSaisie(new TaRChampSuppArt(),Const.C_PRIXTTC_PRIX,new int[]{InfosVerifSaisie.CTRL_QUE_DES_CHIFFRES_POSITIFS}));
//		
//		mapInfosVerifSaisie.put(vue.getTfCODE_UNITE(), new InfosVerifSaisie(new TaUnite(),Const.C_CODE_UNITE,null));

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
		
		for (String nomChamp : listeChampSupp.keySet()) {
			listeChampSupp.get(nomChamp).setToolTipText(nomChamp);
			mapComposantChamps.put(listeChampSupp.get(nomChamp), nomChamp);
		}

//		vue.getTfCODE_UNITE().setToolTipText(Const.C_CODE_UNITE);
//		vue.getTfPRIX_PRIX().setToolTipText(Const.C_PRIX_PRIX);
//		vue.getTfPRIXTTC_PRIX().setToolTipText(Const.C_PRIXTTC_PRIX);
//		vue.getCbID_REF_PRIX().setToolTipText(Const.C_ID_REF_PRIX);
//
//		mapComposantChamps.put(vue.getTfCODE_UNITE(), Const.C_CODE_UNITE);
//		mapComposantChamps.put(vue.getTfPRIX_PRIX(), Const.C_PRIX_PRIX);
//		mapComposantChamps.put(vue.getTfPRIXTTC_PRIX(), Const.C_PRIXTTC_PRIX);
//		mapComposantChamps.put(vue.getCbID_REF_PRIX(), Const.C_ID_REF_PRIX);
		
		mapComposantChamps.put(vue.getTfChamp1(), "champSupp1");
//		mapComposantChamps.put(vue.getTfChamp2(), "champSupp2");
//		mapComposantChamps.put(vue.getTfChamp3(), "champSupp3");
//		mapComposantChamps.put(vue.getTfChamp4(), "champSupp4");
//		mapComposantChamps.put(vue.getTfChamp5(), "champSupp5");

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
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION, 
				listeChampSupp.values().iterator().next());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, 
				listeChampSupp.values().iterator().next());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION, 
				listeChampSupp.values().iterator().next());

		activeModifytListener();

//		vue.getTfPRIX_PRIX().addVerifyListener(queDesChiffres);
//		vue.getTfPRIXTTC_PRIX().addVerifyListener(queDesChiffres);
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

	protected void initEtatBouton() {
		//initEtatBoutonCommand();
		initEtatBouton(IHMmodel());
		enableActionAndHandler(C_COMMAND_GLOBAL_IMPRIMER_ID,false);
	}	

	public SWTPaChampSuppArticleController getThis() {
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
					.getString("Prix.Message.Enregistrer"))) {

				try {
					actEnregistrer();
				} catch (Exception e) {
					vue.getLaMessage().setText(e.getMessage());
					logger.error("", e);
				}
			} else {
				fireAnnuleTout(new AnnuleToutEvent(this,true));
			}
			break;
		case C_MO_CONSULTATION:
			break;
		default:
			break;
		}
		if (retour) {
			if(dao.dataSetEnModif())
				try {
					dao.annuler(taRChampSuppArt);
				} catch (Exception e) {
					throw new ExceptLgr();
				}
				if (ecranAppelant instanceof SWTPaAideControllerSWT) {
					setListeEntity(getModelChampSuppArt().remplirListe());
					dao.initValeurIdTable(taRChampSuppArt);
					fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
							dao.getChampIdEntite(), dao.getValeurIdTable(),
							selectedChampSuppArt.getValue())));

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
//					if(getFocusAvantAideSWT().equals(vue.getTfCODE_UNITE())){
//						TaUnite u = null;
//						TaUniteDAO t = new TaUniteDAO(getEm());
//						u = t.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getIdResult()));
//						t = null;
//						taPrix.setTaUnite(u);
//					}
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
				}
			}
		}
		super.retourEcran(evt);
	}

	@Override
	protected void actInserer() throws Exception {
		try {
			boolean continuer=true;
			VerrouInterface.setVerrouille(true);
			masterDAO.verifAutoriseModification(masterEntity);
			setSwtOldPrix();
			if(LgrMess.isDOSSIER_EN_RESEAU()){
				DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
				fireDeclencheCommandeController(e);
				continuer=masterDAO.dataSetEnModif();
			}
			if(continuer){
				//setMasterEntity(masterDAO.findById(masterEntity.getIdArticle()));
				swtChampSuppArt = new SWTChampSuppArt();
//				if (masterEntity.getTaRChampSuppArt()==null){
//					swtPrix.setIdRefPrix(true);
//				}
//				else
//					swtPrix.setIdRefPrix(false);
//				swtPrix.setPrixPrix(new BigDecimal(0));
//				swtPrix.setPrixttcPrix(new BigDecimal(0));
//				swtPrix.setCodeArticle(masterEntity.getCodeArticle());
				dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_INSERTION);
				taRChampSuppArt = new TaRChampSuppArt();
				List l = IHMmodel();
				l.add(swtChampSuppArt);
				writableList = new WritableList(realm, l, classModel);
				tableViewer.setInput(writableList);
				tableViewer.refresh();
				tableViewer.setSelection(new StructuredSelection(swtChampSuppArt));
				initEtatBouton();

				try {
					DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
					fireDeclencheCommandeController(e);
				} catch (Exception e) {
					logger.error("",e);
				}
			}
		} finally {
			initEtatComposant();
			VerrouInterface.setVerrouille(false);
		}
	}

	@Override
	protected void actModifier() throws Exception {
//		try {
//			boolean continuer=true;
//			setSwtOldPrix();
//			if(LgrMess.isDOSSIER_EN_RESEAU()){
//				DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
//				fireDeclencheCommandeController(e);
//				continuer=masterDAO.dataSetEnModif();
//			}
//			if(continuer){
//				setMasterEntity(masterDAO.findById(masterEntity.getIdArticle()));
//				for (TaRChampSuppArt p : masterEntity.getTaRChampSuppArtes()) {
//					if(p.getIdRChampSuppArt()==((SWTChampSuppArt) selectedChampSuppArt.getValue()).getIdChampSuppArt()) {
//						taRChampSuppArt = p;
//						//					if(LgrMess.isDOSSIER_EN_RESEAU())actRefresh();
//					}
//				}			
//				masterDAO.verifAutoriseModification(masterEntity);
//				DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
//				fireDeclencheCommandeController(e);
//				dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_EDITION);
//				initEtatBouton();
//			}
//		} catch (Exception e1) {
//			vue.getLaMessage().setText(e1.getMessage());
//			logger.error("Erreur : actionPerformed", e1);
//		}
	}

	@Override
	protected void actSupprimer() throws Exception {
//		EntityTransaction transaction = dao.getEntityManager().getTransaction();		
//		try {
//			boolean continuer=true;
//			VerrouInterface.setVerrouille(true);
//			if(isUtilise())MessageDialog.openInformation(vue.getShell(), MessagesEcran
//					.getString("Message.Attention"), MessagesEcran
//					.getString("Message.suppression"));
//			else
//				if(LgrMess.isDOSSIER_EN_RESEAU()){
//					DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
//					fireDeclencheCommandeController(e);
//					continuer=masterDAO.dataSetEnModif();
//				}
//
//			if(continuer){
//				setMasterEntity(masterDAO.findById(masterEntity.getIdArticle()));
//				if (MessageDialog.openConfirm(vue.getShell(), MessagesEcran
//						.getString("Message.Attention"), MessagesEcran
//						.getString("Prix.Message.Supprimer"))) {				
//					dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_SUPPRESSION);
//					try {
//						DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_MODIFIER_ID);
//						fireDeclencheCommandeController(e);
//					} catch (Exception e) {
//						logger.error("",e);
//					}
//					for (TaRChampSuppArt p : masterEntity.getTaRChampSuppArtes()) {
//						if(p.getIdRChampSuppArt()==((SWTChampSuppArt) selectedChampSuppArt.getValue()).getIdChampSuppArt()) {
//							taRChampSuppArt = p;
//						}
//					}
//					dao.begin(transaction);
//					dao.supprimer(taRChampSuppArt);
//					taRChampSuppArt.setTaArticle(null);
////					masterEntity.removePrix(taRChampSuppArt);
//					dao.commit(transaction);
//					modelChampSuppArt.removeEntity(taRChampSuppArt);			
////					taRChampSuppArt=masterEntity.getTaRChampSuppArt();
//					tableViewer.selectionGrille(0);
//					try {
//						DeclencheCommandeControllerEvent e = new DeclencheCommandeControllerEvent(this,C_COMMAND_GLOBAL_ENREGISTRER_ID);
//						fireDeclencheCommandeController(e);
//					} catch (Exception e) {
//						logger.error("",e);
//					}	
//					actRefresh();		
//					initEtatBouton();
//
//				}
//			}
//		} catch (Exception e1) {
//			vue.getLaMessage().setText(e1.getMessage());
//			logger.error("Erreur : actionPerformed", e1);
//		} finally {
//			initEtatBouton();
//			VerrouInterface.setVerrouille(false);
//		}
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
						.getString("Prix.Message.Annuler")))) {
					remetTousLesChampsApresAnnulationSWT(dbc);
					actRefresh();
					dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
					hideDecoratedFields();
				}
				initEtatBouton();
				if(!annuleToutEnCours) {
					fireAnnuleTout(new AnnuleToutEvent(this));
				}
				break;
			case C_MO_EDITION:
				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("Prix.Message.Annuler")))) {
					int rang =((WritableList)tableViewer.getInput()).indexOf(selectedChampSuppArt.getValue());
					List<SWTChampSuppArt> l = IHMmodel();
					if(rang!=-1)
						l.set(rang, swtOldChampSuppArt);
					remetTousLesChampsApresAnnulationSWT(dbc);
					writableList = new WritableList(realm, l, classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.setSelection(new StructuredSelection(swtOldChampSuppArt), true);

					ctrlTousLesChampsAvantEnregistrementSWT();

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
//		switch ((getThis().dao.getModeObjet().getMode())) {
//		case C_MO_CONSULTATION:
//			if(getFocusCourantSWT().equals(vue.getGrille()))
//				result = true;
//			break;
//		case C_MO_EDITION:
//		case C_MO_INSERTION:
//			if(getFocusCourantSWT().equals(vue.getTfCODE_UNITE()))
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
//				paramAfficheAideRecherche.setMessage(message);
//				// Creation de l'ecran d'aide principal
//				Shell s = new Shell(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),LgrShellUtil.styleLgr);
//				PaAideSWT paAide = new PaAideSWT(s, SWT.NONE);
//				SWTPaAideControllerSWT paAideController = new SWTPaAideControllerSWT(
//						paAide);
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
//				switch ((getThis().dao.getModeObjet().getMode())) {
//				case C_MO_CONSULTATION:
//					if(getFocusCourantSWT().equals(vue.getGrille())){
//						PaChampSuppArticle paPrixSWT = new PaChampSuppArticle(s2,SWT.NULL);
//						SWTPaChampSuppArticleController swtPaPrixController = new SWTPaChampSuppArticleController(paPrixSWT);
//
//						editorCreationId = EditorPrix.ID;
//						editorInputCreation = new EditorInputPrix();
//
//						
//						paramAfficheAideRecherche.setForceAffichageAideRemplie(true);
//						paramAfficheAideRecherche.setAfficheDetail(false);
//						
//						
//						ParamAffichePrix paramAffichePrix = new ParamAffichePrix();
//						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
//						paramAffichePrix.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//						paramAffichePrix.setEcranAppelant(paAideController);
//						controllerEcranCreation = swtPaPrixController;
//						parametreEcranCreation = paramAffichePrix;
//
//						paramAfficheAideRecherche.setTypeEntite(TaRChampSuppArt.class);
//						paramAfficheAideRecherche.setChampsRecherche(Const.C_PRIX_PRIX);
//						paramAfficheAideRecherche.setDebutRecherche(vue.getTfPRIX_PRIX().getText());
//						paramAfficheAideRecherche.setControllerAppelant(getThis());
//						paramAfficheAideRecherche.setModel(swtPaPrixController.getModelPrix());
//						paramAfficheAideRecherche.setTypeObjet(swtPaPrixController.getClassModel());
//						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_PRIX);
//					}
//					break;
//				case C_MO_EDITION:
//				case C_MO_INSERTION:
//					if(getFocusCourantSWT().equals(vue.getTfCODE_UNITE())){
//						PaUniteSWT paUniteSWT = new PaUniteSWT(s2,SWT.NULL);
//						SWTPaUniteController swtPaUniteController = new SWTPaUniteController(paUniteSWT);
//
//						editorCreationId = EditorUnite.ID;
//						editorInputCreation = new EditorInputUnite();
//
//						ParamAfficheUnite paramAfficheUnite = new ParamAfficheUnite();
//						paramAfficheAideRecherche.setJPQLQuery(new TaUniteDAO(getEm()).getJPQLQuery());
//						paramAfficheUnite.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//						paramAfficheUnite.setEcranAppelant(paAideController);
//						controllerEcranCreation = swtPaUniteController;
//						parametreEcranCreation = paramAfficheUnite;
//
//						paramAfficheAideRecherche.setTypeEntite(TaUnite.class);
//						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_UNITE);
//						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_UNITE().getText());
//						paramAfficheAideRecherche.setControllerAppelant(SWTPaChampSuppArticleController.this);
//						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTUnite,TaUniteDAO,TaUnite>(SWTUnite.class,dao.getEntityManager()));
//						paramAfficheAideRecherche.setTypeObjet(swtPaUniteController.getClassModel());
//
//						paramAfficheAideRecherche.setChampsIdentifiant(swtPaUniteController.getDao().getChampIdTable());
//					}
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
//					paAideController.addRetourEcranListener(getThis());
//					// affichage de l'ecran d'aide principal (+ ses recherches)
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
		String validationContext = "PRIX";
		try {
			IStatus s = null;

			if(nomChamp.equals(Const.C_CODE_UNITE)) {
//				TaUniteDAO dao = new TaUniteDAO(getEm());
//
//				dao.setModeObjet(getDao().getModeObjet());
//				TaUnite f = new TaUnite();
//				PropertyUtils.setSimpleProperty(f, nomChamp, value);
//				s = dao.validate(f,nomChamp,validationContext);
//
//				if(s.getSeverity()!=IStatus.ERROR) {
//					f = dao.findByCode((String)value);
//					taPrix.setTaUnite(f);
//				}
//				dao = null;
			} else {
				TaRChampSuppArt u = new TaRChampSuppArt();
				u.setTaArticle(masterEntity);
				PropertyUtils.setSimpleProperty(u, nomChamp, value);

				int change=0;
//				if(nomChamp.equals(Const.C_PRIX_PRIX)) {
//					u.setPrixttcPrix(((SWTChampSuppArt) selectedPrix.getValue()).getPrixttcPrix());
//					change =u.getPrixPrix().compareTo(((SWTChampSuppArt) selectedPrix.getValue()).getPrixPrix());
//				}
//				if(nomChamp.equals(Const.C_PRIXTTC_PRIX)) {
//					u.setPrixPrix(((SWTChampSuppArt) selectedPrix.getValue()).getPrixPrix());
//					change =u.getPrixttcPrix().compareTo(((SWTChampSuppArt) selectedPrix.getValue()).getPrixttcPrix());
//				}
//
//				if(((SWTChampSuppArt) selectedPrix.getValue()).getIdPrix()!=null) {
//					u.setIdPrix(((SWTChampSuppArt) selectedPrix.getValue()).getIdPrix());
//				}


				s = dao.validate(u,nomChamp,validationContext);
				if(s.getSeverity()!=IStatus.ERROR && change!=0) {
//					((SWTChampSuppArt) selectedPrix.getValue()).setPrixPrix(u.getPrixPrix());				
//					((SWTChampSuppArt) selectedPrix.getValue()).setPrixttcPrix(u.getPrixttcPrix());
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
			ctrlTousLesChampsAvantEnregistrementSWT();
			dao.begin(transaction);
			//TaRChampSuppArt u = null;
			if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
				LgrDozerMapper<SWTChampSuppArt,TaRChampSuppArt> mapper = new LgrDozerMapper<SWTChampSuppArt,TaRChampSuppArt>();
				mapper.map((SWTChampSuppArt) selectedChampSuppArt.getValue(),taRChampSuppArt);

			} else if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)) {
				LgrDozerMapper<SWTChampSuppArt,TaRChampSuppArt> mapper = new LgrDozerMapper<SWTChampSuppArt,TaRChampSuppArt>();
				mapper.map((SWTChampSuppArt) selectedChampSuppArt.getValue(),taRChampSuppArt);
				taRChampSuppArt.setTaArticle(masterEntity);
//				masterEntity.addPrix(taPrix);
//				if(((SWTChampSuppArt) selectedPrix.getValue()).getIdRefPrix())
//					masterEntity.setTaRChampSuppArt(taPrix);				
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
//			taRChampSuppArt=masterEntity.getTaRChampSuppArt();
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
//			vue.getTfCODE_UNITE().setEditable(!isUtilise());
			changeCouleur(vue);
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
		}

	}
	public boolean isUtilise(){
//		return (((SWTChampSuppArt)selectedPrix.getValue()).getIdArticle()!=null && 
//				!dao.recordModifiable(dao.getNomTable(),
//						((SWTChampSuppArt)selectedPrix.getValue()).getIdArticle()))||
//						!masterDAO.autoriseModification(masterEntity);		
		return false;
	}
	public SWTChampSuppArt getSwtOldChampSuppArt() {
		return swtOldChampSuppArt;
	}

	public void setSwtOldUnite(SWTChampSuppArt swtOldPrix) {
		this.swtOldChampSuppArt = swtOldPrix;
	}

	public void setSwtOldPrix() {
		if (selectedChampSuppArt.getValue() != null)
			this.swtOldChampSuppArt = SWTChampSuppArt.copy((SWTChampSuppArt) selectedChampSuppArt.getValue());
		else {
			if(tableViewer.selectionGrille(0)){
				this.swtOldChampSuppArt = SWTChampSuppArt.copy((SWTChampSuppArt) selectedChampSuppArt.getValue());
				tableViewer.setSelection(new StructuredSelection(
						(SWTChampSuppArt) selectedChampSuppArt.getValue()), true);
			}}
	}

	public void setVue(PaChampSuppArticle vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();

//		mapComposantDecoratedField.put(vue.getTfCODE_UNITE(), vue.getFieldCODE_UNITE());
//		mapComposantDecoratedField.put(vue.getTfPRIX_PRIX(), vue.getFieldPRIX_PRIX());
//		mapComposantDecoratedField.put(vue.getTfPRIXTTC_PRIX(), vue.getFieldPRIXTTC_PRIX());
//		mapComposantDecoratedField.put(vue.getCbID_REF_PRIX(), vue.getFieldID_REF_PRIX());
	}

	public Class getClassModel() {
		return classModel;
	}



	@Override
	protected void sortieChamps() {
//		if(selectedPrix!=null && selectedPrix.getValue()!=null){
//			if(vue.getTfPRIX_PRIX().equals(getFocusCourantSWT())){
//				if(((SWTChampSuppArt)selectedPrix.getValue()).getTauxTva()!=null)
//					((SWTChampSuppArt)selectedPrix.getValue()).setPrixttcPrix(((SWTChampSuppArt)selectedPrix.getValue()).getPrixPrix().multiply(new BigDecimal(1).
//							add(((SWTChampSuppArt)selectedPrix.getValue()).getTauxTva().divide(new BigDecimal(100)))));
//				vue.getTfPRIXTTC_PRIX().setText(LibConversion.bigDecimalToString(((SWTChampSuppArt)selectedPrix.getValue()).getPrixttcPrix()));
//			} else if(vue.getTfPRIXTTC_PRIX().equals(getFocusCourantSWT())){
//				if(((SWTChampSuppArt)selectedPrix.getValue()).getTauxTva()!=null)
//					((SWTChampSuppArt)selectedPrix.getValue()).setPrixPrix(((SWTChampSuppArt)selectedPrix.getValue()).getPrixttcPrix().divide(new BigDecimal(1).
//							add(((SWTChampSuppArt)selectedPrix.getValue()).getTauxTva().divide(new BigDecimal(100))),MathContext.DECIMAL32));
//				vue.getTfPRIX_PRIX().setText(LibConversion.bigDecimalToString(((SWTChampSuppArt)selectedPrix.getValue()).getPrixPrix()));
//			}
//		}		
	}

	@Override
	protected void actRefresh() throws Exception {
		//repositionnement sur la valeur courante
		int idActuel = 0;
		if (taRChampSuppArt!=null) { //enregistrement en cours de modification/insertion
			idActuel = taRChampSuppArt.getIdRChampSuppArt();
		} else if(selectedChampSuppArt!=null && (SWTChampSuppArt) selectedChampSuppArt.getValue()!=null) {
			idActuel = ((SWTChampSuppArt) selectedChampSuppArt.getValue()).getIdChampSuppArt();
		}

		//rafraichissement des valeurs dans la grille
		writableList = new WritableList(realm, IHMmodel(), classModel);
		tableViewer.setInput(writableList);

		if(idActuel!=0) {
			tableViewer.setSelection(new StructuredSelection(recherche(Const.C_ID_PRIX, idActuel)));
		}else
			tableViewer.selectionGrille(0);				
	}


	public	ModelGeneralObjet<SWTChampSuppArt,TaRChampSuppArtDAO,TaRChampSuppArt> getModelChampSuppArt() {
		return modelChampSuppArt;
	}

	public TaArticle getMasterEntity() {
		return masterEntity;
	}

	public void setMasterEntity(TaArticle taArticle) {
		this.masterEntity = taArticle;
	}

	public TaArticleDAO getMasterDAO() {
		return masterDAO;
	}

	public void setMasterDAO(TaArticleDAO masterDAO) {
		this.masterDAO = masterDAO;
	}

	public TaRChampSuppArt getTaRChampSuppArt() {
		return taRChampSuppArt;
	}

	public TaRChampSuppArtDAO getDao() {
		return dao;
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

	public boolean changementPageValide(){
		if (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0
				|| dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0) {
			//mise a jour de l'entite principale
			if(taRChampSuppArt!=null && selectedChampSuppArt!=null && ((SWTChampSuppArt) selectedChampSuppArt.getValue())!=null) {
				LgrDozerMapper<SWTChampSuppArt,TaRChampSuppArt> mapper = new LgrDozerMapper<SWTChampSuppArt,TaRChampSuppArt>();
				mapper.map((SWTChampSuppArt) selectedChampSuppArt.getValue(),taRChampSuppArt);
			}
		}
		//		dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
		//		initEtatBouton();
		return true;
	};

}

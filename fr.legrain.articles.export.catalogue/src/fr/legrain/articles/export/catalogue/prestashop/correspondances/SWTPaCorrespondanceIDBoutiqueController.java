package fr.legrain.articles.export.catalogue.prestashop.correspondances;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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

import fr.legrain.article.export.catalogue.Activator;
import fr.legrain.article.export.catalogue.ModelCorrespondanceIDBoutique;
import fr.legrain.article.export.catalogue.ModelTaxPrestashop;
import fr.legrain.article.export.catalogue.SWTTaxeBoutique;
import fr.legrain.article.export.catalogue.preferences.PreferenceConstantsProject;
import fr.legrain.articles.dao.TaTva;
import fr.legrain.articles.dao.TaTvaDAO;
import fr.legrain.articles.ecran.PaTVASWT;
import fr.legrain.articles.ecran.ParamAfficheTva;
import fr.legrain.articles.ecran.SWTPaTvaController;
import fr.legrain.articles.editor.EditorInputTva;
import fr.legrain.articles.editor.EditorTva;
import fr.legrain.boutique.dao.SWTCorrespondanceIDBoutique;
import fr.legrain.boutique.dao.TaCorrespondanceIDBoutique;
import fr.legrain.boutique.dao.TaCorrespondanceIDBoutiqueDAO;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjet;
import fr.legrain.gestCom.Module_Articles.SWTTva;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.gestCom.librairiesEcran.swt.LgrUpdateValueStrategy;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideRechercheSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideRechercheControllerSWT;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDeSelectionEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.InfosVerifSaisie;
import fr.legrain.lib.data.LibConversion;
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
import fr.legrain.prestashop.ws.WSPrestashop;
import fr.legrain.prestashop.ws.WSPrestashopConfig;
import fr.legrain.prestashop.ws.entities.Taxes;
import fr.legrain.tiers.dao.TaTCPaiement;

public class SWTPaCorrespondanceIDBoutiqueController extends JPABaseControllerSWTStandard
implements RetourEcranListener {

	static Logger logger = Logger.getLogger(SWTPaCorrespondanceIDBoutiqueController.class.getName());
	private PaCorrespondanceIDBoutiqueSWT vue = null;
	private TaCorrespondanceIDBoutiqueDAO dao = null;
	
	private String idTypeConditionPaiement = null;
	private boolean conditionTiers = true;
	
	private Object ecranAppelant = null;
	private SWTCorrespondanceIDBoutique swtCorrespondanceIDBoutique;
	private SWTCorrespondanceIDBoutique swtOldCorrespondanceIDBoutique;
	private Realm realm;
	private DataBindingContext dbc;

	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private IObservableValue selectedCorrespondanceIDBoutique;
	private String[] listeChamp;
	private String nomClassController = this.getClass().getSimpleName();
	private MapChangeListener changeListener = new MapChangeListener();
	private Class classModel = SWTCorrespondanceIDBoutique.class;
	private ModelCorrespondanceIDBoutique modelCorrespondanceIDBoutique = null;
	private LgrDozerMapper<SWTCorrespondanceIDBoutique,TaCorrespondanceIDBoutique> mapperUIToModel  = new LgrDozerMapper<SWTCorrespondanceIDBoutique,TaCorrespondanceIDBoutique>();
	private TaCorrespondanceIDBoutique taCorrespondanceIDBoutique = null;
	
	private String[] itemsCode = null;
	private String[] itemsLibelle = null;
	private String[] itemsCodeEtLibelle = null;
	
	private WSPrestashop ws = null;
	
	public SWTPaCorrespondanceIDBoutiqueController(PaCorrespondanceIDBoutiqueSWT vue) {
		this(vue,null);
	}

	public SWTPaCorrespondanceIDBoutiqueController(PaCorrespondanceIDBoutiqueSWT vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaCorrespondanceIDBoutiqueDAO(getEm());
		
		String cle = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.WEBSERVICE_LOGIN);
		String password = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.WEBSERVICE_PASSWORD);
		String hostName = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.WEBSERVICE_HOST);
		String baseURI = "http://"+hostName+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.WEBSERVICE_BASE_URI);
		WSPrestashopConfig.init(
				baseURI, 
				cle, 
				password, 
				hostName
				);
		ws = new WSPrestashop(true);
		
		modelCorrespondanceIDBoutique = new ModelCorrespondanceIDBoutique();
		modelCorrespondanceIDBoutique.setTypeCorrespondance(TaCorrespondanceIDBoutiqueDAO.TYPE_TVA);
		modelCorrespondanceIDBoutique.setWs(ws);
		setVue(vue);

		// a faire avant l'initialisation du Binding
		vue.getGrille().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setSwtOldCPaiement();
			}
		});
		vue.getShell().addShellListener(this);

		// Branchement action annuler : empeche la fermeture automatique de la fenetre sur ESC
		vue.getShell().addTraverseListener(new Traverse());

		initController();
		
	}
	
	private Map<String,String> contenuCombo = null;
	
	private void initCombo() {
		if(contenuCombo==null) {
			contenuCombo = new HashMap<String, String>();
		} else {
			contenuCombo.clear();
		}
		//List<TaTCPaiement> l = taTCPaiementDAO.selectAll();
		//for (int i = 0; i < l.size(); i++) {
			contenuCombo.put(
					TaCorrespondanceIDBoutiqueDAO.TYPE_TVA, "TVA"
					//l.get(i).getCodeTCPaiement(), 
					//l.get(i).getLiblTCPaiement()
					);
		//}
		
		//TypeDoc c;
	}
	
	private void initItemCombo() {
		if(contenuCombo==null || contenuCombo.size()<1) {
			initCombo();
		}
		int taille = contenuCombo.size();
		if(!conditionTiers){
			taille--;
		}
		
//		String[] itemsCode = new String[taille];
//		String[] itemsLibelle = new String[taille];
//		String[] itemsCodeEtLibelle = new String[taille];
		itemsCode = new String[taille];
		itemsLibelle = new String[taille];
		itemsCodeEtLibelle = new String[taille];
		
		int i = 0;
		for (String s : contenuCombo.keySet()) {
			if(!s.equals(TaTCPaiement.C_CODE_TYPE_TIERS) || (s.equals(TaTCPaiement.C_CODE_TYPE_TIERS) && conditionTiers)) {
				itemsCode[i] = s;
				itemsLibelle[i] = contenuCombo.get(s);
				itemsCodeEtLibelle[i] = s+" - "+contenuCombo.get(s);
				i++;
			}
		}

		//vue.getCbTypeCPaiement().setItems(itemsCode);
		vue.getCbType().setItems(itemsCodeEtLibelle);
		
		//TypeDoc c;
	}
	
	/**
	 * Retourne le code du type de document sélectionné dans la combo,
	 * si le code n'est pas trouvé, retourne une chaine vide
	 * @param comboItem
	 * @return
	 */
	private String findCodeTypeDocumentCombo(String comboItem) {
		
		int pos = vue.getCbType().indexOf(comboItem);
		
		if(pos>=0 && pos<itemsCode.length)
			return itemsCode[pos];
		else
			return "";
	}
	
	private String findComboItemDocumentCombo(String codeType) {
		
		boolean trouve = false;
		String item = "";
		int i = -1;
		while(!trouve && i<itemsCode.length) {
			i++;
			if(itemsCode[i].equals(codeType)) {
				item = itemsCodeEtLibelle[i];
				trouve = true;
			}
		}
		return item;
	}
	
	private void filtreCombo() {
//		Map<String,String[]> map = dao.getParamWhereSQL();
//
//		if(map==null) map = new HashMap<String,String[]>();
//		map.clear();
//		map.put("a.taTCPaiement."+Const.C_CODE_T_C_PAIEMENT,new String[]{"=","'"+findCodeTypeDocumentCombo(vue.getCbTypeCPaiement().getText())+"'"});
//		dao.setParamWhereSQL(map);
//		try {
//			if((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
//				swtCPaiement = (SWTCorrespondanceIDBoutique) selectedCPaiement.getValue();
//			}
//			modelCPaiement.razListEntity();
//			actRefresh();
//			if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
//					|| (dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)) {
//				
//				modelCPaiement.getListeObjet().add(swtCPaiement);
//				writableList = new WritableList(realm, modelCPaiement.getListeObjet(), classModel);
//				tableViewer.setInput(writableList);
//				tableViewer.refresh();
//				tableViewer.setSelection(new StructuredSelection(swtCPaiement));
//			}
//		} catch (Exception e1) {
//			logger.error("", e1);
//		}
	}
	
	class LgrUpdateValueStrategyComboTypeCPaiement extends LgrUpdateValueStrategy {
		public LgrUpdateValueStrategyComboTypeCPaiement() {
			super(new IConverter() {//ui2model
				@Override
				public Object getToType() {
					return String.class;
				}

				@Override
				public Object getFromType() {
					return String.class;
				}

				@Override
				public Object convert(Object fromObject) {
					if(fromObject==null) {
						return "";
					} else {
						return findCodeTypeDocumentCombo((String) fromObject);
					}
				}
			},
			new IConverter() {//model2ui
				@Override
				public Object getToType() {
					return String.class;
				}

				@Override
				public Object getFromType() {
					return String.class;
				}

				@Override
				public Object convert(Object fromObject) {
					
					if(fromObject==null) {
						return "";
					} else {
						return findComboItemDocumentCombo((String) fromObject);
					}
				}
			});
		}
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
			
			initCombo();
			
			vue.getCbType().addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent e) {
					modifMode();
					filtreCombo();		
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					widgetSelected(e);
				}
			});
			
			vue.getTfValeurBoutique().setEditable(false);
			vue.getTfValeurBoutique().setEnabled(false);
			
			vue.getTfValeurBdg().setEditable(false);
			vue.getTfValeurBdg().setEnabled(false);
			
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaTiersController", e);
		}
	}
	

	public void bind(PaCorrespondanceIDBoutiqueSWT ecran) {
		try {
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			tableViewer = new LgrTableViewer(ecran.getGrille());
			tableViewer.createTableCol(classModel,ecran.getGrille(), nomClassController, Const.C_FICHIER_LISTE_CHAMP_GRILLE, -1);
			listeChamp = tableViewer.setListeChampGrille(nomClassController, Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			
			LgrViewerSupport.bind(tableViewer, 
					new WritableList(modelCorrespondanceIDBoutique.remplirListe(), classModel),
					BeanProperties.values(listeChamp)
					);

			selectedCorrespondanceIDBoutique = ViewersObservables.observeSingleSelection(tableViewer);

			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			tableViewer.selectionGrille(0);

			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);
			
			mapComposantUpdateValueStrategy = new HashMap<Control, LgrUpdateValueStrategy>();
			mapComposantUpdateValueStrategy.put(vue.getCbType(), new LgrUpdateValueStrategyComboTypeCPaiement());

			bindingFormMaitreDetail(dbc, realm, selectedCorrespondanceIDBoutique,classModel);
			changementDeSelection();
			selectedCorrespondanceIDBoutique.addChangeListener(new IChangeListener() {

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
		if(selectedCorrespondanceIDBoutique!=null && selectedCorrespondanceIDBoutique.getValue()!=null) {
			if(((SWTCorrespondanceIDBoutique) selectedCorrespondanceIDBoutique.getValue()).getId()!=null) {
				taCorrespondanceIDBoutique = dao.findById(((SWTCorrespondanceIDBoutique) selectedCorrespondanceIDBoutique.getValue()).getId());
			}
			//null a tester ailleurs, car peut etre null en cas d'insertion
			fireChangementDeSelection(new ChangementDeSelectionEvent(SWTPaCorrespondanceIDBoutiqueController.this));
		}
	}

	public Composite getVue() {
		return vue;
	}

	public ResultAffiche configPanel(ParamAffiche param) {
		if (param != null) {
			Map<String,String[]> map = dao.getParamWhereSQL();
			if (((ParamAfficheCorrespondanceIDBoutique) param).getFocusDefautSWT() != null && !((ParamAfficheCorrespondanceIDBoutique) param).getFocusDefautSWT().isDisposed())
				((ParamAfficheCorrespondanceIDBoutique) param).getFocusDefautSWT().setFocus();
			else
				((ParamAfficheCorrespondanceIDBoutique) param).setFocusDefautSWT(vue.getGrille());
			vue.getLaTitreFormulaire().setText(((ParamAfficheCorrespondanceIDBoutique) param).getTitreFormulaire());
			vue.getLaTitreGrille().setText(((ParamAfficheCorrespondanceIDBoutique) param).getTitreGrille());
			vue.getLaTitreFenetre().setText(((ParamAfficheCorrespondanceIDBoutique) param).getSousTitre());
			
			//if(((ParamAfficheConditionPaiement)param).getIdTiers()!=null) {
			String operateur = "=";
			this.conditionTiers = ((ParamAfficheCorrespondanceIDBoutique)param).isConditionTiers();
			initItemCombo();
			vue.getCbType().select(0);
//			if(conditionTiers){
//				vue.getCbTypeCPaiement().setEnabled(false);
//				vue.getCbTypeCPaiement().select(vue.getCbTypeCPaiement().indexOf(findComboItemDocumentCombo(TaTCPaiement.C_CODE_TYPE_TIERS)));
//				vue.getCbCPaiementDocDefaut().setEnabled(false);
//				vue.getCbCPaiementDocDefaut().setVisible(false);
//				vue.getLaCPaiementDocDefaut().setVisible(false);
//			} else {
//				//condition documents
//				operateur = "<>";
//			}
			
			
			//}
			if (param.getEcranAppelant() != null) {
				ecranAppelant = param.getEcranAppelant();
			}
			
			if (param.getEcranAppelant() != null) {
				ecranAppelant = param.getEcranAppelant();
			}

			bind(vue);
			// permet de se positionner sur le 1er enregistrement et de remplir le formulaire
			tableViewer.selectionGrille(0);
			tableViewer.tri(classModel, nomClassController,Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			VerrouInterface.setVerrouille(false);
			setSwtOldCPaiement();

			filtreCombo();
			
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
		
//		mapInfosVerifSaisie.put(vue.getTfCODE_C_PAIEMENT(), new InfosVerifSaisie(new TaCorrespondanceIDBoutique(),Const.C_CODE_C_PAIEMENT,null));
//		mapInfosVerifSaisie.put(vue.getTfFIN_MOIS_C_PAIEMENT(), new InfosVerifSaisie(new TaCorrespondanceIDBoutique(),Const.C_FIN_MOIS_C_PAIEMENT,null));
//		mapInfosVerifSaisie.put(vue.getTfLIB_C_PAIEMENT(), new InfosVerifSaisie(new TaCorrespondanceIDBoutique(),Const.C_LIB_C_PAIEMENT,null));
//		mapInfosVerifSaisie.put(vue.getTfREPORT_C_PAIEMENT(), new InfosVerifSaisie(new TaCorrespondanceIDBoutique(),Const.C_REPORT_C_PAIEMENT,null));

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
		listeComposantFocusable.add(vue.getCbType());

		vue.getTfIdBdg().setToolTipText(Const.C_ID_BDG);
		vue.getTfValeurBdg().setToolTipText(Const.C_LIB_C_PAIEMENT);
		vue.getTfIdBoutique().setToolTipText(Const.C_ID_BOUTIQUE);

		mapComposantChamps.put(vue.getTfIdBdg(), Const.C_ID_BDG);
		mapComposantChamps.put(vue.getTfIdBoutique(),Const.C_ID_BOUTIQUE);
		mapComposantChamps.put(vue.getTfValeurBdg(), Const.C_VALEUR_BDG);
		mapComposantChamps.put(vue.getTfValeurBoutique(), Const.C_VALEUR_BOUTIQUE);

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
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION, vue.getTfIdBdg());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue.getTfIdBdg());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION, vue.getGrille());

		activeModifytListener();

//		vue.getTfFIN_MOIS_C_PAIEMENT().addVerifyListener(queDesChiffres);
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

	public SWTPaCorrespondanceIDBoutiqueController getThis() {
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
					.getString("PaCorrespondanceIDBoutique.Message.Enregistrer"))) {

				try {
					actEnregistrer();
				} catch (Exception e) {
					vue.getLaMessage().setText(e.getMessage());
					logger.error("", e);
				}
			} else
				//ibTaTable.annuler();
			break;
		case C_MO_CONSULTATION:
			break;
		default:
			break;
		}

		if (retour) {
			if (ecranAppelant instanceof SWTPaAideControllerSWT) {
				setListeEntity(getModelCorrespondanceIDBoutique().remplirListe());
				dao.initValeurIdTable(taCorrespondanceIDBoutique);
				fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
						dao.getChampIdEntite(), dao.getValeurIdTable(),
						selectedCorrespondanceIDBoutique.getValue())));
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
			if(getFocusAvantAideSWT().equals(vue.getTfIdBdg())){
				TaTva entity = null;
				TaTvaDAO dao = new TaTvaDAO(getEm());
				entity = dao.findById(Integer.parseInt(((ResultAffiche) evt.getRetour()).getResult()));
				dao = null;
				vue.getTfValeurBdg().setText(entity.getLibelleTva());
			}
			if (getFocusAvantAideSWT() instanceof Table) {
				if (getFocusAvantAideSWT() == vue.getGrille()) {
					if(((ResultAffiche) evt.getRetour()).getSelection()!=null)
						tableViewer.setSelection(((ResultAffiche) evt.getRetour()).getSelection(),true);
				}
			}
		} else 	if (evt.getRetour() != null) {
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
				setSwtOldCPaiement();
				swtCorrespondanceIDBoutique = new SWTCorrespondanceIDBoutique();
				taCorrespondanceIDBoutique = new TaCorrespondanceIDBoutique();			
				
				if(conditionTiers){
					//vue.getCbType().select(vue.getCbType().indexOf(findComboItemDocumentCombo(TaTCPaiement.C_CODE_TYPE_TIERS)));
				}
				//taCorrespondanceIDBoutique.setTaTCPaiement(taTCPaiementDAO.findByCode(findCodeTypeDocumentCombo(vue.getCbTypeCPaiement().getText())));
				swtCorrespondanceIDBoutique.setTypeTable(TaCorrespondanceIDBoutiqueDAO.TYPE_TVA);
				taCorrespondanceIDBoutique.setTypeTable(TaCorrespondanceIDBoutiqueDAO.TYPE_TVA);
					
				dao.inserer(taCorrespondanceIDBoutique);
				modelCorrespondanceIDBoutique.getListeObjet().add(swtCorrespondanceIDBoutique);
				writableList = new WritableList(realm, modelCorrespondanceIDBoutique.getListeObjet(), classModel);
				tableViewer.setInput(writableList);
				tableViewer.refresh();
				tableViewer.setSelection(new StructuredSelection(swtCorrespondanceIDBoutique));
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
				setSwtOldCPaiement();
				taCorrespondanceIDBoutique = dao.findById(((SWTCorrespondanceIDBoutique) selectedCorrespondanceIDBoutique.getValue()).getId());
			}else{
				if(!setSwtOldCorrespondanceIDBoutiqueRefresh())throw new Exception();
			}
			
			dao.modifier(taCorrespondanceIDBoutique);			
			initEtatBouton();
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		}
	}
	
	public boolean containsEntity(TaCorrespondanceIDBoutique entite){
		if(modelCorrespondanceIDBoutique.getListeEntity()!=null){
			for (Object e : modelCorrespondanceIDBoutique.getListeEntity()) {
				if(((TaCorrespondanceIDBoutique)e).getId()==
					entite.getId())return true;
			}
		}
		return false;
	}

	public boolean setSwtOldCorrespondanceIDBoutiqueRefresh() {
		try {	
			if (selectedCorrespondanceIDBoutique.getValue()!=null){
				TaCorrespondanceIDBoutique taArticleOld =dao.findById(taCorrespondanceIDBoutique.getId());
				taArticleOld=dao.refresh(taArticleOld);
				if(containsEntity(taCorrespondanceIDBoutique)) 
					modelCorrespondanceIDBoutique.getListeEntity().remove(taCorrespondanceIDBoutique);
				if(!taCorrespondanceIDBoutique.getVersionObj().equals(taArticleOld.getVersionObj())){
					taCorrespondanceIDBoutique=taArticleOld;
					if(!containsEntity(taCorrespondanceIDBoutique)) 
						modelCorrespondanceIDBoutique.getListeEntity().add(taCorrespondanceIDBoutique);					
					actRefresh();
					dao.messageNonAutoriseModification();
				}
				taCorrespondanceIDBoutique=taArticleOld;
				if(!containsEntity(taCorrespondanceIDBoutique)) 
					modelCorrespondanceIDBoutique.getListeEntity().add(taCorrespondanceIDBoutique);
				changementDeSelection();
				this.swtOldCorrespondanceIDBoutique=SWTCorrespondanceIDBoutique.copy((SWTCorrespondanceIDBoutique)selectedCorrespondanceIDBoutique.getValue());
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
						.getString("PaCorrespondanceIDBoutique.Message.Supprimer"))) {

					dao.begin(transaction);
					TaCorrespondanceIDBoutique u = dao.findById(((SWTCorrespondanceIDBoutique) selectedCorrespondanceIDBoutique.getValue()).getId());
					//u.getTaTCPaiement().setTaCorrespondanceIDBoutique(null);
					//u.setTaTCPaiement(null);
					dao.supprimer(u);
					dao.commit(transaction);
					Object suivant=tableViewer.getElementAt(tableViewer.getTable().getSelectionIndex()+1);
					//modelCorrespondanceIDBoutique.removeEntity(taCorrespondanceIDBoutique);
					modelCorrespondanceIDBoutique.removeFromModel(tableViewer,taCorrespondanceIDBoutique);
					//taCPaiement=null;
					dao.getModeObjet().setMode(ModeObjet.EnumModeObjet.C_MO_CONSULTATION);
					if(suivant!=null)tableViewer.setSelection(new StructuredSelection(suivant),true);
					else tableViewer.selectionGrille(0);
					actRefresh();
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
		// verifier si l'objet est en modification ou en consultation
		// si modification ou insertion, alors demander si annulation des
		// modifications si ok, alors annulation si pas ok, alors arreter le processus d'annulation
		// si consultation declencher l'action "fermer".
		try {
			VerrouInterface.setVerrouille(true);
			//inputVerifier inputVerifier = getFocusCourant().getInputVerifier();
			//getFocusCourant().setInputVerifier(null);
			//switch (ibTaTable.getFModeObjet().getMode()) {
			switch (dao.getModeObjet().getMode()) {
			case C_MO_INSERTION:
				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("PaCorrespondanceIDBoutique.Message.Annuler")))) {
					remetTousLesChampsApresAnnulationSWT(dbc);
					if(((SWTCorrespondanceIDBoutique) selectedCorrespondanceIDBoutique.getValue()).getId()==null){
						modelCorrespondanceIDBoutique.getListeObjet().remove(((SWTCorrespondanceIDBoutique) selectedCorrespondanceIDBoutique.getValue()));
						writableList = new WritableList(realm, modelCorrespondanceIDBoutique.getListeObjet(), classModel);
						tableViewer.setInput(writableList);
						tableViewer.refresh();
						tableViewer.selectionGrille(0);
					}
					dao.annuler(taCorrespondanceIDBoutique);
					hideDecoratedFields();
				}
				initEtatBouton();
				break;
			case C_MO_EDITION:
				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("PaCorrespondanceIDBoutique.Message.Annuler")))) {
					//int rang = getGrille().getSelectionIndex();
					int rang = modelCorrespondanceIDBoutique.getListeObjet().indexOf(selectedCorrespondanceIDBoutique.getValue());
					// selectedCPaiement.setValue(swtOldCPaiement);
					remetTousLesChampsApresAnnulationSWT(dbc);
					modelCorrespondanceIDBoutique.getListeObjet().set(rang, swtOldCorrespondanceIDBoutique);
					writableList = new WritableList(realm, modelCorrespondanceIDBoutique.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.setSelection(new StructuredSelection(swtOldCorrespondanceIDBoutique), true);
					dao.annuler(taCorrespondanceIDBoutique);
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
			// getFocusCourant().setInputVerifier(inputVerifier);
			// initEtatBouton();
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
//		fonctionGetInfosXmlAndProperties.setValueMapAttributeTable(TaCorrespondanceIDBoutique.class.getSimpleName()+".head");
//		fonctionGetInfosXmlAndProperties.setValueMapAttributeTable(TaCorrespondanceIDBoutique.class.getSimpleName()+".detail");
//		
//		LinkedHashMap<String,AttributElementResport> mapAttributeTablHead = fonctionGetInfosXmlAndProperties.getMapAttributeTablHead(); 
//		LinkedHashMap<String,AttributElementResport> mapAttributeTablDetail = fonctionGetInfosXmlAndProperties.getMapAttributeTablDetail();
//		
//		Collection<TaCorrespondanceIDBoutique> collectionTaCorrespondanceIDBoutique = modelCPaiement.getListeEntity();
//		
//		fonctionGetInfosXmlAndProperties.findInfosFileXml(TaCorrespondanceIDBoutique.class.getName(),SWTCorrespondanceIDBoutique.class.getName(),
//				listeChamp,"mapping");
//		fonctionGetInfosXmlAndProperties.getInfosObjetJPA(taCPaiement);
//
//		ConstEdition constEdition = new ConstEdition(); 
////		Impression impression = new Impression(constEdition,taCPaiement,collectionTaCorrespondanceIDBoutique,nomChampIdTable,taCPaiement.getIdCPaiement());
//		String nomDossier = null;
//
//		int nombreLine = collectionTaCorrespondanceIDBoutique.size();
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
//			String folderEditionDynamique = Const.C_RCP_INSTANCE_LOCATION+"/"+Const.C_NOM_PROJET_TMP+"/"+TaCorrespondanceIDBoutique.class.getSimpleName();
//			constEdition.makeFolderEditions(folderEditionDynamique);
//			Path pathFileReport = new Path(folderEditionDynamique+"/"+Const.C_NOM_VU_COND_PAIE+".rptdesign");
//			final String pathFileReportDynamic = pathFileReport.toPortableString();
//
//			MakeDynamiqueReport DynamiqueReport = new MakeDynamiqueReport(constEdition.getNameTableEcran(),
//					constEdition.getNameTableBDD(),pathFileReportDynamic,Const.C_NOM_VU_COND_PAIE,
//					ConstEdition.PAGE_ORIENTATION_LANDSCAPE,nomDossier); 
//			
//			/**************************************************************/
//			DynamiqueReport.setFonctionGetInfosXml(fonctionGetInfosXmlAndProperties);
//			DynamiqueReport.setNomObjet(TaTTel.class.getSimpleName());
//			/**************************************************************/
//
//			Map<String, AttributElementResport> attribuGridHeader = new LinkedHashMap<String, AttributElementResport>();
//			String nameHeaderTitle = ConstEditionTiers.TITLE_EDITION_COND_PAIE;
//			attribuGridHeader.put(nameHeaderTitle, new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
//					ConstEdition.FONT_SIZE_XX_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
//					ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE,""));
//			String nameSousHeaderTitle = ConstEditionTiers.SOUS_TITLE_EDITION_COND_PAIE;
//			attribuGridHeader.put(nameSousHeaderTitle, new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
//					ConstEdition.FONT_SIZE_X_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
//					ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE,ConstEdition.COLOR_GRAY));
//
//			//DynamiqueReport.buildDesignConfig();
//			ConstEdition.CONTENT_COMMENTS = ConstEditionTiers.COMMENTAIRE_EDITION_DEFAUT;
//			DynamiqueReport.setSimpleNameEntity(TaCorrespondanceIDBoutique.class.getSimpleName());
//			DynamiqueReport.initializeBuildDesignReportConfig();
//			DynamiqueReport.makePageMater("1", "1", "1", "1", "100");
//			DynamiqueReport.makeReportHeaderGrid(3,5,100,ConstEdition.UNITS_PERCENTAGE,attribuGridHeader);
////			DynamiqueReport.makeReportTableDB(100,ConstEdition.UNITS_PERCENTAGE,
////					Const.C_NOM_VU_T_TEL,attribuTabHeader,attribuTabDetail,1,1,2,5,"30");
//			DynamiqueReport.biuldTableReport("100", ConstEdition.UNITS_PERCENTAGE, 
//					Const.C_NOM_VU_COND_PAIE,1,1,2,"40", mapAttributeTablHead, mapAttributeTablDetail);
//			DynamiqueReport.savsAsDesignHandle();
//			
////			impression.imprimer(true,pathFileReportDynamic,null,"Condition Paiement",TaCorrespondanceIDBoutique.class.getSimpleName());
//
//			/** 01/03/2010 modifier les editions (zhaolin) **/
//			BaseImpressionEdition impressionEdition = new BaseImpressionEdition(constEdition,taCPaiement,
//					getEm(),collectionTaCorrespondanceIDBoutique,taCPaiement.getIdCPaiement());
//			impressionEdition.impressionEditionTypeEntity(pathFileReportDynamic,"Condition Paiement");
//			
//		}
	}

	@Override
	protected boolean aideDisponible() {
		boolean result = false;
		//switch ((getThis().ibTaTable.getFModeObjet().getMode())) {
		switch ((getThis().dao.getModeObjet().getMode())) {
		case C_MO_CONSULTATION:
			break;
		case C_MO_EDITION:
		case C_MO_INSERTION:
			if(getFocusCourantSWT().equals(vue.getTfIdBdg())
				|| getFocusCourantSWT().equals(vue.getTfIdBoutique())
				)
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
				//SWTBaseControllerSWTStandard controllerEcranCreation = null;
				JPABaseControllerSWTStandard controllerEcranCreation = null;
				ParamAffiche parametreEcranCreation = null;
				IEditorPart editorCreation = null;
				String editorCreationId = null;
				IEditorInput editorInputCreation = null;
				Shell s2 = new Shell(s, LgrShellUtil.styleLgr);

				//switch ((getThis().ibTaTable.getFModeObjet().getMode())) {
				switch ((getThis().dao.getModeObjet().getMode())) {
				case C_MO_CONSULTATION:
					break;
				case C_MO_EDITION:
				case C_MO_INSERTION:
					if(getFocusCourantSWT().equals(vue.getTfIdBdg())){
						
						/*Faire un "if" en fonction du type de correspondance dans la combo, pour l'instant uniquement TVA*/
						PaTVASWT paTvaSWT = new PaTVASWT(s2,SWT.NULL);
						SWTPaTvaController swtPaTvaController = new SWTPaTvaController(paTvaSWT);

						editorCreationId = EditorTva.ID;
						editorInputCreation = new EditorInputTva();

						ParamAfficheTva paramAfficheTva = new ParamAfficheTva();
						paramAfficheAideRecherche.setJPQLQuery(new TaTvaDAO(getEm()).getJPQLQuery());
						paramAfficheTva.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheTva.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaTvaController;
						parametreEcranCreation = paramAfficheTva;

						paramAfficheAideRecherche.setTypeEntite(TaTva.class);
						//paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_TVA);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_ID_TVA);
						paramAfficheAideRecherche.setDebutRecherche(""/*vue.getTfCODE_TVA().getText()*/);
						paramAfficheAideRecherche.setControllerAppelant(SWTPaCorrespondanceIDBoutiqueController.this);
						paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTva,TaTvaDAO,TaTva>(SWTTva.class,dao.getEntityManager()));
						paramAfficheAideRecherche.setTypeObjet(swtPaTvaController.getClassModel());

						paramAfficheAideRecherche.setChampsIdentifiant(swtPaTvaController.getDao().getChampIdTable());
					}
					if(getFocusCourantSWT().equals(vue.getTfIdBoutique())){
						paramAfficheAideRecherche.setJPQLQuery("");

						paramAfficheAideRecherche.setTypeEntite(Taxes.class);
						paramAfficheAideRecherche.setChampsRecherche("id");
						paramAfficheAideRecherche.setDebutRecherche("");
						paramAfficheAideRecherche.setControllerAppelant(SWTPaCorrespondanceIDBoutiqueController.this);
						ModelTaxPrestashop modelTaxPrestashop = new ModelTaxPrestashop();
						modelTaxPrestashop.setWs(ws);
						paramAfficheAideRecherche.setModel(modelTaxPrestashop);
						paramAfficheAideRecherche.setTypeObjet(SWTTaxeBoutique.class);

						paramAfficheAideRecherche.setCleListeTitre("SWTTaxeBoutique");
						paramAfficheAideRecherche.setChampsIdentifiant("id");
						paramAfficheAideRecherche.setAfficheNouveau(false);
					}
					break;
				default:
					break;
				}

				if (paramAfficheAideRecherche.getJPQLQuery() != null) {

					PaAideRechercheSWT paAideRecherche1 = new PaAideRechercheSWT(s,SWT.NULL);
					SWTPaAideRechercheControllerSWT paAideRechercheController1 = new SWTPaAideRechercheControllerSWT(paAideRecherche1);

					// Parametrage de la recherche
					paramAfficheAideRecherche.setFocusSWT(((PaAideRechercheSWT) paAideRechercheController1.getVue()).getTfChoix());
					paramAfficheAideRecherche.setRefCreationSWT(controllerEcranCreation);
					paramAfficheAideRecherche.setEditorCreation(editorCreation);
					paramAfficheAideRecherche.setEditorCreationId(editorCreationId);
					paramAfficheAideRecherche.setEditorInputCreation(editorInputCreation);
					paramAfficheAideRecherche.setParamEcranCreation(parametreEcranCreation);
					paramAfficheAideRecherche.setShellCreation(s2);
					paAideRechercheController1.configPanel(paramAfficheAideRecherche);
					// paramAfficheAideRecherche.setFocusDefaut(paAideRechercheController1.getVue().getTfChoix());

					// Ajout d'une recherche
					paAideController.addRecherche(paAideRechercheController1, paramAfficheAideRecherche.getTitreRecherche());

					// Parametrage de l'ecran d'aide principal
					ParamAfficheAideSWT paramAfficheAideSWT = new ParamAfficheAideSWT();
					ParamAfficheAide paramAfficheAide = new ParamAfficheAide();

					// enregistrement pour le retour de l'ecran d'aide
					paAideController.addRetourEcranListener(getThis());
					Control focus = vue.getShell().getDisplay().getFocusControl();
					// affichage de l'ecran d'aide principal (+ ses recherches)

					dbc.getValidationStatusMap().removeMapChangeListener(changeListener);
					//LgrShellUtil.afficheAideSWT(paramAfficheAide, null, paAide,paAideController, s);
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
		String validationContext = "CORRESPONDANCE_ID_BOUTIQUE";
		try {
			//IStatus s = null;
			IStatus s = new Status(IStatus.OK, "fr.legrain.articles.export.catalogue.prestashop", "");
			boolean verrouilleModifCode = false;
			int change=0;
			
			if(nomChamp.equals(Const.C_ID_BDG) && value!=null) {
				TaTva entity = null;
				TaTvaDAO dao = new TaTvaDAO(getEm());
				entity = dao.findById(LibConversion.stringToInteger(value.toString()));
				dao = null;
				vue.getTfValeurBdg().setText(entity.getLibelleTva());
			}
			
			if(nomChamp.equals(Const.C_ID_BOUTIQUE) && value!=null) {
				String valeurBoutique = "";
				try {
					valeurBoutique += ws.findTax(LibConversion.stringToInteger(value.toString())).getName().get(0).getValue();
				} catch (/*JAXB*/Exception e) {
					valeurBoutique += "Erreur de récupération des données de la boutique";
					logger.error("",e);
				}
				vue.getTfValeurBoutique().setText(valeurBoutique);
			}
			
//			if(nomChamp.equals(Const.C_CODE_T_C_PAIEMENT)) {
//				TaTCPaiementDAO dao = new TaTCPaiementDAO(getEm());
//				
//				dao.setModeObjet(getDao().getModeObjet());
//				TaTCPaiement f = new TaTCPaiement();
//				PropertyUtils.setSimpleProperty(f, nomChamp, value);
//				s = dao.validate(f,nomChamp,validationContext);
//
//				if(s.getSeverity()!=IStatus.ERROR ){
//					f = dao.findByCode((String)value);
//					taCorrespondanceIDBoutique.setTaTCPaiement(f);
//				}
//				dao = null;
//			} else {
			
//				TaCorrespondanceIDBoutique u = new TaCorrespondanceIDBoutique();
//				PropertyUtils.setSimpleProperty(u, nomChamp, value);
//				if(((SWTCorrespondanceIDBoutique) selectedCorrespondanceIDBoutique.getValue()).getIdCPaiement()!=null) {
//					u.setIdCPaiement(((SWTCorrespondanceIDBoutique) selectedCorrespondanceIDBoutique.getValue()).getIdCPaiement());
//				}
//				if(nomChamp.equals(Const.C_CODE_C_PAIEMENT)){
//					verrouilleModifCode = true;
//				}
//
//				s = dao.validate(u,nomChamp,validationContext,verrouilleModifCode);
//				if(s.getSeverity()!=IStatus.ERROR && change!=0) {
//					  
//				}
				
//			}
			return s;
		} catch (Exception e) {
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
			if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)||
					(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)) {
				
//					TaTCPaiement taTCPaiement = taTCPaiementDAO.refresh(taTCPaiementDAO.findByCode(findCodeTypeDocumentCombo(vue.getCbTypeCPaiement().getText())));
//					taCorrespondanceIDBoutique.setTaTCPaiement(taTCPaiement);
				
				LgrDozerMapper<SWTCorrespondanceIDBoutique,TaCorrespondanceIDBoutique> mapper = new LgrDozerMapper<SWTCorrespondanceIDBoutique,TaCorrespondanceIDBoutique>();
				mapper.map((SWTCorrespondanceIDBoutique) selectedCorrespondanceIDBoutique.getValue(),taCorrespondanceIDBoutique);
				
//				if(taCorrespondanceIDBoutique.getTaTCPaiement().getTaCorrespondanceIDBoutique()==null) {
//					taCorrespondanceIDBoutique.getTaTCPaiement().setTaCorrespondanceIDBoutique(taCorrespondanceIDBoutique);	
//				}
//				else if(((SWTCorrespondanceIDBoutique) selectedCorrespondanceIDBoutique.getValue())!=null 
//						&& ((SWTCorrespondanceIDBoutique) selectedCorrespondanceIDBoutique.getValue()).getDefaut()!=null
//						&& ((SWTCorrespondanceIDBoutique) selectedCorrespondanceIDBoutique.getValue()).getDefaut()) {
//					taCorrespondanceIDBoutique.getTaTCPaiement().setTaCorrespondanceIDBoutique(taCorrespondanceIDBoutique);	
//				}
				
				taCorrespondanceIDBoutique=dao.enregistrerMerge(taCorrespondanceIDBoutique);

			} 
			dao.commit(transaction);
			//modelCorrespondanceIDBoutique.addEntity(taCorrespondanceIDBoutique);
			modelCorrespondanceIDBoutique.addToModel(tableViewer,taCorrespondanceIDBoutique);
			
			transaction = null;
			changementDeSelection();
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
			vue.getTfIdBdg().setEditable(!isUtilise());
			changeCouleur(vue);
	    } catch (Exception e) {
		vue.getLaMessage().setText(e.getMessage());
	    }
	}

	public SWTCorrespondanceIDBoutique getSwtOldCorrespondanceIDBoutique() {
		return swtOldCorrespondanceIDBoutique;
	}

	public void setSwtOldCorrespondanceIDBoutique(SWTCorrespondanceIDBoutique swtOldCPaiement) {
		this.swtOldCorrespondanceIDBoutique = swtOldCPaiement;
	}

	public void setSwtOldCPaiement() {
		if (selectedCorrespondanceIDBoutique!=null && selectedCorrespondanceIDBoutique.getValue() != null)
			this.swtOldCorrespondanceIDBoutique = SWTCorrespondanceIDBoutique.copy((SWTCorrespondanceIDBoutique) selectedCorrespondanceIDBoutique.getValue());
		else {
			if (tableViewer.selectionGrille(0)){
				this.swtOldCorrespondanceIDBoutique = SWTCorrespondanceIDBoutique.copy((SWTCorrespondanceIDBoutique) selectedCorrespondanceIDBoutique.getValue());
				tableViewer.setSelection(new StructuredSelection(
						(SWTCorrespondanceIDBoutique) selectedCorrespondanceIDBoutique.getValue()), true);
			}
		}
	}

	public void setVue(PaCorrespondanceIDBoutiqueSWT vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();
		mapComposantDecoratedField.put(vue.getTfIdBdg(), vue.getFieldIdBdg());
		mapComposantDecoratedField.put(vue.getTfValeurBdg(), vue.getFieldValeurBdg());
		mapComposantDecoratedField.put(vue.getTfIdBoutique(), vue.getFieldIdBoutique());
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

		TaCorrespondanceIDBoutique u = taCorrespondanceIDBoutique;
		if (u!=null && u.getId()==0)
			u=dao.findById(u.getId());
		writableList = new WritableList(realm, modelCorrespondanceIDBoutique.remplirListe(), classModel);
		tableViewer.setInput(writableList);

		tableViewer.selectionGrille(
				tableViewer.selectionGrille(selectedCorrespondanceIDBoutique.getValue()));
		
		if(u != null) {
			Iterator<SWTCorrespondanceIDBoutique> ite = modelCorrespondanceIDBoutique.getListeObjet().iterator();
			SWTCorrespondanceIDBoutique tmp = null;
			int i = 0;
			boolean trouve = false;
			while (ite.hasNext() && !trouve) {
				tmp = ite.next();
				if(tmp.getId()==u.getId()) {
					tableViewer.setSelection(new StructuredSelection(tmp));
					trouve = true;
				}
				i++;
			}
		}
	}

	public ModelCorrespondanceIDBoutique getModelCorrespondanceIDBoutique() {
		return modelCorrespondanceIDBoutique;
	}

	public boolean isUtilise(){
		return (((SWTCorrespondanceIDBoutique)selectedCorrespondanceIDBoutique.getValue()).getId()!=null &&
		!dao.recordModifiable(dao.getNomTable(),
				((SWTCorrespondanceIDBoutique)selectedCorrespondanceIDBoutique.getValue()).getId()))||
				!dao.autoriseModification(taCorrespondanceIDBoutique);		
	}

	public TaCorrespondanceIDBoutiqueDAO getDao() {
		return dao;
	}
	
	public void modifMode(){
		if (!VerrouInterface.isVerrouille() ){
			try {
				if(!daoStandard.dataSetEnModif()) {
					if(!modelCorrespondanceIDBoutique.getListeEntity().isEmpty()) {
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

package fr.legrain.tiers.ecran;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.commons.beanutils.PropertyUtils;
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
import org.eclipse.core.runtime.Path;
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

import fr.legrain.articles.dao.TaArticle;
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
import fr.legrain.gestCom.Module_Tiers.SWTCPaiement;
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
import fr.legrain.tiers.dao.TaCPaiement;
import fr.legrain.tiers.dao.TaCPaiementDAO;
import fr.legrain.tiers.dao.TaTCPaiement;
import fr.legrain.tiers.dao.TaTCPaiementDAO;
import fr.legrain.tiers.dao.TaTTel;
import fr.legrain.tiers.dao.TaTTiers;
import fr.legrain.tiers.dao.TaTTiersDAO;
import fr.legrain.tiers.editor.EditorConditionPaiement;
import fr.legrain.tiers.editor.EditorInputConditionPaiement;

public class SWTPaConditionPaiementController extends JPABaseControllerSWTStandard
implements RetourEcranListener {

	static Logger logger = Logger.getLogger(SWTPaConditionPaiementController.class.getName());
	private PaConditionPaiementSWT vue = null;
	private TaCPaiementDAO dao = null;
	private TaTCPaiementDAO taTCPaiementDAO = null;
	
	private String idTypeConditionPaiement = null;
	private boolean conditionTiers = true;
	
	
	private Object ecranAppelant = null;
	private SWTCPaiement swtCPaiement;
	private SWTCPaiement swtOldCPaiement;
	private Realm realm;
	private DataBindingContext dbc;

	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private IObservableValue selectedCPaiement;
	private String[] listeChamp;
	private String nomClassController = this.getClass().getSimpleName();
	private MapChangeListener changeListener = new MapChangeListener();
	private Class classModel = SWTCPaiement.class;
	private ModelGeneralObjet<SWTCPaiement,TaCPaiementDAO,TaCPaiement> modelCPaiement = null;//new ModelGeneralObjet<SWTCPaiement,TaCPaiementDAO,TaCPaiement>(dao,classModel);	
	private LgrDozerMapper<SWTCPaiement,TaCPaiement> mapperUIToModel  = new LgrDozerMapper<SWTCPaiement,TaCPaiement>();
	private TaCPaiement taCPaiement = null;
	
	private String[] itemsCode = null;
	private String[] itemsLibelle = null;
	private String[] itemsCodeEtLibelle = null;
	
	public SWTPaConditionPaiementController(PaConditionPaiementSWT vue) {
		this(vue,null);
	}

	public SWTPaConditionPaiementController(PaConditionPaiementSWT vue,EntityManager em) {
		if(em!=null) {
			setEm(em);
		}
		dao = new TaCPaiementDAO(getEm());
		taTCPaiementDAO = new TaTCPaiementDAO(getEm());
		modelCPaiement = new ModelGeneralObjet<SWTCPaiement,TaCPaiementDAO,TaCPaiement>(dao,classModel);
		setVue(vue);

		// a faire avant l'initialisation du Binding
		vue.getGrille().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setSwtOldCPaiement();
			}
		});
		vue.getShell().addShellListener(this);

		// Branchement action annuler : empeche la fermeture automatique de la
		// fenetre sur ESC
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
		List<TaTCPaiement> l = taTCPaiementDAO.selectAll();
		for (int i = 0; i < l.size(); i++) {
			contenuCombo.put(
					l.get(i).getCodeTCPaiement(), 
					l.get(i).getLiblTCPaiement()
					);
		}
		
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
			
			if(!contenuCombo.get(s).equals(TaTCPaiement.C_CODE_TYPE_TIERS) || (contenuCombo.get(s).equals(TaTCPaiement.C_CODE_TYPE_TIERS) && conditionTiers)) {
				itemsCode[i] = s;
				itemsLibelle[i] = contenuCombo.get(s);
				itemsCodeEtLibelle[i] = s+" - "+contenuCombo.get(s);
				i++;
			}
		}

		//vue.getCbTypeCPaiement().setItems(itemsCode);
		vue.getCbTypeCPaiement().setItems(itemsCodeEtLibelle);
		
		//TypeDoc c;
	}
	
	/**
	 * Retourne le code du type de document sélectionné dans la combo,
	 * si le code n'est pas trouvé, retourne une chaine vide
	 * @param comboItem
	 * @return
	 */
	private String findCodeTypeDocumentCombo(String comboItem) {
		
		int pos = vue.getCbTypeCPaiement().indexOf(comboItem);
		
		if(pos>=0 && pos<itemsCode.length)
			return itemsCode[pos];
		else
			return "";
	}
	
	private String findComboItemDocumentCombo(String codeType) {
		
		boolean trouve = false;
		String item = "";
		int i = -1;
		while(!trouve && i<itemsLibelle.length) {
			i++;
			if(itemsLibelle[i].equals(codeType)) {
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
//				swtCPaiement = (SWTCPaiement) selectedCPaiement.getValue();
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
			
			vue.getCbTypeCPaiement().addSelectionListener(new SelectionListener() {
				
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
			
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaTiersController", e);
		}
	}
	

	public void bind(PaConditionPaiementSWT paAdresseSWT) {
		try {
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			tableViewer = new LgrTableViewer(paAdresseSWT.getGrille());
			tableViewer.createTableCol(classModel,paAdresseSWT.getGrille(), nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE,-1);
			listeChamp = tableViewer.setListeChampGrille(nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE);

//			ObservableListContentProvider viewerContent = new ObservableListContentProvider();
//			tableViewer.setContentProvider(viewerContent);
//
//			IObservableMap[] attributeMaps = BeansObservables.observeMaps(
//					viewerContent.getKnownElements(), classModel,listeChamp);
//
//			tableViewer.setLabelProvider(new ObservableMapLabelProvider(attributeMaps));
//			writableList = new WritableList(realm, modelCPaiement.remplirListe(), classModel);
//			tableViewer.setInput(writableList);
			
			// Set up data binding.
			LgrViewerSupport.bind(tableViewer, 
					new WritableList(modelCPaiement.remplirListe(), classModel),
					BeanProperties.values(listeChamp)
					);

			selectedCPaiement = ViewersObservables.observeSingleSelection(tableViewer);

			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			tableViewer.selectionGrille(0);

			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);
			
			mapComposantUpdateValueStrategy = new HashMap<Control, LgrUpdateValueStrategy>();
			mapComposantUpdateValueStrategy.put(vue.getCbTypeCPaiement(), new LgrUpdateValueStrategyComboTypeCPaiement());

			bindingFormMaitreDetail(dbc, realm, selectedCPaiement,classModel);
			changementDeSelection();
			selectedCPaiement.addChangeListener(new IChangeListener() {

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
		if(selectedCPaiement!=null && selectedCPaiement.getValue()!=null) {
			if(((SWTCPaiement) selectedCPaiement.getValue()).getIdCPaiement()!=null) {
				taCPaiement = dao.findById(((SWTCPaiement) selectedCPaiement.getValue()).getIdCPaiement());
			}
			//null a tester ailleurs, car peut etre null en cas d'insertion
			fireChangementDeSelection(new ChangementDeSelectionEvent(SWTPaConditionPaiementController.this));
		}
	}

	public Composite getVue() {
		return vue;
	}

	public ResultAffiche configPanel(ParamAffiche param) {
		if (param != null) {
			Map<String,String[]> map = dao.getParamWhereSQL();
			//ibTaTable.ouvreDataset();
			if (((ParamAfficheConditionPaiement) param).getFocusDefautSWT() != null && !((ParamAfficheConditionPaiement) param).getFocusDefautSWT().isDisposed())
				((ParamAfficheConditionPaiement) param).getFocusDefautSWT().setFocus();
			else
				((ParamAfficheConditionPaiement) param).setFocusDefautSWT(vue.getGrille());
			vue.getLaTitreFormulaire().setText(((ParamAfficheConditionPaiement) param).getTitreFormulaire());
			vue.getLaTitreGrille().setText(((ParamAfficheConditionPaiement) param).getTitreGrille());
			vue.getLaTitreFenetre().setText(((ParamAfficheConditionPaiement) param).getSousTitre());
			
			//if(((ParamAfficheConditionPaiement)param).getIdTiers()!=null) {
			String operateur = "=";
			this.conditionTiers = ((ParamAfficheConditionPaiement)param).isConditionTiers();
			initItemCombo();
			vue.getCbTypeCPaiement().select(0);
			if(conditionTiers){
				vue.getCbTypeCPaiement().setEnabled(false);
				vue.getCbTypeCPaiement().select(vue.getCbTypeCPaiement().indexOf(findComboItemDocumentCombo(TaTCPaiement.C_CODE_TYPE_TIERS)));
				vue.getCbCPaiementDocDefaut().setEnabled(false);
				vue.getCbCPaiementDocDefaut().setVisible(false);
				vue.getLaCPaiementDocDefaut().setVisible(false);
			} else {
				//condition documents
				operateur = "<>";
			}
			if(((ParamAfficheConditionPaiement)param).getIdTypeCond()!=null){
				this.idTypeConditionPaiement=((ParamAfficheConditionPaiement)param).getIdTypeCond().toString();
				if(map==null) map = new HashMap<String,String[]>();
				map.clear();
				map.put("a.taTCPaiement."+Const.C_ID_T_C_PAIEMENT,new String[]{operateur,idTypeConditionPaiement});
				dao.setParamWhereSQL(map);
			}
			dao.setParamWhereSQL(map);
			
			//}
			if (param.getEcranAppelant() != null) {
				ecranAppelant = param.getEcranAppelant();
			}
			
			if (param.getEcranAppelant() != null) {
				ecranAppelant = param.getEcranAppelant();
			}

			bind(vue);
			// permet de se positionner sur le 1er enregistrement et de remplir
			// le formulaire
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
		
		mapInfosVerifSaisie.put(vue.getTfCODE_C_PAIEMENT(), new InfosVerifSaisie(new TaCPaiement(),Const.C_CODE_C_PAIEMENT,null));
		mapInfosVerifSaisie.put(vue.getTfFIN_MOIS_C_PAIEMENT(), new InfosVerifSaisie(new TaCPaiement(),Const.C_FIN_MOIS_C_PAIEMENT,null));
		mapInfosVerifSaisie.put(vue.getTfLIB_C_PAIEMENT(), new InfosVerifSaisie(new TaCPaiement(),Const.C_LIB_C_PAIEMENT,null));
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
		listeComposantFocusable.add(vue.getCbTypeCPaiement());

		vue.getTfCODE_C_PAIEMENT().setToolTipText(Const.C_CODE_C_PAIEMENT);
		vue.getTfFIN_MOIS_C_PAIEMENT().setToolTipText(Const.C_FIN_MOIS_C_PAIEMENT);
		vue.getTfLIB_C_PAIEMENT().setToolTipText(Const.C_LIB_C_PAIEMENT);
		vue.getTfREPORT_C_PAIEMENT().setToolTipText(Const.C_REPORT_C_PAIEMENT);

		mapComposantChamps.put(vue.getTfCODE_C_PAIEMENT(), Const.C_CODE_C_PAIEMENT);
		mapComposantChamps.put(vue.getTfLIB_C_PAIEMENT(),Const.C_LIB_C_PAIEMENT);
		mapComposantChamps.put(vue.getTfREPORT_C_PAIEMENT(),Const.C_REPORT_C_PAIEMENT);
		mapComposantChamps.put(vue.getTfFIN_MOIS_C_PAIEMENT(), Const.C_FIN_MOIS_C_PAIEMENT);
		mapComposantChamps.put(vue.getCbCPaiementDocDefaut(), Const.C_DEFAUT_C_PAIEMENT);
		mapComposantChamps.put(vue.getCbTypeCPaiement(), Const.C_CODE_T_C_PAIEMENT);

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
				.getTfCODE_C_PAIEMENT());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_EDITION, vue
				.getTfCODE_C_PAIEMENT());
		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_CONSULTATION, vue
				.getGrille());

		activeModifytListener();

//		vue.getTfFIN_MOIS_C_PAIEMENT().addVerifyListener(queDesChiffres);
//		vue.getTfREPORT_C_PAIEMENT().addVerifyListener(queDesChiffres);
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

	public SWTPaConditionPaiementController getThis() {
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
					.getString("ConditionPaiement.Message.Enregistrer"))) {

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
				setListeEntity(getModelCPaiement().remplirListe());
				dao.initValeurIdTable(taCPaiement);
				fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
						//ibTaTable.champIdTable, ibTaTable.valeurIdTable,
						dao.getChampIdEntite(), dao.getValeurIdTable(),
						selectedCPaiement.getValue())));
//				vue.getDisplay().asyncExec(new Runnable() {
//				public void run() {
//				vue.getShell().setVisible(false);
//				}
//				});
//				retour = false;
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
					//ibTaTable.lgrLocateID(((ResultAffiche) evt.getRetour()).getIdResult());
					if(((ResultAffiche) evt.getRetour()).getSelection()!=null)
						tableViewer.setSelection(((ResultAffiche) evt.getRetour()).getSelection(),true);
				}
			}
		} else 	if (evt.getRetour() != null) {
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

	@Override
	protected void actInserer() throws Exception {
		try {
			if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_CONSULTATION)==0) {
				VerrouInterface.setVerrouille(true);
				setSwtOldCPaiement();
				swtCPaiement = new SWTCPaiement();
				taCPaiement = new TaCPaiement();			
				
				if(conditionTiers){
					vue.getCbTypeCPaiement().select(vue.getCbTypeCPaiement().indexOf(findComboItemDocumentCombo(TaTCPaiement.C_CODE_TYPE_TIERS)));
				}
				taCPaiement.setTaTCPaiement(taTCPaiementDAO.findByCode(findCodeTypeDocumentCombo(vue.getCbTypeCPaiement().getText())));
					
				dao.inserer(taCPaiement);
				modelCPaiement.getListeObjet().add(swtCPaiement);
				writableList = new WritableList(realm, modelCPaiement.getListeObjet(), classModel);
				tableViewer.setInput(writableList);
				tableViewer.refresh();
				tableViewer.setSelection(new StructuredSelection(swtCPaiement));
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
				taCPaiement = dao.findById(((SWTCPaiement) selectedCPaiement.getValue()).getIdCPaiement());
			}else{
				if(!setSwtOldCPaiementRefresh())throw new Exception();
			}
			
			dao.modifier(taCPaiement);			
			initEtatBouton();
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		}
	}
	
	public boolean containsEntity(TaCPaiement entite){
		if(modelCPaiement.getListeEntity()!=null){
			for (Object e : modelCPaiement.getListeEntity()) {
				if(((TaCPaiement)e).getIdCPaiement()==
					entite.getIdCPaiement())return true;
			}
		}
		return false;
	}

	public boolean setSwtOldCPaiementRefresh() {
		try {	
			if (selectedCPaiement.getValue()!=null){
				TaCPaiement taArticleOld =dao.findById(taCPaiement.getIdCPaiement());
				taArticleOld=dao.refresh(taArticleOld);
				if(containsEntity(taCPaiement)) 
					modelCPaiement.getListeEntity().remove(taCPaiement);
				if(!taCPaiement.getVersionObj().equals(taArticleOld.getVersionObj())){
					taCPaiement=taArticleOld;
					if(!containsEntity(taCPaiement)) 
						modelCPaiement.getListeEntity().add(taCPaiement);					
					actRefresh();
					dao.messageNonAutoriseModification();
				}
				taCPaiement=taArticleOld;
				if(!containsEntity(taCPaiement)) 
					modelCPaiement.getListeEntity().add(taCPaiement);
				changementDeSelection();
				this.swtOldCPaiement=SWTCPaiement.copy((SWTCPaiement)selectedCPaiement.getValue());
			}
			return true;
		} catch (Exception e) {
			return false;
		}		
	}

//	public void setSwtOldCPaiementRefresh() {
//		if (selectedCPaiement.getValue()!=null){
//			if(LgrMess.isDOSSIER_EN_RESEAU())dao.refresh(dao.findById(((SWTCPaiement) selectedCPaiement.getValue()).getIdCPaiement()));
//			taCPaiement=dao.findById(((SWTCPaiement) selectedCPaiement.getValue()).getIdCPaiement());
//			try {
//				if(LgrMess.isDOSSIER_EN_RESEAU())actRefresh();
//			} catch (Exception e) {
//				logger.error("",e);
//			}			
//			this.swtOldCPaiement=SWTCPaiement.copy((SWTCPaiement)selectedCPaiement.getValue());
//		}
//	}
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
					.getString("ConditionPaiement.Message.Supprimer"))) {

				dao.begin(transaction);
				TaCPaiement u = dao.findById(((SWTCPaiement) selectedCPaiement.getValue()).getIdCPaiement());
				u.getTaTCPaiement().setTaCPaiement(null);
				u.setTaTCPaiement(null);
				dao.supprimer(u);
				dao.commit(transaction);
				Object suivant=tableViewer.getElementAt(tableViewer.getTable().getSelectionIndex()+1);
				modelCPaiement.removeEntity(taCPaiement);
//				taCPaiement=null;
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
			// //InputVerifier inputVerifier =
			// getFocusCourant().getInputVerifier();
			// //getFocusCourant().setInputVerifier(null);
			//switch (ibTaTable.getFModeObjet().getMode()) {
			switch (dao.getModeObjet().getMode()) {
			case C_MO_INSERTION:
				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("ConditionPaiement.Message.Annuler")))) {
					remetTousLesChampsApresAnnulationSWT(dbc);
					if(((SWTCPaiement) selectedCPaiement.getValue()).getIdCPaiement()==null){
						modelCPaiement.getListeObjet().remove(((SWTCPaiement) selectedCPaiement.getValue()));
						writableList = new WritableList(realm, modelCPaiement.getListeObjet(), classModel);
						tableViewer.setInput(writableList);
						tableViewer.refresh();
						tableViewer.selectionGrille(0);
					}
					//ibTaTable.annuler();
					dao.annuler(taCPaiement);
					hideDecoratedFields();
				}
				initEtatBouton();
				break;
			case C_MO_EDITION:
				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("ConditionPaiement.Message.Annuler")))) {
					//int rang = getGrille().getSelectionIndex();
					int rang = modelCPaiement.getListeObjet().indexOf(selectedCPaiement.getValue());
					// selectedCPaiement.setValue(swtOldCPaiement);
					remetTousLesChampsApresAnnulationSWT(dbc);
					modelCPaiement.getListeObjet().set(rang, swtOldCPaiement);
					writableList = new WritableList(realm, modelCPaiement.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.setSelection(new StructuredSelection(swtOldCPaiement), true);
					//ibTaTable.annuler();
					dao.annuler(taCPaiement);
					hideDecoratedFields();
				}
				initEtatBouton();

				break;
			case C_MO_CONSULTATION:
				//actionFermer.run();
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
		
		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();

		String nomChampIdTable =  dao.getChampIdTable();

		FonctionGetInfosXmlAndProperties fonctionGetInfosXmlAndProperties = new FonctionGetInfosXmlAndProperties(mapperUIToModel);
		fonctionGetInfosXmlAndProperties.cleanValueMapAttributeTable();

		fonctionGetInfosXmlAndProperties.setValueMapAttributeTable(TaCPaiement.class.getSimpleName()+".head");
		fonctionGetInfosXmlAndProperties.setValueMapAttributeTable(TaCPaiement.class.getSimpleName()+".detail");
		
		LinkedHashMap<String,AttributElementResport> mapAttributeTablHead = fonctionGetInfosXmlAndProperties.getMapAttributeTablHead(); 
		LinkedHashMap<String,AttributElementResport> mapAttributeTablDetail = fonctionGetInfosXmlAndProperties.getMapAttributeTablDetail();
		
		Collection<TaCPaiement> collectionTaCPaiement = modelCPaiement.getListeEntity();
		
		fonctionGetInfosXmlAndProperties.findInfosFileXml(TaCPaiement.class.getName(),SWTCPaiement.class.getName(),
				listeChamp,"mapping");
		fonctionGetInfosXmlAndProperties.getInfosObjetJPA(taCPaiement);

		ConstEdition constEdition = new ConstEdition(); 
//		Impression impression = new Impression(constEdition,taCPaiement,collectionTaCPaiement,nomChampIdTable,taCPaiement.getIdCPaiement());
		String nomDossier = null;

		int nombreLine = collectionTaCPaiement.size();

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

			constEdition.addValueList(tableViewer, nomClassController);

			/**
			 * pathFileReport ==> le path de ficher de edition dynamique
			 */
			String folderEditionDynamique = Const.C_RCP_INSTANCE_LOCATION+"/"+Const.C_NOM_PROJET_TMP+"/"+TaCPaiement.class.getSimpleName();
			constEdition.makeFolderEditions(folderEditionDynamique);
			Path pathFileReport = new Path(folderEditionDynamique+"/"+Const.C_NOM_VU_COND_PAIE+".rptdesign");
			final String pathFileReportDynamic = pathFileReport.toPortableString();

			MakeDynamiqueReport DynamiqueReport = new MakeDynamiqueReport(constEdition.getNameTableEcran(),
					constEdition.getNameTableBDD(),pathFileReportDynamic,Const.C_NOM_VU_COND_PAIE,
					ConstEdition.PAGE_ORIENTATION_LANDSCAPE,nomDossier); 
			
			/**************************************************************/
			DynamiqueReport.setFonctionGetInfosXml(fonctionGetInfosXmlAndProperties);
			DynamiqueReport.setNomObjet(TaTTel.class.getSimpleName());
			/**************************************************************/

			Map<String, AttributElementResport> attribuGridHeader = new LinkedHashMap<String, AttributElementResport>();
			String nameHeaderTitle = ConstEditionTiers.TITLE_EDITION_COND_PAIE;
			attribuGridHeader.put(nameHeaderTitle, new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
					ConstEdition.FONT_SIZE_XX_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
					ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE,""));
			String nameSousHeaderTitle = ConstEditionTiers.SOUS_TITLE_EDITION_COND_PAIE;
			attribuGridHeader.put(nameSousHeaderTitle, new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
					ConstEdition.FONT_SIZE_X_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
					ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE,ConstEdition.COLOR_GRAY));

			//DynamiqueReport.buildDesignConfig();
			ConstEdition.CONTENT_COMMENTS = ConstEditionTiers.COMMENTAIRE_EDITION_DEFAUT;
			DynamiqueReport.setSimpleNameEntity(TaCPaiement.class.getSimpleName());
			DynamiqueReport.initializeBuildDesignReportConfig();
			DynamiqueReport.makePageMater("1", "1", "1", "1", "100");
			DynamiqueReport.makeReportHeaderGrid(3,5,100,ConstEdition.UNITS_PERCENTAGE,attribuGridHeader);
//			DynamiqueReport.makeReportTableDB(100,ConstEdition.UNITS_PERCENTAGE,
//					Const.C_NOM_VU_T_TEL,attribuTabHeader,attribuTabDetail,1,1,2,5,"30");
			DynamiqueReport.biuldTableReport("100", ConstEdition.UNITS_PERCENTAGE, 
					Const.C_NOM_VU_COND_PAIE,1,1,2,"40", mapAttributeTablHead, mapAttributeTablDetail);
			DynamiqueReport.savsAsDesignHandle();
			
//			impression.imprimer(true,pathFileReportDynamic,null,"Condition Paiement",TaCPaiement.class.getSimpleName());

			/** 01/03/2010 modifier les editions (zhaolin) **/
			BaseImpressionEdition impressionEdition = new BaseImpressionEdition(constEdition,taCPaiement,
					getEm(),collectionTaCPaiement,taCPaiement.getIdCPaiement());
			impressionEdition.impressionEditionTypeEntity(pathFileReportDynamic,"Condition Paiement");
			
		}
	}

	@Override
	protected boolean aideDisponible() {
		boolean result = false;
		//switch ((getThis().ibTaTable.getFModeObjet().getMode())) {
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
					if(getFocusCourantSWT().equals(vue.getGrille())){
						PaConditionPaiementSWT paCPaiementSWT = new PaConditionPaiementSWT(s2,SWT.NULL);
						SWTPaConditionPaiementController swtPaCPaiementController = new SWTPaConditionPaiementController(paCPaiementSWT);
						
						paramAfficheAideRecherche.setForceAffichageAideRemplie(true);
						paramAfficheAideRecherche.setAfficheDetail(false);

						editorCreationId = EditorConditionPaiement.ID;
						editorInputCreation = new EditorInputConditionPaiement();

						ParamAfficheConditionPaiement paramAfficheCPaiement = new ParamAfficheConditionPaiement();
						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
						paramAfficheCPaiement.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						paramAfficheCPaiement.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaCPaiementController;
						parametreEcranCreation = paramAfficheCPaiement;

						paramAfficheAideRecherche.setTypeEntite(TaCPaiement.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_C_PAIEMENT);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_C_PAIEMENT().getText());
						paramAfficheAideRecherche.setControllerAppelant(getThis());
						paramAfficheAideRecherche.setModel(swtPaCPaiementController.getModelCPaiement());
						paramAfficheAideRecherche.setTypeObjet(swtPaCPaiementController.getClassModel());
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_C_PAIEMENT);
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

	public IStatus validateUI() {
		return null;
	}
	
	public IStatus validateUIField(String nomChamp,Object value) {
		String validationContext = "C_PAIEMENT";
		try {
			IStatus s = null;
			boolean verrouilleModifCode = false;
			int change=0;
			if(nomChamp.equals(Const.C_CODE_T_C_PAIEMENT)) {
				TaTCPaiementDAO dao = new TaTCPaiementDAO(getEm());
				
				dao.setModeObjet(getDao().getModeObjet());
				TaTCPaiement f = new TaTCPaiement();
				PropertyUtils.setSimpleProperty(f, nomChamp, value);
				s = dao.validate(f,nomChamp,validationContext);

				if(s.getSeverity()!=IStatus.ERROR ){
					f = dao.findByCode((String)value);
					taCPaiement.setTaTCPaiement(f);
				}
				dao = null;
			} else {
				TaCPaiement u = new TaCPaiement();
				PropertyUtils.setSimpleProperty(u, nomChamp, value);
				if(((SWTCPaiement) selectedCPaiement.getValue()).getIdCPaiement()!=null) {
					u.setIdCPaiement(((SWTCPaiement) selectedCPaiement.getValue()).getIdCPaiement());
				}
				if(nomChamp.equals(Const.C_CODE_C_PAIEMENT)){
					verrouilleModifCode = true;
				}

				s = dao.validate(u,nomChamp,validationContext,verrouilleModifCode);
				if(s.getSeverity()!=IStatus.ERROR && change!=0) {
					  
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
			if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_EDITION) == 0)||
					(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)) {
				
				//if ((dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)) {
					TaTCPaiement taTCPaiement = taTCPaiementDAO.refresh(taTCPaiementDAO.findByCode(findCodeTypeDocumentCombo(vue.getCbTypeCPaiement().getText())));
					//taCPaiement.setTaTCPaiement(taTCPaiementDAO.findByCode(findCodeTypeDocumentCombo(vue.getCbTypeCPaiement().getText())));
					taCPaiement.setTaTCPaiement(taTCPaiement);
				//}
				LgrDozerMapper<SWTCPaiement,TaCPaiement> mapper = new LgrDozerMapper<SWTCPaiement,TaCPaiement>();
				mapper.map((SWTCPaiement) selectedCPaiement.getValue(),taCPaiement);
				
				if(taCPaiement.getTaTCPaiement().getTaCPaiement()==null) {
					taCPaiement.getTaTCPaiement().setTaCPaiement(taCPaiement);	
				}
				else if(((SWTCPaiement) selectedCPaiement.getValue())!=null 
						&& ((SWTCPaiement) selectedCPaiement.getValue()).getDefaut()!=null
						&& ((SWTCPaiement) selectedCPaiement.getValue()).getDefaut()) {
					taCPaiement.getTaTCPaiement().setTaCPaiement(taCPaiement);	
				}
				
				taCPaiement=dao.enregistrerMerge(taCPaiement);
				//if(dao.getModeObjet().getMode().compareTo(ModeObjet.EnumModeObjet.C_MO_INSERTION) == 0)
//					modelCPaiement.getListeEntity().add(taCPaiement);
			} 
			dao.commit(transaction);
			modelCPaiement.addEntity(taCPaiement);
			
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
			vue.getTfCODE_C_PAIEMENT().setEditable(!isUtilise());
			changeCouleur(vue);
	    } catch (Exception e) {
		vue.getLaMessage().setText(e.getMessage());
	    }
	}

	public SWTCPaiement getSwtOldCPaiement() {
		return swtOldCPaiement;
	}

	public void setSwtOldCPaiement(SWTCPaiement swtOldCPaiement) {
		this.swtOldCPaiement = swtOldCPaiement;
	}

	public void setSwtOldCPaiement() {
		if (selectedCPaiement!=null && selectedCPaiement.getValue() != null)
			this.swtOldCPaiement = SWTCPaiement.copy((SWTCPaiement) selectedCPaiement.getValue());
		else {
			if (tableViewer.selectionGrille(0)){
				this.swtOldCPaiement = SWTCPaiement.copy((SWTCPaiement) selectedCPaiement.getValue());
				tableViewer.setSelection(new StructuredSelection(
						(SWTCPaiement) selectedCPaiement.getValue()), true);
			}
		}
	}

	public void setVue(PaConditionPaiementSWT vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, DecoratedField>();
		mapComposantDecoratedField.put(vue.getTfCODE_C_PAIEMENT(), vue
				.getFieldCODE_C_PAIEMENT());
		mapComposantDecoratedField.put(vue.getTfFIN_MOIS_C_PAIEMENT(), vue
				.getFieldFIN_MOIS_C_PAIEMENT());
		mapComposantDecoratedField.put(vue.getTfLIB_C_PAIEMENT(), vue
				.getFieldLIB_C_PAIEMENT());
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

		TaCPaiement u = taCPaiement;
		if (u!=null && u.getIdCPaiement()==0)
			u=dao.findById(u.getIdCPaiement());
		writableList = new WritableList(realm, modelCPaiement.remplirListe(), classModel);
		tableViewer.setInput(writableList);

		tableViewer.selectionGrille(
				tableViewer.selectionGrille(selectedCPaiement.getValue()));
		
		if(u != null) {
			Iterator<SWTCPaiement> ite = modelCPaiement.getListeObjet().iterator();
			SWTCPaiement tmp = null;
			int i = 0;
			boolean trouve = false;
			while (ite.hasNext() && !trouve) {
				tmp = ite.next();
				if(tmp.getIdCPaiement()==u.getIdCPaiement()) {
					tableViewer.setSelection(new StructuredSelection(tmp));
					trouve = true;
				}
				i++;
			}
		}
	}

	//public ModelGeneral<SWTCPaiement> getModelCPaiement() {
	public ModelGeneralObjet<SWTCPaiement,TaCPaiementDAO,TaCPaiement> getModelCPaiement() {
		return modelCPaiement;
	}

	public boolean isUtilise(){
		return (((SWTCPaiement)selectedCPaiement.getValue()).getIdCPaiement()!=null &&
		!dao.recordModifiable(dao.getNomTable(),
				((SWTCPaiement)selectedCPaiement.getValue()).getIdCPaiement()))||
				!dao.autoriseModification(taCPaiement);		
	}

	public TaCPaiementDAO getDao() {
		return dao;
	}
	
	public void modifMode(){
		if (!VerrouInterface.isVerrouille() ){
			try {
				if(!daoStandard.dataSetEnModif()) {
					if(!modelCPaiement.getListeEntity().isEmpty()) {
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

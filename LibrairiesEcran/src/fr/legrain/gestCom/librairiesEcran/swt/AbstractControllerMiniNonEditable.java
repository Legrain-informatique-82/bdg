package fr.legrain.gestCom.librairiesEcran.swt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.expressions.Expression;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.swt.IFocusService;

import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.lib.gui.grille.LgrViewerSupport;


/**
 * Composite vue
 * => databinding
 * (=> ordre focus)
 * @author nicolas
 *
 */
public abstract class AbstractControllerMiniNonEditable {
	
	static Logger logger = Logger.getLogger(AbstractControllerMiniNonEditable.class.getName());
	
	protected Map<Control,String> mapComposantChamps = null;
	protected Map<String, org.eclipse.core.commands.IHandler> mapCommand = null;
	protected List<Control> listeComposantFocusable = null;
	protected Map<Button,Object> mapActions = null;
	
	protected Composite vue = null;
	private Control focusAvantAideSWT = null;

	private Class classModel = null;

	private String nomClass = this.getClass().getSimpleName();
	
	private Map<Table,LgrTableViewer> mapLgrTableViewer = null;
	protected Map<Control,LgrUpdateValueStrategy> mapComposantUpdateValueStrategy = null;
	//private MapChangeListener changeListener = new MapChangeListener();
	

	
	private EntityManager em = null;
	
	public AbstractControllerMiniNonEditable() {
		
	}

	public AbstractControllerMiniNonEditable(Composite vue, EntityManager em, Map<Control, String> mapComposantChamps) {
		if(em!=null) {
			setEm(em);
		}
		initController();
	}
	
	protected void initController()	{
		try {	
//			setDaoStandard(daoStandard);

			initMapComposantChamps();
//			initVerifySaisie();
//			initMapComposantDecoratedField();
//			listeComponentFocusableSWT(listeComposantFocusable);
//			initFocusOrder();
			initActions();
//			initDeplacementSaisie(listeComposantFocusable);

//			branchementBouton();

//			Menu popupMenuFormulaire = new Menu(vue.getShell(), SWT.POP_UP);
//			Menu popupMenuGrille = new Menu(vue.getShell(), SWT.POP_UP);
//			Menu[] tabPopups = new Menu[] {
//					popupMenuFormulaire, popupMenuGrille };
//			this.initPopupAndButtons(mapActions, tabPopups);
//			vue.getPaCorpsFormulaire().setMenu(popupMenuFormulaire);
//			vue.getPaGrille().setMenu(popupMenuGrille);

//			initEtatBouton();
		} catch (Exception e) {
			logger.error("Erreur : PaTiersController", e);
		}
	}

	
	public IObservableValue bindTable(Table table, List model, Class classeEntite, String[] titreColonne, String[] tailleColonne, String[] nomChampsEntite){
		LgrTableViewer tableViewer = new LgrTableViewer(table);
		
		if(mapLgrTableViewer==null) {
			mapLgrTableViewer = new HashMap<Table, LgrTableViewer>();
		}
		mapLgrTableViewer.put(table, tableViewer);
		
		tableViewer.createTableCol(table, titreColonne, tailleColonne);
		String[] listeChamp = nomChampsEntite;
		
		tableViewer.setListeChamp(listeChamp);

		// Set up data binding.
		LgrViewerSupport.bind(tableViewer, 
				new WritableList(model, classeEntite),
				BeanProperties.values(listeChamp)
		);

		IObservableValue selectedObject = ViewersObservables.observeSingleSelection(tableViewer);

//		dbc.getValidationStatusMap().addMapChangeListener(changeListener);
		tableViewer.selectionGrille(0);
		
		return selectedObject;
	}
	
//	public IObservableValue bindTree(Tree tree, List model, final Class classeEntite, String[] titreColonne, String[] tailleColonne, final String[] nomChampsEntite){
////		LgrTableViewer tableViewer = new LgrTableViewer(tree);
//		LgrTreeViewer treeViewer = new LgrTreeViewer(tree);
//		
////		if(mapLgrTableViewer==null) {
////			mapLgrTableViewer = new HashMap<Table, LgrTableViewer>();
////		}
////		mapLgrTableViewer.put(tree, tableViewer);
//		
//		treeViewer.createTableCol(tree, titreColonne, tailleColonne);
//		String[] listeChamp = nomChampsEntite;
//		
//		treeViewer.setListeChamp(listeChamp);
//
//
//		IListProperty childrenProp = new DelegatingListProperty() {
//			IListProperty inputChildren = BeanProperties.list(classeEntite, nomChampsEntite[0]);
//			IListProperty elementChildren = BeanProperties.list(classeEntite, nomChampsEntite[0]);
//			
//			protected IListProperty doGetDelegate(Object source) {
//				if (source instanceof DefaultFormPageController.DocumentIHM)
//					return inputChildren;
//				if (source instanceof DefaultFormPageController.DocumentIHM)
//					return elementChildren;
//				return null;
//			}
//		};
//		
//		// Set up data binding.
//		ViewerSupport.bind(treeViewer, 
//				new WritableList(model, classeEntite),childrenProp,
//				BeanProperties.values(listeChamp)
//		);
//
//		IObservableValue selectedObject = ViewersObservables.observeSingleSelection(treeViewer);
//
////		dbc.getValidationStatusMap().addMapChangeListener(changeListener);
//		treeViewer.selectionGrille(0);
//		
//		return selectedObject;
//	}
	
	public void bindForm(Map<Control,String> mapComposantChamps, Class classeEntite,Object selectedObject,Display display) {
		bindForm(null, mapComposantChamps, classeEntite, selectedObject, display);
	}
	
	public void bindForm(Map<Control,LgrUpdateValueStrategy> mapComposantUpdateValueStrategy,Map<Control,String> mapComposantChamps, Class classeEntite,Object selectedObject,Display display) {
		Realm realm = SWTObservables.getRealm(display);

		DataBindingContext dbc = new DataBindingContext(realm);

//		dbc.getValidationStatusMap().addMapChangeListener(changeListener);
		LgrDatabindingUtil lgrDatabindingUtil = new LgrDatabindingUtil();
		lgrDatabindingUtil.bindingFormSimple(null, mapComposantChamps, dbc, realm,selectedObject, classeEntite,mapComposantUpdateValueStrategy);
	}
	
	public void bindFormMaitreDetail(Map<Control,String> mapComposantChamps, Class classeEntite,IObservableValue selectedObject,Display display) {
		Realm realm = SWTObservables.getRealm(display);

		DataBindingContext dbc = new DataBindingContext(realm);

//		dbc.getValidationStatusMap().addMapChangeListener(changeListener);

		LgrDatabindingUtil lgrDatabindingUtil = new LgrDatabindingUtil();
		lgrDatabindingUtil.bindingFormMaitreDetail(mapComposantChamps, dbc, realm,selectedObject, classeEntite);
	}

//	public void bind(PaTiersSWT paTiersSWT){
	
	protected void initMapComposantChamps() {
//		if (mapComposantChamps == null) 
//			mapComposantChamps = new LinkedHashMap<Control,String>();
//
//		if (listeComposantFocusable == null) 
//			listeComposantFocusable = new ArrayList<Control>();
//		listeComposantFocusable.add(vue.getGrille());
//
//		vue.getTfCODE_TIERS().setToolTipText("C_CODE_TIERS");
//
//		mapComposantChamps.put(vue.getTfCODE_TIERS(),Const.C_CODE_TIERS);
//
//		for (Control c : mapComposantChamps.keySet()) {
//			listeComposantFocusable.add(c);
//		}
//
//		listeComposantFocusable.add(vue.getBtnEnregistrer());
//
//		if(mapInitFocus == null) 
//			mapInitFocus = new LinkedHashMap<ModeObjet.EnumModeObjet,Control>();
//		mapInitFocus.put(ModeObjet.EnumModeObjet.C_MO_INSERTION,vue.getTfCODE_TIERS());

	}

	protected void initActions() {		
//		mapCommand = new HashMap<String, IHandler>();
//
//		mapCommand.put(C_COMMAND_GLOBAL_MODIFIER_ID, handlerModifier);
//
//		initFocusCommand(String.valueOf(this.hashCode()),listeComposantFocusable,mapCommand);
//
//		if (mapActions == null)
//			mapActions = new LinkedHashMap<Button, Object>();
//
//		mapActions.put(vue.getBtnAnnuler(), C_COMMAND_GLOBAL_ANNULER_ID);
//
//
//		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID, C_COMMAND_GLOBAL_REFRESH_ID };
//		mapActions.put(null, tabActionsAutres);
	}
	
	/**
	 * Voir methode initFocusCommand() dans JPABaseControllerSWTStandard
	 * @param focusId
	 * @param controls
	 * @param commandIdsAndHandler
	 */
	public void initFocusCommand(final String focusId, List<Control> controls, Map<String,org.eclipse.core.commands.IHandler> commandIdsAndHandler/*, WorkbenchPart part*/) {
		IHandlerService handlerService = null;
		if(handlerService == null)
			handlerService = (IHandlerService)PlatformUI.getWorkbench().getService(IHandlerService.class);
		
		IFocusService focusService = (IFocusService) PlatformUI.getWorkbench().getService(IFocusService.class);
		for (Control control : controls) {
			focusService.addFocusTracker(control, focusId);
		}
		
		Expression exp = new FocusControlExpression(focusId);
		
		for (String commandId : commandIdsAndHandler.keySet()) {
			handlerService.activateHandler(commandId, commandIdsAndHandler.get(commandId), exp);
		}
	}
	
	public Control getFocusAvantAideSWT() {
		return focusAvantAideSWT;
	}

	public void setFocusAvantAideSWT(Control focusAvantAideSWT) {
		if (focusAvantAideSWT!=null){
			this.focusAvantAideSWT = focusAvantAideSWT;
			System.out.println("focusAvantAideSWT ToolTip= "+this.focusAvantAideSWT.getToolTipText());
		}
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	public Map<Table, LgrTableViewer> getMapLgrTableViewer() {
		return mapLgrTableViewer;
	}	
	
}

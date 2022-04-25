package fr.legrain.tiers.views;

import org.apache.log4j.Logger;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.part.ViewPart;

import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Module_Tiers.ModelTiers;
import fr.legrain.gestCom.Module_Tiers.SWTTiers;
import fr.legrain.gestCom.librairiesEcran.swt.ILgrListView;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.workbench.AbstractLgrMultiPageEditor;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.lib.gui.grille.LgrSimpleTableLabelProvider;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.ecran.SWTPaTiersController;
import fr.legrain.tiers.editor.TiersMultiPageEditor;

public class ListeTiersViewController /*extends JPABaseControllerSWTStandard*/ implements ILgrListView<TaTiers>, RetourEcranListener {
	
	static Logger logger = Logger.getLogger(ListeTiersViewController.class.getName());
	
	private PaListeTiersView vue = null;
	private ViewPart view = null;
	
	private Realm realm;
	private DataBindingContext dbc;
	private Class classModel = SWTTiers.class;
	private ModelTiers modelTiers = new ModelTiers();
	private String nomClass = SWTPaTiersController.class.getSimpleName();
	private String[] listeChamp;
	
	private Action actionRefresh;
	private Action actionVoir;
	private Action actionInserer;
	private Action actionSupprimer;
	private Action actionAide;
	private Action doubleClickAction;
	private Action actionSupportAbon;
	
	public ListeTiersViewController(PaListeTiersView vue, ViewPart view) {
		/*
		 * Pouvoir affecter un "controlleur maitre" pour faire un lien entre l'état des actions
		 * de la vue et de l'éditeur
		 */
		this.vue = vue;
		this.view = view;
		
		bind();
		initController();
		
		view.getSite().setSelectionProvider(vue.getLgrViewer().getViewer());
		makeActions();
//		hookContextMenu();
		hookDoubleClickAction();
//		contributeToActionBars();
	}
	
	private void initController()	{
		try {	
			vue.getTfCodeTiers().addKeyListener(new KeyListener() {
				
				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
						//System.err.println(vue.getTfCodeTiers().getText());
						
						SWTTiers tiersRetour = modelTiers.recherche(Const.C_CODE_TIERS, vue.getTfCodeTiers().getText().toUpperCase(), true);
						vue.getLgrViewer().getViewer().setSelection(new StructuredSelection(tiersRetour),true);
					} else if(e.keyCode == SWT.ARROW_DOWN || e.keyCode == SWT.ARROW_UP) {
						int i = vue.getLgrViewer().getViewer().getTable().getSelectionIndex();
						
						if(e.keyCode == SWT.ARROW_DOWN) {
							if(i-1<vue.getLgrViewer().getViewer().getTable().getItemCount())
								vue.getLgrViewer().getViewer().setSelection(new StructuredSelection(
										vue.getLgrViewer().getViewer().getElementAt(i+1)
										),true);
						} else {
							if(i-1>=0)
								vue.getLgrViewer().getViewer().setSelection(new StructuredSelection(
										vue.getLgrViewer().getViewer().getElementAt(i-1)
										),true);
						}
						
					}
				}
			});
			
		} catch (Exception e) {
			logger.error("Erreur : ListeTiersViewController", e);
		}
	}
	
	private void bind() {
		try {
			realm = SWTObservables.getRealm(vue.getLgrViewer().getViewer().getTable().getDisplay());

			vue.getLgrViewer().createTableCol(classModel,vue.getLgrViewer().getViewer().getTable(),nomClass,Const.C_FICHIER_LISTE_CHAMP_GRILLE,-1); //tri sur les code tiers par un order by
			//viewer.createTableCol(classModel,viewer.getViewer().getTable(),nomClass,Const.C_FICHIER_LISTE_CHAMP_GRILLE,0);
			listeChamp = vue.getLgrViewer().setListeChampGrille(nomClass,Const.C_FICHIER_LISTE_CHAMP_GRILLE);

			ModelTiers.MyContentProvider cp = modelTiers.initCP(vue.getLgrViewer().getViewer(),view,vue.getBar(),vue.getBarContainer(),vue.getLgrViewer());

//			ObservableListContentProvider contentProvider = new ObservableListContentProvider();
//			WritableList input = cp.elements;
			if (vue.getLgrViewer().getViewer().getInput() != null)
				vue.getLgrViewer().getViewer().setInput(null);
			vue.getLgrViewer().getViewer().setContentProvider(cp);

			vue.getLgrViewer().getViewer().setLabelProvider(new LgrSimpleTableLabelProvider(listeChamp));

			vue.getLgrViewer().getViewer().setInput(cp);
			
		} catch(Exception e) {
			logger.error("",e);
		}
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(actionRefresh);
		manager.add(new Separator());
		manager.add(actionVoir);
		manager.add(actionInserer);
		manager.add(new Separator());
		manager.add(actionSupprimer);
		manager.add(new Separator());
		manager.add(actionSupportAbon);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(actionRefresh);
		manager.add(actionVoir);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(actionRefresh);
		manager.add(new Separator());
		manager.add(actionAide);
		manager.add(new Separator());
		manager.add(actionVoir);
		manager.add(actionInserer);
		manager.add(new Separator());
		manager.add(actionSupprimer);
		manager.add(new Separator());
		manager.add(actionSupportAbon);
	}
	
	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				ListeTiersViewController.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(vue.getLgrViewer().getViewer().getControl());
		vue.getLgrViewer().getViewer().getControl().setMenu(menu);
		view.getSite().registerContextMenu(menuMgr, vue.getLgrViewer().getViewer());
	}
	
	private void contributeToActionBars() {
		IActionBars bars = ((IViewSite)view.getSite()).getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}
	
	
//	public class CommandState extends AbstractSourceProvider {
//		public final static String MY_STATE = "de.vogella.rcp.commands.sourceprovider.active";
//		public final static String ENABLED = "ENABLED";
//		public final static String DISENABLED = "DISENABLED";
//		private boolean enabled = true;
//
//
//		@Override
//		public void dispose() {
//		}
//
//		// We could return several values but for this example one value is sufficient
//		@Override
//		public String[] getProvidedSourceNames() {
//			return new String[] { MY_STATE };
//		}
//		
//		// You cannot return NULL
//		@SuppressWarnings("unchecked")
//		@Override
//		public Map getCurrentState() {
//			Map map = new HashMap(1);
//			String value = enabled ? ENABLED : DISENABLED;
//			map.put(MY_STATE, value);
//			return map;
//		}
//
//		
//
//		// This method can be used from other commands to change the state
//		// Most likely you would use a setter to define directly the state and not use this toogle method
//		// But hey, this works well for my example
//		public void toogleEnabled() {
//			enabled = !enabled ;
//			String value = enabled ? ENABLED : DISENABLED;
//			fireSourceChanged(ISources.WORKBENCH, MY_STATE, value);
//		}
//
//	}
	
//	public class HandlerTest2 extends LgrAbstractHandler {
//
//		public Object execute(ExecutionEvent event) throws ExecutionException{
//			try {
//				System.out.println("ListeTiersViewController.HandlerTest2.execute()");
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			return event;
//		}
//	} 
	
	
	
//	public void testHandler() {
//		IHandlerService handlerService = null;
//		if(handlerService == null)
//			handlerService = (IHandlerService)PlatformUI.getWorkbench().getService(IHandlerService.class);
//		
//
//		//Expression exp = new FocusControlExpression(focusId);
//		
//		String PROPERTY_NAMESPACE = "org.demo";
//		String property = "matchesPattern";
//		Object[] args = null;
//		Object expectedValue = null;
//		Expression exp = new TestExpression(PROPERTY_NAMESPACE, property, args, expectedValue); 
//		
//		//for (String commandId : commandIdsAndHandler.keySet()) {
//	//		handlerService.activateHandler(JPABaseControllerSWTStandard.C_COMMAND_GLOBAL_INSERER_ID, new HandlerTest(), exp);
//		//}
//			
////			IEvaluationService evaluationService = (IEvaluationService) PlatformUI.getWorkbench().getService(IEvaluationService.class);
////			if (evaluationService != null) {
////				evaluationService.requestEvaluation(PROPERTY_NAMESPACE + "." + property);
////			}
//	}

	private void makeActions() {
		
//		testHandler();
		
//		actionRefresh = new Action() {
//			public void run() {
//				//showMessage("Action 1 executed");
//				vue.getLgrViewer().getViewer().refresh();
//			}
//		};
//		actionRefresh.setText("Rafraichir");
//		actionRefresh.setToolTipText("Rafraichir");
//		actionRefresh.setImageDescriptor(LibrairiesEcranPlugin.getImageDescriptor("/icons/arrow_refresh.png"));
		
//		actionAide = new Action() {
//			public void run() {
//				try {
//					actAide();
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		};
//		actionAide.setText("Aide");
//		actionAide.setToolTipText("Aide");
//		actionAide.setImageDescriptor(LibrairiesEcranPlugin.getImageDescriptor("/icons/find.png"));

//		actionVoir = new Action() {
//			public void run() {
//				//showMessage("Action 2 executed");
//				doubleClickAction.run();
//			}
//		};
//		
//		actionVoir.setText("Voir");
//		actionVoir.setToolTipText("Voir");
//		actionVoir.setImageDescriptor(LibrairiesEcranPlugin.getImageDescriptor("/icons/magnifier.png"));
		
		doubleClickAction = new Action() {
//			public void run() {
//				ISelection selection = viewer.getViewer().getSelection();
//				Object obj = ((IStructuredSelection)selection).getFirstElement();
//				//showMessage("Double-click detected on tiers : "+((SWTTiers)obj).getIdTiers());
//				
//				AbstractLgrMultiPageEditor.ouvreFiche(String.valueOf(((SWTTiers)obj).getIdTiers()), TiersMultiPageEditor.ID_EDITOR,null,false);
//			}
			
			public void run() {
				if(AbstractLgrMultiPageEditor.isEditeurOuvert(TiersMultiPageEditor.ID_EDITOR)) {
					ParamAffiche param = new ParamAffiche();
					param.setSelection(vue.getLgrViewer().getViewer().getSelection());
					AbstractLgrMultiPageEditor.ouvreFiche(null,null, TiersMultiPageEditor.ID_EDITOR,param,false);
				}
			}

					
		};
		
//		actionInserer = new Action() {
//			public void run() {
//				try {
//					ParamAffiche param = new ParamAffiche();
//					param.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//					
//					IEditorPart editor = AbstractLgrMultiPageEditor.chercheEditeurDocumentOuvert(TiersMultiPageEditor.ID_EDITOR);
//					if(editor==null){
////						IEditorPart e = null;
////
////						e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
////								openEditor(new EditorInputTiers(), TiersMultiPageEditor.ID_EDITOR);
//
////						LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
//						
////						((AbstractLgrMultiPageEditor)e).findMasterController().configPanel(param);
//						AbstractLgrMultiPageEditor.ouvreFiche(null, TiersMultiPageEditor.ID_EDITOR,param,false);
//					} else {
//						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().activate(editor);
//						((AbstractLgrMultiPageEditor)editor).findMasterController().configPanel(param);
//					}
//				} catch (/*PartInit*/Exception e1) {
//					logger.error("",e1);
//				}
//			}
//		};
//		actionInserer.setText("Nouveau");
//		actionInserer.setToolTipText("Nouveau");
//		actionInserer.setImageDescriptor(LibrairiesEcranPlugin.getImageDescriptor("/icons/add.png"));
		
//		actionSupprimer = new Action() {
//			public void run() {
//				try {
//					ISelection selection = vue.getLgrViewer().getViewer().getSelection();
//					Object obj = ((IStructuredSelection)selection).getFirstElement();
//
//					ParamAffiche param = new ParamAffiche();
//					param.setSelection(vue.getLgrViewer().getViewer().getSelection());
//					param.setModeEcran(EnumModeObjet.C_MO_SUPPRESSION);
//					AbstractLgrMultiPageEditor.ouvreFiche(String.valueOf(((SWTTiers)obj).getIdTiers()), TiersMultiPageEditor.ID_EDITOR,param,false);
//
//				} catch (Exception e1) {
//					logger.error("",e1);
//				}
//			}
//		};
//		actionSupprimer.setText("Supprimer");
//		actionSupprimer.setToolTipText("Supprimer");
//		actionSupprimer.setImageDescriptor(LibrairiesEcranPlugin.getImageDescriptor("/icons/delete.png"));
	}

	private void hookDoubleClickAction() {
		vue.getLgrViewer().getViewer().addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}
	private void showMessage(String message) {
		MessageDialog.openInformation(
				vue.getLgrViewer().getViewer().getControl().getShell(),
				"Liste des tiers",
				message);
	}
	
	public void update(TaTiers taTiers) {

		modelTiers.addToModel(vue.getLgrViewer().getViewer(), taTiers);
	}
	
	public void refresh(TaTiers taTiers) {

		modelTiers.refreshModel(vue.getLgrViewer().getViewer(), taTiers);
	}
	
	public void remove(TaTiers taTiers) {

		modelTiers.removeFromModel(vue.getLgrViewer().getViewer(), taTiers);
	}

	@Override
	public void select(TaTiers t) {
		if(t!=null) {
			SWTTiers tiers = modelTiers.recherche(Const.C_ID_TIERS, t.getIdTiers());
			vue.getLgrViewer().getViewer().setSelection(new StructuredSelection(tiers));
		}
	}

	@Override
	public void select(int index) {
		vue.getLgrViewer().selectionGrille(index);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	@Override
//	protected boolean aideDisponible() {
//		boolean result = false;
//		result = true;
//		return result;
//	}
//
////	@Override
//	protected void actAide() throws Exception{
//		actAide(null);
//	}
//
////	@Override
//	protected void actAide(String message) throws Exception{
//		if(aideDisponible()){
//			try{
//		//		setActiveAide(true);
//				VerrouInterface.setVerrouille(true);
//				vue.getLgrViewer().getViewer().getTable().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
//				ParamAfficheAideRechercheSWT paramAfficheAideRecherche = new ParamAfficheAideRechercheSWT();
//				paramAfficheAideRecherche.setMessage(message);
//				//Creation de l'ecran d'aide principal
//				Shell s = new Shell(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),LgrShellUtil.styleLgr);
//				PaAideSWT paAide = new PaAideSWT(s,SWT.NONE);
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
//
//				PaTiersSWT paTiersSWT = new PaTiersSWT(s2,SWT.NULL);
//				SWTPaTiersController swtPaTiersController = new SWTPaTiersController(paTiersSWT);
//
//				editorCreationId = TiersMultiPageEditor.ID_EDITOR;
//				editorInputCreation = new EditorInputTiers();
//
//				ParamAfficheTiers paramAfficheTiers = new ParamAfficheTiers();
//				paramAfficheAideRecherche.setJPQLQuery(modelTiers.getJPQLQuery());
//				paramAfficheTiers.setModeEcran(EnumModeObjet.C_MO_INSERTION);
//				paramAfficheTiers.setEcranAppelant(paAideController);
//				controllerEcranCreation = swtPaTiersController;
//				parametreEcranCreation = paramAfficheTiers;
//
//				paramAfficheAideRecherche.setTypeEntite(TaTiers.class);
//				paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_TIERS);
//				//paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_TIERS().getText());
//				paramAfficheAideRecherche.setDebutRecherche("");
//				//	paramAfficheAideRecherche.setControllerAppelant(this);
//				paramAfficheAideRecherche.setModel(new ModelGeneralObjet<SWTTiers, TaTiersDAO, TaTiers>(SWTTiers.class,new TaTiersDAO().getEntityManager()));
//				//paramAfficheAideRecherche.setModel(modelTiers);
//				paramAfficheAideRecherche.setTypeObjet(swtPaTiersController.getClassModel());
//				paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_TIERS);
//
//
//				if (paramAfficheAideRecherche.getJPQLQuery()!=null){
//
//					PaAideRechercheSWT paAideRecherche1 = new PaAideRechercheSWT(s,SWT.NULL);	
//					SWTPaAideRechercheControllerSWT paAideRechercheController1 = new SWTPaAideRechercheControllerSWT(paAideRecherche1);
//
//					//Parametrage de la recherche
//					paramAfficheAideRecherche.setFocusSWT(((PaAideRechercheSWT)paAideRechercheController1.getVue()).getTfChoix());
//					paramAfficheAideRecherche.setRefCreationSWT(controllerEcranCreation);
//					paramAfficheAideRecherche.setEditorCreation(editorCreation);
//					paramAfficheAideRecherche.setEditorCreationId(editorCreationId);
//					paramAfficheAideRecherche.setEditorInputCreation(editorInputCreation);
//					paramAfficheAideRecherche.setParamEcranCreation(parametreEcranCreation);
//					paramAfficheAideRecherche.setShellCreation(s2);
//					paAideRechercheController1.configPanel(paramAfficheAideRecherche);
//					//paramAfficheAideRecherche.setFocusDefaut(paAideRechercheController1.getVue().getTfChoix());
//
//					//Ajout d'une recherche
//					paAideController.addRecherche(paAideRechercheController1, paramAfficheAideRecherche.getTitreRecherche());
//
//					//Parametrage de l'ecran d'aide principal
//					ParamAfficheAideSWT paramAfficheAideSWT = new ParamAfficheAideSWT();
//					ParamAfficheAide paramAfficheAide = new ParamAfficheAide();
//
//					//enregistrement pour le retour de l'ecran d'aide
//					paAideController.addRetourEcranListener(this);
//					Control focus = vue.getLgrViewer().getViewer().getTable().getShell().getDisplay().getFocusControl();
//					//affichage de l'ecran d'aide principal (+ ses recherches)
//
//				//	dbc.getValidationStatusMap().removeMapChangeListener(changeListener);
//					/*****************************************************************/
//					paAideController.configPanel(paramAfficheAide);
//					/*****************************************************************/	
//				//	dbc.getValidationStatusMap().addMapChangeListener(changeListener);
//
//				}
//
//			}finally{
//				VerrouInterface.setVerrouille(false);
//				vue.getLgrViewer().getViewer().getTable().setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
//			}
//		}
//	}

	@Override
	public void retourEcran(RetourEcranEvent evt) {
		if (evt.getRetour() != null
				&& (evt.getSource() instanceof SWTPaAideControllerSWT)) {
			if(((ResultAffiche) evt.getRetour()).getSelection()!=null){
				SWTTiers tiersRetour =modelTiers.recherche(Const.C_ID_TIERS, LibConversion.stringToInteger(((ResultAffiche) evt.getRetour()).getIdResult())); 
				if(tiersRetour!=null){
					vue.getLgrViewer().getViewer().setSelection(new StructuredSelection(tiersRetour),true);
				}
			}
		}
	}

	public ViewPart getView() {
		return view;
	}

	public ModelTiers getModelTiers() {
		return modelTiers;
	}

	public PaListeTiersView getVue() {
		return vue;
	}
	
	/*
	 * **********************************************************************************************************************
	 * **********************************************************************************************************************
	 */

//	@Override
//	protected void actInserer() throws Exception {
//		System.out.println("ListeTiersViewController.actInserer()");		
//	}
//
//	@Override
//	protected void actEnregistrer() throws Exception {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	protected void actModifier() throws Exception {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	protected void actSupprimer() throws Exception {
//		System.out.println("ListeTiersViewController.actSupprimer()");		
//	}
//
//	@Override
//	protected void actFermer() throws Exception {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	protected void actAnnuler() throws Exception {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	protected void actImprimer() throws Exception {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	protected void actRefresh() throws Exception {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	protected void initActions() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	protected void initComposantsVue() throws ExceptLgr {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	protected void initMapComposantChamps() {
//		if (mapComposantChamps == null) 
//			mapComposantChamps = new LinkedHashMap<Control,String>();
//
//		if (listeComposantFocusable == null) 
//			listeComposantFocusable = new ArrayList<Control>();
//		
//		listeComposantFocusable.add(vue.getLgrViewer().getViewer().getTable());
//		
//	}
//
//	@Override
//	protected void initMapComposantDecoratedField() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void initEtatComposant() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public boolean onClose() throws ExceptLgr {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public ResultAffiche configPanel(ParamAffiche param) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	protected void sortieChamps() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public Composite getVue() {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
}

package fr.legrain.gestCom.librairiesEcran.swt;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerService;

import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;
import fr.legrain.gestCom.librairiesEcran.workbench.AbstractLgrMultiPageEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.ModelObject;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.lib.gui.aide.PaAideRechercheSWT;
import fr.legrain.lib.gui.aide.PaAideSWT;
import fr.legrain.lib.gui.aide.ParamAfficheAide;
import fr.legrain.lib.gui.aide.ResultAide;

public class SWTPaAideControllerSWT extends JPABaseControllerSWTStandard implements RetourEcranListener {

	static Logger logger = Logger.getLogger(SWTPaAideControllerSWT.class.getName());

	private PaAideSWT vue = null; // vue
	
	public static final String C_COMMAND_AIDE_NOUVEAU_ID = "fr.legrain.gestionCommerciale.aide.nouveau";
	public static final String C_COMMAND_AIDE_ANNULER_ID = "fr.legrain.gestionCommerciale.aide.annuler";
	public static final String C_COMMAND_AIDE_OK_ID = "fr.legrain.gestionCommerciale.aide.ok";
	public static final String C_COMMAND_CHANGE_CHAMPS_RECHERCHE_ID = "fr.legrain.gestionCommerciale.aide.champsRecherche";
	public static final String C_COMMAND_AIDE_VALIDER_ID = "fr.legrain.gestionCommerciale.aide.valider";
	public static final String C_COMMAND_DOCUMENT_AFFICHER_SEUL_ID = "fr.legrain.Document.afficherSeul";

	protected IHandlerService handlerService = (IHandlerService)PlatformUI.getWorkbench().getService(IHandlerService.class);
	protected HandlerNouveau handlerNouveau = new HandlerNouveau();
	protected HandlerAnnulerAide handlerAnnulerAide = new HandlerAnnulerAide();
	protected HandlerOK handlerOK = new HandlerOK();
	protected HandlerValider handlerValider = new HandlerValider();
	protected HandlerAfficherFiche handlerAfficherFiche = new HandlerAfficherFiche();

	private Composite appelant = null;
	private Menu[] tabPopups =null;
	// correspondance composant graphique/champs bdd
	private HashMap<Control,String> mapComposantChamps = null;
	//private HashMap<Button,Object> mapActions = null;

	private ActionOK actionOK = new ActionOK("Valider [ENTRER]"); 
	private ActionNouveau actionNouveau = new ActionNouveau("Nouveau [F6]");
	private ActionAnnuler actionAnnuler = new ActionAnnuler("Annuler [ESC]"); 

	//protected List<Control> listeComposantFocusable = null;

	private boolean retourOK = false; //vrai ssi l'ecran a été fermé par l'action prévue à cet effet

	private HashMap<Composite,SWTPaAideRechercheControllerSWT> controllerVue = new HashMap<Composite,SWTPaAideRechercheControllerSWT>();

	public SWTPaAideControllerSWT(PaAideSWT vue) {
		try {
			super.setVue(vue);
			this.vue = vue;
			initComposantsVue();
			initMapComposantChamps();
			listeComponentFocusableSWT(listeComposantFocusable);
			initDeplacementSaisie(listeComposantFocusable);
			initActions();
	//		idContext="LibrairiesEcran.contextAide";
			branchementBouton();
//			for (final Object button : mapActions.keySet()) {
//				if(button!=null)
//					((Button)button).addSelectionListener(new SelectionAdapter() {
//						@Override
//						public void widgetSelected(SelectionEvent e) {
//							((Action)mapActions.get(button)).run();
//						}
//					});
//			}
////			//Branchement action annuler : empeche la fermeture automatique de la fenetre sur ESC
//			vue.getShell().addTraverseListener(new TraverseListener() {
//				public void keyTraversed(TraverseEvent e) {
//					if (e.detail == SWT.TRAVERSE_ESCAPE ) {
//						try {
//							//e.doit = false;
//							onClose();
//						} catch (Exception e1) {
//							logger.error("", e1);
//						}
//					}
//				}
//
//			});

			Menu popupMenuFormulaire = new Menu(vue.getShell(), SWT.POP_UP);
			Menu popupMenuGrille = new Menu(vue.getShell(), SWT.POP_UP);
			Menu[] tabPopups = new Menu[] { popupMenuFormulaire, popupMenuGrille };
			this.initPopupAndButtons(mapActions, tabPopups);

		} catch (ExceptLgr e) {
			logger.error("Erreur : PaAideControllerSWT", e);
		}
	}
	public void initMapComposantChamps(){
		listeComposantFocusable = new ArrayList<Control>();

		listeComposantFocusable.add(vue.getBtnOK());
		listeComposantFocusable.add(vue.getBtnNouveau());		
		listeComposantFocusable.add(vue.getBtnAnnuler());
		listeComposantFocusable.add(vue.getBtnAfficher());
		
		vue.getBtnOK().setToolTipText("BtnOK");
		vue.getBtnNouveau().setToolTipText("BtnNouveau");;		
		vue.getBtnAnnuler().setToolTipText("BtnAnnuler");;
		vue.getBtnAfficher().setToolTipText("BtnAfficher la fiche");;
	}
	
//	public SWTPaAideControllerSWT() {
//	try {
//	this.vue = new PaAideSWT();
//	initComposantsVue();
//	initActions();

//	initMapComposantChamps();
//	listeComponentFocusableSWT(listeComposantFocusable);
//	initDeplacementSaisie(listeComposantFocusable);
//	//vue.setFocusCycleRoot(true);
//	//vue.setFocusTraversalPolicy(new OrdreFocus(listeComposantFocusable));
////	if(this.getFocusCourant()!=null){
////	logger.info("focus courant : "+this.getFocusCourant());
////	this.getFocusCourant().requestFocus();
////	}
//	} catch (ExceptLgr e) {
//	logger.error("Erreur : PaAideControllerSWT", e);
//	}
//	}

	protected void initImageBouton() {
		if(vue instanceof PaAideSWT) {
			((PaAideSWT)vue).getBtnAnnuler().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ANNULER));
			((PaAideSWT)vue).getBtnNouveau().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_INSERER));
			((PaAideSWT)vue).getBtnOK().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_ACCEPTER));
			((PaAideSWT)vue).getBtnAfficher().setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_RECHERCHER));
			vue.layout(true);
		}
	}
	
	public Composite getVue() {
		return vue;
	}

	/**
	 * Méthode appelée à la fermeture du Shell
	 */
	public boolean onClose() throws ExceptLgr {
		//if(!retourOK) {
			fireRetourEcran(new RetourEcranEvent(getThis(),null));
		//}
//#JPA
//		((SWTPaAideRechercheControllerSWT)
//				controllerVue.get(vue.getTabFolder().getItem(vue.getTabFolder().getSelectionIndex()).getControl()))
//				.getQuery().close();

//		annulerListeContext();
		
		for (SWTPaAideRechercheControllerSWT c : controllerVue.values()) {
			c.shellCreation.close();
		}

		try {

			this.finalize();
		} catch (Throwable e) {
			logger.error("",e);
		}
		setActiveAide(false);
		return true;
	}

	/**
	 * Ajout d'un panneau de recherche sous forme d'onglet
	 * @param rechercheSWT
	 * @param titre - titre de l'onglet
	 */
	public void addRecherche(SWTPaAideRechercheControllerSWT rechercheSWT, String titre) {

		TabItem i = new TabItem(this.vue.getTabFolder(),SWT.NONE);
		rechercheSWT.getVue().setParent(vue.getTabFolder());
		i.setControl(rechercheSWT.getVue());

		controllerVue.put(rechercheSWT.getVue(), rechercheSWT);
		tabPopups=rechercheSWT.tabPopupsAide;
		initPopupAndButtons(mapActions,tabPopups);
		
		//ActionOK sur double click
		((PaAideRechercheSWT)rechercheSWT.getVue()).getGrille().addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				if(e.button==1)
					actionOK.run();
			}
		});
		((PaAideRechercheSWT)rechercheSWT.getVue()).getGrille().addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(org.eclipse.swt.events.KeyEvent e) {
			}
			@Override
			public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
				if(e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
					actionOK.run();
				}
			}
		});
	
		

//		((PaAideRechercheSWT)rechercheSWT.getVue()).getGrille().addKeyListener(keyAdapter);
//		((PaAideRechercheSWT)rechercheSWT.getVue()).getTfChoix().addKeyListener(keyAdapter);
		
//		addFocusCommand(String.valueOf(this.hashCode()), ((PaAideRechercheSWT)rechercheSWT.getVue()).getGrille(), C_COMMAND_AIDE_OK_ID, handlerOK);
//		addFocusCommand(String.valueOf(this.hashCode()), ((PaAideRechercheSWT)rechercheSWT.getVue()).getTfChoix(), C_COMMAND_AIDE_OK_ID, handlerOK);
		
		addFocusCommand(String.valueOf(this.hashCode()), ((PaAideRechercheSWT)rechercheSWT.getVue()).getGrille(), C_COMMAND_AIDE_NOUVEAU_ID, handlerNouveau);
		addFocusCommand(String.valueOf(this.hashCode()), ((PaAideRechercheSWT)rechercheSWT.getVue()).getTfChoix(), C_COMMAND_AIDE_NOUVEAU_ID, handlerNouveau);
		
		addFocusCommand(String.valueOf(this.hashCode()), ((PaAideRechercheSWT)rechercheSWT.getVue()).getGrille(), C_COMMAND_AIDE_ANNULER_ID, handlerAnnulerAide);
		addFocusCommand(String.valueOf(this.hashCode()), ((PaAideRechercheSWT)rechercheSWT.getVue()).getTfChoix(), C_COMMAND_AIDE_ANNULER_ID, handlerAnnulerAide);
		
		addFocusCommand(String.valueOf(this.hashCode()), ((PaAideRechercheSWT)rechercheSWT.getVue()).getGrille(), C_COMMAND_AIDE_VALIDER_ID, handlerValider);
		addFocusCommand(String.valueOf(this.hashCode()), ((PaAideRechercheSWT)rechercheSWT.getVue()).getTfChoix(), C_COMMAND_AIDE_VALIDER_ID, handlerValider);
		
		addFocusCommand(String.valueOf(this.hashCode()), ((PaAideRechercheSWT)rechercheSWT.getVue()).getGrille(), C_COMMAND_CHANGE_CHAMPS_RECHERCHE_ID, rechercheSWT.getHandlerChangeChampsRech());
		addFocusCommand(String.valueOf(this.hashCode()), ((PaAideRechercheSWT)rechercheSWT.getVue()).getTfChoix(), C_COMMAND_CHANGE_CHAMPS_RECHERCHE_ID, rechercheSWT.getHandlerChangeChampsRech());
		
	}

	public ResultAffiche configPanel(ParamAffiche param){
		if(param!=null) {
			this.appelant = ((ParamAfficheAide)param).getAppelantSWT();
			if(param.getFocusSWT()!=null)setFocusCourantSWTHorsApplication(param.getFocusSWT());
		}
		if (getFocusCourantSWT()!=null)
			getFocusCourantSWT().setFocus();
		else {
			setFocusCourantSWT(((PaAideRechercheSWT)controllerVue.entrySet().iterator().next().getKey()).getTfChoix());
			getFocusCourantSWT().setFocus();
		}
		SWTPaAideRechercheControllerSWT paAideTmp =((SWTPaAideRechercheControllerSWT)
				controllerVue.get(vue.getTabFolder().getItem(vue.getTabFolder().getSelectionIndex()).getControl()));
		vue.getBtnAfficher().setEnabled(paAideTmp.afficheDetail);
		vue.getBtnNouveau().setEnabled(paAideTmp.afficheNouveau);
		return null;
	}

	/**
	 * Initialisation des composants graphiques de la vue.
	 * @throws ExceptLgr 
	 */
	protected void initComposantsVue() throws ExceptLgr {}

	protected void initActions() {
//		if (mapActions == null)
//			mapActions = new LinkedHashMap<Button,Object>();
//		mapActions.put(vue.getBtnAnnuler(), actionAnnuler);
//		mapActions.put(vue.getBtnOK(), actionOK);
//		mapActions.put(vue.getBtnNouveau(), actionNouveau);
//		
//		IContextService contextService = (IContextService)PlatformUI.getWorkbench()
//		.getService(IContextService.class);
//		contextService.registerShell(vue.getShell(), IContextService.TYPE_WINDOW);
//
//		IHandlerService handlerService = (IHandlerService)PlatformUI.getWorkbench()
//		.getService(IHandlerService.class);
//
//		ActiveShellExpression activeShellExpression = new ActiveShellExpression(vue.getShell());
//		handlerService.activateHandler(C_COMMAND_AIDE_ANNULER_ID, handlerAnnuler, activeShellExpression);
		
		mapCommand = new HashMap<String, IHandler>();
		
		mapCommand.put(C_COMMAND_AIDE_ANNULER_ID, handlerAnnulerAide);
		mapCommand.put(C_COMMAND_AIDE_NOUVEAU_ID, handlerNouveau);
		mapCommand.put(C_COMMAND_AIDE_VALIDER_ID, handlerValider);
		mapCommand.put(C_COMMAND_DOCUMENT_AFFICHER_SEUL_ID, handlerAfficherFiche);

		
		initFocusCommand(String.valueOf(this.hashCode())+".sauf_ok",listeComposantFocusable,mapCommand);
		//addFocusCommand(String.valueOf(this.hashCode()), vue.getBtnOK(), C_COMMAND_AIDE_OK_ID, handlerOK);
		
		handlerService.activateHandler(C_COMMAND_AIDE_VALIDER_ID, handlerValider, activeShellExpression);
		
		if (mapActions == null)
			mapActions = new LinkedHashMap<Button, Object>();
		
		mapActions.put(vue.getBtnAnnuler(), C_COMMAND_AIDE_ANNULER_ID);
		mapActions.put(vue.getBtnNouveau(), C_COMMAND_AIDE_NOUVEAU_ID);
		mapActions.put(vue.getBtnAfficher(), C_COMMAND_DOCUMENT_AFFICHER_SEUL_ID);
		mapActions.put(vue.getBtnOK(), C_COMMAND_AIDE_VALIDER_ID);
		
		Object[] tabActionsAutres = new Object[] { C_COMMAND_CHANGE_CHAMPS_RECHERCHE_ID };
		mapActions.put(null, tabActionsAutres);

	}

	public void addRetourEcranListener(RetourEcranListener l) {
		listenerList.add(RetourEcranListener.class, l);
	}

	protected void fireRetourEcran(RetourEcranEvent e) {
		super.fireRetourEcran(e);
		retourOK = true;
	}

	private SWTPaAideControllerSWT getThis() {
		return this;
	}

	private class ActionOK extends Action {
		public ActionOK(String name) {
			super(name);
		}

		public void run() {
			super.run();
			try {
				if (logger.isDebugEnabled())
					logger.debug("Debug : ActionOK - actionPerformed");

				Object resultat = null;
				Object resultatId = null;
				
				//Si champsRecherche="XXXX", appel de la methode getXXXX() sur la selection actuelle du viewer

				resultat = PropertyUtils.getProperty(
						controllerVue.get(vue.getTabFolder().getItem(vue.getTabFolder().getSelectionIndex()).getControl()).getSelectedObject().getValue(),
						controllerVue.get(vue.getTabFolder().getItem(vue.getTabFolder().getSelectionIndex()).getControl()).getChampsRechercheInitial()
						);
//				resultat = ((SWTPaAideRechercheControllerSWT)
//						controllerVue.get(vue.getTabFolder().getItem(vue.getTabFolder().getSelectionIndex()).getControl()))
//						.getTypeObjet().getMethod("get"+((SWTPaAideRechercheControllerSWT)
//								controllerVue.get(vue.getTabFolder().getItem(vue.getTabFolder().getSelectionIndex()).getControl())).getChampsRecherche(),
//								new Class[0]).invoke(((SWTPaAideRechercheControllerSWT)
//										controllerVue.get(vue.getTabFolder().getItem(vue.getTabFolder().getSelectionIndex()).getControl()))
//										.getSelectedObject().getValue(), new Object[0]);

				resultatId = PropertyUtils.getProperty(
						controllerVue.get(vue.getTabFolder().getItem(vue.getTabFolder().getSelectionIndex()).getControl()).getSelectedObject().getValue(),
						controllerVue.get(vue.getTabFolder().getItem(vue.getTabFolder().getSelectionIndex()).getControl()).getChampsId()
						);
				if(resultat==null && resultatId!=null)
					resultat=controllerVue.get(vue.getTabFolder().getItem(vue.getTabFolder().getSelectionIndex()).getControl()).getSelectedObject().getValue();

//				resultatId = ((SWTPaAideRechercheControllerSWT)
//						controllerVue.get(vue.getTabFolder().getItem(vue.getTabFolder().getSelectionIndex()).getControl()))
//						.getTypeObjet().getMethod("get"+((SWTPaAideRechercheControllerSWT)
//								controllerVue.get(vue.getTabFolder().getItem(vue.getTabFolder().getSelectionIndex()).getControl())).getChampsId(),
//								new Class[0]).invoke(((SWTPaAideRechercheControllerSWT)
//										controllerVue.get(vue.getTabFolder().getItem(vue.getTabFolder().getSelectionIndex()).getControl()))
//										.getSelectedObject().getValue(), new Object[0]);

				
				
				ISelection s = ((SWTPaAideRechercheControllerSWT)controllerVue.get(vue.getTabFolder().getItem(vue.getTabFolder().getSelectionIndex()).getControl())).getTableViewer().getSelection();

				fireRetourEcran(new RetourEcranEvent(SWTPaAideControllerSWT.this,new ResultAffiche(resultat.toString(),resultatId.toString(),s)));										

				//vue.getShell().close();
				closeWorkbenchPart();

			} catch (Exception e1) {
				logger.error("Erreur : actionPerformed", e1);
			}
		}
	}
	
	protected class HandlerOK extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException{
			actionOK.run();
			return event;
		}
	}
	
	protected class HandlerValider extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException{
			actionOK.run();
			return event;
		}
	}
	
	protected class HandlerAnnulerAide extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException{
			//vue.getShell().close();
			try {
				if(onClose())
					closeWorkbenchPart();
			} catch (ExceptLgr e) {
				logger.error("",e);
			}
			return event;
		}
	}

	private class ActionAnnuler extends Action {
		public ActionAnnuler(String name) {
			super(name);
		}

		public void run() {
			super.run();
			try {	
				onClose();
				handlerService.executeCommand(C_COMMAND_AIDE_ANNULER_ID, null);
			} catch (ExecutionException e) {
				logger.error("",e);
			} catch (NotDefinedException e) {
				logger.error("",e);
			} catch (NotEnabledException e) {
				logger.error("",e);
			} catch (NotHandledException e) {
				logger.error("",e);
			} catch (ExceptLgr e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	protected class HandlerAfficherFiche extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException{
			try {
				logger.info("ActionAfficher");

				//TODO A changer le controller n'est pas bon dans controllerVue il faut mettre celui de l'editeur ouvert pour la creation ???
				((SWTPaAideRechercheControllerSWT)
						controllerVue.get(vue.getTabFolder().getItem(vue.getTabFolder().getSelectionIndex()).getControl())).refCreation.addRetourEcranListener(getThis());
				
				
///////////////// CREATION DANS EDITEUR ///////////////////////////////////////////				
			ParamAffiche p = ((SWTPaAideRechercheControllerSWT)
						controllerVue.get(vue.getTabFolder().getItem(vue.getTabFolder().getSelectionIndex()).getControl())).paramEcranCreation;
			
			IEditorInput editorInputCreation = ((SWTPaAideRechercheControllerSWT)
					controllerVue.get(vue.getTabFolder().getItem(vue.getTabFolder().getSelectionIndex()).getControl())).editorInputCreation;
			
			String editorCreationId = ((SWTPaAideRechercheControllerSWT)
					controllerVue.get(vue.getTabFolder().getItem(vue.getTabFolder().getSelectionIndex()).getControl())).editorCreationId;
			JPABaseControllerSWTStandard refCreation =((SWTPaAideRechercheControllerSWT)
					controllerVue.get(vue.getTabFolder().getItem(vue.getTabFolder().getSelectionIndex()).getControl())).refCreation;
			Object resultatId = null;
			resultatId = PropertyUtils.getProperty(
							controllerVue.get(vue.getTabFolder().getItem(vue.getTabFolder().getSelectionIndex()).getControl()).getSelectedObject().getValue(),
							controllerVue.get(vue.getTabFolder().getItem(vue.getTabFolder().getSelectionIndex()).getControl()).getChampsId()
							);
					
					//verrouillage de l'onglet
			LgrPartListener.getLgrPartListener().setLgrActivePart(null);
			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().
			getActivePage().openEditor(editorInputCreation, editorCreationId);
			LgrPartListener.getLgrPartListener().setLgrActivePart(e);
			LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
			p.setModeEcran(EnumModeObjet.C_MO_CONSULTATION);
			p.setIdFiche(resultatId.toString());
			
			if(e instanceof AbstractLgrMultiPageEditor){
				((AbstractLgrMultiPageEditor)e).findMasterController().configPanel(p);	
			}
			else{
			((JPALgrEditorPart)e).setPanel(((JPALgrEditorPart)e).getController().getVue());
			((JPALgrEditorPart)e).getController().configPanel(p);			
			((JPALgrEditorPart)e).getController().addRetourEcranListener(getThis());
			}
				

			} catch (Exception e1) {
				logger.error("Erreur : actionPerformed", e1);
			}
		
			return event;
		}
	}

	private class ActionAfficher extends Action {
		public ActionAfficher(String name) {
			super(name);
		}

		public void run() {
			super.run();
			try {
				handlerService.executeCommand(C_COMMAND_DOCUMENT_AFFICHER_SEUL_ID, null);
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
	
	protected class HandlerNouveau extends LgrAbstractHandler {

		public Object execute(ExecutionEvent event) throws ExecutionException{
			try {
				logger.info("ActionNouveau");
				//TODO A changer le controller n'est pas bon dans controllerVue il faut mettre celui de l'editeur ouvert pour la creation ???
				((SWTPaAideRechercheControllerSWT)
						controllerVue.get(vue.getTabFolder().getItem(vue.getTabFolder().getSelectionIndex()).getControl())).refCreation.addRetourEcranListener(getThis());
				
///////////////// CREATION DANS EDITEUR ///////////////////////////////////////////				
			ParamAffiche p = ((SWTPaAideRechercheControllerSWT)
						controllerVue.get(vue.getTabFolder().getItem(vue.getTabFolder().getSelectionIndex()).getControl())).paramEcranCreation;

			IEditorInput editorInputCreation = ((SWTPaAideRechercheControllerSWT)
					controllerVue.get(vue.getTabFolder().getItem(vue.getTabFolder().getSelectionIndex()).getControl())).editorInputCreation;
			
			String editorCreationId = ((SWTPaAideRechercheControllerSWT)
					controllerVue.get(vue.getTabFolder().getItem(vue.getTabFolder().getSelectionIndex()).getControl())).editorCreationId;
			JPABaseControllerSWTStandard refCreation =((SWTPaAideRechercheControllerSWT)
					controllerVue.get(vue.getTabFolder().getItem(vue.getTabFolder().getSelectionIndex()).getControl())).refCreation;
			
			//verrouillage de l'onglet
			LgrPartListener.getLgrPartListener().setLgrActivePart(null);
			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().
			getActivePage().openEditor(editorInputCreation, editorCreationId);
			LgrPartListener.getLgrPartListener().setLgrActivePart(e);
			LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
			p.setModeEcran(EnumModeObjet.C_MO_INSERTION);
			p.setIdFiche("-1");
			if(e instanceof JPALgrEditorPart) {
				((JPALgrEditorPart)e).getController().setEnregistreEtFerme(true);
				((JPALgrEditorPart)e).setPanel(((JPALgrEditorPart)e).getController().getVue());
				((JPALgrEditorPart)e).getController().configPanel(p);
				//((JPALgrEditorPart)e).getController().setListeEntity(refCreation.getListeEntity());
				//SWTPaFamilleController swtPaFamilleController = new SWTPaFamilleController(((EditorFamille)e).getComposite());
				//swtController = swtController.getClass().getDeclaredConstructor(((LgrEditorPart)e).getPanel().getClass()).newInstance(((LgrEditorPart)e).getPanel());
				//((LgrEditorPart)e).setController(swtController);
				//((LgrEditorPart)e).setPanel(swtController.getVue());
				//swtController.configPanel(p);
				//swtPaFamilleController.configPanel(p);

				((JPALgrEditorPart)e).getController().addRetourEcranListener(getThis());
			} else if(e instanceof AbstractLgrMultiPageEditor) {
				((AbstractLgrMultiPageEditor)e).findMasterController().setEnregistreEtFerme(true);
//				((JPALgrEditorPart)e).setPanel(((AbstractLgrMultiPageEditor)e).findMasterController().getVue());
				((AbstractLgrMultiPageEditor)e).findMasterController().configPanel(p);
				((AbstractLgrMultiPageEditor)e).findMasterController().addRetourEcranListener(getThis());
			}

				

			} catch (Exception e1) {
				logger.error("Erreur : actionPerformed", e1);
			}
		
			return event;
		}
	}

	private class ActionNouveau extends Action {
		public ActionNouveau(String name) {
			super(name);
		}

		public void run() {
			super.run();
			try {
				handlerService.executeCommand(C_COMMAND_AIDE_NOUVEAU_ID, null);
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

	public void retourEcran(RetourEcranEvent evt) {
		
		//logger.debug("retour creation : "+evt.getRetour());

//#JPA
//		if (!((SWTPaAideRechercheControllerSWT)
//				controllerVue.get(vue.getTabFolder().getItem(vue.getTabFolder().getSelectionIndex()).getControl())).getQuery().isOpen())
//			((SWTPaAideRechercheControllerSWT)
//					controllerVue.get(vue.getTabFolder().getItem(vue.getTabFolder().getSelectionIndex()).getControl())).getQuery().open();

		//((PaAideRechercheControllerSWT)controllerVue.get(((PaAideRecherche)vue.getTabPane().getSelectedComponent()))).refresh();
		

		SWTPaAideRechercheControllerSWT paAideTmp =((SWTPaAideRechercheControllerSWT)
				controllerVue.get(vue.getTabFolder().getItem(vue.getTabFolder().getSelectionIndex()).getControl()));
		if(((ResultAide)evt.getRetour()).getObjet()!=null) {
//#JPA
//			paAideTmp.getQuery().refresh();

			WritableList writableList;
			if(((JPABaseControllerSWT)evt.getSource()).getListeEntity()!=null)
				 writableList = new WritableList(paAideTmp.getRealm(),((JPABaseControllerSWT)evt.getSource()).getListeEntity(), paAideTmp.getTypeObjet());
			else
			 writableList = new WritableList(paAideTmp.getRealm(), paAideTmp.getModel().remplirListe(), paAideTmp.getTypeObjet());
			paAideTmp.getTableViewer().setInput(writableList);
			LinkedList<ModelObject> listeObjet = new LinkedList<ModelObject>();
			for (Object object : writableList) {
				listeObjet.add((ModelObject)object);
			}
			
			if(!paAideTmp.getAffichageAideRemplie())((PaAideRechercheSWT)paAideTmp.getVue()).getTfChoix().setText("");
			paAideTmp.getTableViewer().refresh();
			((PaAideRechercheSWT)paAideTmp.getVue()).getTfChoix().setText("");

			//paAideTmp.getTableViewer().add(((ResultAide)evt.getRetour()).getObjet());
		//	paAideTmp.getTableViewer().setSelection(new StructuredSelection(((ResultAide)evt.getRetour()).getObjet()),true);
			//paAideTmp.getTableViewer().setSelection(new StructuredSelection(((ResultAide)evt.getRetour()).getObjet()),true);
			if(paAideTmp.getModel().rechercheDansListe(listeObjet,((ResultAide)evt.getRetour()).getIdChamps(), ((ResultAide)evt.getRetour()).getIdValeur())!=null)
				paAideTmp.getTableViewer().setSelection(new StructuredSelection(paAideTmp.getModel().rechercheDansListe(listeObjet,((ResultAide)evt.getRetour()).getIdChamps(), ((ResultAide)evt.getRetour()).getIdValeur())),true);
		}
		
		//((SWTPaAideRechercheControllerSWT)controllerVue.get(vue.getTabFolder().getItem(vue.getTabFolder().getSelectionIndex()).getControl())).getQuery().locate(((ResultAide)evt.getRetour()).getIdChamps(),((ResultAide)evt.getRetour()).getIdValeur());
	}
	
	
	
	@Override
	protected void sortieChamps() {
		// TODO Raccord de méthode auto-généré
	}
	
	@Override
	protected void actAide() throws Exception {}
	@Override
	protected void actAide(String message) throws Exception {}
	@Override
	protected void actAnnuler() throws Exception {}
	@Override
	protected void actEnregistrer() throws Exception {}
	@Override
	protected void actFermer() throws Exception {}
	@Override
	protected void actImprimer() throws Exception {}
	@Override
	protected void actInserer() throws Exception {}
	@Override
	protected void actModifier() throws Exception {}
	@Override
	protected void actRefresh() throws Exception {}
	@Override
	protected void actSupprimer() throws Exception {}
	@Override
	public void initEtatComposant() {}
	@Override
	protected void initMapComposantDecoratedField() {}


}

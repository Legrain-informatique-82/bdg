package fr.legrain.gestCom.librairiesEcran.workbench;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.part.EditorPart;

import fr.legrain.gestCom.librairiesEcran.swt.BaseControllerSelection;
import fr.legrain.gestCom.librairiesEcran.swt.BaseControllerSelection;
import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.SWTBaseControllerSWTStandard;
import fr.legrain.lib.data.ChangeModeEvent;
import fr.legrain.lib.data.ChangeModeListener;
import fr.legrain.lib.data.ExceptLgr;


abstract public class LgrEditorPart extends EditorPart implements IPartListener  {
	
	static Logger logger = Logger.getLogger(LgrEditorPart.class.getName());

	public static final String ID = null; //$NON-NLS-1$
	private Composite panel = null;
	private JPABaseControllerSWTStandard controller = null;
	private BaseControllerSelection controllerSelection = null;
	private EditorPart parent = null;
	
	public LgrEditorPart(EditorPart parent) {
		super();
		this.parent = parent;
		initEditor();
	}


	public LgrEditorPart() {
		super();
		initEditor();
//		controller = new SWTPaEditorFactureController(panel);
//		controllerLigne = new SWTPaLigneController(panel.getPaSaisieLigneSWT(),controller.getSwtFacture());
//		controller.setControllerLigne(controllerLigne);
//		controller.getIbTaTable().addChangeModeListener(new ChangeMode());
	}
	
	abstract protected void initEditor();
	
	private class ChangeMode implements ChangeModeListener {
		public void changementMode(ChangeModeEvent evt) {
			if(!PlatformUI.getWorkbench().getDisplay().isDisposed()) { 
				PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
					public void run() {
						try {
							firePropertyChange(PROP_DIRTY);
						} catch (Exception e) {
							logger.error(e);
						}
					}
				});
			}
		}
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
//		final Display display = Display.getCurrent();
//		final boolean[] runLoopAndContinue = new boolean[] {true, true};
		final IProgressMonitor m = monitor;
		
		m.setCanceled(true);
		

		try {
//			display.syncExec(new Runnable() {
//				public void run() {
					m.setCanceled(false);
//				}
//			});
			controller.executeCommand(SWTBaseControllerSWTStandard.C_COMMAND_GLOBAL_ENREGISTRER_ID,true);
			controller.executeCommand(SWTBaseControllerSWTStandard.C_COMMAND_GLOBAL_FERMER_ID,true);
//			runLoopAndContinue[1] = true;
		} catch (Exception e) {
			logger.error("Erreur à l'enregistrement, fermeture éditeur",e);
//			runLoopAndContinue[1] = false;	
			m.setCanceled(true);
		}

//		display.syncExec(new Runnable() {
//			public void run() {
//				m.setCanceled(true);
//				runLoopAndContinue[0] = false;    // Tell method to stop looping.
//			}
//		});
//		display.asyncExec(null);  // Wake SWT UI thread up one more time to make sure it terminates.

	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
	    setInput(input);
	    
	    IContextService ics = (IContextService)getSite().getService(IContextService.class);
	    //ics = (IContextService)PlatformUI.getWorkbench().getService(IContextService.class);
	   
	}

	@Override
	public boolean isDirty() {
		changeEditorName();
		if(controller==null || controller.getDaoStandard()==null)
			return false;
		else
			 return controller.getDaoStandard().dataSetEnModif();
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	public Composite getPanel() {
		return panel;
	}

	public void setPanel(Composite panel) {
		this.panel = panel;
	}

	public JPABaseControllerSWTStandard getController() {
		return controller;
	}

	public void setController(JPABaseControllerSWTStandard controller) {
		this.controller = controller;
		if(parent==null)
			controller.setWorkbenchPart(this);
		else
			controller.setWorkbenchPart(parent);
		if(controller.getDaoStandard()!=null) {
			controller.getDaoStandard().addChangeModeListener(new ChangeMode());
		}
	}

	@Override
	public void setFocus() {
		if(controller!=null && controller.getFocusCourantSWT()!=null) {
			if(!controller.getFocusCourantSWT().isDisposed()) {
				controller.getFocusCourantSWT().forceFocus();
			}
		}
		
		if(LgrPartListener.getLgrPartListener().getLgrActivePart()!=null) {
			if(this!=LgrPartListener.getLgrPartListener().getLgrActivePart()) {
				//activation de la WorkbenchPart verrouillee a la place de la partie demandee
				LgrPartListener.getLgrPartListener().getLgrActivePart().getSite().getPage().activate(LgrPartListener.getLgrPartListener().getLgrActivePart());
			}
		}
	}

//    class FenetrePart implements IPartListener{
//		public void partActivated(IWorkbenchPart part) {
////			if(controller!=null)
////				controller.activeWorkenchPartCommands(true);
//		}
//
//		public void partBroughtToTop(IWorkbenchPart part) {}
//
//		public void partClosed(IWorkbenchPart part) {
//			try {
//				//je n'active la perspective bureau que si je ferme l'éditeur facture, et non pas si je ferme
//				//l'éditeur de l'impression
//				if(part.getSite().getId().equals(LgrEditorPart.ID_EDITOR)){
//					controller.getIbTaTable().annuler();
//					controller.actAnnuler();
//			    	if (controller!=null){
//			    		controller.annulerListeContext();
//			    		controller.setIdContext("");
//			    	}
//			    	if (controllerLigne!=null){
//			    		controllerLigne.annulerListeContext();
//			    		controllerLigne.setIdContext("");
//			    	}					
//					//controller.setControllerLigne(null);
//					//controller=null;
////					if(PlatformUI.getWorkbench().getActiveWorkbenchWindow() != null 
////							&& PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()!=null) {
////					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closePerspective(
////					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getPerspective(),
////					false, false);
////					}
//					PlatformUI.getWorkbench().showPerspective(Perspective.ID, PlatformUI.getWorkbench().getActiveWorkbenchWindow());
//				}
//			} catch (Exception e) {
//				logger.error(e);
//			}
//		}
//
//		public void partDeactivated(IWorkbenchPart part) {
//			if(controller!=null){
//				controller.annulerListeContext();
////				for (IContextActivation c : controller.getListeContext()) {
////					((IContextService) PlatformUI.getWorkbench().getService(IContextService.class))
////					.deactivateContext(c);
////				}
//				controller.getControllerLigne().annulerListeContext();
////				for (IContextActivation c : controllerLigne.getListeContext()) {
////					((IContextService) PlatformUI.getWorkbench().getService(IContextService.class))
////					.deactivateContext(c);
////				}
//			}
//		}
//
//		public void partOpened(IWorkbenchPart part) {
//			try {
//				if(part.getSite().getId().equals(LgrEditorPart.ID_EDITOR)){
//				controller.actInserer();
//				}
//			} catch (Exception e) {
//				logger.error(e);
//			}
//		}
//    }
    
    private void changeEditorName() {
		//setPartName("Facture "+((PaEditorFactureSWT)controller.getVue()).getTfCODE_FACTURE().getText());
    	//setPartName(controller.getTitre());
	}


	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		
	}
	
	public BaseControllerSelection getControllerSelection() {
		return controllerSelection;
	}


	public void setControllerSelection(BaseControllerSelection controllerSelection) {
		this.controllerSelection = controllerSelection;
		if(parent==null)
			controllerSelection.setWorkbenchPart(this);
		else
			controllerSelection.setWorkbenchPart(parent);
		if(controllerSelection.getDaoStandard()!=null) {
			controllerSelection.getDaoStandard().addChangeModeListener(new ChangeMode());
		}
	}

	
	/*
	 * Debut IPartListener
	 */

	public void partActivated(IWorkbenchPart part) {
		// TODO Auto-generated method stub
	}


	public void partBroughtToTop(IWorkbenchPart part) {
		// TODO Auto-generated method stub
	}


	public void partClosed(IWorkbenchPart part) {
		if(controller!=null) {
			try {
				controller.onClose();
			} catch (ExceptLgr e) {
				logger.error("",e);
			}
		}
	}


	public void partDeactivated(IWorkbenchPart part) {
		// TODO Auto-generated method stub
	}


	public void partOpened(IWorkbenchPart part) {
		// TODO Auto-generated method stub
	}
	
	/*
	 * Fin IPartListener
	 */
	static public IEditorPart verifEditeurOuvert() {
		
		IEditorPart editor = null;
		if(PlatformUI.getWorkbench().getActiveWorkbenchWindow() != null){
			try {
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

				if(page.getEditorReferences().length>0){	//au moins 1 éditeur ouvert			
					int i = 0;
					while(i<page.getEditorReferences().length && editor==null){
						editor = page.getEditorReferences()[i].getEditor(false);
					}
					i++;
				}
				return editor;
			} catch (Exception e) {
				logger.error("",e);
			}
		}
		return editor;
	}
	
}

package fr.legrain.licence.handle;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;

import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.gestCom.librairiesEcran.workbench.lgrFormEditor;
import fr.legrain.licence.divers.ParamAfficheSupport;
import fr.legrain.licence.editors.FormEditorInputLicence;
import fr.legrain.licence.editors.FormEditorLicence;

public class HandlerEtatLicence extends LgrAbstractHandler {

	static Logger logger = Logger.getLogger(HandlerEtatLicence.class.getName());
	private IWorkbenchWindow window;
	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

//	public Object execute(ExecutionEvent event) throws ExecutionException {
//		try {
//			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(
//						new FormEditorInputLicence(), FormEditorLicence.ID
//					);
//		} catch (PartInitException e) {
//			logger.error("",e);
//		} catch (Exception e) {
//			logger.error("",e);
//		}
//		return null;
//	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		this.window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		try {
			IEditorPart editor = lgrFormEditor.verifEditeurOuvert(FormEditorLicence.ID);
			if(editor==null){
				IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(
						new FormEditorInputLicence(), FormEditorLicence.ID);
			} else {
				page.activate(editor);
			}
		} catch (WorkbenchException e) {
			logger.error("Erreur pendant l'ouverture de l'éditeur ",e);
		}
	return null;
	}
	
	
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isHandled() {
		// TODO Auto-generated method stub
		return true;
	}

}

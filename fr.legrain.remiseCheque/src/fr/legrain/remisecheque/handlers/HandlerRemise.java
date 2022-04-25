package fr.legrain.remisecheque.handlers;
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
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheReglementMultiple;
import fr.legrain.gestCom.librairiesEcran.workbench.AbstractLgrMultiPageEditor;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.remisecheque.editors.EditorInputRemise;
import fr.legrain.remisecheque.editors.RemiseMultiPageEditor;



public class HandlerRemise extends LgrAbstractHandler {
	
	static Logger logger = Logger.getLogger(HandlerRemise.class.getName());
	private IWorkbenchWindow window;
	
	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

	public void dispose() {
		// TODO Auto-generated method stub
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		this.window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		try {
			IEditorPart editor = AbstractLgrMultiPageEditor.chercheEditeurDocumentOuvert(RemiseMultiPageEditor.ID_EDITOR);
			if(editor==null){
			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(
					new EditorInputRemise(), RemiseMultiPageEditor.ID_EDITOR);
			LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
			ParamAfficheReglementMultiple paramAfficheReglementMultiple = new ParamAfficheReglementMultiple();
			((AbstractLgrMultiPageEditor)e).findMasterController().configPanel(paramAfficheReglementMultiple);
			} else {
				page.activate(editor);
			}
		} catch (WorkbenchException e) {
			logger.error("Erreur pendant l'ouverture de la perspective Remise",e);
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

	public void removeHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

}

package fr.legrain.saisiecaisse.handlers;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.workbench.JPALgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.saisiecaisse.divers.ParamAfficheDepot;
import fr.legrain.saisiecaisse.divers.ParamAfficheOperation;
import fr.legrain.saisiecaisse.editor.EditorDepot;
import fr.legrain.saisiecaisse.editor.EditorInputDepot;
import fr.legrain.saisiecaisse.editor.EditorInputOperation;
import fr.legrain.saisiecaisse.editor.EditorOperation2;


public class HandlerOperation extends LgrAbstractHandler {
	
	static Logger logger = Logger.getLogger(HandlerOperation.class.getName());

	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

	public void dispose() {
		// TODO Auto-generated method stub
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
			openEditor(new EditorInputOperation(), EditorOperation2.ID);
			LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
//			SWTPaUniteController swtPaUniteController = new SWTPaUniteController(((EditorUnite)e).getComposite());
//			
//			((LgrEditorPart)e).setController(swtPaUniteController);
//			((LgrEditorPart)e).setPanel(((EditorUnite)e).getComposite());
//			
//			ParamAfficheUnite paramAfficheUnite = new ParamAfficheUnite();
//			swtPaUniteController.configPanel(paramAfficheUnite);
			
			ParamAfficheOperation paramAfficheDepot = new ParamAfficheOperation();
			((JPALgrEditorPart)e).setPanel(((JPALgrEditorPart)e).getController().getVue());
			((JPALgrEditorPart)e).getController().configPanel(paramAfficheDepot);
			
		} catch (PartInitException e) {
			logger.error("",e);
		} catch (Exception e) {
			logger.error("",e);
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

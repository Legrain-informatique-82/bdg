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
import fr.legrain.saisiecaisse.divers.ParamAfficheTOperation;
import fr.legrain.saisiecaisse.editor.EditorInputTOperation;
import fr.legrain.saisiecaisse.editor.EditorTOperation;


public class HandlerTOperation extends LgrAbstractHandler {
	
	static Logger logger = Logger.getLogger(HandlerTOperation.class.getName());

	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

	public void dispose() {
		// TODO Auto-generated method stub
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
			.openEditor(new EditorInputTOperation(), EditorTOperation.ID);
			LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
//			SWTPaTTvaController swtPaTTvaController = new SWTPaTTvaController(((EditorTypeTVA)e).getComposite());
//			
//			((LgrEditorPart)e).setController(swtPaTTvaController);
//			((LgrEditorPart)e).setPanel(((EditorTypeTVA)e).getComposite());
//			
//			
//			ParamAfficheTTva paramAfficheTTva = new ParamAfficheTTva();
//			swtPaTTvaController.configPanel(paramAfficheTTva);
			
			ParamAfficheTOperation paramAfficheArticles = new ParamAfficheTOperation();
			((JPALgrEditorPart)e).setPanel(((JPALgrEditorPart)e).getController().getVue());
			((JPALgrEditorPart)e).getController().configPanel(paramAfficheArticles);
			
		} catch (PartInitException e) {
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

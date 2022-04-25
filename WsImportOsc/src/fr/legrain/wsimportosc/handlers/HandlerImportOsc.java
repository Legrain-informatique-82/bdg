package fr.legrain.wsimportosc.handlers;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.wsimportosc.ecran.ParamAfficheImportOscWs;
import fr.legrain.wsimportosc.editor.EditorImportOscWs;
import fr.legrain.wsimportosc.editor.EditorInputImportOscWs;

//public class HandlerImportOsc implements IHandler {
public class HandlerImportOsc extends LgrAbstractHandler {
	
	static Logger logger = Logger.getLogger(HandlerImportOsc.class.getName());		


	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
	
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
			openEditor(new EditorInputImportOscWs(), EditorImportOscWs.ID);
			LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
			
			ParamAfficheImportOscWs paramAfficheImportOscWs = new ParamAfficheImportOscWs();
			((LgrEditorPart)e).setPanel(((LgrEditorPart)e).getController().getVue());
			((LgrEditorPart)e).getController().configPanel(paramAfficheImportOscWs);

		} catch (PartInitException e) {
			logger.error("",e);
		}
		return null;
	}

//	@Override
//	public Object execute(ExecutionEvent event) throws ExecutionException {
//		// TODO Auto-generated method stub
//		try {
//			AskWebService sss = new AskWebService();
//			int info = sss.obtenirInfoWsorders();
//			System.out.println("eee--"+info);
//			//System.out.println("eee--");
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		
//		return null;
//	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isHandled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

}

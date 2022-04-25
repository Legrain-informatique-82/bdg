package fr.legrain.importinfosegroupeware.handlers;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.importinfosegroupeware.swt.DialogInfosEgw;

//public class HandlerImportOsc implements IHandler {
public class HandlerImportInfosEgw extends LgrAbstractHandler {
	
	static Logger logger = Logger.getLogger(HandlerImportInfosEgw.class.getName());		


	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
	
	public Object execute(ExecutionEvent event) throws ExecutionException {
//		try {
//			IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
//			openEditor(new EditorInputImportOscWs(), EditorImportOscWs.ID);
//			LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
//			
//			ParamAfficheImportOscWs paramAfficheImportOscWs = new ParamAfficheImportOscWs();
//			((LgrEditorPart)e).setPanel(((LgrEditorPart)e).getController().getVue());
//			((LgrEditorPart)e).getController().configPanel(paramAfficheImportOscWs);
//
//		} catch (PartInitException e) {
//			logger.error("",e);
//		}
	
		//Display display = Display.getDefault();
		Shell dialogShell = new Shell(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
				SWT.RESIZE | SWT.CLOSE | SWT.MAX | SWT.APPLICATION_MODAL);
		dialogShell.setText("Import infos de Egroupeware");
		DialogInfosEgw dialogInfosEgw = new DialogInfosEgw(dialogShell,SWT.NULL);
		dialogInfosEgw.showDialog(dialogShell,dialogInfosEgw);
		ActionsDialogInfosEgw actionsDialogInfosEgw = new ActionsDialogInfosEgw();
		actionsDialogInfosEgw.actionButtonAnnuler(dialogInfosEgw.getButtonAnnuler(), dialogShell);
		actionsDialogInfosEgw.actionButtonValider(dialogInfosEgw, dialogShell);
		
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

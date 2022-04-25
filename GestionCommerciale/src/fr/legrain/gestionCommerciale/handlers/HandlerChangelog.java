package fr.legrain.gestionCommerciale.handlers;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.FormDialog;

import fr.legrain.changelog.ws.Article;
import fr.legrain.changelog.ws.Category;
import fr.legrain.changelog.ws.SubCategory;
import fr.legrain.changelog.ws.client.ChangeLogDialog;
import fr.legrain.changelog.ws.client.ChangelogWebServiceClientCXF;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestionCommerciale.UtilWorkspace;
import fr.legrain.lib.data.MessageDialogLGR;
import fr.legrain.libMessageLGR.LgrMess;

public class HandlerChangelog extends LgrAbstractHandler {
	static Logger logger = Logger.getLogger(HandlerChangelog.class.getName());

	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

	public void dispose() {
		// TODO Auto-generated method stub
	}
	
	

	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().
		setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
		try{			
			ChangelogWebServiceClientCXF c = new ChangelogWebServiceClientCXF();
			try {
				Category cat = c.changeLog();
				
				ChangeLogDialog dialog = new ChangeLogDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), cat);
				 dialog.create();
				 dialog.getShell().setSize(1024, 768);
				 dialog.open();
				
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().
			setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));			
		}
		return arg0;
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

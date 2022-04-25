package fr.legrain.gestionCommerciale.handlers;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestionCommerciale.UtilWorkspace;
import fr.legrain.lib.data.MessageDialogLGR;
import fr.legrain.libMessageLGR.LgrMess;

public class HandlerMiseAJourBase extends LgrAbstractHandler {
	static Logger logger = Logger.getLogger(HandlerMiseAJourBase.class.getName());

	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

	public void dispose() {
		// TODO Auto-generated method stub
	}
	
	

	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		Boolean temp = LgrMess.isTOUJOURS_MAJ();
		UtilWorkspace uw =new UtilWorkspace();
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().
		setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
		try{			
			LgrMess.setTOUJOURS_MAJ(true);
			if(uw.appelMAJDatabase())
			    MessageDialogLGR.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell()
					, "Mise à jour", "Mise à jour de la base terminée.");
		}finally{
			LgrMess.setTOUJOURS_MAJ(temp);
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

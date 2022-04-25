package fr.legrain.gestionCommerciale.handlers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestionCommerciale.UtilWorkspace;
import fr.legrain.lib.data.MessageDialogLGR;
import fr.legrain.libMessageLGR.LgrMess;

public class HandlerOuvreFichierLog extends LgrAbstractHandler {
	static Logger logger = Logger.getLogger(HandlerOuvreFichierLog.class.getName());

	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

	public void dispose() {
		// TODO Auto-generated method stub
	}
	
	

	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		String url = Const.C_RCP_INSTALL_LOCATION+"/log_gestCom.log";
		DesktopActionOpen.open(url);
		
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

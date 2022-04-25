package fr.legrain.openmail.mail.handlers;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import fr.legrain.openmail.mail.OpenmailMail;


public class HandlerSendMailOpenMail extends AbstractHandler {

	static Logger logger = Logger.getLogger(HandlerSendMailOpenMail.class.getName());

	private IWorkbenchWindow window;

	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

	public void dispose() {
		// TODO Auto-generated method stub
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		this.window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		try {
			OpenmailMail om = new OpenmailMail();
			String omDispo = om.checkConfig();
			if(omDispo==null) {
				om.sendMail(null);
			} else {
				MessageDialog.openError(window.getShell(), "Erreur", omDispo);
			}
		} catch (Exception e1) {
			logger.error("Erreur : actionPerformed", e1);
		}
		return null;
	}

	public boolean isEnabled() {
		return true;
	}

	public boolean isHandled() {
		return true;
	}

	public void removeHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

}

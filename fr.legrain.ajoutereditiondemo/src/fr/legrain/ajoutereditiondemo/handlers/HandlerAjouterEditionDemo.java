package fr.legrain.ajoutereditiondemo.handlers;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

import fr.legrain.ajoutereditiondemo.controller.SwtDemoEditionPerControl;
import fr.legrain.ajoutereditiondemo.divers.constPlugin;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;

public class HandlerAjouterEditionDemo extends LgrAbstractHandler {

	static Logger logger = Logger.getLogger(HandlerAjouterEditionDemo.class.getName());
	
	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
	}
	
	@Override
	public void dispose() {
	
	}
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// TODO Auto-generated method stub
		


		Shell dialogShell = new Shell(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
				//SWT.DIALOG_TRIM |SWT.APPLICATION_MODAL);
				SWT.RESIZE | SWT.CLOSE | SWT.MAX | SWT.APPLICATION_MODAL);
		dialogShell.setSize(800, 600);
		dialogShell.setText(constPlugin.TITLE_SHELL);
		dialogShell.setLayout(new FillLayout());
		
		SwtDemoEditionPerControl swtDemoEditionPerControl = new SwtDemoEditionPerControl(dialogShell);
		swtDemoEditionPerControl.init();
		swtDemoEditionPerControl.initAction();
		dialogShell.open();
		return null;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
	public boolean isHandled() {
		return true;
	}
	
	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
	}
}

package fr.legrain.ajouteredition.handlers;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;


import fr.legrain.ajouteredition.controller.SwtChoixChargeEditionControl;
import fr.legrain.ajouteredition.divers.ConstPlugin;
import fr.legrain.ajouteredition.swt.SwtReportWithExpandbar;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;

public class HandlerAjouterEdition extends LgrAbstractHandler {

	static Logger logger = Logger.getLogger(HandlerAjouterEdition.class.getName());
	
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
		dialogShell.setText(ConstPlugin.TITLE_SHELL);
		dialogShell.setLayout(new FillLayout());

		SwtChoixChargeEditionControl swtChoixChargeEditionControl = new SwtChoixChargeEditionControl(dialogShell);
		swtChoixChargeEditionControl.init();
		swtChoixChargeEditionControl.initActionElement();
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

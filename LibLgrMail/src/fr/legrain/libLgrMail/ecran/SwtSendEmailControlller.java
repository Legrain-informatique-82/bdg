package fr.legrain.libLgrMail.ecran;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;

public class SwtSendEmailControlller {

	private SwtSendEmail swtSendEmail;
	
	public void initGUI(Shell shell) {
		shell.setText(ConsSendEmail.TITLE_SHELL_SEND_EMAIL);
		shell.setLayout(new FillLayout());
		swtSendEmail = new SwtSendEmail(shell, SWT.NULL);
		
		shell.open();
	}
	
}

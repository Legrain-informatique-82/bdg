package fr.legrain.gestCom.librairiesEcran.swt;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;

public class PasswordDialog extends Dialog {
	//  private Text userText;
	private Text passwordText;
	//  private String user="";
	private String password = null;

	private String titre = null;
	private Button btnEnregistrer = null;
	private boolean enregistrer = false;


	/** * Create the dialog. * * @param parentShell */
	public PasswordDialog(Shell parentShell) {
		super(parentShell);
	}

	public PasswordDialog(Shell parentShell, String titre) {
		super(parentShell);
		this.titre = titre;
	}


	/** * Create contents of the dialog. * * @param parent */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gl_container = new GridLayout(2, false);
		gl_container.marginRight = 5;
		gl_container.marginLeft = 10;
		container.setLayout(gl_container);

		if(titre!=null) {
			Label lblTitre = new Label(container, SWT.NONE);
			GridData gd_lblNewLabel = new GridData(SWT.LEFT, SWT.CENTER, false,
					false,2, 1);
			gd_lblNewLabel.horizontalIndent = 1;
			lblTitre.setLayoutData(gd_lblNewLabel);
			lblTitre.setText(titre);
		}

		//    Label lblUser = new Label(container, SWT.NONE);
		//    lblUser.setText("User:");
		//
		//    userText = new Text(container, SWT.BORDER);
		//    userText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
		//        1, 1));
		//    userText.setText(user);

		Label lblNewLabel = new Label(container, SWT.NONE);
		GridData gd_lblNewLabel = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_lblNewLabel.horizontalIndent = 1;
		lblNewLabel.setLayoutData(gd_lblNewLabel);
		lblNewLabel.setText("Mot de passe :");

		passwordText = new Text(container, SWT.BORDER);
		passwordText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		passwordText.setText("");
		passwordText.setEchoChar('*');

		btnEnregistrer = new Button(container,SWT.CHECK);
		btnEnregistrer.setText("Sauvegarder le mot de passe dans les péférences");
		GridData gd_enregistrer = new GridData(SWT.LEFT, SWT.CENTER, false,false,2, 1);
		gd_enregistrer.horizontalIndent = 1;
		btnEnregistrer.setLayoutData(gd_enregistrer);

		return container;
	}

	public boolean isEnregistrer() {
		return enregistrer;
	}

	/** * Create contents of the button bar. * * @param parent */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}


	/** * Return the initial size of the dialog. */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 180);
	}

	@Override
	protected void okPressed() {
		//    user = userText.getText();
		password = passwordText.getText();
		enregistrer = btnEnregistrer.getSelection();

		super.okPressed();
	}

	//  public String getUser() {
	//    return user;
	//  }
	//
	//  public void setUser(String user) {
	//    this.user = user;
	//  }

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

} 
package fr.legrain.gestionDossier.wizards;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CBanner;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class DiaInfoDossier extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private Label label1;
	private Button button3;
	private Text text2;
	private Button button2;
	private Button button1;
	private Text text1;
	private Label label2;

	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Dialog inside a new Shell.
	*/
	public static void main(String[] args) {
		try {
			Display display = Display.getDefault();
			Shell shell = new Shell(display);
			DiaInfoDossier inst = new DiaInfoDossier(shell, SWT.NULL);
			inst.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DiaInfoDossier(Shell parent, int style) {
		super(parent, style);
	}
	
	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

			GridLayout dialogShellLayout = new GridLayout();
			dialogShellLayout.numColumns = 3;
			dialogShell.setLayout(dialogShellLayout);
			dialogShell.layout();
			dialogShell.pack();
			dialogShell.setSize(416, 255);
			{
				label1 = new Label(dialogShell, SWT.NONE);
				GridData label1LData = new GridData();
				label1LData.horizontalAlignment = GridData.FILL;
				label1.setLayoutData(label1LData);
				label1.setText("label1");
			}
			{
				text1 = new Text(dialogShell, SWT.NONE);
				GridData text1LData = new GridData();
				text1LData.horizontalAlignment = GridData.FILL;
				text1LData.grabExcessHorizontalSpace = true;
				text1.setLayoutData(text1LData);
				text1.setText("text1");
			}
			{
				button1 = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				GridData button1LData = new GridData();
				button1LData.horizontalAlignment = GridData.FILL;
				button1.setLayoutData(button1LData);
				button1.setText("button1");
			}
			{
				label2 = new Label(dialogShell, SWT.NONE);
				GridData label2LData = new GridData();
				label2LData.horizontalAlignment = GridData.FILL;
				label2.setLayoutData(label2LData);
				label2.setText("label2");
			}
			{
				text2 = new Text(dialogShell, SWT.NONE);
				GridData text2LData = new GridData();
				text2LData.horizontalAlignment = GridData.FILL;
				text2LData.grabExcessHorizontalSpace = true;
				text2.setLayoutData(text2LData);
				text2.setText("text2");
			}
			{
				button2 = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				GridData button2LData = new GridData();
				button2LData.horizontalAlignment = GridData.FILL;
				button2.setLayoutData(button2LData);
				button2.setText("button2");
			}
			{
				button3 = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				button3.setText("button3");
			}
			dialogShell.open();
			Display display = dialogShell.getDisplay();
			while (!dialogShell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Label getLabel1() {
		return label1;
	}
	
	public Text getText2() {
		return text2;
	}

	public Shell getDialogShell() {
		return dialogShell;
	}

	public Button getButton3() {
		return button3;
	}

	public Button getButton2() {
		return button2;
	}

	public Button getButton1() {
		return button1;
	}

	public Text getText1() {
		return text1;
	}

	public Label getLabel2() {
		return label2;
	}

}

package fr.legrain.sauvegardeFTP.preferences;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.SWT;

import com.cloudgarden.resource.SWTResourceManager;


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
public class CompositePreferencePage extends org.eclipse.swt.widgets.Composite {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
	
	
	private Text textPasswordServerFTP;
	private Label labelPasswordServerFTP;
	private Text textLoginServerFTP;
	private Label labelLoginServerFTP;
	private Text textPortServerFTP;
	private Label labelPortServerFTP;
	private Text textNameServerFTP;
	private Label labelNameServerFTP;

	
	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void main(String[] args) {
		showGUI();
	}
	
	/**
	* Overriding checkSubclass allows this class to extend org.eclipse.swt.widgets.Composite
	*/	
	protected void checkSubclass() {
	}
	
	/**
	* Auto-generated method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void showGUI() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		CompositePreferencePage inst = new CompositePreferencePage(shell, SWT.NULL);
		Point size = inst.getSize();
		shell.setLayout(new FillLayout());
		shell.layout();
		if(size.x == 0 && size.y == 0) {
			inst.pack();
			shell.pack();
		} else {
			Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
			shell.setSize(shellBounds.width, shellBounds.height);
		}
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	public CompositePreferencePage(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}
			
	private void initGUI() {
		GridLayout thisLayout = new GridLayout();
		thisLayout.numColumns = 2;
		this.setLayout(thisLayout);
		{
			labelNameServerFTP = new Label(this, SWT.NONE);
			GridData labelNameServerFTPLData = new GridData();
			labelNameServerFTP.setLayoutData(labelNameServerFTPLData);
			labelNameServerFTP.setText("Nom serveur FTP : ");
		}
		{
			GridData textNameServerFTPLData = new GridData();
			textNameServerFTPLData.grabExcessHorizontalSpace = true;
			textNameServerFTPLData.horizontalAlignment = GridData.FILL;
			textNameServerFTP = new Text(this, SWT.BORDER);
			textNameServerFTP.setLayoutData(textNameServerFTPLData);
		}
		{
			labelPortServerFTP = new Label(this, SWT.NONE);
			GridData labelPortServerFTPLData = new GridData();
			labelPortServerFTP.setLayoutData(labelPortServerFTPLData);
			labelPortServerFTP.setText("Port serveur FTP : ");
		}
		{
			GridData textPortServerFTPLData = new GridData();
			textPortServerFTPLData.horizontalAlignment = GridData.FILL;
			textPortServerFTPLData.grabExcessHorizontalSpace = true;
			textPortServerFTP = new Text(this, SWT.BORDER);
			textPortServerFTP.setLayoutData(textPortServerFTPLData);
			//textPortServerFTP.setText("21");
		}
		{
			labelLoginServerFTP = new Label(this, SWT.NONE);
			GridData labelLoginServerFTPLData = new GridData();
			labelLoginServerFTP.setLayoutData(labelLoginServerFTPLData);
			labelLoginServerFTP.setText("Login serveur FTP : ");
		}
		{
			GridData textLoginServerFTPLData = new GridData();
			textLoginServerFTPLData.horizontalAlignment = GridData.FILL;
			textLoginServerFTPLData.grabExcessHorizontalSpace = true;
			textLoginServerFTP = new Text(this, SWT.BORDER);
			textLoginServerFTP.setLayoutData(textLoginServerFTPLData);
		}
		{
			labelPasswordServerFTP = new Label(this, SWT.NONE);
			GridData labelPasswordServerFTPLData = new GridData();
			labelPasswordServerFTP.setLayoutData(labelPasswordServerFTPLData);
			labelPasswordServerFTP.setText("Password serveur FTP : ");
		}
		{
			GridData textPasswordServerFTPLData = new GridData();
			textPasswordServerFTPLData.horizontalAlignment = GridData.FILL;
			textPasswordServerFTPLData.grabExcessHorizontalSpace = true;
			textPasswordServerFTP = new Text(this, SWT.BORDER);
			textPasswordServerFTP.setLayoutData(textPasswordServerFTPLData);
		}

		pack();
	}
	
	public Label getLabelNameServerFTP() {
		return labelNameServerFTP;
	}
	
	public Text getTextNameServerFTP() {
		return textNameServerFTP;
	}
	
	public Text getTextPortServerFTP() {
		return textPortServerFTP;
	}
	
	public Text getTextLoginServerFTP() {
		return textLoginServerFTP;
	}
	
	public Text getTextPasswordServerFTP() {
		return textPasswordServerFTP;
	}

}

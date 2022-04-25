package fr.legrain.libLgrMail.ecran;
import com.cloudgarden.resource.SWTResourceManager;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.SWT;


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
public class SwtSendEmail extends org.eclipse.swt.widgets.Composite {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
	
	private Composite comositeButtton;
	private Composite compositeAttach;
	private Composite compositeTo;
	private Group gpAttach;
	private Text textFrom;
	private Label labelFrom;
	private Composite compositeForm;
	private Button btSendEmail;
	private Button btAttachEmail;

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
		SwtSendEmail inst = new SwtSendEmail(shell, SWT.NULL);
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

	public SwtSendEmail(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.numColumns = 2;
			this.setLayout(thisLayout);
			{
				GridData comositeButttonLData = new GridData();
				comositeButttonLData.grabExcessHorizontalSpace = true;
				comositeButttonLData.horizontalAlignment = GridData.FILL;
				comositeButttonLData.verticalAlignment = GridData.BEGINNING;
				comositeButttonLData.heightHint = 39;

				comositeButttonLData.horizontalSpan = 2;
				comositeButtton = new Composite(this, SWT.NONE);
				GridLayout comositeButttonLayout = new GridLayout();
				comositeButttonLayout.numColumns = 3;
				comositeButtton.setLayout(comositeButttonLayout);
				comositeButtton.setLayoutData(comositeButttonLData);
				{
					btSendEmail = new Button(comositeButtton, SWT.PUSH | SWT.CENTER);
					GridData btSendEmailLData = new GridData();
					btSendEmailLData.widthHint = 105;
					btSendEmailLData.heightHint = 31;
					btSendEmailLData.horizontalAlignment = GridData.CENTER;
					btSendEmail.setLayoutData(btSendEmailLData);
					btSendEmail.setText("Send");
				}
				{
					btAttachEmail = new Button(comositeButtton, SWT.PUSH | SWT.CENTER);
					GridData btAttachEmailLData = new GridData();
					btAttachEmailLData.widthHint = 105;
					btAttachEmailLData.heightHint = 31;
					btAttachEmailLData.horizontalAlignment = GridData.CENTER;
					btAttachEmail.setLayoutData(btAttachEmailLData);
					btAttachEmail.setText("Attach");
				}
			}
			{
				GridData compositeFormLData = new GridData();
				compositeFormLData.horizontalAlignment = GridData.CENTER;
				compositeFormLData.heightHint = 34;
				compositeFormLData.widthHint = 441;
				compositeForm = new Composite(this, SWT.NONE);
				GridLayout compositeFormLayout = new GridLayout();
				compositeFormLayout.numColumns = 2;
				compositeForm.setLayout(compositeFormLayout);
				compositeForm.setLayoutData(compositeFormLData);
				{
					labelFrom = new Label(compositeForm, SWT.NONE);
					GridData labelFromLData = new GridData();
					labelFromLData.horizontalAlignment = GridData.FILL;
					labelFrom.setLayoutData(labelFromLData);
					labelFrom.setText("From : ");
					labelFrom.setFont(SWTResourceManager.getFont("Sans", 8, 1, false, false));
				}
				{
					GridData textFromLData = new GridData();
					textFromLData.heightHint = 13;
					textFromLData.horizontalAlignment = GridData.FILL;
					textFromLData.grabExcessHorizontalSpace = true;
					textFrom = new Text(compositeForm, SWT.BORDER);
					textFrom.setLayoutData(textFromLData);
				}
			}
			{
				gpAttach = new Group(this, SWT.NONE);
				GridLayout gpAttachLayout = new GridLayout();
				gpAttachLayout.makeColumnsEqualWidth = true;
				gpAttach.setLayout(gpAttachLayout);
				GridData gpAttachLData = new GridData();
				gpAttachLData.verticalSpan = 2;
				gpAttachLData.widthHint = 267;
				gpAttachLData.horizontalAlignment = GridData.CENTER;
				gpAttachLData.verticalAlignment = GridData.FILL;
				gpAttach.setLayoutData(gpAttachLData);
				gpAttach.setText("Attachements :");
				{
					GridData compositeAttachLData = new GridData();
					compositeAttachLData.horizontalAlignment = GridData.FILL;
					compositeAttachLData.grabExcessHorizontalSpace = true;
					compositeAttachLData.verticalAlignment = GridData.FILL;
					compositeAttachLData.grabExcessVerticalSpace = true;
					compositeAttach = new Composite(gpAttach, SWT.NONE);
					GridLayout compositeAttachLayout = new GridLayout();
					compositeAttachLayout.makeColumnsEqualWidth = true;
					compositeAttach.setLayout(compositeAttachLayout);
					compositeAttach.setLayoutData(compositeAttachLData);
					compositeAttach.setBackground(SWTResourceManager.getColor(255, 255, 255));
				}
			}
			{
				GridData compositeToLData = new GridData();
				compositeToLData.widthHint = 436;
				compositeToLData.verticalAlignment = GridData.FILL;
				compositeToLData.grabExcessVerticalSpace = true;
				compositeTo = new Composite(this, SWT.NONE);
				GridLayout compositeToLayout = new GridLayout();
				compositeToLayout.makeColumnsEqualWidth = true;
				compositeTo.setLayout(compositeToLayout);
				compositeTo.setLayoutData(compositeToLData);
			}
			this.layout();
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Text getTextFrom() {
		return textFrom;
	}

}

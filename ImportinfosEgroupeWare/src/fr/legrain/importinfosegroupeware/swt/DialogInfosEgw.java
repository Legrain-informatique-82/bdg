package fr.legrain.importinfosegroupeware.swt;
import com.cloudgarden.resource.SWTResourceManager;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
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
public class DialogInfosEgw extends org.eclipse.swt.widgets.Composite {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
	
	private Label labelTitle;
	private Text textNumeroCpntact;
	private Label labelExampleIdContact;
	private Button buttonAnnuler;
	private Label labelMessage;
	private Group groupMessage;
	private Button buttonValider;
	private Label label2;
	private Label labelNumeroContact;
	private Label label1;

	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void main(String[] args) {
		showGUI();
	}
		
	/**
	* Auto-generated method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void showGUI() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		DialogInfosEgw inst = new DialogInfosEgw(shell, SWT.NULL);
		Point size = inst.getSize();
		shell.setLayout(new FillLayout());
		shell.layout();
		if(size.x == 0 && size.y == 0) {
			inst.pack();
			shell.pack();
			shell.setSize(493, 100);
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

	public void showDialog(Shell shell,DialogInfosEgw inst) {
		
		//DialogInfosEgw inst = new DialogInfosEgw(shell, SWT.NULL);
		Point size = inst.getSize();
		shell.setLayout(new FillLayout());
		shell.layout();
		if(size.x == 0 && size.y == 0) {
			inst.pack();
			shell.pack();
			shell.setSize(493, 100);
		} else {
			Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
			shell.setSize(shellBounds.width, shellBounds.height);
		}
		shell.open();
		
	}
	
	public DialogInfosEgw(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.numColumns = 4;
			this.setLayout(thisLayout);
			this.setSize(629, 210);
			{
				labelTitle = new Label(this, SWT.NONE);
				GridData labelTitleLData = new GridData();
				labelTitleLData.horizontalSpan = 4;
				labelTitleLData.verticalAlignment = GridData.BEGINNING;
				labelTitleLData.horizontalAlignment = GridData.FILL;
				labelTitleLData.heightHint = 17;
				labelTitleLData.grabExcessHorizontalSpace = true;
				labelTitle.setLayoutData(labelTitleLData);
				labelTitle.setText("SAISIR UN NUMERO DE CONTACT EGROUPEWARE");
				labelTitle.setAlignment(SWT.CENTER);
				labelTitle.setFont(SWTResourceManager.getFont("Sans", 10, 1, false, false));
			}
			{
				label1 = new Label(this, SWT.NONE);
				GridData label1LData = new GridData();
				label1LData.horizontalSpan = 4;
				label1LData.grabExcessHorizontalSpace = true;
				label1LData.horizontalAlignment = GridData.FILL;
				label1LData.verticalAlignment = GridData.BEGINNING;
				label1.setLayoutData(label1LData);
			}
			{
				labelNumeroContact = new Label(this, SWT.NONE);
				labelNumeroContact.setText("NUMERO DE CONTACT :");
				labelNumeroContact.setFont(SWTResourceManager.getFont("Sans", 9, 1, false, false));
			}
			{
				GridData textNumeroCpntactLData = new GridData();
				textNumeroCpntactLData.horizontalAlignment = GridData.FILL;
				textNumeroCpntactLData.grabExcessHorizontalSpace = true;
				textNumeroCpntact = new Text(this, SWT.BORDER);
				textNumeroCpntact.setLayoutData(textNumeroCpntactLData);
			}
			{
				labelExampleIdContact = new Label(this, SWT.NONE);
				GridData labelExampleIdContactLData = new GridData();
				labelExampleIdContactLData.horizontalSpan = 2;
				labelExampleIdContactLData.horizontalAlignment = GridData.FILL;
				labelExampleIdContactLData.grabExcessHorizontalSpace = true;
				labelExampleIdContact.setLayoutData(labelExampleIdContactLData);
				labelExampleIdContact.setText("Example : 2430");
				labelExampleIdContact.setFont(SWTResourceManager.getFont("Sans", 9, 1, false, false));
				labelExampleIdContact.setAlignment(SWT.CENTER);
			}
			{
				label2 = new Label(this, SWT.NONE);
				GridData label2LData = new GridData();
				label2LData.horizontalSpan = 4;
				label2LData.horizontalAlignment = GridData.FILL;
				label2LData.grabExcessHorizontalSpace = true;
				label2.setLayoutData(label2LData);
			}
			{
				buttonAnnuler = new Button(this, SWT.PUSH | SWT.CENTER);
				GridData buttonAnnulerLData = new GridData();
				buttonAnnulerLData.horizontalSpan = 2;
				buttonAnnulerLData.grabExcessHorizontalSpace = true;
				buttonAnnulerLData.horizontalAlignment = GridData.CENTER;
				buttonAnnuler.setLayoutData(buttonAnnulerLData);
				buttonAnnuler.setText("Annuler");
			}
			{
				buttonValider = new Button(this, SWT.PUSH | SWT.CENTER);
				GridData buttonValiderLData = new GridData();
				buttonValiderLData.horizontalSpan = 2;
				buttonValiderLData.widthHint = 57;
				buttonValiderLData.heightHint = 23;
				buttonValider.setLayoutData(buttonValiderLData);
				buttonValider.setText("Valider");
			}
			{
				groupMessage = new Group(this, SWT.NONE);
				GridLayout groupMessageLayout = new GridLayout();
				groupMessageLayout.makeColumnsEqualWidth = true;
				groupMessage.setLayout(groupMessageLayout);
				GridData groupMessageLData = new GridData();
				groupMessageLData.horizontalAlignment = GridData.FILL;
				groupMessageLData.grabExcessHorizontalSpace = true;
				groupMessageLData.horizontalSpan = 4;
				groupMessageLData.grabExcessVerticalSpace = true;
				groupMessageLData.verticalAlignment = GridData.FILL;
				groupMessage.setLayoutData(groupMessageLData);
				groupMessage.setText("Message");
				{
					GridData labelMessageLData = new GridData();
					labelMessageLData.horizontalAlignment = GridData.FILL;
					labelMessageLData.grabExcessHorizontalSpace = true;
					labelMessageLData.grabExcessVerticalSpace = true;
					labelMessageLData.verticalAlignment = GridData.FILL;
					labelMessage = new Label(groupMessage, SWT.NONE);
					labelMessage.setLayoutData(labelMessageLData);
					labelMessage.setAlignment(SWT.CENTER);
					labelMessage.setFont(SWTResourceManager.getFont("Sans", 10, 1, false, false));
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Text getTextNumeroCpntact() {
		return textNumeroCpntact;
	}

	public void setTextNumeroCpntact(Text textNumeroCpntact) {
		this.textNumeroCpntact = textNumeroCpntact;
	}

	public Button getButtonAnnuler() {
		return buttonAnnuler;
	}

	public void setButtonAnnuler(Button buttonAnnuler) {
		this.buttonAnnuler = buttonAnnuler;
	}

	public Button getButtonValider() {
		return buttonValider;
	}

	public void setButtonValider(Button buttonValider) {
		this.buttonValider = buttonValider;
	}

	public Label getLabelMessage() {
		return labelMessage;
	}

	public void setLabelMessage(Label labelMessage) {
		this.labelMessage = labelMessage;
	}

	
}

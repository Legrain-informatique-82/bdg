package fr.legrain.facture.editor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


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
public class TestEditorComposite extends org.eclipse.swt.widgets.Composite {
	private Text text1;
	private Text text3;
	private Label label1;
	private Button button2;
	private Label label3;
	private Label label2;
	private Text text2;
	private Button button1;

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
		TestEditorComposite inst = new TestEditorComposite(shell, SWT.NULL);
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

	public TestEditorComposite(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.numColumns = 3;
			this.setLayout(thisLayout);
			this.setSize(714, 216);
			{
				label1 = new Label(this, SWT.NONE);
				label1.setText("label1");
			}
			{
				text1 = new Text(this, SWT.BORDER);
				GridData text1LData = new GridData();
				text1LData.widthHint = 243;
				text1LData.heightHint = 13;
				text1LData.horizontalSpan = 2;
				text1.setLayoutData(text1LData);
				text1.setText("text1");
			}
			{
				label2 = new Label(this, SWT.NONE);
				label2.setText("label2");
			}
			{
				text2 = new Text(this, SWT.BORDER);
				GridData text2LData = new GridData();
				text2LData.widthHint = 84;
				text2LData.heightHint = 13;
				text2LData.horizontalSpan = 2;
				text2.setLayoutData(text2LData);
				text2.setText("text2");
			}
			{
				label3 = new Label(this, SWT.NONE);
				label3.setText("label3");
			}
			{
				text3 = new Text(this, SWT.BORDER);
				GridData text3LData = new GridData();
				text3LData.widthHint = 156;
				text3LData.heightHint = 13;
				text3LData.horizontalSpan = 2;
				text3.setLayoutData(text3LData);
				text3.setText("text3");
			}
			{
				button1 = new Button(this, SWT.PUSH | SWT.CENTER);
				GridData button1LData = new GridData();
				button1LData.heightHint = 51;
				button1LData.horizontalSpan = 3;
				button1LData.widthHint = 184;
				button1.setLayoutData(button1LData);
				button1.setText("button1");
			}
			{
				button2 = new Button(this, SWT.PUSH | SWT.CENTER);
				GridData button2LData = new GridData();
				button2LData.widthHint = 91;
				button2LData.heightHint = 25;
				button2LData.horizontalSpan = 3;
				button2.setLayoutData(button2LData);
				button2.setText("button2");
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Text getText1() {
		return text1;
	}
	
	public Button getButton1() {
		return button1;
	}
	
	public Text getText2() {
		return text2;
	}
	
	public Text getText3() {
		return text3;
	}
	
	public Label getLabel1() {
		return label1;
	}
	
	public Label getLabel2() {
		return label2;
	}
	
	public Label getLabel3() {
		return label3;
	}
	
	public Button getButton2() {
		return button2;
	}

}

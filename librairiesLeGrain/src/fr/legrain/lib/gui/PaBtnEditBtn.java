package fr.legrain.lib.gui;

import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.fieldassist.TextControlCreator;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
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
public class PaBtnEditBtn extends org.eclipse.swt.widgets.Composite {
	private Button btnAide;
	private Text tfGen;
	private Text edGen;
	
	private DecoratedField fieldGen;
	final private boolean decore = false;//LgrMess.isDECORE();
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
		PaBtnEditBtn inst = new PaBtnEditBtn(shell, SWT.NULL);
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

	public PaBtnEditBtn(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			this.setLayout(thisLayout);
			thisLayout.numColumns = 2;
			{
				GridData tfGenLData = new GridData();
				tfGenLData.widthHint = 194;
				tfGenLData.heightHint = 17;
				tfGen = new Text(this, SWT.NONE);
				tfGen.setLayoutData(tfGenLData);
				tfGen.setSize(194, 17);			
				if(!decore) {
					tfGen = new Text(this, SWT.BORDER);
					tfGen.setLayoutData(tfGenLData);
					tfGen.setText("CODE_BONLIV");
				} else {
					fieldGen = new DecoratedField(this, SWT.BORDER, new TextControlCreator());
					tfGen = (Text)fieldGen.getControl();
					fieldGen.getLayoutControl().setLayoutData(tfGenLData);
				}				
			}
			{
				btnAide = new Button(this, SWT.PUSH | SWT.CENTER);
				GridData btnAideLData = new GridData();
				btnAideLData.verticalAlignment = GridData.FILL;
				btnAide.setLayoutData(btnAideLData);
				btnAide.setText("A");
			}
			this.layout();
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Text getTfGen() {
		return tfGen;
	}

	public DecoratedField getFieldGen() {
		return fieldGen;
	}

}

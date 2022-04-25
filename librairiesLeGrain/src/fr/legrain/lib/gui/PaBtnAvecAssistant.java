package fr.legrain.lib.gui;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
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
public class PaBtnAvecAssistant extends org.eclipse.swt.widgets.Composite {
	private PaBtn paBtn;
	private PaBtnAssitant paBtnAssitant;

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
		PaBtnAvecAssistant inst = new PaBtnAvecAssistant(shell, SWT.NULL);
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

	public PaBtnAvecAssistant(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			this.setLayout(thisLayout);
			{
				GridData paBtnLData = new GridData();
				paBtnLData.heightHint = 35;
				paBtnLData.horizontalAlignment = GridData.FILL;
				paBtnLData.grabExcessHorizontalSpace = true;
				paBtn = new PaBtn(this, SWT.NONE);
				GridLayout paBtnLayout = new GridLayout();
				paBtnLayout.makeColumnsEqualWidth = true;
				paBtnLayout.numColumns = 7;
				paBtn.setLayout(paBtnLayout);
				paBtn.setLayoutData(paBtnLData);
			}
			{
				GridData paBtnAssitantLData = new GridData();
				paBtnAssitantLData.heightHint = 35;
				paBtnAssitantLData.horizontalAlignment = GridData.CENTER;
				paBtnAssitant = new PaBtnAssitant(this, SWT.BORDER);
				paBtnAssitant.setLayoutData(paBtnAssitantLData);
			}
			this.setTabList(new org.eclipse.swt.widgets.Control[] {paBtnAssitant, paBtn});
			this.layout();
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public PaBtn getPaBtn() {
		return paBtn;
	}
	
	public PaBtnAssitant getPaBtnAssitant() {
		return paBtnAssitant;
	}
}

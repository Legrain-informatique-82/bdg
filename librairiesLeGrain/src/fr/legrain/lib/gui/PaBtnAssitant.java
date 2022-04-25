package fr.legrain.lib.gui;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
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
public class PaBtnAssitant extends org.eclipse.swt.widgets.Composite {
	private Button btnSuivant;
	private Button btnPrecedent;

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
		PaBtnAssitant inst = new PaBtnAssitant(shell, SWT.NULL);
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

	public PaBtnAssitant(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			this.setLayout(thisLayout);
			thisLayout.numColumns = 2;
			thisLayout.makeColumnsEqualWidth = true;
			this.setSize(197, 20);
			{
				btnPrecedent = new Button(this, SWT.PUSH | SWT.CENTER);
				GridData btnEnregistrerLData = new GridData();
				btnEnregistrerLData.horizontalAlignment = GridData.FILL;
				btnEnregistrerLData.verticalAlignment = GridData.FILL;
				btnPrecedent.setLayoutData(btnEnregistrerLData);
				btnPrecedent.setText("< Précédent");
			}
			{
				btnSuivant = new Button(this, SWT.PUSH | SWT.CENTER);
				GridData btnInsererLData = new GridData();
				btnInsererLData.horizontalAlignment = GridData.FILL;
				btnInsererLData.verticalAlignment = GridData.FILL;
				btnSuivant.setLayoutData(btnInsererLData);
				btnSuivant.setText("Suivant >");
			}
			this.setTabList(new org.eclipse.swt.widgets.Control[] {btnSuivant, btnPrecedent});
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Button getBtnPrecedent() {
		return btnPrecedent;
	}
	
	public Button getBtnSuivant() {
		return btnSuivant;
	}
}

package fr.legrain.liasseFiscale.wizards;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;


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
public class CompositePageRegime extends org.eclipse.swt.widgets.Composite {
	private Group grpRegime;
	private Button btnAgricole;
	private Button btnBIC;

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
		CompositePageRegime inst = new CompositePageRegime(shell, SWT.NULL);
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

	public CompositePageRegime(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			this.setLayout(new GridLayout());
			this.setSize(444, 336);
			{
				grpRegime = new Group(this, SWT.NONE);
				GridLayout grpRegimeLayout = new GridLayout();
				grpRegimeLayout.makeColumnsEqualWidth = true;
				grpRegime.setLayout(grpRegimeLayout);
				grpRegime.setText("Régime");
				GridData grpRegimeLData = new GridData();
				grpRegimeLData.horizontalAlignment = GridData.FILL;
				grpRegimeLData.horizontalSpan = 2;
				grpRegimeLData.grabExcessHorizontalSpace = true;
				grpRegimeLData.verticalAlignment = GridData.FILL;
				grpRegime.setLayoutData(grpRegimeLData);
				{
					btnAgricole = new Button(grpRegime, SWT.RADIO | SWT.LEFT);
					btnAgricole.setText("Agricole");
				}
				{
					btnBIC = new Button(grpRegime, SWT.RADIO | SWT.LEFT);
					btnBIC.setText("BIC");
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Button getBtnAgricole() {
		return btnAgricole;
	}

	public Button getBtnBIC() {
		return btnBIC;
	}

}

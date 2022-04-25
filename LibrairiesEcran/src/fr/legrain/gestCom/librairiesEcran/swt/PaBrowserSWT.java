package fr.legrain.gestCom.librairiesEcran.swt;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;

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
public class PaBrowserSWT extends org.eclipse.swt.widgets.Composite {
	private Composite paAdresse;
	private Browser browser;
	private Composite paProgress;

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
		PaBrowserSWT inst = new PaBrowserSWT(shell, SWT.NULL);
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

	public PaBrowserSWT(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.makeColumnsEqualWidth = true;
			this.setLayout(thisLayout);
			this.setSize(620, 324);
			{
				GridData paProgressLData = new GridData();
				paProgressLData.horizontalAlignment = GridData.FILL;
				paProgressLData.grabExcessHorizontalSpace = true;
				paProgress = new Composite(this, SWT.NONE);
				GridLayout paProgressLayout = new GridLayout();
				paProgressLayout.makeColumnsEqualWidth = true;
				paProgress.setLayout(paProgressLayout);
				paProgress.setLayoutData(paProgressLData);
			}
			{
				GridData browserLData = new GridData();
				browserLData.verticalAlignment = GridData.FILL;
				browserLData.horizontalAlignment = GridData.FILL;
				browserLData.grabExcessHorizontalSpace = true;
				browserLData.grabExcessVerticalSpace = true;
				////browser = new Browser(this, SWT.NONE);
				browser = new Browser(this, SWT.BORDER);
				//browser = new Browser(this, SWT.WEBKIT);
				//browser = new Browser(this, SWT.BORDER | SWT.MOZILLA);
				browser.setLayoutData(browserLData);
			}
			{
				GridData paAdresseLData = new GridData();
				paAdresseLData.horizontalAlignment = GridData.FILL;
				paAdresseLData.grabExcessHorizontalSpace = true;
				paAdresse = new Composite(this, SWT.NONE);
				GridLayout paAdresseLayout = new GridLayout();
				paAdresseLayout.makeColumnsEqualWidth = true;
				paAdresse.setLayout(paAdresseLayout);
				paAdresse.setLayoutData(paAdresseLData);
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Composite getPaAdresse() {
		return paAdresse;
	}
	
	public Browser getBrowser() {
		return browser;
	}
	
	public Composite getPaProgress() {
		return paProgress;
	}

}

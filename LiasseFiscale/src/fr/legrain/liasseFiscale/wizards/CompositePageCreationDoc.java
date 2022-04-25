package fr.legrain.liasseFiscale.wizards;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
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
public class CompositePageCreationDoc extends org.eclipse.swt.widgets.Composite {
	private Group group2;
	private Combo listeTypeDoc;
	private Group group1;

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
		CompositePageCreationDoc inst = new CompositePageCreationDoc(shell, SWT.NULL);
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

	public CompositePageCreationDoc(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			this.setLayout(new GridLayout());
			this.setSize(444, 336);
			{
				group2 = new Group(this, SWT.NONE);
				GridLayout group2Layout = new GridLayout();
				group2Layout.makeColumnsEqualWidth = true;
				group2.setLayout(group2Layout);
				group2.setText("Type de document");
				GridData group2LData = new GridData();
				group2LData.verticalAlignment = GridData.FILL;
				group2LData.horizontalAlignment = GridData.FILL;
				group2LData.grabExcessHorizontalSpace = true;
				group2.setLayoutData(group2LData);
				{
					listeTypeDoc = new Combo(group2, SWT.READ_ONLY);
					listeTypeDoc.setText("listeTypeDoc");
					GridData combo1LData = new GridData();
					combo1LData.widthHint = 142;
					combo1LData.heightHint = 21;
					listeTypeDoc.setLayoutData(combo1LData);
				}
			}
			{
				group1 = new Group(this, SWT.NONE);
				GridLayout group1Layout = new GridLayout();
				group1Layout.makeColumnsEqualWidth = true;
				group1.setLayout(group1Layout);
				GridData group1LData = new GridData();
				group1LData.verticalAlignment = GridData.FILL;
				group1LData.horizontalAlignment = GridData.FILL;
				group1LData.grabExcessHorizontalSpace = true;
				group1LData.grabExcessVerticalSpace = true;
				group1.setLayoutData(group1LData);
				group1.setText("Informations complementaires");
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Combo getListeTypeDoc() {
		return listeTypeDoc;
	}
	
	public Group getGroup1() {
		return group1;
	}

	public Group getGroup2() {
		return group2;
	}

}

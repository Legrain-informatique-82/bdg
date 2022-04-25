package fr.legrain.facture.ecran.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

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
public class SWTPaMaitreDetail extends org.eclipse.swt.widgets.Composite {
	private SashForm sashForm;
	private Table grille;
	private Composite paDetail;

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
		SWTPaMaitreDetail inst = new SWTPaMaitreDetail(shell, SWT.NULL);
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

	public SWTPaMaitreDetail(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			this.setLayout(new GridLayout());
			this.setSize(452, 202);
			{
				sashForm = new SashForm(this, SWT.HORIZONTAL | SWT.H_SCROLL);
				GridData sashForm1LData = new GridData();
				sashForm1LData.horizontalAlignment = GridData.FILL;
				sashForm1LData.verticalAlignment = GridData.FILL;
				sashForm1LData.grabExcessVerticalSpace = true;
				sashForm1LData.grabExcessHorizontalSpace = true;
				sashForm.setLayoutData(sashForm1LData);
				sashForm.setSize(60, 30);
				{
					grille = new Table(sashForm, SWT.SINGLE
						| SWT.FULL_SELECTION
						| SWT.H_SCROLL
						| SWT.V_SCROLL
						| SWT.BORDER);
					grille.setHeaderVisible(true);
					grille.setLinesVisible(true);
				}
				{
					paDetail = new Composite(sashForm, SWT.NONE);
					GridLayout paDetailLayout = new GridLayout();
					paDetailLayout.makeColumnsEqualWidth = true;
					paDetail.setLayout(paDetailLayout);
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public SashForm getSashForm() {
		return sashForm;
	}
	
	public Table getGrille() {
		return grille;
	}
	
	public Composite getPaDetail() {
		return paDetail;
	}

	public void setPaDetail(Composite paDetail) {
		this.paDetail = paDetail;
	}

}

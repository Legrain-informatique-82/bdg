package fr.legrain.reglement.ecran;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
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
public class PaAffectationReglement extends org.eclipse.swt.widgets.Composite {
	private Table tableReglementNonAffecte;
	private Button btnAffecter;
	private Text tfMontantAffectation;
	private Label laMontantAffectation;
	private Button btnAnnuler;

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
		PaAffectationReglement inst = new PaAffectationReglement(shell, SWT.NULL);
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

	public PaAffectationReglement(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.numColumns = 2;
			this.setLayout(thisLayout);
			{
				btnAffecter = new Button(this, SWT.PUSH | SWT.CENTER);
				GridData btnAffecterLData = new GridData();
				btnAffecterLData.verticalAlignment = GridData.FILL;
				btnAffecterLData.horizontalAlignment = GridData.FILL;
				btnAffecter.setLayoutData(btnAffecterLData);
				btnAffecter.setText("Affecter");
			}
			{
				GridData tableReglementNonAffecteLData = new GridData();
				tableReglementNonAffecteLData.horizontalAlignment = GridData.FILL;
				tableReglementNonAffecteLData.grabExcessHorizontalSpace = true;
				tableReglementNonAffecteLData.verticalAlignment = GridData.FILL;
				tableReglementNonAffecteLData.verticalSpan = 4;
				tableReglementNonAffecte = new Table(this, SWT.SINGLE
						| SWT.FULL_SELECTION
						| SWT.H_SCROLL
						| SWT.V_SCROLL
						| SWT.BORDER);
				tableReglementNonAffecte.setLayoutData(tableReglementNonAffecteLData);
			}
			{
				btnAnnuler = new Button(this, SWT.PUSH | SWT.CENTER);
				GridData button1LData = new GridData();
				button1LData.verticalAlignment = GridData.BEGINNING;
				button1LData.heightHint = 28;
				button1LData.horizontalAlignment = GridData.FILL;
				btnAnnuler.setLayoutData(button1LData);
				btnAnnuler.setText("Annuler");
			}
			{
				laMontantAffectation = new Label(this, SWT.NONE);
				GridData laMontantAffectationLData = new GridData();
				laMontantAffectationLData.verticalAlignment = GridData.BEGINNING;
				laMontantAffectationLData.horizontalAlignment = GridData.FILL;
				laMontantAffectation.setLayoutData(laMontantAffectationLData);
				laMontantAffectation.setText("Montant affectation");
			}
			{
				GridData tfMontantAffectationLData = new GridData();
				tfMontantAffectationLData.verticalAlignment = GridData.BEGINNING;
				tfMontantAffectationLData.widthHint = 102;
				tfMontantAffectationLData.heightHint = 14;
				tfMontantAffectation = new Text(this, SWT.BORDER);
				tfMontantAffectation.setLayoutData(tfMontantAffectationLData);
			}
			this.layout();
			pack();
			this.setSize(715, 110);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Table getTableReglementNonAffecte() {
		return tableReglementNonAffecte;
	}

	public Button getBtnAffecter() {
		return btnAffecter;
	}
	
	public Label getLaMontantAffectation() {
		return laMontantAffectation;
	}
	
	public Text getTfMontantAffectation() {
		return tfMontantAffectation;
	}

	public Button getBtnAnnuler() {
		return btnAnnuler;
	}

}

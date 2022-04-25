package fr.legrain.liasseFiscale.wizards;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

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
public class CompositeInfosTVA extends org.eclipse.swt.widgets.Composite {
	private Spinner spinAnnee;
	private Button button1;
	private Button button2;
	private Label laAnnee;

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
//		Display display = Display.getDefault();
//		Point size = display.getSize();
//		if(size.x == 0 && size.y == 0) {
//		} else {
//			Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
//			shell.setSize(shellBounds.width, shellBounds.height);
//		}
//		while (!shell.isDisposed()) {
//			if (!display.readAndDispatch())
//				display.sleep();
//		}
	}

	public CompositeInfosTVA(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.numColumns = 2;
			this.setLayout(thisLayout);
			{
				laAnnee = new Label(this, SWT.NONE);
				laAnnee.setText("Année");
				GridData label3LData = new GridData();
				laAnnee.setLayoutData(label3LData);
			}
			{
				spinAnnee = new Spinner(this, SWT.BORDER);
				spinAnnee.setMaximum(3000);
				spinAnnee.setMinimum(1900);
				GridData spinAnneeLData = new GridData();
				spinAnnee.setLayoutData(spinAnneeLData);
			}
			{
				button1 = new Button(this, SWT.RADIO | SWT.LEFT);
				button1.setText("Annuel");
			}
			{
				button2 = new Button(this, SWT.RADIO | SWT.LEFT);
				button2.setText("trimestriel");
			}
			this.setSize(511, 483);
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Label getLaAnnee() {
		return laAnnee;
	}

	public void setLaAnnee(Label laAnnee) {
		this.laAnnee = laAnnee;
	}


	public Spinner getSpinAnnee() {
		return spinAnnee;
	}

	public void setSpinAnnee(Spinner spinAnnee) {
		this.spinAnnee = spinAnnee;
	}

}

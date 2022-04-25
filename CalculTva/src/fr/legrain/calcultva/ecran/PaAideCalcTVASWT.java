package fr.legrain.calcultva.ecran;
import fr.legrain.lib.gui.PaBtnReduit;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
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
public class PaAideCalcTVASWT extends org.eclipse.swt.widgets.Composite {
	private Label laTaux;
	private Label laMtTTC;
	private Text tfTaux;
	private PaBtnReduit paBtnReduit1;
	private Text tfMtTVA;
	private Label laMtTVA;
	private Text tfMtHT;
	private Label laMtHT;
	private Text tfMtTTC;

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
		PaAideCalcTVASWT inst = new PaAideCalcTVASWT(shell, SWT.NULL);
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

	public PaAideCalcTVASWT(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			this.setLayout(thisLayout);
			thisLayout.numColumns = 2;
			this.setSize(260, 160);
			{
				laTaux = new Label(this, SWT.NONE);
				GridData laTauxLData = new GridData();
				laTauxLData.heightHint = 13;
				laTauxLData.widthHint = 100;
				laTaux.setLayoutData(laTauxLData);
				laTaux.setText("Montant Taux");
				laTaux.setSize(100, 13);
			}
			{
				GridData tfTauxLData = new GridData();
				tfTauxLData.widthHint = 108;
				tfTauxLData.heightHint = 13;
				tfTaux = new Text(this, SWT.BORDER);
				tfTaux.setLayoutData(tfTauxLData);
				tfTaux.setSize(108, 13);
			}
			{
				laMtTTC = new Label(this, SWT.NONE);
				laMtTTC.setText("Montant TTC");
				laMtTTC.setSize(100, 13);
			}
			{
				GridData tfMtTTCLData = new GridData();
				tfMtTTCLData.widthHint = 108;
				tfMtTTCLData.heightHint = 13;
				tfMtTTC = new Text(this, SWT.BORDER);
				tfMtTTC.setLayoutData(tfMtTTCLData);
				tfMtTTC.setSize(108, 13);
			}
			{
				laMtHT = new Label(this, SWT.NONE);
				laMtHT.setText("Montant HT");
				laMtHT.setSize(100, 13);
			}
			{
				GridData tfMtHTLData = new GridData();
				tfMtHTLData.widthHint = 108;
				tfMtHTLData.heightHint = 13;
				tfMtHT = new Text(this, SWT.BORDER);
				tfMtHT.setLayoutData(tfMtHTLData);
				tfMtHT.setSize(108, 13);
			}
			{
				laMtTVA = new Label(this, SWT.NONE);
				laMtTVA.setText("Montant TVA");
				laMtTVA.setSize(100, 13);
			}
			{
				tfMtTVA = new Text(this, SWT.BORDER);
				GridData tfMtTVALData = new GridData();
				tfMtTVALData.widthHint = 108;
				tfMtTVALData.heightHint = 13;
				tfMtTVA.setLayoutData(tfMtTVALData);
				tfMtTVA.setSize(108, 13);
			}
			{
				GridData paBtnReduit1LData = new GridData();
				paBtnReduit1LData.heightHint = 33;
				paBtnReduit1LData.horizontalSpan = 2;
				paBtnReduit1LData.grabExcessHorizontalSpace = true;
				paBtnReduit1LData.horizontalAlignment = GridData.CENTER;
				paBtnReduit1LData.widthHint = 175;
				paBtnReduit1 = new PaBtnReduit(this, SWT.NONE);
				GridData btnImprimerLData = new GridData();
				btnImprimerLData.horizontalAlignment = GridData.CENTER;
				paBtnReduit1.setLayoutData(paBtnReduit1LData);
				GridData btnFermerLData = new GridData();
				btnFermerLData.widthHint = 82;
				btnFermerLData.heightHint = 23;
				paBtnReduit1.getBtnImprimer().setLayoutData(btnImprimerLData);
				paBtnReduit1.getBtnFermer().setLayoutData(btnFermerLData);
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Label getLaTaux() {
		return laTaux;
	}
	
	public Label getLaMtTTC() {
		return laMtTTC;
	}
	
	public Text getTfTaux() {
		return tfTaux;
	}
	
	public Text getTfMtTTC() {
		return tfMtTTC;
	}
	
	public Label getLaMtHT() {
		return laMtHT;
	}
	
	public Text getTfMtHT() {
		return tfMtHT;
	}
	
	public Label getLaMtTVA() {
		return laMtTVA;
	}
	
	public Text getTfMtTVA() {
		return tfMtTVA;
	}
	
	public PaBtnReduit getPaBtnReduit1() {
		return paBtnReduit1;
	}

}

package fr.legrain.document.ecran;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;


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
public class PaInfosCondPaiement extends org.eclipse.swt.widgets.Composite {
	private Text tfFIN_MOIS_C_PAIEMENT;
	private Label laLIB_C_PAIEMENT;
	private Label laCODE_C_PAIEMENT;
	private Label laREPORT_C_PAIEMENT;
	private Label laFIN_MOIS_C_PAIEMENT;
	private Text tfCODE_C_PAIEMENT;
	private Text tfLIB_C_PAIEMENT;
	private Text tfREPORT_C_PAIEMENT;
	private Button btnAppliquer;

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
		PaInfosCondPaiement inst = new PaInfosCondPaiement(shell, SWT.NULL);
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

	public PaInfosCondPaiement(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.numColumns = 5;
			this.setLayout(thisLayout);
			{
				laCODE_C_PAIEMENT = new Label(this, SWT.NONE);
				laCODE_C_PAIEMENT.setText("Code paiement");
			}
			{
				GridData tfCODE_C_PAIEMENTLData = new GridData();
				tfCODE_C_PAIEMENTLData.horizontalAlignment = GridData.FILL;
				tfCODE_C_PAIEMENT = new Text(this, SWT.BORDER);
				tfCODE_C_PAIEMENT.setLayoutData(tfCODE_C_PAIEMENTLData);
			}
			{
				laLIB_C_PAIEMENT = new Label(this, SWT.NONE);
				laLIB_C_PAIEMENT.setText("Libellé");
			}
			{
				GridData tfLIB_C_PAIEMENTLData = new GridData();
				tfLIB_C_PAIEMENTLData.horizontalAlignment = GridData.FILL;
				tfLIB_C_PAIEMENTLData.heightHint = 13;
				tfLIB_C_PAIEMENTLData.horizontalSpan = 2;
				tfLIB_C_PAIEMENT = new Text(this, SWT.BORDER);
				tfLIB_C_PAIEMENT.setLayoutData(tfLIB_C_PAIEMENTLData);
			}
			{
				laREPORT_C_PAIEMENT = new Label(this, SWT.NONE);
				laREPORT_C_PAIEMENT.setText("Report");
			}
			{
				GridData tfREPORT_C_PAIEMENTLData = new GridData();
				tfREPORT_C_PAIEMENTLData.horizontalAlignment = GridData.FILL;
				tfREPORT_C_PAIEMENT = new Text(this, SWT.BORDER);
				tfREPORT_C_PAIEMENT.setLayoutData(tfREPORT_C_PAIEMENTLData);
			}
			{
				laFIN_MOIS_C_PAIEMENT = new Label(this, SWT.NONE);
				laFIN_MOIS_C_PAIEMENT.setText("Fin de mois");
			}
			{
				GridData tfFIN_MOIS_C_PAIEMENTLData = new GridData();
				tfFIN_MOIS_C_PAIEMENTLData.horizontalAlignment = GridData.FILL;
				tfFIN_MOIS_C_PAIEMENT = new Text(this, SWT.BORDER);
				tfFIN_MOIS_C_PAIEMENT.setLayoutData(tfFIN_MOIS_C_PAIEMENTLData);
			}
			{
				btnAppliquer = new Button(this, SWT.PUSH | SWT.CENTER);
				GridData BtnRaffraichirLData = new GridData();
				BtnRaffraichirLData.horizontalAlignment = GridData.CENTER;
				BtnRaffraichirLData.verticalAlignment = GridData.FILL;
				BtnRaffraichirLData.widthHint = 132;
				btnAppliquer.setLayoutData(BtnRaffraichirLData);
				btnAppliquer.setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_REINITIALISER));
				btnAppliquer.setText("Appliquer");
			}
			this.layout();
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Text getTfFIN_MOIS_C_PAIEMENT() {
		return tfFIN_MOIS_C_PAIEMENT;
	}
	
	public Text getTfREPORT_C_PAIEMENT() {
		return tfREPORT_C_PAIEMENT;
	}
	
	public Text getTfLIB_C_PAIEMENT() {
		return tfLIB_C_PAIEMENT;
	}
	
	public Text getTfCODE_C_PAIEMENT() {
		return tfCODE_C_PAIEMENT;
	}
	
	public Label getLaFIN_MOIS_C_PAIEMENT() {
		return laFIN_MOIS_C_PAIEMENT;
	}
	
	public Label getLaREPORT_C_PAIEMENT() {
		return laREPORT_C_PAIEMENT;
	}
	
	public Label getLaLIB_C_PAIEMENT() {
		return laLIB_C_PAIEMENT;
	}
	
	public Label getLaCODE_C_PAIEMENT() {
		return laCODE_C_PAIEMENT;
	}


	public Button getBtnAppliquer() {
		return btnAppliquer;
	}

}

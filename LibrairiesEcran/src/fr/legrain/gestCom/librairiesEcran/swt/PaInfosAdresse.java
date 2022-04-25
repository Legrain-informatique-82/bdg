package fr.legrain.gestCom.librairiesEcran.swt;

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
public class PaInfosAdresse extends org.eclipse.swt.widgets.Composite {
	private Label laAdresse1;
	private Text tfAdresse2;
	private Label laAdresse2;
	private Label laAdresse3;
	private Label laCodePostal;
	private Label laVille;
	private Text tfVille;
	private Button BtnReinitialiser;
	private Text tfPays;
	private Label laPays;
	private Text tfCodePostal;
	private Text tfAdresse3;
	private Text tfAdresse1;

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
		PaInfosAdresse inst = new PaInfosAdresse(shell, SWT.NULL);
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

	public PaInfosAdresse(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			this.setLayout(thisLayout);
			thisLayout.numColumns = 3;
			{
				laAdresse1 = new Label(this, SWT.NONE);
				laAdresse1.setText("Adresse 1");
			}
			{
				tfAdresse1 = new Text(this, SWT.BORDER);
				GridData tfAdresse1LData = new GridData();
				tfAdresse1LData.widthHint = 238;
				tfAdresse1LData.heightHint = 15;
				tfAdresse1.setLayoutData(tfAdresse1LData);
				tfAdresse1.setText("Adresse1");
				tfAdresse1.setSize(238, 15);
			}
			{
				BtnReinitialiser = new Button(this, SWT.PUSH | SWT.CENTER);
				GridData BtnRaffraichirLData = new GridData();
				BtnRaffraichirLData.grabExcessHorizontalSpace = true;
				BtnRaffraichirLData.widthHint = 125;
				BtnRaffraichirLData.heightHint = 23;
				BtnReinitialiser.setLayoutData(BtnRaffraichirLData);
				BtnReinitialiser.setText("Rafaîchir");
				BtnReinitialiser.setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_REINITIALISER));
				BtnReinitialiser.setSize(125, 23);
			}
			{
				laAdresse2 = new Label(this, SWT.NONE);
				laAdresse2.setText("Adresse2");
			}
			{
				tfAdresse2 = new Text(this, SWT.BORDER);
				GridData tfAdresse2LData = new GridData();
				tfAdresse2LData.grabExcessHorizontalSpace = true;
				tfAdresse2LData.widthHint = 238;
				tfAdresse2LData.heightHint = 15;
				tfAdresse2LData.horizontalSpan = 2;
				tfAdresse2.setLayoutData(tfAdresse2LData);
				tfAdresse2.setText("Adresse2");
				tfAdresse2.setSize(238, 15);
			}
			{
				laAdresse3 = new Label(this, SWT.NONE);
				laAdresse3.setText("Adresse 3");
			}
			{
				tfAdresse3 = new Text(this, SWT.BORDER);
				GridData tfAdresse3LData = new GridData();
				tfAdresse3LData.grabExcessHorizontalSpace = true;
				tfAdresse3LData.widthHint = 238;
				tfAdresse3LData.heightHint = 15;
				tfAdresse3LData.horizontalSpan = 2;
				tfAdresse3.setLayoutData(tfAdresse3LData);
				tfAdresse3.setText("Adresse3");
				tfAdresse3.setSize(238, 15);
			}
			{
				laCodePostal = new Label(this, SWT.NONE);
				laCodePostal.setText("Code Postal");
			}
			{
				tfCodePostal = new Text(this, SWT.BORDER);
				GridData tfCodePostalLData = new GridData();
				tfCodePostalLData.grabExcessHorizontalSpace = true;
				tfCodePostalLData.widthHint = 80;
				tfCodePostalLData.heightHint = 15;
				tfCodePostalLData.horizontalSpan = 2;
				tfCodePostal.setLayoutData(tfCodePostalLData);
				tfCodePostal.setText("CodePostal");
			}
			{
				laVille = new Label(this, SWT.NONE);
				laVille.setText("Ville");
			}
			{
				tfVille = new Text(this, SWT.BORDER);
				GridData tfVilleLData = new GridData();
				tfVilleLData.grabExcessHorizontalSpace = true;
				tfVilleLData.widthHint = 155;
				tfVilleLData.heightHint = 15;
				tfVilleLData.horizontalSpan = 2;
				tfVille.setLayoutData(tfVilleLData);
				tfVille.setText("ville");
			}
			{
				laPays = new Label(this, SWT.NONE);
				laPays.setText("Pays");
			}
			{
				tfPays = new Text(this, SWT.BORDER);
				GridData tfPaysLData = new GridData();
				tfPaysLData.grabExcessHorizontalSpace = true;
				tfPaysLData.widthHint = 155;
				tfPaysLData.heightHint = 15;
				tfPaysLData.horizontalSpan = 2;
				tfPays.setLayoutData(tfPaysLData);
				tfPays.setText("pays");
			}
			this.layout();
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Label getLaAdresse1() {
		return laAdresse1;
	}
	
	public Text getTfAdresse1() {
		return tfAdresse1;
	}
	
	public Label getLaAdresse2() {
		return laAdresse2;
	}
	
	public Text getTfAdresse2() {
		return tfAdresse2;
	}
	
	public Label getLaAdresse3() {
		return laAdresse3;
	}
	
	public Text getTfAdresse3() {
		return tfAdresse3;
	}
	
	public Label getLaCodePostal() {
		return laCodePostal;
	}
	
	public Text getTfCodePostal() {
		return tfCodePostal;
	}
	
	public Label getLaVille() {
		return laVille;
	}
	
	public Label getLaPays() {
		return laPays;
	}
	
	public Text getTfPays() {
		return tfPays;
	}

	public Text getTfVille() {
		return tfVille;
	}
	
	public Button getBtnReinitialiser() {
		return BtnReinitialiser;
	}

}

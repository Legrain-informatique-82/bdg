package fr.legrain.generationdocument.facture.ecran;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import fr.legrain.lib.gui.PaBtnReduit;

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
public class PaSelectionGenerationDocument extends org.eclipse.swt.widgets.Composite {
	private PaBtnReduit paBtn1;
	private Composite composite1;
	private Group group1;
	private Text tfCodeFacture;
	private Label laCodeFacture;
	private Button btnEditeur;
	private Text tfCODE_TIERS;
	private Label laCODE_TIERS;

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
		PaSelectionGenerationDocument inst = new PaSelectionGenerationDocument(shell, SWT.NULL);
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

	public PaSelectionGenerationDocument(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			this.setLayout(thisLayout);
			thisLayout.numColumns = 4;
			thisLayout.horizontalSpacing = 4;
			this.setSize(467, 198);
			{
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.CENTER;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.grabExcessVerticalSpace = true;
				composite1 = new Composite(this, SWT.BORDER);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.makeColumnsEqualWidth = true;
				composite1.setLayout(composite1Layout);
				composite1.setLayoutData(composite1LData);
				{
					group1 = new Group(composite1, SWT.NONE);
					GridLayout group1Layout = new GridLayout();
					group1Layout.numColumns = 2;
					GridData group1LData = new GridData();
					group1LData.horizontalAlignment = GridData.CENTER;
					group1LData.grabExcessVerticalSpace = true;
					group1LData.grabExcessHorizontalSpace = true;
					group1.setLayoutData(group1LData);
					group1.setLayout(group1Layout);
					group1.setDragDetect(false);
					{
						laCodeFacture = new Label(group1, SWT.NONE);
						GridData laCodeFactureLData = new GridData();
						laCodeFactureLData.widthHint = 133;
						laCodeFactureLData.heightHint = 13;
						laCodeFacture.setLayoutData(laCodeFactureLData);
						laCodeFacture.setText("Code facture");
					}
					{
						GridData tfCodeFactureLData = new GridData();
						tfCodeFactureLData.widthHint = 130;
						tfCodeFactureLData.heightHint = 13;
						tfCodeFacture = new Text(group1, SWT.BORDER);
						tfCodeFacture.setLayoutData(tfCodeFactureLData);
						tfCodeFacture.setSize(130, 13);
					}
					{
						laCODE_TIERS = new Label(group1, SWT.NONE);
						GridData laCODE_ARTICLELData = new GridData();
						laCODE_ARTICLELData.widthHint = 135;
						laCODE_ARTICLELData.heightHint = 13;
						laCODE_TIERS.setLayoutData(laCODE_ARTICLELData);
						laCODE_TIERS.setText("Code tiers");
					}
					{
						GridData tfCODE_ARTICLELData = new GridData();
						tfCODE_ARTICLELData.widthHint = 130;
						tfCODE_ARTICLELData.heightHint = 13;
						tfCODE_TIERS = new Text(group1, SWT.BORDER);
						tfCODE_TIERS.setLayoutData(tfCODE_ARTICLELData);
					}
					{
						btnEditeur = new Button(group1, SWT.PUSH | SWT.CENTER);
						GridData btnListeLData = new GridData();
						btnListeLData.widthHint = 114;
						btnListeLData.heightHint = 23;
						btnEditeur.setLayoutData(btnListeLData);
						btnEditeur.setText("Editeur");
					}
				}
				{
					GridData paBtn1LData = new GridData();
					paBtn1LData.horizontalAlignment = GridData.CENTER;
					paBtn1LData.grabExcessHorizontalSpace = true;
					paBtn1LData.grabExcessVerticalSpace = true;
					paBtn1 = new PaBtnReduit(composite1, SWT.NONE);
					paBtn1.getBtnImprimer().setText("Valider[F3]"); 
					GridData btnFermerLData = new GridData();
					btnFermerLData.widthHint = 102;
					btnFermerLData.heightHint = 23;
					paBtn1.setLayoutData(paBtn1LData);
					paBtn1.getBtnFermer().setLayoutData(btnFermerLData);
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	
	public PaBtnReduit getPaBtn1() {
		return paBtn1;
	}
	



	public Text getTfCODE_TIERS() {
		return tfCODE_TIERS;
	}

	public Label getLaCODE_TIERS() {
		return laCODE_TIERS;
	}
	
	public Label getLaCodeFacture() {
		return laCodeFacture;
	}
	
	public Text getTfCodeFacture() {
		return tfCodeFacture;
	}

	public Button getBtnEditeur() {
		return btnEditeur;
	}
	
	public Group getGroup1(Composite parent) {
		return group1;
	}

}

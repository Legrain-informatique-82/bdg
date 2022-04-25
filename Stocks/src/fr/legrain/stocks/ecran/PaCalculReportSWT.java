package fr.legrain.stocks.ecran;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

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
public class PaCalculReportSWT extends org.eclipse.swt.widgets.Composite {
	private PaBtnReduit paBtn1;
	private Group group1;
	private DateTime tfDATEFIN;
	private Label laDATEFIN;
	private DateTime tfDATEDEB;
	private Label laDATEDEB;
	private Button cbExclusion;

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
		PaCalculReportSWT inst = new PaCalculReportSWT(shell, SWT.NULL);
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

	public PaCalculReportSWT(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			this.setLayout(thisLayout);
			thisLayout.numColumns = 4;
			thisLayout.horizontalSpacing = 4;
			this.setSize(693, 301);
			{
				GridData group1LData = new GridData();
				group1LData.horizontalAlignment = GridData.CENTER;
				group1LData.widthHint = 351;
				group1LData.grabExcessHorizontalSpace = true;
				group1LData.grabExcessVerticalSpace = true;
				group1LData.heightHint = 114;
				group1 = new Group(this, SWT.NONE);
				GridLayout group1Layout = new GridLayout();
				group1Layout.numColumns = 4;
				group1Layout.marginHeight = 4;
				group1Layout.marginTop = 8;
				group1.setLayout(group1Layout);
				group1.setLayoutData(group1LData);
				{
					laDATEDEB = new Label(group1, SWT.NONE);
					GridData laDATEDEBLData = new GridData();
					laDATEDEBLData.widthHint = 7;
					laDATEDEBLData.heightHint = 13;
					laDATEDEB.setLayoutData(laDATEDEBLData);
					laDATEDEB.setText("Date début");
					laDATEDEB.setVisible(false);
				}
				{
					tfDATEDEB = new DateTime(group1, SWT.BORDER | SWT.DROP_DOWN);
					GridData tfDATEDEBLData = new GridData();
					tfDATEDEBLData.widthHint = 10;
					tfDATEDEBLData.heightHint = 17;
					tfDATEDEB.setLayoutData(tfDATEDEBLData);
//					tfDATEDEB.setPattern("dd/MM/yyyy");
					tfDATEDEB.setVisible(false);
				}
				{
					laDATEFIN = new Label(group1, SWT.NONE);
					GridData laDATEFINLData = new GridData();
					laDATEFINLData.horizontalAlignment = GridData.FILL;
					laDATEFINLData.grabExcessHorizontalSpace = true;
					laDATEFINLData.heightHint = 13;
					laDATEFIN.setLayoutData(laDATEFINLData);
					laDATEFIN.setText("Date fin");
				}
				{
					GridData tfDATEFINLData = new GridData();
					tfDATEFINLData.horizontalAlignment = GridData.CENTER;
					tfDATEFIN = new DateTime(group1, SWT.BORDER | SWT.DROP_DOWN);
					GridData tfDATEFINLData1 = new GridData();
					tfDATEFINLData1.grabExcessHorizontalSpace = true;
					tfDATEFINLData1.widthHint = 134;
					tfDATEFINLData1.verticalAlignment = GridData.FILL;
					tfDATEFIN.setLayoutData(tfDATEFINLData1);
//					tfDATEFIN.setPattern("dd/MM/yyyy");
				}
				
				{
					cbExclusion = new Button(group1, SWT.CHECK | SWT.LEFT);
					GridData cbChoixImpressionLData = new GridData();
					cbChoixImpressionLData.horizontalSpan = 4;
					cbChoixImpressionLData.grabExcessHorizontalSpace = true;
					cbChoixImpressionLData.horizontalAlignment = GridData.FILL;
					cbExclusion.setLayoutData(cbChoixImpressionLData);
					cbExclusion.setText("Exclure Qté sans unité");
				}
				
				{
					GridData paBtn1LData = new GridData();
					paBtn1LData.horizontalAlignment = GridData.CENTER;
					paBtn1LData.grabExcessHorizontalSpace = true;
					paBtn1LData.horizontalSpan = 4;
					paBtn1 = new PaBtnReduit(group1, SWT.NONE);
					paBtn1.getBtnImprimer().setText("Calculer[F3]"); 
					GridData btnFermerLData = new GridData();
					btnFermerLData.widthHint = 102;
					btnFermerLData.heightHint = 23;
					paBtn1.getBtnFermer().setLayoutData(btnFermerLData);
					paBtn1.setLayoutData(paBtn1LData);
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	public Label getLaDATEDEB() {
		return laDATEDEB;
	}
	
	public DateTime getTfDATEDEB() {
		return tfDATEDEB;
	}
	
	public Label getLaDATEFIN() {
		return laDATEFIN;
	}
	
	public DateTime getTfDATEFIN() {
		return tfDATEFIN;
	}
	
	
	public PaBtnReduit getPaBtn1() {
		return paBtn1;
	}

	public Group getGroup1() {
		return group1;
	}

	public Button getCbExclusion() {
		return cbExclusion;
	}

}

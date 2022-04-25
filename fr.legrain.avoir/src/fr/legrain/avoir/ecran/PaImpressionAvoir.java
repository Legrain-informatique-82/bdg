package fr.legrain.avoir.ecran;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
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
public class PaImpressionAvoir extends org.eclipse.swt.widgets.Composite {
	private Label laNumDeb;
	private PaBtnReduit paBtn1;
	private Button cbReExport;
	private DateTime tfDATEFIN;
	private Label laDATEFIN;
	private DateTime tfDATEDEB;
	private Label laDATEDEB;
	private Text tfNumFin;
	private Label laNumFin;
	private Text tfNumDeb;

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
		PaImpressionAvoir inst = new PaImpressionAvoir(shell, SWT.NULL);
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

	public PaImpressionAvoir(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			this.setLayout(thisLayout);
			thisLayout.numColumns = 4;
			this.setSize(403, 117);
			{
				laNumDeb = new Label(this, SWT.NONE);
				GridData laNumDebLData = new GridData();
				laNumDebLData.heightHint = 13;
				laNumDeb.setLayoutData(laNumDebLData);
				laNumDeb.setText("De la référence");
			}
			{
				GridData tfNumDebLData = new GridData();
				tfNumDebLData.heightHint = 13;
				tfNumDebLData.horizontalAlignment = GridData.FILL;
				tfNumDeb = new Text(this, SWT.BORDER);
				tfNumDeb.setLayoutData(tfNumDebLData);
			}
			{
				laNumFin = new Label(this, SWT.NONE);
				GridData laNumFinLData = new GridData();
				laNumFinLData.horizontalAlignment = GridData.CENTER;
				laNumFin.setLayoutData(laNumFinLData);
				laNumFin.setText("à la référence");
			}
			{
				GridData tfNumFinLData = new GridData();
				tfNumFinLData.horizontalAlignment = GridData.CENTER;
				tfNumFin = new Text(this, SWT.BORDER);
			}
			{
				laDATEDEB = new Label(this, SWT.NONE);
				laDATEDEB.setText("Date début");
			}
			{
				tfDATEDEB = new DateTime(this,SWT.BORDER | SWT.DROP_DOWN );
//				tfDATEDEB.setPattern("dd/MM/yyyy");
			}
			{
				laDATEFIN = new Label(this, SWT.NONE);
				GridData laDATEFINLData = new GridData();
				laDATEFINLData.horizontalAlignment = GridData.FILL;
				laDATEFIN.setLayoutData(laDATEFINLData);
				laDATEFIN.setText("Date fin");
			}
			{
				GridData tfDATEFINLData = new GridData();
				tfDATEFINLData.horizontalAlignment = GridData.CENTER;
				tfDATEFIN = new DateTime(this, SWT.BORDER | SWT.DROP_DOWN );
//				tfDATEFIN.setPattern("dd/MM/yyyy");
			}
			{
				cbReExport = new Button(this, SWT.CHECK | SWT.LEFT);
				GridData cbReExportLData = new GridData();
				cbReExportLData.horizontalSpan = 4;
				cbReExportLData.grabExcessHorizontalSpace = true;
				cbReExportLData.heightHint = 16;
				cbReExport.setLayoutData(cbReExportLData);
				cbReExport.setText("Prévisualisation");
				cbReExport.setSelection(true);
			}
			{
				GridData paBtn1LData = new GridData();
				paBtn1LData.horizontalSpan = 4;
				paBtn1LData.heightHint = 33;
				paBtn1LData.horizontalAlignment = GridData.CENTER;
				paBtn1LData.grabExcessHorizontalSpace = true;
				paBtn1 = new PaBtnReduit(this, SWT.NONE);
				paBtn1.getBtnImprimer().setText("Imprimer [F11]");
				paBtn1.setLayoutData(paBtn1LData);
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Label getLaNumDeb() {
		return laNumDeb;
	}
	
	public Text getTfNumDeb() {
		return tfNumDeb;
	}
	
	public Label getLaNumFin() {
		return laNumFin;
	}
	
	public Text getTfNumFin() {
		return tfNumFin;
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
	
	public Button getCbReExport() {
		return cbReExport;
	}
	
	public PaBtnReduit getPaBtn1() {
		return paBtn1;
	}

}

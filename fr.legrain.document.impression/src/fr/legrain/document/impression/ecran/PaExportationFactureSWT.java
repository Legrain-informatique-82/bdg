package fr.legrain.document.impression.ecran;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
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
public class PaExportationFactureSWT extends org.eclipse.swt.widgets.Composite {
	private PaBtnReduit paBtn1;
	private Button cbReExport;
	private DateTime tfDATEFIN;
	private Label laDATEFIN;
	private DateTime tfDATEDEB;
	private Label laDATEDEB;
	private Group groupDate;
	private Text tfNumFinAvoir;
	private Label laNumFinAvoir;
	private Text tfNumDebAvoir;
	private Label laNumDebAvoir;
	private Text tfNumFin;
	private Label laNumFin;
	private Text tfNumDeb;
	private Label laNumDeb;
	private Group groupReference;
	private Composite composite1;

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
		PaExportationFactureSWT inst = new PaExportationFactureSWT(shell, SWT.NULL);
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

	public PaExportationFactureSWT(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			this.setLayout(thisLayout);
			thisLayout.numColumns = 4;
			{
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.CENTER;
				composite1LData.grabExcessVerticalSpace = true;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1 = new Composite(this, SWT.BORDER);
				GridLayout composite1Layout = new GridLayout();
				composite1.setLayout(composite1Layout);
				composite1.setLayoutData(composite1LData);
				{
					groupReference = new Group(composite1, SWT.NONE);
					GridLayout group1Layout1 = new GridLayout();
					group1Layout1.numColumns = 4;
					groupReference.setLayout(group1Layout1);
					GridData group1LData = new GridData();
					group1LData.verticalAlignment = GridData.FILL;
					group1LData.horizontalAlignment = GridData.FILL;
					group1LData.grabExcessVerticalSpace = true;
					group1LData.grabExcessHorizontalSpace = true;
					groupReference.setLayoutData(group1LData);
					groupReference.setText("Sélection par référence");
					{
						laNumDeb = new Label(groupReference, SWT.NONE);
						laNumDeb.setText("De la facture");
					}
					{
						tfNumDeb = new Text(groupReference, SWT.BORDER);
					}
					{
						laNumFin = new Label(groupReference, SWT.NONE);
						laNumFin.setText("à la facture");
						laNumFin.setAlignment(SWT.CENTER);
					}
					{
						tfNumFin = new Text(groupReference, SWT.BORDER);
					}
					{
						laNumDebAvoir = new Label(groupReference, SWT.NONE);
						laNumDebAvoir.setText("De l'avoir");
					}
					{
						tfNumDebAvoir = new Text(groupReference, SWT.BORDER);
					}
					{
						laNumFinAvoir = new Label(groupReference, SWT.NONE);
						laNumFinAvoir.setText("à l'avoir");
						laNumFinAvoir.setAlignment(SWT.CENTER);
					}
					{
						tfNumFinAvoir = new Text(groupReference, SWT.BORDER);
					}
				}
				{
					groupDate = new Group(composite1, SWT.NONE);
					GridLayout group2Layout = new GridLayout();
					group2Layout.numColumns = 4;
					groupDate.setLayout(group2Layout);
					GridData group2LData = new GridData();
					group2LData.verticalAlignment = GridData.FILL;
					group2LData.grabExcessVerticalSpace = true;
					group2LData.horizontalAlignment = GridData.FILL;
					group2LData.grabExcessHorizontalSpace = true;
					groupDate.setLayoutData(group2LData);
					groupDate.setText("Sélection par date");
					{
						laDATEDEB = new Label(groupDate, SWT.NONE);
						laDATEDEB.setText("Date début");
					}
					{
						tfDATEDEB = new DateTime(groupDate, SWT.NONE);
						GridData tfDATEDEBLData = new GridData();
						tfDATEDEBLData.widthHint = 80;
						tfDATEDEBLData.heightHint = 17;
						tfDATEDEB.setLayoutData(tfDATEDEBLData);
//						tfDATEDEB.setPattern("dd/MM/yyyy");
					}
					{
						laDATEFIN = new Label(groupDate, SWT.NONE);
						laDATEFIN.setText("Date fin");
						laDATEFIN.setAlignment(SWT.CENTER);
					}
					{
						tfDATEFIN = new DateTime(groupDate, SWT.NONE);
						GridData tfDATEFINLData = new GridData();
						tfDATEFINLData.widthHint = 80;
						tfDATEFINLData.heightHint = 17;
						tfDATEFIN.setLayoutData(tfDATEFINLData);
//						tfDATEFIN.setPattern("dd/MM/yyyy");
					}
				}
				{
					cbReExport = new Button(composite1, SWT.CHECK | SWT.LEFT);
					GridData button1LData = new GridData();
					button1LData.verticalAlignment = GridData.FILL;
					button1LData.grabExcessVerticalSpace = true;
					button1LData.horizontalAlignment = GridData.FILL;
					button1LData.grabExcessHorizontalSpace = true;
					cbReExport.setLayoutData(button1LData);
					cbReExport.setText("Ré-exporter les documents déjà exportés");
				}
				{
					GridData paBtnReduit1LData = new GridData();
					paBtnReduit1LData.verticalAlignment = GridData.BEGINNING;
					paBtnReduit1LData.grabExcessVerticalSpace = true;
					paBtnReduit1LData.horizontalAlignment = GridData.CENTER;
					paBtnReduit1LData.grabExcessHorizontalSpace = true;
					paBtn1 = new PaBtnReduit(composite1, SWT.NONE);
					GridLayout paBtnReduit1Layout = new GridLayout();
					paBtnReduit1Layout.numColumns = 2;
					paBtn1.setLayout(paBtnReduit1Layout);
					GridData btnFermerLData = new GridData();
					btnFermerLData.verticalAlignment = GridData.FILL;
					btnFermerLData.horizontalAlignment = GridData.FILL;
					btnFermerLData.grabExcessVerticalSpace = true;
					paBtn1.setLayoutData(paBtnReduit1LData);
					paBtn1.getBtnFermer().setLayoutData(btnFermerLData);
					paBtn1.getBtnImprimer().setText("Exporter");
				}
			}
			this.layout();
			pack();
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
	
	public Group getGroupReference() {
		return groupReference;
	}

	public Label getLaNumFinAvoir() {
		return laNumFinAvoir;
	}

	public Text getTfNumFinAvoir() {
		return tfNumFinAvoir;
	}

	public Label getLaNumDebAvoir() {
		return laNumDebAvoir;
	}

	public Group getGroupDate() {
		return groupDate;
	}

	public Text getTfNumDebAvoir() {
		return tfNumDebAvoir;
	}

}

package fr.legrain.generationdocumentLGR.ecran;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
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
public class PaImpressionFactureSWT extends org.eclipse.swt.widgets.Composite {
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
	private Group groupDate;
	private Group groupSelection;
	private Button cbPrintExport;
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
		PaImpressionFactureSWT inst = new PaImpressionFactureSWT(shell, SWT.NULL);
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

	public PaImpressionFactureSWT(org.eclipse.swt.widgets.Composite parent, int style) {
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
				composite1Layout.numColumns = 2;
				composite1.setLayout(composite1Layout);
				composite1.setLayoutData(composite1LData);
				{
					groupSelection = new Group(composite1, SWT.NONE);
					GridLayout groupSelectionLayout = new GridLayout();
					groupSelectionLayout.numColumns = 4;
					groupSelection.setLayout(groupSelectionLayout);
					GridData groupSelectionLData = new GridData();
					groupSelectionLData.horizontalSpan = 4;
					groupSelectionLData.verticalAlignment = GridData.FILL;
					groupSelectionLData.horizontalAlignment = GridData.FILL;
					groupSelectionLData.grabExcessVerticalSpace = true;
					groupSelectionLData.grabExcessHorizontalSpace = true;
					groupSelection.setLayoutData(groupSelectionLData);
					groupSelection.setText("Sélection par référence");
					{
						laNumDeb = new Label(groupSelection, SWT.NONE);
						GridData laNumDebLData = new GridData();
						laNumDeb.setLayoutData(laNumDebLData);
						laNumDeb.setText("De la référence");
					}
					{
						GridData tfNumDebLData = new GridData();
						tfNumDeb = new Text(groupSelection, SWT.BORDER);
						tfNumDeb.setLayoutData(tfNumDebLData);
					}
					{
						laNumFin = new Label(groupSelection, SWT.NONE);
						GridData laNumFinLData = new GridData();
						laNumFin.setLayoutData(laNumFinLData);
						laNumFin.setText("à la référence");
					}
					{
						GridData tfNumFinLData = new GridData();
						tfNumFinLData.horizontalAlignment = GridData.CENTER;
						GridData tfNumFinLData1 = new GridData();
						tfNumFin = new Text(groupSelection, SWT.BORDER);
						tfNumFin.setLayoutData(tfNumFinLData1);
					}
				}
				{
					groupDate = new Group(composite1, SWT.NONE);
					GridLayout groupDateLayout = new GridLayout();
					groupDateLayout.numColumns = 4;
					groupDate.setLayout(groupDateLayout);
					GridData groupDateLData = new GridData();
					groupDateLData.horizontalSpan = 4;
					groupDateLData.verticalAlignment = GridData.FILL;
					groupDateLData.horizontalAlignment = GridData.FILL;
					groupDateLData.grabExcessVerticalSpace = true;
					groupDateLData.grabExcessHorizontalSpace = true;
					groupDate.setLayoutData(groupDateLData);
					groupDate.setText("Sélection par date");
					{
						laDATEDEB = new Label(groupDate, SWT.NONE);
						GridData laDATEDEBLData = new GridData();
						laDATEDEB.setLayoutData(laDATEDEBLData);
						laDATEDEB.setText("Date début");
					}
					{
						tfDATEDEB = new DateTime(groupDate, SWT.BORDER | SWT.DROP_DOWN);
						GridData tfDATEDEBLData = new GridData();
						tfDATEDEBLData.grabExcessHorizontalSpace = true;
						tfDATEDEBLData.widthHint = 90;
						tfDATEDEBLData.heightHint = 17;
						tfDATEDEB.setLayoutData(tfDATEDEBLData);
//						tfDATEDEB.setPattern("dd/MM/yyyy");
					}
					{
						laDATEFIN = new Label(groupDate, SWT.NONE);
						GridData laDATEFINLData = new GridData();
						laDATEFIN.setLayoutData(laDATEFINLData);
						laDATEFIN.setText("Date fin");
					}
					{
						GridData tfDATEFINLData = new GridData();
						tfDATEFINLData.horizontalAlignment = GridData.CENTER;
						tfDATEFIN = new DateTime(groupDate, SWT.BORDER | SWT.DROP_DOWN);
						GridData tfDATEFINLData1 = new GridData();
						tfDATEFINLData1.horizontalAlignment = GridData.CENTER;
						tfDATEFINLData1.widthHint = 90;
						tfDATEFINLData1.heightHint = 17;
						tfDATEFIN.setLayoutData(tfDATEFINLData1);
//						tfDATEFIN.setPattern("dd/MM/yyyy");
					}
				}
				{
					cbReExport = new Button(composite1, SWT.CHECK | SWT.LEFT);
					cbReExport.setText("Prévisualisation");
					GridData cbReExportLData = new GridData();
					cbReExportLData.horizontalAlignment = GridData.CENTER;
					cbReExport.setLayoutData(cbReExportLData);
					cbReExport.setSelection(true);
				}
				{
					GridData cbPrintExportData = new GridData();
					cbPrintExport = new Button(composite1, SWT.CHECK | SWT.LEFT);
					cbPrintExport.setLayoutData(cbPrintExportData);
					cbPrintExport.setText("Imprimer directement");
					cbPrintExportData.horizontalAlignment = GridData.CENTER;
					cbPrintExport.setSelection(false);
				}
				GridData cbPrintExportLData = new GridData();
				cbPrintExportLData.horizontalSpan = 2;
				{
					GridData paBtn1LData = new GridData();
					paBtn1LData.grabExcessVerticalSpace = true;
					paBtn1LData.grabExcessHorizontalSpace = true;
					paBtn1LData.horizontalAlignment = GridData.CENTER;
					paBtn1LData.horizontalSpan = 2;
					paBtn1 = new PaBtnReduit(composite1, SWT.NONE);
					GridLayout paBtn1Layout = new GridLayout();
					paBtn1Layout.numColumns = 2;
					paBtn1.setLayout(paBtn1Layout);
					paBtn1.getBtnImprimer().setText("Imprimer [F11]");
					GridData btnFermerLData = new GridData();
					btnFermerLData.grabExcessVerticalSpace = true;
					btnFermerLData.grabExcessHorizontalSpace = true;
					btnFermerLData.verticalAlignment = GridData.FILL;
					btnFermerLData.horizontalAlignment = GridData.CENTER;
					paBtn1.setLayoutData(paBtn1LData);
					GridData btnImprimerLData = new GridData();
					btnImprimerLData.grabExcessVerticalSpace = true;
					btnImprimerLData.verticalAlignment = GridData.FILL;
					btnImprimerLData.grabExcessHorizontalSpace = true;
					paBtn1.getBtnFermer().setLayoutData(btnFermerLData);
					paBtn1.getBtnImprimer().setLayoutData(btnImprimerLData);
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
	

	

	public Button getCbPrintExport() {
		return cbPrintExport;
	}

	public void setCbPrintExport(Button cbPrintExport) {
	}
	
	
	public Group getGroupSelection() {
		return groupSelection;
	}
	
	public Group getGroupDate() {
		return groupDate;
	}

}

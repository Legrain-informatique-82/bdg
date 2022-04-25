package fr.legrain.archivageepicea.Projet_swt;
import com.cloudgarden.resource.SWTResourceManager;

import fr.legrain.edition.actions.ConstEdition;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;

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
public class SwtDialogReport extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private Label labelListReport;
	private Button RadioReportDefaut;
	private Group groupFileReport;
	private ScrolledComposite scrolledCompositeListReport;
	private Button buttonAnnulerPrint;
	private Button buttonValiderPrint;
	private Composite compositeButtonPrint;

	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Dialog inside a new Shell.
	*/
//	public static void main(String[] args) {
//		try {
//			Display display = Display.getDefault();
//			Shell shell = new Shell(display);
//			SwtDialogReport inst = new SwtDialogReport(shell, SWT.NULL);
//			inst.open();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public SwtDialogReport(Shell parent, int style) {
		super(parent, style);
		initGUI();
	}
	
	public void open() {
		try {
			dialogShell.setLocation(getParent().toDisplay(100, 100));
			dialogShell.open();
			Display display = dialogShell.getDisplay();
			while (!dialogShell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//public void open() {
		public void initGUI() {	
		
			Shell parent = getParent();
			/**
			 * SWT.RESIZE | SWT.CLOSE | SWT.TITLE | SWT.MAX
			 */
			//dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL );
			dialogShell = new Shell(parent, SWT.RESIZE | SWT.CLOSE | SWT.MAX | SWT.APPLICATION_MODAL );
			//dialogShell.layout(true,true);

			{
				//Register as a resource user - SWTResourceManager will
				//handle the obtaining and disposing of resources
				SWTResourceManager.registerResourceUser(dialogShell);
			}
			

			GridLayout dialogShellLayout = new GridLayout();
			dialogShellLayout.makeColumnsEqualWidth = true;
			dialogShell.setLayout(dialogShellLayout);
			dialogShell.layout();
			dialogShell.pack();			
			dialogShell.setSize(508, 597);
			dialogShell.setText(ConstEdition.TITLE_SHELL);
			{
				labelListReport = new Label(dialogShell, SWT.NONE);
				GridData labelListReportLData = new GridData();
				labelListReportLData.horizontalAlignment = GridData.FILL;
				labelListReportLData.grabExcessHorizontalSpace = true;
				labelListReport.setLayoutData(labelListReportLData);
				labelListReport.setText("Listes Fichiers Editions");
				labelListReport.setFont(SWTResourceManager.getFont("Tahoma", 12, 1, false, false));
				labelListReport.setAlignment(SWT.CENTER);
			}
			{
				GridData scrolledCompositeListReportLData = new GridData();
				scrolledCompositeListReportLData.grabExcessHorizontalSpace = true;
				scrolledCompositeListReportLData.horizontalAlignment = GridData.FILL;
				scrolledCompositeListReportLData.verticalAlignment = GridData.FILL;
				scrolledCompositeListReportLData.grabExcessVerticalSpace = true;
				scrolledCompositeListReport = new ScrolledComposite(dialogShell, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
				GridLayout scrolledCompositeListReportLayout1 = new GridLayout();
				scrolledCompositeListReportLayout1.makeColumnsEqualWidth = true;
				scrolledCompositeListReport.setLayout(scrolledCompositeListReportLayout1);
				GridLayout scrolledCompositeListReportLayout = new GridLayout();
				scrolledCompositeListReportLayout.makeColumnsEqualWidth = true;
				//scrolledCompositeListReport.setLayout(scrolledCompositeListReportLayout);
				scrolledCompositeListReport.setLayoutData(scrolledCompositeListReportLData);
				scrolledCompositeListReport.setExpandHorizontal(true);
				scrolledCompositeListReport.setExpandVertical(true);
				scrolledCompositeListReport.setAlwaysShowScrollBars(true);
				scrolledCompositeListReport.setDragDetect(true);
				{
					groupFileReport = new Group(scrolledCompositeListReport, SWT.NONE);
					GridLayout group1Layout = new GridLayout();
					group1Layout.makeColumnsEqualWidth = true;
					groupFileReport.setLayout(group1Layout);
					scrolledCompositeListReport.setContent(groupFileReport);
					groupFileReport.setText("Editions");
					GridData groupFileReportLData = new GridData();
					groupFileReportLData.grabExcessHorizontalSpace = true;
					groupFileReportLData.horizontalAlignment = GridData.FILL;
					groupFileReportLData.verticalAlignment = GridData.FILL;
					groupFileReportLData.grabExcessVerticalSpace = true;
					groupFileReport.setLayoutData(groupFileReportLData);
					groupFileReport.setBounds(0, 0, 441, 260);
					{
						RadioReportDefaut = new Button(groupFileReport, SWT.RADIO | SWT.LEFT);
						RadioReportDefaut.setText("Edition Defaut");
						RadioReportDefaut.setSelection(true);
					}
				}
			}
			{
				compositeButtonPrint = new Composite(dialogShell, SWT.BORDER);
				GridLayout compositeButtonPrintLayout = new GridLayout();
				compositeButtonPrintLayout.makeColumnsEqualWidth = true;
				compositeButtonPrintLayout.numColumns = 2;
				GridData compositeButtonPrintLData = new GridData();
				compositeButtonPrintLData.heightHint = 45;
				compositeButtonPrintLData.horizontalAlignment = GridData.FILL;
				compositeButtonPrint.setLayoutData(compositeButtonPrintLData);
				compositeButtonPrint.setLayout(compositeButtonPrintLayout);
				{
					buttonValiderPrint = new Button(compositeButtonPrint, SWT.PUSH | SWT.CENTER);
					GridData buttonValiderPrintLData = new GridData();
					buttonValiderPrintLData.verticalAlignment = GridData.FILL;
					buttonValiderPrintLData.grabExcessVerticalSpace = true;
					buttonValiderPrintLData.horizontalAlignment = GridData.FILL;
					buttonValiderPrintLData.grabExcessHorizontalSpace = true;
					buttonValiderPrint.setLayoutData(buttonValiderPrintLData);
					buttonValiderPrint.setText("Valider Imprimer");
				}
				{
					buttonAnnulerPrint = new Button(compositeButtonPrint, SWT.PUSH | SWT.CENTER);
					GridData buttonAnnulerPrintLData = new GridData();
					buttonAnnulerPrintLData.horizontalAlignment = GridData.FILL;
					buttonAnnulerPrintLData.grabExcessHorizontalSpace = true;
					buttonAnnulerPrintLData.verticalAlignment = GridData.FILL;
					buttonAnnulerPrintLData.grabExcessVerticalSpace = true;
					buttonAnnulerPrint.setLayoutData(buttonAnnulerPrintLData);
					buttonAnnulerPrint.setText("Annuler Imprimer");
				}
			}

	}
	

	
	public Composite getCompositeButtonPrint(Composite parent) {
		return compositeButtonPrint;
	}
	
	public Button getButtonValiderPrint(Composite parent) {
		return buttonValiderPrint;
	}
	
	public Button getButtonAnnulerPrint(Composite parent) {
		return buttonAnnulerPrint;
	}

	public Shell getDialogShell() {
		return dialogShell;
	}

	public void setDialogShell(Shell dialogShell) {
		this.dialogShell = dialogShell;
	}

	public Button getButtonAnnulerPrint() {
		return buttonAnnulerPrint;
	}

	public void setButtonAnnulerPrint(Button buttonAnnulerPrint) {
		this.buttonAnnulerPrint = buttonAnnulerPrint;
	}

	public Button getButtonValiderPrint() {
		return buttonValiderPrint;
	}

	public void setButtonValiderPrint(Button buttonValiderPrint) {
		this.buttonValiderPrint = buttonValiderPrint;
	}

	public Composite getCompositeButtonPrint() {
		return compositeButtonPrint;
	}

	public void setCompositeButtonPrint(Composite compositeButtonPrint) {
		this.compositeButtonPrint = compositeButtonPrint;
	}


	
	public ScrolledComposite getScrolledCompositeListReport(Composite parent) {
		return scrolledCompositeListReport;
	}
	
	
	public Button getRadioReportDefaut(Composite parent) {
		return RadioReportDefaut;
	}

	public Button getRadioReportDefaut() {
		return RadioReportDefaut;
	}

	public void setRadioReportDefaut(Button radioReportDefaut) {
		RadioReportDefaut = radioReportDefaut;
	}

	public Group getGroupFileReport() {
		return groupFileReport;
	}

	public void setGroupFileReport(Group groupFileReport) {
		this.groupFileReport = groupFileReport;
	}

	public ScrolledComposite getScrolledCompositeListReport() {
		return scrolledCompositeListReport;
	}

	public void setScrolledCompositeListReport(
			ScrolledComposite scrolledCompositeListReport) {
		this.scrolledCompositeListReport = scrolledCompositeListReport;
	}

}

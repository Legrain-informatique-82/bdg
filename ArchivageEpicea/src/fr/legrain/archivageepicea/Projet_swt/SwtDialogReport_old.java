package fr.legrain.archivageepicea.Projet_swt;
import com.cloudgarden.resource.SWTResourceManager;

import org.eclipse.swt.layout.FillLayout;
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
import org.eclipse.swt.widgets.Text;
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
public class SwtDialogReport_old extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private Group groupFileEdition;
	private Composite compositeListFileReport;
	private ScrolledComposite scrolledListFileReport;
	private Button button1;
	private Composite composite1;
	private Label labelFrontLietReport;
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

	public SwtDialogReport_old(Shell parent, int style) {
		super(parent, style);
		initGUI();
	}

	public void initGUI() {
		
		Shell parent = getParent();
		dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

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
		dialogShell.setSize(565, 425);
		dialogShell.setText("Choix File Edition");
		{
			groupFileEdition = new Group(dialogShell, SWT.NONE);
			GridLayout groupFileEditionLayout = new GridLayout();
			groupFileEditionLayout.makeColumnsEqualWidth = true;
			groupFileEdition.setLayout(groupFileEditionLayout);
			GridData groupFileEditionLData = new GridData();
			groupFileEditionLData.horizontalAlignment = GridData.FILL;
			groupFileEditionLData.grabExcessHorizontalSpace = true;
			groupFileEditionLData.verticalAlignment = GridData.FILL;
			groupFileEditionLData.grabExcessVerticalSpace = true;
			groupFileEdition.setLayoutData(groupFileEditionLData);
			groupFileEdition.setText("Choix Edition");
			{
				labelFrontLietReport = new Label(groupFileEdition, SWT.NONE);
				GridData labelFrontLietReportLData = new GridData();
				labelFrontLietReportLData.horizontalAlignment = GridData.CENTER;
				labelFrontLietReportLData.grabExcessHorizontalSpace = true;
				labelFrontLietReportLData.verticalAlignment = GridData.BEGINNING;
				labelFrontLietReportLData.heightHint = 18;
				labelFrontLietReport.setLayoutData(labelFrontLietReportLData);
				labelFrontLietReport.setText("Listes Fichiers Editions");
				labelFrontLietReport.setFont(SWTResourceManager.getFont("Tahoma", 11, 1, false, false));
			}
			{
				GridData scrolledListFileReportLData = new GridData();
				scrolledListFileReportLData.verticalAlignment = GridData.END;
				scrolledListFileReportLData.grabExcessVerticalSpace = true;
				scrolledListFileReportLData.horizontalAlignment = GridData.END;
				scrolledListFileReport = new ScrolledComposite(groupFileEdition, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
				GridLayout scrolledListFileReportLayout = new GridLayout();
				scrolledListFileReportLayout.makeColumnsEqualWidth = true;
				scrolledListFileReport.setLayout(scrolledListFileReportLayout);
				scrolledListFileReport.setLayoutData(scrolledListFileReportLData);
				scrolledListFileReport.setExpandHorizontal(false);
				scrolledListFileReport.setExpandVertical(false);
				scrolledListFileReport.setAlwaysShowScrollBars(true);
				scrolledListFileReport.setDragDetect(false);
				{
					compositeListFileReport = new Composite(scrolledListFileReport, SWT.NONE);
					GridLayout compositeListFileReportLayout = new GridLayout();
					compositeListFileReportLayout.makeColumnsEqualWidth = true;
					compositeListFileReport.setLayout(compositeListFileReportLayout);
					GridData compositeListFileReportLData = new GridData();
					compositeListFileReportLData.grabExcessHorizontalSpace = true;
					compositeListFileReportLData.horizontalAlignment = GridData.FILL;
					compositeListFileReportLData.grabExcessVerticalSpace = true;
					compositeListFileReportLData.verticalAlignment = GridData.FILL;
					compositeListFileReport.setLayoutData(compositeListFileReportLData);
					scrolledListFileReport.setContent(compositeListFileReport);
					{
						composite1 = new Composite(compositeListFileReport, SWT.BORDER);
						GridLayout composite1Layout = new GridLayout();
						composite1Layout.makeColumnsEqualWidth = true;
						GridData composite1LData = new GridData();
						composite1LData.widthHint = 497;
						composite1LData.heightHint = 423;
						composite1.setLayoutData(composite1LData);
						composite1.setLayout(composite1Layout);
						{
							button1 = new Button(composite1, SWT.PUSH | SWT.CENTER);
							GridData button1LData = new GridData();
							button1LData.grabExcessHorizontalSpace = true;
							button1LData.grabExcessVerticalSpace = true;
							button1LData.verticalAlignment = GridData.FILL;
							button1LData.horizontalAlignment = GridData.FILL;
							button1.setLayoutData(button1LData);
							button1.setText("button1");
						}
					}
				}
			}
		}
		{
			GridData compositeButtonPrintLData = new GridData();
			compositeButtonPrintLData.grabExcessHorizontalSpace = true;
			compositeButtonPrintLData.horizontalAlignment = GridData.FILL;
			compositeButtonPrintLData.heightHint = 43;
			compositeButtonPrint = new Composite(dialogShell, SWT.BORDER);
			GridLayout compositeButtonPrintLayout = new GridLayout();
			compositeButtonPrintLayout.makeColumnsEqualWidth = true;
			compositeButtonPrintLayout.numColumns = 2;
			compositeButtonPrint.setLayout(compositeButtonPrintLayout);
			compositeButtonPrint.setLayoutData(compositeButtonPrintLData);
			{
				buttonValiderPrint = new Button(compositeButtonPrint, SWT.PUSH | SWT.CENTER);
				GridData buttonValiderPrintLData = new GridData();
				buttonValiderPrintLData.horizontalAlignment = GridData.FILL;
				buttonValiderPrintLData.grabExcessHorizontalSpace = true;
				buttonValiderPrintLData.verticalAlignment = GridData.FILL;
				buttonValiderPrintLData.grabExcessVerticalSpace = true;
				buttonValiderPrint.setLayoutData(buttonValiderPrintLData);
				buttonValiderPrint.setText("Valider Imprimer");
			}
			{
				buttonAnnulerPrint = new Button(compositeButtonPrint, SWT.PUSH | SWT.CENTER);
				GridData buttonAnnulerPrintLData = new GridData();
				buttonAnnulerPrintLData.horizontalAlignment = GridData.FILL;
				buttonAnnulerPrintLData.grabExcessVerticalSpace = true;
				buttonAnnulerPrintLData.verticalAlignment = GridData.FILL;
				buttonAnnulerPrintLData.grabExcessHorizontalSpace = true;
				buttonAnnulerPrint.setLayoutData(buttonAnnulerPrintLData);
				buttonAnnulerPrint.setText("Annuler Imprimer");
			}
		}
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
	
	public Group getGroupFileEdition(Composite parent) {
		return groupFileEdition;
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

	public Group getGroupFileEdition() {
		return groupFileEdition;
	}

	public void setGroupFileEdition(Group groupFileEdition) {
		this.groupFileEdition = groupFileEdition;
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
	public ScrolledComposite getScrolledListFileReport(Composite parent) {
		return scrolledListFileReport;
	}
	
	public Composite getCompositeListFileReport(Composite parent) {
		return compositeListFileReport;
	}

}

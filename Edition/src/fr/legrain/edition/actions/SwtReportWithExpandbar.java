package fr.legrain.edition.actions;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.resource.SWTResourceManager;

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
public class SwtReportWithExpandbar extends org.eclipse.swt.widgets.Composite {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
	
	private Label labelListReport;
	private ExpandBar expandBarEdition;
	private Text textCommentsEdition;
	private Group groupComments;
	private Button buttonRadioPPT;
	private Button buttonRadioXLS;
	private Button buttonRadioDOC;
	private Button buttonAnnulerPrint;
	private Button buttonValiderPrint;
	private Button buttonEmail;
	private Button buttonFax;
	private Composite compositeButtonPrint;
	private Button buttonRadioExtraction;
	private Button buttonRadioHTML;
	private Button buttonRadioPDF;
	private Group groupTypeFileReport;
	private ScrolledComposite scrolledCompositeListReport;
	private Group groupFileReport;

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
		SwtReportWithExpandbar inst = new SwtReportWithExpandbar(shell, SWT.NULL);
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

	public SwtReportWithExpandbar(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.makeColumnsEqualWidth = true;
			thisLayout.numColumns = 2;
			this.setLayout(thisLayout);
			this.setSize(668, 531);
			{
				labelListReport = new Label(this, SWT.NONE);
				GridData labelListReportLData = new GridData();
				labelListReportLData.grabExcessHorizontalSpace = true;
				labelListReportLData.horizontalAlignment = GridData.FILL;
				labelListReportLData.verticalAlignment = GridData.BEGINNING;
				labelListReportLData.horizontalSpan = 2;
				labelListReport.setLayoutData(labelListReportLData);
				labelListReport.setText("Liste des éditions disponibles");
				labelListReport.setAlignment(SWT.CENTER);
				labelListReport.setFont(SWTResourceManager.getFont("Tahoma", 12, 1, false, false));
			}
			{
				groupFileReport = new Group(this, SWT.NONE);
				FillLayout groupFileReportLayout = new FillLayout(org.eclipse.swt.SWT.HORIZONTAL);
				groupFileReport.setLayout(groupFileReportLayout);
				GridData groupFileReportLData = new GridData();
				groupFileReportLData.horizontalAlignment = GridData.FILL;
				groupFileReportLData.verticalAlignment = GridData.FILL;
				groupFileReportLData.grabExcessHorizontalSpace = true;
				groupFileReportLData.grabExcessVerticalSpace = true;
				groupFileReportLData.horizontalSpan = 2;
				groupFileReport.setLayoutData(groupFileReportLData);
				groupFileReport.setText("Editions");
				{
					scrolledCompositeListReport = new ScrolledComposite(groupFileReport, SWT.H_SCROLL | SWT.V_SCROLL);
					
					FillLayout scrolledCompositeListReportLayout = new FillLayout(org.eclipse.swt.SWT.HORIZONTAL);
					scrolledCompositeListReport.setLayout(scrolledCompositeListReportLayout);
					scrolledCompositeListReport.setExpandHorizontal(true);
					scrolledCompositeListReport.setExpandVertical(true);
//					scrolledCompositeListReport.setMinHeight(400);
//					scrolledCompositeListReport.setMinWidth(400);
					{
						expandBarEdition = new ExpandBar(scrolledCompositeListReport, SWT.V_SCROLL);
						scrolledCompositeListReport.setContent(expandBarEdition);
					}
				}
			}
			{
				groupComments = new Group(this, SWT.NONE);
				FillLayout groupCommentsLayout = new FillLayout(org.eclipse.swt.SWT.HORIZONTAL);
				groupComments.setLayout(groupCommentsLayout);
				GridData groupCommentsLData = new GridData();
				groupCommentsLData.verticalAlignment = GridData.BEGINNING;
				groupCommentsLData.grabExcessHorizontalSpace = true;
				groupCommentsLData.horizontalAlignment = GridData.FILL;
				groupCommentsLData.horizontalSpan = 2;
				groupCommentsLData.heightHint = 136;
				groupComments.setLayoutData(groupCommentsLData);
				groupComments.setText("Commentaire");
				{
					textCommentsEdition = new Text(groupComments, SWT.MULTI | SWT.WRAP);
					textCommentsEdition.setEditable(false);
				}
			}
			{
				groupTypeFileReport = new Group(this, SWT.NONE);
				GridLayout groupTypeFileReportLayout = new GridLayout();
				groupTypeFileReportLayout.makeColumnsEqualWidth = true;
				groupTypeFileReport.setLayout(groupTypeFileReportLayout);
				GridData groupTypeFileReportLData = new GridData();
				groupTypeFileReportLData.horizontalAlignment = GridData.FILL;
				groupTypeFileReportLData.grabExcessHorizontalSpace = true;
				groupTypeFileReportLData.verticalAlignment = GridData.BEGINNING;
				groupTypeFileReportLData.heightHint = 153;
				groupTypeFileReportLData.horizontalSpan = 2;
				groupTypeFileReport.setLayoutData(groupTypeFileReportLData);
				groupTypeFileReport.setText("Type d'édition");
				{
					buttonRadioPDF = new Button(groupTypeFileReport, SWT.RADIO | SWT.LEFT);
					buttonRadioPDF.setText("Format PDF");
					buttonRadioPDF.setSelection(true);
					buttonRadioPDF.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
				}
				{
					buttonRadioHTML = new Button(groupTypeFileReport, SWT.RADIO | SWT.LEFT);
					buttonRadioHTML.setText("Format HTML");
					buttonRadioHTML.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
				}
				{
					buttonRadioExtraction = new Button(groupTypeFileReport, SWT.RADIO | SWT.LEFT);
					buttonRadioExtraction.setText("Extraction");
					buttonRadioExtraction.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
				}
				{
					buttonRadioDOC = new Button(groupTypeFileReport, SWT.RADIO | SWT.LEFT);
					buttonRadioDOC.setText("Format Word (>= 2003)");
					buttonRadioDOC.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
				}
				{
					buttonRadioXLS = new Button(groupTypeFileReport, SWT.RADIO | SWT.LEFT);
					buttonRadioXLS.setText("Format XLS (>=2003)");
					buttonRadioXLS.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
				}
				{
//					buttonRadioPPT = new Button(groupTypeFileReport, SWT.RADIO | SWT.LEFT);
//					buttonRadioPPT.setText("Format PPT (>=2003)");
//					buttonRadioPPT.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
				}
			}
			{
				compositeButtonPrint = new Composite(this, SWT.NONE);
				GridLayout compositeButtonPrintLayout = new GridLayout();
				compositeButtonPrintLayout.numColumns = 4;
				GridData compositeButtonPrintLData = new GridData();
				compositeButtonPrintLData.heightHint = 39;
				compositeButtonPrintLData.horizontalAlignment = GridData.CENTER;
				compositeButtonPrintLData.horizontalSpan = 2;
				compositeButtonPrint.setLayoutData(compositeButtonPrintLData);
				compositeButtonPrint.setLayout(compositeButtonPrintLayout);
				{
					buttonValiderPrint = new Button(compositeButtonPrint, SWT.PUSH | SWT.CENTER);
					buttonValiderPrint.setText("Imprimer");
				}
				{
					buttonEmail = new Button(compositeButtonPrint, SWT.PUSH | SWT.CENTER);
					buttonEmail.setText("EMail");
					buttonEmail.setToolTipText("Uniquement en format PDF");
				}
				{
					buttonFax = new Button(compositeButtonPrint, SWT.PUSH | SWT.CENTER);
					buttonFax.setText("Fax");
					buttonFax.setToolTipText("Uniquement en format PDF");
				}
				{
					buttonAnnulerPrint = new Button(compositeButtonPrint, SWT.PUSH | SWT.CENTER);
					buttonAnnulerPrint.setText("Annuler");
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Group getGroupFileReport(Composite parent) {
		return groupFileReport;
	}
	
	public ScrolledComposite getScrolledCompositeListReport(Composite parent) {
		return scrolledCompositeListReport;
	}
	
	public Group getGroupTypeFileReport(Composite parent) {
		return groupTypeFileReport;
	}
	
	public Button getButtonRadioPDF(Composite parent) {
		return buttonRadioPDF;
	}
	
	public Button getButtonRadioHTML(Composite parent) {
		return buttonRadioHTML;
	}
	
	public Button getButtonRadioExtraction(Composite parent) {
		return buttonRadioExtraction;
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

	public Label getLabelListReport() {
		return labelListReport;
	}

	public void setLabelListReport(Label labelListReport) {
		this.labelListReport = labelListReport;
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

	public Button getButtonRadioExtraction() {
		return buttonRadioExtraction;
	}

	public void setButtonRadioExtraction(Button buttonRadioExtraction) {
		this.buttonRadioExtraction = buttonRadioExtraction;
	}

	public Button getButtonRadioHTML() {
		return buttonRadioHTML;
	}

	public void setButtonRadioHTML(Button buttonRadioHTML) {
		this.buttonRadioHTML = buttonRadioHTML;
	}

	public Button getButtonRadioPDF() {
		return buttonRadioPDF;
	}

	public void setButtonRadioPDF(Button buttonRadioPDF) {
		this.buttonRadioPDF = buttonRadioPDF;
	}

	public Group getGroupTypeFileReport() {
		return groupTypeFileReport;
	}

	public void setGroupTypeFileReport(Group groupTypeFileReport) {
		this.groupTypeFileReport = groupTypeFileReport;
	}

	public void setCompositeListEdition(Composite compositeListEdition) {

	}

	public ScrolledComposite getScrolledCompositeListReport() {
		return scrolledCompositeListReport;
	}

	public void setScrolledCompositeListReport(
			ScrolledComposite scrolledCompositeListReport) {
		this.scrolledCompositeListReport = scrolledCompositeListReport;
		
	}

	public Group getGroupFileReport() {
		return groupFileReport;
	}

	public void setGroupFileReport(Group groupFileReport) {
		this.groupFileReport = groupFileReport;
	}
	
	public Button getButtonRadioDOC(Composite parent) {
		return buttonRadioDOC;
	}
	
	public Button getButtonRadioXLS(Composite parent) {
		return buttonRadioXLS;
	}
	
	public Button getButtonRadioPPT(Composite parent) {
		return buttonRadioPPT;
	}

	public Button getButtonRadioPPT() {
		return buttonRadioPPT;
	}

	public void setButtonRadioPPT(Button buttonRadioPPT) {
		this.buttonRadioPPT = buttonRadioPPT;
	}

	public Button getButtonRadioXLS() {
		return buttonRadioXLS;
	}

	public void setButtonRadioXLS(Button buttonRadioXLS) {
		this.buttonRadioXLS = buttonRadioXLS;
	}

	public Button getButtonRadioDOC() {
		return buttonRadioDOC;
	}

	public void setButtonRadioDOC(Button buttonRadioDOC) {
		this.buttonRadioDOC = buttonRadioDOC;
	}
	public Group getGroupComments(Composite parent) {
		return groupComments;
	}
	
	public Text getTextCommentsEdition(Composite parent) {
		return textCommentsEdition;
	}

	public Text getTextCommentsEdition() {
		return textCommentsEdition;
	}

	public void setTextCommentsEdition(Text textCommentsEdition) {
		this.textCommentsEdition = textCommentsEdition;
	}

	public Group getGroupComments() {
		return groupComments;
	}

	public void setGroupComments(Group groupComments) {
		this.groupComments = groupComments;
	}
	
	public ExpandBar getExpandBarEdition() {
		return expandBarEdition;
	}

	public Button getButtonEmail() {
		return buttonEmail;
	}

	public Button getButtonFax() {
		return buttonFax;
	}
	

//	public Group getGroupImage(Composite parent) {
//		return groupImage;
//	}

}

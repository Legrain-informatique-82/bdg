package fr.legrain.ajouteredition.swt;

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
	private Composite cpGpImpoterEdition;
	private Group gpAddEditon;
	private ExpandBar expandBarEdition;
	private Group groupComments;
	private Button buttonAnnuler;
	private Button buttonValider;
	private Composite compositeButtonPrint;
	private ScrolledComposite scrolledCompositeListReport;
	private Group groupFileReport;
	private Label labelChoixPathEdition;
	private Text textPathEdition;
	private Button btParcouirir;
	private Label labelMessage;
	private Button btCommander;

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
				labelListReport.setText("Gestion des éditions personnelles");
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
				groupFileReport.setText("Editions personnelle");
				{
					scrolledCompositeListReport = new ScrolledComposite(groupFileReport, SWT.H_SCROLL | SWT.V_SCROLL);
					
					FillLayout scrolledCompositeListReportLayout = new FillLayout(org.eclipse.swt.SWT.HORIZONTAL);
					scrolledCompositeListReport.setLayout(scrolledCompositeListReportLayout);
					scrolledCompositeListReport.setExpandHorizontal(true);
					scrolledCompositeListReport.setExpandVertical(true);
//					scrolledCompositeListReport.setMinHeight(400);
//					scrolledCompositeListReport.setMinWidth(400);
					{
						expandBarEdition = new ExpandBar(scrolledCompositeListReport, SWT.V_SCROLL|SWT.H_SCROLL);
						scrolledCompositeListReport.setContent(expandBarEdition);
					}
				}
			}
			{
				gpAddEditon = new Group(this, SWT.NONE);
				GridLayout group1Layout = new GridLayout();
//				group1Layout.makeColumnsEqualWidth = true;
				group1Layout.numColumns = 3;
				gpAddEditon.setLayout(group1Layout);
				GridData group1LData = new GridData();
				group1LData.grabExcessHorizontalSpace = true;
				group1LData.horizontalSpan = 2;
				group1LData.heightHint = 44;
				group1LData.horizontalAlignment = GridData.FILL;
				group1LData.verticalAlignment = GridData.BEGINNING;
				gpAddEditon.setLayoutData(group1LData);
				gpAddEditon.setText("Ajouter édition personnelle");
				{
					labelChoixPathEdition = new Label(gpAddEditon, SWT.NONE);
					GridData labelChoixPathEditionLData = new GridData();
					labelChoixPathEdition.setLayoutData(labelChoixPathEditionLData);
					labelChoixPathEdition.setText("Chemin édition :");
				}
				{
					GridData textPathEditionLData = new GridData();
					textPathEditionLData.heightHint = 13;
					textPathEditionLData.grabExcessHorizontalSpace = true;
					textPathEditionLData.horizontalAlignment = GridData.FILL;
					textPathEdition = new Text(gpAddEditon, SWT.BORDER);
					textPathEdition.setLayoutData(textPathEditionLData);
					textPathEdition.setEditable(false);
				}
				{
					btParcouirir = new Button(gpAddEditon, SWT.PUSH | SWT.CENTER);
					GridData btParcouirirLData = new GridData();
					btParcouirirLData.horizontalAlignment = GridData.END;
					btParcouirir.setLayoutData(btParcouirirLData);
					btParcouirir.setText("Parcourir");
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
				groupCommentsLData.heightHint = 69;
				groupComments.setLayoutData(groupCommentsLData);
				groupComments.setText("Commander éditions personnelle");
				{
					cpGpImpoterEdition = new Composite(groupComments, SWT.NONE);
					GridLayout cpGpImpoterEditionLayout = new GridLayout();
					cpGpImpoterEditionLayout.makeColumnsEqualWidth = true;
					cpGpImpoterEdition.setLayout(cpGpImpoterEditionLayout);
					btCommander = new Button(cpGpImpoterEdition, SWT.MULTI | SWT.WRAP);
					btCommander.setText("Commander");
				}

			}

			{
				compositeButtonPrint = new Composite(this, SWT.NONE);
				GridLayout compositeButtonPrintLayout = new GridLayout();
				compositeButtonPrintLayout.makeColumnsEqualWidth = true;
				compositeButtonPrintLayout.numColumns = 2;
				GridData compositeButtonPrintLData = new GridData();
				compositeButtonPrintLData.heightHint = 39;
				compositeButtonPrintLData.grabExcessHorizontalSpace = true;
				compositeButtonPrintLData.horizontalAlignment = GridData.FILL;
				compositeButtonPrintLData.verticalAlignment = GridData.END;
				compositeButtonPrintLData.horizontalSpan = 2;
				compositeButtonPrint.setLayoutData(compositeButtonPrintLData);
				compositeButtonPrint.setLayout(compositeButtonPrintLayout);
				{
					buttonValider = new Button(compositeButtonPrint, SWT.PUSH | SWT.CENTER);
					GridData buttonValiderPrintLData = new GridData();
					buttonValiderPrintLData.horizontalAlignment = GridData.END;
					buttonValiderPrintLData.grabExcessHorizontalSpace = true;
					buttonValiderPrintLData.widthHint = 79;
					buttonValiderPrintLData.heightHint = 30;
					buttonValider.setLayoutData(buttonValiderPrintLData);
					buttonValider.setText("Ajouter");
				}
				{
					buttonAnnuler = new Button(compositeButtonPrint, SWT.PUSH | SWT.CENTER);
					GridData buttonAnnulerLData = new GridData();
					buttonAnnulerLData.widthHint = 84;
					buttonAnnulerLData.heightHint = 30;
					buttonAnnuler.setLayoutData(buttonAnnulerLData);
					buttonAnnuler.setText("Annuler");
				}
			}
			{
				labelMessage = new Label(this, SWT.NONE);
				GridData labelMessageLData = new GridData();
				labelMessageLData.horizontalSpan = 2;
				labelMessageLData.horizontalAlignment = GridData.FILL;
				labelMessageLData.grabExcessHorizontalSpace = true;
				labelMessageLData.heightHint = 19;
				labelMessage.setLayoutData(labelMessageLData);
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
	

	
	public Composite getCompositeButtonPrint(Composite parent) {
		return compositeButtonPrint;
	}
	
	public Label getLabelListReport() {
		return labelListReport;
	}

	public void setLabelListReport(Label labelListReport) {
		this.labelListReport = labelListReport;
	}
	
	
	public Composite getCompositeButtonPrint() {
		return compositeButtonPrint;
	}

	public void setCompositeButtonPrint(Composite compositeButtonPrint) {
		this.compositeButtonPrint = compositeButtonPrint;
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
	
	public Group getGroupComments(Composite parent) {
		return groupComments;
	}
	
	public void setTextCommentsEdition(Text textCommentsEdition) {
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

	public Button getButtonValider() {
		return buttonValider;
	}

	public Button getButtonAnnuler() {
		return buttonAnnuler;
	}

	public Button getBtParcouirir() {
		return btParcouirir;
	}

	public Text getTextPathEdition() {
		return textPathEdition;
	}

	public void setTextPathEdition(Text textPathEdition) {
		this.textPathEdition = textPathEdition;
	}

	public Label getLabelMessage() {
		return labelMessage;
	}

	public void setLabelMessage(Label labelMessage) {		
		this.labelMessage = labelMessage;
	}

	public Button getBtCommander() {
		return btCommander;
	}

	public void setBtCommander(Button btCommander) {
		this.btCommander = btCommander;
	}
	
}

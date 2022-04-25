package fr.legrain.ajoutereditiondemo.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
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
public class SwtInfoGestionEditionPersonnelle extends org.eclipse.swt.widgets.Composite {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
	
	private Label labelInfoEdition;
	private StyledText styledTextInfos;
	private Button btCommander;
	private Label InfoSupp;
	private Group groupFileReport;
	private Composite compositeCommander;

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
		SwtInfoGestionEditionPersonnelle inst = new SwtInfoGestionEditionPersonnelle(shell, SWT.NULL);
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

	public SwtInfoGestionEditionPersonnelle(org.eclipse.swt.widgets.Composite parent, int style) {
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
				labelInfoEdition = new Label(this, SWT.NONE);
				GridData labelListReportLData = new GridData();
				labelListReportLData.grabExcessHorizontalSpace = true;
				labelListReportLData.horizontalAlignment = GridData.FILL;
				labelListReportLData.verticalAlignment = GridData.BEGINNING;
				labelListReportLData.horizontalSpan = 2;
				labelInfoEdition.setLayoutData(labelListReportLData);
				labelInfoEdition.setText("Editions personnalisées");
				labelInfoEdition.setAlignment(SWT.CENTER);
				labelInfoEdition.setFont(SWTResourceManager.getFont("Tahoma", 12, 1, false, false));
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
				groupFileReport.setText("Infos");
				{
					styledTextInfos = new StyledText(groupFileReport, SWT.MULTI | SWT.WRAP | 
							SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
					styledTextInfos.setEditable(false);
					
				    
				}
			}
			{
				GridData compositeCommanderLData = new GridData();
				compositeCommanderLData.horizontalSpan = 2;
				compositeCommanderLData.horizontalAlignment = GridData.FILL;
				compositeCommanderLData.grabExcessHorizontalSpace = true;
				compositeCommanderLData.heightHint = 47;
				compositeCommander = new Composite(this,SWT.NONE);
				GridLayout compositeCommanderLayout = new GridLayout();
				compositeCommanderLayout.makeColumnsEqualWidth = true;
				compositeCommander.setLayout(compositeCommanderLayout);
				compositeCommander.setLayoutData(compositeCommanderLData);
				{
					btCommander = new Button(compositeCommander, SWT.PUSH | SWT.CENTER);
					btCommander.setText("Commander sur Site");
					GridData button1LData = new GridData();
					button1LData.verticalAlignment = GridData.BEGINNING;
					button1LData.horizontalAlignment = GridData.CENTER;
					button1LData.grabExcessHorizontalSpace = true;
					button1LData.widthHint = 144;
					button1LData.heightHint = 27;
					btCommander.setLayoutData(button1LData);
				}
			}
			{
				GridData InfoSuppLData = new GridData();
				InfoSuppLData.horizontalSpan = 2;
				InfoSuppLData.horizontalAlignment = GridData.FILL;
				InfoSuppLData.grabExcessHorizontalSpace = true;
				InfoSuppLData.heightHint = 27;
				InfoSupp = new Label(this,SWT.NONE);
				InfoSupp.setLayoutData(InfoSuppLData);
			}
			
		
			
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Group getGroupFileReport(Composite parent) {
		return groupFileReport;
	}
	
	public void setScrolledCompositeListReport(
			ScrolledComposite scrolledCompositeListReport) {

	}

	public Group getGroupFileReport() {
		return groupFileReport;
	}

	public void setGroupFileReport(Group groupFileReport) {
		this.groupFileReport = groupFileReport;
		
	}

	public Button getBtCommander() {
		return btCommander;
	}

	public void setBtCommander(Button btCommander) {
		this.btCommander = btCommander;
	}

	public StyledText getStyledTextInfos() {
		return styledTextInfos;
	}

	public void setStyledTextInfos(StyledText styledTextInfos) {
		this.styledTextInfos = styledTextInfos;
	}

	public Label getInfoSupp() {
		return InfoSupp;
	}

	public void setInfoSupp(Label infoSupp) {
		InfoSupp = infoSupp;
	}
	
}

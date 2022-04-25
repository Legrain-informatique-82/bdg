package fr.legrain.generationModelLettreWS.wizard;


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
public class CompositePageChoiceModelLetter extends org.eclipse.swt.widgets.Composite {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
	
	private Label labelListReport;
	private Composite compositeListModelLetter;
	private ScrolledComposite scrolledCompositeListModelLetter;
	private Group groupListPlugin;
	private Group grpImport;
	private Text tfPathFileModelLettre;
	private Button checkBoxModelLettre;
	private Button btnPathFileModelLettre;
	
	/****** new ********/
	private Group grpSaveParamPublicpostage;
	private Label labelSaveParamPublicpostage;
	private Text textNameParamPublicpostage;
	private Button btCheckBoxSave;

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
		CompositePageChoiceModelLetter inst = new CompositePageChoiceModelLetter(shell, SWT.NULL);
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

	public CompositePageChoiceModelLetter(org.eclipse.swt.widgets.Composite parent, int style) {
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
				labelListReport.setText("Liste des modèles disponibles");
				labelListReport.setAlignment(SWT.CENTER);
				labelListReport.setFont(SWTResourceManager.getFont("Tahoma", 12, 1, false, false));
			}
			{
				groupListPlugin = new Group(this, SWT.NONE);
				FillLayout groupFileReportLayout = new FillLayout(org.eclipse.swt.SWT.HORIZONTAL);
				groupListPlugin.setLayout(groupFileReportLayout);
				GridData groupFileReportLData = new GridData();
				groupFileReportLData.horizontalAlignment = GridData.FILL;
				groupFileReportLData.verticalAlignment = GridData.FILL;
				groupFileReportLData.grabExcessHorizontalSpace = true;
				groupFileReportLData.grabExcessVerticalSpace = true;
				groupFileReportLData.horizontalSpan = 2;
				groupListPlugin.setLayoutData(groupFileReportLData);
				groupListPlugin.setText("Modèles");
				groupListPlugin.setFont(SWTResourceManager.getFont("Sans", 8, 1, false, false));
				{
					scrolledCompositeListModelLetter = new ScrolledComposite(groupListPlugin, SWT.H_SCROLL | SWT.V_SCROLL);
					FillLayout scrolledCompositeListReportLayout = new FillLayout(org.eclipse.swt.SWT.HORIZONTAL);
					scrolledCompositeListModelLetter.setLayout(scrolledCompositeListReportLayout);
					scrolledCompositeListModelLetter.setExpandHorizontal(true);
					scrolledCompositeListModelLetter.setExpandVertical(true);
					scrolledCompositeListModelLetter.setMinHeight(400);
					scrolledCompositeListModelLetter.setMinWidth(400);
					{
						compositeListModelLetter = new Composite(scrolledCompositeListModelLetter, SWT.NONE);
						GridLayout compositeListEditionLayout = new GridLayout();
						compositeListEditionLayout.makeColumnsEqualWidth = true;
						//compositeListEdition.setSize(800, 800);
						compositeListModelLetter.setLayout(compositeListEditionLayout);
						scrolledCompositeListModelLetter.setContent(compositeListModelLetter);
					}
				}
			}
			{
//				grpImport = new Group(this, SWT.NONE);
//				GridLayout grpImportLayout = new GridLayout();
//				grpImportLayout.numColumns = 2;
//				grpImport.setLayout(grpImportLayout);
//				GridData grpImportLData = new GridData();
//				grpImportLData.horizontalAlignment = GridData.FILL;
//				grpImportLData.grabExcessHorizontalSpace = true;
//				grpImportLData.horizontalSpan = 2;
//				grpImportLData.verticalAlignment = GridData.FILL;
//				grpImport.setLayoutData(grpImportLData);
//				grpImport.setText("Import Model Lettre");
//				{
//					tfPathFileModelLettre = new Text(grpImport, SWT.BORDER);
//					GridData tfCheminComptaLData = new GridData();
//					tfCheminComptaLData.horizontalAlignment = GridData.FILL;
//					tfCheminComptaLData.grabExcessHorizontalSpace = true;
//					tfPathFileModelLettre.setLayoutData(tfCheminComptaLData);
//				}
//				{
//					btnPathFileModelLettre = new Button(grpImport, SWT.PUSH | SWT.CENTER);
//					GridData btnCheminComptaLData = new GridData();
//					btnCheminComptaLData.horizontalAlignment = GridData.END;
//					btnPathFileModelLettre.setLayoutData(btnCheminComptaLData);
//					btnPathFileModelLettre.setText("Parcourrir");
//				}
//				{
//					checkBoxModelLettre = new Button(grpImport, SWT.CHECK | SWT.LEFT);
//					checkBoxModelLettre.setText("Choix Import File Model Lettre");
//					GridData button1LData = new GridData();
//					checkBoxModelLettre.setLayoutData(button1LData);
//				}
			}
			{
				grpSaveParamPublicpostage = new Group(this, SWT.NONE);
				GridLayout grpSaveParamPublicpostageLayout = new GridLayout();
				grpSaveParamPublicpostageLayout.numColumns = 4;
				grpSaveParamPublicpostage.setLayout(grpSaveParamPublicpostageLayout);
				GridData grpFormatModelLabelsData = new GridData();
				grpFormatModelLabelsData.horizontalAlignment = GridData.FILL;
				grpFormatModelLabelsData.horizontalSpan = 2;
				grpFormatModelLabelsData.grabExcessHorizontalSpace = true;
				grpSaveParamPublicpostage.setLayoutData(grpFormatModelLabelsData);
				grpSaveParamPublicpostage.setText("Enregristrer les paramètres");
				grpSaveParamPublicpostage.setFont(SWTResourceManager.getFont("Sans", 8, 1, false, false));
				{
					labelSaveParamPublicpostage = new Label(grpSaveParamPublicpostage, SWT.NONE);
					labelSaveParamPublicpostage.setText("Nom Publipostage :");
					GridData labelSaveParamPublicpostageData = new GridData();
					labelSaveParamPublicpostageData.horizontalAlignment = GridData.FILL;
					labelSaveParamPublicpostage.setLayoutData(labelSaveParamPublicpostageData);
				}
				{
					textNameParamPublicpostage = new Text(grpSaveParamPublicpostage, SWT.BORDER);
					GridData textNameParamPublicpostageData = new GridData();
					textNameParamPublicpostageData.grabExcessHorizontalSpace = true;
					textNameParamPublicpostageData.horizontalSpan = 2;
					textNameParamPublicpostageData.widthHint = 123;
					textNameParamPublicpostageData.heightHint = 13;
					textNameParamPublicpostage.setLayoutData(textNameParamPublicpostageData);
				}
				{
					btCheckBoxSave = new Button(grpSaveParamPublicpostage, SWT.CHECK | SWT.LEFT);
					btCheckBoxSave.setText("Enregistrer Publicpostage");
					GridData btCheckBoxSaveLData = new GridData();
					btCheckBoxSaveLData.horizontalSpan = 2;
					btCheckBoxSave.setLayoutData(btCheckBoxSaveLData);
					btCheckBoxSave.setFont(SWTResourceManager.getFont("Sans", 8, 1, false, false));
				}

			}

			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ScrolledComposite getScrolledCompositeListReport(Composite parent) {
		return scrolledCompositeListModelLetter;
	}

	public Label getLabelListReport() {
		return labelListReport;
	}

	public void setLabelListReport(Label labelListReport) {
		this.labelListReport = labelListReport;
	}

	public ScrolledComposite getScrolledCompositeListReport() {
		return scrolledCompositeListModelLetter;
	}

	public void setScrolledCompositeListReport(
			ScrolledComposite scrolledCompositeListReport) {
		this.scrolledCompositeListModelLetter = scrolledCompositeListReport;
	}

//	public void setGroupFileReport(Group groupFileReport) {
//		this.groupFileReport = groupFileReport;
//		GridData groupCommentsLData = new GridData();
//		groupCommentsLData.horizontalAlignment = GridData.FILL;
//		groupCommentsLData.grabExcessHorizontalSpace = true;
//		groupCommentsLData.heightHint = 35;
//		groupCommentsLData.horizontalSpan = 2;
//	}



	public Text getTfPathFileModelLettre() {
		return tfPathFileModelLettre;
	}

	public void setTfPathFileModelLettre(Text tfPathFileModelLettre) {
		this.tfPathFileModelLettre = tfPathFileModelLettre;
	}

	public Button getBtnPathFileModelLettre() {
		return btnPathFileModelLettre;
	}

	public Group getGrpImport() {
		return grpImport;
	}

	public Group getGroupListPlugin() {
		return groupListPlugin;
	}

	public Button getCheckBoxModelLettre() {
		return checkBoxModelLettre;
	}

	public Composite getCompositeListModelLetter() {
		return compositeListModelLetter;
	}

	public void setCompositeListModelLetter(Composite compositeListModelLetter) {
		this.compositeListModelLetter = compositeListModelLetter;
	}

	public Button getBtCheckBoxSave() {
		return btCheckBoxSave;
	}

	public void setBtCheckBoxSave(Button btCheckBoxSave) {
		this.btCheckBoxSave = btCheckBoxSave;
	}

	public Text getTextNameParamPublicpostage() {
		return textNameParamPublicpostage;
	}

	public void setTextNameParamPublicpostage(Text textNameParamPublicpostage) {
		this.textNameParamPublicpostage = textNameParamPublicpostage;
	}
	
//	public Group getGroupImage(Composite parent) {
//		return groupImage;
//	}

}

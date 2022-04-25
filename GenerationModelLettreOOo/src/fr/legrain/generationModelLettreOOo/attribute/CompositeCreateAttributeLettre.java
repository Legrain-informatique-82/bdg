package fr.legrain.generationModelLettreOOo.attribute;
import com.cloudgarden.resource.SWTResourceManager;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.TableViewer;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.SWT;


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
public class CompositeCreateAttributeLettre extends org.eclipse.swt.widgets.Composite {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
	
	private Label labelAttribute;
	private Text textPathModelLettre;
	private Button btOpenFolderPathModelLettre;
	private Label labelPathModel;
	
	private Text textPathFileExtraction;
	private Button btOpenFolderPathFileExtraction;
	private Button btChoixCreateattributeLettre;
	private Button btChoixUpdateAttributeLettre;
	private Button btOpenFolderPathAttributeLettre;
	private Text txPathAttributeLettre;
	private Button btOpenFilePathAttributeLettre;
	private Text txPathFileAttributeLettre;
	private Composite cpBtAnnulerEtValider;
	private Group cpPathFileAttributeLettre;
	private Table tbInfoFileExtraction;
	private Group gpTableInfoFileExtraction;
	private Button btValider;
	private Button btAnnuler;
	private Group gpKeyLettreAsNameChamp;
	private Group gpUpdateattributeLettre;
	private Button btDownModelLettre;
	private Button btUpNameChamp;
	private List listNameChamp;
	private Button btUpModelLettre;
	private Button btDownNameChamp;
	private List listMotKeyModelLettre;
	private Label labelFileExtraction;
	private Label labelFileAttributeLettre;
	private Label labelNameAttributeLettre;
	private Text textNameAttributeLettre;
	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void main(String[] args) {
		showGUI();
	}
	
	/**
	* Overriding checkSubclass allows this class to extend org.eclipse.swt.widgets.Composite
	*/	
	protected void checkSubclass() {
	}
	
	/**
	* Auto-generated method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void showGUI() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		CompositeCreateAttributeLettre inst = new CompositeCreateAttributeLettre(shell, SWT.NULL);
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

	public CompositeCreateAttributeLettre(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.numColumns = 4;
			this.setLayout(thisLayout);
			{
				labelAttribute = new Label(this, SWT.BORDER);
				GridData labelAttributeLData = new GridData();
				labelAttributeLData.horizontalAlignment = GridData.FILL;
				labelAttributeLData.horizontalSpan = 4;
				labelAttributeLData.grabExcessHorizontalSpace = true;
				labelAttribute.setLayoutData(labelAttributeLData);
				labelAttribute.setText("Génération attribute lettre");
				labelAttribute.setAlignment(SWT.CENTER);
				labelAttribute.setFont(SWTResourceManager.getFont("Sans", 12, 1, false, false));
			}
			{
				GridData cpPathFileAttributeLettreLData = new GridData();
				cpPathFileAttributeLettreLData.horizontalSpan = 4;
				cpPathFileAttributeLettreLData.horizontalAlignment = GridData.FILL;
				cpPathFileAttributeLettreLData.grabExcessHorizontalSpace = true;
				cpPathFileAttributeLettreLData.verticalAlignment = GridData.BEGINNING;
				cpPathFileAttributeLettre = new Group(this, SWT.NONE);
				GridLayout cpPathFileAttributeLettreLDataLayout = new GridLayout();
				cpPathFileAttributeLettreLDataLayout.numColumns = 4;
				cpPathFileAttributeLettre.setLayout(cpPathFileAttributeLettreLDataLayout);
				cpPathFileAttributeLettre.setLayoutData(cpPathFileAttributeLettreLData);
				cpPathFileAttributeLettre.setText("Create file attribute lettre ");
				
				{
					labelPathModel = new Label(cpPathFileAttributeLettre, SWT.NONE);
					GridData labelPathModelLData = new GridData();
					labelPathModel.setLayoutData(labelPathModelLData);
					labelPathModel.setText("Choisir chemin model lettre :");
					labelPathModel.setFont(SWTResourceManager.getFont("Sans", 10, 0, false, false));
				}
				{
					GridData textPathModelLettreLData = new GridData();
					textPathModelLettreLData.horizontalSpan = 2;
					textPathModelLettreLData.grabExcessHorizontalSpace = true;
					textPathModelLettreLData.horizontalAlignment = GridData.FILL;
					textPathModelLettre = new Text(cpPathFileAttributeLettre, SWT.BORDER);
					textPathModelLettre.setLayoutData(textPathModelLettreLData);
					textPathModelLettre.setEnabled(false);
				}
				{
					btOpenFolderPathModelLettre = new Button(cpPathFileAttributeLettre, SWT.PUSH | SWT.CENTER);
					GridData btOpenFolderPathModelLettreLData = new GridData();
					btOpenFolderPathModelLettreLData.widthHint = 72;
					btOpenFolderPathModelLettreLData.heightHint = 25;
					btOpenFolderPathModelLettre.setLayoutData(btOpenFolderPathModelLettreLData);
					btOpenFolderPathModelLettre.setText("Parcourir");
				}
				{
					labelFileExtraction = new Label(cpPathFileAttributeLettre, SWT.NONE);
					GridData labelFileExtractionlLData = new GridData();
					labelFileExtraction.setLayoutData(labelFileExtractionlLData);
					labelFileExtraction.setText("Choisir chemin fichier extraction :");
					labelFileExtraction.setFont(SWTResourceManager.getFont("Sans", 10, 0, false, false));
				}
				{
					GridData textlabelFileExtractionLData = new GridData();
					textlabelFileExtractionLData.horizontalSpan = 2;
					textlabelFileExtractionLData.grabExcessHorizontalSpace = true;
					textlabelFileExtractionLData.horizontalAlignment = GridData.FILL;
					textPathFileExtraction = new Text(cpPathFileAttributeLettre, SWT.BORDER);
					textPathFileExtraction.setLayoutData(textlabelFileExtractionLData);
					textPathFileExtraction.setEnabled(false);
				}
				{
					btOpenFolderPathFileExtraction = new Button(cpPathFileAttributeLettre, SWT.PUSH | SWT.CENTER);
					GridData btOpenFolderPathFileExtractionLData = new GridData();
					btOpenFolderPathFileExtractionLData.widthHint = 72;
					btOpenFolderPathFileExtractionLData.heightHint = 25;
					btOpenFolderPathFileExtraction.setLayoutData(btOpenFolderPathFileExtractionLData);
					btOpenFolderPathFileExtraction.setText("Parcourir");
				}
				{
					labelFileAttributeLettre = new Label(cpPathFileAttributeLettre, SWT.NONE);
					GridData labelFileAttributeLettreLData = new GridData();
					labelFileAttributeLettre.setLayoutData(labelFileAttributeLettreLData);
					labelFileAttributeLettre.setText("Choisir chemin Stocker :");
					labelFileAttributeLettre.setFont(SWTResourceManager.getFont("Sans", 10, 0, false, false));
				}
				{
					GridData txPathAttributeLettreLData = new GridData();
					txPathAttributeLettreLData.grabExcessHorizontalSpace = true;
					txPathAttributeLettreLData.heightHint = 13;
					txPathAttributeLettreLData.horizontalSpan = 2;
					txPathAttributeLettreLData.horizontalAlignment = GridData.FILL;
					txPathAttributeLettre = new Text(cpPathFileAttributeLettre, SWT.BORDER);
					txPathAttributeLettre.setLayoutData(txPathAttributeLettreLData);
					txPathAttributeLettre.setEnabled(false);
				}
				{
					btOpenFolderPathAttributeLettre = new Button(cpPathFileAttributeLettre, SWT.PUSH | SWT.CENTER);
					GridData btOpenFolderPathAttributeLettreLData = new GridData();
					btOpenFolderPathAttributeLettreLData.horizontalAlignment = GridData.END;
					btOpenFolderPathAttributeLettreLData.widthHint = 72;
					btOpenFolderPathAttributeLettreLData.heightHint = 25;
					btOpenFolderPathAttributeLettre.setLayoutData(btOpenFolderPathAttributeLettreLData);
					btOpenFolderPathAttributeLettre.setText("Parcourir");
				}
				{
//					labelNameAttributeLettre = new Label(cpPathFileAttributeLettre, SWT.NONE);
//					GridData labelNameAttributeLettreLData = new GridData();
//					labelNameAttributeLettre.setLayoutData(labelNameAttributeLettreLData);
//					labelNameAttributeLettre.setText("Nom de file d'attribute :");
//					labelNameAttributeLettre.setFont(SWTResourceManager.getFont("Sans", 10, 0, false, false));
				}
				{
//					GridData textNameAttributeLettreLData = new GridData();
//					textNameAttributeLettreLData.grabExcessHorizontalSpace = true;
//					textNameAttributeLettreLData.heightHint = 13;
//					textNameAttributeLettreLData.horizontalSpan = 2;
//					textNameAttributeLettreLData.horizontalAlignment = GridData.FILL;
//					textNameAttributeLettre = new Text(cpPathFileAttributeLettre, SWT.BORDER);
//					textNameAttributeLettre.setLayoutData(textNameAttributeLettreLData);
					
				}
				{
					btChoixCreateattributeLettre = new Button(cpPathFileAttributeLettre, SWT.CHECK | SWT.LEFT);
					GridData btChoixCreateattributeLettreLData = new GridData();
					btChoixCreateattributeLettreLData.horizontalSpan = 4;
					btChoixCreateattributeLettre.setLayoutData(btChoixCreateattributeLettreLData);
					btChoixCreateattributeLettre.setText("Create attribute lettre");
				}
			}
			{
				gpUpdateattributeLettre = new Group(this, SWT.NONE);
				GridLayout gpUpdateattributeLettreLayout = new GridLayout();
				gpUpdateattributeLettreLayout.numColumns = 4;
				gpUpdateattributeLettre.setLayout(gpUpdateattributeLettreLayout);
				GridData gpUpdateattributeLettreLData = new GridData();
				gpUpdateattributeLettreLData.horizontalSpan = 4;
				gpUpdateattributeLettreLData.horizontalAlignment = GridData.FILL;
				gpUpdateattributeLettreLData.grabExcessHorizontalSpace = true;
				gpUpdateattributeLettreLData.grabExcessVerticalSpace = true;
				gpUpdateattributeLettre.setLayoutData(gpUpdateattributeLettreLData);
				gpUpdateattributeLettre.setText("groupe 2");
				{
					GridData txPathFileAttributeLettreLData = new GridData();
					txPathFileAttributeLettreLData.horizontalSpan = 3;
					txPathFileAttributeLettreLData.grabExcessHorizontalSpace = true;
					txPathFileAttributeLettreLData.horizontalAlignment = GridData.FILL;
					txPathFileAttributeLettre = new Text(gpUpdateattributeLettre, SWT.BORDER);
					txPathFileAttributeLettre.setLayoutData(txPathFileAttributeLettreLData);
					txPathFileAttributeLettre.setEnabled(false);
				}
				{
					btOpenFilePathAttributeLettre = new Button(gpUpdateattributeLettre, SWT.PUSH | SWT.CENTER);
					GridData btOpenFilePathAttributeLettreLData = new GridData();
					btOpenFilePathAttributeLettreLData.widthHint = 72;
					btOpenFilePathAttributeLettreLData.heightHint = 25;
					btOpenFilePathAttributeLettreLData.horizontalAlignment = GridData.END;
					btOpenFilePathAttributeLettre.setLayoutData(btOpenFilePathAttributeLettreLData);
					btOpenFilePathAttributeLettre.setText("Parcourir");
				}
				{
					GridData btChoixUpdateAttributeLettreLData = new GridData();
					btChoixUpdateAttributeLettreLData.horizontalSpan = 4;
					btChoixUpdateAttributeLettre = new Button(gpUpdateattributeLettre, SWT.CHECK | SWT.LEFT);
					btChoixUpdateAttributeLettre.setLayoutData(btChoixUpdateAttributeLettreLData);
					btChoixUpdateAttributeLettre.setText("Update lattre attribute");
				}
			}
		
			{
				gpKeyLettreAsNameChamp = new Group(this, SWT.NONE);
				GridLayout gpKeyLettreAsNameChampLayout = new GridLayout();
				gpKeyLettreAsNameChampLayout.numColumns = 4;
				gpKeyLettreAsNameChampLayout.makeColumnsEqualWidth = true;
				gpKeyLettreAsNameChamp.setLayout(gpKeyLettreAsNameChampLayout);
				GridData gpKeyLettreAsNameChampLData = new GridData();
				gpKeyLettreAsNameChampLData.horizontalSpan = 4;
				gpKeyLettreAsNameChampLData.horizontalAlignment = GridData.FILL;
				gpKeyLettreAsNameChampLData.verticalAlignment = GridData.FILL;
				gpKeyLettreAsNameChampLData.grabExcessHorizontalSpace = true;
				gpKeyLettreAsNameChampLData.grabExcessVerticalSpace = true;
				gpKeyLettreAsNameChamp.setLayoutData(gpKeyLettreAsNameChampLData);
				gpKeyLettreAsNameChamp.setText("qsdqsd");
				{
					GridData listMotKeyModelLettreLData = new GridData();
					listMotKeyModelLettreLData.horizontalAlignment = GridData.FILL;
					listMotKeyModelLettreLData.grabExcessHorizontalSpace = true;
					listMotKeyModelLettreLData.verticalSpan = 2;
					listMotKeyModelLettreLData.grabExcessVerticalSpace = true;
					listMotKeyModelLettreLData.verticalAlignment = GridData.FILL;
					listMotKeyModelLettre = new List(gpKeyLettreAsNameChamp, /*SWT.NONE*/SWT.V_SCROLL | SWT.BORDER);
					listMotKeyModelLettre.setLayoutData(listMotKeyModelLettreLData);
				}
				{
					btUpModelLettre = new Button(gpKeyLettreAsNameChamp, SWT.PUSH | SWT.CENTER);
					GridData btUpLData = new GridData();
					btUpLData.horizontalAlignment = GridData.CENTER;
					btUpLData.widthHint = 25;
					btUpLData.verticalAlignment = GridData.END;
					btUpModelLettre.setLayoutData(btUpLData);
				}
				{
					GridData listNameChampLData = new GridData();
					listNameChampLData.horizontalAlignment = GridData.FILL;
					listNameChampLData.verticalSpan = 2;
					listNameChampLData.verticalAlignment = GridData.FILL;
					listNameChampLData.grabExcessVerticalSpace = true;
					listNameChamp = new List(gpKeyLettreAsNameChamp, /*SWT.NONE*/SWT.V_SCROLL  | SWT.BORDER);
					listNameChamp.setLayoutData(listNameChampLData);
				}
				{
					btUpNameChamp = new Button(gpKeyLettreAsNameChamp, SWT.PUSH | SWT.CENTER);
					GridData btDownLData = new GridData();
					btDownLData.widthHint = 25;
					btDownLData.heightHint = 25;
					btDownLData.horizontalAlignment = GridData.CENTER;
					btDownLData.verticalAlignment = GridData.END;
					btUpNameChamp.setLayoutData(btDownLData);
				}
				{
					btDownModelLettre = new Button(gpKeyLettreAsNameChamp, SWT.PUSH | SWT.CENTER);
					GridData button1LData = new GridData();
					button1LData.widthHint = 25;
					button1LData.heightHint = 25;
					button1LData.horizontalAlignment = GridData.CENTER;
					button1LData.verticalAlignment = GridData.END;
					btDownModelLettre.setLayoutData(button1LData);
				}
				{
					GridData btDownNameChampLData = new GridData();
					btDownNameChampLData.widthHint = 25;
					btDownNameChampLData.heightHint = 25;
					btDownNameChampLData.horizontalAlignment = GridData.CENTER;
					btDownNameChampLData.verticalAlignment = GridData.END;
					btDownNameChamp = new Button(gpKeyLettreAsNameChamp, SWT.PUSH | SWT.CENTER);
					btDownNameChamp.setLayoutData(btDownNameChampLData);
				}
			}
			{
				gpTableInfoFileExtraction = new Group(this, SWT.NONE);
				//FillLayout group1Layout = new FillLayout(org.eclipse.swt.SWT.HORIZONTAL);
				FillLayout group1Layout = new FillLayout();
				gpTableInfoFileExtraction.setLayout(group1Layout);
				GridData group1LData = new GridData();
				group1LData.horizontalAlignment = GridData.FILL;
				group1LData.horizontalSpan = 4;
				group1LData.verticalAlignment = GridData.FILL;
				group1LData.grabExcessHorizontalSpace = true;
				group1LData.grabExcessVerticalSpace = true;
				gpTableInfoFileExtraction.setLayoutData(group1LData);
				gpTableInfoFileExtraction.setText("group1");
				{
					tbInfoFileExtraction = new Table(gpTableInfoFileExtraction, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
				}
			}
		
			{
				GridData cpBtAnnulerEtValiderLData = new GridData();
				cpBtAnnulerEtValiderLData.horizontalSpan = 4;
				cpBtAnnulerEtValiderLData.horizontalAlignment = GridData.FILL;
				cpBtAnnulerEtValiderLData.grabExcessHorizontalSpace = true;
				cpBtAnnulerEtValiderLData.verticalAlignment = GridData.BEGINNING;
				cpBtAnnulerEtValider = new Composite(this, SWT.NONE);
				GridLayout cpBtAnnulerEtValiderLayout = new GridLayout();
				cpBtAnnulerEtValiderLayout.makeColumnsEqualWidth = true;
				cpBtAnnulerEtValiderLayout.numColumns = 2;
				cpBtAnnulerEtValider.setLayout(cpBtAnnulerEtValiderLayout);
				cpBtAnnulerEtValider.setLayoutData(cpBtAnnulerEtValiderLData);
				
				{
					btValider = new Button(cpBtAnnulerEtValider, SWT.PUSH | SWT.CENTER);
					GridData btValiderLData = new GridData();
					btValiderLData.horizontalAlignment = GridData.CENTER;
					btValiderLData.widthHint = 120;
					btValiderLData.heightHint = 30;

					btValider.setLayoutData(btValiderLData);
					btValider.setText("Valider");
				}
				{
					btAnnuler = new Button(cpBtAnnulerEtValider, SWT.PUSH | SWT.CENTER);
					GridData btAnnulerLData = new GridData();
					btAnnulerLData.widthHint = 120;
					btAnnulerLData.heightHint = 30;
					btAnnulerLData.horizontalAlignment = GridData.CENTER;
					btAnnulerLData.grabExcessHorizontalSpace = true;
					btAnnuler.setLayoutData(btAnnulerLData);
					btAnnuler.setText("Annuler");
				}
		
			}
			this.layout();
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Text getTextPathModelLettre() {
		return textPathModelLettre;
	}
	
	public Button getBtOpenFolderPathModelLettre() {
		return btOpenFolderPathModelLettre;
	}
	
	public List getListMotKeyModelLettre() {
		return listMotKeyModelLettre;
	}
	
	public List getListNameChamp() {
		return listNameChamp;
	}

	public Text getTextPathFileExtraction() {
		return textPathFileExtraction;
	}

	public Button getBtOpenFolderPathFileExtraction() {
		return btOpenFolderPathFileExtraction;
	}

	public Button getBtDownModelLettre() {
		return btDownModelLettre;
	}

	public Button getBtUpNameChamp() {
		return btUpNameChamp;
	}

	public Button getBtUpModelLettre() {
		return btUpModelLettre;
	}

	public Button getBtDownNameChamp() {
		return btDownNameChamp;
	}

	public Button getBtValider() {
		return btValider;
	}

	public Button getBtAnnuler() {
		return btAnnuler;
	}
	
	public Table getTbInfoFileExtraction() {
		return tbInfoFileExtraction;
	}

	public Group getGpTableInfoFileExtraction() {
		return gpTableInfoFileExtraction;
	}
	
	public Text getTxPathAttributeLettre() {
		return txPathAttributeLettre;
	}

	public Button getBtOpenFolderPathAttributeLettre() {
		return btOpenFolderPathAttributeLettre;
	}

	public Button getBtOpenFilePathAttributeLettre() {
		return btOpenFilePathAttributeLettre;
	}

	public Text getTxPathFileAttributeLettre() {
		return txPathFileAttributeLettre;
	}
	
	public Button getBtChoixUpdateAttributeLettre() {
		return btChoixUpdateAttributeLettre;
	}
	
	public Button getBtChoixCreateattributeLettre() {
		return btChoixCreateattributeLettre;
	}

	public Group getGpUpdateattributeLettre() {
		return gpUpdateattributeLettre;
	}

	public Group getCpPathFileAttributeLettre() {
		return cpPathFileAttributeLettre;
	}

	public Text getTextNameAttributeLettre() {
		return textNameAttributeLettre;
	}

}
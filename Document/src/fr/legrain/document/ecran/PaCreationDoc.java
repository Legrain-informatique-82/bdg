package fr.legrain.document.ecran;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


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
public class PaCreationDoc extends org.eclipse.swt.widgets.Composite {
	private CCombo comboTypeDoc;
	private CCombo comboSousListe;
	private Label laSousListe;
	private Composite composite1;
	private Group group1;
	private Button btnCreer;
	private Label laTypeDocument;
	//private Label laCOMMENTAIRE;

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
		PaCreationDoc inst = new PaCreationDoc(shell, SWT.NULL);
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

	public PaCreationDoc(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.numColumns = 2;
			this.setLayout(thisLayout);
			this.setSize(839, 455);
			{
				composite1 = new Composite(this, SWT.BORDER);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.makeColumnsEqualWidth = true;
				GridData composite1LData = new GridData();
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.grabExcessVerticalSpace = true;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					group1 = new Group(composite1, SWT.NONE);
					GridLayout group1Layout = new GridLayout();
					group1Layout.numColumns = 2;
					GridData group1LData = new GridData();
					group1LData.horizontalAlignment = GridData.CENTER;
					group1LData.grabExcessHorizontalSpace = true;
					group1LData.grabExcessVerticalSpace = true;
					group1LData.widthHint = 338;
					group1LData.heightHint = 123;
					group1.setLayoutData(group1LData);
					group1.setLayout(group1Layout);
					{
						laTypeDocument = new Label(group1, SWT.NONE);
						laTypeDocument.setText("Type de document");
						GridData laTypeDocumentLData = new GridData();
						laTypeDocument.setLayoutData(laTypeDocumentLData);
					}
					{
						comboTypeDoc = new CCombo(group1, SWT.FLAT | SWT.BORDER);
						GridData comboTypeDocLData = new GridData();
						comboTypeDocLData.horizontalAlignment = GridData.FILL;
						comboTypeDocLData.grabExcessHorizontalSpace = true;
						comboTypeDoc.setLayoutData(comboTypeDocLData);
					}
					{
						laSousListe = new Label(group1, SWT.NONE);
						laSousListe.setText("label1");
					}
					{
						comboSousListe = new CCombo(group1, SWT.FLAT | SWT.BORDER);
						GridData comboSousListeLData = new GridData();
						comboSousListeLData.horizontalAlignment = GridData.FILL;
						comboSousListe.setLayoutData(comboSousListeLData);
						comboSousListe.setText("sousListe");
					}
					{
						btnCreer = new Button(group1, SWT.PUSH | SWT.CENTER);
						GridData btnCreerLData = new GridData();
						btnCreerLData.horizontalSpan = 2;
						btnCreerLData.horizontalAlignment = GridData.CENTER;
						btnCreerLData.widthHint = 113;
						btnCreerLData.heightHint = 27;
						btnCreer.setLayoutData(btnCreerLData);
						btnCreer.setText("Créer");
					}
				}
			}
			//			{
//				laCOMMENTAIRE = new Label(this, SWT.NONE);
//				laCOMMENTAIRE.setText("Commentaire");
//			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public CCombo getComboTypeDoc() {
		return comboTypeDoc;
	}
	
	public CCombo getComboSousListe() {
		return comboSousListe;
	}
	
	public Label getLaTypeDocument() {
		return laTypeDocument;
	}
	
	public Label getLaSousListe() {
		return laSousListe;
	}
	
	public Button getBtnCreer() {
		return btnCreer;
	}
	
	public Group getGroup1(Composite parent) {
		return group1;
	}
	
	public Composite getComposite1(Composite parent) {
		return composite1;
	}

}

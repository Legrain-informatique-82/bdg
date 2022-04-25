package fr.legrain.liasseFiscale.wizards;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
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
public class CompositePageCreationDossier extends org.eclipse.swt.widgets.Composite {
	private Group group1;
	private Text txtNom;
	private Text txtPrenom;
	private Label label3;
	private Label label2;
	private Text txtCodeDossier;
	private Label label1;
	private Group group2;

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
		CompositePageCreationDossier inst = new CompositePageCreationDossier(shell, SWT.NULL);
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

	public CompositePageCreationDossier(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			this.setLayout(new GridLayout());
			this.setSize(444, 336);
			{
				group1 = new Group(this, SWT.NONE);
				GridLayout group1Layout = new GridLayout();
				group1Layout.numColumns = 2;
				group1.setLayout(group1Layout);
				GridData group1LData = new GridData();
				group1LData.heightHint = 49;
				group1LData.horizontalAlignment = GridData.FILL;
				group1LData.grabExcessHorizontalSpace = true;
				group1.setLayoutData(group1LData);
				group1.setText("Information sur le dossier");
				{
					label1 = new Label(group1, SWT.NONE);
					label1.setText("Code dossier");
				}
				{
					GridData txtCodeDossierLData = new GridData();
					txtCodeDossierLData.horizontalAlignment = GridData.FILL;
					txtCodeDossierLData.grabExcessHorizontalSpace = true;
					txtCodeDossier = new Text(group1, SWT.BORDER);
					txtCodeDossier.setLayoutData(txtCodeDossierLData);
				}
			}
//			{
//				group2 = new Group(this, SWT.NONE);
//				GridLayout group2Layout = new GridLayout();
//				group2Layout.numColumns = 2;
//				group2.setLayout(group2Layout);
//				GridData group2LData = new GridData();
//				group2LData.horizontalAlignment = GridData.FILL;
//				group2LData.grabExcessHorizontalSpace = true;
//				group2LData.verticalAlignment = GridData.FILL;
//				group2LData.grabExcessVerticalSpace = true;
//				group2.setLayoutData(group2LData);
//				group2.setText("Informations complémentaires");
//				{
//					label2 = new Label(group2, SWT.NONE);
//					label2.setText("Nom");
//				}
//				{
//					GridData txtNomLData = new GridData();
//					txtNomLData.horizontalAlignment = GridData.FILL;
//					txtNomLData.grabExcessHorizontalSpace = true;
//					txtNom = new Text(group2, SWT.BORDER);
//					txtNom.setLayoutData(txtNomLData);
//				}
//				{
//					label3 = new Label(group2, SWT.NONE);
//					label3.setText("Prenom");
//				}
//				{
//					GridData txtPrenomLData = new GridData();
//					txtPrenomLData.horizontalAlignment = GridData.FILL;
//					txtPrenom = new Text(group2, SWT.BORDER);
//					txtPrenom.setLayoutData(txtPrenomLData);
//				}
//			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Label getLabel1() {
		return label1;
	}
	
	public Text getTxtNom() {
		return txtNom;
	}

	public Text getTxtCodeDossier() {
		return txtCodeDossier;
	}

	public Text getTxtPrenom() {
		return txtPrenom;
	}

}

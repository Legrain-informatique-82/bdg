package fr.legrain.liasseFiscale.wizards.fichier;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
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
public class CompositePageImportFichier extends org.eclipse.swt.widgets.Composite {
	private Group group2;
	private Label laFichierCompta;
	private Text txtFichierCompta;
	private Button btnParcourirFichierCompta;

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
		CompositePageImportFichier inst = new CompositePageImportFichier(shell, SWT.NULL);
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

	public CompositePageImportFichier(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			this.setLayout(new GridLayout());
			this.setSize(444, 336);
			{
				group2 = new Group(this, SWT.NONE);
				GridLayout group2Layout = new GridLayout();
				group2Layout.numColumns = 3;
				group2.setLayout(group2Layout);
				group2.setText("Initialisation des données");
				GridData group2LData = new GridData();
				group2LData.horizontalAlignment = GridData.FILL;
				group2LData.heightHint = 62;
				group2LData.horizontalSpan = 2;
				group2LData.grabExcessHorizontalSpace = true;
				group2.setLayoutData(group2LData);
				{
					laFichierCompta = new Label(group2, SWT.NONE);
					laFichierCompta
						.setText("Fichier provenant du programme de comptabilité");
					GridData label4LData = new GridData();
					label4LData.widthHint = 340;
					label4LData.heightHint = 13;
					label4LData.horizontalSpan = 3;
					laFichierCompta.setLayoutData(label4LData);
				}
				{
					txtFichierCompta = new Text(group2, SWT.BORDER);
					GridData text2LData = new GridData();
					text2LData.horizontalAlignment = GridData.FILL;
					text2LData.heightHint = 16;
					text2LData.horizontalSpan = 2;
					text2LData.grabExcessHorizontalSpace = true;
					txtFichierCompta.setLayoutData(text2LData);
				}
				{
					btnParcourirFichierCompta = new Button(group2, SWT.PUSH
						| SWT.CENTER);
					btnParcourirFichierCompta.setText("Parcourrir");
					GridData button2LData = new GridData();
					button2LData.horizontalAlignment = GridData.FILL;
					button2LData.heightHint = 28;
					btnParcourirFichierCompta.setLayoutData(button2LData);
					btnParcourirFichierCompta.setBounds(371, 49, 63, 28);
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Group getGroup2() {
		return group2;
	}

	public Text getTxtFichierCompta() {
		return txtFichierCompta;
	}

	public void setTxtFichierCompta(Text txtFichierCompta) {
		this.txtFichierCompta = txtFichierCompta;
	}

	public Button getBtnParcourirFichierCompta() {
		return btnParcourirFichierCompta;
	}

}

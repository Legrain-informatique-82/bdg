package fr.legrain.liasseFiscale.wizards.old;
import org.eclipse.jface.viewers.ListViewer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

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
public class CompositePageAjoutLiasse extends org.eclipse.swt.widgets.Composite {
	private List listeDossier;
	private Group group1;
	private Button btnParcourirFichierCompta;
	private Text txtFichierCompta;
	private Label laFichierCompta;
	private Spinner spinAnnee;
	private Label laAnnee;
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
//		Display display = Display.getDefault();
//		Point size = display.getSize();
//		if(size.x == 0 && size.y == 0) {
//		} else {
//			Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
//			shell.setSize(shellBounds.width, shellBounds.height);
//		}
//		while (!shell.isDisposed()) {
//			if (!display.readAndDispatch())
//				display.sleep();
//		}
	}

	public CompositePageAjoutLiasse(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.numColumns = 2;
			this.setLayout(thisLayout);
			{
				group1 = new Group(this, SWT.NONE);
				GridLayout group1Layout = new GridLayout();
				group1Layout.makeColumnsEqualWidth = true;
				group1.setLayout(group1Layout);
				group1.setText("Choix du dossier ");
				GridData group1LData = new GridData();
				group1LData.horizontalAlignment = GridData.FILL;
				group1LData.heightHint = 106;
				group1LData.grabExcessHorizontalSpace = true;
				group1LData.horizontalSpan = 2;
				group1.setLayoutData(group1LData);
				{
					listeDossier = new List(group1, SWT.H_SCROLL
						| SWT.V_SCROLL
						| SWT.BORDER);
					GridData listeDossierLData = new GridData();
					listeDossierLData.horizontalAlignment = GridData.FILL;
					listeDossierLData.grabExcessHorizontalSpace = true;
					listeDossierLData.verticalAlignment = GridData.FILL;
					listeDossierLData.grabExcessVerticalSpace = true;
					listeDossier.setLayoutData(listeDossierLData);
				}
			}
			{
				group2 = new Group(this, SWT.NONE);
				GridLayout group2Layout = new GridLayout();
				group2Layout.numColumns = 3;
				group2.setLayout(group2Layout);
				group2.setText("Initialisation de la nouvelle liasse");
				GridData group2LData = new GridData();
				group2LData.horizontalSpan = 2;
				group2LData.horizontalAlignment = GridData.FILL;
				group2LData.grabExcessHorizontalSpace = true;
				group2.setLayoutData(group2LData);
				group2.setBounds(7, 189, 392, 105);
				{
					laAnnee = new Label(group2, SWT.NONE);
					laAnnee.setText("Année");
					GridData label3LData = new GridData();
					label3LData.widthHint = 49;
					label3LData.heightHint = 15;
					laAnnee.setLayoutData(label3LData);
				}
				{
					spinAnnee = new Spinner(group2, SWT.BORDER);
					spinAnnee.setMaximum(3000);
					spinAnnee.setMinimum(1900);
					spinAnnee.setBounds(182, 21, 63, 14);
				}
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
					btnParcourirFichierCompta = new Button(group2, SWT.PUSH | SWT.CENTER);
					btnParcourirFichierCompta.setText("Parcourrir");
					GridData button2LData = new GridData();
					button2LData.horizontalAlignment = GridData.END;
					button2LData.widthHint = 63;
					button2LData.heightHint = 28;
					btnParcourirFichierCompta.setLayoutData(button2LData);
					btnParcourirFichierCompta.setBounds(371, 49, 63, 28);
				}
			}
			this.setSize(511, 483);
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Group getGroup1() {
		return group1;
	}

	public void setGroup1(Group group1) {
		this.group1 = group1;
	}

	public List getListeDossier() {
		return listeDossier;
	}

	public void setListeDossier(List list2) {
		this.listeDossier = list2;
	}

	public Button getBtnParcourirFichierCompta() {
		return btnParcourirFichierCompta;
	}

	public void setBtnParcourirFichierCompta(Button btnParcourirFichierCompta) {
		this.btnParcourirFichierCompta = btnParcourirFichierCompta;
	}

	public Label getLaAnnee() {
		return laAnnee;
	}

	public void setLaAnnee(Label laAnnee) {
		this.laAnnee = laAnnee;
	}

	public Label getLaFichierCompta() {
		return laFichierCompta;
	}

	public void setLaFichierCompta(Label laFichierCompta) {
		this.laFichierCompta = laFichierCompta;
	}

	public Spinner getSpinAnnee() {
		return spinAnnee;
	}

	public void setSpinAnnee(Spinner spinAnnee) {
		this.spinAnnee = spinAnnee;
	}

	public Text getTxtFichierCompta() {
		return txtFichierCompta;
	}

	public void setTxtFichierCompta(Text txtFichierCompta) {
		this.txtFichierCompta = txtFichierCompta;
	}

}

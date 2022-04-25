package fr.legrain.liasseFiscale.wizards.old;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
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
public class CompositePageNouveauDossier extends org.eclipse.swt.widgets.Composite {
	private Group group2;
	private Label laFichierCompta;
	private Button btnParcourirFichierCompta;
	private Text txtNomDossier;
	private Label laNomDossier;
	private Spinner spinAnnee;
	private Text txtFichierCompta;
	private Label laAnnee;

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
		CompositePageNouveauDossier inst = new CompositePageNouveauDossier(shell, SWT.NULL);
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

	public CompositePageNouveauDossier(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.numColumns = 2;
			thisLayout.verticalSpacing = 15;
			this.setLayout(thisLayout);
			this.setSize(560, 277);
			{
				laNomDossier = new Label(this, SWT.NONE);
				laNomDossier.setText("Nom du nouveau dossier");
				GridData laNomDossierLData = new GridData();
				laNomDossierLData.grabExcessHorizontalSpace = true;
				laNomDossier.setBounds(7, 14, 119, 14);
			}
			{
				txtNomDossier = new Text(this, SWT.BORDER);
				GridData txtNomDossierLData = new GridData();
				txtNomDossierLData.heightHint = 16;
				txtNomDossierLData.grabExcessHorizontalSpace = true;
				txtNomDossierLData.horizontalAlignment = GridData.FILL;
				txtNomDossier.setLayoutData(txtNomDossierLData);
			}
			{
				group2 = new Group(this, SWT.NONE);
				GridLayout group2Layout = new GridLayout();
				group2Layout.numColumns = 3;
				group2.setText("Initialisation de la nouvelle liasse");
				GridData group2LData = new GridData();
				group2LData.horizontalSpan = 2;
				group2LData.grabExcessHorizontalSpace = true;
				group2LData.horizontalAlignment = GridData.FILL;
				group2.setLayoutData(group2LData);
				group2.setLayout(group2Layout);
				{
					laAnnee = new Label(group2, SWT.NONE);
					laAnnee.setText("Année");
					GridData laAnneeLData = new GridData();
					laAnneeLData.grabExcessHorizontalSpace = true;
					GridData laAnneeLData1 = new GridData();
					laAnneeLData1.widthHint = 49;
					laAnneeLData1.heightHint = 15;
					laAnnee.setLayoutData(laAnneeLData1);
				}
				{
					spinAnnee = new Spinner(group2, SWT.BORDER);
					spinAnnee.setMaximum(3000);
					spinAnnee.setMinimum(1900);
					GridData spinAnneeLData = new GridData();
					spinAnneeLData.grabExcessHorizontalSpace = true;
					spinAnnee.setBounds(182, 21, 63, 14);
				}
				{
					laFichierCompta = new Label(group2, SWT.NONE);
					laFichierCompta
						.setText("Fichier provenant du programme de comptabilité");
					GridData laFichierComptaLData = new GridData();
					laFichierComptaLData.horizontalSpan = 3;
					laFichierComptaLData.widthHint = 340;
					laFichierComptaLData.heightHint = 13;
					laFichierCompta.setLayoutData(laFichierComptaLData);
				}
				{
					txtFichierCompta = new Text(group2, SWT.BORDER);
					GridData txtFichierComptaLData = new GridData();
					txtFichierComptaLData.horizontalSpan = 2;
					txtFichierComptaLData.heightHint = 16;
					txtFichierComptaLData.horizontalAlignment = GridData.FILL;
					txtFichierComptaLData.grabExcessHorizontalSpace = true;
					txtFichierCompta.setLayoutData(txtFichierComptaLData);
				}
				{
					btnParcourirFichierCompta = new Button(group2, SWT.PUSH
						| SWT.CENTER);
					btnParcourirFichierCompta.setText("Parcourrir");
					GridData btnParcourirFichierComptaLData = new GridData();
					btnParcourirFichierComptaLData.horizontalAlignment = GridData.END;
					btnParcourirFichierComptaLData.widthHint = 63;
					btnParcourirFichierComptaLData.heightHint = 22;
					btnParcourirFichierCompta.setLayoutData(btnParcourirFichierComptaLData);
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Button getBtnParcourirFichierCompta() {
		return btnParcourirFichierCompta;
	}

	public void setBtnParcourirFichierCompta(Button btnParcourirFichierCompta) {
		this.btnParcourirFichierCompta = btnParcourirFichierCompta;
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

	public Text getTxtNomDossier() {
		return txtNomDossier;
	}

	public void setTxtNomDossier(Text txtNomDossier) {
		this.txtNomDossier = txtNomDossier;
	}

}

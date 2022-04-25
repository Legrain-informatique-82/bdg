package fr.legrain.liasseFiscale.wizards.old;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
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
public class CompositePageDebut extends org.eclipse.swt.widgets.Composite {
	private Group group1;
	private Button btnRepriseLiasse;
	private Button btnNouvelleLiasse;
	private Button btnNouvelleLiasseEtDossier;

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
		CompositePageDebut inst = new CompositePageDebut(shell, SWT.NULL);
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

	public CompositePageDebut(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.makeColumnsEqualWidth = true;
			this.setLayout(thisLayout);
			this.setSize(399, 294);
			{
				group1 = new Group(this, SWT.NONE);
				GridLayout group1Layout = new GridLayout();
				group1Layout.makeColumnsEqualWidth = true;
				group1.setLayout(group1Layout);
				GridData group1LData = new GridData();
				group1LData.horizontalAlignment = GridData.FILL;
				group1LData.grabExcessHorizontalSpace = true;
				group1.setLayoutData(group1LData);
				group1.setText("Que souhaitez vous faire ?");
				{
					btnNouvelleLiasse = new Button(group1, SWT.RADIO | SWT.LEFT);
					btnNouvelleLiasse.setText("Création d'une nouvelle liasse dans un dossier existant");
					btnNouvelleLiasse.setSelection(true);
				}
				{
					btnNouvelleLiasseEtDossier = new Button(group1, SWT.RADIO
						| SWT.LEFT);
					btnNouvelleLiasseEtDossier.setText("Création d'un nouvelle liasse dans un nouveau dossier");
				}
				{
					btnRepriseLiasse = new Button(group1, SWT.RADIO | SWT.LEFT);
					GridData button3LData = new GridData();
					button3LData.widthHint = 205;
					button3LData.heightHint = 14;
					btnRepriseLiasse.setLayoutData(button3LData);
					btnRepriseLiasse.setText("Reprise d'une liasse déjà existante");
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Button getBtnNouvelleLiasseEtDossier() {
		return btnNouvelleLiasseEtDossier;
	}

	public void setBtnNouvelleLiasseEtDossier(Button button1) {
		this.btnNouvelleLiasseEtDossier = button1;
	}

	public Button getBtnNouvelleLiasse() {
		return btnNouvelleLiasse;
	}

	public void setBtnNouvelleLiasse(Button button2) {
		this.btnNouvelleLiasse = button2;
	}

	public Button getBtnRepriseLiasse() {
		return btnRepriseLiasse;
	}

	public void setBtnRepriseLiasse(Button button3) {
		this.btnRepriseLiasse = button3;
	}

}

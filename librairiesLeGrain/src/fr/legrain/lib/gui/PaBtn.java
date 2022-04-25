package fr.legrain.lib.gui;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
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
public class PaBtn extends org.eclipse.swt.widgets.Composite {
	private Button btnAnnuler;
	private Button btnImprimer;
	private Button btnFermer;
	private Button btnSupprimer;
	private Button btnModifier;
	private Button btnInserer;
	private Button btnEnregistrer;

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
		PaBtn inst = new PaBtn(shell, SWT.NULL);
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

	public PaBtn(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			this.setLayout(thisLayout);
			thisLayout.numColumns = 7;
			thisLayout.makeColumnsEqualWidth = true;
			this.setSize(776, 100);
			{
				btnEnregistrer = new Button(this, SWT.PUSH | SWT.CENTER);
				GridData btnEnregistrerLData = new GridData();
				btnEnregistrerLData.horizontalAlignment = GridData.FILL;
				btnEnregistrer.setLayoutData(btnEnregistrerLData);
				btnEnregistrer.setText("Enregistrer [F3]");
			}
			{
				btnInserer = new Button(this, SWT.PUSH | SWT.CENTER);
				GridData btnInsererLData = new GridData();
				btnInsererLData.horizontalAlignment = GridData.FILL;
				btnInserer.setLayoutData(btnInsererLData);
				btnInserer.setText("Insérer [F6]");
			}
			{
				btnModifier = new Button(this, SWT.PUSH | SWT.CENTER);
				GridData btnModifierLData = new GridData();
				btnModifierLData.horizontalAlignment = GridData.FILL;
				btnModifier.setLayoutData(btnModifierLData);
				btnModifier.setText("Modifier [F2]");
			}
			{
				btnSupprimer = new Button(this, SWT.PUSH | SWT.CENTER);
				GridData btnSupprimerLData = new GridData();
				btnSupprimerLData.horizontalAlignment = GridData.FILL;
				btnSupprimer.setLayoutData(btnSupprimerLData);
				btnSupprimer.setText("Supprimer [F10]");
			}
			{
				btnAnnuler = new Button(this, SWT.PUSH | SWT.CENTER);
				GridData btnAnnulerLData = new GridData();
				btnAnnulerLData.horizontalAlignment = GridData.FILL;
				btnAnnuler.setLayoutData(btnAnnulerLData);
				btnAnnuler.setText("Annuler [ESC]");
			}
			{
				btnFermer = new Button(this, SWT.PUSH | SWT.CENTER);
				GridData btnFermerLData = new GridData();
				btnFermerLData.horizontalAlignment = GridData.FILL;
				btnFermer.setLayoutData(btnFermerLData);
				btnFermer.setText("Fermer [F4]");
			}
			{
				btnImprimer = new Button(this, SWT.PUSH | SWT.CENTER);
				GridData btnImprimerLData = new GridData();
				btnImprimerLData.horizontalAlignment = GridData.FILL;
				btnImprimer.setLayoutData(btnImprimerLData);
				btnImprimer.setText("Imprimer [F11]");
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Button getBtnAnnuler() {
		return btnAnnuler;
	}
	
	public Button getBtnEnregistrer() {
		return btnEnregistrer;
	}
	
	public Button getBtnInserer() {
		return btnInserer;
	}
	
	public Button getBtnModifier() {
		return btnModifier;
	}
	
	public Button getBtnSupprimer() {
		return btnSupprimer;
	}
	
	public Button getBtnFermer() {
		return btnFermer;
	}
	
	public Button getBtnImprimer() {
		return btnImprimer;
	}

}

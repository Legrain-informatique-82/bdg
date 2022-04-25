package fr.legrain.lib.gui;
import com.cloudgarden.resource.SWTResourceManager;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;

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
public class DefaultFrameBoutonSWT extends org.eclipse.swt.widgets.Composite {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	private Button btnAnnuler;
	private Button btnModifier;
	private Button btnFermer;
	private Button btnSupprimer;
	private Button btnInserer;
	private Button btnEnregistrer;
	private Composite paBtn;
	private CLabel laMessage;
	private Composite compositeForm;

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
		DefaultFrameBoutonSWT inst = new DefaultFrameBoutonSWT(shell, SWT.FILL);
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

	public DefaultFrameBoutonSWT(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			this.setLayout(new GridLayout());
			this.setSize(614, 447);
			{
				GridData compositeFormLData = new GridData();
				compositeFormLData.verticalAlignment = GridData.FILL;
				compositeFormLData.horizontalAlignment = GridData.FILL;
				compositeFormLData.grabExcessHorizontalSpace = true;
				compositeFormLData.grabExcessVerticalSpace = true;
				compositeForm = new Composite(this, SWT.BORDER);
				GridLayout compositeFormLayout = new GridLayout();
				compositeFormLayout.numColumns = 2;
				compositeFormLayout.makeColumnsEqualWidth = true;
				compositeForm.setLayout(compositeFormLayout);
				compositeForm.setLayoutData(compositeFormLData);
			}
			{
				GridData compositeToolLData = new GridData();
				compositeToolLData.verticalAlignment = GridData.END;
				compositeToolLData.horizontalAlignment = GridData.CENTER;
				compositeToolLData.widthHint = 505;
				compositeToolLData.heightHint = 33;
				compositeToolLData.grabExcessHorizontalSpace = true;
				paBtn = new Composite(this, SWT.NONE);
				GridLayout compositeToolLayout = new GridLayout();
				compositeToolLayout.numColumns = 6;
				paBtn.setLayout(compositeToolLayout);
				paBtn.setLayoutData(compositeToolLData);
				{
					btnAnnuler = new Button(paBtn, SWT.PUSH | SWT.CENTER);
					GridData button6LData = new GridData();
					button6LData.horizontalAlignment = GridData.FILL;
					btnAnnuler.setLayoutData(button6LData);
					btnAnnuler.setText("Annuler [ESC]");
				}
				{
					btnEnregistrer = new Button(paBtn, SWT.PUSH | SWT.CENTER);
					btnEnregistrer.setText("Enregistrer [F3]");
				}
				{
					btnInserer = new Button(paBtn, SWT.PUSH | SWT.CENTER);
					btnInserer.setText("Insérer [F6]");
				}
				{
					btnModifier = new Button(paBtn, SWT.PUSH | SWT.CENTER);
					btnModifier.setText("Modifier [F2]");
				}
				{
					btnSupprimer = new Button(paBtn, SWT.PUSH | SWT.CENTER);
					btnSupprimer.setText("Supprimer [F10]");
				}
				{
					btnFermer = new Button(paBtn, SWT.PUSH | SWT.CENTER);
					GridData button1LData = new GridData();
					button1LData.horizontalAlignment = GridData.FILL;
					btnFermer.setLayoutData(button1LData);
					btnFermer.setText("Fermer [F4]");
				}
			}
			{
				laMessage = new CLabel(this, SWT.NONE);
				laMessage.setForeground(new Color(getDisplay(),255,100,100));
				GridData cLabel1LData = new GridData();
				cLabel1LData.widthHint = 607;
				cLabel1LData.heightHint = 19;
				laMessage.setLayoutData(cLabel1LData);
				laMessage.setText("");
				laMessage.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.BOLD, false, false));
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

	public Button getBtnFermer() {
		return btnFermer;
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

	public Composite getCompositeForm() {
		return compositeForm;
	}

	public Composite getPaBtn() {
		return paBtn;
	}
	
	public CLabel getLaMessage() {
		return laMessage;
	}

}

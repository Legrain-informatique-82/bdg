package fr.legrain.majcomptelegrainfr.ecrans;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import com.cloudgarden.resource.SWTResourceManager;

import fr.legrain.lib.gui.PaBtnReduit;


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
public class PaMajCompteLegrainFr extends org.eclipse.swt.widgets.Composite {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	private Button btnMAJ;
	private Composite paBtn;
	private Button btnFermer;
	private Button btnCreation;
	private Group groupSelection;
	private Composite composite1;

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
		PaMajCompteLegrainFr inst = new PaMajCompteLegrainFr(shell, SWT.NULL);
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

	public PaMajCompteLegrainFr(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			this.setLayout(thisLayout);
			thisLayout.numColumns = 4;
			{
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.CENTER;
				composite1LData.grabExcessVerticalSpace = true;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.widthHint = 414;
				composite1LData.heightHint = 117;
				composite1 = new Composite(this, SWT.BORDER);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 2;
				composite1.setLayout(composite1Layout);
				composite1.setLayoutData(composite1LData);
				{
					groupSelection = new Group(composite1, SWT.NONE);
					GridLayout groupSelectionLayout = new GridLayout();
					groupSelectionLayout.numColumns = 4;
					groupSelection.setLayout(groupSelectionLayout);
					GridData groupSelectionLData = new GridData();
					groupSelectionLData.horizontalSpan = 4;
					groupSelectionLData.horizontalAlignment = GridData.FILL;
					groupSelectionLData.grabExcessVerticalSpace = true;
					groupSelectionLData.grabExcessHorizontalSpace = true;
					groupSelectionLData.heightHint = 39;
					groupSelection.setLayoutData(groupSelectionLData);
					groupSelection.setText("Mise à jour des comptes - Legrain.fr -");
					groupSelection.setFont(SWTResourceManager.getFont("Segoe UI", 9, 1, false, false));
					{
						btnCreation = new Button(groupSelection, SWT.PUSH | SWT.CENTER);
						GridData btnCreationLData = new GridData();
						btnCreationLData.widthHint = 185;
						btnCreationLData.heightHint = 29;
						btnCreation.setLayoutData(btnCreationLData);
						btnCreation.setText("Création et envoie du fichier");
					}
					{
						btnMAJ = new Button(groupSelection, SWT.PUSH | SWT.CENTER);
						GridData btnMAJLData = new GridData();
						btnMAJLData.horizontalAlignment = GridData.END;
						btnMAJLData.widthHint = 203;
						btnMAJLData.heightHint = 29;
						btnMAJ.setLayoutData(btnMAJLData);
						btnMAJ.setText("Mise à jour des comptes sur le site");
					}
				}

				{
					paBtn = new Composite(composite1, SWT.NONE);
					GridLayout paBtnLayout = new GridLayout();
					paBtnLayout.makeColumnsEqualWidth = true;
					GridData paBtnLData = new GridData();
					paBtnLData.heightHint = 45;
					paBtnLData.horizontalAlignment = GridData.CENTER;
					paBtnLData.grabExcessHorizontalSpace = true;
					paBtnLData.widthHint = 143;
					paBtnLData.grabExcessVerticalSpace = true;
					paBtn.setLayoutData(paBtnLData);
					paBtn.setLayout(paBtnLayout);
					{
						btnFermer = new Button(paBtn, SWT.PUSH | SWT.CENTER);
						GridData btnFermerLData = new GridData();
						btnFermerLData.heightHint = 29;
						btnFermerLData.horizontalAlignment = GridData.CENTER;
						btnFermerLData.widthHint = 111;
						btnFermer.setLayoutData(btnFermerLData);
						btnFermer.setText("Fermer [F4]");
						btnFermer.setSize(111, 29);
					}
				}
				GridData cbPrintExportLData = new GridData();
				cbPrintExportLData.horizontalSpan = 2;
			}
			this.layout();
			pack();
			this.setSize(474, 231);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	

	public void setCbPrintExport(Button cbPrintExport) {
	}
	
	
	
	public Group getGroupSelection() {
		return groupSelection;
	}
	
	
	public Button getBtnMAJ() {
		return btnMAJ;
	}

	public Button getBtnCreation() {
		return btnCreation;
	}
	
	public Button getBtnFermer() {
		return btnFermer;
	}
	
	public Composite getPaBtn() {
		return paBtn;
	}

}

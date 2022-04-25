package fr.legrain.lib.gui.aide;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;

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
public class PaAideSWT extends org.eclipse.swt.widgets.Composite {
	private Composite paBtn;
	private Button btnAnnuler;
	private TabFolder tabFolder;
	private Label laTitre;
	private Button btnNouveau;
	private Button btnOK;
	private Button btnAfficher;
	private Composite paTitre;

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
		PaAideSWT inst = new PaAideSWT(shell, SWT.NULL);
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

	public PaAideSWT(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			this.setLayout(new GridLayout());
			this.setSize(550, 346);
			{
				paTitre = new Composite(this, SWT.NONE);
				GridLayout paTitreLayout = new GridLayout();
				paTitreLayout.makeColumnsEqualWidth = true;
				GridData paTitreLData = new GridData();
				paTitreLData.verticalAlignment = GridData.BEGINNING;
				paTitreLData.horizontalAlignment = GridData.FILL;
				paTitre.setLayoutData(paTitreLData);
				paTitre.setLayout(paTitreLayout);
				{
					laTitre = new Label(paTitre, SWT.NONE);
					GridData laTitreLData = new GridData();
					laTitreLData.horizontalAlignment = GridData.CENTER;
					laTitreLData.grabExcessHorizontalSpace = true;
					laTitre.setLayoutData(laTitreLData);
					laTitre.setText("Aide");
				}
			}
			{
				tabFolder = new TabFolder(this, SWT.NONE);
				GridData tabFolderLData = new GridData();
				tabFolderLData.grabExcessHorizontalSpace = true;
				tabFolderLData.grabExcessVerticalSpace = true;
				tabFolderLData.verticalAlignment = GridData.FILL;
				tabFolderLData.horizontalAlignment = GridData.FILL;
				tabFolder.setLayoutData(tabFolderLData);
				tabFolder.setSelection(0);
			}
			{
				paBtn = new Composite(this, SWT.NONE);
				GridLayout paBtnLayout = new GridLayout();
				paBtnLayout.makeColumnsEqualWidth = true;
				paBtnLayout.numColumns = 4;
				GridData paBtnLData = new GridData();
				paBtnLData.horizontalAlignment = GridData.CENTER;
				paBtnLData.grabExcessHorizontalSpace = true;
				paBtnLData.widthHint = 505;
				paBtnLData.heightHint = 40;
				paBtn.setLayoutData(paBtnLData);
				paBtn.setLayout(paBtnLayout);
				{
					btnAnnuler = new Button(paBtn, SWT.PUSH | SWT.CENTER);
					GridData btnAnnulerLData = new GridData();
					btnAnnulerLData.horizontalAlignment = GridData.FILL;
					btnAnnulerLData.heightHint = 28;
					btnAnnulerLData.grabExcessHorizontalSpace = true;
					btnAnnuler.setLayoutData(btnAnnulerLData);
					btnAnnuler.setText("Annuler [ESC]");
				}
				{
					btnOK = new Button(paBtn, SWT.PUSH | SWT.CENTER);
					GridData btnOKLData = new GridData();
					btnOKLData.horizontalAlignment = GridData.FILL;
					btnOKLData.heightHint = 28;
					btnOKLData.grabExcessHorizontalSpace = true;
					btnOK.setLayoutData(btnOKLData);
					btnOK.setText("Valider [F3]");
				}
				{
					btnNouveau = new Button(paBtn, SWT.PUSH | SWT.CENTER);
					GridData btnNouveauLData = new GridData();
					btnNouveauLData.horizontalAlignment = GridData.FILL;
					btnNouveauLData.grabExcessHorizontalSpace = true;
					btnNouveauLData.heightHint = 28;
					btnNouveau.setLayoutData(btnNouveauLData);
					btnNouveau.setText("Nouveau [F6]");
				}
				{
					btnAfficher = new Button(paBtn, SWT.PUSH | SWT.CENTER);
					GridData btnNouveauLData = new GridData();
					btnNouveauLData.horizontalAlignment = GridData.FILL;
					btnNouveauLData.grabExcessHorizontalSpace = true;
					btnNouveauLData.heightHint = 28;
					btnAfficher.setLayoutData(btnNouveauLData);
					btnAfficher.setText("Afficher la fiche");
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Composite getPaBtn() {
		return paBtn;
	}
	
	public Composite getPaTitre() {
		return paTitre;
	}
	
	public TabFolder getTabFolder() {
		return tabFolder;
	}

	public Button getBtnAnnuler() {
		return btnAnnuler;
	}

	public Button getBtnNouveau() {
		return btnNouveau;
	}

	public Button getBtnOK() {
		return btnOK;
	}

	public Label getLaTitre() {
		return laTitre;
	}

	public Button getBtnAfficher() {
		return btnAfficher;
	}

	public void setBtnAfficher(Button btnAfficher) {
		this.btnAfficher = btnAfficher;
	}

}

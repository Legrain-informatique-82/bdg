package fr.legrain.liasseFiscale.wizards;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

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
public class DialogNouveauDossier extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private Button btnOK;
	private Combo listeTypeDoc;
	private Group group2;
	private Canvas canvas1;
	private Button btnCancel;

	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Dialog inside a new Shell.
	*/
	public static void main(String[] args) {
		try {
			Display display = Display.getDefault();
			Shell shell = new Shell(display);
			DialogNouveauDossier inst = new DialogNouveauDossier(shell, SWT.NULL);
			inst.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DialogNouveauDossier(Shell parent, int style) {
		super(parent, style);
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

			GridLayout dialogShellLayout = new GridLayout();
			dialogShellLayout.numColumns = 2;
			dialogShell.setLayout(dialogShellLayout);
			dialogShell.layout();
			dialogShell.pack();
			dialogShell.setSize(331, 285);
			{
				group2 = new Group(dialogShell, SWT.NONE);
				GridLayout group2Layout = new GridLayout();
				group2Layout.makeColumnsEqualWidth = true;
				group2.setLayout(group2Layout);
				group2.setText("Type de document");
				GridData group2LData = new GridData();
				group2LData.verticalAlignment = GridData.FILL;
				group2LData.horizontalAlignment = GridData.FILL;
				group2LData.grabExcessHorizontalSpace = true;
				group2.setLayoutData(group2LData);
				{
					listeTypeDoc = new Combo(group2, SWT.READ_ONLY);
					listeTypeDoc.setText("listeTypeDoc");
					GridData combo1LData = new GridData();
					combo1LData.widthHint = 142;
					combo1LData.heightHint = 21;
					listeTypeDoc.setLayoutData(combo1LData);
				}
			}
			{
				GridData canvas1LData = new GridData();
				canvas1LData.horizontalSpan = 2;
				canvas1LData.horizontalAlignment = GridData.CENTER;
				canvas1LData.heightHint = 26;
				canvas1LData.verticalAlignment = GridData.END;
				canvas1LData.grabExcessVerticalSpace = true;
				canvas1LData.widthHint = 289;
				canvas1 = new Canvas(dialogShell, SWT.NONE);
				FillLayout canvas1Layout = new FillLayout(
					org.eclipse.swt.SWT.HORIZONTAL);
				canvas1Layout.spacing = 10;
				canvas1Layout.marginWidth = 50;
				canvas1.setLayout(canvas1Layout);
				canvas1.setLayoutData(canvas1LData);
				{
					btnOK = new Button(canvas1, SWT.PUSH | SWT.CENTER);
					btnOK.setText("OK");
					GridData btnOKLData = new GridData();
					btnOKLData.horizontalAlignment = GridData.CENTER;
					btnOKLData.grabExcessHorizontalSpace = true;
					btnOKLData.horizontalSpan = 2;
					btnOK.setLayoutData(btnOKLData);
					btnOK.setBounds(-1, 120, 52, 23);
				}
				{
					btnCancel = new Button(canvas1, SWT.PUSH | SWT.CENTER);
					btnCancel.setText("Annuler");
					GridData btnCancelLData = new GridData();
					btnCancelLData.horizontalAlignment = GridData.END;
					btnCancelLData.grabExcessHorizontalSpace = true;
					btnCancel.setLayoutData(btnCancelLData);
				}
			}
			dialogShell.open();
			Display display = dialogShell.getDisplay();
			while (!dialogShell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public Combo getListeTypeDoc() {
		return listeTypeDoc;
	}

	public void setListeTypeDoc(Combo listeTypeDoc) {
		this.listeTypeDoc = listeTypeDoc;
	}

	public Button getBtnCancel() {
		return btnCancel;
	}

	public Button getBtnOK() {
		return btnOK;
	}

}

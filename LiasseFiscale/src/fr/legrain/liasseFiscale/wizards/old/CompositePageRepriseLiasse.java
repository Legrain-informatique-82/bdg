package fr.legrain.liasseFiscale.wizards.old;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
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
public class CompositePageRepriseLiasse extends org.eclipse.swt.widgets.Composite {
	private List listeDossier;
	private Group group1;
	private Button btnEtape1;
	private Button btnEtape3;
	private Button btnEtape4;
	private Button btnEtape2;
	private Group group3;
	private Group group2;
	private List listeLiasse;

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
		CompositePageRepriseLiasse inst = new CompositePageRepriseLiasse(shell, SWT.NULL);
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

	public CompositePageRepriseLiasse(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.makeColumnsEqualWidth = true;
			thisLayout.numColumns = 2;
			this.setLayout(thisLayout);
			this.setSize(620, 461);
			{
				group2 = new Group(this, SWT.NONE);
				GridLayout group2Layout = new GridLayout();
				group2Layout.makeColumnsEqualWidth = true;
				group2.setLayout(group2Layout);
				GridData group2LData = new GridData();
				group2LData.horizontalAlignment = GridData.FILL;
				group2LData.verticalAlignment = GridData.BEGINNING;
				group2LData.grabExcessHorizontalSpace = true;
				group2.setLayoutData(group2LData);
				group2.setText("Choix du dossier");
				{
					listeDossier = new List(group2, SWT.H_SCROLL
						| SWT.V_SCROLL
						| SWT.BORDER);
					GridData listeDossierLData = new GridData();
					listeDossierLData.verticalAlignment = GridData.FILL;
					listeDossierLData.grabExcessVerticalSpace = true;
					listeDossierLData.horizontalAlignment = GridData.FILL;
					listeDossierLData.grabExcessHorizontalSpace = true;
					listeDossier.setLayoutData(listeDossierLData);
				}
			}
			{
				group3 = new Group(this, SWT.NONE);
				GridLayout group3Layout = new GridLayout();
				group3Layout.makeColumnsEqualWidth = true;
				group3.setLayout(group3Layout);
				GridData group3LData = new GridData();
				group3LData.horizontalAlignment = GridData.FILL;
				group3LData.grabExcessHorizontalSpace = true;
				group3LData.verticalAlignment = GridData.BEGINNING;
				group3.setLayoutData(group3LData);
				group3.setText("Choix de la liasse");
				{
					listeLiasse = new List(group3, SWT.H_SCROLL
						| SWT.V_SCROLL
						| SWT.BORDER);
					GridData listeLiasseLData = new GridData();
					listeLiasseLData.horizontalAlignment = GridData.FILL;
					listeLiasseLData.grabExcessHorizontalSpace = true;
					listeLiasseLData.verticalAlignment = GridData.FILL;
					listeLiasseLData.grabExcessVerticalSpace = true;
					listeLiasse.setLayoutData(listeLiasseLData);
					listeLiasse.setBounds(21, 294, 140, 56);
				}
			}
			{
				group1 = new Group(this, SWT.NONE);
				GridLayout group1Layout = new GridLayout();
				group1.setLayout(group1Layout);
				GridData group1LData = new GridData();
				group1LData.horizontalSpan = 2;
				group1LData.grabExcessHorizontalSpace = true;
				group1LData.horizontalAlignment = GridData.FILL;
				group1LData.verticalAlignment = GridData.FILL;
				group1LData.grabExcessVerticalSpace = true;
				group1.setLayoutData(group1LData);
				group1.setText("Reprendre à l'étape");
				{
					btnEtape1 = new Button(group1, SWT.RADIO | SWT.LEFT);
					btnEtape1.setText("Etape 1 - Création / choix de la liasse + fichier XML de base");
				}
				{
					btnEtape2 = new Button(group1, SWT.RADIO | SWT.LEFT);
					btnEtape2.setText("Etape 2 - Enrichissement du fichier XML");
				}
				{
					btnEtape3 = new Button(group1, SWT.RADIO | SWT.LEFT);
					btnEtape3.setText("Etape 3 - Répartition + fichier XML final");
				}
				{
					btnEtape4 = new Button(group1, SWT.RADIO | SWT.LEFT);
					btnEtape4.setText("Etape 4 - Génaration du document final");
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List getListeLiasse() {
		return listeLiasse;
	}

	public void setListeLiasse(List list1) {
		this.listeLiasse = list1;
	}

	public List getListeDossier() {
		return listeDossier;
	}

	public void setListeDossier(List list2) {
		this.listeDossier = list2;
	}
	
	public Button getBtnEtape1() {
		return btnEtape1;
	}
	
	public Button getBtnEtape2() {
		return btnEtape2;
	}
	
	public Button getBtnEtape3() {
		return btnEtape3;
	}
	
	public Button getBtnEtape4() {
		return btnEtape4;
	}

}

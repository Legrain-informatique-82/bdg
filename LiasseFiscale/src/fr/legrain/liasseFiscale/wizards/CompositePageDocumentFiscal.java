package fr.legrain.liasseFiscale.wizards;
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
import org.eclipse.swt.widgets.Tree;


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
public class CompositePageDocumentFiscal extends org.eclipse.swt.widgets.Composite {
	private Group group1;
	private Button btnNouveauDoc;
	private Button btnSuppr;
	private Composite composite1;
	private Tree treeDossier;
	private Button btnNouveauDossier;
	private Button btnImport;
	private Button btnVerrouiller;

	public Button getBtnNouveauDossier() {
		return btnNouveauDossier;
	}

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
		CompositePageDocumentFiscal inst = new CompositePageDocumentFiscal(shell, SWT.NULL);
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

	public CompositePageDocumentFiscal(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.makeColumnsEqualWidth = true;
			this.setLayout(thisLayout);
			this.setSize(428, 465);
			{
				group1 = new Group(this, SWT.NONE);
				GridLayout group1Layout = new GridLayout();
				group1Layout.numColumns = 2;
				group1.setLayout(group1Layout);
				GridData group1LData = new GridData();
				group1LData.horizontalAlignment = GridData.FILL;
				group1LData.grabExcessHorizontalSpace = true;
				group1LData.verticalAlignment = GridData.FILL;
				group1LData.grabExcessVerticalSpace = true;
				group1.setLayoutData(group1LData);
				group1.setText("Choix du dossier");
				{
					GridData tree1LData = new GridData();
					tree1LData.horizontalAlignment = GridData.FILL;
					tree1LData.grabExcessHorizontalSpace = true;
					tree1LData.verticalAlignment = GridData.FILL;
					tree1LData.grabExcessVerticalSpace = true;
					treeDossier = new Tree(group1, SWT.H_SCROLL
						| SWT.V_SCROLL
						| SWT.BORDER);
					treeDossier.setLayoutData(tree1LData);
				}
				{
					composite1 = new Composite(group1, SWT.NONE);
					GridLayout composite1Layout = new GridLayout();
					composite1Layout.makeColumnsEqualWidth = true;
					GridData composite1LData = new GridData();
					composite1LData.horizontalAlignment = GridData.FILL;
					composite1LData.verticalAlignment = GridData.FILL;
					composite1.setLayoutData(composite1LData);
					composite1.setLayout(composite1Layout);
					{
						btnNouveauDossier = new Button(composite1, SWT.PUSH
							| SWT.CENTER);
						GridData btnNouveauDossierLData = new GridData();
						btnNouveauDossierLData.horizontalAlignment = GridData.FILL;
						btnNouveauDossier.setLayoutData(btnNouveauDossierLData);
						btnNouveauDossier.setText("Nouveau Dossier");
					}
					{
						btnNouveauDoc = new Button(composite1, SWT.PUSH
							| SWT.CENTER);
						GridData btnNouveauDocLData = new GridData();
						btnNouveauDocLData.horizontalAlignment = GridData.FILL;
						btnNouveauDoc.setLayoutData(btnNouveauDocLData);
						btnNouveauDoc.setText("Nouveau Document");
					}
					{
						btnVerrouiller = new Button(composite1, SWT.PUSH
							| SWT.CENTER);
						GridData btnVerrouillerLData = new GridData();
						btnVerrouillerLData.horizontalAlignment = GridData.FILL;
						btnVerrouiller.setLayoutData(btnVerrouillerLData);
						btnVerrouiller.setText("Verrouiller");
					}
					{
						btnSuppr = new Button(composite1, SWT.PUSH | SWT.CENTER);
						GridData btnSupprLData = new GridData();
						btnSupprLData.horizontalAlignment = GridData.FILL;
						btnSuppr.setLayoutData(btnSupprLData);
						btnSuppr.setText("Supprimer");
					}
					{
						btnImport = new Button(composite1, SWT.PUSH
							| SWT.CENTER);
						btnImport.setText("Import Comptabilité");
					}
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Tree getTreeDossier() {
		return treeDossier;
	}
	
	public Button getBtnNouveauDoc() {
		return btnNouveauDoc;
	}
	
	public Button getBtnSuppr() {
		return btnSuppr;
	}
	
	public Button getBtnVerrouiller() {
		return btnVerrouiller;
	}
	
	public Button getBtnImport() {
		return btnImport;
	}

}

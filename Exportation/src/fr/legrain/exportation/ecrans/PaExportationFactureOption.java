package fr.legrain.exportation.ecrans;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.cloudgarden.resource.SWTResourceManager;


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
public class PaExportationFactureOption extends org.eclipse.swt.widgets.Composite {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
	


	private Group groupPrincipal;
	
	private Group groupReglementLie;
	private Label la3;
	private Button cbAcompte;
	private Label la1;
	private Label la2;
	private Button cbTous;
	private Button cbRemise;
	private Button cbReglementSimple;
	private Button cbReglementLies;
	private Group groupDocumentLie;
	private Group groupPointage;

	private Button cbTransPointage;
	private Button cbTransDocLies;
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
		PaExportationFactureOption inst = new PaExportationFactureOption(shell, SWT.NULL);
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

	public PaExportationFactureOption(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			this.setLayout(thisLayout);
			
			groupPrincipal = new Group(this, SWT.NONE);
			GridLayout group1Layout = new GridLayout();
			group1Layout.numColumns =2;
			groupPrincipal.setLayout(group1Layout);
			GridData groupLData = new GridData();
			groupLData.horizontalAlignment = GridData.CENTER;
			groupLData.grabExcessHorizontalSpace = true;
			groupLData.grabExcessVerticalSpace = true;
			groupLData.horizontalSpan = 2;
			groupLData.widthHint = 289;
			groupLData.heightHint = 101;
			groupLData.verticalAlignment = GridData.BEGINNING;
			groupPrincipal.setLayoutData(groupLData);
			groupPrincipal.setText("Transférer les réglements");
			{
				cbTous = new Button(groupPrincipal, SWT.CHECK | SWT.LEFT);
				GridData cbTousLData = new GridData();
				cbTousLData.horizontalSpan = 2;
				cbTous.setLayoutData(cbTousLData);
				cbTous.setText("Tous les types de réglements");
			}
			{
				GridData la1LData = new GridData();
				la1LData.widthHint = 39;
				la1LData.heightHint = 14;
				la1 = new Label(groupPrincipal, SWT.NONE);
				la1.setLayoutData(la1LData);
			}
			{
				cbAcompte = new Button(groupPrincipal, SWT.CHECK | SWT.LEFT);
				GridData cbAcompteLData = new GridData();
				cbAcompteLData.grabExcessHorizontalSpace = true;
				cbAcompte.setLayoutData(cbAcompteLData);
				cbAcompte.setText("Acomptes");
			}
			{
				la2 = new Label(groupPrincipal, SWT.NONE);
				GridData la2LData = new GridData();
				la2.setLayoutData(la2LData);
			}
			{
				cbReglementSimple = new Button(groupPrincipal, SWT.CHECK | SWT.LEFT);
				GridData button1LData = new GridData();
				cbReglementSimple.setLayoutData(button1LData);
				cbReglementSimple.setText("Réglement simple");
			}
			{
				la3 = new Label(groupPrincipal, SWT.NONE);
				GridData la3LData = new GridData();
				la3.setLayoutData(la3LData);
			}
			{
				cbRemise = new Button(groupPrincipal, SWT.CHECK | SWT.LEFT);
				GridData cbRemiseLData = new GridData();
				cbRemise.setLayoutData(cbRemiseLData);
				cbRemise.setText("Remise");
			}
			{
				groupReglementLie = new Group(groupPrincipal, SWT.NONE);
				GridLayout group1LayoutRelation = new GridLayout();
				group1LayoutRelation.numColumns =2;
				groupReglementLie.setLayout(group1LayoutRelation);
				GridData groupRelationLData = new GridData();
				groupRelationLData.verticalAlignment = GridData.FILL;
				groupRelationLData.horizontalAlignment = GridData.FILL;
				groupRelationLData.grabExcessHorizontalSpace = true;
				groupRelationLData.grabExcessVerticalSpace = true;
				groupRelationLData.horizontalSpan = 2;
				groupReglementLie.setLayoutData(groupRelationLData);
				groupReglementLie.setText("Transférer tous les documents non transférés");
				{
					cbReglementLies = new Button(groupReglementLie, SWT.CHECK | SWT.LEFT);
					GridData cbReglementLData = new GridData();
					cbReglementLies.setLayoutData(cbReglementLData);
					cbReglementLies.setText("Transférer les réglements liés au document");
				}
			}
			{
			groupDocumentLie = new Group(groupPrincipal, SWT.NONE);
			GridLayout group1LayoutRelation = new GridLayout();
			group1LayoutRelation.numColumns =2;
			groupDocumentLie.setLayout(group1LayoutRelation);
			GridData groupRelationLData = new GridData();
			groupRelationLData.verticalAlignment = GridData.FILL;
			groupRelationLData.horizontalAlignment = GridData.FILL;
			groupRelationLData.grabExcessHorizontalSpace = true;
			groupRelationLData.grabExcessVerticalSpace = true;
			groupRelationLData.horizontalSpan = 2;
			groupDocumentLie.setLayoutData(groupRelationLData);
			groupDocumentLie.setText("Transfert réglements");
			groupDocumentLie.setVisible(false);
			groupReglementLie.setText("Transfert factures et avoirs");
			groupReglementLie.setVisible(false);
			{
				cbTransDocLies = new Button(groupDocumentLie, SWT.CHECK | SWT.LEFT);
				GridData cbTransDocLiesLData = new GridData();
				cbTransDocLies.setLayoutData(cbTransDocLiesLData);
				cbTransDocLies.setText("Transférer les documents liés au réglement");
			}
			}
			{
				groupPointage = new Group(groupPrincipal, SWT.NONE);
				GridLayout group1LayoutRelation = new GridLayout();
				group1LayoutRelation.numColumns =2;
				groupPointage.setLayout(group1LayoutRelation);
				GridData groupRelationLData = new GridData();
				groupRelationLData.verticalAlignment = GridData.FILL;
				groupRelationLData.horizontalAlignment = GridData.FILL;
				groupRelationLData.grabExcessHorizontalSpace = true;
				groupRelationLData.grabExcessVerticalSpace = true;
				groupRelationLData.horizontalSpan = 2;
				groupPointage.setLayoutData(groupRelationLData);
				groupPointage.setText("Pointages");
				groupPointage.setVisible(false);

			{
				cbTransPointage = new Button(groupPointage, SWT.CHECK | SWT.LEFT);
				GridData cbTransPointageLData = new GridData();
				cbTransPointage.setLayoutData(cbTransPointageLData);
				cbTransPointage.setText("Transférer les pointages");
			}
			}
			thisLayout.numColumns = 2;


			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

	public Group getGroupReglementLie() {
		return groupReglementLie;
	}

	public Group getGroupDocumentLie() {
		return groupDocumentLie;
	}

	public Group getGroupPointage() {
		return groupPointage;
	}
	
	public Button getCbReglementLies() {
		return cbReglementLies;
	}

	
	public Button getCbTransDocLies() {
		return cbTransDocLies;
	}
	
	public Button getCbTransPointage() {
		return cbTransPointage;
	}

	public Group getGroupPrincipal() {
		return groupPrincipal;
	}
	
	public Button getCbAcompte() {
		return cbAcompte;
	}
	
	public Button getButton1() {
		return cbReglementSimple;
	}
	
	public Button getCbRemise() {
		return cbRemise;
	}
	
	public Button getCbTous() {
		return cbTous;
	}

	public Button getCbReglementSimple() {
		return cbReglementSimple;
	}

	public Label getLa3() {
		return la3;
	}

	public Label getLa1() {
		return la1;
	}

	public Label getLa2() {
		return la2;
	}

}

package fr.legrain.articles.ecran;
import com.cloudgarden.resource.SWTResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

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
public class PaMajCodeTvaArticle extends org.eclipse.swt.widgets.Composite {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
	
	private Label laNumDeb;
	private Label laExplication2;
	private Label laExplication;
	private PaBtnReduit paBtn1;
	private Text tfNumFin;
	private Label laNumFin;
	private Text tfNumDeb;
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
		PaMajCodeTvaArticle inst = new PaMajCodeTvaArticle(shell, SWT.NULL);
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

	public PaMajCodeTvaArticle(org.eclipse.swt.widgets.Composite parent, int style) {
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
				composite1LData.widthHint = 557;
				composite1LData.heightHint = 163;
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
					groupSelectionLData.verticalAlignment = GridData.FILL;
					groupSelectionLData.horizontalAlignment = GridData.FILL;
					groupSelectionLData.grabExcessVerticalSpace = true;
					groupSelectionLData.grabExcessHorizontalSpace = true;
					groupSelection.setLayoutData(groupSelectionLData);
					groupSelection.setText("Mise à jour des codes tva dans les articles");
					groupSelection.setFont(SWTResourceManager.getFont("Segoe UI", 9, 1, false, false));
					{
						GridData laExplicationLData = new GridData();
						laExplicationLData.heightHint = 21;
						laExplicationLData.grabExcessHorizontalSpace = true;
						laExplicationLData.horizontalSpan = 4;
						laExplicationLData.widthHint = 441;
						laExplication = new Label(groupSelection, SWT.NONE);
						laExplication.setLayoutData(laExplicationLData);
						laExplication.setText("1) Sélectionnez dans la première zone le code tva actuellement affecté à vos articles.");
					}
					{
						GridData laExplication2LData = new GridData();
						laExplication2LData.heightHint = 35;
						laExplication2LData.horizontalSpan = 4;
						laExplication2LData.grabExcessHorizontalSpace = true;
						laExplication2LData.widthHint = 534;
						laExplication2 = new Label(groupSelection, SWT.NONE);
						laExplication2.setLayoutData(laExplication2LData);
						laExplication2.setText("2) selectionnez dans la deuxième zone, le nouveau code tva que vous souhaité affecter à ces articles");
					}
					{
						laNumDeb = new Label(groupSelection, SWT.NONE);
						GridData laNumDebLData = new GridData();
						laNumDeb.setLayoutData(laNumDebLData);
						laNumDeb.setText("Code tva actuel");
					}
					{
						GridData tfNumDebLData = new GridData();
						tfNumDebLData.horizontalAlignment = GridData.FILL;
						tfNumDebLData.heightHint = 13;
						tfNumDeb = new Text(groupSelection, SWT.BORDER);
						tfNumDeb.setLayoutData(tfNumDebLData);
					}
					{
						laNumFin = new Label(groupSelection, SWT.NONE);
						GridData laNumFinLData = new GridData();
						laNumFin.setLayoutData(laNumFinLData);
						laNumFin.setText("code tva souhaité");
					}
					{
						GridData tfNumFinLData = new GridData();
						tfNumFinLData.horizontalAlignment = GridData.CENTER;
						GridData tfNumFinLData1 = new GridData();
						tfNumFinLData1.widthHint = 74;
						tfNumFinLData1.heightHint = 13;
						tfNumFin = new Text(groupSelection, SWT.BORDER);
						tfNumFin.setLayoutData(tfNumFinLData1);
					}
				}
				{
					GridData paBtn1LData = new GridData();
					paBtn1LData.horizontalAlignment = GridData.CENTER;
					paBtn1LData.grabExcessHorizontalSpace = true;
					paBtn1LData.grabExcessVerticalSpace = true;
					paBtn1LData.horizontalSpan = 2;
					paBtn1LData.verticalAlignment = GridData.BEGINNING;
					paBtn1LData.widthHint = 240;
					paBtn1LData.heightHint = 35;
					paBtn1 = new PaBtnReduit(composite1, SWT.NONE);
					GridLayout paBtn1Layout = new GridLayout();
					paBtn1Layout.numColumns = 2;
					paBtn1.setLayout(paBtn1Layout);
					paBtn1.getBtnImprimer().setText("Mettre à jour [F3]");
					paBtn1.setLayoutData(paBtn1LData);
					GridData btnFermerLData = new GridData();
					btnFermerLData.horizontalAlignment = GridData.FILL;
					btnFermerLData.verticalAlignment = GridData.FILL;
					btnFermerLData.grabExcessVerticalSpace = true;
					btnFermerLData.grabExcessHorizontalSpace = true;
					GridData btnImprimerLData = new GridData();
					btnImprimerLData.grabExcessHorizontalSpace = true;
					btnImprimerLData.horizontalAlignment = GridData.FILL;
					paBtn1.getBtnFermer().setLayoutData(btnFermerLData);
					paBtn1.getBtnImprimer().setLayoutData(btnImprimerLData);
				}
				GridData cbPrintExportLData = new GridData();
				cbPrintExportLData.horizontalSpan = 2;
			}
			this.layout();
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Label getLaNumDeb() {
		return laNumDeb;
	}
	
	public Text getTfNumDeb() {
		return tfNumDeb;
	}
	
	public Label getLaNumFin() {
		return laNumFin;
	}
	
	public Text getTfNumFin() {
		return tfNumFin;
	}
	
	
	
	public PaBtnReduit getPaBtn1() {
		return paBtn1;
	}
	
	

	public void setCbPrintExport(Button cbPrintExport) {
	}
	
	
	
	public Group getGroupSelection() {
		return groupSelection;
	}
	
	public Label getLaExplication() {
		return laExplication;
	}
	
	public Label getLaExplication2() {
		return laExplication2;
	}

}

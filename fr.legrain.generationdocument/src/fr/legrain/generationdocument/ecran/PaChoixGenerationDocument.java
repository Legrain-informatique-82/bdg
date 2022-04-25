package fr.legrain.generationdocument.ecran;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
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
public class PaChoixGenerationDocument extends org.eclipse.swt.widgets.Composite {
	private PaBtnReduit paBtn1;
	private Text tfLibelle;
	private Label laLibelle;
	private Group groupParam;
	private Group groupChoix;
	private Composite composite1;
	private Label laDateDoc;
	private DateTime tfDateDoc;
	private Button btnAucun;
	private Button btnLibelle;
	private Button btnReference;
	private Text tfTiers;
	private Label laTiers;

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
		PaChoixGenerationDocument inst = new PaChoixGenerationDocument(shell, SWT.NULL);
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

	public PaChoixGenerationDocument(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			this.setLayout(thisLayout);
			thisLayout.numColumns = 4;
			thisLayout.horizontalSpacing = 4;
			{
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.grabExcessVerticalSpace = true;
				composite1LData.verticalAlignment = GridData.FILL;
				composite1 = new Composite(this, SWT.BORDER);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.makeColumnsEqualWidth = true;
				composite1.setLayout(composite1Layout);
				composite1.setLayoutData(composite1LData);
				{
					groupChoix = new Group(composite1, SWT.NONE);
					GridLayout groupChoixLayout = new GridLayout();
					groupChoixLayout.makeColumnsEqualWidth = true;
					groupChoix.setLayout(groupChoixLayout);
					GridData groupChoixLData = new GridData();
					groupChoixLData.horizontalAlignment = GridData.FILL;
					groupChoixLData.grabExcessHorizontalSpace = true;
					groupChoixLData.grabExcessVerticalSpace = true;
					groupChoixLData.verticalAlignment = GridData.FILL;
					groupChoix.setLayoutData(groupChoixLData);
					groupChoix.setText("Document à générer");
				}
				{
					groupParam = new Group(composite1, SWT.NONE);
					GridLayout groupParamLayout = new GridLayout();
					groupParamLayout.numColumns = 2;
					groupParam.setLayout(groupParamLayout);
					GridData groupParamLData = new GridData();
					groupParamLData.verticalAlignment = GridData.FILL;
					groupParamLData.horizontalAlignment = GridData.FILL;
					groupParamLData.grabExcessHorizontalSpace = true;
					groupParam.setLayoutData(groupParamLData);
					groupParam.setText("Paramètres");
					{
						laDateDoc = new Label(groupParam, SWT.NONE);
						GridData laDateDebutLData = new GridData();
						laDateDebutLData.widthHint = 104;
						laDateDebutLData.heightHint = 22;
						laDateDoc.setLayoutData(laDateDebutLData);
						laDateDoc.setText("Date document");
					}
					{
						GridData cdatetimeLgr2LData = new GridData();
						cdatetimeLgr2LData.widthHint = 113;
						cdatetimeLgr2LData.heightHint = 23;

						tfDateDoc = new DateTime(groupParam, SWT.BORDER | SWT.DROP_DOWN);
						tfDateDoc.setLayoutData(cdatetimeLgr2LData);
						
					}
					{
						laTiers = new Label(groupParam, SWT.NONE);
						GridData laLibelleLData = new GridData();
						laLibelleLData.widthHint = 63;
						laLibelleLData.heightHint = 13;
						laTiers.setLayoutData(laLibelleLData);
						laTiers.setText("tiers");
					}
					{
						GridData tfLibelleLData = new GridData();
						tfLibelleLData.widthHint = 104;
						tfLibelleLData.heightHint = 14;
						tfTiers = new Text(groupParam, SWT.BORDER);
						tfTiers.setLayoutData(tfLibelleLData);
					}
					{
						laLibelle = new Label(groupParam, SWT.NONE);
						GridData laLibelleLData = new GridData();
						laLibelleLData.widthHint = 63;
						laLibelleLData.heightHint = 13;
						laLibelle.setLayoutData(laLibelleLData);
						laLibelle.setText("Libellé");
					}
					{
						GridData tfLibelleLData = new GridData();
						tfLibelleLData.horizontalAlignment = GridData.FILL;
						tfLibelleLData.grabExcessHorizontalSpace = true;
						tfLibelle = new Text(groupParam, SWT.BORDER);
						tfLibelle.setLayoutData(tfLibelleLData);
					}
					{
						btnReference = new Button(groupParam, SWT.RADIO | SWT.LEFT);
						GridData btnReferenceLData = new GridData();
						btnReferenceLData.horizontalSpan = 2;
						btnReference.setLayoutData(btnReferenceLData);
						btnReference.setText("Reprise du n° du document dans le corps");
					}
					{
						btnLibelle = new Button(groupParam, SWT.RADIO | SWT.LEFT);
						GridData btnLibelleLData = new GridData();
						btnLibelleLData.horizontalSpan = 2;
						btnLibelle.setLayoutData(btnLibelleLData);
						btnLibelle.setText("Reprise du libellé du document dans le corps");
					}
					{
						btnAucun = new Button(groupParam, SWT.RADIO | SWT.LEFT);
						GridData btnAucunLData = new GridData();
						btnAucun.setLayoutData(btnAucunLData);
						btnAucun.setText("Aucun");
					}
				}
				{
					GridData paBtn1LData = new GridData();
					paBtn1LData.horizontalAlignment = GridData.CENTER;
					paBtn1LData.grabExcessHorizontalSpace = true;
					paBtn1 = new PaBtnReduit(composite1, SWT.NONE);
					paBtn1.getBtnImprimer().setText("Valider[F3]"); 
					GridData btnFermerLData = new GridData();
					btnFermerLData.verticalAlignment = GridData.FILL;
					btnFermerLData.horizontalAlignment = GridData.FILL;
					paBtn1.setLayoutData(paBtn1LData);
					GridData btnImprimerLData = new GridData();
					btnImprimerLData.verticalAlignment = GridData.FILL;
					btnImprimerLData.horizontalAlignment = GridData.FILL;
					paBtn1.getBtnFermer().setLayoutData(btnFermerLData);
					paBtn1.getBtnImprimer().setLayoutData(btnImprimerLData);
				}
			}
			this.layout();
			pack();
			this.setSize(421, 235);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	
	public PaBtnReduit getPaBtn1() {
		return paBtn1;
	}
	
	public Group getGroupChoix() {
		return groupChoix;
	}
	
	public Group getGroupParam() {
		return groupParam;
	}
	
	public Label getLaLibelle() {
		return laLibelle;
	}
	
	public Text getTfLibelle() {
		return tfLibelle;
	}

	public Composite getComposite1() {
		return composite1;
	}

	public Label getLaDateDoc() {
		return laDateDoc;
	}

	public DateTime getTfDateDoc() {
		return tfDateDoc;
	}
	
	public Button getBtnReference() {
		return btnReference;
	}
	
	public Button getBtnLibelle() {
		return btnLibelle;
	}

	public Text getTfTiers() {
		return tfTiers;
	}

	public Label getLaTiers() {
		return laTiers;
	}
	
	public Button getBtnAucun() {
		return btnAucun;
	}

}

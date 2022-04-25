package fr.legrain.tiers.ecran;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.fieldassist.TextControlCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.resource.SWTResourceManager;

import fr.legrain.libMessageLGR.LgrMess;

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
public class PaTypeTtvaDocSWT extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
/*

 */
	private Composite paCorpsFormulaire;
	private Label laCODE_T_TVA_DOC;
	private Label laCOMPTE_T_TVA_DOC;
	private Label laLIBELLE_T_TVA_DOC;

	private Text tfCODE_T_TVA_DOC;
	private Text tfCOMPTE_T_TVA_DOC;
	private Text tfLIBELLE_T_TVA_DOC;

	
	private DecoratedField fieldCODE_T_TVA_DOC;
	private DecoratedField fieldCOMPTE_T_TVA_DOC;
	private DecoratedField fieldLIBELLE_T_TVA_DOC;
	
	final private boolean decore = LgrMess.isDECORE();

	/**
	* Auto-generated main method to display this 
	* fr.legrain.lib.gui.DefaultFrameFormulaireSWT inside a new Shell.
	*/
	public static void main(String[] args) {
		showGUI();
	}
		
	/**
	* Auto-generated method to display this 
	* fr.legrain.lib.gui.DefaultFrameFormulaireSWT inside a new Shell.
	*/
	public static void showGUI() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		PaTypeTtvaDocSWT inst = new PaTypeTtvaDocSWT(shell, SWT.NULL);
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

	public PaTypeTtvaDocSWT(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			{
				paCorpsFormulaire = new Composite(
					super.getPaFomulaire(),
					SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 2;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.grabExcessVerticalSpace = true;
				composite1LData.grabExcessHorizontalSpace = true;
				paCorpsFormulaire.setLayoutData(composite1LData);
				paCorpsFormulaire.setLayout(composite1Layout);

				{
					laCODE_T_TVA_DOC = new Label(paCorpsFormulaire, SWT.NONE);
					laCODE_T_TVA_DOC.setText("Code");
				}
				{
					GridData tfCODE_T_TVA_DOCLData = new GridData();
					tfCODE_T_TVA_DOCLData.horizontalAlignment = GridData.FILL;
					//tfCODE_T_TVA_DOCLData.grabExcessHorizontalSpace = true;
					tfCODE_T_TVA_DOCLData.widthHint = 177;
					tfCODE_T_TVA_DOCLData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfCODE_T_TVA_DOC = new Text(paCorpsFormulaire, SWT.BORDER);
						tfCODE_T_TVA_DOC.setLayoutData(tfCODE_T_TVA_DOCLData);
					} else {					
					fieldCODE_T_TVA_DOC = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfCODE_T_TVA_DOC = (Text)fieldCODE_T_TVA_DOC.getControl();
					fieldCODE_T_TVA_DOC.getLayoutControl().setLayoutData(tfCODE_T_TVA_DOCLData);
					}
				}
				{
					laCOMPTE_T_TVA_DOC = new Label(paCorpsFormulaire, SWT.NONE);
					laCOMPTE_T_TVA_DOC.setText("Journal");
				}
				{
					GridData tfCOMPTE_T_TVA_DOCLData = new GridData();
					tfCOMPTE_T_TVA_DOCLData.horizontalAlignment = GridData.FILL;
					if(!decore) {
						tfCOMPTE_T_TVA_DOC = new Text(paCorpsFormulaire, SWT.BORDER);
						tfCOMPTE_T_TVA_DOC.setLayoutData(tfCOMPTE_T_TVA_DOCLData);
					} else {					
					fieldCOMPTE_T_TVA_DOC = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfCOMPTE_T_TVA_DOC = (Text)fieldCOMPTE_T_TVA_DOC.getControl();
					fieldCOMPTE_T_TVA_DOC.getLayoutControl().setLayoutData(tfCOMPTE_T_TVA_DOCLData);
					}
				}
				{
					laLIBELLE_T_TVA_DOC = new Label(paCorpsFormulaire, SWT.NONE);
					laLIBELLE_T_TVA_DOC.setText("Désignation");
				}
				{
					GridData tfLIBELLE_T_TVA_DOCLData = new GridData();
					tfLIBELLE_T_TVA_DOCLData.horizontalAlignment = GridData.FILL;
					if(!decore) {
						tfLIBELLE_T_TVA_DOC = new Text(paCorpsFormulaire, SWT.BORDER);
						tfLIBELLE_T_TVA_DOC.setLayoutData(tfLIBELLE_T_TVA_DOCLData);
					} else {					
					fieldLIBELLE_T_TVA_DOC = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfLIBELLE_T_TVA_DOC = (Text)fieldLIBELLE_T_TVA_DOC.getControl();
					fieldLIBELLE_T_TVA_DOC.getLayoutControl().setLayoutData(tfLIBELLE_T_TVA_DOCLData);
					}
				}
			}
			
			this.setLayout(new GridLayout());
			GridLayout compositeFormLayout = new GridLayout();
			compositeFormLayout.numColumns = 2;
			this.setSize(682, 384);
			GridData paGrilleLData = new GridData();
			paGrilleLData.grabExcessVerticalSpace = true;
			paGrilleLData.verticalAlignment = GridData.FILL;
			paGrilleLData.horizontalAlignment = GridData.FILL;
			paGrilleLData.grabExcessHorizontalSpace = true;
			super.getCompositeForm().setLayout(compositeFormLayout);
			GridData paFomulaireLData = new GridData();
			paFomulaireLData.verticalAlignment = GridData.FILL;
			paFomulaireLData.grabExcessVerticalSpace = true;
			paFomulaireLData.horizontalAlignment = GridData.FILL;
			super.getPaGrille().setLayoutData(paGrilleLData);
			GridData compositeFormLData = new GridData();
			compositeFormLData.verticalAlignment = GridData.FILL;
			compositeFormLData.horizontalAlignment = GridData.FILL;
			compositeFormLData.grabExcessVerticalSpace = true;
			super.getPaFomulaire().setLayoutData(paFomulaireLData);
			GridData grilleLData = new GridData();
			grilleLData.verticalAlignment = GridData.FILL;
			grilleLData.horizontalAlignment = GridData.FILL;
			grilleLData.grabExcessVerticalSpace = true;
			super.getCompositeForm().setLayoutData(compositeFormLData);
			super.getGrille().setLayoutData(grilleLData);
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DecoratedField getFieldCODE_T_TVA_DOC() {
		return fieldCODE_T_TVA_DOC;
	}

	public DecoratedField getFieldCOMPTE_T_TVA_DOC() {
		return fieldCOMPTE_T_TVA_DOC;
	}

	public DecoratedField getFieldLIBELLE_T_TVA_DOC() {
		return fieldLIBELLE_T_TVA_DOC;
	}

	public Label getLaCODE_T_TVA_DOC() {
		return laCODE_T_TVA_DOC;
	}

	public Label getLaCOMPTE_T_TVA_DOC() {
		return laCOMPTE_T_TVA_DOC;
	}

	public Label getLaLIBELLE_T_TVA_DOC() {
		return laLIBELLE_T_TVA_DOC;
	}

	public Composite getPaCorpsFormulaire() {
		return paCorpsFormulaire;
	}

	public Text getTfCODE_T_TVA_DOC() {
		return tfCODE_T_TVA_DOC;
	}

	public Text getTfCOMPTE_T_TVA_DOC() {
		return tfCOMPTE_T_TVA_DOC;
	}

	public Text getTfLIBELLE_T_TVA_DOC() {
		return tfLIBELLE_T_TVA_DOC;
	}

	public void setFieldCODE_T_TVA_DOC(DecoratedField fieldCODE_T_TVA_DOC) {
		this.fieldCODE_T_TVA_DOC = fieldCODE_T_TVA_DOC;
	}

	public void setFieldCOMPTE_T_TVA_DOC(DecoratedField fieldCOMPTE_T_TVA_DOC) {
		this.fieldCOMPTE_T_TVA_DOC = fieldCOMPTE_T_TVA_DOC;
	}

	public void setFieldLIBELLE_T_TVA_DOC(DecoratedField fieldLIBELLE_T_TVA_DOC) {
		this.fieldLIBELLE_T_TVA_DOC = fieldLIBELLE_T_TVA_DOC;
	}

	public void setLaCODE_T_TVA_DOC(Label laCODE_T_TVA_DOC) {
		this.laCODE_T_TVA_DOC = laCODE_T_TVA_DOC;
	}

	public void setLaCOMPTE_T_TVA_DOC(Label laCOMPTE_T_TVA_DOC) {
		this.laCOMPTE_T_TVA_DOC = laCOMPTE_T_TVA_DOC;
	}

	public void setLaLIBELLE_T_TVA_DOC(Label laLIBELLE_T_TVA_DOC) {
		this.laLIBELLE_T_TVA_DOC = laLIBELLE_T_TVA_DOC;
	}

	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public void setTfCODE_T_TVA_DOC(Text tfCODE_T_TVA_DOC) {
		this.tfCODE_T_TVA_DOC = tfCODE_T_TVA_DOC;
	}

	public void setTfCOMPTE_T_TVA_DOC(Text tfCOMPTE_T_TVA_DOC) {
		this.tfCOMPTE_T_TVA_DOC = tfCOMPTE_T_TVA_DOC;
	}

	public void setTfLIBELLE_T_TVA_DOC(Text tfLIBELLE_T_TVA_DOC) {
		this.tfLIBELLE_T_TVA_DOC = tfLIBELLE_T_TVA_DOC;
	}


}

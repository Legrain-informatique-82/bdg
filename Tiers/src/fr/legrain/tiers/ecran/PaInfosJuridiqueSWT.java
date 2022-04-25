package fr.legrain.tiers.ecran;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.fieldassist.TextControlCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
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
public class PaInfosJuridiqueSWT extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
/*

 */
	private Composite paCorpsFormulaire;
	private Label laRCS_INFO_ENTREPRISE;
	private Label laCAPITAL_INFO_ENTREPRISE;
	private Label laAPE_INFO_ENTREPRISE;
	private Label laSIRET_INFO_ENTREPRISE;

	private Text tfCAPITAL_INFO_ENTREPRISE;
	private Text tfRCS_INFO_ENTREPRISE;
	private Text tfAPE_INFO_ENTREPRISE;
	private Text tfSIRET_INFO_ENTREPRISE;
	
	private DecoratedField fieldCAPITAL_INFO_ENTREPRISE;
    private DecoratedField fieldRCS_INFO_ENTREPRISE;
	private DecoratedField fieldAPE_INFO_ENTREPRISE;	
	private DecoratedField fieldSIRET_INFO_ENTREPRISE;
	
	final private boolean decore = LgrMess.isDECORE();
//	final private boolean decore = false;
	
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
		PaInfosJuridiqueSWT inst = new PaInfosJuridiqueSWT(shell, SWT.NULL);
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

	public PaInfosJuridiqueSWT(org.eclipse.swt.widgets.Composite parent, int style) {
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
				composite1LData.horizontalSpan = 3;
				composite1LData.grabExcessHorizontalSpace = true;
				paCorpsFormulaire.setLayoutData(composite1LData);
				paCorpsFormulaire.setLayout(composite1Layout);

				{
					laRCS_INFO_ENTREPRISE = new Label(paCorpsFormulaire, SWT.NONE);
					laRCS_INFO_ENTREPRISE.setText("RCS");
				}
				{
					GridData tfADRESSE2_ADRESSELData = new GridData();
					tfADRESSE2_ADRESSELData.horizontalAlignment = GridData.FILL;
					if(!decore) {
						tfRCS_INFO_ENTREPRISE = new Text(paCorpsFormulaire, SWT.BORDER);
						tfRCS_INFO_ENTREPRISE.setLayoutData(tfADRESSE2_ADRESSELData);
					} else {					
					fieldRCS_INFO_ENTREPRISE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfRCS_INFO_ENTREPRISE = (Text)fieldRCS_INFO_ENTREPRISE.getControl();
					fieldRCS_INFO_ENTREPRISE.getLayoutControl().setLayoutData(tfADRESSE2_ADRESSELData);
					}
				}
				{
					laCAPITAL_INFO_ENTREPRISE = new Label(paCorpsFormulaire, SWT.NONE);
					laCAPITAL_INFO_ENTREPRISE.setText("Capital");
				}
				{
					GridData tfADRESSE1_ADRESSELData = new GridData();
					tfADRESSE1_ADRESSELData.horizontalAlignment = GridData.CENTER;
					tfADRESSE1_ADRESSELData.widthHint = 156;
					tfADRESSE1_ADRESSELData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfCAPITAL_INFO_ENTREPRISE = new Text(paCorpsFormulaire, SWT.BORDER);
						tfCAPITAL_INFO_ENTREPRISE.setLayoutData(tfADRESSE1_ADRESSELData);
					} else {
						fieldCAPITAL_INFO_ENTREPRISE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
						tfCAPITAL_INFO_ENTREPRISE = (Text)fieldCAPITAL_INFO_ENTREPRISE.getControl();
						fieldCAPITAL_INFO_ENTREPRISE.getLayoutControl().setLayoutData(tfADRESSE1_ADRESSELData);
					}					
				}
				{
					laAPE_INFO_ENTREPRISE = new Label(paCorpsFormulaire, SWT.NONE);
					laAPE_INFO_ENTREPRISE.setText("Code APE");
				}
				{
					GridData tfADRESSE3_ADRESSELData = new GridData();
					//tfADRESSE3_ADRESSELData.horizontalAlignment = GridData.FILL;
					if(!decore) {
						tfAPE_INFO_ENTREPRISE = new Text(paCorpsFormulaire, SWT.BORDER);
						tfAPE_INFO_ENTREPRISE.setLayoutData(tfADRESSE3_ADRESSELData);
					} else {					
					fieldAPE_INFO_ENTREPRISE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfAPE_INFO_ENTREPRISE = (Text)fieldAPE_INFO_ENTREPRISE.getControl();
					fieldAPE_INFO_ENTREPRISE.getLayoutControl().setLayoutData(tfADRESSE3_ADRESSELData);
					}
				}
			{
				laSIRET_INFO_ENTREPRISE = new Label(paCorpsFormulaire, SWT.NONE);
				laSIRET_INFO_ENTREPRISE.setText("Siret");
			}
			{
				GridData tfCODEPOSTAL_ADRESSELData = new GridData();
				tfCODEPOSTAL_ADRESSELData.horizontalAlignment = GridData.FILL;
				if(!decore) {
					tfSIRET_INFO_ENTREPRISE = new Text(paCorpsFormulaire, SWT.BORDER);
					//tfCODEPOSTAL_ADRESSE.setLayoutData(tfCODEPOSTAL_ADRESSELData);
				} else {					
				fieldSIRET_INFO_ENTREPRISE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
				tfSIRET_INFO_ENTREPRISE = (Text)fieldSIRET_INFO_ENTREPRISE.getControl();
				fieldSIRET_INFO_ENTREPRISE.getLayoutControl().setLayoutData(tfCODEPOSTAL_ADRESSELData);
				}
			}

			}
			
			this.setLayout(new GridLayout());
			GridLayout compositeFormLayout = new GridLayout();
			compositeFormLayout.numColumns = 2;
			this.setSize(682, 384);
			GridData paGrilleLData = new GridData();
			paGrilleLData.verticalAlignment = GridData.FILL;
			paGrilleLData.horizontalAlignment = GridData.FILL;
			paGrilleLData.grabExcessHorizontalSpace = true;
			paGrilleLData.grabExcessVerticalSpace = true;
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

	public Composite getPaCorpsFormulaire() {
		return paCorpsFormulaire;
	}

	public Label getLaRCS_INFO_ENTREPRISE() {
		return laRCS_INFO_ENTREPRISE;
	}

	public Label getLaCAPITAL_INFO_ENTREPRISE() {
		return laCAPITAL_INFO_ENTREPRISE;
	}

	public Label getLaAPE_INFO_ENTREPRISE() {
		return laAPE_INFO_ENTREPRISE;
	}

	public Label getLaSIRET_INFO_ENTREPRISE() {
		return laSIRET_INFO_ENTREPRISE;
	}

	public Text getTfCAPITAL_INFO_ENTREPRISE() {
		return tfCAPITAL_INFO_ENTREPRISE;
	}

	public Text getTfRCS_INFO_ENTREPRISE() {
		return tfRCS_INFO_ENTREPRISE;
	}

	public Text getTfAPE_INFO_ENTREPRISE() {
		return tfAPE_INFO_ENTREPRISE;
	}

	public Text getTfSIRET_INFO_ENTREPRISE() {
		return tfSIRET_INFO_ENTREPRISE;
	}

	public DecoratedField getFieldCAPITAL_INFO_ENTREPRISE() {
		return fieldCAPITAL_INFO_ENTREPRISE;
	}

	public DecoratedField getFieldRCS_INFO_ENTREPRISE() {
		return fieldRCS_INFO_ENTREPRISE;
	}

	public DecoratedField getFieldAPE_INFO_ENTREPRISE() {
		return fieldAPE_INFO_ENTREPRISE;
	}

	public DecoratedField getFieldSIRET_INFO_ENTREPRISE() {
		return fieldSIRET_INFO_ENTREPRISE;
	}

	

}

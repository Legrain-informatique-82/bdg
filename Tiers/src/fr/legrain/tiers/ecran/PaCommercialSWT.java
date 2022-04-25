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

import fr.legrain.lib.gui.fieldassist.ButtonControlCreator;
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
public class PaCommercialSWT extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	private Composite paCorpsFormulaire;
	private Label laCOMMERCIAL;
	private Label laDefaut;

	private Text tfCOMMERCIAL;
	
	private Button cbDefaut;

	private DecoratedField fieldCOMMERCIAL;
	private DecoratedField fieldDefaut;
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
		PaCommercialSWT inst = new PaCommercialSWT(shell, SWT.NULL);
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

	public PaCommercialSWT(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			{
				paCorpsFormulaire = new Composite(super.getPaFomulaire(), SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 2;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.grabExcessVerticalSpace = true;
				paCorpsFormulaire.setLayoutData(composite1LData);
				paCorpsFormulaire.setLayout(composite1Layout);

				{
					laCOMMERCIAL = new Label(paCorpsFormulaire, SWT.NONE);
					laCOMMERCIAL.setText("Code");
				}
				{
					GridData tfCOMMERCIALLData = new GridData();
					tfCOMMERCIALLData.horizontalAlignment = GridData.CENTER;
					tfCOMMERCIALLData.widthHint = 188;
					tfCOMMERCIALLData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfCOMMERCIAL = new Text(paCorpsFormulaire, SWT.BORDER);
						tfCOMMERCIAL.setLayoutData(tfCOMMERCIALLData);
					} else {					
					fieldCOMMERCIAL = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfCOMMERCIAL = (Text)fieldCOMMERCIAL.getControl();
					fieldCOMMERCIAL.getLayoutControl().setLayoutData(tfCOMMERCIALLData);
					}
				}
				
				{
					laDefaut = new Label(paCorpsFormulaire, SWT.NONE);
					laDefaut.setText("Commercial de référence");
				}
				{
					GridData cbID_REF_PRIXLData = new GridData();
					cbID_REF_PRIXLData.widthHint = 22;
					if(!decore) {
						cbDefaut = new Button(paCorpsFormulaire, SWT.CHECK | SWT.LEFT);
						cbDefaut.setLayoutData(cbID_REF_PRIXLData);
					} else {					
					fieldDefaut = new DecoratedField(paCorpsFormulaire, SWT.CHECK | SWT.LEFT, new ButtonControlCreator());
					cbDefaut = (Button)fieldDefaut.getControl();
					fieldDefaut.getLayoutControl().setLayoutData(cbID_REF_PRIXLData);
					}
				}

//				paCorpsFormulaire
//					.setTabList(new org.eclipse.swt.widgets.Control[] {
//							tfLIBELLE_T_TIERS, tfCOMMERCIAL, tfCODE_T_TIERS,
//							tfPRENOM_TIERS, tfSURNOM_TIERS, composite2,
//							tfCOMMERCIAL, tfCODE_ENTREPRISE, tfCODE_T_TIERS, tfCOMPTE,
//							tfLIBL_COMMENTAIRE, tfCODE_BANQUE, tfEntite, composite3 });
			}
			
			this.setLayout(new GridLayout());
			GridLayout compositeFormLayout = new GridLayout();
			compositeFormLayout.numColumns = 2;
			this.setSize(646, 334);
			GridData paGrilleLData = new GridData();
			paGrilleLData.grabExcessHorizontalSpace = true;
			paGrilleLData.horizontalAlignment = GridData.FILL;
			paGrilleLData.verticalAlignment = GridData.FILL;
			paGrilleLData.grabExcessVerticalSpace = true;
			super.getCompositeForm().setLayout(compositeFormLayout);
			GridData paFomulaireLData = new GridData();
			paFomulaireLData.verticalAlignment = GridData.FILL;
			paFomulaireLData.grabExcessVerticalSpace = true;
			paFomulaireLData.horizontalAlignment = GridData.FILL;
			super.getPaGrille().setLayoutData(paGrilleLData);
			GridData compositeFormLData = new GridData();
			compositeFormLData.grabExcessHorizontalSpace = true;
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

	public Label getLaCOMMERCIAL() {
		return laCOMMERCIAL;
	}


	public Text getTfCOMMERCIAL() {
		return tfCOMMERCIAL;
	}



	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public DecoratedField getFieldCOMMERCIAL() {
		return fieldCOMMERCIAL;
	}

	public Label getLaDefaut() {
		return laDefaut;
	}

	public Button getCbDefaut() {
		return cbDefaut;
	}

	public DecoratedField getFieldDefaut() {
		return fieldDefaut;
	}



}

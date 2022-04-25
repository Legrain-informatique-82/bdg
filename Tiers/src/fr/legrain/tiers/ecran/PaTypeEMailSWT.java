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
public class PaTypeEMailSWT extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
/*

	private JTextField tfCOMPTE;
	private JLabel laCOMPTE;
	private JLabel laLibTTiers;
	private JTextField tfLibTTiers;
 */
	private Composite paCorpsFormulaire;
	private Label laCODE_T_EMAIL;
	private Label laLIBL_T_EMAIL;

	private Text tfCODE_T_EMAIL;
	private Text tfLIBL_T_EMAIL;

	
	private DecoratedField fieldCODE_T_EMAIL;
	private DecoratedField fieldLIBL_T_EMAIL;
	
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
		PaTypeEMailSWT inst = new PaTypeEMailSWT(shell, SWT.NULL);
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

	public PaTypeEMailSWT(org.eclipse.swt.widgets.Composite parent, int style) {
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
					laCODE_T_EMAIL = new Label(paCorpsFormulaire, SWT.NONE);
					laCODE_T_EMAIL.setText("Code");
				}
				{
					GridData tfCODE_T_TIERSLData = new GridData();
					tfCODE_T_TIERSLData.widthHint = 156;
					tfCODE_T_TIERSLData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfCODE_T_EMAIL = new Text(paCorpsFormulaire, SWT.BORDER);
						tfCODE_T_EMAIL.setLayoutData(tfCODE_T_TIERSLData);
					} else {					
					fieldCODE_T_EMAIL = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfCODE_T_EMAIL = (Text)fieldCODE_T_EMAIL.getControl();
					fieldCODE_T_EMAIL.getLayoutControl().setLayoutData(tfCODE_T_TIERSLData);
					}
				}
				{
					laLIBL_T_EMAIL = new Label(paCorpsFormulaire, SWT.NONE);
					laLIBL_T_EMAIL.setText("Libelle");
				}
				{
					GridData tfCOMPTE_T_TIERSLData = new GridData();
					tfCOMPTE_T_TIERSLData.widthHint = 156;
					tfCOMPTE_T_TIERSLData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfLIBL_T_EMAIL = new Text(paCorpsFormulaire, SWT.BORDER);
						tfLIBL_T_EMAIL.setLayoutData(tfCOMPTE_T_TIERSLData);
					} else {					
					fieldLIBL_T_EMAIL = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfLIBL_T_EMAIL = (Text)fieldLIBL_T_EMAIL.getControl();
					fieldLIBL_T_EMAIL.getLayoutControl().setLayoutData(tfCOMPTE_T_TIERSLData);
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

	public DecoratedField getFieldCODE_T_EMAIL() {
		return fieldCODE_T_EMAIL;
	}

	public DecoratedField getFieldLIBL_T_EMAIL() {
		return fieldLIBL_T_EMAIL;
	}

	public Label getLaCODE_T_EMAIL() {
		return laCODE_T_EMAIL;
	}

	public Label getLaLIBL_T_EMAIL() {
		return laLIBL_T_EMAIL;
	}

	public Composite getPaCorpsFormulaire() {
		return paCorpsFormulaire;
	}

	public Text getTfCODE_T_EMAIL() {
		return tfCODE_T_EMAIL;
	}

	public Text getTfLIBL_T_EMAIL() {
		return tfLIBL_T_EMAIL;
	}

	public void setFieldCODE_T_EMAIL(DecoratedField fieldCODE_T_TIERS) {
		this.fieldCODE_T_EMAIL = fieldCODE_T_TIERS;
	}

	public void setFieldLIBL_T_EMAIL(DecoratedField fieldCOMPTE_T_TIERS) {
		this.fieldLIBL_T_EMAIL = fieldCOMPTE_T_TIERS;
	}
	
	public void setLaCODE_T_EMAIL(Label laCODE_T_TIERS) {
		this.laCODE_T_EMAIL = laCODE_T_TIERS;
	}

	public void setLaLIBL_T_EMAIL(Label laCOMPTE_T_TIERS) {
		this.laLIBL_T_EMAIL = laCOMPTE_T_TIERS;
	}

	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public void setTfCODE_T_EMAIL(Text tfCODE_T_TIERS) {
		this.tfCODE_T_EMAIL = tfCODE_T_TIERS;
	}

	public void setTfLIBL_T_EMAIL(Text tfCOMPTE_T_TIERS) {
		this.tfLIBL_T_EMAIL = tfCOMPTE_T_TIERS;
	}

}

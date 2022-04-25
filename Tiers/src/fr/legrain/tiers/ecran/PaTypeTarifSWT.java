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
public class PaTypeTarifSWT extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

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
	private Label laCODE_T_TARIF;
	private Label laLIBL_T_TARIF;

	private Text tfCODE_T_TARIF;
	private Text tfLIBL_T_TARIF;

	
	private DecoratedField fieldCODE_T_TARIF;
	private DecoratedField fieldLIBL_T_TARIF;
	
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
		PaTypeTarifSWT inst = new PaTypeTarifSWT(shell, SWT.NULL);
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

	public PaTypeTarifSWT(org.eclipse.swt.widgets.Composite parent, int style) {
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
					laCODE_T_TARIF = new Label(paCorpsFormulaire, SWT.NONE);
					laCODE_T_TARIF.setText("Code");
				}
				{
					GridData tfCODE_T_TIERSLData = new GridData();
					tfCODE_T_TIERSLData.widthHint = 156;
					tfCODE_T_TIERSLData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfCODE_T_TARIF = new Text(paCorpsFormulaire, SWT.BORDER);
						tfCODE_T_TARIF.setLayoutData(tfCODE_T_TIERSLData);
					} else {					
					fieldCODE_T_TARIF = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfCODE_T_TARIF = (Text)fieldCODE_T_TARIF.getControl();
					fieldCODE_T_TARIF.getLayoutControl().setLayoutData(tfCODE_T_TIERSLData);
					}
				}
				{
					laLIBL_T_TARIF = new Label(paCorpsFormulaire, SWT.NONE);
					laLIBL_T_TARIF.setText("Libelle");
				}
				{
					GridData tfCOMPTE_T_TIERSLData = new GridData();
					tfCOMPTE_T_TIERSLData.widthHint = 156;
					tfCOMPTE_T_TIERSLData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfLIBL_T_TARIF = new Text(paCorpsFormulaire, SWT.BORDER);
						tfLIBL_T_TARIF.setLayoutData(tfCOMPTE_T_TIERSLData);
					} else {					
					fieldLIBL_T_TARIF = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfLIBL_T_TARIF = (Text)fieldLIBL_T_TARIF.getControl();
					fieldLIBL_T_TARIF.getLayoutControl().setLayoutData(tfCOMPTE_T_TIERSLData);
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

	public DecoratedField getFieldCODE_T_TARIF() {
		return fieldCODE_T_TARIF;
	}

	public DecoratedField getFieldLIBL_T_TARIF() {
		return fieldLIBL_T_TARIF;
	}

	public Label getLaCODE_T_TARIF() {
		return laCODE_T_TARIF;
	}

	public Label getLaLIBL_T_TARIF() {
		return laLIBL_T_TARIF;
	}

	public Composite getPaCorpsFormulaire() {
		return paCorpsFormulaire;
	}

	public Text getTfCODE_T_TARIF() {
		return tfCODE_T_TARIF;
	}

	public Text getTfLIBL_T_TARIF() {
		return tfLIBL_T_TARIF;
	}

	public void setFieldCODE_T_TARIF(DecoratedField fieldCODE_T_TIERS) {
		this.fieldCODE_T_TARIF = fieldCODE_T_TIERS;
	}

	public void setFieldLIBL_T_TARIF(DecoratedField fieldCOMPTE_T_TIERS) {
		this.fieldLIBL_T_TARIF = fieldCOMPTE_T_TIERS;
	}
	
	public void setLaCODE_T_TARIF(Label laCODE_T_TIERS) {
		this.laCODE_T_TARIF = laCODE_T_TIERS;
	}

	public void setLaLIBL_T_TARIF(Label laCOMPTE_T_TIERS) {
		this.laLIBL_T_TARIF = laCOMPTE_T_TIERS;
	}

	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public void setTfCODE_T_TARIF(Text tfCODE_T_TIERS) {
		this.tfCODE_T_TARIF = tfCODE_T_TIERS;
	}

	public void setTfLIBL_T_TARIF(Text tfCOMPTE_T_TIERS) {
		this.tfLIBL_T_TARIF = tfCOMPTE_T_TIERS;
	}

}

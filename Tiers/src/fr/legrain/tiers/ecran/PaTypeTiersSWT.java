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
public class PaTypeTiersSWT extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

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
	private Label laCODE_T_TIERS;
	private Label laCOMPTE_T_TIERS;
	private Label laLIBELLE_T_TIERS;

	private Text tfCODE_T_TIERS;
	private Text tfCOMPTE_T_TIERS;
	private Text tfLIBELLE_T_TIERS;

	
	private DecoratedField fieldCODE_T_TIERS;
	private DecoratedField fieldCOMPTE_T_TIERS;
	private DecoratedField fieldLIBELLE_T_TIERS;
	
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
		PaTypeTiersSWT inst = new PaTypeTiersSWT(shell, SWT.NULL);
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

	public PaTypeTiersSWT(org.eclipse.swt.widgets.Composite parent, int style) {
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
					laCODE_T_TIERS = new Label(paCorpsFormulaire, SWT.NONE);
					laCODE_T_TIERS.setText("Code");
				}
				{
					GridData tfCODE_T_TIERSLData = new GridData();
					tfCODE_T_TIERSLData.horizontalAlignment = GridData.FILL;
					//tfCODE_T_TIERSLData.grabExcessHorizontalSpace = true;
					tfCODE_T_TIERSLData.widthHint = 177;
					tfCODE_T_TIERSLData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfCODE_T_TIERS = new Text(paCorpsFormulaire, SWT.BORDER);
						tfCODE_T_TIERS.setLayoutData(tfCODE_T_TIERSLData);
					} else {					
					fieldCODE_T_TIERS = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfCODE_T_TIERS = (Text)fieldCODE_T_TIERS.getControl();
					fieldCODE_T_TIERS.getLayoutControl().setLayoutData(tfCODE_T_TIERSLData);
					}
				}
				{
					laCOMPTE_T_TIERS = new Label(paCorpsFormulaire, SWT.NONE);
					laCOMPTE_T_TIERS.setText("Compte");
				}
				{
					GridData tfCOMPTE_T_TIERSLData = new GridData();
					tfCOMPTE_T_TIERSLData.horizontalAlignment = GridData.FILL;
					if(!decore) {
						tfCOMPTE_T_TIERS = new Text(paCorpsFormulaire, SWT.BORDER);
						tfCOMPTE_T_TIERS.setLayoutData(tfCOMPTE_T_TIERSLData);
					} else {					
					fieldCOMPTE_T_TIERS = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfCOMPTE_T_TIERS = (Text)fieldCOMPTE_T_TIERS.getControl();
					fieldCOMPTE_T_TIERS.getLayoutControl().setLayoutData(tfCOMPTE_T_TIERSLData);
					}
				}
				{
					laLIBELLE_T_TIERS = new Label(paCorpsFormulaire, SWT.NONE);
					laLIBELLE_T_TIERS.setText("Désignation");
				}
				{
					GridData tfLIBELLE_T_TIERSLData = new GridData();
					tfLIBELLE_T_TIERSLData.horizontalAlignment = GridData.FILL;
					if(!decore) {
						tfLIBELLE_T_TIERS = new Text(paCorpsFormulaire, SWT.BORDER);
						tfLIBELLE_T_TIERS.setLayoutData(tfLIBELLE_T_TIERSLData);
					} else {					
					fieldLIBELLE_T_TIERS = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfLIBELLE_T_TIERS = (Text)fieldLIBELLE_T_TIERS.getControl();
					fieldLIBELLE_T_TIERS.getLayoutControl().setLayoutData(tfLIBELLE_T_TIERSLData);
					}
				}
//				paCorpsFormulaire
//					.setTabList(new org.eclipse.swt.widgets.Control[] {
//							tfCODE_TIERS, tfCODE_COMPTA, tfNOM_TIERS,
//							tfPRENOM_TIERS, tfSURNOM_TIERS, composite2,
//							tfCODE_T_CIVILITE, tfCODE_ENTREPRISE, tfCODE_T_TIERS, tfCOMPTE,
//							tfLIBL_COMMENTAIRE, tfCODE_BANQUE, tfEntite, composite3 });
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

	public DecoratedField getFieldCODE_T_TIERS() {
		return fieldCODE_T_TIERS;
	}

	public DecoratedField getFieldCOMPTE_T_TIERS() {
		return fieldCOMPTE_T_TIERS;
	}

	public DecoratedField getFieldLIBELLE_T_TIERS() {
		return fieldLIBELLE_T_TIERS;
	}

	public Label getLaCODE_T_TIERS() {
		return laCODE_T_TIERS;
	}

	public Label getLaCOMPTE_T_TIERS() {
		return laCOMPTE_T_TIERS;
	}

	public Label getLaLIBELLE_T_TIERS() {
		return laLIBELLE_T_TIERS;
	}

	public Composite getPaCorpsFormulaire() {
		return paCorpsFormulaire;
	}

	public Text getTfCODE_T_TIERS() {
		return tfCODE_T_TIERS;
	}

	public Text getTfCOMPTE_T_TIERS() {
		return tfCOMPTE_T_TIERS;
	}

	public Text getTfLIBELLE_T_TIERS() {
		return tfLIBELLE_T_TIERS;
	}

	public void setFieldCODE_T_TIERS(DecoratedField fieldCODE_T_TIERS) {
		this.fieldCODE_T_TIERS = fieldCODE_T_TIERS;
	}

	public void setFieldCOMPTE_T_TIERS(DecoratedField fieldCOMPTE_T_TIERS) {
		this.fieldCOMPTE_T_TIERS = fieldCOMPTE_T_TIERS;
	}

	public void setFieldLIBELLE_T_TIERS(DecoratedField fieldLIBELLE_T_TIERS) {
		this.fieldLIBELLE_T_TIERS = fieldLIBELLE_T_TIERS;
	}

	public void setLaCODE_T_TIERS(Label laCODE_T_TIERS) {
		this.laCODE_T_TIERS = laCODE_T_TIERS;
	}

	public void setLaCOMPTE_T_TIERS(Label laCOMPTE_T_TIERS) {
		this.laCOMPTE_T_TIERS = laCOMPTE_T_TIERS;
	}

	public void setLaLIBELLE_T_TIERS(Label laLIBELLE_T_TIERS) {
		this.laLIBELLE_T_TIERS = laLIBELLE_T_TIERS;
	}

	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public void setTfCODE_T_TIERS(Text tfCODE_T_TIERS) {
		this.tfCODE_T_TIERS = tfCODE_T_TIERS;
	}

	public void setTfCOMPTE_T_TIERS(Text tfCOMPTE_T_TIERS) {
		this.tfCOMPTE_T_TIERS = tfCOMPTE_T_TIERS;
	}

	public void setTfLIBELLE_T_TIERS(Text tfLIBELLE_T_TIERS) {
		this.tfLIBELLE_T_TIERS = tfLIBELLE_T_TIERS;
	}


}

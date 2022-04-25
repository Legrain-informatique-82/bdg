package fr.legrain.tiers.ecran;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.fieldassist.TextControlCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormData;
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
public class PaTypeLiensSWT extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	private Composite paCorpsFormulaire;
	private Label laCODE_T_LIENS;
	private Label laLabelle_T_LIENS;

	private Text tfCODE_T_LIENS;
	private Text tfLABELLE_T_LIENS;

	private DecoratedField fieldCODE_T_LIENS;
	private DecoratedField fieldLABELLE_T_LIENS;
	
	final private boolean decore = LgrMess.isDECORE();
	//final private boolean decore = false;

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
		PaTypeLiensSWT inst = new PaTypeLiensSWT(shell, SWT.NULL);
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

	public PaTypeLiensSWT(org.eclipse.swt.widgets.Composite parent, int style) {
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
					laCODE_T_LIENS = new Label(paCorpsFormulaire, SWT.NONE);
					laCODE_T_LIENS.setText("Code lien :");
				}
				{
					GridData tfCODE_T_LIENSData = new GridData();
					tfCODE_T_LIENSData.horizontalAlignment = GridData.CENTER;
					tfCODE_T_LIENSData.widthHint = 188;
					tfCODE_T_LIENSData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfCODE_T_LIENS = new Text(paCorpsFormulaire, SWT.BORDER);
						tfCODE_T_LIENS.setLayoutData(tfCODE_T_LIENSData);
					} else {					
					fieldCODE_T_LIENS = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfCODE_T_LIENS = (Text)fieldCODE_T_LIENS.getControl();
					fieldCODE_T_LIENS.getLayoutControl().setLayoutData(tfCODE_T_LIENSData);
					}
				}
				{
					laLabelle_T_LIENS = new Label(paCorpsFormulaire, SWT.NONE);
					laLabelle_T_LIENS.setText("Libelle lien :");
				}
				{
					GridData tfLABELLE_T_LiensData = new GridData();
					tfLABELLE_T_LiensData.horizontalAlignment = GridData.CENTER;
					tfLABELLE_T_LiensData.widthHint = 188;
					tfLABELLE_T_LiensData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfLABELLE_T_LIENS = new Text(paCorpsFormulaire, SWT.BORDER);
						tfLABELLE_T_LIENS.setLayoutData(tfLABELLE_T_LiensData);
					} else {					
					fieldLABELLE_T_LIENS = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfLABELLE_T_LIENS = (Text)fieldLABELLE_T_LIENS.getControl();
					fieldLABELLE_T_LIENS.getLayoutControl().setLayoutData(tfLABELLE_T_LiensData);
					}
				}

//				paCorpsFormulaire
//					.setTabList(new org.eclipse.swt.widgets.Control[] {
//							tfLIBELLE_T_TIERS, tfCODE_T_CIVILITE, tfCODE_T_TIERS,
//							tfPRENOM_TIERS, tfSURNOM_TIERS, composite2,
//							tfCODE_T_CIVILITE, tfCODE_ENTREPRISE, tfCODE_T_TIERS, tfCOMPTE,
//							tfLIBL_COMMENTAIRE, tfCODE_BANQUE, tfEntite, composite3 });
			}
			
			this.setLayout(new GridLayout());
			GridLayout compositeFormLayout = new GridLayout();
			compositeFormLayout.numColumns = 2;
			this.setSize(913, 439);
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


	public Label getLaCODE_T_LIENS() {
		return laCODE_T_LIENS;
	}

	public void setLaCODE_T_LIENS(Label laCODE_T_LIENS) {
		this.laCODE_T_LIENS = laCODE_T_LIENS;
	}
	
	public Text getTfCODE_T_LIENS() {
		return tfCODE_T_LIENS;
	}

	public void setTfCODE_T_LIENS(Text tfCODE_T_LIENS) {
		this.tfCODE_T_LIENS = tfCODE_T_LIENS;
	}

	
	public DecoratedField getFieldCODE_T_LIENS() {
		return fieldCODE_T_LIENS;
	}

	public void setFieldCODE_T_LIENS(DecoratedField fieldCODE_T_LIENS) {
		this.fieldCODE_T_LIENS = fieldCODE_T_LIENS;
	}

	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public Label getLaLabelle_T_LIENS() {
		return laLabelle_T_LIENS;
	}

	public void setLaLabelle_T_LIENS(Label laLabelle_T_LIENS) {
		this.laLabelle_T_LIENS = laLabelle_T_LIENS;
	}


	public Text getTfLABELLE_T_LIENS() {
		return tfLABELLE_T_LIENS;
	}

	public void setTfLABELLE_T_LIENS(Text tfLABELLE_T_LIENS) {
		this.tfLABELLE_T_LIENS = tfLABELLE_T_LIENS;
	}



	public DecoratedField getFieldLABELLE_T_LIENS() {
		return fieldLABELLE_T_LIENS;
	}

	public void setFieldLABELLE_T_LIENS(DecoratedField fieldLABELLE_T_LIENS) {
		this.fieldLABELLE_T_LIENS = fieldLABELLE_T_LIENS;
	}

	public boolean isDecore() {
		return decore;
	}
	


}

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
public class PaTypeWebSWT extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	private Composite paCorpsFormulaire;
	private Label laCODE_T_WEB;
	private Label laLabelle_T_WEB;

	private Text tfCODE_T_WEB;
	private Text tfLABELLE_T_WEB;

	private DecoratedField fieldCODE_T_WEB;
	private DecoratedField fieldLABELLE_T_WEB;
	
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
		PaTypeWebSWT inst = new PaTypeWebSWT(shell, SWT.NULL);
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

	public PaTypeWebSWT(org.eclipse.swt.widgets.Composite parent, int style) {
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
					laCODE_T_WEB = new Label(paCorpsFormulaire, SWT.NONE);
					laCODE_T_WEB.setText("Code Web");
				}
				{
					GridData tfCODE_T_WEBData = new GridData();
					tfCODE_T_WEBData.horizontalAlignment = GridData.CENTER;
					tfCODE_T_WEBData.widthHint = 188;
					tfCODE_T_WEBData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfCODE_T_WEB = new Text(paCorpsFormulaire, SWT.BORDER);
						tfCODE_T_WEB.setLayoutData(tfCODE_T_WEBData);
					} else {					
					fieldCODE_T_WEB = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfCODE_T_WEB = (Text)fieldCODE_T_WEB.getControl();
					fieldCODE_T_WEB.getLayoutControl().setLayoutData(tfCODE_T_WEBData);
					}
				}
				{
					laLabelle_T_WEB = new Label(paCorpsFormulaire, SWT.NONE);
					laLabelle_T_WEB.setText("Libelle Web");
				}
				{
					GridData tfLABELLE_T_WEBData = new GridData();
					tfLABELLE_T_WEBData.horizontalAlignment = GridData.CENTER;
					tfLABELLE_T_WEBData.widthHint = 188;
					tfLABELLE_T_WEBData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfLABELLE_T_WEB = new Text(paCorpsFormulaire, SWT.BORDER);
						tfLABELLE_T_WEB.setLayoutData(tfLABELLE_T_WEBData);
					} else {					
					fieldLABELLE_T_WEB = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfLABELLE_T_WEB = (Text)fieldLABELLE_T_WEB.getControl();
					fieldLABELLE_T_WEB.getLayoutControl().setLayoutData(tfLABELLE_T_WEBData);
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

	public Label getLaCODE_T_WEB() {
		return laCODE_T_WEB;
	}


	public Text getTfCODE_T_WEB() {
		return tfCODE_T_WEB;
	}



	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public DecoratedField getFieldCODE_T_WEB() {
		return fieldCODE_T_WEB;
	}

	public Label getLaLabelle_T_WEB() {
		return laLabelle_T_WEB;
	}

	public void setLaLabelle_T_WEB(Label laLabelle_T_WEB) {
		this.laLabelle_T_WEB = laLabelle_T_WEB;
	}

	public Text getTfLABELLE_T_WEB() {
		return tfLABELLE_T_WEB;
	}

	public void setTfLABELLE_T_WEB(Text tfLABELLE_T_WEB) {
		this.tfLABELLE_T_WEB = tfLABELLE_T_WEB;
	}

	public DecoratedField getFieldLABELLE_T_WEB() {
		return fieldLABELLE_T_WEB;
	}

	public void setFieldLABELLE_T_WEB(DecoratedField fieldLABELLE_T_WEB) {
		this.fieldLABELLE_T_WEB = fieldLABELLE_T_WEB;
	}

	public void setLaCODE_T_WEB(Label laCODE_T_WEB) {
		this.laCODE_T_WEB = laCODE_T_WEB;
	}

	public void setTfCODE_T_WEB(Text tfCODE_T_WEB) {
		this.tfCODE_T_WEB = tfCODE_T_WEB;
	}

	public void setFieldCODE_T_WEB(DecoratedField fieldCODE_T_WEB) {
		this.fieldCODE_T_WEB = fieldCODE_T_WEB;
	}



}

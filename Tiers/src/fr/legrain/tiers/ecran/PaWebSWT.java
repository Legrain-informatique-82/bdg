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
public class PaWebSWT extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	private Composite paCorpsFormulaire;
	private Label laADRESSE_WEB;
	private Button btnOpenUrl;
	private Label laCODE_T_WEB;

	private Text tfADRESSE_WEB;
	private Text tfCODE_T_WEB;

	private DecoratedField fieldADRESSE_WEB;
	private DecoratedField fieldCODE_T_WEB;
	
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
		PaWebSWT inst = new PaWebSWT(shell, SWT.NULL);
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

	public PaWebSWT(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			{
				paCorpsFormulaire = new Composite(super.getPaFomulaire(), SWT.NONE);
				GridLayout paCorpsFormulaireLayout = new GridLayout();
				paCorpsFormulaireLayout.numColumns = 3;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.grabExcessVerticalSpace = true;
				paCorpsFormulaire.setLayoutData(composite1LData);
				paCorpsFormulaire.setLayout(paCorpsFormulaireLayout);

				{
					laADRESSE_WEB = new Label(paCorpsFormulaire, SWT.NONE);
					laADRESSE_WEB.setText("Adresse Web");
				}
				{
					GridData tfADRESSE_WEBLData = new GridData();
					tfADRESSE_WEBLData.horizontalAlignment = GridData.CENTER;
					tfADRESSE_WEBLData.widthHint = 188;
					tfADRESSE_WEBLData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfADRESSE_WEB = new Text(paCorpsFormulaire, SWT.BORDER);
						tfADRESSE_WEB.setLayoutData(tfADRESSE_WEBLData);
					} else {					
					fieldADRESSE_WEB = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfADRESSE_WEB = (Text)fieldADRESSE_WEB.getControl();
					fieldADRESSE_WEB.getLayoutControl().setLayoutData(tfADRESSE_WEBLData);
					}
				}
				{
					btnOpenUrl = new Button(paCorpsFormulaire, SWT.PUSH | SWT.CENTER);
					GridData btnOpenUrlLData = new GridData();
					btnOpenUrlLData.horizontalAlignment = GridData.CENTER;
					btnOpenUrl.setLayoutData(btnOpenUrlLData);
					btnOpenUrl.setText("Accéder au site");
				}
				
				{
					laCODE_T_WEB = new Label(paCorpsFormulaire, SWT.NONE);
					laCODE_T_WEB.setText("Code Type Web :");
				}
				{
					GridData tfCODE_T_WEBLData = new GridData();
					tfCODE_T_WEBLData.widthHint = 188;
					tfCODE_T_WEBLData.verticalAlignment = GridData.FILL;
					tfCODE_T_WEBLData.horizontalSpan = 2;
					if(!decore) {
						tfCODE_T_WEB = new Text(paCorpsFormulaire, SWT.BORDER);
						tfCODE_T_WEB.setLayoutData(tfCODE_T_WEBLData);
					} else {					
					fieldCODE_T_WEB = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfCODE_T_WEB = (Text)fieldCODE_T_WEB.getControl();
					fieldCODE_T_WEB.getLayoutControl().setLayoutData(tfCODE_T_WEBLData);
					}
				}

//				paCorpsFormulaire
//					.setTabList(new org.eclipse.swt.widgets.Control[] {
//							tfLIBELLE_T_TIERS, tfADRESSE_WEB, tfCODE_T_TIERS,
//							tfPRENOM_TIERS, tfSURNOM_TIERS, composite2,
//							tfADRESSE_WEB, tfCODE_ENTREPRISE, tfCODE_T_TIERS, tfCOMPTE,
//							tfLIBL_COMMENTAIRE, tfCODE_BANQUE, tfEntite, composite3 });
			}
			
			this.setLayout(new GridLayout());
			GridLayout compositeFormLayout = new GridLayout();
			compositeFormLayout.numColumns = 2;
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
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Composite getPaCorpsFormulaire() {
		return paCorpsFormulaire;
	}

	public Label getLaADRESSE_WEB() {
		return laADRESSE_WEB;
	}


	public Text getTfADRESSE_WEB() {
		return tfADRESSE_WEB;
	}



	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public DecoratedField getFieldADRESSE_WEB() {
		return fieldADRESSE_WEB;
	}

	public Label getLaCODE_T_WEB() {
		return laCODE_T_WEB;
	}

	public void setLaCODE_T_WEB(Label laCODE_T_WEB) {
		this.laCODE_T_WEB = laCODE_T_WEB;
	}

	public Text getTfCODE_T_WEB() {
		return tfCODE_T_WEB;
	}

	public void setTfCODE_T_WEB(Text tfCODE_T_WEB) {
		this.tfCODE_T_WEB = tfCODE_T_WEB;
	}

	public DecoratedField getFieldCODE_T_WEB() {
		return fieldCODE_T_WEB;
	}

	public void setFieldCODE_T_WEB(DecoratedField fieldCODE_T_WEB) {
		this.fieldCODE_T_WEB = fieldCODE_T_WEB;
	}

	public boolean isDecore() {
		return decore;
	}

	public void setLaADRESSE_WEB(Label laADRESSE_WEB) {
		this.laADRESSE_WEB = laADRESSE_WEB;
	}

	public void setTfADRESSE_WEB(Text tfADRESSE_WEB) {
		this.tfADRESSE_WEB = tfADRESSE_WEB;
	}

	public void setFieldADRESSE_WEB(DecoratedField fieldADRESSE_WEB) {
		this.fieldADRESSE_WEB = fieldADRESSE_WEB;
	}
	
	public Button getBtnOpenUrl() {
		return btnOpenUrl;
	}

}

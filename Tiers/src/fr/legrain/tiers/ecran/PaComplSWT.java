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
public class PaComplSWT extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
/*
 */
	private Composite paCorpsFormulaire;
	private Label laTVA_I_COM_COMPL;
	private Label laSIRET_COMPL;
	//private Label laCODE_TIERS;



	private Text tfTVA_I_COM_COMPL;
	private Text tfSIRET_COMPL;
	//private Text tfCODE_TIERS;


	
	private DecoratedField fieldTVA_I_COM_COMPL;
    private DecoratedField fieldSIRET_COMPL;
	//private DecoratedField fieldCODE_TIERS;	
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
		PaComplSWT inst = new PaComplSWT(shell, SWT.NULL);
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

	public PaComplSWT(org.eclipse.swt.widgets.Composite parent, int style) {
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
//				{
//					laCODE_TIERS = new Label(paCorpsFormulaire, SWT.NONE);
//					laCODE_TIERS.setText("Code tiers");
//				}
//				{
//					GridData tfCODE_TIERSLData = new GridData();
//					tfCODE_TIERSLData.horizontalAlignment = GridData.FILL;
////					tfCODE_TIERS = new Text(paCorpsFormulaire, SWT.BORDER);
////					tfCODE_TIERS.setLayoutData(tfCODE_TIERSLData);
//
//					fieldCODE_TIERS = new DecoratedField(
//						paCorpsFormulaire,
//						SWT.BORDER,
//						new TextControlCreator());
//					tfCODE_TIERS = (Text) fieldCODE_TIERS.getControl();
//					fieldCODE_TIERS.getLayoutControl().setLayoutData(
//						tfCODE_TIERSLData);
//				}

				{
					laTVA_I_COM_COMPL = new Label(paCorpsFormulaire, SWT.NONE);
					laTVA_I_COM_COMPL.setText("TVA intracommunautaire");
				}
				{
					GridData tfNUMERO_TELEPHONELData = new GridData();
					tfNUMERO_TELEPHONELData.horizontalAlignment = GridData.CENTER;
					tfNUMERO_TELEPHONELData.widthHint = 156;
					tfNUMERO_TELEPHONELData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfTVA_I_COM_COMPL = new Text(paCorpsFormulaire, SWT.BORDER);
						tfTVA_I_COM_COMPL.setLayoutData(tfNUMERO_TELEPHONELData);
					} else {					
					fieldTVA_I_COM_COMPL = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfTVA_I_COM_COMPL = (Text)fieldTVA_I_COM_COMPL.getControl();
					fieldTVA_I_COM_COMPL.getLayoutControl().setLayoutData(tfNUMERO_TELEPHONELData);
					}
				}
				{
					laSIRET_COMPL = new Label(paCorpsFormulaire, SWT.NONE);
					laSIRET_COMPL.setText("N°siret");
				}
				{
					GridData tfPOSTE_TELEPHONELData = new GridData();
					tfPOSTE_TELEPHONELData.horizontalAlignment = GridData.FILL;
					if(!decore) {
						tfSIRET_COMPL = new Text(paCorpsFormulaire, SWT.BORDER);
						tfSIRET_COMPL.setLayoutData(tfPOSTE_TELEPHONELData);
					} else {					
					fieldSIRET_COMPL = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfSIRET_COMPL = (Text)fieldSIRET_COMPL.getControl();
					fieldSIRET_COMPL.getLayoutControl().setLayoutData(tfPOSTE_TELEPHONELData);
					}
				}
//				paCorpsFormulaire
//					.setTabList(new org.eclipse.swt.widgets.Control[] {
//							tfCODE_TIERS, tfCODE_COMPTA, tfNOM_TIERS,
//							tfPRENOM_TIERS, tfSURNOM_TIERS, composite2,
//							tfCODE_T_CIVILITE, tfCODE_ENTREPRISE, tfTVA_I_COM_COMPL, tfCOMPTE,
//							tfLIBL_COMMENTAIRE, tfTVA_I_COM_COMPL, tfEntite, composite3 });
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

	public DecoratedField getFieldTVA_I_COM_COMPL() {
		return fieldTVA_I_COM_COMPL;
	}

	public DecoratedField getFieldSIRET_COMPL() {
		return fieldSIRET_COMPL;
	}

//	public DecoratedField getFieldCODE_TIERS() {
//		return fieldCODE_TIERS;
//	}

	public Label getLaTVA_I_COM_COMPL() {
		return laTVA_I_COM_COMPL;
	}

	public Label getLaSIRET_COMPL() {
		return laSIRET_COMPL;
	}

//	public Label getLaCODE_TIERS() {
//		return laCODE_TIERS;
//	}

	public Composite getPaCorpsFormulaire() {
		return paCorpsFormulaire;
	}

	public Text getTfTVA_I_COM_COMPL() {
		return tfTVA_I_COM_COMPL;
	}

	public Text getTfSIRET_COMPL() {
		return tfSIRET_COMPL;
	}

//	public Text getTfCODE_TIERS() {
//		return tfCODE_TIERS;
//	}

	public void setFieldTVA_I_COM_COMPL(DecoratedField fieldCODE_BANQUE) {
		this.fieldTVA_I_COM_COMPL = fieldCODE_BANQUE;
	}

	public void setFieldSIRET_COMPL(DecoratedField fieldLIBC_BANQUE) {
		this.fieldSIRET_COMPL = fieldLIBC_BANQUE;
	}

//	public void setFieldCODE_TIERS(DecoratedField fieldLIBL_BANQUE) {
//		this.fieldCODE_TIERS = fieldLIBL_BANQUE;
//	}

	public void setLaTVA_I_COM_COMPL(Label laCODE_BANQUE) {
		this.laTVA_I_COM_COMPL = laCODE_BANQUE;
	}

	public void setLaSIRET_COMPL(Label laLIBC_BANQUE) {
		this.laSIRET_COMPL = laLIBC_BANQUE;
	}

//	public void setLaCODE_TIERS(Label laLIBL_BANQUE) {
//		this.laCODE_TIERS = laLIBL_BANQUE;
//	}

	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public void setTfTVA_I_COM_COMPL(Text tfCODE_BANQUE) {
		this.tfTVA_I_COM_COMPL = tfCODE_BANQUE;
	}

	public void setTfSIRET_COMPL(Text tfLIBC_BANQUE) {
		this.tfSIRET_COMPL = tfLIBC_BANQUE;
	}

//	public void setTfCODE_TIERS(Text tfLIBL_BANQUE) {
//		this.tfCODE_TIERS = tfLIBL_BANQUE;
//	}
	



}

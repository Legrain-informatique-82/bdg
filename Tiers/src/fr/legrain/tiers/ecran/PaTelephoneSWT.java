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
public class PaTelephoneSWT extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
/*
 */
	private Composite paCorpsFormulaire;
	private Label laNUMERO_TELEPHONE;
	private Label laPOSTE_TELEPHONE;
	private Label laCODE_TIERS;
	private Label laCODE_T_TEL;
//	private Label laCommCommercialTelephone;
//	private Label laCommAdministratifTelephone;


	private Text tfNUMERO_TELEPHONE;
	private Button btnSendSMS;
	private Text tfPOSTE_TELEPHONE;
	private Text tfCODE_TIERS;
	private Text tfCODE_T_TEL;
//	private Button btnCommCommercialTelephone;
//	private Button btnCommAdministratifTelephone;

	
	private DecoratedField fieldNUMERO_TELEPHONE;
    private DecoratedField fieldPOSTE_TELEPHONE;
	private DecoratedField fieldCODE_TIERS;	
	private DecoratedField fieldCODE_T_TEL;
	private DecoratedField fieldCommCommercialTelephone;
	private DecoratedField fieldCommAdministratifTelephone;

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
		PaTelephoneSWT inst = new PaTelephoneSWT(shell, SWT.NULL);
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

	public PaTelephoneSWT(org.eclipse.swt.widgets.Composite parent, int style) {
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
				composite1Layout.numColumns = 3;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.grabExcessVerticalSpace = true;
				composite1LData.horizontalSpan = 3;
				composite1LData.grabExcessHorizontalSpace = true;
				paCorpsFormulaire.setLayoutData(composite1LData);
				paCorpsFormulaire.setLayout(composite1Layout);

				{
					laNUMERO_TELEPHONE = new Label(paCorpsFormulaire, SWT.NONE);
					laNUMERO_TELEPHONE.setText("Numéro");
				}
				{
					GridData tfNUMERO_TELEPHONELData = new GridData();
					tfNUMERO_TELEPHONELData.horizontalAlignment = GridData.CENTER;
					tfNUMERO_TELEPHONELData.widthHint = 156;
					tfNUMERO_TELEPHONELData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfNUMERO_TELEPHONE = new Text(paCorpsFormulaire, SWT.BORDER);
						tfNUMERO_TELEPHONE.setLayoutData(tfNUMERO_TELEPHONELData);
					} else {					
					fieldNUMERO_TELEPHONE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfNUMERO_TELEPHONE = (Text)fieldNUMERO_TELEPHONE.getControl();
					fieldNUMERO_TELEPHONE.getLayoutControl().setLayoutData(tfNUMERO_TELEPHONELData);
					}
				}
				{
					btnSendSMS = new Button(paCorpsFormulaire, SWT.PUSH | SWT.CENTER);
					GridData btnSendMailLData = new GridData();
					btnSendMailLData.horizontalAlignment = GridData.CENTER;
					btnSendSMS.setLayoutData(btnSendMailLData);
					btnSendSMS.setText("Envoyer un SMS");
				}
				{
					laPOSTE_TELEPHONE = new Label(paCorpsFormulaire, SWT.NONE);
					laPOSTE_TELEPHONE.setText("Poste");
				}
				{
					GridData tfPOSTE_TELEPHONELData = new GridData();
					tfPOSTE_TELEPHONELData.horizontalAlignment = GridData.FILL;
					tfPOSTE_TELEPHONELData.horizontalSpan = 2;
					if(!decore) {
						tfPOSTE_TELEPHONE = new Text(paCorpsFormulaire, SWT.BORDER);
						tfPOSTE_TELEPHONE.setLayoutData(tfPOSTE_TELEPHONELData);
					} else {					
					fieldPOSTE_TELEPHONE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfPOSTE_TELEPHONE = (Text)fieldPOSTE_TELEPHONE.getControl();
					fieldPOSTE_TELEPHONE.getLayoutControl().setLayoutData(tfPOSTE_TELEPHONELData);
					}
				}
				{
					laCODE_TIERS = new Label(paCorpsFormulaire, SWT.NONE);
					laCODE_TIERS.setText("Contact");
				}
				{
					GridData tfCODE_TIERSLData = new GridData();
					tfCODE_TIERSLData.horizontalAlignment = GridData.FILL;
					tfCODE_TIERSLData.horizontalSpan = 2;
					if(!decore) {
						tfCODE_TIERS = new Text(paCorpsFormulaire, SWT.BORDER);
						tfCODE_TIERS.setLayoutData(tfCODE_TIERSLData);
					} else {					
					fieldCODE_TIERS = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfCODE_TIERS = (Text)fieldCODE_TIERS.getControl();
					fieldCODE_TIERS.getLayoutControl().setLayoutData(tfCODE_TIERSLData);
					}
				}
			{
				laCODE_T_TEL = new Label(paCorpsFormulaire, SWT.NONE);
				laCODE_T_TEL.setText("Type de téléphone");
			}
			{
				GridData tfCODE_T_TELLData = new GridData();
				tfCODE_T_TELLData.horizontalAlignment = GridData.BEGINNING;
				tfCODE_T_TELLData.horizontalSpan = 2;
				if(!decore) {
					tfCODE_T_TEL = new Text(paCorpsFormulaire, SWT.BORDER);
					tfCODE_T_TEL.setLayoutData(tfCODE_T_TELLData);
				} else {					
				fieldCODE_T_TEL = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
				tfCODE_T_TEL = (Text)fieldCODE_T_TEL.getControl();
				fieldCODE_T_TEL.getLayoutControl().setLayoutData(tfCODE_T_TELLData);
				}
				
			}
			
//			{
//				laCommCommercialTelephone = new Label(paCorpsFormulaire, SWT.NONE);
//				laCommCommercialTelephone.setText("Communications commerciales");
//			}
//			{
//				GridData tfTTCLData = new GridData();
//				tfTTCLData.widthHint = 22;
//				tfTTCLData.heightHint = 16;
//				tfTTCLData.horizontalSpan = 2;
//				if(!decore) {
//					btnCommCommercialTelephone = new Button(paCorpsFormulaire, SWT.CHECK | SWT.LEFT);
//					btnCommCommercialTelephone.setLayoutData(tfTTCLData);
//				} else {					
//					fieldCommCommercialTelephone = new DecoratedField(paCorpsFormulaire, SWT.CHECK | SWT.LEFT, new ButtonControlCreator());
//					btnCommCommercialTelephone = (Button)fieldCommCommercialTelephone.getControl();
//					fieldCommCommercialTelephone.getLayoutControl().setLayoutData(tfTTCLData);
//				}
//			}
//			
//			{
//				laCommAdministratifTelephone = new Label(paCorpsFormulaire, SWT.NONE);
//				laCommAdministratifTelephone.setText("Communications administratives");
//			}
//			{
//				GridData tfTTCLData = new GridData();
//				tfTTCLData.widthHint = 22;
//				tfTTCLData.heightHint = 16;
//				if(!decore) {
//					btnCommAdministratifTelephone = new Button(paCorpsFormulaire, SWT.CHECK | SWT.LEFT);
//					btnCommAdministratifTelephone.setLayoutData(tfTTCLData);
//				} else {					
//					fieldCommAdministratifTelephone = new DecoratedField(paCorpsFormulaire, SWT.CHECK | SWT.LEFT, new ButtonControlCreator());
//					btnCommAdministratifTelephone = (Button)fieldCommAdministratifTelephone.getControl();
//					fieldCommAdministratifTelephone.getLayoutControl().setLayoutData(tfTTCLData);
//				}
//			}
//				paCorpsFormulaire
//					.setTabList(new org.eclipse.swt.widgets.Control[] {
//							tfCODE_TIERS, tfCODE_COMPTA, tfNOM_TIERS,
//							tfPRENOM_TIERS, tfSURNOM_TIERS, composite2,
//							tfCODE_T_CIVILITE, tfCODE_ENTREPRISE, tfNUMERO_TELEPHONE, tfCOMPTE,
//							tfLIBL_COMMENTAIRE, tfNUMERO_TELEPHONE, tfEntite, composite3 });
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

	public DecoratedField getFieldNUMERO_TELEPHONE() {
		return fieldNUMERO_TELEPHONE;
	}

	public DecoratedField getFieldPOSTE_TELEPHONE() {
		return fieldPOSTE_TELEPHONE;
	}

	public DecoratedField getFieldCODE_TIERS() {
		return fieldCODE_TIERS;
	}

	public Label getLaNUMERO_TELEPHONE() {
		return laNUMERO_TELEPHONE;
	}

	public Label getLaPOSTE_TELEPHONE() {
		return laPOSTE_TELEPHONE;
	}

	public Label getLaCODE_TIERS() {
		return laCODE_TIERS;
	}

	public Composite getPaCorpsFormulaire() {
		return paCorpsFormulaire;
	}

	public Text getTfNUMERO_TELEPHONE() {
		return tfNUMERO_TELEPHONE;
	}

	public Text getTfPOSTE_TELEPHONE() {
		return tfPOSTE_TELEPHONE;
	}

	public Text getTfCODE_TIERS() {
		return tfCODE_TIERS;
	}

	public void setFieldNUMERO_TELEPHONE(DecoratedField fieldCODE_BANQUE) {
		this.fieldNUMERO_TELEPHONE = fieldCODE_BANQUE;
	}

	public void setFieldPOSTE_TELEPHONE(DecoratedField fieldLIBC_BANQUE) {
		this.fieldPOSTE_TELEPHONE = fieldLIBC_BANQUE;
	}

	public void setFieldCODE_TIERS(DecoratedField fieldLIBL_BANQUE) {
		this.fieldCODE_TIERS = fieldLIBL_BANQUE;
	}

	public void setLaNUMERO_TELEPHONE(Label laCODE_BANQUE) {
		this.laNUMERO_TELEPHONE = laCODE_BANQUE;
	}

	public void setLaPOSTE_TELEPHONE(Label laLIBC_BANQUE) {
		this.laPOSTE_TELEPHONE = laLIBC_BANQUE;
	}

	public void setLaCODE_TIERS(Label laLIBL_BANQUE) {
		this.laCODE_TIERS = laLIBL_BANQUE;
	}

	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public void setTfNUMERO_TELEPHONE(Text tfCODE_BANQUE) {
		this.tfNUMERO_TELEPHONE = tfCODE_BANQUE;
	}

	public void setTfPOSTE_TELEPHONE(Text tfLIBC_BANQUE) {
		this.tfPOSTE_TELEPHONE = tfLIBC_BANQUE;
	}

	public void setTfCODE_TIERS(Text tfLIBL_BANQUE) {
		this.tfCODE_TIERS = tfLIBL_BANQUE;
	}
	
	public Label getLaCODE_T_TEL() {
		return laCODE_T_TEL;
	}
	
	public Text getTfCODE_T_TEL() {
		return tfCODE_T_TEL;
	}
	
	public DecoratedField getFieldCODE_T_TEL() {
		return fieldCODE_T_TEL;
	}

//	public Label getLaCommCommercialTelephone() {
//		return laCommCommercialTelephone;
//	}
//
//	public void setLaCommCommercialTelephone(Label laCommCommercialTelephone) {
//		this.laCommCommercialTelephone = laCommCommercialTelephone;
//	}
//
//	public Label getLaCommAdministratifTelephone() {
//		return laCommAdministratifTelephone;
//	}
//
//	public void setLaCommAdministratifTelephone(Label laCommAdministratifTelephone) {
//		this.laCommAdministratifTelephone = laCommAdministratifTelephone;
//	}

	public Button getBtnSendSMS() {
		return btnSendSMS;
	}

	public void setBtnSendSMS(Button btnSendSMS) {
		this.btnSendSMS = btnSendSMS;
	}

//	public Button getBtnCommCommercialTelephone() {
//		return btnCommCommercialTelephone;
//	}
//
//	public void setBtnCommCommercialTelephone(Button btnCommCommercialTelephone) {
//		this.btnCommCommercialTelephone = btnCommCommercialTelephone;
//	}
//
//	public Button getBtnCommAdministratifTelephone() {
//		return btnCommAdministratifTelephone;
//	}
//
//	public void setBtnCommAdministratifTelephone(
//			Button btnCommAdministratifTelephone) {
//		this.btnCommAdministratifTelephone = btnCommAdministratifTelephone;
//	}

	public DecoratedField getFieldCommCommercialTelephone() {
		return fieldCommCommercialTelephone;
	}

	public void setFieldCommCommercialTelephone(
			DecoratedField fieldCommCommercialTelephone) {
		this.fieldCommCommercialTelephone = fieldCommCommercialTelephone;
	}

	public DecoratedField getFieldCommAdministratifTelephone() {
		return fieldCommAdministratifTelephone;
	}

	public void setFieldCommAdministratifTelephone(
			DecoratedField fieldCommAdministratifTelephone) {
		this.fieldCommAdministratifTelephone = fieldCommAdministratifTelephone;
	}


}

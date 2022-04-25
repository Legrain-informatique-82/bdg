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
public class PaEmailSWT extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
/*

 */
	private Composite paCorpsFormulaire;
	private Label laADRESSE_EMAIL;
	private Label laCODE_T_EMAIL;
//	private Label laCommCommercialEmail;
//	private Label laCommAdministratifEmail;
	
	private Text tfADRESSE_EMAIL;
	private Text tfCODE_T_EMAIL;
	private Button btnSendMail;
//	private Button btnCommCommercialEmail;
//	private Button btnCommAdministratifEmail;

	private DecoratedField fieldADRESSE_EMAIL;
	private DecoratedField fieldCODE_T_EMAIL;
	private DecoratedField fieldCommCommercialEmail;
	private DecoratedField fieldCommAdministratifEmail;
	
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
		PaEmailSWT inst = new PaEmailSWT(shell, SWT.NULL);
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

	public PaEmailSWT(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			{
				paCorpsFormulaire = new Composite(
					super.getPaFomulaire(),
					SWT.NONE);
				GridLayout paCorpsFormulaireLayout = new GridLayout();
				paCorpsFormulaireLayout.numColumns = 3;
				GridData paCorpsFormulaireLData = new GridData();
				paCorpsFormulaireLData.horizontalAlignment = GridData.FILL;
				paCorpsFormulaireLData.verticalAlignment = GridData.FILL;
				paCorpsFormulaireLData.grabExcessVerticalSpace = true;
				paCorpsFormulaireLData.horizontalSpan = 3;
				paCorpsFormulaireLData.grabExcessHorizontalSpace = true;
				paCorpsFormulaire.setLayoutData(paCorpsFormulaireLData);
				paCorpsFormulaire.setLayout(paCorpsFormulaireLayout);

				{
					laADRESSE_EMAIL = new Label(paCorpsFormulaire, SWT.NONE);
					laADRESSE_EMAIL.setText("Adresse Email");
				}
				
				{
					GridData tfADRESSE_EMAILLData = new GridData();
					tfADRESSE_EMAILLData.horizontalAlignment = GridData.CENTER;
					tfADRESSE_EMAILLData.widthHint = 156;
					tfADRESSE_EMAILLData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfADRESSE_EMAIL = new Text(paCorpsFormulaire, SWT.BORDER);
						tfADRESSE_EMAIL.setLayoutData(tfADRESSE_EMAILLData);
					} else {					
						fieldADRESSE_EMAIL = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
						tfADRESSE_EMAIL = (Text)fieldADRESSE_EMAIL.getControl();
						fieldADRESSE_EMAIL.getLayoutControl().setLayoutData(tfADRESSE_EMAILLData);
					}
				}
				{
					btnSendMail = new Button(paCorpsFormulaire, SWT.PUSH | SWT.CENTER);
					GridData btnSendMailLData = new GridData();
					btnSendMailLData.horizontalAlignment = GridData.CENTER;
					btnSendMail.setLayoutData(btnSendMailLData);
					btnSendMail.setText("Envoyer un mail");
				}
				
				{
					laCODE_T_EMAIL = new Label(paCorpsFormulaire, SWT.NONE);
					laCODE_T_EMAIL.setText("Type d'email");
				}
				{
					GridData tfCOTE_T_EMAILLData = new GridData();
					tfCOTE_T_EMAILLData.horizontalAlignment = GridData.BEGINNING;
					tfCOTE_T_EMAILLData.widthHint = 156;
					tfCOTE_T_EMAILLData.horizontalSpan = 2;
					tfCOTE_T_EMAILLData.verticalAlignment = GridData.BEGINNING;
					if(!decore) {
						tfCODE_T_EMAIL = new Text(paCorpsFormulaire, SWT.BORDER);
						tfCODE_T_EMAIL.setLayoutData(tfCOTE_T_EMAILLData);
					} else {					
						fieldCODE_T_EMAIL = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfCODE_T_EMAIL = (Text)fieldCODE_T_EMAIL.getControl();
					fieldCODE_T_EMAIL.getLayoutControl().setLayoutData(tfCOTE_T_EMAILLData);
					}
				}
				
//				{
//					laCommCommercialEmail = new Label(paCorpsFormulaire, SWT.NONE);
//					laCommCommercialEmail.setText("Communications commerciales");
//				}
//				{
//					GridData tfTTCLData = new GridData();
//					tfTTCLData.widthHint = 22;
//					tfTTCLData.heightHint = 16;
//					tfTTCLData.horizontalSpan = 2;
//					if(!decore) {
//						btnCommCommercialEmail = new Button(paCorpsFormulaire, SWT.CHECK | SWT.LEFT);
//						btnCommCommercialEmail.setLayoutData(tfTTCLData);
//					} else {					
//						fieldCommCommercialEmail = new DecoratedField(paCorpsFormulaire, SWT.CHECK | SWT.LEFT, new ButtonControlCreator());
//						btnCommCommercialEmail = (Button)fieldCommCommercialEmail.getControl();
//						fieldCommCommercialEmail.getLayoutControl().setLayoutData(tfTTCLData);
//					}
//				}
//				
//				{
//					laCommAdministratifEmail = new Label(paCorpsFormulaire, SWT.NONE);
//					laCommAdministratifEmail.setText("Communications administratives");
//				}
//				{
//					GridData tfTTCLData = new GridData();
//					tfTTCLData.widthHint = 22;
//					tfTTCLData.heightHint = 16;
//					if(!decore) {
//						btnCommAdministratifEmail = new Button(paCorpsFormulaire, SWT.CHECK | SWT.LEFT);
//						btnCommAdministratifEmail.setLayoutData(tfTTCLData);
//					} else {					
//						fieldCommAdministratifEmail = new DecoratedField(paCorpsFormulaire, SWT.CHECK | SWT.LEFT, new ButtonControlCreator());
//						btnCommAdministratifEmail = (Button)fieldCommAdministratifEmail.getControl();
//						fieldCommAdministratifEmail.getLayoutControl().setLayoutData(tfTTCLData);
//					}
//				}

			}
			
			this.setLayout(new GridLayout());
			GridLayout compositeFormLayout = new GridLayout();
			compositeFormLayout.numColumns = 2;
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
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DecoratedField getFieldADRESSE_EMAIL() {
		return fieldADRESSE_EMAIL;
	}

	public Label getLaADRESSE_EMAIL() {
		return laADRESSE_EMAIL;
	}

	public Composite getPaCorpsFormulaire() {
		return paCorpsFormulaire;
	}

	public Text getTfADRESSE_EMAIL() {
		return tfADRESSE_EMAIL;
	}

	public void setFieldADRESSE_EMAIL(DecoratedField fieldCODE_BANQUE) {
		this.fieldADRESSE_EMAIL = fieldCODE_BANQUE;
	}

	public void setLaADRESSE_EMAIL(Label laCODE_BANQUE) {
		this.laADRESSE_EMAIL = laCODE_BANQUE;
	}

	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public void setTfADRESSE_EMAIL(Text tfCODE_BANQUE) {
		this.tfADRESSE_EMAIL = tfCODE_BANQUE;
	}

	public Label getLaCODE_T_EMAIL() {
		return laCODE_T_EMAIL;
	}

	public void setLaCODE_T_EMAIL(Label laCODE_T_EMAIL) {
		this.laCODE_T_EMAIL = laCODE_T_EMAIL;
	}

	public Text getTfCODE_T_EMAIL() {
		return tfCODE_T_EMAIL;
	}

	public void setTfCODE_T_EMAIL(Text tfCODE_T_EMAIL) {
		this.tfCODE_T_EMAIL = tfCODE_T_EMAIL;
	}

	public DecoratedField getFieldCODE_T_EMAIL() {
		return fieldCODE_T_EMAIL;
	}

	public void setFieldCODE_T_EMAIL(DecoratedField fieldCODE_T_EMAIL) {
		this.fieldCODE_T_EMAIL = fieldCODE_T_EMAIL;
	}
	
	public Button getBtnSendMail() {
		return btnSendMail;
	}

//	public Label getLaCommCommercialEmail() {
//		return laCommCommercialEmail;
//	}
//
//	public void setLaCommCommercialEmail(Label laCommCommercialEmail) {
//		this.laCommCommercialEmail = laCommCommercialEmail;
//	}
//
//	public Label getLaCommAdministratifEmail() {
//		return laCommAdministratifEmail;
//	}
//
//	public void setLaCommAdministratifEmail(Label laCommAdministratifEmail) {
//		this.laCommAdministratifEmail = laCommAdministratifEmail;
//	}
//
//	public Button getBtnCommCommercialEmail() {
//		return btnCommCommercialEmail;
//	}
//
//	public void setBtnCommCommercialEmail(Button btnCommCommercialEmail) {
//		this.btnCommCommercialEmail = btnCommCommercialEmail;
//	}
//
//	public Button getBtnCommAdministratifEmail() {
//		return btnCommAdministratifEmail;
//	}
//
//	public void setBtnCommAdministratifEmail(Button btnCommAdministratifEmail) {
//		this.btnCommAdministratifEmail = btnCommAdministratifEmail;
//	}

	public DecoratedField getFieldCommCommercialEmail() {
		return fieldCommCommercialEmail;
	}

	public void setFieldCommCommercialEmail(DecoratedField fieldCommCommercialEmail) {
		this.fieldCommCommercialEmail = fieldCommCommercialEmail;
	}

	public DecoratedField getFieldCommAdministratifEmail() {
		return fieldCommAdministratifEmail;
	}

	public void setFieldCommAdministratifEmail(
			DecoratedField fieldCommAdministratifEmail) {
		this.fieldCommAdministratifEmail = fieldCommAdministratifEmail;
	}

	public void setBtnSendMail(Button btnSendMail) {
		this.btnSendMail = btnSendMail;
	}

}

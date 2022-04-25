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
public class PaLiensSWT extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
/*

 */
	private Composite paCorpsFormulaire;
	private Label laADRESSE_LIENS;
	private Button btnOpenLink;
	private Button btnBrowse;
	private Label laCODE_T_LIENS;
	
	private Text tfADRESSE_LIENS;
	private Text tfCODE_T_LIENS;

	private DecoratedField fieldADRESSE_LIENS;
	private DecoratedField fieldCODE_T_LIENS;
	
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
		PaLiensSWT inst = new PaLiensSWT(shell, SWT.NULL);
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

	public PaLiensSWT(org.eclipse.swt.widgets.Composite parent, int style) {
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
				composite1Layout.numColumns = 4;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.grabExcessVerticalSpace = true;
				composite1LData.horizontalSpan = 3;
				composite1LData.grabExcessHorizontalSpace = true;
				paCorpsFormulaire.setLayoutData(composite1LData);
				paCorpsFormulaire.setLayout(composite1Layout);
				
				{
					laCODE_T_LIENS = new Label(paCorpsFormulaire, SWT.NONE);
					laCODE_T_LIENS.setText("Type de lien");
				}
				{
					GridData tfCODE_T_EMAILLData = new GridData();
					tfCODE_T_EMAILLData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
					//tfCODE_T_EMAILLData.widthHint = 156;
					tfCODE_T_EMAILLData.horizontalSpan = 3;
					tfCODE_T_EMAILLData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfCODE_T_LIENS = new Text(paCorpsFormulaire, SWT.BORDER);
						tfCODE_T_LIENS.setLayoutData(tfCODE_T_EMAILLData);
					} else {					
						fieldCODE_T_LIENS = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfCODE_T_LIENS = (Text)fieldCODE_T_LIENS.getControl();
					fieldCODE_T_LIENS.getLayoutControl().setLayoutData(tfCODE_T_EMAILLData);
					}
				}

				{
					laADRESSE_LIENS = new Label(paCorpsFormulaire, SWT.NONE);
					laADRESSE_LIENS.setText("Adresse du lien");
				}
				{
					GridData tfADRESSE_EMAILLData = new GridData();
					tfADRESSE_EMAILLData.horizontalAlignment = GridData.CENTER;
					tfADRESSE_EMAILLData.widthHint = 156;
					tfADRESSE_EMAILLData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfADRESSE_LIENS = new Text(paCorpsFormulaire, SWT.BORDER);
						tfADRESSE_LIENS.setLayoutData(tfADRESSE_EMAILLData);
					} else {					
					fieldADRESSE_LIENS = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfADRESSE_LIENS = (Text)fieldADRESSE_LIENS.getControl();
					fieldADRESSE_LIENS.getLayoutControl().setLayoutData(tfADRESSE_EMAILLData);
					}
				}
				{
					btnBrowse = new Button(paCorpsFormulaire, SWT.PUSH | SWT.CENTER);
					GridData btnBrowseLData = new GridData();
					btnBrowseLData.horizontalAlignment = GridData.CENTER;
					btnBrowse.setLayoutData(btnBrowseLData);
					btnBrowse.setText("Parcourrir");
				}
				{
					btnOpenLink = new Button(paCorpsFormulaire, SWT.PUSH | SWT.CENTER);
					GridData btnOpenLinkLData = new GridData();
					btnOpenLinkLData.horizontalAlignment = GridData.CENTER;
					btnOpenLink.setLayoutData(btnOpenLinkLData);
					btnOpenLink.setText("Ouvrir");
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

	public DecoratedField getFieldADRESSE_LIENS() {
		return fieldADRESSE_LIENS;
	}

	public Label getLaADRESSE_LIENS() {
		return laADRESSE_LIENS;
	}

	public Composite getPaCorpsFormulaire() {
		return paCorpsFormulaire;
	}

	public Text getTfADRESSE_LIENS() {
		return tfADRESSE_LIENS;
	}

	public void setFieldADRESSE_LIENS(DecoratedField fieldCODE_BANQUE) {
		this.fieldADRESSE_LIENS = fieldCODE_BANQUE;
	}

	public void setLaADRESSE_LIENS(Label laCODE_BANQUE) {
		this.laADRESSE_LIENS = laCODE_BANQUE;
	}

	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public void setTfADRESSE_LIENS(Text tfCODE_BANQUE) {
		this.tfADRESSE_LIENS = tfCODE_BANQUE;
	}

	public Label getLaCODE_T_LIENS() {
		return laCODE_T_LIENS;
	}

	public void setLaCODE_T_LIENS(Label laCODE_T_EMAIL) {
		this.laCODE_T_LIENS = laCODE_T_EMAIL;
	}

	public Text getTfCODE_T_LIENS() {
		return tfCODE_T_LIENS;
	}

	public void setTfCODE_T_LIENS(Text tfCODE_T_EMAIL) {
		this.tfCODE_T_LIENS = tfCODE_T_EMAIL;
	}

	public DecoratedField getFieldCODE_T_LIENS() {
		return fieldCODE_T_LIENS;
	}

	public void setFieldCODE_T_LIENS(DecoratedField fieldCODE_T_EMAIL) {
		this.fieldCODE_T_LIENS = fieldCODE_T_EMAIL;
	}

	public Button getBtnOpenLink() {
		return btnOpenLink;
	}

	public Button getBtnBrowse() {
		return btnBrowse;
	}

}

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
public class PaEntrepriseSWT extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	private Composite paCorpsFormulaire;
	private Label laNOM_ENTREPRISE;
	private Label laCODE_TIERS;


	private Text tfNOM_ENTREPRISE;
	private Text tfCODE_TIERS;

	
	private DecoratedField fieldNOM_ENTREPRISE;
	private DecoratedField fieldCODE_TIERS;
	
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
		PaEntrepriseSWT inst = new PaEntrepriseSWT(shell, SWT.NULL);
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

	public PaEntrepriseSWT(org.eclipse.swt.widgets.Composite parent, int style) {
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
					laNOM_ENTREPRISE = new Label(paCorpsFormulaire, SWT.NONE);
					laNOM_ENTREPRISE.setText("Nom de l'entreprise");
				}
				{
					GridData tfNOM_ENTREPRISELData = new GridData();
					tfNOM_ENTREPRISELData.horizontalAlignment = GridData.CENTER;
					tfNOM_ENTREPRISELData.widthHint = 163;
					tfNOM_ENTREPRISELData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfNOM_ENTREPRISE = new Text(paCorpsFormulaire, SWT.BORDER);
						tfNOM_ENTREPRISE.setLayoutData(tfNOM_ENTREPRISELData);
					} else {					
					fieldNOM_ENTREPRISE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfNOM_ENTREPRISE = (Text)fieldNOM_ENTREPRISE.getControl();
					fieldNOM_ENTREPRISE.getLayoutControl().setLayoutData(tfNOM_ENTREPRISELData);
					}
				}
			{
				laCODE_TIERS = new Label(paCorpsFormulaire, SWT.NONE);
				laCODE_TIERS.setText("Contact");
			}
			{
				GridData tfCODE_TIERSLData = new GridData();
				tfCODE_TIERSLData.widthHint = 163;
				tfCODE_TIERSLData.verticalAlignment = GridData.FILL;
				tfCODE_TIERSLData.horizontalAlignment = GridData.CENTER;
				if(!decore) {
					tfCODE_TIERS = new Text(paCorpsFormulaire, SWT.BORDER);
					tfCODE_TIERS.setLayoutData(tfCODE_TIERSLData);
				} else {					
				fieldCODE_TIERS = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
				tfCODE_TIERS = (Text)fieldCODE_TIERS.getControl();
				fieldCODE_TIERS.getLayoutControl().setLayoutData(tfCODE_TIERSLData);
				}
			}
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

	public DecoratedField getFieldCODE_ENTREPRISE() {
		return fieldNOM_ENTREPRISE;
	}



	public Label getLaNOM_ENTREPRISE() {
		return laNOM_ENTREPRISE;
	}


	public Composite getPaCorpsFormulaire() {
		return paCorpsFormulaire;
	}

	public Text getTfNOM_ENTREPRISE() {
		return tfNOM_ENTREPRISE;
	}



	public void setFieldCODE_ENTREPRISE(DecoratedField fieldCODE_BANQUE) {
		this.fieldNOM_ENTREPRISE = fieldCODE_BANQUE;
	}



	public void setLaNOM_ENTREPRISE(Label laCODE_BANQUE) {
		this.laNOM_ENTREPRISE = laCODE_BANQUE;
	}



	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public void setTfNOM_ENTREPRISE(Text tfCODE_BANQUE) {
		this.tfNOM_ENTREPRISE = tfCODE_BANQUE;
	}

	
	public Label getLaCODE_TIERS() {
		return laCODE_TIERS;
	}
	
	public Text getTfCODE_TIERS() {
		return tfCODE_TIERS;
	}

	public DecoratedField getFieldCODE_TIERS() {
		return fieldCODE_TIERS;
	}

}

package fr.legrain.abonnement.ecrans;
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
public class PaPourcGroupeSWT extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	private Composite paCorpsFormulaire;
	private Label lacodeTAbonnement;

	private Text tfcodeTAbonnement;

	private DecoratedField fieldCodeTAbonnement;
	
	private Label laPourcentage;

	private Text tfPourcentage;

	private DecoratedField fieldPoucentage;
	
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
		PaPourcGroupeSWT inst = new PaPourcGroupeSWT(shell, SWT.NULL);
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

	public PaPourcGroupeSWT(org.eclipse.swt.widgets.Composite parent, int style) {
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
					lacodeTAbonnement = new Label(paCorpsFormulaire, SWT.NONE);
					lacodeTAbonnement.setText("Code");
				}
				{
					GridData tfCOMMERCIALLData = new GridData();
					tfCOMMERCIALLData.horizontalAlignment = GridData.CENTER;
					tfCOMMERCIALLData.widthHint = 188;
					tfCOMMERCIALLData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfcodeTAbonnement = new Text(paCorpsFormulaire, SWT.BORDER);
						tfcodeTAbonnement.setLayoutData(tfCOMMERCIALLData);
					} else {					
					fieldCodeTAbonnement = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfcodeTAbonnement = (Text)fieldCodeTAbonnement.getControl();
					fieldCodeTAbonnement.getLayoutControl().setLayoutData(tfCOMMERCIALLData);
					}
				}
				
				{
					laPourcentage = new Label(paCorpsFormulaire, SWT.NONE);
					laPourcentage.setText("Pourcentage");
				}
				{
					GridData tfCOMMERCIALLData = new GridData();
					tfCOMMERCIALLData.horizontalAlignment = GridData.CENTER;
					tfCOMMERCIALLData.widthHint = 188;
					tfCOMMERCIALLData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfPourcentage = new Text(paCorpsFormulaire, SWT.BORDER);
						tfPourcentage.setLayoutData(tfCOMMERCIALLData);
					} else {					
					fieldPoucentage = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfPourcentage = (Text)fieldPoucentage.getControl();
					fieldPoucentage.getLayoutControl().setLayoutData(tfCOMMERCIALLData);
					}
				}

			}
			
			this.setLayout(new GridLayout());
			GridLayout compositeFormLayout = new GridLayout();
			compositeFormLayout.numColumns = 2;
			this.setSize(646, 334);
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

	public Label getLacodeTAbonnement() {
		return lacodeTAbonnement;
	}


	public Text getTfcodeTAbonnement() {
		return tfcodeTAbonnement;
	}



	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public DecoratedField getFieldCodeTAbonnement() {
		return fieldCodeTAbonnement;
	}

	public Label getLaPourcentage() {
		return laPourcentage;
	}

	public Text getTfPourcentage() {
		return tfPourcentage;
	}

	public DecoratedField getFieldPoucentage() {
		return fieldPoucentage;
	}



}

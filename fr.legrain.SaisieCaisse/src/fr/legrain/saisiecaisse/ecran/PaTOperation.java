package fr.legrain.saisiecaisse.ecran;

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
public class PaTOperation extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	private Composite paCorpsFormulaire;
	private Label laCODE_T_OPERATION;
	private Label laLIB_T_OPERATION;

	private Text tfLIB_T_OPERATION;
	private Text tfCODE_T_OPERATION;

	private DecoratedField fieldLIB_T_OPERATION;
	private DecoratedField fieldCODE_T_OPERATION;

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
		PaTOperation inst = new PaTOperation(shell, SWT.NULL);
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

	public PaTOperation(org.eclipse.swt.widgets.Composite parent, int style) {
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
					laCODE_T_OPERATION = new Label(paCorpsFormulaire, SWT.NONE);
					laCODE_T_OPERATION.setText("Type d'Opération");
				}

				{
					GridData tfCODE_T_TVALData = new GridData();
					tfCODE_T_TVALData.horizontalAlignment = GridData.CENTER;
					tfCODE_T_TVALData.widthHint = 177;
					if(!decore) {
						tfCODE_T_OPERATION = new Text(paCorpsFormulaire, SWT.BORDER);
						tfCODE_T_OPERATION.setLayoutData(tfCODE_T_TVALData);
					} else {					
					fieldCODE_T_OPERATION = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfCODE_T_OPERATION = (Text)fieldCODE_T_OPERATION.getControl();
					fieldCODE_T_OPERATION.getLayoutControl().setLayoutData(tfCODE_T_TVALData);
					}
				}
				{
					laLIB_T_OPERATION = new Label(paCorpsFormulaire, SWT.NONE);
					laLIB_T_OPERATION.setText("Description");
				}
				{
					GridData tfLIB_T_TVALData = new GridData();
					tfLIB_T_TVALData.horizontalAlignment = GridData.FILL;
					if(!decore) {
						tfLIB_T_OPERATION = new Text(paCorpsFormulaire, SWT.BORDER);
						tfLIB_T_OPERATION.setLayoutData(tfLIB_T_TVALData);
					} else {					
					fieldLIB_T_OPERATION = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfLIB_T_OPERATION = (Text)fieldLIB_T_OPERATION.getControl();
					fieldLIB_T_OPERATION.getLayoutControl().setLayoutData(tfLIB_T_TVALData);
					}
				}
			}

			this.setLayout(new GridLayout());
			GridLayout compositeFormLayout = new GridLayout();
			compositeFormLayout.numColumns = 2;
			this.setSize(646, 690);
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

	public Label getLaLIB_T_OPERATION() {
		return laLIB_T_OPERATION;
	}

	public Label getLaCODE_T_OPERATION() {
		return laCODE_T_OPERATION;
	}

	public Text getTfLIB_T_OPERATION() {
		return tfLIB_T_OPERATION;
	}

	public Text getTfCODE_T_OPERATION() {
		return tfCODE_T_OPERATION;
	}

	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public DecoratedField getFieldLIB_T_OPERATION() {
		return fieldLIB_T_OPERATION;
	}

	public DecoratedField getFieldCODE_T_OPERATION() {
		return fieldCODE_T_OPERATION;
	}

}

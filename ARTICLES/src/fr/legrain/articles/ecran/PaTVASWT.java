package fr.legrain.articles.ecran;

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
public class PaTVASWT extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	private Composite paCorpsFormulaire;
	private Label laCODE_TVA;
	private Label laLIBELLE_TVA;
	private Label laTAUX_TVA;
	private Label laNUMCPT_TVA;

	private Text tfLIBELLE_TVA;
	private Text tfNUMCPT_TVA;
	private Text tfCODE_TVA;
	private Text tfTAUX_TVA;

	private DecoratedField fieldLIBELLE_TVA;
	private DecoratedField fieldNUMCPT_TVA;
	private DecoratedField fieldCODE_TVA;
	private DecoratedField fieldTAUX_TVA;

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
		PaTVASWT inst = new PaTVASWT(shell, SWT.NULL);
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

	public PaTVASWT(org.eclipse.swt.widgets.Composite parent, int style) {
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
					laCODE_TVA = new Label(paCorpsFormulaire, SWT.NONE);
					laCODE_TVA.setText("Code");
				}

				{
					GridData tfCODE_TVALData = new GridData();
					tfCODE_TVALData.horizontalAlignment = GridData.CENTER;
					tfCODE_TVALData.widthHint = 177;
					if(!decore) {
						tfCODE_TVA = new Text(paCorpsFormulaire, SWT.BORDER);
						tfCODE_TVA.setLayoutData(tfCODE_TVALData);
					} else {					
					fieldCODE_TVA = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfCODE_TVA = (Text)fieldCODE_TVA.getControl();
					fieldCODE_TVA.getLayoutControl().setLayoutData(tfCODE_TVALData);
					}
				}
				{
					laLIBELLE_TVA = new Label(paCorpsFormulaire, SWT.NONE);
					laLIBELLE_TVA.setText("Libellé");
				}
				{
					GridData tfLIBELLE_TVALData = new GridData();
					tfLIBELLE_TVALData.horizontalAlignment = GridData.FILL;
					if(!decore) {
						tfLIBELLE_TVA = new Text(paCorpsFormulaire, SWT.BORDER);
						tfLIBELLE_TVA.setLayoutData(tfLIBELLE_TVALData);
					} else {					
					fieldLIBELLE_TVA = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfLIBELLE_TVA = (Text)fieldLIBELLE_TVA.getControl();
					fieldLIBELLE_TVA.getLayoutControl().setLayoutData(tfLIBELLE_TVALData);
					}
				}
				{
					laTAUX_TVA = new Label(paCorpsFormulaire, SWT.NONE);
					laTAUX_TVA.setText("Taux");
				}
				{
					GridData tfTAUX_TVALData = new GridData();
					tfTAUX_TVALData.horizontalAlignment = GridData.FILL;
					if(!decore) {
						tfTAUX_TVA = new Text(paCorpsFormulaire, SWT.BORDER);
						tfTAUX_TVA.setLayoutData(tfTAUX_TVALData);
					} else {					
					fieldTAUX_TVA = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfTAUX_TVA = (Text)fieldTAUX_TVA.getControl();
					fieldTAUX_TVA.getLayoutControl().setLayoutData(tfTAUX_TVALData);
					}
				}
				{
					laNUMCPT_TVA = new Label(paCorpsFormulaire, SWT.NONE);
					laNUMCPT_TVA.setText("Compte");
				}
				{
					GridData tfNUMCPT_TVALData = new GridData();
					tfNUMCPT_TVALData.horizontalAlignment = GridData.FILL;
					if(!decore) {
						tfNUMCPT_TVA = new Text(paCorpsFormulaire, SWT.BORDER);
						tfNUMCPT_TVA.setLayoutData(tfNUMCPT_TVALData);
					} else {					
					fieldNUMCPT_TVA = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfNUMCPT_TVA = (Text)fieldNUMCPT_TVA.getControl();
					fieldNUMCPT_TVA.getLayoutControl().setLayoutData(tfNUMCPT_TVALData);
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

	public Label getLaNUMCPT_TVA() {
		return laNUMCPT_TVA;
	}

	public Label getLaLIBELLE_TVA() {
		return laLIBELLE_TVA;
	}

	public Label getLaCODE_TVA() {
		return laCODE_TVA;
	}

	public Label getLaTAUX_TVA() {
		return laTAUX_TVA;
	}

	public Text getTfNUMCPT_TVA() {
		return tfNUMCPT_TVA;
	}

	public Text getTfLIBELLE_TVA() {
		return tfLIBELLE_TVA;
	}

	public Text getTfTAUX_TVA() {
		return tfTAUX_TVA;
	}

	public Text getTfCODE_TVA() {
		return tfCODE_TVA;
	}

	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public DecoratedField getFieldNUMCPT_TVA() {
		return fieldNUMCPT_TVA;
	}

	public DecoratedField getFieldLIBELLE_TVA() {
		return fieldLIBELLE_TVA;
	}

	public DecoratedField getFieldTAUX_TVA() {
		return fieldTAUX_TVA;
	}

	public DecoratedField getFieldCODE_TVA() {
		return fieldCODE_TVA;
	}

}

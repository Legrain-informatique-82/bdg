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

import fr.legrain.lib.gui.fieldassist.CDateTimeControlCreator;
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
public class PaInfosJuridiqueSWTSimple extends fr.legrain.lib.gui.DefaultFrameBoutonSWT {

	{
		SWTResourceManager.registerResourceUser(this);
	}

	private Composite paCorpsFormulaire;
	private Label laRCS_INFO_ENTREPRISE;
	private Label laCAPITAL_INFO_ENTREPRISE;
	private Label laAPE_INFO_ENTREPRISE;
	private Label laSIRET_INFO_ENTREPRISE;
	
	private Text tfCAPITAL_INFO_ENTREPRISE;
	private Text tfRCS_INFO_ENTREPRISE;
	private Text tfAPE_INFO_ENTREPRISE;
	private Text tfSIRET_INFO_ENTREPRISE;
	
	private DecoratedField fieldCAPITAL_INFO_ENTREPRISE;
	private DecoratedField fieldRCS_INFO_ENTREPRISE;
	private DecoratedField fieldAPE_INFO_ENTREPRISE;
	private DecoratedField fieldSIRET_INFO_ENTREPRISE;

	boolean decore = LgrMess.isDECORE();
//	boolean decore = false;
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
		PaInfosJuridiqueSWTSimple inst = new PaInfosJuridiqueSWTSimple(shell, SWT.NULL);
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

	public PaInfosJuridiqueSWTSimple(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {

			this.setLayout(new GridLayout());
			this.setSize(646, 521);

			GridData compositeFormLData = new GridData();
			compositeFormLData.grabExcessHorizontalSpace = true;
			compositeFormLData.verticalAlignment = GridData.FILL;
			compositeFormLData.horizontalAlignment = GridData.FILL;
			compositeFormLData.grabExcessVerticalSpace = true;
			GridData laMessageLData = new GridData();
			laMessageLData.grabExcessHorizontalSpace = true;
			laMessageLData.horizontalAlignment = GridData.FILL;
			GridLayout compositeFormLayout = new GridLayout();
			compositeFormLayout.makeColumnsEqualWidth = true;
			super.getCompositeForm().setLayoutData(compositeFormLData);
			super.getLaMessage().setLayoutData(laMessageLData);
			GridData paBtnLData = new GridData();
			paBtnLData.horizontalAlignment = GridData.CENTER;
			super.getCompositeForm().setLayout(compositeFormLayout);
			super.getPaBtn().setLayoutData(paBtnLData);
			{
				paCorpsFormulaire = new Composite(
					super.getCompositeForm(),
					SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 6;
				GridData composite1LData = new GridData();
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.grabExcessVerticalSpace = true;
				composite1LData.horizontalAlignment = GridData.FILL;
				paCorpsFormulaire.setLayoutData(composite1LData);
				paCorpsFormulaire.setLayout(composite1Layout);
				
				{
					laSIRET_INFO_ENTREPRISE = new Label(paCorpsFormulaire, SWT.NONE);
					laSIRET_INFO_ENTREPRISE.setText("N° Siret");
					GridData laSIRET_INFO_ENTREPRISELData = new GridData();
					laSIRET_INFO_ENTREPRISE.setLayoutData(laSIRET_INFO_ENTREPRISELData);
				}
				{
					GridData tfSIRET_INFO_ENTREPRISELData = new GridData();
					tfSIRET_INFO_ENTREPRISELData.horizontalSpan = 5;
					tfSIRET_INFO_ENTREPRISELData.widthHint = 184;
					tfSIRET_INFO_ENTREPRISELData.grabExcessHorizontalSpace = true;
//					tfSIRET_INFO_ENTREPRISE = new Text(paCorpsFormulaire, SWT.BORDER);
//					tfSIRET_INFO_ENTREPRISE.setLayoutData(tfSIRET_INFO_ENTREPRISELData);

					fieldSIRET_INFO_ENTREPRISE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfSIRET_INFO_ENTREPRISE = (Text)fieldSIRET_INFO_ENTREPRISE.getControl();
					fieldSIRET_INFO_ENTREPRISE.getLayoutControl().setLayoutData(tfSIRET_INFO_ENTREPRISELData);
				}
				{
					laAPE_INFO_ENTREPRISE = new Label(paCorpsFormulaire, SWT.NONE);
					laAPE_INFO_ENTREPRISE.setText("Code APE");
					GridData laAPE_INFO_ENTREPRISELData = new GridData();
					laAPE_INFO_ENTREPRISE.setLayoutData(laAPE_INFO_ENTREPRISELData);
				}
				{
					GridData tfAPE_INFO_ENTREPRISELData = new GridData();
					tfAPE_INFO_ENTREPRISELData.horizontalSpan = 5;
					tfAPE_INFO_ENTREPRISELData.widthHint = 78;
					tfAPE_INFO_ENTREPRISELData.grabExcessHorizontalSpace = true;
//					tfAPE_INFO_ENTREPRISE = new Text(paCorpsFormulaire, SWT.BORDER);
//					tfAPE_INFO_ENTREPRISE.setLayoutData(tfAPE_INFO_ENTREPRISELData);

					fieldAPE_INFO_ENTREPRISE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfAPE_INFO_ENTREPRISE = (Text)fieldAPE_INFO_ENTREPRISE.getControl();
					fieldAPE_INFO_ENTREPRISE.getLayoutControl().setLayoutData(tfAPE_INFO_ENTREPRISELData);
				}
				{
					laRCS_INFO_ENTREPRISE = new Label(paCorpsFormulaire, SWT.NONE);
					laRCS_INFO_ENTREPRISE.setText("RCS");
					GridData laRCS_INFO_ENTREPRISELData = new GridData();
					laRCS_INFO_ENTREPRISE.setLayoutData(laRCS_INFO_ENTREPRISELData);
				}
				{
					GridData tfRCS_INFO_ENTREPRISELData = new GridData();
					tfRCS_INFO_ENTREPRISELData.horizontalSpan = 5;
					tfRCS_INFO_ENTREPRISELData.widthHint = 135;
					tfRCS_INFO_ENTREPRISELData.grabExcessHorizontalSpace = true;
//					tfRCS_INFO_ENTREPRISE = new Text(paCorpsFormulaire, SWT.BORDER);
//					tfRCS_INFO_ENTREPRISE.setLayoutData(tfRCS_INFO_ENTREPRISELData);

					fieldRCS_INFO_ENTREPRISE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfRCS_INFO_ENTREPRISE = (Text)fieldRCS_INFO_ENTREPRISE.getControl();
					fieldRCS_INFO_ENTREPRISE.getLayoutControl().setLayoutData(tfRCS_INFO_ENTREPRISELData);
				}
				{
					laCAPITAL_INFO_ENTREPRISE = new Label(paCorpsFormulaire, SWT.NONE);
					laCAPITAL_INFO_ENTREPRISE.setText("Capital");
					GridData laCAPITAL_INFO_ENTREPRISELData = new GridData();
					laCAPITAL_INFO_ENTREPRISE.setLayoutData(laCAPITAL_INFO_ENTREPRISELData);
				}
				{
					GridData tfCAPITAL_INFO_ENTREPRISELData = new GridData();
					tfCAPITAL_INFO_ENTREPRISELData.horizontalSpan = 5;
					tfCAPITAL_INFO_ENTREPRISELData.widthHint = 78;
					tfCAPITAL_INFO_ENTREPRISELData.grabExcessHorizontalSpace = true;
//					tfCAPITAL_INFO_ENTREPRISE = new Text(paCorpsFormulaire, SWT.BORDER);
//					tfCAPITAL_INFO_ENTREPRISE.setLayoutData(tfCAPITAL_INFO_ENTREPRISELData);

					fieldCAPITAL_INFO_ENTREPRISE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfCAPITAL_INFO_ENTREPRISE = (Text)fieldCAPITAL_INFO_ENTREPRISE.getControl();
					fieldCAPITAL_INFO_ENTREPRISE.getLayoutControl().setLayoutData(tfCAPITAL_INFO_ENTREPRISELData);
				}

			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Composite getPaCorpsFormulaire() {
		return paCorpsFormulaire;
	}

	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public DecoratedField getFieldAPE_INFO_ENTREPRISE() {
		return fieldAPE_INFO_ENTREPRISE;
	}

	public DecoratedField getFieldCAPITAL_INFO_ENTREPRISE() {
		return fieldCAPITAL_INFO_ENTREPRISE;
	}

	public DecoratedField getFieldRCS_INFO_ENTREPRISE() {
		return fieldRCS_INFO_ENTREPRISE;
	}

	public DecoratedField getFieldSIRET_INFO_ENTREPRISE() {
		return fieldSIRET_INFO_ENTREPRISE;
	}

	public Label getLaAPE_INFO_ENTREPRISE() {
		return laAPE_INFO_ENTREPRISE;
	}

	public Label getLaCAPITAL_INFO_ENTREPRISE() {
		return laCAPITAL_INFO_ENTREPRISE;
	}

	public Label getLaRCS_INFO_ENTREPRISE() {
		return laRCS_INFO_ENTREPRISE;
	}

	public Label getLaSIRET_INFO_ENTREPRISE() {
		return laSIRET_INFO_ENTREPRISE;
	}

	public Text getTfAPE_INFO_ENTREPRISE() {
		return tfAPE_INFO_ENTREPRISE;
	}

	public Text getTfCAPITAL_INFO_ENTREPRISE() {
		return tfCAPITAL_INFO_ENTREPRISE;
	}

	public Text getTfRCS_INFO_ENTREPRISE() {
		return tfRCS_INFO_ENTREPRISE;
	}

	public Text getTfSIRET_INFO_ENTREPRISE() {
		return tfSIRET_INFO_ENTREPRISE;
	}

}

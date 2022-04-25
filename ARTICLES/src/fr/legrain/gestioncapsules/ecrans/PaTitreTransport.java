package fr.legrain.gestioncapsules.ecrans;
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
public class PaTitreTransport extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

	private Composite paCorpsFormulaire;
	private Label laCodeTitreTransport;
	private Label laLibelleTitreTransport;
	private Label laQteMinTitreTransport;

	private Text tfCodeTitreTransport;
	private Text tfLibelleTitreTransport;
	private Text tfQteMinTitreTransport;

	private DecoratedField fieldCodeTitreTransport;
	private DecoratedField fieldLibelleTitreTransport;
	private DecoratedField fieldQteMinTitreTransport;
	
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
		PaTitreTransport inst = new PaTitreTransport(shell, SWT.NULL);
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

	public PaTitreTransport(org.eclipse.swt.widgets.Composite parent, int style) {
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
					laCodeTitreTransport = new Label(paCorpsFormulaire, SWT.NONE);
					laCodeTitreTransport.setText("Code");
				}
				{
					GridData tfCODE_T_NOTEData = new GridData();
					tfCODE_T_NOTEData.horizontalAlignment = GridData.CENTER;
					tfCODE_T_NOTEData.widthHint = 188;
					tfCODE_T_NOTEData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfCodeTitreTransport = new Text(paCorpsFormulaire, SWT.BORDER);
						tfCodeTitreTransport.setLayoutData(tfCODE_T_NOTEData);
					} else {					
					fieldCodeTitreTransport = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfCodeTitreTransport = (Text)fieldCodeTitreTransport.getControl();
					fieldCodeTitreTransport.getLayoutControl().setLayoutData(tfCODE_T_NOTEData);
					}
				}
				
				{
					laLibelleTitreTransport = new Label(paCorpsFormulaire, SWT.NONE);
					laLibelleTitreTransport.setText("Libelle");
				}
				{
					GridData tfCODE_T_NOTEData = new GridData();
					tfCODE_T_NOTEData.horizontalAlignment = GridData.CENTER;
					tfCODE_T_NOTEData.widthHint = 188;
					tfCODE_T_NOTEData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfLibelleTitreTransport = new Text(paCorpsFormulaire, SWT.BORDER);
						tfLibelleTitreTransport.setLayoutData(tfCODE_T_NOTEData);
					} else {					
						fieldLibelleTitreTransport = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
						tfLibelleTitreTransport = (Text)fieldLibelleTitreTransport.getControl();
						fieldLibelleTitreTransport.getLayoutControl().setLayoutData(tfCODE_T_NOTEData);
					}
				}
				
				{
					laQteMinTitreTransport = new Label(paCorpsFormulaire, SWT.NONE);
					laQteMinTitreTransport.setText("Quantité Min");
				}
				{
					GridData tfCODE_T_NOTEData = new GridData();
					tfCODE_T_NOTEData.horizontalAlignment = GridData.CENTER;
					tfCODE_T_NOTEData.widthHint = 188;
					tfCODE_T_NOTEData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfQteMinTitreTransport = new Text(paCorpsFormulaire, SWT.BORDER);
						tfQteMinTitreTransport.setLayoutData(tfCODE_T_NOTEData);
					} else {					
						fieldQteMinTitreTransport = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
						tfQteMinTitreTransport = (Text)fieldQteMinTitreTransport.getControl();
						fieldQteMinTitreTransport.getLayoutControl().setLayoutData(tfCODE_T_NOTEData);
					}
				}

			}
			
			this.setLayout(new GridLayout());
			GridLayout compositeFormLayout = new GridLayout();
			compositeFormLayout.numColumns = 2;
			this.setSize(913, 439);
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

	public Label getLaCodeTitreTransport() {
		return laCodeTitreTransport;
	}

	public Text getTfCodeTitreTransport() {
		return tfCodeTitreTransport;
	}

	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public DecoratedField getFieldCodeTitreTransport() {
		return fieldCodeTitreTransport;
	}

	public void setLaCodeTitreTransport(Label laCODE_T_NOTE_ARTICLE) {
		this.laCodeTitreTransport = laCODE_T_NOTE_ARTICLE;
	}

	public void setTfCodeTitreTransport(Text tfCODE_T_NOTE_ARTICLE) {
		this.tfCodeTitreTransport = tfCODE_T_NOTE_ARTICLE;
	}

	public void setFieldCodeTitreTransport(DecoratedField fieldCODE_T_NOTE_ARTICLE) {
		this.fieldCodeTitreTransport = fieldCODE_T_NOTE_ARTICLE;
	}

	public Label getLaLibelleTitreTransport() {
		return laLibelleTitreTransport;
	}

	public Label getLaQteMinTitreTransport() {
		return laQteMinTitreTransport;
	}

	public Text getTfLibelleTitreTransport() {
		return tfLibelleTitreTransport;
	}

	public Text getTfQteMinTitreTransport() {
		return tfQteMinTitreTransport;
	}

	public DecoratedField getFieldLibelleTitreTransport() {
		return fieldLibelleTitreTransport;
	}

	public DecoratedField getFieldQteMinTitreTransport() {
		return fieldQteMinTitreTransport;
	}

}

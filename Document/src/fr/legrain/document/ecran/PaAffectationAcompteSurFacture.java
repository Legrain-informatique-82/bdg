package fr.legrain.document.ecran;

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

import fr.legrain.lib.gui.DefaultFrameFormulaireDoubleGrille;
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
public class PaAffectationAcompteSurFacture extends
		DefaultFrameFormulaireDoubleGrille {

	private Composite paCorpsFormulaire;
	private Text tfCODE_DOCUMENT;
	private Label laMONTANT_AFFECTE;
	private Text tfMONTANT_AFFECTE;
	private Button btnValider;
	final private boolean decore = LgrMess.isDECORE();
	//final private boolean decore = false;
	private DecoratedField fieldMONTANT_AFFECTE;
	
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
		PaAffectationAcompteSurFacture inst = new PaAffectationAcompteSurFacture(shell, SWT.NULL);
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
	
	public PaAffectationAcompteSurFacture(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}
	private void initGUI() {
		try {
			{
				paCorpsFormulaire = new Composite(super.getPaFomulaire(), SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 4;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.verticalAlignment = GridData.FILL;
				paCorpsFormulaire.setLayoutData(composite1LData);
				paCorpsFormulaire.setLayout(composite1Layout);
			}
			{
				GridData tfCODE_DOCUMENTLData = new GridData();
				tfCODE_DOCUMENTLData.horizontalAlignment = GridData.CENTER;
				tfCODE_DOCUMENTLData.widthHint = 80;
				tfCODE_DOCUMENTLData.verticalAlignment = GridData.BEGINNING;
				tfCODE_DOCUMENT = new Text(paCorpsFormulaire, SWT.BORDER);
				tfCODE_DOCUMENT.setLayoutData(tfCODE_DOCUMENTLData);
			}
			{
				laMONTANT_AFFECTE = new Label(paCorpsFormulaire, SWT.NONE);
				laMONTANT_AFFECTE.setText("Montant affecté");
			}
			{
				GridData tfMONTANT_AFFECTELData = new GridData();
				tfMONTANT_AFFECTELData.horizontalAlignment = GridData.CENTER;
				tfMONTANT_AFFECTELData.widthHint = 100;
				tfMONTANT_AFFECTELData.verticalAlignment = GridData.BEGINNING;
				if(!decore) {
					tfMONTANT_AFFECTE = new Text(paCorpsFormulaire, SWT.BORDER);
					tfMONTANT_AFFECTE.setLayoutData(tfMONTANT_AFFECTELData);
				} else {					
				fieldMONTANT_AFFECTE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
				tfMONTANT_AFFECTE = (Text)fieldMONTANT_AFFECTE.getControl();
				fieldMONTANT_AFFECTE.getLayoutControl().setLayoutData(tfMONTANT_AFFECTELData);
				}
			}
			{
				btnValider = new Button(paCorpsFormulaire, SWT.PUSH | SWT.CENTER);
				GridData btnAnnulerLData = new GridData();
				btnValider.setLayoutData(btnAnnulerLData);
				btnValider.setText("Valider");
//				btnValider.setSize(101, 29);
			}
			this.setLayout(new GridLayout());
			GridLayout compositeFormLayout = new GridLayout();
			compositeFormLayout.numColumns = 3;
			GridData paGrilleLData = new GridData();
			paGrilleLData.grabExcessHorizontalSpace = true;
			paGrilleLData.horizontalAlignment = GridData.FILL;
			paGrilleLData.verticalAlignment = GridData.FILL;
			paGrilleLData.grabExcessVerticalSpace = true;
			super.getCompositeForm().setLayout(compositeFormLayout);
			GridData paFomulaireLData = new GridData();
			paFomulaireLData.verticalAlignment = GridData.FILL;
			paFomulaireLData.horizontalAlignment = GridData.FILL;
			super.getPaGrilleDest().setLayoutData(paGrilleLData);
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
			super.getGrilleDest().setLayoutData(grilleLData);
			this.layout();
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Composite getPaCorpsFormulaire() {
		return paCorpsFormulaire;
	}

	public Text getTfCODE_DOCUMENT() {
		return tfCODE_DOCUMENT;
	}

	public Label getLaMONTANT_AFFECTE() {
		return laMONTANT_AFFECTE;
	}

	public Text getTfMONTANT_AFFECTE() {
		return tfMONTANT_AFFECTE;
	}

	public boolean isDecore() {
		return decore;
	}

	public DecoratedField getFieldMONTANT_AFFECTE() {
		return fieldMONTANT_AFFECTE;
	}

	public Button getBtnValider() {
		return btnValider;
	}

		
}

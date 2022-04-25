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
public class PaTypePaiementSWT extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

	private Composite paCorpsFormulaire;
	private Label laCODE_T_PAIEMENT;
	private Label laLIB_T_PAIEMENT;
	private Label laCOMPTE;
	private Label laREPORT_C_PAIEMENT;
	private Label laFIN_MOIS_C_PAIEMENT;

	private Text tfLIB_T_PAIEMENT;
	private Text tfCODE_T_PAIEMENT;
	private Text tfCOMPTE;
	private Text tfREPORT_C_PAIEMENT;
	private Text tfFIN_MOIS_C_PAIEMENT;

	private DecoratedField fieldLIB_T_PAIEMENT;
	private DecoratedField fieldCODE_T_PAIEMENT;
	private DecoratedField fieldCOMPTE;
	private DecoratedField fieldREPORT_C_PAIEMENT;	
	private DecoratedField fieldFIN_MOIS_C_PAIEMENT;

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
		PaTypePaiementSWT inst = new PaTypePaiementSWT(shell, SWT.NULL);
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

	public PaTypePaiementSWT(org.eclipse.swt.widgets.Composite parent, int style) {
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
					laCODE_T_PAIEMENT = new Label(paCorpsFormulaire, SWT.NONE);
					laCODE_T_PAIEMENT.setText("Code");
				}

				{
					GridData tfCODE_UNITELData = new GridData();
					tfCODE_UNITELData.horizontalAlignment = GridData.CENTER;
					tfCODE_UNITELData.widthHint = 297;
					tfCODE_UNITELData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfCODE_T_PAIEMENT = new Text(paCorpsFormulaire, SWT.BORDER);
						tfCODE_T_PAIEMENT.setLayoutData(tfCODE_UNITELData);
					} else {					
					fieldCODE_T_PAIEMENT = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfCODE_T_PAIEMENT = (Text)fieldCODE_T_PAIEMENT.getControl();
					fieldCODE_T_PAIEMENT.getLayoutControl().setLayoutData(tfCODE_UNITELData);
					}
				}
				{
					laLIB_T_PAIEMENT = new Label(paCorpsFormulaire, SWT.NONE);
					laLIB_T_PAIEMENT.setText("Libellé");
				}
				{
					GridData tfLIBL_UNITELData = new GridData();
					tfLIBL_UNITELData.horizontalAlignment = GridData.FILL;
					tfLIBL_UNITELData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfLIB_T_PAIEMENT = new Text(paCorpsFormulaire, SWT.BORDER);
						tfLIB_T_PAIEMENT.setLayoutData(tfLIBL_UNITELData);
					} else {					
					fieldLIB_T_PAIEMENT = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfLIB_T_PAIEMENT = (Text)fieldLIB_T_PAIEMENT.getControl();
					fieldLIB_T_PAIEMENT.getLayoutControl().setLayoutData(tfLIBL_UNITELData);
					}
				}
				{
					laCOMPTE = new Label(paCorpsFormulaire, SWT.NONE);
					laCOMPTE.setText("Compte financier");
				}
				{
					GridData tfCOMPTELData = new GridData();
					tfCOMPTELData.horizontalAlignment = GridData.FILL;
					tfCOMPTELData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfCOMPTE = new Text(paCorpsFormulaire, SWT.BORDER);
						tfCOMPTE.setLayoutData(tfCOMPTELData);
					} else {					
					fieldCOMPTE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfCOMPTE = (Text)fieldCOMPTE.getControl();
					fieldCOMPTE.getLayoutControl().setLayoutData(tfCOMPTELData);
					}
				}
				{
					laREPORT_C_PAIEMENT = new Label(paCorpsFormulaire, SWT.NONE);
					laREPORT_C_PAIEMENT.setText("Report");
				}
				{
					GridData tfREPORT_C_PAIEMENTLData = new GridData();
					tfREPORT_C_PAIEMENTLData.horizontalAlignment = GridData.FILL;
					tfREPORT_C_PAIEMENTLData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfREPORT_C_PAIEMENT = new Text(paCorpsFormulaire, SWT.BORDER);
						tfREPORT_C_PAIEMENT.setLayoutData(tfREPORT_C_PAIEMENTLData);
					} else {					
					fieldREPORT_C_PAIEMENT = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfREPORT_C_PAIEMENT = (Text)fieldREPORT_C_PAIEMENT.getControl();
					fieldREPORT_C_PAIEMENT.getLayoutControl().setLayoutData(tfREPORT_C_PAIEMENTLData);
					}
				}
				{
					laFIN_MOIS_C_PAIEMENT = new Label(paCorpsFormulaire, SWT.NONE);
					laFIN_MOIS_C_PAIEMENT.setText("Fin de mois");
				}
				{
					GridData tfFIN_MOIS_C_PAIEMENTLData = new GridData();
					tfFIN_MOIS_C_PAIEMENTLData.horizontalAlignment = GridData.FILL;
					tfFIN_MOIS_C_PAIEMENTLData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfFIN_MOIS_C_PAIEMENT = new Text(paCorpsFormulaire, SWT.BORDER);
						tfFIN_MOIS_C_PAIEMENT.setLayoutData(tfFIN_MOIS_C_PAIEMENTLData);
					} else {					fieldFIN_MOIS_C_PAIEMENT = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfFIN_MOIS_C_PAIEMENT = (Text)fieldFIN_MOIS_C_PAIEMENT.getControl();
					fieldFIN_MOIS_C_PAIEMENT.getLayoutControl().setLayoutData(tfFIN_MOIS_C_PAIEMENTLData);
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

	public Label getLaLIB_T_PAIEMENT() {
		return laLIB_T_PAIEMENT;
	}

	public Label getLaCODE_T_PAIEMENT() {
		return laCODE_T_PAIEMENT;
	}

	public Text getTfLIB_T_PAIEMENT() {
		return tfLIB_T_PAIEMENT;
	}

	public Text getTfCODE_T_PAIEMENT() {
		return tfCODE_T_PAIEMENT;
	}

	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public DecoratedField getFieldLIB_T_PAIEMENT() {
		return fieldLIB_T_PAIEMENT;
	}

	public DecoratedField getFieldCODE_T_PAIEMENT() {
		return fieldCODE_T_PAIEMENT;
	}

	public Label getLaCOMPTE() {
		return laCOMPTE;
	}

	public Text getTfCOMPTE() {
		return tfCOMPTE;
	}

	public DecoratedField getFieldCOMPTE() {
		return fieldCOMPTE;
	}

	public Label getLaREPORT_C_PAIEMENT() {
		return laREPORT_C_PAIEMENT;
	}

	public Label getLaFIN_MOIS_C_PAIEMENT() {
		return laFIN_MOIS_C_PAIEMENT;
	}

	public Text getTfREPORT_C_PAIEMENT() {
		return tfREPORT_C_PAIEMENT;
	}

	public Text getTfFIN_MOIS_C_PAIEMENT() {
		return tfFIN_MOIS_C_PAIEMENT;
	}

	public DecoratedField getFieldREPORT_C_PAIEMENT() {
		return fieldREPORT_C_PAIEMENT;
	}

	public DecoratedField getFieldFIN_MOIS_C_PAIEMENT() {
		return fieldFIN_MOIS_C_PAIEMENT;
	}

}

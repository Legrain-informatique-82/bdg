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
import org.eclipse.swt.widgets.Combo;
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
public class PaConditionPaiementSWT extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	private Composite paCorpsFormulaire;
	private Label laCODE_C_PAIEMENT;
	private Label laLIB_C_PAIEMENT;
	private Label laREPORT_C_PAIEMENT;
	private Label laFIN_MOIS_C_PAIEMENT;
	private Label laTypeCPaiement;
	private Label laCPaiementDocDefaut;

	private Text tfCODE_C_PAIEMENT;
	private Text tfLIB_C_PAIEMENT;
	private Text tfREPORT_C_PAIEMENT;
	private Text tfFIN_MOIS_C_PAIEMENT;
	private Combo cbTypeCPaiement;
	private Button cbCPaiementDocDefaut;

	private DecoratedField fieldCODE_C_PAIEMENT;
	private DecoratedField fieldLIB_C_PAIEMENT;
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
		PaConditionPaiementSWT inst = new PaConditionPaiementSWT(shell, SWT.NULL);
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

	public PaConditionPaiementSWT(org.eclipse.swt.widgets.Composite parent, int style) {
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
				composite1LData.horizontalSpan = 3;
				composite1LData.grabExcessHorizontalSpace = true;
				paCorpsFormulaire.setLayoutData(composite1LData);
				paCorpsFormulaire.setLayout(composite1Layout);
				
				{
					laTypeCPaiement = new Label(paCorpsFormulaire, SWT.NONE);
					laTypeCPaiement.setText("Type");
				}
				{
					GridData cbTypeCPaiementLData = new GridData();
					cbTypeCPaiementLData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
					cbTypeCPaiementLData.grabExcessHorizontalSpace = true;
					cbTypeCPaiementLData.widthHint = 156;
					cbTypeCPaiementLData.verticalAlignment = GridData.FILL;
//					if(!decore) {
						cbTypeCPaiement = new Combo(paCorpsFormulaire, SWT.BORDER | SWT.READ_ONLY);
						cbTypeCPaiement.setLayoutData(cbTypeCPaiementLData);
//					} else {					
//					fieldCODE_C_PAIEMENT = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
//					cbTypeCPaiement = (Text)fieldCODE_C_PAIEMENT.getControl();
//					fieldCODE_C_PAIEMENT.getLayoutControl().setLayoutData(tfCODE_C_PAIEMENTLData);
//					}
				}

				{
					laCODE_C_PAIEMENT = new Label(paCorpsFormulaire, SWT.NONE);
					laCODE_C_PAIEMENT.setText("Code Cond paiement");
				}
				{
					GridData tfCODE_C_PAIEMENTLData = new GridData();
					tfCODE_C_PAIEMENTLData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
					tfCODE_C_PAIEMENTLData.grabExcessHorizontalSpace = true;
					tfCODE_C_PAIEMENTLData.widthHint = 156;
					tfCODE_C_PAIEMENTLData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfCODE_C_PAIEMENT = new Text(paCorpsFormulaire, SWT.BORDER);
						tfCODE_C_PAIEMENT.setLayoutData(tfCODE_C_PAIEMENTLData);
					} else {					
					fieldCODE_C_PAIEMENT = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfCODE_C_PAIEMENT = (Text)fieldCODE_C_PAIEMENT.getControl();
					fieldCODE_C_PAIEMENT.getLayoutControl().setLayoutData(tfCODE_C_PAIEMENTLData);
					}
				}
				{
					laLIB_C_PAIEMENT = new Label(paCorpsFormulaire, SWT.NONE);
					laLIB_C_PAIEMENT.setText("Libellé");
				}
				{
					GridData tfLIB_C_PAIEMENTLData = new GridData();
					tfLIB_C_PAIEMENTLData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
					tfLIB_C_PAIEMENTLData.grabExcessHorizontalSpace = true;
					tfLIB_C_PAIEMENTLData.widthHint = 156;
					tfLIB_C_PAIEMENTLData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfLIB_C_PAIEMENT = new Text(paCorpsFormulaire, SWT.BORDER);
						tfLIB_C_PAIEMENT.setLayoutData(tfLIB_C_PAIEMENTLData);
					} else {					
					fieldLIB_C_PAIEMENT = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfLIB_C_PAIEMENT = (Text)fieldLIB_C_PAIEMENT.getControl();
					fieldLIB_C_PAIEMENT.getLayoutControl().setLayoutData(tfLIB_C_PAIEMENTLData);
					}
				}
				{
					laREPORT_C_PAIEMENT = new Label(paCorpsFormulaire, SWT.NONE);
					laREPORT_C_PAIEMENT.setText("Report");
				}
				{
					GridData tfREPORT_C_PAIEMENTLData = new GridData();
					tfREPORT_C_PAIEMENTLData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
					tfREPORT_C_PAIEMENTLData.grabExcessHorizontalSpace = true;
					tfREPORT_C_PAIEMENTLData.widthHint = 156;
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
					tfFIN_MOIS_C_PAIEMENTLData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
					tfFIN_MOIS_C_PAIEMENTLData.grabExcessHorizontalSpace = true;
					tfFIN_MOIS_C_PAIEMENTLData.widthHint = 156;
					tfFIN_MOIS_C_PAIEMENTLData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfFIN_MOIS_C_PAIEMENT = new Text(paCorpsFormulaire, SWT.BORDER);
						tfFIN_MOIS_C_PAIEMENT.setLayoutData(tfFIN_MOIS_C_PAIEMENTLData);
					} else {					fieldFIN_MOIS_C_PAIEMENT = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfFIN_MOIS_C_PAIEMENT = (Text)fieldFIN_MOIS_C_PAIEMENT.getControl();
					fieldFIN_MOIS_C_PAIEMENT.getLayoutControl().setLayoutData(tfFIN_MOIS_C_PAIEMENTLData);
					}
				}
				{
					laCPaiementDocDefaut = new Label(paCorpsFormulaire, SWT.NONE);
					laCPaiementDocDefaut.setText("Défaut");
				}
				{
					GridData cbCPaiementDocDefautLData = new GridData();
					cbCPaiementDocDefautLData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
					cbCPaiementDocDefautLData.grabExcessHorizontalSpace = true;
					cbCPaiementDocDefautLData.widthHint = 156;
					cbCPaiementDocDefautLData.verticalAlignment = GridData.FILL;
					
					cbCPaiementDocDefaut = new Button(paCorpsFormulaire, SWT.CHECK);
					cbCPaiementDocDefaut.setLayoutData(cbCPaiementDocDefautLData);
					
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

	public DecoratedField getFieldCODE_C_PAIEMENT() {
		return fieldCODE_C_PAIEMENT;
	}

	public DecoratedField getFieldLIB_C_PAIEMENT() {
		return fieldLIB_C_PAIEMENT;
	}

	public DecoratedField getFieldREPORT_C_PAIEMENT() {
		return fieldREPORT_C_PAIEMENT;
	}

	public Label getLaCODE_C_PAIEMENT() {
		return laCODE_C_PAIEMENT;
	}

	public Label getLaLIB_C_PAIEMENT() {
		return laLIB_C_PAIEMENT;
	}

	public Label getLaREPORT_C_PAIEMENT() {
		return laREPORT_C_PAIEMENT;
	}

	public Composite getPaCorpsFormulaire() {
		return paCorpsFormulaire;
	}

	public Text getTfCODE_C_PAIEMENT() {
		return tfCODE_C_PAIEMENT;
	}

	public Text getTfLIB_C_PAIEMENT() {
		return tfLIB_C_PAIEMENT;
	}

	public Text getTfREPORT_C_PAIEMENT() {
		return tfREPORT_C_PAIEMENT;
	}

	public void setFieldCODE_C_PAIEMENT(DecoratedField fieldCODE_BANQUE) {
		this.fieldCODE_C_PAIEMENT = fieldCODE_BANQUE;
	}

	public void setFieldLIB_C_PAIEMENT(DecoratedField fieldLIBC_BANQUE) {
		this.fieldLIB_C_PAIEMENT = fieldLIBC_BANQUE;
	}

	public void setFieldREPORT_C_PAIEMENT(DecoratedField fieldLIBL_BANQUE) {
		this.fieldREPORT_C_PAIEMENT = fieldLIBL_BANQUE;
	}

	public void setLaCODE_C_PAIEMENT(Label laCODE_BANQUE) {
		this.laCODE_C_PAIEMENT = laCODE_BANQUE;
	}

	public void setLaLIB_C_PAIEMENT(Label laLIBC_BANQUE) {
		this.laLIB_C_PAIEMENT = laLIBC_BANQUE;
	}

	public void setLaREPORT_C_PAIEMENT(Label laLIBL_BANQUE) {
		this.laREPORT_C_PAIEMENT = laLIBL_BANQUE;
	}

	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public void setTfCODE_C_PAIEMENT(Text tfCODE_BANQUE) {
		this.tfCODE_C_PAIEMENT = tfCODE_BANQUE;
	}

	public void setTfLIB_C_PAIEMENT(Text tfLIBC_BANQUE) {
		this.tfLIB_C_PAIEMENT = tfLIBC_BANQUE;
	}

	public void setTfREPORT_C_PAIEMENT(Text tfLIBL_BANQUE) {
		this.tfREPORT_C_PAIEMENT = tfLIBL_BANQUE;
	}

	public Label getLaFIN_MOIS_C_PAIEMENT() {
		return laFIN_MOIS_C_PAIEMENT;
	}

	public Text getTfFIN_MOIS_C_PAIEMENT() {
		return tfFIN_MOIS_C_PAIEMENT;
	}

	public DecoratedField getFieldFIN_MOIS_C_PAIEMENT() {
		return fieldFIN_MOIS_C_PAIEMENT;
	}

	public Label getLaTypeCPaiement() {
		return laTypeCPaiement;
	}

	public Combo getCbTypeCPaiement() {
		return cbTypeCPaiement;
	}

	public Label getLaCPaiementDocDefaut() {
		return laCPaiementDocDefaut;
	}

	public Button getCbCPaiementDocDefaut() {
		return cbCPaiementDocDefaut;
	}

}

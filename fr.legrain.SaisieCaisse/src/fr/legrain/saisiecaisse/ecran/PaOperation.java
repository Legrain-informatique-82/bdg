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
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.resource.SWTResourceManager;

import fr.legrain.lib.gui.cdatetimeLgr;
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
public class PaOperation extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	private Composite paCorpsFormulaire;
	private Label laDATE_OPERATION;	
	private Label laCODE_T_OPERATION;
	private Label laT_PAIEMENT;
	private Label laETABLISSEMENT;
	private Label laMONTANT_OPERATION;
	private Label laLIB_OPERATION;
	private Label laCOMPTE;
	private Label laTVA;

	private DateTime tfDATE_OPERATION;
	private Text tfCODE_T_OPERATION;
	private Text tfT_PAIEMENT;
	private Text tfETABLISSEMENT;
	private Text tfMONTANT_OPERATION;
	private Text tfLIB_OPERATION;
	private Text tfCOMPTE;
	private Text tfTVA;

	private DecoratedField fieldDATE_OPERATION;
	private DecoratedField fieldCODE_T_OPERATION;	
	private DecoratedField fieldT_PAIEMENT;
	private DecoratedField fieldETABLISSEMENT;
	private DecoratedField fieldMONTANT_OPERATION;
	private DecoratedField fieldLIB_OPERATION;
	private DecoratedField fieldCOMPTE;
	private DecoratedField fieldTVA;
	
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
		PaOperation inst = new PaOperation(shell, SWT.NULL);
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

	public PaOperation(org.eclipse.swt.widgets.Composite parent, int style) {
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
					laDATE_OPERATION = new Label(paCorpsFormulaire, SWT.NONE);
					laDATE_OPERATION.setText("Date de l'opération");
				}
				{
					tfDATE_OPERATION  = new DateTime(paCorpsFormulaire,SWT.BORDER | SWT.DROP_DOWN);
					GridData tfDATE_OPERATIONLData = new GridData();
					tfDATE_OPERATIONLData.widthHint = 90;
					tfDATE_OPERATION.setLayoutData(tfDATE_OPERATIONLData);
//					tfDATE_OPERATION.setPattern("dd/MM/yyyy");
				}				
				{
					laCODE_T_OPERATION = new Label(paCorpsFormulaire, SWT.NONE);
					laCODE_T_OPERATION.setText("Type d'Opération");
				}

				{
					GridData tfCODE_T_OPERATIONLData = new GridData();
					tfCODE_T_OPERATIONLData.horizontalAlignment = GridData.BEGINNING;
					tfCODE_T_OPERATIONLData.widthHint = 177;
					if(!decore) {
						tfCODE_T_OPERATION = new Text(paCorpsFormulaire, SWT.BORDER);
						tfCODE_T_OPERATION.setLayoutData(tfCODE_T_OPERATIONLData);
					} else {					
					fieldCODE_T_OPERATION = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfCODE_T_OPERATION = (Text)fieldCODE_T_OPERATION.getControl();
					fieldCODE_T_OPERATION.getLayoutControl().setLayoutData(tfCODE_T_OPERATIONLData);
					}
				}
				
				//
				{
					laT_PAIEMENT = new Label(paCorpsFormulaire, SWT.NONE);
					laT_PAIEMENT.setText("Type de règlement");
				}

				{
					GridData tfT_PAIEMENTLData = new GridData();
					tfT_PAIEMENTLData.horizontalAlignment = GridData.BEGINNING;
					tfT_PAIEMENTLData.widthHint = 177;
					if(!decore) {
						tfT_PAIEMENT = new Text(paCorpsFormulaire, SWT.BORDER);
						tfT_PAIEMENT.setLayoutData(tfT_PAIEMENTLData);
					} else {					
					fieldT_PAIEMENT = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfT_PAIEMENT = (Text)fieldT_PAIEMENT.getControl();
					fieldT_PAIEMENT.getLayoutControl().setLayoutData(tfT_PAIEMENTLData);
					}
				}				
				{
					laETABLISSEMENT = new Label(paCorpsFormulaire, SWT.NONE);
					laETABLISSEMENT.setText("Etablissement");
				}

				{
					GridData tfETABLISSEMENTLData = new GridData();
					tfETABLISSEMENTLData.horizontalAlignment = GridData.BEGINNING;
					tfETABLISSEMENTLData.widthHint = 177;
					if(!decore) {
						tfETABLISSEMENT = new Text(paCorpsFormulaire, SWT.BORDER);
						tfETABLISSEMENT.setLayoutData(tfETABLISSEMENTLData);
					} else {					
					fieldETABLISSEMENT = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfETABLISSEMENT = (Text)fieldETABLISSEMENT.getControl();
					fieldETABLISSEMENT.getLayoutControl().setLayoutData(tfETABLISSEMENTLData);
					}
				}				

				{
					laMONTANT_OPERATION = new Label(paCorpsFormulaire, SWT.NONE);
					laMONTANT_OPERATION.setText("montant de l'opération");
				}

				{
					GridData tfMONTANT_OPERATIONLData = new GridData();
					tfMONTANT_OPERATIONLData.horizontalAlignment = GridData.BEGINNING;
					tfMONTANT_OPERATIONLData.widthHint = 177;
					if(!decore) {
						tfMONTANT_OPERATION = new Text(paCorpsFormulaire, SWT.BORDER);
						tfMONTANT_OPERATION.setLayoutData(tfMONTANT_OPERATIONLData);
					} else {					
					fieldMONTANT_OPERATION = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfMONTANT_OPERATION = (Text)fieldMONTANT_OPERATION.getControl();
					fieldMONTANT_OPERATION.getLayoutControl().setLayoutData(tfMONTANT_OPERATIONLData);
					}
				}				
				
				{
					laLIB_OPERATION = new Label(paCorpsFormulaire, SWT.NONE);
					laLIB_OPERATION.setText("libellé de l'opération");
				}

				{
					GridData tfLIB_OPERATIONLData = new GridData();
					tfLIB_OPERATIONLData.horizontalAlignment = GridData.BEGINNING;
					tfLIB_OPERATIONLData.widthHint = 300;
					if(!decore) {
						tfLIB_OPERATION = new Text(paCorpsFormulaire, SWT.BORDER);
						tfLIB_OPERATION.setLayoutData(tfLIB_OPERATIONLData);
					} else {					
					fieldLIB_OPERATION = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfLIB_OPERATION = (Text)fieldLIB_OPERATION.getControl();
					fieldLIB_OPERATION.getLayoutControl().setLayoutData(tfLIB_OPERATIONLData);
					}
				}				
				{
					laCOMPTE = new Label(paCorpsFormulaire, SWT.NONE);
					laCOMPTE.setText("Compte (achat)");
				}

				{
					GridData tfCOMPTELData = new GridData();
					tfCOMPTELData.horizontalAlignment = GridData.BEGINNING;
					tfCOMPTELData.widthHint = 300;
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
					laTVA = new Label(paCorpsFormulaire, SWT.NONE);
					laTVA.setText("Tva (achat)");
				}

				{
					GridData tfTVALData = new GridData();
					tfTVALData.horizontalAlignment = GridData.BEGINNING;
					tfTVALData.widthHint = 300;
					if(!decore) {
						tfTVA = new Text(paCorpsFormulaire, SWT.BORDER);
						tfTVA.setLayoutData(tfTVALData);
					} else {					
					fieldTVA = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfTVA = (Text)fieldTVA.getControl();
					fieldTVA.getLayoutControl().setLayoutData(tfTVALData);
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

	public Label getLaDATE_OPERATION() {
		return laDATE_OPERATION;
	}

	public Label getLaCODE_T_OPERATION() {
		return laCODE_T_OPERATION;
	}

	public DateTime getTfDATE_OPERATION() {
		return tfDATE_OPERATION;
	}

	public Text getTfCODE_T_OPERATION() {
		return tfCODE_T_OPERATION;
	}

	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public DecoratedField getFieldDATE_OPERATION() {
		return fieldDATE_OPERATION;
	}

	public DecoratedField getFieldCODE_T_OPERATION() {
		return fieldCODE_T_OPERATION;
	}

	public Label getLaT_PAIEMENT() {
		return laT_PAIEMENT;
	}

	public Label getLaMONTANT_OPERATION() {
		return laMONTANT_OPERATION;
	}

	public Label getLaLIB_OPERATION() {
		return laLIB_OPERATION;
	}

	public Text getTfT_PAIEMENT() {
		return tfT_PAIEMENT;
	}

	public Text getTfMONTANT_OPERATION() {
		return tfMONTANT_OPERATION;
	}

	public Text getTfLIB_OPERATION() {
		return tfLIB_OPERATION;
	}

	public DecoratedField getFieldT_PAIEMENT() {
		return fieldT_PAIEMENT;
	}

	public DecoratedField getFieldMONTANT_OPERATION() {
		return fieldMONTANT_OPERATION;
	}

	public DecoratedField getFieldLIB_OPERATION() {
		return fieldLIB_OPERATION;
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

	public Label getLaETABLISSEMENT() {
		return laETABLISSEMENT;
	}

	public Text getTfETABLISSEMENT() {
		return tfETABLISSEMENT;
	}

	public DecoratedField getFieldETABLISSEMENT() {
		return fieldETABLISSEMENT;
	}

	public boolean isDecore() {
		return decore;
	}

	public Label getLaTVA() {
		return laTVA;
	}

	public Text getTfTVA() {
		return tfTVA;
	}

	public DecoratedField getFieldTVA() {
		return fieldTVA;
	}

}

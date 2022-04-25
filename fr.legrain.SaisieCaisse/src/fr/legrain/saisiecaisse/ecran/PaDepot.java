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
public class PaDepot extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	private Composite paCorpsFormulaire;
	private Label laDATE_DEPOT;
	private Label laCODE_T_PAIEMENT;
	private Label laETABLISSEMENT;
	private Label laMONTANT_DEPOT;
	private Label laLIBL_DEPOT;
	
	private DateTime tfDATE_DEPOT;
	private Text tfMONTANT_DEPOT;
	private Text tfCODE_T_PAIEMENT;
	private Text tfETABLISSEMENT;
	private Text tfLIBL_DEPOT;

	private DecoratedField fieldDATE_DEPOT;
	private DecoratedField fieldMONTANT_DEPOT;
	private DecoratedField fieldCODE_T_PAIEMENT;
	private DecoratedField fieldLIBL_DEPOT;
	private DecoratedField fieldETABLISSEMENT;


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
		PaDepot inst = new PaDepot(shell, SWT.NULL);
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

	public PaDepot(org.eclipse.swt.widgets.Composite parent, int style) {
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
					laDATE_DEPOT = new Label(paCorpsFormulaire, SWT.NONE);
					laDATE_DEPOT.setText("Date du dépôt");
				}
				{
					tfDATE_DEPOT  = new DateTime(paCorpsFormulaire,SWT.BORDER | SWT.DROP_DOWN);
					GridData tftfDATE_DEPOTLData = new GridData();
					tftfDATE_DEPOTLData.widthHint = 90;
					tfDATE_DEPOT.setLayoutData(tftfDATE_DEPOTLData);
//					tfDATE_DEPOT.setPattern("dd/MM/yyyy");
				}				
				
				{
					laCODE_T_PAIEMENT = new Label(paCorpsFormulaire, SWT.NONE);
					laCODE_T_PAIEMENT.setText("Type de règlement");
				}

				{
					GridData tfCODE_T_PAIEMENTLData = new GridData();
					tfCODE_T_PAIEMENTLData.horizontalAlignment = GridData.BEGINNING;
					tfCODE_T_PAIEMENTLData.widthHint = 177;
					if(!decore) {
						tfCODE_T_PAIEMENT = new Text(paCorpsFormulaire, SWT.BORDER);
						tfCODE_T_PAIEMENT.setLayoutData(tfCODE_T_PAIEMENTLData);
					} else {					
					fieldCODE_T_PAIEMENT = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfCODE_T_PAIEMENT = (Text)fieldCODE_T_PAIEMENT.getControl();
					fieldCODE_T_PAIEMENT.getLayoutControl().setLayoutData(tfCODE_T_PAIEMENTLData);
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
					laMONTANT_DEPOT = new Label(paCorpsFormulaire, SWT.NONE);
					laMONTANT_DEPOT.setText("Montant du dépôt");
				}
				{
					GridData tfMONTANT_DEPOTLData = new GridData();
					tfMONTANT_DEPOTLData.horizontalAlignment = GridData.BEGINNING;
					tfMONTANT_DEPOTLData.widthHint = 177;
					if(!decore) {
						tfMONTANT_DEPOT = new Text(paCorpsFormulaire, SWT.BORDER);
						tfMONTANT_DEPOT.setLayoutData(tfMONTANT_DEPOTLData);
					} else {					
					fieldMONTANT_DEPOT = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfMONTANT_DEPOT = (Text)fieldMONTANT_DEPOT.getControl();
					fieldMONTANT_DEPOT.getLayoutControl().setLayoutData(tfMONTANT_DEPOTLData);
					}
				}				
				{
					laLIBL_DEPOT = new Label(paCorpsFormulaire, SWT.NONE);
					laLIBL_DEPOT.setText("Libellé du dépôt");
				}
				{
					GridData tfLIBL_DEPOTLData = new GridData();
					tfLIBL_DEPOTLData.horizontalAlignment = GridData.BEGINNING;
					tfLIBL_DEPOTLData.widthHint = 300;
					if(!decore) {
						tfLIBL_DEPOT = new Text(paCorpsFormulaire, SWT.BORDER);
						tfLIBL_DEPOT.setLayoutData(tfLIBL_DEPOTLData);
					} else {					
					fieldLIBL_DEPOT = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfLIBL_DEPOT = (Text)fieldLIBL_DEPOT.getControl();
					fieldLIBL_DEPOT.getLayoutControl().setLayoutData(tfLIBL_DEPOTLData);
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

	public Label getLaLIBL_DEPOT() {
		return laLIBL_DEPOT;
	}

	public Label getLaCODE_T_PAIEMENT() {
		return laCODE_T_PAIEMENT;
	}

	public Text getTfLIBL_DEPOT() {
		return tfLIBL_DEPOT;
	}

	public Text getTfCODE_T_PAIEMENT() {
		return tfCODE_T_PAIEMENT;
	}

	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public DecoratedField getFieldLIBL_DEPOT() {
		return fieldLIBL_DEPOT;
	}

	public DecoratedField getFieldCODE_T_PAIEMENT() {
		return fieldCODE_T_PAIEMENT;
	}

	public Label getLaDATE_DEPOT() {
		return laDATE_DEPOT;
	}

	public Label getLaMONTANT_DEPOT() {
		return laMONTANT_DEPOT;
	}

	public DateTime getTfDATE_DEPOT() {
		return tfDATE_DEPOT;
	}

	public Text getTfMONTANT_DEPOT() {
		return tfMONTANT_DEPOT;
	}

	public DecoratedField getFieldDATE_DEPOT() {
		return fieldDATE_DEPOT;
	}

	public DecoratedField getFieldMONTANT_DEPOT() {
		return fieldMONTANT_DEPOT;
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

}

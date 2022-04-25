package fr.legrain.saisiecaisse.ecran;
import fr.legrain.lib.gui.PaBtn;
import fr.legrain.lib.gui.PaBtnReduit;
import fr.legrain.lib.gui.cdatetimeLgr;
import fr.legrain.libMessageLGR.LgrMess;

import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.fieldassist.TextControlCreator;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;

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
public class PaEtatSaisieCaisse extends org.eclipse.swt.widgets.Composite {
	private PaBtnReduit paBtn1;

	private Label laDATEDEB;
	private Label laCODE_T_OPERATION;
	private Label laCODE_T_PAIEMENT;
	private Button cbReExport;

	private DateTime tfDATEDEB;
	private Text tfCODE_T_OPERATION;
	private Text tfCODE_T_PAIEMENT;
	
	
	private DecoratedField fieldCODE_T_OPERATION;
	private DecoratedField fieldCODE_T_PAIEMENT;

	private Group group1;

	
	final private boolean decore = LgrMess.isDECORE();
	
	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void main(String[] args) {
		showGUI();
	}
		
	/**
	* Auto-generated method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void showGUI() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		PaEtatSaisieCaisse inst = new PaEtatSaisieCaisse(shell, SWT.NULL);
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

	public PaEtatSaisieCaisse(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			this.setLayout(thisLayout);
			thisLayout.numColumns = 4;
			thisLayout.horizontalSpacing = 4;
			this.setSize(705, 411);
			{
				GridData group1LData = new GridData();
				group1LData.horizontalAlignment = GridData.CENTER;
				group1LData.widthHint = 521;
				group1LData.heightHint = 263;
				group1LData.grabExcessHorizontalSpace = true;
				group1LData.grabExcessVerticalSpace = true;
				group1 = new Group(this, SWT.NONE);
				GridLayout group1Layout = new GridLayout();
				group1Layout.numColumns = 4;
				group1.setLayout(group1Layout);
				group1.setLayoutData(group1LData);
				group1.setText("Edition");
				{
					laDATEDEB = new Label(group1, SWT.NONE);
					GridData laDATEDEBLData = new GridData();
					laDATEDEBLData.grabExcessHorizontalSpace = true;
					laDATEDEBLData.horizontalSpan = 2;
					laDATEDEBLData.horizontalAlignment = GridData.FILL;
					laDATEDEBLData.widthHint = 136;
					//laDATEDEBLData.heightHint = 17;
					laDATEDEB.setLayoutData(laDATEDEBLData);
					laDATEDEB.setText("Date de caisse : ");
					//laDATEDEB.setVisible(false);
				}
				{
					tfDATEDEB = new DateTime(group1, SWT.BORDER | SWT.DROP_DOWN);
					GridData tfDATEDEBLData = new GridData();
					tfDATEDEBLData.heightHint = 17;
					tfDATEDEBLData.widthHint = 189;
					tfDATEDEBLData.horizontalSpan = 2;
					tfDATEDEBLData.grabExcessHorizontalSpace = true;
					tfDATEDEB.setLayoutData(tfDATEDEBLData);
//					tfDATEDEB.setPattern("dd/MM/yyyy");
					//tfDATEDEB.setVisible(false);
				}
			{
				laCODE_T_OPERATION = new Label(group1, SWT.NONE);
				GridData laCODE_ARTICLELData = new GridData();
				laCODE_ARTICLELData.widthHint = 80;
				//laCODE_ARTICLELData.heightHint = 17;
				laCODE_T_OPERATION.setLayoutData(laCODE_ARTICLELData);
				laCODE_T_OPERATION.setText("Type d'opération");
			}

			{
				GridData tfCODE_ARTICLELData = new GridData();
				//tfCODE_ARTICLELData.heightHint = 17;
				tfCODE_ARTICLELData.widthHint = 139;
				if(!decore) {
					tfCODE_T_OPERATION = new Text(group1, SWT.BORDER);
					tfCODE_T_OPERATION.setLayoutData(tfCODE_ARTICLELData);
				} else {					
				fieldCODE_T_OPERATION = new DecoratedField(group1, SWT.BORDER, new TextControlCreator());
				tfCODE_T_OPERATION = (Text)fieldCODE_T_OPERATION.getControl();
				fieldCODE_T_OPERATION.getLayoutControl().setLayoutData(tfCODE_ARTICLELData);
				}
			}

			{
				laCODE_T_PAIEMENT = new Label(group1, SWT.NONE);
				laCODE_T_PAIEMENT.setText("Type de paiement");
			}
			{
				GridData tfLIBELLE_STOCKData = new GridData();
				tfLIBELLE_STOCKData.horizontalAlignment = GridData.FILL;
				//tfLIBELLE_STOCKData.heightHint = 17;
				tfLIBELLE_STOCKData.widthHint = 139;
				if(!decore) {
					tfCODE_T_PAIEMENT = new Text(group1, SWT.BORDER);
					tfCODE_T_PAIEMENT.setLayoutData(tfLIBELLE_STOCKData);
				} else {					
					fieldCODE_T_PAIEMENT = new DecoratedField(group1, SWT.BORDER, new TextControlCreator());
				tfCODE_T_PAIEMENT = (Text)fieldCODE_T_PAIEMENT.getControl();
				fieldCODE_T_PAIEMENT.getLayoutControl().setLayoutData(tfLIBELLE_STOCKData);
				}
			}

			{
				cbReExport = new Button(group1, SWT.CHECK | SWT.LEFT);
				GridData cbReExportLData = new GridData();
				cbReExportLData.horizontalSpan = 4;
				cbReExportLData.grabExcessHorizontalSpace = true;
				cbReExportLData.heightHint = 16;
				cbReExport.setLayoutData(cbReExportLData);
				cbReExport.setText("Ré-exporter les documents déjà exportés");
			}
				{
					GridData paBtn1LData = new GridData();
					paBtn1LData.grabExcessHorizontalSpace = true;
					paBtn1LData.horizontalAlignment = GridData.CENTER;
					paBtn1LData.horizontalSpan = 4;
					paBtn1 = new PaBtnReduit(group1, SWT.NONE);
					paBtn1.getBtnImprimer().setText("Calculer[F3]"); 
					GridData btnFermerLData = new GridData();
					btnFermerLData.widthHint = 102;
					btnFermerLData.heightHint = 23;
					paBtn1.setLayoutData(paBtn1LData);
					paBtn1.getBtnFermer().setLayoutData(btnFermerLData);
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	public Label getLaDATEDEB() {
		return laDATEDEB;
	}
	
	public DateTime getTfDATEDEB() {
		return tfDATEDEB;
	}
	
	
	
	public PaBtnReduit getPaBtn1() {
		return paBtn1;
	}

	public Label getLaCODE_T_OPERATION() {
		return laCODE_T_OPERATION;
	}

	public Label getLaCODE_T_PAIEMENT() {
		return laCODE_T_PAIEMENT;
	}



	public Text getTfCODE_T_OPERATION() {
		return tfCODE_T_OPERATION;
	}

	public Text getTfCODE_T_PAIEMENT() {
		return tfCODE_T_PAIEMENT;
	}


	public DecoratedField getFieldCODE_T_OPERATION() {
		return fieldCODE_T_OPERATION;
	}

	public DecoratedField getFieldCODE_T_PAIEMENT() {
		return fieldCODE_T_PAIEMENT;
	}

	public Button getCbReExport() {
		return cbReExport;
	}

	public Group getGroup1() {
		return group1;
	}



}

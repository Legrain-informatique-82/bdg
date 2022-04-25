package fr.legrain.gestioncapsules.stocks.ecrans;
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
import org.eclipse.swt.widgets.DateTime;
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
public class PaStocks extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {


	private Composite paCorpsFormulaire;
	private Label laCODE_TITRE_TRANSPORT;
	private Label laLIBELLE_STOCK;
	private Label laMOUV_STOCK;
	private Label laDATE_STOCK;
	private Label laQTE1_STOCK;
//	private Label laQTE2_STOCK;
//	private Label laUN1_STOCK;
//	private Label laUN2_STOCK;
	private Composite composite3;

	private Text tfCODE_TITRE_TRANSPORT;
	private Text tfLIBELLE_STOCK;
	private Text tfMOUV_STOCK;
	private DateTime tfDATE_STOCK;
	private Text tfQTE1_STOCK;
//	private Text tfQTE2_STOCK;
//	private Text tfUN1_STOCK;
//	private Text tfUN2_STOCK;

	private Button btnCalcul;



	private DecoratedField fieldLIBELLE_STOCK;
	private DecoratedField fieldQTE1_STOCK;
//	private DecoratedField fieldQTE2_STOCK;
	private DecoratedField fieldCODE_TITRE_TRANSPORT;
	private DecoratedField fieldMOUV_STOCK;
//	private DecoratedField fieldUN1_STOCK;
//	private DecoratedField fieldUN2_STOCK;


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
		PaStocks inst = new PaStocks(shell, SWT.NULL);
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

	public PaStocks(org.eclipse.swt.widgets.Composite parent, int style) {
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
					laCODE_TITRE_TRANSPORT = new Label(paCorpsFormulaire, SWT.NONE);
					GridData laCODE_ARTICLELData = new GridData();
					//laCODE_ARTICLELData.widthHint = 80;
					//laCODE_ARTICLELData.heightHint = 13;
					laCODE_TITRE_TRANSPORT.setLayoutData(laCODE_ARTICLELData);
					laCODE_TITRE_TRANSPORT.setText("Code CRD");
				}

				{
					GridData tfCODE_ARTICLELData = new GridData();
					tfCODE_ARTICLELData.horizontalAlignment = GridData.FILL;
					if(!decore) {
						tfCODE_TITRE_TRANSPORT = new Text(paCorpsFormulaire, SWT.BORDER);
						tfCODE_TITRE_TRANSPORT.setLayoutData(tfCODE_ARTICLELData);
					} else {					
					fieldCODE_TITRE_TRANSPORT = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfCODE_TITRE_TRANSPORT = (Text)fieldCODE_TITRE_TRANSPORT.getControl();
					fieldCODE_TITRE_TRANSPORT.getLayoutControl().setLayoutData(tfCODE_ARTICLELData);
					}
				}
				{
					laMOUV_STOCK = new Label(paCorpsFormulaire, SWT.NONE);
					laMOUV_STOCK.setText("Mouvement");
				}
				{
					GridData tfMOUV_STOCKData = new GridData();
					tfMOUV_STOCKData.widthHint = 25;
					if(!decore) {
						tfMOUV_STOCK = new Text(paCorpsFormulaire, SWT.BORDER);
						tfMOUV_STOCK.setLayoutData(tfMOUV_STOCKData);
					} else {					
					fieldMOUV_STOCK = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfMOUV_STOCK = (Text)fieldMOUV_STOCK.getControl();
					fieldMOUV_STOCK.getLayoutControl().setLayoutData(tfMOUV_STOCKData);
					}
				}
				{
					laDATE_STOCK = new Label(paCorpsFormulaire, SWT.NONE);
					laDATE_STOCK.setText("Date");
				}
				{
					tfDATE_STOCK  = new DateTime(paCorpsFormulaire,SWT.BORDER | SWT.DROP_DOWN);
					GridData tfDATE_STOCKLData = new GridData();
					//tfDATE_STOCKLData.widthHint = 90;
					tfDATE_STOCK.setLayoutData(tfDATE_STOCKLData);
//					tfDATE_STOCK.setPattern("dd/MM/yyyy");
				}
				{
					laLIBELLE_STOCK = new Label(paCorpsFormulaire, SWT.NONE);
					laLIBELLE_STOCK.setText("Libellé");
				}
				{
					GridData tfLIBELLE_STOCKData = new GridData();
					tfLIBELLE_STOCKData.horizontalAlignment = GridData.FILL;
					if(!decore) {
						tfLIBELLE_STOCK = new Text(paCorpsFormulaire, SWT.BORDER);
						tfLIBELLE_STOCK.setLayoutData(tfLIBELLE_STOCKData);
					} else {					
						fieldLIBELLE_STOCK = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfLIBELLE_STOCK = (Text)fieldLIBELLE_STOCK.getControl();
					fieldLIBELLE_STOCK.getLayoutControl().setLayoutData(tfLIBELLE_STOCKData);
					}
				}

				{
					laQTE1_STOCK = new Label(paCorpsFormulaire, SWT.NONE);
					laQTE1_STOCK.setText("Qté");
				}
				{
					GridData tfQTE1_STOCKData = new GridData();
					tfQTE1_STOCKData.horizontalAlignment = GridData.FILL;
					if(!decore) {
						tfQTE1_STOCK = new Text(paCorpsFormulaire, SWT.BORDER);
						tfQTE1_STOCK.setLayoutData(tfQTE1_STOCKData);
					} else {					

					fieldQTE1_STOCK = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfQTE1_STOCK = (Text)fieldQTE1_STOCK.getControl();
					fieldQTE1_STOCK.getLayoutControl().setLayoutData(tfQTE1_STOCKData);
					}
				}
//				{
//					laUN1_STOCK = new Label(paCorpsFormulaire, SWT.NONE);
//					laUN1_STOCK.setText("Unité 1");
//				}
//				{
//					GridData tfU1_STOCKLData = new GridData();
//					tfU1_STOCKLData.widthHint = 80;
//					if(!decore) {
//					tfUN1_STOCK = new Text(paCorpsFormulaire, SWT.BORDER);
//					tfUN1_STOCK.setLayoutData(tfU1_STOCKLData);
//					}else{
//						fieldUN1_STOCK = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
//						tfUN1_STOCK = (Text)fieldUN1_STOCK.getControl();
//						fieldUN1_STOCK.getLayoutControl().setLayoutData(tfU1_STOCKLData);
//					}					
//				}
//				
//				{
//					laQTE2_STOCK = new Label(paCorpsFormulaire, SWT.NONE);
//					laQTE2_STOCK.setText("Qté 2");
//				}
//				{
//					GridData tfQTE2_STOCKData = new GridData();
//					tfQTE2_STOCKData.horizontalAlignment = GridData.FILL;
//					if(!decore) {
//						tfQTE2_STOCK = new Text(paCorpsFormulaire, SWT.BORDER);
//						tfQTE2_STOCK.setLayoutData(tfQTE2_STOCKData);
//					} else {					
//					fieldQTE2_STOCK = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
//					tfQTE2_STOCK = (Text)fieldQTE2_STOCK.getControl();
//					fieldQTE2_STOCK.getLayoutControl().setLayoutData(tfQTE2_STOCKData);
//					}
//				}
//				{
//					laUN2_STOCK = new Label(paCorpsFormulaire, SWT.NONE);
//					laUN2_STOCK.setText("Unité 2");
//				}
//				{
//					GridData tfU2_STOCKLData = new GridData();
//					tfU2_STOCKLData.widthHint = 80;
//					if(!decore) {
//					tfUN2_STOCK = new Text(paCorpsFormulaire, SWT.BORDER);
//					tfUN2_STOCK.setLayoutData(tfU2_STOCKLData);
//					}else{
//						fieldUN2_STOCK = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
//						tfUN2_STOCK = (Text)fieldUN2_STOCK.getControl();
//						fieldUN2_STOCK.getLayoutControl().setLayoutData(tfU2_STOCKLData);						
//					}
//				}
				{
					composite3 = new Composite(paCorpsFormulaire, SWT.NONE);
					GridLayout composite3Layout = new GridLayout();
					composite3Layout.makeColumnsEqualWidth = true;
					composite3Layout.numColumns = 2;
					composite3Layout.marginHeight = 10;
					GridData composite3LData = new GridData();
					composite3LData.widthHint = 263;
					composite3LData.heightHint = 43;
					composite3LData.horizontalSpan = 2;
					composite3.setLayoutData(composite3LData);
					composite3.setLayout(composite3Layout);
					{
						btnCalcul = new Button(composite3, SWT.PUSH | SWT.CENTER);
						GridData btnCalculLData = new GridData();
						btnCalculLData.horizontalAlignment = GridData.END;
						btnCalculLData.grabExcessHorizontalSpace = true;
						btnCalculLData.widthHint = 184;
						btnCalculLData.heightHint = 23;
						btnCalcul.setLayoutData(btnCalculLData);
						btnCalcul.setText("Calcul des Reports");
						btnCalcul.setVisible(false);
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

	public Composite getComposite3() {
		return composite3;
	}


	public Label getLaDATE_STOCK() {
		return laDATE_STOCK;
	}

	public Label getLaQTE1_STOCK() {
		return laQTE1_STOCK;
	}

	public Label getLaLIBELLE_STOCK() {
		return laLIBELLE_STOCK;
	}

	public Label getLaCODE_TITRE_TRANSPORT() {
		return laCODE_TITRE_TRANSPORT;
	}

	public Label getLaMOUV_STOCK() {
		return laMOUV_STOCK;
	}


//	public Label getLaQTE2_STOCK() {
//		return laQTE2_STOCK;
//	}

	public Text getTfCODE_TITRE_TRANSPORT() {
		return tfCODE_TITRE_TRANSPORT;
	}

	public DateTime getTfDATE_STOCK() {
		return tfDATE_STOCK;
	}

	public Text getTfQTE1_STOCK() {
		return tfQTE1_STOCK;
	}

	public Text getTfLIBELLE_STOCK() {
		return tfLIBELLE_STOCK;
	}

	public Text getTfMOUV_STOCK() {
		return tfMOUV_STOCK;
	}


//	public Text getTfQTE2_STOCK() {
//		return tfQTE2_STOCK;
//	}

	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public DecoratedField getFieldCODE_TITRE_TRANSPORT() {
		return fieldCODE_TITRE_TRANSPORT;
	}


	public DecoratedField getFieldQTE1_STOCK() {
		return fieldQTE1_STOCK;
	}

	public DecoratedField getFieldLIBELLE_STOCK() {
		return fieldLIBELLE_STOCK;
	}

	public DecoratedField getFieldMOUV_STOCK() {
		return fieldMOUV_STOCK;
	}

//	public DecoratedField getFieldQTE2_STOCK() {
//		return fieldQTE2_STOCK;
//	}
//	
//	public Text getTfUN2_STOCK() {
//		return tfUN2_STOCK;
//	}
//
//	public Label getLaUN2_STOCK() {
//		return laUN2_STOCK;
//	}
//
//	public Text getTfUN1_STOCK() {
//		return tfUN1_STOCK;
//	}
//
//	public Label getLaUN1_STOCK() {
//		return laUN1_STOCK;
//	}
//
//	public DecoratedField getFieldU1_STOCK() {
//		return fieldUN1_STOCK;
//	}
//
//	public DecoratedField getFieldU2_STOCK() {
//		return fieldUN2_STOCK;
//	}
	
	public Button getBtnCalcul() {
		return btnCalcul;
	}

}

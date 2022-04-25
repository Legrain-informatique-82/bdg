package fr.legrain.stocks.ecran;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.fieldassist.TextControlCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import fr.legrain.lib.gui.PaBtnReduit;
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
public class PaEtatStocks extends org.eclipse.swt.widgets.Composite {
	private PaBtnReduit paBtn1;

	private Label laDATEDEB;
	private Label laDATEFIN;
	private Label laCODE_ARTICLE;
	private Label laLIBELLE_STOCK;
	private Label laQTE1_STOCK;
	private Label laQTE2_STOCK;
	private Label laUN1_STOCK;
	private Label laUN2_STOCK;

	private DateTime tfDATEDEB;
	private DateTime tfDATEFIN;
	private Text tfCODE_ARTICLE;
	private Text tfLIBELLE_STOCK;
	private Text tfQTE1_STOCK;
	private Text tfQTE2_STOCK;
	private Text tfUN1_STOCK;
	private Text tfUN2_STOCK;
	private Button cbExclusion;
	
	private Button cbEditSynthese;
	private Button cbEditMouvDate;
	private Button cbEditMouvType;
	
	
	private DecoratedField fieldCODE_ARTICLE;
	private DecoratedField fieldLIBELLE_STOCK;
	private DecoratedField fieldQTE1_STOCK;
	private DecoratedField fieldQTE2_STOCK;
	private DecoratedField fieldUN1_STOCK;
	private Group group1;
	private DecoratedField fieldUN2_STOCK;
	
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
		PaEtatStocks inst = new PaEtatStocks(shell, SWT.NULL);
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

	public PaEtatStocks(org.eclipse.swt.widgets.Composite parent, int style) {
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
				group1LData.widthHint = 441;
				group1LData.grabExcessHorizontalSpace = true;
				group1LData.grabExcessVerticalSpace = true;
				group1 = new Group(this, SWT.NONE);
				GridLayout group1Layout = new GridLayout();
				group1Layout.numColumns = 4;
				group1Layout.marginTop = 8;
				group1.setLayout(group1Layout);
				group1.setLayoutData(group1LData);
				{
					laDATEDEB = new Label(group1, SWT.NONE);
					GridData laDATEDEBLData = new GridData();
					laDATEDEBLData.grabExcessHorizontalSpace = true;
					laDATEDEB.setLayoutData(laDATEDEBLData);
					laDATEDEB.setText("Date début");
					//laDATEDEB.setVisible(false);
				}
				{
					tfDATEDEB = new DateTime(group1, SWT.BORDER | SWT.DROP_DOWN);
					GridData tfDATEDEBLData = new GridData();
					//tfDATEDEBLData.heightHint = 17;
					tfDATEDEBLData.horizontalAlignment = GridData.CENTER;
					tfDATEDEBLData.widthHint = 153;
					tfDATEDEBLData.verticalAlignment = GridData.FILL;
					tfDATEDEB.setLayoutData(tfDATEDEBLData);
//					tfDATEDEB.setPattern("dd/MM/yyyy");
					//tfDATEDEB.setVisible(false);
				}
				{
					laDATEFIN = new Label(group1, SWT.NONE);
					GridData laDATEFINLData = new GridData();
					laDATEFINLData.grabExcessHorizontalSpace = true;
					laDATEFINLData.horizontalAlignment = GridData.END;
					laDATEFINLData.heightHint = 13;
					laDATEFINLData.widthHint = 51;
					laDATEFIN.setLayoutData(laDATEFINLData);
					laDATEFIN.setText("Date fin");
				}
				{
					GridData tfDATEFINLData = new GridData();
					tfDATEFINLData.horizontalAlignment = GridData.CENTER;
					tfDATEFIN = new DateTime(group1, SWT.BORDER | SWT.DROP_DOWN);
					GridData tfDATEFINLData1 = new GridData();
					tfDATEFINLData1.widthHint = 148;
					tfDATEFINLData1.verticalAlignment = GridData.FILL;
					//tfDATEFINLData1.heightHint = 17;
					tfDATEFIN.setLayoutData(tfDATEFINLData1);
//					tfDATEFIN.setPattern("dd/MM/yyyy");
				}
			{
				laCODE_ARTICLE = new Label(group1, SWT.NONE);
				GridData laCODE_ARTICLELData = new GridData();
				laCODE_ARTICLELData.horizontalAlignment = GridData.FILL;
				//laCODE_ARTICLELData.heightHint = 13;
				laCODE_ARTICLE.setLayoutData(laCODE_ARTICLELData);
				laCODE_ARTICLE.setText("Code article");
			}

			{
				GridData tfCODE_ARTICLELData = new GridData();
				tfCODE_ARTICLELData.horizontalSpan = 3;
				//tfCODE_ARTICLELData.heightHint = 13;
				tfCODE_ARTICLELData.widthHint = 141;
				if(!decore) {
					tfCODE_ARTICLE = new Text(group1, SWT.BORDER);
					tfCODE_ARTICLE.setLayoutData(tfCODE_ARTICLELData);
				} else {					
				fieldCODE_ARTICLE = new DecoratedField(group1, SWT.BORDER, new TextControlCreator());
				tfCODE_ARTICLE = (Text)fieldCODE_ARTICLE.getControl();
				fieldCODE_ARTICLE.getLayoutControl().setLayoutData(tfCODE_ARTICLELData);
				}
			}

			{
				laLIBELLE_STOCK = new Label(group1, SWT.NONE);
				GridData laLIBELLE_STOCKLData = new GridData();
				laLIBELLE_STOCKLData.widthHint = 45;
				laLIBELLE_STOCKLData.heightHint = 13;
				laLIBELLE_STOCK.setLayoutData(laLIBELLE_STOCKLData);
				laLIBELLE_STOCK.setText("Libellé");
			}
			{
				GridData tfLIBELLE_STOCKData = new GridData();
				tfLIBELLE_STOCKData.horizontalAlignment = GridData.FILL;
				tfLIBELLE_STOCKData.horizontalSpan = 3;
				if(!decore) {
					tfLIBELLE_STOCK = new Text(group1, SWT.BORDER);
					tfLIBELLE_STOCK.setLayoutData(tfLIBELLE_STOCKData);
				} else {					
					fieldLIBELLE_STOCK = new DecoratedField(group1, SWT.BORDER, new TextControlCreator());
				tfLIBELLE_STOCK = (Text)fieldLIBELLE_STOCK.getControl();
				fieldLIBELLE_STOCK.getLayoutControl().setLayoutData(tfLIBELLE_STOCKData);
				}
			}

			{
				laQTE1_STOCK = new Label(group1, SWT.NONE);
				laQTE1_STOCK.setText("Qté 1");
			}
			{
				GridData tfQTE1_STOCKData = new GridData();
				tfQTE1_STOCKData.horizontalAlignment = GridData.FILL;
				if(!decore) {
					tfQTE1_STOCK = new Text(group1, SWT.BORDER);
					tfQTE1_STOCK.setLayoutData(tfQTE1_STOCKData);
				} else {					

				fieldQTE1_STOCK = new DecoratedField(group1, SWT.BORDER, new TextControlCreator());
				tfQTE1_STOCK = (Text)fieldQTE1_STOCK.getControl();
				fieldQTE1_STOCK.getLayoutControl().setLayoutData(tfQTE1_STOCKData);
				}
			}
			{
				laUN1_STOCK = new Label(group1, SWT.NONE);
				laUN1_STOCK.setText("Unité 1");
			}
			{
				GridData tfU1_STOCKLData = new GridData();
				tfU1_STOCKLData.widthHint = 153;
				//tfU1_STOCKLData.heightHint = 13;
				

				if(!decore) {
				tfUN1_STOCK = new Text(group1, SWT.BORDER);
				tfUN1_STOCK.setLayoutData(tfU1_STOCKLData);
				tfUN1_STOCK.setSize(153, 13);
				}else{
					fieldUN1_STOCK = new DecoratedField(group1, SWT.BORDER, new TextControlCreator());
					tfUN1_STOCK = (Text)fieldUN1_STOCK.getControl();
					fieldUN1_STOCK.getLayoutControl().setLayoutData(tfU1_STOCKLData);
				}					
			}
			
			{
				laQTE2_STOCK = new Label(group1, SWT.NONE);
				laQTE2_STOCK.setText("Qté 2");
			}
			{
				GridData tfQTE2_STOCKData = new GridData();
				tfQTE2_STOCKData.horizontalAlignment = GridData.FILL;
				if(!decore) {
					tfQTE2_STOCK = new Text(group1, SWT.BORDER);
					tfQTE2_STOCK.setLayoutData(tfQTE2_STOCKData);
				} else {					
				fieldQTE2_STOCK = new DecoratedField(group1, SWT.BORDER, new TextControlCreator());
				tfQTE2_STOCK = (Text)fieldQTE2_STOCK.getControl();
				fieldQTE2_STOCK.getLayoutControl().setLayoutData(tfQTE2_STOCKData);
				}
			}
			
			{
				laUN2_STOCK = new Label(group1, SWT.NONE);
				laUN2_STOCK.setText("Unité 2");
			}
			{
				GridData tfU2_STOCKLData = new GridData();
				tfU2_STOCKLData.widthHint = 153;
				//tfU2_STOCKLData.heightHint = 13;
				if(!decore) {
				tfUN2_STOCK = new Text(group1, SWT.BORDER);
				tfUN2_STOCK.setLayoutData(tfU2_STOCKLData);
				tfUN2_STOCK.setSize(153, 17);
				}else{
					fieldUN2_STOCK = new DecoratedField(group1, SWT.BORDER, new TextControlCreator());
					tfUN2_STOCK = (Text)fieldUN2_STOCK.getControl();
					fieldUN2_STOCK.getLayoutControl().setLayoutData(tfU2_STOCKLData);
					fieldUN2_STOCK.getLayoutControl().setSize(153, 17);
				}
			}
			
			{
				cbExclusion = new Button(group1, SWT.CHECK | SWT.LEFT);
				GridData cbChoixImpressionLData = new GridData();
				cbChoixImpressionLData.horizontalSpan = 4;
				cbChoixImpressionLData.grabExcessHorizontalSpace = true;
				cbChoixImpressionLData.horizontalAlignment = GridData.FILL;
				cbExclusion.setLayoutData(cbChoixImpressionLData);
				cbExclusion.setText("Exclure Qté sans unité");
			}

			{
				cbEditSynthese = new Button(group1, SWT.CHECK | SWT.LEFT);
				GridData cbChoixImpressionLData = new GridData();
				cbChoixImpressionLData.horizontalSpan = 4;
				cbChoixImpressionLData.grabExcessHorizontalSpace = true;
				cbChoixImpressionLData.horizontalAlignment = GridData.FILL;
				cbEditSynthese.setLayoutData(cbChoixImpressionLData);
				cbEditSynthese.setText("Synthèse");
			}
			{
				cbEditMouvDate = new Button(group1, SWT.CHECK | SWT.LEFT);
				GridData cbChoixImpressionLData = new GridData();
				cbChoixImpressionLData.horizontalSpan = 4;
				cbChoixImpressionLData.grabExcessHorizontalSpace = true;
				cbChoixImpressionLData.horizontalAlignment = GridData.FILL;
				cbEditMouvDate.setLayoutData(cbChoixImpressionLData);
				cbEditMouvDate.setText("Mouvements par date");
			}
			{
				cbEditMouvType = new Button(group1, SWT.CHECK | SWT.LEFT);
				GridData cbChoixImpressionLData = new GridData();
				cbChoixImpressionLData.horizontalSpan = 4;
				cbChoixImpressionLData.grabExcessHorizontalSpace = true;
				cbChoixImpressionLData.horizontalAlignment = GridData.FILL;
				cbEditMouvType.setLayoutData(cbChoixImpressionLData);
				cbEditMouvType.setText("Mouvements par type");
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
	
	public Label getLaDATEFIN() {
		return laDATEFIN;
	}
	
	public DateTime getTfDATEFIN() {
		return tfDATEFIN;
	}
	
	
	public PaBtnReduit getPaBtn1() {
		return paBtn1;
	}

	public Label getLaCODE_ARTICLE() {
		return laCODE_ARTICLE;
	}

	public Label getLaLIBELLE_STOCK() {
		return laLIBELLE_STOCK;
	}

	public Label getLaQTE1_STOCK() {
		return laQTE1_STOCK;
	}

	public Label getLaQTE2_STOCK() {
		return laQTE2_STOCK;
	}

	public Label getLaUN1_STOCK() {
		return laUN1_STOCK;
	}

	public Label getLaUN2_STOCK() {
		return laUN2_STOCK;
	}

	public Text getTfCODE_ARTICLE() {
		return tfCODE_ARTICLE;
	}

	public Text getTfLIBELLE_STOCK() {
		return tfLIBELLE_STOCK;
	}

	public Text getTfQTE1_STOCK() {
		return tfQTE1_STOCK;
	}

	public Text getTfQTE2_STOCK() {
		return tfQTE2_STOCK;
	}

	public Text getTfUN1_STOCK() {
		return tfUN1_STOCK;
	}

	public Text getTfUN2_STOCK() {
		return tfUN2_STOCK;
	}

	public DecoratedField getFieldCODE_ARTICLE() {
		return fieldCODE_ARTICLE;
	}

	public DecoratedField getFieldLIBELLE_STOCK() {
		return fieldLIBELLE_STOCK;
	}

	public DecoratedField getFieldQTE1_STOCK() {
		return fieldQTE1_STOCK;
	}

	public DecoratedField getFieldQTE2_STOCK() {
		return fieldQTE2_STOCK;
	}

	public DecoratedField getFieldUN1_STOCK() {
		return fieldUN1_STOCK;
	}

	public DecoratedField getFieldUN2_STOCK() {
		return fieldUN2_STOCK;
	}

	public Group getGroup1() {
		return group1;
	}

	public Button getCbExclusion() {
		return cbExclusion;
	}

	public Button getCbEditSynthese() {
		return cbEditSynthese;
	}

	public Button getCbEditMouvDate() {
		return cbEditMouvDate;
	}

	public Button getCbEditMouvType() {
		return cbEditMouvType;
	}

}

package fr.legrain.articles.champsupp.editors;

import java.util.LinkedHashMap;
import java.util.Map;

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
public class PaChampSuppArticle extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

	private Composite paCorpsFormulaire;
	private Label laChamp1;
	private Label laChamp2;
	private Label laChamp3;
	private Label laChamp4;
	private Label laChamp5;
	private Label laChamp6;
	private Label laChamp7;
	private Label laChamp8;
	private Label laChamp9;
	private Label laChamp10;
	private Label laChamp11;
	private Label laChamp12;
	private Label laChamp13;
	private Label laChamp14;
	private Label laChamp15;
	private Label laChamp16;
	private Label laChamp17;
	private Label laChamp18;
	private Label laChamp19;
	private Label laChamp20;
	private Label laChamp21;
	private Label laChamp22;
	private Label laChamp23;
	private Label laChamp24;
	private Label laChamp25;
	private Label laChamp26;
	private Label laChamp27;
	private Label laChamp28;
	private Label laChamp29;
	private Label laChamp30;
	
	private Text tfChamp1;
	private Text tfChamp2;
	private Text tfChamp3;
	private Text tfChamp4;
	private Text tfChamp5;
	private Text tfChamp6;
	private Text tfChamp7;
	private Text tfChamp8;
	private Text tfChamp9;
	private Text tfChamp10;
	private Text tfChamp11;
	private Text tfChamp12;
	private Text tfChamp13;
	private Text tfChamp14;
	private Text tfChamp15;
	private Text tfChamp16;
	private Text tfChamp17;
	private Text tfChamp18;
	private Text tfChamp19;
	private Text tfChamp20;
	private Text tfChamp21;
	private Text tfChamp22;
	private Text tfChamp23;
	private Text tfChamp24;
	private Text tfChamp25;
	private Text tfChamp26;
	private Text tfChamp27;
	private Text tfChamp28;
	private Text tfChamp29;
	private Text tfChamp30;
	
	private DecoratedField fieldChamp1;
	private DecoratedField fieldChamp2;
	private DecoratedField fieldChamp3;
	private DecoratedField fieldChamp4;
	private DecoratedField fieldChamp5;
	private DecoratedField fieldChamp6;
	private DecoratedField fieldChamp7;
	private DecoratedField fieldChamp8;
	private DecoratedField fieldChamp9;
	private DecoratedField fieldChamp10;
	private DecoratedField fieldChamp11;
	private DecoratedField fieldChamp12;
	private DecoratedField fieldChamp13;
	private DecoratedField fieldChamp14;
	private DecoratedField fieldChamp15;
	private DecoratedField fieldChamp16;
	private DecoratedField fieldChamp17;
	private DecoratedField fieldChamp18;
	private DecoratedField fieldChamp19;
	private DecoratedField fieldChamp20;
	private DecoratedField fieldChamp21;
	private DecoratedField fieldChamp22;
	private DecoratedField fieldChamp23;
	private DecoratedField fieldChamp24;
	private DecoratedField fieldChamp25;
	private DecoratedField fieldChamp26;
	private DecoratedField fieldChamp27;
	private DecoratedField fieldChamp28;
	private DecoratedField fieldChamp29;
	private DecoratedField fieldChamp30;

	final private boolean decore = LgrMess.isDECORE();
	
	private Map<Label, Text> listeChamp = new LinkedHashMap<Label, Text>();

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
		PaChampSuppArticle inst = new PaChampSuppArticle(shell, SWT.NULL);
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

	public PaChampSuppArticle(org.eclipse.swt.widgets.Composite parent, int style) {
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
				composite1LData.grabExcessVerticalSpace = true;
				paCorpsFormulaire.setLayoutData(composite1LData);
				paCorpsFormulaire.setLayout(composite1Layout);
				
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
			
			{
				laChamp1 = new Label(paCorpsFormulaire, SWT.NONE);
				laChamp1.setText("laChamp1");
			}

			{
				GridData tfNomLData = new GridData();
				tfNomLData.horizontalAlignment = GridData.CENTER;
				tfNomLData.widthHint = 177;
				if(!decore) {
					tfChamp1 = new Text(paCorpsFormulaire, SWT.BORDER);
					tfChamp1.setLayoutData(tfNomLData);
				} else {					
					fieldChamp1 = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfChamp1 = (Text)fieldChamp1.getControl();
					fieldChamp1.getLayoutControl().setLayoutData(tfNomLData);
				}
			}
			
			{
				laChamp2 = new Label(paCorpsFormulaire, SWT.NONE);
				laChamp2.setText("laChamp2");
			}

			{
				GridData tfNomLData = new GridData();
				tfNomLData.horizontalAlignment = GridData.CENTER;
				tfNomLData.widthHint = 177;
				if(!decore) {
					tfChamp2 = new Text(paCorpsFormulaire, SWT.BORDER);
					tfChamp2.setLayoutData(tfNomLData);
				} else {					
					fieldChamp2 = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfChamp2 = (Text)fieldChamp2.getControl();
					fieldChamp2.getLayoutControl().setLayoutData(tfNomLData);
				}
			}
			
			{
				laChamp3 = new Label(paCorpsFormulaire, SWT.NONE);
				laChamp3.setText("laChamp3");
			}

			{
				GridData tfNomLData = new GridData();
				tfNomLData.horizontalAlignment = GridData.CENTER;
				tfNomLData.widthHint = 177;
				if(!decore) {
					tfChamp3 = new Text(paCorpsFormulaire, SWT.BORDER);
					tfChamp3.setLayoutData(tfNomLData);
				} else {					
					fieldChamp3 = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfChamp3 = (Text)fieldChamp3.getControl();
					fieldChamp3.getLayoutControl().setLayoutData(tfNomLData);
				}
			}
			
			{
				laChamp4 = new Label(paCorpsFormulaire, SWT.NONE);
				laChamp4.setText("laChamp4");
			}

			{
				GridData tfNomLData = new GridData();
				tfNomLData.horizontalAlignment = GridData.CENTER;
				tfNomLData.widthHint = 177;
				if(!decore) {
					tfChamp4 = new Text(paCorpsFormulaire, SWT.BORDER);
					tfChamp4.setLayoutData(tfNomLData);
				} else {					
					fieldChamp4 = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfChamp4 = (Text)fieldChamp4.getControl();
					fieldChamp4.getLayoutControl().setLayoutData(tfNomLData);
				}
			}
			
			{
				laChamp5 = new Label(paCorpsFormulaire, SWT.NONE);
				laChamp5.setText("laChamp5");
			}

			{
				GridData tfNomLData = new GridData();
				tfNomLData.horizontalAlignment = GridData.CENTER;
				tfNomLData.widthHint = 177;
				if(!decore) {
					tfChamp5 = new Text(paCorpsFormulaire, SWT.BORDER);
					tfChamp5.setLayoutData(tfNomLData);
				} else {					
					fieldChamp5 = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfChamp5 = (Text)fieldChamp5.getControl();
					fieldChamp5.getLayoutControl().setLayoutData(tfNomLData);
				}
			}
			
			{
				laChamp6 = new Label(paCorpsFormulaire, SWT.NONE);
				laChamp6.setText("laChamp6");
			}

			{
				GridData tfNomLData = new GridData();
				tfNomLData.horizontalAlignment = GridData.CENTER;
				tfNomLData.widthHint = 177;
				if(!decore) {
					tfChamp6 = new Text(paCorpsFormulaire, SWT.BORDER);
					tfChamp6.setLayoutData(tfNomLData);
				} else {					
					fieldChamp6 = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfChamp6 = (Text)fieldChamp6.getControl();
					fieldChamp6.getLayoutControl().setLayoutData(tfNomLData);
				}
			}
			
			{
				laChamp7 = new Label(paCorpsFormulaire, SWT.NONE);
				laChamp7.setText("laChamp7");
			}

			{
				GridData tfNomLData = new GridData();
				tfNomLData.horizontalAlignment = GridData.CENTER;
				tfNomLData.widthHint = 177;
				if(!decore) {
					tfChamp7 = new Text(paCorpsFormulaire, SWT.BORDER);
					tfChamp7.setLayoutData(tfNomLData);
				} else {					
					fieldChamp7 = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfChamp7 = (Text)fieldChamp7.getControl();
					fieldChamp7.getLayoutControl().setLayoutData(tfNomLData);
				}
			}
			
			{
				laChamp8 = new Label(paCorpsFormulaire, SWT.NONE);
				laChamp8.setText("laChamp8");
			}

			{
				GridData tfNomLData = new GridData();
				tfNomLData.horizontalAlignment = GridData.CENTER;
				tfNomLData.widthHint = 177;
				if(!decore) {
					tfChamp8 = new Text(paCorpsFormulaire, SWT.BORDER);
					tfChamp8.setLayoutData(tfNomLData);
				} else {					
					fieldChamp8 = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfChamp8 = (Text)fieldChamp8.getControl();
					fieldChamp8.getLayoutControl().setLayoutData(tfNomLData);
				}
			}
			
			{
				laChamp9 = new Label(paCorpsFormulaire, SWT.NONE);
				laChamp9.setText("laChamp9");
			}

			{
				GridData tfNomLData = new GridData();
				tfNomLData.horizontalAlignment = GridData.CENTER;
				tfNomLData.widthHint = 177;
				if(!decore) {
					tfChamp9 = new Text(paCorpsFormulaire, SWT.BORDER);
					tfChamp9.setLayoutData(tfNomLData);
				} else {					
					fieldChamp9 = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfChamp9 = (Text)fieldChamp9.getControl();
					fieldChamp9.getLayoutControl().setLayoutData(tfNomLData);
				}
			}
			
			{
				laChamp10 = new Label(paCorpsFormulaire, SWT.NONE);
				laChamp10.setText("laChamp10");
			}

			{
				GridData tfNomLData = new GridData();
				tfNomLData.horizontalAlignment = GridData.CENTER;
				tfNomLData.widthHint = 177;
				if(!decore) {
					tfChamp10 = new Text(paCorpsFormulaire, SWT.BORDER);
					tfChamp10.setLayoutData(tfNomLData);
				} else {					
					fieldChamp10 = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfChamp10 = (Text)fieldChamp10.getControl();
					fieldChamp10.getLayoutControl().setLayoutData(tfNomLData);
				}
			}
			
			{
				laChamp11 = new Label(paCorpsFormulaire, SWT.NONE);
				laChamp11.setText("laChamp11");
			}

			{
				GridData tfNomLData = new GridData();
				tfNomLData.horizontalAlignment = GridData.CENTER;
				tfNomLData.widthHint = 177;
				if(!decore) {
					tfChamp11 = new Text(paCorpsFormulaire, SWT.BORDER);
					tfChamp11.setLayoutData(tfNomLData);
				} else {					
					fieldChamp11 = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfChamp11 = (Text)fieldChamp11.getControl();
					fieldChamp11.getLayoutControl().setLayoutData(tfNomLData);
				}
			}
			
			{
				laChamp12 = new Label(paCorpsFormulaire, SWT.NONE);
				laChamp12.setText("laChamp12");
			}

			{
				GridData tfNomLData = new GridData();
				tfNomLData.horizontalAlignment = GridData.CENTER;
				tfNomLData.widthHint = 177;
				if(!decore) {
					tfChamp12 = new Text(paCorpsFormulaire, SWT.BORDER);
					tfChamp12.setLayoutData(tfNomLData);
				} else {					
					fieldChamp12 = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfChamp12 = (Text)fieldChamp12.getControl();
					fieldChamp12.getLayoutControl().setLayoutData(tfNomLData);
				}
			}
			
			{
				laChamp13 = new Label(paCorpsFormulaire, SWT.NONE);
				laChamp13.setText("laChamp13");
			}

			{
				GridData tfNomLData = new GridData();
				tfNomLData.horizontalAlignment = GridData.CENTER;
				tfNomLData.widthHint = 177;
				if(!decore) {
					tfChamp13 = new Text(paCorpsFormulaire, SWT.BORDER);
					tfChamp13.setLayoutData(tfNomLData);
				} else {					
					fieldChamp13 = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfChamp13 = (Text)fieldChamp13.getControl();
					fieldChamp13.getLayoutControl().setLayoutData(tfNomLData);
				}
			}
			
			{
				laChamp14 = new Label(paCorpsFormulaire, SWT.NONE);
				laChamp14.setText("laChamp14");
			}

			{
				GridData tfNomLData = new GridData();
				tfNomLData.horizontalAlignment = GridData.CENTER;
				tfNomLData.widthHint = 177;
				if(!decore) {
					tfChamp14 = new Text(paCorpsFormulaire, SWT.BORDER);
					tfChamp14.setLayoutData(tfNomLData);
				} else {					
					fieldChamp14 = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfChamp14 = (Text)fieldChamp14.getControl();
					fieldChamp14.getLayoutControl().setLayoutData(tfNomLData);
				}
			}
			
			{
				laChamp15 = new Label(paCorpsFormulaire, SWT.NONE);
				laChamp15.setText("laChamp15");
			}

			{
				GridData tfNomLData = new GridData();
				tfNomLData.horizontalAlignment = GridData.CENTER;
				tfNomLData.widthHint = 177;
				if(!decore) {
					tfChamp15 = new Text(paCorpsFormulaire, SWT.BORDER);
					tfChamp15.setLayoutData(tfNomLData);
				} else {					
					fieldChamp15 = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfChamp15 = (Text)fieldChamp15.getControl();
					fieldChamp15.getLayoutControl().setLayoutData(tfNomLData);
				}
			}
			
			{
				laChamp16 = new Label(paCorpsFormulaire, SWT.NONE);
				laChamp16.setText("laChamp16");
			}

			{
				GridData tfNomLData = new GridData();
				tfNomLData.horizontalAlignment = GridData.CENTER;
				tfNomLData.widthHint = 177;
				if(!decore) {
					tfChamp16 = new Text(paCorpsFormulaire, SWT.BORDER);
					tfChamp16.setLayoutData(tfNomLData);
				} else {					
					fieldChamp16 = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfChamp16 = (Text)fieldChamp16.getControl();
					fieldChamp16.getLayoutControl().setLayoutData(tfNomLData);
				}
			}
			
			{
				laChamp17 = new Label(paCorpsFormulaire, SWT.NONE);
				laChamp17.setText("laChamp17");
			}

			{
				GridData tfNomLData = new GridData();
				tfNomLData.horizontalAlignment = GridData.CENTER;
				tfNomLData.widthHint = 177;
				if(!decore) {
					tfChamp17 = new Text(paCorpsFormulaire, SWT.BORDER);
					tfChamp17.setLayoutData(tfNomLData);
				} else {					
					fieldChamp17 = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfChamp17 = (Text)fieldChamp17.getControl();
					fieldChamp17.getLayoutControl().setLayoutData(tfNomLData);
				}
			}
			
			{
				laChamp18 = new Label(paCorpsFormulaire, SWT.NONE);
				laChamp18.setText("laChamp18");
			}

			{
				GridData tfNomLData = new GridData();
				tfNomLData.horizontalAlignment = GridData.CENTER;
				tfNomLData.widthHint = 177;
				if(!decore) {
					tfChamp18 = new Text(paCorpsFormulaire, SWT.BORDER);
					tfChamp18.setLayoutData(tfNomLData);
				} else {					
					fieldChamp18 = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfChamp18 = (Text)fieldChamp18.getControl();
					fieldChamp18.getLayoutControl().setLayoutData(tfNomLData);
				}
			}
			
			{
				laChamp19 = new Label(paCorpsFormulaire, SWT.NONE);
				laChamp19.setText("laChamp19");
			}

			{
				GridData tfNomLData = new GridData();
				tfNomLData.horizontalAlignment = GridData.CENTER;
				tfNomLData.widthHint = 177;
				if(!decore) {
					tfChamp19 = new Text(paCorpsFormulaire, SWT.BORDER);
					tfChamp19.setLayoutData(tfNomLData);
				} else {					
					fieldChamp19 = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfChamp19 = (Text)fieldChamp19.getControl();
					fieldChamp19.getLayoutControl().setLayoutData(tfNomLData);
				}
			}
			
			{
				laChamp20 = new Label(paCorpsFormulaire, SWT.NONE);
				laChamp20.setText("laChamp20");
			}

			{
				GridData tfNomLData = new GridData();
				tfNomLData.horizontalAlignment = GridData.CENTER;
				tfNomLData.widthHint = 177;
				if(!decore) {
					tfChamp20 = new Text(paCorpsFormulaire, SWT.BORDER);
					tfChamp20.setLayoutData(tfNomLData);
				} else {					
					fieldChamp20 = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfChamp20 = (Text)fieldChamp20.getControl();
					fieldChamp20.getLayoutControl().setLayoutData(tfNomLData);
				}
			}
			
			{
				laChamp21 = new Label(paCorpsFormulaire, SWT.NONE);
				laChamp21.setText("laChamp21");
			}

			{
				GridData tfNomLData = new GridData();
				tfNomLData.horizontalAlignment = GridData.CENTER;
				tfNomLData.widthHint = 177;
				if(!decore) {
					tfChamp21 = new Text(paCorpsFormulaire, SWT.BORDER);
					tfChamp21.setLayoutData(tfNomLData);
				} else {					
					fieldChamp21 = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfChamp21 = (Text)fieldChamp21.getControl();
					fieldChamp21.getLayoutControl().setLayoutData(tfNomLData);
				}
			}
			
			{
				laChamp22 = new Label(paCorpsFormulaire, SWT.NONE);
				laChamp22.setText("laChamp22");
			}

			{
				GridData tfNomLData = new GridData();
				tfNomLData.horizontalAlignment = GridData.CENTER;
				tfNomLData.widthHint = 177;
				if(!decore) {
					tfChamp22 = new Text(paCorpsFormulaire, SWT.BORDER);
					tfChamp22.setLayoutData(tfNomLData);
				} else {					
					fieldChamp22 = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfChamp22 = (Text)fieldChamp22.getControl();
					fieldChamp22.getLayoutControl().setLayoutData(tfNomLData);
				}
			}
			
			{
				laChamp23 = new Label(paCorpsFormulaire, SWT.NONE);
				laChamp23.setText("laChamp23");
			}

			{
				GridData tfNomLData = new GridData();
				tfNomLData.horizontalAlignment = GridData.CENTER;
				tfNomLData.widthHint = 177;
				if(!decore) {
					tfChamp23 = new Text(paCorpsFormulaire, SWT.BORDER);
					tfChamp23.setLayoutData(tfNomLData);
				} else {					
					fieldChamp23 = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfChamp23 = (Text)fieldChamp23.getControl();
					fieldChamp23.getLayoutControl().setLayoutData(tfNomLData);
				}
			}
			
			{
				laChamp24 = new Label(paCorpsFormulaire, SWT.NONE);
				laChamp24.setText("laChamp24");
			}

			{
				GridData tfNomLData = new GridData();
				tfNomLData.horizontalAlignment = GridData.CENTER;
				tfNomLData.widthHint = 177;
				if(!decore) {
					tfChamp24 = new Text(paCorpsFormulaire, SWT.BORDER);
					tfChamp24.setLayoutData(tfNomLData);
				} else {					
					fieldChamp24 = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfChamp24 = (Text)fieldChamp24.getControl();
					fieldChamp24.getLayoutControl().setLayoutData(tfNomLData);
				}
			}
			
			{
				laChamp25 = new Label(paCorpsFormulaire, SWT.NONE);
				laChamp25.setText("laChamp25");
			}

			{
				GridData tfNomLData = new GridData();
				tfNomLData.horizontalAlignment = GridData.CENTER;
				tfNomLData.widthHint = 177;
				if(!decore) {
					tfChamp25 = new Text(paCorpsFormulaire, SWT.BORDER);
					tfChamp25.setLayoutData(tfNomLData);
				} else {					
					fieldChamp25 = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfChamp25 = (Text)fieldChamp25.getControl();
					fieldChamp25.getLayoutControl().setLayoutData(tfNomLData);
				}
			}
			
			{
				laChamp26 = new Label(paCorpsFormulaire, SWT.NONE);
				laChamp26.setText("laChamp26");
			}

			{
				GridData tfNomLData = new GridData();
				tfNomLData.horizontalAlignment = GridData.CENTER;
				tfNomLData.widthHint = 177;
				if(!decore) {
					tfChamp26 = new Text(paCorpsFormulaire, SWT.BORDER);
					tfChamp26.setLayoutData(tfNomLData);
				} else {					
					fieldChamp26 = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfChamp26 = (Text)fieldChamp26.getControl();
					fieldChamp26.getLayoutControl().setLayoutData(tfNomLData);
				}
			}
			
			{
				laChamp27 = new Label(paCorpsFormulaire, SWT.NONE);
				laChamp27.setText("laChamp27");
			}

			{
				GridData tfNomLData = new GridData();
				tfNomLData.horizontalAlignment = GridData.CENTER;
				tfNomLData.widthHint = 177;
				if(!decore) {
					tfChamp27 = new Text(paCorpsFormulaire, SWT.BORDER);
					tfChamp27.setLayoutData(tfNomLData);
				} else {					
					fieldChamp27 = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfChamp27 = (Text)fieldChamp27.getControl();
					fieldChamp27.getLayoutControl().setLayoutData(tfNomLData);
				}
			}
			
			{
				laChamp28 = new Label(paCorpsFormulaire, SWT.NONE);
				laChamp28.setText("laChamp28");
			}

			{
				GridData tfNomLData = new GridData();
				tfNomLData.horizontalAlignment = GridData.CENTER;
				tfNomLData.widthHint = 177;
				if(!decore) {
					tfChamp28 = new Text(paCorpsFormulaire, SWT.BORDER);
					tfChamp28.setLayoutData(tfNomLData);
				} else {					
					fieldChamp28 = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfChamp28 = (Text)fieldChamp28.getControl();
					fieldChamp28.getLayoutControl().setLayoutData(tfNomLData);
				}
			}
			
			{
				laChamp29 = new Label(paCorpsFormulaire, SWT.NONE);
				laChamp29.setText("laChamp29");
			}

			{
				GridData tfNomLData = new GridData();
				tfNomLData.horizontalAlignment = GridData.CENTER;
				tfNomLData.widthHint = 177;
				if(!decore) {
					tfChamp29 = new Text(paCorpsFormulaire, SWT.BORDER);
					tfChamp29.setLayoutData(tfNomLData);
				} else {					
					fieldChamp29 = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfChamp29 = (Text)fieldChamp29.getControl();
					fieldChamp29.getLayoutControl().setLayoutData(tfNomLData);
				}
			}
			
			{
				laChamp30 = new Label(paCorpsFormulaire, SWT.NONE);
				laChamp30.setText("laChamp30");
			}

			{
				GridData tfNomLData = new GridData();
				tfNomLData.horizontalAlignment = GridData.CENTER;
				tfNomLData.widthHint = 177;
				if(!decore) {
					tfChamp30 = new Text(paCorpsFormulaire, SWT.BORDER);
					tfChamp30.setLayoutData(tfNomLData);
				} else {					
					fieldChamp30 = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfChamp30 = (Text)fieldChamp30.getControl();
					fieldChamp30.getLayoutControl().setLayoutData(tfNomLData);
				}
			}
			
			
			listeChamp.put(laChamp1, tfChamp1);
			listeChamp.put(laChamp2, tfChamp2);
			listeChamp.put(laChamp3, tfChamp3);
			listeChamp.put(laChamp4, tfChamp4);
			listeChamp.put(laChamp5, tfChamp5);
			listeChamp.put(laChamp6, tfChamp6);
			listeChamp.put(laChamp7, tfChamp7);
			listeChamp.put(laChamp8, tfChamp8);
			listeChamp.put(laChamp9, tfChamp9);
			listeChamp.put(laChamp10, tfChamp10);
			listeChamp.put(laChamp11, tfChamp11);
			listeChamp.put(laChamp12, tfChamp12);
			listeChamp.put(laChamp13, tfChamp13);
			listeChamp.put(laChamp14, tfChamp14);
			listeChamp.put(laChamp15, tfChamp15);
			listeChamp.put(laChamp16, tfChamp16);
			listeChamp.put(laChamp17, tfChamp17);
			listeChamp.put(laChamp18, tfChamp18);
			listeChamp.put(laChamp19, tfChamp19);
			listeChamp.put(laChamp20, tfChamp20);
			listeChamp.put(laChamp21, tfChamp21);
			listeChamp.put(laChamp22, tfChamp22);
			listeChamp.put(laChamp23, tfChamp23);
			listeChamp.put(laChamp24, tfChamp24);
			listeChamp.put(laChamp25, tfChamp25);
			listeChamp.put(laChamp26, tfChamp26);
			listeChamp.put(laChamp27, tfChamp27);
			listeChamp.put(laChamp28, tfChamp28);
			listeChamp.put(laChamp29, tfChamp29);
			listeChamp.put(laChamp30, tfChamp30);
			
			
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

	public Label getLaChamp1() {
		return laChamp1;
	}

	public void setLaChamp1(Label laChamp1) {
		this.laChamp1 = laChamp1;
	}

	public Label getLaChamp2() {
		return laChamp2;
	}

	public void setLaChamp2(Label laChamp2) {
		this.laChamp2 = laChamp2;
	}

	public Label getLaChamp3() {
		return laChamp3;
	}

	public void setLaChamp3(Label laChamp3) {
		this.laChamp3 = laChamp3;
	}

	public Label getLaChamp4() {
		return laChamp4;
	}

	public void setLaChamp4(Label laChamp4) {
		this.laChamp4 = laChamp4;
	}

	public Label getLaChamp5() {
		return laChamp5;
	}

	public void setLaChamp5(Label laChamp5) {
		this.laChamp5 = laChamp5;
	}

	public Label getLaChamp6() {
		return laChamp6;
	}

	public void setLaChamp6(Label laChamp6) {
		this.laChamp6 = laChamp6;
	}

	public Label getLaChamp7() {
		return laChamp7;
	}

	public void setLaChamp7(Label laChamp7) {
		this.laChamp7 = laChamp7;
	}

	public Label getLaChamp8() {
		return laChamp8;
	}

	public void setLaChamp8(Label laChamp8) {
		this.laChamp8 = laChamp8;
	}

	public Label getLaChamp9() {
		return laChamp9;
	}

	public void setLaChamp9(Label laChamp9) {
		this.laChamp9 = laChamp9;
	}

	public Label getLaChamp10() {
		return laChamp10;
	}

	public void setLaChamp10(Label laChamp10) {
		this.laChamp10 = laChamp10;
	}

	public Label getLaChamp11() {
		return laChamp11;
	}

	public void setLaChamp11(Label laChamp11) {
		this.laChamp11 = laChamp11;
	}

	public Label getLaChamp12() {
		return laChamp12;
	}

	public void setLaChamp12(Label laChamp12) {
		this.laChamp12 = laChamp12;
	}

	public Label getLaChamp13() {
		return laChamp13;
	}

	public void setLaChamp13(Label laChamp13) {
		this.laChamp13 = laChamp13;
	}

	public Label getLaChamp14() {
		return laChamp14;
	}

	public void setLaChamp14(Label laChamp14) {
		this.laChamp14 = laChamp14;
	}

	public Label getLaChamp15() {
		return laChamp15;
	}

	public void setLaChamp15(Label laChamp15) {
		this.laChamp15 = laChamp15;
	}

	public Label getLaChamp16() {
		return laChamp16;
	}

	public void setLaChamp16(Label laChamp16) {
		this.laChamp16 = laChamp16;
	}

	public Label getLaChamp17() {
		return laChamp17;
	}

	public void setLaChamp17(Label laChamp17) {
		this.laChamp17 = laChamp17;
	}

	public Label getLaChamp18() {
		return laChamp18;
	}

	public void setLaChamp18(Label laChamp18) {
		this.laChamp18 = laChamp18;
	}

	public Label getLaChamp19() {
		return laChamp19;
	}

	public void setLaChamp19(Label laChamp19) {
		this.laChamp19 = laChamp19;
	}

	public Label getLaChamp20() {
		return laChamp20;
	}

	public void setLaChamp20(Label laChamp20) {
		this.laChamp20 = laChamp20;
	}

	public Label getLaChamp21() {
		return laChamp21;
	}

	public void setLaChamp21(Label laChamp21) {
		this.laChamp21 = laChamp21;
	}

	public Label getLaChamp22() {
		return laChamp22;
	}

	public void setLaChamp22(Label laChamp22) {
		this.laChamp22 = laChamp22;
	}

	public Label getLaChamp23() {
		return laChamp23;
	}

	public void setLaChamp23(Label laChamp23) {
		this.laChamp23 = laChamp23;
	}

	public Label getLaChamp24() {
		return laChamp24;
	}

	public void setLaChamp24(Label laChamp24) {
		this.laChamp24 = laChamp24;
	}

	public Label getLaChamp25() {
		return laChamp25;
	}

	public void setLaChamp25(Label laChamp25) {
		this.laChamp25 = laChamp25;
	}

	public Label getLaChamp26() {
		return laChamp26;
	}

	public void setLaChamp26(Label laChamp26) {
		this.laChamp26 = laChamp26;
	}

	public Label getLaChamp27() {
		return laChamp27;
	}

	public void setLaChamp27(Label laChamp27) {
		this.laChamp27 = laChamp27;
	}

	public Label getLaChamp28() {
		return laChamp28;
	}

	public void setLaChamp28(Label laChamp28) {
		this.laChamp28 = laChamp28;
	}

	public Label getLaChamp29() {
		return laChamp29;
	}

	public void setLaChamp29(Label laChamp29) {
		this.laChamp29 = laChamp29;
	}

	public Label getLaChamp30() {
		return laChamp30;
	}

	public void setLaChamp30(Label laChamp30) {
		this.laChamp30 = laChamp30;
	}

	public Text getTfChamp1() {
		return tfChamp1;
	}

	public void setTfChamp1(Text tfChamp1) {
		this.tfChamp1 = tfChamp1;
	}

	public Text getTfChamp2() {
		return tfChamp2;
	}

	public void setTfChamp2(Text tfChamp2) {
		this.tfChamp2 = tfChamp2;
	}

	public Text getTfChamp3() {
		return tfChamp3;
	}

	public void setTfChamp3(Text tfChamp3) {
		this.tfChamp3 = tfChamp3;
	}

	public Text getTfChamp4() {
		return tfChamp4;
	}

	public void setTfChamp4(Text tfChamp4) {
		this.tfChamp4 = tfChamp4;
	}

	public Text getTfChamp5() {
		return tfChamp5;
	}

	public void setTfChamp5(Text tfChamp5) {
		this.tfChamp5 = tfChamp5;
	}

	public Text getTfChamp6() {
		return tfChamp6;
	}

	public void setTfChamp6(Text tfChamp6) {
		this.tfChamp6 = tfChamp6;
	}

	public Text getTfChamp7() {
		return tfChamp7;
	}

	public void setTfChamp7(Text tfChamp7) {
		this.tfChamp7 = tfChamp7;
	}

	public Text getTfChamp8() {
		return tfChamp8;
	}

	public void setTfChamp8(Text tfChamp8) {
		this.tfChamp8 = tfChamp8;
	}

	public Text getTfChamp9() {
		return tfChamp9;
	}

	public void setTfChamp9(Text tfChamp9) {
		this.tfChamp9 = tfChamp9;
	}

	public Text getTfChamp10() {
		return tfChamp10;
	}

	public void setTfChamp10(Text tfChamp10) {
		this.tfChamp10 = tfChamp10;
	}

	public Text getTfChamp11() {
		return tfChamp11;
	}

	public void setTfChamp11(Text tfChamp11) {
		this.tfChamp11 = tfChamp11;
	}

	public Text getTfChamp12() {
		return tfChamp12;
	}

	public void setTfChamp12(Text tfChamp12) {
		this.tfChamp12 = tfChamp12;
	}

	public Text getTfChamp13() {
		return tfChamp13;
	}

	public void setTfChamp13(Text tfChamp13) {
		this.tfChamp13 = tfChamp13;
	}

	public Text getTfChamp14() {
		return tfChamp14;
	}

	public void setTfChamp14(Text tfChamp14) {
		this.tfChamp14 = tfChamp14;
	}

	public Text getTfChamp15() {
		return tfChamp15;
	}

	public void setTfChamp15(Text tfChamp15) {
		this.tfChamp15 = tfChamp15;
	}

	public Text getTfChamp16() {
		return tfChamp16;
	}

	public void setTfChamp16(Text tfChamp16) {
		this.tfChamp16 = tfChamp16;
	}

	public Text getTfChamp17() {
		return tfChamp17;
	}

	public void setTfChamp17(Text tfChamp17) {
		this.tfChamp17 = tfChamp17;
	}

	public Text getTfChamp18() {
		return tfChamp18;
	}

	public void setTfChamp18(Text tfChamp18) {
		this.tfChamp18 = tfChamp18;
	}

	public Text getTfChamp19() {
		return tfChamp19;
	}

	public void setTfChamp19(Text tfChamp19) {
		this.tfChamp19 = tfChamp19;
	}

	public Text getTfChamp20() {
		return tfChamp20;
	}

	public void setTfChamp20(Text tfChamp20) {
		this.tfChamp20 = tfChamp20;
	}

	public Text getTfChamp21() {
		return tfChamp21;
	}

	public void setTfChamp21(Text tfChamp21) {
		this.tfChamp21 = tfChamp21;
	}

	public Text getTfChamp22() {
		return tfChamp22;
	}

	public void setTfChamp22(Text tfChamp22) {
		this.tfChamp22 = tfChamp22;
	}

	public Text getTfChamp23() {
		return tfChamp23;
	}

	public void setTfChamp23(Text tfChamp23) {
		this.tfChamp23 = tfChamp23;
	}

	public Text getTfChamp24() {
		return tfChamp24;
	}

	public void setTfChamp24(Text tfChamp24) {
		this.tfChamp24 = tfChamp24;
	}

	public Text getTfChamp25() {
		return tfChamp25;
	}

	public void setTfChamp25(Text tfChamp25) {
		this.tfChamp25 = tfChamp25;
	}

	public Text getTfChamp26() {
		return tfChamp26;
	}

	public void setTfChamp26(Text tfChamp26) {
		this.tfChamp26 = tfChamp26;
	}

	public Text getTfChamp27() {
		return tfChamp27;
	}

	public void setTfChamp27(Text tfChamp27) {
		this.tfChamp27 = tfChamp27;
	}

	public Text getTfChamp28() {
		return tfChamp28;
	}

	public void setTfChamp28(Text tfChamp28) {
		this.tfChamp28 = tfChamp28;
	}

	public Text getTfChamp29() {
		return tfChamp29;
	}

	public void setTfChamp29(Text tfChamp29) {
		this.tfChamp29 = tfChamp29;
	}

	public Text getTfChamp30() {
		return tfChamp30;
	}

	public void setTfChamp30(Text tfChamp30) {
		this.tfChamp30 = tfChamp30;
	}

	public DecoratedField getFieldChamp1() {
		return fieldChamp1;
	}

	public void setFieldChamp1(DecoratedField fieldChamp1) {
		this.fieldChamp1 = fieldChamp1;
	}

	public DecoratedField getFieldChamp2() {
		return fieldChamp2;
	}

	public void setFieldChamp2(DecoratedField fieldChamp2) {
		this.fieldChamp2 = fieldChamp2;
	}

	public DecoratedField getFieldChamp3() {
		return fieldChamp3;
	}

	public void setFieldChamp3(DecoratedField fieldChamp3) {
		this.fieldChamp3 = fieldChamp3;
	}

	public DecoratedField getFieldChamp4() {
		return fieldChamp4;
	}

	public void setFieldChamp4(DecoratedField fieldChamp4) {
		this.fieldChamp4 = fieldChamp4;
	}

	public DecoratedField getFieldChamp5() {
		return fieldChamp5;
	}

	public void setFieldChamp5(DecoratedField fieldChamp5) {
		this.fieldChamp5 = fieldChamp5;
	}

	public DecoratedField getFieldChamp6() {
		return fieldChamp6;
	}

	public void setFieldChamp6(DecoratedField fieldChamp6) {
		this.fieldChamp6 = fieldChamp6;
	}

	public DecoratedField getFieldChamp7() {
		return fieldChamp7;
	}

	public void setFieldChamp7(DecoratedField fieldChamp7) {
		this.fieldChamp7 = fieldChamp7;
	}

	public DecoratedField getFieldChamp8() {
		return fieldChamp8;
	}

	public void setFieldChamp8(DecoratedField fieldChamp8) {
		this.fieldChamp8 = fieldChamp8;
	}

	public DecoratedField getFieldChamp9() {
		return fieldChamp9;
	}

	public void setFieldChamp9(DecoratedField fieldChamp9) {
		this.fieldChamp9 = fieldChamp9;
	}

	public DecoratedField getFieldChamp10() {
		return fieldChamp10;
	}

	public void setFieldChamp10(DecoratedField fieldChamp10) {
		this.fieldChamp10 = fieldChamp10;
	}

	public DecoratedField getFieldChamp11() {
		return fieldChamp11;
	}

	public void setFieldChamp11(DecoratedField fieldChamp11) {
		this.fieldChamp11 = fieldChamp11;
	}

	public DecoratedField getFieldChamp12() {
		return fieldChamp12;
	}

	public void setFieldChamp12(DecoratedField fieldChamp12) {
		this.fieldChamp12 = fieldChamp12;
	}

	public DecoratedField getFieldChamp13() {
		return fieldChamp13;
	}

	public void setFieldChamp13(DecoratedField fieldChamp13) {
		this.fieldChamp13 = fieldChamp13;
	}

	public DecoratedField getFieldChamp14() {
		return fieldChamp14;
	}

	public void setFieldChamp14(DecoratedField fieldChamp14) {
		this.fieldChamp14 = fieldChamp14;
	}

	public DecoratedField getFieldChamp15() {
		return fieldChamp15;
	}

	public void setFieldChamp15(DecoratedField fieldChamp15) {
		this.fieldChamp15 = fieldChamp15;
	}

	public DecoratedField getFieldChamp16() {
		return fieldChamp16;
	}

	public void setFieldChamp16(DecoratedField fieldChamp16) {
		this.fieldChamp16 = fieldChamp16;
	}

	public DecoratedField getFieldChamp17() {
		return fieldChamp17;
	}

	public void setFieldChamp17(DecoratedField fieldChamp17) {
		this.fieldChamp17 = fieldChamp17;
	}

	public DecoratedField getFieldChamp18() {
		return fieldChamp18;
	}

	public void setFieldChamp18(DecoratedField fieldChamp18) {
		this.fieldChamp18 = fieldChamp18;
	}

	public DecoratedField getFieldChamp19() {
		return fieldChamp19;
	}

	public void setFieldChamp19(DecoratedField fieldChamp19) {
		this.fieldChamp19 = fieldChamp19;
	}

	public DecoratedField getFieldChamp20() {
		return fieldChamp20;
	}

	public void setFieldChamp20(DecoratedField fieldChamp20) {
		this.fieldChamp20 = fieldChamp20;
	}

	public DecoratedField getFieldChamp21() {
		return fieldChamp21;
	}

	public void setFieldChamp21(DecoratedField fieldChamp21) {
		this.fieldChamp21 = fieldChamp21;
	}

	public DecoratedField getFieldChamp22() {
		return fieldChamp22;
	}

	public void setFieldChamp22(DecoratedField fieldChamp22) {
		this.fieldChamp22 = fieldChamp22;
	}

	public DecoratedField getFieldChamp23() {
		return fieldChamp23;
	}

	public void setFieldChamp23(DecoratedField fieldChamp23) {
		this.fieldChamp23 = fieldChamp23;
	}

	public DecoratedField getFieldChamp24() {
		return fieldChamp24;
	}

	public void setFieldChamp24(DecoratedField fieldChamp24) {
		this.fieldChamp24 = fieldChamp24;
	}

	public DecoratedField getFieldChamp25() {
		return fieldChamp25;
	}

	public void setFieldChamp25(DecoratedField fieldChamp25) {
		this.fieldChamp25 = fieldChamp25;
	}

	public DecoratedField getFieldChamp26() {
		return fieldChamp26;
	}

	public void setFieldChamp26(DecoratedField fieldChamp26) {
		this.fieldChamp26 = fieldChamp26;
	}

	public DecoratedField getFieldChamp27() {
		return fieldChamp27;
	}

	public void setFieldChamp27(DecoratedField fieldChamp27) {
		this.fieldChamp27 = fieldChamp27;
	}

	public DecoratedField getFieldChamp28() {
		return fieldChamp28;
	}

	public void setFieldChamp28(DecoratedField fieldChamp28) {
		this.fieldChamp28 = fieldChamp28;
	}

	public DecoratedField getFieldChamp29() {
		return fieldChamp29;
	}

	public void setFieldChamp29(DecoratedField fieldChamp29) {
		this.fieldChamp29 = fieldChamp29;
	}

	public DecoratedField getFieldChamp30() {
		return fieldChamp30;
	}

	public void setFieldChamp30(DecoratedField fieldChamp30) {
		this.fieldChamp30 = fieldChamp30;
	}

	public Map<Label, Text> getListeChamp() {
		return listeChamp;
	}

	public void setListeChamp(Map<Label, Text> listeChamp) {
		this.listeChamp = listeChamp;
	}

}

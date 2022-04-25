package fr.legrain.articles.ecran;

import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.fieldassist.TextControlCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormData;
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

import fr.legrain.lib.gui.fieldassist.ButtonControlCreator;
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
public class PaRapportUniteSWT extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	private Composite paCorpsFormulaire;

	private Label laCODE_UNITE;
	private Label laCODE_UNITE2;
	private Label laRAPPORT;
	private Label laNB_DECIMAL;
	private Label laSENS;

	private Text tfCODE_UNITE;
	private Text tfCODE_UNITE2;
	private Text tfRAPPORT;
	private Text tfNB_DECIMAL;
	private Combo cbSens;

	private Label laU1PhraseRapport; 
	private Label laRapportPhraseRapport;
	private Label laEgalePhraseRapport;
	private Label laU2PhraseRapport; 

	private DecoratedField fieldCODE_UNITE;
	private DecoratedField fieldCODE_UNITE2;
	private DecoratedField fieldRAPPORT;
	private DecoratedField fieldNB_DECIMAL;

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
		PaRapportUniteSWT inst = new PaRapportUniteSWT(shell, SWT.NULL);
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

	public PaRapportUniteSWT(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			{
				paCorpsFormulaire = new Composite(super.getPaFomulaire(), SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 5;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.grabExcessVerticalSpace = true;
				paCorpsFormulaire.setLayoutData(composite1LData);
				paCorpsFormulaire.setLayout(composite1Layout);
				{
					laCODE_UNITE = new Label(paCorpsFormulaire, SWT.NONE);
					laCODE_UNITE.setText("Code unité 1");
				}
				{
					GridData tfCODE_UNITELData = new GridData();
					tfCODE_UNITELData.widthHint = 177;
					tfCODE_UNITELData.horizontalAlignment = GridData.FILL;
					tfCODE_UNITELData.horizontalSpan = 4;
					if(!decore) {
						tfCODE_UNITE = new Text(paCorpsFormulaire, SWT.BORDER);
						tfCODE_UNITE.setLayoutData(tfCODE_UNITELData);
					} else {					
					fieldCODE_UNITE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfCODE_UNITE = (Text)fieldCODE_UNITE.getControl();
					fieldCODE_UNITE.getLayoutControl().setLayoutData(tfCODE_UNITELData);
					}
				}
				{
					laCODE_UNITE2 = new Label(paCorpsFormulaire, SWT.NONE);
					laCODE_UNITE2.setText("Code unité 2");
				}
				{
					GridData tfCODE_UNITE2LData = new GridData();
					//tfCODE_UNITE2LData.horizontalAlignment = GridData.FILL;
					tfCODE_UNITE2LData.widthHint = 177;
					tfCODE_UNITE2LData.horizontalSpan = 4;
					if(!decore) {
						tfCODE_UNITE2 = new Text(paCorpsFormulaire, SWT.BORDER);
						tfCODE_UNITE2.setLayoutData(tfCODE_UNITE2LData);
					} else {					
					fieldCODE_UNITE2 = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfCODE_UNITE2 = (Text)fieldCODE_UNITE2.getControl();
					fieldCODE_UNITE2.getLayoutControl().setLayoutData(tfCODE_UNITE2LData);
					}
				}
				{
					laRAPPORT = new Label(paCorpsFormulaire, SWT.NONE);
					laRAPPORT.setText("Rapport entre les unités");
				}
				{
					GridData tfRAPPORTLData = new GridData();
					//tfRAPPORTLData.horizontalAlignment = GridData.FILL;
					tfRAPPORTLData.widthHint = 50;
					tfRAPPORTLData.horizontalSpan = 4;
					if(!decore) {
						tfRAPPORT = new Text(paCorpsFormulaire, SWT.BORDER);
						tfRAPPORT.setLayoutData(tfRAPPORTLData);
					} else {					
					fieldRAPPORT = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfRAPPORT = (Text)fieldRAPPORT.getControl();
					fieldRAPPORT.getLayoutControl().setLayoutData(tfRAPPORTLData);
					}
				}
				{
					laNB_DECIMAL = new Label(paCorpsFormulaire, SWT.NONE);
					laNB_DECIMAL.setText("Nombre de décimales");
				}
				{
					GridData tfNB_DECIMALLData = new GridData();
					//tfNB_DECIMALLData.horizontalAlignment = GridData.FILL;
					tfNB_DECIMALLData.widthHint = 50;
					tfNB_DECIMALLData.horizontalSpan = 4;
					if(!decore) {
						tfNB_DECIMAL = new Text(paCorpsFormulaire, SWT.BORDER);
						tfNB_DECIMAL.setLayoutData(tfNB_DECIMALLData);
					} else {					
					fieldNB_DECIMAL = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfNB_DECIMAL = (Text)fieldNB_DECIMAL.getControl();
					fieldNB_DECIMAL.getLayoutControl().setLayoutData(tfNB_DECIMALLData);
					}
				}
//				{
//					laSENS = new Label(paCorpsFormulaire, SWT.NONE);
//					laSENS.setText("Sens");
//				}
//				{
//					GridData tfNB_DECIMALLData = new GridData();
//					//tfNB_DECIMALLData.horizontalAlignment = GridData.FILL;
//					tfNB_DECIMALLData.widthHint = 150;
//					cbSens = new Combo(paCorpsFormulaire, SWT.NULL);
//					cbSens.setLayoutData(tfNB_DECIMALLData);
//				}
				{
					GridData laU2PhraseRapportLData = new GridData();
					laU2PhraseRapportLData.horizontalAlignment = SWT.FILL;
					laU1PhraseRapport = new Label(paCorpsFormulaire, SWT.NONE);
					laU1PhraseRapport.setLayoutData(laU2PhraseRapportLData);
					laU1PhraseRapport.setText("U1");
				}
				{
					GridData tfNB_DECIMALLData = new GridData();
					tfNB_DECIMALLData.widthHint = 150;
					cbSens = new Combo(paCorpsFormulaire, SWT.NULL);
					cbSens.setLayoutData(tfNB_DECIMALLData);
				}
				{
					GridData laU2PhraseRapportLData = new GridData();
					laU2PhraseRapportLData.horizontalAlignment = SWT.FILL;
					laRapportPhraseRapport = new Label(paCorpsFormulaire, SWT.NONE);
					laRapportPhraseRapport.setLayoutData(laU2PhraseRapportLData);
					laRapportPhraseRapport.setText("0000000");
				}
				{
					laEgalePhraseRapport = new Label(paCorpsFormulaire, SWT.NONE);
					laEgalePhraseRapport.setText(" = ");
				}
				{
					GridData laU2PhraseRapportLData = new GridData();
					//laU2PhraseRapportLData.grabExcessHorizontalSpace = true;
					laU2PhraseRapportLData.horizontalAlignment = SWT.FILL;
					laU2PhraseRapport = new Label(paCorpsFormulaire, SWT.NONE);
					laU2PhraseRapport.setLayoutData(laU2PhraseRapportLData);
					laU2PhraseRapport.setText("U2");
				}

			}

			this.setLayout(new GridLayout());
			GridLayout compositeFormLayout = new GridLayout();
			compositeFormLayout.numColumns = 2;
			this.setSize(630, 756);
			GridData paGrilleLData = new GridData();
			paGrilleLData.grabExcessHorizontalSpace = true;
			paGrilleLData.horizontalAlignment = GridData.FILL;
			paGrilleLData.verticalAlignment = GridData.FILL;
			paGrilleLData.grabExcessVerticalSpace = true;
			super.getCompositeForm().setLayout(compositeFormLayout);
			GridData paFomulaireLData = new GridData();
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

	public Label getLaRAPPORT() {
		return laRAPPORT;
	}

	public Label getLaCODE_UNITE() {
		return laCODE_UNITE;
	}


	public Label getLaCODE_UNITE2() {
		return laCODE_UNITE2;
	}

	public Text getTfCODE_UNITE2() {
		return tfCODE_UNITE2;
	}

	public Text getTfCODE_UNITE() {
		return tfCODE_UNITE;
	}

	public Text getTfRAPPORT() {
		return tfRAPPORT;
	}


	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public DecoratedField getFieldCODE_UNITE2() {
		return fieldCODE_UNITE2;
	}

	public DecoratedField getFieldCODE_UNITE() {
		return fieldCODE_UNITE;
	}

	public DecoratedField getFieldRAPPORT() {
		return fieldRAPPORT;
	}

	public DecoratedField getFieldNB_DECIMAL() {
		return fieldNB_DECIMAL;
	}

	public Label getLaNB_DECIMAL() {
		return laNB_DECIMAL;
	}

	public Text getTfNB_DECIMAL() {
		return tfNB_DECIMAL;
	}

	public Label getLaSENS() {
		return laSENS;
	}

	public Combo getCbSens() {
		return cbSens;
	}

	public Label getLaU1PhraseRapport() {
		return laU1PhraseRapport;
	}

	public Label getLaRapportPhraseRapport() {
		return laRapportPhraseRapport;
	}

	public Label getLaEgalePhraseRapport() {
		return laEgalePhraseRapport;
	}

	public Label getLaU2PhraseRapport() {
		return laU2PhraseRapport;
	}

	public void setCbSens(Combo cbSens) {
		this.cbSens = cbSens;
	}

}

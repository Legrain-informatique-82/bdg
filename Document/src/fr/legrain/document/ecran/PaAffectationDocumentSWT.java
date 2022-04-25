package fr.legrain.document.ecran;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.fieldassist.TextControlCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
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
public class PaAffectationDocumentSWT extends fr.legrain.lib.gui.DefaultFrameFormulaireSWTSimpleToolBarHaut {



	private Composite paCorpsFormulaire;
	private Label laTYPEDOC;
	private Label laCODE_DOCUMENT;
	private Combo comboTypeDoc;
	private Text tfCODE_DOCUMENT;

	private DecoratedField fieldCODE_DOCUMENT;
	
	final private boolean decore = LgrMess.isDECORE();
	//final private boolean decore = false;

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
		PaAffectationDocumentSWT inst = new PaAffectationDocumentSWT(shell, SWT.NULL);
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

	public PaAffectationDocumentSWT(org.eclipse.swt.widgets.Composite parent, int style) {
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
					laTYPEDOC = new Label(paCorpsFormulaire, SWT.NONE);
					GridData laTYPEDOCLData = new GridData();
					laTYPEDOCLData.verticalAlignment = GridData.BEGINNING;
					laTYPEDOCLData.grabExcessVerticalSpace = true;
					laTYPEDOC.setLayoutData(laTYPEDOCLData);
					laTYPEDOC.setText("Type de document");
				}
				{
					comboTypeDoc = new Combo(paCorpsFormulaire, SWT.NONE);
					GridData comboTypeDocLData = new GridData();
					comboTypeDocLData.grabExcessVerticalSpace = true;
					comboTypeDocLData.grabExcessHorizontalSpace = true;
					comboTypeDocLData.widthHint = 125;
					comboTypeDocLData.horizontalSpan = 1;
					comboTypeDocLData.verticalAlignment = GridData.BEGINNING;
					comboTypeDoc.setLayoutData(comboTypeDocLData);
					comboTypeDoc.setText("Sélection du type de document");
				}
				{
					laCODE_DOCUMENT = new Label(paCorpsFormulaire, SWT.NONE);
					laCODE_DOCUMENT.setText("Code document");
				}
				{
					GridData tfCODE_DOCUMENTLData = new GridData();
					tfCODE_DOCUMENTLData.widthHint = 188;
					tfCODE_DOCUMENTLData.grabExcessHorizontalSpace = true;
					tfCODE_DOCUMENTLData.grabExcessVerticalSpace = true;
					if(!decore) {
						tfCODE_DOCUMENT = new Text(paCorpsFormulaire, SWT.BORDER);
						tfCODE_DOCUMENT.setLayoutData(tfCODE_DOCUMENTLData);
					} else {					
					fieldCODE_DOCUMENT = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfCODE_DOCUMENT = (Text)fieldCODE_DOCUMENT.getControl();
					fieldCODE_DOCUMENT.getLayoutControl().setLayoutData(tfCODE_DOCUMENTLData);
					}
				}



//				paCorpsFormulaire
//					.setTabList(new org.eclipse.swt.widgets.Control[] {
//							tfLIBELLE_T_TIERS, tfCODE_T_CIVILITE, tfCODE_T_TIERS,
//							tfPRENOM_TIERS, tfSURNOM_TIERS, composite2,
//							tfCODE_T_CIVILITE, tfCODE_ENTREPRISE, tfCODE_T_TIERS, tfCOMPTE,
//							tfLIBL_COMMENTAIRE, tfCODE_BANQUE, tfEntite, composite3 });
			}
			
			this.setLayout(new GridLayout());
			GridLayout compositeFormLayout = new GridLayout();
			compositeFormLayout.numColumns = 2;
			this.setSize(913, 439);
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

	public Label getLaCODE_DOCUMENT() {
		return laCODE_DOCUMENT;
	}


	public Text getTfCODE_DOCUMENT() {
		return tfCODE_DOCUMENT;
	}



	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public DecoratedField getFieldCODE_DOCUMENT() {
		return fieldCODE_DOCUMENT;
	}



	public void setLaCODE_DOCUMENT(Label laCODE_DOCUMENT) {
		GridData laCODE_DOCUMENTLData = new GridData();
		laCODE_DOCUMENTLData.grabExcessVerticalSpace = true;
		this.laCODE_DOCUMENT = laCODE_DOCUMENT;
		laCODE_DOCUMENT.setLayoutData(laCODE_DOCUMENTLData);
	}

	public void setTfCODE_DOCUMENT(Text tfCODE_DOCUMENT) {
		this.tfCODE_DOCUMENT = tfCODE_DOCUMENT;
	}

	public void setFieldCODE_DOCUMENT(DecoratedField fieldtCODE_DOCUMENT) {
		this.fieldCODE_DOCUMENT = fieldtCODE_DOCUMENT;
	}

	public Combo getComboTypeDoc() {
		return comboTypeDoc;
	}

	public Label getLaTYPEDOC() {
		return laTYPEDOC;
	}



}

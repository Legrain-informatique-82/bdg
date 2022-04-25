package fr.legrain.document.ecran;
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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
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
public class PaSelectionLigneDocumentCochable extends fr.legrain.lib.gui.DefaultFrameFormulaireSWTSimpleBtnCote {

	private Composite paCorpsFormulaire;
//	private Label laACCEPTE;
	private Label laTYPE_RELANCE;
//	private Button tfACCEPTE;
	private Text tfTYPE_RELANCE;
	private Text tfCODE_DOCUMENT;
	private Label laCODE_DOCUMENT;
	private Text tfCODE_TIERS;
	private Label laCODE_TIERS;
	
	private DecoratedField fieldTYPE_RELANCE;
	private DecoratedField fieldACCEPTE;
	
	final private boolean decore = LgrMess.isDECORE();
//	final private boolean decore = false;

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
		PaSelectionLigneDocumentCochable inst = new PaSelectionLigneDocumentCochable(shell, SWT.NULL);
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

	public PaSelectionLigneDocumentCochable(Composite parent, int style,
			int ordreComposite, int tableStyle) {
		super(parent, style, ordreComposite, tableStyle);
		initGUI();
	}

	public PaSelectionLigneDocumentCochable(org.eclipse.swt.widgets.Composite parent, int style) {
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
					laCODE_DOCUMENT = new Label(paCorpsFormulaire, SWT.NONE);
					laCODE_DOCUMENT.setText("Document relancé");
				}
				{
					GridData tfCODE_DOCUMENTLData = new GridData();
					tfCODE_DOCUMENTLData.widthHint = 188;
					tfCODE_DOCUMENTLData.grabExcessHorizontalSpace = true;
					tfCODE_DOCUMENTLData.horizontalAlignment = GridData.END;
					tfCODE_DOCUMENT = new Text(paCorpsFormulaire, SWT.BORDER);
					tfCODE_DOCUMENT.setLayoutData(tfCODE_DOCUMENTLData);
					
				}
				{
					laCODE_TIERS = new Label(paCorpsFormulaire, SWT.NONE);
					laCODE_TIERS.setText("Tiers relancé");
				}
				{
					GridData tfCODE_TIERSLData = new GridData();
					tfCODE_TIERSLData.widthHint = 188;
					tfCODE_TIERSLData.grabExcessHorizontalSpace = true;
					tfCODE_TIERSLData.horizontalAlignment = GridData.END;
					tfCODE_TIERS = new Text(paCorpsFormulaire, SWT.BORDER);
					tfCODE_TIERS.setLayoutData(tfCODE_TIERSLData);
					
				}
				
//				{
//					laACCEPTE = new Label(paCorpsFormulaire, SWT.NONE);
//					laACCEPTE.setText("Ligne acceptée");
//				}
//				{
//					GridData tfACCEPTELData = new GridData();
//					tfACCEPTELData.widthHint = 188;
//					tfACCEPTELData.grabExcessHorizontalSpace = true;
//					tfACCEPTELData.horizontalAlignment = GridData.CENTER;
//					tfACCEPTE = new Button(paCorpsFormulaire, SWT.CHECK | SWT.LEFT);
//					tfACCEPTE.setLayoutData(tfACCEPTELData);
//					
//				}

				{
					laTYPE_RELANCE = new Label(paCorpsFormulaire, SWT.NONE);
					laTYPE_RELANCE.setText("Type de relance");
				}
				{
					GridData tfTYPE_RELANCELData = new GridData();
					tfTYPE_RELANCELData.widthHint = 188;
					tfTYPE_RELANCELData.grabExcessHorizontalSpace = true;
					tfTYPE_RELANCELData.horizontalAlignment = GridData.END;
					if(!decore) {
						tfTYPE_RELANCE = new Text(paCorpsFormulaire, SWT.BORDER);
						tfTYPE_RELANCE.setLayoutData(tfTYPE_RELANCELData);
					} else {					
					fieldTYPE_RELANCE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfTYPE_RELANCE = (Text)fieldTYPE_RELANCE.getControl();
					fieldTYPE_RELANCE.getLayoutControl().setLayoutData(tfTYPE_RELANCELData);
					}
				}
				
			}
			
			GridLayout thisLayout = new GridLayout();
			this.setLayout(thisLayout);
			thisLayout.numColumns = 2;
			GridLayout compositeFormLayout = new GridLayout();
			compositeFormLayout.numColumns = 2;
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
			super.getBtnAnnuler().setText("Annuler");
			super.getBtnEnregister().setText("Enregistrer");

			this.layout();
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Composite getPaCorpsFormulaire() {
		return paCorpsFormulaire;
	}


//	public Button getTfACCEPTE() {
//		return tfACCEPTE;
//	}



	public DecoratedField getFieldTYPE_RELANCE() {
		return fieldTYPE_RELANCE;
	}





	public Label getLaTYPE_RELANCE() {
		return laTYPE_RELANCE;
	}

	public Text getTfTYPE_RELANCE() {
		return tfTYPE_RELANCE;
	}

//	public Label getLaACCEPTE() {
//		return laACCEPTE;
//	}

	public Text getTfCODE_DOCUMENT() {
		return tfCODE_DOCUMENT;
	}

	public Label getLaCODE_DOCUMENT() {
		return laCODE_DOCUMENT;
	}

	public DecoratedField getFieldACCEPTE() {
		return fieldACCEPTE;
	}

	public Text getTfCODE_TIERS() {
		return tfCODE_TIERS;
	}

	public Label getLaCODE_TIERS() {
		return laCODE_TIERS;
	}





}

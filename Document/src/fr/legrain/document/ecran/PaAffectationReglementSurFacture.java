package fr.legrain.document.ecran;

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

import fr.legrain.lib.gui.DefaultFrameFormulaireSWTSimpleToolBarHaut;
import fr.legrain.lib.gui.fieldassist.DateTimeControlCreator;
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
public class PaAffectationReglementSurFacture extends
		DefaultFrameFormulaireSWTSimpleToolBarHaut {

	private Composite paCorpsFormulaire;

//	private Label laCODE_DOCUMENT;
	private Label laCODE_REGLEMENT;
	private Label laMONTANT_REGLEMENT;
	private Label laMONTANT_AFFECTE;
	private Label laDATE_REGLEMENT;
	private Label laDATE_ENCAISSEMENT;
	private Label laTYPE_PAIEMENT;
	private Label laLIBELLE_PAIEMENT;
	private Label laCPT_COMPTABLE;

//	private Text tfCODE_DOCUMENT;
	private Text tfMONTANT_REGLEMENT;
	private Text tfMONTANT_AFFECTE;
	private Text tfCODE_REGLEMENT;
	private DateTime tfDATE_REGLEMENT;
	private DateTime tfDATE_ENCAISSEMENT;
	private Text tfTYPE_PAIEMENT;
	private Text tfLIBELLE_PAIEMENT;
	private Text tfCPT_COMPTABLE;

	private DecoratedField fieldMONTANT_REGLEMENT;
	private DecoratedField fieldMONTANT_AFFECTE;
	private DecoratedField fieldCODE_REGLEMENT;
	private DecoratedField fieldDATE_REGLEMENT;
	private DecoratedField fieldDATE_ENCAISSEMENT;
	private DecoratedField fieldTYPE_PAIEMENT;
	private DecoratedField fieldLIBELLE_PAIEMENT;
	private DecoratedField fieldCPT_COMPTABLE;;

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
		PaAffectationReglementSurFacture inst = new PaAffectationReglementSurFacture(shell, SWT.NULL);
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
	
	public PaAffectationReglementSurFacture(org.eclipse.swt.widgets.Composite parent, int style) {
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
//			{
//				laCODE_DOCUMENT = new Label(paCorpsFormulaire, SWT.NONE);
//				laCODE_DOCUMENT.setText("Document");
//			}
//			{
//				GridData tfCODE_DOCUMENTLData = new GridData();
//				tfCODE_DOCUMENTLData.widthHint = 188;
//				tfCODE_DOCUMENTLData.verticalAlignment = GridData.BEGINNING;
//				tfCODE_DOCUMENT = new Text(paCorpsFormulaire, SWT.BORDER);
//				tfCODE_DOCUMENT.setLayoutData(tfCODE_DOCUMENTLData);
//			}
			{
				laCODE_REGLEMENT = new Label(paCorpsFormulaire, SWT.NONE);
				laCODE_REGLEMENT.setText("Code réglement");
			}
			{
				GridData tfCODE_REGLEMENTLData = new GridData();
				tfCODE_REGLEMENTLData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
				tfCODE_REGLEMENTLData.widthHint = 130;
				tfCODE_REGLEMENTLData.verticalAlignment = GridData.BEGINNING;
				if(!decore) {
					tfCODE_REGLEMENT = new Text(paCorpsFormulaire, SWT.BORDER);
					tfCODE_REGLEMENT.setLayoutData(tfCODE_REGLEMENTLData);
				} else {					
				fieldCODE_REGLEMENT = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
				tfCODE_REGLEMENT = (Text)fieldCODE_REGLEMENT.getControl();
				fieldCODE_REGLEMENT.getLayoutControl().setLayoutData(tfCODE_REGLEMENTLData);
				}
			}
			
			{
				laCPT_COMPTABLE = new Label(paCorpsFormulaire, SWT.NONE);
				laCPT_COMPTABLE.setText("Compte bancaire");
			}
			{
				GridData tfCPT_COMPTABLELData = new GridData();
				tfCPT_COMPTABLELData.horizontalAlignment = GridData.CENTER;
				tfCPT_COMPTABLELData.widthHint = 130;
				tfCPT_COMPTABLELData.verticalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
				if(!decore) {
					tfCPT_COMPTABLE = new Text(paCorpsFormulaire, SWT.BORDER);
					tfCPT_COMPTABLE.setLayoutData(tfCPT_COMPTABLELData);
				} else {					
				fieldCPT_COMPTABLE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
				tfCPT_COMPTABLE = (Text)fieldCPT_COMPTABLE.getControl();
				fieldCPT_COMPTABLE.getLayoutControl().setLayoutData(tfCPT_COMPTABLELData);
				}
			}
			
			//

			{
				laDATE_REGLEMENT = new Label(paCorpsFormulaire, SWT.NONE);
				laDATE_REGLEMENT.setText("Date réglement");
			}
			{
				GridData dateTimeDATE_LIV_FACTURELData = new GridData();
				dateTimeDATE_LIV_FACTURELData.widthHint = 105;
				dateTimeDATE_LIV_FACTURELData.verticalAlignment = GridData.FILL;

			if(!decore) {
				tfDATE_REGLEMENT = new DateTime(paCorpsFormulaire, SWT.BORDER | SWT.DROP_DOWN);
				tfDATE_REGLEMENT.setLayoutData(dateTimeDATE_LIV_FACTURELData);
				} else {
					fieldDATE_REGLEMENT = new DecoratedField(paCorpsFormulaire, SWT.BORDER | SWT.DROP_DOWN, new DateTimeControlCreator());
					tfDATE_REGLEMENT = (DateTime)fieldDATE_REGLEMENT.getControl();
					fieldDATE_REGLEMENT.getLayoutControl().setLayoutData(dateTimeDATE_LIV_FACTURELData);
				}
//			tfDATE_REGLEMENT.setPattern("dd/MM/yyyy  ");					
			}

			{
				laDATE_ENCAISSEMENT = new Label(paCorpsFormulaire, SWT.NONE);
				laDATE_ENCAISSEMENT.setText("Date encaissement");
			}
			{
				GridData dateTimeDATE_LIV_FACTURELData = new GridData();
				dateTimeDATE_LIV_FACTURELData.widthHint = 105;
				dateTimeDATE_LIV_FACTURELData.verticalAlignment = GridData.FILL;

			if(!decore) {
				tfDATE_ENCAISSEMENT = new DateTime(paCorpsFormulaire, SWT.BORDER | SWT.DROP_DOWN);
				tfDATE_ENCAISSEMENT.setLayoutData(dateTimeDATE_LIV_FACTURELData);
				} else {
					fieldDATE_ENCAISSEMENT = new DecoratedField(paCorpsFormulaire, SWT.BORDER | SWT.DROP_DOWN, new DateTimeControlCreator());
					tfDATE_ENCAISSEMENT = (DateTime)fieldDATE_ENCAISSEMENT.getControl();
					fieldDATE_ENCAISSEMENT.getLayoutControl().setLayoutData(dateTimeDATE_LIV_FACTURELData);
				}
//			tfDATE_ENCAISSEMENT.setPattern("dd/MM/yyyy  ");					
			}
			{
				laMONTANT_REGLEMENT = new Label(paCorpsFormulaire, SWT.NONE);
				laMONTANT_REGLEMENT.setText("Montant réglement");
			}
			{
				GridData tfMONTANT_REGLEMENTLData = new GridData();
				tfMONTANT_REGLEMENTLData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
				tfMONTANT_REGLEMENTLData.widthHint = 130;
				tfMONTANT_REGLEMENTLData.verticalAlignment = GridData.BEGINNING;
				if(!decore) {
					tfMONTANT_REGLEMENT = new Text(paCorpsFormulaire, SWT.BORDER);
					tfMONTANT_REGLEMENT.setLayoutData(tfMONTANT_REGLEMENTLData);
				} else {					
				fieldMONTANT_REGLEMENT = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
				tfMONTANT_REGLEMENT = (Text)fieldMONTANT_REGLEMENT.getControl();
				fieldMONTANT_REGLEMENT.getLayoutControl().setLayoutData(tfMONTANT_REGLEMENTLData);
				}
			}
			{
				laMONTANT_AFFECTE = new Label(paCorpsFormulaire, SWT.NONE);
				laMONTANT_AFFECTE.setText("Montant affecté");
			}
			{
				GridData tfMONTANT_AFFECTELData = new GridData();
				tfMONTANT_AFFECTELData.horizontalAlignment = GridData.CENTER;
				tfMONTANT_AFFECTELData.widthHint = 130;
				tfMONTANT_AFFECTELData.verticalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
				if(!decore) {
					tfMONTANT_AFFECTE = new Text(paCorpsFormulaire, SWT.BORDER);
					tfMONTANT_AFFECTE.setLayoutData(tfMONTANT_AFFECTELData);
				} else {					
				fieldMONTANT_AFFECTE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
				tfMONTANT_AFFECTE = (Text)fieldMONTANT_AFFECTE.getControl();
				fieldMONTANT_AFFECTE.getLayoutControl().setLayoutData(tfMONTANT_AFFECTELData);
				}
			}
			
			{
				laTYPE_PAIEMENT = new Label(paCorpsFormulaire, SWT.NONE);
				laTYPE_PAIEMENT.setText("Type paiement");
			}
			{
				GridData tfTYPE_PAIEMENTLData = new GridData();
				tfTYPE_PAIEMENTLData.horizontalAlignment = GridData.CENTER;
				tfTYPE_PAIEMENTLData.widthHint = 130;
				tfTYPE_PAIEMENTLData.verticalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
				if(!decore) {
					tfTYPE_PAIEMENT = new Text(paCorpsFormulaire, SWT.BORDER);
					tfTYPE_PAIEMENT.setLayoutData(tfTYPE_PAIEMENTLData);
				} else {					
				fieldTYPE_PAIEMENT = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
				tfTYPE_PAIEMENT = (Text)fieldTYPE_PAIEMENT.getControl();
				fieldTYPE_PAIEMENT.getLayoutControl().setLayoutData(tfTYPE_PAIEMENTLData);
				}
			}

			{
				laLIBELLE_PAIEMENT = new Label(paCorpsFormulaire, SWT.NONE);
				laLIBELLE_PAIEMENT.setText("Libellé paiement");
			}
			{
				GridData tfLIBELLE_PAIEMENTLData = new GridData();
				tfLIBELLE_PAIEMENTLData.horizontalAlignment = GridData.CENTER;
				tfLIBELLE_PAIEMENTLData.widthHint = 130;
				tfLIBELLE_PAIEMENTLData.heightHint = 40;
				tfLIBELLE_PAIEMENTLData.verticalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
				if(!decore) {
					tfLIBELLE_PAIEMENT = new Text(paCorpsFormulaire, SWT.MULTI | SWT.WRAP | SWT.BORDER);
					tfLIBELLE_PAIEMENT.setLayoutData(tfLIBELLE_PAIEMENTLData);
				} else {					
				fieldLIBELLE_PAIEMENT = new DecoratedField(paCorpsFormulaire, SWT.MULTI | SWT.WRAP | SWT.BORDER, new TextControlCreator());
				tfLIBELLE_PAIEMENT = (Text)fieldLIBELLE_PAIEMENT.getControl();
				fieldLIBELLE_PAIEMENT.getLayoutControl().setLayoutData(tfLIBELLE_PAIEMENTLData);
				}
			}

			//
			
			this.setLayout(new GridLayout());
			GridLayout compositeFormLayout = new GridLayout();
			compositeFormLayout.numColumns = 3;
			GridData paGrilleLData = new GridData();
			paGrilleLData.grabExcessHorizontalSpace = true;
			paGrilleLData.horizontalAlignment = GridData.FILL;
			paGrilleLData.verticalAlignment = GridData.FILL;
			paGrilleLData.grabExcessVerticalSpace = true;
			super.getCompositeForm().setLayout(compositeFormLayout);
			GridData paFomulaireLData = new GridData();
			paFomulaireLData.verticalAlignment = GridData.FILL;
			paFomulaireLData.horizontalAlignment = GridData.FILL;
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
			this.layout();
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Composite getPaCorpsFormulaire() {
		return paCorpsFormulaire;
	}

//	public Text getTfCODE_DOCUMENT() {
//		return tfCODE_DOCUMENT;
//	}

	public Label getLaMONTANT_AFFECTE() {
		return laMONTANT_AFFECTE;
	}

	public Text getTfMONTANT_AFFECTE() {
		return tfMONTANT_AFFECTE;
	}

	public boolean isDecore() {
		return decore;
	}

	public DecoratedField getFieldMONTANT_AFFECTE() {
		return fieldMONTANT_AFFECTE;
	}


//	public Label getLaCODE_DOCUMENT() {
//		return laCODE_DOCUMENT;
//	}

	public Label getLaCODE_REGLEMENT() {
		return laCODE_REGLEMENT;
	}

	public Label getLaDATE_REGLEMENT() {
		return laDATE_REGLEMENT;
	}

	public Label getLaDATE_ENCAISSEMENT() {
		return laDATE_ENCAISSEMENT;
	}

	public Label getLaTYPE_PAIEMENT() {
		return laTYPE_PAIEMENT;
	}

	public Label getLaLIBELLE_PAIEMENT() {
		return laLIBELLE_PAIEMENT;
	}

	public Label getLaCPT_COMPTABLE() {
		return laCPT_COMPTABLE;
	}

	public Text getTfCODE_REGLEMENT() {
		return tfCODE_REGLEMENT;
	}

	public DateTime getTfDATE_REGLEMENT() {
		return tfDATE_REGLEMENT;
	}

	public DateTime getTfDATE_ENCAISSEMENT() {
		return tfDATE_ENCAISSEMENT;
	}

	public Text getTfTYPE_PAIEMENT() {
		return tfTYPE_PAIEMENT;
	}

	public Text getTfLIBELLE_PAIEMENT() {
		return tfLIBELLE_PAIEMENT;
	}

	public Text getTfCPT_COMPTABLE() {
		return tfCPT_COMPTABLE;
	}

	public DecoratedField getFieldCODE_REGLEMENT() {
		return fieldCODE_REGLEMENT;
	}

	public DecoratedField getFieldDATE_REGLEMENT() {
		return fieldDATE_REGLEMENT;
	}

	public DecoratedField getFieldDATE_ENCAISSEMENT() {
		return fieldDATE_ENCAISSEMENT;
	}

	public DecoratedField getFieldTYPE_PAIEMENT() {
		return fieldTYPE_PAIEMENT;
	}

	public DecoratedField getFieldLIBELLE_PAIEMENT() {
		return fieldLIBELLE_PAIEMENT;
	}

	public DecoratedField getFieldCPT_COMPTABLE() {
		return fieldCPT_COMPTABLE;
	}

	public Label getLaMONTANT_REGLEMENT() {
		return laMONTANT_REGLEMENT;
	}

	public Text getTfMONTANT_REGLEMENT() {
		return tfMONTANT_REGLEMENT;
	}

	public DecoratedField getFieldMONTANT_REGLEMENT() {
		return fieldMONTANT_REGLEMENT;
	}

		
}

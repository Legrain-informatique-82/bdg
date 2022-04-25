package fr.legrain.reglement.ecran;
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
public class PaFormulaireReglement extends org.eclipse.swt.widgets.Composite {
	private Composite paCorpsFormulaire;
	private Label laCODE_DOCUMENT;
	private DateTime tfDATE_REGLEMENT;
	private Text tfMONTANT_AFFECTE;
	private Text tfTYPE_PAIEMENT;
	private Label laTYPE_PAIEMENT;
	private Label laLIBELLE_PAIEMENT;
	private Text tfLIBELLE_PAIEMENT;
	private Button btnValiderReglement;
	private Button btnAnnulerReglement;
	private Text tfCPT_COMPTABLE;
	private Label laCPT_COMPTABLE;
	private Label laMONTANT_AFFECTE;
	private DateTime tfDATE_ENCAISSEMENT;
	private Label laDATE_ENCAISSEMENT;
	private Label laDATE_REGLEMENT;
	private Text tfCODE_REGLEMENT;
	private Label laCODE_REGLEMENT;
	private Text tfCODE_DOCUMENT;

	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void main(String[] args) {
		showGUI();
	}
	
	/**
	* Overriding checkSubclass allows this class to extend org.eclipse.swt.widgets.Composite
	*/	
	protected void checkSubclass() {
	}
	
	/**
	* Auto-generated method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void showGUI() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		PaFormulaireReglement inst = new PaFormulaireReglement(shell, SWT.NULL);
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

	public PaFormulaireReglement(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.makeColumnsEqualWidth = true;
			this.setLayout(thisLayout);
			{
				GridData paCorpsFormulaireLData = new GridData();
				paCorpsFormulaireLData.verticalAlignment = GridData.FILL;
				paCorpsFormulaireLData.horizontalAlignment = GridData.FILL;
				paCorpsFormulaireLData.grabExcessVerticalSpace = true;
				paCorpsFormulaireLData.grabExcessHorizontalSpace = true;
				paCorpsFormulaire = new Composite(this, SWT.BORDER);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 2;
				composite1Layout.makeColumnsEqualWidth = true;
				paCorpsFormulaire.setLayout(composite1Layout);
				paCorpsFormulaire.setLayoutData(paCorpsFormulaireLData);
				{
					laCODE_DOCUMENT = new Label(paCorpsFormulaire, SWT.NONE);
					laCODE_DOCUMENT.setText("Document");
				}
				{
					GridData tfCODE_DOCUMENTLData = new GridData();
					tfCODE_DOCUMENTLData.widthHint = 121;
					tfCODE_DOCUMENTLData.heightHint = 13;
					tfCODE_DOCUMENT = new Text(paCorpsFormulaire, SWT.BORDER);
					tfCODE_DOCUMENT.setLayoutData(tfCODE_DOCUMENTLData);
				}
				{
					laCODE_REGLEMENT = new Label(paCorpsFormulaire, SWT.NONE);
					laCODE_REGLEMENT.setText("Code réglement");
				}
				{
					GridData tfCODE_REGLEMENTLData = new GridData();
					tfCODE_REGLEMENTLData.widthHint = 121;
					tfCODE_REGLEMENTLData.heightHint = 13;
					tfCODE_REGLEMENT = new Text(paCorpsFormulaire, SWT.BORDER);
					tfCODE_REGLEMENT.setLayoutData(tfCODE_REGLEMENTLData);
				}
				{
					laDATE_REGLEMENT = new Label(paCorpsFormulaire, SWT.NONE);
					GridData laDATE_REGLEMENTLData = new GridData();
					laDATE_REGLEMENT.setLayoutData(laDATE_REGLEMENTLData);
					laDATE_REGLEMENT.setText("Date réglement");
				}
				{
					tfDATE_REGLEMENT = new DateTime(paCorpsFormulaire, SWT.BORDER | SWT.DROP_DOWN);
					GridData dateTimeDATE_LIV_FACTURELData = new GridData();
					dateTimeDATE_LIV_FACTURELData.verticalAlignment = GridData.FILL;
					dateTimeDATE_LIV_FACTURELData.widthHint = 113;
					tfDATE_REGLEMENT.setLayoutData(dateTimeDATE_LIV_FACTURELData);
//					tfDATE_REGLEMENT.setPattern("dd/MM/yyyy");
				}
				{
					laDATE_ENCAISSEMENT = new Label(paCorpsFormulaire, SWT.NONE);
					GridData laDATE_ENCAISSEMENTLData = new GridData();
					laDATE_ENCAISSEMENT.setLayoutData(laDATE_ENCAISSEMENTLData);
					laDATE_ENCAISSEMENT.setText("Date encaissement");
				}
				{
					tfDATE_ENCAISSEMENT = new DateTime(paCorpsFormulaire, SWT.BORDER | SWT.DROP_DOWN);
					GridData dateTimeDATE_LIV_FACTURELData = new GridData();
					dateTimeDATE_LIV_FACTURELData.verticalAlignment = GridData.FILL;
					dateTimeDATE_LIV_FACTURELData.widthHint = 113;
					tfDATE_ENCAISSEMENT.setLayoutData(dateTimeDATE_LIV_FACTURELData);
//					tfDATE_ENCAISSEMENT.setPattern("dd/MM/yyyy");
				}
				{
					laMONTANT_AFFECTE = new Label(paCorpsFormulaire, SWT.NONE);
					laMONTANT_AFFECTE.setText("Montant affecté");
				}
				{
					GridData tfMONTANT_AFFECTELData = new GridData();
					tfMONTANT_AFFECTELData.widthHint = 121;
					tfMONTANT_AFFECTELData.heightHint = 13;
					tfMONTANT_AFFECTE = new Text(paCorpsFormulaire, SWT.BORDER);
					tfMONTANT_AFFECTE.setLayoutData(tfMONTANT_AFFECTELData);
				}
				{
					laTYPE_PAIEMENT = new Label(paCorpsFormulaire, SWT.NONE);
					laTYPE_PAIEMENT.setText("Type paiement");
				}
				{
					GridData tfTYPE_PAIEMENTLData = new GridData();
					tfTYPE_PAIEMENTLData.widthHint = 121;
					tfTYPE_PAIEMENTLData.heightHint = 13;
					tfTYPE_PAIEMENT = new Text(paCorpsFormulaire, SWT.BORDER);
					tfTYPE_PAIEMENT.setLayoutData(tfTYPE_PAIEMENTLData);
				}
				{
					laLIBELLE_PAIEMENT = new Label(paCorpsFormulaire, SWT.NONE);
					laLIBELLE_PAIEMENT.setText("Libellé paiement");
				}
				{
					GridData tfLIBELLE_PAIEMENTLData = new GridData();
					tfLIBELLE_PAIEMENTLData.verticalAlignment = GridData.BEGINNING;
					tfLIBELLE_PAIEMENTLData.horizontalAlignment = GridData.CENTER;
					tfLIBELLE_PAIEMENTLData.widthHint = 188;
					tfLIBELLE_PAIEMENTLData.heightHint = 40;
					tfLIBELLE_PAIEMENT = new Text(paCorpsFormulaire, SWT.MULTI | SWT.WRAP | SWT.BORDER);
					tfLIBELLE_PAIEMENT.setLayoutData(tfLIBELLE_PAIEMENTLData);
				}
				{
					laCPT_COMPTABLE = new Label(paCorpsFormulaire, SWT.NONE);
					laCPT_COMPTABLE.setText("Compte bancaire");
				}
				{
					GridData tfCPT_COMPTABLELData = new GridData();
					tfCPT_COMPTABLELData.widthHint = 121;
					tfCPT_COMPTABLELData.heightHint = 13;
					tfCPT_COMPTABLE = new Text(paCorpsFormulaire, SWT.BORDER);
					tfCPT_COMPTABLE.setLayoutData(tfCPT_COMPTABLELData);
				}
				{
					btnAnnulerReglement = new Button(paCorpsFormulaire, SWT.PUSH | SWT.CENTER);
					GridData btnAnnulerReglementLData = new GridData();
					btnAnnulerReglementLData.horizontalAlignment = GridData.END;
					btnAnnulerReglementLData.widthHint = 81;
					btnAnnulerReglementLData.heightHint = 25;
					btnAnnulerReglement.setLayoutData(btnAnnulerReglementLData);
					btnAnnulerReglement.setText("Annuler");
				}
				{
					btnValiderReglement = new Button(paCorpsFormulaire, SWT.PUSH | SWT.CENTER);
					GridData btnValiderReglementLData = new GridData();
					btnValiderReglementLData.widthHint = 81;
					btnValiderReglementLData.heightHint = 25;
					btnValiderReglement.setLayoutData(btnValiderReglementLData);
					btnValiderReglement.setText("Valider");
				}
			}
			this.layout();
			pack();
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

	public DateTime getTfDATE_REGLEMENT() {
		return tfDATE_REGLEMENT;
	}

	public Text getTfMONTANT_AFFECTE() {
		return tfMONTANT_AFFECTE;
	}

	public Text getTfTYPE_PAIEMENT() {
		return tfTYPE_PAIEMENT;
	}

	public Label getLaTYPE_PAIEMENT() {
		return laTYPE_PAIEMENT;
	}

	public Label getLaLIBELLE_PAIEMENT() {
		return laLIBELLE_PAIEMENT;
	}

	public Text getTfLIBELLE_PAIEMENT() {
		return tfLIBELLE_PAIEMENT;
	}

	public Button getBtnValiderReglement() {
		return btnValiderReglement;
	}

	public Button getBtnAnnulerReglement() {
		return btnAnnulerReglement;
	}

	public Text getTfCPT_COMPTABLE() {
		return tfCPT_COMPTABLE;
	}

	public Label getLaCPT_COMPTABLE() {
		return laCPT_COMPTABLE;
	}

	public Label getLaMONTANT_AFFECTE() {
		return laMONTANT_AFFECTE;
	}

	public DateTime getTfDATE_ENCAISSEMENT() {
		return tfDATE_ENCAISSEMENT;
	}

	public Label getLaDATE_ENCAISSEMENT() {
		return laDATE_ENCAISSEMENT;
	}

	public Label getLaDATE_REGLEMENT() {
		return laDATE_REGLEMENT;
	}

	public Text getTfCODE_REGLEMENT() {
		return tfCODE_REGLEMENT;
	}

	public Label getLaCODE_REGLEMENT() {
		return laCODE_REGLEMENT;
	}

	public Text getTfCODE_DOCUMENT() {
		return tfCODE_DOCUMENT;
	}

}

package fr.legrain.archivageepicea.Projet_swt;
import com.cloudgarden.resource.SWTResourceManager;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.SWT;


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
public class CompositeOneTableCompt extends CommunTable {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	private Button buttonComptePrint;
	private Label labelDetailCompte;
	private Label labelCompteCredit;
	private Label labelCreditCompte;
	private Label labelSoldeFin;
	private Label labelEcritureCompt;
	private Label labelTotalEcritureCompte;
	private Label labelCreditSoldeCompte;
	private Label labelDebitSoldeCompte;
	private Label labelSoldeFinDate;
	private Label labelDebitCompte;
	private Label labelTotauxMonvementDate;
	private Label labelTotauxMonvementCompte;
	private Label labelComptDebit;
	private Label labelNumeroCompte;
	private Composite compositeDownTableCompte;
	private Composite compositeZoneButton;
	private Group groupDownTableCompt;

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
		CompositeOneTableCompt inst = new CompositeOneTableCompt(shell, SWT.NULL);
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

	public CompositeOneTableCompt(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.makeColumnsEqualWidth = true;
			this.setLayout(thisLayout);
			this.setSize(919, 549);
			{
				groupDownTableCompt = new Group(this, SWT.H_SCROLL | SWT.V_SCROLL);
				GridLayout groupDownTablePiecesLayout = new GridLayout();
				groupDownTablePiecesLayout.makeColumnsEqualWidth = true;
				groupDownTableCompt.setLayout(groupDownTablePiecesLayout);
				GridData groupDownTablePiecesLData = new GridData();
				groupDownTablePiecesLData.grabExcessHorizontalSpace = true;
				groupDownTablePiecesLData.horizontalAlignment = GridData.FILL;
				groupDownTablePiecesLData.verticalAlignment = GridData.BEGINNING;
				groupDownTablePiecesLData.heightHint = 171;
				groupDownTableCompt.setLayoutData(groupDownTablePiecesLData);
				groupDownTableCompt.setText("Information concenant Comptes");
				groupDownTableCompt.setFont(SWTResourceManager.getFont("Tahoma", 10, 1, false, false));
				{
					compositeDownTableCompte = new Composite(groupDownTableCompt, SWT.BORDER);
					GridLayout compositeDownTableCompteLayout = new GridLayout();
					compositeDownTableCompteLayout.numColumns = 4;
					GridData compositeDownTableCompteLData = new GridData();
					compositeDownTableCompteLData.grabExcessHorizontalSpace = true;
					compositeDownTableCompteLData.horizontalAlignment = GridData.FILL;
					compositeDownTableCompteLData.verticalAlignment = GridData.FILL;
					compositeDownTableCompteLData.grabExcessVerticalSpace = true;
					compositeDownTableCompte.setLayoutData(compositeDownTableCompteLData);
					compositeDownTableCompte.setLayout(compositeDownTableCompteLayout);
					{
						labelDetailCompte = new Label(compositeDownTableCompte, SWT.NONE);
						labelDetailCompte.setText("Détails des écritures pour le compte ");
						labelDetailCompte.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
					}
					{
						GridData labelNumeroCompteLData = new GridData();
						labelNumeroCompteLData.horizontalAlignment = GridData.CENTER;
						labelNumeroCompteLData.widthHint = 204;
						labelNumeroCompteLData.heightHint = 17;
						labelNumeroCompte = new Label(compositeDownTableCompte, SWT.CENTER | SWT.BORDER);
						labelNumeroCompte.setLayoutData(labelNumeroCompteLData);
					}
					{
						labelComptDebit = new Label(compositeDownTableCompte, SWT.CENTER);
						labelComptDebit.setText("Débit");
						GridData labelComptDebitLData = new GridData();
						labelComptDebitLData.heightHint = 13;
						labelComptDebitLData.horizontalAlignment = GridData.CENTER;
						labelComptDebitLData.widthHint = 134;
						labelComptDebit.setLayoutData(labelComptDebitLData);
						labelComptDebit.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
					}
					{
						labelCompteCredit = new Label(compositeDownTableCompte, SWT.CENTER);
						labelCompteCredit.setText("Crédit");
						GridData labelCompteCreditLData = new GridData();
						labelCompteCreditLData.horizontalAlignment = GridData.CENTER;
						labelCompteCreditLData.widthHint = 134;
						labelCompteCreditLData.heightHint = 13;
						labelCompteCredit.setLayoutData(labelCompteCreditLData);
						labelCompteCredit.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
					}
					{
						labelTotauxMonvementCompte = new Label(compositeDownTableCompte, SWT.RIGHT);
						labelTotauxMonvementCompte.setText("Totaux des mouvements");
						GridData labelTotauxMonvementCompteLData = new GridData();
						labelTotauxMonvementCompteLData.horizontalAlignment = GridData.FILL;
						labelTotauxMonvementCompte.setLayoutData(labelTotauxMonvementCompteLData);
						labelTotauxMonvementCompte.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
					}
					{
						GridData labelTotauxMonvementDate1LData = new GridData();
						labelTotauxMonvementDate1LData.horizontalAlignment = GridData.FILL;
						labelTotauxMonvementDate = new Label(compositeDownTableCompte, SWT.CENTER | SWT.BORDER);
						labelTotauxMonvementDate.setLayoutData(labelTotauxMonvementDate1LData);
					}
					{
						GridData labelDebitCompteLData = new GridData();
						labelDebitCompteLData.horizontalAlignment = GridData.FILL;
						labelDebitCompte = new Label(compositeDownTableCompte, SWT.BORDER);
						labelDebitCompte.setLayoutData(labelDebitCompteLData);
						labelDebitCompte.setAlignment(SWT.RIGHT);
					}
					{
						GridData labelCreditCompteLData = new GridData();
						labelCreditCompteLData.horizontalAlignment = GridData.FILL;
						labelCreditCompte = new Label(compositeDownTableCompte, SWT.BORDER);
						labelCreditCompte.setLayoutData(labelCreditCompteLData);
						labelCreditCompte.setAlignment(SWT.RIGHT);
					}
					{
						labelSoldeFin = new Label(compositeDownTableCompte, SWT.NONE);
						GridData labelSoldeFinLData = new GridData();
						labelSoldeFinLData.horizontalAlignment = GridData.END;
						labelSoldeFin.setLayoutData(labelSoldeFinLData);
						labelSoldeFin.setText("Solde Fin Période ");
						labelSoldeFin.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
					}
					{
						GridData labelSoldeFinDateLData = new GridData();
						labelSoldeFinDateLData.horizontalAlignment = GridData.FILL;
						labelSoldeFinDate = new Label(compositeDownTableCompte, SWT.CENTER | SWT.BORDER);
						labelSoldeFinDate.setLayoutData(labelSoldeFinDateLData);
					}
					{
						GridData labelDebitSoldeCompteLData = new GridData();
						labelDebitSoldeCompteLData.horizontalAlignment = GridData.FILL;
						labelDebitSoldeCompte = new Label(compositeDownTableCompte, SWT.BORDER);
						labelDebitSoldeCompte.setLayoutData(labelDebitSoldeCompteLData);
						labelDebitSoldeCompte.setAlignment(SWT.RIGHT);
					}
					{
						GridData labelCreditSoldeCompteLData = new GridData();
						labelCreditSoldeCompteLData.horizontalAlignment = GridData.FILL;
						labelCreditSoldeCompte = new Label(compositeDownTableCompte, SWT.BORDER);
						labelCreditSoldeCompte.setLayoutData(labelCreditSoldeCompteLData);
						labelCreditSoldeCompte.setAlignment(SWT.RIGHT);
					}
					{
						labelEcritureCompt = new Label(compositeDownTableCompte, SWT.RIGHT);
						labelEcritureCompt.setText("Nombre d'écritures ");
						GridData labelEcritureComptLData = new GridData();
						labelEcritureComptLData.horizontalAlignment = GridData.FILL;
						labelEcritureCompt.setLayoutData(labelEcritureComptLData);
						labelEcritureCompt.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
					}
					{
						GridData labelTotalEcritureCompteLData = new GridData();
						labelTotalEcritureCompteLData.horizontalAlignment = GridData.FILL;
						labelTotalEcritureCompte = new Label(compositeDownTableCompte, SWT.CENTER | SWT.BORDER);
						labelTotalEcritureCompte.setLayoutData(labelTotalEcritureCompteLData);
					}
				}
			}
			{
				compositeZoneButton = new Composite(this, SWT.NONE);
				GridLayout compositeZoneButtonLayout = new GridLayout();
				compositeZoneButtonLayout.makeColumnsEqualWidth = true;
				GridData compositeZoneButtonLData = new GridData();
				compositeZoneButtonLData.horizontalAlignment = GridData.FILL;
				compositeZoneButtonLData.grabExcessHorizontalSpace = true;
				compositeZoneButtonLData.verticalAlignment = GridData.END;
				compositeZoneButtonLData.heightHint = 72;
				compositeZoneButton.setLayoutData(compositeZoneButtonLData);
				compositeZoneButton.setLayout(compositeZoneButtonLayout);
				{
					buttonComptePrint = new Button(compositeZoneButton, SWT.PUSH | SWT.CENTER);
					GridData buttonPrintLData = new GridData();
					buttonPrintLData.horizontalAlignment = GridData.FILL;
					buttonPrintLData.verticalAlignment = GridData.BEGINNING;
					buttonPrintLData.heightHint = 23;
					buttonComptePrint.setLayoutData(buttonPrintLData);
					buttonComptePrint.setText("Imprimez");
					buttonComptePrint.setFont(SWTResourceManager.getFont("Tahoma", 11, 1, false, false));
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Group getGroupDownTablePieces(Composite parent) {
		return groupDownTableCompt;
	}
	
	public Composite getCompositeZoneButton(Composite parent) {
		return compositeZoneButton;
	}
	

	
	public Button getButtonPrint(Composite parent) {
		return buttonComptePrint;
	}









	public Button getButtonComptePrint() {
		return buttonComptePrint;
	}

	public void setButtonComptePrint(Button buttonEcriturePrint) {
		this.buttonComptePrint = buttonEcriturePrint;
	}

	

	

	

	

	

	public Composite getCompositeZoneButton() {
		return compositeZoneButton;
	}

	public void setCompositeZoneButton(Composite compositeZoneButton) {
		this.compositeZoneButton = compositeZoneButton;
	}

	public Group getGroupDownTableCompt() {
		return groupDownTableCompt;
	}

	public void setGroupDownTableCompt(Group groupDownTablePieces) {
		this.groupDownTableCompt = groupDownTablePieces;
	}
	
	public Composite getCompositeDownTableCompte(Composite parent) {
		return compositeDownTableCompte;
	}
	
	public Label getLabelDetailCompte(Composite parent) {
		return labelDetailCompte;
	}
	
	public Label getLabelNumeroCompte(Composite parent) {
		return labelNumeroCompte;
	}
	
	public Label getLabelComptDebit(Composite parent) {
		return labelComptDebit;
	}
	
	public Label getLabelCompteCredit(Composite parent) {
		return labelCompteCredit;
	}
	
	public Label getLabelTotauxMonvementCompte(Composite parent) {
		return labelTotauxMonvementCompte;
	}
	
	public Label getLabelTotauxMonvementDate1(Composite parent) {
		return labelTotauxMonvementDate;
	}
	
	public Label getLabelDebitCompte(Composite parent) {
		return labelDebitCompte;
	}
	
	public Label getLabelCreditCompte(Composite parent) {
		return labelCreditCompte;
	}
	
	public Label getLabelSoldeFin(Composite parent) {
		return labelSoldeFin;
	}
	
	public Label getLabelSoldeFinDate(Composite parent) {
		return labelSoldeFinDate;
	}
	
	public Label getLabelDebitSoldeCompte(Composite parent) {
		return labelDebitSoldeCompte;
	}
	
	public Label getLabelCreditSoldeCompte(Composite parent) {
		return labelCreditSoldeCompte;
	}
	
	public Label getLabelEcritureCompt(Composite parent) {
		return labelEcritureCompt;
	}
	
	public Label getLabelTotalEcritureCompte(Composite parent) {
		return labelTotalEcritureCompte;
	}

	public Label getLabelDetailCompte() {
		return labelDetailCompte;
	}

	public void setLabelDetailCompte(Label labelDetailCompte) {
		this.labelDetailCompte = labelDetailCompte;
	}

	public Label getLabelCompteCredit() {
		return labelCompteCredit;
	}

	public void setLabelCompteCredit(Label labelCompteCredit) {
		this.labelCompteCredit = labelCompteCredit;
	}

	public Label getLabelCreditCompte() {
		return labelCreditCompte;
	}

	public void setLabelCreditCompte(Label labelCreditCompte) {
		this.labelCreditCompte = labelCreditCompte;
	}

	public Label getLabelSoldeFin() {
		return labelSoldeFin;
	}

	public void setLabelSoldeFin(Label labelSoldeFin) {
		this.labelSoldeFin = labelSoldeFin;
	}

	public Label getLabelEcritureCompt() {
		return labelEcritureCompt;
	}

	public void setLabelEcritureCompt(Label labelEcritureCompt) {
		this.labelEcritureCompt = labelEcritureCompt;
	}

	public Label getLabelTotalEcritureCompte() {
		return labelTotalEcritureCompte;
	}

	public void setLabelTotalEcritureCompte(Label labelTotalEcritureCompte) {
		this.labelTotalEcritureCompte = labelTotalEcritureCompte;
	}

	public Label getLabelCreditSoldeCompte() {
		return labelCreditSoldeCompte;
	}

	public void setLabelCreditSoldeCompte(Label labelCreditSoldeCompte) {
		this.labelCreditSoldeCompte = labelCreditSoldeCompte;
	}

	public Label getLabelDebitSoldeCompte() {
		return labelDebitSoldeCompte;
	}

	public void setLabelDebitSoldeCompte(Label labelDebitSoldeCompte) {
		this.labelDebitSoldeCompte = labelDebitSoldeCompte;
	}

	public Label getLabelSoldeFinDate() {
		return labelSoldeFinDate;
	}

	public void setLabelSoldeFinDate(Label labelSoldeFinDate) {
		this.labelSoldeFinDate = labelSoldeFinDate;
	}

	public Label getLabelDebitCompte() {
		return labelDebitCompte;
	}

	public void setLabelDebitCompte(Label labelDebitCompte) {
		this.labelDebitCompte = labelDebitCompte;
	}

	public Label getLabelTotauxMonvementDate() {
		return labelTotauxMonvementDate;
	}

	public void setLabelTotauxMonvementDate(Label labelTotauxMonvementDate) {
		this.labelTotauxMonvementDate = labelTotauxMonvementDate;
	}

	public Label getLabelTotauxMonvementCompte() {
		return labelTotauxMonvementCompte;
	}

	public void setLabelTotauxMonvementCompte(Label labelTotauxMonvementCompte) {
		this.labelTotauxMonvementCompte = labelTotauxMonvementCompte;
	}

	public Label getLabelComptDebit() {
		return labelComptDebit;
	}

	public void setLabelComptDebit(Label labelComptDebit) {
		this.labelComptDebit = labelComptDebit;
	}

	public Label getLabelNumeroCompte() {
		return labelNumeroCompte;
	}

	public void setLabelNumeroCompte(Label labelNumeroCompte) {
		this.labelNumeroCompte = labelNumeroCompte;
	}

	public Composite getCompositeDownTableCompte() {
		return compositeDownTableCompte;
	}

	public void setCompositeDownTableCompte(Composite compositeDownTableCompte) {
		this.compositeDownTableCompte = compositeDownTableCompte;
	}

}

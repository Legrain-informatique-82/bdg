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
public class CompositeOneTable extends CommunTable {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	private Composite compositeInfosConcerLeft;
	private Label labelNombreEcriture;
	private Label labelNombreDB;
	private Label labelSoldeCredit;
	private Label labelSoldeDebit;
	private Label labelSolde;
	private Label labelNomDossier;
	private Label labelTotauxCredit;
	private Label labelTotauxDebit;
	private Label labelInfosLibelle;
	private Label labelDate2;
	private Label labelAuDate;
	private Label labelDate1;
	private Label labelDeDate;
	private Button buttonEcriturePrint;
	private Label labelContenuInfoLibelle;
	private Label labelReference2;
	private Label labelAuReference;
	private Label labelReference1;
	private Label labelDuReference;
	private Label labelMontant2;
	private Label labelAuMontant;
	private Label labelMontant1;
	private Label labelDuMontant;
	private Label labelCompte2;
	private Label labelAuCompte;
	private Label labelCompte1;
	private Label labelDuCompte;
	private Label labelTotaux;
	private Label labelCredit;
	private Label labelDebit;
	private Label labelRightVide11;
	private Label labelNameDB;
	private Composite compositeInfosConcerRight;
	private Composite compositeZoneButton;
	private Group groupDownTablePieces;

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
		CompositeOneTable inst = new CompositeOneTable(shell, SWT.NULL);
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

	public CompositeOneTable(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.makeColumnsEqualWidth = true;
			this.setLayout(thisLayout);
			this.setSize(939, 626);
			{
				groupDownTablePieces = new Group(this, SWT.H_SCROLL | SWT.V_SCROLL);
				GridLayout groupDownTablePiecesLayout = new GridLayout();
				groupDownTablePiecesLayout.makeColumnsEqualWidth = true;
				groupDownTablePiecesLayout.numColumns = 2;
				groupDownTablePieces.setLayout(groupDownTablePiecesLayout);
				GridData groupDownTablePiecesLData = new GridData();
				groupDownTablePiecesLData.grabExcessHorizontalSpace = true;
				groupDownTablePiecesLData.horizontalAlignment = GridData.FILL;
				groupDownTablePiecesLData.verticalAlignment = GridData.BEGINNING;
				groupDownTablePiecesLData.heightHint = 199;
				groupDownTablePieces.setLayoutData(groupDownTablePiecesLData);
				groupDownTablePieces.setText("Information concernant votre recherche :");
				groupDownTablePieces.setFont(SWTResourceManager.getFont("Tahoma", 10, 1, false, false));
				{
					compositeInfosConcerRight = new Composite(groupDownTablePieces, SWT.BORDER);
					GridLayout compositeInfosConcerRightLayout = new GridLayout();
					compositeInfosConcerRightLayout.numColumns = 4;
					compositeInfosConcerRightLayout.makeColumnsEqualWidth = true;
					GridData compositeInfosConcerRightLData = new GridData();
					compositeInfosConcerRightLData.verticalAlignment = GridData.FILL;
					compositeInfosConcerRightLData.grabExcessVerticalSpace = true;
					compositeInfosConcerRightLData.horizontalAlignment = GridData.FILL;
					compositeInfosConcerRight.setLayoutData(compositeInfosConcerRightLData);
					compositeInfosConcerRight.setLayout(compositeInfosConcerRightLayout);
					compositeInfosConcerRight.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
					{
						labelNomDossier = new Label(compositeInfosConcerRight, SWT.NONE);
						labelNomDossier.setText("Nom de Dossier :");
						labelNomDossier.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
					}
					{
						GridData labelNomDBLData = new GridData();
						labelNomDBLData.horizontalSpan = 3;
						labelNomDBLData.heightHint = 13;
						labelNomDBLData.grabExcessHorizontalSpace = true;
						labelNomDBLData.horizontalAlignment = GridData.FILL;
						labelNameDB = new Label(compositeInfosConcerRight, SWT.BORDER);
						labelNameDB.setLayoutData(labelNomDBLData);
					}
					{
						labelNombreEcriture = new Label(compositeInfosConcerRight, SWT.CENTER);
						GridData labelNombreEcritureLData = new GridData();
						labelNombreEcritureLData.horizontalAlignment = GridData.CENTER;
						labelNombreEcriture.setText("Nombre d'ecriture :");
						labelNombreEcriture.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
					}
					{
						GridData labelNombreDBLData = new GridData();
						labelNombreDBLData.horizontalSpan = 3;
						labelNombreDBLData.horizontalAlignment = GridData.FILL;
						labelNombreDB = new Label(compositeInfosConcerRight, SWT.BORDER);
						labelNombreDB.setLayoutData(labelNombreDBLData);
					}
					{
						labelDeDate = new Label(compositeInfosConcerRight, SWT.NONE);
						labelDeDate.setText("De la Date :");
						GridData labelDeDateLData = new GridData();
						labelDeDateLData.widthHint = 90;
						labelDeDateLData.heightHint = 13;
						labelDeDate.setLayoutData(labelDeDateLData);
						labelDeDate.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
					}
					{
						GridData labelDate1LData = new GridData();
						labelDate1LData.horizontalAlignment = GridData.FILL;
						labelDate1 = new Label(compositeInfosConcerRight, SWT.BORDER);
						labelDate1.setLayoutData(labelDate1LData);
					}
					{
						labelAuDate = new Label(compositeInfosConcerRight, SWT.NONE);
						labelAuDate.setText("A la Date :");
						labelAuDate.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
					}
					{
						GridData labelDate2LData = new GridData();
						labelDate2LData.horizontalAlignment = GridData.FILL;
						labelDate2 = new Label(compositeInfosConcerRight, SWT.BORDER);
						labelDate2.setLayoutData(labelDate2LData);
					}
					{
						labelDuCompte = new Label(compositeInfosConcerRight, SWT.NONE);
						labelDuCompte.setText("Du compte :");
						GridData labelDuCompteLData = new GridData();
						labelDuCompteLData.verticalAlignment = GridData.FILL;
						labelDuCompte.setLayoutData(labelDuCompteLData);
						labelDuCompte.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
					}
					{
						GridData labelCompte1LData = new GridData();
						labelCompte1LData.horizontalAlignment = GridData.FILL;
						labelCompte1 = new Label(compositeInfosConcerRight, SWT.BORDER);
						labelCompte1.setLayoutData(labelCompte1LData);
					}
					{
						labelAuCompte = new Label(compositeInfosConcerRight, SWT.NONE);
						labelAuCompte.setText("Au Compte :");
						labelAuCompte.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
					}
					{
						GridData labelCompte2LData = new GridData();
						labelCompte2LData.horizontalAlignment = GridData.FILL;
						labelCompte2 = new Label(compositeInfosConcerRight, SWT.BORDER);
						labelCompte2.setLayoutData(labelCompte2LData);
					}
					{
						labelDuMontant = new Label(compositeInfosConcerRight, SWT.NONE);
						labelDuMontant.setText("Du montant :");
						labelDuMontant.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
					}
					{
						GridData labelMontant1LData = new GridData();
						labelMontant1LData.horizontalAlignment = GridData.FILL;
						labelMontant1 = new Label(compositeInfosConcerRight, SWT.BORDER);
						labelMontant1.setLayoutData(labelMontant1LData);
					}
					{
						labelAuMontant = new Label(compositeInfosConcerRight, SWT.NONE);
						labelAuMontant.setText("Au montant :");
						labelAuMontant.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
					}
					{
						GridData labelMontant2LData = new GridData();
						labelMontant2LData.horizontalAlignment = GridData.FILL;
						labelMontant2 = new Label(compositeInfosConcerRight, SWT.BORDER);
						labelMontant2.setLayoutData(labelMontant2LData);
					}
					{
						labelDuReference = new Label(compositeInfosConcerRight, SWT.NONE);
						labelDuReference.setText("Référence début :");
						labelDuReference.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
					}
					{
						GridData labelReference1LData = new GridData();
						labelReference1LData.horizontalAlignment = GridData.FILL;
						labelReference1 = new Label(compositeInfosConcerRight, SWT.BORDER);
						labelReference1.setLayoutData(labelReference1LData);
					}
					{
						labelAuReference = new Label(compositeInfosConcerRight, SWT.NONE);
						labelAuReference.setText("Référence fin :");
						labelAuReference.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
					}
					{
						GridData label1LData = new GridData();
						label1LData.horizontalAlignment = GridData.FILL;
						labelReference2 = new Label(compositeInfosConcerRight, SWT.BORDER);
						labelReference2.setLayoutData(label1LData);
					}
					{
						labelInfosLibelle = new Label(compositeInfosConcerRight, SWT.NONE);
						GridData labelInfosLibelleLData = new GridData();
						labelInfosLibelleLData.horizontalAlignment = GridData.FILL;
						labelInfosLibelle.setLayoutData(labelInfosLibelleLData);
						labelInfosLibelle.setText("Libellé :");
						labelInfosLibelle.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
					}
					{
						GridData labelContenuInfoLibelleLData = new GridData();
						labelContenuInfoLibelleLData.horizontalAlignment = GridData.FILL;
						labelContenuInfoLibelleLData.horizontalSpan = 3;
						labelContenuInfoLibelle = new Label(compositeInfosConcerRight, SWT.BORDER);
						labelContenuInfoLibelle.setLayoutData(labelContenuInfoLibelleLData);
					}
				}
				{
					compositeInfosConcerLeft = new Composite(groupDownTablePieces, SWT.BORDER);
					GridLayout compositeInfosConcerLeftLayout = new GridLayout();
					compositeInfosConcerLeftLayout.numColumns = 3;
					GridData compositeInfosConcerLeftLData = new GridData();
					compositeInfosConcerLeftLData.grabExcessVerticalSpace = true;
					compositeInfosConcerLeftLData.verticalAlignment = GridData.FILL;
					compositeInfosConcerLeftLData.widthHint = 386;
					compositeInfosConcerLeft.setLayoutData(compositeInfosConcerLeftLData);
					compositeInfosConcerLeft.setLayout(compositeInfosConcerLeftLayout);
					{
						GridData labelRightVide11LData = new GridData();
						labelRightVide11LData.horizontalAlignment = GridData.FILL;
						labelRightVide11 = new Label(compositeInfosConcerLeft, SWT.NONE);
						labelRightVide11.setLayoutData(labelRightVide11LData);
					}
					{
						labelDebit = new Label(compositeInfosConcerLeft, SWT.CENTER);
						labelDebit.setText("Débit");
						GridData labelDebitLData = new GridData();
						labelDebitLData.verticalAlignment = GridData.FILL;
						labelDebitLData.widthHint = 101;
						labelDebit.setLayoutData(labelDebitLData);
						labelDebit.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
					}
					{
						labelCredit = new Label(compositeInfosConcerLeft, SWT.CENTER);
						labelCredit.setText("Crédit");
						GridData labelCreditLData = new GridData();
						labelCreditLData.widthHint = 101;
						labelCreditLData.heightHint = 13;
						labelCredit.setLayoutData(labelCreditLData);
						labelCredit.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
					}
					{
						labelTotaux = new Label(compositeInfosConcerLeft, SWT.CENTER);
						GridData labelTotauxLData = new GridData();
						labelTotauxLData.horizontalAlignment = GridData.FILL;
						labelTotaux.setLayoutData(labelTotauxLData);
						labelTotaux.setText("Totaux :");
						labelTotaux.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
					}
					{
						labelTotauxDebit = new Label(compositeInfosConcerLeft, SWT.CENTER | SWT.BORDER);
						GridData labelVide22LData = new GridData();
						labelVide22LData.horizontalAlignment = GridData.FILL;
						labelTotauxDebit.setLayoutData(labelVide22LData);
					}
					{
						GridData labelVide23LData = new GridData();
						labelVide23LData.horizontalAlignment = GridData.FILL;
						labelTotauxCredit = new Label(compositeInfosConcerLeft, SWT.CENTER | SWT.BORDER);
						labelTotauxCredit.setLayoutData(labelVide23LData);
					}
					{
						labelSolde = new Label(compositeInfosConcerLeft, SWT.CENTER);
						GridData labelSoldeLData = new GridData();
						labelSoldeLData.horizontalAlignment = GridData.FILL;
						labelSolde.setLayoutData(labelSoldeLData);
						labelSolde.setText("Solde : ");
						labelSolde.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
					}
					{
						GridData labelVide32LData = new GridData();
						labelVide32LData.horizontalAlignment = GridData.FILL;
						labelSoldeDebit = new Label(compositeInfosConcerLeft, SWT.CENTER | SWT.BORDER);
						labelSoldeDebit.setLayoutData(labelVide32LData);
					}
					{
						GridData labelVide33LData = new GridData();
						labelVide33LData.horizontalAlignment = GridData.FILL;
						labelSoldeCredit = new Label(compositeInfosConcerLeft, SWT.CENTER | SWT.BORDER);
						labelSoldeCredit.setLayoutData(labelVide33LData);
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
					buttonEcriturePrint = new Button(compositeZoneButton, SWT.PUSH | SWT.CENTER);
					GridData buttonPrintLData = new GridData();
					buttonPrintLData.horizontalAlignment = GridData.FILL;
					buttonPrintLData.verticalAlignment = GridData.BEGINNING;
					buttonPrintLData.heightHint = 23;
					buttonEcriturePrint.setLayoutData(buttonPrintLData);
					buttonEcriturePrint.setText("Imprimer");
					buttonEcriturePrint.setFont(SWTResourceManager.getFont("Tahoma", 11, 1, false, false));
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Group getGroupDownTablePieces(Composite parent) {
		return groupDownTablePieces;
	}
	
	public Composite getCompositeZoneButton(Composite parent) {
		return compositeZoneButton;
	}
	
	public Label getLabelNameDB(Composite parent) {
		return labelNameDB;
	}
	
	public Label getLabelDebit(Composite parent) {
		return labelTotauxDebit;
	}
	
	public Composite getCompositeInfosConcerRight(Composite parent) {
		return compositeInfosConcerRight;
	}
	
	public Label getLabelNomDB(Composite parent) {
		return labelNameDB;
	}
	
	public Label getLabelTotaux(Composite parent) {
		return labelTotaux;
	}
	
	public Label getLabelVide22(Composite parent) {
		return labelTotauxDebit;
	}
	
	public Label getLabelVide23(Composite parent) {
		return labelTotauxCredit;
	}
	
	public Label getLabelVide32(Composite parent) {
		return labelSoldeDebit;
	}
	
	public Label getLabelVide33(Composite parent) {
		return labelSoldeCredit;
	}
	
	public Label getLabelLeftVide22(Composite parent) {
		return labelNombreDB;
	}
	
	public Label getLabelCompte1(Composite parent) {
		return labelCompte1;
	}
	
	public Label getLabelCompte2(Composite parent) {
		return labelCompte2;
	}
	
	public Label getLabelMontant1(Composite parent) {
		return labelMontant1;
	}
	
	public Label getLabelMontant2(Composite parent) {
		return labelMontant2;
	}
	
	public Label getLabelReference1(Composite parent) {
		return labelReference1;
	}
	
	public Label getLabel1(Composite parent) {
		return labelReference2;
	}
	
	public Label getLabelContenuInfoLibelle(Composite parent) {
		return labelContenuInfoLibelle;
	}
	
	public Button getButtonPrint(Composite parent) {
		return buttonEcriturePrint;
	}

	public Composite getCompositeInfosConcerLeft() {
		return compositeInfosConcerLeft;
	}

	public void setCompositeInfosConcerLeft(Composite compositeInfosConcerLeft) {
		this.compositeInfosConcerLeft = compositeInfosConcerLeft;
	}

	public Label getLabelNombreEcriture() {
		return labelNombreEcriture;
	}

	public void setLabelNombreEcriture(Label labelNombreEcriture) {
		this.labelNombreEcriture = labelNombreEcriture;
	}

	public Label getLabelNombreDB() {
		return labelNombreDB;
	}

	public void setLabelNombreDB(Label labelNombreDB) {
		this.labelNombreDB = labelNombreDB;
	}

	public Label getLabelSoldeCredit() {
		return labelSoldeCredit;
	}

	public void setLabelSoldeCredit(Label labelSoldeCredit) {
		this.labelSoldeCredit = labelSoldeCredit;
	}

	public Label getLabelSoldeDebit() {
		return labelSoldeDebit;
	}

	public void setLabelSoldeDebit(Label labelSoldeDebit) {
		this.labelSoldeDebit = labelSoldeDebit;
	}

	public Label getLabelSolde() {
		return labelSolde;
	}

	public void setLabelSolde(Label labelSolde) {
		this.labelSolde = labelSolde;
	}

	public Label getLabelNomDossier() {
		return labelNomDossier;
	}

	public void setLabelNomDossier(Label labelNomDossier) {
		this.labelNomDossier = labelNomDossier;
	}

	public Label getLabelTotauxCredit() {
		return labelTotauxCredit;
	}

	public void setLabelTotauxCredit(Label labelTotauxCredit) {
		this.labelTotauxCredit = labelTotauxCredit;
	}

	public Label getLabelTotauxDebit() {
		return labelTotauxDebit;
	}

	public void setLabelTotauxDebit(Label labelTotauxDebit) {
		this.labelTotauxDebit = labelTotauxDebit;
	}

	public Label getLabelInfosLibelle() {
		return labelInfosLibelle;
	}

	public void setLabelInfosLibelle(Label labelInfosLibelle) {
		this.labelInfosLibelle = labelInfosLibelle;
	}

	public Button getButtonEcriturePrint() {
		return buttonEcriturePrint;
	}

	public void setButtonEcriturePrint(Button buttonEcriturePrint) {
		this.buttonEcriturePrint = buttonEcriturePrint;
	}

	public Label getLabelContenuInfoLibelle() {
		return labelContenuInfoLibelle;
	}

	public void setLabelContenuInfoLibelle(Label labelContenuInfoLibelle) {
		this.labelContenuInfoLibelle = labelContenuInfoLibelle;
	}

	public Label getLabelReference2() {
		return labelReference2;
	}

	public void setLabelReference2(Label labelReference2) {
		this.labelReference2 = labelReference2;
	}

	public Label getLabelAuReference() {
		return labelAuReference;
	}

	public void setLabelAuReference(Label labelAuReference) {
		this.labelAuReference = labelAuReference;
	}

	public Label getLabelReference1() {
		return labelReference1;
	}

	public void setLabelReference1(Label labelReference1) {
		this.labelReference1 = labelReference1;
	}

	public Label getLabelDuReference() {
		return labelDuReference;
	}

	public void setLabelDuReference(Label labelDuReference) {
		this.labelDuReference = labelDuReference;
	}

	public Label getLabelMontant2() {
		return labelMontant2;
	}

	public void setLabelMontant2(Label labelMontant2) {
		this.labelMontant2 = labelMontant2;
	}

	public Label getLabelAuMontant() {
		return labelAuMontant;
	}

	public void setLabelAuMontant(Label labelAuMontant) {
		this.labelAuMontant = labelAuMontant;
	}

	public Label getLabelMontant1() {
		return labelMontant1;
	}

	public void setLabelMontant1(Label labelMontant1) {
		this.labelMontant1 = labelMontant1;
	}

	public Label getLabelDuMontant() {
		return labelDuMontant;
	}

	public void setLabelDuMontant(Label labelDuMontant) {
		this.labelDuMontant = labelDuMontant;
	}

	public Label getLabelCompte2() {
		return labelCompte2;
	}

	public void setLabelCompte2(Label labelCompte2) {
		this.labelCompte2 = labelCompte2;
	}

	public Label getLabelAuCompte() {
		return labelAuCompte;
	}

	public void setLabelAuCompte(Label labelAuCompte) {
		this.labelAuCompte = labelAuCompte;
	}

	public Label getLabelCompte1() {
		return labelCompte1;
	}

	public void setLabelCompte1(Label labelCompte1) {
		this.labelCompte1 = labelCompte1;
	}

	public Label getLabelDuCompte() {
		return labelDuCompte;
	}

	public void setLabelDuCompte(Label labelDuCompte) {
		this.labelDuCompte = labelDuCompte;
	}

	public Label getLabelTotaux() {
		return labelTotaux;
	}

	public void setLabelTotaux(Label labelTotaux) {
		this.labelTotaux = labelTotaux;
	}

	public Label getLabelCredit() {
		return labelCredit;
	}

	public void setLabelCredit(Label labelCredit) {
		this.labelCredit = labelCredit;
	}

	public Label getLabelDebit() {
		return labelDebit;
	}

	public void setLabelDebit(Label labelDebit) {
		this.labelDebit = labelDebit;
	}

	public Label getLabelRightVide11() {
		return labelRightVide11;
	}

	public void setLabelRightVide11(Label labelRightVide11) {
		this.labelRightVide11 = labelRightVide11;
	}

	public Label getLabelNameDB() {
		return labelNameDB;
	}

	public void setLabelNameDB(Label labelNameDB) {
		this.labelNameDB = labelNameDB;
	}

	public Composite getCompositeInfosConcerRight() {
		return compositeInfosConcerRight;
	}

	public void setCompositeInfosConcerRight(Composite compositeInfosConcerRight) {
		this.compositeInfosConcerRight = compositeInfosConcerRight;
	}

	public Composite getCompositeZoneButton() {
		return compositeZoneButton;
	}

	public void setCompositeZoneButton(Composite compositeZoneButton) {
		this.compositeZoneButton = compositeZoneButton;
	}

	public Group getGroupDownTablePieces() {
		return groupDownTablePieces;
	}

	public void setGroupDownTablePieces(Group groupDownTablePieces) {
		this.groupDownTablePieces = groupDownTablePieces;
	}
	
	public Label getLabelDate1(Composite parent) {
		return labelDate1;
	}
	
	public Label getLabelDate2(Composite parent) {
		return labelDate2;
	}

	public Label getLabelDate2() {
		return labelDate2;
	}

	public void setLabelDate2(Label labelDate2) {
		this.labelDate2 = labelDate2;
	}

	public Label getLabelDate1() {
		return labelDate1;
	}

	public void setLabelDate1(Label labelDate1) {
		this.labelDate1 = labelDate1;
	}

}

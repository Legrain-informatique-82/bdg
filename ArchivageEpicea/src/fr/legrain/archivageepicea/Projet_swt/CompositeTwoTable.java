package fr.legrain.archivageepicea.Projet_swt;
import com.cloudgarden.resource.SWTResourceManager;

import org.eclipse.swt.layout.FillLayout;
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
public class CompositeTwoTable extends org.eclipse.swt.widgets.Composite {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
	
	private Composite compositeTwoTable;
	private Composite compositeDownGroup;
	private Composite compositeInfosPieceRight;
	private Label labelRightPieceVide11;
	private Label labelPieceTotauxDebit;
	private Label labelPieceMontantCredit;
	private Label labelPieceMontantDebit;
	private Label labelPieceSolde;
	private Label labelPieceTotauxCredit;
	private Label labelPieceTotaux;
	private Label labelPieceCredit;
	private Label labelPieceDebit;
	private Label labelEcritureDateLivraison;
	private Button buttonPiecePrint;
	private Label labelValueNombreEcriture;
	private Label labelNombreEcriture;
	private Label labelValueEcriutreDateLivraison;
	private Label labelValueEcritureTypeTva;
	private Label labelEcritureTypeTva;
	private Label labelValueEcritureMontantTva;
	private Label labelEcritureMontantTva;
	private Label labelValueEcritureCodeTva;
	private Label labelEcritureCodeTva;
	private Label labelValueEcritureTauxTva;
	private Label labelEcritureTauxTva;
	private Composite compositeInfoPieceLeft;
	private Composite composite1;
	private Composite compositeZoneButton;
	private Table tableDownPiece;
	private Group groupDownTablePieces;
	private Table tableTopPiece;

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
		CompositeTwoTable inst = new CompositeTwoTable(shell, SWT.NULL);
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

	public CompositeTwoTable(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.makeColumnsEqualWidth = true;
			this.setLayout(thisLayout);
			this.setSize(958, 602);
			{
				compositeTwoTable = new Composite(this, SWT.NONE);
				GridLayout compositeTwoTableLayout = new GridLayout();
				compositeTwoTableLayout.makeColumnsEqualWidth = true;
				GridData compositeTwoTableLData = new GridData();
				compositeTwoTableLData.horizontalAlignment = GridData.FILL;
				compositeTwoTableLData.grabExcessHorizontalSpace = true;
				compositeTwoTableLData.verticalAlignment = GridData.FILL;
				compositeTwoTableLData.grabExcessVerticalSpace = true;
				compositeTwoTable.setLayoutData(compositeTwoTableLData);
				compositeTwoTable.setLayout(compositeTwoTableLayout);
				{
					composite1 = new Composite(compositeTwoTable, SWT.NONE);
					GridLayout composite1Layout = new GridLayout();
					composite1Layout.makeColumnsEqualWidth = true;
					GridData composite1LData = new GridData();
					composite1LData.horizontalAlignment = GridData.FILL;
					composite1LData.grabExcessHorizontalSpace = true;
					composite1LData.verticalAlignment = GridData.FILL;
					composite1LData.grabExcessVerticalSpace = true;
					composite1.setLayoutData(composite1LData);
					composite1.setLayout(composite1Layout);
					{
						GridData tableTopPieceLData = new GridData();
						tableTopPieceLData.horizontalAlignment = GridData.FILL;
						tableTopPieceLData.grabExcessHorizontalSpace = true;
						tableTopPieceLData.verticalAlignment = GridData.END;
						tableTopPieceLData.heightHint = 50;
						tableTopPiece = new Table(composite1, SWT.SINGLE | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
						tableTopPiece.setLayoutData(tableTopPieceLData);
					}
					{
						groupDownTablePieces = new Group(composite1, SWT.NONE);
						GridLayout groupDownTablePiecesLayout = new GridLayout();
						groupDownTablePiecesLayout.makeColumnsEqualWidth = true;
						groupDownTablePieces.setLayout(groupDownTablePiecesLayout);
						GridData groupDownTablePiecesLData = new GridData();
						groupDownTablePiecesLData.horizontalAlignment = GridData.FILL;
						groupDownTablePiecesLData.grabExcessHorizontalSpace = true;
						groupDownTablePiecesLData.verticalAlignment = GridData.FILL;
						groupDownTablePiecesLData.grabExcessVerticalSpace = true;
						groupDownTablePieces.setLayoutData(groupDownTablePiecesLData);
						groupDownTablePieces.setText("Corps de la Piece");
						{
							GridData tableDownPieceLData = new GridData();
							tableDownPieceLData.horizontalAlignment = GridData.FILL;
							tableDownPieceLData.verticalAlignment = GridData.FILL;
							tableDownPieceLData.grabExcessHorizontalSpace = true;
							tableDownPieceLData.grabExcessVerticalSpace = true;
							tableDownPiece = new Table(groupDownTablePieces, SWT.SINGLE | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
							tableDownPiece.setLayoutData(tableDownPieceLData);
						}
					}
				}
				{
					compositeDownGroup = new Composite(compositeTwoTable, SWT.BORDER);
					GridLayout compositeDownGroupLayout = new GridLayout();
					compositeDownGroupLayout.makeColumnsEqualWidth = true;
					compositeDownGroupLayout.numColumns = 2;
					GridData compositeDownGroupLData = new GridData();
					compositeDownGroupLData.verticalAlignment = GridData.BEGINNING;
					compositeDownGroupLData.horizontalAlignment = GridData.FILL;
					compositeDownGroupLData.grabExcessHorizontalSpace = true;
					compositeDownGroup.setLayoutData(compositeDownGroupLData);
					compositeDownGroup.setLayout(compositeDownGroupLayout);
					{
						compositeInfoPieceLeft = new Composite(compositeDownGroup, SWT.BORDER);
						GridLayout compositeInfoPieceLeftLayout = new GridLayout();
						compositeInfoPieceLeftLayout.numColumns = 4;
						GridData compositeInfoPieceLeftLData = new GridData();
						compositeInfoPieceLeftLData.verticalAlignment = GridData.FILL;
						compositeInfoPieceLeftLData.horizontalAlignment = GridData.FILL;
						compositeInfoPieceLeftLData.grabExcessHorizontalSpace = true;
						compositeInfoPieceLeft.setLayoutData(compositeInfoPieceLeftLData);
						compositeInfoPieceLeft.setLayout(compositeInfoPieceLeftLayout);
						compositeInfoPieceLeft.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
						{
							labelEcritureTauxTva = new Label(compositeInfoPieceLeft, SWT.NONE);
							labelEcritureTauxTva.setText("Taux Tva :");
							labelEcritureTauxTva.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
						}
						{
							GridData labelValueEcritureTauxTvaLData = new GridData();
							labelValueEcritureTauxTvaLData.heightHint = 17;
							labelValueEcritureTauxTvaLData.widthHint = 94;
							labelValueEcritureTauxTva = new Label(compositeInfoPieceLeft, SWT.BORDER);
							labelValueEcritureTauxTva.setLayoutData(labelValueEcritureTauxTvaLData);
						}
						{
							labelEcritureCodeTva = new Label(compositeInfoPieceLeft, SWT.NONE);
							labelEcritureCodeTva.setText("Code Tva :");
							labelEcritureCodeTva.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
						}
						{
							GridData labelValueEcritureCodeTvaLData = new GridData();
							labelValueEcritureCodeTvaLData.widthHint = 79;
							labelValueEcritureCodeTvaLData.heightHint = 17;
							labelValueEcritureCodeTva = new Label(compositeInfoPieceLeft, SWT.BORDER);
							labelValueEcritureCodeTva.setLayoutData(labelValueEcritureCodeTvaLData);
						}
						{
							labelEcritureMontantTva = new Label(compositeInfoPieceLeft, SWT.NONE);
							labelEcritureMontantTva.setText("Montant Tva :");
							labelEcritureMontantTva.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
						}
						{
							GridData labelValueEcritureMontantTvaLData = new GridData();
							labelValueEcritureMontantTvaLData.horizontalAlignment = GridData.FILL;
							labelValueEcritureMontantTva = new Label(compositeInfoPieceLeft, SWT.BORDER);
							labelValueEcritureMontantTva.setLayoutData(labelValueEcritureMontantTvaLData);
						}
						{
							labelEcritureTypeTva = new Label(compositeInfoPieceLeft, SWT.NONE);
							labelEcritureTypeTva.setText("Type Tva :");
							labelEcritureTypeTva.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
						}
						{
							GridData labelValueEcritureTypeTvaLData = new GridData();
							labelValueEcritureTypeTvaLData.horizontalAlignment = GridData.FILL;
							labelValueEcritureTypeTva = new Label(compositeInfoPieceLeft, SWT.BORDER);
							labelValueEcritureTypeTva.setLayoutData(labelValueEcritureTypeTvaLData);
						}
						{
							labelEcritureDateLivraison = new Label(compositeInfoPieceLeft, SWT.NONE);
							labelEcritureDateLivraison.setText("Date livraison :");
							labelEcritureDateLivraison.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
						}
						{
							GridData labelValueEcriutreDateLivraisonLData = new GridData();
							labelValueEcriutreDateLivraisonLData.horizontalAlignment = GridData.FILL;
							labelValueEcriutreDateLivraisonLData.heightHint = 13;
							labelValueEcriutreDateLivraison = new Label(compositeInfoPieceLeft, SWT.BORDER);
							labelValueEcriutreDateLivraison.setLayoutData(labelValueEcriutreDateLivraisonLData);
						}
						{
							labelNombreEcriture = new Label(compositeInfoPieceLeft, SWT.NONE);
							labelNombreEcriture.setText("Nombre\nd'ecriture : ");
							labelNombreEcriture.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
						}
						{
							GridData labelValueNombreEcritureLData = new GridData();
							labelValueNombreEcritureLData.horizontalAlignment = GridData.FILL;
							labelValueNombreEcriture = new Label(compositeInfoPieceLeft, SWT.BORDER);
							labelValueNombreEcriture.setLayoutData(labelValueNombreEcritureLData);
							labelValueNombreEcriture.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
						}
					}
					{
						compositeInfosPieceRight = new Composite(compositeDownGroup, SWT.BORDER);
						GridLayout compositeInfosConcerLeftLayout = new GridLayout();
						compositeInfosConcerLeftLayout.numColumns = 3;
						compositeInfosConcerLeftLayout.makeColumnsEqualWidth = true;
						compositeInfosPieceRight.setLayout(compositeInfosConcerLeftLayout);
						GridData compositeInfosConcerLeftLData = new GridData();
						compositeInfosConcerLeftLData.verticalAlignment = GridData.FILL;
						compositeInfosConcerLeftLData.grabExcessVerticalSpace = true;
						compositeInfosConcerLeftLData.grabExcessHorizontalSpace = true;
						compositeInfosConcerLeftLData.horizontalAlignment = GridData.FILL;
						compositeInfosPieceRight.setLayoutData(compositeInfosConcerLeftLData);
						{
							labelRightPieceVide11 = new Label(compositeInfosPieceRight, SWT.NONE);
							GridData labelRightVide11LData = new GridData();
							labelRightVide11LData.horizontalAlignment = GridData.FILL;
							labelRightPieceVide11.setLayoutData(labelRightVide11LData);
						}
						{
							labelPieceDebit = new Label(compositeInfosPieceRight, SWT.CENTER);
							labelPieceDebit.setText("Débit");
							GridData labelDebitLData = new GridData();
							labelDebitLData.verticalAlignment = GridData.FILL;
							labelDebitLData.widthHint = 101;
							labelPieceDebit.setLayoutData(labelDebitLData);
							labelPieceDebit.setFont(SWTResourceManager.getFont("Tahoma",8,1,false,false));
						}
						{
							labelPieceCredit = new Label(compositeInfosPieceRight, SWT.CENTER);
							labelPieceCredit.setText("Crédit");
							GridData labelCreditLData = new GridData();
							labelCreditLData.widthHint = 101;
							labelCreditLData.heightHint = 13;
							labelPieceCredit.setLayoutData(labelCreditLData);
							labelPieceCredit.setFont(SWTResourceManager.getFont("Tahoma",8,1,false,false));
						}
						{
							labelPieceTotaux = new Label(compositeInfosPieceRight, SWT.CENTER);
							labelPieceTotaux.setText("Totaux :");
							GridData labelTotauxLData = new GridData();
							labelTotauxLData.horizontalAlignment = GridData.FILL;
							labelPieceTotaux.setLayoutData(labelTotauxLData);
							labelPieceTotaux.setFont(SWTResourceManager.getFont("Tahoma",8,1,false,false));
						}
						{
							labelPieceTotauxDebit = new Label(compositeInfosPieceRight, SWT.CENTER | SWT.BORDER);
							GridData labelVide22LData = new GridData();
							labelVide22LData.horizontalAlignment = GridData.FILL;
							labelPieceTotauxDebit.setLayoutData(labelVide22LData);
						}
						{
							labelPieceTotauxCredit = new Label(compositeInfosPieceRight, SWT.CENTER | SWT.BORDER);
							GridData labelVide23LData = new GridData();
							labelVide23LData.horizontalAlignment = GridData.FILL;
							labelPieceTotauxCredit.setLayoutData(labelVide23LData);
						}
						{
							labelPieceSolde = new Label(compositeInfosPieceRight, SWT.CENTER);
							labelPieceSolde.setText("Montant da la pièce : ");
							GridData labelSoldeLData = new GridData();
							labelSoldeLData.horizontalAlignment = GridData.FILL;
							labelPieceSolde.setLayoutData(labelSoldeLData);
							labelPieceSolde.setFont(SWTResourceManager.getFont("Tahoma",8,1,false,false));
						}
						{
							labelPieceMontantDebit = new Label(compositeInfosPieceRight, SWT.CENTER | SWT.BORDER);
							GridData labelVide32LData = new GridData();
							labelVide32LData.horizontalAlignment = GridData.FILL;
							labelPieceMontantDebit.setLayoutData(labelVide32LData);
						}
						{
							labelPieceMontantCredit = new Label(compositeInfosPieceRight, SWT.CENTER | SWT.BORDER);
							GridData labelVide33LData = new GridData();
							labelVide33LData.horizontalAlignment = GridData.FILL;
							labelPieceMontantCredit.setLayoutData(labelVide33LData);
						}
					}
				}
				{
					compositeZoneButton = new Composite(compositeTwoTable, SWT.BORDER);
					GridLayout composite1Layout = new GridLayout();
					composite1Layout.makeColumnsEqualWidth = true;
					GridData composite1LData = new GridData();
					composite1LData.grabExcessHorizontalSpace = true;
					composite1LData.horizontalAlignment = GridData.FILL;
					composite1LData.verticalAlignment = GridData.END;
					compositeZoneButton.setLayoutData(composite1LData);
					compositeZoneButton.setLayout(composite1Layout);
					{
						buttonPiecePrint = new Button(compositeZoneButton, SWT.PUSH | SWT.CENTER);
						buttonPiecePrint.setText("Imprimez");
						buttonPiecePrint.setFont(SWTResourceManager.getFont("Tahoma", 11, 1, false, false));
					}
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Composite getCompositeTwoTable(Composite parent) {
		return compositeTwoTable;
	}
	
	public Table getTableTopPiece(Composite parent) {
		return tableTopPiece;
	}
	
	public Group getGroupDownTablePieces(Composite parent) {
		return groupDownTablePieces;
	}
	
	public Table getTableDownPiece(Composite parent) {
		return tableDownPiece;
	}
	
	public Composite getCompositeInfoPieceLeft(Composite parent) {
		return compositeInfoPieceLeft;
	}
	
	public Label getLabelValueEcritureTauxTva(Composite parent) {
		return labelValueEcritureTauxTva;
	}
	
	public Label getLabelValueEcritureCodeTva(Composite parent) {
		return labelValueEcritureCodeTva;
	}
	
	public Label getLabelValueEcritureMontantTva(Composite parent) {
		return labelValueEcritureMontantTva;
	}
	
	public Label getLabelEcritureTypeTva(Composite parent) {
		return labelEcritureTypeTva;
	}
	
	public Label getLabelValueEcritureTypeTva(Composite parent) {
		return labelValueEcritureTypeTva;
	}
	
	public Label getLabelValueEcriutreDateLivraison(Composite parent) {
		return labelValueEcriutreDateLivraison;
	}
	
	public Label getLabelValueNombreEcriture(Composite parent) {
		return labelValueNombreEcriture;
	}
	
	public Button getButtonImprimerPiece(Composite parent) {
		return buttonPiecePrint;
	}

	public Composite getCompositeTwoTable() {
		return compositeTwoTable;
	}

	public void setCompositeTwoTable(Composite compositeTwoTable) {
		this.compositeTwoTable = compositeTwoTable;
	}

	public Composite getCompositeDownGroup() {
		return compositeDownGroup;
	}

	public void setCompositeDownGroup(Composite compositeDownGroup) {
		this.compositeDownGroup = compositeDownGroup;
	}

	public Composite getCompositeInfosPieceRight() {
		return compositeInfosPieceRight;
	}

	public void setCompositeInfosPieceRight(Composite compositeInfosPieceRight) {
		this.compositeInfosPieceRight = compositeInfosPieceRight;
	}

	public Label getLabelRightPieceVide11() {
		return labelRightPieceVide11;
	}

	public void setLabelRightPieceVide11(Label labelRightPieceVide11) {
		this.labelRightPieceVide11 = labelRightPieceVide11;
	}

	public Label getLabelPieceTotauxDebit() {
		return labelPieceTotauxDebit;
	}

	public void setLabelPieceTotauxDebit(Label labelPieceTotauxDebit) {
		this.labelPieceTotauxDebit = labelPieceTotauxDebit;
	}

	public Label getLabelPieceMontantCredit() {
		return labelPieceMontantCredit;
	}

	public void setLabelPieceMontantCredit(Label labelPieceSoldeCredit) {
		this.labelPieceMontantCredit = labelPieceSoldeCredit;
	}

	public Label getLabelPieceMontantDebit() {
		return labelPieceMontantDebit;
	}

	public void setLabelPieceMontantDebit(Label labelPieceSoldeDebit) {
		this.labelPieceMontantDebit = labelPieceSoldeDebit;
	}

	public Label getLabelPieceSolde() {
		return labelPieceSolde;
	}

	public void setLabelPieceSolde(Label labelPieceSolde) {
		this.labelPieceSolde = labelPieceSolde;
	}

	public Label getLabelPieceTotauxCredit() {
		return labelPieceTotauxCredit;
	}

	public void setLabelPieceTotauxCredit(Label labelPieceTotauxCredit) {
		this.labelPieceTotauxCredit = labelPieceTotauxCredit;
	}

	public Label getLabelPieceTotaux() {
		return labelPieceTotaux;
	}

	public void setLabelPieceTotaux(Label labelPieceTotaux) {
		this.labelPieceTotaux = labelPieceTotaux;
	}

	public Label getLabelPieceCredit() {
		return labelPieceCredit;
	}

	public void setLabelPieceCredit(Label labelPieceCredit) {
		this.labelPieceCredit = labelPieceCredit;
	}

	public Label getLabelPieceDebit() {
		return labelPieceDebit;
	}

	public void setLabelPieceDebit(Label labelPieceDebit) {
		this.labelPieceDebit = labelPieceDebit;
	}

	public Label getLabelEcritureDateLivraison() {
		return labelEcritureDateLivraison;
	}

	public void setLabelEcritureDateLivraison(Label labelEcritureDateLivraison) {
		this.labelEcritureDateLivraison = labelEcritureDateLivraison;
	}

	public Button getButtonPiecePrint() {
		return buttonPiecePrint;
	}

	public void setButtonPiecePrint(Button buttonPiecePrint) {
		this.buttonPiecePrint = buttonPiecePrint;
	}

	public Label getLabelValueNombreEcriture() {
		return labelValueNombreEcriture;
	}

	public void setLabelValueNombreEcriture(Label labelValueNombreEcriture) {
		this.labelValueNombreEcriture = labelValueNombreEcriture;
	}

	public Label getLabelNombreEcriture() {
		return labelNombreEcriture;
	}

	public void setLabelNombreEcriture(Label labelNombreEcriture) {
		GridData labelNombreEcritureLData = new GridData();
		labelNombreEcritureLData.widthHint = 69;
		labelNombreEcritureLData.heightHint = 28;
		this.labelNombreEcriture = labelNombreEcriture;
		labelNombreEcriture.setLayoutData(labelNombreEcritureLData);
	}

	public Label getLabelValueEcriutreDateLivraison() {
		return labelValueEcriutreDateLivraison;
	}

	public void setLabelValueEcriutreDateLivraison(
			Label labelValueEcriutreDateLivraison) {
		this.labelValueEcriutreDateLivraison = labelValueEcriutreDateLivraison;
		labelValueEcriutreDateLivraison.setSize(118, 17);
	}

	public Label getLabelValueEcritureTypeTva() {
		return labelValueEcritureTypeTva;
	}

	public void setLabelValueEcritureTypeTva(Label labelValueEcritureTypeTva) {
		this.labelValueEcritureTypeTva = labelValueEcritureTypeTva;
	}

	public Label getLabelEcritureTypeTva() {
		return labelEcritureTypeTva;
	}

	public void setLabelEcritureTypeTva(Label labelEcritureTypeTva) {
		this.labelEcritureTypeTva = labelEcritureTypeTva;
	}

	public Label getLabelValueEcritureMontantTva() {
		return labelValueEcritureMontantTva;
	}

	public void setLabelValueEcritureMontantTva(Label labelValueEcritureMontantTva) {
		this.labelValueEcritureMontantTva = labelValueEcritureMontantTva;
	}

	public Label getLabelEcritureMontantTva() {
		return labelEcritureMontantTva;
	}

	public void setLabelEcritureMontantTva(Label labelEcritureMontantTva) {
		this.labelEcritureMontantTva = labelEcritureMontantTva;
	}

	public Label getLabelValueEcritureCodeTva() {
		return labelValueEcritureCodeTva;
	}

	public void setLabelValueEcritureCodeTva(Label labelValueEcritureCodeTva) {
		this.labelValueEcritureCodeTva = labelValueEcritureCodeTva;
	}

	public Label getLabelEcritureCodeTva() {
		return labelEcritureCodeTva;
	}

	public void setLabelEcritureCodeTva(Label labelEcritureCodeTva) {
		this.labelEcritureCodeTva = labelEcritureCodeTva;
	}

	public Label getLabelValueEcritureTauxTva() {
		return labelValueEcritureTauxTva;
	}

	public void setLabelValueEcritureTauxTva(Label labelValueEcritureTauxTva) {
		this.labelValueEcritureTauxTva = labelValueEcritureTauxTva;
	}

	public Label getLabelEcritureTauxTva() {
		return labelEcritureTauxTva;
	}

	public void setLabelEcritureTauxTva(Label labelEcritureTauxTva) {
		this.labelEcritureTauxTva = labelEcritureTauxTva;
	}

	public Composite getCompositeInfoPieceLeft() {
		return compositeInfoPieceLeft;
	}

	public void setCompositeInfoPieceLeft(Composite compositeInfoPieceLeft) {
		this.compositeInfoPieceLeft = compositeInfoPieceLeft;
	}

	public Composite getComposite1() {
		return composite1;
	}

	public void setComposite1(Composite composite1) {
		this.composite1 = composite1;
	}

	public Composite getCompositeZoneButton() {
		return compositeZoneButton;
	}

	public void setCompositeZoneButton(Composite compositeZoneButton) {
		this.compositeZoneButton = compositeZoneButton;
	}

	public Table getTableDownPiece() {
		return tableDownPiece;
	}

	public void setTableDownPiece(Table tableDownPiece) {
		this.tableDownPiece = tableDownPiece;
	}

	public Group getGroupDownTablePieces() {
		return groupDownTablePieces;
	}

	public void setGroupDownTablePieces(Group groupDownTablePieces) {
		this.groupDownTablePieces = groupDownTablePieces;
	}

	public Table getTableTopPiece() {
		return tableTopPiece;
	}

	public void setTableTopPiece(Table tableTopPiece) {
		this.tableTopPiece = tableTopPiece;
	}

}

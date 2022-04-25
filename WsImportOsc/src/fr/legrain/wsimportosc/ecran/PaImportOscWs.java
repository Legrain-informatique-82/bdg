package fr.legrain.wsimportosc.ecran;
import com.cloudgarden.resource.SWTResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import fr.legrain.lib.gui.PaBtnReduit;
import fr.legrain.lib.gui.cdatetimeLgr;

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
public class PaImportOscWs extends org.eclipse.swt.widgets.Composite {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
	

	
	
	private PaBtnReduit paBtn1;
	private List tfListeTiers;
	private Button BtnReinitialiser;
	private Composite composite1;
	private StyledText tfStyleEditeur;
	private Label label1;
	private cdatetimeLgr tfDateDocument;
	private Label laDateDocument;
	private Text tfLibelle;
	private Label laLibelleDocument;
	private Text tfRecherche;
	private Button btnReinitDocCree;
	private Button getCbReExport;
	private Button BtnImprimerDoc;
	private Button BtnRemonterDoc;
	private List tfListeDoc;
	private Label laCodeTiersSelection;
	private Label sommeTotauxTtc;
	private Label labelTotauxTtc;
	private Label sommeTotuaxHt;
	private Label labelTotauxHt;
	private Composite compositeSommeFacyure;
	private Label labelMessage;
	private Button buttonSelect;
	private Composite composite3;
	private Button buttonImporter;
	private Text textPassword;
	private Text textLogin;
	private Label label3;
	private Label label2;
	private Composite composite2;
	private Label laTotalDoc;
	private Label laNbFacture;
	private Label laSumWlgr;
	private Label laNbWlgr;
	private Composite PaSelection;
	private Text tfCode_Tiers;
	private Label laCodeTiers;
	private Label laListeCodeTiers;
	private Composite paCorpsFormulaire;
	private Composite PaEntete;
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
		PaImportOscWs inst = new PaImportOscWs(shell, SWT.NULL);
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

	public PaImportOscWs(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {

		try {
			GridLayout thisLayout = new GridLayout();
			this.setLayout(thisLayout);
			thisLayout.numColumns = 8;
			thisLayout.horizontalSpacing = 4;
			this.setSize(877, 524);
			{
				composite2 = new Composite(this, SWT.BORDER);
				GridLayout composite2Layout = new GridLayout();
				composite2Layout.numColumns = 4;
				GridData composite2LData = new GridData();
				composite2LData.horizontalAlignment = GridData.FILL;
				composite2LData.grabExcessHorizontalSpace = true;
				composite2LData.horizontalSpan = 8;
				composite2LData.heightHint = 95;
				composite2.setLayoutData(composite2LData);
				composite2.setLayout(composite2Layout);
				{
					label2 = new Label(composite2, SWT.NONE);
					label2.setText("Login :");
				}
				{
					textLogin = new Text(composite2, SWT.BORDER);
					GridData textLoginData = new GridData();
					textLoginData.horizontalSpan = 3;
					textLoginData.widthHint = 266;
					textLoginData.heightHint = 13;
					textLogin.setLayoutData(textLoginData);
				}
				{
					label3 = new Label(composite2, SWT.NONE);
					label3.setText("Password :");
				}
				{
					textPassword = new Text(composite2, SWT.BORDER);
					GridData textPasswordData = new GridData();
					textPasswordData.widthHint = 267;
					textPasswordData.heightHint = 13;
					textPassword.setLayoutData(textPasswordData);
				}
				{
					buttonImporter = new Button(composite2, SWT.PUSH | SWT.CENTER);
					GridData buttonImporterLData = new GridData();
					buttonImporterLData.horizontalSpan = 2;
					buttonImporterLData.horizontalAlignment = GridData.END;
					buttonImporterLData.grabExcessHorizontalSpace = true;
					buttonImporter.setLayoutData(buttonImporterLData);
					buttonImporter.setText("Importer");
				}
				{
					labelMessage = new Label(composite2, SWT.BORDER);
					GridData labelMessageLData = new GridData();
					labelMessageLData.horizontalSpan = 4;
					labelMessageLData.grabExcessHorizontalSpace = true;
					labelMessageLData.verticalAlignment = GridData.FILL;
					labelMessageLData.horizontalAlignment = GridData.FILL;
					labelMessageLData.grabExcessVerticalSpace = true;
					labelMessage.setLayoutData(labelMessageLData);
					labelMessage.setAlignment(SWT.CENTER);
					labelMessage.setFont(SWTResourceManager.getFont("Sans", 9, 1, false, false));
				}
			}
			{
				PaEntete = new Composite(this, SWT.NONE);
				GridLayout PaEnteteLayout = new GridLayout();
				GridData PaEnteteLData = new GridData();
				PaEnteteLData.horizontalSpan = 8;
				PaEnteteLData.verticalAlignment = GridData.FILL;
				PaEnteteLData.horizontalAlignment = GridData.FILL;
				PaEnteteLData.grabExcessHorizontalSpace = true;
				PaEnteteLayout.numColumns = 4;
				PaEntete.setLayout(PaEnteteLayout);
				PaEntete.setLayoutData(PaEnteteLData);
				{
					laLibelleDocument = new Label(PaEntete, SWT.NONE);
					GridData laLibelleDocumentLData = new GridData();
					laLibelleDocumentLData.widthHint = 52;
					laLibelleDocumentLData.heightHint = 13;
					laLibelleDocument.setLayoutData(laLibelleDocumentLData);
					laLibelleDocument.setText("LIBELLE :");
				}
				{
					GridData tfLibelleLData = new GridData();
					tfLibelleLData.heightHint = 13;
					tfLibelleLData.horizontalAlignment = GridData.FILL;
					tfLibelleLData.grabExcessHorizontalSpace = true;
					tfLibelle = new Text(PaEntete, SWT.BORDER);
					tfLibelle.setLayoutData(tfLibelleLData);
				}
				{
					laDateDocument = new Label(PaEntete, SWT.NONE);
					GridData laDateDocumentLData = new GridData();
					laDateDocumentLData.heightHint = 13;
					laDateDocumentLData.widthHint = 99;
					laDateDocumentLData.horizontalAlignment = GridData.CENTER;
					laDateDocument.setLayoutData(laDateDocumentLData);
					laDateDocument.setText("Date du document");
				}
//				{
//					GridData tfDateDocumentLData = new GridData();
//					tfDateDocumentLData.widthHint = 120;
//					tfDateDocumentLData.heightHint = 17;
//					tfDateDocument = new cdatetimeLgr(PaEntete, SWT.NONE);
//					tfDateDocument.setLayoutData(tfDateDocumentLData);
//				}				
PaSelection = new Composite(this, SWT.NONE);
				GridLayout PaSelectionLayout = new GridLayout();
				PaSelectionLayout.makeColumnsEqualWidth = true;
				PaSelectionLayout.numColumns = 2;
				GridData PaSelectionLData = new GridData();
				PaSelectionLData.verticalAlignment = GridData.FILL;
				PaSelectionLData.horizontalAlignment = GridData.FILL;
				PaSelectionLData.grabExcessVerticalSpace = true;
				PaSelection.setLayoutData(PaSelectionLData);
				PaSelection.setLayout(PaSelectionLayout);
				{
					laCodeTiersSelection = new Label(PaSelection, SWT.NONE);
					laCodeTiersSelection.setText("Factures OS Commerce");
					GridData laCodeTiersSelectionLData = new GridData();
					laCodeTiersSelectionLData.horizontalSpan = 2;
					laCodeTiersSelectionLData.grabExcessHorizontalSpace = true;
					laCodeTiersSelectionLData.horizontalAlignment = GridData.FILL;
					laCodeTiersSelection.setLayoutData(laCodeTiersSelectionLData);
				}
				{
					tfRecherche = new Text(PaSelection, SWT.BORDER);
					GridData tfRechercheLData = new GridData();
					tfRechercheLData.horizontalAlignment = GridData.FILL;
					tfRechercheLData.heightHint = 13;
					tfRechercheLData.grabExcessHorizontalSpace = true;
					tfRechercheLData.horizontalSpan = 2;
					tfRecherche.setLayoutData(tfRechercheLData);
					tfRecherche.setSize(143, 19);
				}
				{
					GridData tfListeTiersLData = new GridData();
					tfListeTiersLData.verticalAlignment = GridData.FILL;
					tfListeTiersLData.grabExcessVerticalSpace = true;
					tfListeTiersLData.horizontalAlignment = GridData.FILL;
					tfListeTiersLData.grabExcessHorizontalSpace = true;
					tfListeTiersLData.horizontalSpan = 2;
					tfListeTiers = new List(PaSelection, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
					tfListeTiers.setLayoutData(tfListeTiersLData);
				}
				{
					laNbWlgr = new Label(PaSelection, SWT.BORDER);
					GridData laNbWlgrLData = new GridData();
					laNbWlgrLData.heightHint = 13;
					laNbWlgrLData.horizontalAlignment = GridData.FILL;
					laNbWlgrLData.grabExcessHorizontalSpace = true;
					laNbWlgr.setLayoutData(laNbWlgrLData);
					laNbWlgr.setToolTipText("Nombre de bons non trait�s");
					laNbWlgr.setText("Totaux :");
				}
				{
					laSumWlgr = new Label(PaSelection, SWT.BORDER);
					GridData laSumWlgrLData = new GridData();
					laSumWlgrLData.heightHint = 13;
					laSumWlgrLData.grabExcessHorizontalSpace = true;
					laSumWlgrLData.horizontalAlignment = GridData.FILL;
					laSumWlgr.setLayoutData(laSumWlgrLData);
					laSumWlgr.setToolTipText("Somme restante � venir");
				}
				{
					BtnReinitialiser = new Button(PaSelection, SWT.PUSH | SWT.CENTER);
					GridData BtnReinitialiserLData = new GridData();
					BtnReinitialiserLData.heightHint = 23;
					BtnReinitialiserLData.grabExcessHorizontalSpace = true;
					BtnReinitialiserLData.horizontalAlignment = GridData.FILL;
					BtnReinitialiserLData.horizontalSpan = 2;
					BtnReinitialiser.setLayoutData(BtnReinitialiserLData);
					BtnReinitialiser.setText("Réinitialiser");
				}
			}
			{
				composite3 = new Composite(this, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				GridData composite3LData = new GridData();
				composite3LData.widthHint = 38;
				composite3LData.grabExcessVerticalSpace = true;
				composite3LData.verticalAlignment = GridData.FILL;
				composite3.setLayoutData(composite3LData);
				composite3.setLayout(composite3Layout);
				{
					buttonSelect = new Button(composite3, SWT.PUSH | SWT.CENTER);
					GridData buttonSelectLData = new GridData();
					buttonSelectLData.grabExcessVerticalSpace = true;
					buttonSelect.setLayoutData(buttonSelectLData);
					buttonSelect.setText(">>");
				}
			}
			{
				GridData composite1LData = new GridData();
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.grabExcessVerticalSpace = true;
				composite1LData.horizontalAlignment = GridData.FILL;
				paCorpsFormulaire = new Composite(this, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.makeColumnsEqualWidth = true;
				paCorpsFormulaire.setLayout(composite1Layout);
				paCorpsFormulaire.setLayoutData(composite1LData);
				{
					laListeCodeTiers = new Label(paCorpsFormulaire, SWT.NONE);
					GridData laCODE_ARTICLELData = new GridData();
					laCODE_ARTICLELData.verticalAlignment = GridData.BEGINNING;
					laCODE_ARTICLELData.heightHint = 13;
					laCODE_ARTICLELData.horizontalAlignment = GridData.FILL;
					laListeCodeTiers.setLayoutData(laCODE_ARTICLELData);
					laListeCodeTiers.setText("Liste des factures OS Commerce sélectionnées");
				}
				{
					GridData tfStyleEditeurLData = new GridData();
					tfStyleEditeurLData.verticalAlignment = GridData.FILL;
					tfStyleEditeurLData.grabExcessVerticalSpace = true;
					tfStyleEditeurLData.grabExcessHorizontalSpace = true;
					tfStyleEditeurLData.horizontalAlignment = GridData.FILL;
					tfStyleEditeur = new StyledText(paCorpsFormulaire, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
					tfStyleEditeur.setLayoutData(tfStyleEditeurLData);
				}
				{
					compositeSommeFacyure = new Composite(paCorpsFormulaire, SWT.NONE);
					GridLayout compositeSommeFacyureLayout = new GridLayout();
					compositeSommeFacyureLayout.numColumns = 4;
					compositeSommeFacyureLayout.makeColumnsEqualWidth = true;
					GridData compositeSommeFacyureLData = new GridData();
					compositeSommeFacyureLData.horizontalAlignment = GridData.FILL;
					compositeSommeFacyureLData.heightHint = 25;
					compositeSommeFacyure.setLayoutData(compositeSommeFacyureLData);
					compositeSommeFacyure.setLayout(compositeSommeFacyureLayout);
					{
						labelTotauxHt = new Label(compositeSommeFacyure, SWT.LEFT | SWT.BORDER);
						GridData labelTotauxHtLData = new GridData();
						labelTotauxHtLData.grabExcessVerticalSpace = true;
						labelTotauxHtLData.grabExcessHorizontalSpace = true;
						labelTotauxHtLData.verticalAlignment = GridData.BEGINNING;
						labelTotauxHtLData.horizontalAlignment = GridData.FILL;
						labelTotauxHt.setLayoutData(labelTotauxHtLData);
						labelTotauxHt.setText("Totaux HT :");
					}
					{
						GridData sommeTotuaxHtLData = new GridData();
						sommeTotuaxHtLData.horizontalAlignment = GridData.FILL;
						sommeTotuaxHt = new Label(compositeSommeFacyure, SWT.BORDER);
						sommeTotuaxHt.setLayoutData(sommeTotuaxHtLData);
					}
					{
						labelTotauxTtc = new Label(compositeSommeFacyure, SWT.BORDER);
						GridData labelTotauxTtcLData = new GridData();
						labelTotauxTtcLData.horizontalAlignment = GridData.FILL;
						labelTotauxTtc.setLayoutData(labelTotauxTtcLData);
						labelTotauxTtc.setText("Totaux TTC:");
					}
					{
						GridData sommeTotauxTtcLData = new GridData();
						sommeTotauxTtcLData.horizontalAlignment = GridData.FILL;
						sommeTotauxTtcLData.grabExcessVerticalSpace = true;
						sommeTotauxTtc = new Label(compositeSommeFacyure, SWT.BORDER);
						sommeTotauxTtc.setLayoutData(sommeTotauxTtcLData);
					}
				}
			}
			{
				GridData composite1LData1 = new GridData();
				composite1LData1.horizontalSpan = 2;
				composite1LData1.grabExcessVerticalSpace = true;
				composite1LData1.verticalAlignment = GridData.FILL;
				composite1LData1.horizontalAlignment = GridData.FILL;
				composite1 = new Composite(this, SWT.NONE);
				GridLayout composite1Layout1 = new GridLayout();
				composite1Layout1.numColumns = 2;
				composite1Layout1.horizontalSpacing = 2;
				composite1Layout1.makeColumnsEqualWidth = true;
				composite1.setLayout(composite1Layout1);
				composite1.setLayoutData(composite1LData1);
				{
					label1 = new Label(composite1, SWT.NONE);
					label1.setText("Facture importées");
					GridData label1LData = new GridData();
					label1LData.widthHint = 164;
					label1LData.heightHint = 13;
					label1LData.horizontalSpan = 3;
					label1.setLayoutData(label1LData);
				}
				{
					GridData list1LData = new GridData();
					list1LData.verticalAlignment = GridData.FILL;
					list1LData.grabExcessVerticalSpace = true;
					list1LData.horizontalSpan = 2;
					list1LData.horizontalAlignment = GridData.FILL;
					list1LData.grabExcessHorizontalSpace = true;
					tfListeDoc = new List(composite1, SWT.MULTI | SWT.V_SCROLL | SWT.BORDER);
					tfListeDoc.setLayoutData(list1LData);
				}
				{
					laNbFacture = new Label(composite1, SWT.BORDER);
					GridData laNbFactureLData = new GridData();
					laNbFactureLData.grabExcessHorizontalSpace = true;
					laNbFactureLData.horizontalAlignment = GridData.FILL;
					laNbFacture.setLayoutData(laNbFactureLData);
					laNbFacture.setToolTipText("Nombre de documents cr��s");
				}
				{
					laTotalDoc = new Label(composite1, SWT.BORDER);
					GridData laTotalDocLData = new GridData();
					laTotalDocLData.heightHint = 13;
					laTotalDocLData.grabExcessHorizontalSpace = true;
					laTotalDocLData.horizontalAlignment = GridData.FILL;
					laTotalDoc.setLayoutData(laTotalDocLData);
					laTotalDoc.setToolTipText("Total factur�");
				}
				{
					BtnRemonterDoc = new Button(composite1, SWT.PUSH | SWT.CENTER);
					GridData button2LData = new GridData();
					button2LData.heightHint = 23;
					button2LData.grabExcessHorizontalSpace = true;
					button2LData.horizontalAlignment = GridData.FILL;
					BtnRemonterDoc.setLayoutData(button2LData);
					BtnRemonterDoc.setText("Remonter");
				}
				{
					btnReinitDocCree = new Button(composite1, SWT.PUSH | SWT.CENTER);
					GridData btnReinitDocCreeLData = new GridData();
					btnReinitDocCreeLData.grabExcessHorizontalSpace = true;
					btnReinitDocCreeLData.heightHint = 23;
					btnReinitDocCreeLData.horizontalAlignment = GridData.FILL;
					btnReinitDocCree.setLayoutData(btnReinitDocCreeLData);
					btnReinitDocCree.setText("Réinitialiser");
				}
				{
					BtnImprimerDoc = new Button(composite1, SWT.PUSH | SWT.CENTER);
					GridData button1LData1 = new GridData();
					button1LData1.heightHint = 23;
					button1LData1.horizontalAlignment = GridData.FILL;
					BtnImprimerDoc.setLayoutData(button1LData1);
					BtnImprimerDoc.setText("Imprimer");
				}
				{
					GridData cbLData = new GridData();
					cbLData.heightHint = 16;
					cbLData.horizontalAlignment = GridData.FILL;
					getCbReExport = new Button(composite1, SWT.CHECK | SWT.LEFT);
					getCbReExport.setLayoutData(cbLData);
					getCbReExport.setText("Prévisualiser");
					getCbReExport.setSelection(true);
				}
			}
			{
				GridData paBtn1LData = new GridData();
				paBtn1LData.horizontalSpan = 8;
				paBtn1LData.heightHint = 33;
				paBtn1LData.horizontalAlignment = GridData.CENTER;
				paBtn1LData.grabExcessHorizontalSpace = true;
				paBtn1 = new PaBtnReduit(this, SWT.NONE);
				paBtn1.getBtnImprimer().setText("Valider[F3]"); 
				GridData btnFermerLData = new GridData();
				btnFermerLData.widthHint = 102;
				btnFermerLData.heightHint = 23;
				paBtn1.setLayoutData(paBtn1LData);
				paBtn1.getBtnFermer().setLayoutData(btnFermerLData);
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	
	public PaBtnReduit getPaBtn1() {
		return paBtn1;
	}
	

	public Label getLaCODE_TIERS() {
		return laListeCodeTiers;
	}
	

	
	public Label getLaListeCodeTiers() {
		return laCodeTiers;
	}
	
	public Composite getPaSelection() {
		return PaSelection;
	}
	
	public Label getLaCodeTiersSelection() {
		return laCodeTiersSelection;
	}
	
	
	public List getTfListeTiers() {
		return tfListeTiers;
	}
	
//	public Button getBtnRajouter() {
//		return BtnRajouter;
//	}
	
	public Button getBtnReinitialiser() {
		return BtnReinitialiser;
	}
	
//	public Button getBtnChargerFichier() {
//		return BtnChargerFichier;
//	}
	
	public StyledText getTfStyleEditeur() {
		return tfStyleEditeur;
	}

	public List getTfListeDoc() {
		return tfListeDoc;
	}

	public Button getBtnImprimerDoc() {
		return BtnImprimerDoc;
	}

	public Button getBtnRemonterDoc() {
		return BtnRemonterDoc;
	}
	
	public Button getCbReExport() {
		return getCbReExport;
	}
	
	public Button getBtnReinitDocCree() {
		return btnReinitDocCree;
	}
	
	public Text getTfRecherche() {
		return tfRecherche;
	}
	
	public Label getLaLibelleDocument() {
		return laLibelleDocument;
	}
	
	public Text getTfLibelle() {
		return tfLibelle;
	}
	
	public Label getLaDateDocument() {
		return laDateDocument;
	}
	
	public cdatetimeLgr getTfDateDocument() {
		return tfDateDocument;
	}

	public Composite getPaEntete() {
		return PaEntete;
	}
	
	public Label getLaNbWlgr() {
		return laNbWlgr;
	}
	
	public Label getLaSumWlgr() {
		return laSumWlgr;
	}
	
	public Label getLaNbFacture() {
		return laNbFacture;
	}
	
	public Label getLaTotalDoc() {
		return laTotalDoc;
	}
	
	public Button getButtonSelect(Composite parent) {
		return buttonSelect;
	}

	public Button getButtonImporter() {
		return buttonImporter;
	}

	public void setButtonImporter(Button buttonImporter) {
		this.buttonImporter = buttonImporter;
	}

	public Text getTextPassword() {
		return textPassword;
	}

	public void setTextPassword(Text textPassword) {
		this.textPassword = textPassword;
	}

	public Text getTextLogin() {
		return textLogin;
	}

	public void setTextLogin(Text textLogin) {
		this.textLogin = textLogin;
	}

	public Label getLabelMessage() {
		return labelMessage;
	}

	public void setLabelMessage(Label labelMessage) {
		this.labelMessage = labelMessage;
	}
	
	public Button getButtonSelect() {
		return buttonSelect;
	}

	public void setButtonSelect(Button buttonSelect) {
		this.buttonSelect = buttonSelect;
	}

	public Label getLabelTotauxTtc() {
		return labelTotauxTtc;
	}

	public void setLabelTotauxTtc(Label labelTotauxTtc) {
		this.labelTotauxTtc = labelTotauxTtc;
	}

	public Label getLabelTotauxHt() {
		return labelTotauxHt;
	}

	public void setLabelTotauxHt(Label labelTotauxHt) {
		this.labelTotauxHt = labelTotauxHt;
	}

	public Label getSommeTotauxTtc() {
		return sommeTotauxTtc;
	}

	public void setSommeTotauxTtc(Label sommeTotauxTtc) {
		this.sommeTotauxTtc = sommeTotauxTtc;
	}

	public Label getSommeTotuaxHt() {
		return sommeTotuaxHt;
	}

	public void setSommeTotuaxHt(Label sommeTotuaxHt) {
		this.sommeTotuaxHt = sommeTotuaxHt;
	}
	

}

package fr.legrain.document.ecran;

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

import fr.legrain.gestCom.librairiesEcran.LibrairiesEcranPlugin;


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
public class PaIdentiteTiers extends org.eclipse.swt.widgets.Composite {
	private Composite paCorpsFormulaire;
	private Text tfCODE_ENTREPRISE;
	private Text tfCODE_T_CIVILITE;
	private Group groupCommentaire;
	private Text tfADRESSE_WEB;
	private Label laADRESSE_WEB;
	private Text tfADRESSE_EMAIL;
	private Label laADRESSE_EMAIL;
	private Text tfNUMERO_TELEPHONE;
	private Label laNUMERO_TELEPHONE;
	private Group groupContact;
	private Text tfCOMPTE;
	private Label laCOMPTE;
	private Text tfCODE_COMPTA;
	private Label laCodeCompta;
	private Group groupCompta;
	private Text tfSIRET_COMPL;
	private Label laSIRET_COMPL;
	private Text tfTVA_I_COM_COMPL;
	private Label laTVA_I_COM_COMPL;
	private Text tfTYPE_TVA_DOC;
	private Label laTYPE_TVA_DOC;	
	private Text tfPRENOM_TIERS;
	private Label laPRENOM_TIERS;
	private Button btnAfficher;
	private Text tfLIBL_COMMENTAIRE;
	private Button BtnReinitialiser;
	private Text tfNOM_TIERS;
	private Label laNOM_TIERS;
	private Label laCODE_T_CIVILITE;
	private Label laCODE_ENTREPRISE;
	private Text tfEntite;
	private Label laEntite;
	private Group groupIdentite;

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
		PaIdentiteTiers inst = new PaIdentiteTiers(shell, SWT.NULL);
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

	public PaIdentiteTiers(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.numColumns = 7;
			thisLayout.makeColumnsEqualWidth = true;
this.setLayout(thisLayout);
{
	GridData paCorpsFormulaireLData1 = new GridData();
	paCorpsFormulaireLData1.widthHint = 1006;
	paCorpsFormulaireLData1.verticalSpan = 3;
	paCorpsFormulaireLData1.verticalAlignment = GridData.FILL;
	paCorpsFormulaireLData1.grabExcessVerticalSpace = true;
	paCorpsFormulaire = new Composite(this, SWT.NONE);
	GridLayout paCorpsFormulaireLayout = new GridLayout();
	paCorpsFormulaireLayout.numColumns = 5;
	paCorpsFormulaire.setLayout(paCorpsFormulaireLayout);
	paCorpsFormulaire.setLayoutData(paCorpsFormulaireLData1);
	{
		groupIdentite = new Group(paCorpsFormulaire, SWT.NONE );
		GridLayout groupIdentiteLayout = new GridLayout();
		groupIdentiteLayout.numColumns = 2;
		groupIdentite.setLayout(groupIdentiteLayout);
		GridData groupIdentiteLData = new GridData();
		groupIdentiteLData.horizontalSpan = 2;
		groupIdentiteLData.verticalSpan = 3;
		groupIdentiteLData.verticalAlignment = GridData.FILL;
		groupIdentiteLData.horizontalAlignment = GridData.FILL;
		groupIdentite.setLayoutData(groupIdentiteLData);
		groupIdentite.setText("Identité");
		{
			laEntite = new Label(groupIdentite, SWT.NONE);
			laEntite.setText("Type entité");
		}
		{
			GridData tfEntiteLData = new GridData();
			tfEntiteLData.heightHint = 13;
			tfEntiteLData.horizontalAlignment = GridData.END;
			tfEntiteLData.widthHint = 243;
			tfEntite = new Text(groupIdentite,  SWT.BORDER | SWT.READ_ONLY);
			tfEntite.setLayoutData(tfEntiteLData);
		}
		{
			laCODE_ENTREPRISE = new Label(groupIdentite, SWT.NONE);
			laCODE_ENTREPRISE.setText("Entreprise");
		}
		{
			GridData tfCODE_ENTREPRISELData = new GridData();
			tfCODE_ENTREPRISELData.grabExcessHorizontalSpace = true;
			tfCODE_ENTREPRISELData.horizontalAlignment = GridData.FILL;
			tfCODE_ENTREPRISE = new Text(groupIdentite,  SWT.BORDER | SWT.READ_ONLY);
			tfCODE_ENTREPRISE.setLayoutData(tfCODE_ENTREPRISELData);
		}
		{
			laCODE_T_CIVILITE = new Label(groupIdentite, SWT.NONE);
			laCODE_T_CIVILITE.setText("Civilité");
		}
		{
			GridData tfCODE_T_CIVILITELData = new GridData();
			tfCODE_T_CIVILITELData.horizontalAlignment = GridData.FILL;
			tfCODE_T_CIVILITE = new Text(groupIdentite,  SWT.BORDER | SWT.READ_ONLY);
			tfCODE_T_CIVILITE.setLayoutData(tfCODE_T_CIVILITELData);
		}
		{
			laNOM_TIERS = new Label(groupIdentite, SWT.NONE);
			laNOM_TIERS.setText("Nom");
		}
		{
			GridData tfNOM_TIERSLData = new GridData();
			tfNOM_TIERSLData.horizontalAlignment = GridData.FILL;
			tfNOM_TIERS = new Text(groupIdentite,  SWT.BORDER | SWT.READ_ONLY);
			tfNOM_TIERS.setLayoutData(tfNOM_TIERSLData);
		}
		{
			laPRENOM_TIERS = new Label(groupIdentite, SWT.NONE);
			laPRENOM_TIERS.setText("Prénom");
		}
		{
			GridData tfPRENOM_TIERSLData = new GridData();
			tfPRENOM_TIERSLData.horizontalAlignment = GridData.FILL;
			tfPRENOM_TIERS = new Text(groupIdentite, SWT.BORDER | SWT.READ_ONLY);
			tfPRENOM_TIERS.setLayoutData(tfPRENOM_TIERSLData);
		}
		{
			laTVA_I_COM_COMPL = new Label(groupIdentite, SWT.NONE);
			laTVA_I_COM_COMPL.setText("TVA intracommunautaire");
		}
		{
			GridData tfTVA_I_COM_COMPLLData = new GridData();
			tfTVA_I_COM_COMPLLData.horizontalAlignment = GridData.FILL;
			tfTVA_I_COM_COMPL = new Text(groupIdentite,  SWT.BORDER | SWT.READ_ONLY);
			tfTVA_I_COM_COMPL.setLayoutData(tfTVA_I_COM_COMPLLData);
		}
		{
			laSIRET_COMPL = new Label(groupIdentite, SWT.NONE);
			laSIRET_COMPL.setText("N°siret");
		}
		{
			GridData tfSIRET_COMPLLData = new GridData();
			tfSIRET_COMPLLData.horizontalAlignment = GridData.FILL;
			tfSIRET_COMPL = new Text(groupIdentite,  SWT.BORDER | SWT.READ_ONLY);
			tfSIRET_COMPL.setLayoutData(tfSIRET_COMPLLData);
		}
	}
	{
		groupContact = new Group(paCorpsFormulaire, SWT.NONE);
		GridLayout groupContactLayout = new GridLayout();
		groupContactLayout.numColumns = 2;
		groupContact.setLayout(groupContactLayout);
		GridData groupContactLData = new GridData();
		groupContactLData.horizontalSpan = 2;
		groupContactLData.verticalSpan = 2;
		groupContactLData.horizontalAlignment = GridData.FILL;
		groupContactLData.verticalAlignment = GridData.FILL;
		groupContact.setLayoutData(groupContactLData);
		groupContact.setText("Contact");
		{
			laNUMERO_TELEPHONE = new Label(groupContact, SWT.NONE);
			laNUMERO_TELEPHONE.setText("Téléphone");
		}
		{
			GridData tfNUMERO_TELEPHONELData = new GridData();
			tfNUMERO_TELEPHONELData.heightHint = 13;
			tfNUMERO_TELEPHONELData.horizontalAlignment = GridData.END;
			tfNUMERO_TELEPHONELData.widthHint = 183;
			tfNUMERO_TELEPHONE = new Text(groupContact,  SWT.BORDER | SWT.READ_ONLY);
			tfNUMERO_TELEPHONE.setLayoutData(tfNUMERO_TELEPHONELData);
		}
		{
			laADRESSE_EMAIL = new Label(groupContact, SWT.NONE);
			laADRESSE_EMAIL.setText("Adresse Email");
		}
		{
			GridData tfADRESSE_EMAILLData = new GridData();
			tfADRESSE_EMAILLData.horizontalAlignment = GridData.FILL;
			tfADRESSE_EMAIL = new Text(groupContact,  SWT.BORDER | SWT.READ_ONLY);
			tfADRESSE_EMAIL.setLayoutData(tfADRESSE_EMAILLData);
		}
		{
			laADRESSE_WEB = new Label(groupContact, SWT.NONE);
			laADRESSE_WEB.setText("Adresse Web");
		}
		{
			GridData tfADRESSE_WEBLData = new GridData();
			tfADRESSE_WEBLData.horizontalAlignment = GridData.FILL;
			tfADRESSE_WEB = new Text(groupContact,  SWT.BORDER | SWT.READ_ONLY);
			tfADRESSE_WEB.setLayoutData(tfADRESSE_WEBLData);
		}
	}
	{
		groupCommentaire = new Group(paCorpsFormulaire, SWT.NONE);
		GridLayout groupCommentaireLayout = new GridLayout();
		groupCommentaireLayout.numColumns = 4;
		groupCommentaire.setLayout(groupCommentaireLayout);
		GridData groupCommentaireLData = new GridData();
		groupCommentaireLData.verticalAlignment = GridData.FILL;
		groupCommentaireLData.verticalSpan = 3;
		groupCommentaireLData.horizontalAlignment = GridData.FILL;
		groupCommentaireLData.grabExcessHorizontalSpace = true;
		groupCommentaire.setLayoutData(groupCommentaireLData);
		groupCommentaire.setText("Commentaire");
		{
			GridData tfCommentaireLData = new GridData();
			tfCommentaireLData.verticalSpan = 3;
			tfCommentaireLData.verticalAlignment = GridData.FILL;
			tfCommentaireLData.grabExcessHorizontalSpace = true;
			tfCommentaireLData.horizontalAlignment = GridData.FILL;
			tfCommentaireLData.grabExcessVerticalSpace = true;
			tfLIBL_COMMENTAIRE = new Text(groupCommentaire, SWT.MULTI | SWT.WRAP | SWT.H_SCROLL| SWT.V_SCROLL| SWT.BORDER | SWT.READ_ONLY);
			tfLIBL_COMMENTAIRE.setLayoutData(tfCommentaireLData);
		}
	}
	{
		groupCompta = new Group(paCorpsFormulaire, SWT.NONE);
		GridLayout groupComptaLayout = new GridLayout();
		groupComptaLayout.numColumns = 2;
		groupCompta.setLayout(groupComptaLayout);
		GridData groupComptaLData = new GridData();
		groupComptaLData.horizontalSpan = 2;
		groupComptaLData.verticalAlignment = GridData.FILL;
		groupComptaLData.horizontalAlignment = GridData.FILL;
		groupCompta.setLayoutData(groupComptaLData);
		groupCompta.setText("Comptabilité");
		{
			laCodeCompta = new Label(groupCompta, SWT.NONE);
			laCodeCompta.setText("Code compta");
		}
		{
			GridData tfCODE_COMPTALData = new GridData();
			tfCODE_COMPTALData.widthHint = 170;
			tfCODE_COMPTALData.heightHint = 13;
			tfCODE_COMPTALData.horizontalAlignment = GridData.END;
			tfCODE_COMPTA = new Text(groupCompta,  SWT.BORDER | SWT.READ_ONLY);
			tfCODE_COMPTA.setLayoutData(tfCODE_COMPTALData);
		}
		{
			laCOMPTE = new Label(groupCompta, SWT.NONE);
			laCOMPTE.setText("Compte rattaché");
		}
		{
			GridData tfCOMPTELData = new GridData();
			tfCOMPTELData.horizontalAlignment = GridData.FILL;
			tfCOMPTE = new Text(groupCompta,  SWT.BORDER | SWT.READ_ONLY);
			tfCOMPTE.setLayoutData(tfCOMPTELData);
		}
		{
			laTYPE_TVA_DOC = new Label(groupCompta, SWT.NONE);
			laTYPE_TVA_DOC.setText("Type de tva document");
		}
		{
			GridData tfTYPE_TVA_DOCLData = new GridData();
			tfTYPE_TVA_DOCLData.horizontalAlignment = GridData.FILL;
			tfTYPE_TVA_DOC = new Text(groupCompta,  SWT.BORDER | SWT.READ_ONLY);
			tfTYPE_TVA_DOC.setLayoutData(tfTYPE_TVA_DOCLData);
		}
	}
	{
		BtnReinitialiser = new Button(paCorpsFormulaire, SWT.PUSH | SWT.CENTER);
		GridData BtnReinitialiserLData = new GridData();
		BtnReinitialiserLData.widthHint = 125;
		BtnReinitialiserLData.verticalAlignment = GridData.FILL;
		BtnReinitialiser.setLayoutData(BtnReinitialiserLData);
		BtnReinitialiser.setText("Rafraîchir");
		BtnReinitialiser.setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_REINITIALISER));
		BtnReinitialiser.setSize(125, 23);
	}
	{
		btnAfficher = new Button(paCorpsFormulaire, SWT.PUSH | SWT.CENTER);
		GridData button1LData = new GridData();
		button1LData.widthHint = 125;
		button1LData.verticalAlignment = GridData.FILL;
		btnAfficher.setLayoutData(button1LData);
		btnAfficher.setText("Fiche tiers");
		btnAfficher.setImage(LibrairiesEcranPlugin.ir.get(LibrairiesEcranPlugin.IMAGE_IDENTITE_TIERS));
		btnAfficher.setSize(125, 23);
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

	public Text getTfCODE_ENTREPRISE() {
		return tfCODE_ENTREPRISE;
	}

	public Text getTfCODE_T_CIVILITE() {
		return tfCODE_T_CIVILITE;
	}

	public Text getTfLIBL_COMMENTAIRE() {
		return tfLIBL_COMMENTAIRE;
	}

	public Group getGroupCommentaire() {
		return groupCommentaire;
	}

	public Text getTfADRESSE_WEB() {
		return tfADRESSE_WEB;
	}

	public Label getLaADRESSE_WEB() {
		return laADRESSE_WEB;
	}

	public Text getTfADRESSE_EMAIL() {
		return tfADRESSE_EMAIL;
	}

	public Label getLaADRESSE_EMAIL() {
		return laADRESSE_EMAIL;
	}

	public Text getTfNUMERO_TELEPHONE() {
		return tfNUMERO_TELEPHONE;
	}

	public Label getLaNUMERO_TELEPHONE() {
		return laNUMERO_TELEPHONE;
	}

	public Group getGroupContact() {
		return groupContact;
	}

	public Text getTfCOMPTE() {
		return tfCOMPTE;
	}

	public Label getLaCOMPTE() {
		return laCOMPTE;
	}

	public Text getTfCODE_COMPTA() {
		return tfCODE_COMPTA;
	}

	public Label getLaCodeCompta() {
		return laCodeCompta;
	}

	public Group getGroupCompta() {
		return groupCompta;
	}

	public Text getTfSIRET_COMPL() {
		return tfSIRET_COMPL;
	}

	public Label getLaSIRET_COMPL() {
		return laSIRET_COMPL;
	}

	public Text getTfTVA_I_COM_COMPL() {
		return tfTVA_I_COM_COMPL;
	}

	public Label getLaTVA_I_COM_COMPL() {
		return laTVA_I_COM_COMPL;
	}

	public Text getTfPRENOM_TIERS() {
		return tfPRENOM_TIERS;
	}

	public Label getLaPRENOM_TIERS() {
		return laPRENOM_TIERS;
	}

	public Button getBtnReinitialiser() {
		return BtnReinitialiser;
	}

	public Text getTfNOM_TIERS() {
		return tfNOM_TIERS;
	}

	public Label getLaNOM_TIERS() {
		return laNOM_TIERS;
	}

	public Label getLaCODE_T_CIVILITE() {
		return laCODE_T_CIVILITE;
	}

	public Label getLaCODE_ENTREPRISE() {
		return laCODE_ENTREPRISE;
	}

	public Text getTfEntite() {
		return tfEntite;
	}

	public Label getLaEntite() {
		return laEntite;
	}

	public Group getGroupIdentite() {
		return groupIdentite;
	}

	public Text getTfTYPE_TVA_DOC() {
		return tfTYPE_TVA_DOC;
	}

	public Label getLaTYPE_TVA_DOC() {
		return laTYPE_TVA_DOC;
	}

	public Button getBtnAfficher() {
		return btnAfficher;
	}
	


}

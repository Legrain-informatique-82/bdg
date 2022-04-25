package fr.legrain.tiers.ecran;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.fieldassist.TextControlCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
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
public class PaTiersInfosEntrepriseSWT extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	private Composite paCorpsFormulaire;
	private Label laCodeCompta;
	private Label laACTIF_TIERS;
	private Label laCODE_T_TIERS;
	private Label laEntite;
	private Label laCOMPTE;
	private Label laCODE_ENTREPRISE;
	private Label laCODE_T_CIVILITE;
	private Label laTTC;
	private Label laSURNOM_TIERS;

	private Label laPRENOM_TIERS;
	private Label laNOM_TIERS;
	private Label laCODE_TIERS;
	private Label laADRESSE1_ADRESSE;
	private Label laADRESSE2_ADRESSE;
	private Label laADRESSE3_ADRESSE;
	private Label laCODEPOSTAL_ADRESSE;
	private Label laVILLE_ADRESSE;
	private Label laPAYS_ADRESSE;
	private Label laADRESSE_EMAIL;
	private Label laNUMERO_TELEPHONE;
	private Label laADRESSE_WEB;
	private Label laTVA_I_COM_COMPL;
	private Label laAccise;
	private Label laIcs;
	private Label laCODE_C_PAIEMENT;
	private Label laCODE_T_PAIEMENT;
	private Label laCODE_T_TVA_DOC;
	private Label laCODE_T_TARIF;
	private Group groupCompta;
	private Group groupContact;
	private Group groupAdresse;
	private Group groupIdentite;
//	private Composite composite3;
	private Composite composite2;
//	private Button btnAjoutAdr;
//	private Button btnAjoutTelephone;
//	private Button btnAjoutEmail;
//	private Button btnAjoutSiteWeb;
//	private Button btnCompl;
//	private Button btnConditionPaiement;
//	private Button btnAjoutCommercial;
	private ScrolledComposite scrolledCompositePublipostage;
	private Composite compositeCheckBoxPubli;
	private Button btnPublipostage;
	private Button btnOuvreRepertoireCourrier;
	private Button btnFusionPublipostage;
	
	private Button tfTTC;
	private Button cbACTIF_TIERS;
	private Text tfLIBL_COMMENTAIRE;
	private Text tfCODE_T_TIERS;
	private Text tfCODE_ENTREPRISE;
	private Text tfCODE_T_CIVILITE;
	private Text tfSURNOM_TIERS;
	private Text tfPRENOM_TIERS;
	private Text tfNOM_TIERS;
	private Text tfCODE_TIERS;
	private Text tfEntite;
	private Text tfCODE_COMPTA;
	private Text tfCOMPTE;
	private Text tfADRESSE1_ADRESSE;
	private Text tfADRESSE2_ADRESSE;
	private Group groupCommentaire;
	private Group groupPublipostage;
	private Group groupParam;
	private Text tfADRESSE3_ADRESSE;
	private Text tfCODEPOSTAL_ADRESSE;
	private Text tfVILLE_ADRESSE;
	private Text tfPAYS_ADRESSE;	
	private Text tfADRESSE_EMAIL;
	private Text tfNUMERO_TELEPHONE;
	private Text tfADRESSE_WEB;
	private Text tfTVA_I_COM_COMPL;
	private Text tfAccise;
	private Text tfIcs;
	private Text tfCODE_C_PAIEMENT;
	private Text tfCODE_T_PAIEMENT;
	private Text tfCODE_T_TVA_DOC;
	private Text tfCODE_T_TARIF;
	
	private DecoratedField fieldLIBL_COMMENTAIRE;
	private DecoratedField fieldCODE_BANQUE;
	private DecoratedField fieldCODE_T_TIERS;
	private DecoratedField fieldCODE_ENTREPRISE;
	private DecoratedField fieldCODE_T_CIVILITE;
	private DecoratedField fieldSURNOM_TIERS;
	private DecoratedField fieldPRENOM_TIERS;
	private DecoratedField fieldNOM_TIERS;
	private DecoratedField fieldCODE_TIERS;
	private DecoratedField fieldEntite;
	private DecoratedField fieldCODE_COMPTA;
	private DecoratedField fieldCOMPTE;
	private DecoratedField fieldTTC;
	private DecoratedField fieldACTIF_TIERS;
	private DecoratedField fieldADRESSE1_ADRESSE;
    private DecoratedField fieldADRESSE2_ADRESSE;
	private DecoratedField fieldADRESSE3_ADRESSE;	
	private DecoratedField fieldCODEPOSTAL_ADRESSE;
	private DecoratedField fieldVILLE_ADRESSE;
	private DecoratedField fieldPAYS_ADRESSE;
	private DecoratedField fieldADRESSE_EMAIL;
	private DecoratedField fieldNUMERO_TELEPHONE;
	private DecoratedField fieldADRESSE_WEB;
	private DecoratedField fieldTVA_I_COM_COMPL;
    private DecoratedField fieldAccise;
    private DecoratedField fieldIcs;
    private DecoratedField fieldCODE_C_PAIEMENT;
    private DecoratedField fieldCODE_T_PAIEMENT;
    private DecoratedField fieldCODE_T_TVA_DOC;
    private DecoratedField fieldCODE_T_TARIF;
	
//	private JScrollPane JScrollPane1;
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
		PaTiersInfosEntrepriseSWT inst = new PaTiersInfosEntrepriseSWT(shell, SWT.NULL);
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

	public PaTiersInfosEntrepriseSWT(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style, fr.legrain.lib.gui.DefaultFrameFormulaireSWT.ORDRE_GRILLE_FORMULAIRE);
		initGUI();
	}

	private void initGUI() {
		try {
			{
				paCorpsFormulaire = new Composite(super.getPaFomulaire(), SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 3;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.grabExcessVerticalSpace = true;
				paCorpsFormulaire.setLayoutData(composite1LData);
				paCorpsFormulaire.setLayout(composite1Layout);

				{
					groupIdentite = new Group(paCorpsFormulaire, SWT.NONE);
					GridLayout groupIdentiteLayout = new GridLayout();
					groupIdentiteLayout.makeColumnsEqualWidth = true;
					groupIdentiteLayout.numColumns = 2;
					groupIdentite.setLayout(groupIdentiteLayout);
					GridData groupIdentiteLData = new GridData();
					groupIdentiteLData.horizontalAlignment = GridData.FILL;
					groupIdentiteLData.grabExcessHorizontalSpace = true;
					groupIdentiteLData.verticalSpan = 2;
					groupIdentiteLData.verticalAlignment = GridData.FILL;
					groupIdentite.setLayoutData(groupIdentiteLData);
					groupIdentite.setText("Identité");
					{
						laCODE_TIERS = new Label(groupIdentite, SWT.NONE);
						laCODE_TIERS.setText("Code");
					}
					{
						GridData tfCODE_TIERSLData = new GridData();
						//tfCODE_TIERSLData.horizontalAlignment = GridData.FILL;
						if(!decore) {
							tfCODE_TIERS = new Text(groupIdentite, SWT.BORDER);
							tfCODE_TIERS.setLayoutData(tfCODE_TIERSLData);
						} else {					
						fieldCODE_TIERS = new DecoratedField(groupIdentite, SWT.BORDER, new TextControlCreator());
						tfCODE_TIERS = (Text)fieldCODE_TIERS.getControl();
						fieldCODE_TIERS.getLayoutControl().setLayoutData(tfCODE_TIERSLData);
						}
					}
					{
						laEntite = new Label(groupIdentite, SWT.NONE);
						laEntite.setText("Type entité");
					}
					{
						GridData tfEntiteLData = new GridData();
						tfEntiteLData.horizontalAlignment = GridData.FILL;
						if(!decore) {
							tfEntite = new Text(groupIdentite, SWT.BORDER);
							tfEntite.setLayoutData(tfEntiteLData);
						} else {					
						fieldEntite = new DecoratedField(groupIdentite, SWT.BORDER, new TextControlCreator());
						tfEntite = (Text)fieldEntite.getControl();
						fieldEntite.getLayoutControl().setLayoutData(tfEntiteLData);
						}
					}
					{
						laCODE_T_TIERS = new Label(groupIdentite, SWT.NONE);
						laCODE_T_TIERS.setText("Type");
					}
					{
						GridData tfCODE_T_TIERSLData = new GridData();
						tfCODE_T_TIERSLData.horizontalAlignment = GridData.FILL;
						if(!decore) {
							tfCODE_T_TIERS = new Text(groupIdentite, SWT.BORDER);
							tfCODE_T_TIERS.setLayoutData(tfCODE_T_TIERSLData);
						} else {					
						fieldCODE_T_TIERS = new DecoratedField(groupIdentite, SWT.BORDER, new TextControlCreator());
						tfCODE_T_TIERS = (Text)fieldCODE_T_TIERS.getControl();
						fieldCODE_T_TIERS.getLayoutControl().setLayoutData(tfCODE_T_TIERSLData);
						}
					}
					{
						laCODE_ENTREPRISE = new Label(groupIdentite, SWT.NONE);
						laCODE_ENTREPRISE.setText("Entreprise");
					}
					{
						GridData tfCODE_ENTREPRISELData = new GridData();
						tfCODE_ENTREPRISELData.horizontalAlignment = GridData.FILL;
						if(!decore) {
							tfCODE_ENTREPRISE = new Text(groupIdentite, SWT.BORDER);
							tfCODE_ENTREPRISE.setLayoutData(tfCODE_ENTREPRISELData);
						} else {					
						fieldCODE_ENTREPRISE = new DecoratedField(groupIdentite, SWT.BORDER, new TextControlCreator());
						tfCODE_ENTREPRISE = (Text)fieldCODE_ENTREPRISE.getControl();
						fieldCODE_ENTREPRISE.getLayoutControl().setLayoutData(tfCODE_ENTREPRISELData);
						}
					}
					{
						laCODE_T_CIVILITE = new Label(groupIdentite, SWT.NONE);
						laCODE_T_CIVILITE.setText("Civilité");
					}
					{
						GridData tfCODE_T_CIVILITELData = new GridData();
						tfCODE_T_CIVILITELData.horizontalAlignment = GridData.FILL;
						if(!decore) {
							tfCODE_T_CIVILITE = new Text(groupIdentite, SWT.BORDER);
							tfCODE_T_CIVILITE.setLayoutData(tfCODE_T_CIVILITELData);
						} else {					
						fieldCODE_T_CIVILITE = new DecoratedField(groupIdentite, SWT.BORDER, new TextControlCreator());
						tfCODE_T_CIVILITE = (Text)fieldCODE_T_CIVILITE.getControl();
						fieldCODE_T_CIVILITE.getLayoutControl().setLayoutData(tfCODE_T_CIVILITELData);
						}
					}
					
					{
						laNOM_TIERS = new Label(groupIdentite, SWT.NONE);
						laNOM_TIERS.setText("Nom");
					}
					{
						GridData tfNOM_TIERSLData = new GridData();
						tfNOM_TIERSLData.horizontalAlignment = GridData.FILL;
						if(!decore) {
							tfNOM_TIERS = new Text(groupIdentite, SWT.BORDER);
							tfNOM_TIERS.setLayoutData(tfNOM_TIERSLData);
						} else {					
						fieldNOM_TIERS = new DecoratedField(groupIdentite, SWT.BORDER, new TextControlCreator());
						tfNOM_TIERS = (Text)fieldNOM_TIERS.getControl();
						fieldNOM_TIERS.getLayoutControl().setLayoutData(tfNOM_TIERSLData);
						}
					}
					{
						laPRENOM_TIERS = new Label(groupIdentite, SWT.NONE);
						laPRENOM_TIERS.setText("Prénom");
					}
					{
						GridData tfPRENOM_TIERSLData = new GridData();
						tfPRENOM_TIERSLData.horizontalAlignment = GridData.FILL;
						if(!decore) {
							tfPRENOM_TIERS = new Text(groupIdentite, SWT.BORDER);
							tfPRENOM_TIERS.setLayoutData(tfPRENOM_TIERSLData);
						} else {					
						fieldPRENOM_TIERS = new DecoratedField(groupIdentite, SWT.BORDER, new TextControlCreator());
						tfPRENOM_TIERS = (Text)fieldPRENOM_TIERS.getControl();
						fieldPRENOM_TIERS.getLayoutControl().setLayoutData(tfPRENOM_TIERSLData);
						}
					}
					{
						laTVA_I_COM_COMPL = new Label(groupIdentite, SWT.NONE);
						laTVA_I_COM_COMPL.setText("TVA intracommunautaire");
					}
					{
						GridData tfNUMERO_TELEPHONELData = new GridData();
						tfNUMERO_TELEPHONELData.horizontalAlignment = GridData.CENTER;
						tfNUMERO_TELEPHONELData.widthHint = 156;
						tfNUMERO_TELEPHONELData.verticalAlignment = GridData.FILL;
						if(!decore) {
							tfTVA_I_COM_COMPL = new Text(groupIdentite, SWT.BORDER);
							tfTVA_I_COM_COMPL.setLayoutData(tfNUMERO_TELEPHONELData);
						} else {					
						fieldTVA_I_COM_COMPL = new DecoratedField(groupIdentite, SWT.BORDER, new TextControlCreator());
						tfTVA_I_COM_COMPL = (Text)fieldTVA_I_COM_COMPL.getControl();
						fieldTVA_I_COM_COMPL.getLayoutControl().setLayoutData(tfNUMERO_TELEPHONELData);
						}
					}
					{
						laAccise = new Label(groupIdentite, SWT.NONE);
						laAccise.setText("N°Accise");
					}
					{
						GridData tfPOSTE_TELEPHONELData = new GridData();
						tfPOSTE_TELEPHONELData.horizontalAlignment = GridData.FILL;
						if(!decore) {
							tfAccise = new Text(groupIdentite, SWT.BORDER);
							tfAccise.setLayoutData(tfPOSTE_TELEPHONELData);
						} else {					
						fieldAccise = new DecoratedField(groupIdentite, SWT.BORDER, new TextControlCreator());
						tfAccise = (Text)fieldAccise.getControl();
						fieldAccise.getLayoutControl().setLayoutData(tfPOSTE_TELEPHONELData);
						}
					}
					{
						laIcs = new Label(groupIdentite, SWT.NONE);
						laIcs.setText("N° ICS");
					}
					{
						GridData tfPOSTE_TELEPHONELData = new GridData();
						tfPOSTE_TELEPHONELData.horizontalAlignment = GridData.FILL;
						if(!decore) {
							tfIcs = new Text(groupIdentite, SWT.BORDER);
							tfIcs.setLayoutData(tfPOSTE_TELEPHONELData);
						} else {					
						fieldIcs = new DecoratedField(groupIdentite, SWT.BORDER, new TextControlCreator());
						tfIcs = (Text)fieldIcs.getControl();
						fieldIcs.getLayoutControl().setLayoutData(tfPOSTE_TELEPHONELData);
						}
					}
				}
				{
					groupAdresse = new Group(paCorpsFormulaire, SWT.NONE);
					GridLayout groupAdresseLayout = new GridLayout();
					groupAdresseLayout.makeColumnsEqualWidth = true;
					groupAdresseLayout.numColumns = 2;
					groupAdresse.setLayout(groupAdresseLayout);
					GridData groupAdresseLData = new GridData();
					groupAdresseLData.horizontalAlignment = GridData.FILL;
					groupAdresseLData.grabExcessHorizontalSpace = true;
					groupAdresse.setLayoutData(groupAdresseLData);
					groupAdresse.setText("Adresse");
					{
						laADRESSE1_ADRESSE = new Label(groupAdresse, SWT.NONE);
						laADRESSE1_ADRESSE.setText("Ligne");
					}
					{
						GridData tfADRESSE1_ADRESSELData = new GridData();
						tfADRESSE1_ADRESSELData.horizontalAlignment = GridData.CENTER;
						tfADRESSE1_ADRESSELData.widthHint = 156;
						tfADRESSE1_ADRESSELData.verticalAlignment = GridData.FILL;
						if(!decore) {
							tfADRESSE1_ADRESSE = new Text(groupAdresse, SWT.BORDER);
							tfADRESSE1_ADRESSE.setLayoutData(tfADRESSE1_ADRESSELData);
						} else {
							fieldADRESSE1_ADRESSE = new DecoratedField(groupAdresse, SWT.BORDER, new TextControlCreator());
							tfADRESSE1_ADRESSE = (Text)fieldADRESSE1_ADRESSE.getControl();
							fieldADRESSE1_ADRESSE.getLayoutControl().setLayoutData(tfADRESSE1_ADRESSELData);
						}					
					}
					{
						laADRESSE2_ADRESSE = new Label(groupAdresse, SWT.NONE);
						laADRESSE2_ADRESSE.setText("Ligne (2)");
					}
					{
						GridData tfADRESSE2_ADRESSELData = new GridData();
						tfADRESSE2_ADRESSELData.horizontalAlignment = GridData.FILL;
						if(!decore) {
							tfADRESSE2_ADRESSE = new Text(groupAdresse, SWT.BORDER);
							tfADRESSE2_ADRESSE.setLayoutData(tfADRESSE2_ADRESSELData);
						} else {					
						fieldADRESSE2_ADRESSE = new DecoratedField(groupAdresse, SWT.BORDER, new TextControlCreator());
						tfADRESSE2_ADRESSE = (Text)fieldADRESSE2_ADRESSE.getControl();
						fieldADRESSE2_ADRESSE.getLayoutControl().setLayoutData(tfADRESSE2_ADRESSELData);
						}
					}
					{
						laADRESSE3_ADRESSE = new Label(groupAdresse, SWT.NONE);
						laADRESSE3_ADRESSE.setText("Ligne (3)");
					}
					{
						GridData tfADRESSE3_ADRESSELData = new GridData();
						tfADRESSE3_ADRESSELData.horizontalAlignment = GridData.FILL;
						if(!decore) {
							tfADRESSE3_ADRESSE = new Text(groupAdresse, SWT.BORDER);
							tfADRESSE3_ADRESSE.setLayoutData(tfADRESSE3_ADRESSELData);
						} else {					
						fieldADRESSE3_ADRESSE = new DecoratedField(groupAdresse, SWT.BORDER, new TextControlCreator());
						tfADRESSE3_ADRESSE = (Text)fieldADRESSE3_ADRESSE.getControl();
						fieldADRESSE3_ADRESSE.getLayoutControl().setLayoutData(tfADRESSE3_ADRESSELData);
						}
					}
				{
					laCODEPOSTAL_ADRESSE = new Label(groupAdresse, SWT.NONE);
					laCODEPOSTAL_ADRESSE.setText("Code postal");
				}
				{
					GridData tfCODEPOSTAL_ADRESSELData = new GridData();
					tfCODEPOSTAL_ADRESSELData.horizontalAlignment = GridData.CENTER;
					if(!decore) {
						tfCODEPOSTAL_ADRESSE = new Text(groupAdresse, SWT.BORDER);
						//tfCODEPOSTAL_ADRESSE.setLayoutData(tfCODEPOSTAL_ADRESSELData);
					} else {					
					fieldCODEPOSTAL_ADRESSE = new DecoratedField(groupAdresse, SWT.BORDER, new TextControlCreator());
					tfCODEPOSTAL_ADRESSE = (Text)fieldCODEPOSTAL_ADRESSE.getControl();
					//fieldCODEPOSTAL_ADRESSE.getLayoutControl().setLayoutData(tfCODEPOSTAL_ADRESSELData);
					}
				}
				{
					laVILLE_ADRESSE = new Label(groupAdresse, SWT.NONE);
					GridData laVILLE_ADRESSELData = new GridData();
					laVILLE_ADRESSELData.widthHint = 60;
					laVILLE_ADRESSELData.heightHint = 13;
					laVILLE_ADRESSE.setLayoutData(laVILLE_ADRESSELData);
					laVILLE_ADRESSE.setText("Ville");
				}
				{
					GridData tfVILLE_ADRESSELData = new GridData();
					tfVILLE_ADRESSELData.horizontalAlignment = GridData.FILL;
					if(!decore) {
						tfVILLE_ADRESSE = new Text(groupAdresse, SWT.BORDER);
						tfVILLE_ADRESSE.setLayoutData(tfVILLE_ADRESSELData);
					} else {					
					fieldVILLE_ADRESSE = new DecoratedField(groupAdresse, SWT.BORDER, new TextControlCreator());
					tfVILLE_ADRESSE = (Text)fieldVILLE_ADRESSE.getControl();
					fieldVILLE_ADRESSE.getLayoutControl().setLayoutData(tfVILLE_ADRESSELData);
					}
				}
				{
					laPAYS_ADRESSE = new Label(groupAdresse, SWT.NONE);
					laPAYS_ADRESSE.setText("Pays");
				}
				{
					GridData tfPAYS_ADRESSELData = new GridData();
					tfPAYS_ADRESSELData.horizontalAlignment = GridData.FILL;
					if(!decore) {
						tfPAYS_ADRESSE = new Text(groupAdresse, SWT.BORDER);
						tfPAYS_ADRESSE.setLayoutData(tfPAYS_ADRESSELData);
					} else {					
					fieldPAYS_ADRESSE = new DecoratedField(groupAdresse, SWT.BORDER, new TextControlCreator());
					tfPAYS_ADRESSE = (Text)fieldPAYS_ADRESSE.getControl();
					fieldPAYS_ADRESSE.getLayoutControl().setLayoutData(tfPAYS_ADRESSELData);
					}
				}
				}
				{
					groupParam = new Group(paCorpsFormulaire, SWT.NONE);
					GridLayout groupParamLayout = new GridLayout();
					groupParamLayout.makeColumnsEqualWidth = true;
					groupParamLayout.numColumns = 2;
					groupParam.setLayout(groupParamLayout);
					GridData groupParamLData = new GridData();
					groupParamLData.verticalAlignment = GridData.FILL;
					groupParamLData.horizontalAlignment = GridData.FILL;
					groupParamLData.grabExcessHorizontalSpace = true;
					groupParam.setLayoutData(groupParamLData);
					groupParam.setText("Paramètres");
				

					{
						composite2 = new Composite(groupParam, SWT.NONE);
						GridLayout composite2Layout = new GridLayout();
						composite2Layout.numColumns = 4;
						composite2Layout.makeColumnsEqualWidth = true;
						GridData composite2LData = new GridData();
						composite2LData.grabExcessHorizontalSpace = true;
						composite2LData.widthHint = 230;
						composite2LData.verticalAlignment = GridData.FILL;
						composite2LData.horizontalSpan = 2;
						composite2.setLayoutData(composite2LData);
						composite2.setLayout(composite2Layout);
						{
							laACTIF_TIERS = new Label(composite2, SWT.NONE);
							laACTIF_TIERS.setText("Actif");
						}
						{
							GridData cbACTIF_TIERSLData = new GridData();
							cbACTIF_TIERSLData.widthHint = 22;
							cbACTIF_TIERSLData.heightHint = 16;
							if(!decore) {
								cbACTIF_TIERS = new Button(composite2, SWT.CHECK | SWT.LEFT);
								cbACTIF_TIERS.setLayoutData(cbACTIF_TIERSLData);
							} else {					
								fieldACTIF_TIERS = new DecoratedField(composite2, SWT.CHECK | SWT.LEFT, new ButtonControlCreator());
								cbACTIF_TIERS = (Button)fieldACTIF_TIERS.getControl();
								fieldACTIF_TIERS.getLayoutControl().setLayoutData(cbACTIF_TIERSLData);
							}
						}
						{
							laTTC = new Label(composite2, SWT.NONE);
							laTTC.setText("Saisie TTC");
						}
						{
							GridData tfTTCLData = new GridData();
							tfTTCLData.widthHint = 22;
							tfTTCLData.heightHint = 16;
							if(!decore) {
								tfTTC = new Button(composite2, SWT.CHECK | SWT.LEFT);
								tfTTC.setLayoutData(tfTTCLData);
							} else {					
								fieldTTC = new DecoratedField(composite2, SWT.CHECK | SWT.LEFT, new ButtonControlCreator());
								tfTTC = (Button)fieldTTC.getControl();
								fieldTTC.getLayoutControl().setLayoutData(tfTTCLData);
							}
						}
//						composite2
//						.setTabList(new org.eclipse.swt.widgets.Control[] {
//						cbACTIF_TIERS, tfTTC });
					}
					
					{
						laCODE_C_PAIEMENT = new Label(groupParam, SWT.NONE);
						laCODE_C_PAIEMENT.setText("Condition de paiement");
					}
					
					{
						GridData tfCODE_C_PAIEMENTLData = new GridData();
						tfCODE_C_PAIEMENTLData.widthHint = 79;
						if(!decore) {
							tfCODE_C_PAIEMENT = new Text(groupParam, SWT.BORDER);
							tfCODE_C_PAIEMENT.setLayoutData(tfCODE_C_PAIEMENTLData);
						} else {					
						fieldCODE_C_PAIEMENT = new DecoratedField(groupParam, SWT.BORDER, new TextControlCreator());
						tfCODE_C_PAIEMENT = (Text)fieldCODE_C_PAIEMENT.getControl();
						fieldCODE_C_PAIEMENT.getLayoutControl().setLayoutData(tfCODE_C_PAIEMENTLData);
						}
					}
					{
						laCODE_T_PAIEMENT = new Label(groupParam, SWT.NONE);
						laCODE_T_PAIEMENT.setText("Type de paiement");
					}
					
					{
						GridData tfCODE_T_PAIEMENTLData = new GridData();
						tfCODE_T_PAIEMENTLData.widthHint = 79;
						if(!decore) {
							tfCODE_T_PAIEMENT = new Text(groupParam, SWT.BORDER);
							tfCODE_T_PAIEMENT.setLayoutData(tfCODE_T_PAIEMENTLData);
						} else {					
						fieldCODE_T_PAIEMENT = new DecoratedField(groupParam, SWT.BORDER, new TextControlCreator());
						tfCODE_T_PAIEMENT = (Text)fieldCODE_T_PAIEMENT.getControl();
						fieldCODE_T_PAIEMENT.getLayoutControl().setLayoutData(tfCODE_T_PAIEMENTLData);
						}
					}
					{
						laCODE_T_TVA_DOC = new Label(groupParam, SWT.NONE);
						laCODE_T_TVA_DOC.setText("Localisation tiers pour TVA");
					}
					{
						GridData tfCODE_T_TVA_DOCLData = new GridData();
						tfCODE_T_TVA_DOCLData.widthHint = 79;
						//tfCODE_T_TVA_DOCLData.horizontalAlignment = GridData.FILL;
						if(!decore) {
							tfCODE_T_TVA_DOC = new Text(groupParam, SWT.BORDER);
							tfCODE_T_TVA_DOC.setLayoutData(tfCODE_T_TVA_DOCLData);
						} else {					
							fieldCODE_T_TVA_DOC = new DecoratedField(groupParam, SWT.BORDER, new TextControlCreator());
							tfCODE_T_TVA_DOC = (Text)fieldCODE_T_TVA_DOC.getControl();
							fieldCODE_T_TVA_DOC.getLayoutControl().setLayoutData(tfCODE_T_TVA_DOCLData);
						}
					}
					
					{
						laCODE_T_TARIF = new Label(groupParam, SWT.NONE);
						laCODE_T_TARIF.setText("Type de tarif");
					}
					
					{
						GridData tfCODE_T_PAIEMENTLData = new GridData();
						tfCODE_T_PAIEMENTLData.widthHint = 79;
						if(!decore) {
							tfCODE_T_TARIF = new Text(groupParam, SWT.BORDER);
							tfCODE_T_TARIF.setLayoutData(tfCODE_T_PAIEMENTLData);
						} else {					
						fieldCODE_T_TARIF = new DecoratedField(groupParam, SWT.BORDER, new TextControlCreator());
						tfCODE_T_TARIF = (Text)fieldCODE_T_TARIF.getControl();
						fieldCODE_T_TARIF.getLayoutControl().setLayoutData(tfCODE_T_PAIEMENTLData);
						}
					}
					
				}
				
				{
					groupContact = new Group(paCorpsFormulaire, SWT.NONE);
					GridLayout groupContactLayout = new GridLayout();
					groupContactLayout.makeColumnsEqualWidth = true;
					groupContactLayout.numColumns = 2;
					groupContact.setLayout(groupContactLayout);
					GridData groupContactLData = new GridData();
					groupContactLData.horizontalAlignment = GridData.FILL;
					groupContactLData.grabExcessHorizontalSpace = true;
					groupContact.setLayoutData(groupContactLData);
					groupContact.setText("Contact");
					
					{
						laNUMERO_TELEPHONE = new Label(groupContact, SWT.NONE);
						laNUMERO_TELEPHONE.setText("Téléphone");
					}
					{
						GridData tfNUMERO_TELEPHONELData = new GridData();
						tfNUMERO_TELEPHONELData.widthHint = 156;
						tfNUMERO_TELEPHONELData.verticalAlignment = GridData.FILL;
						if(!decore) {
							tfNUMERO_TELEPHONE = new Text(groupContact, SWT.BORDER);
							tfNUMERO_TELEPHONE.setLayoutData(tfNUMERO_TELEPHONELData);
						} else {					
						fieldNUMERO_TELEPHONE = new DecoratedField(groupContact, SWT.BORDER, new TextControlCreator());
						tfNUMERO_TELEPHONE = (Text)fieldNUMERO_TELEPHONE.getControl();
						fieldNUMERO_TELEPHONE.getLayoutControl().setLayoutData(tfNUMERO_TELEPHONELData);
						}
					}
					{
						laADRESSE_EMAIL = new Label(groupContact, SWT.NONE);
						laADRESSE_EMAIL.setText("Adresse Email");
					}
					{
						GridData tfADRESSE_EMAILLData = new GridData();
						tfADRESSE_EMAILLData.widthHint = 156;
						tfADRESSE_EMAILLData.verticalAlignment = GridData.FILL;
						if(!decore) {
							tfADRESSE_EMAIL = new Text(groupContact, SWT.BORDER);
							tfADRESSE_EMAIL.setLayoutData(tfADRESSE_EMAILLData);
						} else {					
						fieldADRESSE_EMAIL = new DecoratedField(groupContact, SWT.BORDER, new TextControlCreator());
						tfADRESSE_EMAIL = (Text)fieldADRESSE_EMAIL.getControl();
						fieldADRESSE_EMAIL.getLayoutControl().setLayoutData(tfADRESSE_EMAILLData);
						}
					}
					{
						laADRESSE_WEB = new Label(groupContact, SWT.NONE);
						laADRESSE_WEB.setText("Adresse Web");
					}
					{
						GridData tfADRESSE_WEBLData = new GridData();
						tfADRESSE_WEBLData.widthHint = 156;
						tfADRESSE_WEBLData.verticalAlignment = GridData.FILL;
						if(!decore) {
							tfADRESSE_WEB = new Text(groupContact, SWT.BORDER);
							tfADRESSE_WEB.setLayoutData(tfADRESSE_WEBLData);
						} else {					
						fieldADRESSE_WEB = new DecoratedField(groupContact, SWT.BORDER, new TextControlCreator());
						tfADRESSE_WEB = (Text)fieldADRESSE_WEB.getControl();
						fieldADRESSE_WEB.getLayoutControl().setLayoutData(tfADRESSE_WEBLData);
						}
					}
				}
				

				{
					groupCompta = new Group(paCorpsFormulaire, SWT.NONE);
					GridLayout groupComptaLayout = new GridLayout();
					groupComptaLayout.makeColumnsEqualWidth = true;
					groupComptaLayout.numColumns = 2;
					groupCompta.setLayout(groupComptaLayout);
					GridData groupComptaLData = new GridData();
					groupComptaLData.horizontalAlignment = GridData.FILL;
					groupComptaLData.grabExcessHorizontalSpace = true;
					groupComptaLData.verticalAlignment = GridData.FILL;
					groupCompta.setLayoutData(groupComptaLData);
					groupCompta.setText("Comptabilité");
					{
						laCodeCompta = new Label(groupCompta, SWT.NONE);
						laCodeCompta.setText("Code compta");
					}
					{
						GridData tfCODE_COMPTALData = new GridData();
						tfCODE_COMPTALData.horizontalAlignment = GridData.FILL;
						if(!decore) {
							tfCODE_COMPTA = new Text(groupCompta, SWT.BORDER);
							tfCODE_COMPTA.setLayoutData(tfCODE_COMPTALData);
						} else {					
							fieldCODE_COMPTA = new DecoratedField(groupCompta, SWT.BORDER, new TextControlCreator());
							tfCODE_COMPTA = (Text)fieldCODE_COMPTA.getControl();
							fieldCODE_COMPTA.getLayoutControl().setLayoutData(tfCODE_COMPTALData);
						}
					}
					{
						laCOMPTE = new Label(groupCompta, SWT.NONE);
						laCOMPTE.setText("Compte rattaché");
					}
					{
						GridData tfCOMPTELData = new GridData();
						tfCOMPTELData.horizontalAlignment = GridData.FILL;
						if(!decore) {
							tfCOMPTE = new Text(groupCompta, SWT.BORDER);
							tfCOMPTE.setLayoutData(tfCOMPTELData);
						} else {					
							fieldCOMPTE = new DecoratedField(groupCompta, SWT.BORDER, new TextControlCreator());
							tfCOMPTE = (Text)fieldCOMPTE.getControl();
							fieldCOMPTE.getLayoutControl().setLayoutData(tfCOMPTELData);
						}
					}
				}
				
				{
					groupPublipostage = new Group(paCorpsFormulaire, SWT.NONE);
					GridLayout groupPublipostageLayout = new GridLayout();
					groupPublipostageLayout.makeColumnsEqualWidth = false;
					groupPublipostageLayout.numColumns = 3;
					groupPublipostage.setLayout(groupPublipostageLayout);
					//groupPublipostage.setLayout(new FillLayout());
					GridData groupCommentaireLData = new GridData();
					groupCommentaireLData.horizontalAlignment = GridData.FILL;
					groupCommentaireLData.grabExcessHorizontalSpace = true;
					groupCommentaireLData.verticalAlignment = GridData.FILL;
					groupCommentaireLData.grabExcessVerticalSpace = true;
					groupPublipostage.setLayoutData(groupCommentaireLData);
					groupPublipostage.setText("Publipostage");
					{
					      scrolledCompositePublipostage = new ScrolledComposite(groupPublipostage, SWT.H_SCROLL | SWT.V_SCROLL /*| SWT.BORDER*/);
					      
					      GridData ld = new GridData();
					      ld.horizontalAlignment = GridData.FILL;
					      ld.grabExcessHorizontalSpace = true;
					      ld.verticalAlignment = GridData.FILL;
					      ld.grabExcessVerticalSpace = true;
					      ld.horizontalSpan = 3;
					      scrolledCompositePublipostage.setLayoutData(ld);
					      
					      scrolledCompositePublipostage.setExpandHorizontal(true);
					      scrolledCompositePublipostage.setExpandVertical(true);
					      compositeCheckBoxPubli = new Composite(scrolledCompositePublipostage, SWT.NONE);
					      scrolledCompositePublipostage.setContent(compositeCheckBoxPubli);
					      GridLayout layout = new GridLayout();
					      layout.numColumns = 2;
					      compositeCheckBoxPubli.setLayout(layout);
					      scrolledCompositePublipostage.setMinSize(compositeCheckBoxPubli.computeSize(SWT.DEFAULT, SWT.DEFAULT));
					}
					{
						btnFusionPublipostage = new Button(groupPublipostage, SWT.CHECK);
						btnFusionPublipostage.setText("Fusionner les documents");
						GridData btnPublipostageLData = new GridData();
						btnPublipostageLData.horizontalAlignment = GridData.END;
						btnFusionPublipostage.setLayoutData(btnPublipostageLData);
					}
					{
						btnOuvreRepertoireCourrier = new Button(groupPublipostage, SWT.PUSH);
						//btnPublipostage.setText("Publipostage");
						GridData btnPublipostageLData = new GridData();
						btnPublipostageLData.horizontalAlignment = GridData.END;
						btnOuvreRepertoireCourrier.setLayoutData(btnPublipostageLData);
					}
					{
						btnPublipostage = new Button(groupPublipostage, SWT.PUSH);
						//btnPublipostage.setText("Publipostage");
						GridData btnPublipostageLData = new GridData();
						btnPublipostageLData.horizontalAlignment = GridData.END;
						btnPublipostage.setLayoutData(btnPublipostageLData);
					}
				}
			
				{
					groupCommentaire = new Group(paCorpsFormulaire, SWT.NONE);
					GridLayout groupCommentaireLayout = new GridLayout();
					groupCommentaireLayout.makeColumnsEqualWidth = true;
					groupCommentaire.setLayout(groupCommentaireLayout);
					GridData groupCommentaireLData = new GridData();
					groupCommentaireLData.horizontalAlignment = GridData.FILL;
					groupCommentaireLData.grabExcessHorizontalSpace = true;
					groupCommentaireLData.verticalAlignment = GridData.FILL;
					groupCommentaireLData.horizontalSpan = 2;
					groupCommentaireLData.grabExcessVerticalSpace = true;
					groupCommentaire.setLayoutData(groupCommentaireLData);
					groupCommentaire.setText("Commentaire");
					{
						GridData tfLIBL_COMMENTAIRELData = new GridData();
						tfLIBL_COMMENTAIRELData.horizontalAlignment = GridData.FILL;
						tfLIBL_COMMENTAIRELData.horizontalSpan = 3;
						tfLIBL_COMMENTAIRELData.grabExcessHorizontalSpace = true;
						tfLIBL_COMMENTAIRELData.verticalAlignment = GridData.FILL;
						tfLIBL_COMMENTAIRELData.grabExcessVerticalSpace = true;
						if(!decore) {
							tfLIBL_COMMENTAIRE = new Text(groupCommentaire, SWT.MULTI | SWT.WRAP | SWT.H_SCROLL| SWT.V_SCROLL| SWT.BORDER);
							tfLIBL_COMMENTAIRE.setLayoutData(tfLIBL_COMMENTAIRELData);
						} else {					
							fieldLIBL_COMMENTAIRE = new DecoratedField(groupCommentaire, SWT.MULTI
									| SWT.WRAP
									| SWT.H_SCROLL
									| SWT.V_SCROLL
									| SWT.BORDER, new TextControlCreator());
							tfLIBL_COMMENTAIRE = (Text)fieldLIBL_COMMENTAIRE.getControl();
							fieldLIBL_COMMENTAIRE.getLayoutControl().setLayoutData(tfLIBL_COMMENTAIRELData);
						}
					}
				}
//				{
//					composite3 = new Composite(paCorpsFormulaire, SWT.NONE);
//					GridLayout composite3Layout = new GridLayout();
//					composite3Layout.makeColumnsEqualWidth = true;
//					composite3Layout.numColumns = 2;
//					composite3Layout.marginHeight = 10;
//					GridData composite3LData = new GridData();
//					composite3LData.horizontalSpan = 2;
//					composite3LData.widthHint = 303;
//					composite3LData.heightHint = 44;
//					composite3.setLayoutData(composite3LData);
//					composite3.setLayout(composite3Layout);
//					{
//						btnAjoutAdr = new Button(composite3, SWT.PUSH | SWT.CENTER);
//						GridData btnAjoutAdrLData = new GridData();
//						btnAjoutAdrLData.horizontalAlignment = GridData.FILL;
//						btnAjoutAdrLData.grabExcessHorizontalSpace = true;
//						btnAjoutAdrLData.heightHint = 27;
//						btnAjoutAdr.setLayoutData(btnAjoutAdrLData);
//						btnAjoutAdr.setText("Adresse (Alt+A)");
//						btnAjoutAdr.setSize(144, 27);
//					}
//					{
//						btnAjoutTelephone = new Button(composite3, SWT.PUSH | SWT.CENTER);
//						GridData btnAjoutTelephoneLData = new GridData();
//						btnAjoutTelephoneLData.horizontalAlignment = GridData.FILL;
//						btnAjoutTelephoneLData.grabExcessHorizontalSpace = true;
//						btnAjoutTelephoneLData.heightHint = 27;
//						btnAjoutTelephone.setLayoutData(btnAjoutTelephoneLData);
//						btnAjoutTelephone.setText("Tel (Alt+T)");
//						btnAjoutTelephone.setSize(144, 27);
//					}
//					{
//						btnAjoutEmail = new Button(composite3, SWT.PUSH | SWT.CENTER);
//						GridData btnAjoutEmailLData = new GridData();
//						btnAjoutEmailLData.horizontalAlignment = GridData.FILL;
//						btnAjoutEmailLData.grabExcessHorizontalSpace = true;
//						btnAjoutEmailLData.heightHint = 27;
//						btnAjoutEmail.setLayoutData(btnAjoutEmailLData);
//						btnAjoutEmail.setText("Email (Alt+M)");
//						btnAjoutEmail.setSize(144, 27);
//					}
//					{
//						btnCompl = new Button(composite3, SWT.PUSH | SWT.CENTER);
//						GridData btnComplLData = new GridData();
//						btnComplLData.horizontalAlignment = GridData.FILL;
//						btnComplLData.grabExcessHorizontalSpace = true;
//						btnComplLData.heightHint = 27;
//						btnCompl.setLayoutData(btnComplLData);
//						btnCompl.setText("Compl. (Alt+C)");
//						btnCompl.setSize(144, 27);
//					}
//					{
//						btnConditionPaiement = new Button(composite3, SWT.PUSH | SWT.CENTER);
//						GridData btnConditionPaiementLData = new GridData();
//						btnConditionPaiementLData.grabExcessHorizontalSpace = true;
//						btnConditionPaiementLData.widthHint = 144;
//						btnConditionPaiementLData.heightHint = 27;
//						btnConditionPaiement.setLayoutData(btnConditionPaiementLData);
//						btnConditionPaiement.setText("Cond. Paiement (Alt+D)");
//						btnConditionPaiement.setSize(144, 27);
//					}
//					{
//						btnAjoutSiteWeb = new Button(composite3, SWT.PUSH | SWT.CENTER);
//						GridData btnAjoutSiteWebLData = new GridData();
//						btnAjoutSiteWebLData.horizontalAlignment = GridData.FILL;
//						btnAjoutSiteWebLData.grabExcessHorizontalSpace = true;
//						btnAjoutSiteWebLData.heightHint = 27;
//						btnAjoutSiteWeb.setLayoutData(btnAjoutSiteWebLData);
//						btnAjoutSiteWeb.setText("Web (Alt+W)");
//						btnAjoutSiteWeb.setSize(144, 27);
//					}
//					{
//						btnAjoutCommercial = new Button(composite3, SWT.PUSH | SWT.CENTER);
//						GridData btnAjoutCommercialLData = new GridData();
//						btnAjoutCommercialLData.horizontalSpan = 2;
//						btnAjoutCommercialLData.horizontalAlignment = GridData.CENTER;
//						btnAjoutCommercialLData.grabExcessHorizontalSpace = true;
//						btnAjoutCommercialLData.widthHint = 146;
//						btnAjoutCommercialLData.heightHint = 27;
//						btnAjoutCommercial.setLayoutData(btnAjoutCommercialLData);
//						btnAjoutCommercial.setText("Commercial (Alt+O)");
//						btnAjoutCommercial.setSize(146, 27);
//					}
//				}
				
//				paCorpsFormulaire
//					.setTabList(new org.eclipse.swt.widgets.Control[] {
//							tfCODE_TIERS, tfCODE_COMPTA, tfNOM_TIERS,
//							tfPRENOM_TIERS, tfSURNOM_TIERS, composite2,
//							tfCODE_T_CIVILITE, tfCODE_ENTREPRISE, tfCODE_T_TIERS, tfCOMPTE,
//							tfLIBL_COMMENTAIRE, tfCODE_BANQUE, tfEntite, composite3 });
			}
			
			this.setLayout(new GridLayout());
			GridLayout compositeFormLayout = new GridLayout();
			compositeFormLayout.numColumns = 2;
			this.setSize(1090, 973);
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

//	public Button getBtnAjoutAdr() {
//		return btnAjoutAdr;
//	}
//
//	public Button getBtnAjoutCommercial() {
//		return btnAjoutCommercial;
//	}
//
//	public Button getBtnAjoutEmail() {
//		return btnAjoutEmail;
//	}
//
//	public Button getBtnAjoutSiteWeb() {
//		return btnAjoutSiteWeb;
//	}
//
//	public Button getBtnAjoutTelephone() {
//		return btnAjoutTelephone;
//	}
//
//	public Button getBtnCompl() {
//		return btnCompl;
//	}
//
//	public Button getBtnConditionPaiement() {
//		return btnConditionPaiement;
//	}

	public Label getLaAccise() {
		return laAccise;
	}

	public Label getLaCODE_T_TARIF() {
		return laCODE_T_TARIF;
	}

	public Text getTfAccise() {
		return tfAccise;
	}

	public Text getTfCODE_T_TARIF() {
		return tfCODE_T_TARIF;
	}

	public DecoratedField getFieldAccise() {
		return fieldAccise;
	}

	public DecoratedField getFieldCODE_T_TARIF() {
		return fieldCODE_T_TARIF;
	}

	public Button getCbACTIF_TIERS() {
		return cbACTIF_TIERS;
	}

	public Composite getPaCorpsFormulaire() {
		return paCorpsFormulaire;
	}

	public Composite getComposite2() {
		return composite2;
	}

//	public Composite getComposite3() {
//		return composite3;
//	}

	public Label getLaACTIF_TIERS() {
		return laACTIF_TIERS;
	}

	public Label getLaCODE_ENTREPRISE() {
		return laCODE_ENTREPRISE;
	}

	public Label getLaCODE_T_CIVILITE() {
		return laCODE_T_CIVILITE;
	}

	public Label getLaCODE_T_TIERS() {
		return laCODE_T_TIERS;
	}

	public Label getLaCODE_TIERS() {
		return laCODE_TIERS;
	}

	public Label getLaCodeCompta() {
		return laCodeCompta;
	}

	public Label getLaCOMPTE() {
		return laCOMPTE;
	}

	public Label getLaEntite() {
		return laEntite;
	}

	public Label getLaNOM_TIERS() {
		return laNOM_TIERS;
	}

	public Label getLaPRENOM_TIERS() {
		return laPRENOM_TIERS;
	}

	public Label getLaSURNOM_TIERS() {
		return laSURNOM_TIERS;
	}

	public Label getLaTTC() {
		return laTTC;
	}

	public Text getTfCODE_COMPTA() {
		return tfCODE_COMPTA;
	}

	public Text getTfCODE_ENTREPRISE() {
		return tfCODE_ENTREPRISE;
	}

	public Text getTfCODE_T_CIVILITE() {
		return tfCODE_T_CIVILITE;
	}

	public Text getTfCODE_T_TIERS() {
		return tfCODE_T_TIERS;
	}

	public Text getTfCODE_TIERS() {
		return tfCODE_TIERS;
		//return (Text)field.getControl();
	}

	public Text getTfCOMPTE() {
		return tfCOMPTE;
	}

	public Text getTfEntite() {
		return tfEntite;
	}

	public Text getTfLIBL_COMMENTAIRE() {
		return tfLIBL_COMMENTAIRE;
	}

	public Text getTfNOM_TIERS() {
		return tfNOM_TIERS;
	}

	public Text getTfPRENOM_TIERS() {
		return tfPRENOM_TIERS;
	}

	public Text getTfSURNOM_TIERS() {
		return tfSURNOM_TIERS;
	}

	public Button getTfTTC() {
		return tfTTC;
	}

	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public DecoratedField getFieldCODE_BANQUE() {
		return fieldCODE_BANQUE;
	}

	public DecoratedField getFieldCODE_COMPTA() {
		return fieldCODE_COMPTA;
	}

	public DecoratedField getFieldCODE_ENTREPRISE() {
		return fieldCODE_ENTREPRISE;
	}

	public DecoratedField getFieldCODE_T_CIVILITE() {
		return fieldCODE_T_CIVILITE;
	}

	public DecoratedField getFieldCODE_T_TIERS() {
		return fieldCODE_T_TIERS;
	}

	public DecoratedField getFieldCODE_TIERS() {
		return fieldCODE_TIERS;
	}

	public DecoratedField getFieldCOMPTE() {
		return fieldCOMPTE;
	}

	public DecoratedField getFieldEntite() {
		return fieldEntite;
	}

	public DecoratedField getFieldLIBL_COMMENTAIRE() {
		return fieldLIBL_COMMENTAIRE;
	}

	public DecoratedField getFieldNOM_TIERS() {
		return fieldNOM_TIERS;
	}

	public DecoratedField getFieldPRENOM_TIERS() {
		return fieldPRENOM_TIERS;
	}

	public DecoratedField getFieldSURNOM_TIERS() {
		return fieldSURNOM_TIERS;
	}

	public DecoratedField getFieldACTIF_TIERS() {
		return fieldACTIF_TIERS;
	}

	public DecoratedField getFieldTTC() {
		return fieldTTC;
	}
	
	public Group getGroupIdentite(Composite parent) {
		return groupIdentite;
	}
	
	public Group getGroupAdresse(Composite parent) {
		return groupAdresse;
	}
	
	public Group getGroupContact(Composite parent) {
		return groupContact;
	}
	
	public Group getGroupCompta(Composite parent) {
		return groupCompta;
	}

	public Label getLaADRESSE1_ADRESSE() {
		return laADRESSE1_ADRESSE;
	}

	public Label getLaADRESSE2_ADRESSE() {
		return laADRESSE2_ADRESSE;
	}

	public Label getLaADRESSE3_ADRESSE() {
		return laADRESSE3_ADRESSE;
	}

	public Label getLaCODEPOSTAL_ADRESSE() {
		return laCODEPOSTAL_ADRESSE;
	}

	public Label getLaVILLE_ADRESSE() {
		return laVILLE_ADRESSE;
	}

	public Label getLaPAYS_ADRESSE() {
		return laPAYS_ADRESSE;
	}

	public Label getLaADRESSE_EMAIL() {
		return laADRESSE_EMAIL;
	}

	public Label getLaNUMERO_TELEPHONE() {
		return laNUMERO_TELEPHONE;
	}

	public Label getLaADRESSE_WEB() {
		return laADRESSE_WEB;
	}

	public Label getLaTVA_I_COM_COMPL() {
		return laTVA_I_COM_COMPL;
	}

	public Label getLaACCISE() {
		return laAccise;
	}

	public Group getGroupCompta() {
		return groupCompta;
	}

	public Group getGroupContact() {
		return groupContact;
	}

	public Group getGroupAdresse() {
		return groupAdresse;
	}

	public Group getGroupIdentite() {
		return groupIdentite;
	}

	public Text getTfADRESSE1_ADRESSE() {
		return tfADRESSE1_ADRESSE;
	}

	public Text getTfADRESSE2_ADRESSE() {
		return tfADRESSE2_ADRESSE;
	}

	public Text getTfADRESSE3_ADRESSE() {
		return tfADRESSE3_ADRESSE;
	}

	public Text getTfCODEPOSTAL_ADRESSE() {
		return tfCODEPOSTAL_ADRESSE;
	}

	public Text getTfVILLE_ADRESSE() {
		return tfVILLE_ADRESSE;
	}

	public Text getTfPAYS_ADRESSE() {
		return tfPAYS_ADRESSE;
	}

	public Text getTfADRESSE_EMAIL() {
		return tfADRESSE_EMAIL;
	}

	public Text getTfNUMERO_TELEPHONE() {
		return tfNUMERO_TELEPHONE;
	}

	public Text getTfADRESSE_WEB() {
		return tfADRESSE_WEB;
	}

	public Text getTfTVA_I_COM_COMPL() {
		return tfTVA_I_COM_COMPL;
	}

	public Text getTfACCISE() {
		return tfAccise;
	}

	public DecoratedField getFieldADRESSE1_ADRESSE() {
		return fieldADRESSE1_ADRESSE;
	}

	public DecoratedField getFieldADRESSE2_ADRESSE() {
		return fieldADRESSE2_ADRESSE;
	}

	public DecoratedField getFieldADRESSE3_ADRESSE() {
		return fieldADRESSE3_ADRESSE;
	}

	public DecoratedField getFieldCODEPOSTAL_ADRESSE() {
		return fieldCODEPOSTAL_ADRESSE;
	}

	public DecoratedField getFieldVILLE_ADRESSE() {
		return fieldVILLE_ADRESSE;
	}

	public DecoratedField getFieldPAYS_ADRESSE() {
		return fieldPAYS_ADRESSE;
	}

	public DecoratedField getFieldADRESSE_EMAIL() {
		return fieldADRESSE_EMAIL;
	}

	public DecoratedField getFieldNUMERO_TELEPHONE() {
		return fieldNUMERO_TELEPHONE;
	}

	public DecoratedField getFieldADRESSE_WEB() {
		return fieldADRESSE_WEB;
	}

	public DecoratedField getFieldTVA_I_COM_COMPL() {
		return fieldTVA_I_COM_COMPL;
	}

	public DecoratedField getFieldACCISE() {
		return fieldAccise;
	}
	
	public Group getGroupParam() {
		return groupParam;
	}
	
	public Group getGroupCommentaire() {
		return groupCommentaire;
	}

	public Label getLaCODE_C_PAIEMENT() {
		return laCODE_C_PAIEMENT;
	}

	public Text getTfCODE_C_PAIEMENT() {
		return tfCODE_C_PAIEMENT;
	}

	public DecoratedField getFieldCODE_C_PAIEMENT() {
		return fieldCODE_C_PAIEMENT;
	}

	public Label getLaCODE_T_TVA_DOC() {
		return laCODE_T_TVA_DOC;
	}

	public Text getTfCODE_T_TVA_DOC() {
		return tfCODE_T_TVA_DOC;
	}

	public DecoratedField getFieldCODE_T_TVA_DOC() {
		return fieldCODE_T_TVA_DOC;
	}

	public Group getGroupPublipostage() {
		return groupPublipostage;
	}

	public ScrolledComposite getScrolledCompositePublipostage() {
		return scrolledCompositePublipostage;
	}

	public Composite getCompositeCheckBoxPubli() {
		return compositeCheckBoxPubli;
	}

	public Button getBtnPublipostage() {
		return btnPublipostage;
	}

	public Button getBtnFusionPublipostage() {
		return btnFusionPublipostage;
	}

	public Button getBtnOuvreRepertoireCourrier() {
		return btnOuvreRepertoireCourrier;
	}
	
		public Label getLaCODE_T_PAIEMENT() {
		return laCODE_T_PAIEMENT;
	}

	public Text getTfCODE_T_PAIEMENT() {
		return tfCODE_T_PAIEMENT;
	}

	public DecoratedField getFieldCODE_T_PAIEMENT() {
		return fieldCODE_T_PAIEMENT;
	}

	public Label getLaIcs() {
		return laIcs;
	}

	public Text getTfIcs() {
		return tfIcs;
	}

	public DecoratedField getFieldIcs() {
		return fieldIcs;
	}


}

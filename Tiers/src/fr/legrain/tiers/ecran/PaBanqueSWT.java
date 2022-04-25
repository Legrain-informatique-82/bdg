package fr.legrain.tiers.ecran;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.fieldassist.TextControlCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.resource.SWTResourceManager;

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
public class PaBanqueSWT extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
/*

	private JTextField tfCOMPTE;
	private JLabel laCOMPTE;
	private JLabel laLibTTiers;
	private JTextField tfLibTTiers;
 */
	private Composite paCorpsFormulaire;
	private Label laNOM_BANQUE;
	private Label laCOMPTE;
	private Label laCODE_BANQUE;
	private Label laCODE_GUICHET;
	private Label laCLE_RIB;
	private Label laADRESSE1_BANQUE;
	private Label laADRESSE2_BANQUE;
	private Label laCP_BANQUE;
	private Label laVILLE_BANQUE;
	private Label laIBAN;
	private Label laNOM_COMPTE;
	private Label laCODE_B_I_C;
	private Label laTITULAIRE;
	private Label laCODE_T_BANQUE;
	private Label laCPTCOMPTABLE;
	
	

	
	private Text tfNOM_BANQUE;
	private Text tfCOMPTE;
	private Text tfCODE_BANQUE;
	private Text tfCODE_GUICHET;
	private Text tfCLE_RIB;
	private Text tfADRESSE1_BANQUE;
	private Text tfADRESSE2_BANQUE;
	private Text tfCP_BANQUE;
	private Text tfVILLE_BANQUE;
	private Text tfIBAN;
	private Text tfCODE_B_I_C;
	private Text tfNOM_COMPTE;
	private Text tfTITULAIRE;
	private Text tfCODE_T_BANQUE;
	private Text tfCPTCOMPTABLE;
	
	private Group grpGeneral;
	private Group grpDomiciliation;
	private Group grpRIB;
	private Group grpAutre;

	private DecoratedField fieldNOM_BANQUE;
	private DecoratedField fieldCOMPTE;
	private DecoratedField fieldCODE_BANQUE;
	private DecoratedField fieldCODE_GUICHET;
	private DecoratedField fieldCLE_RIB;
	private DecoratedField fieldADRESSE1_BANQUE;
	private DecoratedField fieldADRESSE2_BANQUE;
	private DecoratedField fieldCP_BANQUE;
	private DecoratedField fieldVILLE_BANQUE;
	private DecoratedField fieldIBAN;
	private DecoratedField fieldCODE_B_I_C;
	private DecoratedField fieldNOM_COMPTE;
	private DecoratedField fieldTITULAIRE;
	private DecoratedField fieldCODE_T_BANQUE;
	private DecoratedField fieldCPTCOMPTABLE;
	

	
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
		PaBanqueSWT inst = new PaBanqueSWT(shell, SWT.NULL);
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

	public PaBanqueSWT(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			{
				paCorpsFormulaire = new Composite(
					super.getPaFomulaire(),
					SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 2;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.grabExcessVerticalSpace = true;
				composite1LData.grabExcessHorizontalSpace = true;
				paCorpsFormulaire.setLayoutData(composite1LData);
				paCorpsFormulaire.setLayout(composite1Layout);
				
				{
					grpGeneral = new Group(paCorpsFormulaire, SWT.NONE);
					GridLayout grpLayout = new GridLayout();
					grpLayout.numColumns = 2;
					GridData grpLData = new GridData();
					grpLData.horizontalAlignment = GridData.FILL;
					grpLData.verticalAlignment = GridData.FILL;
					grpLData.grabExcessVerticalSpace = true;
					grpLData.grabExcessHorizontalSpace = true;
					grpGeneral.setLayoutData(grpLData);
					grpGeneral.setLayout(grpLayout);
				}
				
				{
					laTITULAIRE = new Label(grpGeneral, SWT.NONE);
					laTITULAIRE.setText("Titulaire");
				}
				{
					GridData tfTITULAIRELData = new GridData();
					tfTITULAIRELData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
					tfTITULAIRELData.grabExcessHorizontalSpace = true;
					tfTITULAIRELData.widthHint = 156;
					tfTITULAIRELData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfTITULAIRE = new Text(grpGeneral, SWT.BORDER);
						tfTITULAIRE.setLayoutData(tfTITULAIRELData);
					} else {					
					fieldTITULAIRE = new DecoratedField(grpGeneral, SWT.BORDER, new TextControlCreator());
					tfTITULAIRE = (Text)fieldTITULAIRE.getControl();
					fieldTITULAIRE.getLayoutControl().setLayoutData(tfTITULAIRELData);
					}
				}
				
				{
					laCODE_T_BANQUE = new Label(grpGeneral, SWT.NONE);
					laCODE_T_BANQUE.setText("Code Type Banque");
				}
				{
					GridData tfCODE_T_BANQUELData = new GridData();
					tfCODE_T_BANQUELData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
					tfCODE_T_BANQUELData.grabExcessHorizontalSpace = true;
					tfCODE_T_BANQUELData.widthHint = 156;
					tfCODE_T_BANQUELData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfCODE_T_BANQUE = new Text(grpGeneral, SWT.BORDER);
						tfCODE_T_BANQUE.setLayoutData(tfCODE_T_BANQUELData);
					} else {					
					fieldCODE_T_BANQUE = new DecoratedField(grpGeneral, SWT.BORDER, new TextControlCreator());
					tfCODE_T_BANQUE = (Text)fieldCODE_T_BANQUE.getControl();
					fieldCODE_T_BANQUE.getLayoutControl().setLayoutData(tfCODE_T_BANQUELData);
					}
				}
				
				{
					laCPTCOMPTABLE = new Label(grpGeneral, SWT.NONE);
					laCPTCOMPTABLE.setText("Compte comptable");
				}
				{
					GridData tfCPTCOMPTABLELData = new GridData();
					tfCPTCOMPTABLELData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
					tfCPTCOMPTABLELData.grabExcessHorizontalSpace = true;
					tfCPTCOMPTABLELData.widthHint = 156;
					tfCPTCOMPTABLELData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfCPTCOMPTABLE = new Text(grpGeneral, SWT.BORDER);
						tfCPTCOMPTABLE.setLayoutData(tfCPTCOMPTABLELData);
					} else {					
					fieldCPTCOMPTABLE = new DecoratedField(grpGeneral, SWT.BORDER, new TextControlCreator());
					tfCPTCOMPTABLE = (Text)fieldCPTCOMPTABLE.getControl();
					fieldCPTCOMPTABLE.getLayoutControl().setLayoutData(tfCPTCOMPTABLELData);
					}
				}
				
				{
					grpDomiciliation = new Group(paCorpsFormulaire, SWT.NONE);
					grpDomiciliation.setText("Domiciliation");
					GridLayout grpLayout = new GridLayout();
					grpLayout.numColumns = 2;
					GridData grpLData = new GridData();
					grpLData.horizontalAlignment = GridData.FILL;
					grpLData.verticalAlignment = GridData.FILL;
					grpLData.grabExcessVerticalSpace = true;
					grpLData.grabExcessHorizontalSpace = true;
					grpDomiciliation.setLayoutData(grpLData);
					grpDomiciliation.setLayout(grpLayout);
				}
				
				{
					laNOM_BANQUE = new Label(grpDomiciliation, SWT.NONE);
					laNOM_BANQUE.setText("Etablissement");
				}
				{
					GridData tfNOM_BANQUELData = new GridData();
					tfNOM_BANQUELData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
					tfNOM_BANQUELData.grabExcessHorizontalSpace = true;
					tfNOM_BANQUELData.widthHint = 156;
					tfNOM_BANQUELData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfNOM_BANQUE = new Text(grpDomiciliation, SWT.BORDER);
						tfNOM_BANQUE.setLayoutData(tfNOM_BANQUELData);
					} else {					
					fieldNOM_BANQUE = new DecoratedField(grpDomiciliation, SWT.BORDER, new TextControlCreator());
					tfNOM_BANQUE = (Text)fieldNOM_BANQUE.getControl();
					fieldNOM_BANQUE.getLayoutControl().setLayoutData(tfNOM_BANQUELData);
					}
				}
				
				{
					laADRESSE1_BANQUE = new Label(grpDomiciliation, SWT.NONE);
					laADRESSE1_BANQUE.setText("Adresse(1)");
				}
				{
					GridData tfADRESSE1_BANQUELData = new GridData();
					tfADRESSE1_BANQUELData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
					tfADRESSE1_BANQUELData.grabExcessHorizontalSpace = true;
					tfADRESSE1_BANQUELData.widthHint = 156;
					tfADRESSE1_BANQUELData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfADRESSE1_BANQUE = new Text(grpDomiciliation, SWT.BORDER);
						tfADRESSE1_BANQUE.setLayoutData(tfADRESSE1_BANQUELData);
					} else {					
					fieldADRESSE1_BANQUE = new DecoratedField(grpDomiciliation, SWT.BORDER, new TextControlCreator());
					tfADRESSE1_BANQUE = (Text)fieldADRESSE1_BANQUE.getControl();
					fieldADRESSE1_BANQUE.getLayoutControl().setLayoutData(tfADRESSE1_BANQUELData);
					}
				}
				
				{
					laADRESSE2_BANQUE = new Label(grpDomiciliation, SWT.NONE);
					laADRESSE2_BANQUE.setText("Adresse(2)");
				}
				{
					GridData tfADRESSE2_BANQUELData = new GridData();
					tfADRESSE2_BANQUELData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
					tfADRESSE2_BANQUELData.grabExcessHorizontalSpace = true;
					tfADRESSE2_BANQUELData.widthHint = 156;
					tfADRESSE2_BANQUELData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfADRESSE2_BANQUE = new Text(grpDomiciliation, SWT.BORDER);
						tfADRESSE2_BANQUE.setLayoutData(tfADRESSE2_BANQUELData);
					} else {					
					fieldADRESSE2_BANQUE = new DecoratedField(grpDomiciliation, SWT.BORDER, new TextControlCreator());
					tfADRESSE2_BANQUE = (Text)fieldADRESSE2_BANQUE.getControl();
					fieldADRESSE2_BANQUE.getLayoutControl().setLayoutData(tfADRESSE2_BANQUELData);
					}
				}
				
				{
					laCP_BANQUE = new Label(grpDomiciliation, SWT.NONE);
					laCP_BANQUE.setText("Code Postal");
				}
				{
					GridData tfCP_BANQUELData = new GridData();
					tfCP_BANQUELData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
					tfCP_BANQUELData.grabExcessHorizontalSpace = true;
					tfCP_BANQUELData.widthHint = 156;
					tfCP_BANQUELData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfCP_BANQUE = new Text(grpDomiciliation, SWT.BORDER);
						tfCP_BANQUE.setLayoutData(tfCP_BANQUELData);
					} else {					
					fieldCP_BANQUE = new DecoratedField(grpDomiciliation, SWT.BORDER, new TextControlCreator());
					tfCP_BANQUE = (Text)fieldCP_BANQUE.getControl();
					fieldCP_BANQUE.getLayoutControl().setLayoutData(tfCP_BANQUELData);
					}
				}
				
				{
					laVILLE_BANQUE = new Label(grpDomiciliation, SWT.NONE);
					laVILLE_BANQUE.setText("Ville");
				}
				{
					GridData tfVILLE_BANQUELData = new GridData();
					tfVILLE_BANQUELData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
					tfVILLE_BANQUELData.grabExcessHorizontalSpace = true;
					tfVILLE_BANQUELData.widthHint = 156;
					tfVILLE_BANQUELData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfVILLE_BANQUE = new Text(grpDomiciliation, SWT.BORDER);
						tfVILLE_BANQUE.setLayoutData(tfVILLE_BANQUELData);
					} else {					
					fieldVILLE_BANQUE = new DecoratedField(grpDomiciliation, SWT.BORDER, new TextControlCreator());
					tfVILLE_BANQUE = (Text)fieldVILLE_BANQUE.getControl();
					fieldVILLE_BANQUE.getLayoutControl().setLayoutData(tfVILLE_BANQUELData);
					}
				}
				
				{
					grpRIB = new Group(paCorpsFormulaire, SWT.NONE);
					grpRIB.setText("RIB");
					GridLayout grpLayout = new GridLayout();
					grpLayout.numColumns = 2;
					GridData grpLData = new GridData();
					grpLData.horizontalAlignment = GridData.FILL;
					grpLData.verticalAlignment = GridData.FILL;
					grpLData.grabExcessVerticalSpace = true;
					grpLData.grabExcessHorizontalSpace = true;
					grpRIB.setLayoutData(grpLData);
					grpRIB.setLayout(grpLayout);
				}
				
				{
					laCODE_BANQUE = new Label(grpRIB, SWT.NONE);
					laCODE_BANQUE.setText("Code banque");
				}
				{
					GridData tfCODE_BANQUELData = new GridData();
					tfCODE_BANQUELData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
					tfCODE_BANQUELData.grabExcessHorizontalSpace = true;
					tfCODE_BANQUELData.widthHint = 156;
					tfCODE_BANQUELData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfCODE_BANQUE = new Text(grpRIB, SWT.BORDER);
						tfCODE_BANQUE.setLayoutData(tfCODE_BANQUELData);
					} else {					
					fieldCODE_BANQUE = new DecoratedField(grpRIB, SWT.BORDER, new TextControlCreator());
					tfCODE_BANQUE = (Text)fieldCODE_BANQUE.getControl();
					fieldCODE_BANQUE.getLayoutControl().setLayoutData(tfCODE_BANQUELData);
					}
				}
				
				{
					laCODE_GUICHET = new Label(grpRIB, SWT.NONE);
					laCODE_GUICHET.setText("Guichet");
				}
				{
					GridData tfCODE_GUICHETLData = new GridData();
					tfCODE_GUICHETLData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
					tfCODE_GUICHETLData.grabExcessHorizontalSpace = true;
					tfCODE_GUICHETLData.widthHint = 156;
					tfCODE_GUICHETLData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfCODE_GUICHET = new Text(grpRIB, SWT.BORDER);
						tfCODE_GUICHET.setLayoutData(tfCODE_GUICHETLData);
					} else {					
					fieldCODE_GUICHET = new DecoratedField(grpRIB, SWT.BORDER, new TextControlCreator());
					tfCODE_GUICHET = (Text)fieldCODE_GUICHET.getControl();
					fieldCODE_GUICHET.getLayoutControl().setLayoutData(tfCODE_GUICHETLData);
					}
				}
				
				{
					laCOMPTE = new Label(grpRIB, SWT.NONE);
					laCOMPTE.setText("N° Compte");
				}
				{
					GridData tfCOMPTE_BANQUELData = new GridData();
					tfCOMPTE_BANQUELData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
					tfCOMPTE_BANQUELData.grabExcessHorizontalSpace = true;
					tfCOMPTE_BANQUELData.widthHint = 156;
					tfCOMPTE_BANQUELData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfCOMPTE = new Text(grpRIB, SWT.BORDER);
						tfCOMPTE.setLayoutData(tfCOMPTE_BANQUELData);
					} else {					
					fieldCOMPTE = new DecoratedField(grpRIB, SWT.BORDER, new TextControlCreator());
					tfCOMPTE = (Text)fieldCOMPTE.getControl();
					fieldCOMPTE.getLayoutControl().setLayoutData(tfCOMPTE_BANQUELData);
					}
				}
				
				{
					laCLE_RIB = new Label(grpRIB, SWT.NONE);
					laCLE_RIB.setText("Clé RIB");
				}
				{
					GridData tfCLE_RIBLData = new GridData();
					tfCLE_RIBLData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
					tfCLE_RIBLData.grabExcessHorizontalSpace = true;
					tfCLE_RIBLData.widthHint = 156;
					tfCLE_RIBLData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfCLE_RIB = new Text(grpRIB, SWT.BORDER);
						tfCLE_RIB.setLayoutData(tfCLE_RIBLData);
					} else {					
					fieldCLE_RIB = new DecoratedField(grpRIB, SWT.BORDER, new TextControlCreator());
					tfCLE_RIB = (Text)fieldCLE_RIB.getControl();
					fieldCLE_RIB.getLayoutControl().setLayoutData(tfCLE_RIBLData);
					}
				}
				
				{
					grpAutre = new Group(paCorpsFormulaire, SWT.NONE);
					grpAutre.setText("Autres identifiants");
					GridLayout grpLayout = new GridLayout();
					grpLayout.numColumns = 2;
					GridData grpLData = new GridData();
					grpLData.horizontalAlignment = GridData.FILL;
					grpLData.verticalAlignment = GridData.FILL;
					grpLData.grabExcessVerticalSpace = true;
					grpLData.grabExcessHorizontalSpace = true;
					grpAutre.setLayoutData(grpLData);
					grpAutre.setLayout(grpLayout);
				}
				
				{
					laIBAN = new Label(grpAutre, SWT.NONE);
					laIBAN.setText("IBAN");
				}
				{
					GridData tfIBANLData = new GridData();
					tfIBANLData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
					tfIBANLData.grabExcessHorizontalSpace = true;
					tfIBANLData.widthHint = 156;
					tfIBANLData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfIBAN = new Text(grpAutre, SWT.BORDER);
						tfIBAN.setLayoutData(tfIBANLData);
					} else {					
					fieldIBAN = new DecoratedField(grpAutre, SWT.BORDER, new TextControlCreator());
					tfIBAN = (Text)fieldIBAN.getControl();
					fieldIBAN.getLayoutControl().setLayoutData(tfIBANLData);
					}
				}
				
				{
					laCODE_B_I_C = new Label(grpAutre, SWT.NONE);
					laCODE_B_I_C.setText("BIC");
				}
				{
					GridData tfCODE_B_I_CLData = new GridData();
					tfCODE_B_I_CLData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
					tfCODE_B_I_CLData.grabExcessHorizontalSpace = true;
					tfCODE_B_I_CLData.widthHint = 156;
					tfCODE_B_I_CLData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfCODE_B_I_C = new Text(grpAutre, SWT.BORDER);
						tfCODE_B_I_C.setLayoutData(tfCODE_B_I_CLData);
					} else {					
					fieldCODE_B_I_C = new DecoratedField(grpAutre, SWT.BORDER, new TextControlCreator());
					tfCODE_B_I_C = (Text)fieldCODE_B_I_C.getControl();
					fieldCODE_B_I_C.getLayoutControl().setLayoutData(tfCODE_B_I_CLData);
					}
				}
				
				{
					laNOM_COMPTE = new Label(grpAutre, SWT.NONE);
					laNOM_COMPTE.setText("Nom du compte");
				}
				{
					GridData tfNOM_COMPTELData = new GridData();
					tfNOM_COMPTELData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
					tfNOM_COMPTELData.grabExcessHorizontalSpace = true;
					tfNOM_COMPTELData.widthHint = 156;
					tfNOM_COMPTELData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfNOM_COMPTE = new Text(grpAutre, SWT.BORDER);
						tfNOM_COMPTE.setLayoutData(tfNOM_COMPTELData);
					} else {					
					fieldNOM_COMPTE = new DecoratedField(grpAutre, SWT.BORDER, new TextControlCreator());
					tfNOM_COMPTE = (Text)fieldNOM_COMPTE.getControl();
					fieldNOM_COMPTE.getLayoutControl().setLayoutData(tfNOM_COMPTELData);
					}
				}
				
//				paCorpsFormulaire
//					.setTabList(new org.eclipse.swt.widgets.Control[] {
//							tfCODE_TIERS, tfCODE_COMPTA, tfNOM_TIERS,
//							tfPRENOM_TIERS, tfSURNOM_TIERS, composite2,
//							tfCODE_T_CIVILITE, tfCODE_ENTREPRISE, tfCODE_BANQUE, tfCOMPTE,
//							tfLIBL_COMMENTAIRE, tfCODE_BANQUE, tfEntite, composite3 });
			}
			
			this.setLayout(new GridLayout());
			GridLayout compositeFormLayout = new GridLayout();
			compositeFormLayout.numColumns = 2;
			this.setSize(818, 904);
			GridData paGrilleLData = new GridData();
			paGrilleLData.verticalAlignment = GridData.FILL;
			paGrilleLData.horizontalAlignment = GridData.FILL;
			paGrilleLData.grabExcessHorizontalSpace = true;
			paGrilleLData.grabExcessVerticalSpace = true;
			super.getCompositeForm().setLayout(compositeFormLayout);
			GridData paFomulaireLData = new GridData();
			paFomulaireLData.verticalAlignment = GridData.FILL;
			paFomulaireLData.grabExcessVerticalSpace = true;
			paFomulaireLData.horizontalAlignment = GridData.FILL;
			super.getPaGrille().setLayoutData(paGrilleLData);
			GridData compositeFormLData = new GridData();
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

	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public Label getLaNOM_BANQUE() {
		return laNOM_BANQUE;
	}

	public void setLaNOM_BANQUE(Label laNOM_BANQUE) {
		this.laNOM_BANQUE = laNOM_BANQUE;
	}

	public Label getLaCOMPTE() {
		return laCOMPTE;
	}

	public void setLaCOMPTE(Label laCOMPTE) {
		this.laCOMPTE = laCOMPTE;
	}

	public Label getLaCODE_BANQUE() {
		return laCODE_BANQUE;
	}

	public void setLaCODE_BANQUE(Label laCODE_BANQUE) {
		this.laCODE_BANQUE = laCODE_BANQUE;
	}

	public Label getLaCODE_GUICHET() {
		return laCODE_GUICHET;
	}

	public void setLaCODE_GUICHET(Label laCODE_GUICHET) {
		this.laCODE_GUICHET = laCODE_GUICHET;
	}

	public Label getLaCLE_RIB() {
		return laCLE_RIB;
	}

	public void setLaCLE_RIB(Label laCLE_RIB) {
		this.laCLE_RIB = laCLE_RIB;
	}

	public Label getLaADRESSE1_BANQUE() {
		return laADRESSE1_BANQUE;
	}

	public void setLaADRESSE1_BANQUE(Label laADRESSE1_BANQUE) {
		this.laADRESSE1_BANQUE = laADRESSE1_BANQUE;
	}

	public Label getLaADRESSE2_BANQUE() {
		return laADRESSE2_BANQUE;
	}

	public void setLaADRESSE2_BANQUE(Label laADRESSE2_BANQUE) {
		this.laADRESSE2_BANQUE = laADRESSE2_BANQUE;
	}

	public Label getLaCP_BANQUE() {
		return laCP_BANQUE;
	}

	public void setLaCP_BANQUE(Label laCP_BANQUE) {
		this.laCP_BANQUE = laCP_BANQUE;
	}

	public Label getLaVILLE_BANQUE() {
		return laVILLE_BANQUE;
	}

	public void setLaVILLE_BANQUE(Label laVILLE_BANQUE) {
		this.laVILLE_BANQUE = laVILLE_BANQUE;
	}

	public Label getLaIBAN() {
		return laIBAN;
	}

	public void setLaIBAN(Label laIBAN) {
		this.laIBAN = laIBAN;
	}

	public Label getLaCODE_B_I_C() {
		return laCODE_B_I_C;
	}

	public void setLaCODE_B_I_C(Label laCODE_B_I_C) {
		this.laCODE_B_I_C = laCODE_B_I_C;
	}

	public Label getLaTITULAIRE() {
		return laTITULAIRE;
	}

	public void setLaTITULAIRE(Label laTITULAIRE) {
		this.laTITULAIRE = laTITULAIRE;
	}

	public Text getTfNOM_BANQUE() {
		return tfNOM_BANQUE;
	}

	public void setTfNOM_BANQUE(Text tfNOM_BANQUE) {
		this.tfNOM_BANQUE = tfNOM_BANQUE;
	}

	public Text getTfCOMPTE() {
		return tfCOMPTE;
	}

	public void setTfCOMPTE(Text tfCOMPTE) {
		this.tfCOMPTE = tfCOMPTE;
	}

	public Text getTfCODE_BANQUE() {
		return tfCODE_BANQUE;
	}

	public void setTfCODE_BANQUE(Text tfCODE_BANQUE) {
		this.tfCODE_BANQUE = tfCODE_BANQUE;
	}

	public Text getTfCODE_GUICHET() {
		return tfCODE_GUICHET;
	}

	public void setTfCODE_GUICHET(Text tfCODE_GUICHET) {
		this.tfCODE_GUICHET = tfCODE_GUICHET;
	}

	public Text getTfCLE_RIB() {
		return tfCLE_RIB;
	}

	public void setTfCLE_RIB(Text tfCLE_RIB) {
		this.tfCLE_RIB = tfCLE_RIB;
	}


	public Text getTfADRESSE1_BANQUE() {
		return tfADRESSE1_BANQUE;
	}

	public void setTfADRESSE1_BANQUE(Text tfADRESSE1_BANQUE) {
		this.tfADRESSE1_BANQUE = tfADRESSE1_BANQUE;
	}

	public Text getTfADRESSE2_BANQUE() {
		return tfADRESSE2_BANQUE;
	}

	public void setTfADRESSE2_BANQUE(Text tfADRESSE2_BANQUE) {
		this.tfADRESSE2_BANQUE = tfADRESSE2_BANQUE;
	}

	public Text getTfCP_BANQUE() {
		return tfCP_BANQUE;
	}

	public void setTfCP_BANQUE(Text tfCP_BANQUE) {
		this.tfCP_BANQUE = tfCP_BANQUE;
	}

	public Text getTfVILLE_BANQUE() {
		return tfVILLE_BANQUE;
	}

	public void setTfVILLE_BANQUE(Text tfVILLE_BANQUE) {
		this.tfVILLE_BANQUE = tfVILLE_BANQUE;
	}

	public Text getTfIBAN() {
		return tfIBAN;
	}

	public void setTfIBAN(Text tfIBAN) {
		this.tfIBAN = tfIBAN;
	}

	public Text getTfCODE_B_I_C() {
		return tfCODE_B_I_C;
	}

	public void setTfCODE_B_I_C(Text tfCODE_B_I_C) {
		this.tfCODE_B_I_C = tfCODE_B_I_C;
	}

	public Text getTfTITULAIRE() {
		return tfTITULAIRE;
	}

	public void setTfTITULAIRE(Text tfTITULAIRE) {
		this.tfTITULAIRE = tfTITULAIRE;
	}

	public DecoratedField getFieldNOM_BANQUE() {
		return fieldNOM_BANQUE;
	}

	public void setFieldNOM_BANQUE(DecoratedField fieldNOM_BANQUE) {
		this.fieldNOM_BANQUE = fieldNOM_BANQUE;
	}

	public DecoratedField getFieldCOMPTE() {
		return fieldCOMPTE;
	}

	public void setFieldCOMPTE(DecoratedField fieldCOMPTE) {
		this.fieldCOMPTE = fieldCOMPTE;
	}

	public DecoratedField getFieldCODE_BANQUE() {
		return fieldCODE_BANQUE;
	}

	public void setFieldCODE_BANQUE(DecoratedField fieldCODE_BANQUE) {
		this.fieldCODE_BANQUE = fieldCODE_BANQUE;
	}

	public DecoratedField getFieldCODE_GUICHET() {
		return fieldCODE_GUICHET;
	}

	public void setFieldCODE_GUICHET(DecoratedField fieldCODE_GUICHET) {
		this.fieldCODE_GUICHET = fieldCODE_GUICHET;
	}

	public DecoratedField getFieldCLE_RIB() {
		return fieldCLE_RIB;
	}

	public void setFieldCLE_RIB(DecoratedField fieldCLE_RIB) {
		this.fieldCLE_RIB = fieldCLE_RIB;
	}


	public DecoratedField getFieldADRESSE1_BANQUE() {
		return fieldADRESSE1_BANQUE;
	}

	public void setFieldADRESSE1_BANQUE(DecoratedField fieldADRESSE1_BANQUE) {
		this.fieldADRESSE1_BANQUE = fieldADRESSE1_BANQUE;
	}

	public DecoratedField getFieldADRESSE2_BANQUE() {
		return fieldADRESSE2_BANQUE;
	}

	public void setFieldADRESSE2_BANQUE(DecoratedField fieldADRESSE2_BANQUE) {
		this.fieldADRESSE2_BANQUE = fieldADRESSE2_BANQUE;
	}

	public DecoratedField getFieldCP_BANQUE() {
		return fieldCP_BANQUE;
	}

	public void setFieldCP_BANQUE(DecoratedField fieldCP_BANQUE) {
		this.fieldCP_BANQUE = fieldCP_BANQUE;
	}

	public DecoratedField getFieldVILLE_BANQUE() {
		return fieldVILLE_BANQUE;
	}

	public void setFieldVILLE_BANQUE(DecoratedField fieldVILLE_BANQUE) {
		this.fieldVILLE_BANQUE = fieldVILLE_BANQUE;
	}

	public DecoratedField getFieldIBAN() {
		return fieldIBAN;
	}

	public void setFieldIBAN(DecoratedField fieldIBAN) {
		this.fieldIBAN = fieldIBAN;
	}

	public DecoratedField getFieldCODE_B_I_C() {
		return fieldCODE_B_I_C;
	}

	public void setFieldCODE_B_I_C(DecoratedField fieldCODE_B_I_C) {
		this.fieldCODE_B_I_C = fieldCODE_B_I_C;
	}

	public DecoratedField getFieldTITULAIRE() {
		return fieldTITULAIRE;
	}

	public void setFieldTITULAIRE(DecoratedField fieldTITULAIRE) {
		this.fieldTITULAIRE = fieldTITULAIRE;
	}

	public boolean isDecore() {
		return decore;
	}

	public Label getLaCODE_T_BANQUE() {
		return laCODE_T_BANQUE;
	}

	public void setLaCODE_T_BANQUE(Label laCODE_T_BANQUE) {
		this.laCODE_T_BANQUE = laCODE_T_BANQUE;
	}

	public Text getTfCODE_T_BANQUE() {
		return tfCODE_T_BANQUE;
	}

	public void setTfCODE_T_BANQUE(Text tfCODE_T_BANQUE) {
		this.tfCODE_T_BANQUE = tfCODE_T_BANQUE;
	}

	public DecoratedField getFieldCODE_T_BANQUE() {
		return fieldCODE_T_BANQUE;
	}

	public void setFieldCODE_T_BANQUE(DecoratedField fieldCODE_T_BANQUE) {
		this.fieldCODE_T_BANQUE = fieldCODE_T_BANQUE;
	}

	public Text getTfCPTCOMPTABLE() {
		return tfCPTCOMPTABLE;
	}

	public DecoratedField getFieldCPTCOMPTABLE() {
		return fieldCPTCOMPTABLE;
	}

	public Label getLaCPTCOMPTABLE() {
		return laCPTCOMPTABLE;
	}

	public Group getGrpGeneral() {
		return grpGeneral;
	}

	public Group getGrpDomiciliation() {
		return grpDomiciliation;
	}

	public Group getGrpRIB() {
		return grpRIB;
	}

	public Group getGrpAutre() {
		return grpAutre;
	}

	public Label getLaNOM_COMPTE() {
		return laNOM_COMPTE;
	}

	public Text getTfNOM_COMPTE() {
		return tfNOM_COMPTE;
	}

	public DecoratedField getFieldNOM_COMPTE() {
		return fieldNOM_COMPTE;
	}


}

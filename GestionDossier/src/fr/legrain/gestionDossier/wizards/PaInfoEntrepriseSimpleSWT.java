package fr.legrain.gestionDossier.wizards;

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
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.resource.SWTResourceManager;

import fr.legrain.lib.gui.fieldassist.CDateTimeControlCreator;
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
public class PaInfoEntrepriseSimpleSWT extends fr.legrain.lib.gui.DefaultFrameBoutonSWT {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	private Composite paCorpsFormulaire;
	private Label laNOM_INFO_ENTREPRISE;
	private Label laADRESSE1_INFO_ENTREPRISE;
	private Label laRCS_INFO_ENTREPRISE;
	private Label laTVA_INTRA_INFO_ENTREPRISE;
	private Label laCODEXO_INFO_ENTREPRISE;
	private Label laDATEFIN_INFO_ENTREPRISE;
	private Label laDATEDEB_INFO_ENTREPRISE;
	private Label laWEB_INFO_ENTREPRISE;
	private Label laEMAIL_INFO_ENTREPRISE;
	private Label laCAPITAL_INFO_ENTREPRISE;
	private Label laADRESSE2_INFO_ENTREPRISE;
	private Label laAPE_INFO_ENTREPRISE;
	private Label laSIRET_INFO_ENTREPRISE;
	private Label laFAX_INFO_ENTREPRISE;
	private Label laTEL_INFO_ENTREPRISE;
	private Label laPAYS_INFO_ENTREPRISE;
	private Label laVILLE_INFO_ENTREPRISE;
	private Label laCODEPOSTAL_INFO_ENTREPRISE;
	private Label laADRESSE3_INFO_ENTREPRISE;
	
	private Text tfADRESSE2_INFO_ENTREPRISE;
	private Text tfADRESSE1_INFO_ENTREPRISE;
	private Text tfNOM_INFO_ENTREPRISE;
	//private Text tfDATEFIN_INFO_ENTREPRISE;
	private Text tfEMAIL_INFO_ENTREPRISE;
	private Text tfCODEXO_INFO_ENTREPRISE;
	private Text tfCAPITAL_INFO_ENTREPRISE;
	private Text tfWEB_INFO_ENTREPRISE;
	private Text tfTVA_INTRA_INFO_ENTREPRISE;
	//private Text tfDATEDEB_INFO_ENTREPRISE;
	private Text tfRCS_INFO_ENTREPRISE;
	private Text tfAPE_INFO_ENTREPRISE;
	private Text tfSIRET_INFO_ENTREPRISE;
	private Text tfFAX_INFO_ENTREPRISE;
	private Text tfTEL_INFO_ENTREPRISE;
	private Text tfPAYS_INFO_ENTREPRISE;
	private Text tfVILLE_INFO_ENTREPRISE;
	private Text tfCODEPOSTAL_INFO_ENTREPRISE;
	private Text tfADRESSE3_INFO_ENTREPRISE;
	private Button btnDetail;
	
	private DateTime dateTimeDATEDEB_INFO_ENTREPRISE;
	private DateTime dateTimeDATEFIN_INFO_ENTREPRISE;
	
	private Group groupDate;
	private Button cbActiveDateDebReg;
	private Button cbActiveDateDebRel;
	
	private Label laDATEDEB_REG_INFO_ENTREPRISE;
	private Label laDATEDEB_REL_INFO_ENTREPRISE;
	private DateTime dateTimeDATEDEB_REG_INFO_ENTREPRISE;
	private DateTime dateTimeDATEDEB_REL_INFO_ENTREPRISE;
	
	private DecoratedField fieldADRESSE2_INFO_ENTREPRISE;
	private DecoratedField fieldADRESSE1_INFO_ENTREPRISE;
	private DecoratedField fieldNOM_INFO_ENTREPRISE;
	private DecoratedField fieldDATEFIN_INFO_ENTREPRISE;
	private DecoratedField fieldEMAIL_INFO_ENTREPRISE;
	private DecoratedField fieldCODEXO_INFO_ENTREPRISE;
	private DecoratedField fieldCAPITAL_INFO_ENTREPRISE;
	private DecoratedField fieldWEB_INFO_ENTREPRISE;
	private DecoratedField fieldTVA_INTRA_INFO_ENTREPRISE;
	private DecoratedField fieldDATEDEB_INFO_ENTREPRISE;
	private DecoratedField fieldRCS_INFO_ENTREPRISE;
	private DecoratedField fieldAPE_INFO_ENTREPRISE;
	private DecoratedField fieldSIRET_INFO_ENTREPRISE;
	private DecoratedField fieldFAX_INFO_ENTREPRISE;
	private DecoratedField fieldTEL_INFO_ENTREPRISE;
	private DecoratedField fieldPAYS_INFO_ENTREPRISE;
	private DecoratedField fieldVILLE_INFO_ENTREPRISE;
	private DecoratedField fieldCODEPOSTAL_INFO_ENTREPRISE;
	private DecoratedField fieldADRESSE3_INFO_ENTREPRISE;
	
	private DecoratedField fieldDATEDEB_REG_INFO_ENTREPRISE;
	private DecoratedField fieldDATEDEB_REL_INFO_ENTREPRISE;

	boolean decore = LgrMess.isDECORE();
//	boolean decore = false;
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
		PaInfoEntrepriseSimpleSWT inst = new PaInfoEntrepriseSimpleSWT(shell, SWT.NULL);
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

	public PaInfoEntrepriseSimpleSWT(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {

			this.setLayout(new GridLayout());
			this.setSize(646, 521);

			GridData compositeFormLData = new GridData();
			compositeFormLData.grabExcessHorizontalSpace = true;
			compositeFormLData.verticalAlignment = GridData.FILL;
			compositeFormLData.horizontalAlignment = GridData.FILL;
			compositeFormLData.grabExcessVerticalSpace = true;
			GridData laMessageLData = new GridData();
			laMessageLData.grabExcessHorizontalSpace = true;
			laMessageLData.horizontalAlignment = GridData.FILL;
			GridLayout compositeFormLayout = new GridLayout();
			compositeFormLayout.makeColumnsEqualWidth = true;
			super.getCompositeForm().setLayoutData(compositeFormLData);
			super.getLaMessage().setLayoutData(laMessageLData);
			GridData paBtnLData = new GridData();
			paBtnLData.horizontalAlignment = GridData.CENTER;
			super.getCompositeForm().setLayout(compositeFormLayout);
			super.getPaBtn().setLayoutData(paBtnLData);
			{
				paCorpsFormulaire = new Composite(
					super.getCompositeForm(),
					SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 6;
				GridData composite1LData = new GridData();
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.grabExcessVerticalSpace = true;
				composite1LData.horizontalAlignment = GridData.FILL;
				paCorpsFormulaire.setLayoutData(composite1LData);
				paCorpsFormulaire.setLayout(composite1Layout);
				{
					laNOM_INFO_ENTREPRISE = new Label(paCorpsFormulaire, SWT.NONE);
					GridData laNOM_INFO_ENTREPRISELData = new GridData();
					laNOM_INFO_ENTREPRISELData.widthHint = 56;
					laNOM_INFO_ENTREPRISE.setLayoutData(laNOM_INFO_ENTREPRISELData);
					laNOM_INFO_ENTREPRISE.setText("Nom");
				}

				{
					GridData tfNOM_INFO_ENTREPRISELData = new GridData();
					tfNOM_INFO_ENTREPRISELData.horizontalSpan = 5;
					tfNOM_INFO_ENTREPRISELData.widthHint = 184;
					tfNOM_INFO_ENTREPRISELData.grabExcessHorizontalSpace = true;
//					tfNOM_INFO_ENTREPRISE = new Text(paCorpsFormulaire, SWT.BORDER);
//					tfNOM_INFO_ENTREPRISE.setLayoutData(tfNOM_INFO_ENTREPRISELData);

					fieldNOM_INFO_ENTREPRISE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfNOM_INFO_ENTREPRISE = (Text)fieldNOM_INFO_ENTREPRISE.getControl();
					fieldNOM_INFO_ENTREPRISE.getLayoutControl().setLayoutData(tfNOM_INFO_ENTREPRISELData);
				}
				{
					laADRESSE1_INFO_ENTREPRISE = new Label(paCorpsFormulaire, SWT.NONE);
					GridData laADRESSE1_INFO_ENTREPRISELData = new GridData();
					laADRESSE1_INFO_ENTREPRISELData.horizontalSpan = 2;
					laADRESSE1_INFO_ENTREPRISE.setText("Adresse 1");
				}
				{
					GridData tfADRESSE1_INFO_ENTREPRISELData = new GridData();
					tfADRESSE1_INFO_ENTREPRISELData.horizontalSpan = 5;
					tfADRESSE1_INFO_ENTREPRISELData.horizontalAlignment = GridData.FILL;
					tfADRESSE1_INFO_ENTREPRISELData.grabExcessHorizontalSpace = true;
//					tfADRESSE1_INFO_ENTREPRISE = new Text(paCorpsFormulaire, SWT.BORDER);
//					tfADRESSE1_INFO_ENTREPRISE.setLayoutData(tfADRESSE1_INFO_ENTREPRISELData);

					fieldADRESSE1_INFO_ENTREPRISE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfADRESSE1_INFO_ENTREPRISE = (Text)fieldADRESSE1_INFO_ENTREPRISE.getControl();
					fieldADRESSE1_INFO_ENTREPRISE.getLayoutControl().setLayoutData(tfADRESSE1_INFO_ENTREPRISELData);
				}
				{
					laADRESSE2_INFO_ENTREPRISE = new Label(paCorpsFormulaire, SWT.NONE);
					GridData laADRESSE2_INFO_ENTREPRISELData = new GridData();
					laADRESSE2_INFO_ENTREPRISE.setLayoutData(laADRESSE2_INFO_ENTREPRISELData);
					laADRESSE2_INFO_ENTREPRISE.setText("Adresse 2");
				}
				{
					GridData tfADRESSE2_INFO_ENTREPRISELData = new GridData();
					tfADRESSE2_INFO_ENTREPRISELData.horizontalSpan = 5;
					tfADRESSE2_INFO_ENTREPRISELData.horizontalAlignment = GridData.FILL;
					tfADRESSE2_INFO_ENTREPRISELData.grabExcessHorizontalSpace = true;
//					tfADRESSE2_INFO_ENTREPRISE = new Text(paCorpsFormulaire, SWT.BORDER);
//					tfADRESSE2_INFO_ENTREPRISE.setLayoutData(tfADRESSE2_INFO_ENTREPRISELData);

					fieldADRESSE2_INFO_ENTREPRISE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfADRESSE2_INFO_ENTREPRISE = (Text)fieldADRESSE2_INFO_ENTREPRISE.getControl();
					fieldADRESSE2_INFO_ENTREPRISE.getLayoutControl().setLayoutData(tfADRESSE2_INFO_ENTREPRISELData);
				}
				{
					laADRESSE3_INFO_ENTREPRISE = new Label(paCorpsFormulaire, SWT.NONE);
					GridData laADRESSE3_INFO_ENTREPRISELData = new GridData();
					laADRESSE3_INFO_ENTREPRISE.setLayoutData(laADRESSE3_INFO_ENTREPRISELData);
					laADRESSE3_INFO_ENTREPRISE.setText("Adresse 3");
				}
				{
					GridData tfADRESSE3_INFO_ENTREPRISELData = new GridData();
					tfADRESSE3_INFO_ENTREPRISELData.horizontalSpan = 5;
					tfADRESSE3_INFO_ENTREPRISELData.horizontalAlignment = GridData.FILL;
					tfADRESSE3_INFO_ENTREPRISELData.grabExcessHorizontalSpace = true;
//					tfADRESSE3_INFO_ENTREPRISE = new Text(paCorpsFormulaire, SWT.BORDER);
//					tfADRESSE3_INFO_ENTREPRISE.setLayoutData(tfADRESSE3_INFO_ENTREPRISELData);

					fieldADRESSE3_INFO_ENTREPRISE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfADRESSE3_INFO_ENTREPRISE = (Text)fieldADRESSE3_INFO_ENTREPRISE.getControl();
					fieldADRESSE3_INFO_ENTREPRISE.getLayoutControl().setLayoutData(tfADRESSE3_INFO_ENTREPRISELData);
				}
				{
					laCODEPOSTAL_INFO_ENTREPRISE = new Label(paCorpsFormulaire, SWT.NONE);
					GridData laCODEPOSTAL_INFO_ENTREPRISELData = new GridData();
					laCODEPOSTAL_INFO_ENTREPRISE.setLayoutData(laCODEPOSTAL_INFO_ENTREPRISELData);
					laCODEPOSTAL_INFO_ENTREPRISE.setText("Code postal");
				}
				{
					GridData tfCODEPOSTAL_INFO_ENTREPRISELData = new GridData();
					tfCODEPOSTAL_INFO_ENTREPRISELData.horizontalSpan = 5;
					tfCODEPOSTAL_INFO_ENTREPRISELData.widthHint = 78;
					tfCODEPOSTAL_INFO_ENTREPRISELData.grabExcessHorizontalSpace = true;
//					tfCODEPOSTAL_INFO_ENTREPRISE = new Text(paCorpsFormulaire, SWT.BORDER);
//					tfCODEPOSTAL_INFO_ENTREPRISE.setLayoutData(tfCODEPOSTAL_INFO_ENTREPRISELData);

					fieldCODEPOSTAL_INFO_ENTREPRISE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfCODEPOSTAL_INFO_ENTREPRISE = (Text)fieldCODEPOSTAL_INFO_ENTREPRISE.getControl();
					fieldCODEPOSTAL_INFO_ENTREPRISE.getLayoutControl().setLayoutData(tfCODEPOSTAL_INFO_ENTREPRISELData);
				}
				{
					laVILLE_INFO_ENTREPRISE = new Label(paCorpsFormulaire, SWT.NONE);
					laVILLE_INFO_ENTREPRISE.setText("Ville");
					GridData laVILLE_INFO_ENTREPRISELData = new GridData();
					laVILLE_INFO_ENTREPRISE.setLayoutData(laVILLE_INFO_ENTREPRISELData);
				}
				{
					GridData tfVILLE_INFO_ENTREPRISELData = new GridData();
					tfVILLE_INFO_ENTREPRISELData.widthHint = 135;
					tfVILLE_INFO_ENTREPRISELData.horizontalSpan = 3;
					tfVILLE_INFO_ENTREPRISELData.grabExcessHorizontalSpace = true;
//					tfVILLE_INFO_ENTREPRISE = new Text(paCorpsFormulaire, SWT.BORDER);
//					tfVILLE_INFO_ENTREPRISE.setLayoutData(tfVILLE_INFO_ENTREPRISELData);

					fieldVILLE_INFO_ENTREPRISE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfVILLE_INFO_ENTREPRISE = (Text)fieldVILLE_INFO_ENTREPRISE.getControl();
					fieldVILLE_INFO_ENTREPRISE.getLayoutControl().setLayoutData(tfVILLE_INFO_ENTREPRISELData);
				}
				{
					laDATEDEB_INFO_ENTREPRISE = new Label(paCorpsFormulaire, SWT.LEFT);
					laDATEDEB_INFO_ENTREPRISE.setText("Date début");
					GridData laDATEDEB_INFO_ENTREPRISELData = new GridData();
					laDATEDEB_INFO_ENTREPRISELData.grabExcessHorizontalSpace = true;
					laDATEDEB_INFO_ENTREPRISELData.horizontalIndent = 120;
					laDATEDEB_INFO_ENTREPRISELData.horizontalAlignment = GridData.FILL;
					laDATEDEB_INFO_ENTREPRISE.setLayoutData(laDATEDEB_INFO_ENTREPRISELData);
				}
				{
					GridData tfDATEDEB_INFO_ENTREPRISELData = new GridData();
					tfDATEDEB_INFO_ENTREPRISELData.widthHint = 112;
					tfDATEDEB_INFO_ENTREPRISELData.grabExcessHorizontalSpace = true;
//					tfDATEDEB_INFO_ENTREPRISE = new Text(paCorpsFormulaire, SWT.BORDER);
//					tfDATEDEB_INFO_ENTREPRISE.setLayoutData(tfDATEDEB_INFO_ENTREPRISELData);
//					dateTimeDATEDEB_INFO_ENTREPRISE = new CDateTime(paCorpsFormulaire,CDT.BORDER | CDT.DROP_DOWN);
//					dateTimeDATEDEB_INFO_ENTREPRISE.setLayoutData(tfDATEDEB_INFO_ENTREPRISELData);
					

					fieldDATEDEB_INFO_ENTREPRISE = new DecoratedField(paCorpsFormulaire, SWT.BORDER | SWT.DROP_DOWN, new DateTimeControlCreator());
					dateTimeDATEDEB_INFO_ENTREPRISE = (DateTime)fieldDATEDEB_INFO_ENTREPRISE.getControl();
					fieldDATEDEB_INFO_ENTREPRISE.getLayoutControl().setLayoutData(tfDATEDEB_INFO_ENTREPRISELData);
//					dateTimeDATEDEB_INFO_ENTREPRISE.setPattern("dd/MM/yyyy");
				}
				{
					laPAYS_INFO_ENTREPRISE = new Label(paCorpsFormulaire, SWT.NONE);
					GridData laPAYS_INFO_ENTREPRISELData = new GridData();
					laPAYS_INFO_ENTREPRISE.setLayoutData(laPAYS_INFO_ENTREPRISELData);
					laPAYS_INFO_ENTREPRISE.setText("Pays");
				}
				{
					GridData tfPAYS_INFO_ENTREPRISELData = new GridData();
					tfPAYS_INFO_ENTREPRISELData.horizontalSpan = 3;
					tfPAYS_INFO_ENTREPRISELData.widthHint = 135;
					tfPAYS_INFO_ENTREPRISELData.grabExcessHorizontalSpace = true;
//					tfPAYS_INFO_ENTREPRISE = new Text(paCorpsFormulaire, SWT.BORDER);
//					tfPAYS_INFO_ENTREPRISE.setLayoutData(tfPAYS_INFO_ENTREPRISELData);

					fieldPAYS_INFO_ENTREPRISE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfPAYS_INFO_ENTREPRISE = (Text)fieldPAYS_INFO_ENTREPRISE.getControl();
					fieldPAYS_INFO_ENTREPRISE.getLayoutControl().setLayoutData(tfPAYS_INFO_ENTREPRISELData);
				}
				{
					laDATEFIN_INFO_ENTREPRISE = new Label(paCorpsFormulaire, SWT.LEFT);
					laDATEFIN_INFO_ENTREPRISE.setText("Date fin");
					GridData laDATEFIN_INFO_ENTREPRISELData = new GridData();
					laDATEFIN_INFO_ENTREPRISELData.horizontalIndent = 120;
					laDATEFIN_INFO_ENTREPRISELData.grabExcessHorizontalSpace = true;
					laDATEFIN_INFO_ENTREPRISELData.horizontalAlignment = GridData.FILL;
					laDATEFIN_INFO_ENTREPRISE.setLayoutData(laDATEFIN_INFO_ENTREPRISELData);
				}
				{
					GridData tfDATEFIN_INFO_ENTREPRISELData = new GridData();
					tfDATEFIN_INFO_ENTREPRISELData.widthHint = 112;
					tfDATEFIN_INFO_ENTREPRISELData.grabExcessHorizontalSpace = true;
//					tfDATEFIN_INFO_ENTREPRISE = new Text(paCorpsFormulaire, SWT.BORDER);
//					tfDATEFIN_INFO_ENTREPRISE.setLayoutData(tfDATEFIN_INFO_ENTREPRISELData);
//					dateTimeDATEFIN_INFO_ENTREPRISE = new CDateTime(paCorpsFormulaire,CDT.BORDER | CDT.DROP_DOWN);
//					dateTimeDATEFIN_INFO_ENTREPRISE.setLayoutData(tfDATEFIN_INFO_ENTREPRISELData);
					
					fieldDATEFIN_INFO_ENTREPRISE = new DecoratedField(paCorpsFormulaire, SWT.BORDER | SWT.DROP_DOWN, new DateTimeControlCreator());
					dateTimeDATEFIN_INFO_ENTREPRISE = (DateTime)fieldDATEFIN_INFO_ENTREPRISE.getControl();
					fieldDATEFIN_INFO_ENTREPRISE.getLayoutControl().setLayoutData(tfDATEFIN_INFO_ENTREPRISELData);
//					dateTimeDATEFIN_INFO_ENTREPRISE.setPattern("dd/MM/yyyy");
				}
			
				{
					laTEL_INFO_ENTREPRISE = new Label(paCorpsFormulaire, SWT.NONE);
					GridData laTEL_INFO_ENTREPRISELData = new GridData();
					laTEL_INFO_ENTREPRISE.setLayoutData(laTEL_INFO_ENTREPRISELData);
					laTEL_INFO_ENTREPRISE.setText("Téléphone");
				}
				{
					GridData tfTEL_INFO_ENTREPRISELData = new GridData();
					tfTEL_INFO_ENTREPRISELData.horizontalSpan = 3;
					tfTEL_INFO_ENTREPRISELData.widthHint = 135;
					tfTEL_INFO_ENTREPRISELData.grabExcessHorizontalSpace = true;
//					tfTEL_INFO_ENTREPRISE = new Text(paCorpsFormulaire, SWT.BORDER);
//					tfTEL_INFO_ENTREPRISE.setLayoutData(tfTEL_INFO_ENTREPRISELData);

					fieldTEL_INFO_ENTREPRISE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfTEL_INFO_ENTREPRISE = (Text)fieldTEL_INFO_ENTREPRISE.getControl();
					fieldTEL_INFO_ENTREPRISE.getLayoutControl().setLayoutData(tfTEL_INFO_ENTREPRISELData);
				}
				{
					laCODEXO_INFO_ENTREPRISE = new Label(paCorpsFormulaire, SWT.LEFT);
					laCODEXO_INFO_ENTREPRISE.setText("Code exercice");
					GridData laCODEXO_INFO_ENTREPRISELData = new GridData();
					laCODEXO_INFO_ENTREPRISELData.horizontalIndent = 120;
					laCODEXO_INFO_ENTREPRISELData.grabExcessHorizontalSpace = true;
					laCODEXO_INFO_ENTREPRISELData.horizontalAlignment = GridData.FILL;
					laCODEXO_INFO_ENTREPRISE.setLayoutData(laCODEXO_INFO_ENTREPRISELData);
				}
				{
					GridData tfCODEXO_INFO_ENTREPRISELData = new GridData();
					tfCODEXO_INFO_ENTREPRISELData.widthHint = 112;
					tfCODEXO_INFO_ENTREPRISELData.grabExcessHorizontalSpace = true;
//					tfCODEXO_INFO_ENTREPRISE = new Text(paCorpsFormulaire, SWT.BORDER);
//					tfCODEXO_INFO_ENTREPRISE.setLayoutData(tfCODEXO_INFO_ENTREPRISELData);

					fieldCODEXO_INFO_ENTREPRISE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfCODEXO_INFO_ENTREPRISE = (Text)fieldCODEXO_INFO_ENTREPRISE.getControl();
					fieldCODEXO_INFO_ENTREPRISE.getLayoutControl().setLayoutData(tfCODEXO_INFO_ENTREPRISELData);
				}
				{
					laFAX_INFO_ENTREPRISE = new Label(paCorpsFormulaire, SWT.NONE);
					laFAX_INFO_ENTREPRISE.setText("Fax");
					GridData laFAX_INFO_ENTREPRISELData = new GridData();
					laFAX_INFO_ENTREPRISE.setLayoutData(laFAX_INFO_ENTREPRISELData);
				}
				{
					GridData tfFAX_INFO_ENTREPRISELData = new GridData();
					tfFAX_INFO_ENTREPRISELData.horizontalSpan = 5;
					tfFAX_INFO_ENTREPRISELData.widthHint = 135;
					tfFAX_INFO_ENTREPRISELData.grabExcessHorizontalSpace = true;
//					tfFAX_INFO_ENTREPRISE = new Text(paCorpsFormulaire, SWT.BORDER);
//					tfFAX_INFO_ENTREPRISE.setLayoutData(tfFAX_INFO_ENTREPRISELData);

					fieldFAX_INFO_ENTREPRISE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfFAX_INFO_ENTREPRISE = (Text)fieldFAX_INFO_ENTREPRISE.getControl();
					fieldFAX_INFO_ENTREPRISE.getLayoutControl().setLayoutData(tfFAX_INFO_ENTREPRISELData);
				}
				{
					laSIRET_INFO_ENTREPRISE = new Label(paCorpsFormulaire, SWT.NONE);
					laSIRET_INFO_ENTREPRISE.setText("N° Siret");
					GridData laSIRET_INFO_ENTREPRISELData = new GridData();
					laSIRET_INFO_ENTREPRISE.setLayoutData(laSIRET_INFO_ENTREPRISELData);
				}
				{
					GridData tfSIRET_INFO_ENTREPRISELData = new GridData();
					tfSIRET_INFO_ENTREPRISELData.horizontalSpan = 5;
					tfSIRET_INFO_ENTREPRISELData.widthHint = 184;
					tfSIRET_INFO_ENTREPRISELData.grabExcessHorizontalSpace = true;
//					tfSIRET_INFO_ENTREPRISE = new Text(paCorpsFormulaire, SWT.BORDER);
//					tfSIRET_INFO_ENTREPRISE.setLayoutData(tfSIRET_INFO_ENTREPRISELData);

					fieldSIRET_INFO_ENTREPRISE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfSIRET_INFO_ENTREPRISE = (Text)fieldSIRET_INFO_ENTREPRISE.getControl();
					fieldSIRET_INFO_ENTREPRISE.getLayoutControl().setLayoutData(tfSIRET_INFO_ENTREPRISELData);
				}
				{
					laAPE_INFO_ENTREPRISE = new Label(paCorpsFormulaire, SWT.NONE);
					laAPE_INFO_ENTREPRISE.setText("Code APE");
					GridData laAPE_INFO_ENTREPRISELData = new GridData();
					laAPE_INFO_ENTREPRISE.setLayoutData(laAPE_INFO_ENTREPRISELData);
				}
				{
					GridData tfAPE_INFO_ENTREPRISELData = new GridData();
					tfAPE_INFO_ENTREPRISELData.horizontalSpan = 5;
					tfAPE_INFO_ENTREPRISELData.widthHint = 78;
					tfAPE_INFO_ENTREPRISELData.grabExcessHorizontalSpace = true;
//					tfAPE_INFO_ENTREPRISE = new Text(paCorpsFormulaire, SWT.BORDER);
//					tfAPE_INFO_ENTREPRISE.setLayoutData(tfAPE_INFO_ENTREPRISELData);

					fieldAPE_INFO_ENTREPRISE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfAPE_INFO_ENTREPRISE = (Text)fieldAPE_INFO_ENTREPRISE.getControl();
					fieldAPE_INFO_ENTREPRISE.getLayoutControl().setLayoutData(tfAPE_INFO_ENTREPRISELData);
				}
				{
					laRCS_INFO_ENTREPRISE = new Label(paCorpsFormulaire, SWT.NONE);
					laRCS_INFO_ENTREPRISE.setText("RCS");
					GridData laRCS_INFO_ENTREPRISELData = new GridData();
					laRCS_INFO_ENTREPRISE.setLayoutData(laRCS_INFO_ENTREPRISELData);
				}
				{
					GridData tfRCS_INFO_ENTREPRISELData = new GridData();
					tfRCS_INFO_ENTREPRISELData.horizontalSpan = 5;
					tfRCS_INFO_ENTREPRISELData.widthHint = 135;
					tfRCS_INFO_ENTREPRISELData.grabExcessHorizontalSpace = true;
//					tfRCS_INFO_ENTREPRISE = new Text(paCorpsFormulaire, SWT.BORDER);
//					tfRCS_INFO_ENTREPRISE.setLayoutData(tfRCS_INFO_ENTREPRISELData);

					fieldRCS_INFO_ENTREPRISE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfRCS_INFO_ENTREPRISE = (Text)fieldRCS_INFO_ENTREPRISE.getControl();
					fieldRCS_INFO_ENTREPRISE.getLayoutControl().setLayoutData(tfRCS_INFO_ENTREPRISELData);
				}
				{
					laTVA_INTRA_INFO_ENTREPRISE = new Label(paCorpsFormulaire, SWT.NONE);
					laTVA_INTRA_INFO_ENTREPRISE.setText("N° TVA intracommunautaire");
					GridData laTVA_INTRA_INFO_ENTREPRISELData = new GridData();
					laTVA_INTRA_INFO_ENTREPRISE.setLayoutData(laTVA_INTRA_INFO_ENTREPRISELData);
				}
				{
					GridData tfTVA_INTRA_INFO_ENTREPRISELData = new GridData();
					tfTVA_INTRA_INFO_ENTREPRISELData.horizontalSpan = 5;
					tfTVA_INTRA_INFO_ENTREPRISELData.widthHint = 184;
					tfTVA_INTRA_INFO_ENTREPRISELData.grabExcessHorizontalSpace = true;
//					tfTVA_INTRA_INFO_ENTREPRISE = new Text(paCorpsFormulaire, SWT.BORDER);
//					tfTVA_INTRA_INFO_ENTREPRISE.setLayoutData(tfTVA_INTRA_INFO_ENTREPRISELData);

					fieldTVA_INTRA_INFO_ENTREPRISE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfTVA_INTRA_INFO_ENTREPRISE = (Text)fieldTVA_INTRA_INFO_ENTREPRISE.getControl();
					fieldTVA_INTRA_INFO_ENTREPRISE.getLayoutControl().setLayoutData(tfTVA_INTRA_INFO_ENTREPRISELData);
				}
				{
					laCAPITAL_INFO_ENTREPRISE = new Label(paCorpsFormulaire, SWT.NONE);
					laCAPITAL_INFO_ENTREPRISE.setText("Capital");
					GridData laCAPITAL_INFO_ENTREPRISELData = new GridData();
					laCAPITAL_INFO_ENTREPRISE.setLayoutData(laCAPITAL_INFO_ENTREPRISELData);
				}
				{
					GridData tfCAPITAL_INFO_ENTREPRISELData = new GridData();
					tfCAPITAL_INFO_ENTREPRISELData.horizontalSpan = 5;
					tfCAPITAL_INFO_ENTREPRISELData.widthHint = 78;
					tfCAPITAL_INFO_ENTREPRISELData.grabExcessHorizontalSpace = true;
//					tfCAPITAL_INFO_ENTREPRISE = new Text(paCorpsFormulaire, SWT.BORDER);
//					tfCAPITAL_INFO_ENTREPRISE.setLayoutData(tfCAPITAL_INFO_ENTREPRISELData);

					fieldCAPITAL_INFO_ENTREPRISE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfCAPITAL_INFO_ENTREPRISE = (Text)fieldCAPITAL_INFO_ENTREPRISE.getControl();
					fieldCAPITAL_INFO_ENTREPRISE.getLayoutControl().setLayoutData(tfCAPITAL_INFO_ENTREPRISELData);
				}
				{
					laEMAIL_INFO_ENTREPRISE = new Label(paCorpsFormulaire, SWT.NONE);
					laEMAIL_INFO_ENTREPRISE.setText("Adresse email");
					GridData laEMAIL_INFO_ENTREPRISELData = new GridData();
					laEMAIL_INFO_ENTREPRISE.setLayoutData(laEMAIL_INFO_ENTREPRISELData);
				}
				{
					GridData tfEMAIL_INFO_ENTREPRISELData = new GridData();
					tfEMAIL_INFO_ENTREPRISELData.horizontalSpan = 5;
					tfEMAIL_INFO_ENTREPRISELData.horizontalAlignment = GridData.FILL;
					tfEMAIL_INFO_ENTREPRISELData.grabExcessHorizontalSpace = true;
//					tfEMAIL_INFO_ENTREPRISE = new Text(paCorpsFormulaire, SWT.BORDER);
//					tfEMAIL_INFO_ENTREPRISE.setLayoutData(tfEMAIL_INFO_ENTREPRISELData);

					fieldEMAIL_INFO_ENTREPRISE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfEMAIL_INFO_ENTREPRISE = (Text)fieldEMAIL_INFO_ENTREPRISE.getControl();
					fieldEMAIL_INFO_ENTREPRISE.getLayoutControl().setLayoutData(tfEMAIL_INFO_ENTREPRISELData);
				}
				{
					laWEB_INFO_ENTREPRISE = new Label(paCorpsFormulaire, SWT.NONE);
					laWEB_INFO_ENTREPRISE.setText("Adresse Site");
					GridData laWEB_INFO_ENTREPRISELData = new GridData();
					laWEB_INFO_ENTREPRISE.setLayoutData(laWEB_INFO_ENTREPRISELData);
				}
				{
					GridData tfWEB_INFO_ENTREPRISELData = new GridData();
					tfWEB_INFO_ENTREPRISELData.horizontalSpan = 5;
					tfWEB_INFO_ENTREPRISELData.horizontalAlignment = GridData.FILL;
//					tfWEB_INFO_ENTREPRISE = new Text(paCorpsFormulaire, SWT.BORDER);
//					tfWEB_INFO_ENTREPRISE.setLayoutData(tfWEB_INFO_ENTREPRISELData);

					fieldWEB_INFO_ENTREPRISE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfWEB_INFO_ENTREPRISE = (Text)fieldWEB_INFO_ENTREPRISE.getControl();
					fieldWEB_INFO_ENTREPRISE.getLayoutControl().setLayoutData(tfWEB_INFO_ENTREPRISELData);
				}
				{
					groupDate = new Group(paCorpsFormulaire, SWT.NONE);
					groupDate.setLayout(new GridLayout(2,false));
					groupDate.setText("Règlements et relances");
					GridData groupDateLData = new GridData();
					groupDateLData.horizontalSpan = 6;
					groupDateLData.grabExcessHorizontalSpace = true;
					groupDateLData.horizontalAlignment = GridData.FILL;
					groupDate.setLayoutData(groupDateLData);
				}
				{
					cbActiveDateDebReg = new Button(groupDate, SWT.CHECK);
					cbActiveDateDebReg.setText("Modifier la date de début de gestion des règlements");
					GridData laDATEDEB_REG_INFO_ENTREPRISELData = new GridData();
					laDATEDEB_REG_INFO_ENTREPRISELData.grabExcessHorizontalSpace = true;
					laDATEDEB_REG_INFO_ENTREPRISELData.horizontalAlignment = GridData.FILL;
					laDATEDEB_REG_INFO_ENTREPRISELData.horizontalSpan = 2;
					cbActiveDateDebReg.setLayoutData(laDATEDEB_REG_INFO_ENTREPRISELData);
				}
				{
					laDATEDEB_REG_INFO_ENTREPRISE = new Label(groupDate, SWT.LEFT);
					laDATEDEB_REG_INFO_ENTREPRISE.setText("Date de début de gestion règlements");
					GridData laDATEDEB_REG_INFO_ENTREPRISELData = new GridData();
					laDATEDEB_REG_INFO_ENTREPRISELData.grabExcessHorizontalSpace = true;
//					laDATEDEB_REG_INFO_ENTREPRISELData.horizontalAlignment = GridData.FILL;
					laDATEDEB_REG_INFO_ENTREPRISE.setLayoutData(laDATEDEB_REG_INFO_ENTREPRISELData);
				}
				{
					GridData tfDATEDEB_REG_INFO_ENTREPRISELData = new GridData();
					tfDATEDEB_REG_INFO_ENTREPRISELData.widthHint = 112;
					tfDATEDEB_REG_INFO_ENTREPRISELData.grabExcessHorizontalSpace = true;

					fieldDATEDEB_REG_INFO_ENTREPRISE = new DecoratedField(groupDate, SWT.BORDER | SWT.DROP_DOWN, new DateTimeControlCreator());
					dateTimeDATEDEB_REG_INFO_ENTREPRISE = (DateTime)fieldDATEDEB_REG_INFO_ENTREPRISE.getControl();
					fieldDATEDEB_REG_INFO_ENTREPRISE.getLayoutControl().setLayoutData(tfDATEDEB_REG_INFO_ENTREPRISELData);
//					dateTimeDATEDEB_REG_INFO_ENTREPRISE.setPattern("dd/MM/yyyy");
				}
				{
					cbActiveDateDebRel = new Button(groupDate, SWT.CHECK);
					cbActiveDateDebRel.setText("Modifier la date de début de gestion des relances");
					GridData laDATEDEB_REL_INFO_ENTREPRISELData = new GridData();
					laDATEDEB_REL_INFO_ENTREPRISELData.grabExcessHorizontalSpace = true;
					laDATEDEB_REL_INFO_ENTREPRISELData.horizontalAlignment = GridData.FILL;
					laDATEDEB_REL_INFO_ENTREPRISELData.horizontalSpan = 2;
					cbActiveDateDebRel.setLayoutData(laDATEDEB_REL_INFO_ENTREPRISELData);
				}
				{
					laDATEDEB_REL_INFO_ENTREPRISE = new Label(groupDate, SWT.LEFT);
					laDATEDEB_REL_INFO_ENTREPRISE.setText("Date de début de gestion des relances");
					GridData laDATEDEB_REL_INFO_ENTREPRISELData = new GridData();
					laDATEDEB_REL_INFO_ENTREPRISELData.grabExcessHorizontalSpace = true;
					laDATEDEB_REL_INFO_ENTREPRISELData.horizontalAlignment = GridData.FILL;
					laDATEDEB_REL_INFO_ENTREPRISE.setLayoutData(laDATEDEB_REL_INFO_ENTREPRISELData);
				}
				{
					GridData tfDATEDEB_REL_INFO_ENTREPRISELData = new GridData();
					tfDATEDEB_REL_INFO_ENTREPRISELData.widthHint = 112;
					tfDATEDEB_REL_INFO_ENTREPRISELData.grabExcessHorizontalSpace = true;

					fieldDATEDEB_REL_INFO_ENTREPRISE = new DecoratedField(groupDate, SWT.BORDER | SWT.DROP_DOWN, new DateTimeControlCreator());
					dateTimeDATEDEB_REL_INFO_ENTREPRISE = (DateTime)fieldDATEDEB_REL_INFO_ENTREPRISE.getControl();
					fieldDATEDEB_REL_INFO_ENTREPRISE.getLayoutControl().setLayoutData(tfDATEDEB_REL_INFO_ENTREPRISELData);
//					dateTimeDATEDEB_REL_INFO_ENTREPRISE.setPattern("dd/MM/yyyy");
				}
				
				{
					GridData btnDetailLData = new GridData();
					btnDetailLData.horizontalSpan = 6;
					btnDetailLData.horizontalAlignment = GridData.CENTER;
					btnDetail = new Button(paCorpsFormulaire, SWT.BORDER);
					btnDetail.setText("Détails");
					btnDetail.setLayoutData(btnDetailLData);

//					fieldWEB_INFO_ENTREPRISE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
//					tfWEB_INFO_ENTREPRISE = (Text)fieldWEB_INFO_ENTREPRISE.getControl();
//					fieldWEB_INFO_ENTREPRISE.getLayoutControl().setLayoutData(tfWEB_INFO_ENTREPRISELData);
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Composite getPaCorpsFormulaire() {
		return paCorpsFormulaire;
	}

	public Label getLaADRESSE1_INFO_ENTREPRISE() {
		return laADRESSE1_INFO_ENTREPRISE;
	}

	public Label getLaNOM_INFO_ENTREPRISE() {
		return laNOM_INFO_ENTREPRISE;
	}

	public Text getTfADRESSE1_INFO_ENTREPRISE() {
		return tfADRESSE1_INFO_ENTREPRISE;
	}

	public Text getTfNOM_INFO_ENTREPRISE() {
		return tfNOM_INFO_ENTREPRISE;
	}

	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public DateTime getDateTimeDATEDEB_INFO_ENTREPRISE() {
		return dateTimeDATEDEB_INFO_ENTREPRISE;
	}

	public DateTime getDateTimeDATEFIN_INFO_ENTREPRISE() {
		return dateTimeDATEFIN_INFO_ENTREPRISE;
	}

	public DecoratedField getFieldADRESSE1_INFO_ENTREPRISE() {
		return fieldADRESSE1_INFO_ENTREPRISE;
	}

	public DecoratedField getFieldADRESSE2_INFO_ENTREPRISE() {
		return fieldADRESSE2_INFO_ENTREPRISE;
	}

	public DecoratedField getFieldADRESSE3_INFO_ENTREPRISE() {
		return fieldADRESSE3_INFO_ENTREPRISE;
	}

	public DecoratedField getFieldAPE_INFO_ENTREPRISE() {
		return fieldAPE_INFO_ENTREPRISE;
	}

	public DecoratedField getFieldCAPITAL_INFO_ENTREPRISE() {
		return fieldCAPITAL_INFO_ENTREPRISE;
	}

	public DecoratedField getFieldCODEPOSTAL_INFO_ENTREPRISE() {
		return fieldCODEPOSTAL_INFO_ENTREPRISE;
	}

	public DecoratedField getFieldCODEXO_INFO_ENTREPRISE() {
		return fieldCODEXO_INFO_ENTREPRISE;
	}

	public DecoratedField getFieldDATEDEB_INFO_ENTREPRISE() {
		return fieldDATEDEB_INFO_ENTREPRISE;
	}

	public DecoratedField getFieldDATEFIN_INFO_ENTREPRISE() {
		return fieldDATEFIN_INFO_ENTREPRISE;
	}

	public DecoratedField getFieldEMAIL_INFO_ENTREPRISE() {
		return fieldEMAIL_INFO_ENTREPRISE;
	}

	public DecoratedField getFieldFAX_INFO_ENTREPRISE() {
		return fieldFAX_INFO_ENTREPRISE;
	}

	public DecoratedField getFieldNOM_INFO_ENTREPRISE() {
		return fieldNOM_INFO_ENTREPRISE;
	}

	public DecoratedField getFieldPAYS_INFO_ENTREPRISE() {
		return fieldPAYS_INFO_ENTREPRISE;
	}

	public DecoratedField getFieldRCS_INFO_ENTREPRISE() {
		return fieldRCS_INFO_ENTREPRISE;
	}

	public DecoratedField getFieldSIRET_INFO_ENTREPRISE() {
		return fieldSIRET_INFO_ENTREPRISE;
	}

	public DecoratedField getFieldTEL_INFO_ENTREPRISE() {
		return fieldTEL_INFO_ENTREPRISE;
	}

	public DecoratedField getFieldTVA_INTRA_INFO_ENTREPRISE() {
		return fieldTVA_INTRA_INFO_ENTREPRISE;
	}

	public DecoratedField getFieldVILLE_INFO_ENTREPRISE() {
		return fieldVILLE_INFO_ENTREPRISE;
	}

	public DecoratedField getFieldWEB_INFO_ENTREPRISE() {
		return fieldWEB_INFO_ENTREPRISE;
	}

	public Label getLaADRESSE2_INFO_ENTREPRISE() {
		return laADRESSE2_INFO_ENTREPRISE;
	}

	public Label getLaADRESSE3_INFO_ENTREPRISE() {
		return laADRESSE3_INFO_ENTREPRISE;
	}

	public Label getLaAPE_INFO_ENTREPRISE() {
		return laAPE_INFO_ENTREPRISE;
	}

	public Label getLaCAPITAL_INFO_ENTREPRISE() {
		return laCAPITAL_INFO_ENTREPRISE;
	}

	public Label getLaCODEPOSTAL_INFO_ENTREPRISE() {
		return laCODEPOSTAL_INFO_ENTREPRISE;
	}

	public Label getLaCODEXO_INFO_ENTREPRISE() {
		return laCODEXO_INFO_ENTREPRISE;
	}

	public Label getLaDATEDEB_INFO_ENTREPRISE() {
		return laDATEDEB_INFO_ENTREPRISE;
	}

	public Label getLaDATEFIN_INFO_ENTREPRISE() {
		return laDATEFIN_INFO_ENTREPRISE;
	}

	public Label getLaEMAIL_INFO_ENTREPRISE() {
		return laEMAIL_INFO_ENTREPRISE;
	}

	public Label getLaFAX_INFO_ENTREPRISE() {
		return laFAX_INFO_ENTREPRISE;
	}

	public Label getLaPAYS_INFO_ENTREPRISE() {
		return laPAYS_INFO_ENTREPRISE;
	}

	public Label getLaRCS_INFO_ENTREPRISE() {
		return laRCS_INFO_ENTREPRISE;
	}

	public Label getLaSIRET_INFO_ENTREPRISE() {
		return laSIRET_INFO_ENTREPRISE;
	}

	public Label getLaTEL_INFO_ENTREPRISE() {
		return laTEL_INFO_ENTREPRISE;
	}

	public Label getLaTVA_INTRA_INFO_ENTREPRISE() {
		return laTVA_INTRA_INFO_ENTREPRISE;
	}

	public Label getLaVILLE_INFO_ENTREPRISE() {
		return laVILLE_INFO_ENTREPRISE;
	}

	public Label getLaWEB_INFO_ENTREPRISE() {
		return laWEB_INFO_ENTREPRISE;
	}

	public Text getTfADRESSE2_INFO_ENTREPRISE() {
		return tfADRESSE2_INFO_ENTREPRISE;
	}

	public Text getTfADRESSE3_INFO_ENTREPRISE() {
		return tfADRESSE3_INFO_ENTREPRISE;
	}

	public Text getTfAPE_INFO_ENTREPRISE() {
		return tfAPE_INFO_ENTREPRISE;
	}

	public Text getTfCAPITAL_INFO_ENTREPRISE() {
		return tfCAPITAL_INFO_ENTREPRISE;
	}

	public Text getTfCODEPOSTAL_INFO_ENTREPRISE() {
		return tfCODEPOSTAL_INFO_ENTREPRISE;
	}

	public Text getTfCODEXO_INFO_ENTREPRISE() {
		return tfCODEXO_INFO_ENTREPRISE;
	}

//	public Text getTfDATEDEB_INFO_ENTREPRISE() {
//		return tfDATEDEB_INFO_ENTREPRISE;
//	}
//
//	public Text getTfDATEFIN_INFO_ENTREPRISE() {
//		return tfDATEFIN_INFO_ENTREPRISE;
//	}

	public Text getTfEMAIL_INFO_ENTREPRISE() {
		return tfEMAIL_INFO_ENTREPRISE;
	}

	public Text getTfFAX_INFO_ENTREPRISE() {
		return tfFAX_INFO_ENTREPRISE;
	}

	public Text getTfPAYS_INFO_ENTREPRISE() {
		return tfPAYS_INFO_ENTREPRISE;
	}

	public Text getTfRCS_INFO_ENTREPRISE() {
		return tfRCS_INFO_ENTREPRISE;
	}

	public Text getTfSIRET_INFO_ENTREPRISE() {
		return tfSIRET_INFO_ENTREPRISE;
	}

	public Text getTfTEL_INFO_ENTREPRISE() {
		return tfTEL_INFO_ENTREPRISE;
	}

	public Text getTfTVA_INTRA_INFO_ENTREPRISE() {
		return tfTVA_INTRA_INFO_ENTREPRISE;
	}

	public Text getTfVILLE_INFO_ENTREPRISE() {
		return tfVILLE_INFO_ENTREPRISE;
	}

	public Text getTfWEB_INFO_ENTREPRISE() {
		return tfWEB_INFO_ENTREPRISE;
	}

	public Button getBtnDetail() {
		return btnDetail;
	}

	public DateTime getDateTimeDATEDEB_REG_INFO_ENTREPRISE() {
		return dateTimeDATEDEB_REG_INFO_ENTREPRISE;
	}

	public DateTime getDateTimeDATEDEB_REL_INFO_ENTREPRISE() {
		return dateTimeDATEDEB_REL_INFO_ENTREPRISE;
	}

	public DecoratedField getFieldDATEDEB_REG_INFO_ENTREPRISE() {
		return fieldDATEDEB_REG_INFO_ENTREPRISE;
	}

	public DecoratedField getFieldDATEDEB_REL_INFO_ENTREPRISE() {
		return fieldDATEDEB_REL_INFO_ENTREPRISE;
	}

	public Label getLaDATEDEB_REG_INFO_ENTREPRISE() {
		return laDATEDEB_REG_INFO_ENTREPRISE;
	}

	public Label getLaDATEDEB_REL_INFO_ENTREPRISE() {
		return laDATEDEB_REL_INFO_ENTREPRISE;
	}

	public Group getGroupDate() {
		return groupDate;
	}

	public Button getCbActiveDateDebReg() {
		return cbActiveDateDebReg;
	}

	public Button getCbActiveDateDebRel() {
		return cbActiveDateDebRel;
	}

}

package fr.legrain.avoir.ecran;
import com.cloudgarden.resource.SWTResourceManager;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.fieldassist.TextControlCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import fr.legrain.lib.gui.PaBtnAvecAssistant;
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
public class PaSaisieLignesAvoir extends org.eclipse.swt.widgets.Composite {

	 {
		 //Register as a resource user - SWTResourceManager will
		 //handle the obtaining and disposing of resources
		 SWTResourceManager.registerResourceUser(this);
	 }
	 
	 private Label laNUM_LIGNE_L_AVOIR;
	private Text tfNUM_LIGNE_L_AVOIR;
	private Label laCODE_ARTICLE;
	private Label laLIB_L_AVOIR;
	private Text tfLIB_L_AVOIR;
	private Label laMT_TTC_L_AVOIR;
	private PaBtnAvecAssistant paBtnAvecAssistant;
	private Text tfMT_TTC_L_AVOIR;
	private Table grille;
	private Composite paSaisie;
	private Text tfREM_TX_L_AVOIR;
	private Label laREM_TX_L_AVOIR;
	private Text tfMT_HT_L_AVOIR;
	private Label laMT_HT_L_AVOIR;
	private Text tfPRIX_U_L_AVOIR;
	private Label laPRIX_U_L_AVOIR;
	private Text tfU2_L_AVOIR;
	private Label laU2_L_AVOIR;
	private Text tfU1_L_AVOIR;
	private Label laU1_L_AVOIR;
	private Text tfQTE_L_AVOIR;
	private Text tfQTE2_L_AVOIR;
	private Label laQTE_L_AVOIR;
	private Label laQTE2_L_AVOIR;
	private Text tfCODE_ARTICLE;
	
	private Text tfNET_TVA_CALC;
	private Label laNET_TVA_CALC;
	private Text tfMT_TTC_CALC;
	private Label laMT_TTC_CALC;
	private Text tfMT_HT_CALC;
	private Label laMT_HT_CALC;
	private Text tfNET_HT_CALC;
	private Label laNET_HT_CALC;
	private Text tfNET_TTC_CALC;
	private Label laNET_TTC_CALC;
	
	private Label laTitreTransportU1 = null;
	private Label laQteTitreTransportU1 = null;
	private Text tfTitreTransportU1 = null;
	private Text tfQteTitreTransportU1 = null;
	private DecoratedField fieldTitreTransportU1 = null;
	private DecoratedField fieldQteTitreTransportU1 = null;

	private DecoratedField fieldNUM_LIGNE_L_AVOIR;
	private DecoratedField fieldLIB_L_AVOIR;
	private DecoratedField fieldREM_TX_L_AVOIR;
	private DecoratedField fieldMT_HT_L_AVOIR;
	private Group paTotauxLignes;
	private DecoratedField fieldMT_TTC_L_AVOIR;
	private DecoratedField fieldPRIX_U_L_AVOIR;
	private DecoratedField fieldU2_L_AVOIR;
	private DecoratedField fieldU1_L_AVOIR;
	private DecoratedField fieldQTE_L_AVOIR;
	private DecoratedField fieldQTE2_L_AVOIR;
	private DecoratedField fieldCODE_TIERS;
	private DecoratedField fieldCODE_ARTICLE;
	


	final private boolean decore = LgrMess.isDECORE();
//	final private boolean decore = false;
	//public Object getTfCODE_ARTICLE;

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
		PaSaisieLignesAvoir inst = new PaSaisieLignesAvoir(shell, SWT.NULL);
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

	public PaSaisieLignesAvoir(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			this.setLayout(thisLayout);
			thisLayout.numColumns = 2;
			this.setSize(1066, 255);
			{
				paSaisie = new Composite(this, SWT.NONE);
				GridLayout paSaisieLayout = new GridLayout();
				paSaisieLayout.numColumns = 11;
				GridData paSaisieLData = new GridData();
				paSaisieLData.horizontalAlignment = GridData.FILL;
				paSaisieLData.heightHint = 48;
				paSaisieLData.grabExcessHorizontalSpace = true;
				paSaisie.setLayoutData(paSaisieLData);
				paSaisie.setLayout(paSaisieLayout);
				{
					laNUM_LIGNE_L_AVOIR = new Label(paSaisie, SWT.NONE);
					laNUM_LIGNE_L_AVOIR.setText("N°");
				}
				{
					laCODE_ARTICLE = new Label(paSaisie, SWT.NONE);
					laCODE_ARTICLE.setText("Code article");
				}
				{
					laLIB_L_AVOIR = new Label(paSaisie, SWT.NONE);
					laLIB_L_AVOIR.setText("Libellé");
				}
				{
					laQTE_L_AVOIR = new Label(paSaisie, SWT.NONE);
					laQTE_L_AVOIR.setText("Qté 1");
				}
				{
					laU1_L_AVOIR = new Label(paSaisie, SWT.NONE);
					laU1_L_AVOIR.setText("U1");
				}
				{
					laQTE2_L_AVOIR = new Label(paSaisie, SWT.NONE);
					laQTE2_L_AVOIR.setText("Qté 2");
				}				
				{
					laU2_L_AVOIR = new Label(paSaisie, SWT.NONE);
					laU2_L_AVOIR.setText("U2");
				}
				{
					laPRIX_U_L_AVOIR = new Label(paSaisie, SWT.NONE);
					laPRIX_U_L_AVOIR.setText("Prix unitaire");
				}
				{
					laREM_TX_L_AVOIR = new Label(paSaisie, SWT.NONE);
					laREM_TX_L_AVOIR.setText("Remise %");
				}
				{
					laMT_HT_L_AVOIR = new Label(paSaisie, SWT.NONE);
					GridData laMT_HT_L_AVOIRLData = new GridData();
					laMT_HT_L_AVOIRLData.widthHint = 86;
					laMT_HT_L_AVOIRLData.heightHint = 13;
					laMT_HT_L_AVOIR.setLayoutData(laMT_HT_L_AVOIRLData);
					laMT_HT_L_AVOIR.setText("Montant HT");
				}
				{
					laMT_TTC_L_AVOIR = new Label(paSaisie, SWT.NONE);
					GridData laMT_TTC_L_AVOIRLData = new GridData();
					laMT_TTC_L_AVOIRLData.horizontalSpan = 2;
					laMT_TTC_L_AVOIR.setText("Montant TTC");
				}
				
				{
					GridData tfNUM_LIGNE_L_AVOIRLData = new GridData();
					tfNUM_LIGNE_L_AVOIRLData.widthHint = 23;
					if(!decore){
						tfNUM_LIGNE_L_AVOIR = new Text(paSaisie, SWT.BORDER);
						tfNUM_LIGNE_L_AVOIR.setLayoutData(tfNUM_LIGNE_L_AVOIRLData);
						tfNUM_LIGNE_L_AVOIR.setText("NUM_LIGNE");

					}else {
						fieldNUM_LIGNE_L_AVOIR = new DecoratedField(paSaisie, SWT.BORDER, new TextControlCreator());
						tfNUM_LIGNE_L_AVOIR = (Text)fieldNUM_LIGNE_L_AVOIR.getControl();
						fieldNUM_LIGNE_L_AVOIR.getLayoutControl().setLayoutData(tfNUM_LIGNE_L_AVOIRLData);
					}
				}
				{
					GridData tfCODE_ARTICLELData = new GridData();
					tfCODE_ARTICLELData.widthHint = 78;
					if(!decore) {
						tfCODE_ARTICLE = new Text(paSaisie, SWT.BORDER);
						tfCODE_ARTICLE.setLayoutData(tfCODE_ARTICLELData);
						tfCODE_ARTICLE.setText("CODE_ARTICLE");
					} else {
						fieldCODE_ARTICLE = new DecoratedField(paSaisie, SWT.BORDER, new TextControlCreator());
						tfCODE_ARTICLE = (Text)fieldCODE_ARTICLE.getControl();
						fieldCODE_ARTICLE.getLayoutControl().setLayoutData(tfCODE_ARTICLELData);
					}
				}
				{
					GridData tfLIB_L_AVOIRLData = new GridData();
					tfLIB_L_AVOIRLData.horizontalAlignment = GridData.FILL;
					tfLIB_L_AVOIRLData.grabExcessHorizontalSpace = true;
					if(!decore) {
						tfLIB_L_AVOIR = new Text(paSaisie, SWT.BORDER);
						tfLIB_L_AVOIR.setLayoutData(tfLIB_L_AVOIRLData);
						tfLIB_L_AVOIR.setText("LIB_L_AVOIR");
					} else {
						fieldLIB_L_AVOIR = new DecoratedField(paSaisie, SWT.BORDER, new TextControlCreator());
						tfLIB_L_AVOIR = (Text)fieldLIB_L_AVOIR.getControl();
						fieldLIB_L_AVOIR.getLayoutControl().setLayoutData(tfLIB_L_AVOIRLData);
					}
				}
				{
					GridData tfQTE_L_AVOIRLData = new GridData();
					tfQTE_L_AVOIRLData.widthHint = 67;
					if(!decore){
						tfQTE_L_AVOIR = new Text(paSaisie, SWT.BORDER);
						tfQTE_L_AVOIR.setLayoutData(tfQTE_L_AVOIRLData);
						tfQTE_L_AVOIR.setText("QTE_L_AVOIR");
					}else{
						fieldQTE_L_AVOIR = new DecoratedField(paSaisie, SWT.BORDER, new TextControlCreator());
						tfQTE_L_AVOIR = (Text)fieldQTE_L_AVOIR.getControl();
						fieldQTE_L_AVOIR.getLayoutControl().setLayoutData(tfQTE_L_AVOIRLData);
					}
				}
				{
					GridData tfU1_L_AVOIRLData = new GridData();
					tfU1_L_AVOIRLData.widthHint = 44;
					if(!decore){
						tfU1_L_AVOIR = new Text(paSaisie, SWT.BORDER);
						tfU1_L_AVOIR.setLayoutData(tfU1_L_AVOIRLData);
						tfU1_L_AVOIR.setText("U1_L_AVOIR");
					}else{
						fieldU1_L_AVOIR = new DecoratedField(paSaisie, SWT.BORDER, new TextControlCreator());
						tfU1_L_AVOIR = (Text)fieldU1_L_AVOIR.getControl();
						fieldU1_L_AVOIR.getLayoutControl().setLayoutData(tfU1_L_AVOIRLData);
					}
				}
				{
					GridData tfQTE2_L_AVOIRLData = new GridData();
					tfQTE2_L_AVOIRLData.widthHint = 67;
					if(!decore){
						tfQTE2_L_AVOIR = new Text(paSaisie, SWT.BORDER);
						tfQTE2_L_AVOIR.setLayoutData(tfQTE2_L_AVOIRLData);
						tfQTE2_L_AVOIR.setText("QTE2_L_AVOIR");
					}else{
						fieldQTE2_L_AVOIR = new DecoratedField(paSaisie, SWT.BORDER, new TextControlCreator());
						tfQTE2_L_AVOIR = (Text)fieldQTE2_L_AVOIR.getControl();
						fieldQTE2_L_AVOIR.getLayoutControl().setLayoutData(tfQTE2_L_AVOIRLData);
					}
				}
				
				{
					GridData tfU2_L_AVOIRLData = new GridData();
					tfU2_L_AVOIRLData.widthHint = 47;
					if(!decore){
						tfU2_L_AVOIR = new Text(paSaisie, SWT.BORDER);
						tfU2_L_AVOIR.setLayoutData(tfU2_L_AVOIRLData);
						tfU2_L_AVOIR.setText("U2_L_AVOIR");
					}else{
						fieldU2_L_AVOIR = new DecoratedField(paSaisie, SWT.BORDER, new TextControlCreator());
						tfU2_L_AVOIR = (Text)fieldU2_L_AVOIR.getControl();
						fieldU2_L_AVOIR.getLayoutControl().setLayoutData(tfU2_L_AVOIRLData);
					}
				}
				{
					GridData tfPRIX_U_L_AVOIRLData = new GridData();
					tfPRIX_U_L_AVOIRLData.widthHint = 70;
					if(!decore){
						tfPRIX_U_L_AVOIR = new Text(paSaisie, SWT.BORDER);
						tfPRIX_U_L_AVOIR.setLayoutData(tfPRIX_U_L_AVOIRLData);
						tfPRIX_U_L_AVOIR.setText("PRIX_U_L_AVOIR");
					}else{
						fieldPRIX_U_L_AVOIR = new DecoratedField(paSaisie, SWT.BORDER, new TextControlCreator());
						tfPRIX_U_L_AVOIR = (Text)fieldPRIX_U_L_AVOIR.getControl();
						fieldPRIX_U_L_AVOIR.getLayoutControl().setLayoutData(tfPRIX_U_L_AVOIRLData);
					}
				}
				{
					GridData tfREM_TX_L_AVOIRLData = new GridData();
					tfREM_TX_L_AVOIRLData.widthHint = 54;
					if(!decore){
						tfREM_TX_L_AVOIR = new Text(paSaisie, SWT.BORDER);
						tfREM_TX_L_AVOIR.setLayoutData(tfREM_TX_L_AVOIRLData);
						tfREM_TX_L_AVOIR.setText("REM_TX_L_AVOIR");
					}else{
						fieldREM_TX_L_AVOIR = new DecoratedField(paSaisie, SWT.BORDER, new TextControlCreator());
						tfREM_TX_L_AVOIR = (Text)fieldREM_TX_L_AVOIR.getControl();
						fieldREM_TX_L_AVOIR.getLayoutControl().setLayoutData(tfREM_TX_L_AVOIRLData);
					}
				}
				{
					GridData tfMT_HT_L_AVOIRLData = new GridData();
					tfMT_HT_L_AVOIRLData.horizontalAlignment = GridData.CENTER;
					tfMT_HT_L_AVOIRLData.widthHint = 106;
					if(!decore){
						tfMT_HT_L_AVOIR = new Text(paSaisie, SWT.BORDER);
						tfMT_HT_L_AVOIR.setLayoutData(tfMT_HT_L_AVOIRLData);
						tfMT_HT_L_AVOIR.setText("MT_HT_L_AVOIR");
					}else{
						fieldMT_HT_L_AVOIR = new DecoratedField(paSaisie, SWT.BORDER, new TextControlCreator());
						tfMT_HT_L_AVOIR = (Text)fieldMT_HT_L_AVOIR.getControl();
						fieldMT_HT_L_AVOIR.getLayoutControl().setLayoutData(tfMT_HT_L_AVOIRLData);
					}
				}
				{
					GridData tfMT_TTC_L_AVOIRLData = new GridData();
					tfMT_TTC_L_AVOIRLData.horizontalAlignment = GridData.CENTER;
					tfMT_TTC_L_AVOIRLData.widthHint = 120;
					if(!decore){
						tfMT_TTC_L_AVOIR = new Text(paSaisie, SWT.BORDER);
						tfMT_TTC_L_AVOIR.setLayoutData(tfMT_TTC_L_AVOIRLData);
						tfMT_TTC_L_AVOIR.setText("MT_TTC_L_AVOIR");

					}else{
						fieldMT_TTC_L_AVOIR = new DecoratedField(paSaisie, SWT.BORDER, new TextControlCreator());
						tfMT_TTC_L_AVOIR = (Text)fieldMT_TTC_L_AVOIR.getControl();
						fieldMT_TTC_L_AVOIR.getLayoutControl().setLayoutData(tfMT_TTC_L_AVOIRLData);
					}
				}
			}
			{
				GridData grilleLData = new GridData();
				grilleLData.horizontalAlignment = GridData.FILL;
				grilleLData.verticalAlignment = GridData.FILL;
				grilleLData.grabExcessVerticalSpace = true;
				grilleLData.grabExcessHorizontalSpace = true;
				grilleLData.horizontalSpan = 2;
				grille = new Table(this, SWT.SINGLE
					| SWT.FULL_SELECTION
					| SWT.H_SCROLL
					| SWT.V_SCROLL
					| SWT.BORDER);
				grille.setLayoutData(grilleLData);
				grille.setLinesVisible(true);
				grille.setHeaderVisible(true);
			}
			
			{
				paTotauxLignes = new Group(this, SWT.NONE);
				GridLayout paTotauxLignesLayout = new GridLayout();
				paTotauxLignesLayout.numColumns = 10;
				GridData paTotauxLignesLData = new GridData();
				paTotauxLignesLData.verticalAlignment = GridData.END;
				paTotauxLignesLData.horizontalAlignment = GridData.CENTER;
				paTotauxLignesLData.horizontalSpan = 2;
				paTotauxLignesLData.heightHint = 37;
				paTotauxLignesLData.widthHint = 912;
				paTotauxLignes.setLayoutData(paTotauxLignesLData);
				paTotauxLignes.setLayout(paTotauxLignesLayout);
				paTotauxLignes.setText("Totaux");
			}
			
			{
				laMT_HT_CALC = new Label(paTotauxLignes, SWT.NONE);
				laMT_HT_CALC.setText("HT");
			}
			{
				GridData tfMT_HT_CALCLData = new GridData();
				tfMT_HT_CALCLData.widthHint = 127;
				tfMT_HT_CALCLData.verticalAlignment = GridData.BEGINNING;
				tfMT_HT_CALCLData.horizontalAlignment = GridData.BEGINNING;
				tfMT_HT_CALC = new Text(paTotauxLignes, SWT.BORDER);
				tfMT_HT_CALC.setLayoutData(tfMT_HT_CALCLData);
				tfMT_HT_CALC.setText("MT_HT_CALC");
				tfMT_HT_CALC.setEditable(false);
				tfMT_HT_CALC.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));

			}
			{
				laMT_TTC_CALC = new Label(paTotauxLignes, SWT.NONE);
				laMT_TTC_CALC.setText("TTC");
			}
			{
				GridData tfMT_TTC_CALCLData = new GridData();
				tfMT_TTC_CALCLData.widthHint = 125;
				tfMT_TTC_CALC = new Text(paTotauxLignes, SWT.BORDER);
				tfMT_TTC_CALC.setLayoutData(tfMT_TTC_CALCLData);
				tfMT_TTC_CALC.setText("MT_TTC_CALC");
				tfMT_TTC_CALC.setEditable(false);
				tfMT_TTC_CALC.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));

			}
			{
				laNET_HT_CALC = new Label(paTotauxLignes, SWT.NONE);
				laNET_HT_CALC.setText("NET HT");
			}
			{
				GridData tfNET_HT_CALCLData = new GridData();
				tfNET_HT_CALCLData.widthHint = 127;
				tfNET_HT_CALCLData.verticalAlignment = GridData.BEGINNING;
				tfNET_HT_CALCLData.horizontalAlignment = GridData.BEGINNING;

				tfNET_HT_CALC = new Text(paTotauxLignes, SWT.BORDER);
				tfNET_HT_CALC.setLayoutData(tfNET_HT_CALCLData);
				tfNET_HT_CALC.setText("NET_HT_CALC");
				tfNET_HT_CALC.setEditable(false);
				tfNET_HT_CALC.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));

			}
			{
				laNET_TVA_CALC = new Label(paTotauxLignes, SWT.NONE);
				laNET_TVA_CALC.setText("NET TVA");
			}
			{
				GridData tfMT_TVA_CALCLData = new GridData();
				tfMT_TVA_CALCLData.widthHint = 127;
				tfNET_TVA_CALC = new Text(paTotauxLignes, SWT.BORDER);
				tfNET_TVA_CALC.setLayoutData(tfMT_TVA_CALCLData);
				tfNET_TVA_CALC.setText("MT_TVA_CALC");
				tfNET_TVA_CALC.setEditable(false);
				tfNET_TVA_CALC.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));

			}
			{
				laNET_TTC_CALC = new Label(paTotauxLignes, SWT.NONE);
				laNET_TTC_CALC.setText("NET TTC");
			}
			{
				GridData tfNET_TTC_CALCLData = new GridData();
				tfNET_TTC_CALCLData.widthHint = 127;
				tfNET_TTC_CALCLData.verticalAlignment = GridData.BEGINNING;
				tfNET_TTC_CALCLData.horizontalAlignment = GridData.BEGINNING;

				tfNET_TTC_CALC = new Text(paTotauxLignes, SWT.BORDER);
				tfNET_TTC_CALC.setLayoutData(tfNET_TTC_CALCLData);
				tfNET_TTC_CALC.setText("tfNET_TTC_CALC");
				tfNET_TTC_CALC.setEditable(false);
				tfNET_TTC_CALC.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));

			}			
			
			{
				GridData paBtnAvecAssistantLData = new GridData();
				paBtnAvecAssistantLData.verticalAlignment = GridData.END;
				paBtnAvecAssistantLData.horizontalAlignment = GridData.CENTER;
				paBtnAvecAssistant = new PaBtnAvecAssistant(this, SWT.NONE);
				paBtnAvecAssistant.setLayoutData(paBtnAvecAssistantLData);
			}
			
			tfMT_HT_CALC.setVisible(false);
			tfMT_TTC_CALC.setVisible(false);
			laMT_HT_CALC.setVisible(false);
			laMT_TTC_CALC.setVisible(false);
			
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void extensionTitreTransport(PaSaisieLignesAvoir vue) {
		
		GridLayout paSaisieLayout = new GridLayout();
		paSaisieLayout.numColumns = ((GridLayout)paSaisie.getLayout()).numColumns+2; //12
		paSaisie.setLayout(paSaisieLayout);
		
		{
			laTitreTransportU1 = new Label(vue.getPaSaisie(), SWT.NONE);
			laTitreTransportU1.setText("CRD");
			laTitreTransportU1.moveBelow(laMT_TTC_L_AVOIR);
			//laTitreTransportU1.moveAbove(laQTE_L_AVOIR);
		}
		{
			laQteTitreTransportU1 = new Label(vue.getPaSaisie(), SWT.NONE);
			laQteTitreTransportU1.setText("Qte CRD");
			laQteTitreTransportU1.moveAbove(laTitreTransportU1);
		}
		{
			GridData tfTitreTransportU1LData = new GridData();
			tfTitreTransportU1LData.horizontalAlignment = GridData.FILL;
			//tfTitreTransportU1LData.grabExcessHorizontalSpace = true;
			if(!decore) {
				tfTitreTransportU1 = new Text(paSaisie, SWT.BORDER);
				tfTitreTransportU1.setLayoutData(tfTitreTransportU1LData);
			} else {					
				fieldTitreTransportU1 = new DecoratedField(paSaisie, SWT.BORDER, new TextControlCreator());
				tfTitreTransportU1 = (Text)fieldTitreTransportU1.getControl();
				fieldTitreTransportU1.getLayoutControl().setLayoutData(tfTitreTransportU1LData);
			}
			tfTitreTransportU1.getParent().moveBelow(tfMT_TTC_L_AVOIR.getParent());
			//tfTitreTransportU1.getParent().moveAbove(tfQTE_L_AVOIR.getParent()); //getParent à cause des champs décorés
		}
		{
			GridData tfQteTitreTransportU1LData = new GridData();
			tfQteTitreTransportU1LData.horizontalAlignment = GridData.FILL;
			//tfTitreTransportU1LData.grabExcessHorizontalSpace = true;
			if(!decore) {
				tfQteTitreTransportU1 = new Text(paSaisie, SWT.BORDER);
				tfQteTitreTransportU1.setLayoutData(tfQteTitreTransportU1LData);
			} else {					
				fieldQteTitreTransportU1 = new DecoratedField(paSaisie, SWT.BORDER, new TextControlCreator());
				tfQteTitreTransportU1 = (Text)fieldQteTitreTransportU1.getControl();
				fieldQteTitreTransportU1.getLayoutControl().setLayoutData(tfQteTitreTransportU1LData);
			}
			tfQteTitreTransportU1.getParent().moveAbove(tfTitreTransportU1.getParent()); //getParent à cause des champs décorés
		}
		vue.layout();
	}

	public Label getLaNUM_LIGNE_L_AVOIR() {
		return laNUM_LIGNE_L_AVOIR;
	}

	public Text getTfNUM_LIGNE_L_AVOIR() {
		return tfNUM_LIGNE_L_AVOIR;
	}

	public Label getLaCODE_ARTICLE() {
		return laCODE_ARTICLE;
	}

	public Text getTfCODE_ARTICLE() {
		return tfCODE_ARTICLE;
	}

	public Label getLaLIB_L_AVOIR() {
		return laLIB_L_AVOIR;
	}

	public Text getTfLIB_L_AVOIR() {
		return tfLIB_L_AVOIR;
	}

	public Label getLaQTE_L_AVOIR() {
		return laQTE_L_AVOIR;
	}

	public Label getLaU1_L_AVOIR() {
		return laU1_L_AVOIR;
	}

	public Text getTfMT_HT_L_AVOIR() {
		return tfMT_HT_L_AVOIR;
	}

	public Composite getPaSaisie() {
		return paSaisie;
	}

	public Table getGrille() {
		return grille;
	}

	public Label getLaMT_HT_L_AVOIR() {
		return laMT_HT_L_AVOIR;
	}

	public Label getLaPRIX_U_L_AVOIR() {
		return laPRIX_U_L_AVOIR;
	}

	public Label getLaREM_TX_L_AVOIR() {
		return laREM_TX_L_AVOIR;
	}

	public Label getLaU2_L_AVOIR() {
		return laU2_L_AVOIR;
	}

	public Text getTfPRIX_U_L_AVOIR() {
		return tfPRIX_U_L_AVOIR;
	}

	public Text getTfQTE_L_AVOIR() {
		return tfQTE_L_AVOIR;
	}

	public Text getTfREM_TX_L_AVOIR() {
		return tfREM_TX_L_AVOIR;
	}

	public Text getTfU1_L_AVOIR() {
		return tfU1_L_AVOIR;
	}

	public Text getTfU2_L_AVOIR() {
		return tfU2_L_AVOIR;
	}

	public DecoratedField getFieldCODE_ARTICLE() {
		return fieldCODE_ARTICLE;
	}

	public DecoratedField getFieldCODE_TIERS() {
		return fieldCODE_TIERS;
	}

	public DecoratedField getFieldLIB_L_AVOIR() {
		return fieldLIB_L_AVOIR;
	}

	public DecoratedField getFieldMT_HT_L_AVOIR() {
		return fieldMT_HT_L_AVOIR;
	}

	public DecoratedField getFieldNUM_LIGNE_L_AVOIR() {
		return fieldNUM_LIGNE_L_AVOIR;
	}

	public DecoratedField getFieldPRIX_U_L_AVOIR() {
		return fieldPRIX_U_L_AVOIR;
	}

	public DecoratedField getFieldQTE_L_AVOIR() {
		return fieldQTE_L_AVOIR;
	}

	public DecoratedField getFieldREM_TX_L_AVOIR() {
		return fieldREM_TX_L_AVOIR;
	}

	public DecoratedField getFieldU1_L_AVOIR() {
		return fieldU1_L_AVOIR;
	}

	public DecoratedField getFieldU2_L_AVOIR() {
		return fieldU2_L_AVOIR;
	}

	public Text getTfMT_TTC_L_AVOIR() {
		return tfMT_TTC_L_AVOIR;
	}

	public Label getLaMT_TTC_L_AVOIR() {
		return laMT_TTC_L_AVOIR;
	}

	public DecoratedField getFieldMT_TTC_L_AVOIR() {
		return fieldMT_TTC_L_AVOIR;
	}

	public void setFieldMT_TTC_L_AVOIR(DecoratedField fieldMT_TTC_L_AVOIR) {
		this.fieldMT_TTC_L_AVOIR = fieldMT_TTC_L_AVOIR;
	}
	
	public PaBtnAvecAssistant getPaBtnAvecAssistant() {
		return paBtnAvecAssistant;
	}



	public Text getTfMT_TTC_CALC() {
		return tfMT_TTC_CALC;
	}

	public Label getLaMT_TTC_CALC() {
		return laMT_TTC_CALC;
	}

	public Text getTfMT_HT_CALC() {
		return tfMT_HT_CALC;
	}

	public Label getLaMT_HT_CALC() {
		return laMT_HT_CALC;
	}

	public Group getPaTotauxLignes() {
		return paTotauxLignes;
	}


	public boolean isDecore() {
		return decore;
	}

	public Text getTfQTE2_L_AVOIR() {
		return tfQTE2_L_AVOIR;
	}

	public DecoratedField getFieldQTE2_L_AVOIR() {
		return fieldQTE2_L_AVOIR;
	}

	public Label getLaQTE2_L_AVOIR() {
		return laQTE2_L_AVOIR;
	}

	public Text getTfNET_TVA_CALC() {
		return tfNET_TVA_CALC;
	}

	public Label getLaNET_TVA_CALC() {
		return laNET_TVA_CALC;
	}

	public Text getTfNET_HT_CALC() {
		return tfNET_HT_CALC;
	}

	public Label getLaNET_HT_CALC() {
		return laNET_HT_CALC;
	}

	public Text getTfNET_TTC_CALC() {
		return tfNET_TTC_CALC;
	}

	public Label getLaNET_TTC_CALC() {
		return laNET_TTC_CALC;
	}

	public Label getLaTitreTransportU1() {
		return laTitreTransportU1;
	}

	public Label getLaQteTitreTransportU1() {
		return laQteTitreTransportU1;
	}

	public Text getTfTitreTransportU1() {
		return tfTitreTransportU1;
	}

	public Text getTfQteTitreTransportU1() {
		return tfQteTitreTransportU1;
	}

	public DecoratedField getFieldTitreTransportU1() {
		return fieldTitreTransportU1;
	}

	public DecoratedField getFieldQteTitreTransportU1() {
		return fieldQteTitreTransportU1;
	}

}

package fr.legrain.boncde.ecran;
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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import fr.legrain.lib.gui.PaBtnAvecAssistant;
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
public class PaTotauxBoncde extends org.eclipse.swt.widgets.Composite {
	private Table tableTVA;
	private Composite paTotaux;
	private Label laTX_REM_HT_BONCDE;
	private Text tfCODE_T_PAIEMENT;
	private Label laCODE_T_PAIEMENT;
	private Text tfMT_TVA_CALC;
	private Label laMT_TVA_CALC;
	private Button cbImprimer;
	private Text tfMT_TTC_CALC;
	private Label laMT_TTC_CALC;
	private ExpandBar expandBar;
	private PaBtnAvecAssistant paBtnAvecAssistant;
	private Label laMessage;
	private Text tfMT_HT_CALC;
	private Label laMT_HT_CALC;
	private Text tfTX_REM_TTC_BONCDE;
	private Label laTX_REM_TTC_BONCDE;
	private Text tfTX_REM_HT_BONCDE;
	private Text tfREGLE_DOCUMENT;
	private Label laREGLE_DOCUMENT;

	private DecoratedField fieldMT_TTC_CALC;
	private DecoratedField fieldTTC;
	private DecoratedField fieldTX_REM_TTC_BONCDE;
	private DecoratedField fieldMT_HT_CALC;
	private DecoratedField fieldTX_REM_HT_BONCDE;
	private DecoratedField fieldCODE_T_PAIEMENT;
	private DecoratedField fieldMT_TVA_CALC;
	private DecoratedField fieldImprimer;
	private DecoratedField fieldREGLE_DOCUMENT;

	final private boolean decore = LgrMess.isDECORE();
//	final private boolean decore = false;

	/**
	 * Auto-generated main method to display this 
	 * org.eclipse.swt.widgets.Group inside a new Shell.
	 */
	public static void main(String[] args) {
		showGUI();
	}

	/**
	 * Auto-generated method to display this 
	 * org.eclipse.swt.widgets.Group inside a new Shell.
	 */
	public static void showGUI() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		PaTotauxBoncde inst = new PaTotauxBoncde(shell, SWT.NULL);
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

	public PaTotauxBoncde(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.numColumns = 7;
			this.setLayout(thisLayout);
			this.setLayoutData(null);
			{
				tableTVA = new Table(this, SWT.SINGLE | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
				tableTVA.setHeaderVisible(true);
				GridData tableTVALData = new GridData();
				tableTVALData.verticalAlignment = GridData.FILL;
				tableTVALData.widthHint = 328;
				tableTVA.setLayoutData(tableTVALData);
				tableTVA.setLinesVisible(true);
			}
			{
				paTotaux = new Composite(this, SWT.NONE);
				GridLayout paTotauxLayout = new GridLayout();
				paTotauxLayout.numColumns = 6;
				GridData paTotauxLData = new GridData();
				paTotauxLData.horizontalAlignment = GridData.FILL;
				paTotauxLData.grabExcessHorizontalSpace = true;
				paTotauxLData.horizontalSpan = 6;
				paTotauxLData.verticalAlignment = GridData.FILL;
				paTotaux.setLayoutData(paTotauxLData);
				paTotaux.setLayout(paTotauxLayout);
				{
					laTX_REM_HT_BONCDE = new Label(paTotaux, SWT.NONE);
					laTX_REM_HT_BONCDE.setText("Remise globale %");
				}
				{
					GridData tfTX_REM_HT_FACTURELData = new GridData();
					tfTX_REM_HT_FACTURELData.widthHint = 127;
					if(!decore) {
						tfTX_REM_HT_BONCDE = new Text(paTotaux, SWT.BORDER);
						tfTX_REM_HT_BONCDE.setLayoutData(tfTX_REM_HT_FACTURELData);
						tfTX_REM_HT_BONCDE.setText("TX_REM_HT_BONCDE");
					} else {
						fieldTX_REM_HT_BONCDE = new DecoratedField(paTotaux, SWT.BORDER, new TextControlCreator());
						tfTX_REM_HT_BONCDE = (Text)fieldTX_REM_HT_BONCDE.getControl();
						fieldTX_REM_HT_BONCDE.getLayoutControl().setLayoutData(tfTX_REM_HT_FACTURELData);
					}
				}
				{
					laTX_REM_TTC_BONCDE = new Label(paTotaux, SWT.NONE);
					laTX_REM_TTC_BONCDE.setText("Escompte accordé %");
				}
				{
					GridData tfTX_REM_TTC_FACTURELData = new GridData();
					tfTX_REM_TTC_FACTURELData.horizontalSpan = 3;
					tfTX_REM_TTC_FACTURELData.widthHint = 125;
					if(!decore) {
						tfTX_REM_TTC_BONCDE = new Text(paTotaux, SWT.BORDER);
						tfTX_REM_TTC_BONCDE.setLayoutData(tfTX_REM_TTC_FACTURELData);
						tfTX_REM_TTC_BONCDE.setText("TX_REM_TTC_BONCDE");
					} else {
						fieldTX_REM_TTC_BONCDE = new DecoratedField(paTotaux, SWT.BORDER, new TextControlCreator());
						tfTX_REM_TTC_BONCDE = (Text)fieldTX_REM_TTC_BONCDE.getControl();
						fieldTX_REM_TTC_BONCDE.getLayoutControl().setLayoutData(tfTX_REM_TTC_FACTURELData);
					}
				}
				{
					laMT_HT_CALC = new Label(paTotaux, SWT.NONE);
					laMT_HT_CALC.setText("Montant HT");
				}
				{
					GridData tfMT_HT_CALCLData = new GridData();
					tfMT_HT_CALCLData.widthHint = 127;
					if(!decore) {
						tfMT_HT_CALC = new Text(paTotaux, SWT.BORDER);
						tfMT_HT_CALC.setLayoutData(tfMT_HT_CALCLData);
						tfMT_HT_CALC.setText("MT_HT_CALC");
					} else {
						fieldMT_HT_CALC = new DecoratedField(paTotaux, SWT.BORDER, new TextControlCreator());
						tfMT_HT_CALC = (Text)fieldMT_HT_CALC.getControl();
						fieldMT_HT_CALC.getLayoutControl().setLayoutData(tfMT_HT_CALCLData);
					}
				}
				{
					laMT_TTC_CALC = new Label(paTotaux, SWT.NONE);
					laMT_TTC_CALC.setText("Montant TTC");
				}
				{
					GridData tfMT_TTC_CALCLData = new GridData();
					tfMT_TTC_CALCLData.widthHint = 125;
					if(!decore) {
						tfMT_TTC_CALC = new Text(paTotaux, SWT.BORDER);
						tfMT_TTC_CALC.setLayoutData(tfMT_TTC_CALCLData);
						tfMT_TTC_CALC.setText("MT_TTC_CALC");
					} else {
						fieldMT_TTC_CALC = new DecoratedField(paTotaux, SWT.BORDER, new TextControlCreator());
						tfMT_TTC_CALC = (Text)fieldMT_TTC_CALC.getControl();
						fieldMT_TTC_CALC.getLayoutControl().setLayoutData(tfMT_TTC_CALCLData);
					}
				}

				{
					GridData cbImprimerLData = new GridData();
					cbImprimerLData.horizontalSpan = 2;
					cbImprimerLData.heightHint = 16;
					cbImprimerLData.horizontalAlignment = GridData.FILL;

					if(!decore) {
						cbImprimer = new Button(paTotaux, SWT.CHECK	| SWT.LEFT);
						cbImprimer.setLayoutData(cbImprimerLData);
					} else {
						fieldImprimer = new DecoratedField(paTotaux, SWT.CHECK | SWT.LEFT, new ButtonControlCreator());
						cbImprimer = (Button)fieldImprimer.getControl();
						fieldImprimer.getLayoutControl().setLayoutData(cbImprimerLData);
					}
					cbImprimer.setText("Imprimer");
				}

				{
					laMT_TVA_CALC = new Label(paTotaux, SWT.NONE);
					laMT_TVA_CALC.setText("Montant TVA");
				}
				{
					GridData tfMT_TVA_CALCLData = new GridData();
					tfMT_TVA_CALCLData.widthHint = 127;
					if(!decore) {
						tfMT_TVA_CALC = new Text(paTotaux, SWT.BORDER);
						tfMT_TVA_CALC.setLayoutData(tfMT_TVA_CALCLData);
						tfMT_TVA_CALC.setText("MT_TVA_CALC");
					} else {
						fieldMT_TVA_CALC = new DecoratedField(paTotaux, SWT.BORDER, new TextControlCreator());
						tfMT_TVA_CALC = (Text)fieldMT_TVA_CALC.getControl();
						fieldMT_TVA_CALC.getLayoutControl().setLayoutData(tfMT_TVA_CALCLData);
					}
				}
				{
					laREGLE_DOCUMENT = new Label(paTotaux, SWT.NONE);
					laREGLE_DOCUMENT.setText("Acompte");
				}
				{
					GridData tfREGLE_FACTURELData = new GridData();
					tfREGLE_FACTURELData.widthHint = 125;
					if(!decore) {
						tfREGLE_DOCUMENT = new Text(paTotaux, SWT.BORDER);
						tfREGLE_DOCUMENT.setLayoutData(tfREGLE_FACTURELData);
						tfREGLE_DOCUMENT.setText("REGLE_DOCUMENT");
					} else {
						fieldREGLE_DOCUMENT = new DecoratedField(paTotaux, SWT.BORDER, new TextControlCreator());
						tfREGLE_DOCUMENT = (Text)fieldREGLE_DOCUMENT.getControl();
						fieldREGLE_DOCUMENT.getLayoutControl().setLayoutData(tfREGLE_FACTURELData);
					}
				}
				{
					laCODE_T_PAIEMENT = new Label(paTotaux, SWT.NONE);
					laCODE_T_PAIEMENT.setText("Type paiement");
				}
				{
					GridData tfCODE_T_PAIEMENTLData = new GridData();
					tfCODE_T_PAIEMENTLData.widthHint = 38;
					if(!decore) {
						tfCODE_T_PAIEMENT = new Text(paTotaux, SWT.BORDER);
						tfCODE_T_PAIEMENT.setLayoutData(tfCODE_T_PAIEMENTLData);
						tfCODE_T_PAIEMENT.setText("CODE_T_PAIEMENT");
					} else {
						fieldCODE_T_PAIEMENT = new DecoratedField(paTotaux, SWT.BORDER, new TextControlCreator());
						tfCODE_T_PAIEMENT = (Text)fieldCODE_T_PAIEMENT.getControl();
						fieldCODE_T_PAIEMENT.getLayoutControl().setLayoutData(tfCODE_T_PAIEMENTLData);
					}
				}
			}
			{
				GridData expandBarLData = new GridData();
				expandBarLData.heightHint = 204;
				expandBarLData.horizontalSpan = 7;
				expandBarLData.horizontalAlignment = GridData.FILL;
				expandBarLData.grabExcessHorizontalSpace = true;
				expandBar = new ExpandBar(this, SWT.V_SCROLL);
				expandBar.setLayoutData(expandBarLData);
			}
			{
				GridData laMessageLData = new GridData();
				laMessageLData.horizontalAlignment = GridData.FILL;
				laMessageLData.grabExcessHorizontalSpace = true;
				laMessageLData.horizontalSpan = 7;
				laMessage = new Label(this, SWT.NONE);
				laMessage.setLayoutData(laMessageLData);
			}
			{
				GridData paBtnAvecAssistantLData = new GridData();
				paBtnAvecAssistantLData.horizontalSpan = 7;
				paBtnAvecAssistantLData.grabExcessVerticalSpace = true;
				paBtnAvecAssistantLData.verticalAlignment = GridData.END;
				paBtnAvecAssistantLData.horizontalAlignment = GridData.CENTER;
				paBtnAvecAssistant = new PaBtnAvecAssistant(this, SWT.NONE);
				paBtnAvecAssistant.setLayoutData(paBtnAvecAssistantLData);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


		
//	private Composite getThis(Composite parent) {
//		GridLayout thisLayout = new GridLayout();
//		thisLayout.numColumns = 7;
//		this.setLayout(thisLayout);
//		this.setLayoutData(null);
//		{
//			tableTVA = new Table(this, SWT.SINGLE | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
//			tableTVA.setHeaderVisible(true);
//			GridData tableTVALData = new GridData();
//			tableTVALData.verticalAlignment = GridData.FILL;
//			tableTVALData.widthHint = 328;
//			tableTVA.setLayoutData(tableTVALData);
//			tableTVA.setLinesVisible(true);
//		}
//		{
//			paTotaux = new Composite(this, SWT.NONE);
//			GridLayout paTotauxLayout = new GridLayout();
//			paTotauxLayout.numColumns = 6;
//			GridData paTotauxLData = new GridData();
//			paTotauxLData.horizontalAlignment = GridData.FILL;
//			paTotauxLData.grabExcessHorizontalSpace = true;
//			paTotauxLData.horizontalSpan = 6;
//			paTotauxLData.verticalAlignment = GridData.FILL;
//			paTotaux.setLayoutData(paTotauxLData);
//			paTotaux.setLayout(paTotauxLayout);
//			{
//				laTX_REM_HT_FACTURE = new Label(paTotaux, SWT.NONE);
//				laTX_REM_HT_FACTURE.setText("Remise globale");
//			}
//			{
//				GridData tfTX_REM_HT_FACTURELData = new GridData();
//				tfTX_REM_HT_FACTURELData.widthHint = 127;
//				if(!decore) {
//					tfTX_REM_HT_FACTURE = new Text(paTotaux, SWT.BORDER);
//					tfTX_REM_HT_FACTURE.setLayoutData(tfTX_REM_HT_FACTURELData);
//					tfTX_REM_HT_FACTURE.setText("TX_REM_HT_FACTURE");
//				} else {
//					fieldTX_REM_HT_FACTURE = new DecoratedField(paTotaux, SWT.BORDER, new TextControlCreator());
//					tfTX_REM_HT_FACTURE = (Text)fieldTX_REM_HT_FACTURE.getControl();
//					fieldTX_REM_HT_FACTURE.getLayoutControl().setLayoutData(tfTX_REM_HT_FACTURELData);
//				}
//			}
//			{
//				laTX_REM_TTC_FACTURE = new Label(paTotaux, SWT.NONE);
//				laTX_REM_TTC_FACTURE.setText("Escompte accordé");
//			}
//			{
//				GridData tfTX_REM_TTC_FACTURELData = new GridData();
//				tfTX_REM_TTC_FACTURELData.horizontalSpan = 3;
//				tfTX_REM_TTC_FACTURELData.widthHint = 125;
//				if(!decore) {
//					tfTX_REM_TTC_FACTURE = new Text(paTotaux, SWT.BORDER);
//					tfTX_REM_TTC_FACTURE.setLayoutData(tfTX_REM_TTC_FACTURELData);
//					tfTX_REM_TTC_FACTURE.setText("TX_REM_TTC_FACTURE");
//				} else {
//					fieldTX_REM_TTC_FACTURE = new DecoratedField(paTotaux, SWT.BORDER, new TextControlCreator());
//					tfTX_REM_TTC_FACTURE = (Text)fieldTX_REM_TTC_FACTURE.getControl();
//					fieldTX_REM_TTC_FACTURE.getLayoutControl().setLayoutData(tfTX_REM_TTC_FACTURELData);
//				}
//			}
//			{
//				laMT_HT_CALC = new Label(paTotaux, SWT.NONE);
//				laMT_HT_CALC.setText("Montant HT");
//			}
//			{
//				GridData tfMT_HT_CALCLData = new GridData();
//				tfMT_HT_CALCLData.widthHint = 127;
//				if(!decore) {
//					tfMT_HT_CALC = new Text(paTotaux, SWT.BORDER);
//					tfMT_HT_CALC.setLayoutData(tfMT_HT_CALCLData);
//					tfMT_HT_CALC.setText("MT_HT_CALC");
//				} else {
//					fieldMT_HT_CALC = new DecoratedField(paTotaux, SWT.BORDER, new TextControlCreator());
//					tfMT_HT_CALC = (Text)fieldMT_HT_CALC.getControl();
//					fieldMT_HT_CALC.getLayoutControl().setLayoutData(tfMT_HT_CALCLData);
//				}
//			}
//			{
//				laMT_TTC_CALC = new Label(paTotaux, SWT.NONE);
//				laMT_TTC_CALC.setText("Montant TTC");
//			}
//			{
//				GridData tfMT_TTC_CALCLData = new GridData();
//				tfMT_TTC_CALCLData.widthHint = 125;
//				if(!decore) {
//					tfMT_TTC_CALC = new Text(paTotaux, SWT.BORDER);
//					tfMT_TTC_CALC.setLayoutData(tfMT_TTC_CALCLData);
//					tfMT_TTC_CALC.setText("MT_TTC_CALC");
//				} else {
//					fieldMT_TTC_CALC = new DecoratedField(paTotaux, SWT.BORDER, new TextControlCreator());
//					tfMT_TTC_CALC = (Text)fieldMT_TTC_CALC.getControl();
//					fieldMT_TTC_CALC.getLayoutControl().setLayoutData(tfMT_TTC_CALCLData);
//				}
//			}
//
//			{
//				GridData cbImprimerLData = new GridData();
//				cbImprimerLData.horizontalSpan = 2;
//				cbImprimerLData.heightHint = 16;
//				cbImprimerLData.horizontalAlignment = GridData.FILL;
//
//				if(!decore) {
//					cbImprimer = new Button(paTotaux, SWT.CHECK	| SWT.LEFT);
//					cbImprimer.setLayoutData(cbImprimerLData);
//				} else {
//					fieldImprimer = new DecoratedField(paTotaux, SWT.CHECK | SWT.LEFT, new ButtonControlCreator());
//					cbImprimer = (Button)fieldImprimer.getControl();
//					fieldImprimer.getLayoutControl().setLayoutData(cbImprimerLData);
//				}
//				cbImprimer.setText("Imprimer");
//			}
//
//			{
//				laMT_TVA_CALC = new Label(paTotaux, SWT.NONE);
//				laMT_TVA_CALC.setText("Montant TVA");
//			}
//			{
//				GridData tfMT_TVA_CALCLData = new GridData();
//				tfMT_TVA_CALCLData.widthHint = 127;
//				if(!decore) {
//					tfMT_TVA_CALC = new Text(paTotaux, SWT.BORDER);
//					tfMT_TVA_CALC.setLayoutData(tfMT_TVA_CALCLData);
//					tfMT_TVA_CALC.setText("MT_TVA_CALC");
//				} else {
//					fieldMT_TVA_CALC = new DecoratedField(paTotaux, SWT.BORDER, new TextControlCreator());
//					tfMT_TVA_CALC = (Text)fieldMT_TVA_CALC.getControl();
//					fieldMT_TVA_CALC.getLayoutControl().setLayoutData(tfMT_TVA_CALCLData);
//				}
//			}
//			{
//				laREGLE_FACTURE = new Label(paTotaux, SWT.NONE);
//				laREGLE_FACTURE.setText("Réglé");
//			}
//			{
//				GridData tfREGLE_FACTURELData = new GridData();
//				tfREGLE_FACTURELData.widthHint = 125;
//				if(!decore) {
//					tfREGLE_FACTURE = new Text(paTotaux, SWT.BORDER);
//					tfREGLE_FACTURE.setLayoutData(tfREGLE_FACTURELData);
//					tfREGLE_FACTURE.setText("REGLE_FACTURE");
//				} else {
//					fieldREGLE_FACTURE = new DecoratedField(paTotaux, SWT.BORDER, new TextControlCreator());
//					tfREGLE_FACTURE = (Text)fieldREGLE_FACTURE.getControl();
//					fieldREGLE_FACTURE.getLayoutControl().setLayoutData(tfREGLE_FACTURELData);
//				}
//			}
//			{
//				laCODE_T_PAIEMENT = new Label(paTotaux, SWT.NONE);
//				laCODE_T_PAIEMENT.setText("Type paiement");
//			}
//			{
//				GridData tfCODE_T_PAIEMENTLData = new GridData();
//				tfCODE_T_PAIEMENTLData.widthHint = 90;
//				if(!decore) {
//					tfCODE_T_PAIEMENT = new Text(paTotaux, SWT.BORDER);
//					tfCODE_T_PAIEMENT.setLayoutData(tfCODE_T_PAIEMENTLData);
//					tfCODE_T_PAIEMENT.setText("CODE_T_PAIEMENT");
//				} else {
//					fieldCODE_T_PAIEMENT = new DecoratedField(paTotaux, SWT.BORDER, new TextControlCreator());
//					tfCODE_T_PAIEMENT = (Text)fieldCODE_T_PAIEMENT.getControl();
//					fieldCODE_T_PAIEMENT.getLayoutControl().setLayoutData(tfCODE_T_PAIEMENTLData);
//				}
//			}
//		}
//		{
//			GridData paBtnLData = new GridData();
//			paBtnLData.horizontalAlignment = GridData.CENTER;
//			paBtnLData.grabExcessHorizontalSpace = true;
//			paBtnLData.horizontalSpan = 2;
//			paBtn = new PaBtn(this, SWT.NONE);
//			paBtn.setLayoutData(paBtnLData);
//		}
//
//		if(this == null) {
//
//		}
//		return this;
//	}
	

	public Table getTableTVA() {
		return tableTVA;
	}

	public Composite getPaTotaux() {
		return paTotaux;
	}

	public Label getLaTX_REM_HT_BONCDE() {
		return laTX_REM_HT_BONCDE;
	}

	public Text getTfCODE_T_PAIEMENT() {
		return tfCODE_T_PAIEMENT;
	}

	public Label getLaCODE_T_PAIEMENT() {
		return laCODE_T_PAIEMENT;
	}

//	public Text getTfREGLE_PROFORMA() {
//		return tfREGLE_PROFORMA;
//	}
//
//	public Label getLaREGLE_PROFORMA() {
//		return laREGLE_PROFORMA;
//	}

	public Text getTfMT_TVA_CALC() {
		return tfMT_TVA_CALC;
	}

	public Label getLaMT_TVA_CALC() {
		return laMT_TVA_CALC;
	}

	public Button getCbImprimer() {
		return cbImprimer;
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

	public Text getTfTX_REM_TTC_BONCDE() {
		return tfTX_REM_TTC_BONCDE;
	}

	public Label getLaTX_REM_TTC_BONCDE() {
		return laTX_REM_TTC_BONCDE;
	}

	public Text getTfTX_REM_HT_BONCDE() {
		return tfTX_REM_HT_BONCDE;
	}

	public DecoratedField getFieldMT_TTC_CALC() {
		return fieldMT_TTC_CALC;
	}

	public DecoratedField getFieldTTC() {
		return fieldTTC;
	}

	public DecoratedField getFieldTX_REM_TTC_BONCDE() {
		return fieldTX_REM_TTC_BONCDE;
	}

	public DecoratedField getFieldMT_HT_CALC() {
		return fieldMT_HT_CALC;
	}

	public DecoratedField getFieldTX_REM_HT_BONCDE() {
		return fieldTX_REM_HT_BONCDE;
	}

//	public DecoratedField getFieldREGLE_PROFORMA() {
//		return fieldREGLE_PROFORMA;
//	}

	public DecoratedField getFieldCODE_T_PAIEMENT() {
		return fieldCODE_T_PAIEMENT;
	}

	public DecoratedField getFieldMT_TVA_CALC() {
		return fieldMT_TVA_CALC;
	}

	public DecoratedField getFieldImprimer() {
		return fieldImprimer;
	}
	
	public Label getLaMessage() {
		return laMessage;
	}
	
	public PaBtnAvecAssistant getPaBtnAvecAssistant() {
		return paBtnAvecAssistant;
	}

	public ExpandBar getExpandBar() {
		return expandBar;
	}

	public Text getTfREGLE_DOCUMENT() {
		return tfREGLE_DOCUMENT;
	}

	public Label getLaREGLE_DOCUMENT() {
		return laREGLE_DOCUMENT;
	}

	public DecoratedField getFieldREGLE_DOCUMENT() {
		return fieldREGLE_DOCUMENT;
	}

}

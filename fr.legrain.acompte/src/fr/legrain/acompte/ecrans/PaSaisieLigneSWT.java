package fr.legrain.acompte.ecrans;
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
public class PaSaisieLigneSWT extends org.eclipse.swt.widgets.Composite {

	 {
		 //Register as a resource user - SWTResourceManager will
		 //handle the obtaining and disposing of resources
		 SWTResourceManager.registerResourceUser(this);
	 }
	 
	 private Label laNUM_LIGNE_L_FACTURE;
	private Text tfNUM_LIGNE_L_FACTURE;
	private Label laCODE_ARTICLE;
	private Label laLIB_L_FACTURE;
	private Text tfLIB_L_FACTURE;
	private Label laMT_TTC_L_FACTURE;
	private PaBtnAvecAssistant paBtnAvecAssistant;
	private Text tfMT_TTC_L_FACTURE;
	private Table grille;
	private Composite paSaisie;
	private Text tfREM_TX_L_FACTURE;
	private Label laREM_TX_L_FACTURE;
	private Text tfMT_HT_L_FACTURE;
	private Label laMT_HT_L_FACTURE;
	private Text tfPRIX_U_L_FACTURE;
	private Label laPRIX_U_L_FACTURE;
	private Text tfU2_L_FACTURE;
	private Label laU2_L_FACTURE;
	private Text tfU1_L_FACTURE;
	private Label laU1_L_FACTURE;
	private Text tfQTE_L_FACTURE;
	private Text tfQTE2_L_FACTURE;
	private Label laQTE_L_FACTURE;
	private Label laQTE2_L_FACTURE;
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

	private DecoratedField fieldNUM_LIGNE_L_FACTURE;
	private DecoratedField fieldLIB_L_FACTURE;
	private DecoratedField fieldREM_TX_L_FACTURE;
	private DecoratedField fieldMT_HT_L_FACTURE;
	private Group paTotauxLignes;
	private DecoratedField fieldMT_TTC_L_FACTURE;
	private DecoratedField fieldPRIX_U_L_FACTURE;
	private DecoratedField fieldU2_L_FACTURE;
	private DecoratedField fieldU1_L_FACTURE;
	private DecoratedField fieldQTE_L_FACTURE;
	private DecoratedField fieldQTE2_L_FACTURE;
	private DecoratedField fieldCODE_TIERS;
	private DecoratedField fieldCODE_ARTICLE;
	
//	private DecoratedField fieldMT_TTC_CALC;
//	private DecoratedField fieldMT_HT_CALC;
//	private DecoratedField fieldMT_TVA_CALC;
//	private DecoratedField fieldNET_HT_CALC;
//	private DecoratedField fieldNET_TTC_CALC;
	
	final private boolean decore = LgrMess.isDECORE();
	//final private boolean decore = false;
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
		PaSaisieLigneSWT inst = new PaSaisieLigneSWT(shell, SWT.NULL);
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

	public PaSaisieLigneSWT(org.eclipse.swt.widgets.Composite parent, int style) {
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
					laNUM_LIGNE_L_FACTURE = new Label(paSaisie, SWT.NONE);
					laNUM_LIGNE_L_FACTURE.setText("N°");
				}
				{
					laCODE_ARTICLE = new Label(paSaisie, SWT.NONE);
					laCODE_ARTICLE.setText("Code article");
				}
				{
					laLIB_L_FACTURE = new Label(paSaisie, SWT.NONE);
					laLIB_L_FACTURE.setText("Libellé");
				}
				{
					laQTE_L_FACTURE = new Label(paSaisie, SWT.NONE);
					laQTE_L_FACTURE.setText("Qté 1");
				}
				{
					laU1_L_FACTURE = new Label(paSaisie, SWT.NONE);
					laU1_L_FACTURE.setText("U1");
				}
				{
					laQTE2_L_FACTURE = new Label(paSaisie, SWT.NONE);
					laQTE2_L_FACTURE.setText("Qté 2");
				}				
				{
					laU2_L_FACTURE = new Label(paSaisie, SWT.NONE);
					laU2_L_FACTURE.setText("U2");
				}
				{
					laPRIX_U_L_FACTURE = new Label(paSaisie, SWT.NONE);
					laPRIX_U_L_FACTURE.setText("Prix unitaire");
				}
				{
					laREM_TX_L_FACTURE = new Label(paSaisie, SWT.NONE);
					laREM_TX_L_FACTURE.setText("Remise %");
				}
				{
					laMT_HT_L_FACTURE = new Label(paSaisie, SWT.NONE);
					GridData laMT_HT_L_FACTURELData = new GridData();
					laMT_HT_L_FACTURELData.widthHint = 86;
					laMT_HT_L_FACTURELData.heightHint = 13;
					laMT_HT_L_FACTURE.setLayoutData(laMT_HT_L_FACTURELData);
					laMT_HT_L_FACTURE.setText("Montant HT");
				}
				{
					laMT_TTC_L_FACTURE = new Label(paSaisie, SWT.NONE);
					GridData laMT_TTC_L_FACTURELData = new GridData();
					laMT_TTC_L_FACTURELData.horizontalSpan = 2;
					laMT_TTC_L_FACTURE.setText("Montant TTC");
				}
				
				{
					GridData tfNUM_LIGNE_L_FACTURELData = new GridData();
					tfNUM_LIGNE_L_FACTURELData.widthHint = 23;
					if(!decore){
						tfNUM_LIGNE_L_FACTURE = new Text(paSaisie, SWT.BORDER);
						tfNUM_LIGNE_L_FACTURE.setLayoutData(tfNUM_LIGNE_L_FACTURELData);
						tfNUM_LIGNE_L_FACTURE.setText("NUM_LIGNE");

					}else {
						fieldNUM_LIGNE_L_FACTURE = new DecoratedField(paSaisie, SWT.BORDER, new TextControlCreator());
						tfNUM_LIGNE_L_FACTURE = (Text)fieldNUM_LIGNE_L_FACTURE.getControl();
						fieldNUM_LIGNE_L_FACTURE.getLayoutControl().setLayoutData(tfNUM_LIGNE_L_FACTURELData);
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
					GridData tfLIB_L_FACTURELData = new GridData();
					tfLIB_L_FACTURELData.horizontalAlignment = GridData.FILL;
					tfLIB_L_FACTURELData.grabExcessHorizontalSpace = true;
					if(!decore) {
						tfLIB_L_FACTURE = new Text(paSaisie, SWT.BORDER);
						tfLIB_L_FACTURE.setLayoutData(tfLIB_L_FACTURELData);
						tfLIB_L_FACTURE.setText("LIB_L_FACTURE");
					} else {
						fieldLIB_L_FACTURE = new DecoratedField(paSaisie, SWT.BORDER, new TextControlCreator());
						tfLIB_L_FACTURE = (Text)fieldLIB_L_FACTURE.getControl();
						fieldLIB_L_FACTURE.getLayoutControl().setLayoutData(tfLIB_L_FACTURELData);
					}
				}
				{
					GridData tfQTE_L_FACTURELData = new GridData();
					tfQTE_L_FACTURELData.widthHint = 67;
					if(!decore){
						tfQTE_L_FACTURE = new Text(paSaisie, SWT.BORDER);
						tfQTE_L_FACTURE.setLayoutData(tfQTE_L_FACTURELData);
						tfQTE_L_FACTURE.setText("QTE_L_FACTURE");
					}else{
						fieldQTE_L_FACTURE = new DecoratedField(paSaisie, SWT.BORDER, new TextControlCreator());
						tfQTE_L_FACTURE = (Text)fieldQTE_L_FACTURE.getControl();
						fieldQTE_L_FACTURE.getLayoutControl().setLayoutData(tfQTE_L_FACTURELData);
					}
				}
				{
					GridData tfU1_L_FACTURELData = new GridData();
					tfU1_L_FACTURELData.widthHint = 44;
					if(!decore){
						tfU1_L_FACTURE = new Text(paSaisie, SWT.BORDER);
						tfU1_L_FACTURE.setLayoutData(tfU1_L_FACTURELData);
						tfU1_L_FACTURE.setText("U1_L_FACTURE");
					}else{
						fieldU1_L_FACTURE = new DecoratedField(paSaisie, SWT.BORDER, new TextControlCreator());
						tfU1_L_FACTURE = (Text)fieldU1_L_FACTURE.getControl();
						fieldU1_L_FACTURE.getLayoutControl().setLayoutData(tfU1_L_FACTURELData);
					}
				}
				{
					GridData tfQTE2_L_FACTURELData = new GridData();
					tfQTE2_L_FACTURELData.widthHint = 67;
					if(!decore){
						tfQTE2_L_FACTURE = new Text(paSaisie, SWT.BORDER);
						tfQTE2_L_FACTURE.setLayoutData(tfQTE2_L_FACTURELData);
						tfQTE2_L_FACTURE.setText("QTE2_L_FACTURE");
					}else{
						fieldQTE2_L_FACTURE = new DecoratedField(paSaisie, SWT.BORDER, new TextControlCreator());
						tfQTE2_L_FACTURE = (Text)fieldQTE2_L_FACTURE.getControl();
						fieldQTE2_L_FACTURE.getLayoutControl().setLayoutData(tfQTE2_L_FACTURELData);
					}
				}
				
				{
					GridData tfU2_L_FACTURELData = new GridData();
					tfU2_L_FACTURELData.widthHint = 47;
					if(!decore){
						tfU2_L_FACTURE = new Text(paSaisie, SWT.BORDER);
						tfU2_L_FACTURE.setLayoutData(tfU2_L_FACTURELData);
						tfU2_L_FACTURE.setText("U2_L_FACTURE");
					}else{
						fieldU2_L_FACTURE = new DecoratedField(paSaisie, SWT.BORDER, new TextControlCreator());
						tfU2_L_FACTURE = (Text)fieldU2_L_FACTURE.getControl();
						fieldU2_L_FACTURE.getLayoutControl().setLayoutData(tfU2_L_FACTURELData);
					}
				}
				{
					GridData tfPRIX_U_L_FACTURELData = new GridData();
					tfPRIX_U_L_FACTURELData.widthHint = 70;
					if(!decore){
						tfPRIX_U_L_FACTURE = new Text(paSaisie, SWT.BORDER);
						tfPRIX_U_L_FACTURE.setLayoutData(tfPRIX_U_L_FACTURELData);
						tfPRIX_U_L_FACTURE.setText("PRIX_U_L_FACTURE");
					}else{
						fieldPRIX_U_L_FACTURE = new DecoratedField(paSaisie, SWT.BORDER, new TextControlCreator());
						tfPRIX_U_L_FACTURE = (Text)fieldPRIX_U_L_FACTURE.getControl();
						fieldPRIX_U_L_FACTURE.getLayoutControl().setLayoutData(tfPRIX_U_L_FACTURELData);
					}
				}
				{
					GridData tfREM_TX_L_FACTURELData = new GridData();
					tfREM_TX_L_FACTURELData.widthHint = 54;
					if(!decore){
						tfREM_TX_L_FACTURE = new Text(paSaisie, SWT.BORDER);
						tfREM_TX_L_FACTURE.setLayoutData(tfREM_TX_L_FACTURELData);
						tfREM_TX_L_FACTURE.setText("REM_TX_L_FACTURE");
					}else{
						fieldREM_TX_L_FACTURE = new DecoratedField(paSaisie, SWT.BORDER, new TextControlCreator());
						tfREM_TX_L_FACTURE = (Text)fieldREM_TX_L_FACTURE.getControl();
						fieldREM_TX_L_FACTURE.getLayoutControl().setLayoutData(tfREM_TX_L_FACTURELData);
					}
				}
				{
					GridData tfMT_HT_L_FACTURELData = new GridData();
					tfMT_HT_L_FACTURELData.horizontalAlignment = GridData.CENTER;
					tfMT_HT_L_FACTURELData.widthHint = 106;
					if(!decore){
						tfMT_HT_L_FACTURE = new Text(paSaisie, SWT.BORDER);
						tfMT_HT_L_FACTURE.setLayoutData(tfMT_HT_L_FACTURELData);
						tfMT_HT_L_FACTURE.setText("MT_HT_L_FACTURE");
					}else{
						fieldMT_HT_L_FACTURE = new DecoratedField(paSaisie, SWT.BORDER, new TextControlCreator());
						tfMT_HT_L_FACTURE = (Text)fieldMT_HT_L_FACTURE.getControl();
						fieldMT_HT_L_FACTURE.getLayoutControl().setLayoutData(tfMT_HT_L_FACTURELData);
					}
				}
				{
					GridData tfMT_TTC_L_FACTURELData = new GridData();
					tfMT_TTC_L_FACTURELData.horizontalAlignment = GridData.CENTER;
					tfMT_TTC_L_FACTURELData.widthHint = 120;
					if(!decore){
						tfMT_TTC_L_FACTURE = new Text(paSaisie, SWT.BORDER);
						tfMT_TTC_L_FACTURE.setLayoutData(tfMT_TTC_L_FACTURELData);
						tfMT_TTC_L_FACTURE.setText("MT_TTC_L_FACTURE");

					}else{
						fieldMT_TTC_L_FACTURE = new DecoratedField(paSaisie, SWT.BORDER, new TextControlCreator());
						tfMT_TTC_L_FACTURE = (Text)fieldMT_TTC_L_FACTURE.getControl();
						fieldMT_TTC_L_FACTURE.getLayoutControl().setLayoutData(tfMT_TTC_L_FACTURELData);
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

	public Label getLaNUM_LIGNE_L_FACTURE() {
		return laNUM_LIGNE_L_FACTURE;
	}

	public Text getTfNUM_LIGNE_L_FACTURE() {
		return tfNUM_LIGNE_L_FACTURE;
	}

	public Label getLaCODE_ARTICLE() {
		return laCODE_ARTICLE;
	}

	public Text getTfCODE_ARTICLE() {
		return tfCODE_ARTICLE;
	}

	public Label getLaLIB_L_FACTURE() {
		return laLIB_L_FACTURE;
	}

	public Text getTfLIB_L_FACTURE() {
		return tfLIB_L_FACTURE;
	}

	public Label getLaQTE_L_FACTURE() {
		return laQTE_L_FACTURE;
	}

	public Label getLaU1_L_FACTURE() {
		return laU1_L_FACTURE;
	}

	public Text getTfMT_HT_L_FACTURE() {
		return tfMT_HT_L_FACTURE;
	}

	public Composite getPaSaisie() {
		return paSaisie;
	}

	public Table getGrille() {
		return grille;
	}

	public Label getLaMT_HT_L_FACTURE() {
		return laMT_HT_L_FACTURE;
	}

	public Label getLaPRIX_U_L_FACTURE() {
		return laPRIX_U_L_FACTURE;
	}

	public Label getLaREM_TX_L_FACTURE() {
		return laREM_TX_L_FACTURE;
	}

	public Label getLaU2_L_FACTURE() {
		return laU2_L_FACTURE;
	}

	public Text getTfPRIX_U_L_FACTURE() {
		return tfPRIX_U_L_FACTURE;
	}

	public Text getTfQTE_L_FACTURE() {
		return tfQTE_L_FACTURE;
	}

	public Text getTfREM_TX_L_FACTURE() {
		return tfREM_TX_L_FACTURE;
	}

	public Text getTfU1_L_FACTURE() {
		return tfU1_L_FACTURE;
	}

	public Text getTfU2_L_FACTURE() {
		return tfU2_L_FACTURE;
	}

	public DecoratedField getFieldCODE_ARTICLE() {
		return fieldCODE_ARTICLE;
	}

	public DecoratedField getFieldCODE_TIERS() {
		return fieldCODE_TIERS;
	}

	public DecoratedField getFieldLIB_L_FACTURE() {
		return fieldLIB_L_FACTURE;
	}

	public DecoratedField getFieldMT_HT_L_FACTURE() {
		return fieldMT_HT_L_FACTURE;
	}

	public DecoratedField getFieldNUM_LIGNE_L_FACTURE() {
		return fieldNUM_LIGNE_L_FACTURE;
	}

	public DecoratedField getFieldPRIX_U_L_FACTURE() {
		return fieldPRIX_U_L_FACTURE;
	}

	public DecoratedField getFieldQTE_L_FACTURE() {
		return fieldQTE_L_FACTURE;
	}

	public DecoratedField getFieldREM_TX_L_FACTURE() {
		return fieldREM_TX_L_FACTURE;
	}

	public DecoratedField getFieldU1_L_FACTURE() {
		return fieldU1_L_FACTURE;
	}

	public DecoratedField getFieldU2_L_FACTURE() {
		return fieldU2_L_FACTURE;
	}

	public Text getTfMT_TTC_L_FACTURE() {
		return tfMT_TTC_L_FACTURE;
	}

	public Label getLaMT_TTC_L_FACTURE() {
		return laMT_TTC_L_FACTURE;
	}

	public DecoratedField getFieldMT_TTC_L_FACTURE() {
		return fieldMT_TTC_L_FACTURE;
	}

	public void setFieldMT_TTC_L_FACTURE(DecoratedField fieldMT_TTC_L_FACTURE) {
		this.fieldMT_TTC_L_FACTURE = fieldMT_TTC_L_FACTURE;
	}
	
	public PaBtnAvecAssistant getPaBtnAvecAssistant() {
		return paBtnAvecAssistant;
	}

	public Text getTfNET_TVA_CALC() {
		return tfNET_TVA_CALC;
	}

	public Label getLaNET_TVA_CALC() {
		return laNET_TVA_CALC;
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

//	public DecoratedField getFieldMT_TTC_CALC() {
//		return fieldMT_TTC_CALC;
//	}
//
//	public DecoratedField getFieldMT_HT_CALC() {
//		return fieldMT_HT_CALC;
//	}
//
//	public DecoratedField getFieldMT_TVA_CALC() {
//		return fieldMT_TVA_CALC;
//	}

	public boolean isDecore() {
		return decore;
	}

	public Text getTfQTE2_L_FACTURE() {
		return tfQTE2_L_FACTURE;
	}

	public DecoratedField getFieldQTE2_L_FACTURE() {
		return fieldQTE2_L_FACTURE;
	}

	public Label getLaQTE2_L_FACTURE() {
		return laQTE2_L_FACTURE;
	}

	public Text getTfNET_HT_CALC() {
		return tfNET_HT_CALC;
	}

	public Label getLaNET_HT_CALC() {
		return laNET_HT_CALC;
	}

//	public DecoratedField getFieldNET_HT_CALC() {
//		return fieldNET_HT_CALC;
//	}

	public Text getTfNET_TTC_CALC() {
		return tfNET_TTC_CALC;
	}

	public Label getLaNET_TTC_CALC() {
		return laNET_TTC_CALC;
	}

//	public DecoratedField getFieldNET_TTC_CALC() {
//		return fieldNET_TTC_CALC;
//	}

}

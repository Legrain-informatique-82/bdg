package fr.legrain.boncde.ecran;
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
public class PaSaisieLigneBoncde extends org.eclipse.swt.widgets.Composite {

	 {
		 //Register as a resource user - SWTResourceManager will
		 //handle the obtaining and disposing of resources
		 SWTResourceManager.registerResourceUser(this);
	 }
	 
	 private Label laNUM_LIGNE_L_BONCDE;
	private Text tfNUM_LIGNE_L_BONCDE;
	private Label laCODE_ARTICLE;
	private Label laLIB_L_BONCDE;
	private Text tfLIB_L_BONCDE;
	private Label laMT_TTC_L_BONCDE;
	private PaBtnAvecAssistant paBtnAvecAssistant;
	private Text tfMT_TTC_L_BONCDE;
	private Table grille;
	private Composite paSaisie;
	private Text tfREM_TX_L_BONCDE;
	private Label laREM_TX_L_BONCDE;
	private Text tfMT_HT_L_BONCDE;
	private Label laMT_HT_L_BONCDE;
	private Text tfPRIX_U_L_BONCDE;
	private Label laPRIX_U_L_BONCDE;
	private Text tfU2_L_BONCDE;
	private Label laU2_L_BONCDE;
	private Text tfU1_L_BONCDE;
	private Label laU1_L_BONCDE;
	private Text tfQTE_L_BONCDE;
	private Label laQTE_L_BONCDE;
	private Text tfQTE2_L_BONCDE;
	private Label laQTE2_L_BONCDE;
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

	private DecoratedField fieldNUM_LIGNE_L_BONCDE;
	private DecoratedField fieldLIB_L_BONCDE;
	private DecoratedField fieldREM_TX_L_BONCDE;
	private DecoratedField fieldMT_HT_L_BONCDE;
	private Group paTotauxLignes;	
	private DecoratedField fieldMT_TTC_L_BONCDE;
	private DecoratedField fieldPRIX_U_L_BONCDE;
	private DecoratedField fieldU2_L_BONCDE;
	private DecoratedField fieldU1_L_BONCDE;
	private DecoratedField fieldQTE_L_BONCDE;
	private DecoratedField fieldQTE2_L_BONCDE;
	private DecoratedField fieldCODE_ARTICLE;
	private DecoratedField fieldCODE_TIERS;

	
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
		PaSaisieLigneBoncde inst = new PaSaisieLigneBoncde(shell, SWT.NULL);
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

	public PaSaisieLigneBoncde(org.eclipse.swt.widgets.Composite parent, int style) {
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
					laNUM_LIGNE_L_BONCDE = new Label(paSaisie, SWT.NONE);
					laNUM_LIGNE_L_BONCDE.setText("N°");
				}
				{
					laCODE_ARTICLE = new Label(paSaisie, SWT.NONE);
					laCODE_ARTICLE.setText("Code article");
				}
				{
					laLIB_L_BONCDE = new Label(paSaisie, SWT.NONE);
					laLIB_L_BONCDE.setText("Libellé");
				}
				{
					laQTE_L_BONCDE = new Label(paSaisie, SWT.NONE);
					laQTE_L_BONCDE.setText("Qté 1");
				}
				{
					laU1_L_BONCDE = new Label(paSaisie, SWT.NONE);
					laU1_L_BONCDE.setText("U1");
				}
				{
					laQTE2_L_BONCDE = new Label(paSaisie, SWT.NONE);
					laQTE2_L_BONCDE.setText("Qté 2");
				}				
				{
					laU2_L_BONCDE = new Label(paSaisie, SWT.NONE);
					laU2_L_BONCDE.setText("U2");
				}
				{
					laPRIX_U_L_BONCDE = new Label(paSaisie, SWT.NONE);
					laPRIX_U_L_BONCDE.setText("Prix unitaire");
				}
				{
					laREM_TX_L_BONCDE = new Label(paSaisie, SWT.NONE);
					laREM_TX_L_BONCDE.setText("Remise");
				}
				{
					laMT_HT_L_BONCDE = new Label(paSaisie, SWT.NONE);
					GridData laMT_HT_L_FACTURELData = new GridData();
					laMT_HT_L_FACTURELData.widthHint = 86;
					laMT_HT_L_FACTURELData.heightHint = 13;
					laMT_HT_L_BONCDE.setLayoutData(laMT_HT_L_FACTURELData);
					laMT_HT_L_BONCDE.setText("Montant HT");
				}
				{
					laMT_TTC_L_BONCDE = new Label(paSaisie, SWT.NONE);
					GridData laMT_TTC_L_FACTURELData = new GridData();
					laMT_TTC_L_FACTURELData.horizontalSpan = 2;
					laMT_TTC_L_BONCDE.setText("Montant TTC");
				}
				
				{
					GridData tfNUM_LIGNE_L_FACTURELData = new GridData();
					tfNUM_LIGNE_L_FACTURELData.widthHint = 23;
					if(!decore){
						tfNUM_LIGNE_L_BONCDE = new Text(paSaisie, SWT.BORDER);
						tfNUM_LIGNE_L_BONCDE.setLayoutData(tfNUM_LIGNE_L_FACTURELData);
						tfNUM_LIGNE_L_BONCDE.setText("NUM_LIGNE");

					}else {
						fieldNUM_LIGNE_L_BONCDE = new DecoratedField(paSaisie, SWT.BORDER, new TextControlCreator());
						tfNUM_LIGNE_L_BONCDE = (Text)fieldNUM_LIGNE_L_BONCDE.getControl();
						fieldNUM_LIGNE_L_BONCDE.getLayoutControl().setLayoutData(tfNUM_LIGNE_L_FACTURELData);
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
						tfLIB_L_BONCDE = new Text(paSaisie, SWT.BORDER);
						tfLIB_L_BONCDE.setLayoutData(tfLIB_L_FACTURELData);
						tfLIB_L_BONCDE.setText("LIB_L_BONCDE");
					} else {
						fieldLIB_L_BONCDE = new DecoratedField(paSaisie, SWT.BORDER, new TextControlCreator());
						tfLIB_L_BONCDE = (Text)fieldLIB_L_BONCDE.getControl();
						fieldLIB_L_BONCDE.getLayoutControl().setLayoutData(tfLIB_L_FACTURELData);
					}
				}
				{
					GridData tfQTE_L_FACTURELData = new GridData();
					tfQTE_L_FACTURELData.widthHint = 67;
					if(!decore){
						tfQTE_L_BONCDE = new Text(paSaisie, SWT.BORDER);
						tfQTE_L_BONCDE.setLayoutData(tfQTE_L_FACTURELData);
						tfQTE_L_BONCDE.setText("QTE_L_BONCDE");
					}else{
						fieldQTE_L_BONCDE = new DecoratedField(paSaisie, SWT.BORDER, new TextControlCreator());
						tfQTE_L_BONCDE = (Text)fieldQTE_L_BONCDE.getControl();
						fieldQTE_L_BONCDE.getLayoutControl().setLayoutData(tfQTE_L_FACTURELData);
					}
				}
				{
					GridData tfU1_L_FACTURELData = new GridData();
					tfU1_L_FACTURELData.widthHint = 44;
					if(!decore){
						tfU1_L_BONCDE = new Text(paSaisie, SWT.BORDER);
						tfU1_L_BONCDE.setLayoutData(tfU1_L_FACTURELData);
						tfU1_L_BONCDE.setText("U1_L_BONCDE");
					}else{
						fieldU1_L_BONCDE = new DecoratedField(paSaisie, SWT.BORDER, new TextControlCreator());
						tfU1_L_BONCDE = (Text)fieldU1_L_BONCDE.getControl();
						fieldU1_L_BONCDE.getLayoutControl().setLayoutData(tfU1_L_FACTURELData);
					}
				}
				{
					GridData tfQTE2_L_PROFORMALData = new GridData();
					tfQTE2_L_PROFORMALData.widthHint = 67;
					if(!decore){
						tfQTE2_L_BONCDE = new Text(paSaisie, SWT.BORDER);
						tfQTE2_L_BONCDE.setLayoutData(tfQTE2_L_PROFORMALData);
						tfQTE2_L_BONCDE.setText("QTE2_L_BONCDE");
					}else{
						fieldQTE2_L_BONCDE = new DecoratedField(paSaisie, SWT.BORDER, new TextControlCreator());
						tfQTE2_L_BONCDE = (Text)fieldQTE2_L_BONCDE.getControl();
						fieldQTE2_L_BONCDE.getLayoutControl().setLayoutData(tfQTE2_L_PROFORMALData);
					}
				}
				
				{
					GridData tfU2_L_FACTURELData = new GridData();
					tfU2_L_FACTURELData.widthHint = 47;
					if(!decore){
						tfU2_L_BONCDE = new Text(paSaisie, SWT.BORDER);
						tfU2_L_BONCDE.setLayoutData(tfU2_L_FACTURELData);
						tfU2_L_BONCDE.setText("U2_L_BONCDE");
					}else{
						fieldU2_L_BONCDE = new DecoratedField(paSaisie, SWT.BORDER, new TextControlCreator());
						tfU2_L_BONCDE = (Text)fieldU2_L_BONCDE.getControl();
						fieldU2_L_BONCDE.getLayoutControl().setLayoutData(tfU2_L_FACTURELData);
					}
				}
				{
					GridData tfPRIX_U_L_FACTURELData = new GridData();
					tfPRIX_U_L_FACTURELData.widthHint = 70;
					if(!decore){
						tfPRIX_U_L_BONCDE = new Text(paSaisie, SWT.BORDER);
						tfPRIX_U_L_BONCDE.setLayoutData(tfPRIX_U_L_FACTURELData);
						tfPRIX_U_L_BONCDE.setText("PRIX_U_L_BONCDE");
					}else{
						fieldPRIX_U_L_BONCDE = new DecoratedField(paSaisie, SWT.BORDER, new TextControlCreator());
						tfPRIX_U_L_BONCDE = (Text)fieldPRIX_U_L_BONCDE.getControl();
						fieldPRIX_U_L_BONCDE.getLayoutControl().setLayoutData(tfPRIX_U_L_FACTURELData);
					}
				}
				{
					GridData tfREM_TX_L_FACTURELData = new GridData();
					tfREM_TX_L_FACTURELData.widthHint = 54;
					if(!decore){
						tfREM_TX_L_BONCDE = new Text(paSaisie, SWT.BORDER);
						tfREM_TX_L_BONCDE.setLayoutData(tfREM_TX_L_FACTURELData);
						tfREM_TX_L_BONCDE.setText("REM_TX_L_PROFORMA");
					}else{
						fieldREM_TX_L_BONCDE = new DecoratedField(paSaisie, SWT.BORDER, new TextControlCreator());
						tfREM_TX_L_BONCDE = (Text)fieldREM_TX_L_BONCDE.getControl();
						fieldREM_TX_L_BONCDE.getLayoutControl().setLayoutData(tfREM_TX_L_FACTURELData);
					}
				}
				{
					GridData tfMT_HT_L_FACTURELData = new GridData();
					tfMT_HT_L_FACTURELData.horizontalAlignment = GridData.CENTER;
					tfMT_HT_L_FACTURELData.widthHint = 106;
					if(!decore){
						tfMT_HT_L_BONCDE = new Text(paSaisie, SWT.BORDER);
						tfMT_HT_L_BONCDE.setLayoutData(tfMT_HT_L_FACTURELData);
						tfMT_HT_L_BONCDE.setText("MT_HT_L_PROFORMA");
					}else{
						fieldMT_HT_L_BONCDE = new DecoratedField(paSaisie, SWT.BORDER, new TextControlCreator());
						tfMT_HT_L_BONCDE = (Text)fieldMT_HT_L_BONCDE.getControl();
						fieldMT_HT_L_BONCDE.getLayoutControl().setLayoutData(tfMT_HT_L_FACTURELData);
					}
				}
				{
					GridData tfMT_TTC_L_FACTURELData = new GridData();
					tfMT_TTC_L_FACTURELData.horizontalAlignment = GridData.CENTER;
					tfMT_TTC_L_FACTURELData.widthHint = 120;
					if(!decore){
						tfMT_TTC_L_BONCDE = new Text(paSaisie, SWT.BORDER);
						tfMT_TTC_L_BONCDE.setLayoutData(tfMT_TTC_L_FACTURELData);
						tfMT_TTC_L_BONCDE.setText("MT_TTC_L_BONCDE");

					}else{
						fieldMT_TTC_L_BONCDE = new DecoratedField(paSaisie, SWT.BORDER, new TextControlCreator());
						tfMT_TTC_L_BONCDE = (Text)fieldMT_TTC_L_BONCDE.getControl();
						fieldMT_TTC_L_BONCDE.getLayoutControl().setLayoutData(tfMT_TTC_L_FACTURELData);
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

	public Label getLaNUM_LIGNE_L_BONCDE() {
		return laNUM_LIGNE_L_BONCDE;
	}

	public Text getTfNUM_LIGNE_L_BONCDE() {
		return tfNUM_LIGNE_L_BONCDE;
	}

	public Label getLaCODE_ARTICLE() {
		return laCODE_ARTICLE;
	}

	public Text getTfCODE_ARTICLE() {
		return tfCODE_ARTICLE;
	}

	public Label getLaLIB_L_BONCDE() {
		return laLIB_L_BONCDE;
	}

	public Text getTfLIB_L_BONCDE() {
		return tfLIB_L_BONCDE;
	}

	public Label getLaQTE_L_BONCDE() {
		return laQTE_L_BONCDE;
	}

	public Label getLaU1_L_BONCDE() {
		return laU1_L_BONCDE;
	}

	public Text getTfMT_HT_L_BONCDE() {
		return tfMT_HT_L_BONCDE;
	}

	public Composite getPaSaisie() {
		return paSaisie;
	}

	public Table getGrille() {
		return grille;
	}

	public Label getLaMT_HT_L_BONCDE() {
		return laMT_HT_L_BONCDE;
	}

	public Label getLaPRIX_U_L_BONCDE() {
		return laPRIX_U_L_BONCDE;
	}

	public Label getLaREM_TX_L_BONCDE() {
		return laREM_TX_L_BONCDE;
	}

	public Label getLaU2_L_BONCDE() {
		return laU2_L_BONCDE;
	}

	public Text getTfPRIX_U_L_BONCDE() {
		return tfPRIX_U_L_BONCDE;
	}

	public Text getTfQTE_L_BONCDE() {
		return tfQTE_L_BONCDE;
	}

	public Text getTfREM_TX_L_BONCDE() {
		return tfREM_TX_L_BONCDE;
	}

	public Text getTfU1_L_BONCDE() {
		return tfU1_L_BONCDE;
	}

	public Text getTfU2_L_BONCDE() {
		return tfU2_L_BONCDE;
	}

	public DecoratedField getFieldCODE_ARTICLE() {
		return fieldCODE_ARTICLE;
	}

	public DecoratedField getFieldCODE_TIERS() {
		return fieldCODE_TIERS;
	}

	public DecoratedField getFieldLIB_L_BONCDE() {
		return fieldLIB_L_BONCDE;
	}

	public DecoratedField getFieldMT_HT_L_BONCDE() {
		return fieldMT_HT_L_BONCDE;
	}

	public DecoratedField getFieldNUM_LIGNE_L_BONCDE() {
		return fieldNUM_LIGNE_L_BONCDE;
	}

	public DecoratedField getFieldPRIX_U_L_BONCDE() {
		return fieldPRIX_U_L_BONCDE;
	}

	public DecoratedField getFieldQTE_L_BONCDE() {
		return fieldQTE_L_BONCDE;
	}

	public DecoratedField getFieldREM_TX_L_BONCDE() {
		return fieldREM_TX_L_BONCDE;
	}

	public DecoratedField getFieldU1_L_BONCDE() {
		return fieldU1_L_BONCDE;
	}

	public DecoratedField getFieldU2_L_BONCDE() {
		return fieldU2_L_BONCDE;
	}

	public Text getTfMT_TTC_L_BONCDE() {
		return tfMT_TTC_L_BONCDE;
	}

	public Label getLaMT_TTC_L_BONCDE() {
		return laMT_TTC_L_BONCDE;
	}

	public DecoratedField getFieldMT_TTC_L_BONCDE() {
		return fieldMT_TTC_L_BONCDE;
	}

	public void setFieldMT_TTC_L_BONCDE(DecoratedField fieldMT_TTC_L_FACTURE) {
		this.fieldMT_TTC_L_BONCDE = fieldMT_TTC_L_FACTURE;
	}
	
	public PaBtnAvecAssistant getPaBtnAvecAssistant() {
		return paBtnAvecAssistant;
	}

	public Text getTfQTE2_L_BONCDE() {
		return tfQTE2_L_BONCDE;
	}

	public Label getLaQTE2_L_BONCDE() {
		return laQTE2_L_BONCDE;
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

	public DecoratedField getFieldQTE2_L_BONCDE() {
		return fieldQTE2_L_BONCDE;
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

	public Group getPaTotauxLignes() {
		return paTotauxLignes;
	}



}

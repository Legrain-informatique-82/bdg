package fr.legrain.publipostagetiers.ecrans;

import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import fr.legrain.lib.gui.cdatetimeLgr;
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
public class PaCriterePublipostage extends org.eclipse.swt.widgets.Composite {
	private Group groupCritereDoc;
	private Combo cbListeDoc;
	private Text tfCritereDoc2;
	private Text tfCritereDoc;
	private Combo cbMots;
	private Combo cbChamps;
	private Text tfTiers;
	private Label label1;
	public Label getLabel1() {
		return label1;
	}

	private Button btnTousTiers;
	private Group groupCritereTiers;
	private Text tfCritereTiers;
	private Text tfCritereTiers2;
	private Combo cbMotsDoc;
	private Combo cbChampsDoc;
//	private Combo cbListeChamps;
//	private Combo cbMotsReserve;
	private Button btnValiderCritereTiers;
	private Group groupFactureNonRegle;

	private ExpandBar expandBarGroup;
//	private ExpandBar expandBar;
	private Label laMessage;
	private Button btnValiderCritereDoc;
	private Button btnSuivant;
	private Button btnFermer;
	private Composite paBtn;
	
	private DecoratedField fieldTiers;
	private DecoratedField fieldDateDebutPeriode;
	private DecoratedField fieldDateFinPeriode;
	private Button ccFiltreSelectionFinaleDoc;
	private Button ccFiltreFinal;

	final private boolean decore = LgrMess.isDECORE();
//	final private boolean decore = false;

	/**
	 * Auto-generated main method to display this 
	 * org.eclipse.swt.widgets.Composite inside a new Shell.
	 */
	public static void main(String[] args) {
		showGUI();
	}

	/**
	 * Overriding checkSubclass allows this class to extend org.eclipse.swt.widgets.Composite
	 */	
	protected void checkSubclass() {
	}

	/**
	 * Auto-generated method to display this 
	 * org.eclipse.swt.widgets.Composite inside a new Shell.
	 */
	public static void showGUI() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		PaCriterePublipostage inst = new PaCriterePublipostage(shell, SWT.NULL);
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

	public PaCriterePublipostage(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.numColumns = 2;
			this.setLayout(thisLayout);
			{
				groupCritereTiers = new Group(this, SWT.NONE);
				GridLayout group1Layout = new GridLayout();
				group1Layout.numColumns = 4;
				groupCritereTiers.setLayout(group1Layout);
				GridData group1LData = new GridData();
				group1LData.grabExcessHorizontalSpace = true;
				group1LData.horizontalAlignment = GridData.FILL;
				group1LData.verticalAlignment = GridData.FILL;
				groupCritereTiers.setLayoutData(group1LData);
				groupCritereTiers.setText("Critères tiers");
				{
					btnTousTiers = new Button(groupCritereTiers, SWT.CHECK | SWT.LEFT);
					GridData button1LData = new GridData();
					button1LData.horizontalSpan = 2;
					btnTousTiers.setLayoutData(button1LData);
					btnTousTiers.setText("Tous les tiers");
				}
				{
					label1 = new Label(groupCritereTiers, SWT.NONE);
					GridData label1LData = new GridData();
					label1LData.horizontalAlignment = GridData.END;
					label1.setLayoutData(label1LData);
					label1.setText("Ou le tiers");
				}
				{
					GridData text1LData = new GridData();
					text1LData.grabExcessHorizontalSpace = true;
					text1LData.horizontalAlignment = GridData.FILL;
					text1LData.verticalAlignment = GridData.FILL;
					tfTiers = new Text(groupCritereTiers, SWT.BORDER);
					tfTiers.setLayoutData(text1LData);
					tfTiers.setSize(144, 13);
				}
				{
					cbChamps = new Combo(groupCritereTiers, SWT.NONE);
					GridData combo1LData = new GridData();
					combo1LData.verticalAlignment = GridData.FILL;
					combo1LData.horizontalAlignment = GridData.FILL;
					cbChamps.setLayoutData(combo1LData);
					cbChamps.setText("liste des champs");
				}
				{
					cbMots = new Combo(groupCritereTiers, SWT.NONE);
					GridData combo2LData = new GridData();
					combo2LData.verticalAlignment = GridData.FILL;
					combo2LData.grabExcessHorizontalSpace = true;
					combo2LData.horizontalAlignment = GridData.FILL;
					cbMots.setLayoutData(combo2LData);
					cbMots.setText("comparer");
				}
				{
					tfCritereTiers = new Text(groupCritereTiers, SWT.BORDER);
					GridData tfCritereTiersLData = new GridData();
					tfCritereTiersLData.verticalAlignment = GridData.FILL;
					tfCritereTiersLData.horizontalAlignment = GridData.END;
					tfCritereTiersLData.widthHint = 205;
					tfCritereTiers.setLayoutData(tfCritereTiersLData);
					tfCritereTiers.setText("");
				}
				{
					tfCritereTiers2 = new Text(groupCritereTiers, SWT.BORDER);
					GridData tfCritereTiersLData = new GridData();
					tfCritereTiersLData.grabExcessHorizontalSpace = true;
					tfCritereTiersLData.horizontalAlignment = GridData.FILL;
					tfCritereTiersLData.verticalAlignment = GridData.FILL;
					tfCritereTiers2.setLayoutData(tfCritereTiersLData);
					tfCritereTiers2.setText("");
					tfCritereTiers2.setSize(144, 13);
				}
				{
					btnValiderCritereTiers = new Button(groupCritereTiers, SWT.PUSH | SWT.CENTER);
					GridData button2LData = new GridData();
					button2LData.widthHint = 97;
					button2LData.grabExcessHorizontalSpace = true;
					button2LData.verticalAlignment = GridData.FILL;
					btnValiderCritereTiers.setLayoutData(button2LData);
					btnValiderCritereTiers.setText("Valider");
				}
				{
					ccFiltreFinal = new Button(groupCritereTiers, SWT.CHECK | SWT.LEFT);
					GridData ccFiltreFinalLData = new GridData();
					ccFiltreFinal.setLayoutData(ccFiltreFinalLData);
					ccFiltreFinal.setText("Appliquer à la sélection finale");
				}
			}
			{
				groupCritereDoc = new Group(this, SWT.NONE);
				GridLayout groupParamLayout = new GridLayout();
				groupParamLayout.numColumns = 4;
				groupCritereDoc.setLayout(groupParamLayout);
				GridData groupParamLData = new GridData();
				groupParamLData.horizontalAlignment = GridData.FILL;
				groupParamLData.verticalAlignment = GridData.FILL;
				groupParamLData.grabExcessHorizontalSpace = true;
				groupCritereDoc.setLayoutData(groupParamLData);
				groupCritereDoc.setText("Critères documents");
				{
					GridData cbListeDocLData = new GridData();
					cbListeDocLData.horizontalSpan = 4;
					cbListeDocLData.widthHint = 118;
					cbListeDocLData.heightHint = 21;
					cbListeDoc = new Combo(groupCritereDoc, SWT.NONE);
					cbListeDoc.setLayoutData(cbListeDocLData);
				}
				
						{
							cbChampsDoc = new Combo(groupCritereDoc, SWT.NONE);
							GridData cbChampsLData = new GridData();
							cbChampsLData.verticalAlignment = GridData.FILL;
							cbChampsLData.horizontalAlignment = GridData.FILL;
							cbChampsDoc.setLayoutData(cbChampsLData);
							cbChampsDoc.setText("liste des champs");
						}
						{
							cbMotsDoc = new Combo(groupCritereDoc, SWT.NONE);
							GridData cbMotsLData = new GridData();
							cbMotsLData.verticalAlignment = GridData.FILL;
							cbMotsLData.horizontalAlignment = GridData.FILL;
							cbMotsLData.grabExcessHorizontalSpace = true;
							cbMotsDoc.setLayoutData(cbMotsLData);
							cbMotsDoc.setText("comparer");
						}
						{
							tfCritereDoc = new Text(groupCritereDoc, SWT.BORDER);
							GridData text2LData = new GridData();
							text2LData.verticalAlignment = GridData.FILL;
							text2LData.horizontalAlignment = GridData.END;
							text2LData.widthHint = 145;
							tfCritereDoc.setLayoutData(text2LData);
							tfCritereDoc.setText("");
							tfCritereDoc.setSize(156, 13);
						}
						{
							tfCritereDoc2 = new Text(groupCritereDoc, SWT.BORDER);
							GridData text3LData = new GridData();
							text3LData.grabExcessHorizontalSpace = true;
							text3LData.verticalAlignment = GridData.FILL;
							text3LData.horizontalAlignment = GridData.FILL;
							tfCritereDoc2.setLayoutData(text3LData);
							tfCritereDoc2.setText("");
							tfCritereDoc2.setSize(156, 13);
						}

						{
							btnValiderCritereDoc = new Button(groupCritereDoc, SWT.PUSH | SWT.CENTER);
							GridData btnValiderParamLData = new GridData();
							btnValiderParamLData.grabExcessHorizontalSpace = true;
							btnValiderParamLData.widthHint = 83;
							btnValiderParamLData.verticalAlignment = GridData.FILL;
							btnValiderCritereDoc.setLayoutData(btnValiderParamLData);
							btnValiderCritereDoc.setText("Valider");
						}
						{
							ccFiltreSelectionFinaleDoc = new Button(groupCritereDoc, SWT.CHECK | SWT.LEFT);
							GridData ccFiltreSelectionFinaleDocLData = new GridData();
							ccFiltreSelectionFinaleDoc.setLayoutData(ccFiltreSelectionFinaleDocLData);
							ccFiltreSelectionFinaleDoc.setText("Appliquer à la sélection finale");
						}
					}	
			{
				groupFactureNonRegle = new Group(this, SWT.NONE);
				GridLayout groupFactureNonRegleLayout = new GridLayout();
				groupFactureNonRegleLayout.numColumns = 2;
				groupFactureNonRegle.setLayout(groupFactureNonRegleLayout);
				GridData groupFactureNonRegleLData = new GridData();
				groupFactureNonRegleLData.horizontalAlignment = GridData.FILL;
				groupFactureNonRegleLData.grabExcessHorizontalSpace = true;
				groupFactureNonRegleLData.verticalAlignment = GridData.FILL;
				groupFactureNonRegleLData.horizontalSpan = 2;
				groupFactureNonRegleLData.grabExcessVerticalSpace = true;
				groupFactureNonRegle.setLayoutData(groupFactureNonRegleLData);
				groupFactureNonRegle.setText("Liste des tiers filtrés");
				{
					GridData expandBar1LData = new GridData();
					expandBar1LData.horizontalAlignment = GridData.FILL;
					expandBar1LData.grabExcessHorizontalSpace = true;
					expandBar1LData.verticalAlignment = GridData.FILL;
					expandBar1LData.horizontalSpan = 2;
					expandBar1LData.grabExcessVerticalSpace = true;
					expandBarGroup = new ExpandBar(groupFactureNonRegle, SWT.V_SCROLL);
					expandBarGroup.setLayoutData(expandBar1LData);
				}
			}

//			{
//				GridData expandBarLData = new GridData();
//				expandBarLData.horizontalAlignment = GridData.FILL;
//				expandBarLData.horizontalSpan = 2;
//				expandBarLData.verticalAlignment = GridData.BEGINNING;
//				expandBarLData.verticalSpan = 2;
//				expandBar = new ExpandBar(this, SWT.V_SCROLL);
//				expandBar.setLayoutData(expandBarLData);
//			}
			{
				GridData paBtnLData = new GridData();
				paBtnLData.horizontalAlignment = GridData.CENTER;
				paBtnLData.grabExcessHorizontalSpace = true;
				paBtnLData.verticalAlignment = GridData.END;
				paBtnLData.widthHint = 181;
				paBtnLData.heightHint = 35;
				paBtnLData.horizontalSpan = 2;
				paBtn = new Composite(this, SWT.NONE);
				GridLayout paBtnLayout = new GridLayout();
				paBtnLayout.makeColumnsEqualWidth = true;
				paBtnLayout.numColumns = 2;
				paBtn.setLayout(paBtnLayout);
				paBtn.setLayoutData(paBtnLData);
				{
					btnSuivant = new Button(paBtn, SWT.PUSH | SWT.CENTER);
					GridData btnSuivantLData = new GridData();
					btnSuivantLData.widthHint = 81;
					btnSuivantLData.horizontalAlignment = GridData.CENTER;
					btnSuivantLData.verticalAlignment = GridData.FILL;
					btnSuivant.setLayoutData(btnSuivantLData);
					btnSuivant.setText("Suivant");
				}
				{
					btnFermer = new Button(paBtn, SWT.PUSH | SWT.CENTER);
					GridData btnFermerLData = new GridData();
					btnFermerLData.horizontalAlignment = GridData.CENTER;
					btnFermerLData.widthHint = 81;
					btnFermerLData.verticalAlignment = GridData.FILL;
					btnFermer.setLayoutData(btnFermerLData);
					btnFermer.setText("Fermer");
				}
			}
			{
				GridData laMessageLData = new GridData();
				laMessageLData.horizontalAlignment = GridData.FILL;
				laMessageLData.grabExcessHorizontalSpace = true;
				laMessageLData.horizontalSpan = 2;
				laMessage = new Label(this, SWT.NONE);
				laMessage.setLayoutData(laMessageLData);
			}

			this.layout();
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}




	public Group getGroupParam() {
		return groupCritereDoc;
	}



	public Group getGroupFactureNonRegle() {
		return groupFactureNonRegle;
	}





	public Composite getPaBtn() {
		return paBtn;
	}

	public Button getBtnFermer() {
		return btnFermer;
	}

	public Button getBtnSuivant() {
		return btnSuivant;
	}

	public Button getBtnValiderCritereDoc() {
		return btnValiderCritereDoc;
	}
	
	public Label getLaMessage() {
		return laMessage;
	}
	
//	public ExpandBar getExpandBar() {
//		return expandBar;
//	}



	public ExpandBar getExpandBarGroup() {
		return expandBarGroup;
	}


	public DecoratedField getFieldTiers() {
		return fieldTiers;
	}

	public DecoratedField getFieldDateDebutPeriode() {
		return fieldDateDebutPeriode;
	}

	public DecoratedField getFieldDateFinPeriode() {
		return fieldDateFinPeriode;
	}
	

	
	public Combo getCbChampsDoc() {
		return cbChampsDoc;
	}
	
	public Combo getCbMotsDoc() {
		return cbMotsDoc;
	}
	
	public Text getTfCritereTiers() {
		return tfCritereTiers;
	}

	public Text getTfCritereTiers2() {
		return tfCritereTiers2;
	}

	public Button getBtnValiderCritereTiers() {
		return btnValiderCritereTiers;
	}

	public Button getBtnTousTiers() {
		return btnTousTiers;
	}

	public boolean isDecore() {
		return decore;
	}
	
	public Combo getCbListeDoc() {
		return cbListeDoc;
	}

	public Text getTfCritereDoc2() {
		return tfCritereDoc2;
	}

	public Text getTfCritereDoc() {
		return tfCritereDoc;
	}

	public Combo getCbMots() {
		return cbMots;
	}

	public Combo getCbChamps() {
		return cbChamps;
	}

	public Text getTfTiers() {
		return tfTiers;
	}
	
	public Button getCcFiltreFinal() {
		return ccFiltreFinal;
	}
	
	public Button getCcFiltreSelectionFinaleDoc() {
		return ccFiltreSelectionFinaleDoc;
	}

}

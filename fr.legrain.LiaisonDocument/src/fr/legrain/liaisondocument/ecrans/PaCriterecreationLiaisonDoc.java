package fr.legrain.liaisondocument.ecrans;

import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.fieldassist.TextControlCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.nebula.widgets.cdatetime.CDT;
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
import fr.legrain.lib.gui.fieldassist.CDateTimeControlCreator;
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
public class PaCriterecreationLiaisonDoc extends org.eclipse.swt.widgets.Composite {
	private Group groupParam;
	private Label laTiers;
	private Group groupType;
	private ExpandBar expandBarDocuments2;
	private Combo comboTypeDocuments2;
	private Button btnLies;
	private Button btnLie2;
	private Group group1;
	private Combo comboTypeDocuments1;
	private Text tfTiers;
	private Label laFin;
	private Label laDateDebut;
	private Group groupFactureNonRegle;
	private Group groupStat;

	private ExpandBar expandBarDocuments1;
	private Label laMessage;
	private cdatetimeLgr tfDateDebutPeriode;
	private cdatetimeLgr tfDateFinPeriode;
	private Button btnValiderParam;
	private Button btnValider;
	private Button btnFermer;
	private Composite paBtn;
	
	private DecoratedField fieldTiers;
	private DecoratedField fieldDateDebutPeriode;
	private DecoratedField fieldDateFinPeriode;

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
		PaCriterecreationLiaisonDoc inst = new PaCriterecreationLiaisonDoc(shell, SWT.NULL);
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

	public PaCriterecreationLiaisonDoc(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.numColumns = 2;
			thisLayout.makeColumnsEqualWidth = true;
			this.setLayout(thisLayout);
			{
				groupParam = new Group(this, SWT.NONE);
				GridLayout groupParamLayout = new GridLayout();
				groupParamLayout.numColumns = 3;
				groupParam.setLayout(groupParamLayout);
				GridData groupParamLData = new GridData();
				groupParamLData.horizontalAlignment = GridData.FILL;
				groupParamLData.verticalAlignment = GridData.FILL;
				groupParam.setLayoutData(groupParamLData);
				groupParam.setText("Paramètres");
				{
					laDateDebut = new Label(groupParam, SWT.NONE);
					GridData laDateDebutLData = new GridData();
					laDateDebutLData.widthHint = 149;
					laDateDebutLData.heightHint = 22;
					laDateDebut.setLayoutData(laDateDebutLData);
					laDateDebut.setText("Début");
				}
				{
					GridData cdatetimeLgr2LData = new GridData();
					cdatetimeLgr2LData.horizontalSpan = 2;
					cdatetimeLgr2LData.widthHint = 113;
					cdatetimeLgr2LData.heightHint = 23;
				if(!decore) {
					tfDateDebutPeriode = new cdatetimeLgr(groupParam, CDT.BORDER | CDT.DROP_DOWN);
					tfDateDebutPeriode.setLayoutData(cdatetimeLgr2LData);
					} else {
					fieldDateDebutPeriode = new DecoratedField(groupParam, CDT.BORDER | CDT.DROP_DOWN, new CDateTimeControlCreator());
					tfDateDebutPeriode = (cdatetimeLgr)fieldDateDebutPeriode.getControl();
					fieldDateDebutPeriode.getLayoutControl().setLayoutData(cdatetimeLgr2LData);
					}
				tfDateDebutPeriode.setPattern("dd/MM/yyyy");
				}
				
				{
					laFin = new Label(groupParam, SWT.NONE);
					GridData laFinLData = new GridData();
					laFinLData.widthHint = 149;
					laFinLData.heightHint = 22;
					laFin.setLayoutData(laFinLData);
					laFin.setText("Fin");
				}
				{
					GridData cdatetimeLgr2LData = new GridData();
					cdatetimeLgr2LData.horizontalSpan = 2;
					cdatetimeLgr2LData.widthHint = 113;
					cdatetimeLgr2LData.heightHint = 23;
				if(!decore) {
					tfDateFinPeriode = new cdatetimeLgr(groupParam, CDT.BORDER | CDT.DROP_DOWN);
					tfDateFinPeriode.setLayoutData(cdatetimeLgr2LData);
					} else {
					fieldDateFinPeriode = new DecoratedField(groupParam, CDT.BORDER | CDT.DROP_DOWN, new CDateTimeControlCreator());
					tfDateFinPeriode = (cdatetimeLgr)fieldDateFinPeriode.getControl();
					fieldDateFinPeriode.getLayoutControl().setLayoutData(cdatetimeLgr2LData);
					}
				tfDateFinPeriode.setPattern("dd/MM/yyyy");

				}
				{
					laTiers = new Label(groupParam, SWT.NONE);
					GridData laTiersLData = new GridData();
					laTiersLData.heightHint = 13;
					laTiers.setLayoutData(laTiersLData);
					laTiers.setText("le tiers");
				}
				{
					GridData tfTiersLData = new GridData();
//					tfTiersLData.heightHint = 19;
					tfTiersLData.widthHint = 69;
					if(!decore) {
						tfTiers = new Text(groupParam, SWT.BORDER);
						tfTiers.setLayoutData(tfTiersLData);
					} else {
						fieldTiers = new DecoratedField(groupParam, SWT.BORDER, new TextControlCreator());
						tfTiers = (Text)fieldTiers.getControl();
						fieldTiers.getLayoutControl().setLayoutData(tfTiersLData);
					}
				}
				{
					btnValiderParam = new Button(groupParam, SWT.PUSH | SWT.CENTER);
					GridData btnValiderParamLData = new GridData();
					btnValiderParamLData.horizontalAlignment = GridData.CENTER;
					btnValiderParamLData.horizontalSpan = 3;
					btnValiderParamLData.grabExcessHorizontalSpace = true;
					btnValiderParamLData.widthHint = 149;
					btnValiderParamLData.verticalAlignment = GridData.FILL;
					btnValiderParam.setLayoutData(btnValiderParamLData);
					btnValiderParam.setText("Valider la recherche");
				}
			}
			{
				groupStat = new Group(this, SWT.NONE);
				GridLayout groupStatLayout = new GridLayout();
				groupStatLayout.makeColumnsEqualWidth = true;
				groupStat.setLayout(groupStatLayout);
				GridData groupStatLData = new GridData();
				groupStatLData.horizontalAlignment = GridData.FILL;
				groupStatLData.verticalAlignment = GridData.FILL;
				groupStatLData.grabExcessHorizontalSpace = true;
				groupStat.setLayoutData(groupStatLData);
				groupStat.setText("Compléments");}

			{
				groupFactureNonRegle = new Group(this, SWT.NONE);
				GridLayout groupFactureNonRegleLayout = new GridLayout();
				groupFactureNonRegleLayout.numColumns = 2;
				groupFactureNonRegleLayout.makeColumnsEqualWidth = true;
				groupFactureNonRegle.setLayout(groupFactureNonRegleLayout);
				GridData groupFactureNonRegleLData = new GridData();
				groupFactureNonRegleLData.horizontalAlignment = GridData.FILL;
				groupFactureNonRegleLData.grabExcessHorizontalSpace = true;
				groupFactureNonRegleLData.verticalAlignment = GridData.FILL;
				groupFactureNonRegleLData.horizontalSpan = 2;
				groupFactureNonRegleLData.grabExcessVerticalSpace = true;
				groupFactureNonRegle.setLayoutData(groupFactureNonRegleLData);
				groupFactureNonRegle.setText("Liste des documents à prendre en compte dans la liaison");
				{
					groupType = new Group(groupFactureNonRegle, SWT.NONE);
					GridLayout groupTypeLayout = new GridLayout();
					groupTypeLayout.numColumns = 2;
					groupType.setLayout(groupTypeLayout);
					GridData groupTypeLData = new GridData();
					groupTypeLData.heightHint = 31;
					groupTypeLData.horizontalAlignment = GridData.FILL;
					groupType.setLayoutData(groupTypeLData);
					groupType.setText("Sélection type de document à lier");
					{
						comboTypeDocuments1 = new Combo(groupType, SWT.NONE);
						GridData comboTypeDocLData = new GridData();
						comboTypeDocLData.widthHint = 172;
						comboTypeDocLData.heightHint = 21;
						comboTypeDocuments1.setLayoutData(comboTypeDocLData);
					}
					{
						btnLies = new Button(groupType, SWT.CHECK | SWT.LEFT);
						GridData btnLiesLData = new GridData();
						btnLies.setLayoutData(btnLiesLData);
						btnLies.setText("déjà liés");
					}
				}
				{
					group1 = new Group(groupFactureNonRegle, SWT.NONE);
					GridLayout group1Layout = new GridLayout();
					group1Layout.numColumns = 2;
					group1.setLayout(group1Layout);
					GridData group1LData = new GridData();
					group1LData.heightHint = 31;
					group1LData.horizontalAlignment = GridData.FILL;
					group1.setLayoutData(group1LData);
					group1.setText("Sélection type de document à lier");
					{
						comboTypeDocuments2 = new Combo(group1, SWT.NONE);
						GridData combo1LData = new GridData();
						combo1LData.widthHint = 143;
						combo1LData.heightHint = 21;
						comboTypeDocuments2.setLayoutData(combo1LData);
						comboTypeDocuments2.setSize(172, 21);
					}
					{
						btnLie2 = new Button(group1, SWT.CHECK | SWT.LEFT);
						GridData button1LData = new GridData();
						button1LData.widthHint = 60;
						button1LData.heightHint = 16;
						btnLie2.setLayoutData(button1LData);
						btnLie2.setText("déjà liés");
					}
				}
				{
					GridData expandBar1LData1 = new GridData();
					expandBar1LData1.verticalAlignment = GridData.FILL;
					expandBar1LData1.horizontalAlignment = GridData.FILL;
					expandBar1LData1.grabExcessVerticalSpace = true;
					expandBarDocuments1 = new ExpandBar(groupFactureNonRegle, SWT.V_SCROLL|SWT.H_SCROLL);
					expandBarDocuments1.setLayoutData(expandBar1LData1);
				}
				{
					GridData expandBar1LData = new GridData();
					expandBar1LData.verticalAlignment = GridData.FILL;
					expandBar1LData.horizontalAlignment = GridData.FILL;
					expandBar1LData.grabExcessVerticalSpace = true;
					expandBar1LData.grabExcessHorizontalSpace = true;
					expandBarDocuments2 = new ExpandBar(groupFactureNonRegle, SWT.V_SCROLL|SWT.H_SCROLL);
					expandBarDocuments2.setLayoutData(expandBar1LData);
				}
			}

			{
				GridData paBtnLData = new GridData();
				paBtnLData.horizontalAlignment = GridData.CENTER;
				paBtnLData.grabExcessHorizontalSpace = true;
				paBtnLData.verticalAlignment = GridData.END;
				paBtnLData.widthHint = 261;
				paBtnLData.heightHint = 35;
				paBtnLData.horizontalSpan = 2;
				paBtn = new Composite(this, SWT.NONE);
				GridLayout paBtnLayout = new GridLayout();
				paBtnLayout.makeColumnsEqualWidth = true;
				paBtnLayout.numColumns = 2;
				paBtn.setLayout(paBtnLayout);
				paBtn.setLayoutData(paBtnLData);
				{
					btnValider = new Button(paBtn, SWT.PUSH | SWT.CENTER);
					GridData btnSuivantLData = new GridData();
					btnSuivantLData.widthHint = 119;
					btnSuivantLData.horizontalAlignment = GridData.CENTER;
					btnSuivantLData.verticalAlignment = GridData.FILL;
					btnValider.setLayoutData(btnSuivantLData);
					btnValider.setText("Valider les liaisons");
				}
				{
					btnFermer = new Button(paBtn, SWT.PUSH | SWT.CENTER);
					GridData btnFermerLData = new GridData();
					btnFermerLData.horizontalAlignment = GridData.CENTER;
					btnFermerLData.widthHint = 119;
					btnFermerLData.verticalAlignment = GridData.FILL;
					btnFermer.setLayoutData(btnFermerLData);
					btnFermer.setText("Fermer");
					btnFermer.setSize(119, 23);
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

	public cdatetimeLgr getTfDateDebutPeriode() {
		return tfDateDebutPeriode;
	}

	public cdatetimeLgr getTfDateFinPeriode() {
		return tfDateFinPeriode;
	}


	public Group getGroupParam() {
		return groupParam;
	}

	public Group getGroupStat() {
		return groupStat;
	}

	public Group getGroupFactureNonRegle() {
		return groupFactureNonRegle;
	}



	public Label getLaDateDebut() {
		return laDateDebut;
	}

	public Label getLaFin() {
		return laFin;
	}

	public Label getLaTiers() {
		return laTiers;
	}

	public Text getTfTiers() {
		return tfTiers;
	}

	public Composite getPaBtn() {
		return paBtn;
	}

	public Button getBtnFermer() {
		return btnFermer;
	}

	public Button getBtnValider() {
		return btnValider;
	}

	public Button getBtnValiderParam() {
		return btnValiderParam;
	}
	
	public Label getLaMessage() {
		return laMessage;
	}
	
	public ExpandBar getExpandBarDocuments2() {
		return expandBarDocuments2;
	}



	public ExpandBar getExpandBarDocuments1() {
		return expandBarDocuments1;
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

	public Combo getComboTypeDocuments1() {
		return comboTypeDocuments1;
	}

	public Combo getComboTypeDocuments2() {
		return comboTypeDocuments2;
	}
	
	public Button getBtnLies() {
		return btnLies;
	}

	public Button getBtnLie2() {
		return btnLie2;
	}



}

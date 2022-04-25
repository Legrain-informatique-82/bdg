package fr.legrain.creationDocMultipleTiers.ecrans;

import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.fieldassist.TextControlCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import fr.legrain.document.ecran.PaParamCreationDocMultiple;
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
public class PaCriterecreationDocMultiple extends org.eclipse.swt.widgets.Composite {
	private Group groupParam;
//	private Label laTiers;
	private Group groupType;
	private Group groupRegroupement;
	private Combo comboTypeDocCreation;
	private Combo cbTypePaiement;
	private Group group1;
	private Combo comboTypeDocSelection;
//	private Text tfTiers;
	private Label laFin;
	private Label laDateDebut;
	private Group groupFactureNonRegle;
	private Group groupStat;
	
	private Text tfCritereTiers;
	private Text tfCritereTiers2;
	private Combo cbMots;
	private Combo cbChamps;

	private ExpandBar expandBarGroup;
	private Label laMessage;
	private DateTime tfDateDebutPeriode;
	private DateTime tfDateFinPeriode;
	private Button btnValiderParam;
	private Button btnValider;
	private Button btnFermer;
	private Composite paBtn;
	private Label laDocument;
	private Text tfDocument;
	
	private DecoratedField fieldTiers;
	private DecoratedField fieldDocument;
	private DecoratedField fieldDateDebutPeriode;
	private DecoratedField fieldDateFinPeriode;
	
	private PaParamCreationDocMultiple regroupement;
	private Composite paEcran;


	private ScrolledComposite scrolledComposite = null;
		
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
		PaCriterecreationDocMultiple inst = new PaCriterecreationDocMultiple(shell, SWT.NULL);
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

	public PaCriterecreationDocMultiple(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.numColumns = 3;
			this.setLayout(thisLayout);
			{
				
				scrolledComposite = new ScrolledComposite(this, SWT.H_SCROLL | SWT.V_SCROLL /*| SWT.BORDER*/);
				 GridData ld = new GridData();
			      ld.horizontalAlignment = GridData.FILL;
			      ld.grabExcessHorizontalSpace = true;
			      ld.verticalAlignment = GridData.FILL;
			      ld.grabExcessVerticalSpace = true;
			      ld.horizontalSpan = 3;
			      scrolledComposite.setLayoutData(ld);
			      
			      scrolledComposite.setExpandHorizontal(true);
			      scrolledComposite.setExpandVertical(true);
			      
			      /****/
			      paEcran = new Composite(scrolledComposite, SWT.NONE);
				  GridLayout paEcranLayout = new GridLayout();
				  paEcranLayout.numColumns = 5;
				  paEcran.setLayout(paEcranLayout);
			      
			      scrolledComposite.setContent(paEcran);
			      scrolledComposite.setMinSize(paEcran.computeSize(SWT.DEFAULT, SWT.DEFAULT));
				
			
				GridData paEcranLData = new GridData();
				paEcranLData.verticalAlignment = GridData.FILL;
				paEcranLData.grabExcessVerticalSpace = true;
				paEcranLData.horizontalAlignment = GridData.FILL;
				paEcranLData.grabExcessHorizontalSpace = true;
				
				
				groupParam = new Group(paEcran, SWT.NONE);
				GridLayout groupParamLayout = new GridLayout();
				groupParamLayout.numColumns = 4;
				groupParam.setLayout(groupParamLayout);
				GridData groupParamLData = new GridData();
				groupParamLData.horizontalAlignment = GridData.FILL;
				groupParamLData.verticalAlignment = GridData.FILL;
				groupParam.setLayoutData(groupParamLData);
				groupParam.setText("Paramètres");
				{
					laDateDebut = new Label(groupParam, SWT.NONE);
					GridData laDateDebutLData = new GridData();
					laDateDebutLData.heightHint = 22;
					laDateDebutLData.horizontalAlignment = GridData.FILL;
					laDateDebut.setLayoutData(laDateDebutLData);
					laDateDebut.setText("Début");
				}
				{
					GridData cdatetimeLgr2LData = new GridData();
					cdatetimeLgr2LData.widthHint = 113;
					cdatetimeLgr2LData.heightHint = 23;
				if(!decore) {
					tfDateDebutPeriode = new DateTime(groupParam, SWT.BORDER | SWT.DROP_DOWN);
					tfDateDebutPeriode.setLayoutData(cdatetimeLgr2LData);
					} else {
					fieldDateDebutPeriode = new DecoratedField(groupParam, SWT.BORDER | SWT.DROP_DOWN, new DateTimeControlCreator());
					tfDateDebutPeriode = (DateTime)fieldDateDebutPeriode.getControl();
					fieldDateDebutPeriode.getLayoutControl().setLayoutData(cdatetimeLgr2LData);
					}
				//tfDateDebutPeriode.setPattern("dd/MM/yyyy");
				}
				
				{
					laFin = new Label(groupParam, SWT.NONE);
					GridData laFinLData = new GridData();
					laFinLData.heightHint = 22;
					laFinLData.horizontalAlignment = GridData.FILL;
					laFin.setLayoutData(laFinLData);
					laFin.setText("Fin");
					laFin.setOrientation(SWT.HORIZONTAL);
				}
				{
					GridData cdatetimeLgr2LData = new GridData();
					cdatetimeLgr2LData.widthHint = 113;
					cdatetimeLgr2LData.heightHint = 23;
				if(!decore) {
					tfDateFinPeriode = new DateTime(groupParam, SWT.BORDER | SWT.DROP_DOWN);
					tfDateFinPeriode.setLayoutData(cdatetimeLgr2LData);
					} else {
					fieldDateFinPeriode = new DecoratedField(groupParam, SWT.BORDER | SWT.DROP_DOWN, new DateTimeControlCreator());
					tfDateFinPeriode = (DateTime)fieldDateFinPeriode.getControl();
					fieldDateFinPeriode.getLayoutControl().setLayoutData(cdatetimeLgr2LData);
					}
				//tfDateFinPeriode.setPattern("dd/MM/yyyy");

				}
				{
					laDocument = new Label(groupParam, SWT.NONE);
					GridData laTiersLData = new GridData();
					laTiersLData.heightHint = 13;
					laTiersLData.horizontalAlignment = GridData.FILL;
					laDocument.setLayoutData(laTiersLData);
					laDocument.setText("le document");
				}
				{
					GridData tfTiersLData = new GridData();
//					tfTiersLData.heightHint = 19;
					tfTiersLData.heightHint = 17;
					tfTiersLData.horizontalSpan = 3;
					tfTiersLData.grabExcessHorizontalSpace = true;
					tfTiersLData.horizontalAlignment = GridData.FILL;
					if(!decore) {
						tfDocument = new Text(groupParam, SWT.BORDER);
						tfDocument.setLayoutData(tfTiersLData);
						tfDocument.setSize(343, 17);
					} else {
						fieldDocument = new DecoratedField(groupParam, SWT.BORDER, new TextControlCreator());
						tfDocument = (Text)fieldDocument.getControl();
						fieldDocument.getLayoutControl().setLayoutData(tfTiersLData);
					}
				}
				{
					cbChamps = new Combo(groupParam, SWT.NONE);
					GridData combo1LData = new GridData();
					combo1LData.verticalAlignment = GridData.FILL;
					combo1LData.horizontalAlignment = GridData.FILL;
					cbChamps.setLayoutData(combo1LData);
					cbChamps.setText("liste des champs");
				}
				{
					cbMots = new Combo(groupParam, SWT.NONE);
					GridData combo2LData = new GridData();
					combo2LData.verticalAlignment = GridData.FILL;
					combo2LData.grabExcessHorizontalSpace = true;
					combo2LData.horizontalAlignment = GridData.FILL;
					cbMots.setLayoutData(combo2LData);
					cbMots.setText("comparer");
				}
				{
					tfCritereTiers = new Text(groupParam, SWT.BORDER);
					GridData tfCritereTiersLData = new GridData();
					tfCritereTiersLData.verticalAlignment = GridData.FILL;
					tfCritereTiersLData.widthHint = 111;
					tfCritereTiers.setLayoutData(tfCritereTiersLData);
					tfCritereTiers.setText("");
				}
				{
					tfCritereTiers2 = new Text(groupParam, SWT.BORDER);
					GridData tfCritereTiersLData = new GridData();
					tfCritereTiersLData.verticalAlignment = GridData.FILL;
					tfCritereTiersLData.widthHint = 102;
					tfCritereTiers2.setLayoutData(tfCritereTiersLData);
					tfCritereTiers2.setText("");
				}
				{
					cbTypePaiement = new Combo(groupParam, SWT.NONE);
					GridData cbTypePaiementLData = new GridData();
					cbTypePaiementLData.horizontalSpan = 2;
					cbTypePaiementLData.horizontalAlignment = GridData.FILL;
					cbTypePaiement.setLayoutData(cbTypePaiementLData);
					cbTypePaiement.setText("Type Paiement");
				}
				{
					btnValiderParam = new Button(groupParam, SWT.PUSH | SWT.CENTER);
					GridData btnValiderParamLData = new GridData();
					btnValiderParamLData.horizontalAlignment = GridData.CENTER;
					btnValiderParamLData.horizontalSpan = 2;
					btnValiderParamLData.grabExcessHorizontalSpace = true;
					btnValiderParamLData.widthHint = 149;
					btnValiderParamLData.verticalAlignment = GridData.FILL;
					btnValiderParam.setLayoutData(btnValiderParamLData);
					btnValiderParam.setText("Valider la recherche");
				}
			}

			
			{
				groupRegroupement= new Group(paEcran, SWT.NONE);
				GridLayout groupStatLayout = new GridLayout();
				groupStatLayout.makeColumnsEqualWidth = true;
				groupRegroupement.setLayout(groupStatLayout);
				GridData groupStatLData = new GridData();
				groupStatLData.verticalAlignment = GridData.FILL;
				groupStatLData.horizontalAlignment = GridData.FILL;
				groupRegroupement.setLayoutData(groupStatLData);
				groupRegroupement.setText("Regroupement - 1 document généré : ");
				
				{
					GridData regroupementLData = new GridData();
					regroupementLData.verticalAlignment = GridData.BEGINNING;
					regroupementLData.horizontalAlignment = GridData.FILL;
					regroupementLData.grabExcessVerticalSpace = true;
					regroupementLData.grabExcessHorizontalSpace = true;
					regroupement = new PaParamCreationDocMultiple(groupRegroupement, SWT.NONE);
					((GridData) regroupement.getTfJours().getLayoutData()).widthHint = 61;
					regroupement.setLayoutData(regroupementLData);
//					regroupement.set().setText("1 document généré :");
					regroupement.getBtnAppliquer().setText("Ignorer les paramètrages particuliers");
					regroupement.getBtnTiers().setText("par tiers");
					regroupement.getBtnDocument().setText("par document");
					regroupement.getBtn1semaine().setText("par semaine");
					regroupement.getBtn2semaines().setText("par quinzaine");
					regroupement.getBtn1mois().setText("par mois de document");
					regroupement.getBtnXjours().setText("pour X jour(s) de document");
				}
			}
			{
				groupStat = new Group(paEcran, SWT.NONE);
				GridLayout groupStatLayout = new GridLayout();
				groupStatLayout.makeColumnsEqualWidth = true;
				groupStat.setLayout(groupStatLayout);
				GridData groupStatLData = new GridData();
				groupStatLData.verticalAlignment = GridData.FILL;
				groupStatLData.horizontalAlignment = GridData.FILL;
				groupStatLData.grabExcessHorizontalSpace = true;
				groupStat.setLayoutData(groupStatLData);
				groupStat.setText("Compléments");}
			{
				groupType = new Group(groupStat, SWT.NONE);
				GridLayout groupTypeLayout = new GridLayout();
				groupTypeLayout.makeColumnsEqualWidth = true;
				groupType.setLayout(groupTypeLayout);
				GridData groupTypeLData = new GridData();
				groupTypeLData.widthHint = 158;
				groupTypeLData.heightHint = 31;
				groupTypeLData.horizontalAlignment = GridData.CENTER;
				groupType.setLayoutData(groupTypeLData);
				groupType.setText("Sélection type document à récupérer");
				{
					comboTypeDocSelection = new Combo(groupType, SWT.NONE);
					GridData comboTypeDocLData = new GridData();
					comboTypeDocLData.widthHint = 175;
					comboTypeDocLData.heightHint = 22;
					comboTypeDocSelection.setLayoutData(comboTypeDocLData);
				}
			}
			{
				group1 = new Group(groupStat, SWT.NONE);
				GridLayout group1Layout = new GridLayout();
				group1Layout.makeColumnsEqualWidth = true;
				group1.setLayout(group1Layout);
				GridData group1LData = new GridData();
				group1LData.widthHint = 219;
				group1LData.heightHint = 24;
				group1.setLayoutData(group1LData);
				group1.setText("Sélection type de document à créer");
				{
					comboTypeDocCreation = new Combo(group1, SWT.NONE);
					GridData combo1LData = new GridData();
					combo1LData.widthHint = 173;
					combo1LData.heightHint = 21;
					comboTypeDocCreation.setLayoutData(combo1LData);
				}
			}

			{
				groupFactureNonRegle = new Group(paEcran, SWT.NONE);
				GridLayout groupFactureNonRegleLayout = new GridLayout();
				groupFactureNonRegleLayout.numColumns = 2;
				groupFactureNonRegle.setLayout(groupFactureNonRegleLayout);
				GridData groupFactureNonRegleLData = new GridData();
				groupFactureNonRegleLData.horizontalAlignment = GridData.FILL;
				groupFactureNonRegleLData.grabExcessHorizontalSpace = true;
				groupFactureNonRegleLData.verticalAlignment = GridData.FILL;
				groupFactureNonRegleLData.horizontalSpan = 3;
				groupFactureNonRegleLData.grabExcessVerticalSpace = true;
				groupFactureNonRegle.setLayoutData(groupFactureNonRegleLData);
				groupFactureNonRegle.setText("Liste des documents à prendre en compte dans le document à créer");
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

			{
				GridData paBtnLData = new GridData();
				paBtnLData.horizontalAlignment = GridData.CENTER;
				paBtnLData.grabExcessHorizontalSpace = true;
				paBtnLData.verticalAlignment = GridData.END;
				paBtnLData.widthHint = 261;
				paBtnLData.heightHint = 35;
				paBtnLData.horizontalSpan = 3;
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
					btnValider.setText("Créer document");
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
			this.setSize(1203, 332);
			new Label(this, SWT.NONE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DateTime getTfDateDebutPeriode() {
		return tfDateDebutPeriode;
	}

	public DateTime getTfDateFinPeriode() {
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

//	public Label getLaTiers() {
//		return laTiers;
//	}
//
//	public Text getTfTiers() {
//		return tfTiers;
//	}

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

	public Combo getComboTypeDocSelection() {
		return comboTypeDocSelection;
	}

	public Combo getComboTypeDocCreation() {
		return comboTypeDocCreation;
	}

	public PaParamCreationDocMultiple getRegroupement() {
		return regroupement;
	}

	public Group getGroupRegroupement() {
		return groupRegroupement;
	}

	public Text getTfCritereTiers() {
		return tfCritereTiers;
	}

	public Text getTfCritereTiers2() {
		return tfCritereTiers2;
	}

	public Combo getCbMots() {
		return cbMots;
	}

	public Combo getCbChamps() {
		return cbChamps;
	}

	public Label getLaDocument() {
		return laDocument;
	}

	public Text getTfDocument() {
		return tfDocument;
	}

	public DecoratedField getFieldDocument() {
		return fieldDocument;
	}
	public Composite getPaEcran() {
		return paEcran;
	}
	public Group getGroupType() {
		return groupType;
	}

	public Group getGroup1() {
		return group1;
	}

	public ScrolledComposite getScrolledComposite() {
		return scrolledComposite;
	}

	public boolean isDecore() {
		return decore;
	}

	public Combo getCbTypePaiement() {
		return cbTypePaiement;
	}
}

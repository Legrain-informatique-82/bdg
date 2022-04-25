package fr.legrain.reglement.ecran;

import org.eclipse.jface.action.ToolBarManager;
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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

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
public class PaReglementMultiple extends org.eclipse.swt.widgets.Composite {
	private Group groupParam;
	private Label laTiers;
	private Text tfTiers;
	private Label laFin;
	private Label laDateDebut;
	private Table tableFacture;
	private Group groupFactureNonRegle;
	private Label laFacture;
	private Text tfFacture;

	private DecoratedField fieldMONTANT_AFFECTE;
	private Text tfSoldeTiers;
	private Label laSoldeTiers;



	private Label label1;
	private Label laMT_HT_CALC;
	private Text tfMT_HT_CALC;
	private Label laMT_TTC_CALC;
	private Text tfMT_TTC_CALC;
	private Group PaGroupTotaux;
	private ExpandBar expandBar;
	private Label laMessage;
	private DateTime tfDateDebutPeriode;
	private DateTime tfDateFinPeriode;
	private Button btnValiderParam;
	private Button btnSuivant;
	private Button btnFermer;
		private Button btnToutRegler;



		public boolean isDecore() {
			return decore;
		}

	private Composite paBtn;
	private DecoratedField fieldCODE_DOCUMENT;
//	private DecoratedField fieldDATE_REGLEMENT;
//	private DecoratedField fieldDATE_ENCAISSEMENT;
//	private DecoratedField fieldTYPE_PAIEMENT;
//	private DecoratedField fieldLIBELLE_PAIEMENT;
	private DecoratedField fieldCODE_TIERS;

	private ScrolledComposite scrolledComposite = null;
	private Composite paEcran;
	
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
		PaReglementMultiple inst = new PaReglementMultiple(shell, SWT.NULL);
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

	public PaReglementMultiple(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.makeColumnsEqualWidth = true;
			thisLayout.numColumns = 1;
			this.setLayout(thisLayout);
			{
				
				scrolledComposite = new ScrolledComposite(this, SWT.H_SCROLL | SWT.V_SCROLL /*| SWT.BORDER*/);
				 GridData ld = new GridData();
			      ld.horizontalAlignment = GridData.FILL;
			      ld.grabExcessHorizontalSpace = true;
			      ld.verticalAlignment = GridData.FILL;
			      ld.grabExcessVerticalSpace = true;
			      ld.horizontalSpan = 2;
			      scrolledComposite.setLayoutData(ld);
			      
			      scrolledComposite.setExpandHorizontal(true);
			      scrolledComposite.setExpandVertical(true);
			      
			      /****/
			      paEcran = new Composite(scrolledComposite, SWT.NONE);
			      GridLayout paEcranLayout = new GridLayout();
				  paEcranLayout.numColumns = 2;
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
				groupParamLayout.numColumns = 5;
				groupParam.setLayout(groupParamLayout);
				GridData groupParamLData = new GridData();
				groupParamLData.horizontalAlignment = GridData.FILL;
				groupParamLData.verticalAlignment = GridData.FILL;
				groupParam.setLayoutData(groupParamLData);
				groupParam.setText("Paramètres");
				{
					PaGroupTotaux = new Group(paEcran, SWT.NONE);
					GridLayout PaGroupTotauxLayout = new GridLayout();
					PaGroupTotauxLayout.numColumns = 4;
					PaGroupTotaux.setLayout(PaGroupTotauxLayout);
					GridData PaGroupTotauxLData = new GridData();
					PaGroupTotauxLData.verticalAlignment = GridData.FILL;
					PaGroupTotauxLData.horizontalAlignment = GridData.FILL;
					PaGroupTotauxLData.grabExcessHorizontalSpace = true;
					PaGroupTotaux.setLayoutData(PaGroupTotauxLData);
					PaGroupTotaux.setText("Totaux");
					{
						laMT_HT_CALC = new Label(PaGroupTotaux, SWT.NONE);
						GridData laMT_HT_CALCLData = new GridData();
						laMT_HT_CALCLData.horizontalAlignment = GridData.FILL;
						laMT_HT_CALC.setLayoutData(laMT_HT_CALCLData);
						laMT_HT_CALC.setText("Total factures restant dues");
					}
					{
						tfMT_HT_CALC = new Text(PaGroupTotaux, SWT.BORDER);
						GridData tfMT_HT_CALCLData = new GridData();
						tfMT_HT_CALCLData.verticalAlignment = GridData.BEGINNING;
						tfMT_HT_CALCLData.widthHint = 127;
						tfMT_HT_CALCLData.horizontalAlignment = GridData.CENTER;
						tfMT_HT_CALCLData.heightHint = 14;
						tfMT_HT_CALCLData.widthHint = 75;
						tfMT_HT_CALC.setLayoutData(tfMT_HT_CALCLData);
						tfMT_HT_CALC.setEditable(false);
					}
					{
						laMT_TTC_CALC = new Label(PaGroupTotaux, SWT.NONE);
						GridData laMT_TTC_CALCLData = new GridData();
						laMT_TTC_CALCLData.horizontalAlignment = GridData.FILL;
						laMT_TTC_CALC.setLayoutData(laMT_TTC_CALCLData);
						laMT_TTC_CALC.setText("Nombre de lignes");
					}
					{
						tfMT_TTC_CALC = new Text(PaGroupTotaux, SWT.BORDER);
						GridData tfMT_TVA_AVANT_REMISELData = new GridData();
						tfMT_TVA_AVANT_REMISELData.widthHint = 127;
						GridData tfMT_TTC_CALCLData = new GridData();
						tfMT_TTC_CALCLData.horizontalAlignment = GridData.CENTER;
						tfMT_TTC_CALCLData.widthHint = 33;
						tfMT_TTC_CALCLData.heightHint = 14;
						tfMT_TTC_CALC.setLayoutData(tfMT_TTC_CALCLData);
						tfMT_TTC_CALC.setEditable(false);
					}
					{
						laSoldeTiers = new Label(PaGroupTotaux, SWT.NONE);
						GridData laSoldeTiersLData = new GridData();
						laSoldeTiers.setLayoutData(laSoldeTiersLData);
						laSoldeTiers.setText("Solde du tiers");
					}
					{
						GridData tfSoldeTiersLData = new GridData();
						tfSoldeTiersLData.widthHint = 75;
						tfSoldeTiersLData.heightHint = 14;
						tfSoldeTiers = new Text(PaGroupTotaux, SWT.BORDER);
						tfSoldeTiers.setLayoutData(tfSoldeTiersLData);
						tfSoldeTiers.setSize(75, 14);
						tfSoldeTiers.setEditable(false);
					}
					{
						GridData label1LData = new GridData();
						label1 = new Label(PaGroupTotaux, SWT.NONE);
						label1.setLayoutData(label1LData);
					}
				}
				{
					laDateDebut = new Label(groupParam, SWT.NONE);
					GridData laDateDebutLData = new GridData();
					laDateDebut.setLayoutData(laDateDebutLData);
					laDateDebut.setText("Début");
				}
				{
					tfDateDebutPeriode = new DateTime(groupParam, SWT.BORDER | SWT.DROP_DOWN);
					GridData cdatetimeLgr2LData = new GridData();
					cdatetimeLgr2LData.heightHint = 18;
					cdatetimeLgr2LData.widthHint = 105;
					tfDateDebutPeriode.setLayoutData(cdatetimeLgr2LData);
					tfDateDebutPeriode.setSize(105, 18);
//					tfDateDebutPeriode.setPattern("dd/MM/yyyy  ");
				}
				{
					laFin = new Label(groupParam, SWT.NONE);
					GridData laFinLData = new GridData();
					laFin.setLayoutData(laFinLData);
					laFin.setText("Fin");
				}
				{
					tfDateFinPeriode = new DateTime(groupParam, SWT.BORDER | SWT.DROP_DOWN);
					GridData cdatetimeLgr1LData1 = new GridData();
					cdatetimeLgr1LData1.heightHint = 18;
					cdatetimeLgr1LData1.widthHint = 105;
					tfDateFinPeriode.setLayoutData(cdatetimeLgr1LData1);
					tfDateFinPeriode.setSize(105, 18);
					//					tfDateFinPeriode.setPattern("dd/MM/yyyy  ");
				}
				{
					btnToutRegler = new Button(groupParam, SWT.PUSH | SWT.CENTER);
					GridData btnValiderParamLData = new GridData();
					btnValiderParamLData.horizontalAlignment = GridData.END;
					btnValiderParamLData.widthHint = 116;
					btnValiderParamLData.verticalAlignment = GridData.FILL;
					btnToutRegler.setLayoutData(btnValiderParamLData);
					btnToutRegler.setText("Tout régler");
//					btnToutRegler.setVisible(false);
				}
				{
					laTiers = new Label(groupParam, SWT.NONE);
					GridData laTiersLData = new GridData();
					laTiers.setLayoutData(laTiersLData);
					laTiers.setText("Tiers");
				}
				{
					GridData tfTiersLData = new GridData();
					tfTiersLData.widthHint = 99;
					tfTiersLData.heightHint = 16;
					if(!decore) {
						tfTiers = new Text(groupParam, SWT.BORDER);
						tfTiers.setLayoutData(tfTiersLData);
					} else {
						fieldCODE_TIERS = new DecoratedField(groupParam, SWT.BORDER, new TextControlCreator());
						tfTiers = (Text)fieldCODE_TIERS.getControl();
						fieldCODE_TIERS.getLayoutControl().setLayoutData(tfTiersLData);
					}
				}
				{
					laFacture = new Label(groupParam, SWT.NONE);
					GridData laTiersLData = new GridData();
					laFacture.setLayoutData(laTiersLData);
					laFacture.setText("Code facture");
				}
				{
					GridData tfFactureLData = new GridData();
					tfFactureLData.widthHint = 99;
					tfFactureLData.heightHint = 16;
					tfFactureLData.horizontalAlignment = GridData.END;
					if(!decore) {
						tfFacture = new Text(groupParam, SWT.BORDER);
						tfFacture.setLayoutData(tfFactureLData);
					} else {
						fieldCODE_DOCUMENT = new DecoratedField(groupParam, SWT.BORDER, new TextControlCreator());
						tfFacture = (Text)fieldCODE_DOCUMENT.getControl();
						fieldCODE_DOCUMENT.getLayoutControl().setLayoutData(tfFactureLData);
					}
				}
				{
					btnValiderParam = new Button(groupParam, SWT.PUSH | SWT.CENTER);
					GridData btnValiderParamLData = new GridData();
					btnValiderParamLData.verticalAlignment = GridData.BEGINNING;
					btnValiderParamLData.heightHint = 24;
					btnValiderParamLData.widthHint = 107;
					btnValiderParamLData.grabExcessHorizontalSpace = true;
					btnValiderParam.setLayoutData(btnValiderParamLData);
					btnValiderParam.setText("Valider");
				}
			}
			{
				groupFactureNonRegle = new Group(paEcran, SWT.NONE);
				GridLayout groupFactureNonRegleLayout = new GridLayout();
				groupFactureNonRegle.setLayout(groupFactureNonRegleLayout);
				GridData groupFactureNonRegleLData = new GridData();
				groupFactureNonRegleLData.horizontalAlignment = GridData.FILL;
				groupFactureNonRegleLData.grabExcessHorizontalSpace = true;
				groupFactureNonRegleLData.verticalAlignment = GridData.FILL;
				groupFactureNonRegleLData.grabExcessVerticalSpace = true;
				groupFactureNonRegleLData.horizontalSpan = 2;
				groupFactureNonRegle.setLayoutData(groupFactureNonRegleLData);
				groupFactureNonRegle.setText("Liste des factures non totalement réglées");
				
				
				{
					GridData tableFactureLData = new GridData();
					tableFactureLData.horizontalAlignment = GridData.FILL;
					tableFactureLData.verticalAlignment = GridData.FILL;
					tableFactureLData.grabExcessHorizontalSpace = true;
					tableFactureLData.grabExcessVerticalSpace = true;

					tableFacture = new Table(groupFactureNonRegle, SWT.SINGLE
							| SWT.FULL_SELECTION
							| SWT.H_SCROLL
							| SWT.V_SCROLL
							| SWT.BORDER);
					tableFacture.setLayoutData(tableFactureLData);
				}
			}

			{
				GridData expandBarLData = new GridData();
				expandBarLData.horizontalAlignment = GridData.FILL;
				expandBarLData.horizontalSpan = 2;
				expandBarLData.verticalAlignment = GridData.FILL;
				expandBarLData.grabExcessVerticalSpace = true;
				expandBarLData.verticalSpan = 2;
				expandBar = new ExpandBar(paEcran, SWT.V_SCROLL);
				expandBar.setLayoutData(expandBarLData);
			}
			{
				GridData paBtnLData = new GridData();
				paBtnLData.horizontalAlignment = GridData.CENTER;
				paBtnLData.horizontalSpan = 2;
				paBtnLData.grabExcessHorizontalSpace = true;
				paBtnLData.verticalAlignment = GridData.END;
				paBtnLData.widthHint = 181;
				paBtnLData.heightHint = 35;
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
			this.setSize(1100, 505);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DecoratedField getFieldMONTANT_AFFECTE() {
		return fieldMONTANT_AFFECTE;
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


	public Group getGroupFactureNonRegle() {
		return groupFactureNonRegle;
	}

	public Table getTableFacture() {
		return tableFacture;
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

	public Button getBtnSuivant() {
		return btnSuivant;
	}

	public Button getBtnValiderParam() {
		return btnValiderParam;
	}
	
	public Label getLaMessage() {
		return laMessage;
	}
	
	public ExpandBar getExpandBar() {
		return expandBar;
	}

	public Label getLaFacture() {
		return laFacture;
	}

	public Text getTfFacture() {
		return tfFacture;
	}


	
	public DecoratedField getFieldCODE_DOCUMENT() {
		return fieldCODE_DOCUMENT;
	}

	public DecoratedField getFieldCODE_TIERS() {
		return fieldCODE_TIERS;
	}


	public Composite getPaEcran() {
		return paEcran;
	}

	public ScrolledComposite getScrolledComposite() {
		return scrolledComposite;
	}
	
	public Group getPaGroupTotaux() {
		return PaGroupTotaux;
	}

	public Label getLaMT_HT_CALC() {
		return laMT_HT_CALC;
	}

	public Text getTfMT_HT_CALC() {
		return tfMT_HT_CALC;
	}

	public Label getLaMT_TTC_CALC() {
		return laMT_TTC_CALC;
	}

	public Text getTfMT_TTC_CALC() {
		return tfMT_TTC_CALC;
	}

	public Button getBtnToutRegler() {
			return btnToutRegler;
		}
	
	public Text getTfSoldeTiers() {
		return tfSoldeTiers;
	}
	public Label getLaSoldeTiers() {
		return laSoldeTiers;
	}
}

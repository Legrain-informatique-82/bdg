package fr.legrain.remisecheque.ecrans;

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
import org.eclipse.swt.widgets.Table;
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
public class PaCritereRemonterRemise extends org.eclipse.swt.widgets.Composite {
	private Table grille;
//	private Group groupRelanceFactures;

	private ExpandBar expandBarGroup;
	private Group groupParam;
	private Button btnValiderParam;
	private Combo cbListePaiement;
	private Label laListePaiement;
	private DateTime tfDateFinPeriode;
	private DateTime tfDateDebutPeriode;
	private Label laFin;
	private Label laDateDebut;
	private Button btnSupprimer;
	private Label laMessage;
	private Button btnFermer;
	private Composite paBtn;
	private ScrolledComposite scrolledComposite = null;
	private Composite paEcran;
	private Composite compositeGrille;
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
		PaCritereRemonterRemise inst = new PaCritereRemonterRemise(shell, SWT.NULL);
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

	public PaCritereRemonterRemise(org.eclipse.swt.widgets.Composite parent, int style) {
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
				
				scrolledComposite = new ScrolledComposite(this, SWT.H_SCROLL | SWT.V_SCROLL);
				GridData ld = new GridData();
				ld.horizontalAlignment = GridData.FILL;
				ld.grabExcessHorizontalSpace = true;
				ld.grabExcessVerticalSpace = true;
				ld.verticalAlignment = GridData.FILL;
				scrolledComposite.setLayoutData(ld);
				
				scrolledComposite.setExpandHorizontal(true);
				scrolledComposite.setExpandVertical(true);
				
				paEcran = new Composite(scrolledComposite, SWT.NONE);
				GridLayout paEcranLayout = new GridLayout();
				paEcranLayout.numColumns = 2;
				paEcran.setLayout(paEcranLayout);
				
				scrolledComposite.setContent(paEcran);
				paEcran.setBounds(0, 0, 882, 316);
				scrolledComposite.setMinSize(paEcran.computeSize(SWT.DEFAULT, SWT.DEFAULT));
				
				
				GridData paEcranLData = new GridData();
				paEcranLData.verticalAlignment = GridData.FILL;
				paEcranLData.grabExcessVerticalSpace = true;
				paEcranLData.horizontalAlignment = GridData.FILL;
				paEcranLData.grabExcessHorizontalSpace = true;
				
//				groupRelanceFactures = new Group(paEcran, SWT.NONE);
//				GridLayout groupFactureNonRegleLayout = new GridLayout();
//				groupFactureNonRegleLayout.numColumns = 2;
//				groupRelanceFactures.setLayout(groupFactureNonRegleLayout);
//				GridData groupFactureNonRegleLData = new GridData();
//				groupFactureNonRegleLData.horizontalAlignment = GridData.FILL;
//				groupFactureNonRegleLData.grabExcessHorizontalSpace = true;
//				groupFactureNonRegleLData.verticalAlignment = GridData.FILL;
//				groupFactureNonRegleLData.horizontalSpan = 2;
//				groupRelanceFactures.setLayoutData(groupFactureNonRegleLData);
//				groupRelanceFactures.setText("Liste des remises existantes");
				{
					groupParam = new Group(paEcran, SWT.NONE);
					GridLayout groupParamLayout = new GridLayout();
					groupParamLayout.numColumns = 8;
					groupParam.setLayout(groupParamLayout);
					GridData groupParamLData = new GridData();
					groupParamLData.horizontalSpan = 2;
					groupParamLData.horizontalAlignment = GridData.FILL;
					groupParamLData.verticalAlignment = GridData.FILL;
					groupParamLData.grabExcessHorizontalSpace = true;
					groupParam.setLayoutData(groupParamLData);
					groupParam.setText("Paramètres");
					{
						laDateDebut = new Label(groupParam, SWT.NONE);
						laDateDebut.setText("Début");
					}
					{
						tfDateDebutPeriode = new DateTime(groupParam, SWT.BORDER | SWT.DROP_DOWN);
						GridData tfDateDebutPeriodeLData = new GridData();
						tfDateDebutPeriodeLData.heightHint = 18;
						tfDateDebutPeriodeLData.horizontalAlignment = GridData.FILL;
						tfDateDebutPeriode.setLayoutData(tfDateDebutPeriodeLData);
//						tfDateDebutPeriode.setPattern("dd/MM/yyyy");
					}
					{
						laFin = new Label(groupParam, SWT.NONE);
						GridData laFinLData = new GridData();
						laFinLData.heightHint = 14;
						laFinLData.widthHint = 27;
						laFin.setLayoutData(laFinLData);
						laFin.setText("Fin");
					}
					{
						tfDateFinPeriode = new DateTime(groupParam, SWT.BORDER | SWT.DROP_DOWN);
						GridData tfDateFinPeriodeLData = new GridData();
						tfDateFinPeriodeLData.heightHint = 18;
						tfDateFinPeriode.setLayoutData(tfDateFinPeriodeLData);
						tfDateFinPeriode.setSize(101, 18);
//						tfDateFinPeriode.setPattern("dd/MM/yyyy");
					}
					{
						laListePaiement = new Label(groupParam, SWT.NONE);
						GridData laListePaiementLData = new GridData();
						laListePaiementLData.horizontalIndent = 20;
						laListePaiement.setLayoutData(laListePaiementLData);
						laListePaiement.setText("Type de paiement");
					}
					{
						GridData cbListePaiementLData = new GridData();
						cbListePaiementLData.horizontalAlignment = GridData.FILL;
						cbListePaiement = new Combo(groupParam, SWT.NONE);
						cbListePaiement.setLayoutData(cbListePaiementLData);
					}
					{
						btnValiderParam = new Button(groupParam, SWT.PUSH | SWT.CENTER);
						GridData btnValiderParamLData = new GridData();
						btnValiderParamLData.heightHint = 24;
						btnValiderParamLData.horizontalAlignment = GridData.END;
						btnValiderParamLData.horizontalIndent = 20;
						btnValiderParamLData.widthHint = 116;
						btnValiderParam.setLayoutData(btnValiderParamLData);
						btnValiderParam.setText("Valider");
					}
				}
				
				GridData paGrilleLData = new GridData();
				paGrilleLData.horizontalAlignment = GridData.FILL;
				paGrilleLData.horizontalSpan = 2;
				paGrilleLData.grabExcessHorizontalSpace = true;
				paGrilleLData.verticalAlignment = GridData.FILL;
				paGrilleLData.grabExcessVerticalSpace = true;
				compositeGrille = new Composite(paEcran, SWT.NONE);
				GridLayout paBtnLayout = new GridLayout();
				paBtnLayout.numColumns = 2;
				compositeGrille.setLayout(paBtnLayout);
				compositeGrille.setLayoutData(paGrilleLData);
				{
					GridData tableFactureLData = new GridData();
					tableFactureLData.horizontalAlignment = GridData.FILL;
					tableFactureLData.grabExcessHorizontalSpace = true;
					tableFactureLData.verticalSpan = 3;
					tableFactureLData.verticalAlignment = GridData.FILL;
					tableFactureLData.grabExcessVerticalSpace = true;

					grille = new Table(compositeGrille, SWT.SINGLE
							| SWT.FULL_SELECTION
							| SWT.H_SCROLL
							| SWT.V_SCROLL
							| SWT.BORDER);
					grille.setLayoutData(tableFactureLData);
				}
				{
					btnSupprimer = new Button(compositeGrille, SWT.PUSH | SWT.CENTER);
					GridData button1LData = new GridData();
					button1LData.widthHint = 97;
					button1LData.verticalAlignment = GridData.FILL;
					button1LData.horizontalAlignment = GridData.END;
					btnSupprimer.setLayoutData(button1LData);
					btnSupprimer.setText("Supprimer");
				}
				{
					GridData expandBar1LData = new GridData();
					expandBar1LData.horizontalAlignment = GridData.FILL;
					expandBar1LData.verticalAlignment = GridData.BEGINNING;
					expandBar1LData.horizontalSpan = 2;
					expandBar1LData.grabExcessHorizontalSpace = true;
					expandBarGroup = new ExpandBar(paEcran, SWT.V_SCROLL);
					expandBarGroup.setLayoutData(expandBar1LData);
				}
			}

			{
				GridData paBtnLData = new GridData();
				paBtnLData.horizontalAlignment = GridData.CENTER;
				paBtnLData.horizontalSpan = 2;
				paBtnLData.grabExcessHorizontalSpace = true;
				paBtnLData.verticalAlignment = GridData.END;
				paBtnLData.widthHint = 91;
				paBtnLData.heightHint = 35;
				paBtn = new Composite(this, SWT.NONE);
				GridLayout paBtnLayout = new GridLayout();
				paBtnLayout.makeColumnsEqualWidth = true;
				paBtnLayout.numColumns = 2;
				paBtn.setLayout(paBtnLayout);
				paBtn.setLayoutData(paBtnLData);
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
			this.setSize(911, 322);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//
//	public Group getGroupFactureNonRegle() {
//		return groupRelanceFactures;
//	}

	public Table getTableFacture() {
		return grille;
	}

	public Composite getPaBtn() {
		return paBtn;
	}

	public Button getBtnFermer() {
		return btnFermer;
	}
	
	public Label getLaMessage() {
		return laMessage;
	}
	

	public ExpandBar getExpandBarGroup() {
		return expandBarGroup;
	}

	public Table getGrille() {
		return grille;
	}

//	public Group getGroupRelanceFactures() {
//		return groupRelanceFactures;
//	}

	public Button getBtnSupprimer() {
		return btnSupprimer;
	}

	public Button getBtnValiderParam() {
		return btnValiderParam;
	}

	public Combo getCbListePaiement() {
		return cbListePaiement;
	}

	public DateTime getTfDateFinPeriode() {
		return tfDateFinPeriode;
	}

	public DateTime getTfDateDebutPeriode() {
		return tfDateDebutPeriode;
	}

	public Label getLaFin() {
		return laFin;
	}

	public Label getLaDateDebut() {
		return laDateDebut;
	}
	public ScrolledComposite getScrolledComposite() {
		return scrolledComposite;
	}
	public Composite getPaEcran() {
		return paEcran;
	}

	public Composite getCompositeGrille() {
		return compositeGrille;
	}
}

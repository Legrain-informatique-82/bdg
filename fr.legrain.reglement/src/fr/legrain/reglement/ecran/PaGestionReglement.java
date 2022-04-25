package fr.legrain.reglement.ecran;
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
import org.eclipse.swt.widgets.Text;

import fr.legrain.document.ecran.PaAffectationReglementSurFacture;


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
public class PaGestionReglement extends org.eclipse.swt.widgets.Composite {
	private Group groupParam;
	private DateTime tfDateDebutPeriode;
	private Label laTiers;
	private Button btnValiderParam;
	private ExpandBar expandBar;
	private PaAffectationReglementSurFacture paAffectationReglementSurFacture;
	private Text tfTiers;
	private DateTime tfDateFinPeriode;
	private Label laFin;
	private Label laDateDebut;
	private Label laFacture;
	private Text tfFacture;
	private ScrolledComposite scrolledComposite = null;
	private Composite paEcran;
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
		PaGestionReglement inst = new PaGestionReglement(shell, SWT.NULL);
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

	public PaGestionReglement(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
	
		try {
			GridLayout thisLayout = new GridLayout();
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
				scrolledComposite.setMinSize(paEcran.computeSize(SWT.DEFAULT, SWT.DEFAULT));
				
				
				GridData paEcranLData = new GridData();
				paEcranLData.verticalAlignment = GridData.FILL;
				paEcranLData.grabExcessVerticalSpace = true;
				paEcranLData.horizontalAlignment = GridData.FILL;
				paEcranLData.grabExcessHorizontalSpace = true;
				
				groupParam = new Group(paEcran, SWT.NONE);
				GridLayout groupParamLayout = new GridLayout();
				groupParamLayout.numColumns = 5;
				groupParamLayout.makeColumnsEqualWidth = true;
				groupParam.setLayout(groupParamLayout);
				GridData groupParamLData = new GridData();
				groupParamLData.horizontalSpan = 2;
				groupParamLData.horizontalAlignment = GridData.FILL;
				groupParamLData.heightHint = 62;
				groupParamLData.grabExcessHorizontalSpace = true;
				groupParam.setLayoutData(groupParamLData);
				groupParam.setText("Paramètres");
				{
					laDateDebut = new Label(groupParam, SWT.NONE);
					GridData laDateDebutLData = new GridData();
					laDateDebut.setLayoutData(laDateDebutLData);
					laDateDebut.setText("Début");
				}
				{
					tfDateDebutPeriode = new DateTime(groupParam, SWT.BORDER | SWT.DROP_DOWN);
					GridData cdatetimeLgr2LData = new GridData();
					cdatetimeLgr2LData.widthHint = 113;
					cdatetimeLgr2LData.heightHint = 20;
					tfDateDebutPeriode.setLayoutData(cdatetimeLgr2LData);
					tfDateDebutPeriode.setSize(113, 20);
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
					cdatetimeLgr1LData1.widthHint = 109;
					cdatetimeLgr1LData1.heightHint = 20;
					cdatetimeLgr1LData1.horizontalSpan = 2;
					tfDateFinPeriode.setLayoutData(cdatetimeLgr1LData1);
					tfDateFinPeriode.setSize(109, 20);
//					tfDateFinPeriode.setPattern("dd/MM/yyyy  ");
				}
				{
					laTiers = new Label(groupParam, SWT.NONE);
					GridData laTiersLData = new GridData();
					laTiers.setLayoutData(laTiersLData);
					laTiers.setText("Tiers");
				}
				{
					GridData tfTiersLData = new GridData();
					tfTiersLData.widthHint = 103;
					tfTiersLData.heightHint = 16;
					tfTiers = new Text(groupParam, SWT.BORDER);
					tfTiers.setLayoutData(tfTiersLData);
					tfTiers.setSize(103, 16);
				}
				{
					laFacture = new Label(groupParam, SWT.NONE);
					GridData laTiersLData = new GridData();
					laFacture.setLayoutData(laTiersLData);
					laFacture.setText("Code facture");
				}
				{
					GridData tfFactureLData = new GridData();
					tfFactureLData.widthHint = 103;
					tfFactureLData.heightHint = 16;
					tfFacture = new Text(groupParam, SWT.BORDER);
					tfFacture.setLayoutData(tfFactureLData);
					tfFacture.setSize(103, 16);
				}				
				{
					btnValiderParam = new Button(groupParam, SWT.PUSH | SWT.CENTER);
					GridData btnValiderParamLData = new GridData();
					btnValiderParamLData.horizontalAlignment = GridData.END;
					btnValiderParamLData.widthHint = 140;
					btnValiderParamLData.verticalAlignment = GridData.FILL;
					btnValiderParam.setLayoutData(btnValiderParamLData);
					btnValiderParam.setText("Valider");
				}
			}
			{
				GridData paAffectationReglementSurFactureLData = new GridData();
				paAffectationReglementSurFactureLData.horizontalSpan = 2;
				paAffectationReglementSurFactureLData.horizontalAlignment = GridData.FILL;
				paAffectationReglementSurFactureLData.grabExcessVerticalSpace = true;
				paAffectationReglementSurFactureLData.grabExcessHorizontalSpace = true;
				paAffectationReglementSurFactureLData.verticalAlignment = GridData.FILL;
				paAffectationReglementSurFacture = new PaAffectationReglementSurFacture(paEcran, SWT.NONE);
				paAffectationReglementSurFacture.setOrdreComposite(0);
				GridData paGrilleLData = new GridData();
				paGrilleLData.horizontalAlignment = GridData.FILL;
				paGrilleLData.grabExcessHorizontalSpace = true;
				paAffectationReglementSurFacture.setLayoutData(paAffectationReglementSurFactureLData);
				GridData compositeFormLData = new GridData();
				compositeFormLData.horizontalAlignment = GridData.FILL;
				compositeFormLData.grabExcessHorizontalSpace = true;
				compositeFormLData.verticalAlignment = GridData.BEGINNING;
				paAffectationReglementSurFacture.getPaGrille().setLayoutData(paGrilleLData);
				{
					GridData expandBarLData = new GridData();
					expandBarLData.grabExcessHorizontalSpace = true;
					expandBarLData.horizontalAlignment = GridData.FILL;
					expandBarLData.verticalAlignment = GridData.FILL;
					expandBarLData.grabExcessVerticalSpace = true;
					expandBar = new ExpandBar(paAffectationReglementSurFacture, SWT.V_SCROLL);
					expandBar.setLayoutData(expandBarLData);
				}
				GridData laMessageLData = new GridData();
				laMessageLData.grabExcessHorizontalSpace = true;
				laMessageLData.horizontalAlignment = GridData.FILL;
				laMessageLData.verticalAlignment = GridData.BEGINNING;
				paAffectationReglementSurFacture.getCompositeForm().setLayoutData(compositeFormLData);
				paAffectationReglementSurFacture.getLaMessage().setLayoutData(laMessageLData);
				
			}
			this.layout();
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public PaAffectationReglementSurFacture getPaAffectationReglementSurFacture() {
		return paAffectationReglementSurFacture;
	}

	public Group getGroupParam() {
		return groupParam;
	}

	public DateTime getTfDateDebutPeriode() {
		return tfDateDebutPeriode;
	}

	public Label getLaTiers() {
		return laTiers;
	}

	public Button getBtnValiderParam() {
		return btnValiderParam;
	}

	public Text getTfTiers() {
		return tfTiers;
	}

	public DateTime getTfDateFinPeriode() {
		return tfDateFinPeriode;
	}

	public Label getLaFin() {
		return laFin;
	}

	public Label getLaDateDebut() {
		return laDateDebut;
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
	public ScrolledComposite getScrolledComposite() {
		return scrolledComposite;
	}
	public Composite getPaEcran() {
		return paEcran;
	}
}

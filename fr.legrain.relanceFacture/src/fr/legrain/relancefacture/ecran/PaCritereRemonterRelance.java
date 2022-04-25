package fr.legrain.relancefacture.ecran;

import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
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
public class PaCritereRemonterRelance extends org.eclipse.swt.widgets.Composite {
	private Table grille;
	private Group groupRelanceFactures;

	private ExpandBar expandBarGroup;
	private Button btnSupprimer;
	private ExpandBar expandBar;
	private Label laMessage;
	private Button btnFermer;
	private Composite paBtn;
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
		PaCritereRemonterRelance inst = new PaCritereRemonterRelance(shell, SWT.NULL);
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

	public PaCritereRemonterRelance(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
//			GridLayout thisLayout = new GridLayout();
//			thisLayout.makeColumnsEqualWidth = true;
//			thisLayout.numColumns = 2;
//			this.setLayout(thisLayout);
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
			{
				groupRelanceFactures = new Group(paEcran, SWT.NONE);
				GridLayout groupFactureNonRegleLayout = new GridLayout();
				groupFactureNonRegleLayout.numColumns = 2;
				groupRelanceFactures.setLayout(groupFactureNonRegleLayout);
				GridData groupFactureNonRegleLData = new GridData();
				groupFactureNonRegleLData.horizontalAlignment = GridData.FILL;
				groupFactureNonRegleLData.grabExcessHorizontalSpace = true;
				groupFactureNonRegleLData.verticalAlignment = GridData.FILL;
				groupFactureNonRegleLData.horizontalSpan = 2;
				groupRelanceFactures.setLayoutData(groupFactureNonRegleLData);
				groupRelanceFactures.setText("Liste des relances de factures");
				{
					GridData tableFactureLData = new GridData();
					tableFactureLData.horizontalAlignment = GridData.FILL;
					tableFactureLData.grabExcessHorizontalSpace = true;
					tableFactureLData.verticalSpan = 3;
					tableFactureLData.verticalAlignment = GridData.BEGINNING;
					tableFactureLData.heightHint = 121;
					tableFactureLData.grabExcessVerticalSpace = true;
					
					grille = new Table(groupRelanceFactures, SWT.SINGLE
							| SWT.FULL_SELECTION
							| SWT.H_SCROLL
							| SWT.V_SCROLL
							| SWT.BORDER);
					grille.setLayoutData(tableFactureLData);
				}
				{
					btnSupprimer = new Button(groupRelanceFactures, SWT.PUSH | SWT.CENTER);
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
					expandBar1LData.grabExcessHorizontalSpace = true;
					expandBar1LData.horizontalSpan = 2;
					expandBar1LData.verticalAlignment = GridData.END;
					expandBarGroup = new ExpandBar(groupRelanceFactures, SWT.V_SCROLL);
					expandBarGroup.setLayoutData(expandBar1LData);
				}
			}

			{
				GridData expandBarLData = new GridData();
				expandBarLData.horizontalAlignment = GridData.FILL;
				expandBarLData.horizontalSpan = 2;
				expandBarLData.verticalAlignment = GridData.FILL;
				expandBarLData.verticalSpan = 2;
				expandBarLData.grabExcessVerticalSpace = true;
				expandBarLData.grabExcessHorizontalSpace = true;
				expandBar = new ExpandBar(paEcran, SWT.V_SCROLL);
				expandBar.setLayoutData(expandBarLData);
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
				paBtn = new Composite(paEcran, SWT.NONE);
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
				laMessage = new Label(paEcran, SWT.NONE);
				laMessage.setLayoutData(laMessageLData);
			}

			this.layout();
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public Group getGroupFactureNonRegle() {
		return groupRelanceFactures;
	}

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
	
	public ExpandBar getExpandBar() {
		return expandBar;
	}

	public ExpandBar getExpandBarGroup() {
		return expandBarGroup;
	}

	public Table getGrille() {
		return grille;
	}

	public Group getGroupRelanceFactures() {
		return groupRelanceFactures;
	}

	public Button getBtnSupprimer() {
		return btnSupprimer;
	}

	public ScrolledComposite getScrolledComposite() {
		return scrolledComposite;
	}

	public Composite getPaEcran() {
		return paEcran;
	}

}

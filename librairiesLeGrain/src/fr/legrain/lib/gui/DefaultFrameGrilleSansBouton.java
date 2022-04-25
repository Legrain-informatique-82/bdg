package fr.legrain.lib.gui;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

import com.cloudgarden.resource.SWTResourceManager;

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
public class DefaultFrameGrilleSansBouton extends org.eclipse.swt.widgets.Composite {

	private Table grille;
	private CLabel laMessage;
	private Label laTitreGrille;
	private Composite paGrille;
	private SashForm compositeForm;
	private int tableStyle = SWT.NULL; //style de la table
	
	public static final int ORDRE_GRILLE_FORMULAIRE = 0;
	public static final int ORDRE_FORMULAIRE_GRILLE = 1;
	private int ordreComposite = ORDRE_GRILLE_FORMULAIRE;
	
	private int orientationDefaut = SWT.VERTICAL;
	private int weightDefautForm = 20;
	private int weightDefautGrille = 80;

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
		DefaultFrameFormulaireSWTSimpleToolBarHaut inst = new DefaultFrameFormulaireSWTSimpleToolBarHaut(shell, SWT.FILL);
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

	public DefaultFrameGrilleSansBouton(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}
	
	public DefaultFrameGrilleSansBouton(org.eclipse.swt.widgets.Composite parent, int style, int ordreComposite) {
		super(parent, style);
		this.ordreComposite = ordreComposite;
		initGUI();
	}
	
	/**
	 * Comme le constructeur par defaut mais on peut specifie un style different pour la table,
	 * cela permet par exemple de specifier le style SWT.CHECK
	 * @param parent
	 * @param style
	 * @param tableStyle
	 */
	public DefaultFrameGrilleSansBouton(org.eclipse.swt.widgets.Composite parent, int style, int ordreComposite, int tableStyle) {
		super(parent, style);
		this.ordreComposite = ordreComposite;
		this.tableStyle = tableStyle;
		initGUI();
	}

	private void initGUI() {
		try {
			this.setLayout(new GridLayout());
			{
				GridData compositeFormLData = new GridData();
				compositeFormLData.verticalAlignment = GridData.FILL;
				compositeFormLData.horizontalAlignment = GridData.FILL;
				compositeFormLData.grabExcessHorizontalSpace = true;
				compositeFormLData.grabExcessVerticalSpace = true;
				compositeForm = new SashForm(this, SWT.BORDER);
GridLayout compositeFormLayout = new GridLayout();
				compositeFormLayout.makeColumnsEqualWidth = true;
				if(ordreComposite == ORDRE_FORMULAIRE_GRILLE) { //on pourrait faire une fonction, mais jigloo ne pourra pas la lire
				}
				{
					GridData compositeGrilleLData = new GridData();
					compositeGrilleLData.verticalAlignment = GridData.FILL;
					compositeGrilleLData.horizontalAlignment = GridData.FILL;
					compositeGrilleLData.grabExcessHorizontalSpace = true;
					compositeGrilleLData.grabExcessVerticalSpace = true;
					paGrille = new Composite(compositeForm, SWT.BORDER);
					GridLayout compositeGrilleLayout = new GridLayout();
					compositeGrilleLayout.makeColumnsEqualWidth = true;
					paGrille.setLayout(compositeGrilleLayout);
					paGrille.setLayoutData(compositeGrilleLData);
					{
						laTitreGrille = new Label(paGrille, SWT.CENTER);
						GridData label6LData = new GridData();
						label6LData.horizontalAlignment = GridData.FILL;
						label6LData.grabExcessHorizontalSpace = true;
						laTitreGrille.setLayoutData(label6LData);
						laTitreGrille.setText("titre grille");
					}
					{
						if(tableStyle==SWT.NULL) {
							grille = new Table(paGrille, SWT.SINGLE
									| SWT.FULL_SELECTION
									| SWT.H_SCROLL
									| SWT.V_SCROLL
									| SWT.BORDER);
//									| SWT.CHECK);
									//| SWT.VIRTUAL);
						} else {
							grille = new Table(paGrille, tableStyle);
						}
						grille.setHeaderVisible(true);
						GridData grilleLData = new GridData();
						grilleLData.verticalAlignment = GridData.FILL;
						grilleLData.horizontalAlignment = GridData.FILL;
						grilleLData.grabExcessHorizontalSpace = true;
						grilleLData.grabExcessVerticalSpace = true;
						grille.setLayoutData(grilleLData);
						grille.setLinesVisible(true);
					}
				}
			}

			if(ordreComposite == ORDRE_GRILLE_FORMULAIRE) {
				GridData compositeFormLData1 = new GridData();
				compositeFormLData1.horizontalAlignment = GridData.FILL;
				compositeFormLData1.heightHint = 143;
				compositeForm.setLayoutData(compositeFormLData1);
//				compositeForm.setWeights(new int[]{weightDefautGrille,weightDefautForm});
			}else if(ordreComposite == ORDRE_FORMULAIRE_GRILLE) {
				GridData compositeFormLData1 = new GridData();
				compositeFormLData1.horizontalAlignment = GridData.FILL;
				compositeFormLData1.grabExcessHorizontalSpace = true;
				compositeFormLData1.grabExcessVerticalSpace = true;
				compositeFormLData1.verticalAlignment = GridData.FILL;
				compositeForm.setLayoutData(compositeFormLData1);
//				compositeForm.setWeights(new int[]{weightDefautForm,weightDefautGrille});
			}

			{
				laMessage = new CLabel(this, SWT.NONE);
				laMessage.setForeground(new Color(getDisplay(),255,100,100));
				GridData cLabel1LData = new GridData();
				cLabel1LData.widthHint = 607;
				cLabel1LData.heightHint = 18;
				laMessage.setLayoutData(cLabel1LData);
				laMessage.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.BOLD, false, false));
			}
			this.layout();
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public SashForm getCompositeForm() {
	//public Composite getCompositeForm() {
		return compositeForm;
	}



	public Label getLaTitreGrille() {
		return laTitreGrille;
	}




	public Composite getPaGrille() {
		return paGrille;
	}
	
	public Table getGrille() {
		return grille;
	}
	
	public CLabel getLaMessage() {
		return laMessage;
	}

	public void setGrille(Table grille) {
	}


}

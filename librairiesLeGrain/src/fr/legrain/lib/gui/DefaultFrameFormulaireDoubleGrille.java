package fr.legrain.lib.gui;
import com.cloudgarden.resource.SWTResourceManager;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;

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
public class DefaultFrameFormulaireDoubleGrille extends org.eclipse.swt.widgets.Composite {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	private Composite paFomulaire;
	private Button btnAjouter;
	private Button btnEnlever;
	private Table grilleSource;
	private Table grilleDest;
	private Composite paBtn;
	private CLabel laMessage;
	private Label laTitreGrilleSource;
	private Composite paGrilleSource;
	private Label laTitreGrilleDest;
	private Composite paGrilleDest;
	private Composite compositeForm;
//	private SashForm compositeForm;
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
		DefaultFrameFormulaireDoubleGrille inst = new DefaultFrameFormulaireDoubleGrille(shell, SWT.FILL);
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

	public DefaultFrameFormulaireDoubleGrille(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}
	
	public DefaultFrameFormulaireDoubleGrille(org.eclipse.swt.widgets.Composite parent, int style, int ordreComposite) {
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
	public DefaultFrameFormulaireDoubleGrille(org.eclipse.swt.widgets.Composite parent, int style, int ordreComposite, int tableStyle) {
		super(parent, style);
		this.ordreComposite = ordreComposite;
		this.tableStyle = tableStyle;
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			this.setLayout(thisLayout);
			this.setSize(803, 454);
			{
				GridData compositeFormLData = new GridData();
				compositeFormLData.verticalAlignment = GridData.FILL;
				compositeFormLData.horizontalAlignment = GridData.FILL;
				compositeFormLData.grabExcessHorizontalSpace = true;
				compositeFormLData.grabExcessVerticalSpace = true;
				compositeForm = new Composite(this, SWT.BORDER);
//				compositeForm = new SashForm(this, SWT.BORDER);
				GridLayout compositeFormLayout1 = new GridLayout();
				compositeFormLayout1.numColumns = 3;
				compositeForm.setLayout(compositeFormLayout1);

				GridLayout compositeFormLayout = new GridLayout();
				compositeFormLayout.makeColumnsEqualWidth = true;

				{
					paGrilleSource = new Composite(compositeForm, SWT.BORDER);
					GridLayout compositeGrilleLayout = new GridLayout();
					compositeGrilleLayout.makeColumnsEqualWidth = true;
					paGrilleSource.setLayout(compositeGrilleLayout);
					GridData paGrilleSourceLData = new GridData();
					paGrilleSourceLData.verticalAlignment = GridData.FILL;
					paGrilleSourceLData.widthHint = 453;
					paGrilleSourceLData.grabExcessVerticalSpace = true;
					paGrilleSource.setLayoutData(paGrilleSourceLData);
					{
						laTitreGrilleSource = new Label(paGrilleSource, SWT.CENTER);
						GridData label6LData = new GridData();
						label6LData.horizontalAlignment = GridData.FILL;
						label6LData.grabExcessHorizontalSpace = true;
						laTitreGrilleSource.setLayoutData(label6LData);
						laTitreGrilleSource.setText("titre grille");
					}
					{
						if(tableStyle==SWT.NULL) {
							grilleSource = new Table(paGrilleSource, SWT.SINGLE
									| SWT.FULL_SELECTION
									| SWT.H_SCROLL
									| SWT.V_SCROLL
									| SWT.BORDER);
									//| SWT.VIRTUAL);
						} else {
							grilleSource = new Table(paGrilleSource, tableStyle);
						}
						grilleSource.setHeaderVisible(true);
						GridData grilleLData = new GridData();
						grilleLData.verticalAlignment = GridData.FILL;
						grilleLData.horizontalAlignment = GridData.FILL;
						grilleLData.grabExcessHorizontalSpace = true;
						grilleLData.grabExcessVerticalSpace = true;
						grilleSource.setLayoutData(grilleLData);
						grilleSource.setLinesVisible(true);
					}
				}
				{
					GridData compositeToolLData = new GridData();
					paBtn = new Composite(compositeForm, SWT.NONE);
					GridLayout compositeToolLayout = new GridLayout();
					compositeToolLayout.numColumns = 2;
					paBtn.setLayout(compositeToolLayout);
					paBtn.setLayoutData(compositeToolLData);
					{
						btnAjouter = new Button(paBtn, SWT.PUSH | SWT.CENTER);
						GridData btnAnnulerLData = new GridData();
						btnAjouter.setLayoutData(btnAnnulerLData);
						btnAjouter.setText("Ajouter");
					}
					{
						btnEnlever = new Button(paBtn, SWT.PUSH | SWT.CENTER);
						//					GridData button6LData = new GridData();
						//					button6LData.heightHint = 29;
						//					button6LData.widthHint = 101;
						//					btnAnnuler.setLayoutData(button6LData);
						GridData btnEnregistrerLData = new GridData();
						btnEnlever.setLayoutData(btnEnregistrerLData);
						btnEnlever.setText("Enlever");
						//btnAnnuler.setSize(101, 29);
					}
					//				paBtn.setTabList(new org.eclipse.swt.widgets.Control[] {
							//						btnEnregistrer, btnAnnuler, btnInserer, btnModifier,
							//						btnSupprimer, btnFermer });
				}
				{
					paGrilleDest = new Composite(compositeForm, SWT.BORDER);
					GridLayout compositeGrilleLayout = new GridLayout();
					compositeGrilleLayout.makeColumnsEqualWidth = true;
					paGrilleDest.setLayout(compositeGrilleLayout);
					GridData paGrilleDestLData = new GridData();
					paGrilleDestLData.horizontalAlignment = GridData.FILL;
					paGrilleDestLData.grabExcessHorizontalSpace = true;
					paGrilleDestLData.grabExcessVerticalSpace = true;
					paGrilleDestLData.verticalAlignment = GridData.FILL;
					paGrilleDest.setLayoutData(paGrilleDestLData);
					{
						laTitreGrilleDest = new Label(paGrilleDest, SWT.CENTER);
						GridData label6LData = new GridData();
						label6LData.horizontalAlignment = GridData.FILL;
						label6LData.grabExcessHorizontalSpace = true;
						laTitreGrilleDest.setLayoutData(label6LData);
						laTitreGrilleDest.setText("titre grille");
					}
					{
						if(tableStyle==SWT.NULL) {
							grilleDest = new Table(paGrilleDest, SWT.SINGLE
									| SWT.FULL_SELECTION
									| SWT.H_SCROLL
									| SWT.V_SCROLL
									| SWT.BORDER);
									//| SWT.VIRTUAL);
						} else {
							grilleDest = new Table(paGrilleDest, tableStyle);
						}
						grilleDest.setHeaderVisible(true);
						GridData grilleLData = new GridData();
						grilleLData.verticalAlignment = GridData.FILL;
						grilleLData.horizontalAlignment = GridData.FILL;
						grilleLData.grabExcessHorizontalSpace = true;
						grilleLData.grabExcessVerticalSpace = true;
						grilleDest.setLayoutData(grilleLData);
						grilleDest.setLinesVisible(true);
						{
							paFomulaire = new Composite(paGrilleDest, SWT.BORDER);
							GridLayout compositeFormulaireLayout = new GridLayout();
							paFomulaire.setLayout(compositeFormulaireLayout);
							GridData paFomulaireLData = new GridData();
							paFomulaireLData.heightHint = 33;
							paFomulaireLData.horizontalAlignment = GridData.FILL;
							paFomulaireLData.grabExcessHorizontalSpace = true;
							paFomulaire.setLayoutData(paFomulaireLData);
						}
					}
				}
				if(ordreComposite == ORDRE_GRILLE_FORMULAIRE) {
				}
			}

			if(ordreComposite == ORDRE_GRILLE_FORMULAIRE) {
				GridData compositeFormLData1 = new GridData();
				compositeFormLData1.verticalAlignment = GridData.BEGINNING;
				compositeFormLData1.horizontalAlignment = GridData.FILL;
				compositeFormLData1.grabExcessHorizontalSpace = true;
				compositeFormLData1.heightHint = 182;
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Button getBtnEnlever() {
		return btnEnlever;
	}


//	public SashForm getCompositeForm() {
	public Composite getCompositeForm() {
		return compositeForm;
	}

	public Label getLaTitreGrilleSource() {
		return laTitreGrilleSource;
	}

	public Composite getPaBtn() {
		return paBtn;
	}

	public Composite getPaFomulaire() {
		return paFomulaire;
	}

	public Composite getPaGrilleSource() {
		return paGrilleSource;
	}
	
	public Table getGrilleSource() {
		return grilleSource;
	}
	
	public CLabel getLaMessage() {
		return laMessage;
	}

	public void setGrilleSource(Table grille) {
	}

	public Button getBtnAjouter() {
		return btnAjouter;
	}

	public Table getGrilleDest() {
		return grilleDest;
	}

	public static int getOrdreGrilleFormulaire() {
		return ORDRE_GRILLE_FORMULAIRE;
	}

	public static int getOrdreFormulaireGrille() {
		return ORDRE_FORMULAIRE_GRILLE;
	}

	public Label getLaTitreGrilleDest() {
		return laTitreGrilleDest;
	}

	public Composite getPaGrilleDest() {
		return paGrilleDest;
	}

}

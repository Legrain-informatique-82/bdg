package fr.legrain.lib.gui;
import com.cloudgarden.resource.SWTResourceManager;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormData;
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
public class DefaultFrameFormulaireSWTSimpleBtnCote extends org.eclipse.swt.widgets.Composite {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	private Composite paFomulaire;
	private Button btnAnnuler;
	private Button btnModifier;
	private Table grille;
	private Button btnSupprimer;
	private Button btnInserer;
	private Composite paBtn;
	private CLabel laMessage;
	private Label laTitreGrille;
	private Composite paGrille;
	private Label laTitreFormulaire;
//	private Composite compositeForm;
	private SashForm compositeForm;
	private int tableStyle = SWT.NULL; //style de la table
	
	public static final int ORDRE_GRILLE_FORMULAIRE = 0;
	private Button btnImprimer;
	private Button btnFermer;
	private Button btnEnregistrer;
	public static final int ORDRE_FORMULAIRE_GRILLE = 1;
	private int ordreComposite = ORDRE_FORMULAIRE_GRILLE;
	
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
		DefaultFrameFormulaireSWTSimpleBtnCote inst = new DefaultFrameFormulaireSWTSimpleBtnCote(shell, SWT.FILL);
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

	public DefaultFrameFormulaireSWTSimpleBtnCote(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}
	
	public DefaultFrameFormulaireSWTSimpleBtnCote(org.eclipse.swt.widgets.Composite parent, int style, int ordreComposite) {
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
	public DefaultFrameFormulaireSWTSimpleBtnCote(org.eclipse.swt.widgets.Composite parent, int style, int ordreComposite, int tableStyle) {
		super(parent, style);
		this.ordreComposite = ordreComposite;
		this.tableStyle = tableStyle;
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			this.setLayout(thisLayout);
			thisLayout.numColumns = 2;
			this.setSize(803, 454);
			{
				GridData compositeFormLData = new GridData();
				compositeFormLData.verticalAlignment = GridData.FILL;
				compositeFormLData.horizontalAlignment = GridData.FILL;
				compositeFormLData.grabExcessHorizontalSpace = true;
				compositeFormLData.grabExcessVerticalSpace = true;
//				compositeForm = new Composite(this, SWT.BORDER);
				compositeForm = new SashForm(this, SWT.BORDER);
GridLayout compositeFormLayout = new GridLayout();
				compositeFormLayout.makeColumnsEqualWidth = true;
//				compositeForm.setLayout(compositeFormLayout);
//				compositeForm.setLayoutData(compositeFormLData);
				if(ordreComposite == ORDRE_FORMULAIRE_GRILLE) { //on pourrait faire une fonction, mais jigloo ne pourra pas la lire
					{
						GridData compositeFormulaireLData = new GridData();
						compositeFormulaireLData.verticalAlignment = GridData.FILL;
						compositeFormulaireLData.horizontalAlignment = GridData.FILL;
						compositeFormulaireLData.grabExcessVerticalSpace = true;
						paFomulaire = new Composite(compositeForm, SWT.BORDER);
						GridLayout compositeFormulaireLayout = new GridLayout();
						paFomulaire.setLayout(compositeFormulaireLayout);
						paFomulaire.setLayoutData(compositeFormulaireLData);
						{
							laTitreFormulaire = new Label(paFomulaire, SWT.CENTER);
							GridData label5LData = new GridData();
							label5LData.horizontalAlignment = GridData.FILL;
							label5LData.grabExcessHorizontalSpace = true;
							laTitreFormulaire.setLayoutData(label5LData);
							laTitreFormulaire.setText("titre Formulaire");
						}
					}
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
									//| SWT.VIRTUAL);
						} else {
							grille = new Table(paGrille, tableStyle);
						}
						grille.setHeaderVisible(true);
						GridData grilleLData = new GridData();
						grilleLData.horizontalAlignment = GridData.FILL;
						grilleLData.verticalAlignment = GridData.FILL;
						grilleLData.grabExcessVerticalSpace = true;
						grille.setLayoutData(grilleLData);
						grille.setLinesVisible(true);
					}
				}
				if(ordreComposite == ORDRE_GRILLE_FORMULAIRE) {
					{
						GridData compositeFormulaireLData = new GridData();
						compositeFormulaireLData.verticalAlignment = GridData.FILL;
						compositeFormulaireLData.horizontalAlignment = GridData.FILL;
						compositeFormulaireLData.grabExcessVerticalSpace = true;
						paFomulaire = new Composite(compositeForm, SWT.BORDER);
						GridLayout compositeFormulaireLayout = new GridLayout();
						paFomulaire.setLayout(compositeFormulaireLayout);
						paFomulaire.setLayoutData(compositeFormulaireLData);
						{
							laTitreFormulaire = new Label(paFomulaire, SWT.CENTER);
							GridData label5LData = new GridData();
							label5LData.horizontalAlignment = GridData.FILL;
							label5LData.grabExcessHorizontalSpace = true;
							laTitreFormulaire.setLayoutData(label5LData);
							laTitreFormulaire.setText("titre Formulaire");
						}
					}
				}
			}

			if(ordreComposite == ORDRE_GRILLE_FORMULAIRE) {
				GridData compositeFormLData1 = new GridData();
				compositeFormLData1.horizontalAlignment = GridData.FILL;
				compositeForm.setLayoutData(compositeFormLData1);
				compositeForm.setWeights(new int[]{weightDefautGrille,weightDefautForm});
			}else if(ordreComposite == ORDRE_FORMULAIRE_GRILLE) {
				GridData compositeFormLData1 = new GridData();
				compositeFormLData1.horizontalAlignment = GridData.FILL;
				compositeFormLData1.grabExcessHorizontalSpace = true;
				compositeFormLData1.grabExcessVerticalSpace = true;
				compositeFormLData1.verticalAlignment = GridData.FILL;
				compositeForm.setLayoutData(compositeFormLData1);
				compositeForm.setWeights(new int[]{weightDefautForm,weightDefautGrille});
			}
			
			{
				GridData compositeToolLData = new GridData();
				compositeToolLData.verticalAlignment = GridData.FILL;
				compositeToolLData.horizontalAlignment = GridData.FILL;
				paBtn = new Composite(this, SWT.NONE);
				GridLayout compositeToolLayout = new GridLayout();
				paBtn.setLayout(compositeToolLayout);
				paBtn.setLayoutData(compositeToolLData);
				{
					btnAnnuler = new Button(paBtn, SWT.PUSH | SWT.CENTER);
//					GridData button6LData = new GridData();
//					button6LData.heightHint = 29;
//					button6LData.widthHint = 101;
//					btnAnnuler.setLayoutData(button6LData);
GridData btnAnnulerLData = new GridData();
btnAnnulerLData.horizontalAlignment = GridData.FILL;
btnAnnulerLData.verticalAlignment = GridData.FILL;
btnAnnuler.setLayoutData(btnAnnulerLData);
					btnAnnuler.setText("Annuler [ESC]");
					//btnAnnuler.setSize(101, 29);
				}
				{
					btnInserer = new Button(paBtn, SWT.PUSH | SWT.CENTER);
					GridData btnInsererLData = new GridData();
					btnInsererLData.horizontalAlignment = GridData.FILL;
					btnInsererLData.verticalAlignment = GridData.FILL;
					btnInserer.setLayoutData(btnInsererLData);
					btnInserer.setText("Insérer [F6]");
				}
				{
					btnModifier = new Button(paBtn, SWT.PUSH | SWT.CENTER);
					GridData btnModifierLData = new GridData();
					btnModifierLData.horizontalAlignment = GridData.FILL;
					btnModifierLData.verticalAlignment = GridData.FILL;
					btnModifier.setLayoutData(btnModifierLData);
					btnModifier.setText("Modifier [F2]");
				}
				{
					btnEnregistrer = new Button(paBtn, SWT.PUSH | SWT.CENTER);
					GridData button1LData = new GridData();
					button1LData.horizontalAlignment = GridData.FILL;
					button1LData.verticalAlignment = GridData.FILL;
					btnEnregistrer.setLayoutData(button1LData);
					btnEnregistrer.setText("Enregistrer [F3]");
				}
				{
					btnSupprimer = new Button(paBtn, SWT.PUSH | SWT.CENTER);
					GridData btnSupprimerLData = new GridData();
					btnSupprimerLData.horizontalAlignment = GridData.CENTER;
					GridData btnSupprimerLData1 = new GridData();
					btnSupprimerLData1.horizontalAlignment = GridData.FILL;
					btnSupprimerLData1.verticalAlignment = GridData.FILL;
					btnSupprimer.setLayoutData(btnSupprimerLData1);
					btnSupprimer.setText("Supprimer [F10]");
				}
				{
					btnImprimer = new Button(paBtn, SWT.PUSH | SWT.CENTER);
					GridData button3LData = new GridData();
					button3LData.horizontalAlignment = GridData.FILL;
					button3LData.verticalAlignment = GridData.FILL;
					btnImprimer.setLayoutData(button3LData);
					btnImprimer.setText("Imprimer [F11]");
				}
				{
					btnFermer = new Button(paBtn, SWT.PUSH | SWT.CENTER);
					GridData button2LData = new GridData();
					button2LData.horizontalAlignment = GridData.FILL;
					button2LData.verticalAlignment = GridData.FILL;
					btnFermer.setLayoutData(button2LData);
					btnFermer.setText("Fermer [F4]");
				}
				//				paBtn.setTabList(new org.eclipse.swt.widgets.Control[] {
//						btnEnregistrer, btnAnnuler, btnInserer, btnModifier,
//						btnSupprimer, btnFermer });
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

	public Button getBtnAnnuler() {
		return btnAnnuler;
	}

	public Button getBtnInserer() {
		return btnInserer;
	}

	public Button getBtnModifier() {
		return btnModifier;
	}

	public Button getBtnSupprimer() {
		return btnSupprimer;
	}

	public SashForm getCompositeForm() {
	//public Composite getCompositeForm() {
		return compositeForm;
	}

	public Label getLaTitreFormulaire() {
		return laTitreFormulaire;
	}

	public Label getLaTitreGrille() {
		return laTitreGrille;
	}

	public Composite getPaBtn() {
		return paBtn;
	}

	public Composite getPaFomulaire() {
		return paFomulaire;
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

	public Button getBtnImprimer() {
		return btnImprimer;
	}

	public Button getBtnFermer() {
		return btnFermer;
	}

	public Button getBtnEnregister() {
		return btnEnregistrer;
	}

}

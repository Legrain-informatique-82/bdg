package fr.legrain.lib.gui;
import com.cloudgarden.resource.SWTResourceManager;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
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
public class DefaultFrameFormulaireSWTExpandBar extends org.eclipse.swt.widgets.Composite {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	private Composite paTitre;
	private Label laTitreFenetre;
	private Composite paFomulaire;
	private Button btnAnnuler;
	private Button btnModifier;
	private ExpandBar expandBar;
	private Button btnFermer;
	private Button btnSupprimer;
	private Button btnInserer;
	private Button btnEnregistrer;
	private Composite paBtn;
	private Button btnImprimer;
	private CLabel laMessage;
	private Label laTitreGrille;
	private Composite paGrille;
	private Label laTitreFormulaire;
//	private Composite compositeForm;
	private SashForm compositeForm;
	private int tableStyle = SWT.NULL; //style de la table
	
	public static final int ORDRE_EXPANDBAR_FORMULAIRE = 0;
	private Button btnTous;
	public static final int ORDRE_EXPANDBAR_GRILLE = 1;
	private int ordreComposite = ORDRE_EXPANDBAR_FORMULAIRE;
	
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
		DefaultFrameFormulaireSWTExpandBar inst = new DefaultFrameFormulaireSWTExpandBar(shell, SWT.FILL);
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

	public DefaultFrameFormulaireSWTExpandBar(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}
	
	public DefaultFrameFormulaireSWTExpandBar(org.eclipse.swt.widgets.Composite parent, int style, int ordreComposite) {
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
	public DefaultFrameFormulaireSWTExpandBar(org.eclipse.swt.widgets.Composite parent, int style, int ordreComposite, int tableStyle) {
		super(parent, style);
		this.ordreComposite = ordreComposite;
		this.tableStyle = tableStyle;
		initGUI();
	}

	private void initGUI() {
		try {
			this.setLayout(new GridLayout());
			this.setSize(803, 454);
			{
				GridData compositeTitreLData = new GridData();
				compositeTitreLData.horizontalAlignment = GridData.FILL;
				compositeTitreLData.grabExcessHorizontalSpace = true;
				compositeTitreLData.heightHint = 28;
				paTitre = new Composite(this, SWT.NONE);
				GridLayout compositeTitreLayout = new GridLayout();
				compositeTitreLayout.numColumns = 2;
				paTitre.setLayout(compositeTitreLayout);
				paTitre.setLayoutData(compositeTitreLData);
				{
					laTitreFenetre = new Label(paTitre, SWT.CENTER);
					GridData label4LData = new GridData();
					label4LData.horizontalAlignment = GridData.FILL;
					label4LData.grabExcessHorizontalSpace = true;
					label4LData.verticalAlignment = GridData.FILL;
					label4LData.grabExcessVerticalSpace = true;
					laTitreFenetre.setLayoutData(label4LData);
					laTitreFenetre.setText("titre ecran");
				}
				{
					btnTous = new Button(paTitre, SWT.PUSH | SWT.CENTER);
					btnTous.setText("Afficher tous");
				}
			}
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
				if(ordreComposite == ORDRE_EXPANDBAR_GRILLE) { //on pourrait faire une fonction, mais jigloo ne pourra pas la lire
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
						laTitreGrille.setText("titre expand bar");
					}
					{
						GridData grilleLData = new GridData();
						grilleLData.horizontalAlignment = GridData.FILL;
						grilleLData.verticalAlignment = GridData.FILL;
						grilleLData.grabExcessVerticalSpace = true;
						grilleLData.grabExcessHorizontalSpace = true;
						if(tableStyle==SWT.NULL) {
							expandBar = new ExpandBar(paGrille, SWT.SINGLE
									| SWT.FULL_SELECTION
									| SWT.H_SCROLL
									| SWT.V_SCROLL
									| SWT.BORDER);
//									| SWT.CHECK);
						} else {
							expandBar = new ExpandBar(paGrille, tableStyle);
						}
						expandBar.setLayoutData(grilleLData);
						//expandBar.setHeaderVisible(true);
						//expandBar.setLinesVisible(true);
					}
				}
				if(ordreComposite == ORDRE_EXPANDBAR_FORMULAIRE) {
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
							label5LData.grabExcessHorizontalSpace = true;
							label5LData.horizontalAlignment = GridData.FILL;
							laTitreFormulaire.setLayoutData(label5LData);
							laTitreFormulaire.setText("titre Formulaire");
						}
					}
				}
			}
			
			compositeForm.setOrientation(orientationDefaut);
			if(ordreComposite == ORDRE_EXPANDBAR_FORMULAIRE) {
				compositeForm.setWeights(new int[]{weightDefautGrille,weightDefautForm});
			}else if(ordreComposite == ORDRE_EXPANDBAR_GRILLE) {
				compositeForm.setWeights(new int[]{weightDefautForm,weightDefautGrille});
			}
			
			{
				GridData compositeToolLData = new GridData();
				compositeToolLData.verticalAlignment = GridData.END;
				compositeToolLData.horizontalAlignment = GridData.CENTER;
				compositeToolLData.heightHint = 33;
				compositeToolLData.grabExcessHorizontalSpace = true;
				paBtn = new Composite(this, SWT.NONE);
				GridLayout compositeToolLayout = new GridLayout();
				compositeToolLayout.numColumns = 7;
				paBtn.setLayout(compositeToolLayout);
				paBtn.setLayoutData(compositeToolLData);
				{
					btnAnnuler = new Button(paBtn, SWT.PUSH | SWT.CENTER);
//					GridData button6LData = new GridData();
//					button6LData.heightHint = 29;
//					button6LData.widthHint = 101;
//					btnAnnuler.setLayoutData(button6LData);
					btnAnnuler.setText("Annuler [ESC]");
					//btnAnnuler.setSize(101, 29);
				}
				{
					btnEnregistrer = new Button(paBtn, SWT.PUSH | SWT.CENTER);
					btnEnregistrer.setText("Enregistrer [F3]");
				}
				{
					btnInserer = new Button(paBtn, SWT.PUSH | SWT.CENTER);
					btnInserer.setText("Insérer [F6]");
				}
				{
					btnModifier = new Button(paBtn, SWT.PUSH | SWT.CENTER);
					btnModifier.setText("Modifier [F2]");
				}
				{
					btnSupprimer = new Button(paBtn, SWT.PUSH | SWT.CENTER);
					GridData btnSupprimerLData = new GridData();
					btnSupprimerLData.horizontalAlignment = GridData.CENTER;
					btnSupprimer.setText("Supprimer [F10]");
				}
				{
					btnFermer = new Button(paBtn, SWT.PUSH | SWT.CENTER);
//					GridData button1LData = new GridData();
//					button1LData.horizontalAlignment = GridData.FILL;
//					button1LData.heightHint = 29;
					//btnFermer.setLayoutData(button1LData);
					btnFermer.setText("Fermer [F4]");
					//btnFermer.setSize(86, 29);
				}
				{
					btnImprimer = new Button(paBtn, SWT.PUSH | SWT.CENTER);
					btnImprimer.setText("Imprimer [F11]");
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
			GridData btnTousLData = new GridData();
			btnTous.setLayoutData(btnTousLData);
			btnTous.setVisible(false);

			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Button getBtnAnnuler() {
		return btnAnnuler;
	}

	public Button getBtnEnregistrer() {
		return btnEnregistrer;
	}

	public Button getBtnFermer() {
		return btnFermer;
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

	public Label getLaTitreFenetre() {
		return laTitreFenetre;
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

	public Composite getPaTitre() {
		return paTitre;
	}
	
	public ExpandBar getExpandBar() {
		return expandBar;
	}
	
	public CLabel getLaMessage() {
		return laMessage;
	}

	public void setExpandBar(ExpandBar grille) {
		this.expandBar = grille;
	}
	
	public Button getBtnImprimer() {
		return btnImprimer;
	}
	
	public Button getBtnTous() {
		return btnTous;
	}

}

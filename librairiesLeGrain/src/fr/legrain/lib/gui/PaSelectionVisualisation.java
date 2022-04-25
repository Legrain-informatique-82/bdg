package fr.legrain.lib.gui;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;

import com.cloudgarden.resource.SWTResourceManager;
import org.eclipse.jface.viewers.TreeViewer;

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
public class PaSelectionVisualisation extends org.eclipse.swt.widgets.Composite {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	private Composite paTitre;
	private Label laTitreFenetre;
	private Group groupResultats;
	private Group groupRqt;
	private TabItem tabItem1;
	private PaBtn paBtnImprimer;
	private CTabFolder cTabFolderResultat;
	private TabFolder tabFolder;
	private Table tableRequete;
	private CLabel laMessage;
	private PaBtnReduit paBtnRecherche;
	private Table GrilleSelection;
	private Composite PaSelection;
	private Composite compositeForm;
	private int tableStyle = SWT.NULL; //style de la table



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
		DefaultFrameFormulaireSWT inst = new DefaultFrameFormulaireSWT(shell, SWT.FILL);
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
	public PaSelectionVisualisation(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	/**
	 * Comme le constructeur par defaut mais on peut specifie un style different pour la table,
	 * cela permet par exemple de specifier le style SWT.CHECK
	 * @param parent
	 * @param style
	 * @param tableStyle
	 */
	public PaSelectionVisualisation(org.eclipse.swt.widgets.Composite parent, int style, int tableStyle) {
		super(parent, style);
		this.tableStyle = tableStyle;
		initGUI();
	}

	private void initGUI() {
		try {
			this.setLayout(new GridLayout());
			this.setSize(890, 661);
			{
				GridData compositeTitreLData = new GridData();
				compositeTitreLData.horizontalAlignment = GridData.FILL;
				compositeTitreLData.grabExcessHorizontalSpace = true;
				compositeTitreLData.heightHint = 23;
				paTitre = new Composite(this, SWT.NONE);
				GridLayout compositeTitreLayout = new GridLayout();
				compositeTitreLayout.makeColumnsEqualWidth = true;
				paTitre.setLayout(compositeTitreLayout);
				paTitre.setLayoutData(compositeTitreLData);
				{
					laTitreFenetre = new Label(paTitre, SWT.CENTER);
					GridData label4LData = new GridData();
					label4LData.horizontalAlignment = GridData.FILL;
					label4LData.grabExcessHorizontalSpace = true;
					laTitreFenetre.setLayoutData(label4LData);
					laTitreFenetre.setText("Recherches et visualisations");
				}
			}
			{
				GridData compositeFormLData = new GridData();
				compositeFormLData.horizontalAlignment = GridData.FILL;
				compositeFormLData.grabExcessHorizontalSpace = true;
				compositeFormLData.verticalAlignment = GridData.BEGINNING;
				compositeFormLData.heightHint = 331;
				compositeForm = new Composite(this, SWT.BORDER);
				GridLayout compositeFormLayout = new GridLayout();
				compositeFormLayout.numColumns = 2;
				compositeForm.setLayout(compositeFormLayout);
				compositeForm.setLayoutData(compositeFormLData);
				{
					groupRqt = new Group(compositeForm, SWT.NONE);
					GridLayout groupRqtLayout = new GridLayout();
					groupRqtLayout.makeColumnsEqualWidth = true;
					groupRqt.setLayout(groupRqtLayout);
					GridData groupRqtLData = new GridData();
					groupRqtLData.verticalAlignment = GridData.FILL;
					groupRqtLData.grabExcessVerticalSpace = true;
					groupRqtLData.horizontalAlignment = GridData.FILL;
					groupRqtLData.grabExcessHorizontalSpace = true;
					groupRqt.setLayoutData(groupRqtLData);
					groupRqt.setText("Requetes disponibles");
					{
						GridData treeViewerRequeteLData = new GridData();
						treeViewerRequeteLData.horizontalAlignment = GridData.FILL;
						treeViewerRequeteLData.verticalAlignment = GridData.FILL;
						treeViewerRequeteLData.grabExcessVerticalSpace = true;
						treeViewerRequeteLData.grabExcessHorizontalSpace = true;
						tableRequete = new Table(groupRqt, SWT.SINGLE | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
						tableRequete.setLayoutData(treeViewerRequeteLData);
					}
				}
				{
					tabFolder = new TabFolder(compositeForm, SWT.NONE);
					{
						tabItem1 = new TabItem(tabFolder, SWT.NONE);
						tabItem1.setText("Vos critères de recherche");
						{
							PaSelection = new Composite(tabFolder, SWT.BORDER);
							tabItem1.setControl(PaSelection);
							GridLayout composite1Layout = new GridLayout();
							composite1Layout.makeColumnsEqualWidth = true;
							PaSelection.setLayout(composite1Layout);
							{
								GrilleSelection = new Table(PaSelection, SWT.SINGLE | SWT.FULL_SELECTION | SWT.H_SCROLL| SWT.V_SCROLL| SWT.BORDER);
								GrilleSelection.setHeaderVisible(true);
								GridData table1LData = new GridData();
								table1LData.verticalAlignment = GridData.FILL;
								table1LData.grabExcessVerticalSpace = true;
								table1LData.horizontalAlignment = GridData.FILL;
								table1LData.grabExcessHorizontalSpace = true;
								GrilleSelection.setLayoutData(table1LData);
								GrilleSelection.setLinesVisible(true);
							}
							{
								GridData paBtnReduit2LData = new GridData();
								paBtnReduit2LData.widthHint = 275;
								paBtnReduit2LData.heightHint = 33;
								paBtnReduit2LData.horizontalAlignment = GridData.CENTER;
								paBtnRecherche = new PaBtnReduit(PaSelection, SWT.NONE);
								GridLayout paBtnRechercheLayout = new GridLayout();
								paBtnRechercheLayout.numColumns = 2;
								paBtnRecherche.setLayout(paBtnRechercheLayout);
								paBtnRecherche.setLayoutData(paBtnReduit2LData);
								paBtnRecherche.getBtnFermer().setText("Réinitialiser [F5]");
								GridData btnImprimerLData = new GridData();
								btnImprimerLData.widthHint = 126;
								btnImprimerLData.heightHint = 27;
								paBtnRecherche.getBtnImprimer().setText("Rechercher [F3]");
								GridData btnFermerLData1 = new GridData();
								btnFermerLData1.widthHint = 124;
								btnFermerLData1.heightHint = 27;
								paBtnRecherche.getBtnImprimer().setLayoutData(btnImprimerLData);
								paBtnRecherche.getBtnFermer().setLayoutData(btnFermerLData1);
							}
						}
					}
				}
				GridData tabFolderLData = new GridData();
				tabFolderLData.widthHint = 407;
				tabFolderLData.verticalAlignment = GridData.FILL;
				tabFolder.setLayoutData(tabFolderLData);
				tabFolder.setSelection(0);
			}

			{
				groupResultats = new Group(this, SWT.NONE);
				GridLayout groupResultatsLayout = new GridLayout();
				groupResultatsLayout.makeColumnsEqualWidth = true;
				groupResultats.setLayout(groupResultatsLayout);
				GridData groupResultatsLData = new GridData();
				groupResultatsLData.horizontalAlignment = GridData.FILL;
				groupResultatsLData.verticalAlignment = GridData.FILL;
				groupResultatsLData.grabExcessVerticalSpace = true;
				groupResultatsLData.grabExcessHorizontalSpace = true;
				groupResultats.setLayoutData(groupResultatsLData);
				groupResultats.setText("Résultat de la recherche");
				{
					cTabFolderResultat = new CTabFolder(groupResultats, SWT.NONE);
					GridData cTabFolderResultatLData = new GridData();
					cTabFolderResultatLData.verticalAlignment = GridData.FILL;
					cTabFolderResultatLData.grabExcessVerticalSpace = true;
					cTabFolderResultatLData.horizontalAlignment = GridData.FILL;
					cTabFolderResultatLData.grabExcessHorizontalSpace = true;
					cTabFolderResultat.setLayoutData(cTabFolderResultatLData);
					cTabFolderResultat.setSelection(0);
				}
			}
			{
				GridData paBtnImprimerLData = new GridData();
				paBtnImprimerLData.verticalAlignment = GridData.END;
				paBtnImprimerLData.heightHint = 33;
				paBtnImprimerLData.grabExcessHorizontalSpace = true;
				paBtnImprimerLData.widthHint = 620;
				paBtnImprimerLData.horizontalAlignment = GridData.CENTER;
				paBtnImprimer = new PaBtn(this, SWT.NONE);
				GridLayout paBtnImprimerLayout = new GridLayout();
				paBtnImprimerLayout.numColumns = 7;
				paBtnImprimer.setLayout(paBtnImprimerLayout);
				GridData btnEnregistrerLData = new GridData();
				btnEnregistrerLData.widthHint = 4;
				btnEnregistrerLData.heightHint = 27;
				paBtnImprimer.setLayoutData(paBtnImprimerLData);
				GridData btnInsererLData = new GridData();
				btnInsererLData.widthHint = 0;
				btnInsererLData.heightHint = 27;
				paBtnImprimer.getBtnEnregistrer().setLayoutData(btnEnregistrerLData);
				GridData btnModifierLData = new GridData();
				btnModifierLData.heightHint = 27;
				btnModifierLData.widthHint = 0;
				paBtnImprimer.getBtnInserer().setLayoutData(btnInsererLData);
				GridData btnSupprimerLData = new GridData();
				btnSupprimerLData.widthHint = 131;
				btnSupprimerLData.heightHint = 27;
				paBtnImprimer.getBtnModifier().setLayoutData(btnModifierLData);
				paBtnImprimer.getBtnSupprimer().setLayoutData(btnSupprimerLData);
				paBtnImprimer.getBtnAnnuler().setText("Réinitialiser [ALT+F5]");
				paBtnImprimer.getBtnSupprimer().setText("Extraction [F12]");
				paBtnImprimer.getBtnSupprimer().setVisible(true);
				paBtnImprimer.getBtnModifier().setVisible(false);
				paBtnImprimer.getBtnInserer().setVisible(false);
				GridData btnAnnulerLData = new GridData();
				btnAnnulerLData.widthHint = 143;
				btnAnnulerLData.heightHint = 27;
				paBtnImprimer.getBtnEnregistrer().setVisible(false);
				GridData btnFermerLData = new GridData();
				btnFermerLData.widthHint = 131;
				btnFermerLData.heightHint = 27;
				paBtnImprimer.getBtnAnnuler().setLayoutData(btnAnnulerLData);
				GridData btnImprimerLData1 = new GridData();
				btnImprimerLData1.widthHint = 131;
				btnImprimerLData1.heightHint = 27;
				paBtnImprimer.getBtnFermer().setLayoutData(btnFermerLData);
				paBtnImprimer.getBtnImprimer().setLayoutData(btnImprimerLData1);
				paBtnImprimer.getBtnImprimer().setSize(131, 27);
				paBtnImprimer.getBtnFermer().setSize(131, 27);
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public Composite getCompositeForm() {
		return compositeForm;
	}

	public Label getLaTitreFenetre() {
		return laTitreFenetre;
	}

	public Composite getPaTitre() {
		return paTitre;
	}

	public CLabel getLaMessage() {
		return laMessage;
	}


	public Table getGrilleSelection() {
		return GrilleSelection;
	}

	public PaBtnReduit getPaBtnRecherche() {
		return paBtnRecherche;
	}

	public Composite getPaSelection() {
		return PaSelection;
	}

	public PaBtn getPaBtnImprimer() {
		return paBtnImprimer;
	}

	public void setPaBtnImprimer(PaBtn paBtnImprimer) {
		this.paBtnImprimer = paBtnImprimer;
	}

	public Table getTableRequete() {
		return tableRequete;
	}

	public void setTableRequete(Table tableRequete) {
		this.tableRequete = tableRequete;
	}

	public void setGrilleSelection(Table grilleSelection) {
		GrilleSelection = grilleSelection;
	}

	public TabFolder getTabFolder() {
		return tabFolder;
	}

	public Group getGroupRqt() {
		return groupRqt;
	}

	public Group getGroupResultats() {
		return groupResultats;
	}

	public CTabFolder getCTabFolderResultat() {
		return cTabFolderResultat;
	}

}

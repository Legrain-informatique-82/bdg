package fr.legrain.tiers.ecran;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.fieldassist.TextControlCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import fr.legrain.lib.gui.DefaultFrameFormulaireSWT;
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
public class PaDocumentTiers extends org.eclipse.swt.widgets.Composite {
	private Group groupParam;
	private Group groupFactureNonRegle;

	private Combo cbListeVersion;
	private DefaultFrameFormulaireSWT formulaire;
	private Composite paCorpsFormulaire;
	private Label laCodeDocumentTiers;
	private Label laLibelleDocumentTiers;
	private Label laCheminModelDocumentTiers;
	private Label laCheminCorrespDocumentTiers;
	
	private Text tfCodeDocumentTiers;
	private Button btnChemin_Corresp;
	private Button btnChemin_Model;
	private Button btnOuvrir;
	private Text tfLibelleDocumentTiers;
	private Text tfCheminModelDocumentTiers;
	private Text tfCheminCorrespDocumentTiers;

	private DecoratedField fieldCodeDocumentTiers;
	private DecoratedField fieldLibelleDocumentTiers;
	private DecoratedField fieldCheminDocumentTiers;
	private DecoratedField fieldCheminCorrespDocumentTiers;

	final private boolean decore = LgrMess.isDECORE();
	//final private boolean decore = false;

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
		PaDocumentTiers inst = new PaDocumentTiers(shell, SWT.NULL);
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

	public PaDocumentTiers(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.makeColumnsEqualWidth = true;
			thisLayout.numColumns = 2;
			this.setLayout(thisLayout);
			{
				groupParam = new Group(this, SWT.NONE);
				GridLayout groupParamLayout = new GridLayout();
				groupParamLayout.numColumns = 3;
				groupParamLayout.makeColumnsEqualWidth = true;
				groupParam.setLayout(groupParamLayout);
				GridData groupParamLData = new GridData();
				groupParamLData.horizontalAlignment = GridData.FILL;
				groupParamLData.grabExcessHorizontalSpace = true;
				groupParamLData.verticalAlignment = GridData.FILL;
				groupParamLData.horizontalSpan = 2;
				groupParam.setLayoutData(groupParamLData);
				groupParam.setText("Indiquez la version du logiciel de publipostage que vous souhaitez utiliser");
				{
					cbListeVersion = new Combo(groupParam, SWT.NONE);
					GridData cbListeVersionLData = new GridData();
					cbListeVersion.setLayoutData(cbListeVersionLData);
					cbListeVersion.setText("Version logiciel publipostage");
				}
			}
			{
				groupFactureNonRegle = new Group(this, SWT.NONE);
				GridLayout groupFactureNonRegleLayout = new GridLayout();
				groupFactureNonRegleLayout.numColumns = 2;
				groupFactureNonRegle.setLayout(groupFactureNonRegleLayout);
				GridData groupFactureNonRegleLData = new GridData();
				groupFactureNonRegleLData.horizontalAlignment = GridData.FILL;
				groupFactureNonRegleLData.grabExcessHorizontalSpace = true;
				groupFactureNonRegleLData.verticalAlignment = GridData.FILL;
				groupFactureNonRegleLData.grabExcessVerticalSpace = true;
				groupFactureNonRegleLData.horizontalSpan = 2;
				groupFactureNonRegle.setLayoutData(groupFactureNonRegleLData);
				groupFactureNonRegle.setText("Liste des documents");
				{
					GridData formulaireLData = new GridData();
					formulaireLData.grabExcessVerticalSpace = true;
					formulaireLData.verticalAlignment = GridData.FILL;
					formulaireLData.horizontalAlignment = GridData.FILL;
					formulaireLData.grabExcessHorizontalSpace = true;
					formulaireLData.horizontalSpan = 2;
					formulaire = new DefaultFrameFormulaireSWT(groupFactureNonRegle, SWT.NONE,0,
							 SWT.FULL_SELECTION
							| SWT.H_SCROLL
							| SWT.V_SCROLL
							| SWT.BORDER
							| SWT.CHECK);
					GridData compositeFormLData = new GridData();
					compositeFormLData.grabExcessHorizontalSpace = true;
					compositeFormLData.grabExcessVerticalSpace = true;
					compositeFormLData.verticalAlignment = GridData.FILL;
					compositeFormLData.horizontalAlignment = GridData.FILL;
					compositeFormLData.horizontalSpan = 2;
					formulaire.setLayoutData(formulaireLData);
					GridData grilleLData = new GridData();
					grilleLData.verticalAlignment = GridData.FILL;
					grilleLData.horizontalAlignment = GridData.FILL;
					grilleLData.grabExcessVerticalSpace = true;
					formulaire.getCompositeForm().setLayoutData(compositeFormLData);
					GridData paGrilleLData = new GridData();
					paGrilleLData.verticalAlignment = GridData.FILL;
					formulaire.getGrille().setLayoutData(grilleLData);
					formulaire.getPaGrille().setLayoutData(paGrilleLData);
				}
				{
					paCorpsFormulaire = new Composite(formulaire.getPaFomulaire(), SWT.NONE);
					GridLayout composite1Layout = new GridLayout();
					composite1Layout.numColumns = 4;
					GridData composite1LData = new GridData();
					composite1LData.horizontalAlignment = GridData.FILL;
					composite1LData.grabExcessHorizontalSpace = true;
					composite1LData.verticalAlignment = GridData.FILL;
					composite1LData.grabExcessVerticalSpace = true;
					paCorpsFormulaire.setLayoutData(composite1LData);
					paCorpsFormulaire.setLayout(composite1Layout);

					{
						laCodeDocumentTiers = new Label(paCorpsFormulaire, SWT.NONE);
						GridData laCODE_T_RELANCELData = new GridData();
						laCODE_T_RELANCELData.horizontalSpan = 1;
						laCodeDocumentTiers.setLayoutData(laCODE_T_RELANCELData);
						laCodeDocumentTiers.setText("Code");
					}
					{
						GridData tfCODE_T_RELANCEData = new GridData();
						tfCODE_T_RELANCEData.horizontalAlignment = GridData.BEGINNING;
						tfCODE_T_RELANCEData.horizontalSpan = 3;
						tfCODE_T_RELANCEData.widthHint = 200;
						tfCODE_T_RELANCEData.verticalAlignment = GridData.FILL;
						if(!decore) {
							tfCodeDocumentTiers = new Text(paCorpsFormulaire, SWT.BORDER);
							tfCodeDocumentTiers.setLayoutData(tfCODE_T_RELANCEData);
						} else {					
						fieldCodeDocumentTiers = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
						tfCodeDocumentTiers = (Text)fieldCodeDocumentTiers.getControl();
						fieldCodeDocumentTiers.getLayoutControl().setLayoutData(tfCODE_T_RELANCEData);
						}
					}
					{
						laLibelleDocumentTiers = new Label(paCorpsFormulaire, SWT.NONE);
						GridData laLIBELLE_T_RELANCELData = new GridData();
						laLIBELLE_T_RELANCELData.horizontalSpan = 1;
						laLibelleDocumentTiers.setLayoutData(laLIBELLE_T_RELANCELData);
						laLibelleDocumentTiers.setText("Libellé");
					}
					{
						GridData tfLIBELLE_T_RELANCEData = new GridData();
						tfLIBELLE_T_RELANCEData.horizontalAlignment = GridData.BEGINNING;
						tfLIBELLE_T_RELANCEData.widthHint = 600;
						tfLIBELLE_T_RELANCEData.horizontalSpan = 3;
						tfLIBELLE_T_RELANCEData.verticalAlignment = GridData.FILL;
						if(!decore) {
							tfLibelleDocumentTiers = new Text(paCorpsFormulaire, SWT.BORDER);
							tfLibelleDocumentTiers.setLayoutData(tfLIBELLE_T_RELANCEData);
						} else {					
							fieldLibelleDocumentTiers = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
							tfLibelleDocumentTiers = (Text)fieldLibelleDocumentTiers.getControl();
							fieldLibelleDocumentTiers.getLayoutControl().setLayoutData(tfLIBELLE_T_RELANCEData);
						}
					}
					{
						laCheminModelDocumentTiers = new Label(paCorpsFormulaire, SWT.NONE);
						laCheminModelDocumentTiers.setText("Adresse du fichier modèle");
					}
					{
						GridData tfCHEMIN_T_RELANCEData = new GridData();
						tfCHEMIN_T_RELANCEData.horizontalAlignment = GridData.BEGINNING;
						tfCHEMIN_T_RELANCEData.widthHint = 600;
						tfCHEMIN_T_RELANCEData.verticalAlignment = GridData.FILL;
						if(!decore) {
							tfCheminModelDocumentTiers = new Text(paCorpsFormulaire, SWT.BORDER);
							tfCheminModelDocumentTiers.setLayoutData(tfCHEMIN_T_RELANCEData);
						} else {					
						fieldCheminDocumentTiers = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
						tfCheminModelDocumentTiers = (Text)fieldCheminDocumentTiers.getControl();
						fieldCheminDocumentTiers.getLayoutControl().setLayoutData(tfCHEMIN_T_RELANCEData);
						}
					}
					{
						GridData btnChemin_ModelLData = new GridData();
						btnChemin_Model = new Button(paCorpsFormulaire, SWT.PUSH | SWT.CENTER);
						btnChemin_Model.setLayoutData(btnChemin_ModelLData);
						btnChemin_Model.setText("Parcourir...");
					}
					{
						GridData btnChemin_ModelLData = new GridData();
						btnOuvrir = new Button(paCorpsFormulaire, SWT.PUSH | SWT.CENTER);
						btnOuvrir.setLayoutData(btnChemin_ModelLData);
						btnOuvrir.setText("Ouvrir...");
					}
					{
						laCheminCorrespDocumentTiers = new Label(paCorpsFormulaire, SWT.NONE);
						GridData laLIBELLE_T_RELANCELData = new GridData();
						laLIBELLE_T_RELANCELData.horizontalSpan = 1;
						laCheminCorrespDocumentTiers.setLayoutData(laLIBELLE_T_RELANCELData);						
						laCheminCorrespDocumentTiers.setText("Adresse du fichier de correspondance");
					}
					{
						GridData tfCHEMIN_CORRESP_RELANCEData = new GridData();
						tfCHEMIN_CORRESP_RELANCEData.horizontalAlignment = GridData.BEGINNING;
						tfCHEMIN_CORRESP_RELANCEData.widthHint = 600;
						tfCHEMIN_CORRESP_RELANCEData.horizontalSpan = 1;
						tfCHEMIN_CORRESP_RELANCEData.verticalAlignment = GridData.FILL;
						if(!decore) {
							tfCheminCorrespDocumentTiers = new Text(paCorpsFormulaire, SWT.BORDER);
							tfCheminCorrespDocumentTiers.setLayoutData(tfCHEMIN_CORRESP_RELANCEData);
						} else {					
						fieldCheminCorrespDocumentTiers = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
						tfCheminCorrespDocumentTiers = (Text)fieldCheminCorrespDocumentTiers.getControl();
						fieldCheminCorrespDocumentTiers.getLayoutControl().setLayoutData(tfCHEMIN_CORRESP_RELANCEData);
						}
					}
					{
						btnChemin_Corresp = new Button(paCorpsFormulaire, SWT.PUSH | SWT.CENTER);
						GridData btnChemin_CorrespLData = new GridData();
						btnChemin_CorrespLData.horizontalSpan =2;
						btnChemin_Corresp.setLayoutData(btnChemin_CorrespLData);
						btnChemin_Corresp.setText("Parcourir...");
					}
				}

			}


			this.layout();
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Group getGroupParam() {
		return groupParam;
	}

	public Group getGroupFactureNonRegle() {
		return groupFactureNonRegle;
	}


	
	public Combo getCbListeVersion() {
		return cbListeVersion;
	}
	
	public DefaultFrameFormulaireSWT getFormulaire() {
		return formulaire;
	}

	public Composite getPaCorpsFormulaire() {
		return paCorpsFormulaire;
	}

	public Label getLaCodeDocumentTiers() {
		return laCodeDocumentTiers;
	}

	public Label getLaLibelleDocumentTiers() {
		return laLibelleDocumentTiers;
	}

	public Label getLaCheminModelDocumentTiers() {
		return laCheminModelDocumentTiers;
	}

	public Label getLaCheminCorrespDocumentTiers() {
		return laCheminCorrespDocumentTiers;
	}

	public Text getTfCodeDocumentTiers() {
		return tfCodeDocumentTiers;
	}

	public Button getBtnChemin_Corresp() {
		return btnChemin_Corresp;
	}

	public Button getBtnChemin_Model() {
		return btnChemin_Model;
	}

	public Text getTfLibelleDocumentTiers() {
		return tfLibelleDocumentTiers;
	}

	public Text getTfCheminModelDocumentTiers() {
		return tfCheminModelDocumentTiers;
	}

	public Text getTfCheminCorrespDocumentTiers() {
		return tfCheminCorrespDocumentTiers;
	}

	public DecoratedField getFieldCodeDocumentTiers() {
		return fieldCodeDocumentTiers;
	}

	public DecoratedField getFieldLibelleDocumentTiers() {
		return fieldLibelleDocumentTiers;
	}

	public DecoratedField getFieldCheminDocumentTiers() {
		return fieldCheminDocumentTiers;
	}

	public DecoratedField getFieldCheminCorrespDocumentTiers() {
		return fieldCheminCorrespDocumentTiers;
	}

	public boolean isDecore() {
		return decore;
	}

	public Button getBtnOuvrir() {
		return btnOuvrir;
	}

}

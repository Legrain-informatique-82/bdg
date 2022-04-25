package fr.legrain.prelevement.exportation.ecrans;
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
public class PaTypeRelanceVersion extends org.eclipse.swt.widgets.Composite {
	private Group groupParam;
	private Group groupFactureNonRegle;

	private Combo cbListeVersion;
	private DefaultFrameFormulaireSWT formulaire;
//	private PaTypeRelanceSWT paTypeRelanceSWT1;
	private Composite paCorpsFormulaire;
	private Label laCODE_T_RELANCE;
	private Label laLIBELLE_T_RELANCE;
	private Label laCHEMIN_MODEL_RELANCE;
	private Label laCHEMIN_CORRESP_RELANCE;
	private Label laORDRE_T_RELANCE;
	
	private Text tfCODE_T_RELANCE;
	private Button btnChemin_Corresp;
	private Button btnChemin_Model;
	private Button btnOuvrir;
	private Text tfLIBELLE_T_RELANCE;
	private Text tfCHEMIN_MODEL_RELANCE;
	private Text tfCHEMIN_CORRESP_RELANCE;
	private Text tfORDRE_T_RELANCE;

	private DecoratedField fieldCODE_T_RELANCE;
	private DecoratedField fieldLIBELLE_T_RELANCE;
	private DecoratedField fieldCHEMIN_MODEL_RELANCE;
	private DecoratedField fieldCHEMIN_CORRESP_RELANCE;
	private DecoratedField fieldORDRE_T_RELANCE;

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
		PaTypeRelanceVersion inst = new PaTypeRelanceVersion(shell, SWT.NULL);
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

	public PaTypeRelanceVersion(org.eclipse.swt.widgets.Composite parent, int style) {
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
				groupFactureNonRegle.setText("Gestion des types de relance");
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
						laCODE_T_RELANCE = new Label(paCorpsFormulaire, SWT.NONE);
						GridData laCODE_T_RELANCELData = new GridData();
						laCODE_T_RELANCELData.horizontalSpan = 1;
						laCODE_T_RELANCE.setLayoutData(laCODE_T_RELANCELData);
						laCODE_T_RELANCE.setText("Code type relance :");
					}
					{
						GridData tfCODE_T_RELANCEData = new GridData();
						tfCODE_T_RELANCEData.horizontalAlignment = GridData.BEGINNING;
						tfCODE_T_RELANCEData.horizontalSpan = 3;
						tfCODE_T_RELANCEData.widthHint = 200;
						tfCODE_T_RELANCEData.verticalAlignment = GridData.FILL;
						if(!decore) {
							tfCODE_T_RELANCE = new Text(paCorpsFormulaire, SWT.BORDER);
							tfCODE_T_RELANCE.setLayoutData(tfCODE_T_RELANCEData);
						} else {					
						fieldCODE_T_RELANCE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
						tfCODE_T_RELANCE = (Text)fieldCODE_T_RELANCE.getControl();
						fieldCODE_T_RELANCE.getLayoutControl().setLayoutData(tfCODE_T_RELANCEData);
						}
					}
					{
						laLIBELLE_T_RELANCE = new Label(paCorpsFormulaire, SWT.NONE);
						GridData laLIBELLE_T_RELANCELData = new GridData();
						laLIBELLE_T_RELANCELData.horizontalSpan = 1;
						laLIBELLE_T_RELANCE.setLayoutData(laLIBELLE_T_RELANCELData);
						laLIBELLE_T_RELANCE.setText("Libellé type relance :");
					}
					{
						GridData tfLIBELLE_T_RELANCEData = new GridData();
						tfLIBELLE_T_RELANCEData.horizontalAlignment = GridData.BEGINNING;
						tfLIBELLE_T_RELANCEData.widthHint = 600;
						tfLIBELLE_T_RELANCEData.horizontalSpan = 3;
						tfLIBELLE_T_RELANCEData.verticalAlignment = GridData.FILL;
						if(!decore) {
							tfLIBELLE_T_RELANCE = new Text(paCorpsFormulaire, SWT.BORDER);
							tfLIBELLE_T_RELANCE.setLayoutData(tfLIBELLE_T_RELANCEData);
						} else {					
							fieldLIBELLE_T_RELANCE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
							tfLIBELLE_T_RELANCE = (Text)fieldLIBELLE_T_RELANCE.getControl();
							fieldLIBELLE_T_RELANCE.getLayoutControl().setLayoutData(tfLIBELLE_T_RELANCEData);
						}
					}
					{
						laCHEMIN_MODEL_RELANCE = new Label(paCorpsFormulaire, SWT.NONE);
						laCHEMIN_MODEL_RELANCE.setText("Adresse du fichier modèle :");
					}
					{
						GridData tfCHEMIN_T_RELANCEData = new GridData();
						tfCHEMIN_T_RELANCEData.horizontalAlignment = GridData.BEGINNING;
						tfCHEMIN_T_RELANCEData.widthHint = 600;
						tfCHEMIN_T_RELANCEData.verticalAlignment = GridData.FILL;
						if(!decore) {
							tfCHEMIN_MODEL_RELANCE = new Text(paCorpsFormulaire, SWT.BORDER);
							tfCHEMIN_MODEL_RELANCE.setLayoutData(tfCHEMIN_T_RELANCEData);
						} else {					
						fieldCHEMIN_MODEL_RELANCE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
						tfCHEMIN_MODEL_RELANCE = (Text)fieldCHEMIN_MODEL_RELANCE.getControl();
						fieldCHEMIN_MODEL_RELANCE.getLayoutControl().setLayoutData(tfCHEMIN_T_RELANCEData);
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
						laCHEMIN_CORRESP_RELANCE = new Label(paCorpsFormulaire, SWT.NONE);
						GridData laLIBELLE_T_RELANCELData = new GridData();
						laLIBELLE_T_RELANCELData.horizontalSpan = 1;
						laCHEMIN_CORRESP_RELANCE.setLayoutData(laLIBELLE_T_RELANCELData);						
						laCHEMIN_CORRESP_RELANCE.setText("Adresse du fichier de correspondance :");
					}
					{
						GridData tfCHEMIN_CORRESP_RELANCEData = new GridData();
						tfCHEMIN_CORRESP_RELANCEData.horizontalAlignment = GridData.BEGINNING;
						tfCHEMIN_CORRESP_RELANCEData.widthHint = 600;
						tfCHEMIN_CORRESP_RELANCEData.horizontalSpan = 1;
						tfCHEMIN_CORRESP_RELANCEData.verticalAlignment = GridData.FILL;
						if(!decore) {
							tfCHEMIN_CORRESP_RELANCE = new Text(paCorpsFormulaire, SWT.BORDER);
							tfCHEMIN_CORRESP_RELANCE.setLayoutData(tfCHEMIN_CORRESP_RELANCEData);
						} else {					
						fieldCHEMIN_CORRESP_RELANCE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
						tfCHEMIN_CORRESP_RELANCE = (Text)fieldCHEMIN_CORRESP_RELANCE.getControl();
						fieldCHEMIN_CORRESP_RELANCE.getLayoutControl().setLayoutData(tfCHEMIN_CORRESP_RELANCEData);
						}
					}
					{
						btnChemin_Corresp = new Button(paCorpsFormulaire, SWT.PUSH | SWT.CENTER);
						GridData btnChemin_CorrespLData = new GridData();
						btnChemin_CorrespLData.horizontalSpan =2;
						btnChemin_Corresp.setLayoutData(btnChemin_CorrespLData);
						btnChemin_Corresp.setText("Parcourir...");
					}
					{
						laORDRE_T_RELANCE = new Label(paCorpsFormulaire, SWT.NONE);
						GridData laORDRE_T_RELANCELData = new GridData();
						laORDRE_T_RELANCELData.horizontalSpan = 1;
						laORDRE_T_RELANCE.setLayoutData(laORDRE_T_RELANCELData);
						laORDRE_T_RELANCE.setText("Ordre de la relance");
					}
					{
						GridData tfORDRE_T_RELANCEData = new GridData();
						tfORDRE_T_RELANCEData.horizontalAlignment = GridData.BEGINNING;
						tfORDRE_T_RELANCEData.widthHint = 30;
						tfORDRE_T_RELANCEData.horizontalSpan = 3;
						tfORDRE_T_RELANCEData.verticalAlignment = GridData.FILL;
						if(!decore) {
							tfORDRE_T_RELANCE = new Text(paCorpsFormulaire, SWT.BORDER);
							tfORDRE_T_RELANCE.setLayoutData(tfORDRE_T_RELANCEData);
						} else {					
						fieldORDRE_T_RELANCE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
						tfORDRE_T_RELANCE = (Text)fieldORDRE_T_RELANCE.getControl();
						fieldORDRE_T_RELANCE.getLayoutControl().setLayoutData(tfORDRE_T_RELANCEData);
						}
					}

				}
//				{
//					GridData paTypeRelanceSWT1LData = new GridData();
//					paTypeRelanceSWT1 = new PaTypeRelanceSWT(groupFactureNonRegle, SWT.NONE);
//					paTypeRelanceSWT1.setLayoutData(paTypeRelanceSWT1LData);
//				}
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
//	
//	public PaTypeRelanceSWT getPaTypeRelanceSWT1() {
//		return paTypeRelanceSWT1;
//	}

	public Composite getPaCorpsFormulaire() {
		return paCorpsFormulaire;
	}

	public Label getLaCODE_T_RELANCE() {
		return laCODE_T_RELANCE;
	}

	public Label getLaLIBELLE_T_RELANCE() {
		return laLIBELLE_T_RELANCE;
	}

	public Label getLaCHEMIN_MODEL_RELANCE() {
		return laCHEMIN_MODEL_RELANCE;
	}

	public Label getLaCHEMIN_CORRESP_RELANCE() {
		return laCHEMIN_CORRESP_RELANCE;
	}

	public Label getLaORDRE_T_RELANCE() {
		return laORDRE_T_RELANCE;
	}

	public Text getTfCODE_T_RELANCE() {
		return tfCODE_T_RELANCE;
	}

	public Button getBtnChemin_Corresp() {
		return btnChemin_Corresp;
	}

	public Button getBtnChemin_Model() {
		return btnChemin_Model;
	}

	public Text getTfLIBELLE_T_RELANCE() {
		return tfLIBELLE_T_RELANCE;
	}

	public Text getTfCHEMIN_MODEL_RELANCE() {
		return tfCHEMIN_MODEL_RELANCE;
	}

	public Text getTfCHEMIN_CORRESP_RELANCE() {
		return tfCHEMIN_CORRESP_RELANCE;
	}

	public Text getTfORDRE_T_RELANCE() {
		return tfORDRE_T_RELANCE;
	}

	public DecoratedField getFieldCODE_T_RELANCE() {
		return fieldCODE_T_RELANCE;
	}

	public DecoratedField getFieldLIBELLE_T_RELANCE() {
		return fieldLIBELLE_T_RELANCE;
	}

	public DecoratedField getFieldCHEMIN_MODEL_RELANCE() {
		return fieldCHEMIN_MODEL_RELANCE;
	}

	public DecoratedField getFieldCHEMIN_CORRESP_RELANCE() {
		return fieldCHEMIN_CORRESP_RELANCE;
	}

	public DecoratedField getFieldORDRE_T_RELANCE() {
		return fieldORDRE_T_RELANCE;
	}

	public boolean isDecore() {
		return decore;
	}

	public Button getBtnOuvrir() {
		return btnOuvrir;
	}





}

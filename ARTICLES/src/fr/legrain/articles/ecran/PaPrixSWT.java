package fr.legrain.articles.ecran;

import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.fieldassist.TextControlCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.resource.SWTResourceManager;

import fr.legrain.lib.gui.fieldassist.ButtonControlCreator;
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
public class PaPrixSWT extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	private Composite paCorpsFormulaire;
//	private Label laCODE_ARTICLE;
	private Label laCODE_UNITE;
	private Label laPRIX_PRIX;
	private Label laPRIXTTC_PRIX;
	private Label laID_REF_PRIX;
	private Label laCodeTiers;
	private Label laCodeTTarif;

	private Text tfCODE_UNITE;
	private Text tfPRIXTTC_PRIX;
//	private Text tfCODE_ARTICLE;
	private Text tfPRIX_PRIX;
	private Text tfCodeTiers;
	private Text tfCodeTTarif;
	
	private Group group;
	
	private Button cbID_REF_PRIX;
	
	private Button btnAjouter;
	private Button btnSupprimerCible;
	
	private Table table;

	private DecoratedField fieldCODE_UNITE;
	private DecoratedField fieldPRIXTTC_PRIX;
//	private DecoratedField fieldCODE_ARTICLE;
	private DecoratedField fieldPRIX_PRIX;
	private DecoratedField fieldID_REF_PRIX;
	private DecoratedField fieldCodeTiers;
	private DecoratedField fieldCodeTTarif;

	final private boolean decore = LgrMess.isDECORE();

	/**
	 * Auto-generated main method to display this 
	 * fr.legrain.lib.gui.DefaultFrameFormulaireSWT inside a new Shell.
	 */
	public static void main(String[] args) {
		showGUI();
	}

	/**
	 * Auto-generated method to display this 
	 * fr.legrain.lib.gui.DefaultFrameFormulaireSWT inside a new Shell.
	 */
	public static void showGUI() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		PaPrixSWT inst = new PaPrixSWT(shell, SWT.NULL);
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

	public PaPrixSWT(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			{
				paCorpsFormulaire = new Composite(super.getPaFomulaire(), SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 2;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.grabExcessVerticalSpace = true;
				paCorpsFormulaire.setLayoutData(composite1LData);
				paCorpsFormulaire.setLayout(composite1Layout);
				{
					laCODE_UNITE = new Label(paCorpsFormulaire, SWT.NONE);
					laCODE_UNITE.setText("Code unité");
				}
				{
					GridData tfCODE_UNITELData = new GridData();
					tfCODE_UNITELData.horizontalAlignment = GridData.FILL;
					if(!decore) {
						tfCODE_UNITE = new Text(paCorpsFormulaire, SWT.BORDER);
						tfCODE_UNITE.setLayoutData(tfCODE_UNITELData);
					} else {					
					fieldCODE_UNITE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfCODE_UNITE = (Text)fieldCODE_UNITE.getControl();
					fieldCODE_UNITE.getLayoutControl().setLayoutData(tfCODE_UNITELData);
					}
				}
				{
					laPRIX_PRIX = new Label(paCorpsFormulaire, SWT.NONE);
					laPRIX_PRIX.setText("Tarif HT");
				}
				{
					GridData tfPRIX_PRIXLData = new GridData();
					tfPRIX_PRIXLData.horizontalAlignment = GridData.FILL;
					if(!decore) {
						tfPRIX_PRIX = new Text(paCorpsFormulaire, SWT.BORDER);
						tfPRIX_PRIX.setLayoutData(tfPRIX_PRIXLData);
					} else {					
					fieldPRIX_PRIX = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfPRIX_PRIX = (Text)fieldPRIX_PRIX.getControl();
					fieldPRIX_PRIX.getLayoutControl().setLayoutData(tfPRIX_PRIXLData);
					}
				}
				{
					laPRIXTTC_PRIX = new Label(paCorpsFormulaire, SWT.NONE);
					laPRIXTTC_PRIX.setText("Tarif TTC");
				}
				{
					GridData tfPRIXTTC_PRIXLData = new GridData();
					tfPRIXTTC_PRIXLData.horizontalAlignment = GridData.FILL;
					tfPRIXTTC_PRIXLData.widthHint = 177;
					if(!decore) {
						tfPRIXTTC_PRIX = new Text(paCorpsFormulaire, SWT.BORDER);
						tfPRIXTTC_PRIX.setLayoutData(tfPRIXTTC_PRIXLData);
					} else {					
					fieldPRIXTTC_PRIX = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfPRIXTTC_PRIX = (Text)fieldPRIXTTC_PRIX.getControl();
					fieldPRIXTTC_PRIX.getLayoutControl().setLayoutData(tfPRIXTTC_PRIXLData);
					}
				}
				{
					laID_REF_PRIX = new Label(paCorpsFormulaire, SWT.NONE);
					laID_REF_PRIX.setText("Tarif de référence");
				}
				{
					GridData cbID_REF_PRIXLData = new GridData();
					cbID_REF_PRIXLData.widthHint = 22;
					if(!decore) {
						cbID_REF_PRIX = new Button(paCorpsFormulaire, SWT.CHECK | SWT.LEFT);
						cbID_REF_PRIX.setLayoutData(cbID_REF_PRIXLData);
					} else {					
					fieldID_REF_PRIX = new DecoratedField(paCorpsFormulaire, SWT.CHECK | SWT.LEFT, new ButtonControlCreator());
					cbID_REF_PRIX = (Button)fieldID_REF_PRIX.getControl();
					fieldID_REF_PRIX.getLayoutControl().setLayoutData(cbID_REF_PRIXLData);
					}
				}
			}
			
			{
				group = new Group(paCorpsFormulaire, SWT.NONE);
				GridLayout groupLayout = new GridLayout();
				groupLayout.numColumns = 4;
				group.setLayout(groupLayout);
				GridData groupLData = new GridData();
				groupLData.grabExcessHorizontalSpace = true;
				groupLData.grabExcessVerticalSpace = true;
				groupLData.horizontalSpan = 2;
				groupLData.horizontalAlignment = GridData.FILL;
				groupLData.verticalAlignment = GridData.FILL;
				group.setLayoutData(groupLData);
				group.setText("Tiers / Type tarif");
			}
			
			{
				laCodeTiers = new Label(group, SWT.NONE);
				laCodeTiers.setText("Code tiers");
			}
			{
				GridData tfCODE_UNITELData = new GridData();
				tfCODE_UNITELData.horizontalAlignment = GridData.FILL;
				tfCODE_UNITELData.grabExcessHorizontalSpace = true;
				if(!decore) {
					tfCodeTiers = new Text(group, SWT.BORDER);
					tfCodeTiers.setLayoutData(tfCODE_UNITELData);
				} else {					
				fieldCodeTiers = new DecoratedField(group, SWT.BORDER, new TextControlCreator());
				tfCodeTiers = (Text)fieldCodeTiers.getControl();
				fieldCodeTiers.getLayoutControl().setLayoutData(tfCODE_UNITELData);
				}
			}
			
			{
				laCodeTTarif = new Label(group, SWT.NONE);
				laCodeTTarif.setText("Code type tarif");
			}
			{
				GridData tfCODE_UNITELData = new GridData();
				tfCODE_UNITELData.horizontalAlignment = GridData.FILL;
				tfCODE_UNITELData.grabExcessHorizontalSpace = true;
				if(!decore) {
					tfCodeTTarif = new Text(group, SWT.BORDER);
					tfCodeTTarif.setLayoutData(tfCODE_UNITELData);
				} else {					
				fieldCodeTTarif = new DecoratedField(group, SWT.BORDER, new TextControlCreator());
				tfCodeTTarif = (Text)fieldCodeTTarif.getControl();
				fieldCodeTTarif.getLayoutControl().setLayoutData(tfCODE_UNITELData);
				}
			}
			
			{
				btnAjouter = new Button(group, SWT.PUSH);
				btnAjouter.setText("Ajouter");
				GridData tfCODE_UNITELData = new GridData();
				tfCODE_UNITELData.horizontalSpan = 2;
				btnAjouter.setLayoutData(tfCODE_UNITELData);
			}
			
			{
				btnSupprimerCible = new Button(group, SWT.PUSH);
				btnSupprimerCible.setText("Supprimer");
				GridData tfCODE_UNITELData = new GridData();
				tfCODE_UNITELData.horizontalSpan = 2;
				btnSupprimerCible.setLayoutData(tfCODE_UNITELData);
			}
			
			{
				GridData grilleLData = new GridData();
				grilleLData.horizontalAlignment = GridData.FILL;
				grilleLData.verticalAlignment = GridData.FILL;
				grilleLData.grabExcessVerticalSpace = true;
				grilleLData.grabExcessHorizontalSpace = true;
				grilleLData.horizontalSpan = 4;
				//if(tableStyle==SWT.NULL) {
					table = new Table(group, SWT.SINGLE
							| SWT.FULL_SELECTION
							| SWT.H_SCROLL
							| SWT.V_SCROLL
							| SWT.BORDER);
//							| SWT.CHECK);
				//} else {
				//	table = new Table(group, tableStyle);
				//}
				table.setLayoutData(grilleLData);
				table.setHeaderVisible(true);
				table.setLinesVisible(true);
			}

			this.setLayout(new GridLayout());
			GridLayout compositeFormLayout = new GridLayout();
			compositeFormLayout.numColumns = 2;
			this.setSize(646, 690);
			GridData paGrilleLData = new GridData();
			paGrilleLData.grabExcessHorizontalSpace = true;
			paGrilleLData.horizontalAlignment = GridData.FILL;
			paGrilleLData.verticalAlignment = GridData.FILL;
			paGrilleLData.grabExcessVerticalSpace = true;
			super.getCompositeForm().setLayout(compositeFormLayout);
			GridData paFomulaireLData = new GridData();
			paFomulaireLData.verticalAlignment = GridData.FILL;
			paFomulaireLData.grabExcessVerticalSpace = true;
			paFomulaireLData.horizontalAlignment = GridData.FILL;
			super.getPaGrille().setLayoutData(paGrilleLData);
			GridData compositeFormLData = new GridData();
			compositeFormLData.grabExcessHorizontalSpace = true;
			compositeFormLData.verticalAlignment = GridData.FILL;
			compositeFormLData.horizontalAlignment = GridData.FILL;
			compositeFormLData.grabExcessVerticalSpace = true;
			super.getPaFomulaire().setLayoutData(paFomulaireLData);
			GridData grilleLData = new GridData();
			grilleLData.verticalAlignment = GridData.FILL;
			grilleLData.horizontalAlignment = GridData.FILL;
			grilleLData.grabExcessVerticalSpace = true;
			super.getCompositeForm().setLayoutData(compositeFormLData);
			super.getGrille().setLayoutData(grilleLData);
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Composite getPaCorpsFormulaire() {
		return paCorpsFormulaire;
	}

	public Label getLaPRIXTTC_PRIX() {
		return laPRIXTTC_PRIX;
	}

	public Label getLaCODE_UNITE() {
		return laCODE_UNITE;
	}

//	public Label getLaCODE_ARTICLE() {
//		return laCODE_ARTICLE;
//	}

	public Label getLaPRIX_PRIX() {
		return laPRIX_PRIX;
	}

	public Text getTfPRIXTTC_PRIX() {
		return tfPRIXTTC_PRIX;
	}

	public Text getTfCODE_UNITE() {
		return tfCODE_UNITE;
	}

	public Text getTfPRIX_PRIX() {
		return tfPRIX_PRIX;
	}

//	public Text getTfCODE_ARTICLE() {
//		return tfCODE_ARTICLE;
//	}

	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public DecoratedField getFieldPRIXTTC_PRIX() {
		return fieldPRIXTTC_PRIX;
	}

	public DecoratedField getFieldCODE_UNITE() {
		return fieldCODE_UNITE;
	}

	public DecoratedField getFieldPRIX_PRIX() {
		return fieldPRIX_PRIX;
	}

//	public DecoratedField getFieldCODE_ARTICLE() {
//		return fieldCODE_ARTICLE;
//	}

	public Button getCbID_REF_PRIX() {
		return cbID_REF_PRIX;
	}

	public DecoratedField getFieldID_REF_PRIX() {
		return fieldID_REF_PRIX;
	}

	public Label getLaID_REF_PRIX() {
		return laID_REF_PRIX;
	}

	public Label getLaCodeTiers() {
		return laCodeTiers;
	}

	public Label getLaCodeTTarif() {
		return laCodeTTarif;
	}

	public Text getTfCodeTiers() {
		return tfCodeTiers;
	}

	public Text getTfCodeTTarif() {
		return tfCodeTTarif;
	}

	public Group getGroup() {
		return group;
	}

	public Button getBtnAjouter() {
		return btnAjouter;
	}

	public Button getBtnSupprimerCible() {
		return btnSupprimerCible;
	}

	public Table getTable() {
		return table;
	}

	public DecoratedField getFieldCodeTiers() {
		return fieldCodeTiers;
	}

	public DecoratedField getFieldCodeTTarif() {
		return fieldCodeTTarif;
	}

	public boolean isDecore() {
		return decore;
	}

}

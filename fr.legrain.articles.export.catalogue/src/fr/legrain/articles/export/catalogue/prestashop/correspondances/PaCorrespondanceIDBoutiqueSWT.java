package fr.legrain.articles.export.catalogue.prestashop.correspondances;

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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

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
public class PaCorrespondanceIDBoutiqueSWT extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

	private Composite paCorpsFormulaire;
	private Label laIdBdg;
	private Label laValeurBdg;
	private Label laValeurBoutique;
	private Label laIdBoutique;
//	private Label laBoutique;
	private Label laType;
	private Label laCPaiementDocDefaut;

	private Text tfIdBdg;
	private Text tfValeurBdg;
	private Text tfValeurBoutique;
	private Text tfIdBoutique;
//	private Text tfBoutique;
	private Combo cbType;

	private DecoratedField fieldIdBdg;
	private DecoratedField fieldValeurBdg;
	private DecoratedField fieldIdBoutique;	
	private DecoratedField fieldValeurBoutique;

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
		PaCorrespondanceIDBoutiqueSWT inst = new PaCorrespondanceIDBoutiqueSWT(shell, SWT.NULL);
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

	public PaCorrespondanceIDBoutiqueSWT(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			{
				paCorpsFormulaire = new Composite(
						super.getPaFomulaire(),
						SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 2;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.grabExcessVerticalSpace = true;
				composite1LData.horizontalSpan = 3;
				composite1LData.grabExcessHorizontalSpace = true;
				paCorpsFormulaire.setLayoutData(composite1LData);
				paCorpsFormulaire.setLayout(composite1Layout);

				{
					laType = new Label(paCorpsFormulaire, SWT.NONE);
					laType.setText("Type de correspondance");
				}
				{
					GridData cbTypeCPaiementLData = new GridData();
					cbTypeCPaiementLData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
					cbTypeCPaiementLData.grabExcessHorizontalSpace = true;
					cbTypeCPaiementLData.widthHint = 156;
					cbTypeCPaiementLData.verticalAlignment = GridData.FILL;
					//					if(!decore) {
					cbType = new Combo(paCorpsFormulaire, SWT.BORDER | SWT.READ_ONLY);
					cbType.setLayoutData(cbTypeCPaiementLData);
					//					} else {					
					//					fieldCODE_C_PAIEMENT = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					//					cbTypeCPaiement = (Text)fieldCODE_C_PAIEMENT.getControl();
					//					fieldCODE_C_PAIEMENT.getLayoutControl().setLayoutData(tfCODE_C_PAIEMENTLData);
					//					}
				}

				{
					laIdBdg = new Label(paCorpsFormulaire, SWT.NONE);
					laIdBdg.setText("ID BDG");
				}
				{
					GridData tfCODE_C_PAIEMENTLData = new GridData();
					tfCODE_C_PAIEMENTLData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
					tfCODE_C_PAIEMENTLData.grabExcessHorizontalSpace = true;
					tfCODE_C_PAIEMENTLData.widthHint = 156;
					tfCODE_C_PAIEMENTLData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfIdBdg = new Text(paCorpsFormulaire, SWT.BORDER);
						tfIdBdg.setLayoutData(tfCODE_C_PAIEMENTLData);
					} else {					
						fieldIdBdg = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
						tfIdBdg = (Text)fieldIdBdg.getControl();
						fieldIdBdg.getLayoutControl().setLayoutData(tfCODE_C_PAIEMENTLData);
					}
				}
				{
					laValeurBdg = new Label(paCorpsFormulaire, SWT.NONE);
					laValeurBdg.setText("Valeur BDG");
				}
				{
					GridData tfLIB_C_PAIEMENTLData = new GridData();
					tfLIB_C_PAIEMENTLData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
					tfLIB_C_PAIEMENTLData.grabExcessHorizontalSpace = true;
					tfLIB_C_PAIEMENTLData.widthHint = 156;
					tfLIB_C_PAIEMENTLData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfValeurBdg = new Text(paCorpsFormulaire, SWT.BORDER);
						tfValeurBdg.setLayoutData(tfLIB_C_PAIEMENTLData);
					} else {					
						fieldValeurBdg = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
						tfValeurBdg = (Text)fieldValeurBdg.getControl();
						fieldValeurBdg.getLayoutControl().setLayoutData(tfLIB_C_PAIEMENTLData);
					}
				}
				{
					laIdBoutique = new Label(paCorpsFormulaire, SWT.NONE);
					laIdBoutique.setText("ID Boutique");
				}
				{
					GridData tfREPORT_C_PAIEMENTLData = new GridData();
					tfREPORT_C_PAIEMENTLData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
					tfREPORT_C_PAIEMENTLData.grabExcessHorizontalSpace = true;
					tfREPORT_C_PAIEMENTLData.widthHint = 156;
					tfREPORT_C_PAIEMENTLData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfIdBoutique = new Text(paCorpsFormulaire, SWT.BORDER);
						tfIdBoutique.setLayoutData(tfREPORT_C_PAIEMENTLData);
					} else {					
						fieldIdBoutique = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
						tfIdBoutique = (Text)fieldIdBoutique.getControl();
						fieldIdBoutique.getLayoutControl().setLayoutData(tfREPORT_C_PAIEMENTLData);
					}
				}
				{
					laValeurBoutique = new Label(paCorpsFormulaire, SWT.NONE);
					laValeurBoutique.setText("Valeur boutique");
				}
				{
					GridData tfLIB_C_PAIEMENTLData = new GridData();
					tfLIB_C_PAIEMENTLData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
					tfLIB_C_PAIEMENTLData.grabExcessHorizontalSpace = true;
					tfLIB_C_PAIEMENTLData.widthHint = 156;
					tfLIB_C_PAIEMENTLData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfValeurBoutique = new Text(paCorpsFormulaire, SWT.BORDER);
						tfValeurBoutique.setLayoutData(tfLIB_C_PAIEMENTLData);
					} else {					
						fieldValeurBoutique = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
						tfValeurBoutique = (Text)fieldValeurBoutique.getControl();
						fieldValeurBoutique.getLayoutControl().setLayoutData(tfLIB_C_PAIEMENTLData);
					}
				}
			}

			this.setLayout(new GridLayout());
			GridLayout compositeFormLayout = new GridLayout();
			compositeFormLayout.numColumns = 2;
			this.setSize(682, 384);
			GridData paGrilleLData = new GridData();
			paGrilleLData.grabExcessVerticalSpace = true;
			paGrilleLData.verticalAlignment = GridData.FILL;
			paGrilleLData.horizontalAlignment = GridData.FILL;
			paGrilleLData.grabExcessHorizontalSpace = true;
			super.getCompositeForm().setLayout(compositeFormLayout);
			GridData paFomulaireLData = new GridData();
			paFomulaireLData.verticalAlignment = GridData.FILL;
			paFomulaireLData.grabExcessVerticalSpace = true;
			paFomulaireLData.horizontalAlignment = GridData.FILL;
			super.getPaGrille().setLayoutData(paGrilleLData);
			GridData compositeFormLData = new GridData();
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

	public DecoratedField getFieldIdBdg() {
		return fieldIdBdg;
	}

	public DecoratedField getFieldValeurBdg() {
		return fieldValeurBdg;
	}

	public DecoratedField getFieldIdBoutique() {
		return fieldIdBoutique;
	}

	public Label getLaIdBdg() {
		return laIdBdg;
	}

	public Label getLaValeurBdg() {
		return laValeurBdg;
	}

	public Label getLaIdBoutique() {
		return laIdBoutique;
	}

	public Composite getPaCorpsFormulaire() {
		return paCorpsFormulaire;
	}

	public Text getTfIdBdg() {
		return tfIdBdg;
	}

	public Text getTfValeurBdg() {
		return tfValeurBdg;
	}

	public Text getTfIdBoutique() {
		return tfIdBoutique;
	}

	public void setFieldIdBdg(DecoratedField fieldCODE_BANQUE) {
		this.fieldIdBdg = fieldCODE_BANQUE;
	}

	public void setFieldValeurBdg(DecoratedField fieldLIBC_BANQUE) {
		this.fieldValeurBdg = fieldLIBC_BANQUE;
	}

	public void setFieldIdBoutique(DecoratedField fieldLIBL_BANQUE) {
		this.fieldIdBoutique = fieldLIBL_BANQUE;
	}

	public void setLaIdBdg(Label laCODE_BANQUE) {
		this.laIdBdg = laCODE_BANQUE;
	}

	public void setLaValeurBdg(Label laLIBC_BANQUE) {
		this.laValeurBdg = laLIBC_BANQUE;
	}

	public void setLaIdBoutique(Label laLIBL_BANQUE) {
		this.laIdBoutique = laLIBL_BANQUE;
	}

	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public void setTfIdBdg(Text tfCODE_BANQUE) {
		this.tfIdBdg = tfCODE_BANQUE;
	}

	public void setTfValeurBdg(Text tfLIBC_BANQUE) {
		this.tfValeurBdg = tfLIBC_BANQUE;
	}

	public void setTfIdBoutique(Text tfLIBL_BANQUE) {
		this.tfIdBoutique = tfLIBL_BANQUE;
	}

//	public Label getLaBoutique() {
//		return laBoutique;
//	}
//
//	public Text getTfBoutique() {
//		return tfBoutique;
//	}

	public DecoratedField getFieldValeurBoutique() {
		return fieldValeurBoutique;
	}

	public Label getLaType() {
		return laType;
	}

	public Combo getCbType() {
		return cbType;
	}

	public Label getLaCPaiementDocDefaut() {
		return laCPaiementDocDefaut;
	}

	public Label getLaValeurBoutique() {
		return laValeurBoutique;
	}

	public Text getTfValeurBoutique() {
		return tfValeurBoutique;
	}

}

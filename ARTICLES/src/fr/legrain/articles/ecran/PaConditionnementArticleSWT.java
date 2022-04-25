package fr.legrain.articles.ecran;

import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.fieldassist.TextControlCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
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
public class PaConditionnementArticleSWT extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	private Composite paCorpsFormulaire;
//	private Label laCodeType;
	private Label laCode;
	private Label laLibelle;
	private Label laLongueur;
	private Label laLargeur;
	private Label laHauteur;
	private Label laPoids;
	private Label laNbUnite;
//	private Label laReduction;
//	private Label laDefaut;

//	private Text tfCodeType;
	private Text tfCode;
	private Text tfLibelle;
	private Text tfLongueur;
	private Text tfLargeur;
	private Text tfHauteur;
	private Text tfPoids;
	private Text tfNbUnite;
//	private Text tfReduction;
//	
//	private Button cbDefaut;

//	private DecoratedField fieldCodeType;
	private DecoratedField fieldCode;
	private DecoratedField fieldLibelle;
	private DecoratedField fieldLongueur;
	private DecoratedField fieldLargeur;
	private DecoratedField fieldHauteur;
	private DecoratedField fieldPoids;
	private DecoratedField fieldNbUnite;
//	private DecoratedField fieldReduction;
//	private DecoratedField fieldDefaut;

	final private boolean decore = LgrMess.isDECORE();
	//final private boolean decore = false;

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
		PaConditionnementArticleSWT inst = new PaConditionnementArticleSWT(shell, SWT.NULL);
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

	public PaConditionnementArticleSWT(org.eclipse.swt.widgets.Composite parent, int style) {
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
//				{
//					laCodeType = new Label(paCorpsFormulaire, SWT.NONE);
//					laCodeType.setText("Code type");
//				}
//				{
//					GridData tfCheminImageLData = new GridData();
//					tfCheminImageLData.horizontalAlignment = GridData.FILL;
//					if(!decore) {
//						tfCodeType = new Text(paCorpsFormulaire, SWT.BORDER);
//						tfCodeType.setLayoutData(tfCheminImageLData);
//					} else {					
//						fieldCodeType = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
//						tfCodeType = (Text)fieldCodeType.getControl();
//						fieldCodeType.getLayoutControl().setLayoutData(tfCheminImageLData);
//					}
//				}
				{
					laCode = new Label(paCorpsFormulaire, SWT.NONE);
					laCode.setText("Code");
				}
				{
					GridData tfCheminImageLData = new GridData();
					tfCheminImageLData.horizontalAlignment = GridData.FILL;
					if(!decore) {
						tfCode = new Text(paCorpsFormulaire, SWT.BORDER);
						tfCode.setLayoutData(tfCheminImageLData);
					} else {					
						fieldCode = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
						tfCode = (Text)fieldCode.getControl();
						fieldCode.getLayoutControl().setLayoutData(tfCheminImageLData);
					}
				}
				{
					laLibelle = new Label(paCorpsFormulaire, SWT.NONE);
					laLibelle.setText("Libellé");
				}
				{
					GridData tfCheminImageLData = new GridData();
					tfCheminImageLData.horizontalAlignment = GridData.FILL;
					if(!decore) {
						tfLibelle = new Text(paCorpsFormulaire, SWT.BORDER);
						tfLibelle.setLayoutData(tfCheminImageLData);
					} else {					
						fieldLibelle = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
						tfLibelle = (Text)fieldLibelle.getControl();
						fieldLibelle.getLayoutControl().setLayoutData(tfCheminImageLData);
					}
				}
				{
					laLongueur = new Label(paCorpsFormulaire, SWT.NONE);
					laLongueur.setText("Longueur");
				}
				{
					GridData tfCheminImageLData = new GridData();
					tfCheminImageLData.horizontalAlignment = GridData.FILL;
					if(!decore) {
						tfLongueur = new Text(paCorpsFormulaire, SWT.BORDER);
						tfLongueur.setLayoutData(tfCheminImageLData);
					} else {					
						fieldLongueur = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
						tfLongueur = (Text)fieldLongueur.getControl();
						fieldLongueur.getLayoutControl().setLayoutData(tfCheminImageLData);
					}
				}
				{
					laLargeur = new Label(paCorpsFormulaire, SWT.NONE);
					laLargeur.setText("Largeur");
				}
				{
					GridData tfPRIXTTC_PRIXLData = new GridData();
					tfPRIXTTC_PRIXLData.horizontalAlignment = GridData.FILL;
					tfPRIXTTC_PRIXLData.widthHint = 177;
					if(!decore) {
						tfLargeur = new Text(paCorpsFormulaire, SWT.BORDER);
						tfLargeur.setLayoutData(tfPRIXTTC_PRIXLData);
					} else {					
						fieldLargeur = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
						tfLargeur = (Text)fieldLargeur.getControl();
						fieldLargeur.getLayoutControl().setLayoutData(tfPRIXTTC_PRIXLData);
					}
				}
				{
					laHauteur = new Label(paCorpsFormulaire, SWT.NONE);
					laHauteur.setText("Hauteur");
				}
				{
					GridData tfPRIXTTC_PRIXLData = new GridData();
					tfPRIXTTC_PRIXLData.horizontalAlignment = GridData.FILL;
					tfPRIXTTC_PRIXLData.widthHint = 177;
					if(!decore) {
						tfHauteur = new Text(paCorpsFormulaire, SWT.BORDER);
						tfHauteur.setLayoutData(tfPRIXTTC_PRIXLData);
					} else {					
						fieldHauteur = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
						tfHauteur = (Text)fieldHauteur.getControl();
						fieldHauteur.getLayoutControl().setLayoutData(tfPRIXTTC_PRIXLData);
					}
				}
				
				{
					laPoids = new Label(paCorpsFormulaire, SWT.NONE);
					laPoids.setText("Poids");
				}
				{
					GridData tfPRIXTTC_PRIXLData = new GridData();
					tfPRIXTTC_PRIXLData.horizontalAlignment = GridData.FILL;
					tfPRIXTTC_PRIXLData.widthHint = 177;
					if(!decore) {
						tfPoids = new Text(paCorpsFormulaire, SWT.BORDER);
						tfPoids.setLayoutData(tfPRIXTTC_PRIXLData);
					} else {					
						fieldPoids = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
						tfPoids = (Text)fieldPoids.getControl();
						fieldPoids.getLayoutControl().setLayoutData(tfPRIXTTC_PRIXLData);
					}
				}
				
				{
					laNbUnite = new Label(paCorpsFormulaire, SWT.NONE);
					laNbUnite.setText("Nombre d'unité");
				}
				{
					GridData tfPRIXTTC_PRIXLData = new GridData();
					tfPRIXTTC_PRIXLData.horizontalAlignment = GridData.FILL;
					tfPRIXTTC_PRIXLData.widthHint = 177;
					if(!decore) {
						tfNbUnite = new Text(paCorpsFormulaire, SWT.BORDER);
						tfNbUnite.setLayoutData(tfPRIXTTC_PRIXLData);
					} else {					
						fieldNbUnite = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
						tfNbUnite = (Text)fieldNbUnite.getControl();
						fieldNbUnite.getLayoutControl().setLayoutData(tfPRIXTTC_PRIXLData);
					}
				}
				
//				{
//					laReduction = new Label(paCorpsFormulaire, SWT.NONE);
//					laReduction.setText("Réduction");
//				}
//				{
//					GridData tfPRIXTTC_PRIXLData = new GridData();
//					tfPRIXTTC_PRIXLData.horizontalAlignment = GridData.FILL;
//					tfPRIXTTC_PRIXLData.widthHint = 177;
//					if(!decore) {
//						tfReduction = new Text(paCorpsFormulaire, SWT.BORDER);
//						tfReduction.setLayoutData(tfPRIXTTC_PRIXLData);
//					} else {					
//						fieldReduction = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
//						tfReduction = (Text)fieldReduction.getControl();
//						fieldReduction.getLayoutControl().setLayoutData(tfPRIXTTC_PRIXLData);
//					}
//				}
				
			}
			
//			{
//				laDefaut = new Label(paCorpsFormulaire, SWT.NONE);
//				laDefaut.setText("Conditionnement par défaut");
//			}
//			{
//				GridData cbID_REF_PRIXLData = new GridData();
//				cbID_REF_PRIXLData.widthHint = 22;
//				if(!decore) {
//					cbDefaut = new Button(paCorpsFormulaire, SWT.CHECK | SWT.LEFT);
//					cbDefaut.setLayoutData(cbID_REF_PRIXLData);
//				} else {					
//				fieldDefaut = new DecoratedField(paCorpsFormulaire, SWT.CHECK | SWT.LEFT, new ButtonControlCreator());
//				cbDefaut = (Button)fieldDefaut.getControl();
//				fieldDefaut.getLayoutControl().setLayoutData(cbID_REF_PRIXLData);
//				}
//			}

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

	public Label getLaLongueur() {
		return laLongueur;
	}

	public Label getLaLargeur() {
		return laLargeur;
	}

	public Text getTfLargeur() {
		return tfLargeur;
	}

	public Text getTfLongueur() {
		return tfLongueur;
	}

	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public Label getLaHauteur() {
		return laHauteur;
	}

	public Label getLaPoids() {
		return laPoids;
	}

	public Label getLaNbUnite() {
		return laNbUnite;
	}

//	public Label getLaReduction() {
//		return laReduction;
//	}

	public Text getTfHauteur() {
		return tfHauteur;
	}

	public Text getTfPoids() {
		return tfPoids;
	}

	public Text getTfNbUnite() {
		return tfNbUnite;
	}

//	public Text getTfReduction() {
//		return tfReduction;
//	}

	public DecoratedField getFieldLongueur() {
		return fieldLongueur;
	}

	public DecoratedField getFieldLargeur() {
		return fieldLargeur;
	}

	public DecoratedField getFieldHauteur() {
		return fieldHauteur;
	}

	public DecoratedField getFieldPoids() {
		return fieldPoids;
	}

	public DecoratedField getFieldNbUnite() {
		return fieldNbUnite;
	}

//	public DecoratedField getFieldReduction() {
//		return fieldReduction;
//	}
//
//	public Label getLaDefaut() {
//		return laDefaut;
//	}
//
//	public Button getCbDefaut() {
//		return cbDefaut;
//	}
//
//	public DecoratedField getFieldDefaut() {
//		return fieldDefaut;
//	}
//
//	public Label getLaCodeType() {
//		return laCodeType;
//	}
//
//	public Text getTfCodeType() {
//		return tfCodeType;
//	}
//
//	public DecoratedField getFieldCodeType() {
//		return fieldCodeType;
//	}

	public Label getLaCode() {
		return laCode;
	}

	public Label getLaLibelle() {
		return laLibelle;
	}

	public Text getTfCode() {
		return tfCode;
	}

	public Text getTfLibelle() {
		return tfLibelle;
	}

	public DecoratedField getFieldCode() {
		return fieldCode;
	}

	public DecoratedField getFieldLibelle() {
		return fieldLibelle;
	}

}

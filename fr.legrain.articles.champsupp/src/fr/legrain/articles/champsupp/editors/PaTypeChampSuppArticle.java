package fr.legrain.articles.champsupp.editors;

import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.fieldassist.TextControlCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
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
public class PaTypeChampSuppArticle extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

	private Composite paCorpsFormulaire;
	private Label laNom;
	private Label laType;
	private Label laDescription;
	private Label laDefaut;

	private Text tfNom;
	private Text tfType;
	private Text tfDescription;
	private Text tfDefaut;

	private DecoratedField fieldNom;
	private DecoratedField fieldType;
	private DecoratedField fieldDescription;
	private DecoratedField fieldDefaut;
	
	final private boolean decore = LgrMess.isDECORE();
//	final private boolean decore = false;

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
		PaTypeChampSuppArticle inst = new PaTypeChampSuppArticle(shell, SWT.NULL);
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

	public PaTypeChampSuppArticle(org.eclipse.swt.widgets.Composite parent, int style) {
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
					laNom = new Label(paCorpsFormulaire, SWT.NONE);
					laNom.setText("Nom");
				}

				{
					GridData tfNomLData = new GridData();
					tfNomLData.horizontalAlignment = GridData.CENTER;
					tfNomLData.widthHint = 177;
					if(!decore) {
						tfNom = new Text(paCorpsFormulaire, SWT.BORDER);
						tfNom.setLayoutData(tfNomLData);
					} else {					
						fieldNom = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfNom = (Text)fieldNom.getControl();
					fieldNom.getLayoutControl().setLayoutData(tfNomLData);
					}
				}
				{
					laType = new Label(paCorpsFormulaire, SWT.NONE);
					laType.setText("Type");
				}
				{
					GridData tfTypeLData = new GridData();
					tfTypeLData.horizontalAlignment = GridData.FILL;
					if(!decore) {
						tfType = new Text(paCorpsFormulaire, SWT.BORDER);
						tfType.setLayoutData(tfTypeLData);
					} else {					
					fieldType = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfType = (Text)fieldType.getControl();
					fieldType.getLayoutControl().setLayoutData(tfTypeLData);
					}
				}
				
				{
					laDescription = new Label(paCorpsFormulaire, SWT.NONE);
					laDescription.setText("Description");
				}
				{
					GridData tfDecriptionLData = new GridData();
					tfDecriptionLData.horizontalAlignment = GridData.FILL;
					if(!decore) {
						tfDescription = new Text(paCorpsFormulaire, SWT.BORDER);
						tfDescription.setLayoutData(tfDecriptionLData);
					} else {					
					fieldDescription = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfDescription = (Text)fieldDescription.getControl();
					fieldDescription.getLayoutControl().setLayoutData(tfDecriptionLData);
					}
				}
				
				{
					laDefaut = new Label(paCorpsFormulaire, SWT.NONE);
					laDefaut.setText("Valeur defaut");
				}
				{
					GridData tfDefautLData = new GridData();
					tfDefautLData.horizontalAlignment = GridData.FILL;
					if(!decore) {
						tfDefaut = new Text(paCorpsFormulaire, SWT.BORDER);
						tfDefaut.setLayoutData(tfDefautLData);
					} else {					
					fieldDefaut = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfDefaut = (Text)fieldDefaut.getControl();
					fieldDefaut.getLayoutControl().setLayoutData(tfDefautLData);
					}
				}
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

	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public Label getLaNom() {
		return laNom;
	}

	public void setLaNom(Label laNom) {
		this.laNom = laNom;
	}

	public Label getLaType() {
		return laType;
	}

	public void setLaType(Label laType) {
		this.laType = laType;
	}

	public Label getLaDescription() {
		return laDescription;
	}

	public void setLaDescription(Label laDescription) {
		this.laDescription = laDescription;
	}

	public Label getLaDefaut() {
		return laDefaut;
	}

	public void setLaDefaut(Label laDefaut) {
		this.laDefaut = laDefaut;
	}

	public Text getTfNom() {
		return tfNom;
	}

	public void setTfNom(Text tfNom) {
		this.tfNom = tfNom;
	}

	public Text getTfType() {
		return tfType;
	}

	public void setTfType(Text tfType) {
		this.tfType = tfType;
	}

	public Text getTfDescription() {
		return tfDescription;
	}

	public void setTfDescription(Text tfDescription) {
		this.tfDescription = tfDescription;
	}

	public Text getTfDefaut() {
		return tfDefaut;
	}

	public void setTfDefaut(Text tfDefaut) {
		this.tfDefaut = tfDefaut;
	}

	public DecoratedField getFieldNom() {
		return fieldNom;
	}

	public void setFieldNom(DecoratedField fieldNom) {
		this.fieldNom = fieldNom;
	}

	public DecoratedField getFieldType() {
		return fieldType;
	}

	public void setFieldType(DecoratedField fieldType) {
		this.fieldType = fieldType;
	}

	public DecoratedField getFieldDescription() {
		return fieldDescription;
	}

	public void setFieldDescription(DecoratedField fieldDescription) {
		this.fieldDescription = fieldDescription;
	}

	public DecoratedField getFieldDefaut() {
		return fieldDefaut;
	}

	public void setFieldDefaut(DecoratedField fieldDefaut) {
		this.fieldDefaut = fieldDefaut;
	}


}

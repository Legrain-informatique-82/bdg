package fr.legrain.articles.ecran;

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

import com.cloudgarden.resource.SWTResourceManager;

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
public class PaUniteSWT extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	private Composite paCorpsFormulaire;
	private Label laCODE_UNITE;
	private Label laLIBL_UNITE;
	private Label laCODE_FAMILLE;
	private Label laLongueur;
	private Label laLargeur;
	private Label laHauteur;
	private Label laPoids;
	private Label laNbUnite;


	private Text tfLIBL_UNITE;
	private Text tfCODE_UNITE;
	private Text tfCODE_FAMILLE;
	private Text tfLongueur;
	private Text tfLargeur;
	private Text tfHauteur;
	private Text tfPoids;
	private Text tfNbUnite;

	private DecoratedField fieldLIBL_UNITE;
	private DecoratedField fieldCODE_UNITE;
	private DecoratedField fieldCODE_FAMILLE;
	private DecoratedField fieldLongueur;
	private DecoratedField fieldLargeur;
	private DecoratedField fieldHauteur;
	private DecoratedField fieldPoids;
	private DecoratedField fieldNbUnite;


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
		PaUniteSWT inst = new PaUniteSWT(shell, SWT.NULL);
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

	public PaUniteSWT(org.eclipse.swt.widgets.Composite parent, int style) {
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
					tfCODE_UNITELData.horizontalAlignment = GridData.CENTER;
					tfCODE_UNITELData.widthHint = 177;
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
					laLIBL_UNITE = new Label(paCorpsFormulaire, SWT.NONE);
					laLIBL_UNITE.setText("Libellé");
				}
				{
					GridData tfLIBL_UNITELData = new GridData();
					tfLIBL_UNITELData.horizontalAlignment = GridData.FILL;
					if(!decore) {
						tfLIBL_UNITE = new Text(paCorpsFormulaire, SWT.BORDER);
						tfLIBL_UNITE.setLayoutData(tfLIBL_UNITELData);
					} else {					
					fieldLIBL_UNITE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfLIBL_UNITE = (Text)fieldLIBL_UNITE.getControl();
					fieldLIBL_UNITE.getLayoutControl().setLayoutData(tfLIBL_UNITELData);
					}
				}
				{
					laCODE_FAMILLE = new Label(paCorpsFormulaire, SWT.NONE);
					laCODE_FAMILLE.setText("Code famille");
				}

				{
					GridData tfCODE_FAMILLELData = new GridData();
					tfCODE_FAMILLELData.horizontalAlignment = GridData.CENTER;
					tfCODE_FAMILLELData.widthHint = 177;
					if(!decore) {
						tfCODE_FAMILLE = new Text(paCorpsFormulaire, SWT.BORDER);
						tfCODE_FAMILLE.setLayoutData(tfCODE_FAMILLELData);
					} else {					
					fieldCODE_FAMILLE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfCODE_FAMILLE = (Text)fieldCODE_FAMILLE.getControl();
					fieldCODE_FAMILLE.getLayoutControl().setLayoutData(tfCODE_FAMILLELData);
					}
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

			this.setLayout(new GridLayout());
			GridLayout compositeFormLayout = new GridLayout();
			compositeFormLayout.numColumns = 4;
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

	public Label getLaLongueur() {
		return laLongueur;
	}

	public Label getLaLargeur() {
		return laLargeur;
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

	public Text getTfLongueur() {
		return tfLongueur;
	}

	public Text getTfLargeur() {
		return tfLargeur;
	}

	public Text getTfHauteur() {
		return tfHauteur;
	}

	public Text getTfPoids() {
		return tfPoids;
	}

	public Text getTfNbUnite() {
		return tfNbUnite;
	}

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

	public Composite getPaCorpsFormulaire() {
		return paCorpsFormulaire;
	}

	public Label getLaLIBL_UNITE() {
		return laLIBL_UNITE;
	}

	public Label getLaCODE_UNITE() {
		return laCODE_UNITE;
	}

	public Text getTfLIBL_UNITE() {
		return tfLIBL_UNITE;
	}

	public Text getTfCODE_UNITE() {
		return tfCODE_UNITE;
	}

	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public DecoratedField getFieldLIBL_UNITE() {
		return fieldLIBL_UNITE;
	}

	public DecoratedField getFieldCODE_UNITE() {
		return fieldCODE_UNITE;
	}

	public Label getLaCODE_FAMILLE() {
		return laCODE_FAMILLE;
	}

	public Text getTfCODE_FAMILLE() {
		return tfCODE_FAMILLE;
	}

	public DecoratedField getFieldCODE_FAMILLE() {
		return fieldCODE_FAMILLE;
	}

	

}

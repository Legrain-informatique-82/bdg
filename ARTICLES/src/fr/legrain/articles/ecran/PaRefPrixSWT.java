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
public class PaRefPrixSWT extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	private Composite paCorpsFormulaire;
	private Label laCODE_ARTICLE;
	private Label laPRIX_PRIX;

	private Text tfCODE_ARTICLE;
	private Text tfPRIX_PRIX;

	private DecoratedField fieldPRIX_PRIX;
	private DecoratedField fieldCODE_ARTICLE;

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
		PaRefPrixSWT inst = new PaRefPrixSWT(shell, SWT.NULL);
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

	public PaRefPrixSWT(org.eclipse.swt.widgets.Composite parent, int style) {
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
					laCODE_ARTICLE = new Label(paCorpsFormulaire, SWT.NONE);
					laCODE_ARTICLE.setText("Code swtArticle");
				}

				{
					GridData tfPRIX_PRIXLData = new GridData();
					tfPRIX_PRIXLData.horizontalAlignment = GridData.CENTER;
					tfPRIX_PRIXLData.widthHint = 177;
					if(!decore) {
						tfPRIX_PRIX = new Text(paCorpsFormulaire, SWT.BORDER);
						tfPRIX_PRIX.setLayoutData(tfPRIX_PRIXLData);
					} else {					
					fieldCODE_ARTICLE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfPRIX_PRIX = (Text)fieldCODE_ARTICLE.getControl();
					fieldCODE_ARTICLE.getLayoutControl().setLayoutData(tfPRIX_PRIXLData);
					}
				}
				{
					laPRIX_PRIX = new Label(paCorpsFormulaire, SWT.NONE);
					laPRIX_PRIX.setText("Prix");
				}
				{
					GridData tfCODE_ARTICLELData = new GridData();
					tfCODE_ARTICLELData.horizontalAlignment = GridData.FILL;
					if(!decore) {
						tfCODE_ARTICLE = new Text(paCorpsFormulaire, SWT.BORDER);
						tfCODE_ARTICLE.setLayoutData(tfCODE_ARTICLELData);
					} else {					
					fieldPRIX_PRIX = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfCODE_ARTICLE = (Text)fieldPRIX_PRIX.getControl();
					fieldPRIX_PRIX.getLayoutControl().setLayoutData(tfCODE_ARTICLELData);
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

	public Label getLaPRIX_PRIX() {
		return laPRIX_PRIX;
	}

	public Label getLaCODE_ARTICLE() {
		return laCODE_ARTICLE;
	}

	public Text getTfCODE_ARTICLE() {
		return tfCODE_ARTICLE;
	}

	public Text getTfPRIX_PRIX() {
		return tfPRIX_PRIX;
	}

	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public DecoratedField getFieldPRIX_PRIX() {
		return fieldPRIX_PRIX;
	}

	public DecoratedField getFieldCODE_ARTICLE() {
		return fieldCODE_ARTICLE;
	}

}

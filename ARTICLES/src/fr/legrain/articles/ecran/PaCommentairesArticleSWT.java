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
public class PaCommentairesArticleSWT extends fr.legrain.lib.gui.DefaultFrameBoutonSWT {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	private Composite paCorpsFormulaire;
	
	private Text tfCOMMENTAIRE_ARTICLE;

	private DecoratedField fieldCOMMENTAIRE_ARTICLE;

	boolean decore = LgrMess.isDECORE();
	//boolean decore = false;
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
		PaCommentairesArticleSWT inst = new PaCommentairesArticleSWT(shell, SWT.NULL);
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

	public PaCommentairesArticleSWT(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {

			this.setLayout(new GridLayout());
			this.setSize(646, 521);

			GridData compositeFormLData = new GridData();
			compositeFormLData.grabExcessHorizontalSpace = true;
			compositeFormLData.verticalAlignment = GridData.FILL;
			compositeFormLData.horizontalAlignment = GridData.FILL;
			compositeFormLData.grabExcessVerticalSpace = true;
			GridData laMessageLData = new GridData();
			laMessageLData.grabExcessHorizontalSpace = true;
			laMessageLData.horizontalAlignment = GridData.FILL;
			GridLayout compositeFormLayout = new GridLayout();
			compositeFormLayout.makeColumnsEqualWidth = true;
			super.getCompositeForm().setLayoutData(compositeFormLData);
			super.getLaMessage().setLayoutData(laMessageLData);
			GridData paBtnLData = new GridData();
			paBtnLData.horizontalAlignment = GridData.CENTER;
			super.getCompositeForm().setLayout(compositeFormLayout);
			{
				paCorpsFormulaire = new Composite(super.getCompositeForm(), SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 6;
				GridData composite1LData = new GridData();
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.grabExcessVerticalSpace = true;
				composite1LData.horizontalAlignment = GridData.FILL;
				paCorpsFormulaire.setLayoutData(composite1LData);
				paCorpsFormulaire.setLayout(composite1Layout);
				{
					GridData tfCOMMENTAIRE_ARTICLELData = new GridData();
					tfCOMMENTAIRE_ARTICLELData.horizontalAlignment = GridData.FILL;
					tfCOMMENTAIRE_ARTICLELData.grabExcessHorizontalSpace = true;
					tfCOMMENTAIRE_ARTICLELData.grabExcessVerticalSpace = true;
					tfCOMMENTAIRE_ARTICLELData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfCOMMENTAIRE_ARTICLE = new Text(paCorpsFormulaire, SWT.MULTI | SWT.WRAP | SWT.H_SCROLL| SWT.V_SCROLL| SWT.BORDER);
						tfCOMMENTAIRE_ARTICLE.setLayoutData(tfCOMMENTAIRE_ARTICLELData);
					} else {					
						fieldCOMMENTAIRE_ARTICLE = new DecoratedField(paCorpsFormulaire, SWT.MULTI
								| SWT.WRAP
								| SWT.H_SCROLL
								| SWT.V_SCROLL
								| SWT.BORDER, new TextControlCreator());
						tfCOMMENTAIRE_ARTICLE = (Text)fieldCOMMENTAIRE_ARTICLE.getControl();
						fieldCOMMENTAIRE_ARTICLE.getLayoutControl().setLayoutData(tfCOMMENTAIRE_ARTICLELData);
					}
				}
			}
			super.getPaBtn().setLayoutData(paBtnLData);
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

	public Text getTfCOMMENTAIRE_ARTICLE() {
		return tfCOMMENTAIRE_ARTICLE;
	}

	public void setTfCOMMENTAIRE_ARTICLE(Text tfCOMMENTAIRE_ARTICLE) {
		this.tfCOMMENTAIRE_ARTICLE = tfCOMMENTAIRE_ARTICLE;
	}

	public DecoratedField getFieldCOMMENTAIRE_ARTICLE() {
		return fieldCOMMENTAIRE_ARTICLE;
	}

	public void setFieldCOMMENTAIRE_ARTICLE(DecoratedField fieldCOMMENTAIRE_ARTICLE) {
		this.fieldCOMMENTAIRE_ARTICLE = fieldCOMMENTAIRE_ARTICLE;
	}

}

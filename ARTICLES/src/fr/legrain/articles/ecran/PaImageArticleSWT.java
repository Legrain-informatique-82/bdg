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
public class PaImageArticleSWT extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	private Composite paCorpsFormulaire;
//	private Label laCODE_ARTICLE;
	private Label laCheminImageArticle;
	private Label laNomImageArticle;
	private Label laDefautImageArticle;

	private Text tfCheminImageArticle;
	private Text tfNomImageArticle;
	private Button cbDefautImageArticle;
	private Button btnBrowseImage;
	
	//private ScrolledComposite scPreview;
	private Canvas scPreview;
	
	private Group grpPreview;

	private DecoratedField fieldCheminImageArticle;
	private DecoratedField fieldNomImageArticle;
	private DecoratedField fieldID_REF_PRIX;

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
		PaImageArticleSWT inst = new PaImageArticleSWT(shell, SWT.NULL);
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

	public PaImageArticleSWT(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			{
				paCorpsFormulaire = new Composite(super.getPaFomulaire(), SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 3;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.grabExcessVerticalSpace = true;
				paCorpsFormulaire.setLayoutData(composite1LData);
				paCorpsFormulaire.setLayout(composite1Layout);
				{
					laCheminImageArticle = new Label(paCorpsFormulaire, SWT.NONE);
					laCheminImageArticle.setText("Chemin");
				}
				{
					GridData tfCheminImageLData = new GridData();
					tfCheminImageLData.horizontalAlignment = GridData.FILL;
					if(!decore) {
						tfCheminImageArticle = new Text(paCorpsFormulaire, SWT.BORDER);
						tfCheminImageArticle.setLayoutData(tfCheminImageLData);
					} else {					
					fieldCheminImageArticle = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfCheminImageArticle = (Text)fieldCheminImageArticle.getControl();
					fieldCheminImageArticle.getLayoutControl().setLayoutData(tfCheminImageLData);
					}
				}
				{
					btnBrowseImage = new Button(paCorpsFormulaire, SWT.PUSH);
					btnBrowseImage.setText("Parcourrir");
				}
				{
					laNomImageArticle = new Label(paCorpsFormulaire, SWT.NONE);
					laNomImageArticle.setText("Nom");
				}
				{
					GridData tfPRIXTTC_PRIXLData = new GridData();
					tfPRIXTTC_PRIXLData.horizontalAlignment = GridData.FILL;
					tfPRIXTTC_PRIXLData.horizontalSpan = 2;
					tfPRIXTTC_PRIXLData.widthHint = 177;
					if(!decore) {
						tfNomImageArticle = new Text(paCorpsFormulaire, SWT.BORDER);
						tfNomImageArticle.setLayoutData(tfPRIXTTC_PRIXLData);
					} else {					
					fieldNomImageArticle = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfNomImageArticle = (Text)fieldNomImageArticle.getControl();
					fieldNomImageArticle.getLayoutControl().setLayoutData(tfPRIXTTC_PRIXLData);
					}
				}
				{
					laDefautImageArticle = new Label(paCorpsFormulaire, SWT.NONE);
					laDefautImageArticle.setText("Image par défaut");
				}
				{
					GridData cbID_REF_PRIXLData = new GridData();
					cbID_REF_PRIXLData.widthHint = 22;
					cbID_REF_PRIXLData.horizontalSpan = 2;
					if(!decore) {
						cbDefautImageArticle = new Button(paCorpsFormulaire, SWT.CHECK | SWT.LEFT);
						cbDefautImageArticle.setLayoutData(cbID_REF_PRIXLData);
					} else {					
					fieldID_REF_PRIX = new DecoratedField(paCorpsFormulaire, SWT.CHECK | SWT.LEFT, new ButtonControlCreator());
					cbDefautImageArticle = (Button)fieldID_REF_PRIX.getControl();
					fieldID_REF_PRIX.getLayoutControl().setLayoutData(cbID_REF_PRIXLData);
					}
				}
				
				{
					GridData grpPreviewLData = new GridData();
					grpPreviewLData.horizontalSpan = 3;
					grpPreviewLData.heightHint=300;
					grpPreviewLData.widthHint=300;
					grpPreview = new Group(paCorpsFormulaire, SWT.NONE);
					grpPreview.setLayoutData(grpPreviewLData);
					grpPreview.setText("Aperçu");
					FillLayout fillLayout = new FillLayout();
					grpPreview.setLayout(fillLayout);
					
//					GridData scPreviewLData = new GridData();
//					scPreviewLData.horizontalSpan = 3;
//					//scPreview = new ScrolledComposite(paCorpsFormulaire,
//					scPreview = new Canvas(paCorpsFormulaire,
//							SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL 
//							| SWT.NO_BACKGROUND 
//							| SWT.NO_REDRAW_RESIZE);
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

	public Label getLaCheminImageArticle() {
		return laCheminImageArticle;
	}

	public Label getLaNomImageArticle() {
		return laNomImageArticle;
	}

	public Text getTfNomImageArticle() {
		return tfNomImageArticle;
	}

	public Text getTfCheminImageArticle() {
		return tfCheminImageArticle;
	}

	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public DecoratedField getFieldNomImageArticle() {
		return fieldNomImageArticle;
	}

	public DecoratedField getFieldCheminImageArticle() {
		return fieldCheminImageArticle;
	}

	public Button getCbDefautImageArticle() {
		return cbDefautImageArticle;
	}

	public DecoratedField getFieldID_REF_PRIX() {
		return fieldID_REF_PRIX;
	}

	public Label getLaDefautImageArticle() {
		return laDefautImageArticle;
	}

	public Button getBtnBrowseImage() {
		return btnBrowseImage;
	}

	//public ScrolledComposite getScPreview() {
	public Canvas getScPreview() {
		return scPreview;
	}

	public Group getGrpPreview() {
		return grpPreview;
	}

}

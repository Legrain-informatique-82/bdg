package fr.legrain.articles.ecran;

import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.fieldassist.TextControlCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
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
public class PaCatalogueWebSWT extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	private Composite paCorpsFormulaire;
	private Label laUrlRewriting;
	private Label laDescriptionLongue;
	private Label laPromotion;
	private Label laPromotionU2;
	private Label laNouveaute;
	private Label laExportation;
	private Label laExpediable;
	private Label laSpecial;

	private Text tfUrlRewriting;
	private Text tfPromotion;
	private Text tfPromotionU2;
	private Text tfDescriptionLongue;
	private Browser brDescriptionLongue;
	private Button cbExportation;
	private Button cbExpediable;
	private Button cbSpecial;
	
	private Button cbNouveaute;

	private DecoratedField fieldUrlRewriting;
	private DecoratedField fieldPromotion;
	private DecoratedField fieldPromotionU2;
	private DecoratedField fieldDescriptionLongue;
	private DecoratedField fieldNouveaute;
	private DecoratedField fieldExportation;
	private DecoratedField fieldExpediable;
	private DecoratedField fieldSpecial;

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
		PaCatalogueWebSWT inst = new PaCatalogueWebSWT(shell, SWT.NULL);
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

	public PaCatalogueWebSWT(org.eclipse.swt.widgets.Composite parent, int style) {
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
					laUrlRewriting = new Label(paCorpsFormulaire, SWT.NONE);
					laUrlRewriting.setText("URL Rewriting");
				}
				{
					GridData tfUrlRewritingLData = new GridData();
					tfUrlRewritingLData.horizontalAlignment = GridData.FILL;
					if(!decore) {
						tfUrlRewriting = new Text(paCorpsFormulaire, SWT.BORDER);
						tfUrlRewriting.setLayoutData(tfUrlRewritingLData);
					} else {					
					fieldUrlRewriting = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfUrlRewriting = (Text)fieldUrlRewriting.getControl();
					fieldUrlRewriting.getLayoutControl().setLayoutData(tfUrlRewritingLData);
					}
				}
				{
					laDescriptionLongue = new Label(paCorpsFormulaire, SWT.NONE);
					laDescriptionLongue.setText("Description longue (HTML)");
				}
				{
					GridData tfDescriptionLongueLData = new GridData();
					tfDescriptionLongueLData.horizontalAlignment = GridData.FILL;
					if(!decore) {
						tfDescriptionLongue = new Text(paCorpsFormulaire, SWT.BORDER);
						tfDescriptionLongue.setLayoutData(tfDescriptionLongueLData);
					} else {					
					fieldDescriptionLongue = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfDescriptionLongue = (Text)fieldDescriptionLongue.getControl();
					fieldDescriptionLongue.getLayoutControl().setLayoutData(tfDescriptionLongueLData);
					}
				}
				{
					GridData tfDescriptionLongueLData = new GridData();
					tfDescriptionLongueLData.horizontalAlignment = GridData.FILL;
					tfDescriptionLongueLData.horizontalSpan = 2;
					tfDescriptionLongueLData.heightHint = 250;
					tfDescriptionLongueLData.grabExcessHorizontalSpace = true;

					brDescriptionLongue = new Browser(paCorpsFormulaire, SWT.BORDER);
					brDescriptionLongue.setLayoutData(tfDescriptionLongueLData);
			
				}
				{
					laPromotion = new Label(paCorpsFormulaire, SWT.NONE);
					laPromotion.setText("Promotion");
				}
				{
					GridData tfPromotionLData = new GridData();
					if(!decore) {
						tfPromotion = new Text(paCorpsFormulaire, SWT.BORDER);
						tfPromotion.setLayoutData(tfPromotionLData);
					} else {					
					fieldPromotion = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfPromotion = (Text)fieldPromotion.getControl();
					fieldPromotion.getLayoutControl().setLayoutData(tfPromotionLData);
					}
				}
				
				{
					laPromotionU2 = new Label(paCorpsFormulaire, SWT.NONE);
					laPromotionU2.setText("Promotion U2");
				}
				{
					GridData tfPromotionLData = new GridData();
					if(!decore) {
						tfPromotionU2 = new Text(paCorpsFormulaire, SWT.BORDER);
						tfPromotionU2.setLayoutData(tfPromotionLData);
					} else {					
					fieldPromotionU2 = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfPromotionU2 = (Text)fieldPromotionU2.getControl();
					fieldPromotionU2.getLayoutControl().setLayoutData(tfPromotionLData);
					}
				}
				
				{
					laNouveaute = new Label(paCorpsFormulaire, SWT.NONE);
					laNouveaute.setText("Nouveauté");
				}
				{
					GridData cbNouveauteLData = new GridData();
					cbNouveauteLData.widthHint = 22;
					if(!decore) {
						cbNouveaute = new Button(paCorpsFormulaire, SWT.CHECK | SWT.LEFT);
						cbNouveaute.setLayoutData(cbNouveauteLData);
					} else {					
					fieldNouveaute = new DecoratedField(paCorpsFormulaire, SWT.CHECK | SWT.LEFT, new ButtonControlCreator());
					cbNouveaute = (Button)fieldNouveaute.getControl();
					fieldNouveaute.getLayoutControl().setLayoutData(cbNouveauteLData);
					}
				}
				
				{
					laExportation = new Label(paCorpsFormulaire, SWT.NONE);
					laExportation.setText("Exportation");
				}
				{
					GridData cbExportationLData = new GridData();
					cbExportationLData.widthHint = 22;
					if(!decore) {
						cbExportation = new Button(paCorpsFormulaire, SWT.CHECK | SWT.LEFT);
						cbExportation.setLayoutData(cbExportationLData);
					} else {					
					fieldExportation = new DecoratedField(paCorpsFormulaire, SWT.CHECK | SWT.LEFT, new ButtonControlCreator());
					cbExportation = (Button)fieldExportation.getControl();
					fieldExportation.getLayoutControl().setLayoutData(cbExportationLData);
					}
				}
				
				{
					laExpediable = new Label(paCorpsFormulaire, SWT.NONE);
					laExpediable.setText("Expédiable");
				}
				{
					GridData cbExportationLData = new GridData();
					cbExportationLData.widthHint = 22;
					if(!decore) {
						cbExpediable = new Button(paCorpsFormulaire, SWT.CHECK | SWT.LEFT);
						cbExpediable.setLayoutData(cbExportationLData);
					} else {					
					fieldExpediable = new DecoratedField(paCorpsFormulaire, SWT.CHECK | SWT.LEFT, new ButtonControlCreator());
					cbExpediable = (Button)fieldExpediable.getControl();
					fieldExpediable.getLayoutControl().setLayoutData(cbExportationLData);
					}
				}
				
				{
					laSpecial = new Label(paCorpsFormulaire, SWT.NONE);
					laSpecial.setText("Article spécial");
				}
				{
					GridData cbExportationLData = new GridData();
					cbExportationLData.widthHint = 22;
					if(!decore) {
						cbSpecial = new Button(paCorpsFormulaire, SWT.CHECK | SWT.LEFT);
						cbSpecial.setLayoutData(cbExportationLData);
					} else {					
					fieldSpecial = new DecoratedField(paCorpsFormulaire, SWT.CHECK | SWT.LEFT, new ButtonControlCreator());
					cbSpecial = (Button)fieldSpecial.getControl();
					fieldSpecial.getLayoutControl().setLayoutData(cbExportationLData);
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
			paFomulaireLData.horizontalAlignment = GridData.FILL;
			paFomulaireLData.grabExcessVerticalSpace = true;
			paFomulaireLData.grabExcessHorizontalSpace = true;
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

	public Label getLaPromotion() {
		return laPromotion;
	}

	public Label getLaUrlRewriting() {
		return laUrlRewriting;
	}

//	public Label getLaCODE_ARTICLE() {
//		return laCODE_ARTICLE;
//	}

	public Label getLaDescriptionLongue() {
		return laDescriptionLongue;
	}

	public Text getTfPromotion() {
		return tfPromotion;
	}

	public Text getTfUrlRewriting() {
		return tfUrlRewriting;
	}

	public Text getTfDescriptionLongue() {
		return tfDescriptionLongue;
	}

//	public Text getTfCODE_ARTICLE() {
//		return tfCODE_ARTICLE;
//	}

	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public DecoratedField getFieldPromotion() {
		return fieldPromotion;
	}

	public DecoratedField getFieldUrlRewriting() {
		return fieldUrlRewriting;
	}

	public DecoratedField getFieldDescriptionLongue() {
		return fieldDescriptionLongue;
	}

//	public DecoratedField getFieldCODE_ARTICLE() {
//		return fieldCODE_ARTICLE;
//	}

	public Button getCbNouveaute() {
		return cbNouveaute;
	}

	public DecoratedField getFieldNouveaute() {
		return fieldNouveaute;
	}

	public Label getLaNouveaute() {
		return laNouveaute;
	}

	public Label getLaExportation() {
		return laExportation;
	}

	public Button getCbExportation() {
		return cbExportation;
	}

	public DecoratedField getFieldExportation() {
		return fieldExportation;
	}

	public Browser getBrDescriptionLongue() {
		return brDescriptionLongue;
	}

	public Label getLaPromotionU2() {
		return laPromotionU2;
	}

	public Label getLaExpediable() {
		return laExpediable;
	}

	public Label getLaSpecial() {
		return laSpecial;
	}

	public Text getTfPromotionU2() {
		return tfPromotionU2;
	}

	public Button getCbExpediable() {
		return cbExpediable;
	}

	public Button getCbSpecial() {
		return cbSpecial;
	}

	public DecoratedField getFieldPromotionU2() {
		return fieldPromotionU2;
	}

	public DecoratedField getFieldExpediable() {
		return fieldExpediable;
	}

	public DecoratedField getFieldSpecial() {
		return fieldSpecial;
	}

}

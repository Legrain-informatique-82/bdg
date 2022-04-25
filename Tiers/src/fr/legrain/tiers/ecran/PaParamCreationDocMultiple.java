package fr.legrain.tiers.ecran;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.resource.SWTResourceManager;

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
public class PaParamCreationDocMultiple extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT  {
	private Composite paCorpsFormulaire;
	private Label label1;
	private Button btn1semaine;
	private Button btnAppliquer;
//	private Label label2;
//	private Label label3;
	private Text tfJours;
	private Button btnXjours;
	private Button btn1mois;
	private Button btnDecad;


	private Button btn2semaines;
	private Button btnDocument;
	private Button btnTiers;
	private Table tableTypeDoc;
	private Label laCodeParam;
	private Label laTitreGrille;
	private Text tfCodeParam;
	
	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
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
		PaParamCreationDocMultiple inst = new PaParamCreationDocMultiple(shell, SWT.NULL);
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


	public PaParamCreationDocMultiple(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
		// TODO Auto-generated constructor stub
	}
	
	private void initGUI() {
		try {
			{
				paCorpsFormulaire = new Composite(super.getPaFomulaire(), SWT.NONE);
				GridLayout paCorpsFormulaireLayout = new GridLayout();
				paCorpsFormulaireLayout.numColumns = 2;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.grabExcessVerticalSpace = true;
				paCorpsFormulaire.setLayoutData(composite1LData);
				paCorpsFormulaire.setLayout(paCorpsFormulaireLayout);

				{
					laCodeParam = new Label(paCorpsFormulaire, SWT.NONE);
					laCodeParam.setText("Code paramètre");
					GridData label1LData = new GridData();
					label1LData.horizontalSpan = 1;
					laCodeParam.setLayoutData(label1LData);
				}
				{
					tfCodeParam = new Text(paCorpsFormulaire, SWT.BORDER);
					GridData tfJoursLData = new GridData();
					tfJoursLData.horizontalSpan = 1;
					tfJoursLData.widthHint = 300;
					tfJoursLData.heightHint = 14;
					//tfJoursLData.horizontalAlignment = GridData.FILL;
					tfCodeParam.setLayoutData(tfJoursLData);
				}
				{
					label1 = new Label(paCorpsFormulaire, SWT.NONE);
					label1.setText("1 document créé :");
					GridData label1LData = new GridData();
					label1LData.horizontalSpan = 2;
					label1LData.widthHint = 307;
					label1LData.heightHint = 18;
					label1.setLayoutData(label1LData);
				}

				{
					btnTiers = new Button(paCorpsFormulaire, SWT.RADIO | SWT.LEFT);
					GridData btnTiersLData = new GridData();
					btnTiersLData.horizontalIndent = 20;
					btnTiersLData.horizontalSpan = 1;
					btnTiers.setLayoutData(btnTiersLData);
					btnTiers.setText("par tiers");
				}
				{
					btnDocument = new Button(paCorpsFormulaire, SWT.RADIO | SWT.LEFT);
					GridData btnDocumentLData = new GridData();
					btnDocument.setLayoutData(btnDocumentLData);
					btnDocument.setText("par document");
				}
				{
					btn1semaine = new Button(paCorpsFormulaire, SWT.RADIO | SWT.LEFT);
					GridData btn1semaineLData = new GridData();
					btn1semaineLData.horizontalIndent = 20;
					btn1semaine.setLayoutData(btn1semaineLData);
					btn1semaine.setText("par semaine");
				}
//				{
//					label2 = new Label(paCorpsFormulaire, SWT.NONE);
//					GridData label2LData = new GridData();
//					label2LData.horizontalAlignment = GridData.CENTER;
//					label2LData.widthHint = 42;
//					label2LData.heightHint = 14;
//					label2.setLayoutData(label2LData);
//					label2.setText("ou");
//				}
				{
					btn2semaines = new Button(paCorpsFormulaire, SWT.RADIO | SWT.LEFT);
					GridData btn2semainesLData = new GridData();
					btn2semaines.setLayoutData(btn2semainesLData);
					btn2semaines.setText("par quinzaine");
				}
				{
					btnDecad = new Button(paCorpsFormulaire, SWT.RADIO | SWT.LEFT);
					GridData btn1moisLData = new GridData();
					btn1moisLData.horizontalIndent = 20;
//					btn1moisLData.horizontalSpan = 1;
					btnDecad.setLayoutData(btn1moisLData);
					btnDecad.setText("par décade");
				}
//				{
//					label3 = new Label(paCorpsFormulaire, SWT.NONE);
//					GridData label2LData = new GridData();
//					label2LData.horizontalAlignment = GridData.CENTER;
//					label2LData.widthHint = 42;
//					label2LData.heightHint = 14;
//					label3.setLayoutData(label2LData);
//					label3.setText("ou");
//				}
				{
					btn1mois = new Button(paCorpsFormulaire, SWT.RADIO | SWT.LEFT);
//					btn1moisLData.horizontalSpan = 3;
					btn1mois.setText("par mois");
				}
				{
					btnXjours = new Button(paCorpsFormulaire, SWT.RADIO | SWT.LEFT);
					GridData btnXjoursLData = new GridData();
					btnXjoursLData.horizontalIndent = 20;
					btnXjoursLData.horizontalSpan = 1;
					btnXjours.setLayoutData(btnXjoursLData);
					btnXjours.setText("pour X jour(s) de document");
				}
				{
					tfJours = new Text(paCorpsFormulaire, SWT.BORDER);
					GridData tfJoursLData = new GridData();
					tfJoursLData.widthHint = 40;
					tfJoursLData.heightHint = 14;
					tfJours.setLayoutData(tfJoursLData);
					tfJours.setText("1");
				}
				{
					btnAppliquer = new Button(paCorpsFormulaire, SWT.CHECK | SWT.LEFT);
					GridData btnAppliquerLData = new GridData();
					btnAppliquerLData.horizontalSpan = 2;
					btnAppliquerLData.grabExcessHorizontalSpace = true;
					btnAppliquer.setLayoutData(btnAppliquerLData);
					btnAppliquer.setText("Ignorer les paramètrages particuliers");
					btnAppliquer.setVisible(false);
				}
				{
					laTitreGrille = new Label(paCorpsFormulaire, SWT.NONE);
					laTitreGrille.setText("Cochez ci-dessous les types de documents à regrouper, concernés par ce paramètre");
					//laTitreGrille.setFont(SWTResourceManager.getFont("Segoe UI", 9, 1, false, false));
					//laTitreGrille.setFont()
					GridData label1LData = new GridData();
					label1LData.horizontalSpan = 1;
					laTitreGrille.setLayoutData(label1LData);
				}
				{

					GridData grilleLData = new GridData();
					grilleLData.horizontalAlignment = GridData.FILL;
					grilleLData.verticalAlignment = GridData.FILL;
					grilleLData.grabExcessVerticalSpace = true;
					grilleLData.grabExcessHorizontalSpace = true;
					grilleLData.horizontalSpan = 4;
//					if(tableStyle==SWT.NULL) {
						tableTypeDoc = new Table(paCorpsFormulaire, SWT.SINGLE
								| SWT.FULL_SELECTION
								| SWT.H_SCROLL
								| SWT.V_SCROLL
								| SWT.BORDER
								| SWT.CHECK);
//					} else {
//						tableTypeDoc = new Table(tableTypeDoc, tableStyle);
//					}
					tableTypeDoc.setLayoutData(grilleLData);
					tableTypeDoc.setHeaderVisible(true);
					tableTypeDoc.setLinesVisible(true);

				}
			}
			this.setLayout(new GridLayout());
			GridLayout compositeFormLayout = new GridLayout();
			compositeFormLayout.numColumns = 2;
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
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	private void initGUI() {
//		try {
//			{
//				paCorpsFormulaire = new Composite(super.getPaFomulaire(), SWT.NONE);
//				GridLayout paCorpsFormulaireLayout = new GridLayout();
//				paCorpsFormulaireLayout.numColumns = 3;
//				GridData composite1LData = new GridData();
//				composite1LData.horizontalAlignment = GridData.FILL;
//				composite1LData.grabExcessHorizontalSpace = true;
//				composite1LData.verticalAlignment = GridData.FILL;
//				composite1LData.grabExcessVerticalSpace = true;
//				paCorpsFormulaire.setLayoutData(composite1LData);
//				paCorpsFormulaire.setLayout(paCorpsFormulaireLayout);
//					
////				GridLayout thisLayout = new GridLayout();
////				thisLayout.numColumns = 3;
////				this.setLayout(thisLayout);
////				this.setSize(347, 138);
//				{
//					label1 = new Label(paCorpsFormulaire, SWT.NONE);
//					label1.setText("1 document créé par :");
//					GridData label1LData = new GridData();
//					label1LData.horizontalSpan = GridData.FILL;
//					label1.setLayoutData(label1LData);
//				}
//				{
//					btnTiers = new Button(paCorpsFormulaire, SWT.RADIO | SWT.LEFT);
//					GridData btnTiersLData = new GridData();
////					btnTiersLData.horizontalIndent = 20;
//					btnTiersLData.horizontalSpan = GridData.FILL;
//					btnTiers.setLayoutData(btnTiersLData);
//					btnTiers.setText("tiers");
//				}
//				{
//					btnDocument = new Button(paCorpsFormulaire, SWT.RADIO | SWT.LEFT);
//					GridData btnDocumentLData = new GridData();
//					btnDocumentLData.horizontalSpan = GridData.FILL;
//					btnDocument.setLayoutData(btnDocumentLData);
//					btnDocument.setText("document");
//				}
//				{
//					btn1semaine = new Button(paCorpsFormulaire, SWT.RADIO | SWT.LEFT);
//					GridData btn1semaineLData = new GridData();
////					btn1semaineLData.horizontalIndent = 20;
//					btn1semaineLData.horizontalSpan = GridData.FILL;
//					btn1semaine.setLayoutData(btn1semaineLData);
//					btn1semaine.setText("1");
//				}
//				{
//					label2 = new Label(paCorpsFormulaire, SWT.NONE);
//					GridData label2LData = new GridData();
//					label2LData.horizontalSpan = GridData.FILL;
//					label2LData.horizontalAlignment = GridData.CENTER;
//					label2.setLayoutData(label2LData);
//					label2.setText("ou");
//				}
//				{
//					btn2semaines = new Button(paCorpsFormulaire, SWT.RADIO | SWT.LEFT);
//					GridData btn2semainesLData = new GridData();
//					btn2semaines.setLayoutData(btn2semainesLData);
//					btn2semainesLData.horizontalSpan = GridData.FILL;
//					btn2semaines.setText("2 semaines de document");
//				}
//				{
//					btn1mois = new Button(paCorpsFormulaire, SWT.RADIO | SWT.LEFT);
//					GridData btn1moisLData = new GridData();
////					btn1moisLData.horizontalIndent = 20;
//					btn1moisLData.horizontalSpan =  GridData.FILL;
//					btn1mois.setLayoutData(btn1moisLData);
//					btn1mois.setText("1 mois de document");
//				}
//				{
//					btnXjours = new Button(paCorpsFormulaire, SWT.RADIO | SWT.LEFT);
//					GridData btnXjoursLData = new GridData();
////					btnXjoursLData.horizontalIndent = 20;
//					btnXjoursLData.horizontalSpan =  GridData.FILL;
//					btnXjours.setLayoutData(btnXjoursLData);
//					btnXjours.setText("X jour(s) de document");
//				}
//				{
//					tfJours = new Text(paCorpsFormulaire, SWT.BORDER);
//					GridData tfJoursLData = new GridData();
//					tfJoursLData.horizontalSpan =  GridData.FILL;
//					tfJours.setLayoutData(tfJoursLData);
//					tfJours.setText("1");
//				}
//				{
//					btnAppliquer = new Button(paCorpsFormulaire, SWT.CHECK | SWT.LEFT);
//					GridData btnAppliquerLData = new GridData();
//					btnAppliquerLData.horizontalSpan = GridData.FILL;
////					btnAppliquerLData.grabExcessHorizontalSpace = true;
//					btnAppliquer.setLayoutData(btnAppliquerLData);
//					btnAppliquer.setText("Appliquer à tous les tiers");
//					btnAppliquer.setVisible(false);
//				}
//			}
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	public Label getLabel1() {
		return label1;
	}
	
	public Button getBtnTiers() {
		return btnTiers;
	}
	
	public Button getBtnDocument() {
		return btnDocument;
	}
	
	public Button getBtn1semaine() {
		return btn1semaine;
	}
	
	public Button getBtn2semaines() {
		return btn2semaines;
	}
	
	public Button getBtn1mois() {
		return btn1mois;
	}
	
	public Button getBtnXjours() {
		return btnXjours;
	}
	
	public Text getTfJours() {
		return tfJours;
	}
	
	public Button getBtnAppliquer() {
		return btnAppliquer;
	}
	
//	public Label getLabel2() {
//		return label2;
//	}

	public Composite getPaCorpsFormulaire() {
		return paCorpsFormulaire;
	}

	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public Table getTableTypeDoc() {
		return tableTypeDoc;
	}

	public Label getLaCodeParam() {
		return laCodeParam;
	}

	public Text getTfCodeParam() {
		return tfCodeParam;
	}
	public Button getBtnDecad() {
		return btnDecad;
	}
}

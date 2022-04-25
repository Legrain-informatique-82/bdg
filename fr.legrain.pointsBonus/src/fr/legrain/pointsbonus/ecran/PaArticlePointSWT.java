package fr.legrain.pointsbonus.ecran;
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
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.resource.SWTResourceManager;

import fr.legrain.lib.gui.fieldassist.DateTimeControlCreator;
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
public class PaArticlePointSWT extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
	

	private Composite paCorpsFormulaire;
	
	private Label laCodeArticle;
	private Label laPoint;
	private Label laTypeMouvement;
	private Label laDateDebutPeriode;
	private Label laDateFinPeriode;
	private Label laLibelleArticle;
	private Label laIndice;
	private Label laPrixReference;

	private Text tfPoint;
	private Combo tfTypeMouvement;
	private DateTime tfDateDebutPeriode;
	private DateTime tfDateFinPeriode;
	private Text tfIndice;
	private Text tfPrixReference;
	
	private Text tfLibelleArticle;
	

	private Button btnArticle;
	private Group groupArticle;
	private Group groupPoint;
//	private Group groupCommentaire;
	
	private DecoratedField fieldPoint;
	private DecoratedField fieldTypeMouvement;
	private DecoratedField fieldDateDebutPeriode;
	private DecoratedField fieldDateFinPeriode;
	private DecoratedField fieldIndice;
	private DecoratedField fieldPrixReference;
	
	private DecoratedField fieldLibelleArticle;
	
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
		PaArticlePointSWT inst = new PaArticlePointSWT(shell, SWT.NULL);
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




	public PaArticlePointSWT(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
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
					groupArticle = new Group(paCorpsFormulaire, SWT.NONE);
					GridLayout groupSupportLayout = new GridLayout();
					groupSupportLayout.numColumns = 2;
					groupArticle.setLayout(groupSupportLayout);
					GridData groupSuppportLData = new GridData();
					groupSuppportLData.horizontalAlignment = GridData.FILL;
					groupSuppportLData.verticalAlignment = GridData.FILL;
					groupArticle.setLayoutData(groupSuppportLData);
					groupArticle.setText("Infos Article");
					{
						laCodeArticle = new Label(groupArticle, SWT.NONE);
						GridData laCodeTiersLData = new GridData();
						laCodeTiersLData.horizontalSpan = 2;
						laCodeTiersLData.horizontalAlignment = GridData.FILL;
						laCodeArticle.setLayoutData(laCodeTiersLData);
						laCodeArticle.setText("Code article :");
						laCodeArticle.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
					}
					{
						laLibelleArticle = new Label(groupArticle, SWT.NONE);
						laLibelleArticle.setText("Libellé article");
					}
					{
						GridData tfGroupeLData = new GridData();
						tfGroupeLData.horizontalAlignment = GridData.CENTER;
						tfGroupeLData.verticalAlignment = GridData.FILL;
						tfGroupeLData.grabExcessHorizontalSpace = true;
						tfGroupeLData.widthHint = 314;
						if(!decore) {
							tfLibelleArticle = new Text(groupArticle, SWT.BORDER);
							tfLibelleArticle.setLayoutData(tfGroupeLData);
							tfLibelleArticle.setEditable(false);
						} else {					
							fieldLibelleArticle = new DecoratedField(groupArticle, SWT.BORDER, new TextControlCreator());
							tfLibelleArticle = (Text)fieldLibelleArticle.getControl();
							fieldLibelleArticle.getLayoutControl().setLayoutData(tfGroupeLData);
						}
					}

					{
						btnArticle = new Button(groupArticle, SWT.PUSH | SWT.CENTER);
						GridData btnTiersLData = new GridData();
						btnTiersLData.horizontalSpan = 2;
						btnArticle.setLayoutData(btnTiersLData);
						btnArticle.setText("Fiche Article");
					}
				}
				{
					groupPoint = new Group(paCorpsFormulaire, SWT.NONE);
					GridLayout groupSupportLayout = new GridLayout();
					groupSupportLayout.numColumns = 2;
					groupPoint.setLayout(groupSupportLayout);
					GridData groupSuppportLData = new GridData();
					groupSuppportLData.verticalAlignment = GridData.FILL;
					groupSuppportLData.horizontalAlignment = GridData.FILL;
					groupSuppportLData.grabExcessHorizontalSpace = true;
					groupPoint.setLayoutData(groupSuppportLData);
					groupPoint.setText("Point Bonus");

					{
						laPoint = new Label(groupPoint, SWT.NONE);
						laPoint.setText("Point");
					}
					{
						GridData tfCodeSupportLData = new GridData();
						tfCodeSupportLData.widthHint = 112;
						tfCodeSupportLData.verticalAlignment = GridData.FILL;
						if(!decore) {
							tfPoint= new Text(groupPoint, SWT.BORDER);
							tfPoint.setLayoutData(tfCodeSupportLData);
						} else {					
							fieldPoint = new DecoratedField(groupPoint, SWT.BORDER, new TextControlCreator());
							tfPoint=(Text)fieldPoint.getControl();
							fieldPoint.getLayoutControl().setLayoutData(tfCodeSupportLData);
						}
					}
					{
						laTypeMouvement = new Label(groupPoint, SWT.NONE);
						laTypeMouvement.setText("Type Mouvement");
					}
					{
						GridData tfCodeSupportLData = new GridData();
						tfCodeSupportLData.verticalAlignment = GridData.FILL;
						tfTypeMouvement = new Combo(groupPoint, SWT.BORDER);
						tfTypeMouvement.setLayoutData(tfCodeSupportLData);
					}
					{
						laDateDebutPeriode = new Label(groupPoint, SWT.NONE);
						laDateDebutPeriode.setText("Date Début période");
					}
					{
						GridData dateTimeDATELData = new GridData();
						dateTimeDATELData.widthHint = 90;
						dateTimeDATELData.verticalAlignment = GridData.FILL;

						if(!decore) {
							tfDateDebutPeriode = new DateTime(groupPoint, SWT.BORDER | SWT.DROP_DOWN);
							tfDateDebutPeriode.setLayoutData(dateTimeDATELData);
						} else {
							fieldDateDebutPeriode = new DecoratedField(groupPoint, SWT.BORDER | SWT.DROP_DOWN, new  DateTimeControlCreator());
							tfDateDebutPeriode = (DateTime)fieldDateDebutPeriode.getControl();
							fieldDateDebutPeriode.getLayoutControl().setLayoutData(dateTimeDATELData);
						}
					}
					{
						laDateFinPeriode = new Label(groupPoint, SWT.NONE);
						laDateFinPeriode.setText("Date fin période");
					}
					{
						GridData dateTimeDATELData = new GridData();
						dateTimeDATELData.widthHint = 90;
						dateTimeDATELData.verticalAlignment = GridData.FILL;

						if(!decore) {
							tfDateFinPeriode = new DateTime(groupPoint, SWT.BORDER | SWT.DROP_DOWN);
							tfDateFinPeriode.setLayoutData(dateTimeDATELData);
						} else {
							fieldDateFinPeriode = new DecoratedField(groupPoint, SWT.BORDER | SWT.DROP_DOWN, new  DateTimeControlCreator());
							tfDateFinPeriode = (DateTime)fieldDateFinPeriode.getControl();
							fieldDateFinPeriode.getLayoutControl().setLayoutData(dateTimeDATELData);
						}
					}
					{
						laIndice = new Label(groupPoint, SWT.NONE);
						laIndice.setText("Indice");
					}
					{
						GridData tfCodeSupportLData = new GridData();
						tfCodeSupportLData.widthHint = 112;
						tfCodeSupportLData.verticalAlignment = GridData.FILL;
						if(!decore) {
							tfIndice= new Text(groupPoint, SWT.BORDER);
							tfIndice.setLayoutData(tfCodeSupportLData);
						} else {					
							fieldIndice = new DecoratedField(groupPoint, SWT.BORDER, new TextControlCreator());
							tfIndice=(Text)fieldIndice.getControl();
							fieldIndice.getLayoutControl().setLayoutData(tfCodeSupportLData);
						}
					}
					{
						laPrixReference = new Label(groupPoint, SWT.NONE);
						laPrixReference.setText("Prix de référence");
					}
					{
						GridData tfCodeSupportLData = new GridData();
						tfCodeSupportLData.widthHint = 112;
						tfCodeSupportLData.verticalAlignment = GridData.FILL;
						if(!decore) {
							tfPrixReference= new Text(groupPoint, SWT.BORDER);
							tfPrixReference.setLayoutData(tfCodeSupportLData);
						} else {					
							fieldPrixReference = new DecoratedField(groupPoint, SWT.BORDER, new TextControlCreator());
							tfPrixReference=(Text)fieldPrixReference.getControl();
							fieldPrixReference.getLayoutControl().setLayoutData(tfCodeSupportLData);
						}
					}		
				}			
			}


			this.setLayout(new GridLayout());
			GridLayout compositeFormLayout = new GridLayout();
			compositeFormLayout.numColumns = 2;
			this.setSize(978, 499);
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
			paFomulaireLData.grabExcessHorizontalSpace = true;
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

	public Label getLaPoint() {
		return laPoint;
	}


	public DecoratedField getFieldPoint() {
		return fieldPoint;
	}

	public Label getLaTypeMouvement() {
		return laTypeMouvement;
	}

	public Label getLaDateDebutPeriode() {
		return laDateDebutPeriode;
	}


	public Combo getTfTypeMouvement() {
		return tfTypeMouvement;
	}

	public DateTime getTfDateDebutPeriode() {
		return tfDateDebutPeriode;
	}



	public Text getTfIndice() {
		return tfIndice;
	}


	public Group getGroupPoint() {
		return groupPoint;
	}


	public DecoratedField getFieldTypeMouvement() {
		return fieldTypeMouvement;
	}

	public DecoratedField getFieldDateDebutPeriode() {
		return fieldDateDebutPeriode;
	}
	

	public DecoratedField getFieldIndice() {
		return fieldIndice;
	}

	

	public Label getLaCodeArticle() {
		return laCodeArticle;
	}

	
	public Group getGroupArticle() {
		return groupArticle;
	}

	public Text getTfLibelleArticle() {
		return tfLibelleArticle;
	}


	public Label getLaLibelleArticle() {
		return laLibelleArticle;
	}

	public DecoratedField getFieldLibelleArticle() {
		return fieldLibelleArticle;
	}


	
	public Button getBtnArticle() {
		return btnArticle;
	}

	public Text getTfPoint() {
		return tfPoint;
	}

	public Label getLaDateFinPeriode() {
		return laDateFinPeriode;
	}

	public Label getLaIndice() {
		return laIndice;
	}

	public Label getLaPrixReference() {
		return laPrixReference;
	}

	public DateTime getTfDateFinPeriode() {
		return tfDateFinPeriode;
	}

	public Text getTfPrixReference() {
		return tfPrixReference;
	}

	public DecoratedField getFieldDateFinPeriode() {
		return fieldDateFinPeriode;
	}

	public DecoratedField getFieldPrixReference() {
		return fieldPrixReference;
	}


}

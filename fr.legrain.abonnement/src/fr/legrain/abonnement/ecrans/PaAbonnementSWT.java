package fr.legrain.abonnement.ecrans;
import com.cloudgarden.resource.SWTResourceManager;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.fieldassist.TextControlCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

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
public class PaAbonnementSWT extends fr.legrain.lib.gui.DefaultFrameFormulaireSWTSansGrille {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
	
	
//	{
//		//Register as a resource user - SWTResourceManager will
//		//handle the obtaining and disposing of resources
//		SWTResourceManager.registerResourceUser(this);
//	}

	private Composite paCorpsFormulaire;
	
	private Label laCodeTiers;
//	private Label laCodeAbonnement;
	private Label laCodeArticle;
	private Label laDateDebAbon;
	private Label laDateFinAbon;

//	private Text tfCodeAbonnement;
	private Text tfCodeArticle;
	private Text tfCommentaire;
	private DateTime tfDateDebAbon;
	private DateTime tfDateFinAbon;

	private Group groupSupport;
	private Text tfDocument;
	private Label laDocument;
	private Group groupDocument;
	private Label laHorsAbon;
	private Group groupComplement;
	private Group groupAbonnement;
	private Group groupCommentaire;
	
//	private DecoratedField fieldCodeAbonnement;
	private DecoratedField fieldCodeArticle;
	private DecoratedField fieldCommentaire;
	private DecoratedField fieldDateDebAbon;
	private DecoratedField fieldDateFinAbon;
	
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
		PaAbonnementSWT inst = new PaAbonnementSWT(shell, SWT.NULL);
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




	public PaAbonnementSWT(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			{
				paCorpsFormulaire = new Composite(super.getPaFomulaire(), SWT.NONE);
				GridLayout paCorpsFormulaireLayout = new GridLayout();
				paCorpsFormulaireLayout.numColumns = 4;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.grabExcessVerticalSpace = true;
				paCorpsFormulaire.setLayoutData(composite1LData);
				paCorpsFormulaire.setLayout(paCorpsFormulaireLayout);

				{
					groupSupport = new Group(paCorpsFormulaire, SWT.NONE);
					GridLayout groupSupportLayout = new GridLayout();
					groupSupportLayout.numColumns = 2;
					groupSupport.setLayout(groupSupportLayout);
					GridData groupSuppportLData = new GridData();
					groupSuppportLData.verticalAlignment = GridData.FILL;
					groupSuppportLData.widthHint = 224;
					groupSupport.setLayoutData(groupSuppportLData);
					groupSupport.setText("Licence");
					{
						laCodeTiers = new Label(groupSupport, SWT.NONE);
						GridData laCodeTiersLData = new GridData();
						laCodeTiersLData.horizontalSpan = 2;
						laCodeTiersLData.horizontalAlignment = GridData.FILL;
						laCodeTiers.setLayoutData(laCodeTiersLData);
						laCodeTiers.setText("Code tiers :");
						laCodeTiers.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
					}
//					{
//						laCodeAbonnement = new Label(groupSupport, SWT.NONE);
//						laCodeAbonnement.setText("Code");
//					}
//					{
//						GridData tfCodeSupportLData = new GridData();
//						tfCodeSupportLData.widthHint = 112;
//						tfCodeSupportLData.verticalAlignment = GridData.FILL;
//						if(!decore) {
//							tfCodeAbonnement = new Text(groupSupport, SWT.BORDER);
//							tfCodeAbonnement.setLayoutData(tfCodeSupportLData);
//						} else {					
//							fieldCodeAbonnement = new DecoratedField(groupSupport, SWT.BORDER, new TextControlCreator());
//							tfCodeAbonnement = (Text)fieldCodeAbonnement.getControl();
//							fieldCodeAbonnement.getLayoutControl().setLayoutData(tfCodeSupportLData);
//						}
//					}
					{
						laCodeArticle = new Label(groupSupport, SWT.NONE);
						laCodeArticle.setText("Article");
					}
					{
						GridData tfCodeSupportLData = new GridData();
						tfCodeSupportLData.verticalAlignment = GridData.FILL;
						tfCodeSupportLData.horizontalAlignment = GridData.FILL;
						if(!decore) {
							tfCodeArticle = new Text(groupSupport, SWT.BORDER);
							tfCodeArticle.setLayoutData(tfCodeSupportLData);
						} else {					
							fieldCodeArticle = new DecoratedField(groupSupport, SWT.BORDER, new TextControlCreator());
							tfCodeArticle = (Text)fieldCodeArticle.getControl();
							fieldCodeArticle.getLayoutControl().setLayoutData(tfCodeSupportLData);
						}
					}
					{


						}
					}

				{

					groupAbonnement = new Group(paCorpsFormulaire, SWT.NONE);
					GridLayout groupSupportLayout = new GridLayout();
					groupSupportLayout.numColumns = 2;
					groupAbonnement.setLayout(groupSupportLayout);
					GridData groupSuppportLData = new GridData();
					groupSuppportLData.verticalAlignment = GridData.FILL;
					groupSuppportLData.horizontalAlignment = GridData.FILL;
					groupAbonnement.setLayoutData(groupSuppportLData);
					groupAbonnement.setText("Abonnement");
					{
						laDateDebAbon = new Label(groupAbonnement, SWT.NONE);
						laDateDebAbon.setText("Date début");
					}
					{
						GridData dateTimeDATELData = new GridData();
						dateTimeDATELData.widthHint = 90;
						dateTimeDATELData.verticalAlignment = GridData.FILL;
						dateTimeDATELData.horizontalAlignment = GridData.END;

					if(!decore) {
						tfDateDebAbon = new DateTime(groupAbonnement, SWT.BORDER | SWT.DROP_DOWN);
						tfDateDebAbon.setLayoutData(dateTimeDATELData);
						} else {
							fieldDateDebAbon = new DecoratedField(groupAbonnement, SWT.BORDER | SWT.DROP_DOWN, new  DateTimeControlCreator());
							tfDateDebAbon = (DateTime)fieldDateDebAbon.getControl();
							fieldDateDebAbon.getLayoutControl().setLayoutData(dateTimeDATELData);
						}
					}
					{
						laDateFinAbon = new Label(groupAbonnement, SWT.NONE);
						laDateFinAbon.setText("Date fin");
					}
					{
						GridData dateTimeDATELData = new GridData();
						dateTimeDATELData.widthHint = 90;
						dateTimeDATELData.verticalAlignment = GridData.FILL;
						dateTimeDATELData.horizontalAlignment = GridData.END;

					if(!decore) {
						tfDateFinAbon = new DateTime(groupAbonnement, SWT.BORDER | SWT.DROP_DOWN);
						tfDateFinAbon.setLayoutData(dateTimeDATELData);
						} else {							
							fieldDateDebAbon = new DecoratedField(groupAbonnement, SWT.BORDER | SWT.DROP_DOWN, new  DateTimeControlCreator());
							tfDateFinAbon = (DateTime)fieldDateDebAbon.getControl();
							fieldDateDebAbon.getLayoutControl().setLayoutData(dateTimeDATELData);
						}
					}
					{
						laHorsAbon = new Label(groupAbonnement, SWT.NONE);
						GridData laHorsAbonLData = new GridData();
						laHorsAbonLData.horizontalSpan = 2;
						laHorsAbonLData.horizontalAlignment = GridData.FILL;
						laHorsAbon.setLayoutData(laHorsAbonLData);
						laHorsAbon.setText("Hors abonnement !!!");
						laHorsAbon.setForeground(SWTResourceManager.getColor(255, 0, 0));
						laHorsAbon.setFont(SWTResourceManager.getFont("Tahoma", 10, 3, false, false));
					}
				}
				{
					groupDocument = new Group(paCorpsFormulaire, SWT.NONE);
					GridLayout groupDocumentLayout = new GridLayout();
					groupDocumentLayout.makeColumnsEqualWidth = true;
					groupDocumentLayout.numColumns = 2;
					groupDocument.setLayout(groupDocumentLayout);
					GridData groupDocumentLData = new GridData();
					groupDocumentLData.verticalAlignment = GridData.FILL;
					groupDocumentLData.horizontalAlignment = GridData.FILL;
					groupDocumentLData.grabExcessHorizontalSpace = true;
					groupDocument.setLayoutData(groupDocumentLData);
					groupDocument.setText("Document");
					{
						laDocument = new Label(groupDocument, SWT.NONE);
						GridData laDocumentLData = new GridData();
						laDocument.setLayoutData(laDocumentLData);
						laDocument.setText("document");
					}
					{
						tfDocument = new Text(groupDocument, SWT.NONE);
						GridData tfDocumentLData = new GridData();
						tfDocument.setLayoutData(tfDocumentLData);
						tfDocument.setText("tfdocument");
					}
				}
				{
					groupCommentaire = new Group(paCorpsFormulaire, SWT.NONE);
					GridLayout groupCommentaireLayout = new GridLayout();
					groupCommentaire.setLayout(groupCommentaireLayout);
					GridData groupCommentaireLData = new GridData();
					groupCommentaireLData.grabExcessHorizontalSpace = true;
					groupCommentaireLData.verticalAlignment = GridData.FILL;
					groupCommentaireLData.grabExcessVerticalSpace = true;
					groupCommentaireLData.horizontalAlignment = GridData.FILL;
					groupCommentaireLData.horizontalSpan = 3;
					groupCommentaire.setLayoutData(groupCommentaireLData);
					groupCommentaire.setText("Commentaire");
					{
						GridData tfCOMMENTAIRELData = new GridData();
						tfCOMMENTAIRELData.horizontalAlignment = GridData.FILL;
						tfCOMMENTAIRELData.grabExcessHorizontalSpace = true;
						tfCOMMENTAIRELData.verticalAlignment = GridData.FILL;
						tfCOMMENTAIRELData.grabExcessVerticalSpace = true;
						if(!decore) {
							tfCommentaire = new Text(groupCommentaire, SWT.MULTI | SWT.WRAP | SWT.H_SCROLL| SWT.V_SCROLL| SWT.BORDER);
							tfCommentaire.setLayoutData(tfCOMMENTAIRELData);
						} else {					
							fieldCommentaire = new DecoratedField(groupCommentaire, SWT.MULTI
									| SWT.WRAP
									| SWT.H_SCROLL
									| SWT.V_SCROLL
									| SWT.BORDER, new TextControlCreator());
							tfCommentaire = (Text)fieldCommentaire.getControl();
							fieldCommentaire.getLayoutControl().setLayoutData(tfCOMMENTAIRELData);
						}
					}
				}
			}

			
			this.setLayout(new GridLayout());
			GridLayout compositeFormLayout = new GridLayout();
			compositeFormLayout.numColumns = 2;
			this.setSize(955, 403);
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

//	public Label getLaCodeAbonnement() {
//		return laCodeAbonnement;
//	}
//
//	public Text getTfCodeAbonnement() {
//		return tfCodeAbonnement;
//	}
//
//	public DecoratedField getFieldCodeAbonnement() {
//		return fieldCodeAbonnement;
//	}

	public Label getLaCodeArticle() {
		return laCodeArticle;
	}




	public Label getLaDateDebAbon() {
		return laDateDebAbon;
	}

	public Label getLaDateFinAbon() {
		return laDateFinAbon;
	}

	public Text getTfCodeArticle() {
		return tfCodeArticle;
	}




	public Text getTfCommentaire() {
		return tfCommentaire;
	}

	public DateTime getTfDateDebAbon() {
		return tfDateDebAbon;
	}

	public DateTime getTfDateFinAbon() {
		return tfDateFinAbon;
	}

	public Group getGroupSupport() {
		return groupSupport;
	}


	public Group getGroupComplement() {
		return groupComplement;
	}

	public Group getGroupAbonnement() {
		return groupAbonnement;
	}

	public Group getGroupCommentaire() {
		return groupCommentaire;
	}

	public DecoratedField getFieldCodeArticle() {
		return fieldCodeArticle;
	}


	public DecoratedField getFieldCommentaire() {
		return fieldCommentaire;
	}

	public DecoratedField getFieldDateDebAbon() {
		return fieldDateDebAbon;
	}

	public DecoratedField getFieldDateFinAbon() {
		return fieldDateFinAbon;
	}
	
	public Label getLaHorsAbon() {
		return laHorsAbon;
	}

	public Label getLaCodeTiers() {
		return laCodeTiers;
	}
	
	public Group getGroupDocument() {
		return groupDocument;
	}
	
	public Label getLaDocument() {
		return laDocument;
	}
	
	public Text getTfDocument() {
		return tfDocument;
	}

}

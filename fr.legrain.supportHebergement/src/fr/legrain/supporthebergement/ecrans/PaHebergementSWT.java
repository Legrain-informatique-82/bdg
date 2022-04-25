package fr.legrain.supporthebergement.ecrans;
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
import org.eclipse.swt.widgets.ExpandBar;
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
public class PaHebergementSWT extends fr.legrain.lib.gui.DefaultFrameFormulaireSWTSansGrille {

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
	private Label laCodeSupport;
	private Label laCodeArticle;
	private Label laDateAcquisition;

	private Label laServeur;
	private Label laDateDebAbon;
	private Label laDateFinAbon;

	private Text tfCodeSupport;
	private Text tfCodeArticle;
	private DateTime tfDateAcquisition;

	private Combo cfServeur;

	private Text tfCommentaire;
	private DateTime tfDateDebAbon;
	private DateTime tfDateFinAbon;
    private Button btnAbonnement;
	private Group groupSupport;
	private Label laHorsAbon;
	private Group groupComplement;
	private Group groupAbonnement;
	private Group groupCommentaire;
	
	private DecoratedField fieldCodeSupport;
	private DecoratedField fieldCodeArticle;
	private DecoratedField fieldDateAcquisition;

	private DecoratedField fieldCommentaire;
	private DecoratedField fieldDateDebAbon;
	private DecoratedField fieldDateFinAbon;
	
	private ExpandBar expandBarOption;
	
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
		PaHebergementSWT inst = new PaHebergementSWT(shell, SWT.NULL);
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




	public PaHebergementSWT(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			{
				paCorpsFormulaire = new Composite(super.getPaFomulaire(), SWT.NONE);
				GridLayout paCorpsFormulaireLayout = new GridLayout();
				paCorpsFormulaireLayout.numColumns = 3;
				GridData paCorpsFormulaireLData = new GridData();
				paCorpsFormulaireLData.horizontalAlignment = GridData.FILL;
				paCorpsFormulaireLData.grabExcessHorizontalSpace = true;
				paCorpsFormulaireLData.verticalAlignment = GridData.FILL;
				paCorpsFormulaireLData.grabExcessVerticalSpace = true;
				paCorpsFormulaire.setLayoutData(paCorpsFormulaireLData);
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
					{
						laCodeSupport = new Label(groupSupport, SWT.NONE);
						laCodeSupport.setText("Code");
					}
					{
						GridData tfCodeSupportLData = new GridData();
						tfCodeSupportLData.widthHint = 112;
						tfCodeSupportLData.verticalAlignment = GridData.FILL;
						if(!decore) {
							tfCodeSupport = new Text(groupSupport, SWT.BORDER);
							tfCodeSupport.setLayoutData(tfCodeSupportLData);
						} else {					
							fieldCodeSupport = new DecoratedField(groupSupport, SWT.BORDER, new TextControlCreator());
							tfCodeSupport = (Text)fieldCodeSupport.getControl();
							fieldCodeSupport.getLayoutControl().setLayoutData(tfCodeSupportLData);
						}
					}
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
						laDateAcquisition = new Label(groupSupport, SWT.NONE);
						laDateAcquisition.setText("Date acquisition");
					}
					{
						GridData dateTimeDATELData = new GridData();
						dateTimeDATELData.widthHint = 90;
						dateTimeDATELData.verticalAlignment = GridData.FILL;

					if(!decore) {
						tfDateAcquisition = new DateTime(groupSupport, SWT.BORDER | SWT.DROP_DOWN);
						tfDateAcquisition.setLayoutData(dateTimeDATELData);
						} else {
							fieldDateAcquisition = new DecoratedField(groupSupport, SWT.BORDER | SWT.DROP_DOWN, new  DateTimeControlCreator());
							tfDateAcquisition = (DateTime)fieldDateAcquisition.getControl();
							fieldDateAcquisition.getLayoutControl().setLayoutData(dateTimeDATELData);
						}
					}
					{
						laServeur = new Label(groupSupport, SWT.NONE);
						laServeur.setText("Serveur");
					}
					{
						GridData tfNomLData = new GridData();
						tfNomLData.verticalAlignment = GridData.FILL;
						tfNomLData.grabExcessHorizontalSpace = true;
						tfNomLData.horizontalAlignment = GridData.FILL;
							cfServeur = new Combo(groupSupport, SWT.BORDER);
							cfServeur.setLayoutData(tfNomLData);
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
					groupCommentaireLData.horizontalSpan = 2;
					groupCommentaireLData.horizontalAlignment = GridData.FILL;
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
				{
					groupAbonnement = new Group(paCorpsFormulaire, SWT.NONE);
					GridLayout groupSupportLayout = new GridLayout();
					groupSupportLayout.numColumns = 2;
					groupAbonnement.setLayout(groupSupportLayout);
					GridData groupSuppportLData = new GridData();
					groupSuppportLData.verticalAlignment = GridData.FILL;
					groupSuppportLData.widthHint = 224;
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
						btnAbonnement = new Button(groupAbonnement, SWT.PUSH | SWT.CENTER);
						GridData btnAbonnementLData = new GridData();
						btnAbonnement.setLayoutData(btnAbonnementLData);
						btnAbonnement.setText("Abonnement");
					}
					{
						laHorsAbon = new Label(groupAbonnement, SWT.NONE);
						GridData laHorsAbonLData = new GridData();
						laHorsAbonLData.horizontalAlignment = GridData.FILL;
						laHorsAbonLData.grabExcessHorizontalSpace = true;
						laHorsAbon.setLayoutData(laHorsAbonLData);
						laHorsAbon.setText("Hors abonnement !!!");
						laHorsAbon.setForeground(SWTResourceManager.getColor(255, 0, 0));
						laHorsAbon.setFont(SWTResourceManager.getFont("Tahoma", 10, 3, false, false));
					}
				}
				{
					GridData expandBarLData = new GridData();
					expandBarLData.horizontalAlignment = GridData.FILL;
					expandBarLData.grabExcessHorizontalSpace = true;
					expandBarLData.heightHint = 165;
					expandBarLData.verticalAlignment = GridData.BEGINNING;
					expandBarLData.grabExcessVerticalSpace = true;
					expandBarLData.horizontalSpan = 2;
					expandBarOption = new ExpandBar(paCorpsFormulaire, SWT.V_SCROLL);
					expandBarOption.setLayoutData(expandBarLData);
				}

			}

			
			this.setLayout(new GridLayout());
			GridLayout compositeFormLayout = new GridLayout();
			compositeFormLayout.numColumns = 2;
			this.setSize(955, 457);
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

	public Label getLaCodeSupport() {
		return laCodeSupport;
	}

	public Text getTfCodeSupport() {
		return tfCodeSupport;
	}

	public DecoratedField getFieldCodeSupport() {
		return fieldCodeSupport;
	}

	public Label getLaCodeArticle() {
		return laCodeArticle;
	}

	public Label getLaDateAcquisition() {
		return laDateAcquisition;
	}
//
//	public Label getLaNbPoste() {
//		return laNbPoste;
//	}
//
//	public Label getLaNbDossier() {
//		return laNbDossier;
//	}



	public Label getLaServeur() {
		return laServeur;
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

	public DateTime getTfDateAcquisition() {
		return tfDateAcquisition;
	}

//	public Text getTfNbPoste() {
//		return tfNbPoste;
//	}
//
//	public Text getTfNbDossier() {
//		return tfNbDossier;
//	}


	public Combo getCfServeur() {
		return cfServeur;
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

	public DecoratedField getFieldDateAcquisition() {
		return fieldDateAcquisition;
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

	public Button getBtnAbonnement() {
		return btnAbonnement;
	}

}

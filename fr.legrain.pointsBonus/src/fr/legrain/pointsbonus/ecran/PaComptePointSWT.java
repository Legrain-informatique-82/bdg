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
public class PaComptePointSWT extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
	

	private Composite paCorpsFormulaire;
	
	private Group paTotauxLignes;
	private Text tfNbLigne;
	private Label laMT_TTC_CALC;
	private Text tfMT_HT_CALC;
	private Label laMT_HT_CALC;
	private Text tfMT_AVIS_CALC;


	private Label laMT_AVIS_CALC;	
	
	private Label laCodeTiers;
	private Label laPoint;
	private Label laTypeMouvement;
	private Label laDatePeremption;
	private Label laCodeDocument;
	private Label laNomTiers;
	private Label laPrenomTiers;
	private Label laCodeEntite;
	private Label laEntreprise;

	private Text tfPoint;
	private Combo tfTypeMouvement;
	private DateTime tfDatePeremption;
	private Text tfCodeDocument;
	private Text tfCommentaire;
	
	private Text tfNomTiers;
	private Text tfPrenomTiers;
	private Text tfCodeEntite;
	private Text tfEntreprise;
	

	private Button btnTiers;
	private Button btnCalcul;
	private Group groupTiers;
	private Group groupPoint;
	private Group groupCommentaire;
	
	private DecoratedField fieldPoint;
	private DecoratedField fieldTypeMouvement;
	private DecoratedField fieldDatePeremption;
	private DecoratedField fieldCodeDocument;
	private DecoratedField fieldCommentaire;
	
	private DecoratedField fieldNomTiers;
	private DecoratedField fieldPrenomTiers;
	private DecoratedField fieldCodeEntite;
	private DecoratedField fieldEntreprise;
	
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
		PaComptePointSWT inst = new PaComptePointSWT(shell, SWT.NULL);
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




	public PaComptePointSWT(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			{
				{
					paTotauxLignes = new Group(this, SWT.NONE);
					GridLayout paTotauxLignesLayout = new GridLayout();
					paTotauxLignesLayout.numColumns = 10;
					GridData paTotauxLignesLData = new GridData();
					paTotauxLignesLData.verticalAlignment = GridData.BEGINNING;
					paTotauxLignesLData.horizontalAlignment = GridData.CENTER;
					paTotauxLignesLData.horizontalSpan = 2;
					paTotauxLignesLData.widthHint = 800;
					paTotauxLignes.setLayoutData(paTotauxLignesLData);
					paTotauxLignes.setLayout(paTotauxLignesLayout);
					paTotauxLignes.setText("Solde compte point bonus");
				}
				{
					laMT_AVIS_CALC = new Label(paTotauxLignes, SWT.NONE);
					laMT_AVIS_CALC.setText("Solde avis echéance");
				}
				{
					GridData tfMT_AVIS_CALCLData = new GridData();
					tfMT_AVIS_CALCLData.widthHint = 127;
					tfMT_AVIS_CALCLData.verticalAlignment = GridData.BEGINNING;
					tfMT_AVIS_CALC = new Text(paTotauxLignes, SWT.BORDER);
					tfMT_AVIS_CALC.setLayoutData(tfMT_AVIS_CALCLData);
					tfMT_AVIS_CALC.setText("Points");
					tfMT_AVIS_CALC.setEditable(false);

				}
				{
					laMT_HT_CALC = new Label(paTotauxLignes, SWT.NONE);
					laMT_HT_CALC.setText("Solde facture");
				}
				{
					GridData tfMT_HT_CALCLData = new GridData();
					tfMT_HT_CALCLData.widthHint = 127;
					tfMT_HT_CALCLData.verticalAlignment = GridData.BEGINNING;
					tfMT_HT_CALC = new Text(paTotauxLignes, SWT.BORDER);
					tfMT_HT_CALC.setLayoutData(tfMT_HT_CALCLData);
					tfMT_HT_CALC.setText("Points");
					tfMT_HT_CALC.setEditable(false);

				}
				{
					laMT_TTC_CALC = new Label(paTotauxLignes, SWT.NONE);
					laMT_TTC_CALC.setText("Nombre de lignes");
				}
				{
					GridData tfMT_TVA_AVANT_REMISELData = new GridData();
					tfMT_TVA_AVANT_REMISELData.widthHint = 127;
					tfNbLigne = new Text(paTotauxLignes, SWT.BORDER);
					tfNbLigne.setLayoutData(tfMT_TVA_AVANT_REMISELData);
					tfNbLigne.setText("NB");
					tfNbLigne.setEditable(false);

				}
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
					groupTiers = new Group(paCorpsFormulaire, SWT.NONE);
					GridLayout groupSupportLayout = new GridLayout();
					groupSupportLayout.numColumns = 2;
					groupTiers.setLayout(groupSupportLayout);
					GridData groupSuppportLData = new GridData();
					groupSuppportLData.horizontalAlignment = GridData.FILL;
					groupSuppportLData.verticalAlignment = GridData.FILL;
					groupTiers.setLayoutData(groupSuppportLData);
					groupTiers.setText("Identité Tiers");
					{
						laCodeTiers = new Label(groupTiers, SWT.NONE);
						GridData laCodeTiersLData = new GridData();
						laCodeTiersLData.horizontalSpan = 2;
						laCodeTiersLData.horizontalAlignment = GridData.FILL;
						laCodeTiers.setLayoutData(laCodeTiersLData);
						laCodeTiers.setText("Code tiers :");
						laCodeTiers.setFont(SWTResourceManager.getFont("Tahoma", 8, 1, false, false));
					}
					{
						laNomTiers = new Label(groupTiers, SWT.NONE);
						laNomTiers.setText("Nom Tiers");
					}
					{
						GridData tfGroupeLData = new GridData();
						tfGroupeLData.horizontalAlignment = GridData.CENTER;
						tfGroupeLData.verticalAlignment = GridData.FILL;
						tfGroupeLData.grabExcessHorizontalSpace = true;
						tfGroupeLData.widthHint = 175;
						if(!decore) {
							tfNomTiers = new Text(groupTiers, SWT.BORDER);
							tfNomTiers.setLayoutData(tfGroupeLData);
							tfNomTiers.setEditable(false);
						} else {					
							fieldNomTiers = new DecoratedField(groupTiers, SWT.BORDER, new TextControlCreator());
							tfNomTiers = (Text)fieldNomTiers.getControl();
							fieldNomTiers.getLayoutControl().setLayoutData(tfGroupeLData);
						}
					}
					{
						laPrenomTiers = new Label(groupTiers, SWT.NONE);
						laPrenomTiers.setText("Prénom Tiers");
					}					
					{
						GridData tfGroupeLData = new GridData();
						tfGroupeLData.horizontalAlignment = GridData.CENTER;
						tfGroupeLData.verticalAlignment = GridData.FILL;
						tfGroupeLData.grabExcessHorizontalSpace = true;
						tfGroupeLData.widthHint = 175;
						if(!decore) {
							tfPrenomTiers = new Text(groupTiers, SWT.BORDER);
							tfPrenomTiers.setLayoutData(tfGroupeLData);
							tfPrenomTiers.setEditable(false);
						} else {					
							fieldPrenomTiers = new DecoratedField(groupTiers, SWT.BORDER, new TextControlCreator());
							tfPrenomTiers = (Text)fieldPrenomTiers.getControl();
							fieldPrenomTiers.getLayoutControl().setLayoutData(tfGroupeLData);
						}
					}
					{
						laCodeEntite = new Label(groupTiers, SWT.NONE);
						laCodeEntite.setText("Entité");
					}					
					{
						GridData tfGroupeLData = new GridData();
						tfGroupeLData.horizontalAlignment = GridData.CENTER;
						tfGroupeLData.verticalAlignment = GridData.FILL;
						tfGroupeLData.grabExcessHorizontalSpace = true;
						tfGroupeLData.widthHint = 175;
						if(!decore) {
							tfCodeEntite = new Text(groupTiers, SWT.BORDER);
							tfCodeEntite.setLayoutData(tfGroupeLData);
							tfCodeEntite.setEditable(false);
						} else {					
							fieldCodeEntite = new DecoratedField(groupTiers, SWT.BORDER, new TextControlCreator());
							tfCodeEntite = (Text)fieldCodeEntite.getControl();
							fieldCodeEntite.getLayoutControl().setLayoutData(tfGroupeLData);
						}
					}	
					{
						laEntreprise = new Label(groupTiers, SWT.NONE);
						laEntreprise.setText("Entreprise");
					}					
					{
						GridData tfGroupeLData = new GridData();
						tfGroupeLData.horizontalAlignment = GridData.CENTER;
						tfGroupeLData.verticalAlignment = GridData.FILL;
						tfGroupeLData.grabExcessHorizontalSpace = true;
						tfGroupeLData.widthHint = 175;
						if(!decore) {
							tfEntreprise = new Text(groupTiers, SWT.BORDER);
							tfEntreprise.setLayoutData(tfGroupeLData);
							tfEntreprise.setEditable(false);
						} else {
							fieldEntreprise = new DecoratedField(groupTiers, SWT.BORDER, new TextControlCreator());
							tfEntreprise = (Text)fieldEntreprise.getControl();
							fieldEntreprise.getLayoutControl().setLayoutData(tfGroupeLData);
						}
					}
					{
						btnTiers = new Button(groupTiers, SWT.PUSH | SWT.CENTER);
						GridData btnTiersLData = new GridData();
						btnTiersLData.horizontalSpan = 1;
						btnTiers.setLayoutData(btnTiersLData);
						btnTiers.setText("Fiche Tiers");
					}
					{
						btnCalcul = new Button(groupTiers, SWT.PUSH | SWT.CENTER);
						GridData btnTiersLData = new GridData();
						btnTiersLData.horizontalSpan = 1;
						btnCalcul.setLayoutData(btnTiersLData);
						btnCalcul.setText("Calcul points utilisés");
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
//						if(!decore) {
							tfTypeMouvement = new Combo(groupPoint, SWT.BORDER);
							tfTypeMouvement.setLayoutData(tfCodeSupportLData);
//						} 
//						else {					
//							fieldTypeMouvement = new DecoratedField(groupPoint, SWT.BORDER, new TextControlCreator());
//							tfTypeMouvement = (Combo)fieldTypeMouvement.getControl();
//							fieldTypeMouvement.getLayoutControl().setLayoutData(tfCodeSupportLData);
//						}
					}
					{
						laDatePeremption = new Label(groupPoint, SWT.NONE);
						laDatePeremption.setText("Date péremption");
					}
					{
						GridData dateTimeDATELData = new GridData();
						dateTimeDATELData.widthHint = 90;
						dateTimeDATELData.verticalAlignment = GridData.FILL;

					if(!decore) {
						tfDatePeremption = new DateTime(groupPoint, SWT.BORDER | SWT.DROP_DOWN);
						tfDatePeremption.setLayoutData(dateTimeDATELData);
						} else {
							fieldDatePeremption = new DecoratedField(groupPoint, SWT.BORDER | SWT.DROP_DOWN, new  DateTimeControlCreator());
							tfDatePeremption = (DateTime)fieldDatePeremption.getControl();
							fieldDatePeremption.getLayoutControl().setLayoutData(dateTimeDATELData);
						}
					}
					{
						laCodeDocument = new Label(groupPoint, SWT.NONE);
						laCodeDocument.setText("Code document");
					}
					{
						GridData tfGroupeLData = new GridData();
						tfGroupeLData.verticalAlignment = GridData.FILL;
						tfGroupeLData.grabExcessHorizontalSpace = true;
						tfGroupeLData.widthHint = 112;
						if(!decore) {
							tfCodeDocument = new Text(groupPoint, SWT.BORDER);
							tfCodeDocument.setLayoutData(tfGroupeLData);
						} else {					
							fieldCodeDocument = new DecoratedField(groupPoint, SWT.BORDER, new TextControlCreator());
							tfCodeDocument = (Text)fieldCodeDocument.getControl();
							fieldCodeDocument.getLayoutControl().setLayoutData(tfGroupeLData);
						}
					}
				}
				
				
				{
					groupCommentaire = new Group(paCorpsFormulaire, SWT.NONE);
					GridLayout groupCommentaireLayout = new GridLayout();
					groupCommentaire.setLayout(groupCommentaireLayout);
					GridData groupCommentaireLData = new GridData();
					groupCommentaireLData.verticalAlignment = GridData.FILL;
					groupCommentaireLData.grabExcessVerticalSpace = true;
					groupCommentaireLData.horizontalAlignment = GridData.FILL;
					groupCommentaireLData.grabExcessHorizontalSpace = true;
					groupCommentaireLData.horizontalSpan = 2;
					groupCommentaire.setLayoutData(groupCommentaireLData);
					groupCommentaire.setText("Commentaire");
					{
						GridData tfCOMMENTAIRELData = new GridData();
						tfCOMMENTAIRELData.horizontalAlignment = GridData.FILL;
						tfCOMMENTAIRELData.verticalAlignment = GridData.FILL;
						tfCOMMENTAIRELData.grabExcessVerticalSpace = true;
						tfCOMMENTAIRELData.grabExcessHorizontalSpace = true;
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

	public Label getLaDatePeremption() {
		return laDatePeremption;
	}


	public Label getLaCodeDocument() {
		return laCodeDocument;
	}


	public Combo getTfTypeMouvement() {
		return tfTypeMouvement;
	}

	public DateTime getTfDatePeremption() {
		return tfDatePeremption;
	}


	public Text getTfCodeDocument() {
		return tfCodeDocument;
	}



	public Text getTfCommentaire() {
		return tfCommentaire;
	}


	public Group getGroupPoint() {
		return groupPoint;
	}

	public Group getGroupCommentaire() {
		return groupCommentaire;
	}

	public DecoratedField getFieldTypeMouvement() {
		return fieldTypeMouvement;
	}

	public DecoratedField getFieldDatePeremption() {
		return fieldDatePeremption;
	}

	public DecoratedField getFieldCodeDocument() {
		return fieldCodeDocument;
	}

	

	public DecoratedField getFieldCommentaire() {
		return fieldCommentaire;
	}

	

	public Label getLaCodeTiers() {
		return laCodeTiers;
	}

	
	public Group getGroupTiers() {
		return groupTiers;
	}

	public Text getTfNomTiers() {
		return tfNomTiers;
	}

	public Text getTfPrenomTiers() {
		return tfPrenomTiers;
	}

	public Text getTfCodeEntite() {
		return tfCodeEntite;
	}

	public Text getTfEntreprise() {
		return tfEntreprise;
	}

	public Label getLaNomTiers() {
		return laNomTiers;
	}

	public DecoratedField getFieldNomTiers() {
		return fieldNomTiers;
	}

	public DecoratedField getFieldPrenomTiers() {
		return fieldPrenomTiers;
	}

	public DecoratedField getFieldCodeEntite() {
		return fieldCodeEntite;
	}

	public DecoratedField getFieldEntreprise() {
		return fieldEntreprise;
	}

	public Label getLaPrenomTiers() {
		return laPrenomTiers;
	}

	public Label getLaCodeEntite() {
		return laCodeEntite;
	}

	public Label getLaEntreprise() {
		return laEntreprise;
	}
	
	public Button getBtnTiers() {
		return btnTiers;
	}

	public Text getTfPoint() {
		return tfPoint;
	}

	public Button getBtnCalcul() {
		return btnCalcul;
	}

	public Group getPaTotauxLignes() {
		return paTotauxLignes;
	}

	public Text getTfNbLigne() {
		return tfNbLigne;
	}

	public Label getLaMT_TTC_CALC() {
		return laMT_TTC_CALC;
	}

	public Text getTfMT_HT_CALC() {
		return tfMT_HT_CALC;
	}

	public Label getLaMT_HT_CALC() {
		return laMT_HT_CALC;
	}

	public Text getTfMT_AVIS_CALC() {
		return tfMT_AVIS_CALC;
	}

	public Label getLaMT_AVIS_CALC() {
		return laMT_AVIS_CALC;
	}
}

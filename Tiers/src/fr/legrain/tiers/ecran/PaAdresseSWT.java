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
public class PaAdresseSWT extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
/*

 */
	private Composite paCorpsFormulaire;
	private Label laADRESSE1_ADRESSE;
	private Label laADRESSE2_ADRESSE;
	private Label laADRESSE3_ADRESSE;
	private Label laCODEPOSTAL_ADRESSE;
	private Label laVILLE_ADRESSE;
	private Label laPAYS_ADRESSE;
	private Label laCODE_T_ADR;

	private Text tfADRESSE1_ADRESSE;
	private Text tfADRESSE2_ADRESSE;
	private Text tfADRESSE3_ADRESSE;
	private Text tfCODEPOSTAL_ADRESSE;
	private Text tfVILLE_ADRESSE;
	private Text tfPAYS_ADRESSE;	
	private Text tfCODE_T_ADR;
	
	private DecoratedField fieldADRESSE1_ADRESSE;
    private DecoratedField fieldADRESSE2_ADRESSE;
	private DecoratedField fieldADRESSE3_ADRESSE;	
	private DecoratedField fieldCODEPOSTAL_ADRESSE;
	private Button btnGeolocalisation;
	private Button btnItineraire;
	private DecoratedField fieldVILLE_ADRESSE;
	private DecoratedField fieldPAYS_ADRESSE;
	private DecoratedField fieldCODE_T_ADR;
	
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
		PaAdresseSWT inst = new PaAdresseSWT(shell, SWT.NULL);
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

	public PaAdresseSWT(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			{
				paCorpsFormulaire = new Composite(
					super.getPaFomulaire(),
					SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 2;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.grabExcessVerticalSpace = true;
				composite1LData.horizontalSpan = 3;
				composite1LData.grabExcessHorizontalSpace = true;
				paCorpsFormulaire.setLayoutData(composite1LData);
				paCorpsFormulaire.setLayout(composite1Layout);

				{
					laADRESSE1_ADRESSE = new Label(paCorpsFormulaire, SWT.NONE);
					laADRESSE1_ADRESSE.setText("Adresse");
				}
				{
					GridData tfADRESSE1_ADRESSELData = new GridData();
					tfADRESSE1_ADRESSELData.horizontalAlignment = GridData.CENTER;
					tfADRESSE1_ADRESSELData.widthHint = 156;
					tfADRESSE1_ADRESSELData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfADRESSE1_ADRESSE = new Text(paCorpsFormulaire, SWT.BORDER);
						tfADRESSE1_ADRESSE.setLayoutData(tfADRESSE1_ADRESSELData);
					} else {
						fieldADRESSE1_ADRESSE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
						tfADRESSE1_ADRESSE = (Text)fieldADRESSE1_ADRESSE.getControl();
						fieldADRESSE1_ADRESSE.getLayoutControl().setLayoutData(tfADRESSE1_ADRESSELData);
					}					
				}
				{
					laADRESSE2_ADRESSE = new Label(paCorpsFormulaire, SWT.NONE);
					laADRESSE2_ADRESSE.setText("Adresse (2)");
				}
				{
					GridData tfADRESSE2_ADRESSELData = new GridData();
					tfADRESSE2_ADRESSELData.horizontalAlignment = GridData.FILL;
					if(!decore) {
						tfADRESSE2_ADRESSE = new Text(paCorpsFormulaire, SWT.BORDER);
						tfADRESSE2_ADRESSE.setLayoutData(tfADRESSE2_ADRESSELData);
					} else {					
					fieldADRESSE2_ADRESSE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfADRESSE2_ADRESSE = (Text)fieldADRESSE2_ADRESSE.getControl();
					fieldADRESSE2_ADRESSE.getLayoutControl().setLayoutData(tfADRESSE2_ADRESSELData);
					}
				}
				{
					laADRESSE3_ADRESSE = new Label(paCorpsFormulaire, SWT.NONE);
					laADRESSE3_ADRESSE.setText("Adresse (3)");
				}
				{
					GridData tfADRESSE3_ADRESSELData = new GridData();
					tfADRESSE3_ADRESSELData.horizontalAlignment = GridData.FILL;
					if(!decore) {
						tfADRESSE3_ADRESSE = new Text(paCorpsFormulaire, SWT.BORDER);
						tfADRESSE3_ADRESSE.setLayoutData(tfADRESSE3_ADRESSELData);
					} else {					
					fieldADRESSE3_ADRESSE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfADRESSE3_ADRESSE = (Text)fieldADRESSE3_ADRESSE.getControl();
					fieldADRESSE3_ADRESSE.getLayoutControl().setLayoutData(tfADRESSE3_ADRESSELData);
					}
				}
			{
				laCODEPOSTAL_ADRESSE = new Label(paCorpsFormulaire, SWT.NONE);
				laCODEPOSTAL_ADRESSE.setText("Code postal");
			}
			{
				GridData tfCODEPOSTAL_ADRESSELData = new GridData();
				tfCODEPOSTAL_ADRESSELData.horizontalAlignment = GridData.CENTER;
				if(!decore) {
					tfCODEPOSTAL_ADRESSE = new Text(paCorpsFormulaire, SWT.BORDER);
					//tfCODEPOSTAL_ADRESSE.setLayoutData(tfCODEPOSTAL_ADRESSELData);
				} else {					
				fieldCODEPOSTAL_ADRESSE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
				tfCODEPOSTAL_ADRESSE = (Text)fieldCODEPOSTAL_ADRESSE.getControl();
				//fieldCODEPOSTAL_ADRESSE.getLayoutControl().setLayoutData(tfCODEPOSTAL_ADRESSELData);
				}
			}
			{
				laVILLE_ADRESSE = new Label(paCorpsFormulaire, SWT.NONE);
				GridData laVILLE_ADRESSELData = new GridData();
				laVILLE_ADRESSELData.widthHint = 60;
				laVILLE_ADRESSELData.heightHint = 13;
				laVILLE_ADRESSE.setLayoutData(laVILLE_ADRESSELData);
				laVILLE_ADRESSE.setText("Ville");
			}
			{
				GridData tfVILLE_ADRESSELData = new GridData();
				tfVILLE_ADRESSELData.horizontalAlignment = GridData.FILL;
				if(!decore) {
					tfVILLE_ADRESSE = new Text(paCorpsFormulaire, SWT.BORDER);
					tfVILLE_ADRESSE.setLayoutData(tfVILLE_ADRESSELData);
				} else {					
				fieldVILLE_ADRESSE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
				tfVILLE_ADRESSE = (Text)fieldVILLE_ADRESSE.getControl();
				fieldVILLE_ADRESSE.getLayoutControl().setLayoutData(tfVILLE_ADRESSELData);
				}
			}
			{
				laPAYS_ADRESSE = new Label(paCorpsFormulaire, SWT.NONE);
				laPAYS_ADRESSE.setText("Pays");
			}
			{
				GridData tfPAYS_ADRESSELData = new GridData();
				tfPAYS_ADRESSELData.horizontalAlignment = GridData.FILL;
				if(!decore) {
					tfPAYS_ADRESSE = new Text(paCorpsFormulaire, SWT.BORDER);
					tfPAYS_ADRESSE.setLayoutData(tfPAYS_ADRESSELData);
				} else {					
				fieldPAYS_ADRESSE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
				tfPAYS_ADRESSE = (Text)fieldPAYS_ADRESSE.getControl();
				fieldPAYS_ADRESSE.getLayoutControl().setLayoutData(tfPAYS_ADRESSELData);
				}
			}
			{
				laCODE_T_ADR = new Label(paCorpsFormulaire, SWT.NONE);
				laCODE_T_ADR.setText("Type d'adresse");
			}
			{
				GridData tfCODE_T_ADRLData = new GridData();
				tfCODE_T_ADRLData.verticalAlignment = GridData.FILL;
				if(!decore) {
					tfCODE_T_ADR = new Text(paCorpsFormulaire, SWT.BORDER);
					tfCODE_T_ADR.setLayoutData(tfCODE_T_ADRLData);
				} else {					
				fieldCODE_T_ADR = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
				tfCODE_T_ADR = (Text)fieldCODE_T_ADR.getControl();
				fieldCODE_T_ADR.getLayoutControl().setLayoutData(tfCODE_T_ADRLData);
				}
			}
			{
				btnGeolocalisation = new Button(paCorpsFormulaire, SWT.PUSH | SWT.CENTER);
				GridData btnGeolocalisationLData = new GridData();
				btnGeolocalisationLData.horizontalSpan = 1;
				btnGeolocalisationLData.horizontalAlignment = GridData.CENTER;
				btnGeolocalisation.setLayoutData(btnGeolocalisationLData);
				btnGeolocalisation.setText("Géolocalisation");
			}
			{
				btnItineraire = new Button(paCorpsFormulaire, SWT.PUSH | SWT.CENTER);
				GridData btnGeolocalisationLData = new GridData();
				btnGeolocalisationLData.horizontalSpan = 1;
				btnGeolocalisationLData.horizontalAlignment = GridData.CENTER;
				btnItineraire.setLayoutData(btnGeolocalisationLData);
				btnItineraire.setText("Itinéraire");
			}
//				paCorpsFormulaire
//					.setTabList(new org.eclipse.swt.widgets.Control[] {
//							tfCODE_TIERS, tfCODE_COMPTA, tfNOM_TIERS,
//							tfPRENOM_TIERS, tfSURNOM_TIERS, composite2,
//							tfCODE_T_CIVILITE, tfCODE_ENTREPRISE, tfADRESSE1_ADRESSE, tfCOMPTE,
//							tfLIBL_COMMENTAIRE, tfADRESSE1_ADRESSE, tfEntite, composite3 });
			}
			
			this.setLayout(new GridLayout());
			GridLayout compositeFormLayout = new GridLayout();
			compositeFormLayout.numColumns = 2;
			this.setSize(682, 384);
			GridData paGrilleLData = new GridData();
			paGrilleLData.verticalAlignment = GridData.FILL;
			paGrilleLData.horizontalAlignment = GridData.FILL;
			paGrilleLData.grabExcessHorizontalSpace = true;
			paGrilleLData.grabExcessVerticalSpace = true;
			super.getCompositeForm().setLayout(compositeFormLayout);
			GridData paFomulaireLData = new GridData();
			paFomulaireLData.verticalAlignment = GridData.FILL;
			paFomulaireLData.grabExcessVerticalSpace = true;
			paFomulaireLData.horizontalAlignment = GridData.FILL;
			super.getPaGrille().setLayoutData(paGrilleLData);
			GridData compositeFormLData = new GridData();
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

	public DecoratedField getFieldADRESSE1_ADRESSE() {
		return fieldADRESSE1_ADRESSE;
	}

	public DecoratedField getFieldADRESSE2_ADRESSE() {
		return fieldADRESSE2_ADRESSE;
	}

	public DecoratedField getFieldADRESSE3_ADRESSE() {
		return fieldADRESSE3_ADRESSE;
	}

	public Label getLaADRESSE1_ADRESSE() {
		return laADRESSE1_ADRESSE;
	}

	public Label getLaADRESSE2_ADRESSE() {
		return laADRESSE2_ADRESSE;
	}

	public Label getLaADRESSE3_ADRESSE() {
		return laADRESSE3_ADRESSE;
	}

	public Composite getPaCorpsFormulaire() {
		return paCorpsFormulaire;
	}

	public Text getTfADRESSE1_ADRESSE() {
		return tfADRESSE1_ADRESSE;
	}

	public Text getTfADRESSE2_ADRESSE() {
		return tfADRESSE2_ADRESSE;
	}

	public Text getTfADRESSE3_ADRESSE() {
		return tfADRESSE3_ADRESSE;
	}

	public void setFieldADRESSE1_ADRESSE(DecoratedField fieldCODE_BANQUE) {
		this.fieldADRESSE1_ADRESSE = fieldCODE_BANQUE;
	}

	public void setFieldADRESSE2_ADRESSE(DecoratedField fieldLIBC_BANQUE) {
		this.fieldADRESSE2_ADRESSE = fieldLIBC_BANQUE;
	}

	public void setFieldADRESSE3_ADRESSE(DecoratedField fieldLIBL_BANQUE) {
		this.fieldADRESSE3_ADRESSE = fieldLIBL_BANQUE;
	}

	public void setLaADRESSE1_ADRESSE(Label laCODE_BANQUE) {
		this.laADRESSE1_ADRESSE = laCODE_BANQUE;
	}

	public void setLaADRESSE2_ADRESSE(Label laLIBC_BANQUE) {
		this.laADRESSE2_ADRESSE = laLIBC_BANQUE;
	}

	public void setLaADRESSE3_ADRESSE(Label laLIBL_BANQUE) {
		this.laADRESSE3_ADRESSE = laLIBL_BANQUE;
	}

	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public void setTfADRESSE1_ADRESSE(Text tfCODE_BANQUE) {
		this.tfADRESSE1_ADRESSE = tfCODE_BANQUE;
	}

	public void setTfADRESSE2_ADRESSE(Text tfLIBC_BANQUE) {
		this.tfADRESSE2_ADRESSE = tfLIBC_BANQUE;
	}

	public void setTfADRESSE3_ADRESSE(Text tfLIBL_BANQUE) {
		this.tfADRESSE3_ADRESSE = tfLIBL_BANQUE;
	}
	
	public Label getLaCODEPOSTAL_ADRESSE() {
		return laCODEPOSTAL_ADRESSE;
	}
	
	public Text getTfCODEPOSTAL_ADRESSE() {
		return tfCODEPOSTAL_ADRESSE;
	}
	
	public Label getLaVILLE_ADRESSE() {
		return laVILLE_ADRESSE;
	}
	
	public Text getTfVILLE_ADRESSE() {
		return tfVILLE_ADRESSE;
	}
	
	public Label getLaPAYS_ADRESSE() {
		return laPAYS_ADRESSE;
	}
	
	public Text getTfPAYS_ADRESSE() {
		return tfPAYS_ADRESSE;
	}
	
	public Label getLaCODE_T_ADR() {
		return laCODE_T_ADR;
	}
	
	public Text getTfCODE_T_ADR() {
		return tfCODE_T_ADR;
	}

	public DecoratedField getFieldCODE_T_ADR() {
		return fieldCODE_T_ADR;
	}

	public DecoratedField getFieldCODEPOSTAL_ADRESSE() {
		return fieldCODEPOSTAL_ADRESSE;
	}

	public DecoratedField getFieldPAYS_ADRESSE() {
		return fieldPAYS_ADRESSE;
	}

	public DecoratedField getFieldVILLE_ADRESSE() {
		return fieldVILLE_ADRESSE;
	}
	
	public Button getBtnGeolocalisation() {
		return btnGeolocalisation;
	}

	public Button getBtnItineraire() {
		return btnItineraire;
	}

}

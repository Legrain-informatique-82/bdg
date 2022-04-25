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
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.resource.SWTResourceManager;

import fr.legrain.lib.gui.cdatetimeLgr;
import fr.legrain.lib.gui.fieldassist.CDateTimeControlCreator;
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
public class PaNoteTiersSWT extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	private Composite paCorpsFormulaire;
	private Label laNOTE_TIERS;
//	private Button btnOpenUrl;
	private Label laCODE_T_NOTE_TIERS;
	private Label laDATE_NOTE_TIERS;

	private Text tfNOTE_TIERS;
	private Text tfCODE_T_NOTE_TIERS;
	private DateTime dateTimeNOTE_TIERS;

	private DecoratedField fieldNOTE_TIERS;
	private DecoratedField fieldCODE_T_NOTE_TIERS;
	private DecoratedField fieldDATE_NOTE_TIERS;
	
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
		PaNoteTiersSWT inst = new PaNoteTiersSWT(shell, SWT.NULL);
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

	public PaNoteTiersSWT(org.eclipse.swt.widgets.Composite parent, int style) {
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
					laNOTE_TIERS = new Label(paCorpsFormulaire, SWT.NONE);
					laNOTE_TIERS.setText("Note");
				}
				{
					GridData tfNOTE_TIERSLData = new GridData();
					tfNOTE_TIERSLData.horizontalAlignment = GridData.CENTER;
					tfNOTE_TIERSLData.grabExcessHorizontalSpace = true;
					tfNOTE_TIERSLData.horizontalAlignment = GridData.FILL;
					tfNOTE_TIERSLData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfNOTE_TIERS = new Text(paCorpsFormulaire, SWT.BORDER);
						tfNOTE_TIERS.setLayoutData(tfNOTE_TIERSLData);
					} else {					
					fieldNOTE_TIERS = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfNOTE_TIERS = (Text)fieldNOTE_TIERS.getControl();
					fieldNOTE_TIERS.getLayoutControl().setLayoutData(tfNOTE_TIERSLData);
					}
				}
//				{
//					btnOpenUrl = new Button(paCorpsFormulaire, SWT.PUSH | SWT.CENTER);
//					GridData btnLData = new GridData();
//					btnLData.horizontalAlignment = GridData.CENTER;
//					btnOpenUrl.setLayoutData(btnLData);
//					btnOpenUrl.setText("Accéder au site");
//				}
				
				{
					laCODE_T_NOTE_TIERS = new Label(paCorpsFormulaire, SWT.NONE);
					laCODE_T_NOTE_TIERS.setText("Code type");
				}
				{
					GridData tfCODE_T_WEBLData = new GridData();
					tfCODE_T_WEBLData.widthHint = 188;
					tfCODE_T_WEBLData.verticalAlignment = GridData.FILL;
					//tfCODE_T_WEBLData.horizontalSpan = 2;
					if(!decore) {
						tfCODE_T_NOTE_TIERS = new Text(paCorpsFormulaire, SWT.BORDER);
						tfCODE_T_NOTE_TIERS.setLayoutData(tfCODE_T_WEBLData);
					} else {					
					fieldCODE_T_NOTE_TIERS = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfCODE_T_NOTE_TIERS = (Text)fieldCODE_T_NOTE_TIERS.getControl();
					fieldCODE_T_NOTE_TIERS.getLayoutControl().setLayoutData(tfCODE_T_WEBLData);
					}
				}
				{
					laDATE_NOTE_TIERS = new Label(paCorpsFormulaire, SWT.NONE);
					laDATE_NOTE_TIERS.setText("Date");
				}
				{
					GridData dateTimeDATE_NOTELData = new GridData();
					dateTimeDATE_NOTELData.widthHint = 90;
					dateTimeDATE_NOTELData.verticalAlignment = GridData.FILL;

				if(!decore) {
					dateTimeNOTE_TIERS = new DateTime(paCorpsFormulaire, SWT.BORDER | SWT.DROP_DOWN);
					dateTimeNOTE_TIERS.setLayoutData(dateTimeDATE_NOTELData);
					} else {
						fieldDATE_NOTE_TIERS = new DecoratedField(paCorpsFormulaire, SWT.BORDER | SWT.DROP_DOWN, new  DateTimeControlCreator());
						dateTimeNOTE_TIERS = (DateTime)fieldDATE_NOTE_TIERS.getControl();
						fieldDATE_NOTE_TIERS.getLayoutControl().setLayoutData(dateTimeDATE_NOTELData);
					}
				//dateTimeNOTE_TIERS.setPattern("dd/MM/yyyy");						
				}

//				paCorpsFormulaire
//					.setTabList(new org.eclipse.swt.widgets.Control[] {
//							tfLIBELLE_T_TIERS, tfADRESSE_WEB, tfCODE_T_TIERS,
//							tfPRENOM_TIERS, tfSURNOM_TIERS, composite2,
//							tfADRESSE_WEB, tfCODE_ENTREPRISE, tfCODE_T_TIERS, tfCOMPTE,
//							tfLIBL_COMMENTAIRE, tfCODE_BANQUE, tfEntite, composite3 });
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

	public Composite getPaCorpsFormulaire() {
		return paCorpsFormulaire;
	}

	public Label getLaNOTE_TIERS() {
		return laNOTE_TIERS;
	}


	public Text getTfNOTE_TIERS() {
		return tfNOTE_TIERS;
	}

	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public DecoratedField getFieldNOTE_TIERS() {
		return fieldNOTE_TIERS;
	}

	public Label getLaCODE_T_NOTE_TIERS() {
		return laCODE_T_NOTE_TIERS;
	}

	public void setLaCODE_T_NOTE_TIERS(Label laCODE_T_NOTE_TIERS) {
		this.laCODE_T_NOTE_TIERS = laCODE_T_NOTE_TIERS;
	}

	public Text getTfCODE_T_NOTE_TIERS() {
		return tfCODE_T_NOTE_TIERS;
	}

	public void setTfCODE_T_NOTE_TIERS(Text tfCODE_T_NOTE_TIERS) {
		this.tfCODE_T_NOTE_TIERS = tfCODE_T_NOTE_TIERS;
	}

	public DecoratedField getFieldCODE_T_NOTE_TIERS() {
		return fieldCODE_T_NOTE_TIERS;
	}

	public void setFieldCODE_T_NOTE_TIERS(DecoratedField fieldCODE_T_NOTE_TIERS) {
		this.fieldCODE_T_NOTE_TIERS = fieldCODE_T_NOTE_TIERS;
	}

	public boolean isDecore() {
		return decore;
	}

	public void setLaNOTE_TIERS(Label laNOTE_TIERS) {
		this.laNOTE_TIERS = laNOTE_TIERS;
	}

	public void setTfNOTE_TIERS(Text tfNOTE_TIERS) {
		this.tfNOTE_TIERS = tfNOTE_TIERS;
	}

	public void setFieldNOTE_TIERS(DecoratedField fieldNOTE_TIERS) {
		this.fieldNOTE_TIERS = fieldNOTE_TIERS;
	}

	public Label getLaDATE_NOTE_TIERS() {
		return laDATE_NOTE_TIERS;
	}

	public void setLaDATE_NOTE_TIERS(Label laDATENOTE_TIERS) {
		laDATE_NOTE_TIERS = laDATENOTE_TIERS;
	}

	public DateTime getDateTimeNOTE_TIERS() {
		return dateTimeNOTE_TIERS;
	}

	public void setDateTimeNOTE_TIERS(DateTime dateTimeNOTE_TIERS) {
		this.dateTimeNOTE_TIERS = dateTimeNOTE_TIERS;
	}

	public DecoratedField getFieldDATE_NOTE_TIERS() {
		return fieldDATE_NOTE_TIERS;
	}

	public void setFieldDATE_NOTE_TIERS(DecoratedField fieldDATENOTE_TIERS) {
		fieldDATE_NOTE_TIERS = fieldDATENOTE_TIERS;
	}
	
//	public Button getBtnOpenUrl() {
//		return btnOpenUrl;
//	}

}

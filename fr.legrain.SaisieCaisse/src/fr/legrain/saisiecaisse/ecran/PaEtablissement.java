package fr.legrain.saisiecaisse.ecran;

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

import fr.legrain.lib.gui.cdatetimeLgr;
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
public class PaEtablissement extends fr.legrain.lib.gui.DefaultFrameFormulaireSWT {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	private Composite paCorpsFormulaire;
	private Label laNOM_ETABLISSEMENT;
	private Label laCONTACT;
	private Label laJOURNAL_CAISSE;
	
	private Text tfNOM_ETABLISSEMENT;
	private Text tfCONTACT;
	private Text tfJOURNAL_CAISSE;

	private DecoratedField fieldNOM_ETABLISSEMENT;
	private DecoratedField fieldCONTACT;
	private DecoratedField fieldJOURNAL_CAISSE;


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
		PaEtablissement inst = new PaEtablissement(shell, SWT.NULL);
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

	public PaEtablissement(org.eclipse.swt.widgets.Composite parent, int style) {
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
					laNOM_ETABLISSEMENT = new Label(paCorpsFormulaire, SWT.NONE);
					laNOM_ETABLISSEMENT.setText("Nom de l'etablissement");
				}
				{
					GridData tfNOM_ETABLISSEMENTLData = new GridData();
					tfNOM_ETABLISSEMENTLData.horizontalAlignment = GridData.BEGINNING;
					tfNOM_ETABLISSEMENTLData.widthHint = 177;
					if(!decore) {
						tfNOM_ETABLISSEMENT = new Text(paCorpsFormulaire, SWT.BORDER);
						tfNOM_ETABLISSEMENT.setLayoutData(tfNOM_ETABLISSEMENTLData);
					} else {					
					fieldNOM_ETABLISSEMENT = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfNOM_ETABLISSEMENT = (Text)fieldNOM_ETABLISSEMENT.getControl();
					fieldNOM_ETABLISSEMENT.getLayoutControl().setLayoutData(tfNOM_ETABLISSEMENTLData);
					}
				}				
				
				{
					laCONTACT = new Label(paCorpsFormulaire, SWT.NONE);
					laCONTACT.setText("Contact");
				}
				{
					GridData tfMONTANT_DEPOTLData = new GridData();
					tfMONTANT_DEPOTLData.horizontalAlignment = GridData.BEGINNING;
					tfMONTANT_DEPOTLData.widthHint = 177;
					if(!decore) {
						tfCONTACT = new Text(paCorpsFormulaire, SWT.BORDER);
						tfCONTACT.setLayoutData(tfMONTANT_DEPOTLData);
					} else {					
					fieldCONTACT = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfCONTACT = (Text)fieldCONTACT.getControl();
					fieldCONTACT.getLayoutControl().setLayoutData(tfMONTANT_DEPOTLData);
					}
				}				

				{
					laJOURNAL_CAISSE = new Label(paCorpsFormulaire, SWT.NONE);
					laJOURNAL_CAISSE.setText("Journal de caisse");
				}
				{
					GridData tfCODE_T_PAIEMENTLData = new GridData();
					tfCODE_T_PAIEMENTLData.horizontalAlignment = GridData.BEGINNING;
					tfCODE_T_PAIEMENTLData.widthHint = 177;
					if(!decore) {
						tfJOURNAL_CAISSE = new Text(paCorpsFormulaire, SWT.BORDER);
						tfJOURNAL_CAISSE.setLayoutData(tfCODE_T_PAIEMENTLData);
					} else {					
					fieldJOURNAL_CAISSE = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfJOURNAL_CAISSE = (Text)fieldJOURNAL_CAISSE.getControl();
					fieldJOURNAL_CAISSE.getLayoutControl().setLayoutData(tfCODE_T_PAIEMENTLData);
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



	public Label getLaCONTACT() {
		return laCONTACT;
	}



	public Text getTfJOURNAL_CAISSE() {
		return tfJOURNAL_CAISSE;
	}

	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}


	public DecoratedField getFieldJOURNAL_CAISSE() {
		return fieldJOURNAL_CAISSE;
	}

	public Label getLaNOM_ETABLISSEMENT() {
		return laNOM_ETABLISSEMENT;
	}

	public Label getLaJOURNAL_CAISSE() {
		return laJOURNAL_CAISSE;
	}

	public Text getTfNOM_ETABLISSEMENT() {
		return tfNOM_ETABLISSEMENT;
	}

	public Text getTfCONTACT() {
		return tfCONTACT;
	}

	public DecoratedField getFieldNOM_ETABLISSEMENT() {
		return fieldNOM_ETABLISSEMENT;
	}

	public DecoratedField getFieldCONTACT() {
		return fieldCONTACT;
	}

}

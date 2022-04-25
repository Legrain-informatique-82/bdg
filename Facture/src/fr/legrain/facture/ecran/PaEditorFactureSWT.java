package fr.legrain.facture.ecran;
//import com.cloudgarden.resource.SWTResourceManager;
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
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import fr.legrain.lib.gui.PaBtnAvecAssistant;
import fr.legrain.lib.gui.fieldassist.ButtonControlCreator;
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
public class PaEditorFactureSWT extends org.eclipse.swt.widgets.Composite {

//	{
//		//Register as a resource user - SWTResourceManager will
//		//handle the obtaining and disposing of resources
//		SWTResourceManager.registerResourceUser(this);
//	}

	private Group paEntete;
	private Label laDATE_LIV_DOCUMENT;
	private Button cbTTC;
	private Label laMessage;
	private Label laTTC;
	private Text tfCODE_TIERS;
	private Label laCODE_TIERS;
	private Text tfLIBELLE_DOCUMENT;
	private Label laLIBELLE_DOCUMENT;
	//private Text tfDATE_LIV_FACTURE;
	//private Text tfDATE_ECH_FACTURE;
	//private Text tfDATE_FACTURE;
	private Label laDATE_DOCUMENT;
	private Text tfCODE_DOCUMENT;
	private Label laCODE_DOCUMENT;

	private Text tfNOM_TIERS;
	private Label laNOM_TIERS;
	
	private Label laLocalisationTVA;
	private Combo comboLocalisationTVA;
	
	private DateTime dateTimeDATE_LIV_DOCUMENT;
	private DateTime dateTimeDATE_DOCUMENT;

	private DecoratedField fieldTTC;
	private DecoratedField fieldCODE_TIERS;
	private DecoratedField fieldLIBELLE_DOCUMENT;
	private DecoratedField fieldDATE_LIV_DOCUMENT;
	private DecoratedField fieldDATE_DOCUMENT;
	private DecoratedField fieldCODE_DOCUMENT;
	private DecoratedField fieldNOM_TIERS;
	private Button btnFicheTiers;
	private Button btnGenDoc;
	private Button btnGenModel;
	private Button btnAutres;
	private Group paBtnSupp;
	private ExpandBar expandBar;
	private PaBtnAvecAssistant paBtnAvecAssistant;

	private Button btnAideDocument; 
	private Button btnAideTiers;
	final private boolean decore =LgrMess.isDECORE();
//	final private boolean decore =false;
	/**
	 * Auto-generated main method to display this 
	 * org.eclipse.swt.widgets.Composite inside a new Shell.
	 */
	public static void main(String[] args) {
		showGUI();
	}

	/**
	 * Auto-generated method to display this 
	 * org.eclipse.swt.widgets.Composite inside a new Shell.
	 */
	public static void showGUI() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		PaEditorFactureSWT inst = new PaEditorFactureSWT(shell, SWT.NULL);
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

	public PaEditorFactureSWT(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			this.setLayout(new GridLayout());
			this.setSize(1059, 715);
			{
				paEntete = new Group(this, SWT.NONE);
				GridLayout PaEnteteLayout = new GridLayout();
				PaEnteteLayout.numColumns = 10;
				paEntete.setLayout(PaEnteteLayout);
				GridData PaEnteteLData = new GridData();
				//PaEnteteLData.heightHint = 64;
				PaEnteteLData.verticalAlignment = GridData.BEGINNING;
				PaEnteteLData.horizontalAlignment = GridData.FILL;
				PaEnteteLData.grabExcessHorizontalSpace = true;
				paEntete.setLayoutData(PaEnteteLData);
				paEntete.setText("Entête facture");
				{
					laCODE_DOCUMENT = new Label(paEntete, SWT.NONE);
					laCODE_DOCUMENT.setText("Code Facture");
				}
				{
					GridData tfCODE_FACTURELData = new GridData();
					tfCODE_FACTURELData.horizontalAlignment = GridData.FILL;
					if(!decore) {
						tfCODE_DOCUMENT = new Text(paEntete, SWT.BORDER);
						tfCODE_DOCUMENT.setLayoutData(tfCODE_FACTURELData);
						tfCODE_DOCUMENT.setText("CODE_FACTURE ");
					} else {
						fieldCODE_DOCUMENT = new DecoratedField(paEntete, SWT.BORDER, new TextControlCreator());
						tfCODE_DOCUMENT = (Text)fieldCODE_DOCUMENT.getControl();
						fieldCODE_DOCUMENT.getLayoutControl().setLayoutData(tfCODE_FACTURELData);
					}
				}
				{
					btnAideDocument = new Button(paEntete, SWT.PUSH | SWT.BEGINNING);
					GridData btnLData = new GridData();
					btnAideDocument.setLayoutData(btnLData);
					btnAideDocument.setToolTipText("Recherche");
				}

				{
					laCODE_TIERS = new Label(paEntete, SWT.NONE);
					GridData laCODE_TIERSLData = new GridData();
					laCODE_TIERSLData.widthHint = 63;
					laCODE_TIERSLData.horizontalAlignment = GridData.END;
					laCODE_TIERS.setLayoutData(laCODE_TIERSLData);
					laCODE_TIERS.setText("Code tiers");
				}
				{
					GridData tfCODE_TIERSLData = new GridData();
					tfCODE_TIERSLData.widthHint = 168;
					if(!decore) {
						tfCODE_TIERS = new Text(paEntete, SWT.BORDER);
						tfCODE_TIERS.setLayoutData(tfCODE_TIERSLData);
						tfCODE_TIERS.setText("CODE_TIERS");
					} else {
						fieldCODE_TIERS = new DecoratedField(paEntete, SWT.BORDER, new TextControlCreator());
						tfCODE_TIERS = (Text)fieldCODE_TIERS.getControl();
						fieldCODE_TIERS.getLayoutControl().setLayoutData(tfCODE_TIERSLData);
					}
				}
				{
					btnAideTiers = new Button(paEntete, SWT.PUSH | SWT.BEGINNING);
					GridData btnLData = new GridData();
					btnAideTiers.setLayoutData(btnLData);
					btnAideTiers.setToolTipText("Recherche");
				}	
				{
					laNOM_TIERS = new Label(paEntete, SWT.NONE);
					GridData laNOM_TIERSLData = new GridData();
					laNOM_TIERSLData.horizontalAlignment = GridData.END;
					laNOM_TIERS.setLayoutData(laNOM_TIERSLData);
					laNOM_TIERS.setText("Nom du Tiers");
				}
				{
					GridData tfNOMLData = new GridData();
					tfNOMLData.horizontalAlignment = GridData.FILL;
					tfNOMLData.widthHint = 180;
//					tfNOMLData.horizontalSpan = 1;
					//tfNOMLData.grabExcessHorizontalSpace = true;
					if(!decore) {
						tfNOM_TIERS = new Text(paEntete, SWT.BORDER);
						tfNOM_TIERS.setLayoutData(tfNOMLData);
						tfNOM_TIERS.setText("NOM TIERS");
					} else {
						fieldNOM_TIERS = new DecoratedField(paEntete, SWT.BORDER, new TextControlCreator());
						tfNOM_TIERS = (Text)fieldNOM_TIERS.getControl();
						fieldNOM_TIERS.getLayoutControl().setLayoutData(tfNOMLData);
					}
				}				
			}
				{
					laDATE_DOCUMENT = new Label(paEntete, SWT.NONE);
					GridData laDATE_FACTURELData = new GridData();
					laDATE_FACTURELData.grabExcessHorizontalSpace = true;
					laDATE_FACTURELData.horizontalAlignment = GridData.END;
					laDATE_FACTURELData.verticalAlignment = GridData.BEGINNING;
					//laDATE_FACTURELData.verticalAlignment = GridData.FILL;
					laDATE_DOCUMENT.setLayoutData(laDATE_FACTURELData);
					laDATE_DOCUMENT.setText("Date facture");
				}
				{
					
					GridData dateTimeDATE_FACTURELData = new GridData();
					dateTimeDATE_FACTURELData.verticalAlignment = GridData.FILL;
					dateTimeDATE_FACTURELData.grabExcessHorizontalSpace = true;
					dateTimeDATE_FACTURELData.widthHint = 95;

				if(!decore) {
					dateTimeDATE_DOCUMENT = new DateTime(paEntete, SWT.BORDER | SWT.DROP_DOWN);
					dateTimeDATE_DOCUMENT.setLayoutData(dateTimeDATE_FACTURELData);
					} else {
					fieldDATE_DOCUMENT = new DecoratedField(paEntete, SWT.BORDER | SWT.DROP_DOWN, new DateTimeControlCreator());
					dateTimeDATE_DOCUMENT = (DateTime)fieldDATE_DOCUMENT.getControl();
					fieldDATE_DOCUMENT.getLayoutControl().setLayoutData(dateTimeDATE_FACTURELData);
					}
//					dateTimeDATE_DOCUMENT.setPattern("dd/MM/yyyy");	
				}
				{
					laLIBELLE_DOCUMENT = new Label(paEntete, SWT.NONE);
					laLIBELLE_DOCUMENT.setText("Libellé");
				}
				{
					GridData tfLIBELLE_FACTURELData = new GridData();
					tfLIBELLE_FACTURELData.horizontalSpan = 5;
					tfLIBELLE_FACTURELData.horizontalAlignment = GridData.FILL;
					tfLIBELLE_FACTURELData.grabExcessHorizontalSpace = true;
					if(!decore) {
						tfLIBELLE_DOCUMENT = new Text(paEntete, SWT.BORDER);
						tfLIBELLE_DOCUMENT.setLayoutData(tfLIBELLE_FACTURELData);
						tfLIBELLE_DOCUMENT.setText("LIBELLE_FACTURE");
					} else {
						fieldLIBELLE_DOCUMENT = new DecoratedField(paEntete, SWT.BORDER, new TextControlCreator());
						tfLIBELLE_DOCUMENT = (Text)fieldLIBELLE_DOCUMENT.getControl();
						fieldLIBELLE_DOCUMENT.getLayoutControl().setLayoutData(tfLIBELLE_FACTURELData);
					}
				}
				{
					laTTC = new Label(paEntete, SWT.NONE);
					GridData laTTCLData = new GridData();
					laTTCLData.horizontalAlignment = GridData.END;
					laTTCLData.grabExcessHorizontalSpace = true;
					laTTC.setLayoutData(laTTCLData);
					laTTC.setText("TTC");
				}
				{
					if(!decore) {
						cbTTC = new Button(paEntete, SWT.CHECK | SWT.LEFT);
					} else {
						fieldTTC = new DecoratedField(paEntete, SWT.CHECK | SWT.LEFT, new ButtonControlCreator());
						cbTTC = (Button)fieldTTC.getControl();
					}
				}
	
				{
					laDATE_LIV_DOCUMENT = new Label(paEntete, SWT.NONE);
					GridData laDATE_LIV_FACTURELData = new GridData();
					laDATE_LIV_FACTURELData.horizontalAlignment = GridData.END;
					laDATE_LIV_FACTURELData.verticalAlignment = GridData.BEGINNING;
					//laDATE_LIV_FACTURELData.verticalAlignment = GridData.FILL;
					laDATE_LIV_DOCUMENT.setLayoutData(laDATE_LIV_FACTURELData);
					laDATE_LIV_DOCUMENT.setText("Date livraison");
				}
				{
					GridData dateTimeDATE_LIV_FACTURELData = new GridData();
					dateTimeDATE_LIV_FACTURELData.widthHint = 95;
					dateTimeDATE_LIV_FACTURELData.verticalAlignment = GridData.FILL;

				if(!decore) {
					dateTimeDATE_LIV_DOCUMENT = new DateTime(paEntete, SWT.BORDER | SWT.DROP_DOWN);
					dateTimeDATE_LIV_DOCUMENT.setLayoutData(dateTimeDATE_LIV_FACTURELData);
					} else {
						fieldDATE_LIV_DOCUMENT = new DecoratedField(paEntete, SWT.BORDER | SWT.DROP_DOWN, new DateTimeControlCreator());
						dateTimeDATE_LIV_DOCUMENT = (DateTime)fieldDATE_LIV_DOCUMENT.getControl();
					fieldDATE_LIV_DOCUMENT.getLayoutControl().setLayoutData(dateTimeDATE_LIV_FACTURELData);
					}
//				dateTimeDATE_LIV_DOCUMENT.setPattern("dd/MM/yyyy");						
				}
				
				
				{
					laLocalisationTVA = new Label(paEntete, SWT.NONE);
					GridData laTTCLData = new GridData();
					//laTTCLData.horizontalAlignment = GridData.END;
					laTTCLData.grabExcessHorizontalSpace = true;
					laLocalisationTVA.setLayoutData(laTTCLData);
					laLocalisationTVA.setText("Localisation TVA");
				}
				{
					GridData comboTVALData = new GridData();
					comboTVALData.widthHint = 180;
//					if(!decore) {
						comboLocalisationTVA = new Combo(paEntete, SWT.DROP_DOWN | SWT.READ_ONLY);
						comboLocalisationTVA.setLayoutData(comboTVALData);
//					} else {
//						fieldTTC = new DecoratedField(paEntete, SWT.CHECK | SWT.LEFT, new ButtonControlCreator());
//						comboLocalisationTVA = (Combo)fieldTTC.getControl();
//					}
				}

			{
				GridData paBtnSuppLData = new GridData();
				paBtnSuppLData.grabExcessHorizontalSpace = true;
				paBtnSuppLData.horizontalAlignment = GridData.FILL;
				paBtnSuppLData.horizontalSpan = 5;
				paBtnSuppLData.verticalSpan = 5;
				paBtnSuppLData.minimumWidth = 150;
				paBtnSuppLData.verticalAlignment = GridData.FILL;
				paBtnSupp = new Group(this, SWT.NONE);
				GridLayout paBtnSuppLayout = new GridLayout();
				paBtnSuppLayout.numColumns = 4;
				paBtnSuppLayout.marginHeight = 0;
				paBtnSuppLayout.marginWidth = 0;
				paBtnSuppLayout.verticalSpacing = 0;
				paBtnSupp.setLayout(paBtnSuppLayout);
				paBtnSupp.setLayoutData(paBtnSuppLData);
				{
					btnFicheTiers = new Button(paBtnSupp, SWT.PUSH);
					GridData button4LData = new GridData();
					button4LData.horizontalAlignment = GridData.FILL;
					button4LData.verticalAlignment = GridData.FILL;
					btnFicheTiers.setLayoutData(button4LData);
				}
				{
					btnGenDoc = new Button(paBtnSupp, SWT.PUSH);
					GridData button3LData = new GridData();
					button3LData.horizontalAlignment = GridData.FILL;
					button3LData.verticalAlignment = GridData.FILL;
					btnGenDoc.setLayoutData(button3LData);
				}
				{
					btnGenModel = new Button(paBtnSupp, SWT.PUSH);
					GridData button2LData = new GridData();
					button2LData.verticalAlignment = GridData.FILL;
					button2LData.horizontalAlignment = GridData.FILL;
					btnGenModel.setLayoutData(button2LData);
				}
				{
					btnAutres = new Button(paBtnSupp, SWT.PUSH);
					GridData button1LData = new GridData();
					button1LData.verticalAlignment = GridData.FILL;
					button1LData.horizontalAlignment = GridData.FILL;
					btnAutres.setLayoutData(button1LData);
				}
			}
			
			{
				laMessage = new Label(this, SWT.NONE);
				GridData laMessageLData = new GridData();
				laMessageLData.horizontalAlignment = GridData.FILL;
				laMessageLData.grabExcessHorizontalSpace = true;
				laMessage.setLayoutData(laMessageLData);
			}
			{
				GridData expandBarLData = new GridData();
				expandBarLData.horizontalAlignment = GridData.FILL;
				expandBarLData.verticalAlignment = GridData.FILL;
				expandBarLData.grabExcessVerticalSpace = true;
				expandBar = new ExpandBar(this, SWT.V_SCROLL);
				expandBar.setLayoutData(expandBarLData);
			}
			{
				GridData paBtnAvecAssistantLData = new GridData();
				paBtnAvecAssistantLData.verticalAlignment = GridData.END;
				paBtnAvecAssistantLData.horizontalAlignment = GridData.CENTER;
				paBtnAvecAssistant = new PaBtnAvecAssistant(this, SWT.NONE);
				paBtnAvecAssistant.setLayoutData(paBtnAvecAssistantLData);
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Group getPaEntete() {
		return paEntete;
	}

	public Label getLaCODE_DOCUMENT() {
		return laCODE_DOCUMENT;
	}

	public Text getTfCODE_DOCUMENT() {
		return tfCODE_DOCUMENT;
	}

	public Label getLaDATE_DOCUMENT() {
		return laDATE_DOCUMENT;
	}







	public Label getLaDATE_LIV_DOCUMENT() {
		return laDATE_LIV_DOCUMENT;
	}



	public Label getLaLIBELLE_DOCUMENT() {
		return laLIBELLE_DOCUMENT;
	}

	public Text getTfLIBELLE_DOCUMENT() {
		return tfLIBELLE_DOCUMENT;
	}

	public Label getLaCODE_TIERS() {
		return laCODE_TIERS;
	}

	public Text getTfCODE_TIERS() {
		return tfCODE_TIERS;
	}

	public Label getLaTTC() {
		return laTTC;
	}

	public Button getCbTTC() {
		return cbTTC;
	}



	public DateTime getDateTimeDATE_DOCUMENT() {
		return dateTimeDATE_DOCUMENT;
	}

	public DateTime getDateTimeDATE_LIV_DOCUMENT() {
		return dateTimeDATE_LIV_DOCUMENT;
	}


	public Label getLaMessage() {
		return laMessage;
	}

	public DecoratedField getFieldCODE_DOCUMENT() {
		return fieldCODE_DOCUMENT;
	}

	public DecoratedField getFieldCODE_TIERS() {
		return fieldCODE_TIERS;
	}

	public DecoratedField getFieldDATE_DOCUMENT() {
		return fieldDATE_DOCUMENT;
	}

	public DecoratedField getFieldDATE_LIV_DOCUMENT() {
		return fieldDATE_LIV_DOCUMENT;
	}

	public DecoratedField getFieldLIBELLE_DOCUMENT() {
		return fieldLIBELLE_DOCUMENT;
	}

	public DecoratedField getFieldTTC() {
		return fieldTTC;
	}

	public PaBtnAvecAssistant getPaBtnAvecAssistant() {
		return paBtnAvecAssistant;
	}

	public void setPaBtnAvecAssistant(PaBtnAvecAssistant paBtnAvecAssistant) {
		this.paBtnAvecAssistant = paBtnAvecAssistant;
	}
	
	public ExpandBar getExpandBar() {
		return expandBar;
	}
	
	public Group getPaBtnSupp() {
		return paBtnSupp;
	}

	public Button getBtnAideDocument() {
		return btnAideDocument;
	}

	public Button getBtnAideTiers() {
		return btnAideTiers;
	}

	public Button getBtnFicheTiers() {
		return btnFicheTiers;
	}

	public Button getBtnGenDoc() {
		return btnGenDoc;
	}

	public Button getBtnGenModel() {
		return btnGenModel;
	}

	public Button getBtnAutres() {
		return btnAutres;
	}

	public Text getTfNOM_TIERS() {
		return tfNOM_TIERS;
	}

	public Label getLaNOM_TIERS() {
		return laNOM_TIERS;
	}

	public DecoratedField getFieldNOM_TIERS() {
		return fieldNOM_TIERS;
	}

	public Label getLaLocalisationTVA() {
		return laLocalisationTVA;
	}

	public Combo getComboLocalisationTVA() {
		return comboLocalisationTVA;
	}

}

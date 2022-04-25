package fr.legrain.document.ecran;
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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

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
public class PaDocumentEditable extends fr.legrain.lib.gui.DefaultFrameFormulaireSWTTree {

	private Composite paCorpsFormulaire;
	private Label laCode;
	private Label laLibelle;
	private Label laCheminDocument;
	private Label laCheminCorresp;
	private Label laType;
	private Label laDefaut;

	private Text tfCode;
	private Text tfLibelle;
	private Text tfCheminDocument;
	private Text tfCheminCorresp;
//	private Combo cbType;
	private Button cbDefaut;
	private Button btnChemin_Corresp;
	private Button btnChemin_Model;
	private Button btnOuvrir;
	private Table tableTypeDoc;

	private DecoratedField fieldCODE_C_PAIEMENT;
	private DecoratedField fieldLIB_C_PAIEMENT;
	private DecoratedField fieldREPORT_C_PAIEMENT;	
	private DecoratedField fieldFIN_MOIS_C_PAIEMENT;

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
		PaDocumentEditable inst = new PaDocumentEditable(shell, SWT.NULL);
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

	public PaDocumentEditable(org.eclipse.swt.widgets.Composite parent, int style) {
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
				composite1Layout.numColumns = 4;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.grabExcessVerticalSpace = true;
				composite1LData.horizontalSpan = 3;
				composite1LData.grabExcessHorizontalSpace = true;
				paCorpsFormulaire.setLayoutData(composite1LData);
				paCorpsFormulaire.setLayout(composite1Layout);
				
//				{
//					laType = new Label(paCorpsFormulaire, SWT.NONE);
//					laType.setText("Type");
//				}
//				{
//					GridData cbTypeCPaiementLData = new GridData();
//					cbTypeCPaiementLData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
//					cbTypeCPaiementLData.grabExcessHorizontalSpace = true;
//					cbTypeCPaiementLData.horizontalSpan = 3;
//					cbTypeCPaiementLData.widthHint = 156;
//					cbTypeCPaiementLData.verticalAlignment = GridData.FILL;
////					if(!decore) {
//						cbType = new Combo(paCorpsFormulaire, SWT.BORDER | SWT.READ_ONLY);
//						cbType.setLayoutData(cbTypeCPaiementLData);
////					} else {					
////					fieldCODE_C_PAIEMENT = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
////					cbTypeCPaiement = (Text)fieldCODE_C_PAIEMENT.getControl();
////					fieldCODE_C_PAIEMENT.getLayoutControl().setLayoutData(tfCODE_C_PAIEMENTLData);
////					}
//				}

				{
					laCode = new Label(paCorpsFormulaire, SWT.NONE);
					laCode.setText("Code");
				}
				{
					GridData tfCODE_C_PAIEMENTLData = new GridData();
					tfCODE_C_PAIEMENTLData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
					tfCODE_C_PAIEMENTLData.grabExcessHorizontalSpace = true;
					tfCODE_C_PAIEMENTLData.widthHint = 156;
					tfCODE_C_PAIEMENTLData.horizontalSpan = 3;
					tfCODE_C_PAIEMENTLData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfCode = new Text(paCorpsFormulaire, SWT.BORDER);
						tfCode.setLayoutData(tfCODE_C_PAIEMENTLData);
					} else {					
					fieldCODE_C_PAIEMENT = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfCode = (Text)fieldCODE_C_PAIEMENT.getControl();
					fieldCODE_C_PAIEMENT.getLayoutControl().setLayoutData(tfCODE_C_PAIEMENTLData);
					}
				}
				{
					laLibelle = new Label(paCorpsFormulaire, SWT.NONE);
					laLibelle.setText("Libellé");
				}
				{
					GridData tfLIB_C_PAIEMENTLData = new GridData();
					tfLIB_C_PAIEMENTLData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
					tfLIB_C_PAIEMENTLData.grabExcessHorizontalSpace = true;
					tfLIB_C_PAIEMENTLData.widthHint = 156;
					tfLIB_C_PAIEMENTLData.horizontalSpan = 3;
					tfLIB_C_PAIEMENTLData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfLibelle = new Text(paCorpsFormulaire, SWT.BORDER);
						tfLibelle.setLayoutData(tfLIB_C_PAIEMENTLData);
					} else {					
					fieldLIB_C_PAIEMENT = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfLibelle = (Text)fieldLIB_C_PAIEMENT.getControl();
					fieldLIB_C_PAIEMENT.getLayoutControl().setLayoutData(tfLIB_C_PAIEMENTLData);
					}
				}
				{
					laCheminDocument = new Label(paCorpsFormulaire, SWT.NONE);
					laCheminDocument.setText("Chemin document");
				}
				{
					GridData tfREPORT_C_PAIEMENTLData = new GridData();
					//tfREPORT_C_PAIEMENTLData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
//					tfREPORT_C_PAIEMENTLData.grabExcessHorizontalSpace = true;
					//tfREPORT_C_PAIEMENTLData.horizontalAlignment = GridData.BEGINNING;
					tfREPORT_C_PAIEMENTLData.widthHint = 600;
					//tfREPORT_C_PAIEMENTLData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfCheminDocument = new Text(paCorpsFormulaire, SWT.BORDER);
						tfCheminDocument.setLayoutData(tfREPORT_C_PAIEMENTLData);
					} else {					
					fieldREPORT_C_PAIEMENT = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfCheminDocument = (Text)fieldREPORT_C_PAIEMENT.getControl();
					fieldREPORT_C_PAIEMENT.getLayoutControl().setLayoutData(tfREPORT_C_PAIEMENTLData);
					}
				}
				{
					btnChemin_Model = new Button(paCorpsFormulaire, SWT.PUSH /*| SWT.CENTER*/);
					GridData btnChemin_CorrespLData = new GridData();
					btnChemin_CorrespLData.horizontalAlignment = GridData.BEGINNING;
//					btnChemin_CorrespLData.horizontalSpan =2;
					btnChemin_Model.setLayoutData(btnChemin_CorrespLData);
					btnChemin_Model.setText("Parcourir...");
				}
				{
					btnOuvrir = new Button(paCorpsFormulaire, SWT.PUSH /*| SWT.CENTER*/);
					GridData btnChemin_CorrespLData = new GridData();
					btnChemin_CorrespLData.horizontalAlignment = GridData.BEGINNING;
//					btnChemin_CorrespLData.horizontalSpan =2;
					btnOuvrir.setLayoutData(btnChemin_CorrespLData);
					btnOuvrir.setText("Ouvrir...");
				}
				{
					laCheminCorresp = new Label(paCorpsFormulaire, SWT.NONE);
					laCheminCorresp.setText("Chemin fichier correspondance");
				}
				{
					GridData tfFIN_MOIS_C_PAIEMENTLData = new GridData();
//					tfFIN_MOIS_C_PAIEMENTLData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_BEGINNING;
//					tfFIN_MOIS_C_PAIEMENTLData.grabExcessHorizontalSpace = true;
//					tfFIN_MOIS_C_PAIEMENTLData.horizontalAlignment = GridData.BEGINNING;
					tfFIN_MOIS_C_PAIEMENTLData.widthHint = 600;
//					tfFIN_MOIS_C_PAIEMENTLData.verticalAlignment = GridData.FILL;
					if(!decore) {
						tfCheminCorresp = new Text(paCorpsFormulaire, SWT.BORDER);
						tfCheminCorresp.setLayoutData(tfFIN_MOIS_C_PAIEMENTLData);
					} else {					fieldFIN_MOIS_C_PAIEMENT = new DecoratedField(paCorpsFormulaire, SWT.BORDER, new TextControlCreator());
					tfCheminCorresp = (Text)fieldFIN_MOIS_C_PAIEMENT.getControl();
					fieldFIN_MOIS_C_PAIEMENT.getLayoutControl().setLayoutData(tfFIN_MOIS_C_PAIEMENTLData);
					}
				}
				{
					btnChemin_Corresp = new Button(paCorpsFormulaire, SWT.PUSH /*| SWT.CENTER*/);
					GridData btnChemin_CorrespLData = new GridData();
					btnChemin_CorrespLData.horizontalSpan = 2;
					btnChemin_CorrespLData.horizontalAlignment = GridData.BEGINNING;
					//btnChemin_CorrespLData.grabExcessHorizontalSpace = true;
					btnChemin_Corresp.setLayoutData(btnChemin_CorrespLData);
					btnChemin_Corresp.setText("Parcourir...");
				}
				{
					laDefaut = new Label(paCorpsFormulaire, SWT.NONE);
					laDefaut.setText("Présélectionné");
				}
				{
					GridData cbCPaiementDocDefautLData = new GridData();
					cbCPaiementDocDefautLData.widthHint = 156;
					cbCPaiementDocDefautLData.verticalAlignment = GridData.FILL;
					
					cbDefaut = new Button(paCorpsFormulaire, SWT.CHECK);
					cbDefaut.setLayoutData(cbCPaiementDocDefautLData);
					
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
			this.setSize(682, 384);
			GridData paGrilleLData = new GridData();
			paGrilleLData.grabExcessVerticalSpace = true;
			paGrilleLData.verticalAlignment = GridData.FILL;
			paGrilleLData.horizontalAlignment = GridData.FILL;
			paGrilleLData.grabExcessHorizontalSpace = true;
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
			//super.getGrille().setLayoutData(grilleLData);
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DecoratedField getFieldCODE_C_PAIEMENT() {
		return fieldCODE_C_PAIEMENT;
	}

	public DecoratedField getFieldLIB_C_PAIEMENT() {
		return fieldLIB_C_PAIEMENT;
	}

	public DecoratedField getFieldREPORT_C_PAIEMENT() {
		return fieldREPORT_C_PAIEMENT;
	}

	public Label getLaCode() {
		return laCode;
	}

	public Label getLaLibelle() {
		return laLibelle;
	}

	public Label getLaCheminDocument() {
		return laCheminDocument;
	}

	public Composite getPaCorpsFormulaire() {
		return paCorpsFormulaire;
	}

	public Text getTfCode() {
		return tfCode;
	}

	public Text getTfLibelle() {
		return tfLibelle;
	}

	public Text getTfCheminDocument() {
		return tfCheminDocument;
	}

	public void setFieldCODE_C_PAIEMENT(DecoratedField fieldCODE_BANQUE) {
		this.fieldCODE_C_PAIEMENT = fieldCODE_BANQUE;
	}

	public void setFieldLIB_C_PAIEMENT(DecoratedField fieldLIBC_BANQUE) {
		this.fieldLIB_C_PAIEMENT = fieldLIBC_BANQUE;
	}

	public void setFieldREPORT_C_PAIEMENT(DecoratedField fieldLIBL_BANQUE) {
		this.fieldREPORT_C_PAIEMENT = fieldLIBL_BANQUE;
	}

	public void setLaCode(Label laCODE_BANQUE) {
		this.laCode = laCODE_BANQUE;
	}

	public void setLaLibelle(Label laLIBC_BANQUE) {
		this.laLibelle = laLIBC_BANQUE;
	}

	public void setLaCheminDocument(Label laLIBL_BANQUE) {
		this.laCheminDocument = laLIBL_BANQUE;
	}

	public void setPaCorpsFormulaire(Composite paCorpsFormulaire) {
		this.paCorpsFormulaire = paCorpsFormulaire;
	}

	public void setTfCode(Text tfCODE_BANQUE) {
		this.tfCode = tfCODE_BANQUE;
	}

	public void setTfLibelle(Text tfLIBC_BANQUE) {
		this.tfLibelle = tfLIBC_BANQUE;
	}

	public void setTfCheminDocument(Text tfLIBL_BANQUE) {
		this.tfCheminDocument = tfLIBL_BANQUE;
	}

	public Label getLaCheminCorresp() {
		return laCheminCorresp;
	}

	public Text getTfCheminCorresp() {
		return tfCheminCorresp;
	}

	public DecoratedField getFieldFIN_MOIS_C_PAIEMENT() {
		return fieldFIN_MOIS_C_PAIEMENT;
	}

	public Label getLaType() {
		return laType;
	}

//	public Combo getCbType() {
//		return cbType;
//	}

	public Label getLaDefaut() {
		return laDefaut;
	}

	public Button getCbDefaut() {
		return cbDefaut;
	}

	public Button getBtnChemin_Corresp() {
		return btnChemin_Corresp;
	}

	public Button getBtnChemin_Model() {
		return btnChemin_Model;
	}

	public Button getBtnOuvrir() {
		return btnOuvrir;
	}

	public Table getTableTypeDoc() {
		return tableTypeDoc;
	}

}

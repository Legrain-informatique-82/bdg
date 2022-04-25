package fr.legrain.creationDocMultipleTiers.ecrans;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.fieldassist.TextControlCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
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
public class PaSelectionLigneMultiTiers extends fr.legrain.lib.gui.DefaultFrameFormulaireSWTSimpleBtnCoteValiderSepare {



	private Composite paCorpsFormulaire;
	private Label laLETTRE;
	private Combo cbListeVersion;
	private Group groupParam;
	private Text tfLETTRE;
	//private Text tfETIQUETTE;
	private Label laETTIQUETTE;
	private Button btnImprimerEtiquette;
	private Button btnModifierEtiquette;
	private Button btnChemin_Model;
	private Button btnOuvrir_Model;
	private Combo cbListeParamEtiquette;
	
	public Button getBtnOuvrir_Model() {
		return btnOuvrir_Model;
	}

	public boolean isDecore() {
		return decore;
	}

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
		PaSelectionLigneMultiTiers inst = new PaSelectionLigneMultiTiers(shell, SWT.NULL);
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

	public PaSelectionLigneMultiTiers(Composite parent, int style,
			int ordreComposite, int tableStyle) {
		super(parent, style, ordreComposite, tableStyle);
		initGUI();
	}

	public PaSelectionLigneMultiTiers(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			{
				paCorpsFormulaire = new Composite(super.getPaFomulaire(), SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 4;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.grabExcessVerticalSpace = true;
				paCorpsFormulaire.setLayoutData(composite1LData);
				paCorpsFormulaire.setLayout(composite1Layout);
				{
					groupParam = new Group(paCorpsFormulaire, SWT.NONE);
					GridLayout groupParamLayout = new GridLayout();
					groupParamLayout.numColumns =3;
					groupParam.setLayout(groupParamLayout);
					GridData groupParamLData = new GridData();
					groupParamLData.horizontalSpan = 4;
					groupParam.setLayoutData(groupParamLData);
					groupParam.setText("Indiquez la version du logiciel de publipostage que vous souhaitez utiliser");
					{
						cbListeVersion = new Combo(groupParam, SWT.NONE);
					}
				}
				{
					laLETTRE = new Label(paCorpsFormulaire, SWT.NONE);
					laLETTRE.setText("Document choisi");
				}
				{
					GridData tfLETTRELData = new GridData();
					tfLETTRELData.horizontalAlignment = GridData.FILL;
					tfLETTRELData.horizontalAlignment = GridData.FILL;
					tfLETTRELData.horizontalSpan = 4;
					tfLETTRELData.heightHint = 13;
					tfLETTRELData.grabExcessHorizontalSpace = true;
					tfLETTRE = new Text(paCorpsFormulaire, SWT.BORDER);
					tfLETTRE.setLayoutData(tfLETTRELData);
					
				}
				{
					GridData btnChemin_ModelLData = new GridData();
					btnChemin_ModelLData.heightHint = 23;
					btnChemin_ModelLData.horizontalAlignment = GridData.END;
					btnChemin_ModelLData.horizontalSpan = 3;
					btnChemin_ModelLData.widthHint = 94;
					//btnChemin_ModelLData.grabExcessHorizontalSpace = true;
					btnChemin_Model = new Button(paCorpsFormulaire, SWT.PUSH | SWT.CENTER);
					btnChemin_Model.setLayoutData(btnChemin_ModelLData);
					btnChemin_Model.setText("Parcourir...");
				}	
				{
					GridData btnChemin_OuvrirLData = new GridData();
					btnChemin_OuvrirLData.horizontalAlignment = GridData.END;
					//btnChemin_ModelLData.grabExcessHorizontalSpace = true;
					btnChemin_OuvrirLData.heightHint = 23;
					btnChemin_OuvrirLData.widthHint = 94;
					btnOuvrir_Model = new Button(paCorpsFormulaire, SWT.PUSH | SWT.CENTER);
					btnOuvrir_Model.setLayoutData(btnChemin_OuvrirLData);
					btnOuvrir_Model.setText("Ouvrir");
				}
				{
					laETTIQUETTE = new Label(paCorpsFormulaire, SWT.NONE);
					GridData laETTIQUETTELData = new GridData();
					laETTIQUETTELData.widthHint = 189;
					laETTIQUETTELData.heightHint = 13;
					laETTIQUETTELData.horizontalSpan = 4;
					laETTIQUETTE.setLayoutData(laETTIQUETTELData);
					laETTIQUETTE.setText("Choix du paramètre d'étiquette : ");
				}
				
				{
					cbListeParamEtiquette = new Combo(paCorpsFormulaire, SWT.NONE);
					GridData cbListeParamEtiquetteLData = new GridData();
					//cbListeParamEtiquetteLData.heightHint = 13;
					cbListeParamEtiquetteLData.horizontalSpan = 2;
					cbListeParamEtiquetteLData.horizontalAlignment = GridData.FILL;
					cbListeParamEtiquetteLData.grabExcessHorizontalSpace = true;
					cbListeParamEtiquetteLData.heightHint = 21;
					cbListeParamEtiquette.setLayoutData(cbListeParamEtiquetteLData);
				}
				
				{
					GridData btnImprimerEtiquetteLData = new GridData();
					btnImprimerEtiquetteLData.widthHint = 94;
					btnImprimerEtiquetteLData.horizontalAlignment = GridData.END;
					btnImprimerEtiquette = new Button(paCorpsFormulaire, SWT.PUSH | SWT.CENTER);
					btnImprimerEtiquette.setLayoutData(btnImprimerEtiquetteLData);
					btnImprimerEtiquette.setText("Imprimer");
				}	
				
				{
					GridData btnModifierEtiquetteLData = new GridData();
					btnModifierEtiquetteLData.widthHint = 94;
					btnModifierEtiquetteLData.horizontalAlignment = GridData.END;
					btnModifierEtiquette = new Button(paCorpsFormulaire, SWT.PUSH | SWT.CENTER);
					btnModifierEtiquette.setLayoutData(btnModifierEtiquetteLData);
					btnModifierEtiquette.setText("Créer/Modifier");
				}
			}

			GridLayout thisLayout = new GridLayout();
			this.setLayout(thisLayout);
			thisLayout.numColumns = 2;
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

	public Label getLaLETTRE() {
		return laLETTRE;
	}

	public Text getTfLETTRE() {
		return tfLETTRE;
	}

//	public Text getTfETIQUETTE() {
//		return tfETIQUETTE;
//	}

	public Label getLaETTIQUETTE() {
		return laETTIQUETTE;
	}

	public Button getBtnImprimerEtiquette() {
		return btnImprimerEtiquette;
	}

	public Button getBtnChemin_Model() {
		return btnChemin_Model;
	}

	public Combo getCbListeVersion() {
		return cbListeVersion;
	}

	public Combo getCbListeParamEtiquette() {
		return cbListeParamEtiquette;
	}

	public void setCbListeParamEtiquette(Combo cbListeParamEtiquette) {
		this.cbListeParamEtiquette = cbListeParamEtiquette;
	}

	public Group getGroupParam() {
		return groupParam;
	}

	public Button getBtnModifierEtiquette() {
		return btnModifierEtiquette;
	}













}

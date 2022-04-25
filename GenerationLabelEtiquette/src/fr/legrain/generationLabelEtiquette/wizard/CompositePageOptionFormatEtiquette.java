package fr.legrain.generationLabelEtiquette.wizard;


import net.miginfocom.swt.MigLayout;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.SWT;

import com.cloudgarden.resource.SWTResourceManager;



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
public class CompositePageOptionFormatEtiquette extends org.eclipse.swt.widgets.Composite {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	private Group grpProprityEtiquette;

	private Group grpFormatModelLabels;
	
	
	private Text textFormatEtiquette;
	private Button btCheckBoxGras;
	private Label laDecalage;
	private Group groupParam;
	private Text tfQuantite;
	private Label laQuantite;
	private Text tfDecalage;
	private Button btCheckBoxSave;
	private Text textNameEtiquette;
	private Label labelSaveEtiquette;
	private Group grpSaveEtiquette;
	private Label labelSizeEtiquette;
	private Combo comboSizeEtiquette;
	private Button btCheckBoxItalique;

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
		CompositePageOptionFormatEtiquette inst = new CompositePageOptionFormatEtiquette(shell, SWT.NULL);
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

	public CompositePageOptionFormatEtiquette(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.makeColumnsEqualWidth = true;
			this.setLayout(thisLayout);
			// here
			{
				grpProprityEtiquette = new Group(this, SWT.NONE);
				GridLayout grpParametrePapierLHLayout = new GridLayout();
				grpParametrePapierLHLayout.numColumns = 4;
				grpProprityEtiquette.setLayout(grpParametrePapierLHLayout);
				GridData grpParametrePapierLHData = new GridData();
				grpParametrePapierLHData.horizontalAlignment = GridData.FILL;
				grpParametrePapierLHData.grabExcessHorizontalSpace = true;
				grpProprityEtiquette.setLayoutData(grpParametrePapierLHData);
				grpProprityEtiquette.setText("Police");
				grpProprityEtiquette.setFont(SWTResourceManager.getFont("Sans", 8, 1, false, false));
				{
					btCheckBoxGras = new Button(grpProprityEtiquette, SWT.CHECK | SWT.LEFT);
					btCheckBoxGras.setText("Gras");
					GridData btCheckBoxGrasLData = new GridData();
					btCheckBoxGrasLData.horizontalSpan = 1;
					btCheckBoxGrasLData.horizontalAlignment = GridData.CENTER;
					btCheckBoxGrasLData.grabExcessHorizontalSpace = true;
					btCheckBoxGras.setLayoutData(btCheckBoxGrasLData);
					btCheckBoxGras.setFont(SWTResourceManager.getFont("Sans", 10, 1, false, false));
				}
				{
					btCheckBoxItalique = new Button(grpProprityEtiquette, SWT.CHECK | SWT.LEFT);
					btCheckBoxItalique.setText("Italique");
					GridData btCheckBoxItaliqueLData = new GridData();
					btCheckBoxItaliqueLData.horizontalSpan = 1;
					btCheckBoxItaliqueLData.horizontalAlignment = GridData.CENTER;
					btCheckBoxItaliqueLData.grabExcessHorizontalSpace = true;
					btCheckBoxItalique.setLayoutData(btCheckBoxItaliqueLData);
					btCheckBoxItalique.setFont(SWTResourceManager.getFont("Sans", 10, 2, false, false));
				}
				{
					labelSizeEtiquette = new Label(grpProprityEtiquette, SWT.NONE);
					labelSizeEtiquette.setText("Taille");
					GridData labelSizeEtiquetteLData = new GridData();
					labelSizeEtiquette.setLayoutData(labelSizeEtiquetteLData);
				}
				{
					comboSizeEtiquette = new Combo(grpProprityEtiquette, SWT.NONE);
					GridData comboSizeEtiquetteLData = new GridData();
					comboSizeEtiquetteLData.heightHint = 25;
					comboSizeEtiquetteLData.grabExcessHorizontalSpace = true;
					comboSizeEtiquetteLData.widthHint = 68;
					comboSizeEtiquetteLData.horizontalSpan = 1;
					comboSizeEtiquette.setLayoutData(comboSizeEtiquetteLData);
					comboSizeEtiquette.setEnabled(false);
				}
			}
			{
				grpFormatModelLabels = new Group(this, SWT.NONE);
				MigLayout migLayoutGrpFormatModelLabels = new MigLayout("");
				grpFormatModelLabels.setLayout(migLayoutGrpFormatModelLabels);
				
				grpFormatModelLabels.setText("Disposition des champs sur l'étiquette");
				GridData grpFormatModelLabelsLData = new GridData();
				grpFormatModelLabelsLData.grabExcessHorizontalSpace = true;
				grpFormatModelLabelsLData.horizontalAlignment = GridData.FILL;
				grpFormatModelLabelsLData.verticalAlignment = GridData.FILL;
				grpFormatModelLabels.setLayoutData(grpFormatModelLabelsLData);
				grpFormatModelLabels.setFont(SWTResourceManager.getFont("Sans", 8, 1, false, false));
				{
					textFormatEtiquette = new Text(grpFormatModelLabels, SWT.MULTI | SWT.WRAP | SWT.BORDER /*| SWT.V_SCROLL*/);
					
					//textFormatEtiquette.setLayoutData("width 10cm!,height 10cm!");
					
				}
				
//				GridLayout grpFormatModelLabelsLayout = new GridLayout();
//				grpFormatModelLabelsLayout.numColumns = 1;
//				grpFormatModelLabels.setLayout(grpFormatModelLabelsLayout);
//				GridData grpFormatModelLabelsData = new GridData();
//				grpFormatModelLabelsData.horizontalAlignment = GridData.FILL;
//				grpFormatModelLabelsData.grabExcessHorizontalSpace = true;
//				grpFormatModelLabels.setLayoutData(grpFormatModelLabelsData);
//				grpFormatModelLabels.setText("Présentation Etiquette");
//				grpFormatModelLabels.setFont(SWTResourceManager.getFont("Sans", 8, 1, false, false));
//				{
//					textFormatEtiquette = new Text(grpFormatModelLabels, SWT.MULTI | SWT.WRAP | SWT.BORDER);
//					GridData textFormatEtiquetteLData = new GridData();
//					textFormatEtiquetteLData.horizontalAlignment = GridData.CENTER;
//					textFormatEtiquette.setLayoutData(textFormatEtiquetteLData);
//				}
			}
			{
				GridData groupParamLData = new GridData();
				groupParamLData.grabExcessHorizontalSpace = true;
				groupParamLData.horizontalAlignment = GridData.FILL;
				groupParamLData.verticalAlignment = GridData.FILL;
				groupParam = new Group(this, SWT.NONE);
				GridLayout groupParamLayout = new GridLayout();
				groupParamLayout.numColumns = 4;
				groupParam.setLayout(groupParamLayout);
				groupParam.setLayoutData(groupParamLData);
				{
					laDecalage = new Label(groupParam, SWT.NONE);
					GridData laDecalageLData = new GridData();
					laDecalage.setLayoutData(laDecalageLData);
					laDecalage.setText("Décalage");
				}
				{
					tfDecalage = new Text(groupParam, SWT.BORDER);
				}
				{
					laQuantite = new Label(groupParam, SWT.NONE);
					GridData laQuantiteLData = new GridData();
					laQuantite.setLayoutData(laQuantiteLData);
					laQuantite.setText("Quantité");
				}
				{
					GridData tfQuantiteLData = new GridData();
					tfQuantite = new Text(groupParam, SWT.BORDER);
					tfQuantite.setLayoutData(tfQuantiteLData);
				}
			}
			{
				grpSaveEtiquette = new Group(this, SWT.NONE);
				GridLayout grpFormatModelLabelsLayout = new GridLayout();
				grpFormatModelLabelsLayout.numColumns = 4;
				grpSaveEtiquette.setLayout(grpFormatModelLabelsLayout);
				GridData grpFormatModelLabelsData = new GridData();
				grpFormatModelLabelsData.horizontalAlignment = GridData.FILL;
				grpFormatModelLabelsData.grabExcessHorizontalSpace = true;
				grpSaveEtiquette.setLayoutData(grpFormatModelLabelsData);
				grpSaveEtiquette.setText("Enregistrer les paramètres");
				grpSaveEtiquette.setFont(SWTResourceManager.getFont("Sans", 8, 1, false, false));
				{
					btCheckBoxSave = new Button(grpSaveEtiquette, SWT.CHECK | SWT.LEFT);
					btCheckBoxSave.setText("Enregistrer ce paramétrage");
					GridData btCheckBoxSaveLData = new GridData();
					btCheckBoxSaveLData.horizontalSpan = 4;
					btCheckBoxSave.setLayoutData(btCheckBoxSaveLData);
					btCheckBoxSave.setFont(SWTResourceManager.getFont("Sans", 8, 1, false, false));
				}
				{
					labelSaveEtiquette = new Label(grpSaveEtiquette, SWT.NONE);
					labelSaveEtiquette.setText("Nom étiquette");
					GridData labelSaveEtiquetteData = new GridData();
					labelSaveEtiquetteData.horizontalAlignment = GridData.FILL;
					labelSaveEtiquette.setLayoutData(labelSaveEtiquetteData);
				}
				{
					textNameEtiquette = new Text(grpSaveEtiquette, SWT.BORDER);
					GridData textNameEtiquetteData = new GridData();
					textNameEtiquetteData.horizontalSpan = 2;
					textNameEtiquetteData.grabExcessHorizontalSpace = true;
					textNameEtiquetteData.horizontalAlignment = GridData.FILL;
					textNameEtiquette.setLayoutData(textNameEtiquetteData);
				}

			}
			this.layout();
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public Group getGrpParametrePapierLH() {
		return grpProprityEtiquette;
	}
	
	public void setButtonCheckBoxChoixModelLabel(
			Button buttonCheckBoxChoixModelLabel) {

	}

	public Text getTextFormatEtiquette() {
		return textFormatEtiquette;
	}

	public void setTextFormatEtiquette(Text textFormatEtiquette) {
		this.textFormatEtiquette = textFormatEtiquette;
	}

	public Group getGrpFormatModelLabels() {
		return grpFormatModelLabels;
	}
	
	public Button getBtCheckBoxGras() {
		return btCheckBoxGras;
	}

	public Button getBtCheckBoxItalique() {
		return btCheckBoxItalique;
	}
	
	public Combo getComboSizeEtiquette() {
		return comboSizeEtiquette;
	}
	
	public Text getTextNameEtiquette() {
		return textNameEtiquette;
	}
	
	public Button getBtCheckBoxSave() {
		return btCheckBoxSave;
	}
	
	public Group getGroupParam() {
		return groupParam;
	}
	
	public Label getLaDecalage() {
		return laDecalage;
	}
	
	public Text getTfDecalage() {
		return tfDecalage;
	}
	
	public Label getLaQuantite() {
		return laQuantite;
	}
	
	public Text getTfQuantite() {
		return tfQuantite;
	}

}

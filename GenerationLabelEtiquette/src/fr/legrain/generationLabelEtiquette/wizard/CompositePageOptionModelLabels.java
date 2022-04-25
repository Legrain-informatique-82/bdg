package fr.legrain.generationLabelEtiquette.wizard;


import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.custom.CCombo;
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
public class CompositePageOptionModelLabels extends org.eclipse.swt.widgets.Composite {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
	
	private Group grpParameterMarginPapier;
	private Group grpParameterPadding;
	private Group grpParametrePapierLH;
//	private Group grpSaveModelLables;
	private Group grpFileExtraction;
	private Group grpMotCleEtiquette;
	
	private Group grpFormatModelLabels;
	private Group grpEspaceModelLabels;
	private Group grpFormatFileLabels;
	private Label labelMarginRight;
	private Text textNombreRows;
	private Text textSeparatuer;
	private Label labelSeparateur;
	private Button btDeleteParaEtiquette;
//	private Button btSaveNewParaEtiquette;
	private Label labelChoixParametreEtiquette;
	private Label labelChoixFileSeparateur;
	private CCombo comboChoixParamEtiquette;
	private CCombo comboChoixFileSeparateur;
	private Button btOptionBorderCell;
	private Button btOptionFileSeparateur;
	private Button btOptionMotCle;
	private Group grpOptionEtiquette;
	private Group grpFileSeparateur;
	private Text textEspaceRows;
//	private Button buttonPathSaveEtiquette;
	private Button buttonPathFileExtraction;
	private Button buttonPathFileMotCle;
//	private Text textPathSaveEtiquette;
	private Text textPathFileExtraction;
	private Text textPathFileMotCle;
	private Text textNombreColumns;
	private Text textEspaceColumns;
	private Label labelNombreColumns;
	private Label labelNombreRows;
	private Label labelEspaceRows;
	private Label labelEspaceColumns;
	private Label labelChoixModelLabel;
	private CCombo comboModelLabels;
//	private Button buttonCheckBoxModelLabel;
	private Text textHauteurPapier;
	private Label labelHauteurPapier;
	private Text textLargeurPapier;
	private Label labelLargeurPapier;
	private Label labelExampleMargin;
	private Text textMarginBottom;
	private Label labelMarginBottom;
	private Text textMarginTop;
	private Label labelMarginTop;
	private Text textMarginRight;
	private Text textMarginLeft;
	private Label labelMarginLeft;
	private Label labelPaddingLeft;
	private Text textPaddingLeft;
	private Label labelPaddingRight;
	private Text textPaddingRight;
	private Label labelPaddingTop;
	private Text textPaddingTop;
	private Label labelPaddingBottom;
	private Text textPaddingBottom;
	private Label labelExamplePadding;
	private Button btFormatFIlePDF;
	private Button btFormatFIleDOC;
	private Label labelExampleFormatEtiquette;
	private Label labelExampleEspaceEtiquette;
	private Label labelCommentaire;

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
		CompositePageOptionModelLabels inst = new CompositePageOptionModelLabels(shell, SWT.NULL);
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

	public CompositePageOptionModelLabels(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.makeColumnsEqualWidth = true;
			thisLayout.numColumns = 2;
			this.setLayout(thisLayout);
			{
				grpOptionEtiquette = new Group(this, SWT.NONE);
				GridLayout grpOptionEtiquetteLayout = new GridLayout();
				grpOptionEtiquetteLayout.numColumns = 4;
				grpOptionEtiquette.setLayout(grpOptionEtiquetteLayout);
				grpOptionEtiquette.setText("Paramètres déjà utilisés");
				GridData grpOptionEtiquetteData = new GridData();
				grpOptionEtiquetteData.horizontalAlignment = GridData.FILL;
				grpOptionEtiquetteData.grabExcessHorizontalSpace = true;
				grpOptionEtiquetteData.horizontalSpan = 4;
				grpOptionEtiquette.setLayoutData(grpOptionEtiquetteData);
				grpOptionEtiquette.setFont(SWTResourceManager.getFont("Sans", 8, 1, false, false));
//				{
//					btOptionBorderCell = new Button(grpOptionEtiquette, SWT.CHECK | SWT.LEFT);
//					btOptionBorderCell.setText("Afficher les bordures");
//					GridData btOptionBorderCellLData = new GridData();
//					btOptionBorderCellLData.horizontalSpan = 4;
//					btOptionBorderCellLData.horizontalAlignment = GridData.FILL;
//					btOptionBorderCellLData.grabExcessHorizontalSpace = true;
//					btOptionBorderCell.setLayoutData(btOptionBorderCellLData);
//				}
				{
					labelChoixParametreEtiquette = new Label(grpOptionEtiquette, SWT.NONE);
					labelChoixParametreEtiquette.setText("Choix de paramètre d'étiquette");
					GridData labelChoixParametreEtiquetteLData = new GridData();
					labelChoixParametreEtiquetteLData.horizontalAlignment = GridData.FILL;
					labelChoixParametreEtiquetteLData.horizontalSpan = 2;
					labelChoixParametreEtiquette.setLayoutData(labelChoixParametreEtiquetteLData);
				}
				{
					comboChoixParamEtiquette = new CCombo(grpOptionEtiquette, SWT.BORDER);
					GridData comboChoixParamEtiquetteLData = new GridData();
					comboChoixParamEtiquetteLData.heightHint = 20;
					comboChoixParamEtiquetteLData.widthHint = 171;
					comboChoixParamEtiquetteLData.horizontalSpan = 2;
					comboChoixParamEtiquette.setLayoutData(comboChoixParamEtiquetteLData);
					comboChoixParamEtiquette.setEnabled(false);
					comboChoixParamEtiquette.setEditable(false);
					
				}
				{
//					btSaveNewParaEtiquette = new Button(grpOptionEtiquette, SWT.PUSH | SWT.CENTER);
//					btSaveNewParaEtiquette.setText("Enregistrer");
//					GridData btSaveNewParaEtiquetteLData = new GridData();
//					btSaveNewParaEtiquetteLData.horizontalSpan = 2;
//					btSaveNewParaEtiquetteLData.horizontalAlignment = GridData.END;
//					btSaveNewParaEtiquette.setLayoutData(btSaveNewParaEtiquetteLData);
//					btSaveNewParaEtiquette.setAlignment(SWT.RIGHT);
//					btSaveNewParaEtiquette.setEnabled(false);
				}
				{
					btDeleteParaEtiquette = new Button(grpOptionEtiquette, SWT.PUSH | SWT.CENTER);
					btDeleteParaEtiquette.setText("Supprimer");
					GridData btDeleteParaEtiquetteLData = new GridData();
					btDeleteParaEtiquetteLData.horizontalSpan = 4;
					btDeleteParaEtiquetteLData.grabExcessHorizontalSpace = true;
					btDeleteParaEtiquette.setLayoutData(btDeleteParaEtiquetteLData);
				}
			}
			{
				grpParameterMarginPapier = new Group(this, SWT.NONE);
				GridLayout grpImportLayout = new GridLayout();
				grpImportLayout.numColumns = 4;
				grpParameterMarginPapier.setLayout(grpImportLayout);
				GridData grpImportLData = new GridData();
				grpImportLData.horizontalAlignment = GridData.FILL;
				grpImportLData.grabExcessHorizontalSpace = true;
				grpParameterMarginPapier.setLayoutData(grpImportLData);
				grpParameterMarginPapier.setText("Marge de la page");
				grpParameterMarginPapier.setFont(SWTResourceManager.getFont("Sans", 8, 1, false, false));
				{
					labelMarginLeft = new Label(grpParameterMarginPapier, SWT.NONE);
					labelMarginLeft.setText("Gauche (cm)");
					GridData labelMarginLeftLData = new GridData();
					labelMarginLeftLData.horizontalAlignment = GridData.FILL;
					labelMarginLeft.setLayoutData(labelMarginLeftLData);
				}
				{
					textMarginLeft = new Text(grpParameterMarginPapier, SWT.BORDER);
					GridData textMarginLeftLData = new GridData();
					textMarginLeftLData.horizontalAlignment = GridData.FILL;
					textMarginLeftLData.grabExcessHorizontalSpace = true;
					textMarginLeft.setLayoutData(textMarginLeftLData);
				}
				{
					labelMarginRight = new Label(grpParameterMarginPapier, SWT.NONE);
					labelMarginRight.setText("Droit (cm)");
					GridData labelMarginRightLData = new GridData();
					labelMarginRightLData.horizontalAlignment = GridData.FILL;
					labelMarginRight.setLayoutData(labelMarginRightLData);
				}
				{
					textMarginRight = new Text(grpParameterMarginPapier, SWT.BORDER);
					GridData textMarginRightLData = new GridData();
					textMarginRightLData.grabExcessHorizontalSpace = true;
					textMarginRightLData.horizontalAlignment = GridData.FILL;
					textMarginRight.setLayoutData(textMarginRightLData);
				}
				{
					labelMarginTop = new Label(grpParameterMarginPapier, SWT.NONE);
					labelMarginTop.setText("Haut (cm)");
					GridData labelMarginTopLData = new GridData();
					labelMarginTopLData.horizontalAlignment = GridData.FILL;
					labelMarginTop.setLayoutData(labelMarginTopLData);
				}
				{
					textMarginTop = new Text(grpParameterMarginPapier, SWT.BORDER);
					GridData textMarginTopLData = new GridData();
					textMarginTopLData.horizontalAlignment = GridData.FILL;
					textMarginTopLData.grabExcessHorizontalSpace = true;
					textMarginTop.setLayoutData(textMarginTopLData);
				}
				{
					labelMarginBottom = new Label(grpParameterMarginPapier, SWT.NONE);
					labelMarginBottom.setText("Bas (cm)");
					GridData labelMarginBottomLData = new GridData();
					labelMarginBottomLData.horizontalAlignment = GridData.FILL;
					labelMarginBottom.setLayoutData(labelMarginBottomLData);
				}
				{
					textMarginBottom = new Text(grpParameterMarginPapier, SWT.BORDER);
					GridData textMarginBottomLData = new GridData();
					textMarginBottomLData.horizontalAlignment = GridData.FILL;
					textMarginBottomLData.grabExcessHorizontalSpace = true;
					textMarginBottom.setLayoutData(textMarginBottomLData);
				}
				{
					labelExampleMargin = new Label(grpParameterMarginPapier, SWT.NONE);
					labelExampleMargin.setText("Exemple :  Gauche : 1.5");
					GridData labelExampleMarginLData = new GridData();
					labelExampleMarginLData.horizontalAlignment = GridData.FILL;
					labelExampleMarginLData.horizontalSpan = 4;
					labelExampleMarginLData.grabExcessHorizontalSpace = true;
					labelExampleMargin.setLayoutData(labelExampleMarginLData);
					labelExampleMargin.setFont(SWTResourceManager.getFont("Sans", 8, 1, false, false));
					labelExampleMargin.setAlignment(SWT.RIGHT);
				}
			}
			// here
			{
				grpParameterPadding = new Group(this, SWT.NONE);
				GridLayout grpParameterPaddingLayout = new GridLayout();
				grpParameterPaddingLayout.numColumns = 4;
				grpParameterPadding.setLayout(grpParameterPaddingLayout);
				GridData grpParameterPaddingData = new GridData();
				grpParameterPaddingData.horizontalAlignment = GridData.FILL;
				grpParameterPaddingData.grabExcessHorizontalSpace = true;
				grpParameterPadding.setLayoutData(grpParameterPaddingData);
				grpParameterPadding.setText("Marge intérieure");
				grpParameterPadding.setFont(SWTResourceManager.getFont("Sans", 8, 1, false, false));
				{
					labelPaddingLeft = new Label(grpParameterPadding, SWT.NONE);
					labelPaddingLeft.setText("Gauche (cm)");
					GridData labelPaddingLeftData = new GridData();
					labelPaddingLeftData.horizontalAlignment = GridData.FILL;
					labelPaddingLeft.setLayoutData(labelPaddingLeftData);
				}
				{
					textPaddingLeft = new Text(grpParameterPadding, SWT.BORDER);
					GridData textPaddingLeftData = new GridData();
					textPaddingLeftData.horizontalAlignment = GridData.FILL;
					textPaddingLeftData.grabExcessHorizontalSpace = true;
					textPaddingLeft.setLayoutData(textPaddingLeftData);
				}
				{
					labelPaddingRight = new Label(grpParameterPadding, SWT.NONE);
					labelPaddingRight.setText("Droit (cm)");
					GridData labelPaddingRightData = new GridData();
					labelPaddingRightData.horizontalAlignment = GridData.FILL;
					labelPaddingRight.setLayoutData(labelPaddingRightData);
				}
				{
					textPaddingRight = new Text(grpParameterPadding, SWT.BORDER);
					GridData textPaddingRightData = new GridData();
					textPaddingRightData.grabExcessHorizontalSpace = true;
					textPaddingRightData.horizontalAlignment = GridData.FILL;
					textPaddingRight.setLayoutData(textPaddingRightData);
				}
				{
					labelPaddingTop = new Label(grpParameterPadding, SWT.NONE);
					labelPaddingTop.setText("Haut (cm)");
					GridData labelPaddingTopData = new GridData();
					labelPaddingTopData.horizontalAlignment = GridData.FILL;
					labelPaddingTop.setLayoutData(labelPaddingTopData);
				}
				{
					textPaddingTop = new Text(grpParameterPadding, SWT.BORDER);
					GridData textPaddingTopData = new GridData();
					textPaddingTopData.horizontalAlignment = GridData.FILL;
					textPaddingTopData.grabExcessHorizontalSpace = true;
					textPaddingTop.setLayoutData(textPaddingTopData);
				}
				{
					labelPaddingBottom = new Label(grpParameterPadding, SWT.NONE);
					labelPaddingBottom.setText("Bas (cm)");
					GridData labelPaddingBottomData = new GridData();
					labelPaddingBottomData.horizontalAlignment = GridData.FILL;
					labelPaddingBottom.setLayoutData(labelPaddingBottomData);
				}
				{
					textPaddingBottom = new Text(grpParameterPadding, SWT.BORDER);
					GridData textPaddingBottomData = new GridData();
					textPaddingBottomData.horizontalAlignment = GridData.FILL;
					textPaddingBottomData.grabExcessHorizontalSpace = true;
					textPaddingBottom.setLayoutData(textPaddingBottomData);
				}
				{
					labelExamplePadding = new Label(grpParameterPadding, SWT.NONE);
					labelExamplePadding.setText("Exemple :  Gauche : 1.5");
					GridData labelExamplePaddingData = new GridData();
					labelExamplePaddingData.horizontalAlignment = GridData.FILL;
					labelExamplePaddingData.horizontalSpan = 4;
					labelExamplePaddingData.grabExcessHorizontalSpace = true;
					labelExamplePadding.setLayoutData(labelExamplePaddingData);
					labelExamplePadding.setFont(SWTResourceManager.getFont("Sans", 8, 1, false, false));
					labelExamplePadding.setAlignment(SWT.RIGHT);
				}
			}
			{
				grpParametrePapierLH = new Group(this, SWT.NONE);
				GridLayout grpParametrePapierLHLayout = new GridLayout();
				grpParametrePapierLHLayout.numColumns = 4;
				grpParametrePapierLH.setLayout(grpParametrePapierLHLayout);
				GridData grpParametrePapierLHData = new GridData();
				grpParametrePapierLHData.horizontalAlignment = GridData.FILL;
				grpParametrePapierLHData.grabExcessHorizontalSpace = true;
				grpParametrePapierLHData.horizontalSpan = 2;
				grpParametrePapierLH.setLayoutData(grpParametrePapierLHData);
				grpParametrePapierLH.setText("Largeur et Hauteur (Page)");
				grpParametrePapierLH.setFont(SWTResourceManager.getFont("Sans", 8, 1, false, false));
				{
					labelLargeurPapier = new Label(grpParametrePapierLH, SWT.NONE);
					labelLargeurPapier.setText("Largeur (cm)");
					GridData labelLargeurPapierLData = new GridData();
					labelLargeurPapier.setLayoutData(labelLargeurPapierLData);
				}
				{
					textLargeurPapier = new Text(grpParametrePapierLH, SWT.BORDER);
					GridData textLargeurPapierLData = new GridData();
					textLargeurPapierLData.grabExcessHorizontalSpace = true;
					textLargeurPapierLData.horizontalAlignment = GridData.FILL;
					textLargeurPapier.setLayoutData(textLargeurPapierLData);
				}
				{
					labelHauteurPapier = new Label(grpParametrePapierLH, SWT.NONE);
					labelHauteurPapier.setText("Hauteur (cm)");
					GridData labelHauteurPapierLData = new GridData();
					labelHauteurPapier.setLayoutData(labelHauteurPapierLData);
				}
				{
					textHauteurPapier = new Text(grpParametrePapierLH, SWT.BORDER);
					GridData textHauteurPapierLData = new GridData();
					textHauteurPapierLData.horizontalAlignment = GridData.FILL;
					textHauteurPapierLData.grabExcessHorizontalSpace = true;
					textHauteurPapier.setLayoutData(textHauteurPapierLData);
				}
				{
					labelCommentaire = new Label(grpParametrePapierLH, SWT.NONE);
					labelCommentaire.setText("Exemple : Largeur : 15.5");
					GridData labelCommentaireLData = new GridData();
					labelCommentaireLData.horizontalSpan = 4;
					labelCommentaireLData.horizontalAlignment = GridData.FILL;
					labelCommentaireLData.grabExcessHorizontalSpace = true;
					labelCommentaire.setLayoutData(labelCommentaireLData);
					labelCommentaire.setAlignment(SWT.RIGHT);
					labelCommentaire.setFont(SWTResourceManager.getFont("Sans", 8, 1, false, false));
				}
				{
//					buttonCheckBoxModelLabel = new Button(grpParametrePapierLH, SWT.CHECK | SWT.LEFT);
//					buttonCheckBoxModelLabel.setText("Choix d'un format prédéfinit");
//					GridData buttonCheckBoxModelLabelLData = new GridData();
//					buttonCheckBoxModelLabelLData.horizontalSpan = 4;
//					buttonCheckBoxModelLabel.setLayoutData(buttonCheckBoxModelLabelLData);
				}
				{
					labelChoixModelLabel = new Label(grpParametrePapierLH, SWT.NONE);
					labelChoixModelLabel.setText("Format");
					GridData labelChoixModelLabelLData = new GridData();
					labelChoixModelLabelLData.horizontalAlignment = GridData.FILL;
					labelChoixModelLabel.setLayoutData(labelChoixModelLabelLData);
				}
				{
					comboModelLabels = new CCombo(grpParametrePapierLH, SWT.BORDER);
					GridData comboModelLabelsLData = new GridData();
					comboModelLabelsLData.horizontalSpan = 3;
					comboModelLabelsLData.widthHint = 98;
					comboModelLabelsLData.heightHint = 20;
					comboModelLabels.setLayoutData(comboModelLabelsLData);
					comboModelLabels.setEditable(false);
					
				}
			}
			{
				grpFormatModelLabels = new Group(this, SWT.NONE);
				GridLayout grpFormatModelLabelsLayout = new GridLayout();
				grpFormatModelLabelsLayout.numColumns = 4;
				grpFormatModelLabels.setLayout(grpFormatModelLabelsLayout);
				GridData grpFormatModelLabelsData = new GridData();
				grpFormatModelLabelsData.horizontalAlignment = GridData.FILL;
				grpFormatModelLabelsData.grabExcessHorizontalSpace = true;
				grpFormatModelLabels.setLayoutData(grpFormatModelLabelsData);
				grpFormatModelLabels.setText("Découpage de la page");
				grpFormatModelLabels.setFont(SWTResourceManager.getFont("Sans", 8, 1, false, false));
				{
					labelNombreRows = new Label(grpFormatModelLabels, SWT.NONE);
					labelNombreRows.setText("Nombre de ligne");
					GridData labelNombreRowsLData = new GridData();
					labelNombreRowsLData.horizontalAlignment = GridData.FILL;
					labelNombreRows.setLayoutData(labelNombreRowsLData);
				}
				{
					textNombreRows = new Text(grpFormatModelLabels, SWT.BORDER);
					GridData textNombreRowsLData = new GridData();
					textNombreRowsLData.horizontalAlignment = GridData.FILL;
					textNombreRowsLData.grabExcessHorizontalSpace = true;
					textNombreRows.setLayoutData(textNombreRowsLData);
				}
				{
					labelNombreColumns = new Label(grpFormatModelLabels, SWT.NONE);
					labelNombreColumns.setText("Nombre Colonnes");
					GridData labelNombreColumnsLData = new GridData();
					labelNombreColumnsLData.horizontalAlignment = GridData.FILL;
					labelNombreColumns.setLayoutData(labelNombreColumnsLData);
				}
				{
					textNombreColumns = new Text(grpFormatModelLabels, SWT.BORDER);
					GridData textNombreColumnsLData = new GridData();
					textNombreColumnsLData.horizontalAlignment = GridData.FILL;
					textNombreColumnsLData.grabExcessHorizontalSpace = true;
					textNombreColumns.setLayoutData(textNombreColumnsLData);
				}
				{
					labelExampleFormatEtiquette = new Label(grpFormatModelLabels, SWT.NONE);
					labelExampleFormatEtiquette.setText("Exemple : Nombre de ligne : 2");
					GridData labelExampleFormatEtiquetteLData = new GridData();
					labelExampleFormatEtiquetteLData.horizontalSpan = 4;
					labelExampleFormatEtiquetteLData.horizontalAlignment = GridData.FILL;
					labelExampleFormatEtiquetteLData.grabExcessHorizontalSpace = true;
					labelExampleFormatEtiquette.setLayoutData(labelExampleFormatEtiquetteLData);
					labelExampleFormatEtiquette.setAlignment(SWT.RIGHT);
					labelExampleFormatEtiquette.setFont(SWTResourceManager.getFont("Sans", 8, 1, false, false));
				}
			}
			{
				grpEspaceModelLabels = new Group(this, SWT.NONE);
				GridLayout grpEspaceModelLabelsLayout = new GridLayout();
				grpEspaceModelLabelsLayout.numColumns = 4;
				grpEspaceModelLabels.setLayout(grpEspaceModelLabelsLayout);
				GridData grpEspaceModelLabelsData = new GridData();
				grpEspaceModelLabelsData.horizontalAlignment = GridData.FILL;
				grpEspaceModelLabelsData.grabExcessHorizontalSpace = true;
				grpEspaceModelLabels.setLayoutData(grpEspaceModelLabelsData);
				grpEspaceModelLabels.setText("Espacement entre deux étiquettes");
				grpEspaceModelLabels.setFont(SWTResourceManager.getFont("Sans", 8, 1, false, false));
				{
					labelEspaceRows = new Label(grpEspaceModelLabels, SWT.NONE);
					labelEspaceRows.setText("Largeur (cm)");
					GridData labelEspaceRowsLData = new GridData();
					labelEspaceRowsLData.horizontalAlignment = GridData.FILL;
					labelEspaceRows.setLayoutData(labelEspaceRowsLData);
				}
				{
					textEspaceRows = new Text(grpEspaceModelLabels, SWT.BORDER);
					GridData textEspaceRowsData = new GridData();
					textEspaceRowsData.horizontalAlignment = GridData.FILL;
					textEspaceRowsData.grabExcessHorizontalSpace = true;
					textEspaceRows.setLayoutData(textEspaceRowsData);
				}
				{
					labelEspaceColumns = new Label(grpEspaceModelLabels, SWT.NONE);
					labelEspaceColumns.setText("Hauteur (cm)");
					GridData labelEspaceColumnsData = new GridData();
					labelEspaceColumnsData.horizontalAlignment = GridData.FILL;
					labelEspaceColumns.setLayoutData(labelEspaceColumnsData);
				}
				{
					textEspaceColumns = new Text(grpEspaceModelLabels, SWT.BORDER);
					GridData textEspaceColumnsData = new GridData();
					textEspaceColumnsData.horizontalAlignment = GridData.FILL;
					textEspaceColumnsData.grabExcessHorizontalSpace = true;
					textEspaceColumns.setLayoutData(textEspaceColumnsData);
				}
				{
					labelExampleEspaceEtiquette = new Label(grpEspaceModelLabels, SWT.NONE);
					labelExampleEspaceEtiquette.setText("Exemple : Largeur : 2");
					GridData labelExampleEspaceEtiquetteData = new GridData();
					labelExampleEspaceEtiquetteData.horizontalSpan = 4;
					labelExampleEspaceEtiquetteData.grabExcessHorizontalSpace = true;
					labelExampleEspaceEtiquetteData.horizontalAlignment = GridData.FILL;
					labelExampleEspaceEtiquette.setLayoutData(labelExampleEspaceEtiquetteData);
					labelExampleEspaceEtiquette.setAlignment(SWT.RIGHT);
					labelExampleEspaceEtiquette.setFont(SWTResourceManager.getFont("Sans", 8, 1, false, false));
					
				}
			}
			{
//				grpSaveModelLables = new Group(this, SWT.NONE);
//				GridLayout grpSaveModelLablesLayout = new GridLayout();
//				grpSaveModelLablesLayout.numColumns = 2;
//				grpSaveModelLables.setLayout(grpSaveModelLablesLayout);
//				GridData grpSaveModelLablesData = new GridData();
//				grpSaveModelLablesData.horizontalAlignment = GridData.FILL;
//				grpSaveModelLablesData.grabExcessHorizontalSpace = true;
//				grpSaveModelLables.setLayoutData(grpSaveModelLablesData);
//				grpSaveModelLables.setText("* Choix chemin pour enregister le Ficherette");
//				grpSaveModelLables.setFont(SWTResourceManager.getFont("Sans", 8, 1, false, false));
//				{
//					textPathSaveEtiquette = new Text(grpSaveModelLables, SWT.BORDER);
//					GridData textPathSaveEtiquetteLData = new GridData();
//					textPathSaveEtiquetteLData.grabExcessHorizontalSpace = true;
//					textPathSaveEtiquetteLData.horizontalAlignment = GridData.FILL;
//					textPathSaveEtiquette.setLayoutData(textPathSaveEtiquetteLData);
//				}
//				{
//					buttonPathSaveEtiquette = new Button(grpSaveModelLables, SWT.PUSH | SWT.CENTER);
//					buttonPathSaveEtiquette.setText("Parcourir");
//					GridData buttonPathSaveEtiquetteLData = new GridData();
//					buttonPathSaveEtiquetteLData.horizontalAlignment = GridData.FILL;
//					buttonPathSaveEtiquette.setLayoutData(buttonPathSaveEtiquetteLData);
//				}

			}
			{
				grpFileExtraction = new Group(this, SWT.NONE);
				GridLayout grpFileExtractionsLayout = new GridLayout();
				grpFileExtractionsLayout.numColumns = 2;
				grpFileExtraction.setLayout(grpFileExtractionsLayout);
				GridData grpFileExtractionsData = new GridData();
				grpFileExtractionsData.horizontalAlignment = GridData.FILL;
				grpFileExtractionsData.grabExcessHorizontalSpace = true;
				grpFileExtractionsData.verticalAlignment = GridData.FILL;
				grpFileExtraction.setLayoutData(grpFileExtractionsData);
				grpFileExtraction.setText("Choix du fichier contenant les données");
				grpFileExtraction.setFont(SWTResourceManager.getFont("Sans", 8, 1, false, false));
				{
					textPathFileExtraction = new Text(grpFileExtraction, SWT.BORDER);
					GridData textPathFileExtractionData = new GridData();
					textPathFileExtractionData.grabExcessHorizontalSpace = true;
					textPathFileExtractionData.horizontalAlignment = GridData.FILL;
					textPathFileExtraction.setLayoutData(textPathFileExtractionData);
				}
				{
					buttonPathFileExtraction = new Button(grpFileExtraction, SWT.PUSH | SWT.CENTER);
					buttonPathFileExtraction.setText("Parcourir");
					GridData buttonPathFileExtractionData = new GridData();
					buttonPathFileExtractionData.horizontalAlignment = GridData.FILL;
					buttonPathFileExtraction.setLayoutData(buttonPathFileExtractionData);
				}

			}
			{
				grpMotCleEtiquette = new Group(this, SWT.NONE);
				GridLayout grpMotCleEtiquetteLayout = new GridLayout();
				grpMotCleEtiquetteLayout.numColumns = 2;
				grpMotCleEtiquette.setLayout(grpMotCleEtiquetteLayout);
				GridData grpMotCleEtiquetteData = new GridData();
				grpMotCleEtiquetteData.horizontalAlignment = GridData.FILL;
				grpMotCleEtiquetteData.grabExcessHorizontalSpace = true;
				grpMotCleEtiquette.setLayoutData(grpMotCleEtiquetteData);
				grpMotCleEtiquette.setText("Fichier de mot clé");
				grpMotCleEtiquette.setFont(SWTResourceManager.getFont("Sans", 8, 1, false, false));
				GridData btOptionMotCleLData = new GridData();
				btOptionMotCleLData.horizontalSpan = 4;
				btOptionMotCleLData.horizontalAlignment = GridData.FILL;
				btOptionMotCleLData.grabExcessHorizontalSpace = true;
				{
					btOptionMotCle = new Button(grpMotCleEtiquette, SWT.CHECK | SWT.LEFT);
					btOptionMotCle.setText("Utiliser un fichier de mots clés personnalisé");
					btOptionMotCle.setLayoutData(btOptionMotCleLData);
				}
				{
					textPathFileMotCle = new Text(grpMotCleEtiquette, SWT.BORDER);
					GridData textPathFileMotCleData = new GridData();
					textPathFileMotCleData.grabExcessHorizontalSpace = true;
					textPathFileMotCleData.horizontalAlignment = GridData.FILL;
					textPathFileMotCle.setLayoutData(textPathFileMotCleData);
					textPathFileMotCle.setEnabled(false);
				}
				{
					buttonPathFileMotCle = new Button(grpMotCleEtiquette, SWT.PUSH | SWT.CENTER);
					buttonPathFileMotCle.setText("Parcourir");
					GridData buttonPathFileMotCleData = new GridData();
					buttonPathFileMotCleData.horizontalAlignment = GridData.FILL;
					buttonPathFileMotCle.setLayoutData(buttonPathFileMotCleData);
					buttonPathFileMotCle.setEnabled(false);
				}
			}
			{
				grpFileSeparateur = new Group(this, SWT.NONE);
				GridLayout grpFileSeparateurLayout = new GridLayout();
				grpFileSeparateurLayout.numColumns = 4;
				grpFileSeparateur.setLayout(grpFileSeparateurLayout);
				grpFileSeparateur.setText("Options");
				GridData grpFileSeparateurData = new GridData();
				grpFileSeparateurData.horizontalAlignment = GridData.FILL;
				grpFileSeparateurData.grabExcessHorizontalSpace = true;
				grpFileSeparateurData.horizontalSpan = 4;
				grpFileSeparateur.setLayoutData(grpFileSeparateurData);
				grpFileSeparateur.setFont(SWTResourceManager.getFont("Sans", 8, 1, false, false));
				{
					btOptionBorderCell = new Button(grpFileSeparateur, SWT.CHECK | SWT.LEFT);
					btOptionBorderCell.setText("Afficher les bordures");
					GridData btOptionBorderCellLData = new GridData();
					btOptionBorderCellLData.horizontalSpan = 4;
					btOptionBorderCellLData.horizontalAlignment = GridData.FILL;
					btOptionBorderCellLData.grabExcessHorizontalSpace = true;
					btOptionBorderCell.setLayoutData(btOptionBorderCellLData);
				}
				{
					labelChoixFileSeparateur = new Label(grpFileSeparateur, SWT.NONE);
					labelChoixFileSeparateur.setText("Choix du Séparateur");
					GridData labelChoixFileSeparateurData = new GridData();
					labelChoixFileSeparateurData.horizontalAlignment = GridData.FILL;
					labelChoixParametreEtiquette.setLayoutData(labelChoixFileSeparateurData);
				}
				{
					comboChoixFileSeparateur = new CCombo(grpFileSeparateur, SWT.NONE | SWT.BORDER);
					GridData comboChoixFileSeparateurLData = new GridData();
					comboChoixFileSeparateurLData.heightHint = 20;
					comboChoixFileSeparateurLData.widthHint = 86;
					comboChoixFileSeparateur.setFont(SWTResourceManager.getFont("Sans", 10, 1, false, false));
					comboChoixFileSeparateur.setLayoutData(comboChoixFileSeparateurLData);
					comboChoixFileSeparateur.setEditable(false);
				}
				{
					btOptionFileSeparateur = new Button(grpFileSeparateur, SWT.CHECK | SWT.LEFT);
					btOptionFileSeparateur.setText("Séparateur personnalisé");
					GridData btOptionFileSeparateurData = new GridData();
					btOptionFileSeparateurData.horizontalSpan = 4;
					btOptionFileSeparateurData.horizontalAlignment = GridData.FILL;
					btOptionFileSeparateurData.grabExcessHorizontalSpace = true;
					btOptionFileSeparateur.setLayoutData(btOptionFileSeparateurData);
				}
				{
					labelSeparateur = new Label(grpFileSeparateur, SWT.NONE);
					labelSeparateur.setText("Séparateur");
					GridData labelSeparateurData = new GridData();
					labelSeparateur.setLayoutData(labelSeparateurData);
				}
				{
					textSeparatuer = new Text(grpFileSeparateur, SWT.BORDER);
					GridData textSeparatuerLData = new GridData();
					textSeparatuerLData.grabExcessHorizontalSpace = true;
					textSeparatuer.setLayoutData(textSeparatuerLData);
				}
				{
//					btSaveNewParaEtiquette = new Button(grpOptionEtiquette, SWT.PUSH | SWT.CENTER);
//					btSaveNewParaEtiquette.setText("Enregistrer");
//					GridData btSaveNewParaEtiquetteLData = new GridData();
//					btSaveNewParaEtiquetteLData.horizontalSpan = 2;
//					btSaveNewParaEtiquetteLData.horizontalAlignment = GridData.END;
//					btSaveNewParaEtiquette.setLayoutData(btSaveNewParaEtiquetteLData);
//					btSaveNewParaEtiquette.setAlignment(SWT.RIGHT);
//					btSaveNewParaEtiquette.setEnabled(false);
				}
			}
//			{
//				grpFormatFileLabels = new Group(this, SWT.NONE);
//				GridLayout grpFormatFileLabelsLayout = new GridLayout();
//				grpFormatFileLabelsLayout.numColumns = 4;
//				grpFormatFileLabels.setLayout(grpFormatFileLabelsLayout);
//				GridData grpFormatFileLabelsData = new GridData();
//				grpFormatFileLabelsData.horizontalAlignment = GridData.FILL;
//				grpFormatFileLabelsData.grabExcessHorizontalSpace = true;
//				grpFormatFileLabels.setLayoutData(grpFormatFileLabelsData);
//				grpFormatFileLabels.setText("Option Format File Etiquette");
//				grpFormatFileLabels.setFont(SWTResourceManager.getFont("Sans", 8, 1, false, false));
//				{
//					btFormatFIlePDF = new Button(grpFormatFileLabels, SWT.RADIO | SWT.LEFT);
//					btFormatFIlePDF.setText("Format PDF");
//					GridData btFormatFIlePDFLData = new GridData();
//					btFormatFIlePDF.setLayoutData(btFormatFIlePDFLData);
//					
//				}
//				{
//					btFormatFIleDOC = new Button(grpFormatFileLabels, SWT.RADIO | SWT.LEFT);
//					btFormatFIleDOC.setText("Format DOC");
//					GridData btFormatFIleDOCData = new GridData();
//					btFormatFIleDOC.setLayoutData(btFormatFIleDOCData);
//				}
//			}
			this.layout();
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public Text getTextMarginLeft() {
		return textMarginLeft;
	}
	
	public Text getTextMarginRight() {
		return textMarginRight;
	}
	
	public Text getTextMarginTop() {
		return textMarginTop;
	}
	
	public Text getTextMarginBottom() {
		return textMarginBottom;
	}

	public Group getGrpParameterMarginPapier() {
		return grpParameterMarginPapier;
	}

	public Group getGrpParametrePapierLH() {
		return grpParametrePapierLH;
	}
	
	public Text getTextLargeurPapier() {
		return textLargeurPapier;
	}
	
	public Text getTextHauteurPapier() {
		return textHauteurPapier;
	}

	public void setButtonCheckBoxChoixModelLabel(
			Button buttonCheckBoxChoixModelLabel) {

	}

//	public Button getButtonCheckBoxModelLabel() {
//		return buttonCheckBoxModelLabel;
//	}
//
//	public void setButtonCheckBoxModelLabel(Button buttonCheckBoxModelLabel) {
//		this.buttonCheckBoxModelLabel = buttonCheckBoxModelLabel;
//		
//	}
	
	public CCombo getComboModelLabels() {
		return comboModelLabels;
	}
	
	public Text getTextNombreRows() {
		return textNombreRows;
	}
	
	public Text getTextNombreColumns() {
		return textNombreColumns;
	}
	
	public void setBtShowEtiquette(Button btShowEtiquette) {
	}
	public Button getBtFormatFIlePDF() {
		return btFormatFIlePDF;
	}

	public void setBtFormatFIlePDF(Button btFormatFIlePDF) {
		this.btFormatFIlePDF = btFormatFIlePDF;
	}

	public Button getBtFormatFIleDOC() {
		return btFormatFIleDOC;
	}

	public void setBtFormatFIleDOC(Button btFormatFIleDOC) {
		this.btFormatFIleDOC = btFormatFIleDOC;

	}

	public Button getButtonPathFileExtraction() {
		return buttonPathFileExtraction;
	}

	public void setButtonPathFileExtraction(Button buttonPathFileExtraction) {
		this.buttonPathFileExtraction = buttonPathFileExtraction;
	}

	public Text getTextPathFileExtraction() {
		return textPathFileExtraction;
	}

	public void setTextPathFileExtraction(Text textPathFileExtraction) {
		this.textPathFileExtraction = textPathFileExtraction;
	}

	public Text getTextEspaceColumns() {
		return textEspaceColumns;
	}

	public Text getTextEspaceRows() {
		return textEspaceRows;
	}

	public Text getTextPaddingLeft() {
		return textPaddingLeft;
	}

	public Text getTextPaddingRight() {
		return textPaddingRight;
	}

	public Text getTextPaddingTop() {
		return textPaddingTop;
	}

	public Text getTextPaddingBottom() {
		return textPaddingBottom;
	}

	public Button getBtOptionBorderCell() {
		return btOptionBorderCell;
	}

	public void setBtOptionBorderCell(Button btOptionBorderCell) {
		this.btOptionBorderCell = btOptionBorderCell;
		
	}
	
//	public Button getBtSaveNewParaEtiquette() {
//		return btSaveNewParaEtiquette;
//	}

	public CCombo getComboChoixParamEtiquette() {
		return comboChoixParamEtiquette;
	}

	public Button getBtDeleteParaEtiquette() {
		return btDeleteParaEtiquette;
	}

	public CCombo getComboChoixFileSeparateur() {
		return comboChoixFileSeparateur;
	}

	public void setComboChoixFileSeparateur(CCombo comboChoixFileSeparateur) {
		this.comboChoixFileSeparateur = comboChoixFileSeparateur;
	}

	public Button getBtOptionFileSeparateur() {
		return btOptionFileSeparateur;
	}

	public void setBtOptionFileSeparateur(Button btOptionFileSeparateur) {
		this.btOptionFileSeparateur = btOptionFileSeparateur;
	}

	public Text getTextSeparatuer() {
		return textSeparatuer;
	}

	public void setTextSeparatuer(Text textSeparatuer) {
		this.textSeparatuer = textSeparatuer;
		
	}

	public Text getTextPathFileMotCle() {
		return textPathFileMotCle;
	}

	public void setTextPathFileMotCle(Text textPathFileMotCle) {
		this.textPathFileMotCle = textPathFileMotCle;
	}

	public Button getButtonPathFileMotCle() {
		return buttonPathFileMotCle;
	}

	public void setButtonPathFileMotCle(Button buttonPathFileMotCle) {
		this.buttonPathFileMotCle = buttonPathFileMotCle;
	}

	public Button getBtOptionMotCle() {
		return btOptionMotCle;
	}

	public void setBtOptionMotCle(Button btOptionMotCle) {
	}

}

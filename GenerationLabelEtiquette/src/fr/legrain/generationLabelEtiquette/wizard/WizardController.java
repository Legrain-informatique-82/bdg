package fr.legrain.generationLabelEtiquette.wizard;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import fr.legrain.generationLabelEtiquette.divers.ConstantModelLabels;
import fr.legrain.generationLabelEtiquette.divers.GenerationFileEtiquette;
import fr.legrain.generationLabelEtiquette.divers.ParameterEtiquette;
import fr.legrain.generationLabelEtiquette.handlers.ParamWizardEtiquettes;
import fr.legrain.generationLabelEtiquette.preferences.PreferenceConstants;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.lib.data.LibConversion;
import generationlabeletiquette.Activator;

public class WizardController {

	public static final String DOSSIER_PARAM_TIERS   = Const.FOLDER_PARAMETRES_ETIQUETTE_TIERS; 
	public static final String DOSSIER_PARAM_ARTICLE = Const.FOLDER_PARAMETRES_ETIQUETTE_ARTICLE;

	private Shell shell;
	private CompositePageOptionModelLabels compositePageOptionModelLabels;
	private CompositePageOptionFormatEtiquette compositePageOptionFormatEtiquette;
	private String modelLables;
	private String valueSeparateur = null;
//	private boolean flagChoixPreDefinionModelLables;
	private WizardPageModelLables wizardPageModelLables;
	private WizardPageFormatEtiquette wizardPageFormatEtiquette;
	
	private ParamWizardEtiquettes param;
	
	private Float marginLeft = null;
	private Float marginRight = null;
	private Float marginTop = null;
	private Float marginButtom = null;
	
	private Float paddingLeft = null;
	private Float paddingRight = null;
	private Float paddingTop = null;
	private Float paddingButtom = null;
	
	private Float largeurPapier = null;
	private Float hauteurPapier = null;
	
	private boolean cellBorder = false;
	private boolean choixSeparateur = false;
	private boolean choixFileMotCle = false;
	private boolean choixSaveEtiquette = false;
	
	private Integer nombreRows = null;
	private Integer nombrecolumns = null;
	
	private Integer decalage;
	private Integer quantite;
	
	private Float largeurEspace = null;
	private Float hauteurEspace = null;
	
	private String pathFolderSaveEtiquete = null;
	private String pathFileExtraction = null;
	private String pathFileMotCle = null;
	private boolean flagPageModelLables = false;
	private boolean flagPageFormatEtiquette = false;
	
	
	private boolean flagMargin = true;
	private boolean flagPadding = true;
	private boolean flagRowsAndColumns = false;
	private boolean flagFormatPaper = true;
	private boolean flagPathSaveEtiquete = true;
	private boolean flagPathFileExtraction = false;
	private boolean flagPathMotCle = false;
	private boolean flagSeparateur = false;
	private boolean flagEspace = false;
	private boolean flagActiveScrollBar = false;
	
	private boolean flagNameEtiquette = false;
	private boolean flagTextEtiquette = false;
	private GenerationFileEtiquette generationFileEtiquette;
	
	private String textFormatEtiquette;
	private String textFormatEtiquetteFinal;
	
	private boolean isGras = false; 
	private boolean isItalic = false;
	
	private String sizeEtiquette;
	
	private String nameParamEtiquette;
	
	private String newParamEtiquette;
	
	private ParameterEtiquette parameterEtiqutte;
	
	private int selectComboParamEtiquette;
	
	private Listener listenervalidPageModelLables = new Listener() {
		@Override
		public void handleEvent(Event event) {
			wizardPageModelLables.setPageComplete(validatePageModelLables());
		}
	};
	
	private Listener listenervalidPageFormatEtiquette = new Listener() {
		@Override
		public void handleEvent(Event event) {
			wizardPageFormatEtiquette.setPageComplete(validatePagePageFormatEtiquette());
		}
	};
	
	public boolean validatePagePageFormatEtiquette(){
		reinitialMessageWizardPage(wizardPageFormatEtiquette);
		
		decalage = LibConversion.stringToInteger(compositePageOptionFormatEtiquette.getTfDecalage().getText());
		quantite = LibConversion.stringToInteger(compositePageOptionFormatEtiquette.getTfQuantite().getText());
		
		textFormatEtiquette = compositePageOptionFormatEtiquette.getTextFormatEtiquette().getText();
		
		if(textFormatEtiquette.trim().length() != 0){
			String value1 = textFormatEtiquette.replaceAll("\r", "").replaceAll("\n", "").trim().replaceAll(" ", "");
			String value2 = generationFileEtiquette.getMotCleEtiquette().replaceAll("\r", "").replaceAll("\n", "").trim().replaceAll(" ", "");
//			if(!value1.equalsIgnoreCase(value2)){
//				wizardPageFormatEtiquette.setErrorMessage(ConstantModelLabels.WIZARDPAGE_ERROR_MESSAGE_FORMAT_ETIQUETTE);
//				flagTextEtiquette = false;
//			}else{
				flagTextEtiquette = true;
//			}
		}else{
			flagTextEtiquette = false;
		}
		
//		if(choixSaveEtiquette){
			//nameParamEtiquette = compositePageOptionFormatEtiquette.getTextNameEtiquette().getText();
			newParamEtiquette = compositePageOptionFormatEtiquette.getTextNameEtiquette().getText();
			if(newParamEtiquette.trim().length() != 0){
				flagNameEtiquette = true;
			}else{
				flagNameEtiquette = false;
			}
//		}else{
//			flagNameEtiquette = true;
//		}
		
		
//		if(textFormatEtiquette.length()==0){
//			flagPageFormatEtiquette = false;
//		}else{
//			flagPageFormatEtiquette = true;
//		}
//		Text text = compositePageOptionFormatEtiquette.getTextFormatEtiquette();
//		System.out.println("text.getClientArea()==> "+text.getClientArea().height);
//		ScrollBar scrollBar = compositePageOptionFormatEtiquette.getTextFormatEtiquette().getVerticalBar(); 
//		System.out.println("scrollBar.getDismissalAlignment() ==>"+scrollBar.getDisplay().getDismissalAlignment());
//		if(!scrollBar.isDisposed()){
//			System.out.println("ok");
//		}
		
		flagPageFormatEtiquette = flagTextEtiquette && flagNameEtiquette;
		
		return flagPageFormatEtiquette;
	}
	
	
	public boolean validatePageModelLables(){
	
		reinitialMessageWizardPage(wizardPageModelLables);
		
		String valueMarginLeft = compositePageOptionModelLabels.getTextMarginLeft().getText();
		marginLeft = checkFloatValueValide(valueMarginLeft);
		
		String valueMarginRight = compositePageOptionModelLabels.getTextMarginRight().getText();
		marginRight = checkFloatValueValide(valueMarginRight);
		
		String valueMarginTop = compositePageOptionModelLabels.getTextMarginTop().getText();
		marginTop = checkFloatValueValide(valueMarginTop);
		
		String valueMarginButtom = compositePageOptionModelLabels.getTextMarginBottom().getText();
		marginButtom = checkFloatValueValide(valueMarginButtom);
		if(marginLeft != null && marginRight != null && marginTop != null && marginButtom != null){
			flagMargin = true;
		}else{
			flagMargin = false;
		}
		String valuePaddingLeft = compositePageOptionModelLabels.getTextPaddingLeft().getText();
		paddingLeft = checkFloatValueValide(valuePaddingLeft);
		
		String valueePaddingRight = compositePageOptionModelLabels.getTextPaddingRight().getText();
		paddingRight = checkFloatValueValide(valueePaddingRight);
		
		String valueePaddingTop = compositePageOptionModelLabels.getTextPaddingTop().getText();
		paddingTop = checkFloatValueValide(valueePaddingTop);
		
		String valueePaddingButtom = compositePageOptionModelLabels.getTextPaddingBottom().getText();
		paddingButtom = checkFloatValueValide(valueePaddingButtom);
		
		if(paddingLeft != null && paddingRight != null && paddingTop != null && paddingButtom != null){
			flagPadding = true;
		}else{
			flagPadding = false;
		}
		
//		if(!flagChoixPreDefinionModelLables){
			String valueLargeurPapier = compositePageOptionModelLabels.getTextLargeurPapier().getText();
			largeurPapier = checkFloatValueValide(valueLargeurPapier);
			
			String valueHauteurPapier = compositePageOptionModelLabels.getTextHauteurPapier().getText();
			hauteurPapier = checkFloatValueValide(valueHauteurPapier);
			
			if(largeurPapier != null && hauteurPapier != null){
				flagFormatPaper = true;
			}else{
				flagFormatPaper = false;
			}
//		}
		

		String valueNombreRows = compositePageOptionModelLabels.getTextNombreRows().getText();
		nombreRows = checkIntegerValueValide(valueNombreRows);
		
		String valueNombreColumns = compositePageOptionModelLabels.getTextNombreColumns().getText();
		nombrecolumns = checkIntegerValueValide(valueNombreColumns);
	
		if(nombreRows != null && nombrecolumns != null){
			flagRowsAndColumns = true;
		}else{
			flagRowsAndColumns = false;
		}
		
		String valueEspaceRows = compositePageOptionModelLabels.getTextEspaceRows().getText();
		largeurEspace = checkFloatValueValide(valueEspaceRows);
		
		String valueEspaceColumns = compositePageOptionModelLabels.getTextEspaceColumns().getText();
		hauteurEspace = checkFloatValueValide(valueEspaceColumns);
		
		if(largeurEspace != null && hauteurEspace != null){
			flagEspace = true;
		}else{
			flagEspace = false;
		}
		
//		String pathSaveEtiquette = compositePageOptionModelLabels.getTextPathSaveEtiquette().getText();
//		flagPathSaveEtiquete = checkFolderExist(pathSaveEtiquette,wizardPageModelLables);
		
		String pathFileExtraction = compositePageOptionModelLabels.getTextPathFileExtraction().getText();
		flagPathFileExtraction = checkFileExist(pathFileExtraction, wizardPageModelLables);
		if(flagPathFileExtraction){
			this.pathFileExtraction = pathFileExtraction;
		}
		
		if(choixFileMotCle){
			String pathFileMotCle = compositePageOptionModelLabels.getTextPathFileMotCle().getText();
			flagPathMotCle = checkFileExist(pathFileMotCle, wizardPageModelLables);
			if(flagPathMotCle){
				this.pathFileMotCle = pathFileMotCle;
			}
		}else{
			flagPathMotCle = true;
		}
		
		if(choixSeparateur){
			valueSeparateur = compositePageOptionModelLabels.getTextSeparatuer().getText();
			if(valueSeparateur.trim().length() != 0){
				flagSeparateur = true;
			}else{
				flagSeparateur = false;
			}
		}else{
			flagSeparateur = true;
		}
		
		flagPageModelLables = flagMargin && flagPadding && flagFormatPaper && flagRowsAndColumns && flagPathSaveEtiquete && 
						      flagPathFileExtraction && flagEspace && flagPathMotCle &&
							  flagSeparateur;
		return flagPageModelLables;
	}
	
	public Integer checkIntegerValueValide(String value){
		
		Integer valueInteger = null;
		if(value.matches(ConstantModelLabels.PATTERN_INTEGER)){
			valueInteger = new Integer(value);
		}else{
			wizardPageModelLables.setMessage(ConstantModelLabels.MESSAGE_ERROR_MODEL_LABLES_TEXT,
					DialogPage.ERROR);
		}
		return valueInteger;
	}
	
	public Float checkFloatValueValide(String value){
		
		Float valueFloat = null;
		if(value.matches(ConstantModelLabels.PATTERN_FLOAT)){
			valueFloat = new Float(value);
		}else{
			wizardPageModelLables.setMessage(ConstantModelLabels.MESSAGE_ERROR_MODEL_LABLES_TEXT,
					DialogPage.ERROR);
		}
		return valueFloat;
	}
	
	public boolean checkFolderExist(String pathFolder,WizardPage wizardPage){
		
		boolean flag = false;
		File folder = new File(pathFolder);
		if(folder.exists() && folder.isDirectory()){
			pathFolderSaveEtiquete = pathFolder;
			flag = true;
		}
		if(!flag){
			
			wizardPage.setMessage(ConstantModelLabels.MESSAGE_FOLDER_NO_EXIST, DialogPage.ERROR);
			wizardPage.setPageComplete(false);
		}
		
		return flag;
	}
	
	public boolean checkFileExist(String pathFile,WizardPage wizardPage){
		
		boolean flag = false;
		File file = new File(pathFile);
		if(file.exists() && file.isFile()){
//			pathFileExtraction = pathFile;
			flag = true;
		}
		if(!flag){
			wizardPage.setMessage(ConstantModelLabels.MESSAGE_FILE_NO_EXIST, DialogPage.ERROR);
		}
		
		return flag;
	}
	
	public void initialCompsitePageOptionFormatEtiquette(ParameterEtiquette parameterEtiqutte){
		
		this.nameParamEtiquette = parameterEtiqutte.getNameParameterEtiqutte();
		
		String pathFileParamEtiquette = generationFileEtiquette.getMapFileParamEtiquette().
										get(nameParamEtiquette);
		
		String[] arraySizeEtiquette = makeItemCombo();
		Combo comboSizeEtiquette = compositePageOptionFormatEtiquette.getComboSizeEtiquette();
		comboSizeEtiquette.setItems(arraySizeEtiquette);
		comboSizeEtiquette.setEnabled(true);
//		this.sizeEtiquette = "10";
		
		Text textZoneFromatEtiquette = compositePageOptionFormatEtiquette.getTextFormatEtiquette();
		FontData fontData = textZoneFromatEtiquette.getFont().getFontData()[0];
		
//		if(pathFileParamEtiquette != null){
			
//			this.parameterEtiqutte = generationFileEtiquette.readObjectCastor(pathFileParamEtiquette);
			this.parameterEtiqutte = parameterEtiqutte;
			this.isGras = parameterEtiqutte.isGras();
			this.isItalic = parameterEtiqutte.isItalic();
			this.sizeEtiquette = parameterEtiqutte.getSizeEtiquette();
			this.textFormatEtiquette = parameterEtiqutte.getTextModelEtiquette();
			this.decalage = parameterEtiqutte.getDecalage();
			this.quantite = parameterEtiqutte.getQuantite();
			
			
			if(isGras && !isItalic){
				fontData.setStyle(SWT.BOLD);	
			}
			else if(!isGras && isItalic){
				fontData.setStyle(SWT.ITALIC);
			}
			else if(isGras && isItalic){
				fontData.setStyle(SWT.BOLD | SWT.ITALIC);
			}
			else if(!isGras && !isItalic){
				fontData.setStyle(SWT.NORMAL);
			}
//		}
		
		fontData.setHeight(Integer.valueOf(sizeEtiquette));
		fontData.setName("Sans");
		
		textZoneFromatEtiquette.setFont(new Font(compositePageOptionFormatEtiquette.getDisplay(),
				fontData));
		
		int indexSelectSizeEtiquette = getIndexArray(arraySizeEtiquette, sizeEtiquette);
		compositePageOptionFormatEtiquette.getComboSizeEtiquette().select(indexSelectSizeEtiquette);
		
		compositePageOptionFormatEtiquette.getBtCheckBoxGras().setSelection(parameterEtiqutte.isGras());
		
		compositePageOptionFormatEtiquette.getBtCheckBoxItalique().setSelection(parameterEtiqutte.isItalic());
		
//		compositePageOptionFormatEtiquette.getTextFormatEtiquette().setText(parameterEtiqutte.getTextModelEtiquette());
		
		compositePageOptionFormatEtiquette.getTextNameEtiquette().setText(nameParamEtiquette);
		
		compositePageOptionFormatEtiquette.layout();
	}
	
	public void initialActionListenerPageFormatEtiquette(){
		reinitialMessageWizardPage(wizardPageFormatEtiquette);
		
//		initialCompsitePageOptionFormatEtiquette();
		
		final Text textAreaFormatEtiquette = compositePageOptionFormatEtiquette.getTextFormatEtiquette();
		addListenertext(textAreaFormatEtiquette, listenervalidPageFormatEtiquette);
		
		Button btCheckBoxGras = compositePageOptionFormatEtiquette.getBtCheckBoxGras();
		addListenerCheckBoxGras(btCheckBoxGras);
		
		Button btCheckBoxItalic = compositePageOptionFormatEtiquette.getBtCheckBoxItalique();
		addListenerCheckBoxItalic(btCheckBoxItalic);
		
		Combo comboSizeEtiquette = compositePageOptionFormatEtiquette.getComboSizeEtiquette();
		initialAndAddListenerComboSizeEtiquette(comboSizeEtiquette);	
		
		
		Text textNameParaEtiquette = compositePageOptionFormatEtiquette.getTextNameEtiquette();
		textNameParaEtiquette.setEnabled(false);
		addListenertext(textNameParaEtiquette, listenervalidPageFormatEtiquette);
		
		Button btCheckBoxSaveEtiquette = compositePageOptionFormatEtiquette.getBtCheckBoxSave();
		addListenerbtChoixSaveEtiquette(btCheckBoxSaveEtiquette,textNameParaEtiquette);
		
		addListenertext(compositePageOptionFormatEtiquette.getTfDecalage(), listenervalidPageFormatEtiquette);
		addListenertext(compositePageOptionFormatEtiquette.getTfQuantite(), listenervalidPageFormatEtiquette);
		
//		ScrollBar scrollBar = compositePageOptionFormatEtiquette.getTextFormatEtiquette().getVerticalBar();
//		scrollBar.setVisible(false);
//		scrollBar.addListener(SWT.Selection,new Listener (){
//			@Override
//			public void handleEvent(Event event) {
//				wizardPageFormatEtiquette.setErrorMessage(
//						ConstantModelLabels.WIZARDPAGE_ERROR_MESSAGE_SCROLLBAR);
//			}		
//		});
	}
	
	public void addListenerbtChoixSaveEtiquette(final Button button,final Text text){
		button.addListener(SWT.Selection, new Listener(){

			@Override
			public void handleEvent(Event event) {
				choixSaveEtiquette = button.getSelection();
//				text.setText("");
				if(choixSaveEtiquette){
					text.setEnabled(true);
					flagNameEtiquette = false;
				}else{
					text.setEnabled(false);
					flagNameEtiquette = true;
				}
			}
		});
	}
	
	public void initialActionListenerPageModelLables(String type){
		
		reinitialMessageWizardPage(wizardPageModelLables);
		
		
		initialInterfacePageModelLables(type);
		
		CCombo comboParamEtiquette = compositePageOptionModelLabels.getComboChoixParamEtiquette();
//		Button btSaveNewParaEtiquette = compositePageOptionModelLabels.getBtSaveNewParaEtiquette();
//		InitialAndAddListenerComboParamEtiquette(comboParamEtiquette,btSaveNewParaEtiquette);
		initialAndAddListenerComboParamEtiquette(comboParamEtiquette);
		
		Button btDeleteParaEtiquette = compositePageOptionModelLabels.getBtDeleteParaEtiquette();
		
		ParameterEtiquette parameterEtiqutte = new ParameterEtiquette(); 
		
		addListenerButtonSaveAndDeleteEtiquette(/*btSaveNewParaEtiquette,*/ btDeleteParaEtiquette,parameterEtiqutte
				,compositePageOptionModelLabels,comboParamEtiquette);
		
			
		CCombo combo = compositePageOptionModelLabels.getComboModelLabels();
		initialAndAddListenerCombo(combo);
		
//		Button buttonCheckBoxModelLabel = compositePageOptionModelLabels.getButtonCheckBoxModelLabel();
//		addListenerCheckBoxModelLabel(buttonCheckBoxModelLabel);
		
		Button buttonCheckCellBorder = compositePageOptionModelLabels.getBtOptionBorderCell();
		addListenerCheckBoxCellBorder(buttonCheckCellBorder);
		
		Text textMarginLeft =  compositePageOptionModelLabels.getTextMarginLeft();
		addListenertext(textMarginLeft,listenervalidPageModelLables);
		Text textMarginRight =  compositePageOptionModelLabels.getTextMarginRight();
		addListenertext(textMarginRight,listenervalidPageModelLables);
		Text textMarginTop =  compositePageOptionModelLabels.getTextMarginTop();
		addListenertext(textMarginTop,listenervalidPageModelLables);		
		Text textMarginButtom =  compositePageOptionModelLabels.getTextMarginBottom();
		addListenertext(textMarginButtom,listenervalidPageModelLables);
		
		Text textPaddingLeft =  compositePageOptionModelLabels.getTextPaddingLeft();
		addListenertext(textPaddingLeft,listenervalidPageModelLables);
		Text textPaddingRight =  compositePageOptionModelLabels.getTextPaddingRight();
		addListenertext(textPaddingRight,listenervalidPageModelLables);
		Text textPaddingTop =  compositePageOptionModelLabels.getTextPaddingTop();
		addListenertext(textPaddingTop,listenervalidPageModelLables);		
		Text textPaddingButtom =  compositePageOptionModelLabels.getTextPaddingBottom();
		addListenertext(textPaddingButtom,listenervalidPageModelLables);
		
		Text textLargeurPapier =  compositePageOptionModelLabels.getTextLargeurPapier();
		addListenertext(textLargeurPapier,listenervalidPageModelLables);		
		Text textHauteurPapier =  compositePageOptionModelLabels.getTextHauteurPapier();
		addListenertext(textHauteurPapier,listenervalidPageModelLables);
		
		Text textRowsLabel =  compositePageOptionModelLabels.getTextNombreRows();
		addListenertext(textRowsLabel,listenervalidPageModelLables);
		Text textColumnsLabel =  compositePageOptionModelLabels.getTextNombreColumns();
		addListenertext(textColumnsLabel,listenervalidPageModelLables);
		
		Text textEspaceRows =  compositePageOptionModelLabels.getTextEspaceRows();
		addListenertext(textEspaceRows,listenervalidPageModelLables);
		Text textEspaceColumns =  compositePageOptionModelLabels.getTextEspaceColumns();
		addListenertext(textEspaceColumns,listenervalidPageModelLables);
		
//		Button buttonPathSaveEtiquette = compositePageOptionModelLabels.getButtonPathSaveEtiquette();
//		Text textPathSaveEtiquette = compositePageOptionModelLabels.getTextPathSaveEtiquette();
//		addListenerButtonParcourirFolder(buttonPathSaveEtiquette,textPathSaveEtiquette);
//		addListenertext(textPathSaveEtiquette,listenervalidPageModelLables);
		
		Button buttonFileExtraction = compositePageOptionModelLabels.getButtonPathFileExtraction();
		Text textPathFileExtraction = compositePageOptionModelLabels.getTextPathFileExtraction();
		addListenerButtonParcourirFile(buttonFileExtraction,textPathFileExtraction);
		addListenertext(textPathFileExtraction,listenervalidPageModelLables);
		
		
		
		Text textPathMotCle = compositePageOptionModelLabels.getTextPathFileMotCle();
//		addListenerTextMotCle(textPathMotCle);
		addListenertext(textPathMotCle,listenervalidPageModelLables);
		
		
		
		Button buttonFileMotCle = compositePageOptionModelLabels.getButtonPathFileMotCle();
		addListenerBtParcourirFileMotCle(buttonFileMotCle,textPathMotCle);
		
		Button btChoixMotCle = compositePageOptionModelLabels.getBtOptionMotCle();
		addListenerbtChoixMotCle(btChoixMotCle,textPathMotCle,buttonFileMotCle);
		
		
		
		CCombo comboSeparateur = compositePageOptionModelLabels.getComboChoixFileSeparateur();
		comboSeparateur.setItems(ConstantModelLabels.VALUES_COMBO_TYPE_SEPARATEUR);
		comboSeparateur.select(0);
		valueSeparateur = ConstantModelLabels.VALUES_COMBO_TYPE_SEPARATEUR[0];
		addListenerComboSeparateur(comboSeparateur);
		
		Text textSeparateur = compositePageOptionModelLabels.getTextSeparatuer();
		textSeparateur.setEnabled(false);
//		addListenerTextSeparateur(textSeparateur);
		addListenertext(textSeparateur,listenervalidPageModelLables);
		
		Button btChoixSeparateur = compositePageOptionModelLabels.getBtOptionFileSeparateur();
		addListenerbtChoixSeparateur(btChoixSeparateur,comboSeparateur,textSeparateur);
		
	}
	
	public void addListenerBtParcourirFileMotCle(Button button,final Text text){
		button.addListener(SWT.Selection, new Listener(){
			@Override
			public void handleEvent(Event event) {
				FileDialog fileDialog= new FileDialog(shell);
				pathFileMotCle = fileDialog.open();
				text.setText(pathFileMotCle);
			}
		});
	}
	
	public void addListenerTextMotCle(final Text text){
		text.addListener(SWT.Modify,new Listener(){

			@Override
			public void handleEvent(Event event) {
				String pathMotCle = text.getText();
				flagPathMotCle = checkFileExist(pathFileMotCle, wizardPageModelLables);
				if(flagPathMotCle){
					pathFileMotCle = pathMotCle;
				}else{
					wizardPageModelLables.setPageComplete(flagPathMotCle);
				}
			}
		});
	}
	
	public void addListenerbtChoixMotCle(final Button button,final Text text,final Button btPathMotCle){
		button.addListener(SWT.Selection, new Listener(){

			@Override
			public void handleEvent(Event event) {
				choixFileMotCle = button.getSelection();
				if(choixFileMotCle){
					text.setEnabled(true);
					btPathMotCle.setEnabled(true);
					wizardPageModelLables.setPageComplete(false);
				}else{
					text.setEnabled(false);
					text.setText("");
					btPathMotCle.setEnabled(false);
					pathFileMotCle = Const.C_FICHIER_LISTE_ATTRIBUTE_ETIQUETTE;
				}
			}
		});
	}
	
	public void addListenerTextSeparateur(final Text text){
		text.addListener(SWT.Modify,new Listener(){
			@Override
			public void handleEvent(Event event) {
				valueSeparateur = text.getText();
			}
		});
	}
	
	public void addListenerbtChoixSeparateur(final Button button,final CCombo combo,final Text text){
		button.addListener(SWT.Selection, new Listener(){

			@Override
			public void handleEvent(Event event) {
				choixSeparateur = button.getSelection();
				if(choixSeparateur){
					text.setEnabled(true);
					combo.setEnabled(false);
					valueSeparateur = "";
					wizardPageModelLables.setPageComplete(false);					
				}else{
					text.setEnabled(false);
					combo.setEnabled(true);
					text.setText("");
					combo.select(0);
					valueSeparateur = combo.getText();
					wizardPageModelLables.setPageComplete(flagPageModelLables);
				}
			}
		});
	}
	
	public void addListenerComboSeparateur(final CCombo combo) {
		combo.addListener(SWT.Selection, new Listener(){
			@Override
			public void handleEvent(Event event) {
				valueSeparateur = combo.getText();
			}		
		});
	}

	public void addListenerButtonSaveAndDeleteEtiquette(/*Button buttonSave,*/final Button buttonDelete,
			final ParameterEtiquette parameterEtiqutte,final Composite composite,
			final CCombo combo){
//		
//		final ParameterEtiqutte parameterEtiqutte = new ParameterEtiqutte();
		
//		buttonSave.addListener(SWT.Selection,new Listener(){
//			@Override
//			public void handleEvent(Event event) {
//				parameterEtiqutte.setNameParameterEtiqutte(newParamEtiquette);
//				String nameFile = Const.PATH_FOLDER_PARAMETRES_ETIQUETTE +"/" 
//								  + newParamEtiquette + ConstantModelLabels.TYPE_FILE_XML;
//								//	+ newParamEtiquette + ConstantModelLabels.TYPE_FILE_DAT;
//				//generationFileEtiquette.writeObject(parameterEtiqutte, nameFile);
//				//generationFileEtiquette.writeObjectSerializable(parameterEtiqutte, nameFile);
//				generationFileEtiquette.updateProprityObject(cellBorder, marginLeft, marginRight, marginTop, 
//						marginButtom, paddingLeft, paddingRight, paddingTop, paddingButtom, largeurPapier, 
//						hauteurPapier, largeurEspace, hauteurEspace, modelLables, nombrecolumns, nombreRows, 
//						pathFileExtraction, "", nameFile,parameterEtiqutte);
//				
//				generationFileEtiquette.writeObjectCastor(parameterEtiqutte, nameFile);
//				combo.setItems(generationFileEtiquette.arrayPathFileParamEtiquette(
//						Const.PATH_FOLDER_PARAMETRES_ETIQUETTE));
//				
//			}
//			
//		});
		
		buttonDelete.addListener(SWT.Selection,new Listener(){
			@Override
			public void handleEvent(Event event) {
				if(nameParamEtiquette!=null && nameParamEtiquette.length()!=0){
					boolean flag = MessageDialog.openConfirm(composite.getShell(),ConstantModelLabels.MESSAGE_DIALOG_TITLE,
							ConstantModelLabels.MESSAGE_DIALOG_CONTENT);
					if(flag){
						String deletePathParametiquette = generationFileEtiquette.getMapFileParamEtiquette().
											get(nameParamEtiquette);
						new File(deletePathParametiquette).delete();
						generationFileEtiquette.getMapFileParamEtiquette().remove(nameParamEtiquette);
						String[] elementCcomboStrings = generationFileEtiquette.arrayPathFileParamEtiquette(Const.PATH_FOLDER_PARAMETRES_ETIQUETTE);
						if(elementCcomboStrings.length != 0){
							combo.setItems(elementCcomboStrings);
						}else{
							combo.setEnabled(false);
							buttonDelete.setEnabled(false);
						}
					}
				}else{
					MessageDialog.openInformation(composite.getShell(),ConstantModelLabels.MESSAGE_DIALOG_INFOS,
							ConstantModelLabels.MESSAGE_DIALOG_INFOS_CONTENT);
				}
				
				
				
			}
			
		});
		
	}
	
	public void addListenertext(Text text,Listener listener){
		text.addListener(SWT.Modify, listener);
	}
	
	public void addListenerButtonParcourirFolder(Button button,final Text text){
		button.addListener(SWT.Selection, new Listener(){
			@Override
			public void handleEvent(Event event) {
				DirectoryDialog directoryDialog= new DirectoryDialog(shell);
				text.setText(directoryDialog.open());
				pathFolderSaveEtiquete = directoryDialog.open();
			}
		});
	}
	
	public void addListenerButtonParcourirFile(Button button,final Text text){
		button.addListener(SWT.Selection, new Listener(){
			@Override
			public void handleEvent(Event event) {
				FileDialog fileDialog= new FileDialog(shell);
				pathFileExtraction = fileDialog.open();
				text.setText(pathFileExtraction);
				
			}
		});
	}
	
	/**
	 * 
	 * @param champ text largeur ou text hauteur
	 * @param row or column
	 */
	public void addListenerTextFormatEtiquete(final Text text,final String name){
		
		text.addListener(SWT.FocusOut, new Listener(){
			@Override
			public void handleEvent(Event event) {
				reinitialMessageWizardPage(wizardPageModelLables);
				String value = text.getText();
				try {
					if(name.equals(ConstantModelLabels.ROWS_LABLES)){
//						String valueRows = text.getText();
						nombreRows = new Integer(Integer.valueOf(value));
					}else if(name.equals(ConstantModelLabels.COLUMNS_LABLES)){
//						String valueColumns = text.getText();
						nombrecolumns = new Integer(Integer.valueOf(value));
					}
					flagPageModelLables = true;
					wizardPageModelLables.setPageComplete(flagPageModelLables);
				} catch (Exception e) {
//					flagPageModelLables = false;
					wizardPageModelLables.setMessage(ConstantModelLabels.MESSAGE_ERROR_MODEL_LABLES_TEXT,
							DialogPage.ERROR);
					text.setText("");
					
				}
			}
			
		});
	}
	
	public void reinitialMessageWizardPage(WizardPage wizardPage){
		wizardPage.setMessage(null);
		wizardPage.setErrorMessage(null);
	}
	
	/**
	 * Modification des propriétés du controller et de celles de l'interface.
	 */
	public void updateCompostiPageModelAndValue(boolean cellBorder,int index,
			Float marginLeft,Float marginRight,Float marginTop,Float marginButtom,
			Float paddingLeft,Float paddingRight,Float paddingTop,Float paddingButtom,
			Integer nombreRows,Integer nombrecolumns,Float largeurEspace,Float hauteurEspace,
			Float largeurPapier,Float hauteurPapier,
			String pathFileExtraction,int choixComboModelLetter,
			String[] choixParamEtiquette,String modelLables,String pathFileMotCle,
			boolean choixFileMotCle,String valueSeparateur,boolean choixSeparateur,
			boolean isGras,boolean isItalic,String textFormatEtiquette,Integer decalage,Integer quantite){
		
		this.cellBorder = cellBorder;
		this.marginLeft = marginLeft;
		this.marginRight = marginRight;
		this.marginTop = marginTop;
		this.marginButtom = marginButtom;
		
		this.paddingLeft = paddingLeft;
		this.paddingRight = paddingRight;
		this.paddingTop = paddingTop;
		this.paddingButtom = paddingButtom;
		
		this.nombreRows = nombreRows;
		this.nombrecolumns = nombrecolumns;


		this.largeurEspace = largeurEspace;
		this.hauteurEspace = hauteurEspace;
		

		this.largeurPapier = Float.valueOf(largeurPapier);
		this.hauteurPapier = Float.valueOf(hauteurPapier);
		
		this.modelLables = modelLables;
		
		this.pathFileMotCle = pathFileMotCle;
		this.choixFileMotCle = choixFileMotCle;
		
		this.isGras = isGras;
		this.isItalic = isItalic;
		this.textFormatEtiquette = textFormatEtiquette;
		
		this.decalage = decalage;
		this.quantite = quantite;
		
		compositePageOptionModelLabels.getBtOptionBorderCell().setSelection(cellBorder);
		
		
//		compositePageOptionModelLabels.getBtSaveNewParaEtiquette().setEnabled(false);
		if(choixParamEtiquette.length != 0){
			compositePageOptionModelLabels.getComboChoixParamEtiquette().setItems(choixParamEtiquette);
//			compositePageOptionModelLabels.getBtDeleteParaEtiquette().setEnabled(true);
		}else{
//			compositePageOptionModelLabels.getBtDeleteParaEtiquette().setEnabled(false);
		}
		compositePageOptionModelLabels.getComboChoixParamEtiquette().select(index);
		
		compositePageOptionModelLabels.getTextMarginTop().setText(marginTop.toString());
		compositePageOptionModelLabels.getTextMarginBottom().setText(marginButtom.toString());
		compositePageOptionModelLabels.getTextMarginLeft().setText(marginLeft.toString());
		compositePageOptionModelLabels.getTextMarginRight().setText(marginRight.toString());
		
		compositePageOptionModelLabels.getTextPaddingTop().setText(paddingTop.toString());
		compositePageOptionModelLabels.getTextPaddingBottom().setText(paddingButtom.toString());
		compositePageOptionModelLabels.getTextPaddingLeft().setText(paddingLeft.toString());
		compositePageOptionModelLabels.getTextPaddingRight().setText(paddingRight.toString());
		
		compositePageOptionModelLabels.getTextNombreRows().setText(nombreRows.toString());
		compositePageOptionModelLabels.getTextNombreColumns().setText(nombrecolumns.toString());
		
		compositePageOptionModelLabels.getTextEspaceRows().setText(largeurEspace.toString());
		compositePageOptionModelLabels.getTextEspaceColumns().setText(hauteurEspace.toString());
	
		compositePageOptionModelLabels.getTextHauteurPapier().setText(String.valueOf(hauteurPapier));
		compositePageOptionModelLabels.getTextLargeurPapier().setText(String.valueOf(largeurPapier));
		
		compositePageOptionModelLabels.getComboModelLabels().setItems(
				ConstantModelLabels.VALUES_COMBO_TYPE_PAPER);
		compositePageOptionModelLabels.getComboModelLabels().select(choixComboModelLetter);
		
		if(!modelLables.equalsIgnoreCase(ConstantModelLabels.TYPE_PAPER_CUSTOM)){
			
//			compositePageOptionModelLabels.getButtonCheckBoxModelLabel().setSelection(true);
			compositePageOptionModelLabels.getTextHauteurPapier().setEnabled(false);
			compositePageOptionModelLabels.getTextLargeurPapier().setEnabled(false);
//			compositePageOptionModelLabels.getComboModelLabels().setEnabled(true);
			
		}else{
//			this.flagChoixPreDefinionModelLables =  false;
//			compositePageOptionModelLabels.getButtonCheckBoxModelLabel().setSelection(false);
			compositePageOptionModelLabels.getTextHauteurPapier().setEnabled(true);
			compositePageOptionModelLabels.getTextLargeurPapier().setEnabled(true);
//			compositePageOptionModelLabels.getComboModelLabels().setEnabled(false);
		}
		
		if(param==null || (param!=null && param.isModeIntegre()==false)) {
			this.pathFileExtraction = pathFileExtraction;
			compositePageOptionModelLabels.getTextPathFileExtraction().setText(pathFileExtraction);
		}
		
		if(choixFileMotCle){
			Text textPathFileMotCle = compositePageOptionModelLabels.getTextPathFileMotCle();
			Button btChoixMotCle = compositePageOptionModelLabels.getBtOptionMotCle();
			
			if(param==null || (param!=null && param.isModeIntegre()==false)) {
				textPathFileMotCle.setEnabled(true);
				textPathFileMotCle.setText(pathFileMotCle);
				btChoixMotCle.setSelection(true);
			}
		}
		
//		this.valueSeparateur = valueSeparateur;
		this.choixSeparateur = choixSeparateur;
		
		Text textSeparateur = compositePageOptionModelLabels.getTextSeparatuer();
		CCombo comboChoixFileSeparateur = compositePageOptionModelLabels.getComboChoixFileSeparateur();
		if(choixSeparateur){
			textSeparateur.setEnabled(true);
			textSeparateur.setText(valueSeparateur);
			
			comboChoixFileSeparateur.setEnabled(false);
		}else{
			if(param==null || (param!=null && param.isModeIntegre()==false)) {
				textSeparateur.setEnabled(false);
				textSeparateur.setText("");
			
				comboChoixFileSeparateur.setEnabled(true);
				//comboChoixFileSeparateur.select(0);
				comboChoixFileSeparateur.select(getIndexArray(ConstantModelLabels.VALUES_COMBO_TYPE_SEPARATEUR,
					valueSeparateur));
			}
		}
		this.valueSeparateur = valueSeparateur;
	}
	
	public int getIndexArray(String[] arrayString,String specificValue){
		
		int index = 0;
		for (int i = 0; i < arrayString.length; i++) {
			if(arrayString[i].equalsIgnoreCase(specificValue)){
				index = i;
			}
		}
		return index;
	}
	
	/**
	 * Retourne la liste des nom donnés aux différents paramétrage d'étiquettes enregistrés dans le dossier par défaut.
	 * @return
	 */
	public String[] listeNomParamEtiquette(String type) {
		String chemin = Const.PATH_FOLDER_PARAMETRES_ETIQUETTE;
		if(type.equals(DOSSIER_PARAM_TIERS)) {
			chemin+="/"+DOSSIER_PARAM_TIERS;
		} else if(type.equals(DOSSIER_PARAM_ARTICLE)) {
			chemin+="/"+DOSSIER_PARAM_ARTICLE;
		} else {
			return new String[]{};
		}
		return generationFileEtiquette.arrayPathFileParamEtiquette(chemin);
		
	}
	
	public String[] listeNomParamEtiquette(String[] ajoutDebut, String[] ajoutFin) {
		return generationFileEtiquette.arrayPathFileParamEtiquette(Const.PATH_FOLDER_PARAMETRES_ETIQUETTE,ajoutDebut,ajoutFin);
	}
	
	public void initialInterfacePageModelLables(String type){

		CCombo comboParamEtiquette = compositePageOptionModelLabels.getComboChoixParamEtiquette();
		
		
		Button btDeleteParaEtiquette = compositePageOptionModelLabels.getBtDeleteParaEtiquette();
		
		String[] arrayChoixParamEtiquette = listeNomParamEtiquette(type);
		
		if(arrayChoixParamEtiquette.length ==0 ){
			comboParamEtiquette.setEnabled(false);
		}else{
			comboParamEtiquette.setEnabled(true);
			btDeleteParaEtiquette.setEnabled(true);
		}
		
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		
		int indxChoixComboModelLetter = getIndexArray(ConstantModelLabels.VALUES_COMBO_TYPE_PAPER,
													  ConstantModelLabels.TYPE_PAPER_A4);
		
		updateCompostiPageModelAndValue(store.getBoolean(PreferenceConstants.CELL_BORDER),
										-1,
										store.getFloat(PreferenceConstants.LEFT_MARGIN),
										store.getFloat(PreferenceConstants.RIGHT_MARGIN),
										store.getFloat(PreferenceConstants.TOP_MARGIN),
										store.getFloat(PreferenceConstants.BOTTOM_MARGIN),
										
										store.getFloat(PreferenceConstants.LEFT_PADDING),
										store.getFloat(PreferenceConstants.RIGHT_PADDING),
										store.getFloat(PreferenceConstants.TOP_PADDING),
										store.getFloat(PreferenceConstants.BOTTOM_PADDING),
										
										store.getInt(PreferenceConstants.ROWS_ETIQUETTE),
										store.getInt(PreferenceConstants.COLUMS_ETIQUETTE),
										
										store.getFloat(PreferenceConstants.LARGEUR_ESPACE),
										store.getFloat(PreferenceConstants.HAUTEUR_ESPACE),
								
										Float.valueOf(ConstantModelLabels.A4_WIDTH),
										Float.valueOf(ConstantModelLabels.A4_HEIGHT),
										"",
										indxChoixComboModelLetter,
										arrayChoixParamEtiquette,
										ConstantModelLabels.TYPE_PAPER_A4,
										Const.C_FICHIER_LISTE_ATTRIBUTE_ETIQUETTE,
										false,
										";",
										false,
										false,false,"",
										0,0);
		
		pathFolderSaveEtiquete = Const.C_REPERTOIRE_BASE+Const.C_NOM_PROJET_TMP;
		pathFileMotCle = Const.C_FICHIER_LISTE_ATTRIBUTE_ETIQUETTE;
		
	}
	
//	public void addListenerCheckBoxModelLabel(final Button button){
//		button.addListener(SWT.FocusIn, new Listener(){
//
//			@Override
//			public void handleEvent(Event event) {
//				flagChoixPreDefinionModelLables = button.getSelection();
//				if(flagChoixPreDefinionModelLables){
//					compositePageOptionModelLabels.getTextHauteurPapier().setEnabled(false);
//					compositePageOptionModelLabels.getTextLargeurPapier().setEnabled(false);
//					compositePageOptionModelLabels.getComboModelLabels().setEnabled(true);
//					
//					modelLables = compositePageOptionModelLabels.getComboModelLabels().getText();
//					updateTextHauteurAndLargeurPapier(modelLables);
//					
//					
//				}else{
//					compositePageOptionModelLabels.getTextHauteurPapier().setEnabled(true);
//					compositePageOptionModelLabels.getTextLargeurPapier().setEnabled(true);
//					compositePageOptionModelLabels.getTextHauteurPapier().setText("");
//					compositePageOptionModelLabels.getTextLargeurPapier().setText("");
//					compositePageOptionModelLabels.getComboModelLabels().setEnabled(false);
//					
//					largeurPapier = null;
//					hauteurPapier = null;
//				}
//			}
//			
//		});
//	}
	
	public void addListenerCheckBoxGras(final Button button){
		button.addListener(SWT.Selection, new Listener(){

			@Override
			public void handleEvent(Event event) {
				isGras = button.getSelection(); 
				Text text = compositePageOptionFormatEtiquette.getTextFormatEtiquette();
				FontData fontData = text.getFont().getFontData()[0];
				if(isGras && !isItalic){
					fontData.setStyle(SWT.BOLD);
				}	
				else if(!isGras && isItalic){
					fontData.setStyle(SWT.ITALIC);
				}
				else if(isGras && isItalic){
					fontData.setStyle(SWT.BOLD | SWT.ITALIC);
				}else if(!isGras && !isItalic){
					fontData.setStyle(SWT.NORMAL);
				}
				text.setFont(new Font(compositePageOptionFormatEtiquette.getDisplay(),
						fontData));
			}
			
		});
	}
	public void addListenerCheckBoxItalic(final Button button){
		button.addListener(SWT.Selection, new Listener(){

			@Override
			public void handleEvent(Event event) {
				isItalic = button.getSelection(); 
				Text text = compositePageOptionFormatEtiquette.getTextFormatEtiquette();
				FontData fontData = text.getFont().getFontData()[0];
				if(isGras && !isItalic){
					fontData.setStyle(SWT.BOLD);
				}	
				else if(!isGras && isItalic){
					fontData.setStyle(SWT.ITALIC);
				}
				else if(isGras && isItalic){
					fontData.setStyle(SWT.BOLD | SWT.ITALIC);
				}else if(!isGras && !isItalic){
					fontData.setStyle(SWT.NORMAL);
				}
				text.setFont(new Font(compositePageOptionFormatEtiquette.getDisplay(),
						fontData));
			}
			
		});
	}
	
	public void addListenerCheckBoxCellBorder(final Button button){
		button.addListener(SWT.Selection, new Listener(){
			@Override
			public void handleEvent(Event event) {
				cellBorder = button.getSelection();
			}
		});
	}
	
	public void initialAndAddListenerComboSizeEtiquette(final Combo combo){
//		combo.setItems(makeItemCombo());
//		combo.select(0);
//		sizeEtiquette = combo.getText();
//		final Text text = compositePageOptionFormatEtiquette.getTextFormatEtiquette();
//		final FontData fontData = text.getFont().getFontData()[0];
		combo.addListener(SWT.Selection, new Listener(){
			@Override
			public void handleEvent(Event event) {
				Text text = compositePageOptionFormatEtiquette.getTextFormatEtiquette();
				FontData fontData = text.getFont().getFontData()[0];
				sizeEtiquette = combo.getText();
				fontData.setHeight(Integer.valueOf(sizeEtiquette));
				fontData.setName("Sans");
				text.setFont(new Font(compositePageOptionFormatEtiquette.getDisplay(),
						fontData));
			}
			
		});
	}
	
	public String[] makeItemCombo(){
		int min = 10;
		int max = 44;
	
		List<String> listSizeEtiquette = new LinkedList<String>();
		for (int i = min; i <= max; i++) {
			if(i<=20){
				listSizeEtiquette.add(String.valueOf(i));
			}
			else{
				i+=1;
				listSizeEtiquette.add(String.valueOf(i));
			}
		}
		String[] arrayItemEtiquette = new String[listSizeEtiquette.size()];
		for (int i = 0; i < listSizeEtiquette.size(); i++) {
			arrayItemEtiquette[i] = listSizeEtiquette.get(i);
		}
		return arrayItemEtiquette;
	}

	public void getAllFileParamEtiquette(){
		
	}
	//public void InitialAndAddListenerComboParamEtiquette(final Combo combo,final Button button){
	public void initialAndAddListenerComboParamEtiquette(final CCombo combo){
				
//		combo.setItems(generationFileEtiquette.arrayPathFileParamEtiquette(
//				Const.PATH_FOLDER_PARAMETRES_ETIQUETTE));
	
//		combo.addListener(SWT.Modify, new Listener(){
//
//			@Override
//			public void handleEvent(Event event) {
//				button.setEnabled(true);
//				newParamEtiquette = combo.getText();
//				combo.select(selectComboParamEtiquette);
//			}
//			
//		});
		combo.addListener(SWT.Selection, new Listener(){
			@Override
			public void handleEvent(Event event) {
				//newParamEtiquette = combo.getText();
				boolean flag = false;
				if(!nameParamEtiquette.equals(ConstantModelLabels.CHOIX_AUCUN_CCOMB_PARAM_ETIQUETTE)){
					flag = false;
				}
				nameParamEtiquette = combo.getText();
				selectComboParamEtiquette = combo.getSelectionIndex();
				String pathFileParamEtiquette = generationFileEtiquette.getMapFileParamEtiquette().get(nameParamEtiquette);
				newParamEtiquette = nameParamEtiquette;	
				if(!nameParamEtiquette.equals(ConstantModelLabels.CHOIX_DEFAUT_CCOMB_PARAM_ETIQUETTE)){
					flag = true;
//					button.setEnabled(false);
					//String pathFileParamEtiquette = generationFileEtiquette.getMapFileParamEtiquette().get(newParamEtiquette);
				}
				compositePageOptionModelLabels.getBtDeleteParaEtiquette().setEnabled(flag);
				changeValueCompositPageModelLabel(pathFileParamEtiquette,selectComboParamEtiquette);
			}
			
		});
	}
	
	public void changeValueCompositPageModelLabel(String pathFileParamEtiquette,int indexSelectCombo){
		//ParameterEtiqutte parameterEtiqutte = generationFileEtiquette.readObjet(pathFileParamEtiquette);
		//ParameterEtiqutte parameterEtiqutte = (ParameterEtiqutte)generationFileEtiquette.readObjetSerializable(pathFileParamEtiquette);
		
		ParameterEtiquette parameterEtiquette = null;
		if(pathFileParamEtiquette != null){
			parameterEtiquette = generationFileEtiquette.readObjectCastor(pathFileParamEtiquette);
		}else{
			Float largeurPapier = Float.valueOf(ConstantModelLabels.A4_WIDTH);
			Float hauteurPapier =  Float.valueOf(ConstantModelLabels.A4_HEIGHT);
			
			parameterEtiquette = new ParameterEtiquette(new Float(1.0),new Float(1.0),new Float(1.0),new Float(1.0),
													  new Float(1.0),new Float(1.0),new Float(1.0),new Float(1.0),
													  largeurPapier,hauteurPapier,
													  new Float(1.0),new Float(1.0),
													  ConstantModelLabels.TYPE_PAPER_A4,
													  3,3,
													  ConstantModelLabels.VALUES_COMBO_TYPE_SEPARATEUR[0],
													  Const.C_FICHIER_LISTE_ATTRIBUTE_ETIQUETTE);
		}
		 
		
		this.parameterEtiqutte = parameterEtiquette;
		
		boolean cellBorder = parameterEtiquette.isCellBorder();
		
		Float marginLeft = parameterEtiquette.getLeftMargin();
		Float marginRight = parameterEtiquette.getRightMargin();
		Float marginTop = parameterEtiquette.getTopMargin();
		Float marginButtom = parameterEtiquette.getBottomMargin();
		
		Float paddingLeft = parameterEtiquette.getLeftPadding();
		Float paddingRight = parameterEtiquette.getRightPadding();
		Float paddingTop = parameterEtiquette.getTopPadding();
		Float paddingButtom = parameterEtiquette.getBottomPadding();
		
		Integer nombreRows = parameterEtiquette.getNombreRows();
		Integer nombrecolumns = parameterEtiquette.getNombreColumns();
		
		Float largeurPapier = parameterEtiquette.getLargeurPapier();
		Float hauteurPapier = parameterEtiquette.getHauteurPapier();
		
		Float largeurEspace = parameterEtiquette.getLargeurEspace();
		Float hauteurEspace = parameterEtiquette.getHauteurEspace();
		
		String pathFileExtraction = parameterEtiquette.getPathFileExtraction();
		String modelLables = parameterEtiquette.getTypePaper();
		
		String pathFileMotCle = parameterEtiquette.getPathFileMotCle();
		boolean choixMotCle = parameterEtiquette.isChoixMotCle();
		
		String valueSeparateur = parameterEtiquette.getValueSeparateur();
		boolean choixSeparateur = parameterEtiquette.isChoixSeparateur();
		
		int choixComboModelLetter = getIndexArray(ConstantModelLabels.VALUES_COMBO_TYPE_PAPER, 
									modelLables);
//		boolean flagChoixPreDefinionModelLables = true;
		
		String type = param.getType();
		String chemin = Const.PATH_FOLDER_PARAMETRES_ETIQUETTE;
		if(type.equals(DOSSIER_PARAM_TIERS)) {
			chemin+="/"+DOSSIER_PARAM_TIERS;
		} else if(type.equals(DOSSIER_PARAM_ARTICLE)) {
			chemin+="/"+DOSSIER_PARAM_ARTICLE;
		} 
		
		String[] arrayChoixParamEtiquette = generationFileEtiquette.arrayPathFileParamEtiquette(chemin);
		
		updateCompostiPageModelAndValue(cellBorder,indexSelectCombo,marginLeft, marginRight, marginTop, marginButtom, 
				paddingLeft, paddingRight, paddingTop, paddingButtom, 
				nombreRows, nombrecolumns, largeurEspace, hauteurEspace, 
				largeurPapier, hauteurPapier, pathFileExtraction,
				choixComboModelLetter, arrayChoixParamEtiquette, modelLables,
				pathFileMotCle,choixMotCle,valueSeparateur,choixSeparateur,
				parameterEtiquette.isGras(),parameterEtiquette.isItalic(),parameterEtiquette.getTextModelEtiquette(),
				parameterEtiquette.getDecalage(),parameterEtiquette.getQuantite());
		
		
//		String pathFileExtraction =  parameterEtiqutte.getPathFileExtraction();
//		compositePageOptionModelLabels.getTextPathFileExtraction().setText(pathFileExtraction);
//		compositePageOptionModelLabels.layout();		
	}
	
	public void initialAndAddListenerCombo(final CCombo combo){
//		combo.setItems(ConstantModelLabels.VALUES_COMBO_TYPE_PAPER);
//		combo.select(0);
//		modelLables = ConstantModelLabels.TYPE_PAPER_A4;
//		combo.addSelectionListener(new SelectionAdapter(){
//			
//		});
		combo.addListener(SWT.Selection, new Listener(){
			@Override
			public void handleEvent(Event event) {
				modelLables = combo.getText();
				updateTextHauteurAndLargeurPapier(modelLables);
			}
			
		});
	}
	
	

	/**
	 *  According to choice, update largeur and hauteur paper
	 * @param modelLables ==> Model Etiquette EX:A4
	 */
	public void updateTextHauteurAndLargeurPapier(String modelLables){
		boolean flagEnable = false;
		
		String textHauteurPapier = null;
		String textLargeurPapier = null;

		if(modelLables.equalsIgnoreCase(ConstantModelLabels.TYPE_PAPER_US_LEGAL)){
			textHauteurPapier = ConstantModelLabels.US_LEGAL_HEIGHT;
			textLargeurPapier = ConstantModelLabels.US_LEGAL_WIDTH;
			compositePageOptionModelLabels.getTextHauteurPapier().
				setText(ConstantModelLabels.US_LEGAL_HEIGHT);
			compositePageOptionModelLabels.getTextLargeurPapier().
				setText(ConstantModelLabels.US_LEGAL_WIDTH);
			largeurPapier = Float.valueOf(ConstantModelLabels.US_LEGAL_WIDTH);
			hauteurPapier = Float.valueOf(ConstantModelLabels.US_LEGAL_HEIGHT);
			
		}else if(modelLables.equalsIgnoreCase(ConstantModelLabels.TYPE_PAPER_US_LETTER)){
			textHauteurPapier = ConstantModelLabels.US_LETTER_HEIGHT;
			textLargeurPapier = ConstantModelLabels.US_LETTER_WIDTH;
			compositePageOptionModelLabels.getTextHauteurPapier().
				setText(ConstantModelLabels.US_LETTER_HEIGHT);
			compositePageOptionModelLabels.getTextLargeurPapier().
				setText(ConstantModelLabels.US_LETTER_WIDTH);
			largeurPapier = Float.valueOf(ConstantModelLabels.US_LETTER_WIDTH);
			hauteurPapier = Float.valueOf(ConstantModelLabels.US_LETTER_HEIGHT);
		}else if(modelLables.equalsIgnoreCase(ConstantModelLabels.TYPE_PAPER_A4)){
			textHauteurPapier = ConstantModelLabels.A4_HEIGHT;
			textLargeurPapier = ConstantModelLabels.A4_WIDTH;
			compositePageOptionModelLabels.getTextHauteurPapier().
				setText(ConstantModelLabels.A4_HEIGHT);
			compositePageOptionModelLabels.getTextLargeurPapier().
				setText(ConstantModelLabels.A4_WIDTH);
			largeurPapier = Float.valueOf(ConstantModelLabels.A4_WIDTH);
			hauteurPapier = Float.valueOf(ConstantModelLabels.A4_HEIGHT);
		}else if(modelLables.equalsIgnoreCase(ConstantModelLabels.TYPE_PAPER_CUSTOM)){
			compositePageOptionModelLabels.getTextHauteurPapier().setText("");
			compositePageOptionModelLabels.getTextLargeurPapier().setText("");
			flagEnable = true;
			largeurPapier = null;
			hauteurPapier = null;
		}
		
//		compositePageOptionModelLabels.getTextHauteurPapier().setText(textHauteurPapier);
//		compositePageOptionModelLabels.getTextLargeurPapier().setText(textLargeurPapier);
		
		compositePageOptionModelLabels.getTextHauteurPapier().setEnabled(flagEnable);
		compositePageOptionModelLabels.getTextLargeurPapier().setEnabled(flagEnable);
	}
	
	public Shell getShell() {
		return shell;
	}

	public void setShell(Shell shell) {
		this.shell = shell;
	}

	public CompositePageOptionModelLabels getCompositePageOptionModelLabels() {
		return compositePageOptionModelLabels;
	}

	public void setCompositePageOptionModelLabels(
			CompositePageOptionModelLabels compositePageOptionModelLabels) {
		this.compositePageOptionModelLabels = compositePageOptionModelLabels;
	}

	public String getModelLables() {
		return modelLables;
	}

	public void setModelLables(String modelLables) {
		this.modelLables = modelLables;
	}
	
	public WizardPageModelLables getWizardPageModelLables() {
		return wizardPageModelLables;
	}
	
	public void setWizardPageModelLables(WizardPageModelLables wizardPageModelLables) {
		this.wizardPageModelLables = wizardPageModelLables;
	}

	public Integer getNombreRows() {
		return nombreRows;
	}

	public Integer getNombrecolumns() {
		return nombrecolumns;
	}

	public Float getMarginLeft() {
		return marginLeft;
	}

	public boolean isFlagPageModelLables() {
		return flagPageModelLables;
	}

	public boolean isFlagMargin() {
		return flagMargin;
	}

	public GenerationFileEtiquette getGenerationFileEtiquette() {
		return generationFileEtiquette;
	}

	public void setGenerationFileEtiquette(
			GenerationFileEtiquette generationFileEtiquette) {
		this.generationFileEtiquette = generationFileEtiquette;
	}

	public String getPathFolderSaveEtiquete() {
		return pathFolderSaveEtiquete;
	}

	public void setPathFolderSaveEtiquete(String pathFolderSaveEtiquete) {
		this.pathFolderSaveEtiquete = pathFolderSaveEtiquete;
	}

	public Float getMarginRight() {
		return marginRight;
	}

	public Float getMarginTop() {
		return marginTop;
	}

	public Float getMarginButtom() {
		return marginButtom;
	}

	public Float getLargeurPapier() {
		return largeurPapier;
	}

	public Float getHauteurPapier() {
		return hauteurPapier;
	}

//	public boolean isFlagChoixPreDefinionModelLables() {
//		return flagChoixPreDefinionModelLables;
//	}

	public String getPathFileExtraction() {
		return pathFileExtraction;
	}

	public void setPathFileExtraction(String pathFileExtraction) {
		this.pathFileExtraction = pathFileExtraction;
	}

	public Float getLargeurEspace() {
		return largeurEspace;
	}

	public Float getHauteurEspace() {
		return hauteurEspace;
	}

	public Float getPaddingLeft() {
		return paddingLeft;
	}

	public Float getPaddingRight() {
		return paddingRight;
	}

	public Float getPaddingTop() {
		return paddingTop;
	}

	public Float getPaddingButtom() {
		return paddingButtom;
	}

	public boolean isCellBorder() {
		return cellBorder;
	}

	public CompositePageOptionFormatEtiquette getCompositePageOptionFormatEtiquette() {
		return compositePageOptionFormatEtiquette;
	}

	public void setCompositePageOptionFormatEtiquette(
			CompositePageOptionFormatEtiquette compositePageOptionFormatEtiquette) {
		this.compositePageOptionFormatEtiquette = compositePageOptionFormatEtiquette;
	}

	public WizardPageFormatEtiquette getWizardPageFormatEtiquette() {
		return wizardPageFormatEtiquette;
	}

	public void setWizardPageFormatEtiquette(
			WizardPageFormatEtiquette wizardPageFormatEtiquette) {
		this.wizardPageFormatEtiquette = wizardPageFormatEtiquette;
	}
	
	public String getTextFormatEtiquette() {
		return textFormatEtiquette;
	}
	
	public void setTextFormatEtiquette(String textFormatEtiquette) {
		this.textFormatEtiquette = textFormatEtiquette;
	}
	
	public boolean isFlagPageFormatEtiquette() {
		return flagPageFormatEtiquette;
	}
	
	public void setFlagPageFormatEtiquette(boolean flagPageFormatEtiquette) {
		this.flagPageFormatEtiquette = flagPageFormatEtiquette;
	}
	
	public boolean isFlagActiveScrollBar() {
		return flagActiveScrollBar;
	}
	
	public void setFlagActiveScrollBar(boolean flagActiveScrollBar) {
		this.flagActiveScrollBar = flagActiveScrollBar;
	}
	
	public String getTextFormatEtiquetteFinal() {
		return textFormatEtiquetteFinal;
	}
	
	public void setTextFormatEtiquetteFinal(String textFormatEtiquetteFinal) {
		this.textFormatEtiquetteFinal = textFormatEtiquetteFinal;
	}

	public boolean isGras() {
		return isGras;
	}

	public void setGras(boolean isGras) {
		this.isGras = isGras;
	}

	public boolean isItalic() {
		return isItalic;
	}

	public void setItalic(boolean isItalic) {
		this.isItalic = isItalic;
	}

	public String getSizeEtiquette() {
		return sizeEtiquette;
	}

	public void setSizeEtiquette(String sizeEtiquette) {
		this.sizeEtiquette = sizeEtiquette;
	}

	public String getNameParamEtiquette() {
		return nameParamEtiquette;
	}

	public void setNameParamEtiquette(String nameParamEtiquette) {
		this.nameParamEtiquette = nameParamEtiquette;
	}

	public String getNewParamEtiquette() {
		return newParamEtiquette;
	}

	public void setNewParamEtiquette(String newParamEtiquette) {
		this.newParamEtiquette = newParamEtiquette;
	}

	public ParameterEtiquette getParameterEtiquette() {
		return parameterEtiqutte;
	}

	public void setParameterEtiqutte(ParameterEtiquette parameterEtiqutte) {
		this.parameterEtiqutte = parameterEtiqutte;
	}

	public String getValueSeparateur() {
		return valueSeparateur;
	}

	public void setValueSeparateur(String valueSeparateur) {
		this.valueSeparateur = valueSeparateur;
	}

	public boolean isChoixSeparateur() {
		return choixSeparateur;
	}

	public void setChoixSeparateur(boolean choixSeparateur) {
		this.choixSeparateur = choixSeparateur;
	}

	public boolean isChoixFileMotCle() {
		return choixFileMotCle;
	}

	public void setChoixFileMotCle(boolean choixFileMotCle) {
		this.choixFileMotCle = choixFileMotCle;
	}

	public boolean isFlagPathMotCle() {
		return flagPathMotCle;
	}

	public void setFlagPathMotCle(boolean flagPathMotCle) {
		this.flagPathMotCle = flagPathMotCle;
	}

	public String getPathFileMotCle() {
		return pathFileMotCle;
	}

	public void setPathFileMotCle(String pathFileMotCle) {
		this.pathFileMotCle = pathFileMotCle;
	}

	public boolean isFlagSeparateur() {
		return flagSeparateur;
	}

	public void setFlagSeparateur(boolean flagSeparateur) {
		this.flagSeparateur = flagSeparateur;
	}

	public boolean isChoixSaveEtiquette() {
		return choixSaveEtiquette;
	}

	public void setChoixSaveEtiquette(boolean choixSaveEtiquette) {
		this.choixSaveEtiquette = choixSaveEtiquette;
	}

	public boolean isFlagNameEtiquette() {
		return flagNameEtiquette;
	}

	public void setFlagNameEtiquette(boolean flagNameEtiquette) {
		this.flagNameEtiquette = flagNameEtiquette;
	}

	public boolean isFlagTextEtiquette() {
		return flagTextEtiquette;
	}

	public void setFlagTextEtiquette(boolean flagTextEtiquette) {
		this.flagTextEtiquette = flagTextEtiquette;
	}


	public ParamWizardEtiquettes getParam() {
		return param;
	}


	public void setParam(ParamWizardEtiquettes param) {
		this.param = param;
	}


	public Integer getDecalage() {
		return decalage;
	}


	public Integer getQuantite() {
		return quantite;
	}
}

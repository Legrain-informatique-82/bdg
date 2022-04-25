package fr.legrain.generationModelLettreWS.wizard;

import fr.legrain.generationModelLettreMS.divers.ConstModelLettre;
import fr.legrain.generationModelLettreMS.divers.FonctionGeneral;
import fr.legrain.generationModelLettreMS.divers.IgenerationModelLettre;
import fr.legrain.generationModelLettreMS.divers.ParametrePublicPostage;
import fr.legrain.gestCom.Appli.Const;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;



public class WizardController {

	private IgenerationModelLettre igenerationModelLettre;
	
	private CompositePageChoiceDataLetter compositePageChoiceDataLetter;
	private CompositePageChoicePugin compositePageChoicePugin;
	private CompositePageChoiceModelLetter compositePageChoiceModelLetter;
	//private ConserverValuesPageWizard conserverValuesPageWizard = new ConserverValuesPageWizard();
	private Shell shell;
	private WizardPageChoiceDataLettre wizardPageChoiceDataLettre;
	private WizardPageChoicePlugin wizardPageChoicePlugin;
	private WizardPageChoiceModelLettre wizardPageChoiceModelLettre;
	private String pathFileExtraction = null;
	private String pathFileMotCle = null;
	private String pathFileModelLettre = null;
	private String pathSavePublipostage = null;
	private FonctionGeneral fonctionGeneral;
		
	private boolean flagButtonNextPagePlugin = false;
	private boolean flagPageModelLettre = false;
	
	private boolean flagPageChoixPlugin = false;
	
	private boolean isExistFileExtraction = false;
	private boolean isExistFileMotCle = false;
	private boolean isExistFileModel = false;
	private boolean isFolderPublipostage = false;
	private boolean isSelectBtnCheckBoxMotCle = false;
	private boolean isSelectBtnCheckBoxPublipostage = false;
	
	
	private boolean flagSeparateur = false;
	
	private boolean flagFileModelLettre = false;
	private boolean flagNameParamPublicpostage = false;
	private ParametrePublicPostage parametrePublicPostage;
	
	private boolean isChoixCcomboParamPublicPostage = false;
	
	private String newNameParamPublicPostage;
	
	private String textChoixCcombo;
	

	private Listener listenervalidPageDataLetter = new Listener() {
		@Override
		public void handleEvent(Event event) {
			// TODO Auto-generated method stub
			wizardPageChoiceDataLettre.setPageComplete(validatePageDataLettre());
		}
	};
	
	private Listener listenervalidPagePlugin = new Listener() {
		@Override
		public void handleEvent(Event event) {
			// TODO Auto-generated method stub
			wizardPageChoicePlugin.setPageComplete(validatePagePlugin());
		}
	};
	
	private Listener listenervalidPageModelLettre = new Listener() {
		@Override
		public void handleEvent(Event event) {
			// TODO Auto-generated method stub
			wizardPageChoiceModelLettre.setPageComplete(validatePageModelLettre());
		}
	};
	
	public WizardController() {
		super();
		// TODO Auto-generated constructor stub	
	}
	/**
	 * valider pour page model Lettre 
	 * @return
	 */
//	public boolean validatePageModelLettre(boolean flag){
	public boolean validatePageModelLettre(){
//		wizardPageChoiceModelLettre.setMessage(null);
//		wizardPageChoiceModelLettre.setErrorMessage(null);
		
		reinitialMessageWizardPage(wizardPageChoiceModelLettre);
		boolean flagPathModellettre = false;
		boolean flagNamePublicPostage = false;
		if(parametrePublicPostage.isSavePublicpostage()){
			String namePublicpostage = compositePageChoiceModelLetter.getTextNameParamPublicpostage().getText();
			if(namePublicpostage != null && namePublicpostage.length() != 0){
				flagNamePublicPostage = true;
				//parametrePublicPostage.setNameParamPublicPostage(namePublicpostage);
				newNameParamPublicPostage = namePublicpostage;
			}
			else{
				flagNamePublicPostage = false;
				wizardPageChoiceModelLettre.setMessage(ConstModelLettre.MESSAGE_NAME_PUBLICPOSTAGE,
						DialogPage.ERROR);
			}
		}else{
			flagNamePublicPostage = true;
		}
		String pathFileModelLettre = parametrePublicPostage.getPathFileModelLettre();
		if(pathFileModelLettre != null && pathFileModelLettre.length() != 0){
			if(pathFileModelLettre != null && pathFileModelLettre.length() != 0){
				flagPathModellettre = true;
			}else{
				flagPathModellettre = false;
			}
		}
		flagPageModelLettre = flagNamePublicPostage && flagPathModellettre;
		//if(!flag){
//		if(!flagPageModelLettre){
//			wizardPageChoiceModelLettre.setMessage(ConstModelLettre.MESSAGE_MODEL_LETTER_MOT_CLE,
//					DialogPage.ERROR);
//		}
		return false;
	}
	/*
	 * pour page WizardPageChoiceModelLettre
	 */
	public void initialActionListenerPageModelLetter(List<String> listPathModelLettre){
		try {
			addButtonPageModelLetter(listPathModelLettre);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		Text textNameParamPublicpostage = compositePageChoiceModelLetter.getTextNameParamPublicpostage();
		Button btCheckBoxSave = compositePageChoiceModelLetter.getBtCheckBoxSave();
		boolean isSavePublicpostage = parametrePublicPostage.isSavePublicpostage();
		String nameParamPublicPostage = parametrePublicPostage.getNameParamPublicPostage();
		boolean flag = fonctionGeneral.checkValueString(nameParamPublicPostage);
		if(flag){
			textNameParamPublicpostage.setText(nameParamPublicPostage);
		}
		
		initialPageChoiceModelLettre(textNameParamPublicpostage,btCheckBoxSave,isSavePublicpostage);
		
		textNameParamPublicpostage.addListener(SWT.Modify,listenervalidPageModelLettre);
		addListenerbtChoixSavePublicpostagePageModelLettre(btCheckBoxSave,textNameParamPublicpostage);
		
		//wizardPageChoiceModelLettre.setPageComplete(validatePagePlugin());
		
		//wizardPageChoiceModelLettre.setPageComplete(false);
	}

	public void addListenerbtChoixSavePublicpostagePageModelLettre(final Button button,final Text text){
		
		button.addListener(SWT.Selection, new Listener(){
			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub
				boolean isSavePublicpostage = button.getSelection();
				parametrePublicPostage.setSavePublicpostage(isSavePublicpostage);
				
//				text.setText("");
				if(isSavePublicpostage){
					text.setEnabled(true);
					text.setEditable(true);
					flagPageModelLettre = false;
				}else{
					text.setEnabled(false);		
					flagPageModelLettre = true;
//					flagNameEtiquette = true;
				}
				wizardPageChoiceModelLettre.setPageComplete(validatePageModelLettre());
			}
		});
	}
	public void initialPageChoiceModelLettre(Text text,Button button,boolean isPageSave){
		
		if(isChoixCcomboParamPublicPostage){
			if(isPageSave){
				text.setText(parametrePublicPostage.getNameParamPublicPostage());
				text.setEnabled(true);
				button.setSelection(parametrePublicPostage.isSavePublicpostage());
			}
		}
		
		text.setEnabled(false);
		button.setSelection(false);
		
	}
	public void reinitialMessageWizardPage(WizardPage WizardPage){
		WizardPage.setMessage(null);
		WizardPage.setErrorMessage(null);
	}
	
	public void addButtonPageModelLetter(List<String> pathModelLettre){
		
		for (int i = 0; i < pathModelLettre.size(); i++) {
			String pathFileModel = pathModelLettre.get(i);
			final Button buttonPlugin = new Button(compositePageChoiceModelLetter.getCompositeListModelLetter(),
											SWT.RADIO |SWT.LEFT);
			buttonPlugin.setText(pathModelLettre.get(i));
			buttonPlugin.addListener(SWT.Selection, new Listener(){
				@Override
				public void handleEvent(Event event) {
					
					String pathFileModelLettre = buttonPlugin.getText();
					System.out.println(pathFileModelLettre);
					parametrePublicPostage.setPathFileModelLettre(pathFileModelLettre);
					fonctionGeneral.setPathFileModelLettre(buttonPlugin.getText());
					String pathFileMotCle = parametrePublicPostage.getPathFileMotCle();
					fonctionGeneral.setPathFileMotCle(pathFileMotCle);
					
					flagFileModelLettre = fonctionGeneral.verityFileExtractionAndModelLettre(wizardPageChoiceModelLettre,pathFileMotCle,
							parametrePublicPostage.getValueSeparateur());
					wizardPageChoiceModelLettre.setPageComplete(validatePageModelLettre());
					// TODO Auto-generated method stub
//					fonctionGeneral.setPathFileModelLettre(pathFileModel);
//					fonctionGeneral.setPathFileMotCle(Const.C_FICHIER_LISTE_ATTRIBUTE_LETTRE);
//					boolean isChoix = true;
//					wizardPageChoiceModelLettre.setPageComplete(flagPageModelLettre);
//					String section = fonctionGeneral.getNamePlugin();
//					boolean flag = false;
//					try {
//						flag = fonctionGeneral.verityFileExtractionAndModelLettre(
//								wizardPageChoiceModelLettre,ConstModelLettre.MESSAGE_MODEL_LETTER_MOT_CLE);
//					} catch (Exception e) {
//						// TODO: handle exception
//						e.printStackTrace();
//					}
//					if(isChoix && flag){
//						flagPageModelLettre = true;
//						wizardPageChoiceModelLettre.setPageComplete(true);
//					}else{
//						wizardPageChoiceModelLettre.setPageComplete(validatePageModelLettre(flag));
//					}
					
				}
			});
		}
		compositePageChoiceModelLetter.getCompositeListModelLetter().layout();
	}

	public void deleteButtonRadio(Composite composite){
		
		Control[] controls = composite.getChildren();
		for (Control control : controls) {
			control.dispose();	
		}
	}
	
	public boolean checkPageModelLettreCanFinish(){
		boolean flag = false;
		//boolean isSelect = false;
		Control[] controls = compositePageChoiceModelLetter.getCompositeListModelLetter().getChildren();
		for (Control control : controls) {
			boolean isSelect =  ((Button)control).getSelection();
			if(isSelect){
				flag =isSelect;
				break;
			}
		}
		return true;
	}
	
//	public void initialEtatElementPagePlugin(){
//		compositePageChoicePugin.getTfPathFileModelLettre().setEnabled(false);
//		compositePageChoicePugin.getBtnPathFileModelLettre().setEnabled(false);
//	}
//	
//	public void initialEtatElementPageDataLettre(){
//		compositePageChoiceDataLetter.getTfPathFileMotCle().setEnabled(false);
//		compositePageChoiceDataLetter.getBtnPathFileMotCle().setEnabled(false);
//	}
	
	//public void initialEtatElementWizardPage(Button button,Text text,boolean flag){
	public void initialEtatElementWizardPage(CompositePageChoicePugin composite){
		
		composite.getBtnPathFileModelLettre().setEnabled(false);
		composite.getTfPathFileModelLettre().setEnabled(false);
		
		composite.getTextNameParamPublicpostage().setEditable(false);
	
	}
	
	public void initialActionListenerPagePlugin(){
				
		//initialEtatElementPagePlugin();
		
//		initialEtatElementWizardPage(compositePageChoicePugin.getBtnPathFileModelLettre(),
//		compositePageChoicePugin.getTfPathFileModelLettre(),false);
		initialEtatElementWizardPage(compositePageChoicePugin);
		
		addListenerButtoncheckBox();
		
		addListenerBtParcourirModelLettre(compositePageChoicePugin.getBtnPathFileModelLettre(),
				compositePageChoicePugin.getTfPathFileModelLettre());
			
		addListenerTextPagePlugin();
		
		Text textNameParamPublicpostage = compositePageChoicePugin.getTextNameParamPublicpostage();
		Button BtCheckBoxSave = compositePageChoicePugin.getBtCheckBoxSave();
		boolean isSavePublicpostagePagePlugin = parametrePublicPostage.isSavePublicpostagePagePlugin();
		
		initialPageChoiceModelLettre(textNameParamPublicpostage, BtCheckBoxSave,isSavePublicpostagePagePlugin);
		
		textNameParamPublicpostage.addListener(SWT.Modify,listenervalidPagePlugin);
		
		
		addListenerbtChoixSavePublicpostage(BtCheckBoxSave,textNameParamPublicpostage);
		
		wizardPageChoicePlugin.setPageComplete(validatePagePlugin());
	}
	
	public void addListenerbtChoixSavePublicpostage(final Button button,final Text text){
		
		button.addListener(SWT.Selection, new Listener(){
			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub
				boolean isSavePublicpostage = button.getSelection();
				parametrePublicPostage.setSavePublicpostagePagePlugin(isSavePublicpostage);
				
//				text.setText("");
				if(isSavePublicpostage){
					text.setEnabled(true);
					text.setEditable(true);
					flagNameParamPublicpostage = false;
				}else{
					text.setEnabled(false);		
					flagNameParamPublicpostage = true;
//					flagNameEtiquette = true;
				}
				wizardPageChoicePlugin.setPageComplete(validatePagePlugin());
			}
		});
	}
	/**
	 * ajouter listener pour text de CompositePageChoicePugin 
	 */
	public void addListenerTextPagePlugin(){
		compositePageChoicePugin.getTfPathFileModelLettre().addListener(SWT.Modify,listenervalidPagePlugin);
	}

	public void addListenerBtParcourirSavePublicpostage(Button button,final Text text){
		button.addListener(SWT.Selection, new Listener(){
			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub
				DirectoryDialog directoryDialog= new DirectoryDialog(shell);
				String pathFileSavePublicPostage = directoryDialog.open();
				text.setText(pathFileSavePublicPostage);
				parametrePublicPostage.setPathFileSavePublicPostage(pathFileSavePublicPostage);
			}
		});
	}
	/**
	 * ajouter listener pour Button de compositePageChoiceDataLetter 
	 */
	public void addListenerBtParcourirModelLettre(Button button,final Text text){
		button.addListener(SWT.Selection, new Listener(){
			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub
				FileDialog fileDialog = new FileDialog(shell);
				String pathFileModelLettre = fileDialog.open(); 
				parametrePublicPostage.setPathFileModelLettre(pathFileModelLettre);
				text.setText(pathFileModelLettre);
				
			}
		});
	}
	
	/**
	 * ajouter listener pour Button de compositePageChoiceDataLetter 
	 */
	public void addListenerBtParcourirFileExtraction(Button button,final Text text){
		button.addListener(SWT.Selection, new Listener(){
			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub
				FileDialog fileDialog = new FileDialog(shell);
				String pathFileExtraction = fileDialog.open(); 
				text.setText(pathFileExtraction);
				parametrePublicPostage.setPathFileExtraction(pathFileExtraction);
			}
		});
	}
	
	public void addListenerBtParcourirFileMotCle(Button button,final Text text){
		button.addListener(SWT.Selection, new Listener(){
			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub
				FileDialog fileDialog = new FileDialog(shell);
				String pathFileMotCle = fileDialog.open(); 
				text.setText(pathFileMotCle);
				parametrePublicPostage.setPathFileMotCle(pathFileMotCle);
			}
		});
	}
	
	public boolean checkFileTypeOffice(String pathFile,WizardPage wizardPage,boolean isExistFile){
		String[] arrayFileTypeOffice = null;
		String aa = fonctionGeneral.getTypeOffice();
//		String aa = ConstModelLettre.TYPE_OFFICE_WO;
		if(fonctionGeneral.getTypeOffice().equals("OO")){
			arrayFileTypeOffice = ConstModelLettre.TYPE_FILE_OFFICE_OO;
		}else if(fonctionGeneral.getTypeOffice().equals("WO")){
			arrayFileTypeOffice = ConstModelLettre.TYPE_FILE_OFFICE_WS;
		}
		boolean flag = false;
		if(isExistFile){
			File file = new File(pathFile);
			//for (int i = 0; i < ConstModelLettre.TYPE_FILE_OFFICE_OO.length; i++) {
			for (int i = 0; i < arrayFileTypeOffice.length; i++) {
				flag = file.getName().endsWith(arrayFileTypeOffice[i]);
				if(flag){
					break;
				}
			}
			if(!flag){
				wizardPage.setMessage(ConstModelLettre.MESSAGE_FILE_TYPE_OFFICE, DialogPage.ERROR);
			}
		}
		return flag;
	}
	
	public boolean checkFolderExist(String pathFolder,WizardPage wizardPage){
		
		boolean flag = false;
		File folder = new File(pathFolder);
		if(folder.exists() && folder.isDirectory()){
			flag = true;
		}
		if(!flag){
			wizardPage.setMessage(ConstModelLettre.MESSAGE_FOLDER_NO_EXIST, DialogPage.ERROR);
		}
		
		return flag;
	}
	/**
	 * check out model of letter i
	 * @param pathFile 
	 * @param wizardPage ==> en fonction de la page
	 * @return
	 */
	public boolean checkFileExist(String pathFile,WizardPage wizardPage){
		
		boolean flag = false;
		File file = new File(pathFile);
		if(file.exists() && file.isFile()){
			flag = true;
		}
		if(!flag){
			wizardPage.setMessage(ConstModelLettre.MESSAGE_FILE_NO_EXIST, DialogPage.ERROR);
		}
		
		return flag;
	}
	/**
	 * valider pour page plugin 
	 * @return
	 */
	public boolean validatePagePlugin(){
//		wizardPageChoicePlugin.setMessage(null);
//		wizardPageChoicePlugin.setErrorMessage(null);
//		boolean flagValidate = false;
//		try {
//			if(flagButtonNextPagePlugin){
//				flagValidate = true;
//				fonctionGeneral.setPathFileModelLettre(null);
//			}else {
//				flagValidate = false;
//							
//				pathFileModelLettre = compositePageChoicePugin.getTfPathFileModelLettre().getText();
//				
//				if(pathFileModelLettre!=null && !pathFileModelLettre.equals("")){
//					updateElementEnsable();
//					boolean flag = checkFileExist(pathFileModelLettre,wizardPageChoicePlugin);
//					if(flag){
//						fonctionGeneral.setPathFileModelLettre(pathFileModelLettre);
//						fonctionGeneral.setNamePlugin(null);
//					}
//				}
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//	
//		//boolean flagValidate = flagButtonNext;
//
//		return flagValidate;
		
		wizardPageChoicePlugin.setMessage(null);
		wizardPageChoicePlugin.setErrorMessage(null);
		boolean flagValidate = false;
		//boolean flagValidate = flagButtonNext;
//		if(flagButtonNextPagePlugin){
		if(!parametrePublicPostage.isChoixModelLettre()){
			String namePlugin = parametrePublicPostage.getNamePlugin();
			if(namePlugin != null && namePlugin.length() != 0){
				flagPageChoixPlugin = true;
				fonctionGeneral.setPathFileModelLettre(null);
			}else{
				flagPageChoixPlugin = false;
			}
		}
		else{			
			pathFileModelLettre = compositePageChoicePugin.getTfPathFileModelLettre().getText();
			
			if(pathFileModelLettre!=null && !pathFileModelLettre.equals("")){
				updateElementEnsable();
				boolean flag = checkFileExist(pathFileModelLettre,wizardPageChoicePlugin);
				flagPageChoixPlugin = checkFileTypeOffice(pathFileModelLettre, wizardPageChoicePlugin, flag);
				flagFileModelLettre = flagPageChoixPlugin;
				
				if(flagPageChoixPlugin){
					fonctionGeneral.setPathFileModelLettre(pathFileModelLettre);
					fonctionGeneral.setNamePlugin(null);
					
					parametrePublicPostage.setPathFileModelLettre(pathFileModelLettre);
					parametrePublicPostage.setNamePlugin("");
				}
			}
			else{
//				flagPageChoixPlugin = false;
				flagFileModelLettre = false;
				parametrePublicPostage.setPathFileModelLettre("");
			}
				flagPageChoixPlugin = false;	
		}
		//if(parametrePublicPostage.isSavePublicpostage()){
		if(parametrePublicPostage.isSavePublicpostagePagePlugin()){
			String namePublicpostage = compositePageChoicePugin.getTextNameParamPublicpostage().getText();
			if(namePublicpostage.trim().length() != 0 && namePublicpostage != null){
//				flagPageChoixPlugin = true;
				//parametrePublicPostage.setNameParamPublicPostage(namePublicpostage);
				newNameParamPublicPostage = namePublicpostage;
				parametrePublicPostage.setNameParamPublicPostage(newNameParamPublicPostage);
				flagNameParamPublicpostage = true;
				flagPageChoixPlugin = false;
			}else{
				wizardPageChoicePlugin.setMessage(ConstModelLettre.MESSAGE_NAME_PUBLICPOSTAGE, DialogPage.ERROR);
				flagPageChoixPlugin = false;
				flagNameParamPublicpostage = false;
			}	
		}
		return flagPageChoixPlugin;
	}
	public void updateElementEnsable(){
		Control[] controls = compositePageChoicePugin.getCompositeListPlugin().getChildren();
		for (Control control : controls) {
			control.setEnabled(false);
		}
	}
	/**
	 * ajouter listener pour Button CheckBox de CompositePageChoicePugin
	 */
	public void addListenerButtoncheckBox(){
		compositePageChoicePugin.getCheckBoxModelLettre().addListener(SWT.Selection,new Listener(){
			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub
				boolean flag = compositePageChoicePugin.getCheckBoxModelLettre().getSelection();
				parametrePublicPostage.setChoixModelLettre(flag);
//				parametrePublicPostage.setSavePublicpostage(flag);
				flagPageChoixPlugin = flag;
				if(flag){
					compositePageChoicePugin.getTfPathFileModelLettre().setEnabled(true);
					compositePageChoicePugin.getBtnPathFileModelLettre().setEnabled(true);
					compositePageChoicePugin.getBtCheckBoxSave().setEnabled(true);
					flagButtonNextPagePlugin = false;
					updateElementEnsable();
					compositePageChoicePugin.getTfPathFileModelLettre().setText("");
					wizardPageChoicePlugin.setPageComplete(validatePagePlugin());
				}else{
					parametrePublicPostage.setSavePublicpostagePagePlugin(false);
					reinitialMessageWizardPage(wizardPageChoicePlugin);
					flagFileModelLettre = true;
					flagNameParamPublicpostage = true;
					Control[] controls = compositePageChoicePugin.getCompositeListPlugin().getChildren();
					for (Control control : controls) {
						control.setEnabled(true);
					}
					compositePageChoicePugin.getTfPathFileModelLettre().setEnabled(false);
					compositePageChoicePugin.getBtnPathFileModelLettre().setEnabled(false);
					
					flagButtonNextPagePlugin = true;
					
					compositePageChoicePugin.getTextNameParamPublicpostage().setEnabled(false);
					compositePageChoicePugin.getBtCheckBoxSave().setSelection(false);
					compositePageChoicePugin.getBtCheckBoxSave().setEnabled(false);
					
					wizardPageChoicePlugin.setPageComplete(validatePagePlugin());
				}
			}
			
		});
	}
	
	/**
	 * ajouter listener pour Button CheckBox modifier chemin de 
	 * stockage publipostage de CompositePageChoiceDataLetter
	 */
	public void addListenerButtonCheckBoxPublipostage(){
		
		compositePageChoiceDataLetter.getButtonCheckBoxPublipostage().addListener(SWT.Selection,new Listener(){
			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub
				isSelectBtnCheckBoxPublipostage = compositePageChoiceDataLetter.getButtonCheckBoxPublipostage().getSelection();
				parametrePublicPostage.setChoixSavePublicPostage(isSelectBtnCheckBoxPublipostage);
				if(isSelectBtnCheckBoxPublipostage){
					compositePageChoiceDataLetter.getTfPathSavePublipostage().setEnabled(true);
					compositePageChoiceDataLetter.getBtnPathSavePublipostage().setEnabled(true);
				}else{
					compositePageChoiceDataLetter.getTfPathSavePublipostage().setText("");
					compositePageChoiceDataLetter.getTfPathSavePublipostage().setEnabled(false);
					compositePageChoiceDataLetter.getBtnPathSavePublipostage().setEnabled(false);
					
					String PathDefautFileSavePublipostage = parametrePublicPostage.getPathDefautFileSavePublipostage();
					parametrePublicPostage.setPathFileSavePublicPostage(PathDefautFileSavePublipostage);
				}				
			}
		});
	}
	
	/**
	 * ajouter listener pour Button CheckBox de CompositePageChoiceDataLetter
	 */
	public void addListenerButtonCheckBoxMotCle(){
		
		compositePageChoiceDataLetter.getButtonCheckBoxMotCle().addListener(SWT.Selection,new Listener(){
			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub
				isSelectBtnCheckBoxMotCle = compositePageChoiceDataLetter.getButtonCheckBoxMotCle().getSelection();
				parametrePublicPostage.setChoixMotCle(isSelectBtnCheckBoxMotCle);
				
				if(isSelectBtnCheckBoxMotCle){
					compositePageChoiceDataLetter.getTfPathFileMotCle().setEnabled(true);
					compositePageChoiceDataLetter.getBtnPathFileMotCle().setEnabled(true);
					wizardPageChoiceDataLettre.setPageComplete(isExistFileExtraction && isExistFileMotCle);
					
				}else{
					
					if(isExistFileExtraction){
						wizardPageChoiceDataLettre.setErrorMessage(null);
					}
					compositePageChoiceDataLetter.getTfPathFileMotCle().setText("");
					compositePageChoiceDataLetter.getTfPathFileMotCle().setEnabled(false);
					compositePageChoiceDataLetter.getBtnPathFileMotCle().setEnabled(false);
					
					parametrePublicPostage.setPathFileMotCle(Const.C_FICHIER_LISTE_ATTRIBUTE_LETTRE);
					
					wizardPageChoiceDataLettre.setPageComplete(true);
				}				
			}
		});
	}
	
	/**
	 * ajouter les buttons pour page plugin
	 */
	public void addButtonPagePlugin(Map<String, List<String>> mapButtonPlugin){
		
		for (String nomPlugin : mapButtonPlugin.keySet()) {
//			String namePlugin = nomPlugin;
			final Button buttonPlugin = new Button(compositePageChoicePugin.getCompositeListPlugin(),SWT.RADIO |SWT.LEFT);
			buttonPlugin.setText(nomPlugin);
			buttonPlugin.addListener(SWT.Selection, new Listener(){
				@Override
				public void handleEvent(Event event) {
					// TODO Auto-generated method stub
					compositePageChoicePugin.getTextNameParamPublicpostage().setEnabled(false);
					compositePageChoicePugin.getBtCheckBoxSave().setEnabled(false);
					fonctionGeneral.setNamePlugin(buttonPlugin.getText());
					parametrePublicPostage.setNamePlugin(buttonPlugin.getText());
					parametrePublicPostage.setSavePublicpostagePagePlugin(false);
//					parametrePublicPostage.setChoixSavePublicPostage(false);
//					parametrePublicPostage.setNameParamPublicPostage("");
					compositePageChoicePugin.getTfPathFileModelLettre().setText("");
					compositePageChoicePugin.getTfPathFileModelLettre().setEnabled(false);
					compositePageChoicePugin.getBtnPathFileModelLettre().setEnabled(false);
					compositePageChoicePugin.getCheckBoxModelLettre().setSelection(false);
					flagButtonNextPagePlugin = true;
					wizardPageChoicePlugin.setPageComplete(validatePagePlugin());
				}
				
			});
		}
		compositePageChoicePugin.getCompositeListPlugin().layout();
		
	}
	/**
	 * add listener pour BtCheckBoxPrint 
	 */
//	public void addListenerBtCheckBoxPrint(final Button button){
//		button.addListener(SWT.Selection,new Listener(){
//			@Override
//			public void handleEvent(Event event) {
//				// TODO Auto-generated method stub
//				boolean isSelect = button.getSelection();
//				fonctionGeneral.setPrint(isSelect);
//			}
//			
//		});
//	}
	
	/**
	 * add listener pour BtCheckBoxPrint 
	 */
//	public void addListenerBtCheckBoxShow(final Button button){
//		button.addListener(SWT.Selection,new Listener(){
//			@Override
//			public void handleEvent(Event event) {
//				// TODO Auto-generated method stub
//				boolean isSelect = button.getSelection();
//				fonctionGeneral.setShowPublipostage(isSelect);
//			}
//			
//		});
//	}
	public void initialEtatElementWizardPageDataLettre(CompositePageChoiceDataLetter composite,boolean flag){
		composite.getBtnPathFileMotCle().setEnabled(flag);
		composite.getTfPathFileMotCle().setEnabled(flag);
		composite.getBtnPathSavePublipostage().setEnabled(flag);
		composite.getTfPathSavePublipostage().setEnabled(flag);
//		compositePageChoiceDataLetter.getBtShowOO().setSelection(fonctionGeneral.isShowPublipostage());
//		compositePageChoiceDataLetter.getBtOptionPrint().setSelection(fonctionGeneral.isPrint());
		
		
		/*******new*******/
		CCombo comboParamPublicPostage = composite.getComboChoixParamPublipostage();
		Button btDeleteParamPublicPostage = composite.getBtDeleteParamPublipostage();
		
		String[] arrayChoixParamPublicPostage = fonctionGeneral.arrayPathFileParamModelLettreMS(
				Const.PATH_FOLDER_MODEL_LETTRE_DOSSIER+"/"+Const.FOLDER_PARAM_LETTRE_WO,
				ConstModelLettre.TYPE_FILE_XML);
		
		if(arrayChoixParamPublicPostage.length == 0){
			comboParamPublicPostage.setEnabled(false);
			btDeleteParamPublicPostage.setEnabled(false);
		}else{
			comboParamPublicPostage.setItems(arrayChoixParamPublicPostage);
			comboParamPublicPostage.setEnabled(true);
			btDeleteParamPublicPostage.setEnabled(true);
		}
		
		CCombo comboChoixFileSeparateur = compositePageChoiceDataLetter.getComboChoixFileSeparateur();
		comboChoixFileSeparateur.setItems(ConstModelLettre.VALUES_COMBO_TYPE_SEPARATEUR);

	}
	/**
	 * ajouter les listeners pour les elements 
	 * pour pageChoiceLettre
	 */
	public void initialActionListenerPageLettre(){
		/* button de parcourir pour choisir file d"extraction */
		
		initialEtatElementWizardPageDataLettre(compositePageChoiceDataLetter,false);
		
		CCombo comboParamPublicPostage = compositePageChoiceDataLetter.getComboChoixParamPublipostage();
		addListenerComboParamPublicPostage(comboParamPublicPostage);
		
		Button btDeleteParamPublicpostage = compositePageChoiceDataLetter.getBtDeleteParamPublipostage();
		addListenerBtDeletePublicpostage(btDeleteParamPublicpostage,compositePageChoiceDataLetter,comboParamPublicPostage);
		
		addListenerBtParcourirFileExtraction(compositePageChoiceDataLetter.getBtnPathFileExtraction(),
				compositePageChoiceDataLetter.getTfPathFileExtraction());
		
	
		addListenerBtParcourirFileMotCle(compositePageChoiceDataLetter.getBtnPathFileMotCle(),
				compositePageChoiceDataLetter.getTfPathFileMotCle());
		
		addListenerBtParcourirSavePublicpostage(compositePageChoiceDataLetter.getBtnPathSavePublipostage(),
				compositePageChoiceDataLetter.getTfPathSavePublipostage());
		
		/* champ de text pour path de la file d"extraction */
		compositePageChoiceDataLetter.getTfPathFileExtraction().addListener(SWT.Modify,listenervalidPageDataLetter);
		
		/* champ de text pour path de la file d"extraction */
		compositePageChoiceDataLetter.getTfPathFileMotCle().addListener(SWT.Modify,listenervalidPageDataLetter);
		
		compositePageChoiceDataLetter.getTfPathSavePublipostage().addListener(SWT.Modify, listenervalidPageDataLetter);
		
		addListenerButtonCheckBoxMotCle();
		
		addListenerButtonCheckBoxPublipostage();
		
		CCombo comboChoixFileSeparateur = compositePageChoiceDataLetter.getComboChoixFileSeparateur();
		comboChoixFileSeparateur.select(0);
		parametrePublicPostage.setIndexChoixCcomboSeparateur(0);
		parametrePublicPostage.setValueSeparateur(ConstModelLettre.VALUES_COMBO_TYPE_SEPARATEUR[0]);
		addListenerComboSeparateur(comboChoixFileSeparateur);
		
		Text textSeparateur = compositePageChoiceDataLetter.getTextSeparatuer();
		textSeparateur.setEnabled(false);
		textSeparateur.addListener(SWT.Modify,listenervalidPageDataLetter);
		
		
		Button btChoixSeparateur = compositePageChoiceDataLetter.getBtOptionFileSeparateur();
		addListenerbtChoixSeparateur(btChoixSeparateur,comboChoixFileSeparateur,textSeparateur);
		
//		addListenerBtCheckBoxPrint(compositePageChoiceDataLetter.getBtOptionPrint());
		
//		addListenerBtCheckBoxShow(compositePageChoiceDataLetter.getBtShowOO());
		
		/* pour valider page Data lettre ==> verouille button finish 
		 * rappler une fois pour initialisation
		 * deuxième fois rappler pour fonction de validatePageLettre() pour 
		 * valider les data de page
		 */
		wizardPageChoiceDataLettre.setPageComplete(validatePageDataLettre());
	}
	public void addListenerBtDeletePublicpostage(final Button btDelete,final Composite composite,final CCombo combo){
		
		btDelete.addListener(SWT.Selection,new Listener(){

			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub

				if (textChoixCcombo != null && textChoixCcombo.length() != 0) {
					boolean flag = MessageDialog.openConfirm(composite
							.getShell(), ConstModelLettre.MESSAGE_DIALOG_TITLE,
							ConstModelLettre.MESSAGE_DIALOG_CONTENT);

					if (flag) {
						String deletePathParamePublicpostage = fonctionGeneral
								.getMapFileParamPublicPostage().get(
										textChoixCcombo);
						new File(deletePathParamePublicpostage).delete();
						fonctionGeneral.getMapFileParamPublicPostage().remove(
								textChoixCcombo);

						String[] elementCcombo = fonctionGeneral
								.arrayPathFileParamModelLettreMS(
										Const.PATH_FOLDER_MODEL_LETTRE_DOSSIER
												+ "/"
												+ Const.FOLDER_PARAM_LETTRE_WO,
										ConstModelLettre.TYPE_FILE_XML);
						if (elementCcombo.length != 0) {
							combo.setItems(elementCcombo);
						} else {
							combo.setEnabled(false);
							btDelete.setEnabled(false);
						}
						combo.select(-1);
						afterActionBtDelete(compositePageChoiceDataLetter,
								parametrePublicPostage, fonctionGeneral);

						wizardPageChoiceDataLettre
								.setPageComplete(validatePageDataLettre());

					}
				} else {
					MessageDialog.openInformation(composite.getShell(),
							ConstModelLettre.MESSAGE_DIALOG_INFOS,
							ConstModelLettre.MESSAGE_DIALOG_INFOS_CONTENT);
				}
				
				
			}
			
		});
		
	}
			
	public void addListenerbtChoixSeparateur(final Button button,final CCombo combo,final Text text){
		button.addListener(SWT.Selection, new Listener(){

			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub
				boolean isChoixSeparateur = button.getSelection();
				String valueSeparateur = "";
				parametrePublicPostage.setChoixSeparateur(isChoixSeparateur);
				
				boolean flag;
				if(isChoixSeparateur){
					text.setEnabled(true);
					combo.setEnabled(false);
					flag = false;				
				}else{
					text.setEnabled(false);
					combo.setEnabled(true);
					combo.select(0);
					valueSeparateur = combo.getText();
					parametrePublicPostage.setValueSeparateur(valueSeparateur);
					text.setText("");
					flag = validatePageDataLettre();
				}
//				
				
				wizardPageChoiceDataLettre.setPageComplete(flag);
				
			}
		});
	}
	public void addListenerComboSeparateur(final CCombo combo) {
		combo.addListener(SWT.Selection, new Listener(){
			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub
//				valueSeparateur = combo.getText();
				int indexChoixCcomboSeparateur = combo.getSelectionIndex();
				parametrePublicPostage.setIndexChoixCcomboSeparateur(indexChoixCcomboSeparateur);
				parametrePublicPostage.setValueSeparateur(combo.getText());
			}		
		});
	}
	public void addListenerComboParamPublicPostage(final CCombo combo){
		
		combo.addListener(SWT.Selection, new Listener(){
			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub
				textChoixCcombo = combo.getText();
				boolean flag = false;
				if(!textChoixCcombo.equals(ConstModelLettre.CHOIX_DEFAUT_CCOMB_PARAM_PUBLIPOSTAGE)){
					flag = true;
					int choixIndexCcombo = combo.getSelectionIndex();
					String pathFileParamPublicPostage = fonctionGeneral.getMapFileParamPublicPostage().
													get(textChoixCcombo);
					
//					parametrePublicPostage.setNameParamPublicPostage(textChoixCcombo);
//					parametrePublicPostage.setPathParamPublicPostage(pathFileParamPublicPostage);
//					parametrePublicPostage.setChoixIndexCcombo(choixIndexCcombo);
					isChoixCcomboParamPublicPostage = true;
					changeValueCompositPageChoixDataLetter(pathFileParamPublicPostage,choixIndexCcombo);
				}else{
					afterActionBtDelete(compositePageChoiceDataLetter, parametrePublicPostage, fonctionGeneral);
				}
				compositePageChoiceDataLetter.getBtDeleteParamPublipostage().setEnabled(flag);
				
			}
			
		});
	}
	public void changeValueCompositPageChoixDataLetter(String pathFileParamPublicPostage,int choixIndexCcombo){
		
		ParametrePublicPostage parametrePublicPostage = fonctionGeneral.readObjectCastor(pathFileParamPublicPostage);
		this.parametrePublicPostage = parametrePublicPostage;
		
		String pathFileExtraction = parametrePublicPostage.getPathFileExtraction();
		String pathFileMotCle = parametrePublicPostage.getPathFileMotCle();
		boolean isChoixMotCle = parametrePublicPostage.isChoixMotCle();
		
		compositePageChoiceDataLetter.getTfPathFileExtraction().setText(pathFileExtraction);
		
		
		if(isChoixMotCle){
			compositePageChoiceDataLetter.getTfPathFileMotCle().setEnabled(true);
			compositePageChoiceDataLetter.getTfPathFileMotCle().setText(pathFileMotCle);
			compositePageChoiceDataLetter.getButtonCheckBoxMotCle().setSelection(true);
			compositePageChoiceDataLetter.getBtnPathFileMotCle().setEnabled(true);
		}else{
			compositePageChoiceDataLetter.getTfPathFileMotCle().setEnabled(false);
			compositePageChoiceDataLetter.getButtonCheckBoxMotCle().setSelection(false);
			compositePageChoiceDataLetter.getBtnPathFileMotCle().setEnabled(false);
		}
		
		String pathFileSavePublicPostage = parametrePublicPostage.getPathFileSavePublicPostage();
		boolean isChoixSavePublicPostage = parametrePublicPostage.isChoixSavePublicPostage();
		if(isChoixSavePublicPostage){
			compositePageChoiceDataLetter.getTfPathSavePublipostage().setEnabled(true);
			compositePageChoiceDataLetter.getTfPathSavePublipostage().setText(pathFileSavePublicPostage);
			compositePageChoiceDataLetter.getButtonCheckBoxPublipostage().setSelection(true);
			compositePageChoiceDataLetter.getBtnPathSavePublipostage().setEnabled(true);
		}else{
			compositePageChoiceDataLetter.getTfPathSavePublipostage().setEnabled(false);
			compositePageChoiceDataLetter.getButtonCheckBoxPublipostage().setSelection(false);
			compositePageChoiceDataLetter.getBtnPathSavePublipostage().setEnabled(false);
		}
		
//		int choixIndexCcombo = parametrePublicPostage.getChoixIndexCcombo();
		compositePageChoiceDataLetter.getComboChoixParamPublipostage().select(choixIndexCcombo);
		
		String valueSeparateur = parametrePublicPostage.getValueSeparateur();
		boolean isChoixSeparateur = parametrePublicPostage.isChoixSeparateur();
		int indexChoixCcomboSeparateur = parametrePublicPostage.getIndexChoixCcomboSeparateur();
		
		if(isChoixSeparateur){
			compositePageChoiceDataLetter.getTextSeparatuer().setEnabled(true);
			compositePageChoiceDataLetter.getTextSeparatuer().setText(valueSeparateur);
			compositePageChoiceDataLetter.getBtOptionFileSeparateur().setSelection(true);
			compositePageChoiceDataLetter.getComboChoixFileSeparateur().setEnabled(false);
		}else{
			compositePageChoiceDataLetter.getTextSeparatuer().setEnabled(false);
			compositePageChoiceDataLetter.getComboChoixFileSeparateur().select(indexChoixCcomboSeparateur);
			compositePageChoiceDataLetter.getBtOptionFileSeparateur().setSelection(false);
			compositePageChoiceDataLetter.getComboChoixFileSeparateur().setEnabled(true);
		}
	}

	public boolean validatePageDataLettre(){
		
		reinitialMessageWizardPage(wizardPageChoiceDataLettre);
		
		boolean flagValidate = false;
		String valueSeparateur = "";
		
		pathFileExtraction = compositePageChoiceDataLetter.getTfPathFileExtraction().getText();
		pathFileMotCle = compositePageChoiceDataLetter.getTfPathFileMotCle().getText();
		pathSavePublipostage = compositePageChoiceDataLetter.getTfPathSavePublipostage().getText();
	    
		fonctionGeneral.setPathFileMotCle(Const.C_FICHIER_LISTE_ATTRIBUTE_LETTRE);
		/**
		 * verifier file d'extraction
		 */
		if(pathFileExtraction!=null && !pathFileExtraction.equals("")){
			isExistFileExtraction = checkFileExist(pathFileExtraction,wizardPageChoiceDataLettre);
			flagValidate = isExistFileExtraction;
			if(isExistFileExtraction){
				fonctionGeneral.setPathFileDataExtraction(pathFileExtraction);
				
				parametrePublicPostage.setPathFileExtraction(pathFileExtraction);
				
				isExistFileMotCle = fonctionGeneral.verityFileExtractionAndModelLettre(
						wizardPageChoiceDataLettre,parametrePublicPostage.getPathFileMotCle(),
						parametrePublicPostage.getValueSeparateur());
			}
		}
		if(isSelectBtnCheckBoxMotCle){			
			/**
			 * verifier file de mot de Clé
			 */
			if(pathFileMotCle!=null && !pathFileMotCle.equals("")){
				isExistFileMotCle = checkFileExist(pathFileMotCle,wizardPageChoiceDataLettre);
				if(isExistFileMotCle){
					fonctionGeneral.setPathFileMotCle(pathFileMotCle);
					parametrePublicPostage.setPathFileMotCle(pathFileMotCle);
					
					isExistFileMotCle = fonctionGeneral.verityFileExtractionAndModelLettre(
							wizardPageChoiceDataLettre,parametrePublicPostage.getPathFileMotCle(),
							parametrePublicPostage.getValueSeparateur());
//					if(!isExistFileModel){
//						wizardPageChoiceDataLettre.setMessage(
//								ConstModelLettre.MESSAGE_MODEL_LETTER_MOT_CLE, DialogPage.ERROR);
//					}else{
////						wizardPageChoiceDataLettre.setmessage(null);
////						wizardPageChoiceDataLettre.setErrorMessage(null);
//						wizardPageChoiceDataLettre.setDescription(
//								ConstModelLettre.DESCRIPTION_PAGE_CHOICE_DATA_LETTER);
//					}
				
				}
			}
			flagValidate = isExistFileMotCle && isExistFileExtraction;
		}
		if(isSelectBtnCheckBoxPublipostage){
			boolean isExistFolder = false;
			if(pathSavePublipostage!=null && !pathSavePublipostage.equals("")){
				isExistFolder = checkFolderExist(pathSavePublipostage, wizardPageChoiceDataLettre);
				if(isExistFolder){
					fonctionGeneral.setPathSavePublipostage(pathSavePublipostage);
					parametrePublicPostage.setPathFileSavePublicPostage(pathSavePublipostage);
					parametrePublicPostage.setChoixSavePublicPostage(true);
				}
			}
			flagValidate = flagValidate && isExistFolder;
		}

		if(parametrePublicPostage.isChoixSeparateur()){
			valueSeparateur = compositePageChoiceDataLetter.getTextSeparatuer().getText();
			if(valueSeparateur.trim().length() != 0){
				flagSeparateur = true;
				parametrePublicPostage.setValueSeparateur(valueSeparateur);
			}else{
				flagSeparateur = false;
			}
			flagValidate = flagValidate && flagSeparateur;
		}
		
//		if(flagValidate){
//			parametrePublicPostage.setPathFileExtraction(pathFileExtraction);
////			parametrePublicPostage.setPathFileMotCle(pathFileMotCle);
//			
//			
//		}

		return flagValidate;
		//return ;
	}
	public void setChoixBtRadioNamePlugin(Composite composite,String name){
		Control[] controls =  composite.getChildren();
		for (Control control : controls) {
			Button button = (Button)control;
			String nameButton = button.getText();
			if(nameButton.equals(name)){
				button.setSelection(true);
			}
		}
	}
	/**
	 * apres qu'on supprime une parametre,cette fonction
	 * est pour modifier Composite et objet de ParametrePublicPostage
	 */
	public void afterActionBtDelete(CompositePageChoiceDataLetter compositePageChoiceDataLetter,
			ParametrePublicPostage parametrePublicPostage,FonctionGeneral fonctionGeneral){
		
		boolean flagFileExtration = false;
		Text tfPathFileExtraction = compositePageChoiceDataLetter.getTfPathFileExtraction();
		Button BtPathFileExtraction = compositePageChoiceDataLetter.getBtnPathFileExtraction();
		tfPathFileExtraction.setEnabled(!flagFileExtration);
		tfPathFileExtraction.setText("");
		BtPathFileExtraction.setEnabled(!flagFileExtration);
		parametrePublicPostage.setPathFileExtraction("");
		
		boolean flagMotCle = false;
		Text tfPathFileMotCle = compositePageChoiceDataLetter.getTfPathFileMotCle();
		Button btPathFileMotCle = compositePageChoiceDataLetter.getBtnPathFileMotCle();
		Button btCheckBoxMotCle = compositePageChoiceDataLetter.getButtonCheckBoxMotCle();
		tfPathFileMotCle.setEnabled(flagMotCle);
		btPathFileMotCle.setEnabled(flagMotCle);
		btCheckBoxMotCle.setSelection(flagMotCle);
		tfPathFileMotCle.setText("");
		parametrePublicPostage.setPathFileMotCle(Const.C_FICHIER_LISTE_ATTRIBUTE_LETTRE);
		parametrePublicPostage.setChoixMotCle(flagMotCle);
		
		boolean flagSavePathPubliposateg = false;
		Text tfPathSavePublipostage = compositePageChoiceDataLetter.getTfPathSavePublipostage();
		Button btPathSavePublipostage = compositePageChoiceDataLetter.getBtnPathSavePublipostage();
		Button btCheckBoxPublipostage = compositePageChoiceDataLetter.getButtonCheckBoxPublipostage();
		tfPathSavePublipostage.setEnabled(flagSavePathPubliposateg);
		tfPathSavePublipostage.setText("");
		btPathSavePublipostage.setEnabled(flagSavePathPubliposateg);
		btCheckBoxPublipostage.setEnabled(flagSavePathPubliposateg);
		parametrePublicPostage.setPathFileSavePublicPostage("");
		fonctionGeneral.setPathSavePublipostage(parametrePublicPostage.getPathDefautFileSavePublipostage());
		
		boolean flagSaparateur = false;
		CCombo comboSeparateur = compositePageChoiceDataLetter.getComboChoixFileSeparateur();
		Button btOptionFileSeparateur = compositePageChoiceDataLetter.getBtOptionFileSeparateur();
		Text textSeparatuer = compositePageChoiceDataLetter.getTextSeparatuer(); 
		comboSeparateur.setEnabled(!flagSaparateur);
		comboSeparateur.select(0);
		btOptionFileSeparateur.setSelection(flagSaparateur);
		textSeparatuer.setEnabled(flagSaparateur);
		textSeparatuer.setText("");
		parametrePublicPostage.setChoixSeparateur(flagSaparateur);
		parametrePublicPostage.setValueSeparateur(";");
		
		parametrePublicPostage.setNamePlugin("");
		parametrePublicPostage.setNameParamPublicPostage("");
		parametrePublicPostage.setSavePublicpostage(false);
		parametrePublicPostage.setSavePublicpostagePagePlugin(false);
		
		parametrePublicPostage.setChoixModelLettre(false);
		parametrePublicPostage.setPathFileModelLettre("");
		
		isChoixCcomboParamPublicPostage = false;
		textChoixCcombo = "";
	}
	
	public CompositePageChoiceDataLetter getCompositePageChoiceDataLetter() {
		return compositePageChoiceDataLetter;
	}

	public void setCompositePageChoiceDataLetter(
			CompositePageChoiceDataLetter compositePageChoiceDataLetter) {
		this.compositePageChoiceDataLetter = compositePageChoiceDataLetter;
	}

	public void setWizardPageChoiceDataLettre(
			WizardPageChoiceDataLettre wizardPageChoiceDataLettre) {
		this.wizardPageChoiceDataLettre = wizardPageChoiceDataLettre;
	}

	public Shell getShell() {
		return shell;
	}

	public void setShell(Shell shell) {
		this.shell = shell;
	}

	public void setWizardPageChoicePlugin(
			WizardPageChoicePlugin wizardPageChoicePlugin) {
		this.wizardPageChoicePlugin = wizardPageChoicePlugin;
	}

	public CompositePageChoicePugin getCompositePageChoicePugin() {
		return compositePageChoicePugin;
	}

	public void setCompositePageChoicePugin(
			CompositePageChoicePugin compositePageChoicePugin) {
		this.compositePageChoicePugin = compositePageChoicePugin;
	}
	public FonctionGeneral getFonctionGeneral() {
		return fonctionGeneral;
	}
	public void setFonctionGeneral(FonctionGeneral fonctionGeneral) {
		this.fonctionGeneral = fonctionGeneral;		
	}
	public String getPathFileExtraction() {
		return pathFileExtraction;
	}
	public String getPathFileModelLettre() {
		return pathFileModelLettre;
	}

	public WizardPageChoiceModelLettre getWizardPageChoiceModelLettre() {
		return wizardPageChoiceModelLettre;
	}

	public void setWizardPageChoiceModelLettre(
			WizardPageChoiceModelLettre wizardPageChoiceModelLettre) {
		this.wizardPageChoiceModelLettre = wizardPageChoiceModelLettre;
	}

	public CompositePageChoiceModelLetter getCompositePageChoiceModelLetter() {
		return compositePageChoiceModelLetter;
	}

	public void setCompositePageChoiceModelLetter(
			CompositePageChoiceModelLetter compositePageChoiceModelLetter) {
		this.compositePageChoiceModelLetter = compositePageChoiceModelLetter;
	}
//	public String getTypeOffice() {
//		return typeOffice;
//	}
//	public void setTypeOffice(String typeOffice) {
//		this.typeOffice = typeOffice;
//	}
	public boolean isFlagPageModelLettre() {
		return flagPageModelLettre;
	}
	public void setFlagPageModelLettre(boolean flagPageModelLettre) {
		this.flagPageModelLettre = flagPageModelLettre;
	}
	public WizardPageChoiceDataLettre getWizardPageChoiceDataLettre() {
		return wizardPageChoiceDataLettre;
	}
	public WizardPageChoicePlugin getWizardPageChoicePlugin() {
		return wizardPageChoicePlugin;
	}
	public String getPathFileMotCle() {
		return pathFileMotCle;
	}
	public void setPathFileMotCle(String pathFileMotCle) {
		this.pathFileMotCle = pathFileMotCle;
	}
	public boolean isExistFileExtraction() {
		return isExistFileExtraction;
	}
	public void setExistFileExtraction(boolean isExistFileExtraction) {
		this.isExistFileExtraction = isExistFileExtraction;
	}
	public boolean isExistFileMotCle() {
		return isExistFileMotCle;
	}
	public void setExistFileMotCle(boolean isExistFileMotCle) {
		this.isExistFileMotCle = isExistFileMotCle;
	}
	public boolean isExistFileModel() {
		return isExistFileModel;
	}
	public void setExistFileModel(boolean isExistFileModel) {
		this.isExistFileModel = isExistFileModel;
	}
	public void setPathFileExtraction(String pathFileExtraction) {
		this.pathFileExtraction = pathFileExtraction;
	}
	public void setPathFileModelLettre(String pathFileModelLettre) {
		this.pathFileModelLettre = pathFileModelLettre;
	}
	public boolean isSelectBtnCheckBoxMotCle() {
		return isSelectBtnCheckBoxMotCle;
	}
	public void setSelectBtnCheckBoxMotCle(boolean isSelectBtnCheckBoxMotCle) {
		this.isSelectBtnCheckBoxMotCle = isSelectBtnCheckBoxMotCle;
	}
	public IgenerationModelLettre getIgenerationModelLettre() {
		return igenerationModelLettre;
	}
	public void setIgenerationModelLettre(
			IgenerationModelLettre igenerationModelLettre) {
		this.igenerationModelLettre = igenerationModelLettre;
	}
	public boolean isFlagSeparateur() {
		return flagSeparateur;
	}
	public void setFlagSeparateur(boolean flagSeparateur) {
		this.flagSeparateur = flagSeparateur;
	}
	public boolean isFlagFileModelLettre() {
		return flagFileModelLettre;
	}
	public void setFlagFileModelLettre(boolean flagFileModelLettre) {
		this.flagFileModelLettre = flagFileModelLettre;
	}
	public boolean isFlagNameParamPublicpostage() {
		return flagNameParamPublicpostage;
	}
	public void setFlagNameParamPublicpostage(boolean flagNameParamPublicpostage) {
		this.flagNameParamPublicpostage = flagNameParamPublicpostage;
	}
	public ParametrePublicPostage getParametrePublicPostage() {
		return parametrePublicPostage;
	}
	public void setParametrePublicPostage(
			ParametrePublicPostage parametrePublicPostage) {
		this.parametrePublicPostage = parametrePublicPostage;
	}
	public boolean isChoixCcomboParamPublicPostage() {
		return isChoixCcomboParamPublicPostage;
	}
	public void setChoixCcomboParamPublicPostage(
			boolean isChoixCcomboParamPublicPostage) {
		this.isChoixCcomboParamPublicPostage = isChoixCcomboParamPublicPostage;
	}
	public String getNewNameParamPublicPostage() {
		return newNameParamPublicPostage;
	}
	public void setNewNameParamPublicPostage(String newNameParamPublicPostage) {
		this.newNameParamPublicPostage = newNameParamPublicPostage;
	}
	public String getTextChoixCcombo() {
		return textChoixCcombo;
	}
	public void setTextChoixCcombo(String textChoixCcombo) {
		this.textChoixCcombo = textChoixCcombo;
	}
	public boolean isFlagPageChoixPlugin() {
		return flagPageChoixPlugin;
	}
	public void setFlagPageChoixPlugin(boolean flagPageChoixPlugin) {
		this.flagPageChoixPlugin = flagPageChoixPlugin;
	}


	

}

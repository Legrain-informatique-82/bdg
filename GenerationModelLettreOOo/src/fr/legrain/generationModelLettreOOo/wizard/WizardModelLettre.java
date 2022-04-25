package fr.legrain.generationModelLettreOOo.wizard;

import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Button;

import fr.legrain.generationModelLettreOOo.divers.ConstModelLettre;
import fr.legrain.generationModelLettreOOo.divers.FonctionGeneral;
import fr.legrain.generationModelLettreOOo.divers.ParametrePublicPostage;

public class WizardModelLettre extends Wizard {

	private WizardPageChoiceDataLettre wizardPageChoiceDataLettre;
	private WizardPageChoicePlugin wizardPageChoicePlugin;
	private WizardPageChoiceModelLettre wizardPageChoiceModelLettre;
	
	private WizardController wizardController;
	
//	public WizardModelLettre(FonctionGeneral fonctionGeneral,String typeOffice,String pathOffice,
//			String portOffice) {
	public WizardModelLettre(FonctionGeneral fonctionGeneral,
							ParametrePublicPostage parametrePublicPostage) {
		super();
		
		wizardController = new WizardController();
		
		wizardController.setFonctionGeneral(fonctionGeneral);
		wizardController.setParametrePublicPostage(parametrePublicPostage);
			
		wizardPageChoiceDataLettre = new WizardPageChoiceDataLettre(ConstModelLettre.NAME_PAGE_CHOICE_DATA_LETTER);
		wizardController.setWizardPageChoiceDataLettre(wizardPageChoiceDataLettre);
		wizardPageChoicePlugin = new WizardPageChoicePlugin(ConstModelLettre.NAME_PAGE_CHOICE_PLUGIN);
		wizardController.setWizardPageChoicePlugin(wizardPageChoicePlugin);
		wizardPageChoiceModelLettre = new WizardPageChoiceModelLettre(ConstModelLettre.NAME_PAGE_CHOICE_MODEL_LETTER);
		wizardController.setWizardPageChoiceModelLettre(wizardPageChoiceModelLettre);
		setWindowTitle(ConstModelLettre.NAME_WIZARD);
		
		addPage(wizardPageChoiceDataLettre);
		addPage(wizardPageChoicePlugin);
		addPage(wizardPageChoiceModelLettre);
		
	}


	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		//return false;
		return true;
	}
	
	@Override
	public boolean canFinish() {
		boolean flagPage = false;
		boolean isExistFile = false;
		boolean isTypeFile = false;
		boolean isNamePublicPostage = false;
		
		if(getContainer().getCurrentPage().getName().equals(ConstModelLettre.NAME_PAGE_CHOICE_DATA_LETTER)) {
			return flagPage;
		}
		if(getContainer().getCurrentPage().getName().equals(ConstModelLettre.NAME_PAGE_CHOICE_PLUGIN)) {
			
			ParametrePublicPostage parametrePublicPostage = wizardController.getParametrePublicPostage();
			boolean isChoixCcomboParamPublicPostage = wizardController.isChoixCcomboParamPublicPostage();
			boolean isChoixModelLettre = parametrePublicPostage.isChoixModelLettre();
			String pathFileModelLettre = parametrePublicPostage.getPathFileModelLettre();
			boolean isFlagPathFileModelLettre = false; 
			if(pathFileModelLettre != null){
				if(pathFileModelLettre.trim().length()!=0){
					isFlagPathFileModelLettre = true;
				}
			}			
			if(isChoixCcomboParamPublicPostage){
				if(isChoixModelLettre && isFlagPathFileModelLettre){
					flagPage = true;
				}else{
					flagPage = false;
				}
			}else{
				
				boolean isChoixSavePublicPostage = parametrePublicPostage.isSavePublicpostage();
				if(isChoixModelLettre){
					flagPage = wizardController.isFlagFileModelLettre();
				}
				if(isChoixSavePublicPostage){
					flagPage = wizardController.isFlagNameParamPublicpostage();
				}
			}
						
//			String pathFileModelLettre = wizardController.getPathFileModelLettre();
//			Button button = wizardController.getCompositePageChoicePugin().getCheckBoxModelLettre();
//			Button btChoixSave = wizardController.getCompositePageChoicePugin().getBtCheckBoxSave();
//			ParametrePublicPostage parametrePublicPostage = wizardController.getParametrePublicPostage();
//			if(wizardController.isSelectBtnCheckBoxMotCle()){
//				wizardController.getCompositePageChoicePugin().getCompositeListPlugin().setEnabled(true);
//				//wizardController.getCompositePageChoicePugin().getCheckBoxModelLettre().setSelection(true);
//				
//			}
//			if(button.getSelection()){
//				isExistFile = wizardController.checkFileExist(pathFileModelLettre,wizardPageChoicePlugin); 
//				isTypeFile = wizardController.checkFileTypeOffice(pathFileModelLettre, wizardPageChoicePlugin, isExistFile);
//				flagPage = isExistFile && isTypeFile;
//			}
//			if(parametrePublicPostage.isChoixSavePublicPostage()){
//			//if(btChoixSave.getSelection()){
//				String namePublicpostage = wizardController.getCompositePageChoicePugin().getTextNameParamPublicpostage().getText();
//				if(namePublicpostage.trim().length() != 0 && namePublicpostage == null){
//					flagPage = true;
//					parametrePublicPostage.setNameParamPublicPostage(namePublicpostage);
//				}else{
//					wizardPageChoicePlugin.setMessage(ConstModelLettre.MESSAGE_NAME_PUBLICPOSTAGE, DialogPage.ERROR);
//					flagPage = false;
//				}	
//			}

			return flagPage;
//			return false;
		}
		if(getContainer().getCurrentPage().getName().equals(ConstModelLettre.NAME_PAGE_CHOICE_MODEL_LETTER)) {

			return wizardController.isFlagPageModelLettre();
		}
		return true;
	}
	
	protected void backPressed(IModelWizardPage page){
		
	}
	
	protected void nextPressed(IModelWizardPage wizardPage){
		wizardPage.finishPage();
	}
	
	protected void finishPressed(IModelWizardPage wizardPage){
		 wizardPage.finishPage();
	}


	public WizardController getWizardController() {
		return wizardController;
	}


	public void setWizardController(WizardController wizardController) {
		this.wizardController = wizardController;
	}
	

}

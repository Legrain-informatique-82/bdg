package fr.legrain.generationModelLettreWS.wizard;

import java.util.List;
import java.util.Map;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import fr.legrain.generationModelLettreMS.divers.ConstModelLettre;
import fr.legrain.generationModelLettreMS.divers.FonctionGeneral;
import fr.legrain.generationModelLettreMS.divers.ParametrePublicPostage;

public class WizardPageChoiceDataLettre extends WizardPage implements IModelWizardPage {

//	private WizardPageChoiceDataLettre WizardPageChoiceDataLettre;
		
	protected WizardPageChoiceDataLettre(String pageName) {
		super(pageName);
		// TODO Auto-generated constructor stub
		setTitle(ConstModelLettre.TITLE_PAGE_CHOICE_DATA_LETTER);
		setDescription(ConstModelLettre.DESCRIPTION_PAGE_CHOICE_DATA_LETTER);
		
	}
	

	@Override
	public void finishPage() {
		// TODO Auto-generated method stub
//		try {
//			WizardController wizardController = getController();
//			Composite composite = wizardController.getCompositePageChoicePugin().getCompositeListPlugin();
//			wizardController.deleteButtonRadio(composite);
//			String typeOffice = wizardController.getFonctionGeneral().getTypeOffice();
//			wizardController.getFonctionGeneral().getInfosModelLetter(typeOffice);
//			Map<String, List<String>> buttonPlugin = wizardController.getFonctionGeneral().getMapModelLettre();
//			wizardController.addButtonPagePlugin(buttonPlugin);
//			wizardController.getWizardPageChoicePlugin().setPageComplete(false);
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
		WizardController wizardController = getController();
		FonctionGeneral fonctionGeneral = wizardController.getFonctionGeneral();
		CompositePageChoicePugin compositePageChoicePugin = wizardController.getCompositePageChoicePugin();
		Composite composite = compositePageChoicePugin.getCompositeListPlugin();
		ParametrePublicPostage parametrePublicPostage = wizardController.getParametrePublicPostage();
		
		wizardController.deleteButtonRadio(composite);
		String typeOffice = fonctionGeneral.getTypeOffice();
		fonctionGeneral.getInfosModelLetter(typeOffice);
		Map<String, List<String>> buttonPlugin = fonctionGeneral.getMapModelLettre();
		wizardController.addButtonPagePlugin(buttonPlugin);
		wizardController.getWizardPageChoicePlugin().setPageComplete(false);
		
		if(wizardController.isChoixCcomboParamPublicPostage()){
			String namePlugin = parametrePublicPostage.getNamePlugin();
			if(namePlugin.trim().length()!=0 && namePlugin!=null){
				wizardController.setChoixBtRadioNamePlugin(composite, namePlugin);
			}else{
				wizardController.updateElementEnsable();
			}
			
			boolean isChoixModelLettre = parametrePublicPostage.isChoixModelLettre();	
			Text textPathFileModelLettre = compositePageChoicePugin.getTfPathFileModelLettre();
			Button btCheckBoxModelLettre = compositePageChoicePugin.getCheckBoxModelLettre();
			if(isChoixModelLettre){
				String pathFileModelLettre = parametrePublicPostage.getPathFileModelLettre();
				textPathFileModelLettre.setEnabled(true);
				textPathFileModelLettre.setText(pathFileModelLettre);
				
				btCheckBoxModelLettre.setSelection(true);
				
			}else{
				textPathFileModelLettre.setEnabled(false);
				btCheckBoxModelLettre.setSelection(false);
			}
			
			String nameParamPublicPostage = parametrePublicPostage.getNameParamPublicPostage();
			Text textNameParamPublicpostage = compositePageChoicePugin.getTextNameParamPublicpostage();
			boolean flag = fonctionGeneral.checkValueString(nameParamPublicPostage);
			if(flag){
				textNameParamPublicpostage.setText(nameParamPublicPostage);
			}
			
			boolean isSavePublicpostagePagePlugin = parametrePublicPostage.isSavePublicpostagePagePlugin();
			
			
			Button btCheckBoxSave = compositePageChoicePugin.getBtCheckBoxSave();
			if(isSavePublicpostagePagePlugin){
//				textNameParamPublicpostage.setText(nameParamPublicpostage);
				btCheckBoxSave.setSelection(true);
				textNameParamPublicpostage.setEnabled(true);
				wizardController.getWizardPageChoicePlugin().setPageComplete(false);	
			}else{
				textNameParamPublicpostage.setEnabled(false);
				btCheckBoxSave.setSelection(false);
				
				wizardController.getWizardPageChoicePlugin().setPageComplete(true);
			}
			
		}

	}

	@Override
	public void saveToModel() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean validatePage() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createControl(Composite parent) {
		// TODO Auto-generated method stub
		CompositePageChoiceDataLetter control = new CompositePageChoiceDataLetter(parent,SWT.NULL);
		Shell shell = parent.getShell();
		getController().setCompositePageChoiceDataLetter(control);
		getController().setShell(shell);
		getController().initialActionListenerPageLettre();
		setControl(control);
		setPageComplete(getController().validatePageDataLettre());
	}
	/*
	 * get class controller
	 */
	public WizardController getController(){
		return ((WizardModelLettre)getWizard()).getWizardController();
	}
	
}

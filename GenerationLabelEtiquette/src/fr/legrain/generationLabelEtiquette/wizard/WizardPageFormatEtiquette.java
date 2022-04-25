package fr.legrain.generationLabelEtiquette.wizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import fr.legrain.generationLabelEtiquette.divers.ConstantModelLabels;
import fr.legrain.generationLabelEtiquette.divers.GenerationFileEtiquette;

public class WizardPageFormatEtiquette extends WizardPage implements IModelWizardPage {
//public class WizardPageFormatEtiquette extends Wizard {

	protected WizardPageFormatEtiquette(String pageName) {
		super(pageName);
		setTitle(ConstantModelLabels.TITLE_PAGE_FORMAT_ETIQUETTE);
		setTitle(ConstantModelLabels.DESCRIPTION_PAGE_FORMAT_ETIQUETTE);
	}

	@Override
	public void finishPage() {
		// TODO Auto-generated method stub
	}

	@Override
	public void saveToModel() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public boolean validatePage() {
		return false;
	}

	@Override
	public void createControl(Composite parent) {
		// TODO Auto-generated method stub
		CompositePageOptionFormatEtiquette composite = new CompositePageOptionFormatEtiquette(parent,SWT.NULL);
		Shell shell = parent.getShell();
		getController().setShell(shell);
		getController().setCompositePageOptionFormatEtiquette(composite);
		getController().initialActionListenerPageFormatEtiquette();
		setControl(composite);
//		setPageComplete(true);
		setPageComplete(false);
		
	}
	
	/*
	 * get class controller
	 */
	public WizardController getController(){
		return ((WizardModelLables)getWizard()).getWizardController();
	}
	
	@Override
	public void setVisible(boolean visible) {
		
		if(getController().getParam().isChangeStartingPage()) {
			((WizardPageModelLables)getWizard().getPage(ConstantModelLabels.NAME_PAGE_MODEL_LABLES)).finishPage();
			getController().initialCompsitePageOptionFormatEtiquette(getController().getParameterEtiquette());
		}
		
		super.setVisible(visible);
	}

}

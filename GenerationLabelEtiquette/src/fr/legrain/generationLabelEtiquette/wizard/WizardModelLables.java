package fr.legrain.generationLabelEtiquette.wizard;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Text;

import fr.legrain.generationLabelEtiquette.divers.ConstantModelLabels;
import fr.legrain.generationLabelEtiquette.divers.GenerationFileEtiquette;
import fr.legrain.generationLabelEtiquette.handlers.ParamWizardEtiquettes;

public class WizardModelLables extends Wizard {

	private WizardController wizardController;
	
	public WizardModelLables(GenerationFileEtiquette generationFileEtiquette) {
		this(generationFileEtiquette,null);
	}

	public WizardModelLables(GenerationFileEtiquette generationFileEtiquette, ParamWizardEtiquettes param) {
		super();
		wizardController = new WizardController();
		wizardController.setGenerationFileEtiquette(generationFileEtiquette);
		// TODO Auto-generated constructor stub
		WizardPageModelLables wizardPageModelLables = new WizardPageModelLables(
													   ConstantModelLabels.NAME_PAGE_MODEL_LABLES);
		
		WizardPageFormatEtiquette wizardPageFormatEtiquette = new WizardPageFormatEtiquette(
													   ConstantModelLabels.NAME_PAGE_FORMAT_ETIQUETTE);
		setWindowTitle(ConstantModelLabels.NAME_WIZARD_MODEL_LABLES);
		
		wizardController.setWizardPageModelLables(wizardPageModelLables);
		wizardController.setWizardPageFormatEtiquette(wizardPageFormatEtiquette);
		wizardController.setParam(param);
		
		addPage(wizardPageModelLables);
		addPage(wizardPageFormatEtiquette);
		
	}
	
	public void initParam(ParamWizardEtiquettes param) {
		wizardController.setParam(param);
	}
	
	public String[] listeParamEtiquette(String type) {
		return wizardController.listeNomParamEtiquette(type);
	}
	
	public String[] listeParamEtiquette(String[] ajoutDebut, String[] ajoutFin) {
		return wizardController.listeNomParamEtiquette(ajoutDebut, ajoutFin);
	}
	
	@Override
	public boolean performFinish() {
		return true;
	}
	
	@Override
	public boolean canFinish() {

		boolean flag = true;
		if(getContainer().getCurrentPage().getName().equals(ConstantModelLabels.NAME_PAGE_MODEL_LABLES)){
//			if(wizardController.getParam()!=null && wizardController.getParam().isModeIntegre()) {
//				flag = true;
//			} else {
				flag = false;
//			}
		}
		if(getContainer().getCurrentPage().getName().equals(ConstantModelLabels.NAME_PAGE_FORMAT_ETIQUETTE)){
					
			boolean flagChoixSaveEtiquette = wizardController.isChoixSaveEtiquette();
			if(flagChoixSaveEtiquette){
				flag = wizardController.isFlagTextEtiquette() && wizardController.isFlagNameEtiquette();
			}else{
				flag = wizardController.isFlagTextEtiquette();
			}
			
		}
		return flag;
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

	@Override
	public IWizardPage getStartingPage() {
		if(wizardController.getParam()!=null && wizardController.getParam().isChangeStartingPage())
			return getPage(ConstantModelLabels.NAME_PAGE_FORMAT_ETIQUETTE);
		else
			return super.getStartingPage();
	}

}

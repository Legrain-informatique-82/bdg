package fr.legrain.generationModelLettreWS.wizard;

import java.util.List;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import fr.legrain.generationModelLettreMS.divers.ConstModelLettre;

public class WizardPageChoicePlugin extends WizardPage implements IModelWizardPage {

	protected WizardPageChoicePlugin(String pageName) {
		super(pageName);
		// TODO Auto-generated constructor stub
		setTitle(ConstModelLettre.TITLE_PAGE_CHOICE_PLUGIN);
		setDescription(ConstModelLettre.DESCRIPTION_PAGE_CHOICE_PLUGIN);
	}

	@Override
	public void finishPage() {
		
		// TODO Auto-generated method stub
		try {
			WizardController wizardController = getController();
			Composite composite = wizardController.getCompositePageChoiceModelLetter().getCompositeListModelLetter();
			wizardController.deleteButtonRadio(composite);
			//String nomPlugin = wizardController.getFonctionGeneral().getNamePlugin();
			String nomPlugin = wizardController.getParametrePublicPostage().getNamePlugin();
			List<String> listPathModelLettre =  wizardController.getFonctionGeneral().
											getMapModelLettre().get(nomPlugin);
			
			wizardController.reinitialMessageWizardPage(wizardController.getWizardPageChoiceModelLettre());
			wizardController.initialActionListenerPageModelLetter(listPathModelLettre);
			wizardController.getWizardPageChoiceModelLettre().setPageComplete(false);
			
//			setPageComplete(getController().validatePagePlugin());
			setPageComplete(false);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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
		CompositePageChoicePugin control = new CompositePageChoicePugin(parent,SWT.NULL);
		Shell shell = parent.getShell();
		getController().setCompositePageChoicePugin(control);
		getController().setShell(shell);
		getController().initialActionListenerPagePlugin();
		setControl(control);
		
		setPageComplete(getController().validatePagePlugin());

	}

	public WizardController getController(){
		return ((WizardModelLettre)getWizard()).getWizardController();
	}
}

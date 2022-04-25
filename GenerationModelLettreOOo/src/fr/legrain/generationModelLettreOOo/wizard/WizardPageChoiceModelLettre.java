package fr.legrain.generationModelLettreOOo.wizard;

import java.util.List;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import fr.legrain.generationModelLettreOOo.divers.ConstModelLettre;

public class WizardPageChoiceModelLettre extends WizardPage implements IModelWizardPage {

	protected WizardPageChoiceModelLettre(String pageName) {
		super(pageName);
		// TODO Auto-generated constructor stub
		setTitle(ConstModelLettre.TITLE_PAGE_CHOICE_MODEL_LETTER);
		setDescription(ConstModelLettre.DESCRIPTION_PAGE_CHOICE_MODEL_LETTER);
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createControl(Composite parent) {
		// TODO Auto-generated method stub

		CompositePageChoiceModelLetter control = new CompositePageChoiceModelLetter(parent,SWT.NULL);
		Shell shell = parent.getShell();
		getController().setCompositePageChoiceModelLetter(control);
		getController().setShell(shell);
//		getController().initialActionListenerPageModelLetter();		
		setControl(control);
		
		setPageComplete(false);
	}
	
	public WizardController getController(){
		return ((WizardModelLettre)getWizard()).getWizardController();
	}
}

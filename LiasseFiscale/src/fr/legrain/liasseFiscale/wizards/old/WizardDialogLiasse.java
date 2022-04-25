package fr.legrain.liasseFiscale.wizards.old;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import fr.legrain.liasseFiscale.wizards.ILgrWizardPage;

public class WizardDialogLiasse extends WizardDialog {

	private WizardLiasse wizard;
	
	public WizardDialogLiasse(Shell parentShell, IWizard newWizard) {
		super(parentShell, newWizard);
		wizard = (WizardLiasse)newWizard;
	}

	@Override
	protected void backPressed() {
		super.backPressed();
		//wizard.backPressed((ILgrWizardPage)getCurrentPage());
	}

	@Override
	protected void finishPressed() {
		super.finishPressed();
	}

	@Override
	protected void nextPressed() {
		super.nextPressed();
		wizard.nextPressed((ILgrWizardPage)getCurrentPage().getPreviousPage());
	}
	
}

package fr.legrain.liasseFiscale.wizards;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

public class WizardDialogDossier extends WizardDialog {

	private WizardDossier wizard;
	
	public WizardDialogDossier(Shell parentShell, IWizard newWizard) {
		super(parentShell, newWizard);
		wizard = (WizardDossier)newWizard;
	}

	@Override
	protected void backPressed() {
		super.backPressed();
	}

	@Override
	protected void nextPressed() {
		super.nextPressed();
		wizard.nextPressed((ILgrWizardPage)getCurrentPage().getPreviousPage());
	}
	
}

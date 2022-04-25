package fr.legrain.liasseFiscale.wizards;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

public class WizardDialogDoc extends WizardDialog {

	private WizardDoc wizard;
	
	public WizardDialogDoc(Shell parentShell, IWizard newWizard) {
		super(parentShell, newWizard);
		wizard = (WizardDoc)newWizard;
	}

	@Override
	protected void backPressed() {
		super.backPressed();
	}

	@Override
	protected void finishPressed() {
		super.finishPressed();
	//	wizard.finishPressed((ILgrWizardPage)getCurrentPage());
	}

	@Override
	protected void nextPressed() {
		super.nextPressed();
		wizard.nextPressed((ILgrWizardPage)getCurrentPage().getPreviousPage());
	}
	
}

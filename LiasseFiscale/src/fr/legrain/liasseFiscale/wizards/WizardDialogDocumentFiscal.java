package fr.legrain.liasseFiscale.wizards;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

public class WizardDialogDocumentFiscal extends WizardDialog implements ILgrWizardDialog{

	private WizardDocumentFiscal wizard;
	
	public WizardDialogDocumentFiscal(Shell parentShell, IWizard newWizard) {
		super(parentShell, newWizard);
		wizard = (WizardDocumentFiscal)newWizard;
//		setHelpAvailable(true);
//		setDialogHelpAvailable(true);
	}

	@Override
	protected void backPressed() {
		super.backPressed();
		//wizard.backPressed((ILgrWizardPage)getCurrentPage());
	}

	@Override
	protected void nextPressed() {
		wizard.nextPressed((ILgrWizardPage)getCurrentPage());
		super.nextPressed();
	}

	@Override
	protected void finishPressed() {
		wizard.finishPressed((ILgrWizardPage)getCurrentPage());
		super.finishPressed();
	}
	
	public void resize(IWizardPage p) {
		this.updateSize(p);
	}
	
}

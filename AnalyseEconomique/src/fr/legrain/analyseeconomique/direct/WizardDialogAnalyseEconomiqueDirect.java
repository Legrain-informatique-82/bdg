package fr.legrain.analyseeconomique.direct;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import fr.legrain.liasseFiscale.wizards.ILgrWizardPage;

public class WizardDialogAnalyseEconomiqueDirect extends WizardDialog {

	private WizardAnalyseEconomiqueDirect wizard;
	
	public WizardDialogAnalyseEconomiqueDirect(Shell parentShell, IWizard newWizard) {
		super(parentShell, newWizard);
		wizard = (WizardAnalyseEconomiqueDirect)newWizard;
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

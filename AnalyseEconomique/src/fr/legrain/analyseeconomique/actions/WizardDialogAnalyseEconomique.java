package fr.legrain.analyseeconomique.actions;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import fr.legrain.liasseFiscale.wizards.ILgrWizardPage;

public class WizardDialogAnalyseEconomique extends WizardDialog {

	private WizardAnalyseEconomique wizard;
	
	public WizardDialogAnalyseEconomique(Shell parentShell, IWizard newWizard) {
		super(parentShell, newWizard);
		wizard = (WizardAnalyseEconomique)newWizard;
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

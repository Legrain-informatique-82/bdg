package fr.legrain.liasseFiscale.wizards.fichier;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import fr.legrain.liasseFiscale.wizards.ILgrWizardDialog;
import fr.legrain.liasseFiscale.wizards.ILgrWizardPage;

public class WizardDialogDocFichier extends WizardDialog implements ILgrWizardDialog{

	private WizardDocFichier wizard;
	
	public WizardDialogDocFichier(Shell parentShell, IWizard newWizard) {
		super(parentShell, newWizard);
		wizard = (WizardDocFichier)newWizard;
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
		
		//wizard.nextPressed((ILgrWizardPage)getCurrentPage().getPreviousPage());
		wizard.nextPressed((ILgrWizardPage)getCurrentPage());
		super.nextPressed();
	}
	
	public void resize(IWizardPage p) {
		this.updateSize(p);
	}
	
}

package fr.legrain.liasseFiscale.wizards.old;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import fr.legrain.liasseFiscale.wizards.ILgrWizardPage;

public class WizardPageDebut extends WizardPage implements ILgrWizardPage {
	static final public String PAGE_NAME = "WizardPageDebut";
	static final private String PAGE_TITLE = "Liasse fiscale";
	private Control control;

	public WizardPageDebut(String pageName) {
		super(pageName);
		setTitle(PAGE_TITLE);
		//setDescription("Description page 1");
	}

	public void createControl(Composite parent) {
		CompositePageDebut control = new CompositePageDebut(parent,SWT.NULL);
		
		this.control = control;
		setControl(control);
	}

	public Control getControl() {
		return control;
	}
	
	@Override
	public IWizardPage getNextPage() {
		if(((CompositePageDebut)control).getBtnNouvelleLiasseEtDossier().getSelection()){
			return getWizard().getPage(WizardPageNouveauDossier.PAGE_NAME);
		} else if(((CompositePageDebut)control).getBtnNouvelleLiasse().getSelection()){
			return getWizard().getPage(WizardPageAjoutLiasse.PAGE_NAME);
		} else if(((CompositePageDebut)control).getBtnRepriseLiasse().getSelection()){
			return getWizard().getPage(WizardPageRepriseLiasse.PAGE_NAME);
		} else {
			return null;
		}
	}

	public boolean validatePage() {
        setErrorMessage(null);
        setMessage(null);
        return true;
	}

	public void finishPage() {
		if (isPageComplete()) {
		}
	}

	public void saveToModel() {
		// TODO Auto-generated method stub
		
	}

}

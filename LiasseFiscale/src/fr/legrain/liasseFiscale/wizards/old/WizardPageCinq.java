package fr.legrain.liasseFiscale.wizards.old;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import fr.legrain.liasseFiscale.wizards.ILgrWizardPage;

public class WizardPageCinq extends WizardPage implements ILgrWizardPage {
	static final public String PAGE_NAME = "WizardPageCinq";
	static final private String PAGE_TITLE = "Fichier XML de base et saisie des infos complémentaires";
	private Control control;

	public WizardPageCinq(String pageName) {
		super(pageName);
		setTitle(PAGE_TITLE);
		// TODO Auto-generated constructor stub
	}

	public void createControl(Composite parent) {
		// TODO Auto-generated method stub
		control = new CompositePageCinq(parent,SWT.NULL);
		setControl(control);
	}
	
	public Control getControl() {
		return control;
	}

	public boolean validatePage() {
		// TODO Auto-generated method stub
		return false;
	}

	public void finishPage() {
		if (isPageComplete()) {}	
	}

	public void saveToModel() {
		// TODO Auto-generated method stub
		
	}

}

package fr.legrain.liasseFiscale.wizards;

import org.apache.log4j.Logger;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.graphics.Point;

public class WizardTVA extends Wizard implements IWizardNode{
	
	static Logger logger = Logger.getLogger(WizardTVA.class.getName());
	
	private WizardLiasseModel model = new WizardLiasseModel(); 
	
	private WizardPageAFaire wizardPageAFaire = new WizardPageAFaire(WizardPageAFaire.PAGE_NAME);
	
	public WizardTVA() {
		super();
		setWindowTitle("TVA Test");

		addPage(wizardPageAFaire);

	}
	
	protected void backPressed(ILgrWizardPage page) {
	}
	
	protected void nextPressed(ILgrWizardPage page) {
		page.finishPage();
	}
	
	protected void finishPressed(ILgrWizardPage page) {
		page.finishPage();
	}

	@Override
	public boolean performFinish() {
		return true;
	}
	
	public WizardLiasseModel getModel() {
		return model;
	}

////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
	public Point getExtent() {
		// TODO Auto-generated method stub
		return new Point(-1,-1);
	}

	public IWizard getWizard() {
		// TODO Auto-generated method stub
		return this;
	}

	public boolean isContentCreated() {
		// TODO Auto-generated method stub
		return false;
	}
	
}

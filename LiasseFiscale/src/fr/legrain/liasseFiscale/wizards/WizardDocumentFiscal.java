package fr.legrain.liasseFiscale.wizards;

import org.apache.log4j.Logger;
import org.eclipse.jface.wizard.Wizard;

public class WizardDocumentFiscal extends Wizard {
	
	static Logger logger = Logger.getLogger(WizardDocumentFiscal.class.getName());
	
	private WizardLiasseModel model = new WizardLiasseModel(); 
	
	private WizardPageDocumentFiscal wizardPageDocumentFiscal = new WizardPageDocumentFiscal(WizardPageDocumentFiscal.PAGE_NAME);
	private WizardPageAFaire wizardPageComplement = new WizardPageAFaire(WizardPageAFaire.PAGE_NAME);
	
//	private ArrayList<WizardPageSaisie> listePageSaisie = new ArrayList<WizardPageSaisie>();
//	private Composite pageContainer;
	
	public WizardDocumentFiscal() {
		super();
		setWindowTitle("Assistant - Document fiscal");
		
		addPage(wizardPageDocumentFiscal);
//		addPage(wizardPageRegime);

		addPage(wizardPageComplement);
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
	
	@Override
	public boolean canFinish() {
		if(getContainer().getCurrentPage().getName().equals(WizardPageDocumentFiscal.PAGE_NAME)) {
			return false;
		}
		return true;
	}

	public WizardLiasseModel getModel() {
		return model;
	}
	
//	@Override
//	public void createPageControls(Composite pageContainer) {
//		super.createPageControls(pageContainer);
//		this.pageContainer = pageContainer;
//	}
	
}

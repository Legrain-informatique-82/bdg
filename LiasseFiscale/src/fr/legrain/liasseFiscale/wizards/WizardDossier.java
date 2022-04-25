package fr.legrain.liasseFiscale.wizards;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.graphics.Point;

import fr.legrain.liasseFiscale.wizards.fichier.WizardDialogDocFichier;

public class WizardDossier extends Wizard implements IWizardNode{
	
	static Logger logger = Logger.getLogger(WizardDossier.class.getName());
	
	private WizardLiasseModel model = new WizardLiasseModel(); 
	
	private WizardPageCreationDossier wizardPageCreationDossier = new WizardPageCreationDossier(WizardPageCreationDossier.PAGE_NAME);
	
	public WizardDossier() {
		super();
		setWindowTitle("Assistant - Création d'un nouveau dossier");
	
		addPage(wizardPageCreationDossier);

	}
	
	protected void backPressed(ILgrWizardPage page) {
	}
	
	protected void nextPressed(ILgrWizardPage page) {
		page.finishPage();
	}
	
	public boolean canFinish() {
		if(getContainer() instanceof WizardDialogDocFichier //le wizard est un noeud de WizardDocFichier
				&& getContainer().getCurrentPage().getName().equals(WizardPageCreationDossier.PAGE_NAME)) {
			return false;
		}
		return true;
		//return super.canFinish();
	}
	
	@Override
	public boolean performFinish() {
		File dossier = new File(model.getCheminDossiers()+"/"+model.getNomDossier());
		dossier.mkdirs();

		return true;
	}
	
	public WizardLiasseModel getModel() {
		return model;
	}
	


	public Point getExtent() {
		//TODO Auto-generated method stub
		return new Point(-1,-1);
	}

	public IWizard getWizard() {
		//TODO Auto-generated method stub
		return this;
	}

	public boolean isContentCreated() {
		//TODO Auto-generated method stub
		return false;
	}

}

package fr.legrain.generationModelLettreWS.wizard;

import java.util.List;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import fr.legrain.generationModelLettreMS.divers.ConstModelLettre;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class WizardPageChoiceModelLettre extends WizardPage implements IModelWizardPage {

	protected WizardPageChoiceModelLettre(String pageName) {
		super(pageName);
		// TODO Auto-generated constructor stub
		setTitle(ConstModelLettre.TITLE_PAGE_CHOICE_MODEL_LETTER);
		setDescription(ConstModelLettre.DESCRIPTION_PAGE_CHOICE_MODEL_LETTER);
	}

	@Override
	public void finishPage() {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveToModel() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean validatePage() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createControl(Composite parent) {
		// TODO Auto-generated method stub

		CompositePageChoiceModelLetter control = new CompositePageChoiceModelLetter(parent,SWT.NULL);
		Shell shell = parent.getShell();
		getController().setCompositePageChoiceModelLetter(control);
		getController().setShell(shell);
//		getController().initialActionListenerPageModelLetter();				
		setControl(control);
		
		setPageComplete(false);
//		parent.setSize(447, 474);
	}
	
	public WizardController getController(){
		return ((WizardModelLettre)getWizard()).getWizardController();
	}
}

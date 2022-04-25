package fr.legrain.generationLabelEtiquette.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.ui.PlatformUI;

import fr.legrain.generationLabelEtiquette.divers.GenerationFileEtiquette;
import fr.legrain.generationLabelEtiquette.wizard.HeadlessEtiquette;
import fr.legrain.generationLabelEtiquette.wizard.WizardDialogModelLabels;
import fr.legrain.generationLabelEtiquette.wizard.WizardModelLables;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;


public class HandlerGenerationLabelsEtiquette extends LgrAbstractHandler {

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			
			ParamWizardEtiquettes p = null;
//			p = new ParamWizardEtiquettes();
//			//p.setModelePredefini("test_2");
//			p.setModeIntegre(true);
//			p.setCheminFichierDonnees("/home/nicolas/Desktop/Publipostage-30122010.txt");
//			p.setCheminFichierMotsCle("/home/nicolas/Desktop/MotCleExtractionTiers.properties");
//			p.setSeparateur(";");
	
			GenerationFileEtiquette generationFileEtiquette = new GenerationFileEtiquette();
			//WizardModelLables wizardModelLables = new WizardModelLables(generationFileEtiquette);
			WizardModelLables wizardModelLables = new WizardModelLables(generationFileEtiquette,p);
			WizardDialogModelLabels wizardDialogModelLabels = new WizardDialogModelLabels
				(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),wizardModelLables);
			wizardDialogModelLabels.open();
			
//			HeadlessEtiquette headlessEtiquette = new HeadlessEtiquette();
//			headlessEtiquette.test();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isHandled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

}

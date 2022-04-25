package fr.legrain.generationModelLettreMS.handlers;

import org.apache.commons.logging.impl.AvalonLogger;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.PlatformUI;

import fr.legrain.generationModelLettreMS.divers.ConstModelLettre;
import fr.legrain.generationModelLettreMS.divers.FonctionGeneral;
import fr.legrain.generationModelLettreMS.divers.ParametrePublicPostage;
import fr.legrain.generationModelLettreWS.preferences.PreferenceConstants;
import fr.legrain.generationModelLettreWS.wizard.WizardDialogModelLettre;
import fr.legrain.generationModelLettreWS.wizard.WizardModelLettre;

import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import generationmodellettrems.Activator;

public class HandlerGenerationLettreWS extends LgrAbstractHandler {

	static Logger logger = Logger.getLogger(HandlerGenerationLettreWS.class.getName());
	
	
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
		// TODO Auto-generated method stub
		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		FonctionGeneral fonctionGeneral = new FonctionGeneral();
		
		String pathOffice = preferenceStore.getString(PreferenceConstants.PATH_WORD_OFFICE);
		String pathSavePublipostage = preferenceStore.getString(PreferenceConstants.PATH_SAVE_PUBLIPOSTAGE_WO);
//		boolean optionAffichage = preferenceStore.getBoolean(PreferenceConstants.OPTION_AFFICHAGE);
		
		fonctionGeneral.setTypeOffice(ConstModelLettre.TYPE_OFFICE_WO);
//		fonctionGeneral.setPathOffice(pathOffice);
		fonctionGeneral.setPathSavePublipostage(pathSavePublipostage);
//		fonctionGeneral.setShowPublipostage(optionAffichage);
		
		ParametrePublicPostage parametrePublicPostage = new ParametrePublicPostage();
		parametrePublicPostage.setPathFileSavePublicPostage(pathSavePublipostage);
		parametrePublicPostage.setPathDefautFileSavePublipostage(pathSavePublipostage);
		parametrePublicPostage.setPathFileMotCle(Const.C_FICHIER_LISTE_ATTRIBUTE_LETTRE);
		
		WizardModelLettre wizardModelLettre = new WizardModelLettre(fonctionGeneral,parametrePublicPostage);
		WizardDialogModelLettre wizardDialogModelLettre = new WizardDialogModelLettre(PlatformUI.getWorkbench().
														  getActiveWorkbenchWindow().getShell(),wizardModelLettre);
		wizardDialogModelLettre.open();
		
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

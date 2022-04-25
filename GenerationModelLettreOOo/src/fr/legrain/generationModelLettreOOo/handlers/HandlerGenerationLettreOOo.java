package fr.legrain.generationModelLettreOOo.handlers;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.PlatformUI;

import fr.legrain.generationModelLettreOOo.divers.ConstModelLettre;
import fr.legrain.generationModelLettreOOo.divers.FonctionGeneral;
import fr.legrain.generationModelLettreOOo.divers.ParametrePublicPostage;
import fr.legrain.generationModelLettreOOo.preferences.PreferenceConstants;
import fr.legrain.generationModelLettreOOo.wizard.WizardDialogModelLettre;
import fr.legrain.generationModelLettreOOo.wizard.WizardModelLettre;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import generationmodellettreooo.Activator;


public class HandlerGenerationLettreOOo extends LgrAbstractHandler {

	static Logger logger = Logger.getLogger(HandlerGenerationLettreOOo.class.getName());
	
	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
	}
	
	@Override
	public void dispose() {
	}
	
	@Override
	public Object execute(ExecutionEvent event) {
		try {
			IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
			FonctionGeneral fonctionGeneral = new FonctionGeneral();
			String pathSavePublipostage = new File(preferenceStore.getString(PreferenceConstants.PATH_SAVE_PUBLIPOSTAGE_OO)).getPath();
			String pathOffice = preferenceStore.getString(PreferenceConstants.PATH_OPEN_OFFICE);
			String portOffice = preferenceStore.getString(PreferenceConstants.PORT_SERVER_OPEN_OFFICE);

			if(!pathSavePublipostage.equals("")& !pathOffice.equals("")& !portOffice.equals("")) {

				//			boolean optionAffichage = preferenceStore.getBoolean(PreferenceConstants.OPTION_AFFICHAGE);

				fonctionGeneral.setTypeOffice(ConstModelLettre.TYPE_OFFICE_OO);
				fonctionGeneral.setPortOpenOffice(portOffice);
				fonctionGeneral.setPathOffice(pathOffice);
				fonctionGeneral.setPathSavePublipostage(pathSavePublipostage);
				//			fonctionGeneral.setShowPublipostage(optionAffichage);

				ParametrePublicPostage parametrePublicPostage = new ParametrePublicPostage();

				parametrePublicPostage.setPathFileSavePublicPostage(pathSavePublipostage);
				parametrePublicPostage.setPathDefautFileSavePublipostage(pathSavePublipostage);
				parametrePublicPostage.setPathFileMotCle(Const.C_FICHIER_LISTE_ATTRIBUTE_LETTRE);

				WizardModelLettre wizardModelLettre = new WizardModelLettre(fonctionGeneral,
						parametrePublicPostage);
				WizardDialogModelLettre wizardDialogModelLettre = new WizardDialogModelLettre(PlatformUI.getWorkbench().
						getActiveWorkbenchWindow().getShell(),wizardModelLettre);
				wizardDialogModelLettre.open();
			} else {
				logger.info("Preferences OpenOffice incompletes");
				MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
						"Erreur", "Les préférences concernant OpenOffice ne sont pas toutes renseignées.\n\n" +
								"Veuillez aller dans le menu Outils/Préférences pour paramétrer OpenOffice avant d'utiliser le publipostage.");
			}
		} catch (Exception e) {
			logger.error("",e);
		}

		
		return null;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
	public boolean isHandled() {
		return true;
	}
	
	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
	}

}

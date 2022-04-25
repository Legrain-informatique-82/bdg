package fr.legrain.sauvegardeFTP.handlers;
import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.libMessageLGR.LgrMess;
import fr.legrain.sauvegarde.handlers.FonctionSauvegarde;
import fr.legrain.sauvegardeFTP.Activator;
import fr.legrain.sauvegardeFTP.divers.FonctionGeneral;
import fr.legrain.sauvegardeFTP.preferences.PreferenceConstants;


//public class HandlerSauvergardeFTP implements IHandler {
public class HandlerSauvergardeFTP extends LgrAbstractHandler {

	static Logger logger = Logger.getLogger(HandlerSauvergardeFTP.class.getName());
	
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
		
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		FonctionGeneral fonctionGeneral = new FonctionGeneral();
		if(LgrEditorPart.verifEditeurOuvert()!=null){
			MessageDialog.openInformation(shell,"Sauvegarde","Vous devez fermer tous les écrans avant de lancer la sauvegarde du dossier !");	
		}else{
			boolean flag = fonctionGeneral.getInfosFileProperties(Const.C_FICHIER_PREFERENCE_PAGE_FTP);
			if(flag){
				try {
					PlatformUI.getWorkbench().getProgressService().run(true, false, fonctionGeneral);
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					logger.error("", e);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					logger.error("", e);
				}
				if(!fonctionGeneral.isFlagSauvegardeFTP()){
					logger.info("Impossible d'envoyer vers le serveur distant !");
					MessageDialog.openError(shell,"Erreur", "Impossible d'envoyer votre sauvegarde vers le serveur distant.\n\n" +
							"Veuillez vérifier les paramèmtres de connection au serveur distant.\n"+
							LgrMess.C_CONTACTER_SERVICE_MAINTENANCE);
				}else{
					logger.info("envoyer vers serveur FTP !");
					MessageDialog.openInformation(shell,"Information", "Le transfert a été effectué avec succès!");
				}
			}else{
				logger.info("Preferences Sauvegarde FTP incompletes");
				MessageDialog.openError(shell,"Erreur", 
						"Les préférences concernant le serveur distant ne sont pas toutes renseignées.\n\n" +
						"Veuillez aller dans le menu Outils/Préférences pour paramétrer serveur distant avant d'utiliser " +
						"Sauvegarde distante.\n"+
						LgrMess.C_CONTACTER_SERVICE_MAINTENANCE);
			}
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

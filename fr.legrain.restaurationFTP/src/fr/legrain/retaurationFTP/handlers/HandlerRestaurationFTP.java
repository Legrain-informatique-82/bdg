package fr.legrain.retaurationFTP.handlers;

import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.libMessageLGR.LgrMess;
import fr.legrain.restaurationFTP.divers.FonctionGeneralRsFtp;

public class HandlerRestaurationFTP extends LgrAbstractHandler {

	static Logger logger = Logger.getLogger(HandlerRestaurationFTP.class.getName());
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// TODO Auto-generated method stub
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		FonctionGeneralRsFtp fRsFtp = new FonctionGeneralRsFtp();
		fRsFtp.setShell(shell);
		boolean flag = fRsFtp.getInfosFileProperties(Const.C_FICHIER_PREFERENCE_PAGE_FTP);
		if(flag){
			String folderFtpSauvegarde = fRsFtp.getFolderFtpSauvegarde();
			fRsFtp.getListFileFtp(folderFtpSauvegarde);
		
			if(FonctionGeneralRsFtp.retList.size() == 0){
				logger.info("Il n'y a aucune sauvegarde correspondant à votre dossier sur le serveur distant.");
				MessageDialog.openInformation(shell,"Information", 
								  "Il n'y a aucune sauvegarde correspondant à votre dossier sur le serveur distant.\n\n" +
								  LgrMess.C_CONTACTER_SERVICE_MAINTENANCE);
			}else{
//				try {
//					PlatformUI.getWorkbench().getProgressService().run(true, false, fRsFtp);
//				} catch (InvocationTargetException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				fRsFtp.makeInterfaceChoixFileRst(shell);
			}
		}else{
			logger.info("Preferences Sauvegarde FTP incompletes");
			MessageDialog.openError(shell,"Erreur", 
							  "Les préférences concernant serveur distant ne sont pas toutes renseignées.\n\n" +
							  "Veuillez aller dans le menu Outils/Préférences pour paramétrer le serveur distant avant d'utiliser " +
							  "Sauvegarde distante.\n"+
							  LgrMess.C_CONTACTER_SERVICE_MAINTENANCE);
		}
		return null;
	}

}

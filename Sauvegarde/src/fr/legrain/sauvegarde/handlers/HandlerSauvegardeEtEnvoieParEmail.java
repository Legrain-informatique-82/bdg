package fr.legrain.sauvegarde.handlers;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.gestCom.librairiesEcran.swt.LgrAbstractHandler;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrEditorPart;
import fr.legrain.libLgrMail.divers.EnvoieSauvegarde;


public class HandlerSauvegardeEtEnvoieParEmail extends LgrAbstractHandler {

	static Logger logger = Logger.getLogger(HandlerSauvegardeEtEnvoieParEmail.class.getName());

	private IWorkbenchWindow window;

	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

	public void dispose() {
		// TODO Auto-generated method stub
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		this.window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		try {
			if(LgrEditorPart.verifEditeurOuvert()!=null){
				MessageDialog.openInformation(window.getShell(),"Sauvegarde","Vous devez fermer tous les écrans avant de lancer la sauvegarde du dossier !");	
			}
			else{
				String message="...";
				TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO();
				TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
				if(taInfoEntreprise!=null && taInfoEntreprise.getNomInfoEntreprise()!=null) {
					message="Envoie de la sauvegarde de : ";
					message+="\n";
					message+=taInfoEntreprise.getNomInfoEntreprise();
				}


				HandlerSauvegarde sauv = new HandlerSauvegarde();			
				EnvoieSauvegarde envoie = new EnvoieSauvegarde();

				/** 18/01/2010 zhaolin **/
				FonctionSauvegarde fonctionSauvegarde = new FonctionSauvegarde();
//				SessionFtp ftp = new SessionFtp("L01071021","P8140140",21,"test","monftp-1.net");
//				String connexion =ftp.ouvrirConnexion();
//				ftp.envoyerFichier(sauv.sauvegarde());
//				if(!connexion.equals(""))MessageDialog.openInformation(window.getShell(),"Connexion au serveur",connexion);
				if(envoie.verifParamsConnexion(true))
//				  envoie.sendSauvegardeFiles(sauv.sauvegarde(),message);
					envoie.sendSauvegardeFiles(fonctionSauvegarde.sauvegarde(),message);
				else throw new Exception();
				MessageDialog.openInformation(window.getShell(),"Sauvegarde et envoie par E-mail","Sauvegarde et envoie terminés.");
			}
		} catch(Exception e) {
			MessageDialog.openInformation(window.getShell(),"Sauvegarde et envoie par E-mail","Erreur durant la sauvegarde et l'envoie par E-mail.");
			logger.error("",e);
		}
		return null;
	}

	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isHandled() {
		// TODO Auto-generated method stub
		return true;
	}

	public void removeHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

}

package fr.legrain.libLgrMail.actions;

import java.io.File;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

import fr.legrain.libLgrMail.LibLgrMailPlugin;
import fr.legrain.libLgrMail.MailUtil;
import fr.legrain.libLgrMail.preferences.PreferenceConstants;
import fr.legrain.libMessageLGR.*;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class MailAction implements IWorkbenchWindowActionDelegate {

	static Logger logger = Logger.getLogger(MailAction.class.getName());

	private IWorkbenchWindow window;

	/**
	 * The constructor.
	 */
	public MailAction() {
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		window.getShell().getDisplay().asyncExec(new Runnable(){
			public void run() {
				try {
					MailAction m = new MailAction();
					m.sendLogFiles();
					MessageDialog.openInformation(
							window.getShell(),
							"Envoie des fichiers de log",
					"Fichiers de log correctement envoyés par e-mail");
				} catch (Exception e) {
					String message = "Erreur durant l'envoie de l'email"
						+LgrMess.C_DOUBLE_SAUT_DE_LIGNE
						+"Modifier vos paramètres d'email dans le menu :"
						+LgrMess.C_SAUT_DE_LIGNE
						+"Affichage/Préférences/E-mail"
						+LgrMess.C_DOUBLE_SAUT_DE_LIGNE
						+"ou"
						+LgrMess.C_CONTACTER_SERVICE_MAINTENANCE;
					MessageDialog.openInformation(
							window.getShell(),
							"Envoie des fichiers de log",
					message);					
					logger.error("",e);
				}
			}
		});
	}

	/**
	 * Envoie des fichiers de logs du programme 
	 */
	public void sendLogFiles() throws Exception{
		MailUtil mailUtil = new MailUtil();
		String smtp = LibLgrMailPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_SERVEUR_SMTP);
		String to = LibLgrMailPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_ADRESSE_MAIL_DEST);
		String from = LibLgrMailPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_ADRESSE_MAIL_EXP);
		String user = LibLgrMailPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_USER);
		String password = LibLgrMailPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PASSWORD);
		boolean ssl = LibLgrMailPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.P_SSL);
		String subject = "[Bureau de gestion] fichiers de log";
		String body = "...";
		ArrayList<String> fichiers = new ArrayList<String>();
		String[] tableauFichiers;
		String log1 = Platform.getInstanceLocation().getURL().getFile()+"/.metadata/.log";
		String log2 = Platform.getInstallLocation().getURL().getFile()+"/log_gestCom.log";
		if(smtp!=null && !smtp.equals("")
				&& to!=null && !to.equals("")
				&& from!=null && !from.equals("")) {

			//Préparation de la liste des fichiers joints
			//TODO amélioré la préparation des fichiers et les zippés dans une seule archive
			File file1 = new File(log1);
			if(file1!=null && file1.exists()) {
				fichiers.add(log1);
			}
			File file2 = new File(log2);
			if(file2!=null && file2.exists()) {
				fichiers.add(log2);
			}
			//convertion en tableau
			tableauFichiers = new String[fichiers.size()];
			int i = 0;
			for (String s : fichiers) {
				tableauFichiers[i] = s;
			}

			mailUtil.smtpMailSender(smtp,
							user,password,ssl,
					from,
					to,
					subject,
					body,
					tableauFichiers
			);
		} else {
			MessageDialog.openInformation(PlatformUI.getWorkbench().
					getActiveWorkbenchWindow().getShell(), "Configuration des E-mails", LgrMess.C_CONFIG_EMAIL
					+LgrMess.C_SAUT_DE_LIGNE
					+"L'E-mail n'a pas pu être envoyé.");
			throw new Exception();
		}
	}

	
	
	
	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}
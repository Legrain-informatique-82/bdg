package fr.legrain.libLgrMail.divers;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import fr.legrain.libLgrMail.LibLgrMailPlugin;
import fr.legrain.libLgrMail.MailUtil;
import fr.legrain.libLgrMail.preferences.PreferenceConstants;
import fr.legrain.libMessageLGR.LgrMess;

public class EnvoieSauvegarde {
	/**
	 * Envoie des fichiers de logs du programme 
	 */
	public void sendSauvegardeFiles(String pathSauvegarde, String message) throws Exception{
		MailUtil mailUtil = new MailUtil();
		String smtp = LibLgrMailPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_SERVEUR_SMTP);
		String to = LibLgrMailPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_ADRESSE_MAIL_DEST);
		String from = LibLgrMailPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_ADRESSE_MAIL_EXP);
		String user = LibLgrMailPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_USER);
		String password = LibLgrMailPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PASSWORD);
		boolean ssl = LibLgrMailPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.P_SSL);
		String subject = "[Bureau de gestion] fichiers de sauvegarde";
		String body = message ;
		ArrayList<String> fichiers = new ArrayList<String>();
		String[] tableauFichiers;
		String sauvegarde = pathSauvegarde;
		if(verifParamsConnexion(false)) {

			//Préparation de la liste des fichiers joints
			//TODO amélioré la préparation des fichiers et les zippés dans une seule archive
			File file1 = new File(sauvegarde);
			if(file1!=null && file1.exists()) {
				fichiers.add(sauvegarde);
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
	

	public boolean verifParamsConnexion(Boolean message){
		String smtp = LibLgrMailPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_SERVEUR_SMTP);
		String to = LibLgrMailPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_ADRESSE_MAIL_DEST);
		String from = LibLgrMailPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_ADRESSE_MAIL_EXP);
		String user = LibLgrMailPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_USER);
		String password = LibLgrMailPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PASSWORD);
		Boolean result = (!smtp.equals("")&& !smtp.equals(null)
				&& !to.equals("")&& !to.equals(null)
				&& !from.equals("")&& !from.equals(null)
				&& !user.equals("")&& !user.equals(null)
				&& !password.equals("")&& !password.equals(null));;
		if(!result)MessageDialog.openInformation(PlatformUI.getWorkbench().
				getActiveWorkbenchWindow().getShell(), "Configuration des E-mails", LgrMess.C_CONFIG_EMAIL);

		return result;
		
	}
}

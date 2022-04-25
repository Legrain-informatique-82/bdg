package fr.legrain.openmail.mail;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.eclipse.ui.PartInitException;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import fr.legrain.openmail.mail.preferences.PreferenceConstantsProject;
import fr.legrain.openmail.mail.ui.EditorBrowserOpenMail;

public class OpenmailMail {
	
	static Logger logger = Logger.getLogger(OpenmailMail.class.getName());
	
	public static final String URL_OPENMAIL = "http://app1.openmail.fr/api/sendEmail.php";
	public static final String URL_OPENMAIL_MAILING = "http://app1.openmail.fr/api/sendMailing.php";
//	public static final String URL_OPENMAIL = "http://192.168.1.35/aptana/open-mail/api/sendEmail.php";
	public static final String URL_OPENMAIL_ACCOUNT = "http://app1.openmail.fr/api/account.php";
	//"devapi.openmail.fr"
	//public static final String URL_OPENMAIL = "http://www.openmail.fr/mail.php";
	
	public boolean useOpenMail() {
		return Activator.getDefault().getPreferenceStoreProject().getBoolean(PreferenceConstantsProject.USE_OPENMAIL);
	}
	
	/**
	 * Vérification de la configuration d'openmail si le service doit être utilisé, ou de la disponibilité du client mail par défaut le cas échéant.
	 * @return - null si aucun problème, sinon le message d'erreur explicant le problème
	 */
	public String checkConfig() {
		String retour = null;
		
		String login = null;
		String password = null;
		String expediteur = null;
		
		if(useOpenMail()) {
			if(login==null)
				login = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.LOGIN);
			if(password==null)
				password = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.PASSWORD);
			if(expediteur==null)
				expediteur = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.LOGIN);


			if(login.equals("")
					|| password.equals("") ) {
				retour = "Open Mail n'est pas (ou incorrectement) configuré. Veuillez le configurer dans le menu Outils/Préférences pour pouvoir l'utiliser";
			}
		} else {
			if(!Desktop.isDesktopSupported() || Desktop.getDesktop().isSupported(java.awt.Desktop.Action.MAIL)) {
				retour = "Impossible d'utiliser ou d'identifié votre client mail par défaut. Vous pouvez utiliser le service Open Mail à la place.";
			}
		}
		
		if(retour!=null) {
			logger.error(retour);
		}
		
		return retour;
	}
	
	public void sendMail(String[] destinataires, String[] destinatairesBc, String[] destinatairesBcc, String sujet, String body, Map<String, String> pieceJointes, String login, String password, String expediteur, boolean mailing) {
		
		if(useOpenMail()) {
			if(login==null)
				login = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.LOGIN);
			if(password==null)
				password = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.PASSWORD);
			if(expediteur==null)
				expediteur = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.LOGIN);
		}

		if(useOpenMail() 
				&& login!=null && !login.equals("")
				&& password!=null && !password.equals("") ) {
			openMailService(destinataires, destinatairesBc, destinatairesBcc, sujet, body,  pieceJointes, login, password, expediteur, mailing);
		} else {
			sendMailClientSysteme(destinataires[0]);
		}
	}
	
	public void sendMail(String[] destinataires, String sujet, String body, Map<String, String> pieceJointes, String login, String password, String expediteur, boolean mailing) {
		sendMail(destinataires,null,null,sujet,body,pieceJointes,login,password,expediteur,mailing);
	}
	
	public void sendMail(String destinataire, boolean mailing) {
		sendMail(new String[] {destinataire}, null, null, null, null, null, null, mailing);
	}
	
	public void sendMail(String destinataire) {
		sendMail(new String[] {destinataire}, null, null, null, null, null, null, false);
	}
	
	public void sendMail(String[] destinataires, String sujet, String body, Map<String, String> pieceJointes, boolean mailing) {
		sendMail(destinataires, sujet, body, pieceJointes, null, null, null,mailing);
	}
	
	public void sendMail(String[] destinataires, String sujet, String body, Map<String, String> pieceJointes) {
		sendMail(destinataires, sujet, body, pieceJointes, null, null, null,false);
	}
	
	private void openMailService(String[] destinatairesTo, String[] destinatairesBc, String[] destinatairesBcc, String sujet, String body, Map<String, String> pieceJointes, 
			String login, String password, String expediteur, boolean mailing) {

		String url = URL_OPENMAIL;
		if(mailing) {
			url = URL_OPENMAIL_MAILING;
		}
		String browserTitle = "OpenMail Email";
		String browserTooltip = browserTitle;
		String urlEncoding = "UTF-8";
		String nomVariablePost = "script";
		//Pour IE à partir de BDG : Content-Type:application/x-www-form-urlencoded
		String[] httpHeader = new String[]{"Content-Type:application/x-www-form-urlencoded"};
		
//		Map<String,String> param = new HashMap<String,String>();
//		UtilHTTP http = new UtilHTTP();
//		param.put("script", post);
//		http.post(url,param);
		
		String fichierPost = null;
//		fichierPost = encodeFileToBase64(pieceJointes[0]);
		
//		System.out.println(fichierPost);
		
		Map<String,Object> objetGlobal = new LinkedHashMap<String,Object>();
		
		String from = null;
		String replyto = null;
		if(from==null)
			from = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.EXPEDITEUR_EMAIL);
		if(replyto==null)
			replyto = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.REPONDRE_A_EMAIL);
		
		objetGlobal.put("login", login);
		objetGlobal.put("password", password);
		objetGlobal.put("from", from.equals("") ? expediteur : from);
		objetGlobal.put("replyto", replyto.equals("") ? expediteur : replyto);
		
		objetGlobal.put("subject", sujet);
		objetGlobal.put("content", body);
		
		//gestion des pièces jointes
		Map<String,Object> objInterne = null;
		//if(pieceJointes!=null && pieceJointes.length>0) {
		if(pieceJointes!=null) {
			JSONArray arrayPJ = new JSONArray();
			String nomPieceJointe = null;
			for (String cheminFichier : pieceJointes.keySet()) {
			//for (int i = 0; i < pieceJointes.length; i++) {
				fichierPost = encodeFileToBase64(cheminFichier);
				if(fichierPost!=null) {
					objInterne = new LinkedHashMap<String,Object>();
					if(pieceJointes.get(cheminFichier)!=null && !pieceJointes.get(cheminFichier).equals(""))
						nomPieceJointe = pieceJointes.get(cheminFichier);
					else
						nomPieceJointe = new File(cheminFichier).getName();
					objInterne.put("filename",nomPieceJointe);
					objInterne.put("content",fichierPost);
				}
				arrayPJ.add(objInterne);
			}
			objetGlobal.put("files",arrayPJ);
		}
		//gestion des destinataires
		if(destinatairesTo!=null && destinatairesTo.length>0) {
			JSONArray arrayDest = new JSONArray();
			for (int i = 0; i < destinatairesTo.length; i++) {
				objInterne = new LinkedHashMap<String,Object>();
				//objInterne.put("email",destinataires[i]);
				objInterne.put("email",destinatairesTo[i]);
				arrayDest.add(objInterne);
			}
			objetGlobal.put("to",arrayDest);
			//objetGlobal.put("bcc",arrayDest);
		}
		
		//gestion des copies
		if(destinatairesBc!=null && destinatairesBc.length>0) {
			JSONArray arrayDest = new JSONArray();
			for (int i = 0; i < destinatairesBc.length; i++) {
				objInterne = new LinkedHashMap<String,Object>();
				//objInterne.put("email",destinataires[i]);
				objInterne.put("email",destinatairesBc[i]);
				arrayDest.add(objInterne);
			}
			objetGlobal.put("bc",arrayDest);
		}

		//gestion des copie cachées
		if(destinatairesBcc!=null && destinatairesBcc.length>0) {
			JSONArray arrayDest = new JSONArray();
			for (int i = 0; i < destinatairesBcc.length; i++) {
				objInterne = new LinkedHashMap<String,Object>();
				//objInterne.put("email",destinataires[i]);
				objInterne.put("email",destinatairesBcc[i]);
				arrayDest.add(objInterne);
			}
			objetGlobal.put("bcc",arrayDest);
		}
		
//		objetGlobal.put("to", destinataires[0]);
//		objetGlobal.put("files", fichierPost);

		String post = JSONValue.toJSONString(objetGlobal);
		
		System.err.println(post);
		
		try {
			post=URLEncoder.encode(post, urlEncoding);
			EditorBrowserOpenMail.openURL(url,nomVariablePost+"="+post,httpHeader, browserTitle, browserTooltip);
		} catch (PartInitException e1) {
			logger.error("",e1);
		} catch (UnsupportedEncodingException ex) {
			logger.error("",ex);
		}
	}
	
	public void openMailAccount() {
		openMailAccount(null,null);
	}
	
	public void openMailAccount(String login, String password) {
		if(login==null)
			login = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.LOGIN);
		if(password==null)
			password = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.PASSWORD);
		
		String url = URL_OPENMAIL_ACCOUNT;
		String browserTitle = "OpenMail Account";
		String browserTooltip = browserTitle;
		String urlEncoding = "UTF-8";
		String nomVariablePost = "script";
		//Pour IE à partir de BDG : Content-Type:application/x-www-form-urlencoded
		String[] httpHeader = new String[]{"Content-Type:application/x-www-form-urlencoded"};

		String actionBoutton = "valider";
		
		Map<String,Object> objetGlobal = new LinkedHashMap<String,Object>();

		
		objetGlobal.put("login", login);
		objetGlobal.put("password", password);
		objetGlobal.put("action", actionBoutton);

		String post = JSONValue.toJSONString(objetGlobal);
	
		try {
			post=URLEncoder.encode(post, urlEncoding);
			EditorBrowserOpenMail.openURL(url,nomVariablePost+"="+post,httpHeader, browserTitle, browserTooltip);
		} catch (PartInitException e1) {
			logger.error("",e1);
		} catch (UnsupportedEncodingException ex) {
			logger.error("",ex);
		}
	}
	
	private void sendMailClientSysteme(String adresseEmail) {
		if(Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(java.awt.Desktop.Action.MAIL)) {
			if(adresseEmail!=null) {
				final String adresse = adresseEmail;
				/*
				 * Definition du format de l'uri mailto
				 * http://www.ietf.org/rfc/rfc2368.txt
				 */
				URI u = null;
				try {
					u = new URI("mailto:"+adresse/*+"?subject=current-issue"+&body=aaa"*/);
//					u = new URI("mailto:"+adresse+"?subject=current-issue"/*+&body=aaa"*/);
					//u = new URI("mailto",adresse,null);
					Desktop.getDesktop().mail(u);
				} catch (URISyntaxException e) {
					logger.error("",e);
				} catch (IOException e) {
					logger.error("Le client mail par defaut n'a pas ete trouve ou n'a pas pu etre ouvert",e);
				}
			}
		}
	}
	
	private String encodeFileToBase64(String filePath) {
		File f = new File(filePath);
		if(f.exists()) {
			String base64Encoding = "UTF-8";
			FileInputStream is;
			try {
				is = new FileInputStream(f);

				int length = (int)f.length();
				byte[] bytes = new byte[length];
				int offset = 0;
				int numRead = 0;
				while (offset < bytes.length
						&& (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
					offset += numRead;
				}
				is.close();
				return new String(Base64.encodeBase64(bytes),base64Encoding);
			} catch (FileNotFoundException e2) {
				logger.error("",e2);
			} catch (IOException e3) {
				logger.error("",e3);
			}
		}
		return null;
	}
	
	private void decodeFileFromBase64(String base64, String filePath) {
		Base64.decodeBase64(base64);
		FileOutputStream fos;
		try {
//			BufferedWriter out = new BufferedWriter(new FileWriter("/home/nicolas/Bureau/test.txt"));
//			BufferedWriter out2 = new BufferedWriter(new FileWriter("/home/nicolas/Bureau/test2.txt"));
//			out.write(base64);
//			out2.write(base64);
//			out.close();
//			out2.close();
			fos = new FileOutputStream(new File(filePath));
			fos.write(Base64.decodeBase64(base64));
			fos.close();
		} catch (FileNotFoundException e2) {
			logger.error("",e2);
		} catch (IOException e4) {
			logger.error("",e4);
		}
	}
	
}

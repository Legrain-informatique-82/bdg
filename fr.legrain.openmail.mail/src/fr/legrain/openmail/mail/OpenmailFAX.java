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

public class OpenmailFAX {
	
	static Logger logger = Logger.getLogger(OpenmailFAX.class.getName());
	
	public static final String URL_OPENMAIL = "http://app1.openmail.fr/api/sendFax.php";
	
	
	public void sendFax(String[] destinataires, String notifyemail, Map<String, String> pieceJointes, String login, String password, String expediteur) {
		
		if(login==null)
			login = fr.legrain.openmail.mail.Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.LOGIN);
		if(password==null)
			password = fr.legrain.openmail.mail.Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.PASSWORD);
		if(expediteur==null)
			expediteur = fr.legrain.openmail.mail.Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.EXPEDITEUR_SMS);

		if(login!=null && !login.equals("")
				&& password!=null && !password.equals("") ) {
			openFaxService(destinataires, notifyemail, pieceJointes, login, password, expediteur);
		}
	}
	
	public void sendFax(String destinataire) {
		sendFax(new String[] {destinataire}, null, null, null, null, null);
	}
	
	public void sendMail(String[] destinataires, String notifyemail, Map<String, String> pieceJointes) {
		sendFax(destinataires, notifyemail, pieceJointes, null, null, null);
	}
	
	private void openFaxService(String[] destinataires, String notifyemail, Map<String, String> pieceJointes, 
			String login, String password, String expediteur) {

		String url = URL_OPENMAIL;
		String browserTitle = "OpenMail FAX";
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
		
		/*
		(OBLIGATOIRE) $json["login"]=(String)'julien@openmail.fr';
(OBLIGATOIRE) $json["password"]=(String)'1234';
 $json["pdf"]=(String)$pdf;
 $json["phones"]=(Object)array(new ObjPhone('+33563303167'),new
ObjPhone('+358465517705'),new ObjPhone('+1111465517705'));
 $json["notifyemail"]=(String)'legrainsas@gmail.com';
		 */
		objetGlobal.put("login", login);
		objetGlobal.put("password", password);
		//objetGlobal.put("from", from.equals("") ? expediteur : from);
		//objetGlobal.put("replyto", replyto.equals("") ? expediteur : replyto);
		
		objetGlobal.put("notifyemail", notifyemail);
		
		//gestion des pièces jointes
		Map<String,Object> objInterne = null;
		//if(pieceJointes!=null && pieceJointes.length>0) {
		if(pieceJointes!=null) {
			JSONArray arrayPJ = new JSONArray();
			String nomPieceJointe = null;
//			for (String cheminFichier : pieceJointes.keySet()) {
//			//for (int i = 0; i < pieceJointes.length; i++) {
//				fichierPost = encodeFileToBase64(cheminFichier);
//				if(fichierPost!=null) {
//					objInterne = new LinkedHashMap<String,Object>();
//					if(pieceJointes.get(cheminFichier)!=null && !pieceJointes.get(cheminFichier).equals(""))
//						nomPieceJointe = pieceJointes.get(cheminFichier);
//					else
//						nomPieceJointe = new File(cheminFichier).getName();
//					objInterne.put("filename",nomPieceJointe);
//					objInterne.put("content",fichierPost);
//				}
//				arrayPJ.add(objInterne);
//			}
//			objetGlobal.put("files",arrayPJ);
			
			fichierPost = encodeFileToBase64(pieceJointes.keySet().iterator().next());
			objetGlobal.put("pdf",fichierPost);
		}
		//gestion des destinataires
		if(destinataires.length>0) {
			JSONArray arrayDest = new JSONArray();
			for (int i = 0; i < destinataires.length; i++) {
				objInterne = new LinkedHashMap<String,Object>();
				objInterne.put("phone",destinataires[i]);
				arrayDest.add(objInterne);
			}
			objetGlobal.put("phones",arrayDest);
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

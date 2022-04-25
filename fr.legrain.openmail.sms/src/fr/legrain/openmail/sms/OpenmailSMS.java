package fr.legrain.openmail.sms;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.ui.PartInitException;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import fr.legrain.openmail.mail.preferences.PreferenceConstantsProject;
import fr.legrain.openmail.mail.ui.EditorBrowserOpenMail;

public class OpenmailSMS {
	
	static Logger logger = Logger.getLogger(OpenmailSMS.class.getName());
	
	//public static final String URL_OPENMAIL = "http://192.168.1.35/aptana/open-mail/sms.php";
	public static final String URL_OPENMAIL = "http://app1.openmail.fr/api/sendSms.php";
	//public static final String URL_OPENMAIL = "http://www.openmail.fr/mail.php";
	
	public void sendSMS(String destinataire) {
		sendSMS(new String[] {destinataire}, null, null, null, null);
	}
	
	public void sendSMS(String[] destinataires, String body, String login, String password, String expediteur) {

		if(login==null)
			login = fr.legrain.openmail.mail.Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.LOGIN);
		if(password==null)
			password = fr.legrain.openmail.mail.Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.PASSWORD);
		if(expediteur==null)
			expediteur = fr.legrain.openmail.mail.Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstantsProject.EXPEDITEUR_SMS);

		if(login!=null && !login.equals("")
				&& password!=null && !password.equals("") ) {
			openSMSService(destinataires, body, login, password, expediteur);
		} 
	}
	
	public void openSMSService(String[] destinataires, String body,
			String login, String password, String expediteur) {

		String url = URL_OPENMAIL;
		String browserTitle = "OpenMail SMS";
		String browserTooltip = browserTitle;
		String urlEncoding = "UTF-8";
		String nomVariablePost = "script";
		//Pour IE à partir de BDG : Content-Type:application/x-www-form-urlencoded
		String[] httpHeader = new String[]{"Content-Type:application/x-www-form-urlencoded"};
		
		Map<String,Object> objetGlobal = new LinkedHashMap<String,Object>();
		
		objetGlobal.put("login", login);
		objetGlobal.put("password", password);
		objetGlobal.put("from", expediteur);
		
		objetGlobal.put("content", body);
	
		//objetGlobal.put("to", destinataires[0]);
		Map<String,Object> objInterne = null;
		if(destinataires.length>0) {
			JSONArray arrayDest = new JSONArray();
			for (int i = 0; i < destinataires.length; i++) {
					objInterne = new LinkedHashMap<String,Object>();
					objInterne.put("phone",destinataires[i]);
				arrayDest.add(objInterne);
			}
			objetGlobal.put("to",arrayDest);
		}


		String post = JSONValue.toJSONString(objetGlobal);
		
		System.err.println(nomVariablePost+"="+post);
		try {
			post=URLEncoder.encode(post, urlEncoding);
			EditorBrowserOpenMail.openURL(url,nomVariablePost+"="+post,httpHeader, browserTitle, browserTooltip);
		} catch (PartInitException e1) {
			logger.error("",e1);
		} catch (UnsupportedEncodingException ex) {
			logger.error("",ex);
		}
	}
	
}

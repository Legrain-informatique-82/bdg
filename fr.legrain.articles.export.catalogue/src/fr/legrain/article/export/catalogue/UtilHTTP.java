package fr.legrain.article.export.catalogue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;

public class UtilHTTP {
	
	static Logger logger = Logger.getLogger(UtilHTTP.class.getName());
	
	static public void post(String url) {
		post(url,null);
	}

	static public void post(String url, String paramName, String paramValue) {
		Map<String,String> param = new HashMap<String, String>();
		param.put(paramName, paramValue);
		post(url,param);
	}
	
	/**
	 * Envoi d'un requete HTTP en post à l'url indiquée avec les paramètres présent dans la map
	 * @param url
	 * @param param - Map - clé => nom_du_parametre, valeur => valeur du paramètre
	 */
	static public void post(String url, Map<String,String> param) {
		try {
			// prepare post method  
			HttpPost post = new HttpPost(url);

			// add parameters to the post method  
			if(param!=null) {
				List <NameValuePair> parameters = new ArrayList <NameValuePair>();  
				for (String paramName : param.keySet()) {
					parameters.add(new BasicNameValuePair(paramName, param.get(paramName)));  
				}
				UrlEncodedFormEntity sendentity = new UrlEncodedFormEntity(parameters, HTTP.UTF_8);  
				post.setEntity(sendentity);   
			}

			// create the client and execute the post method  
			HttpClient client = new DefaultHttpClient();  
			HttpResponse response = client.execute(post);  

			//retrieve the output and display it in console
			logger.debug("URL appellée : "+url);
			logger.debug(response.getStatusLine());

			client.getConnectionManager().shutdown();  
		} catch (Exception e) {
			logger.error("", e);
		}
	}

}

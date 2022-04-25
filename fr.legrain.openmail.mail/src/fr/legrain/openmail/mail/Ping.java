package fr.legrain.openmail.mail;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

public class Ping {
	
	static Logger logger = Logger.getLogger(Ping.class.getName());
	
	static String urlDefaut = "http://www.google.com";

	public static boolean ping(String adresse) {
		try {
			URL url = new URL(adresse);
			HttpURLConnection urlConnect = (HttpURLConnection)url.openConnection();
			Object objData = urlConnect.getContent();
		} catch (UnknownHostException e) {
			logger.error("",e);
			return false;
		}
		catch (IOException e) {
			logger.error("",e);
			return false;
		}
		return true;
	}
	
	public static boolean isInternetReachable() {
		return ping(urlDefaut);
	}
}

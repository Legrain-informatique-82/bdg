/**
 * Outils.java
 */
package fr.legrain.statistiques;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.DateTime;

import fr.legrain.gestCom.librairiesEcran.swt.LibDateTime;

/**
 * Outils utile aux statistiques
 * @author nicolas²
 *
 */
public class Outils {
	
	static Logger logger = Logger.getLogger(Outils.class.getName());
	/**
	 * Méthode permettant l'extraction d'une date à partir d'un DateTime
	 * @param laSelection, le Datetime
	 * @return une Date sous forme y/m/d
	 */
	public static Date extractDate (DateTime laSelection){
		return LibDateTime.getDate(laSelection);
	}

	/**
	 * Méthode permettant l'initialisation d'un dateTime à partir d'une date
	 * @param DateTime à modifier
	 * @param Date sous forme y/m/d
	 * 
	 */
	public static void setDateTime (DateTime leDateTime,Date laDate){
		LibDateTime.setDate(leDateTime,laDate);
	}

	/**
	 * Méthode permettant de vérifier si la connexion à internet est établie
	 * @return boolean de confirmation
	 */
	public static boolean isInternetReachable()
	{
		try {
			// On utilise la connexion internet pour accéder au site de google
			// la meilleure des url à tester est donc celle de google
			URL url = new URL("http://www.google.com");

			// On ouvre une connexion
			HttpURLConnection urlConnect = (HttpURLConnection)url.openConnection();

			// On tente de récupérer les données
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
}

package fr.legrain.libLgrMail;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.apache.log4j.Logger;

/**
 * Mail de mise à jour </br>
 * Dans le corps du mail : </br>
 * <li>1ère ligne : nom du site de mise à jour</li>
 * <li>2ème ligne : adresse (url) du site de mise à jour</li>
 * @author nicolas
 */
public class MailUpdate {
	
	static Logger logger = Logger.getLogger(MailUpdate.class.getName());
	
	private Message mail = null;
	private URL url = null;
	private String nomSite = null;
	private String sujet = null;
	private String from = null;
	private Date date = null;

	public MailUpdate(Message mail) {
		setMail(mail);
	}

	public Message getMail() {
		return mail;
	}

	public void setMail(Message mail) {
		String CR = "\r\n";
		this.mail = mail;
		try {
			//TODO extraction de l'url et du nom du site en fonction de format de mail choisi
			String[] corpsMail = ((String) mail.getContent()).trim().split(CR);
			this.nomSite = corpsMail[0];
			this.url = new URL(corpsMail[1]);
			this.sujet = mail.getSubject();
			this.date = mail.getSentDate();
			this.from = mail.getFrom()[0].toString();
	
		} catch (MalformedURLException e) {
			logger.error(e);
		} catch (MessagingException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
	}

	public String getNomSite() {
		return nomSite;
	}

	public URL getUrl() {
		return url;
	}

	public Date getDate() {
		return date;
	}

	public String getFrom() {
		return from;
	}

	public String getSujet() {
		return sujet;
	}
	
}

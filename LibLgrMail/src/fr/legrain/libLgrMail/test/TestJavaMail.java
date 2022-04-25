package fr.legrain.libLgrMail.test;

import fr.legrain.libLgrMail.MailUtil;

public class TestJavaMail {
	public void testPOP() {
		MailUtil m = new MailUtil();
		String serveurPOP = "mail.logiciel-gestion.fr";
		String popUser = "client1";
		String popUserPassword = "xxxxxxxx";

		m.popMailReader(serveurPOP,popUser,popUserPassword,true);
	}
	
	public void testSMTP() {
		MailUtil m = new MailUtil();
		String serveurSMTP = "smtp.free.fr";
		String from = "xxxx@legrain.fr";
		String to = "xxxx@legrain.fr";
		String sujet = "sujet";
		String texte = "texte";
		
		try {
			m.smtpMailSender(serveurSMTP, from, to, sujet, texte);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

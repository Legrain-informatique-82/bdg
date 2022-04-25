package fr.legrain.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.io.CopyStreamEvent;
import org.apache.commons.net.io.CopyStreamListener;
import org.apache.commons.net.io.Util;

public class TestFTP {
	

	FTPClient ftp;
	String server = "lgrser.lgr";
	String user = "zhaolin";
	String pass = "lgr009";
	
	/* Parametre de base : host, login, password, mode (actif ou passif)
	 * Autres parametres : fichier/repertoire local, fichier/repertoire distant, repertoire de connexion, droit du fichier
	 * 
	 * Envoyer un seul fichier
	 * Envoyer une liste de fichier
	 * Envoyer le contenu d'un repertoire (recursivement)
	 * Envoyer un ou plusieurs objet File (fichier ou repertoire)
	 * Pouvoir creer un repertoire sur le serveur
	 * 
	 * Equivalent des fonctions d'envoi en telechargement
	 * 
	 * Utilisation avec ou sans interface
	 * 2 barres de progression possible : totale et une par element
	 * Pouvoir utiliser le systeme de job d'eclipse facilement
	 * Faire un essai avec une ProgressBar "classique" SWT
	 * (Pouvoir faire suivre les evenement de progression de la barre a d'autre element de l'interface)
	 */
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			TestFTP t =new TestFTP();
			t.envoiFTP();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void envoiFTP() {
		boolean error = false;
		ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(server);
			System.out.println("Connected to " + server + ".");
			System.out.print(ftp.getReplyString());

			// After connection attempt, you should check the reply code to verify
			// success.
			reply = ftp.getReplyCode();

			if(!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				System.err.println("FTP server refused connection.");
				System.exit(1);
			}
			ftp.login(user, pass);
			System.out.print(ftp.getReplyString());

			OutputStream os = ftp.storeFileStream("test.txt");
			System.out.print(ftp.getReplyString());

			File f = new File("C:/Documents and Settings/lee.LEE-94A756772D2/Bureau/l.txt");
			FileInputStream fs = new FileInputStream(f);
			
			Util.copyStream(fs, os, 512, f.length(), new CopyStreamListener(){

				public void bytesTransferred(CopyStreamEvent arg0) {
					// TODO Auto-generated method stub
				}

				public void bytesTransferred(long arg0, int arg1, long arg2) {
					System.out.println("totalBytesTransferred : "+arg0+" bytesTransferred : "+arg1+" streamSize : "+arg2);
				}
				
			});
			System.out.print(ftp.getReplyString());

			os.close();
			fs.close();

			// transfer files
			ftp.logout();
			System.out.print(ftp.getReplyString());
		} catch(IOException e) {
			error = true;
			e.printStackTrace();
		} finally {
			if(ftp.isConnected()) {
				try {
					ftp.disconnect();
					System.out.print(ftp.getReplyString());
				} catch(IOException ioe) {
					// do nothing
				}
			}
			System.exit(error ? 1 : 0);
		}

	}

}

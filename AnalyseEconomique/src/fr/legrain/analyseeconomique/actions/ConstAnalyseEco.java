package fr.legrain.analyseeconomique.actions;

public class ConstAnalyseEco {
	public static String C_FICHIER_XML_AE 	  = "analyseEco.xml";
	public static String C_CHEMIN_FTP_AE      = "/httpdocs/cogere/ftp";
	public static String C_VAR_CNX_SERVEUR_LOGIN    = "login_ae";
	public static String C_VAR_CNX_SERVEUR_PWD      = "password_ae";
	
	public static String[][] C_LISTE_AGENCE = new String[][] {
		{ "COGERE", "cogere" },
		{ "COGERE Langon", "cogereLangon" },
		//{ "COGERE Mont de Marsan", "cogereMontDeMarsan" }
	};  
	
	/**
	 * 
	 * @param chaine - chaine a chercher
	 * @param libelle - true => la chaine est un libelle, false => la chaine est une valeur
	 * @return
	 */
	public static int findPositionListeAgence(String chaine,boolean libelle) {
		boolean trouve = false;
		int i = 0;
		int recherche = 0;
		while (!trouve && i<C_LISTE_AGENCE.length) {
			if(libelle) recherche = 0; else recherche = 1;
			if(C_LISTE_AGENCE[i][recherche].equals(chaine)) {
				trouve = true;
			} else i++;
		}
		return trouve ? i : -1;
	}
	
	public static String findValueListeAgence(String libelle) {
		boolean trouve = false;
		int i = 0;
		while (!trouve && i<C_LISTE_AGENCE.length) {
			if(C_LISTE_AGENCE[i][0].equals(libelle)) {
				trouve = true;
			} else i++;
		}
		return trouve ? C_LISTE_AGENCE[i][1] : null;
	}
	
	public static String findLibelleListeAgence(String valeur) {
		boolean trouve = false;
		int i = 0;
		while (!trouve && i<C_LISTE_AGENCE.length) {
			if(C_LISTE_AGENCE[i][1].equals(valeur)) {
				trouve = true;
			} else i++;
		}
		return trouve ? C_LISTE_AGENCE[i][0] : null;
	}
}

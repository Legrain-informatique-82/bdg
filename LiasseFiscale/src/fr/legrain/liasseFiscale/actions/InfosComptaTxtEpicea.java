package fr.legrain.liasseFiscale.actions;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import fr.legrain.lib.data.LibChaine;

public class InfosComptaTxtEpicea extends InfosCompta {
	
	static Logger logger = Logger.getLogger(InfosComptaTxtEpicea.class.getName());
	private String fichierTxt;
	private static final String SEPARATEUR = ";";
	private String origineInfosDossier = "InfosDossier"; // si modif : a changer aussi dans l'analyse economique
	
	public InfosComptaTxtEpicea() {
		super();
	}
	
	public InfosComptaTxtEpicea(String fichierTxt) {
		super();
		this.fichierTxt = fichierTxt;
	}
	
	/**
	 * Format du fichier texte : 
	 * <li> separateur = ;</li>
	 * <li> ligne contentant les titres, puis ligne avec les valeurs, entre 2 series de titre
	 * les lignes de valeurs commencent par la même valeur, ex: <br>
	 * <b>titre1;titre2;titre3</b><br>
	 * valeur1;valeur2;valeur3<br>
	 * valeur1;valeur4;valeur5<br>
	 * <b>titre4;titre5;titre6</b><br>
	 * valeur15;valeur2;valeur3<br>
	 * valeur15;valeur5;valeur6<br>
	 * valeur15;valeur7;valeur8<br>
	 * </li>
	 * <li>ligne classique : origine;compte;debit;credit</li>
	 */
	public void readTxt() {
		BufferedReader br = null;
		try {
			String encoding = "ISO-8859-1";
			FileInputStream fis = new FileInputStream(fichierTxt);
			InputStreamReader isr = new InputStreamReader(fis, encoding);
			br = new BufferedReader(isr);
			
			//br = new BufferedReader(new FileReader(fichierTxt));
			
			String ligne = br.readLine();
    		String[] retour = null;
    		String origine = "changement d'origine"; //init avec une chaine qui ne doit pas se trouver dans le fichier
    		Compte c = null;
    		InfoComplement info = null;
    		while (ligne!=null){
    			
    			//si le dernier caractère est SEPARATEUR, la dernière valeur est nulle
    			if(ligne.endsWith(SEPARATEUR))
    				ligne+="0";
    			
    			retour = ligne.split(SEPARATEUR);
    			
    			if(origine.equalsIgnoreCase(origineInfosDossier)) {
    				info = readLigneInfosTxtEpicea(retour);
    				if(info!=null) {
    					this.getListeInfosDossier().add(info);
    				}
    			}

    			if(origine.equalsIgnoreCase(retour[0])) {
    				//le separateur n'a pas change
    				c = readLigneTxtEpicea(retour);
    				
    				if(c!=null) {
    					this.getListeCompte().add(c);
    				}    				
    			} else {
    				origine = retour[0];
    			}
    			ligne = br.readLine();
    		}
			br.close();
			
		} catch (Exception e) {
			logger.error("",e);
		} 
	}
	
	public InfoComplement readLigneInfosTxtEpicea(String[] ligne) {
		InfoComplement info = null;
		switch (ligne.length) {
		case 3:
			info = new InfoComplement(ligne[1],ligne[2],ligne[2]);
			break;
		default:
			logger.error("format de ligne incorrect : "+ligne);
			break;
		}
		return info;
	}
	
	public Compte readLigneTxtEpicea(String[] ligne) {
		Compte c = null;
		int[] positionZoneNum = null;
		
		switch (ligne.length) {
		case 3: //derniere colonne vide
			if(!origineInfosDossier.equalsIgnoreCase(ligne[0])) {
				c = new Compte(ligne[0],ligne[1],null,
					Double.parseDouble(ligne[2]),null);
			}
			break;
		case 4:
			positionZoneNum = new int[]{2,3};
			for (int i = 0; i < positionZoneNum.length; i++) {
				if(LibChaine.empty(ligne[positionZoneNum[i]]))
					ligne[positionZoneNum[i]] = "0";
			}
			c = new Compte(ligne[0],ligne[1],null,
					Double.parseDouble(ligne[2]),Double.parseDouble(ligne[3]));
			break;
			
		case 9:
			//Balance;1010;2529.66;0;0;0;2529.66;0;Capital individuel
			c = new Compte(ligne[0],ligne[1],ligne[8],
					Double.parseDouble(ligne[2]),Double.parseDouble(ligne[3]),0d,
					//0d,0d,0d,
					Double.parseDouble(ligne[4]),Double.parseDouble(ligne[5]),0d,
					Double.parseDouble(ligne[6]),Double.parseDouble(ligne[7]),0d);
			break;
		case 8:
			//Balance;1010;2529.66;0;0;0;2529.66;0
			//0:origine
			//1:compte
			//2:debit1
			//3:credit1
			//4:solde1
			//5:debit2
			//6:credit2
			//7:solde2
			//8:debit3
			//9:credit3
			//10:solde3
			//11:libelle
			//Remplacement des chaines vide dans les zones numériques (zones devant contenir le montant d'un compte)
			positionZoneNum = new int[]{2,3,4,5,6,7};
			for (int i = 0; i < positionZoneNum.length; i++) {
				if(LibChaine.empty(ligne[positionZoneNum[i]]))
					ligne[positionZoneNum[i]] = "0";
			}
			c = new Compte(ligne[0],ligne[1],"",
					//Report => debit1,credit1,solde1
					Double.parseDouble(ligne[2]),Double.parseDouble(ligne[3]),0d,
					//Mouvement => debit2,credit2,solde2
					Double.parseDouble(ligne[4]),Double.parseDouble(ligne[5]),0d,
					//Solde fin => debit3,credit3,solde3
					Double.parseDouble(ligne[6]),Double.parseDouble(ligne[7]),0d);
			
			break;
			
		case 12:
			//Bilan;Compte;Debit_Report;Credit_Report;Debit_base;Credit_base;debit_total;credit_total;Report_Mouv_debit;Report_Mouv_Credit;Mouv_Debit;Mouv_Credit
			//Bilan;1010;0;127900;0;0;0;127900;0;127900;0;0
			//Remplacement des chaines vide dans les zones numériques (zones devant contenir le montant d'un compte)
			positionZoneNum = new int[]{2,3,4,5,6,7,8,9,10,11};
			for (int i = 0; i < positionZoneNum.length; i++) {
				if(LibChaine.empty(ligne[positionZoneNum[i]]))
					ligne[positionZoneNum[i]] = "0";
			}
			c = new Compte(ligne[0],ligne[1],"",
					//Report => debit1,credit1,solde1
					Double.parseDouble(ligne[2]),Double.parseDouble(ligne[3]),0d,
					//Mouvement (solde) => debit2,credit2,solde2
					Double.parseDouble(ligne[4]),Double.parseDouble(ligne[5]),0d,
					//Solde fin => debit3,credit3,solde3
					Double.parseDouble(ligne[6]),Double.parseDouble(ligne[7]),0d,
					//Mouvement Report => debit4,credit4,solde4
					Double.parseDouble(ligne[8]),Double.parseDouble(ligne[9]),0d,
					//Mouvement => debit5,credit5,solde5
					Double.parseDouble(ligne[10]),Double.parseDouble(ligne[11]),0d);
			
			break;
			
		default:
			logger.error("format de ligne incorrect : "+ligne);
			break;
		}
		return c;
	}

	public String getFichierTxt() {
		return fichierTxt;
	}

	public void setFichierTxt(String fichierTxt) {
		this.fichierTxt = fichierTxt;
	}
}

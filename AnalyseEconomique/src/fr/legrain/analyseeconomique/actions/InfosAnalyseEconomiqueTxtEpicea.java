package fr.legrain.analyseeconomique.actions;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.log4j.Logger;

import fr.legrain.liasseFiscale.actions.Compte;
import fr.legrain.liasseFiscale.actions.CompteSimple;
import fr.legrain.liasseFiscale.actions.InfoComplement;
import fr.legrain.liasseFiscale.actions.InfosComptaTxtEpicea;
import fr.legrain.lib.data.LibChaine;

public class InfosAnalyseEconomiqueTxtEpicea {
	
	static Logger logger = Logger.getLogger(InfosAnalyseEconomiqueTxtEpicea.class.getName());
	private String fichierTxt;
	private static final String SEPARATEUR = ";";
	private static final String origineBilan = "Bilan";
	private static final String origineAnalytique = "Analytique";
	private static final String origineStocks = "Stocks";
	private static final String origineAcquisitions = "5A3";
	private static final String origineGrdLivreQte = "GLivreQte";
	private static final String origineInfosDossier = "InfosDossier"; // si modif : a changer aussi dans la liasse
	private DonneesAnalyseEco donneesAnalyseEco = new DonneesAnalyseEco();
	
	public InfosAnalyseEconomiqueTxtEpicea() {
		super();
	}
	
	public InfosAnalyseEconomiqueTxtEpicea(String fichierTxt) {
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
    		int[] positionZoneNum = null;
    		InfosComptaTxtEpicea infosTxt = new InfosComptaTxtEpicea();
    		while (ligne!=null){
    			
    			//si le dernier caractère est SEPARATEUR, la dernière valeur est nulle
    			if(ligne.endsWith(SEPARATEUR))
    				ligne+="0";
    			
    			retour = ligne.split(SEPARATEUR);
    			
//    			for (int i = 0; i < retour.length; i++) {
//					System.out.println(i+" - "+retour[i]);
//				}

    			if(origine.equalsIgnoreCase(retour[0])) {
    				
////    				Remplacement des chaines vide dans les zones numériques (zones devant contenir le montant d'un compte)
//					positionZoneNum = new int[]{2,3,4,5,6,7};
//					for (int i = 0; i < positionZoneNum.length; i++) {
//						if(LibChaine.empty(retour[positionZoneNum[i]]))
//							retour[positionZoneNum[i]] = "0";
//					}    				
    				
    				if(origine.equals(origineBilan)) {
    					donneesAnalyseEco.getListeInfosCompta().add(new InfosCompta(infosTxt.readLigneTxtEpicea(retour)));
    				} else if(origine.equals(origineAnalytique)) {
    					donneesAnalyseEco.getListeInfosAnalytique().add(new InfosAnalytique(
    							retour[0],retour[1],retour[2],retour[3],retour[4],retour[5],
    							retour[6],retour[7],retour[8],retour[9],retour[10],retour[11],retour[12],retour[13]
    							));
    				} else if(origine.equals(origineStocks)) {
    					donneesAnalyseEco.getListeInfosStocks().add(new InfosStocks(
    							retour[0],retour[1],retour[2],retour[3],retour[4],retour[5],
    							retour[6]));
    				} else if(origine.equals(origineAcquisitions)) {
    					positionZoneNum = new int[]{2,3};
    					for (int i = 0; i < positionZoneNum.length; i++) {
    						if(LibChaine.empty(retour[positionZoneNum[i]]))
    							retour[positionZoneNum[i]] = "0";
    					}
    					donneesAnalyseEco.getListeAcquisition().add(new Acquisition(new CompteSimple(
    							retour[0],retour[1],retour[1],
    							Double.parseDouble(retour[2]),
    							Double.parseDouble(retour[3]))));
    				} else if(origine.equals(origineGrdLivreQte)) {
    					donneesAnalyseEco.getListeQte().add(new InfosGrdLivreQte(
    							retour[0],retour[1],retour[2]));
    				} else if(origine.equals(origineInfosDossier)) {
    					donneesAnalyseEco.getListeInfosDossier().add(new InfoComplement(
    							retour[1],retour[2],null));
    				} else {
    					logger.error("format de ligne incorrect pour l'analyse economique: "+ligne);
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

	public String getFichierTxt() {
		return fichierTxt;
	}

	public void setFichierTxt(String fichierTxt) {
		this.fichierTxt = fichierTxt;
	}

	public DonneesAnalyseEco getDonneesAnalyseEco() {
		return donneesAnalyseEco;
	}

	public void setDonneesAnalyseEco(DonneesAnalyseEco donneesAnalyseEco) {
		this.donneesAnalyseEco = donneesAnalyseEco;
	}
}

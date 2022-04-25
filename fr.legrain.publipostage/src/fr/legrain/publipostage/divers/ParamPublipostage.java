package fr.legrain.publipostage.divers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.wizard.WizardPage;

public class ParamPublipostage {

	static Logger logger = Logger.getLogger(ParamPublipostage.class.getName());
	
	static public final String ENCODAGE_DONNEES_UTF_8 = "UTF-8";
	static public final String ENCODAGE_DONNEES_ISO_8859_1 = "ISO-8859-1";

	private String valueSeparteur = ";";
	private String baliseDebutDetail = "<debut_detail>";
	private String baliseFinDetail = "<fin_detail>";

	private boolean afficheDetail = true;
	private int positionMarqueurGroupeDetail = 0;

	private String cheminFichierDonnees = null;
	private String cheminFichierModelLettre = null;
	private String cheminFichierMotCle = null;

	private String cheminRepertoireFinal = null;
	private String cheminFichierFinal = null;
	
	//private String encodageDonnees = null;
	private String encodageDonnees = ENCODAGE_DONNEES_UTF_8;

	private LinkedList<String> listeMotCleModelLettre = new LinkedList<String>(); 
	private LinkedList<String> listeNomChampsDonnees = new LinkedList<String>();

	private HashMap<Integer, String> motCleLettreEtPostion = new HashMap<Integer, String>(0);
	private Map<String, LinkedList<String[]>> details = new LinkedHashMap<String, LinkedList<String[]>>(0); //<code_tiers, LinkedList<ligne_detail>>

	/* Stocker toute ligne fiche extraction */
	private LinkedList<String> donnees = null;
	
	private String[] listeValeurNonAffichableDefaut = new String[]{"null","NULL"};
	private List<String> listeValeurNonAffichable = new  ArrayList<String>();

	public ParamPublipostage() {
		this(null);
	}
	
	public ParamPublipostage(List<String> listeValeurNonAffichable) {
		if(listeValeurNonAffichable!=null) {
			this.listeValeurNonAffichable = listeValeurNonAffichable; 
		} else {
			for (int i = 0; i < listeValeurNonAffichableDefaut.length; i++) {
				this.listeValeurNonAffichable.add(listeValeurNonAffichableDefaut[i]);
			}
		}
	}
	
	public void intialiseDonnees() {
		listeNomChampsDonnees.clear();
		if(donnees!=null)
			donnees.clear();		
		String value = "";
		String codeTiers = "";
		try {
			FileUtils fileUtils = new FileUtils();
			File file = new File(cheminFichierDonnees);
			//donnees = new LinkedList<String>(fileUtils.readLines(file, encodageDonnees));
			donnees = new LinkedList<String>();
			
			LinkedList<String> line = new LinkedList<String>(fileUtils.readLines(file, encodageDonnees));
			
			Iterator<String> ite = line.iterator();
			String valeur = "";
			String stock = "";
			while (ite.hasNext()) {
				valeur = ite.next();
				if(!valeur.endsWith(";")) { //changement de ligne dans le fichier mais toujours à l'intérieur de la même ligne de valeur
					stock += valeur+System.getProperty("line.separator");
				} else {
					if(!stock.equals("")) {
						donnees.add(stock/*+System.getProperty("line.separator")*/+valeur);
						//System.err.println("sep : "+System.getProperty("line.separator")+" format "+String.format("%n"));
						stock = "";
					} else
						donnees.add(valeur);
				}
			}
			
			String listNameChamp = donnees.get(0);
			String[] arrayNameChamp = listNameChamp.split(valueSeparteur,-1);

			for (int i = 0; i < arrayNameChamp.length; i++) {
				value = arrayNameChamp[i];
				listeNomChampsDonnees.add(value);
			}

			if(afficheDetail) {
				for (int i = 1; i < donnees.size(); i++) {
					String listDetail = donnees.get(i);
					String[] arrayDetail = donnees.get(i).split(valueSeparteur,-1);
					codeTiers = arrayDetail[positionMarqueurGroupeDetail];

					if(details.containsKey(codeTiers)) {
						details.get(codeTiers).add(arrayDetail);
					} else {
						LinkedList<String[]> liste = new LinkedList<String[]>();
						liste.add(arrayDetail);
						details.put(codeTiers, liste);
					}
				}
			}
		} catch (IOException e) {
			logger.error("",e);
			e.printStackTrace();
		}
	}

	//	public String remplaceEspace(String valueRemplace){
	//		String value = valueRemplace;
	//		if(value.indexOf(" ") != -1){
	//			value = valueRemplace.replace(" ", "_");
	//		}
	//		return value;
	//	}

	/**
	 * 
	 * @param filePath ==> path file de properties
	 * @param key ==> cle de properties
	 * @param positionMotCleLettre ==> mot cle de Lettre positionne
	 * @return
	 */
	private boolean readValueMotCleLettre(String key,int positionMotCleLettre) {
		boolean isFound = true;
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream (new FileInputStream(cheminFichierMotCle));
			props.load(in);
			String value = props.getProperty(key);
			if(value != null){
				listeMotCleModelLettre.add(value);
				motCleLettreEtPostion.put(positionMotCleLettre,value);
			}
			return isFound;
		} catch (Exception e) {
			logger.error("",e);
			return false;
		}
	}

	/**
	 * verifier entre AttributeModelLettre.properties et le nombre de valeur dans le file d'extraction
	 * EX : nombre de valeurs >= mots de cle dans la mode de lettre 
	 * 		et plus , Entre valeurs d'extraction et mots de cle sont correspondants.
	 *
	 * @return
	 */
	public boolean verityFileExtractionAndModelLettre(){

		boolean flag = true;
		for (int i = 0; i < listeNomChampsDonnees.size(); i++) {
			String nomChamp = listeNomChampsDonnees.get(i);
			flag = readValueMotCleLettre(nomChamp,i);
		}

		return flag;
	}


	public String getCheminFichierDonnees() {
		return cheminFichierDonnees;
	}

	public void setCheminFichierDonnees(String cheminFichierDonnees) {
		this.cheminFichierDonnees = cheminFichierDonnees;
	}

	public String getCheminFichierModelLettre() {
		return cheminFichierModelLettre;
	}

	public void setCheminFichierModelLettre(String cheminFichierModelLettre) {
		this.cheminFichierModelLettre = cheminFichierModelLettre;
	}

	public String getCheminFichierMotCle() {
		return cheminFichierMotCle;
	}

	public void setCheminFichierMotCle(String cheminFichierMotCle) {
		this.cheminFichierMotCle = cheminFichierMotCle;
	}



	public HashMap<Integer, String> getMotCleLettreEtPostion() {
		return motCleLettreEtPostion;
	}

	public void setMotCleLettreEtPostion(
			HashMap<Integer, String> motCleLettreEtPostion) {
		this.motCleLettreEtPostion = motCleLettreEtPostion;
	}

	public LinkedList<String> getDonnees() {
		return donnees;
	}

	public void setDonnees(LinkedList<String> allLineFileExtraction) {
		this.donnees = allLineFileExtraction;
	}

	public String getCheminRepertoireFinal() {
		return cheminRepertoireFinal;
	}

	public void setCheminRepertoireFinal(String cheminFichierFinal) {
		this.cheminRepertoireFinal = cheminFichierFinal;
	}

	public LinkedList<String> getListeMotCleModelLettre() {
		return listeMotCleModelLettre;
	}

	public void setListeMotCleModelLettre(LinkedList<String> listeMotCleModelLettre) {
		this.listeMotCleModelLettre = listeMotCleModelLettre;
	}

	public LinkedList<String> getListeNomChampsDonnees() {
		return listeNomChampsDonnees;
	}

	public void setListeNomChampsDonnees(LinkedList<String> listeNomChampsDonnees) {
		this.listeNomChampsDonnees = listeNomChampsDonnees;
	}

	public String getValueSeparteur() {
		return valueSeparteur;
	}

	public void setValueSeparteur(String valueSeparteur) {
		this.valueSeparteur = valueSeparteur;
	}

	public String getCheminFichierFinal() {
		return cheminFichierFinal;
	}

	public void setCheminFichierFinal(String cheminFichierFinal) {
		this.cheminFichierFinal = cheminFichierFinal;
	}

	public boolean isAfficheDetail() {
		return afficheDetail;
	}

	public void setAfficheDetail(boolean afficheDetail) {
		this.afficheDetail = afficheDetail;
	}

	public Map<String, LinkedList<String[]>> getDetails() {
		return details;
	}

	public void setDetails(Map<String, LinkedList<String[]>> details) {
		this.details = details;
	}

	public int getPositionMarqueurGroupeDetail() {
		return positionMarqueurGroupeDetail;
	}

	public void setPositionMarqueurGroupeDetail(int positionMarqueurGroupeDetail) {
		this.positionMarqueurGroupeDetail = positionMarqueurGroupeDetail;
	}

	public String getBaliseDebutDetail() {
		return baliseDebutDetail;
	}

	public void setBaliseDebutDetail(String baliseDebutDetail) {
		this.baliseDebutDetail = baliseDebutDetail;
	}

	public String getBaliseFinDetail() {
		return baliseFinDetail;
	}

	public void setBaliseFinDetail(String baliseFinDetail) {
		this.baliseFinDetail = baliseFinDetail;
	}

	public List<String> getListeValeurNonAffichable() {
		return listeValeurNonAffichable;
	}

	public void setListeValeurNonAffichable(List<String> listeValeurNonAffichable) {
		this.listeValeurNonAffichable = listeValeurNonAffichable;
	}

	public String getEncodageDonnees() {
		return encodageDonnees;
	}

	public void setEncodageDonnees(String encodageDonnees) {
		this.encodageDonnees = encodageDonnees;
	}


}

/**
 * 
 */
package fr.legrain.recherchermulticrit;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;

import fr.legrain.recherchermulticrit.controllers.FormPageController;
import fr.legrain.tiers.dao.TaAdresse;
import fr.legrain.tiers.dao.TaCPaiement;
import fr.legrain.tiers.dao.TaEmail;
import fr.legrain.tiers.dao.TaEntreprise;
import fr.legrain.tiers.dao.TaTEntite;
import fr.legrain.tiers.dao.TaTTiers;
import fr.legrain.tiers.dao.TaTelephone;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaWeb;

/**
 * @author nicolas²
 *
 */
public class LigneTiers extends Ligne {

	private Map<String,String> listeCorrespondanceTiers=null;
	private Map<String,String> listeCorrespondanceTiersPourIsNull=null;
	private Map<String,String> listeTitreChampsTiers=null;
//	private Map<String,Object> listeObjetTiers=null;
	
//	private Map<String,String> listeCorrespondanceComparateurs=null;
	private Map<String,String> listeTitreChampsComparateurs=null;
	
	

	/**
	 * @param parent
	 * @param toolkit
	 * @param masterController 
	 */
	public LigneTiers(int numGroupe,Composite parent, FormToolkit toolkit, FormPageController masterController) {
		super(numGroupe,parent, toolkit,masterController);
		super.getType().setText("Tiers");
		initChamps();
	}

	private void initChamps() {
		// *****************************************
		// ** Initialisation du champ de critères **
		// *****************************************
		listeCorrespondanceTiers=new LinkedHashMap<String, String>();
		listeCorrespondanceTiersPourIsNull=new LinkedHashMap<String, String>();
		listeTitreChampsTiers=new LinkedHashMap<String, String>();
//		listeObjetTiers=new LinkedHashMap<String,Object>();

		listeTitreChampsTiers.put("Code Tiers","codeTiers");
		listeTitreChampsTiers.put("Compte","compte");
		listeTitreChampsTiers.put("Nom Entreprise","nomEntreprise");
		listeTitreChampsTiers.put("Civilité","codeTCivilite");
		listeTitreChampsTiers.put("Nom","nomTiers");
		listeTitreChampsTiers.put("Prénom","prenomTiers");
		listeTitreChampsTiers.put("Téléphone","numeroTelephone");
		listeTitreChampsTiers.put("Adresse Mail","adresseEmail");
		listeTitreChampsTiers.put("Adresse Web","adresseWeb");
		listeTitreChampsTiers.put("Code Postal","codepostalAdresse");
		listeTitreChampsTiers.put("Ville","villeAdresse");
		listeTitreChampsTiers.put("Pays","paysAdresse");
		listeTitreChampsTiers.put("Code Paiement","codeCPaiement");
		listeTitreChampsTiers.put("Code Entite","codeTEntite");
		listeTitreChampsTiers.put("Type tiers","codeTTiers");
		listeTitreChampsTiers.put("Actif","actifTiers");
		
		listeTitreChampsTiers.put("Code Commercial","commercial");
		listeTitreChampsTiers.put("Code Famille","codeFamille");
		listeTitreChampsTiers.put("date anniversaire","dateAnniv");
		
//		TaTiers tiers =new TaTiers();
//		TaAdresse adresse=new TaAdresse();
//		listeObjetTiers.put("codeTiers",tiers);
//		listeObjetTiers.put("compte",tiers);
//		listeObjetTiers.put("nomTiers",tiers);
//		listeObjetTiers.put("prenomTiers",tiers);
//		listeObjetTiers.put("numeroTelephone",new TaTelephone());
//		listeObjetTiers.put("codeCPaiement",new TaCPaiement());
//		listeObjetTiers.put("adresseEmail",new TaEmail());
//		listeObjetTiers.put("tadresseWeb",new TaWeb());
//		listeObjetTiers.put("codeTEntite",new TaTEntite());
//		listeObjetTiers.put("codepostalAdresse",adresse);
//		listeObjetTiers.put("villeAdresse",adresse);
//		listeObjetTiers.put("paysAdresse",adresse);
//		listeObjetTiers.put("nomEntreprise",new TaEntreprise());
//		listeObjetTiers.put("codeTTiers",new TaTTiers());
		
		listeCorrespondanceTiers.put("codeTiers","codeTiers");
		listeCorrespondanceTiers.put("compte","compte");
		listeCorrespondanceTiers.put("nomTiers","nomTiers");
		listeCorrespondanceTiers.put("prenomTiers","prenomTiers");
		listeCorrespondanceTiers.put("numeroTelephone","taTelephone.numeroTelephone");
		listeCorrespondanceTiers.put("codeCPaiement","taCPaiement.codeCPaiement");
		listeCorrespondanceTiers.put("adresseEmail","taEmail.adresseEmail");
		listeCorrespondanceTiers.put("adresseWeb","taWeb.adresseWeb");
		listeCorrespondanceTiers.put("codeTEntite","taTEntite.codeTEntite");
		listeCorrespondanceTiers.put("codepostalAdresse","taAdresse.codepostalAdresse");
		listeCorrespondanceTiers.put("villeAdresse","taAdresse.villeAdresse");
		listeCorrespondanceTiers.put("paysAdresse","taAdresse.paysAdresse");
		listeCorrespondanceTiers.put("nomEntreprise","taEntreprise.nomEntreprise");
		listeCorrespondanceTiers.put("codeTCivilite","taTCivilite.codeTCivilite");
		listeCorrespondanceTiers.put("codeTTiers","taTTiers.codeTTiers");
		listeCorrespondanceTiers.put("actifTiers","actifTiers");
		
		//listeCorrespondanceTiers.put("comm","taCommerciaux.codeTTiers");
		//listeCorrespondanceTiers.put("comm","taCommerciaux.codeTiers");
		listeCorrespondanceTiers.put("commercial","taCommercial.codeTiers");
		listeCorrespondanceTiers.put("codeFamille","taFamilleTiers.codeFamille");
		listeCorrespondanceTiers.put("dateAnniv","dateAnniv");
		
		listeCorrespondanceTiersPourIsNull.put("codeTiers","codeTiers");
		listeCorrespondanceTiersPourIsNull.put("compte","compte");
		listeCorrespondanceTiersPourIsNull.put("nomTiers","nomTiers");
		listeCorrespondanceTiersPourIsNull.put("prenomTiers","prenomTiers");
		listeCorrespondanceTiersPourIsNull.put("numeroTelephone","taTelephone");
		listeCorrespondanceTiersPourIsNull.put("codeCPaiement","taCPaiement");
		listeCorrespondanceTiersPourIsNull.put("adresseEmail","taEmail");
		listeCorrespondanceTiersPourIsNull.put("adresseWeb","taWeb");
		listeCorrespondanceTiersPourIsNull.put("codeTEntite","taTEntite");
		listeCorrespondanceTiersPourIsNull.put("codepostalAdresse","taAdresse.codepostalAdresse");
		listeCorrespondanceTiersPourIsNull.put("villeAdresse","taAdresse.villeAdresse");
		listeCorrespondanceTiersPourIsNull.put("paysAdresse","taAdresse.paysAdresse");
		listeCorrespondanceTiersPourIsNull.put("nomEntreprise","taEntreprise.nomEntreprise");
		listeCorrespondanceTiersPourIsNull.put("codeTCivilite","taTCivilite.codeTCivilite");
		listeCorrespondanceTiersPourIsNull.put("codeTTiers","taTTiers");
		listeCorrespondanceTiersPourIsNull.put("actifTiers","actifTiers");
		listeCorrespondanceTiersPourIsNull.put("commercial","taCommercial");
		listeCorrespondanceTiersPourIsNull.put("codeFamille","taFamilleTiers");
		listeCorrespondanceTiersPourIsNull.put("dateAnniv","dateAnniv");
		
		// -- Insertion des critères dans la combo de critères --
		getCritere().add("<Choisir>");
		for (String champs : listeTitreChampsTiers.keySet()) {
			getCritere().add(champs);
		}
		getCritere().setVisibleItemCount(getCritere().getItemCount());
		getCritere().select(0);
		
		// *********************************************
		// ** Initialisation du champ de comparateurs **
		// *********************************************
		listeTitreChampsComparateurs=new LinkedHashMap<String, String>();
		
		listeTitreChampsComparateurs.put("est égal à","=");
		listeTitreChampsComparateurs.put("n'est pas égal à","<>");
		listeTitreChampsComparateurs.put("est supérieur à",">");
		listeTitreChampsComparateurs.put("est inférieur à","<");
		listeTitreChampsComparateurs.put("est compris entre","BETWEEN");
		listeTitreChampsComparateurs.put("contient","LIKE");
		listeTitreChampsComparateurs.put("ne contient pas","NOT LIKE");
		listeTitreChampsComparateurs.put("commence par","LIKE");
		listeTitreChampsComparateurs.put("ne commence pas par","NOT LIKE");
		listeTitreChampsComparateurs.put("fini par","LIKE");
		listeTitreChampsComparateurs.put("ne fini pas par","NOT LIKE");
		listeTitreChampsComparateurs.put(BOOLEAN_LIBELLE_CRITERE,"=");
		listeTitreChampsComparateurs.put("est vide","IS NULL");
		
		//listeTitreChampsComparateurs.put("liste contient","IN");
		
		// -- Insertion des critères dans la combo de comparateurs --
		getComparaison().add("<Choisir>");
		for (String champs : listeTitreChampsComparateurs.keySet()) {
			getComparaison().add(champs);
		}
		getComparaison().setVisibleItemCount(getComparaison().getItemCount());
		getComparaison().select(0);
		
	}
	
	public String transformSQLTiers(String champ) {
		return transformSQLTiers(champ, false);
	}
	
	public String transformSQLTiers(String champ, boolean critereIsNull) {
		if(critereIsNull) {
			return listeCorrespondanceTiersPourIsNull.get(listeTitreChampsTiers.get(champ));
		} else {
			return listeCorrespondanceTiers.get(listeTitreChampsTiers.get(champ));
		}
	}
	
	public String transformSQLcomparateur(String champ) {
		return listeTitreChampsComparateurs.get(champ);
	}





}

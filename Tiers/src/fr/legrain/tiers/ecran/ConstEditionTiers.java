package fr.legrain.tiers.ecran;

import fr.legrain.tiers.dao.TaCPaiement;
import fr.legrain.tiers.dao.TaFamilleTiers;
import fr.legrain.tiers.dao.TaTAdr;
import fr.legrain.tiers.dao.TaTBanque;
import fr.legrain.tiers.dao.TaTCivilite;
import fr.legrain.tiers.dao.TaTEmail;
import fr.legrain.tiers.dao.TaTEntite;
import fr.legrain.tiers.dao.TaTLiens;
import fr.legrain.tiers.dao.TaTTarif;
import fr.legrain.tiers.dao.TaTTel;
import fr.legrain.tiers.dao.TaTTiers;
import fr.legrain.tiers.dao.TaTWeb;

public class ConstEditionTiers {

	public static final String SOUS_REPORT_ADRESSE = "/ADRESSE";
	public static final String SOUS_REPORT_BANQUE = "/BANQUE";
	public static final String SOUS_REPORT_COMMERCIAL = "/COMMERCIAL";
	public static final String SOUS_REPORT_COMPL = "/COMPL";
	public static final String SOUS_REPORT_CONDITION_PAY= "/CONDITION_PAY";
	public static final String SOUS_REPORT_EMAIL = "/EMAIL";
	public static final String SOUS_REPORT_ENTREPRISE = "/ENTREPRISE";
	public static final String SOUS_REPORT_WEB = "/WEB";
	public static final String SOUS_REPORT_TYPE_TIERS = "/TYPE_TIERS";
	public static final String SOUS_REPORT_TYPE_ENTITE = "/TYPE_ENTITE";
	public static final String SOUS_REPORT_TYPE_CIVILITE = "/TYPE_CIVILITE";
	public static final String SOUS_REPORT_TYPE_TEL = "/TYPE_TEL";
	public static final String SOUS_REPORT_TYPE_ADR = "/TYPE_ADR";
	public static final String SOUS_REPORT_TELEPHONE = "/TELEPHONE";
	public static final String SOUS_REPORT_TIERS = "/TIERS";
	public static final String SOUS_REPORT_FACTURE = "/FACTURE";
	public static final String ALL_FICHE_IMPRIMER = "/allFicheImprimer";
	public static final String FICHE_ADRESSE = "FicheAdresse";
	public static final String FICHE_TEL= "FicheTel";
	public static final String FICHE_EMAIL = "FicheEmail";
	public static final String FICHE_COMPL = "FicheCompl";
	public static final String FICHE_COND_PAIEMENT = "FicheCondPaiment";
	public static final String FICHE_WED = "FicheWeb";
	public static final String FICHE_COMMERCIAL = "FicheCommercial";
	public static final String FICHE_ALL_TIERS_EDITON = "/FicheAllTiers.rptdesign";
	public static final String FOLDER_ALL_TIERS_IMPRIMER = "allFicheImprimer";
	
	public static final String PARAM_REPORT_ID_TIER= "ParamIDTiers";
	/**
	 * par-dessous: les constants pour Tiers
	 */
	public static final String TITLE_EDITION_TIERS= "Edition Tiers";
	public static final String SOUS_TITLE_EDITION_TIERS= "Liste des Tiers";
	
	/**
	 * par-dessous: les constants pour Edition Type Tiers
	 */
	public static final String TITLE_EDITION_TYPE_TIERS= "Edition Type Tiers";
	public static final String SOUS_TITLE_EDITION_TYPE_TIERS= "Liste des types Tiers";
	public static final String TYPE_TIERS= "Type_Tiers";
	
	/**
	 * par-dessous: les constants pour Edition Type Entite
	 */
	public static final String TITLE_EDITION_TYPE_ENTITE= "Edition Type Entités";
	public static final String SOUS_TITLE_EDITION_TYPE_ENTITE= "Liste des Entités";
	public static final String TYPE_ENTITE= "Type_Entité";
	
	/**
	 * par-dessous: les constants pour Edition Type Adresse
	 */
	public static final String TITLE_EDITION_TYPE_ADRESSE= "Edition Type Adresse";
	public static final String SOUS_TITLE_EDITION_TYPE_ADRESSE= "Liste des types d'adresse";
	public static final String TYPE_ADRESSE= "Type_Adresse";
	/**
	 * par-dessous: les constants pour Edition Type Civilite
	 */
	public static final String TITLE_EDITION_TYPE_CIVILITE= "Edition Type Civilité";
	public static final String SOUS_TITLE_EDITION_TYPE_CIVILITE= "Liste des Civilités";
	public static final String TYPE_CIVILITE= "Type_Civilite";
	/**
	 * par-dessous: les constants pour Edition Type Téléphones
	 */
	public static final String TITLE_EDITION_TYPE_TELEPHONES= "Edition Type Téléphones";
	public static final String SOUS_TITLE_EDITION_TYPE_TELEPHONES= "Liste des Téléphones";
	public static final String TYPE_TELEPHONES= "Type_Telephones";
	
	/**
	 * par-dessous: les constants pour Edition Type Téléphones
	 */
	public static final String TITLE_EDITION_COND_PAIE= "Edition Condition Paiement";
	public static final String SOUS_TITLE_EDITION_COND_PAIE= "Liste des Condition Paiement";
	public static final String TYPE_COND_PAIE= "Type_Cond_Paie";
	
	/**
	 * par-dessous: les constants pour Edition Type Liens
	 */
	public static final String TITLE_EDITION_TYPE_LIENS= "Edition Type Liens";
	public static final String SOUS_TITLE_EDITION_TYPE_LIENS= "Liste des Liens";
	public static final String TYPE_LIENS= "Type_Liens";
	/**
	 * par-dessous: les constants pour Edition Type Téléphones
	 */
	public static final String TITLE_EDITION_TYPE_EMAILS= "Edition Type Emails";
	public static final String SOUS_TITLE_EDITION_TYPE_EMAILS= "Liste des Emails";
	public static final String TYPE_EMAILS= "Type_Emails";
	
	/**
	 * par-dessous: les constants pour Edition Type Web
	 */
	public static final String TITLE_EDITION_TYPE_WEBS= "Edition Type Webs";
	public static final String SOUS_TITLE_EDITION_TYPE_WEBS = "Liste des Webs";
	public static final String TYPE_WEBS= "Type_Telephones";
	
	/**
	 * par-dessous: les constants pour Edition Type Tarif
	 */
	public static final String TITLE_EDITION_TYPE_TARIFS= "Edition Type Tarifs";
	public static final String SOUS_TITLE_EDITION_TYPE_TARIFS = "Liste des Tarifs";
	public static final String TYPE_TARIFS= "Type_Tarifs";
	
	/**
	 * par-dessous: les constants pour Edition Type Banque
	 */
	public static final String TITLE_EDITION_TYPE_BANQUES= "Edition Type Banques";
	public static final String SOUS_TITLE_EDITION_TYPE_BANQUES= "Liste des Banques";
	public static final String TYPE_BANQUES= "Type_Banques";
	/**
	 * par-dessous: les constants pour Tiers de Entreprise
	 */
	public static final String TITLE_EDITION_ENTREPRISES= "Edition Entreprises";
	public static final String SOUS_TITLE_EDITION_ENTREPRISES= "Liste des Entreprises";
	/**
	 * par-dessous: les constants pour Tiers de Banques
	 */
	public static final String TITLE_EDITION_BANQUES= "Edition Banques";
	public static final String SOUS_TITLE_EDITION_BANQUES= "Liste des Banques";
	
	/**
	 * par-dessous: les constants pour Tiers de Adresse
	 */
	public static final String TITLE_EDITION_TIERS_ADRESSE= "Edition des adresses Tiers";
	public static final String SOUS_TITLE_EDITION_TIERS_ADRESSE= "Liste des adresses des tiers";
	public static final String TIERS_ADRESSE= "Tiers_adresse";
	/**
	 * par-dessous: les constants pour Tiers de Tel
	 */
	public static final String TITLE_EDITION_TIERS_TEL= "Edition des téléphones du Tiers";
	public static final String SOUS_TITLE_EDITION_TIERS_TEL= "Liste des numéros de téléphone";
	public static final String TIERS_TEL= "Tiers_tel";
	/**
	 * par-dessous: les constants pour Tiers de Banque
	 */
	public static final String TITLE_EDITION_TIERS_BANQUE= "Edition de Banque";
	public static final String SOUS_TITLE_EDITION_TIERS_BANQUE= "Liste des Banques";
	public static final String TIERS_BANQUE= "Tiers_Banque";
	/**
	 * par-dessous: les constants pour Tiers de Email
	 */
	public static final String TITLE_EDITION_TIERS_EMAIL= "Edition des EMails";
	public static final String SOUS_TITLE_EDITION_TIERS_EMAIL= "Liste des EMails";
	public static final String TIERS_EMAIL= "Tiers_email";
	
	/**
	 * par-dessous: les constants pour Tiers de Compl
	 */
	public static final String TITLE_EDITION_TIERS_COMPL= "Edition Tiers de Compléments d'information";
	public static final String SOUS_TITLE_EDITION_TIERS_COMPL= "Liste des complément d'information";
	public static final String TIERS_COMPL= "Tiers_compl";
	/**
	 * par-dessous: les constants pour Tiers de Condition paiement
	 */
	public static final String TITLE_EDITION_TIERS_COND_PAYMENT= "Edition Tiers de Condition de paiement";
	public static final String SOUS_TITLE_EDITION_TIERS_COND_PAYMENT= "Liste des condition paiement";
	public static final String TIERS_COND_PAYMENT= "Tiers_cond_payment";
	/**
	 * par-dessous: les constants pour Tiers de Web
	 */
	public static final String TITLE_EDITION_TIERS_WEB= "Edition des adresses web";
	public static final String SOUS_TITLE_EDITION_TIERS_WEB= "Liste des adresses web";
	public static final String TIERS_WED= "Tiers_web";
	
	public static final String TITLE_EDITION_TIERS_NOTE = "Edition des notes";
	public static final String SOUS_TITLE_EDITION_TIERS_NOTE = "Liste des notes";
	public static final String TIERS_NOTE = "Tiers notes";
	/**
	 * par-dessous: les constants pour Tiers de Liens
	 */
	public static final String TITLE_EDITION_TIERS_LIEN= "Edition des Liens";
	public static final String SOUS_TITLE_EDITION_TIERS_LIEN= "Liste des Liens";
	public static final String TIERS_LIEN= "Tiers_Lien";
	/**
	 * par-dessous: les constants pour Tiers de Condition paiement
	 */
	public static final String TITLE_EDITION_TIERS_COMERCIAUX= "Edition des comerciaux";
	public static final String SOUS_TITLE_EDITION_TIERS_COMERCIAUX= "Liste des comerciaux";
	public static final String TIERS_COMERCIAUX= "Tiers_comerciaux";
	
	
	
	public static final String TITLE_EDITION_T_PAIEMENT= "Edition Type Paiement";
	public static final String SOUS_TITLE_EDITION_T_PAIEMENT= "Liste des Types Paiements";
	
	
	
	public static final String TITLE_EDITION_FAMILLE_TIERS= "Edition Famille de tiers";
	public static final String SOUS_TITLE_EDITION_FAMILLE_TIERS= "Liste des Famille de tiers";
	
	public static String COMMENTAIRE_EDITION_DEFAUT = "Liste des Tiers";
	/**
	 * parametre of edition (TIERS)
	 */
	public static final String PARAM_ID_TIERS= "ParamIDTiers";
	
	/**
	 * message 
	 */
	public static final String MESSAGE_EDITION_PREFERENCE= "Le chemin d'edition n'est pas correct !! ";
	public static final String TITLE_MESSAGE_EDITION_PREFERENCE = "Erreur chemin edition";
	
	public static final String[] arrayTypeEntity = {TaTTel.class.getSimpleName(),TaTCivilite.class.getSimpleName(),
		TaTAdr.class.getSimpleName(),TaTEntite.class.getSimpleName(),TaTTiers.class.getSimpleName(),
		TaTBanque.class.getSimpleName(),TaTWeb.class.getSimpleName(),TaTTarif.class.getSimpleName(),
		TaTEmail.class.getSimpleName(),TaTLiens.class.getSimpleName(),TaCPaiement.class.getSimpleName(),
		TaFamilleTiers.class.getSimpleName()};
	
	
	
}

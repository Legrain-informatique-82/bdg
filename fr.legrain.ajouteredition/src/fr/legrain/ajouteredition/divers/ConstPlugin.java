package fr.legrain.ajouteredition.divers;

import java.util.LinkedHashMap;
import java.util.Map;


public class ConstPlugin {

	public static final String TITLE_SHELL ="Gestion édition personnelle"; 
	public static final String C_COMMAND_BT_PARCOURIR_ID = "fr.legrain.ajouterEdition.parcourir";
	public static final String C_COMMAND_BT_ANNULER_ID = "fr.legrain.ajouterEdition.btAnnuler";
	
	public static Map<String, String> mapTypeEditionAndEntity = new LinkedHashMap<String, String>(); 
	public static final String[] fileTypeEdition = new String[] {"*.rptdesign"};
	public static void fillMapTypeEditionAndEntity() {
		mapTypeEditionAndEntity.clear();
		
//		mapTypeEditionAndEntity.put("TaArticle", "Edition Article");
//		mapTypeEditionAndEntity.put("TaTiers", "Edition Tiers");
//		mapTypeEditionAndEntity.put("TaFacture", "Edition Facture");
//		mapTypeEditionAndEntity.put("TaApporteur", "Edition Apporteur");
//		mapTypeEditionAndEntity.put("TaBonliv", "Edition Bon de livraison");
//		mapTypeEditionAndEntity.put("TaBoncde", "Edition Bon de Commande");
//		mapTypeEditionAndEntity.put("TaAvoir", "Edition Avoir");
//		mapTypeEditionAndEntity.put("TaDevis", "Edition Devis");
//		mapTypeEditionAndEntity.put("TaProforma", "Edition Proforma");
		
		mapTypeEditionAndEntity.put("Edition article","TaArticle");
		mapTypeEditionAndEntity.put("Edition tiers","TaTiers");
		mapTypeEditionAndEntity.put("Edition facture","TaFacture");
		mapTypeEditionAndEntity.put("Edition apporteur","TaApporteur");
		mapTypeEditionAndEntity.put("Edition bon de livraison","TaBonliv");
		mapTypeEditionAndEntity.put("Edition bon de Commande","TaBoncde");
		mapTypeEditionAndEntity.put("Edition avoir","TaAvoir");
		mapTypeEditionAndEntity.put("Edition devis","TaDevis");
		mapTypeEditionAndEntity.put("Edition proforma","TaProforma");
	}
	
	public static final String MESSAGE_EDITION_EXISTE ="L'édition à ajouter déja exsite ! ";
	public static final String MESSAGE_TITLE_OVERWRITE_EDITION ="Message Edition";
	public static final String MESSAGE_CONTENT_OVERWRITE_EDITION ="Voulez vous rempalcer l'ancienne édition";
	public static final String MESSAGE_EDITION_ERROR ="le fichier d'édition " +
			"ne correspond pas le type d'édition !! ";
	
	public static final String MESSAGE_TITLE_DELETE_EDITION ="Message édition supprimer";
	public static final String MESSAGE_CONTENT_DELETE_EDITION ="Voulez vous vraiment supprimer cette édition ?";
	
	public static final String MESSAGE_RESSUE_ADD_EDITION ="L'édition est bien ajoutée dans l'édition ";
	
	public static final String HTTP_URL ="http://www.legrain.fr/lgr/index.php?page=6";
}

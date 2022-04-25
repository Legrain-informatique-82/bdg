package fr.legrain.avisecheance.divers;

import fr.legrain.document.divers.ConstEditionDocument;

public class ConstEditionAvisEcheance extends ConstEditionDocument {
	
	public static String COMMENTAIRE_EDITION_DEFAUT = "Edition AvisEcheance";
	
	/**
	 * message edition
	 */
	public static final String MESSAGE_EDITION_PREFERENCE= "Le chemin d'edition n'est pas correct !! ";
	public static final String TITLE_MESSAGE_EDITION_PREFERENCE = "Erreur chemin edition";
	public static String SEPARATOR = "/";//File.separator;
	/**
	 * parametre of edition (AvisEcheance)
	 */
	public static final String PARAM_ID_AVIS_ECHEANCE= "paramID_DOC";
	public static final String PARAM_ID_DOC="paramID_DOC";
	public static final String PARAM_CHOIX_DEST="ParamChoix";
	
	public static final String PARAM_CAPITAL="capital";
	public static final String PARAM_APE="ape";
	public static final String PARAM_SIRET="siret";
	public static final String PARAM_RCS="rcs";
	public static final String PARAM_NOM_ENTREPRISE="nomEntreprise";
	
	public static final String TEXT_BUTTON_EDITION_DEFAUT = "Fiche de ";
	/** /report/defaut/TaAvisEcheance/Fiche_AvisEcheance.rptdesign **/
	public static String FICHE_FILE_REPORT_AVIS_ECHEANCE = SEPARATOR+"report"+SEPARATOR+"defaut"+SEPARATOR+"TaAvisEcheance"+SEPARATOR+"Fiche_AvisEcheance.rptdesign";

}

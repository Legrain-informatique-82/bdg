package fr.legrain.articles.ecran;

import fr.legrain.articles.dao.TaFamille;
import fr.legrain.articles.dao.TaFamilleUnite;
import fr.legrain.articles.dao.TaTva;
import fr.legrain.articles.dao.TaUnite;

public class ConstEditionArticle {

	public static final String SOUS_REPORT_FAMILLE = "/FAMILLE";
	public static final String SOUS_REPORT_ARTICLE = "/ARTICLES";
	public static final String SOUS_REPORT_PRIX = "/PRIX";
	public static final String SOUS_REPORT_TYPE_TVA= "/TYPE_TVA";
	public static final String SOUS_REPORT_TVA = "/TVA";
	public static final String SOUS_REPORT_UNITE = "/UNITE";
	
	/**
	 * par-dessous: les constants pour Article
	 */
	public static final String TITLE_EDITION_ARTICLE= "Edition Article";
	public static final String SOUS_TITLE_EDITION_ARTICLE= "Liste des Articles";
	/**
	 * par-dessous: les constants pour Article Famille
	 */
	public static final String TITLE_EDITION_ARTICLE_FAMILLE= "Edition Famille";
	public static final String SOUS_TITLE_EDITION_ARTICLE_FAMILLE= "Liste des Familles";
	public static final String ARTICLE_FAMILLE= "Article Famille";
	
	/**
	 * par-dessous: les constants pour Article Tva
	 */
	public static final String TITLE_EDITION_ARTICLE_TVA= "Edition Tva";
	public static final String SOUS_TITLE_EDITION_ARTICLE_TVA= "Liste des Codes Tva";
	public static final String ARTICLE_TVA= "Article Tva";
	
	public static final String TITLE_EDITION_ARTICLE_NOTE = "Edition Notes";
	public static final String SOUS_TITLE_EDITION_ARTICLE_NOTE = "Liste des Notes";
	public static final String ARTICLE_NOTE = "Article Note";
	
	/**
	 * par-dessous: les constants pour Article Unite
	 **/
	public static final String TITLE_EDITION_ARTICLE_UNITE= "Edition Unité";
	public static final String SOUS_TITLE_EDITION_ARTICLE_UNITE= "Liste des Unités";
	public static final String ARTICLE_UNITE= "Article Unite";
	
	public static final String FICHE_ARTICLE= "FicheArticle.rptdesign";
	public static final String PARAM_REPORT_ID_ARTICLE= "ParamIdArticle";
	
	public static final String FICHE_UNITE= "FicheUnite.rptdesign";
	public static final String PARAM_REPORT_ID_UNITE= "paramIdUnite";
	
	public static final String PARAM_REPORT_ID_TVA= "paramIdTva";
	
	public static String COMMENTAIRE_EDITION = "COMMENTAIRE_EDITION";
	public static String COMMENTAIRE_EDITION_DEFAUT = "Liste des Articles";
	
	/**
	 * folders edition 
	 */
	public static final String ECRAN_ARTICLE= "ecranArticle";
	public static final String ECRAN_FAMILLE= "ecranFamille";
	public static final String ECRAN_UNITE= "ecranUnite";
	
	/**
	 * message 
	 */
	public static final String MESSAGE_EDITION_PREFERENCE= "Le chemin d'edition n'est pas correct !! ";
	public static final String TITLE_MESSAGE_EDITION_PREFERENCE = "Erreur chemin edition";
	
	public static final String[] arrayTypeEntity = {TaUnite.class.getSimpleName(),TaTva.class.getSimpleName(),
												    TaFamille.class.getSimpleName(),TaFamilleUnite.class.getSimpleName()};
	
	public static final String COMMENT_EDITION_DEFAUT= "Liste des Fiches d'";
	
	
	
}

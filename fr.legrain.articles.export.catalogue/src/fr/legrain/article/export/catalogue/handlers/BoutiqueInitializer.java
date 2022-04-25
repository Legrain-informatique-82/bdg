package fr.legrain.article.export.catalogue.handlers;

import java.math.BigDecimal;

import javax.persistence.EntityTransaction;

import org.apache.log4j.Logger;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.articles.dao.TaUnite;
import fr.legrain.articles.dao.TaUniteDAO;

public class BoutiqueInitializer {
	
	static Logger logger = Logger.getLogger(BoutiqueInitializer.class.getName());
	
	public void initialize() {
		try {
		//Création d'un article sans TVA pour la gestion des frais de port
		String codeArticleFDP = "FDP"; // faire une constante ou une préférence (non modifiable)
		TaArticleDAO daoArticle = new TaArticleDAO();
		try {
			daoArticle.findByCode(codeArticleFDP);
		} catch(javax.persistence.NoResultException e) {
			TaArticle articleFDP = new TaArticle();

			articleFDP.setCodeArticle(codeArticleFDP);
			articleFDP.setStockMinArticle(new BigDecimal(0));
			articleFDP.setActif(1);
			articleFDP.setLibellecArticle("Frais de port");
			articleFDP.setLibellelArticle("Frais de port");

			EntityTransaction tx = daoArticle.getEntityManager().getTransaction();
			tx.begin();
			daoArticle.enregistrerMerge(articleFDP);
			daoArticle.commit(tx);
		}
		
		//Vérifier la présence des 2 Unités (UNITE et COLIS)
		String codeUniteUnite = "UNITE";
		String codeUniteColis = "COLIS";
		TaUniteDAO daoUnite = new TaUniteDAO();
		try {
			daoUnite.findByCode(codeUniteUnite);
		} catch(javax.persistence.NoResultException e) {
			TaUnite taUnite = new TaUnite();
			taUnite.setCodeUnite(codeUniteUnite);
			EntityTransaction tx = daoUnite.getEntityManager().getTransaction();
			tx.begin();
			daoUnite.enregistrerMerge(taUnite);
			daoUnite.commit(tx);
		}
		try {
			daoUnite.findByCode(codeUniteColis);
		} catch(javax.persistence.NoResultException e) {
			TaUnite taUnite = new TaUnite();
			taUnite.setCodeUnite(codeUniteColis);
			EntityTransaction tx = daoUnite.getEntityManager().getTransaction();
			tx.begin();
			daoUnite.enregistrerMerge(taUnite);
			daoUnite.commit(tx);
		}
		
		
		//Mise en place d'un paramètre pour automatisation de la création d'un 2ème prix
		
		//Paramètre/système de vérouillage de certaines préférences de la boutique : mode d'affichage du 2ème prix
		} catch(Exception e) {
			logger.error("",e);
		}
	}

}

package fr.legrain.recherchermulticrit.controllers;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;

public interface IFormPageArticlesController {
	
	public TaArticle getMasterEntity();
	
	public void setMasterEntity(TaArticle masterEntity);
	
	public TaArticleDAO getMasterDAO();
	
	public void setMasterDAO(TaArticleDAO masterDAO);
	
}

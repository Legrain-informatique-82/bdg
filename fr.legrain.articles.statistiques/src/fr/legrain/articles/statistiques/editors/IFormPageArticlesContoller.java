package fr.legrain.articles.statistiques.editors;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;

public interface IFormPageArticlesContoller {
	
	public TaArticle getMasterEntity();
	
	public void setMasterEntity(TaArticle masterEntity);
	
	public TaArticleDAO getMasterDAO();
	
	public void setMasterDAO(TaArticleDAO masterDAO);
	
}

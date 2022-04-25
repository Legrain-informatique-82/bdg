package fr.legrain.articles.editor;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;

public interface IEditorArticlesExtension {
	
	public TaArticle getMasterEntity();
	
	public void setMasterEntity(TaArticle masterEntity);
	
	public TaArticleDAO getMasterDAO();
	
	public void setMasterDAO(TaArticleDAO masterDAO);
	
	/**
	 * Methode qui doit être appelée automatiquement lors de l'appel de la méthode <code>pageChange()</code> du MultipageEditor
	 * contenant c'est Editeur.
	 */
	public void activate();
	
}

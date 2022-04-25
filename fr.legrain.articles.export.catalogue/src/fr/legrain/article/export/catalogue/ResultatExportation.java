package fr.legrain.article.export.catalogue;

import java.util.ArrayList;
import java.util.List;

import fr.legrain.articles.dao.TaArticle;

public class ResultatExportation {

	private List<TaArticle> listeArticleEnvoyes = null;

	public List<TaArticle> getListeArticleEnvoyes() {
		return listeArticleEnvoyes;
	}

	public void setListeArticleEnvoyes(List<TaArticle> listeArticleEnvoyes) {
		this.listeArticleEnvoyes = listeArticleEnvoyes;
	}
	
}

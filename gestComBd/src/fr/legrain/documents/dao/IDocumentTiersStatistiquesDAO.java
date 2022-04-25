package fr.legrain.documents.dao;

import java.util.Date;
import java.util.List;

import fr.legrain.tiers.dao.TaTiers;

public interface IDocumentTiersStatistiquesDAO<T> {
	
	public List<T> findByCodeTiersAndDate(String codeTiers, Date debut, Date fin);
	
	public List<Object> findChiffreAffaireByCodeTiers(String codeTiers,Date debut, Date fin, int precision);
	
	public List<TaTiers> findTiersByCodeArticleAndDate(String codeArticle, Date debut, Date fin);
	
	public List<Object> findChiffreAffaireByCodeArticle(String codeArticle,Date debut, Date fin, int precision);

}

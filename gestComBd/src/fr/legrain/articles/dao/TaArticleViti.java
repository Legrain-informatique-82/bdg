package fr.legrain.articles.dao;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import fr.legrain.gestCom.Appli.LgrHibernateValidated;
import fr.legrain.gestioncapsules.dao.TaTitreTransport;

//@Entity
//@Table(name = "TA_ARTICLE_VITI")
public class TaArticleViti extends TaArticle implements java.io.Serializable {
	
	private TaTitreTransport titreTransport;
	
	public TaArticleViti() {}
		
//	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	@JoinColumn(name = "ID_TITRE_TRANSPORT")
//	@LgrHibernateValidated(champ = "ID_TITRE_TRANSPORT",table = "TA_TITRE_TRANSPORT",clazz = TaTitreTransport.class)
	public TaTitreTransport getTaTitreTransport(){
		return this.titreTransport;
	}

	public void setTaTitreTransport(TaTitreTransport titreTransport) {
		this.titreTransport = titreTransport;
	}

}

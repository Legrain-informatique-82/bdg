package fr.legrain.articles.dao;

// Generated Sep 1, 2008 3:06:27 PM by Hibernate Tools 3.2.0.CR1

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Query;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import fr.legrain.gestCom.Appli.LgrHibernateValidated;

/**
 * TaRefPrix generated by hbm2java
 */
@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "TA_REF_PRIX")
@SequenceGenerator(name = "GEN_REF_PRIX", sequenceName = "NUM_ID_REF_PRIX", allocationSize = 1)
public class TaRefPrix implements java.io.Serializable {

	private int idRefPrix;
	private String version;
	private TaPrix taPrix;
	private TaArticle taArticle;
	private String quiCreeRefPrix;
	private Date quandCreeRefPrix;
	private String quiModifRefPrix;
	private Date quandModifRefPrix;
	private String ipAcces;
	private Integer versionObj;

	public TaRefPrix() {
	}

	public TaRefPrix(int idRefPrix) {
		this.idRefPrix = idRefPrix;
	}

	public TaRefPrix(int idRefPrix, TaPrix taPrix, TaArticle taArticle,
			String quiCreeRefPrix, Date quandCreeRefPrix,
			String quiModifRefPrix, Date quandModifRefPrix, String ipAcces) {
		this.idRefPrix = idRefPrix;
		this.taPrix = taPrix;
		this.taArticle = taArticle;
		this.quiCreeRefPrix = quiCreeRefPrix;
		this.quandCreeRefPrix = quandCreeRefPrix;
		this.quiModifRefPrix = quiModifRefPrix;
		this.quandModifRefPrix = quandModifRefPrix;
		this.ipAcces = ipAcces;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_REF_PRIX")
	@Column(name = "ID_REF_PRIX", unique = true, nullable = false)
	@LgrHibernateValidated(champ = "ID_REF_PRIX",table = "TA_REF_PRIX",clazz = TaRefPrix.class)
	public int getIdRefPrix() {
		return this.idRefPrix;
	}

	public void setIdRefPrix(int idRefPrix) {
		this.idRefPrix = idRefPrix;
	}

	//@Version
	@Column(name = "VERSION", length = 20)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	@Version
	@Column(name = "VERSION_OBJ", precision = 15)
	public Integer getVersionObj() {
		return this.versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PRIX")
	@LgrHibernateValidated(champ = "ID_PRIX",table = "TA_REF_PRIX",clazz = TaRefPrix.class)
	public TaPrix getTaPrix() {
		return this.taPrix;
	}

	public void setTaPrix(TaPrix taPrix) {
		this.taPrix = taPrix;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ARTICLE")
	@LgrHibernateValidated(champ = "ID_ARTICLE",table = "TA_REF_PRIX",clazz = TaRefPrix.class)
	public TaArticle getTaArticle() {
		return this.taArticle;
	}

	public void setTaArticle(TaArticle taArticle) {
		this.taArticle = taArticle;
	}

	@Column(name = "QUI_CREE_REF_PRIX", length = 50)
	public String getQuiCreeRefPrix() {
		return this.quiCreeRefPrix;
	}

	public void setQuiCreeRefPrix(String quiCreeRefPrix) {
		this.quiCreeRefPrix = quiCreeRefPrix;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "QUAND_CREE_REF_PRIX", length = 19)
	public Date getQuandCreeRefPrix() {
		return this.quandCreeRefPrix;
	}

	public void setQuandCreeRefPrix(Date quandCreeRefPrix) {
		this.quandCreeRefPrix = quandCreeRefPrix;
	}

	@Column(name = "QUI_MODIF_REF_PRIX", length = 50)
	public String getQuiModifRefPrix() {
		return this.quiModifRefPrix;
	}

	public void setQuiModifRefPrix(String quiModifRefPrix) {
		this.quiModifRefPrix = quiModifRefPrix;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "QUAND_MODIF_REF_PRIX", length = 19)
	public Date getQuandModifRefPrix() {
		return this.quandModifRefPrix;
	}

	public void setQuandModifRefPrix(Date quandModifRefPrix) {
		this.quandModifRefPrix = quandModifRefPrix;
	}

	@Column(name = "IP_ACCES", length = 50)
	public String getIpAcces() {
		return this.ipAcces;
	}

	public void setIpAcces(String ipAcces) {
		this.ipAcces = ipAcces;
	}

//	@PrePersist
//	@PreUpdate
	public void beforeInsert(){
		TaRefPrixDAO dao = new TaRefPrixDAO();
		Query ejbQuery = dao.getEntityManager().createQuery("delete from TaRefPrix where idArticle =?");
		ejbQuery.setParameter(1, this.getTaArticle().getIdArticle());
		ejbQuery.executeUpdate();
	}
//	@PrePersist
//	@PreUpdate
	public void beforePost()throws Exception{
		TaArticleDAO dao = new TaArticleDAO();
		dao.findById(this.getTaArticle().getIdArticle());
	}
}

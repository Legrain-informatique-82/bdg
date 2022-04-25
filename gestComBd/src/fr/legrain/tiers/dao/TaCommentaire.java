package fr.legrain.tiers.dao;

// Generated Mar 24, 2009 4:44:04 PM by Hibernate Tools 3.2.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import fr.legrain.gestCom.Appli.LgrHibernateValidated;

/**
 * TaCommentaire generated by hbm2java
 */
@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "TA_COMMENTAIRE")
@SequenceGenerator(name = "GEN_COMMENTAIRE", sequenceName = "NUM_ID_COMMENTAIRE", allocationSize = 1)
public class TaCommentaire implements java.io.Serializable {

	private int idCommentaire;
	private String version;
	private String commentaire;
	private String quiCreeCommentaire;
	private Date quandCreeCommentaire;
	private String quiModifCommentaire;
	private Date quandModifCommentaire;
	private String ipAcces;
	private Integer versionObj;
	private Set<TaTiers> taTierses = new HashSet<TaTiers>(0);

	public TaCommentaire() {
	}

	public TaCommentaire(int idCommentaire) {
		this.idCommentaire = idCommentaire;
	}

	public TaCommentaire(int idCommentaire, String commentaire,
			String quiCreeCommentaire, Date quandCreeCommentaire,
			String quiModifCommentaire, Date quandModifCommentaire,
			String ipAcces, Integer versionObj, Set<TaTiers> taTierses) {
		this.idCommentaire = idCommentaire;
		this.commentaire = commentaire;
		this.quiCreeCommentaire = quiCreeCommentaire;
		this.quandCreeCommentaire = quandCreeCommentaire;
		this.quiModifCommentaire = quiModifCommentaire;
		this.quandModifCommentaire = quandModifCommentaire;
		this.ipAcces = ipAcces;
		this.versionObj = versionObj;
		this.taTierses = taTierses;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_COMMENTAIRE")
	@Column(name = "ID_COMMENTAIRE", unique = true, nullable = false)
	@LgrHibernateValidated(champ = "ID_COMMENTAIRE",table = "TA_COMMENTAIRE",clazz = TaCommentaire.class)
	public int getIdCommentaire() {
		return this.idCommentaire;
	}

	public void setIdCommentaire(int idCommentaire) {
		this.idCommentaire = idCommentaire;
	}

	@Column(name = "VERSION", length = 20)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name = "COMMENTAIRE", length = 2000)
	@LgrHibernateValidated(champ = "COMMENTAIRE",table = "TA_COMMENTAIRE",clazz = TaCommentaire.class)
	public String getCommentaire() {
		return this.commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	@Column(name = "QUI_CREE_COMMENTAIRE", length = 50)
	public String getQuiCreeCommentaire() {
		return this.quiCreeCommentaire;
	}

	public void setQuiCreeCommentaire(String quiCreeCommentaire) {
		this.quiCreeCommentaire = quiCreeCommentaire;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "QUAND_CREE_COMMENTAIRE", length = 19)
	public Date getQuandCreeCommentaire() {
		return this.quandCreeCommentaire;
	}

	public void setQuandCreeCommentaire(Date quandCreeCommentaire) {
		this.quandCreeCommentaire = quandCreeCommentaire;
	}

	@Column(name = "QUI_MODIF_COMMENTAIRE", length = 50)
	public String getQuiModifCommentaire() {
		return this.quiModifCommentaire;
	}

	public void setQuiModifCommentaire(String quiModifCommentaire) {
		this.quiModifCommentaire = quiModifCommentaire;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "QUAND_MODIF_COMMENTAIRE", length = 19)
	public Date getQuandModifCommentaire() {
		return this.quandModifCommentaire;
	}

	public void setQuandModifCommentaire(Date quandModifCommentaire) {
		this.quandModifCommentaire = quandModifCommentaire;
	}

	@Column(name = "IP_ACCES", length = 50)
	public String getIpAcces() {
		return this.ipAcces;
	}

	public void setIpAcces(String ipAcces) {
		this.ipAcces = ipAcces;
	}

	@Version
	@Column(name = "VERSION_OBJ")
	public Integer getVersionObj() {
		return this.versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "taCommentaire")
	public Set<TaTiers> getTaTierses() {
		return this.taTierses;
	}

	public void setTaTierses(Set<TaTiers> taTierses) {
		this.taTierses = taTierses;
	}

}

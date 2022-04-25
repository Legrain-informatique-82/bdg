package fr.legrain.articles.dao;

// Generated Sep 1, 2008 3:06:27 PM by Hibernate Tools 3.2.0.CR1

import java.math.BigDecimal;
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
 * TaTTva generated by hbm2java
 */
@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "TA_T_CONDITIONNEMENT")
@SequenceGenerator(name = "GEN_T_CONDITIONNEMENT", sequenceName = "NUM_ID_T_CONDITIONNEMENT", allocationSize = 1)
public class TaTConditionnement implements java.io.Serializable {

	private int id;
	private String version;
	private String codeType;
	private String libelle;
	private BigDecimal hauteur;
	private BigDecimal longueur;
	private BigDecimal largeur;
	private BigDecimal poids;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;
	private Integer versionObj;

	public TaTConditionnement() {
	}

	public TaTConditionnement(int id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_T_CONDITIONNEMENT")
	@Column(name = "ID", unique = true, nullable = false)
	@LgrHibernateValidated(champ = "ID",table = "TA_T_CONDITIONNEMENT",clazz = TaTConditionnement.class)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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

	@Column(name = "CODE_T_CONDITIONNEMENT", length = 1)
	@LgrHibernateValidated(champ = "CODE_T_CONDITIONNEMENT",table = "TA_T_CONDITIONNEMENT",clazz = TaTConditionnement.class)
	public String getCodeType() {
		return this.codeType;
	}

	public void setCodeType(String code) {
		this.codeType = code;
	}

	@Column(name = "LIBELLE_T_CONDITIONNEMENT")
	@LgrHibernateValidated(champ = "LIBELLE_T_CONDITIONNEMENT",table = "TA_T_CONDITIONNEMENT",clazz = TaTConditionnement.class)
	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	@Column(name = "HAUTEUR_T_CONDITIONNEMENT")
	@LgrHibernateValidated(champ = "HAUTEUR_T_CONDITIONNEMENT",table = "TA_T_CONDITIONNEMENT",clazz = TaTConditionnement.class)
	public BigDecimal getHauteur() {
		return hauteur;
	}

	public void setHauteur(BigDecimal hauteur) {
		this.hauteur = hauteur;
	}

	@Column(name = "LONGUEUR_T_CONDITIONNEMENT")
	@LgrHibernateValidated(champ = "LONGUEUR_T_CONDITIONNEMENT",table = "TA_T_CONDITIONNEMENT",clazz = TaTConditionnement.class)
	public BigDecimal getLongueur() {
		return longueur;
	}

	public void setLongueur(BigDecimal longueur) {
		this.longueur = longueur;
	}

	@Column(name = "LARGEUR_T_CONDITIONNEMENT")
	@LgrHibernateValidated(champ = "LARGEUR_T_CONDITIONNEMENT",table = "TA_T_CONDITIONNEMENT",clazz = TaTConditionnement.class)
	public BigDecimal getLargeur() {
		return largeur;
	}

	public void setLargeur(BigDecimal largeur) {
		this.largeur = largeur;
	}

	@Column(name = "POIDS")
	@LgrHibernateValidated(champ = "POIDS",table = "TA_T_CONDITIONNEMENT",clazz = TaTConditionnement.class)
	public BigDecimal getPoids() {
		return poids;
	}

	public void setPoids(BigDecimal poids) {
		this.poids = poids;
	}

	@Column(name = "QUI_CREE", length = 50)
	public String getQuiCree() {
		return this.quiCree;
	}

	public void setQuiCree(String quiCree) {
		this.quiCree = quiCree;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "QUAND_CREE", length = 19)
	public Date getQuandCree() {
		return this.quandCree;
	}

	public void setQuandCree(Date quandCree) {
		this.quandCree = quandCree;
	}

	@Column(name = "QUI_MODIF", length = 50)
	public String getQuiModif() {
		return this.quiModif;
	}

	public void setQuiModif(String quiModif) {
		this.quiModif = quiModif;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "QUAND_MODIF", length = 19)
	public Date getQuandModif() {
		return this.quandModif;
	}

	public void setQuandModif(Date quandModif) {
		this.quandModif = quandModif;
	}

	@Column(name = "IP_ACCES", length = 50)
	public String getIpAcces() {
		return this.ipAcces;
	}

	public void setIpAcces(String ipAcces) {
		this.ipAcces = ipAcces;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((hauteur == null) ? 0 : hauteur.hashCode());
		return result;
	}


}

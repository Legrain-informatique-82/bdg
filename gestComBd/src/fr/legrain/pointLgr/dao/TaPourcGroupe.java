package fr.legrain.pointLgr.dao;

// Generated Mar 24, 2009 4:44:04 PM by Hibernate Tools 3.2.0.CR1

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import fr.legrain.SupportAbon.dao.TaSupportAbon;
import fr.legrain.articles.dao.TaTAbonnement;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.gestCom.Appli.LgrHibernateValidated;
import fr.legrain.stocks.dao.TaStock;
import fr.legrain.tiers.dao.TaFamilleTiers;
import fr.legrain.tiers.dao.TaTiers;


/**
 * TaAdresse generated by hbm2java
 */
@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "TA_POURC_GROUPE")
@SequenceGenerator(name = "GEN_POURC_GROUPE", sequenceName = "NUM_ID_POURC_GROUPE", allocationSize = 1)
public class TaPourcGroupe implements java.io.Serializable {

	private int idPourcGroupe;
	private String version;
	private TaTAbonnement taTAbonnement;
	private TaFamilleTiers taFamilleTiers;
	private BigDecimal pourcentage;
	private Integer versionObj;

	public TaPourcGroupe() {
	}

	public TaPourcGroupe(int idTypeMouv) {
		this.idPourcGroupe = idTypeMouv;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_POURC_GROUPE")
	@Column(name = "ID_POURC_GROUPE", unique = true, nullable = false)
	@LgrHibernateValidated(champ = "ID_POURC_GROUPE",table = "TA_POURC_GROUPE",clazz = TaPourcGroupe.class)
	public int getIdPourcGroupe() {
		return this.idPourcGroupe;
	}

	public void setIdPourcGroupe(int idTypeMouv) {
		this.idPourcGroupe = idTypeMouv;
	}

	@Column(name = "VERSION", length = 20)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_T_ABONNEMENT")
	@LgrHibernateValidated(champ = "ID_T_ABONNEMENT",table = "TA_T_ABONNEMENT",clazz = TaTAbonnement.class)
	public TaTAbonnement getTaTAbonnement() {
		return taTAbonnement;
	}

	public void setTaTAbonnement(TaTAbonnement taTAbonnement) {
		this.taTAbonnement = taTAbonnement;
	}


	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "ID_FAMILLE_TIERS")
	@LgrHibernateValidated(champ = "ID_FAMILLE",table = "TA_FAMILLE_TIERS",clazz = TaFamilleTiers.class)
	public TaFamilleTiers getTaFamilleTiers() {
		return taFamilleTiers;
	}

	public void setTaFamilleTiers(TaFamilleTiers taFamilleTiers) {
		this.taFamilleTiers = taFamilleTiers;
	}

	@Column(name = "POURCENTAGE")
	@LgrHibernateValidated(champ = "POURCENTAGE",table = "TA_POURC_GROUPE",clazz = TaPourcGroupe.class)
	public BigDecimal getPourcentage() {
		return pourcentage;
	}

	public void setPourcentage(BigDecimal pourcentage) {
		this.pourcentage = pourcentage;
	}
	
	

	@Version
	@Column(name = "VERSION_OBJ")
	public Integer getVersionObj() {
		return this.versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}









}

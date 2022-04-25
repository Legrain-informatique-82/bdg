package fr.legrain.tiers.dao;

// Generated Mar 24, 2009 4:44:04 PM by Hibernate Tools 3.2.0.CR1

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import fr.legrain.gestCom.Appli.LgrHibernateValidated;

/**
 * TaLiens generated by hbm2java
 */
@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "TA_LIENS")
@SequenceGenerator(name = "GEN_LIENS", sequenceName = "NUM_ID_LIENS", allocationSize = 1)
public class TaLiens implements java.io.Serializable {

	private int idLiens;
	private String version;
	private TaTLiens taTLiens;
	private TaTiers taTiers;
	private String adresseLiens;
	private String quiCreeLiens;
	private Date quandCreeLiens;
	private String quiModifLiens;
	private Date quandModifLiens;
	private String ipAcces;
	private Integer versionObj;

	public TaLiens() {
	}

	public TaLiens(int idLiens) {
		this.idLiens = idLiens;
	}

	public TaLiens(int idLiens, TaTLiens taTLiens, TaTiers taTiers,
			String adresseLiens, String quiCreeLiens, Date quandCreeLiens,
			String quiModifLiens, Date quandModifLiens, String ipAcces,
			Integer versionObj) {
		this.idLiens = idLiens;
		this.taTLiens = taTLiens;
		this.taTiers = taTiers;
		this.adresseLiens = adresseLiens;
		this.quiCreeLiens = quiCreeLiens;
		this.quandCreeLiens = quandCreeLiens;
		this.quiModifLiens = quiModifLiens;
		this.quandModifLiens = quandModifLiens;
		this.ipAcces = ipAcces;
		this.versionObj = versionObj;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_LIENS")
	@Column(name = "ID_LIENS", unique = true, nullable = false)
	@LgrHibernateValidated(champ = "ID_LIENS",table = "TA_LIENS",clazz = TaLiens.class)
	public int getIdLiens() {
		return this.idLiens;
	}

	public void setIdLiens(int idLiens) {
		this.idLiens = idLiens;
	}

	@Column(name = "VERSION", length = 20)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "ID_T_LIENS")
	@LgrHibernateValidated(champ = "ID_T_LIENS",table = "TA_T_LIENS",clazz = TaTLiens.class)
	public TaTLiens getTaTLiens() {
		return this.taTLiens;
	}

	public void setTaTLiens(TaTLiens taTLiens) {
		this.taTLiens = taTLiens;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "ID_TIERS")
	@LgrHibernateValidated(champ = "ID_TIERS",table = "TA_TIERS",clazz = TaTiers.class)
	public TaTiers getTaTiers() {
		return this.taTiers;
	}

	public void setTaTiers(TaTiers taTiers) {
		this.taTiers = taTiers;
	}

	@Column(name = "ADRESSE_LIENS")
	@LgrHibernateValidated(champ = "ADRESSE_LIENS",table = "TA_LIENS",clazz = TaLiens.class)
	public String getAdresseLiens() {
		return this.adresseLiens;
	}

	public void setAdresseLiens(String adresseLiens) {
		this.adresseLiens = adresseLiens;
	}

	@Column(name = "QUI_CREE_LIENS", length = 50)
	public String getQuiCreeLiens() {
		return this.quiCreeLiens;
	}

	public void setQuiCreeLiens(String quiCreeLiens) {
		this.quiCreeLiens = quiCreeLiens;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "QUAND_CREE_LIENS", length = 19)
	public Date getQuandCreeLiens() {
		return this.quandCreeLiens;
	}

	public void setQuandCreeLiens(Date quandCreeLiens) {
		this.quandCreeLiens = quandCreeLiens;
	}

	@Column(name = "QUI_MODIF_LIENS", length = 50)
	public String getQuiModifLiens() {
		return this.quiModifLiens;
	}

	public void setQuiModifLiens(String quiModifLiens) {
		this.quiModifLiens = quiModifLiens;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "QUAND_MODIF_LIENS", length = 19)
	public Date getQuandModifLiens() {
		return this.quandModifLiens;
	}

	public void setQuandModifLiens(Date quandModifLiens) {
		this.quandModifLiens = quandModifLiens;
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

}

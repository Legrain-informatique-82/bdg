package fr.legrain.tiers.dao;

// Generated Mar 25, 2009 10:06:49 AM by Hibernate Tools 3.2.0.CR1

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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import fr.legrain.gestCom.Appli.LgrHibernateValidated;

/**
 * TaTBanque generated by hbm2java
 */
@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "TA_T_BANQUE")
@SequenceGenerator(name = "GEN_T_BANQUE", sequenceName = "NUM_ID_T_BANQUE", allocationSize = 1)
public class TaTBanque implements java.io.Serializable {

	private int idTBanque;
	private String version;
	private String codeTBanque;
	private String liblTBanque;
	private String quiCreeTBanque;
	private Date quandCreeTBanque;
	private String quiModifTBanque;
	private Date quandModifTBanque;
	private String ipAcces;
	private Integer versionObj;
	private Set<TaCompteBanque> taCompteBanques = new HashSet<TaCompteBanque>(0);

	public TaTBanque() {
	}

	public TaTBanque(int idTBanque) {
		this.idTBanque = idTBanque;
	}

	public TaTBanque(int idTBanque, String codeTBanque, String liblTBanque,
			String quiCreeTBanque, Date quandCreeTBanque,
			String quiModifTBanque, Date quandModifTBanque, String ipAcces,
			Integer versionObj, Set<TaCompteBanque> taCompteBanques) {
		this.idTBanque = idTBanque;
		this.codeTBanque = codeTBanque;
		this.liblTBanque = liblTBanque;
		this.quiCreeTBanque = quiCreeTBanque;
		this.quandCreeTBanque = quandCreeTBanque;
		this.quiModifTBanque = quiModifTBanque;
		this.quandModifTBanque = quandModifTBanque;
		this.ipAcces = ipAcces;
		this.versionObj = versionObj;
		this.taCompteBanques = taCompteBanques;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_T_BANQUE")
	@Column(name = "ID_T_BANQUE", unique = true, nullable = false)
	@LgrHibernateValidated(champ = "ID_T_BANQUE",table = "TA_T_BANQUE",clazz = TaTBanque.class)
	public int getIdTBanque() {
		return this.idTBanque;
	}

	public void setIdTBanque(int idTBanque) {
		this.idTBanque = idTBanque;
	}

	
	@Column(name = "VERSION", length = 20)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name = "CODE_T_BANQUE", length = 20)
	@LgrHibernateValidated(champ = "CODE_T_BANQUE",table = "TA_T_BANQUE",clazz = TaTBanque.class)
	public String getCodeTBanque() {
		return this.codeTBanque;
	}

	public void setCodeTBanque(String codeTBanque) {
		this.codeTBanque = codeTBanque;
	}

	@Column(name = "LIBL_T_BANQUE", length = 100)
	@LgrHibernateValidated(champ = "LIBL_T_BANQUE",table = "TA_T_BANQUE",clazz = TaTBanque.class)
	public String getLiblTBanque() {
		return this.liblTBanque;
	}

	public void setLiblTBanque(String liblTBanque) {
		this.liblTBanque = liblTBanque;
	}

	@Column(name = "QUI_CREE_T_BANQUE", length = 50)
	public String getQuiCreeTBanque() {
		return this.quiCreeTBanque;
	}

	public void setQuiCreeTBanque(String quiCreeTBanque) {
		this.quiCreeTBanque = quiCreeTBanque;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "QUAND_CREE_T_BANQUE", length = 19)
	public Date getQuandCreeTBanque() {
		return this.quandCreeTBanque;
	}

	public void setQuandCreeTBanque(Date quandCreeTBanque) {
		this.quandCreeTBanque = quandCreeTBanque;
	}

	@Column(name = "QUI_MODIF_T_BANQUE", length = 50)
	public String getQuiModifTBanque() {
		return this.quiModifTBanque;
	}

	public void setQuiModifTBanque(String quiModifTBanque) {
		this.quiModifTBanque = quiModifTBanque;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "QUAND_MODIF_T_BANQUE", length = 19)
	public Date getQuandModifTBanque() {
		return this.quandModifTBanque;
	}

	public void setQuandModifTBanque(Date quandModifTBanque) {
		this.quandModifTBanque = quandModifTBanque;
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

	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "taTBanque")
	public Set<TaCompteBanque> getTaCompteBanques() {
		return this.taCompteBanques;
	}

	public void setTaCompteBanques(Set<TaCompteBanque> taCompteBanques) {
		this.taCompteBanques = taCompteBanques;
	}


//	@PrePersist
//	@PreUpdate
	public void beforePost ()throws Exception{
		this.setCodeTBanque(codeTBanque.toUpperCase());
	}
}

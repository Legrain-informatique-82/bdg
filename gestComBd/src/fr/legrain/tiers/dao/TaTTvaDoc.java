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
 * TaTTiers generated by hbm2java
 */
@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "TA_T_TVA_DOC")
@SequenceGenerator(name = "GEN_T_TVA_DOC", sequenceName = "NUM_ID_T_TVA_DOC", allocationSize = 1)
public class TaTTvaDoc implements java.io.Serializable {

	private int idTTvaDoc;
	private String version;
	private String codeTTvaDoc;
	private String libelleTTvaDoc;
	private String journalTTvaDoc;
	private String libelleEdition;
	private String quiCreeTTvaDoc;
	private Date quandCreeTTvaDoc;
	private String quiModifTTvaDoc;
	private Date quandModifTTvaDoc;
	private String ipAcces;
	private Integer versionObj;
	private Set<TaTiers> taTierses = new HashSet<TaTiers>(0);

	public TaTTvaDoc() {
	}

	public TaTTvaDoc(int idTTvaDoc) {
		this.idTTvaDoc = idTTvaDoc;
	}

	public TaTTvaDoc(int idTTvaDoc, String codeTTvaDoc, String libelleTTvaDoc,
			String compteTTvaDoc, String quiCreeTTvaDoc, Date quandCreeTTvaDoc,
			String quiModifTTvaDoc, Date quandModifTTvaDoc, String ipAcces,
			Integer versionObj, Set<TaTiers> taTierses,String libelleEdition) {
		this.idTTvaDoc = idTTvaDoc;
		this.codeTTvaDoc = codeTTvaDoc;
		this.libelleTTvaDoc = libelleTTvaDoc;
		this.journalTTvaDoc = compteTTvaDoc;
		this.libelleEdition = libelleEdition;
		this.quiCreeTTvaDoc = quiCreeTTvaDoc;
		this.quandCreeTTvaDoc = quandCreeTTvaDoc;
		this.quiModifTTvaDoc = quiModifTTvaDoc;
		this.quandModifTTvaDoc = quandModifTTvaDoc;
		this.ipAcces = ipAcces;
		this.versionObj = versionObj;
		this.taTierses = taTierses;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_T_TVA_DOC")
	@Column(name = "ID_T_TVA_DOC", unique = true, nullable = false)
	@LgrHibernateValidated(champ = "ID_T_TVA_DOC",table = "TA_T_TVA_DOC",clazz = TaTTvaDoc.class)
	public int getIdTTvaDoc() {
		return this.idTTvaDoc;
	}

	public void setIdTTvaDoc(int idTTvaDoc) {
		this.idTTvaDoc = idTTvaDoc;
	}

	@Column(name = "VERSION", length = 20)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name = "CODE_T_TVA_DOC", length = 20)
	@LgrHibernateValidated(champ = "CODE_T_TVA_DOC",table = "TA_T_TVA_DOC",clazz = TaTTvaDoc.class)
	public String getCodeTTvaDoc() {
		return this.codeTTvaDoc;
	}

	public void setCodeTTvaDoc(String codeTTvaDoc) {
		this.codeTTvaDoc = codeTTvaDoc;
	}

	@Column(name = "LIBELLE_T_TVA_DOC", length = 100)
	@LgrHibernateValidated(champ = "LIBELLE_T_TVA_DOC",table = "TA_T_TVA_DOC",clazz = TaTTvaDoc.class)
	public String getLibelleTTvaDoc() {
		return this.libelleTTvaDoc;
	}

	public void setLibelleTTvaDoc(String libelleTTvaDoc) {
		this.libelleTTvaDoc = libelleTTvaDoc;
	}

	@Column(name = "JOURNAL_T_TVA_DOC", length = 8)
	@LgrHibernateValidated(champ = "JOURNAL_T_TVA_DOC",table = "TA_T_TVA_DOC",clazz = TaTTvaDoc.class)
	public String getJournalTTvaDoc() {
		return this.journalTTvaDoc;
	}

	public void setJournalTTvaDoc(String compteTTvaDoc) {
		this.journalTTvaDoc = compteTTvaDoc;
	}


	@Column(name = "LIBELLE_EDITION", length = 255)
	@LgrHibernateValidated(champ = "LIBELLE_EDITION",table = "TA_T_TVA_DOC",clazz = TaTTvaDoc.class)
	public String getLibelleEdition() {
		return libelleEdition;
	}

	public void setLibelleEdition(String libelleEdition) {
		this.libelleEdition = libelleEdition;
	}
	
	@Column(name = "QUI_CREE_T_TVA_DOC", length = 50)
	public String getQuiCreeTTvaDoc() {
		return this.quiCreeTTvaDoc;
	}

	public void setQuiCreeTTvaDoc(String quiCreeTTvaDoc) {
		this.quiCreeTTvaDoc = quiCreeTTvaDoc;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "QUAND_CREE_T_TVA_DOC", length = 19)
	public Date getQuandCreeTTvaDoc() {
		return this.quandCreeTTvaDoc;
	}

	public void setQuandCreeTTvaDoc(Date quandCreeTTvaDoc) {
		this.quandCreeTTvaDoc = quandCreeTTvaDoc;
	}

	@Column(name = "QUI_MODIF_T_TVA_DOC", length = 50)
	public String getQuiModifTTvaDoc() {
		return this.quiModifTTvaDoc;
	}

	public void setQuiModifTTvaDoc(String quiModifTTvaDoc) {
		this.quiModifTTvaDoc = quiModifTTvaDoc;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "QUAND_MODIF_T_TVA_DOC", length = 19)
	public Date getQuandModifTTvaDoc() {
		return this.quandModifTTvaDoc;
	}

	public void setQuandModifTTvaDoc(Date quandModifTTvaDoc) {
		this.quandModifTTvaDoc = quandModifTTvaDoc;
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

	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "taTTiers")
	public Set<TaTiers> getTaTierses() {
		return this.taTierses;
	}

	public void setTaTierses(Set<TaTiers> taTierses) {
		this.taTierses = taTierses;
	}

	
	
//	@PrePersist
//	@PreUpdate
	public void beforePost ()throws Exception{
		this.setCodeTTvaDoc(codeTTvaDoc.toUpperCase());
	}


}
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
 * TaTelephone generated by hbm2java
 */
@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "TA_TELEPHONE")
@SequenceGenerator(name = "GEN_TELEPHONE", sequenceName = "NUM_ID_TELEPHONE", allocationSize = 1)
@NamedQueries(value = { 
		@NamedQuery(name=TaTelephone.QN.FIND_BY_IMPORT, query="select a from TaTelephone a where a.origineImport = ? and a.idImport = ?")
		})
public class TaTelephone implements java.io.Serializable {
	
	public static class QN {
		public static final String FIND_BY_IMPORT = "TaTelephone.findByImport";
	}

	private int idTelephone;
	private String version;
	private TaTiers taTiers;
	private TaTTel taTTel;
	private String numeroTelephone;
	private String posteTelephone;
	private String contact;
	private Integer commCommercialTelephone;
	private Integer commAdministratifTelephone;
	private String quiCreeTelephone;
	private Date quandCreeTelephone;
	private String quiModifTelephone;
	private Date quandModifTelephone;
	private String idImport;
	private String origineImport;
	private String ipAcces;
	private Integer versionObj;
	private Set<TaTiers> taTierses = new HashSet<TaTiers>(0);

	public TaTelephone() {
	}

	public TaTelephone(int idTelephone) {
		this.idTelephone = idTelephone;
	}

	public TaTelephone(int idTelephone, TaTiers taTiers, TaTTel taTTel,
			String numeroTelephone, String posteTelephone, String contact,
			String quiCreeTelephone, Date quandCreeTelephone,
			String quiModifTelephone, Date quandModifTelephone, String ipAcces,
			Integer versionObj, Set<TaTiers> taTierses) {
		this.idTelephone = idTelephone;
		this.taTiers = taTiers;
		this.taTTel = taTTel;
		this.numeroTelephone = numeroTelephone;
		this.posteTelephone = posteTelephone;
		this.contact = contact;
		this.quiCreeTelephone = quiCreeTelephone;
		this.quandCreeTelephone = quandCreeTelephone;
		this.quiModifTelephone = quiModifTelephone;
		this.quandModifTelephone = quandModifTelephone;
		this.ipAcces = ipAcces;
		this.versionObj = versionObj;
		this.taTierses = taTierses;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_TELEPHONE")
	@Column(name = "ID_TELEPHONE", unique = true, nullable = false)
	@LgrHibernateValidated(champ = "ID_TELEPHONE",table = "TA_TELEPHONE",clazz = TaTelephone.class)
	public int getIdTelephone() {
		return this.idTelephone;
	}

	public void setIdTelephone(int idTelephone) {
		this.idTelephone = idTelephone;
	}

	
	@Column(name = "VERSION", length = 20)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
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

	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "ID_T_TEL")
	@LgrHibernateValidated(champ = "ID_T_TEL",table = "TA_T_TEL",clazz = TaTTel.class)
	public TaTTel getTaTTel() {
		return this.taTTel;
	}

	public void setTaTTel(TaTTel taTTel) {
		this.taTTel = taTTel;
	}

	@Column(name = "NUMERO_TELEPHONE", length = 20)
	@LgrHibernateValidated(champ = "NUMERO_TELEPHONE",table = "TA_TELEPHONE",clazz = TaTelephone.class)
	public String getNumeroTelephone() {
		return this.numeroTelephone;
	}

	public void setNumeroTelephone(String numeroTelephone) {
		this.numeroTelephone = numeroTelephone;
	}

	@Column(name = "POSTE_TELEPHONE", length = 20)
	@LgrHibernateValidated(champ = "POSTE_TELEPHONE",table = "TA_TELEPHONE",clazz = TaTelephone.class)
	public String getPosteTelephone() {
		return this.posteTelephone;
	}

	public void setPosteTelephone(String posteTelephone) {
		this.posteTelephone = posteTelephone;
	}

	@Column(name = "CONTACT", length = 100)
	@LgrHibernateValidated(champ = "CONTACT",table = "TA_TELEPHONE",clazz = TaTelephone.class)
	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	
	@Column(name = "COMM_COMMERCIAL")
	@LgrHibernateValidated(champ = "COMM_COMMERCIAL",table = "TA_TELEPHONE",clazz = TaTelephone.class)
	public Integer getCommCommercialTelephone() {
		return this.commCommercialTelephone;
	}

	public void setCommCommercialTelephone(Integer commCommercialTelephone) {
		this.commCommercialTelephone = commCommercialTelephone;
	}
	
	@Column(name = "COMM_ADMINISTRATIF")
	@LgrHibernateValidated(champ = "COMM_ADMINISTRATIF",table = "TA_TELEPHONE",clazz = TaTelephone.class)
	public Integer getCommAdministratifTelephone() {
		return this.commAdministratifTelephone;
	}

	public void setCommAdministratifTelephone(Integer commAdministratifTelephone) {
		this.commAdministratifTelephone = commAdministratifTelephone;
	}

	@Column(name = "QUI_CREE_TELEPHONE", length = 50)
	public String getQuiCreeTelephone() {
		return this.quiCreeTelephone;
	}

	public void setQuiCreeTelephone(String quiCreeTelephone) {
		this.quiCreeTelephone = quiCreeTelephone;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "QUAND_CREE_TELEPHONE", length = 19)
	public Date getQuandCreeTelephone() {
		return this.quandCreeTelephone;
	}

	public void setQuandCreeTelephone(Date quandCreeTelephone) {
		this.quandCreeTelephone = quandCreeTelephone;
	}

	@Column(name = "QUI_MODIF_TELEPHONE", length = 50)
	public String getQuiModifTelephone() {
		return this.quiModifTelephone;
	}

	public void setQuiModifTelephone(String quiModifTelephone) {
		this.quiModifTelephone = quiModifTelephone;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "QUAND_MODIF_TELEPHONE", length = 19)
	public Date getQuandModifTelephone() {
		return this.quandModifTelephone;
	}

	public void setQuandModifTelephone(Date quandModifTelephone) {
		this.quandModifTelephone = quandModifTelephone;
	}
	
	@Column(name = "ORIGINE_IMPORT", length = 100)
	@LgrHibernateValidated(champ = "ORIGINE_IMPORT",table = "TA_TELEPHONE",clazz = TaTelephone.class)
	public String getOrigineImport() {
		return this.origineImport;
	}

	public void setOrigineImport(String origineImport) {
		this.origineImport = origineImport;
	}
	
	@Column(name = "ID_IMPORT", length = 100)
	@LgrHibernateValidated(champ = "ID_IMPORT",table = "TA_TELEPHONE",clazz = TaTelephone.class)
	public String getIdImport() {
		return this.idImport;
	}

	public void setIdImport(String idImport) {
		this.idImport = idImport;
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

	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "taTelephone")
	public Set<TaTiers> getTaTierses() {
		return this.taTierses;
	}

	public void setTaTierses(Set<TaTiers> taTierses) {
		this.taTierses = taTierses;
	}
	
	@PrePersist
	@PreUpdate
	public void verif() {
		if(commCommercialTelephone==null) commCommercialTelephone = 0;
		if(commAdministratifTelephone==null) commAdministratifTelephone = 0;
	}

}

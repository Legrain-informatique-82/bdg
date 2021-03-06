package fr.legrain.documents.dao;

// Generated Mar 24, 2009 4:44:04 PM by Hibernate Tools 3.2.0.CR1

import java.math.BigDecimal;
import java.sql.Timestamp;
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
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OrderBy;

import fr.legrain.gestCom.Appli.LgrHibernateValidated;

/**
 * TaTRelance generated by hbm2java
 */
@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "TA_L_RELANCE")
@SequenceGenerator(name = "GEN_L_RELANCE", sequenceName = "NUM_ID_L_RELANCE", allocationSize = 1)
public class TaLRelance implements java.io.Serializable {

	private int idLRelance;
	private TaRelance taRelance;
	private String codeDocument;
	private Date dateEcheance;
	private String codeTiers;
	private String typeDocument;
	private BigDecimal netTTC=new BigDecimal(0);
	private BigDecimal resteARegler=new BigDecimal(0);
	private TaTRelance taTRelance;
	private Integer etat = 0;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;
	private Integer versionObj;
	private String version;
	private String nomTiers;
	
	@Transient
	private Boolean accepte=true;
	
//	@Transient
//	private BigDecimal totalTtc=new BigDecimal(0);
	
	
	public TaLRelance() {
	}

	public TaLRelance(int idLRelance) {
		this.idLRelance = idLRelance;
	}





	public TaLRelance(int idLRelance, TaRelance taRelance, String codeDocument,
			Date dateEcheance, String codeTiers, String typeDocument,
			BigDecimal netTTC, BigDecimal resteARegler, TaTRelance taTRelance,
			Integer etat, String quiCree, Date quandCree, String quiModif,
			Date quandModif, String ipAcces, Integer versionObj,
			String version, Boolean accepte) {
		super();
		this.idLRelance = idLRelance;
		this.taRelance = taRelance;
		this.codeDocument = codeDocument;
		this.dateEcheance = dateEcheance;
		this.codeTiers = codeTiers;
		this.typeDocument = typeDocument;
		this.netTTC = netTTC;
		this.resteARegler = resteARegler;
		this.taTRelance = taTRelance;
		this.etat = etat;
		this.quiCree = quiCree;
		this.quandCree = quandCree;
		this.quiModif = quiModif;
		this.quandModif = quandModif;
		this.ipAcces = ipAcces;
		this.versionObj = versionObj;
		this.version = version;
		this.accepte = accepte;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_L_RELANCE")
	@Column(name = "ID_L_RELANCE", unique = true, nullable = false)
	@LgrHibernateValidated(champ = "ID_L_RELANCE",table = "TA_L_RELANCE",clazz = TaLRelance.class)
	public int getIdLRelance() {
		return this.idLRelance;
	}

	public void setIdLRelance(int idLRelance) {
		this.idLRelance = idLRelance;
	}

	@Column(name = "VERSION", length = 20)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name = "CODE_DOCUMENT", length = 20)
	@LgrHibernateValidated(champ = "CODE_DOCUMENT",table = "TA_L_RELANCE",clazz = TaLRelance.class)
	public String getCodeDocument() {
		return this.codeDocument;
	}

	public void setCodeDocument(String codeRelance) {
		this.codeDocument = codeRelance;
	}



	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_ECHEANCE", length = 19)
	@LgrHibernateValidated(champ = "DATE_ECHEANCE",table = "TA_L_RELANCE",clazz = TaLRelance.class)
	public Date getDateEcheance() {
		return this.dateEcheance;
	}

	public void setDateEcheance(Date dateEcheance) {
		this.dateEcheance = dateEcheance;
	}
	
	@Column(name = "TYPE_DOCUMENT", length = 100)
	@LgrHibernateValidated(champ = "TYPE_DOCUMENT",table = "TA_L_RELANCE",clazz = TaLRelance.class)
	public String getTypeDocument() {
		return this.typeDocument;
	}

	public void setTypeDocument(String typeDocument) {
		this.typeDocument = typeDocument;
	}
	
	
	@Column(name = "CODE_TIERS", length = 20)
	@LgrHibernateValidated(champ = "CODE_TIERS",table = "TA_L_RELANCE",clazz = TaLRelance.class)
	public String getCodeTiers() {
		return this.codeTiers;
	}

	public void setCodeTiers(String codeTiers) {
		this.codeTiers = codeTiers;
	}
	
	@Column(name = "NOM_TIERS", length = 100)
	@LgrHibernateValidated(champ = "NOM_TIERS",table = "TA_L_RELANCE",clazz = TaLRelance.class)
	public String getNomTiers() {
		return nomTiers;
	}

	public void setNomTiers(String nomTiers) {
		this.nomTiers = nomTiers;
	}
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "ID_RELANCE")
	@LgrHibernateValidated(champ = "ID_RELANCE",table = "TA_RELANCE",clazz = TaRelance.class)
	public TaRelance getTaRelance() {
		return taRelance;
	}

	public void setTaRelance(TaRelance taRelance) {
		this.taRelance = taRelance;
	}

	@Column(name = "NET_TTC", precision = 15)
	@LgrHibernateValidated(champ = "NET_TTC",table = "TA_L_RELANCE",clazz = TaLRelance.class)
	public BigDecimal getNetTTC() {
		return netTTC;
	}

	public void setNetTTC(BigDecimal netTTC) {
		this.netTTC = netTTC;
	}

	@Column(name = "RESTE_A_REGLER", precision = 15)
	@LgrHibernateValidated(champ = "RESTE_A_REGLER",table = "TA_L_RELANCE",clazz = TaLRelance.class)
	public BigDecimal getResteARegler() {
		return resteARegler;
	}

	public void setResteARegler(BigDecimal resteARegler) {
		this.resteARegler = resteARegler;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "ID_T_RELANCE")
	@LgrHibernateValidated(champ = "ID_T_RELANCE",table = "TA_T_RELANCE",clazz = TaTRelance.class)
	public TaTRelance getTaTRelance() {
		return taTRelance;
	}

	public void setTaTRelance(TaTRelance taTRelance) {
		this.taTRelance = taTRelance;
	}


	
	@Column(name = "ETAT")
	@LgrHibernateValidated(champ = "ETAT",table = "TA_L_RELANCE",clazz = TaLRelance.class)
	public Integer getEtat() {
		return etat;
	}

	public void setEtat(Integer etat) {
		this.etat = etat;
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

	public void setQuandCree(Date quandCreeTRelance) {
		this.quandCree = quandCreeTRelance;
	}

	@Column(name = "QUI_MODIF", length = 50)
	public String getQuiModif() {
		return this.quiModif;
	}

	public void setQuiModif(String quiModifTRelance) {
		this.quiModif = quiModifTRelance;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "QUAND_MODIF", length = 19)
	public Date getQuandModif() {
		return this.quandModif;
	}

	public void setQuandModif(Date quandModifTRelance) {
		this.quandModif = quandModifTRelance;
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

	@Transient
	public Boolean getAccepte() {
		return accepte;
	}

	public void setAccepte(Boolean accepte) {
		this.accepte = accepte;
	}

//	@Transient
//	public BigDecimal getTotalTtc() {
//		return totalTtc;
//	}
//
//	public void setTotalTtc(BigDecimal totalTtc) {
//		this.totalTtc = totalTtc;
//	}

}

package fr.legrain.saisieCaisse.dao;

// Generated 30 juin 2009 10:40:13 by Hibernate Tools 3.2.4.GA

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import fr.legrain.documents.dao.TaTPaiement;
import fr.legrain.gestCom.Appli.LgrHibernateValidated;

/**
 * TaReportTPaiement generated by hbm2java
 */
@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "TA_REPORT_T_PAIEMENT")
public class TaReportTPaiement implements java.io.Serializable {

	private int idReportTPaiement;
	private String version;
	private TaTPaiement taTPaiement;
	private TaEtablissement taEtablissement;
	private BigDecimal montantReport;
	private Date dateReportTPaiement;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;
	private Integer versionObj;

	public TaReportTPaiement() {
	}

	public TaReportTPaiement(int idReportTPaiement) {
		this.idReportTPaiement = idReportTPaiement;
	}

	public TaReportTPaiement(int idReportTPaiement, TaTPaiement taTPaiement,
			TaEtablissement taEtablissement, BigDecimal montantReport,
			Date dateReportTPaiement, String quiCree, Date quandCree,
			String quiModif, Date quandModif, String ipAcces, Integer versionObj) {
		this.idReportTPaiement = idReportTPaiement;
		this.taTPaiement = taTPaiement;
		this.taEtablissement = taEtablissement;
		this.montantReport = montantReport;
		this.dateReportTPaiement = dateReportTPaiement;
		this.quiCree = quiCree;
		this.quandCree = quandCree;
		this.quiModif = quiModif;
		this.quandModif = quandModif;
		this.ipAcces = ipAcces;
		this.versionObj = versionObj;
	}

	@Id
	@Column(name = "ID_REPORT_T_PAIEMENT", unique = true, nullable = false)
	public int getIdReportTPaiement() {
		return this.idReportTPaiement;
	}

	public void setIdReportTPaiement(int idReportTPaiement) {
		this.idReportTPaiement = idReportTPaiement;
	}

	//@Version
	@Column(name = "VERSION", length = 20)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "ID_T_PAIEMENT")
	@LgrHibernateValidated(champ = "ID_T_PAIEMENT",table = "TA_T_PAIEMENT",clazz = TaTPaiement.class)
	public TaTPaiement getTaTPaiement() {
		return this.taTPaiement;
	}

	public void setTaTPaiement(TaTPaiement taTPaiement) {
		this.taTPaiement = taTPaiement;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ETABLISSEMENT")
	@LgrHibernateValidated(champ = "ID_ETABLISSEMENT",table = "TA_ETABLISSEMENT",clazz = TaEtablissement.class)
	public TaEtablissement getTaEtablissement() {
		return this.taEtablissement;
	}

	public void setTaEtablissement(TaEtablissement taEtablissement) {
		this.taEtablissement = taEtablissement;
	}

	@Column(name = "MONTANT_REPORT", precision = 15)
	public BigDecimal getMontantReport() {
		return this.montantReport;
	}

	public void setMontantReport(BigDecimal montantReport) {
		this.montantReport = montantReport;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_REPORT_T_PAIEMENT", length = 10)
	public Date getDateReportTPaiement() {
		return this.dateReportTPaiement;
	}

	public void setDateReportTPaiement(Date dateReportTPaiement) {
		this.dateReportTPaiement = dateReportTPaiement;
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
	
	@Version
	@Column(name = "VERSION_OBJ")
	public Integer getVersionObj() {
		return this.versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

}

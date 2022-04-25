package fr.legrain.gestioncapsules.dao;

import java.math.BigDecimal;
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

@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "TA_REPORT_STOCK_CAPSULES")
@SequenceGenerator(name = "GEN_REPORT_STOCK_CAPSULES", sequenceName = "NUM_ID_REPORT_STOCK_CAPSULES", allocationSize = 1)
public class TaReportStockCapsules implements java.io.Serializable {

	private int idReportStock;
	private String version;
	private TaTitreTransport taTitreTransport;
	private Date dateDebReportStock;
	private Date dateFinReportStock;
	private BigDecimal qte1ReportStock;
	private String unite1ReportStock;
	private BigDecimal qte2ReportStock;
	private String unite2ReportStock;
	private String quiCreeReportStock;
	private Date quandCreeReportStock;
	private String quiModifReportStock;
	private Date quandModifReportStock;
	private String ipAcces;
	private Integer versionObj;

	public TaReportStockCapsules() {
	}

	public TaReportStockCapsules(int idReportStock) {
		this.idReportStock = idReportStock;
	}

	public TaReportStockCapsules(int idReportStock, TaTitreTransport taTitreTransport,
			Date dateDebReportStock, Date dateFinReportStock,
			BigDecimal qteReportStock, String uniteReportStock,
			String quiCreeReportStock, Date quandCreeReportStock,
			String quiModifReportStock, Date quandModifReportStock,
			String ipAcces, Integer versionObj) {
		this.idReportStock = idReportStock;
		this.taTitreTransport = taTitreTransport;
		this.dateDebReportStock = dateDebReportStock;
		this.dateFinReportStock = dateFinReportStock;
		this.qte1ReportStock = qteReportStock;
		this.unite1ReportStock = uniteReportStock;
		this.quiCreeReportStock = quiCreeReportStock;
		this.quandCreeReportStock = quandCreeReportStock;
		this.quiModifReportStock = quiModifReportStock;
		this.quandModifReportStock = quandModifReportStock;
		this.ipAcces = ipAcces;
		this.versionObj = versionObj;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_REPORT_STOCK_CAPSULES")
	@Column(name = "ID_REPORT_STOCK_CAPSULES", unique = true, nullable = false)
	@LgrHibernateValidated(champ = "ID_REPORT_STOCK_CAPSULES",table = "TA_REPORT_STOCK_CAPSULES",clazz = TaReportStockCapsules.class)
	public int getIdReportStock() {
		return this.idReportStock;
	}

	public void setIdReportStock(int idReportStock) {
		this.idReportStock = idReportStock;
	}

//	@Version
	@Column(name = "VERSION", length = 20)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "ID_TITRE_TRANSPORT_REPORT_STOCK")
	@LgrHibernateValidated(champ = "ID_TITRE_TRANSPORT",table = "TA_TITRE_TRANSPORT",clazz = TaTitreTransport.class)
	public TaTitreTransport getTaTitreTransport() {
		return this.taTitreTransport;
	}

	public void setTaTitreTransport(TaTitreTransport taTitreTransport) {
		this.taTitreTransport = taTitreTransport;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_DEB_REPORT_STOCK", length = 19)
	@LgrHibernateValidated(champ = "DATE_DEB_REPORT_STOCK",table = "TA_REPORT_STOCK_CAPSULES",clazz = TaReportStockCapsules.class)
	public Date getDateDebReportStock() {
		return this.dateDebReportStock;
	}

	public void setDateDebReportStock(Date dateDebReportStock) {
		this.dateDebReportStock = dateDebReportStock;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_FIN_REPORT_STOCK", length = 19)
	@LgrHibernateValidated(champ = "DATE_FIN_REPORT_STOCK",table = "TA_REPORT_STOCK_CAPSULES",clazz = TaReportStockCapsules.class)
	public Date getDateFinReportStock() {
		return this.dateFinReportStock;
	}

	public void setDateFinReportStock(Date dateFinReportStock) {
		this.dateFinReportStock = dateFinReportStock;
	}

	@Column(name = "QTE1_REPORT_STOCK", precision = 15)
	@LgrHibernateValidated(champ = "QTE1_REPORT_STOCK",table = "TA_REPORT_STOCK_CAPSULES",clazz = TaReportStockCapsules.class)
	public BigDecimal getQte1ReportStock() {
		return this.qte1ReportStock;
	}

	public void setQte1ReportStock(BigDecimal qte1ReportStock) {
		this.qte1ReportStock = qte1ReportStock;
	}

	@Column(name = "UNITE1_REPORT_STOCK", length = 20)
	@LgrHibernateValidated(champ = "UNITE1_REPORT_STOCK",table = "TA_REPORT_STOCK_CAPSULES",clazz = TaReportStockCapsules.class)
	public String getUnite1ReportStock() {
		return this.unite1ReportStock;
	}

	public void setUnite1ReportStock(String unite1ReportStock) {
		this.unite1ReportStock = unite1ReportStock;
	}
	
	@Column(name = "QTE2_REPORT_STOCK", precision = 15)
	@LgrHibernateValidated(champ = "QTE2_REPORT_STOCK",table = "TA_REPORT_STOCK_CAPSULES",clazz = TaReportStockCapsules.class)
	public BigDecimal getQte2ReportStock() {
		return this.qte2ReportStock;
	}

	public void setQte2ReportStock(BigDecimal qte2ReportStock) {
		this.qte2ReportStock = qte2ReportStock;
	}

	@Column(name = "UNITE2_REPORT_STOCK", length = 20)
	@LgrHibernateValidated(champ = "UNITE2_REPORT_STOCK",table = "TA_REPORT_STOCK_CAPSULES",clazz = TaReportStockCapsules.class)
	public String getUnite2ReportStock() {
		return this.unite2ReportStock;
	}

	public void setUnite2ReportStock(String unite2ReportStock) {
		this.unite2ReportStock = unite2ReportStock;
	}

	@Column(name = "QUI_CREE", length = 50)
	public String getQuiCreeReportStock() {
		return this.quiCreeReportStock;
	}

	public void setQuiCreeReportStock(String quiCreeReportStock) {
		this.quiCreeReportStock = quiCreeReportStock;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "QUAND_CREE", length = 19)
	public Date getQuandCreeReportStock() {
		return this.quandCreeReportStock;
	}

	public void setQuandCreeReportStock(Date quandCreeReportStock) {
		this.quandCreeReportStock = quandCreeReportStock;
	}

	@Column(name = "QUI_MODIF", length = 50)
	public String getQuiModifReportStock() {
		return this.quiModifReportStock;
	}

	public void setQuiModifReportStock(String quiModifReportStock) {
		this.quiModifReportStock = quiModifReportStock;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "QUAND_MODIF", length = 19)
	public Date getQuandModifReportStock() {
		return this.quandModifReportStock;
	}

	public void setQuandModifReportStock(Date quandModifReportStock) {
		this.quandModifReportStock = quandModifReportStock;
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

package fr.legrain.gestioncapsules.dao;

// Generated 4 juin 2009 10:11:38 by Hibernate Tools 3.2.4.GA

import java.math.BigDecimal;
import java.math.MathContext;
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
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.gestCom.Appli.LgrHibernateValidated;
import fr.legrain.lib.data.LibCalcul;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.saisieCaisse.dao.TaDepot;

/**
 * TaStock generated by hbm2java
 */
@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "TA_STOCK_CAPSULES")
@SequenceGenerator(name = "GEN_STOCK_CAPSULES", sequenceName = "NUM_ID_STOCK_CAPSULES", allocationSize = 1)
public class TaStockCapsules implements java.io.Serializable {

	private int idStock;
	private String version;
	private TaTitreTransport taTitreTransport;
	private String mouvStock;
	private Date dateStock;
	private String libelleStock;
	private BigDecimal qte1Stock;
	private String un1Stock;
	private BigDecimal qte2Stock;
	private String un2Stock;
	private String quiCreeStock;
	private Date quandCreeStock;
	private String quiModifStock;
	private Date quandModifStock;
	private String ipAcces;
	private Integer versionObj;

	public TaStockCapsules() {
	}

	public TaStockCapsules(int idStock) {
		this.idStock = idStock;
	}

	public TaStockCapsules(int idStock, TaTitreTransport taTitreTransport, String mouvStock,
			Date dateStock, String libelleStock, BigDecimal qte1Stock,
			String un1Stock, BigDecimal qte2Stock, String un2Stock,
			String quiCreeStock, Date quandCreeStock, String quiModifStock,
			Date quandModifStock, String ipAcces, Integer versionObj) {
		this.idStock = idStock;
		this.taTitreTransport = taTitreTransport;
		this.mouvStock = mouvStock;
		this.dateStock = dateStock;
		this.libelleStock = libelleStock;
		this.qte1Stock = qte1Stock;
		this.un1Stock = un1Stock;
		this.qte2Stock = qte2Stock;
		this.un2Stock = un2Stock;
		this.quiCreeStock = quiCreeStock;
		this.quandCreeStock = quandCreeStock;
		this.quiModifStock = quiModifStock;
		this.quandModifStock = quandModifStock;
		this.ipAcces = ipAcces;
		this.versionObj = versionObj;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_STOCK_CAPSULES")
	@Column(name = "ID_STOCK_CAPSULES", unique = true, nullable = false)
	@LgrHibernateValidated(champ = "ID_STOCK_CAPSULES",table = "TA_STOCK_CAPSULES",clazz = TaStockCapsules.class)
	public int getIdStock() {
		return this.idStock;
	}

	public void setIdStock(int idStock) {
		this.idStock = idStock;
	}

//	@Version
	@Column(name = "VERSION", length = 20)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TITRE_TRANSPORT")
	@LgrHibernateValidated(champ = "ID_TITRE_TRANSPORT",table = "TA_TITRE_TRANSPORT",clazz = TaTitreTransport.class)
	public TaTitreTransport getTaTitreTransport() {
		return this.taTitreTransport;
	}

	public void setTaTitreTransport(TaTitreTransport taTitreTransport) {
		this.taTitreTransport = taTitreTransport;
	}
	
	@Column(name = "MOUV_STOCK", length = 1)
	@LgrHibernateValidated(champ = "MOUV_STOCK",table = "TA_STOCK_CAPSULES",clazz = TaStockCapsules.class)
	public String getMouvStock() {
		return this.mouvStock;
	}

	public void setMouvStock(String mouvStock) {
		this.mouvStock = mouvStock;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_STOCK", length = 19)
	@LgrHibernateValidated(champ = "DATE_STOCK",table = "TA_STOCK_CAPSULES",clazz = TaStockCapsules.class)
	public Date getDateStock() {
		return this.dateStock;
	}

	public void setDateStock(Date dateStock) {
		this.dateStock = dateStock;
	}

	@Column(name = "LIBELLE_STOCK")
	@LgrHibernateValidated(champ = "LIBELLE_STOCK",table = "TA_STOCK_CAPSULES",clazz = TaStockCapsules.class)
	public String getLibelleStock() {
		return this.libelleStock;
	}

	public void setLibelleStock(String libelleStock) {
		this.libelleStock = libelleStock;
	}

	@Column(name = "QTE1_STOCK", precision = 15)
	@LgrHibernateValidated(champ = "QTE1_STOCK",table = "TA_STOCK_CAPSULES",clazz = TaStockCapsules.class)
	public BigDecimal getQte1Stock() {
		return this.qte1Stock;
	}

	public void setQte1Stock(BigDecimal qte1Stock) {
		this.qte1Stock = qte1Stock;
	}

	@Column(name = "UN1_STOCK", length = 20)
	@LgrHibernateValidated(champ = "UN1_STOCK",table = "TA_STOCK_CAPSULES",clazz = TaStockCapsules.class)
	public String getUn1Stock() {
		return this.un1Stock;
	}

	public void setUn1Stock(String un1Stock) {
		this.un1Stock = un1Stock;
	}

	@Column(name = "QTE2_STOCK", precision = 15)
	@LgrHibernateValidated(champ = "QTE2_STOCK",table = "TA_STOCK_CAPSULES",clazz = TaStockCapsules.class)
	public BigDecimal getQte2Stock() {
		return this.qte2Stock;
	}

	public void setQte2Stock(BigDecimal qte2Stock) {
		this.qte2Stock = qte2Stock;
	}

	@Column(name = "UN2_STOCK", length = 20)
	@LgrHibernateValidated(champ = "UN2_STOCK",table = "TA_STOCK_CAPSULES",clazz = TaStockCapsules.class)
	public String getUn2Stock() {
		return this.un2Stock;
	}

	public void setUn2Stock(String un2Stock) {
		this.un2Stock = un2Stock;
	}

	@Column(name = "QUI_CREE", length = 50)
	public String getQuiCreeStock() {
		return this.quiCreeStock;
	}

	public void setQuiCreeStock(String quiCreeStock) {
		this.quiCreeStock = quiCreeStock;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "QUAND_CREE", length = 19)
	public Date getQuandCreeStock() {
		return this.quandCreeStock;
	}

	public void setQuandCreeStock(Date quandCreeStock) {
		this.quandCreeStock = quandCreeStock;
	}

	@Column(name = "QUI_MODIF", length = 50)
	public String getQuiModifStock() {
		return this.quiModifStock;
	}

	public void setQuiModifStock(String quiModifStock) {
		this.quiModifStock = quiModifStock;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "QUAND_MODIF", length = 19)
	public Date getQuandModifStock() {
		return this.quandModifStock;
	}

	public void setQuandModifStock(Date quandModifStock) {
		this.quandModifStock = quandModifStock;
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
	public void initQte2(TaArticle article){
		if(article.getTaRapportUnite()!=null && article.getTaRapportUnite().getTaUnite1()!=null && article.getTaRapportUnite().getTaUnite2()!=null
				&& article.getTaRapportUnite().getTaUnite1().getCodeUnite().equals(getUn1Stock())){
			int nbDecimal =article.getTaRapportUnite().getNbDecimal(); 
			setUn2Stock(article.getTaRapportUnite().getTaUnite2().getCodeUnite());
			BigDecimal Qte2 = new BigDecimal(0);
			if(getQte1Stock()!=null) {
				boolean sens = LibConversion.intToBoolean(article.getTaRapportUnite().getSens());
				if(sens) {
					if(getQte1Stock().compareTo(new BigDecimal(0))!=0)
						//Qte2 = LibCalcul.arrondi(getQte1Stock().divide(article.getTaRapportUnite().getRapport()),nbDecimal);
						Qte2 = getQte1Stock().divide(article.getTaRapportUnite().getRapport(),MathContext.DECIMAL128).setScale(nbDecimal,BigDecimal.ROUND_HALF_UP);
				} else {
					Qte2 = getQte1Stock().multiply(article.getTaRapportUnite().getRapport(),MathContext.DECIMAL128).setScale(nbDecimal,BigDecimal.ROUND_HALF_UP);
				}
			}

				setQte2Stock(Qte2);
		}
//				else
//		{
//			setUn2Stock("");
//			setQte2Stock(BigDecimal.ZERO);
//		}
	}

}

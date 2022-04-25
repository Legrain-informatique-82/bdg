package fr.legrain.articles.dao;

// Generated Sep 1, 2008 3:06:27 PM by Hibernate Tools 3.2.0.CR1

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.log4j.Logger;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import fr.legrain.gestCom.Appli.LgrHibernateValidated;
import fr.legrain.tiers.dao.TaFamilleTiers;
import fr.legrain.tiers.dao.TaTTarif;
import fr.legrain.tiers.dao.TaTiers;

/**
 * TaPrix generated by hbm2java
 */
@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "TA_PRIX")
@SequenceGenerator(name = "GEN_PRIX", sequenceName = "NUM_ID_PRIX", allocationSize = 1)
@NamedQueries(value = { 
		@NamedQuery(name=TaPrix.QN.FIND_BY_IMPORT, query="select a from TaPrix a where a.origineImport = ? and a.idImport = ?")
		})
public class TaPrix implements java.io.Serializable {
	
	public static class QN {
		public static final String FIND_BY_IMPORT = "TaPrix.findByImport";
	}

	@Transient
	static Logger logger = Logger.getLogger(TaPrix.class.getName());
	
	private int idPrix;
	private String version;
	private TaUnite taUnite;
	private TaArticle taArticle;
	private BigDecimal prixPrix;
	private BigDecimal prixttcPrix;
	private String idImport;
	private String origineImport;
	private String quiCreePrix;
	private Date quandCreePrix;
	private String quiModifPrix;
	private Date quandModifPrix;
	private String ipAcces;
	private Set<TaTiers> taTiers = new HashSet<TaTiers>(0);
	private Set<TaTTarif> taTTarifs = new HashSet<TaTTarif>(0);
	private Set<TaRefPrix> taRefPrixes = new HashSet<TaRefPrix>(0);
	private Boolean idRefPrix=true;
	private Integer versionObj;
	
	@Transient
	public Boolean getIdRefPrix() {
//		return taArticle.getTaPrix()==this;
		if(taArticle!=null && taArticle.getTaPrix()!=null)
		  return taArticle.getTaPrix().equals(this);
		else return false;
//		return idRefPrix;
	}

	public void setIdRefPrix(Boolean idRefPrix) {

		this.idRefPrix = idRefPrix;
		if(taArticle!=null) {
			if(idRefPrix) {
				taArticle.setTaPrix(this);
			} else {
				//selection d'un prix d'un prix de reference "par defaut"
				//le premier de la liste, qui n'est pas relie à un tiers ou un type tarif
				initPrixRefDefaut();
			}
		}
	}
	
	private void initPrixRefDefaut() {
		if(!taArticle.getTaPrixes().isEmpty()) {
			boolean trouve = false;
			TaPrix prixCourant = null;
			Iterator<TaPrix> ite = taArticle.getTaPrixes().iterator();
			while(!trouve && ite.hasNext()) {
				prixCourant = ite.next();
				if(prixCourant.getTaTiers().isEmpty() 
						&& prixCourant.getTaTTarif().isEmpty()) {
					trouve = true;
				}
			}
			if(trouve) {
				taArticle.setTaPrix(prixCourant);
			}
			
		}
	}

	public TaPrix() {
	}

	public TaPrix(int idPrix) {
		this.idPrix = idPrix;
	}

	public TaPrix(int idPrix, TaUnite taUnite, TaArticle taArticle,
			BigDecimal prixPrix, BigDecimal prixttcPrix, String quiCreePrix,
			Date quandCreePrix, String quiModifPrix, Date quandModifPrix,
			String ipAcces
			, Set<TaRefPrix> taRefPrixes
			) {
		this.idPrix = idPrix;
		this.taUnite = taUnite;
		this.taArticle = taArticle;
		this.prixPrix = prixPrix;
		this.prixttcPrix = prixttcPrix;
		this.quiCreePrix = quiCreePrix;
		this.quandCreePrix = quandCreePrix;
		this.quiModifPrix = quiModifPrix;
		this.quandModifPrix = quandModifPrix;
		this.ipAcces = ipAcces;
		//this.taRefPrixes = taRefPrixes;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_PRIX")
	@Column(name = "ID_PRIX", unique = true, nullable = false)
	@LgrHibernateValidated(champ = "ID_PRIX",table = "TA_PRIX",clazz = TaPrix.class)
	public int getIdPrix() {
		return this.idPrix;
	}

	public void setIdPrix(int idPrix) {
		this.idPrix = idPrix;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_UNITE")
	@LgrHibernateValidated(champ = "ID_UNITE",table = "TA_PRIX",clazz = TaPrix.class)
	public TaUnite getTaUnite() {
		return this.taUnite;
	}

	public void setTaUnite(TaUnite taUnite) {
		this.taUnite = taUnite;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ARTICLE")
	@LgrHibernateValidated(champ = "ID_ARTICLE",table = "TA_PRIX",clazz = TaPrix.class)
	public TaArticle getTaArticle() {
		return this.taArticle;
	}

	public void setTaArticle(TaArticle taArticle) {
		this.taArticle = taArticle;
	}

	@Column(name = "PRIX_PRIX", precision = 15)
	@LgrHibernateValidated(champ = "PRIX_PRIX",table = "TA_PRIX",clazz = TaPrix.class)
	public BigDecimal getPrixPrix() {
		return this.prixPrix;
	}

	public void setPrixPrix(BigDecimal prixPrix) {
		this.prixPrix = prixPrix;
	}

	@Column(name = "PRIXTTC_PRIX", precision = 15)
	@LgrHibernateValidated(champ = "PRIXTTC_PRIX",table = "TA_PRIX",clazz = TaPrix.class)
	public BigDecimal getPrixttcPrix() {
		return this.prixttcPrix;
	}

	public void setPrixttcPrix(BigDecimal prixttcPrix) {
		this.prixttcPrix = prixttcPrix;
	}
	
	@Column(name = "ORIGINE_IMPORT", length = 100)
	@LgrHibernateValidated(champ = "ORIGINE_IMPORT",table = "TA_PRIX",clazz = TaPrix.class)
	public String getOrigineImport() {
		return this.origineImport;
	}

	public void setOrigineImport(String origineImport) {
		this.origineImport = origineImport;
	}
	
	@Column(name = "ID_IMPORT", length = 100)
	@LgrHibernateValidated(champ = "ID_IMPORT",table = "TA_PRIX",clazz = TaPrix.class)
	public String getIdImport() {
		return this.idImport;
	}

	public void setIdImport(String idImport) {
		this.idImport = idImport;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "TA_R_PRIX",
			joinColumns = {@JoinColumn(name = "ID_PRIX")},inverseJoinColumns = {@JoinColumn(name = "ID_TIERS")})
	public Set<TaTiers> getTaTiers() {
		return taTiers;
	}

	public void setTaTiers(Set<TaTiers> taTiers) {
		this.taTiers = taTiers;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "TA_R_PRIX",
			joinColumns = {@JoinColumn(name = "ID_PRIX")},inverseJoinColumns = {@JoinColumn(name = "ID_T_TARIF")})
	public Set<TaTTarif> getTaTTarif() {
		return taTTarifs;
	}

	public void setTaTTarif(Set<TaTTarif> taTTarifs) {
		this.taTTarifs = taTTarifs;
	}

	@Column(name = "QUI_CREE_PRIX", length = 50)
	public String getQuiCreePrix() {
		return this.quiCreePrix;
	}

	public void setQuiCreePrix(String quiCreePrix) {
		this.quiCreePrix = quiCreePrix;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "QUAND_CREE_PRIX", length = 19)
	public Date getQuandCreePrix() {
		return this.quandCreePrix;
	}

	public void setQuandCreePrix(Date quandCreePrix) {
		this.quandCreePrix = quandCreePrix;
	}

	@Column(name = "QUI_MODIF_PRIX", length = 50)
	public String getQuiModifPrix() {
		return this.quiModifPrix;
	}

	public void setQuiModifPrix(String quiModifPrix) {
		this.quiModifPrix = quiModifPrix;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "QUAND_MODIF_PRIX", length = 19)
	public Date getQuandModifPrix() {
		return this.quandModifPrix;
	}

	public void setQuandModifPrix(Date quandModifPrix) {
		this.quandModifPrix = quandModifPrix;
	}

	@Column(name = "IP_ACCES", length = 50)
	public String getIpAcces() {
		return this.ipAcces;
	}

	public void setIpAcces(String ipAcces) {
		this.ipAcces = ipAcces;
	}

//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "taPrix")
//	public Set<TaRefPrix> getTaRefPrixes() {
//		return this.taRefPrixes;
//	}
//
//	public void setTaRefPrixes(Set<TaRefPrix> taRefPrixes) {
//		this.taRefPrixes = taRefPrixes;
//	}
//
	
	public void addTaTiers(TaTiers taTiers){
		if(!this.getTaTiers().contains(taTiers)) {
			this.getTaTiers().add(taTiers);
			if(getIdRefPrix()) {
				setIdRefPrix(false);
			}
		}
	}
	public void removeTaTiers(TaTiers taTiers){
		this.getTaTiers().remove(taTiers);
	}
	
	public void addTaTTarif(TaTTarif taTTarif){
		if(!this.getTaTTarif().contains(taTTarif)) {
			this.getTaTTarif().add(taTTarif);
			if(getIdRefPrix()) {
				setIdRefPrix(false);
			}
		}
	}
	public void removeTaTTarif(TaTTarif taTTarif){
		this.getTaTTarif().remove(taTTarif);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idPrix;
		result = prime * result
				+ ((idRefPrix == null) ? 0 : idRefPrix.hashCode());
		result = prime * result + ((ipAcces == null) ? 0 : ipAcces.hashCode());
		result = prime * result
				+ ((prixPrix == null) ? 0 : prixPrix.hashCode());
		result = prime * result
				+ ((prixttcPrix == null) ? 0 : prixttcPrix.hashCode());
		result = prime * result + ((taUnite == null) ? 0 : taUnite.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TaPrix other = (TaPrix) obj;
		if (idPrix != other.idPrix)
			return false;
		if (idRefPrix == null) {
			if (other.idRefPrix != null)
				return false;
		} else if (!idRefPrix.equals(other.idRefPrix))
			return false;
		if (ipAcces == null) {
			if (other.ipAcces != null)
				return false;
		} else if (!ipAcces.equals(other.ipAcces))
			return false;
		if (prixPrix == null) {
			if (other.prixPrix != null)
				return false;
		} else if (!prixPrix.equals(other.prixPrix))
			return false;
		if (prixttcPrix == null) {
			if (other.prixttcPrix != null)
				return false;
		} else if (!prixttcPrix.equals(other.prixttcPrix))
			return false;
		if (taUnite == null) {
			if (other.taUnite != null)
				return false;
		} else if (!taUnite.equals(other.taUnite))
			return false;
		return true;
	}

	
	@Override
	public Object clone() throws CloneNotSupportedException {
		TaPrix objet = new TaPrix();
		try {
			objet.setPrixPrix(prixPrix);
			objet.setPrixttcPrix(prixttcPrix);
			objet.setTaArticle(taArticle);
			objet.setTaUnite(taUnite);
		} catch (Exception e) {
			logger.error("", e);
		}
		// on renvoie le clone
		return objet;
	}
}

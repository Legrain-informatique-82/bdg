package fr.legrain.articles.dao;

// Generated Sep 1, 2008 3:06:27 PM by Hibernate Tools 3.2.0.CR1

import java.math.BigDecimal;
import java.math.MathContext;
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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Transient;

import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.apache.log4j.Logger;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import fr.legrain.documents.dao.TaFacture;
import fr.legrain.articles.champsupp.dao.TaRChampSuppArt;
import fr.legrain.documents.dao.TaRAcompte;
import fr.legrain.gestCom.Appli.LgrHibernateValidated;
import fr.legrain.gestioncapsules.dao.TaTitreTransport;
import fr.legrain.pointLgr.dao.TaArticlePointDAO;
import fr.legrain.tiers.dao.TaFamilleTiers;
import fr.legrain.tiers.dao.TaInfoJuridique;
import fr.legrain.tiers.dao.TaNoteTiers;
import fr.legrain.tiers.dao.TaTTarif;
import fr.legrain.tiers.dao.TaTiers;

/**
 * TaArticle generated by hbm2java
 */
@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
//@Inheritance(strategy=InheritanceType.JOINED)
@Table(name = "TA_ARTICLE")
@SequenceGenerator(name = "GEN_ARTICLE", sequenceName = "NUM_ID_ARTICLE", allocationSize = 1)
@NamedQueries(value = { 
		@NamedQuery(name=TaArticle.QN.FIND_BY_IMPORT, query="select a from TaArticle a where a.origineImport = ? and a.idImport = ?"),
		//@NamedQuery(name=TaArticle.QN.FIND_BY_NEW_OR_UPDATED_AFTER, query="select a from TaArticle a where a.quandCreeArticle >= ? or a.quandModifArticle >= ?"),
		
			@NamedQuery(name=TaArticle.QN.FIND_BY_NEW_OR_UPDATED_AFTER, 
		query="select distinct a from TaArticle a " +
				"left join a.taPrixes p " +
				"left join a.taLabelArticles lab " +
				"left join a.taCategorieArticles cat " +
				"left join a.taImageArticles img " +
				"left join a.taConditionnementsArticle cond " +
				"left join a.taRapportUnites ru " +
				"left join a.taCatalogueWeb web " +
				"where a.quandCreeArticle >= :dateLimite or a.quandModifArticle >= :dateLimite " +
				"or p.quandCreePrix >= :dateLimite or p.quandModifPrix >= :dateLimite " +

				"or lab.quandCreeLabelArticle >= :dateLimite or lab.quandModifLabelArticle >= :dateLimite " +
				"or cat.quandCreeCategorieArticle >= :dateLimite or cat.quandModifCategorieArticle >= :dateLimite " +
				"or img.quandCreeImageArticle >= :dateLimite or img.quandModifImageArticle >= :dateLimite " +
				"or cond.quandCreeUnite >= :dateLimite or cond.quandModifUnite >= :dateLimite " +
				"or ru.quandCree >= :dateLimite or ru.quandModif >= :dateLimite " +
				"or web.quandCreeCatalogueWeb >= :dateLimite or web.quandModifCatalogueWeb >= :dateLimite " +

				""),
				
//				query="select distinct a from TaArticle a " +
//						"left join a.taPrixes p " +
//						"left join a.taLabelArticles lab " +
//						"left join a.taCategorieArticles cat " +
//						"left join a.taImageArticles img " +
//						"left join a.taConditionnementsArticle cond " +
//						"left join a.taRapportUnites ru " +
//						"left join a.taCatalogueWeb web " +
//						"where a.quandCreeArticle >= ? or a.quandModifArticle >= ? " +
//						"or p.quandCreePrix >= ? or p.quandModifPrix >= ? " +
//
//						"or lab.quandCreeLabelArticle >= ? or lab.quandModifLabelArticle >= ? " +
//						"or cat.quandCreeCategorieArticle >= ? or cat.quandModifCategorieArticle >= ? " +
//						"or img.quandCreeImageArticle >= ? or img.quandModifImageArticle >= ? " +
//						"or cond.quandCreeUnite >= ? or cond.quandModifUnite >= ? " +
//						"or ru.quandCree >= ? or ru.quandModif >= ? " +
//						"or web.quandCreeCatalogueWeb >= ? or web.quandModifCatalogueWeb >= ? " +
//
//						""),
		
		
		
		
		@NamedQuery(name=TaArticle.QN.FIND_BY_ACTIF, query="select a from TaArticle a where a.actif = ? order by codeArticle"),
		@NamedQuery(name=TaArticle.QN.FIND_BY_ACTIF_LIGHT, query="select a.codeArticle,a.libellecArticle,fam.codeFamille,a.numcptArticle,p.prixPrix,p.prixttcPrix,un.codeUnite,tva.codeTva,ttva.codeTTva,a.diversArticle,a.commentaireArticle from TaArticle a left join a.taFamille fam left join a.taPrix p left join a.taTva tva left join a.taTTva ttva left join p.taUnite un  where a.actif = ? order by a.codeArticle"),
		@NamedQuery(name=TaArticle.QN.FIND_BY_ECRAN_LIGHT, query="select a.codeArticle,a.libellecArticle,a.numcptArticle,p.prixPrix,un.codeUnite,fam.codeFamille,tva.codeTva,tva.tauxTva,ttva.codeTTva from TaArticle a left join a.taFamille fam left join a.taPrix p left join a.taTva tva left join a.taTTva ttva left join a.taPrix.taUnite un   order by a.codeArticle")
		})
public class TaArticle implements java.io.Serializable {
	
	public static class QN {
		public static final String FIND_BY_IMPORT = "TaArticle.findByImport";
		public static final String FIND_BY_ACTIF = "TaArticle.findByActif";
		public static final String FIND_BY_ACTIF_LIGHT = "TaArticle.findByActifLight";
		public static final String FIND_BY_ECRAN_LIGHT = "TaArticle.findByEcranLight";
		public static final String FIND_BY_NEW_OR_UPDATED_AFTER = "TaArticle.findByNewOrUpdatedAfter";
	}
	
	@Transient
	static Logger logger = Logger.getLogger(TaArticle.class.getName());

	private int idArticle;
	private String version;
	private TaFamille taFamille;
	private TaTTva taTTva;
	private TaTva taTva;
	private TaPrix taPrix;
	private TaUnite taConditionnementArticle;
	private TaImageArticle taImageArticle;
	private TaCatalogueWeb taCatalogueWeb;
	private TaRapportUnite taRapportUnite;
	//private TaTitreTransport taTitreTransport;
	private TaRTaTitreTransport taRTaTitreTransport;
	private String codeArticle;
	private String libellecArticle;
	private String libellelArticle;
	private String numcptArticle;
	private String diversArticle;
	private String commentaireArticle;
	private BigDecimal stockMinArticle;
	private BigDecimal longueur;
	private BigDecimal largeur;
	private BigDecimal hauteur;
	private BigDecimal poids;
	private Integer actif;
	private String idImport;
	private String origineImport;
	private String quiCreeArticle;
	private Date quandCreeArticle;
	private String quiModifArticle;
	private Date quandModifArticle;
	private String ipAcces;
	//private Set<TaRefPrix> taRefPrixes = new HashSet<TaRefPrix>(0);
	private Set<TaPrix> taPrixes = new HashSet<TaPrix>(0);
	private Set<TaUnite> taConditionnementsArticle = new HashSet<TaUnite>(0);
	private Set<TaRapportUnite> taRapportUnites = new HashSet<TaRapportUnite>(0);
	private Integer versionObj;
	private Set<TaNoteArticle> taNotes = new HashSet<TaNoteArticle>(0);
	
//	private Set<TaRChampSuppArt> taRChampSuppArtes = new HashSet<TaRChampSuppArt>(0);
	
	private Set<TaImageArticle> taImageArticles = new HashSet<TaImageArticle>(0);
	
	private Set<TaCategorieArticle> taCategorieArticles = new HashSet<TaCategorieArticle>(0);
	
//	private Set<TaRTSupport> taRTSupports = new HashSet<TaRTSupport>(0);
	
	private Set<TaLabelArticle> taLabelArticles = new HashSet<TaLabelArticle>(0);
	
	private Set<TaTiers> taFournisseurs = new HashSet<TaTiers>(0);

	
	public TaArticle() {
	}

	public TaArticle(int idArticle) {
		this.idArticle = idArticle;
	}

	public TaArticle(int idArticle, TaFamille taFamille, TaTTva taTTva,
			TaTva taTva, String codeArticle, String libellecArticle,
			String libellelArticle, String numcptArticle, String diversArticle,
			String commentaireArticle, BigDecimal stockMinArticle,
			String quiCreeArticle, Date quandCreeArticle,
			String quiModifArticle, Date quandModifArticle, String ipAcces,
			Set<TaRefPrix> taRefPrixes, Set<TaPrix> taPrixes, Set<TaCategorieArticle> taCategorieArticles,
			Integer actif) {
		this.idArticle = idArticle;
		this.taFamille = taFamille;
		this.taTTva = taTTva;
		this.taTva = taTva;
		this.codeArticle = codeArticle;
		this.libellecArticle = libellecArticle;
		this.libellelArticle = libellelArticle;
		this.numcptArticle = numcptArticle;
		this.diversArticle = diversArticle;
		this.commentaireArticle = commentaireArticle;
		this.stockMinArticle = stockMinArticle;
		this.quiCreeArticle = quiCreeArticle;
		this.quandCreeArticle = quandCreeArticle;
		this.quiModifArticle = quiModifArticle;
		this.quandModifArticle = quandModifArticle;
		this.ipAcces = ipAcces;
		//this.taRefPrixes = taRefPrixes;
		this.taPrixes = taPrixes;
		this.taCategorieArticles = taCategorieArticles;
		this.actif = actif;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_ARTICLE")
	@Column(name = "ID_ARTICLE", unique = true, nullable = false)
	@LgrHibernateValidated(champ = "ID_ARTICLE",table = "TA_ARTICLE",clazz = TaArticle.class)
	public int getIdArticle() {
		return this.idArticle;
	}

	public void setIdArticle(int idArticle) {
		this.idArticle = idArticle;
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

	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	//cascade = {CascadeType.PERSIST, CascadeType.MERGE}
	@JoinColumn(name = "ID_FAMILLE")
	@LgrHibernateValidated(champ = "ID_FAMILLE",table = "TA_ARTICLE",clazz = TaFamille.class)
	public TaFamille getTaFamille() {
		return this.taFamille;
	}

	public void setTaFamille(TaFamille taFamille) {
		this.taFamille = taFamille;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "ID_T_TVA")
	@LgrHibernateValidated(champ = "ID_T_TVA",table = "TA_ARTICLE",clazz = TaTTva.class)
	public TaTTva getTaTTva() {
		return this.taTTva;
	}

	public void setTaTTva(TaTTva taTTva) {
		this.taTTva = taTTva;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, /*CascadeType.MERGE,*/ CascadeType.REFRESH})
	@JoinColumn(name = "ID_TVA")
	@LgrHibernateValidated(champ = "ID_TVA",table = "TA_ARTICLE",clazz = TaTva.class)
	public TaTva getTaTva() {
		return this.taTva;
	}

	public void setTaTva(TaTva taTva) {
		this.taTva = taTva;
	}

	@Column(name = "CODE_ARTICLE", length = 20)
	@LgrHibernateValidated(champ = "CODE_ARTICLE",table = "TA_ARTICLE",clazz = TaArticle.class)
	public String getCodeArticle() {
		return this.codeArticle;
	}

	public void setCodeArticle(String codeArticle) {
		this.codeArticle = codeArticle;
	}

	@Column(name = "LIBELLEC_ARTICLE", length = 100)
	@LgrHibernateValidated(champ = "LIBELLEC_ARTICLE",table = "TA_ARTICLE",clazz = TaArticle.class)
	public String getLibellecArticle() {
		return this.libellecArticle;
	}

	public void setLibellecArticle(String libellecArticle) {
		this.libellecArticle = libellecArticle;
	}

	@Column(name = "LIBELLEL_ARTICLE")
	@LgrHibernateValidated(champ = "LIBELLEL_ARTICLE",table = "TA_ARTICLE",clazz = TaArticle.class)
	public String getLibellelArticle() {
		return this.libellelArticle;
	}

	public void setLibellelArticle(String libellelArticle) {
		this.libellelArticle = libellelArticle;
	}

	@Column(name = "NUMCPT_ARTICLE", length = 8)
	@LgrHibernateValidated(champ = "NUMCPT_ARTICLE",table = "TA_ARTICLE",clazz = TaArticle.class)
	public String getNumcptArticle() {
		return this.numcptArticle;
	}

	public void setNumcptArticle(String numcptArticle) {
		this.numcptArticle = numcptArticle;
	}

	@Column(name = "DIVERS_ARTICLE")
	@LgrHibernateValidated(champ = "DIVERS_ARTICLE",table = "TA_ARTICLE",clazz = TaArticle.class)
	public String getDiversArticle() {
		return this.diversArticle;
	}

	public void setDiversArticle(String diversArticle) {
		this.diversArticle = diversArticle;
	}

	@Column(name = "COMMENTAIRE_ARTICLE")
	@LgrHibernateValidated(champ = "COMMENTAIRE_ARTICLE",table = "TA_ARTICLE",clazz = TaArticle.class)
	public String getCommentaireArticle() {
		return this.commentaireArticle;
	}

	public void setCommentaireArticle(String commentaireArticle) {
		this.commentaireArticle = commentaireArticle;
	}

	@Column(name = "STOCK_MIN_ARTICLE", precision = 15)
	@LgrHibernateValidated(champ = "STOCK_MIN_ARTICLE",table = "TA_ARTICLE",clazz = TaArticle.class)
	public BigDecimal getStockMinArticle() {
		return this.stockMinArticle;
	}

	public void setStockMinArticle(BigDecimal stockMinArticle) {
		this.stockMinArticle = stockMinArticle;
	}
	
	@Column(name = "ACTIF")
	@LgrHibernateValidated(champ = "ACTIF",table = "TA_ARTICLE",clazz = TaArticle.class)
	public Integer getActif() {
		return this.actif;
	}

	public void setActif(Integer actif) {
		this.actif = actif;
	}
	
	@Column(name = "ORIGINE_IMPORT", length = 100)
	@LgrHibernateValidated(champ = "ORIGINE_IMPORT",table = "TA_ARTICLE",clazz = TaArticle.class)
	public String getOrigineImport() {
		return this.origineImport;
	}

	public void setOrigineImport(String origineImport) {
		this.origineImport = origineImport;
	}
	
	@Column(name = "ID_IMPORT", length = 100)
	@LgrHibernateValidated(champ = "ID_IMPORT",table = "TA_ARTICLE",clazz = TaArticle.class)
	public String getIdImport() {
		return this.idImport;
	}

	public void setIdImport(String idImport) {
		this.idImport = idImport;
	}

	@Column(name = "QUI_CREE_ARTICLE", length = 50)
	public String getQuiCreeArticle() {
		return this.quiCreeArticle;
	}

	public void setQuiCreeArticle(String quiCreeArticle) {
		this.quiCreeArticle = quiCreeArticle;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "QUAND_CREE_ARTICLE", length = 19)
	public Date getQuandCreeArticle() {
		return this.quandCreeArticle;
	}

	public void setQuandCreeArticle(Date quandCreeArticle) {
		this.quandCreeArticle = quandCreeArticle;
	}

	@Column(name = "QUI_MODIF_ARTICLE", length = 50)
	public String getQuiModifArticle() {
		return this.quiModifArticle;
	}

	public void setQuiModifArticle(String quiModifArticle) {
		this.quiModifArticle = quiModifArticle;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "QUAND_MODIF_ARTICLE", length = 19)
	public Date getQuandModifArticle() {
		return this.quandModifArticle;
	}

	public void setQuandModifArticle(Date quandModifArticle) {
		this.quandModifArticle = quandModifArticle;
	}

	@Column(name = "IP_ACCES", length = 50)
	public String getIpAcces() {
		return this.ipAcces;
	}

	public void setIpAcces(String ipAcces) {
		this.ipAcces = ipAcces;
	}

//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "taArticle")
//	public Set<TaRefPrix> getTaRefPrixes() {
//		return this.taRefPrixes;
//	}
//
//	public void setTaRefPrixes(Set<TaRefPrix> taRefPrixes) {
//		this.taRefPrixes = taRefPrixes;
//	}

	@OrderBy("idPrix")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "taArticle")
	public Set<TaPrix> getTaPrixes() {
		return this.taPrixes;
	}

	public void setTaPrixes(Set<TaPrix> taPrixes) {
		this.taPrixes = taPrixes;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "taArticle")
	public Set<TaUnite> getTaConditionnementsArticle() {
		return this.taConditionnementsArticle;
	}

	public void setTaConditionnementsArticle(Set<TaUnite> taConditionnementsArticle) {
		this.taConditionnementsArticle = taConditionnementsArticle;
	}
	
	@OrderBy("id")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "taArticle")
	public Set<TaRapportUnite> getTaRapportUnites() {
		return this.taRapportUnites;
	}

	public void setTaRapportUnites(Set<TaRapportUnite> taRapportUnites) {
		this.taRapportUnites = taRapportUnites;
	}
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_PRIX")
	@LgrHibernateValidated(champ = "ID_PRIX",table = "TA_ARTICLE",clazz = TaPrix.class)
	public TaPrix getTaPrix() {
		return taPrix;
	}

	public void setTaPrix(TaPrix taPrix) {
		this.taPrix = taPrix;
	}
	
//	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	@JoinColumn(name = "ID_CONDITIONNEMENT_ARTICLE")
//	@LgrHibernateValidated(champ = "ID_CONDITIONNEMENT_ARTICLE",table = "TA_ARTICLE",clazz = TaUnite.class)
//	public TaUnite getTaConditionnementArticle() {
//		return taConditionnementArticle;
//	}
//
//	public void setTaConditionnementArticle(TaUnite taConditionnementArticle) {
//		this.taConditionnementArticle = taConditionnementArticle;
//	}
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_RAPPORT_UNITE")
	@LgrHibernateValidated(champ = "ID_RAPPORT_UNITE",table = "TA_ARTICLE",clazz = TaRapportUnite.class)
	public TaRapportUnite getTaRapportUnite() {
		return taRapportUnite;
	}

	public void setTaRapportUnite(TaRapportUnite taRapportUnite) {
		this.taRapportUnite = taRapportUnite;
	}
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_IMAGE_ARTICLE")
	@LgrHibernateValidated(champ = "ID_IMAGE_ARTICLE",table = "TA_ARTICLE",clazz = TaImageArticle.class)
	public TaImageArticle getTaImageArticle() {
		return taImageArticle;
	}

	public void setTaImageArticle(TaImageArticle taImageArticle) {
		this.taImageArticle = taImageArticle;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "taArticle")
	public Set<TaNoteArticle> getTaNotes() {
		return this.taNotes;
	}

	public void setTaNotes(Set<TaNoteArticle> taNotes) {
		this.taNotes = taNotes;
	}
	
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "taArticle")
//	public Set<TaRChampSuppArt> getTaRChampSuppArtes(){
//		return this.taRChampSuppArtes;
//	}
//
//	public void setTaRChampSuppArtes(Set<TaRChampSuppArt> taRChampSuppArtes) {
//		this.taRChampSuppArtes = taRChampSuppArtes;
//	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "taArticle")
	public Set<TaImageArticle> getTaImageArticles(){
		return this.taImageArticles;
	}

	public void setTaImageArticles(Set<TaImageArticle> taImageArticles) {
		this.taImageArticles = taImageArticles;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "TA_R_CATEGORIE_ARTICLE",
			joinColumns = {@JoinColumn(name = "ID_ARTICLE")},inverseJoinColumns = {@JoinColumn(name = "ID_CATEGORIE_ARTICLE")})
	public Set<TaCategorieArticle> getTaCategorieArticles(){
		return this.taCategorieArticles;
	}

	public void setTaCategorieArticles(Set<TaCategorieArticle> taCategorieArticles) {
		this.taCategorieArticles = taCategorieArticles;
	}
	
////	@ManyToMany(fetch = FetchType.LAZY)
////	@JoinTable(name = "TA_R_T_SUPPORT",
////			joinColumns = {@JoinColumn(name = "ID_ARTICLE")},inverseJoinColumns = {@JoinColumn(name = "ID_ARTICLE")})
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "taArticle")
//	public Set<TaRTSupport> getTaRTSupports(){
//		return this.taRTSupports;
//	}
//
//	public void setTaRTSupports(Set<TaRTSupport> taRTSupports) {
//		this.taRTSupports = taRTSupports;
//	}
	
	
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "TA_R_FOURNISSEUR_ARTICLE",
			joinColumns = {@JoinColumn(name = "ID_ARTICLE")},inverseJoinColumns = {@JoinColumn(name = "ID_TIERS")})
	public Set<TaTiers> getTaFournisseurs(){
		return this.taFournisseurs;
	}

	public void setTaFournisseurs(Set<TaTiers> taFournisseurs) {
		this.taFournisseurs = taFournisseurs;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "TA_R_LABEL_ARTICLE",
			joinColumns = {@JoinColumn(name = "ID_ARTICLE")},inverseJoinColumns = {@JoinColumn(name = "ID_LABEL_ARTICLE")})
	public Set<TaLabelArticle> getTaLabelArticles(){
		return this.taLabelArticles;
	}

	public void setTaLabelArticles(Set<TaLabelArticle> taLabelArticles) {
		this.taLabelArticles = taLabelArticles;
	}
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_CATALOGUE_WEB")
	@LgrHibernateValidated(champ = "ID_CATALOGUE_WEB",table = "TA_ARTICLE",clazz = TaArticle.class)
	public TaCatalogueWeb getTaCatalogueWeb() {
		return this.taCatalogueWeb;
	}

	public void setTaCatalogueWeb(TaCatalogueWeb taCatalogueWeb) {
		this.taCatalogueWeb = taCatalogueWeb;
	}
	
	@Column(name = "LONGUEUR", precision = 15)
	@LgrHibernateValidated(champ = "LONGUEUR",table = "TA_ARTICLE",clazz = TaArticle.class)
	public BigDecimal getLongueur() {
		return longueur;
	}

	public void setLongueur(BigDecimal longueur) {
		this.longueur = longueur;
	}

	@Column(name = "LARGEUR", precision = 15)
	@LgrHibernateValidated(champ = "LARGEUR",table = "TA_ARTICLE",clazz = TaArticle.class)
	public BigDecimal getLargeur() {
		return largeur;
	}

	public void setLargeur(BigDecimal largeur) {
		this.largeur = largeur;
	}

	@Column(name = "HAUTEUR", precision = 15)
	@LgrHibernateValidated(champ = "HAUTEUR",table = "TA_ARTICLE",clazz = TaArticle.class)
	public BigDecimal getHauteur() {
		return hauteur;
	}

	public void setHauteur(BigDecimal hauteur) {
		this.hauteur = hauteur;
	}

	@Column(name = "POIDS", precision = 15)
	@LgrHibernateValidated(champ = "POIDS",table = "TA_ARTICLE",clazz = TaArticle.class)
	public BigDecimal getPoids() {
		return poids;
	}

	public void setPoids(BigDecimal poids) {
		this.poids = poids;
	}

//	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	@JoinColumn(name = "ID_TITRE_TRANSPORT")
//	@LgrHibernateValidated(champ = "ID_TITRE_TRANSPORT",table = "TA_TITRE_TRANSPORT",clazz = TaTitreTransport.class)
//	public TaTitreTransport getTaTitreTransport(){
//		return this.taTitreTransport;
//	}
//
//	public void setTaTitreTransport(TaTitreTransport titreTransport) {
//		this.taTitreTransport = titreTransport;
//	}
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_R_TITRE_TRANSPORT")
	@LgrHibernateValidated(champ = "ID_R_TITRE_TRANSPORT",table = "TA_ARTICLE",clazz = TaRTaTitreTransport.class)
	public TaRTaTitreTransport getTaRTaTitreTransport() {
		return taRTaTitreTransport;
	}

	public void setTaRTaTitreTransport(TaRTaTitreTransport taTitreTransport) {
		this.taRTaTitreTransport = taTitreTransport;
	}

//	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "taArticle")
//	public Set<TaRTArticle> getTaRTArticles() {
//		return this.taRTArticles;
//	}
//
//	public void setTaRTArticles(Set<TaRTArticle> taRTArticles) {
//		this.taRTArticles = taRTArticles;
//	}	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codeArticle == null) ? 0 : codeArticle.hashCode());
		return result;
	}

	

	
	public void addPrix(TaPrix taPrix){
		this.getTaPrixes().add(taPrix);
	}
	
	public void removePrix(TaPrix taPrix){
		boolean estRefPrix=false;
		if(this.taPrix!=null) estRefPrix=this.taPrix.getIdPrix()==taPrix.getIdPrix();

		this.getTaPrixes().remove(taPrix);
		if(estRefPrix && !this.getTaPrixes().isEmpty()){
			TaPrix prix =this.getTaPrixes().iterator().next();
			if(prix!=null)
				this.setTaPrix(prix);
		}
		else if(this.getTaPrixes().isEmpty()){
			this.setTaPrix(null);
		}
		
	}
	
	public void addContiditionnementArticle(TaUnite taConditionnementArticle){
		this.getTaConditionnementsArticle().add(taConditionnementArticle);
	}
	
	public void removeContiditionnementArticle(TaUnite taConditionnementArticle){
		boolean estConditionnementDefaut=false;
		if(this.taConditionnementArticle!=null) estConditionnementDefaut=this.taConditionnementArticle.getIdUnite()==taConditionnementArticle.getIdUnite();

		this.getTaConditionnementsArticle().remove(taConditionnementArticle);
//		if(estConditionnementDefaut && !this.getTaConditionnementsArticle().isEmpty()){
//			TaUnite conditionnementArticle = this.getTaConditionnementsArticle().iterator().next();
//			if(conditionnementArticle!=null)
//				this.setTaConditionnementArticle(conditionnementArticle);
//		}
//		else if(this.getTaConditionnementsArticle().isEmpty()){
//			this.setTaConditionnementArticle(null);
//		}
		
	}
	
	public void addRapportUnite(TaRapportUnite taRapportUnite){
		this.getTaRapportUnites().add(taRapportUnite);
	}
	
	
	public void removeRapportUnite(TaRapportUnite taRapportUnite){
		boolean estRefRapportUnite=false;
		if(this.taRapportUnite!=null)
			estRefRapportUnite=this.taRapportUnite.getId()==taRapportUnite.getId();

		this.getTaRapportUnites().remove(taRapportUnite);		
		if(estRefRapportUnite && !this.getTaRapportUnites().isEmpty()){
			Iterator<TaRapportUnite> iterator = this.getTaRapportUnites().iterator();
			boolean trouve = false;
			if(getTaPrix()!=null && getTaPrix().getTaUnite()!=null){
			while (iterator.hasNext()&& !trouve ){
				TaRapportUnite type = (TaRapportUnite) iterator.next();
				if(type.getTaUnite1().equals(getTaPrix().getTaUnite()))
					this.setTaRapportUnite(type);
			}
			}
			if (!trouve)this.setTaRapportUnite(null);
		}
		else if(this.getTaRapportUnites().isEmpty()){
			this.setTaRapportUnite(null);
		}
	}

	public boolean isRapportUniteRef(TaRapportUnite taRapportUnite){
		boolean estRefRapportUnite=false;
		if(this.taRapportUnite!=null && taRapportUnite!=null)
			estRefRapportUnite= taRapportUnite.getId()==taRapportUnite.getId();
		return estRefRapportUnite;
	}
	
	
	public TaRapportUnite recupRapportUnite(){
		return taRapportUnite;
		
	}
	
	public void addNote(TaNoteArticle taNote){
		if(!this.getTaNotes().contains(taNote))
			this.getTaNotes().add(taNote);
		//if(this.getTaNote()==null)this.setTaNote(taNote);
	}
	public void removeNote(TaNoteArticle taNote){
		//boolean estDefaut=(this.taNote!=null)&& this.taNote.getIdNote()==taNote.getIdNote();

		this.getTaNotes().remove(taNote);
//		if(estDefaut && !this.getTaNotes().isEmpty())
//			this.setTaNote(this.getTaNotes().iterator().next());
//		else if(this.getTaNotes().isEmpty()){
//			this.setTaNote(null);
//		}
		
	}
	
	public void addImageArticle(TaImageArticle taImageArticle){
		this.getTaImageArticles().add(taImageArticle);
	}
	
	public void removeImageArticle(TaImageArticle taImageArticle){
		boolean estDefaut=false;
		if(this.taImageArticle!=null) estDefaut=this.taImageArticle.getIdImageArticle()==taImageArticle.getIdImageArticle();

		this.getTaImageArticles().remove(taImageArticle);
		
		if(estDefaut && !this.getTaImageArticles().isEmpty()){
			TaImageArticle image =this.getTaImageArticles().iterator().next();
			if(image!=null)
				this.setTaImageArticle(image);
		}
		else if(this.getTaImageArticles().isEmpty()){
			this.setTaImageArticle(null);
		}
		
	}
	
	public void addCategorie(TaCategorieArticle taCategorieArticle){
		if(!this.getTaCategorieArticles().contains(taCategorieArticle))
			this.getTaCategorieArticles().add(taCategorieArticle);
	}
	public void removeCategorie(TaCategorieArticle taCategorieArticle){
		this.getTaCategorieArticles().remove(taCategorieArticle);
	}
	
//	public void addTArticle(TaRTSupport taRTSupport){
//		if(!this.getTaRTSupports().contains(taRTSupport))
//			this.getTaRTSupports().add(taRTSupport);
//	}
//	public void removeTArticle(TaRTSupport taRTSupport){
//		this.getTaRTSupports().remove(taRTSupport);
//	}
	
	public void addLabelArticles(TaLabelArticle taLabelArticle){
		if(!this.getTaLabelArticles().contains(taLabelArticle))
			this.getTaLabelArticles().add(taLabelArticle);
	}
	public void removeLabelArticles(TaLabelArticle taLabelArticle){
		this.getTaLabelArticles().remove(taLabelArticle);
	}
	
	public void addFournisseur(TaTiers taTiers){
		this.getTaFournisseurs().add(taTiers);
	}
	
	public void removeFournisseur(TaTiers taTiers){
//		boolean estDefaut=false;
//		if(this.taTiers!=null) estDefaut = this.taTiers.getIdTiers()==taTiers.getIdTiers();

		this.getTaFournisseurs().remove(taTiers);
		
//		if(estDefaut && !this.getTaFournisseurs().isEmpty()){
//			TaTiers image =this.getTaFournisseurs().iterator().next();
//			if(image!=null)
//				this.setTaImageArticle(image);
//		}
//		else if(this.getTaFournisseurs().isEmpty()){
//			this.setTaImageArticle(null);
//		}
		
	}
	
	/**
	 * Retourne le prix pour le tiers passé en paramètres, ou le prix par defaut pour cet article.
	 * Le prix affecté au tiers est prioritaire par rapport au prix affecté au "type de tarif" du tiers.
	 * @param tiers
	 * @return
	 */
	public TaPrix chercheTarif(TaTiers tiers) {
		if(tiers!=null) {
			boolean trouve = false;
			Iterator<TaPrix> ite = taPrixes.iterator();
			Iterator<TaTTarif> iteTTarif = null;
			Iterator<TaTiers> iteTiers = null;
			TaPrix p = null;
			TaTiers t =null;
			TaTTarif ttarif =null;
			//recherche d'un prix pour le tiers
			while(!trouve && ite.hasNext()) {
				p = ite.next();
				iteTiers = p.getTaTiers().iterator();
				while(!trouve && iteTiers.hasNext()) {
					t = iteTiers.next();
					if(t.getCodeTiers().equals(tiers.getCodeTiers())) {
						trouve = true;
					}
				}
			}
			if(trouve) {
				return p;
			} else {
				//aucun pour ce tiers => recherche d'un prix pour le "type tarif" du tiers
				if(tiers.getTaTTarif()!=null) {
					ite = taPrixes.iterator();
					while(!trouve && ite.hasNext()) {
						p = ite.next();
						iteTTarif = p.getTaTTarif().iterator();
						while(!trouve && iteTTarif.hasNext()) {
							ttarif = iteTTarif.next();
							if(ttarif.getCodeTTarif().equals(tiers.getTaTTarif().getCodeTTarif())) {
								trouve = true;
							}
						}
					}
				}
			}
			if(trouve) {
				return p;
			}
		} 
		//aucun prix spécifique pour ce tiers, on prend le prix par defaut de l'article
		return taPrix;
	}
	
	public boolean possedeDejaUnPrixPourCeTiers(String codeTiers) {
		boolean trouve = false;
		Iterator<TaPrix> ite = taPrixes.iterator();
		Iterator<TaTiers> iteTiers = null;
		TaPrix p = null;
		TaTiers t =null;
		while(!trouve && ite.hasNext()) {
			p = ite.next();
			iteTiers = p.getTaTiers().iterator();
			while(!trouve && iteTiers.hasNext()) {
				t = iteTiers.next();
				if(t.getCodeTiers().equals(codeTiers)) {
					trouve = true;
				}
			}
		}
		return trouve;
	}
	
	public boolean possedeDejaUnPrixPourCeTypeDeTarif(String codeTTarif) {
		boolean trouve = false;
		Iterator<TaPrix> ite = taPrixes.iterator();
		Iterator<TaTTarif> iteTTarif = null;
		TaPrix p = null;
		TaTTarif t =null;
		while(!trouve && ite.hasNext()) {
			p = ite.next();
			iteTTarif = p.getTaTTarif().iterator();
			while(!trouve && iteTTarif.hasNext()) {
				t = iteTTarif.next();
				if(t.getCodeTTarif().equals(codeTTarif)) {
					trouve = true;
				}
			}
		}
		return trouve;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		TaArticle article =new TaArticle();
		try {
			article.setCodeArticle(codeArticle);
			article.setCommentaireArticle(commentaireArticle);
			article.setDiversArticle(diversArticle);
//			article.setIdArticle(0);
//			article.setIdImport(null);
			article.setLibellecArticle(libellecArticle);
			article.setLibellelArticle(libellelArticle);
			article.setNumcptArticle(numcptArticle);
//			article.setOrigineImport(origineImport);
			article.setStockMinArticle(stockMinArticle);
//			article.setTaCatalogueWeb(taCatalogueWeb);
//			article.setTaCategorieArticles(taCategorieArticles);
			article.setTaFamille(taFamille);
//			article.setTaFournisseurs(taFournisseurs);
//			article.setTaImageArticle(taImageArticle);
//			article.setTaImageArticles(taImageArticles);
//			article.setTaLabelArticles(taLabelArticles);
			for (TaNoteArticle note : taNotes) {
				TaNoteArticle noteArticle = (TaNoteArticle)note.clone();
				noteArticle.setTaArticle(article);
				article.addNote(note);
			}
			//article.setTaNotes(taNotes);
//			if(taPrix!=null){
//				article.setTaPrix((TaPrix)taPrix.clone());
//				article.getTaPrix().setTaArticle(article);
//			}
			
			for (TaPrix obj : taPrixes) {
				boolean ref=obj==taPrix;
				TaPrix prixNew = (TaPrix)obj.clone();
				prixNew.setTaArticle(article);
				article.addPrix(prixNew);
				if(ref){
					article.setTaPrix(prixNew);
				}

			}
//			article.setTaPrixes(taPrixes);
			if(taRapportUnite!=null){				
				article.setTaRapportUnite(((TaRapportUnite)taRapportUnite.clone()));
				article.getTaRapportUnite().setTaArticle(article);
			}
			//article.setTaRapportUnites(taRapportUnites);
			for (TaRapportUnite rapportUnite : taRapportUnites) {
				TaRapportUnite rapportNew=(TaRapportUnite)rapportUnite.clone();
				rapportNew.setTaArticle(article);
				article.addRapportUnite(rapportUnite);
			}


			
			
			article.setTaTTva(taTTva);
			article.setTaTva(taTva);
			
		} catch (Exception e) {
			logger.error("", e);
		}
		// on renvoie le clone
		return article;
	}
	
//@PostPersist
//@PostUpdate
public void afterPost()throws Exception{
	//recalcul le prix ttc en fonction du taux de tva
	BigDecimal taux=BigDecimal.valueOf(1);
	if(this.getTaTva()!=null && this.getTaTva().getTauxTva()!=null)
		taux= this.getTaTva().getTauxTva().divide(BigDecimal.valueOf(100));
	for (TaPrix prix : this.getTaPrixes()) {
		prix.setPrixttcPrix(prix.getPrixPrix().add(prix.getPrixPrix().multiply(taux),MathContext.DECIMAL128).setScale(3,BigDecimal.ROUND_HALF_UP));
	}
}

}

package fr.legrain.tiers.dao;

// Generated Mar 25, 2009 10:06:49 AM by Hibernate Tools 3.2.0.CR1

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
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
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PostRemove;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Query;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import fr.legrain.SupportAbon.dao.TaSupportAbon;
import fr.legrain.articles.dao.TaPrix;
import fr.legrain.documents.dao.TaAcompte;
import fr.legrain.documents.dao.TaAvoir;
import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaReglement;
import fr.legrain.documents.dao.TaTPaiement;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.gestCom.Appli.LgrHibernateValidated;
import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.lib.data.LibDate;

/**
 * TaTiers generated by hbm2java
 */
@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "TA_TIERS")
@SequenceGenerator(name = "GEN_TIERS", sequenceName = "NUM_ID_TIERS", allocationSize = 1)
@NamedQueries(value = { 
		@NamedQuery(name=TaTiers.QN.FIND_BY_TYPE, query="select a from TaTiers a where a.taTTiers.codeTTiers = ?"),
		@NamedQuery(name=TaTiers.QN.FIND_BY_IMPORT, query="select a from TaTiers a where a.origineImport = ? and a.idImport = ?")
		})
public class TaTiers implements java.io.Serializable {
	
	public static class QN {
		public static final String FIND_BY_TYPE = "TaTiers.findByType";
		public static final String FIND_BY_IMPORT = "TaTiers.findByImport";
	}
	public static final String TYPE_DOC = "Tiers";
	private int idTiers;
	private String version;
	private TaCompl taCompl;
	private TaTTiers taTTiers;
	private TaTTvaDoc taTTvaDoc;
	private TaTCivilite taTCivilite;
	private TaTelephone taTelephone;
	private TaCPaiement taCPaiement;
	private TaTPaiement taTPaiement;
	private TaEmail taEmail;
	private TaWeb taWeb;
	private TaTEntite taTEntite;
	private TaAdresse taAdresse;
	private TaTTarif taTTarif;
	private TaEntreprise taEntreprise;
	private TaCommentaire taCommentaire;
	private TaInfoJuridique taInfoJuridique;
	private TaTiers taCommercial;
	private Date dateAnniv;
//	private TaParamCreeDocTiers taParamCreeDocTiers;
	private String codeTiers;
	private String codeCompta;
	private String compte;
	private String nomTiers;
	private String prenomTiers;
	private String surnomTiers;
	private Integer actifTiers;
	private Integer ttcTiers;
	private String quiCreeTiers;
	private Date quandCreeTiers;
	private String quiModifTiers;
	private Date quandModifTiers;
	private String idImport;
	private String origineImport;
	private String ipAcces;
	private Integer versionObj;
	private TaFamilleTiers taFamilleTiers;
	
	private Set<TaAdresse> taAdresses = new HashSet<TaAdresse>(0);
	private Set<TaCompteBanque> taCompteBanques = new HashSet<TaCompteBanque>(0);
	private Set<TaEmail> taEmails = new HashSet<TaEmail>(0);
	private Set<TaTelephone> taTelephones = new HashSet<TaTelephone>(0);
	private Set<TaLiens> taLienses = new HashSet<TaLiens>(0);
	private Set<TaWeb> taWebs = new HashSet<TaWeb>(0);
	private Set<TaFamilleTiers> taFamilleTierses = new HashSet<TaFamilleTiers>(0);
	private Set<TaTiers> taCommerciaux = new HashSet<TaTiers>(0);
	private Set<TaNoteTiers> taNotes = new HashSet<TaNoteTiers>(0);
	private Set<TaParamCreeDocTiers> taParamCreeDocTierses = new HashSet<TaParamCreeDocTiers>(0);
	
	//private Set<TaSupportAbon> taSupportAbons = new HashSet<TaSupportAbon>(0);
	
	@Transient
	private Boolean accepte=true;
	
	
	public TaTiers() {
	}

	public TaTiers(int idTiers) {
		this.idTiers = idTiers;
	}

	public TaTiers(int idTiers, TaCompl taCompl, TaTTiers taTTiers,
			TaTCivilite taTCivilite, TaTelephone taTelephone,
			TaCPaiement taCPaiement, TaEmail taEmail, TaWeb taWeb,
			TaTEntite taTEntite, TaAdresse taAdresse, TaTTarif taTTarif,
			TaEntreprise taEntreprise, TaCommentaire taCommentaire,
			TaInfoJuridique taInfoJuridique,
			String codeTiers, String codeCompta, String compte,
			String nomTiers, String prenomTiers, String surnomTiers,
			Integer actifTiers, Integer ttcTiers, String quiCreeTiers,
			Date quandCreeTiers, String quiModifTiers, Date quandModifTiers,
			String ipAcces, Integer versionObj, Set<TaAdresse> taAdresses,
			Set<TaCompteBanque> taCompteBanques, Set<TaEmail> taEmails,
			Set<TaTelephone> taTelephones, Set<TaLiens> taLienses,
			Set<TaWeb> taWebs, Date dateAnniv) {
		this.idTiers = idTiers;
		this.taCompl = taCompl;
		this.taTTiers = taTTiers;
		this.taTCivilite = taTCivilite;
		this.taTelephone = taTelephone;
		this.taCPaiement = taCPaiement;
		this.taEmail = taEmail;
		this.taWeb = taWeb;
		this.taTEntite = taTEntite;
		this.taAdresse = taAdresse;
		this.taTTarif = taTTarif;
		this.taEntreprise = taEntreprise;
		this.taInfoJuridique = taInfoJuridique;
		this.taCommentaire = taCommentaire;
		this.codeTiers = codeTiers;
		this.codeCompta = codeCompta;
		this.compte = compte;
		this.nomTiers = nomTiers;
		this.prenomTiers = prenomTiers;
		this.surnomTiers = surnomTiers;
		this.dateAnniv = dateAnniv;
		this.actifTiers = actifTiers;
		this.ttcTiers = ttcTiers;
		this.quiCreeTiers = quiCreeTiers;
		this.quandCreeTiers = quandCreeTiers;
		this.quiModifTiers = quiModifTiers;
		this.quandModifTiers = quandModifTiers;
		this.ipAcces = ipAcces;
		this.versionObj = versionObj;
		this.taAdresses = taAdresses;
		this.taCompteBanques = taCompteBanques;
		this.taEmails = taEmails;
		this.taTelephones = taTelephones;
		this.taLienses = taLienses;
		this.taWebs = taWebs;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_TIERS")
	@Column(name = "ID_TIERS", unique = true, nullable = false)
	@LgrHibernateValidated(champ = "ID_TIERS",table = "TA_TIERS",clazz = TaTiers.class)
	public int getIdTiers() {
		return this.idTiers;
	}

	public void setIdTiers(int idTiers) {
		this.idTiers = idTiers;
	}

	
	@Column(name = "VERSION", length = 20)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "ID_COMPL")
	@LgrHibernateValidated(champ = "ID_COMPL",table = "TA_TIERS",clazz = TaCompl.class)
	public TaCompl getTaCompl() {
		return this.taCompl;
	}

	public void setTaCompl(TaCompl taCompl) {
		this.taCompl = taCompl;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "ID_T_TIERS")
	@LgrHibernateValidated(champ = "ID_T_TIERS",table = "TA_TIERS",clazz = TaTTiers.class)
	public TaTTiers getTaTTiers() {
		return this.taTTiers;
	}

	public void setTaTTiers(TaTTiers taTTiers) {
		this.taTTiers = taTTiers;
	}
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "ID_T_TVA_DOC")
	@LgrHibernateValidated(champ = "ID_T_TVA_DOC",table = "TA_TIERS",clazz = TaTTvaDoc.class)
	public TaTTvaDoc getTaTTvaDoc() {
		return taTTvaDoc;
	}

	public void setTaTTvaDoc(TaTTvaDoc taTTvaDoc) {
		this.taTTvaDoc = taTTvaDoc;
	}
	
	

	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "ID_T_CIVILITE")
	@LgrHibernateValidated(champ = "ID_T_CIVILITE",table = "TA_TIERS",clazz = TaTCivilite.class)
	public TaTCivilite getTaTCivilite() {
		return this.taTCivilite;
	}

	public void setTaTCivilite(TaTCivilite taTCivilite) {
		this.taTCivilite = taTCivilite;
	}

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_TELEPHONE")
	@LgrHibernateValidated(champ = "ID_TELEPHONE",table = "TA_TIERS",clazz = TaTelephone.class)
	public TaTelephone getTaTelephone() {
		return this.taTelephone;
	}

	public void setTaTelephone(TaTelephone taTelephone) {
		this.taTelephone = taTelephone;
	}
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_COMMERCIAL")
	@LgrHibernateValidated(champ = "ID_COMMERCIAL",table = "TA_TIERS",clazz = TaTiers.class)
	public TaTiers getTaCommercial() {
		return this.taCommercial;
	}

	public void setTaCommercial(TaTiers taCommercial) {
		this.taCommercial = taCommercial;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "ID_C_PAIEMENT")
	@LgrHibernateValidated(champ = "ID_C_PAIEMENT",table = "TA_TIERS",clazz = TaCPaiement.class)
	public TaCPaiement getTaCPaiement() {
		return this.taCPaiement;
	}

	public void setTaCPaiement(TaCPaiement taCPaiement) {
		this.taCPaiement = taCPaiement;
	}
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "ID_T_PAIEMENT")
	@LgrHibernateValidated(champ = "ID_T_PAIEMENT",table = "TA_TIERS",clazz = TaTPaiement.class)
	public TaTPaiement getTaTPaiement() {
		return taTPaiement;
	}

	public void setTaTPaiement(TaTPaiement taTPaiement) {
		this.taTPaiement = taTPaiement;
	}

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_EMAIL")
	@LgrHibernateValidated(champ = "ID_EMAIL",table = "TA_TIERS",clazz = TaEmail.class)
	public TaEmail getTaEmail() {
		return this.taEmail;
	}

	public void setTaEmail(TaEmail taEmail) {
		this.taEmail = taEmail;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_WEB")
	@LgrHibernateValidated(champ = "ID_WEB",table = "TA_TIERS",clazz = TaWeb.class)
	public TaWeb getTaWeb() {
		return this.taWeb;
	}

	public void setTaWeb(TaWeb taWeb) {
		this.taWeb = taWeb;
	}


	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_FAMILLE_TIERS")
	@LgrHibernateValidated(champ = "ID_FAMILLE_TIERS",table = "TA_TIERS",clazz = TaFamilleTiers.class)
	public TaFamilleTiers getTaFamilleTiers() {
		return taFamilleTiers;
	}

	public void setTaFamilleTiers(TaFamilleTiers taFamilleTiers) {
		this.taFamilleTiers = taFamilleTiers;
	}

	
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "ID_T_ENTITE")
	@LgrHibernateValidated(champ = "ID_T_ENTITE",table = "TA_TIERS",clazz = TaTEntite.class)
	public TaTEntite getTaTEntite() {
		return this.taTEntite;
	}

	public void setTaTEntite(TaTEntite taTEntite) {
		this.taTEntite = taTEntite;
	}

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_ADRESSE")
	@LgrHibernateValidated(champ = "ID_ADRESSE",table = "TA_TIERS",clazz = TaAdresse.class)
	public TaAdresse getTaAdresse() {
		return this.taAdresse;
	}

	public void setTaAdresse(TaAdresse taAdresse) {
		this.taAdresse = taAdresse;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "ID_T_TARIF")
	@LgrHibernateValidated(champ = "ID_T_TARIF",table = "TA_TIERS",clazz = TaTTarif.class)
	public TaTTarif getTaTTarif() {
		return this.taTTarif;
	}

	public void setTaTTarif(TaTTarif taTTarif) {
		this.taTTarif = taTTarif;
	}

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_ENTREPRISE")
	@LgrHibernateValidated(champ = "ID_ENTREPRISE",table = "TA_TIERS",clazz = TaEntreprise.class)
	public TaEntreprise getTaEntreprise() {
		return this.taEntreprise;
	}

	public void setTaEntreprise(TaEntreprise taEntreprise) {
		this.taEntreprise = taEntreprise;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_COMMENTAIRE")
	@LgrHibernateValidated(champ = "ID_COMMENTAIRE",table = "TA_TIERS",clazz = TaCommentaire.class)
	public TaCommentaire getTaCommentaire() {
		return this.taCommentaire;
	}

	public void setTaCommentaire(TaCommentaire taCommentaire) {
		this.taCommentaire = taCommentaire;
	}
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_INFO_JURIDIQUE")
	@LgrHibernateValidated(champ = "ID_INFO_JURIDIQUE",table = "TA_TIERS",clazz = TaInfoJuridique.class)
	public TaInfoJuridique getTaInfoJuridique() {
		return this.taInfoJuridique;
	}

	public void setTaInfoJuridique(TaInfoJuridique taInfoJuridique) {
		this.taInfoJuridique = taInfoJuridique;
	}
	
//	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	@JoinColumn(name = "ID_PARAM_CREE_DOC_TIERS")
//	@LgrHibernateValidated(champ = "ID_PARAM_CREE_DOC_TIERS",table = "TA_TIERS",clazz = TaParamCreeDocTiers.class)
//	public TaParamCreeDocTiers getTaParamCreeDocTiers() {
//		return taParamCreeDocTiers;
//	}
//
//	public void setTaParamCreeDocTiers(TaParamCreeDocTiers taParamCreeDocTiers) {
//		this.taParamCreeDocTiers = taParamCreeDocTiers;
//	}
	
	@Column(name = "CODE_TIERS", length = 20)
	@LgrHibernateValidated(champ = "CODE_TIERS",table = "TA_TIERS",clazz = TaTiers.class)
	public String getCodeTiers() {
		return this.codeTiers;
	}

	public void setCodeTiers(String codeTiers) {
		this.codeTiers = codeTiers;
	}

	@Column(name = "CODE_COMPTA", length = 7)
	@LgrHibernateValidated(champ = "CODE_COMPTA",table = "TA_TIERS",clazz = TaTiers.class)
	public String getCodeCompta() {
		return this.codeCompta;
	}

	public void setCodeCompta(String codeCompta) {
		this.codeCompta = codeCompta;
	}

	@Column(name = "COMPTE", length = 8)
	@LgrHibernateValidated(champ = "COMPTE",table = "TA_TIERS",clazz = TaTiers.class)
	public String getCompte() {
		return this.compte;
	}

	public void setCompte(String compte) {
		this.compte = compte;
	}

	@Column(name = "NOM_TIERS", length = 100)
	@LgrHibernateValidated(champ = "NOM_TIERS",table = "TA_TIERS",clazz = TaTiers.class)
	public String getNomTiers() {
		return this.nomTiers;
	}

	public void setNomTiers(String nomTiers) {
		this.nomTiers = nomTiers;
	}

	@Column(name = "PRENOM_TIERS", length = 100)
	@LgrHibernateValidated(champ = "PRENOM_TIERS",table = "TA_TIERS",clazz = TaTiers.class)
	public String getPrenomTiers() {
		return this.prenomTiers;
	}

	public void setPrenomTiers(String prenomTiers) {
		this.prenomTiers = prenomTiers;
	}

	@Column(name = "SURNOM_TIERS", length = 20)
	@LgrHibernateValidated(champ = "SURNOM_TIERS",table = "TA_TIERS",clazz = TaTiers.class)
	public String getSurnomTiers() {
		return this.surnomTiers;
	}

	public void setSurnomTiers(String surnomTiers) {
		this.surnomTiers = surnomTiers;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_ANNIV", length = 10)
	public Date getDateAnniv() {
		return dateAnniv;
	}

	public void setDateAnniv(Date dateAnniv) {
		this.dateAnniv = dateAnniv;
	}
	
	
	@Column(name = "ACTIF_TIERS")
	@LgrHibernateValidated(champ = "ACTIF_TIERS",table = "TA_TIERS",clazz = TaTiers.class)
	public Integer getActifTiers() {
		return this.actifTiers;
	}

	public void setActifTiers(Integer actifTiers) {
		this.actifTiers = actifTiers;
	}

	@Column(name = "TTC_TIERS")
	@LgrHibernateValidated(champ = "TTC_TIERS",table = "TA_TIERS",clazz = TaTiers.class)
	public Integer getTtcTiers() {
		return this.ttcTiers;
	}

	public void setTtcTiers(Integer ttcTiers) {
		this.ttcTiers = ttcTiers;
	}

	@Column(name = "QUI_CREE_TIERS", length = 50)
	public String getQuiCreeTiers() {
		return this.quiCreeTiers;
	}

	public void setQuiCreeTiers(String quiCreeTiers) {
		this.quiCreeTiers = quiCreeTiers;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "QUAND_CREE_TIERS", length = 19)
	public Date getQuandCreeTiers() {
		return this.quandCreeTiers;
	}

	public void setQuandCreeTiers(Date quandCreeTiers) {
		this.quandCreeTiers = quandCreeTiers;
	}

	@Column(name = "QUI_MODIF_TIERS", length = 50)
	public String getQuiModifTiers() {
		return this.quiModifTiers;
	}

	public void setQuiModifTiers(String quiModifTiers) {
		this.quiModifTiers = quiModifTiers;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "QUAND_MODIF_TIERS", length = 19)
	public Date getQuandModifTiers() {
		return this.quandModifTiers;
	}

	public void setQuandModifTiers(Date quandModifTiers) {
		this.quandModifTiers = quandModifTiers;
	}
	
	@Column(name = "ORIGINE_IMPORT", length = 100)
	@LgrHibernateValidated(champ = "ORIGINE_IMPORT",table = "TA_TIERS",clazz = TaTiers.class)
	public String getOrigineImport() {
		return this.origineImport;
	}

	public void setOrigineImport(String origineImport) {
		this.origineImport = origineImport;
	}
	
	@Column(name = "ID_IMPORT", length = 100)
	@LgrHibernateValidated(champ = "ID_IMPORT",table = "TA_TIERS",clazz = TaTiers.class)
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

	@OrderBy("idAdresse")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "taTiers")
	public Set<TaAdresse> getTaAdresses() {
		return this.taAdresses;
	}

	public void setTaAdresses(Set<TaAdresse> taAdresses) {
		this.taAdresses = taAdresses;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "taTiers")
	public Set<TaCompteBanque> getTaCompteBanques() {
		return this.taCompteBanques;
	}

	public void setTaCompteBanques(Set<TaCompteBanque> taCompteBanques) {
		this.taCompteBanques = taCompteBanques;
	}

	@OrderBy("idEmail")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "taTiers")
	public Set<TaEmail> getTaEmails() {
		return this.taEmails;
	}

	
	public void setTaEmails(Set<TaEmail> taEmails) {
		this.taEmails = taEmails;
	}
	
//	@OrderBy("idSupportAbon")
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "taTiers")
//	public Set<TaSupportAbon> getTaSupportAbons() {
//		return this.taSupportAbons;
//	}
//	
//	
//	public void setTaSupportAbons(Set<TaSupportAbon> taSupportAbons) {
//		this.taSupportAbons = taSupportAbons;
//	}

	@OrderBy("idTelephone")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "taTiers")
	public Set<TaTelephone> getTaTelephones() {
		return this.taTelephones;
		
	}

	public void setTaTelephones(Set<TaTelephone> taTelephones) {
		this.taTelephones = taTelephones;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "taTiers")
	public Set<TaLiens> getTaLienses() {
		return this.taLienses;
	}

	public void setTaLienses(Set<TaLiens> taLienses) {
		this.taLienses = taLienses;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "taTiers")
	public Set<TaWeb> getTaWebs() {
		return this.taWebs;
	}

	public void setTaWebs(Set<TaWeb> taWebs) {
		this.taWebs = taWebs;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "taTiers")
	public Set<TaParamCreeDocTiers> getTaParamCreeDocTierses() {
		return this.taParamCreeDocTierses;
	}

	public void setTaParamCreeDocTierses(Set<TaParamCreeDocTiers> taParamCreeDocTierses) {
		this.taParamCreeDocTierses = taParamCreeDocTierses;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "TA_R_FAMILLE_TIERS",
			joinColumns = {@JoinColumn(name = "ID_TIERS")},inverseJoinColumns = {@JoinColumn(name = "ID_FAMILLE")})
	public Set<TaFamilleTiers> getTaFamilleTierses() {
		return taFamilleTierses;
	}

	public void setTaFamilleTierses(Set<TaFamilleTiers> taFamilleTierses) {
		this.taFamilleTierses = taFamilleTierses;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "TA_R_COMMERCIAL",
			joinColumns = {@JoinColumn(name = "ID_TIERS")},inverseJoinColumns = {@JoinColumn(name = "ID_TIERS_COM")})
	public Set<TaTiers> getTaCommerciaux() {
		return taCommerciaux;
	}

	public void setTaCommerciaux(Set<TaTiers> taCommerciaux) {
		this.taCommerciaux = taCommerciaux;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "taTiers")
	public Set<TaNoteTiers> getTaNotes() {
		return this.taNotes;
	}

	public void setTaNotes(Set<TaNoteTiers> taNotes) {
		this.taNotes = taNotes;
	}
	
	public void addEntreprise(TaEntreprise taEntreprise){
		this.setTaEntreprise(taEntreprise);
	}
	
	public void addAdresse(TaAdresse taAdresse){
		if(!this.getTaAdresses().contains(taAdresse))
			this.getTaAdresses().add(taAdresse);
		if(this.getTaAdresse()==null)this.setTaAdresse(taAdresse);
	}
	public void addCompteBanque(TaCompteBanque taCompteBanque){
		if(!this.getTaCompteBanques().contains(taCompteBanque))
			this.getTaCompteBanques().add(taCompteBanque);
	}
	public void addTelephone(TaTelephone taTelephone){
		if(!this.getTaTelephones().contains(taTelephone))
			this.getTaTelephones().add(taTelephone);
		if(this.getTaTelephone()==null)this.setTaTelephone(taTelephone);
	}
		public void removeTelephone(TaTelephone taTelephone){
		boolean estDefaut=(this.taTelephone!=null)&& this.taTelephone.getIdTelephone()==taTelephone.getIdTelephone();

		this.getTaTelephones().remove(taTelephone);
		if(estDefaut && !this.getTaTelephones().isEmpty())
			this.setTaTelephone(this.getTaTelephones().iterator().next());
		else if(this.getTaTelephones().isEmpty()){
			this.setTaTelephone(null);
		}  
		
	}
	public void removeAdresse(TaAdresse taAdresse){
		
		boolean estDefaut=(this.taAdresse!=null)&& this.taAdresse.getIdAdresse()==taAdresse.getIdAdresse();

		this.getTaAdresses().remove(taAdresse);
		if(estDefaut && !this.getTaAdresses().isEmpty())
			this.setTaAdresse(this.getTaAdresses().iterator().next());
		else if(this.getTaAdresses().isEmpty()){
			this.setTaAdresse(null);
		}
		
	}
	public void removeCompteBanque(TaCompteBanque taCompteBanque){
		this.getTaCompteBanques().remove(taCompteBanque);		
	}
	
	public void addEmail(TaEmail taEmail){
		if(!this.getTaEmails().contains(taEmail))
			this.getTaEmails().add(taEmail);
		if(this.getTaEmail()==null)this.setTaEmail(taEmail);
	}
	
	
	public void addCommercial(TaTiers taTiers){
		if(!this.getTaCommerciaux().contains(taTiers))
			this.getTaCommerciaux().add(taTiers);
	}
	
	public void removeCommercial(TaTiers taTiers){
		this.getTaCommerciaux().remove(taTiers);
	}
	
	public void addFamilleTiers(TaFamilleTiers taFamilleTiers){
		if(!this.getTaFamilleTierses().contains(taFamilleTiers))
			this.getTaFamilleTierses().add(taFamilleTiers);
		if(this.getTaFamilleTiers()==null)this.setTaFamilleTiers(taFamilleTiers);		
	}
	
	public void removeFamilleTiers(TaFamilleTiers taFamilleTiers){
		boolean estDefaut=(this.taFamilleTiers!=null)&& this.taFamilleTiers.getIdFamille()==taFamilleTiers.getIdFamille();

		this.getTaFamilleTierses().remove(taFamilleTiers);
		if(estDefaut && !this.getTaFamilleTierses().isEmpty())
			this.setTaFamilleTiers(this.getTaFamilleTierses().iterator().next());
		else if(this.getTaFamilleTierses().isEmpty()){
			this.setTaFamilleTiers(null);
		}
	}

	public void addLiens(TaLiens taLiens){
		if(!this.getTaLienses().contains(taLiens))
			this.getTaLienses().add(taLiens);
	}
	
	public void removeLiens(TaLiens taLiens){
		this.getTaLienses().remove(taLiens);
	}	

	
//	public void addSupportAbons(TaSupportAbon taSupport){
//		if(!this.getTaSupportAbons().contains(taSupport))
//			this.getTaSupportAbons().add(taSupport);
//	}
//	public void removeSupportAbons(TaSupportAbon taSupport){
//		this.getTaSupportAbons().remove(taSupport);
//	}
	
	public void removeEmail(TaEmail taEmail){
		boolean estDefaut=(this.taEmail!=null)&& this.taEmail.getIdEmail()==taEmail.getIdEmail();

		this.getTaEmails().remove(taEmail);
		if(estDefaut && !this.getTaEmails().isEmpty())
			this.setTaEmail(this.getTaEmails().iterator().next());
		else if(this.getTaEmails().isEmpty()){
			this.setTaEmail(null);
		}
	}
	
	public void addWeb(TaWeb taWeb){
		if(!this.getTaWebs().contains(taWeb))
			this.getTaWebs().add(taWeb);
		if(this.getTaWeb()==null)this.setTaWeb(taWeb);
	}
	public void removeWeb(TaWeb taWeb){
		boolean estDefaut=(this.taWeb!=null)&& this.taWeb.getIdWeb()==taWeb.getIdWeb();

		this.getTaWebs().remove(taWeb);
		if(estDefaut && !this.getTaWebs().isEmpty())
			this.setTaWeb(this.getTaWebs().iterator().next());
		else if(this.getTaWebs().isEmpty()){
			this.setTaWeb(null);
		}
		
	}
	
	public void addNote(TaNoteTiers taNote){
		if(!this.getTaNotes().contains(taNote))
			this.getTaNotes().add(taNote);
	}
	public void removeNote(TaNoteTiers taNote){
		this.getTaNotes().remove(taNote);
	}
	
	public void addParamCreeDocTiers(TaParamCreeDocTiers taParamCreeDocTiers){
		if(!this.getTaParamCreeDocTierses().contains(taParamCreeDocTiers))
			this.getTaParamCreeDocTierses().add(taParamCreeDocTiers);
		taParamCreeDocTiers.setTaTiers(this);
	}
	public void removeParamCreeDocTiers(TaParamCreeDocTiers taParamCreeDocTiers){
		this.getTaParamCreeDocTierses().remove(taParamCreeDocTiers);
		taParamCreeDocTiers.setTaTiers(null);
	}
	
	public void removeEntreprise(TaEntreprise taEntreprise){
		this.setTaEntreprise(null);
	}
	
	/**
	 * Renvoie vrai ssi le tiers possede des adresses dont le type est passe en parametre
	 * @param codeTypeAdresse
	 * @return
	 */
	public boolean aDesAdressesDuType(String codeTypeAdresse) {
		boolean trouve = false;
		for (TaAdresse taAdresse : taAdresses) {
			if(taAdresse.getTaTAdr()!=null && taAdresse.getTaTAdr().getCodeTAdr().equals(codeTypeAdresse)){
				trouve = true;
				break;
			}
		}
		return trouve;
	}

	/**
	 * Renvoie le premier telephone que possede le tiers dont le type est passe en parametre
	 * @param codeTypeTelephone
	 * @return
	 */
	public TaTelephone aDesTelephoneDuType(String codeTypeTelephone) {
		if(codeTypeTelephone == null)return null;
		for (TaTelephone taTelephone : taTelephones) {
			if(taTelephone.getTaTTel()!=null && taTelephone.getTaTTel().getCodeTTel().equals(codeTypeTelephone)){
				return taTelephone;
			}
		}
		return null;
	}

	@Transient
	public Boolean getAccepte() {
		return accepte;
	}

	public void setAccepte(Boolean accepte) {
		this.accepte = accepte;
	}

	public BigDecimal soldeTiersFacture(Date fin, EntityManager em,IDocumentTiers doc) {
		TaInfoEntrepriseDAO dao = new TaInfoEntrepriseDAO();
		TaInfoEntreprise infos = dao.findInstance();
		Date deb =infos.getDatedebRegInfoEntreprise();
		if(deb==null)deb=LibDate.stringToDate2("01/01/1759");
		return soldeTiersFacture(deb,fin,em,doc);
	}
	public BigDecimal soldeTiersFacture(Date deb,Date fin, EntityManager em,IDocumentTiers doc) {
		// TODO Auto-generated method stub
		BigDecimal resultat=BigDecimal.valueOf(0);
		List<TaFacture> listeDocument=null;
		try {
			String requete ="select f from TaFacture f join f.taTiers t where t.codeTiers like ? " +
					" and f.dateDocument between cast(? as date) and cast(? as date)";
			Query ejbQuery = em.createQuery(requete);
			ejbQuery.setParameter(1, this.getCodeTiers());
			ejbQuery.setParameter(2, deb, TemporalType.DATE);
			ejbQuery.setParameter(3, fin, TemporalType.DATE);
			listeDocument=ejbQuery.getResultList();
			if(listeDocument!=null){
				for (TaFacture taDoc : listeDocument) {
					if(taDoc!=null && taDoc.getIdDocument()!=doc.getIdDocument()){
						if(taDoc.getResteAReglerComplet()!=null)
							resultat=resultat.add(taDoc.getResteAReglerComplet());	
					}
				}
			}
			return resultat;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public BigDecimal soldeTiersFactureReglement(Date fin, EntityManager em,IDocumentTiers doc) {
		TaInfoEntrepriseDAO dao = new TaInfoEntrepriseDAO();
		TaInfoEntreprise infos = dao.findInstance();
		Date deb =infos.getDatedebRegInfoEntreprise();
		if(deb==null)deb=LibDate.stringToDate2("01/01/1759");
		return soldeTiersFactureReglement(deb,fin,em,doc);
	}
	public BigDecimal soldeTiersFactureReglement(Date deb,Date fin, EntityManager em,IDocumentTiers doc) {
		// TODO Auto-generated method stub
		BigDecimal resultat=BigDecimal.valueOf(0);
		List<TaFacture> listeDocument=null;
		try {
			String requete ="select f from TaFacture f join f.taTiers t where t.codeTiers like ? " +
					" and f.dateDocument <= cast(? as date) and cast(? as date)";
			Query ejbQuery = em.createQuery(requete);
			ejbQuery.setParameter(1, this.getCodeTiers());
			ejbQuery.setParameter(2, deb, TemporalType.DATE);
			ejbQuery.setParameter(3, fin, TemporalType.DATE);
			listeDocument=ejbQuery.getResultList();
			if(listeDocument!=null){
				for (TaFacture taDoc : listeDocument) {
					if(taDoc!=null && taDoc.getIdDocument()==doc.getIdDocument()){
						if(taDoc.getResteAReglerComplet()!=null && taDoc.getResteAReglerEcran()!=null)
							resultat=resultat.add(taDoc.getResteAReglerEcran().
									subtract(taDoc.getResteAReglerComplet()));
					}
				}
			}
			return resultat;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public BigDecimal soldeTiersReglement( Date fin, EntityManager em) {
		TaInfoEntrepriseDAO dao = new TaInfoEntrepriseDAO();
		TaInfoEntreprise infos = dao.findInstance();
		Date deb =infos.getDatedebRegInfoEntreprise();
		if(deb==null)deb=LibDate.stringToDate2("01/01/1759");
		return soldeTiersReglement(deb,fin,em);
	}
	public BigDecimal soldeTiersReglement( Date deb,Date fin, EntityManager em) {
		// TODO Auto-generated method stub
		BigDecimal resultat=BigDecimal.valueOf(0);
		List<TaReglement> listeDocument=null;
		try {
			String requete ="select f from TaReglement f join f.taTiers t where t.codeTiers like ? "+
					" and f.dateDocument <= cast(? as date) and cast(? as date)";
			Query ejbQuery = em.createQuery(requete);
			ejbQuery.setParameter(1, this.getCodeTiers());
			ejbQuery.setParameter(2, deb, TemporalType.DATE);
			ejbQuery.setParameter(3, fin, TemporalType.DATE);
			listeDocument=ejbQuery.getResultList();
			if(listeDocument!=null){
				for (TaReglement taDoc : listeDocument) {
					if(taDoc!=null ){
					    if(taDoc.calculAffectationTotale()!=null)
					    	resultat=resultat.add(taDoc.getNetTtcCalc().subtract(taDoc.calculAffectationTotale()));
					}	
				}
			}
			return resultat;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public BigDecimal soldeTiersAvoir( Date fin, EntityManager em) {
		TaInfoEntrepriseDAO dao = new TaInfoEntrepriseDAO();
		TaInfoEntreprise infos = dao.findInstance();
		Date deb =infos.getDatedebRegInfoEntreprise();
		if(deb==null)deb=LibDate.stringToDate2("01/01/1759");
		return soldeTiersAvoir(deb,fin,em);
	}
	public BigDecimal soldeTiersAvoir(Date deb, Date fin, EntityManager em) {
		// TODO Auto-generated method stub
		BigDecimal resultat=BigDecimal.valueOf(0);
		List<TaAvoir> listeDocument=null;
		try {
			String requete ="select f from TaAvoir f join f.taTiers t where t.codeTiers like ? " +
					" and f.dateDocument <= cast(? as date) and cast(? as date)";
			Query ejbQuery = em.createQuery(requete);
			ejbQuery.setParameter(1, this.getCodeTiers());
			ejbQuery.setParameter(2, deb, TemporalType.DATE);
			ejbQuery.setParameter(3, fin, TemporalType.DATE);
			listeDocument=ejbQuery.getResultList();
			if(listeDocument!=null){
				for (TaAvoir taDoc : listeDocument) {
					resultat=resultat.add(taDoc.getResteAReglerComplet());	
				}
			}
			return resultat;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public BigDecimal soldeTiersAcompte( Date fin, EntityManager em) {
		TaInfoEntrepriseDAO dao = new TaInfoEntrepriseDAO();
		TaInfoEntreprise infos = dao.findInstance();
		Date deb =infos.getDatedebRegInfoEntreprise();
		if(deb==null)deb=LibDate.stringToDate2("01/01/1759");
		return soldeTiersAcompte(deb,fin,em);
	}
	public BigDecimal soldeTiersAcompte(Date deb, Date fin, EntityManager em) {
		// TODO Auto-generated method stub
		BigDecimal resultat=BigDecimal.valueOf(0);
		List<TaAcompte> listeDocument=null;
		try {
			String requete ="select f from TaAcompte f join f.taTiers t where t.codeTiers like ? " +
					" and f.dateDocument <= cast(? as date) and cast(? as date)";
			Query ejbQuery = em.createQuery(requete);
			ejbQuery.setParameter(1, this.getCodeTiers());
			ejbQuery.setParameter(2, deb, TemporalType.DATE);
			ejbQuery.setParameter(3, fin, TemporalType.DATE);
			listeDocument=ejbQuery.getResultList();
			if(listeDocument!=null){
				for (TaAcompte taDoc : listeDocument) {
					resultat=resultat.add(taDoc.calculResteARegler());	
				}
			}
			return resultat;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<String> findNumeroFax() {
		return findNumeroFax("FAX");
	}
	
	public List<String> findNumeroFax(String codeTypeFAX) {
		List<String> liste = new ArrayList<String>();
		for (TaTelephone tel : taTelephones) {
			if(tel.getTaTTel()!=null && tel.getTaTTel().getCodeTTel().equals(codeTypeFAX)) {
				liste.add(tel.getNumeroTelephone());
			}
		}
		return liste;
	}

	@PostRemove
	public void afterDelete()throws Exception{

	}


	//@PrePersist
	//@PreUpdate
	public void beforePost ()throws Exception{
		this.setCodeTiers(codeTiers.toUpperCase());
		this.setCodeCompta(codeCompta.toUpperCase());
		this.setNomTiers(nomTiers.toUpperCase());
	}

}

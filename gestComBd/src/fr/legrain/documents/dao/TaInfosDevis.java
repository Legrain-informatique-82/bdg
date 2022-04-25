package fr.legrain.documents.dao;

// Generated Apr 7, 2009 3:27:23 PM by Hibernate Tools 3.2.0.CR1

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
import fr.legrain.gestCom.Module_Document.IInfosDocumentTiers;
import fr.legrain.gestCom.Module_Document.SWTInfosDocument;

/**
 * TaInfosDevis generated by hbm2java
 */
@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "TA_INFOS_DEVIS")
//@SequenceGenerator(name = "GEN_INFOS_DEVIS", sequenceName = "NUM_ID_INFOS_DEVIS", allocationSize = 1)
public class TaInfosDevis extends SWTInfosDocument implements java.io.Serializable, IInfosDocumentTiers {

	private int idInfosDocument;
//	private String version;
	private TaDevis taDocument;
	private String adresse1;
	private String adresse2;
	private String adresse3;
	private String codepostal;
	private String ville;
	private String pays;
	private String adresse1Liv;
	private String adresse2Liv;
	private String adresse3Liv;
	private String codepostalLiv;
	private String villeLiv;
	private String paysLiv;
	private String codeCompta;
	private String compte;
	private String nomTiers;
	private String prenomTiers;
	private String surnomTiers;
	private String codeTCivilite;
	private String codeTEntite;
	private String tvaIComCompl;
	private String codeCPaiement;
	private String libCPaiement;
	private Integer reportCPaiement;
	private Integer finMoisCPaiement;
	private String nomEntreprise;
	private String codeTTvaDoc;
//	private String quiCreeInfosDocument;
//	private Date quandCreeInfosDocument;
//	private String quiModifInfosDocument;
//	private Date quandModifInfosDocument;
//	private String ipAcces;
//	private Integer versionObj;
	@Transient
	static Logger logger = Logger.getLogger(TaInfosDevis.class.getName());
	public TaInfosDevis() {
	}

	public TaInfosDevis(int idInfosDevis) {
		this.idInfosDocument = idInfosDevis;
	}

	public TaInfosDevis(int idInfosDevis, TaDevis idDevis, String adresse1,
			String adresse2, String adresse3, String codepostal, String ville,
			String pays, String adresse1Liv, String adresse2Liv,
			String adresse3Liv, String codepostalLiv, String villeLiv,
			String paysLiv, String codeCompta, String compte, String nomTiers,
			String prenomTiers, String surnomTiers, String codeTCivilite,
			String codeTEntite, String tvaIComCompl, String codeCPaiement,
			String libCPaiement, Integer reportCPaiement,
			Integer finMoisCPaiement, String quiCreeInfosDevis,
			Date quandCreeInfosDevis, String quiModifInfosDevis,
			Date quandModifInfosDevis, String ipAcces, Integer versionObj,
			String nomEntreprise) {
		this.idInfosDocument = idInfosDevis;
		this.taDocument = idDevis;
		this.adresse1 = adresse1;
		this.adresse2 = adresse2;
		this.adresse3 = adresse3;
		this.codepostal = codepostal;
		this.ville = ville;
		this.pays = pays;
		this.adresse1Liv = adresse1Liv;
		this.adresse2Liv = adresse2Liv;
		this.adresse3Liv = adresse3Liv;
		this.codepostalLiv = codepostalLiv;
		this.villeLiv = villeLiv;
		this.paysLiv = paysLiv;
		this.codeCompta = codeCompta;
		this.compte = compte;
		this.nomTiers = nomTiers;
		this.prenomTiers = prenomTiers;
		this.surnomTiers = surnomTiers;
		this.codeTCivilite = codeTCivilite;
		this.codeTEntite = codeTEntite;
		this.tvaIComCompl = tvaIComCompl;
		this.codeCPaiement = codeCPaiement;
		this.libCPaiement = libCPaiement;
		this.reportCPaiement = reportCPaiement;
		this.finMoisCPaiement = finMoisCPaiement;
		this.quiCreeInfosDocument = quiCreeInfosDevis;
		this.quandCreeInfosDocument = quandCreeInfosDevis;
		this.quiModifInfosDocument = quiModifInfosDevis;
		this.quandModifInfosDocument = quandModifInfosDevis;
		this.ipAcces = ipAcces;
		this.versionObj = versionObj;
		this.nomEntreprise = nomEntreprise;
	}

//	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_INFOS_DEVIS")
//	@Column(name = "ID_INFOS_DOCUMENT", unique = true, nullable = false)
//	@LgrHibernateValidated(champ = "ID_INFOS_DOCUMENT",table = "TA_INFOS_DEVIS",clazz = TaInfosFacture.class)
//	public int getIdInfosDocument() {
//		return this.idInfosDocument;
//	}
//
//	public void setIdInfosDocument(int idInfosDevis) {
//		this.idInfosDocument = idInfosDevis;
//	}
//
//	@Column(name = "VERSION", length = 20)
//	public String getVersion() {
//		return this.version;
//	}
//
//	public void setVersion(String version) {
//		this.version = version;
//	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DOCUMENT",unique = true)
	@LgrHibernateValidated(champ = "ID_DOCUMENT",table = "TA_DEVIS",clazz = TaDevis.class)
	public TaDevis getTaDocument() {
		return this.taDocument;
	}

	public void setTaDocument(TaDevis idDevis) {
		this.taDocument = idDevis;
	}

	@Column(name = "ADRESSE1", length = 100)
	@LgrHibernateValidated(champ = "ADRESSE1",table = "TA_INFOS_DEVIS",clazz = TaInfosDevis.class)
	public String getAdresse1() {
		return this.adresse1;
	}

	public void setAdresse1(String adresse1) {
		this.adresse1 = adresse1;
	}

	@Column(name = "ADRESSE2", length = 100)
	@LgrHibernateValidated(champ = "ADRESSE2",table = "TA_INFOS_DEVIS",clazz = TaInfosDevis.class)
	public String getAdresse2() {
		return this.adresse2;
	}

	public void setAdresse2(String adresse2) {
		this.adresse2 = adresse2;
	}

	@Column(name = "ADRESSE3", length = 100)
	@LgrHibernateValidated(champ = "ADRESSE3",table = "TA_INFOS_DEVIS",clazz = TaInfosDevis.class)
	public String getAdresse3() {
		return this.adresse3;
	}

	public void setAdresse3(String adresse3) {
		this.adresse3 = adresse3;
	}

	@Column(name = "CODEPOSTAL", length = 25)
	@LgrHibernateValidated(champ = "CODEPOSTAL",table = "TA_INFOS_DEVIS",clazz = TaInfosDevis.class)
	public String getCodepostal() {
		return this.codepostal;
	}

	public void setCodepostal(String codepostal) {
		this.codepostal = codepostal;
	}

	@Column(name = "VILLE", length = 100)
	@LgrHibernateValidated(champ = "VILLE",table = "TA_INFOS_DEVIS",clazz = TaInfosDevis.class)
	public String getVille() {
		return this.ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	@Column(name = "PAYS", length = 100)
	@LgrHibernateValidated(champ = "PAYS",table = "TA_INFOS_DEVIS",clazz = TaInfosDevis.class)
	public String getPays() {
		return this.pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	@Column(name = "ADRESSE1_LIV", length = 100)
	@LgrHibernateValidated(champ = "ADRESSE1_LIV",table = "TA_INFOS_DEVIS",clazz = TaInfosDevis.class)
	public String getAdresse1Liv() {
		return this.adresse1Liv;
	}

	public void setAdresse1Liv(String adresse1Liv) {
		this.adresse1Liv = adresse1Liv;
	}

	@Column(name = "ADRESSE2_LIV", length = 100)
	@LgrHibernateValidated(champ = "ADRESSE2_LIV",table = "TA_INFOS_DEVIS",clazz = TaInfosDevis.class)
	public String getAdresse2Liv() {
		return this.adresse2Liv;
	}

	public void setAdresse2Liv(String adresse2Liv) {
		this.adresse2Liv = adresse2Liv;
	}

	@Column(name = "ADRESSE3_LIV", length = 100)
	@LgrHibernateValidated(champ = "ADRESSE3_LIV",table = "TA_INFOS_DEVIS",clazz = TaInfosDevis.class)
	public String getAdresse3Liv() {
		return this.adresse3Liv;
	}

	public void setAdresse3Liv(String adresse3Liv) {
		this.adresse3Liv = adresse3Liv;
	}

	@Column(name = "CODEPOSTAL_LIV", length = 25)
	@LgrHibernateValidated(champ = "CODEPOSTAL_LIV",table = "TA_INFOS_DEVIS",clazz = TaInfosDevis.class)
	public String getCodepostalLiv() {
		return this.codepostalLiv;
	}

	public void setCodepostalLiv(String codepostalLiv) {
		this.codepostalLiv = codepostalLiv;
	}

	@Column(name = "VILLE_LIV", length = 100)
	@LgrHibernateValidated(champ = "VILLE_LIV",table = "TA_INFOS_DEVIS",clazz = TaInfosDevis.class)
	public String getVilleLiv() {
		return this.villeLiv;
	}

	public void setVilleLiv(String villeLiv) {
		this.villeLiv = villeLiv;
	}

	@Column(name = "PAYS_LIV", length = 100)
	@LgrHibernateValidated(champ = "PAYS_LIV",table = "TA_INFOS_DEVIS",clazz = TaInfosDevis.class)
	public String getPaysLiv() {
		return this.paysLiv;
	}

	public void setPaysLiv(String paysLiv) {
		this.paysLiv = paysLiv;
	}

	@Column(name = "CODE_COMPTA", length = 8)
	@LgrHibernateValidated(champ = "CODE_COMPTA",table = "TA_INFOS_DEVIS",clazz = TaInfosDevis.class)
	public String getCodeCompta() {
		return this.codeCompta;
	}

	public void setCodeCompta(String codeCompta) {
		this.codeCompta = codeCompta;
	}

	@Column(name = "COMPTE", length = 8)
	@LgrHibernateValidated(champ = "COMPTE",table = "TA_INFOS_DEVIS",clazz = TaInfosDevis.class)
	public String getCompte() {
		return this.compte;
	}

	public void setCompte(String compte) {
		this.compte = compte;
	}

	@Column(name = "NOM_TIERS", length = 100)
	@LgrHibernateValidated(champ = "NOM_TIERS",table = "TA_INFOS_DEVIS",clazz = TaInfosDevis.class)
	public String getNomTiers() {
		return this.nomTiers;
	}

	public void setNomTiers(String nomTiers) {
		this.nomTiers = nomTiers;
	}

	@Column(name = "PRENOM_TIERS", length = 100)
	@LgrHibernateValidated(champ = "PRENOM_TIERS",table = "TA_INFOS_DEVIS",clazz = TaInfosDevis.class)
	public String getPrenomTiers() {
		return this.prenomTiers;
	}

	public void setPrenomTiers(String prenomTiers) {
		this.prenomTiers = prenomTiers;
	}

	@Column(name = "SURNOM_TIERS", length = 20)
	@LgrHibernateValidated(champ = "SURNOM_TIERS",table = "TA_INFOS_DEVIS",clazz = TaInfosDevis.class)
	public String getSurnomTiers() {
		return this.surnomTiers;
	}

	public void setSurnomTiers(String surnomTiers) {
		this.surnomTiers = surnomTiers;
	}

	@Column(name = "CODE_T_CIVILITE", length = 20)
	@LgrHibernateValidated(champ = "CODE_T_CIVILITE",table = "TA_INFOS_DEVIS",clazz = TaInfosDevis.class)
	public String getCodeTCivilite() {
		return this.codeTCivilite;
	}

	public void setCodeTCivilite(String codeTCivilite) {
		this.codeTCivilite = codeTCivilite;
	}

	@Column(name = "CODE_T_ENTITE", length = 20)
	@LgrHibernateValidated(champ = "CODE_T_ENTITE",table = "TA_INFOS_DEVIS",clazz = TaInfosDevis.class)
	public String getCodeTEntite() {
		return this.codeTEntite;
	}

	public void setCodeTEntite(String codeTEntite) {
		this.codeTEntite = codeTEntite;
	}

	@Column(name = "TVA_I_COM_COMPL", length = 50)
	@LgrHibernateValidated(champ = "TVA_I_COM_COMPL",table = "TA_INFOS_DEVIS",clazz = TaInfosDevis.class)
	public String getTvaIComCompl() {
		return this.tvaIComCompl;
	}

	public void setTvaIComCompl(String tvaIComCompl) {
		this.tvaIComCompl = tvaIComCompl;
	}

	@Column(name = "CODE_C_PAIEMENT", length = 20)
	@LgrHibernateValidated(champ = "CODE_C_PAIEMENT",table = "TA_INFOS_DEVIS",clazz = TaInfosDevis.class)
	public String getCodeCPaiement() {
		return this.codeCPaiement;
	}

	public void setCodeCPaiement(String codeCPaiement) {
		this.codeCPaiement = codeCPaiement;
	}

	@Column(name = "LIB_C_PAIEMENT")
	@LgrHibernateValidated(champ = "LIB_C_PAIEMENT",table = "TA_INFOS_DEVIS",clazz = TaInfosDevis.class)
	public String getLibCPaiement() {
		return this.libCPaiement;
	}

	public void setLibCPaiement(String libCPaiement) {
		this.libCPaiement = libCPaiement;
	}

	@Column(name = "REPORT_C_PAIEMENT")
	@LgrHibernateValidated(champ = "REPORT_C_PAIEMENT",table = "TA_INFOS_DEVIS",clazz = TaInfosDevis.class)
	public Integer getReportCPaiement() {
		return this.reportCPaiement;
	}

	public void setReportCPaiement(Integer reportCPaiement) {
		this.reportCPaiement = reportCPaiement;
	}

	@Column(name = "FIN_MOIS_C_PAIEMENT")
	@LgrHibernateValidated(champ = "FIN_MOIS_C_PAIEMENT",table = "TA_INFOS_DEVIS",clazz = TaInfosDevis.class)
	public Integer getFinMoisCPaiement() {
		return this.finMoisCPaiement;
	}

	public void setFinMoisCPaiement(Integer finMoisCPaiement) {
		this.finMoisCPaiement = finMoisCPaiement;
	}
	@Column(name = "NOM_ENTREPRISE")
	@LgrHibernateValidated(champ = "NOM_ENTREPRISE",table = "TA_INFOS_DEVIS",clazz = TaInfosDevis.class)
	public String getNomEntreprise() {
		return nomEntreprise;
	}

	public void setNomEntreprise(String nomEntreprise) {
		this.nomEntreprise = nomEntreprise;
	}
	
	@Column(name = "CODE_T_TVA_DOC")
	@LgrHibernateValidated(champ = "CODE_T_TVA_DOC",table = "TA_INFOS_DEVIS",clazz = TaInfosDevis.class)
	public String getCodeTTvaDoc() {
		return codeTTvaDoc;
	}

	public void setCodeTTvaDoc(String codeTTvaDoc) {
		this.codeTTvaDoc = codeTTvaDoc;
	}
	
//	@Column(name = "QUI_CREE_INFOS_DOCUMENT", length = 50)
//	public String getQuiCreeInfosDocument() {
//		return this.quiCreeInfosDocument;
//	}
//
//	public void setQuiCreeInfosDocument(String quiCreeInfosDevis) {
//		this.quiCreeInfosDocument = quiCreeInfosDevis;
//	}
//
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "QUAND_CREE_INFOS_DOCUMENT", length = 19)
//	public Date getQuandCreeInfosDocument() {
//		return this.quandCreeInfosDocument;
//	}
//
//	public void setQuandCreeInfosDocument(Date quandCreeInfosDevis) {
//		this.quandCreeInfosDocument = quandCreeInfosDevis;
//	}
//
//	@Column(name = "QUI_MODIF_INFOS_DOCUMENT", length = 50)
//	public String getQuiModifInfosDocument() {
//		return this.quiModifInfosDocument;
//	}
//
//	public void setQuiModifInfosDocument(String quiModifInfosDevis) {
//		this.quiModifInfosDocument = quiModifInfosDevis;
//	}
//
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "QUAND_MODIF_INFOS_DOCUMENT", length = 19)
//	public Date getQuandModifInfosDocument() {
//		return this.quandModifInfosDocument;
//	}
//
//	public void setQuandModifInfosDocument(Date quandModifInfosDevis) {
//		this.quandModifInfosDocument = quandModifInfosDevis;
//	}
//
//	@Column(name = "IP_ACCES", length = 50)
//	public String getIpAcces() {
//		return this.ipAcces;
//	}
//
//	public void setIpAcces(String ipAcces) {
//		this.ipAcces = ipAcces;
//	}

//	@Version
//	@Column(name = "VERSION_OBJ")
//	public Integer getVersionObj() {
//		return this.versionObj;
//	}
//
//	public void setVersionObj(Integer versionObj) {
//		this.versionObj = versionObj;
//	}
	/**
	 * Vide toutes les infos concernant le tiers
	 */
	public void clear() {
		setAdresse1(null);
		setAdresse2(null);
		setAdresse3(null);
		setCodepostal(null);
		setVille(null);
		setPays(null);
		setAdresse1Liv(null);
		setAdresse2Liv(null);
		setAdresse3Liv(null);
		setCodepostalLiv(null);
		setVilleLiv(null);
		setPaysLiv(null);
		setCodeCompta(null);
		setCompte(null);
		setNomTiers(null);
		setPrenomTiers(null);
		setSurnomTiers(null);
		setCodeTCivilite(null);
		setCodeTEntite(null);
		setTvaIComCompl(null);
		setCodeCPaiement(null);
		setLibCPaiement(null);
		setReportCPaiement(null);
		setFinMoisCPaiement(null);
		setNomEntreprise(null);
	}
	public TaInfosDevis clone() {
		TaInfosDevis infos = new TaInfosDevis();
		try {
//			infos = (TaInfosDevis)super.clone();
			infos.setIdInfosDocument(0);
			infos.setVersion(version);
			infos.setTaDocument(taDocument);
			infos.setAdresse1(adresse1);
			infos.setAdresse2(adresse2);
			infos.setAdresse3(adresse3);
			infos.setCodepostal(codepostal);
			infos.setVille(ville);
			infos.setPays(pays);
			infos.setAdresse1Liv(adresse1Liv);
			infos.setAdresse2Liv(adresse2Liv);
			infos.setAdresse3Liv(adresse3Liv);
			infos.setCodepostalLiv(codepostalLiv);
			infos.setVilleLiv(villeLiv);
			infos.setPaysLiv(paysLiv);
			infos.setCodeCompta(codeCompta);
			infos.setCompte(compte);
			infos.setNomTiers(nomTiers);
			infos.setPrenomTiers(prenomTiers);
			infos.setSurnomTiers(surnomTiers);
			infos.setCodeTCivilite(codeTCivilite);
			infos.setCodeTEntite(codeTEntite);
			infos.setTvaIComCompl(tvaIComCompl);
			infos.setCodeCPaiement(codeCPaiement);
			infos.setLibCPaiement(libCPaiement);
			infos.setReportCPaiement(reportCPaiement);
			infos.setFinMoisCPaiement(finMoisCPaiement);
			infos.setQuiCreeInfosDocument(quiCreeInfosDocument);
			infos.setQuandCreeInfosDocument(quandCreeInfosDocument);
			infos.setQuiModifInfosDocument(quiModifInfosDocument);
			infos.setQuandModifInfosDocument(quandModifInfosDocument);
			infos.setIpAcces(ipAcces);
			infos.setVersionObj(versionObj);
			infos.setNomEntreprise(nomEntreprise);
		} catch(Exception cnse) {
			logger.error("",cnse);
		}
		// on renvoie le clone
		return infos;
	}
}

package fr.legrain.saisieCaisse.dao;

// Generated 2 juin 2009 14:13:00 by Hibernate Tools 3.2.4.GA

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
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import fr.legrain.documents.dao.TaFacture;
import fr.legrain.documents.dao.TaTPaiement;
import fr.legrain.gestCom.Appli.LgrHibernateValidated;
import fr.legrain.tiers.dao.TaEntreprise;

/**
 * TaOperation generated by hbm2java
 */
@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "TA_OPERATION")
@SequenceGenerator(name = "GEN_OPERATION", sequenceName = "NUM_ID_OPERATION", allocationSize = 1)
public class TaOperation implements java.io.Serializable {

	private int idOperation;
	private String version;
	private TaTPaiement taTPaiement;
	private TaTOperation taTOperation;
	private TaEtablissement taEtablissement;
	private BigDecimal montantOperation;
	private Date dateOperation;
	private String liblOperation;
	private String compte;
	private BigDecimal tva;
	private Integer export = 0;
	private String quiCreeOperation;
	private Date quandCreeOperation;
	private String quiModifOperation;
	private Date quandModifOperation;
	private String ipAcces;
	private Integer versionObj;

	public TaOperation() {
	}

	public TaOperation(int idOperation) {
		this.idOperation = idOperation;
	}

	public TaOperation(String codeTPaiement, String codeTOperation,
			BigDecimal montantOperation) {
		super();
		this.taTPaiement.setCodeTPaiement(codeTPaiement);
		this.taTOperation.setCodeTOperation(codeTOperation);
		this.montantOperation = montantOperation;
	}

	
	public TaOperation(int idOperation, TaTPaiement taTPaiement,
			TaTOperation taTOperation, BigDecimal montantOperation,
			Date dateOperation, String liblOperation, String compte,Integer export) {
		this.idOperation = idOperation;
		this.taTPaiement = taTPaiement;
		this.taTOperation = taTOperation;
		this.montantOperation = montantOperation;
		this.dateOperation = dateOperation;
		this.liblOperation = liblOperation;
		this.compte = compte;
		this.export = export;
	}
	public TaOperation(Date dateOperation, TaTPaiement taTPaiement,
			TaTOperation taTOperation, BigDecimal montantOperation,
			 String liblOperation, String compte,Integer export) {
		this.taTPaiement = taTPaiement;
		this.taTOperation = taTOperation;
		this.montantOperation = montantOperation;
		this.dateOperation = dateOperation;
		this.liblOperation = liblOperation;
		this.compte = compte;
		this.export = export;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_OPERATION")
	@Column(name = "ID_OPERATION", unique = true, nullable = false)
	@LgrHibernateValidated(champ = "ID_OPERATION",table = "TA_OPERATION",clazz = TaOperation.class)
	public int getIdOperation() {
		return this.idOperation;
	}

	public void setIdOperation(int idOperation) {
		this.idOperation = idOperation;
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
	@LgrHibernateValidated(champ = "ID_T_PAIEMENT",table = "TA_OPERATION",clazz = TaTPaiement.class)
	public TaTPaiement getTaTPaiement() {
		return this.taTPaiement;
	}

	public void setTaTPaiement(TaTPaiement taTPaiement) {
		this.taTPaiement = taTPaiement;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ETABLISSEMENT")
	@LgrHibernateValidated(champ = "ID_ETABLISSEMENT",table = "TA_OPERATION",clazz = TaEtablissement.class)
	public TaEtablissement getTaEtablissement() {
		return this.taEtablissement;
	}

	public void setTaEtablissement(TaEtablissement taEtablissement) {
		this.taEtablissement = taEtablissement;
	}
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "ID_T_OPERATION")
	@LgrHibernateValidated(champ = "ID_T_OPERATION",table = "TA_OPERATION",clazz = TaTOperation.class)
	public TaTOperation getTaTOperation() {
		return this.taTOperation;
	}

	public void setTaTOperation(TaTOperation taTOperation) {
		this.taTOperation = taTOperation;
	}

	@Column(name = "MONTANT_OPERATION", precision = 15)
	@LgrHibernateValidated(champ = "MONTANT_OPERATION",table = "TA_OPERATION",clazz = TaOperation.class)
	public BigDecimal getMontantOperation() {
		return this.montantOperation;
	}

	public void setMontantOperation(BigDecimal montantOperation) {
		this.montantOperation = montantOperation;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_OPERATION", length = 19)
	@LgrHibernateValidated(champ = "DATE_OPERATION",table = "TA_OPERATION",clazz = TaOperation.class)
	public Date getDateOperation() {
		return this.dateOperation;
	}

	public void setDateOperation(Date dateOperation) {
		this.dateOperation = dateOperation;
	}

	@Column(name = "LIBL_OPERATION")
	@LgrHibernateValidated(champ = "LIBL_OPERATION",table = "TA_OPERATION",clazz = TaOperation.class)
	public String getLiblOperation() {
		return this.liblOperation;
	}

	public void setLiblOperation(String liblOperation) {
		this.liblOperation = liblOperation;
	}
	
	@Column(name = "COMPTE")
	@LgrHibernateValidated(champ = "COMPTE",table = "TA_OPERATION",clazz = TaOperation.class)
	public String getCompte() {
		return this.compte;
	}

	public void setCompte(String compte) {
		this.compte = compte;
	}
	
	@Column(name = "TVA", precision = 15)
	@LgrHibernateValidated(champ = "TVA",table = "TA_OPERATION",clazz = TaOperation.class)
	public BigDecimal getTva() {
		return this.tva;
	}

	public void setTva(BigDecimal tva) {
		this.tva = tva;
	}
	
	@Column(name = "EXPORT")
	@LgrHibernateValidated(champ = "EXPORT",table = "TA_OPERATION",clazz = TaOperation.class)
	public Integer getExport() {
		return this.export;
	}

	public void setExport(Integer export) {
		this.export = export;
	}
	
	@Column(name = "QUI_CREE_OPERATION", length = 50)
	@LgrHibernateValidated(champ = "ID_OPERATION",table = "TA_OPERATION",clazz = TaOperation.class)
	public String getQuiCreeOperation() {
		return this.quiCreeOperation;
	}

	public void setQuiCreeOperation(String quiCreeOperation) {
		this.quiCreeOperation = quiCreeOperation;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "QUAND_CREE_OPERATION", length = 19)
	public Date getQuandCreeOperation() {
		return this.quandCreeOperation;
	}

	public void setQuandCreeOperation(Date quandCreeOperation) {
		this.quandCreeOperation = quandCreeOperation;
	}

	@Column(name = "QUI_MODIF_OPERATION", length = 50)
	public String getQuiModifOperation() {
		return this.quiModifOperation;
	}

	public void setQuiModifOperation(String quiModifOperation) {
		this.quiModifOperation = quiModifOperation;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "QUAND_MODIF_OPERATION", length = 19)
	public Date getQuandModifOperation() {
		return this.quandModifOperation;
	}

	public void setQuandModifOperation(Date quandModifOperation) {
		this.quandModifOperation = quandModifOperation;
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

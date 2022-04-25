package fr.legrain.documents.dao;

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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import fr.legrain.gestCom.Appli.LgrHibernateValidated;

/**
 * TaReponse
 */
@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "TA_REPONSE", uniqueConstraints = @UniqueConstraint(columnNames = "CODE_REPONSE"))
@SequenceGenerator(name = "GEN_REPONSE", sequenceName = "NUM_ID_REPONSE", allocationSize = 1)
public class TaReponse implements java.io.Serializable {

	private int idReponse;
	private String version;
	private String codeReponse;
	private String libReponse;
	private Integer action;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;
	private Integer versionObj;

	public TaReponse() {
	}

	public TaReponse(int idReponse) {
		this.idReponse = idReponse;
	}

	public TaReponse(int idReponse, String codeReponse, String libReponse,
			String quiCree, Date quandCree, String quiModif,
			Date quandModif, String ipAcces, Integer versionObj,
			Set<TaComDoc> taComDocs) {
		this.idReponse = idReponse;
		this.codeReponse = codeReponse;
		this.libReponse = libReponse;
		this.quiCree = quiCree;
		this.quandCree = quandCree;
		this.quiModif = quiModif;
		this.quandModif = quandModif;
		this.ipAcces = ipAcces;
		this.versionObj = versionObj;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_REPONSE")
	@Column(name = "ID_REPONSE", unique = true, nullable = false)
	@LgrHibernateValidated(champ = "ID_REPONSE",table = "TA_REPONSE",clazz = TaReponse.class)
	public int getIdReponse() {
		return this.idReponse;
	}

	public void setIdReponse(int idTDoc) {
		this.idReponse = idTDoc;
	}

	@Column(name = "VERSION", length = 20)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name = "CODE_REPONSE", unique = true, length = 50)
	@LgrHibernateValidated(champ = "CODE_REPONSE",table = "TA_REPONSE",clazz = TaReponse.class)
	public String getCodeReponse() {
		return this.codeReponse;
	}

	public void setCodeReponse(String codeReponse) {
		this.codeReponse = codeReponse;
	}

	@Column(name = "LIBELLE_REPONSE")
	@LgrHibernateValidated(champ = "LIBELLE_REPONSE",table = "TA_REPONSE",clazz = TaReponse.class)
	public String getLibReponse() {
		return this.libReponse;
	}

	public void setLibReponse(String libReponse) {
		this.libReponse = libReponse;
	}
	
	
	@Column(name = "ACTION")
	@LgrHibernateValidated(champ = "ACTION",table = "TA_REPONSE",clazz = TaReponse.class)
	public Integer getAction() {
		return action;
	}

	public void setAction(Integer action) {
		this.action = action;
	}
	
	@Column(name = "QUI_CREE", length = 50)
	public String getQuiCree() {
		return this.quiCree;
	}

	public void setQuiCree(String quiCreeTDoc) {
		this.quiCree = quiCreeTDoc;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "QUAND_CREE", length = 19)
	public Date getQuandCree() {
		return this.quandCree;
	}

	public void setQuandCree(Date quandCreeTDoc) {
		this.quandCree = quandCreeTDoc;
	}

	@Column(name = "QUI_MODIF", length = 50)
	public String getQuiModif() {
		return this.quiModif;
	}

	public void setQuiModif(String quiModifTDoc) {
		this.quiModif = quiModifTDoc;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "QUAND_MODIF", length = 19)
	public Date getQuandModif() {
		return this.quandModif;
	}

	public void setQuandModif(Date quandModifTDoc) {
		this.quandModif = quandModifTDoc;
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

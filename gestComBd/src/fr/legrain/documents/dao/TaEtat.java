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
 * TaEtat
 */
@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "TA_ETAT", uniqueConstraints = @UniqueConstraint(columnNames = "CODE_ETAT"))
@SequenceGenerator(name = "GEN_ETAT", sequenceName = "NUM_ID_ETAT", allocationSize = 1)
public class TaEtat implements java.io.Serializable {

	private int idEtat;
	private String version;
	private String codeEtat;
	private String libEtat;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;
	private Integer versionObj;

	public TaEtat() {
	}

	public TaEtat(int idEtat) {
		this.idEtat = idEtat;
	}

	public TaEtat(int idEtat, String codeEtat, String libEtat,
			String quiCree, Date quandCree, String quiModif,
			Date quandModif, String ipAcces, Integer versionObj,
			Set<TaComDoc> taComDocs) {
		this.idEtat = idEtat;
		this.codeEtat = codeEtat;
		this.libEtat = libEtat;
		this.quiCree = quiCree;
		this.quandCree = quandCree;
		this.quiModif = quiModif;
		this.quandModif = quandModif;
		this.ipAcces = ipAcces;
		this.versionObj = versionObj;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_ETAT")
	@Column(name = "ID_ETAT", unique = true, nullable = false)
	@LgrHibernateValidated(champ = "ID_ETAT",table = "TA_ETAT",clazz = TaEtat.class)
	public int getIdEtat() {
		return this.idEtat;
	}

	public void setIdEtat(int idTDoc) {
		this.idEtat = idTDoc;
	}

	@Column(name = "VERSION", length = 20)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name = "CODE_ETAT", unique = true, length = 50)
	@LgrHibernateValidated(champ = "CODE_ETAT",table = "TA_ETAT",clazz = TaEtat.class)
	public String getCodeEtat() {
		return this.codeEtat;
	}

	public void setCodeEtat(String codeEtat) {
		this.codeEtat = codeEtat;
	}

	@Column(name = "LIBELLE_ETAT")
	@LgrHibernateValidated(champ = "LIBELLE_ETAT",table = "TA_ETAT",clazz = TaEtat.class)
	public String getLibEtat() {
		return this.libEtat;
	}

	public void setLibEtat(String libEtat) {
		this.libEtat = libEtat;
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

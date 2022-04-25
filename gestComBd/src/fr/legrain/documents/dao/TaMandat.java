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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
import fr.legrain.tiers.dao.TaTiers;

/**
 * TaMandat
 */
@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "TA_MANDAT", uniqueConstraints = @UniqueConstraint(columnNames = "CODE_MANDAT"))
@SequenceGenerator(name = "GEN_MANDAT", sequenceName = "NUM_ID_MANDAT", allocationSize = 1)
public class TaMandat implements java.io.Serializable {

	private int idMandat;
	private String version;
	private String codeMandat;
	private TaTiers taTiers;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;
	private Integer versionObj;

	public TaMandat() {
	}

	public TaMandat(int idMandat) {
		this.idMandat = idMandat;
	}

	public TaMandat(int idMandat, String codeMandat, TaTiers taTiers,
			String quiCree, Date quandCree, String quiModif,
			Date quandModif, String ipAcces, Integer versionObj,
			Set<TaComDoc> taComDocs) {
		this.idMandat = idMandat;
		this.codeMandat = codeMandat;
		this.taTiers = taTiers;
		this.quiCree = quiCree;
		this.quandCree = quandCree;
		this.quiModif = quiModif;
		this.quandModif = quandModif;
		this.ipAcces = ipAcces;
		this.versionObj = versionObj;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_MANDAT")
	@Column(name = "ID_MANDAT", unique = true, nullable = false)
	@LgrHibernateValidated(champ = "ID_MANDAT",table = "TA_MANDAT",clazz = TaMandat.class)
	public int getIdMandat() {
		return this.idMandat;
	}

	public void setIdMandat(int idTDoc) {
		this.idMandat = idTDoc;
	}

	@Column(name = "VERSION", length = 20)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name = "CODE_MANDAT", unique = true, length = 50)
	@LgrHibernateValidated(champ = "CODE_MANDAT",table = "TA_MANDAT",clazz = TaMandat.class)
	public String getCodeMandat() {
		return this.codeMandat;
	}

	public void setCodeMandat(String codeMandat) {
		this.codeMandat = codeMandat;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIERS")
	@LgrHibernateValidated(champ = "ID_TIERS",table = "TA_MANDAT",clazz = TaMandat.class)
	public TaTiers getTaTiers() {
		return this.taTiers;
	}

	public void setTaTiers(TaTiers taTiers) {
		this.taTiers = taTiers;
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

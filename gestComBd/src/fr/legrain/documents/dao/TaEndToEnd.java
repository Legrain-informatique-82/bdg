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

import fr.legrain.articles.dao.TaPrix;
import fr.legrain.gestCom.Appli.LgrHibernateValidated;

/**
 * TaEndToEnd
 */
@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "TA_END_TO_END", uniqueConstraints = @UniqueConstraint(columnNames = "CODE_END_TO_END"))
@SequenceGenerator(name = "GEN_END_TO_END", sequenceName = "NUM_ID_END_TO_END", allocationSize = 1)
public class TaEndToEnd implements java.io.Serializable {

	private int idEndToEnd;
	private String version;
	private String codeEndToEnd;
	private TaMandat taMandat;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;
	private Integer versionObj;

	public TaEndToEnd() {
	}

	public TaEndToEnd(int idEndToEnd) {
		this.idEndToEnd = idEndToEnd;
	}

	public TaEndToEnd(int idEndToEnd, String codeEndToEnd, TaMandat taMandat,
			String quiCree, Date quandCree, String quiModif,
			Date quandModif, String ipAcces, Integer versionObj,
			Set<TaComDoc> taComDocs) {
		this.idEndToEnd = idEndToEnd;
		this.codeEndToEnd = codeEndToEnd;
		this.taMandat = taMandat;
		this.quiCree = quiCree;
		this.quandCree = quandCree;
		this.quiModif = quiModif;
		this.quandModif = quandModif;
		this.ipAcces = ipAcces;
		this.versionObj = versionObj;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_END_TO_END")
	@Column(name = "ID_END_TO_END", unique = true, nullable = false)
	@LgrHibernateValidated(champ = "ID_END_TO_END",table = "TA_END_TO_END",clazz = TaEndToEnd.class)
	public int getIdEndToEnd() {
		return this.idEndToEnd;
	}

	public void setIdEndToEnd(int idTDoc) {
		this.idEndToEnd = idTDoc;
	}

	@Column(name = "VERSION", length = 20)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name = "CODE_END_TO_END", unique = true, length = 50)
	@LgrHibernateValidated(champ = "CODE_END_TO_END",table = "TA_END_TO_END",clazz = TaEndToEnd.class)
	public String getCodeEndToEnd() {
		return this.codeEndToEnd;
	}

	public void setCodeEndToEnd(String codeEndToEnd) {
		this.codeEndToEnd = codeEndToEnd;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_MANDAT")
	@LgrHibernateValidated(champ = "ID_MANDAT",table = "TA_END_TO_END",clazz = TaEndToEnd.class)
	public TaMandat getTaMandat() {
		return this.taMandat;
	}

	public void setTaMandat(TaMandat taMandat) {
		this.taMandat = taMandat;
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

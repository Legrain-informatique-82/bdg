package fr.legrain.gestCom.Module_Document;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import fr.legrain.dossier.dao.TaVersion;
import fr.legrain.dossier.dao.TaVersionDAO;

@Entity
@SequenceGenerator(name = "GEN_INFOS_FACTURE", sequenceName = "NUM_ID_INFOS_DOCUMENT", allocationSize = 1)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class SWTInfosDocument  {
	
	protected int idInfosDocument;
	protected Integer versionObj;
	protected String quiCreeInfosDocument;
	protected Date quandCreeInfosDocument;
	protected String quiModifInfosDocument;
	protected Date quandModifInfosDocument;
	protected String ipAcces;
	protected String version;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_INFOS_FACTURE")
	@Column(name = "ID_INFOS_DOCUMENT", unique = true, nullable = false)
	public int getIdInfosDocument() {
		return this.idInfosDocument;
	}

	public void setIdInfosDocument(int idInfosFacture) {
		this.idInfosDocument = idInfosFacture;
	}
	
	@Version
	@Column(name = "VERSION_OBJ")
	public Integer getVersionObj() {
		return this.versionObj;
	}
	
	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	
	@Column(name = "QUI_CREE_INFOS_DOCUMENT", length = 50)
	public String getQuiCreeInfosDocument() {
		return this.quiCreeInfosDocument;
	}

	public void setQuiCreeInfosDocument(String quiCreeInfosAcompte) {
		this.quiCreeInfosDocument = quiCreeInfosAcompte;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "QUAND_CREE_INFOS_DOCUMENT", length = 19)
	public Date getQuandCreeInfosDocument() {
		return this.quandCreeInfosDocument;
	}

	public void setQuandCreeInfosDocument(Date quandCreeInfosAcompte) {
		this.quandCreeInfosDocument = quandCreeInfosAcompte;
	}

	@Column(name = "QUI_MODIF_INFOS_DOCUMENT", length = 50)
	public String getQuiModifInfosDocument() {
		return this.quiModifInfosDocument;
	}

	public void setQuiModifInfosDocument(String quiModifInfosAcompte) {
		this.quiModifInfosDocument = quiModifInfosAcompte;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "QUAND_MODIF_INFOS_DOCUMENT", length = 19)
	public Date getQuandModifInfosDocument() {
		return this.quandModifInfosDocument;
	}

	public void setQuandModifInfosDocument(Date quandModifInfosAcompte) {
		this.quandModifInfosDocument = quandModifInfosAcompte;
	}

	@Column(name = "IP_ACCES", length = 50)
	public String getIpAcces() {
		return this.ipAcces;
	}

	public void setIpAcces(String ipAcces) {
		this.ipAcces = ipAcces;
	}
	@Column(name = "VERSION", length = 20)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
//	@PrePersist
	public void beforeInsert()throws Exception{
		TaVersionDAO daoVersion =new TaVersionDAO();
		TaVersion version= daoVersion.findInstance();
		this.setQuiCreeInfosDocument("");
		this.setQuandCreeInfosDocument(new Date());
		this.setQuiModifInfosDocument("");
		this.setQuandModifInfosDocument(new Date());
		this.setIpAcces("");
		this.setVersion(version.getNumVersion());
	}
	
//	@PreUpdate
	public void beforeUpdate()throws Exception{
		TaVersionDAO daoVersion =new TaVersionDAO();
		TaVersion version= daoVersion.findInstance();
		this.setQuiModifInfosDocument("");
		this.setQuandModifInfosDocument(new Date());
		this.setIpAcces("");
		this.setVersion(version.getNumVersion());
	}
}

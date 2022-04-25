package fr.legrain.documents.dao;

// Generated Apr 7, 2009 3:27:23 PM by Hibernate Tools 3.2.0.CR1

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * TaLModele generated by hbm2java
 */
@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "TA_L_MODELE")
@SequenceGenerator(name = "GEN_L_MODELE", sequenceName = "NUM_ID_L_MODELE", allocationSize = 1)
public class TaLModele implements java.io.Serializable {

	private int idLModele;
	private String version;
	private String quiCreeLModele;
	private Date quandCreeLModele;
	private String quiModifLModele;
	private Date quandModifLModele;
	private String ipAcces;
	private Integer versionObj;
	@Transient
	private boolean legrain = false;

	public TaLModele() {
	}

	public TaLModele(int idLModele) {
		this.idLModele = idLModele;
	}

	public TaLModele(int idLModele, String quiCreeLModele,
			Date quandCreeLModele, String quiModifLModele,
			Date quandModifLModele, String ipAcces, Integer versionObj) {
		this.idLModele = idLModele;
		this.quiCreeLModele = quiCreeLModele;
		this.quandCreeLModele = quandCreeLModele;
		this.quiModifLModele = quiModifLModele;
		this.quandModifLModele = quandModifLModele;
		this.ipAcces = ipAcces;
		this.versionObj = versionObj;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_L_MODELE")
	@Column(name = "ID_L_MODELE", unique = true, nullable = false)
	public int getIdLModele() {
		return this.idLModele;
	}

	public void setIdLModele(int idLModele) {
		this.idLModele = idLModele;
	}

	@Column(name = "VERSION", length = 20)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name = "QUI_CREE_L_MODELE", length = 50)
	public String getQuiCreeLModele() {
		return this.quiCreeLModele;
	}

	public void setQuiCreeLModele(String quiCreeLModele) {
		this.quiCreeLModele = quiCreeLModele;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "QUAND_CREE_L_MODELE", length = 19)
	public Date getQuandCreeLModele() {
		return this.quandCreeLModele;
	}

	public void setQuandCreeLModele(Date quandCreeLModele) {
		this.quandCreeLModele = quandCreeLModele;
	}

	@Column(name = "QUI_MODIF_L_MODELE", length = 50)
	public String getQuiModifLModele() {
		return this.quiModifLModele;
	}

	public void setQuiModifLModele(String quiModifLModele) {
		this.quiModifLModele = quiModifLModele;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "QUAND_MODIF_L_MODELE", length = 19)
	public Date getQuandModifLModele() {
		return this.quandModifLModele;
	}

	public void setQuandModifLModele(Date quandModifLModele) {
		this.quandModifLModele = quandModifLModele;
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

	public void setLegrain(boolean legrain) {
		this.legrain = legrain;
	}

}
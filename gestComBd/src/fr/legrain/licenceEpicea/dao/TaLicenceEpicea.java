package fr.legrain.licenceEpicea.dao;

// Generated 11 août 2009 15:52:23 by Hibernate Tools 3.2.4.GA

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import fr.legrain.SupportAbon.dao.TaLicenceLogiciel;
import fr.legrain.SupportAbon.dao.TaSupportAbon;
import fr.legrain.SupportAbonLegrain.dao.TaUtilisateur;
import fr.legrain.gestCom.Appli.LgrHibernateValidated;
import fr.legrain.tiers.dao.TaFamilleTiers;


/**
 * TaWlgr generated by hbm2java
 */
@Entity
//@SecondaryTable(name="TA_LICENCE_EPICEA")
@Table(name = "TA_LICENCE_EPICEA")
//@DiscriminatorValue("EPICEA")
public  class TaLicenceEpicea extends TaLicenceLogiciel implements java.io.Serializable {

	private TaUtilisateur taUtilisateur;
    private TaFamilleTiers groupe;
	private Integer telechargement=0;
//	private Integer versionObj;
	
	public TaLicenceEpicea() {
	}

	
public TaLicenceEpicea(TaUtilisateur taUtilisateur) {
	super();
	this.setClassName(this.getClass().getName());
	this.taUtilisateur = taUtilisateur;
}

	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_UTILISATEUR")
	@LgrHibernateValidated(champ = "ID_UTILISATEUR",table = "TA_UTILISATEUR",clazz = TaUtilisateur.class)
	public TaUtilisateur getTaUtilisateur() {
		return taUtilisateur;
	}

	public void setTaUtilisateur(TaUtilisateur taUtilisateur) {
		this.taUtilisateur = taUtilisateur;
	}

	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "GROUPE")
	@LgrHibernateValidated(champ = "ID_FAMILLE",table = "TA_FAMILLE_TIERS",clazz = TaFamilleTiers.class)
	public TaFamilleTiers getGroupe() {
		return groupe;
	}

	public void setGroupe(TaFamilleTiers groupe) {
		this.groupe = groupe;
	}

	@Column(name = "TELECHARGEMENT")
	@LgrHibernateValidated(champ = "TELECHARGEMENT",table = "TA_LICENCE_EPICEA",clazz = TaLicenceEpicea.class)
	public Integer getTelechargement() {
		return telechargement;
	}


	public void setTelechargement(Integer telechargement) {
		this.telechargement = telechargement;
	}
	

//	@Version
//	@Column(name = "VERSION_OBJ", precision = 15)
//	public Integer getVersionObj() {
//		return this.versionObj;
//	}
//
//	public void setVersionObj(Integer versionObj) {
//		this.versionObj = versionObj;
//	}
}
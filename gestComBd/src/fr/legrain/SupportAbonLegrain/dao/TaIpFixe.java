package fr.legrain.SupportAbonLegrain.dao;

// Generated 11 août 2009 15:52:23 by Hibernate Tools 3.2.4.GA

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

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.gestCom.Appli.LgrHibernateValidated;
import fr.legrain.tiers.dao.TaTiers;


/**
 * TaWlgr generated by hbm2java
 */
@Entity
@Table(name = "TA_IP_FIXE")
@SequenceGenerator(name = "GEN_IP_FIXE", sequenceName = "NUM_ID_IP_FIXE", allocationSize = 1)

public class TaIpFixe implements java.io.Serializable {
    
	private Integer idIpFixe;
	private String IpFixe;


	
	public TaIpFixe() {
	}

	public TaIpFixe(Integer idIpFixe) {
		this.idIpFixe = idIpFixe;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_IP_FIXE")
	@Column(name = "ID_IP_FIXE", unique = true)
	public Integer getIdIpFixe() {
		return this.idIpFixe;
	}

	public void setIdIpFixe(Integer idIpFixe) {
		this.idIpFixe = idIpFixe;
	}

	@Column(name = "IP_FIXE")
	@LgrHibernateValidated(champ = "IP_FIXE",table = "TA_IP_FIXE",clazz = TaIpFixe.class)
	public String getIpFixe() {
		return this.IpFixe;
	}

	public void setIpFixe(String IpFixe) {
		this.IpFixe = IpFixe;
	}
	
	
	
}

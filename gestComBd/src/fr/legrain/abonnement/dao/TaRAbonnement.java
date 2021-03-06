package fr.legrain.abonnement.dao;

// Generated 11 août 2009 15:52:23 by Hibernate Tools 3.2.4.GA

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

import fr.legrain.SupportAbon.dao.TaSupportAbon;
import fr.legrain.articles.dao.TaArticle;
import fr.legrain.gestCom.Appli.LgrHibernateValidated;
import fr.legrain.tiers.dao.TaTiers;


/**
 * TaWlgr generated by hbm2java
 */
@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "TA_R_ABONNEMENT")
@SequenceGenerator(name = "GEN_R_ABONNEMENT", sequenceName = "NUM_ID_R_ABONNEMENT", allocationSize = 1)

public class TaRAbonnement implements java.io.Serializable {

   
	private Integer idRAbonnement;
	private TaAbonnement taAbonnement;
	 private TaSupportAbon taSupportAbon;

	
	public TaRAbonnement() {
	}

	public TaRAbonnement(Integer idRAbonnement) {
		this.idRAbonnement = idRAbonnement;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_R_ABONNEMENT")
	@Column(name = "ID_R_ABONNEMENT", unique = true)
	public Integer getIdRAbonnement() {
		return this.idRAbonnement;
	}

	public void setIdRAbonnement(Integer idRAbonnement) {
		this.idRAbonnement = idRAbonnement;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_SUPPORT_ABON")
	@LgrHibernateValidated(champ = "ID_SUPPORT_ABON",table = "TA_SUPPORT_ABON",clazz = TaSupportAbon.class)
	public TaSupportAbon getTaSupportAbon() {
		return this.taSupportAbon;
	}

	public void setTaSupportAbon(TaSupportAbon taSupportAbon) {
		this.taSupportAbon = taSupportAbon;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ABONNEMENT")
	@LgrHibernateValidated(champ = "ID_ABONNEMENT",table = "TA_ABONNEMENT",clazz = TaAbonnement.class)
	public TaAbonnement getTaAbonnement() {
		return this.taAbonnement;
	}

	public void setTaAbonnement(TaAbonnement taAbonnement) {
		this.taAbonnement = taAbonnement;
	}
	

	
	
}

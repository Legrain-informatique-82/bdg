package fr.legrain.generationDocumentLGR.dao;

// Generated 11 août 2009 15:52:23 by Hibernate Tools 3.2.4.GA

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "TA_RECAP_WLGR")
public class RecapMaintenances implements java.io.Serializable {

	private Integer id;

	  private String annee;
	  private BigDecimal nbPrevu;
	  private BigDecimal prevueHt;
	  private BigDecimal reelHt;
	  private BigDecimal bonus;
	  private BigDecimal cogere;

	public RecapMaintenances(Integer id) {
			this.id = id;
		}


	@Id
	@Column(name = "ID", unique = true)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	@Column(name = "ANNEE", length = 4)
	public String getAnnee() {
		return annee;
	}


	public void setAnnee(String annee) {
		this.annee = annee;
	}

	@Column(name = "PREVUE_HT", precision = 15)
	public BigDecimal getPrevueHt() {
		return prevueHt;
	}


	public void setPrevueHt(BigDecimal prevueHt) {
		this.prevueHt = prevueHt;
	}

	@Column(name = "REEL_HT", precision = 15)
	public BigDecimal getReelHt() {
		return reelHt;
	}


	public void setReelHt(BigDecimal reelHt) {
		this.reelHt = reelHt;
	}

	@Column(name = "NB_PREVU" , precision = 15)
	public BigDecimal getNbPrevu() {
		return nbPrevu;
	}


	public void setNbPrevu(BigDecimal nbPrevu) {
		this.nbPrevu = nbPrevu;
	}

	@Column(name = "BONUS", precision = 15)
	public BigDecimal getBonus() {
		return bonus;
	}


	public void setBonus(BigDecimal bonus) {
		this.bonus = bonus;
	}

	@Column(name = "COGERE", precision = 15)
	public BigDecimal getCogere() {
		return cogere;
	}


	public void setCogere(BigDecimal cogere) {
		this.cogere = cogere;
	}
	
	

	


}

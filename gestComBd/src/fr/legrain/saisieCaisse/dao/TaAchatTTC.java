package fr.legrain.saisieCaisse.dao;

// Generated 4 juin 2009 10:11:38 by Hibernate Tools 3.2.4.GA

import java.math.BigDecimal;
import java.util.Date;

/**
 * TaStock generated by hbm2java
 */

public class TaAchatTTC implements java.io.Serializable {


	private Date dateAchatTTC;
	private BigDecimal montant;
	private BigDecimal tva;


	public TaAchatTTC() {
	}


	public BigDecimal getMontant() {
		return montant;
	}


	public void setMontant(BigDecimal montant) {
		this.montant = montant;
	}


	public BigDecimal getTva() {
		return tva;
	}


	public void setTva(BigDecimal tva) {
		this.tva = tva;
	}


	public Date getDateAchatTTC() {
		return dateAchatTTC;
	}


	public void setDateAchatTTC(Date dateAchatTTC) {
		this.dateAchatTTC = dateAchatTTC;
	}


	public TaAchatTTC(Date dateAchatTTC, BigDecimal montant, BigDecimal tva) {
		super();
		this.dateAchatTTC = dateAchatTTC;
		this.montant = montant;
		this.tva = tva;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dateAchatTTC == null) ? 0 : dateAchatTTC.hashCode());
		result = prime * result + ((montant == null) ? 0 : montant.hashCode());
		result = prime * result + ((tva == null) ? 0 : tva.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaAchatTTC other = (TaAchatTTC) obj;
		if (dateAchatTTC == null) {
			if (other.dateAchatTTC != null)
				return false;
		} else if (!dateAchatTTC.equals(other.dateAchatTTC))
			return false;
		if (montant == null) {
			if (other.montant != null)
				return false;
		} else if (!montant.equals(other.montant))
			return false;
		if (tva == null) {
			if (other.tva != null)
				return false;
		} else if (!tva.equals(other.tva))
			return false;
		return true;
	}






}

package fr.legrain.document.divers;

import fr.legrain.lib.data.ModelObject;

public class SWTChampWidth extends ModelObject {
	private String reference;
	private String designation;
	private String qte;
	private String u1;
	private String PU;
	private String remise;
	private String montant;
	private String tva;



	
	public SWTChampWidth(){}
	


	public SWTChampWidth(String reference, String designation, String qte,
			String u1, String pU, String remise, String montant, String tva) {
		super();
		this.reference = reference;
		this.designation = designation;
		this.qte = qte;
		this.u1 = u1;
		PU = pU;
		this.remise = remise;
		this.montant = montant;
		this.tva = tva;
	}



	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getQte() {
		return qte;
	}

	public void setQte(String qte) {
		this.qte = qte;
	}

	public String getU1() {
		return u1;
	}

	public void setU1(String u1) {
		this.u1 = u1;
	}

	public String getPU() {
		return PU;
	}

	public void setPU(String pU) {
		PU = pU;
	}

	public String getRemise() {
		return remise;
	}

	public void setRemise(String remise) {
		this.remise = remise;
	}

	public String getMontant() {
		return montant;
	}

	public void setMontant(String montant) {
		this.montant = montant;
	}

	public String getTva() {
		return tva;
	}

	public void setTva(String tva) {
		this.tva = tva;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((PU == null) ? 0 : PU.hashCode());
		result = prime * result
				+ ((designation == null) ? 0 : designation.hashCode());
		result = prime * result + ((montant == null) ? 0 : montant.hashCode());
		result = prime * result + ((qte == null) ? 0 : qte.hashCode());
		result = prime * result
				+ ((reference == null) ? 0 : reference.hashCode());
		result = prime * result + ((remise == null) ? 0 : remise.hashCode());
		result = prime * result + ((tva == null) ? 0 : tva.hashCode());
		result = prime * result + ((u1 == null) ? 0 : u1.hashCode());
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
		SWTChampWidth other = (SWTChampWidth) obj;
		if (PU == null) {
			if (other.PU != null)
				return false;
		} else if (!PU.equals(other.PU))
			return false;
		if (designation == null) {
			if (other.designation != null)
				return false;
		} else if (!designation.equals(other.designation))
			return false;
		if (montant == null) {
			if (other.montant != null)
				return false;
		} else if (!montant.equals(other.montant))
			return false;
		if (qte == null) {
			if (other.qte != null)
				return false;
		} else if (!qte.equals(other.qte))
			return false;
		if (reference == null) {
			if (other.reference != null)
				return false;
		} else if (!reference.equals(other.reference))
			return false;
		if (remise == null) {
			if (other.remise != null)
				return false;
		} else if (!remise.equals(other.remise))
			return false;
		if (tva == null) {
			if (other.tva != null)
				return false;
		} else if (!tva.equals(other.tva))
			return false;
		if (u1 == null) {
			if (other.u1 != null)
				return false;
		} else if (!u1.equals(other.u1))
			return false;
		return true;
	}


	
}


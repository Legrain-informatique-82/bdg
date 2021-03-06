package fr.legrain.gestCom.Module_Document;

import fr.legrain.lib.data.ModelObject;

// Generated Mar 1, 2007 11:26:10 AM by Hibernate Tools 3.2.0.b9

/**
 * SWTTPaiement generated by hbm2java
 */
public class SWTTPaiement extends ModelObject {

	private Integer idTPaiement;
	private String codeTPaiement;
	private String libTPaiement;
	private String compte;
	private Integer reportTPaiement;
	private Integer finMoisTPaiement;

	public SWTTPaiement() {
	}

	
	public static SWTTPaiement copy(SWTTPaiement swtTPaiement){
		SWTTPaiement swtTPaiementLoc = new SWTTPaiement();
		swtTPaiementLoc.setCodeTPaiement(swtTPaiement.codeTPaiement);
		swtTPaiementLoc.setIdTPaiement(swtTPaiement.idTPaiement);
		swtTPaiementLoc.setLibTPaiement(swtTPaiement.libTPaiement);
		return swtTPaiementLoc;
	}
	
	public void setSWTTPaiement(SWTTPaiement swtTPaiement){
		this.idTPaiement = swtTPaiement.idTPaiement;
		this.codeTPaiement = swtTPaiement.codeTPaiement;
		this.libTPaiement = swtTPaiement.libTPaiement;
	}
	
	
	public SWTTPaiement(Integer ID_T_PAIEMENT, String CODE_T_PAIEMENT,
			String LIB_T_PAIEMENT, String IP_ACCES) {
		this.idTPaiement = ID_T_PAIEMENT;
		this.codeTPaiement = CODE_T_PAIEMENT;
		this.libTPaiement = LIB_T_PAIEMENT;
	}
	public Integer getIdTPaiement() {
		return this.idTPaiement;
	}

	public void setIdTPaiement(Integer idTPaiement) {
		firePropertyChange("idTPaiement", this.idTPaiement, this.idTPaiement = idTPaiement);
	}

	public String getCodeTPaiement() {
		return this.codeTPaiement;
	}

	public void setCodeTPaiement(String codeTPaiement) {
		firePropertyChange("codeTPaiement", this.codeTPaiement, this.codeTPaiement = codeTPaiement);
	}

	public String getLibTPaiement() {
		return this.libTPaiement;
	}

	public void setLibTPaiement(String libTPaiement) {
		firePropertyChange("libTPaiement", this.libTPaiement, this.libTPaiement = libTPaiement);
	}

	public String getCompte() {
		return compte;
	}


	public void setCompte(String compte) {
		firePropertyChange("compte", this.compte, this.compte = compte);
	}

	public Integer getReportTPaiement() {
		return this.reportTPaiement;
	}

	public void setReportTPaiement(Integer REPORT_C_PAIEMENT) {
		firePropertyChange("reportTPaiement", this.reportTPaiement, this.reportTPaiement = REPORT_C_PAIEMENT);
	}

	public Integer getFinMoisTPaiement() {
		return this.finMoisTPaiement;
	}

	public void setFinMoisTPaiement(Integer FIN_MOIS_C_PAIEMENT) {
		firePropertyChange("finMoisTPaiement", this.finMoisTPaiement, this.finMoisTPaiement = FIN_MOIS_C_PAIEMENT);
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codeTPaiement == null) ? 0 : codeTPaiement.hashCode());
		result = prime * result + ((compte == null) ? 0 : compte.hashCode());
		result = prime
				* result
				+ ((finMoisTPaiement == null) ? 0 : finMoisTPaiement.hashCode());
		result = prime * result
				+ ((idTPaiement == null) ? 0 : idTPaiement.hashCode());
		result = prime * result
				+ ((libTPaiement == null) ? 0 : libTPaiement.hashCode());
		result = prime * result
				+ ((reportTPaiement == null) ? 0 : reportTPaiement.hashCode());
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
		SWTTPaiement other = (SWTTPaiement) obj;
		if (codeTPaiement == null) {
			if (other.codeTPaiement != null)
				return false;
		} else if (!codeTPaiement.equals(other.codeTPaiement))
			return false;
		if (compte == null) {
			if (other.compte != null)
				return false;
		} else if (!compte.equals(other.compte))
			return false;
		if (finMoisTPaiement == null) {
			if (other.finMoisTPaiement != null)
				return false;
		} else if (!finMoisTPaiement.equals(other.finMoisTPaiement))
			return false;
		if (idTPaiement == null) {
			if (other.idTPaiement != null)
				return false;
		} else if (!idTPaiement.equals(other.idTPaiement))
			return false;
		if (libTPaiement == null) {
			if (other.libTPaiement != null)
				return false;
		} else if (!libTPaiement.equals(other.libTPaiement))
			return false;
		if (reportTPaiement == null) {
			if (other.reportTPaiement != null)
				return false;
		} else if (!reportTPaiement.equals(other.reportTPaiement))
			return false;
		return true;
	}





}

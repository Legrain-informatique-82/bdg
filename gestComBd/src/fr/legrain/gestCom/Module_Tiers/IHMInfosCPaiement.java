package fr.legrain.gestCom.Module_Tiers;

import fr.legrain.lib.data.ModelObject;

// Generated Mar 1, 2007 11:26:10 AM by Hibernate Tools 3.2.0.b9

/**
 * SWTCPaiement generated by hbm2java
 */
public class IHMInfosCPaiement extends ModelObject {

	private String codeCPaiement;
	private String libCPaiement;
	private Integer reportCPaiement;
	private Integer finMoisCPaiement;

	public IHMInfosCPaiement() {
	}

	public IHMInfosCPaiement(String CODE_C_PAIEMENT,
			String LIB_C_PAIEMENT, Integer REPORT_C_PAIEMENT,
			Integer FIN_MOIS_C_PAIEMENT) {
		this.codeCPaiement = CODE_C_PAIEMENT;
		this.libCPaiement = LIB_C_PAIEMENT;
		this.reportCPaiement = REPORT_C_PAIEMENT;
		this.finMoisCPaiement = FIN_MOIS_C_PAIEMENT;
	}
	
	public void setIHMInfosCPaiement(IHMInfosCPaiement swtCPaiement) {
		setCodeCPaiement(swtCPaiement.codeCPaiement);
		setLibCPaiement(swtCPaiement.libCPaiement);
		setReportCPaiement(swtCPaiement.reportCPaiement);
		setFinMoisCPaiement(swtCPaiement.finMoisCPaiement);
	}
	
	public void setSWTCPaiement(SWTCPaiement swtCPaiement) {
		setCodeCPaiement(swtCPaiement.getCodeCPaiement());
		setLibCPaiement(swtCPaiement.getCodeCPaiement());
		setReportCPaiement(swtCPaiement.getReportCPaiement());
		setFinMoisCPaiement(swtCPaiement.getFinMoisCPaiement());
	}

	
	public static IHMInfosCPaiement copy(IHMInfosCPaiement swtCPaiement){
		IHMInfosCPaiement ihmInfosCPaiement = new IHMInfosCPaiement();
		ihmInfosCPaiement.setCodeCPaiement(swtCPaiement.getCodeCPaiement());
		ihmInfosCPaiement.setLibCPaiement(swtCPaiement.getLibCPaiement());
		ihmInfosCPaiement.setReportCPaiement(swtCPaiement.getReportCPaiement());
		ihmInfosCPaiement.setFinMoisCPaiement(swtCPaiement.getFinMoisCPaiement());
		return ihmInfosCPaiement;
	}

	public String getCodeCPaiement() {
		return this.codeCPaiement;
	}

	public void setCodeCPaiement(String CODE_C_PAIEMENT) {
		firePropertyChange("codeCPaiement", this.codeCPaiement, this.codeCPaiement = CODE_C_PAIEMENT);
	}

	public String getLibCPaiement() {
		return this.libCPaiement;
	}

	public void setLibCPaiement(String LIB_C_PAIEMENT) {
		firePropertyChange("libCPaiement", this.libCPaiement, this.libCPaiement = LIB_C_PAIEMENT);
	}

	public Integer getReportCPaiement() {
		return this.reportCPaiement;
	}

	public void setReportCPaiement(Integer REPORT_C_PAIEMENT) {
		firePropertyChange("reportCPaiement", this.reportCPaiement, this.reportCPaiement = REPORT_C_PAIEMENT);
	}

	public Integer getFinMoisCPaiement() {
		return this.finMoisCPaiement;
	}

	public void setFinMoisCPaiement(Integer FIN_MOIS_C_PAIEMENT) {
		firePropertyChange("finMoisCPaiement", this.finMoisCPaiement, this.finMoisCPaiement = FIN_MOIS_C_PAIEMENT);
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof IHMInfosCPaiement))
			return false;
		IHMInfosCPaiement castOther = (IHMInfosCPaiement) other;

		return (((this.getCodeCPaiement() == castOther
						.getCodeCPaiement()) || (this.getCodeCPaiement() != null
						&& castOther.getCodeCPaiement() != null && this
						.getCodeCPaiement().equals(
								castOther.getCodeCPaiement())))
				&& ((this.getLibCPaiement() == castOther.getLibCPaiement()) || (this
						.getLibCPaiement() != null
						&& castOther.getLibCPaiement() != null && this
						.getLibCPaiement().equals(
								castOther.getLibCPaiement())))
				&& ((this.getReportCPaiement() == castOther
						.getReportCPaiement()) || (this
						.getReportCPaiement() != null
						&& castOther.getReportCPaiement() != null && this
						.getReportCPaiement().equals(
								castOther.getReportCPaiement())))
				&& ((this.getFinMoisCPaiement() == castOther
						.getFinMoisCPaiement()) || (this
						.getFinMoisCPaiement() != null
						&& castOther.getFinMoisCPaiement() != null && this
						.getFinMoisCPaiement().equals(
								castOther.getFinMoisCPaiement()))));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getCodeCPaiement() == null ? 0 : this.getCodeCPaiement()
						.hashCode());
		result = 37
				* result
				+ (getLibCPaiement() == null ? 0 : this.getLibCPaiement()
						.hashCode());
		result = 37
				* result
				+ (getReportCPaiement() == null ? 0 : this
						.getReportCPaiement().hashCode());
		result = 37
				* result
				+ (getFinMoisCPaiement() == null ? 0 : this
						.getFinMoisCPaiement().hashCode());
		return result;
	}

	
	public boolean estVide(){
		return this.equals(new IHMInfosCPaiement());
	}
}

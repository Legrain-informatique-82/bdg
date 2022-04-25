package fr.legrain.gestCom.Module_Document;

import fr.legrain.lib.data.ModelObject;

// Generated 20 mars 2007 09:51:19 by Hibernate Tools 3.2.0.b9

/**
 * IHMAdresse generated by hbm2java
 */
public class IHMAdresse extends ModelObject {

	private Integer idAdresse;
	private String adresse1Adresse;
	private String adresse2Adresse;
	private String adresse3Adresse;
	private String codepostalAdresse;
	private String villeAdresse;
	private String paysAdresse;
	private String codeTAdr;
	private Integer idTAdr;
	private Integer idTiers;

	public IHMAdresse() {
	}

	public IHMAdresse(Integer ID_ADRESSE, String ADRESSE1_ADRESSE,
			String ADRESSE2_ADRESSE, String ADRESSE3_ADRESSE,
			String CODEPOSTAL_ADRESSE, String VILLE_ADRESSE,
			String PAYS_ADRESSE, String CODE_T_ADR, Integer ID_T_ADR,
			Integer ID_TIERS) {
		this.idAdresse = ID_ADRESSE;
		this.adresse1Adresse = ADRESSE1_ADRESSE;
		this.adresse2Adresse = ADRESSE2_ADRESSE;
		this.adresse3Adresse = ADRESSE3_ADRESSE;
		this.codepostalAdresse = CODEPOSTAL_ADRESSE;
		this.villeAdresse = VILLE_ADRESSE;
		this.paysAdresse = PAYS_ADRESSE;
		this.codeTAdr = CODE_T_ADR;
		this.idTAdr = ID_T_ADR;
		this.idTiers = ID_TIERS;
	}
	
	public void setIHMAdresse(IHMAdresse ihmAdresse) {
		this.idAdresse = ihmAdresse.idAdresse;
		this.adresse1Adresse = ihmAdresse.adresse1Adresse;
		this.adresse2Adresse = ihmAdresse.adresse2Adresse;
		this.adresse3Adresse = ihmAdresse.adresse3Adresse;
		this.codepostalAdresse = ihmAdresse.codepostalAdresse;
		this.villeAdresse = ihmAdresse.villeAdresse;
		this.paysAdresse = ihmAdresse.paysAdresse;
		this.codeTAdr = ihmAdresse.codeTAdr;
		this.idTAdr = ihmAdresse.idTAdr;
		this.idTiers = ihmAdresse.idTiers;

	}
	
	public static IHMAdresse copy(IHMAdresse ihmAdresse){
		IHMAdresse ihmAdresseLoc = new IHMAdresse();
		ihmAdresseLoc.setIdAdresse(ihmAdresse.idAdresse);
		ihmAdresseLoc.setAdresse1Adresse(ihmAdresse.adresse1Adresse);
		ihmAdresseLoc.setAdresse2Adresse(ihmAdresse.adresse2Adresse);
		ihmAdresseLoc.setAdresse3Adresse(ihmAdresse.adresse3Adresse);
		ihmAdresseLoc.setCodepostalAdresse(ihmAdresse.codepostalAdresse);
		ihmAdresseLoc.setVilleAdresse(ihmAdresse.villeAdresse);
		ihmAdresseLoc.setPaysAdresse(ihmAdresse.paysAdresse);
		ihmAdresseLoc.setCodeTAdr(ihmAdresse.codeTAdr);
		ihmAdresseLoc.setIdTAdr(ihmAdresse.idTAdr);
		ihmAdresseLoc.setIdTiers(ihmAdresse.idTiers);
		return ihmAdresseLoc;
	}


	public Integer getIdAdresse() {
		return this.idAdresse;
	}

	public void setIdAdresse(Integer ID_ADRESSE) {
		firePropertyChange("idAdresse", this.idAdresse, this.idAdresse = ID_ADRESSE);
	}

	public String getAdresse1Adresse() {
		return this.adresse1Adresse;
	}

	public void setAdresse1Adresse(String ADRESSE1_ADRESSE) {
		firePropertyChange("adresse1Adresse", this.adresse1Adresse, this.adresse1Adresse = ADRESSE1_ADRESSE);
	}

	public String getAdresse2Adresse() {
		return this.adresse2Adresse;
	}

	public void setAdresse2Adresse(String ADRESSE2_ADRESSE) {
		firePropertyChange("adresse2Adresse", this.adresse2Adresse, this.adresse2Adresse = ADRESSE2_ADRESSE);
	}

	public String getAdresse3Adresse() {
		return this.adresse3Adresse;
	}

	public void setAdresse3Adresse(String ADRESSE3_ADRESSE) {
		firePropertyChange("adresse3Adresse", this.adresse3Adresse, this.adresse3Adresse = ADRESSE3_ADRESSE);
	}

	public String getCodepostalAdresse() {
		return this.codepostalAdresse;
	}

	public void setCodepostalAdresse(String CODEPOSTAL_ADRESSE) {
		firePropertyChange("codepostalAdresse", this.codepostalAdresse, this.codepostalAdresse = CODEPOSTAL_ADRESSE);
	}

	public String getVilleAdresse() {
		return this.villeAdresse;
	}

	public void setVilleAdresse(String VILLE_ADRESSE) {
		firePropertyChange("villeAdresse", this.villeAdresse, this.villeAdresse = VILLE_ADRESSE);
	}

	public String getPaysAdresse() {
		return this.paysAdresse;
	}

	public void setPaysAdresse(String PAYS_ADRESSE) {
		firePropertyChange("paysAdresse", this.paysAdresse, this.paysAdresse = PAYS_ADRESSE);
	}

	public String getCodeTAdr() {
		return this.codeTAdr;
	}

	public void setCodeTAdr(String CODE_T_ADR) {
		firePropertyChange("codeTAdr", this.codeTAdr, this.codeTAdr = CODE_T_ADR);
	}

	public Integer getIdTAdr() {
		return this.idTAdr;
	}

	public void setIdTAdr(Integer ID_T_ADR) {
		firePropertyChange("idTAdr", this.idTAdr, this.idTAdr = ID_T_ADR);
	}

	public Integer getIdTiers() {
		return this.idTiers;
	}

	public void setIdTiers(Integer ID_TIERS) {
		firePropertyChange("idTiers", this.idTiers, this.idTiers = ID_TIERS);
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof IHMAdresse))
			return false;
		IHMAdresse castOther = (IHMAdresse) other;

		return ((this.getIdAdresse() == castOther.getIdAdresse()) || (this
				.getIdAdresse() != null
				&& castOther.getIdAdresse() != null && this.getIdAdresse()
				.equals(castOther.getIdAdresse())))
				&& ((this.getAdresse1Adresse() == castOther
						.getAdresse1Adresse()) || (this.getAdresse1Adresse() != null
						&& castOther.getAdresse1Adresse() != null && this
						.getAdresse1Adresse().equals(
								castOther.getAdresse1Adresse())))
				&& ((this.getAdresse2Adresse() == castOther
						.getAdresse2Adresse()) || (this.getAdresse2Adresse() != null
						&& castOther.getAdresse2Adresse() != null && this
						.getAdresse2Adresse().equals(
								castOther.getAdresse2Adresse())))
				&& ((this.getAdresse3Adresse() == castOther
						.getAdresse3Adresse()) || (this.getAdresse3Adresse() != null
						&& castOther.getAdresse3Adresse() != null && this
						.getAdresse3Adresse().equals(
								castOther.getAdresse3Adresse())))
				&& ((this.getCodepostalAdresse() == castOther
						.getCodepostalAdresse()) || (this
						.getCodepostalAdresse() != null
						&& castOther.getCodepostalAdresse() != null && this
						.getCodepostalAdresse().equals(
								castOther.getCodepostalAdresse())))
				&& ((this.getVilleAdresse() == castOther.getVilleAdresse()) || (this
						.getVilleAdresse() != null
						&& castOther.getVilleAdresse() != null && this
						.getVilleAdresse()
						.equals(castOther.getVilleAdresse())))
				&& ((this.getPaysAdresse() == castOther.getPaysAdresse()) || (this
						.getPaysAdresse() != null
						&& castOther.getPaysAdresse() != null && this
						.getPaysAdresse().equals(castOther.getPaysAdresse())))
				&& ((this.getCodeTAdr() == castOther.getCodeTAdr()) || (this
						.getCodeTAdr() != null
						&& castOther.getCodeTAdr() != null && this
						.getCodeTAdr().equals(castOther.getCodeTAdr())))
				&& ((this.getIdTAdr() == castOther.getIdTAdr()) || (this
						.getIdTAdr() != null
						&& castOther.getIdTAdr() != null && this
						.getIdTAdr().equals(castOther.getIdTAdr())))
				&& ((this.getIdTiers() == castOther.getIdTiers()) || (this
						.getIdTiers() != null
						&& castOther.getIdTiers() != null && this
						.getIdTiers().equals(castOther.getIdTiers())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getIdAdresse() == null ? 0 : this.getIdAdresse()
						.hashCode());
		result = 37
				* result
				+ (getAdresse1Adresse() == null ? 0 : this
						.getAdresse1Adresse().hashCode());
		result = 37
				* result
				+ (getAdresse2Adresse() == null ? 0 : this
						.getAdresse2Adresse().hashCode());
		result = 37
				* result
				+ (getAdresse3Adresse() == null ? 0 : this
						.getAdresse3Adresse().hashCode());
		result = 37
				* result
				+ (getCodepostalAdresse() == null ? 0 : this
						.getCodepostalAdresse().hashCode());
		result = 37
				* result
				+ (getVilleAdresse() == null ? 0 : this.getVilleAdresse()
						.hashCode());
		result = 37
				* result
				+ (getPaysAdresse() == null ? 0 : this.getPaysAdresse()
						.hashCode());
		result = 37
				* result
				+ (getCodeTAdr() == null ? 0 : this.getCodeTAdr()
						.hashCode());
		result = 37 * result
				+ (getIdTAdr() == null ? 0 : this.getIdTAdr().hashCode());
		result = 37 * result
				+ (getIdTiers() == null ? 0 : this.getIdTiers().hashCode());
		return result;
	}

}

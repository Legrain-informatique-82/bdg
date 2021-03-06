package fr.legrain.gestCom.Module_Tiers;

import fr.legrain.lib.data.ModelObject;

// Generated Mar 1, 2007 11:26:10 AM by Hibernate Tools 3.2.0.b9

/**
 * SWTAdresse generated by hbm2java
 */
public class SWTAdresse extends ModelObject {

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

	public SWTAdresse() {
	}

	public void setSWTAdresse(SWTAdresse swtAdresse) {
		this.idAdresse = swtAdresse.idAdresse;
		this.adresse1Adresse = swtAdresse.adresse1Adresse;
		this.adresse2Adresse = swtAdresse.adresse2Adresse;
		this.adresse3Adresse = swtAdresse.adresse3Adresse;
		this.codepostalAdresse = swtAdresse.codepostalAdresse;
		this.villeAdresse = swtAdresse.villeAdresse;
		this.paysAdresse = swtAdresse.paysAdresse;
		this.codeTAdr = swtAdresse.codeTAdr;
		this.idTAdr = swtAdresse.idTAdr;
		this.idTiers = swtAdresse.idTiers;
	}

	
	public static SWTAdresse copy(SWTAdresse swtAdresse){
		SWTAdresse swtAdresseLoc = new SWTAdresse();
		swtAdresseLoc.setIdAdresse(swtAdresse.getIdAdresse());                //1
		swtAdresseLoc.setIdTAdr(swtAdresse.getIdTAdr());                //1
		swtAdresseLoc.setIdTiers(swtAdresse.getIdTiers());                //1
		swtAdresseLoc.setAdresse1Adresse(swtAdresse.getAdresse1Adresse());                //1
		swtAdresseLoc.setAdresse2Adresse(swtAdresse.getAdresse2Adresse());                //1
		swtAdresseLoc.setAdresse3Adresse(swtAdresse.getAdresse3Adresse());                //1
		swtAdresseLoc.setCodepostalAdresse(swtAdresse.getCodepostalAdresse());                //1
		swtAdresseLoc.setVilleAdresse(swtAdresse.getVilleAdresse());                //1
		swtAdresseLoc.setPaysAdresse(swtAdresse.getPaysAdresse());                //1
		swtAdresseLoc.setCodeTAdr(swtAdresse.getCodeTAdr());                //1
		return swtAdresseLoc;
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
		if (!(other instanceof SWTAdresse))
			return false;
		SWTAdresse castOther = (SWTAdresse) other;

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
	
	public SWTAdresse setSWTAdresse(IHMAdresseInfosFacturation adr) {
		setAdresse1Adresse(adr.getAdresse1Adresse());
		setAdresse2Adresse(adr.getAdresse2Adresse());
		setAdresse3Adresse(adr.getAdresse3Adresse());
		setCodepostalAdresse(adr.getCodepostalAdresse());
		setVilleAdresse(adr.getVilleAdresse());
		setPaysAdresse(adr.getPaysAdresse());
		return this;
	}
	
	public SWTAdresse setSWTAdresse(IHMAdresseInfosLivraison adr) {
		setAdresse1Adresse(adr.getAdresse1AdresseLiv());
		setAdresse2Adresse(adr.getAdresse2AdresseLiv());
		setAdresse3Adresse(adr.getAdresse3AdresseLiv());
		setCodepostalAdresse(adr.getCodepostalAdresseLiv());
		setVilleAdresse(adr.getVilleAdresseLiv());
		setPaysAdresse(adr.getPaysAdresseLiv());
		return this;
	}

}

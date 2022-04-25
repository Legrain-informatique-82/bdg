package fr.legrain.gestCom.Module_Tiers;

import fr.legrain.lib.data.ModelObject;


public class SWTInfoJuridique extends ModelObject {

	private Integer idInfoJuridique;
	private String rcsInfoJuridique;
	private String siretInfoJuridique;
	private String capitalInfoJuridique;
	private String apeInfoJuridique;

	public SWTInfoJuridique() {
	}

	public void setSWTAdresse(SWTInfoJuridique swtInfoJuridique) {
		this.idInfoJuridique = swtInfoJuridique.idInfoJuridique;
		this.rcsInfoJuridique = swtInfoJuridique.rcsInfoJuridique;
		this.siretInfoJuridique = swtInfoJuridique.siretInfoJuridique;
		this.capitalInfoJuridique = swtInfoJuridique.capitalInfoJuridique;
		this.apeInfoJuridique = swtInfoJuridique.apeInfoJuridique;
	}

	public static SWTInfoJuridique copy(SWTInfoJuridique swtInfoJuridique){
		SWTInfoJuridique swtInfoJuridiqueLoc = new SWTInfoJuridique();
		swtInfoJuridiqueLoc.setIdInfoJuridique(swtInfoJuridique.getIdInfoJuridique());           
		swtInfoJuridiqueLoc.setRcsInfoJuridique(swtInfoJuridique.getRcsInfoJuridique());          
		swtInfoJuridiqueLoc.setSiretInfoJuridique(swtInfoJuridique.getSiretInfoJuridique());               
		swtInfoJuridiqueLoc.setCapitalInfoJuridique(swtInfoJuridique.getCapitalInfoJuridique());       
		swtInfoJuridiqueLoc.setApeInfoJuridique(swtInfoJuridique.getApeInfoJuridique());            
		return swtInfoJuridiqueLoc;
	}
	
	public Integer getIdInfoJuridique() {
		return idInfoJuridique;
	}

	public void setIdInfoJuridique(Integer idInfoJuridique) {
		firePropertyChange("idInfoJuridique", this.idInfoJuridique, this.idInfoJuridique = idInfoJuridique);
	}

	public String getRcsInfoJuridique() {
		return rcsInfoJuridique;
	}

	public void setRcsInfoJuridique(String rcsInfoJuridique) {
		firePropertyChange("rcsInfoJuridique", this.rcsInfoJuridique, this.rcsInfoJuridique = rcsInfoJuridique);
	}

	public String getSiretInfoJuridique() {
		return siretInfoJuridique;
	}

	public void setSiretInfoJuridique(String siretInfoJuridique) {
		firePropertyChange("siretInfoJuridique", this.siretInfoJuridique, this.siretInfoJuridique = siretInfoJuridique);
	}

	public String getCapitalInfoJuridique() {
		return capitalInfoJuridique;
	}

	public void setCapitalInfoJuridique(String capitalInfoJuridique) {
		firePropertyChange("capitalInfoJuridique", this.capitalInfoJuridique, this.capitalInfoJuridique = capitalInfoJuridique);
	}

	public String getApeInfoJuridique() {
		return apeInfoJuridique;
	}

	public void setApeInfoJuridique(String apeInfoJuridique) {
		firePropertyChange("apeInfoJuridique", this.apeInfoJuridique, this.apeInfoJuridique = apeInfoJuridique);
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SWTInfoJuridique))
			return false;
		SWTInfoJuridique castOther = (SWTInfoJuridique) other;

		return ((this.getIdInfoJuridique() == castOther.getIdInfoJuridique()) || (this
				.getIdInfoJuridique() != null
				&& castOther.getIdInfoJuridique() != null && this.getIdInfoJuridique()
				.equals(castOther.getIdInfoJuridique())))
				&& ((this.getRcsInfoJuridique() == castOther
						.getRcsInfoJuridique()) || (this.getRcsInfoJuridique() != null
						&& castOther.getRcsInfoJuridique() != null && this
						.getRcsInfoJuridique().equals(
								castOther.getRcsInfoJuridique())))
				&& ((this.getSiretInfoJuridique() == castOther
						.getSiretInfoJuridique()) || (this.getSiretInfoJuridique() != null
						&& castOther.getSiretInfoJuridique() != null && this
						.getSiretInfoJuridique().equals(
								castOther.getSiretInfoJuridique())))
				&& ((this.getCapitalInfoJuridique() == castOther
						.getCapitalInfoJuridique()) || (this.getCapitalInfoJuridique() != null
						&& castOther.getCapitalInfoJuridique() != null && this
						.getCapitalInfoJuridique().equals(
								castOther.getCapitalInfoJuridique())))
				&& ((this.getApeInfoJuridique() == castOther
						.getApeInfoJuridique()) || (this
						.getApeInfoJuridique() != null
						&& castOther.getApeInfoJuridique() != null && this
						.getApeInfoJuridique().equals(
								castOther.getApeInfoJuridique())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getIdInfoJuridique() == null ? 0 : this.getIdInfoJuridique()
						.hashCode());
		result = 37
				* result
				+ (getRcsInfoJuridique() == null ? 0 : this
						.getRcsInfoJuridique().hashCode());
		result = 37
				* result
				+ (getCapitalInfoJuridique() == null ? 0 : this
						.getCapitalInfoJuridique().hashCode());
		result = 37
				* result
				+ (getApeInfoJuridique() == null ? 0 : this
						.getApeInfoJuridique().hashCode());
		return result;
	}

}

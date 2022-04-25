package fr.legrain.gestCom.Module_Tiers;

import fr.legrain.lib.data.ModelObject;

public class SWTTypeTiers extends ModelObject {

	private Integer idTTiers;
	private String compteTTiers;
	private String codeTTiers;
	private String libelleTTiers;

	

	
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SWTTypeTiers))
			return false;
		SWTTypeTiers castOther = (SWTTypeTiers) other;

		return ((this.getIdTTiers() == castOther.getIdTTiers()) || (this
				.getIdTTiers() != null
				&& castOther.getIdTTiers() != null && this.getIdTTiers()
				.equals(castOther.getIdTTiers())))
				&& ((this.getCodeTTiers() == castOther.getCodeTTiers()) || (this
						.getCodeTTiers() != null
						&& castOther.getCodeTTiers() != null && this
						.getCodeTTiers().equals(castOther.getCodeTTiers())))
				&& ((this.getLibelleTTiers() == castOther
						.getLibelleTTiers()) || (this.getLibelleTTiers() != null
						&& castOther.getLibelleTTiers() != null && this
						.getLibelleTTiers().equals(
								castOther.getLibelleTTiers())))
				&& ((this.getCompteTTiers() == castOther.getCompteTTiers()) || (this
						.getCompteTTiers() != null
						&& castOther.getCompteTTiers() != null && this
						.getCompteTTiers().equals(
								castOther.getCompteTTiers())));
	}
	
	
	public void setSwtTypeTiers(SWTTypeTiers swtTypeTiers){
		this.setIdTTiers(swtTypeTiers.getIdTTiers());                //1
		this.setCodeTTiers(swtTypeTiers.getCodeTTiers());            //2
		this.setCompteTTiers(swtTypeTiers.getCompteTTiers());        //3
		this.setLibelleTTiers(swtTypeTiers.getLibelleTTiers());      //4
	}
	
	public static SWTTypeTiers copy(SWTTypeTiers swtTypeTiers){
		SWTTypeTiers swtTypeTiersLoc = new SWTTypeTiers();
		swtTypeTiersLoc.setIdTTiers(swtTypeTiers.getIdTTiers());                //1
		swtTypeTiersLoc.setCompteTTiers(swtTypeTiers.getCompteTTiers());        //2
		swtTypeTiersLoc.setCodeTTiers(swtTypeTiers.getCodeTTiers());            //3
		swtTypeTiersLoc.setLibelleTTiers(swtTypeTiers.getLibelleTTiers());      //4
		return swtTypeTiersLoc;
	}
	

	public String getCodeTTiers() {
		return codeTTiers;
	}

	public void setCodeTTiers(String CODE_T_TIERS) {
		firePropertyChange("codeTTiers", this.codeTTiers, this.codeTTiers = CODE_T_TIERS);
	}

	public String getCompteTTiers() {
		return compteTTiers;
	}

	public void setCompteTTiers(String COMPTE_T_TIERS) {
		firePropertyChange("compteTTiers", this.compteTTiers, this.compteTTiers = COMPTE_T_TIERS);
	}

	public Integer getIdTTiers() {
		return idTTiers;
	}

	public void setIdTTiers(Integer ID_T_TIERS) {
		firePropertyChange("idTTiers", this.idTTiers, this.idTTiers = ID_T_TIERS);
	}

	public String getLibelleTTiers() {
		return libelleTTiers;
	}

	public void setLibelleTTiers(String LIBELLE_T_TIERS) {
		firePropertyChange("libelleTTiers", this.libelleTTiers, this.libelleTTiers = LIBELLE_T_TIERS);
	}
}

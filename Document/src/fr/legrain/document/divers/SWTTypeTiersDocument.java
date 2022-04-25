package fr.legrain.document.divers;

import fr.legrain.lib.data.ModelObject;

public class SWTTypeTiersDocument extends ModelObject {
	private String codeTTiers;
	private String libelleTTiers;

	

	
	public SWTTypeTiersDocument(){}
	
	public SWTTypeTiersDocument(String code_type_tiers,String nom_type_tiers) {
		this.setCodeTTiers(code_type_tiers);
		this.setLibelleTTiers(nom_type_tiers);
	}


	public String getCodeTTiers() {
		return codeTTiers;
	}

	public void setCodeTTiers(String codeTTiers) {
		firePropertyChange("codeTTiers", this.codeTTiers, this.codeTTiers = codeTTiers);
	}

	public String getLibelleTTiers() {
		return libelleTTiers;
	}

	public void setLibelleTTiers(String libelleTTiers) {
		firePropertyChange("libelleTTiers", this.libelleTTiers, this.libelleTTiers = libelleTTiers);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codeTTiers == null) ? 0 : codeTTiers.hashCode());
		result = prime * result
				+ ((libelleTTiers == null) ? 0 : libelleTTiers.hashCode());
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
		SWTTypeTiersDocument other = (SWTTypeTiersDocument) obj;
		if (codeTTiers == null) {
			if (other.codeTTiers != null)
				return false;
		} else if (!codeTTiers.equals(other.codeTTiers))
			return false;
		if (libelleTTiers == null) {
			if (other.libelleTTiers != null)
				return false;
		} else if (!libelleTTiers.equals(other.libelleTTiers))
			return false;
		return true;
	}
}


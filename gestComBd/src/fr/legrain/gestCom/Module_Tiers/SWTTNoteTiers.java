package fr.legrain.gestCom.Module_Tiers;

import fr.legrain.lib.data.ModelObject;

public class SWTTNoteTiers extends ModelObject {
	private Integer idTNoteTiers;
	private String codeTNoteTiers;
	private String liblTNoteTiers;
	
	public SWTTNoteTiers() {
		
	}
	
	public Integer getIdTNoteTiers() {
		return idTNoteTiers;
	}
	public void setIdTNoteTiers(Integer idTNoteTiers) {
		firePropertyChange("idTNoteTiers", this.idTNoteTiers, this.idTNoteTiers = idTNoteTiers);
	}
	public String getCodeTNoteTiers() {
		return codeTNoteTiers;
	}
	public void setCodeTNoteTiers(String codeTNoteTiers) {
		firePropertyChange("codeTNoteTiers", this.codeTNoteTiers, this.codeTNoteTiers = codeTNoteTiers);
	}
	public String getLiblTNoteTiers() {
		return liblTNoteTiers;
	}
	public void setLiblTNoteTiers(String liblTNoteTiers) {
		firePropertyChange("liblTNoteTiers", this.liblTNoteTiers, this.liblTNoteTiers = liblTNoteTiers);
	}

	
	public static SWTTNoteTiers copy(SWTTNoteTiers swtTNoteTiers){
		SWTTNoteTiers swtTNoteTiersLoc = new SWTTNoteTiers();
		swtTNoteTiersLoc.setCodeTNoteTiers(swtTNoteTiers.codeTNoteTiers);
		swtTNoteTiersLoc.setIdTNoteTiers(swtTNoteTiers.idTNoteTiers);
		swtTNoteTiersLoc.setLiblTNoteTiers(swtTNoteTiers.liblTNoteTiers);
		return swtTNoteTiersLoc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codeTNoteTiers == null) ? 0 : codeTNoteTiers.hashCode());
		result = prime * result + ((idTNoteTiers == null) ? 0 : idTNoteTiers.hashCode());
		result = prime * result
				+ ((liblTNoteTiers == null) ? 0 : liblTNoteTiers.hashCode());
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
		SWTTNoteTiers other = (SWTTNoteTiers) obj;
		if (codeTNoteTiers == null) {
			if (other.codeTNoteTiers != null)
				return false;
		} else if (!codeTNoteTiers.equals(other.codeTNoteTiers))
			return false;
		if (idTNoteTiers == null) {
			if (other.idTNoteTiers != null)
				return false;
		} else if (!idTNoteTiers.equals(other.idTNoteTiers))
			return false;
		if (liblTNoteTiers == null) {
			if (other.liblTNoteTiers != null)
				return false;
		} else if (!liblTNoteTiers.equals(other.liblTNoteTiers))
			return false;
		return true;
	}

}

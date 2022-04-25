package fr.legrain.gestCom.Module_Tiers;

import fr.legrain.lib.data.ModelObject;

public class SWTTLiens extends ModelObject {
	private Integer idTLiens;
	private String codeTLiens;
	private String liblTLiens;
	
	public SWTTLiens(){
	}
	
	public Integer getIdTLiens() {
		return idTLiens;
	}
	public void setIdTLiens(Integer idTLiens) {
		firePropertyChange("idTLiens", this.idTLiens, this.idTLiens = idTLiens);
	}
	public String getCodeTLiens() {
		return codeTLiens;
	}
	public void setCodeTLiens(String codeTLiens) {
		firePropertyChange("codeTLiens", this.codeTLiens, this.codeTLiens = codeTLiens);
	}
	public String getLiblTLiens() {
		return liblTLiens;
	}
	public void setLiblTLiens(String liblTLiens) {
		firePropertyChange("liblTLiens", this.liblTLiens, this.liblTLiens = liblTLiens);
	}
	
	public static SWTTLiens copy(SWTTLiens swtTLiens){
		SWTTLiens swtTLiensLoc = new SWTTLiens();
		swtTLiensLoc.setCodeTLiens(swtTLiens.codeTLiens);
		swtTLiensLoc.setIdTLiens(swtTLiens.idTLiens);
		swtTLiensLoc.setLiblTLiens(swtTLiens.liblTLiens);
		return swtTLiensLoc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codeTLiens == null) ? 0 : codeTLiens.hashCode());
		result = prime * result
				+ ((idTLiens == null) ? 0 : idTLiens.hashCode());
		result = prime * result
				+ ((liblTLiens == null) ? 0 : liblTLiens.hashCode());
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
		SWTTLiens other = (SWTTLiens) obj;
		if (codeTLiens == null) {
			if (other.codeTLiens != null)
				return false;
		} else if (!codeTLiens.equals(other.codeTLiens))
			return false;
		if (idTLiens == null) {
			if (other.idTLiens != null)
				return false;
		} else if (!idTLiens.equals(other.idTLiens))
			return false;
		if (liblTLiens == null) {
			if (other.liblTLiens != null)
				return false;
		} else if (!liblTLiens.equals(other.liblTLiens))
			return false;
		return true;
	}
	
}

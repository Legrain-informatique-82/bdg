package fr.legrain.gestCom.Module_Tiers;

import fr.legrain.lib.data.ModelObject;

public class SWTLiens extends ModelObject {
	private Integer idLiens;
	private String codeTLiens;
	private String codeTiers;
	private String adresseLiens;
	private Integer idTLiens;
	
	public SWTLiens() {
		
	}
	
	public Integer getIdLiens() {
		return idLiens;
	}
	public void setIdLiens(Integer idLiens) {
		firePropertyChange("idLiens", this.idLiens, this.idLiens = idLiens);
	}
	public String getCodeTLiens() {
		return codeTLiens;
	}
	public void setCodeTLiens(String codeTLiens) {
		firePropertyChange("codeTLiens", this.codeTLiens, this.codeTLiens = codeTLiens);
	}
	public String getCodeTiers() {
		return codeTiers;
	}
	public void setCodeTiers(String codeTiers) {
		firePropertyChange("codeTiers", this.codeTiers, this.codeTiers = codeTiers);
	}
	public String getAdresseLiens() {
		return adresseLiens;
	}
	public void setAdresseLiens(String adresseLiens) {
		firePropertyChange("adresseLiens", this.adresseLiens, this.adresseLiens = adresseLiens);
	}
	
	public static SWTLiens copy(SWTLiens swtLiens) {
		SWTLiens swtLiensLoc = new SWTLiens();
		swtLiensLoc.setIdLiens(swtLiens.getIdLiens());
		swtLiensLoc.setAdresseLiens(swtLiens.getAdresseLiens());
		swtLiensLoc.setCodeTiers(swtLiens.getCodeTiers());
		swtLiensLoc.setIdLiens(swtLiens.getIdLiens());
		swtLiensLoc.setIdTLiens(swtLiens.getIdTLiens());
		swtLiensLoc.setCodeTLiens(swtLiens.getCodeTLiens());
		return swtLiensLoc;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((adresseLiens == null) ? 0 : adresseLiens.hashCode());
		result = prime * result
				+ ((codeTLiens == null) ? 0 : codeTLiens.hashCode());
		result = prime * result
				+ ((codeTiers == null) ? 0 : codeTiers.hashCode());
		result = prime * result
		+ ((idTLiens == null) ? 0 : idTLiens.hashCode());		
		result = prime * result
		+ ((idLiens == null) ? 0 : idLiens.hashCode());		
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
		final SWTLiens other = (SWTLiens) obj;
		if (adresseLiens == null) {
			if (other.adresseLiens != null)
				return false;
		} else if (!adresseLiens.equals(other.adresseLiens))
			return false;
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
		if (codeTiers == null) {
			if (other.codeTiers != null)
				return false;
		} else if (!codeTiers.equals(other.codeTiers))
			return false;
		if (idLiens != other.idLiens)
			return false;
		return true;
	}

	public Integer getIdTLiens() {
		return idTLiens;
	}

	public void setIdTLiens(Integer idTLiens) {		
		firePropertyChange("idTLiens", this.idTLiens, this.idTLiens = idTLiens);
	}
}

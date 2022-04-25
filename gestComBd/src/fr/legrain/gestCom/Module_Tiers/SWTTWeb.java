package fr.legrain.gestCom.Module_Tiers;

import fr.legrain.lib.data.ModelObject;

public class SWTTWeb extends ModelObject {
	private Integer idTWeb;
	private String codeTWeb;
	private String liblTWeb;
	
	public SWTTWeb() {
		
	}
	
	public Integer getIdTWeb() {
		return idTWeb;
	}
	public void setIdTWeb(Integer idTWeb) {
		firePropertyChange("idTWeb", this.idTWeb, this.idTWeb = idTWeb);
	}
	public String getCodeTWeb() {
		return codeTWeb;
	}
	public void setCodeTWeb(String codeTWeb) {
		firePropertyChange("codeTWeb", this.codeTWeb, this.codeTWeb = codeTWeb);
	}
	public String getLiblTWeb() {
		return liblTWeb;
	}
	public void setLiblTWeb(String liblTWeb) {
		firePropertyChange("liblTWeb", this.liblTWeb, this.liblTWeb = liblTWeb);
	}

	
	public static SWTTWeb copy(SWTTWeb swtTWeb){
		SWTTWeb swtTWebLoc = new SWTTWeb();
		swtTWebLoc.setCodeTWeb(swtTWebLoc.codeTWeb);
		swtTWebLoc.setIdTWeb(swtTWebLoc.idTWeb);
		swtTWebLoc.setLiblTWeb(swtTWebLoc.liblTWeb);
		return swtTWebLoc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codeTWeb == null) ? 0 : codeTWeb.hashCode());
		result = prime * result + ((idTWeb == null) ? 0 : idTWeb.hashCode());
		result = prime * result
				+ ((liblTWeb == null) ? 0 : liblTWeb.hashCode());
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
		SWTTWeb other = (SWTTWeb) obj;
		if (codeTWeb == null) {
			if (other.codeTWeb != null)
				return false;
		} else if (!codeTWeb.equals(other.codeTWeb))
			return false;
		if (idTWeb == null) {
			if (other.idTWeb != null)
				return false;
		} else if (!idTWeb.equals(other.idTWeb))
			return false;
		if (liblTWeb == null) {
			if (other.liblTWeb != null)
				return false;
		} else if (!liblTWeb.equals(other.liblTWeb))
			return false;
		return true;
	}

}

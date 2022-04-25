package fr.legrain.gestCom.Module_Tiers;

import fr.legrain.lib.data.ModelObject;

public class SWTTEmail extends ModelObject {
	private Integer idTEmail;
	private String codeTEmail;
	private String liblTEmail;
	
	public SWTTEmail() {
		
	}
	
	public Integer getIdTEmail() {
		return idTEmail;
	}
	public void setIdTEmail(Integer idTEmail) {
		firePropertyChange("idTEmail", this.idTEmail, this.idTEmail = idTEmail);
	}
	public String getCodeTEmail() {
		return codeTEmail;
	}
	public void setCodeTEmail(String codeTEmail) {
		firePropertyChange("codeTEmail", this.codeTEmail, this.codeTEmail = codeTEmail);
	}
	public String getLiblTEmail() {
		return liblTEmail;
	}
	public void setLiblTEmail(String liblTEmail) {
		firePropertyChange("liblTEmail", this.liblTEmail, this.liblTEmail = liblTEmail);
	}
	
	
	
	public static SWTTEmail copy(SWTTEmail swtTEmail){
		SWTTEmail swtTEmailLoc = new SWTTEmail();
		swtTEmailLoc.setIdTEmail(swtTEmail.idTEmail);             
		swtTEmailLoc.setCodeTEmail(swtTEmail.codeTEmail);        
		swtTEmailLoc.setLiblTEmail(swtTEmail.liblTEmail);            
		return swtTEmailLoc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codeTEmail == null) ? 0 : codeTEmail.hashCode());
		result = prime * result
				+ ((idTEmail == null) ? 0 : idTEmail.hashCode());
		result = prime * result
				+ ((liblTEmail == null) ? 0 : liblTEmail.hashCode());
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
		SWTTEmail other = (SWTTEmail) obj;
		if (codeTEmail == null) {
			if (other.codeTEmail != null)
				return false;
		} else if (!codeTEmail.equals(other.codeTEmail))
			return false;
		if (idTEmail == null) {
			if (other.idTEmail != null)
				return false;
		} else if (!idTEmail.equals(other.idTEmail))
			return false;
		if (liblTEmail == null) {
			if (other.liblTEmail != null)
				return false;
		} else if (!liblTEmail.equals(other.liblTEmail))
			return false;
		return true;
	}

}

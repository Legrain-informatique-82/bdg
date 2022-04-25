package fr.legrain.gestCom.Module_Document;

import fr.legrain.lib.data.ModelObject;

public class SWTEtat extends ModelObject {
	private Integer idEtat;
	private String codeEtat;
	private String libEtat;
	
	public SWTEtat(){
	}
	
	public Integer getIdEtat() {
		return idEtat;
	}
	public void setIdEtat(Integer idEtat) {
		firePropertyChange("idEtat", this.idEtat, this.idEtat = idEtat);
	}
	public String getCodeEtat() {
		return codeEtat;
	}
	public void setCodeEtat(String codeEtat) {
		firePropertyChange("codeEtat", this.codeEtat, this.codeEtat = codeEtat);
	}
	public String getLibEtat() {
		return libEtat;
	}

	public void setLibEtat(String libEtat) {
		firePropertyChange("libEtat", this.libEtat, this.libEtat = libEtat);
	}

	
	public SWTEtat(String codeEtat, String libEtat) {
		super();
		this.codeEtat = codeEtat;
		this.libEtat = libEtat;
	}

	public static SWTEtat copy(SWTEtat swtTDoc){
		SWTEtat swtEtat = new SWTEtat();
		swtEtat.setCodeEtat(swtTDoc.codeEtat);
		swtEtat.setIdEtat(swtTDoc.idEtat);
		swtEtat.setLibEtat(swtTDoc.libEtat);
		return swtEtat;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codeEtat == null) ? 0 : codeEtat.hashCode());
		result = prime * result
				+ ((idEtat == null) ? 0 : idEtat.hashCode());
		result = prime * result
				+ ((libEtat == null) ? 0 : libEtat.hashCode());
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
		SWTEtat other = (SWTEtat) obj;
		if (codeEtat == null) {
			if (other.codeEtat != null)
				return false;
		} else if (!codeEtat.equals(other.codeEtat))
			return false;
		if (idEtat == null) {
			if (other.idEtat != null)
				return false;
		} else if (!idEtat.equals(other.idEtat))
			return false;
		if (libEtat == null) {
			if (other.libEtat != null)
				return false;
		} else if (!libEtat.equals(other.libEtat))
			return false;
		return true;
	}

}

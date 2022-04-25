package fr.legrain.gestCom.Module_GestionCapsules;

import fr.legrain.lib.data.ModelObject;

public class SWTTitreTransport extends ModelObject {
	private Integer idTitreTransport;
	private String codeTitreTransport;
	private String libelleTitreTransport;
	private Integer qteMinTitreTransport;
	
	public SWTTitreTransport() {
	}
	
	public Integer getIdTitreTransport() {
		return idTitreTransport;
	}
	public void setIdTitreTransport(Integer idTNoteArticle) {
		firePropertyChange("idTNoteArticle", this.idTitreTransport, this.idTitreTransport = idTNoteArticle);
	}
	public String getCodeTitreTransport() {
		return codeTitreTransport;
	}
	public void setCodeTitreTransport(String codeTNoteArticle) {
		firePropertyChange("codeTNoteArticle", this.codeTitreTransport, this.codeTitreTransport = codeTNoteArticle);
	}
	
	public String getLibelleTitreTransport() {
		return libelleTitreTransport;
	}

	public void setLibelleTitreTransport(String libelleTitreTransport) {
		firePropertyChange("libelleTitreTransport", this.libelleTitreTransport, this.libelleTitreTransport = libelleTitreTransport);
	}

	public Integer getQteMinTitreTransport() {
		return qteMinTitreTransport;
	}

	public void setQteMinTitreTransport(Integer qteMinTitreTransport) {
		firePropertyChange("qteMinTitreTransport", this.qteMinTitreTransport, this.qteMinTitreTransport = qteMinTitreTransport);
	}
	
	public static SWTTitreTransport copy(SWTTitreTransport swtTitreTransport){
		SWTTitreTransport swtTitreTransportLoc = new SWTTitreTransport();
		swtTitreTransportLoc.setCodeTitreTransport(swtTitreTransport.codeTitreTransport);
		swtTitreTransportLoc.setIdTitreTransport(swtTitreTransport.idTitreTransport);
		swtTitreTransportLoc.setLibelleTitreTransport(swtTitreTransport.libelleTitreTransport);
		swtTitreTransportLoc.setQteMinTitreTransport(swtTitreTransport.qteMinTitreTransport);
		return swtTitreTransportLoc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codeTitreTransport == null) ? 0 : codeTitreTransport.hashCode());
		result = prime * result + ((idTitreTransport == null) ? 0 : idTitreTransport.hashCode());
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
		SWTTitreTransport other = (SWTTitreTransport) obj;
		if (codeTitreTransport == null) {
			if (other.codeTitreTransport != null)
				return false;
		} else if (!codeTitreTransport.equals(other.codeTitreTransport))
			return false;
		if (idTitreTransport == null) {
			if (other.idTitreTransport != null)
				return false;
		} else if (!idTitreTransport.equals(other.idTitreTransport))
			return false;
		return true;
	}

}

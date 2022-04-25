package fr.legrain.gestCom.Module_Document;

import fr.legrain.lib.data.ModelObject;

public class SWTTDoc extends ModelObject {
	private Integer idTDoc;
	private String codeTDoc;
	private String libTDoc;
	
	public SWTTDoc(){
	}
	
	public Integer getIdTDoc() {
		return idTDoc;
	}
	public void setIdTDoc(Integer idTDoc) {
		firePropertyChange("idTDoc", this.idTDoc, this.idTDoc = idTDoc);
	}
	public String getCodeTDoc() {
		return codeTDoc;
	}
	public void setCodeTDoc(String codeTDoc) {
		firePropertyChange("codeTDoc", this.codeTDoc, this.codeTDoc = codeTDoc);
	}
	public String getLibTDoc() {
		return libTDoc;
	}

	public void setLibTDoc(String libTDoc) {
		firePropertyChange("libTDoc", this.libTDoc, this.libTDoc = libTDoc);
	}

	
	public SWTTDoc(String codeTDoc, String libTDoc) {
		super();
		this.codeTDoc = codeTDoc;
		this.libTDoc = libTDoc;
	}

	public static SWTTDoc copy(SWTTDoc swtTDoc){
		SWTTDoc swtTDocLoc = new SWTTDoc();
		swtTDocLoc.setCodeTDoc(swtTDoc.codeTDoc);
		swtTDocLoc.setIdTDoc(swtTDoc.idTDoc);
		swtTDocLoc.setLibTDoc(swtTDoc.libTDoc);
		return swtTDocLoc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codeTDoc == null) ? 0 : codeTDoc.hashCode());
		result = prime * result
				+ ((idTDoc == null) ? 0 : idTDoc.hashCode());
		result = prime * result
				+ ((libTDoc == null) ? 0 : libTDoc.hashCode());
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
		SWTTDoc other = (SWTTDoc) obj;
		if (codeTDoc == null) {
			if (other.codeTDoc != null)
				return false;
		} else if (!codeTDoc.equals(other.codeTDoc))
			return false;
		if (idTDoc == null) {
			if (other.idTDoc != null)
				return false;
		} else if (!idTDoc.equals(other.idTDoc))
			return false;
		if (libTDoc == null) {
			if (other.libTDoc != null)
				return false;
		} else if (!libTDoc.equals(other.libTDoc))
			return false;
		return true;
	}

}

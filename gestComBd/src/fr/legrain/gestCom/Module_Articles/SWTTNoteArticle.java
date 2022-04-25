package fr.legrain.gestCom.Module_Articles;

import fr.legrain.lib.data.ModelObject;

public class SWTTNoteArticle extends ModelObject {
	private Integer idTNoteArticle;
	private String codeTNoteArticle;
	private String liblTNoteArticle;
	
	public SWTTNoteArticle() {
		
	}
	
	public Integer getIdTNoteArticle() {
		return idTNoteArticle;
	}
	public void setIdTNoteArticle(Integer idTNoteArticle) {
		firePropertyChange("idTNoteArticle", this.idTNoteArticle, this.idTNoteArticle = idTNoteArticle);
	}
	public String getCodeTNoteArticle() {
		return codeTNoteArticle;
	}
	public void setCodeTNoteArticle(String codeTNoteArticle) {
		firePropertyChange("codeTNoteArticle", this.codeTNoteArticle, this.codeTNoteArticle = codeTNoteArticle);
	}
	public String getLiblTNoteArticle() {
		return liblTNoteArticle;
	}
	public void setLiblTNoteArticle(String liblTNoteArticle) {
		firePropertyChange("liblTNoteArticle", this.liblTNoteArticle, this.liblTNoteArticle = liblTNoteArticle);
	}

	
	public static SWTTNoteArticle copy(SWTTNoteArticle swtTNoteArticle){
		SWTTNoteArticle swtTNoteTiersLoc = new SWTTNoteArticle();
		swtTNoteTiersLoc.setCodeTNoteArticle(swtTNoteArticle.codeTNoteArticle);
		swtTNoteTiersLoc.setIdTNoteArticle(swtTNoteArticle.idTNoteArticle);
		swtTNoteTiersLoc.setLiblTNoteArticle(swtTNoteArticle.liblTNoteArticle);
		return swtTNoteTiersLoc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codeTNoteArticle == null) ? 0 : codeTNoteArticle.hashCode());
		result = prime * result + ((idTNoteArticle == null) ? 0 : idTNoteArticle.hashCode());
		result = prime * result
				+ ((liblTNoteArticle == null) ? 0 : liblTNoteArticle.hashCode());
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
		SWTTNoteArticle other = (SWTTNoteArticle) obj;
		if (codeTNoteArticle == null) {
			if (other.codeTNoteArticle != null)
				return false;
		} else if (!codeTNoteArticle.equals(other.codeTNoteArticle))
			return false;
		if (idTNoteArticle == null) {
			if (other.idTNoteArticle != null)
				return false;
		} else if (!idTNoteArticle.equals(other.idTNoteArticle))
			return false;
		if (liblTNoteArticle == null) {
			if (other.liblTNoteArticle != null)
				return false;
		} else if (!liblTNoteArticle.equals(other.liblTNoteArticle))
			return false;
		return true;
	}

}

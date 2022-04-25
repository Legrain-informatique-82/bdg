package fr.legrain.document.divers;

import fr.legrain.lib.data.ModelObject;

public class SWTNoteTiersDocument extends ModelObject {
	private String codeTNoteTiers;
	private String liblTNoteTiers;
	
	public SWTNoteTiersDocument() {
		
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

	
	public static SWTNoteTiersDocument copy(SWTNoteTiersDocument swtTNoteTiers){
		SWTNoteTiersDocument swtTNoteTiersLoc = new SWTNoteTiersDocument();
		swtTNoteTiersLoc.setCodeTNoteTiers(swtTNoteTiers.codeTNoteTiers);
		swtTNoteTiersLoc.setLiblTNoteTiers(swtTNoteTiers.liblTNoteTiers);
		return swtTNoteTiersLoc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codeTNoteTiers == null) ? 0 : codeTNoteTiers.hashCode());
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
		SWTNoteTiersDocument other = (SWTNoteTiersDocument) obj;
		if (codeTNoteTiers == null) {
			if (other.codeTNoteTiers != null)
				return false;
		} else if (!codeTNoteTiers.equals(other.codeTNoteTiers))
			return false;
		if (liblTNoteTiers == null) {
			if (other.liblTNoteTiers != null)
				return false;
		} else if (!liblTNoteTiers.equals(other.liblTNoteTiers))
			return false;
		return true;
	}

}

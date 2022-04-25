package fr.legrain.gestCom.Module_Tiers;

import fr.legrain.lib.data.ModelObject;

public class SWTTRelance extends ModelObject {
	private Integer idTRelance;
	private String codeTRelance;
	private String libelleTRelance;
	private String cheminModelRelance;
	private String cheminCorrespRelance;
	private Integer ordreTRelance;
	private Boolean actif;
	private Boolean defaut;
	private String typeLogiciel;
	
	public SWTTRelance(){
	}
	
	public Integer getIdTRelance() {
		return idTRelance;
	}
	public void setIdTRelance(Integer idTRelance) {
		firePropertyChange("idTRelance", this.idTRelance, this.idTRelance = idTRelance);
	}
	public String getCodeTRelance() {
		return codeTRelance;
	}
	public void setCodeTRelance(String codeTRelance) {
		firePropertyChange("codeTRelance", this.codeTRelance, this.codeTRelance = codeTRelance);
	}
	public String getCheminModelRelance() {
		return cheminModelRelance;
	}
	public void setCheminModelRelance(String cheminTRelance) {
		firePropertyChange("cheminTRelance", this.cheminModelRelance, this.cheminModelRelance = cheminTRelance);
	}
	public String getLibelleTRelance() {
		return libelleTRelance;
	}

	public void setLibelleTRelance(String libelleTRelance) {
		firePropertyChange("libelleTRelance", this.libelleTRelance, this.libelleTRelance = libelleTRelance);
	}
	public Integer getOrdreTRelance() {
		return ordreTRelance;
	}

	public void setOrdreTRelance(Integer ordreTRelance) {
		firePropertyChange("ordreTRelance", this.ordreTRelance, this.ordreTRelance = ordreTRelance);
	}
	public String getCheminCorrespRelance() {
		return cheminCorrespRelance;
	}

	public void setCheminCorrespRelance(String cheminCorrespRelance) {
		firePropertyChange("cheminCorrespRelance", this.cheminCorrespRelance, this.cheminCorrespRelance = cheminCorrespRelance);
	}

	public Boolean getActif() {
		return actif;
	}

	public void setActif(Boolean actif) {
		firePropertyChange("actif", this.actif, this.actif = actif);
	}
	
	public Boolean getDefaut() {
		return defaut;
	}

	public void setDefaut(Boolean defaut) {
		firePropertyChange("defaut", this.defaut, this.defaut = defaut);
	}
	
	public String getTypeLogiciel() {
		return typeLogiciel;
	}

	public void setTypeLogiciel(String typeLogiciel) {
		firePropertyChange("typeLogiciel", this.typeLogiciel, this.typeLogiciel = typeLogiciel);
	}
	
	
	public static SWTTRelance copy(SWTTRelance swtTLiens){
		SWTTRelance swtTRelanceLoc = new SWTTRelance();
		swtTRelanceLoc.setCodeTRelance(swtTLiens.codeTRelance);
		swtTRelanceLoc.setIdTRelance(swtTLiens.idTRelance);
		swtTRelanceLoc.setCheminModelRelance(swtTLiens.cheminModelRelance);
		swtTRelanceLoc.setCheminCorrespRelance(swtTLiens.cheminCorrespRelance);
		swtTRelanceLoc.setLibelleTRelance(swtTLiens.libelleTRelance);
		swtTRelanceLoc.setOrdreTRelance(swtTLiens.ordreTRelance);
		swtTRelanceLoc.setActif(swtTLiens.actif);
		swtTRelanceLoc.setDefaut(swtTLiens.defaut);
		swtTRelanceLoc.setTypeLogiciel(swtTLiens.typeLogiciel);
		return swtTRelanceLoc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actif == null) ? 0 : actif.hashCode());
		result = prime
				* result
				+ ((cheminCorrespRelance == null) ? 0 : cheminCorrespRelance
						.hashCode());
		result = prime
				* result
				+ ((cheminModelRelance == null) ? 0 : cheminModelRelance
						.hashCode());
		result = prime * result
				+ ((codeTRelance == null) ? 0 : codeTRelance.hashCode());
		result = prime * result + ((defaut == null) ? 0 : defaut.hashCode());
		result = prime * result
				+ ((idTRelance == null) ? 0 : idTRelance.hashCode());
		result = prime * result
				+ ((libelleTRelance == null) ? 0 : libelleTRelance.hashCode());
		result = prime * result
				+ ((ordreTRelance == null) ? 0 : ordreTRelance.hashCode());
		result = prime * result
				+ ((typeLogiciel == null) ? 0 : typeLogiciel.hashCode());
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
		SWTTRelance other = (SWTTRelance) obj;
		if (actif == null) {
			if (other.actif != null)
				return false;
		} else if (!actif.equals(other.actif))
			return false;
		if (cheminCorrespRelance == null) {
			if (other.cheminCorrespRelance != null)
				return false;
		} else if (!cheminCorrespRelance.equals(other.cheminCorrespRelance))
			return false;
		if (cheminModelRelance == null) {
			if (other.cheminModelRelance != null)
				return false;
		} else if (!cheminModelRelance.equals(other.cheminModelRelance))
			return false;
		if (codeTRelance == null) {
			if (other.codeTRelance != null)
				return false;
		} else if (!codeTRelance.equals(other.codeTRelance))
			return false;
		if (defaut == null) {
			if (other.defaut != null)
				return false;
		} else if (!defaut.equals(other.defaut))
			return false;
		if (idTRelance == null) {
			if (other.idTRelance != null)
				return false;
		} else if (!idTRelance.equals(other.idTRelance))
			return false;
		if (libelleTRelance == null) {
			if (other.libelleTRelance != null)
				return false;
		} else if (!libelleTRelance.equals(other.libelleTRelance))
			return false;
		if (ordreTRelance == null) {
			if (other.ordreTRelance != null)
				return false;
		} else if (!ordreTRelance.equals(other.ordreTRelance))
			return false;
		if (typeLogiciel == null) {
			if (other.typeLogiciel != null)
				return false;
		} else if (!typeLogiciel.equals(other.typeLogiciel))
			return false;
		return true;
	}


	
}

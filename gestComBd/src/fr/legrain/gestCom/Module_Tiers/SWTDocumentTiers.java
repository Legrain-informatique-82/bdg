package fr.legrain.gestCom.Module_Tiers;

import fr.legrain.lib.data.ModelObject;

public class SWTDocumentTiers extends ModelObject {
	private Integer idDocumentTiers;
	private String codeDocumentTiers;
	private String libelleDocumentTiers;
	private String cheminModelDocumentTiers;
	private String cheminCorrespDocumentTiers;
	private Boolean actif;
	private Boolean defaut;
	private String typeLogiciel;
	
	public SWTDocumentTiers(){
	}
	
	public Integer getIdDocumentTiers() {
		return idDocumentTiers;
	}
	public void setIdDocumentTiers(Integer idDocumentTiers) {
		firePropertyChange("idDocumentTiers", this.idDocumentTiers, this.idDocumentTiers = idDocumentTiers);
	}
	public String getCodeDocumentTiers() {
		return codeDocumentTiers;
	}
	public void setCodeDocumentTiers(String codeDocumentTiers) {
		firePropertyChange("codeDocumentTiers", this.codeDocumentTiers, this.codeDocumentTiers = codeDocumentTiers);
	}
	public String getCheminModelDocumentTiers() {
		return cheminModelDocumentTiers;
	}
	public void setCheminModelDocumentTiers(String cheminModelDocumentTiers) {
		firePropertyChange("cheminModelDocumentTiers", this.cheminModelDocumentTiers, this.cheminModelDocumentTiers = cheminModelDocumentTiers);
	}
	public String getLibelleDocumentTiers() {
		return libelleDocumentTiers;
	}

	public void setLibelleDocumentTiers(String libelleDocumentTiers) {
		firePropertyChange("libelleDocumentTiers", this.libelleDocumentTiers, this.libelleDocumentTiers = libelleDocumentTiers);
	}

	public String getCheminCorrespDocumentTiers() {
		return cheminCorrespDocumentTiers;
	}

	public void setCheminCorrespDocumentTiers(String cheminCorrespDocumentTiers) {
		firePropertyChange("cheminCorrespDocumentTiers", this.cheminCorrespDocumentTiers, this.cheminCorrespDocumentTiers = cheminCorrespDocumentTiers);
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
	
	
	public static SWTDocumentTiers copy(SWTDocumentTiers swtTLiens){
		SWTDocumentTiers swtTRelanceLoc = new SWTDocumentTiers();
		swtTRelanceLoc.setCodeDocumentTiers(swtTLiens.codeDocumentTiers);
		swtTRelanceLoc.setIdDocumentTiers(swtTLiens.idDocumentTiers);
		swtTRelanceLoc.setCheminModelDocumentTiers(swtTLiens.cheminModelDocumentTiers);
		swtTRelanceLoc.setCheminCorrespDocumentTiers(swtTLiens.cheminCorrespDocumentTiers);
		swtTRelanceLoc.setLibelleDocumentTiers(swtTLiens.libelleDocumentTiers);
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
				+ ((cheminCorrespDocumentTiers == null) ? 0 : cheminCorrespDocumentTiers
						.hashCode());
		result = prime
				* result
				+ ((cheminModelDocumentTiers == null) ? 0 : cheminModelDocumentTiers
						.hashCode());
		result = prime * result
				+ ((codeDocumentTiers == null) ? 0 : codeDocumentTiers.hashCode());
		result = prime * result + ((defaut == null) ? 0 : defaut.hashCode());
		result = prime * result
				+ ((idDocumentTiers == null) ? 0 : idDocumentTiers.hashCode());
		result = prime * result
				+ ((libelleDocumentTiers == null) ? 0 : libelleDocumentTiers.hashCode());
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
		SWTDocumentTiers other = (SWTDocumentTiers) obj;
		if (actif == null) {
			if (other.actif != null)
				return false;
		} else if (!actif.equals(other.actif))
			return false;
		if (cheminCorrespDocumentTiers == null) {
			if (other.cheminCorrespDocumentTiers != null)
				return false;
		} else if (!cheminCorrespDocumentTiers.equals(other.cheminCorrespDocumentTiers))
			return false;
		if (cheminModelDocumentTiers == null) {
			if (other.cheminModelDocumentTiers != null)
				return false;
		} else if (!cheminModelDocumentTiers.equals(other.cheminModelDocumentTiers))
			return false;
		if (codeDocumentTiers == null) {
			if (other.codeDocumentTiers != null)
				return false;
		} else if (!codeDocumentTiers.equals(other.codeDocumentTiers))
			return false;
		if (defaut == null) {
			if (other.defaut != null)
				return false;
		} else if (!defaut.equals(other.defaut))
			return false;
		if (idDocumentTiers == null) {
			if (other.idDocumentTiers != null)
				return false;
		} else if (!idDocumentTiers.equals(other.idDocumentTiers))
			return false;
		if (libelleDocumentTiers == null) {
			if (other.libelleDocumentTiers != null)
				return false;
		} else if (!libelleDocumentTiers.equals(other.libelleDocumentTiers))
			return false;
		if (typeLogiciel == null) {
			if (other.typeLogiciel != null)
				return false;
		} else if (!typeLogiciel.equals(other.typeLogiciel))
			return false;
		return true;
	}
	
}

package fr.legrain.boutique.dao;

import fr.legrain.gestCom.Module_Articles.SWTPrix;
import fr.legrain.lib.data.ModelObject;

// Generated Mar 1, 2007 11:26:10 AM by Hibernate Tools 3.2.0.b9

/**
 * SWTCPaiement generated by hbm2java
 */
public class SWTCorrespondanceIDBoutique extends ModelObject {

	private Integer id;
	private String valeurBDG;
	private Integer idBdg;
	private Integer idBoutique;
	private String valeurBoutique;
	private String typeTable;

	public SWTCorrespondanceIDBoutique() {
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		firePropertyChange("id", this.id, this.id = id);
	}

	public String getValeurBDG() {
		return this.valeurBDG;
	}

	public void setValeurBDG(String valeurBDG) {
		firePropertyChange("valeurBDG", this.valeurBDG, this.valeurBDG = valeurBDG);
	}
	
	public String getValeurBoutique() {
		return this.valeurBoutique;
	}

	public void setValeurBoutique(String valeurBoutique) {
		firePropertyChange("valeurBoutique", this.valeurBoutique, this.valeurBoutique = valeurBoutique);
	}

	public Integer getIdBdg() {
		return this.idBdg;
	}

	public void setIdBdg(Integer idBdg) {
		firePropertyChange("idBdg", this.idBdg, this.idBdg = idBdg);
	}

	public Integer getIdBoutique() {
		return this.idBoutique;
	}

	public void setIdBoutique(Integer idBoutique) {
		firePropertyChange("idBoutique", this.idBoutique, this.idBoutique = idBoutique);
	}
	
	public String getTypeTable() {
		return typeTable;
	}

	public void setTypeTable(String typeTable) {
		firePropertyChange("typeTable", this.typeTable, this.typeTable = typeTable);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SWTCorrespondanceIDBoutique other = (SWTCorrespondanceIDBoutique) obj;
		if (valeurBDG == null) {
			if (other.valeurBDG != null)
				return false;
		} else if (!valeurBDG.equals(other.valeurBDG))
			return false;
		if (idBoutique == null) {
			if (other.idBoutique != null)
				return false;
		} else if (!idBoutique.equals(other.idBoutique))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idBdg == null) {
			if (other.idBdg != null)
				return false;
		} else if (!idBdg.equals(other.idBdg))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((valeurBDG == null) ? 0 : valeurBDG.hashCode());
		result = prime
				* result
				+ ((idBoutique == null) ? 0 : idBoutique.hashCode());
		result = prime * result
				+ ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((idBdg == null) ? 0 : idBdg.hashCode());
		return result;
	}
	
	public static SWTCorrespondanceIDBoutique copy(SWTCorrespondanceIDBoutique swtCPaiement){
		SWTCorrespondanceIDBoutique swtCPaiementLoc = new SWTCorrespondanceIDBoutique();
		swtCPaiementLoc.setId(swtCPaiement.id);
		swtCPaiementLoc.setValeurBDG(swtCPaiement.valeurBDG);
		swtCPaiementLoc.setIdBdg(swtCPaiement.idBdg);
		swtCPaiementLoc.setIdBoutique(swtCPaiement.idBoutique);
		return swtCPaiementLoc;
	}
	
	public void setSWTCPaiement(SWTCorrespondanceIDBoutique swtCPaiement) {
		this.id = swtCPaiement.id;
		this.valeurBDG = swtCPaiement.valeurBDG;
		this.idBdg = swtCPaiement.idBdg;
		this.idBoutique = swtCPaiement.idBoutique;
	}

}
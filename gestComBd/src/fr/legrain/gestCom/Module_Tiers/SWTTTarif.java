package fr.legrain.gestCom.Module_Tiers;

import java.math.BigDecimal;

import fr.legrain.lib.data.ModelObject;

public class SWTTTarif extends ModelObject {
	private Integer idTTarif;
	private String codeTTarif;
	private String liblTTarif;
	private Boolean sens = false;
	private Boolean pourcentage = false;
	private BigDecimal valeur = new BigDecimal(0);
	
	public SWTTTarif() {
		
	}
	
	public Integer getIdTTarif() {
		return idTTarif;
	}
	public void setIdTTarif(Integer idTTarif) {
		firePropertyChange("idTTarif", this.idTTarif, this.idTTarif = idTTarif);
	}
	public String getCodeTTarif() {
		return codeTTarif;
	}
	public void setCodeTTarif(String codeTTarif) {
		firePropertyChange("codeTTarif", this.codeTTarif, this.codeTTarif = codeTTarif);
	}
	public String getLiblTTarif() {
		return liblTTarif;
	}
	public void setLiblTTarif(String liblTTarif) {
		firePropertyChange("liblTTarif", this.liblTTarif, this.liblTTarif = liblTTarif);
	}
	
	public Boolean getSens() {
		return sens;
	}

	public void setSens(Boolean sens) {
		firePropertyChange("sens", this.sens, this.sens = sens);
	}

	public Boolean getPourcentage() {
		return pourcentage;
	}

	public void setPourcentage(Boolean pourcentage) {
		firePropertyChange("pourcentage", this.pourcentage, this.pourcentage = pourcentage);
	}

	public BigDecimal getValeur() {
		return valeur;
	}

	public void setValeur(BigDecimal valeur) {
		firePropertyChange("valeur", this.valeur, this.valeur = valeur);
	}

	public static SWTTTarif copy(SWTTTarif swtTTarif){
		SWTTTarif swtTTarifLoc = new SWTTTarif();
		swtTTarifLoc.setIdTTarif(swtTTarif.getIdTTarif());                //1
		swtTTarifLoc.setCodeTTarif(swtTTarif.getCodeTTarif());        //2
		swtTTarifLoc.setLiblTTarif(swtTTarif.getLiblTTarif());            //3
		swtTTarifLoc.setSens(swtTTarif.getSens());
		swtTTarifLoc.setPourcentage(swtTTarif.getPourcentage());
		swtTTarifLoc.setValeur(swtTTarif.getValeur());
		return swtTTarifLoc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codeTTarif == null) ? 0 : codeTTarif.hashCode());
		result = prime * result
				+ ((idTTarif == null) ? 0 : idTTarif.hashCode());
		result = prime * result
				+ ((liblTTarif == null) ? 0 : liblTTarif.hashCode());
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
		SWTTTarif other = (SWTTTarif) obj;
		if (codeTTarif == null) {
			if (other.codeTTarif != null)
				return false;
		} else if (!codeTTarif.equals(other.codeTTarif))
			return false;
		if (idTTarif == null) {
			if (other.idTTarif != null)
				return false;
		} else if (!idTTarif.equals(other.idTTarif))
			return false;
		if (liblTTarif == null) {
			if (other.liblTTarif != null)
				return false;
		} else if (!liblTTarif.equals(other.liblTTarif))
			return false;
		return true;
	}
	
}

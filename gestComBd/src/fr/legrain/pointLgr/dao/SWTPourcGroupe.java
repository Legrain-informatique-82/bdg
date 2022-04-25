package fr.legrain.pointLgr.dao;

import java.math.BigDecimal;

import fr.legrain.lib.data.ModelObject;

// Generated Mar 1, 2007 11:26:10 AM by Hibernate Tools 3.2.0.b9

/**
 * SWTFamille generated by hbm2java
 */
public class SWTPourcGroupe extends ModelObject {

	private Integer idPourcGroupe;
	private String codeTAbonnement;
	private Integer idTAbonnement;
	private Integer idFamille;
	private String codeFamille;
	private BigDecimal pourcentage;

	
	public SWTPourcGroupe() {
	}


	

	
	public SWTPourcGroupe(Integer idPourcGroupe, String codeTAbonnement,
			Integer idTAbonnement, Integer idFamille, String codeFamille,
			BigDecimal pourcentage) {
		super();
		this.idPourcGroupe = idPourcGroupe;
		this.codeTAbonnement = codeTAbonnement;
		this.idTAbonnement = idTAbonnement;
		this.idFamille = idFamille;
		this.codeFamille = codeFamille;
		this.pourcentage = pourcentage;
	}





	public static SWTPourcGroupe copy(SWTPourcGroupe swtFamille){
		SWTPourcGroupe swtFamilleLoc = new SWTPourcGroupe();
		swtFamilleLoc.setIdPourcGroupe(swtFamille.idPourcGroupe);
		swtFamilleLoc.setIdFamille(swtFamille.idFamille);
		swtFamilleLoc.setCodeFamille(swtFamille.codeFamille);
		swtFamilleLoc.setCodeTAbonnement(swtFamille.codeTAbonnement);
		swtFamilleLoc.setIdTAbonnement(swtFamille.idTAbonnement);
		swtFamilleLoc.setPourcentage(swtFamille.pourcentage);
		return swtFamilleLoc;
	}

	public Integer getIdFamille() {
		return this.idFamille;
	}

	public void setIdFamille(Integer idFamille) {
		firePropertyChange("idFamille", this.idFamille, this.idFamille = idFamille);
	}
	
	public String getCodeFamille() {
		return this.codeFamille;
	}

	public void setCodeFamille(String CODE_FAMILLE) {
		firePropertyChange("codeFamille", this.codeFamille, this.codeFamille = CODE_FAMILLE);
	}


	
	public Integer getIdPourcGroupe() {
		return idPourcGroupe;
	}



	public void setIdPourcGroupe(Integer idPourcGroupe) {
		firePropertyChange("idPourcGroupe", this.idPourcGroupe, this.idPourcGroupe = idPourcGroupe);
	}



	public String getCodeTAbonnement() {
		return codeTAbonnement;
	}



	public void setCodeTAbonnement(String codeTAbonnement) {
		firePropertyChange("codeTAbonnement", this.codeTAbonnement, this.codeTAbonnement = codeTAbonnement);
	}



	public Integer getIdTAbonnement() {
		return idTAbonnement;
	}



	public void setIdTAbonnement(Integer idTAbonnement) {
		firePropertyChange("idTAbonnement", this.idTAbonnement, this.idTAbonnement = idTAbonnement);
	}



	public BigDecimal getPourcentage() {
		return pourcentage;
	}



	public void setPourcentage(BigDecimal pourcentage) {
		firePropertyChange("pourcentage", this.pourcentage, this.pourcentage = pourcentage);
	}





	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codeFamille == null) ? 0 : codeFamille.hashCode());
		result = prime * result
				+ ((codeTAbonnement == null) ? 0 : codeTAbonnement.hashCode());
		result = prime * result
				+ ((idFamille == null) ? 0 : idFamille.hashCode());
		result = prime * result
				+ ((idPourcGroupe == null) ? 0 : idPourcGroupe.hashCode());
		result = prime * result
				+ ((idTAbonnement == null) ? 0 : idTAbonnement.hashCode());
		result = prime * result
				+ ((pourcentage == null) ? 0 : pourcentage.hashCode());
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
		SWTPourcGroupe other = (SWTPourcGroupe) obj;
		if (codeFamille == null) {
			if (other.codeFamille != null)
				return false;
		} else if (!codeFamille.equals(other.codeFamille))
			return false;
		if (codeTAbonnement == null) {
			if (other.codeTAbonnement != null)
				return false;
		} else if (!codeTAbonnement.equals(other.codeTAbonnement))
			return false;
		if (idFamille == null) {
			if (other.idFamille != null)
				return false;
		} else if (!idFamille.equals(other.idFamille))
			return false;
		if (idPourcGroupe == null) {
			if (other.idPourcGroupe != null)
				return false;
		} else if (!idPourcGroupe.equals(other.idPourcGroupe))
			return false;
		if (idTAbonnement == null) {
			if (other.idTAbonnement != null)
				return false;
		} else if (!idTAbonnement.equals(other.idTAbonnement))
			return false;
		if (pourcentage == null) {
			if (other.pourcentage != null)
				return false;
		} else if (!pourcentage.equals(other.pourcentage))
			return false;
		return true;
	}


}

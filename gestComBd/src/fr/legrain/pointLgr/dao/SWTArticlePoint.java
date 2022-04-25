package fr.legrain.pointLgr.dao;

import java.math.BigDecimal;
import java.util.Date;

import fr.legrain.lib.data.ModelObject;
import fr.legrain.tiers.dao.TaTiers;

// Generated Mar 1, 2007 11:26:10 AM by Hibernate Tools 3.2.0.b9

/**
 * SWTAdresse generated by hbm2java
 */
public class SWTArticlePoint extends ModelObject {

	private Integer idArticlePoint;
	private BigDecimal points;
	private Date debutPeriode;
	private Date finPeriode;
	private BigDecimal prixReference;
	private Integer idArticle;
	private String codeArticle;
	private String libelleArticle;
	private Integer indice = 0;
	private String typeMouv;
	private Integer idTypeMouv;
	private String libelleMouv;
	private Boolean accepte=false;
	
	private Integer idTSupportAbonnement;
	private String  codeArticleSupport;

	public SWTArticlePoint() {
	}

	public void setSWTAdresse(SWTArticlePoint swtAdresse) {
		this.idArticlePoint = swtAdresse.idArticlePoint;

	}

	
	public static SWTArticlePoint copy(SWTArticlePoint swtAdresse){
		SWTArticlePoint swtAdresseLoc = new SWTArticlePoint();
		swtAdresseLoc.setIdArticlePoint(swtAdresse.getIdArticlePoint());                //1
		swtAdresseLoc.setIdArticle(swtAdresse.getIdArticle()); 
		swtAdresseLoc.setCodeArticle(swtAdresse.getCodeArticle()); 
		swtAdresseLoc.setDebutPeriode(swtAdresse.getDebutPeriode()); 
		swtAdresseLoc.setPoints(swtAdresse.getPoints()); 
		swtAdresseLoc.setTypeMouv(swtAdresse.getTypeMouv()); 
		swtAdresseLoc.setIdTypeMouv(swtAdresse.getIdTypeMouv()); 
		swtAdresseLoc.setLibelleMouv(swtAdresse.getLibelleMouv()); 
		swtAdresseLoc.setFinPeriode(swtAdresse.getFinPeriode());
		swtAdresseLoc.setIndice(swtAdresse.getIndice());
		swtAdresseLoc.setPrixReference(swtAdresse.getPrixReference());
		return swtAdresseLoc;
	}
	
	
	public Integer getIdArticlePoint() {
		return this.idArticlePoint;
	}

	public void setIdArticlePoint(Integer idArticlePoint) {
		firePropertyChange("idArticlePoint", this.idArticlePoint, this.idArticlePoint = idArticlePoint);
	}

	public BigDecimal getPoints() {
		return points;
	}

	public void setPoints(BigDecimal point) {
		firePropertyChange("point", this.points, this.points = point);
	}

	public Date getDebutPeriode() {
		return debutPeriode;
	}

	public void setDebutPeriode(Date datePeremption) {
		firePropertyChange("datePeremption", this.debutPeriode, this.debutPeriode = datePeremption);
	}

	public Integer getIdArticle() {
		return idArticle;
	}

	public void setIdArticle(Integer idTiers) {
		firePropertyChange("idTiers", this.idArticle, this.idArticle = idTiers);
	}

	public String getCodeArticle() {
		return codeArticle;
	}

	public void setCodeArticle(String codeTiers) {
		firePropertyChange("codeTiers", this.codeArticle, this.codeArticle = codeTiers);
	}


	public String getTypeMouv() {
		return typeMouv;
	}

	public void setTypeMouv(String typeMouv) {
		firePropertyChange("typeMouv", this.typeMouv, this.typeMouv = typeMouv);
	}


	public String getLibelleArticle() {
		return libelleArticle;
	}

	public void setLibelleArticle(String nomTiers) {
		firePropertyChange("nomTiers", this.libelleArticle, this.libelleArticle = nomTiers);
	}

	public Integer getIdTypeMouv() {
		return idTypeMouv;
	}

	public void setIdTypeMouv(Integer idTypeMouv) {
		firePropertyChange("idTypeMouv", this.idTypeMouv, this.idTypeMouv = idTypeMouv);
	}

	public String getLibelleMouv() {
		return libelleMouv;
	}

	public void setLibelleMouv(String libelleMouv) {
		firePropertyChange("libelleMouv", this.libelleMouv, this.libelleMouv = libelleMouv);
	}

	public Date getFinPeriode() {
		return finPeriode;
	}

	public void setFinPeriode(Date finPeriode) {
		firePropertyChange("finPeriode", this.finPeriode, this.finPeriode = finPeriode);
	}

	public BigDecimal getPrixReference() {
		return prixReference;
	}

	public void setPrixReference(BigDecimal prixReference) {
		firePropertyChange("prixReference", this.prixReference, this.prixReference = prixReference);
	}

	public Integer getIndice() {
		return indice;
	}

	public void setIndice(Integer indice) {
		firePropertyChange("indice", this.indice, this.indice = indice);
	}
	
	public Boolean getAccepte() {
		return accepte;
	}

	public void setAccepte(Boolean accepte) {
		firePropertyChange("accepte", this.accepte, this.accepte = accepte);
	}

	

	public Integer getIdTSupportAbonnement() {
		return idTSupportAbonnement;
	}

	public void setIdTSupportAbonnement(Integer idTSupportAbonnement) {
		firePropertyChange("idTSupportAbonnement", this.idTSupportAbonnement, this.idTSupportAbonnement = idTSupportAbonnement);
	}

	public String getCodeArticleSupport() {
		return codeArticleSupport;
	}

	public void setCodeArticleSupport(String codeArticleSupport) {
		firePropertyChange("codeArticleSupport", this.codeArticleSupport, this.codeArticleSupport = codeArticleSupport);
	}

	
	
	public SWTArticlePoint(Integer idArticlePoint, BigDecimal points,
			Date debutPeriode, Date finPeriode, BigDecimal prixReference,
			Integer idArticle, String codeArticle, String libelleArticle,
			Integer indice, String typeMouv, Integer idTypeMouv,
			String libelleMouv, Boolean accepte, Integer idTSupportAbonnement,
			String codeArticleSupport) {
		super();
		this.idArticlePoint = idArticlePoint;
		this.points = points;
		this.debutPeriode = debutPeriode;
		this.finPeriode = finPeriode;
		this.prixReference = prixReference;
		this.idArticle = idArticle;
		this.codeArticle = codeArticle;
		this.libelleArticle = libelleArticle;
		this.indice = indice;
		this.typeMouv = typeMouv;
		this.idTypeMouv = idTypeMouv;
		this.libelleMouv = libelleMouv;
		this.accepte = accepte;
		this.idTSupportAbonnement = idTSupportAbonnement;
		this.codeArticleSupport = codeArticleSupport;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accepte == null) ? 0 : accepte.hashCode());
		result = prime * result
				+ ((codeArticle == null) ? 0 : codeArticle.hashCode());
		result = prime
				* result
				+ ((codeArticleSupport == null) ? 0 : codeArticleSupport
						.hashCode());
		result = prime * result
				+ ((debutPeriode == null) ? 0 : debutPeriode.hashCode());
		result = prime * result
				+ ((finPeriode == null) ? 0 : finPeriode.hashCode());
		result = prime * result
				+ ((idArticle == null) ? 0 : idArticle.hashCode());
		result = prime * result
				+ ((idArticlePoint == null) ? 0 : idArticlePoint.hashCode());
		result = prime
				* result
				+ ((idTSupportAbonnement == null) ? 0 : idTSupportAbonnement
						.hashCode());
		result = prime * result
				+ ((idTypeMouv == null) ? 0 : idTypeMouv.hashCode());
		result = prime * result + ((indice == null) ? 0 : indice.hashCode());
		result = prime * result
				+ ((libelleArticle == null) ? 0 : libelleArticle.hashCode());
		result = prime * result
				+ ((libelleMouv == null) ? 0 : libelleMouv.hashCode());
		result = prime * result + ((points == null) ? 0 : points.hashCode());
		result = prime * result
				+ ((prixReference == null) ? 0 : prixReference.hashCode());
		result = prime * result
				+ ((typeMouv == null) ? 0 : typeMouv.hashCode());
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
		SWTArticlePoint other = (SWTArticlePoint) obj;
		if (accepte == null) {
			if (other.accepte != null)
				return false;
		} else if (!accepte.equals(other.accepte))
			return false;
		if (codeArticle == null) {
			if (other.codeArticle != null)
				return false;
		} else if (!codeArticle.equals(other.codeArticle))
			return false;
		if (codeArticleSupport == null) {
			if (other.codeArticleSupport != null)
				return false;
		} else if (!codeArticleSupport.equals(other.codeArticleSupport))
			return false;
		if (debutPeriode == null) {
			if (other.debutPeriode != null)
				return false;
		} else if (!debutPeriode.equals(other.debutPeriode))
			return false;
		if (finPeriode == null) {
			if (other.finPeriode != null)
				return false;
		} else if (!finPeriode.equals(other.finPeriode))
			return false;
		if (idArticle == null) {
			if (other.idArticle != null)
				return false;
		} else if (!idArticle.equals(other.idArticle))
			return false;
		if (idArticlePoint == null) {
			if (other.idArticlePoint != null)
				return false;
		} else if (!idArticlePoint.equals(other.idArticlePoint))
			return false;
		if (idTSupportAbonnement == null) {
			if (other.idTSupportAbonnement != null)
				return false;
		} else if (!idTSupportAbonnement.equals(other.idTSupportAbonnement))
			return false;
		if (idTypeMouv == null) {
			if (other.idTypeMouv != null)
				return false;
		} else if (!idTypeMouv.equals(other.idTypeMouv))
			return false;
		if (indice == null) {
			if (other.indice != null)
				return false;
		} else if (!indice.equals(other.indice))
			return false;
		if (libelleArticle == null) {
			if (other.libelleArticle != null)
				return false;
		} else if (!libelleArticle.equals(other.libelleArticle))
			return false;
		if (libelleMouv == null) {
			if (other.libelleMouv != null)
				return false;
		} else if (!libelleMouv.equals(other.libelleMouv))
			return false;
		if (points == null) {
			if (other.points != null)
				return false;
		} else if (!points.equals(other.points))
			return false;
		if (prixReference == null) {
			if (other.prixReference != null)
				return false;
		} else if (!prixReference.equals(other.prixReference))
			return false;
		if (typeMouv == null) {
			if (other.typeMouv != null)
				return false;
		} else if (!typeMouv.equals(other.typeMouv))
			return false;
		return true;
	}

	

	
	
	
}
package fr.legrain.documents.dao;

import java.math.BigDecimal;
import java.util.Date;

public class LigneDocumentSelection implements java.io.Serializable {
	private String code = null;
	private String libelleLigne = null;
	private String codeTiers = null;
	private String nomTiers = null;
	private String codeArticle = null;
	private Date dateDocument = null;
	private String codeTAbonnement = null;
	private Integer idLDocument = 0;
	private String unite=null;
	private BigDecimal qte=null;
	private Date debAbonnement=null;
	private Date finAbonnement=null;
	private Integer idAbonnement=null;
	private String codeSupport=null;
	private Boolean accepte=true;
	private String etat=null;



	public LigneDocumentSelection() {
	}



	public LigneDocumentSelection(String code, String libelleLigne,
			String codeTiers, String nomTiers, String codeArticle,
			Date dateDocument, String codeTAbonnement, Integer idLDocument,
			String unite, BigDecimal qte, Date debAbonnement,
			Date finAbonnement, Integer idAbonnement, String codeSupport) {
		super();
		this.code = code;
		this.libelleLigne = libelleLigne;
		this.codeTiers = codeTiers;
		this.nomTiers = nomTiers;
		this.codeArticle = codeArticle;
		this.dateDocument = dateDocument;
		this.codeTAbonnement = codeTAbonnement;
		this.idLDocument = idLDocument;
		this.unite = unite;
		this.qte = qte;
		this.debAbonnement = debAbonnement;
		this.finAbonnement = finAbonnement;
		this.idAbonnement = idAbonnement;
		this.codeSupport = codeSupport;
	}



	public LigneDocumentSelection(String code, String libelleLigne) {
		super();
		this.code = code;
		this.libelleLigne = libelleLigne;
		
	}


	public String getCode() {
		return code;
	}
	

	public void setCode(String code) {
		this.code = code;
	}
	public String getLibelleLigne() {
		return libelleLigne;
	}
	public void setLibelleLigne(String libelleLigne) {
		this.libelleLigne = libelleLigne;
	}
	public String getCodeTiers() {
		return codeTiers;
	}
	public void setCodeTiers(String codeTiers) {
		this.codeTiers = codeTiers;
	}
	public String getNomTiers() {
		return nomTiers;
	}
	public void setNomTiers(String nomTiers) {
		this.nomTiers = nomTiers;
	}
	public Date getDateDocument() {
		return dateDocument;
	}
	public void setDateDocument(Date dateDocument) {
		this.dateDocument = dateDocument;
	}
	public String getCodeArticle() {
		return codeArticle;
	}

	public void setCodeArticle(String codeArticle) {
		this.codeArticle = codeArticle;
	}

	public String getCodeTAbonnement() {
		return codeTAbonnement;
	}

	public void setCodeTAbonnement(String codeTSupport) {
		this.codeTAbonnement = codeTSupport;
	}

	public Integer getIdLDocument() {
		return idLDocument;
	}

	public void setIdLDocument(Integer idLDocument) {
		this.idLDocument = idLDocument;
	}


	public String getUnite() {
		return unite;
	}


	public void setUnite(String unite) {
		this.unite = unite;
	}


	public BigDecimal getQte() {
		return qte;
	}


	public void setQte(BigDecimal qte) {
		this.qte = qte;
	}


	public Date getDebAbonnement() {
		return debAbonnement;
	}


	public void setDebAbonnement(Date debAbonnement) {
		this.debAbonnement = debAbonnement;
	}


	public Date getFinAbonnement() {
		return finAbonnement;
	}


	public void setFinAbonnement(Date finAbonnement) {
		this.finAbonnement = finAbonnement;
	}


	public Boolean getAccepte() {
		return accepte;
	}


	public void setAccepte(Boolean accepte) {
		this.accepte = accepte;
	}


	public Integer getIdAbonnement() {
		return idAbonnement;
	}


	public void setIdAbonnement(Integer idAbonnement) {
		this.idAbonnement = idAbonnement;
	}


	public String getCodeSupport() {
		return codeSupport;
	}

	public void setCodeSupport(String codeSupport) {
		this.codeSupport = codeSupport;
	}




	public String getEtat() {
		return etat;
	}



	public void setEtat(String etat) {
		this.etat = etat;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accepte == null) ? 0 : accepte.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result
				+ ((codeArticle == null) ? 0 : codeArticle.hashCode());
		result = prime * result
				+ ((codeSupport == null) ? 0 : codeSupport.hashCode());
		result = prime * result
				+ ((codeTAbonnement == null) ? 0 : codeTAbonnement.hashCode());
		result = prime * result
				+ ((codeTiers == null) ? 0 : codeTiers.hashCode());
		result = prime * result
				+ ((dateDocument == null) ? 0 : dateDocument.hashCode());
		result = prime * result
				+ ((debAbonnement == null) ? 0 : debAbonnement.hashCode());
		result = prime * result + ((etat == null) ? 0 : etat.hashCode());
		result = prime * result
				+ ((finAbonnement == null) ? 0 : finAbonnement.hashCode());
		result = prime * result
				+ ((idAbonnement == null) ? 0 : idAbonnement.hashCode());
		result = prime * result
				+ ((idLDocument == null) ? 0 : idLDocument.hashCode());
		result = prime * result
				+ ((libelleLigne == null) ? 0 : libelleLigne.hashCode());
		result = prime * result
				+ ((nomTiers == null) ? 0 : nomTiers.hashCode());
		result = prime * result + ((qte == null) ? 0 : qte.hashCode());
		result = prime * result + ((unite == null) ? 0 : unite.hashCode());
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
		LigneDocumentSelection other = (LigneDocumentSelection) obj;
		if (accepte == null) {
			if (other.accepte != null)
				return false;
		} else if (!accepte.equals(other.accepte))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (codeArticle == null) {
			if (other.codeArticle != null)
				return false;
		} else if (!codeArticle.equals(other.codeArticle))
			return false;
		if (codeSupport == null) {
			if (other.codeSupport != null)
				return false;
		} else if (!codeSupport.equals(other.codeSupport))
			return false;
		if (codeTAbonnement == null) {
			if (other.codeTAbonnement != null)
				return false;
		} else if (!codeTAbonnement.equals(other.codeTAbonnement))
			return false;
		if (codeTiers == null) {
			if (other.codeTiers != null)
				return false;
		} else if (!codeTiers.equals(other.codeTiers))
			return false;
		if (dateDocument == null) {
			if (other.dateDocument != null)
				return false;
		} else if (!dateDocument.equals(other.dateDocument))
			return false;
		if (debAbonnement == null) {
			if (other.debAbonnement != null)
				return false;
		} else if (!debAbonnement.equals(other.debAbonnement))
			return false;
		if (etat == null) {
			if (other.etat != null)
				return false;
		} else if (!etat.equals(other.etat))
			return false;
		if (finAbonnement == null) {
			if (other.finAbonnement != null)
				return false;
		} else if (!finAbonnement.equals(other.finAbonnement))
			return false;
		if (idAbonnement == null) {
			if (other.idAbonnement != null)
				return false;
		} else if (!idAbonnement.equals(other.idAbonnement))
			return false;
		if (idLDocument == null) {
			if (other.idLDocument != null)
				return false;
		} else if (!idLDocument.equals(other.idLDocument))
			return false;
		if (libelleLigne == null) {
			if (other.libelleLigne != null)
				return false;
		} else if (!libelleLigne.equals(other.libelleLigne))
			return false;
		if (nomTiers == null) {
			if (other.nomTiers != null)
				return false;
		} else if (!nomTiers.equals(other.nomTiers))
			return false;
		if (qte == null) {
			if (other.qte != null)
				return false;
		} else if (!qte.equals(other.qte))
			return false;
		if (unite == null) {
			if (other.unite != null)
				return false;
		} else if (!unite.equals(other.unite))
			return false;
		return true;
	}		




	
}

package fr.legrain.documents.dao;

//Generated Apr 9, 2009 12:40:07 PM by Hibernate Tools 3.2.0.CR1

import java.math.BigDecimal;

public class TaSumTaLAvisEcheance implements java.io.Serializable {



	private BigDecimal mtHtLApresRemiseGlobaleDocument;
	private Integer idTAbonnement;
	private String codeTAbonnement;
	private Integer nbLigne;
	
	
	public TaSumTaLAvisEcheance() {
		super();
	}

	public TaSumTaLAvisEcheance(BigDecimal mtHtLApresRemiseGlobaleDocument,
			Integer idTAbonnement, String codeTAbonnement, Integer nbLigne) {
		super();
		this.mtHtLApresRemiseGlobaleDocument = mtHtLApresRemiseGlobaleDocument;
		this.idTAbonnement = idTAbonnement;
		this.codeTAbonnement = codeTAbonnement;
		this.nbLigne = nbLigne;
	}
	
	public BigDecimal getMtHtLApresRemiseGlobaleDocument() {
		return mtHtLApresRemiseGlobaleDocument;
	}
	public void setMtHtLApresRemiseGlobaleDocument(
			BigDecimal mtHtLApresRemiseGlobaleDocument) {
		this.mtHtLApresRemiseGlobaleDocument = mtHtLApresRemiseGlobaleDocument;
	}
	public Integer getIdTAbonnement() {
		return idTAbonnement;
	}
	public void setIdTAbonnement(Integer idTAbonnement) {
		this.idTAbonnement = idTAbonnement;
	}
	public String getCodeTAbonnement() {
		return codeTAbonnement;
	}
	public void setCodeTAbonnement(String codeTAbonnement) {
		this.codeTAbonnement = codeTAbonnement;
	}
	public Integer getNbLigne() {
		return nbLigne;
	}
	public void setNbLigne(Integer nbLigne) {
		this.nbLigne = nbLigne;
	}
	



}

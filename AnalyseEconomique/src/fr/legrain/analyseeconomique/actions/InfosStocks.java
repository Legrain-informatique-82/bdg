package fr.legrain.analyseeconomique.actions;

public class InfosStocks {

	private String origine ="Stocks";
	private String compte;
	private String designation;
	private String qte;
	private String prixUnitaire;
	private String decote;
	private String montantHT;
	
	public InfosStocks() {
		super();
	}

	public InfosStocks(String origine, String compte, String designation, String qte, String prixUnitaire, String decote, String montantHT) {
		super();
		this.origine = origine;
		this.compte = compte;
		this.designation = designation;
		this.qte = qte;
		this.prixUnitaire = prixUnitaire;
		this.decote = decote;
		this.montantHT = montantHT;
	}
	
	public String getCompte() {
		return compte;
	}
	
	public void setCompte(String compte) {
		this.compte = compte;
	}
	
	public String getDecote() {
		return decote;
	}
	
	public void setDecote(String decote) {
		this.decote = decote;
	}
	
	public String getDesignation() {
		return designation;
	}
	
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	public String getMontantHT() {
		return montantHT;
	}
	
	public void setMontantHT(String montantHT) {
		this.montantHT = montantHT;
	}
	
	public String getOrigine() {
		return origine;
	}
	
	public void setOrigine(String origine) {
		this.origine = origine;
	}
	
	public String getPrixUnitaire() {
		return prixUnitaire;
	}
	
	public void setPrixUnitaire(String prixUnitaire) {
		this.prixUnitaire = prixUnitaire;
	}
	
	public String getQte() {
		return qte;
	}
	
	public void setQte(String qte) {
		this.qte = qte;
	}
}

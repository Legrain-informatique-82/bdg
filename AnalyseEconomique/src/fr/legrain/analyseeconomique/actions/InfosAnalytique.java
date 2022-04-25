package fr.legrain.analyseeconomique.actions;

public class InfosAnalytique {

	private String origine = "Analytique";
	private String atelier;
	private String libelleAtelier;
	private String compte;
	private String designation;
	private String totalCharges;
	private String totalProduits;
	private String qt1;
	private String pu1;
	private String qt2;
	private String pu2;
	private String codeActivite;
	private String libelleActivite;
	private String nbUniteAtelier;
	
	public InfosAnalytique() {
		super();
	}
	
	public InfosAnalytique(String origine, String atelier, String libelleAtelier, String compte, String designation, String totalCharges, String totalProduits, String qt1, String pu1, String qt2, String pu2, String codeActivite, String libelleActivite, String nbUniteAtelier) {
		super();
		this.origine = origine;
		this.atelier = atelier;
		this.libelleAtelier = libelleAtelier;
		this.compte = compte;
		this.designation = designation;
		this.totalCharges = totalCharges;
		this.totalProduits = totalProduits;
		this.qt1 = qt1;
		this.pu1 = pu1;
		this.qt2 = qt2;
		this.pu2 = pu2;
		this.codeActivite = codeActivite;
		this.libelleActivite = libelleActivite;
		this.nbUniteAtelier = nbUniteAtelier;
	}
	
	public String getAtelier() {
		return atelier;
	}
	
	public void setAtelier(String atelier) {
		this.atelier = atelier;
	}
	
	public String getCodeActivite() {
		return codeActivite;
	}
	
	public void setCodeActivite(String codeActivite) {
		this.codeActivite = codeActivite;
	}
	
	public String getCompte() {
		return compte;
	}
	
	public void setCompte(String compte) {
		this.compte = compte;
	}
	
	public String getDesignation() {
		return designation;
	}
	
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	public String getLibelleActivite() {
		return libelleActivite;
	}
	
	public void setLibelleActivite(String libelleActivite) {
		this.libelleActivite = libelleActivite;
	}
	
	public String getLibelleAtelier() {
		return libelleAtelier;
	}
	
	public void setLibelleAtelier(String libelleAtelier) {
		this.libelleAtelier = libelleAtelier;
	}
	
	public String getOrigine() {
		return origine;
	}
	
	public void setOrigine(String origine) {
		this.origine = origine;
	}
	
	public String getPu1() {
		return pu1;
	}
	
	public void setPu1(String pu1) {
		this.pu1 = pu1;
	}
	
	public String getPu2() {
		return pu2;
	}
	
	public void setPu2(String pu2) {
		this.pu2 = pu2;
	}
	
	public String getQt1() {
		return qt1;
	}
	
	public void setQt1(String qt1) {
		this.qt1 = qt1;
	}
	
	public String getQt2() {
		return qt2;
	}
	
	public void setQt2(String qt2) {
		this.qt2 = qt2;
	}
	
	public String getTotalCharges() {
		return totalCharges;
	}
	
	public void setTotalCharges(String totalCharges) {
		this.totalCharges = totalCharges;
	}
	
	public String getTotalProduits() {
		return totalProduits;
	}
	
	public void setTotalProduits(String totalProduits) {
		this.totalProduits = totalProduits;
	}

	public String getNbUniteAtelier() {
		return nbUniteAtelier;
	}

	public void setNbUniteAtelier(String nbUniteAtelier) {
		this.nbUniteAtelier = nbUniteAtelier;
	}
	
}

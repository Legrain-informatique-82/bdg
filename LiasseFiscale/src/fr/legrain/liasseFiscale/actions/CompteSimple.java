package fr.legrain.liasseFiscale.actions;

public class CompteSimple {
	private String numero = null;
	private String libelle = null;
	private Double mtDebit = null;
	private Double mtCredit = null;
	private Double solde = null;
	private String origine = null;
	
	public CompteSimple() {
	}
	
	public CompteSimple(String numero) {
		this.numero = numero;
	}
	
	public CompteSimple(String origine, String numero, String libelle, Double mtDebit, Double mtCredit) {
		this.origine = origine;
		this.numero = numero;
		this.libelle = libelle;
		this.mtDebit = mtDebit;
		this.mtCredit = mtCredit;
	}
	
	public CompteSimple(String origine, String numero, String libelle, Double mtDebit, Double mtCredit, Double solde) {
		this.origine = origine;
		this.numero = numero;
		this.libelle = libelle;
		this.mtDebit = mtDebit;
		this.mtCredit = mtCredit;
		this.solde = solde;
	}
	
	public CompteSimple(String origine, String numero, String libelle, Double mtDebit, Double mtCredit, Double solde, Double mtDebit2, Double mtCredit2, Double solde2) {
		this.origine = origine;
		this.numero = numero;
		this.libelle = libelle;
		this.mtDebit = mtDebit;
		this.mtCredit = mtCredit;
		this.solde = solde;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public Double getMtCredit() {
		return mtCredit;
	}

	public void setMtCredit(Double mtCredit) {
		this.mtCredit = mtCredit;
	}

	public Double getMtDebit() {
		return mtDebit;
	}

	public void setMtDebit(Double mtDebit) {
		this.mtDebit = mtDebit;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getOrigine() {
		return origine;
	}

	public void setOrigine(String origine) {
		this.origine = origine;
	}

	public Double getSolde() {
		return solde;
	}

	public void setSolde(Double solde) {
		this.solde = solde;
	}
	
}

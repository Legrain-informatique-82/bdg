package fr.legrain.analyseeconomique.actions;


public class InfosGrdLivreQte {
	private String origine;
	private String compte;
	private String qte;
//	private String debit;
//	private String credit;
//	private String qte2;

	public InfosGrdLivreQte() {
		super();
	}
	
	public InfosGrdLivreQte(String origine, String compte, String qte) {
		this.origine = origine;
		this.compte = compte;
		this.qte = qte;
	}

	public String getCompte() {
		return compte;
	}

	public void setCompte(String compte) {
		this.compte = compte;
	}

	public String getQte() {
		return qte;
	}

	public void setQte(String qte) {
		this.qte = qte;
	}

	public String getOrigine() {
		return origine;
	}

	public void setOrigine(String origine) {
		this.origine = origine;
	}

}

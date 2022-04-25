package fr.legrain.bonlivraison.divers;

import java.math.BigDecimal;

import fr.legrain.documents.dao.LigneTva;
import fr.legrain.lib.data.LibCalcul;
import fr.legrain.lib.data.ModelObject;

public class IHMLignesTVABL extends ModelObject{
	private String code;
	private String libelle;
	private BigDecimal taux;
	private BigDecimal montant;
	
	public String getcode() {
		return code;
	}
	
	public void setcode(String code) {
		firePropertyChange("code", this.code, this.code = code);
	}
	
	public String getlibelle() {
		return libelle;
	}
	
	public void setlibelle(String libelle) {
		firePropertyChange("libelle", this.libelle, this.libelle = libelle);
	}
	
	public BigDecimal getmontant() {
		return montant;
	}
	
	public void setmontant(BigDecimal montant) {
		firePropertyChange("montant", this.montant, this.montant = montant);
	}
	
	public BigDecimal gettaux() {
		return taux;
	}
	
	public void settaux(BigDecimal taux) {
		firePropertyChange("taux", this.taux, this.taux = taux);
	}
	
	public IHMLignesTVABL setIHMDevisLignesTVA(LigneTva swtLigneDevis){
		setcode(swtLigneDevis.getCodeTva());
		setlibelle(swtLigneDevis.getLibelle());
		setmontant(LibCalcul.arrondi(swtLigneDevis.getMtTva()));
		settaux(swtLigneDevis.getTauxTva());
		return this;
	}

}

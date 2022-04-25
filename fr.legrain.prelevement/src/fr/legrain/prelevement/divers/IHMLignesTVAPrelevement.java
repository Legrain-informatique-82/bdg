package fr.legrain.prelevement.divers;

import java.math.BigDecimal;

import fr.legrain.documents.dao.LigneTva;
import fr.legrain.lib.data.LibCalcul;
import fr.legrain.lib.data.ModelObject;

public class IHMLignesTVAPrelevement extends ModelObject{
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
	
	public IHMLignesTVAPrelevement setIHMPrelevementLignesTVA(LigneTva swtLigneProforma){
		setcode(swtLigneProforma.getCodeTva());
		setlibelle(swtLigneProforma.getLibelle());
		setmontant(LibCalcul.arrondi(swtLigneProforma.getMtTva()));
		settaux(swtLigneProforma.getTauxTva());
		return this;
	}

}

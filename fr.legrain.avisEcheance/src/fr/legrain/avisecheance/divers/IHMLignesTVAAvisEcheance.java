package fr.legrain.avisecheance.divers;

import java.math.BigDecimal;

import fr.legrain.documents.dao.LigneTva;
import fr.legrain.lib.data.LibCalcul;
import fr.legrain.lib.data.ModelObject;

public class IHMLignesTVAAvisEcheance extends ModelObject{
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
	
	public IHMLignesTVAAvisEcheance setIHMAvisEcheanceLignesTVA(LigneTva swtLigneAvisEcheance){
		setcode(swtLigneAvisEcheance.getCodeTva());
		setlibelle(swtLigneAvisEcheance.getLibelle());
		setmontant(LibCalcul.arrondi(swtLigneAvisEcheance.getMtTva()));
		settaux(swtLigneAvisEcheance.getTauxTva());
		return this;
	}

}

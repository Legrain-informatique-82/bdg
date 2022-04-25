package fr.legrain.calcultva.divers;

import javax.swing.JComponent;

import fr.legrain.lib.gui.ParamAffiche;

public class ParamAideCalcTVA extends ParamAffiche {
	private String titreFormulaire = "";
	private String titreGrille = "";
	private String sousTitre = "";
	private JComponent focusDefaut = null;
	private String taux = null;
	private String mtTTC = null;
	private String mtHT = null;
	private String mtTVA = null;
	
	
	public JComponent getFocusDefaut() {
		return focusDefaut;
	}
	public void setFocusDefaut(JComponent focusDefaut) {
		this.focusDefaut = focusDefaut;
	}
	public String getSousTitre() {
		return sousTitre;
	}
	public void setSousTitre(String sousTitre) {
		this.sousTitre = sousTitre;
	}
	public String getTitreFormulaire() {
		return titreFormulaire;
	}
	public void setTitreFormulaire(String titreFormulaire) {
		this.titreFormulaire = titreFormulaire;
	}
	public String getTitreGrille() {
		return titreGrille;
	}
	public void setTitreGrille(String titreGrille) {
		this.titreGrille = titreGrille;
	}
	public String getMtHT() {
		return mtHT;
	}
	public void setMtHT(String mtHT) {
		this.mtHT = mtHT;
	}
	public String getMtTTC() {
		return mtTTC;
	}
	public void setMtTTC(String mtTTC) {
		this.mtTTC = mtTTC;
	}
	public String getMtTVA() {
		return mtTVA;
	}
	public void setMtTVA(String mtTVA) {
		this.mtTVA = mtTVA;
	}
	public String getTaux() {
		return taux;
	}
	public void setTaux(String taux) {
		this.taux = taux;
	} 
}

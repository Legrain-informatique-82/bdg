package fr.legrain.saisiecaisse.divers;

import javax.swing.JComponent;

import fr.legrain.lib.gui.ParamAffiche;

public class ParamImpressionSaisieCaisse extends ParamAffiche {
	private String titreFormulaire = "";
	private String titreGrille = "";
	private String sousTitre = "";
	private JComponent focusDefaut = null;
	private boolean synthese = false;
	public String idEtablissement = null;
	
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
	public boolean isSynthese() {
		return synthese;
	}
	public void setSynthese(boolean synthese) {
		this.synthese = synthese;
	}
	public String getIdEtablissement() {
		return idEtablissement;
	}
	public void setIdEtablissement(String idEtablissement) {
		this.idEtablissement = idEtablissement;
	} 
}

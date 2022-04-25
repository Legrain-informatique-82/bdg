package fr.legrain.tiers.ecran;

import javax.swing.JComponent;

import org.eclipse.swt.widgets.Control;

import fr.legrain.lib.gui.ParamAffiche;

public class ParamAfficheTelephone extends ParamAffiche {
	private String titreFormulaire = "Fiche téléphone";
	private String titreGrille = "Liste des numéros de téléphone";
	private String sousTitre = "Gestion des numéros de téléphone";
	private JComponent focusDefaut = null;
	private Control focusDefautSWT = null;
	private Integer idTiers = null;
	
	
	public Integer getIdTiers() {
		return idTiers;
	}
	public void setIdTiers(Integer idTiers) {
		this.idTiers = idTiers;
	}
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
	public Control getFocusDefautSWT() {
		return focusDefautSWT;
	}
	public void setFocusDefautSWT(Control focusDefautSWT) {
		this.focusDefautSWT = focusDefautSWT;
	} 
}

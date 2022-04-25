package fr.legrain.tiers.ecran;

import javax.swing.JComponent;

import org.eclipse.swt.widgets.Control;

import fr.legrain.lib.gui.ParamAffiche;

public class ParamAfficheTiers extends ParamAffiche {
	private String idTiers = null;
	private String idTypeTiers = null;
	
	public static final String C_TITRE_FORMULAIRE = "Fiche tiers";
	public static final String C_TITRE_GRILLE = "Liste des tiers";
	public static final String C_SOUS_TITRE = "Gestion des tiers";
	
	
	public ParamAfficheTiers() {
		titreFormulaire = C_TITRE_FORMULAIRE;
		titreGrille = C_TITRE_GRILLE;
		sousTitre = C_SOUS_TITRE;
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
	public String getIdTiers() {
		return idTiers;
	}
	public void setIdTiers(String idTiers) {
		this.idTiers = idTiers;
	}
	public String getIdTypeTiers() {
		return idTypeTiers;
	}
	public void setIdTypeTiers(String idTypeTiers) {
		this.idTypeTiers = idTypeTiers;
	}
	public Control getFocusDefautSWT() {
		return focusDefautSWT;
	}
	public void setFocusDefautSWT(Control focusDefautSWT) {
		this.focusDefautSWT = focusDefautSWT;
	}


}

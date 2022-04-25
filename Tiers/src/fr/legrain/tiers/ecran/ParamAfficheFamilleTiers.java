package fr.legrain.tiers.ecran;

import javax.swing.JComponent;

import org.eclipse.swt.widgets.Control;

import fr.legrain.lib.gui.ParamAffiche;

public class ParamAfficheFamilleTiers extends ParamAffiche {
	public static final String C_TITRE_FORMULAIRE = "Fiche famille";
	public static final String C_TITRE_GRILLE = "liste des familles";
	public static final String C_SOUS_TITRE = "Liste des familles";
	private JComponent focusDefaut = null;
	private Control focusDefautSWT = null;

	public ParamAfficheFamilleTiers() {
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
	
	public Control getFocusDefautSWT() {
		return focusDefautSWT;
	}
	
	public void setFocusDefautSWT(Control focusDefautSWT) {
		this.focusDefautSWT = focusDefautSWT;
	} 
}

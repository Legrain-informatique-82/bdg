package fr.legrain.articles.ecran;

import javax.swing.JComponent;

import org.eclipse.swt.widgets.Control;

import fr.legrain.lib.gui.ParamAffiche;

public class ParamAfficheFournisseurArticle extends ParamAffiche {
	public static final String C_TITRE_FORMULAIRE = "Fiche fournisseur";
	public static final String C_TITRE_GRILLE = "Liste des fournisseurs";
	public static final String C_SOUS_TITRE = "Gestion des fournisseurs";
	private JComponent focusDefaut = null;
	private String idArticle = null;
	private Control focusDefautSWT = null;

	public ParamAfficheFournisseurArticle() {
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
	
	public String getIdArticle() {
		return idArticle;
	}
	
	public void setIdArticle(String idArticle) {
		this.idArticle = idArticle;
	}
	
	public Control getFocusDefautSWT() {
		return focusDefautSWT;
	}
	
	public void setFocusDefautSWT(Control focusDefautSWT) {
		this.focusDefautSWT = focusDefautSWT;
	} 
}

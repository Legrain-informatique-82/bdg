package fr.legrain.articles.ecran;

import javax.swing.JComponent;

import org.eclipse.swt.widgets.Control;

import fr.legrain.lib.gui.ParamAffiche;

public class ParamAfficheNoteArticle extends ParamAffiche {
	private String titreFormulaire = "Fiche note";
	private String titreGrille = "Liste des notes";
	private String sousTitre = "Gestion des notes";
	private JComponent focusDefaut = null;
	private Control focusDefautSWT = null;
	private Integer idArticle = null;
	
	
	public Integer getIdArticle() {
		return idArticle;
	}
	public void setIdArticle(Integer idTiers) {
		this.idArticle = idTiers;
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

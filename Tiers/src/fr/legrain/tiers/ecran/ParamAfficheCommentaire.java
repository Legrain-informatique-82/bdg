package fr.legrain.tiers.ecran;

import javax.swing.JComponent;

import fr.legrain.lib.gui.ParamAffiche;

public class ParamAfficheCommentaire extends ParamAffiche {
private String titreFormulaire = "Fiche Commentaire";
private String titreGrille = "Liste des Commentaires";
private String sousTitre = "Gestion des Commentaires";
private JComponent focusDefaut = null;
private String idTiers = null;


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
}

package fr.legrain.acompte.divers;

import javax.swing.JComponent;

import fr.legrain.lib.gui.ParamAffiche;

public class ParamAfficheLAcompte extends ParamAffiche {
private String titreFormulaire = "Fiche ligne de devis";
private String titreGrille = "liste des lignes de devis";
private String sousTitre = "Liste des lignes de devis";
private JComponent focusDefaut = null;
private String idAcompte = null;
private Boolean InitFocus = true;


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
public String getIdAcompte() {
	return idAcompte;
}
public void setIdAcompte(String idDevis) {
	this.idAcompte = idDevis;
}
public Boolean getInitFocus() {
	return InitFocus;
}
public void setInitFocus(Boolean initFocus) {
	InitFocus = initFocus;
} 
}

package fr.legrain.facture.divers;

import javax.swing.JComponent;

import fr.legrain.lib.gui.ParamAffiche;

public class ParamAfficheLFacture extends ParamAffiche {
private String titreFormulaire = "Fiche ligne de facture";
private String titreGrille = "liste des lignes de facture";
private String sousTitre = "Liste des lignes de facture";
private JComponent focusDefaut = null;
private String idFacture = null;
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
public String getIdFacture() {
	return idFacture;
}
public void setIdFacture(String idArticle) {
	this.idFacture = idArticle;
}
public Boolean getInitFocus() {
	return InitFocus;
}
public void setInitFocus(Boolean initFocus) {
	InitFocus = initFocus;
} 
}

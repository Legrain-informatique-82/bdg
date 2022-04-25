package fr.legrain.bonlivraison.divers;

import javax.swing.JComponent;

import fr.legrain.lib.gui.ParamAffiche;

public class ParamAfficheLBL extends ParamAffiche {
private String titreFormulaire = "Fiche ligne du bon de livraison";
private String titreGrille = "liste des lignes du bon de livraison";
private String sousTitre = "Liste des lignes du bon de livraison";
private JComponent focusDefaut = null;
private String idBonliv = null;
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
public String getIdBonliv() {
	return idBonliv;
}
public void setIdBonliv(String idDevis) {
	this.idBonliv = idDevis;
}
public Boolean getInitFocus() {
	return InitFocus;
}
public void setInitFocus(Boolean initFocus) {
	InitFocus = initFocus;
} 
}

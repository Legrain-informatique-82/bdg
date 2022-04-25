package fr.legrain.avisecheance.divers;

import javax.swing.JComponent;

import fr.legrain.lib.gui.ParamAffiche;

public class ParamAfficheLAvisEcheance extends ParamAffiche {
private String titreFormulaire = "Fiche ligne d'avis d'échéance";
private String titreGrille = "liste des lignes d'avis d'échéance";
private String sousTitre = "Liste des lignes d'avis d'échéance";
private JComponent focusDefaut = null;
private String idDevis = null;
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
public String getIdDevis() {
	return idDevis;
}
public void setIdDevis(String idDevis) {
	this.idDevis = idDevis;
}
public Boolean getInitFocus() {
	return InitFocus;
}
public void setInitFocus(Boolean initFocus) {
	InitFocus = initFocus;
} 
}

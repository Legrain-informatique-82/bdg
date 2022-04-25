package fr.legrain.apporteur.divers;

import javax.swing.JComponent;

import fr.legrain.lib.gui.ParamAffiche;

public class ParamAfficheLApporteur extends ParamAffiche {
private String titreFormulaire = "Fiche ligne de facture apporteur";
private String titreGrille = "liste des lignes de facture apporteur";
private String sousTitre = "Liste des lignes de facture apporteur";
private JComponent focusDefaut = null;
private String codeDocument = null;
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
public String getCodeDocument() {
	return codeDocument;
}
public void setCodeDocument(String codeDocument) {
	this.codeDocument = codeDocument;
}
public Boolean getInitFocus() {
	return InitFocus;
}
public void setInitFocus(Boolean initFocus) {
	InitFocus = initFocus;
} 
}

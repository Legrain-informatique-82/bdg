package fr.legrain.gestCom.librairiesEcran.swt;

import org.eclipse.swt.widgets.Control;

import fr.legrain.lib.gui.ParamAffiche;

public class ParamAfficheVisualisation extends ParamAffiche {
	private String titreSelection = "Vos critères de recherche";
	private String titreResultat = "Résultat de la recherche";
	private String titreHistorique = "Historique de recherche";
	private String titreFenetre = "RECHERCHES ET VISUALISATIONS";
	private String sousTitre = "Recherche et visualistion";
	private Control focusDefautSWT = null;
	private String nomRequete = null;
	private String nomClassController = null;
	private String module = null;
	
	public String getSousTitre() {
		return sousTitre;
	}
	
	public void setSousTitre(String sousTitre) {
		this.sousTitre = sousTitre;
	}
	
	
	public Control getFocusDefautSWT() {
		return focusDefautSWT;
	}
	
	public void setFocusDefautSWT(Control focusDefautSWT) {
		this.focusDefautSWT = focusDefautSWT;
	}

	public String getTitreSelection() {
		return titreSelection;
	}

	public void setTitreSelection(String titreSelection) {
		this.titreSelection = titreSelection;
	}

	public String getTitreResultat() {
		return titreResultat;
	}

	public void setTitreResultat(String titreResultat) {
		this.titreResultat = titreResultat;
	}

	public String getTitreHistorique() {
		return titreHistorique;
	}

	public void setTitreHistorique(String titreHistorique) {
		this.titreHistorique = titreHistorique;
	}

	public String getTitreFenetre() {
		return titreFenetre;
	}

	public void setTitreFenetre(String titreFenetre) {
		this.titreFenetre = titreFenetre;
	}

	public String getNomRequete() {
		return nomRequete;
	}

	public void setNomRequete(String nomRequete) {
		this.nomRequete = nomRequete;
	}

	public String getNomClassController() {
		return nomClassController;
	}

	public void setNomClassController(String nomClassController) {
		this.nomClassController = nomClassController;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	} 
}

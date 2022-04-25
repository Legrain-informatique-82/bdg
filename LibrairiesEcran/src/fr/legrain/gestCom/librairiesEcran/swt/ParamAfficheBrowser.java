package fr.legrain.gestCom.librairiesEcran.swt;

import javax.swing.JComponent;

import org.eclipse.swt.widgets.Control;

import fr.legrain.lib.gui.ParamAffiche;

public class ParamAfficheBrowser extends ParamAffiche {
//	public static final String C_TITRE_FORMULAIRE = "Fiche Article";
//	public static final String C_TITRE_GRILLE = "liste des Articles";
//	public static final String C_SOUS_TITRE = "Liste des Articles";
	public String url = null;
	public String postData = null;
	public String[] httpHeader = null;
	public String titre = null;
	
	
	public ParamAfficheBrowser() {
//		titreFormulaire = C_TITRE_FORMULAIRE;
//		titreGrille = C_TITRE_GRILLE;
//		sousTitre = C_SOUS_TITRE;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getPostData() {
		return postData;
	}

	public void setPostData(String postData) {
		this.postData = postData;
	}

	public String[] getHttpHeader() {
		return httpHeader;
	}

	public void setHttpHeader(String[] httpHeader) {
		this.httpHeader = httpHeader;
	} 
}

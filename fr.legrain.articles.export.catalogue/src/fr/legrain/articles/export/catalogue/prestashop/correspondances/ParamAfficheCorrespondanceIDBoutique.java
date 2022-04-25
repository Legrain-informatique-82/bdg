package fr.legrain.articles.export.catalogue.prestashop.correspondances;

import javax.swing.JComponent;

import org.eclipse.swt.widgets.Control;

import fr.legrain.lib.gui.ParamAffiche;

public class ParamAfficheCorrespondanceIDBoutique extends ParamAffiche {
	private String titreFormulaire = "";
	private String titreGrille = "Liste des correspondances";
	private String sousTitre = "Gestion des correspondances entre Bureau de Gestion et la boutique";
	private JComponent focusDefaut = null;
	private Control focusDefautSWT = null;
	private Integer idTiers = null;
	private Integer idType = null;
	private boolean conditionTiers = true; //true => condition de paiement pour les tiers, false => cond. paiement/echéance pour les documents
	
	public JComponent getFocusDefaut() {
		return focusDefaut;
	}
	
	public void setFocusDefaut(JComponent focusDefaut) {
		this.focusDefaut = focusDefaut;
	}
	
	public String getSousTitre() {
		return sousTitre;
	}
	
	public Integer getIdTiers() {
		return idTiers;
	}
	
	public void setIdTiers(Integer idTiers) {
		this.idTiers = idTiers;
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

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idTypeCond) {
		this.idType = idTypeCond;
	}

	public boolean isConditionTiers() {
		return conditionTiers;
	}

	public void setConditionTiers(boolean conditionTiers) {
		this.conditionTiers = conditionTiers;
	} 
}

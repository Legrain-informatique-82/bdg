package fr.legrain.tiers.ecran;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import org.eclipse.swt.widgets.Control;

import fr.legrain.lib.gui.ParamAffiche;

public class ParamAfficheParamCreeDocTiers extends ParamAffiche {
	private String titreFormulaire = "Fiche paramètre pour la création d'un type de document sur une selection d'un autre type ";
	private String titreGrille = "Fiche des paramètres pour la création d'un type de document sur une selection d'un autre type";
	private String sousTitre = "Gestion des paramètres";
	private JComponent focusDefaut = null;
	private Control focusDefautSWT = null;
	private Integer idTiers = null;
	private List<String> listeTypeNoteAAfficher = new ArrayList<String>();
	
	
	public Integer getIdTiers() {
		return idTiers;
	}
	public void setIdTiers(Integer idTiers) {
		this.idTiers = idTiers;
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
	public List<String> getListeTypeNoteAAfficher() {
		return listeTypeNoteAAfficher;
	}
	public void setListeTypeNoteAAfficher(List<String> listeTypeNoteAAfficher) {
		this.listeTypeNoteAAfficher = listeTypeNoteAAfficher;
	} 
}

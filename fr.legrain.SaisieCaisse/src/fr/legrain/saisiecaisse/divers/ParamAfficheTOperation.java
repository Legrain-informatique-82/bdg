package fr.legrain.saisiecaisse.divers;

import javax.swing.JComponent;

import org.eclipse.swt.widgets.Control;

import fr.legrain.lib.gui.ParamAffiche;

public class ParamAfficheTOperation extends ParamAffiche {
	public static final String C_TITRE_FORMULAIRE = "Fiche type d'opération";
	public static final String C_TITRE_GRILLE = "liste des types d'opération";
	public static final String C_SOUS_TITRE = "Liste des types d'opération";
	private JComponent focusDefaut = null;
	private Control focusDefautSWT = null;

	public JComponent getFocusDefaut() {
		return focusDefaut;
	}
	
	public void setFocusDefaut(JComponent focusDefaut) {
		this.focusDefaut = focusDefaut;
	}
	

	
	public Control getFocusDefautSWT() {
		return focusDefautSWT;
	}
	
	public void setFocusDefautSWT(Control focusDefautSWT) {
		this.focusDefautSWT = focusDefautSWT;
	} 
}

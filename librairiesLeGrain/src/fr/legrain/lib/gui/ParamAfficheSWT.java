package fr.legrain.lib.gui;

import org.eclipse.swt.widgets.Shell;

/**
 * <p>Title: </p>
 * <p>Description: Parametre pour les fonctions d'affichage.</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Le Grain SA</p>
 * @author nicolas
 * @version 1.0
 */

public class ParamAfficheSWT {
	
	private int shellStyle;
	private String titre = "";
	private int hauteur;
	private int largeur;
	
	public ParamAfficheSWT() {
	}
	
	public int getHauteur() {
		return hauteur;
	}
	
	public void setHauteur(int hauteur) {
		this.hauteur = hauteur;
	}
	
	public int getLargeur() {
		return largeur;
	}
	
	public void setLargeur(int largeur) {
		this.largeur = largeur;
	}
	
	public int getShellStyle() {
		return shellStyle;
	}
	
	public void setShellStyle(int shellStyle) {
		this.shellStyle = shellStyle;
	}
	
	public String getTitre() {
		return titre;
	}
	
	public void setTitre(String titre) {
		this.titre = titre;
	}
	
}

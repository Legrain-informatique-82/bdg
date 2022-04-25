package fr.legrain.lib.gui.aide;

import javax.swing.JComponent;

import org.eclipse.swt.widgets.Composite;

import fr.legrain.lib.gui.ParamAffiche;

public class ParamAfficheAide extends ParamAffiche {
	
	private JComponent appelant;
	private Composite appelantSWT;
	
	//TODO ajouter info sur l'ecran appelant

	public JComponent getAppelant() {
		return appelant;
	}

	public void setAppelant(JComponent appelant) {
		this.appelant = appelant;
	}

	public Composite getAppelantSWT() {
		return appelantSWT;
	}

	public void setAppelantSWT(Composite appelantSWT) {
		this.appelantSWT = appelantSWT;
	}

}

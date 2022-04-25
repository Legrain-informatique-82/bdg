package fr.legrain.document.divers;

import java.util.EventObject;

import fr.legrain.lib.data.ModeObjet.EnumModeObjet;


public class DeclencheChangementEtatEvent extends EventObject {
	
	private EnumModeObjet etat = null;

	public DeclencheChangementEtatEvent(Object source) {
		super(source);
	}
	
	public DeclencheChangementEtatEvent(Object source, EnumModeObjet etat) {
		super(source);
		this.etat = etat;
	}

	public EnumModeObjet getEtat() {
		return etat;
	}

	public void setEtat(EnumModeObjet etat) {
		this.etat = etat;
	}

}

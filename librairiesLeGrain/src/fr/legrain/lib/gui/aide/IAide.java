package fr.legrain.lib.gui.aide;

import java.beans.PropertyChangeListener;

//TODO a supprimer quand le retour de l'ecran d'aide se fera avec systeme general de retour d'ecran
public interface IAide extends PropertyChangeListener {
	
	public Aide getAide();

}

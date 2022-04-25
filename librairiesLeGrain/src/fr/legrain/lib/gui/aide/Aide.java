package fr.legrain.lib.gui.aide;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.event.EventListenerList;
//TODO a supprimer quand le retour de l'ecran d'aide se fera avec systeme general de retour d'ecran
public class Aide {
	
	EventListenerList listenerList = new EventListenerList();

	private Object source;//objet source //composant "à remplir" ou qui avait le focus
	//private Object destination;//objet destination
	private Object choix;//valeur de retour
	private String nomChamps;
	//etat : edition, insertion,..
	//action retour : fermeture, annulation, validation
	
	static private Aide instance = null;
	
	private Aide(){}
	
	public static Aide createAide() {
		if(instance==null)
			instance = new Aide();
		return instance;
	}
	
	public void setChoix(Object choix) {
		this.choix = choix;
		firePropertyChange(new PropertyChangeEvent(this,"choix",null,choix));
	}
	
	public void addPropertyChangeListener(PropertyChangeListener l) {
		listenerList.add(PropertyChangeListener.class, l);
	}
	
	protected void firePropertyChange(PropertyChangeEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == PropertyChangeListener.class) {
				if (e == null)
					e = new PropertyChangeEvent(this,null,null,null);
				( (PropertyChangeListener) listeners[i + 1]).propertyChange(e);
			}
		}
	}

	public Object getSource() {
		return source;
	}

	public void setSource(Object source) {
		this.source = source;
	}
	
	//gestion d'une liste LIFO d'instance d'Aide pour les appels consécutifs
	// OU peut être evenement + listener
	
}

package fr.legrain.lib.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JSpinner;

import org.apache.log4j.Logger;

/**
 * Initialisaton de l'ordre dans lequel les composants de l'ecran doivent recevoir le focus
 */
public class OrdreFocus extends FocusTraversalPolicy {
    
	static Logger logger = Logger.getLogger(OrdreFocus.class.getName());
	
	/** Liste des composant de l'écran dans l'ordre dans lequel les composants de l'ecran doivent recevoir le focus */
	private List listeComposant = null;
	private Component repriseBefore = null;
	private Component repriseAfter = null;
    
	/**
	 * @param listeComposant - Liste des composant de l'écran dans l'ordre dans lequel les composants de l'ecran doivent recevoir le focus
	 */
	public OrdreFocus(List listeComposant) {
		this.listeComposant = listeComposant;
	}
	
	public OrdreFocus(List listeComposant,Component repriseBefore, Component repriseAfter) {
		this.listeComposant = listeComposant;
		this.repriseBefore = repriseBefore;
		this.repriseAfter = repriseAfter;
	}
	
	@Override
	public Component getComponentAfter(Container aContainer, Component aComponent) {
//		if(listeComposant.indexOf(aComponent)<listeComposant.size())
//			return (Component)listeComposant.get(listeComposant.indexOf(aComponent)+1);
//		else
//			return getFirstComponent(aContainer);
		return composantFocusableAfter(aContainer, aComponent);
	}
	
	private boolean isFocusable(Component aComponent) {
		if(!aComponent.isEnabled() 
				|| !aComponent.isFocusable())
			return false;
		else
			return true;
	}
	
	private Component composantFocusableAfter(Container aContainer, Component aComponent) {	  
		boolean trouve = false;
		//position actuelle
		int positionComposant = listeComposant.indexOf(aComponent);
		int i = 0;
		//chercher si composant avant la fin de la liste
		while((positionComposant+i)<listeComposant.size()-1 && !trouve) {
			i++;
			if(isFocusable((Component)listeComposant.get(positionComposant+i))) {
				trouve = true;
			}
		}
		if(trouve) {
			if(((Component)listeComposant.get(positionComposant+i) instanceof JPanel)&&(! (((Component)listeComposant.get(positionComposant+i) instanceof JSpinner.DateEditor))))
				return ((OrdreFocus)((JPanel)listeComposant.get(positionComposant+i)).getFocusTraversalPolicy()).getRepriseAfter((JPanel)(Component)listeComposant.get(positionComposant+i));
			else
				return (Component)listeComposant.get(positionComposant+i);
		}
		else { //reprise au début de la liste
			i=0;
			while(i<positionComposant && !trouve) {
				if(isFocusable((Component)listeComposant.get(i))) {
					trouve = true;
				}
				if(!trouve) i++;
			}
			if(trouve)
				return (Component)listeComposant.get(i);
			else
				return getFirstComponent(aContainer);
		}		
	}
	
	private Component composantFocusableBefore(Container aContainer, Component aComponent) {	  
		boolean trouve = false;
		//position actuelle
		int positionComposant = listeComposant.indexOf(aComponent);
		int i = 0;
		//chercher si composant avant la fin de la liste
		while((positionComposant-i)>0 && !trouve) {
			i++;
			if(isFocusable((Component)listeComposant.get(positionComposant-i))) {
				trouve = true;
			}
		}
		if(trouve)
			if((Component)listeComposant.get(positionComposant-i) instanceof JPanel)
				return ((OrdreFocus)((JPanel)listeComposant.get(positionComposant-i)).getFocusTraversalPolicy()).getRepriseBefore((JPanel)(Component)listeComposant.get(positionComposant-i));
			else
				return (Component)listeComposant.get(positionComposant-i);
		else { //reprise au début de la liste
			i=listeComposant.size()-1;
			while(positionComposant<i && !trouve) {
				if(isFocusable((Component)listeComposant.get(i))) {
					trouve = true;
				}
				if(!trouve) i--;
			}
			if(trouve)
				return (Component)listeComposant.get(i);
			else
				return getFirstComponent(aContainer);
		}		
	}

	@Override
	public Component getComponentBefore(Container aContainer, Component aComponent) {
//		if(listeComposant.indexOf(aComponent)>1)
//			return (Component)listeComposant.get(listeComposant.indexOf(aComponent)-1);
//		else
//			return getLastComponent(aContainer);
		return composantFocusableBefore(aContainer, aComponent);
	}

	@Override
	public Component getFirstComponent(Container aContainer) {
		return (Component)listeComposant.get(0);
	}

	@Override
	public Component getLastComponent(Container aContainer) {
		return (Component)listeComposant.get(listeComposant.size());
	}

	@Override
	public Component getDefaultComponent(Container aContainer) {
		return getFirstComponent(aContainer);
	}

	public Component getRepriseAfter(Container aContainer) {
		if(repriseAfter!=null)
			return repriseAfter;
		else 
			return getFirstComponent(aContainer);
	}

	public Component getRepriseBefore(Container aContainer) {
		if(repriseBefore!=null)
			return repriseBefore;
		else 
			return getFirstComponent(aContainer);
	}

}

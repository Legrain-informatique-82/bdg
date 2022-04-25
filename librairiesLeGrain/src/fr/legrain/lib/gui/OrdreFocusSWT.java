package fr.legrain.lib.gui;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

/**
 * Initialisaton de l'ordre dans lequel les composants de l'ecran doivent recevoir le focus
 */
public class OrdreFocusSWT implements TraverseListener {
    
	static Logger logger = Logger.getLogger(OrdreFocusSWT.class.getName());
	private List listeComposantFocusable = null;
	
	public OrdreFocusSWT(List<Control> listeComposantFocusable) {
		this.listeComposantFocusable = listeComposantFocusable;
	}

	public void keyTraversed(TraverseEvent e) {
		if(e.detail == SWT.TRAVERSE_TAB_NEXT) {
			if(listeComposantFocusable.contains(e.getSource())) {
				suivant((Control)e.getSource()).setFocus();
			} else if((e.getSource() instanceof Text) && listeComposantFocusable.contains(((Text)e.getSource()).getParent())) {
				//Pour CDateTime car c'est le composant Text qui declenche l'evenement
				suivant(((Text)e.getSource()).getParent()).setFocus();
			}
			e.doit = false;
		} else if(e.detail == SWT.TRAVERSE_TAB_PREVIOUS) {
			if(listeComposantFocusable.contains(e.getSource())) {
				precedent((Control)e.getSource()).setFocus();
			} else if((e.getSource() instanceof Text) && listeComposantFocusable.contains(((Text)e.getSource()).getParent())) {
				//Pour CDateTime car c'est le composant Text qui declenche l'evenement
				precedent(((Text)e.getSource()).getParent()).setFocus();
			}
			e.doit = false;
		} else if(e.detail == SWT.TRAVERSE_RETURN && (e.getSource() instanceof Text &&
				 (((Text)e.getSource()).getStyle() & SWT.MULTI)== 0)){
			//empeche le declenchement automatique du bouton par defaut
			e.doit = false;
			e.detail = SWT.TRAVERSE_TAB_NEXT;
		} 
			
	}
	
	public Control suivant(Control c) {
		boolean trouve = false;
		//position actuelle
		int positionControl = listeComposantFocusable.indexOf(c);
		int i = 0;
		//chercher si composant avant la fin de la liste
		while((positionControl+i)<listeComposantFocusable.size()-1 && !trouve) {
			i++;
			if(isFocusable((Control)listeComposantFocusable.get(positionControl+i))) {
				trouve = true;
			}
		}
		if(trouve) {
//			if(((Control)listeComposantFocusable.get(positionControl+i) instanceof JPanel)&&(! (((Control)listeComposant.get(positionControl+i) instanceof JSpinner.DateEditor))))
//				return ((OrdreFocus)((JPanel)listeComposantFocusable.get(positionControl+i)).getFocusTraversalPolicy()).getRepriseAfter((JPanel)(Component)listeComposant.get(positionControl+i));
//			else
				return (Control)listeComposantFocusable.get(positionControl+i);
		}
		else { //reprise au début de la liste
			i=0;
			while(i<positionControl && !trouve) {
				if(isFocusable((Control)listeComposantFocusable.get(i))) {
					trouve = true;
				}
				if(!trouve) i++;
			}
			if(trouve)
				return (Control)listeComposantFocusable.get(i);
			else
				return (Control)listeComposantFocusable.get(0);
		}		
	}
	
	private Control precedent(Control c) {	  
		boolean trouve = false;
		//position actuelle
		int positionComposant = listeComposantFocusable.indexOf(c);
		int i = 0;
		//chercher si composant avant la fin de la liste
		while((positionComposant-i)>0 && !trouve) {
			i++;
			if(isFocusable((Control)listeComposantFocusable.get(positionComposant-i))) {
				trouve = true;
			}
		}
		if(trouve)
//			if((Control)listeComposant.get(positionComposant-i) instanceof JPanel)
//				return ((OrdreFocus)((JPanel)listeComposant.get(positionComposant-i)).getFocusTraversalPolicy()).getRepriseBefore((JPanel)(Component)listeComposant.get(positionComposant-i));
//			else
				return (Control)listeComposantFocusable.get(positionComposant-i);
		else { //reprise au début de la liste
			i=listeComposantFocusable.size()-1;
			while(positionComposant<i && !trouve) {
				if(isFocusable((Control)listeComposantFocusable.get(i))) {
					trouve = true;
				}
				if(!trouve) i--;
			}
			if(trouve)
				return (Control)listeComposantFocusable.get(i);
			else
				return (Control)listeComposantFocusable.get(0);
		}		
	}
	
	private boolean isFocusable(Control aComponent) {
		if(!aComponent.isEnabled() || !aComponent.isVisible())
			return false;
		else
			return true;
	}

	
////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////======== SWING ========//////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////
//	/** Liste des composant de l'écran dans l'ordre dans lequel les composants de l'ecran doivent recevoir le focus */
//	private List listeComposant = null;
//	private Component repriseBefore = null;
//	private Component repriseAfter = null;
//    
//	/**
//	 * @param listeComposant - Liste des composant de l'écran dans l'ordre dans lequel les composants de l'ecran doivent recevoir le focus
//	 */
//	public OrdreFocusSWT(List listeComposant) {
//		this.listeComposant = listeComposant;
//	}
//	
//	public OrdreFocusSWT(List listeComposant,Component repriseBefore, Component repriseAfter) {
//		this.listeComposant = listeComposant;
//		this.repriseBefore = repriseBefore;
//		this.repriseAfter = repriseAfter;
//	}
//	
//	@Override
//	public Component getComponentAfter(Container aContainer, Component aComponent) {
////		if(listeComposant.indexOf(aComponent)<listeComposant.size())
////			return (Component)listeComposant.get(listeComposant.indexOf(aComponent)+1);
////		else
////			return getFirstComponent(aContainer);
//		return composantFocusableAfter(aContainer, aComponent);
//	}
//	
//	private boolean isFocusable(Component aComponent) {
//		if(!aComponent.isEnabled() 
//				|| !aComponent.isFocusable())
//			return false;
//		else
//			return true;
//	}
//	
//	private Component composantFocusableAfter(Container aContainer, Component aComponent) {	  
//		boolean trouve = false;
//		//position actuelle
//		int positionComposant = listeComposant.indexOf(aComponent);
//		int i = 0;
//		//chercher si composant avant la fin de la liste
//		while((positionComposant+i)<listeComposant.size()-1 && !trouve) {
//			i++;
//			if(isFocusable((Component)listeComposant.get(positionComposant+i))) {
//				trouve = true;
//			}
//		}
//		if(trouve) {
//			if(((Component)listeComposant.get(positionComposant+i) instanceof JPanel)&&(! (((Component)listeComposant.get(positionComposant+i) instanceof JSpinner.DateEditor))))
//				return ((OrdreFocusSWT)((JPanel)listeComposant.get(positionComposant+i)).getFocusTraversalPolicy()).getRepriseAfter((JPanel)(Component)listeComposant.get(positionComposant+i));
//			else
//				return (Component)listeComposant.get(positionComposant+i);
//		}
//		else { //reprise au début de la liste
//			i=0;
//			while(i<positionComposant && !trouve) {
//				if(isFocusable((Component)listeComposant.get(i))) {
//					trouve = true;
//				}
//				if(!trouve) i++;
//			}
//			if(trouve)
//				return (Component)listeComposant.get(i);
//			else
//				return getFirstComponent(aContainer);
//		}		
//	}
//	
//	private Component composantFocusableBefore(Container aContainer, Component aComponent) {	  
//		boolean trouve = false;
//		//position actuelle
//		int positionComposant = listeComposant.indexOf(aComponent);
//		int i = 0;
//		//chercher si composant avant la fin de la liste
//		while((positionComposant-i)>0 && !trouve) {
//			i++;
//			if(isFocusable((Component)listeComposant.get(positionComposant-i))) {
//				trouve = true;
//			}
//		}
//		if(trouve)
//			if((Component)listeComposant.get(positionComposant-i) instanceof JPanel)
//				return ((OrdreFocusSWT)((JPanel)listeComposant.get(positionComposant-i)).getFocusTraversalPolicy()).getRepriseBefore((JPanel)(Component)listeComposant.get(positionComposant-i));
//			else
//				return (Component)listeComposant.get(positionComposant-i);
//		else { //reprise au début de la liste
//			i=listeComposant.size()-1;
//			while(positionComposant<i && !trouve) {
//				if(isFocusable((Component)listeComposant.get(i))) {
//					trouve = true;
//				}
//				if(!trouve) i--;
//			}
//			if(trouve)
//				return (Component)listeComposant.get(i);
//			else
//				return getFirstComponent(aContainer);
//		}		
//	}
//
//	@Override
//	public Component getComponentBefore(Container aContainer, Component aComponent) {
////		if(listeComposant.indexOf(aComponent)>1)
////			return (Component)listeComposant.get(listeComposant.indexOf(aComponent)-1);
////		else
////			return getLastComponent(aContainer);
//		return composantFocusableBefore(aContainer, aComponent);
//	}
//
//	@Override
//	public Component getFirstComponent(Container aContainer) {
//		return (Component)listeComposant.get(0);
//	}
//
//	@Override
//	public Component getLastComponent(Container aContainer) {
//		return (Component)listeComposant.get(listeComposant.size());
//	}
//
//	@Override
//	public Component getDefaultComponent(Container aContainer) {
//		return getFirstComponent(aContainer);
//	}
//
//	public Component getRepriseAfter(Container aContainer) {
//		if(repriseAfter!=null)
//			return repriseAfter;
//		else 
//			return getFirstComponent(aContainer);
//	}
//
//	public Component getRepriseBefore(Container aContainer) {
//		if(repriseBefore!=null)
//			return repriseBefore;
//		else 
//			return getFirstComponent(aContainer);
//	}

}

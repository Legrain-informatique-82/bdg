package fr.legrain.lib.data;

import javax.swing.JComponent;
import javax.swing.event.EventListenerList;

import org.eclipse.swt.widgets.Control;

/**
 * <p>Title: </p>
 * <p>Description: Etat de l'objet gérant la base de données.</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Le Grain SA</p>
 * @author Le Grain SA
 * @version 1.0
 */

public class ModeObjet {
	
	static public enum EnumModeObjet {C_MO_CONSULTATION,C_MO_EDITION,C_MO_INSERTION,C_MO_IMPORTATION,C_MO_ERREUR,C_MO_SUPPRESSION};
	protected EventListenerList listenerList = new EventListenerList();
	
//	public static final int C_MO_CONSULTATION = 0; //MoConsultation
//	public static final int C_MO_EDITION      = 1; //MoEdition
//	public static final int C_MO_INSERTION    = 2; //MoInsertion
//	public static final int C_MO_IMPORTATION  = 3; //MoImportation
//	public static final int C_MO_ERREUR       = 4; //MoErreur
//	public static final int C_MO_SUPPRESSION  = 5; //MoSuppression
	
	private EnumModeObjet mode = EnumModeObjet.C_MO_CONSULTATION;
	private JComponent focusCourant = null;
	private Control focusCourantSWT = null;
	
	public ModeObjet() {}
	
	public ModeObjet(EnumModeObjet mode) {
		this.mode = mode;
	}
	
	public void setMode(EnumModeObjet mode) {
		if (this.mode!=mode){
			this.mode = mode;
			switch (mode) {
			case C_MO_CONSULTATION :
			case C_MO_EDITION :  
			case C_MO_INSERTION :
				setFocusCourant(null);
				setFocusCourantSWT(null);
				break;
			default:
				break;
			}
		}	  
		fireChangementMode(new ChangeModeEvent(this,getMode(),mode));
	}
	
	protected void fireChangementMode(ChangeModeEvent evt) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ChangeModeListener.class) {
				if (evt == null)
					evt = new ChangeModeEvent(this);
				( (ChangeModeListener) listeners[i + 1]).changementMode(evt);
			}
		}
	}
	
	public void addChangeModeListener(ChangeModeListener l) {
		listenerList.add(ChangeModeListener.class, l);
	}
	
	public void removeChangeModeListener(ChangeModeListener l) {
		listenerList.remove(ChangeModeListener.class, l);
	}
	
	public EnumModeObjet getMode() {
		return mode;
	}
	
	public JComponent getFocusCourant() {
		return this.focusCourant;
	}
	
	public void setFocusCourant(JComponent focusCourant) {
		this.focusCourant = focusCourant;
	}
	
	public void setFocusCourantHorsApplication(JComponent focusCourant) {
		this.focusCourant = focusCourant;		
	}

	
	public void setFocusCourantSWTHorsApplication(Control focusCourantSWT) {
		this.focusCourantSWT = focusCourantSWT;		
	}
	
	
	public Control getFocusCourantSWT() {
		return focusCourantSWT;
	}

	public void setFocusCourantSWT(Control focusCourantSWT) {
		this.focusCourantSWT = focusCourantSWT;
	}
	
	public void destroy(){		
		mode = null;
		focusCourant = null;
		focusCourantSWT = null;		
	}
}

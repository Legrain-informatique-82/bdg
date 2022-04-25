package fr.legrain.gestCom.librairiesEcran.swt;

import javax.swing.event.EventListenerList;

import org.eclipse.jface.preference.StringButtonFieldEditor;
import org.eclipse.swt.widgets.Composite;

import fr.legrain.lib.gui.RetourEcranListener;

public class StringButtonFieldEditorLgr extends StringButtonFieldEditor {
	
	protected String valeurParDefaut = null; 
	protected EventListenerList listenerList = new EventListenerList();
	
	public StringButtonFieldEditorLgr(String pathEditionDefaut, String string,
			Composite fieldEditorParent) {
		super( pathEditionDefaut,  string,
			 fieldEditorParent);
	}

	@Override
	protected String changePressed() {
		
		/** 05/01/2010 **/
		firePagePreferencePage(new StringButtonFieldEditorLgrEvent(this));
		
		setStringValue(this.valeurParDefaut);
		return this.valeurParDefaut;
		
	}

	public String getValeurParDefaut() {
		return valeurParDefaut;
	}

	public void setValeurParDefaut(String valeurParDefaut) {
		this.valeurParDefaut = valeurParDefaut;
	}
	
	/** 05/01/2010 add **/
	public void addPagePreferencePageListener(IStringButtonFieldEditorLgrListener l) {
		listenerList.add(IStringButtonFieldEditorLgrListener.class, l);
	}
	
	public void removePagePreferencePageListener(IStringButtonFieldEditorLgrListener l) {
		listenerList.remove(IStringButtonFieldEditorLgrListener.class, l);
	}
	
	protected void firePagePreferencePage(StringButtonFieldEditorLgrEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == IStringButtonFieldEditorLgrListener.class) {
				if (e == null)
					e = new StringButtonFieldEditorLgrEvent(this);
				( (IStringButtonFieldEditorLgrListener) listeners[i + 1]).declencheBtReinitiale(e);
			}
		}
	}
	

}

package fr.legrain.gestCom.Module_Document;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;

import javax.swing.event.EventListenerList;

import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.Update;
import fr.legrain.lib.data.ModeObjet.EnumModeObjet;

public abstract class EnteteDocument {
	
	protected EventListenerList listenerList = new EventListenerList();
	
	protected String CODE = "";
	protected Date DATE = new Date();
	protected String LIBELLE = "";
	protected Integer ID_TIERS = 0;
	protected String CODE_TIERS = "";
	protected String OLD_CODE_TIERS = "";
	protected EnumModeObjet mode;
	protected Update updateEntete = new Update();
	protected String tableEntete = null;
	
	protected abstract boolean beforeEnregistrerEntete();
	
	protected abstract void afterEnregistrerEntete() throws ExceptLgr;
	
	protected abstract boolean beforeModifierEntete();
	
	protected abstract void afterModifierEntete() throws ExceptLgr;
	
	protected abstract boolean beforeSupprimerEntete();
	
	protected abstract void afterSupprimerEntete() throws ExceptLgr;
	
	public abstract void enregistrer() throws ExceptLgr;
	
	public abstract void modifier() throws ExceptLgr;
	
	public abstract void supprimer() throws ExceptLgr;
	
	protected void firePropertyChange(PropertyChangeEvent evt) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == PropertyChangeListener.class) {
				if (evt == null)
					evt = new PropertyChangeEvent(this,"CODE",null,null);
				( (PropertyChangeListener) listeners[i + 1]).propertyChange(evt);
			}
		}
	}
	
	public void addPropertyChangeListener(PropertyChangeListener l) {
		listenerList.add(PropertyChangeListener.class, l);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener l) {
		listenerList.remove(PropertyChangeListener.class, l);
	}	
	
	abstract public void videEntete();
	
	public String getCODE() {
		return CODE;
	}
	public void setCODE(String code) {
		firePropertyChange(new PropertyChangeEvent(this,"CODE",this.CODE,code));
		CODE = code;
	}
	public Date getDATE() {
		return DATE;
	}
	public void setDATE(Date date) {
		firePropertyChange(new PropertyChangeEvent(this,"DATE",this.DATE,date));
		DATE = date;
	}
	public Integer getID_TIERS() {
		return ID_TIERS;
	}
	public void setID_TIERS(Integer id_tiers) {
		ID_TIERS = id_tiers;
	}
	public String getLIBELLE() {
		return LIBELLE;
	}
	public void setLIBELLE(String libelle) {
		LIBELLE = libelle;
	}

	public EnumModeObjet getMode() {
		return mode;
	}

	public void setMode(EnumModeObjet mode) {
		this.mode = mode;
	}

	public String getTableEntete() {
		return tableEntete;
	}

	public void setTableEntete(String tableEntete) {
		this.tableEntete = tableEntete;
	}

	public String getCODE_TIERS() {
		return CODE_TIERS;
	}

	public void setCODE_TIERS(String code_tiers) {
		CODE_TIERS = code_tiers;
	}

	public String getOLD_CODE_TIERS() {
		return OLD_CODE_TIERS;
	}

	public void setOLD_CODE_TIERS(String old_code_tiers) {
		OLD_CODE_TIERS = old_code_tiers;
	}


}

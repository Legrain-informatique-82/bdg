package fr.legrain.lib.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.event.EventListenerList;

import org.apache.log4j.Logger;

import fr.legrain.lib.data.ModeObjet.EnumModeObjet;
import fr.legrain.lib.gui.BaseController;
import fr.legrain.lib.gui.DeclencheAideEvent;
import fr.legrain.lib.gui.DeclencheAideListener;
import fr.legrain.lib.gui.SortieChampsEvent;
import fr.legrain.lib.gui.SortieChampsListener;

public class CtrlSortieChamps extends InputVerifier {
	static Logger logger = Logger.getLogger(CtrlSortieChamps.class.getName());
	protected EventListenerList listenerList = new EventListenerList();
	private Map mapComposantChamps = null;
	private IBQuLgr ibTaTable = null;
	 
	
	public CtrlSortieChamps(Map mapComposantChamps, IBQuLgr ibTaTable) {
		this.mapComposantChamps = mapComposantChamps;
		this.ibTaTable = ibTaTable;
	}
	
	public CtrlSortieChamps(Map mapComposantChamps, IBQuLgr ibTaTable, BaseController controller) {
		this.mapComposantChamps = mapComposantChamps;
		this.ibTaTable = ibTaTable;
		this.addSortieChampsListener((SortieChampsListener)controller);
		this.addDeclencheAideListener((DeclencheAideListener)controller);
	}
	
	public boolean verify(JComponent input) {
		boolean res = true;
		try {
			logger.debug(input.getName()); 
			// ///////////////////////////////***********************/////////////////		
			
			String nomChamp = null;
			String nouvelleValeur = null;
			if (mapComposantChamps.get(input) != null) { // le champs est
				// relié à la bdd, il faut faire un controle
				nomChamp = mapComposantChamps.get(input).toString();				
				nouvelleValeur = BaseController.stringValue(input);
				input.setInputVerifier(null); //Work Around pour le bug
				// #4532517 JDK 1.4 pour la perte de focus à l'intérieur des

		// méthodes d'un InputVerifier (Résolu avec le JDK 1.5)
				if (!VerrouInterface.getVerrouInterface().isVerrouille()){
					if (this.ibTaTable.getFModeObjet().getMode()!=EnumModeObjet.C_MO_CONSULTATION){
						res = ibTaTable.verifChamp(nomChamp, nouvelleValeur,null,null);
					}
					if (!res){ 
						ibTaTable.getFModeObjet().setFocusCourant(input);
						input.requestFocus();
						fireDeclencheAide(new DeclencheAideEvent(this,input));
					} else
						try{
							fireSortieChamps(new SortieChampsEvent(this,input));
						}catch(Exception e1){
							res=false;
							ibTaTable.getFModeObjet().setFocusCourant(input);
							input.requestFocus();
						}
				}
//				 #4532517 JDK 1.4
				input.setInputVerifier(this); //Work Around pour le bug
			}
			//
			return res;
//			// /////////////////////////////////*******************///////////////////
		} catch (Exception e) {
			logger.error("Erreur : verify", e);
			input.setInputVerifier(this); 
			return false;
		}
	}

	

	protected void fireSortieChamps(SortieChampsEvent evt) throws Exception {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == SortieChampsListener.class) {
				if (evt == null)
					evt = new SortieChampsEvent(this,evt.getInput());
				( (SortieChampsListener) listeners[i + 1]).sortieChamps(evt);
			}
		}		
	}
	
	public void addSortieChampsListener(SortieChampsListener l) {
		listenerList.add(SortieChampsListener.class, l);
	}
	
	public void removeSortieChampsListener(SortieChampsListener l) {
		listenerList.remove(SortieChampsListener.class, l);
	}	
	

	
	protected void fireDeclencheAide(DeclencheAideEvent evt) throws Exception {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == DeclencheAideListener.class) {
				if (evt == null)
					evt = new DeclencheAideEvent(this,evt.getInput());
				( (DeclencheAideListener) listeners[i + 1]).declencheAide(evt);
			}
		}		
	}
	
	public void addDeclencheAideListener(DeclencheAideListener l) {
		listenerList.add(DeclencheAideListener.class, l);
	}
	
	public void removeDeclencheAideListener(DeclencheAideListener l) {
		listenerList.remove(DeclencheAideListener.class, l);
	}
}

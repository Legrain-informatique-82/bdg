package fr.legrain.lib.gui.aide;

import fr.legrain.lib.gui.ResultAffiche;

public class ResultAide extends ResultAffiche {
	
	private String idChamps = null;
	private Integer idValeur = null;
	private Object objet = null;
	//TODO info pour savoir si une valeur a été créer + son type
	
	public ResultAide(String idChamps, Integer idValeur) {
		this.idChamps = idChamps;
		this.idValeur = idValeur;
	}
	
	public ResultAide(String idChamps, Integer idValeur, Object o) {
		this.idChamps = idChamps;
		this.idValeur = idValeur;
		this.objet = o;
	}
	
	public String getIdChamps() {
		return idChamps;
	}
	
	public void setIdChamps(String idChamps) {
		this.idChamps = idChamps;
	}
	
	public Integer getIdValeur() {
		return idValeur;
	}
	
	public void setIdValeur(Integer idValeur) {
		this.idValeur = idValeur;
	}

	public Object getObjet() {
		return objet;
	}

	public void setObjet(Object objet) {
		this.objet = objet;
	}
	
}

package fr.legrain.liasseFiscale.ecran;

import java.util.ArrayList;

public class Page {
	private Integer numero;
	private ArrayList<Champ> champs = new ArrayList<Champ>(); //liste des champs

	public Page() {}

	public ArrayList<Champ> getChamps() {
		return champs;
	}

	public void setChamps(ArrayList<Champ> champs) {
		this.champs = champs;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	
}

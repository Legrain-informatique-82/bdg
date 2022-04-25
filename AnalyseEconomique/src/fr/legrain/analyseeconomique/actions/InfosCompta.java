package fr.legrain.analyseeconomique.actions;

import fr.legrain.liasseFiscale.actions.Compte;

public class InfosCompta {
	private Compte c;

	public InfosCompta() {
		super();
	}

	public InfosCompta(Compte c) {
		super();
		this.c = c;
	}

	public Compte getC() {
		return c;
	}

	public void setC(Compte c) {
		this.c = c;
	}
}

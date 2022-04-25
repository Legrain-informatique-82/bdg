package fr.legrain.analyseeconomique.actions;

import fr.legrain.liasseFiscale.actions.CompteSimple;

public class Divers {
	private CompteSimple c;

	public Divers() {
		super();
	}

	public Divers(CompteSimple c) {
		super();
		this.c = c;
	}

	public CompteSimple getC() {
		return c;
	}

	public void setC(CompteSimple c) {
		this.c = c;
	}
}

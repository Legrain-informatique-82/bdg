package fr.legrain.analyseeconomique.actions;

import fr.legrain.liasseFiscale.actions.CompteSimple;

public class Acquisition {
	private CompteSimple c;

	public Acquisition() {
		super();
	}

	public Acquisition(CompteSimple c) {
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

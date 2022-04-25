package fr.legrain.analyseeconomique.actions;

import fr.legrain.liasseFiscale.actions.Cle;
import fr.legrain.liasseFiscale.actions.Repartition;

public class InfosLiasse {
	private Cle c;
	private Repartition r;
	
	public InfosLiasse() {
		super();
	}
	
	public InfosLiasse(Cle c, Repartition r) {
		super();
		this.c = c;
		this.r = r;
	}
	
	public Cle getC() {
		return c;
	}
	
	public void setC(Cle c) {
		this.c = c;
	}
	
	public Repartition getR() {
		return r;
	}
	
	public void setR(Repartition r) {
		this.r = r;
	}
}

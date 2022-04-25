package fr.legrain.gestCom.librairiesEcran.workbench;

import java.util.EventObject;


public class ChangementDePageEvent extends EventObject {
	
	public static final int PRECEDENT = 1;
	public static final int SUIVANT = 2;
	public static final int DEBUT = 3;
	public static final int FIN = 4;
	public static final int AUTRE = 5;
	
	private int sens = 0;
	private int position = 0;
	private int pageAppelante=0;
	
	private Object paramPage = null;

	public ChangementDePageEvent(Object source) {
		super(source);
	}
	
	public ChangementDePageEvent(Object source, int sens) {
		super(source);
		this.sens = sens;
	}
	
	public ChangementDePageEvent(Object source, int sens, int position) {
		super(source);
		this.sens = sens;
		this.position = position;
	}

	public ChangementDePageEvent(Object source, int sens, int position, int pageAppelante) {
		super(source);
		this.sens = sens;
		this.position = position;
		this.pageAppelante = pageAppelante;
	}
	
	public int getSens() {
		return sens;
	}

	public void setSens(int sens) {
		this.sens = sens;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Object getParamPage() {
		return paramPage;
	}

	public void setParamPage(Object paramPage) {
		this.paramPage = paramPage;
	}

	public int getPageAppelante() {
		return pageAppelante;
	}

	public void setPageAppelante(int pageAppelante) {
		this.pageAppelante = pageAppelante;
	}

}

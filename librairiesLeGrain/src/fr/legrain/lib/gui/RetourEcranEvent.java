package fr.legrain.lib.gui;

import java.util.EventObject;

public class RetourEcranEvent extends EventObject {
	
	//TODO variable permettant de connaitre l'action qui a déclenché le retour
	
	private ResultAffiche retour;
	
	public RetourEcranEvent(Object source, ResultAffiche retour) {
		super(source);
		this.retour = retour;
	}

	public ResultAffiche getRetour() {
		return retour;
	}

	public void setRetour(ResultAffiche retour) {
		this.retour = retour;
	}
}

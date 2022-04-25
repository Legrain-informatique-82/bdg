package fr.legrain.gestCom.librairiesEcran.workbench;

import java.util.EventObject;


public class ChangementDeSelectionEvent extends EventObject {
		
	private Object selection = 0;

	public ChangementDeSelectionEvent(Object source) {
		super(source);
	}
	
	public ChangementDeSelectionEvent(Object source, Object selection) {
		super(source);
		this.selection = selection;
	}

}

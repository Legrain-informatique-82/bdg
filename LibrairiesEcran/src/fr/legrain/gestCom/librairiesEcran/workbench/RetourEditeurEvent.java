package fr.legrain.gestCom.librairiesEcran.workbench;

import java.util.EventObject;

public class RetourEditeurEvent extends EventObject {
	
	private Object value = null;
	
	public RetourEditeurEvent(Object source, Object value) {
		super(source);
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	
}
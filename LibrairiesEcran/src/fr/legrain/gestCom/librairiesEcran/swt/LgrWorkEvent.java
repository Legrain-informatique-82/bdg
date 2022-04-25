package fr.legrain.gestCom.librairiesEcran.swt;

import java.util.EventObject;

public class LgrWorkEvent extends EventObject {
	
	private int totalAmount;
	private String subTaskName = null;
	
	public LgrWorkEvent(Object source) {
		super(source);
	}

	public LgrWorkEvent(Object source, int totalAmount) {
		super(source);
		this.totalAmount = totalAmount;
	}
	
	public LgrWorkEvent(Object source, int totalAmount,String subTaskName) {
		super(source);
		this.totalAmount = totalAmount;
		this.subTaskName = subTaskName;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getSubTaskName() {
		return subTaskName;
	}

	public void setSubTaskName(String subTaskName) {
		this.subTaskName = subTaskName;
	}
	
}

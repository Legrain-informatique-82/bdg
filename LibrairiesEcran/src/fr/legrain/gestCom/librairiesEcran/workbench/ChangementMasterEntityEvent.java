package fr.legrain.gestCom.librairiesEcran.workbench;

import java.util.EventObject;


public class ChangementMasterEntityEvent extends EventObject {
		
	private Object newMasterEntity = null;
	private boolean simple = false;

	public ChangementMasterEntityEvent(Object source) {
		super(source);
	}
	
	public ChangementMasterEntityEvent(Object source, Object newMasterEntity, boolean simple) {
		super(source);
		this.newMasterEntity = newMasterEntity;
		this.simple = simple;
	}
	
	public ChangementMasterEntityEvent(Object source, Object newMasterEntity) {
		super(source);
		this.newMasterEntity = newMasterEntity;
	}

	public Object getNewMasterEntity() {
		return newMasterEntity;
	}

	public void setNewMasterEntity(Object newMasterEntity) {
		this.newMasterEntity = newMasterEntity;
	}

	public boolean isSimple() {
		return simple;
	}

	public void setSimple(boolean simple) {
		this.simple = simple;
	}

}

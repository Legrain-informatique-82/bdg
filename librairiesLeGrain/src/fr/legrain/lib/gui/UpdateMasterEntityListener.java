package fr.legrain.lib.gui;


import java.util.EventListener;


public interface UpdateMasterEntityListener extends EventListener {
	
	public void updateMasterEntity(UpdateMasterEntityEvent evt);
	
}
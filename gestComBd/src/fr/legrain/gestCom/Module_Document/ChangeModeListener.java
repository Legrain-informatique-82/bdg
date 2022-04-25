package fr.legrain.gestCom.Module_Document;


import java.util.EventListener;


public interface ChangeModeListener extends EventListener {
	
	public void changementMode(ChangeModeEvent evt);
	
}
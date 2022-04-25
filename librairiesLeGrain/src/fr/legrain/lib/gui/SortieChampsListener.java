package fr.legrain.lib.gui;


import java.util.EventListener;


public interface SortieChampsListener extends EventListener {
	
	public void sortieChamps(SortieChampsEvent evt)throws Exception ;
	
}
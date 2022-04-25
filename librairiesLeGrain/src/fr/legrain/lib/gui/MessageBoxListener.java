package fr.legrain.lib.gui;

import java.util.EventListener;

public interface MessageBoxListener extends EventListener {
	
	public void reponseMessageBox(MessageBoxEvent evt);
	
	public void messageBoxYes(MessageBoxEvent evt);
	public void messageBoxNo(MessageBoxEvent evt);
	public void messageBoxCancel(MessageBoxEvent evt);

}

package fr.legrain.lib.gui;

import java.awt.AWTEvent;
import java.awt.Event;
import java.util.EventObject;

public class MessageBoxEvent extends EventObject {
	
	//TODO variable permettant de connaitre l'action qui a déclenché le retour
	
	private int reponse;
	private String command;
	
	public MessageBoxEvent(Object source) {
		super(source);
	}
	
	public MessageBoxEvent(Object source, int reponse, String command) {
		super(source);
		this.reponse = reponse;
		this.command = command;
	}

	public int getReponse() {
		return reponse;
	}

	public void setReponse(int reponse) {
		this.reponse = reponse;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

}

package fr.legrain.lib.gui;

import java.awt.AWTEvent;
import java.awt.Event;
import java.util.EventObject;

import javax.swing.JComponent;

import fr.legrain.lib.data.ModeObjet.EnumModeObjet;

public class SortieChampsEvent extends EventObject {
	
	private JComponent input = null;
	
	public SortieChampsEvent(Object source, JComponent input) {
		super(source);
		this.input = input;
	}

	public JComponent getInput() {
		return input;
	}

	public void setInput(JComponent input) {
		this.input = input;
	}
	



}
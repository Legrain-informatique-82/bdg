package fr.legrain.etats.controllers;

import java.util.Date;
import java.util.List;

import org.eclipse.swt.widgets.Control;

public class Param {
	
	public int type;
	public String name;
	public String label;
	public String value;
	public String[][] liste = null;
	public String defaultValue = "";
	public Date defaultDate = new Date();
	public int position;
	public boolean singleLine;
	public List<Control> control;
	
	public Param(int type, String name, String label, String value, String[][] liste,
			String defaultValue, Date defaultDate, int position,
			boolean singleLine) {
		super();
		this.type = type;
		this.name = name;
		this.label = label;
		this.value = value;
		this.liste = liste;
		this.defaultValue = defaultValue;
		this.defaultDate = defaultDate;
		this.position = position;
		this.singleLine = singleLine;
	}
	
	public String findValueList(String s) {
		for (int i = 0; i < liste.length; i++) {
			if(liste[i][0].equals(s)){
				return liste[i][1];
			}
		}
		return null;
	}
	
	
}

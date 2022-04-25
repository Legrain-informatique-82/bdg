package fr.legrain.prestashop.ws.entities;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;

import org.eclipse.persistence.oxm.annotations.XmlCDATA;

public class Language {

	@XmlAttribute
	private int id;

	@XmlValue
	@XmlCDATA
	private String value;

	public Language() {
	}

	public Language(int id, String value) {
		this.id = id;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}

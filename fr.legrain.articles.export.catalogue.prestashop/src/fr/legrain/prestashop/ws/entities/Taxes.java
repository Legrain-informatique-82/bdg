package fr.legrain.prestashop.ws.entities;

import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlCDATA;
import org.eclipse.persistence.oxm.annotations.XmlPath;

import fr.legrain.prestashop.ws.BooleanAdapter;


/**
 * @author nicolas
 */
@XmlRootElement(name="prestashop")
public class Taxes {

	public Taxes() {
		super();
	}

	/*
	<?xml version="1.0" encoding="UTF-8"?>
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<tax>
	<rate required="true" format="isFloat"></rate>
	<active></active>
	<deleted></deleted>
	<name required="true" maxSize="32" format="isGenericName"><language id="1" xlink:href="http://dev2.pageweb.fr/api/languages/1" format="isUnsignedId" ></language></name>
</tax>
</prestashop>
	 */

	@XmlPath("tax/id/text()")
	private Integer id;
	
	@XmlPath("tax/rate/text()")
	private Float rate;
	
	@XmlPath("tax/active/text()")
	private Integer active;
	
	@XmlPath("tax/deleted/text()")
	private Integer deleted;
	
	@XmlPath("tax/name/language")
	@XmlElementWrapper(name="name")
	private List<Language> name = null;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getRate() {
		return rate;
	}

	public void setRate(Float rate) {
		this.rate = rate;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public List<Language> getName() {
		return name;
	}

	public void setName(List<Language> name) {
		this.name = name;
	}
	
	
}

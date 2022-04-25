package fr.legrain.prestashop.ws.entities;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.oxm.annotations.XmlPath;

@XmlRootElement(name="prestashop")
public class Errors {
/*
	<?xml version="1.0" encoding="UTF-8"?>
	<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
	<errors>
	<error>
	<code><![CDATA[65]]></code>
	<message><![CDATA[This image already exists. To modify it, please use the PUT method]]></message>
	</error>
	</errors>
	</prestashop>
	*/
	
	@XmlPath("errors/error/code")
	@XmlElementWrapper(name="errors")
	private String code;
	
}

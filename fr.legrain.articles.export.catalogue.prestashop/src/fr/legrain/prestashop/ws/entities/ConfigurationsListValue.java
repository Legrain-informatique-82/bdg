package fr.legrain.prestashop.ws.entities;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.oxm.annotations.XmlPath;

/**
 * @author nicolas
 */
@XmlRootElement(name="configuration")
public class ConfigurationsListValue {
	
	@XmlAttribute(name="id")
	private Integer id = null;	
		

	public ConfigurationsListValue() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}

package fr.legrain.prestashop.ws.entities;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.oxm.annotations.XmlPath;

/**
 * @author nicolas
 */
@XmlRootElement(name="customer")
public class CustomersListValue {
	
	@XmlAttribute(name="id")
	private Integer id = null;	
		

	public CustomersListValue() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}

package fr.legrain.prestashop.ws.entities;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.oxm.annotations.XmlPath;

/**
 * The product features
 * @author nicolas
 *
 */
@XmlRootElement(name="category")
public class CategoriesListValue {
	
	@XmlAttribute(name="id")
	private Integer id = null;	
		

	public CategoriesListValue() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}

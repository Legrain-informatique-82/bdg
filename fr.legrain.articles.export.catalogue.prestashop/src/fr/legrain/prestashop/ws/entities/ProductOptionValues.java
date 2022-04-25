package fr.legrain.prestashop.ws.entities;

import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.oxm.annotations.XmlPath;

/**
 * The product options value
 * @author nicolas
 *
 */
@XmlRootElement(name="prestashop")
public class ProductOptionValues {
	
	@XmlPath("product_option_value/id/text()")
	private Integer id = null;	
	
	@XmlPath("product_option_value/id_attribute_group/text()")
	private Integer idAttributeGroup;
	
	//private String _default; // probleme de nom ???
	
	@XmlPath("product_option_value/color/text()")
	private String color;
	
	@XmlPath("product_option_value/position/text()")
	private Integer position;
	
	@XmlPath("product_option_value/name/language")
	@XmlElementWrapper(name="name")
	private List<Language> name = null;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdAttributeGroup() {
		return idAttributeGroup;
	}

	public void setIdAttributeGroup(Integer idAttributeGroup) {
		this.idAttributeGroup = idAttributeGroup;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public List<Language> getName() {
		return name;
	}

	public void setName(List<Language> name) {
		this.name = name;
	} 
	
	
}

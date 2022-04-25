package fr.legrain.prestashop.ws.entities;

import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.oxm.annotations.XmlPath;

/**
 * The product features
 * @author nicolas
 *
 */
@XmlRootElement(name="prestashop")
public class ProductFeature {
	
	@XmlPath("product_feature/id/text()")
	private Integer id = null;	
		
	@XmlPath("product_feature/position/text()")
	private Integer position;
	
	@XmlPath("product_feature/name/language")
	@XmlElementWrapper(name="name")
	private List<Language> name = null;
	
	public ProductFeature() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

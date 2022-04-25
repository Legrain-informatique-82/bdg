package fr.legrain.prestashop.ws.entities;

import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlPath;

import fr.legrain.prestashop.ws.BooleanAdapter;


/**
 * The product options
 * @author nicolas
 *
 */
public class ProductOptions {

	@XmlPath("product_option/id/text()")
	private Integer id = null;	
	
	@XmlPath("product_option/is_color_group/text()")
	@XmlJavaTypeAdapter( BooleanAdapter.class )
	private Boolean isColorGroup;
	
	@XmlPath("product_option/name/language")
	@XmlElementWrapper(name="name")
	private List<Language> name; 
	
	@XmlPath("product_option/public_name/language")
	@XmlElementWrapper(name="public_name")
	private List<Language> publicName; 
	
	@XmlPath("product_option/group_type/text()")
	private String groupType;
	
	@XmlPath("product_option/group_type/text()")
	private Integer position = null;	
	
	@XmlPath("product_option/associations/product_option_values/product_option_value")
	@XmlElementWrapper(name="product_option_values")
	private List<ProductOptionValues> associationProductOptionValues = null;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getIsColorGroup() {
		return isColorGroup;
	}

	public void setIsColorGroup(Boolean isColorGroup) {
		this.isColorGroup = isColorGroup;
	}

	public List<Language> getName() {
		return name;
	}

	public void setName(List<Language> name) {
		this.name = name;
	}

	public List<Language> getPublicName() {
		return publicName;
	}

	public void setPublicName(List<Language> publicName) {
		this.publicName = publicName;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public List<ProductOptionValues> getAssociationProductOptionValues() {
		return associationProductOptionValues;
	}

	public void setAssociationProductOptionValues(
			List<ProductOptionValues> associationProductOptionValues) {
		this.associationProductOptionValues = associationProductOptionValues;
	}
	
	
}

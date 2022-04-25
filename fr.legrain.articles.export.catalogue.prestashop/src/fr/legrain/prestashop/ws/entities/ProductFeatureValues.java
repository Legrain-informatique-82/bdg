package fr.legrain.prestashop.ws.entities;

import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlPath;

import fr.legrain.prestashop.ws.BooleanAdapter;


/**
 * The product feature values
 * @author nicolas
 *
 */
@XmlRootElement(name="prestashop")
public class ProductFeatureValues {

	@XmlPath("product_feature_value/id/text()")
	private Integer id = null;	
	
	@XmlPath("product_feature_value/id_feature/text()")	
	private Integer idFeature;
	
	@XmlPath("product_feature_value/custom/text()")	
	@XmlJavaTypeAdapter( BooleanAdapter.class )
	private Boolean custom;
		
	@XmlPath("product_feature_value/position/text()")
	private Integer position;
	
	@XmlPath("product_feature_value/value/language")
	@XmlElementWrapper(name="value")
	private List<Language> value = null;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdFeature() {
		return idFeature;
	}

	public void setIdFeature(Integer idFeature) {
		this.idFeature = idFeature;
	}

	public Boolean getCustom() {
		return custom;
	}

	public void setCustom(Boolean custom) {
		this.custom = custom;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public List<Language> getValue() {
		return value;
	}

	public void setValue(List<Language> value) {
		this.value = value;
	} 
	
	
	
}

package fr.legrain.prestashop.ws.entities;

import javax.xml.bind.annotation.XmlElement;

public class ProductAssociationFeature {
	
	@XmlElement(name="id")
	private int id;
	
	@XmlElement(name="id_feature_value")
	private int idFeatureValue;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdFeatureValue() {
		return idFeatureValue;
	}
	public void setIdFeatureValue(int idFeatureValue) {
		this.idFeatureValue = idFeatureValue;
	}
	
	public ProductAssociationFeature() {

	}
	
	public ProductAssociationFeature(int id, int idFeatureValue) {
		super();
		this.id = id;
		this.idFeatureValue = idFeatureValue;
	}
	
	
	
}

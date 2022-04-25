package fr.legrain.prestashop.ws.entities;

import javax.xml.bind.annotation.XmlElement;

public class ProductAssociationStockAvailable {
	
	@XmlElement(name="id")
	private int id;
	
	@XmlElement(name="id_product_attribute")
	private int idProductAttribute;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public ProductAssociationStockAvailable() {
	}
	
	public ProductAssociationStockAvailable(int id) {
		this.id = id;
	}

	public int getIdProductAttribute() {
		return idProductAttribute;
	}

	public void setIdProductAttribute(int idProductAttribute) {
		this.idProductAttribute = idProductAttribute;
	}
	
	
}

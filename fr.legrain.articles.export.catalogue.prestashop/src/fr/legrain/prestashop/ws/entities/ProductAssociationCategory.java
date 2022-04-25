package fr.legrain.prestashop.ws.entities;

import javax.xml.bind.annotation.XmlElement;

public class ProductAssociationCategory {
	
	@XmlElement(name="id")
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public ProductAssociationCategory() {
	}
	
	public ProductAssociationCategory(int id) {
		this.id = id;
	}
	
	
}

package fr.legrain.prestashop.ws.entities;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;

public class ProductAssociationCategoryList extends ArrayList<ProductAssociationCategory>{
	@XmlAttribute
	private String node_type = "category";
	
}

package fr.legrain.prestashop.ws.entities;

import javax.xml.bind.annotation.XmlElement;

import org.eclipse.persistence.oxm.annotations.XmlPath;

public class OrderRow {
	
	@XmlElement
	private Integer id; //<id></id>
	
	@XmlElement(name="product_id")
	private Integer productId; //<product_id required="true"></product_id>
	
	@XmlElement(name="product_attribute_id")
	private Integer productAttributeId; //<product_attribute_id required="true"></product_attribute_id>
	
	@XmlElement(name="product_quantity")
	private Integer productQuantity; //<product_quantity required="true"></product_quantity>
	
	@XmlElement(name="product_name")
	private String productName; //<product_name></product_name>
	
	@XmlElement(name="product_price")
	private Float productPrice; //<product_price></product_price>

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getProductAttributeId() {
		return productAttributeId;
	}

	public void setProductAttributeId(Integer productAttributeId) {
		this.productAttributeId = productAttributeId;
	}

	public Integer getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(Integer productQuantity) {
		this.productQuantity = productQuantity;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Float getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Float productPrice) {
		this.productPrice = productPrice;
	}
	
	
}

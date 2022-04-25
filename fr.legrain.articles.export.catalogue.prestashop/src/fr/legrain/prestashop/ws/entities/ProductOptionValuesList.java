package fr.legrain.prestashop.ws.entities;

import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.oxm.annotations.XmlPath;

@XmlRootElement(name="prestashop")
public class ProductOptionValuesList {

	@XmlPath("product_option_values/product_option_value")
	@XmlElementWrapper(name="product_option_values")
	private List<ProductOptionValues> productOptionValues = null;

	public List<ProductOptionValues> getProductOptionValues() {
		return productOptionValues;
	}

	public void setProductOptionValues(List<ProductOptionValues> productOptionValues) {
		this.productOptionValues = productOptionValues;
	}
}
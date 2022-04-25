package fr.legrain.prestashop.ws.entities;

import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.oxm.annotations.XmlPath;

@XmlRootElement(name="prestashop")
public class ProductOptionsList {
		
		@XmlPath("product_options/product_option")
		@XmlElementWrapper(name="product_options")
		private List<ProductOptions> productOptions = null;

		public List<ProductOptions> getProductOptions() {
			return productOptions;
		}

		public void setProductOptions(List<ProductOptions> productOptions) {
			this.productOptions = productOptions;
		}

		
}

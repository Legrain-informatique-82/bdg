package fr.legrain.prestashop.ws.entities;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlPath;

import fr.legrain.prestashop.ws.BooleanAdapter;
import fr.legrain.prestashop.ws.ProductFeaturesAdapter;


@XmlRootElement(name="prestashop")
//@XmlRootElement(name="prestashop/product_features")
public class ProductFeatures {

//	//@XmlPath("product_features/product_feature")
//	@XmlPath("product_features")
//	@XmlElementWrapper(name="product_features")
//	@XmlElement(name="product_feature")
//	private List<ProductFeature> productFeatures = null;
//	
//	public ProductFeatures() {
//		
//	}
//
//	public List<ProductFeature> getProductFeatures() {
//		return productFeatures;
//	}
//
//	public void setProductFeatures(List<ProductFeature> productFeatures) {
//		this.productFeatures = productFeatures;
//	}
	
	@XmlElementWrapper(name="product_features")
	@XmlElement(name="product_feature")
	private List<ProductFeatureListValue> productFeatures = null;
	
	public ProductFeatures() {

	}

	public List<ProductFeatureListValue> getProductFeatures() {
		return productFeatures;
	}

	public void setProductFeatures(List<ProductFeatureListValue> productFeatures) {
		this.productFeatures = productFeatures;
	}
}
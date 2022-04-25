package fr.legrain.prestashop.ws.entities;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlList;

import org.apache.cxf.binding.corba.wsdl.Array;
import org.eclipse.persistence.oxm.annotations.XmlPath;

public class ProductAssociation {

	@XmlList
	@XmlElementWrapper(name="categories")
	@XmlElement(name="category")
	private List<ProductAssociationCategory> categories = new ArrayList<ProductAssociationCategory>();
	//@XmlPath("products/associations/categories")
	//private ProductAssociationCategoryList categories = new ProductAssociationCategoryList();
	
	@XmlPath("categories/@node_type")
	private String node_type_categories = "category";
	
	@XmlList
	@XmlElementWrapper(name="images")
	@XmlElement(name="image")
	private List<ProductAssociationImage> images = new ArrayList<ProductAssociationImage>();
	
	@XmlPath("images/@node_type")
	private String node_type_image = "image";
	
	@XmlList
	@XmlElementWrapper(name="combinations")
	@XmlElement(name="combinations")
	private List<ProductAssociationCombinations> combinations = new ArrayList<ProductAssociationCombinations>();
	
	@XmlPath("combinations/@node_type")
	private String node_type_combinationss = "combinations";
	
	@XmlList
	@XmlElementWrapper(name="product_option_values")
	@XmlElement(name="product_option_values")
	private List<ProductAssociationOptionsValues> optionsValues = new ArrayList<ProductAssociationOptionsValues>();
	
	@XmlPath("product_option_values/@node_type")
	private String node_type_product_option_values = "product_option_values";
	
	@XmlList
	@XmlElementWrapper(name="product_features")
	@XmlElement(name="product_feature")
	private List<ProductAssociationFeature> features = new ArrayList<ProductAssociationFeature>();
	
	@XmlPath("product_features/@node_type")
	private String node_type_product_feature = "product_feature";
	
	@XmlList
	@XmlElementWrapper(name="stock_availables")
	@XmlElement(name="stock_available")
	private List<ProductAssociationStockAvailable> stocks = new ArrayList<ProductAssociationStockAvailable>();
	
	@XmlPath("stock_availables/@node_type")
	private String node_type_stock_available = "stock_available";

	public List<ProductAssociationCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<ProductAssociationCategory> categories) {
		this.categories = categories;
	}

	public List<ProductAssociationImage> getImages() {
		return images;
	}

	public void setImages(List<ProductAssociationImage> images) {
		this.images = images;
	}

	public List<ProductAssociationCombinations> getCombinations() {
		return combinations;
	}

	public void setCombinations(List<ProductAssociationCombinations> combinations) {
		this.combinations = combinations;
	}

	public List<ProductAssociationOptionsValues> getOptionsValues() {
		return optionsValues;
	}

	public void setOptionsValues(List<ProductAssociationOptionsValues> optionsValues) {
		this.optionsValues = optionsValues;
	}

	public List<ProductAssociationFeature> getFeatures() {
		return features;
	}

	public void setFeatures(List<ProductAssociationFeature> features) {
		this.features = features;
	}

	public List<ProductAssociationStockAvailable> getStocks() {
		return stocks;
	}

	public void setStocks(List<ProductAssociationStockAvailable> stocks) {
		this.stocks = stocks;
	}
}

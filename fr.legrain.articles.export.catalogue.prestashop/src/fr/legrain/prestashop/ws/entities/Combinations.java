package fr.legrain.prestashop.ws.entities;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlPath;

import fr.legrain.prestashop.ws.BooleanAdapter;


/**
 * The product combinations
 * @author nicolas
 *
 */
@XmlRootElement(name="prestashop")
public class Combinations {

	@XmlPath("combination/id/text()")
	private Integer id = null;	
	
	@XmlPath("combination/id_product/text()")
	private Integer idProduct;
	
	@XmlPath("combination/reference/text()")
	private String reference;
	
	@XmlPath("combination/supplier_reference/text()")
	private String supplierReference;
	
	@XmlPath("combination/location/text()")
	private String location;
	
	@XmlPath("combination/ean13/text()")
	private String ean13;
	
	@XmlPath("combination/upc/text()")
	private String upc;
	
	@XmlPath("combination/wholesale_price/text()")
	private Float wholesalePrice;
	
	@XmlPath("combination/price/text()")
	private Float price;
	
	@XmlPath("combination/ecotax/text()")
	private Float ecotax;
	
	@XmlPath("combination/quantity/text()")
	private Integer quantity;
	
	@XmlPath("combination/weight/text()")
	private Float weight;
	
	@XmlPath("combination/default_on/text()")
	@XmlJavaTypeAdapter( BooleanAdapter.class )
	private Boolean defaultOn;
	
	@XmlPath("combination/unit_price_impact/text()")
	private Float unitPriceImpact;
	
	@XmlPath("combination/available_date/text()")
	private Date availableDate;
	
	@XmlPath("combination/minimal_quantity/text()")
	private Integer minimalQuantity = null;	
	
	@XmlPath("combination/associations/product_option_values/product_option_value")
	@XmlElementWrapper(name="product_option_values")
	private List<ProductOptionValues> associationProductOptionValues = null;
	
	@XmlPath("combination/associations/images/image")
	@XmlElementWrapper(name="images")
	private List<Images> associationImages = null;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Integer idProduct) {
		this.idProduct = idProduct;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getSupplierReference() {
		return supplierReference;
	}

	public void setSupplierReference(String supplierReference) {
		this.supplierReference = supplierReference;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getEan13() {
		return ean13;
	}

	public void setEan13(String ean13) {
		this.ean13 = ean13;
	}

	public String getUpc() {
		return upc;
	}

	public void setUpc(String upc) {
		this.upc = upc;
	}

	public Float getWholesalePrice() {
		return wholesalePrice;
	}

	public void setWholesalePrice(Float wholesalePrice) {
		this.wholesalePrice = wholesalePrice;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Float getEcotax() {
		return ecotax;
	}

	public void setEcotax(Float ecotax) {
		this.ecotax = ecotax;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public Boolean getDefaultOn() {
		return defaultOn;
	}

	public void setDefaultOn(Boolean defaultOn) {
		this.defaultOn = defaultOn;
	}

	public Float getUnitPriceImpact() {
		return unitPriceImpact;
	}

	public void setUnitPriceImpact(Float unitPriceImpact) {
		this.unitPriceImpact = unitPriceImpact;
	}

	public Date getAvailableDate() {
		return availableDate;
	}

	public void setAvailableDate(Date availableDate) {
		this.availableDate = availableDate;
	}

	public Integer getMinimalQuantity() {
		return minimalQuantity;
	}

	public void setMinimalQuantity(Integer minimalQuantity) {
		this.minimalQuantity = minimalQuantity;
	}

	public List<ProductOptionValues> getAssociationProductOptionValues() {
		return associationProductOptionValues;
	}

	public void setAssociationProductOptionValues(
			List<ProductOptionValues> associationProductOptionValues) {
		this.associationProductOptionValues = associationProductOptionValues;
	}

	public List<Images> getAssociationImages() {
		return associationImages;
	}

	public void setAssociationImages(List<Images> associationImages) {
		this.associationImages = associationImages;
	}

}

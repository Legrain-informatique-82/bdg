package fr.legrain.prestashop.ws.entities;

import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlPath;

import fr.legrain.prestashop.ws.BooleanAdapter;


/**
 * The products
 * @author nicolas
 *
 */
@XmlRootElement(name="prestashop")
public class Products {
	
	@XmlPath("product/id/text()")
	private Integer id = null;
	
	@XmlPath("product/id_manufacturer/text()")
	private Integer idManufacturer = null;
	
	@XmlPath("product/id_supplier/text()")
	private Integer idSupplier = null;
	
	@XmlPath("product/id_category_default/text()")
	private Integer idCategoryDefault = null;
	
	@XmlPath("product/out_of_stock/text()")
	private Integer outOfStock = null;
	
	@XmlPath("product/new/text()")
	@XmlJavaTypeAdapter( BooleanAdapter.class )
	private Boolean _new = null; // probleme de nom
	
	@XmlPath("product/cache_default_attribute/text()")
	private String cacheDefaultAttribute = null;
	
	@XmlPath("product/id_default_image/text()")
	private Integer idDefaultImage = null;
	
	@XmlPath("product/reference/text()")
	private String reference = null;
	
	@XmlPath("product/supplier_reference/text()")
	private String supplierReference = null;
	
	@XmlPath("product/location/text()")
	private String location = null;
	
	@XmlPath("product/ean13/text()")
	private String ean13 = null;
	
	@XmlPath("product/upc/text()")
	private String upc = null;
	
	@XmlPath("product/unity/text()")
	private String unity = null;
	
	@XmlPath("product/id_tax_rules_group/text()")
	private Integer idTaxRulesGroup = null;
	
	@XmlPath("product/id_color_default/text()")
	private Integer idcolorDefault = null;
	
	@XmlPath("product/minimal_quantity/text()")
	private Integer minimalQuantity = null;
	
	@XmlPath("product/price/text()")
	private Float price = null;
	
	@XmlPath("product/additional_shipping_cost/text()")
	private Float additionalShippingCost = null;
	
	@XmlPath("product/wholesale_price/text()")
	private Float wholesalePrice = null;
	
	@XmlJavaTypeAdapter( BooleanAdapter.class )
	@XmlPath("product/on_sale/text()")
	private Boolean onSale;
	
	@XmlJavaTypeAdapter( BooleanAdapter.class )
	@XmlPath("product/online_only/text()")
	private Boolean onlineOnly = null;
	
	@XmlPath("product/ecotax/text()")
	private Float ecotax = null;
	
	@XmlPath("product/unit_price/text()")
	private Float unitPrice = null;
	
	@XmlPath("product/width/text()")
	private Float width = null;
	
	@XmlPath("product/height/text()")
	private Float height = null;
	
	@XmlPath("product/weight/text()")
	private Float weight = null;
	
	@XmlPath("product/quantity_discount/text()")
	private Integer quantityDiscount;
	
	@XmlPath("product/customizable/text()")
	private String customizable = null;
	
	@XmlPath("product/uploadable_files/text()")
	private String uploadableFiles = null;
	
	@XmlPath("product/text_fields/text()")
	private String textFields = null;
	
	@XmlJavaTypeAdapter( BooleanAdapter.class )
	@XmlPath("product/active/text()")
	private Boolean active = null;
	
	@XmlJavaTypeAdapter( BooleanAdapter.class )
	@XmlPath("product/available_for_order/text()")
	private Boolean availableForOrder = null;
	
	@XmlPath("product/condition/text()")
	private String condition = null;
	
	@XmlPath("product/show_price/text()")
	@XmlJavaTypeAdapter( BooleanAdapter.class )
	private Boolean showPrice = null;
	
	@XmlPath("product/indexed/text()")
	private String indexed = null;
	
	@XmlPath("product/cache_is_pack/text()")
	private String cacheIsPack = null;
	
	@XmlPath("product/cache_has_attachments/text()")
	private String cacheHasAttachments = null;
	
//	@XmlPath("product/quantity/text()")
//	private Integer quantity = null; //non modifiable
	
	@XmlPath("product/meta_description/language")
	@XmlElementWrapper(name="meta_description")
	private List<Language> metaDescription = null;
	
	@XmlPath("product/meta_keywords/language")
	@XmlElementWrapper(name="meta_keywords")
	private List<Language> metaKeywords = null; // ???
	
	@XmlPath("product/meta_title/language")
	@XmlElementWrapper(name="meta_title")
	private List<Language> metaTitle = null; // ???
	
	@XmlPath("product/link_rewrite/language")
	@XmlElementWrapper(name="link_rewrite")
	private List<Language> linkRewrite = null; // ???
	
	@XmlPath("product/name/language")
	@XmlElementWrapper(name="name")
	private List<Language> name = null; // ???
	
	@XmlPath("product/available_now/language")
	@XmlElementWrapper(name="available_now")
	private List<Language> availableNow = null; // ???
	
	@XmlPath("product/available_later/language")
	@XmlElementWrapper(name="available_later")
	private List<Language> availableLater = null; // ???
	
	@XmlPath("product/description/language")
	@XmlElementWrapper(name="description")
	private List<Language> description = null; // ???
	
	@XmlPath("product/description_short/language")
	@XmlElementWrapper(name="description_short")
	private List<Language> descriptionShort = null; //???
	
	@XmlPath("product/associations")
	private ProductAssociation associations = null;
	//association category
	//association image
	
	public void cleanLanguageElements() {
		
		associations = null;
		
		if(metaDescription != null) {
			if(metaDescription.isEmpty())
				metaDescription = null;
		}
		
		if(metaKeywords != null) {
			if(metaKeywords.isEmpty())
				metaKeywords = null;
		}
		
		if(metaTitle != null) {
			if(metaTitle.isEmpty())
				metaTitle = null;
		}
		
		if(linkRewrite != null) {
			if(linkRewrite.isEmpty())
				linkRewrite = null;
		}
		
		if(name != null) {
			if(name.isEmpty())
				name = null;
		}
		
		if(availableNow != null) {
			if(metaDescription.isEmpty())
				metaDescription = null;
		}
		
		if(availableLater != null) {
			if(availableLater.isEmpty())
				availableLater = null;
		}
		
		if(description != null) {
			if(description.isEmpty())
				description = null;
		}
		
		if(descriptionShort != null) {
			if(descriptionShort.isEmpty())
				descriptionShort = null;
		}
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdManufacturer() {
		return idManufacturer;
	}
	public void setIdManufacturer(Integer idManufacturer) {
		this.idManufacturer = idManufacturer;
	}
	public Integer getIdSupplier() {
		return idSupplier;
	}
	public void setIdSupplier(Integer idSupplier) {
		this.idSupplier = idSupplier;
	}
	public Integer getIdCategoryDefault() {
		return idCategoryDefault;
	}
	public void setIdCategoryDefault(Integer idCategoryDefault) {
		this.idCategoryDefault = idCategoryDefault;
	}
	public Integer getOutOfStock() {
		return outOfStock;
	}
	public void setOutOfStock(Integer outOfStock) {
		this.outOfStock = outOfStock;
	}
	public Boolean is_new() {
		return _new;
	}
	public void set_new(Boolean new1) {
		_new = new1;
	}
	public String getCacheDefaultAttribute() {
		return cacheDefaultAttribute;
	}
	public void setCacheDefaultAttribute(String cacheDefaultAttribute) {
		this.cacheDefaultAttribute = cacheDefaultAttribute;
	}
	public Integer getIdDefaultImage() {
		return idDefaultImage;
	}
	public void setIdDefaultImage(Integer idDefaultImage) {
		this.idDefaultImage = idDefaultImage;
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
	public String getUnity() {
		return unity;
	}
	public void setUnity(String unity) {
		this.unity = unity;
	}
	public Integer getIdTaxRulesGroup() {
		return idTaxRulesGroup;
	}
	public void setIdTaxRulesGroup(Integer idTaxRulesGroup) {
		this.idTaxRulesGroup = idTaxRulesGroup;
	}
	public Integer getIdcolorDefault() {
		return idcolorDefault;
	}
	public void setIdcolorDefault(Integer idcolorDefault) {
		this.idcolorDefault = idcolorDefault;
	}
	public Integer getMinimalQuantity() {
		return minimalQuantity;
	}
	public void setMinimalQuantity(Integer minimalQuantity) {
		this.minimalQuantity = minimalQuantity;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Float getAdditionalShippingCost() {
		return additionalShippingCost;
	}
	public void setAdditionalShippingCost(Float additionalShippingCost) {
		this.additionalShippingCost = additionalShippingCost;
	}
	public Float getWholesalePrice() {
		return wholesalePrice;
	}
	public void setWholesalePrice(Float wholesalePrice) {
		this.wholesalePrice = wholesalePrice;
	}
	public Boolean isOnSale() {
		return onSale;
	}
	public void setOnSale(Boolean onSale) {
		this.onSale = onSale;
	}
	public Boolean isOnlineOnly() {
		return onlineOnly;
	}
	public void setOnlineOnly(Boolean onlineOnly) {
		this.onlineOnly = onlineOnly;
	}
	public Float getEcotax() {
		return ecotax;
	}
	public void setEcotax(Float ecotax) {
		this.ecotax = ecotax;
	}
	public Float getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Float unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Float getWidth() {
		return width;
	}
	public void setWidth(Float width) {
		this.width = width;
	}
	public Float getHeight() {
		return height;
	}
	public void setHeight(Float height) {
		this.height = height;
	}
	public Float getWeight() {
		return weight;
	}
	public void setWeight(Float weight) {
		this.weight = weight;
	}
	public Integer getQuantityDiscount() {
		return quantityDiscount;
	}
	public void setQuantityDiscount(Integer quantityDiscount) {
		this.quantityDiscount = quantityDiscount;
	}
	public String getCustomizable() {
		return customizable;
	}
	public void setCustomizable(String customizable) {
		this.customizable = customizable;
	}
	public String getUploadableFiles() {
		return uploadableFiles;
	}
	public void setUploadableFiles(String uploadableFiles) {
		this.uploadableFiles = uploadableFiles;
	}
	public String getTextFields() {
		return textFields;
	}
	public void setTextFields(String textFields) {
		this.textFields = textFields;
	}
	public Boolean isActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public Boolean isAvailableForOrder() {
		return availableForOrder;
	}
	public void setAvailableForOrder(Boolean availableForOrder) {
		this.availableForOrder = availableForOrder;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public Boolean getShowPrice() {
		return showPrice;
	}
	public void setShowPrice(Boolean showPrice) {
		this.showPrice = showPrice;
	}
	public String getIndexed() {
		return indexed;
	}
	public void setIndexed(String indexed) {
		this.indexed = indexed;
	}
	public String getCacheIsPack() {
		return cacheIsPack;
	}
	public void setCacheIsPack(String cacheIsPack) {
		this.cacheIsPack = cacheIsPack;
	}
	public String getCacheHasAttachments() {
		return cacheHasAttachments;
	}
	public void setCacheHasAttachments(String cacheHasAttachments) {
		this.cacheHasAttachments = cacheHasAttachments;
	}
//	public Integer getQuantity() {
//		return quantity;
//	}
//	public void setQuantity(Integer quantity) {
//		this.quantity = quantity;
//	}
	
	public List<Language> getMetaDescription() {
		return metaDescription;
	}
	public void setMetaDescription(List<Language> metaDescription) {
		this.metaDescription = metaDescription;
	}
	
	public List<Language> getMetaKeywords() {
		return metaKeywords;
	}
	public void setMetaKeywords(List<Language> metaKeywords) {
		this.metaKeywords = metaKeywords;
	}
	public List<Language> getMetaTitle() {
		return metaTitle;
	}
	public void setMetaTitle(List<Language> metaTitle) {
		this.metaTitle = metaTitle;
	}
	public List<Language> getLinkRewrite() {
		return linkRewrite;
	}
	public void setLinkRewrite(List<Language> linkRewrite) {
		this.linkRewrite = linkRewrite;
	}
	public List<Language> getName() {
		return name;
	}
	public void setName(List<Language> name) {
		this.name = name;
	}
	public List<Language> getAvailableNow() {
		return availableNow;
	}
	public void setAvailableNow(List<Language> availableNow) {
		this.availableNow = availableNow;
	}
	public List<Language> getAvailableLater() {
		return availableLater;
	}
	public void setAvailableLater(List<Language> availableLater) {
		this.availableLater = availableLater;
	}
	public List<Language> getDescription() {
		return description;
	}
	public void setDescription(List<Language> description) {
		this.description = description;
	}
	public List<Language> getDescriptionShort() {
		return descriptionShort;
	}
	public void setDescriptionShort(List<Language> descriptionShort) {
		this.descriptionShort = descriptionShort;
	}
	public ProductAssociation getAssociations() {
		return associations;
	}
	public void setAssociations(ProductAssociation associations) {
		this.associations = associations;
	}
}

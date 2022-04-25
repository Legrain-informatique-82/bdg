package fr.legrain.prestashop.ws.entities;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlPath;

import fr.legrain.prestashop.ws.BooleanAdapter;


/**
 * The product categories
 * @author nicolas
 *
 */
@XmlRootElement(name="prestashop")
public class Categories {

	@XmlPath("category/id/text()")
	private Integer id = null;	

	@XmlPath("category/id_parent/text()")
	@XmlJavaTypeAdapter(CategoryAdapter.class)
	private Categories idParent;

	@XmlPath("category/active/text()")
	@XmlJavaTypeAdapter( BooleanAdapter.class )
	private Boolean active;

	@XmlPath("category/position/text()")
	private Integer position;
	
	@XmlPath("category/id_shop_default/text()")
	private Integer idShopDefault;
	
//	@XmlPath("category/nb_products_recursive/text()")
//	private Integer nbProductsRecursive; // lecture seule

	@XmlJavaTypeAdapter( BooleanAdapter.class )
	@XmlPath("category/is_root_category/text()")
	private Boolean isRootCategory;
	
//	@XmlPath("category/date_add/text()")
//	private Date dateAdd;
//	
//	@XmlPath("category/date_upd/text()")
//	private Date dateUpd;

//	@XmlPath("category/level_depth/text()")
//	private Integer levelDepth; //non variable

	@XmlPath("category/name/language")
	@XmlElementWrapper(name="name")
	private List<Language> name = null; // ???

	@XmlPath("category/link_rewrite/language")
	@XmlElementWrapper(name="link_rewrite")
	private List<Language> linkRewrite = null; // ???

	@XmlPath("category/meta_title/language")
	@XmlElementWrapper(name="meta_title")
	private List<Language> metaTitle = null; // ???

	@XmlPath("category/meta_description/language")
	@XmlElementWrapper(name="meta_description")
	private List<Language> metaDescription = null; // ???

	@XmlPath("category/meta_keywords/language")
	@XmlElementWrapper(name="meta_keywords")
	private List<Language> metaKeywords = null; // ?????

	@XmlPath("category/description/language")
	@XmlElementWrapper(name="description")
	private List<Language> description = null; // ???

	@XmlPath("category/associations/categories/category")
	@XmlElementWrapper(name="categories")
	private List<CategoriesListValue> associationCategories = null; // ???

	@XmlPath("category/associations/products/product")
	@XmlElementWrapper(name="products")
	private List<ProductsCategoryAssociationValue> associationProducts = null;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Categories getIdParent() {
		return idParent;
	}

	public void setIdParent(Categories idParent) {
		this.idParent = idParent;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getIdShopDefault() {
		return idShopDefault;
	}

	public void setIdShopDefault(Integer idShopDefault) {
		this.idShopDefault = idShopDefault;
	}

//	public Integer getNbProductsRecursive() {
//		return nbProductsRecursive;
//	}
//
//	public void setNbProductsRecursive(Integer nbProductsRecursive) {
//		this.nbProductsRecursive = nbProductsRecursive;
//	}

	public Boolean getIsRootCategory() {
		return isRootCategory;
	}

	public void setIsRootCategory(Boolean isRootCategory) {
		this.isRootCategory = isRootCategory;
	}

//	public Date getDateAdd() {
//		return dateAdd;
//	}
//
//	public void setDateAdd(Date dateAdd) {
//		this.dateAdd = dateAdd;
//	}
//
//	public Date getDateUpd() {
//		return dateUpd;
//	}
//
//	public void setDateUpd(Date dateUpd) {
//		this.dateUpd = dateUpd;
//	}

//	public Integer getLevelDepth() {
//		return levelDepth;
//	}
//
//	public void setLevelDepth(Integer levelDepth) {
//		this.levelDepth = levelDepth;
//	}

	public List<Language> getName() {
		return name;
	}

	public void setName(List<Language> name) {
		this.name = name;
	}

	public List<Language> getLinkRewrite() {
		return linkRewrite;
	}

	public void setLinkRewrite(List<Language> linkRewrite) {
		this.linkRewrite = linkRewrite;
	}

	public List<Language> getMetaTitle() {
		return metaTitle;
	}

	public void setMetaTitle(List<Language> metaTitle) {
		this.metaTitle = metaTitle;
	}

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

	public List<Language> getDescription() {
		return description;
	}

	public void setDescription(List<Language> description) {
		this.description = description;
	}

	public List<CategoriesListValue> getAssociationCategories() {
		return associationCategories;
	}

	public void setAssociationCategories(List<CategoriesListValue> associationCategories) {
		this.associationCategories = associationCategories;
	}

	public List<ProductsCategoryAssociationValue> getAssociationProducts() {
		return associationProducts;
	}

	public void setAssociationProducts(List<ProductsCategoryAssociationValue> associationProducts) {
		this.associationProducts = associationProducts;
	}

}

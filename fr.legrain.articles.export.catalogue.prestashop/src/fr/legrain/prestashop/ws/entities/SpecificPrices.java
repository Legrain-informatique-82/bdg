package fr.legrain.prestashop.ws.entities;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;

import org.eclipse.persistence.oxm.annotations.XmlPath;

import fr.legrain.prestashop.ws.DateAdapter;


@XmlRootElement(name="prestashop")
public class SpecificPrices {
	
	@XmlPath("specific_price/id/text()")
	private Integer id = null;
	
	@XmlPath("specific_price/id_shop_group/text()")
	private Integer idShopGroup = null;

	@XmlPath("specific_price/id_shop/text()")
	private Integer idShop = null; //not null
	
	@XmlPath("specific_price/id_cart/text()")
	private Integer idCart = null; //not null
	
	@XmlPath("specific_price/id_product/text()")
	private Integer idProduct = null; //not null
	
	@XmlPath("specific_price/id_product_attribute/text()")
	private Integer idProductAttribute = null; //not null
	
	@XmlPath("specific_price/id_currency/text()")
	private Integer idCurrency = null; //not null
	
	@XmlPath("specific_price/id_country/text()")
	private Integer idCountry = null; //not null
	
	@XmlPath("specific_price/id_group/text()")
	private Integer idGroup = null; //not null
	
	@XmlPath("specific_price/id_customer/text()")
	private Integer idCustomer = null; //not null
	
	@XmlPath("specific_price/id_specific_price_rule/text()")
	private Integer idSpecificPriceRule = null; //not null
	
	@XmlPath("specific_price/price/text()")
	private Float price = null; //not null
	
	@XmlPath("specific_price/from_quantity/text()")
	private Integer fromQuantity = null; //not null
	
	@XmlPath("specific_price/reduction/text()")
	private Float reduction = null; //not null
	
	@XmlPath("specific_price/reduction_type/text()")
	private String reductionType = null; //not null
	
	@XmlPath("specific_price/from/text()")
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date from = null; //not null
	DateAdapter b;
	
	@XmlPath("specific_price/to/text()")
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date to = null; //not null

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdShopGroup() {
		return idShopGroup;
	}

	public void setIdShopGroup(Integer idShopGroup) {
		this.idShopGroup = idShopGroup;
	}

	public Integer getIdShop() {
		return idShop;
	}

	public void setIdShop(Integer idShop) {
		this.idShop = idShop;
	}

	public Integer getIdCart() {
		return idCart;
	}

	public void setIdCart(Integer idCart) {
		this.idCart = idCart;
	}

	public Integer getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Integer idProduct) {
		this.idProduct = idProduct;
	}

	public Integer getIdProductAttribute() {
		return idProductAttribute;
	}

	public void setIdProductAttribute(Integer idProductAttribute) {
		this.idProductAttribute = idProductAttribute;
	}

	public Integer getIdCurrency() {
		return idCurrency;
	}

	public void setIdCurrency(Integer idCurrency) {
		this.idCurrency = idCurrency;
	}

	public Integer getIdCountry() {
		return idCountry;
	}

	public void setIdCountry(Integer idCountry) {
		this.idCountry = idCountry;
	}

	public Integer getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(Integer idGroup) {
		this.idGroup = idGroup;
	}

	public Integer getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(Integer idCustomer) {
		this.idCustomer = idCustomer;
	}

	public Integer getIdSpecificPriceRule() {
		return idSpecificPriceRule;
	}

	public void setIdSpecificPriceRule(Integer idSpecificPriceRule) {
		this.idSpecificPriceRule = idSpecificPriceRule;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Integer getFromQuantity() {
		return fromQuantity;
	}

	public void setFromQuantity(Integer fromQuantity) {
		this.fromQuantity = fromQuantity;
	}

	public Float getReduction() {
		return reduction;
	}

	public void setReduction(Float reduction) {
		this.reduction = reduction;
	}

	public String getReductionType() {
		return reductionType;
	}

	public void setReductionType(String reductionType) {
		this.reductionType = reductionType;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}
}

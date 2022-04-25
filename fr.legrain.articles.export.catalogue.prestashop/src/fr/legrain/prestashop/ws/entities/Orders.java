package fr.legrain.prestashop.ws.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlPath;

import fr.legrain.prestashop.ws.BooleanAdapter;
import fr.legrain.prestashop.ws.DateAdapter;


/**
 * The Customers orders
 * @author nicolas
 *
 */
@XmlRootElement(name="prestashop")
public class Orders {
/*
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<order>
	<id_address_delivery required="true" format="isUnsignedId"></id_address_delivery>
	<id_address_invoice required="true" format="isUnsignedId"></id_address_invoice>
	<id_cart required="true" format="isUnsignedId"></id_cart>
	<id_currency required="true" format="isUnsignedId"></id_currency>
	<id_lang required="true" format="isUnsignedId"></id_lang>
	<id_customer required="true" format="isUnsignedId"></id_customer>
	<id_carrier required="true" format="isUnsignedId"></id_carrier>
	<current_state format="isUnsignedId"></current_state>
	<module></module>
	<invoice_number></invoice_number>
	<invoice_date></invoice_date>
	<delivery_number></delivery_number>
	<delivery_date></delivery_date>
	<valid></valid>
	<date_add format="isDate"></date_add>
	<date_upd format="isDate"></date_upd>
	<id_shop_group format="isUnsignedId"></id_shop_group>
	<id_shop format="isUnsignedId"></id_shop>
	<secure_key format="isMd5"></secure_key>
	<payment required="true" format="isGenericName"></payment>
	<recyclable format="isBool"></recyclable>
	<gift format="isBool"></gift>
	<gift_message format="isMessage"></gift_message>
	<mobile_theme format="isBool"></mobile_theme>
	<total_discounts format="isPrice"></total_discounts>
	<total_discounts_tax_incl format="isPrice"></total_discounts_tax_incl>
	<total_discounts_tax_excl format="isPrice"></total_discounts_tax_excl>
	<total_paid required="true" format="isPrice"></total_paid>
	<total_paid_tax_incl format="isPrice"></total_paid_tax_incl>
	<total_paid_tax_excl format="isPrice"></total_paid_tax_excl>
	<total_paid_real required="true" format="isPrice"></total_paid_real>
	<total_products required="true" format="isPrice"></total_products>
	<total_products_wt required="true" format="isPrice"></total_products_wt>
	<total_shipping format="isPrice"></total_shipping>
	<total_shipping_tax_incl format="isPrice"></total_shipping_tax_incl>
	<total_shipping_tax_excl format="isPrice"></total_shipping_tax_excl>
	<carrier_tax_rate format="isFloat"></carrier_tax_rate>
	<total_wrapping format="isPrice"></total_wrapping>
	<total_wrapping_tax_incl format="isPrice"></total_wrapping_tax_incl>
	<total_wrapping_tax_excl format="isPrice"></total_wrapping_tax_excl>
	<shipping_number format="isTrackingNumber"></shipping_number>
	<conversion_rate required="true" format="isFloat"></conversion_rate>
	<reference></reference>
<associations>
<order_rows virtual_entity="true" node_type="order_row">
	<order_row>
	<id></id>
	<product_id required="true"></product_id>
	<product_attribute_id required="true"></product_attribute_id>
	<product_quantity required="true"></product_quantity>
	<product_name></product_name>
	<product_price></product_price>
	</order_row>
</order_rows>
</associations>
</order>
</prestashop>
 */
	@XmlPath("order/id/text()")
	private Integer id;
	
	@XmlPath("order/id_address_delivery/text()")
	private Integer idAddressDelivry; //<id_address_delivery required="true" format="isUnsignedId"></id_address_delivery>
	
	@XmlPath("order/id_address_invoice/text()")
	private Integer idAddressInvoice; //<id_address_invoice required="true" format="isUnsignedId"></id_address_invoice>
	
	@XmlPath("order/id_cart/text()")
	private Integer idCart; //<id_cart required="true" format="isUnsignedId"></id_cart>
	
	@XmlPath("order/id_currency/text()")
	private Integer idCurrency; //<id_currency required="true" format="isUnsignedId"></id_currency>
	
	@XmlPath("order/id_lang/text()")
	private Integer idLang; //<id_lang required="true" format="isUnsignedId"></id_lang>
	
	@XmlPath("order/id_customer/text()")
	private Integer idCustomer; //<id_customer required="true" format="isUnsignedId"></id_customer>
	
	@XmlPath("order/id_carrier/text()")
	private Integer idCarrier; //<id_carrier required="true" format="isUnsignedId"></id_carrier>
	
	@XmlPath("order/current_state/text()")
	private Integer currentState; //<current_state format="isUnsignedId"></current_state>
	
	@XmlPath("order/module/text()")
	private String module; //<module></module>
	
	@XmlPath("order/invoice_number/text()")
	private String invoiceNumber; //<invoice_number></invoice_number>
	
	@XmlPath("order/invoice_date/text()")
	private String invoiceDate; //<invoice_date></invoice_date>
	
	@XmlPath("order/delivery_number/text()")
	private String deliveryNumber; //<delivery_number></delivery_number>
	
	@XmlPath("order/delivery_date/text()")
	private String deliveryDate; //<delivery_date></delivery_date>
	
	@XmlPath("order/valid/text()")
	private String valid; //<valid></valid>
	
	@XmlPath("order/date_add/text()")
	@XmlJavaTypeAdapter( DateAdapter.class )
	private Date dateAdd; //<date_add format="isDate"></date_add>
	
	@XmlPath("order/date_upd/text()")
	@XmlJavaTypeAdapter( DateAdapter.class )
	private Date dateUpd; //<date_upd format="isDate"></date_upd>
	
	@XmlPath("order/id_shop_group/text()")
	private Integer idShopGroup; //<id_shop_group format="isUnsignedId"></id_shop_group>
	
	@XmlPath("order/id_shop/text()")
	private Integer idShop; //<id_shop format="isUnsignedId"></id_shop>
	
	@XmlPath("order/secure_key/text()")
	private String secureKey; //<secure_key format="isMd5"></secure_key>
	
	@XmlPath("order/payment/text()")
	private String payment; //<payment required="true" format="isGenericName"></payment>
	
	@XmlPath("order/recyclable/text()")
	@XmlJavaTypeAdapter( BooleanAdapter.class )
	private Boolean recyclable; //<recyclable format="isBool"></recyclable>
	
	@XmlPath("order/gift/text()")
	@XmlJavaTypeAdapter( BooleanAdapter.class )
	private Boolean gift; //<gift format="isBool"></gift>
	
	@XmlPath("order/gift_message/text()")
	private String giftMessage; //<gift_message format="isMessage"></gift_message>
	
	@XmlPath("order/mobile_theme/text()")
	@XmlJavaTypeAdapter( BooleanAdapter.class )
	private Boolean mobile_theme; //<mobile_theme format="isBool"></mobile_theme>
	
	@XmlPath("order/total_discounts/text()")
	private Float totalDiscounts; //<total_discounts format="isPrice"></total_discounts>
	
	@XmlPath("order/total_discounts_tax_incl/text()")
	private Float totalDiscountsTaxIncl; //<total_discounts_tax_incl format="isPrice"></total_discounts_tax_incl>
	
	@XmlPath("order/total_discounts_tax_excl/text()")
	private Float totalDiscountsTaxExcl; //<total_discounts_tax_excl format="isPrice"></total_discounts_tax_excl>
	
	@XmlPath("order/total_paid/text()")
	private Float totalPaid; //<total_paid required="true" format="isPrice"></total_paid>
	
	@XmlPath("order/total_paid_tax_incl/text()")
	private Float totalPaidTaxIncl; //<total_paid_tax_incl format="isPrice"></total_paid_tax_incl>
	
	@XmlPath("order/total_paid_tax_excl/text()")
	private Float totalPaidTaxExcl; //<total_paid_tax_excl format="isPrice"></total_paid_tax_excl>
	
	@XmlPath("order/total_paid_real/text()")
	private Float totalPaidReal; //<total_paid_real required="true" format="isPrice"></total_paid_real>
	
	@XmlPath("order/total_products/text()")
	private Float totalProducts; //<total_products required="true" format="isPrice"></total_products>
	
	@XmlPath("order/total_products_wt/text()")
	private Float totalProductsWt; //<total_products_wt required="true" format="isPrice"></total_products_wt>
	
	@XmlPath("order/total_shipping/text()")
	private Float totalShipping; //<total_shipping format="isPrice"></total_shipping>
	
	@XmlPath("order/total_shipping_tax_incl/text()")
	private Float totalShippingTaxIncl; //<total_shipping_tax_incl format="isPrice"></total_shipping_tax_incl>
	
	@XmlPath("order/total_shipping_tax_excl/text()")
	private Float totalShippingTaxExcl; //<total_shipping_tax_excl format="isPrice"></total_shipping_tax_excl>
	
	@XmlPath("order/carrier_tax_rate/text()")
	private Float carrierTaxRate; //<carrier_tax_rate format="isFloat"></carrier_tax_rate>
	
	@XmlPath("order/total_wrapping/text()")
	private Float totalWrapping; //<total_wrapping format="isPrice"></total_wrapping>
	
	@XmlPath("order/total_wrapping_tax_incl/text()")
	private Float totalWrappingTaxIncl; //<total_wrapping_tax_incl format="isPrice"></total_wrapping_tax_incl>
	
	@XmlPath("order/total_wrapping_tax_excl/text()")
	private Float totalWrappingTaxExcl; //<total_wrapping_tax_excl format="isPrice"></total_wrapping_tax_excl>
	
	@XmlPath("order/shipping_number/text()")
	private String shippingNumber; //<shipping_number format="isTrackingNumber"></shipping_number>
	
	@XmlPath("order/conversion_rate/text()")
	private Float conversionRate; //<conversion_rate required="true" format="isFloat"></conversion_rate>
	
	@XmlPath("order/reference/text()")
	private String reference; //<reference></reference>
	
	@XmlPath("order/associations")
	private OrderRowAssociation associations = null;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdAddressDelivry() {
		return idAddressDelivry;
	}

	public void setIdAddressDelivry(Integer idAddressDelivry) {
		this.idAddressDelivry = idAddressDelivry;
	}

	public Integer getIdAddressInvoice() {
		return idAddressInvoice;
	}

	public void setIdAddressInvoice(Integer idAddressInvoice) {
		this.idAddressInvoice = idAddressInvoice;
	}

	public Integer getIdCart() {
		return idCart;
	}

	public void setIdCart(Integer idCart) {
		this.idCart = idCart;
	}

	public Integer getIdCurrency() {
		return idCurrency;
	}

	public void setIdCurrency(Integer idCurrency) {
		this.idCurrency = idCurrency;
	}

	public Integer getIdLang() {
		return idLang;
	}

	public void setIdLang(Integer idLang) {
		this.idLang = idLang;
	}

	public Integer getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(Integer idCustomer) {
		this.idCustomer = idCustomer;
	}

	public Integer getIdCarrier() {
		return idCarrier;
	}

	public void setIdCarrier(Integer idCarrier) {
		this.idCarrier = idCarrier;
	}

	public Integer getCurrentState() {
		return currentState;
	}

	public void setCurrentState(Integer currentState) {
		this.currentState = currentState;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getDeliveryNumber() {
		return deliveryNumber;
	}

	public void setDeliveryNumber(String deliveryNumber) {
		this.deliveryNumber = deliveryNumber;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public Date getDateAdd() {
		return dateAdd;
	}

	public void setDateAdd(Date dateAdd) {
		this.dateAdd = dateAdd;
	}

	public Date getDateUpd() {
		return dateUpd;
	}

	public void setDateUpd(Date dateUpd) {
		this.dateUpd = dateUpd;
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

	public String getSecureKey() {
		return secureKey;
	}

	public void setSecureKey(String secureKey) {
		this.secureKey = secureKey;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public Boolean getRecyclable() {
		return recyclable;
	}

	public void setRecyclable(Boolean recyclable) {
		this.recyclable = recyclable;
	}

	public Boolean getGift() {
		return gift;
	}

	public void setGift(Boolean gift) {
		this.gift = gift;
	}

	public String getGiftMessage() {
		return giftMessage;
	}

	public void setGiftMessage(String giftMessage) {
		this.giftMessage = giftMessage;
	}

	public Boolean getMobile_theme() {
		return mobile_theme;
	}

	public void setMobile_theme(Boolean mobile_theme) {
		this.mobile_theme = mobile_theme;
	}

	public Float getTotalDiscounts() {
		return totalDiscounts;
	}

	public void setTotalDiscounts(Float totalDiscounts) {
		this.totalDiscounts = totalDiscounts;
	}

	public Float getTotalDiscountsTaxIncl() {
		return totalDiscountsTaxIncl;
	}

	public void setTotalDiscountsTaxIncl(Float totalDiscountsTaxIncl) {
		this.totalDiscountsTaxIncl = totalDiscountsTaxIncl;
	}

	public Float getTotalDiscountsTaxExcl() {
		return totalDiscountsTaxExcl;
	}

	public void setTotalDiscountsTaxExcl(Float totalDiscountsTaxExcl) {
		this.totalDiscountsTaxExcl = totalDiscountsTaxExcl;
	}

	public Float getTotalPaid() {
		return totalPaid;
	}

	public void setTotalPaid(Float totalPaid) {
		this.totalPaid = totalPaid;
	}

	public Float getTotalPaidTaxIncl() {
		return totalPaidTaxIncl;
	}

	public void setTotalPaidTaxIncl(Float totalPaidTaxIncl) {
		this.totalPaidTaxIncl = totalPaidTaxIncl;
	}

	public Float getTotalPaidTaxExcl() {
		return totalPaidTaxExcl;
	}

	public void setTotalPaidTaxExcl(Float totalPaidTaxExcl) {
		this.totalPaidTaxExcl = totalPaidTaxExcl;
	}

	public Float getTotalPaidReal() {
		return totalPaidReal;
	}

	public void setTotalPaidReal(Float totalPaidReal) {
		this.totalPaidReal = totalPaidReal;
	}

	public Float getTotalProducts() {
		return totalProducts;
	}

	public void setTotalProducts(Float totalProducts) {
		this.totalProducts = totalProducts;
	}

	public Float getTotalProductsWt() {
		return totalProductsWt;
	}

	public void setTotalProductsWt(Float totalProductsWt) {
		this.totalProductsWt = totalProductsWt;
	}

	public Float getTotalShipping() {
		return totalShipping;
	}

	public void setTotalShipping(Float totalShipping) {
		this.totalShipping = totalShipping;
	}

	public Float getTotalShippingTaxIncl() {
		return totalShippingTaxIncl;
	}

	public void setTotalShippingTaxIncl(Float totalShippingTaxIncl) {
		this.totalShippingTaxIncl = totalShippingTaxIncl;
	}

	public Float getTotalShippingTaxExcl() {
		return totalShippingTaxExcl;
	}

	public void setTotalShippingTaxExcl(Float totalShippingTaxExcl) {
		this.totalShippingTaxExcl = totalShippingTaxExcl;
	}

	public Float getCarrierTaxRate() {
		return carrierTaxRate;
	}

	public void setCarrierTaxRate(Float carrierTaxRate) {
		this.carrierTaxRate = carrierTaxRate;
	}

	public Float getTotalWrapping() {
		return totalWrapping;
	}

	public void setTotalWrapping(Float totalWrapping) {
		this.totalWrapping = totalWrapping;
	}

	public Float getTotalWrappingTaxIncl() {
		return totalWrappingTaxIncl;
	}

	public void setTotalWrappingTaxIncl(Float totalWrappingTaxIncl) {
		this.totalWrappingTaxIncl = totalWrappingTaxIncl;
	}

	public Float getTotalWrappingTaxExcl() {
		return totalWrappingTaxExcl;
	}

	public void setTotalWrappingTaxExcl(Float totalWrappingTaxExcl) {
		this.totalWrappingTaxExcl = totalWrappingTaxExcl;
	}

	public String getShippingNumber() {
		return shippingNumber;
	}

	public void setShippingNumber(String shippingNumber) {
		this.shippingNumber = shippingNumber;
	}

	public Float getConversionRate() {
		return conversionRate;
	}

	public void setConversionRate(Float conversionRate) {
		this.conversionRate = conversionRate;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public OrderRowAssociation getAssociations() {
		return associations;
	}

	public void setAssociations(OrderRowAssociation associations) {
		this.associations = associations;
	}
	
}

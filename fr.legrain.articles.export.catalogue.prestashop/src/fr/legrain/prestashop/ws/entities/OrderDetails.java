package fr.legrain.prestashop.ws.entities;

/**
 * Details of an order
 * @author nicolas
 *
 */
public class OrderDetails {
/*
 <?xml version="1.0" encoding="UTF-8"?>
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<order_detail>
<id_order></id_order>
<product_id></product_id>
<product_attribute_id></product_attribute_id>
<product_quantity_reinjected></product_quantity_reinjected>
<group_reduction></group_reduction>
<discount_quantity_applied></discount_quantity_applied>
<download_hash></download_hash>
<download_deadline></download_deadline>
<product_name></product_name>
<product_quantity></product_quantity>
<product_quantity_in_stock></product_quantity_in_stock>
<product_quantity_return></product_quantity_return>
<product_quantity_refunded></product_quantity_refunded>
<product_price></product_price>

<reduction_percent></reduction_percent>
<reduction_amount></reduction_amount>
<product_quantity_discount></product_quantity_discount>
<product_ean13></product_ean13>
<product_upc></product_upc>
<product_reference></product_reference>
<product_supplier_reference></product_supplier_reference>
<product_weight></product_weight>
<tax_name></tax_name>
<tax_rate></tax_rate>
<ecotax></ecotax>
<ecotax_tax_rate></ecotax_tax_rate>
<download_nb></download_nb>
</order_detail>
</prestashop>

 */
	
/*
 <?xml version="1.0" encoding="UTF-8"?>
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<order_detail>
<id_order xlink:href="http://promethee.biz/prestaEb/api/orders/" required="true" format="isUnsignedId"></id_order>
<product_id xlink:href="http://promethee.biz/prestaEb/api/products/" format="isUnsignedId"></product_id>
<product_attribute_id xlink:href="http://promethee.biz/prestaEb/api/product_attributes/" format="isUnsignedId"></product_attribute_id>
<product_quantity_reinjected format="isUnsignedInt"></product_quantity_reinjected>
<group_reduction format="isFloat"></group_reduction>
<discount_quantity_applied></discount_quantity_applied>
<download_hash></download_hash>
<download_deadline></download_deadline>
<product_name required="true" format="isGenericName"></product_name>
<product_quantity required="true" format="isInt"></product_quantity>
<product_quantity_in_stock format="isInt"></product_quantity_in_stock>
<product_quantity_return format="isUnsignedInt"></product_quantity_return>
<product_quantity_refunded format="isUnsignedInt"></product_quantity_refunded>
<product_price required="true" format="isPrice"></product_price>
<reduction_percent format="isFloat"></reduction_percent>
<reduction_amount format="isPrice"></reduction_amount>
<product_quantity_discount format="isFloat"></product_quantity_discount>
<product_ean13 format="isEan13"></product_ean13>
<product_upc format="isUpc"></product_upc>
<product_reference format="isReference"></product_reference>
<product_supplier_reference format="isReference"></product_supplier_reference>
<product_weight format="isFloat"></product_weight>
<tax_name format="isGenericName"></tax_name>
<tax_rate required="true" format="isFloat"></tax_rate>
<ecotax format="isFloat"></ecotax>
<ecotax_tax_rate format="isFloat"></ecotax_tax_rate>
<download_nb format="isInt"></download_nb>
</order_detail>
</prestashop>
 */
	
	private int idOrder;
	private int productId;
	private int productAttributeId;
	private int productQuantityReinjected;
	private float groupRedirection;
	private int discountQuantityApplied;
	private String downloadHash;
	private String downloadDeadline;
	private String productName;
	private int productQuantity;
	private int productQuantityInStock;
	private int productQuantityReturn;
	private int productQuantityRefunded;
	private float productPrice;
	private float reductionPercent;
	private float productQuantityDiscount;
	private String productEan13;
	private String productUpc;
	private String productReference;
	private String productSupplier;
	private float productWeight;
	private String taxName;
	private float taxRate;
	private float ecotax;
	private float ecotaxTaxRate;
	private int downloadNb;
}

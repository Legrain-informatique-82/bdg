package fr.legrain.prestashop.ws.entities;

/**
 * Stock movements
 * @author nicolas
 *
 */
public class StockMovements {
/*
 <?xml version="1.0" encoding="UTF-8"?>
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<stock_mvt>
<id_product></id_product>
<id_product_attribute></id_product_attribute>
<id_order></id_order>
<id_employee></id_employee>
<quantity></quantity>
<id_stock_mvt_reason></id_stock_mvt_reason>
</stock_mvt>
</prestashop>
 */
	
/*
<?xml version="1.0" encoding="UTF-8"?>
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<stock_mvt>
<id_product required="true" format="isUnsignedId"></id_product>
<id_product_attribute format="isUnsignedId"></id_product_attribute>
<id_order format="isUnsignedId"></id_order>
<id_employee format="isUnsignedId"></id_employee>
<quantity required="true" format="isInt"></quantity>
<id_stock_mvt_reason required="true" format="isUnsignedId"></id_stock_mvt_reason>
</stock_mvt>
</prestashop>
 */
	
	private int idProduct;
	private int idProductAttribute;
	private int idOrder;
	private int idEmployee;
	private int quantity;
	private int idStockMvtReason;
}

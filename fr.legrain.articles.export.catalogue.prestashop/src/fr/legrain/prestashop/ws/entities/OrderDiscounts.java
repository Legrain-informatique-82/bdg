package fr.legrain.prestashop.ws.entities;

/**
 * Discounts of an order
 * @author nicolas
 *
 */
public class OrderDiscounts {
/*
 <?xml version="1.0" encoding="UTF-8"?>
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<order_discount>
<id_order></id_order>
<name></name>
<value></value>
</order_discount>
</prestashop>

 */
	
/*
 <?xml version="1.0" encoding="UTF-8"?>
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<order_discount>
<id_order xlink:href="http://promethee.biz/prestaEb/api/orders/" required="true" format="isUnsignedId"></id_order>
<name required="true" format="isGenericName"></name>
<value required="true" format="isInt"></value>
</order_discount>
</prestashop>

 */
	
	private int idOrder;
	private String name;
	private int value;
}

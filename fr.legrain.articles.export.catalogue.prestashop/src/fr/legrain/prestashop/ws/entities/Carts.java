package fr.legrain.prestashop.ws.entities;

/**
 * Customer's carts
 * @author nicolas
 *
 */
public class Carts {
/*
 id_product^Arrayid_product_attribute^Array<?xml version="1.0" encoding="UTF-8"?>
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<errors>
<error><![CDATA[Internal error]]></error>
<error><![CDATA[Internal error]]></error>
</errors>
</prestashop>
 */

/*
 id_product^Arrayid_product_attribute^Array<?xml version="1.0" encoding="UTF-8"?>
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<errors>
<error><![CDATA[Internal error]]></error>
<error><![CDATA[Internal error]]></error>
</errors>
</prestashop>
 */
	
/*
<?xml version="1.0" encoding="UTF-8"?>
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<cart>
<id><![CDATA[1]]></id>
<id_address_delivery xlink:href="http://promethee.biz/prestaEb/api/addresses/6"><![CDATA[6]]></id_address_delivery>
<id_address_invoice xlink:href="http://promethee.biz/prestaEb/api/addresses/6"><![CDATA[6]]></id_address_invoice>
<id_currency xlink:href="http://promethee.biz/prestaEb/api/currencies/1"><![CDATA[1]]></id_currency>
<id_customer xlink:href="http://promethee.biz/prestaEb/api/customers/1"><![CDATA[1]]></id_customer>
<id_guest xlink:href="http://promethee.biz/prestaEb/api/guests/1"><![CDATA[1]]></id_guest>
<id_lang xlink:href="http://promethee.biz/prestaEb/api/languages/2"><![CDATA[2]]></id_lang>
<id_carrier xlink:href="http://promethee.biz/prestaEb/api/carriers/2"><![CDATA[2]]></id_carrier>
<recyclable><![CDATA[1]]></recyclable>
<gift><![CDATA[0]]></gift>
<gift_message><![CDATA[]]></gift_message>
<associations>
<cart_rows node_type="cart_row">
<cart_row>
<id_product><![CDATA[7]]></id_product>
<id_product_attribute><![CDATA[23]]></id_product_attribute>
<quantity><![CDATA[1]]></quantity>
</cart_row>
<cart_row>
<id_product><![CDATA[9]]></id_product>
<id_product_attribute><![CDATA[0]]></id_product_attribute>
<quantity><![CDATA[1]]></quantity>
</cart_row>
</cart_rows>
</associations>
</cart>
</prestashop>
 */
	private int id;
	private int idAdrressDelivery;
	private int idAdresseInvoice;
	private int idCurrency;
	private int idCustomer;
	private int idGuest;
	private int idLang;
	private int idCarrier;
	private boolean recyclable;
	private boolean gift;
	private String giftMessage;
	//association cartRow (idProduct, idProductAttribute,quantity)
}

package fr.legrain.prestashop.ws.entities;

/**
 * Price ranges
 * @author nicolas
 *
 */
public class PriceRanges {
/*
 <?xml version="1.0" encoding="UTF-8"?>
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<range_price>
<id_carrier></id_carrier>
<delimiter1></delimiter1>
<delimiter2></delimiter2>
</range_price>
</prestashop>

 */
	
/*
 <?xml version="1.0" encoding="UTF-8"?>
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<range_price>
<id_carrier xlink:href="http://promethee.biz/prestaEb/api/carriers/" required="true" format="isInt"></id_carrier>
<delimiter1 required="true" format="isFloat"></delimiter1>
<delimiter2 required="true" format="isFloat"></delimiter2>
</range_price>
</prestashop>
 */
	
	private int idCarrier;
	private float delimiter1;
	private float delimiter2;
}

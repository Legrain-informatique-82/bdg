package fr.legrain.prestashop.ws.entities;

/**
 * Product delivery
 * @author nicolas
 *
 */
public class Deliveries {
/*
 <?xml version="1.0" encoding="UTF-8"?>
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<delivery>
<id_carrier></id_carrier>
<id_range_price></id_range_price>
<id_range_weight></id_range_weight>
<id_zone></id_zone>
<price></price>
</delivery>
</prestashop>
 */
	
/*
 <?xml version="1.0" encoding="UTF-8"?>
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<delivery>
<id_carrier xlink:href="http://promethee.biz/prestaEb/api/carriers/" required="true" format="isUnsignedId"></id_carrier>
<id_range_price xlink:href="http://promethee.biz/prestaEb/api/priceranges/" required="true" format="isUnsignedId"></id_range_price>
<id_range_weight xlink:href="http://promethee.biz/prestaEb/api/weightranges/" required="true" format="isUnsignedId"></id_range_weight>
<id_zone xlink:href="http://promethee.biz/prestaEb/api/zones/" required="true" format="isUnsignedId"></id_zone>
<price required="true" format="isPrice"></price>
</delivery>
</prestashop>

 */
	
	private int idCarrier;
	private int idRangePrice;
	private int idRandeWeight;
	private int idZone;
	private float price;
}

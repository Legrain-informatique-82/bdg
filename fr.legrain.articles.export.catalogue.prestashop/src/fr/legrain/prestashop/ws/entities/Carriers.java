package fr.legrain.prestashop.ws.entities;

/**
 * The Carriers
 * @author nicolas
 *
 */
public class Carriers {
/*
 <?xml version="1.0" encoding="UTF-8"?>
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<carrier>
<id_tax_rules_group></id_tax_rules_group>
<deleted></deleted>
<is_module></is_module>
<name></name>
<active></active>
<url></url>
<shipping_handling></shipping_handling>
<range_behavior></range_behavior>
<shipping_method></shipping_method>
<delay>
<language id="1" ></language>
<language id="2" ></language>
<language id="3" ></language>
<language id="4" ></language>
<language id="5" ></language>
</delay>
</carrier>
</prestashop>
 */
	
/*
 <?xml version="1.0" encoding="UTF-8"?>
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<carrier>
<id_tax_rules_group format="isInt"></id_tax_rules_group>
<deleted></deleted>
<is_module></is_module>
<name required="true" maxSize="64" format="isCarrierName"></name>
<active required="true" format="isBool"></active>
<url format="isAbsoluteUrl"></url>
<shipping_handling format="isBool"></shipping_handling>
<range_behavior format="isBool"></range_behavior>
<shipping_method format="isUnsignedInt"></shipping_method>
<delay required="true" maxSize="128" format="isGenericName" >
<language id="1" format="isUnsignedId" xlink:href="http://promethee.biz/prestaEb/api/languages/1"></language>
<language id="2" format="isUnsignedId" xlink:href="http://promethee.biz/prestaEb/api/languages/2"></language>
<language id="3" format="isUnsignedId" xlink:href="http://promethee.biz/prestaEb/api/languages/3"></language>
<language id="4" format="isUnsignedId" xlink:href="http://promethee.biz/prestaEb/api/languages/4"></language>
<language id="5" format="isUnsignedId" xlink:href="http://promethee.biz/prestaEb/api/languages/5"></language>
</delay>
</carrier>
</prestashop>
 */

	private int idTaxRulesGroup;
	private boolean deleted;
	private boolean isModule;
	private String name;
	private boolean active;
	private String url;
	private boolean shippingHandling;
	private boolean rangeBehavior;
	private String shippingMethodFormat;
	private String delay; // ????
}

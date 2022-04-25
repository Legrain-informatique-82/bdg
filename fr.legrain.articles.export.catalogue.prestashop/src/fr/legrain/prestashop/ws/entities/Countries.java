package fr.legrain.prestashop.ws.entities;

/**
 * The countries
 * @author nicolas
 *
 */
public class Countries {
/*
<?xml version="1.0" encoding="UTF-8"?>
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<country>
<id_zone></id_zone>
<id_currency></id_currency>
<iso_code></iso_code>
<call_prefix></call_prefix>
<active></active>
<contains_states></contains_states>
<need_identification_number></need_identification_number>
<need_zip_code></need_zip_code>
<zip_code_format></zip_code_format>
<display_tax_label></display_tax_label>
<name>
<language id="1" ></language>
<language id="2" ></language>
<language id="3" ></language>

<language id="4" ></language>
<language id="5" ></language>
</name>
</country>
</prestashop>
 */
	
/*
 <?xml version="1.0" encoding="UTF-8"?>
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<country>
<id_zone xlink:href="http://promethee.biz/prestaEb/api/zones/" required="true" format="isUnsignedId"></id_zone>
<id_currency xlink:href="http://promethee.biz/prestaEb/api/currencies/" required="true" format="isUnsignedId"></id_currency>
<iso_code required="true" maxSize="3" format="isLanguageIsoCode"></iso_code>
<call_prefix format="isInt"></call_prefix>
<active format="isBool"></active>
<contains_states required="true" format="isBool"></contains_states>
<need_identification_number required="true" format="isBool"></need_identification_number>
<need_zip_code format="isBool"></need_zip_code>
<zip_code_format format="isZipCodeFormat"></zip_code_format>
<display_tax_label required="true" format="isBool"></display_tax_label>
<name required="true" maxSize="64" format="isGenericName" >
<language id="1" format="isUnsignedId" xlink:href="http://promethee.biz/prestaEb/api/languages/1"></language>
<language id="2" format="isUnsignedId" xlink:href="http://promethee.biz/prestaEb/api/languages/2"></language>
<language id="3" format="isUnsignedId" xlink:href="http://promethee.biz/prestaEb/api/languages/3"></language>

<language id="4" format="isUnsignedId" xlink:href="http://promethee.biz/prestaEb/api/languages/4"></language>
<language id="5" format="isUnsignedId" xlink:href="http://promethee.biz/prestaEb/api/languages/5"></language>
</name>
</country>
</prestashop>

 */
	private int idZone;
	private int idCurrency;
	private String isoCode;
	private int callPrefix;
	private boolean active;
	private boolean containsStates;
	private boolean needIdentificationNumber;
	private boolean needZipCode;
	private String zipCodeFormat;
	private boolean displayTaxLabel;
	private String name; // ???
}

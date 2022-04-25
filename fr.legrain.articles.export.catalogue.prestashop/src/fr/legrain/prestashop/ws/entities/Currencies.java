package fr.legrain.prestashop.ws.entities;

/**
 * The currencies
 * @author nicolas
 *
 */
public class Currencies {
/*
 <?xml version="1.0" encoding="UTF-8"?>
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<currency>
<name></name>
<iso_code></iso_code>
<iso_code_num></iso_code_num>
<sign></sign>
<blank></blank>
<format></format>
<decimals></decimals>
<conversion_rate></conversion_rate>
<deleted></deleted>
<active></active>
</currency>
</prestashop>

 */
	
/*
 <?xml version="1.0" encoding="UTF-8"?>
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<currency>
<name required="true" maxSize="32" format="isGenericName"></name>
<iso_code required="true" maxSize="3" format="isLanguageIsoCode"></iso_code>
<iso_code_num maxSize="3" format="isNumericIsoCode"></iso_code_num>
<sign required="true" maxSize="8" format="isGenericName"></sign>
<blank format="isInt"></blank>
<format required="true" format="isUnsignedId"></format>
<decimals required="true" format="isBool"></decimals>
<conversion_rate required="true" format="isFloat"></conversion_rate>
<deleted format="isBool"></deleted>
<active format="isBool"></active>
</currency>
</prestashop>

 */
	
	private String name;
	private String isoCode;
	private String isoCodeNum;
	private String sign;
	private int blank;
	private int format;
	private boolean decimals;
	private float convertionRate;
	private boolean deleted;
	private boolean active;
}

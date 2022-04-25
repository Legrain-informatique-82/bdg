package fr.legrain.prestashop.ws.entities;

/**
 * The stores
 * @author nicolas
 *
 */
public class Stores {
/*
 <?xml version="1.0" encoding="UTF-8"?>
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<store>
<id_country></id_country>
<id_state></id_state>
<name></name>
<address1></address1>
<address2></address2>
<postcode></postcode>
<city></city>
<latitude></latitude>
<longitude></longitude>
<hours></hours>
<phone></phone>
<fax></fax>
<email></email>
<note></note>
<active></active>
</store>
</prestashop>
 */
	
/*
 <?xml version="1.0" encoding="UTF-8"?>
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<store>
<id_country xlink:href="http://promethee.biz/prestaEb/api/countries/" required="true" format="isUnsignedId"></id_country>
<id_state xlink:href="http://promethee.biz/prestaEb/api/states/" format="isNullOrUnsignedId"></id_state>
<name required="true" maxSize="128" format="isGenericName"></name>
<address1 required="true" maxSize="128" format="isAddress"></address1>
<address2 maxSize="128" format="isAddress"></address2>
<postcode maxSize="12"></postcode>
<city required="true" maxSize="64" format="isCityName"></city>
<latitude maxSize="10" format="isCoordinate"></latitude>
<longitude maxSize="10" format="isCoordinate"></longitude>
<hours maxSize="254" format="isSerializedArray"></hours>
<phone maxSize="16" format="isPhoneNumber"></phone>
<fax maxSize="16" format="isPhoneNumber"></fax>
<email maxSize="128" format="isEmail"></email>
<note maxSize="65000" format="isCleanHtml"></note>
<active required="true" format="isBool"></active>
</store>
</prestashop>
 */
	private int idcountry;
	private int idState;
	private String name;
	private String adress1;
	private String adress2;
	private String postcode;
	private String city;
	private String latitude;
	private String longitude;
	private String hours;
	private String phone;
	private String fax;
	private String email;
	private String note;
	private boolean active;
	
}

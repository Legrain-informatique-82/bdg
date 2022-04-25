package fr.legrain.prestashop.ws.entities;

/**
 * The Order states
 * @author nicolas
 *
 */
public class OrderStates {
/*
<?xml version="1.0" encoding="UTF-8"?>
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<order_state>
<unremovable></unremovable>
<delivery></delivery>
<hidden></hidden>
<send_email></send_email>
<invoice></invoice>
<color></color>
<logable></logable>
<name>
<language id="1" ></language>
<language id="2" ></language>
<language id="3" ></language>
<language id="4" ></language>
<language id="5" ></language>
</name>

<template>
<language id="1" ></language>
<language id="2" ></language>
<language id="3" ></language>
<language id="4" ></language>
<language id="5" ></language>
</template>
</order_state>
</prestashop>

 */
	
/*
 <?xml version="1.0" encoding="UTF-8"?>
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<order_state>
<unremovable></unremovable>
<delivery></delivery>
<hidden></hidden>
<send_email format="isBool"></send_email>
<invoice format="isBool"></invoice>
<color format="isColor"></color>
<logable format="isBool"></logable>
<name required="true" maxSize="64" format="isGenericName" >
<language id="1" format="isUnsignedId" xlink:href="http://promethee.biz/prestaEb/api/languages/1"></language>
<language id="2" format="isUnsignedId" xlink:href="http://promethee.biz/prestaEb/api/languages/2"></language>
<language id="3" format="isUnsignedId" xlink:href="http://promethee.biz/prestaEb/api/languages/3"></language>
<language id="4" format="isUnsignedId" xlink:href="http://promethee.biz/prestaEb/api/languages/4"></language>
<language id="5" format="isUnsignedId" xlink:href="http://promethee.biz/prestaEb/api/languages/5"></language>
</name>
<template maxSize="64" format="isTplName" >
<language id="1" format="isUnsignedId" xlink:href="http://promethee.biz/prestaEb/api/languages/1"></language>
<language id="2" format="isUnsignedId" xlink:href="http://promethee.biz/prestaEb/api/languages/2"></language>
<language id="3" format="isUnsignedId" xlink:href="http://promethee.biz/prestaEb/api/languages/3"></language>
<language id="4" format="isUnsignedId" xlink:href="http://promethee.biz/prestaEb/api/languages/4"></language>
<language id="5" format="isUnsignedId" xlink:href="http://promethee.biz/prestaEb/api/languages/5"></language>
</template>
</order_state>
</prestashop>
 */
	
	private String unremovable;
	private String delivry;
	private String hidden;
	private boolean sendMail;
	private boolean invoice;
	private boolean color;
	private boolean logable;
	private String name; // ???
	private String template; // ???
}

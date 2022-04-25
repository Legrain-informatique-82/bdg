package fr.legrain.prestashop.ws.entities;

import java.util.Date;

/**
 * The Employees
 * @author nicolas
 *
 */
public class Employees {
/*
 <?xml version="1.0" encoding="UTF-8"?>
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<employee>
<id_lang></id_lang>
<last_passwd_gen></last_passwd_gen>
<stats_date_from></stats_date_from>
<stats_date_to></stats_date_to>
<lastname></lastname>
<firstname></firstname>
<email></email>
<passwd></passwd>
<bo_color></bo_color>
<bo_theme></bo_theme>
<active></active>
<id_profile></id_profile>
<bo_uimode></bo_uimode>
</employee>

</prestashop>

 */
	
/*
 <?xml version="1.0" encoding="UTF-8"?>
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<employee>
<id_lang xlink:href="http://promethee.biz/prestaEb/api/languages/" required="true" format="isUnsignedInt"></id_lang>
<last_passwd_gen></last_passwd_gen>
<stats_date_from></stats_date_from>
<stats_date_to></stats_date_to>
<lastname required="true" maxSize="32" format="isName"></lastname>
<firstname required="true" maxSize="32" format="isName"></firstname>
<email required="true" maxSize="128" format="isEmail"></email>
<passwd required="true" maxSize="32" format="isPasswdAdmin"></passwd>
<bo_color maxSize="32" format="isColor"></bo_color>
<bo_theme maxSize="32" format="isGenericName"></bo_theme>
<active format="isBool"></active>
<id_profile required="true" format="isInt"></id_profile>
<bo_uimode format="isGenericName"></bo_uimode>
</employee>

</prestashop>

 */
	
	private int idLang;
	private String lastPasswdGen;
	private Date statsDateFrom;
	private Date statsDateTo;
	private String lastname;
	private String firstname;
	private String email;
	private String passwd;
	private String boColor;
	private String boTheme;
	private boolean active;
	private int idProfile;
	private String boUimode;
	
}

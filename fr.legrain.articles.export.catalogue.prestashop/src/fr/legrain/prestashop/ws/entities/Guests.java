package fr.legrain.prestashop.ws.entities;

/**
 * The guests
 * @author nicolas
 *
 */
public class Guests {
/*
 <?xml version="1.0" encoding="UTF-8"?>
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<guest>
<id_customer></id_customer>
<accept_language></accept_language>
<id_operating_system></id_operating_system>
<id_web_browser></id_web_browser>
<javascript></javascript>
<screen_resolution_x></screen_resolution_x>
<screen_resolution_y></screen_resolution_y>
<screen_color></screen_color>
<sun_java></sun_java>
<adobe_flash></adobe_flash>
<adobe_director></adobe_director>
<apple_quicktime></apple_quicktime>
<real_player></real_player>
<windows_media></windows_media>

</guest>
</prestashop>
 */
	
/*
<?xml version="1.0" encoding="UTF-8"?>
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<guest>
<id_customer xlink:href="http://promethee.biz/prestaEb/api/customers/" format="isUnsignedId"></id_customer>
<accept_language maxSize="8" format="isGenericName"></accept_language>
<id_operating_system format="isUnsignedId"></id_operating_system>
<id_web_browser format="isUnsignedId"></id_web_browser>
<javascript format="isBool"></javascript>
<screen_resolution_x format="isInt"></screen_resolution_x>
<screen_resolution_y format="isInt"></screen_resolution_y>
<screen_color format="isInt"></screen_color>
<sun_java format="isBool"></sun_java>
<adobe_flash format="isBool"></adobe_flash>
<adobe_director format="isBool"></adobe_director>
<apple_quicktime format="isBool"></apple_quicktime>
<real_player format="isBool"></real_player>
<windows_media format="isBool"></windows_media>

</guest>
</prestashop>

 */
	
	private int idCustomer;
	private String acceptLanguage;
	private int idOperatingSystem;
	private int idBrowser;
	private boolean javascript;
	private int screenResolutionX;
	private int screenResolutionY;
	private int screenColor;
	private boolean sunJava;
	private boolean adobeFlash;
	private boolean adobeDirector;
	private boolean appleQuicktime;
	private boolean realPlayer;
	private boolean windiwsMedia;
}

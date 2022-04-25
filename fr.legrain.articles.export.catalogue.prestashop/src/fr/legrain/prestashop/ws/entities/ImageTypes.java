package fr.legrain.prestashop.ws.entities;

/**
 * The image types
 * @author nicolas
 *
 */
public class ImageTypes {
/*
<?xml version="1.0" encoding="UTF-8"?>
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<image_type>
<name></name>
<width></width>
<height></height>
<categories></categories>
<products></products>
<manufacturers></manufacturers>
<suppliers></suppliers>
<scenes></scenes>
<stores></stores>
</image_type>
</prestashop>

 */
	
/*
 <?xml version="1.0" encoding="UTF-8"?>
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<image_type>
<name required="true" maxSize="16" format="isImageTypeName"></name>
<width required="true" format="isImageSize"></width>
<height required="true" format="isImageSize"></height>
<categories format="isBool"></categories>
<products format="isBool"></products>
<manufacturers format="isBool"></manufacturers>
<suppliers format="isBool"></suppliers>
<scenes format="isBool"></scenes>
<stores format="isBool"></stores>
</image_type>
</prestashop>
 */
	
	private String name;
	private float width;
	private float height;
	private boolean categories;
	private boolean products;
	private boolean manufacturers;
	private boolean suppliers;
	private boolean scenes;
	private boolean stores;
}

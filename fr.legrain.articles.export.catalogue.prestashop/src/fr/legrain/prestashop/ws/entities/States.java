package fr.legrain.prestashop.ws.entities;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlPath;

import fr.legrain.prestashop.ws.BooleanAdapter;


/**
 * The available states of countries
 * @author nicolas
 *
 */
@XmlRootElement(name="prestashop")
public class States {
/*
 <?xml version="1.0" encoding="UTF-8"?>
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<state>
<id_zone></id_zone>
<id_country></id_country>
<iso_code></iso_code>
<name></name>
<active></active>
</state>
</prestashop>
 */
	
/*
 <?xml version="1.0" encoding="UTF-8"?>
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<state>
<id_zone xlink:href="http://promethee.biz/prestaEb/api/zones/" required="true" format="isUnsignedId"></id_zone>
<id_country xlink:href="http://promethee.biz/prestaEb/api/countries/" required="true" format="isUnsignedId"></id_country>
<iso_code required="true" maxSize="4" format="isStateIsoCode"></iso_code>
<name required="true" maxSize="32" format="isGenericName"></name>
<active format="isBool"></active>
</state>
</prestashop>
 */
	@XmlPath("state/id/text()")
	private int id = 1;
	
	@XmlPath("state/idZone/text()")
	private int idZone;
	
	@XmlPath("state/idCountry/text()")
	private int idCountry;
	
	@XmlPath("state/isoCode/text()")
	private String isoCode;
	
	@XmlPath("state/name/text()")
	private String name;
	
	@XmlPath("state/active/text()")
	@XmlJavaTypeAdapter( BooleanAdapter.class )
	private boolean active;

	public States() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdZone() {
		return idZone;
	}

	public void setIdZone(int idZone) {
		this.idZone = idZone;
	}

	public int getIdCountry() {
		return idCountry;
	}

	public void setIdCountry(int idCountry) {
		this.idCountry = idCountry;
	}

	public String getIsoCode() {
		return isoCode;
	}

	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}

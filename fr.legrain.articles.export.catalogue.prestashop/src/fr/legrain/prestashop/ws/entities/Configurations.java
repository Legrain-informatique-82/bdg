package fr.legrain.prestashop.ws.entities;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlPath;

import fr.legrain.prestashop.ws.DateAdapter;


/**
 * Shop configuration
 * @author nicolas
 *
 */
@XmlRootElement(name="prestashop")
public class Configurations {
/*
<?xml version="1.0" encoding="UTF-8"?>
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<configuration>
	<value></value>
	<name required="true" maxSize="32" format="isConfigName"></name>
	<id_shop_group format="isUnsignedId"></id_shop_group>
	<id_shop format="isUnsignedId"></id_shop>
	<date_add format="isDate"></date_add>
	<date_upd format="isDate"></date_upd>
</configuration>
</prestashop>
 */
	@XmlPath("configuration/id/text()")
	private Integer id;
	
	@XmlPath("configuration/value/text()")
	private String value; //<value></value>
	
	@XmlPath("configuration/name/text()")
	private String name; //<name required="true" maxSize="32" format="isConfigName"></name>
	
	@XmlPath("configuration/id_shop_group/text()")
	private Integer idShopGroup; //<id_shop_group format="isUnsignedId"></id_shop_group>
	
	@XmlPath("configuration/id_shop/text()")
	private Integer idShop; //<id_shop format="isUnsignedId"></id_shop>
	
	@XmlPath("configuration/date_add/text()")
	@XmlJavaTypeAdapter( DateAdapter.class )
	private Date dateAdd; //<date_add format="isDate"></date_add>
	
	@XmlPath("configuration/date_upd/text()")
	@XmlJavaTypeAdapter( DateAdapter.class )
	private Date dateUpd; //<date_upd format="isDate"></date_upd>

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIdShopGroup() {
		return idShopGroup;
	}

	public void setIdShopGroup(Integer idShopGroup) {
		this.idShopGroup = idShopGroup;
	}

	public Integer getIdShop() {
		return idShop;
	}

	public void setIdShop(Integer idShop) {
		this.idShop = idShop;
	}

	public Date getDateAdd() {
		return dateAdd;
	}

	public void setDateAdd(Date dateAdd) {
		this.dateAdd = dateAdd;
	}

	public Date getDateUpd() {
		return dateUpd;
	}

	public void setDateUpd(Date dateUpd) {
		this.dateUpd = dateUpd;
	}
	
}

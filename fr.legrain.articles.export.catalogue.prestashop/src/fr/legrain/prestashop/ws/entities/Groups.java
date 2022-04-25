package fr.legrain.prestashop.ws.entities;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlPath;

import fr.legrain.prestashop.ws.BooleanAdapter;
import fr.legrain.prestashop.ws.DateAdapter;


/**
 * The customer's groups
 * @author nicolas
 *
 */
@XmlRootElement(name="prestashop")
public class Groups {
/*
<?xml version="1.0" encoding="UTF-8"?>
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<group>
	<reduction format="isFloat"></reduction>
	<price_display_method required="true" format="isPriceDisplayMethod"></price_display_method>
	<show_prices format="isBool"></show_prices>
	<date_add format="isDate"></date_add>
	<date_upd format="isDate"></date_upd>
	<name required="true" maxSize="32" format="isGenericName"><language id="1" xlink:href="http://dev2.pageweb.fr/api/languages/1" format="isUnsignedId" ></language></name>
</group>
</prestashop>
 */
	@XmlPath("group/id/text()")
	private Integer id = null;	
	
	@XmlPath("group/reduction/text()")
	private Float reduction = null;	//<reduction format="isFloat"></reduction>
	
	@XmlPath("group/price_display_method/text()")
	private Integer priceDisplayMethod = null;	//<price_display_method required="true" format="isPriceDisplayMethod"></price_display_method>
	
	@XmlPath("group/show_prices/text()")
	@XmlJavaTypeAdapter( BooleanAdapter.class )
	private Boolean showPrices = null;	//<show_prices format="isBool"></show_prices>
	
	@XmlPath("group/date_add/text()")
	@XmlJavaTypeAdapter( DateAdapter.class )
	private Date dateAdd = null;	//<date_add format="isDate"></date_add>
	
	@XmlPath("group/date_upd/text()")
	@XmlJavaTypeAdapter( DateAdapter.class )
	private Date dateUpd = null;	//<date_upd format="isDate"></date_upd>
	
	@XmlPath("group/required/language")
	@XmlElementWrapper(name="required")
	private List<Language> required = null; //<name required="true" maxSize="32" format="isGenericName"><language id="1" xlink:href="http://dev2.pageweb.fr/api/languages/1" format="isUnsignedId" ></language></name>

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getReduction() {
		return reduction;
	}

	public void setReduction(Float reduction) {
		this.reduction = reduction;
	}

	public Integer getPriceDisplayMethod() {
		return priceDisplayMethod;
	}

	public void setPriceDisplayMethod(Integer priceDisplayMethod) {
		this.priceDisplayMethod = priceDisplayMethod;
	}

	public Boolean getShowPrices() {
		return showPrices;
	}

	public void setShowPrices(Boolean showPrices) {
		this.showPrices = showPrices;
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

	public List<Language> getRequired() {
		return required;
	}

	public void setRequired(List<Language> required) {
		this.required = required;
	}
	
}

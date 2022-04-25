package fr.legrain.prestashop.ws.entities;

import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.oxm.annotations.XmlPath;

/**
 * Shop languages
 * @author nicolas
 *
 */
@XmlRootElement(name="prestashop")
public class Languages {
/*
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<language>
	<name required="true" maxSize="32" format="isGenericName"></name>
	<iso_code required="true" maxSize="2" format="isLanguageIsoCode"></iso_code>
	<language_code maxSize="5" format="isLanguageCode"></language_code>
	<active format="isBool"></active>
	<is_rtl format="isBool"></is_rtl>
	<date_format_lite required="true" maxSize="32" format="isPhpDateFormat"></date_format_lite>
	<date_format_full required="true" maxSize="32" format="isPhpDateFormat"></date_format_full>
</language>
</prestashop>

 */
	@XmlPath("language/id/text()")
	private Integer id = null;	
	
	@XmlPath("language/name/text()")
	private String name;
	
	@XmlPath("language/iso_code/text()")
	private String isoCode;
	
	@XmlPath("language/language_code/text()")
	private String languageCode;
	
	@XmlPath("language/active/text()")
	private Boolean active;
	
	@XmlPath("language/is_rtl/text()")
	private Boolean isRtl;
	
	@XmlPath("language/date_format_lite/text()")
	private String dateFormatLite;
	
	@XmlPath("language/date_format_full/text()")
	private String dateFormatFull;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsoCode() {
		return isoCode;
	}

	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getIsRtl() {
		return isRtl;
	}

	public void setIsRtl(Boolean isRtl) {
		this.isRtl = isRtl;
	}

	public String getDateFormatLite() {
		return dateFormatLite;
	}

	public void setDateFormatLite(String dateFormatLite) {
		this.dateFormatLite = dateFormatLite;
	}

	public String getDateFormatFull() {
		return dateFormatFull;
	}

	public void setDateFormatFull(String dateFormatFull) {
		this.dateFormatFull = dateFormatFull;
	}
	
	
}

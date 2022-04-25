package fr.legrain.prestashop.ws.entities;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlPath;

import fr.legrain.prestashop.ws.BooleanAdapter;
import fr.legrain.prestashop.ws.DateAdapter;


/**
 * The e-shop's customers
 * @author nicolas
 *
 */
@XmlRootElement(name="prestashop")
public class Customers {
/*
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<customer>
	<id_default_group></id_default_group>
	<id_lang format="isUnsignedId"></id_lang>
	<newsletter_date_add></newsletter_date_add>
	<ip_registration_newsletter></ip_registration_newsletter>
	<last_passwd_gen></last_passwd_gen>
	<secure_key format="isMd5"></secure_key>
	<deleted format="isBool"></deleted>
	<passwd required="true" maxSize="32" format="isPasswd"></passwd>
	<lastname required="true" maxSize="32" format="isName"></lastname>
	<firstname required="true" maxSize="32" format="isName"></firstname>
	<email required="true" maxSize="128" format="isEmail"></email>
	<id_gender format="isUnsignedId"></id_gender>
	<birthday format="isBirthDate"></birthday>
	<newsletter format="isBool"></newsletter>
	<optin format="isBool"></optin>
	<website format="isUrl"></website>
	<company format="isGenericName"></company>
	<siret format="isSiret"></siret>
	<ape format="isApe"></ape>
	<outstanding_allow_amount format="isFloat"></outstanding_allow_amount>
	<show_public_prices format="isBool"></show_public_prices>
	<id_risk format="isUnsignedInt"></id_risk>
	<max_payment_days format="isUnsignedInt"></max_payment_days>
	<active format="isBool"></active>
	<note maxSize="65000" format="isCleanHtml"></note>
	<is_guest format="isBool"></is_guest>
	<id_shop format="isUnsignedId"></id_shop>
	<id_shop_group format="isUnsignedId"></id_shop_group>
	<date_add format="isDate"></date_add>
	<date_upd format="isDate"></date_upd>
<associations>
<groups node_type="group">
	<group>
	<id></id>
	</group>
</groups>
</associations>
</customer>
</prestashop>
 */
	@XmlPath("customer/id/text()")
	private Integer id;
	
	@XmlPath("customer/id_default_group/text()")
	private Integer idDefault_group;//<id_default_group></id_default_group>
	
	@XmlPath("customer/id_lang/text()")
	private Integer idLang;//<id_lang format="isUnsignedId"></id_lang>
	
	@XmlPath("customer/newsletter_date_add/text()")
	@XmlJavaTypeAdapter( DateAdapter.class )
	private Date newsletterDateAdd; //<newsletter_date_add></newsletter_date_add>
	
	@XmlPath("customer/ip_registration_newsletter/text()")
	private String ipRegistrationNewsletter; //<ip_registration_newsletter></ip_registration_newsletter>
	
	@XmlPath("customer/last_passwd_gen/text()")
	private String lastPasswdGen; //<last_passwd_gen></last_passwd_gen>
	
	@XmlPath("customer/secure_key/text()")
	private String secureKey; //<secure_key format="isMd5"></secure_key>
	
	@XmlPath("customer/deleted/text()")
	@XmlJavaTypeAdapter( BooleanAdapter.class )
	private Boolean deleted; //<deleted format="isBool"></deleted>
	
	@XmlPath("customer/passwd/text()")
	private String passwd; //<passwd required="true" maxSize="32" format="isPasswd"></passwd>
	
	@XmlPath("customer/lastname/text()")
	private String lastname; //<lastname required="true" maxSize="32" format="isName"></lastname>
	
	@XmlPath("customer/firstname/text()")
	private String firstname; //<firstname required="true" maxSize="32" format="isName"></firstname>
	
	@XmlPath("customer/email/text()")
	private String email; //<email required="true" maxSize="128" format="isEmail"></email>
	
	@XmlPath("customer/id_gender/text()")
	private Integer idGender;//<id_gender format="isUnsignedId"></id_gender>
	
	@XmlPath("customer/birthday/text()")
	@XmlJavaTypeAdapter( DateAdapter.class )
	private Date birthday; //<birthday format="isBirthDate"></birthday>
	
	@XmlPath("customer/newsletter/text()")
	@XmlJavaTypeAdapter( BooleanAdapter.class )
	private Boolean newsletter; //<newsletter format="isBool"></newsletter>
	
	@XmlPath("customer/optin/text()")
	@XmlJavaTypeAdapter( BooleanAdapter.class )
	private Boolean optin; //<optin format="isBool"></optin>
	
	@XmlPath("customer/website/text()")
	private String website; //<website format="isUrl"></website>
	
	@XmlPath("customer/company/text()")
	private String company; //<company format="isGenericName"></company>
	
	@XmlPath("customer/siret/text()")
	private String siret; //<siret format="isSiret"></siret>
	
	@XmlPath("customer/ape/text()")
	private String ape; //<ape format="isApe"></ape>
	
	@XmlPath("customer/outstanding_allow_amount/text()")
	private Float outstandingAllowAmount; //<outstanding_allow_amount format="isFloat"></outstanding_allow_amount>
	
	@XmlPath("customer/show_public_prices/text()")
	@XmlJavaTypeAdapter( BooleanAdapter.class )
	private Boolean showPublicPrices; //<show_public_prices format="isBool"></show_public_prices>
	
	@XmlPath("customer/id_risk/text()")
	private Integer idRisk;//<id_risk format="isUnsignedInt"></id_risk>
	
	@XmlPath("customer/max_payment_days/text()")
	private Integer maxPaymentDays;//<max_payment_days format="isUnsignedInt"></max_payment_days>
	
	@XmlPath("customer/active/text()")
	@XmlJavaTypeAdapter( BooleanAdapter.class )
	private Boolean active; //<active format="isBool"></active>
	
	@XmlPath("customer/note/text()")
	private String note; //<note maxSize="65000" format="isCleanHtml"></note>
	
	@XmlPath("customer/is_guest/text()")
	@XmlJavaTypeAdapter( BooleanAdapter.class )
	private Boolean isGuest; //<is_guest format="isBool"></is_guest>
	
	@XmlPath("customer/id_shop/text()")
	private Integer idShop;//<id_shop format="isUnsignedId"></id_shop>
	
	@XmlPath("customer/id_shop_group/text()")
	private Integer idShopGroup;//<id_shop_group format="isUnsignedId"></id_shop_group>
	
	@XmlPath("customer/date_add/text()")
	@XmlJavaTypeAdapter( DateAdapter.class )
	private Date dateAdd; //<date_add format="isDate"></date_add>
	
	@XmlPath("customer/date_upd/text()")
	@XmlJavaTypeAdapter( DateAdapter.class )
	private Date dateUpd; //<date_upd format="isDate"></date_upd>

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdDefault_group() {
		return idDefault_group;
	}

	public void setIdDefault_group(Integer idDefault_group) {
		this.idDefault_group = idDefault_group;
	}

	public Integer getIdLang() {
		return idLang;
	}

	public void setIdLang(Integer idLang) {
		this.idLang = idLang;
	}

	public Date getNewsletterDateAdd() {
		return newsletterDateAdd;
	}

	public void setNewsletterDateAdd(Date newsletterDateAdd) {
		this.newsletterDateAdd = newsletterDateAdd;
	}

	public String getIpRegistrationNewsletter() {
		return ipRegistrationNewsletter;
	}

	public void setIpRegistrationNewsletter(String ipRegistrationNewsletter) {
		this.ipRegistrationNewsletter = ipRegistrationNewsletter;
	}

	public String getLastPasswdGen() {
		return lastPasswdGen;
	}

	public void setLastPasswdGen(String lastPasswdGen) {
		this.lastPasswdGen = lastPasswdGen;
	}

	public String getSecureKey() {
		return secureKey;
	}

	public void setSecureKey(String secureKey) {
		this.secureKey = secureKey;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getIdGender() {
		return idGender;
	}

	public void setIdGender(Integer idGender) {
		this.idGender = idGender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Boolean getNewsletter() {
		return newsletter;
	}

	public void setNewsletter(Boolean newsletter) {
		this.newsletter = newsletter;
	}

	public Boolean getOptin() {
		return optin;
	}

	public void setOptin(Boolean optin) {
		this.optin = optin;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getSiret() {
		return siret;
	}

	public void setSiret(String siret) {
		this.siret = siret;
	}

	public String getApe() {
		return ape;
	}

	public void setApe(String ape) {
		this.ape = ape;
	}

	public Float getOutstandingAllowAmount() {
		return outstandingAllowAmount;
	}

	public void setOutstandingAllowAmount(Float outstandingAllowAmount) {
		this.outstandingAllowAmount = outstandingAllowAmount;
	}

	public Boolean getShowPublicPrices() {
		return showPublicPrices;
	}

	public void setShowPublicPrices(Boolean showPublicPrices) {
		this.showPublicPrices = showPublicPrices;
	}

	public Integer getIdRisk() {
		return idRisk;
	}

	public void setIdRisk(Integer idRisk) {
		this.idRisk = idRisk;
	}

	public Integer getMaxPaymentDays() {
		return maxPaymentDays;
	}

	public void setMaxPaymentDays(Integer maxPaymentDays) {
		this.maxPaymentDays = maxPaymentDays;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Boolean getIsGuest() {
		return isGuest;
	}

	public void setIsGuest(Boolean isGuest) {
		this.isGuest = isGuest;
	}

	public Integer getIdShop() {
		return idShop;
	}

	public void setIdShop(Integer idShop) {
		this.idShop = idShop;
	}

	public Integer getIdShopGroup() {
		return idShopGroup;
	}

	public void setIdShopGroup(Integer idShopGroup) {
		this.idShopGroup = idShopGroup;
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

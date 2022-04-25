package fr.legrain.prestashop.ws.entities;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlCDATA;
import org.eclipse.persistence.oxm.annotations.XmlPath;

import fr.legrain.prestashop.ws.BooleanAdapter;



/**
 * The Customer, Manufacturer and Customer addresses
 * @author nicolas
 *
 */
//@XmlRootElement(name="address")
@XmlRootElement(name="prestashop")
public class Addresses {
/*
<?xml version="1.0" encoding="UTF-8"?>
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<address>
<id_customer></id_customer>
<id_manufacturer></id_manufacturer>
<id_supplier></id_supplier>
<id_country></id_country>
<id_state></id_state>
<alias></alias>
<company></company>
<lastname></lastname>
<firstname></firstname>
<address1></address1>
<address2></address2>
<postcode></postcode>
<city></city>
<other></other>
<phone></phone>
<phone_mobile></phone_mobile>
<dni></dni>
<vat_number></vat_number>
<deleted></deleted>
</address>
</prestashop>
 */
	
public Addresses() {
		super();
	}

	/*
 <?xml version="1.0" encoding="UTF-8"?>
<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<address>
<id_customer xlink:href="http://promethee.biz/prestaEb/api/customers/" format="isNullOrUnsignedId"></id_customer>
<id_manufacturer xlink:href="http://promethee.biz/prestaEb/api/manufacturers/" format="isNullOrUnsignedId"></id_manufacturer>
<id_supplier xlink:href="http://promethee.biz/prestaEb/api/suppliers/" format="isNullOrUnsignedId"></id_supplier>
<id_country xlink:href="http://promethee.biz/prestaEb/api/countries/" required="true" format="isUnsignedId"></id_country>
<id_state xlink:href="http://promethee.biz/prestaEb/api/states/" format="isNullOrUnsignedId"></id_state>
<alias required="true" maxSize="32" format="isGenericName"></alias>
<company maxSize="32" format="isGenericName"></company>
<lastname required="true" maxSize="32" format="isName"></lastname>
<firstname required="true" maxSize="32" format="isName"></firstname>
<address1 required="true" maxSize="128" format="isAddress"></address1>
<address2 maxSize="128" format="isAddress"></address2>
<postcode maxSize="12" format="isPostCode"></postcode>
<city required="true" maxSize="64" format="isCityName"></city>
<other maxSize="300" format="isMessage"></other>
<phone maxSize="16" format="isPhoneNumber"></phone>
<phone_mobile maxSize="16" format="isPhoneNumber"></phone_mobile>
<dni maxSize="16" format="isDniLite"></dni>
<vat_number format="isGenericName"></vat_number>
<deleted format="isBool"></deleted>
</address>
</prestashop>
 */
	@XmlPath("address/id/text()")
//	@XmlCDATA
	private Integer id;
	@XmlPath("address/id_customer/text()")
//	@XmlCDATA
	private int idCustomer;
	@XmlPath("address/id_manufacturer/text()")
//	@XmlCDATA
	private int idManufacturer;
	@XmlPath("address/id_supplier/text()")
//	@XmlCDATA
	private int idSupplier;
	@XmlPath("address/id_country/text()")
//	@XmlCDATA
	private int idCountry;
	
	
	
	@XmlPath("address/id_state/text()")
	private int idState;
	//@XmlJavaTypeAdapter(StateAdapter.class)
	//private States idState;
	
	
	@XmlPath("address/alias/text()")
//	@XmlCDATA
	private String alias;
	@XmlPath("address/company/text()")
//	@XmlCDATA
	private String company;
	@XmlPath("address/lastname/text()")
//	@XmlCDATA
	private String lastname;
	@XmlPath("address/firstname/text()")
//	@XmlCDATA
	private String firstname;
	@XmlPath("address/address1/text()")
//	@XmlCDATA
	private String address1;
	@XmlPath("address/address2/text()")
//	@XmlCDATA
	private String address2;
	@XmlPath("address/postcode/text()")
	@XmlCDATA
	private String postcode;
	@XmlPath("address/city/text()")
//	@XmlCDATA
	private String city;
	@XmlPath("address/other/text()")
//	@XmlCDATA
	private String other;
	@XmlPath("address/phone/text()")
//	@XmlCDATA
	private String phone;
	@XmlPath("address/phone_mobile/text()")
//	@XmlCDATA
	private String phoneMobile;
	@XmlPath("address/dni/text()")
//	@XmlCDATA
	private String dni;
	@XmlPath("address/vat_number/text()")
//	@XmlCDATA
	private String vatNumber;
	@XmlPath("address/deleted/text()")
//	@XmlCDATA
	@XmlJavaTypeAdapter( BooleanAdapter.class )
	private boolean deleted;
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIdCustomer(int idCustomer) {
		this.idCustomer = idCustomer;
	}

	public void setIdManufacturer(int idManufacturer) {
		this.idManufacturer = idManufacturer;
	}

	public void setIdSupplier(int idSupplier) {
		this.idSupplier = idSupplier;
	}

	public void setIdCountry(int idCountry) {
		this.idCountry = idCountry;
	}

	public void setIdState(int idState) {
		this.idState = idState;
	}
	
//	public void setIdState(States idState) {
//		this.idState = idState;
//	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setPhoneMobile(String phoneMobile) {
		this.phoneMobile = phoneMobile;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Integer getId() {
		return id;
	}

	public int getIdCustomer() {
		return idCustomer;
	}

	public int getIdManufacturer() {
		return idManufacturer;
	}

	public int getIdSupplier() {
		return idSupplier;
	}

	public int getIdCountry() {
		return idCountry;
	}

	public int getIdState() {
		return idState;
	}
	
//	public States getIdState() {
//		return idState;
//	}

	public String getAlias() {
		return alias;
	}

	public String getCompany() {
		return company;
	}

	public String getLastname() {
		return lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getAddress1() {
		return address1;
	}

	public String getAddress2() {
		return address2;
	}

	public String getPostcode() {
		return postcode;
	}

	public String getCity() {
		return city;
	}

	public String getOther() {
		return other;
	}

	public String getPhone() {
		return phone;
	}

	public String getPhoneMobile() {
		return phoneMobile;
	}

	public String getDni() {
		return dni;
	}

	public String getVatNumber() {
		return vatNumber;
	}

	public boolean isDeleted() {
		return deleted;
	}
	
	
	
}

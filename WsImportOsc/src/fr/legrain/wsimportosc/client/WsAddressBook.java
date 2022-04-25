
package fr.legrain.wsimportosc.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for wsAddressBook complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wsAddressBook">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="adressBookId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="customersId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="entryCity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="entryCompany" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="entryCountriesId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="entryFirstname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="entryGender" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="entryLastname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="entryPostcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="entryStreetAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="entrySuburb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="entryZoneId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wsAddressBook", propOrder = {
    "adressBookId",
    "customersId",
    "entryCity",
    "entryCompany",
    "entryCountriesId",
    "entryFirstname",
    "entryGender",
    "entryLastname",
    "entryPostcode",
    "entryStreetAddress",
    "entrySuburb",
    "entryZoneId"
})
public class WsAddressBook {

    protected int adressBookId;
    protected int customersId;
    protected String entryCity;
    protected String entryCompany;
    protected int entryCountriesId;
    protected String entryFirstname;
    protected String entryGender;
    protected String entryLastname;
    protected String entryPostcode;
    protected String entryStreetAddress;
    protected String entrySuburb;
    protected int entryZoneId;

    /**
     * Gets the value of the adressBookId property.
     * 
     */
    public int getAdressBookId() {
        return adressBookId;
    }

    /**
     * Sets the value of the adressBookId property.
     * 
     */
    public void setAdressBookId(int value) {
        this.adressBookId = value;
    }

    /**
     * Gets the value of the customersId property.
     * 
     */
    public int getCustomersId() {
        return customersId;
    }

    /**
     * Sets the value of the customersId property.
     * 
     */
    public void setCustomersId(int value) {
        this.customersId = value;
    }

    /**
     * Gets the value of the entryCity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntryCity() {
        return entryCity;
    }

    /**
     * Sets the value of the entryCity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntryCity(String value) {
        this.entryCity = value;
    }

    /**
     * Gets the value of the entryCompany property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntryCompany() {
        return entryCompany;
    }

    /**
     * Sets the value of the entryCompany property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntryCompany(String value) {
        this.entryCompany = value;
    }

    /**
     * Gets the value of the entryCountriesId property.
     * 
     */
    public int getEntryCountriesId() {
        return entryCountriesId;
    }

    /**
     * Sets the value of the entryCountriesId property.
     * 
     */
    public void setEntryCountriesId(int value) {
        this.entryCountriesId = value;
    }

    /**
     * Gets the value of the entryFirstname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntryFirstname() {
        return entryFirstname;
    }

    /**
     * Sets the value of the entryFirstname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntryFirstname(String value) {
        this.entryFirstname = value;
    }

    /**
     * Gets the value of the entryGender property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntryGender() {
        return entryGender;
    }

    /**
     * Sets the value of the entryGender property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntryGender(String value) {
        this.entryGender = value;
    }

    /**
     * Gets the value of the entryLastname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntryLastname() {
        return entryLastname;
    }

    /**
     * Sets the value of the entryLastname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntryLastname(String value) {
        this.entryLastname = value;
    }

    /**
     * Gets the value of the entryPostcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntryPostcode() {
        return entryPostcode;
    }

    /**
     * Sets the value of the entryPostcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntryPostcode(String value) {
        this.entryPostcode = value;
    }

    /**
     * Gets the value of the entryStreetAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntryStreetAddress() {
        return entryStreetAddress;
    }

    /**
     * Sets the value of the entryStreetAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntryStreetAddress(String value) {
        this.entryStreetAddress = value;
    }

    /**
     * Gets the value of the entrySuburb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntrySuburb() {
        return entrySuburb;
    }

    /**
     * Sets the value of the entrySuburb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntrySuburb(String value) {
        this.entrySuburb = value;
    }

    /**
     * Gets the value of the entryZoneId property.
     * 
     */
    public int getEntryZoneId() {
        return entryZoneId;
    }

    /**
     * Sets the value of the entryZoneId property.
     * 
     */
    public void setEntryZoneId(int value) {
        this.entryZoneId = value;
    }

}

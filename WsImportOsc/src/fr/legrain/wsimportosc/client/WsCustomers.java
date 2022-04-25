
package fr.legrain.wsimportosc.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for wsCustomers complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wsCustomers">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="addressBookId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="customersDob" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="customersEmailAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customersFax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customersFirstname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customersGender" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customersId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="customersLastname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customersTelephone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wsCustomers", propOrder = {
    "addressBookId",
    "customersDob",
    "customersEmailAddress",
    "customersFax",
    "customersFirstname",
    "customersGender",
    "customersId",
    "customersLastname",
    "customersTelephone"
})
public class WsCustomers {

    protected int addressBookId;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar customersDob;
    protected String customersEmailAddress;
    protected String customersFax;
    protected String customersFirstname;
    protected String customersGender;
    protected int customersId;
    protected String customersLastname;
    protected String customersTelephone;

    /**
     * Gets the value of the addressBookId property.
     * 
     */
    public int getAddressBookId() {
        return addressBookId;
    }

    /**
     * Sets the value of the addressBookId property.
     * 
     */
    public void setAddressBookId(int value) {
        this.addressBookId = value;
    }

    /**
     * Gets the value of the customersDob property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCustomersDob() {
        return customersDob;
    }

    /**
     * Sets the value of the customersDob property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCustomersDob(XMLGregorianCalendar value) {
        this.customersDob = value;
    }

    /**
     * Gets the value of the customersEmailAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomersEmailAddress() {
        return customersEmailAddress;
    }

    /**
     * Sets the value of the customersEmailAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomersEmailAddress(String value) {
        this.customersEmailAddress = value;
    }

    /**
     * Gets the value of the customersFax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomersFax() {
        return customersFax;
    }

    /**
     * Sets the value of the customersFax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomersFax(String value) {
        this.customersFax = value;
    }

    /**
     * Gets the value of the customersFirstname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomersFirstname() {
        return customersFirstname;
    }

    /**
     * Sets the value of the customersFirstname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomersFirstname(String value) {
        this.customersFirstname = value;
    }

    /**
     * Gets the value of the customersGender property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomersGender() {
        return customersGender;
    }

    /**
     * Sets the value of the customersGender property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomersGender(String value) {
        this.customersGender = value;
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
     * Gets the value of the customersLastname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomersLastname() {
        return customersLastname;
    }

    /**
     * Sets the value of the customersLastname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomersLastname(String value) {
        this.customersLastname = value;
    }

    /**
     * Gets the value of the customersTelephone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomersTelephone() {
        return customersTelephone;
    }

    /**
     * Sets the value of the customersTelephone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomersTelephone(String value) {
        this.customersTelephone = value;
    }

}

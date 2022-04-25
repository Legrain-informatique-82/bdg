
package fr.legrain.wsimportosc.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for wsOrders complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wsOrders">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="billingCity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="billingCompany" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="billingCountry" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="billingName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="billingPostcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="billingState" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="billingStreetAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="billingSuburb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ccExpires" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ccNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ccOwner" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ccType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="currency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="currencyValue" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="customersCity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customersCompany" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customersCountry" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customersEmailAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customersId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="customersName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customersPostcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customersState" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customersStreetAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customersSuburb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customersTelephone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="datePurchased" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="deliveryCity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deliveryCompany" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deliveryCountry" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deliveryName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deliveryPostcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deliveryState" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deliveryStreetAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deliverySuburb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastModified" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ordersDateFinished" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ordersId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ordersSstatus" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="paymentMethod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wsOrders", propOrder = {
    "billingCity",
    "billingCompany",
    "billingCountry",
    "billingName",
    "billingPostcode",
    "billingState",
    "billingStreetAddress",
    "billingSuburb",
    "ccExpires",
    "ccNumber",
    "ccOwner",
    "ccType",
    "currency",
    "currencyValue",
    "customersCity",
    "customersCompany",
    "customersCountry",
    "customersEmailAddress",
    "customersId",
    "customersName",
    "customersPostcode",
    "customersState",
    "customersStreetAddress",
    "customersSuburb",
    "customersTelephone",
    "datePurchased",
    "deliveryCity",
    "deliveryCompany",
    "deliveryCountry",
    "deliveryName",
    "deliveryPostcode",
    "deliveryState",
    "deliveryStreetAddress",
    "deliverySuburb",
    "lastModified",
    "ordersDateFinished",
    "ordersId",
    "ordersSstatus",
    "paymentMethod"
})
public class WsOrders {

    protected String billingCity;
    protected String billingCompany;
    protected String billingCountry;
    protected String billingName;
    protected String billingPostcode;
    protected String billingState;
    protected String billingStreetAddress;
    protected String billingSuburb;
    protected String ccExpires;
    protected String ccNumber;
    protected String ccOwner;
    protected String ccType;
    protected String currency;
    protected float currencyValue;
    protected String customersCity;
    protected String customersCompany;
    protected String customersCountry;
    protected String customersEmailAddress;
    protected int customersId;
    protected String customersName;
    protected String customersPostcode;
    protected String customersState;
    protected String customersStreetAddress;
    protected String customersSuburb;
    protected String customersTelephone;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datePurchased;
    protected String deliveryCity;
    protected String deliveryCompany;
    protected String deliveryCountry;
    protected String deliveryName;
    protected String deliveryPostcode;
    protected String deliveryState;
    protected String deliveryStreetAddress;
    protected String deliverySuburb;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastModified;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar ordersDateFinished;
    protected int ordersId;
    protected int ordersSstatus;
    protected String paymentMethod;

    /**
     * Gets the value of the billingCity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillingCity() {
        return billingCity;
    }

    /**
     * Sets the value of the billingCity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillingCity(String value) {
        this.billingCity = value;
    }

    /**
     * Gets the value of the billingCompany property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillingCompany() {
        return billingCompany;
    }

    /**
     * Sets the value of the billingCompany property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillingCompany(String value) {
        this.billingCompany = value;
    }

    /**
     * Gets the value of the billingCountry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillingCountry() {
        return billingCountry;
    }

    /**
     * Sets the value of the billingCountry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillingCountry(String value) {
        this.billingCountry = value;
    }

    /**
     * Gets the value of the billingName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillingName() {
        return billingName;
    }

    /**
     * Sets the value of the billingName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillingName(String value) {
        this.billingName = value;
    }

    /**
     * Gets the value of the billingPostcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillingPostcode() {
        return billingPostcode;
    }

    /**
     * Sets the value of the billingPostcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillingPostcode(String value) {
        this.billingPostcode = value;
    }

    /**
     * Gets the value of the billingState property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillingState() {
        return billingState;
    }

    /**
     * Sets the value of the billingState property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillingState(String value) {
        this.billingState = value;
    }

    /**
     * Gets the value of the billingStreetAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillingStreetAddress() {
        return billingStreetAddress;
    }

    /**
     * Sets the value of the billingStreetAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillingStreetAddress(String value) {
        this.billingStreetAddress = value;
    }

    /**
     * Gets the value of the billingSuburb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillingSuburb() {
        return billingSuburb;
    }

    /**
     * Sets the value of the billingSuburb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillingSuburb(String value) {
        this.billingSuburb = value;
    }

    /**
     * Gets the value of the ccExpires property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCcExpires() {
        return ccExpires;
    }

    /**
     * Sets the value of the ccExpires property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCcExpires(String value) {
        this.ccExpires = value;
    }

    /**
     * Gets the value of the ccNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCcNumber() {
        return ccNumber;
    }

    /**
     * Sets the value of the ccNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCcNumber(String value) {
        this.ccNumber = value;
    }

    /**
     * Gets the value of the ccOwner property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCcOwner() {
        return ccOwner;
    }

    /**
     * Sets the value of the ccOwner property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCcOwner(String value) {
        this.ccOwner = value;
    }

    /**
     * Gets the value of the ccType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCcType() {
        return ccType;
    }

    /**
     * Sets the value of the ccType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCcType(String value) {
        this.ccType = value;
    }

    /**
     * Gets the value of the currency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrency(String value) {
        this.currency = value;
    }

    /**
     * Gets the value of the currencyValue property.
     * 
     */
    public float getCurrencyValue() {
        return currencyValue;
    }

    /**
     * Sets the value of the currencyValue property.
     * 
     */
    public void setCurrencyValue(float value) {
        this.currencyValue = value;
    }

    /**
     * Gets the value of the customersCity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomersCity() {
        return customersCity;
    }

    /**
     * Sets the value of the customersCity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomersCity(String value) {
        this.customersCity = value;
    }

    /**
     * Gets the value of the customersCompany property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomersCompany() {
        return customersCompany;
    }

    /**
     * Sets the value of the customersCompany property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomersCompany(String value) {
        this.customersCompany = value;
    }

    /**
     * Gets the value of the customersCountry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomersCountry() {
        return customersCountry;
    }

    /**
     * Sets the value of the customersCountry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomersCountry(String value) {
        this.customersCountry = value;
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
     * Gets the value of the customersName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomersName() {
        return customersName;
    }

    /**
     * Sets the value of the customersName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomersName(String value) {
        this.customersName = value;
    }

    /**
     * Gets the value of the customersPostcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomersPostcode() {
        return customersPostcode;
    }

    /**
     * Sets the value of the customersPostcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomersPostcode(String value) {
        this.customersPostcode = value;
    }

    /**
     * Gets the value of the customersState property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomersState() {
        return customersState;
    }

    /**
     * Sets the value of the customersState property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomersState(String value) {
        this.customersState = value;
    }

    /**
     * Gets the value of the customersStreetAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomersStreetAddress() {
        return customersStreetAddress;
    }

    /**
     * Sets the value of the customersStreetAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomersStreetAddress(String value) {
        this.customersStreetAddress = value;
    }

    /**
     * Gets the value of the customersSuburb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomersSuburb() {
        return customersSuburb;
    }

    /**
     * Sets the value of the customersSuburb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomersSuburb(String value) {
        this.customersSuburb = value;
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

    /**
     * Gets the value of the datePurchased property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDatePurchased() {
        return datePurchased;
    }

    /**
     * Sets the value of the datePurchased property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDatePurchased(XMLGregorianCalendar value) {
        this.datePurchased = value;
    }

    /**
     * Gets the value of the deliveryCity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliveryCity() {
        return deliveryCity;
    }

    /**
     * Sets the value of the deliveryCity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveryCity(String value) {
        this.deliveryCity = value;
    }

    /**
     * Gets the value of the deliveryCompany property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliveryCompany() {
        return deliveryCompany;
    }

    /**
     * Sets the value of the deliveryCompany property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveryCompany(String value) {
        this.deliveryCompany = value;
    }

    /**
     * Gets the value of the deliveryCountry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliveryCountry() {
        return deliveryCountry;
    }

    /**
     * Sets the value of the deliveryCountry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveryCountry(String value) {
        this.deliveryCountry = value;
    }

    /**
     * Gets the value of the deliveryName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliveryName() {
        return deliveryName;
    }

    /**
     * Sets the value of the deliveryName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveryName(String value) {
        this.deliveryName = value;
    }

    /**
     * Gets the value of the deliveryPostcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliveryPostcode() {
        return deliveryPostcode;
    }

    /**
     * Sets the value of the deliveryPostcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveryPostcode(String value) {
        this.deliveryPostcode = value;
    }

    /**
     * Gets the value of the deliveryState property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliveryState() {
        return deliveryState;
    }

    /**
     * Sets the value of the deliveryState property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveryState(String value) {
        this.deliveryState = value;
    }

    /**
     * Gets the value of the deliveryStreetAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliveryStreetAddress() {
        return deliveryStreetAddress;
    }

    /**
     * Sets the value of the deliveryStreetAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveryStreetAddress(String value) {
        this.deliveryStreetAddress = value;
    }

    /**
     * Gets the value of the deliverySuburb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliverySuburb() {
        return deliverySuburb;
    }

    /**
     * Sets the value of the deliverySuburb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliverySuburb(String value) {
        this.deliverySuburb = value;
    }

    /**
     * Gets the value of the lastModified property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastModified() {
        return lastModified;
    }

    /**
     * Sets the value of the lastModified property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastModified(XMLGregorianCalendar value) {
        this.lastModified = value;
    }

    /**
     * Gets the value of the ordersDateFinished property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOrdersDateFinished() {
        return ordersDateFinished;
    }

    /**
     * Sets the value of the ordersDateFinished property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOrdersDateFinished(XMLGregorianCalendar value) {
        this.ordersDateFinished = value;
    }

    /**
     * Gets the value of the ordersId property.
     * 
     */
    public int getOrdersId() {
        return ordersId;
    }

    /**
     * Sets the value of the ordersId property.
     * 
     */
    public void setOrdersId(int value) {
        this.ordersId = value;
    }

    /**
     * Gets the value of the ordersSstatus property.
     * 
     */
    public int getOrdersSstatus() {
        return ordersSstatus;
    }

    /**
     * Sets the value of the ordersSstatus property.
     * 
     */
    public void setOrdersSstatus(int value) {
        this.ordersSstatus = value;
    }

    /**
     * Gets the value of the paymentMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * Sets the value of the paymentMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentMethod(String value) {
        this.paymentMethod = value;
    }

}

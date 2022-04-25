
package fr.legrain.wsimportosc.client;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for classWebService complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="classWebService">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="flag" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="listWsAddressBook" type="{http://osc.service.web/}wsAddressBook" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="listWsCountries" type="{http://osc.service.web/}wsCountries" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="listWsCustomers" type="{http://osc.service.web/}wsCustomers" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="listWsOrders" type="{http://osc.service.web/}wsOrders" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="listWsOrdersProducts" type="{http://osc.service.web/}wsOrdersProducts" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="listWsProducts" type="{http://osc.service.web/}wsProducts" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="listWsZone" type="{http://osc.service.web/}wsZone" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="mapWsAddressBook">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="entry" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *                             &lt;element name="value" type="{http://osc.service.web/}wsAddressBook" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="mapWsCountries">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="entry" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *                             &lt;element name="value" type="{http://osc.service.web/}wsCountries" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="mapWsCustomers">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="entry" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *                             &lt;element name="value" type="{http://osc.service.web/}wsCustomers" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="mapWsOrders">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="entry" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *                             &lt;element name="value" type="{http://osc.service.web/}wsOrders" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="mapWsOrdersProducts">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="entry" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *                             &lt;element name="value" type="{http://osc.service.web/}wsOrdersProducts" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="mapWsProducts">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="entry" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *                             &lt;element name="value" type="{http://osc.service.web/}wsProducts" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="mapWsZone">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="entry" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *                             &lt;element name="value" type="{http://osc.service.web/}wsZone" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "classWebService", propOrder = {
    "flag",
    "listWsAddressBook",
    "listWsCountries",
    "listWsCustomers",
    "listWsOrders",
    "listWsOrdersProducts",
    "listWsProducts",
    "listWsZone",
    "mapWsAddressBook",
    "mapWsCountries",
    "mapWsCustomers",
    "mapWsOrders",
    "mapWsOrdersProducts",
    "mapWsProducts",
    "mapWsZone"
})
public class ClassWebService {

    protected boolean flag;
    @XmlElement(nillable = true)
    protected List<WsAddressBook> listWsAddressBook;
    @XmlElement(nillable = true)
    protected List<WsCountries> listWsCountries;
    @XmlElement(nillable = true)
    protected List<WsCustomers> listWsCustomers;
    @XmlElement(nillable = true)
    protected List<WsOrders> listWsOrders;
    @XmlElement(nillable = true)
    protected List<WsOrdersProducts> listWsOrdersProducts;
    @XmlElement(nillable = true)
    protected List<WsProducts> listWsProducts;
    @XmlElement(nillable = true)
    protected List<WsZone> listWsZone;
    @XmlElement(required = true)
    protected ClassWebService.MapWsAddressBook mapWsAddressBook;
    @XmlElement(required = true)
    protected ClassWebService.MapWsCountries mapWsCountries;
    @XmlElement(required = true)
    protected ClassWebService.MapWsCustomers mapWsCustomers;
    @XmlElement(required = true)
    protected ClassWebService.MapWsOrders mapWsOrders;
    @XmlElement(required = true)
    protected ClassWebService.MapWsOrdersProducts mapWsOrdersProducts;
    @XmlElement(required = true)
    protected ClassWebService.MapWsProducts mapWsProducts;
    @XmlElement(required = true)
    protected ClassWebService.MapWsZone mapWsZone;

    /**
     * Gets the value of the flag property.
     * 
     */
    public boolean isFlag() {
        return flag;
    }

    /**
     * Sets the value of the flag property.
     * 
     */
    public void setFlag(boolean value) {
        this.flag = value;
    }

    /**
     * Gets the value of the listWsAddressBook property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listWsAddressBook property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListWsAddressBook().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WsAddressBook }
     * 
     * 
     */
    public List<WsAddressBook> getListWsAddressBook() {
        if (listWsAddressBook == null) {
            listWsAddressBook = new ArrayList<WsAddressBook>();
        }
        return this.listWsAddressBook;
    }

    /**
     * Gets the value of the listWsCountries property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listWsCountries property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListWsCountries().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WsCountries }
     * 
     * 
     */
    public List<WsCountries> getListWsCountries() {
        if (listWsCountries == null) {
            listWsCountries = new ArrayList<WsCountries>();
        }
        return this.listWsCountries;
    }

    /**
     * Gets the value of the listWsCustomers property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listWsCustomers property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListWsCustomers().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WsCustomers }
     * 
     * 
     */
    public List<WsCustomers> getListWsCustomers() {
        if (listWsCustomers == null) {
            listWsCustomers = new ArrayList<WsCustomers>();
        }
        return this.listWsCustomers;
    }

    /**
     * Gets the value of the listWsOrders property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listWsOrders property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListWsOrders().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WsOrders }
     * 
     * 
     */
    public List<WsOrders> getListWsOrders() {
        if (listWsOrders == null) {
            listWsOrders = new ArrayList<WsOrders>();
        }
        return this.listWsOrders;
    }

    /**
     * Gets the value of the listWsOrdersProducts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listWsOrdersProducts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListWsOrdersProducts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WsOrdersProducts }
     * 
     * 
     */
    public List<WsOrdersProducts> getListWsOrdersProducts() {
        if (listWsOrdersProducts == null) {
            listWsOrdersProducts = new ArrayList<WsOrdersProducts>();
        }
        return this.listWsOrdersProducts;
    }

    /**
     * Gets the value of the listWsProducts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listWsProducts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListWsProducts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WsProducts }
     * 
     * 
     */
    public List<WsProducts> getListWsProducts() {
        if (listWsProducts == null) {
            listWsProducts = new ArrayList<WsProducts>();
        }
        return this.listWsProducts;
    }

    /**
     * Gets the value of the listWsZone property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listWsZone property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListWsZone().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WsZone }
     * 
     * 
     */
    public List<WsZone> getListWsZone() {
        if (listWsZone == null) {
            listWsZone = new ArrayList<WsZone>();
        }
        return this.listWsZone;
    }

    /**
     * Gets the value of the mapWsAddressBook property.
     * 
     * @return
     *     possible object is
     *     {@link ClassWebService.MapWsAddressBook }
     *     
     */
    public ClassWebService.MapWsAddressBook getMapWsAddressBook() {
        return mapWsAddressBook;
    }

    /**
     * Sets the value of the mapWsAddressBook property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClassWebService.MapWsAddressBook }
     *     
     */
    public void setMapWsAddressBook(ClassWebService.MapWsAddressBook value) {
        this.mapWsAddressBook = value;
    }

    /**
     * Gets the value of the mapWsCountries property.
     * 
     * @return
     *     possible object is
     *     {@link ClassWebService.MapWsCountries }
     *     
     */
    public ClassWebService.MapWsCountries getMapWsCountries() {
        return mapWsCountries;
    }

    /**
     * Sets the value of the mapWsCountries property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClassWebService.MapWsCountries }
     *     
     */
    public void setMapWsCountries(ClassWebService.MapWsCountries value) {
        this.mapWsCountries = value;
    }

    /**
     * Gets the value of the mapWsCustomers property.
     * 
     * @return
     *     possible object is
     *     {@link ClassWebService.MapWsCustomers }
     *     
     */
    public ClassWebService.MapWsCustomers getMapWsCustomers() {
        return mapWsCustomers;
    }

    /**
     * Sets the value of the mapWsCustomers property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClassWebService.MapWsCustomers }
     *     
     */
    public void setMapWsCustomers(ClassWebService.MapWsCustomers value) {
        this.mapWsCustomers = value;
    }

    /**
     * Gets the value of the mapWsOrders property.
     * 
     * @return
     *     possible object is
     *     {@link ClassWebService.MapWsOrders }
     *     
     */
    public ClassWebService.MapWsOrders getMapWsOrders() {
        return mapWsOrders;
    }

    /**
     * Sets the value of the mapWsOrders property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClassWebService.MapWsOrders }
     *     
     */
    public void setMapWsOrders(ClassWebService.MapWsOrders value) {
        this.mapWsOrders = value;
    }

    /**
     * Gets the value of the mapWsOrdersProducts property.
     * 
     * @return
     *     possible object is
     *     {@link ClassWebService.MapWsOrdersProducts }
     *     
     */
    public ClassWebService.MapWsOrdersProducts getMapWsOrdersProducts() {
        return mapWsOrdersProducts;
    }

    /**
     * Sets the value of the mapWsOrdersProducts property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClassWebService.MapWsOrdersProducts }
     *     
     */
    public void setMapWsOrdersProducts(ClassWebService.MapWsOrdersProducts value) {
        this.mapWsOrdersProducts = value;
    }

    /**
     * Gets the value of the mapWsProducts property.
     * 
     * @return
     *     possible object is
     *     {@link ClassWebService.MapWsProducts }
     *     
     */
    public ClassWebService.MapWsProducts getMapWsProducts() {
        return mapWsProducts;
    }

    /**
     * Sets the value of the mapWsProducts property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClassWebService.MapWsProducts }
     *     
     */
    public void setMapWsProducts(ClassWebService.MapWsProducts value) {
        this.mapWsProducts = value;
    }

    /**
     * Gets the value of the mapWsZone property.
     * 
     * @return
     *     possible object is
     *     {@link ClassWebService.MapWsZone }
     *     
     */
    public ClassWebService.MapWsZone getMapWsZone() {
        return mapWsZone;
    }

    /**
     * Sets the value of the mapWsZone property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClassWebService.MapWsZone }
     *     
     */
    public void setMapWsZone(ClassWebService.MapWsZone value) {
        this.mapWsZone = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="entry" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
     *                   &lt;element name="value" type="{http://osc.service.web/}wsAddressBook" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "entry"
    })
    public static class MapWsAddressBook {

        protected List<ClassWebService.MapWsAddressBook.Entry> entry;

        /**
         * Gets the value of the entry property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the entry property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEntry().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ClassWebService.MapWsAddressBook.Entry }
         * 
         * 
         */
        public List<ClassWebService.MapWsAddressBook.Entry> getEntry() {
            if (entry == null) {
                entry = new ArrayList<ClassWebService.MapWsAddressBook.Entry>();
            }
            return this.entry;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
         *         &lt;element name="value" type="{http://osc.service.web/}wsAddressBook" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "key",
            "value"
        })
        public static class Entry {

            protected Integer key;
            protected WsAddressBook value;

            /**
             * Gets the value of the key property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getKey() {
                return key;
            }

            /**
             * Sets the value of the key property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setKey(Integer value) {
                this.key = value;
            }

            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link WsAddressBook }
             *     
             */
            public WsAddressBook getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link WsAddressBook }
             *     
             */
            public void setValue(WsAddressBook value) {
                this.value = value;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="entry" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
     *                   &lt;element name="value" type="{http://osc.service.web/}wsCountries" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "entry"
    })
    public static class MapWsCountries {

        protected List<ClassWebService.MapWsCountries.Entry> entry;

        /**
         * Gets the value of the entry property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the entry property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEntry().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ClassWebService.MapWsCountries.Entry }
         * 
         * 
         */
        public List<ClassWebService.MapWsCountries.Entry> getEntry() {
            if (entry == null) {
                entry = new ArrayList<ClassWebService.MapWsCountries.Entry>();
            }
            return this.entry;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
         *         &lt;element name="value" type="{http://osc.service.web/}wsCountries" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "key",
            "value"
        })
        public static class Entry {

            protected Integer key;
            protected WsCountries value;

            /**
             * Gets the value of the key property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getKey() {
                return key;
            }

            /**
             * Sets the value of the key property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setKey(Integer value) {
                this.key = value;
            }

            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link WsCountries }
             *     
             */
            public WsCountries getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link WsCountries }
             *     
             */
            public void setValue(WsCountries value) {
                this.value = value;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="entry" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
     *                   &lt;element name="value" type="{http://osc.service.web/}wsCustomers" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "entry"
    })
    public static class MapWsCustomers {

        protected List<ClassWebService.MapWsCustomers.Entry> entry;

        /**
         * Gets the value of the entry property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the entry property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEntry().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ClassWebService.MapWsCustomers.Entry }
         * 
         * 
         */
        public List<ClassWebService.MapWsCustomers.Entry> getEntry() {
            if (entry == null) {
                entry = new ArrayList<ClassWebService.MapWsCustomers.Entry>();
            }
            return this.entry;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
         *         &lt;element name="value" type="{http://osc.service.web/}wsCustomers" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "key",
            "value"
        })
        public static class Entry {

            protected Integer key;
            protected WsCustomers value;

            /**
             * Gets the value of the key property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getKey() {
                return key;
            }

            /**
             * Sets the value of the key property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setKey(Integer value) {
                this.key = value;
            }

            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link WsCustomers }
             *     
             */
            public WsCustomers getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link WsCustomers }
             *     
             */
            public void setValue(WsCustomers value) {
                this.value = value;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="entry" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
     *                   &lt;element name="value" type="{http://osc.service.web/}wsOrders" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "entry"
    })
    public static class MapWsOrders {

        protected List<ClassWebService.MapWsOrders.Entry> entry;

        /**
         * Gets the value of the entry property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the entry property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEntry().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ClassWebService.MapWsOrders.Entry }
         * 
         * 
         */
        public List<ClassWebService.MapWsOrders.Entry> getEntry() {
            if (entry == null) {
                entry = new ArrayList<ClassWebService.MapWsOrders.Entry>();
            }
            return this.entry;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
         *         &lt;element name="value" type="{http://osc.service.web/}wsOrders" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "key",
            "value"
        })
        public static class Entry {

            protected Integer key;
            protected WsOrders value;

            /**
             * Gets the value of the key property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getKey() {
                return key;
            }

            /**
             * Sets the value of the key property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setKey(Integer value) {
                this.key = value;
            }

            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link WsOrders }
             *     
             */
            public WsOrders getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link WsOrders }
             *     
             */
            public void setValue(WsOrders value) {
                this.value = value;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="entry" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
     *                   &lt;element name="value" type="{http://osc.service.web/}wsOrdersProducts" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "entry"
    })
    public static class MapWsOrdersProducts {

        protected List<ClassWebService.MapWsOrdersProducts.Entry> entry;

        /**
         * Gets the value of the entry property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the entry property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEntry().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ClassWebService.MapWsOrdersProducts.Entry }
         * 
         * 
         */
        public List<ClassWebService.MapWsOrdersProducts.Entry> getEntry() {
            if (entry == null) {
                entry = new ArrayList<ClassWebService.MapWsOrdersProducts.Entry>();
            }
            return this.entry;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
         *         &lt;element name="value" type="{http://osc.service.web/}wsOrdersProducts" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "key",
            "value"
        })
        public static class Entry {

            protected Integer key;
            protected WsOrdersProducts value;

            /**
             * Gets the value of the key property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getKey() {
                return key;
            }

            /**
             * Sets the value of the key property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setKey(Integer value) {
                this.key = value;
            }

            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link WsOrdersProducts }
             *     
             */
            public WsOrdersProducts getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link WsOrdersProducts }
             *     
             */
            public void setValue(WsOrdersProducts value) {
                this.value = value;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="entry" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
     *                   &lt;element name="value" type="{http://osc.service.web/}wsProducts" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "entry"
    })
    public static class MapWsProducts {

        protected List<ClassWebService.MapWsProducts.Entry> entry;

        /**
         * Gets the value of the entry property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the entry property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEntry().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ClassWebService.MapWsProducts.Entry }
         * 
         * 
         */
        public List<ClassWebService.MapWsProducts.Entry> getEntry() {
            if (entry == null) {
                entry = new ArrayList<ClassWebService.MapWsProducts.Entry>();
            }
            return this.entry;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
         *         &lt;element name="value" type="{http://osc.service.web/}wsProducts" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "key",
            "value"
        })
        public static class Entry {

            protected Integer key;
            protected WsProducts value;

            /**
             * Gets the value of the key property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getKey() {
                return key;
            }

            /**
             * Sets the value of the key property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setKey(Integer value) {
                this.key = value;
            }

            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link WsProducts }
             *     
             */
            public WsProducts getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link WsProducts }
             *     
             */
            public void setValue(WsProducts value) {
                this.value = value;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="entry" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
     *                   &lt;element name="value" type="{http://osc.service.web/}wsZone" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "entry"
    })
    public static class MapWsZone {

        protected List<ClassWebService.MapWsZone.Entry> entry;

        /**
         * Gets the value of the entry property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the entry property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEntry().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ClassWebService.MapWsZone.Entry }
         * 
         * 
         */
        public List<ClassWebService.MapWsZone.Entry> getEntry() {
            if (entry == null) {
                entry = new ArrayList<ClassWebService.MapWsZone.Entry>();
            }
            return this.entry;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
         *         &lt;element name="value" type="{http://osc.service.web/}wsZone" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "key",
            "value"
        })
        public static class Entry {

            protected Integer key;
            protected WsZone value;

            /**
             * Gets the value of the key property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getKey() {
                return key;
            }

            /**
             * Sets the value of the key property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setKey(Integer value) {
                this.key = value;
            }

            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link WsZone }
             *     
             */
            public WsZone getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link WsZone }
             *     
             */
            public void setValue(WsZone value) {
                this.value = value;
            }

        }

    }

}

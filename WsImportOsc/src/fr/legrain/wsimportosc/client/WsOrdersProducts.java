
package fr.legrain.wsimportosc.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for wsOrdersProducts complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wsOrdersProducts">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="finalPrice" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="ordersId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ordersProductsId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="productsId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="productsModel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="productsName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="productsPrice" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="productsQuantity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="productsTax" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wsOrdersProducts", propOrder = {
    "finalPrice",
    "ordersId",
    "ordersProductsId",
    "productsId",
    "productsModel",
    "productsName",
    "productsPrice",
    "productsQuantity",
    "productsTax"
})
public class WsOrdersProducts {

    protected float finalPrice;
    protected int ordersId;
    protected int ordersProductsId;
    protected int productsId;
    protected String productsModel;
    protected String productsName;
    protected float productsPrice;
    protected int productsQuantity;
    protected float productsTax;

    /**
     * Gets the value of the finalPrice property.
     * 
     */
    public float getFinalPrice() {
        return finalPrice;
    }

    /**
     * Sets the value of the finalPrice property.
     * 
     */
    public void setFinalPrice(float value) {
        this.finalPrice = value;
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
     * Gets the value of the ordersProductsId property.
     * 
     */
    public int getOrdersProductsId() {
        return ordersProductsId;
    }

    /**
     * Sets the value of the ordersProductsId property.
     * 
     */
    public void setOrdersProductsId(int value) {
        this.ordersProductsId = value;
    }

    /**
     * Gets the value of the productsId property.
     * 
     */
    public int getProductsId() {
        return productsId;
    }

    /**
     * Sets the value of the productsId property.
     * 
     */
    public void setProductsId(int value) {
        this.productsId = value;
    }

    /**
     * Gets the value of the productsModel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductsModel() {
        return productsModel;
    }

    /**
     * Sets the value of the productsModel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductsModel(String value) {
        this.productsModel = value;
    }

    /**
     * Gets the value of the productsName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductsName() {
        return productsName;
    }

    /**
     * Sets the value of the productsName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductsName(String value) {
        this.productsName = value;
    }

    /**
     * Gets the value of the productsPrice property.
     * 
     */
    public float getProductsPrice() {
        return productsPrice;
    }

    /**
     * Sets the value of the productsPrice property.
     * 
     */
    public void setProductsPrice(float value) {
        this.productsPrice = value;
    }

    /**
     * Gets the value of the productsQuantity property.
     * 
     */
    public int getProductsQuantity() {
        return productsQuantity;
    }

    /**
     * Sets the value of the productsQuantity property.
     * 
     */
    public void setProductsQuantity(int value) {
        this.productsQuantity = value;
    }

    /**
     * Gets the value of the productsTax property.
     * 
     */
    public float getProductsTax() {
        return productsTax;
    }

    /**
     * Sets the value of the productsTax property.
     * 
     */
    public void setProductsTax(float value) {
        this.productsTax = value;
    }

}

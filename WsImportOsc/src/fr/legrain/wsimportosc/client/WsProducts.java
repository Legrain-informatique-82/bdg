
package fr.legrain.wsimportosc.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for wsProducts complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wsProducts">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="productsCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="productsId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="productsName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="productsQuantity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wsProducts", propOrder = {
    "productsCode",
    "productsId",
    "productsName",
    "productsQuantity"
})
public class WsProducts {

    protected String productsCode;
    protected int productsId;
    protected String productsName;
    protected int productsQuantity;

    /**
     * Gets the value of the productsCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductsCode() {
        return productsCode;
    }

    /**
     * Sets the value of the productsCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductsCode(String value) {
        this.productsCode = value;
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

}


package fr.legrain.wsimportosc.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for wsZone complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wsZone">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="countriesId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="zoneCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="zoneId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="zoneName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wsZone", propOrder = {
    "countriesId",
    "zoneCode",
    "zoneId",
    "zoneName"
})
public class WsZone {

    protected int countriesId;
    protected String zoneCode;
    protected int zoneId;
    protected String zoneName;

    /**
     * Gets the value of the countriesId property.
     * 
     */
    public int getCountriesId() {
        return countriesId;
    }

    /**
     * Sets the value of the countriesId property.
     * 
     */
    public void setCountriesId(int value) {
        this.countriesId = value;
    }

    /**
     * Gets the value of the zoneCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZoneCode() {
        return zoneCode;
    }

    /**
     * Sets the value of the zoneCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZoneCode(String value) {
        this.zoneCode = value;
    }

    /**
     * Gets the value of the zoneId property.
     * 
     */
    public int getZoneId() {
        return zoneId;
    }

    /**
     * Sets the value of the zoneId property.
     * 
     */
    public void setZoneId(int value) {
        this.zoneId = value;
    }

    /**
     * Gets the value of the zoneName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZoneName() {
        return zoneName;
    }

    /**
     * Sets the value of the zoneName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZoneName(String value) {
        this.zoneName = value;
    }

}

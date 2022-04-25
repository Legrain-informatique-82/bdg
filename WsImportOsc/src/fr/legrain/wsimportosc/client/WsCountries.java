
package fr.legrain.wsimportosc.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for wsCountries complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="wsCountries">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="countriesId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="countriesIsoCode2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="countriesIsoCode3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="countriesName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wsCountries", propOrder = {
    "countriesId",
    "countriesIsoCode2",
    "countriesIsoCode3",
    "countriesName"
})
public class WsCountries {

    protected int countriesId;
    protected String countriesIsoCode2;
    protected String countriesIsoCode3;
    protected String countriesName;

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
     * Gets the value of the countriesIsoCode2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountriesIsoCode2() {
        return countriesIsoCode2;
    }

    /**
     * Sets the value of the countriesIsoCode2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountriesIsoCode2(String value) {
        this.countriesIsoCode2 = value;
    }

    /**
     * Gets the value of the countriesIsoCode3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountriesIsoCode3() {
        return countriesIsoCode3;
    }

    /**
     * Sets the value of the countriesIsoCode3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountriesIsoCode3(String value) {
        this.countriesIsoCode3 = value;
    }

    /**
     * Gets the value of the countriesName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountriesName() {
        return countriesName;
    }

    /**
     * Sets the value of the countriesName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountriesName(String value) {
        this.countriesName = value;
    }

}

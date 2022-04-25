
package fr.legrain.changelog.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour Category complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="Category">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="categories" type="{http://dev.changelog.fr/ws/changelogApi/1.0/}ArrayOfSubCategory"/>
 *         &lt;element name="articles" type="{http://dev.changelog.fr/ws/changelogApi/1.0/}ArrayOfArticle"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Category", propOrder = {

})
public class Category {

    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer id;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfSubCategory categories;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfArticle articles;

    /**
     * Obtient la valeur de la propriété id.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getId() {
        return id;
    }

    /**
     * Définit la valeur de la propriété id.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setId(Integer value) {
        this.id = value;
    }

    /**
     * Obtient la valeur de la propriété name.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Définit la valeur de la propriété name.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Obtient la valeur de la propriété categories.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSubCategory }
     *     
     */
    public ArrayOfSubCategory getCategories() {
        return categories;
    }

    /**
     * Définit la valeur de la propriété categories.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSubCategory }
     *     
     */
    public void setCategories(ArrayOfSubCategory value) {
        this.categories = value;
    }

    /**
     * Obtient la valeur de la propriété articles.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfArticle }
     *     
     */
    public ArrayOfArticle getArticles() {
        return articles;
    }

    /**
     * Définit la valeur de la propriété articles.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfArticle }
     *     
     */
    public void setArticles(ArrayOfArticle value) {
        this.articles = value;
    }

}

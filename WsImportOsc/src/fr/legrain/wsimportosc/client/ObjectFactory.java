
package fr.legrain.wsimportosc.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the fr.legrain.wsimportosc.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _EnvoyerInfosResponse_QNAME = new QName("http://osc.service.web/", "envoyerInfosResponse");
    private final static QName _EnvoyerInfos_QNAME = new QName("http://osc.service.web/", "envoyerInfos");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: fr.legrain.wsimportosc.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link WsAddressBook }
     * 
     */
    public WsAddressBook createWsAddressBook() {
        return new WsAddressBook();
    }

    /**
     * Create an instance of {@link ClassWebService.MapWsProducts }
     * 
     */
    public ClassWebService.MapWsProducts createClassWebServiceMapWsProducts() {
        return new ClassWebService.MapWsProducts();
    }

    /**
     * Create an instance of {@link ClassWebService.MapWsCountries }
     * 
     */
    public ClassWebService.MapWsCountries createClassWebServiceMapWsCountries() {
        return new ClassWebService.MapWsCountries();
    }

    /**
     * Create an instance of {@link EnvoyerInfosResponse }
     * 
     */
    public EnvoyerInfosResponse createEnvoyerInfosResponse() {
        return new EnvoyerInfosResponse();
    }

    /**
     * Create an instance of {@link ClassWebService.MapWsProducts.Entry }
     * 
     */
    public ClassWebService.MapWsProducts.Entry createClassWebServiceMapWsProductsEntry() {
        return new ClassWebService.MapWsProducts.Entry();
    }

    /**
     * Create an instance of {@link ClassWebService.MapWsCustomers.Entry }
     * 
     */
    public ClassWebService.MapWsCustomers.Entry createClassWebServiceMapWsCustomersEntry() {
        return new ClassWebService.MapWsCustomers.Entry();
    }

    /**
     * Create an instance of {@link ClassWebService.MapWsOrdersProducts.Entry }
     * 
     */
    public ClassWebService.MapWsOrdersProducts.Entry createClassWebServiceMapWsOrdersProductsEntry() {
        return new ClassWebService.MapWsOrdersProducts.Entry();
    }

    /**
     * Create an instance of {@link WsCustomers }
     * 
     */
    public WsCustomers createWsCustomers() {
        return new WsCustomers();
    }

    /**
     * Create an instance of {@link ClassWebService.MapWsAddressBook }
     * 
     */
    public ClassWebService.MapWsAddressBook createClassWebServiceMapWsAddressBook() {
        return new ClassWebService.MapWsAddressBook();
    }

    /**
     * Create an instance of {@link ClassWebService.MapWsOrders.Entry }
     * 
     */
    public ClassWebService.MapWsOrders.Entry createClassWebServiceMapWsOrdersEntry() {
        return new ClassWebService.MapWsOrders.Entry();
    }

    /**
     * Create an instance of {@link WsOrders }
     * 
     */
    public WsOrders createWsOrders() {
        return new WsOrders();
    }

    /**
     * Create an instance of {@link ClassWebService.MapWsZone }
     * 
     */
    public ClassWebService.MapWsZone createClassWebServiceMapWsZone() {
        return new ClassWebService.MapWsZone();
    }

    /**
     * Create an instance of {@link WsZone }
     * 
     */
    public WsZone createWsZone() {
        return new WsZone();
    }

    /**
     * Create an instance of {@link ClassWebService.MapWsAddressBook.Entry }
     * 
     */
    public ClassWebService.MapWsAddressBook.Entry createClassWebServiceMapWsAddressBookEntry() {
        return new ClassWebService.MapWsAddressBook.Entry();
    }

    /**
     * Create an instance of {@link WsOrdersProducts }
     * 
     */
    public WsOrdersProducts createWsOrdersProducts() {
        return new WsOrdersProducts();
    }

    /**
     * Create an instance of {@link WsProducts }
     * 
     */
    public WsProducts createWsProducts() {
        return new WsProducts();
    }

    /**
     * Create an instance of {@link WsCountries }
     * 
     */
    public WsCountries createWsCountries() {
        return new WsCountries();
    }

    /**
     * Create an instance of {@link ClassWebService.MapWsOrdersProducts }
     * 
     */
    public ClassWebService.MapWsOrdersProducts createClassWebServiceMapWsOrdersProducts() {
        return new ClassWebService.MapWsOrdersProducts();
    }

    /**
     * Create an instance of {@link ClassWebService.MapWsOrders }
     * 
     */
    public ClassWebService.MapWsOrders createClassWebServiceMapWsOrders() {
        return new ClassWebService.MapWsOrders();
    }

    /**
     * Create an instance of {@link ClassWebService.MapWsCountries.Entry }
     * 
     */
    public ClassWebService.MapWsCountries.Entry createClassWebServiceMapWsCountriesEntry() {
        return new ClassWebService.MapWsCountries.Entry();
    }

    /**
     * Create an instance of {@link EnvoyerInfos }
     * 
     */
    public EnvoyerInfos createEnvoyerInfos() {
        return new EnvoyerInfos();
    }

    /**
     * Create an instance of {@link ClassWebService }
     * 
     */
    public ClassWebService createClassWebService() {
        return new ClassWebService();
    }

    /**
     * Create an instance of {@link ClassWebService.MapWsZone.Entry }
     * 
     */
    public ClassWebService.MapWsZone.Entry createClassWebServiceMapWsZoneEntry() {
        return new ClassWebService.MapWsZone.Entry();
    }

    /**
     * Create an instance of {@link ClassWebService.MapWsCustomers }
     * 
     */
    public ClassWebService.MapWsCustomers createClassWebServiceMapWsCustomers() {
        return new ClassWebService.MapWsCustomers();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnvoyerInfosResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://osc.service.web/", name = "envoyerInfosResponse")
    public JAXBElement<EnvoyerInfosResponse> createEnvoyerInfosResponse(EnvoyerInfosResponse value) {
        return new JAXBElement<EnvoyerInfosResponse>(_EnvoyerInfosResponse_QNAME, EnvoyerInfosResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnvoyerInfos }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://osc.service.web/", name = "envoyerInfos")
    public JAXBElement<EnvoyerInfos> createEnvoyerInfos(EnvoyerInfos value) {
        return new JAXBElement<EnvoyerInfos>(_EnvoyerInfos_QNAME, EnvoyerInfos.class, null, value);
    }

}

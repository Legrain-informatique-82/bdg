package fr.legrain.changelog.ws;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.11
 * 2015-11-20T14:23:46.148+01:00
 * Generated source version: 2.7.11
 * 
 */
@WebServiceClient(name = "changelogApiService", 
                  wsdlLocation = "http://changelog.legrain.fr/ws/changelogApi?wsdl",
                  targetNamespace = "http://dev.changelog.fr/ws/changelogApi/1.0/") 
public class ChangelogApiService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://dev.changelog.fr/ws/changelogApi/1.0/", "changelogApiService");
    public final static QName ChangelogApiPort = new QName("http://dev.changelog.fr/ws/changelogApi/1.0/", "changelogApiPort");
    static {
        URL url = null;
        try {
            url = new URL("http://changelog.legrain.fr/ws/changelogApi?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(ChangelogApiService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://changelog.legrain.fr/ws/changelogApi?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public ChangelogApiService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public ChangelogApiService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ChangelogApiService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
//    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
//    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
//    //compliant code instead.
//    public ChangelogApiService(WebServiceFeature ... features) {
//        super(WSDL_LOCATION, SERVICE, features);
//    }
//
//    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
//    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
//    //compliant code instead.
//    public ChangelogApiService(URL wsdlLocation, WebServiceFeature ... features) {
//        super(wsdlLocation, SERVICE, features);
//    }
//
//    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
//    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
//    //compliant code instead.
//    public ChangelogApiService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
//        super(wsdlLocation, serviceName, features);
//    }

    /**
     *
     * @return
     *     returns ChangelogApiPortType
     */
    @WebEndpoint(name = "changelogApiPort")
    public ChangelogApiPortType getChangelogApiPort() {
        return super.getPort(ChangelogApiPort, ChangelogApiPortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ChangelogApiPortType
     */
    @WebEndpoint(name = "changelogApiPort")
    public ChangelogApiPortType getChangelogApiPort(WebServiceFeature... features) {
        return super.getPort(ChangelogApiPort, ChangelogApiPortType.class, features);
    }

}

package fr.legrain.prestashop.ws.test;


import java.io.InputStream;
import java.io.StringWriter;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;

import fr.legrain.prestashop.ws.entities.Addresses;
import fr.legrain.prestashop.ws.entities.Prestashop;


//import com.sun.jersey.api.client.Client;
//import com.sun.jersey.api.client.WebResource;
//import com.sun.jersey.api.client.config.ClientConfig;
//import com.sun.jersey.api.client.config.DefaultClientConfig;
//import com.sun.jersey.core.util.Base64;


public class ClientApacheCXF {
	
	//private String baseURI = "http://promethee.biz/prestaEb/api";
	private String baseURI = "http://localhost/prestashop/api";
	//private String baseURI = "http://192.168.1.35/aptana/prestaEb/api";
	private String cle = "JILABJDCX20P4M19CU1BWJFJ4D4XHOYA";
	
	public void testClient() {
		try {
			WebClient client = WebClient.create(baseURI);
			
			
			String authorizationHeader = "Basic " + org.apache.cxf.common.util.Base64Utility.encode((cle+":password").getBytes());

			// proxies
			//WebClient.client(proxy).header("Authorization", authorizationHeader);
			// web clients
			client.header("Authorization", authorizationHeader);
		
			/*
			Response r = client.path("/").accept("text/xml").get();
			InputStream is = (InputStream)r.getEntity();
			//System.err.println(r.getEntity());
			//System.err.println(is.);
			*/ 
			
			
			Addresses a = client.path("/addresses/2").accept("text/xml").get(Addresses.class);
			//Prestashop p = client.path("/addresses/1").accept("text/xml").get(Prestashop.class);
			System.out.println("get ok");
			
			a.setLastname("test");
			
			a.setAddress1("test 44");
			
			
			 StringWriter sw = new StringWriter();
		        
		        Marshaller marshaller = JAXBContext.newInstance(Addresses.class).createMarshaller();
		        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		        marshaller.marshal(a, sw);
		        
		        client.header("Content-Type", "application/x-www-form-urlencoded");
		        //client.header("Content-Type", "text/xml");
		        
		        Response r = client.path("/addresses/2").put(sw.toString().replace("\n", ""));
		        System.out.println("Put : "+sw.toString());
			System.out.println("put return code : " +r.getStatus());

			
			//System.out.println(s);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	
}

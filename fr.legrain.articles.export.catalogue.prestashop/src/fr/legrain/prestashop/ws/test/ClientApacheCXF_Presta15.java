package fr.legrain.prestashop.ws.test;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.ext.form.Form;
import org.eclipse.ui.PlatformUI;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import fr.legrain.prestashop.ws.UtilHTTP;
import fr.legrain.prestashop.ws.WSPrestashopProgress;
import fr.legrain.prestashop.ws.entities.Addresses;
import fr.legrain.prestashop.ws.entities.Language;
import fr.legrain.prestashop.ws.entities.Products;
import fr.legrain.prestashop.ws.entities.States;


public class ClientApacheCXF_Presta15 {
	
	//private String baseURI = "http://promethee.biz/prestaEb/api";
	private String baseURI = "http://dev2.pageweb.fr/api";
	//login : julien@legrain.fr
	//pass  : pwdlegrain
	
	//install 1.5.4 => mettre en commentaire la ligne 50 de classes/ConfigurationTest.php (concernant les sessions)
	//base : dev2_pageweb
	//user : lgr2
	//pass : legrain
	
	/*
	 curl 
	 -X POST 
	 -u 'NAEVYYHRN00WH0SS0G87RDE550OL9V92:' 
	 -d xml="<?xml version="1.0" encoding="UTF-8"?><prestashop><out_of_stock>0</out_of_stock><price>1.0</price><quantity>0</quantity><meta_description/><meta_keywords/><meta_title/><link_rewrite><language id="1">produit_test</language></link_rewrite><name><language id="1">Produit test</language></name><available_now/><available_later/><description/><description_short/><associations><categories node_type="category"/><images node_type="image"/><combinations node_type="combinations"/><product_option_values node_type="product_option_values"/><product_features node_type="product_feature"/></associations></prestashop>" 
	 http://dev2.pageweb.fr/api
	 */
	
	/*
	 config/config.inv.php
	 //@ini_set('display_errors', 'on');
     //define('_PS_DEBUG_SQL_', true);
	 */
	
	//private String baseURI = "http://192.168.1.35/aptana/prestaEb/api";
	private String cle = "NAEVYYHRN00WH0SS0G87RDE550OL9V92";
	
	public void testClient() {
		try {
//			Client c = Client.create();
//			WebResource client = c.resource(baseURI);
			
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
			
			//testAdressePost(client);
//			testAdresseGet(client);
			//testAdressePut(client);
			
			//testAdresseJersey();
//			testAdresse(c);
			
			//testAdresseApacheHttp();
			//testProductApacheHttp();
			testWS();

			
			//System.out.println(s);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testWS() throws Exception{
//		WSPrestashop ws = new WSPrestashop();
//		ws.exportProdcut(null);
		
		WSPrestashopProgress wsp = new WSPrestashopProgress(WSPrestashopProgress.TYPE_EXPORT, null,false);
		//WSPrestashopProgress wsp = new WSPrestashopProgress(WSPrestashopProgress.TYPE_IMPORT);
		PlatformUI.getWorkbench().getProgressService().run(false, false, wsp);
	}
	
	public void testProductApacheHttp() throws Exception{
		String strURL = baseURI+"/products/";
		UtilHTTP.setHostname("dev2.pageweb.fr");
		UtilHTTP.setUser(cle);
		UtilHTTP.setPassword("");
		
		Products newProduct = new Products();
		//Prestashop p = client.path("/addresses/1").accept("text/xml").get(Prestashop.class);

		//newProduct.setId(0);
		List<Language> listeName = new ArrayList<Language>();
		listeName.add(new Language(1,"Produit test"));
		//newProduct.setName("Produit test");
		newProduct.setName(listeName);
		
		List<Language> listeLinkRewrite = new ArrayList<Language>();
		listeLinkRewrite.add(new Language(1,"produit_test"));
		//newProduct.setLinkRewrite("produit_test");
		newProduct.setLinkRewrite(listeLinkRewrite);
		
		//newProduct.setQuantity(0);
		newProduct.setPrice(1f);
		newProduct.setOutOfStock(0);


		StringWriter sw = new StringWriter();

		Marshaller marshaller = JAXBContext.newInstance(Products.class).createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		//marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(newProduct, sw);

		String input = sw.toString().replace("\n", "");
		
		input = input.replace("<id>0</id>", "");
		System.out.println(input);
		
		String s = UtilHTTP.post(strURL, "xml", input);
		System.out.println(s);
		
		Unmarshaller unmarshaller = JAXBContext.newInstance(Products.class).createUnmarshaller();
		//unmarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		StringReader reader = new StringReader(s);
		Products newProducReturn = (Products) unmarshaller.unmarshal(reader);
		
		System.out.println(newProducReturn.getId());
		//newProducReturn.setQuantity(100);
		newProducReturn.getName().get(0).setValue("article "+newProducReturn.getId());
		
		StringWriter sw2 = new StringWriter();
		//newProducReturn.cleanLanguageElements();
		//newProducReturn.setQuantity(null); //ce champ n'est pas accessible en écriture par les webservices
		marshaller.marshal(newProducReturn, sw2);
		String input2 = sw2.toString().replace("\n", "");
		System.out.println(input2);
		//UtilHTTP.put(strURL/*+"3"*/, "xml", input2 );	
		UtilHTTP.put(strURL/*+"3"*/ /*+newProducReturn.getId()*/, "xml", input2 );
		//System.out.println(s2);
		
		/*
	<?xml version="1.0" encoding="UTF-8"?><prestashop><out_of_stock>0</out_of_stock><price>1.0</price><quantity>0</quantity><meta_description/><meta_keywords/><meta_title/><link_rewrite><language id="1">produit_test</language></link_rewrite><name><language id="1">Produit test</language></name><available_now/><available_later/><description/><description_short/><associations><categories node_type="category"/><images node_type="image"/><combinations node_type="combinations"/><product_option_values node_type="product_option_values"/><product_features node_type="product_feature"/></associations></prestashop>

		 */
		
		//===== Lecture: GET ======
//		String result = UtilHTTP.get(strURL+"3");
//		//String result = UtilHTTP.get(strURL);
//		System.out.println("== Réponse GET ==");
//		System.out.println(result);
		
	}
	
	public void testAdresseApacheHttp() throws Exception{
		String strURL = baseURI+"/addresses/";
		UtilHTTP.setHostname("dev2.pageweb.fr");
		UtilHTTP.setUser(cle);
		UtilHTTP.setPassword("");

		//===== Ajout: POST ======
		Addresses newAddress = new Addresses();
		//Prestashop p = client.path("/addresses/1").accept("text/xml").get(Prestashop.class);

		newAddress.setId(0);
		newAddress.setIdCustomer(1);
		newAddress.setIdCountry(8);
		newAddress.setAlias("test2");
		newAddress.setLastname("test last");
		newAddress.setFirstname("test first");
		newAddress.setAddress1("test 44");
		newAddress.setPostcode("31000");
		newAddress.setCity("tls");
		
		//newAddress.setIdState(new States());

		StringWriter sw = new StringWriter();

		Marshaller marshaller = JAXBContext.newInstance(Addresses.class).createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		marshaller.marshal(newAddress, sw);

		String input = sw.toString().replace("\n", "");
		
//		input = "<prestashop><address><id_customer>1</id_customer><id_manufacturer>0</id_manufacturer><id_supplier>0</id_supplier><id_country>8</id_country>" +
//				"<id_state>0</id_state><alias>test2</alias><lastname>test last</lastname><firstname>test first</firstname><address1>test 44</address1><postcode>31000</postcode>" +
//				"<city>tls</city><deleted>0</deleted></address></prestashop>";
		//input = input.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
		input = input.replace("<id>0</id>", "");
		System.out.println(input);
		
		//UtilHTTP.post(strURL, "xml", input);
		
		//===== Lecture: GET ======
		String result = UtilHTTP.get(strURL+"3");
		//String result = UtilHTTP.get(strURL);
		System.out.println("== Réponse GET ==");
		System.out.println(result);
		
		Unmarshaller unmarshaller = JAXBContext.newInstance(Addresses.class).createUnmarshaller();
		//unmarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		StringReader reader = new StringReader(result);
		Addresses a2 = (Addresses) unmarshaller.unmarshal(reader);

		System.out.println("Add 2 : "+a2.getAddress1());
		//System.out.println("state : "+a2.getIdState().getName());
		
		//===== Modification: PUT ======
		a2.setAddress1("azertyuiop");
		StringWriter sw2 = new StringWriter();
		marshaller.marshal(a2, sw2);
		String input2 = sw2.toString().replace("\n", "");
		//input2 = input2.replace("<id>0</id>","" /*"<id>2</id>"*/);
		System.out.println(input2);
		UtilHTTP.put(strURL/*+"3"*/, "xml", input2 );	
		
		//===== Suppression: DELETE ======
		//UtilHTTP.delete(strURL+"7");	
		
		
		//===== Ajout image article: POST ======
		String img = "/home/nicolas/echappee_bio/echappee bio/images_test_echapeebio/img_article/6h.jpg";
		File file = new File(img);
		String urlImage = baseURI+"/images/products/1";
		//UtilHTTP.put(urlImage, "image", input2 );
		Map<String, String> param = new HashMap<String, String>();
		param.put("Content-Type", "image/jpeg");
		param.put("Content-Length", "" + file.length());
		
		BufferedInputStream b;
		InputStream f =  new BufferedInputStream(new FileInputStream(file));
		byte[] r = readAndClose(f);
		
		param.put("image", new String(r));
		//UtilHTTP.post(urlImage, param );
		UtilHTTP.postImage(urlImage, param, file);
		
	}
	
	/**
	   Read an input stream, and return it as a byte array.  
	   Sometimes the source of bytes is an input stream instead of a file. 
	   This implementation closes aInput after it's read.
	  */
	  byte[] readAndClose(InputStream aInput){
	    //carries the data from input to output :    
	    byte[] bucket = new byte[32*1024]; 
	    ByteArrayOutputStream result = null; 
	    try  {
	      try {
	        //Use buffering? No. Buffering avoids costly access to disk or network;
	        //buffering to an in-memory stream makes no sense.
	        result = new ByteArrayOutputStream(bucket.length);
	        int bytesRead = 0;
	        while(bytesRead != -1){
	          //aInput.read() returns -1, 0, or more :
	          bytesRead = aInput.read(bucket);
	          if(bytesRead > 0){
	            result.write(bucket, 0, bytesRead);
	          }
	        }
	      }
	      finally {
	        aInput.close();
	        //result.close(); this is a no-operation for ByteArrayOutputStream
	      }
	    }
	    catch (IOException ex){
	      ex.printStackTrace();
	    }
	    return result.toByteArray();
	  }
	
	public void testAdresseJersey() throws Exception{
		Client c = Client.create();
		c.addFilter(new HTTPBasicAuthFilter(cle, ""));
		
		WebResource webResource = c.resource(baseURI+"/addresses/");
		
		Addresses a = new Addresses();
		//Prestashop p = client.path("/addresses/1").accept("text/xml").get(Prestashop.class);
		System.out.println("get ok");

		//a.setId(3);
		a.setIdCustomer(1);
		a.setIdCountry(8);
		a.setAlias("test2");
		a.setLastname("test last");
		a.setFirstname("test first");
		a.setAddress1("test 44");
		a.setPostcode("31000");
		a.setCity("tls");

		StringWriter sw = new StringWriter();

		Marshaller marshaller = JAXBContext.newInstance(Addresses.class).createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		marshaller.marshal(a, sw);

		String input = sw.toString().replace("\n", "");

		System.out.println(input);
		//ClientResponse response = webResource.type("application/xml").post(ClientResponse.class, input);

		MultivaluedMap formData = new MultivaluedMapImpl();
		formData.add("xml", input);
		//formData.add("resource", /*baseURI+*/"/addresses");
		ClientResponse response = webResource.type("application/x-www-form-urlencoded").post(ClientResponse.class, formData);

		if (response.getStatus() != 201) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}

		System.out.println("Output from Server .... \n");
		String output = response.getEntity(String.class);
		System.out.println(output);
	}
	
	public void testAdresseGet(WebClient client) throws Exception{
			//Addresses a = client.path("/addresses/1").accept("text/xml").get(Addresses.class);
			//System.out.println("Alias : "+a.getAlias());
			
			Response s = client.path("/addresses/1").get();
			System.out.println("XML : "+s);
	}

	
	public void testAdressePost(WebClient client) throws Exception{
//	public void testAdresse(Client c) throws Exception{
//		WebResource client = c.resource(baseURI);
		
		//Addresses a = client.path("/addresses/2").accept("text/xml").get(Addresses.class);
		Addresses a = new Addresses();
		//Prestashop p = client.path("/addresses/1").accept("text/xml").get(Prestashop.class);
		System.out.println("get ok");

		a.setIdCustomer(1);
		a.setIdCountry(8);
		a.setAlias("test2");
		a.setLastname("test last");
		a.setFirstname("test first");
		a.setAddress1("test 44");
		a.setPostcode("31000");
		a.setCity("tls");

		StringWriter sw = new StringWriter();

		Marshaller marshaller = JAXBContext.newInstance(Addresses.class).createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		marshaller.marshal(a, sw);

		//client.header("Content-Type", "application/x-www-form-urlencoded");
		//client.header("Content-Type", "text/xml");
		//http://maboutique.com/api/customers?schema=blank
			//client.path("/addresses/");
	
		Response r = null;
		//r = client.path("/addresses/").post(sw.toString().replace("\n", ""));
		//r = client.path(baseURI+"/addresses/").post("xml="+sw.toString().replace("\n", ""));
		client.path(baseURI+"/addresses/");
		
		Form f = new Form();
		f.set("resource", "/addresses");
		f.set("xml", sw.toString().replace("\n", ""));
		r = client.form(f);
		
//		List<Attachment> atts = new LinkedList<Attachment>(); 
//	    //atts.add(new Attachment("resource", "text/xml", t1)); 
//	    atts.add(new Attachment("xml", MediaType.TEXT_XML, sw.toString().replace("\n", ""))); 
//	    r = client.postCollection(atts, Attachment.class);
//	    System.out.println(r.getStatus());

		
//		c.resource("http://dev2.pageweb.fr/api/addresses");
//		Form f = new Form();
//		f.add("resource", "/addresses");
//		f.add("xml", sw.toString().replace("\n", ""));
//		client.
//				type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
//				.accept(MediaType.APPLICATION_XML_TYPE)
//				.post(String.class, f);
		 
		System.out.println("Put : "+sw.toString());
		System.out.println("put return code : " +r.getStatus());
	}
	
	public void testAdressePut(WebClient client) throws Exception{
		Addresses a = client.path("/addresses/1").accept("text/xml").get(Addresses.class);
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

		Response r = client.path("/addresses/1").put(sw.toString().replace("\n", ""));
		System.out.println("Put : "+sw.toString());
		System.out.println("put return code : " +r.getStatus());
	}

	
}

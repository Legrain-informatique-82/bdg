package fr.legrain.prestashop.ws;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.PlatformUI;

import fr.legrain.prestashop.ws.entities.Images;


public class WSPrestaUtil<T> {
	
	static Logger logger = Logger.getLogger(WSPrestaUtil.class.getName());
	
//	private String baseURI = "http://dev2.pageweb.fr/api";
//	private String cle = "NAEVYYHRN00WH0SS0G87RDE550OL9V92";
//	private String password = "";
//	private String hostName = "dev2.pageweb.fr";
	
	private String baseURI = null;
	private String cle = null;
	private String password = null;
	private String hostName = null;
	
	public UtilHTTP utilHTTP = null;
	
	private Marshaller marshaller;
	private Unmarshaller unmarshaller;
	
	private static Unmarshaller unmarshallerImage;
	
	public WSPrestaUtil(Class<T> t) {
		try {
			
			WSPrestashopConfig config = WSPrestashopConfig.getConfig();
			baseURI = config.getBaseURI();
			cle = config.getCle();
			password = config.getPassword();
			hostName = config.getHostName();
			
			marshaller = JAXBContext.newInstance(t).createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			//marshaller.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");

			unmarshaller = JAXBContext.newInstance(t).createUnmarshaller();
			if(unmarshallerImage==null) {
				unmarshallerImage = JAXBContext.newInstance(Images.class).createUnmarshaller();
			}
			//unmarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			UtilHTTP.setHostname(hostName);
			UtilHTTP.setUser(cle);
			UtilHTTP.setPassword(password);
		} catch (JAXBException e) {
			logger.error("",e);
		}
		
	}
	
	public void saveToFile(T object) throws JAXBException {
		marshaller.marshal(object, new File("/tmp/test.txt"));
	}
	
	/**
	 * WS - GET
	 * @param url
	 * @param id
	 * @return
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException 
	 */
	public T get(String url, int id) throws JAXBException {
		//===== Lecture: GET ======
		String result = UtilHTTP.get(url+"/"+id);
		System.out.println("== Réponse GET ==");
		System.out.println(result);
 
//		InputStream in = null;
//		try {
//			in = new ByteArrayInputStream(result.getBytes("UTF-8"));
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 

		StringReader reader = new StringReader(result);
		T a2 = (T) unmarshaller.unmarshal(reader);
//		T a2 = (T) unmarshaller.unmarshal(in);
		
		return a2;
	}
	
	public T getQuery(String url) throws JAXBException {
		//===== Lecture: GET ======
//		try {
//			url = URLEncoder.encode(url, "UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		String result = UtilHTTP.get(url);

		System.out.println("== Réponse GET ==");
		System.out.println(result);

		StringReader reader = new StringReader(result);
		T a2 = (T) unmarshaller.unmarshal(reader);
		
		return a2;
	}
	
	/**
	 * WS - POST
	 * @param url
	 * @param object
	 * @return
	 * @throws JAXBException
	 */
	public T create(String url, T object) throws JAXBException {
		StringWriter sw = new StringWriter();
		
		marshaller.marshal(object, sw);
		String input = sw.toString().replace("\n", "");
		input = input.replace("<id>0</id>", "");
		input = input.replace("%", "");
		
		System.out.println(input);
		String s = UtilHTTP.post(url, "xml", input);
		System.out.println(s);
		
		StringReader reader = new StringReader(s);
		T newProducReturn = (T) unmarshaller.unmarshal(reader);
		
		return newProducReturn;
	}
	
	/**
	 * WS - PUT
	 * @param url
	 * @param object
	 * @throws JAXBException
	 */
	public void update(String url, T object) throws JAXBException {
		StringWriter sw2 = new StringWriter();

		marshaller.marshal(object, sw2);
		String input2 = sw2.toString().replace("\n", "");
		System.out.println(input2);
		
		//UtilHTTP.put(strURL/*+"3"*/, "xml", input2 );	
		try {
			//UtilHTTP.put(url/*+"3"*/ /*+newProducReturn.getId()*/, "xml", new String(input2.getBytes("ISO-8859-1"),"UTF-8") );
			//UtilHTTP.put(url/*+"3"*/ /*+newProducReturn.getId()*/, "xml", new String(input2.getBytes("windows-1252"),"UTF-8") );
			//UtilHTTP.put(url/*+"3"*/ /*+newProducReturn.getId()*/, "xml", new String(input2.getBytes(),"UTF-8"));
			if(Platform.getOS().equals(Platform.OS_WIN32)) {
				UtilHTTP.put(url/*+"3"*/ /*+newProducReturn.getId()*/, "xml", new String(input2.getBytes(),"windows-1252"));
			} else {
				UtilHTTP.put(url/*+"3"*/ /*+newProducReturn.getId()*/, "xml", new String(input2.getBytes(),"UTF-8"));
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("",e);
		}
	}
	
	public Images createImageProduct(String url, String localFileImagePath) throws JAXBException, FileNotFoundException {
		File file = new File(localFileImagePath);
		if(file.exists()) {
			//String urlImage = getBaseURI()+"/images/products/"+idProduct;
			String urlImage = url;
			//UtilHTTP.put(urlImage, "image", input2 );
			Map<String, String> param = new HashMap<String, String>();
			param.put("Content-Type", "image/jpeg");
			param.put("Content-Length", "" + file.length());

			BufferedInputStream b;
			InputStream f =  new BufferedInputStream(new FileInputStream(file));
			byte[] r = readAndClose(f);

			//param.put("image", new String(r));
			//UtilHTTP.post(urlImage, param );
			String result = UtilHTTP.postImage(urlImage, param, file);
			
			StringReader reader = new StringReader(result);
			
			Images img = (Images) unmarshallerImage.unmarshal(reader);
			return img;
		} else {
			logger.error("FICHIER IMAGE ARTCILE INEXISTANT, CHEMIN INCORECT : "+localFileImagePath);
		}
		return null;
	}
	
	/**
	 * WS - DELETE
	 * @param url
	 * @param id
	 */
	public void delete(String url, int id) {
		//===== Suppression: DELETE ======
		UtilHTTP.delete(url+id);	
	}

	public String getBaseURI() {
		return baseURI;
	}

	public void setBaseURI(String baseURI) {
		this.baseURI = baseURI;
	}

	public String getCle() {
		return cle;
	}

	public void setCle(String cle) {
		this.cle = cle;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
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
			logger.error("",ex);
		}
		return result.toByteArray();
	}
	
}

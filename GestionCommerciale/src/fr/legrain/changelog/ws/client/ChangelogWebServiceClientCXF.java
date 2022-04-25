package fr.legrain.changelog.ws.client;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.FormDialog;

import fr.legrain.changelog.ws.Article;
import fr.legrain.changelog.ws.Category;
import fr.legrain.changelog.ws.ChangelogApiPortType;
import fr.legrain.changelog.ws.ChangelogApiService;
import fr.legrain.changelog.ws.SubCategory;


public class ChangelogWebServiceClientCXF {
	
	/**
	 * apache-cxf-2.7.11/bin$ ./wsdl2java -p fr.legrain.changelog.ws -d /donnees/Projet/Java/Eclipse/GestionCommerciale_branche_2_0_13/GestionCommerciale/src http://changelog.legrain.fr/ws/changelogApi?wsdl
	 * 
	 * Ajouter "extends ModelObject" à la classe Article si on veut l'utiliser dans le Databinding
	 * 
	 * Les dates PHP sont des timestamps UNIX en secondes, pour les convertir en date Java (millisecondes) il faut les multiplier par 1000l
	 * 
	 */
	
	private String hote = "localhost";
	private String port = "80";
	private String wsdlUrl = "";
	
	public void initWSClient() throws FileNotFoundException, IOException {
//		 String propertiesFileName = "bdg.properties";  
//	      
//		    Properties props = new Properties();  
//		    String path = System.getProperty("jboss.server.config.dir")+"/"+propertiesFileName;  
//		      
//		    if(new File(path).exists()) { 
//		    	File f = new File(path);
//		        props.load(new FileInputStream(f));  
//		        port = props.getProperty("webservice_autorisation.wsdl.port");
//		        hote = props.getProperty("webservice_autorisation.wsdl.host");
//		        wsdlUrl = props.getProperty("webservice_autorisation.wsdl.url");
//		    } else {  
		        //props.load(MyClass.class.getResourceAsStream("/" + propertiesFileName));
		        hote = "changelog.legrain.fr";
				port = "80";
				wsdlUrl = "http://"+hote+":"+port+"/ws/changelogApi?wsdl";
//		    }  
	}
	
	public Category changeLog() throws RemoteException {
		return changeLog(11); //ID pour BDG = 11
	}
	
	public Category changeLog(int idCategory) throws RemoteException {
		try {
			
			initWSClient();
		    
			URL newEndpoint = new URL(wsdlUrl);
			//QName qname = new QName("http://service.autorisations.legrain.fr/","TaAutorisationsServiceService"); 

//			TaAutorisationsServiceService service = new TaAutorisationsServiceService();
//			TaAutorisationsServiceService service = new TaAutorisationsServiceService(newEndpoint, qname);
			ChangelogApiService service = new ChangelogApiService(newEndpoint);

			ChangelogApiPortType servicePort = service.getChangelogApiPort();
			System.out.println("Call Web Service Operation...");

			Category c = servicePort.get(idCategory);
			
			return c;
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}

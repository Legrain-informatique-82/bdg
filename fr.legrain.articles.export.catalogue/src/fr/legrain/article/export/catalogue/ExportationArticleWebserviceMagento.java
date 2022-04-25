package fr.legrain.article.export.catalogue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import fr.legrain.article.export.catalogue.preferences.PreferenceConstants;
import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaArticleDAO;
import fr.legrain.articles.dao.TaCategorieArticle;
import fr.legrain.articles.dao.TaCategorieArticleDAO;
import fr.legrain.articles.dao.TaImageArticle;
import fr.legrain.articles.dao.TaLabelArticle;
import fr.legrain.articles.dao.TaLabelArticleDAO;
import fr.legrain.articles.dao.TaPrix;
import fr.legrain.articles.dao.TaTva;
import fr.legrain.articles.dao.TaTvaDAO;
import fr.legrain.articles.export.catalogue.magento.Magento;

public class ExportationArticleWebserviceMagento implements IExportationArticle {


	static Logger logger = Logger.getLogger(ExportationArticleWebserviceMagento.class.getName());

	private String fichierExportation = Platform.getInstanceLocation().getURL().getFile()+"/E2-IMPOR.TXT";

	private String finDeLigne = "\r\n";
	private String compteEscompte = "665";
	public static final int RE_EXPORT = 1;
	private String messageRetour="";
	private Boolean retour=true;
	private String locationFichier="";
	
	public ExportationArticleWebserviceMagento() {}

//	public void effaceFichierTexte(String chemin) {
//		File f = new File(chemin);
//		f.delete();
//	}
	
	public ResultatExportation exportationCatalogueWeb() {
		Magento m = new Magento();
		m.exportArticle();
		return null;
	}
	
//	public void declencheMAJSite() {
//		String authTokenName = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstants.URL_HTTP_TOKEN_AUTH);
//		String authTokenValue = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstants.URL_HTTP_TOKEN_AUTH_VALUE);
//		String urlString = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstants.URL_HTTP_MAJ_CATALOGUE);
//
//		try {
//			logger.debug("URL : "+urlString);
//			
//			// prepare post method  
//			HttpPost post = new HttpPost(urlString);
//
//			// add parameters to the post method  
//			List <NameValuePair> parameters = new ArrayList <NameValuePair>();  
//			parameters.add(new BasicNameValuePair(authTokenName, authTokenValue));  
//
//			UrlEncodedFormEntity sendentity = new UrlEncodedFormEntity(parameters, HTTP.UTF_8);  
//			post.setEntity(sendentity);   
//
//			// create the client and execute the post method  
//			HttpClient client = new DefaultHttpClient();  
//			HttpResponse response = client.execute(post);  
//
//			//retrieve the output and display it in console  
//			logger.debug(response.getStatusLine());
//			
//			client.getConnectionManager().shutdown();  
//		}catch (Exception e) {
//			logger.error("", e);
//		}
//
//	}

//	public void importWeb() {
//		String fichier = null;
//		if(Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstants.REP_LOC_FICHIER_EXPORT)!=null)
//			fichier = Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstants.REP_LOC_FICHIER_EXPORT)
//			+"/"+Activator.getDefault().getPreferenceStoreProject().getString(PreferenceConstants.NOM_FICHIER_EXPORT);
//
//		TaCategorieArticleDAO taCategorieArticleDAO = new TaCategorieArticleDAO();
//		List<TaCategorieArticle> listeCategorie = taCategorieArticleDAO.selectAll();
//		
//		try {
////			FileReader fr = null;
//			BufferedReader br = null;
////
////			fr = new FileReader(fichier);
////			File exportFile = new File(fichier);
////			br = new BufferedReader(fr);
//
//				//String encoding = "ISO-8859-1";
//				String encoding = "UTF-8";
//				FileInputStream fis = new FileInputStream(fichier);
//				InputStreamReader isr = new InputStreamReader(fis, encoding);
//				br = new BufferedReader(isr);
//
////				String ligne = br.readLine();
////	    
////				StringBuffer sb = new StringBuffer();
////	    		while (ligne!=null){
////	    			sb.append(ligne);
////	    			ligne = br.readLine();
////	    		}
////	    		
////	    		String jsonText = sb.toString();
////	    		System.out.println(jsonText);
//	    		
//	    		/////////////////////////////////////////////////////////////////////////
//
//	    		JSONParser parser = new JSONParser();
//	    		ContainerFactory containerFactory = new ContainerFactory(){
//	    			public List creatArrayContainer() {
//	    				return new LinkedList();
//	    			}
//
//	    			public Map createObjectContainer() {
//	    				return new LinkedHashMap();
//	    			}
//
//	    		};
//
//	    		try{
//	    			//Map json = (Map)parser.parse(jsonText, containerFactory);
//	    			Map json = (Map)parser.parse(isr, containerFactory);
//	    			Iterator iter = json.entrySet().iterator();
//	    			System.out.println("==iterate result==");
//	    			while(iter.hasNext()){
//	    				Map.Entry entry = (Map.Entry)iter.next();
//	    				System.out.println(entry.getKey() + "=>" + entry.getValue());
//	    			}
//
//	    			System.out.println("==toJSONString()==");
//	    			System.out.println(JSONValue.toJSONString(json));
//	    		}
//	    		catch(ParseException pe){
//	    			System.out.println(pe);
//	    		}
//
//			
//		} catch(Exception e) {
//			logger.error("", e);
//		}
//	}

	public String getMessageRetour() {
		return messageRetour;
	}

	public void setMessageRetour(String messageRetour) {
		this.messageRetour = messageRetour;
	}

	public String getLocationFichier() {
		return locationFichier;
	}

	public void setLocationFichier(String locationFichier) {
		this.locationFichier = locationFichier;
	}

	public Boolean getRetour() {
		return retour;
	}

	public void setRetour(Boolean retour) {
		this.retour = retour;
	}

	@Override
	public void transfert(IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void declencheMAJSite() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exportWS() {
		// TODO Auto-generated method stub
		
	}
}

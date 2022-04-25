package fr.legrain.article.export.catalogue.prestashop;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;

import fr.legrain.article.export.catalogue.IExportationArticle;
import fr.legrain.article.export.catalogue.ResultatExportation;

public class ExportationArticleWebservicePrestashop implements IExportationArticle {


	static Logger logger = Logger.getLogger(ExportationArticleWebservicePrestashop.class.getName());

	private String fichierExportation = Platform.getInstanceLocation().getURL().getFile()+"/E2-IMPOR.TXT";

	private String finDeLigne = "\r\n";
	private String compteEscompte = "665";
	public static final int RE_EXPORT = 1;
	private String messageRetour="";
	private Boolean retour=true;
	private String locationFichier="";
	
	public ExportationArticleWebservicePrestashop() {}

//	public void effaceFichierTexte(String chemin) {
//		File f = new File(chemin);
//		f.delete();
//	}
	
	public ResultatExportation exportationCatalogueWeb() {
//		Prestashop m = new Prestashop();
//		m.exportArticle();
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

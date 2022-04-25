package Interface;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import ControleAction.ClassTaDepFeature;
import ControleAction.ClassTaDepFeaturePlugin;
import ControleAction.ClassTaFeature;
import ControleAction.ClassTaPlugin;

public class AfficheAllRelation {
	
	
	//Element racine;
	private Connection conn;
	/*
	 * String ==> id of feature
	 * File ==> path of feature
	 */
	private Map<String, File> allFileFeature = new HashMap<String, File>();
	
	private List<ClassTaFeature> objetTaFeature = new ArrayList<ClassTaFeature>();
	private List<ClassTaPlugin>  objetTaPlugin = new ArrayList<ClassTaPlugin>();
	private List<ClassTaDepFeature>  objetTaDepFeature = new ArrayList<ClassTaDepFeature>();
	private List<ClassTaDepFeaturePlugin>  objetTaDepFeaturePlugin = new ArrayList<ClassTaDepFeaturePlugin>();
	
	private String qualifier;
	
	
	private String replaceQualifier(String version) {
		return version.replaceFirst(ConstProjet.QUALIFIER, qualifier);
	}
	
	public void newAffichageRelation(Map<String,File> allFeatureEclipse,Map<String,File> allFeatureBG){
		
		Set<String> SEEK_LABEL = new HashSet<String>();
		SEEK_LABEL.add(ConstProjet.INCLUDES);
		SEEK_LABEL.add(ConstProjet.REQUIRES);
		
		Map<String, Map<String, File>> features = new HashMap<String, Map<String,File>>();
		features.put(ConstProjet.TYPE_FEATURE_ECLIPSE, allFeatureEclipse);
		features.put(ConstProjet.TYPE_FEATURE_BG, allFeatureBG);
		
		for(String typeFeature : features.keySet()){
			//System.out.println(typeFeature);
			for(String nameFeature : features.get(typeFeature).keySet()){
				if(typeFeature.equalsIgnoreCase(ConstProjet.TYPE_FEATURE_ECLIPSE)){
					//System.out.println(nameFeature+"---"+features.get(typeFeature).get(nameFeature));
				}
				int id_feature = 0;
				//String nomFeature = null; //for new table relation 
				SAXBuilder sxb = new SAXBuilder();
				Document document;
				try {
					document = sxb.build(features.get(typeFeature).get(nameFeature));
					Element racine = document.getRootElement();
					
					String nom_feature = racine.getAttributeValue(ConstProjet.ID);
					String nom_label = racine.getAttributeValue(ConstProjet.LABEL);
					String version_feature = racine.getAttributeValue(ConstProjet.VERSION);
					//ajout du qualifer au numero de version des features legrain
					if(allFeatureBG.keySet().contains(nom_feature)) {
						version_feature = replaceQualifier(version_feature);
					}
					String provider_name = racine.getAttributeValue(ConstProjet.PROVIDER_NAME);
					String os = racine.getAttributeValue(ConstProjet.OS);
					String ws = racine.getAttributeValue(ConstProjet.WS);
					String nl = racine.getAttributeValue(ConstProjet.NL);
					String arch = racine.getAttributeValue(ConstProjet.ARCH);
					
					String urlDescription = valueUrl(racine, ConstProjet.DESCRIPTION);
					String urlCopyright = valueUrl(racine, ConstProjet.COPYRIGHT);
					String urlLicense = valueUrl(racine, ConstProjet.LICENSE);
					
					String updateLabel = null;
					String updateUrl = null;
					
					List listUrl = racine.getChildren(ConstProjet.URL);
					if(listUrl!=null){
						Iterator i = listUrl.iterator();
						while(i.hasNext()){
							Element courantlistUrl = (Element)i.next();
							
							for (int j = 0; j < courantlistUrl.getChildren().size(); j++) {
								Element childrencourantlistUrl =(Element) courantlistUrl.getChildren().get(j);
								updateLabel = childrencourantlistUrl.getAttributeValue(ConstProjet.LABEL);
								updateUrl = childrencourantlistUrl.getAttributeValue(ConstProjet.URL);		
							}
						}
					}else{
						updateLabel = null;
						updateUrl = null;
					}
					/**
					 * start add objet of feature in a list
					 */
					if(objetTaFeature.size()==0){
						id_feature = objetTaFeature.size()+1;
						ClassTaFeature CTF = new ClassTaFeature(id_feature,nom_feature,nom_label,version_feature,provider_name
								,os,ws,nl,arch,urlDescription,urlCopyright,urlLicense,updateLabel,updateUrl,typeFeature);
						objetTaFeature.add(CTF);
					}
					else{
						id_feature = verficationObjetTaFeature(objetTaFeature, nom_feature,nom_label,version_feature,provider_name
								,os,ws,nl,arch,urlDescription,urlCopyright,urlLicense,updateLabel,updateUrl,typeFeature);
						
//						verficationObjetTaFeature(objetTaFeature, nom_feature,nom_label,version_feature,provider_name
//								,os,ws,nl,arch,urlDescription,urlCopyright,urlLicense,updateLabel,updateUrl,typeFeature);
					}
					
					for (String label : SEEK_LABEL) {
						/*
						 * for each of the file(feature.xml)  find the label(<include>) 
						 */
						if(label.equalsIgnoreCase(ConstProjet.INCLUDES)){
//							List<Integer> idFeatureIncludes = analyseXmlIncludes(racine, label);
//							int idTaDepFeature = objetTaDepFeature.size()+1;
//							for (Integer idFeature : idFeatureIncludes) {
//								int idBaseFeature = id_feature;
//								int idDepFeature = idFeature;
//								ClassTaDepFeature CTDF = new ClassTaDepFeature(idTaDepFeature,idBaseFeature,idDepFeature);
//								objetTaDepFeature.add(CTDF);
//								idTaDepFeature++;
//							}
							List<String> nomFeatureIncludes = analyseXmlIncludes(racine, label);
							int idTaDepFeature = objetTaDepFeature.size()+1;
							for (String nomFeatureDep : nomFeatureIncludes) {
								String nom_feature_base = nom_feature;
								String nom_feature_dep = nomFeatureDep;
								ClassTaDepFeature CTDF = new ClassTaDepFeature(idTaDepFeature,nom_feature_base,nom_feature_dep);
								objetTaDepFeature.add(CTDF);
								idTaDepFeature++;
							}
						}
						if(label.equalsIgnoreCase(ConstProjet.REQUIRES)){
//							List<Integer> idFeatureRequires = analyseXmlRequires(racine,label,id_feature,nom_label,provider_name
//									,os,ws,nl,arch,urlDescription,urlCopyright,urlLicense,updateLabel,updateUrl);
//							int idTaDepFeature = objetTaDepFeature.size()+1;
//							for (Integer idFeature : idFeatureRequires) {
//								int idBaseFeature = id_feature;
//								int idDepFeature = idFeature;
//								ClassTaDepFeature CTDF = new ClassTaDepFeature(idTaDepFeature,idBaseFeature,idDepFeature);
//								objetTaDepFeature.add(CTDF);
//								idTaDepFeature++;
//							}
							List<String> nomFeatureRequires = analyseXmlRequires(racine, label,id_feature,nom_feature,nom_label,provider_name
									,os,ws,nl,arch,urlDescription,urlCopyright,urlLicense,updateLabel,updateUrl);
							int idTaDepFeature = objetTaDepFeature.size()+1;
							for (String nomFeatureDep : nomFeatureRequires) {
								String nom_feature_base = nom_feature;
								String nom_feature_dep = nomFeatureDep;
								ClassTaDepFeature CTDF = new ClassTaDepFeature(idTaDepFeature,nom_feature_base,nom_feature_dep);
								objetTaDepFeature.add(CTDF);
								idTaDepFeature++;
							}
						}	
						List listLabel = racine.getChildren(ConstProjet.PLUGIN);
						if(listLabel!=null){
							Iterator i = listLabel.iterator();
							while (i.hasNext()) {
								Element courant = (Element)i.next();
								String namePlugin = courant.getAttributeValue(ConstProjet.ID);
								String nameVersion = courant.getAttributeValue(ConstProjet.VERSION); 
								String osPlugin = courant.getAttributeValue(ConstProjet.OS);
								String wsPlugin = courant.getAttributeValue(ConstProjet.WS);
								String nlPlugin = courant.getAttributeValue(ConstProjet.NL);
								String archPlugin = courant.getAttributeValue(ConstProjet.ARCH);
								verificationObjetTaPlugin(objetTaPlugin, objetTaDepFeaturePlugin,nom_feature ,namePlugin, nameVersion,
										osPlugin,wsPlugin,nlPlugin,archPlugin);
								//verificationObjetTaPlugin(objetTaPlugin, objetTaDepFeaturePlugin,id_feature ,namePlugin, nameVersion);
							}
							
						}
					}
					
				} catch (JDOMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
//		for (int i = 0; i < objetTaFeature.size(); i++) {
//			System.out.println(objetTaFeature.get(i).getNOM_FEATURE()+"--"+
//							   objetTaFeature.get(i).getVERSION_FEATURE());
//		}
//		System.out.println(objetTaDepFeature.size());
		
	}
	
	public void affichageRelation(Map<String,File> allFeature){
//		Set<String> SEEK_LABEL = new HashSet<String>();
//		SEEK_LABEL.add(ConstProjet.INCLUDES);
//		SEEK_LABEL.add(ConstProjet.REQUIRES);
//		//SEEK_LABEL.add(ConstProjet.PLUGIN);
//		//SEEK_LABEL.add(ConstProjet.IMPORT);
//		/*
//		 * allFeature stocker name of feature and version feature in the fodler of 
//		 * GestionCommerciale(GestionCommercialeComplet.product)  
//		 */
//		//addDefautTaFeature(allFeature);
//		
//		
//		for (String nameFeature : allFeature.keySet()) {
//			int id_feature = 0;
//			//System.out.println(allFileFeature.get(nameFeature).toString());
//			SAXBuilder sxb = new SAXBuilder();
//			try {
//				//Document document = sxb.build(allFileFeature.get(nameFeature));
//				Document document = sxb.build(allFeature.get(nameFeature));
//				Element racine = document.getRootElement();
//				
//				String nom_feature = racine.getAttributeValue(ConstProjet.ID);
//				String nom_label = racine.getAttributeValue(ConstProjet.LABEL);
//				String version_feature = racine.getAttributeValue(ConstProjet.VERSION);
//				String provider_name = racine.getAttributeValue(ConstProjet.PROVIDER_NAME);
//				String os = racine.getAttributeValue(ConstProjet.OS);
//				String ws = racine.getAttributeValue(ConstProjet.WS);
//				String nl = racine.getAttributeValue(ConstProjet.NL);
//				String arch = racine.getAttributeValue(ConstProjet.ARCH);
//				
//
//				String urlDescription = valueUrl(racine, ConstProjet.DESCRIPTION);
//				String urlCopyright = valueUrl(racine, ConstProjet.COPYRIGHT);
//				String urlLicense = valueUrl(racine, ConstProjet.LICENSE);
//				String updateLabel = null;
//				String updateUrl = null;
//				
//				List listUrl = racine.getChildren(ConstProjet.URL);
//				if(listUrl!=null){
//					Iterator i = listUrl.iterator();
//					while(i.hasNext()){
//						Element courantlistUrl = (Element)i.next();
//						
//						for (int j = 0; j < courantlistUrl.getChildren().size(); j++) {
//							Element childrencourantlistUrl =(Element) courantlistUrl.getChildren().get(j);
//							updateLabel = childrencourantlistUrl.getAttributeValue(ConstProjet.LABEL);
//							updateUrl = childrencourantlistUrl.getAttributeValue(ConstProjet.URL);		
//						}
//					}
//				}else{
//					updateLabel = null;
//					updateUrl = null;
//				}
//				if(objetTaFeature.size()==0){
//					id_feature = objetTaFeature.size()+1;
//					ClassTaFeature CTF = new ClassTaFeature(id_feature,nom_feature,nom_label,version_feature,provider_name
//							,os,ws,nl,arch,urlDescription,urlCopyright,urlLicense,updateLabel,updateUrl,null);
//					objetTaFeature.add(CTF);
//				}
//				else{
//					id_feature = verficationObjetTaFeature(objetTaFeature, nom_feature,nom_label,version_feature,provider_name
//							,os,ws,nl,arch,urlDescription,urlCopyright,urlLicense,updateLabel,updateUrl,null);
//				}
//				//System.out.println("-*-*-"+allFeature.get(nameFeature).toString()+"-*-*-");
//
//				for (String label : SEEK_LABEL) {
//					if(label.equalsIgnoreCase(ConstProjet.INCLUDES)){
//						List<Integer> idFeatureIncludes = analyseXmlIncludes(racine, label);
//						int idTaDepFeature = objetTaDepFeature.size()+1;
//						for (Integer idFeature : idFeatureIncludes) {
//							int idBaseFeature = id_feature;
//							int idDepFeature = idFeature;
//							ClassTaDepFeature CTDF = new ClassTaDepFeature(idTaDepFeature,idBaseFeature,idDepFeature);
//							objetTaDepFeature.add(CTDF);
//							idTaDepFeature++;
//						}
//					}
//					if(label.equalsIgnoreCase(ConstProjet.REQUIRES)){
//						List<Integer> idFeatureRequires = analyseXmlRequires(racine, label,id_feature,nom_label,provider_name
//								,os,ws,nl,arch,urlDescription,urlCopyright,urlLicense,updateLabel,updateUrl);
//						int idTaDepFeature = objetTaDepFeature.size()+1;
//						for (Integer idFeature : idFeatureRequires) {
//							int idBaseFeature = id_feature;
//							int idDepFeature = idFeature;
//							ClassTaDepFeature CTDF = new ClassTaDepFeature(idTaDepFeature,idBaseFeature,idDepFeature);
//							objetTaDepFeature.add(CTDF);
//							idTaDepFeature++;
//						}					
//					}
//				}
//
//				List listLabel = racine.getChildren(ConstProjet.PLUGIN);
//				if(listLabel!=null){
//					Iterator i = listLabel.iterator();
//					while (i.hasNext()) {
//						Element courant = (Element)i.next();
//						String namePlugin = courant.getAttributeValue(ConstProjet.ID);
//						String nameVersion = courant.getAttributeValue(ConstProjet.VERSION);
//						verificationObjetTaPlugin(objetTaPlugin, objetTaDepFeaturePlugin,id_feature ,namePlugin, nameVersion);
//					}
//				}
//			} catch (JDOMException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//		}
//		
//
//		for (ClassTaFeature TaFeature : objetTaFeature) {
//			System.out.println(TaFeature.getNOM_FEATURE()+"--"+TaFeature.getLABLE_FEATURE());
//		}
////		File file2 = new File("C:/workspace/GestionCommerciale/GestionCommercialeComplet.product");
////		File file3 = new File("C:/workspace/GestionCommercialeComplet.xml");
////		try {
////			FileReader FR = new FileReader(file2);
////			FileWriter FW = new FileWriter(file3);
////			
////			CopyUtils.copy(FR, FW);
////		} catch (Exception e) {
////			// TODO: handle exception
////		}
////		
////				Set<String> s = new HashSet<String>();
	}
	
	public String valueUrl(Element racine,String nameBalise){
		String url ="";
		List listLabelDescription = racine.getChildren(nameBalise);
		if(listLabelDescription!=null){
			Iterator i = listLabelDescription.iterator();
//			Element courant = (Element)i.next();
//			String url = courant.getAttributeValue(ConstProjet.URL);
			while (i.hasNext()) {
				Element courant = (Element)i.next();
				url = courant.getAttributeValue(ConstProjet.URL);
			}
		}
		return url;
	}
	/*********************** analyseXmlRequires *************************/
	/*
	 * analyse Balise XML is <requires>
	 */
//	public List<Integer> analyseXmlRequires(Element racine,String label,int id_feature,String nom_label,String provider_name
//			,String os,String ws,String nl,String arch,String urlDescription,String urlCopyright,String urlLicense,
//			String updateLabel,String updateUrl){
//		int id;
//		List<Integer> listID = new ArrayList<Integer>();
//		List listRequires = racine.getChildren(label);
//		if(listRequires!=null){
//			Iterator i = listRequires.iterator();
//			while(i.hasNext()){
//				Element courantRequires = (Element)i.next();
//				
//				for (int j = 0; j < courantRequires.getChildren().size(); j++) {
//					Element childrenRequires =(Element) courantRequires.getChildren().get(j);
//					String namefeature = childrenRequires.getAttributeValue(ConstProjet.FEATURE);
//					String nameplugin = childrenRequires.getAttributeValue(ConstProjet.PLUGIN);
//					String version = null;
//					if(childrenRequires.getAttributeValue(ConstProjet.VERSION)==null){
//						version = "0.0.0";
//					}else{
//						version = childrenRequires.getAttributeValue(ConstProjet.VERSION);
//					}
//					
//					if(namefeature!=null){
//						id = verficationObjetTaFeature(objetTaFeature, namefeature,null,version,null
//								,null,null,null,null,null,null,null,null,null,null);
//						listID.add(id);
//					}
//					if(nameplugin!=null){
//						verificationObjetTaPlugin(objetTaPlugin, objetTaDepFeaturePlugin,id_feature ,nameplugin, version);
//					}
//					
//				}
//			}
//		}
//		return listID;
//	}
	public List<String> analyseXmlRequires(Element racine,String label,int id_feature,String nom_feature,String nom_label,String provider_name
			,String os,String ws,String nl,String arch,String urlDescription,String urlCopyright,String urlLicense,
			String updateLabel,String updateUrl){
		int id;
		List<String> listNomFeature = new ArrayList<String>();
		List listRequires = racine.getChildren(label);
		if(listRequires!=null){
			Iterator i = listRequires.iterator();
			while(i.hasNext()){
				Element courantRequires = (Element)i.next();
				
				for (int j = 0; j < courantRequires.getChildren().size(); j++) {
					Element childrenRequires =(Element) courantRequires.getChildren().get(j);
					String namefeature = childrenRequires.getAttributeValue(ConstProjet.FEATURE);
					String nameplugin = childrenRequires.getAttributeValue(ConstProjet.PLUGIN);
					String version = null;
					if(childrenRequires.getAttributeValue(ConstProjet.VERSION)==null){
						version = "0.0.0";
					}else{
						version = childrenRequires.getAttributeValue(ConstProjet.VERSION);
					}
					
					if(namefeature!=null){
						verficationObjetTaFeature(objetTaFeature, namefeature,null,version,null
								,null,null,null,null,null,null,null,null,null,null);
						listNomFeature.add(namefeature);
					}
					if(nameplugin!=null){
						
						verificationObjetTaPlugin(objetTaPlugin, objetTaDepFeaturePlugin,nom_feature ,
								nameplugin, version,null,null,null,null);
					}
					
				}
			}
		}
		return listNomFeature;
	}
/************************************************************************************************************/
	////////////////////////////////////////////////////////////////////////////////////////////////
/*************************************** analyseXmlIncludes **************************************************/	
	/*
	 * analyse Balise XML is <includes>
	 */
//	public List<Integer> analyseXmlIncludes(Element racine,String label){
//		int id;
//		List<Integer> listID = new ArrayList<Integer>();
//		List listLabel = racine.getChildren(label);
//		if(listLabel!=null){
//			Iterator i = listLabel.iterator();
//			while (i.hasNext()) {
//				Element courant = (Element)i.next();
//				String namefeature = courant.getAttributeValue(ConstProjet.ID);
//				String versionfeature = courant.getAttributeValue(ConstProjet.VERSION);
//				id = verficationObjetTaFeature(objetTaFeature, namefeature,null,versionfeature,null
//						,null,null,null,null,null,null,null,null,null,null);
//				listID.add(id);
//			}
//		}
//		return listID;
//	}
	public List<String> analyseXmlIncludes(Element racine,String label){
		int id;
		List<String> listNomFeature = new ArrayList<String>();
		List listLabel = racine.getChildren(label);
		if(listLabel!=null){
			Iterator i = listLabel.iterator();
			while (i.hasNext()) {
				Element courant = (Element)i.next();
				String namefeature = courant.getAttributeValue(ConstProjet.ID);
				String versionfeature = courant.getAttributeValue(ConstProjet.VERSION);
				verficationObjetTaFeature(objetTaFeature, namefeature,null,versionfeature,null
						,null,null,null,null,null,null,null,null,null,null);
				listNomFeature.add(namefeature);
			}
		}
		return listNomFeature;
	}
/********************************************************************************************************/
	
/********************** pour ancienne et nouvelle function verficationObjetTaFeature***************************/
	public int verficationObjetTaFeature(List<ClassTaFeature> objetTaFeature,String nameFeature,String nom_label,String version,
			String provider_name,String os,String ws,String nl,String arch,String urlDescription,String urlCopyright,
			String urlLicense,String updateLabel,String updateUrl,String typeFeature){
		Map<String, Integer> id_ValueFeature = new HashMap<String, Integer>();
		int id_feature=0;
		Boolean noExist = false;
		for (ClassTaFeature TaFeature : objetTaFeature) {
			id_ValueFeature.put(TaFeature.getNOM_FEATURE(),TaFeature.getID_FEATURE());
		}
		if(!id_ValueFeature.containsKey(nameFeature)){
			id_feature = objetTaFeature.size()+1;
			ClassTaFeature CTF = new ClassTaFeature(id_feature,nameFeature,nom_label,version,provider_name
					,os,ws,nl,arch,urlDescription,urlCopyright,urlLicense,updateLabel,updateUrl,typeFeature);
			objetTaFeature.add(CTF);
		}
		else{
			id_feature = id_ValueFeature.get(nameFeature);
			for (ClassTaFeature TaFeature : objetTaFeature) {
				if(TaFeature.getNOM_FEATURE().equalsIgnoreCase(nameFeature)){
					if(TaFeature.getVERSION_FEATURE().equalsIgnoreCase("0.0.0")
							|| TaFeature.getVERSION_FEATURE().contains(ConstProjet.QUALIFIER)) {
						TaFeature.setVERSION_FEATURE(version);
					}
					if(TaFeature.getLABLE_FEATURE()==null){TaFeature.setLABLE_FEATURE(nom_label);}
					if(TaFeature.getPROVIDER_NAME()==null){TaFeature.setPROVIDER_NAME(provider_name);}
					if(TaFeature.getOS()==null){TaFeature.setOS(os);}
					if(TaFeature.getWS()==null){TaFeature.setWS(ws);}
					if(TaFeature.getPROVIDER_NAME()==null){TaFeature.setPROVIDER_NAME(provider_name);}
					if(TaFeature.getNL()==null){TaFeature.setNL(nl);}
					if(TaFeature.getARCH()==null){TaFeature.setARCH(arch);}
					if(TaFeature.getDESCRIPTION_URL()==null){TaFeature.setDESCRIPTION_URL(urlDescription);}
					if(TaFeature.getCOPYRIGHT_URL()==null){TaFeature.setCOPYRIGHT_URL(urlCopyright);}
					if(TaFeature.getLICENSE_URL()==null){TaFeature.setLICENSE_URL(urlLicense);}
					if(TaFeature.getUPDATE_LABEL()==null){TaFeature.setUPDATE_LABEL(updateLabel);}
					if(TaFeature.getUPDATE_URL()==null){TaFeature.setUPDATE_URL(updateUrl);}
					if(TaFeature.getTYPE_FEATURE()==null){TaFeature.setTYPE_FEATURE(typeFeature);}
				}
			}
		}
		return id_feature;
	}
//	public void verficationObjetTaFeature(List<ClassTaFeature> objetTaFeature,String nameFeature,String nom_label,String version,
//			String provider_name,String os,String ws,String nl,String arch,String urlDescription,String urlCopyright,
//			String urlLicense,String updateLabel,String updateUrl,String typeFeature){
//		Map<String, Integer> id_ValueFeature = new HashMap<String, Integer>();
//		int id_feature=0;
//		Boolean noExist = false;
//		for (ClassTaFeature TaFeature : objetTaFeature) {
//			id_ValueFeature.put(TaFeature.getNOM_FEATURE(),TaFeature.getID_FEATURE());
//		}
//		if(!id_ValueFeature.containsKey(nameFeature)){
//			id_feature = objetTaFeature.size()+1;
//			ClassTaFeature CTF = new ClassTaFeature(id_feature,nameFeature,nom_label,version,provider_name
//					,os,ws,nl,arch,urlDescription,urlCopyright,urlLicense,updateLabel,updateUrl,typeFeature);
//			objetTaFeature.add(CTF);
//		}
//		else{
//			id_feature = id_ValueFeature.get(nameFeature);
//			for (ClassTaFeature TaFeature : objetTaFeature) {
//				if(TaFeature.getNOM_FEATURE().equalsIgnoreCase(nameFeature)){
//					if(TaFeature.getVERSION_FEATURE().equalsIgnoreCase("0.0.0")){TaFeature.setVERSION_FEATURE(version);}
//					if(TaFeature.getLABLE_FEATURE()==null){TaFeature.setLABLE_FEATURE(nom_label);}
//					if(TaFeature.getPROVIDER_NAME()==null){TaFeature.setPROVIDER_NAME(provider_name);}
//					if(TaFeature.getOS()==null){TaFeature.setOS(os);}
//					if(TaFeature.getWS()==null){TaFeature.setWS(ws);}
//					if(TaFeature.getPROVIDER_NAME()==null){TaFeature.setPROVIDER_NAME(provider_name);}
//					if(TaFeature.getNL()==null){TaFeature.setNL(nl);}
//					if(TaFeature.getARCH()==null){TaFeature.setARCH(arch);}
//					if(TaFeature.getDESCRIPTION_URL()==null){TaFeature.setDESCRIPTION_URL(urlDescription);}
//					if(TaFeature.getCOPYRIGHT_URL()==null){TaFeature.setCOPYRIGHT_URL(urlCopyright);}
//					if(TaFeature.getLICENSE_URL()==null){TaFeature.setLICENSE_URL(urlLicense);}
//					if(TaFeature.getUPDATE_LABEL()==null){TaFeature.setUPDATE_LABEL(updateLabel);}
//					if(TaFeature.getUPDATE_URL()==null){TaFeature.setUPDATE_URL(updateUrl);}
//					if(TaFeature.getTYPE_FEATURE()==null){TaFeature.setTYPE_FEATURE(typeFeature);}
//				}
//			}
//		}
//		//return nameFeature;
//	}
/********************************************************************************/	
/******************************* verificationObjetTaPlugin ********************************************/	
//	public void verificationObjetTaPlugin(List<ClassTaPlugin> objetTaPlugin,List<ClassTaDepFeaturePlugin> objetTaDepFeaturePlugin,
//										  int idFeature,String namePlugin,String versionPlugin){
//		Map<String, Integer> id_ValuePlugin = new HashMap<String, Integer>();
//		ClassTaPlugin test = null; 
//		int id_Plugin = 0;
//		if(objetTaPlugin.size()==0){
//			id_Plugin = objetTaPlugin.size()+1;
//			ClassTaPlugin CTP = new ClassTaPlugin(id_Plugin,namePlugin,versionPlugin);
//			
//			objetTaPlugin.add(CTP);
//			test = CTP;
//			//System.out.println(test.getID_PLUGIN()+"--"+test.getNOM_PLUGIN()+"--"+test.getVERSION_PLUGIN());
//		}
//		else{
//			for (ClassTaPlugin TaPlugin : objetTaPlugin) {
//				id_ValuePlugin.put(TaPlugin.getNOM_PLUGIN(), TaPlugin.getID_PLUGIN());
//			}
//			if(!id_ValuePlugin.containsKey(namePlugin)){
//				id_Plugin = objetTaPlugin.size()+1;
//				ClassTaPlugin CTP = new ClassTaPlugin(id_Plugin,namePlugin,versionPlugin);
//				
//				objetTaPlugin.add(CTP);
//				test = CTP;
//				//System.out.println(test.getID_PLUGIN()+"--"+test.getNOM_PLUGIN()+"--"+test.getVERSION_PLUGIN());
//			}
//			else{
//				id_Plugin = id_ValuePlugin.get(namePlugin);
//			}
//		}
//		if(objetTaDepFeaturePlugin.size()==0){
//			int id_TaDepFeaturePlugin =  objetTaDepFeaturePlugin.size()+1;
//			ClassTaDepFeaturePlugin CTDFP = new ClassTaDepFeaturePlugin(id_TaDepFeaturePlugin,idFeature,id_Plugin);
//			objetTaDepFeaturePlugin.add(CTDFP);
//		}else{
//			boolean find = false;
//			
//			for (ClassTaDepFeaturePlugin classTaDepFeaturePlugin : objetTaDepFeaturePlugin) {
//				int idFeatureCourant = classTaDepFeaturePlugin.getID_FEATURE();
//				int idPluginCourant = classTaDepFeaturePlugin.getID_PLUGIN();
//				if(idFeatureCourant== idFeature &&idPluginCourant==id_Plugin){
//					find = true;
//				}
//			}
//			
//			if(!find){
//				int id_TaDepFeaturePlugin =  objetTaDepFeaturePlugin.size()+1;
//				ClassTaDepFeaturePlugin CTDFP = new ClassTaDepFeaturePlugin(id_TaDepFeaturePlugin,idFeature,id_Plugin);
//				objetTaDepFeaturePlugin.add(CTDFP);
//			}
//		}
//	}
	public void verificationObjetTaPlugin(List<ClassTaPlugin> objetTaPlugin,List<ClassTaDepFeaturePlugin> objetTaDepFeaturePlugin,
			String nomFeature,String namePlugin,String versionPlugin,String osPlugin,String wsPlugin,
			String nlPlugin,String archPlugin){
		Map<String, Integer> id_ValuePlugin = new HashMap<String, Integer>();
		ClassTaPlugin test = null; 
		int id_Plugin = 0;
		String nom_plugin = null;
		
		if(objetTaPlugin.size()==0){
			id_Plugin = objetTaPlugin.size()+1;
			ClassTaPlugin CTP = new ClassTaPlugin(id_Plugin,namePlugin,versionPlugin,osPlugin,wsPlugin,nlPlugin,archPlugin);

			objetTaPlugin.add(CTP);
			test = CTP;
//			System.out.println(test.getID_PLUGIN()+"--"+test.getNOM_PLUGIN()+"--"+test.getVERSION_PLUGIN());
		}
		else{
			for (ClassTaPlugin TaPlugin : objetTaPlugin) {
				id_ValuePlugin.put(TaPlugin.getNOM_PLUGIN(), TaPlugin.getID_PLUGIN());
			}
			if(!id_ValuePlugin.containsKey(namePlugin)){
				id_Plugin = objetTaPlugin.size()+1;
				nom_plugin = namePlugin;
				ClassTaPlugin CTP = new ClassTaPlugin(id_Plugin,namePlugin,versionPlugin,osPlugin,wsPlugin,nlPlugin,archPlugin);

				objetTaPlugin.add(CTP);
				test = CTP;
//				System.out.println(test.getID_PLUGIN()+"--"+test.getNOM_PLUGIN()+"--"+test.getVERSION_PLUGIN());
			}
			else{
				id_Plugin = id_ValuePlugin.get(namePlugin);
				nom_plugin = namePlugin;
			}
		}
		if(objetTaDepFeaturePlugin.size()==0){
			int id_TaDepFeaturePlugin =  objetTaDepFeaturePlugin.size()+1;
			//ClassTaDepFeaturePlugin CTDFP = new ClassTaDepFeaturePlugin(id_TaDepFeaturePlugin,idFeature,id_Plugin);
			ClassTaDepFeaturePlugin CTDFP = new ClassTaDepFeaturePlugin(id_TaDepFeaturePlugin,nomFeature,namePlugin);
			objetTaDepFeaturePlugin.add(CTDFP);
		}else{
			boolean find = false;

			for (ClassTaDepFeaturePlugin classTaDepFeaturePlugin : objetTaDepFeaturePlugin) {
//				int idFeatureCourant = classTaDepFeaturePlugin.getID_FEATURE();
//				int idPluginCourant = classTaDepFeaturePlugin.getID_PLUGIN();
//				if(idFeatureCourant== idFeature &&idPluginCourant==id_Plugin){
//					find = true;
//				}
				String nomFeatureCourant = classTaDepFeaturePlugin.getNOM_FEATURE();
				String nomPluginCourant = classTaDepFeaturePlugin.getNOM_PLUGIN();
				
				if(nomFeatureCourant.equals(nomFeature) && nomPluginCourant.equals(nom_plugin)){
					find = true;
				}
			}

			if(!find){
				int id_TaDepFeaturePlugin =  objetTaDepFeaturePlugin.size()+1;
				ClassTaDepFeaturePlugin CTDFP = new ClassTaDepFeaturePlugin(id_TaDepFeaturePlugin,
												nomFeature,namePlugin);
				objetTaDepFeaturePlugin.add(CTDFP);
			}
		}
	}
	/********************************************************************************************/
	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public Map<String, File> getAllFileFeature() {
		return allFileFeature;
	}

	public void setAllFileFeature(Map<String, File> allFileFeature) {
		this.allFileFeature = allFileFeature;
	}

	public List<ClassTaPlugin> getObjetTaPlugin() {
		return objetTaPlugin;
	}

	public void setObjetTaPlugin(List<ClassTaPlugin> objetTaPlugin) {
		this.objetTaPlugin = objetTaPlugin;
	}

	public List<ClassTaFeature> getObjetTaFeature() {
		return objetTaFeature;
	}

	public void setObjetTaFeature(List<ClassTaFeature> objetTaFeature) {
		this.objetTaFeature = objetTaFeature;
	}

	public List<ClassTaDepFeature> getObjetTaDepFeature() {
		return objetTaDepFeature;
	}

	public void setObjetTaDepFeature(List<ClassTaDepFeature> objetTaDepFeature) {
		this.objetTaDepFeature = objetTaDepFeature;
	}

	public List<ClassTaDepFeaturePlugin> getObjetTaDepFeaturePlugin() {
		return objetTaDepFeaturePlugin;
	}

	public void setObjetTaDepFeaturePlugin(
			List<ClassTaDepFeaturePlugin> objetTaDepFeaturePlugin) {
		this.objetTaDepFeaturePlugin = objetTaDepFeaturePlugin;
	}

	public String getQualifier() {
		return qualifier;
	}

	public void setQualifier(String qualifier) {
		this.qualifier = qualifier;
	}
	
	

}

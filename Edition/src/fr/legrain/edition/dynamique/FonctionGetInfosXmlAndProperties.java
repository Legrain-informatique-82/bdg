package fr.legrain.edition.dynamique;

import java.beans.PropertyDescriptor;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import fr.legrain.edition.Activator;
import fr.legrain.edition.actions.AttributElementResport;
import fr.legrain.gestCom.Appli.Const;
import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.lib.gui.grille.LgrTableViewer;



/**
 * 
 * @author lee
 * pour analyser le contenu du fichier dozerBeanMapping.xml
 * et du fichier "AttributeTableEdition.properties"
 */
public class FonctionGetInfosXmlAndProperties {
	
	static Logger logger = Logger.getLogger(FonctionGetInfosXmlAndProperties.class.getName()); 
	
	private String tag_class_a = "class-a";//libelle de <class-a> ... </class-a>
	private String tag_class_b = "class-b";//libelle de <class-b> ... </class-b>

	private String tag_field = "field";// libelle de <field> ... </field>
	
	private String tag_a = "a";// libelle de <a> ... </a>
	private String tag_b = "b";// libelle de <b> ... </b>
	
	private LinkedList <String> listChampColonne = new LinkedList<String>();// ==> 
	private LinkedList <String> list_tag_a= new LinkedList<String>(); // les values de libelle de <a> ... </a>
	
	private LgrDozerMapper lgrDozerMapper = null;
	private String pathFileXml = null; //path de dozerBeanMapping.xml
	
	/**
	 * mapListChampEtType ==> pour stocker les atrribute de colonne et ses type et les methode
	 * mapListChampEtType EX: codeArticle <==> string mapListChampEtType
	 */ 
	private LinkedHashMap<String,String> mapListChampEtType = new LinkedHashMap<String, String>();
	/**
	 * mapListChampEtMethode ==> pour stocker les atrribute de colonne et ses methode
	 * mapListChampEtMethode EX: codeArticle <==> getCodeArticle()
	 */ 
	private LinkedHashMap<String,String> mapListChampEtMethode = new LinkedHashMap<String,String>();
	/**
	 * mapListChampEtRow ==> pour stocker les atrribute de colonne et ses valeur de row
	 * mapListChampEtRow EX: codeArticle <==> row["codeArticle"]
	 */ 
	private LinkedHashMap<String,String> mapListChampEtRow = new LinkedHashMap<String,String>();
	
	/**
	 * stocker infos de la ficher "AttributeTableEdition.properties" 
	 * mapAttributeTablHead ==> stocker les attributes de head Table
	 * mapAttributeTablDetail ==> stocker les attributes de Detail Table
	 */
	private LinkedHashMap<String,AttributElementResport> mapAttributeTablHead = new LinkedHashMap<String,AttributElementResport>(); 
	private LinkedHashMap<String,AttributElementResport> mapAttributeTablDetail = new LinkedHashMap<String,AttributElementResport>();
	
	/**
	 * pour trouver les infos de dozerBeanMapping.xml
	 * @param valueClassA ==> nom de class objet JPA. EX ==> fr.legrain.articles.dao.TaArticle
	 * @param valueClassB ==> nom de class objet SWT. EX ==> fr.legrain.gestCom.Module_Articles.SWTArticle
	 * @param listeChamp ==> les attribute de class correspond les colonne de affichage 
	 * @param tagName ==> quelle libelle que l'on va commencer à traiter
	 */
	public void findInfosFileXml(String valueClassA,String valueClassB,String[] listeChamp,String tagName){
		

		getPathFileXml();
		/** 16/03/2010 zhaolin**/
//		String namePlugin = Activator.getDefault().getBundle().getSymbolicName();
//		getPathFileXmlAttributeEditionDynamique("attributeEditiondynamique.xml", namePlugin);
		/**
		 * array_list_tag_a qui stocke l'ordre des attributes 
		 * comme lise de champs
		 */
		String[] array_list_tag_a = new String[listeChamp.length];
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		String nodeValueClassA = null;
		String nodeValueClassB = null;
		Document document = null;
		try {
			
			DocumentBuilder builder = dbf.newDocumentBuilder();
			builder.setEntityResolver(new  EntityResolver() {
				
				@Override
				public InputSource resolveEntity(String arg0, String arg1)
						throws SAXException, IOException {
					// TODO Auto-generated method stub
//					return null;
					return new InputSource(new StringReader(""));
				}
			});
			document = builder.parse(pathFileXml);
			NodeList nodeListMapping = document.getElementsByTagName(tagName);
			for (int i = 0; i < nodeListMapping.getLength(); i++) {
				Element mapping = (Element) nodeListMapping.item(i);
				NodeList nodeListClassA = mapping.getElementsByTagName(tag_class_a);
				if (nodeListClassA.getLength() == 1) {
					Element e = (Element) nodeListClassA.item(0);
					Text t = (Text) e.getFirstChild();
					nodeValueClassA = t.getNodeValue();
				}
				NodeList nodeListClassB = mapping.getElementsByTagName(tag_class_b);
				if (nodeListClassB.getLength() == 1) {
					Element e = (Element) nodeListClassB.item(0);
					Text t = (Text) e.getFirstChild();
					nodeValueClassB = t.getNodeValue();
				}
				if(nodeValueClassA.equals(valueClassA) && nodeValueClassB.equals(valueClassB)){
					NodeList nodeListField = mapping.getElementsByTagName(tag_field);
					for (int j = 0; j < nodeListField.getLength(); j++) {
						Element field = (Element) nodeListField.item(j);
						
						NodeList nodeListA = field.getElementsByTagName(tag_a);
						Element elementA = (Element) nodeListA.item(0);
						Text textA = (Text) elementA.getFirstChild();
						String valueTestA = textA.getNodeValue();
						
						NodeList nodeListB = field.getElementsByTagName(tag_b);
						Element elementB = (Element) nodeListB.item(0);
						Text textB = (Text) elementB.getFirstChild();
						String valueTestB = textB.getNodeValue();
						
						/**
						 *  pour convertir String[] listeChamp vers List listChampColonne
						 */
//						if(listChampColonne.contains(valueTestB)){
//							list_tag_a.add(valueTestA);
//						}	
						for (int m = 0; m < listeChamp.length; m++) {
							if(listeChamp[m].equalsIgnoreCase(valueTestB)){
								array_list_tag_a[m]= valueTestA;
							}
						}
					}
					for (int k = 0; k < array_list_tag_a.length; k++) {
						list_tag_a.add(array_list_tag_a[k]);
					}
					
					break;
				}
			}
			
		} 
		catch (ParserConfigurationException e) {
			logger.error("Erreur ", e);
		} 
		catch (SAXException e) {
			logger.error("Erreur ", e);
		} 
		catch (IOException e) {
			logger.error("Erreur ", e);
		}
		
	}

	/**
	 * pour obtenir path de dozerBeanMapping.xml
	 * EX : ... /gestComBd/dozerBeanMapping.xml
	 */
	public void getPathFileXml(){
		
		for (int i = 0; i < lgrDozerMapper.getMappingFiles().size(); i++) {
			String pathFile = (String) lgrDozerMapper.getMyMappingFiles().get(i);
			if(pathFile.endsWith(lgrDozerMapper.getMappingFile())){
				pathFileXml = pathFile;
				break;
			}
		}
	}
	
	public void getPathFileXmlAttributeEditionDynamique(String path,String namePlugin){
	
		String pathFile = null;
		Bundle bundleEditions = Platform.getBundle(namePlugin);
		
		URL urlReportFile;
		urlReportFile = FileLocator.find(bundleEditions,new Path(path),null);
		if(urlReportFile!=null){
			try {
				urlReportFile = FileLocator.toFileURL(urlReportFile);
				
				URI uriReportFile = new URI("file", urlReportFile.getAuthority(),
						urlReportFile.getPath(), urlReportFile.getQuery(), null);
				File reportFileEdition = new File(uriReportFile);
				
				pathFile = reportFileEdition.getAbsolutePath();
			} catch (Exception e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				logger.error("", e);
			}
		}
		pathFileXml = pathFile;
	}
	
	/**
	 * pour construire le file de XML EX: int ==> integer, BigDecimal ==> float
	 * @return
	 */
	
	public String changeNomType(String typeJava){
		String typeReport = null;
		if(typeJava.equalsIgnoreCase("BigDecimal")){
			typeReport = Float.class.getSimpleName().toLowerCase();
		}else if(typeJava.equalsIgnoreCase("int")){
			typeReport = Integer.class.getSimpleName().toLowerCase();
		}else if(typeJava.equalsIgnoreCase("String")){
			typeReport = String.class.getSimpleName().toLowerCase();
		}else if(typeJava.equalsIgnoreCase("Date")){
			typeReport = Date.class.getSimpleName().toLowerCase();
		}else if(typeJava.equalsIgnoreCase("Integer")){
			typeReport = typeJava.toLowerCase();
		}
		return typeReport;
	}
	
	/**
	 * pour get les nom de getxxx()
	 * EX : JPAReadMethod = "public final int fr.legrain.articles.dao.TaFamille$$EnhancerByCGLIB$$95e5ac97.getIdFamille()";
	 * Return : partiJPAReadMethod = "getIdFamille()";  
	 */
	public String getPartiJPAReadMethod(String JPAReadMethod){
		String partiJPAReadMethod = null;
		String[] arrayPartiJPAReadMethod = JPAReadMethod.split("\\.");
		partiJPAReadMethod = arrayPartiJPAReadMethod[arrayPartiJPAReadMethod.length-1];
		return partiJPAReadMethod;
	}
	
	/**
	 * pour la même object, ne pas mix plusieurs objects 
	 * @param objectJPA ==> est objet de ecran  EX: l'objet de TaArticle etc.
	 */
	public void getInfosObjetJPA(Object objectJPA){
		getInfosObjetJPA(objectJPA,null);
	}
	/**
	 * pour la même object, ne pas mix plusieurs objects 
	 * @param objectJPA ==> est objet de ecran  EX: l'objet de TaArticle etc.
	 * @param listeChampObjet - liste des champs passé explicitement, si null, utilise le resultat de la l'analyse du fichier XML dozer
	 */
	public void getInfosObjetJPA(Object objectJPA, LinkedList<String> listeChampObjet){
		
		mapListChampEtType.clear();
		mapListChampEtMethode.clear();
		mapListChampEtRow.clear();
		LinkedList<String> liste = null;
		
		if(listeChampObjet==null) {
			liste = list_tag_a;
		} else {
			liste = listeChampObjet;
		}
		
		for (int i = 0; i < liste.size(); i++) {
			Object object = null;
			Object objectCourant = null;
			PropertyDescriptor propertyDescriptor = null;
			String nameReadMethod = "";
			String attributeObjet =liste.get(i);
			
			if(attributeObjet.indexOf(".")!= -1){
				String[] arrayAttributeObjet = attributeObjet.split("\\.");
				for (int j = 0; j < arrayAttributeObjet.length; j++) {
					try {
						if(j == arrayAttributeObjet.length-1){
							objectCourant = object;

							propertyDescriptor = PropertyUtils.getPropertyDescriptor(objectCourant, arrayAttributeObjet[j]);
							nameReadMethod += getPartiJPAReadMethod(propertyDescriptor.getReadMethod().toString());
							String typeChamp = changeNomType(propertyDescriptor.getPropertyType().getSimpleName());
							mapListChampEtMethode.put(arrayAttributeObjet[j], nameReadMethod);
							mapListChampEtType.put(arrayAttributeObjet[j], typeChamp);
							mapListChampEtRow.put(arrayAttributeObjet[j], makeEditionScriptRow(arrayAttributeObjet[j]));
							
						}else if(j == 0){
							objectCourant = objectJPA;
							propertyDescriptor = PropertyUtils.getPropertyDescriptor(objectCourant, arrayAttributeObjet[j]);
							object =  PropertyUtils.getProperty(objectCourant, arrayAttributeObjet[j]);
							if(object == null){
								object = propertyDescriptor.getPropertyType().newInstance();				
							}
//							if(object!=null)
							nameReadMethod += getPartiJPAReadMethod(propertyDescriptor.getReadMethod().toString())+".";
						}else{
							objectCourant = object;
							propertyDescriptor = PropertyUtils.getPropertyDescriptor(objectCourant, arrayAttributeObjet[j]);
							object =  PropertyUtils.getProperty(objectCourant, arrayAttributeObjet[j]);
							if(object == null ){
								object = propertyDescriptor.getPropertyType().newInstance();	
							}
//							if(object != null)
							nameReadMethod += getPartiJPAReadMethod(propertyDescriptor.getReadMethod().toString())+".";
						}
					} 
//					catch (IllegalAccessException e) {
//						logger.error("error", e);
//					} catch (InvocationTargetException e) {
//						logger.error("error", e);
//					} catch (NoSuchMethodException e) {
//						logger.error("error", e);
//					} 
					catch (Exception e) {
						logger.error("error", e);
					}
				}
			}else{
				try {
					objectCourant = objectJPA;
					if(objectCourant != null){
						propertyDescriptor = PropertyUtils.getPropertyDescriptor(objectCourant, attributeObjet);
						nameReadMethod += getPartiJPAReadMethod(propertyDescriptor.getReadMethod().toString());
						String typeChamp = changeNomType(propertyDescriptor.getPropertyType().getSimpleName());
						logger.debug("==>"+nameReadMethod+" === "+typeChamp);
						mapListChampEtMethode.put(attributeObjet, nameReadMethod);
						mapListChampEtType.put(attributeObjet, typeChamp);
						mapListChampEtRow.put(attributeObjet, makeEditionScriptRow(attributeObjet));
					}
		
				} catch (IllegalAccessException e) {
					logger.error("error", e);
				} catch (InvocationTargetException e) {
					logger.error("error", e);
				} catch (NoSuchMethodException e) {
					logger.error("error", e);
				}
			}
		}
	}
	
	public void cleanValueMapAttributeTable(){
		mapAttributeTablHead.clear();
		mapAttributeTablDetail.clear();
	}
	
	public static PropertiesConfiguration listeChampEditionDynamique = new PropertiesConfiguration();
	
	public static void setListeChampGrille(String fileName){
		try {
			if (!new File(fileName).exists()) {
				MessageDialog.openError(
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
						"ERREUR",
						"Le fichier .properties "+ fileName + " est inexistant");
			} else {
				FileInputStream file = new FileInputStream(fileName);
				listeChampEditionDynamique.load(file);
				file.close();
			}
		}
		catch (Exception e) {
			logger.error("Erreur : setListeChampGrille", e);
		}
	}
	
	/**
	 * chercher les attributes de table head dans le "AttributeTableEdition.properties"
	 * EX : ... /GestionCommerciale/Bd/AttributeTableEdition.properties
	 */
	public void setValueMapAttributeTable(String section){	

//		LgrTableViewer.setListeChampGrille(Const.C_FICHIER_LISTE_ATTRIBUTE_EDITION);
		setListeChampGrille(Const.C_FICHIER_LISTE_ATTRIBUTE_EDITION);
		org.apache.commons.configuration.SubsetConfiguration propertie = null;
//		propertie = new org.apache.commons.configuration.SubsetConfiguration(
//				LgrTableViewer.getListeChampGrille(),section,".");
		propertie = new org.apache.commons.configuration.SubsetConfiguration(
				listeChampEditionDynamique,section,".");
		if (!propertie.isEmpty()){
			for (Iterator iter = propertie.getKeys(); iter.hasNext();) {
				String KeyMap = iter.next().toString();
				String valueMap = propertie.getString(KeyMap);
				String[] valueAttributes = valueMap.split(";");
				AttributElementResport attributElementResport = new AttributElementResport(valueAttributes);

//				if(section.equals(TaArticle.class.getSimpleName()+".head")){
//					mapAttributeTablHead.put(KeyMap, attributElementResport);
//				}
//				if(section.equals(TaArticle.class.getSimpleName()+".detail")){
//					mapAttributeTablDetail.put(KeyMap, attributElementResport);
//				}
				if(section.indexOf("head")!=-1){
					mapAttributeTablHead.put(KeyMap, attributElementResport);
				}
				if(section.indexOf("detail")!=-1){
					mapAttributeTablDetail.put(KeyMap, attributElementResport);
				}

			}
		}
	}
	
	/**
	 * pour contstruire une partie de Script fetch
	 * EX : voir la partie de Script fetch
	 * @param nomChamp EX : codeArticle
	 * @return EX: row["codeArticle"]
	 */
	public String makeEditionScriptRow(String nomChamp){
		String scriptRow = "";
		scriptRow = "row[\""+nomChamp+"\"]";
		return scriptRow;
	}
	
	public FonctionGetInfosXmlAndProperties(LgrDozerMapper lgrDozerMapper) {
		super();
		this.lgrDozerMapper = lgrDozerMapper;
	}
	
	public FonctionGetInfosXmlAndProperties() {
		super();
	}

	public List<String> getListChampColonne() {
		return listChampColonne;
	}

	public List<String> getList_tag_a() {
		return list_tag_a;
	}

	public LinkedHashMap<String, String> getMapListChampEtType() {
		return mapListChampEtType;
	}

	public void setMapListChampEtType(LinkedHashMap<String, String> mapListChampEtType) {
		this.mapListChampEtType = mapListChampEtType;
	}

	public LinkedHashMap<String, String> getMapListChampEtMethode() {
		return mapListChampEtMethode;
	}

	public void setMapListChampEtMethode(LinkedHashMap<String, String> mapListChampEtMethode) {
		this.mapListChampEtMethode = mapListChampEtMethode;
	}

	public LinkedHashMap<String, String> getMapListChampEtRow() {
		return mapListChampEtRow;
	}

	public void setMapListChampEtRow(LinkedHashMap<String, String> mapListChampEtRow) {
		this.mapListChampEtRow = mapListChampEtRow;
	}

	public LinkedHashMap<String, AttributElementResport> getMapAttributeTablHead() {
		return mapAttributeTablHead;
	}

	public LinkedHashMap<String, AttributElementResport> getMapAttributeTablDetail() {
		return mapAttributeTablDetail;
	}



}

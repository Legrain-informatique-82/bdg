package fr.legrain.publipostage.divers;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.runtime.Platform;

public class TypeVersionPublipostage {
	public static final String TYPE_OPENOFFICE = "OpenOffice"; 
	public static final String TYPE_MSWORD = "MsWord";
	
	public static final String TYPE_OPENOFFICE_3 = "OpenOffice_3"; 
	public static final String TYPE_MSWORD_2003 = "MsWord_2003";
	public static final String TYPE_MSWORD_2007 = "MsWord_2007";
	public static final String TYPE_MSWORD_2000 = "MsWord_2000";
	
	public static final String VERSION_OPENOFFICE_3 = "OpenOffice 3";
	public static final String VERSION_MSWORD_2000 = "Word 2000";
	public static final String VERSION_MSWORD_2003 = "Word 2003";
	public static final String VERSION_MSWORD_2007 = "Word 2007";

	public static final String EXTENSION_CORRESPONDANCE = "*.properties;*.ini;*.txt";
	public static final String EXTENSION_TOUT = "*.*";
	
	//liste de toutes les versions possible pour le publipostage
	private Map<String,String> typeVersionExistantes = new LinkedHashMap<String, String>(); 
	
	//liste des extension associées à chaque version
	private Map<String,String> extension = new HashMap<String, String>();
	
	//liste du type de logiciel associé à chaque extension
	private Map<String,String> logicielParExtension = new HashMap<String, String>(); 

	//liste des type de logiciel utilisé
	private Map<String,String> type = new HashMap<String, String>(); 

	//liste des extensions pour le fichier final associées à chaque version
	private Map<String,String> extensionFinale = new HashMap<String, String>();
	
	//liste des extensions pour le fichier final associées à type de logiciel
	private Map<String,String> extensionFinaleDefaut = new HashMap<String, String>();

	private TypeVersionPublipostage() {}
	
	private static TypeVersionPublipostage instance = null;
	
	public static TypeVersionPublipostage getInstance() {
		if(instance==null) {
			instance = new TypeVersionPublipostage(); 
			
			instance.typeVersionExistantes.put(TYPE_OPENOFFICE_3,VERSION_OPENOFFICE_3);
			instance.typeVersionExistantes.put(TYPE_MSWORD_2000,VERSION_MSWORD_2000);
			instance.typeVersionExistantes.put(TYPE_MSWORD_2003,VERSION_MSWORD_2003);
			instance.typeVersionExistantes.put(TYPE_MSWORD_2007,VERSION_MSWORD_2007);

			
			instance.extension.put(VERSION_OPENOFFICE_3, "*.odt");
			instance.extension.put(VERSION_MSWORD_2000, "*.doc");
			instance.extension.put(VERSION_MSWORD_2003, "*.doc");
			instance.extension.put(VERSION_MSWORD_2007, "*.doc;*.docx");
			
			instance.extensionFinale.put(VERSION_OPENOFFICE_3, ".odt");
			instance.extensionFinale.put(VERSION_MSWORD_2000, ".doc");
			instance.extensionFinale.put(VERSION_MSWORD_2003, ".doc");
			instance.extensionFinale.put(VERSION_MSWORD_2007, ".doc");
			

			instance.type.put(VERSION_OPENOFFICE_3, TYPE_OPENOFFICE);
			instance.type.put(VERSION_MSWORD_2000, TYPE_MSWORD);
			instance.type.put(VERSION_MSWORD_2003, TYPE_MSWORD);
			instance.type.put(VERSION_MSWORD_2007, TYPE_MSWORD);
			
			instance.logicielParExtension.put("odt", TYPE_OPENOFFICE);
			instance.logicielParExtension.put("doc", TYPE_MSWORD);
			instance.logicielParExtension.put("docx", TYPE_MSWORD);
			
			instance.extensionFinaleDefaut.put(TYPE_OPENOFFICE,"odt");
			instance.extensionFinaleDefaut.put(TYPE_MSWORD,"doc");
		} 
		return instance;
	}
	
	public Map<String, String> getTypeVersionExistantes() {
		return typeVersionExistantes;
	}


	public Map<String, String> getExtension() {
		return extension;
	}

	public Map<String, String> getType() {
		return type;
	}

	public void setType(Map<String, String> type) {
		this.type = type;
	}

	public Map<String, String> getExtensionFinale() {
		return extensionFinale;
	}

	public void setExtensionFinale(Map<String, String> extensionFinale) {
		this.extensionFinale = extensionFinale;
	}

	public Map<String, String> getLogicielParExtension() {
		return logicielParExtension;
	}

	public Map<String, String> getExtensionFinaleDefaut() {
		return extensionFinaleDefaut;
	}
	
}

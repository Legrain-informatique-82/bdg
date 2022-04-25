package fr.legrain.restaurationFTP.divers;

import java.util.Properties;

public class PropertiesFilePreference {

	static private Properties props;

	private PropertiesFilePreference() {
	
	}
	public static Properties getProperties(){
		if(props == null) props = new Properties();
//		props.
		return props;
	}
}

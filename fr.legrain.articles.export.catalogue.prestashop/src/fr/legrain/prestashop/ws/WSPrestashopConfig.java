package fr.legrain.prestashop.ws;

public class WSPrestashopConfig {
	
	static private WSPrestashopConfig config = null;
	
//	private String baseURI = "http://dev2.pageweb.fr/api";
//	private String cle = "NAEVYYHRN00WH0SS0G87RDE550OL9V92";
//	private String password = "";
//	private String hostName = "dev2.pageweb.fr";
	
	private String baseURI = null;
	private String cle = null;
	private String password = null;
	private String hostName = null;
	
	private WSPrestashopConfig(){}
	
	public static void init (String baseURI, String cle, String password, String hostName) {
		if(config == null) {
			config = new WSPrestashopConfig();
		}
		config.baseURI = baseURI;
		config.cle = cle;
		config.password = password;
		config.hostName = hostName;
	}

	public static WSPrestashopConfig getConfig() {
		return config;
	}

	public String getBaseURI() {
		return baseURI;
	}

	public String getCle() {
		return cle;
	}

	public String getPassword() {
		return password;
	}

	public String getHostName() {
		return hostName;
	}

}

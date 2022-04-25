package ControleAction;

public class ClassTaPlugin {

	private int ID_PLUGIN;
	private String NOM_PLUGIN;
	private String VERSION_PLUGIN;
	private String OS;
	private String WS;
	private String NL;
	private String ARCH;
	
	public ClassTaPlugin(int id_plugin, String nom_plugin, String version_plugin) {
		super();
		ID_PLUGIN = id_plugin;
		NOM_PLUGIN = nom_plugin;
		VERSION_PLUGIN = version_plugin;
	}

	
	public int getID_PLUGIN() {
		return ID_PLUGIN;
	}

	public String getNOM_PLUGIN() {
		return NOM_PLUGIN;
	}

	public String getVERSION_PLUGIN() {
		return VERSION_PLUGIN;
	}


	public ClassTaPlugin(int id_plugin, String nom_plugin,
			String version_plugin, String os, String ws, String nl, String arch) {
		super();
		ID_PLUGIN = id_plugin;
		NOM_PLUGIN = nom_plugin;
		VERSION_PLUGIN = version_plugin;
		OS = os;
		WS = ws;
		NL = nl;
		ARCH = arch;
	}


	public String getOS() {
		return OS;
	}


	public String getWS() {
		return WS;
	}


	public String getNL() {
		return NL;
	}


	public String getARCH() {
		return ARCH;
	}
	

}

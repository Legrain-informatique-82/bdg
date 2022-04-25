package ControleAction;

public class ClassTaDepFeaturePlugin {
	
	private int ID;
	private int ID_FEATURE;
	private int ID_PLUGIN;
	
	private String NOM_FEATURE;
	private String NOM_PLUGIN;
	
	public ClassTaDepFeaturePlugin(int id, int id_feature, int id_plugin) {
		super();
		ID = id;
		ID_FEATURE = id_feature;
		ID_PLUGIN = id_plugin;
	}
	public ClassTaDepFeaturePlugin(int id,String nom_feature, String nom_plugin) {
		super();
		ID = id;
		NOM_FEATURE = nom_feature;
		NOM_PLUGIN = nom_plugin;
	}

	public int getID() {
		return ID;
	}
	public int getID_FEATURE() {
		return ID_FEATURE;
	}
	public int getID_PLUGIN() {
		return ID_PLUGIN;
	}
	public String getNOM_FEATURE() {
		return NOM_FEATURE;
	}

	public String getNOM_PLUGIN() {
		return NOM_PLUGIN;
	}

}

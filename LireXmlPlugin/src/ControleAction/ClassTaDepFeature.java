package ControleAction;

public class ClassTaDepFeature {
	
	private int ID;
	private int ID_FEATURE_BASE;
	private int ID_FEATURE_DEP;
	
	private String NOM_FEATURE_BASE;
	private String NOM_FEATURE_DEP;
	
	public ClassTaDepFeature(int id, int id_feature_base, int id_feature_dep) {
		super();
		ID = id;
		ID_FEATURE_BASE = id_feature_base;
		ID_FEATURE_DEP = id_feature_dep;
	}
	
	
	public ClassTaDepFeature(int id,String nom_feature_base, String nom_feature_dep) {
		super();
		ID = id;
		NOM_FEATURE_BASE = nom_feature_base;
		NOM_FEATURE_DEP = nom_feature_dep;
	}


	public int getID() {
		return ID;
	}
	public int getID_FEATURE_BASE() {
		return ID_FEATURE_BASE;
	}
	public int getID_FEATURE_DEP() {
		return ID_FEATURE_DEP;
	}
	public String getNOM_FEATURE_BASE() {
		return NOM_FEATURE_BASE;
	}
	public void setNOM_FEATURE_BASE(String nom_feature_base) {
		NOM_FEATURE_BASE = nom_feature_base;
	}
	public String getNOM_FEATURE_DEP() {
		return NOM_FEATURE_DEP;
	}
	public void setNOM_FEATURE_DEP(String nom_feature_dep) {
		NOM_FEATURE_DEP = nom_feature_dep;
	}
	
	
}

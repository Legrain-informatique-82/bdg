package ControleAction;

public class ClassTaFeature {
	
	private int ID_FEATURE;
	private String NOM_FEATURE;
	private String LABLE_FEATURE;
	private String VERSION_FEATURE;
	private String PROVIDER_NAME;
	private String OS;
	private String WS;
	private String NL;
	private String ARCH;
	private String DESCRIPTION_URL;
	private String COPYRIGHT_URL;
	private String LICENSE_URL;
	private String UPDATE_LABEL;
	private String UPDATE_URL;
	private String TYPE_FEATURE;
	public ClassTaFeature(int id_feature, String nom_feature,
			String lable_feature, String version_feature, String provider_name,
			String os, String ws, String nl, String arch,
			String description_url, String copyright_url, String license_url,
			String update_label, String update_url, String type_feature) {
		super();
		ID_FEATURE = id_feature;
		NOM_FEATURE = nom_feature;
		LABLE_FEATURE = lable_feature;
		VERSION_FEATURE = version_feature;
		PROVIDER_NAME = provider_name;
		OS = os;
		WS = ws;
		NL = nl;
		ARCH = arch;
		DESCRIPTION_URL = description_url;
		COPYRIGHT_URL = copyright_url;
		LICENSE_URL = license_url;
		UPDATE_LABEL = update_label;
		UPDATE_URL = update_url;
		TYPE_FEATURE = type_feature;
	}
	public int getID_FEATURE() {
		return ID_FEATURE;
	}
	public void setID_FEATURE(int id_feature) {
		ID_FEATURE = id_feature;
	}
	public String getNOM_FEATURE() {
		return NOM_FEATURE;
	}
	public void setNOM_FEATURE(String nom_feature) {
		NOM_FEATURE = nom_feature;
	}
	public String getLABLE_FEATURE() {
		return LABLE_FEATURE;
	}
	public void setLABLE_FEATURE(String lable_feature) {
		LABLE_FEATURE = lable_feature;
	}
	public String getVERSION_FEATURE() {
		return VERSION_FEATURE;
	}
	public void setVERSION_FEATURE(String version_feature) {
		VERSION_FEATURE = version_feature;
	}
	public String getPROVIDER_NAME() {
		return PROVIDER_NAME;
	}
	public void setPROVIDER_NAME(String provider_name) {
		PROVIDER_NAME = provider_name;
	}
	public String getOS() {
		return OS;
	}
	public void setOS(String os) {
		OS = os;
	}
	public String getWS() {
		return WS;
	}
	public void setWS(String ws) {
		WS = ws;
	}
	public String getNL() {
		return NL;
	}
	public void setNL(String nl) {
		NL = nl;
	}
	public String getARCH() {
		return ARCH;
	}
	public void setARCH(String arch) {
		ARCH = arch;
	}
	public String getDESCRIPTION_URL() {
		return DESCRIPTION_URL;
	}
	public void setDESCRIPTION_URL(String description_url) {
		DESCRIPTION_URL = description_url;
	}
	public String getCOPYRIGHT_URL() {
		return COPYRIGHT_URL;
	}
	public void setCOPYRIGHT_URL(String copyright_url) {
		COPYRIGHT_URL = copyright_url;
	}
	public String getLICENSE_URL() {
		return LICENSE_URL;
	}
	public void setLICENSE_URL(String license_url) {
		LICENSE_URL = license_url;
	}
	public String getUPDATE_LABEL() {
		return UPDATE_LABEL;
	}
	public void setUPDATE_LABEL(String update_label) {
		UPDATE_LABEL = update_label;
	}
	public String getUPDATE_URL() {
		return UPDATE_URL;
	}
	public void setUPDATE_URL(String update_url) {
		UPDATE_URL = update_url;
	}
	public String getTYPE_FEATURE() {
		return TYPE_FEATURE;
	}
	public void setTYPE_FEATURE(String type_feature) {
		TYPE_FEATURE = type_feature;
	}
}

package fr.legrain.services;

public class Branding {
	
	public static final String C_VERSION_CONTACT = "Contact";
	
	static private Branding instance = null;
	
	private String typeVersion = null;
	private String descriptionVersion = null;
	
	private Branding() {
	}
	
	static public Branding getInstance() {
		if(instance==null) {
			instance = new Branding();
		}
		return instance;
	}
	
	public String getTypeVersion() {
		return typeVersion;
	}

	public void setTypeVersion(String type_version) {
		this.typeVersion = type_version;
	}

	public String getDescriptionVersion() {
		return descriptionVersion;
	}

	public void setDescriptionVersion(String description_version) {
		this.descriptionVersion = description_version;
	}
}

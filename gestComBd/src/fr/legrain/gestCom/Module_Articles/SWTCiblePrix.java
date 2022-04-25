package fr.legrain.gestCom.Module_Articles;

import fr.legrain.lib.data.ModelObject;

public class SWTCiblePrix extends ModelObject {
	
	public static String TYPE_TIERS = "Tiers";
	public static String TYPE_TARIF = "Type tarif";
	
	private Integer id;
	private String code;
	private String libelle;
	private String type;
	
	public SWTCiblePrix() {
		super();
	}
	
	public SWTCiblePrix(String code, String libelle, String type) {
		super();
		this.code = code;
		this.libelle = libelle;
		this.type = type;
	}
	
	public void setSWTCiblePrix(SWTCiblePrix swtCiblePrix) {
		this.id = swtCiblePrix.id;
		this.code = swtCiblePrix.code;
		this.libelle = swtCiblePrix.libelle;
		this.type = swtCiblePrix.type;
	}
	
	public SWTCiblePrix copy(SWTCiblePrix swtCiblePrix){
		SWTCiblePrix swtCiblePrixLoc = new SWTCiblePrix();
		swtCiblePrixLoc.setId(swtCiblePrix.id);
		swtCiblePrixLoc.setCode(swtCiblePrix.code);
		swtCiblePrixLoc.setLibelle(swtCiblePrix.libelle);
		swtCiblePrixLoc.setType(swtCiblePrix.type);
		return swtCiblePrixLoc;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		firePropertyChange("id", this.id, this.id = id);
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		firePropertyChange("code", this.code, this.code = code);
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		firePropertyChange("libelle", this.libelle, this.libelle = libelle);
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		firePropertyChange("type", this.type, this.type = type);
	}

}

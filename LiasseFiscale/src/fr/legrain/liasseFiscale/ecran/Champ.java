package fr.legrain.liasseFiscale.ecran;

public class Champ {
	private String label;
	private Integer order; //position
	private String type;
	private String code;
	private String valeur;
	private Boolean enable;
	private Boolean visible;
	
	public Champ() {}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String labal) {
		this.label = labal;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValeur() {
		return valeur;
	}

	public void setValeur(String valeur) {
		this.valeur = valeur;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	
	
}

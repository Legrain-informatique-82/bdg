package fr.legrain.document.RechercheDocument.editions;

public 	class InfosPresentation {
	private String titre;
	private String taille;
	private Class type;
	private String typeString;
	private boolean total;

	public InfosPresentation(String titre, String taille, Class type,
			String typeString, boolean total) {
		super();
		this.titre = titre;
		this.taille = taille;
		this.type = type;
		this.typeString = typeString;
		this.total = total;
	}

	public InfosPresentation() {

	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getTaille() {
		return taille;
	}

	public void setTaille(String taille) {
		this.taille = taille;
	}

	public Class getType() {
		return type;
	}

	public void setType(Class type) {
		this.type = type;
	}

	public String getTypeString() {
		return typeString;
	}

	public void setTypeString(String typeString) {
		this.typeString = typeString;
	}

	public boolean isTotal() {
		return total;
	}

	public void setTotal(boolean total) {
		this.total = total;
	}

}

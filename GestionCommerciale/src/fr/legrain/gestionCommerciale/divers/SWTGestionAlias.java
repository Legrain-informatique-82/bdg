package fr.legrain.gestionCommerciale.divers;

import fr.legrain.lib.data.ModelObject;

public class SWTGestionAlias extends ModelObject {
	private String SERVEUR;
	private String ALIAS;
	private String CHEMINBASE;
	private String RESEAU;
	

	
	public SWTGestionAlias(){}
	
	public SWTGestionAlias(String serveur,String alias,String cheminBase,String reseau) {
		this.setSERVEUR(serveur);
		this.setALIAS(alias);
		this.setCHEMINBASE(cheminBase);
		this.setRESEAU(reseau);
	}

	public String getSERVEUR() {
		return SERVEUR;
	}

	public void setSERVEUR(String SERVEUR) {
		firePropertyChange("SERVEUR", this.SERVEUR, this.SERVEUR = SERVEUR);
	}

	public String getALIAS() {
		return ALIAS;
	}

	public void setALIAS(String ALIAS) {
		firePropertyChange("ALIAS", this.ALIAS, this.ALIAS = ALIAS);
	}

	public String getCHEMINBASE() {
		return CHEMINBASE;
	}

	public void setCHEMINBASE(String CHEMINBASE) {
		firePropertyChange("CHEMINBASE", this.CHEMINBASE, this.CHEMINBASE = CHEMINBASE);
	}

	public String getRESEAU() {
		return RESEAU;
	}

	public void setRESEAU(String RESEAU) {
		firePropertyChange("RESEAU", this.RESEAU, this.RESEAU = RESEAU);
	}
}


package fr.legrain.exportation.divers;

import fr.legrain.exportation.ExportationPlugin;
import fr.legrain.exportation.preferences.PreferenceConstants;

public class OptionExportation {
	Boolean tous;
	Boolean acomptes;
	Boolean apporteurs;
	Boolean reglementSimple;
	Boolean documentLies;
	Boolean reglementLies;
	Boolean pointages;
	Boolean remise;
	
	
	public Boolean getTous() {
		return tous;
	}
	public void setTous(Boolean tous) {
		this.tous = tous;
	}
	public Boolean getAcomptes() {
		return acomptes;
	}
	public void setAcomptes(Boolean acomptes) {
		this.acomptes = acomptes;
	}
	public Boolean getReglementSimple() {
		return reglementSimple;
	}
	public void setReglementSimple(Boolean reglementSimple) {
		this.reglementSimple = reglementSimple;
	}
	public Boolean getDocumentLies() {
		return documentLies;
	}
	public void setDocumentLies(Boolean documentLies) {
		this.documentLies = documentLies;
	}
	public Boolean getReglementLies() {
		return reglementLies;
	}
	public void setReglementLies(Boolean reglementLies) {
		this.reglementLies = reglementLies;
	}
	public Boolean getPointages() {
		return pointages;
	}
	public void setPointages(Boolean pointages) {
		this.pointages = pointages;
	}
	public Boolean getRemise() {
		return remise;
	}
	public void setRemise(Boolean remise) {
		this.remise = remise;
	}
	public Boolean getApporteurs() {
		return apporteurs;
	}
	public void setApporteurs(Boolean apporteurs) {
		this.apporteurs = apporteurs;
	}
}

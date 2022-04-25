package fr.legrain.creationDocMultipleTiers.divers;

import java.util.Date;

import javax.swing.JComponent;

import org.eclipse.swt.widgets.Control;

import fr.legrain.lib.gui.ParamAffiche;

public class ParamAffichecreationDocMultiple extends ParamAffiche {
	private String titreFormulaire = "Liste des documents à prendre en compte dans le(s) document(s) à créer";
	private String titreGrille = "Liste des documents à prendre en compte dans le(s) document(s) à créer";
	private String sousTitre = "Gestion de la création de document à partir d'une sélection de documents";
	private JComponent focusDefaut = null;
	private Control focusDefautSWT = null;
	private int idTiers;
	private int idFacture;
	private Date dateDeb=null;
	private Date dateFin=null;
	protected Boolean enregistreDirect=false; 
	
	
	public JComponent getFocusDefaut() {
		return focusDefaut;
	}
	public void setFocusDefaut(JComponent focusDefaut) {
		this.focusDefaut = focusDefaut;
	}
	public String getSousTitre() {
		return sousTitre;
	}
	public int getIdTiers() {
		return idTiers;
	}
	public void setIdTiers(int idTiers) {
		this.idTiers = idTiers;
	}
	public void setSousTitre(String sousTitre) {
		this.sousTitre = sousTitre;
	}
	public String getTitreFormulaire() {
		return titreFormulaire;
	}
	public void setTitreFormulaire(String titreFormulaire) {
		this.titreFormulaire = titreFormulaire;
	}
	public String getTitreGrille() {
		return titreGrille;
	}
	public void setTitreGrille(String titreGrille) {
		this.titreGrille = titreGrille;
	}
	public Control getFocusDefautSWT() {
		return focusDefautSWT;
	}
	public void setFocusDefautSWT(Control focusDefautSWT) {
		this.focusDefautSWT = focusDefautSWT;
	}
	public Date getDateDeb() {
		return dateDeb;
	}
	public void setDateDeb(Date dateDeb) {
		this.dateDeb = dateDeb;
	}
	public Date getDateFin() {
		return dateFin;
	}
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}
	public int getIdFacture() {
		return idFacture;
	}
	public void setIdFacture(int idFacture) {
		this.idFacture = idFacture;
	}
	public Boolean getEnregistreDirect() {
		return enregistreDirect;
	}
	public void setEnregistreDirect(Boolean enregistreDirect) {
		this.enregistreDirect = enregistreDirect;
	}

//	public JPABaseControllerSWTStandard getControllerRecherche() {
//		return controllerRecherche;
//	}
//	public void setControllerRecherche(
//			JPABaseControllerSWTStandard controllerRecherche) {
//		this.controllerRecherche = controllerRecherche;
//	}

}

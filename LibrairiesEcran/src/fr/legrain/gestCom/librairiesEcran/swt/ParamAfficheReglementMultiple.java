package fr.legrain.gestCom.librairiesEcran.swt;

import java.util.Date;

import javax.swing.JComponent;

import org.eclipse.swt.widgets.Control;

import fr.legrain.lib.gui.ParamAffiche;

public class ParamAfficheReglementMultiple extends ParamAffiche {
	private String titreFormulaire = "";
	private String titreGrille = "";
	private String sousTitre = "";
	private JComponent focusDefaut = null;
	private Control focusDefautSWT = null;
	private int idTiers;
	private int idFacture;
	private Date dateDeb=null;
	private Date dateFin=null;
	private String codeReglement;
//	private JPABaseControllerSWTStandard controllerRecherche;
	
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
	public String getCodeReglement() {
		return codeReglement;
	}
	public void setCodeReglement(String codeReglement) {
		this.codeReglement = codeReglement;
	}
//	public JPABaseControllerSWTStandard getControllerRecherche() {
//		return controllerRecherche;
//	}
//	public void setControllerRecherche(
//			JPABaseControllerSWTStandard controllerRecherche) {
//		this.controllerRecherche = controllerRecherche;
//	}

}

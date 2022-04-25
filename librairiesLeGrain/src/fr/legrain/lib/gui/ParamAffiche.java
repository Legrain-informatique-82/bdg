package fr.legrain.lib.gui;

import javax.swing.JComponent;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Control;

import fr.legrain.lib.data.AbstractLgrDAO;
import fr.legrain.lib.data.ModeObjet;

/**
 * <p>Title: </p>
 * <p>Description: Parametre pour les fonctions d'affichage.</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Le Grain SA</p>
 * @author nicolas
 * @version 1.0
 */

public class ParamAffiche {

	protected ModeObjet.EnumModeObjet modeEcran; //mode dans lequel l'ecran doit être lors de son affichage
	protected Object ecranAppelant;
	protected JComponent focus;
	protected Control focusSWT;
	protected String codeDocument = null;
	protected Integer idDocument = null;
	protected String idFiche = null;
	protected String titreFormulaire = null;
	protected String titreGrille = null;
	protected String sousTitre = null;
	protected JComponent focusDefaut = null;
	protected Control focusDefautSWT = null;  
	protected ISelection selection = null;
	protected Object selected = null;
	protected Boolean EnregistreEtFerme=false;
	protected Boolean Raffraichi=true;
    protected Object masterEntity=null;
    protected AbstractLgrDAO masterDao=null;

	public ParamAffiche() {}
	public ParamAffiche(Boolean raffraichi) {
		this.Raffraichi=raffraichi;
	}
	public Object getEcranAppelant() {
		return ecranAppelant;
	}

	public void setEcranAppelant(Object ecranAppelant) {
		this.ecranAppelant = ecranAppelant;
	}

	public ModeObjet.EnumModeObjet getModeEcran() {
		return modeEcran;
	}

	public void setModeEcran(ModeObjet.EnumModeObjet modeEcran) {
		this.modeEcran = modeEcran;
	}

	public JComponent getFocus() {
		return focus;
	}

	public void setFocus(JComponent focus) {
		this.focus = focus;
	}

	public Control getFocusSWT() {
		return focusSWT;
	}

	public void setFocusSWT(Control focusSWT) {
		this.focusSWT = focusSWT;
	}

	public String getCodeDocument() {
		return codeDocument;
	}

	public void setCodeDocument(String codeDocument) {
		this.codeDocument = codeDocument;
	}

	public Integer getIdDocument() {
		return idDocument;
	}

	public void setIdDocument(Integer idDocument) {
		this.idDocument = idDocument;
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

	public String getSousTitre() {
		return sousTitre;
	}

	public void setSousTitre(String sousTitre) {
		this.sousTitre = sousTitre;
	}

	public JComponent getFocusDefaut() {
		return focusDefaut;
	}

	public void setFocusDefaut(JComponent focusDefaut) {
		this.focusDefaut = focusDefaut;
	}

	public Control getFocusDefautSWT() {
		return focusDefautSWT;
	}

	public void setFocusDefautSWT(Control focusDefautSWT) {
		this.focusDefautSWT = focusDefautSWT;
	}

	public ISelection getSelection() {
		return selection;
	}

	public void setSelection(ISelection selection) {
		this.selection = selection;
	}

	public String getIdFiche() {
		return idFiche;
	}

	public void setIdFiche(String idFiche) {
		this.idFiche = idFiche;
	}

	public Boolean getEnregistreEtFerme() {
		return EnregistreEtFerme;
	}

	public void setEnregistreEtFerme(Boolean enregistreEtFerme) {
		EnregistreEtFerme = enregistreEtFerme;
	}

	public Boolean getRaffraichi() {
		return Raffraichi;
	}

	public void setRaffraichi(Boolean raffraichi) {
		Raffraichi = raffraichi;
	}
	public Object getSelected() {
		return selected;
	}
	public void setSelected(Object selected) {
		this.selected = selected;
	}
	public Object getMasterEntity() {
		return masterEntity;
	}
	public void setMasterEntity(Object masterEntity) {
		this.masterEntity = masterEntity;
	}
	public AbstractLgrDAO getMasterDao() {
		return masterDao;
	}
	public void setMasterDao(AbstractLgrDAO masterDao) {
		this.masterDao = masterDao;
	}

}

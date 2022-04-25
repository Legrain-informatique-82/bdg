package fr.legrain.generationdocument.divers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JComponent;

import org.eclipse.swt.widgets.Control;

import fr.legrain.gestCom.Module_Document.IDocumentTiers;
import fr.legrain.lib.gui.ParamAffiche;

public class ParamAfficheChoixGenerationDocument extends ParamAffiche {
	public static final String C_TITRE_FORMULAIRE = "Selection des tiers";
	public static final String C_TITRE_GRILLE = "liste des tiers sélectionnés";
	public static final String C_SOUS_TITRE = "Liste des tiers sélectionnés";
	private String typeSrc = "";
	private String typeDest = "";
	private IDocumentTiers documentSrc = null;
	private List<IDocumentTiers> listeDocumentSrc=null;
	private String libelle ="";
	private Date dateDocument = null;
	private Date dateLivraison = null;
	private Boolean repriseReferenceSrc=true;
	private Boolean repriseLibelleSrc=false;
	private Boolean repriseAucun=false;
	private Boolean retour=false;
	private String codeTiers="";
	private boolean tiersModifiable=true;
	private  boolean relation=false;
	private boolean multiple=false;
	private boolean generationModele=false;
	
	protected Boolean ligneSeparatrice=false;
//	protected Boolean RecupLibelleSeparateurDoc=false;
	
	
	public ParamAfficheChoixGenerationDocument() {
		titreFormulaire = C_TITRE_FORMULAIRE;
		titreGrille = C_TITRE_GRILLE;
		sousTitre = C_SOUS_TITRE;
		if (listeDocumentSrc==null)listeDocumentSrc=new ArrayList<IDocumentTiers>(0);
		listeDocumentSrc.clear();
		repriseReferenceSrc=true;
		repriseLibelleSrc=false;
		codeTiers="";
		tiersModifiable=true;
		ligneSeparatrice=false;
		repriseAucun=false;
//		RecupLibelleSeparateurDoc=false;
	}

	public JComponent getFocusDefaut() {
		return focusDefaut;
	}
	
	public void setFocusDefaut(JComponent focusDefaut) {
		this.focusDefaut = focusDefaut;
	}
	
	public String getSousTitre() {
		return sousTitre;
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

	public String getTypeSrc() {
		return typeSrc;
	}

	public void setTypeSrc(String typeSrc) {
		this.typeSrc = typeSrc;
	}

	public IDocumentTiers getDocumentSrc() {
		return documentSrc;
	}

	public void setDocumentSrc(IDocumentTiers documentSrc) {
		listeDocumentSrc.add(documentSrc);
		this.documentSrc = documentSrc;
	}

	public List<IDocumentTiers> getListeDocumentSrc() {
		return listeDocumentSrc;
	}

	public void setListeDocumentSrc(List<IDocumentTiers> listeDocumentSrc) {
		this.listeDocumentSrc = listeDocumentSrc;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public Date getDateDocument() {
		return dateDocument;
	}

	public void setDateDocument(Date dateDocument) {
		this.dateDocument = dateDocument;
	}

	public Date getDateLivraison() {
		return dateLivraison;
	}

	public void setDateLivraison(Date dateLivraison) {
		this.dateLivraison = dateLivraison;
	}

	public String getTypeDest() {
		return typeDest;
	}

	public void setTypeDest(String typeDest) {
		this.typeDest = typeDest;
	}

	public Boolean getRepriseReferenceSrc() {
		return repriseReferenceSrc;
	}

	public void setRepriseReferenceSrc(Boolean repriseReferenceSrc) {
		this.repriseReferenceSrc = repriseReferenceSrc;
	}

	public Boolean getRepriseLibelleSrc() {
		return repriseLibelleSrc;
	}

	public void setRepriseLibelleSrc(Boolean repriseLibelleSrc) {
		this.repriseLibelleSrc = repriseLibelleSrc;
	}

	public Boolean getRetour() {
		return retour;
	}

	public void setRetour(Boolean retour) {
		this.retour = retour;
	}

	public String getCodeTiers() {
		return codeTiers;
	}

	public void setCodeTiers(String codeTiers) {
		this.codeTiers = codeTiers;
	}

	public boolean isTiersModifiable() {
		return tiersModifiable;
	}

	public void setTiersModifiable(boolean tiersModifiable) {
		this.tiersModifiable = tiersModifiable;
	}

	public boolean isRelation() {
		return relation;
	}

	public void setRelation(boolean relation) {
		this.relation = relation;
	}

	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

	public Boolean getLigneSeparatrice() {
		return ligneSeparatrice;
	}

	public void setLigneSeparatrice(Boolean ligneSeparatrice) {
		this.ligneSeparatrice = ligneSeparatrice;
	}

//	public Boolean getRecupLibelleSeparateurDoc() {
//		return RecupLibelleSeparateurDoc;
//	}
//
//	public void setRecupLibelleSeparateurDoc(Boolean recupLibelleSeparateurDoc) {
//		RecupLibelleSeparateurDoc = recupLibelleSeparateurDoc;
//	}

	public Boolean getRepriseAucun() {
		return repriseAucun;
	}

	public void setRepriseAucun(Boolean repriseAucun) {
		this.repriseAucun = repriseAucun;
	}

	public boolean isGenerationModele() {
		return generationModele;
	}

	public void setGenerationModele(boolean generationModele) {
		this.generationModele = generationModele;
	}



 
}

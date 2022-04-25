package fr.legrain.gestCom.Module_Tiers;

import fr.legrain.lib.data.ModelObject;

// Generated Mar 1, 2007 11:26:10 AM by Hibernate Tools 3.2.0.b9

/**
 * SWTAdresse generated by hbm2java
 */
public class IHMIdentiteTiers extends ModelObject {

	private String codeTCivilite;
	private String nomTiers;
	private String prenomTiers;
	private String nomEntreprise;
	private String codeTEntite;
	private String tvaIComCompl;
	private String codeCompta;
	private String compte;
	private String numeroTelephone;
	private String adresseEmail;
	private String adresseWeb;
	private String commentaire;
	private String siretCompl;
	private String codeTTvaDoc;
	

	public IHMIdentiteTiers() {
	}

	public void setIHMIdentiteTiers(IHMIdentiteTiers swtInfosTiers) {
		setCodeTCivilite(swtInfosTiers.codeTCivilite);
		setCodeCompta(swtInfosTiers.codeCompta);
		setCodeTEntite(swtInfosTiers.codeTEntite);
		setAdresseEmail(swtInfosTiers.adresseEmail);
		setCompte(swtInfosTiers.compte);
		setNomEntreprise(swtInfosTiers.nomEntreprise);
		setNomTiers(swtInfosTiers.nomTiers);
		setNumeroTelephone(swtInfosTiers.numeroTelephone);
		setPrenomTiers(swtInfosTiers.prenomTiers);
		setTvaIComCompl(swtInfosTiers.tvaIComCompl);
		setAdresseWeb(swtInfosTiers.adresseWeb);
		setSiretCompl(swtInfosTiers.siretCompl);
		setCommentaire(swtInfosTiers.commentaire);
		setCodeTTvaDoc(swtInfosTiers.codeTTvaDoc);
	}


	public static IHMIdentiteTiers copy(IHMIdentiteTiers ihmIdentiteTiers){
		IHMIdentiteTiers ihmIdentiteTiersLoc = new IHMIdentiteTiers();
		ihmIdentiteTiersLoc.setCodeTCivilite(ihmIdentiteTiers.getCodeTCivilite());
		ihmIdentiteTiersLoc.setCodeTEntite(ihmIdentiteTiers.getCodeTEntite());
		ihmIdentiteTiersLoc.setCodeCompta(ihmIdentiteTiers.getCodeCompta());
		ihmIdentiteTiersLoc.setAdresseEmail(ihmIdentiteTiers.getAdresseEmail());
		ihmIdentiteTiersLoc.setCompte(ihmIdentiteTiers.getCodeCompta());
		ihmIdentiteTiersLoc.setNomEntreprise(ihmIdentiteTiers.getNomEntreprise());
		ihmIdentiteTiersLoc.setNomTiers(ihmIdentiteTiers.getNomTiers());
		ihmIdentiteTiersLoc.setNumeroTelephone(ihmIdentiteTiers.getNumeroTelephone());
		ihmIdentiteTiersLoc.setPrenomTiers(ihmIdentiteTiers.getPrenomTiers());
		ihmIdentiteTiersLoc.setTvaIComCompl(ihmIdentiteTiers.getTvaIComCompl());//1
		ihmIdentiteTiersLoc.setAdresseWeb(ihmIdentiteTiers.getAdresseWeb());
		ihmIdentiteTiersLoc.setSiretCompl(ihmIdentiteTiers.getSiretCompl());
		ihmIdentiteTiersLoc.setCommentaire(ihmIdentiteTiers.getCommentaire());
		ihmIdentiteTiersLoc.setCodeTTvaDoc(ihmIdentiteTiers.getCodeTTvaDoc());
		return ihmIdentiteTiersLoc;
	}



	public String getCodeCompta() {
		return codeCompta;
	}

	public void setCodeCompta(String codeCompta) {
		firePropertyChange("codeCompta", this.codeCompta, this.codeCompta = codeCompta);
	}

	public String getCompte() {
		return compte;
	}

	public void setCompte(String compte) {
		firePropertyChange("compte", this.compte, this.compte = compte);
	}

	public String getCodeTCivilite() {
		return codeTCivilite;
	}

	public void setCodeTCivilite(String codeTCivilite) {
		firePropertyChange("codeTCivilite", this.codeTCivilite, this.codeTCivilite = codeTCivilite);
	}

	public String getNomTiers() {
		return nomTiers;
	}

	public void setNomTiers(String nomTiers) {
		firePropertyChange("nomTiers", this.nomTiers, this.nomTiers = nomTiers);
	}

	public String getPrenomTiers() {
		return prenomTiers;
	}

	public void setPrenomTiers(String prenomTiers) {
		firePropertyChange("prenomTiers", this.prenomTiers, this.prenomTiers = prenomTiers);
	}

	public String getNomEntreprise() {
		return nomEntreprise;
	}

	public void setNomEntreprise(String nomEntreprise) {
		firePropertyChange("nomEntreprise", this.nomEntreprise, this.nomEntreprise = nomEntreprise);
	}

	public String getCodeTEntite() {
		return codeTEntite;
	}

	public void setCodeTEntite(String codeTEntite) {
		firePropertyChange("codeTEntite", this.codeTEntite, this.codeTEntite = codeTEntite);
	}

	public String getTvaIComCompl() {
		return tvaIComCompl;
	}

	public void setTvaIComCompl(String tvaIComCompl) {
		firePropertyChange("tvaIComCompl", this.tvaIComCompl, this.tvaIComCompl = tvaIComCompl);
	}

	public String getNumeroTelephone() {
		return numeroTelephone;
	}

	public void setNumeroTelephone(String numeroTelephone) {
		firePropertyChange("numeroTelephone", this.numeroTelephone, this.numeroTelephone = numeroTelephone);
	}

	public String getAdresseEmail() {
		return adresseEmail;
	}

	public void setAdresseEmail(String adresseEmail) {
		firePropertyChange("adresseEmail", this.adresseEmail, this.adresseEmail = adresseEmail);
	}

	public String getAdresseWeb() {
		return adresseWeb;
	}

	public void setAdresseWeb(String adresseWeb) {
		firePropertyChange("adresseWeb", this.adresseWeb, this.adresseWeb = adresseWeb);
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		firePropertyChange("commentaire", this.commentaire, this.commentaire = commentaire);
	}

	public String getSiretCompl() {
		return siretCompl;
	}

	public void setSiretCompl(String siretCompl) {
		firePropertyChange("siretCompl", this.siretCompl, this.siretCompl = siretCompl);
	}

	public String getCodeTTvaDoc() {
		return codeTTvaDoc;
	}

	public void setCodeTTvaDoc(String codeTTvaDoc) {
		firePropertyChange("codeTTvaDoc", this.codeTTvaDoc, this.codeTTvaDoc = codeTTvaDoc);
	}




}

package fr.legrain.gestCom.Module_Tiers;


import java.util.Date;

import fr.legrain.lib.data.ModelObject;

public class SWTTiers extends ModelObject {


	private Integer idTiers;
	private String codeTiers;
	private String codeCompta;
	private String compte;
	private String nomTiers;
	private String prenomTiers;
	private String surnomTiers;
	private Boolean actifTiers;
	private Boolean ttcTiers;
	private Integer idTCivilite;
	private String codeTCivilite;
	private Integer idEntreprise;
	private String nomEntreprise;
	private String liblEntreprise;
	private Integer idTTiers;
	private String codeTTiers;
	private String libelleTTiers;
	private Integer idCommentaire;
	private String commentaire;
	private String codeTTvaDoc;
	private Integer idTTvaDoc;
	
	private Integer ID_I_BANQUE;
	private String CODE_BANQUE;
	private String LIBC_BANQUE;
	private String LIBL_BANQUE;
	
	private Integer idTEntite;
	private String codeTEntite;
	private String tvaIComCompl;
	private String siretCompl;
	private String accise;
	private String ics;
	
	private Integer idAdresse;
	private String adresse1Adresse;
	private String adresse2Adresse;
	private String adresse3Adresse;
	private String codepostalAdresse;
	private String villeAdresse;
	private String paysAdresse;
	
	private Integer idEmail;
	private String adresseEmail;
	
	private String adresseWeb;
	private String numeroTelephone;
	
//	private Integer idFamilleTiers;
//	private String codeFamille;
	
	private String CODE_T_ENTITE_ENTREPRISE;
	
	private String codeCPaiement;
	private String codeTPaiement;
	
	private String codeTTarif;
	private Boolean accepte;
	
	private Date dateAnniv=new Date();
	
	public SWTTiers() {
	}
	

	public Boolean getActifTiers() {
		return actifTiers;
	}

	public void setActifTiers(Boolean ACTIF_TIERS) {
		firePropertyChange("actifTiers", this.actifTiers, this.actifTiers = ACTIF_TIERS);
	}

	public String getCODE_BANQUE() {
		return CODE_BANQUE;
	}

	public void setCODE_BANQUE(String CODE_BANQUE) {
		firePropertyChange("CODE_BANQUE", this.CODE_BANQUE, this.CODE_BANQUE = CODE_BANQUE);
	}

	public String getCodeCompta() {
		return codeCompta;
	}

	public void setCodeCompta(String CODE_COMPTA) {
		firePropertyChange("codeCompta", this.codeCompta, this.codeCompta = CODE_COMPTA);
	}

	public String getNomEntreprise() {
		return nomEntreprise;
	}

	public void setNomEntreprise(String NOM_ENTREPRISE) {
		firePropertyChange("nomEntreprise", this.nomEntreprise, this.nomEntreprise = NOM_ENTREPRISE);
	}

	public String getCodeTCivilite() {
		return codeTCivilite;
	}

	public void setCodeTCivilite(String CODE_T_CIVILITE) {
		firePropertyChange("codeTCivilite", this.codeTCivilite, this.codeTCivilite = CODE_T_CIVILITE);
	}

	public String getCodeTEntite() {
		return codeTEntite;
	}

	public void setCodeTEntite(String CODE_T_ENTITE) {
		firePropertyChange("codeTEntite", this.codeTEntite, this.codeTEntite = CODE_T_ENTITE);
	}

	public String getCodeTTiers() {
		return codeTTiers;
	}

	public void setCodeTTiers(String CODE_T_TIERS) {
		firePropertyChange("codeTTiers", this.codeTTiers, this.codeTTiers = CODE_T_TIERS);
	}

	public String getCodeTiers() {
		return codeTiers;
	}

	public void setCodeTiers(String CODE_TIERS) {
		firePropertyChange("codeTiers", this.codeTiers, this.codeTiers = CODE_TIERS);
	}

	public String getCompte() {
		return compte;
	}

	public void setCompte(String COMPTE) {
		firePropertyChange("compte", this.compte, this.compte = COMPTE);
	}

	public Integer getIdCommentaire() {
		return idCommentaire;
	}

	public void setIdCommentaire(Integer ID_COMMENTAIRE) {
		firePropertyChange("idCommentaire", this.idCommentaire, this.idCommentaire = ID_COMMENTAIRE);
	}

	public Integer getIdEntreprise() {
		return idEntreprise;
	}

	public void setIdEntreprise(Integer ID_ENTREPRISE) {
		firePropertyChange("idEntreprise", this.idEntreprise, this.idEntreprise = ID_ENTREPRISE);
	}

	public Integer getID_I_BANQUE() {
		return ID_I_BANQUE;
	}

	public void setID_I_BANQUE(Integer ID_I_BANQUE) {
		firePropertyChange("ID_I_BANQUE", this.ID_I_BANQUE, this.ID_I_BANQUE = ID_I_BANQUE);
	}

	public Integer getIdTCivilite() {
		return idTCivilite;
	}

	public void setIdTCivilite(Integer ID_T_CIVILITE) {
		firePropertyChange("ID_T_CIVILITE", this.idTCivilite, this.idTCivilite = ID_T_CIVILITE);
	}

	public Integer getIdTEntite() {
		return idTEntite;
	}

	public void setIdTEntite(Integer ID_T_ENTITE) {
		firePropertyChange("idTEntite", this.idTEntite, this.idTEntite = ID_T_ENTITE);
	}

	public Integer getIdTTiers() {
		return idTTiers;
	}

	public void setIdTTiers(Integer ID_T_TIERS) {
		firePropertyChange("idTTiers", this.idTTiers, this.idTTiers = ID_T_TIERS);
	}

	public Integer getIdTiers() {
		return idTiers;
	}

	public void setIdTiers(Integer ID_TIERS) {
		firePropertyChange("idTiers", this.idTiers, this.idTiers = ID_TIERS);
	}

	public String getLIBC_BANQUE() {
		return LIBC_BANQUE;
	}

	public void setLIBC_BANQUE(String LIBC_BANQUE) {
		firePropertyChange("LIBC_BANQUE", this.LIBC_BANQUE, this.LIBC_BANQUE = LIBC_BANQUE);
	}

	public String getLibelleTTiers() {
		return libelleTTiers;
	}

	public void setLibelleTTiers(String LIBELLE_T_TIERS) {
		firePropertyChange("libelleTTiers", this.libelleTTiers, this.libelleTTiers = LIBELLE_T_TIERS);
	}

	public String getLIBL_BANQUE() {
		return LIBL_BANQUE;
	}

	public void setLIBL_BANQUE(String LIBL_BANQUE) {
		firePropertyChange("LIBL_BANQUE", this.LIBL_BANQUE, this.LIBL_BANQUE = LIBL_BANQUE);
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String LIBL_COMMENTAIRE) {
		firePropertyChange("commentaire", this.commentaire, this.commentaire = LIBL_COMMENTAIRE);
	}

	public String getLiblEntreprise() {
		return liblEntreprise;
	}

	public void setLiblEntreprise(String LIBL_ENTREPRISE) {
		firePropertyChange("liblEntreprise", this.liblEntreprise, this.liblEntreprise = LIBL_ENTREPRISE);
	}

	public String getNomTiers() {
		return nomTiers;
	}

	public void setNomTiers(String NOM_TIERS) {
		firePropertyChange("nomTiers", this.nomTiers, this.nomTiers = NOM_TIERS);
	}

	public String getPrenomTiers() {
		return prenomTiers;
	}

	public void setPrenomTiers(String PRENOM_TIERS) {
		firePropertyChange("prenomTiers", this.prenomTiers, this.prenomTiers = PRENOM_TIERS);
	}

	public String getSiretCompl() {
		return siretCompl;
	}

	public void setSiretCompl(String SIRET_COMPL) {
		firePropertyChange("siretCompl", this.siretCompl, this.siretCompl = SIRET_COMPL);
	}

	public String getSurnomTiers() {
		return surnomTiers;
	}

	public void setSurnomTiers(String SURNOM_TIERS) {
		firePropertyChange("surnomTiers", this.surnomTiers, this.surnomTiers = SURNOM_TIERS);
	}

	public Boolean getTtcTiers() {
		return ttcTiers;
	}

	public void setTtcTiers(Boolean TTC_TIERS) {
		firePropertyChange("ttcTiers", this.ttcTiers, this.ttcTiers = TTC_TIERS);
	}

	public String getTvaIComCompl() {
		return tvaIComCompl;
	}

	public void setTvaIComCompl(String TVA_I_COM_COMPL) {
		firePropertyChange("tvaIComCompl", this.tvaIComCompl, this.tvaIComCompl = TVA_I_COM_COMPL);
	}
	
	public String getCODE_T_ENTITE_ENTREPRISE() {
		return CODE_T_ENTITE_ENTREPRISE;
	}

	public void setCODE_T_ENTITE_ENTREPRISE(String CODE_T_ENTITE_ENTREPRISE) {
		firePropertyChange("CODE_T_ENTITE_ENTREPRISE", this.CODE_T_ENTITE_ENTREPRISE, this.CODE_T_ENTITE_ENTREPRISE = CODE_T_ENTITE_ENTREPRISE);
	}

	public void setSwtTiers(SWTTiers swtTiers){		
			this.idTiers = swtTiers.idTiers;       				//1
			this.codeTiers = swtTiers.codeTiers;					//2
			this.codeCompta = swtTiers.codeCompta;				//3
			this.compte = swtTiers.compte;							//4
			this.nomTiers = swtTiers.nomTiers;					//5
			this.prenomTiers = swtTiers.prenomTiers;				//6
			this.surnomTiers = swtTiers.surnomTiers;				//7
			this.actifTiers = swtTiers.actifTiers;				//8
			this.ttcTiers = swtTiers.ttcTiers;					//9
			this.idTCivilite = swtTiers.idTCivilite;			//10
			this.codeTCivilite = swtTiers.codeTCivilite;		//11
			this.idEntreprise = swtTiers.idEntreprise;			//12
			this.nomEntreprise = swtTiers.nomEntreprise;		//13
			this.liblEntreprise = swtTiers.liblEntreprise;        //14
			this.idTTiers = swtTiers.idTTiers;					//15
			this.codeTTiers = swtTiers.codeTTiers;				//16
			this.libelleTTiers = swtTiers.libelleTTiers;		//17
			this.idCommentaire = swtTiers.idCommentaire;			//18
			this.commentaire = swtTiers.commentaire;		//19
			this.ID_I_BANQUE = swtTiers.ID_I_BANQUE;				//20
			this.CODE_BANQUE = swtTiers.CODE_BANQUE;				//21
			this.LIBC_BANQUE = swtTiers.LIBC_BANQUE;				//22
			this.LIBL_BANQUE = swtTiers.LIBL_BANQUE;				//23
			this.idTEntite = swtTiers.idTEntite;				//24
			this.codeTEntite = swtTiers.codeTEntite;			//25
			this.tvaIComCompl = swtTiers.tvaIComCompl;		//26
			this.siretCompl = swtTiers.siretCompl;
			this.accise = swtTiers.accise;//27
			this.ics = swtTiers.ics;//28
			this.CODE_T_ENTITE_ENTREPRISE = swtTiers.CODE_T_ENTITE_ENTREPRISE;				//27
			
			this.adresse1Adresse=swtTiers.adresse1Adresse;
			this.adresse2Adresse=swtTiers.adresse2Adresse;
			this.adresse3Adresse=swtTiers.adresse3Adresse;
			this.codepostalAdresse=swtTiers.codepostalAdresse;
			this.villeAdresse=swtTiers.villeAdresse;
			this.paysAdresse=swtTiers.paysAdresse;
			
			this.adresseWeb=swtTiers.adresseWeb;
			this.adresseEmail=swtTiers.adresseEmail;
			this.numeroTelephone=swtTiers.numeroTelephone;
			this.codeTTvaDoc=swtTiers.codeTTvaDoc;
			this.idTTvaDoc=swtTiers.idTTvaDoc;
			this.dateAnniv=swtTiers.dateAnniv;
//			this.idFamilleTiers=swtTiers.idFamilleTiers;
//			this.codeFamille=swtTiers.codeFamille;
	}
	
	public static SWTTiers copy(SWTTiers swtTiers){
		SWTTiers swtTiersLoc = new SWTTiers();
		swtTiersLoc.setIdTiers(swtTiers.idTiers);       				//1
		swtTiersLoc.setCodeTiers(swtTiers.codeTiers);					//2
		swtTiersLoc.setCodeCompta(swtTiers.codeCompta);				//3
		swtTiersLoc.setCompte(swtTiers.compte);							//4
		swtTiersLoc.setNomTiers(swtTiers.nomTiers);					//5
		swtTiersLoc.setPrenomTiers(swtTiers.prenomTiers);				//6
		swtTiersLoc.setSurnomTiers(swtTiers.surnomTiers);				//7
		swtTiersLoc.setActifTiers(swtTiers.actifTiers);				//8
		swtTiersLoc.setTtcTiers(swtTiers.ttcTiers);					//9
		swtTiersLoc.setIdTCivilite(swtTiers.idTCivilite);			//10
		swtTiersLoc.setCodeTCivilite(swtTiers.codeTCivilite);		//11
		swtTiersLoc.setIdEntreprise(swtTiers.idEntreprise);			//12
		swtTiersLoc.setNomEntreprise(swtTiers.nomEntreprise);		//13
		swtTiersLoc.setLiblEntreprise(swtTiers.liblEntreprise);        //14
		swtTiersLoc.setIdTTiers(swtTiers.idTTiers);					//15
		swtTiersLoc.setCodeTTiers(swtTiers.codeTTiers);				//16
		swtTiersLoc.setLibelleTTiers(swtTiers.libelleTTiers);		//17
		swtTiersLoc.setIdCommentaire(swtTiers.idCommentaire);			//18
		swtTiersLoc.setCommentaire(swtTiers.commentaire);		//19
		swtTiersLoc.setID_I_BANQUE(swtTiers.ID_I_BANQUE);				//20
		swtTiersLoc.setCODE_BANQUE(swtTiers.CODE_BANQUE);				//21
		swtTiersLoc.setLIBC_BANQUE(swtTiers.LIBC_BANQUE);				//22
		swtTiersLoc.setLIBL_BANQUE(swtTiers.LIBL_BANQUE);				//23
		swtTiersLoc.setIdTEntite(swtTiers.idTEntite);				//24
		swtTiersLoc.setCodeTEntite(swtTiers.codeTEntite);			//25
		swtTiersLoc.setTvaIComCompl(swtTiers.tvaIComCompl);		//26
		swtTiersLoc.setSiretCompl(swtTiers.siretCompl);	
		swtTiersLoc.setAccise(swtTiers.accise);//27
		swtTiersLoc.setIcs(swtTiers.ics);//28
		
		swtTiersLoc.setAdresse1Adresse(swtTiers.adresse1Adresse);
		swtTiersLoc.setAdresse2Adresse(swtTiers.adresse2Adresse);
		swtTiersLoc.setAdresse3Adresse(swtTiers.adresse3Adresse);
		swtTiersLoc.setCodepostalAdresse(swtTiers.codepostalAdresse);
		swtTiersLoc.setVilleAdresse(swtTiers.villeAdresse);
		swtTiersLoc.setPaysAdresse(swtTiers.paysAdresse);
		
		swtTiersLoc.setAdresseEmail(swtTiers.adresseEmail);
		swtTiersLoc.setAdresseWeb(swtTiers.adresseWeb);
		swtTiersLoc.setNumeroTelephone(swtTiers.numeroTelephone);
		swtTiersLoc.setCodeTTvaDoc(swtTiers.codeTTvaDoc);
		swtTiersLoc.setIdTTvaDoc(swtTiers.idTTvaDoc);
		
		swtTiersLoc.setCODE_T_ENTITE_ENTREPRISE(swtTiers.CODE_T_ENTITE_ENTREPRISE);				//27
		swtTiersLoc.setDateAnniv(swtTiers.dateAnniv);
//		swtTiersLoc.setIdFamilleTiers(swtTiers.idFamilleTiers);
//		swtTiersLoc.setCodeFamille(swtTiers.codeFamille);
		return swtTiersLoc;
	}

	public String getAdresse1Adresse() {
		return adresse1Adresse;
	}

	public void setAdresse1Adresse(String adresse1Adresse) {
		firePropertyChange("adresse1Adresse", this.adresse1Adresse, this.adresse1Adresse = adresse1Adresse);	
	}

	public String getAdresse2Adresse() {
		return adresse2Adresse;
	}

	public void setAdresse2Adresse(String adresse2Adresse) {
		firePropertyChange("adresse2Adresse", this.adresse2Adresse, this.adresse2Adresse = adresse2Adresse);	
	}

	public String getAdresse3Adresse() {
		return adresse3Adresse;
	}

	public void setAdresse3Adresse(String adresse3Adresse) {
		firePropertyChange("adresse3Adresse", this.adresse3Adresse, this.adresse3Adresse = adresse3Adresse);	
	}

	public String getCodepostalAdresse() {
		return codepostalAdresse;
	}

	public void setCodepostalAdresse(String codepostalAdresse) {
		firePropertyChange("codepostalAdresse", this.codepostalAdresse, this.codepostalAdresse = codepostalAdresse);	
	}

	public String getVilleAdresse() {
		return villeAdresse;
	}

	public void setVilleAdresse(String villeAdresse) {
		firePropertyChange("villeAdresse", this.villeAdresse, this.villeAdresse = villeAdresse);	
	}

	public String getPaysAdresse() {
		return paysAdresse;
	}

	public void setPaysAdresse(String paysAdresse) {
		firePropertyChange("paysAdresse", this.paysAdresse, this.paysAdresse = paysAdresse);	
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
	

	public String getNumeroTelephone() {
		return numeroTelephone;
	}

	public void setNumeroTelephone(String numeroTelephone) {
		firePropertyChange("numeroTelephone", this.numeroTelephone, this.numeroTelephone = numeroTelephone);
	}



	public Integer getIdAdresse() {
		return idAdresse;
	}


	public void setIdAdresse(Integer idAdresse) {
		this.idAdresse = idAdresse;
	}


	public Integer getIdEmail() {
		return idEmail;
	}


	public void setIdEmail(Integer idEmail) {
		this.idEmail = idEmail;
	}

//	public Integer getIdFamilleTiers() {
//		return idFamilleTiers;
//	}
//
//
//	public void setIdFamilleTiers(Integer idFamilleTiers) {
//		this.idFamilleTiers = idFamilleTiers;
//	}
//
//
//	public String getCodeFamille() {
//		return codeFamille;
//	}
//
//
//	public void setCodeFamille(String codeFamille) {
//		firePropertyChange("codeFamille", this.codeFamille, this.codeFamille = codeFamille);
//	}

	
	public String getCodeCPaiement() {
		return codeCPaiement;
	}


	public void setCodeCPaiement(String codeCPaiement) {
		firePropertyChange("codeCPaiement", this.codeCPaiement, this.codeCPaiement = codeCPaiement);
	}

	public String getCodeTTarif() {
		return codeTTarif;
	}


	public void setCodeTTarif(String codeTTarif) {
		firePropertyChange("codeTTarif", this.codeTTarif, this.codeTTarif = codeTTarif);
	}


	public String getCodeTTvaDoc() {
		return codeTTvaDoc;
	}


	public void setCodeTTvaDoc(String codeTTvaDoc) {
		firePropertyChange("codeTTvaDoc", this.codeTTvaDoc, this.codeTTvaDoc = codeTTvaDoc);
	}


	public Integer getIdTTvaDoc() {
		return idTTvaDoc;
	}


	public void setIdTTvaDoc(Integer idTTvaDoc) {
		firePropertyChange("idTTvaDoc", this.idTTvaDoc, this.idTTvaDoc = idTTvaDoc);
	}


	public Boolean getAccepte() {
		return accepte;
	}


	public void setAccepte(Boolean accepte) {
		firePropertyChange("accepte", this.accepte, this.accepte = accepte);
	}


	public String getCodeTPaiement() {
		return codeTPaiement;
	}


	public void setCodeTPaiement(String codeTPaiement) {
		firePropertyChange("codeTPaiement", this.codeTPaiement, this.codeTPaiement = codeTPaiement);
	}


	public String getAccise() {
		return accise;
	}


	public void setAccise(String accise) {
		firePropertyChange("accise", this.accise, this.accise = accise);
	}
	
	public String getIcs() {
		return ics;
	}


	public void setIcs(String ics) {
		firePropertyChange("ics", this.ics, this.ics = ics);
	}


	public Date getDateAnniv() {
		return dateAnniv;
	}


	public void setDateAnniv(Date dateAnniv) {
		firePropertyChange("dateAnniv", this.dateAnniv, this.dateAnniv = dateAnniv);
	}






}

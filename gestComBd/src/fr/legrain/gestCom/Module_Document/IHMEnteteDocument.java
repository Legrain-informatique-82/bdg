package fr.legrain.gestCom.Module_Document;

// Generated 20 mars 2007 09:51:19 by Hibernate Tools 3.2.0.b9

import java.math.BigDecimal;
import java.util.Date;

import fr.legrain.lib.data.LgrConstantes;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.LibDate;
import fr.legrain.lib.data.ModelObject;

/**
 * IHMEnteteFacture generated by hbm2java
 */
public class IHMEnteteDocument extends ModelObject {

	private Integer idDocument=0;
	private String codeDocument=LgrConstantes.C_STR_VIDE;
	private Date dateDocument=new Date();
//	private Date dateEchDocument=new Date();;
	private Date dateLivDocument=new Date();;
	private String libelleDocument=LgrConstantes.C_STR_VIDE;
	private Integer idAdresse=0;
	private Integer idAdresseLiv=0;
	private Integer idTiers=0;
	private String codeTiers=LgrConstantes.C_STR_VIDE;
	private String nomTiers=LgrConstantes.C_STR_VIDE;
	private String prenomTiers=LgrConstantes.C_STR_VIDE;
	private String surnomTiers=LgrConstantes.C_STR_VIDE;
	private String codeCompta=LgrConstantes.C_STR_VIDE;
	private String compte=LgrConstantes.C_STR_VIDE;
	private Integer idTPaiement=0;
	private String codeTPaiement=LgrConstantes.C_STR_VIDE;
	private Integer idCPaiement=0;
	private BigDecimal remHtDocument=new BigDecimal(0);
	private BigDecimal txRemHtDocument=new BigDecimal(0);
	private BigDecimal remTtcDocument=new BigDecimal(0);
	private BigDecimal txRemTtcDocument=new BigDecimal(0);
	private Integer nbEDocument=0;
	private BigDecimal mtTtcCalc=new BigDecimal(0);
	private BigDecimal mtHtCalc=new BigDecimal(0);
	private BigDecimal mtTvaCalc=new BigDecimal(0);
	private BigDecimal netTtcCalc=new BigDecimal(0);
	private BigDecimal netHtCalc=new BigDecimal(0);
	private BigDecimal netTvaCalc=new BigDecimal(0);
	private String ipAcces=LgrConstantes.C_STR_VIDE;
	private Boolean ttc=false;
	private Boolean export=false;
	private String commentaire = "";
	private Boolean accepte=true;

	private String typeDocument;


	public IHMEnteteDocument() {
	}

	public IHMEnteteDocument(Integer ID_BONLIV, String CODE_BONLIV,
			Date DATE_BONLIV, Date DATE_LIV_BONLIV,
			String LIBELLE_BONLIV, Integer ID_ADRESSE, Integer ID_ADRESSE_LIV,
			Integer ID_TIERS, String CODE_TIERS, String NOM_TIERS,
			String PRENOM_TIERS, String SURNOM_TIERS, String CODE_COMPTA,
			String COMPTE, Integer ID_T_PAIEMENT, String CODE_T_PAIEMENT,
			Integer ID_C_PAIEMENT, BigDecimal REGLE_BONLIV,
			BigDecimal REM_HT_BONLIV, BigDecimal TX_REM_HT_BONLIV,
			BigDecimal REM_TTC_BONLIV, BigDecimal TX_REM_TTC_BONLIV,
			Integer NB_E_BONLIV, BigDecimal MT_TTC_CALC, BigDecimal MT_HT_CALC,
			BigDecimal MT_TVA_CALC, BigDecimal NET_TTC_CALC, BigDecimal NET_HT_CALC,
			BigDecimal NET_TVA_CALC, String IP_ACCES, Boolean TTC, Boolean EXPORT,String COMMENTAIRE) {
		this.idDocument = ID_BONLIV;
		this.codeDocument = CODE_BONLIV;
		this.dateDocument = DATE_BONLIV;
//		this.dateEchDocument = DATE_ECH_BONLIV;
		this.dateLivDocument = DATE_LIV_BONLIV;
		this.libelleDocument = LIBELLE_BONLIV;
		this.idAdresse = ID_ADRESSE;
		this.idAdresseLiv = ID_ADRESSE_LIV;
		this.idTiers = ID_TIERS;
		this.codeTiers = CODE_TIERS;
		this.nomTiers = NOM_TIERS;
		this.prenomTiers = PRENOM_TIERS;
		this.surnomTiers = SURNOM_TIERS;
		this.codeCompta = CODE_COMPTA;
		this.compte = COMPTE;
		this.idTPaiement = ID_T_PAIEMENT;
		this.codeTPaiement = CODE_T_PAIEMENT;
		this.idCPaiement = ID_C_PAIEMENT;
		//this.REGLE_BONLIV = REGLE_BONLIV;
		this.remHtDocument = REM_HT_BONLIV;
		this.txRemHtDocument = TX_REM_HT_BONLIV;
		this.remTtcDocument = REM_TTC_BONLIV;
		this.txRemTtcDocument = TX_REM_TTC_BONLIV;
		this.nbEDocument = NB_E_BONLIV;
		this.mtTtcCalc = MT_TTC_CALC;
		this.mtHtCalc = MT_HT_CALC;
		this.mtTvaCalc = MT_TVA_CALC;
		this.netTtcCalc = NET_TTC_CALC;
		this.netHtCalc = NET_HT_CALC;
		this.netTvaCalc = NET_TVA_CALC;
		this.ipAcces = IP_ACCES;
		this.ttc = TTC;
		this.export = EXPORT;
		this.commentaire = COMMENTAIRE;
	}

	public void setIHMEnteteBonliv(IHMEnteteDocument ihmEnteteBonliv){
		setIdDocument(ihmEnteteBonliv.idDocument);
		setCodeDocument(ihmEnteteBonliv.codeDocument);
		setDateDocument(ihmEnteteBonliv.dateDocument);
//		setDateEchDocument(ihmEnteteBonliv.dateEchDocument);
		setDateLivDocument(ihmEnteteBonliv.dateLivDocument);
		setLibelleDocument(ihmEnteteBonliv.libelleDocument);
		setIdAdresse(ihmEnteteBonliv.idAdresse);
		setIdAdresseLiv(ihmEnteteBonliv.idAdresseLiv);
		setIdTiers(ihmEnteteBonliv.idTiers);
		setCodeTiers(ihmEnteteBonliv.codeTiers);
		setNomTiers(ihmEnteteBonliv.nomTiers);
		setPrenomTiers(ihmEnteteBonliv.prenomTiers);
		setSurnomTiers(ihmEnteteBonliv.surnomTiers);
		setCodeCompta(ihmEnteteBonliv.codeCompta);
		setCompte(ihmEnteteBonliv.compte);
		setIdTPaiement(ihmEnteteBonliv.idTPaiement);
		setCodeTPaiement(ihmEnteteBonliv.codeTPaiement);
		setIdCPaiement(ihmEnteteBonliv.idCPaiement);
		//setREGLE_BONLIV(ihmEnteteBonliv.REGLE_BONLIV);
		setRemHtDocument(ihmEnteteBonliv.remHtDocument);
		setTxRemHtDocument(ihmEnteteBonliv.txRemHtDocument);
		setRemTtcDocument(ihmEnteteBonliv.remTtcDocument);
		setTxRemTtcDocument(ihmEnteteBonliv.txRemTtcDocument);
		setNbEDocument(ihmEnteteBonliv.nbEDocument);
		setMtTtcCalc(ihmEnteteBonliv.mtTtcCalc);
		setMtHtCalc(ihmEnteteBonliv.mtHtCalc);
		setMtTvaCalc(ihmEnteteBonliv.mtTvaCalc);
		setNetTtcCalc(ihmEnteteBonliv.netTtcCalc);
		setNetHtCalc(ihmEnteteBonliv.netHtCalc);
		setNetTvaCalc(ihmEnteteBonliv.netTvaCalc);
		setIpAcces(ihmEnteteBonliv.ipAcces);
		setTtc(ihmEnteteBonliv.ttc);
		setExport(ihmEnteteBonliv.export);
		setCommentaire(ihmEnteteBonliv.commentaire);
	}
	
	public static IHMEnteteDocument copy(IHMEnteteDocument ihmEnteteBonliv){
		IHMEnteteDocument ihmEnteteBonlivLoc = new IHMEnteteDocument();
		ihmEnteteBonlivLoc.setIdDocument(ihmEnteteBonliv.idDocument);
		ihmEnteteBonlivLoc.setCodeDocument(ihmEnteteBonliv.codeDocument);
		ihmEnteteBonlivLoc.setDateDocument(ihmEnteteBonliv.dateDocument);
//		ihmEnteteBonlivLoc.setDateEchDocument(ihmEnteteBonliv.dateEchDocument);
		ihmEnteteBonlivLoc.setDateLivDocument(ihmEnteteBonliv.dateLivDocument);
		ihmEnteteBonlivLoc.setLibelleDocument(ihmEnteteBonliv.libelleDocument);
		ihmEnteteBonlivLoc.setIdAdresse(ihmEnteteBonliv.idAdresse);
		ihmEnteteBonlivLoc.setIdAdresseLiv(ihmEnteteBonliv.idAdresseLiv);
		ihmEnteteBonlivLoc.setIdTiers(ihmEnteteBonliv.idTiers);
		ihmEnteteBonlivLoc.setCodeTiers(ihmEnteteBonliv.codeTiers);
		ihmEnteteBonlivLoc.setNomTiers(ihmEnteteBonliv.nomTiers);
		ihmEnteteBonlivLoc.setPrenomTiers(ihmEnteteBonliv.prenomTiers);
		ihmEnteteBonlivLoc.setSurnomTiers(ihmEnteteBonliv.surnomTiers);
		ihmEnteteBonlivLoc.setCodeCompta(ihmEnteteBonliv.codeCompta);
		ihmEnteteBonlivLoc.setCompte(ihmEnteteBonliv.compte);
		ihmEnteteBonlivLoc.setIdTPaiement(ihmEnteteBonliv.idTPaiement);
		ihmEnteteBonlivLoc.setCodeTPaiement(ihmEnteteBonliv.codeTPaiement);
		ihmEnteteBonlivLoc.setIdCPaiement(ihmEnteteBonliv.idCPaiement);
		//ihmEnteteBonlivLoc.setREGLE_BONLIV(ihmEnteteBonliv.REGLE_BONLIV);
		ihmEnteteBonlivLoc.setRemHtDocument(ihmEnteteBonliv.remHtDocument);
		ihmEnteteBonlivLoc.setTxRemHtDocument(ihmEnteteBonliv.txRemHtDocument);
		ihmEnteteBonlivLoc.setRemTtcDocument(ihmEnteteBonliv.remTtcDocument);
		ihmEnteteBonlivLoc.setTxRemTtcDocument(ihmEnteteBonliv.txRemTtcDocument);
		ihmEnteteBonlivLoc.setNbEDocument(ihmEnteteBonliv.nbEDocument);
		ihmEnteteBonlivLoc.setMtTtcCalc(ihmEnteteBonliv.mtTtcCalc);
		ihmEnteteBonlivLoc.setMtHtCalc(ihmEnteteBonliv.mtHtCalc);
		ihmEnteteBonlivLoc.setMtTvaCalc(ihmEnteteBonliv.mtTvaCalc);
		ihmEnteteBonlivLoc.setNetTtcCalc(ihmEnteteBonliv.netTtcCalc);
		ihmEnteteBonlivLoc.setNetHtCalc(ihmEnteteBonliv.netHtCalc);
		ihmEnteteBonlivLoc.setNetTvaCalc(ihmEnteteBonliv.netTvaCalc);
		ihmEnteteBonlivLoc.setIpAcces(ihmEnteteBonliv.ipAcces);
		ihmEnteteBonlivLoc.setTtc(ihmEnteteBonliv.ttc);
		ihmEnteteBonlivLoc.setExport(ihmEnteteBonliv.export);
		ihmEnteteBonlivLoc.setCommentaire(ihmEnteteBonliv.commentaire);
		return ihmEnteteBonlivLoc;
	}
	
	public boolean BonlivEstVide(){
		Date dateExemple = new Date();
//		if(!getCODE_BONLIV().equals(LgrConstantes.C_STR_VIDE))return true;
		if(!(LibDate.compareTo(getDateDocument(), dateExemple)==0))return false;
//		if(!(LibDate.compareTo(getDateEchDocument(), dateExemple)==0))return false;
		if(!(LibDate.compareTo(getDateLivDocument(), dateExemple)==0))return false;
		
		if(!getLibelleDocument().equals(LgrConstantes.C_STR_VIDE))return false;
//		if(!getID_ADRESSE().equals(0))return false;
//		if(!getID_ADRESSE_LIV().equals(0))return false;
		if(!getIdTiers().equals(0))return false;
		if(!getCodeTiers().equals(LgrConstantes.C_STR_VIDE))return false;
		if(!getNomTiers().equals(LgrConstantes.C_STR_VIDE))return false;
		if(!getPrenomTiers().equals(LgrConstantes.C_STR_VIDE))return false;
		if(!getSurnomTiers().equals(LgrConstantes.C_STR_VIDE))return false;
		if(!getCodeCompta().equals(LgrConstantes.C_STR_VIDE))return false;
		if(!getCompte().equals(LgrConstantes.C_STR_VIDE))return false;
//		if(!getID_T_PAIEMENT().equals(0))return false;
//		if(!getCODE_T_PAIEMENT().equals(LgrConstantes.C_STR_VIDE))return false;
//		if(!getID_C_PAIEMENT().equals(0))return false;
//		//if(!LibChaine.emptyNumeric(getREGLE_BONLIV()))return false;
//		if(!LibChaine.emptyNumeric(getREM_HT_BONLIV()))return false;
//		if(!LibChaine.emptyNumeric(getTX_REM_HT_BONLIV()))return false;
//		if(!LibChaine.emptyNumeric(getREM_TTC_BONLIV()))return false;
//		if(!LibChaine.emptyNumeric(getTX_REM_TTC_BONLIV()))return false;
//		if(!getNB_E_BONLIV().equals(0))return false;
//		if(!LibChaine.emptyNumeric(getMT_TTC_CALC()))return false;
//		if(!LibChaine.emptyNumeric(getMT_HT_CALC()))return false;
//		if(!LibChaine.emptyNumeric(getMT_TVA_CALC()))return false;
//		if(!LibChaine.emptyNumeric(getNET_TTC_CALC()))return false;
//		if(!LibChaine.emptyNumeric(getNET_HT_CALC()))return false;
//		if(!LibChaine.emptyNumeric(getNET_TVA_CALC()))return false;
//		if(!getIP_ACCES().equals(""))return false;
//		if(!getTTC().equals(false))return false;
//		if(!getEXPORT().equals(false))return false;
		return true;
	}
	
	public boolean BonlivEstVide(Date dateDansExercice){
		Date dateExemple = new Date();
//		if(!getCODE_BONLIV().equals(LgrConstantes.C_STR_VIDE))return true;
		if(!((LibDate.compareTo(getDateDocument(), dateDansExercice)==0) || 
				(LibDate.compareTo(getDateDocument(), dateExemple)==0)))return false;
//		if(!((LibDate.compareTo(getDateEchDocument(), dateDansExercice)==0) || 
//				(LibDate.compareTo(getDateEchDocument(), dateExemple)==0)))return false;
//		if(!((LibDate.compareTo(getDateLivDocument(), dateDansExercice)==0) || 
//				(LibDate.compareTo(getDateLivDocument(), dateExemple)==0)))return false;
		
		if(getLibelleDocument()!=null && !getLibelleDocument().equals(LgrConstantes.C_STR_VIDE))return false;
//		if(!getID_ADRESSE().equals(0))return false;
//		if(!getID_ADRESSE_LIV().equals(0))return false;
		if(getIdTiers()!=null && !getIdTiers().equals(0))return false;
		if(getCodeTiers()!=null && !getCodeTiers().equals(LgrConstantes.C_STR_VIDE))return false;
		if(getNomTiers()!=null && !getNomTiers().equals(LgrConstantes.C_STR_VIDE))return false;
		if(getPrenomTiers()!=null && !getPrenomTiers().equals(LgrConstantes.C_STR_VIDE))return false;
		if(getSurnomTiers()!=null && !getSurnomTiers().equals(LgrConstantes.C_STR_VIDE))return false;
		if(getCodeCompta()!=null && !getCodeCompta().equals(LgrConstantes.C_STR_VIDE))return false;
		if(getCompte()!=null && !getCompte().equals(LgrConstantes.C_STR_VIDE))return false;
//		if(!getID_T_PAIEMENT().equals(0))return false;
//		if(!getCODE_T_PAIEMENT().equals(LgrConstantes.C_STR_VIDE))return false;
//		if(!getID_C_PAIEMENT().equals(0))return false;
//		//if(!LibChaine.emptyNumeric(getREGLE_BONLIV()))return false;
//		if(!LibChaine.emptyNumeric(getREM_HT_BONLIV()))return false;
//		if(!LibChaine.emptyNumeric(getTX_REM_HT_BONLIV()))return false;
//		if(!LibChaine.emptyNumeric(getREM_TTC_BONLIV()))return false;
//		if(!LibChaine.emptyNumeric(getTX_REM_TTC_BONLIV()))return false;
//		if(!getNB_E_BONLIV().equals(0))return false;
//		if(!LibChaine.emptyNumeric(getMT_TTC_CALC()))return false;
//		if(!LibChaine.emptyNumeric(getMT_HT_CALC()))return false;
//		if(!LibChaine.emptyNumeric(getMT_TVA_CALC()))return false;
//		if(!LibChaine.emptyNumeric(getNET_TTC_CALC()))return false;
//		if(!LibChaine.emptyNumeric(getNET_HT_CALC()))return false;
//		if(!LibChaine.emptyNumeric(getNET_TVA_CALC()))return false;
//		if(!getIP_ACCES().equals(""))return false;
//		if(!getTTC().equals(false))return false;
//		if(!getEXPORT().equals(false))return false;
		return true;
	}


	public Integer getIdDocument() {
		return this.idDocument;
	}


	public void setIdDocument(Integer ID_BONLIV) {
		firePropertyChange("idDocument", this.idDocument, this.idDocument = ID_BONLIV);
	}


	public String getCodeDocument() {
		return this.codeDocument;
	}


	public void setCodeDocument(String CODE_BONLIV) {
		firePropertyChange("codeDocument", this.codeDocument, this.codeDocument = CODE_BONLIV);
	}


	public Date getDateDocument() {
		return this.dateDocument;
	}

	public void setDateDocument(Date DATE_BONLIV) {
		firePropertyChange("dateDocument", this.dateDocument, this.dateDocument = DATE_BONLIV);
	}



	public Date getDateLivDocument() {
		return this.dateLivDocument;
	}

	public void setDateLivDocument(Date DATE_LIV_BONLIV) {
		firePropertyChange("dateLivDocument", this.dateLivDocument, this.dateLivDocument = DATE_LIV_BONLIV);
	}

	public String getLibelleDocument() {
		return this.libelleDocument;
	}

	public void setLibelleDocument(String LIBELLE_BONLIV) {
		firePropertyChange("libelleDocument", this.libelleDocument, this.libelleDocument = LIBELLE_BONLIV);
	}

	public Integer getIdAdresse() {
		return this.idAdresse;
	}

	public void setIdAdresse(Integer ID_ADRESSE) {
		firePropertyChange("idAdresse", this.idAdresse, this.idAdresse = ID_ADRESSE);
	}

	public Integer getIdAdresseLiv() {
		return this.idAdresseLiv;
	}

	public void setIdAdresseLiv(Integer ID_ADRESSE_LIV) {
		firePropertyChange("idAdresseLiv", this.idAdresseLiv, this.idAdresseLiv = ID_ADRESSE_LIV);
	}

	public Integer getIdTiers() {
		return this.idTiers;
	}

	public void setIdTiers(Integer ID_TIERS) {
		firePropertyChange("idTiers", this.idTiers, this.idTiers = ID_TIERS);
	}

	public String getCodeTiers() {
		return this.codeTiers;
	}

	public void setCodeTiers(String CODE_TIERS) {
		firePropertyChange("codeTiers", this.codeTiers, this.codeTiers = CODE_TIERS);
	}

	public String getNomTiers() {
		return this.nomTiers;
	}

	public void setNomTiers(String NOM_TIERS) {
		firePropertyChange("nomTiers", this.nomTiers, this.nomTiers = NOM_TIERS);
	}

	public String getPrenomTiers() {
		return this.prenomTiers;
	}

	public void setPrenomTiers(String PRENOM_TIERS) {
		firePropertyChange("prenomTiers", this.prenomTiers, this.prenomTiers = PRENOM_TIERS);
	}

	public String getSurnomTiers() {
		return this.surnomTiers;
	}

	public void setSurnomTiers(String SURNOM_TIERS) {
		firePropertyChange("surnomTiers", this.surnomTiers, this.surnomTiers = SURNOM_TIERS);
	}

	public String getCodeCompta() {
		return this.codeCompta;
	}

	public void setCodeCompta(String CODE_COMPTA) {
		firePropertyChange("codeCompta", this.codeCompta, this.codeCompta = CODE_COMPTA);
	}

	public String getCompte() {
		return this.compte;
	}

	public void setCompte(String COMPTE) {
		firePropertyChange("compte", this.compte, this.compte = COMPTE);
	}

	public Integer getIdTPaiement() {
		return this.idTPaiement;
	}

	public void setIdTPaiement(Integer ID_T_PAIEMENT) {
		firePropertyChange("idTPaiement", this.idTPaiement, this.idTPaiement = ID_T_PAIEMENT);
	}

	public String getCodeTPaiement() {
		return this.codeTPaiement;
	}

	public void setCodeTPaiement(String CODE_T_PAIEMENT) {
		firePropertyChange("codeTPaiement", this.codeTPaiement, this.codeTPaiement = CODE_T_PAIEMENT);
	}

	public Integer getIdCPaiement() {
		return this.idCPaiement;
	}

	public void setIdCPaiement(Integer ID_C_PAIEMENT) {
		firePropertyChange("idCPaiement", this.idCPaiement, this.idCPaiement = ID_C_PAIEMENT);
	}

//	public BigDecimal getREGLE_BONLIV() {
//		return this.REGLE_BONLIV;
//	}
//
//	public void setREGLE_BONLIV(BigDecimal REGLE_BONLIV) {
//		firePropertyChange("REGLE_BONLIV", this.REGLE_BONLIV, this.REGLE_BONLIV = REGLE_BONLIV);
//	}

	public BigDecimal getRemHtDocument() {
		return this.remHtDocument;
	}

	public void setRemHtDocument(BigDecimal REM_HT_BONLIV) {
		firePropertyChange("remHtDocument", this.remHtDocument, this.remHtDocument = REM_HT_BONLIV);
	}

	public BigDecimal getTxRemHtDocument() {
		return this.txRemHtDocument;
	}

	public void setTxRemHtDocument(BigDecimal TX_REM_HT_BONLIV) {
		firePropertyChange("txRemHtDocument", this.txRemHtDocument, this.txRemHtDocument = TX_REM_HT_BONLIV);
	}

	public BigDecimal getRemTtcDocument() {
		return this.remTtcDocument;
	}

	public void setRemTtcDocument(BigDecimal REM_TTC_BONLIV) {
		firePropertyChange("remTtcDocument", this.remTtcDocument, this.remTtcDocument = REM_TTC_BONLIV);
	}

	public BigDecimal getTxRemTtcDocument() {
		return this.txRemTtcDocument;
	}

	public void setTxRemTtcDocument(BigDecimal TX_REM_TTC_BONLIV) {
		firePropertyChange("txRemTtcDocument", this.txRemTtcDocument, this.txRemTtcDocument = TX_REM_TTC_BONLIV);
	}

	public Integer getNbEDocument() {
		return this.nbEDocument;
	}

	public void setNbEDocument(Integer NB_E_BONLIV) {
		firePropertyChange("nbEDocument", this.nbEDocument, this.nbEDocument = NB_E_BONLIV);
	}

	public BigDecimal getMtTtcCalc() {
		return this.mtTtcCalc;
	}

	public void setMtTtcCalc(BigDecimal MT_TTC_CALC) {
		firePropertyChange("mtTtcCalc", this.mtTtcCalc, this.mtTtcCalc = MT_TTC_CALC);
	}

	public BigDecimal getMtHtCalc() {
		return this.mtHtCalc;
	}

	public void setMtHtCalc(BigDecimal MT_HT_CALC) {
		firePropertyChange("mtHtCalc", this.mtHtCalc, this.mtHtCalc = MT_HT_CALC);
	}

	public BigDecimal getMtTvaCalc() {
		return this.mtTvaCalc;
	}

	public void setMtTvaCalc(BigDecimal MT_TVA_CALC) {
		firePropertyChange("mtTvaCalc", this.mtTvaCalc, this.mtTvaCalc = MT_TVA_CALC);
	}

	public BigDecimal getNetTtcCalc() {
		return this.netTtcCalc;
	}

	public void setNetTtcCalc(BigDecimal NET_TTC_CALC) {
		firePropertyChange("netTtcCalc", this.netTtcCalc, this.netTtcCalc = NET_TTC_CALC);
	}

	public BigDecimal getNetHtCalc() {
		return this.netHtCalc;
	}

	public void setNetHtCalc(BigDecimal NET_HT_CALC) {
		firePropertyChange("netHtCalc", this.netHtCalc, this.netHtCalc = NET_HT_CALC);
	}

	public BigDecimal getNetTvaCalc() {
		return this.netTvaCalc;
	}

	public void setNetTvaCalc(BigDecimal NET_TVA_CALC) {
		firePropertyChange("netTvaCalc", this.netTvaCalc, this.netTvaCalc = NET_TVA_CALC);
	}

	public String getIpAcces() {
		return this.ipAcces;
	}

	public void setIpAcces(String IP_ACCES) {
		firePropertyChange("ipAcces", this.ipAcces, this.ipAcces = IP_ACCES);
	}

	public Boolean getTtc() {
		return this.ttc;
	}

	public void setTtc(Boolean TTC) {
		this.ttc = TTC;
		firePropertyChange("ttc", this.ttc, LibConversion.booleanToInt(TTC));
	}

	public Boolean getExport() {
		return this.export;
	}

	public void setExport(Boolean EXPORT) {
		firePropertyChange("export", this.export, this.export = EXPORT);
	}
	
	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String COMMENTAIRE) {
		firePropertyChange("commentaire", this.commentaire, this.commentaire = COMMENTAIRE);		
	}	

	public Boolean getAccepte() {
		return accepte;
	}

	public void setAccepte(Boolean accepte) {
		firePropertyChange("accepte", this.accepte, this.accepte = accepte);		
	}
	
	public String getTypeDocument() {
		return typeDocument;
	}

	public void setTypeDocument(String typeDocument) {
		firePropertyChange("typeDocument", this.typeDocument, this.typeDocument = typeDocument);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codeDocument == null) ? 0 : codeDocument.hashCode());
		result = prime * result
				+ ((codeTiers == null) ? 0 : codeTiers.hashCode());
		result = prime * result + ((compte == null) ? 0 : compte.hashCode());
		result = prime * result
				+ ((dateDocument == null) ? 0 : dateDocument.hashCode());
		result = prime * result + ((export == null) ? 0 : export.hashCode());
		result = prime * result + ((idTiers == null) ? 0 : idTiers.hashCode());
		result = prime * result
				+ ((libelleDocument == null) ? 0 : libelleDocument.hashCode());
		result = prime * result
				+ ((netTtcCalc == null) ? 0 : netTtcCalc.hashCode());
		result = prime * result
				+ ((nomTiers == null) ? 0 : nomTiers.hashCode());
		result = prime * result
				+ ((typeDocument == null) ? 0 : typeDocument.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IHMEnteteDocument other = (IHMEnteteDocument) obj;
		if (codeDocument == null) {
			if (other.codeDocument != null)
				return false;
		} else if (!codeDocument.equals(other.codeDocument))
			return false;
		if (codeTiers == null) {
			if (other.codeTiers != null)
				return false;
		} else if (!codeTiers.equals(other.codeTiers))
			return false;
		if (compte == null) {
			if (other.compte != null)
				return false;
		} else if (!compte.equals(other.compte))
			return false;
		if (dateDocument == null) {
			if (other.dateDocument != null)
				return false;
		} else if (!dateDocument.equals(other.dateDocument))
			return false;
		if (export == null) {
			if (other.export != null)
				return false;
		} else if (!export.equals(other.export))
			return false;
		if (idTiers == null) {
			if (other.idTiers != null)
				return false;
		} else if (!idTiers.equals(other.idTiers))
			return false;
		if (libelleDocument == null) {
			if (other.libelleDocument != null)
				return false;
		} else if (!libelleDocument.equals(other.libelleDocument))
			return false;
		if (netTtcCalc == null) {
			if (other.netTtcCalc != null)
				return false;
		} else if (!netTtcCalc.equals(other.netTtcCalc))
			return false;
		if (nomTiers == null) {
			if (other.nomTiers != null)
				return false;
		} else if (!nomTiers.equals(other.nomTiers))
			return false;
		if (typeDocument == null) {
			if (other.typeDocument != null)
				return false;
		} else if (!typeDocument.equals(other.typeDocument))
			return false;
		return true;
	}

//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((accepte == null) ? 0 : accepte.hashCode());
//		result = prime * result
//				+ ((codeCompta == null) ? 0 : codeCompta.hashCode());
//		result = prime * result
//				+ ((codeDocument == null) ? 0 : codeDocument.hashCode());
//		result = prime * result
//				+ ((codeTPaiement == null) ? 0 : codeTPaiement.hashCode());
//		result = prime * result
//				+ ((codeTiers == null) ? 0 : codeTiers.hashCode());
//		result = prime * result
//				+ ((commentaire == null) ? 0 : commentaire.hashCode());
//		result = prime * result + ((compte == null) ? 0 : compte.hashCode());
//		result = prime * result
//				+ ((dateDocument == null) ? 0 : dateDocument.hashCode());
//		result = prime * result
//				+ ((dateLivDocument == null) ? 0 : dateLivDocument.hashCode());
//		result = prime * result + ((export == null) ? 0 : export.hashCode());
//		result = prime * result
//				+ ((idAdresse == null) ? 0 : idAdresse.hashCode());
//		result = prime * result
//				+ ((idAdresseLiv == null) ? 0 : idAdresseLiv.hashCode());
//		result = prime * result
//				+ ((idCPaiement == null) ? 0 : idCPaiement.hashCode());
//		result = prime * result
//				+ ((idDocument == null) ? 0 : idDocument.hashCode());
//		result = prime * result
//				+ ((idTPaiement == null) ? 0 : idTPaiement.hashCode());
//		result = prime * result + ((idTiers == null) ? 0 : idTiers.hashCode());
//		result = prime * result + ((ipAcces == null) ? 0 : ipAcces.hashCode());
//		result = prime * result
//				+ ((libelleDocument == null) ? 0 : libelleDocument.hashCode());
//		result = prime * result
//				+ ((mtHtCalc == null) ? 0 : mtHtCalc.hashCode());
//		result = prime * result
//				+ ((mtTtcCalc == null) ? 0 : mtTtcCalc.hashCode());
//		result = prime * result
//				+ ((mtTvaCalc == null) ? 0 : mtTvaCalc.hashCode());
//		result = prime * result
//				+ ((nbEDocument == null) ? 0 : nbEDocument.hashCode());
//		result = prime * result
//				+ ((netHtCalc == null) ? 0 : netHtCalc.hashCode());
//		result = prime * result
//				+ ((netTtcCalc == null) ? 0 : netTtcCalc.hashCode());
//		result = prime * result
//				+ ((netTvaCalc == null) ? 0 : netTvaCalc.hashCode());
//		result = prime * result
//				+ ((nomTiers == null) ? 0 : nomTiers.hashCode());
//		result = prime * result
//				+ ((prenomTiers == null) ? 0 : prenomTiers.hashCode());
//		result = prime * result
//				+ ((remHtDocument == null) ? 0 : remHtDocument.hashCode());
//		result = prime * result
//				+ ((remTtcDocument == null) ? 0 : remTtcDocument.hashCode());
//		result = prime * result
//				+ ((surnomTiers == null) ? 0 : surnomTiers.hashCode());
//		result = prime * result + ((ttc == null) ? 0 : ttc.hashCode());
//		result = prime * result
//				+ ((txRemHtDocument == null) ? 0 : txRemHtDocument.hashCode());
//		result = prime
//				* result
//				+ ((txRemTtcDocument == null) ? 0 : txRemTtcDocument.hashCode());
//		result = prime * result
//				+ ((typeDocument == null) ? 0 : typeDocument.hashCode());
//		return result;
//	}


	



	

}
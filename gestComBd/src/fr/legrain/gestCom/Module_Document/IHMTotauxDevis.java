package fr.legrain.gestCom.Module_Document;

// Generated 20 mars 2007 09:51:19 by Hibernate Tools 3.2.0.b9

import java.math.BigDecimal;

import fr.legrain.lib.data.LgrConstantes;
import fr.legrain.lib.data.ModelObject;

/**
 * IHMEnteteFacture generated by hbm2java
 */
public class IHMTotauxDevis extends ModelObject {

	private Integer idDocument=0;
	private Integer idTPaiement=0;
	private String codeTPaiement=LgrConstantes.C_STR_VIDE;
	private Integer idCPaiement=0;
	private BigDecimal regleDocument=new BigDecimal(0);
	private BigDecimal remHtDocument=new BigDecimal(0);
	private BigDecimal txRemHtDocument=new BigDecimal(0);
	private BigDecimal remTtcDocument=new BigDecimal(0);
	private BigDecimal txRemTtcDocument=new BigDecimal(0);
	private BigDecimal mtTtcCalc=new BigDecimal(0);
	private BigDecimal mtHtCalc=new BigDecimal(0);
	private BigDecimal mtTvaCalc=new BigDecimal(0);
	private BigDecimal netTtcCalc=new BigDecimal(0);
	private BigDecimal netHtCalc=new BigDecimal(0);
	private BigDecimal netTvaCalc=new BigDecimal(0);
	private String commentaire = "";
	
	private Boolean imprimer = false;

	public IHMTotauxDevis() {
	}

	public IHMTotauxDevis(Integer ID_DEVIS, Integer ID_T_PAIEMENT, String CODE_T_PAIEMENT,
			Integer ID_C_PAIEMENT, BigDecimal REGLE_DEVIS,
			BigDecimal REM_HT_DEVIS, BigDecimal TX_REM_HT_DEVIS,
			BigDecimal REM_TTC_DEVIS, BigDecimal TX_REM_TTC_DEVIS,
			BigDecimal MT_TTC_CALC, BigDecimal MT_HT_CALC,
			BigDecimal MT_TVA_CALC, BigDecimal NET_TTC_CALC, BigDecimal NET_HT_CALC,
			BigDecimal NET_TVA_CALC, Boolean imprimer,String COMMENTAIRE) {
		this.idDocument = ID_DEVIS;

		this.idTPaiement = ID_T_PAIEMENT;
		this.codeTPaiement = CODE_T_PAIEMENT;
		this.idCPaiement = ID_C_PAIEMENT;
		this.regleDocument = REGLE_DEVIS;
		this.remHtDocument = REM_HT_DEVIS;
		this.txRemHtDocument = TX_REM_HT_DEVIS;
		this.remTtcDocument = REM_TTC_DEVIS;
		this.txRemTtcDocument = TX_REM_TTC_DEVIS;
		this.mtTtcCalc = MT_TTC_CALC;
		this.mtHtCalc = MT_HT_CALC;
		this.mtTvaCalc = MT_TVA_CALC;
		this.netTtcCalc = NET_TTC_CALC;
		this.netHtCalc = NET_HT_CALC;
		this.netTvaCalc = NET_TVA_CALC;
		this.imprimer = imprimer;
		this.commentaire = COMMENTAIRE;
	}

	public void setIHMTotauxDEVIS(IHMTotauxDevis ihmTotauxDEVIS){
		setIdDocument(ihmTotauxDEVIS.idDocument);
		setIdTPaiement(ihmTotauxDEVIS.idTPaiement);
		setCodeTPaiement(ihmTotauxDEVIS.codeTPaiement);
		setIdCPaiement(ihmTotauxDEVIS.idCPaiement);
		///setREGLE_DEVIS(ihmTotauxDEVIS.REGLE_DEVIS);
		setRemHtDocument(ihmTotauxDEVIS.remHtDocument);
		setTxRemHtDocument(ihmTotauxDEVIS.txRemHtDocument);
		setRemTtcDocument(ihmTotauxDEVIS.remTtcDocument);
		setTxRemTtcDocument(ihmTotauxDEVIS.txRemTtcDocument);
		setMtTtcCalc(ihmTotauxDEVIS.mtTtcCalc);
		setMtHtCalc(ihmTotauxDEVIS.mtHtCalc);
		setMtTvaCalc(ihmTotauxDEVIS.mtTvaCalc);
		setNetTtcCalc(ihmTotauxDEVIS.netTtcCalc);
		setNetHtCalc(ihmTotauxDEVIS.netHtCalc);
		setNetTvaCalc(ihmTotauxDEVIS.netTvaCalc);
		setImprimer(ihmTotauxDEVIS.imprimer);
		setCommentaire(ihmTotauxDEVIS.commentaire);
		setRegleDocument(ihmTotauxDEVIS.regleDocument);
	}

	public static IHMTotauxDevis copy(IHMTotauxDevis ihmTotauxDEVIS){
		IHMTotauxDevis ihmEnteteDEVISLoc = new IHMTotauxDevis();
		ihmEnteteDEVISLoc.setIdDocument(ihmTotauxDEVIS.idDocument);
		ihmEnteteDEVISLoc.setIdTPaiement(ihmTotauxDEVIS.idTPaiement);
		ihmEnteteDEVISLoc.setCodeTPaiement(ihmTotauxDEVIS.codeTPaiement);
		ihmEnteteDEVISLoc.setIdCPaiement(ihmTotauxDEVIS.idCPaiement);
		//ihmEnteteDEVISLoc.setREGLE_DEVIS(ihmTotauxDEVIS.REGLE_DEVIS);
		ihmEnteteDEVISLoc.setRemHtDocument(ihmTotauxDEVIS.remHtDocument);
		ihmEnteteDEVISLoc.setTxRemHtDocument(ihmTotauxDEVIS.txRemHtDocument);
		ihmEnteteDEVISLoc.setRemTtcDocument(ihmTotauxDEVIS.remTtcDocument);
		ihmEnteteDEVISLoc.setTxRemTtcDocument(ihmTotauxDEVIS.txRemTtcDocument);
		ihmEnteteDEVISLoc.setMtTtcCalc(ihmTotauxDEVIS.mtTtcCalc);
		ihmEnteteDEVISLoc.setMtHtCalc(ihmTotauxDEVIS.mtHtCalc);
		ihmEnteteDEVISLoc.setMtTvaCalc(ihmTotauxDEVIS.mtTvaCalc);
		ihmEnteteDEVISLoc.setNetTtcCalc(ihmTotauxDEVIS.netTtcCalc);
		ihmEnteteDEVISLoc.setNetHtCalc(ihmTotauxDEVIS.netHtCalc);
		ihmEnteteDEVISLoc.setNetTvaCalc(ihmTotauxDEVIS.netTvaCalc);
		ihmEnteteDEVISLoc.setImprimer(ihmTotauxDEVIS.imprimer);
		ihmEnteteDEVISLoc.setCommentaire(ihmTotauxDEVIS.commentaire);
		ihmEnteteDEVISLoc.setRegleDocument(ihmTotauxDEVIS.regleDocument);
		return ihmEnteteDEVISLoc;
	}
	


	public Integer getIdDocument() {
		return this.idDocument;
	}

	public void setIdDocument(Integer ID_DEVIS) {
		firePropertyChange("idDocument", this.idDocument, this.idDocument = ID_DEVIS);
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



	public BigDecimal getRemHtDocument() {
		return this.remHtDocument;
	}

	public void setRemHtDocument(BigDecimal REM_HT_DEVIS) {
		firePropertyChange("remHtDocument", this.remHtDocument, this.remHtDocument = REM_HT_DEVIS);
	}

	public BigDecimal getTxRemHtDocument() {
		return this.txRemHtDocument;
	}

	public void setTxRemHtDocument(BigDecimal TX_REM_HT_DEVIS) {
		firePropertyChange("txRemHtDocument", this.txRemHtDocument, this.txRemHtDocument = TX_REM_HT_DEVIS);
	}

	public BigDecimal getRemTtcDocument() {
		return this.remTtcDocument;
	}

	public void setRemTtcDocument(BigDecimal REM_TTC_DEVIS) {
		firePropertyChange("remTtcDocument", this.remTtcDocument, this.remTtcDocument = REM_TTC_DEVIS);
	}

	public BigDecimal getTxRemTtcDocument() {
		return this.txRemTtcDocument;
	}

	public void setTxRemTtcDocument(BigDecimal TX_REM_TTC_DEVIS) {
		firePropertyChange("txRemTtcDocument", this.txRemTtcDocument, this.txRemTtcDocument = TX_REM_TTC_DEVIS);
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

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String COMMENTAIRE) {
		firePropertyChange("commentaire", this.commentaire, this.commentaire = COMMENTAIRE);
	}


	public Boolean getImprimer() {
		return imprimer;
	}

	public void setImprimer(Boolean imprimer) {
		firePropertyChange("imprimer", this.imprimer, this.imprimer = imprimer);
	}

	public BigDecimal getRegleDocument() {
		return regleDocument;
	}

	public void setRegleDocument(BigDecimal regleDocument) {
		firePropertyChange("regleDocument", this.regleDocument, this.regleDocument = regleDocument);
	}




	

}

package fr.legrain.gestCom.Module_Document;

// Generated Mar 1, 2007 11:26:10 AM by Hibernate Tools 3.2.0.b9

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;

import fr.legrain.lib.data.ModelObject;

/**
 * SWTLFacture generated by hbm2java
 */
public class IHMLPrelevement extends ModelObject implements PropertyChangeListener{

	private Integer idLDocument;
	private Integer idDocument;
	private String codeTLigne;
	private Integer idArticle;
	private Integer numLigneLDocument;
	private String codeArticle;
	private String libLDocument;
	private BigDecimal qteLDocument;
	private BigDecimal qte2LDocument;
	private BigDecimal prixULDocument;
	private BigDecimal tauxTvaLDocument;
	private String codeTvaLDocument;
	private String codeTTvaLDocument;
	private BigDecimal mtHtLDocument;
	private BigDecimal remTxLDocument;
	private BigDecimal mtTtcLDocument;
	private BigDecimal remHtLDocument;
	private String ipAcces;
	private String u1LDocument;
	private String u2LDocument;
	private BigDecimal txRemHtDocument;
	
	public IHMLPrelevement() {
	}

	public IHMLPrelevement(Integer ID_L_Proforma, Integer ID_Proforma,
			String ID_T_LIGNE, Integer ID_ARTICLE,
			Integer NUM_LIGNE_L_Proforma, String CODE_ARTICLE,
			String LIB_L_Proforma, BigDecimal QTE_L_Proforma,
			String U1_L_Proforma, String U2_L_Proforma,
			BigDecimal PRIX_U_L_Proforma, BigDecimal TAUX_TVA_L_Proforma,
			String CODE_TVA_L_Proforma, String CODE_T_TVA_L_Proforma,
			BigDecimal MT_HT_L_Proforma, BigDecimal MT_TTC_L_Proforma,
			BigDecimal REM_TX_L_Proforma, BigDecimal REM_HT_L_Proforma,
			String IP_ACCES) {
		this.idLDocument = ID_L_Proforma;
		this.idDocument = ID_Proforma;
		this.codeTLigne = ID_T_LIGNE;
		this.idArticle = ID_ARTICLE;
		this.numLigneLDocument = NUM_LIGNE_L_Proforma;
		this.codeArticle = CODE_ARTICLE;
		this.libLDocument = LIB_L_Proforma;
		this.qteLDocument = QTE_L_Proforma;
		this.u1LDocument = U1_L_Proforma;
		this.u2LDocument = U2_L_Proforma;
		this.prixULDocument = PRIX_U_L_Proforma;
		this.tauxTvaLDocument = TAUX_TVA_L_Proforma;
		this.codeTvaLDocument = CODE_TVA_L_Proforma;
		this.codeTTvaLDocument = CODE_T_TVA_L_Proforma;
		this.mtHtLDocument = MT_HT_L_Proforma;
		this.mtTtcLDocument = MT_TTC_L_Proforma;
		this.remTxLDocument = REM_TX_L_Proforma;
		this.remHtLDocument = REM_HT_L_Proforma;
		this.ipAcces = IP_ACCES;
	}
	
	public void setIHMLProforma(IHMLPrelevement ihmLProforma) {
		setIdLDocument(ihmLProforma.idLDocument);
		setIdDocument(ihmLProforma.idDocument);
		setCodeTLigne(ihmLProforma.codeTLigne);
		setIdArticle(ihmLProforma.idArticle);
		setNumLigneLDocument(ihmLProforma.numLigneLDocument);
		setCodeArticle(ihmLProforma.codeArticle);
		setLibLDocument(ihmLProforma.libLDocument);
		setQteLDocument(ihmLProforma.qteLDocument);
		setU1LDocument(ihmLProforma.u1LDocument);
		setU2LDocument(ihmLProforma.u2LDocument);
		setPrixULDocument(ihmLProforma.prixULDocument);
		setTauxTvaLDocument(ihmLProforma.tauxTvaLDocument);
		setCodeTvaLDocument(ihmLProforma.codeTvaLDocument);
		setCodeTTvaLDocument(ihmLProforma.codeTTvaLDocument);
		setMtHtLDocument(ihmLProforma.mtHtLDocument);
		setMtTtcLDocument(ihmLProforma.mtTtcLDocument);
		setRemTxLDocument(ihmLProforma.remTxLDocument);
		setRemHtLDocument(ihmLProforma.remHtLDocument);
		setIpAcces(ihmLProforma.ipAcces);
	}
	
	static public IHMLPrelevement copy(IHMLPrelevement ihmLProforma) {
		IHMLPrelevement swtLProformaLoc = new IHMLPrelevement();
		swtLProformaLoc.setIdLDocument(ihmLProforma.idLDocument);
		swtLProformaLoc.setIdDocument(ihmLProforma.idDocument);
		swtLProformaLoc.setCodeTLigne(ihmLProforma.codeTLigne);
		swtLProformaLoc.setIdArticle(ihmLProforma.idArticle);
		swtLProformaLoc.setNumLigneLDocument(ihmLProforma.numLigneLDocument);
		swtLProformaLoc.setCodeArticle(ihmLProforma.codeArticle);
		swtLProformaLoc.setLibLDocument(ihmLProforma.libLDocument);
		swtLProformaLoc.setQteLDocument(ihmLProforma.qteLDocument);
		swtLProformaLoc.setU1LDocument(ihmLProforma.u1LDocument);
		swtLProformaLoc.setU2LDocument(ihmLProforma.u2LDocument);
		swtLProformaLoc.setPrixULDocument(ihmLProforma.prixULDocument);
		swtLProformaLoc.setTauxTvaLDocument(ihmLProforma.tauxTvaLDocument);
		swtLProformaLoc.setCodeTvaLDocument(ihmLProforma.codeTvaLDocument);
		swtLProformaLoc.setCodeTTvaLDocument(ihmLProforma.codeTTvaLDocument);
		swtLProformaLoc.setMtHtLDocument(ihmLProforma.mtHtLDocument);
		swtLProformaLoc.setMtTtcLDocument(ihmLProforma.mtTtcLDocument);
		swtLProformaLoc.setRemTxLDocument(ihmLProforma.remTxLDocument);
		swtLProformaLoc.setRemHtLDocument(ihmLProforma.remHtLDocument);
		swtLProformaLoc.setIpAcces(ihmLProforma.ipAcces);
		return swtLProformaLoc;
	}

	public Integer getIdLDocument() {
		return this.idLDocument;
	}

	public void setIdLDocument(Integer ID_L_Proforma) {
		firePropertyChange("idLDocument", this.idLDocument, this.idLDocument = ID_L_Proforma);
	}

	public Integer getIdDocument() {
		return this.idDocument;
	}

	public void setIdDocument(Integer ID_Proforma) {
		firePropertyChange("idDocument", this.idDocument, this.idDocument = ID_Proforma);
	}

	public String getCodeTLigne() {
		return this.codeTLigne;
	}

	public void setCodeTLigne(String codeTLigne) {
		firePropertyChange("codeTLigne", this.codeTLigne, this.codeTLigne = codeTLigne);
	}


	public Integer getIdArticle() {
		return this.idArticle;
	}

	public void setIdArticle(Integer ID_ARTICLE) {
		firePropertyChange("ID_ARTICLE", this.idArticle, this.idArticle = ID_ARTICLE);
	}

	public Integer getNumLigneLDocument() {
		return this.numLigneLDocument;
	}

	public void setNumLigneLDocument(Integer NUM_LIGNE_L_Proforma) {
		firePropertyChange("numLigneLDocument", this.numLigneLDocument, this.numLigneLDocument = NUM_LIGNE_L_Proforma);
	}

	public String getCodeArticle() {
		return this.codeArticle;
	}

	public void setCodeArticle(String CODE_ARTICLE) {
		firePropertyChange("codeArticle", this.codeArticle, this.codeArticle = CODE_ARTICLE);
	}

	public String getLibLDocument() {
		return this.libLDocument;
	}

	public void setLibLDocument(String LIB_L_Proforma) {
		firePropertyChange("libLDocument", this.libLDocument, this.libLDocument = LIB_L_Proforma);
	}

	public BigDecimal getQteLDocument() {
		return this.qteLDocument;
	}

	public void setQteLDocument(BigDecimal QTE_L_Proforma) {
		firePropertyChange("qteLDocument", this.qteLDocument, this.qteLDocument = QTE_L_Proforma);
	}

	public String getU1LDocument() {
		return this.u1LDocument;
	}

	public void setU1LDocument(String U1_L_Proforma) {
		firePropertyChange("u1LDocument", this.u1LDocument, this.u1LDocument = U1_L_Proforma);
	}

	public String getU2LDocument() {
		return this.u2LDocument;
	}

	public void setU2LDocument(String U2_L_Proforma) {
		firePropertyChange("u2LDocument", this.u2LDocument, this.u2LDocument = U2_L_Proforma);
	}

	public BigDecimal getPrixULDocument() {
		return this.prixULDocument;
	}

	public void setPrixULDocument(BigDecimal PRIX_U_L_Proforma) {
		firePropertyChange("prixULDocument", this.prixULDocument, this.prixULDocument = PRIX_U_L_Proforma);
	}

	public BigDecimal getTauxTvaLDocument() {
		return this.tauxTvaLDocument;
	}

	public void setTauxTvaLDocument(BigDecimal TAUX_TVA_L_Proforma) {
		firePropertyChange("tauxTvaLDocument", this.tauxTvaLDocument, this.tauxTvaLDocument = TAUX_TVA_L_Proforma);
	}

	public String getCodeTvaLDocument() {
		return this.codeTvaLDocument;
	}

	public void setCodeTvaLDocument(String CODE_TVA_L_Proforma) {
		firePropertyChange("codeTvaLDocument", this.codeTvaLDocument, this.codeTvaLDocument = CODE_TVA_L_Proforma);
	}

	public String getCodeTTvaLDocument() {
		return this.codeTTvaLDocument;
	}

	public void setCodeTTvaLDocument(String CODE_T_TVA_L_Proforma) {
		firePropertyChange("codeTTvaLDocument", this.codeTTvaLDocument, this.codeTTvaLDocument = CODE_T_TVA_L_Proforma);
	}

	public BigDecimal getMtHtLDocument() {
		return this.mtHtLDocument;
	}

	public void setMtHtLDocument(BigDecimal MT_HT_L_Proforma) {
		firePropertyChange("mtHtLDocument", this.mtHtLDocument, this.mtHtLDocument = MT_HT_L_Proforma);
	}

	public BigDecimal getMtTtcLDocument() {
		return this.mtTtcLDocument;
	}

	public void setMtTtcLDocument(BigDecimal MT_TTC_L_Proforma) {
		firePropertyChange("mtTtcLDocument", this.mtTtcLDocument, this.mtTtcLDocument = MT_TTC_L_Proforma);
	}

	public BigDecimal getRemTxLDocument() {
		return this.remTxLDocument;
	}

	public void setRemTxLDocument(BigDecimal REM_TX_L_Proforma) {
		firePropertyChange("remTxLDocument", this.remTxLDocument, this.remTxLDocument = REM_TX_L_Proforma);
	}

	public BigDecimal getRemHtLDocument() {
		return this.remHtLDocument;
	}

	public void setRemHtLDocument(BigDecimal REM_HT_L_Proforma) {
		firePropertyChange("remHtLDocument", this.remHtLDocument, this.remHtLDocument = REM_HT_L_Proforma);
	}

	public String getIpAcces() {
		return this.ipAcces;
	}

	public void setIpAcces(String IP_ACCES) {
		firePropertyChange("ipAcces", this.ipAcces, this.ipAcces = IP_ACCES);
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof IHMLPrelevement))
			return false;
		IHMLPrelevement castOther = (IHMLPrelevement) other;

		return ((this.getIdLDocument() == castOther.getIdLDocument()) || (this
				.getIdLDocument() != null
				&& castOther.getIdLDocument() != null && this
				.getIdLDocument().equals(castOther.getIdLDocument())))
				&& ((this.getIdDocument() == castOther.getIdDocument()) || (this
						.getIdDocument() != null
						&& castOther.getIdDocument() != null && this
						.getIdDocument().equals(castOther.getIdDocument())))
				&& ((this.getCodeTLigne() == castOther.getCodeTLigne()) || (this
						.getCodeTLigne() != null
						&& castOther.getCodeTLigne() != null && this
						.getCodeTLigne().equals(castOther.getCodeTLigne())))
				&& ((this.getIdArticle() == castOther.getIdArticle()) || (this
						.getIdArticle() != null
						&& castOther.getIdArticle() != null && this
						.getIdArticle().equals(castOther.getIdArticle())))
				&& ((this.getNumLigneLDocument() == castOther
						.getNumLigneLDocument()) || (this
						.getNumLigneLDocument() != null
						&& castOther.getNumLigneLDocument() != null && this
						.getNumLigneLDocument().equals(
								castOther.getNumLigneLDocument())))
				&& ((this.getCodeArticle() == castOther.getCodeArticle()) || (this
						.getCodeArticle() != null
						&& castOther.getCodeArticle() != null && this
						.getCodeArticle().equals(castOther.getCodeArticle())))
				&& ((this.getLibLDocument() == castOther.getLibLDocument()) || (this
						.getLibLDocument() != null
						&& castOther.getLibLDocument() != null && this
						.getLibLDocument()
						.equals(castOther.getLibLDocument())))
				&& ((this.getQteLDocument() == castOther.getQteLDocument()) || (this
						.getQteLDocument() != null
						&& castOther.getQteLDocument() != null && this
						.getQteLDocument()
						.equals(castOther.getQteLDocument())))
				&& ((this.getU1LDocument() == castOther.getU1LDocument()) || (this
						.getU1LDocument() != null
						&& castOther.getU1LDocument() != null && this
						.getU1LDocument().equals(castOther.getU1LDocument())))
				&& ((this.getU2LDocument() == castOther.getU2LDocument()) || (this
						.getU2LDocument() != null
						&& castOther.getU2LDocument() != null && this
						.getU2LDocument().equals(castOther.getU2LDocument())))
				&& ((this.getPrixULDocument() == castOther
						.getPrixULDocument()) || (this.getPrixULDocument() != null
						&& castOther.getPrixULDocument() != null && this
						.getPrixULDocument().equals(
								castOther.getPrixULDocument())))
				&& ((this.getTauxTvaLDocument() == castOther
						.getTauxTvaLDocument()) || (this
						.getTauxTvaLDocument() != null
						&& castOther.getTauxTvaLDocument() != null && this
						.getTauxTvaLDocument().equals(
								castOther.getTauxTvaLDocument())))
				&& ((this.getCodeTvaLDocument() == castOther
						.getCodeTvaLDocument()) || (this
						.getCodeTvaLDocument() != null
						&& castOther.getCodeTvaLDocument() != null && this
						.getCodeTvaLDocument().equals(
								castOther.getCodeTvaLDocument())))
				&& ((this.getCodeTTvaLDocument() == castOther
						.getCodeTTvaLDocument()) || (this
						.getCodeTTvaLDocument() != null
						&& castOther.getCodeTTvaLDocument() != null && this
						.getCodeTTvaLDocument().equals(
								castOther.getCodeTTvaLDocument())))
				&& ((this.getMtHtLDocument() == castOther
						.getMtHtLDocument()) || (this.getMtHtLDocument() != null
						&& castOther.getMtHtLDocument() != null && this
						.getMtHtLDocument().equals(
								castOther.getMtHtLDocument())))
				&& ((this.getMtTtcLDocument() == castOther
						.getMtTtcLDocument()) || (this.getMtTtcLDocument() != null
						&& castOther.getMtTtcLDocument() != null && this
						.getMtTtcLDocument().equals(
								castOther.getMtTtcLDocument())))
				&& ((this.getRemTxLDocument() == castOther
						.getRemTxLDocument()) || (this.getRemTxLDocument() != null
						&& castOther.getRemTxLDocument() != null && this
						.getRemTxLDocument().equals(
								castOther.getRemTxLDocument())))
				&& ((this.getRemHtLDocument() == castOther
						.getRemHtLDocument()) || (this.getRemHtLDocument() != null
						&& castOther.getRemHtLDocument() != null && this
						.getRemHtLDocument().equals(
								castOther.getRemHtLDocument())))
				&& ((this.getIpAcces() == castOther.getIpAcces()) || (this
						.getIpAcces() != null
						&& castOther.getIpAcces() != null && this
						.getIpAcces().equals(castOther.getIpAcces())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getIdLDocument() == null ? 0 : this.getIdLDocument()
						.hashCode());
		result = 37
				* result
				+ (getIdDocument() == null ? 0 : this.getIdDocument()
						.hashCode());
		result = 37
				* result
				+ (getCodeTLigne() == null ? 0 : this.getCodeTLigne()
						.hashCode());
		result = 37
				* result
				+ (getIdArticle() == null ? 0 : this.getIdArticle()
						.hashCode());
		result = 37
				* result
				+ (getNumLigneLDocument() == null ? 0 : this
						.getNumLigneLDocument().hashCode());
		result = 37
				* result
				+ (getCodeArticle() == null ? 0 : this.getCodeArticle()
						.hashCode());
		result = 37
				* result
				+ (getLibLDocument() == null ? 0 : this.getLibLDocument()
						.hashCode());
		result = 37
				* result
				+ (getQteLDocument() == null ? 0 : this.getQteLDocument()
						.hashCode());
		result = 37
				* result
				+ (getU1LDocument() == null ? 0 : this.getU1LDocument()
						.hashCode());
		result = 37
				* result
				+ (getU2LDocument() == null ? 0 : this.getU2LDocument()
						.hashCode());
		result = 37
				* result
				+ (getPrixULDocument() == null ? 0 : this
						.getPrixULDocument().hashCode());
		result = 37
				* result
				+ (getTauxTvaLDocument() == null ? 0 : this
						.getTauxTvaLDocument().hashCode());
		result = 37
				* result
				+ (getCodeTvaLDocument() == null ? 0 : this
						.getCodeTvaLDocument().hashCode());
		result = 37
				* result
				+ (getCodeTTvaLDocument() == null ? 0 : this
						.getCodeTTvaLDocument().hashCode());
		result = 37
				* result
				+ (getMtHtLDocument() == null ? 0 : this.getMtHtLDocument()
						.hashCode());
		result = 37
				* result
				+ (getMtTtcLDocument() == null ? 0 : this
						.getMtTtcLDocument().hashCode());
		result = 37
				* result
				+ (getRemTxLDocument() == null ? 0 : this
						.getRemTxLDocument().hashCode());
		result = 37
				* result
				+ (getRemHtLDocument() == null ? 0 : this
						.getRemHtLDocument().hashCode());
		result = 37 * result
				+ (getIpAcces() == null ? 0 : this.getIpAcces().hashCode());
		return result;
	}

	public void propertyChange(PropertyChangeEvent evt) {
/*		
//		*/
//		if(evt.getPropertyName().equals("ID_L_Proforma")){
//			this.setIdLProforma((Integer)evt.getNewValue());
//		}		
//		if(evt.getPropertyName().equals("ID_T_LIGNE")){
//			this.setCodeTLigne((Integer)evt.getNewValue());
//		}		
//		if(evt.getPropertyName().equals("ID_ARTICLE")){
//			this.setIdArticle((Integer)evt.getNewValue());
//		}		
//		if(evt.getPropertyName().equals("LIB_L_Proforma")){
//			this.setLibLProforma((String)evt.getNewValue());
//		}		
//		if(evt.getPropertyName().equals("QTE_L_Proforma")){
//			this.setQteLProforma((BigDecimal)evt.getNewValue());
//		}		
//		if(evt.getPropertyName().equals("U1_L_Proforma")){
//			this.setU1LProforma((String)evt.getNewValue());
//		}		
//		if(evt.getPropertyName().equals("U2_L_Proforma")){
//			this.setU2LProforma((String)evt.getNewValue());
//		}		
//		if(evt.getPropertyName().equals("PRIX_U_L_Proforma")){
//			this.setPrixULProforma((BigDecimal)evt.getNewValue());
//		}		
//		if(evt.getPropertyName().equals("TAUX_TVA_L_Proforma")){
//			this.setTauxTvaLProforma((BigDecimal)evt.getNewValue());
//		}		
//		if(evt.getPropertyName().equals("CODE_TVA_L_Proforma")){
//			this.setCodeTvaLProforma((String)evt.getNewValue());
//		}		
//		if(evt.getPropertyName().equals("CODE_T_TVA_L_Proforma")){
//			this.setCodeTTvaLProforma((String)evt.getNewValue());
//		}		
//		if(evt.getPropertyName().equals("MT_HT_L_Proforma")){
//			this.setMtHtLProforma((BigDecimal)evt.getNewValue());
//		}		
//		if(evt.getPropertyName().equals("MT_TTC_L_Proforma")){
//			this.setMtTtcLProforma((BigDecimal)evt.getNewValue());
//		}		
//		if(evt.getPropertyName().equals("REM_TX_L_Proforma")){
//			this.setRemTxLProforma((BigDecimal)evt.getNewValue());
//		}		
//		if(evt.getPropertyName().equals("REM_HT_L_Proforma")){
//			this.setRemHtLProforma((BigDecimal)evt.getNewValue());
//		}		
//		if(evt.getPropertyName().equals("CODE_ARTICLE")){
//			this.setCodeArticle((String)evt.getNewValue());
//		}		
	}

	public BigDecimal getTxRemHtDocument() {
		return txRemHtDocument;
	}

	public void setTxRemHtDocument(BigDecimal txRemHtProforma) {
		firePropertyChange("txRemHtDocument", this.txRemHtDocument, this.txRemHtDocument = txRemHtProforma);
	}

	public BigDecimal getQte2LDocument() {
		return qte2LDocument;
	}

	public void setQte2LDocument(BigDecimal qte2LProforma) {
		firePropertyChange("qte2LDocument", this.qte2LDocument, this.qte2LDocument = qte2LProforma);
	}

}

package fr.legrain.gestCom.Module_Articles;

// Generated Mar 1, 2007 11:26:10 AM by Hibernate Tools 3.2.0.b9

import java.math.BigDecimal;

import fr.legrain.lib.data.ModelObject;

/**
 * SWTArticle generated by hbm2java
 */
public class IHMArticle extends ModelObject {

	private Integer ID_ARTICLE;
	private String CODE_ARTICLE;
	private String LIBELLEC_ARTICLE;
	private String LIBELLEL_ARTICLE;
	private String NUMCPT_ARTICLE;
	private String DIVERS_ARTICLE;
	private String COMMENTAIRE_ARTICLE;
	private Integer ID_T_TVA;
	private BigDecimal STOCK_MIN_ARTICLE;
	private Integer ID_FAMILLE;
	private Integer ID_PRIX;
	private BigDecimal PRIX_PRIX;
	private Integer ID_UNITE;
	private String CODE_UNITE;
	private String CODE_FAMILLE;
	private String LIBC_FAMILLE;
	private Integer ID_TVA;
	private String CODE_TVA;
	private BigDecimal TAUX_TVA;
	private String NUMCPT_TVA;
	private String CODE_T_TVA;
	private String LIB_T_TVA;

	public IHMArticle() {
	}
	
	public IHMArticle(Integer ID_ARTICLE, String CODE_ARTICLE,
			String LIBELLEC_ARTICLE, String LIBELLEL_ARTICLE,
			String NUMCPT_ARTICLE, String DIVERS_ARTICLE,
			String COMMENTAIRE_ARTICLE, Integer ID_T_TVA,
			BigDecimal STOCK_MIN_ARTICLE, Integer ID_FAMILLE, Integer ID_PRIX,
			BigDecimal PRIX_PRIX, Integer ID_UNITE, String CODE_UNITE,
			String CODE_FAMILLE, String LIBC_FAMILLE, Integer ID_TVA,
			String CODE_TVA, BigDecimal TAUX_TVA, String NUMCPT_TVA,
			String CODE_T_TVA, String LIB_T_TVA) {
		this.ID_ARTICLE = ID_ARTICLE;
		this.CODE_ARTICLE = CODE_ARTICLE;
		this.LIBELLEC_ARTICLE = LIBELLEC_ARTICLE;
		this.LIBELLEL_ARTICLE = LIBELLEL_ARTICLE;
		this.NUMCPT_ARTICLE = NUMCPT_ARTICLE;
		this.DIVERS_ARTICLE = DIVERS_ARTICLE;
		this.COMMENTAIRE_ARTICLE = COMMENTAIRE_ARTICLE;
		this.ID_T_TVA = ID_T_TVA;
		this.STOCK_MIN_ARTICLE = STOCK_MIN_ARTICLE;
		this.ID_FAMILLE = ID_FAMILLE;
		this.ID_PRIX = ID_PRIX;
		this.PRIX_PRIX = PRIX_PRIX;
		this.ID_UNITE = ID_UNITE;
		this.CODE_UNITE = CODE_UNITE;
		this.CODE_FAMILLE = CODE_FAMILLE;
		this.LIBC_FAMILLE = LIBC_FAMILLE;
		this.ID_TVA = ID_TVA;
		this.CODE_TVA = CODE_TVA;
		this.TAUX_TVA = TAUX_TVA;
		this.NUMCPT_TVA = NUMCPT_TVA;
		this.CODE_T_TVA = CODE_T_TVA;
		this.LIB_T_TVA = LIB_T_TVA;
	}

	public void setSWTArticle(IHMArticle swtArticle) {
		this.ID_ARTICLE = swtArticle.ID_ARTICLE;
		this.CODE_ARTICLE = swtArticle.CODE_ARTICLE;
		this.LIBELLEC_ARTICLE = swtArticle.LIBELLEC_ARTICLE;
		this.LIBELLEL_ARTICLE = swtArticle.LIBELLEL_ARTICLE;
		this.NUMCPT_ARTICLE = swtArticle.NUMCPT_ARTICLE;
		this.DIVERS_ARTICLE = swtArticle.DIVERS_ARTICLE;
		this.COMMENTAIRE_ARTICLE = swtArticle.COMMENTAIRE_ARTICLE;
		this.ID_T_TVA = swtArticle.ID_T_TVA;
		this.STOCK_MIN_ARTICLE = swtArticle.STOCK_MIN_ARTICLE;
		this.ID_FAMILLE = swtArticle.ID_FAMILLE;
		this.ID_PRIX = swtArticle.ID_PRIX;
		this.PRIX_PRIX = swtArticle.PRIX_PRIX;
		this.ID_UNITE = swtArticle.ID_UNITE;
		this.CODE_UNITE = swtArticle.CODE_UNITE;
		this.CODE_FAMILLE = swtArticle.CODE_FAMILLE;
		this.LIBC_FAMILLE = swtArticle.LIBC_FAMILLE;
		this.ID_TVA = swtArticle.ID_TVA;
		this.CODE_TVA = swtArticle.CODE_TVA;
		this.TAUX_TVA = swtArticle.TAUX_TVA;
		this.NUMCPT_TVA = swtArticle.NUMCPT_TVA;
		this.CODE_T_TVA = swtArticle.CODE_T_TVA;
		this.LIB_T_TVA = swtArticle.LIB_T_TVA;
	}

	public Integer getID_ARTICLE() {
		return this.ID_ARTICLE;
	}

	public void setID_ARTICLE(Integer ID_ARTICLE) {
		firePropertyChange("ID_ARTICLE", this.ID_ARTICLE, this.ID_ARTICLE = ID_ARTICLE);
	}

	public String getCODE_ARTICLE() {
		return this.CODE_ARTICLE;
	}

	public void setCODE_ARTICLE(String CODE_ARTICLE) {
		firePropertyChange("CODE_ARTICLE", this.CODE_ARTICLE, this.CODE_ARTICLE = CODE_ARTICLE);
	}

	public String getLIBELLEC_ARTICLE() {
		return this.LIBELLEC_ARTICLE;
	}

	public void setLIBELLEC_ARTICLE(String LIBELLEC_ARTICLE) {
		firePropertyChange("LIBELLEC_ARTICLE", this.LIBELLEC_ARTICLE, this.LIBELLEC_ARTICLE = LIBELLEC_ARTICLE);
	}

	public String getLIBELLEL_ARTICLE() {
		return this.LIBELLEL_ARTICLE;
	}

	public void setLIBELLEL_ARTICLE(String LIBELLEL_ARTICLE) {
		firePropertyChange("LIBELLEL_ARTICLE", this.LIBELLEL_ARTICLE, this.LIBELLEL_ARTICLE = LIBELLEL_ARTICLE);
	}

	public String getNUMCPT_ARTICLE() {
		return this.NUMCPT_ARTICLE;
	}

	public void setNUMCPT_ARTICLE(String NUMCPT_ARTICLE) {
		firePropertyChange("NUMCPT_ARTICLE", this.NUMCPT_ARTICLE, this.NUMCPT_ARTICLE = NUMCPT_ARTICLE);
	}

	public String getDIVERS_ARTICLE() {
		return this.DIVERS_ARTICLE;
	}

	public void setDIVERS_ARTICLE(String DIVERS_ARTICLE) {
		firePropertyChange("DIVERS_ARTICLE", this.DIVERS_ARTICLE, this.DIVERS_ARTICLE = DIVERS_ARTICLE);
	}

	public String getCOMMENTAIRE_ARTICLE() {
		return this.COMMENTAIRE_ARTICLE;
	}

	public void setCOMMENTAIRE_ARTICLE(String COMMENTAIRE_ARTICLE) {
		firePropertyChange("COMMENTAIRE_ARTICLE", this.COMMENTAIRE_ARTICLE, this.COMMENTAIRE_ARTICLE = COMMENTAIRE_ARTICLE);
	}

	public Integer getID_T_TVA() {
		return this.ID_T_TVA;
	}

	public void setID_T_TVA(Integer ID_T_TVA) {
		firePropertyChange("ID_T_TVA", this.ID_T_TVA, this.ID_T_TVA = ID_T_TVA);
	}

	public BigDecimal getSTOCK_MIN_ARTICLE() {
		return this.STOCK_MIN_ARTICLE;
	}

	public void setSTOCK_MIN_ARTICLE(BigDecimal STOCK_MIN_ARTICLE) {
		firePropertyChange("STOCK_MIN_ARTICLE", this.STOCK_MIN_ARTICLE, this.STOCK_MIN_ARTICLE = STOCK_MIN_ARTICLE);
	}

	public Integer getID_FAMILLE() {
		return this.ID_FAMILLE;
	}

	public void setID_FAMILLE(Integer ID_FAMILLE) {
		firePropertyChange("ID_FAMILLE", this.ID_FAMILLE, this.ID_FAMILLE = ID_FAMILLE);
	}

	public Integer getID_PRIX() {
		return this.ID_PRIX;
	}

	public void setID_PRIX(Integer ID_PRIX) {
		firePropertyChange("ID_PRIX", this.ID_PRIX, this.ID_PRIX = ID_PRIX);
	}

	public BigDecimal getPRIX_PRIX() {
		return this.PRIX_PRIX;
	}

	public void setPRIX_PRIX(BigDecimal PRIX_PRIX) {
		firePropertyChange("PRIX_PRIX", this.PRIX_PRIX, this.PRIX_PRIX = PRIX_PRIX);
	}

	public Integer getID_UNITE() {
		return this.ID_UNITE;
	}

	public void setID_UNITE(Integer ID_UNITE) {
		firePropertyChange("ID_UNITE", this.ID_UNITE, this.ID_UNITE = ID_UNITE);
	}

	public String getCODE_UNITE() {
		return this.CODE_UNITE;
	}

	public void setCODE_UNITE(String CODE_UNITE) {
		firePropertyChange("CODE_UNITE", this.CODE_UNITE, this.CODE_UNITE = CODE_UNITE);
	}

	public String getCODE_FAMILLE() {
		return this.CODE_FAMILLE;
	}

	public void setCODE_FAMILLE(String CODE_FAMILLE) {
		firePropertyChange("CODE_FAMILLE", this.CODE_FAMILLE, this.CODE_FAMILLE = CODE_FAMILLE);
	}

	public String getLIBC_FAMILLE() {
		return this.LIBC_FAMILLE;
	}

	public void setLIBC_FAMILLE(String LIBC_FAMILLE) {
		firePropertyChange("LIBC_FAMILLE", this.LIBC_FAMILLE, this.LIBC_FAMILLE = LIBC_FAMILLE);
	}

	public Integer getID_TVA() {
		return this.ID_TVA;
	}

	public void setID_TVA(Integer ID_TVA) {
		firePropertyChange("ID_TVA", this.ID_TVA, this.ID_TVA = ID_TVA);
	}

	public String getCODE_TVA() {
		return this.CODE_TVA;
	}

	public void setCODE_TVA(String CODE_TVA) {
		firePropertyChange("CODE_TVA", this.CODE_TVA, this.CODE_TVA = CODE_TVA);
	}

	public BigDecimal getTAUX_TVA() {
		return this.TAUX_TVA;
	}

	public void setTAUX_TVA(BigDecimal TAUX_TVA) {
		firePropertyChange("TAUX_TVA", this.TAUX_TVA, this.TAUX_TVA = TAUX_TVA);
	}

	public String getNUMCPT_TVA() {
		return this.NUMCPT_TVA;
	}

	public void setNUMCPT_TVA(String NUMCPT_TVA) {
		firePropertyChange("NUMCPT_TVA", this.NUMCPT_TVA, this.NUMCPT_TVA = NUMCPT_TVA);
	}

	public String getCODE_T_TVA() {
		return this.CODE_T_TVA;
	}

	public void setCODE_T_TVA(String CODE_T_TVA) {
		firePropertyChange("CODE_T_TVA", this.CODE_T_TVA, this.CODE_T_TVA = CODE_T_TVA);
	}

	public String getLIB_T_TVA() {
		return this.LIB_T_TVA;
	}

	public void setLIB_T_TVA(String LIB_T_TVA) {
		firePropertyChange("LIB_T_TVA", this.LIB_T_TVA, this.LIB_T_TVA = LIB_T_TVA);
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof IHMArticle))
			return false;
		IHMArticle castOther = (IHMArticle) other;

		return ((this.getID_ARTICLE() == castOther.getID_ARTICLE()) || (this
				.getID_ARTICLE() != null
				&& castOther.getID_ARTICLE() != null && this.getID_ARTICLE()
				.equals(castOther.getID_ARTICLE())))
				&& ((this.getCODE_ARTICLE() == castOther.getCODE_ARTICLE()) || (this
						.getCODE_ARTICLE() != null
						&& castOther.getCODE_ARTICLE() != null && this
						.getCODE_ARTICLE().equals(castOther.getCODE_ARTICLE())))
				&& ((this.getLIBELLEC_ARTICLE() == castOther
						.getLIBELLEC_ARTICLE()) || (this.getLIBELLEC_ARTICLE() != null
						&& castOther.getLIBELLEC_ARTICLE() != null && this
						.getLIBELLEC_ARTICLE().equals(
								castOther.getLIBELLEC_ARTICLE())))
				&& ((this.getLIBELLEL_ARTICLE() == castOther
						.getLIBELLEL_ARTICLE()) || (this.getLIBELLEL_ARTICLE() != null
						&& castOther.getLIBELLEL_ARTICLE() != null && this
						.getLIBELLEL_ARTICLE().equals(
								castOther.getLIBELLEL_ARTICLE())))
				&& ((this.getNUMCPT_ARTICLE() == castOther.getNUMCPT_ARTICLE()) || (this
						.getNUMCPT_ARTICLE() != null
						&& castOther.getNUMCPT_ARTICLE() != null && this
						.getNUMCPT_ARTICLE().equals(
								castOther.getNUMCPT_ARTICLE())))
				&& ((this.getDIVERS_ARTICLE() == castOther.getDIVERS_ARTICLE()) || (this
						.getDIVERS_ARTICLE() != null
						&& castOther.getDIVERS_ARTICLE() != null && this
						.getDIVERS_ARTICLE().equals(
								castOther.getDIVERS_ARTICLE())))
				&& ((this.getCOMMENTAIRE_ARTICLE() == castOther
						.getCOMMENTAIRE_ARTICLE()) || (this
						.getCOMMENTAIRE_ARTICLE() != null
						&& castOther.getCOMMENTAIRE_ARTICLE() != null && this
						.getCOMMENTAIRE_ARTICLE().equals(
								castOther.getCOMMENTAIRE_ARTICLE())))
				&& ((this.getID_T_TVA() == castOther.getID_T_TVA()) || (this
						.getID_T_TVA() != null
						&& castOther.getID_T_TVA() != null && this
						.getID_T_TVA().equals(castOther.getID_T_TVA())))
				&& ((this.getSTOCK_MIN_ARTICLE() == castOther
						.getSTOCK_MIN_ARTICLE()) || (this
						.getSTOCK_MIN_ARTICLE() != null
						&& castOther.getSTOCK_MIN_ARTICLE() != null && this
						.getSTOCK_MIN_ARTICLE().equals(
								castOther.getSTOCK_MIN_ARTICLE())))
				&& ((this.getID_FAMILLE() == castOther.getID_FAMILLE()) || (this
						.getID_FAMILLE() != null
						&& castOther.getID_FAMILLE() != null && this
						.getID_FAMILLE().equals(castOther.getID_FAMILLE())))
				&& ((this.getID_PRIX() == castOther.getID_PRIX()) || (this
						.getID_PRIX() != null
						&& castOther.getID_PRIX() != null && this.getID_PRIX()
						.equals(castOther.getID_PRIX())))
				&& ((this.getPRIX_PRIX() == castOther.getPRIX_PRIX()) || (this
						.getPRIX_PRIX() != null
						&& castOther.getPRIX_PRIX() != null && this
						.getPRIX_PRIX().equals(castOther.getPRIX_PRIX())))
				&& ((this.getID_UNITE() == castOther.getID_UNITE()) || (this
						.getID_UNITE() != null
						&& castOther.getID_UNITE() != null && this
						.getID_UNITE().equals(castOther.getID_UNITE())))
				&& ((this.getCODE_UNITE() == castOther.getCODE_UNITE()) || (this
						.getCODE_UNITE() != null
						&& castOther.getCODE_UNITE() != null && this
						.getCODE_UNITE().equals(castOther.getCODE_UNITE())))
				&& ((this.getCODE_FAMILLE() == castOther.getCODE_FAMILLE()) || (this
						.getCODE_FAMILLE() != null
						&& castOther.getCODE_FAMILLE() != null && this
						.getCODE_FAMILLE().equals(castOther.getCODE_FAMILLE())))
				&& ((this.getLIBC_FAMILLE() == castOther.getLIBC_FAMILLE()) || (this
						.getLIBC_FAMILLE() != null
						&& castOther.getLIBC_FAMILLE() != null && this
						.getLIBC_FAMILLE().equals(castOther.getLIBC_FAMILLE())))
				&& ((this.getID_TVA() == castOther.getID_TVA()) || (this
						.getID_TVA() != null
						&& castOther.getID_TVA() != null && this.getID_TVA()
						.equals(castOther.getID_TVA())))
				&& ((this.getCODE_TVA() == castOther.getCODE_TVA()) || (this
						.getCODE_TVA() != null
						&& castOther.getCODE_TVA() != null && this
						.getCODE_TVA().equals(castOther.getCODE_TVA())))
				&& ((this.getTAUX_TVA() == castOther.getTAUX_TVA()) || (this
						.getTAUX_TVA() != null
						&& castOther.getTAUX_TVA() != null && this
						.getTAUX_TVA().equals(castOther.getTAUX_TVA())))
				&& ((this.getNUMCPT_TVA() == castOther.getNUMCPT_TVA()) || (this
						.getNUMCPT_TVA() != null
						&& castOther.getNUMCPT_TVA() != null && this
						.getNUMCPT_TVA().equals(castOther.getNUMCPT_TVA())))
				&& ((this.getCODE_T_TVA() == castOther.getCODE_T_TVA()) || (this
						.getCODE_T_TVA() != null
						&& castOther.getCODE_T_TVA() != null && this
						.getCODE_T_TVA().equals(castOther.getCODE_T_TVA())))
				&& ((this.getLIB_T_TVA() == castOther.getLIB_T_TVA()) || (this
						.getLIB_T_TVA() != null
						&& castOther.getLIB_T_TVA() != null && this
						.getLIB_T_TVA().equals(castOther.getLIB_T_TVA())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getID_ARTICLE() == null ? 0 : this.getID_ARTICLE()
						.hashCode());
		result = 37
				* result
				+ (getCODE_ARTICLE() == null ? 0 : this.getCODE_ARTICLE()
						.hashCode());
		result = 37
				* result
				+ (getLIBELLEC_ARTICLE() == null ? 0 : this
						.getLIBELLEC_ARTICLE().hashCode());
		result = 37
				* result
				+ (getLIBELLEL_ARTICLE() == null ? 0 : this
						.getLIBELLEL_ARTICLE().hashCode());
		result = 37
				* result
				+ (getNUMCPT_ARTICLE() == null ? 0 : this.getNUMCPT_ARTICLE()
						.hashCode());
		result = 37
				* result
				+ (getDIVERS_ARTICLE() == null ? 0 : this.getDIVERS_ARTICLE()
						.hashCode());
		result = 37
				* result
				+ (getCOMMENTAIRE_ARTICLE() == null ? 0 : this
						.getCOMMENTAIRE_ARTICLE().hashCode());
		result = 37 * result
				+ (getID_T_TVA() == null ? 0 : this.getID_T_TVA().hashCode());
		result = 37
				* result
				+ (getSTOCK_MIN_ARTICLE() == null ? 0 : this
						.getSTOCK_MIN_ARTICLE().hashCode());
		result = 37
				* result
				+ (getID_FAMILLE() == null ? 0 : this.getID_FAMILLE()
						.hashCode());
		result = 37 * result
				+ (getID_PRIX() == null ? 0 : this.getID_PRIX().hashCode());
		result = 37 * result
				+ (getPRIX_PRIX() == null ? 0 : this.getPRIX_PRIX().hashCode());
		result = 37 * result
				+ (getID_UNITE() == null ? 0 : this.getID_UNITE().hashCode());
		result = 37
				* result
				+ (getCODE_UNITE() == null ? 0 : this.getCODE_UNITE()
						.hashCode());
		result = 37
				* result
				+ (getCODE_FAMILLE() == null ? 0 : this.getCODE_FAMILLE()
						.hashCode());
		result = 37
				* result
				+ (getLIBC_FAMILLE() == null ? 0 : this.getLIBC_FAMILLE()
						.hashCode());
		result = 37 * result
				+ (getID_TVA() == null ? 0 : this.getID_TVA().hashCode());
		result = 37 * result
				+ (getCODE_TVA() == null ? 0 : this.getCODE_TVA().hashCode());
		result = 37 * result
				+ (getTAUX_TVA() == null ? 0 : this.getTAUX_TVA().hashCode());
		result = 37
				* result
				+ (getNUMCPT_TVA() == null ? 0 : this.getNUMCPT_TVA()
						.hashCode());
		result = 37
				* result
				+ (getCODE_T_TVA() == null ? 0 : this.getCODE_T_TVA()
						.hashCode());
		result = 37 * result
				+ (getLIB_T_TVA() == null ? 0 : this.getLIB_T_TVA().hashCode());
		return result;
	}
	
	public static IHMArticle copy(IHMArticle swtArticle){
		IHMArticle swtArticleLoc = new IHMArticle();
		swtArticleLoc.setCODE_ARTICLE(swtArticle.CODE_ARTICLE);
		swtArticleLoc.setCODE_FAMILLE(swtArticle.CODE_FAMILLE);
		swtArticleLoc.setCODE_T_TVA(swtArticle.CODE_T_TVA);
		swtArticleLoc.setCODE_TVA(swtArticle.CODE_TVA);
		swtArticleLoc.setCODE_UNITE(swtArticle.CODE_UNITE);
		swtArticleLoc.setCOMMENTAIRE_ARTICLE(swtArticle.COMMENTAIRE_ARTICLE);
		swtArticleLoc.setDIVERS_ARTICLE(swtArticle.DIVERS_ARTICLE);
		swtArticleLoc.setID_ARTICLE(swtArticle.ID_ARTICLE);
		swtArticleLoc.setID_FAMILLE(swtArticle.ID_FAMILLE);
		swtArticleLoc.setID_PRIX(swtArticle.ID_PRIX);
		swtArticleLoc.setID_T_TVA(swtArticle.ID_T_TVA);
		swtArticleLoc.setID_TVA(swtArticle.ID_TVA);
		swtArticleLoc.setID_UNITE(swtArticle.ID_UNITE);
		swtArticleLoc.setLIB_T_TVA(swtArticle.LIB_T_TVA);
		swtArticleLoc.setLIBC_FAMILLE(swtArticle.LIBC_FAMILLE);
		swtArticleLoc.setLIBELLEC_ARTICLE(swtArticle.LIBELLEC_ARTICLE);
		swtArticleLoc.setLIBELLEL_ARTICLE(swtArticle.LIBELLEL_ARTICLE);
		swtArticleLoc.setNUMCPT_ARTICLE(swtArticle.NUMCPT_ARTICLE);
		swtArticleLoc.setNUMCPT_TVA(swtArticle.NUMCPT_TVA);
		swtArticleLoc.setPRIX_PRIX(swtArticle.PRIX_PRIX);
		swtArticleLoc.setSTOCK_MIN_ARTICLE(swtArticle.STOCK_MIN_ARTICLE);
		swtArticleLoc.setTAUX_TVA(swtArticle.TAUX_TVA);
		return swtArticleLoc;
	}

}

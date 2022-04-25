package fr.legrain.generationDocumentLGR.dao;

// Generated 11 août 2009 15:52:23 by Hibernate Tools 3.2.4.GA

import java.math.BigDecimal;


public class ArticlesMaintenance implements java.io.Serializable {

//	private Integer id;
	  private String articleE2;
	  private BigDecimal reducE2;
	  private Integer dureeE2;
	  private BigDecimal totalHtE2;
	  private String articleF2;
	  private BigDecimal reducF2;
	  private Integer dureeF2;
	  private BigDecimal totalHtF2;
	  private String articleS2;
	  private BigDecimal reducS2;
	  private Integer dureeS2;
	  private BigDecimal totalHtS2;
	  private BigDecimal participationCogere;
	  private BigDecimal bonusHt;
	  private Integer bdg;

	public ArticlesMaintenance() {
	}
	
	

	public String getArticleE2() {
		return articleE2;
	}

	public void setArticleE2(String article) {
		this.articleE2 = article;
	}

	public BigDecimal getReducE2() {
		return reducE2;
	}

	public void setReducE2(BigDecimal reducE2) {
		this.reducE2 = reducE2;
	}

	public Integer getDureeE2() {
		return dureeE2;
	}

	public void setDureeE2(Integer dureeE2) {
		this.dureeE2 = dureeE2;
	}

	public BigDecimal getTotalHtE2() {
		return totalHtE2;
	}

	public void setTotalHtE2(BigDecimal totalHtE2) {
		this.totalHtE2 = totalHtE2;
	}

	public BigDecimal getReducF2() {
		return reducF2;
	}

	public void setReducF2(BigDecimal reducF2) {
		this.reducF2 = reducF2;
	}

	public Integer getDureeF2() {
		return dureeF2;
	}

	public void setDureeF2(Integer dureeF2) {
		this.dureeF2 = dureeF2;
	}

	public BigDecimal getTotalHtF2() {
		return totalHtF2;
	}

	public void setTotalHtF2(BigDecimal totalHtF2) {
		this.totalHtF2 = totalHtF2;
	}

	public BigDecimal getReducS2() {
		return reducS2;
	}

	public void setReducS2(BigDecimal reducS2) {
		this.reducS2 = reducS2;
	}

	public Integer getDureeS2() {
		return dureeS2;
	}

	public void setDureeS2(Integer dureeS2) {
		this.dureeS2 = dureeS2;
	}

	public BigDecimal getTotalHtS2() {
		return totalHtS2;
	}

	public void setTotalHtS2(BigDecimal totalHtS2) {
		this.totalHtS2 = totalHtS2;
	}

	public BigDecimal getParticipationCogere() {
		return participationCogere;
	}

	public void setParticipationCogere(BigDecimal participationCogere) {
		this.participationCogere = participationCogere;
	}



	public String getArticleF2() {
		return articleF2;
	}



	public void setArticleF2(String articleF2) {
		this.articleF2 = articleF2;
	}



	public String getArticleS2() {
		return articleS2;
	}



	public void setArticleS2(String articleS2) {
		this.articleS2 = articleS2;
	}
	
	public Integer getBdg() {
		return bdg;
	}



	public void setBdg(Integer bdg) {
		this.bdg = bdg;
	}

	public ArticlesMaintenance(String articleE2, BigDecimal reducE2,
			Integer dureeE2, BigDecimal totalHtE2, BigDecimal participationCogere,
			String articleF2,BigDecimal reducF2, Integer dureeF2, BigDecimal totalHtF2,
			String articleS2, BigDecimal reducS2, Integer dureeS2,
			BigDecimal totalHtS2,BigDecimal bonusHt,Integer bdg) {
		super();
		this.articleE2 = articleE2;
		this.reducE2 = reducE2;
		this.dureeE2 = dureeE2;
		this.totalHtE2 = totalHtE2;
		this.articleF2 = articleF2;
		this.reducF2 = reducF2;
		this.dureeF2 = dureeF2;
		this.totalHtF2 = totalHtF2;
		this.articleS2 = articleS2;
		this.reducS2 = reducS2;
		this.dureeS2 = dureeS2;
		this.totalHtS2 = totalHtS2;
		this.participationCogere = participationCogere;
		this.bonusHt =bonusHt; 
		this.bdg = bdg;
	}
	public ArticlesMaintenance(String articleE2, BigDecimal reducE2,
			Integer dureeE2, BigDecimal totalHtE2, String articleF2,
			BigDecimal reducF2, Integer dureeF2, BigDecimal totalHtF2,
			String articleS2, BigDecimal reducS2, Integer dureeS2,
			BigDecimal totalHtS2, BigDecimal participationCogere,
			BigDecimal bonusHt, Integer bdg) {
		super();
		this.articleE2 = articleE2;
		this.reducE2 = reducE2;
		this.dureeE2 = dureeE2;
		this.totalHtE2 = totalHtE2;
		this.articleF2 = articleF2;
		this.reducF2 = reducF2;
		this.dureeF2 = dureeF2;
		this.totalHtF2 = totalHtF2;
		this.articleS2 = articleS2;
		this.reducS2 = reducS2;
		this.dureeS2 = dureeS2;
		this.totalHtS2 = totalHtS2;
		this.participationCogere = participationCogere;
		this.bonusHt = bonusHt;
		this.bdg = bdg;
	}



	public ArticlesMaintenance(String articleE2, BigDecimal reducE2,
			Integer dureeE2, BigDecimal totalHtE2, BigDecimal participationCogere,
			String articleF2,BigDecimal reducF2, Integer dureeF2, BigDecimal totalHtF2,
			String articleS2, BigDecimal reducS2,BigDecimal bonusHt, Integer dureeS2,
			BigDecimal totalHtS2) {
		super();
		this.articleE2 = articleE2;
		this.reducE2 = reducE2;
		this.dureeE2 = dureeE2;
		this.totalHtE2 = totalHtE2;
		
		this.articleF2 = articleF2;
		this.reducF2 = reducF2;
		this.dureeF2 = dureeF2;
		this.totalHtF2 = totalHtF2;
		this.articleS2 = articleS2;
		this.reducS2 = reducS2;
		this.dureeS2 = dureeS2;
		this.totalHtS2 = totalHtS2;
		this.participationCogere = participationCogere;
		this.bonusHt =bonusHt; 
	}

	public ArticlesMaintenance(String articleE2, BigDecimal reducE2,
			Integer dureeE2, BigDecimal totalHtE2, BigDecimal participationCogere,
			String articleF2,BigDecimal reducF2, Integer dureeF2, BigDecimal totalHtF2,
			String articleS2, BigDecimal reducS2, Integer dureeS2,
			BigDecimal totalHtS2,Integer bdg) {
		super();
		this.articleE2 = articleE2;
		this.reducE2 = reducE2;
		this.dureeE2 = dureeE2;
		this.totalHtE2 = totalHtE2;
		this.articleF2 = articleF2;
		this.reducF2 = reducF2;
		this.dureeF2 = dureeF2;
		this.totalHtF2 = totalHtF2;
		this.articleS2 = articleS2;
		this.reducS2 = reducS2;
		this.dureeS2 = dureeS2;
		this.totalHtS2 = totalHtS2;
		this.participationCogere = participationCogere;
		this.bdg = bdg;
	}
	public ArticlesMaintenance(String articleE2, BigDecimal reducE2,
			Integer dureeE2, BigDecimal totalHtE2, BigDecimal participationCogere,
			String articleF2,BigDecimal reducF2, Integer dureeF2, BigDecimal totalHtF2,
			String articleS2, BigDecimal reducS2, Integer dureeS2,
			BigDecimal totalHtS2) {
		super();
		this.articleE2 = articleE2;
		this.reducE2 = reducE2;
		this.dureeE2 = dureeE2;
		this.totalHtE2 = totalHtE2;
		this.articleF2 = articleF2;
		this.reducF2 = reducF2;
		this.dureeF2 = dureeF2;
		this.totalHtF2 = totalHtF2;
		this.articleS2 = articleS2;
		this.reducS2 = reducS2;
		this.dureeS2 = dureeS2;
		this.totalHtS2 = totalHtS2;
		this.participationCogere = participationCogere;
	}

	public BigDecimal getBonusHt() {
		return bonusHt;
	}



	public void setBonusHt(BigDecimal bonusHt) {
		this.bonusHt = bonusHt;
	}






}

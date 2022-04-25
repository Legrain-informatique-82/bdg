package fr.legrain.gestCom.Module_Document;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.documents.dao.TaTLigne;

public interface ILigneDocumentTiers {
	
	public TaTLigne getTaTLigne(); 
	public void setTaTLigne(TaTLigne taTLigne);

	public IDocumentTiers getTaDocument(); 
	//public void setTaDocument(IDocumentTiers taDocument); 
	
	public TaArticle getTaArticle();
	public void setTaArticle(TaArticle taArticle); 
	
	public Integer getNumLigneLDocument();
	public void setNumLigneLDocument(Integer numLigneLFacture);

	public String getLibLDocument();
	public void setLibLDocument(String libLFacture);
	
	public BigDecimal getQteLDocument(); 
	public void setQteLDocument(BigDecimal qteLFacture);
	
	public BigDecimal getQte2LDocument();
	public void setQte2LDocument(BigDecimal qte2LFacture);

	public String getU1LDocument();
	public void setU1LDocument(String u1LFacture);

	public String getU2LDocument();
	public void setU2LDocument(String u2LFacture); 

	public BigDecimal getPrixULDocument();
	public void setPrixULDocument(BigDecimal prixULFacture); 

	public BigDecimal getTauxTvaLDocument();
	public void setTauxTvaLDocument(BigDecimal tauxTvaLFacture);
	
	public String getCompteLDocument();
	public void setCompteLDocument(String compteLFacture);
	
	public String getCodeTvaLDocument();
	public void setCodeTvaLDocument(String codeTvaLFacture);
	
	public String getCodeTTvaLDocument();
	public void setCodeTTvaLDocument(String codeTTvaLFacture);

	public BigDecimal getMtHtLDocument();
	public void setMtHtLDocument(BigDecimal mtHtLFacture);
	
	public BigDecimal getMtTtcLDocument();
	public void setMtTtcLDocument(BigDecimal mtTtcLFacture);
	
	public BigDecimal getRemTxLDocument();
	public void setRemTxLDocument(BigDecimal remTxLFacture);
	
	public BigDecimal getRemHtLDocument();
	public void setRemHtLDocument(BigDecimal remHtLFacture);

	public BigDecimal getTxRemHtDocument();
	public void setTxRemHtDocument(BigDecimal txRemHtFacture);

	public BigDecimal getMtHtLApresRemiseGlobaleDocument();
	public void setMtHtLApresRemiseGlobaleDocument(BigDecimal mtHtLApresRemiseGlobaleDocument);
	
	public BigDecimal getMtTtcLApresRemiseGlobaleDocument();
	public void setMtTtcLApresRemiseGlobaleDocument(BigDecimal mtTtcLApresRemiseGlobaleDocument);
	
	public String getQuiCreeLDocument() ;
	public void setQuiCreeLDocument(String quiCreelApporteur) ;

	public Date getQuandCreeLDocument() ;
	public void setQuandCreeLDocument(Date quandCreeLApporteur);

	public String getQuiModifLDocument();
	public void setQuiModifLDocument(String quiModifLApporteur) ;

	public Date getQuandModifLDocument() ;
	public void setQuandModifLDocument(Date quandModifLApporteur) ;
	
	public String getIpAcces();
	public void setIpAcces(String ipAcces);

	public String getVersion() ;
	public void setVersion(String version);
}

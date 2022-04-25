package fr.legrain.gestCom.Module_Document;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import fr.legrain.documents.dao.TaRDocument;
import fr.legrain.tiers.dao.TaTiers;

public interface IDocumentTiers {
	public String getCodeDocument();
	public void setCodeDocument(String codeDocument);
	
	public int getIdDocument();
	public void setIdDocument(int idDocument);
	
	public Date getDateDocument();
	public void setDateDocument(Date dateDocument);
	
//	public Date getDateEchDocument();
//	public Date setDateEchDocument(Date dateEchDocument);
	
	public BigDecimal getTxRemHtDocument();
	public void setTxRemHtDocument(BigDecimal txRemHtDocument)	;
	
	public BigDecimal getTxRemTtcDocument();
	public void setTxRemTtcDocument(BigDecimal txRemTtcDocument);
	
	public Date getDateLivDocument();
	public void setDateLivDocument(Date dateLivDocument);
	
	public BigDecimal getNetHtCalc();
	
	public BigDecimal getNetTtcCalc();
	public void setNetTtcCalc(BigDecimal netTtcCalc);
	
	public BigDecimal getRegleCompletDocument();
	public BigDecimal getResteAReglerComplet();
	
	public String getLibelleDocument();
	public void setLibelleDocument(String libelleDocument);
	
	public Boolean getAccepte();
	public void setAccepte(Boolean accepte);
	
	public Integer getTtc();
	public void setTtc(Integer accepte);
	
	public Set<TaRDocument> getTaRDocuments();
	public void setTaRDocuments(Set<TaRDocument> taRDocuments);
	
	public TaTiers getTaTiers();
	public void setTaTiers(TaTiers taTiers);
	public void calculeTvaEtTotaux();
//	public void calculDateEcheance();
	public void calculDateEcheanceAbstract(Integer report, Integer finDeMois);
	public void setRelationDocument(Boolean relation);
	public boolean getRelationDocument();
	
	public String getTypeDocument();
	
	public void setExport(Integer export);
	public Integer getExport();
	
	public String getCommentaire();
	public void setCommentaire(String commentaire);
//	public void setTypeDocument(String typeDocument);
	
	public List<ILigneDocumentTiers> getLignesGeneral();
//	public void setLignes(ILigneDocumentTiers lignes);
	public IInfosDocumentTiers getTaInfosDocument();
	public boolean isLegrain();
/*
	public String getQuiCreeDocument() ;
	public void setQuiCreeDocument(String quiCreeApporteur) ;

	public Date getQuandCreeDocument() ;
	public void setQuandCreeDocument(Date quandCreeApporteur);

	public String getQuiModifDocument();
	public void setQuiModifDocument(String quiModifApporteur) ;

	public Date getQuandModifDocument() ;
	public void setQuandModifDocument(Date quandModifApporteur) ;
	
	public String getIpAcces();
	public void setIpAcces(String ipAcces);

	public String getVersion() ;
	public void setVersion(String version);
*/
}

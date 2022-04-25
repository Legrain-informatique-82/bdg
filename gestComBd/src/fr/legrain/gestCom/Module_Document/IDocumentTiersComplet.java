package fr.legrain.gestCom.Module_Document;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import fr.legrain.documents.dao.TaInfosFacture;
import fr.legrain.documents.dao.TaRDocument;
import fr.legrain.tiers.dao.TaTiers;

public interface IDocumentTiersComplet  {
	public String getCodeDocument();
	public void setCodeDocument(String codeDocument);
	
	public int getIdDocument();
	public void setIdDocument(int idDocument);
	
	public Date getDateDocument();
	public void setDateDocument(Date dateDocument);
	
	public Date getDateEchDocument();
	public void setDateEchDocument(Date dateEchDocument);
	
	public Date getDateLivDocument();
	public void setDateLivDocument(Date dateLivDocument);
	
	public String getLibelleDocument();
	public void setLibelleDocument(String libelleDocument);
	
	
	public TaTiers getTaTiers();
	public void setTaTiers(TaTiers taTiers);
	public void calculeTvaEtTotaux();
	public void setRelationDocument(Boolean relation);
	public boolean getRelationDocument();
	
	//public BigDecimal getAcomptes();
	//public Integer getExport();
	//public String getLibellePaiement();
	public BigDecimal getNetTtcCalc();
	public BigDecimal getNetHtCalc();
	public BigDecimal getRegleCompletDocument();
	public BigDecimal getResteAReglerComplet();
	//public String getCommentaire();
	public String getTypeDocument();
	public Integer getExport();
	public List<ILigneDocumentTiers> getLignesGeneral();
	public IInfosDocumentTiers getTaInfosDocument();
	//public void setAcomptes(BigDecimal acomptes);
	//public void setAvoirs(BigDecimal avoirs);
	//public void setExport(Integer export);
	//public void setLibellePaiement(String LibellePaiement);
	public void setNetTtcCalc(BigDecimal netTtcCalc);
	public void setRegleCompletDocument(BigDecimal regleCompletDocument);
	public void setResteAReglerComplet(BigDecimal resteAReglerComplet);
	//public void setCommentaire(String Commentaire);
	public void setTypeDocument(String typeDocument);
	
}

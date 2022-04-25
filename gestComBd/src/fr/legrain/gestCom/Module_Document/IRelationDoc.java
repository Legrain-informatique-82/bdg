package fr.legrain.gestCom.Module_Document;

import java.math.BigDecimal;

public interface IRelationDoc {
	public Integer getExport();
	public void setExport(Integer export);
	
	public BigDecimal getAffectation();
	public void setAffectation(BigDecimal montant);
}

package fr.legrain.tiers.statistiques.editors;

import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;

public interface IFormPageTiersContoller {
	
	public TaTiers getMasterEntity();
	
	public void setMasterEntity(TaTiers masterEntity);
	
	public TaTiersDAO getMasterDAO();
	
	public void setMasterDAO(TaTiersDAO masterDAO);
	
}

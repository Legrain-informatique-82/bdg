package fr.legrain.tiers.editor;

import fr.legrain.tiers.dao.TaFamilleTiers;
import fr.legrain.tiers.dao.TaFamilleTiersDAO;
import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;

public interface IEditorFamilleTiersExtension {
	
	public TaFamilleTiers getMasterEntity();
	
	public void setMasterEntity(TaFamilleTiers masterEntity);
	
	public TaFamilleTiersDAO getMasterDAO();
	
	public void setMasterDAO(TaFamilleTiersDAO masterDAO);
	
	/**
	 * Methode qui doit être appelée automatiquement lors de l'appel de la méthode <code>pageChange()</code> du MultipageEditor
	 * contenant c'est Editeur.
	 */
	public void activate();
	
}

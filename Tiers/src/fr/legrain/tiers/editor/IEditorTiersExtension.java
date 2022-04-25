package fr.legrain.tiers.editor;

import fr.legrain.tiers.dao.TaTiers;
import fr.legrain.tiers.dao.TaTiersDAO;

public interface IEditorTiersExtension {
	
	public TaTiers getMasterEntity();
	
	public void setMasterEntity(TaTiers masterEntity);
	
	public TaTiersDAO getMasterDAO();
	
	public void setMasterDAO(TaTiersDAO masterDAO);
	
	/**
	 * Methode qui doit être appelée automatiquement lors de l'appel de la méthode <code>pageChange()</code> du MultipageEditor
	 * contenant c'est Editeur.
	 */
	public void activate();
	
}

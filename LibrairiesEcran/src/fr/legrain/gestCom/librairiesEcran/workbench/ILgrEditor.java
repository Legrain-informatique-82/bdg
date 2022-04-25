package fr.legrain.gestCom.librairiesEcran.workbench;

import org.eclipse.ui.IEditorPart;

import fr.legrain.gestCom.librairiesEcran.swt.JPABaseControllerSWTStandard;

public interface ILgrEditor extends IEditorPart {
	/**
	 * Activation ou desactivation du contenu de l'editeur
	 * @param enabled
	 */
	public void setEnabled(boolean enabled);
	public boolean isEnabled();
	
	/**
	 * Validation du contenu (donnees) de l'editeur
	 * @return vrai ssi toutes les valeurs de l'editeur sont correctes
	 */
	public boolean validate();
	
	/**
	 * 
	 * @return vrai ssi dans un MultiPageEditor le changement de page est possible
	 */
	public boolean canLeaveThePage();
	
	public JPABaseControllerSWTStandard getController();
}

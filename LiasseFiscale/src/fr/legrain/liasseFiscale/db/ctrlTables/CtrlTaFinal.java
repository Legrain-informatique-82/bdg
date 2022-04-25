package fr.legrain.liasseFiscale.db.ctrlTables;

import fr.legrain.liasseFiscale.db.CtrlLiasse;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.MessCtrlLgr;

/**
 * @author Le Grain S.A
 */
public class CtrlTaFinal extends CtrlLiasse{
	
	public CtrlTaFinal() {
		super();
	}
	
	/**
	 * CtrlSaisie
	 * @param message TMessCtrlLGR
	 * @throws TExceptLgr - si la valeur n'est pas correcte pour l'un des controles
	 */
	public void ctrlSaisie(MessCtrlLgr message) throws ExceptLgr {
		super.ctrlSaisie(message); //controles généreaux à l'application
	}
	
}

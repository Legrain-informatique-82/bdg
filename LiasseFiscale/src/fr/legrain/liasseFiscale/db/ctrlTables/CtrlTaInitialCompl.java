package fr.legrain.liasseFiscale.db.ctrlTables;

import fr.legrain.liasseFiscale.db.CtrlLiasse;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.MessCtrlLgr;

/**
 * @author Le Grain S.A
 */
public class CtrlTaInitialCompl extends CtrlLiasse{
	
	public CtrlTaInitialCompl() {
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

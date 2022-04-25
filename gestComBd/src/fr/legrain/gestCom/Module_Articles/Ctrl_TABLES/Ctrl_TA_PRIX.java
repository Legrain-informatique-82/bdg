package fr.legrain.gestCom.Module_Articles.Ctrl_TABLES;

import fr.legrain.gestCom.Appli.Ctrl_Application;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.MessCtrlLgr;

/**
 * <p>Title: Gestion Commerciale</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author Le Grain A.A
 * @version 1.0
 */
public class Ctrl_TA_PRIX extends Ctrl_Application{
  public Ctrl_TA_PRIX() {
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

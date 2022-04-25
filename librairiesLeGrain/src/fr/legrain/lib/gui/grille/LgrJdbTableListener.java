package fr.legrain.lib.gui.grille;

import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Le Grain SA</p>
 * @author nicolas
 * @version 1.0
 */

public interface LgrJdbTableListener extends EventListener {
  
  /**
   * "Flèche bas" dans la dernière ligne de la table
   * @param evt
   */
  public void dernierLigneAtteinte(LgrJdbTableEvent evt);

}

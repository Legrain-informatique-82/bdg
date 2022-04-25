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

public interface LgrTableListener extends EventListener {

  public void selectionChange(LgrTableEvent evt);

}
